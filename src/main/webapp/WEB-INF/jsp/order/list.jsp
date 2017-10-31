<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <section  class="content-header">
      <h1>
        订单管理
        <small>销售订单管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">订单管理</a></li>
        <li><a href="#">销售订单信息管理</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">销售订单列表</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <form class="form-inline" method="get" action="#">
			  <div class="form-group">
			    <label for="keyword">搜索订单编号</label>
			    <input type="text" class="form-control" id="keyword"  name="keyword" />
			  </div>
			  <button type="button" id="keywordsearchbtn" class="btn btn-default">搜索</button>
			</form>
			<br/>
			<button type="button" id="addButton" class="btn btn-success">添加</button>
			<button type="button" id="deleteButton" class="btn btn-danger">取消订单</button>
			
			<button type="button" id="descrButton" class="btn btn-info">查看订单</button>
			<button type="button" id="reviewSuccessButton" class="btn btn-success">审核通过</button>
			<button type="button" id="reviewFailButton" class="btn btn-danger">审核失败</button>
              <table id="data-table" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th><input type="checkbox" value="" id="ck_all"/></th>
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
		$('#keywordsearchbtn').click(function(){
			ajaxGetList('${pageContext.request.contextPath}/order/list.htm?keyword='+$('#keyword').val(),'${pageContext.request.contextPath}/order/list.htm');
		});
		$('#ck_all').click(function(){
			var ck=$("#ck_all").prop("checked");     //读取所有name为'chk_list'对象的状态（是否选中）
			if(ck){
				$("input[name='ck_id']").prop("checked",true);
			}else{
				$("input[name='ck_id']").prop("checked",false);
			}
		});
		$('#addButton').click(function(){
			window.location.href="${pageContext.request.contextPath}/order/edit.htm";
		});
		$('#reviewSuccessButton').click(function(){
			if(window.confirm("审核通过？")){
				var ids=getChecks();
				if(ids.length==1){
					$.ajax({
						url:'${pageContext.request.contextPath}/order/review.htm?ids='+ids[0]+'&status=2',
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
					alert('只能操作一条数据');
				}
			}
		});
		$('#reviewFailButton').click(function(){
			if(window.confirm("审核失败？")){
				var ids=getChecks();
				if(ids.length==1){
					$.ajax({
						url:'${pageContext.request.contextPath}/order/review.htm?ids='+ids[0]+'&status=-1',
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
					alert('只能操作一条数据');
				}
			}
		});
		$('#descrButton').click(function(){
			var ids=getChecks();
			if(ids.length==1){
				location.href="${pageContext.request.contextPath}/order/descr.htm?id="+ids[0];					
			}else{
				alert("只能查看一条数据");
			}
		});
		$("#deleteButton").click(function(){
			if(window.confirm("确定取消？")){
				var ids=getChecks();
				if(ids.length==1){
					$.ajax({
						url:'${pageContext.request.contextPath}/order/cancel.htm?ids='+ids[0],
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
					alert('只能取消一条数据');
				}
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
		ajaxGetList('${pageContext.request.contextPath}/order/list.htm','${pageContext.request.contextPath}/order/list.htm');
	}
	function ajaxGetList(url,targetUrl){
		$('.box-body .pagination').remove();
		$('#data-table tbody').empty();
		$.ajax({
			url:url,
			dataType:'json',
			type:'get',
			success:function(data){
				if(data.success){
					var tr="";
					if(data.result.length>0){
						$.each(data.result,function(idx,item){
							tr+="<tr>";
							tr+="<td><input type=\"checkbox\" value=\""+item.orderno+"\" name=\"ck_id\"/></td>";
							tr+="<td>"+item.orderno+"</td>";
							tr+="<td>"+item.customer.name+"</td>";
							tr+="<td>"+item.saleperson+"</td>";
							tr+="<td>"+item.amount+"</td>";
							var paytype='';
							if(item.paytype==1){
								paytype='现金支付';
							}
							if(item.paytype==2){
								paytype='网银支付';
							}
							if(item.paytype==3){
								paytype='支付宝支付';
							}
							if(item.paytype==4){
								paytype='微信支付';
							}
							tr+="<td>"+paytype+"</td>";
							var status='';
							if(item.status==-1){
								status='审核失败';
							}
							if(item.status==0){
								status='取消订单';
							}
							if(item.status==1){
								status='待审核';
							}
							if(item.status==2){
								status='审核通过待出库';
							}
							if(item.status==3){
								status='出库完成';
							}
							tr+="<td>"+status+"</td>";
							tr+="<td>"+getDateByMillisecond(item.saledate.time)+"</td>";
							tr+="<td>"+item.operater+"</td>";
							tr+="<td>"+item.reviewer+"</td>";
							var reviewdate=''; 
							if(item.reviewdate!=null){
								reviewdate=getDateByMillisecond(item.reviewdate.time)
							}
							tr+="<td>"+reviewdate+"</td>";
							tr+="<td>"+item.address+"</td>";
							tr+="<td>"+item.remarks+"</td>";
							tr+="</tr>";
						});
						createPagination(data,targetUrl);
					}else{
						tr+="<tr><td colspan='13' align='center'>没有查询到数据</td></tr>";
					}
					
					$(tr).appendTo('#data-table tbody');
					
				}else{
					alert('数据错误，'+data.message);
				}
				
			}
		});
	}
</script>