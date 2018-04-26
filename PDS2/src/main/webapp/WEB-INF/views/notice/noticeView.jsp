<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Notice View</title>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8">
					<!-- Notice View -->
						<div>
							<a href="./updateNotice?noticeId=${notice.noticeId}">
								[수정]
							</a>&nbsp;&nbsp;
							<a href="./deleteNotice?noticeId=${notice.noticeId}">
								[삭제]
							</a>&nbsp;&nbsp;
							<a href="./noticeList">
								[목록]
							</a>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-sm-4">
										번호 : ${noticeView.noticeId}<br/>
										제목 : ${noticeView.noticeTitle}
									</div>
									<div class="col-sm-4 text-center">
										<h3 class="panel-title">${noticeView.noticeContent}</h3>
									</div>
								</div>
							</div>
							<div class="panel-body" style="height:100px;">
								첨부파일
								<c:forEach var="notideFile" items="${list}">								
									<a href="${pageContext.request.contextPath}/download?path=${path}&noticeFileName=${noticeFile.noticeFileName}&noticeFileExt=${noticeFile.noticeFileExt}">${noticeFile.noticeFileName}.${noticeFile.noticeFileExt}</a><br/>
								</c:forEach>
							</div>
						</div>
					<!-- Notice View end -->
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
	
	</body>
</html>