package it.appio.loader.timer.job;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.appio.loader.config.ExternalPropertiesConfig;
import it.appio.loader.timer.info.TimerInfo;
import it.appio.loader.timer.service.SchedulerService;

/**
 * Demone che salva la lista dei timer attivi
 * 
 * @author Montobbio
 * @version 1.0.9
 * @since 1.0.0
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class Job_DEMONE implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(Job_DEMONE.class);

	// inject
	private final SchedulerService schedulerService;

	@Autowired
	public Job_DEMONE(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@Value("${app.version}")
	private String appVersion;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- JOB_DEMONE - START ---");
		LOG.info("--------------------------------------------------------------");

		String risultato = null;

		try {

			// estraggo la lista dei timer attivi
			List<TimerInfo> timersList = schedulerService.getAllRunningTimers();
			LOG.info(String.format("APP_VERSION: %s", appVersion));
			LOG.info(String.format("Lista timer attivi: dimensione = %d, elenco = %s", timersList.size(), Arrays.toString(timersList.toArray())));

			if (timersList.size() > 0) {
				risultato = Arrays.toString(timersList.toArray());
			} else {
				risultato = "Nessun timer attivo";
			}

			// contenuto da scrivere
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String timestamp = LocalDateTime.now().format(formatter);
			List<String> lines = Arrays.asList(String.format("Versione della app: %s", appVersion), "",
					String.format("Estrazione timer del: %s", timestamp), "", String.format("Risultato: %s", risultato), "", "####################",
					"");

			// scelgo la posizione per salvare il file
			Path fileToSave = Paths.get(ExternalPropertiesConfig.CATALINA_BASE_SPRING, "conf", "LD_ComGE_AppIO_Loader_lista_timer_attivi.txt");

			// scrivo nel file oppure ne creo uno nuovo se è più vecchio di 30gg
			// Nota: creationTime potrebbe essere non valorizzato oppure coincidere con lastModifiedTime in base al sistema operativo in uso
			if (Files.exists(fileToSave)) {
				BasicFileAttributes attr = Files.readAttributes(fileToSave, BasicFileAttributes.class);
				FileTime creationTime = attr.creationTime();
				LocalDate creationDate = LocalDate.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
				LOG.info(String.format("Lista timer - data creazione: %s", creationDate));
				LocalDate limitDate = LocalDate.now().minusDays(30L);

				if (creationDate.isBefore(limitDate)) {
					Files.delete(fileToSave);
					Files.write(fileToSave, lines, StandardCharsets.UTF_8);
					LOG.warn("Lista timer attivi: ricreata");
				} else {
					Files.write(fileToSave, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
					LOG.info("Lista timer attivi: inserita riga");
				}
			} else {
				Files.write(fileToSave, lines, StandardCharsets.UTF_8);
				LOG.warn("Lista timer attivi: creata");
			}
		} catch (IOException e) {
			LOG.warn("Errore nella gestione del file della lista timer");
			LOG.error(e.getMessage(), e);
		} catch (SchedulerException e) {
			LOG.warn("Errore nella creazione del timer del demone");
			LOG.error(e.getMessage(), e);
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- JOB_DEMONE - END ---");
		LOG.info("--------------------------------------------------------------");

	}

}
