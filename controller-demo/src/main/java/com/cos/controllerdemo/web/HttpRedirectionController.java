package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpRedirectionController {
	
	/*
	 * home 와 away의 중복된 코드가 1만줄 있을때 
	 * 1만줄을 중복해서 2번 사용하는것이 아니라 
	 * 
	 * redirect를 사용하여 재사용할 수 있음
	 * 
	 * away의 로직을 사용후 home을 리다이랙션
	*/
	@GetMapping("/home")
	public String home() {
		//1만줄 가정
		return "home";
	}
	
	@GetMapping("/away")
	public String away() {
		//다른  코드
		return "redirect:/home"; //"redirect:"은 예약어 // home 경로로 redirection 한다.(@Controller)
	
	}
}
