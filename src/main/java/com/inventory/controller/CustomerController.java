package com.inventory.controller;

import com.inventory.dao.CustomerDAO;
import com.inventory.entity.Customer;
import com.inventory.util.WebUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.inventory.util.WebPage;

@Controller
public class CustomerController {
	@Resource
	private CustomerDAO customerDao;
	
	@RequestMapping("/customer/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/customer/list");
		return "jsp/main";
	}
	
	@RequestMapping("/customer/list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		String page=request.getParameter("page");
		String keyword=request.getParameter("keyword");
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuilder params=new StringBuilder();
		if(StringUtils.isNotBlank(keyword)){
			map.put("name","%"+keyword+"%");
			params.append("keyword="+keyword);
			request.setAttribute("keyword", keyword);
		}
		Long total=customerDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<Customer> customers=customerDao.findAll(map);
		webPage.setResult(customers);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/customer/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();

		try {
			String[] ids=idsStr.split(",");
			if(ids.length>0){
				Integer[] ids_=new Integer[ids.length];
				for(int i=0;i<ids.length;i++){
					ids_[i]=Integer.parseInt(ids[i]);
				}
				customerDao.delete(ids_);
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
	@RequestMapping("/customer/edit")
	public String edit(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Customer d=customerDao.findById(Integer.parseInt(id));
			if(d!=null){
				request.setAttribute("id", id);
				request.setAttribute("customer", d);
			}
		}
		request.setAttribute("page","/Sreticeference/customer/edit");
		return "jsp/main";
	}
	@RequestMapping("/customer/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String id=request.getParameter("id");
		String address=request.getParameter("address");
		String bankaccount=request.getParameter("bankaccount");
		String contactman=request.getParameter("contactman");
		String depositbank=request.getParameter("depositbank");
		String email=request.getParameter("email");
		String position=request.getParameter("position");
		String remarks=request.getParameter("remarks");
		String status=request.getParameter("status");
		String taxnum=request.getParameter("taxnum");
		String tel=request.getParameter("tel");
		String website=request.getParameter("website");
		JSONObject result=new JSONObject();
		try {
			Customer p=new Customer();
			p.setAddress(address);
			p.setBankaccount(bankaccount);
			p.setContactman(contactman);
			p.setDepositbank(depositbank);
			p.setEmail(email);
			p.setName(name);
			p.setPosition(position);
			p.setRemarks(remarks);
			p.setStatus(Integer.parseInt(status));
			p.setTaxnum(taxnum);
			p.setTel(tel);
			p.setWebsite(website);
			if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
				p.setCustomerid(Integer.parseInt(id));
				customerDao.update(p);
			}else{
				customerDao.save(p);
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "数据错误");
			e.printStackTrace();
		}
		WebUtils.outputWebPage(result, response);
	}
	
	@RequestMapping("/customer/updateStatus")
	public void updateStatus(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		String status=request.getParameter("status");
		JSONObject result=new JSONObject();
		try {
			String[] ids=idsStr.split(",");
			if(ids.length>0){
				for(int i=0;i<ids.length;i++){
					Customer c=new Customer();
					c.setCustomerid(Integer.parseInt(ids[i]));
					c.setStatus(Integer.parseInt(status));
					customerDao.update(c);
				}
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
	
	@RequestMapping("/customer/select")
	public void select(HttpServletRequest request,HttpServletResponse response){
		String keyword=request.getParameter("keyword");
		JSONArray ja=new JSONArray();
		if(StringUtils.isNotBlank(keyword)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("name","%"+keyword+"%");
			map.put("status",1);
			map.put("size", 10);
			map.put("start", 0);
			List<Customer> customers=customerDao.findAll(map);
			for(Customer c:customers){
				JSONObject jo=new JSONObject();
				jo.put("id", c.getCustomerid());
				jo.put("text", c.getName());
				ja.add(jo);
			}
		}
		try {
			WebUtils.outputWebPage(ja, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
