package it.appio.loader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.appio.loader.service.LoaderService;
import it.appio.loader.timer.info.TimerInfo;
import it.appio.loader.timer.job.Job_EDU_BOLLETTINO_RISTO;

/**
 * Endpoint REST
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/appio/loader")
public class LoaderController {

	// inject
	private final LoaderService loaderService;

	@Autowired
	public LoaderController(LoaderService loaderService) {
		this.loaderService = loaderService;
	}

	// --------------------------------------------------------------------------------------------------------------------------
	// --- Metodi di uso generale
	// --------------------------------------------------------------------------------------------------------------------------

	@GetMapping("/status")
	public ResponseEntity<Object> status() {
		return loaderService.testService();
	}

	@GetMapping("/timer")
	public ResponseEntity<Object> timerFindAll() {
		return loaderService.getAllRunningTimers();
	}

	@DeleteMapping("/timer")
	public ResponseEntity<Object> timerDelete(@RequestParam(name = "id") String timerId) {
		return loaderService.deleteTimer(timerId);
	}

	// --------------------------------------------------------------------------------------------------------------------------
	// --- AppIO - Scolastico - EDU_BOLLETTINO_RISTO
	// --------------------------------------------------------------------------------------------------------------------------

	@PostMapping("/scolastico/eduBollettinoRisto")
	public ResponseEntity<Object> eduBollettinoRistoTimerStart(@RequestBody TimerInfo timerInfo) {
		return loaderService.createTimer(timerInfo, Job_EDU_BOLLETTINO_RISTO.class);
	}

}
