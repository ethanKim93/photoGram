package com.cos.photogramstart.web.dto;

import java.util.Map;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

//제너릭 사용
public class CMRespDto<T> {
	private int code; // 1(성공).-1(실패)
	private String message;
	private T data;
}
/*
 * public class CMRespDto {
	//상황에 따라 Map이나 User가 필요할때도 있고 없을때도 있다. 그럴때마다 null을 넣을수 없다
	private String message;
	private Map<String, String> errorMap;
	private User user;
	
}*/
