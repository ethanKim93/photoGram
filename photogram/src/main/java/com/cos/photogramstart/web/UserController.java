package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping({"/user/{pageUserId}"})
	public String profile(@PathVariable int pageUserId,Model  model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.회원프로필(pageUserId,principalDetails.getUser().getId());
		model.addAttribute("dto",dto); 
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		//1. 추천 @AuthenticationPrincipal PrincipalDetails principalDetails : 어노테이션을 이용하여 세션 정보 가져오는법
//		System.out.println("세션 정보 : " + principalDetails.getUser());
		//2. 비추천 세션 정보를 직접 가져오는법 방법
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//		System.out.println("직접 찾은 세션 정보 : " + mPrincipalDetails.getUser());
		
		
		return "user/update";
	}
	
//	hearder.jsp 상단에 isAuthenticated을 사용하여 Model로 넘겨주지 않아도 세션 정보에 접근이 가능함
	/*
	 * @GetMapping("/user/{id}/update") public String update(@PathVariable int
	 * id,@AuthenticationPrincipal PrincipalDetails principalDetails,Model model) {
	 * //1. 추천 @AuthenticationPrincipal PrincipalDetails principalDetails : 어노테이션을
	 * 이용하여 세션 정보 가져오는법 System.out.println("세션 정보 : " + principalDetails.getUser());
	 * //2. 비추천 세션 정보를 직접 가져오는법 방법 Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); PrincipalDetails
	 * mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
	 * System.out.println("직접 찾은 세션 정보 : " + mPrincipalDetails.getUser());
	 * 
	 * 
	 * model.addAttribute("principal",principalDetails.getUser()); return
	 * "user/update"; }
	 */
}
