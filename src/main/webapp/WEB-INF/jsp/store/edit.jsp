<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <section  class="content-header">
 <h1>
        商品管理
        <small>仓库管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">仓库管理</a></li>
        <li><a href="#">仓库信息管理</a></li>
        <li><a href="#">${empty id?'添加':'添加' }仓库信息</a></li>
      </ol>
    </section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">${empty id?'添加':'添加' }仓库信息</h3>
				</div>
				<!-- /.box-header -->
				<form id="editForm" role="form" method="post"
					action="${pageContext.request.contextPath}/store/save.htm">
					<div class="box-body">
						<input type="hidden" value="${id }" name="id" />
						<div class="form-group">
							<label for="name">仓库名称*</label> 
							<input type="text" value="${store.name }" name="name" maxlength="20" class="form-control" id="name" placeholder="请输入仓库名称" required="required">
						</div>
						<div class="form-group">
							<label for="name">仓库联系人工号*</label> 
							<input type="text" value="${store.contactman }" name="contactman" maxlength="20" class="form-control" id="contactman" placeholder="请输入仓库联系人工号" required="required">
						</div>
						<div class="form-group">
							<label for="name">联系电话*</label> 
							<input type="text" value="${store.tel }" name="tel" maxlength="20" class="form-control" id="tel" placeholder="请输入联系电话" required="required">
						</div>
						<div class="form-group">
							<label for="name">联系邮箱</label> 
							<input type="email" value="${store.email }" name="email" maxlength="50" class="form-control" id="email" placeholder="请输入联系邮箱" >
						</div>
						<div class="form-group">
							<label for="name">地址*</label> 
							<input type="text" value="${store.address }" name="address" maxlength="200" class="form-control" id="address" placeholder="请输入地址" required="required">
						</div>
					</div>
			<!-- /.box-body -->
			<div class="box-footer">
				<button type="submit" id="submitBtn" class="btn btn-primary">提交</button>
			</div>
			</form>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
	</div>
	</div>
</section>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script>
	$(function() {
		initmenu();
		$("#submitBtn").click(function() {
			$("#editForm").validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						success : function(data) {
							// 提交成功
							data = eval("("+ data+ ")");
							if (data.success) {
								alert('成功');
								location.href = '${pageContext.request.contextPath}/store/listPage.htm';
							} else {
								alert(data.message);
							}
						},
						// 提交失败
						error : function(response) {
						}
					});													
					}
				});
			});
	});
	function initmenu() {
		$('.sidebar-menu .treeview:eq(1)').addClass('active');
		$('.sidebar-menu .treeview:eq(1) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(1) .treeview-menu li:eq(2)').addClass('active');
	}
</script>