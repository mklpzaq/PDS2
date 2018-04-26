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
import org.springframework.web.multipart.MultipartFile;

import com.test.pds2.resume.service.Resume;


@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private NoticeFileDao noticeFileDao;
	private String noticeFileName;
	private String noticeFileExt;
	private String noticeFileType;
	private long noticeFileSize;
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
	
	public void insertNotice(NoticeRequest noticeRequest, String path) {
		MultipartFile multipartFile = noticeRequest.getMultipartFile();
		
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeRequest.getNoticeTitle());
		notice.setNoticeContent(noticeRequest.getNoticeContent());
		
		NoticeFile noticeFile = new NoticeFile();
		int noticeId= noticeDao.insertNotice(notice); 
		
		//1.파일이름
		UUID uuid = UUID.randomUUID(); // 16진수 uuid 생성
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
		File file = new File("d:\\upload\\"+filename+fileExt);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//셋팅
		noticeFile.setNoticeId(noticeId);
		noticeFile.setNoticeFileName(noticeFileName);
		noticeFile.setNoticeFileExt(noticeFileExt);
		noticeFile.setNoticeFileType(noticeFileType);
		noticeFile.setNoticeFileSize(noticeFileSize);
		noticeFileDao.insertNoticeFile(noticeFile);
	}
	
	public Map<String, Object> selectNoticeList(int currentPage, int pagePerRow) {
		logger.debug("selectNoticeList");
		Map<String, Integer> map = new HashMap<String, Integer>();
		int beginRow = (currentPage-1)*pagePerRow; //페이지의 첫번째 행을 지정해줌
		map.put("beginRow", beginRow);
		map.put("pagePerRow", pagePerRow);
		List<Notice> list = noticeDao.selectNoticeList(map);
		logger.debug("selectNoticeList - list.get(1).getNoticeId() : "+list.get(1).getNoticeId());
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
		logger.debug("noticeView : " + notice);
		Notice noticeView = noticeDao.noticeView(notice);
		logger.debug("noticeView : " + noticeView.toString());
		return noticeView;
	}
}
