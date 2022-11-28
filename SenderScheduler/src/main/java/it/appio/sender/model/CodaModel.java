package it.appio.sender.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Entity corrispondente alla tabella IO_CODA
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonPropertyOrder(alphabetic = true)
public class CodaModel {

	@JsonProperty("id_coda")
	private Long idCoda;

	@JsonProperty("codice_fiscale")
	private String codiceFiscale;

	@JsonProperty("data_inserimento")
	private LocalDateTime dataIns;

	@JsonProperty("data_invio_previsto")
	private LocalDateTime dataInvioPrevisto;

	@JsonProperty("intervallo_orario_invio")
	private Integer intervalloOrarioInvio;

	@JsonProperty("tipo_messaggio")
	private String tipoMessaggio;

	@JsonProperty("payload_messaggio")
	private String payloadMessaggio;

	@JsonProperty("data_scadenza")
	private LocalDateTime dataScadenza;

	@JsonProperty("notice_number")
	private String noticeNumber;

	private String note;

	private Long amount;

	@JsonProperty("invalid_after_due_date")
	private String invalidAfterDueDate;

	@JsonProperty("data_promemoria_io")
	private String dataPromemoriaIo;

	public Long getIdCoda() {
		return idCoda;
	}

	public void setIdCoda(Long idCoda) {
		this.idCoda = idCoda;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public LocalDateTime getDataIns() {
		return dataIns;
	}

	public void setDataIns(LocalDateTime dataIns) {
		this.dataIns = dataIns;
	}

	public LocalDateTime getDataInvioPrevisto() {
		return dataInvioPrevisto;
	}

	public void setDataInvioPrevisto(LocalDateTime dataInvioPrevisto) {
		this.dataInvioPrevisto = dataInvioPrevisto;
	}

	public Integer getIntervalloOrarioInvio() {
		return intervalloOrarioInvio;
	}

	public void setIntervalloOrarioInvio(Integer intervalloOrarioInvio) {
		this.intervalloOrarioInvio = intervalloOrarioInvio;
	}

	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}

	public String getPayloadMessaggio() {
		return payloadMessaggio;
	}

	public void setPayloadMessaggio(String payloadMessaggio) {
		this.payloadMessaggio = payloadMessaggio;
	}

	public LocalDateTime getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(LocalDateTime dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getNoticeNumber() {
		return noticeNumber;
	}

	public void setNoticeNumber(String noticeNumber) {
		this.noticeNumber = noticeNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getInvalidAfterDueDate() {
		return invalidAfterDueDate;
	}

	public void setInvalidAfterDueDate(String invalidAfterDueDate) {
		this.invalidAfterDueDate = invalidAfterDueDate;
	}

	public String getDataPromemoriaIo() {
		return dataPromemoriaIo;
	}

	public void setDataPromemoriaIo(String dataPromemoriaIo) {
		this.dataPromemoriaIo = dataPromemoriaIo;
	}

	@Override
	public String toString() {
		return "CodaModel [idCoda=" + idCoda + ", codiceFiscale=" + codiceFiscale + ", dataIns=" + dataIns + ", dataInvioPrevisto="
				+ dataInvioPrevisto + ", intervalloOrarioInvio=" + intervalloOrarioInvio + ", tipoMessaggio=" + tipoMessaggio + ", payloadMessaggio="
				+ payloadMessaggio + ", dataScadenza=" + dataScadenza + ", noticeNumber=" + noticeNumber + ", note=" + note + ", amount=" + amount
				+ ", invalidAfterDueDate=" + invalidAfterDueDate + ", dataPromemoriaIo=" + dataPromemoriaIo + "]";
	}

}
