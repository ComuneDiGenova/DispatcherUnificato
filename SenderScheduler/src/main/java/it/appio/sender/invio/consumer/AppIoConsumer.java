package it.appio.sender.invio.consumer;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Oggetto da inviare a WSO2 per notifiche AppIo
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppIoConsumer {

	private String fiscalCode;
	
	private String token;
	
	private Integer timeToLive;
	
	private String tipoInvio;
	
	private String idCoda;
	
	private String note;
	
	private Map<String, Object> content;
	
	
	// ---------------------
	// Getters and setters
	// ---------------------

	@JsonProperty("fiscal_code")
	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty("time_to_live")
	public Integer getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}

	@JsonProperty("tipo_invio")
	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	@JsonProperty("id_coda")
	public String getIdCoda() {
		return idCoda;
	}

	public void setIdCoda(String idCoda) {
		this.idCoda = idCoda;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
	
}
