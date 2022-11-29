package it.appio.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import it.appio.loader.service.LoaderService;

@SpringBootApplication
public class LoaderMessageQueueApplication {

	private static final Logger LOG = LoggerFactory.getLogger(LoaderMessageQueueApplication.class);

	@Autowired
	private LoaderService loaderService;

	@Value("${app.version}")
	private String appVersion;

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true"); // flag auto restart ad ogni modifica (spring-boot-devtools)
		SpringApplication.run(LoaderMessageQueueApplication.class, args);
	}

	// Serve per creare il war di Tomcat
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(LoaderMessageQueueApplication.class);
	}

	// Operazioni allo startup
	@EventListener(ApplicationReadyEvent.class)
	public void startup() {
		LOG.info(String.format("APP_VERSION: %s", appVersion));

		// Crea il demone che periodicamente legge la lista dei timer attivi e la salva
		loaderService.createDemon();

	}

}
