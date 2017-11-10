package com.inventory.controller;

import com.inventory.dao.*;
import com.inventory.entity.*;
import com.inventory.util.OrderUtils;
import com.inventory.util.WebPage;
import com.inventory.util.WebUtils;
import net.sf.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库
 */

@Controller
public class OutStoreController {
	@Resource
	private OutStoreDAO outstoreDao;
	@Resource
	private OutStoreItemDAO itemDao;
	@Resource
	private EmployeeDAO empDao;
	@Resource
	private OrderItemDAO orderItemDao;
	@Resource
	private OrderDAO orderDao;
	@Resource
	private ProductStoreLinkDAO pslDao;
	@Resource
	private StoreDAO storeDao;

	/**
	 * 出库首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/outstore/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/outstore/list");
		return "jsp/main";
	}

	/**
	 * 出库搜索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/outstore/list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		String page=request.getParameter("page");
		String keyword=request.getParameter("keyword");
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuilder params=new StringBuilder();
		if(StringUtils.isNotBlank(keyword)){
			map.put("outstoreno","%"+keyword+"%");
			params.append("keyword="+keyword);
			request.setAttribute("keyword", keyword);
		}
		Long total=outstoreDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<OutStore> outstores=outstoreDao.findAll(map);
		webPage.setResult(outstores);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 出库删除
	 * @param request
	 * @param response
	 */
	@Transactional
	@RequestMapping("/outstore/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();
		try {
			String[] ids=idsStr.split(",");
			if(ids.length>0){
				outstoreDao.delete(ids);
				itemDao.deleteByOutStoreNo(ids);
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

	/**
	 * 出库新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/outstore/edit")
	public String edit(HttpServletRequest request){
		request.setAttribute("stores", storeDao.findAll(null));
		request.setAttribute("page","/Sreticeference/outstore/edit");
		return "jsp/main";
	}

	/**
	 * 查看订单号
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/outstore/orderitems/select")
	public void itemsSelect(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String orderno=request.getParameter("orderno");
		String type=request.getParameter("type");
		JSONArray ja=new JSONArray();
		if("1".equals(type)){
			
			List<OrderItem> items=orderItemDao.findByOrderNo(orderno);
			for(OrderItem item:items){
				JSONObject jo=new JSONObject();
				jo.put("id", item.getProduct().getProductid());
				jo.put("text", item.getProduct().getName());
				ja.add(jo);
			}
			WebUtils.outputWebPage(ja, response);
		}
	}

	/**
	 * 出库查看详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/outstore/descr")
	public String descr(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			OutStore d=outstoreDao.findById(id);
			if(d!=null){
				request.setAttribute("outstore", d);
				request.setAttribute("items", itemDao.findByOutStoreNo(id));
			}
		}
		request.setAttribute("page","/Sreticeference/outstore/descr");
		return "jsp/main";
	}

	/**
	 * 新增
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping("/outstore/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String type=request.getParameter("type");
		String orderno=request.getParameter("orderno");
		String deliverperson=request.getParameter("deliverperson");
		String count=request.getParameter("count");
		JSONObject result=new JSONObject();
		try {
			if(Integer.parseInt(count)>0){
				if(empDao.findById(deliverperson)!=null){
					boolean storeNotSame=true;
					String outstoreNo= OrderUtils.createOrderNo();
					List<OutStoreItem> items=new ArrayList<OutStoreItem>();
					OK:for(int i = 0 ;i < Integer.parseInt(count) ; i++){
						String productId=request.getParameter("productid"+i);
						String[] stores=request.getParameterValues("store"+i);
						String[] nums=request.getParameterValues("num"+i);
						for(int j=0;j<stores.length;j++){
							for(OutStoreItem tmp:items){
								if(tmp.getProductid()==Integer.parseInt(productId)&&tmp.getStoreid()==Integer.parseInt(stores[j])){
									storeNotSame=false;
									break OK;
								}
							}
							OutStoreItem osi=new OutStoreItem();
							osi.setNum(Integer.parseInt(nums[j]));
							osi.setOutstoreno(outstoreNo);
							osi.setProductid(Integer.parseInt(productId));
							osi.setStoreid(Integer.parseInt(stores[j]));
							items.add(osi);
						}
					}
					if(storeNotSame){
						OutStore os=new OutStore();
						os.setDeliverperson(deliverperson);
						os.setOperater(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
						os.setOrderno(orderno);
						os.setOutno(outstoreNo);
						os.setStatus(1);
						os.setType(Integer.parseInt(type));
						outstoreDao.save(os);
						for(OutStoreItem item:items){
							itemDao.save(item);
						}
						result.put("success", true);
					}else{
						result.put("success", false);
						result.put("message", "同一件商品中同一个仓库只能选择一次");
					}
				}else{
					result.put("success", false);
					result.put("message", "发货人编号不存在");
				}
			}else{
				result.put("success", false);
				result.put("message", "至少输入一件商品");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "数据错误");
		}
		WebUtils.outputWebPage(result, response);
	}

	/**
	 * 取消出库
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/outstore/cancel")
	public void cancel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			OutStore d=outstoreDao.findById(id);
			if(d!=null&&d.getStatus()==1){
				d.setStatus(0);
				outstoreDao.update(d);
				json.put("success", true);
			}else{
				json.put("success", false);
				json.put("message", "状态不符，必须为待审核");
			}
		}else{
			json.put("success", false);
			json.put("message", "单号错误");
		}
		WebUtils.outputWebPage(json, response);
	}

	/**
	 * 出库审核通过/出库审核失败
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/outstore/review")
	public void review(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		String status=request.getParameter("status");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			OutStore d=outstoreDao.findById(id);
			if(d!=null){
				if("-1".equals(status)&&d.getStatus()==1){
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(-1);
					outstoreDao.update(d);
					json.put("success", true);
				}else if ("2".equals(status)&&d.getStatus()==1){
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(2);
					outstoreDao.update(d);
					json.put("success", true);
				}else{
					json.put("success", false);
					json.put("message", "状态不符");
				}
			}else{
				json.put("success", false);
				json.put("message", "单号不存在");
			}
		}else{
			json.put("success", false);
			json.put("message", "单号错误");
		}
		WebUtils.outputWebPage(json, response);
	}

	/**
	 * 确认出库
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/outstore/outstoresuccess")
	public void outstoresuccess(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			OutStore d=outstoreDao.findById(id);
			if(d!=null){
				if(d.getStatus()==2){
					//更新出库单状态
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(3);
					outstoreDao.update(d);
					if(d.getType()==1){
						//更新订单状态
						String orderno=d.getOrderno();
						Order order=orderDao.findById(orderno);
						order.setStatus(3);
						orderDao.update(order);
					}
					//更新库存
					List<OutStoreItem> items=itemDao.findByOutStoreNo(id);
					for(OutStoreItem item:items){
						ProductStoreLink psl=new ProductStoreLink();
						psl.setStock(-item.getNum());
						psl.setProductId(item.getProduct().getProductid());
						psl.setStoreId(item.getStore().getStoreId());
						pslDao.updateStock(psl);
					}
					json.put("success", true);
				}else{
					json.put("success", false);
					json.put("message", "状态不符");
				}
			}else{
				json.put("success", false);
				json.put("message", "单号不存在");
			}
		}else{
			json.put("success", false);
			json.put("message", "单号错误");
		}
		WebUtils.outputWebPage(json, response);
	}
}
