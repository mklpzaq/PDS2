<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
	    $("#myBtn").click(function(){
	        $("#myModal").modal();
	    });
	});

$(function(){
	
	$('#myModal').on('shown.bs.modal', function () {
		  $('#myInput').focus()
		})
		
    // 검색버튼 눌렸을 때 함수 실행
    $("#searchBtn").click(function(e){
	        e.preventDefault();
	        // ajax
	        $.ajax({
	            // zip_codeList controller 진입 url
	            url : "https://biz.epost.go.kr/KpostPortal/openapi?regkey=521635254dc937fc01527815009308&target=postNew&query="+  +"&countPerPage=20&currentPage=1",
	            // zip_codeForm을 serialize 해줍니다.
	            data : $("#zip_codeForm").serialize(),
	            type : "POST",
	            // dataType 은 json형태로 보냅니다.
	            dataType : "json",
	            success : function(result){
	                $("#zip_codeList").empty();
	                var html = "";
	                if(result.errorCode != null && result.errorCode != ""){
	                    html += "<tr>";
	                    html += "    <td colspan='2'>";
	                    html +=            result.errorMessage;
	                    html += "    </td>";
	                    html += "</tr>";
	                }
	                else{
	                    // 검색결과를 list에 담는다.
	                    var list = result.list;
	                    
	                    for(var i = 0; i < list.length; i++){
	                        html += "<tr>";
	                        html += "    <td>";
	                        // 우편번호
	                        var zipcode = list[i].zipcode;
	                        // 주소
	                        var address = list[i].address;
	 
	                        html +=         list[i].zipcode;
	                        html += "    </td>";
	                        html += "    <td>";
	                        html +=     '<a href="#" onclick="put(\'' + list[i].address + '\',\'' + zipcode + '\')">' + address + '</a>';
	                        html += "    </td>";
	                        html += "</tr>";
	                    }
	                }
	                // 완성된 html(우편번호 list)를 zip_codeList밑에 append
	                $("#zip_codeList").append(html);
	            }
	        });
	    });
	});
	 
	// 원하는 우편번호 선택시 함수 실행
	function put(address,zipcode){
	    var address = address;
	    var zipcode = zipcode;
	    // 모달창 닫기
	    $("#zip_codeModal").modal("hide");
	    $("#zip_code").val(zipcode);
	    $("#address1").val(address);
	}
 
</script>



<title>Insert title here</title>
</head>
<body>
		<!-- <div class = "row">
	        <div class = "col-md-4">
	            <div class = "input-group">
	                <span class = "input-group-addon">우편번호</span>
	                <input type = "text" class = "form-control" id = "zip_code" name = "zip_code">
	                <span class = "input-group-addon"><a href = "#" id = "zip_codeBtn" data-toggle="modal" data-target="#zip_codeModal">검색하기</a></span>                
	            </div>
	        </div>
	    </div>
	    <div class = "row">
	        <div class = "col-md-4">
	            <div class = "input-group">
	                <span class = "input-group-addon">주소</span>
	                <input type = "text" class = "form-control" id = "address1">                
	            </div>
	        </div>
	    </div>        
	    <div class = "row">
	        <div class = "col-md-4">
	            <div class = "input-group">
	                <span class = "input-group-addon">상세주소</span>
	                <input type = "text" class = "form-control" id = "address2">        
	            </div>
	        </div>
	    </div> -->    
	    
	    <button type="button" data-toggle="modal" data-target="#myModal">Open Modal</button>
 
		<!-- Links -->
		<a data-toggle="modal" href="#myModal">Open Modal</a>
		 
		<!-- Other elements -->
		<p data-toggle="modal" data-target="#myModal">Open Modal</p>
		
		<!-- //만약 배경을 클릭해도 모달이 닫히지 않게 하려면 data-backdrop="static" 같은 옵션을 이 때 써도 된다. -->
		
		<button type="button" class="btn btn-info btn-lg" id="myBtn">Open Modal</button>

		<div class="modal fade" id="myModal" role="dialog">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header text-center">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">×</span>
		                </button>
		                <h3 class="modal-title" id="myModalLabel">우편번호</h3>
		            </div>    
		            <div class="modal-body text-center">
		                 <form id = "zip_codeForm">
		                         <div class = "input-group">
		                            <span class = "input-group-addon">동 입력</span>
		                            <input type="text" class = "form-control" name="query" id="query">
		                            <span class = "input-group-btn">                                                
		                                <input type="submit" class = "btn btn-warning" value="검색" id="searchBtn" onkeydown="javascript:if(event.keyCode==13)">                                            
		                            </span>
		                        </div>
		                </form>
		                <p>
		                </p>
		                <div>
		                <div style="width:100%; height:200px; overflow:auto">
		                       <table class = "table text-center">
		                        <thead>
		                            <tr>
		                                <th style="width:150px;">우편번호</th>
		                                <th style="width:600px;">주소</th>
		                                </tr>
		                        </thead>
		                        <tbody id="zip_codeList"></tbody>
		                    </table>
		                </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

</body>
</html>