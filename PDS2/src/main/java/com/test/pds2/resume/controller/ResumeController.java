package com.test.pds2.resume.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.pds2.resume.service.ResumeRequest;



@Controller
public class ResumeController {

	@Autowired
	//private ArticleService articleService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);
	
	
	@RequestMapping(value = "/addArticle", method = RequestMethod.GET)
	public String addArticle(){
		
		return "addArticle";
	}
	
	@RequestMapping(value = "/addArticle", method = RequestMethod.POST)
	public String addArticle(ResumeRequest resumeRequest, HttpSession session){
		
		logger.info(resumeRequest.toString());
		String path = session.getServletContext().getRealPath("/resources/upload");  //세션이 만들어진 톰캣 자체를 가져온다  resources/upload
		System.out.println("addArticle+path : "+path);
		// service : articleRequest를 -> article로 맞춰준다 + 파일 폴더 저장  + 트랜잭션 + 알파
		// dao : insert 
		//articleService.addArticle(articleRequest, path);
		
		return "redirect:/";
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	//리턴타입을 보이드로 하면 매서드 명을 따라간다
}
