package com.inventory.util;

import com.inventory.entity.Employee;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
public class WebUtils {
	//当前用户
	public static final String SESSION_EMPLOYEE="SESSION_EMPLOYEE";
	/**
	 * 设置当前员工
	 * @param admin
	 * @param session
	 */
	public static void setCurrentEmployee(Employee emp, HttpSession session){
		session.setAttribute(SESSION_EMPLOYEE, emp);
	}
	/**
	 * 获取当前管理员信息
	 * @param session
	 * @return
	 */
	public static Employee getCurrentEmployee(HttpSession session){
		return (Employee) session.getAttribute(SESSION_EMPLOYEE);
	}
	public static void invalidateCurrentSession(HttpSession session){
		session.invalidate();
	}
	public static void outputWebPage(Object object,HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
	    response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		pw.print(object);
		pw.flush();
		pw.close();
	}
}
