<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<head>
<meta name="decorator" content="iframe" />
</head>
<body>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup1">
			<div class="TabbedPanelsContent">
				<div class="tool-box">
					<ul>
							<li>实验室日考勤 </li>
					</ul>
				</div>
			</div>
			<div class="content-box">
				<table>
					<thead>
						<tr>
							<th>课程名称</th>
							<th>起始节次</th>
							<th>应到人数</th>
							<th>实到人数</th>
							<th>缺勤人数</th>
							
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${list}" var="curr" varStatus="i">
                    <tr>
                        <td>${curr[4]}</td>
						<td>${curr[8]}-${curr[9]}</td>
                        <td>${curr[5]}</td>
                        <td>${curr[6]}</td>
                        <td>${curr[7]}</td>
                    </tr>
                </c:forEach>
					</tbody>
				</table>
				

			</div>
</div>		
	</div></div>