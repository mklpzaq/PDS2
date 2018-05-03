package com.test.pds2.article.controller;

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

import com.test.pds2.article.service.Article;
import com.test.pds2.article.service.ArticleRequest;
import com.test.pds2.article.service.ArticleService;
import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.path.SystemPath;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@RequestMapping(value="/deleteArticleFile", method=RequestMethod.GET)
	public String deleteArticleFile(Model model
									,@RequestParam(value="sendNo") int articleId
									,@RequestParam(value="sendFileNo") int articleFileId
									,@RequestParam(value="fileName") String articleFileName
									,@RequestParam(value="fileExt") String articleFileExt) {
		logger.debug("GET /deleteArticleFile deleteArticleFile");
		/* 파일 삭제 과정 */
		articleService.deleteArticleFileOne(articleFileId, articleFileName, articleFileExt);
		/* articleDetailView.jsp 로 돌아가기 위한 작업 */
		Article article = articleService.getDetailArticle(articleId);
		if(article != null) {
			logger.debug("Article : " + article.toString());
		}else {
			logger.debug("Article : null");
		}
		model.addAttribute("detailArticle", article);
		logger.debug("■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		return "/article/articleDetailView";
	}
	
	
	@RequestMapping(value="/getDetailArticle", method=RequestMethod.GET)
	public String getDetailArticle(Model model
									,@RequestParam(value="sendNo") int articleId) {
		
		Article article = articleService.getDetailArticle(articleId);
		logger.debug("article : " + article);
		model.addAttribute("detailArticle", article);
		
		return "/article/articleDetailView";
	}
	
	@RequestMapping(value="/getArticleList", method=RequestMethod.GET)
	public String getArticleList(Model model
									,@RequestParam(value="currentPage", defaultValue="1") int currentPage
									,@RequestParam(value="pagePerRow", defaultValue="10", required=true) int pagePerRow
									,@RequestParam(value="searchSelect", defaultValue="articleId") String searchSelect
									,@RequestParam(value="searchWord", defaultValue="") String searchWord) {
		logger.debug("GET /getArticleList ArticleController");
		logger.debug("searchSelect : " + searchSelect);
		logger.debug("searchWord : " + searchWord);
		
		Map<String, Object> map = articleService.getArticleList(currentPage, pagePerRow, searchSelect, searchWord);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("beginPageNumForCurrentPage", map.get("beginPageNumForCurrentPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pagePerRow", pagePerRow);
		model.addAttribute("searchSelect", searchSelect);
		model.addAttribute("searchWord", searchWord);
		logger.debug("list : "+ map.get("list"));
		logger.debug("lastPage : "+ map.get("lastPage"));
		logger.debug("beginPageNumForCurrentPage : "+ map.get("beginPageNumForCurrentPage"));
		logger.debug("currentPage : "+ currentPage);
		logger.debug("pagePerRow : "+ pagePerRow);
		logger.debug("searchSelect : " + searchSelect);
		logger.debug("searchWord : " + searchWord);
		return "/article/articleList";
	}
	
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
		
		articleService.insertArticle(articleRequest, path);
		return "redirect:/getArticleList";
	}
	
}
