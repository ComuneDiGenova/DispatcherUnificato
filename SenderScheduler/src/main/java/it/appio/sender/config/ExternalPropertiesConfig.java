package it.appio.sender.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

/**
 * File esterno di properties
 * 
 * @author Michele Montobbio
 * @version 1.0.5
 * @since 1.0.0
 */
@Configuration
public class ExternalPropertiesConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(Environment env) {
		
		PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
		
		// ubicazione del file di properties
		Path propertiesFile = Paths.get(System.getProperty("catalina.base"), "conf", "SenderScheduler.properties");
		
		props.setLocation(new FileSystemResource(propertiesFile));
		props.setFileEncoding("UTF-8");
		props.setIgnoreResourceNotFound(false);
		
		return props;
	}
}
