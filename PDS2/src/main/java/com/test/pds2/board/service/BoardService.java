package com.test.pds2.board.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	public void insertBoard(BoardRequest boardRequest, String path) {
		MultipartFile multipartFile = boardRequest.getMultipartFile();
		
		Board board = new Board();
		board.setBoardTitle(boardRequest.getBoardTitle());
		board.setBoardContent(boardRequest.getBoardContent());
		/* 
		 * boardRequest가 multipartFile을 가지고 있다.
		 * multipartFile -> articleFile의 내용으로 맞추어 준다.
		 *  */
		BoardFile boardFile = new BoardFile();
		
		/* 
		 * 1. 파일 이름
		 * 16진수의 UUID타입이 만들어진다.
		 * - 문자를 아무것도 없는 문자로 변경시킨다.
		 * */
		UUID uuid = UUID.randomUUID();
		logger.info("uuid : " + uuid);
		String filename = uuid.toString();
		logger.info("filename : " + filename);
		filename = filename.replace("-", "");
		logger.info("repalced filename : " + filename);
		boardFile.setBoardFileName(filename);
		
		/*
		 * 2. 파일 확장자
		 * */
		logger.info("originalFilename : " + multipartFile.getOriginalFilename());
		int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
		String fileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
		logger.info("fileExt : " + fileExt);
		boardFile.setBoardFileExt(fileExt);
		
		/*
		 * 3. 파일 타입
		 * */
		String fileType = multipartFile.getContentType();
		logger.info("fileType : " + fileType);
		boardFile.setBoardFileType(fileType);
		
		/*
		 * 4. 파일 사이즈 
		 * */
		long longFileSize = multipartFile.getSize();
		logger.info("longFileSize : " + longFileSize);
		int fileSize = (int)longFileSize;
		logger.info("fileSize : " + fileSize);
		boardFile.setBoardFileSize(fileSize);
		
		/*
		 * 5. 파일 저장(매개변수 path를 사용)
		 * File file = new File(path+"/"+filename+"."+fileExt);
		 * */
		File file = new File("d:\\upload\\"+filename+"."+fileExt);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//board.id = 0;
		board.setBoardFile(boardFile);
		boardDao.insertBoard(board);
		board.getBoardFile().setBoardId(board.getBoardId());
		logger.info("board.getBoardFile().getBoardId() : " + board.getBoardFile().getBoardId());
		boardDao.insertBoardFile(board.getBoardFile());
		//board.id = 7;
		
	}
}



















