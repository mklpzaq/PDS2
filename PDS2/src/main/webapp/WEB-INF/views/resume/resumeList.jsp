<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>BoardList</title>
		<script>
			$(document).ready(function() {
				$('#pagePerRow').change(function() {
					$(location).attr('href', './resumeList?pagePerRow=' + $('#pagePerRow > option:selected').val()+'&searchOption='+$('#searchOption').val()+'&keyword='+$('#keyword').val());
				});
			});
			
			$(document).ready(function(){
				$("#button").click(function(){
					$('input[name="deleteCheckbox"]:checked').each(function(){							
						var text = $(this).val();				
						$("#form1").submit();	
					});
				})	
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
			
			/* function changehtml(){
				var property = $("#searchOption").val();
				var show = $("#keyword");
				if(property=="resume_date"){
					$("#keyword").html('<input type="date" name="keyword">~<input type="date" name="keyword">');
				}else {
					$("#keyword").html('<input type="text" name="keyword">');
				}
			}	 */
			
			
		
		</script>
	</head>
	<body>	
		<jsp:include page="../navbar.jsp"/>
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<!-- Begin Content -->
					<a href="${pageContext.request.contextPath}/">Home</a>
					<button><a href = "${pageContext.request.contextPath}/insertResume">글쓰기</a></button>
					<div class="panel panel-default">
						<div class="panel-body text-center">
							<div class="row">
								<div class="col-sm-3">
									<strong>${currentPage} / ${lastPage} Page</strong><br/>
									pagePerRow : <strong id="textPagePerRow">${pagePerRow}</strong><br/>
									searchOption : <strong id="textSearchOption">${searchOption}</strong><br/>
									keyword : <strong id="textKeyword">
																			
											</strong>
								</div>
								<div class="col-sm-6">
									<h3>Resume List</h3>
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
							<table class="table table-striped">
								<thead>
									<tr>
										<th width="15%"><input type="checkbox" id="checkboxAll" value="">전체선택</th>
										<td width="10%">이력서 번호</td>
										<td width="25%">이력서 제목</td>
										<td width="35%">이력서 내용</td>
									</tr>
								</thead>
								<tbody>
									<form id="form1" method="get" action="${pageContext.request.contextPath}/deleteResume">
										<c:forEach var="resume" items="${list}">
											<tbody>
												<tr>
													<th><input type="checkbox" name="deleteCheckbox" value="${resume.resumeId}"></th>
													<th scope = "row">${resume.resumeId}</th>
													<td><a href="${pageContext.request.contextPath}/resumeView?resumeId=${resume.resumeId}">${resume.resumeTitle}</a></td>
													<td>${resume.resumeContent}</td>																							
												</tr>
											</tbody>
										</c:forEach>
									</form>
								</tbody>
							</table>
							<nav>
								<ul class="pagination">								
									<c:if test="${currentPage>1}">
										<li><a href="${pageContext.request.contextPath}/resumeList?pagePerRow=${pagePerRow}&searchOption=${searchOption}&keyword=${keyword}">처음으로</a></li>
										<li><a href="${pageContext.request.contextPath}/resumeList?currentPage=${currentPage-1}&pagePerRow=${pagePerRow}&searchOption=${searchOption}&keyword=${keyword}">이전</a></li>
									</c:if>
									
									<c:forEach var="iCount" begin="${startPage}" end="${endPage}">
										<c:choose>
											<c:when test="${iCount == currentPage}">
												<li class="active"><a href="${pageContext.request.contextPath}/resumeList?currentPage=${iCount}&pagePerRow=${pagePerRow}&searchOption=${searchOption}&keyword=${keyword}">${iCount}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageContext.request.contextPath}/resumeList?currentPage=${iCount}&pagePerRow=${pagePerRow}&searchOption=${searchOption}&keyword=${keyword}">${iCount}</a></li>
											</c:otherwise>
										</c:choose>								
									</c:forEach>
									
									<c:if test="${currentPage < lastPage}">
										<li><a href="${pageContext.request.contextPath}/resumeList?currentPage=${currentPage+1}&pagePerRow=${pagePerRow}&searchOption=${searchOption}&keyword=${keyword}">다음</a></li>
										<li><a href="${pageContext.request.contextPath}/resumeList?currentPage=${lastPage}&pagePerRow=${pagePerRow}&searchOption=${searchOption}&keyword=${keyword}">마지막으로</a></li>
									</c:if>
								</ul>
							</nav>
							<div>
								<%-- <form action="<%=request.getContextPath()%>/resumeList" method="get">
										<select id="searchOption" name="searchOption" onclick="changehtml();">											
										<option value="all" <c:if test="${searchOption == 'all'}">selected</c:if>>전체검색</option>
										<option value="resume_title" <c:if test="${searchOption == '"resume_title"'}">selected</c:if>>이력서 제목</option>
										<option value="resume_content"  <c:if test="${searchOption == 'resume_content'}">selected</c:if>>이력서 내용</option>												
									</select>
									<!-- <dr id="keyword"></dr> -->
									<input id="keyword" name="keyword" value="${keyword}">
									<input type="submit" value="검색버튼" >
								</form> --%>
							</div>
							<div>
						  		<button id="button">삭제</button>
							</div>	
						</div>
					</div>
					<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
	</body>
</html>