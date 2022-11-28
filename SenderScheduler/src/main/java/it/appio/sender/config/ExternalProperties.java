package it.appio.sender.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Properties esterne
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ExternalProperties {

	@Value("${batch.ripetibile.errore.delta}")
	private Integer DELTA_RICERCA_ERRORE;

	@Value("${batch.wso2.base.url}")
	private String WSO2_BASE_URL;

	@Value("${batch.wso2.uri.path}")
	private String WSO2_URI_PATH;

	@Value("${batch.wso2.bearer.token}")
	private String WSO2_BEARER;
	
	@Value("${app.version}")
	private String APP_VERSION;

	public Integer getDELTA_RICERCA_ERRORE() {
		return DELTA_RICERCA_ERRORE;
	}

	public String getWSO2_BASE_URL() {
		return WSO2_BASE_URL;
	}

	public String getWSO2_URI_PATH() {
		return WSO2_URI_PATH;
	}

	public String getWSO2_BEARER() {
		return WSO2_BEARER;
	}
	
	public String getAPP_VERSION() {
		return APP_VERSION;
	}

	@Override
	public String toString() {
		return "ExternalProperties [APP_VERSION=" + APP_VERSION + ", DELTA_RICERCA_ERRORE=" + DELTA_RICERCA_ERRORE + ", WSO2_BASE_URL=" + WSO2_BASE_URL + ", WSO2_URI_PATH="
				+ WSO2_URI_PATH + "]";
	}
	
}
