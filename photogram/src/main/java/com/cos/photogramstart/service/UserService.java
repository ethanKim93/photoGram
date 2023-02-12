package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCyBCryptPasswordEncoder;

	@Transactional // write니깐 사용
	public User 회원수정(int id, User user) {
		System.out.println("@@@@@@@@ in?? ");
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
