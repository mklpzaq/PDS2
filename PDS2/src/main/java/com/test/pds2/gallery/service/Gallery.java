package com.test.pds2.gallery.service;

import java.util.ArrayList;
import java.util.List;

public class Gallery {
	private int galleryId; //gallery_id
	private String galleryTitle; //gallery_title
	private String galleryContent; //gallery_content	
	private List<GalleryFile> galleryFile; 
	
	public Gallery() {
		this.galleryFile = new ArrayList<GalleryFile>();
	}
	
	public int getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(int galleryId) {
		this.galleryId = galleryId;
	}
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
	public List<GalleryFile> getGalleryFile() {
		return galleryFile;
	}
	public void setGalleryFile(List<GalleryFile> galleryFile) {
		this.galleryFile = galleryFile;
	}
	@Override
	public String toString() {
		return "Gallery [galleryId=" + galleryId + ", galleryTitle=" + galleryTitle + ", galleryContent="
				+ galleryContent + ", galleryFile=" + galleryFile + "]";
	}
	
	
	
	
}
