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
	                            , HttpServletRequest request
	                            , HttpServletResponse response) throws Exception {	  
		 
	        String fullPath = SystemPath.SYSTEM_PATH + "\\" + resumeFileName + "." + resumeFileExt;	//저장된 SystemPath.SYSTEM_PATH에 파일 이름 +.+ 파일 확장자를 붙여서 경로 완성 
	         
	        File file = new File(fullPath); //새로운 파일 생성?
	      
	        
	        
	        
	        response.setContentType("application/download; utf-8");  
	        //response 객체는 JSP의 실행 결과를 웹브라우저로 전송하고자 할때 사용하며 
	        //setContentType 메소드는 html의 표준 MIME 타입인 "text/html" 의 변경이나 캐릭터의 인코딩을 재지정하고자 할때 사용합니다.
	        //다시 말해서 무슨 응답인지 정하는거라고 볼수 있을듯? 
	        //MIME 타입은 여러가지가 있어서 검색해봐야 안다.....
	        response.setContentLength((int)file.length());
	        //응답하는 ContentLength를 셋팅하는것 같은데 자꾸 해더에 셋팅을 한다는데 무슨뜻인지 잘 모르겠다
	        //응답하는 콘텐츠가 파일 다운로드니까 파일을 length를 셋팅해주면 되는것 같다
	        
	        String userAgent = request.getHeader("User-Agent");
	        //request.getHeader("User-Agent") HTTP 요청 헤더에 지정된 이름으로 할당된 값을 리턴하는데
	        //지정된 이름이 User-Agent인 경우는 브라우저의 소프트웨어 종류와 버전을 가져오는 것 같다. 
	        //브라우저에 따라서 다운되는게 달라서 필요한 메서드 인듯

	        boolean ie = userAgent.indexOf("MSIE") > -1;
	        //말했던대로 브라우저 정보를 가져와서 MSIE 단어를 기준으로 참인지 거짓인지 나누는것 같다
	        //indexOf의 리턴값은 int인데 리턴값이 -1를 비교하니까 존재하는지 안하는지가 boolean값에 들어가는듯
	       
	        
	        String fileName = null;
	        
	        if(ie){
	             
	            fileName = URLEncoder.encode(file.getName(), "utf-8"); //MSIE가 존재할때
	                         
	        } else {
	             
	            fileName = new String(file.getName().getBytes("utf-8")); //MSIE가 존재하지 않을때?
	             
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
	    }
	 
}
