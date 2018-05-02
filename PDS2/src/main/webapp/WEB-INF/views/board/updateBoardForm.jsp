<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Update Board</title>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					<h3 class="text-center">Update Board</h3>
					<div class="panel panel-default">
						
						<form action="${pageContext.request.contextPath}/updateBoard" method="post">
							<div class="panel-heading">
								boardId : <input name="boardId" value="${board.boardId}" readonly><br/>
								boardTitle : <input name="boardTitle" value="${board.boardTitle}">							
							</div>
							<div class="panel-body">
								boardContent : <input name="boardContent" value="${board.boardContent}">
								<c:forEach var="boardFile" items="${board.boardFile}">
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
							<button type="submit">수정완료</button>
						</form>
						
					</div>
						
						
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		</body>
</html>	