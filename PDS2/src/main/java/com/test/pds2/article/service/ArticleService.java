package com.test.pds2.article.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
	
	public void insertArticle() {
		articleDao.insertArticle();
	}
}
