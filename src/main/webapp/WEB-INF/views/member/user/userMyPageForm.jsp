<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<%
	String context = request.getContextPath();
%>
<html>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
		
		var id_check = false;
		
		//자동으로 도메인값을 넣어주는 함수 
		function changeDomain()
		{
			if($('#domain_list').val() == 'type')
			{
				$('#user_email2').val("");
				$('#user_email2').attr("readonly", false);
			}
			else
			{
				$('#user_email2').val($('#domain_list').val());
				$('#user_email2').attr("readonly", true);
			}
		}
		
		//회원 가입 유효성 체크 
		function signUpCheck()
		{	
			changeId(); //회원 가입전 아이디 변동 유효성을 체크하기 위한 함수 
			var check = true;
			var address = $('#postcode').val() + " " + $('#address').val() + " " + $('#detailAddress').val();
			var email = $('#user_email1').val() + "@" +  $('#user_email2').val()
			
			$('#emp_address').attr('value', address);
			$('#emp_email').attr('value', email);
			
			if($('#emp_passwd').val() != $('#chk_emp_passwd').val())
			{
				alert('패스워드가 일치하지 않습니다. 다시 입력해주세요.');
				check = false;
			}
			
			if($('#emp_passwd').val().length < 8)
			{
				alert('패스워드는 보안상의 이유로 8글자 이상 입력해주세요');
				check = false;
			}
			
			if($('#dept_num').val() == "")
			{
				check = false;
			}
			
			if($('#auth_num').val() == "")
			{
				check = false;
			}
			
			if($('#status_num').val() == "")
			{
				check = false;
			}
			
			if($('#position_num').val() == "")
			{
				check = false;
			}
			
			if(check == false)
			{
				alert("체크 항목들을 확인해 주세요");
				return false;
			}
		}
		
		//주소 가져오기
		function DaumPostcode() {
			new daum.Postcode({
				oncomplete: function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

					// 각 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					var addr = ''; // 주소 변수
					var extraAddr = ''; // 참고항목 변수

					//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
					if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
						addr = data.roadAddress;
					} else { // 사용자가 지번 주소를 선택했을 경우(J)
						addr = data.jibunAddress;
					}

					// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
					if(data.userSelectedType === 'R'){
						// 법정동명이 있을 경우 추가한다. (법정리는 제외)
						// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
						if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
							extraAddr += data.bname;
						}
						// 건물명이 있고, 공동주택일 경우 추가한다.
						if(data.buildingName !== '' && data.apartment === 'Y'){
							extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
						}
						// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
						if(extraAddr !== ''){
							extraAddr = ' (' + extraAddr + ')';
						}
						// 조합된 참고항목을 해당 필드에 넣는다.
						document.getElementById("extraAddress").value = extraAddr;

					} else {
						document.getElementById("extraAddress").value = '';
					}

					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('postcode').value = data.zonecode;
					document.getElementById("address").value = addr;

					// 커서를 상세주소 필드로 이동한다.
					document.getElementById("detailAddress").focus();

					// 우편변호와 도로명주소에 'focus' class 추가
					document.getElementById("postcode").parentNode.classList.add('focus');
					document.getElementById("address").parentNode.classList.add('focus');
				}    
			}).open();
		}

		$(function(){
			//	페이지 로딩 완료 후 focus 이동 - 처음에 label zoom 효과 안되는 오류 수정(임시방편)
			$(document).ready(function(){
				$("input[type='text']:first, input[type='password']:first").focus();
			});

			//	Label Zoom 효과 - input/select 선택했을 때
			$("input, select").focus(function() {
				// 'textbox' class가 적용된 태그 안에서 input[type="text"], input[type="password"] 검색
				var $input = $('.textbox').find('input[type="text"], input[type="password"]');
				$input.on({
					'focus': function () { // input 태그가 focus를 가지면 'focus' class 추가
						$(this).parent().addClass('focus');
					},
					'blur': function () { // input 태그가 focus를 잃으면
						if ($(this).val() == '') { // input 태그에  값이 없으면 'focus' class 삭제
							$(this).parent().removeClass('focus');                
						}           
					}
				});
				// 'textbox' class가 적용된 태그 안에서 select 검색
				var $select = $('.textbox').find('select');
				$select.on({
					'change': function () { // select 태그 값이 change가 발생하면 'focus' class 추가
						$(this).parent().addClass('focus');
					}
				});
			});

			//	회원가입 중에 오류가 날 때 zoom 관련 css 초기화 되어 다시 설정
			$('input[type=text], input[type=password], select').each(function () {
				if ($(this).val() !== '') { // input 태그가 value값을 가지면 'focus' class 추가
					// label focus
					$(this).closest('.textbox').addClass('focus');
				} else { // input 태그에  value값이 없으면 'focus' class 삭제
					$(this).closest('.textbox').removeClass('focus');
				}
			});
			
			var email = {emp.email};
			alert(email);
			
		});
		
</script>
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
					<th><label for="emp_name">사번</label></th>
					<td><input type="text" readonly="readonly" id="emp_nun" name="emp_nun" value="${emp.emp_num}"}></td>
				</tr>
				<tr>
					<th><label for="emp_name">이름</label></th>
					<td><input type="text" id="emp_name" name="emp_name" value="${emp.emp_name}"}></td>
				</tr>
				<tr>
					<th><label for="emp_name">아이디</label></th>
					<td><input type="text" readonly="readonly" id="emp_id" name="emp_id" value="${emp.emp_id}"}></td>
				</tr>
				<tr>
					<th><label for="emp_name">비밀번호</label></th>
					<td><input type="password" id="emp_passwd" name="emp_passwd" value="${emp.emp_passwd}"}></td>
				</tr>
				<tr>
					<th><label for="chk_emp_passwd">비밀번호 재확인</label></th>
					<td><input type="password" id="chk_emp_passwd" name="chk_emp_passwd" value="${emp.emp_passwd}"}></td>
				</tr>
					
				<tr>
					<th><label for="user_email">이메일</label></th>
					<td><input type="text" class="form-control" name="user_email1" id="user_email1" required="required"> <br> 
					<input type="text" class="form-control" class="email" name="user_email2" id="user_email2" required="required">
					<select name="email" class="form-control" id="domain_list" onclick="changeDomain()">
						<option value="naver.com">naver.com</option>
						<option value="daum.net">daum.net</option>
						<option value="gmail.com">gmail.com</option>
						<option value="type" selected="selected">직접 입력</option>
					</select></td>
				</tr>
				
				
			</table>
		</form>
	</div>
</body>
</html>