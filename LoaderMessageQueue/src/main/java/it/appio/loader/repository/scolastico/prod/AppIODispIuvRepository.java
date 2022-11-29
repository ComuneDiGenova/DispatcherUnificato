package it.appio.loader.repository.scolastico.prod;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.appio.loader.entity.scolastico.prod.AppIODispIuvEntity;



/**
 * Query per la vista VwAppIODisponibilitaIuv
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface AppIODispIuvRepository extends JpaRepository<AppIODispIuvEntity, Long> {
	
	@Query(nativeQuery = true, value = "SELECT * FROM is_ristorazione.dbo.VwAppIODisponibilitaIuv")
	List<AppIODispIuvDTO> getAllViewRows();
	
	@Query(nativeQuery = true, value = "SELECT TOP 5 * FROM is_ristorazione.dbo.VwAppIODisponibilitaIuv")
	List<AppIODispIuvDTO> getViewRows();

}
