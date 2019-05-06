<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="supwisdom/CASUtils.jsp"%>
<%!public boolean doLogin(LoginUser loginUser, HttpServletRequest request) {
		HttpSession session = request.getSession();
		//需要的
		session.setAttribute("username", loginUser.getAccount());
		return true;
	}%>
<%
	String targetUrl = CasUtils.getTargetUrl(request);
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	if (CasUtils.isLogin(session)) {
		response.sendRedirect(basePath);
//		response.sendRedirect(targetUrl);
	} else {
		if (CasUtils.hasTicket(request)) {
 			LoginUser loginUser = CasUtils.getLoginUser(request);
			if (loginUser.isLogin() && doLogin(loginUser, request)) {
				CasUtils.login(loginUser, session);
				response.sendRedirect(basePath);
//				response.sendRedirect(targetUrl);
			} else {
				response.sendRedirect(CasUtils.getLogoutUrl(request));
			}
		} else {
			String loginUrl = CasUtils.getLoginUrl(request);
			response.sendRedirect(loginUrl);
		}
	}
%>