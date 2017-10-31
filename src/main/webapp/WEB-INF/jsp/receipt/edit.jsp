<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/plugins/select2/select2.full.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/select2.min.css">
 <section  class="content-header">
 <h1>
       <h1>
        资金管理
        <small>收款单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">收款单管理</a></li>
        <li><a href="#">收款单信息管理</a></li>
        <li><a href="#">收款单编辑</a></li>
      </ol>
    </section>

<section class="content">

	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">创建收款单信息</h3>
		</div>
		<!-- /.box-header -->
		<form id="editForm" role="form" method="post"
			action="${pageContext.request.contextPath}/receipt/save.htm">
			<input type="hidden" name="count" value="0" id="count"/> 
			<div class="box-body">
				<div class="row">
					<div class="col-xs-4">
						<label for="name">客户搜索</label>
						<div class="input-group">
							<input type="text" name="customersearch" id="customersearch"
								class="form-control" /> <span class="input-group-btn">
								<button class="btn btn-default" onclick="initCustomer()"
									type="button">搜索</button>
							</span>

						</div>
					</div>
					<div class="col-xs-8">
						<label for="name">选择客户</label>
						<div class="form-group">
							<select id="customerid" name="customerid" required="required"
								class="form-control">
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<label for="name">收款类型</label>
						<div class="form-group">
						<select name="receipttype" id="receipttype" class="form-control">
							<option value="1">销售订单收款</option>
							<option value="2">采购订单退货</option>
						</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<label for="name">最大收款期限日</label>
						<div class="form-group">
							<input type="text" name="receiptmaxdate" id="receiptmaxdate" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<label for="name">总金额</label>
						<div class="form-group">
							<input type="text" name="amount" id="amount" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<label for="name">备注</label>
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
		initCustomer();
		
		//Date picker
	    $('#receiptmaxdate').datepicker({
	    	format: "yyyy年mm月dd日",
	      autoclose: true,
	    });
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

	function initCustomer(){
		var keyword=$('#customersearch').val();
		$.ajax({
			url:'${pageContext.request.contextPath}/customer/select.htm',
			data:{'keyword':keyword},
			dataType:'json',
			type:'get',
			success:function(data){
				$('#customerid').empty();
				if(data.length>0){
					$.each(data,function(idx,item){
						$('#customerid').append("<option value='"+item.id+"'>"+item.text+"</option>");
					});
				}else{
				}
				
			}
		});
	}

	function initmenu() {
		$('.sidebar-menu .treeview:eq(5)').addClass('active');
		$('.sidebar-menu .treeview:eq(5) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(5) .treeview-menu li:eq(0)').addClass(
				'active');
	}
</script>