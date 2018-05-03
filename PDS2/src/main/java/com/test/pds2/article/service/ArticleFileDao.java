package com.test.pds2.article.service;

import java.util.List;

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
	
	public int deleteArticleFile(int articleId) {
		return sqlSession.delete(NS+"deleteArticleFile", articleId);
	}
	
	public List<ArticleFile> selectArticleFileListForDelete(int articleId) {
		return sqlSession.selectList(NS+"selectArticleFileListForDelete", articleId);
	}
	
	public int getCountArticleFile(int articleId) {
		return sqlSession.selectOne(NS+"getCountArticleFile", articleId);
	}
	
	public int deleteArticleFileOne(int articleFileId) {
		return sqlSession.delete(NS+"deleteArticleFileOne", articleFileId);
	}
	
	public void insertArticleFile(ArticleFile articleFile) {
		sqlSession.insert(NS+"insertArticleFile", articleFile);
	}
}
