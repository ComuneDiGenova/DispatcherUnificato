package it.appio.loader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.appio.loader.entity.appio.test.CodaTestEntity;
import it.appio.loader.repository.appio.test.CodaTestRepository;

/**
 * Business logic per il DB AppIO Test
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class DB_AppIO_Test_Service {

	private final CodaTestRepository codaRepo;

	@Autowired
	public DB_AppIO_Test_Service(CodaTestRepository codaRepo) {
		this.codaRepo = codaRepo;
	}

	public List<CodaTestEntity> findAllSources() {
		List<CodaTestEntity> result = codaRepo.findAll();

		return result;
	}

	public CodaTestEntity addRigaCoda(CodaTestEntity riga) {
		CodaTestEntity result = codaRepo.saveAndFlush(riga);

		return result;
	}

}
