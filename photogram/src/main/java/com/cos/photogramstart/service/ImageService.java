package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(){
		return imageRepository.mPopular();
	}
	
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) X
	public Page<Image> 이미지스토리(int principalId,Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId,pageable);
		return images;
	}
	
	
	@Value("${file.path}") 
	private String uploadFolder;
	
	public void 사진업로드(ImageUploadDto imageUploadDto,PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();// 몇십억분의 1의 확률로 같은게 들어올수 있다.
		String imageFileName = uuid +"_"+imageUploadDto.getFile().getOriginalFilename(); // 1.jpg
		System.out.println("이미지 파일이름" + imageFileName);
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		//통신 , I/O -> 예외가 발생할 수 가 있다. 컴파일시 잡아 낼수 없는 오류
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
		// image 테이블 저장 
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
//		System.out.println(imageEntity); //출력을 toString에서 하면 getter가 전부 실행됨. 
	}
}
