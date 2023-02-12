package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	@Transactional
	public void  구독하기(int fromUserId,int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);

		}catch (Exception e) {
			throw new CustomApiException("이미 구독하였습니다");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId,int toUserId) {
//		int result = subscribeRepository.mUnSubscribe(fromUserId, toUserId);
		//오류가 날 일이 없음. 삭제가 실패해도 오류가 아님
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
