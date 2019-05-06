<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<fmt:setBundle basename="bundles.projecttermination-resources" />

<html>
<head>
<meta name="decorator" content="iframe" />
<title><fmt:message key="html.title" />
</title>
<!--分页js  -->
 <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/operation/operationoutline.js"></script>
<!--分页js  -->

<!-- 打印插件的引用 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	$("#print").click(function(){
	$("#ssssss").jqprint();
	})
	});
	function zln(s){
    if(confirm( '你真的要删除吗？ ')==false){
       return   false;
    }else{
     window.location.href="${pageContext.request.contextPath}/operation/delectuotline?idkey="+s;
    }

}
function exportAlls(){
  window.location.href="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=1";
}
// 分页
function targetUrl(url) {
	document.queryForm.action=url;
	document.queryForm.submit();
}
</script>
<!-- 打印插件的引用 -->

<%--<link type="text/css" rel="stylesheet"--%>
	<%--href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>

<body>
<div class="iStyle_RightInner">
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.labconstruction.fund"/></a></li>
		</ul>
	</div>
</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab1 selected" id="s1">
			<a href="javascript:void(0)">预算科目管理</a>
		</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/dictionary/newCFundAppItem">新建</a>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		        <div class="content-box">
				<%--<div class="title">--%>
				<%--<div id="title">预算科目管理</div>--%>
				<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/dictionary/newCFundAppItem">新建</a>--%>
				<%--&lt;%&ndash;<a class="btn btn-new" href="${pageContext.request.contextPath}${exportLabConstructApp.url}?modelId=${exportLabConstructApp.id}"> 导出</a>--%>
			    <%--<input class="btn btn-new" type="button" value="打印" id="print">&ndash;%&gt;--%>
			<%--</div>--%>
		     <%--<div class="tool-box">--%>
		      	<%--<form:form name="queryForm" action="${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=1" method="post" modelAttribute="cFundAppItem">--%>
				    <%--&lt;%&ndash;<ul>--%>
				    <%--<li>预算科目名称： </li>--%>
				    <%--<li><form:input id="name" path="name"/></li>--%>
				    <%--<li>--%>
    				   <%--<input type="submit" value="查询"/>--%>
					   <%--<input type="button" value="取消" onclick="cancel();"/>--%>
					<%--</li>--%>
					<%--</ul>--%>
				<%--&ndash;%&gt;</form:form>--%>
		    <%--</div>--%>
    	<div class="content-box">
            <table id="ssssss"> 
                <thead>
                    <tr>
                    	<th>预算科目编号</th>
                        <th>预算科目名称</th>
                        <th>操作</th>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${listCFundAppItem}" var="current" varStatus="i">
                		<tr>
                        <td>${current.id}</td>
                        <td>${current.name}</td>
                        <td>
                           <a href="${pageContext.request.contextPath}/dictionary/editCFundAppItem?idKey=${current.id}"   >编辑</a>
                           <a href="${pageContext.request.contextPath}/dictionary/deleteCFundAppItem?idKey=${current.id}" onclick="return confirm('确认删除吗？')">删除</a>
                         </td>
                        </tr>
                		</c:forEach>
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/dictionary/listCFundAppItem?page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
<!-- 分页模块 -->
</div>

</div>
</div>
</div>
</div>
</div>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
--%><script type="text/javascript" src="${pageContext.request.contextPath}/js/is_Icons.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/is_LeftList.js"></script>
<!--<script type="text/javascript" src="js/is_ControlsDrag.js"></script>-->
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


