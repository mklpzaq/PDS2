package com.test.pds2.notice.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.pds2.article.service.ArticleDao;

@Repository
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(ArticleDao.class);
	final String NS = "com.test.pds2.notice.service.NoticeMapper.";
	
	public int insertNotice(Notice notice) {
		logger.info("insertNotice");
		return sqlSession.insert(NS + "insertNotice", notice);
	}
	
	public int insertNoticeFile(NoticeFile noticeFile) {
		logger.info("insertNoticeFile");
		sqlSession.insert(NS + "insertNoticeFile", noticeFile);
		return 0;
	}
}
