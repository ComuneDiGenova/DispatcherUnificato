package it.appio.sender.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import it.appio.sender.model.CodaModel;

/**
 * Interfaccia verso il DB (equivalente al Repository di JPA) per IO_CODA
 * 
 * @author Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Mapper
public interface CodaMapper {

	@Select("select * from IO_CODA where tipo_messaggio = #{tipoMessaggio} and data_invio_previsto = #{dataInvioPrevisto}")
	@Results({ @Result(property = "idCoda", column = "id_coda"), @Result(property = "codiceFiscale", column = "codice_fiscale"),
			@Result(property = "dataIns", column = "data_inserimento"), @Result(property = "dataInvioPrevisto", column = "data_invio_previsto"),
			@Result(property = "intervalloOrarioInvio", column = "intervallo_orario_invio"),
			@Result(property = "tipoMessaggio", column = "tipo_messaggio"), @Result(property = "payloadMessaggio", column = "payload_messaggio"),
			@Result(property = "dataScadenza", column = "data_scadenza"), @Result(property = "noticeNumber", column = "notice_number"),
			@Result(property = "note", column = "note"), @Result(property = "amount", column = "amount"),
			@Result(property = "invalidAfterDueDate", column = "invalid_after_due_date"),
			@Result(property = "dataPromemoriaIo", column = "data_promemoria_io") })
	public List<CodaModel> findByDataInvioPrevistoAndTipoMessaggio(String tipoMessaggio, LocalDateTime dataInvioPrevisto);

}
