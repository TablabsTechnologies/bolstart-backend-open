<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Startup Verification</title>



<link
	href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css"
	rel="stylesheet">
<!-- Toastr -->
<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>


</head>
<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<ul
			class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
			id="accordionSidebar">

			<!-- Sidebar - Brand -->
			<a
				class="sidebar-brand d-flex align-items-center justify-content-center"
				href="#">
				<div class="sidebar-brand-icon rotate-n-15">
					<i class="fas fa-laugh-wink"></i>
				</div>
				<div class="sidebar-brand-text mx-3">${admiName}<sup>2</sup>
				</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">

			<!-- Nav Item - Dashboard -->
			<li class="nav-item "><a class="nav-link"
				href="${pageContext.request.contextPath}/dashboard"> <i
					class="fas fa-fw fa-tachometer-alt"></i> <span>Dashboard</span></a></li>

			<hr class="sidebar-divider my-0">

			<li class="nav-item "><a class="nav-link"
				href="${pageContext.request.contextPath}/startupVerification"> <i
					class="fas fa-fw fa-table"></i> <span>Verification</span></a></li>

			<hr class="sidebar-divider my-0">

			<li class="nav-item active"><a class="nav-link"
				href="${pageContext.request.contextPath}/usersData/0"> <i
					class="fas fa-fw fa-table"></i> <span>Users</span></a></li>

			<hr class="sidebar-divider my-0">

			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/companyData"> <i
					class="fas fa-fw fa-table"></i> <span>Company</span></a></li>

			<!-- Divider -->
			<hr class="sidebar-divider d-none d-md-block">

			<!-- Sidebar Toggler (Sidebar) -->
			<div class="text-center d-none d-md-inline">
				<button class="rounded-circle border-0" id="sidebarToggle"></button>
			</div>



		</ul>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>



					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a
							class="nav-link dropdown-toggle" href="#" id="searchDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text"
											class="form-control bg-light border-0 small"
											placeholder="Search for..." aria-label="Search"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>



						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">${admiName}</span>
								<img class="img-profile rounded-circle" src="${image}">
						</a> <!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<!--  <a class="dropdown-item" href="#">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Profile
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Settings
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Activity Log
                                </a> -->
								<!-- <div class="dropdown-divider"></div> -->
								<a class="dropdown-item"
									href="${pageContext.request.contextPath}/logout"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									Logout
								</a>
							</div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">Users</h1>
						<input type="text" id="search" placeholder="filter by Name"
							onkeyup="searchname()" style="" /> <input type="text"
							id="searchUserType" placeholder="filter by User Type"
							onkeyup="searchUserType()" /> <input type="text"
							id="searchIndustry" placeholder="filter by Industry"
							onkeyup="searchIndustry()" /> <a class="btn btn-outline-info"
							href="${pageContext.request.contextPath}/downloadUsersInExcel"
							role="button">User Download</a>
					</div>

					<!-- Content Row -->
					<div class="row" style="overflow-y: auto;">
						<div class="col-md-12">
							<div class="card" style="width: 100%;">
                              
								<div class="content table-responsive table-full-width">
									<table
										class="table table-striped table-bordered dt-responsive nowrap"
										id="actionTable"
										style='font-size: 87%; width: 87%; margin-left: 5%;'>
										<thead>
											<tr>
												<th>S No.</th>
												<th>USER NAME</th>
												<th>EMAIL</th>
												<th>CONTACT</th>
												<th>IMAGE</th>
												<th>User Type</th>
												<th>Industry Type</th>
												<th>Verified</th>
												<th>Date Of Join</th>

												<!-- <th colspan="2">ACTION</th> -->


											</tr>
										</thead>
										<tbody>
											<c:forEach items="${users}" var="users" varStatus="counter">
												<tr>
													<td>${counter.count}</td>
													<td>${users.firstName}${users.lastName}</td>
													<td>${users.emailId}</td>
													<td>${users.mobileNumber}</td>
													<td><img src="${users.profilePicture}"
														style="height: 40px; width: 40px"></td>
													<c:choose>
														<c:when test="${users.typeOfUser=='1'}">
															<td>Startup Founder</td>
														</c:when>
														<c:when test="${users.typeOfUser=='2'}">
															<td>Investor</td>
														</c:when>
														<c:when test="${users.typeOfUser=='3'}">
															<td>Mentor</td>
														</c:when>
														<c:when test="${users.typeOfUser=='4'}">
															<td>Freelancer</td>
														</c:when>
														<c:when test="${users.typeOfUser=='5'}">
															<td>Startup Enthusiast/Student</td>
														</c:when>
														<c:otherwise>
															<td>No</td>
														</c:otherwise>
													</c:choose>
													<td>${users.industryName}</td>
													<c:choose>
														<c:when test="${users.isEmailVerified=='true'}">
															<td>Yes</td>
														</c:when>
														<c:otherwise>
															<td>No</td>
														</c:otherwise>
													</c:choose>
													<td>${users.creationDate}</td>

													<!--    <td><a href="${pageContext.request.contextPath}/verify?id=${users.id}"><button type="button" class="btn btn-secondary">Verify</button></a></td>
						                                      <td><a href="${pageContext.request.contextPath}/reject?id=${users.id}"><button type="button" class="btn btn-danger">Reject</button></a></td>
						                                   -->
												</tr>

											</c:forEach>
										</tbody>
									</table>
									<nav aria-label="Page navigation example">
										<ul class="pagination">
											<li class="page-item"><a class="page-link" href="#">Previous</a></li>
											<c:forEach var="i" begin="1" end="${totalPage}">
											<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/usersData/${i-1}">${i}</a></li>
											</c:forEach>
											
											<li class="page-item" style><a class="page-link" href="${pageContext.request.contextPath}/usersData/${i+1}">Next</a></li>
										  
										</ul>
									</nav>
								</div>
								
							</div>
						</div>
					</div>
				</div>

			</div>

			<!-- Footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; www.bolstart.com 2021</span>
					</div>

				</div>
			</footer>
			<!-- End of Footer -->
			<!-- Scroll to Top Button-->
			<a class="scroll-to-top rounded" href="#page-top"> <i
				class="fas fa-angle-up"></i>
			</a>

		</div>
		<!-- /.container-fluid -->
	</div>
	<!-- End of Main Content -->



	<!-- Custom fonts for this template-->
	<script
		src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>
	<!-- Bootstrap core JavaScript-->

	<script type="text/javascript">
		$(document).ready(function() {
			console.log("called tastr");
			toastr.options = {
				'closeButton' : true,
				'debug' : false,
				'newestOnTop' : false,
				'progressBar' : false,
				'positionClass' : 'toast-top-right',
				'preventDuplicates' : false,
				/* 'showDuration': '1000',
				'hideDuration': '500',*/
				'timeOut' : '100',
				/* 'extendedTimeOut': '1000', */
				'showEasing' : 'swing',
				'hideEasing' : 'linear',
				'showMethod' : 'fadeIn',
				'hideMethod' : 'fadeOut',
			}
		});

		// Toast Type
		var success = '${success}';
		if (success != null && success != "") {
			toastr.options.timeOut = 1000;
			toastr.success(success);
		};

		var errorMsg = '${errorMsg}';
		if (errorMsg != null && errorMsg != "") {
			toastr.options.timeOut = 1000;
			toastr.error(errorMsg);
		};
	</script>


	<script>
		function searchname() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("search");
			filter = input.value.toUpperCase();
			table = document.getElementById("actionTable");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1];

				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>

	<script>
		function searchUserType() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("searchUserType");
			filter = input.value.toUpperCase();
			table = document.getElementById("actionTable");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[5];

				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>

	<script>
		function searchIndustry() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("searchIndustry");
			filter = input.value.toUpperCase();
			table = document.getElementById("actionTable");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[6];

				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>

<!--**********************************
        Scripts
    ***********************************-->
       <script src="${pageContext.request.contextPath}/plugins/common/common.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/custom.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/settings.js"></script>
    <script src="${pageContext.request.contextPath}/js/gleek.js"></script>
    <script src="${pageContext.request.contextPath}/js/styleSwitcher.js"></script>

 	<script src="${pageContext.request.contextPath}/plugins/tables/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/tables/js/datatable/dataTables.bootstrap4.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/tables/js/datatable-init/datatable-basic.min.js"></script>




</body>
</html>