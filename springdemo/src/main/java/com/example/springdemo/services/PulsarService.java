package com.example.springdemo.services;

import com.example.springdemo.models.MessagePacket;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

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
		System.out.println(" service setup - client ");
            producer = client.newProducer().topic(TOPIC).create();
		System.out.println(" service setup - producer ");
            consumer = client.newConsumer().topic(TOPIC).subscriptionName(SUBSCRIPTION1).subscribe();
		System.out.println(" service setup - consumer ");
        } catch (PulsarClientException e) {
            System.out.println(PULSAR_CLIENT_EXCEPTION_MSG + " in constructor :(");
            System.out.println(e);
		System.out.println("end error");
        }
    }
    
    public String postMessage(MessagePacket msg) throws Exception {
	   System.out.println("new post");
	ObjectMapper objMap = new ObjectMapper();
        byte[] content = objMap.writeValueAsBytes(msg);
	    System.out.println("sending");
        MessageId msgId = producer.newMessage().value(content).send();
	System.out.println("return from post");
        return "Successful Post";
    }
    public void closeOut() throws Exception {
        producer.close();
        consumer.close();
        client.close();
        System.out.println("Shut down");
        }
    public String getMessage() throws Exception{
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
    
