package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

//원래 시큐리티에서 로그인 요청이 들어요면 UserDetailsService 가 낚아챈다
@RequiredArgsConstructor
@Service //IOC
public class PrincipalDetailsService implements UserDetailsService{ // UserDetailsService가 PrincipalDetailsService로 덮어 씌어진다
	
	
	private final UserRepository userRepository;
	//비밀번호는 알아서 처리를 해줌 . 
	//ID만 확인 하면됨
	// 1. 패스워드는 알아서 체킹하기 때문에 신경 쓸 필요가 없다
	//	2. 리턴이 잘되면 자동으로 UserDetails을 세션으로 만든다
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//		System.out.println("실행확인" + username);
		User userEntity = userRepository.findByUsername(username);
		 
		if(userEntity == null) {
			return null;

		}else {
			return new PrincipalDetails(userEntity);
		}
	}
}
