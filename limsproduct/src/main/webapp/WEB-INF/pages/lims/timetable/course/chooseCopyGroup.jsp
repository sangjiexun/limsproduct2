<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<html>
<head>
    <meta name="decorator" content="none"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <title>选择分组</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" >
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
    <style>
        #chooseGroup{
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
        #chooseGroupForm{
            position: relative;
            top:50%;
            margin:-88px 0 0 0;
            text-align: center;
        }
        .form-inline{
            display:inline-block;
        }
        .ig_tit{
            font-size: 21px;
            font-family: simhei;
            color: #3e454c;
            margin: 0 0 15px;
        }
        .input-group .bootstrap-select>.dropdown-toggle{
            border-radius: 4px;
        }
        .btn_cog{
            margin: 21px 0 0;
        }
        .btn_cog .btn{
            color:#fff;
            background: #77bace;
            padding: 6px 20px;
        }
        .btn_cog .btn:hover{
            opacity: 0.9;
        }
    </style>
</head>
<body>
<!-- 选择分组 -->
<div id="chooseGroup">
    <form id="chooseGroupForm" name="chooseGroupForm" action="copyTimetableGroup" method="post">
        <input type="hidden" name="batchId" id="batchId" value="${batchId}"/>
        <input type="hidden" name="sourceId" id="sourceId" value="${sourceId}"/>
        <input type="hidden" name="courseNo" id="courseNo" value="${courseNo}"/>
        <input type="hidden" name="termId" id="termId" value="${termId}"/>
        <div class="form-inline">
            <div class="form-group">
                <label class="sr-only">需复制到的分组</label>
                <div class="input-group">
                    <div class="ig_tit">需复制到的分组</div>
                    <select class="selectpicker" name="destinationId" id="destinationId" data-live-search="true" title="请选择">
                        <c:forEach items="${groups}" varStatus="i" var="group">
                            <option value="${group.id}">${group.groupName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="btn_cog">
            <input class="btn" type="submit" value="确定"/>
        </div>
    </form>
</div>

</body>
</html>
