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

	public int insertNoticeFile(NoticeFile noticeFile) {
		logger.debug("insertNoticeFile");
		logger.debug("NoticeFileDao.insertNoticeFile: {}"+noticeFile);
		return sqlSession.insert(NS + "insertNoticeFile", noticeFile);	
	}
	
	public List<NoticeFile> selectNoticeFile(Notice notice) {
		logger.debug("selectNoticeFile - notice : "+notice.toString());
		 
		return sqlSession.selectList(NS+"selectNoticeFile", notice);  
	}
	//뷰에서 첨부파일삭제
	public int deleteNoticeFile(int noticeFileId) {
		logger.debug("deleteNoticeFile - noticeFileId : "+noticeFileId);
		return sqlSession.delete(NS+"deleteNoticeFile", noticeFileId);  
	}
/*	//수정화면에서 첨부파일 한개 삭제
	public int deleteNoticeFileOne(int noticeFileId) {
		return sqlSession.delete(NS + "deleteNoticeFile", noticeFileId);
	}
*/
}
