package com.trino.mqttdemo;

import com.trino.mqttdemo.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MqttDemoApplication {

	@Autowired
	private MessagingService messagingService;

	public static void main(String[] args) {
		SpringApplication.run(MqttDemoApplication.class, args);
	}

	@PostConstruct
	public void subscriber(){

		String base =Constant.baseTopic;
		final String safetyTopic = base+"safety";
		final String driverWellBeingTopic = base+"driverWellBeing";
		final String telematicsTopic = base+"telematics";


		try {
			messagingService.subscribe(safetyTopic);
			messagingService.subscribe(driverWellBeingTopic);
			messagingService.subscribe(telematicsTopic);

		} catch (MqttException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
