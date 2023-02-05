package com.cos.controllerdemo.web;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.controllerdemo.domain.User;

import ch.qos.logback.classic.Logger;

@RestController
public class HttpBodyController {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(HttpBodyController.class);
	
	@PostMapping("/body1")
	public String xwwwformurlencoded(String username) {
		
		log.info(username);
		return "key=value 전송옴";
	} 
	
	@PostMapping("/body2")
	public String plantext(@RequestBody String data){ // 평문
		
		log.info(data);
		return "text/plain 전송옴";

	}
	@PostMapping("/body3")
	public String applicationjson(@RequestBody String data) {
		log.info(data);
		return "json 전송옴";
		
	}
	
	@PostMapping("/body4")
	public String applicationjsonToObject(@RequestBody User user) {
		log.info(user.getUsername());
		return "json 전송옴";
		
	}
}
