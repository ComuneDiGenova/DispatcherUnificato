package it.appio.sender.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.appio.sender.model.ConfigurazioneModel;

/**
 * Interfaccia verso il DB (equivalente al Repository di JPA) per IO_CONFIGURAZIONE
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface ConfigurazioneMapper {

	// findAll
	@Select("select * from IO_CONFIGURAZIONE")
	@Results({ @Result(property = "tipoBatch", column = "tipo_batch"), @Result(property = "token", column = "token"),
			@Result(property = "payloadType", column = "payload_type"), @Result(property = "subject", column = "subject"),
			@Result(property = "timeToLive", column = "time_to_live"), @Result(property = "ripetibile", column = "ripetibile"),
			@Result(property = "messaggioBase", column = "messaggio_base"), @Result(property = "note", column = "note"),
			@Result(property = "payloadMex", column = "payload_mex"), @Result(property = "idStatoRec", column = "id_stato_rec"),
			@Result(property = "tipoDescEstesa", column = "tipo_desc_estesa") })
	List<ConfigurazioneModel> findAll();

	// findByTipoBatch
	@Select("select * from IO_CONFIGURAZIONE where tipo_batch = #{tipo}")
	@Results({ @Result(property = "tipoBatch", column = "tipo_batch"), @Result(property = "token", column = "token"),
			@Result(property = "payloadType", column = "payload_type"), @Result(property = "subject", column = "subject"),
			@Result(property = "timeToLive", column = "time_to_live"), @Result(property = "ripetibile", column = "ripetibile"),
			@Result(property = "messaggioBase", column = "messaggio_base"), @Result(property = "note", column = "note"),
			@Result(property = "payloadMex", column = "payload_mex"), @Result(property = "idStatoRec", column = "id_stato_rec"),
			@Result(property = "tipoDescEstesa", column = "tipo_desc_estesa") })
	Optional<ConfigurazioneModel> findByTipoBatch(String tipo);

}
