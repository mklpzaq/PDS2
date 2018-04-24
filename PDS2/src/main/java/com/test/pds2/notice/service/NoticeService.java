package com.test.pds2.notice.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.pds2.board.service.BoardDao;
import com.test.pds2.board.service.BoardService;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
	
	public void insertNotice(NoticeRequest noticeRequest, String path) {
		MultipartFile multipartFile = noticeRequest.getMultipartFile();
		
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeRequest.getNoticeTitle());
		notice.setNoticeContent(noticeRequest.getNoticeContent());
		
		NoticeFile noticeFile = new NoticeFile();
		
		//1.파일이름
		UUID uuid = UUID.randomUUID(); // 16진수 uuid 생성
		String filename = uuid.toString();
		filename = filename.replace("-", "");
		System.out.println("filename : " + filename);
		noticeFile.setNoticeFileName(filename);
		
		//2.파일확장자
		int doIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
		String fileExt = multipartFile.getOriginalFilename().substring(doIndex + 1);
		noticeFile.setNoticeFileExt(fileExt);
		
		//3.파일컨텐트 타입
		String fileType = multipartFile.getContentType();
		System.out.println("fileType : " + fileType);
		noticeFile.setNoticeFileType(fileType);
		
		//4.파일사이즈
		long fileSize = multipartFile.getSize();
		System.out.println("fileSize : " + fileSize);
		noticeFile.setNoticeFileSize(fileSize);
		
		//5.파일저장(매개변수 path를 이용하여 저장장소를 지정해준다. path+"\\"+filename + "."+fileExt)
		File file = new File(path+"\\"+filename + "."+fileExt);;
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
		notice.setNoticeFile(noticeFile);
		notice.getNoticeFile().setNoticeId(notice.getNoticeId());
		noticeDao.insertNotice(notice);
		noticeDao.insertNoticeFile(notice.getNoticeFile());
		
	}
}
