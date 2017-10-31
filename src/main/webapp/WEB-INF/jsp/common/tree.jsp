<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ul class="sidebar-menu">
	<li class="header">菜单</li>
	<li class="treeview">
		<a href="#"> 
		<i class="fa fa-users"></i>
		<span>人员管理</span> 
		<span class="pull-right-container"> 
			<i class="fa fa-angle-left pull-right"></i>
		</span>
		</a>
		<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/department/listPage.htm"  >
					<i class="fa fa-indent"></i> 
					部门管理
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/employee/listPage.htm">
					<i class="fa fa-user"></i>
					员工管理
				</a>
			</li>
		</ul>
	</li>
	<li class="treeview">
		<a href="#"> 
		<i class="fa fa-reorder"></i>
		<span>商品管理</span> 
		<span class="pull-right-container"> 
			<i class="fa fa-angle-left pull-right"></i>
		</span>
		</a>
		<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/productcategory/listPage.htm"  >
					<i class="fa fa-list"></i> 
					商品分类管理
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/product/listPage.htm">
					<i class="fa fa-diamond"></i>
					商品信息管理
				</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/store/listPage.htm">
					<i class="fa fa-building"></i>
					仓库信息管理
				</a>
			</li>
		</ul>
	</li>
	<li class="treeview">
		<a href="#"> 
		<i class="fa fa-group"></i>
		<span>客户管理</span> 
		<span class="pull-right-container"> 
			<i class="fa fa-angle-left pull-right"></i>
		</span>
		</a>
		<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/customer/listPage.htm"  >
					<i class="fa fa-user"></i> 
					客户信息管理
				</a>
			</li>
		</ul>
	</li>
	<li class="treeview">
		<a href="#"> 
		<i class="fa fa-list"></i>
		<span>订单管理</span> 
		<span class="pull-right-container"> 
			<i class="fa fa-angle-left pull-right"></i>
		</span>
		</a>
		<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/order/listPage.htm"  >
					<i class="fa fa-align-center"></i> 
					销售订单管理
				</a>
			</li>
		</ul>
	</li>
	<li class="treeview">
		<a href="#"> 
		<i class="fa fa-archive"></i>
		<span>库存管理</span> 
		<span class="pull-right-container"> 
			<i class="fa fa-angle-left pull-right"></i>
		</span>
		</a>
		<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/outstore/listPage.htm"  >
					<i class="fa fa-arrow-circle-up"></i> 
					出库单管理
				</a>
			</li>
		</ul>
		<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/order/listPage.htm"  >
					<i class="fa fa-arrow-circle-down"></i> 
					入库单管理
				</a>
			</li>
		</ul>
	</li>
	<li class="treeview">
		<a href="#"> 
		<i class="fa fa-money"></i>
		<span>资金管理</span> 
		<span class="pull-right-container"> 
			<i class="fa fa-angle-left pull-right"></i>
		</span>
		</a>

		<%--<ul class="treeview-menu">
			<li>
				<a href="${pageContext.request.contextPath}/receipt/descr.htm"  >
					<i class="fa fa-arrow-circle-up"></i>
					付款单管理
				</a>
			</li>
		</ul>--%>
		<ul class="treeview-menu">
			<li>
				<a href=" /receipt/listPage.htm"  >
					<i class="fa fa-arrow-circle-down"></i> 
					收款单管理
				</a>
			</li>
		</ul>

	</li>
</ul>