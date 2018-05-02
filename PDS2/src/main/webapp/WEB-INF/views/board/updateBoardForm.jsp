<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					<form class="form-horizontal" action="${pageContext.request.contextPath}/updateBoard" method="post">					
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="form-group">
									<label for="boardId" class="col-sm-2 control-label">boardId</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="boardId" name="boardId" value="${board.boardId}" placeholder="boardId" readonly>
										
									</div>
								</div>
								<div class="form-group">
									<label for="boardTitle" class="col-sm-2 control-label">boardTitle</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="boardTitle" name="boardTitle" value="${board.boardTitle}" placeholder="boardTitle">
									</div>
								</div>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="boardContent" class="col-sm-2 control-label">boardContent</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="boardContent" name="boardContent" value="${board.boardContent}" placeholder="boardContent">
									</div>
								</div>
								
								<c:forEach var="boardFile" items="${board.boardFile}">
									<!-- 파일 속성 시작 -->
									<hr/>
									<div class="bs-example" data-example-id="bordered-table">
										<table class="table table-bordered">
											<thead>
												<tr>
													<td><strong>파일 속성</strong></td>
													<td><strong>파일 값</strong></td>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><strong>boardFileId</strong></td>
													<td>${boardFile.boardFileId}</td>
												</tr>
												<tr>
													<td><strong>boardId</strong></td>
													<td>${boardFile.boardId}</td>
												</tr>
												<tr>
													<td><strong>boardFileName</strong></td>
													<td>${boardFile.boardFileName}</td>
												</tr>
												<tr>
													<td><strong>boardFileExt</strong></td>
													<td>${boardFile.boardFileExt}</td>
												</tr>
												<tr>
													<td><strong>boardFileType</strong></td>
													<td>${boardFile.boardFileType}</td>
												</tr>
												<tr>
													<td><strong>boardFileSize</strong></td>
													<td>${boardFile.boardFileSize}</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!-- 파일 속성 끝 -->
								</c:forEach>
								
								
							</div>
						</div>					
					</form>					
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		</body>
</html>	