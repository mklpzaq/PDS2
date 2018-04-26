package com.test.pds2.board.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(BoardDao.class);
	final String NS = "com.test.pds2.board.service.BoardMapper.";
	
	public Board getDetailBoard(int boardId) {
		return sqlSession.selectOne(NS+"getDetailBoard", boardId);
	}
	
	public int totalCountBoard(Map<String, Object> map) {
		return sqlSession.selectOne(NS+"totalCountBoard", map);
	}
	
	public List<Board> getBoardList(Map<String, Object> map){
		return sqlSession.selectList(NS+"getBoardList", map);
	}
	
	public void insertBoard(Board board) {
		sqlSession.insert(NS+"insertBoard", board);
		logger.debug(board.toString());
	}
	

}
