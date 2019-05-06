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
	    <li><a href="javascript:void(0)">导师论文互选</a></li>
		<li class="end"><a href="javascript:void(0)">毕业论文互选审核</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">毕业论文互选审核</a>
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
	  <%--<div id="title">导师论文互选列表</div>--%>
	  <%--<a class="btn btn-new" href="${pageContext.request.contextPath}">新建</a>
	<%--&ndash;%&gt;</div>--%>
	
	<%--<div class="tool-box">--%>
		<%--<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/ChoseThemeListForProfessor?currpage=1" method="post" modelAttribute="choseTheme">&lt;%&ndash;--%>
			 <%--<ul>--%>
  				<%--<li>实验项目名称： </li>--%>
  				<%--<li><form:input id="lp_name" path="lpName"/></li>--%>
  				<%--<li>学期：</li>--%>
  				<%--<li>--%>
  				  <%--<form:select path="schoolTerm.id" id="term_id">--%>
  				    <%--<form:option value="${schoolTerm.id }">${schoolTerm.termName }</form:option>--%>
  				    <%--<c:forEach items="${schoolTerms}" var="curr">--%>
  				    	<%--<c:if test="${curr.id ne schoolTerm.id }">--%>
	  				    	<%--<form:option value="${curr.id }">${curr.termName }</form:option>--%>
	  				    <%--</c:if>--%>
  				    <%--</c:forEach>--%>
  				  <%--</form:select>--%>
  				<%--</li>--%>
  				<%--<li>所属课程：</li>--%>
  				<%--<li>--%>
	  			    <%--<form:select path="schoolCourseInfo.courseNumber"  id="course_number" class="chzn-select">--%>
	  				    <%--<form:option value="">请选择</form:option>--%>
	  				    <%--<c:forEach items="${schoolCourseInfos}" var="sc">--%>
	  				    	<%--<form:option value="${sc.key}">[${sc.key}]${sc.value }</form:option>--%>
	  				    <%--</c:forEach>--%>
	  				<%--</form:select>--%>
  				<%--</li>--%>
  				<%--<li><input type="submit" value="查询"/>--%>
			      <%--<input type="button" value="取消" onclick="cancel();"/></li>--%>
  				<%--</ul>--%>
			 <%----%>
		<%--&ndash;%&gt;</form:form>--%>
	<%--</div>--%>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th>编号</th>
	    <!-- <th>立题数量</th> -->
	    <th>学生数量</th>
	    <th>导师数量</th>
	    <th>最小立题数量</th>
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
	  <c:forEach items="${choseProfessorList}" var="curr" varStatus="i">
	  <tr>
	  	<input name="expectDeadline" type="hidden" value="${curr.choseTheme.expectDeadline.time }"/>
	  	<%-- <input name="finalTime" type="hidden" value="${curr.choseTheme.finishTime.time }"/> --%>
	  	<input name="expectStartline" type="hidden" value="${curr.choseTheme.expectStartline.time }"/>
	  	<input name="themeId" type="hidden" value="${curr.choseTheme.id }"/>
	  	<input name="state" type="hidden" value="${curr.choseTheme.state }"/>
	  	<input name="professorId" type="hidden" value="${curr.id }"/>
	  	<input name="expectNumber" type="hidden" value="${curr.expectNumber }"/><!-- 立题数量 -->
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.choseTheme.studentNumber}</td>
	    <td>${curr.choseTheme.teacherNumber}</td>
	    <td name="maxStudent">${curr.choseTheme.maxStudent}</td>
	    <td><fmt:formatDate value='${curr.choseTheme.startTime.time}' pattern='yyyy-MM-dd'/></td>
	    <td><fmt:formatDate value='${curr.choseTheme.endTime.time}' pattern='yyyy-MM-dd'/></td>
	    <td>${curr.choseTheme.batchNumber}</td>
	    <td>${curr.choseTheme.advanceDay}</td>
	    <td>${curr.choseTheme.theYear}</td>
	    <td><c:if test="${curr.choseTheme.state==0}">未发布</c:if>
	    <c:if test="${curr.choseTheme.state==1}">已发布</c:if></td>
	    <td name="operator">
	      <c:if test="${curr.choseTheme.state!=0}">
	        <%-- <a href="${pageContext.request.contextPath}/choseDissertation/choseProfessorBatchListForProfessor?themeId=${curr.choseTheme.id}&currpage=1">我的审核</a> --%>
	      	<a href="javaScript:void(0)" onclick="checkIfReadAttention(${curr.choseTheme.id})">我的审核</a>
	      </c:if>
	    </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/choseThemeListForProfessor?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	    $(function(){
	        //var finalTimeArray=$("input[name='finalTime']");
	    	var expectDeadlineArray=$("input[name='expectDeadline']");
	    	var expectStartlineArray=$("input[name='expectStartline']");
	    	var professorIdArray=$("input[name='professorId']");
	    	var stateArray=$("input[name='state']");
	    	var expectNumberArray=$("input[name='expectNumber']");//导师立题的数量
	    	var operatorArray=$("td[name='operator']");
	    	var themeIds=$("input[name='themeId']");
	    	var maxStudentArray=$("td[name='maxStudent']");//最大立题的数量
	    	//当前时间
	    	var currTime=new Date();
	    	//初始化当前时间
	    	currTime.setHours(0);	    	
	    	currTime.setMinutes(0, 0, 0);
	    	currTime.setSeconds(0, 0);
	    	for(var i=0;i<stateArray.length;i++){
	    	    var state= stateArray.eq(i).val();
	    		if(state==0){
	    			var expectDeadline=new Date(expectDeadlineArray.eq(i).val());
	    			//var finalTime=new Date(finalTimeArray.eq(i).val());
	    			//初始化期望结束时间
	    			//finalTime.setHours(0);	    	
			    	//finalTime.setMinutes(0, 0, 0);
			    	//finalTime.setSeconds(0, 0);
	    			//初始化期望结束时间
	    			expectDeadline.setHours(0);	    	
			    	expectDeadline.setMinutes(0, 0, 0);
			    	expectDeadline.setSeconds(0, 0);
	    			var expectStartline=new Date(expectStartlineArray.eq(i).val());
	    			//初始化期望开始时间
	    			expectStartline.setHours(0);	    	
			    	expectStartline.setMinutes(0, 0, 0);
			    	expectStartline.setSeconds(0, 0);
			    		if((currTime.getTime()<=expectDeadline.getTime())&&(currTime.getTime()>=expectStartline.getTime())){
			    		  //if((currTime.getTime()<=finalTime.getTime())&&(currTime.getTime()>=finalTime.getTime())){
	    				var content=operatorArray.eq(i).html();
	    				//var t='<a href="${pageContext.request.contextPath}/choseDissertation/addDissertation?choseProfessor.choseTheme.id='+themeIds.eq(i).val()+'";>立题</a>';
	    				var t='<a href="${pageContext.request.contextPath}/choseDissertation/findDissertationList?currpage=1&id='+professorIdArray.eq(i).val()+'";>立题</a>';
	    				operatorArray.eq(i).html(content+t);
	    			  //}
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
   				window.location.href="${pageContext.request.contextPath}/choseDissertation/choseProfessorBatchListForProfessor?currpage=1&themeId="+themeId;
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
			            content: '${pageContext.request.contextPath}/nwuChose/findChoseAttentionByType?type=4&themeId='+themeId,
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
