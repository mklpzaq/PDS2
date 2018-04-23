package com.test.pds2.resume.service;

import org.springframework.web.multipart.MultipartFile;

public class ResumeRequest {
	private String resumeTitle;
	private String resumeContent;
	private MultipartFile multipartFile;
	
	public String getResumeTitle() {
		return resumeTitle;
	}
	public void setResumeTitle(String resumeTitle) {
		this.resumeTitle = resumeTitle;
	}
	public String getResumeContent() {
		return resumeContent;
	}
	public void setResumeContent(String resumeContent) {
		this.resumeContent = resumeContent;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	@Override
	public String toString() {
		return "ResumeRequest [resumeTitle=" + resumeTitle + ", resumeContent=" + resumeContent + ", multipartFile="
				+ multipartFile + "]";
	}
	
	
}
