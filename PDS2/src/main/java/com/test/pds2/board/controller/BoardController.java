package com.test.pds2.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pds2.board.service.Board;
import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.board.service.BoardService;
import com.test.pds2.path.SystemPath;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	
	@RequestMapping(value="/getBoardList", method=RequestMethod.GET)
	public String getBoardList(Model model
								,@RequestParam(value="currentPage", defaultValue="1") int currentPage
								,@RequestParam(value="pagePerRow", defaultValue="10", required=true) int pagePerRow
								,@RequestParam(value="searchSelect", defaultValue="addressNo") String searchSelect
								,@RequestParam(value="searchWord", defaultValue="") String searchWord) {
		logger.debug("GET /getBoardList BoardController");
		logger.debug("searchSelect : " + searchSelect);
		logger.debug("searchWord : " + searchWord);
		
		
		List<Board> list = boardService.getBoardList();
		model.addAttribute("boardList", list);
		
		
		
		
		
		return "/board/boardList";
	}
	
	@RequestMapping(value="/insertBoard", method=RequestMethod.GET)
	public String insertBoard() {
		logger.debug("GET /insertBoard BoardController");
		return "/board/insertBoardForm";
	}
	
	@RequestMapping(value="/insertBoard", method=RequestMethod.POST)
	public String insertBoard(BoardRequest boardRequest, HttpSession session) {
		logger.debug("POST /insertBoard BoardController");
		logger.debug("boardRequest : " + boardRequest);
		// service : ArticleRequest => Article + 실제 파일을 폴더에 저장(일단 DB에 말고 ) 트랜젝션 서비스가 필요하다 트랜젝션을 쓰려면 설정파일 설정해야한다.
		// dao : insert 
		
		//세션이 만들어져 있는 톰켓 자체를 가리킨다.
		/*
		 * 내 안에 만들어져 있는 upload의 주소가 
		 * '/resources'/upload
		 * */
		//String path = session.getServletContext().getRealPath("/resources/upload");
		String path = SystemPath.SYSTEM_PATH;
		logger.debug("path : " + path);
		
		boardService.insertBoard(boardRequest, path);
			
		return "redirect:/getBoardList";
	}
	
}
