package com.test.pds2.board.service;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private List<BoardFile> boardFile;	//지금은 1:1, 나중에 1:N으로 구현해야 한다.

	public Board() {
		this.boardFile = new ArrayList<BoardFile>();
	}
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
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
	public List<BoardFile> getBoardFile() {
		return boardFile;
	}
	public void setBoardFile(List<BoardFile> boardFile) {
		this.boardFile = boardFile;
	}
	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", boardFile=" + boardFile + "]";
	}
}
