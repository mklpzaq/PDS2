package com.test.pds2.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.test.pds2.board.service.BoardFile;
import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.board.service.BoardService;
import com.test.pds2.path.SystemPath;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value="/updateBoard", method=RequestMethod.GET)
	public String updateBoard(Model model
							,@RequestParam(value="sendNo") int boardId) {
		logger.debug("GET /updateBoard BoardController");
		logger.debug("boardId : " + boardId);
		
		//완전히 세팅된 board가 나온다.
		Board board = boardService.getDetailBoard(boardId);
		logger.debug("setted board : "+ board.toString() );
		model.addAttribute("board", board);
		
		return "/board/updateBoardForm";
	}
	
	@RequestMapping(value="/updateBoard", method=RequestMethod.POST)
	public String updateBoard(Board board) {
		logger.debug("POST /updateBoard BoardController");
		logger.debug("board : " + board);
		int result = boardService.updateBoard(board);
		
		return "redirect:/getBoardList";
	}
	
	@RequestMapping(value="/deleteBoard", method=RequestMethod.GET)
	public String deleteBoard(@RequestParam(value="sendNo") int boardId) {
		logger.debug("GET /deleteBoard deleteBoard");
		
		/* boardId에 해당되는 파일 이름 정보값(boardFileName, boardFileExt)들을 얻어와서(List로)
		 * return된 List를  deleteBoard의 매개변수로 넘겨주어야
		 * 하드드라이브에 저장되어있는 boardId에 해당되는 boardFile들을 삭제해줄 수 있다.
		 * */
		List<BoardFile> boardFileList = boardService.selectBoardFileListForDelete(boardId);
		
		/* 우선 boardId에 해당되는 파일을 모두 지우고 그 다음, boardId에 해당되는 board를 지운다. */
		//매개변수로 boardId에 해당하는 boardFileName, boardFileExt를 가지고 있는 boardFile List를 넘겨 받아야 한다.
		boardService.deleteBoard(boardId, boardFileList);
		
		return "redirect:/getBoardList";
	}
	
	@RequestMapping(value="/deleteBoardFile", method=RequestMethod.GET)
	public String deleteBoardFile(Model model
									,@RequestParam(value="sendNo") int boardId
									,@RequestParam(value="sendFileNo") int boardFileId
									,@RequestParam(value="fileName") String boardFileName
									,@RequestParam(value="fileExt") String boardFileExt) {
		logger.debug("GET /deleteBoardFile deleteBoardFile");
		/* file 삭제 과정 */
		boardService.deleteBoardFileOne(boardFileId, boardFileName, boardFileExt);
		/* boardDetailView.jsp 로 돌아가기 위한 작업 */
		Board board = boardService.getDetailBoard(boardId);
		logger.debug("board : "+ board.toString());
		model.addAttribute("detailBoard", board);
		
		return "/board/boardDetailView";
	}
	
	@RequestMapping(value="/downloadBoardFile", method=RequestMethod.GET)
	public String downloadBoardFile(Model model
							,HttpServletRequest request
							,HttpServletResponse response
							,@RequestParam(value="sendNo") int boardId
							,@RequestParam(value="fileName") String fileName
							,@RequestParam(value="fileExt") String fileExt) {
		logger.debug("GET /downloadBoardFile BoardController");
		
		/* boardDetailView.jsp 로 돌아가기 위한 사전 작업 */
		Board board = boardService.getDetailBoard(boardId);
		logger.debug("board : "+ board.toString());
		model.addAttribute("detailBoard", board);
		
		/* 파일을 다운로드 시키기 위한 작업 */
		boardService.downloadBoardFile(fileName, fileExt, request, response);
		
		return "/board/boardDetailView";
	}
	
	
	@RequestMapping(value="/getDetailBoard", method=RequestMethod.GET)
	public String getDetailBoard(Model model
								,@RequestParam(value="sendNo") int boardId) {
		
		Board board = boardService.getDetailBoard(boardId);
		logger.debug("board : "+ board.toString());
		model.addAttribute("detailBoard", board);
		
		return "/board/boardDetailView";
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
