package com.example.springdemo.services;

import java.nio.charset.StandardCharsets;

import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.TypedMessageBuilder;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.ClientBuilder;

import java.util.stream.IntStream;
import org.springframework.stereotype.Service;


@Service
public class PulsarService {
    public static final String SUBSCRIPTION = "pulsar-to-local-subscription";
    public static final String SUBSCRIPTION1 = "pulsar-to-local-subscription-1";
    public static final String TOPIC = "my-topic";
    private static final String PULSAR_CLIENT_EXCEPTION_MSG = "There was a PulsarClientException";
    
    public PulsarClient client;
    public Producer<byte[]> producer;
    public Consumer<byte[]> consumer;
    
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
        MessageId msgId = producer.newMessage().value(content).send();
        return "Successful Post";
    }
    public void closeOut() {
        producer.close();
        consumer.close();
        client.close();
        System.out.println("Shut down");
        }
    public String getMessage() {
	   boolean waiting = true;
	   String pojoPrep = "";
        while (waiting) {
							   Message msg = consumer.receive();
							   try {
								   System.out.printf("Message rec: %s", new String(msg.getData()));
								   //convert to pojo for fun, not useful at this point without successful import
								  pojoPrep = new String(msg.getData());
								   //MessagePacket pojoDemo = new MessagePacket(pojoPrep);
									
					
								   
								   consumer.acknowledge(msg);
								   waiting = false;
								   
							   } catch (Exception e) {
								   consumer.negativeAcknowledge(msg);
								   pojoPrep = "error in consume fn";
							   }
						   }
	    return pojoPrep;
    }
}
    
