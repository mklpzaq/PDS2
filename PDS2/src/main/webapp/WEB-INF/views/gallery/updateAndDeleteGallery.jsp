<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Update Form</title>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<jsp:include page="../navbar.jsp"/>
		<div class="container-fluid">
			<div class="row">
			<div class="col-sm-3"></div>
				<form action="${pageContext.request.contextPath}/updateAndDeleteGallery"  method="post">
					<div class="col-sm-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-sm-2">
										사진관아이디<br> <input type="text"  value="${updateGallery.galleryId}" name="galleryId" readonly>										
									</div>
									<div class="col-sm-10 text-center">
										사진관제목<br>  <input type="text"  value="${updateGallery.galleryTitle}" name="galleryTitle"> 										
									</div> 							
								</div>
							</div>
							<div class="panel-body" style="height:100%;">								
									<input name="galleryContent" style="height:90%; width:90%;" value="${updateGallery.galleryContent}" type="text">
								<div>
									첨부파일 : <br/>
									<c:forEach var="galleryFile" items="${list}">
										<%-- <img src="${pageContext.request.contextPath}/downloadGallery?galleryFileName=${galleryFile.galleryFileName}&galleryFileExt=${galleryFile.galleryFileExt}" alt="140x140" class="img-circle" ${galleryFile.galleryFileName}.${galleryFile.galleryFileExt}/> --%>             
										<img data-src="holder.js/140x140" class="img-circle" alt="140x140" src="${pageContext.request.contextPath}/downloadGallery?galleryFileName=${galleryFile.galleryFileName}&galleryFileExt=${galleryFile.galleryFileExt}" data-holder-rendered="true" style="width: 140px; height: 140px;">
										<br/>
									</c:forEach>
								</div>
							</div>								
						</div>
							<button type="submit" class="btn btn-default">수정/삭제 완료</button>
					</div>
				</form> 			   
			</div>
		</div>
</body>
</html>