package it.appio.loader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.appio.loader.repository.scolastico.prod.AppIODispIuvDTO;
import it.appio.loader.repository.scolastico.prod.AppIODispIuvRepository;

/**
 * Business logic per il DB Scolastico Prod
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class DB_Scol_Prod_Service {

	private final AppIODispIuvRepository appIODispIuvRepo;

	@Autowired
	public DB_Scol_Prod_Service(AppIODispIuvRepository appIODispIuvRepo) {
		this.appIODispIuvRepo = appIODispIuvRepo;
	}

	// legge la vista VwAppIODisponibilitaIuv per intero
	public List<AppIODispIuvDTO> findAllSources_DispIuv() {
		List<AppIODispIuvDTO> result = appIODispIuvRepo.getAllViewRows();

		return result;
	}

	// legge la vista VwAppIODisponibilitaIuv troncata
	public List<AppIODispIuvDTO> findSources_DispIuv() {
		List<AppIODispIuvDTO> result = appIODispIuvRepo.getViewRows();

		return result;
	}

}
