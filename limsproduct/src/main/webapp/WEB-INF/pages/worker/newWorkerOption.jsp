<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<head>
<meta name="decorator" content="iframe"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript"> 
$(function(){
$(".datebox :text").attr("readonly","readonly"); 
  $('#practice_name').blur(function(){
            var name = $('#practice_name').val();
            $.trim(name);
        });
});
</script>
</head>
<!-- 导航栏 -->
<div class="navigation">
<div id="navigation">
<ul>
<li><a href="">开放预约</a></li>
<li><a href="${pageContext.request.contextPath}/worker/listWorkerOption?currpage=1">可预约工位数设置</a></li>
<c:if test="${flag==0}">
    <li class="end"><a href="${pageContext.request.contextPath}/worker/newWorkerOption">新建可预约工位数</a></li>
</c:if>
<c:if test="${flag==1}">
    <li class="end"><a href="${pageContext.request.contextPath}/worker/newWorkerOption">修改可预约工位数</a></li>
</c:if>
</ul>
</div>
</div>
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
			    <c:if test="${flag==0}">
				    <div class="title">新建可预约工位数</div>
				</c:if> 
				<c:if test="${flag==1}">
				    <div class="title">修改可预约工位数</div>
				</c:if>  
                <form:form id="myForm" action="${pageContext.request.contextPath}/worker/saveWorkerOption" name="myForm" method="POST" modelAttribute="workerOption">
                <table>
                <form:hidden path="id" value="${id}"/>
                   <tr>
                       <td>教师名称</td>
                       <td>
			           <form:select path="user.username" id="user.username">
	                       <form:option value =""></form:option>
	                       <c:forEach items="${teachers}" var="t">
								<form:option value="${t.username}">${t.cname}</form:option>
							</c:forEach>
	                   </form:select>
	                   </td>
			       </tr>
			       <tr>
			           <td>可预约工位数</td>
			           <td>
			           <form:input id="worker" path="worker"/>
			           </td>
			       </tr>
			  </table>
			  <div class="moudle_footer">
			  <div class="submit_link">
			      <input class="btn btn-common" type="submit" value="保存" >
			      <input class="btn btn-common" type="button" value="返回" onclick="window.history.go(-1)">
			  </div>
			  </div>
              </form:form>
           </div>
      </div>
   </div>
</div>