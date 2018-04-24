package com.test.pds2.notice.service;

public class Notice {
	private int noticeId; // notice_id
	private String noticeTitle; //notice_title
	private String noticeContent; //notice_content
	private NoticeFile noticeFile; // or private ArticleFile[] articleFile; 현재 1:1로 구성, 나중에 1:N으로 바꿔야함, command객체
	
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
	
	public NoticeFile getNoticeFile() {
		return noticeFile;
	}
	public void setNoticeFile(NoticeFile noticeFile) {
		this.noticeFile = noticeFile;
	}
	@Override
	public String toString() {
		return "Notice [noticeId=" + noticeId + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ "]";
	}
}
