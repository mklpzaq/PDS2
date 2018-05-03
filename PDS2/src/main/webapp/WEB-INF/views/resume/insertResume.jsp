<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Insert title here</title>
		<script type="text/javascript">
		
		$(document).ready(function(){
			$('#fileAdd').click(function(){
				$('#addFile').append('<div><input name="multipartFile" type="file"><button id="del" type="button">삭제</button></div>');
			});	
			
			$(document).on('click','#del',function(){
				$(this).closest('div').remove();
			});
			
			$('#submit').click(function(){
				if($("#resumeTitle").val().length < 1){
					alert('제목을 작성하세요');
				}else if($("#resumeContent").val().length < 1){
					alert('내용을 입력하세요')
				}else{
					$("#form2").submit();
				}	
			});
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
								<form id="form2" action="${pageContext.request.contextPath}/insertResume" method="post" enctype="multipart/form-data">
									<div><input type="text" id="resumeTitle" name="resumeTitle"></div> <!-- ResumeRequest클래스의 String resumeTitle, db Resume테이블의 resume_title컬럼에 셋팅될 데이터 -->
									<textarea id="resumeContent" name="resumeContent"></textarea> <!-- ResumeRequest클래스의 String resumeContent, db Resume테이블의 resume_content컬럼에 셋팅될 데이터 -->
									<div id="addFile"></div>		<!-- ResumeRequest클래스의 List<MultipartFile> multipartFile에 셋팅될 데이터 -->					
									<div><button id="fileAdd" type="button">파일추가</button></div><br/>									
								</form>
									<div><button id="submit" type="button">작성</button></div>
							</div>
						<!-- End Content -->
						</div>
						<div class="col-sm-3"></div>
					</div>
				</div>
		
	</body>
</html>