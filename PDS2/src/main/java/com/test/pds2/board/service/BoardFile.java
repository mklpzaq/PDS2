package com.test.pds2.board.service;

public class BoardFile {
	private int boardFileId;
	private int boardId;
	private String boardFileName;
	private String boardFileExt;
	private String boardFileType;
	private int boardFileSize;
	
	public int getBoardFileId() {
		return boardFileId;
	}
	public void setBoardFileId(int boardFileId) {
		this.boardFileId = boardFileId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardFileName() {
		return boardFileName;
	}
	public void setBoardFileName(String boardFileName) {
		this.boardFileName = boardFileName;
	}
	public String getBoardFileExt() {
		return boardFileExt;
	}
	public void setBoardFileExt(String boardFileExt) {
		this.boardFileExt = boardFileExt;
	}
	public String getBoardFileType() {
		return boardFileType;
	}
	public void setBoardFileType(String boardFileType) {
		this.boardFileType = boardFileType;
	}
	public int getBoardFileSize() {
		return boardFileSize;
	}
	public void setBoardFileSize(int boardFileSize) {
		this.boardFileSize = boardFileSize;
	}
	@Override
	public String toString() {
		return "BoardFile [boardFileId=" + boardFileId + ", boardId=" + boardId + ", boardFileName=" + boardFileName
				+ ", boardFileExt=" + boardFileExt + ", boardFileType=" + boardFileType + ", boardFileSize="
				+ boardFileSize + "]";
	}
}
