package it.appio.loader.entity.appio.test;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Mapping della tabella IO_CODA per il DB del Comune - TEST
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Table(name = "IO_CODA")
public class CodaTestEntity {

	@Id
	@Column(name = "ID_CODA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOM_SEQ")
	@SequenceGenerator(sequenceName = "IO_CODA_SEQ", allocationSize = 1, name = "CUSTOM_SEQ")
	private Long idCoda;

	@NotBlank
	@Column(name = "TIPO_MESSAGGIO")
	private String tipoMessaggio;

	@NotBlank
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name = "DATA_INS")
	private LocalDateTime dataIns;

	@Column(name = "DATA_INVIO_PREVISTO")
	private LocalDateTime dataInvioPrevisto;

	@Column(name = "INTERVALLO_ORARIO_INVIO")
	private Integer intervalloOrarioInvio;

	@NotBlank
	@Column(name = "PAYLOAD_MESSAGGIO")
	private String payloadMessaggio;

	@Column(name = "DATA_SCADENZA")
	private LocalDateTime dataScadenza;

	@Column(name = "NOTICE_NUMBER")
	private String noticeNumber;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "AMOUNT")
	private Long amount;

	@Column(name = "INVALID_AFTER_DUE_DATE")
	private String invalidAfterDueDate;

	@Column(name = "DATA_PROMEMORIA_IO")
	private String dataPromemoriaIo;

	public Long getIdCoda() {
		return idCoda;
	}

	public void setIdCoda(Long idCoda) {
		this.idCoda = idCoda;
	}

	public String getTipoMessaggio() {
		return tipoMessaggio;
	}

	public void setTipoMessaggio(String tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
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

}
