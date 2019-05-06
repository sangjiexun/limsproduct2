<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
  <head>
  <meta name="decorator" content="iframe" />
      <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
  <style>
  .tool-box input[type=text]{
	width:100px!important;}
  </style>
<script type="text/javascript">
$(function(){
	var s=${tage};
	 if(1==s){
	 $("#s1").addClass("TabbedPanelsTab selected");
	  $("#s2").addClass("TabbedPanelsTab");
	  $("#s3").addClass("TabbedPanelsTab");
	 }
	  if(2==s){
	 $("#s2").addClass("TabbedPanelsTab selected");
	 $("#s1").addClass("TabbedPanelsTab ");
	 $("#s3").addClass("TabbedPanelsTab ");
	 }
	  if(3==s){
	 $("#s3").addClass("TabbedPanelsTab selected");
	 $("#s1").addClass("TabbedPanelsTab ");
	 $("#s2").addClass("TabbedPanelsTab ");
	 }
});
</script>
<script type="text/javascript">
$(function(){
    //设置邮箱需要验证
    $("#email").validatebox();
})
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
</script>
<script langauge="javascript">
//个人信息修改
function addRecords(){
    $("#repairRecords").window('open');      
}
function changePassword(){
    $("#changePassword").window('open');
}
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

      <div class="right-content">
          <div class="iStyle_RightInner">
              <div class="navigation">
                  <div id="navigation">
                      <ul>
                          <li><a href="javascript:void(0)"><spring:message code="left.my.workspace"/></a></li>
                          <li class="end"><a href="javascript:void(0)"><spring:message code="left.personal.info"/></a></li>
                      </ul>
                  </div>
              </div>
          </div>
       </div>
       <c:if test="${!fn:contains('zjcclims',PROJECT_NAME)}">
           <div id="TabbedPanels1" class="TabbedPanels">
               <div class="TabbedPanelsContentGroup">
                   <div class="TabbedPanelsContent">
                       <div class="user_box">
                           <img src="${pageContext.request.contextPath}/images/user_head.jpg">
                           <div class="user_text">
                               <div class="userinfo_top">${user.cname}
                                   <font>${user.eNname}</font>
                                   <lable>${user.username}&nbsp;/&nbsp;${user.schoolAcademy.academyName}</lable>
                                   <div class="ut_right">
                                       <font>我的信誉积分&nbsp;/&nbsp;<span>${user.creditScore}</span></font>
                                       <sec:authorize ifAnyGranted="ROLE_STUDENT" >
                                           <%--<font>我的准入考试&nbsp;/&nbsp;<a href="${pageContext.request.contextPath}/personal/examinationRecords">${testRecords}</a>次</font>--%>
                                           <font>我的准入培训&nbsp;/&nbsp;<a href="${pageContext.request.contextPath}/personal/trainingRecords">${trainingRecords}</a>次</font>
                                       </sec:authorize>
                                       <font>手机&nbsp;/&nbsp;<span>${user.telephone}</span></font>
                                       <font>邮箱&nbsp;/&nbsp;<span>${user.email}</span></font>
                                   </div>
                               </div>
                               <div class="userinfo_btm">
                                   <div class="ub_left">
                                       <sec:authorize ifAnyGranted="ROLE_STUDENT" >
                                           <%--<div>${user.schoolMajor.majorName}</div>专业--%>
                                           <div>${user.grade}年级&nbsp;/&nbsp;${user.schoolClasses.className}班	</div>
                                       </sec:authorize>
                                       <div class="identity_lable">【&nbsp;${str}&nbsp;】</div>
                                   </div>
                                   <div class="cog">
                                       <c:if test="${fn:contains('nwulims',PROJECT_NAME) && sessionScope.selected_role ne 'ROLE_STUDENT'}">
                                           <a href="${sessionScope.cms}editTeacherInfo?name=${user.username}&&cid=2" target="_blank">个人简历</a>
                                       </c:if>
                                       <a href="javascript:void(0)" onclick="addRecords();">编辑信息</a>
                                       <a href="javascript:void(0)" onclick="changePassword();">修改密码</a>
                                   </div>
                               </div>
                           </div>
                       </div>
                   </div>
               </div>
           </div>
       </c:if>
       <div id="repairRecords" class="easyui-window" title="修改个人信息" modal="true" closed="true" iconCls="icon-add">
           <form:form action="${pageContext.request.contextPath}/personal/saveMyInfo?tage=${tage}" method="POST" onsubmit="return checkMail()" modelAttribute="user">
               <table class="msg_edit">
                   <tr style="display: none;">
                       <form:hidden path="username" value=""/>
                   </tr>
                   <tr>
                       <th>英文名 </th>
                       <td>
                           <form:input type="text" id="eNname" path="eNname"/>
                       </td>
                   </tr>
                   <tr>
                       <th>邮箱 </th>
                       <td>
                           <form:input type="email" id="email"  path="email" validtype="text" invalidMessage="邮箱格式不正确,正确格式如xxx@xxx.xxx" />
                       </td>
                   </tr>
                   <tr>
                       <th>电话 </th>
                       <td>
                           <form:input type="tel" id="telephone" path="telephone" class="easyui-numberbox"  />
                       </td>
                   </tr>
               </table>
               <input id="save" type="submit" value="保存" style="margin:0 242px 0 0;">
           </form:form>
       </div>
      <%--修改密码--%>
      <div id="changePassword" class="easyui-window" title="修改密码" modal="true" closed="true" iconCls="icon-add">
          <table class="msg_edit">
              <tr>
                  <th>原密码</th>
                  <td>
                      <input type="password" id="password" name="password"/>
                  </td>
              </tr>
              <tr>
                  <th>新密码</th>
                  <td>
                      <input type="password" id="newPassword" name="newPassword"/>
                  </td>
              </tr>
              <tr>
                  <th>新密码确认</th>
                  <td>
                      <input type="password" id="checkNewPassword" name="checkNewPassword"/>
                  </td>
              </tr>
          </table>
          <input class="save" type="submit" value="保存" onclick="savePassword('${user.username}');">
      </div>
  <!--消息内容弹出框-->
<div id="msg" class="easyui-window" title="消息" closed="true" iconCls="icon-add" style="width:450px;">
	<table class="tb" style="margin:10px"> 
	<tr id="magContent">
	
	</tr>	
	</table>
	<br>
	<button class="btn" onClick="location.reload();" style="margin:10px;">关闭</button>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/personal/messageList?currpage=1&tage=1">我的申请</a></li>
        <c:if test="${sessionScope.selected_role ne 'ROLE_STUDENT'}">
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/personal/messageList?currpage=1&tage=2">我的审核</a></li>
        </c:if>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/personal/messageStatistics?tage=3">流程统计</a></li>
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">			
<div id="contentarea">
<div id="content">
<div class="content-box">
	<table >
			<thead>
				<tr>
					<th>办理状</th>
					<th>今天</th>
					<th>本月</th>
					<th>本年</th>
				</tr>
			</thead>
					<tbody>
		<c:forEach items="${messages}" var="current"  varStatus="i">	
        	<tr>
            	<td>${current[0]}</td>
            	<td>${current[1]}</td>
            	<td>${current[2]}</td>
            	<td>${current[3]}</td>
         	</tr>
        </c:forEach>
      	</tbody>
	</table>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>