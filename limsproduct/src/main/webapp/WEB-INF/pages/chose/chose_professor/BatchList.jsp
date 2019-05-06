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
	   <a href="${pageContext.request.contextPath }/nwuChose/ChoseThemeList?currpage=1"  class="btn btn-return">返回</a>
	  </div>
	   <div class="tool-box">
	   </div>
	       <c:forEach items="${sessionScope.batchInfo}" var="curr" varStatus="i">
	       		<fieldset> 
			    <label>第${curr[2]}志愿导师:</label>
			   	${curr[4] }:${curr[5]}
			  </fieldset> 
	       </c:forEach>
	       <a href="${pageContext.request.contextPath}/nwuChose/saveBatch" class="btn">确定</a>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	     $(document).ready(function(){
	    	var s=$("input[name='ss']");
	    	var ids=$("input[name='ids']");
	    	var tds=$("td[name='start']");
	    	var currTime=new Date().getTime();
	    	var startTime;
	        for(var i=0;i<s.length;i++){
	        	startTime=new Date(s.eq(i).val()).getTime();
	        	if(currTime-startTime>0){
	        		var t="<a href='${pageContext.request.contextPath}/toAddBatch?i=1&id="+ids.eq(i).val()+"'>填写志愿</a>";
	        		tds.eq(i).html(t);
	        	}
	        }
	    }); 
	</script>
	<!-- 下拉框的js -->
</body>
</html>
