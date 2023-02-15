package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCyBCryptPasswordEncoder;
	
	@Transactional(readOnly = true)
	public User 회원프로필(int uesrId) {
		// SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(uesrId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});
//		System.out.println("===========");
//		userEntity.getImages().get(0); // 양방향이 Lazy 전략이면 이때 select 쿼리로 조회함
		
		return userEntity; 
	}
	@Transactional // write니깐 사용
	public User 회원수정(int id, User user) {
		// 1 . 영속화
//		User userEntity = userRepository.findById(id).get(); // 1.무조건 찾았다. 걱정마 2.못찾았어 익섹션 발동 시킬께 orElseThrow()
		/*
		 * User userEntity = userRepository.findById(id).orElseThrow(new
		 * Supplier<IllegalArgumentException>() {
		 * 
		 * @Override public IllegalArgumentException get() { // return new
		 * IllegalArgumentException("찾을 수 없는 id  입니다."); } });
		 */
		User userEntity = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을 수 없는 id  입니다.");
		});
		// 2.영속화된 오브젝트 수정 - 더티 체킹(업데이트 완료)
		userEntity.setName(user.getName());

		String rawPassword = user.getPassword();
		String encPassword = bCyBCryptPasswordEncoder.encode(rawPassword);

		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());

		return userEntity;
	}// 더티체킹이 일어나면서 업데이트가 완료됨
}
