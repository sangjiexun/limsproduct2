<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_LeftList.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_Searcher.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/is_SystemUI_Allpages.css"/>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
</head>

<div class="navigation">
<div id="navigation">
	<ul>
		<li><a href="javascript:void(0)">实验设备管理</a></li>
		<li class="end"><a href="javascript:void(0)">借用单审核</a></li>
	</ul>
</div>
</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="content-box">

<div class="title">
	<div id="title">审核借用单</div>
</div>   	
    		 		
<div class="tool-box1">
<table cellpadding="0" cellspacing="0" id="viewTable">
	<tr>
				<th>申请日期</th><td><fmt:formatDate value="${lrdl.lendingTime.time }" pattern="yyyy-MM-dd"/></td>
				<th>预归还日期</th><td><fmt:formatDate value="${lrdl.returnTime.time }" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th>借用人</th><td>${lrdl.userByLendingUser.cname }</td>
				<th>指导老师</th><td>${lrdl.userByTeacher.cname }</td>
			</tr>
			<tr>
				<th>借用内容</th><td>${lrdl.content }</td>
			</tr>
</table>
	</div>
	
	<div class="content-box">
            <div class="title">
                <div id="title">借用清单</div>
            </div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                        <th>借用设备</th>
                        <th>所属实验室</th>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${lends}" var="reservation" varStatus="i">
                		<tr>
                        <td>
							<c:if test="${reservation.stage >= 0}">
								<input name="CK" type="checkbox"
									   value="${reservation.id}" checked="checked"/>
							</c:if>
							<c:if test="${reservation.stage < 0}">
								<span style="color: red">已拒绝</span>
							</c:if>
						</td>
                        <td>${reservation.labRoomDevice.schoolDevice.deviceName}(${reservation.labRoomDevice.schoolDevice.deviceNumber})</td>
                        <td>${reservation.labRoomDevice.labRoom.labRoomName}</td>
                        </tr>
                        </c:forEach>
                       
                </tbody>
            </table>
       </div> 
	
<div id="TabbedPanels1" class="TabbedPanels">
	<!-- 标题栏  -->
	  <ul class="TabbedPanelsTabGroup">
	   		<li class="TabbedPanelsTab" tabindex="0">
				<a href="${pageContext.request.contextPath}/device/auditDepartmentDeviceLending?idKey=${idKey }">系主任审核</a>
			</li>
			
			<li class="TabbedPanelsTab selected" tabindex="0">
				<a href="${pageContext.request.contextPath}/device/auditLabRoomAdminDeviceLending?idKey=${idKey }">实训室管理员审核</a>
			</li>
		
			<li class="TabbedPanelsTab"  tabindex="0">
				<a href="${pageContext.request.contextPath}/device/auditLabRoomHeadDeviceLending?idKey=${idKey }">实训部主任审核</a>
			</li>
		<a class="btn btn-new" href="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1">返回查看我的设备借用申请</a>
			<a class="btn btn-new" href="${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1">返回查看我的审核结果</a>
	  </ul>
	  
	 <!--内容栏  --> 
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<c:if test="${fn:contains(lrdl.labRoomDevice.labRoom.labRoomAdmins,user.username) &&lrdl.stage==1}">
					<div class="title">实训室管理员意见</div>
						<form:form id="edit_form"
						 action="" method="POST">
							<table cellpadding="0" cellspacing="0" id="viewTable">
								<tr>
									<th>审核人</th><td>${user.cname }</td>
								</tr>
								<tr>
									<th>审核结果<br><font color="blue">如果需要拒绝，请借用设备清单前面的打勾去掉</font></th>
									<td> 
									<select>
										<option value="615">审核通过</option>
									</select></td>
								</tr>
								<tr>
									<th>备注</th><td><input type="textarea" name="remark" /></td>
									</tr>
							</table>
							<input type="button" value="确定" onclick="saveAudit()"/>
        					<input type="button" value="取消" onclick="window.history.go(-1);" />
						</form:form>
				</c:if>
			
					<table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter" >
					    <tr><td colspan="6">实训室管理员信息 </td></tr>
					    <c:forEach items="${lrdl.labRoomDevice.labRoom.labRoomAdmins}" var="admin">
					 	<tr align="center" >
						     <td>姓名：   ${admin.user.cname}</td> 
						     <td>工号：   ${admin.user.username}</td> 
						     <td>部门：   ${admin.user.schoolAcademy.academyName}</td> 
						     <td>联系方式：   ${admin.user.telephone}</td> 
						</tr>  
					    </c:forEach>
					</table>
				<c:if test="${lrdl.stage>1||lrdl.stage==-3}">
				<table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter">
					<tr><td colspan="6" ><font color="blue">实训室管理员审核</font> </td></tr>
					<tr>
						<td>审核意见：通过  </td>
					</tr>
				</table>
				</c:if>
				<c:if test="${lrdl.stage==-2}">
					<table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter">
						<tr><td colspan="6" ><font color="blue">实训室管理员审核</font> </td></tr>
						<tr>
							<td>审核意见：<font color="red">拒绝</font>  </td>
						</tr>
					</table>
				</c:if>
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
    
    function saveAudit(){
    	var array_ok=new Array();// 同意
    	var array_refuse=new Array();// 拒绝
    	$("input[name='CK']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox  
            if ($(this).attr("checked")) { //判断是否选中    
            	array_ok.push($(this).val()); //将选中的值 添加到 array中 
            }else{
            	array_refuse.push($(this).val()); //将选中的值 添加到 array中 
            }
        });
        
        $.ajax({
            url:'${pageContext.request.contextPath}/device/saveLabRoomAdminDeviceLending?array_ok='
            		+ array_ok + '&array_refuse=' + array_refuse,
            type:'POST',
           data:$('#edit_form').serialize(),
           error:function (request){
             alert('请求错误!');
           },
           success:function(){
        	   location.href='${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1';
           }         
     	});
    }
</script>
<!-- 下拉框的js -->
	<script type="text/javascript">
        $(document).ready(function () {
            if(${noLabRoomDeviceLending eq 1}){
                alert("该借用已撤回");
                window.history.back();
            }
        });
	</script>
</div>
</div>
</div>
</div>
</body>
</html>