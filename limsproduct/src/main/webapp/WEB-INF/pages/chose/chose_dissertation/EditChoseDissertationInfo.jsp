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
 
  </script>
<script type="text/javascript">
</script>	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师论文互选</a></li>
		<li class="end"><a href="javascript:void(0)">备选导师论文列表</a></li>
	  </ul>
	</div>
  </div>
   <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
    <a class="btn btn-new" href="javascript:window.history.go(-1)" >返回</a>
  </div>
   <form name="form" action="${pageContext.request.contextPath }/choseDissertation/savaChoseDissertation" method="post">
   	 <div>
   	 	<fieldset>
	   	 <label>编号：</label>
		 <input value="${choseDissertation.id }" name="id"/>
		 </fieldset>
   		<fieldset>
	    	<label>题目: </label>
	  		<input value="${choseDissertation.tittle }" name="tittle"/>
	    </fieldset>
		<fieldset>
	    	<label> 内容简介: </label>
   		    <textarea name="content">${choseDissertation.content }</textarea>
	    </fieldset>
   		 <fieldset>
	    	<label> 所属学部:</label>
	    	<select name="department">
	    		<option>请选择</option>
	    		<c:forEach items="${teachingDepartment }" var="curr">
	    			<c:if test="${curr==choseDissertation.department }">
	    				<option value="${curr}" selected>${curr}</option>
	    		    </c:if>
	    		    <c:if test="${curr!=choseDissertation.department }">
	    				<option value="${curr }">${curr}</option>
	    		    </c:if>
	    		</c:forEach>
	    	</select>
	    </fieldset>
	    <fieldset>
	    	<label> 所属方向:</label>
	   		   <select name="direction">
		    		<option>请选择</option>
		    		<c:forEach items="${directions }" var="curr">
		    			<c:if test="${curr.name==choseDissertation.direction }">
		    				<option value="${curr.name }" selected>${curr.name }</option>
		    		    </c:if>
		    		    <c:if test="${curr.name!=choseDissertation.direction }">
		    				<option value="${curr.name }">${curr.name }</option>
		    		    </c:if>
		    		</c:forEach>
		    	</select>
	    </fieldset>
	    <fieldset>
	    	<label> 备注:</label>
	    	<textarea name="remark">${choseDissertation.remark }</textarea>
   		 </fieldset>
   	</div>
   	<div class="moudle_footer">
        <div class="submit_link">
          <input type="submit" value="确定"/>
        </div>
	</div>
   </form>   
  </div>
  </div>
  </div>
</div>
</div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		 function check(){
		 	//获得开始和结束时间
		    var expectStartline= $('#expectStartline').datebox('getValue');
			var expectDeadline= $('#expectDeadline').datebox('getValue');
			var startTime=new Date(expectStartline);
			var endTime=new Date(expectDeadline);
			if(endTime<startTime){
				alert("开始时间超过结束时间");
			}
			else{
				//获得所填的届
				var theYear=$("#theYear").val();
				$.ajax({
					url:"${pageContext.request.contextPath}/nwuChose/checkUncloseSameOutline?theYear="+theYear,
					type:"post",
					success:function(result){
						if(result==0){
							//跳转到关闭和删除大纲的页面
							var flag=confirm("存在同届的大纲，需要手动操作大纲");
							if(flag){
							   window.location.href="${pageContext.request.contextPath}/nwuChose/ChoseThemeList?currpage=1";
							}
						}
						else{
							document.form.submit();//进入新建大纲的下一步
						}
					}
				});
			}
		} 
	</script>
	<!-- 下拉框的js -->
</body>
</html>
