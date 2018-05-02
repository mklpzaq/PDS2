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
		
		/* $(document).ready(function(){			
			$("[id^=btn]").click(function(){          //제이쿼리쪽은 더 파야겠다
	            console.log(this.id);					//this.id를 해서 id를 가져오고 
	            var vId = this.id;						//가져온 this.id를 함수 vId에 담고
	          	$(this).hide();							//가져온 함수를 ("#vId").hide로 실행시켰는데 왜 안됐는지 모르겠다
	        });				
		}); */
		
		$(document).ready(function(){
			$("#update").click(function(){
				$("input").submit();							
			});			
		});
		
		/* $("[id^=btn]").click(function(){
			$("#div").hide();
			$("body").on("click", "[id^=btn]", function(){ 	
		}) */
		
		$(document).ready(function(){
			$("#button").click(function(){
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
					
							<form id="form1" action="${pageContext.request.contextPath}/updateResume" method="post" enctype="multipart/form-data">
								<fieldset>
									<legend>텍스트수정 공간</legend>
									<div><input type="text" value="${updateResume.resumeTitle}" name="resumeTitle"></div> 
									<textarea name="resumeContent">${updateResume.resumeContent}</textarea><br/>
								</fieldset>	
							</form>							
										
							<form id="form1" action="${pageContext.request.contextPath}/updateResume" method="post" enctype="multipart/form-data">
							<fieldset><br/>
							<legend>파일 수정 공간</legend>	
							<c:forEach var="resumeFile" items="${list}" varStatus="status">									
									<div id="div${status.count}">${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}
									<input type="checkbox" name="deleteCheckbox" value="${resumeFile.resumeFileId}">
									<input type="hidden" name="resumeFileName" value="${resumeFile.resumeFileName}">
									<input type="hidden" name="resumeFileExt" value="${resumeFile.resumeFileExt}">
									<%-- <button id="btn${status.count}" type="button" name="resumeFileId" value="${resumeFile.resumeFileName}/${resumeFile.resumeFileExt}">삭제</button><br/> --%>
									</div>
							</c:forEach>							
							<div><input type="file" name="multipartFile"></div><br/>
							</fieldset>
							</form>	
							
							<div><input id="button" type="button" value="수정"></div>	
							<div><input type="reset" value="다시 입력"></div>											
					<!-- 여러개의 form을 한번에 보낼수 있는지 없는지 궁금하다  -->
					</div>
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</body>
</html>