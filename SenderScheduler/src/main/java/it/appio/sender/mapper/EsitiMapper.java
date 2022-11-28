package it.appio.sender.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.appio.sender.model.EsitiModel;

/**
 * Interfaccia verso il DB (equivalente al Repository di JPA) per IO_ESITI
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface EsitiMapper {

	@Select("select * from IO_ESITI where id_coda = #{id}")
	@Results({ @Result(property = "idEsiti", column = "id_esiti"), @Result(property = "codiceFiscale", column = "codice_fiscale"),
			@Result(property = "tipoInvio", column = "tipo_invio"), @Result(property = "dataInizio", column = "data_inizio"),
			@Result(property = "dataFine", column = "data_fine"), @Result(property = "responseCode", column = "response_code"),
			@Result(property = "responseDesc", column = "response_desc"), @Result(property = "messageId", column = "message_id"),
			@Result(property = "note", column = "note"), @Result(property = "codInvio", column = "cod_invio"),
			@Result(property = "idCoda", column = "id_coda"), @Result(property = "request", column = "request"),
			@Result(property = "response", column = "response"), @Result(property = "isPayment", column = "is_payment"),
			@Result(property = "noticeNumber", column = "notice_number"), @Result(property = "invioTentato", column = "invio_tentato"),
			@Result(property = "dataInvio", column = "data_invio"), @Result(property = "idCodaOld", column = "id_coda_old"),
			@Result(property = "tipoBatch", column = "tipo_batch"), @Result(property = "tipoDescEstesa", column = "tipo_desc_estesa") })
	public List<EsitiModel> findByIdCoda(String id);

}
