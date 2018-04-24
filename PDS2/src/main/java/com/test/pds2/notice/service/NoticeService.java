package com.test.pds2.notice.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticeService {
	
	public void addNotice(NoticeRequest noticeRequest, String path) {
		MultipartFile multipartFile = noticeRequest.getMultipartFile();
		
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeRequest.getNoticeTitle());
		notice.setNoticeContent(noticeRequest.getNoticeContent());
		
		NoticeFile noticeFile = new NoticeFile();
		
		//1.파일이름
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString();
		filename = filename.replace("-", "");
		System.out.println("filename" + filename);
		
		//2.파일확장자
		int doIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
		String fileExt = multipartFile.getOriginalFilename().substring(doIndex + 1);
				
		//3.파일컨텐트 타입
		String fileType = multipartFile.getContentType();
		System.out.println("fileType : " + fileType);
		
		//4.파일사이즈
		long fileSize = multipartFile.getSize();
		System.out.println("fileSize : " + fileSize);
		
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
	}
}
