package com.test.pds2.gallery.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GalleryFileDao {
	private static final Logger logger = LoggerFactory.getLogger(GalleryFileDao.class);
	@Autowired
	private SqlSessionTemplate sqlSession;
	final String NS ="com.test.pds2.gallery.service.GalleryFileMapper.";
	
	public int insertGalleryFile(GalleryFile galleryFile) {
		logger.info("====== insertGalleryFile : ");
		int row = sqlSession.insert(NS+"insertgalleryFile",galleryFile);
		return row;
	}
}
