package com.example.springdemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.springdemo.services.HelloService;

@RestController
@RequestMapping("/app")
public class AppController {
	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	public String getHello() {
		return helloService.getHello();
	}
}
