 <%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
 <jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">立题</a></li>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">立题</div>
	</div>
	<form:form name="form" action="${pageContext.request.contextPath }/choseDissertation/saveDissertationForAdmin?professorId=${professorId }" method="post" modelAttribute="choseDissertation" >
	<div class="new-classroom">
	  <form:hidden path="choseProfessor.choseTheme.id"/> 
	  <fieldset>
	    <label>题目：</label>
	  	<form:input path="tittle" id="tittle" class="easyui-validatebox" required="true"/>
	  </fieldset>
	  <fieldset>
	    <label>内容简介：</label>
	  	<form:input path="content" id="content" class="easyui-validatebox" required="true"/>
	  </fieldset>
	   <!-- <fieldset>
	    <label>完成时间：</label>
	  	<input class="easyui-datebox" id="finishTime"  name="finishTime"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
	  </fieldset>  -->
	  
	  <fieldset>
	    <label>所属方向：</label>
	    <form:select path="direction">
	    	<c:forEach items="${directionList }" var="curr">
	    		<form:option value="${curr.name }">${ curr.name}</form:option>
	    	</c:forEach>
	    </form:select>
	  </fieldset>
     <fieldset>
     <label>备注：</label>
  	 <form:input path="remark" id="remark" class="easyui-validatebox" required="true"/>
     </fieldset>
	  </div>
	<div class="moudle_footer">
        <div class="submit_link">
            <input class="btn" id="save" type="submit" value="保存" >
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
	
	<!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<!-- 下拉框的js -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
