package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//요청하는 데이터는 DTO
//응답하는 데이터는 Req

//@Getter
//@Setter
@Data
public class SignupDto {
	
	/*
	 * validation 조건 추가 
	 * 어떤 설정이 있을지는 구글링!
	 * 예전에는 기본 기능이 였으나 현재는  의존성 추가 해줘야함
	*/
	@Max(20)
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank 
	private String name;
	
	public User toEntity() {
		return  User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build(); 
	}
}
