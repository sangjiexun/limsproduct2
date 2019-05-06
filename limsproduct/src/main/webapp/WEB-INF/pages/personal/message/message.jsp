	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<meta name="decorator" content="iframe" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
	<script type="text/javascript">
        $(function(){
            var s=${tage};
            if(1==s){
                $("#s1").addClass("TabbedPanelsTab selected");
                $("#s2").addClass("TabbedPanelsTab");
            }
            if(2==s){
                $("#s2").addClass("TabbedPanelsTab selected");
                $("#s1").addClass("TabbedPanelsTab ");
            }
        });
        $(function(){
            //设置邮箱需要验证
            $("#email").validatebox();
        })
		/*function addRecords(){
		 $("#repairRecords").window('open');
		 }*/

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
        //点击查看，后台获取消息内容
        function setMsgState(id){
            //	$("#state"+id).text("已处理");
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/setMsgStateNew?id="+id,
                async:false,
                data:{},
                dataType:'json',
                success:function(data){

                    // $("#magContent").html("<font size='2px;'>"+data[1]+"</font>");
                    // $("#msg").window('open');
                    //
                    // if(data[0]==0){
                    // 	$(".red_numb",parent.document).each(function(){
                    // 	$(this).text(Number($(this).text())-1);
                    // })
                    // }
                },
                error:function(){
                    alert("数据加载失败，请检查网络");
                }
            });
        }

	</script>

	<script langauge="javascript">
        //如果为查询则提交查询页面，如果为电子表格导出，则导出excel
        function subform(gourl){
            var gourl;
            form.action=gourl;
            form.submit();
        }
	</script>
	<script langauge="javascript">
        //个人信息修改
        function addRecords(){
            $("#repairRecords").window('open');
        }
        //考试
		/*function findTestList(labRoomId){//将labRoomId deviceNumber deviceName page传递到后台
		 var page = ${currpage};
		 var status = -1;
		 var url = "tcoursesite/teaching/test/labRoomTestListForStudent.jsp";
		 window.location.href=url;
		 }*/
        //取消查询
        function cancel(){
            window.location.href="${pageContext.request.contextPath}/personal/messageList?tage=${tage}&currpage=1";
        }
        function changeMessage(obj){
            var mId = $(obj).prev().val();
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/personal/changeMessage?mId="+mId,
                async:false,
                dataType:'json',
                success:function(){
                },
                error:function(){
                    console.log("改状态");
                }
            });
        }
	</script>
	<script type="text/javascript">
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
	<style>
		.tool-box input[type=text]{
			width:100px;
		}
		#form_act td span{
			white-space:nowrap;
		}
		.combo{
			margin: 0;
			position: relative;
			top: 2px;
			overflow: hidden;
		}
	</style>
</head>

<body>
<div class="iStyle_RightInner">

	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.my.workspace"/></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.personal.info"/></a></li>
			</ul>
			<%--<div class="personal_info">
				<font>我的信誉积分&nbsp;/&nbsp;${user.creditScore}</font>
				<sec:authorize ifAnyGranted="ROLE_STUDENT" >
					&lt;%&ndash;<font>我的准入考试&nbsp;/&nbsp;<a href="${pageContext.request.contextPath}/personal/examinationRecords" style="color:#0066ff;text-decoration:underline">${testRecords}次</a></font>&ndash;%&gt;
					<font>我的准入培训&nbsp;/&nbsp;<a href="${pageContext.request.contextPath}/personal/trainingRecords" style="color:#0066ff;text-decoration:underline">${trainingRecords}次</a></font>
				</sec:authorize>
				<font>手机&nbsp;/&nbsp;${user.telephone}</font>
				<font>邮箱&nbsp;/&nbsp;${user.email}</font>
			</div>--%>
		</div>
	</div>
</div>
	<div class="right-content">
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
									<c:if test="${(fn:contains('nwulims',PROJECT_NAME) || fn:contains('xzyxy',PROJECT_NAME)) && sessionScope.selected_role ne 'ROLE_STUDENT'}">
										<a href="${sessionScope.cms}editTeacherInfo?name=${user.username}&&cid=2" target="_blank">个人简历</a>
									</c:if>
									<a href="javascript:void(0)" onclick="addRecords();">编辑信息</a>
									<a href="javascript:void(0)" onclick="changePassword();">修改密码</a>
								</div>
							</div>
						</div>
					</div>
						<%--<div class="content-box">
                            <div class="title">我的信息</div>
                            <table>
                                <thead>
                                <tr>
                                    <th>工号/学号</th>
                                    <th>用户姓名</th>
                                    <th>英文名</th>
                                    <th>用户身份</th>
                                    <th>学院/部门</th>
                                    <sec:authorize ifAnyGranted="ROLE_STUDENT" >
                                        &lt;%&ndash;<th>专业</th>&ndash;%&gt;
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
                                    <td>${user.eNname}</td>
                                    <td>${str}</td>
                                    <td>${user.schoolAcademy.academyName}</td>
                                    <sec:authorize ifAnyGranted="ROLE_STUDENT" >
                                        &lt;%&ndash;<td> ${user.schoolMajor.majorName} </td>&ndash;%&gt;
                                        <td>${user.grade}</td>
                                        <td> ${user.schoolClasses.className} </td>
                                    </sec:authorize>
                                    <td>${user.telephone}</td>
                                    <td>${user.email}</td>
                                    <td><a onclick="addRecords();">编辑</a>&nbsp;&nbsp;&nbsp;<a onclick="changePassword();">修改密码</a>&nbsp;&nbsp;&nbsp;
                                        <c:if test="${fn:contains('nwulims',PROJECT_NAME) && sessionScope.selected_role ne 'ROLE_STUDENT'}">
                                            <a target="_blank" href="${sessionScope.cms}editTeacherInfo?name=${user.username}&&cid=2">个人简历</a></c:if></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>--%>
				</div>
			</div>
		</div>
	</div>
<!--消息内容弹出框-->
<%--<div id="msg" class="easyui-window" title="消息" closed="true" iconCls="icon-add" style="width:450px;">--%>
<%--<table class="tb" style="margin:10px"> --%>
<%--<tr id="magContent">--%>
<%----%>
<%--</tr>	--%>
<%--</table>--%>
<%--<br>--%>
<%--<button class="btn" onClick="location.reload();" style="margin:10px;">关闭</button>--%>
<%--</div>--%>



<div class="right-content">

	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<c:if test="${tage==1}">
				<li class="TabbedPanelsTab selected" id="s1"><a href="${pageContext.request.contextPath}/personal/messageList?currpage=1&tage=1">我的申请</a></li>
				<c:if test="${sessionScope.selected_role ne 'ROLE_STUDENT'}">
				<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/personal/messageList?currpage=1&tage=2">我的审核</a></li>
				</c:if>
				<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/personal/messageStatistics?tage=3">流程统计</a></li>
			</c:if>
			<c:if test="${tage==2}">
				<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/personal/messageList?currpage=1&tage=1">我的申请</a></li>
				<li class="TabbedPanelsTab selected" id="s2"><a href="${pageContext.request.contextPath}/personal/messageList?currpage=1&tage=2">我的审核</a></li>
				<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/personal/messageStatistics?tage=3">流程统计</a></li>
			</c:if>
		</ul>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="tool-box">
						<form:form id="form_act"  name="form" action="${pageContext.request.contextPath}/personal/messageList?tage=${tage}&currpage=1 " method="post" modelAttribute="message">
							<table>
								<tbody>
								<tr>
									<td class="label" valign="middle">
										<span style="float:left;" >消息人:</span>
										<form:input  path="sendUser" style="float:left;"/>
									</td>
									<td class="label" valign="middle" >
										<span style="float:left;" >消息标题:</span>
										<form:input path="title" style="float:left;"/>
									</td>
									<td class="label" valign="middle">
										<span style="margin:2px 0 0;">申请日期:</span>
										<input  class="easyui-datebox"  id="starttime" name="starttime" value="${starttime}"   onclick="new Calendar().show(this);" style="width:100px;" />
										<span style="position:relative;margin:0 2px;">到</span>
										<input class="easyui-datebox"  id="endtime" name="endtime"  value="${endtime}"   onclick="new Calendar().show(this);" style="width:100px;" />
									</td>
									<td>
										<input class="btn btn-new-new"  type="submit" value="查询"  />
										<input class="cancel-submit" type="button" onclick="cancel()" value="取消"/>
									</td>
								</tr>
								</tbody>
							</table>
						</form:form>
					</div>
				</div>
				<div id="contentarea">
					<div id="content">
						<div class="content-box">
							<table >
								<thead>
								<tr>
									<th>序号</th>
									<%--<th>❉</th>
                                    --%><th>消息人</th>
									<th>消息人单位</th>
									<th>消息标题</th>
									<th>发送时间</th>
									<th>消息状态</th>
									<th>操作</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${messages}" var="current"  varStatus="i">
									<tr>
										<td>${i.count}</td>
											<%--<td>
                                            <c:if test="${current.cond==0}">
                                            紧急
                                            </c:if>
                                            <c:if test="${current.cond==1}">
                                            一般
                                            </c:if>
                                            </td>
                                            --%><td>${current.sendUser}</td>
										<td>${current.sendCparty }</td>
										<td>${current.title }</td>
										<td><fmt:formatDate value="${current.createTime.time}" pattern="yyyy-MM-dd"/></td>
										<td>
											<c:if test="${current.messageState==0}">
												<font style="color: red">未读</font>
											</c:if>
											<c:if test="${current.messageState==1}">
												已读
											</c:if>
										</td>


										<td >
											<input type="hidden" value="${current.id}" id="oneInput"/>
											<label onclick="setMsgState(${current.id});">${current.content}</label>
												<%--<a onclick="setMsgState(${current.id});" href="javascript:void(0);">查看</a>--%>
											<%--<a onclick='deleteMessage(${current.id})'>删除</a>--%>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							<div class="page">
								共:${totalRecords}&nbsp;条记录 总页数:${pageModel.totalPage}&nbsp; <input type="hidden" value="${pageModel.totalPage}" id="totalpage" />
								当前页为 :${page}&nbsp; 转到第 ：<input type="text" class="gvsun-input" name="currpage" id="page" />&nbsp;页
								<a onclick="$('#form_act').attr('action','messageList?tage=${tage}&currpage='+turnPage()+'').submit();"><img src="${pageContext.request.contextPath}/images/icons/go.gif"  /></a> &nbsp;
								<a onclick="$('#form_act').attr('action','messageList?tage=${tage}&currpage=1').submit();">首页</a>
								<a onclick="$('#form_act').attr('action','messageList?tage=${tage}&currpage=${pageModel.previousPage}').submit();">上一页</a>
								<a onclick="$('#form_act').attr('action','messageList?tage=${tage}&currpage=${pageModel.nextPage}').submit();" >下一页</a>
								<a onclick="$('#form_act').attr('action','messageList?tage=${tage}&currpage=${pageModel.lastPage}').submit();">末页</a>
							</div>
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
    function deleteMessage(id){
        if(confirm("删除?"))
        {
            $.ajax({
                url:"${pageContext.request.contextPath}/deleteMessage?idKey="+id,
                type: 'POST',
                async: false,
                cache: false,
                contentType: false,
                processData: false,

                error: function(request) {
                    alert("请求错误");
                },
                success: function(data) {
                    parent.location.reload();
                }
            });

        }
    }
</script>
<!-- 下拉框的js -->
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
		<input id="save" type="submit" value="保存"/>
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
</body>
</html>