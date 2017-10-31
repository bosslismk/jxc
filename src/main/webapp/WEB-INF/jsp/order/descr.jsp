<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <section  class="content-header">
      <h1>
        订单管理
        <small>销售订单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">订单管理</a></li>
        <li><a href="#">销售订单信息管理</a></li>
        <li><a href="#">销售订单详细信息</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">销售订单详细信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
			<button type="button" id="deleteButton" class="btn btn-danger">取消订单</button>
			<button type="button" id="reviewSuccessButton" class="btn btn-success">审核通过</button>
			<button type="button" id="reviewFailButton" class="btn btn-danger">审核失败</button>
              <table id="data-table" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>单号</th>
                  <th>客户名称</th>
                  <th>销售员</th>
                  <th>订单金额</th>
                  <th>支付类型</th>
                  <th>订单状态</th>
                  <th>销售日期</th>
                  <th>操作员</th>
                  <th>审核员</th>
                  <th>审核日期</th>
                  <th>地址</th>
                  <th>备注</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                <td>${order.orderno }</td>
                <td>${order.customer.name }</td>
                <td>${order.saleperson }</td>
                <td>${order.amount }</td>
                <td>${order.paytype==1?'现金支付':order.paytype==2?'网银支付':order.paytype==3?'支付宝支付':order.paytype==4?'微信支付':'' }</td>
                <td>${order.status==-1?'审核失败':order.status==0?'已取消':order.status==1?'待审核':order.status==2?'审核成功待出库':order.status==3?'出库完成':'' }</td>
                <td><fmt:formatDate value="${order.saledate }" type="date"/></td>
                <td>${order.operater }</td>
                <td>${order.reviewer }</td>
                <td><fmt:formatDate value="${order.reviewdate }" type="date"/></td>
                <td>${order.address }</td>
                <td>${order.remarks }</td>
                </tr>
                
                </tbody>
                <thead>
                <tr>
                  <th >子项ID</th>
                  <th >商品名称</th>
                  <th >商品建议销售价</th>
                  <th >商品实际销售价</th>
                  <th>数量</th>
                  <th>总计</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items }" var="item">
                <tr>
                 <td >${item.orderitemid }</td>
                  <td>${item.product.name }</td>
                  <td>${item.product.outprice }</td>
                  <td>${item.price }</td>
                  <td>${item.num }</td>
                  <td>${item.num*item.price }</td>
                </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
          </div>
          </div>
          </section>
<script>
	$(function(){
		init();
		$('#reviewSuccessButton').click(function(){
			if(window.confirm("审核通过？")){
				$.ajax({
					url:'${pageContext.request.contextPath}/order/review.htm?ids=${order.orderno}&status=2',
					type:'get',
					dataType:'json',
					success:function(result){
						if(result.success){
							location.reload();
						}else{
							alert(result.message);
						}
						
					}
				});
			}
		});
		$('#reviewFailButton').click(function(){
			if(window.confirm("审核失败？")){
				$.ajax({
					url:'${pageContext.request.contextPath}/order/review.htm?ids=${order.orderno}&status=-1',
					type:'get',
					dataType:'json',
					success:function(result){
						if(result.success){
							location.reload();
						}else{
							alert(result.message);
						}
						
					}
				});
			}
		});
		
		$("#deleteButton").click(function(){
			if(window.confirm("确定取消？")){
					$.ajax({
						url:'${pageContext.request.contextPath}/order/cancel.htm?ids=${order.orderno}',
						type:'get',
						dataType:'json',
						success:function(result){
							if(result.success){
								location.reload();
							}else{
								alert(result.message);
							}
						}
					});
			}
		});
	}); 
	function initmenu(){
		$('.sidebar-menu .treeview:eq(3)').addClass('active');
		$('.sidebar-menu .treeview:eq(3) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(3) .treeview-menu li:eq(0)').addClass('active');
	}
	function init(){
		initmenu();
	}
	 
</script>