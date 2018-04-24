<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<title>notice List</title>
	</head>
	<body>
		<div class="container-fluid">
				<div class="row">
					<div class="col-sm-3"></div>
					<div class="col-sm-6">
						<!-- Begin Content -->
						<a href="${pageContext.request.contextPath}/">Home</a>
						<button><a href="${pageContext.request.contextPath}/insertNotice">추가</a></button>
						<div class="panel panel-danger">
							<div class="panel-heading">
								<h1 class="panel-title">Notice List</h1>
							</div>
								<div class="row">
									<div class="col-sm-3">
										<strong>${currentPage} / ${lastPage} Page</strong><br/>
										pagePerRow : <strong id="textPagePerRow">${pagePerRow}</strong>
									</div>
									<div class="col-sm-6">
										<h3>Item List</h3>
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
									<form action="<%=request.getContextPath()%>/searchItemList" method="get">
										<select id="searchOption" name="searchOption" onclick="changehtml();">											
											<option value="category_name"  <c:out value="${searchOption == 'category_name'?'selected':''}"/>>카테고리</option>
											<option value="item_name"  <c:out value="${searchOption == 'item_name'?'selected':''}"/>>상품명</option>											
											<option value="item_price"  <c:out value="${searchOption == 'item_price'?'selected':''}"/>>상품가격</option>		
										</select>
										<dr id="keyword"></dr>
										<input type="submit" value="검색버튼" >
									</form>
								</div>
								<div class="panel-body">
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
															<th><input type="checkbox" name="deleteCheckbox" value="${item.itemNo}"></th>
															<td scope = "row">${notice.itemId}</td>
															<td>${notice.noticeName}</td>
															<td>${notice.noticefile.noticeFileName}</td>
															<td>${notice.noticefile.noticeFileExt}</td>
															<td><a href="${pageContext.request.contextPath}/insertOrder?itemNo=${item.itemNo}">구매</a></td>
															<td><a href="${pageContext.request.contextPath}/updateItemForm?itemNo=${item.itemNo}&pagePerRow=${pagePerRow}">수정</a></td>							
														</tr>
													</tbody>
												</c:forEach>
											</form>
										</tbody>
									</table>
								<div class="panel-body">
								<nav>
									<ul class="pagination">
									
									<c:if test="${currentPage>1}">
										<li><a href="${pageContext.request.contextPath}/selectItemList?pagePerRow=${pagePerRow}">«</a></li>
										<li><a href="${pageContext.request.contextPath}/selectItemList?currentPage=${currentPage-1}&pagePerRow=${pagePerRow}"><</a></li>
									</c:if>
									
									<c:forEach var="iCount" begin="${startPage}" end="${endPage}">
										<c:choose>
											<c:when test="${iCount == currentPage}">
												<li class="active"><a href="${pageContext.request.contextPath}/selectItemList?currentPage=${iCount}&pagePerRow=${pagePerRow}">${iCount}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageContext.request.contextPath}/selectItemList?currentPage=${iCount}&pagePerRow=${pagePerRow}">${iCount}</a></li>
											</c:otherwise>
										</c:choose>								
									</c:forEach>				
									
									<c:if test="${currentPage < lastPage}">
										<li><a href="${pageContext.request.contextPath}/selectItemList?currentPage=${currentPage+1}&pagePerRow=${pagePerRow}">></a></li>
										<li><a href="${pageContext.request.contextPath}/selectItemList?currentPage=${lastPage}&pagePerRow=${pagePerRow}">»</a></li>
									</c:if>
									
									<div>
								  		<button id="button">아이템 삭제</button>
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