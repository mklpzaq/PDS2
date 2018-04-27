<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Board Detail View</title>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					
					<!-- detailBoard를 쓰자 -->
					<h3 class="text-center">Board Detail</h3>
					<div class="panel panel-default">
						<div class="panel-heading">boardId : ${detailBoard.boardId}, boardTitle : ${detailBoard.boardTitle}</div>
						<div class="panel-body">
							boardContent : ${detailBoard.boardContent}
							<hr/>
							<c:forEach var="boardFile" items="${detailBoard.boardFile}">
								<div> 
									boardFileId : ${boardFile.boardFileId}<br/>
									boardId : ${boardFile.boardId}<br/>
									boardFileName : ${boardFile.boardFileName}<br/>
									boardFileExt : ${boardFile.boardFileExt}<br/>
									boardFileType : ${boardFile.boardFileType}<br/>
									boardFileSize : ${boardFile.boardFileSize}<br/>
									<a href="${pageContext.request.contextPath}/downloadBoardFile?sendNo=${boardFile.boardId}&fileName=${boardFile.boardFileName}&fileExt=${boardFile.boardFileExt}">파일 다운로드</a>
									 | <a href="${pageContext.request.contextPath}/deleteBoardFile?sendNo=${boardFile.boardId}&sendFileNo=${boardFile.boardFileId}&fileName=${boardFile.boardFileName}&fileExt=${boardFile.boardFileExt}">파일 삭제</a>
								</div>
								<hr/>
							</c:forEach>
							
						</div>
					</div>
					
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		
		

		
	</body>
</html>