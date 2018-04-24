package com.test.pds2.resume.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.test.pds2.path.SystemPath;

@Service
public class ResumeService {
	
	@Autowired 
	private ResumeDao resumeDao;	
	@Autowired 
	private ResumeFileDao resumeFileDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);
	
	@Transactional
	public void insertResume(ResumeRequest resumeRequest, String path) {
		
		Resume resume = new Resume();	
		resume.setResumeTitle(resumeRequest.getResumeTitle());
		resume.setResumeContent(resumeRequest.getResumeContent());
		
		//multipartFile을 resumeFile에 끼워맞춘다
		
		ResumeFile resumeFile = new ResumeFile();
		// 유효 이름
		UUID uuid = UUID.randomUUID();  //16진수의 유효 아이디가 만들어진다 랜덤 유효 아이디
		String resumeFileName = uuid.toString();
		resumeFileName = resumeFileName.replace("-", "");  //기존 문자열에서 발견되는 모든 문자 집합을 다른 지정 문자로 변경합니다.		
		resume.setResumeFile(resumeFile); //이게 왜들어갔지?
		System.out.println("resumeFileName  : "+resumeFileName);
		
		int resumeId= resumeDao.insertResume(resume);
		
		for(int i = 0; i<resumeRequest.getMultipartFile().size(); i++) {
			MultipartFile multipartFile = resumeRequest.getMultipartFile().get(i);			
			int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");  
			String resumeFileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
			String resumeFileType = multipartFile.getContentType();
			long resumeFileSize = multipartFile.getSize();
			File file = new File(SystemPath.SYSTEM_PATH+"/"+resumeFileName+"."+resumeFileExt);		
			try {
				multipartFile.transferTo(file); 
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} 
			
			resumeFile.setResumeId(resumeId);
			resumeFile.setResumeFileName(resumeFileName);
			resumeFile.setResumeFileExt(resumeFileExt);
			resumeFile.setResumeFileType(resumeFileType);
			resumeFile.setResumeFileSize(resumeFileSize);
			
			resumeFileDao.insertResumeFile(resumeFile);	
		}
		
		
		// 파일 확장자		
		/*String sss = "abcdabcd";
		System.out.println(sss.indexOf("ab"));  //0부터 시작이니까 0
		System.out.println(sss.lastIndexOf("ab")); //뒤에서부터 찾는데 번호 자체는 어차피 첫번째부터 셈. 4
		System.out.println(multipartFile.getOriginalFilename());  //현재 파일의 전체 이름 예를 들어 ajax.txt*/		
		
			
			//IndexOf, lastIndexOf에서 ""안에 문자가 몇번째에 있는지 찾는 매서드 IndexOf는 앞부터 lastIndexOf는 뒤부터
		
				
		//파틸 타입
		 //multipartFile의 파일타입을 가져옴 
		//파일 사이즈
		 //multipartFile의 사이즈를 가져옴 
			
		//파일 저장(매개변수 path 위치 가지고 해야한다 path+"/"+resumeFileName+"."+resumeFileExt)
		
		
		
		
		
		//resumeFile에 셋팅	
		
	
		
		
	}
	
	
	public Map<String, Object> resumeList(int currentPage, int pagePerRow, String searchOption, String keyword){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Resume> list;
		int total;
		/*String loginMemberId = loginMember.getMemberId();*/
		
		int beginRow = (currentPage-1)*pagePerRow;
		
		System.out.println("beginRow : "+beginRow);
		
			map.put("beginRow", beginRow);
			map.put("pagePerRow", pagePerRow);
			map.put("searchOption", searchOption);
			map.put("keyword", keyword);/*
			map.put("loginMemberId", loginMemberId);*/
			list = resumeDao.selectResumeList(map);
			total = resumeDao.totalCountResume(map);
			
		
		int lastPage = 0;
		if(total%pagePerRow == 0) {
			lastPage = total/pagePerRow;
		}else {
			lastPage = total/pagePerRow + 1;
		}
		
		int pageView = 5;
		int startPage = ((currentPage-1)/5)*5+1; 
		int endPage = startPage + pageView -1; 
		if(endPage>lastPage) {
			endPage=lastPage;
		}
		
		Map<String, Object> returnmap = new HashMap<String, Object>();
		returnmap.put("list", list);
		returnmap.put("lastPage", lastPage);
		returnmap.put("startPage", startPage);
		returnmap.put("endPage", endPage);
		return returnmap;
	}
}
