package com.test.pds2.article.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleFileDao articleFileDao;
	private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
	
	public void insertArticle(ArticleRequest articleRequest, String path) {
		logger.debug("insertArticle ArticleService");
		
		Article article = new Article();
		article.setArticleTitle(articleRequest.getArticleTitle());
		article.setArticleContent(articleRequest.getArticleContent());
		logger.debug("<타이틀 컨텐트 세팅>article : " + article);
		
		List<MultipartFile> multipartFileList = articleRequest.getMultipartFile();
		
		for(MultipartFile multipartFile : multipartFileList) {
			
			/*
			 * 1. 파일 이름
			 * */
			UUID uuid = UUID.randomUUID();
			logger.debug("uuid : " + uuid);
			String filename = uuid.toString();
			logger.debug("filename : " + filename);
			filename = filename.replace("-", "");
			logger.debug("replaced filename : " + filename);
			
			/*
			 * 2. 파일 확장자
			 * */
			logger.debug("originaFileName : " + multipartFile.getOriginalFilename());
			int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
			String fileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
			logger.debug("fileExt : " + fileExt);
			
			/*
			 * 3. 파일 타일
			 * */
			String fileType = multipartFile.getContentType();
			logger.debug("fileType : " + fileType);
			
			/*
			 * 4. 파일 사이즈
			 * */
			long longFileSize = multipartFile.getSize();
			logger.debug("longFileSize : " + longFileSize);
			int fileSize = (int)longFileSize;
			logger.debug("fileSize : " + fileSize);
			
			/*
			 * 5. 파일 저장(매개변수 path를 사용)
			 * File file = new File(path+"/"+filename+"."+fileExt);
			 * */
			logger.debug("파일 저장 경로 : " + path+filename+"."+fileExt);
			File file = new File(path+filename+"."+fileExt);
			
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArticleFile articleFile = new ArticleFile();
			
			articleFile.setArticleFileName(filename);
			articleFile.setArticleFileExt(fileExt);
			articleFile.setArticleFileType(fileType);
			articleFile.setArticleFileSize(fileSize);
			logger.debug("articleFile : " + articleFile);
			
			article.getArticleFile().add(articleFile);
		}
		
		logger.debug("Before article : " + article);
		articleDao.insertArticle(article);
		logger.debug("After article : " + article);
		
		
		int articleId = article.getArticleId();
		logger.debug("articleId : " + articleId);
		for(ArticleFile articleFile : article.getArticleFile()) {
			articleFile.setArticleId(articleId);
			logger.debug("articleFile.getArticleId() : " + articleFile.getArticleId());
			articleFileDao.insertArticleFile(articleFile);
		}
	}
}


















