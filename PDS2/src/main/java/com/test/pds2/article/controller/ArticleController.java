package com.test.pds2.article.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.article.service.ArticleRequest;
import com.test.pds2.article.service.ArticleService;
import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.path.SystemPath;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@RequestMapping(value="/insertArticle", method=RequestMethod.GET)
	public String insertArticle() {
		logger.debug("GET /insertArticle ArticleController");
		return "/article/insertArticle";
	}
	
	@RequestMapping(value="/insertArticle", method=RequestMethod.POST)
	public String insertArticle(ArticleRequest articleRequest, HttpSession session) {
		logger.debug("POST /insertArticle ArticleController");
		logger.debug("ArticleRequest : " + articleRequest);
		
		//String path = session.getServletContext().getRealPath("/resources/upload");
		String path = SystemPath.SYSTEM_PATH;
		logger.debug("path : " + path);
		
		//articleService.insertArticle(articleRequest, path);
		return "redirect:/home";
	}
	
}
