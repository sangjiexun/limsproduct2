<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
  <head>
  <meta name="decorator" content="iframe" />
  </head>
  <body>
	  <div class="main_container cf rel w95p ma">
		  <div id="TabbedPanels1" class="TabbedPanels">
			  <div class="TabbedPanelsContentGroup">
				  <div class="TabbedPanelsContent">
					  <div class="content-box">
						<table class="tb" id="my_show">
						  <thead>
						  <tr>
						    <th>序号</th>
						    <th>实验编号</th>
						    <th>实验名称</th>
						    <th>学期</th>
						    <th>创建者</th>
						  </tr>
						  </thead>
						  <tbody>
						  <c:forEach items="${operationItems}" var="curr" varStatus="i">
							  <tr>
							    <td>${i.count}</td>
							    <td>${curr.lpCodeCustom}</td>
							    <td>${curr.lpName}</td>
							    <td>${curr.schoolTerm.termName}</td>
							    <td>${curr.userByLpCreateUser.cname}[${curr.userByLpCreateUser.username}]</td>
							  </tr>
						  </c:forEach>
						  </tbody>
						</table>
					  </div>
				  </div>
			  </div>
		  </div>
	</div>

</body>
</html>