<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >  
<head>
<meta name="decorator" content="iframe"/>  
<title><fmt:message key="html.title"/></title>

  
  <!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
<script type="text/javascript">
$(function () {
    window.parent.location.reload();
    parent.layer.closeAll('iframe');
});
</script>
</head>
<body>
<h1>保存成功,关闭中...</h1>
</body>

</html>