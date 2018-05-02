package com.test.pds2.gallery.service;

import java.util.ArrayList;
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

	public List<Gallery> listAll(String searchOption, ArrayList<String> keyword) {
		
		return null;
	}

	public Gallery selectDetailList(Gallery gallery) {
		logger.debug("====== selectDetailList : " );
		return sqlSession.selectOne(NS+"selectGalleryDetail",gallery);		
	}

	public int updateGallery(Gallery gallery) {
		return sqlSession.update(NS+"updateGallery", gallery);
		
	}

	public Gallery selectGalleryForId(int galleryId) {
		
		return sqlSession.selectOne(NS+"selectGalleryForId", galleryId);
	}

	public int deleteGallery(int galleryId) {
		
		return sqlSession.delete(NS+"deleteGallery", galleryId);
		
	}	
}
