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
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/TeachingEvaluationList?currpage=1&courseType=${courseType}";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  //发布学年论文
  function releaseChoseDisserationForYear(id,currpage){
	  $.ajax({
          type: "POST",
          url: "${pageContext.request.contextPath}/nwuChose/releaseChoseDisserationForYear",
          data: {'id':id},
          dataType: "text",
          success: 
          	function(data){
                 if(data =="success"){
                	 alert("发布成功！");
                	 window.location.href="${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage="+currpage;
                 }else if(data == "isReleased"){
              	 alert("该学生的学年论文已发布，请勿重复发布！");		 
                 }
          	},
          error:function(){
        	  alert("后台出了点问题，请重试！");
          }
      });
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
		<li class="end"><a href="javascript:void(0)">学年论文列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">学年论文列表</a>
		  </li>
		  <a class="btn btn-new" href="${pageContext.request.contextPath}/nwuChose/NewChoseDisserationForYear">新建学年论文</a>
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">学年论文列表</div>--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/nwuChose/NewChoseDisserationForYear">新建学年论文</a>--%>
	<%--</div>--%>
	
	<%--<div class="tool-box">--%>
		<%--<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=1" method="post" >--%>
		<%--</form:form>--%>
	<%--</div>--%>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>编号</th>
	    <th>论题</th>
	    <th>要求</th>
	    <th>完成时间</th>
	    <th>学生</th>
	    <th>状态</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${dessitationForYears}" var="curr" varStatus="i">
	  <tr>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.theme}</td>
	    <td>${curr.requirements}</td>
	    <td><fmt:formatDate pattern='yyyy-MM-dd' value="${curr.finishTime.time}"/></td>
	    <td>(${curr.student})${curr.studentCname}</td>
	    <td>
	    	<c:if test="${curr.state eq 0}">未发布</c:if>
	    	<c:if test="${curr.state eq 1}">
	    		<c:if test="${curr.documentId eq null}">学生未提交</c:if>
	    		<c:if test="${curr.documentId ne null}">学生已提交</c:if>
	    	</c:if>
	    </td>
	    <td>
	      <c:if test="${curr.state eq 0}">
	      <a href="${pageContext.request.contextPath}/nwuChose/editChoseDisserationForYear?id=${curr.id}&currpage=${currpage}">编辑</a>
	      <a href="${pageContext.request.contextPath}/nwuChose/deleteChoseDisserationForYear?id=${curr.id}&currpage=${currpage}" onclick="return confirm('确定删除？');">删除</a>
	      <a onclick="releaseChoseDisserationForYear(${curr.id},${currpage})">发布</a>
	      </c:if>
	       <c:if test="${curr.state eq 1}">
	       <c:if test="${curr.documentId ne null}">
	       <a href="${pageContext.request.contextPath}/nwuChose/downloadDessistationForYear?id=${curr.id}">下载</a>
	       </c:if>
	       </c:if>
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseDisserationForYearList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
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
</body>
</html>
