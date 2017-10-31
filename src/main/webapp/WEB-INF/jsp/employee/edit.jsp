<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <section  class="content-header">
  <h1>
        人员管理
        <small>员工管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">人员管理</a></li>
        <li><a href="#">员工管理</a></li>
        <li><a href="#">${empty id?'添加':'添加' }员工</a></li>
      </ol>
    </section>
    
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box box-primary">
            <div class="box-header">
              <h3 class="box-title">${empty id?'添加':'添加' }员工</h3>
            </div>
            <!-- /.box-header -->
            <form id="editForm" role="form" method="post" action="${pageContext.request.contextPath}/employee/save.htm">
              <div class="box-body">
              	<input type="hidden" value="${id }" name="id" />
                  <label for="exampleInputEmail1">员工编号*</label>
                 <%-- <div class="input-group">
			      <span class="input-group-btn">
			        <button class="btn btn-default" style="${empty id?'':'display:none' }" onclick="$.get('${pageContext.request.contextPath}/employee/generateEmpNo.htm',function(data){$('#empNo').val(data)})" type="button">自动生成</button>
			      </span>
			      <input type="text" class="form-control" value="${employee.empNo }"  ${empty id?'':'readonly' } name="empNo" maxlength="20" class="form-control" id="empNo" placeholder="请输入员工编号" required="required">
			    </div> --%>
			    <c:if test="${empty id }">
			    <div class="input-group">
			      <span class="input-group-btn">
			        <button class="btn btn-default" onclick="$.get('${pageContext.request.contextPath}/employee/generateEmpNo.htm',function(data){$('#empNo').val(data)})" type="button">自动生成</button>
			      </span>
			      <input type="text" class="form-control" name="empNo" maxlength="20" class="form-control" id="empNo" placeholder="请输入员工编号" required="required">
			    </div>
			    </c:if>
			    <c:if test="${not empty id }">
			    <div class="form-group">
			      <input type="text" class="form-control" readonly="readonly" value="${employee.empNo }" name="empNo" maxlength="20" class="form-control" id="empNo" placeholder="请输入员工编号" required="required">
			    </div>
			    </c:if>
                <div class="form-group">
                  <label for="exampleInputEmail1">员工密码*</label>
                  <input type="password"  value="${employee.password }" name="password" maxlength="20" class="form-control" id="exampleInputEmail1" placeholder="请输入员工密码" required="required">
                </div>
                <div class="form-group">
                  <label for="exampleInputEmail1">员工姓名*</label>
                  <input type="text" value="${employee.name }" name="name" maxlength="20" class="form-control" id="exampleInputEmail1" placeholder="请输入员工姓名" required="required">
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">性别*</label>
                  <select class="form-control" name="sex">
                    <option value="男" ${employee.sex=='男'?'selected':'' }>男</option>
                    <option value="女" ${employee.sex=='女'?'selected':'' }>女</option>
                  </select>
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">联系方式*</label>
                  <input type="text" class="form-control" value="${employee.tel }" id="tel" placeholder="请输入联系方式" maxlength="20" name="tel" required="required">
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">角色*</label>
                  <select class="form-control" name="role">
                    <option value="0" ${employee.role==0?'selected':'' }>普通员工</option>
                    <option value="1" ${employee.role==1?'selected':'' }>管理员</option>
                  </select>
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">部门*</label>
                  <select class="form-control" name="departmentId">
                  <c:forEach items="${departments }" var="department">
                    <option value="${department.departmentId }" ${employee.department.departmentId==department.departmentId?'selected':'' }>${department.name }</option>
                  </c:forEach>
                  </select>
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
								location.href='${pageContext.request.contextPath}/employee/listPage.htm';
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
		$('.sidebar-menu .treeview:eq(0) .treeview-menu li:eq(1)').addClass('active');
	}
</script>