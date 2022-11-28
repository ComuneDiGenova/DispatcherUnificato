package it.appio.sender.invio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.appio.sender.config.ExternalProperties;
import it.appio.sender.invio.client.WSO2FeignClient;
import it.appio.sender.invio.consumer.AppIoConsumer;
import it.appio.sender.invio.producer.AppIoProducer;
import it.appio.sender.invio.util.BATCH_RIPETIBILE;
import it.appio.sender.mapper.CodaMapper;
import it.appio.sender.mapper.ConfigurazioneMapper;
import it.appio.sender.mapper.EsitiMapper;
import it.appio.sender.mapper.IntervalloInvioMapper;
import it.appio.sender.model.CodaModel;
import it.appio.sender.model.ConfigurazioneModel;
import it.appio.sender.model.EsitiModel;
import it.appio.sender.model.IntervalloInvioModel;

/**
 * Metodi di business per l'invio dei messaggi tramite WSO2
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class InvioService {

	private static final Logger LOG = LoggerFactory.getLogger(InvioService.class);
	private static final String SEGNAPOSTO_LD_A_CAPO = "QQ";
	private static final String MARKDOWN_A_CAPO = "  \n ";
	private static final int[] LISTA_ERRORI_RE_INVIO = { 429, 500 };

	// Autowire
	private final ExternalProperties props;
	private final WSO2FeignClient wso2Client;

	public InvioService(ExternalProperties props, WSO2FeignClient wso2Client) {
		this.props = props;
		this.wso2Client = wso2Client;
	}

	/**
	 * Estrae da IO_CODA i messaggi da inviare oggi
	 * 
	 * @param Map<String, ConfigurazioneModel> mappaConfig
	 * @param int         deltaRicercaErrore numero di giorni da recuperare per invii in errore
	 * @param CodaMapper
	 * @return Lista dei messaggi da inviare oggi
	 */
	public List<CodaModel> popolaCodaOggi(Map<String, ConfigurazioneModel> mappaConfig, int deltaRicercaErrore, CodaMapper CodaMapper) {
		LOG.debug("--- popolaCodaOggi : START");

		// inizializzo le viariabili di oggi
		LocalDateTime oggi = LocalDate.now().atStartOfDay();
		LOG.debug(String.format("Data odierna: %s", oggi));
		List<CodaModel> listaCodaOggi = new ArrayList<>();

		// per ogni tipo_batch su IO_CONFIGURAZIONE controllo in IO_CODA se ci sono invii da fare oggi
		LOG.debug(String.format("La mappa contiene %d configurazioni distinte", mappaConfig.size()));
		for (String tipoBatch : mappaConfig.keySet()) {

			ConfigurazioneModel config = mappaConfig.get(tipoBatch);
			List<CodaModel> listaCodaOggiPerTipoInvio = new ArrayList<>();
			int deltaRicerca = 0;

			// nel caso di invio da ripetere per errore prendo anche i dati dei giorni precedenti
			if (config.getRipetibile().equalsIgnoreCase(BATCH_RIPETIBILE.ERRORE.name())) {
				deltaRicerca = deltaRicercaErrore;
			}

			// estraggo la lista da IO_CODA
			for (int i = 0; i <= deltaRicerca; i++) {
				long deltaQuery = deltaRicerca - i;
				listaCodaOggiPerTipoInvio.addAll(CodaMapper.findByDataInvioPrevistoAndTipoMessaggio(tipoBatch, oggi.minusDays(deltaQuery)));
			}

			// controllo anche che la DATA_SCADENZA sia successiva ad oggi (se presente)
			long messaggiPerTipo = 0L;
			for (CodaModel rigaCoda : listaCodaOggiPerTipoInvio) {
				if (rigaCoda.getDataScadenza() != null) {
					if (rigaCoda.getDataScadenza().isAfter(LocalDateTime.now())) {
						listaCodaOggi.add(rigaCoda);
						messaggiPerTipo++;
					}
				} else {
					listaCodaOggi.add(rigaCoda);
					messaggiPerTipo++;
				}
			}

			LOG.debug(String.format("Record trovati: %d per tipo invio: %s", messaggiPerTipo, tipoBatch));
		}

		LOG.debug("--- popolaCodaOggi : END");
		return listaCodaOggi;
	}

	/**
	 * Filtra la coda di oggi estraendo solo i messaggi della fascia oraria corrente
	 * 
	 * @param List<CodaModel>       listaCodaOggi
	 * @param IntervalloInvioMapper
	 * @return Lista dei messaggi da inviare nella fascia oraria corrente
	 */
	public List<CodaModel> filtraCodaIntervalloInvio(List<CodaModel> listaCodaOggi, IntervalloInvioMapper IntervalloInvioMapper) {
		LOG.debug("--- filtraCodaIntervalloInvio : START");
		List<CodaModel> listaCodaIntervallo = new ArrayList<>();
		int now = LocalDateTime.now().getHour();

		for (CodaModel entity : listaCodaOggi) {
			if (entity.getIntervalloOrarioInvio() == 0) {
				LOG.debug("Messaggio senza fascia oraria");
				listaCodaIntervallo.add(entity);
			} else {
				Optional<IntervalloInvioModel> intEntity = IntervalloInvioMapper.findById(entity.getIntervalloOrarioInvio());

				if (intEntity.isPresent()) {
					int oraInizio = intEntity.get().getOraInizio();
					int oraFine = intEntity.get().getOraFine();

					if (now >= oraInizio && now < oraFine) {
						LOG.debug("Messaggio fascia oraria corrente - OK");
						listaCodaIntervallo.add(entity);
					} else {
						LOG.debug("Messaggio fascia oraria corrente - KO");
					}
				} else {
					LOG.error(String.format("Errore nel filtro coda per intervallo orario con valore: %d", entity.getIntervalloOrarioInvio()));
					throw new RuntimeException();
				}
			}
		}

		LOG.debug("--- filtraCodaIntervalloInvio : END");
		return listaCodaIntervallo;
	}

	/**
	 * Filtra la coda orario sottraendo i messaggi già presenti su IO_ESITI
	 * 
	 * @param List<CodaModel>      listaCodaOrario
	 * @param EsitiMapper
	 * @param ConfigurazioneMapper
	 * @return Lista dei messaggi da inviare
	 */
	public List<CodaModel> filtraCodaEsitiPresenti(List<CodaModel> listaCodaOrario, EsitiMapper EsitiMapper, ConfigurazioneMapper configRepository) {
		LOG.debug("--- filtraCodaEsitiPresenti : START");
		List<CodaModel> listaCodaInvio = new ArrayList<>();
		boolean inEsiti = false;

		for (CodaModel entity : listaCodaOrario) {
			// query dentro IO_ESITI per ID_CODA
			List<EsitiModel> entityByIdCoda = EsitiMapper.findByIdCoda(entity.getIdCoda().toString());
			inEsiti = !entityByIdCoda.isEmpty();

			// valuto l'eventuale presenza del messaggio in IO_ESITI
			if (inEsiti) {
				Optional<ConfigurazioneModel> configEntity = configRepository.findByTipoBatch(entity.getTipoMessaggio());

				if (configEntity.isPresent()) {
					String configRipetibile = configEntity.get().getRipetibile();

					if (configRipetibile.equalsIgnoreCase(BATCH_RIPETIBILE.MAI.name())) {
						// escludo l'invio non aggiungendo la riga corrente nella lista di uscita
						LOG.debug(String.format("Messaggio con ID_CODA: %d escluso dall'invio in quanto gia' presente in esiti per RIPETIBILE: %s",
								entity.getIdCoda(), configRipetibile));

					} else if (configRipetibile.equalsIgnoreCase(BATCH_RIPETIBILE.ERRORE.name())) {
						// metto la riga degli esiti più recente come primo elemento e verifico il RESPONSE_CODE
						entityByIdCoda.sort(Comparator.comparing(EsitiModel::getDataInizio).reversed());
						int resCodeEsiti = entityByIdCoda.get(0).getResponseCode().intValue();

						boolean isReinvio = false;
						for (int errore : LISTA_ERRORI_RE_INVIO) {
							if (errore == resCodeEsiti) {
								// essendo previsto il re-invio di questo errore, aggiungo la riga corrente nella lista di uscita
								LOG.debug(String.format("Messaggio con ID_CODA: %d aggiunto all'invio anche se presente in esiti per RIPETIBILE: %s",
										entity.getIdCoda(), configRipetibile));
								listaCodaInvio.add(entity);
								isReinvio = true;
								break;
							}
						}
						if (!isReinvio)
							LOG.debug(String.format(
									"Messaggio con ID_CODA: %d escluso dall'invio in quanto gia' presente in esiti per RIPETIBILE: %s e RESPONSE_CODE: %d",
									entity.getIdCoda(), configRipetibile, resCodeEsiti));

					} else if (configRipetibile.equalsIgnoreCase(BATCH_RIPETIBILE.SEMPRE.name())) {
						LOG.debug(String.format("Messaggio con ID_CODA: %d aggiunto all'invio anche se presente in esiti per RIPETIBILE: %s",
								entity.getIdCoda(), configRipetibile));
						listaCodaInvio.add(entity);

					} else {
						LOG.error("Valore di BATCH_RIPETIBILE non ammesso. Non invio il messaggio");
					}

				} else {
					LOG.error(String.format("Errore nel filtro coda per esiti presenti. TIPO_BATCH: %s sconosciuto", entity.getTipoMessaggio()));
					throw new RuntimeException();
				}

			} else {
				LOG.debug(String.format("Messaggio con ID_CODA: %d aggiunto all'invio", entity.getIdCoda()));
				listaCodaInvio.add(entity);
			}
		}

		LOG.debug("--- filtraCodaEsitiPresenti : END");
		return listaCodaInvio;
	}

	/**
	 * Invia i messaggi invocando l'API REST di WSO2
	 * 
	 * @param rigaCoda
	 * @param rigaConfig
	 * @return ResponseEntity<AppIoProducer> Risposta WSO2
	 */
	public ResponseEntity<AppIoProducer> inviaMessaggio(CodaModel rigaCoda, ConfigurazioneModel rigaConfig) {

		// Creo il corretto consumer da inviare come body
		AppIoConsumer consumer = creaAppIoConsumer(rigaCoda, rigaConfig);

		// Chiamo il backend WSO2 (Versione OpenFeign)
		String bearerToken = props.getWSO2_BEARER();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(bearerToken);
		ResponseEntity<AppIoProducer> result = wso2Client.sendMessage(headers, consumer);

		return result;
	}

	private AppIoConsumer creaAppIoConsumer(CodaModel rigaCoda, ConfigurazioneModel rigaConfig) {

		AppIoConsumer consumer = new AppIoConsumer();
		consumer.setFiscalCode(StringUtils.isNotBlank(rigaCoda.getCodiceFiscale()) ? rigaCoda.getCodiceFiscale().toUpperCase() : "00000000000");
		consumer.setToken(rigaConfig.getToken());
		consumer.setTimeToLive(rigaConfig.getTimeToLive());
		consumer.setTipoInvio(rigaCoda.getTipoMessaggio());
		consumer.setIdCoda(rigaCoda.getIdCoda().toString());
		consumer.setNote(rigaCoda.getNote());

		// Sezione content
		Map<String, Object> mappaContent = new HashMap<String, Object>();
		mappaContent.put("subject", rigaConfig.getSubject());
		String payloadFinale = creaPayloadFinaleAppIO(rigaCoda, rigaConfig);
		mappaContent.put("markdown", payloadFinale);
		if (rigaCoda.getDataPromemoriaIo() != null)
			mappaContent.put("due_date", rigaCoda.getDataPromemoriaIo());

		// Sezione payment_data
		if (rigaCoda.getAmount() != null) {
			Map<String, Object> mappaPayment = new HashMap<String, Object>();
			mappaPayment.put("amount", rigaCoda.getAmount());
			mappaPayment.put("notice_number", rigaCoda.getNoticeNumber());
			if (rigaCoda.getInvalidAfterDueDate() != null)
				mappaPayment.put("invalid_afer_due_date", Boolean.valueOf(rigaCoda.getInvalidAfterDueDate()));

			mappaContent.put("payment_data", mappaPayment);
		}

		consumer.setContent(mappaContent);

		return consumer;
	}

	private String creaPayloadFinaleAppIO(CodaModel rigaCoda, ConfigurazioneModel rigaConfig) {

		String payloadFinale;

		// sbivio tra invio statico e dinamico
		payloadFinale = "STATICO".equalsIgnoreCase(rigaConfig.getPayloadType()) ? rigaConfig.getMessaggioBase() : rigaCoda.getPayloadMessaggio();

		// inserisco gli a capo
		Pattern pattern = Pattern.compile(SEGNAPOSTO_LD_A_CAPO);
		Matcher matcher = pattern.matcher(payloadFinale);
		payloadFinale = matcher.replaceAll(Matcher.quoteReplacement(MARKDOWN_A_CAPO));

		return payloadFinale;

	}

}
