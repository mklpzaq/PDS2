<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>View Detail</title>
</head>
<body>
	<jsp:include page="../navbar.jsp"/>
	<div class="container-fluid">
  		<div class="row">
 			<div class="col-sm-3"></div>
 			<div class="col-sm-6">
 				<!-- Begin Content -->
 				<%-- <div class="panel panel-default">
 					<div class="panel-body text-center">
 						<div class="row">
 						<div class="col-sm-3">
							<strong>${currentPage} / ${lastPage} Page</strong><br/>
							pagePerRow : <strong id="textPagePerRow">${pagePerRow}</strong>
						</div>
 						<div class="col-sm-6">
							<h3>Detail Gallery</h3>
						</div>
 						<div class="col-sm-3">
							<select id="selectPagePerRow" name="selectPagePerRow">
								<option value="5"<c:if test="${pagePerRow == 5}">selected</c:if>>5</option>
								<option value="10"<c:if test="${pagePerRow == 10}">selected</c:if>>10</option>
								<option value="15"<c:if test="${pagePerRow == 15}">selected</c:if>>15</option>
								<option value="20"<c:if test="${pagePerRow == 20}">selected</c:if>>20</option>
								<option value="30"<c:if test="${pagePerRow == 30}">selected</c:if>>30</option>
								<option value="40"<c:if test="${pagePerRow == 40}">selected</c:if>>40</option>
								<option value="50"<c:if test="${pagePerRow == 50}">selected</c:if>>50</option>
							</select>개씩 보기
						</div>
 					</div> --%>
 					<!-- <hr/>  -->
 						<%-- <form action="${pageContext.request.contextPath}/searchGallery" method="post">
 							<select id="searchOption" name="searchOption" onclick="changehtml();">
 								<option value="gallery_id" <c:out value="${searchOption == 'gallery_id'?'selected':''}"/>>사진관 아이디</option>
 								<option value="gallery_title" <c:out value="${searchOption == 'gallery_title'?'selected':''}"/>>사진관 제목</option>						
 								<option value="gallery_content" <c:out value="${searchOption == 'gallery_content'?'selected':''}"/>>사진관 내용</option>
 							</select>
 							<dr id="keyword"></dr>
 							<input type="submit" value="검색버튼"> 						
 						</form> --%>
 					<div class="panel panel-default">
 						<div class="panel-heading">
 							<div class="row">
								<div class="col-sm-2">
									사진관아이디 :${viewGallery.galleryId}<br/>										
								</div>
								<div class="col-sm-10 text-center">
									사진관제목 : ${viewGallery.galleryTitle}										
								</div> 							
 							</div>
 						</div>
					 	<div class="panel-body" style="height:100%;">								
							<textarea class="panel-title" style="height:90%; width:90%;">${viewGallery.galleryContent}</textarea>
							<div>
								첨부파일 : <br/>
								<c:forEach var="galleryFile" items="${viewGallery.galleryFile}">
									<%-- <img src="${pageContext.request.contextPath}/downloadGallery?galleryFileName=${galleryFile.galleryFileName}&galleryFileExt=${galleryFile.galleryFileExt}" alt="140x140" class="img-circle" ${galleryFile.galleryFileName}.${galleryFile.galleryFileExt}/> --%>             
									<img data-src="holder.js/140x140" class="img-circle" alt="${galleryFile.galleryFileName}.${galleryFile.galleryFileExt}" src="${pageContext.request.contextPath}/downloadGallery?galleryFileName=${galleryFile.galleryFileName}&galleryFileExt=${galleryFile.galleryFileExt}" data-holder-rendered="true" style="width: 140px; height: 140px;">
									<br/>
								</c:forEach>
							</div>
						</div>
						<div>
							<button><a href="${pageContext.request.contextPath}/updateAndDeleteGallery?galleryId=${gallery.galleryId}">수정/삭제</a></button>
						</div>				
 					</div>
					
				<%-- 	<nav>
						 <ul class="pagination">
						    <li>
						      <a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=1&pagePerRow=${pagePerRow}" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>							   
						    <li>
						    	<c:choose>
						    		<c:when test="${currentPage > 1}">
							      		<a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${currentPage-1}&pagePerRow=${pagePerRow}" aria-label="Previous">
							        		<span aria-hidden="true">&lt;</span>
							      		</a>
						      		</c:when>
						      		<c:otherwise>
						      			<a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=1&pagePerRow=${pagePerRow}" aria-label="Previous">
							        		<span aria-hidden="true">&lt;</span>
							      		</a>
						      		</c:otherwise>
						      	</c:choose>
						    </li>
						    <c:choose>
						    	<c:when test="${lastPage > beginPageNumForCurrentPage + 4}">
									<c:forEach var="pageNum" begin="${beginPageNumForCurrentPage}" end="${beginPageNumForCurrentPage + 4}" step="1">
										<c:choose>
											<c:when test="${pageNum == currentPage}">
												<li class="active"><a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${pageNum}&pagePerRow=${pagePerRow}">${pageNum}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${pageNum}&pagePerRow=${pagePerRow}">${pageNum}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="pageNum" begin="${beginPageNumForCurrentPage}" end="${lastPage}" step="1">
										<c:choose>
											<c:when test="${pageNum == currentPage}">
												<li class="active"><a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${pageNum}&pagePerRow=${pagePerRow}">${pageNum}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${pageNum}&pagePerRow=${pagePerRow}">${pageNum}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
						    </c:choose>
						    <li>
								<c:choose>
									<c:when test="${currentPage < lastPage}">
										<a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${currentPage+1}&pagePerRow=${pagePerRow}" aria-label="Next">
											<span aria-hidden="true">&gt;</span>
										</a>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${lastPage}&pagePerRow=${pagePerRow}"aria-label="Next">
											<span aria-hidden="true">&gt;</span>
										</a>
									</c:otherwise>
								</c:choose>
							</li>
							<li>
								<a href="${pageContext.request.contextPath}/viewDetailGallery?currentPage=${lastPage}&pagePerRow=${pagePerRow}" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</ul>						
					</nav>	 --%>
 				<!-- Begin Content END -->
 				<!-- </div> 	 -->		
 			</div> 			   
  		</div>
  		<!-- container-fluid END -->
	</div>

</body>
</html>