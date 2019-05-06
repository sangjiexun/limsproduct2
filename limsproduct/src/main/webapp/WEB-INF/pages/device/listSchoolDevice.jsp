<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

<head>
<meta name="decorator" content="iframe" />
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<!-- 弹窗 -->
<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
 <script type="text/javascript">
	$(document).ready(function(){
	$("#print").click(function(){
	$("#my_show").jqprint();
	});
});
</script> 
<script type="text/javascript">
function exportSelect(){
	 document.form.action="exportExcelSelectUser";
	 document.form.submit();
	}
function importDevice(){
  	$('#importDevice').window({width:500,left:"30px",top:"30px"});
  	$("#importDevice").window('open');
  } 
//AJAX验证是否有不规范的字段
function checkRegex(){
	var formData = new FormData(document.forms.namedItem("importForm"));
	$.ajax({
        url:"${pageContext.request.contextPath}/labRoom/checkRegex",
        type: 'POST',  
        data: formData,  
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false, 
        
        error: function(request) {
            alert("暂时不能判断该文档是否规范");
        },
        success: function(data) {
        	if(data == 'success'){
        		// do nothing
        	}else if(data == 'nullError'){
        		alert("文档不规范，请完善必填项！");
        		var obj = document.getElementById('file');
                obj.outerHTML = obj.outerHTML; //兼容IE8
        	}else if(data == 'dateError'){
        		alert("出生年月格式填写有误！");
        		var obj = document.getElementById('file');
                obj.outerHTML = obj.outerHTML; //兼容IE8
        	}else if(data == '"categoryError"'){
        		alert("请检查上传文档中的药品类型格式，只能填写'试剂'或'耗材'");
        		var obj = document.getElementById('file');
                obj.outerHTML = obj.outerHTML; //兼容IE8
        	}else{
        		alert("文件格式错误，请您上传excel格式的文档");
        		var obj = document.getElementById('file');
                obj.outerHTML = obj.outerHTML; //兼容IE8
        	}
        }
    });
}
//弹出卡号绑定
function cardBlind(username){
    layer.ready(function(){
        layer.open({
            type: 2,
            title: '卡号绑定',
            fix: true,
            maxmin:true,
            shift:-1,
            closeBtn: 1,
            shadeClose: true,
            move:false,
            maxmin: false,
            area: ['1000px', '420px'],
            content: '../system/cardBlind?username='+username,
            end: function(){
            }
        });
    });
}

// 设备详情
function labRoomDeviceInfo(id) {
    var index = layer.open({
        type:2,
        title:'详情',
        maxmin:true,
        shadeClose:true,
        content:"${pageContext.request.contextPath}/device/viewDeviceDetails/" + id ,
    });
    layer.full(index);
}
</script>
<script type="text/javascript">
	function exportAll(url) {
		document.form.action = url;
		document.form.submit();
	}
</script>
</head>
<body>
<!-- 导航栏 -->
<div class="navigation">
<div id="navigation">
<ul>
<li><a href=""><spring:message code="left.system.management" /></a></li>
<li class="end"><a href="${pageContext.request.contextPath}/system/listUser?currpage=1"><spring:message code="left.user.management" /></a></li>
</ul>
</div>
</div>
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">设备信息</a>
        </li>
    </ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent0">
		<!-- 查询、导出、打印 -->
            <div class="content-box">
		<div class="tool-box" style="overflow: initial;">
    <form:form name="form" method="Post" action="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=1" modelAttribute="schoolDevice">
	<ul>
	    <li> 设备编号:
            <form:input path="deviceNumber"></form:input>
        </li>
		<li><input type="submit" value="查询">
            <a href="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=1"><input class="cancel-submit" type="button" value="取消查询"></a>
            <a onclick="importDevice()"><input type="button" value="导入设备"></a>
        </li>
	</ul>
</form:form>
</div>
            </div>
<!-- 查询、导出、打印 -->

		<div class="content-box">
				<div class="title">设备信息</div>
            <table  id="my_show"> 
                <thead>
                <tr>
                    <th>仪器编号</th>
                    <th>仪器名称</th>
                    <th>仪器型号</th>
                    <th>仪器规格</th>
                    <th>所属实训室</th>
                    <th>仪器价格</th>
                    <th>领用人</th>
                    <th>设备入账日期</th>
                    <th>设备状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                	<c:forEach items="${schoolDeviceList}" var="current"  varStatus="i">
                        <tr>
                           <td>${current.deviceNumber}</td>
                           <td>${current.deviceName}</td>
                           <td>${current.devicePattern}</td>
                           <td>${current.deviceFormat}</td>
                           <td>${current.systemRoom}</td>
                           <td>${current.devicePrice}</td>
                           <td>${current.userByUserNumber.cname}</td>
                           <td><fmt:formatDate value="${current.deviceAccountedDate.time}" pattern="yyyy-MM-dd"/></td>
                           <td>${current.CDictionaryByUseStatus.CName}</td>
                            <td><a class="btn btn-new" href="javascript:void(0)" onclick="labRoomDeviceInfo(${current.id})"; >详情</a></td>
                        </tr>
                        </c:forEach>
                </tbody>
</table>
            <!-- 分页导航 -->
            <div class="page">
                总记录:<strong>${totalRecords}</strong>条&nbsp;
                总页数:<strong>${pageModel.totalPage}</strong>页 <input type="hidden" value="${pageModel.lastPage}" id="totalpage" />
                <a href="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=${pageModel.firstPage}" target="_self"> 首页</a>
                <a href="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=${pageModel.previousPage}"  target="_self">上一页 </a>
                第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                <option value="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=${page}">${page}</option>
                <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                    <c:if test="${j.index!=page}">
                        <option value="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=${j.index}">${j.index}</option>
                    </c:if>
                </c:forEach></select>页
                <a href="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=${pageModel.nextPage}"  target="_self">下一页 </a>
                <a href="${pageContext.request.contextPath}/system/schoolDeviceList?currpage=${pageModel.lastPage}" target="_self">末页 </a>&nbsp;
            </div>
</div>
</div>
</div>
<div id= "importDevice"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/system/importSchoolDevice" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file" required="required" />
	         <input type="submit"  value ="开始上传" />
	         <br><br><hr><br>
		<a href="${pageContext.request.contextPath}/pages/importSample/importSchoolDevice.xlsx">点击此处</a>，下载范例<br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/schoolDevice.png" heigth ="100%" width="100%" />
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
</body>
</html>

