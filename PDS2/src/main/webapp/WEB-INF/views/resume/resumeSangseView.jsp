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
		
		<jsp:include page="../navbar.jsp"/>
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8">
					<!-- resumeSangse -->
						<div>
							<a href="./updateResume?resumeId=${resumeView.resumeId}">
								[수정]
							</a>&nbsp;&nbsp;
							<a href="./deleteResume?resumeId=${resumeView.resumeId}"><!-- &resumeFile=${resumeView.resumeFile}로 Resume안의 List<ResumeFile> resumeFile변수에 바로 셋팅하려 했으나 실패 -->
								[삭제]												
							</a>&nbsp;&nbsp;
							<a href="./resumeList">
								[목록]
							</a>
						</div>						
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-sm-2">
										번호 : ${resumeView.resumeId}<br/>										
									</div>
									<div class="col-sm-10 text-center">
										제목 : ${resumeView.resumeTitle}										
									</div>
								</div>
							</div>
							<div class="panel-body" style="height:100%;">								
								<textarea class="panel-title" style="height:90%; width:90%;">${resumeView.resumeContent}</textarea>
								<div>
									첨부파일 : <br/>
									<c:forEach var="resumeFile" items="${list}">								
										<a href="${pageContext.request.contextPath}/download?resumeFileName=${resumeFile.resumeFileName}&resumeFileExt=${resumeFile.resumeFileExt}">${resumeFile.resumeFileName}.${resumeFile.resumeFileExt}</a>
										<button><a href="${pageContext.request.contextPath}/deleteResumeFile?resumeFileId=${resumeFile.resumeFileId}&resumeFileName=${resumeFile.resumeFileName}&resumeFileExt=${resumeFile.resumeFileExt}">삭제</a></button><br/>
									</c:forEach>
								</div>
							</div>
						</div>
					<!-- resumeSangse end -->
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
	
	</body>
</html>