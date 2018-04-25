<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<title>Insert Gallery</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/insertGallery" enctype="multipart/form-data" method="post">
		<div class="form-group">
			<label for="galleryTitle">사진관 제목</label>
			<input type="text" class="form-control" name="galleryTitle" placeholder="사진관 제목을 입력하세요">
		</div>
		<div class="form-group">
			<label for="galleryContent">사진관 내용</label>
			<input type="text" class="form-control" name="galleryContent" placeholder="사진관 내용을 입력하세요">
		</div>
		<div class="form-group">
			<label for="multipartfile">파일 업로드</label>
			<input type="file" name="multipartfile">			
		</div>
		<div class="form-group">
			<label for="multipartfile">파일 업로드</label>
			<input type="file" name="multipartfile">			
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