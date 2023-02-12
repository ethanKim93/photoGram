package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	
	
	@GetMapping({"/image/popular"})
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping({"/image"})
	public String imageUpload(ImageUploadDto imageUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
//			System.out.println("이미지가 첨부 되지 않았습니다");
			throw new CustomValidationApiException("이미지가 첨부되지 않았습니다",null);
		}
		 
		//서비스 호출 
		imageService.사진업로드(imageUploadDto, principalDetails); // 실제 로직은 service에서 구현
		
		return "redirect:/user/" + principalDetails.getUser().getId();
	}
}


