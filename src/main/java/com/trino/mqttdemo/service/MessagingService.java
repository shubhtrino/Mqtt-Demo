package com.trino.mqttdemo.service;

import com.trino.mqttdemo.Constant;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

@Service
public class MessagingService  {

	public boolean flag=true;


	@Autowired
	private IMqttClient mqttClient;

	public void publish(final String topic, final String payload, int qos,
						boolean retained) throws MqttPersistenceException, MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(payload.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retained);

		mqttClient.publish(topic, mqttMessage);

		//mqttClient.publish(topic, payload.getBytes(), qos, retained);

		//mqttClient.disconnect();


	}

	public void subscribe (final String topic) throws MqttException,
			InterruptedException {

		mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
			System.out.println("Messages received for Topic ->" + tpic);
			System.out.println( new String(msg.getPayload()));
		});
	}

	public void generateRandomData(){

		System.out.println("Generating random data...");
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
		Future future = executor.submit(() -> {

			while (flag) {
				Random random = new Random();
				int objIndex = random.nextInt(3);

				String event = Constant.MAINTOPICS[objIndex];
				String completeEvent = Constant.baseTopic + event;

				try {
					publish(completeEvent, Constant.getRandomPayLoad(event), 0, true);

				} catch (MqttException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		executor.schedule(new Runnable(){
			public void run(){
				future.cancel(true);
			}
		}, 10000, TimeUnit.MILLISECONDS);
		executor.shutdown();

//		MessagingService m1 = new MessagingService();
//		Thread t1= new Thread(m1);
//		Thread t2= new Thread(m1);
//		Thread t3= new Thread(m1);
//
//		t1.start();
//		t2.start();
//		t3.start();


	}

}