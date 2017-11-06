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
        <li><a href="#">${empty id?'添加':'添加' }部门</a></li>
      </ol>
    </section>
    
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box box-primary">
            <div class="box-header">
              <h3 class="box-title">${empty id?'添加':'添加' }部门</h3>
            </div>
            <!-- /.box-header -->
            <form id="editForm" role="form" method="post" action="${pageContext.request.contextPath}/department/save.htm">
              <div class="box-body">
              	<input type="hidden" value="${id }" name="id" />
                <div class="form-group">
                  <label for="exampleInputEmail1">部门名称*</label>
                  <input type="text" value="${department.name }" name="name" maxlength="20" class="form-control" id="exampleInputEmail1" placeholder="请输入部门名称" required="required">
                </div>
                <div class="form-group">
                  <label>部门经理*</label>
                  <input type="text" class="form-control" value="${department.manager }" maxlength="20" name="manager" id="manager" placeholder="请输入部门经理员工号" required="required">
                </div>
                <div class="form-group">
                  <label>部门联系方式*</label>
                  <input type="text" class="form-control" value="${department.tel }" id="tel" placeholder="请输入联系方式" maxlength="20" name="tel" required="required">
                </div>
                <div class="form-group">
                  <label>部门简介</label>
                  <textarea style="min-height: 200px" class="form-control" name="remarks" maxlength="200" placeholder="请输入200字内的简介">${department.remarks }</textarea>
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
          </div>
          </div>
          </section>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script>
	$(function(){
		initmenu();
		$("#submitBtn").click(function(){
			$("#editForm").validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						success:function(data) {
							// 提交成功
							data=eval("("+data+")");  
							if(data.success){
								alert('成功');
								location.href='${pageContext.request.contextPath}/department/listPage.htm';
							}else{
								alert(data.message);
							}
						} ,
						// 提交失败
						error:function(response){
						}
					});
				}
			});
		});
	}); 
	function initmenu(){
		$('.sidebar-menu .treeview:eq(0)').addClass('active');
		$('.sidebar-menu .treeview:eq(0) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(0) .treeview-menu li:eq(0)').addClass('active');
	}
</script>