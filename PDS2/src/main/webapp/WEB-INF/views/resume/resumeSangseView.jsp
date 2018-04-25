<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Resume Sangse View</title>
		<style>
			.none{display:none;}
		</style>
		<script>
			/* $(document).ready(function(){
				$('#insertBoardCommentFormButton').click(function(){
					if($('#insertBoardCommentForm').hasClass('none')){
						$('#insertBoardCommentForm').removeClass('none');
						
					}else{
						$('#insertBoardCommentForm').addClass('none');
					}
				});
				
				
			}); */	
		
		</script>
	</head>
	<body>
		
			
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8">
					<!-- resumeSangse -->
						<div>
							<a href="./updateBoardForm?boardNo=${board.boardNo}">
								[수정]
							</a>&nbsp;&nbsp;
							<a href="./deleteBoard?boardNo=${board.boardNo}">
								[삭제]
							</a>&nbsp;&nbsp;
							<a href="./resumeList">
								[목록]
							</a>
						</div>
						
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-sm-4">
										번호 : ${resumeView.resumeId}<br/>
										제목 : ${resumeView.resumeTitle}
									</div>
									<div class="col-sm-4 text-center">
										<h3 class="panel-title">${resumeView.resumeContent}</h3>
									</div>
								</div>
							</div>
							<div class="panel-body" style="height:100px;">
								첨부파일
								<c:forEach var="resumeFile" items="${list}">
									<a href="${pageContext.request.contextPath}/resumeView?resumeId=">${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}</a><br/>
								</c:forEach>
							</div>
						</div>
					<!-- resumeSangse end -->
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
	
	</body>
</html>