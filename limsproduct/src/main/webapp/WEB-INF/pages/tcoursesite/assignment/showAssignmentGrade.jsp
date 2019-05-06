 <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
 <jsp:useBean id="now" class="java.util.Date" />  
 <html >  
<head>
<meta name="decorator" content="iframe"/>
<!-- 富文本的css -->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css"/> 

<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery-1.7.1.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript">
	function checkUser(obj){
		if($(obj).val().trim()!=""){
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/teaching/coursesitetag/checkUser",
				data: {'username':$(obj).val().trim()},
				dataType:'json',
				success:function(data){
					if(!data){
						alert("该工号不存在，请查询后输入！");
						$(obj).val("");
					}
				}
			});
			
		}
	}
	
	function checkForm(){
		var now = ${tAssignment.TAssignmentGradings.size()};//已提交次数
		var total = ${tAssignment.TAssignmentControl.timelimit};//提交次数限制
		if(now >= total){
			alert("提交次数已达限制，无法再次提交！");
			return false;
		}
	}
</script>
</head>
<body>
	
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
		  	<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
							<div id="title">学生提交作业</div>
						</div> 
						
						<form:form action="${pageContext.request.contextPath}/teaching/assignment/saveTAssignmentGradeByTeacher" method="POST" modelAttribute="tAssignmentGrade" >
							<form:hidden path="accessmentgradingId"/>
							<form:hidden path="TAssignment.id"/>
							<div class="new-classroom">
								<table style="width: 60%;margin-top: 10px;">
									<tr>
										<td align="center" width="20%">作业标题：</td>
										<td align="left" width="30%">${tAssignment.title }</td>
										<td align="center" width="20%">最高分值：</td>
										<td align="left" width="30%">${tAssignment.TAssignmentAnswerAssign.score }</td>
										
									</tr>
									<tr>
										<td align="center">开始时间：</td>
										<td align="left">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${tAssignment.TAssignmentControl.startdate.time}" type="both"/>
										</td>
										<td align="center">截止时间：</td>
										<td align="left">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${tAssignment.TAssignmentControl.duedate.time}" type="both"/>
										</td>
									</tr>
									<tr>
										<td align="center">作业要求：</td>
										<td align="left" colspan="3">
											${tAssignment.content }
										</td>
									</tr>
									<tr>
										<td align="center">提交的作业：</td>
										<td align="left" colspan="3">
											${tAssignmentGrade.content }
										</td>
									</tr>
									<c:if test="${fn:contains(user.authorities,'TEACHER')&&flag!=0}">	
								        <c:if test="${tAssignmentGrade.userByGradeBy != null}">
											<tr>
												<td align="center">作业得分：</td>
												<td align="left">
													<font color="green">${tAssignmentGrade.finalScore }</font>
												</td>
												<td align="center">评语：</td>
												<td align="left">
													<font color="green">${tAssignmentGrade.comments }</font>
												</td>
											</tr>
										</c:if>
										<c:if test="${tAssignmentGrade.userByGradeBy == null}">
											<tr>
												<td colspan="4">
													<font color="red">尚未评分，无成绩！</font>
												</td>
											</tr>
										</c:if>
							        </c:if>
							        <c:if test="${fn:contains(user.authorities,'TEACHER')&&flag==0}">	
								        <c:if test="${tAssignmentGrade.TAssignment.TAssignmentControl.gradeToStudent == 'yes' }">
								        	<c:if test="${tAssignmentGrade.userByGradeBy != null}">
												<tr>
													<td align="center">作业得分：</td>
													<td align="left">
														<font color="green">${tAssignmentGrade.finalScore }</font>
													</td>
													<td align="center">评语：</td>
													<td align="left">
														<font color="green">${tAssignmentGrade.comments }</font>
													</td>
												</tr>
											</c:if>
											<c:if test="${tAssignmentGrade.userByGradeBy == null}">
												<tr>
													<td colspan="4">
														<font color="red">尚未评分，无成绩！</font>
													</td>
												</tr>
											</c:if>
								        </c:if>
								        <c:if test="${tAssignmentGrade.TAssignment.TAssignmentControl.gradeToStudent == 'no' }">
								        	该作业成绩不公布
								        </c:if>
							        </c:if>
									
								</table>
					 	        <form:hidden path="gradeTime"/>
					 	        <form:hidden path="userByGradeBy.username"/>
							</div>
							<div class="moudle_footer">
						        <div class="submit_link">
									<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
						        </div>
						    </div>
			
						</form:form>
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
<script type="text/javascript">
	//生成html编辑栏，设置宽度为888px
	var editor = new UE.ui.Editor({initialFrameWidth:800});
	editor.render("content");	 
</script>	
</body>


</html>