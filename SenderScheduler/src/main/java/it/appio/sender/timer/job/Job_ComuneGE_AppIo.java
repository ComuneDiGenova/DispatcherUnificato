package it.appio.sender.timer.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import feign.FeignException;
import it.appio.sender.config.ExternalProperties;
import it.appio.sender.invio.producer.AppIoProducer;
import it.appio.sender.invio.service.InvioService;
import it.appio.sender.mapper.CodaMapper;
import it.appio.sender.mapper.ConfigurazioneMapper;
import it.appio.sender.mapper.EsitiMapper;
import it.appio.sender.mapper.IntervalloInvioMapper;
import it.appio.sender.model.CodaModel;
import it.appio.sender.model.ConfigurazioneModel;

/**
 * Invio messaggi AppIO per ComuneGE
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class Job_ComuneGE_AppIo implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(Job_ComuneGE_AppIo.class);

	private Map<String, ConfigurazioneModel> mappaConfig = new HashMap<>();
	private List<CodaModel> listaCodaOggi = new ArrayList<>();
	private List<CodaModel> listaCodaOrario = new ArrayList<>();
	private List<CodaModel> listaCodaInvio = new ArrayList<>();
	private boolean flagErrore = false;

	// Autowire
	private final CodaMapper codaMapper;
	private final ConfigurazioneMapper configMapper;
	private final EsitiMapper esitiMapper;
	private final ExternalProperties props;
	private final IntervalloInvioMapper intervalloInvioMapper;
	private final InvioService invioService;

	public Job_ComuneGE_AppIo(CodaMapper codaMapper, ConfigurazioneMapper configMapper, EsitiMapper esitiMapper, ExternalProperties props,
			IntervalloInvioMapper intervalloInvioMapper, InvioService invioService) {
		this.codaMapper = codaMapper;
		this.configMapper = configMapper;
		this.esitiMapper = esitiMapper;
		this.intervalloInvioMapper = intervalloInvioMapper;
		this.invioService = invioService;
		this.props = props;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		// -----------------------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------------------

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- INIT_PROPS - START");
		LOG.info("--------------------------------------------------------------");

		LOG.info(props.toString());

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- INIT_PROPS - END");
		LOG.info("--------------------------------------------------------------");

		// -----------------------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------------------

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- IO_CONFIGURAZIONE - START");
		LOG.info("--------------------------------------------------------------");

		// Leggo tutte le configurazioni presenti su IO_CONFIGURAZIONE e le metto nella mappa
		try {
			List<ConfigurazioneModel> listaConfig = configMapper.findAll();
			for (ConfigurazioneModel ce : listaConfig) {
				mappaConfig.put(ce.getTipoBatch(), ce);
			}
			LOG.info(String.format("Servizi totali trovati = %d", listaConfig.size()));
		} catch (Exception e) {
			LOG.error("Impossibile leggere IO_CONFIGURAZIONE");
			e.printStackTrace();
			flagErrore = true;
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- IO_CONFIGURAZIONE - END");
		LOG.info("--------------------------------------------------------------");

		// -----------------------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------------------

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- CODA_OGGI - START");
		LOG.info("--------------------------------------------------------------");

		if (!flagErrore) {
			try {
				listaCodaOggi = invioService.popolaCodaOggi(mappaConfig, props.getDELTA_RICERCA_ERRORE(), codaMapper);
				LOG.info(String.format("## Record totali estratti dalla coda di oggi: %d", listaCodaOggi.size()));
			} catch (Exception e) {
				LOG.error("Errore nella creazione della coda di oggi");
				e.printStackTrace();
				flagErrore = true;
			}
		} else {
			LOG.warn("Step saltato a causa di errore precedente!");
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- CODA_OGGI - END");
		LOG.info("--------------------------------------------------------------");

		// -----------------------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------------------

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- FILTRA_CODA - START");
		LOG.info("--------------------------------------------------------------");

		if (!flagErrore) {
			if (!listaCodaOggi.isEmpty()) {
				try {
					// Seleziono dalla coda di oggi solo i messaggi per la fascia oraria corrente
					listaCodaOrario = invioService.filtraCodaIntervalloInvio(listaCodaOggi, intervalloInvioMapper);
					LOG.info(String.format("## Messaggi totali per la fascia oraria corrente: %d", listaCodaOrario.size()));

					// Sottraggo anche quelli già presenti su IO_ESITI
					listaCodaInvio = invioService.filtraCodaEsitiPresenti(listaCodaOrario, esitiMapper, configMapper);
					LOG.info(String.format("## Messaggi totali da inviare: %d", listaCodaInvio.size()));
				} catch (Exception e) {
					LOG.error("Errore nel filtro della coda di oggi");
					e.printStackTrace();
					flagErrore = true;
				}
			} else {
				LOG.warn("La coda di oggi e' vuota!");
			}
		} else {
			LOG.warn("Step saltato a causa di errore precedente!");
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- FILTRA_CODA - END");
		LOG.info("--------------------------------------------------------------");

		// -----------------------------------------------------------------------------------------------------------------
		// -----------------------------------------------------------------------------------------------------------------

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- INVIO - START");
		LOG.info("--------------------------------------------------------------");

		if (!flagErrore) {
			if (!listaCodaInvio.isEmpty()) {
				long invioOk = 0L;
				long invioErrore = 0L;
				for (CodaModel rigaCoda : listaCodaInvio) {
					try {
						Optional<ConfigurazioneModel> configModel = configMapper.findByTipoBatch(rigaCoda.getTipoMessaggio());

						ResponseEntity<AppIoProducer> resInvio = invioService.inviaMessaggio(rigaCoda, configModel.get());
						int resCode = resInvio.getStatusCode().value();
						LOG.info("Invio OK");
						LOG.info(String.format("RES_CODE: %d - ID_CODA: %d - TIPO_INVIO: %s", resCode, rigaCoda.getIdCoda(),
								rigaCoda.getTipoMessaggio()));
						invioOk++;
					} catch (FeignException e) {
						if (e.status() == 500) {
							LOG.error("Invio KO - codice errore imprevisto");
						} else {
							LOG.warn("Invio KO - codice errore WSO2 noto");
						}
						LOG.warn(String.format("RES_CODE: %d - ID_CODA: %d - TIPO_INVIO: %s", e.status(), rigaCoda.getIdCoda(),
								rigaCoda.getTipoMessaggio()));
						invioErrore++;
					} catch (RuntimeException e) {
						LOG.error("Invio KO - errore interno");
						LOG.warn(String.format("Messaggio di errore: %s", e.getMessage()));
						e.printStackTrace();
						invioErrore++;
					}
				}

				long invioTotale = invioOk + invioErrore;

				LOG.info("");
				LOG.info("#############################################################");
				LOG.info("### RISULTATI INVIO ###");
				LOG.info(String.format("Invio OK: %d", invioOk));
				LOG.info(String.format("Invio ERRORE: %d", invioErrore));
				LOG.info(String.format("Invio TOTALE: %d", invioTotale));
				LOG.info("#############################################################");
				LOG.info("");
			} else {
				LOG.warn("La coda di invio e' vuota!");
			}
		} else {
			LOG.warn("Step saltato a causa di errore precedente!");
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- INVIO - END");
		LOG.info("--------------------------------------------------------------");

	}

}
