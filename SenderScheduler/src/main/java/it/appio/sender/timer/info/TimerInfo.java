package it.appio.sender.timer.info;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Dettagli per il timer. Anche usato come client consumer per le chiamate REST al timer
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public class TimerInfo {

	@JsonProperty("initial_offset_ms")
	private Long initialOffsetMs;

	@JsonProperty("cron_schedule")
	private String cronScheduleValue;

	@JsonProperty("timer_name")
	private String timerName;

	public Long getInitialOffsetMs() {
		return initialOffsetMs;
	}

	public void setInitialOffsetMs(Long initialOffsetMs) {
		this.initialOffsetMs = initialOffsetMs;
	}

	public String getCronScheduleValue() {
		return cronScheduleValue;
	}

	public void setCronScheduleValue(String cronScheduleValue) {
		this.cronScheduleValue = cronScheduleValue;
	}

	public String getTimerName() {
		return timerName;
	}

	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}

	@Override
	public String toString() {
		return "TimerInfo [initialOffsetMs=" + initialOffsetMs + ", cronScheduleValue=" + cronScheduleValue + ", timerName=" + timerName + "]";
	}

}
