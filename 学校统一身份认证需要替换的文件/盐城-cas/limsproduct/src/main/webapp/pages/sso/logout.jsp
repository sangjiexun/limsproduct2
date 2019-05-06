<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="supwisdom/CASUtils.jsp"%>
<%!public boolean doLogout(HttpServletRequest request) {
    	request.getSession().invalidate();
		return true;
	}%>
<%
	if (doLogout(request)) {
		CasUtils.logout(request.getSession());
		response.sendRedirect(CasUtils.getLogoutUrl(request));
	} else {
		response.sendRedirect(CasUtils.getLoginUrl(request));
	}
%>
