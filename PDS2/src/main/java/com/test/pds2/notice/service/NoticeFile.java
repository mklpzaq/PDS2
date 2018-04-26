package com.test.pds2.notice.service;

public class NoticeFile {
	private int noticeFileId; //notice_file_id
	private String noticeFileName; //notice_file_name
	private String noticeFileExt; //notice_file_ext
	private String noticeFileType; //notice_file_type
	private long noticeFileSize; //notice_file_size
	private int noticeId; //notice_id
	
	
	public int getNoticeFileId() {
		return noticeFileId;
	}


	public void setNoticeFileId(int noticeFileId) {
		this.noticeFileId = noticeFileId;
	}


	public String getNoticeFileName() {
		return noticeFileName;
	}


	public void setNoticeFileName(String noticeFileName) {
		this.noticeFileName = noticeFileName;
	}


	public String getNoticeFileExt() {
		return noticeFileExt;
	}


	public void setNoticeFileExt(String noticeFileExt) {
		this.noticeFileExt = noticeFileExt;
	}


	public String getNoticeFileType() {
		return noticeFileType;
	}


	public void setNoticeFileType(String noticeFileType) {
		this.noticeFileType = noticeFileType;
	}


	public long getNoticeFileSize() {
		return noticeFileSize;
	}


	public void setNoticeFileSize(long noticeFileSize) {
		this.noticeFileSize = noticeFileSize;
	}


	public int getNoticeId() {
		return noticeId;
	}


	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}


	@Override
	public String toString() {
		return "NoticeFile [noticeFileId=" + noticeFileId + ", noticeFileName=" + noticeFileName + ", noticeFileExt="
				+ noticeFileExt + ", noticeFileType=" + noticeFileType + ", noticeFileSize=" + noticeFileSize
				+ ", noticeId=" + noticeId + "]";
	}
	
	
}
