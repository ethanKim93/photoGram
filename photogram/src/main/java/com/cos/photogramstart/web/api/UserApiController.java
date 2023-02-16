package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;

	// update시 DB 만 바꾸지 말고 세션 정보도 함께 변경해줘야한다
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, // 꼭Valid가/적혀있는다음파라메터에적어야함.
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
//				System.out.println("====================ㄴㄴ"); 
//				System.out.println(error.getDefaultMessage()); 
			}
			throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
		} else {
			System.out.println(userUpdateDto);
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity); // 세션정보 변경
			return new CMRespDto<>(1, "회원수정완료", userEntity); // 응답시에 userEntity의 모든 Getter 함수가 호출되고 JSON으로 파싱하여 응답한다.
		}

	}
}
