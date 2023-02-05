package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//원래 시큐리티에서 로그인 요청이 들어요면 UserDetailsService 가 낚아챈다
@Service //IOC
public class PrincipalDetailsService implements UserDetailsService{ // UserDetailsService가 PrincipalDetailsService로 덮어 씌어진다
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		System.out.println("실행확인" + username);
		return null;
	}
}
