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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.test.pds2.path.SystemPath;


@Service
public class ResumeService {
	 
	@Autowired                            //@Autowired는 한꺼번에 하는게 안되는 것 같다 
	private ResumeDao resumeDao;			//두개의 클래스를 주입시키고 싶을때는 두번 써야하는듯
	@Autowired 
	private ResumeFileDao resumeFileDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);
	
	@Transactional
	public void insertResume(ResumeRequest resumeRequest) {
		
		logger.debug("insertResume - parameter - resumeRequest : " + resumeRequest.toString());
		//logger.debug("insertResume - parameter - path : " + path);
		
		Resume resume = new Resume();
		//새로운 Resume클래스의 객체 resume을 생성
		resume.setResumeTitle(resumeRequest.getResumeTitle());
		resume.setResumeContent(resumeRequest.getResumeContent());
		//resumeRequest에 셋팅되어있던 insertResume.jsp의 입력데이터를 resume 객체에 셋팅
			
		
		ResumeFile resumeFile = new ResumeFile();		
		//새로운 ResumeFile클래스의 객체 resumeFile을 생성
		
		//resume.setResumeFile(resumeFile); //이게 왜들어갔지?	
				
		int resumeId= resumeDao.insertResume(resume); 
		//미리 주입해둔 resumeDao에 접근해 셋팅된 resume객체를 입력값으로 하는 insertResume메서드를 실행한뒤 
		//처리 후 반환되는 키값 resumeId변수에 저장
				
		for(int i= 0; i<resumeRequest.getMultipartFile().size(); i++) {
			//multipartFile을 resumeFile에 끼워맞춘다
			MultipartFile multipartFile = resumeRequest.getMultipartFile().get(i);
			
			// 유효 이름
			UUID uuid = UUID.randomUUID();  //16진수의 유효 아이디가 만들어진다 랜덤 유효 아이디
			String resumeFileName = uuid.toString();
			resumeFileName = resumeFileName.replace("-", "");  //기존 문자열에서 발견되는 모든 문자 집합을 다른 지정 문자로 변경합니다.	
			// 파일 확장자	
			int dotIndex = multipartFile.getOriginalFilename().lastIndexOf("."); 			
			String resumeFileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
			//IndexOf, lastIndexOf에서 ""안에 문자가 몇번째에 있는지 찾는 매서드 IndexOf는 앞부터 lastIndexOf는 뒤부터
			
			//파틸 타입
			String resumeFileType = multipartFile.getContentType();	 //multipartFile의 파일타입을 가져옴 
			//파일 사이즈
			long resumeFileSize = multipartFile.getSize();  //multipartFile의 사이즈를 가져옴 

			//파일 저장(매개변수 path 위치 가지고 해야한다 path+"/"+resumeFileName+"."+resumeFileExt)
			File file = new File(SystemPath.SYSTEM_PATH+"/"+resumeFileName+"."+resumeFileExt);	
			try {
				multipartFile.transferTo(file); 
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			//resumeFile에 셋팅
			resumeFile.setResumeId(resumeId);
			resumeFile.setResumeFileName(resumeFileName);
			resumeFile.setResumeFileExt(resumeFileExt);
			resumeFile.setResumeFileType(resumeFileType);
			resumeFile.setResumeFileSize(resumeFileSize);
			
			resumeFileDao.insertResumeFile(resumeFile);	
		}
			
		/*MultipartFile multipartFile = resumeRequest.getMultipartFile();
		
		// 유효 이름
		UUID uuid = UUID.randomUUID();  //16진수의 유효 아이디가 만들어진다 랜덤 유효 아이디
		String resumeFileName = uuid.toString();
		resumeFileName = resumeFileName.replace("-", "");  //기존 문자열에서 발견되는 모든 문자 집합을 다른 지정 문자로 변경합니다.	
		// 파일 확장자	
		int dotIndex = multipartFile.getOriginalFilename().lastIndexOf("."); 			
		String resumeFileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
		//IndexOf, lastIndexOf에서 ""안에 문자가 몇번째에 있는지 찾는 매서드 IndexOf는 앞부터 lastIndexOf는 뒤부터
		
		//파틸 타입
		String resumeFileType = multipartFile.getContentType();	 //multipartFile의 파일타입을 가져옴 
		//파일 사이즈
		long resumeFileSize = multipartFile.getSize();  //multipartFile의 사이즈를 가져옴 

		//파일 저장(매개변수 path 위치 가지고 해야한다 path+"/"+resumeFileName+"."+resumeFileExt)
		File file = new File(SystemPath.SYSTEM_PATH+"/"+resumeFileName+"."+resumeFileExt);	
		try {
			multipartFile.transferTo(file); 
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		//resumeFile에 셋팅
		resumeFile.setResumeId(resumeId);
		resumeFile.setResumeFileName(resumeFileName);
		resumeFile.setResumeFileExt(resumeFileExt);
		resumeFile.setResumeFileType(resumeFileType);
		resumeFile.setResumeFileSize(resumeFileSize);
		
		resumeFileDao.insertResumeFile(resumeFile);	*/
		
	}
	
	public Map<String, Object> resumeList(int currentPage, int pagePerRow, String searchOption, String keyword){
		logger.debug("resumeList - method : ");
		logger.debug("resumeList - currentPage : " + currentPage);
		logger.debug("resumeList - pagePerRow : " + pagePerRow);
		logger.debug("resumeList - searchOption : " + searchOption);
		logger.debug("resumeList - keyword : " + keyword);
		
		/*String loginMemberId = loginMember.getMemberId();*/
		
		int beginRow = (currentPage-1)*pagePerRow;

		Map<String, Object> map = new HashMap<String, Object>();
			map.put("beginRow", beginRow);
			map.put("pagePerRow", pagePerRow);
			map.put("searchOption", searchOption);
			map.put("keyword", keyword);/*
			map.put("loginMemberId", loginMemberId);*/
			List<Resume> list = resumeDao.selectResumeList(map);
			int total = resumeDao.totalCountResume(map);
			
		
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
	
	public Resume resumeView(Resume resume) {
		logger.debug("resumeView - resume : " + resume);
				
		Resume resumeView = resumeDao.resumeView(resume);
		logger.debug("resumeView - resumeView : " + resumeView.toString());
		return resumeView;
	}
	
	@Transactional
	public void deleteResume(Resume resume) {
		logger.debug("deleteResume - resume : " + resume.toString());
		
		List<ResumeFile> list = resumeFileDao.selectResumeFile(resume);
		logger.debug("deleteResume - list : " + list.toString());
		
		for(int i = 0; i<list.size(); i++) {
			String resumeFileName = list.get(i).getResumeFileName();
			String resumeFileExt = list.get(i).getResumeFileExt();
			int resumeFileId = list.get(i).getResumeFileId();
			String fullPath = SystemPath.SYSTEM_PATH + "\\" + resumeFileName + "." + resumeFileExt;
			File file = new File(fullPath);
			resumeFileDao.deleteResumeFile(resumeFileId);
			file.delete();			
		}
		resumeDao.deleteResume(resume);
	}
	
	@Transactional
	public void deleteResumeFile(ResumeFile resumeFile, String resumeFileName, String resumeFileExt) {
		logger.debug("deleteResumeFile - resumeFile : " + resumeFile.toString());
		

		String fullPath = SystemPath.SYSTEM_PATH + "\\" + resumeFileName + "." + resumeFileExt;
		logger.debug("deleteResumeFile - fullPath : " + fullPath.toString());
		
		File file = new File(fullPath);
		logger.debug("deleteResumeFile - file : " + file.toString());
		
				
		int resumeFileId = resumeFile.getResumeFileId();
		
		resumeFileDao.deleteResumeFile(resumeFileId);
		
		file.delete();
	}
}
