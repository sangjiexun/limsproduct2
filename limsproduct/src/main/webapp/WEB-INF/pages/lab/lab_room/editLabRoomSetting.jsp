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

	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript">
function cancel(){
	window.location.href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1";
}
</script>
	<style type="text/css">
		.chzn-choices {
			border: none!important;
		}
		.content-double {
			overflow: visible;
		}
	</style>
<script>
//定义全局变量
var appointment = "${device.labRoomReservation}";//是否允许预约
//var needLoan="${device.CDictionaryByAllowLending.id}";//是否允许出借
var needAudit="${device.CDictionaryByIsAudit.id}";//预约是否需要审核
var needtutor="${needtutor}";//是否需要系主任审核
var needdean="${needdean}";//是否需要系主任审核
var trainingCenterDirector="${trainingCenterDirector}";//是否需要实训中心主任审核
var trainingDepartmentDirrector="${trainingDepartmentDirrector}";//是否需要实训中心主任审核
var needlabadministrator="${needlabadministrator}";//是否需要实训室管理员审核
var needAllowSecurityAccess="${device.CDictionaryByAllowSecurityAccess.id}";//是否需要安全准入trainType
//var trainType="${device.CDictionaryByTrainType.id}";//培训形式
var needAllAudits = "${needAllAudits}".replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
//得到各个参数的值
$(document).ready(function(){
	<%--var needAllAuditsStr = "${needAllAudits}";--%>
	<%--var needAllAuditStatusStr = "${needAllAuditStatus}";--%>
	<%--needAllAuditsStr  =needAllAuditsStr.replace(/\[|]/g,'');--%>
    <%--needAllAuditsStr   =   needAllAuditsStr.replace(/\s+/g,"");--%>
    <%--needAllAuditStatusStr  =needAllAuditStatusStr.replace(/\[|]/g,'');--%>
    <%--needAllAuditStatusStr   =   needAllAuditStatusStr.replace(/\s+/g,"");--%>
	<%--var needAllAudits = needAllAuditsStr.split(",");--%>
	<%--var needAllAuditStatus = needAllAuditStatusStr.split(",");--%>
    var needAllAuditStatus = "${needAllAuditStatus}".replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
    if(needAllAudits[0]) {
        for (var i = 0; i < needAllAudits.length; i++) {
            document.getElementById(needAllAudits[i] + needAllAuditStatus[i]).checked = true;
        }
    }
    <%--document.getElementById("needtutor${needtutor}").checked = true;--%>
    <%--document.getElementById("needdean${needdean}").checked = true;--%>
    <%--document.getElementById("needlabadministrator${needlabadministrator}").checked = true;--%>
    <%--document.getElementById("trainingCenterDirector${trainingCenterDirector}").checked = true;--%>
    <%--document.getElementById("trainingDepartmentDirrector${trainingDepartmentDirrector}").checked = true;--%>

    $("#appointment1").click(function () {
        appointment = $("#appointment1").val();
    });
    $("#appointment2").click(function () {
        appointment = $("#appointment2").val();
    });
	// $("#needLoan1").click(function(){
	// 	needLoan=$("#needLoan1").val();
	// });
	// $("#needLoan2").click(function(){
	// 	needLoan=$("#needLoan2").val();
	// });
	$("#needtutor1").click(function(){
		needtutor=$("#needtutor1").val();
	});
	$("#needtutor2").click(function(){
		needtutor=$("#needtutor2").val();
	});
	$("#needdean1").click(function(){
		needdean=$("#needdean1").val();
	});
	$("#needdean2").click(function(){
		needdean=$("#needdean2").val();
	});
	$("#needAudit1").click(function(){
		needAudit=$("#needAudit1").val();
	});
	$("#needAudit2").click(function(){
		needAudit=$("#needAudit2").val();
	});
	$("#needlabadministrator1").click(function(){
		needlabadministrator=$("#needlabadministrator1").val();
	});
	$("#needlabadministrator2").click(function(){
		needlabadministrator=$("#needlabadministrator2").val();
	});
	$("#trainingCenterDirector1").click(function(){
		trainingCenterDirector=$("#trainingCenterDirector1").val();
	});
	$("#trainingCenterDirector2").click(function(){
		trainingCenterDirector=$("#trainingCenterDirector2").val();
	});
	$("#trainingDepartmentDirrector1").click(function(){
		trainingDepartmentDirrector=$("#trainingDepartmentDirrector1").val();
	});
	$("#trainingDepartmentDirrector2").click(function(){
		trainingDepartmentDirrector=$("#trainingDepartmentDirrector2").val();
	});
	$("#needAllowSecurityAccess1").click(function(){
		needAllowSecurityAccess=$("#needAllowSecurityAccess1").val();
	});
	$("#needAllowSecurityAccess2").click(function(){
		needAllowSecurityAccess=$("#needAllowSecurityAccess2").val();
	});
	
	//$("#trainType1").click(function(){
		//trainType=$("#trainType1").val();
	//);
	$("#trainType2").click(function(){
		trainType=$("#trainType2").val();
	});
	$("#trainType3").click(function(){
		trainType=$("#trainType3").val();
	});
	
	/*$("#isAuditTimeLimit0").change(function(){
		isAuditTimeLimit=$("#isAuditTimeLimit0").val();
	});
	$("#isAuditTimeLimit1").change(function(){
		isAuditTimeLimit=$("#isAuditTimeLimit1").val();
	});*/

});

//保存参数设置
function saveDeviceSettingRest(id,type){//将labRoomId deviceNumber deviceName page传递到后台
//	var needLoan1=needLoan;//是否允许出借
    var appointment1 = appointment;//是否允许预约
	var needAudit1=needAudit;//预约是否需要审核
	var needtutor1=needtutor;//是否需要导师审核
	var needdean1=needdean;//是否需要系主任审核
	var needlabadministrator1=needlabadministrator;//是否需要实训室管理员审核
	var trainingCenterDirector1=trainingCenterDirector;//是否需要实训中心主任审核
	var trainingDepartmentDirrector1=trainingDepartmentDirrector;//是否需要实训部主任审核
	var needAllowSecurityAccess1=needAllowSecurityAccess;//是否需要安全准入
	//var trainType1=trainType;//培训形式
	//var isAuditTimeLimit1=isAuditTimeLimit;//是否有预约审核时间限制
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
    // var academies = $("#selectedSchoolAcademy").val();
    //
    // if(academies == undefined || academies.length == 0){
    //     academies = ["-1"];
    // }
    // if(needLoan==""){
    // 	needLoan1=-1;
    // }
    if(!needAllAudits[0]) {
        realAllAudits = [0];
    }
    if(realAllAudits.length != needAllAudits.length && needAllAudits[0]){
        alert("请检查是否选完所有选项！");
        return false;
	}
    // if(appointment1==1 && needAudit ==621 && needtutor==""){
    // 	alert("请选择是否导师审核");
    // 	return false;
    // }
    // if(appointment1==1 && needAudit ==621 && needdean==""){
    // 	alert("请选择是否系主任审核");
    // 	return false;
    // }
    // if(appointment1==1 && needAudit ==621 && trainingCenterDirector==""){
    // 	alert("请选择是否实验中心主任审核");
    // 	return false;
    // }
    // if(appointment1==1 && needAudit ==621 && trainingDepartmentDirrector==""){
    // 	alert("请选择是否实验部主任审核");
    // 	return false;
    // }
    // if(appointment1==1 && needAudit ==621 && needlabadministrator==""){
    //     alert("请选择是否实训室管理员审核");
    //     return false;
    // }
    if(appointment1==1) {
        document.getElementById("isAudit").style.display = "";
        // document.getElementById("selectAcademy").style.display="";
        document.getElementById("allowSecurityAccess").style.display="";
    }
    if(appointment1==1 && needAudit==""){
    	alert("请选择是否审核");
    	return false;
    }
    if(appointment1==1 && needAudit==622){
    	if(needlabadministrator==""){
    		needlabadministrator1=-1;
    	}
    	if(needtutor==""){
    		needtutor1=-1;
    	}
    	needdean1 = 2;
    	trainingCenterDirector1 = 2;
    	trainingDepartmentDirrector1 = 2;
    }

    if (appointment == "") {
        alert("请选择是否预约");
        return false;
    }
    if(appointment1==1 && needAllowSecurityAccess==""){
    	alert("请选择是否安全准入");
    	return false;
    }
    //准入形式判断隐去
    //if(appointment1==1 && needAllowSecurityAccess == 621 && trainType==""){
    //	alert("请选择准入形式");
    //	return false;
    //}
    //if(appointment1==1 && needAllowSecurityAccess == 622){
    	//trainType1 = -1;
    //}

    if( appointment == 2){
//        needAudit1 = -1;
        needtutor1 = -1;
        needdean1 = -1;
        trainingCenterDirector1 = -1;
        trainingDepartmentDirrector1 = -1;
        needlabadministrator1 = -1;
        // needadministrator1 = -1;
        // isAuditTimeLimit1 = -1;
        needAllowSecurityAccess1 = -1;
        //trainType1 = -1;
        academies = ["-1"];
    }

    if (needAudit == 622) {
        needtutor1 = -1;
        needdean1 = -1;
        trainingCenterDirector1 = -1;
        trainingDepartmentDirrector1 = -1;
        needlabadministrator1 = -1;
        // needadministrator1 = -1;
    }

    //if (needAllowSecurityAccess == 622 ) {
        //trainType1 = -1;
    //}
    //alert(needAllowSecurityAccess);
    var data1 = JSON.stringify({
        "labRoomId": ${labRoomId},
        "page": ${page},
        "type": ${type},
        "isAppointment": appointment1,
        "needAudit": needAudit1,
        "realAllAudits": realAllAudits,
        "needAllowSecurityAccess": needAllowSecurityAccess1,
        "flag": ${flag},
//        "needLoan":needLoan1,
    });
	$.ajax({
		<%--url:"${pageContext.request.contextPath}/device/saveLabRoomSettingRest/" + "${labRoomId}" + "/"+"${page}"+"/"+"${type}"+"/"+needLoan1+"/"--%>
				<%--+ needAudit1+ "/"+needAllowSecurityAccess1+"/" +appointment1+"/"+realAllAudits,--%>
        url:"${pageContext.request.contextPath}/device/saveLabRoomSettingRest",
        type:'POST',
        data:data1,
        contentType: "application/json;charset=UTF-8",
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
            window.location.href="${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+1+"/"+1;
			//var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			//parent.layer.close(index);
		}
	})
}


//是否需要审核的联动
$(document).ready(function(){
        document.getElementById("isAudit").style.display="";
    if (${allowAppointment==2}) {//是否可以预约联动
        //document.getElementById("allowLending").style.display = "None";
        document.getElementById("isAudit").style.display = "None";
        // document.getElementById("manager").style.display="None";
        // document.getElementById("teacher").style.display = "None";
        // document.getElementById("dean").style.display = "None";
        // document.getElementById("labManager").style.display = "None";
        // document.getElementById("trainingCenterDirector").style.display = "None";
        // document.getElementById("trainingDepartmentDirrector").style.display = "None";
        //document.getElementById("selectAcademy").style.display="None";
        if(needAllAudits[0]) {
            for (var i = 0; i < needAllAudits.length; i++) {
                document.getElementById(needAllAudits[i]).style.display = "None";
            }
        }
        document.getElementById("allowSecurityAccess").style.display = "None";
//        document.getElementById("trainingType").style.display = "None";
    } else {
        document.getElementById("isAudit").style.display = "";
        //document.getElementById("selectAcademy").style.display="";
        if (${isAudit==2} || ${empty isAudit}) {//是否需要审核联动
            // document.getElementById("manager").style.display="None";
            // document.getElementById("teacher").style.display = "None";
            // document.getElementById("labManager").style.display = "None";
            // document.getElementById("dean").style.display = "None";
            // document.getElementById("trainingCenterDirector").style.display = "None";
            // document.getElementById("trainingDepartmentDirrector").style.display = "None";
             document.getElementById("1staudit").style.display = "None";
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i]).style.display = "None";
                }
            }
        } else {
            // document.getElementById("manager").style.display="";
            // document.getElementById("teacher").style.display = "";
            // document.getElementById("labManager").style.display = "";
            // document.getElementById("dean").style.display = "";
            // document.getElementById("trainingCenterDirector").style.display = "";
            // document.getElementById("trainingDepartmentDirrector").style.display = "";
            if(needAllAudits[0]) {
                for (var i = 0; i < needAllAudits.length; i++) {
                    document.getElementById(needAllAudits[i]).style.display = "";
                }
            }
        }
        document.getElementById("allowSecurityAccess").style.display="";
        //if(${allowSecurityAccess==2} || ${empty allowSecurityAccess})
        //{//是否需要安全准入联动
            //document.getElementById("trainingType").style.display="None";
        //}else
        //{
            //document.getElementById("trainingType").style.display="";//此处本来是""，由于不需要进行培训形式选择才引掉
        //}
	}
 			
	
$("#needAudit1").change(function(){
	// document.getElementById("teacher").style.display="";
	// document.getElementById("dean").style.display="";
	// document.getElementById("labManager").style.display="";
	// document.getElementById("trainingCenterDirector").style.display="";
	// document.getElementById("trainingDepartmentDirrector").style.display="";
    document.getElementById("1staudit").style.display = "";
    if(needAllAudits[0]) {
        for (var i = 0; i < needAllAudits.length; i++) {
            document.getElementById(needAllAudits[i]).style.display = "";
        }
    }
});
//是否需要安全准入联动
// $("#needAllowSecurityAccess1").change(function(){
// 	document.getElementById("trainingType").style.display="";//此处本来是""，由于不需要进行培训形式选择才引掉
// });

//是否可以预约联动
$("#appointment1").change(function(){
	document.getElementById("allowSecurityAccess").style.display="";
	document.getElementById("isAudit").style.display="";
    //document.getElementById("selectAcademy").style.display="";
	if(${isAudit==2} || ${empty isAudit})
    {//是否需要审核联动
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
    }else
        {
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
        }
    <%--if(${allowSecurityAccess==2} || ${empty allowSecurityAccess})--%>
    <%--{//是否需要安全准入联动--%>
        <%--document.getElementById("trainingType").style.display="None";--%>
    <%--}else--%>
    <%--{--%>
        <%--document.getElementById("trainingType").style.display="";//此处本来是""，由于不需要进行培训形式选择才引掉--%>
    <%--}--%>
});
});

$(document).ready(function(){
	$("#needAudit2").change(function(){
		// document.getElementById("teacher").style.display="None";
		// document.getElementById("dean").style.display="None";
		// document.getElementById("labManager").style.display="None";
		// document.getElementById("trainingCenterDirector").style.display="None";
		// document.getElementById("trainingDepartmentDirrector").style.display="None";
        document.getElementById("1staudit").style.display = "None";
        if(needAllAudits[0]) {
            for (var i = 0; i < needAllAudits.length; i++) {
                document.getElementById(needAllAudits[i]).style.display = "None";
            }
        }
		
	});
	if($("#needAudit2").prop("checked")){
		$("#needAudit2").change();
	}
	// $("#needAllowSecurityAccess2").click(function(){
	// 	document.getElementById("trainingType").style.display="None";
	// });
	
	$("#appointment2").change(function(){
		document.getElementById("allowSecurityAccess").style.display="None";
		document.getElementById("isAudit").style.display="None";
        //document.getElementById("selectAcademy").style.display="None";
		// document.getElementById("labManager").style.display="None";
		// document.getElementById("dean").style.display="None";
		// document.getElementById("trainingCenterDirector").style.display="None";
		// document.getElementById("trainingDepartmentDirrector").style.display="None";
		// document.getElementById("teacher").style.display="None";
        if(needAllAudits[0]) {
            for (var i = 0; i < needAllAudits.length; i++) {
                document.getElementById(needAllAudits[i]).style.display = "None";
            }
        }
//		document.getElementById("trainingType").style.display="None";
		//document.getElementById("isAuditTimeLimit").style.display="None";
	});
	});
function add(openFlag) {
    if (openFlag == 0) {
        var selectedAuthority = $("#selectedAuthority").val();
        var selectedSchoolAcademy = $("#selectedSchoolAcademy").val();
        if(selectedAuthority == null){
            alert("请选择开放对象!");
            return false;
        }
        if(selectedSchoolAcademy == null){
            alert("请选择开放范围!");
            return false;
        }
        addRecord(selectedAuthority,selectedSchoolAcademy,openFlag)
    }
}
function addRecord(selectedAuthority,selectedSchoolAcademy,openFlag) {
    var myData = {
        "academies": selectedSchoolAcademy,
        "authorities": selectedAuthority,
        "labRoomId": ${labRoomId},
    };
    $.ajax({
        url: "${pageContext.request.contextPath}/device/saveLabRoomOpenSetting",
        type: 'POST',
        data:  JSON.stringify(myData),
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            if (openFlag == 0) {
                $("#openSet tr:gt(1)").remove();
                var str='';
                for(var i=0;i<data.results.length;i++){
                    str +="<tr>"+
                        "<td>" + data.results[i][2] + "</td>" +
                        "<td>" + data.results[i][1] + "</td>"+
                        "<td><a href='javascript:void(0);'  onclick='deleteOAA(&quot;" + data.results[i][0] + "&quot,this)'>删除</a></td>"+
                        "</tr>";
                }

                $("#openSet").append(str);
                //清空选择框的内容
                $("#selectedAuthority").val("");
                $("#selectedAuthority").trigger("liszt:updated");
                $("#selectedSchoolAcademy").val("");
                $("#selectedSchoolAcademy").trigger("liszt:updated");


            }
        }
    });
}
function deleteOAA(academyNumber,obj) {
    var labRoomId = ${labRoomId};
    $.ajax({
        url: "${pageContext.request.contextPath}/device/deleteLabRoomOpenSetting?labRoomId="+labRoomId+"&academyNumber="+academyNumber,
        type: 'GET',
        async: false,
        success: function (data) {
            if(data == "success"){
                var tr = getRowObj(obj);
                if(tr != null){
                    tr.parentNode.removeChild(tr);
                }
            }else if(data == "error"){
                alert("请求错误!");
            }
        }
    });
}

function getRowObj(obj){
    var i = 0;
    while(obj.tagName.toLowerCase() != "tr"){
        obj = obj.parentNode;
        if(obj.tagName.toLowerCase() == "table")return null;
    }
    return obj;
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
		<li class="TabbedPanelsTab selected" tabindex="0">
		<a href="javascript:void(0);" onclick="openSetupLink(${device.id},${currpage },${type})">实验室预约设置</a>
		</li>
		 <li class="TabbedPanelsTab" tabindex="0">
			 <a href="javascript:void(0);" onclick="openStationReserSetting(${device.id},${currpage },${type})">工位预约设置</a>
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
				<div id="title">参数设置</div>
				<a class="btn btn-new"  onclick="closeMyWindow()" >返回</a>
				</div>
					<form:form action="${pageContext.request.contextPath}/device/saveDeviceSetting" method="post" modelAttribute="device">
					<div class="new-classroom">
			<table id="radioTable">
			<%--<tr id="allowLending">--%>
				<%--<td>&lt;%&ndash;${device.CDictionaryByTrainType.id }&ndash;%&gt;是否允许借用:</td>--%>
				<%--<td>--%>
				<%--<c:forEach items="${CActives}" var="active" varStatus="i">--%>
				<%--<form:radiobutton path="CDictionaryByAllowLending.id" value="${active.id}" id="needLoan${i.count}" /><label for="needLoan${i.count}">${active.CName}</label>--%>
				<%--</c:forEach>										--%>
				<%--</td>--%>
			<%--</tr>--%>

				<tr id="allowAppointment">
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
				</tr>
			<tr id="isAudit">
				<td>预约是否需要审核:</td>
				<td>
				<c:forEach items="${CActives}" var="activ" varStatus="i">
				<%-- <form:radiobutton id="needAudit${i.count}" path="CActiveByIsAudit.id" value="${activ.id}"  /><label for="needAudit${i.count}">${activ.name}</label> --%>
				<form:radiobutton id="needAudit${i.count}" path="CDictionaryByIsAudit.id" value="${activ.id}"  /><label for="needAudit${i.count}">${activ.CName}</label>
				</c:forEach>
				</td>
			</tr>
				<c:if test="${flag == true}">
					<tr id="1staudit">
						<td>${authLevelOne}</td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${flag == false}">
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
				</c:if>
			<%--<tr id="teacher">--%>
				<%--<td>是否需要导师审核:</td>--%>
                <%--<td>--%>
                    <%--<input type="radio" name="needtutor" id="needtutor1" value="1"/>--%>
                    <%--<label for="needtutor1">是</label>--%>
                    <%--<input type="radio" name="needtutor" id="needtutor2" value="2"/>--%>
                    <%--<label for="needtutor2">否</label>--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%----%>
			<%--<tr id="dean">--%>
				<%--<td>是否需要系主任审核:</td>--%>
                <%--<td>--%>
                    <%--<input type="radio" name="needdean" id="needdean1" value="1"/>--%>
                    <%--<label for="needdean1">是</label>--%>
                    <%--<input type="radio" name="needdean" id="needdean2" value="2"/>--%>
                    <%--<label for="needdean2">否</label>--%>
                <%--</td>--%>
			<%--</tr>--%>
			<%----%>
			<%--<tr id="labManager">--%>
				<%--<td>是否需要实训室管理员审核:</td>--%>
                <%--<td>--%>
                    <%--<input type="radio" name="needlabadministrator" id="needlabadministrator1" value="1"/>--%>
                    <%--<label for="needlabadministrator1">是</label>--%>
                    <%--<input type="radio" name="needlabadministrator" id="needlabadministrator2" value="2"/>--%>
                    <%--<label for="needlabadministrator2">否</label>--%>
                <%--</td>--%>
			<%--</tr>--%>
			<%--<tr id="trainingCenterDirector">--%>
				<%--<td>是否需要实训中心主任审核:</td>--%>
                <%--<td>--%>
                    <%--<input type="radio" name="trainingCenterDirector" id="trainingCenterDirector1" value="1"/>--%>
                    <%--<label for="trainingCenterDirector1">是</label>--%>
                    <%--<input type="radio" name="trainingCenterDirector" id="trainingCenterDirector2" value="2"/>--%>
                    <%--<label for="trainingCenterDirector2">否</label>--%>
                <%--</td>--%>
			<%--</tr>--%>
			<%--<tr id="trainingDepartmentDirrector">--%>
				<%--<td>是否需要实训部主任审核:</td>--%>
                <%--<td>--%>
                    <%--<input type="radio" name="trainingDepartmentDirrector" id="trainingDepartmentDirrector1" value="1"/>--%>
                    <%--<label for="trainingDepartmentDirrector1">是</label>--%>
                    <%--<input type="radio" name="trainingDepartmentDirrector" id="trainingDepartmentDirrector2" value="2"/>--%>
                    <%--<label for="trainingDepartmentDirrector2">否</label>--%>
                <%--</td>--%>
			<%--</tr>		--%>
			<tr id="allowSecurityAccess">
				<td>是否需要安全准入:</td>
				<td>
				<c:forEach items="${CActives}" var="activ" varStatus="i">
				<%-- <form:radiobutton path="CActiveByAllowSecurityAccess.id" value="${activ.id}" id="needAllowSecurityAccess${i.count}"/>${activ.name} --%>
				<form:radiobutton path="CDictionaryByAllowSecurityAccess.id" value="${activ.id}" id="needAllowSecurityAccess${i.count}"/>${activ.CName}
				</c:forEach>								
				</td>
			</tr>

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
				<%--<div class="TabbedPanels" id="selectAcademy">--%>
					<%--<table class="tab_lab" style="margin:10px 0 0;">--%>
						<%--<tr>--%>
							<%--<th>开放范围</th>--%>
							<%--<td width="90%">--%>
								<%--<select class="chzn-select" multiple id="selectedSchoolAcademy"--%>
										<%--name="selectedSchoolAcademy">--%>
									<%--<c:forEach items="${schoolAcademyList}" var="schoolAcademy" varStatus="i">--%>
										<%--<c:if test="${selectedSchoolAcademies.contains(schoolAcademy)}">--%>
											<%--<option value="${schoolAcademy.academyNumber}"--%>
													<%--selected>${schoolAcademy.academyName}</option>--%>
										<%--</c:if>--%>
										<%--<c:if test="${!selectedSchoolAcademies.contains(schoolAcademy)}">--%>
											<%--<option value="${schoolAcademy.academyNumber}">${schoolAcademy.academyName}</option>--%>
										<%--</c:if>--%>
									<%--</c:forEach>--%>
								<%--</select>--%>
							<%--</td>--%>
						<%--</tr>--%>
					<%--</table>--%>
				<%--</div>--%>
				<div style="width: 50px; margin: 20px auto">
					<input type="button" onclick="saveDeviceSettingRest(${device.id});" value="保存">
				</div>
				<div class="title">
					<div id="title2">开放设置</div>
					<%--<a class="btn btn-new"  onclick="newAcAndAuth()" >增加</a>--%>
				</div>
				<div class="content-double">
					<table id="openSet">
						<thead>
						<tr>
							<th>开放对象</th>
							<th>开放范围</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td>
								<select class="chzn-select" multiple id="selectedAuthority"
										name="selectedAuthority">
									<c:forEach items="${authorityList}" var="authority" varStatus="i">
										<%--<c:if test="${selectedAuthorities.contains(authority)}">--%>
										<%--<option value="${authority.authorityName}"--%>
										<%--selected>${authority.cname}</option>--%>
										<%--</c:if>--%>
										<%--<c:if test="${!selectedAuthorities.contains(authority)}">--%>
										<%--<option value="">请选择</option>--%>
										<option value="${authority.id}">${authority.cname}</option>
										<%--</c:if>--%>
									</c:forEach>
								</select>
							</td>
							<td>
								<select class="chzn-select" multiple id="selectedSchoolAcademy"
										name="selectedSchoolAcademy">
									<c:forEach items="${schoolAcademyList}" var="schoolAcademy" varStatus="i">
										<%--<c:if test="${selectedSchoolAcademies.contains(schoolAcademy)}">--%>
										<%--<option value="${schoolAcademy.academyNumber}"--%>
										<%--selected>${schoolAcademy.academyName}</option>--%>
										<%--</c:if>--%>
										<c:if test="${schoolAcademy.academyName!='跨学科'}">
											<%--<option value="">请选择</option>--%>
											<option value="${schoolAcademy.academyNumber}">${schoolAcademy.academyName}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="add(0)";>保存</a>
							</td>
						</tr>
						<c:forEach items="${openAcademyAndAuthorities}" var="oaa">
						<tr>
							<td>${oaa[2]}</td>
							<td>${oaa[1]}</td>
							<td>
								<a href="javascript:void(0);" onclick="deleteOAA('${oaa[0]}',this)";>删除</a>
							</td>
						</tr>
						</c:forEach>
					</table>
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
