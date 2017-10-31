<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <section  class="content-header">
  <h1>
        客户管理
        <small>客户信息管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">客户管理</a></li>
        <li><a href="#">客户信息管理</a></li>
        <li><a href="#">${empty id?'添加':'添加' }客户信息</a></li>
      </ol>
    </section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">${empty id?'添加':'添加' }客户信息</h3>
				</div>
				<!-- /.box-header -->
				<form id="editForm" role="form" method="post"
					action="${pageContext.request.contextPath}/customer/save.htm">
					<div class="box-body">
						<input type="hidden" value="${id }" name="id" />
						<div class="form-group">
							<label for="name">客户名称*</label> 
							<input type="text" value="${customer.name }" name="name" maxlength="50" class="form-control" id="name" required="required">
						</div>
						<div class="form-group">
							<label for="name">客户联系人*</label> 
							<input type="text" value="${customer.contactman }" name="contactman" maxlength="20" class="form-control" id="contactman"  required="required">
						</div>
						<div class="form-group">
							<label for="name">联系人职务*</label> 
							<input type="text" value="${customer.position }" name="position" maxlength="20" class="form-control" id="position"  required="required">
						</div>
						<div class="form-group">
							<label for="name">客户联系电话*</label> 
							<input type="text" value="${customer.tel }" name="tel" maxlength="20" class="form-control" id="tel"  required="required">
						</div>
						<div class="form-group">
							<label for="name">客户联系邮箱</label> 
							<input type="text" value="${customer.email }" name="email" maxlength="20" class="form-control" id="email"  >
						</div>
						<div class="form-group">
							<label for="name">开户银行</label> 
							<input type="text" value="${customer.depositbank }" name="depositbank" maxlength="50" class="form-control" id="depositbank"  >
						</div>
						<div class="form-group">
							<label for="name">银行账号</label> 
							<input type="text" value="${customer.bankaccount }" name="bankaccount" maxlength="50" class="form-control" id="bankaccount"  >
						</div>
						<div class="form-group">
							<label for="name">税号*</label> 
							<input type="text" value="${customer.taxnum }" name="taxnum" maxlength="100" class="form-control" id="taxnum"  required="required">
						</div>
						<div class="form-group">
							<label for="name">官网</label> 
							<input type="text" value="${customer.website }" name="website" maxlength="100" class="form-control" id="website"   >
						</div>
						<div class="form-group">
							<label for="category">客户状态*</label> 
							<select id="status" class="form-control" name="status">
								<option value="1" ${customer.status==1?'selected':'' }>正常使用</option>
								<option value="2" ${customer.status==2?'selected':'' }>暂停使用</option>
							</select>
						</div>
						<div class="form-group">
							<label for="name">地址*</label> 
							<textarea style="min-height: 200px;" name="address" id="address" class="form-control" required="required" maxlength="200">${customer.address }</textarea>
						</div>
						<div class="form-group">
							<label for="name">备注</label> 
							<textarea style="min-height: 200px;" name="remarks" id="remarks" class="form-control" maxlength="200">${customer.remarks }</textarea>
						</div>
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
								location.href = '${pageContext.request.contextPath}/customer/listPage.htm';
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
		$('.sidebar-menu .treeview:eq(2)').addClass('active');
		$('.sidebar-menu .treeview:eq(2) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(2) .treeview-menu li:eq(0)').addClass('active');
	}
</script>