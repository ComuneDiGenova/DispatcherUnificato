package it.appio.sender.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Entity corrispondente alla tabella IO_ESITI
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonPropertyOrder(alphabetic = true)
public class EsitiModel {

	@JsonProperty("id_esiti")
	private Long idEsiti;

	@JsonProperty("codice_fiscale")
	private String codiceFiscale;

	@JsonProperty("tipo_invio")
	private String tipoInvio;

	@JsonProperty("data_inizio")
	private LocalDateTime dataInizio;

	@JsonProperty("data_fine")
	private LocalDateTime dataFine;

	@JsonProperty("response_code")
	private Integer responseCode;

	@JsonProperty("response_desc")
	private String responseDesc;

	@JsonProperty("message_id")
	private String messageId;

	private String note;

	@JsonProperty("cod_invio")
	private String codInvio;

	@JsonProperty("id_coda")
	private String idCoda;

	private String request;

	private String response;

	@JsonProperty("is_payment")
	private String isPayment;

	@JsonProperty("notice_number")
	private String noticeNumber;

	@JsonProperty("invio_tentato")
	private String invioTentato;

	@JsonProperty("data_invio")
	private LocalDateTime dataInvio;

	@JsonProperty("id_coda_old")
	private Long idCodaOld;

	@JsonProperty("tipo_batch")
	private String tipoBatch;

	@JsonProperty("tipo_desc_estesa")
	private String tipoDescEstesa;

	public Long getIdEsiti() {
		return idEsiti;
	}

	public void setIdEsiti(Long idEsiti) {
		this.idEsiti = idEsiti;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getTipoInvio() {
		return tipoInvio;
	}

	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCodInvio() {
		return codInvio;
	}

	public void setCodInvio(String codInvio) {
		this.codInvio = codInvio;
	}

	public String getIdCoda() {
		return idCoda;
	}

	public void setIdCoda(String idCoda) {
		this.idCoda = idCoda;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getIsPayment() {
		return isPayment;
	}

	public void setIsPayment(String isPayment) {
		this.isPayment = isPayment;
	}

	public String getNoticeNumber() {
		return noticeNumber;
	}

	public void setNoticeNumber(String noticeNumber) {
		this.noticeNumber = noticeNumber;
	}

	public String getInvioTentato() {
		return invioTentato;
	}

	public void setInvioTentato(String invioTentato) {
		this.invioTentato = invioTentato;
	}

	public LocalDateTime getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(LocalDateTime dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Long getIdCodaOld() {
		return idCodaOld;
	}

	public void setIdCodaOld(Long idCodaOld) {
		this.idCodaOld = idCodaOld;
	}

	public String getTipoBatch() {
		return tipoBatch;
	}

	public void setTipoBatch(String tipoBatch) {
		this.tipoBatch = tipoBatch;
	}

	public String getTipoDescEstesa() {
		return tipoDescEstesa;
	}

	public void setTipoDescEstesa(String tipoDescEstesa) {
		this.tipoDescEstesa = tipoDescEstesa;
	}

	@Override
	public String toString() {
		return "EsitiModel [idEsiti=" + idEsiti + ", codiceFiscale=" + codiceFiscale + ", tipoInvio=" + tipoInvio + ", dataInizio=" + dataInizio
				+ ", dataFine=" + dataFine + ", responseCode=" + responseCode + ", responseDesc=" + responseDesc + ", messageId=" + messageId
				+ ", note=" + note + ", codInvio=" + codInvio + ", idCoda=" + idCoda + ", request=" + request + ", response=" + response
				+ ", isPayment=" + isPayment + ", noticeNumber=" + noticeNumber + ", invioTentato=" + invioTentato + ", dataInvio=" + dataInvio
				+ ", idCodaOld=" + idCodaOld + ", tipoBatch=" + tipoBatch + ", tipoDescEstesa=" + tipoDescEstesa + "]";
	}
	
}
