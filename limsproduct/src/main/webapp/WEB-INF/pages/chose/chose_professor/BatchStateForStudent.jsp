<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
	  $(document).ready(function(){
		});
	  //取消查询
	  function cancel()
	  {
	    window.location.href="${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=1";
	  }
	  //跳转
	  function targetUrl(url)
	  {
	    document.queryForm.action=url;
	    document.queryForm.submit();
	  }
  </script>	
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">导师互选列表</a></li>
	  </ul>
	</div>
  </div>
  <div id="TabbedPanels1" class="TabbedPanels">
   <div class="TabbedPanelsContentGroup">
    <div class="TabbedPanelsContent">
     <div class="content-box">
      <div class="title">
	   <div id="title">导师互选列表</div>
	   <a href="${pageContext.request.contextPath }/nwuChose/BelongChoseThemeList?currpage=1"  class="btn btn-return">返回</a>
	  </div>
	   <div class="tool-box">
	   </div>
	       <c:forEach items="${list}" var="curr" varStatus="i">
	       		<fieldset> 
			    <label>第${curr[0]}志愿:</label>
			    <c:if test="${ curr[1]==0}">
			  	未开始
			  </c:if>
			  <c:if test="${ curr[1]==1}">
			  	正在进行
			  </c:if>
			  <c:if test="${ curr[1]==2}">
			  	已结束
			  </c:if>
			  </fieldset> 
	       </c:forEach>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框的js -->
</body>
</html>