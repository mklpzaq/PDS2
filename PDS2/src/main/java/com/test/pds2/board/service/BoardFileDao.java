package com.test.pds2.board.service;

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
	
	public void insertBoardFile(BoardFile boardFile) {
		sqlSession.insert(NS+"insertBoardFile", boardFile);
	}
	
}
