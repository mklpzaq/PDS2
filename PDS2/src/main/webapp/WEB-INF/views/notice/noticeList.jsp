<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>item list</title>
		<script type="text/javascript">
	
			//체크박스 삭제기능
			$(document).ready(function(){			
				  $('#pagePerRow').change(function(){	     	 
				     	 $(location).attr('href', './selectItemList?pagePerRow='+$('#pagePerRow > option:selected').val());
				  });
				});
				
				$(document).ready(function(){
					$("#button").click(function(){
						$('input[name="deleteCheckbox"]:checked').each(function(){							
							var text = $(this).val();
							alert("noticeId"+text+"삭제");					
							$("#form1").submit();	
						});
					});	
				});
				
				$(document).ready(function(){
					$("#checkboxAll").click(function(){
						if($(this).prop("checked")){
							$("input[type=checkbox]").prop("checked",true);
						}else{
							$("input[type=checkbox]").prop("checked",false);
						}
					});
				});
				//검색기능
				function changehtml(){
					var property = $("#searchOption").val();
					var show = $("#keyword");
						$("#keyword").html('<input type="text" name="keyword">');
				}
					
			</script>
	</head>
	<body>
		<div class="container-fluid">
				<div class="row">
					<div class="col-sm-3"></div>
					<div class="col-sm-6">
						<!-- Begin Content -->
						<a href="${pageContext.request.contextPath}/">Home</a>
						<button><a href="${pageContext.request.contextPath}/insertNotice">공지 등록</a></button>
						<div class="panel panel-default">
							<div class="panel-body text-center">
								<div class="row">
									<div class="col-sm-3">
										<strong>${currentPage} / ${lastPage} Page</strong><br/>
										pagePerRow : <strong id="textPagePerRow">${pagePerRow}</strong>
									</div>
									<div class="col-sm-6">
										<h3>Notice List</h3>
									</div>
									<div class="col-sm-3">
										<select id="pagePerRow">
											<option	class="option" value=5 <c:if test="${pagePerRow == 5}">selected</c:if>>5개씩보기</option>
											<option	class="option" value=10 <c:if test="${pagePerRow == 10}">selected</c:if>>10개씩보기</option>
											<option class="option" value=15 <c:if test="${pagePerRow == 15}">selected</c:if>>15개씩보기</option>
											<option class="option" value=20 <c:if test="${pagePerRow == 20}">selected</c:if>>20개씩보기</option>			
										</select>
									</div>
								</div>
								<hr/>
								
								<div>
									<%-- <form action="<%=request.getContextPath()%>/searchNoticeList" method="get">
										<select id="searchOption" name="searchOption" onclick="changehtml();">											
											<option value="category_name"  <c:out value="${searchOption == 'category_name'?'selected':''}"/>>카테고리</option>
											<option value="item_name"  <c:out value="${searchOption == 'item_name'?'selected':''}"/>>상품명</option>											
											<option value="item_price"  <c:out value="${searchOption == 'item_price'?'selected':''}"/>>상품가격</option>		
										</select>
										<dr id="keyword"></dr>
										<input type="submit" value="검색버튼" >
									</form> --%>
								</div>
								
								<table class="table table-striped">
									<thead>
										<tr>
											<th><input type="checkbox" id="checkboxAll" value=""></th>
											<td>번호</td>
											<td>제목</td>
											<td>첨부파일</td>
											<td>확장자</td>
											<td>수정</td>
										</tr>
									</thead>
									<tbody>
										<form id="form1" method="get" action="${pageContext.request.contextPath}/deleteNotice">
											<c:forEach var="notice" items="${list}">
												<tbody>
													<tr>
														<th><input type="checkbox" name="deleteCheckbox" value="${notice.noticeId}"></th>
														<td scope = "row">${notice.noticeId}</td>
														<td>${notice.noticeTitle}</td>
														<td>${notice.noticeFile.noticeFileTitle}</td>
														<td>${notice.noticeFile.noticeFileExt}</td>
														<td><a href="${pageContext.request.contextPath}/updateNotice?noticeId=${notice.noticeId}&pagePerRow=${pagePerRow}">수정</a></td>							
													</tr>
												</tbody>
											</c:forEach>
										</form>
									</tbody>
								</table>
								<nav>
									<ul class="pagination">
									
									<c:if test="${currentPage>1}">
										<li><a href="${pageContext.request.contextPath}/selectNoticeList?pagePerRow=${pagePerRow}">«</a></li>
										<li><a href="${pageContext.request.contextPath}/selectNoticeList?currentPage=${currentPage-1}&pagePerRow=${pagePerRow}"><</a></li>
									</c:if>
									
									<c:forEach var="iCount" begin="${startPage}" end="${endPage}">
										<c:choose>
											<c:when test="${iCount == currentPage}">
												<li class="active"><a href="${pageContext.request.contextPath}/selectNoticeList?currentPage=${iCount}&pagePerRow=${pagePerRow}">${iCount}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageContext.request.contextPath}/selectNoticeList?currentPage=${iCount}&pagePerRow=${pagePerRow}">${iCount}</a></li>
											</c:otherwise>
										</c:choose>								
									</c:forEach>				
									
									<c:if test="${currentPage < lastPage}">
										<li><a href="${pageContext.request.contextPath}/selectNoticeList?currentPage=${currentPage+1}&pagePerRow=${pagePerRow}">></a></li>
										<li><a href="${pageContext.request.contextPath}/selectNoticeList?currentPage=${lastPage}&pagePerRow=${pagePerRow}">»</a></li>
									</c:if>
									
									<div>
								  		<button id="button">공지 삭제</button>
									</div>							
										
						<!-- End Content -->
					</div>
				</div>
			</div>
		</div>
	</div>
					<div class="col-sm-3"></div>
	
	</body>
</html>