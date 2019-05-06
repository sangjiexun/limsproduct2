<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
     <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 --> 
  <script type="text/javascript">
//取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=${status}";
  }
  //跳转
  function targetUrl(url)
{
	location.href = url;
} 
  $(document).ready(function(){
      var s=${status};
      if(s==9){
    	  $("#s9").addClass("TabbedPanelsTab selected");
    	  $("#s3").addClass("TabbedPanelsTab");
    	  $("#s1").addClass("TabbedPanelsTab");
    	  $("#s0").addClass("TabbedPanelsTab");
      }
      if(s==1){
    	  $("#s1").addClass("TabbedPanelsTab selected");
    	  $("#s0").addClass("TabbedPanelsTab");
    	  $("#s9").addClass("TabbedPanelsTab");
    	  $("#s3").addClass("TabbedPanelsTab");
      }
      if(s==3){
    	  $("#s3").addClass("TabbedPanelsTab selected");
    	  $("#s1").addClass("TabbedPanelsTab");
    	  $("#s0").addClass("TabbedPanelsTab");
    	  $("#s9").addClass("TabbedPanelsTab");
      }
      if(s==0){
    	  $("#s0").addClass("TabbedPanelsTab selected");
    	  $("#s9").addClass("TabbedPanelsTab");
    	  $("#s1").addClass("TabbedPanelsTab");
    	  $("#s3").addClass("TabbedPanelsTab");
      }
	});
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="left.material.management"/></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.receive.audit"/></a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <ul class="TabbedPanelsTabGroup">
  <li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=3">审核中</a></li>
  <li class="TabbedPanelsTab" id="s9"><a href="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=9">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=1">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=0">审核拒绝</a></li>
	</ul>
  <div class="TabbedPanelsContentGroup">
  
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">药品申领列表</div>--%>
	<%--</div>--%>
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=${status}" method="post" modelAttribute="assetReceive">
			  <ul>
  				<li>申请编号:
  					<form:select id="receiveNo" path="receiveNo" class="chzn-select">
  					<form:option value="">请选择</form:option>
  						<c:forEach items="${listAssetReceives}" var="curr">
  							<form:option value="${curr.receiveNo}">${curr.receiveNo}</form:option>
  						</c:forEach>
  					</form:select>
  				</li>
  				<li>申领人:
  					<form:select id="user" path="user.username" class="chzn-select">
  					<form:option value="">请选择</form:option>
  						<c:forEach items="${users}" var="curr">
  							<form:option value="${curr.key}">${curr.value}</form:option>
  						</c:forEach>
  					</form:select>
  				</li>
  				<li>
					<input type="submit" value="查询"/>
			      <input class="cancel-submit" type="button" value="取消" onclick="cancel()"/></li>
		</form:form>
	</div>
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>序号</th> 
	    <th>申请编号</th>
	    <th>领用时间</th>
	    <th>申领人</th>
	    <th>申请时间</th>
	    <th>实验大纲</th>
	    <th>实验项目</th> 
	    <th>审核状态</th> 
	    <th>分配状态</th>
	    <th>操作</th> 
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${listAssetReceives}" var="curr" varStatus="i">
	  <tr>
	    <td>${i.count+pageSize*(currpage-1)}</td> 
	    <td>${curr.receiveNo}</td>
	    <td>
	    	<fmt:formatDate value="${curr.startData.time}" pattern="yyyy年MM月dd日 hh:mm" />-
	    	<fmt:formatDate value="${curr.endDate.time}" pattern="yyyy年MM月dd日 hh:mm" />
	    </td>  
	    <td>${curr.user.cname}</td> 
	    <td><fmt:formatDate value="${curr.receiveDate.time}" pattern="yyyy-MM-dd" /></td>
	    <td>
	    	<c:if test = "${curr.type == 0}">公用</c:if>
	    	<c:if test = "${curr.type != 0}">${curr.operationItem.operationOutline.labOutlineName}</c:if>
	    </td> 
	    <td>
	    	<c:if test = "${curr.type == 0}">--</c:if>
	    	<c:if test = "${curr.type != 0}">${curr.operationItem.lpName}</c:if>
	    </td>
	    <td>
	    <c:if test="${curr.status eq 2}">未提交</c:if>
	    <c:if test="${curr.status eq 3}">审核中</c:if>
	    <c:if test="${curr.status eq 0}"><font color="red">拒绝</fond></c:if>
	    <c:if test="${curr.status eq 1}">审核通过</c:if>
	    </td> 
	    <td>
	    <c:if test="${curr.status eq 1 && curr.allocationStatus eq 1}">已分配</c:if>
	    <c:if test="${curr.status eq 1 && curr.allocationStatus eq 0}">未分配</c:if>
	    </td> 
	     <td>
	     <c:if test="${curr.status eq 1||curr.status eq 0}">
	      <a href="${pageContext.request.contextPath}/asset/viewAuditAssetReceive?id=${curr.id}">查看</a> 
	      </c:if> 
	      <c:if test="${curr.status eq 3}">
	      <a href="${pageContext.request.contextPath}/asset/auditAssetReceive?id=${curr.id}">审核</a>
	      </c:if>
	      <c:if test="${curr.status eq 1 && curr.allocationStatus eq 0}">
	      <a href="${pageContext.request.contextPath}/asset/allocateReceive?id=${curr.id}">分配</a>
	      </c:if>
	    </td>
	    </tr>
	  </c:forEach>
	  </tbody>
	</table>
	</div>
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
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=1&status=${status}')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=${pageModel.previousPage}&status=${status}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}//asset/listAssetReceiveAudits?currpage=${pageModel.currpage}&status=${status}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=${j.index}&status=${status}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=${pageModel.nextPage}&status=${status}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetReceiveAudits?currpage=${pageModel.lastPage}&status=${status}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
