package com.test.pds2.gallery.controller;



import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.gallery.service.GalleryRequest;
import com.test.pds2.gallery.service.GalleryService;
import com.test.pds2.path.SystemPath;

@Controller
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	private static final Logger logger = LoggerFactory.getLogger(GalleryController.class);
	
	@RequestMapping(value = "/insertGallery", method = RequestMethod.GET)
	public String insertGallery() {
		return "/gallery/insertGallery";
	}
	/*
	 * jsp 폼에서 넘어온값을 처리한다.
	 * service에 절대경로와 galleryRequest객체를 넘긴다.
	 */	
	@RequestMapping(value = "/insertGallery", method = RequestMethod.POST)
	public String insertGallery(GalleryRequest galleryRequest, HttpSession session) {
		logger.debug("GalleryController.insertGallery(galleryRequest) : " + galleryRequest);
		String path = SystemPath.SYSTEM_PATH;		
		logger.debug("path : " + path);
		
		galleryService.insertGallery(galleryRequest, path);
		return "redirect:/";
	}
}
