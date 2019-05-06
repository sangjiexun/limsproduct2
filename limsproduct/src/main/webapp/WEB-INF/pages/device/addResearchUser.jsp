<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
<meta name="decorator" content="iframe"/>
<title>无标题文档</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<%--<script src="${pageContext.request.contextPath}/js/SpryTabbedPanels.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/css/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
--%><script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
//定义全局变量
var result=1;//审核结果,默认是1--通过
$(document).ready(function(){
	
	if(${refresh==1}){
		parent.location.href="${pageContext.request.contextPath}/device/passReservationList?page=1";
	}
	
	if(${refresh==2}){
		parent.location.href="${pageContext.request.contextPath}/device/rejectedReservationList?page=1";
	}

});
//跳转
function targetUrl(url)
{  
	location.href = url;
}  


function addUser(){
    var array=new Array();
    var flag; //判断是否一个未选   
    $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为CK_name的 checkbox  
                if ( this.checked  == true) { //判断是否选中    
                    flag = true; //只要有一个被选择 设置为 true  
                }  
            })  

    if (flag) {  
       $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                    if (this.checked==true) { //判断是否选中
                        array.push($(this).val()); //将选中的值 添加到 array中 
                    }  
                });
       $.ajax({
           url:"${pageContext.request.contextPath}/device/saveResearchUser",
           dataType:"json",
           type:'GET',
           data:{researchId:${id},array:array.join(",")},
           complete:function(result)
           {
        	   parent.location.href="${pageContext.request.contextPath}/device/listAllResearchProjects?page=1";
            }
  });
       //将要所有要添加的数据传给后台处理   
	   //window.location.href="${pageContext.request.contextPath}/labRoom/saveLabRoomAdmin?researchId="+${id}+"&array="+array; 
    } else {   
    	alert("请至少选择一条记录");  
    	}  
	}

</script>
</head>
<body>

<div class="iStyle_Conteiner">
<div class="iStyle_RightInner">

		<!-- 添加设备页面 结束-->
			<!-- 添加管理员 -->
	<div id="addResearchUser">
		<div class="content-box">
		<form:form id="userForm" method="post"   modelAttribute="user">
			<table class="tb" id="my_show">
				<tr>
					<td>姓名：<form:input id="cname" path="cname"/></td>
					<td>工号：<form:input id="username" path="username"/>
						<input type="submit" value="搜索" >
					</td>
					<td>
						<input type="hidden" id="adminType">
						<input type="button" value="添加" onclick="addUser();">
					</td>
				</tr>
			</table>
		</form:form>
		
		<table id="my_show">
					<thead>
						<tr>
							<th style="width:10% !important">选择</th>
							<th style="width:30% !important">姓名</th>
							<th style="width:30% !important">工号</th>
							<th style="width:30% !important">所属学院</th>
							
						</tr>
					</thead>
						
					<tbody id="user_body">
					<c:forEach items="${users }" var="curr" varStatus="i">
					<tr>
						<td><input type='checkbox' name='CK_name'  value='${curr.username}'/></td>
						<td>${curr.cname }</td>
						<td>${curr.username }</td>
						<td>${curr.schoolAcademy.academyName }</td>
					</tr>	
					</c:forEach> 
					</tbody>
					
			</table>
			</div>
			<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/addResearchUser?currpage=1&id=${id }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/addResearchUser?currpage=${pageModel.previousPage}&id=${id }')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/addResearchUser?currpage=${pageModel.currpage}&id=${id }">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/device/addResearchUser?currpage=${j.index}&id=${id } ">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/addResearchUser?currpage=${pageModel.nextPage}&id=${id }')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/addResearchUser?currpage=${pageModel.lastPage}&id=${id }')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
	</div>

</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Downlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Allpages.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Interface.js"></script>
</body>
</html>
