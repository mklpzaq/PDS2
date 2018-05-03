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
		
		
		if(resumeRequest.getMultipartFile() != null) {
			for(int i= 0; i<resumeRequest.getMultipartFile().size(); i++) {
				if(resumeRequest.getMultipartFile().get(i).getSize() != 0) {
				
				//기존에 Controller에서 넘겨받은 커맨드객체 resumeRequest에 List<MultipartFile>타입으로 
				//자동 셋팅되어 있던 multipartFile만큼 for문을 돌려준다
						
				MultipartFile multipartFile = resumeRequest.getMultipartFile().get(i);
				//List의 i번째의 MultipartFile을 겟팅해와 변수 multipartFile에 담는다
				
				// 유효 이름구하는 코드 
				UUID uuid = UUID.randomUUID();  //16진수의 유효 아이디가 만들어진다 랜덤 유효 아이디
				//실행시 uuid : b0fe5762-936b-41d6-ae47-26d55511a2aa 이런 랜덤아이디 값이 만들어진다
				String resumeFileName = uuid.toString();
				//우리가 기존에 변수의 값을 확인하기 위해 사용했던 toString메서드는 기본적으로 값을 확인하는 용도도 있지만
				//값을 String 타입으로 변환해주는 용도도 있는것 같다
				//결론적으로 UUID타입이었던 uuid변수를 String 타입 변수 resumeFileName형변환하여 담는다
				resumeFileName = resumeFileName.replace("-", "");  //기존 문자열에서 발견되는 모든 문자 집합을 다른 지정 문자로 변경합니다.	
				//resumeFileName에서 발견되는 특수문자를 없애고 최종적으로 유효아이디를 완성한다
				
				
				// 파일 확장자	
				int dotIndex = multipartFile.getOriginalFilename().lastIndexOf("."); //IndexOf, lastIndexOf에서 ""안에 문자가 몇번째에 있는지 찾는 매서드 IndexOf는 앞부터 lastIndexOf는 뒤부터
				//파일의 OriginalFilename을 가져온뒤 뒤에 있는 .문자가 몇번째 행에 위치하는지 구하여 int타입 변수  dotIndex에 담는다			
				String resumeFileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
				//앞서 구한 .이 위치하는 행보다 바로 뒷부분부터 잘라내서 변수 resumeFileExt에 담는다
				
				
				//파일 타입
				String resumeFileType = multipartFile.getContentType();	 //multipartFile의 파일타입을 가져옴 
				//그냥 getContentType메서드로 파일의 타입을 가져와 변수에 담는다
				
				//파일 사이즈
				long resumeFileSize = multipartFile.getSize();  //multipartFile의 사이즈를 가져옴 
				//그냥 getSize로 파일의 사이즈를 가져와 변수에 담는데, 다만 큰 파일이 올것을 생각해 타입을 int보다 큰 long로 받아준다
				
				
				//파일 저장(매개변수 path 위치 가지고 해야한다 path+"/"+resumeFileName+"."+resumeFileExt)
				File file = new File(SystemPath.SYSTEM_PATH+"/"+resumeFileName+"."+resumeFileExt);
				//기본에 만들어두었던 SYSTEM_PATH의 위치에 유효아이디+"."+확장자를 더해 빈 파일 객체를 만든다
				try {
					multipartFile.transferTo(file); 
					//resumeRequest에 셋팅되었던 multipartFile를 빈 파일 객체에 저장한다
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
				//resumeFile에 셋팅
				//랜덤으로 얻어낸 resumeId와
				//multipartFile에서 얻어낸 resumeFileName,resumeFileExt,resumeFileType,resumeFileSize을 
				//미리 선언해둔 resumeFile객체에 셋팅한다
				resumeFile.setResumeId(resumeId);
				resumeFile.setResumeFileName(resumeFileName);
				resumeFile.setResumeFileExt(resumeFileExt);
				resumeFile.setResumeFileType(resumeFileType);
				resumeFile.setResumeFileSize(resumeFileSize);
				
				//셋팅된 resumeFile객체를 입력값으로 주입된 resumeFileDao의 insertResumeFile메서드를 호출한다
				resumeFileDao.insertResumeFile(resumeFile);	
				
				}
			}
		}
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
			String fullPath = SystemPath.SYSTEM_PATH + "\\" + resumeFileName + "." + resumeFileExt;
			File file = new File(fullPath);
			resumeFileDao.deleteResumeFile(resumeFileName);
			file.delete();			
		}
		resumeDao.deleteResume(resume);
	}
	
	@Transactional
	public void deleteResumeFile(String resumeFileName, String resumeFileExt) {
		

		String fullPath = SystemPath.SYSTEM_PATH + "\\" + resumeFileName + "." + resumeFileExt;
		logger.debug("deleteResumeFile - fullPath : " + fullPath.toString());
		
		File file = new File(fullPath);
		logger.debug("deleteResumeFile - file : " + file.toString());
		
				
		resumeFileDao.deleteResumeFile(resumeFileName);
		
		file.delete();
	}
	
	public Resume updateResumeView(Resume resume) {
		logger.debug("updateResumeView - resume : " + resume.toString());
				
		return resumeDao.updateResumeView(resume);
	}
	
	@Transactional
	public void updateResume(Resume resume, ResumeRequest resumeRequest, List<String> deleteFile) {
		
		logger.debug("updateResume - resumeRequest : " + resumeRequest.toString());
		logger.debug("updateResume - deleteFile : " + deleteFile);
		
		resume.setResumeTitle(resumeRequest.getResumeTitle());
		resume.setResumeContent(resumeRequest.getResumeContent());			
		
		resumeDao.updateResume(resume); 
		
		ResumeFile resumeFile = new ResumeFile();		
		if(resumeRequest.getMultipartFile() != null) {
			for(int i= 0; i<resumeRequest.getMultipartFile().size(); i++) {
				if(resumeRequest.getMultipartFile().get(i).getSize() != 0) {
					MultipartFile multipartFile = resumeRequest.getMultipartFile().get(i);
									
					//파일네임
					UUID uuid = UUID.randomUUID();  
					String resumeFileName = uuid.toString();				
					resumeFileName = resumeFileName.replace("-", "");  
					
					//파일확장자
					int dotIndex = multipartFile.getOriginalFilename().lastIndexOf("."); 		
					String resumeFileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
					
					//파일타입
					String resumeFileType = multipartFile.getContentType();	 
					
					//파일사이즈
					long resumeFileSize = multipartFile.getSize();  
					
					//파일생성
					File file = new File(SystemPath.SYSTEM_PATH+"/"+resumeFileName+"."+resumeFileExt);				
					try {
						multipartFile.transferTo(file); 
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				
					resumeFile.setResumeId(resume.getResumeId());
					resumeFile.setResumeFileName(resumeFileName);
					resumeFile.setResumeFileExt(resumeFileExt);
					resumeFile.setResumeFileType(resumeFileType);
					resumeFile.setResumeFileSize(resumeFileSize);
					
					resumeFileDao.insertResumeFile(resumeFile);	
				}
			}
		}
		
		for(int i = 0; i<deleteFile.size(); i++) {
			String deleteOneFile =  deleteFile.get(i);
			int indexNum= deleteOneFile.lastIndexOf(".");
			String fileExt = deleteOneFile.substring(indexNum+1);
			String fileName = deleteOneFile.substring(0, indexNum);			
			
			String fullPath = SystemPath.SYSTEM_PATH + "\\" + fileName + "." + fileExt;
			
			File file = new File(fullPath);			
								
			resumeFileDao.deleteResumeFile(fileName);
			
			file.delete();
		}
		
	}
}
