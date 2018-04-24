package com.test.pds2.board.service;

import org.springframework.web.multipart.MultipartFile;

/*
 * 입력받는 모양하고 테이블 모양하고 일치하지 않는다.
 * 이러한 불일치를 서비스에서 해결하자.
 * */
public class BoardRequest {
	private String boardTitle;
	private String boardContent;
	private MultipartFile multipartFile;
	/*private File file; 스프링에서 파일로 바로 받지 말고 Spring에서 쓰는 멀티파트를 쓰자
	 * 
	 * */
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	@Override
	public String toString() {
		return "BoardRequest [boardTitle=" + boardTitle + ", boardContent=" + boardContent + ", multipartFile="
				+ multipartFile + "]";
	}
}
