package com.test.pds2.notice.service;

import org.springframework.web.multipart.MultipartFile;

public class NoticeRequest {
	private String NoticeTitle;
	private String NoticeContent;
	private MultipartFile multipartFile;
	
	public String getNoticeTitle() {
		return NoticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		NoticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return NoticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		NoticeContent = noticeContent;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	@Override
	public String toString() {
		return "NoticeRequest [NoticeTitle=" + NoticeTitle + ", NoticeContent=" + NoticeContent + ", multipartFile="
				+ multipartFile + "]";
	}
	
	

}
