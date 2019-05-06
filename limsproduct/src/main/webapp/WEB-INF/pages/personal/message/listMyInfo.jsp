<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<html>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<meta name="decorator" content="iframe"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
<script type="text/javascript">
$(function(){
    //设置邮箱需要验证
    $("#email").validatebox();
})
function addRecords(){
         $("#repairRecords").window('open');      
    }

function checkMail(){
	var email = $("#email").val();
	if(email.trim()!=""){
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(email)){
			alert('您的邮箱格式不正确');
			return false;
		}
	}
}

//修改密码
function changePassword(){
    $("#changePassword").window('open');
}

//保存新密码
function savePassword(username){
    var password = document.getElementById("password").value;
    var newPassword = document.getElementById("newPassword").value;
    var checkNewPassword = document.getElementById("checkNewPassword").value;
    var reg = /^(\w){6,24}$/;
    //密码不能为空
    if (!password) {
        alert("原密码不能为空。");
    } else if (!newPassword) {
        alert("新密码不能为空。");
    } else if (!checkNewPassword) {
        alert("新密码确认不能为空。");
    } else {
        //判断新密码字符类型和长度
        if (!reg.exec(newPassword)) {
            alert("只允许输入字母和数字，输入长度为6~24位。");
        } else {
            //判断新密码和新密码确认是否相等
            if (newPassword != checkNewPassword) {
                alert("请重新输入新密码确认。");
            }
            else {
                //保存新密码
                $.ajax({
                    url: "${pageContext.request.contextPath}/personal/savePassword?username="+username+"&password="+password+"&newPassword="+newPassword,
                    type: "get",
                    success: function (result) {
                        if (result[0] == 'OK') {
                            window.parent.location.href = "${pageContext.request.contextPath}/pages/logout-front-redirect.jsp";
                        } else {
                            alert("原密码输入错误");
                        }
                    }
                });
            }
        }
    }

}
</script>
</head>
<body>
<div class="iStyle_RightInner">

<div class="navigation">
<div id="navigation">
<ul>
	<li><a href="javascript:void(0)">个人中心</a></li>
	<li class="end"><a href="${pageContext.request.contextPath}/personal/listMyInfo">我的资料</a></li>
</ul>
</div>
</div>


<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">
<div class="title">我的信息</div>
<table> 
<thead>
<tr>
<th>用户工号</th>
<th>用户姓名</th>
<th>用户身份</th>
<th>学院/部门</th>
<sec:authorize ifAnyGranted="ROLE_STUDENT" >
<th>专业</th>
<th>年级</th>
<th>班级</th>
</sec:authorize>
<th>电话</th>
<th>邮箱</th>
<th>操作</th>
</tr>
</thead>
<tbody>

<tr>
<td>${user.username}</td>
<td>${user.cname}</td>
<td>${str}</td>
<td>${user.schoolAcademy.academyName}</td>
<sec:authorize ifAnyGranted="ROLE_STUDENT" >
<td><%-- ${user.schoolMajor.majorName} --%></td>
<td>${user.grade}</td>
<td><%-- ${user.schoolClasses.className} --%></td>
</sec:authorize>
<td>${user.telephone}</td>
<td>${user.email}</td>
<td><a onclick="addRecords();">编辑</a>&nbsp;&nbsp;&nbsp;<a onclick="changePassword();">修改密码</a>&nbsp;&nbsp;&nbsp;
	<c:if test="${fn:contains('nwulims',PROJECT_NAME)}">
	<a target="_blank" href="${sessionScope.cms}/teacherInfo/${user.username}">个人简历</a></c:if></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
</div>

<div id="repairRecords" class="easyui-window" title="修改个人信息" modal="true" closed="true" iconCls="icon-add" style="width:1000px;height:200px">
<form:form action="${pageContext.request.contextPath}/personal/saveMyInfoNext" method="POST" onsubmit="return checkMail()" modelAttribute="user">
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="tool-box">
<table>
<tbody>	
			<tr>
			<form:hidden path="username" value=""/>
			</tr>
			<tr>
			<td class="label" valign="top"> 邮箱: </td>
			<td><form:input id="email"  path="email" validtype="email" invalidMessage="邮箱格式不正确,正确格式如xxx@xxx.xxx" /> </td>
			</tr>
			<tr>
			<td> &nbsp;</td><td></td>
			</tr>
			<tr>
			<td class="label" valign="top"> 电话: </td>
			<td><form:input id="username" path="telephone" class="easyui-numberbox" placeholder="仅可输入数字" /> </td>
			</tr>
</table>

</div>
<input id="save" type="submit" value="保存">
</div>
</div>
</div>
</div>
</form:form>

</div>

<%--修改密码--%>
<div id="changePassword" class="easyui-window" title="修改密码" modal="true" closed="true" iconCls="icon-add"
	 style="width:1000px;height:200px">
	<div class="right-content">
		<div id="" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="tool-box">
						<table>
							<tbody>
							<tr>
								<td class="label" valign="top"> 原密码:</td>
								<td><input id="password" name="password"/></td>
							</tr>
							<tr>
								<td> &nbsp;</td>
								<td></td>
							</tr>
							<tr>
								<td class="label" valign="top"> 新密码:</td>
								<td><input id="newPassword" name="newPassword"/></td>
							</tr>
							<tr>
								<td> &nbsp;</td>
								<td></td>
							</tr>
							<tr>
								<td class="label" valign="top"> 新密码确认:</td>
								<td><input id="checkNewPassword" name="checkNewPassword"/></td>
							</tr>
						</table>

					</div>
					<input type="submit" value="保存" onclick="savePassword('${user.username}');">
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
