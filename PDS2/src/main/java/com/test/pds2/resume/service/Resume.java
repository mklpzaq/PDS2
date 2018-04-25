package com.test.pds2.resume.service;

import java.util.List;

//resume테이블
public class Resume {
	private int resumeId; //resume_id auto_increment primary
	private String resumeTitle; //resume_title 
	private String resumeContent; //resume_content
	private List<ResumeFile> resumeFile;
	public int getResumeId() {
		return resumeId;
	}
	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}
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
	public List<ResumeFile> getResumeFile() {
		return resumeFile;
	}
	public void setResumeFile(List<ResumeFile> resumeFile) {
		this.resumeFile = resumeFile;
	}
	@Override
	public String toString() {
		return "Resume [resumeId=" + resumeId + ", resumeTitle=" + resumeTitle + ", resumeContent=" + resumeContent
				+ ", resumeFile=" + resumeFile + "]";
	}
	
	
	
	
}
