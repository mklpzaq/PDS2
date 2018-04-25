package com.test.pds2.gallery.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GalleryDao {
	private static final Logger logger = LoggerFactory.getLogger(GalleryDao.class);
	@Autowired
	private SqlSessionTemplate sqlSession;
	final String NS ="com.test.pds2.gallery.service.GalleryMapper.";
	
	public List<Gallery> selectGalleryList(Map<String, Integer> map){
		return sqlSession.selectList(NS+"selectGalleryList", map);
	}
	
	public int insertGallery(Gallery gallery) {
		logger.info("====== insertGallery : ");
		int row = sqlSession.insert(NS+"insertGallery",gallery);
		return row;
	}

	public int totalCountGallery() {		
		return sqlSession.selectOne(NS+"totalCountGallery");
	}	
}
