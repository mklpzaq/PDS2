<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Insert Article</title>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					
					<div class="panel panel-default">
						<div class="panel-body">
							<h3 class="text-center">Insert Article</h3>
							<hr/>
							<form class="form-horizontal" action="${pageContext.request.contextPath}/insertArticle" method="post" enctype="multipart/form-data">
								<div class="form-group">
									<label for="articleTitle" class="col-sm-3 control-label">articleTitle</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" name="articleTitle" id="articleTitle" placeholder="articleTitle">
									</div>
								</div>
								<div class="form-group">
									<label for="articleContent" class="col-sm-3 control-label">articleContent</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" name="articleContent" id="articleContent" placeholder="articleContent">
									</div>
								</div>
								
								<div class="form-group">
									<label for="multipartFile" class="col-sm-3 control-label">파일 업로드</label>
									<div class="col-sm-9">
										<input type="file" name="multipartFile">
										<p class="help-block">업로드할 파일을 선택하세요</p>
									</div>
								</div>
								<div class="form-group">
									<label for="multipartFile" class="col-sm-3 control-label">파일 업로드</label>
									<div class="col-sm-9">
										<input type="file" name="multipartFile">
										<p class="help-block">업로드할 파일을 선택하세요</p>
									</div>
								</div>
								<div class="text-center">
									<button type="submit" class="btn btn-default">입력 완료</button>
								</div>
							</form>
						</div>
					</div>
					
					
					
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		
	</body>
</html>