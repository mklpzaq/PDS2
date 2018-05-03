package com.test.pds2.notice.service;

import java.util.List;

public class Notice {
	private int noticeId; // notice_id
	private String noticeTitle; //notice_title
	private String noticeContent; //notice_content
	private List<NoticeFile> noticeFile; //1:N 구현해야함
	
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	
	public List<NoticeFile> getNoticeFile() {
		return noticeFile;
	}
	public void setNoticeFile(List<NoticeFile> noticeFile) {
		this.noticeFile = noticeFile;
	}
	@Override
	public String toString() {
		return "Notice [noticeId=" + noticeId + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ "]";
	}
}
