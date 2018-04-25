package com.test.pds2.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.board.service.BoardService;
import com.test.pds2.path.SystemPath;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value="/getDetailBoard", method=RequestMethod.GET)
	public String getDetailBoard() {
		
		return null;
	}
	
	
	/* searchSignal 값이 1이 넘어왔을 경우는 boardList에서 '검색버튼'을 누른 경우이다.
	 * 그리고 '검색버튼'을 누르지 않았을 겨우는 defaultValue값으로 0이 들어오게 설정하였다.
	 * searchSignal : 1 => '검색버튼'을 누름
	 * searchSignal : 0 => '검색버튼'을 누르지 아니함.
	 * 
	 * 그리고 searchWord의 defaultValue 값을 ""로 설정한 이유는 '검색버튼'을 누르지 않았을경우
	 * BoardList페이지에서 오류가 생기지 않게 하기 위해서 defaultValue값을 그렇게 설정하였다.
	 *  */
	@RequestMapping(value="/getBoardList", method=RequestMethod.GET)
	public String getBoardList(Model model
								,@RequestParam(value="currentPage", defaultValue="1") int currentPage
								,@RequestParam(value="pagePerRow", defaultValue="10", required=true) int pagePerRow
								,@RequestParam(value="searchSelect", defaultValue="boardId") String searchSelect
								,@RequestParam(value="searchWord", defaultValue="") String searchWord) {
		logger.debug("GET /getBoardList BoardController");
		logger.debug("searchSelect : " + searchSelect);
		logger.debug("searchWord : " + searchWord);
		
		Map<String, Object> map = boardService.getBoardList(currentPage, pagePerRow, searchSelect, searchWord);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("beginPageNumForCurrentPage", map.get("beginPageNumForCurrentPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pagePerRow", pagePerRow);
		model.addAttribute("searchSelect", searchSelect);
		model.addAttribute("searchWord", searchWord);
		logger.debug("list : "+ map.get("list"));
		logger.debug("lastPage : "+ map.get("lastPage"));
		logger.debug("beginPageNumForCurrentPage : "+ map.get("beginPageNumForCurrentPage"));
		logger.debug("currentPage : "+ currentPage);
		logger.debug("pagePerRow : "+ pagePerRow);
		logger.debug("searchSelect : " + searchSelect);
		logger.debug("searchWord : " + searchWord);
		
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
		
		/*
		 * service : ArticleRequest => Article + 실제 파일을 폴더에 저장(일단 DB에 말고 ) 트랜젝션 서비스가 필요하다 트랜젝션을 쓰려면 설정파일 설정해야한다.
		 * dao : insert 
		 * 세션이 만들어져 있는 톰켓 자체를 가리킨다.
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
