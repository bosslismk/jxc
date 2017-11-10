package com.inventory.controller;

import com.inventory.dao.CustomerDAO;
import com.inventory.dao.EmployeeDAO;
import com.inventory.dao.ReceiptDAO;
import com.inventory.dao.ReceiptReadyDAO;
import com.inventory.entity.Receipt;
import com.inventory.entity.ReceiptReady;
import com.inventory.util.OrderUtils;
import com.inventory.util.WebPage;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收款
 */
@Controller
public class ReceiptController {
	@Resource
	private EmployeeDAO empDao;
	@Resource
	private CustomerDAO customerDao;
	@Resource
	private ReceiptDAO receiptDao;
	@Resource
	private ReceiptReadyDAO receiptReadyDao;

	/**
	 * 收款首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/receipt/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/receipt/list");
		return "jsp/main";
	}

	/**
	 * 收款搜索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/receipt/list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		String page=request.getParameter("page");
		String keyword=request.getParameter("keyword");
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuilder params=new StringBuilder();
		if(StringUtils.isNotBlank(keyword)){
			map.put("receiptno","%"+keyword+"%");
			params.append("keyword="+keyword);
			request.setAttribute("keyword", keyword);
		}
		Long total=receiptDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<Receipt> receipts=receiptDao.findAll(map);
		webPage.setResult(receipts);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取消收款单
	 * @param request
	 * @param response
	 */
	@Transactional
	@RequestMapping("/receipt/cancel")
	public void cancel(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();
		try {
			Receipt r=	receiptDao.findById(idsStr);
			if(r!=null&&r.getStatus()==1){
				r.setStatus(0);
				receiptDao.update(r);
				result.put("success", true);
			} else{
				result.put("success", false);
				result.put("message", "收款单状态不符");
			}
		} catch (Exception e) {
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
	 * 收款审核通过/收款审核失败
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/receipt/review")
	public void review(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		String status=request.getParameter("status");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Receipt d=	receiptDao.findById(id);
			if(d!=null){
				if("-1".equals(status)&&d.getStatus()==1){
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(-1);
					receiptDao.update(d);
					json.put("success", true);
				}else if ("2".equals(status)&&d.getStatus()==1){
					d.setReviewer(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
					d.setStatus(2);
					receiptDao.update(d);
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
	@RequestMapping("/receipt/receiptSuccess")
	public void receiptSuccess(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("ids");
		JSONObject json=new JSONObject();
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Receipt d=	receiptDao.findById(id);
			if(d!=null){
				if( d.getStatus()==2){
					d.setStatus(3);
					receiptDao.update(d);
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
	@RequestMapping("/receipt/addItemStatus")
	public void addItemStatus(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();
		try {
			if(StringUtils.isNotBlank(idsStr)){
				Receipt rec=receiptDao.findById(idsStr);
				if(rec!=null){
					if(rec.getStatus()==2){
						result.put("success", true);
					}else{
						result.put("success", false);
						result.put("message", "状态必须为已审核通过");
					}
				}else{
					result.put("success", false);
					result.put("message", "ID错误");
				}
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
	 * 收款新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/receipt/edit")
	public String edit(HttpServletRequest request){
		request.setAttribute("page","/Sreticeference/receipt/edit");
		return "jsp/main";
	}
	@RequestMapping("/receipt/addItem")
	public String addItem(HttpServletRequest request){
		request.setAttribute("id", request.getParameter("ids"));
		request.setAttribute("page","/Sreticeference/receipt/addItem");
		return "jsp/main";
	}
	@RequestMapping("/receipt/descr")
	public String descr(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Receipt d=receiptDao.findById(id);
			if(d!=null){
				request.setAttribute("receipt", d);
				request.setAttribute("items", receiptReadyDao.findByReceiptNo(id));
			}
		}
		request.setAttribute("page","/Sreticeference/receipt/descr");
		return "jsp/main";
	}

	/**
	 * 收款新增
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Transactional
	@RequestMapping("/receipt/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String customerid=request.getParameter("customerid");
		String receipttype=request.getParameter("receipttype");
		String receiptmaxdate=request.getParameter("receiptmaxdate");
		String amount=request.getParameter("amount");
		String remarks=request.getParameter("remarks");
		JSONObject result=new JSONObject();
		try{ 
			Receipt rec=new Receipt();
			rec.setAmount(new BigDecimal(amount));
			rec.setCustomerid(Integer.parseInt(customerid));
			rec.setOperater(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
			rec.setReadyamount(new BigDecimal(0));
			rec.setReceiptmaxdate(new SimpleDateFormat("yyyy年MM月dd日").parse(receiptmaxdate));
			rec.setReceipttype(Integer.parseInt(receipttype));
			rec.setReceiptno(OrderUtils.createOrderNo());
			rec.setStatus(1);
			rec.setRemarks(remarks);
			receiptDao.save(rec);
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "数据错误");
		}
		WebUtils.outputWebPage(result, response);
	}
	
	@Transactional
	@RequestMapping("/receipt/saveItem")
	public void saveItem(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		String amount=request.getParameter("amount");
		String receiptperson=request.getParameter("receiptperson");
		String remarks=request.getParameter("remarks");
		JSONObject result=new JSONObject();
		try{ 
			ReceiptReady rr=new ReceiptReady();
			rr.setAmount(new BigDecimal(amount));
			rr.setOperater(WebUtils.getCurrentEmployee(request.getSession()).getEmpNo());
			rr.setReceiptno(id);
			rr.setReceiptperson(receiptperson);
			rr.setRemarks(remarks);
			rr.setType(Integer.parseInt(type));
			receiptReadyDao.save(rr);
			
			Receipt r=receiptDao.findById(id);
			r.setReadyamount(r.getReadyamount().add(new BigDecimal(amount)));
			receiptDao.update(r);
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "数据错误");
		}
		WebUtils.outputWebPage(result, response);
	}
	@Transactional
	@RequestMapping("/receipt/deleteItem")
	public void deleteItem(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();
		try{ 
			String[] idsStr_=idsStr.split(",");
			BigDecimal amount=new BigDecimal(0);
			Integer[] ids_=new Integer[idsStr_.length];
			int i=0;
			String receipyNo=null;
			for(String idStr:idsStr_){
				ids_[i]=Integer.parseInt(idStr);
				ReceiptReady rr=receiptReadyDao.findById(Integer.parseInt(idStr));
				amount=amount.add(rr.getAmount());
				if(i==0){
					receipyNo=rr.getReceipt().getReceiptno();
				}
				i++;
			}
			receiptReadyDao.delete(ids_);
			
			Receipt r=receiptDao.findById(receipyNo);
			r.setReadyamount(r.getReadyamount().subtract(amount));
			receiptDao.update(r);
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "数据错误");
		}
		WebUtils.outputWebPage(result, response);
	}
}
