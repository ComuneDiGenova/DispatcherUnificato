package it.appio.loader.repository.appio.prod;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.appio.loader.entity.appio.prod.CodaProdEntity;



/**
 * Query per la tabella IO_CODA - PROD
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface CodaProdRepository extends JpaRepository<CodaProdEntity, Long> {
	
	public List<CodaProdEntity> findByTipoMessaggio(String tipoMessaggio);
	
	public List<CodaProdEntity> findByDataInvioPrevisto(LocalDateTime dataInvioPrevisto);
	
	@Query(value = "SELECT ce FROM CodaComuneProdEntity ce WHERE ce.dataInvioPrevisto = :data AND ce.tipoMessaggio = :tipo")
	public List<CodaProdEntity> findByDataInvioPrevistoAndTipoMessaggio(@Param("data") LocalDateTime dataInvioPrevisto, @Param("tipo") String tipoMessaggio);
	
}
