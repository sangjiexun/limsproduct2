
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <script type="text/javascript">
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/indexSoftware?currPage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  //导出
  function subform(gourl) {
		var gourl;
		queryForm.action = gourl;
		queryForm.submit();
	}
  //导入
  function importSoft(){
  	$('#importSoft').window({width:500,left:"30px",top:"30px"});
  	$("#importSoft").window('open');
  } 
  </script>
  
  <script type="text/javascript">
	//选中当前的标签页
	$(function(){
		/* var type=${type};
		$("#c li").eq(type-1).css("padding","4px 10px");
		$("#c li").eq(type-1).css("background-color","#FFF");
		$("#c li").eq(type-1).css("border-bottom","1px solid #FFF");
		$("#c li").eq(type-1).css("color","#999"); */
		
		$("#campus").val(${lab.campus})
		$("#toDepartment").val(${lab.toDepartment})
	});
	
</script>
<style type="text/css">
	th {
		text-align: center;
	}
</style>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.software.management" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">教学软件列表</a>
		  </li>
		  <c:if test="${(fn:contains('zjcclims',PROJECT_NAME) &&
								(sessionScope.selected_role eq 'ROLE_SUPERADMIN' ||
								sessionScope.selected_role eq 'ROLE_LABMANAGER')) ||
				               (!fn:contains('zjcclims',PROJECT_NAME) &&
				               sessionScope.selected_role eq 'ROLE_SUPERADMIN')}">
			  <a class="btn btn-new" href="${pageContext.request.contextPath}/editSoftware?softwareId=-1&page=${currPage}">新建</a>
		  </c:if>
		  <input class="btn btn-new" type="button" value="导出" onclick="subform('${pageContext.request.contextPath}/exportSoftList?currPage=${currPage}');">
		  <a onclick="importSoft()"><input class="btn btn-new" type="button" value="导入"></a>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">教学软件列表</div>--%>
		<%--<c:if test="${(fn:contains('zjcclims',PROJECT_NAME) &&--%>
								<%--(sessionScope.selected_role eq 'ROLE_SUPERADMIN' ||--%>
								<%--sessionScope.selected_role eq 'ROLE_LABMANAGER')) ||--%>
				               <%--(!fn:contains('zjcclims',PROJECT_NAME) &&--%>
				               <%--sessionScope.selected_role eq 'ROLE_SUPERADMIN')}">--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/editSoftware?softwareId=-1&page=${currPage}">新建</a>--%>
		<%--</c:if>--%>
	<%--</div>--%>
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/indexSoftware?currPage=1" method="post" modelAttribute="software">
			 <ul>
  				<li>软件名称:<form:input id="software_name" path="name"/></li>
  				<li>所属<spring:message code="all.trainingRoom.labroom"/>:<form:input type="text" id="lab_room_name" name="labRoom" path="labRoom"/></li>
  				<li>
					<input type="submit" value="查询"/>
			      	<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
					<%--<input type="button" value="导出" onclick="subform('${pageContext.request.contextPath}/exportSoftList?currPage=${currPage}');">--%>
					<%--<a onclick="importSoft()"><input type="button" value="导入"></a>--%>
				</li>
  				</ul>
			 
		</form:form>
	</div>
		<div class="content-box" style="margin-top: 10px">
	<table class="tb" style="text-align: center;"> 
    <thead>
    	<tr>
        	<th>软件编号</th>
        	<th>软件名称</th>
        	<th>软件版本</th>
        	<th>所属<spring:message code="all.trainingRoom.labroom"/></th>
        	<th>有无加密狗</th>
        	<th>点数</th>
        	<th>供应商</th>
        	<th>供应商联系方式</th>
        	<th>操作</th>
    	</tr>
	</thead>
	<tbody>
   	<c:forEach items="${listSoftware}" var="current">	
    	<tr>
           	<td>${current.code}</td>
           	<td>${current.name}</td>
           	<td>${current.edition}</td>
           	<td>
           		<c:forEach items="${current.softwareRoomRelateds}" var="curr">
           		${curr.labRoom.labRoomName}
           		</c:forEach>
           	</td>
         	<td><c:if test="${current.dongle == 1}">有</c:if>
         		<c:if test="${current.dongle == 0}">无</c:if></td>
         	<td>${current.points}</td>
         	<td>${current.supplier}</td>
          	<td>${current.supplierTel}</td>
           	<td>
               	<a href="${pageContext.request.contextPath}/softwareInfo?idkey=${current.id}">查看</a>
				<c:if test="${(fn:contains('zjcclims',PROJECT_NAME) &&
								(sessionScope.selected_role eq 'ROLE_SUPERADMIN' ||
								sessionScope.selected_role eq 'ROLE_LABMANAGER')) ||
				               (!fn:contains('zjcclims',PROJECT_NAME) &&
				               sessionScope.selected_role eq 'ROLE_SUPERADMIN')}">
               	<a href="${pageContext.request.contextPath}/editSoftware?softwareId=${current.id}&page=${currPage}">编辑</a>
               	<a href="${pageContext.request.contextPath}/deleteSoftware?softwareId=${current.id}&page=${currPage}" onclick="return confirm('确定删除？');">删除</a>
				</c:if>
         	</td>
    	</tr>
  	</c:forEach>
	</tbody>
	</table>
		</div>
	<!-- 分页样式 -->
	<%--<div class="pagination">--%>
	<div class="page">
	${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/indexSoftware?currPage=1')" target="_self">首页</a>					    
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/indexSoftware?currPage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/indexSoftware?currPage=${pageModel.currPage}&orderBy=${orderBy }">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currPage}">
    <option value="${pageContext.request.contextPath}/indexSoftware?currPage=${j.index}&orderBy=${orderBy }">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/indexSoftware?currPage=${pageModel.nextPage}')" target="_self">下一页</a>
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/indexSoftware?currPage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>  
	<!-- 分页样式 -->
    
  </div>
  </div>
  </div>
  </div>
  </div>
  <div id= "importSoft"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/importSoft" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file" required="required" />
	         <input type="submit"  value ="开始上传" />
	         <br><br><hr><br>
		点击此处<a href="${pageContext.request.contextPath}/pages/importSample/softExample.xls">下载范例</a><br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/softExample.png" heigth ="100%" width="100%" />
        </form:form>
</div>
</body>
</html>


