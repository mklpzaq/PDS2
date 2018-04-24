package com.test.pds2.notice.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.notice.service.NoticeRequest;
import com.test.pds2.notice.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@RequestMapping(value = "addNotice", method = RequestMethod.GET)
	public String addNotice() {
		return "addNotice";
	}
	
	@RequestMapping(value = "addNotice", method = RequestMethod.POST)
	public String addNotice(NoticeRequest noticeRequest, HttpSession session ) {
		System.out.println("NoticeRequest");
		String path = session.getServletContext().getRealPath("/resources/upload");
		System.out.println("path" + path);
		noticeService.addNotice(noticeRequest, path);
		return "redirect:/";
	}
}
