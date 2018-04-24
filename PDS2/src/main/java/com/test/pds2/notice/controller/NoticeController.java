package com.test.pds2.notice.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pds2.notice.service.NoticeRequest;
import com.test.pds2.notice.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@RequestMapping(value = "/insertNotice", method = RequestMethod.GET)
	public String insertNotice() {
		logger.debug("insertNotice-GET");
		return "notice/insertNotice";
	}
	
	@RequestMapping(value = "/insertNotice", method = RequestMethod.POST)
	public String insertNotice(NoticeRequest noticeRequest, HttpSession session ) {
		logger.debug("insertNotice-POST");
		logger.debug("NoticeRequest");
		String path = session.getServletContext().getRealPath("/resources/upload/notice");
		logger.debug("path" + path);
		noticeService.insertNotice(noticeRequest, path);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/selectNoticeList", method = RequestMethod.GET)
	public String selectNoticeList(Model model
									, @RequestParam(value="currentPage", defaultValue="1") int currentPage
									,@RequestParam(value="pagePerRow", required=true, defaultValue="10") int pagePerRow) {
		logger.info("selectNoticeList");
		Map<String, Object> map = noticeService.selectNoticeList(currentPage,pagePerRow);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", map.get("startPage"));
		model.addAttribute("endPage", map.get("endPage"));
		model.addAttribute("pagePerRow", pagePerRow);
		return "NoticeList";
	}
}
