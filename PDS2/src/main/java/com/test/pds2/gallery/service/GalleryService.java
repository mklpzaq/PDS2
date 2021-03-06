package com.test.pds2.gallery.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	private static final Logger logger = LoggerFactory.getLogger(GalleryService.class);
		
	/*
	 * 2개이상의 쿼리문을 사용 하기에 하나의 쿼리문이 오류가 나도 원래상태로 되돌리는 @Transactional을 쓴다.
	 * 파일전송은 1:N 로 했다. 
	 */
	@Transactional
	public void insertGallery(GalleryRequest galleryRequest, String path) {
		List<MultipartFile> multipartFileList = galleryRequest.getMultipartfile();
		
		//Gallery의 값에 galleryRequest에서 받아온 값으로 세팅해준다.
		Gallery gallery = new Gallery();
		gallery.setGalleryTitle(galleryRequest.getGalleryTitle());
		gallery.setGalleryContent(galleryRequest.getGalleryContent());
		// 다중 파일 업로드를 위해 for문으로 반복 시킨다.
		// 파일 업로드의 갯수만큼 갯수당 파일명을 입력해주는 반복문이 필요하다.
		for(MultipartFile multipartFile : multipartFileList) {
		
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
			File file = new File(path+filename+"."+fileExt);
			
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {			
				e.printStackTrace();
			} catch (IOException e) {			
				e.printStackTrace();
			}
			
			//
			GalleryFile galleryFile = new GalleryFile();			
				
			galleryFile.setGalleryFileName(filename);
			galleryFile.setGalleryFileExt(fileExt);
			galleryFile.setGalleryFileType(fileType);
			galleryFile.setGalleryFileSize(doIndex);
			logger.debug("galleryFile : " + galleryFile);
			gallery.getGalleryFile().add(galleryFile);			
			
		}
			logger.debug("gallery : " + gallery);
			galleryDao.insertGallery(gallery);
			
			for(GalleryFile galleryFile : gallery.getGalleryFile()) {
				galleryFile.setGalleryId(gallery.getGalleryId());
				galleryFileDao.insertGalleryFile(galleryFile);
			}
	}
	
	/*
	 * Map에 넣어야 할 값이 List와 int이다. 이럴경우 모든형을 받는 Object를 쓴다.
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
	public List<Gallery> listAll(String searchOption, ArrayList<String> keyword) {
		
		return galleryDao.listAll(searchOption, keyword);
	}
	/*// 상세보기 List
	public Map<String, Object> viewDetailGallery(Gallery gallery, int currentPage, int pagePerRow) {
		logger.debug("GalleryService.viewDetailGallery");
		Map<String,Object> map = new HashMap<String,Object>();
		int beginRow = (currentPage -1)*pagePerRow;		
		map.put("beginRow", beginRow);
		map.put("pagePerRow", pagePerRow);		
		map.put("gallery", gallery.getGalleryId());
		logger.debug("GalleryService.viewDetailGallery.getGalleryId : " + gallery.getGalleryId());
		
		Gallery detailList = galleryDao.selectDetailList(map);
		int total = galleryDao.totalCountGallery();
		logger.debug("GalleryService.total  : " + total);
		
		int lastPage = 0;
		int lastPageGalleryCnt = 0;
		if(0 == total) {
			lastPage = 1;
		}else if(total%pagePerRow == 0) {
			lastPage = total/pagePerRow;			
		}else {
			lastPage = total/pagePerRow+1;
		}
		
		int temp = (currentPage -1)/5;
		int beginPageNumForCurrentPage = temp * 5 + 1;
		
		//====================
		
		
		
		Map<String,Object> returnmap = new HashMap<String, Object>();
		returnmap.put("detailList", detailList);
		returnmap.put("lastPage", lastPage);
		returnmap.put("lastPageGalleryCnt", lastPageGalleryCnt);
		returnmap.put("beginPageNumForCurrentPage", beginPageNumForCurrentPage);
		
		return returnmap;
	}*/
	
	public Gallery viewDetailGallery(Gallery gallery) {
		logger.debug("GalleryService.viewDetailGallery()");
		// DAO에서 join문 쿼리를 불러 온다.
		

		Gallery viewDetailGallery = galleryDao.selectDetailList(gallery);
		
		logger.debug("GalleryService.viewDetailGallery(). viewDetailGallery : " +  viewDetailGallery);
		return viewDetailGallery;
	}

	public Gallery updateGallery(Gallery gallery) {
		logger.debug("GalleryService.updateGallery() gallery" + gallery);
		
		return galleryDao.updateGallery(gallery);
	}

	public Gallery updateGalleryForId(int galleryId) {
		logger.debug("GalleryService.updateGallery() updateGalleryForId" + galleryId);
		return galleryDao.selectGalleryForId(galleryId);
	}

	public void deleteGallery(int galleryId, String path) {
		logger.debug("GalleryService.deleteGallery() galleryId" + galleryId);
		logger.debug("GalleryService.deleteGallery() path" + path);
		String fileName = null;
		String fileExt = null;
		
		List<GalleryFile> list = galleryFileDao.selectFileList(galleryId);
		logger.debug("list : " + list);
		for(GalleryFile galleryFile : list) {
			fileName = galleryFile.getGalleryFileName();
			fileExt = galleryFile.getGalleryFileExt();
			logger.debug("fileName : " + fileName + fileExt);
			
			File file = new File(path+fileName+"."+fileExt);
			logger.debug("삭제 전 파일 존재여부 확인"+file.exists());
			file.delete();
			logger.debug("삭제 후 파일 존재여부 확인"+file.exists());
		}
		galleryFileDao.deleteGalleryFile(galleryId);
		galleryDao.deleteGallery(galleryId);
		
	}

	public void updateGallery(int galleryId, GalleryRequest galleryRequest, String path, List<String> deleteImg) {
		logger.debug("GalleryService.updateGallery() update post 호출");
		Gallery gallery = new Gallery();
		gallery.setGalleryTitle(galleryRequest.getGalleryTitle());
		gallery.setGalleryContent(galleryRequest.getGalleryContent());
		gallery.setGalleryId(galleryId);
		galleryDao.updateGallery(gallery);
		
		List<MultipartFile> multipartFileList = galleryRequest.getMultipartfile();
		logger.debug("GalleryService.updateGallery() update post multipartFileList : " + multipartFileList);
		
		if(multipartFileList.size() != 0) {
			logger.debug("GalleryService.updateGallery() update post 호출 multipartFileList.size() : " + multipartFileList.size());
			for(MultipartFile multipartFile : multipartFileList) {
				
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
				File file = new File(path+filename+"."+fileExt);
				
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {			
					e.printStackTrace();
				} catch (IOException e) {			
					e.printStackTrace();
				}
				
				//
				GalleryFile galleryFile = new GalleryFile();			
					
				galleryFile.setGalleryFileName(filename);
				galleryFile.setGalleryFileExt(fileExt);
				galleryFile.setGalleryFileType(fileType);
				galleryFile.setGalleryFileSize(doIndex);
				logger.debug("galleryFile : " + galleryFile);
				gallery.getGalleryFile().add(galleryFile);			
				
			}				
				for(GalleryFile galleryFile : gallery.getGalleryFile()) {
					galleryFile.setGalleryId(gallery.getGalleryId());
					galleryFileDao.insertGalleryFile(galleryFile);
				}
		}
		
		if(deleteImg != null) {// 삭제되는 이미지 파일이 있다면 삭제하는 부분
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("galleryId", galleryId);
			for(String fileNameExt : deleteImg) {
				logger.debug("파일풀네임: "+fileNameExt);
				File file = new File(path+"\\"+fileNameExt);
				logger.debug("삭제 전 파일 존재여부 확인: "+file.exists());
				file.delete();
				logger.debug("삭제 후 파일 존재여부 확인: "+file.exists());
				int fileNameSize = fileNameExt.indexOf(".");
				String fileName = fileNameExt.substring(0,fileNameSize);
				map.put("galleryFileName", fileName);
				logger.debug("galleryFileMap(작성자&삭제하고자하는 파일명): "+map.toString());
				galleryFileDao.deleteImgFile(map);
			}
		}
	}
}
