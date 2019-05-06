<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'NewChoseTheme.jsp' starting page</title>
    
	<meta name="decorator" content="iframe"/>
	<!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  </head>
  
  <body>
  	 <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li><a href="javascript:void(0)">导师互选列表</a></li>
		<li class="end"><a href="javascript:void(0)">新建导师互选</a></li>
	  </ul>
	</div>
  </div>
  <form:form action="${pageContext.request.contextPath }/savaChoseTheme" method="post" onsubmit="return check()" modelAttribute="choseTheme">
  	<table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter">
	   <tr align="center">
			<td class="label" valign="top">导师可带最大学生数量:</td>
			<td class="label" valign="top" ><form:input path="maxStudent" required="true"/> </td>
		 </tr>
		 <tr align="center">
			<td class="label" valign="top">开始时间:</td>
			<td class="label" valign="top" ><form:input id="startTime"   path="startTime" type="date"/></td>
		 </tr>
		 <tr align="center">
			<td class="label" valign="top">结束时间：</td>
			<td class="label" valign="top" ><form:input path="endTime" type="date" id="endTime"/></td>
		 </tr>
		  <tr align="center">
			<td class="label" valign="top">提前通知天数：</td>
			<td class="label" valign="top" ><form:input path="advanceDay" required="true"/></td>
		 </tr>
		  <tr align="center">
			<td class="label" valign="top">期望截止时间:</td>
			<td class="label" valign="top" ><form:input path="expectDeadline" type="date"/></td>
		 </tr>
		 <tr align="center">
			<td class="label" valign="top" colspan="2"><input type="submit" value="确定"/></td>
		 </tr>
		 </table>
  </form:form>
    <script type="text/javascript">
    	function check(){
    		 var startTime=document.getElementById("startTime").value;
    		alert(startTime);
    		 var endTime=document.getElementById("endTime").value;
    		 if(startTime.length>0&&endTime.length>0){
    		 	var startTmp=startTime.split("-");
    		 	var endTmp=endTime.split("-");
    		 	var sd=new Date(startTmp[0],startTmp[1],startTmp[2]); 
    			var ed=new Date(endTmp[0],endTmp[1],endTmp[2]);
    			if(sd.getTime()>ed.getTime()){
    			alert("开始时间不能大于结束时间");
    			return false;
    		 	}
    		 }
    		return true;
    	}
	</script>	   
  </body>
</html>
