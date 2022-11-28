package it.appio.sender.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.appio.sender.model.IntervalloInvioModel;

/**
 * Interfaccia verso il DB (equivalente al Repository di JPA) per IO_INTERVALLO_INVIO
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface IntervalloInvioMapper {

	// findByTipoBatch
	@Select("select * from IO_INTERVALLO_INVIO where id_intervallo_invio = #{id}")
	@Results({ @Result(property = "idIntervalloInvio", column = "id_intervallo_invio"), @Result(property = "oraInizio", column = "ora_inizio"),
			@Result(property = "oraFine", column = "ora_fine") })
	Optional<IntervalloInvioModel> findById(Integer id);

}
