<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
  	<script type="text/javascript">
		function targetUrl(url)
		{
    		document.queryForm.action=url;
    		document.queryForm.submit();
  		}
  		//取消
  		function cancle()
		{
    		window.location.href="${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=1";
  		}
  		//新建
  		function addOperationItem(){
  			window.location.href="${pageContext.request.contextPath}/operation/newOperationItem?isMine=1&flagId=0";
  		}
  	</script>
</head>
  
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">教学课程</a>
				</li>
				<li class="end"><a href="javascript:void(0)">课程列表</a>
				</li>
			</ul>
		</div>
	</div>

	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
							<div id="title">项目列表</div>
						</div>

						<div class="tool-box">
							<form:form name="queryForm"
								action="${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=1"
								method="post" modelAttribute="operationItem">
								<ul>
									<li>项目名称:<form:input id="lpName" path="lpName" /></li>
									<li>
										<input type="submit" value="查询" />
										<input class="cancel-submit" type="button" value="取消" onclick="cancle()" />
										<input type="button" value="新建" onclick="addOperationItem()" />
									</li>
								</ul>

							</form:form>

						</div>

						<table class="tb" id="my_show">
							<thead>
								<tr>
									<th>序号</th>
									<th>项目名称</th>
									<th>课程名称</th>
									<th>实验学时</th>
									<th>所属实验室</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${operationItems}" var="curr" varStatus="i">
									<tr>
										<td>${i.count}</td>
										<td>${curr.lpName}</td>
										<td>${curr.schoolCourseInfo.courseName}</td>
										<td>${curr.lpDepartmentHours}</td>
										<td>
											<c:choose>
												<c:when test="${curr.labRoom ne null}">${curr.labRoom.labRoomName}</c:when>
												<c:otherwise>
													<c:if test="${curr.labRooms ne null}">
														<c:forEach items="${curr.labRooms}" var="lab">${lab.labRoomName}
															<br></c:forEach>
													</c:if>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页[s] -->
						<div class="page" >
					        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
					    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/tcoursesite/operationItemList?tCourseSiteId=${tCourseSiteId}&currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
					    <c:if test="${j.index!=pageModel.currpage}">
					    <option value="${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=${j.index}">${j.index}</option>
					    </c:if>
					    </c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=${pageModel.nextPage}')" target="_self">下一页</a>
					 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/teaching/coursesite/operationItemList?courseNumber=${courseNumber}&currpage=${pageModel.lastPage}')" target="_self">末页</a>
					    </div>
					    <!-- 分页[e] -->
					</div>
  				</div>
  			</div>
  		</div>
  	</div>
</body>
</html>
