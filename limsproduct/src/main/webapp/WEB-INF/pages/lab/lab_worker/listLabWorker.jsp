<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
 <link href="${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css " rel="stylesheet" type="text/css">
  <script type="text/javascript">
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=1";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  function importLabWorker(){
	  	$('#importLabWorker').window({width:900,left:"30px",top:"30px"});
	  	$("#importLabWorker").window('open');
	  } 
//AJAX验证是否有不规范的字段
  function checkRegex(){
  	var formData = new FormData(document.forms.namedItem("importForm"));
  	$.ajax({
          url:"${pageContext.request.contextPath}/labRoom/checkRegex",
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false, 
          
          error: function(request) {
              alert("暂时不能判断该文档是否规范");
          },
          success: function(data) {
          	if(data == 'success'){
          		// do nothing
          	}else if(data == 'nullError'){
          		alert("文档不规范，请完善必填项！");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else if(data == 'dateError'){
          		alert("出生年月格式填写有误！");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else if(data == '"categoryError"'){
          		alert("请检查上传文档中的药品类型格式，只能填写'试剂'或'耗材'");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else{
          		alert("文件格式错误，请您上传excel格式的文档");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}
          }
      });
  }
  
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	      <li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
		  <li><a href="javascript:void(0)"><spring:message code="left.trainingTeam.management" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)"><spring:message code="all.training.name" />队伍人员列表</a>
		  </li>
		  <sec:authorize ifAnyGranted="ROLE_LABMANAGER,ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN" >
			  <a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/newLabWorker?page=${page}">新建</a>
			  <a class="btn btn-new" onclick="importLabWorker()">导入</a>
		  </sec:authorize>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title"><spring:message code="all.training.name" />队伍人员列表</div>--%>
	  <%--<sec:authorize ifAnyGranted="ROLE_LABMANAGER,ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN" >--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/newLabWorker?page=${page}">新建</a>--%>
	  <%--<a class="btn btn-new" onclick="importLabWorker()">导入</a>--%>
	  <%--</sec:authorize>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=1" method="post" modelAttribute="labWorker">
			 <ul>
  				<li><spring:message code="all.training.name" />队伍人员姓名:<form:input id="lw_name" path="lwName"/></li>
  				<li>
					<input type="submit" value="查询"/>
				 	<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
				</li>
  				</ul>
			 
		</form:form>
	</div>
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th width=5%>姓名</th>
		    <th width=5%>性别</th>
		    <th width=8%>出生年月</th>
		    <th width=7%>学历</th>
		    <th width=7%>学位</th>
		    <th width=7%>所属专业系</th>
		    <th width=8%>专业技术职务</th>
          <th width=7%>职业资格证书</th>
		    <th width=7%>承担任务</th>
		    <th width=7%>专职/兼职</th>
		    <%--<th width=7%>单位</th>
		    --%><th width=7%>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${listLabWorker}" var="curr">
	 <tr>
			  <td>${curr.lwName}</td>
			  <td><c:if test="${curr.lwSex eq 1}">男</c:if>
			  		<c:if test="${curr.lwSex eq 0}">女</c:if></td>
			  <td><fmt:formatDate value="${curr.lwBirthday.time}" pattern="yyyy-MM-dd"/></td>
			  <td>${curr.CDictionaryByLwAcademicDegree.CName}</td>
			  <td>${curr.CDictionaryByLwDegree.CName}</td>
		      <td>${curr.schoolAcademy.academyName}</td>
			  <td>${curr.CDictionaryByLwSpecialtyDuty.CName}</td>
         <td>${curr.vocationalQualification}</td>
			  <td>${curr.lwDuty}</td>
			  <td>${curr.CDictionaryByLwCategoryStaff.CName}</td>
			  <%--
			  <td>${curr.employer}</td>
			  --%>
			  <td>
			  <sec:authorize ifAnyGranted="ROLE_LABMANAGER,ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN" >
			  	<a href="${pageContext.request.contextPath}/labRoom/listLabWorkerTraining?labWorkerId=${curr.id}&currpage=1">查看</a>
			    <a href="${pageContext.request.contextPath}/labRoom/deleteLabWorker?labWorkerId=${curr.id}&page=${page}" onclick="return confirm('若存在培训进修登记，不可删除！！！！');">删除</a>
		      	<a href="${pageContext.request.contextPath}/labRoom/editLabWorker?labWorkerId=${curr.id}&page=${page}">编辑</a>
		      	<a href="${pageContext.request.contextPath}/labRoom/editLabWorkerTraining?labWorkerId=${curr.id}">培训进修登记</a>
		      </sec:authorize>
		      </td>
		      
		    </tr>
	  </c:forEach>
	  </tbody>
	</table>
  </div>
  </div>
  </div>
  </div>
  </div>
  <div id= "importLabWorker"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/labRoom/importLabWorker" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file" onchange="checkRegex()" required="required" />
	         <input type="submit"  value ="开始上传" />
	         <br><br><hr><br>
		<a href="${pageContext.request.contextPath}/pages/importSample/example.xlsx">点击此处</a>，下载范例<br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/labWorker.png" heigth ="100%" width="100%" />
        </form:form>
        </div>
        
        <!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/labRoom/listLabWorker?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
</body>
</html>
