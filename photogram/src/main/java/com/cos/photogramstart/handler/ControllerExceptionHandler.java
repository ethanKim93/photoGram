package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController //data return
@ControllerAdvice // 모든 controller의 execption을 낚아챔
public class ControllerExceptionHandler {

//handling 하는것으로 수정
//	@ExceptionHandler(RuntimeException.class) // RuntimeException이 발생하는 exception 다 가져옴
//	public String validationException(RuntimeException e){
//		return e.getMessage();
//	}
	
	/*// 
	 * @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하는
	 * exception 다 가져옴 public CMRespDto<?>
	 * validationException(CustomValidationException e){ //제너릭에 ? 를 넣으면 알아서 return
	 * 값이랑 맞춤 return new
	 * CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap()); }
	 */
	
		// 자바스크립트 사용 , 에러 화면 페이지 전환이 아니라 error 알림창만 발생하고 다시 회원가입 창으로 돌아옴
	@ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하는 exception 다 가져옴
	public String validationException(CustomValidationException e){ //제너릭에 ? 를 넣으면 알아서 return 값이랑 맞춤
		return Script.back(e.getErrorMap().toString());
	} 
	/*
		CMRespDto , Script 비교
		1. 클라이언트에게 응답할때는 Script 좋음 
		2. Ajax 통신 - CMResDTO
		3. Android 통신 - CMResDTO
		
		사용자가 직접 볼때는 Script 
		다른 개발자(프론트)가 받을때는 CMResDTO
	*/
}
