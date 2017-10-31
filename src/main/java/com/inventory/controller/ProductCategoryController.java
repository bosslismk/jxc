package com.inventory.controller;

import com.inventory.dao.ProductCategoryDAO;
import com.inventory.entity.ProductCategory;
import com.inventory.util.WebUtils;
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
public class ProductCategoryController {
	@Resource
	private ProductCategoryDAO categoryDao;
	
	@RequestMapping("/productcategory/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/productcategory/list");
		return "jsp/main";
	}
	
	@RequestMapping("/productcategory/list")
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
		Long total=categoryDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<ProductCategory> categorys=categoryDao.findAll(map);
		webPage.setResult(categorys);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/productcategory/delete")
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
				categoryDao.delete(ids_);
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
	@RequestMapping("/productcategory/edit")
	public String edit(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			ProductCategory d=categoryDao.findById(Integer.parseInt(id));
			if(d!=null){
				request.setAttribute("id", id);
				request.setAttribute("category", d);
			}
		}
		request.setAttribute("page","/Sreticeference/productcategory/edit");
		return "jsp/main";
	}
	@RequestMapping("/productcategory/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String id=request.getParameter("id");
		JSONObject result=new JSONObject();
		try {
			ProductCategory pc=new ProductCategory();
			pc.setName(name);
			if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
				pc.setCategoryId(Integer.parseInt(id));
				categoryDao.update(pc);
			}else{
				categoryDao.save(pc);
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "数据错误");
			e.printStackTrace();
		}
		WebUtils.outputWebPage(result, response);
	}
}
