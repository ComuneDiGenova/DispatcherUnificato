package it.appio.sender.model;

/**
 * Entity corrispondente alla tabella IO_INTERVALLO_INVIO
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public class IntervalloInvioModel {

	private Integer idIntervalloInvio;

	private Integer oraInizio;

	private Integer oraFine;

	public Integer getIdIntervalloInvio() {
		return idIntervalloInvio;
	}

	public void setIdIntervalloInvio(Integer idIntervalloInvio) {
		this.idIntervalloInvio = idIntervalloInvio;
	}

	public Integer getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(Integer oraInizio) {
		this.oraInizio = oraInizio;
	}

	public Integer getOraFine() {
		return oraFine;
	}

	public void setOraFine(Integer oraFine) {
		this.oraFine = oraFine;
	}

	@Override
	public String toString() {
		return "IntervalloInvioModel [idIntervalloInvio=" + idIntervalloInvio + ", oraInizio=" + oraInizio + ", oraFine=" + oraFine + "]";
	}

}
