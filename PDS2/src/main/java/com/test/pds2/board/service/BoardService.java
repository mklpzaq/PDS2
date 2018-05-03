package com.test.pds2.board.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.test.pds2.article.service.Article;
import com.test.pds2.article.service.ArticleFile;
import com.test.pds2.path.SystemPath;
 
@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private BoardFileDao boardFileDao;
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	/*public Board selectBoardOneForUpdate(int boardId) {
		return boardDao.selectBoardOneForUpdate(boardId);
	}*/
	public void updateBoard(BoardRequest boardRequest, String path) {
		logger.debug("updateBoard BoardService");
		/* 이곳에서 추가된 파일은 insert가 되어야 하고, 수정된 boardTitle, boardContent 는 update가 되어야 한다. */
		/* 수정된 정보들을  board에 세팅 */
		Board board = new Board();
		board.setBoardId(boardRequest.getBoardId());
		board.setBoardTitle(boardRequest.getBoardTitle());
		board.setBoardContent(boardRequest.getBoardContent());
		
		/* updateForm에서 넘겨받은 multipartFile 정보를 해석하기 위해 List에 저장한 후 forEach문을 돌리면서 해석한다. */
		List<MultipartFile> multipartFileList = boardRequest.getMultipartFile();
		for(MultipartFile multipartFile : multipartFileList) {
			/*
			 * 1. 파일 이름
			 * */
			UUID uuid = UUID.randomUUID();
			logger.debug("uuid : " + uuid);
			String filename = uuid.toString();
			logger.debug("filename : " + filename);
			filename = filename.replace("-", "");
			logger.debug("replaced filename : " + filename);
			
			/*
			 * 2. 파일 확장자
			 * */
			logger.debug("originaFileName : " + multipartFile.getOriginalFilename());
			int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
			String fileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
			logger.debug("fileExt : " + fileExt);
			//여기서 file_ext가 값이 없는 상태이면("") 파일선택하지 않은 것으로 간주하여 insert시키지 말아야 한다.
			if(fileExt.equals("")) {
				continue;
			}
			
			
			/*
			 * 3. 파일 타일
			 * */
			String fileType = multipartFile.getContentType();
			logger.debug("fileType : " + fileType);
			
			/*
			 * 4. 파일 사이즈
			 * */
			long longFileSize = multipartFile.getSize();
			logger.debug("longFileSize : " + longFileSize);
			int fileSize = (int)longFileSize;
			logger.debug("fileSize : " + fileSize);
			
			/*
			 * 5. 파일 저장(매개변수 path를 사용)
			 * File file = new File(path+"/"+filename+"."+fileExt);
			 * */
			logger.debug("파일 저장 경로 : " + path+filename+"."+fileExt);
			File file = new File(path+filename+"."+fileExt);
			
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BoardFile boardFile = new BoardFile();
			
			boardFile.setBoardFileName(filename);
			boardFile.setBoardFileExt(fileExt);
			boardFile.setBoardFileType(fileType);
			boardFile.setBoardFileSize(fileSize);
			logger.debug("boardFile : " + boardFile.toString());
			
			board.getBoardFile().add(boardFile);
		}
		/* forEach문 종료 */
		/* 이곳까지 도달하면  multipartFile에 대한 해석이 끝나며  해석된 정보들은 boardFile객체에 담기며,
		 * 파일의 정보가 담겨진 boardFile은 board객체의 맴버객체참조변수인 
		 * List<BoardFile> boardFile에 add()메서드를 통해 담기게 된다.
		 * 그렇게 되면 board객체의 맴버변수인 boardId, boardTitle, boardContent를 통해 
		 * board 테이블을 업데이트 시킬수 잇으며,
		 * board의 맴버변수인 boardFile에 담긴 맴버변수들을 통해 boardFile을 업데이트 시킬 수 있다. 
		 * */
		boardDao.updateBoard(board);
		int boardId = board.getBoardId();
		logger.debug("boardId : " + boardId);
		for(BoardFile boardFile : board.getBoardFile()) {
			boardFile.setBoardId(boardId);
			logger.debug("boardFile.getBoardId() : " + boardFile.getBoardId());
			boardFileDao.insertBoardFile(boardFile);
		}
		
		
		
		
		//return boardDao.updateBoard(board);
	}
	
	public List<BoardFile> selectBoardFileListForDelete(int boardId) {
		return boardFileDao.selectBoardFileListForDelete(boardId);
	}
	
	@Transactional
	public void deleteBoard(int boardId, List<BoardFile> boardFileList) {
		logger.debug("deleteBoard BoardService");
		/* 1. 하드디스크에서 boardId에 관련된 boardFile삭제
		 * boardFileList를 활용하여 하드디스크에 있는 boardId에 관련된 boardFile삭제
		 **/
		for(BoardFile boardFile : boardFileList) {
			File file = new File(SystemPath.SYSTEM_PATH + boardFile.getBoardFileName() + "." + boardFile.getBoardFileExt());
			if(file.exists()) {
				if(file.delete()) {
					logger.debug("파일 삭제 성공");
				}else {
					logger.debug("파일 삭제 실패");
				}
			}else {
				logger.debug("파일이 없음.");
			}
		}
		
		/* 2. DB에서 boardId에 관련된 boardFile삭제
		 **/
		int resultDeleteBoardFile = boardFileDao.deleteBoardFile(boardId);
		
		/* 3. DB에서 boardId에 관련된 board 삭제
		 **/  
		int resultDeleteBoard = boardDao.deleteBoard(boardId);
	}
	
	@Transactional
	public void deleteBoardFileOne(int boardFileId, String boardFileName, String boardFileExt) {
		logger.debug("deleteBoardFile BoardService");
		/* DB에서 파일 정보를 삭제하는 과정 */
		int result = boardFileDao.deleteBoardFileOne(boardFileId);
		
		/* 하드디스크에서 파일을 삭제하는 과정 */
		File file = new File(SystemPath.SYSTEM_PATH + boardFileName + "." + boardFileExt);
		if(file.exists()) {
			if(file.delete()) {
				logger.debug("파일 삭제 성공");
			}else {
				logger.debug("파일 삭제 실패");
			}
		}else {
			logger.debug("파일이 없음.");
		}
	}
	
	public void downloadBoardFile(String fileName
								,String fileExt
								,HttpServletRequest request
								,HttpServletResponse response) {
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferdInputStream = null;
		ServletOutputStream ServletOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		
		String path = SystemPath.SYSTEM_PATH+fileName+"."+fileExt;
		File file = new File(path);
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("rv:11") > -1;
		String fileNameTwo = null;
		
		try {
			if(ie) {
				fileNameTwo = URLEncoder.encode(file.getName(), "utf-8");
			}else {
				fileNameTwo = new String(file.getName().getBytes("utf-8"), "iso-8859-1");
			}
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileNameTwo + "\";");
			
			fileInputStream = new FileInputStream(file);
			bufferdInputStream = new BufferedInputStream(fileInputStream);
			ServletOutputStream = response.getOutputStream();
			bufferedOutputStream = new BufferedOutputStream(ServletOutputStream);
			
			byte[] data = new byte[2048];
			int input = 0;
			while((input=bufferdInputStream.read(data)) != -1) {
				bufferedOutputStream.write(data, 0, input);
				bufferedOutputStream.flush();
			}
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(bufferedOutputStream!=null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bufferdInputStream!=null) {
				try {
					bufferdInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ServletOutputStream!=null) {
				try {
					ServletOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileInputStream!=null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Board getDetailBoard(int boardId) {
		logger.debug("getDetailBoard BoardService");
		
		//보드 아이디를 받아서 보드 아이디에 대한 보드파일 카운트를 센다.
		int result = boardFileDao.getCountBoardFile(boardId);
		Board board = null;
		//아이디에 대한 보드파일 카운팅이 0이면 board정보만을 가져오고
		//0이 아닐경우 (보드파일이 존재할 경우) 파일에 대한 정보까지 가져온다.
		if(result == 0) {
			board = boardDao.getBoardOne(boardId); 
		}else {
			board = boardDao.getDetailBoard(boardId);
		}
		return board;
	}
	
	public Map<String, Object> getBoardList(int currentPage, int pagePerRow, String searchSelect, String searchWord){
		logger.debug("getBoardList BoardService");
		int beginRow = (currentPage-1)*pagePerRow;
		
		/*Map<String, Integer> map = new HashMap<String, Integer>();*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginRow", beginRow);
		map.put("pagePerRow", pagePerRow);
		logger.debug("currentPage :" + currentPage);
		logger.debug("beginRow :" + beginRow);
		logger.debug("pagePerRow :" + pagePerRow);
		
		/* searchSignal : 1 일경우 '검색버튼'을 누른경우가 되므로 
		 * selectAddressList() 메서드를 사용하여 list를 가져올때,
		 * searchWord와 일치하는 레코드 부분만 list에 저장하게 만들어 주어야 한다.
		 * 그러기 위해서는 searchWord 값 정보와
		 * searchSignal 정보에 따른 분기가 필요할지 모르므로 map에 넣어준다. 
		 * */
		
		map.put("searchSelect", searchSelect);
		map.put("searchWord", searchWord);
		logger.debug("searchSelect :" + searchSelect);
		logger.debug("searchWord :" + searchWord);
		
		/* beginRow와 pagePerRow값에 따라 SQL문의 LIMIT문이 작동될 것이고
		 * 그에 맞는 list가 반환된다.
		 * 검색 또한 마찬가지로 검색결과에 맞는 레코드들이
		 * SQL문의 LIMIT문에 의해 제한되어 list에 저장된다.
		 * (pagePerRow값이 10이면 list에 담기는 개수는 10개 레코드이다.)
		 * */
		List<Board> list = boardDao.getBoardList(map);
		
		/* 검색을 하였다면 검색조건에 맞는 레코드 개수가 반환되고,
		 * 검색을 하지 않았다면 DB에 존재하는 모든 address 레코드 개수가 반환된다. 
		 *  */
		int total = boardDao.totalCountBoard(map);
		
		/* DB에 address 레코드 수가 1개도 존재하지 않는 경우 == 초기상태일때, 1페이지로 나오게 lastPage를 1로 초기화 한다.*/
		int lastPage = 0;
		if(0 == total) {
			lastPage = 1;
		}else if(total%pagePerRow == 0) {
			lastPage = total/pagePerRow;
		}else {
			lastPage = total/pagePerRow + 1;
		}
		/* 페이지가 5개 단위씩 보이게 하는 계산식 */
		int temp = (currentPage - 1)/5;
		int beginPageNumForCurrentPage = temp * 5 + 1;
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("lastPage", lastPage);
		returnMap.put("beginPageNumForCurrentPage", beginPageNumForCurrentPage);
		
		return returnMap;

	}
	
	@Transactional
	public void insertBoard(BoardRequest boardRequest, String path) {
		//여기서 file_ext가 null이면 파일선택하지 않은 것으로 간주하여 insert시키지 말아야 한다.
		//MultipartFile multipartFile = boardRequest.getMultipartFile();
		
		
		Board board = new Board();
		board.setBoardTitle(boardRequest.getBoardTitle());
		board.setBoardContent(boardRequest.getBoardContent());
		
		List<MultipartFile> multipartFileList = boardRequest.getMultipartFile();
		//List<BoardFile> boardFileList = new ArrayList<BoardFile>();
		for(MultipartFile multipartFile : multipartFileList) {
			
			
			
			/* 
			 * 1. 파일 이름
			 * 16진수의 UUID타입이 만들어진다.
			 * - 문자를 아무것도 없는 문자로 변경시킨다.
			 * */
			UUID uuid = UUID.randomUUID();
			logger.debug("uuid : " + uuid);
			String filename = uuid.toString();
			logger.debug("filename : " + filename);
			filename = filename.replace("-", "");
			logger.debug("repalced filename : " + filename);
			
			
			/*
			 * 2. 파일 확장자
			 * */
			logger.debug("originalFilename : " + multipartFile.getOriginalFilename());
			int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
			String fileExt = multipartFile.getOriginalFilename().substring(dotIndex+1);
			logger.debug("fileExt : " + fileExt);
			//여기서 file_ext가 값이 없는 상태이면("") 파일선택하지 않은 것으로 간주하여 insert시키지 말아야 한다.
			if(fileExt.equals("")) {
				continue;
			}
			
			/*
			 * 3. 파일 타입
			 * */
			String fileType = multipartFile.getContentType();
			logger.debug("fileType : " + fileType);
			
			
			/*
			 * 4. 파일 사이즈 
			 * */
			long longFileSize = multipartFile.getSize();
			logger.debug("longFileSize : " + longFileSize);
			int fileSize = (int)longFileSize;
			logger.debug("fileSize : " + fileSize);
			
			
			/*
			 * 5. 파일 저장(매개변수 path를 사용)
			 * File file = new File(path+"/"+filename+"."+fileExt);
			 * */
			//File file = new File("d:\\upload\\"+filename+"."+fileExt);
			logger.debug("파일 저장 경로 : " + path+filename+"."+fileExt);
			File file = new File(path+filename+"."+fileExt);
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/* 
			 * boardRequest가 multipartFile을 가지고 있다.
			 * multipartFile -> articleFile의 내용으로 맞추어 준다.
			 *  */
			BoardFile boardFile = new BoardFile();
			
			boardFile.setBoardFileName(filename);
			boardFile.setBoardFileExt(fileExt);
			boardFile.setBoardFileType(fileType);
			boardFile.setBoardFileSize(fileSize);
			logger.debug("boardFile : " + boardFile);
			
			
			board.getBoardFile().add(boardFile);
			//board.setBoardFile(boardFile);
		}
		
		
		logger.debug("board : " + board);
		boardDao.insertBoard(board);
		
		for(BoardFile boardFile : board.getBoardFile()) {
			boardFile.setBoardId(board.getBoardId());
			boardFileDao.insertBoardFile(boardFile);
			logger.debug("boardFile.getBoardId() : " + boardFile.getBoardId());
		}
		//board.getBoardFile().setBoardId(board.getBoardId());
		
		//logger.debug("board.getBoardFile().getBoardId() : " + board.getBoardFile().getBoardId());
		
		//boardFileDao.insertBoardFile(board.getBoardFile());
		
		
	}
}



















