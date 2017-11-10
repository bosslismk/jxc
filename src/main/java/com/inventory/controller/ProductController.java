package com.inventory.controller;

import com.inventory.dao.ProductCategoryDAO;
import com.inventory.dao.ProductDAO;
import com.inventory.dao.ProductStoreLinkDAO;
import com.inventory.entity.Product;
import com.inventory.entity.ProductStoreLink;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inventory.util.WebPage;

/**
 * 商品
 */
@Controller
public class ProductController {
	@Resource
	private ProductDAO productDao;
	@Resource
	private ProductCategoryDAO categoryDao;
	@Resource
	private ProductStoreLinkDAO productStoreLinkDao;

	/**
	 * 商品信息首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/product/list");
		return "jsp/main";
	}

	/**
	 * 商品信息搜索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/product/list")
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
		Long total=productDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<Product> products=productDao.findAll(map);
		webPage.setResult(products);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 商品信息删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("/product/delete")
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
				productDao.delete(ids_);
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
	 * 商品信息新增页面/商品信息修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/product/edit")
	public String edit(HttpServletRequest request){
		String id=request.getParameter("id");
		request.setAttribute("categorys", categoryDao.findAll(null));
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Product d=productDao.findById(Integer.parseInt(id));
			if(d!=null){
				request.setAttribute("id", id);
				request.setAttribute("product", d);
			}
		}
		request.setAttribute("page","/Sreticeference/product/edit");
		return "jsp/main";
	}

	/**
	 * 商品信息新增
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/product/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String id=request.getParameter("id");
		String categoryId=request.getParameter("categoryId");
		String model=request.getParameter("model");
		String unit=request.getParameter("unit");
		String inPrice=request.getParameter("inPrice");
		String outPrice=request.getParameter("outPrice");
		String stocks=request.getParameter("stocks");
		String minstocks=request.getParameter("minstocks");
		String maxstocks=request.getParameter("maxstocks");
		JSONObject result=new JSONObject();
		try {
			Product p=new Product();
			p.setCategoryid(Integer.parseInt(categoryId));
			p.setInprice(new BigDecimal(inPrice));
			p.setMaxstocks(Integer.parseInt(maxstocks));
			p.setMinstocks(Integer.parseInt(minstocks));
			p.setModel(model);
			p.setName(name);
			p.setOutprice(new BigDecimal(outPrice));
			p.setStocks(Integer.parseInt(stocks));
			p.setUnit(unit);
			if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
				p.setProductid(Integer.parseInt(id));
				productDao.update(p);
			}else{
				productDao.save(p);
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "数据错误");
			e.printStackTrace();
		}
		WebUtils.outputWebPage(result, response);
	}

	/**
	 * 查询商品库存
	 * @param request
	 * @return
	 */
	@RequestMapping("/product/store")
	public String store(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			request.setAttribute("product", productDao.findById(Integer.parseInt(id)));
			List<ProductStoreLink> pss=productStoreLinkDao.findByProductId(Integer.parseInt(id));
			request.setAttribute("pss", pss);
		}
		request.setAttribute("page","/Sreticeference/product/stores");
		return "jsp/main";
	}
	
	@RequestMapping("/product/select")
	public void select(HttpServletRequest request,HttpServletResponse response){
		String keyword=request.getParameter("keyword");
		Map<String,Object> map=new HashMap<String,Object>();
		JSONArray ja=new JSONArray();
		if(StringUtils.isNotBlank(keyword)){
			map.put("name","%"+keyword+"%");
			map.put("start",0);
			map.put("size", 10);
			List<Product> products=productDao.findAll(map);
			for(Product p:products){
				JSONObject jo=new JSONObject();
				jo.put("id", p.getProductid());
				jo.put("text", p.getName());
				ja.add(jo);
			}
		}
		try {
			WebUtils.outputWebPage(ja, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/product/getByAjax")
	public void getByAjax(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(productDao.findById(Integer.parseInt(id))), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
