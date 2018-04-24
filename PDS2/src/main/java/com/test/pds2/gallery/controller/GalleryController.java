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

@Controller
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	private static final Logger logger = LoggerFactory.getLogger(GalleryController.class);
	
	@RequestMapping(value = "/insertGallery", method = RequestMethod.GET)
	public String insertGallery() {
		return "/gallery/insertGallery";
	}
	
	@RequestMapping(value = "/insertGallery", method = RequestMethod.POST)
	public String insertGallery(GalleryRequest galleryRequest, HttpSession session) {
		logger.info("=============== galleryRequest :  " + galleryRequest);
		String path = session.getServletContext().getRealPath("");
		
		logger.info("=============== path : " + path);
		galleryService.insertGallery(galleryRequest, path);
		return "redirect:/";
	}
}
