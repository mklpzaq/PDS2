<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	</head>		
	<body>	
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<!-- <span class="icon-bar"></span> -->
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="${pageContext.request.contextPath}/">PDS2</a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="${pageContext.request.contextPath}/articleList">articleList</a></li>
						<li><a href="${pageContext.request.contextPath}/getBoardList">boardList</a></li>
						<li><a href="${pageContext.request.contextPath}/galleryList">galleryList</a></li>
						<li><a href="${pageContext.request.contextPath}/selectNoticeList">noticeList</a></li>
						<li><a href="${pageContext.request.contextPath}/resumeList">resumeList</a></li>
					
						<%-- <c:choose>
							<c:when test="${loginMember != null}">	
								<!-- <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li> -->
								<li><a href="${pageContext.request.contextPath}/insertMemberForm">insertMember</a></li>			
								<li><a href="${pageContext.request.contextPath}/getMemberList">MemerList</a></li>						
								<li><a href="${pageContext.request.contextPath}/selectCategoryList">CategoryList</a></li>
								<li><a href="${pageContext.request.contextPath}/selectBoardList">BoardList</a></li>
								<li><a href="${pageContext.request.contextPath}/getAddressList">AddressList</a></li>					
								<li><a href="${pageContext.request.contextPath}/selectItemList">ItemList</a></li>
								<li><a href="${pageContext.request.contextPath}/getBoardCommentAllList">BoardCommentAllList</a></li>
								<li><a href="${pageContext.request.contextPath}/orderList">orderList</a></li>
								
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/insertMemberForm">insertMember</a></li>										
								<li><a href="${pageContext.request.contextPath}/selectBoardList">BoardList</a></li>
								<li><a href="${pageContext.request.contextPath}/getBoardCommentAllList">BoardCommentAllList</a></li>	
							</c:otherwise>
						</c:choose>	 --%>			
					</ul>										
					<%-- <ul class="nav navbar-nav navbar-right">	
						<li>								
							<div style="padding:15px;">
							<c:choose>
								<c:when test="${loginMember != null}">
									<strong>${loginMember.memberId} 님 환영합니다.</strong>
								</c:when>
								<c:otherwise>
									<strong> 손님 환영합니다.</strong>
								</c:otherwise>
							</c:choose>	
							</div>									
						</li>	
						<c:choose>
							<c:when test="${loginMember != null}">
								<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/getLogin">Login</a></li>
							</c:otherwise>
						</c:choose>					
					</ul> --%>
				</div><!-- /.navbar-collapse -->
			</div><!-- /.container-fluid -->
		</nav>	
	</body>
</html>