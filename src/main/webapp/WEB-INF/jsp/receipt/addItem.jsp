<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/plugins/select2/select2.full.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/select2.min.css">
 <section  class="content-header"> </section>
 <h1>
       <h1>
        资金管理
        <small>收款单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">收款单管理</a></li>
        <li><a href="#">收款单信息管理</a></li>
        <li><a href="#">收款单款项信息编辑</a></li>
      </ol>


<section class="content">

	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">创建收款单款项信息</h3>
		</div>
		<!-- /.box-header -->
		<form id="editForm" role="form" method="post"
			action="${pageContext.request.contextPath}/receipt/saveItem.htm">
			<input type="hidden" name="id" value="${id }" id="id"/> 
			<div class="box-body">
				<div class="row">
					<div class="col-xs-12"> 
						<label>支付方式</label>
						<div class="form-group">
							<select name="type" id="type" class="form-control" >
								<option value="1"  >现金支付</option>
								<option value="2"  >网银支付</option>
								<option value="3"  >支付宝支付</option>
								<option value="4"  >微信支付</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<label>本次收款</label>
						<div class="form-group">
							<input type="text" name="amount" id="amount" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<label>收款人</label>
						<div class="form-group">
							<input type="text" name="receiptperson" id="receiptperson" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<label>备注</label>
						<div class="form-group">
							<textarea style="min-height: 100px" maxlength="250" name="remarks" class="form-control"></textarea>
						</div>
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
</section>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script>
var count=0;
	$(function() {
		initmenu();
		$("#submitBtn").click(function() {
				if(window.confirm("确定提交？")){
					$("#editForm").validate({
						submitHandler : function(form) {
							$(form).ajaxSubmit({
								success : function(data) {
									// 提交成功
									data = eval("("+ data+ ")");
									if (data.success) {
										alert('成功');
										location.href = '${pageContext.request.contextPath}/receipt/listPage.htm';
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
				}else{
					return false;
				}
			});
	});

	function initmenu() {
		$('.sidebar-menu .treeview:eq(5)').addClass('active');
		$('.sidebar-menu .treeview:eq(5) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(5) .treeview-menu li:eq(0)').addClass(
				'active');
	}
</script>