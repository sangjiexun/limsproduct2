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
  $(function(){
     $("#print").click(function(){
	$("#my_show").jqprint();
	});
	$("#lab_name").focus();
	$("#lab_name").bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                document.form.action="${pageContext.request.contextPath}/selectLabList";
	            document.form.submit();
            }
        });
  });
  
 
    //首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	} 
function cancel(){
	window.location.href="${pageContext.request.contextPath}/labRoom/appointment/findLabRoomByLabAnnexId?id="+${id}+"&page=1";
}	
 </script> 
</head>


<body>

<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">实验室及预约管理</a></li>
			<li class="end"><a href="javascript:void(0)">实验室管理</a></li>
			<li class="end"><a href="javascript:void(0)">实验分室管理</a></li>
		</ul>
	</div>
</div>

<!-- 查询表单 -->






<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">
					<div id="title">实验分室列表</div>
					<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_TEACHER">
					<a class="btn btn-new" href="${pageContext.request.contextPath}/appointment/newLabRoom">新建</a>
					</sec:authorize>
					<a class="btn btn-new" onclick="window.history.go(-1)">返回</a>
				</div>   
				<div class="tool-box">
					<form:form name="queryForm" action="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=1" method="post" modelAttribute="labRoom">
					<ul>
						<li>实验分室名称:</li>
						<li> <form:input id="labRoomName" path="labRoomName"/>	</li>
						<li><input type="submit" value="查询"/>
                            <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
					</ul>
					
					</form:form>
		    	</div>
    	<div class="content-box">
            <table  class="tb"  id="my_show"> 
                <thead>
                    <tr>
                        <th><center>序号</center></th>
                        <th><center>实验室名称</center></th>
                        <th><center>实验室编号</center></th>
                        <th><center>实验室地点</center></th>
                        <th><center>操作</center></th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${listLabRoom}" var="current"  varStatus="i">	
                        <tr>
                            <td><center>${i.count}</center></td>
                            <td><center>
                            <a class="classroom-name" href="${pageContext.request.contextPath}/appointment/showLabRoom?id=${current.id}">${current.labRoomName}</a>
                            <c:if test="${current.isUsed!=1}">
                            <font color=red>（实验室已禁用）</font>
                            </c:if>
                            </center></td>
                            <td><center>${current.labRoomNumber}</center></td>
                            <td>
                            <c:if test="${not empty current.systemRoom}">
                            <center>${current.systemBuild.systemCampus.campusName}<!-- 校区 -->
                            ${current.systemBuild.buildName} <!-- 楼栋 -->
                            ${current.systemRoom.roomName}(${current.systemRoom.roomNo}) <!-- 房间 -->
                            </center>
                            </c:if>
                            </td>
                            <td>
                            	<center>
                            	<a href="${pageContext.request.contextPath}/appointment/getLabRoom?id=${current.id}">查看</a>&nbsp;&nbsp;
                            	<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_TEACHER">
                            	<a href="${pageContext.request.contextPath}/appointment/updateLabRoom?id=${current.id}">编辑</a>&nbsp;&nbsp;
                            	</sec:authorize>
                            	<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
                                <a href="${pageContext.request.contextPath}/appointment/deleteLabRoom?id=${current.id}" onclick="return confirm('确认要删除吗？')">删除</a>
                                </sec:authorize>
                                </center>
                            </td>     
                        </tr>
                        </c:forEach>
                </tbody>
                
            </table>
 <div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?id=${id}&page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
</div>
</div>
</div>
</div>
</div>

</body>
</html>
