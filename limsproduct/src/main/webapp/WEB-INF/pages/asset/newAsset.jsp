<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	  var oldFlag='<%=request.getAttribute("flag")%>';
	  if(oldFlag == 0){
		  document.getElementById("assetLimit").style.display="None";
	  }
  });
  
  // 字段显示切换
  	function display(){
  		var needLimit  = $("#flag").val();
  		if(needLimit == 0){
  			document.getElementById("assetLimit").style.display="None";
  		}else{
  			document.getElementById("assetLimit").style.display="";
  		}
  	}
  </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>  
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
		    <li><a href="javascript:void(0)"><spring:message code="left.material.dictionary"/></a></li>
		    <li><c:if test="${isEdit eq 1 }"><a href="javascript:void(0)">编辑药品字典</a></c:if></li>
			<li><c:if test="${isEdit eq 0 }"><a href="javascript:void(0)">新建药品字典</a></c:if></li>
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">
      	<c:if test="${isEdit eq 0 }">新建药品信息(星号<span style="color:red">*</span>为必填项)</c:if>
      	<c:if test="${isEdit eq 1 }">编辑药品信息(星号<span style="color:red">*</span>为必填项)</c:if>
      </div>
	</div>
	<form:form action="${pageContext.request.contextPath}/asset/saveAsset" method="POST" modelAttribute="asset">
	<div class="new-classroom"> 
		<fieldset>
	 	 <form:hidden path="id"/> 
	    	<label>药品名称<span style="color:red">*</span></label>
	   	 <form:input path="chName" required="true"/>
	  	</fieldset>
	  	<fieldset> 
			<label style="width:300px;">规格/型号<span style="color:red;text-transform:lowercase;">（规范填写：500g/瓶）</span></label>
	   	 <form:input path="specifications"/>
	  	</fieldset>
	  	<fieldset> 
	    	<label>计量单位</label>
	   	 <form:input path="unit"/>
	  	</fieldset>
	  	<fieldset> 
	    	<label>CAS号</label>
	   	 <form:input path="cas"/>
	  	</fieldset>
	  	<fieldset> 
	    	<label>药品类型<span style="color:red">*</span></label>
	   	 <form:select path="category" class="chzn-select" id="category" required="true"> 
	   	 	<form:option value="0">试剂</form:option>
	   	 	<form:option value="1">耗材</form:option>
		</form:select>
	  	</fieldset>
	  	<fieldset> 
	    	<label>库存提醒<span style="color:red">*</span></label>
	   	<form:select path="flag" class="chzn-select" id="flag"  onchange="display()" required="true"> 
		<form:option value="0">不需要</form:option>
		<form:option value="1">需要</form:option>
		</form:select>
	  	</fieldset>
		<fieldset>
			<label>实验中心<span style="color:red">*</span></label>
			<form:select path="centerId" class="chzn-select" id="centerId" required="true">
				<form:option value="">请选择</form:option>
				<c:forEach items="${labCenterList}" var="curr">
					<form:option value="${curr.id}">${curr.centerName}</form:option>
				</c:forEach>
			</form:select>
		</fieldset>
	  <fieldset id="assetLimit">
	    <label>提醒下限</label>
	    <form:input path="assetLimit" placeholder="请填写数字"
        onkeyup="value=value.replace(/[^\d]/g,'') "
        onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
	  </fieldset>   
	</div>
	<div class="moudle_footer">
        <div class="submit_link">
          <input class="btn" id="save" type="submit" value="确定">
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
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
