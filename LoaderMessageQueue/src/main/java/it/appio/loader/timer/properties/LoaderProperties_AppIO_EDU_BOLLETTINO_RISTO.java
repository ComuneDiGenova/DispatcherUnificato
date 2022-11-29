package it.appio.loader.timer.properties;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO {

	// Properties per il servizio: EDU_BOLLETTINO_RISTO

	@Value("${loader.edu.bollettino.risto.ambiente.target}")
	private String AMBIENTE_TARGET;

	@Value("${loader.edu.bollettino.risto.tipo.messaggio}")
	private String TIPO_MESSAGGIO;

	@Value("${loader.edu.bollettino.risto.data.invio.base}")
	private String DATA_INVIO_BASE_STRING;

	@Value("${loader.edu.bollettino.risto.invii.al.giorno}")
	private int INVII_AL_GIORNO;

	@Value("${loader.edu.bollettino.risto.orario}")
	private int ORARIO;

	@Value("${loader.edu.bollettino.risto.messaggio.base}")
	private String MESSAGGIO_BASE;

	@Value("${loader.edu.bollettino.risto.data.scadenza}")
	private String DATA_SCADENZA_STRING;

	@Value("${loader.edu.bollettino.risto.note}")
	private String NOTE;

	@Value("${loader.edu.bollettino.risto.invalid}")
	private String INVALID;

	@Value("${loader.edu.bollettino.risto.promemoria}")
	private String PROMEMORIA;

	@Value("${loader.edu.bollettino.risto.top}")
	private String TOP;

	// Conversione in oggetti
	private LocalDateTime DATA_INVIO_BASE;
	private LocalDateTime DATA_SCADENZA;

	@PostConstruct
	public void init() {
		this.DATA_INVIO_BASE = LocalDate.parse(DATA_INVIO_BASE_STRING).atStartOfDay();
		if (DATA_SCADENZA_STRING != null)
			this.DATA_SCADENZA = LocalDateTime.parse(DATA_SCADENZA_STRING);
	}

	public String getAMBIENTE_TARGET() {
		return AMBIENTE_TARGET;
	}

	public void setAMBIENTE_TARGET(String aMBIENTE_TARGET) {
		AMBIENTE_TARGET = aMBIENTE_TARGET;
	}

	public String getTIPO_MESSAGGIO() {
		return TIPO_MESSAGGIO;
	}

	public void setTIPO_MESSAGGIO(String tIPO_MESSAGGIO) {
		TIPO_MESSAGGIO = tIPO_MESSAGGIO;
	}

	public String getDATA_INVIO_BASE_STRING() {
		return DATA_INVIO_BASE_STRING;
	}

	public void setDATA_INVIO_BASE_STRING(String dATA_INVIO_BASE_STRING) {
		DATA_INVIO_BASE_STRING = dATA_INVIO_BASE_STRING;
	}

	public int getINVII_AL_GIORNO() {
		return INVII_AL_GIORNO;
	}

	public void setINVII_AL_GIORNO(int iNVII_AL_GIORNO) {
		INVII_AL_GIORNO = iNVII_AL_GIORNO;
	}

	public int getORARIO() {
		return ORARIO;
	}

	public void setORARIO(int oRARIO) {
		ORARIO = oRARIO;
	}

	public String getMESSAGGIO_BASE() {
		return MESSAGGIO_BASE;
	}

	public void setMESSAGGIO_BASE(String mESSAGGIO_BASE) {
		MESSAGGIO_BASE = mESSAGGIO_BASE;
	}

	public String getDATA_SCADENZA_STRING() {
		return DATA_SCADENZA_STRING;
	}

	public void setDATA_SCADENZA_STRING(String dATA_SCADENZA_STRING) {
		DATA_SCADENZA_STRING = dATA_SCADENZA_STRING;
	}

	public String getNOTE() {
		return NOTE;
	}

	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}

	public String getINVALID() {
		return INVALID;
	}

	public void setINVALID(String iNVALID) {
		INVALID = iNVALID;
	}

	public String getPROMEMORIA() {
		return PROMEMORIA;
	}

	public void setPROMEMORIA(String pROMEMORIA) {
		PROMEMORIA = pROMEMORIA;
	}

	public String getTOP() {
		return TOP;
	}

	public void setTOP(String tOP) {
		TOP = tOP;
	}

	public LocalDateTime getDATA_INVIO_BASE() {
		return DATA_INVIO_BASE;
	}

	public void setDATA_INVIO_BASE(LocalDateTime dATA_INVIO_BASE) {
		DATA_INVIO_BASE = dATA_INVIO_BASE;
	}

	public LocalDateTime getDATA_SCADENZA() {
		return DATA_SCADENZA;
	}

	public void setDATA_SCADENZA(LocalDateTime dATA_SCADENZA) {
		DATA_SCADENZA = dATA_SCADENZA;
	}

	@Override
	public String toString() {
		return "LoaderProperties_AppIO_EDU_BOLLETTINO_RISTO [AMBIENTE_TARGET=" + AMBIENTE_TARGET + ", TIPO_MESSAGGIO=" + TIPO_MESSAGGIO
				+ ", DATA_INVIO_BASE_STRING=" + DATA_INVIO_BASE_STRING + ", INVII_AL_GIORNO=" + INVII_AL_GIORNO + ", ORARIO=" + ORARIO
				+ ", MESSAGGIO_BASE=" + MESSAGGIO_BASE + ", DATA_SCADENZA_STRING=" + DATA_SCADENZA_STRING + ", NOTE=" + NOTE + ", INVALID=" + INVALID
				+ ", PROMEMORIA=" + PROMEMORIA + ", TOP=" + TOP + ", DATA_INVIO_BASE=" + DATA_INVIO_BASE + ", DATA_SCADENZA=" + DATA_SCADENZA + "]";
	}
	
}
