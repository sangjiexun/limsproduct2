<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
    <meta name="decorator" content="iframe"/>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        //取消
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/labSecurityCheck?currpage=1";
        }

    </script>
</head>


<body>

<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
            <li><a href="javascript:void(0)"><spring:message code="left.safety.inspection"/></a></li>
            <li class="end"><a href="javascript:void(0)">新建</a></li>
        </ul>
    </div>
</div>
<form:form name="subForm" action="${pageContext.request.contextPath}/newChecks" method="post">
    <table style="width:85%;align:center">
        <tr>
            <br/>
            <td>
                <select id="types" name="types" class="chzn-select">
                    <option value="1">一类(涉化)</option>
                    <option value="2">二类(机械)</option>
                    <option value="3">三类(文化)</option>
                </select>
            </td>
            <td>时间：${time}
                <input class="Wdate" id="month" name="month" type="text"
                       <%--value="<fmt:formatDate value="${month.time}" pattern="MM"/>"--%>
                       value="${curMonth}"
                       onclick="WdatePicker({dateFmt:'MM'})" />月
                </td>
            <td>学院：${academyName}</td>
            <td>实验中心：
                <select id="labCenter" name="labCenter" class="chzn-select">
                    <c:forEach items="${labCenters}" var="l">
                        <option value="${l.id}">${l.centerName}</option>
                    </c:forEach>
                </select>
            </td>
            <td>检查人：${cname}</td>
            <td>&nbsp&nbsp&nbsp</td>
            <td>
                <input type="submit" value="确定"/>
                <input type="button" value="取消" onclick="cancel();"/>
            </td>
        </tr>

    </table>
</form:form>
</body>
</html>
