package com.test.pds2.resume.service;

//resume_file테이블
public class ResumeFile {
	
		private int resumeFileId;	//resume_file_id 컬럼
		private int resumeId;	//resume_id 컬럼
		private String resumeFileName;	//resume_file_name 컬럼
		private String resumeFileExt;	//resume_file_ext 컬럼
		private String resumeFileType;	//resume_file_type 컬럼
		private long resumeFileSize;	//resume_file_size 컬럼
		
		public int getResumeFileId() {
			return resumeFileId;
		}
		public void setResumeFileId(int resumeFileId) {
			this.resumeFileId = resumeFileId;
		}
		public int getResumeId() {
			return resumeId;
		}
		public void setResumeId(int resumeId) {
			this.resumeId = resumeId;
		}
		public String getResumeFileName() {
			return resumeFileName;
		}
		public void setResumeFileName(String resumeFileName) {
			this.resumeFileName = resumeFileName;
		}
		public String getResumeFileExt() {
			return resumeFileExt;
		}
		public void setResumeFileExt(String resumeFileExt) {
			this.resumeFileExt = resumeFileExt;
		}
		public String getResumeFileType() {
			return resumeFileType;
		}
		public void setResumeFileType(String resumeFileType) {
			this.resumeFileType = resumeFileType;
		}
		public long getResumeFileSize() {
			return resumeFileSize;
		}
		public void setResumeFileSize(long resumeFileSize) {
			this.resumeFileSize = resumeFileSize;
		}
		@Override
		public String toString() {
			return "ResumeFile [resumeFileId=" + resumeFileId + ", resumeId=" + resumeId + ", resumeFileName="
					+ resumeFileName + ", resumeFileExt=" + resumeFileExt + ", resumeFileType=" + resumeFileType
					+ ", resumeFileSize=" + resumeFileSize + "]";
		}
		
		
		
		
}
