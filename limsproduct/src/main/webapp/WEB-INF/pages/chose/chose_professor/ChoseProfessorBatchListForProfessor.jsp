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
    window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }";
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
		<li class="end"><a href="javascript:void(0)">导师互选列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <div class="title">
	  <div id="title">导师互选列表</div>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}">新建</a>
	--%></div>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=1" method="post" modelAttribute="choseTheme"></form:form>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th>编号</th>
	   <th>所属届</th>
	   <th>批次</th>
	   <th>开始时间</th>
	   <th>结束时间</th>
	   <th>选报人数</th>
	   <th>已选人数</th>
	   <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${lists}" var="curr" varStatus="i">
	  <tr>
	  	<input type="hidden" value="${curr[0]}" name="batchId"/>
	  	<input type="hidden" value="${curr[9]}" name="isAduit"/>
	  	<input type="hidden" value="${curr[5]}" name="themeId"/>
	  	<input type="hidden" value="${curr[10]}" name="maxNumber"/>
	  	<input type="hidden" value="${curr[8]}" name="finalNumber"/>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr[1]}</td>
	    <td name="batchNumber">${curr[2]}</td>
	    <td name="startTime">${curr[3]}</td>
	    <td name="endTime">${curr[4]}</td>
	    <td>${curr[6]+curr[7]}</td>
	    <td>${curr[7]}</td>
	    <td name="operator">
	      	
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=1&themeId=${themeId }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=${pageModel.previousPage}&themeId=${themeId }')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=${pageModel.currpage}&themeId=${themeId}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=${j.index}&themeId=${themeId}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=${pageModel.nextPage}&themeId=${themeId }')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=${pageModel.lastPage}&themeId=${themeId }')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		  $(document).ready(function(){
	    	var st=$("td[name='startTime']");
	    	var et=$("td[name='endTime']");
	    	var oper=$("td[name='operator']");
	    	
	    	//第几志愿
	    	var batchNumbers=$("td[name='batchNumber']");
	    	var batchIds=$("input[name='batchId']");
	    	var themeIds=$("input[name='themeId']");
	    	var isAduits=$("input[name='isAduit']");
	    	var maxNumbers=$("input[name='maxNumber']");
	    	var finalNumbers=$("input[name='finalNumber']");
	    	//现在的时间
	    	var currTime=new Date().getTime();
	    	//开始时间
	    	var startTime;
	    	//结束时间
	    	var endTime;
	        for(var i=0;i<st.length;i++){
	        	
	        	startTime=new Date(st.eq(i).text()).getTime();
	        	endTime=new Date(et.eq(i).text()).getTime();
	        	if(finalNumbers.eq(i).val()>=maxNumbers.eq(i).val()){
	        		var t="<a href='${pageContext.request.contextPath}/nwuChose/studentList?batchId="+batchIds.eq(i).val()+"&flag=0&themeId="+themeIds.eq(i).val()+"'>查看</a>";
	        		oper.eq(i).html(t);
	        	}
	        	else{
	        		 if(currTime>startTime&&currTime<endTime){
		        		if(isAduits.eq(i).val()>=batchNumbers.eq(i).text()){
		        			var t="<a href='${pageContext.request.contextPath}/nwuChose/studentList?batchId="+batchIds.eq(i).val()+"&flag=0&themeId="+themeIds.eq(i).val()+"'>查看</a>";
		        		    oper.eq(i).html(t);
		        		}else{
		        			var t="<a href='${pageContext.request.contextPath}/nwuChose/aduitStudentListForProfessor?batchId="+batchIds.eq(i).val()+"&themeId="+themeIds.eq(i).val()+"&flag=1'>我的审核</a>";
		        		   oper.eq(i).html(t);
		        		}
	        	    }
		        	else{
		        		var t="<a href='${pageContext.request.contextPath}/nwuChose/studentList?batchId="+batchIds.eq(i).val()+"&flag=0&themeId="+themeIds.eq(i).val()+"'>查看</a>";
		        		oper.eq(i).html(t);
		        	}
	        		
	        	}
	        }
	    });  
	</script>
	<!-- 下拉框的js -->
</body>
</html>
