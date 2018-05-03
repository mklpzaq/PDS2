package com.test.pds2.gallery.service;

import java.util.List;
import java.util.Map;

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
		logger.debug("====== insertGalleryFile : ");
		int row = sqlSession.insert(NS+"insertgalleryFile",galleryFile);
		return row;
	}

	public List<GalleryFile> selectFileList(int galleryId) {
		logger.debug("selectFileList() galleryId " + galleryId);
		return sqlSession.selectList(NS+"selectGalleryFileList", galleryId);
	}

	public int deleteGalleryFile(int galleryId) {
		logger.debug("deleteGalleryFile() galleryId " + galleryId);
		return sqlSession.delete(NS + "deleteGalleryFile", galleryId);
	}

	public int deleteImgFile(Map<String, Object> map) {
		logger.debug("deleteImgFile map : " + map);
		return sqlSession.update(NS+"deleteImgFile", map);
		
	}
}
