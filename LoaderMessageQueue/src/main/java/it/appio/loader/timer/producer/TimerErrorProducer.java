package it.appio.loader.timer.producer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Risposta per le chiamate REST al timer
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public class TimerErrorProducer {

	@JsonProperty("error_title")
	private String errorTitle;

	@JsonProperty("error_code")
	private int errorCode;

	@JsonProperty("error_desc")
	private String errorDesc;

	public String getErrorTitle() {
		return errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Override
	public String toString() {
		return "TimerErrorProducer [errorTitle=" + errorTitle + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc + "]";
	}

}
