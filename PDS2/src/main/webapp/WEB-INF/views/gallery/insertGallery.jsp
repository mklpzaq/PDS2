<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>Insert Gallery</title>

	<script>
		$(document).ready(function(){
			${error};
			
			$('#add').click(function(){
				$('#upload').append('<div><label for="multipartfile">파일 업로드</label><input type="file" name="multipartfile"></div>');
			});
			
			$('button#insert').click(function(){
					var insert = true;
					if($('input[name="galleryTitle"]').val() == ''){
						alert('galleryTitle를 작성해주세요');
						insert = false;
						return;
					}
					
					if($('input[name="galleryContent"]').val() == ''){
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
				
					if(insert){
						$('form').submit();
					}
				
			});	
		});
	</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/insertGallery" enctype="multipart/form-data" method="post">
		<div class="form-group">
			<label for="galleryTitle">사진관 제목</label>
			<input type="text" class="form-control" name="galleryTitle" placeholder="사진관 제목을 입력하세요">
		</div>
		<div class="form-group">
			<label for="galleryContent">사진관 내용</label>
			<input type="text" class="form-control" name="galleryContent" placeholder="사진관 내용을 입력하세요">
		</div>
		<div class="form-group" id="upload">
			<label for="multipartfile">파일 업로드</label>
			<input type="file" name="multipartfile">			
		</div>					
		<!-- <div class="checkbox">
			<label>
				<input type="checkbox"> 입력을 기억합니다
			</label>
		</div> -->
		<button id="add" type="button">파일추가</button><br><br>					
		<button id="insert" type="button" class="btn btn-default">제출</button>
	</form>	
</body>
</html>