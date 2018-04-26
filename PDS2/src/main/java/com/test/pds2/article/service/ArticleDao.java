package com.test.pds2.article.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(ArticleDao.class);
	final String NS ="com.test.pds2.article.service.ArticleMapper.";
	
	public void insertArticle(Article article) {
		logger.debug("insertArticle ArticleDao");
		sqlSession.insert(NS+"insertArticle", article);
	}
	
	
}
