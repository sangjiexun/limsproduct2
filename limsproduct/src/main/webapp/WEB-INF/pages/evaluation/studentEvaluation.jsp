<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<html>
<head>
	<meta name="decorator" content="iframe"/>
	<!-- 下拉框的样式 -->
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  	<!-- 下拉的样式结束 -->
	<script type="text/javascript">
		// 计算总评分
		function sum(ids){
			var strs=ids.split("*");
			var info=0;
			for(var i=0;i<strs.length;i++){
				var selValue=$("#value"+strs[i]).val();
				info=parseInt(info)+parseInt(selValue);
			}
			$("#sumScore").empty();
			$("#sumScore").append("单项评分(总分="+info+")");
		};
		
		// 提交
		function commitInfo(ids){
			var info = "";
			var strs = ids.split("*");
			for(var i=0;i<strs.length;i++){
				if(i==0){
					continue;
				}
				var selValue = $("#value"+strs[i]).val();
				info += strs[i]+"-"+selValue+"~";
			}
			location.href="${pageContext.request.contextPath}/evaluation/saveStudentEvaluation?infos="+info+"&idKey="+${evaluationSource.id };
		}
	</script>
</head>
<body>
	<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">评教管理</a></li>
			<li class="end"><a href="javascript:void(0)">学生评教</a></li>
			
		</ul>
	</div>
  	</div>
  	<div class="right-content">
  		<div id="TabbedPanels1" class="TabbedPanels">
  			<div class="TabbedPanelsContentGroup">
  				<div class="TabbedPanelsContent">
  					<div class="content-box">
  						<div class="title">
					      	<div id="title">学生评教</div>
					  	</div>
					  	<c:set var="totalId" value="0"></c:set>
					  	<form:form name="sourceForm" method="POST" modelAttribute="evaluationSource">
					  		<div class="new-classroom">
					  			<form:input path="id" type="hidden" value="${evaluationSource.id}" />
					  			<div>
					  				<label>课程名称：</label>
					  				<form:input path="schoolCourseInfo.courseNumber" type="hidden" readonly="true"/>
					  				<label>${evaluationSource.schoolCourseInfo.courseName }</label>
					  				<label>上课教师：</label>
					  				<form:input path="user.cname" type="hidden" readonly="true"/>
					  				<label>${evaluationSource.user.cname }</label>
					  			</div>
					  			<div class="content-box">
					  				<table>
					  					<thead>
					  						<tr>
					  							<th>序号</th>
					  							<th>评教内容</th>
					  							<th id="sumScore">单项评分(总分=0)</th>
					  						</tr>
					  					</thead>
					  					<tbody>
					  						<c:forEach items="${evaluationContents}" var="curr" varStatus="i">
					  						<c:set var="totalId" value="${totalId}+'*'+${curr.id}"></c:set>
					  							<tr>
					  								<td>${i.count}</td>
					  								<td>${curr.options}</td>
					  								<td>
														<select id="value${curr.id }" onchange="sum('${ids }')" style="width: 50px;" >
															<option value="0">0</option>
															<c:forEach begin="0" end="${curr.scores-1 }" varStatus="i">
																<option value="${i.count}">${i.count}</option>
															</c:forEach>
														</select>
													</td>
					  							</tr>
					  						</c:forEach>
					  					</tbody>
					  				</table>
					  			</div>
					  		</div>
					  	</form:form>
					  	<label class="btn btn-new" onclick="commitInfo(${totalId})" >提交</label>

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
  					</div>
  				</div>
  			</div>
  		</div>
  	</div>
</body>
</html>