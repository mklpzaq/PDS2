package com.test.pds2.article.service;

import java.util.List;
import java.util.Map;

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
	
	public int updateArticle(Article article) {
		return sqlSession.update(NS+"updateArticle", article);
	}
	
	public int deleteArticle(int articleId) {
		return sqlSession.delete(NS+"deleteArticle", articleId);
	}
	
	public Article getArticleOne(int articleId) {
		return sqlSession.selectOne(NS+"getArticleOne", articleId);
	}
	
	public Article getDetailArticle(int articleId) {
		return sqlSession.selectOne(NS+"getDetailArticle", articleId);
	}
	
	public int totalCountArticle(Map<String, Object> map) {
		return sqlSession.selectOne(NS+"totalCountArticle", map);
	}
	
	public List<Article> getArticleList(Map<String, Object> map){
		return sqlSession.selectList(NS+"getArticleList", map);
	}
	
	public void insertArticle(Article article) {
		logger.debug("insertArticle ArticleDao");
		sqlSession.insert(NS+"insertArticle", article);
	}
	
	
}
