<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Home</title>
	</head>
	<body>
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
				
				
					<div class="text-center">
						<h1>Welcome Teme2 Page!</h1>
						<P>The time on the server is ${serverTime}.</P>
						
						<a href="#">articleList</a> |
						<a href="${pageContext.request.contextPath}/getBoardList">boardList</a> |
						<a href="#">galleryList</a> |
						<a href="${pageContext.request.contextPath}/selectNoticeList">noticeList</a> |
						<a href="${pageContext.request.contextPath}/resumeList">resumeList</a>
					</div>
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		
	</body>
</html>
