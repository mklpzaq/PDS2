package com.test.pds2.board.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
 
@Repository
public class BoardFileDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(BoardFileDao.class);
	final String NS = "com.test.pds2.board.service.BoardFileMapper.";
	
	public List<BoardFile> selectBoardFileListForDelete(int boardId){
		return sqlSession.selectList(NS+"selectBoardFileListForDelete", boardId);
	}
	
	/* boardId에 해당되는 boardFile을 모두 삭제 */
	public int deleteBoardFile(int boardId) {
		return sqlSession.delete(NS+"deleteBoardFile", boardId);
	}
	
	/* boardFileId에 해당되는 boardFile 1개를 삭제 */
	public int deleteBoardFileOne(int boardFileId) {
		return sqlSession.delete(NS+"deleteBoardFileOne", boardFileId);
	}
	
	public void insertBoardFile(BoardFile boardFile) {
		sqlSession.insert(NS+"insertBoardFile", boardFile);
	}
	
}
