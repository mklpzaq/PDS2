package com.test.pds2.gallery.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.test.pds2.gallery.service.Gallery;
import com.test.pds2.gallery.service.GalleryFile;
import com.test.pds2.gallery.service.GalleryRequest;
import com.test.pds2.gallery.service.GalleryService;
import com.test.pds2.path.SystemPath;

@Controller
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	private static final Logger logger = LoggerFactory.getLogger(GalleryController.class);
	
	/*@RequestMapping(value ="/viewDetailGallery", method = RequestMethod.GET)
	public String viewDetailGallery(Model model
									,Gallery gallery
									,@RequestParam(value="currentPage", defaultValue="1") int currentPage
									,@RequestParam(value="pagePerRow", defaultValue="10", required=true) int pagePerRow) {
		logger.debug("GalleryController.viewDetailGallery()");		
		Map<String, Object> map = galleryService.viewDetailGallery(gallery, currentPage, pagePerRow);
		model.addAttribute("detailList", map.get("detailList"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("lastPageGalleryCnt", map.get("lastPageGalleryCnt"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pagePerRow", pagePerRow);
		model.addAttribute("beginPageNumForCurrentPage", map.get("beginPageNumForCurrentPage"));		
		return "/gallery/galleryDetailView";
	}*/
			
	@RequestMapping(value = "/downloadGallery", method = RequestMethod.GET)
	public void download(@RequestParam("galleryFileName") String galleryFileName
						,@RequestParam("galleryFileExt") String galleryFileExt
						,HttpSession session
						,HttpServletRequest request
                        , HttpServletResponse response) throws Exception{
		//String fullPath = SystemPath.SYSTEM_PATH + galleryFileName + "." + galleryFileExt;
		String path = session.getServletContext().getRealPath("/resources/upload/");
		String fullPath = path + galleryFileName + "." + galleryFileExt;
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
            out.flush();
        }    
	}
	
	/*
	 * 상세보기 컨트롤러 리스트에서 제목을 눌러 galleryId값을 받아왔다.  
	 */
	@RequestMapping(value = "/viewDetailGallery", method = RequestMethod.GET)
	public String viewDetailGallery(Gallery gallery
									,Model model) {
		logger.debug("GalleryController.viewDetailGallery()");
				
		Gallery viewGallery = galleryService.viewDetailGallery(gallery);
		
		
		logger.debug("viewGallery Id: " + viewGallery.getGalleryId());
		logger.debug("viewGallery title: " + viewGallery.getGalleryTitle());
		logger.debug("viewGallery content: " + viewGallery.getGalleryContent());
		
		/*
		// Gallery 에 List<GalleryFile>를 getting하고 list에 담는다.
		// 화면에 보여줄 준비를 한다.
		List<GalleryFile> list = viewGallery.getGalleryFile();
		logger.debug("Gallery viewGallery : " + viewGallery);
		logger.debug("List<GalleryFile> list : " + list);*/
		model.addAttribute("viewGallery", viewGallery);
		//model.addAttribute("list", list);
		
		return "/gallery/galleryDetailView";
	}
	
	@RequestMapping(value = "/searchGallery", method = RequestMethod.POST)
	public String searchGallery(@RequestParam(value="searchOption", defaultValue="all") String searchOption
								,@RequestParam(value="keyword", defaultValue="") ArrayList<String> keyword
								,Model model) {
		
		List<Gallery> list = galleryService.listAll(searchOption, keyword);
		
		return "redirect:/galleryList";
	}
	
	/*
	 * currentPage의 디폴트 지정값은 1로 설정 리스트에 아무것도 없는 경우는 1페이지다.
	 * pagePerRow 한번에 보여줄 리스트의 양은 10개로 지정해놓는다. 
	 * required=ture 로 설정해놓으면 값이 무조건 넘어온다.
	 */	
	@RequestMapping(value="/galleryList", method = RequestMethod.GET)
	public String galleryList(Model model
								,@RequestParam(value="currentPage", defaultValue="1") int currentPage
								,@RequestParam(value="pagePerRow", defaultValue="10", required=true) int pagePerRow) {
		logger.debug("GalleryController.galleryList()");
		// 페이지 값을 map 형태로 받아온다.
		// map에 리스트값, 마지막페이지, 현제페이지, 한페이지에 보여줄갯수, 5개 단위로 자른 현제 페이지 값을 넣는다.
		Map<String, Object> map = galleryService.galleryList(currentPage,pagePerRow);
		logger.debug("GalleryController.Map<String, Object> map : " + map);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("lastPageGalleryCnt", map.get("lastPageGalleryCnt"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pagePerRow", pagePerRow);
		model.addAttribute("beginPageNumForCurrentPage", map.get("beginPageNumForCurrentPage"));		
		return "/gallery/galleryList";
	}
	
	@RequestMapping(value = "/updateAndDeleteGallery", method = RequestMethod.POST)
	public String updateGallery(HttpSession session
								,@RequestParam(value="galleryId", required=true) int galleryId
								,@RequestParam(value="galleryTitle") String galleryTitle
								,@RequestParam(value="galleryContent") String galleryContent
								,@RequestParam(value="multipartFile") List<MultipartFile> multipartFile
								,@RequestParam(value="deleteImg",required=false)List<String> deleteImg) {
		logger.debug("GalleryController.updateGallery() post 호출 ");
		
		GalleryRequest galleryRequest = new GalleryRequest();
		galleryRequest.setGalleryTitle(galleryTitle);
		logger.debug("GalleryController.galleryRequest.setGalleryTitle() post galleryTitle : "+ galleryTitle);
		galleryRequest.setGalleryContent(galleryContent);
		logger.debug("GalleryController.galleryRequest.setGalleryContent() post galleryContent : "+ galleryContent);
		galleryRequest.setMultipartfile(multipartFile);
		logger.debug("GalleryController.galleryRequest.setMultipartfile() post multipartFile : "+ multipartFile);
		//String path = SystemPath.SYSTEM_PATH;
		String path = session.getServletContext().getRealPath("/resources/upload/");
		galleryService.updateGallery(galleryId, galleryRequest, path, deleteImg);
		
		return "redirect:/galleryList";
	}
	
	@RequestMapping(value = "/updateAndDeleteGallery", method = RequestMethod.GET)
	public String updateGallery(Model model
								,Gallery gallery) {
		logger.debug("GalleryController.updateGallery() Gallery : " + gallery);
		Gallery updateGallery = galleryService.updateGallery(gallery);		
		logger.debug("GalleryController.updateGallery() updateGallery : " + updateGallery.toString());
		
		model.addAttribute("updateGallery", updateGallery);
		List<GalleryFile> list = updateGallery.getGalleryFile();
		logger.debug("GalleryController.List<GalleryFile> list : " + list.toString());
		model.addAttribute("list", list);
		
		return "/gallery/updateAndDeleteGallery";
	}
	
	@RequestMapping(value = "/deleteGallery", method = RequestMethod.GET)
	public String deleteGallery(HttpSession session
								,@RequestParam(value="galleryId") int galleryId) {
		logger.debug("GalleryController.deleteGallery(galleryId) : " + galleryId);	
		
		//String path = SystemPath.SYSTEM_PATH;
		String path = session.getServletContext().getRealPath("/resources/upload/");
		galleryService.deleteGallery(galleryId, path);
		
		return "redirect:/galleryList";		
	}
	
	@RequestMapping(value = "/insertGallery", method = RequestMethod.GET)
	public String insertGallery() {
		return "/gallery/insertGallery";
	}
	/*
	 * jsp 폼에서 넘어온값을 처리한다.
	 * service에 절대경로와 galleryRequest객체를 넘긴다.
	 */	
	@RequestMapping(value = "/insertGallery", method = RequestMethod.POST)
	public String insertGallery(HttpSession session
								,GalleryRequest galleryRequest
								,Model model) {
		logger.debug("GalleryController.insertGallery(galleryRequest) : " + galleryRequest);		
		
		List<MultipartFile> list = galleryRequest.getMultipartfile();
		logger.debug("list : " + list);
		
		// 서버에서 이미지파일만 올릴수 있게 file 명을 받아온다.
		for(MultipartFile file : list) {
			String fileType = file.getContentType();			
			logger.debug("fileType : " + fileType);
			
			if(!fileType.equals("image/jpeg") && !fileType.equals("image/jpg") && !fileType.equals("image/png") &&
				!fileType.equals("image/bmp") && fileType.equals("image/webp")) {
				
				logger.debug("fileType : " + fileType);
				logger.info("이미지 파일이 아닙니다.");
				model.addAttribute("error", "alert('이미지 파일이 아닙니다.')");
				model.addAttribute("galleryTitle", galleryRequest.getGalleryTitle());
				model.addAttribute("galleryContent", galleryRequest.getGalleryContent());
				
				return "/gallery/insertGallery";
			}
		}	
		
		//String path = SystemPath.SYSTEM_PATH;
		String path = session.getServletContext().getRealPath("/resources/upload/");
		logger.debug("path : " + path);
		galleryService.insertGallery(galleryRequest, path);
		return "redirect:/";
	}
}
