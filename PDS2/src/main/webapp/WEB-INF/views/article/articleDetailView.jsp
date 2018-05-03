<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Article Detail View</title>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					
					<!-- detailBoard를 쓰자 -->
					<h3 class="text-center">Article Detail</h3>
					<div class="panel panel-default">
						<div class="panel-heading">articleId : ${detailArticle.articleId}, articleTitle : ${detailArticle.articleTitle}</div>
						<div class="panel-body">
							articleContent : ${detailArticle.articleContent}
							<hr/>
							<c:forEach var="articleFile" items="${detailArticle.articleFile}">
								<div> 
									articleFileId : ${articleFile.articleFileId}<br/>
									articleId : ${articleFile.articleId}<br/>
									articleFileName : ${articleFile.articleFileName}<br/>
									articleFileExt : ${articleFile.articleFileExt}<br/>
									articleFileType : ${articleFile.articleFileType}<br/>
									articleFileSize : ${articleFile.articleFileSize}<br/>
									<a href="${pageContext.request.contextPath}/downloadArticleFile?sendNo=${articleFile.articleId}&fileName=${articleFile.articleFileName}&fileExt=${articleFile.articleFileExt}">파일 다운로드</a>
									 | <a href="${pageContext.request.contextPath}/deleteArticleFile?sendNo=${articleFile.articleId}&sendFileNo=${articleFile.articleFileId}&fileName=${articleFile.articleFileName}&fileExt=${articleFile.articleFileExt}">파일 삭제</a>
								</div>
								<hr/>
							</c:forEach>
							
						</div>
					</div>
					
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		
		

		
	</body>
</html>