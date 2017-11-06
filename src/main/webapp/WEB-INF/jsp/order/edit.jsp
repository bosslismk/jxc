<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/plugins/select2/select2.full.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/select2.min.css">
 <section  class="content-header">
 <h1>
        订单管理
        <small>销售订单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">订单管理</a></li>
        <li><a href="#">销售订单信息管理</a></li>
        <li><a href="#">创建订单信息</a></li>
      </ol>
    </section>

<section class="content">

	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">创建订单信息</h3>
		</div>
		<!-- /.box-header -->
		<form id="editForm" role="form" method="post"
			action="${pageContext.request.contextPath}/order/save.htm">
			<input type="hidden" name="count" value="0" id="count"/> 
			<div class="box-body">
				<div class="row">
					<div class="col-xs-4">
						<label>客户搜索</label>
						<div class="input-group">
							<input type="text" name="customersearch" id="customersearch"
								class="form-control" /> <span class="input-group-btn">
								<button class="btn btn-default" onclick="initCustomer()"
									type="button">搜索</button>
							</span>

						</div>
					</div>
					<div class="col-xs-8">
						<label>选择客户</label>
						<div class="form-group">
							<select id="customerid" name="customerid" required="required"
								class="form-control">
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<label>销售员编号</label>
						<div class="form-group">
							<input type="text" name="saleperson" id="saleperson" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<label>销售日期</label>
						<div class="form-group">
							<input type="text" name="saledate" id="saledate" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<label>支付方式</label>
						<div class="form-group">
							<select name="paytype" id="paytype" class="form-control" >
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
						<label>送货地址</label>
						<div class="form-group">
							<textarea style="min-height: 100px" maxlength="250" name="address" class="form-control"></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<label>订单备注</label>
						<div class="form-group">
							<textarea style="min-height: 100px" maxlength="250" name="remarks" class="form-control"></textarea>
						</div>
					</div>
				</div>
				<hr >
				<hr id="hr">
			</div>

			<!-- /.box-body -->

			<div class="box-footer">
				<button type="button" id="addItemBtn" class="btn btn-success">添加商品信息</button>
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
	    $('#saledate').datepicker({
	    	format: "yyyy年mm月dd日",
	      autoclose: true,
	    });
	    $('#addItemBtn').click(function(){
	    	var item='<div class="row"><div class="col-xs-3">';
			item+='<label for="name">商品搜索</label>';
			item+='<div class="input-group">';
			item+='<input type="text" name="productsearch" id="productsearch"';
			item+='class="form-control" /> <span class="input-group-btn">';
			item+='<button class="btn btn-default" onclick="initProduct(this)"';
			item+='type="button">搜索</button>';
			item+='</span>';
			item+='</div>';
			item+='</div>';
			item+='<div class="col-xs-3">';
			item+='<label for="name">选择商品</label>';
			item+='<div class="form-group">';
			item+='<select onchange="initProductOutPrice(this)" id="productid'+count+'" name="productid'+count+'" required="required"';
			item+='class="form-control">';
			item+='</select>';
			item+='</div>';
			item+='</div>';
			item+='<div class="col-xs-2">';
			item+='<label for="name">商品数量</label>';
			item+='<div class="form-group">';
			item+='<input class="form-control" type="number" required="required" id="num'+count+'" name="num'+count+'">';
			item+='</div>';
			item+='</div>';
			item+='<div class="col-xs-2">';
			item+='<label for="name">单价</label>';
			item+='<div class="form-group">';
			item+='<input class="form-control" type="text" required="required" id="price'+count+'" name="price'+count+'">';
			item+='</div>';
			item+='</div>';
			item+='<div class="col-xs-1">';
			item+='<label for="name">建议单价</label>';
			item+='<div class="form-group">';
			item+='<input class="form-control" type="text" id="outprice'+count+'" name="outprice'+count+'">';
			item+='</div>';
			item+='</div>';
			item+='<div class="col-xs-1">';
			item+='<label for="name">操作</label>';
			item+='<div class="form-group">';
			item+='<button class="btn btn-default" onclick="removeProduct(this)"';
			item+='type="button">移除</button>';
			item+='</div>';
			item+='</div>';
			item+='</div>';
	    	$('#hr').before(item);
	    	count++;
	    	$('#count').val(count);
	    });
		$("#submitBtn").click(function() {
			if(count>0){
				if(window.confirm("确定提交？")){
					$("#editForm").validate({
						submitHandler : function(form) {
							$(form).ajaxSubmit({
								success : function(data) {
									// 提交成功
									data = eval("("+ data+ ")");
									if (data.success) {
										alert('成功');
										location.href = '${pageContext.request.contextPath}/order/listPage.htm';
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
			}else{
				alert('请至少添加一件商品');
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
	function removeProduct(obj){
		count--;
    	$('#count').val(count);
		$(obj).parent().parent().parent().remove();
	}
	function initProduct(obj){
		var keyword=$(obj).parent().prev().val();
		if(keyword!=null && keyword!=''){
			$.ajax({
				url:'${pageContext.request.contextPath}/product/select.htm',
				data:{'keyword':keyword},
				dataType:'json',
				type:'get',
				success:function(data){
					$('#productid').empty();
					if(data.length>0){
						$.each(data,function(idx,item){
							var selectEl=$(obj).parent().parent().parent().next().find("select");
							selectEl.append("<option value='"+item.id+"'>"+item.text+"</option>");
							selectEl.change();
						});
					}else{
					}
				}
			});
		}
		
	}
	function initProductOutPrice(obj){
		var id=$(obj).val();
		$.ajax({
			url:'${pageContext.request.contextPath}/product/getByAjax.htm',
			data:{id:id},
			dataType:'json',
			type:'get',
			success:function(data){
				$(obj).parent().parent().next().next().next().find("input").val(data.outprice);
			}
		});
	}

	function initmenu() {
		$('.sidebar-menu .treeview:eq(3)').addClass('active');
		$('.sidebar-menu .treeview:eq(3) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(3) .treeview-menu li:eq(0)').addClass(
				'active');
	}
</script>