package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 pom.xml의 시큐리티 설정때문에 
 항상 기본 로그인 창으로 리다이랙션 함 
 
 우리가 제작한 인스타그램 로그인 페이지를 가기위해 아래와 같은 설정을 진행해야함 
 configure(HttpSecurity http) 을 오버라이딩 해서 
 		super.configure(http);을 삭제 
 
*/
@EnableWebSecurity // 해당 파일로 시큐리티 활성화 
@Configuration //IOC로 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean // IOC 등록될때 Bean을 읽을수 있음
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http); // super 삭제 : 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨
		http.csrf().disable(); //postman이나 회원가입 폼으로 요청하나, 구분하지 않음
		http.authorizeRequests()
		.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**") // 해당 경로를 제외하고
		.authenticated()
		.anyRequest().permitAll() //이외의 경로는 접근 허용
		.and()
		.formLogin() // 해당 경로는 로그인 form으로 
		.loginPage("/auth/signin") // 로그인 form url // GET //다른페이지를 갔는데 인증이 안되어 있으면 해당 페이지로 이동
		.loginProcessingUrl("/auth/signin") // POST // 로그인 요청이 들어왔다면 스프링 시큐리티가 로그인 프로세스를 진행
		.defaultSuccessUrl("/"); // 성공했을시의 url
		
	}
}
