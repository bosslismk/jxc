<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <section  class="content-header">
      <h1>
        人员管理
        <small>部门管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">人员管理</a></li>
        <li><a href="#">部门管理</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">部门列表</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <form class="form-inline" method="get" action="#">
			  <div class="form-group">
			    <label for="keyword">搜索部门名称</label>
			    <input type="text" class="form-control" id="keyword"  name="keyword" />
			  </div>
			  <button type="button" id="keywordsearchbtn" class="btn btn-default">搜索</button>
			</form>
			<br/>
			<button type="button" id="addButton" class="btn btn-success">添加</button>
			<button type="button" id="editButton" class="btn btn-warning">编辑</button>
			<button type="button" id="deleteButton" class="btn btn-danger">删除</button>
			
              <table id="data-table" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th><input type="checkbox" value="" id="ck_all"/></th>
                  <th>ID</th>
                  <th>部门名称</th>
                  <th>部门经理工号</th>
                  <th>部门电话</th>
                  <th>部门简介</th>
                  <th>创建时间</th>
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
			ajaxGetList('${pageContext.request.contextPath}/department/list.htm?keyword='+$('#keyword').val(),'${pageContext.request.contextPath}/department/list.htm');
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
			window.location.href="${pageContext.request.contextPath}/department/edit.htm";
		});
		$('#editButton').click(function(){
			var ids=getChecks();
			if(ids.length==1){
				location.href="${pageContext.request.contextPath}/department/edit.htm?id="+ids[0];					
			}else{
				alert("只能编辑一条数据");
			}
		});
		$("#deleteButton").click(function(){
			if(window.confirm("确定删除？")){
				var ids=getChecks();
				if(ids.length>=1){
					$.ajax({
						url:'${pageContext.request.contextPath}/department/delete.htm?ids='+ids,
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
					alert('请选择数据');
				}
			}
		});
	}); 
	function initmenu(){
		$('.sidebar-menu .treeview:eq(0)').addClass('active');
		$('.sidebar-menu .treeview:eq(0) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(0) .treeview-menu li:eq(0)').addClass('active');
	}
	function init(){
		initmenu();
		ajaxGetList('${pageContext.request.contextPath}/department/list.htm','${pageContext.request.contextPath}/department/list.htm');
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
							tr+="<tr>"
							tr+="<td><input type=\"checkbox\" value=\""+item.departmentId+"\" name=\"ck_id\"/></td>";
							tr+="<td>"+item.departmentId+"</td>";
							tr+="<td>"+item.name+"</td>";
							tr+="<td>"+item.manager+"</td>";
							tr+="<td>"+item.tel+"</td>";
							tr+="<td>"+item.remarks+"</td>";
							tr+="<td>"+getDateByMillisecond(item.createTime.time)+"</td>";
							tr+="</tr>";
						});
						createPagination(data,targetUrl);
					}else{
						tr+="<tr><td colspan='7' align='center'>没有查询到数据</td></tr>";
					}
					
					$(tr).appendTo('#data-table tbody');
					
				}else{
					alert('数据错误，'+data.message);
				}
				
			}
		});
	}
</script>