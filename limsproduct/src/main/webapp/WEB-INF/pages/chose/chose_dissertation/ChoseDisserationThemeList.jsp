<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe" />

<!-- 下拉框的样式 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->

<script type="text/javascript">
	$(document).ready(function() {
	});
	//取消查询
	function cancel() {
		window.location.href = "${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=1";
	}
	//跳转
	function targetUrl(url) {
		document.queryForm.action = url;
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
				<li><a href="javascript:void(0)">毕业论文互选</a>
				</li>
				<li class="end"><a href="javascript:void(0)">毕业论文互选列表</a>
				</li>
			</ul>
		</div>
	</div>

	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)">毕业论文互选管理</a>
			</li>
			<font style="font-size:12px;font-weight:normal;color:#888;margin:0 0 0 10px;">
				（提示信息：
				<c:if test="${choseProfessorBatch ne NULL }">
					${choseProfessorBatch.choseTheme.theYear }届 当前批次:第${choseProfessorBatch.batchNumber}批  参选人数:${participantNumber} 未被选人数:${noSelectedNumber }
				</c:if>
				<c:if test="${choseProfessorBatch eq NULL }">
					当前无正在进行的大纲
				</c:if>）
			</font>
			<a class="btn btn-new" href="${pageContext.request.contextPath}/choseDissertation/newChoseThemeForCD">新建</a>
		</ul>
		<%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/ChoseThemeList?currpage=1">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=1&orderBy=${orderBy }">草稿</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
	</ul>
  --%>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">

				<div class="content-box">
					<%--<div class="title">--%>
						<%--<div id="title">导师论文互选列表</div>--%>
	                    <%--<font style="font-size:12px;font-weight:normal;color:#888;margin:0 0 0 10px;">--%>
						  <%--（提示信息：--%>
					<%--<c:if test="${choseProfessorBatch ne NULL }">--%>
						<%--${choseProfessorBatch.choseTheme.theYear }届 当前批次:第${choseProfessorBatch.batchNumber}批  参选人数:${participantNumber} 未被选人数:${noSelectedNumber }--%>
					<%--</c:if>--%>
					<%--<c:if test="${choseProfessorBatch eq NULL }">--%>
						<%--当前无正在进行的大纲--%>
					<%--</c:if>）--%>
						<%--</font>--%>
						<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/choseDissertation/newChoseThemeForCD">新建</a>--%>
                    <%--</div>--%>
					<div class="tool-box">
						<form:form name="queryForm"
							action="${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=1"
							method="post" modelAttribute="choseTheme">
							<ul>
								<li>所属届:<form:input path="theYear" id="theYear"/></li>
								<li><input type="submit" value="查询"/>
			                    <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
							</ul>
							
							<%--
			 <ul>
  				<li>实验项目名称： </li>
  				<li><form:input id="lp_name" path="lpName"/></li>
  				<li>学期：</li>
  				<li>
  				  <form:select path="schoolTerm.id" id="term_id">
  				    <form:option value="${schoolTerm.id }">${schoolTerm.termName }</form:option>
  				    <c:forEach items="${schoolTerms}" var="curr">
  				    	<c:if test="${curr.id ne schoolTerm.id }">
	  				    	<form:option value="${curr.id }">${curr.termName }</form:option>
	  				    </c:if>
  				    </c:forEach>
  				  </form:select>
  				</li>
  				<li>所属课程：</li>
  				<li>
	  			    <form:select path="schoolCourseInfo.courseNumber"  id="course_number" class="chzn-select">
	  				    <form:option value="">请选择</form:option>
	  				    <c:forEach items="${schoolCourseInfos}" var="sc">
	  				    	<form:option value="${sc.key}">[${sc.key}]${sc.value }</form:option>
	  				    </c:forEach>
	  				</form:select>
  				</li>
  				<li><input type="submit" value="查询"/>
			      <input type="button" value="取消" onclick="cancel();"/></li>
  				</ul>
			 
		--%>
						</form:form>
					</div>
					<table class="tb" id="my_show">
						<thead>
							<tr>
								<th>编号</th>
								<th>全部立题数量</th>
								<th>全部导师数量</th>
								<th>导师立题的最小数量</th>
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
							<c:forEach items="${choseThemeVos}" var="curr" varStatus="i">
								<tr>
									<input type="hidden" value="${curr.expectDeadline.time }" name="expectDeadline"/>
	  								<input type="hidden" value="${curr.state }" name="state"/>
	  								<input type="hidden" value="${curr.id }" name="themeId"/>
									<td>${(pageModel.currpage-1)*pageSize+i.count }</td>
									<td>${curr.studentNumber}</td>
									<td>${curr.teacherNumber}</td>
									<td>${curr.maxStudent}</td>
									<td><fmt:formatDate value='${curr.startTime.time}'
											pattern='yyyy-MM-dd' />
									</td>
									<td><fmt:formatDate value='${curr.endTime.time}'
											pattern='yyyy-MM-dd' />
									</td>
									<td>${curr.batchNumber}</td>
									<td>${curr.advanceDay}</td>
									<td>${curr.theYear}</td>
									<td>${curr.state}</td>
									<td name="operator">
								       <c:if test="${curr.state==0}">
								        <a href="javascript:deleteChoseTheme('${curr.id }');" onclick="return confirm('确定删除？');">删除</a>
								      </c:if>
								       <c:if test="${curr.state==1}">
								        <a href="javascript:closeChoseTheme('${curr.id }');" onclick="return confirm('确定关闭？');">关闭</a>
								      </c:if>
								      <a href="${pageContext.request.contextPath}/choseDissertation/choseResultList?themeId=${curr.id}&currpage=1">详情</a>
								      <c:if test="${curr.isOverCurrTime==1&&curr.state!=2}">
								      	
								        <a href="${pageContext.request.contextPath}/choseDissertation/noDissertationStudentList?attendanceTime=${curr.theYear}&currpage=1&themeId=${curr.id}">补选</a>
								      </c:if>  
								    </td>
									<%--<td >
									 <a href="javascript:void(0)">发布</a> 
									   <a
										href="javascript:void(0)"
										onclick="checkExpectDeadline('${curr.expectDeadline.time}','${curr.id }')">编辑</a>
										<a href="javascript:deleteOperationItemRest();"
										onclick="return confirm('确定删除？');">删除</a> <a
										href="${pageContext.request.contextPath}/ChoseResultList?themeId=${curr.id}&currpage=1">详情</a>
									</td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page">
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页 <a
							href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=1')"
							target="_self">首页</a> <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=${pageModel.previousPage}')"
							target="_self">上一页</a> 第<select
							onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
							<option
								value="${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
							<c:forEach begin="${pageModel.firstPage}"
								end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
								<c:if test="${j.index!=pageModel.currpage}">
									<option
										value="${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=${j.index}">${j.index}</option>
								</c:if>
							</c:forEach>
						</select>页 <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=${pageModel.nextPage}')"
							target="_self">下一页</a> <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/ChoseDissertationThemeList?currpage=${pageModel.lastPage}')"
							target="_self">末页</a>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!-- 下拉框的js -->
	<script
		src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
		type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var config = {
			'.chzn-select' : {
				search_contains : true
			},
			'.chzn-select-deselect' : {
				allow_single_deselect : true
			},
			'.chzn-select-no-single' : {
				disable_search_threshold : 10
			},
			'.chzn-select-no-results' : {
				no_results_text : '选项, 没有发现!'
			},
			'.chzn-select-width' : {
				width : "95%"
			}
		}
		 for( var selector in config) {
			$(selector).chosen(config[selector]);
		 }
		 function deleteChoseTheme(themeId){
	    	window.location.href="${pageContext.request.contextPath}/choseDissertation/deleteChoseTheme?themeId="+themeId;
	     }
	     function closeChoseTheme(themeId){
	    	window.location.href="${pageContext.request.contextPath}/choseDissertation/closeChoseTheme?themeId="+themeId;
	    }
	    $(function(){
	        // 期望的截止时间
	    	var expectDeadlines=$("input[name='expectDeadline']");
	    	//是否发布
		    var states=$("input[name='state']");
		    //获得大纲的id
		     var themeIds=$("input[name='themeId']");
	 	    //现在的时间
	    	var currTime=new Date();
	    	//初始化当前时间
	    	currTime.setHours(0);
	    	currTime.setMinutes(0, 0, 0);
	    	var oper=$("td[name='operator']");
	    	for(var i=0;i<expectDeadlines.length;i++){
	    		if(states.eq(i).val()==0){
	    			var expectDeadline=new Date(expectDeadlines.eq(i).val());
	    			expectDeadline.setHours(0);
	    	        expectDeadline.setMinutes(0, 0, 0);
		    		if(currTime.getTime()>expectDeadline.getTime()){
		    		    var content=oper.eq(i).html();
		    		    var t='<a href="${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=1&themeId='+themeIds.eq(i).val()+'">编辑</a>';
		    			oper.eq(i).html(content+t);
		    		}	
	    		}
	    	}
	    })
	</script>
	<!-- 下拉框的js -->
</body>
</html>
