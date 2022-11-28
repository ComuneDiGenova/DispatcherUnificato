package it.appio.sender.timer.producer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Risposta per le chiamate REST al timer
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public class TimerProducer {

	@JsonProperty("timer_status")
	private String timerStatus;

	public String getTimerStatus() {
		return timerStatus;
	}

	public void setTimerStatus(String timerStatus) {
		this.timerStatus = timerStatus;
	}

}
