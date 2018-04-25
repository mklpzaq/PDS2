package com.test.pds2.article.service;

public class ArticleFile {
	private int articleFileId;
	private int articleId;
	private String articleFileName;
	private String articleFileExt;
	private String articleFileType;
	private int articleFileSize;
	
	public int getArticleFileId() {
		return articleFileId;
	}
	public void setArticleFileId(int articleFileId) {
		this.articleFileId = articleFileId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getArticleFileName() {
		return articleFileName;
	}
	public void setArticleFileName(String articleFileName) {
		this.articleFileName = articleFileName;
	}
	public String getArticleFileExt() {
		return articleFileExt;
	}
	public void setArticleFileExt(String articleFileExt) {
		this.articleFileExt = articleFileExt;
	}
	public String getArticleFileType() {
		return articleFileType;
	}
	public void setArticleFileType(String articleFileType) {
		this.articleFileType = articleFileType;
	}
	public int getArticleFileSize() {
		return articleFileSize;
	}
	public void setArticleFileSize(int articleFileSize) {
		this.articleFileSize = articleFileSize;
	}
	@Override
	public String toString() {
		return "ArticleFile [articleFileId=" + articleFileId + ", articleId=" + articleId + ", articleFileName="
				+ articleFileName + ", articleFileExt=" + articleFileExt + ", articleFileType=" + articleFileType
				+ ", articleFileSize=" + articleFileSize + "]";
	}
}
