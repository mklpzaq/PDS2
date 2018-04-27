<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Notice View</title>
		<style>
			.none{display:none;}
		</style>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8">
						<!-- Notice View -->			
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-sm-2">
										번호 : ${noticeView.noticeId}<br/>										
									</div>
									<div class="col-sm-10 text-center">
										제목 : ${noticeView.noticeTitle}										
									</div>
								</div>
							</div>
							<div class="panel-body" style="height:100%;">								
								<textarea class="panel-title" style="height:90%; width:90%;">${noticeView.noticeContent}</textarea>
								<div>
									첨부파일 : <br/>
									<c:forEach var="noticeFile" items="${list}">								
										<a href="${pageContext.request.contextPath}/downloadNotice?noticeFileName=${noticeFile.noticeFileName}&noticeFileExt=${noticeFile.noticeFileExt}">${noticeFile.noticeFileName}.${noticeFile.noticeFileExt}</a>
										<button><a href="${pageContext.request.contextPath}/deleteNoticeFile?noticeFileId=${noticeFile.noticeFileId}&noticeFileName=${noticeFile.noticeFileName}&noticeFileExt=${noticeFile.noticeFileExt}">삭제</a></button><br/>
									</c:forEach>
								</div>
							</div>
						</div>
						<div>
							<a href="./updateNotice?noticeNo=${notice.noticeId}">[수정]</a>&nbsp;&nbsp;
							<a href="./deleteNotice?noticeId=${noticeView.noticeId}">[삭제]</a>&nbsp;&nbsp;
							<a href="./selectNoticeList">[목록]	</a>
						</div>		
						<!-- Notice View -->
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
	</body>
</html>