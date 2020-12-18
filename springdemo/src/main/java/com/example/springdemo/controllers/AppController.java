package com.example.springdemo.controllers;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.example.springdemo.models.MessagePacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.springdemo.services.HelloService;
import com.example.springdemo.services.PulsarService;

@RestController
@RequestMapping("/pulsar")
public class AppController {
	@Autowired
	HelloService helloService;
	@Autowired
	PulsarService pulsarService;
	
	@GetMapping("/hello")
	public String getHello() {
		return helloService.getHello();
	}
	
	@PostMapping("/postmessage")
	public String postMessage(@RequestBody MessagePacket newMessage) {
		try {
			return pulsarService.postMessage(newMessage);
		} catch(Exception e) {
			return "failure in controller";
		}
	}
		     
	@GetMapping("/getmessage")
	public String getMessage() {
		try {
		return pulsarService.getMessage();
		} catch(Exception e) {
			return "failure in controller";
		}
	}
}
