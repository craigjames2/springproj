package com.example.springdemo.services;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
	public HelloService() {
		String greeting = "Hello";
	}
	public String getHello() {
		return greeting;
	}
}
