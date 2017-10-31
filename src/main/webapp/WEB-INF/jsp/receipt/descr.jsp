<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <section  class="content-header">
      <h1>
        资金管理
        <small>收款单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">收款单管理</a></li>
        <li><a href="#">收款单详细信息管理</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">收款单详细信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <c:if test="${receipt.status==2 }">
            <button type="button" id="receiptSuccessButton" class="btn btn-success">收款完成</button>
            <button type="button" id="deleteItemButton" class="btn btn-danger">删除子项</button>
            </c:if>
              <table id="data-table" class="table table-breceipted table-hover">
                <thead>
                <tr>
                  <th>单号</th>
                  <th>收款类型</th>
                  <th>客户名称</th>
                  <th>最大收款期</th>
                  <th>总金额</th>
                  <th>已收金额</th>
                  <th>状态</th>
                  <th>操作员</th>
                  <th>审核员</th>
                  <th>备注</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                <td>${receipt.receiptno }</td>
                <td>${receipt.receipttype==1?'销售订单收款':receipt.receipttype==2?'采购订单退款':'' }</td>
                <td>${receipt.customer.name }</td>
                <td><fmt:formatDate value="${receipt.receiptmaxdate }" type="date"/></td>
                <td>${receipt.amount }</td>
                <td>${receipt.readyamount}</td>
                <td>${receipt.status==-1?'审核失败':receipt.status==0?'已取消':receipt.status==1?'待审核':receipt.status==2?'审核成功待收款':receipt.status==3?'收款完成':'' }</td>
                <td>${receipt.operater }</td>
                <td>${receipt.reviewer }</td>
                <td>${receipt.remarks }</td>
                </tr>
                </tbody>
                <thead>
                <tr>
                <th><input type="checkbox" value="" id="ck_all"/></th>
                  <th >子项ID</th>
                  <th >收款金额</th>
                  <th >收款方式</th>
                  <th >收款人</th>
                  <th>备注</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${items }" var="item">
                <tr>
                <td><input type="checkbox" value="${item.receiptreadyno }" name="ck_id"/></td>
                 <td >${item.receiptreadyno }</td>
                  <td>${item.amount}</td>
                  <td>${item.type==1?'现金支付':item.type==2?'网银支付':item.type==3?'支付宝支付':item.type==4?'微信支付':'' }</td>
                  <td>${item.receiptperson }</td>
                  <td>${item.remarks }</td>
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
		$('#ck_all').click(function(){
			var ck=$("#ck_all").prop("checked");     //读取所有name为'chk_list'对象的状态（是否选中）
			if(ck){
				$("input[name='ck_id']").prop("checked",true);
			}else{
				$("input[name='ck_id']").prop("checked",false);
			}
		});
		$('#receiptSuccessButton').click(function(){
			if(window.confirm("收款完成？")){
					$.ajax({
						url:'${pageContext.request.contextPath}/receipt/receiptSuccess.htm?ids=${receipt.receiptno}',
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
		$("#deleteItemButton").click(function(){
			if(window.confirm("确定删除款项？")){
				var ids=getChecks();
				if(ids.length>=1){
					$.ajax({
						url:'${pageContext.request.contextPath}/receipt/deleteItem.htm?ids='+ids,
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
				}else{
					alert('请选择');
				}
					
			}
		});
	}); 
	function initmenu(){
		$('.sidebar-menu .treeview:eq(5)').addClass('active');
		$('.sidebar-menu .treeview:eq(5) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(5) .treeview-menu li:eq(0)').addClass('active');
	}
	function init(){
		initmenu();
	}
	 
</script>