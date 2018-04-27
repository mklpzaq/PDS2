<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>Board List</title>
		<script>
			$(document).ready(function() {
				var searchSelect;
				var searchWord;
				
				/* 드롭다운 박스 클릭시 열고 닫고 이벤트 */
				$('#selectButton').click(function(){
					if($('#selectButton').parent().hasClass('open')){
						$('#selectButton').parent().removeClass('open');
					}else{
						$('#selectButton').parent().addClass('open');
					}
				});
				
				/* 드롭메뉴 클릭했을때 글자 바꾸기와 드롭메뉴 닫기 */
				$('#dropDownMenu > li').click(function(){
					/* searchButton을 누르면 searchWord변수의 값을 get방식으로 getBoardListController로 검색어와 함께 넘길 것이다.
					*  클릭했을때 선택한 select내용의 값을 #selectButtonText에 넣어준다.
					*/
					$('#selectButtonText, #monitorSearchSelect').text($(this).text());
					$('#selectButton').parent().removeClass('open');
				});
				
				$('#selectPagePerRow').change(function() {
					/* searchSelect = $('#monitorSearchSelect').text();
					searchWord = $('#monitorSearchWord').val(); */
					$(location).attr('href', './getBoardList?pagePerRow=' + $('#selectPagePerRow > option:selected').val() + '&searchSelect=' + $('#monitorSearchSelect').text() + '&searchWord=' + $('#monitorSearchWord').text());
				});
				
				/* 검색버튼을 클릭하면 get방식으로  searchSignal, searchSelect, searchWord값을 넘긴다.*/
				$('#searchButton').click(function(){
					searchSelect = $('#selectButtonText').text();
					searchWord = $('#searchWord').val();
					$(location).attr('href', './getBoardList?searchSignal=y&searchSelect=' + searchSelect + '&searchWord=' + searchWord);
				});
				
			});
		</script>
	</head>
	<body>
		<jsp:include page="../navbar.jsp"/>
	
		<!-- <a href="/pds2/resources/upload/aaa.txt">다운로드 테스트</a> -->
		<!-- <img src="/pds2/resources/upload/kkk.png"/> -->
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
				<!-- Begin Content -->
					
					<a href="${pageContext.request.contextPath}/insertBoard">insertBoard & boardFile</a>
		
					<div class="panel panel-default">
						<div class="panel-body text-center">
							<div class="row">
								<div class="col-md-4">
									<strong>${currentPage} / ${lastPage} Page</strong><br/>
									<strong>searchSelect : </strong><span id="monitorSearchSelect">${searchSelect}</span><br/>
									<strong>searchWord : </strong><span id="monitorSearchWord">${searchWord}</span><br/>						
								</div>
								<div class="col-md-4">
									<h3>Board List</h3>
								</div>
								<div class="col-md-4">
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
							</div>
							<hr/>
							
							<!-- Begin Search -->
							<div class="row">
								<div class="col-sm-2"></div>
								<div class="col-sm-8">
									
									<div class="input-group">
										<div class="input-group-btn">
											<button type="button" id="selectButton" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
												<span id="selectButtonText">${searchSelect}</span> <span class="caret"></span>
											</button>
											<ul id="dropDownMenu" class="dropdown-menu" role="menu">
												<li><a href="#">boardId</a></li>
												<li><a href="#">boardTitle</a></li>
												<li><a href="#">boardContent</a></li>
											</ul>
										</div>
										<input type="text" id="searchWord" class="form-control" placeholder="검색어 입력">
										<span class="input-group-btn">
											<button id="searchButton" class="btn btn-default" type="button">검색</button>
										</span>
									</div>
								
								</div>
								<div class="col-sm-2"></div>
							</div>
							<!-- End Search -->
							
							<table class="table table-striped">
								<thead>
									<tr>
										<td><strong>boardId</strong></td>
										<td><strong>boardTitle</strong></td>
										<td><strong>boardContent</strong></td>
										<td><strong>수정</strong></td>
										<td><strong>삭제</strong></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="board" items="${list}">
										<tr>
											<td><strong>${board.boardId}</strong></td>
											<%-- <td>${board.boardTitle}</td> --%>
											<td><a href="${pageContext.request.contextPath}/getDetailBoard?sendNo=${board.boardId}">${board.boardTitle}</a></td>
											<td>${board.boardContent}</td>
											<td><a href="${pageContext.request.contextPath}/updateBoard?sendNo=${board.boardId}">수정</a></td>
											<td><a href="${pageContext.request.contextPath}/deleteBoard?sendNo=${board.boardId}">삭제</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<nav>
								<ul class="pagination">
									<li>
										<a href="${pageContext.request.contextPath}/getBoardList?currentPage=1&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</li>
									<li>
										<c:choose>
											<c:when test="${currentPage > 1}">
												<a href="${pageContext.request.contextPath}/getBoardList?currentPage=${currentPage-1}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}"aria-label="Previous">
													<span aria-hidden="true">&lt;</span>
												</a>
											</c:when>
											<c:otherwise>
												<a href="${pageContext.request.contextPath}/getBoardList?currentPage=1&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}"aria-label="Previous">
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
														<li class="active"><a href="${pageContext.request.contextPath}/getBoardList?currentPage=${pageNum}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}">${pageNum}</a></li>
													</c:when>
													<c:otherwise>
														<li><a href="${pageContext.request.contextPath}/getBoardList?currentPage=${pageNum}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}">${pageNum}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="pageNum" begin="${beginPageNumForCurrentPage}" end="${lastPage}" step="1">
												<c:choose>
													<c:when test="${pageNum == currentPage}">
														<li class="active"><a href="${pageContext.request.contextPath}/getBoardList?currentPage=${pageNum}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}">${pageNum}</a></li>
													</c:when>
													<c:otherwise>
														<li><a href="${pageContext.request.contextPath}/getBoardList?currentPage=${pageNum}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}">${pageNum}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<li>
										<c:choose>
											<c:when test="${currentPage < lastPage}">
												<a href="${pageContext.request.contextPath}/getBoardList?currentPage=${currentPage+1}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}" aria-label="Next">
													<span aria-hidden="true">&gt;</span>
												</a>
											</c:when>
											<c:otherwise>
												<a href="${pageContext.request.contextPath}/getBoardList?currentPage=${lastPage}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}"aria-label="Next">
													<span aria-hidden="true">&gt;</span>
												</a>
											</c:otherwise>
										</c:choose>
									</li>
									<li>
										<a href="${pageContext.request.contextPath}/getBoardList?currentPage=${lastPage}&pagePerRow=${pagePerRow}&searchSignal=${searchSignal}&searchSelect=${searchSelect}&searchWord=${searchWord}" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</li>
								</ul>
							</nav>
						</div>
					</div>					
				
					
				<!-- End Content -->
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</body>
</html>