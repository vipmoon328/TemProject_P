<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid">
		<h3>마이페이지</h3>
		<form action="/editInfo" >
			<table class="table table-hover text-center">
				<tr>
					<th><label for="emp_name">이름</label></th>
					<td><input type="text" id="emp_name" name="emp_name" value="${emp.emp_name}"}></td>
				</tr>
				
				
			</table>
		</form>
	</div>
</body>
</html>