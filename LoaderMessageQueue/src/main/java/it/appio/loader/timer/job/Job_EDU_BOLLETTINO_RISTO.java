package it.appio.loader.timer.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.appio.loader.entity.appio.prod.CodaProdEntity;
import it.appio.loader.entity.appio.test.CodaTestEntity;
import it.appio.loader.repository.scolastico.prod.AppIODispIuvDTO;
import it.appio.loader.service.DB_AppIO_Prod_Service;
import it.appio.loader.service.DB_AppIO_Test_Service;
import it.appio.loader.service.DB_Scol_Prod_Service;
import it.appio.loader.timer.properties.LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO;

/**
 * Caricamento IO_CODA per ComuneGE - EDU_BOLLETTINO_RISTO
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class Job_EDU_BOLLETTINO_RISTO implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(Job_EDU_BOLLETTINO_RISTO.class);

	private final DB_Scol_Prod_Service dbScolProdService;
	private final DB_AppIO_Test_Service dbAppIoTestService;
	private final DB_AppIO_Prod_Service dbAppIOProdService;
	private final LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO localProps;

	@Autowired
	public Job_EDU_BOLLETTINO_RISTO(DB_Scol_Prod_Service dbScolProdService, DB_AppIO_Test_Service dbAppIoTestService,
			DB_AppIO_Prod_Service dbAppIOProdService, LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO localProps) {
		this.dbScolProdService = dbScolProdService;
		this.dbAppIoTestService = dbAppIoTestService;
		this.dbAppIOProdService = dbAppIOProdService;
		this.localProps = localProps;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- RIEMPO_CODA_COMUNE - START ---");
		LOG.info("--------------------------------------------------------------");

		// stampo le properties dell'invio
		LOG.debug(String.format("## AppIO Properties: %s", localProps.toString()));

		// giorno inizio invii
		LocalDateTime giornoInvio = localProps.getDATA_INVIO_BASE();

		// leggo la tabella sorgente dal DB Scolastico
		List<AppIODispIuvDTO> listaSorgente = new ArrayList<>();

		if ("TRUE".equalsIgnoreCase(localProps.getTOP())) {
			listaSorgente = dbScolProdService.findSources_DispIuv();
		} else {
			listaSorgente = dbScolProdService.findAllSources_DispIuv();
		}

		int listaSorgenteSize = listaSorgente.size();
		LOG.info(String.format("## Record totali della sorgente: %d", listaSorgenteSize));

		// ciclo sui dati da inviare
		int nEsimoRecordDaInserire = 0;
		int totaleRecordInseriti = 0;
		for (AppIODispIuvDTO source : listaSorgente) {

			// calcolo la data di invio in base agli invii giornalieri max
			if (localProps.getINVII_AL_GIORNO() > 0) {
				if (nEsimoRecordDaInserire < localProps.getINVII_AL_GIORNO()) {
					nEsimoRecordDaInserire++;
				} else {
					nEsimoRecordDaInserire = 1;
					giornoInvio = trovaPrimoGiornoUtile(giornoInvio);
				}
				LOG.info(String.format("Giorno invio calcolato: %s", giornoInvio));
			} else {
				LOG.info(String.format("Giorno invio unico: %s", giornoInvio));
			}

			// scrivo la riga su IO_CODA
			if (localProps.getAMBIENTE_TARGET().equalsIgnoreCase("TEST")) {
				// COMUNE TEST
				CodaTestEntity recordDinamico = creaCodaComuneTestEntity(source, giornoInvio, localProps);
				CodaTestEntity entityCreataTest = dbAppIoTestService.addRigaCoda(recordDinamico);

				if (entityCreataTest != null) {
					LOG.info(String.format("Creata riga per TIPO_MESSAGGIO: %s", entityCreataTest.getTipoMessaggio()));
					totaleRecordInseriti++;
				}
			} else if (localProps.getAMBIENTE_TARGET().equalsIgnoreCase("PROD")) {
				// COMUNE PROD
				CodaProdEntity recordDinamico = creaCodaComuneProdEntity(source, giornoInvio, localProps);
				CodaProdEntity entityCreataProd = dbAppIOProdService.addRigaCoda(recordDinamico);

				if (entityCreataProd != null) {
					LOG.info(String.format("Creata riga per TIPO_MESSAGGIO: %s", entityCreataProd.getTipoMessaggio()));
					totaleRecordInseriti++;
				}
			} else {
				throw new JobExecutionException(String.format("Ambiente target: %s non ammesso", localProps.getAMBIENTE_TARGET()));
			}

		}

		LOG.info(String.format("## Record totali inseriti: %d", totaleRecordInseriti));

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- RIEMPO_CODA_COMUNE - END ---");
		LOG.info("--------------------------------------------------------------");

	}

	// --------------------------------------------------------------------------------------------------------------------------
	// --- METODI GENERALI
	// --------------------------------------------------------------------------------------------------------------------------

	// Creo il record dinamico in TEST
	private CodaTestEntity creaCodaComuneTestEntity(AppIODispIuvDTO source, LocalDateTime giornoInvio,
			LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO localProps) {
		final CodaTestEntity ce = new CodaTestEntity();

		// ce.setCodiceFiscale("MNTMHL79E26D969G");
		ce.setCodiceFiscale(source.getCodiceFiscale().toUpperCase());
		ce.setDataIns(LocalDateTime.now());
		ce.setDataInvioPrevisto(giornoInvio);
		ce.setIntervalloOrarioInvio(localProps.getORARIO());
		ce.setTipoMessaggio(localProps.getTIPO_MESSAGGIO());
		ce.setPayloadMessaggio(creaPayloadMessaggio(source, localProps.getMESSAGGIO_BASE()));
		ce.setDataScadenza(localProps.getDATA_SCADENZA());
		ce.setNote(localProps.getNOTE());
		if (localProps.getPROMEMORIA() != null)
			ce.setDataPromemoriaIo(localProps.getPROMEMORIA());

		// sezione pagamento
		ce.setNoticeNumber(source.getCodiceIuv());
		ce.setAmount(source.getImportoTotale());
		if (localProps.getINVALID() != null)
			ce.setInvalidAfterDueDate(localProps.getINVALID());

		return ce;
	}

	// Creo il record dinamico in PROD
	private CodaProdEntity creaCodaComuneProdEntity(AppIODispIuvDTO source, LocalDateTime giornoInvio,
			LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO localProps) {
		final CodaProdEntity ce = new CodaProdEntity();

		// ce.setCodiceFiscale("MNTMHL79E26D969G");
		ce.setCodiceFiscale(source.getCodiceFiscale().toUpperCase());
		ce.setDataIns(LocalDateTime.now());
		ce.setDataInvioPrevisto(giornoInvio);
		ce.setIntervalloOrarioInvio(localProps.getORARIO());
		ce.setTipoMessaggio(localProps.getTIPO_MESSAGGIO());
		ce.setPayloadMessaggio(creaPayloadMessaggio(source, localProps.getMESSAGGIO_BASE()));
		ce.setDataScadenza(localProps.getDATA_SCADENZA());
		ce.setNote(localProps.getNOTE());
		if (localProps.getPROMEMORIA() != null)
			ce.setDataPromemoriaIo(localProps.getPROMEMORIA());

		// sezione pagamento
		ce.setNoticeNumber(source.getCodiceIuv());
		ce.setAmount(source.getImportoTotale());
		if (localProps.getINVALID() != null)
			ce.setInvalidAfterDueDate(localProps.getINVALID());

		return ce;
	}

	// Creo il messaggio custom
	private String creaPayloadMessaggio(AppIODispIuvDTO source, String messaggioBase) {

		final double importoVirgola = (double) source.getImportoTotale() / 100d;
		final String payload = String.format(messaggioBase, source.getEmissione(), 2022, importoVirgola);

		return payload;
	}

	// Incremento la data di invio quando si raggiunge il limite di invii giornalieri
	private static LocalDateTime trovaPrimoGiornoUtile(LocalDateTime giorno) {
		return giorno.plusDays(1);
	}

}
