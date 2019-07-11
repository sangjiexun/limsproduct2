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
    // 编辑卡号
    function editUserCard(card_id) {
        if ($("#cardNo_" + card_id).text() == "确定") {
            var newCard = $("#input_"+card_id).val();
            // 保存
            saveUserCard(card_id, newCard);
            $('#div_' + card_id).hide();
            $("#cardNo_" + card_id).text("点击修改：" + newCard);
            $(':button').removeAttr("disabled");
        }else {
            $('#div_' + card_id).show();
            $("#cardNo_" + card_id).text('确定');
            $(':button').attr("disabled", "true");
            $("#cardNo_" + card_id).removeAttr("disabled");
        }
    }

    // 保存卡号
    function saveUserCard(card_id, new_card_no) {
        var username = $("#username").val();
        if(new_card_no==null || new_card_no=="") {
            alert("请填写卡号！");
        }else {
            $.ajax({
                url:"${pageContext.request.contextPath}/system/saveCardBlind",
                type: 'POST',
                datatype: 'text',
                data: {username:username,cardno:new_card_no,cardId:card_id},
                complete:function(data){
                    // window.parent.location.reload();
                    // window.close();
                }
            })
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
                <td>${users.username}
                    <input value="${users.username}" type="hidden" id="username" /></td>
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
            <c:forEach var="curr" items="${userCards}" varStatus="i">
                <tr>
                    <th>卡号${i.count}</th>
                    <td>
                        <div id="div_${curr.id}" style="display: none; float:center"><input id="input_${curr.id}" type='text' value='${curr.cardNo}' style="width:150px;"></div>
                        <button id='cardNo_${curr.id}' class='btn btn-xs green' onclick="editUserCard('${curr.id}')" title='编辑' ><span class='glyphicon glyphicon'>点击修改：${curr.cardNo}</span></button></td>
                </tr>
            </c:forEach>
            <tr></tr>
        </table>
    </div>
</div>
</div>
</body>
</html>


