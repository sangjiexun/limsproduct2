<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
</head>
<body>
 <div id="windowheight" class="rel">
  <script type="text/javascript">
  $(function() {
		$("#windowheight").height($(window).height() / 1);
	});
  </script>
	<div style="overflow:hidden;height:100%;">	
    <iframe src="<c:url value="${cmmonDocument.getDocumentUrl()}"/>"
            style="width:100%;height:100%;border:none;">
    </iframe>
    </div>
    
  

    </div>
</body>
</html>