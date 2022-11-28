package it.appio.sender.invio.producer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Oggetto ricevuto da WSO2 per notifiche AppIO in caso di risposta positiva
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public class AppIoProducer {
	
	private String id;
	
	private String fiscalCode;
	
	private String codInvio;

	private String tipoInvio;

	private String dbResult;

	
	// ---------------------
	// Getters and setters
	// ---------------------
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("fiscal_code")
	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	@JsonProperty("cod_invio")
	public String getCodInvio() {
		return codInvio;
	}

	public void setCodInvio(String codInvio) {
		this.codInvio = codInvio;
	}

	@JsonProperty("tipo_invio")
	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	@JsonProperty("db_result")
	public String getDbResult() {
		return dbResult;
	}

	public void setDbResult(String dbResult) {
		this.dbResult = dbResult;
	}
	
}
