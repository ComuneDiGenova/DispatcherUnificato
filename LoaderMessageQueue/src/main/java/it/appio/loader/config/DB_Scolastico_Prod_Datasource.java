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
 * Datasource per la connessione al DB Scolastico - PROD
 * 
 * @author Michele Montobbio
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = {
		"it.appio.loader.repository.scolastico.prod" }, entityManagerFactoryRef = "dbScolProdEntityManager", transactionManagerRef = "dbScolProdTransactionManager")
public class DB_Scolastico_Prod_Datasource {

	private final DB_PropertiesConfig dbPropertiesConfig;

	@Autowired
	public DB_Scolastico_Prod_Datasource(DB_PropertiesConfig dbPropertiesConfig) {
		this.dbPropertiesConfig = dbPropertiesConfig;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean dbScolProdEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dbScolProdDatasource());
		em.setPackagesToScan(new String[] { "it.appio.loader.entity.scolastico.prod" });
		em.setPersistenceUnitName("dbScolProdEntityManager");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", dbPropertiesConfig.getSCOL_PROD_DIALECT());
		properties.put("hibernate.show-sql", dbPropertiesConfig.getSCOL_PROD_SHOW());

		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public DataSource dbScolProdDatasource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(dbPropertiesConfig.getSCOL_PROD_DRIVER());
		ds.setUrl(dbPropertiesConfig.getSCOL_PROD_URL());
		ds.setUsername(dbPropertiesConfig.getSCOL_PROD_USER());
		ds.setPassword(dbPropertiesConfig.getSCOL_PROD_PWD());

		return ds;
	}

	@Bean
	public PlatformTransactionManager dbScolProdTransactionManager() {

		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(dbScolProdEntityManager().getObject());

		return tm;
	}

}
