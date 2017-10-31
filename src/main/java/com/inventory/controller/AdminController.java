package com.inventory.controller;

import com.inventory.dao.EmployeeDAO;
import com.inventory.entity.Employee;
import com.inventory.util.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminController {
	@Resource
	private EmployeeDAO empDao;

	@RequestMapping(value="/login",method={RequestMethod.GET})
	public String login(){
		return "jsp/login";
	}
	@RequestMapping(value="/logout",method={RequestMethod.GET})
	public String logout(HttpServletRequest request){
		WebUtils.invalidateCurrentSession(request.getSession());
		return "redirect:/login.htm";
	}
	@RequestMapping(value="/login",method={RequestMethod.POST})
	public String login(String empNo,String password,HttpServletRequest request){
		if(StringUtils.isBlank(empNo)||StringUtils.isBlank(password)){
			request.setAttribute("errorInfo", "用户名密码不能为空");
			return "jsp/login";
		}
		Employee emp=new Employee();
		emp.setEmpNo(empNo);
		emp.setPassword(password);
		emp=empDao.login(emp);
		if(emp==null){
			request.setAttribute("errorInfo", "用户名密码错误");
			return "jsp/login";
		}
		WebUtils.setCurrentEmployee(emp, request.getSession());
		return "redirect:/index.htm";
	}
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("page","/Sreticeference/index");
		return "jsp/main";
	}
}
