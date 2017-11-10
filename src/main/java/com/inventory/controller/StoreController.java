package com.inventory.controller;

import com.inventory.dao.EmployeeDAO;
import com.inventory.dao.StoreDAO;
import com.inventory.entity.Store;
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

/**
 * 仓库
 */
@Controller
public class StoreController {
	@Resource
	private StoreDAO storeDao;
	@Resource
	private EmployeeDAO empDao;


	/**
	 * 仓库首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/store/list");
		return "jsp/main";
	}

	/**
	 * 仓库搜索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/store/list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		String page=request.getParameter("page");
		String keyword=request.getParameter("keyword");
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuilder params=new StringBuilder();
		if(StringUtils.isNotBlank(keyword)){
			map.put("searchWord","%"+keyword+"%");
			params.append("keyword="+keyword);
			request.setAttribute("keyword", keyword);
		}
		Long total=storeDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<Store> stores=storeDao.findAll(map);
		webPage.setResult(stores);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 仓库删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("/store/delete")
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
				storeDao.delete(ids_);
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
	 * 仓库新增页面/仓库编辑页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/store/edit")
	public String edit(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
			Store d=storeDao.findById(Integer.parseInt(id));
			if(d!=null){
				request.setAttribute("id", id);
				request.setAttribute("store", d);
			}
		}
		request.setAttribute("page","/Sreticeference/store/edit");
		return "jsp/main";
	}

	/**
	 * 仓库新增
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/store/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String address=request.getParameter("address");
		String contactman=request.getParameter("contactman");
		String email=request.getParameter("email");
		String tel=request.getParameter("tel");
		String id=request.getParameter("id");
		JSONObject result=new JSONObject();
		try {
			if(StringUtils.isNotBlank(contactman)&&StringUtils.startsWith(contactman, "E")&&empDao.findById(contactman)!=null){
				Store p=new Store();
				p.setName(name);
				p.setAddress(address);
				p.setContactman(contactman);
				p.setEmail(email);
				p.setTel(tel);
				if(StringUtils.isNotBlank(id)&&StringUtils.isNumeric(id)){
					p.setStoreId(Integer.parseInt(id));
					storeDao.update(p);
				}else{
					storeDao.save(p);
				}
				result.put("success", true);
			}else{
				result.put("success", false);
				result.put("message", "联系人错误");
			}
			
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "数据错误");
			e.printStackTrace();
		}
		WebUtils.outputWebPage(result, response);
	}
}
