package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	private String name ; //필수
	private String password ; //필수 
	private String website ;
	private String bio ;
	private String phone ;
	private String gender ;
	
	//필수가 아닌 값까지 받기때문에 조금 위험. 수정이 필요함
	public User toEntity() {
		return  User.builder()
				.name(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build(); 
	}
}
