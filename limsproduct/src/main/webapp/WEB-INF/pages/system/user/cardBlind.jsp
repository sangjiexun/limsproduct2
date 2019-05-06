<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta name="decorator" content="iframe" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
    function saveCardBlind(username){
        var cardNo = $("#cardNo").val();
        if(cardNo==null || cardNo=="") {
            alert("请填写卡号！");
        }else {
            $.ajax({
                url:"${pageContext.request.contextPath}/system/saveCardBlind",
                type: 'POST',
                datatype: 'text',
                data: {username:username,cardno:$("#cardNo").val()},
                complete:function(data){
                    window.parent.location.reload();
                    window.close();
                }
            })
        }
    }

    function EnterPress(){ //传入 event
        if (event.keyCode == 13) {
            saveCardBlind('${users.username}');
        }
    }
</script>
<div class="content-box">

    <!-- 导航栏 -->
    <div id="userInfo" >
<%--<form:form name="queryForm" method="post" modelAttribute="users">--%>
        <table>
            <tr>
                <th>学号/工号:</th>
                <td>${users.username}</td>
            <tr>
            <tr>
                <th>用户姓名:</th>
                <td>${users.cname}</td>
            </tr>
            <tr>
                <th>
                    学院/部门:</th>
                <td>${users.schoolAcademy.academyName}</td>
            </tr>
            <c:choose>
                <c:when test="${users.userRole==0}">
                    <tr>
                        <th>班级:</th>
                        <td>${users.schoolClasses.className}</td>
                    </tr>
                </c:when>
            </c:choose>
            <tr>
                <th>卡号:</th>
              <td><input id="cardNo" name="cardNo" value="${cardNo}" onkeypress="EnterPress();"></td>
            </tr>
            <tr>
                <td><a href="" onclick="saveCardBlind('${users.username}')">保存</a>
                 </td>
            </tr>
        </table>
       <%--</form:form>--%>
    </div>
</div>
</div>
</body>
</html>


