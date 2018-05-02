<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<title>updateNotice</title>
	</head>
	<body>
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h1 class="panel-title">updateNotice</h1>
						</div>
						<div class="panel-body">
							<form action="${pageContext.request.contextPath}/updateNotice" method="post" enctype="multipart/form-data">
								<div class="form-group">
									<label for="InputTitle">noticeTitle</label>
									<input type="text" class="form-control" name="noticeTitle" value="${noticeId.noticeTitle}">
								</div>
								<div class="form-group">
									<label for="InputContent">noticeContent</label>
									<textarea type="text" class="form-control" name="noticeContent" value="${noticeId.noticeContent}"></textarea>
								</div>
								<div class="form-group">
									<label for="InputFile">noticeFile</label>
									<input type="file" id="noticeFile" name="multipartFile">
									<input type="file" id="noticeFile" name="multipartFile">
									<input type="file" id="noticeFile" name="multipartFile">
								</div>
								<button class="btn pull-right" type="submit" class="btn btn-default">수정하기</button>
							</form>
						</div>
					</div>
				</div>
				<div class="col-md-3"></div>
			</div>
		</div>
	</body>
</html>