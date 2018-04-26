package com.test.pds2.article.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleFileDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(ArticleFileDao.class);
	final String NS ="com.test.pds2.article.service.ArticleFileMapper.";
	
	public void insertArticleFile(ArticleFile articleFile) {
		sqlSession.insert(NS+"insertArticleFile", articleFile);
	}
}
