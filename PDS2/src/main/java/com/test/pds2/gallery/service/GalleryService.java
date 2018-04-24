package com.test.pds2.gallery.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.test.pds2.gallery.controller.GalleryController;
import com.test.pds2.path.SystemPath;

@Service
public class GalleryService {
	/*
	 * service에서는 2개의 DAO를 처리한다.
	 */
	@Autowired 
	GalleryDao galleryDao;
	@Autowired
	GalleryFileDao galleryFileDao;
	private static final Logger logger = LoggerFactory.getLogger(GalleryController.class);
	/*
	 * 2개이상의 쿼리문을 사용 하기에 하나의 쿼리문이 오류가 나도 원래상태로 되돌리는 @Transactional을 쓴다.
	 * 아직 파일전송은 1:1 로 했다. 
	 */
	@Transactional
	public void insertGallery(GalleryRequest galleryRequest, String path) {
		MultipartFile multipartFile = galleryRequest.getMultipartfile();
		
		//Gallery의 값에 galleryRequest에서 받아온 값으로 세팅해준다.
		Gallery gallery = new Gallery();
		gallery.setGalleryTitle(galleryRequest.getGalleryTitle());
		gallery.setGalleryContent(galleryRequest.getGalleryContent());
		
		GalleryFile galleryFile = new GalleryFile();
		//16진수 유효아이디가 만들어진다?
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString();
		logger.debug("String filename : " + filename);
		//-문자를 찾아 없에고 문자열을 반환한다. 
		filename = filename.replace("-", "");
		//2.파일 확장자
		//lastIndexOf()문자열에서 마지막 문자열을 반환한다.
		//getOriginalFilename() 올린 파일의 전체 이름
		int doIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
		logger.info("int doIndex : " + doIndex);
		String fileExt = multipartFile.getOriginalFilename().substring(doIndex+1);
		logger.info("String fileExt : " + fileExt);
		
		//3.파일 컨텐트 타입
		String fileType = multipartFile.getContentType();
		logger.info("String fileType : " + fileType);		
		
		//4.파일 사이즈
		long fileSize = multipartFile.getSize();
		//5.파일 저장(매개변수 path를 이용)
		File file = new File(path+filename+fileExt);
		
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		int galleryId = galleryDao.insertGallery(gallery);
				
		galleryFile.setGalleryId(galleryId);
		galleryFile.setGalleryFileName(filename);
		galleryFile.setGalleryFileExt(fileExt);
		galleryFile.setGalleryFileType(fileType);
		galleryFile.setGalleryFileSize(doIndex);
		
		galleryFileDao.insertGalleryFile(galleryFile);
	}
}
