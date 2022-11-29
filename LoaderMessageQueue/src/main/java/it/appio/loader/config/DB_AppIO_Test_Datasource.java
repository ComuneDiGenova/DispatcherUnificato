package it.appio.loader.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Datasource per la connessione al DB Appio Comune - TEST
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"it.appio.loader.repository.appio.test" }, entityManagerFactoryRef = "dbAppIOTestEntityManager", transactionManagerRef = "dbAppIOTestTransactionManager")
public class DB_AppIO_Test_Datasource {

	private final DB_PropertiesConfig dbPropertiesConfig;

	@Autowired
	public DB_AppIO_Test_Datasource(DB_PropertiesConfig dbPropertiesConfig) {
		this.dbPropertiesConfig = dbPropertiesConfig;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean dbAppIOTestEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dbAppIOTestDatasource());
		em.setPackagesToScan(new String[] { "it.appio.loader.entity.appio.test" });
		em.setPersistenceUnitName("dbAppIOTestEntityManager");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", dbPropertiesConfig.getAPPIO_TEST_DIALECT());
		properties.put("hibernate.show-sql", dbPropertiesConfig.getAPPIO_TEST_SHOW());

		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public DataSource dbAppIOTestDatasource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(dbPropertiesConfig.getAPPIO_TEST_DRIVER());
		ds.setUrl(dbPropertiesConfig.getAPPIO_TEST_URL());
		ds.setUsername(dbPropertiesConfig.getAPPIO_TEST_USER());
		ds.setPassword(dbPropertiesConfig.getAPPIO_TEST_PWD());

		return ds;
	}

	@Bean
	public PlatformTransactionManager dbAppIOTestTransactionManager() {

		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(dbAppIOTestEntityManager().getObject());

		return tm;
	}

}
