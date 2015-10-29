package com.nova.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "com.nova.demo.service.HelloService")
public class HelloServiceImpl implements HelloService {
	@WebMethod
	public String say(String name) {
		return "hello, " + name;
	}
}
