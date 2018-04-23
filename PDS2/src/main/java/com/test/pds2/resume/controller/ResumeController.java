package com.test.pds2.resume.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.test.pds2.resume.service.ResumeRequest;
import com.test.pds2.resume.service.ResumeService;



@Controller
public class ResumeController {

	@Autowired
	private ResumeService resumeService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);
	
	
	@RequestMapping(value = "/insertResume", method = RequestMethod.GET)
	public String insertResume(){
		
		return "/resume/insertResume";
	}
	
	@RequestMapping(value = "/insertResume", method = RequestMethod.POST)
	public String insertResume(ResumeRequest resumeRequest, HttpSession session){
		
		logger.info(resumeRequest.toString());
		String path = session.getServletContext().getRealPath("/resources/upload");  //세션이 만들어진 톰캣 자체를 가져온다  resources/upload
		System.out.println("insertResume+path : "+path);
		// service : articleRequest를 -> article로 맞춰준다 + 파일 폴더 저장  + 트랜잭션 + 알파
		// dao : insert 
		resumeService.insertResume(resumeRequest, path);
		
		return "redirect:/";
	}
			
	//리턴타입을 보이드로 하면 매서드 명을 따라간다
}
