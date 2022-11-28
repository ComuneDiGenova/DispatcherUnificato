package it.appio.sender.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Entity corrispondente alla tabella IO_CONFIGURAZIONE
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonPropertyOrder(alphabetic = true)
public class ConfigurazioneModel {

	@JsonProperty("tipo_batch")
	private String tipoBatch;

	private String token;

	@JsonProperty("payload_type")
	private String payloadType;

	private String subject;

	@JsonProperty("time_to_live")
	private Integer timeToLive;

	private String ripetibile;

	@JsonProperty("messaggio_base")
	private String messaggioBase;

	private String note;

	@JsonProperty("payload_mex")
	private String payloadMex;

	@JsonProperty("id_stato_rec")
	private Long idStatoRec;

	@JsonProperty("tipo_desc_estesa")
	private String tipoDescEstesa;

	public String getTipoBatch() {
		return tipoBatch;
	}

	public void setTipoBatch(String tipoBatch) {
		this.tipoBatch = tipoBatch;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPayloadType() {
		return payloadType;
	}

	public void setPayloadType(String payloadType) {
		this.payloadType = payloadType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}

	public String getRipetibile() {
		return ripetibile;
	}

	public void setRipetibile(String ripetibile) {
		this.ripetibile = ripetibile;
	}

	public String getMessaggioBase() {
		return messaggioBase;
	}

	public void setMessaggioBase(String messaggioBase) {
		this.messaggioBase = messaggioBase;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPayloadMex() {
		return payloadMex;
	}

	public void setPayloadMex(String payloadMex) {
		this.payloadMex = payloadMex;
	}

	public Long getIdStatoRec() {
		return idStatoRec;
	}

	public void setIdStatoRec(Long idStatoRec) {
		this.idStatoRec = idStatoRec;
	}

	public String getTipoDescEstesa() {
		return tipoDescEstesa;
	}

	public void setTipoDescEstesa(String tipoDescEstesa) {
		this.tipoDescEstesa = tipoDescEstesa;
	}

	@Override
	public String toString() {
		return "ConfigurazioneModel [tipoBatch=" + tipoBatch + ", token=" + token + ", payloadType=" + payloadType + ", subject=" + subject
				+ ", timeToLive=" + timeToLive + ", ripetibile=" + ripetibile + ", messaggioBase=" + messaggioBase + ", note=" + note
				+ ", payloadMex=" + payloadMex + ", idStatoRec=" + idStatoRec + ", tipoDescEstesa=" + tipoDescEstesa + "]";
	}

}
