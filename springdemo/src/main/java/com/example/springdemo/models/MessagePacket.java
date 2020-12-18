package com.example.springdemo.models;

public class MessagePacket {
	private String message;
	private MessagePacket() {
	}
	public MessagePacket(String message) {
		this();
		this.message = message;
	}
	public String getMessage(){
		return message;
	}
}
