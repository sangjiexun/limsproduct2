<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
	$("#print").click(function(){
	$("#ssssss").jqprint();
	})
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
		var page=${currpage};
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
		var page=${currpage};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
function cancelQuery(url){
	window.location.href=url;
}	
function cancel(){
	window.location.href="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=1";
}
//AJAX验证是否通过安全准入
function Access(surl,furl,id){
	var successURL=surl+"&id="+id;
	var failURL=furl+"&deviceId="+id;
	//"${pageContext.request.contextPath}/device/securityAccess?id="+id
	$.ajax({
        url:"${pageContext.request.contextPath}/device/securityAccess?id="+id,
        type:"POST",
        success:function(data){//AJAX查询成功
        		if(data=="success"){
               	//window.location.href="${pageContext.request.contextPath}/device/reservationDevice?id="+id;
               	window.location.href=successURL;
        		}else{
        			alert("您还未通过安全准入验证！");
        			//window.location.href="${pageContext.request.contextPath}/device/findAllTrainingByDeviceId?deviceId="+id;
        			window.location.href=failURL;
        		}    
        }
	});
	
}

function importAnnualBudget(){
  	$('#importAnnualBudget').window({left:"50px",top:"50px",model:true});
  	$("#importAnnualBudget").window('open');
  	
}
</script>
	<style type="text/css">
		.tool-box {
			overflow: hidden;
		}
	</style>
</head>

<body>
<div class="iStyle_RightInner">
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.labconstruction.annualBudget"/></a></li>
		</ul>
	</div>
</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">实验室历年建设项目报告</a>
		</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/annualBudget/newAnnualBudget">新建</a>
		<a  class="btn btn-new" onclick="importAnnualBudget()">导入</a>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/annualBudget/exportAnnualBudget"> 导出</a>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		        <div class="content-box">
				<%--<div class="title">--%>
				<%--<div id="title">实验室历年建设项目报告</div>--%>
				  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/annualBudget/newAnnualBudget">新建</a>--%>
				  <%--<a  class="btn btn-new" onclick="importAnnualBudget()">导入</a>--%>
				  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/annualBudget/exportAnnualBudget"> 导出</a>--%>
			        <%--&lt;%&ndash; <input class="btn btn-new" type="button" value="打印" id="print"> &ndash;%&gt;--%>
			<%--</div> --%>
		     <div class="tool-box">
		      	<form:form name="queryForm" action="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=1" method="post" modelAttribute="annualBudget">
				    <ul>
				    <li>项目名称:<form:input path="projectName" /></li>
				    <li>所属系部:<form:input path="academyName"/></li>
				    <li>建设年度:<form:input path="constructYear"/></li>
				    <li>项目负责人:<form:input path="manager"/></li>
				    <li>状态栏:
				    <form:select path="status" class="chzn-select">
		   		        <form:option value="验收">验收</form:option>
		   		        <form:option value="未验收">未验收</form:option>
		   	        </form:select>
		   	        </li>
				    <li>
						<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
						<input type="submit" value="查询"/>
					</li>
					</ul>
				</form:form>		        
		    </div>
				</div>
    	<div class="content-box">
            <table id="ssssss"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                        <th>系部</th>
                        <th>项目名称</th>
                        <th>项目来源</th>
                        <th>建设年度</th>
                        <th>项目经费（万元）</th>
                        <th>项目负责人</th>
                        <th>项目进展</th>
                        <th>最晚验收时间</th>
                        <th>状态栏</th>
                        <th>操作</th>
                    </tr>
                </thead>
                
                <tbody>
           		<c:forEach items="${projectAnnualBudgets}" var="current" varStatus="i">
               		<tr>
               			<td>${i.count}</td>
                        <td>${current.academyName}</td>
                        <td>${current.projectName}</td>
                        <td>${current.projectSource}</td>
                        <td>${current.constructYear}</td>
                        <td>${current.projectFunds}</td>
                        <td>${current.manager}</td>
                        <td>${current.projectProceed}</td>
                        <td><fmt:formatDate value="${current.deadLines.time}" pattern="yyyy-MM-dd" /></td>
	                    <td>${current.status}</td>
	                    <%-- <td>${current.appendix}</td> --%>
                        <td>
                           	<a href="${pageContext.request.contextPath}/annualBudget/editAnnualBudget?idKey=${current.id}" >编辑</a>
                           	<a href="${pageContext.request.contextPath}/annualBudget/docAnnualBudget?idKey=${current.id}" >相关文档</a>
                        </td>
                    </tr>
           		</c:forEach>
                </tbody>
            </table>
  <div id= "importAnnualBudget"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/annualBudget/importAnnualBudget" enctype ="multipart/form-data">
	        <br>
	        <input type="file" id="file" name ="file" required="required" /><!-- onchange="checkRegex()" -->
	        <input type="submit"  value ="开始上传" />
	        <br><br><hr><br>
			<a href="${pageContext.request.contextPath}/pages/importSample/sample.xlsx">点击此处</a>，下载范例<br><br><hr><br>
			示例图片：<br>
			<img src="${pageContext.request.contextPath}/images/importSample/sample.png" heigth ="65%" width="65%" />
        </form:form>
    </div>
<!-- 分页模块 -->
<%--<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>--%>
<!-- 分页模块 -->
	<!-- 分页开始 -->
	<div class="page" >
		${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
		<a href="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=1" target="_self">首页</a>
		<a href="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${pageModel.previousPage}" target="_self">上一页</a>
		第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
		<option value="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${currpage}">${currpage}</option>
		<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
			<c:if test="${j.index!=pageModel.currpage}">
				<option value="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${j.index}">${j.index}</option>
			</c:if>
		</c:forEach></select>页
		<a href="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${pageModel.nextPage}" target="_self">下一页</a>
		<a href="${pageContext.request.contextPath}/annualBudget/listAnnualBudget?currpage=${pageModel.lastPage}" target="_self">末页</a>
	</div>
	<!-- 分页结束 -->
</div>


</div>
</div>
</div>
</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Downlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Allpages.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/iStyle_SystemUI_Interface.js"></script>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select': {search_contains : true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->

</body>
</html>


