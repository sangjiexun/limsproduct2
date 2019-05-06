<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<meta name="decorator" content="iframe"/>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">



 <script type="text/javascript">
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/appointment/listLabAnnex?page=1";
	}
 </script>
 
 
 <!--批量撤回的功能-->
 <script type="text/javascript">
    var scrapAppIds = "";
    function batchWithdraw(){
    	clearCookie();
		var array=sss();
		for(var i=0;i<array.length;i++){
			addToScrap(array[i]);
		}
				
		var values=getScrapCookie();
		for(var ele in values){
			if(values[ele]!=''){
				var eid=$.trim($("#Id"+values[ele]).siblings().eq(1).text());
				scrapAppIds+=$.trim(eid)+",";
			}
		}
		alert(scrapAppIds);
		//$("input[name='scrapAppIds']").val(scrapAppIds);
		//alert($("input[name='scrapAppIds']").val());
		clearCookie();
		window.location.href="${pageContext.request.contextPath}/deleteSchoolClassesStudent?scrapAppIds="+scrapAppIds;
    }
    
 	function sss(){
        var array=new Array();
           $("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                        if ($(this).attr("checked")) { //判断是否选中
                            array.push($(this).val()); //将选中的值 添加到 array中 
                        }  
                    })    
          return array;
    	}
    	
    	function clearCookie(){
		var keys=document.cookie.match(/[^ =;]+(?=\=)/g); 
		if (keys) { 
		for (var i = keys.length; i--;) 
		document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString() 
		} 
	};
	
	//把要删除的学生id存入cookie
	function addToScrap(id){
		var strCookie=document.cookie;
		//将多cookie切割为多个名/值对 
		var arrCookie=strCookie.split("; "); 
		var scrapList; 
		//遍历cookie数组，处理每个cookie对 
		for(var i=0;i<arrCookie.length;i++){ 
			var arr=arrCookie[i].split("="); 
			//找到名称为userId的cookie，并返回它的值 
			if("scrapList"==arr[0]){ 
				scrapList=arr[1]; 
				break; 
			} 
		} 
		console.info(scrapList);
		if(scrapList==null){scrapList="";}
		document.cookie="scrapList="+scrapList+","+id; 
		//$("#Id"+id).html("已添加！<button onclick=\"delScrap('"+id+"');\">取消</button>");
	}
	
	//得到cookie里面的删除的学生列表 数组
	function getScrapCookie(){
		var values=new Array();
		var strCookie=document.cookie; 
		if(strCookie==null||strCookie==''){return false;}
		//将多cookie切割为多个名/值对 
		var arrCookie=strCookie.split("; "); 
		var scrapList; 
		//遍历cookie数组，处理每个cookie对 
		for(var i=0;i<arrCookie.length;i++){ 
			
			var arr=arrCookie[i].split("="); 
			//找到名称为userId的cookie，并返回它的值 
			if("scrapList"==arr[0]){ 
				scrapList=arr[1]; 
				break; 
			} 
		} 
		values=scrapList.split(",");
		return values;
	}
	
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/system/listSchoolClasses?currpage=1";
	}
 </script>
 
</head>
<body>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
			<%-- <div class="title">
				<div id="title">班级列表</div>
				<div>
				<a class="btn btn-new"  href= '${pageContext.request.contextPath}/newSchoolClasses'>新建</a>
				</div>
				
			<!-- 	<a class="btn btn-new" onclick="window.history.go(-1)">返回</a> -->
			</div>   	 --%>
			<%-- <div class="tool-box">
				<form:form name="queryForm" action="${pageContext.request.contextPath}/findAllSchoolAcademy?page=1" method="post" modelAttribute="schoolAcademy">
					 <ul>
    				<li>学院名称： </li>
    				<li><form:input id="buildName" path="academyName"/></li>
    				
    				<li><input type="submit" value="查询"/></li>
    				</ul>
    					<a class="btn btn-new"  href= '${pageContext.request.contextPath}/newSchoolAcademy'>新建</a>
				</form:form>
		       </div> --%>
    		<!-- 实验室列表 -->
    		<div class="content-box">   
    		 <div class="title">
						<div id="title">所有学生</div>	
							<a class="btn btn-new"   onclick="batchWithdraw();"><span>批量删除</span></a>
							<a class="btn btn-new"   href="${pageContext.request.contextPath}/addSchoolClassesStudent?currpage=1&classNumber=${classNumber}"><span>批量添加</span></a>
					     	<a class="btn btn-new"   onclick="cancel();"><span>返回</span></a>
						</div>
            <table> 
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>学号</th>
					    <th>姓名</th>
					    <th>学院</th>
					    <th>专业</th>
				        <th>班级</th>
					     <th>批量删除</th>
					     </tr>
                </thead>
                <tbody>
                	<c:forEach items="${user}" var="current"  varStatus="i">
                           <tr>
                           <td>${ i.count}</td>
                           <td>${current[0]}</td>
						    <td>${current[1]}</td>
						    <td>${current[2]}</td>
						    <td>${current[3]}</td>
					        <td>${current[4]}</td>
						    <td id="Id${current[0]}">
						    <input type="checkbox" name="CK" value="${current[0]}">
						    </td> 
                           </tr>
                    </c:forEach> 
                </tbody>
            </table>

        <%--  <div class="pagination" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href=" ${pageContext.request.contextPath}/system/listSchoolClasses?currpage=1"    target ="_self"> 首页</a >                           
           <a href=" ${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${pageModel.previousPage}" target ="_self"> 上一页</a >
          第 <select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
           <option value=" ${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${page}">${page} </option>
           <c:forEach begin= "${pageModel.firstPage}" end="${pageModel.lastPage}" step= "1" varStatus ="j" var="current">           
    <c:if test="${j.index!=page}">
    <option value=" ${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${j.index} ">${j.index} </option>
    </c:if >
    </c:forEach ></select> 页
           <a href=" ${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${pageModel. nextPage} "   target ="_self"> 下一页</a >
           <a href=" ${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${pageModel.totalPage}"   target ="_self"> 末页</a >
    </div > --%>

    
</div>
</div>
</div>
</div>
</div>
</body>
</html>