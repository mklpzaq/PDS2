package com.test.pds2.notice.service;

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

	public int insertNoticeFile(NoticeFile noticeFile) {
		logger.debug("insertNoticeFile");
		logger.debug("NoticeFileDao.insertNoticeFile: {}"+noticeFile);
		return sqlSession.insert(NS + "insertNoticeFile", noticeFile);
		
	}
}
