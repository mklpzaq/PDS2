package com.test.pds2.article.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.article.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@RequestMapping(value="/insertArticle", method=RequestMethod.GET)
	public String insertArticle() {
		logger.info("/insertArticle GET ArticleController");
		return "home";
	}
	
	@RequestMapping(value="/insertArticle", method=RequestMethod.POST)
	public String insertArticle(int a) {
		logger.info("/insertArticle POST ArticleController");
		articleService.insertArticle();
		return "redirect:/home";
	}
	
}
