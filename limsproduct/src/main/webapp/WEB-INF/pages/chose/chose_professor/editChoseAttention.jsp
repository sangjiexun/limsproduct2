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
  
  <script type="text/javascript">
  //提交表单
  function submitForm(){
    if($.trim($("#tittle").val())=="")
    {
      alert("请填写标题！");
      return false;
    }
    if($.trim($("#content").val())=="")
    {
      alert("请填写内容！");
      return false;
    }
    if($("#type").val()=="")
    {
      alert("请填写类型！");
      return false;
    }
    if($("#theme_tittle").val()=="")
    {
      alert("请填写所属校区！");
      return false;
    }
   
    document.center_form.action="${pageContext.request.contextPath}/nwuChose/saveChoseAttention";
    document.center_form.submit();
  }
  </script>
	<style>
		.content-box .textarea_info{
			width:96%;
		}
		.textarea_info textarea{
			box-sizing: border-box;
			width: 99%;
			height: auto;
			resize: none;
			padding: 4.5px 10px;
			margin: 0 0.5%;
			outline:none;
		}
	</style>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">注意事项</a></li>
			<li class="end"><c:if test="${!isEdit}"><a href="javascript:void(0)">编辑</a></c:if>
			                <c:if test="${isEdit}"><a href="javascript:void(0)">新建</a></c:if></li>
			
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">注意事项<c:if test="${!isEdit}">编辑</c:if><c:if test="${isEdit}">新建</c:if></div>
	</div>
	<form:form name="form" action="${pageContext.request.contextPath}/nwuChose/saveChoseAttention" method="POST" modelAttribute="choseAttention">
	<div class="new-classroom">
	    
	    <form:hidden path="id"/>
	  <fieldset>
	    <label>标题：</label>
	    <form:input path="tittle" class="easyui-validatebox" required="true"/>
	  </fieldset>

		<fieldset>
			<label>类型：</label>
			<form:select id="type" path="type" class="chzn-select" required="true"  onchange="return checkIf()">
				<form:option value="">请选择:</form:option>
				<form:option value="1">导师制互选导师</form:option>
				<form:option value="2">导师制互选学生</form:option>
				<form:option value="3">论文制互选导师</form:option>
				<form:option value="4">论文制互选学生</form:option><%--
	      <c:forEach items="${choseAttentions}" var="currThe">
	      	<form:option value="${currThe.type}">${currThe.type}</form:option>
	      </c:forEach>
        --%></form:select>
		</fieldset>
	  <fieldset class="textarea_info">
	    <label>内容：</label>
		  <form:textarea path="content" class="easyui-validatebox" required="true" rows="5"></form:textarea>
	  </fieldset>
	 <%--  <fieldset>
	    <label>所属大纲</label>
	    <form:select id="theme_tittle" path="choseTheme.id" class="chzn-select" required="true" >
	     <form:option value="${choseTheme.id}">请选择</form:option>
	      <c:forEach items="${choseThemes}" var="currThe">
	      	<form:option value="${currThe.id}">${currThe.tittle}</form:option>
	      </c:forEach>
        </form:select>
	  </fieldset> --%>
	  <%--
	  <fieldset>
        <label>所属院（系、部）：</label>
        <form:select path="schoolAcademy.academyNumber" class="chzn-select" required="true">
          <form:option value="${user.schoolAcademy.academyNumber}">${user.schoolAcademy.academyName}</form:option>
          <c:forEach items="${schoolAcademies}" var="sa">
            <form:option value="${sa.academyNumber}">${sa.academyName}</form:option>
          </c:forEach>
        </form:select>
      </fieldset>
	  
	--%></div>
	<div class="moudle_footer">
        <div class="submit_link">
          <%--<input class="btn" id="save" type="button" onclick="submitForm();" value="确定">
             --%><input class="btn" id="save" type="submit" onclick="return ifSubmit()" value="保存">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
	
	<!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	var flag;
		function checkIf(){
			var options=$("#type option:selected"); 
			var type=options.val();
			$.ajax({
				url:"${pageContext.request.contextPath}/nwuChose/checkIfRepeatType?type="+type,
				type:"post",
				success:function(result){
					flag = result != "error";
					
				}
			});
		}
		function ifSubmit(){
			if(!flag){
				alert("该类型的注意事项已经存在");
			}
		  return flag;
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
