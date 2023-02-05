package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import javassist.bytecode.stackmap.MapMaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드의 생성자를 만들어줌 DI 할때 사용 //  DI를 위한 생성자 삭제 가능 
@Controller // 1.ioc 등록 2. 파일을 리턴하는 컨트롤러
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}
//	
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}

	/*
		CSRF 토큰. 
		클라이언트에서 서버로 form을 전송할때 , 
		서버는 시큐리티가 먼저 CSRF 토큰을 검사함. 
		
		서버에서 회원가입을 응답할때 시큐티는 signup.jsp를 반활할때 CSRF 토큰을 함께 반환 한다. 
		 그 이후 클라이언트가 서버에 요청을 한다면 , 서버는 서버가 보낸CSRF 토큰이 있는지 확인 한다.
		 
		 따라서 정상적인 방법으로 로그인인지 확인할수 있음 
		 ex) post Man 같은 요청은 회원가입으로 될 수 없음 
	*/
	
	/*
	 * AOP를 활용한 전,후 처리
	 * 아래처럼 컨트롤러에서 요청을 받았을때 처리 할 수도 있음
	 * if(signupDto.getUsername().lenth() > 20 {
	 * 	   return "길이초과" 
	 * }
	 * 하지만 일일이 이렇게 하면 컨트롤러가 너무 복잡해짐 
	 * mvn에서 "Spring Boot Starter Validation" 을 사용할 수 있음 
	 * 
	 * @Valid 추가
	 * 
	*/
	//회원 가입 버튼 -> /auth/signup->/auth/signin
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto,BindingResult bindingResult) { // key=value (x-www=-form-urlencoded)
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.print("===================="); 
				System.out.print(error.getDefaultMessage()); 
			}
		}
		
		log.info(signupDto.toString());
		//User <- SignuoDto
		User user = signupDto.toEntity();
		User userEntity =  authService.회원가입(user);
		System.out.println(userEntity);
		return "auth/signin"; // 회원가입이 성공하면 로그인 페이지로 이동
	}
}
