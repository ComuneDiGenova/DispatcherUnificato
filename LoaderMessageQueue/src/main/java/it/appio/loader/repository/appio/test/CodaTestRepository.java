package it.appio.loader.repository.appio.test;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.appio.loader.entity.appio.test.CodaTestEntity;



/**
 * Query per la tabella IO_CODA - TEST
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface CodaTestRepository extends JpaRepository<CodaTestEntity, Long> {
	
	public List<CodaTestEntity> findByTipoMessaggio(String tipoMessaggio);
	
	public List<CodaTestEntity> findByDataInvioPrevisto(LocalDateTime dataInvioPrevisto);
	
	@Query(value = "SELECT ce FROM CodaComuneTestEntity ce WHERE ce.dataInvioPrevisto = :data AND ce.tipoMessaggio = :tipo")
	public List<CodaTestEntity> findByDataInvioPrevistoAndTipoMessaggio(@Param("data") LocalDateTime dataInvioPrevisto, @Param("tipo") String tipoMessaggio);
	
}
