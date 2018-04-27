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
						,HttpServletRequest request
                        , HttpServletResponse response) throws Exception{
		String fullPath = SystemPath.SYSTEM_PATH + galleryFileName + "." + galleryFileExt;
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
		// join문 쿼리를 받아서 Gallery viewGallery에 대입했다.
		Gallery viewGallery = galleryService.viewDetailGallery(gallery);
		// Gallery 에 List<GalleryFile>를 getting하고 list에 담는다.
		// 화면에 보여줄 준비를 한다.
		List<GalleryFile> list = viewGallery.getGalleryFile();
		logger.debug("Gallery viewGallery : " + viewGallery);
		logger.debug("List<GalleryFile> list : " + list);
		model.addAttribute("viewGallery", viewGallery);
		model.addAttribute("list", list);
		
		return "/gallery/galleryDetailView";
	}
	
	@RequestMapping(value = "/searchGallery", method = RequestMethod.POST)
	public String searchGallery(@RequestParam(value="searchOption", defaultValue="all") String searchOption
								,@RequestParam(value="keyword", defaultValue="") ArrayList<String> keyword
								,Model model) {
		
		List<Gallery> list = galleryService.listAll(searchOption, keyword);
		
		return "redirect:/gallery/galleryList";
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
	
	@RequestMapping(value = "/updateGallery", method = RequestMethod.POST)
	public String updateGallery(Model model, Gallery gallery) {
		logger.debug("GalleryController.updateGallery() Gallery : " + gallery);
		int result = galleryService.updateGallery(gallery);
		
		return "redirect:/gallery/galleryList";
	}
	
	@RequestMapping(value = "/updateGallery", method = RequestMethod.GET)
	public String updateGallery(Model model
								,@RequestParam(value="galleryId") int galleryId) {
		logger.debug("GalleryController.updateGallery() Gallery : " + galleryId);
		Gallery gallery = galleryService.updateGalleryForId(galleryId);
		logger.debug("GalleryController.updateGallery() gallery : " + gallery);
		model.addAttribute("galleryForId", gallery);
		return "/gallery/updateGallery";
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
	public String insertGallery(GalleryRequest galleryRequest, HttpSession session) {
		logger.debug("GalleryController.insertGallery(galleryRequest) : " + galleryRequest);
		String path = SystemPath.SYSTEM_PATH;		
		logger.debug("path : " + path);
		
		galleryService.insertGallery(galleryRequest, path);
		return "redirect:/";
	}
}
