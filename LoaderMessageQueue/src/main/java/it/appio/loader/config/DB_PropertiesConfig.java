package it.appio.loader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * File di properties per le connessioni ai DB
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public final class DB_PropertiesConfig {

	// --------------------------------------------------------------------------
	// AppIO Comune - TEST
	// --------------------------------------------------------------------------

	@Value("${db.appio.test.driver.class.name}")
	private String APPIO_TEST_DRIVER;

	@Value("${db.appio.test.hibernate.dialect}")
	private String APPIO_TEST_DIALECT;

	@Value("${db.appio.test.show-sql}")
	private String APPIO_TEST_SHOW;

	@Value("${db.appio.test.datasource.url}")
	private String APPIO_TEST_URL;

	@Value("${db.appio.test.datasource.username}")
	private String APPIO_TEST_USER;

	@Value("${db.appio.test.datasource.password}")
	private String APPIO_TEST_PWD;

	// --------------------------------------------------------------------------
	// AppIO Comune - PROD
	// --------------------------------------------------------------------------

	@Value("${db.appio.prod.driver.class.name}")
	private String APPIO_PROD_DRIVER;

	@Value("${db.appio.prod.hibernate.dialect}")
	private String APPIO_PROD_DIALECT;

	@Value("${db.appio.prod.show-sql}")
	private String APPIO_PROD_SHOW;

	@Value("${db.appio.prod.datasource.url}")
	private String APPIO_PROD_URL;

	@Value("${db.appio.prod.datasource.username}")
	private String APPIO_PROD_USER;

	@Value("${db.appio.prod.datasource.password}")
	private String APPIO_PROD_PWD;

	// --------------------------------------------------------------------------
	// Scolastico - PROD
	// --------------------------------------------------------------------------

	@Value("${db.scol.prod.driver.class.name}")
	private String SCOL_PROD_DRIVER;

	@Value("${db.scol.prod.hibernate.dialect}")
	private String SCOL_PROD_DIALECT;

	@Value("${db.scol.prod.show-sql}")
	private String SCOL_PROD_SHOW;

	@Value("${db.scol.prod.datasource.url}")
	private String SCOL_PROD_URL;

	@Value("${db.scol.prod.datasource.username}")
	private String SCOL_PROD_USER;

	@Value("${db.scol.prod.datasource.password}")
	private String SCOL_PROD_PWD;

	// --------------------------------------------------------------------------
	// GETTERS&SETTERS
	// --------------------------------------------------------------------------

	public String getAPPIO_TEST_DRIVER() {
		return APPIO_TEST_DRIVER;
	}

	public void setAPPIO_TEST_DRIVER(String aPPIO_TEST_DRIVER) {
		APPIO_TEST_DRIVER = aPPIO_TEST_DRIVER;
	}

	public String getAPPIO_TEST_DIALECT() {
		return APPIO_TEST_DIALECT;
	}

	public void setAPPIO_TEST_DIALECT(String aPPIO_TEST_DIALECT) {
		APPIO_TEST_DIALECT = aPPIO_TEST_DIALECT;
	}

	public String getAPPIO_TEST_SHOW() {
		return APPIO_TEST_SHOW;
	}

	public void setAPPIO_TEST_SHOW(String aPPIO_TEST_SHOW) {
		APPIO_TEST_SHOW = aPPIO_TEST_SHOW;
	}

	public String getAPPIO_TEST_URL() {
		return APPIO_TEST_URL;
	}

	public void setAPPIO_TEST_URL(String aPPIO_TEST_URL) {
		APPIO_TEST_URL = aPPIO_TEST_URL;
	}

	public String getAPPIO_TEST_USER() {
		return APPIO_TEST_USER;
	}

	public void setAPPIO_TEST_USER(String aPPIO_TEST_USER) {
		APPIO_TEST_USER = aPPIO_TEST_USER;
	}

	public String getAPPIO_TEST_PWD() {
		return APPIO_TEST_PWD;
	}

	public void setAPPIO_TEST_PWD(String aPPIO_TEST_PWD) {
		APPIO_TEST_PWD = aPPIO_TEST_PWD;
	}

	public String getAPPIO_PROD_DRIVER() {
		return APPIO_PROD_DRIVER;
	}

	public void setAPPIO_PROD_DRIVER(String aPPIO_PROD_DRIVER) {
		APPIO_PROD_DRIVER = aPPIO_PROD_DRIVER;
	}

	public String getAPPIO_PROD_DIALECT() {
		return APPIO_PROD_DIALECT;
	}

	public void setAPPIO_PROD_DIALECT(String aPPIO_PROD_DIALECT) {
		APPIO_PROD_DIALECT = aPPIO_PROD_DIALECT;
	}

	public String getAPPIO_PROD_SHOW() {
		return APPIO_PROD_SHOW;
	}

	public void setAPPIO_PROD_SHOW(String aPPIO_PROD_SHOW) {
		APPIO_PROD_SHOW = aPPIO_PROD_SHOW;
	}

	public String getAPPIO_PROD_URL() {
		return APPIO_PROD_URL;
	}

	public void setAPPIO_PROD_URL(String aPPIO_PROD_URL) {
		APPIO_PROD_URL = aPPIO_PROD_URL;
	}

	public String getAPPIO_PROD_USER() {
		return APPIO_PROD_USER;
	}

	public void setAPPIO_PROD_USER(String aPPIO_PROD_USER) {
		APPIO_PROD_USER = aPPIO_PROD_USER;
	}

	public String getAPPIO_PROD_PWD() {
		return APPIO_PROD_PWD;
	}

	public void setAPPIO_PROD_PWD(String aPPIO_PROD_PWD) {
		APPIO_PROD_PWD = aPPIO_PROD_PWD;
	}

	public String getSCOL_PROD_DRIVER() {
		return SCOL_PROD_DRIVER;
	}

	public void setSCOL_PROD_DRIVER(String sCOL_PROD_DRIVER) {
		SCOL_PROD_DRIVER = sCOL_PROD_DRIVER;
	}

	public String getSCOL_PROD_DIALECT() {
		return SCOL_PROD_DIALECT;
	}

	public void setSCOL_PROD_DIALECT(String sCOL_PROD_DIALECT) {
		SCOL_PROD_DIALECT = sCOL_PROD_DIALECT;
	}

	public String getSCOL_PROD_SHOW() {
		return SCOL_PROD_SHOW;
	}

	public void setSCOL_PROD_SHOW(String sCOL_PROD_SHOW) {
		SCOL_PROD_SHOW = sCOL_PROD_SHOW;
	}

	public String getSCOL_PROD_URL() {
		return SCOL_PROD_URL;
	}

	public void setSCOL_PROD_URL(String sCOL_PROD_URL) {
		SCOL_PROD_URL = sCOL_PROD_URL;
	}

	public String getSCOL_PROD_USER() {
		return SCOL_PROD_USER;
	}

	public void setSCOL_PROD_USER(String sCOL_PROD_USER) {
		SCOL_PROD_USER = sCOL_PROD_USER;
	}

	public String getSCOL_PROD_PWD() {
		return SCOL_PROD_PWD;
	}

	public void setSCOL_PROD_PWD(String sCOL_PROD_PWD) {
		SCOL_PROD_PWD = sCOL_PROD_PWD;
	}

}
