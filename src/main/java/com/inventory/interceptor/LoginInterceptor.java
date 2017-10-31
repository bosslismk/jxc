package com.inventory.interceptor;

import com.inventory.util.WebUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mv)
			throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		if(WebUtils.getCurrentEmployee(request.getSession())!=null||request.getRequestURI().contains("login")){
			return true;
		}else{
			response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/login.htm");
			return false;
		}
	}
	
}
