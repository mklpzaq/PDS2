package com.test.pds2.board.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.board.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value="/insertBoard", method=RequestMethod.GET)
	public String insertBoard() {
		logger.info("GET /insertBoard BoardController");
		return "insertBoardForm";
	}
	
	@RequestMapping(value="/insertBoard", method=RequestMethod.POST)
	public String insertBoard(BoardRequest boardRequest, HttpSession session) {
		logger.info("POST /insertBoard BoardController");
		logger.info("boardRequest : " + boardRequest);
		// service : ArticleRequest => Article + 실제 파일을 폴더에 저장(일단 DB에 말고 ) 트랜젝션 서비스가 필요하다 트랜젝션을 쓰려면 설정파일 설정해야한다.
		// dao : insert 
		
		//세션이 만들어져 있는 톰켓 자체를 가리킨다.
		/*
		 * 내 안에 만들어져 있는 upload의 주소가 
		 * '/resources'/upload
		 * */
		String path = session.getServletContext().getRealPath("/resources/upload/board");
		logger.info(path);
		
		boardService.insertBoard();
			
		return "redirect:/";
	}
	
}
