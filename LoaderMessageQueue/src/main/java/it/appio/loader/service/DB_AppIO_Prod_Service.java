package it.appio.loader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.appio.loader.entity.appio.prod.CodaProdEntity;
import it.appio.loader.repository.appio.prod.CodaProdRepository;

/**
 * Business logic per il DB AppIO Prod
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class DB_AppIO_Prod_Service {

	private final CodaProdRepository codaRepo;

	@Autowired
	public DB_AppIO_Prod_Service(CodaProdRepository codaRepo) {
		this.codaRepo = codaRepo;
	}

	// Sezione IO_CODA
	public List<CodaProdEntity> findAllSources() {
		List<CodaProdEntity> result = codaRepo.findAll();

		return result;
	}

	public CodaProdEntity addRigaCoda(CodaProdEntity riga) {
		CodaProdEntity result = codaRepo.saveAndFlush(riga);

		return result;
	}

}
