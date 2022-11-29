package it.appio.loader.entity.scolastico.prod;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.Immutable;

/**
 * Mapping della vista VwAppIODisponibilitaIuv
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Immutable
public class AppIODispIuvEntity {

	@Id
	private Long id;

	private String codiceFiscale;

	private String codiceIuv;

	private String emissione;

	private Integer anno;

	private Long importoTotale;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceIuv() {
		return codiceIuv;
	}

	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}

	public String getEmissione() {
		return emissione;
	}

	public void setEmissione(String emissione) {
		this.emissione = emissione;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Long getImportoTotale() {
		return importoTotale;
	}

	public void setImportoTotale(Long importoTotale) {
		this.importoTotale = importoTotale;
	}

}
