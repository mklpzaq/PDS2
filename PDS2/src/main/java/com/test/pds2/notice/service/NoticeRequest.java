package com.test.pds2.notice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class NoticeRequest {
	private String NoticeTitle;
	private String NoticeContent;
	private List<MultipartFile> multipartFile;
	
	
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

	public List<MultipartFile> getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(List<MultipartFile> multipartFile) {
		this.multipartFile = multipartFile;
	}

	@Override
	public String toString() {
		return "NoticeRequest [NoticeTitle=" + NoticeTitle + ", NoticeContent=" + NoticeContent + ", multipartFile="
				+ multipartFile + "]";
	}

}
