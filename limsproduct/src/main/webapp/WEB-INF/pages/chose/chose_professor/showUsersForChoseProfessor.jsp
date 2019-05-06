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
//添加
  function addToProfessor(){
       var array=new Array();
       var flag; //判断是否一个未选   
       $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为CK_name的 checkbox  
                   if ($(this).attr("checked")) { //判断是否选中    
                       flag = true; //只要有一个被选择 设置为 true  
                   }  
               })  
       if (flag) {  
          $("input[name='CK_name']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
                       if ($(this).attr("checked")) { //判断是否选中
                           array.push($(this).val()); //将选中的值 添加到 array中 
                       }  
                   })
       var myData = {
        	  "array":array.toString()
          }
       //alert(array);         
       //将要所有要添加的数据传给后台处理   
        $.ajax({
             type: "POST",
             url: "${pageContext.request.contextPath}/choseDissertation/addToProfessor?array="+array,
             //data: myData,
             /* dataType: "String", */
             success: function(data){
                      parent.window.location.reload();
                      var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                      parent.layer.close(index);
             		}
         });
         window.location.href="${pageContext.request.contextPath}/choseDissertation/ChoseProfessorListForCD?currpage=1"; 
       } else {   
       	alert("请至少选择一条记录");  
       }
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
	  <div id="title">导师互选列表</div>
	  <a class="btn btn-new" onclick="addToProfessor();">添加导师</a>
	</div>
	
	<div class="tool-box">
		<form:form name="queryForm" method="post" action="${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=1" modelAttribute="user">
			 <ul>
			 	<li>
			 		姓名:<form:input path="cname"/> 
			 	</li>
  				<li>
			    	<input type="submit" value="查询">
			    </li>
  			</ul>
		</form:form>
		<%--<form:form name="queryForm" action="${pageContext.request.contextPath}/ChoseResultList?themeId=${theme.id}&currpage=1" method="post" modelAttribute="choseTheme">
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
			 
		</form:form>--%>
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>编号</th>
	    <th>姓名</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${userForProfessor}" var="teacher" varStatus="i">
	  <tr>
	    <td>${teacher.username}</td>
	    <td>${teacher.cname}</td>
	    <td><input type="checkbox" name="CK_name" value="${teacher.username}"/></td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/choseDissertation/showUsersForChoseProfessor?currpage=${pageModel.lastPage}')" target="_self">末页</a>
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
</body>
</html>
