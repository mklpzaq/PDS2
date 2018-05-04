<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Update Form</title>
	<script type="text/javascript">
	$(document).ready(function(){		
		$('#add').click(function(){
			$('#upload').append('<div><label for="multipartfile">파일 업로드</label><input type="file" name="multipartFile"><button id="del" type="button">삭제</button></div>');
		});
		
		$(document).on('click','#del',function(){
			$(this).parent('div').remove();
		});
		
		$(document).on('click','#delImg',function(){
			var alt = $(this).parent('div').find('img').attr('alt');
			$(this).parents('form').append('<input name="deleteImg" type="hidden" value="'+alt+'">');
			$(this).parent('div').find('img').remove();
			$(this).remove();
		});
		
		$('button#updateAndDelete').click(function(){
				var insert = true;
				if($('input[name="galleryTitle"]').val() == ''){
					alert('galleryTitle를 작성해주세요');
					insert = false;
					return;
				}
				
				if($('textarea[name="galleryContent"]').val() == ''){
					alert('galleryContent를 작성해주세요');
					insert = false;
					return;
				}					
				
				if($(document).find('input[type="file"]').length >= 4){
					alert('이미지파일을 최대 3개까지 등록가능합니다.');
					insert = false;
					return;
				}
		
				$('input[type="file"]').each(function(i,e){
					if($(this).val()==''){
						alert('이미지 파일을 올려주세요.');
						insert = false;
					}
					if($(this).val != ''){
						var extension = $(this).val().split('.').pop().toLowerCase();
						console.log('extension : ' + extension);
						
						if($.inArray(extension,['xwd','xpm','xbm','rgb','ppm','pgm','pbm','pnm','ras','tif','tiff','ief','gif','jpg','jpeg','png']) == -1){
							alert('이미지 파일이 아닙니다.');
							insert = false;
						}					
					}
				});		
			
				if(updateAndDelete){
					$('form').submit();
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
				<form action="${pageContext.request.contextPath}/updateAndDeleteGallery"  method="post" enctype="multipart/form-data">
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
									<textarea name="galleryContent" class="panel-title" style="height:90%; width:90%;">${updateGallery.galleryContent}</textarea>
								<div>
									첨부파일 : <br/>
									<c:forEach var="galleryFile" items="${list}">
										<%-- <img src="${pageContext.request.contextPath}/downloadGallery?galleryFileName=${galleryFile.galleryFileName}&galleryFileExt=${galleryFile.galleryFileExt}" alt="140x140" class="img-circle" ${galleryFile.galleryFileName}.${galleryFile.galleryFileExt}/> --%>             
										<div>
											<img data-src="holder.js/140x140" class="img-circle" alt="${galleryFile.galleryFileName}.${galleryFile.galleryFileExt}" src="${pageContext.request.contextPath}/downloadGallery?galleryFileName=${galleryFile.galleryFileName}&galleryFileExt=${galleryFile.galleryFileExt}" data-holder-rendered="true" style="width: 140px; height: 140px;">
											<button id="delImg" type="button">이미지 파일 삭제</button>
										</div>										
									</c:forEach>
									<div id="upload">
									</div>
								<button id="add" type="button">이미지추가</button><br><br>
								</div>								
							</div>								
						</div>							
							<button id="updateAndDelete" type="button" class="btn btn-default">수정/삭제 완료</button>
					</div>
				</form> 			   
			</div>
		</div>
</body>
</html>