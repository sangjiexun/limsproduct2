<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
<title></title>
<meta name="decorator" content="iframe"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
<style type="text/css" media="screen">
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
</style>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">

<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/labroom/editDevice.js"></script>
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->


<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript">
function cancel(){
	window.location.href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1";
}
</script>

<script>
    //定义全局变量
    var needAudit="${device.CDictionaryByIsStationAudit.id}";//预约是否需要审核
    var needtutor="${needtutor}";//是否需要系主任审核
    var needdean="${needdean}";//是否需要系主任审核
    var trainingCenterDirector="${trainingCenterDirector}";//是否需要实训中心主任审核
    var trainingDepartmentDirrector="${trainingDepartmentDirrector}";//是否需要实训中心主任审核
    var needlabadministrator="${needlabadministrator}";//是否需要实训室管理员审核
    var needAllowSecurityAccess="${device.CDictionaryByAllowSecurityAccess.id}";//是否需要安全准入trainType
    //var trainType="${device.CDictionaryByTrainType.id}";//培训形式
    var needAllAudits = "${needAllAudits}".replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
    //得到各个参数的值
    $(document).ready(function() {
        var needAllAuditStatus = "${needAllAuditStatus}".replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
        if(needAllAudits[0]) {
            for (var i = 0; i < needAllAudits.length; i++) {
                document.getElementById(needAllAudits[i] + needAllAuditStatus[i]).checked = true;
            }
        }
        $("#needAudit1").click(function(){
            needAudit=$("#needAudit1").val();
        });
        $("#needAudit2").click(function(){
            needAudit=$("#needAudit2").val();
        });
    })
    //是否需要审核的联动
    $(document).ready(function(){
        document.getElementById("isAudit").style.display="";
        if (${empty isAudit}) {//是否可以预约联动
//            document.getElementById("isAudit").style.display = "None";
            document.getElementById('needAudit1').checked = "";
            document.getElementById('needAudit2').checked = "";
//            $("#rdo1").removeAttr("checked");
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i]).style.display = "None";
                }
            }
//        document.getElementById("trainingType").style.display = "None";
        } else if(${isAudit == 1}){
            document.getElementById('needAudit1').checked = true;
        } else if(${isAudit == 2}){
            document.getElementById('needAudit2').checked = true;
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i]).style.display = "None";
                }
            }
        }


        $("#needAudit1").change(function(){
            // document.getElementById("teacher").style.display="";
            // document.getElementById("dean").style.display="";
            // document.getElementById("labManager").style.display="";
            // document.getElementById("trainingCenterDirector").style.display="";
            // document.getElementById("trainingDepartmentDirrector").style.display="";
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i]).style.display = "";
                }
            }
        });
        $("#needAudit2").change(function(){
            // document.getElementById("teacher").style.display="None";
            // document.getElementById("dean").style.display="None";
            // document.getElementById("labManager").style.display="None";
            // document.getElementById("trainingCenterDirector").style.display="None";
            // document.getElementById("trainingDepartmentDirrector").style.display="None";
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i]).style.display = "None";
                }
            }

        });
        if($("#needAudit2").prop("checked")){
            $("#needAudit2").change();
        }
    });
    function saveDeviceSettingRest() {
        var needAudit1=needAudit;//预约是否需要审核
        var realAllAudits = [];
        if(needAllAudits[0]) {
            for (var i = 0; i < needAllAudits.length; i++) {
                for (var j = 1; j < 3; j++) {
                    if (document.getElementById(needAllAudits[i] + j).checked) {
                        realAllAudits.push(document.getElementById(needAllAudits[i] + j).value);
                    }
                }
            }
        }
        if(needAudit==""){
            alert("请选择是否审核");
            return false;
        }
        if(!needAllAudits[0]) {
            realAllAudits = [0];
        }
        if(realAllAudits.length != needAllAudits.length && needAllAudits[0]){
            alert("请检查是否选完所有选项！");
            return false;
        }
        $.ajax({
            url:"${pageContext.request.contextPath}/device/saveLabRoomStationReserSetting/" + "${labRoomId}" + "/"+"${page}"+"/"+"${type}"+"/"
            + needAudit1+"/"+realAllAudits,
            type:'GET',
            async:false,
            error:function (request){
                alert('请求错误!');
            },
            success:function(data){
                if(data == "success"){
                    alert("保存成功");
                }else{
                    alert("保存失败");
                }
                var labRoomId = ${labRoomId};
                window.location.href="${pageContext.request.contextPath}/device/editLabRoomStationReserSetting/"+labRoomId+"/"+1+"/"+1;
                //var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                //parent.layer.close(index);
            }
        })
    }
function closeMyWindow(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
</script>

<style>
.tool-box2 th{text-align:left;}
table label{
position:relative;
top:-6px;
margin-left:3px;
}
</style>
</head>


<body>

<div class="right-content">	
	<%--<div class="tool-box1">
		<table class="equip_tab">
		    <tr>
				<td>
				    <span>设备编号：</span>
				    <p></p>
				</td>
				<td>
				    <span>设备名称：</span>
				    <p class="equip_name"></p>
				</td>
				<td>
				    <span>仪器型号：</span>
				    <p></p>
				</td>
			</tr>
			<tr>
				<td>
				    <span>所在实训室：</span>
				    <p></p>
				</td>
				<td>
				    <span>生产国别：</span>
				    <p></p>
				</td>
				<td>
				    <span>生产厂家：</span>
				    <p></p>
				</td>
			</tr>
		</table>
	</div>
	--%><div id="TabbedPanels1" class="TabbedPanels">
	 <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="openSetupLink(${device.id},${currpage },${type})">参数设置</a>
		</li>
		 <li class="TabbedPanelsTab selected" tabindex="0">
			 <a href="javascript:void(0);" onclick="openStationReserSetting(${device.id},${currpage },${type})">工位预约审核设置</a>
		 </li>
		 <c:if test="${device.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber=='1' && device.labRoomReservation.toString() == '1'}">
			 <li class="TabbedPanelsTab" tabindex="0">
				 <a href="javascript:void(0);" onclick="editLabRoomTrainingRest(${device.id},${currpage },${type})">准入管理</a>
			 </li>
		 </c:if>
         <c:if test="${device.labRoomReservation.toString() == '1'}">
             <li class="TabbedPanelsTab" tabindex="0">
                 <a href="javascript:void(0);" onclick="editLabRoomOpenSettingRest(${device.id},${currpage },${type})">开放设置</a>
             </li>
         </c:if>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomImageRest(${device.id},${currpage },${type})">相关图片</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomVideoRest(${device.id},${currpage },${type})">相关视频</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomDocumentRest(${device.id},${currpage },${type})">相关文档</a>
		</li>
<%--		<c:if test="${(device.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && device.CDictionaryByAllowSecurityAccess.CNumber == '1' && device.CDictionaryByTrainType.CNumber == '3')}">
		<li class="TabbedPanelsTab" tabindex="0">
		&lt;%&ndash;<a href="${pageContext.request.contextPath}/tcoursesite/question/findQuestionList?tCourseSiteId=${device.id}"target="_blank">题库</a>
		&ndash;%&gt;<a href="#" onclick="findLabRoomQuestionList(${device.id})">题库</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="#" onclick="findLabRoomTestList(${device.id})">考试</a>
		</li>
		</c:if>--%>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">
				<div id="title">工位预约审核层级设置</div>
				<a class="btn btn-new"  onclick="closeMyWindow()" >返回</a>
				</div>
					<form:form action="${pageContext.request.contextPath}/device/saveDeviceSetting" method="post" modelAttribute="device">
					<div class="new-classroom">
			<table id="radioTable">
			<%--<tr id="allowLending">
				<td>&lt;%&ndash;${device.CDictionaryByTrainType.id }&ndash;%&gt;是否允许借用:</td>
				<td>
				<c:forEach items="${CActives}" var="active" varStatus="i">
				<form:radiobutton path="CDictionaryByAllowLending.id" value="${active.id}" id="needLoan${i.count}" /><label for="needLoan${i.count}">${active.CName}</label>
				</c:forEach>										
				</td>
			</tr>--%>

				<%--<tr id="allowAppointment">
					<td>是否允许预约:</td>
					<td>
						<c:forEach var="xx" begin="1" end="2" step="1">
							<c:if test="${xx == 1 }">
								<form:radiobutton path="labRoomReservation" value="${xx}" id="appointment${xx}"/><label for="appointment${xx}">是</label>
							</c:if>
							<c:if test="${xx == 2 }">
								<form:radiobutton path="labRoomReservation" value="${xx}" id="appointment${xx}"/><label for="appointment${xx}">否</label>
							</c:if>
						</c:forEach>
					</td>
				</tr>--%>
			<tr id="isAudit">
				<td>预约是否需要审核:</td>
				<td>
				<c:forEach items="${CActives}" var="activ" varStatus="i">
				<%-- <form:radiobutton id="needAudit${i.count}" path="CActiveByIsAudit.id" value="${activ.id}"  /><label for="needAudit${i.count}">${activ.name}</label> --%>
				<form:radiobutton id="needAudit${i.count}" path="CDictionaryByIsStationAudit.id" value="${activ.id}"  /><label for="needAudit${i.count}">${activ.CName}</label>
				</c:forEach>
				</td>
			</tr>
				<c:forEach items="${needAllAudits}" var="needAllAudit" varStatus="i">
					<tr id="${needAllAudit}">
						<td>是否需要${authNames[i.count-1]}审核:</td>
						<td>
							<input type="radio" name="${needAllAudit}" id="${needAllAudit}1" value="1"/>
							<label for="${needAllAudit}1">是</label>
							<input type="radio" name="${needAllAudit}" id="${needAllAudit}2" value="2"/>
							<label for="${needAllAudit}2">否</label>
						</td>
					</tr>
				</c:forEach>

			<%--<tr id="allowSecurityAccess">
				<td>是否需要安全准入:</td>
				<td>
				<c:forEach items="${CActives}" var="activ" varStatus="i">
				&lt;%&ndash; <form:radiobutton path="CActiveByAllowSecurityAccess.id" value="${activ.id}" id="needAllowSecurityAccess${i.count}"/>${activ.name} &ndash;%&gt;
				<form:radiobutton path="CDictionaryByAllowSecurityAccess.id" value="${activ.id}" id="needAllowSecurityAccess${i.count}"/>${activ.CName}
				</c:forEach>								
				</td>
			</tr>--%>

                <%--安全准入流程梳理隐掉--%>
			<%--<tr id="trainingType">
				<td>培训形式:</td>
				<td>
				<c:forEach items="${CTrainingTypes}" var="type" varStatus="i">
				&lt;%&ndash; <form:radiobutton path="CTrainingType.id" value="${type.id}" id="trainType${i.count}"/><label for="trainType${i.count}">${type.name}</label> &ndash;%&gt;
				<form:radiobutton path="CDictionaryByTrainType.id" value="${type.id}" id="trainType${i.count}"/><label for="trainType${i.count}">${type.CName}</label>
				</c:forEach>								
				</td>
			</tr>--%>
			
			</table>
			</div>
			</form:form>
				<%--<div class="TabbedPanels" id="selectAcademy">
					<table class="tab_lab" style="margin:10px 0 0;">
						<tr>
							<th>开放范围</th>
							<td width="90%">
								<select class="chzn-select" multiple id="selectedSchoolAcademy"
										name="selectedSchoolAcademy">
									<c:forEach items="${schoolAcademyList}" var="schoolAcademy" varStatus="i">
										<c:if test="${selectedSchoolAcademies.contains(schoolAcademy)}">
											<option value="${schoolAcademy.academyNumber}"
													selected>${schoolAcademy.academyName}</option>
										</c:if>
										<c:if test="${!selectedSchoolAcademies.contains(schoolAcademy)}">
											<option value="${schoolAcademy.academyNumber}">${schoolAcademy.academyName}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</div>--%>
				<div style="width: 50px; margin: 20px auto">
					<input type="button" onclick="saveDeviceSettingRest(${device.id});" value="确定">
				</div>
			</div>
		</div>
	  </div>
	  		<input type="hidden" id="labRoomId" value="${labRoomId }">
            <input type="hidden" id="page" value="${page }">
		<input type="hidden" id="type" value="${type}">
            <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
	</div>
</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
		charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chzn-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
