package com.trino.mqttdemo.controller;

import com.trino.mqttdemo.Constant;
import com.trino.mqttdemo.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AppController {

    @Autowired
    private MessagingService messagingService;

    @GetMapping("/")
    public String healthCheck() {
        return "Welcome to Connected Cars.";
    }

    @GetMapping("/topic/{event}")
    public void publishMessage(@PathVariable("event") String event ) {

        String completeEvent = Constant.baseTopic+event;
        System.out.println("Publishing event...");

		try {
			messagingService.publish(completeEvent, Constant.getRandomPayLoad(event), 0, true);

		} catch (MqttPersistenceException | IOException | JSONException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }
}
