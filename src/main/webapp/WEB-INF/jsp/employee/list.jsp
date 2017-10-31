<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <section  class="content-header">
      <h1>
        人员管理
        <small>员工管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">人员管理</a></li>
        <li><a href="#">员工管理</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">员工列表</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <form class="form-inline" method="get" action="#">
			  <div class="form-group">
			    <label for="keyword">搜索员工姓名或编号</label>
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
                  <th>员工编号</th>
                  <th>员工名字</th>
                  <th>性别</th>
                  <th>联系方式</th>
                  <th>所属部门</th>
                  <th>角色</th>
                  <th>加入时间</th>
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
			ajaxGetList('${pageContext.request.contextPath}/employee/list.htm?keyword='+$('#keyword').val(),'${pageContext.request.contextPath}/employee/list.htm');
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
			window.location.href="${pageContext.request.contextPath}/employee/edit.htm";
		});
		$('#editButton').click(function(){
			var ids=getChecks();
			if(ids.length==1){
				location.href="${pageContext.request.contextPath}/employee/edit.htm?id="+ids[0];					
			}else{
				alert("只能编辑一条数据");
			}
		});
		$("#deleteButton").click(function(){
			if(window.confirm("确定删除？")){
				var ids=getChecks();
				if(ids.length>=1){
					$.ajax({
						url:'${pageContext.request.contextPath}/employee/delete.htm?ids='+ids,
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
		$('.sidebar-menu .treeview:eq(0) .treeview-menu li:eq(1)').addClass('active');
	}
	function init(){
		initmenu();
		ajaxGetList('${pageContext.request.contextPath}/employee/list.htm','${pageContext.request.contextPath}/employee/list.htm');
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
							tr+="<td><input type=\"checkbox\" value=\""+item.empNo+"\" name=\"ck_id\"/></td>";
							tr+="<td>"+item.empNo+"</td>";
							tr+="<td>"+item.name+"</td>";
							tr+="<td>"+item.sex+"</td>";
							tr+="<td>"+item.tel+"</td>";
							tr+="<td>"+item.department.name+"</td>";
							tr+="<td>"+item.role+"</td>";
							tr+="<td>"+getDateByMillisecond(item.createTime.time)+"</td>";
							tr+="</tr>";
						});
						createPagination(data,targetUrl);
					}else{
						tr+="<tr><td colspan='8' align='center'>没有查询到数据</td></tr>";
					}
					
					$(tr).appendTo('#data-table tbody');
					
				}else{
					alert('数据错误，'+data.message);
				}
				
			}
		});
	}
</script>