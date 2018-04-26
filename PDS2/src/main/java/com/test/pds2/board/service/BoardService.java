package com.test.pds2.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private BoardFileDao boardFileDao;
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	public void getDetailBoard(int boardId) {
		logger.debug("getDetailBoard BoardService");
		//Board board = boardFileDao.getDetailBoard(boardId);
	}
	
	public Map<String, Object> getBoardList(int currentPage, int pagePerRow, String searchSelect, String searchWord){
		logger.debug("getBoardList BoardService");
		int beginRow = (currentPage-1)*pagePerRow;
		
		/*Map<String, Integer> map = new HashMap<String, Integer>();*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginRow", beginRow);
		map.put("pagePerRow", pagePerRow);
		logger.debug("currentPage :" + currentPage);
		logger.debug("beginRow :" + beginRow);
		logger.debug("pagePerRow :" + pagePerRow);
		
		/* searchSignal : 1 일경우 '검색버튼'을 누른경우가 되므로 
		 * selectAddressList() 메서드를 사용하여 list를 가져올때,
		 * searchWord와 일치하는 레코드 부분만 list에 저장하게 만들어 주어야 한다.
		 * 그러기 위해서는 searchWord 값 정보와
		 * searchSignal 정보에 따른 분기가 필요할지 모르므로 map에 넣어준다. 
		 * */
		
		map.put("searchSelect", searchSelect);
		map.put("searchWord", searchWord);
		logger.debug("searchSelect :" + searchSelect);
		logger.debug("searchWord :" + searchWord);
		
		/* beginRow와 pagePerRow값에 따라 SQL문의 LIMIT문이 작동될 것이고
		 * 그에 맞는 list가 반환된다.
		 * 검색 또한 마찬가지로 검색결과에 맞는 레코드들이
		 * SQL문의 LIMIT문에 의해 제한되어 list에 저장된다.
		 * (pagePerRow값이 10이면 list에 담기는 개수는 10개 레코드이다.)
		 * */
		List<Board> list = boardDao.getBoardList(map);
		
		/* 검색을 하였다면 검색조건에 맞는 레코드 개수가 반환되고,
		 * 검색을 하지 않았다면 DB에 존재하는 모든 address 레코드 개수가 반환된다. 
		 *  */
		int total = boardDao.totalCountBoard(map);
		
		/* DB에 address 레코드 수가 1개도 존재하지 않는 경우 == 초기상태일때, 1페이지로 나오게 lastPage를 1로 초기화 한다.*/
		int lastPage = 0;
		if(0 == total) {
			lastPage = 1;
		}else if(total%pagePerRow == 0) {
			lastPage = total/pagePerRow;
		}else {
			lastPage = total/pagePerRow + 1;
		}
		/* 페이지가 5개 단위씩 보이게 하는 계산식 */
		int temp = (currentPage - 1)/5;
		int beginPageNumForCurrentPage = temp * 5 + 1;
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("lastPage", lastPage);
		returnMap.put("beginPageNumForCurrentPage", beginPageNumForCurrentPage);
		
		return returnMap;

	}
	
	@Transactional
	public void insertBoard(BoardRequest boardRequest, String path) {
		
		//MultipartFile multipartFile = boardRequest.getMultipartFile();
		
		
		Board board = new Board();
		board.setBoardTitle(boardRequest.getBoardTitle());
		board.setBoardContent(boardRequest.getBoardContent());
		
		List<MultipartFile> multipartFileList = boardRequest.getMultipartFile();
		//List<BoardFile> boardFileList = new ArrayList<BoardFile>();
		for(MultipartFile multipartFile : multipartFileList) {
			
			
			
			/* 
			 * 1. 파일 이름
			 * 16진수의 UUID타입이 만들어진다.
			 * - 문자를 아무것도 없는 문자로 변경시킨다.
			 * */
			UUID uuid = UUID.randomUUID();
			logger.debug("uuid : " + uuid);
			String filename = uuid.toString();
			logger.debug("filename : " + filename);
			filename = filename.replace("-", "");
			logger.debug("repalced filename : " + filename);
			
			
			/*
			 * 2. 파일 확장자
			 * */
			logger.debug("originalFilename : " + multipartFile.getOriginalFilename());
			int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
			String fileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
			logger.debug("fileExt : " + fileExt);
			
			
			/*
			 * 3. 파일 타입
			 * */
			String fileType = multipartFile.getContentType();
			logger.debug("fileType : " + fileType);
			
			
			/*
			 * 4. 파일 사이즈 
			 * */
			long longFileSize = multipartFile.getSize();
			logger.debug("longFileSize : " + longFileSize);
			int fileSize = (int)longFileSize;
			logger.debug("fileSize : " + fileSize);
			
			
			/*
			 * 5. 파일 저장(매개변수 path를 사용)
			 * File file = new File(path+"/"+filename+"."+fileExt);
			 * */
			//File file = new File("d:\\upload\\"+filename+"."+fileExt);
			logger.debug("파일 저장 경로 : " + path+filename+"."+fileExt);
			File file = new File(path+filename+"."+fileExt);
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			/* 
			 * boardRequest가 multipartFile을 가지고 있다.
			 * multipartFile -> articleFile의 내용으로 맞추어 준다.
			 *  */
			BoardFile boardFile = new BoardFile();
			
			boardFile.setBoardFileName(filename);
			boardFile.setBoardFileExt(fileExt);
			boardFile.setBoardFileType(fileType);
			boardFile.setBoardFileSize(fileSize);
			logger.debug("boardFile : " + boardFile);
			
			
			board.getBoardFile().add(boardFile);
			//board.setBoardFile(boardFile);
		}
		
		
		logger.debug("board : " + board);
		boardDao.insertBoard(board);
		
		for(BoardFile boardFile : board.getBoardFile()) {
			boardFile.setBoardId(board.getBoardId());
			boardFileDao.insertBoardFile(boardFile);
			logger.debug("boardFile.getBoardId() : " + boardFile.getBoardId());
		}
		//board.getBoardFile().setBoardId(board.getBoardId());
		
		//logger.debug("board.getBoardFile().getBoardId() : " + board.getBoardFile().getBoardId());
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		//boardFileDao.insertBoardFile(board.getBoardFile());
		
		
	}
}



















