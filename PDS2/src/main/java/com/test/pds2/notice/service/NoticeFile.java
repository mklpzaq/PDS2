package com.test.pds2.notice.service;

public class NoticeFile {
	private int noticeId; //notice_id
	private String noticeName; //notice_name
	private String noticeExt; //notice_ext
	private String noticeType; //notice_type
	private int noticeSize; //notice_size
	
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getNoticeExt() {
		return noticeExt;
	}
	public void setNoticeExt(String noticeExt) {
		this.noticeExt = noticeExt;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public int getNoticeSize() {
		return noticeSize;
	}
	public void setNoticeSize(int noticeSize) {
		this.noticeSize = noticeSize;
	}
}
