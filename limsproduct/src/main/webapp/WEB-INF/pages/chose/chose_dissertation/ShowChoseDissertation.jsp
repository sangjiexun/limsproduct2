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
    parent.window.location.reload();
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
  }
  </script>
</head>
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">论文互选</a></li>
		<li class="end"><a href="javascript:void(0)">论文互选列表</a></li>
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
	  <div id="title">论文互选列表</div>
	  <a class="btn btn-new" onclick="cancel();">返回</a>
	</div>
	<form name="form" action="${pageContext.request.contextPath }/savaThemeChoseOneStep" >
   	 <div>
   		<fieldset>
	    	<label>题目: </label>
	  		<input value="${choseDissertation.tittle }"  readonly/>
	    </fieldset>
		<fieldset>
	    	<label> 内容简介: </label>
   		    <textarea  readonly>${choseDissertation.content }</textarea>
	    </fieldset>
   		
	    <fieldset>
	    	<label> 导师邮箱:</label>
   		   <input value="${choseDissertation.choseProfessor.user.email }"  readonly/>
	    </fieldset>
	     <fieldset>
	    	<label> 导师联系电话:</label>
   		   <input value="${choseDissertation.choseProfessor.user.telephone }"  readonly/>
	    </fieldset>
	    <fieldset>
	    	<label> 备注:</label>
	    	<textarea  readonly>${choseDissertation.remark }</textarea>
   		 </fieldset>
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
	<!-- 下拉框的js -->
</body>
</html>
