package it.appio.sender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.appio.sender.service.SenderService;
import it.appio.sender.timer.info.TimerInfo;

/**
 * Endpoint REST
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/appio/sender")
public class SenderController {

	// inject
	private final SenderService senderService;

	@Autowired
	public SenderController(SenderService senderService) {
		this.senderService = senderService;
	}

	@GetMapping("/status")
	public ResponseEntity<Object> status() {
		return senderService.testService();
	}

	@GetMapping("/timer")
	public ResponseEntity<Object> timerFindAll() {
		return senderService.getAllRunningTimers();
	}

	@DeleteMapping("/timer")
	public ResponseEntity<Object> timerDelete(@RequestParam(name = "id") String timerId) {
		return senderService.deleteTimer(timerId);
	}

	@PostMapping("/timer")
	public ResponseEntity<Object> timerCreate(@RequestBody TimerInfo timerInfo) {
		return senderService.createTimer(timerInfo);
	}
}
