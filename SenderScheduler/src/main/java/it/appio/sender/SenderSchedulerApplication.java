package it.appio.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "it.appio.sender.invio.client")
public class SenderSchedulerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true"); // flag auto restart ad ogni modifica (spring-boot-devtools)
		SpringApplication.run(SenderSchedulerApplication.class, args);
	}

	// Serve per creare il war di Tomcat
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SenderSchedulerApplication.class);
	}

}
