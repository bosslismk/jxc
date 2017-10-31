<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <section  class="content-header">
      <h1>
        库存管理
        <small>出库单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">库存管理</a></li>
        <li><a href="#">出库单信息管理</a></li>
        <li><a href="#">出库单详情</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">出库单详细信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
			<button type="button" id="deleteButton" class="btn btn-danger">取消出库单</button>
			<button type="button" id="reviewSuccessButton" class="btn btn-success">审核通过</button>
			<button type="button" id="reviewFailButton" class="btn btn-danger">审核失败</button>
			<button type="button" id="outSuccessButton" class="btn btn-info">完成出库</button> 
              <table id="data-table" class="table table-boutstoreed table-hover">
                <thead>
                <tr>
                 <th>出库单号</th>
                  <th>出库类型</th>
                  <th>订单单号</th>
                  <th>发货人/</th>
                  <th>操作员</th>
                  <th>状态</th>
                  <th>审核员</th>
                  <th>创建日期</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                <td>${outstore.outno }</td>
                <td>${outstore.type==1?'销售订单出库':outstore.type==2?'采购订单退货':'' }</td>
                <td>${outstore.orderno }</td>
                <td>${outstore.deliverperson }</td>
                <td>${outstore.operater }</td>
                <td>${outstore.status==-1?'审核失败':outstore.status==0?'已取消':outstore.status==1?'待审核':outstore.status==2?'审核成功待出库':outstore.status==3?'出库完成':'' }</td>
                <td>${outstore.reviewer }</td>
                <td>${outstore.operater }</td>
                <td><fmt:formatDate value="${outstore.createtime }" type="date"/></td>
                </tr>
                
                </tbody>
                <thead>
                <tr>
                  <th >子项ID</th>
                  <th >商品名称</th>
                  <th >出库仓库</th>
                  <th>数量</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items }" var="item">
                <tr>
                 <td >${item.outstoreitemid }</td>
                  <td>${item.product.name }</td>
                  <td>${item.store.name }</td>
                  <td>${item.num }</td>
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
		$('#outSuccessButton').click(function(){
			if(window.confirm("出库完成？")){
					$.ajax({
						url:'${pageContext.request.contextPath}/outstore/outstoresuccess.htm?ids='+ids[0],
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
		$('#reviewSuccessButton').click(function(){
			if(window.confirm("审核通过？")){
					$.ajax({
						url:'${pageContext.request.contextPath}/outstore/review.htm?ids=${outstore.outno}&status=2',
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
						url:'${pageContext.request.contextPath}/outstore/review.htm?ids=${outstore.outno}&status=-1',
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
						url:'${pageContext.request.contextPath}/outstore/cancel.htm?ids=${outstore.outno}',
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
		$('.sidebar-menu .treeview:eq(4)').addClass('active');
		$('.sidebar-menu .treeview:eq(4) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(4) .treeview-menu li:eq(0)').addClass('active');
	}
	function init(){
		initmenu();
	}
	 
</script>