<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<title>Update Form</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/updateGallery" enctype="multipart/form-data" method="post">
		<div class="form-group">
			<label for="galleryTitle">사진관 아이디</label>
			<input type="text" class="form-control" value="${galleryForId.galleryId}" readonly>
		</div>
		<div class="form-group">
			<label for="galleryTitle">사진관 제목</label>
			<input type="text" class="form-control" name="galleryTitle" value="${galleryForId.galleryTitle}">
		</div>
		<div class="form-group">
			<label for="galleryContent">사진관 내용</label>
			<input type="text" class="form-control" name="galleryContent" value="${galleryForId.galleryContent}">
		</div>		
		<!-- <div class="checkbox">
			<label>
				<input type="checkbox"> 입력을 기억합니다
			</label>
		</div> -->			
		<button type="submit" class="btn btn-default">제출</button>
	</form>	
</body>
</html>