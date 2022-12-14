<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <title>DevWare-BETA</title>  
  <script src="https://kit.fontawesome.com/5aa66a35d0.js" crossorigin="anonymous"></script>
  <!-- Custom fonts for this template-->
  <link href="${pageContext.request.contextPath}/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <link href="${pageContext.request.contextPath}/resources/css/sb-admin-2.min.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
  
<%--   <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 --%>
  <script src="${pageContext.request.contextPath}/resources/vendor/jquery-easing/jquery.easing.min.js"></script>
  
  <script src="${pageContext.request.contextPath}/resources/js/sb-admin-2.min.js"></script> 
  <script>
function fn_formSubmit(){
	document.form1.submit();	
}

$.ajax({
	type:'POST',
	url: '${ pageContext.request.contextPath }/approval/notAuthApvCount',
	success: function(data){
		//console.log(data);
		$('#apvCount').text(data);
	}
})
</script>
<style>
ul .nav-item {padding-left:7px;}
</style>
</head>

<body>

  <!-- Page Wrapper -->
  <div>

    <!-- Sidebar -->
    <ul class="navbar-nav sidebar sidebar-dark" id="accordionSidebar"">
           <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/member/main">
		<i class="fa-brands fa-dev"></i>
        <div class="sidebar-brand-text mx-3">DevWare<sup>beta</sup></div>
      </a>
     
     
      <!-- Divider -->
      <hr class="sidebar-divider my-0">

 	  <li class="nav-item active">
       	  <a class="nav-link" href="${pageContext.request.contextPath}/boardList?bgno=2">
          <i class="fas fa-flag"></i>
          <span>????????????</span></a>
      </li>
      <hr class="sidebar-divider">
      
   	  <!-- ???????????? -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
          <i class="fas fa-pen-nib"></i>
          <span>????????????</span>
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">??????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/approval">????????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/apv_Vacat">??????/????????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/apv_payment">????????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/approval/tempApvList?page=1">???????????????</a>
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">?????????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/approval/myApvList?page=1">????????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/approval/notAuthApvList?page=1">??????????????? (<span id="apvCount"></span>)</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/approval/yesAuthApvList?page=1">??????????????????</a>
           
          </div>
        </div>
      </li>

      <!-- ????????? -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-envelope"></i>
          <span>????????????</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">????????????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/email/write">?????? ?????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/email/receive">?????? ????????? (<span id="menu_email_count">0</span>)</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/email/send">?????? ?????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/email/recycle">?????????</a>
                   
          </div>
        </div>
      </li>

       <!-- ???????????? -->
        <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/calendar">
           <i class="fas fa-calendar-alt"></i>
          <span>????????????</span></a>
      	</li>
      
      <!-- ???????????? -->
        <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/commuting/commuting">
          <i class="fas fa-building"></i>
          <span>????????????</span></a>
      </li>
      
      <!-- ????????? -->
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/boardList">
          <i class="fas fa-comment"></i>
          <span>???????????????</span></a>
      </li>
      

      <!-- ????????? -->
      <hr class="sidebar-divider">

    

    <!-- ??? ?????? -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsemem" aria-expanded="true" aria-controls="collapsemem">
          <!-- <i class="fas fa-fw fa-wrench"></i> -->
          <i class="fas fa-id-card"></i>
          <span>??? ????????????</span>
        </a>
        <div id="collapsemem" class="collapse" aria-labelledby="headingmem" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            
            <a class="collapse-item" href="${pageContext.request.contextPath}/member/myinfo">??? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/member/editPw?type=0">???????????? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/member/myboard?member_name=${sessionScope.member.member_name}">??? ?????????</a>            
             </div>
        </div>
      </li>
      
         <!-- ????????? -->
      <hr class="sidebar-divider">
      
      <!-- ????????? -->
      <c:if test="${sessionScope.member.member_status eq '9'}">
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
          <!-- <i class="fas fa-fw fa-wrench"></i> -->
          <i class="fas fa-cog"></i>
          <span>?????????</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
          
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">????????????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/unauthorized?page=1">?????? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/member?page=1">?????? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/commuting">???????????? ??????</a>
            
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">????????????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/allApvList?page=1">???????????? ?????????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/apvDelList?page=1">?????? ????????????</a>
            
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">????????????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/calendar/company">?????? ?????? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/calendar/member">?????? ?????? ??????</a>
            
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">????????????:</h6>       
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/department?page=1">?????? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/rank?page=1">?????? ??????</a>
            <div class="collapse-divider"></div>
            <h6 class="collapse-header">??????:</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/manager/statistics">?????? ??????</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/apv_payment/statistics">?????? ??????</a>
       
          </div>
        </div>
      </li>
      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">
      </c:if>

    

    </ul>
    <!-- ????????? ?????? -->


</div>
 

</body>

</html>