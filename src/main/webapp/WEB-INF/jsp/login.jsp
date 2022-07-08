<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
 <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">
<!-- Toastr -->
<!-- jQuery -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>

</head>
<body>
<section>
	
    <div>
        <img src="${pageContext.request.contextPath}/img/logo.jpeg" style="width:300px;" />
    </div>

    <div class="row mt-3">
        <div class="col-md-7">
            <img class="giphy" src="${pageContext.request.contextPath}/img/main.jpeg" />
        </div>
        <div class="col-md-5">
            <div class="inner-wrap" >
                <!-- <h2>Welcome to Our <br /> Community</h2> -->
                <h3 style="color: black;">Bolstering the Startup Community. <br />Network,Share Knowledge and Grow</h3>
                <div class="card" style="box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);">
                    <div class="card-body">
                        <h2 style="color: black;">Sign in</h2>
                        <!-- <img src="assets/img/bslogo_1.png" style="height: 50px;width:190px;" alt=""
                        class="img-fluid mb-4 d-block"> -->
                        <form  action="${pageContext.request.contextPath}/login/authenticate" method="post">
                            <div class="row">
                                <div class="col col-md-12 p-2">
                                    <input type="text" placeholder="Email" id="email" name="email" class="form-control">
                                </div>
                            </div>
                             <div class="row p-2">
                                <div class="col col-md-12 form-control">
                                    <input type="password"  style="border: none !important; outline:none !important; width:90%; " placeholder="Password" id="password" name="password" >
                                  <div style="display: inline-flex;">
                                    <i class="fas fa-eye-slash" id="passHide" onclick="myFunction()"  style=" cursor: pointer;"></i>
                                    <i class="far fa-eye" id="passShow" onclick="myFunction()"  style=" cursor: pointer; display: none;"></i>
                                  </div>
                                 
                                </div>
                            </div>
                            <button  type="submit"  class="btn" style="  border-radius: 20px;background-color:#293266;color:white;   width: 100%;">Log in</button>
                        <br>
                        
                         <label class="checkbox">
          <input type="checkbox" id="rememberChkBox"><b  style="font-family: sans-serif;font-weight: 300;"> remember me</b>
        </label>
                        </form>
                     
                       
                      
                    </div>
                </div>
                
            </div>

          
        </div>

    </div>


</section>
   
 <script>
    function myFunction() {
    	  var x = document.getElementById("password");
    	  if (x.type === "password") {
    	    x.type = "text";
    	    document.getElementById("passHide").style.display="none";
    	    document.getElementById("passShow").style.display="block";
    	  } else {
    	    x.type = "password";
    	    document.getElementById("passHide").style.display="block";
    	    document.getElementById("passShow").style.display="none";
    	  }
    	}
    </script>
    <script type="text/javascript">
    $(function () {
        if (localStorage.chkbox && localStorage.chkbox != '') {
            $('#rememberChkBox').attr('checked', 'checked');
            $('#email').val(localStorage.username);
            $('#password').val(localStorage.pass);
        } else {
            $('#rememberChkBox').removeAttr('checked');
            $('#email').val('');
            $('#password').val('');
        }

        $('#rememberChkBox').click(function () {

            if ($('#rememberChkBox').is(':checked')) {
                // save username and password
                localStorage.username = $('#email').val();
                localStorage.pass = $('#password').val();
                localStorage.chkbox = $('#rememberChkBox').val();
            } else {
                localStorage.username = '';
                localStorage.pass = '';
                localStorage.chkbox = '';
            }
        });
    });
    </script>
<script type="text/javascript">
	// Default Configuration
		$(document).ready(function() {
			toastr.options = {
				'closeButton': true,
				'debug': false,
				'newestOnTop': false,
				'progressBar': false,
				'positionClass': 'toast-top-right',
				'preventDuplicates': false,
			/* 	'showDuration': '1000',
				'hideDuration': '500',*/
				'timeOut': '100',
			/* 	'extendedTimeOut': '1000',  */
				'showEasing': 'swing',
				'hideEasing': 'linear',
				'showMethod': 'fadeIn',
				'hideMethod': 'fadeOut',
			}
		});

	// Toast Type
	var success='${success}';
		if(success!=null && success!="") {
			toastr.options.timeOut=1000;
			toastr.success(success);
		};

		var errormsg='${errormsg}';
	
		if(errormsg!=null && errormsg!="") {
			toastr.options.timeOut=1000;
			toastr.error(errormsg);
		};
	
	</script>
	
   
 <!-- Bootstrap core JavaScript-->
 
    <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>

<!-- Global site tag (gtag.js) - Google Analytics -->

<script async src="https://www.googletagmanager.com/gtag/js?id=G-1SY174C2EJ"></script>

<script>

  window.dataLayer = window.dataLayer || [];

  function gtag(){dataLayer.push(arguments);}

  gtag('js', new Date());

 

  gtag('config', 'G-1SY174C2EJ');

</script>



 <%--    <!-- Page level plugins -->
    <script src="${pageContext.request.contextPath}/vendor/chart.js/Chart.min.js"></script> --%>

 <%--    <!-- Page level custom scripts -->
    <script src="${pageContext.request.contextPath}/js/demo/chart-area-demo.js"></script>
    <script src="${pageContext.request.contextPath}/js/demo/chart-pie-demo.js"></script> --%>

</body>
</html>