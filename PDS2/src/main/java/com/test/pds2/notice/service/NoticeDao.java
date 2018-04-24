package com.test.pds2.notice.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(NoticeDao.class);
	final String NS = "com.test.pds2.notice.service.NoticeMapper.";
	
	public int insertNotice(Notice notice) {
		logger.debug("insertNotice"); //info는 사용자에게 노출, debug는 개발자까지 노출
		return sqlSession.insert(NS + "insertNotice", notice);
	}
	
	
}
