package com.test.pds2.resume.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.pds2.path.SystemPath;
import com.test.pds2.resume.service.Resume;
import com.test.pds2.resume.service.ResumeFile;
import com.test.pds2.resume.service.ResumeRequest;
import com.test.pds2.resume.service.ResumeService;



@Controller
public class ResumeController {

	@Autowired
	private ResumeService resumeService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);
	
	
	@RequestMapping(value = "/insertResume", method = RequestMethod.GET)
	public String insertResume(){
		logger.debug("insertResume - method = RequestMethod.GET : ");		
		return "/resume/insertResume";
	}
	
	@RequestMapping(value = "/insertResume", method = RequestMethod.POST)
	public String insertResume(ResumeRequest resumeRequest, HttpSession session){
		logger.debug("insertResume - method = RequestMethod.POST : ");	
		logger.debug("insertResume - resumeRequest : " + resumeRequest.toString());
		
		String path = session.getServletContext().getRealPath("D:\\upload");  //세션이 만들어진 톰캣 자체를 가져온다  resources/upload/resume
		logger.debug("insertResume - path : " + path);
		// MultipartHttpServletRequest 멀티파일을 기본적으로 리스트로 받을 수 있게 해주는 클래스. 이런곳도 있다
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
				logger.debug("resumeList - method = RequestMethod.GET : ");	
				logger.debug("resumeList - currentPage : " + currentPage);
				logger.debug("resumeList - currentPage : " + pagePerRow);
				logger.debug("resumeList - currentPage : " + searchOption);
				logger.debug("resumeList - currentPage : " + keyword);
		
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
	
	@RequestMapping(value = "/resumeView", method= RequestMethod.GET)
	public String resumeView(Resume resume, Model model) {
		logger.debug("resumeView - resume : " + resume.toString());
		
		
		Resume resumeView = resumeService.resumeView(resume);
		List <ResumeFile> list = resumeView.getResumeFile();
		logger.debug("resumeView - resumeView : " + resumeView.toString());
		logger.debug("resumeView - resumeFile : " + list.toString());
		model.addAttribute("resumeView", resumeView);
		model.addAttribute("list", list);
				
		return "/resume/resumeSangseView";
	}
	
	
	
	
	 @RequestMapping(value = "/download", method= RequestMethod.GET)
	    public void download(@RequestParam("resumeFileName") String resumeFileName
	                            ,@RequestParam("resumeFileExt") String resumeFileExt
	                            , RedirectAttributes redirectAttributes
	                            , HttpServletRequest request
	                            , HttpServletResponse response) throws Exception {	         
	        String fullPath = SystemPath.SYSTEM_PATH + "\\" + resumeFileName + "." + resumeFileExt;
	         
	        File file = new File(fullPath);
	        
	        response.setContentType("application/download; utf-8");
	        response.setContentLength((int)file.length());
	        
	        String userAgent = request.getHeader("User-Agent");

	        boolean ie = userAgent.indexOf("MSIE") > -1;
	         
	        String fileName = null;
	        
	        if(ie){
	             
	            fileName = URLEncoder.encode(file.getName(), "utf-8");
	                         
	        } else {
	             
	            fileName = new String(file.getName().getBytes("utf-8"));
	             
	        }

	        
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
	         
	        response.setHeader("Content-Transfer-Encoding", "binary");
	         
	        OutputStream out = response.getOutputStream();
	         
	        FileInputStream fis = null;
	         
	        try {
	             
	            fis = new FileInputStream(file);
	             
	            FileCopyUtils.copy(fis, out);
	             
	             
	        } catch(Exception e){
	             
	            e.printStackTrace();
	             
	        }finally{
	             
	            if(fis != null){
	                 
	                try{
	                    fis.close();
	                }catch(Exception e){}
	            }
	             
	        }// try end;
	         
	        out.flush();

	        
	        //redirectAttributes.addFlashAttribute("file", file);
	        
	        //return "download";
	    }
	 
	   
}
