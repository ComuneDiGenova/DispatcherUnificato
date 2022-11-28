package it.appio.sender.invio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import it.appio.sender.invio.consumer.AppIoConsumer;
import it.appio.sender.invio.producer.AppIoProducer;

@FeignClient(name = "backendWSO2", url = "${batch.wso2.base.url}")
public interface WSO2FeignClient {

	@PostMapping(value = "/notifica")
	ResponseEntity<AppIoProducer> sendMessage(@RequestHeader HttpHeaders headers, @RequestBody AppIoConsumer body);

}
