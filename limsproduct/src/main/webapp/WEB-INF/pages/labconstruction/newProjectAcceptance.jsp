<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%----%>

<html >  
<head>
<meta name="decorator" content="iframe"/>  
<title><fmt:message key="html.title"/></title>
<!-- <meta name="decorator" content="iframe"/> -->

  <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
  
  <!-- 下拉框的样式 -->
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 --> 
  
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
  
<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css"/> 
<script type="text/javascript">

//添加参与人员
function addAdmin(url){
	var data="<tr>"+
				"<td><input type='text' name='username'/></td>"+
		    	"<td><input type='text' name='sex'/></td>"+
				"<td><input type='text' name='age'/></td>"+
				"<td><input type='text' name='position'/></td>"+
				"<td><input type='text' name='jobtitle'/></td>"+
				"<td><input type='text' name='content'/></td>"+
				"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
				"</tr>";	;
	$("#add_user").append(data); 
	    
 }


//添加新的预算
function addMoney(thisUrl){
	$.ajax({
	           url:thisUrl,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		$("#add_money").append(data);  
	           				
	           }
	});
	
}





//添加设备
function addDevice(intputpage) {
    var deviceName = document.getElementById("deviceName").value;
    var thisUrl="${pageContext.request.contextPath}/labconstruction/findDeviceBydeviceName?page="+intputpage+"&deviceName="+deviceName;
    $.ajax({
        url: thisUrl,
        type: "POST",
        success: function (data) {//AJAX查询成功

            document.getElementById("device_body").innerHTML = data;
            $("#addDevice").window({top: window.pageYOffset+100});
            $("#addDevice").show();
            $("#addDevice").window('open');

        }
    });

}
 
//保存选中的设备
function saveDevice(url){
        var deviceArray=new Array();
        var flag; //判断是否一个未选   
        $("input[name='CK_device']:checkbox").each(function() { //遍历所有的name为CK_device的 checkbox  
                    if ($(this).attr("checked")) { //判断是否选中    
                        flag = true; //只要有一个被选择 设置为 true  
                    }  
                })  

        if (flag) {  
           $("input[name='CK_device']:checkbox").each(function() { //遍历所有的name为CK_device的 checkbox  
                        if ($(this).attr("checked")) { //判断是否选中
                            deviceArray.push($(this).val()); //将选中的值 添加到 array中 
                        }  
                    })  
           //alert(array);         
           //将要所有要添加的数据传给后台处理   
           var thisURL=url+"?deviceArray="+deviceArray;
           
           $.ajax({
	           url:thisURL,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	                  //document.getElementById("add_user").innerHTML=data;		
	            	 $("#add_device").append(data); 
	           }
			});
           $("#addDevice").window('close'); 
           
        } else {   
        	alert("请至少选择一条记录");  
        	}  
   }
//添加实验项目
function addItem(){
	var id=document.getElementById("item").value;
	//序号的最大值
	var i=document.getElementsByName("itemId").length;
	$.ajax({
	           url:"${pageContext.request.contextPath}/labconstruction/findOperationItemById?id="+id+"&i="+i,
	           type:"POST",
	           success:function(data){//AJAX查询成功	
	            	 $("#add_Item").append(data); 
	           }
			});
}   
//新增填写项
function addOperationItem(){
	//序号的最大值
	var m=document.getElementsByName("itemId").length;
	var i=parseInt(m);
	i++;
	var s="<tr>"+
		    	//"<td>"+i+"<input type='hidden' name='itemId' value='0'>"+"</td>"+
		    	"<td>"+"<input type='text' name='itemName'>"+"</td>"+
				"<td>"+"否"+"</td>"+
				"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
				"</tr>";	
	$("#add_Item").append(s);
}
//新增设备填写项
function addDeviceProperty(){
	//序号的最大值
	var m=document.getElementsByName("CK_device").length;
	var i=parseInt(m);
	i++;
	var s="<tr>"+
		    	//"<td>"+i+"</td>"+
		    	"<td>"+"<input type='text' name='equipmentName'>"+"</td>"+
		    	"<td>"+"<input type='text' name='format'>"+"</td>"+
		    	"<td>"+"<input type='text' name='amount'>"+"</td>"+
		    	"<td>"+"<input type='text' name='unitPrice'>"+"</td>"+
				"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
				"</tr>";	
	$("#add_device").append(s);
}
</script>
<script type="text/javascript">
function uploadDocument(){
			 $("#searchFile").window({top: 1500});
			 $("#searchFile").window('open');
			 $("#file_upload").uploadify({
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            //提交的controller和要在火狐下使用必须要加的id
	            'uploader':'${pageContext.request.contextPath}/labconstruction/projectAcceptanceApp/uploadFile;jsessionid=<%=session.getId()%>',		
	            buttonText:'选择文件',
	            //上传成功之后在列表追加显示文档
	            'onUploadSuccess' : function(file, data, response) {
        		  
					   $("#document").append(data); 
    		 	},
	            onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发	
	      			    //当上传完所有文件的时候关闭对话框并且转到显示界面
	            	 $('#searchFile').window('close');	
	            	 
				}
	        });
			
		}


</script>
<script type="text/javascript">
//获取参与人员username数组
function getUsernameArray(){
	var usernameArray=new Array();
	$("input[name='username']:hidden").each(function() { //遍历所有的name为username的 hidden  
		usernameArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return usernameArray;
}
//获取参与人员sex数组
function getSexArray(){
	var sexArray=new Array();
	$("input[name='sex']:text").each(function() { //遍历所有的name为username的 text  
		sexArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return sexArray;
}
//获取参与人员age数组
function getAgeArray(){
	var ageArray=new Array();
	$("input[name='age']:text").each(function() { //遍历所有的name为age的 text  
		ageArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return ageArray;
}
//获取参与人员position(职务)数组
function getPositionArray(){
	var positionArray=new Array();
	$("input[name='position']:text").each(function() { //遍历所有的name为age的 text  
		positionArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return positionArray;
}
//获取参与人员jobtitle(职称)数组
function getJobTitleArray(){
	var jobtitleArray=new Array();
	$("input[name='jobtitle']:text").each(function() { //遍历所有的name为age的 text  
		jobtitleArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return jobtitleArray;
}
//获取参与人员负责内容数组
function getContentArray(){
	var contentArray=new Array();
	$("input[name='content']:text").each(function() { //遍历所有的name为content的 text  
		contentArray.push($(this).val()); //将选中的值 添加到 array中 
    });
    return contentArray;
}
//计算申请资助金额
function count(){
	var moneyArray=getMoneyArray();
	var count=0;
	for(var i=0;i<moneyArray.length;i++){
		var money=parseFloat(moneyArray[i]);
		count+=money;
	}
	//转化为万元
	count=(count/10000).toFixed(4);
	document.getElementById("feeAmount").value=count;
}
//获取资金类型数组
function getFundTypeArray(){
	var fundTypeArray=new Array();
	var ops=document.getElementsByName("fundtype");
	for(var i=0;i<ops.length;i++){
		fundTypeArray.push(ops[i].value);
	}
    return fundTypeArray;
}
//获取金额数组
function getMoneyArray(){
	var moneyArray=new Array();
	$("input[name='money']:text").each(function() { //遍历所有的name为money的 text  
		moneyArray.push($(this).val()); //将选中的值 添加到 array中 
    });
    return moneyArray;
} 
//获取说明数组
function getExplainArray(){
	var explainArray=new Array();
	$("input[name='explain']:text").each(function() { //遍历所有的name为explain的 text  
		explainArray.push($(this).val()); //将选中的值 添加到 array中 
    });
    return explainArray;
}
//获取设备编号数组
function getSchoolDeviceArray(){
	var schoolDeviceArray=new Array();
	$("input[name='schoolDevice']:hidden").each(function() { //遍历所有的name为schoolDevice的 hidden  
		schoolDeviceArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return schoolDeviceArray;
}
//获取实验项目id数组
function getItemIdArray(){
	var itemIdArray=new Array();
	$("input[name='itemId']:hidden").each(function() { //遍历所有的name为itemId的 hidden  
		itemIdArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return itemIdArray;
}
//获取实验项目名称数组
function getItemNameArray(){
	var itemNameArray=new Array();
	$("input[name='itemName']:text").each(function() { //遍历所有的name为itemName的 text  
		itemNameArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return itemNameArray;
}
//获取申请附件id数组
function getDocumentIdArray(){
	var documentIdArray=new Array();
	$("input[name='docId']:hidden").each(function() { //遍历所有的name为docId的 hidden  
		documentIdArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return documentIdArray;
}
//获取添加设备equipmentName数组
function getEquipmentNameArray(){
	var equipmentNameArray=new Array();
	$("input[name='equipmentName']:text").each(function() { //遍历所有的name为username的 text  
		equipmentNameArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return equipmentNameArray;
}
//获取添加设备format数组
function getFormatArray(){
	var formatArray=new Array();
	$("input[name='format']:text").each(function() { //遍历所有的name为username的 text  
		formatArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return formatArray;
}
//获取添加设备Amount数组
function getAmountArray(){
	var amountArray=new Array();
	$("input[name='amount']:text").each(function() { //遍历所有的name为username的 text  
		amountArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return amountArray;
}
//获取添加设备unitPrice数组
function getUnitPriceArray(){
	var unitPriceArray=new Array();
	$("input[name='unitPrice']:text").each(function() { //遍历所有的name为username的 text  
		unitPriceArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return unitPriceArray;
}
</script>
<script type="text/javascript">
function sub(url){
	//申请人员名单
	var usernameArray=getUsernameArray();
	//性别
	var sexArray=getSexArray();
	//年龄
	var ageArray=getAgeArray();
	//职务
	var positionArray=getPositionArray();
	//职称
	var jobtitleArray=getJobTitleArray();
	//负责内容
	var contentArray=getContentArray();
	//经费类型
	var fundTypeArray=getFundTypeArray();
	//金额
	var moneyArray=getMoneyArray();
	//说明
	var explainArray=getExplainArray();
    //申请资助金额
    var feeAmount=document.getElementById("feeAmount").value;
    //设备
    var schoolDeviceArray=getSchoolDeviceArray();
    //实验项目id数组
    var itemIdArray=getItemIdArray();
    //实验名称数组
    var itemNameArray=getItemNameArray();
    //申请附件id
    var documentIdArray=getDocumentIdArray();
    //设备名称
    var equipmentNameArray=getEquipmentNameArray();
    //设备型号
    var formatArray=getFormatArray();
    //设备数量
    var amountArray=getAmountArray();
    //设备单价
    var unitPriceArray=getUnitPriceArray();
    //注意此处的url长度，每个浏览器可以解析的url长度不同
    document.subForm.action=url+"?usernameArray="+usernameArray+"&sexArray="+sexArray+"&ageArray="+ageArray
    +"&positionArray="+positionArray+"&jobtitleArray="+jobtitleArray+"&contentArray="+contentArray+"&fundTypeArray="+fundTypeArray
    +"&moneyArray="+moneyArray+"&explainArray="+explainArray+"&schoolDeviceArray="+schoolDeviceArray
    +"&itemIdArray="+itemIdArray+"&itemNameArray="+itemNameArray+"&documentIdArray="+documentIdArray
    +"&equipmentNameArray="+equipmentNameArray+"&formatArray="+formatArray+"&amountArray="+amountArray
    +"&unitPriceArray="+unitPriceArray;
	document.subForm.submit();
}
</script>
<script type="text/javascript">
				
	//生成html编辑栏，设置宽度为500px
	var editor = new UE.ui.Editor({initialFrameWidth:900});
	editor.render("projectSituation_progress");
	var editor1 = new UE.ui.Editor({initialFrameWidth:900});
	editor1.render("projectExpectedBenefits_progress");
	var editor2 = new UE.ui.Editor({initialFrameWidth:900});
	editor2.render("constructCondition_progress");
	var editor3 = new UE.ui.Editor({initialFrameWidth:900});
	editor3.render("actualBenefits_progress");
</script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>
<body>
<div class="navigation">
<div id="navigation">
	<ul>
		<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
		<li class="end"><a href="javascript:void(0)">实验室建设项目验收申请</a></li>
	</ul>
</div>
</div>


<!-- 结项申报列表 -->

<!-- 查询表单 -->
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="title">
	<div id="title">实验室建设项目验收申请新建申请</div>
	<a class="btn btn-return" onclick="window.history.go(-1)">返回</a>
</div>   	
<div class="content-box">
<form:form name="subForm" method="post" modelAttribute="projectAcceptanceApplication" >
<fieldset class="introduce-box">
<legend>基础信息（带<font color=red>*</font>为必填项）<input type="hidden" value="" id="xsd"></legend>
<table cellpadding="0" cellspacing="0" id="viewTable">
<tbody>
    <tr>
        <td class="label" valign="top">项目名称<font color=red>*</font>：</td>
		<td class="label" valign="top" >${projectStartedReport.projectName} </td>
       <input type="hidden" name="projectStartedReport.id" value="${projectAcceptanceApplication.projectStartedReport.id} " >
        <td class="label" valign="top">所在系部<font color=red>*</font>：</td>
	    <td>				
			<%--<form:select path="schoolAcademy.academyNumber" class="chzn-select">
			<form:options items="${SchoolAcademys}" itemLabel="academyName" itemValue="academyNumber"/>
			</form:select>--%>
					${projectStartedReport.schoolAcademy.academyName}
	    </td>
       
     	   <td class="label" valign="top">申报时间：</td>
       <td class="label" valign="top"><fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectAcceptanceApplication.projectStartedReport.labConstructApp.appDate.time}"/></td>
       <form:hidden path="id" />
    </tr>
    <tr>
       <td class="label" valign="top">申请人：</td>
	   <td class="label" valign="top">${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.cname}</td> 
	   <!--申报人属性  -->
	   <input type="hidden" name="user.username" value="${projectStartedReport.labConstructApp.user.username} " >
	   </td>
	   <!--申报单位属性  -->
	   <input type="hidden" name="schoolAcademy.academyNumber" value="${projectStartedReport.labConstructApp.user.schoolAcademy.academyNumber}  " >
	   </td>
	   <td class="label" valign="top">工号：</td>
	   <td class="label" valign="top">${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.username}</td>
	   <td class="label" valign="top">性别：</td>
	   <td class="label" valign="top">${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.userSexy}</td>
	</tr>
	<tr>
	   <td class="label" valign="top">联系电话：</td>
	   <td class="label" valign="top">${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.telephone}</td>
	   <td class="label" valign="top">E-MAIL：</td>
	   <td class="label" valign="top">${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.email}</td> 
	   <td class="label" valign="top">项目来源<font color=red>*</font>：</td>
		<td>				
			<%--<form:select path="CProjectSource.id" class="chzn-select">
			<form:options items="${CProjectSources}" itemLabel="name" itemValue="id"/>
			</form:select>--%>
					${projectStartedReport.CProjectSource.name}
	   </td>
	</tr>
    <tr>
       <td>用途：${projectStartedReport.labConstructApp.CProjectPurpose.name}</td> 
       <%--<td>立项时间：<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectStartedReport.labConstructApp.appDate.time}"/></td>	
	--%></tr>
</tbody>
</table></fieldset>
<fieldset class="introduce-box">
			<legend>项目完成情况概述（带<font color=red>*</font>为必填项）<input type="hidden" value="" id="xsd"></legend>
<table cellpadding="0" cellspacing="0">
<tbody>
   <tr>
   	   <td class="label" valign="top">预期完成时间：</td>
	   <td class="label" valign="top"><input id="expectCompleteDate" name="expectCompleteDate" value="${expectCompleteDate}" onclick="new Calendar().show(this);"  readonly="readonly"  required="true"/></td> 
   	   <td class="label" valign="top">实际完成时间：</td>
	   <td class="label" valign="top"><input id="realityCompleteDate" name="realityCompleteDate" value="${realityCompleteDate}" onclick="new Calendar().show(this);"  readonly="readonly"  required="true"/></td> 
   </tr>
      <tr>
   	   <td class="label" valign="top">立项时间：</td>
	   <td class="label" valign="top"><input id="projectStartDate" name="projectStartDate" value="${projectStartDate}" onclick="new Calendar().show(this);"  readonly="readonly"  required="true"/></td> 
   </tr>
   <tr>
	    <td>原实验室概况：</td>
   </tr>
   <tr>
       <td class="label" valign="top">面积：</td>
	   <td class="label" valign="top"><form:input id="originalLabroomArea" path="originalLabroomArea"></form:input></td>
       <td class="label" valign="top">地点：</td>
	   <td class="label" valign="top"><form:input id="originalLabroomAdd" path="originalLabroomAdd"></form:input></td>
   </tr>
   <tr>
       <td class="label" valign="top">设备价值：</td>
	   <td class="label" valign="top"><form:input id="originalLabroomValue" path="originalLabroomValue"></form:input></td>
       <td class="label" valign="top">开设实验项目数：</td>
	   <td class="label" valign="top"><form:input id="originalLabroomItem" path="originalLabroomItem"></form:input></td> 
   </tr> 
   <tr>
	    <td>建成后实验室概况：</td>
   </tr>
   <tr>
       <td class="label" valign="top">面积：</td>
	   <td class="label" valign="top"><form:input id="targetLabroomArea" path="targetLabroomArea"></form:input></td>
       <td class="label" valign="top">地点：</td>
	   <td class="label" valign="top"><form:input id="targetLabroomAdd" path="targetLabroomAdd"></form:input></td>
   </tr>
   <tr>
       <td class="label" valign="top">设备价值：</td>
	   <td class="label" valign="top"><form:input id="targetLabroomValue" path="targetLabroomValue"></form:input></td>
       <td class="label" valign="top">开设实验项目数：</td>
	   <td class="label" valign="top"><form:input id="targetLabroomItem" path="targetLabroomItem"></form:input></td> 
   </tr>	    
   <tr>
       <td class="label" valign="top">面向专业数：</td>
	   <td class="label" valign="top"><form:input id="majorAmount" path="majorAmount"></form:input></td>
       <td class="label" valign="top">面向课程数：</td>
	   <td class="label" valign="top"><form:input id="courseAmount" path="courseAmount"></form:input></td>
   </tr>
   <tr>
       <td class="label" valign="top">专业名称：</td>
       <td><select name=majorName id="majorName" style="width: 280px" multiple="true" class="chzn-select" >          
            <c:forEach items="${ID1}" var="s">
			<option value="${s.majorNumber }">${s.majorName }</option></c:forEach> 
		    </select>
	   </td>
	   <td class="label" valign="top">课程名称：</td>
       <td><select name=courseName id="courseName" style="width: 280px" multiple="true" class="chzn-select" >          
            <c:forEach items="${ID2}" var="s">
			<option value="${s.courseNo }">${s.courseName }</option></c:forEach> 
		    </select>
	   </td>
   </tr>
   <tr>
       <td class="label" valign="top">可开设实验项目数：</td>
	   <td class="label" valign="top"><form:input id="expectLabItem" path="expectLabItem"></form:input></td>
       <td class="label" valign="top">实际开设实验项目数：</td>
	   <td class="label" valign="top"><form:input id="realityLabItem" path="realityLabItem"></form:input></td>
   </tr>
   <tr>
	   <td class="label" valign="top" >立项概况：</td>
	   <td colspan="3">
	   <form:textarea id="projectSituation_progress" path="projectSituation" />
	   </td>
   </tr>
   <tr>
	   <td class="label" valign="top" >立项预期效益：</td>
	   <td colspan="3">
	   <form:textarea id="projectExpectedBenefits_progress" path="projectExpectedBenefits" />
	   </td>
   </tr>
   <tr>
	   <td class="label" valign="top" >建设完成情况：</td>
	   <td colspan="3">
	   <form:textarea id="constructCondition_progress" path="constructCondition" />
	   </td>
   </tr>
   <tr>
	   <td class="label" valign="top" >实际效益：</td>
	   <td colspan="3">
	   <form:textarea id="actualBenefits_progress" path="actualBenefits" />
	   </td>
   </tr>
   
</tbody>
</table>
</fieldset>    
<!-- 经费预算 -->	
<fieldset class="introduce-box">
	<legend>经费预算</legend>
		<table>
			<tr>
				<td>申请资助金额（万元）：
				<font color=red>*根据下方预算金额计算总额</font>
				</td>
				<td>
				<form:input id="feeAmount" path="feeAmount" readonly="true"/>
				</td>
				<td>其他经费来源及金额</td>
				<td>
				<form:input path="otherFee" placeholder="请输入数字" class="easyui-numberbox"  maxlength="10" validType="length[0,9]"/>
				</td>
				<td><input type="button"onclick="addMoney('${pageContext.request.contextPath}/labconstruction/addMoney');" value="添加新的预算">
			</tr>
		  <table>
		  	<thead>
				<tr>
				<td>预算科目</td>
				<td>金额（元）</td>
				<td>说明</td>
				<td>操作</td>
				</tr>
			</thead>	
			<tbody id="add_money">
				<c:forEach items="${projectAcceptFeeLists}" var="fee">
				<tr>
				<td>${fee.CFundAppItem.name}</td>
				<td>${fee.amount} </td>
				<td>${fee.budgetaryItem}</td>
				<td>
				<a onclick="return confirm('确认要删除吗？')" href="${pageContext.request.contextPath}/labconstruction/deleteProjectAcceptFeeListNew?id=${fee.id}&AppId=${projectAcceptanceApplication.projectStartedReport.id}">删除</a>
				<a href="${pageContext.request.contextPath}/labconstruction/updateProjectAcceptFeeList?id=${fee.id}&AppId=${projectAcceptanceApplication.id}&flag=1" >修改</a>
				</td>
				</tr>
				</c:forEach>
			</tbody>
		  </table>
	</table>
</fieldset>  
<!-- 设备列表 -->
<fieldset class="introduce-box">
	<legend>设备列表（带<font color=red>*</font>为必填项）</legend>
		<table>
			<tr>
				<td><input type="button"onclick="addDevice(1);" value="添加新的设备">
				</td>
				<td><input type="button"onclick="addDeviceProperty();" value="新增填写项"></td>
			</tr>
		  <table>
		  	<thead>
				<tr>
				<%--<td>序号</td>
				--%><td>名称</td>
				<td>规格或型号<font color=red>*</font></td>
				<td>数量<font color=red>*</font></td>
				<td>单价（元）<font color=red>*</font></td>
				<td>操作</td>
				</tr>
			</thead>	
			<tbody id="add_device">
				<c:forEach items="${projectAcceptDevices}" var="device" varStatus="i">
				<tr>
				<%--<td>${i.count}</td>
				--%><td>${device.equipmentName}</td>
				<td>${device.format}</td>
				<td>${device.amount}</td>
				<td>${device.unitPrice}</td>
				<td>
				<a onclick="return confirm('确认要删除吗？')" href="${pageContext.request.contextPath}/labconstruction/deleteProjectAcceptDeviceNew?id=${device.id}&AppId=${projectAcceptanceApplication.projectStartedReport.id}">删除</a>
				<a href="${pageContext.request.contextPath}/labconstruction/updateProjectAcceptDevice?id=${device.id}&AppId=${projectAcceptanceApplication.id}&flag=1" >修改</a>
				</td>
				
				</tr>
				</c:forEach>		
			</tbody>
		  </table>
	</table>
</fieldset>
<!-- 实验项目 -->
<fieldset class="introduce-box">
	<legend>建成后可开设实验（实训）项目清单</legend>
		<table>
			<tr>
				<td>实验项目</td>
				<td>
				<select class="chzn-select" id="item">
				<c:forEach items="${Items}" var="item">
				<option value="${item.id}">${item.lpName}</option>
				</c:forEach>
				</select>
				<input type="button"onclick="addItem();" value="添加">
				</td>
				<td><input type="button"onclick="addOperationItem();" value="新增填写项"></td>
			</tr>
		  <table>
		  	<thead>
				<tr>
				<%--<td>序号</td>
				--%><td>名称</td>
				<td>是否原有实验项目</td>
				<td>操作</td>
				</tr>
			</thead>	
			<tbody id="add_Item">
				<c:forEach items="${projectAcceptCompletionItems}" var="item" varStatus="i">
				<tr>
				<%--<td>${i.count}</td>
				--%><td>${item.experimentName}</td>
				<td>${item.isOriginal}</td>
				<td>
				<a onclick="return confirm('确认要删除吗？')" href="${pageContext.request.contextPath}/labconstruction/deleteProjectAcceptCompletionItemNew?id=${item.id}&AppId=${projectAcceptanceApplication.projectStartedReport.id}">删除</a>
				<a href="${pageContext.request.contextPath}/labconstruction/updateProjectAcceptCompletionItem?id=${item.id}&AppId=${projectAcceptanceApplication.id}&flag=1" >修改</a>
				</td>
				</tr>
				</c:forEach>		
			</tbody>
		  </table>
	</table>
</fieldset>
 <!-- 审核附件 --> 
<fieldset class="introduce-box">
						<legend>附件上传处</legend>
					<table id="document">
						<tr><input type="button" onclick="uploadDocument()" value="上传附件"/></tr>
						<tr >
							 <td>文档名称</td>
							 <td>操作&nbsp;</td>
						 </tr>
					</table>
					 <table>
						  <tbody id="doc"></tbody>
					 </table>	
</fieldset>	 
     <tr><td colspan="5"></td></tr>
        <input type="button" value="确定" onclick="sub('${pageContext.request.contextPath}/labconstruction/saveProjectAcceptanceNew');"/>
        <input type="button" value="取消" onclick="window.history.go(-1);" />
</form:form> 
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
 		<!-- 添加设备 -->
		<div id="addDevice" class="easyui-window " title="添加设备" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 600px; height: 500px;">
		<div class="content-box">
		
			<table class="tb" id="my_show">
				<tr>
					<td>设备名称：<input type="text" id="deviceName"> </td>
					<td><a onclick="addDevice(1);" >搜索</a>	</td>
					<td>
						<input type="button" value="添加" onclick="saveDevice('${pageContext.request.contextPath}/labconstruction/saveDevice2');">
					</td>
				</tr>
			</table>
		
		
		<table id="my_show">
					<thead>
						<tr>
							<th style="width:10% !important">选择</th>
							<th style="width:30% !important">设备名称</th>
							<th style="width:30% !important">规格型号</th>
							<th style="width:30% !important">单价</th>
							
						</tr>
					</thead>
						
					<tbody id="device_body">
						
					</tbody>
					
			</table>
			</div>
		</div>
		<!-- 添加设备结束 -->		
		<!-- 上传图片 -->
		<div id="searchFile" class="easyui-window" title="上传图片" closed="true" iconCls="icon-add" style="width:400px;height:200px">
	    <form id="form_file" method="post" enctype="multipart/form-data">
           <table  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
            <td>
          	<div id="queue"></div>
		    <input id="file_upload" name="file_upload" type="file" multiple="true">
            </tr>   
            </table>
         </form>
     	</div>	
     	<!-- 上传图片结束 -->
     	
     	<!-- 修改参与人员的层 -->
     	<div id="updateUser" class="easyui-window " title="编辑参与人员" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 1000px; height: 500px;">
		
		</div>
		<!-- 修改参与人员的层结束 -->
		<!-- 修改经费预算的层 -->
     	<div id="updateMoney" class="easyui-window " title="编辑参与人员" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 1000px; height: 500px;">
		
		</div>
		<!-- 修改经费预算的层结束 --> 
</body>
</html>