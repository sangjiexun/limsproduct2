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
//弹出学生预约框
  function showUsersForChoseProfessor(){
	  layer.ready(function(){
	        layer.open({
	            type: 2,
	            title: '添加导师',
	            fix: true,
	            maxmin:true,
	            shift:-1,
	            closeBtn: 1,
	            shadeClose: true,
	            move:false,
	            maxmin: false,
	            area: ['1000px', '420px'],
	            content: '${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=1',
	            end: function(){
	            }
	        });
	    });
  }
  </script>
	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师论文互选</a></li>
		<li class="end"><a href="javascript:void(0)">导师论文互选列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
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
    <div class="title">
	  <div id="title">导师论文互选列表</div>
	    <a class="btn btn-new" href="${pageContext.request.contextPath }/choseDissertation/ChoseDissertationThemeList?currpage=1">返回</a>
		<a class="btn btn-new" href="javascript:void(0)" onclick="check()">下一步</a>
	</div>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/choseDissertation/ChoseResultList?&currpage=1" method="post" modelAttribute="choseTheme"><%--
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
			 
		--%></form:form>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>编号</th>
	    <th>导师编号</th>
	    <th>导师姓名</th>
	    <th >管理员设置论文数量</th>
	    <th >导师立题论文数量</th>
	    <th>立题论文列表</th> 
	    <!-- <th>题目列表</th> -->
	    <th>立题</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${choseProfessorList}" var="professor" varStatus="i">
	  <tr>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${professor.user.username}</td>
	    <td>${professor.user.cname}</td>
	    <td name="maxStudent">${professor.choseTheme.maxStudent}</td>
	    <td name="expectNumber">${professor.expectNumber}</td>
	    <td><a href="${pageContext.request.contextPath }/choseDissertation/dissertationList?currpage=1&professorId=${professor.id}">论文列表</a></td>
	    <!-- <td><a href="javascript:void(0)">论文列表</a></td> -->
	    <td><a href="${pageContext.request.contextPath }/choseDissertation/deleteChoseProfessorForAdmin?professorId=${professor.id}">删除</a></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=1&themeId=${themeId }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=${pageModel.previousPage}&themeId=${themeId }')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=${j.index}&themeId=${themeId}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=${pageModel.nextPage}&themeId=${themeId }')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/editChoseTheme?currpage=${pageModel.lastPage}&themeId=${themeId }')" target="_self">末页</a>
    </div>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		function deleteChoseProfessor(username){
			var flag=confirm("确定要删除吗？");
			if(flag){
				window.location.href="${pageContext.request.contextPath}/choseDissertation/deleteChoseProfessor?username="+username;
			}
		} 
		function check(){
			var flag=true;
			var maxStudentArray=$("td:[name='maxStudent']");
			var expectNumberArray=$("td:[name='expectNumber']");
			for(var i=0;i<maxStudentArray.length;i++){
				var maxStudent=maxStudentArray.eq(i).text();
				var expectNumber=expectNumberArray.eq(i).text();
				if(expectNumber<maxStudent){
					flag=false;
				}
				//${pageContext.request.contextPath }/choseDissertation/newChoseThemecreateBatch?id=${themeId}
			}
			if(flag){
				window.location.href="${pageContext.request.contextPath }/choseDissertation/newChoseThemecreateBatch?id=${themeId}";
			}
			else{
				alert("存在没有超过最低立题数量的导师")
			}
		}  
	</script>
	<!-- 下拉框的js -->
</body>
</html>
