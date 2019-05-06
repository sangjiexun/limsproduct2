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
	if("${tip }"!=""){
		alert("${tip }");
	}

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
		if(now >= total&&total!=0){
			alert("提交次数已达限制，无法再进行保存或提交操作！");
			return false;
		}else{
			if($("#submitTime").val()==1&&now > 0){
				return confirm("此次提交会覆盖以前的提交记录，是否确认继续？");
			}
		}
	}
	
	function keepTAssignmentGrade(){//保存作业但不提交
		$.ajax({
			url:'${pageContext.request.contextPath}/teaching/assignment/keepTAssignmentGrade',
			type:'post',
			async:false,  // 设置同步方式
	        cache:false,
	        data:$('#myForm').serialize(),
			success:function(data){
				$("#assignmentGradingId").val();
				alert("作业已保存，请及时提交");
			}
		}); 
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
							<mytag:JspSecurity realm="check" menu="tAssignment">
								<a class="btn btn-new" href="${pageContext.request.contextPath}/teaching/assignment/assignmentGradingList?assignmentId=${tAssignment.id  }&flag=0" onclick="return checkSubmitNumber()">查看历史提交记录</a>
							</mytag:JspSecurity>
						</div> 
						<c:if test="${isGraded!=null }">
							<font color="red">该作业已被批改，无法再提交！</font>
						</c:if>
						<c:if test="${isGraded==null&&now>tAssignment.TAssignmentControl.duedate.time }">
							<font color="red">已超过截止时间，无法再提交！</font>
						</c:if>
						<form:form id="myForm" action="${pageContext.request.contextPath}/teaching/assignment/saveTAssignmentGrade" method="POST" modelAttribute="tAssignmentGrade" onsubmit="return checkForm()" enctype="multipart/form-data">
							<form:hidden path="accessmentgradingId" />
							<div class="new-classroom">
								<table style="width: 60%;margin-top: 10px;">
									<tr>
										<td align="center">作业标题：</td>
										<td align="left">${tAssignment.title }</td>
										<td align="center">最高分值：</td>
										<td align="left">${tAssignment.TAssignmentAnswerAssign.score }</td>
										
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
										<td align="center">状态：</td>
										<td align="left">
											<c:if test="${now>= tAssignment.TAssignmentControl.startdate.time&&now< tAssignment.TAssignmentControl.duedate.time}">
										                      开始
									       	</c:if>
									        <c:if test="${now<tAssignment.TAssignmentControl.startdate.time}">
											 	未到期
											</c:if>
											<c:if test="${now>tAssignment.TAssignmentControl.duedate.time}">
												已过期
											</c:if>
										</td>
										<td align="center">已提交次数/提交限制：</td>
										<td align="left">
											<c:if test="${tAssignment.TAssignmentGradings.size() == 0 }">
												<b><span style="color: red">未提交</span>
												/
												${tAssignment.TAssignmentControl.timelimit==0?'无限制':tAssignment.TAssignmentControl.timelimit }</b>
											</c:if>
											<c:if test="${tAssignment.TAssignmentGradings.size() != 0 }">
												<b><span style="color: green">${tAssignment.TAssignmentGradings.size() }</span>
												/
												${tAssignment.TAssignmentControl.timelimit==0?'无限制':tAssignment.TAssignmentControl.timelimit }</b>
											</c:if>
										</td>
									</tr>
								</table>
								<mytag:JspSecurity realm="update" menu="tAssignment">
								<c:if test="${tAssignment.TAssignmentControl.submitType==1 }">
									<fieldset>
									   	<div>
							     			<label>作业提交：</label>
							     			<textarea id="content" name="content" cols="120">${tAssignmentGrade.content }</textarea>
									 	</div>
								 	</fieldset>
								 	<fieldset>
									   	<div>
							     			<label style="margin-left: 16px">上传附件：</label>
					     					<input type="file" name="file">
					     				</div>
								 	</fieldset>
								</c:if>
								<c:if test="${tAssignment.TAssignmentControl.submitType==2 }">
									<fieldset>
									   	<div>
							     			<label>作业提交：</label>
							     			<textarea id="content" name="content" cols="120">${tAssignmentGrade.content }</textarea>
									 	</div>
								 	</fieldset>
								</c:if>
								<c:if test="${tAssignment.TAssignmentControl.submitType==3 }">
									<fieldset>
									   	<div>
							     			<label style="margin-left: 16px">上传附件：</label>
					     					<input type="file" name="file">
					     				</div>
								 	</fieldset>
								</c:if>
								</mytag:JspSecurity>
					 	        <form:hidden path="submitTime" id="submitTime"/>
					 	        <form:hidden path="submitdate"/>
					 	        <form:hidden path="TAssignment.id"/>
					 	        <form:hidden path="userByStudent.username"/>
							</div>
							
							<c:if test="${isGrade==null && now <= tAssignment.TAssignmentControl.duedate.time}">
								<div class="moudle_footer">
							        <div class="submit_link">
							        	<input class="btn" id="save" type="submit" value="提交" onclick="$('#submitTime').val(1);">
						        		<input class="btn" id="save" type="submit" value="保存" onclick="$('#submitTime').val(0);">
										<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
							        </div>
							    </div>
							</c:if>
			
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