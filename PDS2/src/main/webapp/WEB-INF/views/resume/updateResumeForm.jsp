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
		
		/* $("[id^=btn]").click(function(){
			$("#div").hide();
			$("body").on("click", "[id^=btn]", function(){ 	
		}) */
					
		$(document).ready(function(){
			$('#fileAdd').click(function(){
				$('#addFile').append('<div><input name="multipartFile" type="file"><button id="del" type="button">삭제</button></div>');
			});	
			
			$(document).on('click','#del',function(){
				$(this).closest('div').remove();
			});
			
			//파일 삭제하는 부분
			$(".removeFile").click(function(){				
				var deleteFile = "<input type='hidden' name='deleteFile' value='"+$(this).attr("value")+"'>";
				$("#submitButton").before(deleteFile);
				$(this).closest("div").remove();
			});
			
		
			$("#update").click(function(){
				$("#form1").submit();							
			});			
		
			
		});
		
		//prompt(''); alter같이 팝업창을 뜨게해주는 메서드인데 단지 팝업창에 입력창이 같이 들어가 있음
			
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
									<input type="hidden" name="resumeId" value="${updateResume.resumeId}">
									<div><input type="text" value="${updateResume.resumeTitle}" name="resumeTitle"></div> 
									<textarea name="resumeContent">${updateResume.resumeContent}</textarea><br/>
								</fieldset><br/>						
															
								<div id="addFile"></div>							
								<div><button id="fileAdd" type="button">파일추가</button></div><br/>
								
								<div id="submitButton"><input id="update" type="button" value="수정">&emsp;<input type="reset" value="다시 입력"></div>	
							</form>	
							
							
																
					<!-- 여러개의 form을 한번에 보낼수 있는지 없는지 궁금하다  -->
					</div>
					
					<fieldset>
						<legend>파일 수정 공간</legend>	
						<c:forEach var="resumeFile" items="${list}">	<!-- varStatus="status" foreach문에서 한번 돌때마다 숫자를 카운트 할 수 있게 해주는 함수? id="div${status.count}" 이런식으로 사용한다 -->							
								<div id="div">${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}
								<button class="removeFile" type="button" name="resumeFile" value="${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}">삭제</button><br/>
								</div>
						</c:forEach>
					</fieldset>
			
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</body>
</html>