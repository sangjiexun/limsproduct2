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
  <!-- layer弹窗 -->
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	});
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/nwuChose/ChoseThemeList?currpage=1";
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
		<li class="end"><a href="javascript:void(0)">导师互选审核-导师</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">导师互选审核-导师</a>
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
	  <%--<div id="title">导师互选列表</div>--%>
	  <%--&lt;%&ndash;<a class="btn btn-new" href="${pageContext.request.contextPath}">新建</a>--%>
	<%--&ndash;%&gt;</div>--%>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=1" method="post" modelAttribute="choseTheme">
			<ul>
				<li>所属届:<form:input path="theYear" id="theYear"/></li>
				<li><input type="submit" value="查询"/>
			    <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
			</ul>
		</form:form>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th>编号</th>
	    <th>全部学生数量</th>
	    <th>全部导师数量</th>
	    <th>导师可带最大学生数量</th>
	    <th>开始时间</th>
	    <th>结束时间</th>
	    <th>志愿数量</th>
	    <th>提前通知天数</th>
	    <th>所属届</th>
	    <th>状态</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${choseThemes}" var="curr" varStatus="i">
	  <tr>
	  	<input name="expectDeadline" type="hidden" value="${curr.expectDeadline.time }"/>
	  	<input name="expectStartline" type="hidden" value="${curr.expectStartline.time }"/>
	  	<input name="themeId" type="hidden" value="${curr.id }"/>
	  	<input name="state" type="hidden" value="${curr.state }"/>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.studentNumber}</td>
	    <td>${curr.teacherNumber}</td>
	    <td>${curr.maxStudent}</td>
	    <td><fmt:formatDate value='${curr.startTime.time}' pattern='yyyy-MM-dd'/></td>
	    <td><fmt:formatDate value='${curr.endTime.time}' pattern='yyyy-MM-dd'/></td>
	    <td>${curr.batchNumber}</td>
	    <td>${curr.advanceDay}</td>
	    <td>${curr.theYear}</td>
	    <td><c:if test="${curr.state==0}">未发布</c:if>
	    <c:if test="${curr.state==1}">已发布</c:if></td>
	    <td name="operator">
	      <c:if test="${curr.state!=0}">
	        <%-- <a href="${pageContext.request.contextPath}/ChoseProfessorBatchListForProfessor?themeId=${curr.id}&currpage=1">我的审核</a> --%>
	      	<a href="javaScript:void(0)" onclick="checkIfReadAttention(${curr.id})">我的审核</a>
	      </c:if>
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		    /* function checkExpectDeadline(expectDeadline,themeId){
		    	var currTime=new Date();
		        var expectDeadline=new Date(expectDeadline);
		        if(expectDeadline.getTime()<currTime.getTime()){
		        alert("超过期望的截止时间");
		       }
		    else{
		    	window.location.href="${pageContext.request.contextPath}/addExpectNumber?themeId="+themeId;
		     } 
		    } */
		    $(function(){
		    	var expectDeadlineArray=$("input[name='expectDeadline']");
		    	var expectStartlineArray=$("input[name='expectStartline']");
		    	var stateArray=$("input[name='state']");
		    	var operatorArray=$("td[name='operator']");
		    	var themeIds=$("input[name='themeId']");
		    	//当前时间
		    	var currTime=new Date();
		    	//初始化当前时间
		    	currTime.setHours(0);	    	
		    	currTime.setMinutes(0, 0, 0);
		    	currTime.setSeconds(0, 0);
		    	for(var i=0;i<expectStartlineArray.length;i++){
		    	    var state= stateArray.eq(i).val();
		    		if(state==0){
		    			var expectDeadline=new Date(expectDeadlineArray.eq(i).val());
		    			//初始化期望结束时间
		    			expectDeadline.setHours(0);	    	
				    	expectDeadline.setMinutes(0, 0, 0);
				    	expectDeadline.setSeconds(0, 0);
		    			var expectStartline=new Date(expectStartlineArray.eq(i).val());
		    			//初始化期望开始时间
		    			expectStartline.setHours(0);	    	
				    	expectStartline.setMinutes(0, 0, 0);
				    	expectStartline.setSeconds(0, 0);
				    	
				    	
				    	//alert(currTime.getTime()<=expectDeadline.getTime())
		    			if((currTime.getTime()<=expectDeadline.getTime())&&(currTime.getTime()>=expectStartline.getTime())){
		    				var content=operatorArray.eq(i).html();
		    				//alert(0)
		    				var t='<a href="${pageContext.request.contextPath}/nwuChose/addExpectNumber?themeId='+themeIds.eq(i).val()+'";>填写期望学生数</a>';
		    				operatorArray.eq(i).html(content+t);
		    			}
		    		}
		    	}
		    	
		    })
		function  checkIfReadAttention(themeId){
       //type 所属类型   1导师制互选导师   2导师制互选学生   3论文制互选导师  4论文制互选学生
   	     $.ajax({
   		url:"${pageContext.request.contextPath}/nwuChose/checkIfReadAttention?themeId="+themeId,
   		type:"post",
   		success:function(result){
   			if(result=="success"){
   				window.location.href="${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=1&themeId="+themeId;
   			}else{
   				//弹出注意事项框
			  layer.ready(function(){
			        layer.open({
			            type: 2,
			            title: '',
			            fix: true,
			            maxmin:true,
			            shift:-1,
			            closeBtn: 1,
			            shadeClose: true,
			            move:false,
			            maxmin: false,
			            area: ['1000px', '420px'],
			            content: '${pageContext.request.contextPath}/nwuChose/findChoseAttentionByType?type=2&themeId='+themeId,
			            end: function(){
			            }
			        });
			    });
   			}
   		}
   	}) 
   }
	</script>
	<!-- 下拉框的js -->
</body>
</html>
