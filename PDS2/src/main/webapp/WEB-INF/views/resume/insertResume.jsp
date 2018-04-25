<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
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
								<div><input type="text" name="resumeTitle"></div>
								<textarea name="resumeContent"></textarea>
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