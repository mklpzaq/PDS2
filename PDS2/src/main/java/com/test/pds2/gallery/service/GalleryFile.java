package com.test.pds2.gallery.service;

public class GalleryFile {
	private int galleryFileId; // gallery_file_id
	private int galleryId; // gallery_id
	private String galleryFileName; // gallery_file_name
	private String galleryFileExt; // gallery_file_ext
	private String galleryFileType; // gallery_file_type
	private int galleryFileSize; // gallery_file_size
	
	public int getGalleryFileId() {
		return galleryFileId;
	}
	public void setGalleryFileId(int galleryFileId) {
		this.galleryFileId = galleryFileId;
	}
	public int getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(int galleryId) {
		this.galleryId = galleryId;
	}
	public String getGalleryFileName() {
		return galleryFileName;
	}
	public void setGalleryFileName(String galleryFileName) {
		this.galleryFileName = galleryFileName;
	}
	public String getGalleryFileExt() {
		return galleryFileExt;
	}
	public void setGalleryFileExt(String galleryFileExt) {
		this.galleryFileExt = galleryFileExt;
	}
	public String getGalleryFileType() {
		return galleryFileType;
	}
	public void setGalleryFileType(String galleryFileType) {
		this.galleryFileType = galleryFileType;
	}
	public int getGalleryFileSize() {
		return galleryFileSize;
	}
	public void setGalleryFileSize(int galleryFileSize) {
		this.galleryFileSize = galleryFileSize;
	}
	@Override
	public String toString() {
		return "GalleryFile [galleryFileId=" + galleryFileId + ", galleryId=" + galleryId + ", galleryFileName="
				+ galleryFileName + ", galleryFileExt=" + galleryFileExt + ", galleryFileType=" + galleryFileType
				+ ", galleryFileSize=" + galleryFileSize + "]";
	}
	
	
}
