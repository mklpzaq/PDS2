package com.test.pds2.resume.controller;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	public String insertResume(ResumeRequest resumeRequest, HttpSession session, MultipartHttpServletRequest multipartHttpServletRequest){
		
		logger.info(resumeRequest.toString());
		String path = session.getServletContext().getRealPath("/resources/upload/resume");  //세션이 만들어진 톰캣 자체를 가져온다  resources/upload/resume
		System.out.println("insertResume+path : "+path);
		// service : ResumeRequest를 -> Resume로 맞춰준다 + 파일 폴더 저장  + 트랜잭션 + 알파
		// dao : insert 
		resumeService.insertResume(resumeRequest, path);
		
		return "redirect:/resumeList";   //리턴타입을 보이드로 하면 매서드 명을 따라간다
	}
			
	@RequestMapping(value = "/resumeList", method = RequestMethod.GET)
	public String resumeList(Model model, HttpSession session										
			,@RequestParam(value="currentPage", defaultValue="1") int currentPage
			,@RequestParam(value="pagePerRow", required=true, defaultValue="10") int pagePerRow
			,@RequestParam(value="searchOption", defaultValue="all") String searchOption
			,@RequestParam(value="keyword", defaultValue="") String keyword) {
				/*Member loginMember = (Member) session.getAttribute("loginMember");*/
				logger.info("resumeList");
		
				Map<String, Object> map = resumeService.resumeList(currentPage, pagePerRow, searchOption, keyword);
				model.addAttribute("list", map.get("list"));
				model.addAttribute("lastPage", map.get("lastPage"));
				model.addAttribute("currentPage", currentPage);
				model.addAttribute("startPage", map.get("startPage"));
				model.addAttribute("endPage", map.get("endPage"));
				model.addAttribute("pagePerRow", pagePerRow);
				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
		return "/resume/resumeList";
	}
}
