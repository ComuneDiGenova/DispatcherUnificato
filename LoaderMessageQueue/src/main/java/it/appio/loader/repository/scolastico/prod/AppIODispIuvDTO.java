package it.appio.loader.repository.scolastico.prod;

/**
 * Interfaccia che permette di leggere le colonne della vista VwAppIODisponibilitaIuv.
 * Non avendo una tabella, ma una vista occorre fare una query nativa e questo metodo (PROJECTION) permette di mappare i campi della vista nell'Entity corretta.
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AppIODispIuvDTO {
	
	Long getId();
	
	String getCodiceFiscale();
	
	String getCodiceIuv();
	
	String getEmissione();
	
	Integer getAnno();
	
	Long getImportoTotale();

}
