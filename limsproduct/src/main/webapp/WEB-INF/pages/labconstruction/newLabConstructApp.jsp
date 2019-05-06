<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css"/> 
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  

<script type="text/javascript">
var k = 0;
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
function addDevice(intputpage){
	var deviceName=document.getElementById("deviceName").value;
	var thisUrl="${pageContext.request.contextPath}/labconstruction/findDeviceBydeviceName?page="+intputpage+"&deviceName="+deviceName;
	$.ajax({
	           url:thisUrl,
	           type:"POST",
	           success:function(data){//AJAX查询成功
	           		
	           			document.getElementById("device_body").innerHTML=data;
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
	k++;
	var s="<tr>"+
		    	//"<td>"+k+"</td>"+
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
	k++;
	var s="<tr>"+
		    	//"<td>"+k+"</td>"+
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
			 $("#searchFile").window({top: 2200});
			 $("#searchFile").window('open');
			 $("#file_upload").uploadify({
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            //提交的controller和要在火狐下使用必须要加的id
	            'uploader':'${pageContext.request.contextPath}/labconstruction/uploadFile;jsessionid=<%=session.getId()%>',		
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
	$("input[name='username']:text").each(function() { //遍历所有的name为username的 text  
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
	editor.render("activity_progress");
	var editor1 = new UE.ui.Editor({initialFrameWidth:900});
	editor1.render("specialInnovation_progress");
	var editor2 = new UE.ui.Editor({initialFrameWidth:900});
	editor2.render("projectBasis_progress");
	var editor3 = new UE.ui.Editor({initialFrameWidth:900});
	editor3.render("constructBasis_progress");
	var editor3 = new UE.ui.Editor({initialFrameWidth:900});
	editor3.render("planSchedule_progress");
	var editor4 = new UE.ui.Editor({initialFrameWidth:900});
	editor4.render("expectedResult_progress");
</script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
	<style>
		.content-box fieldset{
			width:96%!important;
		}
		.introduce-box th, .introduce-box td {
			text-align:left;
		}
		.introduce-box tr td:nth-child(odd){
			width:100px;
		}
		.introduce-box table input, .introduce-box table select {
			border: 1px solid #cdcdcd;
			line-height: 22px;
			border-radius: 3px;
			margin: 0;
			box-shadow:none;
		}
		.chzn-container{
			width:350px!important;
		}
		.chzn-choices{
			border-radius:3px;
			box-shadow:none!important;
		}
		.edui-default .edui-editor{
			width:100%!important;
		}
	</style>
</head>
<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.applicant"/></a></li>
						<li class="end"><a href="javascript:void(0)">新建申请</a></li>
					</ul>
				</div>
			</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="title">
						<div id="title"> 新建申请	 </div>
						<a class="btn btn-return" onclick="window.history.go(-1)">返回</a>
					</div>
					 
			 <div class="content-box">
<%--<form:form name="subForm" method="post" modelAttribute="labConstructApp" >

--%><form:form name="subForm" onsubmit="sub('${pageContext.request.contextPath}/labconstruction/saveLabConstructApp')"  method="post" modelAttribute="labConstructApp" >
<fieldset class="introduce-box">
			<legend>基础信息（带<font color=red>*</font>为必填项）</legend>
<table cellpadding="0" cellspacing="0" id="viewTable">
<tbody>
     <tr>
       <td class="label" valign="top">申请人：</td>
	   <td class="label" valign="top">${loginUser.cname}</td> 
	   <!--申报人属性  -->
	   <input type="hidden" name="user.username" value="${loginUser.username} " >
	   <!--申报单位属性  -->
	   <input type="hidden" name="schoolAcademy.academyNumber" value="${loginUser.schoolAcademy.academyNumber}  " >
	   <td class="label" valign="top">工号：</td>
	   <td class="label" valign="top">${loginUser.username}</td>
	</tr>
	<tr>
	   <td class="label" valign="top">性别：</td>
	   <td class="label" valign="top">${loginUser.userSexy}</td> 
	   <td class="label" valign="top">联系电话：</td>
	   <td class="label" valign="top">${loginUser.telephone}</td> 
	</tr>
	<tr>
	   <td class="label" valign="top">E-MAIL：</td>
	   <td class="label" valign="top">${loginUser.email}</td> 
	</tr>
    <tr>
        <td class="label" valign="top">项目名称<font color=red>*</font>：</td>
		<td class="label" valign="top" ><form:input path="projectName" required="true"/> </td>
	    <td class="label" valign="top">申报时间：</td>
	    <td class="label" valign="top" ><input id="app_date" name="appDate" value="${date}" onclick="new Calendar().show(this);"  readonly="readonly"  required="true"/></td>	
	</tr>
	<tr>
		<td class="label" valign="top">项目来源：</td>
		<td>				
			<form:select path="CProjectSource.id" class="chzn-select">
			<form:options items="${CProjectSources}" itemLabel="name" itemValue="id"/>
			</form:select>
		</td>
		<td class="label" valign="top">用途：</td>
       <td><select name=purposeName id="purposeName" style="width: 280px" multiple="true" class="chzn-select" >          
            <c:forEach items="${ProjectPurposes}" var="p">
			<option value="${p.id }">${p.name }</option></c:forEach> 
		    </select>
	   </td>
	</tr>
	<tr>
	   <td class="label" valign="top">面向专业数：</td>
	   <td class="label" valign="top"><form:input id="majorAmount" path="majorAmount"></form:input></td>
       <td class="label" valign="top">专业名称：</td>
       <td><select name=majorName id="majorName" style="width: 280px" multiple="true" class="chzn-select" >          
            <c:forEach items="${ID1}" var="s">
			<option value="${s.majorNumber }">${s.majorName }</option></c:forEach> 
		    </select>
	   </td>
	 </tr>
	 <tr>
	   <td class="label" valign="top">面向课程数：</td>
	   <td class="label" valign="top"><form:input id="courseAmount" path="courseAmount"></form:input></td>
       <td class="label" valign="top">课程名称：</td>
       <td><select name=courseName id="courseName" style="width: 280px" multiple="true" class="chzn-select" >          
            <c:forEach items="${ID2}" var="k">
			<option value="${k.courseNo }">${k.courseName }(${k.courseNo })</option></c:forEach>
		    </select>
	 </td>
	 </tr>
  
    <tr>
	   <td class="label" valign="top" >主要目标</td>
	   <%--<td colspan="3"><form:textarea path="primaryObjective" /></td>
	   --%><td colspan="8">
		<form:textarea id="activity_progress" path="primaryObjective" />
	  </td>
    </tr>
    <tr>
	   <td class="label" valign="top" >特色或创新</td>
	   <td colspan="3">
	   <form:textarea id="specialInnovation_progress" path="specialInnovation" />
	   </td>
	 </tr>
</tbody>
</table>
</fieldset>
		

<fieldset class="introduce-box">
			<legend>项目详情（带<font color=red>*</font>为必填项）</legend>
<table cellpadding="0" cellspacing="0" id="viewTable">
<tbody>
    <tr>
	   <td class="label" valign="top" width="40px">立项依据</td>
	   <td colspan="3">
	   <form:textarea id="projectBasis_progress" path="projectBasis" />
	   </td>
    </tr>
    <tr>
	   <td class="label" valign="top" width="40px">建设的基础</td>
	   <td colspan="3">
	   <form:textarea id="constructBasis_progress" path="constructBasis" />
	   </td>
    </tr>
    <tr>
	   <td class="label" valign="top" width="40px">计划进度</td>
	   <td colspan="3">
	   <form:textarea id="planSchedule_progress" path="planSchedule" />
	   </td>
    </tr>
    <tr>
	   <td class="label" valign="top" width="40px">预期成果</td>
	   <td colspan="3">
	   <form:textarea id="expectedResult_progress" path="expectedResult" />
	   </td>
    </tr>

</tbody>
</table>    
</fieldset>	
<!-- 参与人员 -->	
<fieldset class="introduce-box">
	<legend>参与人员</legend>
		<table>
			<tr>
				<td>参与人员添加</td>
				<td><input type="button"onclick="addAdmin('${pageContext.request.contextPath}/labconstruction/findUserByCname');" value="添加">
			</tr>
		  <table>
		  	<thead>
				<tr>
				<td>姓名</td>
				<td>性别</td>
				<td>年龄</td>
				<td>职务</td>
				<td>职称</td>
				<td>负责内容</td>
				<td>操作</td>
				</tr>
			</thead>	
			<tbody id="add_user">
						
			</tbody>
		  </table>
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
				<td>规格或型号</td>
				<td>数量</td>
				<td>单价（元）</td>
				<td>操作</td>
				</tr>
			</thead>	
			<tbody id="add_device">
						
			</tbody>
		  </table>
	</table>
</fieldset>
<!-- 实验项目 -->
<fieldset class="introduce-box">
	<legend>建成后可开设实验（实训）项目清单（带<font color=red>*</font>为必填项）</legend>
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
						
			</tbody>
		  </table>
	</table>
</fieldset>
 <!-- 审核附件 --> 
<fieldset class="introduce-box">
						<legend>审核附件</legend>
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
					<%--<input type="button" value="确定" onclick="sub('${pageContext.request.contextPath}/labconstruction/saveLabConstructApp');"/>
        			
        			--%><input class="btn-big" type="submit" value="提交">
        			<input class="btn btn-return" type="button" value="取消" onclick="window.history.go(-1);" />
			</form:form>
		</div>
				
	<!-- 下拉框的js -->
					<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
					<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
					<script type="text/javascript">
						    var config = {
						      '.chzn-select': {search_contains : true},
						      '.chzn-select-deselect'  : {allow_single_deselect:true},
						      '.chzn-select-no-single' : {disable_search_threshold:10},
						      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
						      '.chzn-select-width'     : {width:"95%"}
						    }
						    for (var selector in config) {
						      $(selector).chosen(config[selector]);
						    }
						</script>
					<!-- 下拉框的js -->
		<!-- 添加参与人员 -->
		<div id="addAdmin" class="easyui-window " title="添加参与人员" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 600px; height: 500px;">
		<div class="content-box">
		
			<table class="tb" id="my_show">
				<tr>
					<td>姓名：<input type="text" id="cname"> </td>
					<td><a onclick="addAdmin('${pageContext.request.contextPath}/labconstruction/findUserByCname');" >搜索</a>	</td>
					<td>
						<input type="button" value="添加" onclick="addUser('${pageContext.request.contextPath}/labconstruction/saveParticipants');">
					</td>
				</tr>
			</table>
		
		
		<table id="my_show">
					<thead>
						<tr>
							<th style="width:10% !important">选择</th>
							<th style="width:30% !important">姓名</th>
							<th style="width:30% !important">工号</th>
							<th style="width:30% !important">所属学院</th>
							
						</tr>
					</thead>
						
					<tbody id="user_body">
						
					</tbody>
					
			</table>
			</div>
		</div>
		<!-- 添加参与人员结束 -->
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
</body>
<!-------------列表结束----------->
</html>