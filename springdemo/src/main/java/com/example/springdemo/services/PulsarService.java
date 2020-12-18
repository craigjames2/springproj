package com.example.springdemo.services;

import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

@Service
public class PulsarService {
    public static final String SUBSCRIPTION = "pulsar-to-local-subscription";
    public static final String SUBSCRIPTION1 = "pulsar-to-local-subscription-1";
    public static final String TOPIC = "my-topic";
    private static final String PULSAR_CLIENT_EXCEPTION_MSG = "There was a PulsarClientException";
    
    private PulsarClient client;
    private Producer<byte[]> producer;
    private Consumer<byte[]> consumer;
    
    public PulsarService() {
        try {
            client = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();
            producer = client.newProducer().topic(TOPIC).create();
            consumer = client.newConsumer().topic(TOPIC).subscriptionName(SUBSCRIPTION1).subscribe();
        } catch (PulsarClientException e) {
            System.out.println(PULSAR_CLIENT_EXCEPTION_MSG + " in constructor :(");
            System.out.println(e);
        }
    }
    
    public String postMessage(String msg) {
        byte[] content = msg.getBytes();
        MessageID msgId = producer.newMessage().value(content).send();
        return "Successful Post";
    }
    public void closeOut() {
        producer.close();
        consumer.close();
        client.close();
        System.out.println("Shut down");
        }
    public String getMessage() {
        while (waiting) {
							   Message msg = consumer.receive();
							   try {
								   System.out.printf("Message rec: %s", new String(msg.getData()));
								   //convert to pojo for fun, not useful at this point without successful import
								  //String pojoPrep = new String(msg.getData());
								   //MessagePacket pojoDemo = new MessagePacket(pojoPrep);
									return String(msg.getData());
					
								   
								   consumer.acknowledge(msg);
								   waiting = false;
							   } catch (Exception e) {
								   consumer.negativeAcknowledge(msg);
							   }
						   }
    }
        
    
