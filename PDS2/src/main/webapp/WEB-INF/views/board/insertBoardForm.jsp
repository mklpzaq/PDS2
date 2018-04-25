<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Insert Board Form</title>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					
					<h3>insert Board</h3>
					<form action="${pageContext.request.contextPath}/insertBoard" method="post" enctype="multipart/form-data">
						<div>boardTitle : <input type="text" name="boardTitle"></div>
						<div>boardContent : <input type="text" name="boardContent"></div>
						<div>boardFile : <input type="file" name="multipartFile"></div>
						<div>boardFile : <input type="file" name="multipartFile"></div>		
						<button type="submit">입력버튼</button>
					</form>
					
					
					
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		
	</body>
</html>