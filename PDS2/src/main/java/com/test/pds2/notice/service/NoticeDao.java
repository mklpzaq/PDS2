package com.test.pds2.notice.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.pds2.gallery.service.Gallery;


@Repository
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(NoticeDao.class);
	final String NS = "com.test.pds2.notice.service.NoticeMapper.";

	//공지 추가
	public int insertNotice(Notice notice) {
		logger.debug("insertNotice"); //info는 사용자에게 노출, debug는 개발자까지 노출
		sqlSession.insert(NS + "insertNotice", notice);
		return notice.getNoticeId();
	}

	//추가된 공지를 리스트에 나타냄
	public List<Notice> selectNoticeList(Map<String, Object> map) {
		logger.debug("selectNoticeList");
		return sqlSession.selectList(NS+"selectNoticeList", map);
	}
	
	//리스트화면 페이징
	public int totalCountNotice() {
		logger.debug("totalCountNotice");
		return sqlSession.selectOne(NS+"totalCountNotice"); // 결과값이 하나 이므로 selectOne 사용
	}
	
	//공지 상세화면
	public Notice noticeView(Notice noticeId) {		
		return sqlSession.selectOne(NS+"selectNoticeOne", noticeId);
	}
	
	
	

	//리스트에서 게시글 삭제
	public void deleteNoticeList(int noticeId) {
		sqlSession.delete(NS+"deleteNoticeList", noticeId);
	}
	//뷰에서 게시물 삭제
	public int deleteNotice(Notice notice) {
		logger.debug("deleteNotice - notice : "+notice.toString());
		return sqlSession.delete(NS+"deleteNotice", notice);  
	}	
	
	public int updateNotice(Notice notice) {
		return sqlSession.update(NS+"updateNotice", notice);
		
	}

	public Notice selectNoticeview(int notice) {
		
		return sqlSession.selectOne(NS+"selectNoticeOne", notice);
	}	
}
