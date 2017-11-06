<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/plugins/select2/select2.full.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/select2.min.css">
 <section  class="content-header">
      <h1>
        库存管理
        <small>出库单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">库存管理</a></li>
        <li><a href="#">出库单信息管理</a></li>
        <li><a href="#">创建出库单</a></li>
      </ol>
    </section>

<section class="content">

	<div class="box box-primary">
		<div class="box-header">
			<h3 class="box-title">创建出库单信息</h3>
		</div>
		<!-- /.box-header -->
		<form id="editForm" role="form" method="post"
			action="${pageContext.request.contextPath}/outstore/save.htm">
			<input type="hidden" name="count" value="0" id="count"/> 
			<div class="box-body">
				<div class="row">
					<div class="col-xs-12">
						<label>出库类型</label>
						<div class="form-group">
							<select name="type" id="type" class="form-control" required="required">
								<option value="1">销售订单出库</option>
								<option value="2">采购订单退货出库</option>
							</select>  
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<label>订单单号</label>
						<div class="form-group">
							<input type="text" name="orderno" id="orderno" required="required"  
								class="form-control" />  
						</div>
					</div>
					<div class="col-xs-4">
						<label>查询</label>
						<div class="form-group">
								<button type="button"  id="searchOrderItems" class="btn btn-success">查询</button> 
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<label>发货人编号</label>
						<div class="form-group">
							<input type="text" name="deliverperson" id="deliverperson" required="required"  
								class="form-control" />  
						</div>
					</div>
				</div>
				<hr>
				<hr id="hr">
			</div>

			<!-- /.box-body -->

			<div class="box-footer">
				<button type="submit" id="submitBtn" class="btn btn-primary">提交</button>
			</div>
		</form>
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<div id="hiddenStore" style="display: none;">
	<c:forEach items="${stores }" var="store">
	<option value="${store.storeId }">${store.name }</option>
	</c:forEach>
	</div>
</section>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script>
	$(function() {
		initmenu();
		//生成商品
		$('#searchOrderItems').click(function(){
			var orderno=$('#orderno').val();
			if(orderno.length>0){
				$.ajax({
					url:'${pageContext.request.contextPath}/outstore/orderitems/select.htm?orderno='+orderno+'&type='+$('#type').val(),
					dataType:'json',
					type:'get',
					success:function(data){
						if(data.length>0){
							$('#count').val(data.length);
							$.each(data,function(idx,item){
								var tmp='<div class="row"><div class="col-xs-8">'+
								'<label for="name">商品信息</label>'+
								'<div class="form-group">'+
								'<input name="productid'+idx+'" type="hidden" value="'+item.id+'"/>'+
								'<input type="text" readonly  '+
								'class="form-control" value="'+item.text+'"/>  '+
								'</div>'+
								'</div>'+
								'<div class="col-xs-4">'+
								'<label for="name">操作</label>'+
								'<div class="form-group">'+
								'<button onclick="addstore(this)"  type="button" pcount="'+idx+'" scount="0" id="addStoreBtn" class="btn btn-success  ">添加出库信息</button>'+
								'</div>'+
								'</div></div>';
								$('#hr').before(tmp);
							});
						}else{
							alert('订单编号错误');
						}
					}
				});
			}
			
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
										location.href = '${pageContext.request.contextPath}/outstore/listPage.htm';
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

	function removeRow(obj){
		var prev1=$(obj).parent().parent().prev();
		var prev2=$(obj).parent().parent().prev().prev();
		$(obj).parent().parent().remove();
		prev1.remove();
		prev2.remove();
	}
	function addstore(obj){
		var pcount=$(obj).attr('pcount');
    	var scount=$(obj).attr('scount');
    	var item='<div class="col-xs-4">	';
    	item+='<label for="name">选择仓库</label>';
    	item+='<select name="store'+pcount+ '" class="form-control">';
    	item+=$('#hiddenStore').html();
    	item+='</select>';
    	item+='</div>';
    	item+='<div class="col-xs-4">	';
    	item+='<label for="name">出库数量</label>';
    	item+='<input type="number" name="num'+pcount+ '"  required="required" ';
    	item+='class="form-control" />  ';
    	item+='</div>';
    	item+='<div class="col-xs-4">	';
    	item+='<label for="name">操作</label>';
    	item+='<div class="form-group">';
    	item+='<button type="button" id=" " onclick="removeRow(this)" class=" btn btn-default">移除</button> ';
    	item+='</div>';
    	item+='</div>';
    	item+='</div>';
    	$(obj).attr('scount',parseInt(scount)+1);
    	console.info(item);
    	$(obj).parent().parent().after(item);;
		
	}
	function initmenu() {
		$('.sidebar-menu .treeview:eq(4)').addClass('active');
		$('.sidebar-menu .treeview:eq(4) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(4) .treeview-menu li:eq(0)').addClass(
				'active');
	}
</script>