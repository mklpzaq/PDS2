package com.test.pds2.resume.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {
	public void insertResume(ResumeRequest resumeRequest, String path) {
		MultipartFile multipartFile = resumeRequest.getMultipartFile();
		
		Resume resume = new Resume();
		resume.setResumeTitle(resumeRequest.getResumeTitle());
		resume.setResumeContent(resumeRequest.getResumeContent());
		
		//multipartFile을 resumeFile에 끼워맞춘다
		
		ResumeFile resumeFile = new ResumeFile();
		// 유효 이름
		UUID uuid = UUID.randomUUID();  //16진수의 유효 아이디가 만들어진다 랜덤 유효 아이디
		String resumeFileName = uuid.toString();
		resumeFileName = resumeFileName.replace("-", "");  //기존 문자열에서 발견되는 모든 문자 집합을 다른 지정 문자로 변경합니다.
		resume.setResumeFile(resumeFile);
		System.out.println("resumeFileName  : "+resumeFileName);
		
		// 파일 확장자
		
		String sss = "abcdabcd";
		System.out.println(sss.indexOf("ab"));  //0부터 시작이니까 0
		System.out.println(sss.lastIndexOf("ab")); //뒤에서부터 찾는데 번호 자체는 어차피 첫번째부터 셈. 4
		System.out.println(multipartFile.getOriginalFilename());  //현재 파일의 전체 이름 예를 들어 ajax.txt
		int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");  //IndexOf, lastIndexOf에서 ""안에 문자가 몇번째에 있는지 찾는 매서드 IndexOf는 앞부터 lastIndexOf는 뒤부터
		String resumeFileExt = multipartFile.getOriginalFilename().substring(dotIndex+1); 		
		
		System.out.println("resumeFileExt  : "+resumeFileExt);
				
		//파틸 타입
		String resumeFileType = multipartFile.getContentType(); //multipartFile의 파일타입을 가져옴 
		System.out.println("resumeFileType : "+resumeFileType);
		//파일 사이즈
		long resumeFileSize = multipartFile.getSize(); //multipartFile의 사이즈를 가져옴 
			System.out.println("resumeFileSize : "+resumeFileSize);	
		//파일 저장(매개변수 path 위치 가지고 해야한다 path+"/"+resumeFileName+"."+resumeFileExt)
		File file = new File(path+"/"+resumeFileName+"."+resumeFileExt);
		
		try {
			multipartFile.transferTo(file); //파일을 만드는 매서드. multipartFile에 빈파일을 만들어 만든 file을 넣는다
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		
		
	}
}
