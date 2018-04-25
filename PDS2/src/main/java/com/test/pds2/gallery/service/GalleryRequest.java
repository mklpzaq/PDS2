package com.test.pds2.gallery.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class GalleryRequest {
	private String galleryTitle; //gallery_title
	private String galleryContent; //gallery_content
	private List<MultipartFile> multipartfile;
	
	public String getGalleryTitle() {
		return galleryTitle;
	}
	public void setGalleryTitle(String galleryTitle) {
		this.galleryTitle = galleryTitle;
	}
	public String getGalleryContent() {
		return galleryContent;
	}
	public void setGalleryContent(String galleryContent) {
		this.galleryContent = galleryContent;
	}
	public List<MultipartFile> getMultipartfile() {
		return multipartfile;
	}
	public void setMultipartfile(List<MultipartFile> multipartfile) {
		this.multipartfile = multipartfile;
	}
	@Override
	public String toString() {
		return "GalleryRequest [galleryTitle=" + galleryTitle + ", galleryContent=" + galleryContent
				+ ", multipartfile=" + multipartfile + "]";
	}
	
	
	
}
