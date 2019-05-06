<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >  
<head>
 <meta name="decorator" content="iframe"/>
<title><fmt:message key="html.title"/></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>
<body style="overflow:hidden">



<!-- 结项申报列表 -->
<!-- <div class="tab"> -->
<div id="content" >
<div id="tablewrapper" class="content-box">

<div class="list_top">
		<ul class="top_tittle">
			<li></li>
		</ul>
		<ul id="list-nav" >
			
		</ul>
		<ul class="new_bulid">
			
		</ul>
 </div>


	
<div class="content-box">

		<fieldset class="introduce-box">
								<legend>基本信息</legend>

 <table id="listTable"   >
 <tr align="center" >
 
     <th  >预约性质</th>
     <td  >${infor.nametype}</td> 
     </select></td>      
      <th  >活动名称</th>
     <td >${infor.name }</td>  
    </tr>  
 <tr> 
	 <th >周次</th>
     <td ><c:forEach items="${infor.week }"  var="a" >
 			<c:if test="${a=='563' }">第一周，</c:if>   
 			<c:if test="${a=='564' }">第二周，</c:if>
 			<c:if test="${a=='565' }">第三周，</c:if>
 			<c:if test="${a=='566' }">第四周，</c:if>
 			<c:if test="${a=='567' }">第五周，</c:if>
 			<c:if test="${a=='568' }">第六周，</c:if>
 			<c:if test="${a=='569' }">第七周，</c:if>
 			<c:if test="${a=='570' }">第八周，</c:if>
 			<c:if test="${a=='571' }">第九周，</c:if>
 			<c:if test="${a=='572' }">第十周，</c:if>
 			<c:if test="${a=='573' }">第十一周，</c:if>
 			<c:if test="${a=='574' }">第十二周，</c:if>
 			<c:if test="${a=='575' }">第十三周，</c:if>
 			<c:if test="${a=='576' }">第十四周，</c:if>
 			<c:if test="${a=='577' }">第十五周，</c:if>
 			<c:if test="${a=='578' }">第十六周，</c:if>
 			<c:if test="${a=='579' }">第十七周，</c:if>
 			<c:if test="${a=='580' }">第十八周，</c:if>
 			<c:if test="${a=='581' }">第十九周，</c:if>
 			<c:if test="${a=='582' }">第二十周，</c:if>
		  </c:forEach> </td> 
     </td> 	
	 <th >星期</th>
     <td ><c:forEach items="${infor.day}"  var="d" >
     		<c:if test="${d=='1'}">星期一，</c:if>
     		<c:if test="${d=='2' }">星期二，</c:if>
     		<c:if test="${d=='3' }">星期三，</c:if>
     		<c:if test="${d=='4' }">星期四，</c:if>
     		<c:if test="${d=='5' }">星期五，</c:if>
     		<c:if test="${d=='6' }">星期六，</c:if>
     		<c:if test="${d=='7' }">星期日，</c:if>
     	  </c:forEach></td> 
    </tr>
      <tr>      
      <th >节次</th>
     <td ><c:forEach items="${infor.time}"  var="f" >${f },</c:forEach></td> 
     </td> 	
	 <th >预约<spring:message code="all.trainingRoom.labroom"/></th>
     <td >${infor.lab}</td>      
      </tr>
    
      <tr> 
	 <th >内容</th>
     <td >${infor.start}</td> 
	 <td ></td>
     <td ></td>   
     </tr>
 </table>
 </fieldset>
 
 </div>
 
</div><!-- </div> -->

</div>
</body>
<!-------------列表结束----------->
</html>