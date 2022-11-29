package it.appio.loader.service;

import java.util.Arrays;
import java.util.List;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.appio.loader.timer.info.TimerInfo;
import it.appio.loader.timer.job.Job_DEMONE;
import it.appio.loader.timer.producer.ResponseHandler;
import it.appio.loader.timer.producer.TimerErrorProducer;
import it.appio.loader.timer.producer.TimerProducer;
import it.appio.loader.timer.service.SchedulerService;

/**
 * Definisce le property del timer e lancia lo scheduler con la classe di servizi che vogliamo eseguire
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class LoaderService {

	private static final Logger LOG = LoggerFactory.getLogger(LoaderService.class);

	// inject
	private final SchedulerService schedulerService;

	@Autowired
	public LoaderService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	/**
	 * Servizio di test
	 */
	public ResponseEntity<Object> testService() {

		LOG.info("");
		LOG.info("--------------------------------------------------------------");
		LOG.info("Chiamata di test");
		LOG.info("--------------------------------------------------------------");
		LOG.info("");

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Elenco di tutti i timer attivi
	 * 
	 * @return elenco dei timer attivi
	 */
	public ResponseEntity<Object> getAllRunningTimers() {

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- getAllRunningTimers - START");
		LOG.info("--------------------------------------------------------------");

		Object responseBody = null;
		HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpHeaders httpHeaders = new HttpHeaders();

		try {
			List<TimerInfo> timersList = schedulerService.getAllRunningTimers();
			LOG.info(String.format("Lista timer attivi: dimensione = %d, elenco = %s", timersList.size(), Arrays.toString(timersList.toArray())));

			if (timersList.size() > 0) {
				responseBody = timersList;
				responseStatus = HttpStatus.OK;
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			} else {
				responseStatus = HttpStatus.NO_CONTENT;
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

			TimerErrorProducer error = new TimerErrorProducer();
			error.setErrorTitle("Errore estrazione lista timer");
			error.setErrorCode(responseStatus.value());
			error.setErrorDesc(e.getMessage());
			responseBody = error;
			
			httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- getAllRunningTimers - END");
		LOG.info("--------------------------------------------------------------");

		return ResponseHandler.generateResponse(responseBody, httpHeaders, responseStatus);
	}

	/**
	 * Cancella un timer esistente
	 * 
	 * @param timerId
	 * @return risultato cancellazione timer
	 */
	public ResponseEntity<Object> deleteTimer(String timerId) {

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- deleteTimer - START");
		LOG.info("--------------------------------------------------------------");

		Object responseBody = null;
		HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpHeaders httpHeaders = new HttpHeaders();

		try {
			if (schedulerService.deleteTimer(timerId)) {
				LOG.info(String.format("Timer: %s cancellato", timerId));

				responseStatus = HttpStatus.ACCEPTED;

				TimerProducer response = new TimerProducer();
				response.setTimerStatus("deleted");
				responseBody = response;
				
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			} else {
				responseStatus = HttpStatus.NOT_FOUND;

				TimerErrorProducer error = new TimerErrorProducer();
				error.setErrorTitle("Errore cancellazione timer");
				error.setErrorCode(responseStatus.value());
				error.setErrorDesc("Timer non presente");
				responseBody = error;
				
				LOG.warn(responseBody.toString());
				
				httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

			TimerErrorProducer error = new TimerErrorProducer();
			error.setErrorTitle("Errore cancellazione timer");
			error.setErrorCode(responseStatus.value());
			error.setErrorDesc(e.getMessage());
			responseBody = error;
			
			httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- deleteTimer - END");
		LOG.info("--------------------------------------------------------------");

		return ResponseHandler.generateResponse(responseBody, httpHeaders, responseStatus);
	}

	/**
	 * Crea il timer per la classe passata come parametro
	 * 
	 * @param timerInfoClient
	 * @param jobClass
	 * @return risultato creazione timer
	 */
	public ResponseEntity<Object> createTimer(TimerInfo timerInfoClient, Class<?> jobClass) {

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- createTimer - START");
		LOG.info("--------------------------------------------------------------");

		Object responseBody = null;
		HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpHeaders httpHeaders = new HttpHeaders();

		try {
			TimerInfo timerInfo = new TimerInfo();

			timerInfo.setInitialOffsetMs(timerInfoClient.getInitialOffsetMs() != null ? timerInfoClient.getInitialOffsetMs() : 10000L);
			timerInfo.setCronScheduleValue(timerInfoClient.getCronScheduleValue());
			timerInfo.setTimerName(timerInfoClient.getTimerName());

			schedulerService.schedule(jobClass, timerInfo);
			LOG.info(String.format("Schedulato nuovo timer per la classe: %s con info: %s", jobClass.getSimpleName(), timerInfo));

			responseStatus = HttpStatus.CREATED;

			TimerProducer response = new TimerProducer();
			response.setTimerStatus("created");
			responseBody = response;
			
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		} catch (SchedulerException e) {
			responseStatus = HttpStatus.NOT_ACCEPTABLE;

			TimerErrorProducer error = new TimerErrorProducer();
			error.setErrorTitle("Errore schedulazione timer");
			error.setErrorCode(responseStatus.value());
			error.setErrorDesc(e.getMessage());
			responseBody = error;

			LOG.warn(responseBody.toString());
			
			httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

			TimerErrorProducer error = new TimerErrorProducer();
			error.setErrorTitle("Internal error");
			error.setErrorCode(responseStatus.value());
			error.setErrorDesc(e.getMessage());
			responseBody = error;
			
			httpHeaders.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- createTimer - END");
		LOG.info("--------------------------------------------------------------");

		return ResponseHandler.generateResponse(responseBody, httpHeaders, responseStatus);
	}

	/**
	 * Crea il timer per il demone che salva la lista dei timer attivi
	 */
	public void createDemon() {

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- createDemon - START");
		LOG.info("--------------------------------------------------------------");

		try {
			TimerInfo timerInfo = new TimerInfo();

			timerInfo.setInitialOffsetMs(5000L);
			timerInfo.setCronScheduleValue("5 */15 7-20 * * ?");
			timerInfo.setTimerName("Demone_Lista_Timer");

			schedulerService.schedule(Job_DEMONE.class, timerInfo);
			LOG.info(String.format("Schedulato nuovo timer per la classe: %s con info: %s", Job_DEMONE.class.getSimpleName(), timerInfo));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		LOG.info("--------------------------------------------------------------");
		LOG.info("--- createDemon - END");
		LOG.info("--------------------------------------------------------------");

	}

}
