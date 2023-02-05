package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController //data return
@ControllerAdvice // 모든 controller의 execption을 낚아챔
public class ControllerExceptionHandler {

//handling 하는것으로 수정
//	@ExceptionHandler(RuntimeException.class) // RuntimeException이 발생하는 exception 다 가져옴
//	public String validationException(RuntimeException e){
//		return e.getMessage();
//	}
	
	@ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하는 exception 다 가져옴
	public CMRespDto<?> validationException(CustomValidationException e){ //제너릭에 ? 를 넣으면 알아서 return 값이랑 맞춤
		return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());
	} 
}
