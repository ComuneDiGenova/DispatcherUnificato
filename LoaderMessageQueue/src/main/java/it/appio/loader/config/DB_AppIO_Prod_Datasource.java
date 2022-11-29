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
 * Datasource per la connessione al DB Appio Comune - PROD
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"it.appio.loader.repository.appio.prod" }, entityManagerFactoryRef = "dbAppIOProdEntityManager", transactionManagerRef = "dbAppIOProdTransactionManager")
public class DB_AppIO_Prod_Datasource {

	private final DB_PropertiesConfig dbPropertiesConfig;

	@Autowired
	public DB_AppIO_Prod_Datasource(DB_PropertiesConfig dbPropertiesConfig) {
		this.dbPropertiesConfig = dbPropertiesConfig;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean dbAppIOProdEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dbAppIOProdDatasource());
		em.setPackagesToScan(new String[] { "it.appio.loader.entity.appio.prod" });
		em.setPersistenceUnitName("dbAppIOProdEntityManager");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", dbPropertiesConfig.getAPPIO_PROD_DIALECT());
		properties.put("hibernate.show-sql", dbPropertiesConfig.getAPPIO_PROD_SHOW());

		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public DataSource dbAppIOProdDatasource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(dbPropertiesConfig.getAPPIO_PROD_DRIVER());
		ds.setUrl(dbPropertiesConfig.getAPPIO_PROD_URL());
		ds.setUsername(dbPropertiesConfig.getAPPIO_PROD_USER());
		ds.setPassword(dbPropertiesConfig.getAPPIO_PROD_PWD());

		return ds;
	}

	@Bean
	public PlatformTransactionManager dbAppIOProdTransactionManager() {

		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(dbAppIOProdEntityManager().getObject());

		return tm;
	}

}
