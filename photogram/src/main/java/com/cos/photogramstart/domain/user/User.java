package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API (자바로 데이터를 영구적으로 저장할수 있는 API를 제공)
/*
	Validation을 사용하여 데이터를 전처리함 .
	(ID가 20자 이상이면 service와 repository 까지 가지 않고 앞단에서 컷) 
	exceptionHandler는 후처리 
	(User가 있는지는 DB에서 조회해야함.)
	
	=> 공통기능은 (AOP)로 처리
*/
@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data
@Entity // DB에 테이블 생성
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 번호 증가 전략이 데이터베이스를 따라간다
	private int id; // 서비스가 크다면 Long으로 만들것
	
	@Column(length = 20, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	private String website; //웹 사이트 
	private String bii; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role;// 권한
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 Insert되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
