<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Insert title here</title>
		<script>
		
		$(document).ready(function(){
			$("#btn").click(function(){
				$("#div").hide();
				$('input[name="deleteCheckbox"]:checked').each(function(){							
					var text = $(this).val();				
					$("#form1").submit();	
				});
			})	
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
					<div class="text-center">
						<form action="${pageContext.request.contextPath}/updateResume" method="post" enctype="multipart/form-data">
							<div><input type="text" value="${updateResume.resumeTitle}" name="resumeTitle"></div> 
							<textarea name="resumeContent">${updateResume.resumeContent}</textarea><br/>					
						</form>
						<c:forEach var="resumeFile" items="${list}">
								<div id="div">${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}
								<button id="btn" type="button" name="resumeFileId" value="${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}">삭제</button><br/>
								</div>
						</c:forEach>	
						<div><input type="file" name="multipartFile"></div><br/>					
						<div><input type="submit" value="수정"></div>
					</div>
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</body>
</html>