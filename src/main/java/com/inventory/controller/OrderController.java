package com.inventory.controller;

import com.inventory.dao.EmployeeDAO;
import com.inventory.dao.OrderDAO;
import com.inventory.dao.OrderItemDAO;
import com.inventory.dao.ProductDAO;
import com.inventory.entity.Order;
import com.inventory.entity.OrderItem;
import com.inventory.util.OrderUtils;
import com.inventory.util.WebUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import com.inventory.util.WebPage;

@Controller
public class OrderController {
	@Resource
	private OrderItemDAO itemDao;
	@Resource
	private OrderDAO orderDao;
	@Resource
	private EmployeeDAO empDao;
	@Resource
	private ProductDAO productDao;
	
	@RequestMapping("/order/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/order/list");
		return "jsp/main";
	}
	
	@RequestMapping("/order/list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		String page=request.getParameter("page");
		String keyword=request.getParameter("keyword");
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuilder params=new StringBuilder();
		if(StringUtils.isNotBlank(keyword)){
			map.put("orderno","%"+keyword+"%");
			params.append("keyword="+keyword);
			request.setAttribute("keyword", keyword);
		}
		Long total=orderDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<Order> orders=orderDao.findAll(map);
		webPage.setResult(orders);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Transactional
	@RequestMapping("/order/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();
		try {
			String[] ids=idsStr.split(",");
			if(ids.length>0){
				orderDao.delete(ids);
				itemDao.deleteByOrderNo(ids);
				result.put("success", true);
			}else{
				result.put("success", false);
				result.put("message", "ID错误");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "错误");
		}
		try {
			WebUtils.outputWebPage(result, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/order/edit")
	public String edit(HttpServletRequest request){
		request.setAttribute("page","/Sreticeference/order/edit");
		return "jsp/main";
	}
	@RequestMapping("/order/descr")
	public String descr(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Order d=orderDao.findById(id);
			if(d!=null){
				request.setAttribute("order", d);
				request.setAttribute("items", itemDao.findByOrderNo(id));
			}
		}
		request.setAttribute("page","/Sreticeference/order/descr");
		return "jsp/main";
	}
	@Transactional
	@RequestMapping("/order/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String customerid=request.getParameter("customerid");
		String saleperson=request.getParameter("saleperson");
		String saledate=request.getParameter("saledate");
		String paytype=request.getParameter("paytype");
		String address=request.getParameter("address");
		String remarks=request.getParameter("remarks");
		String count=request.getParameter("count");
		JSONObject result=new JSONObject();
		try{
			if(Integer.parseInt(count)>0){
				if(empDao.findById(saleperson)!=null){
					boolean productNotEmpty=true;
					boolean productNotSame=true;
					String orderNo= OrderUtils.createOrderNo();
					BigDecimal amount=new BigDecimal(0);
					Order order=new Order();
					order.setAddress(address);
					order.setCustomerid(Integer.parseInt(customerid));
					order.setOperater(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					order.setOrderno(orderNo);
					order.setPaytype(Integer.parseInt(paytype));
					order.setRemarks(remarks);
					order.setSaledate(new SimpleDateFormat("yyyy年MM月dd日").parse(saledate));
					order.setSaleperson(saleperson);
					order.setStatus(1);
					List<OrderItem> items=new ArrayList<OrderItem>();
					OK:for(int i = 0 ;i < Integer.parseInt(count) ; i++){
						String productid=request.getParameter("productid"+i);
						String num=request.getParameter("num"+i);
						String price=request.getParameter("price"+i);
						if(StringUtils.isNotBlank(productid)&&StringUtils.isNotBlank(num)){
							//查询是否有重复的 有重复的则需要修改
							for(OrderItem tmp:items){
								if(tmp.getProductid()==Integer.parseInt(productid)){
									productNotSame=false;
									break OK;
								}
							}
							OrderItem item=new OrderItem();
							item.setNum(Integer.parseInt(num));
							item.setOrderno(orderNo);
							item.setProductid(Integer.parseInt(productid));
							item.setPrice(new BigDecimal(price));
							items.add(item);
							amount=amount.add(new BigDecimal(price).multiply(new BigDecimal(Integer.parseInt(num))));
						}else{
							productNotEmpty=false;
							break OK;
						}
					}
					if(productNotEmpty){
						if(productNotSame){
							order.setAmount(amount);
							orderDao.save(order);
							for(OrderItem item:items){
								itemDao.save(item);
							}
							result.put("success", true);
						}else{
							result.put("success", false);
							result.put("message", "请不要重复商品数据");
						}
					}else{
						result.put("success", false);
						result.put("message", "请添加完整商品信息");
					}
				}else{
					result.put("success", false);
					result.put("message", "销售员工编号不存在");
				}
				
			}else{
				result.put("success", false);
				result.put("message", "请至少添加一件商品");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "数据错误");
		}
		WebUtils.outputWebPage(result, response);
	}
	
	@RequestMapping("/order/cancel")
	public void cancel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Order d=orderDao.findById(id);
			if(d!=null&&d.getStatus()==1){
				d.setStatus(0);
				orderDao.update(d);
				json.put("success", true);
			}else{
				json.put("success", false);
				json.put("message", "订单状态不符，必须为待审核订单");
			}
		}else{
			json.put("success", false);
			json.put("message", "单号错误");
		}
		WebUtils.outputWebPage(json, response);
	}
	@RequestMapping("/order/review")
	public void review(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		String status=request.getParameter("status");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Order d=orderDao.findById(id);
			if(d!=null){
				if("-1".equals(status)&&d.getStatus()==1){
					d.setReviewdate(new Date());
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(-1);
					orderDao.update(d);
					json.put("success", true);
				}else if ("2".equals(status)&&d.getStatus()==1){
					d.setReviewdate(new Date());
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(2);
					orderDao.update(d);
					json.put("success", true);
				}else{
					json.put("success", false);
					json.put("message", "订单状态不符");
				}
			}else{
				json.put("success", false);
				json.put("message", "订单不存在");
			}
		}else{
			json.put("success", false);
			json.put("message", "单号错误");
		}
		WebUtils.outputWebPage(json, response);
	}
}
