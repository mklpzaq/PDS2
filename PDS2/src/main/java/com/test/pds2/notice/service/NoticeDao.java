package com.test.pds2.notice.service;

import java.util.List;
import java.util.Map;

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
		sqlSession.insert(NS + "insertNotice", notice);
		return notice.getNoticeId();
	}

	public List<Notice> selectNoticeList(Map<String, Integer> map) {
		logger.debug("selectNoticeList");
		return sqlSession.selectList(NS+"selectNoticeList", map);
	}
	
	public int totalCountNotice() {
		logger.debug("totalCountNotice");
		return sqlSession.selectOne(NS+"totalCountNotice"); // 결과값이 하나 이므로 selectOne 사용
	}
	
	public Notice noticeView(Notice notice) {
		logger.debug("noticeView - notice : "+notice.toString());
		
		return sqlSession.selectOne(NS+"selectNoticeOne", notice);
	}	
	
	public void deleteNoticeList(int noticeId) {
		sqlSession.delete(NS+"deleteNoticeList", noticeId);
	}
	
	public int deleteNotice(Notice notice) {
		logger.debug("deleteNotice - notice : "+notice.toString());
		return sqlSession.delete(NS+"deleteNotice", notice);  
	}
}
