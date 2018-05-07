package com.test.pds2.article.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.pds2.article.service.Article;
import com.test.pds2.article.service.ArticleFile;
import com.test.pds2.article.service.ArticleRequest;
import com.test.pds2.article.service.ArticleService;
import com.test.pds2.board.service.Board;
import com.test.pds2.board.service.BoardRequest;
import com.test.pds2.path.SystemPath;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@RequestMapping(value="/downloadArticleFile", method=RequestMethod.GET)
	public String downloadArticleFile(Model model
									,HttpServletRequest request
									,HttpServletResponse response
									,HttpSession session
									,@RequestParam(value="sendNo") int articleId
									,@RequestParam(value="fileName") String fileName
									,@RequestParam(value="fileExt") String fileExt) {
		logger.debug("GET /downloadArticleFile ArticleController");
		
		/* articleDetailView.jsp 로 돌아가기 위한 사전 작업 */
		Article article = articleService.getDetailArticle(articleId);
		logger.debug("article : "+ article.toString());
		model.addAttribute("article", article);
		
		/* 파일을 다운로드 시키기 위한 작업 */
		String path = session.getServletContext().getRealPath("/resources/upload/");
		articleService.downloadArticleFile(fileName, fileExt, request, response, path);
		
		return "/article/articleDetailView";
	}
	
	
	@RequestMapping(value="/updateArticle", method=RequestMethod.GET)
	public String updateArticle(Model model
								,@RequestParam(value="sendNo") int articleId) {
		/* updateArticleForm을 보여주기 위해  모든 정보가 포함된 Article객체가 있어야 한다.*/
		Article article = articleService.getDetailArticle(articleId);
		model.addAttribute("article", article);
		return "/article/updateArticleForm";
	}
	
	@RequestMapping(value="/updateArticle", method=RequestMethod.POST)
	public String updateArticle(ArticleRequest articleRequest
								,HttpSession session) {
		logger.debug("POST /updateArticle ArticleController");
		/* 이곳에서 추가된 파일은 insert가 되어야 하고, 수정된 articleTitle, articleContent 는 update가 되어야 한다. */
		
		logger.debug("articleRequest : " + articleRequest.toString());
		//String path = SystemPath.SYSTEM_PATH;
		String path = session.getServletContext().getRealPath("/resources/upload/");
		articleService.updateArticle(articleRequest, path);
		
		return "redirect:/getArticleList";
	}
	
	@RequestMapping(value="/deleteArticle", method=RequestMethod.GET)
	public String deleteArticle(HttpSession session
								,@RequestParam(value="sendNo") int articleId) {
		logger.debug("GET /deleteArticle ArticleController");
		/* articleId에 해당되는 파일 이름 정보값(articleFileName, aritlceFileExt)들을 얻어와서(List로)
		 * return된 List를  deleteArticle의 매개변수로 넘겨주어야
		 * 하드드라이브에 저장되어있는 articleId에 해당되는 articleFile들을 삭제해줄 수 있다.
		 * */
		
		List<ArticleFile> articleFileList = articleService.selectArticleFileListForDelete(articleId);
		logger.debug("articleFileList : " + articleFileList.toString());
		/* 우선 articleId에 해당되는 파일을 모두 지우고 그 다음, articleId에 해당되는 article를 지운다. */
		//매개변수로 articleId에 해당하는 aritlceFileName, articleFileExt를 가지고 있는 articleFile List를 넘겨 받아야 한다.
		String path = session.getServletContext().getRealPath("/resources/upload/");
		articleService.deleteArticle(articleId, articleFileList, path);
		
		return "redirect:/getArticleList";
	}
	

	@RequestMapping(value="/deleteArticleFile", method=RequestMethod.GET)
	public String deleteArticleFile(Model model
									,HttpSession session
									,@RequestParam(value="pageCode", defaultValue="detail") String pageCode
									,@RequestParam(value="sendNo") int articleId
									,@RequestParam(value="sendFileNo") int articleFileId
									,@RequestParam(value="fileName") String articleFileName
									,@RequestParam(value="fileExt") String articleFileExt) {
		logger.debug("GET /deleteArticleFile deleteArticleFile");
		/* 파일 삭제 과정 */
		String path = session.getServletContext().getRealPath("/resources/upload/");
		articleService.deleteArticleFileOne(articleFileId, articleFileName, articleFileExt, path);
		/* articleDetailView.jsp 로 돌아가기 위한 작업 */
		Article article = articleService.getDetailArticle(articleId);
		if(article != null) {
			logger.debug("Article : " + article.toString());
		}else {
			logger.debug("Article : null");
		}
		model.addAttribute("article", article);
		
		String returnValue = null;
		if(pageCode.equals("update")) {
			logger.debug("★★★★★★★업데이트★★★★★★★★★★★");
			returnValue="/article/updateArticleForm";
		}else if(pageCode.equals("detail")) {
			logger.debug("★★★★★★★디테일★★★★★★★★★★★");
			returnValue="/article/articleDetailView";
		}else {
			logger.debug("★★★★★★★else★★★★★★★★★★★");
			returnValue="/article/articleDetailView";
		}
		logger.debug("★★★★★★★리턴전★★★★★★★★★★★");
		return returnValue;
	}
	
	
	@RequestMapping(value="/getDetailArticle", method=RequestMethod.GET)
	public String getDetailArticle(Model model
									,@RequestParam(value="sendNo") int articleId) {
		
		Article article = articleService.getDetailArticle(articleId);
		logger.debug("article : " + article);
		model.addAttribute("article", article);
		
		return "/article/articleDetailView";
	}
	
	@RequestMapping(value="/getArticleList", method=RequestMethod.GET)
	public String getArticleList(Model model
									,HttpSession session
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
		
		/* 파일 저장루트 확인용 */
		String path = session.getServletContext().getRealPath("/resources/upload/");
		model.addAttribute("path", path);
		
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
		
		
		String path = session.getServletContext().getRealPath("/resources/upload/");
		//String path = SystemPath.SYSTEM_PATH;
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		logger.debug("path : " + path);
		
		articleService.insertArticle(articleRequest, path);
		
		return "redirect:/getArticleList";
	}
	
}
