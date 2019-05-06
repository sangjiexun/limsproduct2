<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
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
  $(document).ready(function(){
	});
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/nwuChose/ChoseAttentionList?currpage=1";
    	
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  </script>
<script type="text/javascript">
</script>	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">注意事项管理</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">注意事项</a>
		  </li>
	  </ul>
    <%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/ChoseThemeList?currpage=1">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=1&orderBy=${orderBy }">草稿</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
	</ul>
  --%><div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">注意事项</div>--%>
	   <%--&lt;%&ndash;<a class="btn btn-new" href="${pageContext.request.contextPath}/nwuChose/newChoseAttention">新建</a> &ndash;%&gt;--%>
	  <%--<!-- <a class="btn btn-new" href="javaScript:void(0)" onclick="check" >新建</a> -->--%>
	<%--</div>--%>
		<!-- 查询开始  -->
	<%--<div class="tool-box">
		 <form:form name="queryForm" action="${pageContext.request.contextPath}/ChoseAttentionList?currpage=1" method="post" modelAttribute="choseAttention">
			 <ul>
			 	
  				<li>标题： </li>
  				<li><form:input id="theme_id" path="tittle"/></li>
  				<li>所属大纲：</li>
  				<li>
	  			    <form:select path="choseTheme.tittle"  id="theme_id" class="chzn-select">
	  				    <form:option value="">请选择</form:option>
	  				    <c:forEach items="${choseThemes}" var="currThe">
	  				    	<form:option value="${currThe.tittle}">[${currThe.id}]${currThe.tittle}</form:option>
	  				    </c:forEach>
	  				</form:select>
  				</li>
                  <li><input type="submit" value="查询"/>
			      <input type="button" value="取消" onclick="cancel();"/>
  			</ul>
	    
			 
		</form:form> 
	</div>--%>
	
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>编号</th>
	    <th>标题</th>
	    <%--<th>内容</th>--%>
	    <th>类型</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${choseAttentions}" var="curr" varStatus="i">
	  <tr>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.tittle}</td>
	    <%--<td>${curr.content}</td>--%>
	    <c:if test="${curr.type==1}">
	   <td> 导师制互选导师</td>
	    </c:if>
	    <c:if test="${curr.type==2}">
	   <td> 导师制互选学生</td>
	    </c:if>
	      <c:if test="${curr.type==3}">
	   <td> 论文制互选导师</td>
	    </c:if>
	      <c:if test="${curr.type==4}">
	   <td> 论文制互选学生</td>
	    </c:if>
	    <td>
	      <%--<a href="javascript:void(0)">发布</a>
	      --%><a href="${pageContext.request.contextPath}/nwuChose/editChoseAttention?choseAttentionId=${curr.id}">编辑</a>
	      <%-- <a href="${pageContext.request.contextPath}/deleteChoseAttention?choseAttentionId=${curr.id}" onclick="return confirm('确定删除？');">删除</a> --%>
	      <a href="${pageContext.request.contextPath}/nwuChose/veiwChoseAttention?choseAttentionId=${curr.id}&currpage=1">详情</a>
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<%-- <div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/ChoseAttentionList?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/ChoseAttentionList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/ChoseAttentionList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/ChoseAttentionList?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/ChoseAttentionList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/ChoseAttentionList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div> --%>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
  <!-- 下拉框的js -->
</body>
</html>
