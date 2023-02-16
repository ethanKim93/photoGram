package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role;// 권한
	
	// 나는 연관관계의 주인이 아니다. 그러므로 테이블에 만들지마
	//  User를 Select 할때 해당 User id로 등록된 image들을 다 가져와
	// Lazy = User를 Select  할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수가 호출될때 가져와!(Default)
	//  Eager = User를 select 할때 해당 User id로 등록된 image들을 전부 Join 해서 가져와!
	//
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY) 
	@JsonIgnoreProperties({"user"}) // Image 내부에있는 user를 무시하고 가져옴 . 설정을 안하면 무한 순환 참조
	private List<Image> images; // 양방향 매핑
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 Insert되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
