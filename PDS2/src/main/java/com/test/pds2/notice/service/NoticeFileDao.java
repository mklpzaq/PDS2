package com.test.pds2.notice.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class NoticeFileDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(NoticeFileDao.class);
	final String NS = "com.test.pds2.notice.service.NoticeFileMapper.";
	
	//첨부파일 추가
	public int insertNoticeFile(NoticeFile noticeFile) {
		logger.debug("insertNoticeFile");
		logger.debug("NoticeFileDao.insertNoticeFile: {}"+noticeFile);
		return sqlSession.insert(NS + "insertNoticeFile", noticeFile);	
	}
	
	public List<NoticeFile> selectNoticeFile(Notice notice) {
		logger.debug("selectNoticeFile - notice : "+notice);
		 
		return sqlSession.selectList(NS+"selectNoticeFile", notice);  
	}
	//파일삭제
	public void deleteNoticeFile(int noticeFile) {
		logger.debug("deleteNoticeFile - noticeFile : "+noticeFile);
		//return sqlSession.delete(NS+"deleteNoticeFile", noticeFile);  
	}
	//첨부파일 하나 삭제
	public int deleteNoticeFileOne(NoticeFile noticeFile) {
		return sqlSession.delete(NS+"deleteNoticeFileOne", noticeFile);
	}




}
