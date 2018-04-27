<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="../navbar.jsp"/>
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
			<!-- Begin Content -->					
				<div class="text-center">
					<form action="${pageContext.request.contextPath}/insertResume" method="post" enctype="multipart/form-data">
						<div><input type="text" value="${updateResume.resumeTitle}" name="resumeTitle"></div> 
						<textarea name="resumeContent">${updateResume.resumeContent}</textarea><br/>
						<c:forEach var="resumeFile" items="${list}">
							${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}
							<button><a href="${pageContext.request.contextPath}/deleteResumeFile?resumeFileId=${resumeFile.resumeFileId}&resumeFileName=${resumeFile.resumeFileName}&resumeFileExt=${resumeFile.resumeFileExt}">삭제</a></button><br/>
						</c:forEach>
						<div><input type="file" name="multipartFile"></div>	
						<div><input type="file" name="multipartFile"></div>		
						<div><input type="submit"></div>
					</form>
				</div>
			<!-- End Content -->
			</div>
			<div class="col-sm-3"></div>
		</div>
	</div>
</body>
</html>