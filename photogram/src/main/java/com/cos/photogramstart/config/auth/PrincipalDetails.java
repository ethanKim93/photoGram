package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//권한을 가져오는 함수 ,권한은 user가 가지고 있음
	//권한 : 한개가 아닐 수 있음 (3개 이상의 권한)
	// Collection으로 반환해야함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collector = new ArrayList<GrantedAuthority>();
		// 기본
		/*
		 * collector.add(new GrantedAuthority() {
		 * 
		 * @Override public String getAuthority() { // TODO Auto-generated method stub
		 * return user.getRole(); } });
		 */
		//람다식으로 변경
		collector.add(()->{return user.getRole();}); 
		
		return collector;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	//나중에 만료된 user를 설정하고 싶다면 사용
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
 	}
 
}
