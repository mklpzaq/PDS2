package com.test.pds2.gallery.service;

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
	/*
	 * Map에 넣어야 할 값이 List와 int이다. 이럴경우 모든형은 받는 Object를 쓴다.
	 */
	public Map<String, Object> galleryList(int currentPage, int pagePerRow) {
		logger.debug("GalleryService.galleryList");
		//처음 시작하는 줄의 값을 구하는 방법이다. 처음 시작줄 : beginRow이 되고 끝은 pagePerRow 가 될것이다.
		Map<String, Integer> map = new HashMap<String, Integer>();
		int beginRow = (currentPage-1)*pagePerRow;
		
		map.put("beginRow", beginRow);
		map.put("pagePerRow", pagePerRow);
		// Gallery에 가입된 값들을 리스트 형태로 받아온다. 
		List<Gallery> list = galleryDao.selectGalleryList(map);
		logger.debug("GalleryService.List<Gallery> list : " + list);
		// Gallery에 속한 값을 세서 총 값을 받는다.
		int total = galleryDao.totalCountGallery();
		logger.debug("GalleryService.total  : " + total);
		// 마지막 페이지는 일단 0이라고 초기화 시킨다.
		int lastPage = 0;
		int lastPageGalleryCnt = 0;
		if(0 == total) {
			// 총 갯수가 0일때 현제 페이지는 1이여야 한다.
			lastPage = 1;
			// 총 갯수와 10(pagePerRow)개 로 나눈 나머지 값이 딱 떨어 졌을때
			// 마지막 페이지는 나눈 몫값이 되게 한다.
		}else if(total%pagePerRow == 0) {
			lastPage = total/pagePerRow;
			// 나머지 범위는 마지막 페이지에 1을 더한 값이 되어야 한다.
		}else {
			lastPage = total/pagePerRow+1;
		}
			// 한 페이지에 보여주는 갯수를 5개 단위로 짜를때 쓴다.
			// 5개 단위로 나눈 처음 페이지 값
		int temp = (currentPage -1)/5;
		int beginPageNumForCurrentPage = temp * 5 + 1;
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("lastPage", lastPage);
		returnMap.put("lastPageGalleryCnt", lastPageGalleryCnt);
		returnMap.put("beginPageNumForCurrentPage", beginPageNumForCurrentPage);
		
		return returnMap;
	}
}
