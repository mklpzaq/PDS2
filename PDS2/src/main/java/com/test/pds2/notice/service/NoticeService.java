package com.test.pds2.notice.service;

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

import com.test.pds2.gallery.service.Gallery;
import com.test.pds2.path.SystemPath;



@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private NoticeFileDao noticeFileDao;
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
	@Transactional
	public void insertNotice(NoticeRequest noticeRequest) {
		
		//Notice클래스를 생성하고 notice 객체참조변수를 생성
		Notice notice = new Notice(); 
		//NoticeRequest클래스에 셋팅된 NoticeTitle,NoticeContent를 가져와 notice에 셋팅한다.
		notice.setNoticeTitle(noticeRequest.getNoticeTitle());
		notice.setNoticeContent(noticeRequest.getNoticeContent());
		
		//NoticeFile클래스를 생성하고 noticeFile 객체참조변수를 생성
		NoticeFile noticeFile = new NoticeFile();
		//noticeDao에 접근해 insertNotice를 호출하여 실행한뒤, 반환되는 값을 int타입의 noticeId에 저장한다.
		int noticeId= noticeDao.insertNotice(notice); 
			
		//for문을 통해 noticeRequest 내의 MultipartFile의 사이즈만큼 값을 돌려줌으로 인해, 다수의 파일이 올라오는 것을 처리할 수 있다.
		for(int i=0; i<noticeRequest.getMultipartFile().size(); i++) {
			MultipartFile multipartFile = noticeRequest.getMultipartFile().get(i);
			
			//1.파일이름
			UUID uuid = UUID.randomUUID(); // 16진수 uuid 생성
			//UUID(universally unique identifier)는 소프트웨어 구축에 쓰이는 식별자 표준
			String filename = uuid.toString();
			filename = filename.replace("-", "");
			logger.debug("filename : " + filename);
			noticeFile.setNoticeFileName(filename);
			
			//2.파일확장자
			int doIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
			String fileExt = multipartFile.getOriginalFilename().substring(doIndex + 1);
			noticeFile.setNoticeFileExt(fileExt);
			
			//3.파일컨텐트 타입
			String fileType = multipartFile.getContentType();
			logger.debug("fileType : " + fileType);
			noticeFile.setNoticeFileType(fileType);
			
			//4.파일사이즈
			long fileSize = multipartFile.getSize();
			logger.debug("fileSize : " + fileSize);
			noticeFile.setNoticeFileSize(fileSize);
			
			//5.파일저장(매개변수 path를 이용하여 저장장소를 지정해준다. path+"\\"+filename + "."+fileExt)
			File file = new File(SystemPath.SYSTEM_PATH+"/"+filename+"."+fileExt);
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//셋팅
			noticeFile.setNoticeId(noticeId);
			noticeFileDao.insertNoticeFile(noticeFile);
		}
	}
	
	public Map<String, Object> selectNoticeList(int currentPage, int pagePerRow) {
		logger.debug("selectNoticeList");
		Map<String, Object> map = new HashMap<String, Object>();
		int beginRow = (currentPage-1)*pagePerRow; //페이지의 첫번째 행을 지정해줌
		map.put("beginRow", beginRow);
		map.put("pagePerRow", pagePerRow);
		
		List<Notice> list = noticeDao.selectNoticeList(map);
		/*if(list.size() > 0) {
			logger.debug("selectNoticeList - list.get(1).getNoticeId() : "+list.get(1).getNoticeId());
		}*/
		int total = noticeDao.totalCountNotice();
		int lastPage =0;
		if(total%pagePerRow ==0) {
			lastPage = total/pagePerRow;
		}else {
			lastPage = total/pagePerRow + 1;
		}
		
		int pageView = 5;
		int startPage = ((currentPage-1)/5)*5+1; //페이지 목록이 새로 나올 때, 첫번째로 뜨는 페이지 숫자
		int endPage = startPage + pageView -1; //페이지 목록이 새로 나올 때, 마지막으로 뜨는 페이지 숫자
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
	
	public Notice noticeView(Notice notice) {
		logger.debug("noticeView - notice : " + notice);
		Notice noticeView = noticeDao.noticeView(notice);
		logger.debug("noticeView - noticeView : " + noticeView.toString());
		return noticeView;
	}
	
	public void deleteNoticeList(int[] deleteCheckbox) {	
		logger.debug("deleteNoticeList");
		for(int i = 0; i<deleteCheckbox.length; i++) {
			int noticeId = deleteCheckbox[i];
			noticeDao.deleteNoticeList(noticeId);
		}
	}
	
	public void deleteNotice(Notice notice) {
		logger.debug("deleteNotice - notice : " + notice.toString());
		
		List<NoticeFile> list = noticeFileDao.selectNoticeFile(notice);
		logger.debug("deleteNotice - list : " + list.toString());
		
		for(int i = 0; i<list.size(); i++) {
			String noticeFileName = list.get(i).getNoticeFileName();
			String noticeFileExt = list.get(i).getNoticeFileExt();
			int noticeFileId = list.get(i).getNoticeFileId();
			String fullPath = SystemPath.SYSTEM_PATH + "\\" + noticeFileName + "." + noticeFileExt;
			File file = new File(fullPath);
			noticeFileDao.deleteNoticeFile(noticeFileId);
			file.delete();			
		}
		noticeDao.deleteNotice(notice);
	}
	
	public void deleteNoticeFile(NoticeFile noticeFile, String noticeFileName, String noticeFileExt) {
		logger.debug("deleteNoticeFile - noticeFile : " + noticeFile.toString());
		String Path = SystemPath.SYSTEM_PATH+"\\" + noticeFileName + "." + noticeFileExt;
		//path에 FileNaem과 FileExt를 담는다.
		logger.debug("deleteNoticeFile - Path : " + Path.toString());
		File file = new File(Path);
		//file에 File(Path)의 주소값을 담는다.
		logger.debug("deleteNoticeFile - file : " + file.toString());
		int noticeFileId = noticeFile.getNoticeFileId();
		//noticeFile에서 FileId를 get해와서 noticeFileId에 담는다.
		noticeFileDao.deleteNoticeFile(noticeFileId);
		//noticeFileDao를 찾아가, deleteNoticeFile을 호출한다.
		file.delete();
	}
	
	public int updateNotice(Notice notice) {
		logger.debug("updateNotice() notice" + notice);
		return noticeDao.updateNotice(notice);
	}

	public Notice updateNoticeId(int noticeId) {
		logger.debug("updateNotice() updateNoticeId" + noticeId);
		return noticeDao.updateNoticeId(noticeId);
	}
}
