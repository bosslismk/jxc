<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <section  class="content-header">
      <h1>
        商品管理
        <small>商品信息管理</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#">商品管理</a></li>
        <li><a href="#">商品信息管理</a></li>
        <li><a href="#">仓储管理</a></li>
      </ol>
    </section>
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">仓储信息</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
			<br/>
              <table id="data-table" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th colspan="4">商品：${product.name }->总库存：${product.stocks }</th>
                </tr>
                <tr>
                  <th>仓库ID</th>
                  <th>仓库名称</th>
                  <th>仓库储量</th>
                  <th>仓库地址</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach  items="${pss }" var="ps">
                <tr>
                <td>${ps.store.storeId }</td>
                <td>${ps.store.name }</td>
                <td>${ps.stock}</td>
                <td>${ps.store.address }</td>
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
	}); 
	function initmenu(){
		$('.sidebar-menu .treeview:eq(1)').addClass('active');
		$('.sidebar-menu .treeview:eq(1) .treeview-menu').addClass('menu-open');
		$('.sidebar-menu .treeview:eq(1) .treeview-menu li:eq(1)').addClass('active');
	}
	function init(){
		initmenu();
		//ajaxGetList('${pageContext.request.contextPath}/product/list.htm','${pageContext.request.contextPath}/product/list.htm');
	}
	
</script>