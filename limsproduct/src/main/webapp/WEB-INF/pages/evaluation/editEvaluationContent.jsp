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
	<style>
		#save{
			/*float: none;*/
			/*padding:4px 30px;*/
			/*background: #77bace;*/
			/*border-radius: 3px;*/
		}
		.popup_btns .btn{
			/*margin:0 10px 0 0;*/
		}
	</style>
</head>
<body>
	<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">评教管理</a></li>
			<li class="end"><a href="javascript:void(0)">新建</a></li>
			
		</ul>
	</div>
  	</div>
  	<div class="right-content">
  		<div id="TabbedPanels1" class="TabbedPanels">
  			<div class="TabbedPanelsContentGroup">
  				<div class="TabbedPanelsContent">
  					<div class="content-box">
  						<div class="title">
					      	<div id="title">新建评教项目</div>
					  	</div>
					  	<form:form action="${pageContext.request.contextPath}/evaluation/saveEvaluationContent" method="POST" modelAttribute="evaluationContent">
							<table class="tab_lab">
								<tr>
									<th><form:hidden path="id"/>所属学期</th>
									<td>
										<form:select path="schoolTerm.id" class="chzn-select">
											<c:forEach items="${schoolTerms}" var="curr">
												<form:option value="${curr.id}">${curr.termName}</form:option>
											</c:forEach>
										</form:select>
									</td>
									<th>单项内容</th>
									<td>
										<form:input path="options" required="true"/>
									</td>
									<th>单项分值</th>
									<td>
										<form:input path="scores" required="true"/>
									</td>
								</tr>
								<tr>
									<th>备注</th>
									<td colspan="5">
										<form:textarea path="memo" rows="5"></form:textarea>
									</td>
								</tr>
							</table>
							<div class="popup_btns">
								<input class="btn-big" id="save" type="submit" value="确定">
								<input class="btn-return" type="button" value="返回" onclick="window.history.go(-1)">
							</div>
							<%--<div class="new-classroom">
					  			<fieldset>
					  				<form:hidden path="id"/>
					  				<label>所属学期：</label>
					  				<form:select path="schoolTerm.id" class="chzn-select">
									    <c:forEach items="${schoolTerms}" var="curr">
									    	<form:option value="${curr.id}">${curr.termName}</form:option>
									    </c:forEach>
								    </form:select>
					  			</fieldset>
					  			<fieldset>
					  				<label>单项内容：</label>
					  				<form:input path="options" required="true"/>
					  			</fieldset>
					  			<fieldset>
					  				<label>单项分值：</label>
					  				<form:input path="scores" required="true"/>
					  			</fieldset>
					  			<fieldset>
					  				<label>备注：</label>
					  				<form:input path="memo" />
					  			</fieldset>
					  		</div>
					  		<div class="moudle_footer">
						      	<div class="submit_link">
						      		<input class="btn" id="save" type="submit" value="确定">
						      		<input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
						      	</div>
						  	</div>--%>
					  	</form:form>
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