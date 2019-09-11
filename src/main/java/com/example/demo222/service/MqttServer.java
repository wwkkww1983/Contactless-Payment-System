package com.example.demo222.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttServer extends Thread{

    private static String topic1 = "authentication";
    private static String topic2 = "payment";
    private static String broker = "tcp://106.75.214.136:1883";
    private static String clientId = "cloud_client";
    public static int qos = 2;

    public static MemoryPersistence persistence = new MemoryPersistence();

    public void run(){
        MqttClient client = null;
        try {
            client = new MqttClient(broker, clientId, persistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        client.setCallback(new CallBack());
        try {
            client.connect(options);
            if (client.isConnected()) {
                System.out.println("已连接到broker");
            }
            client.subscribe(topic1, qos);
            client.subscribe(topic2, qos);
        } catch (MqttException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }

        System.out.println("已订阅主题");
    }

}
