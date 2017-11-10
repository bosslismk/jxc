package com.inventory.controller;

import com.inventory.dao.DepartmentDAO;
import com.inventory.dao.EmployeeDAO;
import com.inventory.entity.Department;
import com.inventory.entity.Employee;
import com.inventory.util.WebPage;
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

/**
 * 员工
 */
@Controller
public class EmployeeController {
	@Resource
	private EmployeeDAO employeeDao;
	@Resource
	protected DepartmentDAO departmentDao;

	/**
	 * 员工显示首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/employee/listPage")
	public String listPage(Model model){
		model.addAttribute("page","/Sreticeference/employee/list");
		return "jsp/main";
	}

	/**
	 * 员工搜索
	 * @param request
	 * @param response
	 */
	@RequestMapping("/employee/list")
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
		Long total=employeeDao.countAll(map);
		WebPage webPage=null;
		try {
			webPage=new WebPage(Integer.parseInt(page), total.intValue());
		} catch (Exception e) {
			webPage=new WebPage(1, total.intValue());
		}
		map.put("start", webPage.getStart());
		map.put("size", webPage.getSize());
		List<Employee> employees=employeeDao.findAll(map);
		webPage.setResult(employees);
		webPage.setParams(params.toString());
		webPage.setSuccess(true);
		try {
			WebUtils.outputWebPage(JSONObject.fromObject(webPage), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 员工删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("/employee/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String idsStr=request.getParameter("ids");
		JSONObject result=new JSONObject();
		try {
			String[] ids=idsStr.split(",");
			if(ids.length>0){
				employeeDao.delete(ids);
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
	 * 员工新增页面/员工编辑页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/employee/edit")
	public String edit(HttpServletRequest request){
		String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Employee d=employeeDao.findById(id);
			if(d!=null){
				request.setAttribute("id", id);
				request.setAttribute("employee", d);
			}
		}
		request.setAttribute("departments", departmentDao.findAll(null));
		request.setAttribute("page","/Sreticeference/employee/edit");
		return "jsp/main";
	}

	/**
	 * 员工新增
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/employee/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("id");
		String empNo=request.getParameter("empNo");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String sex=request.getParameter("sex");
		String tel=request.getParameter("tel");
		String role=request.getParameter("role");
		String departmentId=request.getParameter("departmentId");
		JSONObject result=new JSONObject();
		try {
			Employee emp=new Employee();
			Department d=new Department();
			d.setDepartmentId(Integer.parseInt(departmentId));
			emp.setEmpNo(empNo);
			emp.setDepartment(d);
			emp.setName(name);
			emp.setPassword(password);
			emp.setRole(Integer.parseInt(role));
			emp.setSex(sex);
			emp.setTel(tel);
			if(StringUtils.isNotBlank(id)){
				if(StringUtils.equals(id, empNo)){
					employeeDao.update(emp);
					result.put("success", true);
				}else{
					result.put("success", false);
					result.put("message", "数据错误");
				}
			}else{
				employeeDao.save(emp);
				result.put("success", true);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "数据错误");
			e.printStackTrace();
		}
		WebUtils.outputWebPage(result, response);
	}

	/**
	 * 生成工号
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/employee/generateEmpNo")
	public void generateEmpNo(HttpServletResponse response) throws IOException{
		String empNo="生成失败,稍后再试";
		try {
			empNo=generateEmpNo(empNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		WebUtils.outputWebPage(empNo, response);
	}
	
	private String generateEmpNo(String empNo){
		Employee e=employeeDao.findLastOne();
		empNo = e.getEmpNo();
		empNo=empNo.substring(1);
		empNo="E"+(Integer.parseInt(empNo)+1);
		if(employeeDao.findById(empNo)!=null){
			return generateEmpNo(empNo);
		}else{
			return empNo;
		}
		
		
	}
}
