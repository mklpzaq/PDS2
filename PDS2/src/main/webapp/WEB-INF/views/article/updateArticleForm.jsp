<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Update Board</title>
		<script>
			$(document).ready(function(){
				var count = 0;
				$('#addFilebutton').click(function(){
					/* $('#addFileDiv').append('파일추가 : <input type="file" name="multipartFile"><br/>'); */
					$('#addFileDiv').append('<div class="form-group"><label for="multipartFile" class="col-sm-3 control-label">파일 업로드</label><div class="col-sm-9"><input type="file" name="multipartFile"><p class="help-block">업로드할 파일을 선택하세요</p></div></div>');
				});
				
				/*
				// ==== 선택한 파일의 확장자 확인하기
				if($('#addFileDiv input').length > 0){
					for(int i = 0; i < $('#addFileDiv input').length; ++i){
						if()
					}
					
					
				}
				
				var str = $('#addFileDiv input').eq(1).val();
				var start = $('#addFileDiv input').eq(1).val().lastIndexOf(".") + 1;
				var length = $('#addFileDiv input').eq(1).val().length;
				var mimeType = str.substring(start, length);
				alert(mimeType);
				
				//포이치문 
				$('#addFileDiv input').each(function(index) {

		            alert($(this).val());
				});
				
				
				//이벤트 
				//change select
				//윈도우 이벤트
				//load 문서객체를 불러들일때 발생한다.
				//unload 문서 객체를 닫을 때 발생한다.
				//글자자르기
				//substring( 0, 40 )
				//마지막 문자 위치
				//lastIndexOf(".") 찾는 검색어가 없으면 -1을 리턴한다.
				//subString([시작위치], [종료위치]);
				//subString(9, 16)
				$('#addFileDiv input').eq(0).change(function(){
					alert('바뀌었다.')
				});
				*/
				
			});
		</script>
		
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					
					<h3 class="text-center">Update Article</h3>
					<form class="form-horizontal" action="${pageContext.request.contextPath}/updateArticle" method="post" enctype="multipart/form-data">					
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="form-group">
									<label for="articleId" class="col-sm-2 control-label">articleId</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="articleId" name="articleId" value="${article.articleId}" placeholder="articleId" readonly>
										
									</div>
								</div>
								<div class="form-group">
									<label for="articleTitle" class="col-sm-2 control-label">articleTitle</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="articleTitle" name="articleTitle" value="${article.articleTitle}" placeholder="articleTitle">
									</div>
								</div>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="articleContent" class="col-sm-2 control-label">articleContent</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="articleContent" name="articleContent" value="${article.articleContent}" placeholder="articleContent">
									</div>
								</div>
								
								<c:forEach var="articleFile" items="${article.articleFile}">
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
													<td><strong>articleFileId</strong></td>
													<td>${articleFile.articleFileId}</td>
												</tr>
												<tr>
													<td><strong>articleId</strong></td>
													<td>${articleFile.articleId}</td>
												</tr>
												<tr>
													<td><strong>articleFileName</strong></td>
													<td>${articleFile.articleFileName}</td>
												</tr>
												<tr>
													<td><strong>articleFileExt</strong></td>
													<td>${articleFile.articleFileExt}</td>
												</tr>
												<tr>
													<td><strong>articleFileType</strong></td>
													<td>${articleFile.articleFileType}</td>
												</tr>
												<tr>
													<td><strong>articleFileSize</strong></td>
													<td>${articleFile.articleFileSize}</td>
												</tr>
											</tbody>
										</table>
									</div>
									<a href="${pageContext.request.contextPath}/deleteArticleFile?pageCode=update&sendNo=${articleFile.articleId}&sendFileNo=${articleFile.articleFileId}&fileName=${articleFile.articleFileName}&fileExt=${articleFile.articleFileExt}">파일 삭제</a>
									<!-- 파일 속성 끝 -->
								</c:forEach>
								
								
								<hr/>
								<div id="addFileDiv">
									
								</div>
								<button id="addFilebutton" type="button">파일추가</button>
							</div>
						</div>
						<button type="submit">수정완료</button>				
					</form>					
					
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</body>
</html>	