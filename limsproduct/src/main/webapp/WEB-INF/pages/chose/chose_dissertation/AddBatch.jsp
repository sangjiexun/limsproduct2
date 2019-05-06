<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	</head>
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">导师论文互选</a>
				</li>
				<li class="end"><a href="javascript:void(0)">备选导师论文列表</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="content-box">
						<div class="title">
							<label style="float: left">填写志愿:第${i }志愿</label>
							<a href="javascript:void(0);" onclick="check()" class="btn btn-return">下一步</a>
							<a href="${pageContext.request.contextPath }/choseDissertation/belongChoseThemeList?currpage=1"  class="btn btn-return">返回</a>
						</div>
						<div class="tool-box">
							<form:form name="queryForm" method="post" action="${pageContext.request.contextPath}/choseDissertation/toAddBatch?&i=${i}&currpage=1&themeId=${themeId }" modelAttribute="choseDissertation">
								 <ul>
								 	<li>
								         所属方向:
								 		<form:select class="chzn-select"   path="direction" style="width:80px">
										    <option value =""> 请选择</option>
										    <c:forEach items="${directions}" var="curr">
									          <form:option value="${curr.name}">${curr.id }:${curr.name }</form:option>
									       </c:forEach>
								         </form:select>
								 	</li>
								 	<li>
								    	立题题目:<form:input  path="tittle"/>
								    </li>
					  				<li>
								    	<input type="submit" value="查询">
								    </li>
					  			</ul>
							</form:form>
						</div>
						<table class="tb" id="my_show">
						  <thead>
						  <tr>
						   <th>序号</th>
						   <th>题目</th>
						    <th>所属方向</th>
						   <th>导师姓名</th>
						   <th>详情</th>
						   <th>操作</th>
						  </tr>
						  </thead>
						  <tbody>
						    <c:forEach items="${ChoseDissertationList}" var="curr" varStatus="i">
						  <tr>
						  	<input type="hidden" value="${curr.id}" name="recordId"/>
						    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
						    <td>${curr.tittle}</td>
						    <td>${curr.direction}</td>
						    <td>${curr.choseProfessor.user.cname}</td>
							<td><a href="javascript:void(0)" onclick="showChoseDissertation('${curr.id}')">详情</a></td>
						    <td>
						    	<input type="radio"  name="oper" value="${curr.id }"/>
						    </td>
						  </tr>
						  </c:forEach>  
						  </tbody>
						</table>
						<div class="page" >
					        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
					    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/toAddBatch?i=${i}&currpage=1&themeId=${themeId}')" target="_self">首页</a>			    
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/toAddBatch?i=${i}&currpage=${pageModel.previousPage}&themeId=${themeId }')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/choseDissertation/toAddBatch?i=${i}&currpage=${pageModel.currpage}&themeId=${themeId}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
					    <c:if test="${j.index!=pageModel.currpage}">
					    <option value="${pageContext.request.contextPath}/choseDissertation/toAddBatch?i=${i}&currpage=${j.index}&themeId=${themeId }">${j.index}</option>
					    </c:if>
					    </c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/toAddBatch?i=${i}&currpage=${pageModel.nextPage}&themeId=${themeId}')" target="_self">下一页</a>
					 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/toAddBatch?i=${i}&currpage=${pageModel.lastPage}&themeId=${themeId}')" target="_self">末页</a>
					    </div>
						<!-- 下拉框的js -->
						<script
							src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
							type="text/javascript"></script>
						<script
							src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
							type="text/javascript" charset="utf-8"></script>
						<!--layer弹框  -->
  						 <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
  						 <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
						 <script type="text/javascript">
							function targetUrl(url)
							  {
							    document.queryForm.action=url;
							    document.queryForm.submit();
							  }
							  function check(){
							  	var checked=$('input[name:"oper"]:checked');
							  	if(checked.length==0){
							  	  alert("请选择一位导师");
							  	}
							  	else{
							  		var value=checked.eq(0).val();
							  		window.location.href="${pageContext.request.contextPath}/choseDissertation/saveBatchInfoToSession?themeId=${themeId}&batchId=${batchId}&i=${i}&choseDissertationId="+value;
							  	}
							  }
							  //弹出论题的详情框
							  function showChoseDissertation(choseDissertationId){
								  layer.ready(function(){
								        layer.open({
								            type: 2,
								            title: '立题信息',
								            fix: true,
								            maxmin:true,
								            shift:-1,
								            closeBtn: 1,
								            shadeClose: true,
								            move:false,
								            maxmin: false,
								            area: ['1000px', '420px'],
								            content: '${pageContext.request.contextPath}/choseDissertation/showChoseDissertation?choseDissertationId='+choseDissertationId,
								            end: function(){
								            }
								        });
								    });
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