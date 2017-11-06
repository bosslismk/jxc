<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Java进销存管理系统</title>
<!-- Tell the browser to be responsive to screen width -->
<jsp:include page="${pageContext.request.contextPath}/Staticreference/include-css.htm"></jsp:include>

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<jsp:include page="${pageContext.request.contextPath}/Sraticreference/header.htm"></jsp:include>

		<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<jsp:include page="${pageContext.request.contextPath}/Sreticreference/user-panel.htm"></jsp:include>
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<jsp:include page="${pageContext.request.contextPath}/Sreticreference/tree.htm"></jsp:include>
			</section>
			<!-- /.sidebar -->
		</aside>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<!-- /.content -->
			<jsp:include page="${requestScope.page}.htm"></jsp:include>

		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="${pageContext.request.contextPath}/Sreticeference/footer.htm"></jsp:include>

		<!-- Control Sidebar -->
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<!-- <div class="control-sidebar-bg"></div> -->
	</div>
	<!-- ./wrapper -->
<jsp:include page="${pageContext.request.contextPath}/Sreticeference/includejs.htm"></jsp:include>
</body>
</html>
