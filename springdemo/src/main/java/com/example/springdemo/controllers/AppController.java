package com.example.springdemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.springdemo.services.HelloService;

@RestController
@RequestMapping("/pulsar")
public class AppController {
	@Autowired
	HelloService helloService;
	PulsarService pulsarService;
	
	@GetMapping("/hello")
	public String getHello() {
		return helloService.getHello();
	}
	
	@PostMapping(("/postmessage")
	public String postMessage(@RequestBody String newMessage) {
		return pulsarService.postMessage(newMessage);
	}
}
