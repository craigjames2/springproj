package com.example.springdemo.services;

import org.springframework.stereotype.Service;

@Service

public class HelloService {
	public static final String greeting = "Hello, World!";
	public HelloService() {
		System.out.println("running");
	}
	public String getHello() {
		return greeting;
	}
}
