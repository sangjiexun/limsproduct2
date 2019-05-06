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
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css"/> 
<script type="text/javascript">
var fix=document.getElementById("feeAmount").value;
alert(fix);
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
function addDevice(url){
	var deviceName=document.getElementById("deviceName").value;
	var thisUrl=url+"?deviceName="+deviceName+"&page=1";
	$.ajax({
      	url:thisUrl,
      	type:"POST",
      	success:function(data){//AJAX查询成功
   			document.getElementById("device_body").innerHTML=data;
   			$("#addDevice").window({top: 1300});
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
	 $("#searchFile").window({top: window.pageYOffset+100});
	 $("#searchFile").window('open');
	 $("#file_upload").uploadify({
           'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
           //提交的controller和要在火狐下使用必须要加的id
           'uploader':'${pageContext.request.contextPath}/annualBudget/uploadAnnualBudgetFile;jsessionid=<%=session.getId()%>',		
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
//获取申请附件id数组
function getDocumentIdArray(){
	var documentIdArray=new Array();
	$("input[name='docId']:hidden").each(function() { //遍历所有的name为docId的 hidden  
		documentIdArray.push($(this).val()); //将选中的值 添加到 array中 
    });  
    return documentIdArray;
}
</script>
<script type="text/javascript">
function sub(url){
    //申请附件id
    var documentIdArray=getDocumentIdArray();
    
    //注意此处的url长度，每个浏览器可以解析的url长度不同
    document.subForm.action=url+"?documentIdArray="+documentIdArray;
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
</head>
<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)"><spring:message code="left.labconstruction.management"/></a></li>
						<li class="end"><a href="javascript:void(0)">相关文档</a></li>
					</ul>
				</div>
			</div>
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<div class="title">
						<div id="title"> 相关文档操作	 </div>
						<a class="btn btn-return" onclick="window.history.go(-1)">返回</a>
					</div>
			 <div class="content-box">
<form:form name="subForm" method="post" modelAttribute="annualBudget" >
<!-- <fieldset class="introduce-box">
<legend>基础信息（带<font color=red>*</font>为必填项）</legend>
<table cellpadding="0" cellspacing="0" id="viewTable">
<tbody>
    <tr>
     	<form:hidden path="id"/>
	   	
	   	<td class="label" valign="top">系部：</td>
	   	<td><form:input path="academyName" /></td>
	   	<td class="label" valign="top">项目名称：</td>
	   	<td><form:input path="projectName" /></td>
	</tr>
	<tr>
		<td class="label" valign="top">项目来源：</td>
	   	<td><form:input path="projectSource" /></td>
	   	<td class="label" valign="top">建设年度：</td>
	   	<td><form:input path="constructYear" /></td> 
	</tr>
	<tr>
		<td class="label" valign="top">项目经费（万元）：</td>
	   	<td><form:input path="projectFunds" /></td>
	   	<td class="label" valign="top">项目负责人：</td>
	   	<td><form:input path="manager" /></td> 
	</tr>
	<tr>
		<td class="label" valign="top">项目进展：</td>
	   	<td><form:input path="projectProceed" /></td>
		<td class="label" valign="top">项目状态：</td>
	   	<td><form:input path="status" /></td>
	   	<td>
		   	<form:select path="status" class="chzn-select">
		   		<form:option value="验收">验收</form:option>
		   		<form:option value="未验收">未验收</form:option>
		   	</form:select>
		</td>
	</tr>
	<%-- <tr>
	   	<td class="label" valign="top">最晚验收时间：</td>
	   	<td><form:input onclick="new Calendar().show(this);" path="deadline" /></td>
	</tr> --%>
</tbody>
</table>
</fieldset> -->
<form:hidden path="id"/>
<fieldset class="introduce-box">
	<legend>项目附件</legend>
	<table id="document">
		<tr><input type="button" onclick="uploadDocument()" value="上传附件"/></tr>
		<tr >
			 <td>文档名称</td>
			 <td>操作&nbsp;</td>
		 </tr>
	</table>
	 <table>
		  <tbody id="doc">
		  	<c:forEach items="${annualBudget.commonDocuments}" var="doc" varStatus="i">
				<tr >
			 	<td>${doc.documentName}</td>
			 	<td><a onclick="return confirm('确认要删除吗？')" href="${pageContext.request.contextPath}/annualBudget/deleteCommonDocument?id=${doc.id}&annualId=${annualBudget.id}">删除</a> </td>
			 	<td><a href="${pageContext.request.contextPath}/annualBudget/downloadFile?idkey=${doc.id}">下载</a></td>
				</tr>
			</c:forEach>	
		  </tbody>
	 </table>	
</fieldset>

                           
	<tr><td colspan="5"></td></tr>
			<input type="button btn-big" value="确定" onclick="sub('${pageContext.request.contextPath}/annualBudget/saveDocAnnualBudget');"/>
   			<input type="button btn-return" value="取消" onclick="window.history.go(-1);" />
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
</html>