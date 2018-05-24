<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>addUser</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script>
		$(document).ready(function(){
			$('#btnconfirm').click(function(){
				$.post({
				      /* type: "POST", */
				      url: "/pds2/idCheck",
				      data: { id: $('#id').val() },
				      success:function( JSON ) { // result : String, XML, JSON
				    	  if(JSON=='T'){
				    		alert(JSON);
				    		$('#id2').val($('#id').val());  
				    	  }
				      		
				      }
				});
			});
			
		    
		});
		
		</script>
		
	</head>
	<body>
		<h1>ADD USER PAGE</h1>
		<form action="${pageContext.request.contextPath}/addUser" method="post">
			<div>
				ID : <input type="text" name="id" id="id">
				<button type="button" id="btnconfirm">ID확인</button>
			</div>
			<div>
				ID2 : <input type="text" name="id2" id="id2">
				<button type="button" id="btnconfirm">ID</button>
			</div>
			<div>
				NAME : <input type="text" name="name">
			</div>
			<div><button type="submit">회원가입</button></div>
		</form>
	</body>
</html>