package com.test.pds2.resume.service;
//resume테이블
public class Resume {
	private int resumeId; //resume_id auto_increment primary
	private String resumeTitle; //resume_title 
	private String resumeContent; //resume_content
	private ResumeFile resumeFile;
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
	public ResumeFile getResumeFile() {
		return resumeFile;
	}
	public void setResumeFile(ResumeFile resumeFile) {
		this.resumeFile = resumeFile;
	}
	@Override
	public String toString() {
		return "Resume [resumeId=" + resumeId + ", resumeTitle=" + resumeTitle + ", resumeContent=" + resumeContent
				+ ", resumeFile=" + resumeFile + "]";
	}
	
	
	
}
