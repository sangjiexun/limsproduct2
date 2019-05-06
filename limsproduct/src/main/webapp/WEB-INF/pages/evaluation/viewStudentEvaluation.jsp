<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<html>
<head>
	<meta name="decorator" content="iframe"/>
</head>
<body>
  	<div class="right-content">
  		<div id="TabbedPanels1" class="TabbedPanels">
  			<div class="TabbedPanelsContentGroup">
  				<div class="TabbedPanelsContent">
  					<div class="content-box">
				  		<div class="new-classroom">
				  			<div>
				  				<label>课程名称：</label>
				  				<label>${evaluationSource.schoolCourseInfo.courseName }</label>
				  				<label>上课教师：</label>
				  				<label>${evaluationSource.user.cname }</label>
				  			</div>
				  			<div class="content-box">
				  				<table>
				  					<thead>
				  						<tr>
				  							<th>序号</th>
				  							<th>评教内容</th>
				  							<th>单项评分</th>
				  						</tr>
				  					</thead>
				  					<tbody>
				  						<c:forEach items="${evaluationResults}" var="curr" varStatus="i">
				  							<tr>
				  								<td>${i.count}</td>
				  								<td>${curr.evaluationContent.options}</td>
				  								<td>${curr.score}</td>
				  							</tr>
				  						</c:forEach>
				  					</tbody>
				  				</table>
				  			</div>
				  		</div>
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
</body>
</html>