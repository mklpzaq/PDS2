package com.test.pds2.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.board.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value="/insertBoard", method=RequestMethod.GET)
	public String insertBoard() {
		logger.info("GET /insertBoard BoardController");
		boardService.insertBoard();
			
		return "home";
	}
	
}
