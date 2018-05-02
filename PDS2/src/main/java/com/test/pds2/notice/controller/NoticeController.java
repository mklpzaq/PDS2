package com.test.pds2.notice.controller;

import java.io.File;
import java.io.FileInputStream;
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

import com.test.pds2.notice.service.Notice;
import com.test.pds2.notice.service.NoticeFile;
import com.test.pds2.notice.service.NoticeRequest;
import com.test.pds2.notice.service.NoticeService;
import com.test.pds2.path.SystemPath;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	//새로운 notice를 추가
	@RequestMapping(value = "/insertNotice", method = RequestMethod.GET)
	public String insertNotice() {
		logger.debug("insertNotice-GET");
		return "/notice/insertNotice";
	}
	
	@RequestMapping(value = "/insertNotice", method = RequestMethod.POST)
	public String insertNotice(NoticeRequest noticeRequest, HttpSession session ) {
		logger.debug("insertNotice-POST");
		logger.debug("NoticeRequest");
		String path = SystemPath.SYSTEM_PATH;	
		logger.debug("path" + path);
		noticeService.insertNotice(noticeRequest);
		return "redirect:/selectNoticeList";
	}
	
	//추가된 게시글을 리스트로 저장함
	@RequestMapping(value = "/selectNoticeList", method = RequestMethod.GET)
	public String selectNoticeList(Model model
									, HttpSession session										
									,@RequestParam(value="currentPage", defaultValue="1") int currentPage
									,@RequestParam(value="pagePerRow", required=true, defaultValue="10") int pagePerRow
									,@RequestParam(value="searchOption", defaultValue="all") String searchOption
									,@RequestParam(value="keyword", defaultValue="") String keyword) {
		logger.info("selectNoticeList");
		Map<String, Object> map = noticeService.selectNoticeList(currentPage,pagePerRow);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", map.get("startPage"));
		model.addAttribute("endPage", map.get("endPage"));
		model.addAttribute("pagePerRow", pagePerRow);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		return "/notice/noticeList";
	}
	
	//리스트에서 게시글 하나를 클릭하면 단독으로 보여줄 화면
	@RequestMapping(value = "/noticeView", method= RequestMethod.GET)
	public String noticeView(Notice notice, Model model) {
		logger.debug("noticeView - notice : " + notice.toString());
		Notice noticeView = noticeService.noticeView(notice);
		logger.debug("noticeView - noticeView : " + noticeView.toString());		
		model.addAttribute("noticeView", noticeView);		
		List <NoticeFile> list = noticeView.getNoticeFile();
		logger.debug("noticeView - noticeFile : " + list.toString());
		model.addAttribute("list", list);
		return "/notice/noticeView";
	}
	
	//게시글 목록에서 삭제
	@RequestMapping(value = "/deleteNoticeList", method= RequestMethod.GET)
	public String deleteNoticeList(@RequestParam(value="deleteCheckbox") int[] deleteCheckbox) {
		logger.debug("deleteNoticeList - notice");
		noticeService.deleteNoticeList(deleteCheckbox);		
		return "redirect:/selectNoticeList";
	}
	
	//단독으로 보여진 뷰 내부에서 삭제
	@RequestMapping(value = "/deleteNotice", method= RequestMethod.GET)
	public String deleteNotice(Notice notice, Model model) {
		logger.debug("deleteNotice - notice : " + notice.toString());		
		noticeService.deleteNotice(notice);		
		return "redirect:/selectNoticeList";
	}

	//업로드한 파일 삭제하기
	@RequestMapping(value = "/deleteNoticeFile", method= RequestMethod.GET)
	public String deleteNoticeFile(NoticeFile noticeFile
									, @RequestParam("noticeFileName") String noticeFileName
	                            	, @RequestParam("noticeFileExt") String noticeFileExt
	                            	, RedirectAttributes redirectAttributes) {
	noticeService.deleteNoticeFile(noticeFile, noticeFileName, noticeFileExt);
		return "redirect:/selectNoticeList";
		
	}
	
	//수정
	@RequestMapping(value = "/updateNotice", method= RequestMethod.POST)
		public String updateNotice(Notice notice) {
			return null;
		
	}
	
	
	//다운로드(인호오빠꺼 참고, 읽어봐도 무슨소린지 잘 모르겠다)
	 @RequestMapping(value = "/downloadNotice", method= RequestMethod.GET)
	    public void download(@RequestParam("noticeFileName") String noticeFileName
	                            ,@RequestParam("noticeFileExt") String noticeFileExt
	                            , HttpServletRequest request
	                            , HttpServletResponse response) throws Exception {	  
		 
	        String Path = SystemPath.SYSTEM_PATH + "\\" + noticeFileName + "." + noticeFileExt;	
	        File file = new File(Path); //새로운 파일 생성? 요청하는 파일과 동일한 파일 객체 생성
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
	        //검색해보니 "MSIE"라는 단어는 익스플로어에만 들어가는것 같다. 
	        //문제는 이것도 익스플로어 10까지만 있고 11부터는 User-Agent에서 MSIE가 사라졌다고 한다....
	        //그래서 11부터는 User-Agent의 Trident라는 값으로 찾아낸다고 한다
	        //Trident는 익스플로어에서 사용하는 레이아웃 엔진이라고 하는데 이것도 문제가 조금 있는게 익스 7버전 이후부터 발견이 된다고 한다
	        //암튼 적당히 섞어서 써야하는듯
	        
	        String fileName = null;
	        
	        if(ie){
	             
	            fileName = URLEncoder.encode(file.getName(), "utf-8"); //MSIE가 존재할때 즉 익스플로어
						//익스플로어에서는 다른 웹브라우저와 한글을 처리하는 방식이 다르기 때문에 주소에 한글이 들어갔을 때를 대비해서 만드는 코드 같다
						//URLEncoder.encode()는 주소를 인코딩해주는 메서드이다. 
						//주소에 한글이 들어갈 경우를 즉 지금은 파일을 생성한 파일(다운로드할 파일?)에 한글이 들어가서 깨질 경우를 대비해 파일이름을 utf-8로 인코딩을 시켜주는것 같다
						//참고로 URLEncoder는 웹서버에서 요구되는 MIME 데이터 형식으로 데이터를 바꾸는 역할을 하는데 
						//-MIME 형식 변환 규칙은 다음과 같다
							/*1. 아스키 문자(a~z, A~Z, 0~9), '.', '-', '_' 등은 그대로 전달된다.									
							2. 공백은 '+'로 전달된다.									
							3. 기타문자는 %ㅁㅁ 와 같이 전달된다. 이때 %ㅁㅁ는 아스키코드를 16진수화한 결과를 나타낸 것이다.
	            			4. 바뀌고 나면  abc+%A4%B5 뭐 이런걸로 변환되는듯*/
	            		//URLEncoder과 반대되는 URLDecoder라는 클래스가 있는데  abc+%A4%B5로 인코딩되어있는 문자열을 다시 일반문자열로 변환하는 역할을 한다
	        } else {
	             
	            fileName = new String(file.getName().getBytes("utf-8")); //MSIE가 존재하지 않을때? 익스플로어가 아닐때 
	             			 //file.getName(), 생성된 파일을 이름을 가져와서 바이트배열을 돌려준다고 하는데 무슨 소리인지 잘 모르겠다?
	            			 //설명을 들어보니 자바 내부에 있는 모든 문자열을 유니코드로 형식으로 처리되는데 
	            			 //getBytes메서드는 유니코드 문자열을 지정된 캐릭터셋의 바이트 배열로 반환하는 메서드라고 한다
	            			 //즉 파일 이름을 가져와서 바이트 배열을 utf-8로 바꿔준다는 얘기인데 이것도 인코딩같다
	            			 //인코딩, 캐릭터셋쪽은 좀더 파봐야할것 같다	            			
	        }
	        
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
	        //응답헤더더 셋팅한다는 메서드같다. setHeader() 이미 존재하는 값을 덮어 쓰고 addHeader() 값을 하나 더 추가한다는데 정확히 무슨 값을 덮어쓰고 추가한다는건지
	        /*검색해보니 
	        "Content-disposition: inline"은 브라우저 인식 파일확장자를 가진 파일들에 대해서는 웹브라우저 상에서 바로 파일을 자동으로 보여줄 수 있어서 
			의미상인 멀티파트 메시지를 표현하는데 있다. 그외의 파일들에 대해서는 "파일 다운로드" 대화상자가 뜨도록 하는 헤더속성이다.
	        "Content-disposition: attachment"은 브라우저 인식 파일확장자를 포함하여 모든 확장자의 파일들에 대해,  다운로드시 
			무조건 "파일 다운로드" 대화상자가 뜨도록 하는 헤더속성이라 할 수 있다.*/
	        //라고한다. 
	        //setHeader매서드를 보니 ("name", "value")였는데 그럼 "value"부분에 해당하는 "attachment; filename=\"" + fileName + "\";"중에서
	        //filename=\"" + fileName + "\";"까지 무슨 말인지 모르겠다. 
	        //하기사 없애고 실행해보면 알듯
	        //대박사건인데 filename=\"" + fileName + "\";"를 없애니까 다운로드창이 뜰때 내가 다운로드하는 파일의 이름 확장자명같은게 사라졌다
	        //다시 말해서 파일 다운로드 대화상자가 뜰때 대화상자에 지금 다운로드 하는 파일의 이름이나 확장자 같은걸 정해주는 코드인듯

	        response.setHeader("Content-Transfer-Encoding", "binary");
	        //Content-Transfer-Encoding 이새끼는 또 뭐하는 해더정보일까
	        //검색해보니 Content-Transfer-Encoding : 전송 데이타의 body를 인코딩한 방법[인코딩 방식]을 표시함. 
	        //즉 이제까지는 주소를 인코딩했고 이제는 전송데이터의 인코딩을 또 해줘야한다는 말이다. 헤헤 인코딩은 참 많이 하는것 같다 ^^
			/* 5) MIME에서의 Content-Transfer-Encoding 방식
			전송내용물 인코딩 방식은 크게 5 가지가 있습니다. 헤더에 Content-Transfer-Encoding 필드가
			생략되어 있으면 7bit 방식을 의미합니다. 7bit 방식, 8bit 방식, 및 binary 방식 이들 세가지
			방식들은 인코딩을 하지 않는 방식입니다
			이에 반해 Base64와 Quoted-Printable 방식은 8bit코드를 7bit코드로 인코딩하고, 다시 7bit를 8bit로 디코딩합니다. 
			그러므로 HTML문서를 전자메일로(SMTP로) 보낼 때나, 그래픽 파일을 첨부파일로 보낼
			때에 이들 문서들은 7bit 텍스트(문자 메시지) 이외에 8bit 이미지 등을 포함하고 있으므로 Base64와
			Quoted-Printable 방식 중에 하나를 사용해야 합니다. 실제로 Outlook Express는 이 두가지 중 하나로
			설정되어 있음을 확인할 수 있습니다.*/
	        /*(5) binary
	        1) [binary] 방식은 SMTP에서는 한 라인에 1,000 문자를 넘지 못하도록 규정하고 있는데, 7bit와
				8bit는 이를 준수합니다.
	        2) 그러나, [binary] 방식은 길이에 제약이 없고, 역시 실제 이진 데이터에 대한 인코딩은 하지 않
				습니다.*/
	        //빡치니까 다른 방식들은 알아서 찾아보도록 하자
	         
	        OutputStream out = response.getOutputStream();	        
	        FileInputStream fis = null;
	        
	        //일단 간단히 설명된걸 긁어와보면 
			/*FileInputStream 클래스는 InputStream 클래스를 상속받은 후손 클래스로 하드 디스크상에 존재하는 파일로부터 바이트 단위의 입력을 받는 클래스이다. 
			이 클래스는 출발 지점과 도착 지점을 연결하는 통로, 즉 스트림을 생성하는 클래스이다.
			생성자의 인자로는 File 객체를 주거나 파일의 이름을 직접 String 형태로 줄 수 있다. 
			일반적으로 파일의 이름을 String 형태로 주는 경우가 많은데 파일이 존재하지 않을 가능성도 있으므로 Exception 처리를 해야 한다.*/
	        /*FileOutputStream 클래스도 OutputStream 클래스의 후손 클래스로 파일로 바이트 단위의 출력을 내보내는 클래스이다.
	        Sink 스트림의 일종으로 3 개의 생성자가 중복 정의되어 있다. FileInputStream의 생성자와 거의 같은 형태인데 하나 더 있는 생성자의 
			형식은 append 처리를 위한 논리 변수를 인자로 가지고 있다. 이 값이 true로 설정되면 기존에 존재하고 있는 파일의 가장 뒷 부분에 연결하여 출력된다.*/
	        //라고 하는데 실질적으로 입력 통로(request) 출력 통로 (response)에 관련된건 알겠다
	        //실제로 쳐보니 request는 getInputStream메서드만 있고 response는 위와 같이 getOutputStream매서드만 있다

	        try {
	             
	            fis = new FileInputStream(file);
	            //새로운 FileInputStream니까 통로를 만들어 이전에 생성한 파일을 요청하는것 같은데
	            //이게 이렇게 되니까 요청하고 응답이 좀 햇갈리는데
	            //해석하면 요청하는 파일과 동일한 파일 객체를 만들어놓고 
	            //해당 파일 객체와 동일한 파일 객체를 요청하는 거라고 해석해야하나?
	     
	            FileCopyUtils.copy(fis, out);
	            //스프링 프레임워크 포함되어있는 클래스와 메서드라고 한다
	            //보니까 기존에 java.io 패키지에 구현되어있는 stream기반의 api를 기반으로 만들어진 클래스라고 한다
	            //copy에 해당하는 매서드는 여러 매서드로 Overloading되어있는데 매개변수를 해석보면 아무튼 첫번째 매개변수는 입력받는 부분이고
	            //뒤에 매개변수는 출력하는 부분이다.
	            //적당히 해석보면 요청받은 파일(InputStream)을  카피해서 응답(OutputStream) 시켜주는거인것 같다
	            //카피해서 응답하는거니까 당연히 카피할 파일(file)이 있어야하는거고.
	            //맞을까?
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
	        //Stream이 통로라고 했으니까 비유하자면 통로를 닫으면서 남아있는 데이터를 쏘아주는 역할을 한다고 한다.
	        //flush()는 현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다. (JSP) 이렇게도 되어있고
	        //buffer가 다 차기 전에 프로그램을 종료하면 buffer에 들어있는 내용은 파일에 쓰여지지 않는다.
	        //그 때 flush()를 호출하면 buffer의 내용이 파일에 쓰여진다. 
	        //말하자면 데이터가 전송될때는 기본적으로 일정부분 쌓이다 물총쏘듯이 한꺼번에 보내지는데
	        //flush는 아직 물총을 쏠만큼 모이지 않았어도 강제적으로 지금까지 쌓인 데이터를 쏘아버리는 느낌?
	        //close와 비슷하면서도 다른것 같은데
	        //close 메서드를 실행하면 자동으로 flush가 실행된다고도 한다.
	        
	    }
}



