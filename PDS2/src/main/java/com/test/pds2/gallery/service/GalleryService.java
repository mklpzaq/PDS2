package com.test.pds2.gallery.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GalleryService {
	
	@Autowired 
	GalleryDao galleryDao;
	
	public void insertGallery(GalleryRequest galleryRequest, String path) {
		MultipartFile multipartFile = galleryRequest.getMultipartfile();
		
		Gallery gallery = new Gallery();
		gallery.setGalleryTitle(galleryRequest.getGalleryTitle());
		gallery.setGalleryContent(galleryRequest.getGalleryContent());
		
		GalleryFile galleryFile = new GalleryFile();
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString();
		filename = filename.replace("-", "");
		//2.파일 확장자
		int doIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
		String fileExt = multipartFile.getOriginalFilename().substring(doIndex+1);
		
		//3.파일 컨텐트 타입
		String fileType = multipartFile.getContentType();
		System.out.println("fileType : " + fileType);
		
		//4.파일 사이즈
		long fileSize = multipartFile.getSize();
		//5.파일 저장(매개변수 path를 이용)
		File file = new File("d:\\upload\\"+filename+fileExt);
		
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
				
		galleryFile.setGalleryId(galleryDao.insertGallery(gallery));
		galleryFile.setGalleryFileName(filename);
		galleryFile.setGalleryFileExt(fileExt);
		galleryFile.setGalleryFileType(fileType);
		galleryFile.setGalleryFileSize(doIndex);
		
		galleryDao.insertGalleryFile(galleryFile);
	}
}
