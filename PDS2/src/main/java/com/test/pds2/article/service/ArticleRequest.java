package com.test.pds2.article.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ArticleRequest {
	private String articleTitle;
	private String articleContent;
	private List<MultipartFile> multipartFile;
	
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public List<MultipartFile> getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(List<MultipartFile> multipartFile) {
		this.multipartFile = multipartFile;
	}
	@Override
	public String toString() {
		return "ArticleRequest [articleTitle=" + articleTitle + ", articleContent=" + articleContent
				+ ", multipartFile=" + multipartFile + "]";
	}
}
