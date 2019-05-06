<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!doctype html>
<html lang="en">
<head>
    <title><spring:message code="title.html.name"/></title>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="decorator" content="none">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/school_select.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
</head>

<body>
<input type="hidden" id="pageContext" name="pageContext" value="${pageContext.request.contextPath }">
<div class="model_box">
    <div class="model_tit">选择学校</div>
    <div class="search_box_limit">
        <div class="search_box">
            <input type="text" placeholder="请输入学校名称" />
            <i class="fa fa-search search_btn" title="开始搜索"></i>
        </div>
    </div>
    <div class="school_content_limit">
        <div class="school_content scrollbar">
            <c:forEach items="${list}" var="current" varStatus="i">
                <label class="school_box" onclick="dataResource('${current.CNumber}')" ><div title="${current.CName}">${current.CName}</div></label>

            </c:forEach>

        </div>
    </div>
</div>
</body>
<script>
    function dataResource(value){
        addCookie('dataResource',value);
        window.location.href=$("#pageContext").val()+"/home";
    }
    function addCookie(objName,objValue){      //为父窗口添加cookie
        var str = objName + "=" +objValue;
        document.cookie = str;
    }
</script>

</html>