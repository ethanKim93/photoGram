package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCyBCryptPasswordEncoder;
	
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId,int principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		// SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
		});
//		System.out.println("===========");
//		userEntity.getImages().get(0); // 양방향이 Lazy 전략이면 이때 select 쿼리로 조회함
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); // 1은 페이지 주인 , -1은 주인 아님  
		dto.setImageCount(userEntity.getImages().size());
		
		int SubscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int SubscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeState(SubscribeState == 1);
		dto.setSubscribeCount(SubscribeCount);
		return dto; 
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
