<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <section  class="content-header">
  <h1>
        商品管理
        <small>商品信息管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">商品管理</a></li>
        <li><a href="#">商品信息管理</a></li>
        <li><a href="#">${empty id?'添加':'添加' }商品信息</a></li>
      </ol>
    </section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">${empty id?'添加':'添加' }商品信息</h3>
				</div>
				<!-- /.box-header -->
				<form id="editForm" role="form" method="post"
					action="${pageContext.request.contextPath}/product/save.htm">
					<div class="box-body">
						<input type="hidden" value="${id }" name="id" />
						<div class="form-group">
							<label for="name">商品名称*</label> 
							<input type="text" value="${product.name }" name="name" maxlength="50" class="form-control" id="name" placeholder="请输入商品名称" required="required">
						</div>
						<div class="form-group">
							<label for="category">商品分类*</label> 
							<select id="category" class="form-control" name="categoryId">
								<c:forEach items="${categorys }" var="category">
									<option value="${category.categoryId }" ${product.category.categoryId==category.categoryId?'selected':'' }>${category.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="name">商品型号*</label> 
							<input type="text" value="${product.model }" name="model" maxlength="50" class="form-control" id="model" placeholder="请输入商品型号" required="required">
						</div>
						<div class="form-group">
							<label for="name">商品单位*</label> 
							<input type="text" value="${product.unit }" name="unit" maxlength="20" class="form-control" id="unit" placeholder="请输入商品单位" required="required">
						</div>
						<div class="form-group">
							<label for="name">建议进购价*</label> 
							<input type="text" value="${product.inprice }" name="inPrice" maxlength="20" class="form-control" id="inPrice" placeholder="请输入建议进购价" required="required">
						</div>
						<div class="form-group">
							<label for="name">建议出售价*</label> 
							<input type="text" value="${product.outprice }" name="outPrice" maxlength="20" class="form-control" id="outPrice" placeholder="请输入建议出售价" required="required">
						</div>
						<div class="form-group">
							<label for="name">总库存*</label> 
							<input type="number" value="${product.stocks }" name="stocks" maxlength="20" class="form-control" id="stocks" placeholder="请输入总库存" required="required">
						</div>
						<div class="form-group">
							<label for="name">最小警报库存*</label> 
							<input type="number" value="${product.minstocks }" name="minstocks" maxlength="20" class="form-control" id="minstocks" placeholder="请输入最小警报库存" required="required">
						</div>
						<div class="form-group">
							<label for="name">最大警报库存*</label> 
							<input type="number" value="${product.maxstocks }" name="maxstocks" maxlength="20" class="form-control" id="maxstocks" placeholder="请输入最大警报库存" required="required">
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
								location.href = '${pageContext.request.contextPath}/product/listPage.htm';
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
		$('.sidebar-menu .treeview:eq(1) .treeview-menu li:eq(1)').addClass('active');
	}
</script>