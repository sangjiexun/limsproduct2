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
function importUser(){
  	$('#importUser').window({width:500,left:"30px",top:"30px"});
  	$("#importUser").window('open');
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
</script>
<script type="text/javascript">
	function exportAll(url) {
		document.form.action = url;
		document.form.submit();
	}
	// 分页
    function targetUrl(url) {
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
            <a href="javascript:void(0)">用户管理</a>
        </li>
    </ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent0">
		<!-- 查询、导出、打印 -->
            <div class="content-box">
		<div class="tool-box" style="overflow: initial;">
    <form:form name="form" method="Post" action="${pageContext.request.contextPath}/system/listUser?currpage=1" modelAttribute="user">
	<ul>
	    <li> 用户:
            <form:input path="username"></form:input>
        </li>
		<li><input type="submit" value="查询">
            <a href="${pageContext.request.contextPath}/system/listUser?currpage=1"><input class="cancel-submit" type="button" value="取消查询"></a>
            <input type="button" value="导出" onclick="exportAll('${pageContext.request.contextPath}/system/exportUserList?page=${page}');">
            <input type="button" value="打印" id="print">
        <c:if test="${userOperation eq 'true'}">
            <a onclick="importUser()"><input type="button" value="导入用户"></a>
        </c:if>
        </li>
	</ul>
</form:form>
</div>
            </div>
<!-- 查询、导出、打印 -->

		<div class="content-box">
				<%--<div class="title">用户管理</div>--%>
            <table  id="my_show"> 
                <thead>
                <tr>
                    <th>序号</th>
                    <th>用户工号</th>
                    <th>用户姓名</th>
                    <th>学院</th>
                    <th>用户身份</th>
                    <c:if test="${userOperation eq 'true'}">
                        <th>操作</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                	<c:forEach items="${userMap}" var="current"  varStatus="i">	
                        <tr>
                           <td>${i.count}</td>
                           <td>${current.username}</td>
                           <td>${current.cname}</td>
                           <td>${current.schoolAcademy.academyName}</td>
                           <td>
                           <c:choose>
                           		<c:when test="${current.userRole==0}">
                           			学生
                           		</c:when>
                           		<c:otherwise>
                           			教师
                           		</c:otherwise>
                           </c:choose>
                          </td>
                            <c:if test="${userOperation eq 'true'}">
                                <td>
                                    <a  onclick="cardBlind('${current.username}')"
                                       href="javascript:void(0)">卡号绑定</a>
                                        <%-- <a class="btn btn-normal" href="${pageContext.request.contextPath}/system/userDetailInfo?num=${current.username}&">信息展示</a>--%>
                                    <a href="${pageContext.request.contextPath}/system/deleteUser?username=${current.username}"
                                       onclick="return confirm('确定删除？');">删除</a>
                                    <c:if test="${sessionScope.selected_role eq 'ROLE_SUPERADMIN'}">
                                        <a href="${pageContext.request.contextPath}/system/resetPassword?username=${current.username}"
                                           onclick="return confirm('确定重置密码？');">密码重置</a>
                                    </c:if>
                                </td>
                            </c:if>
                        </tr>
                        </c:forEach>
                </tbody>
</table>
                    <!-- 分页导航 -->
                    <div class="page">
                总记录:<strong>${totalRecords}</strong>条&nbsp;
                总页数:<strong>${pageModel.totalPage}</strong>页 <input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp;
                <a onclick=targetUrl("${pageContext.request.contextPath}/system/listUser?currpage=${pageModel.firstPage}") target="_self"> 首页</a>
                <a onclick=targetUrl("${pageContext.request.contextPath}/system/listUser?currpage=${pageModel.previousPage}")  target="_self">上一页 </a>
                第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                <option value="${pageContext.request.contextPath}/system/listUser?currpage=${page}">${page}</option>
                <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                    <c:if test="${j.index!=page}">
                        <option value="${pageContext.request.contextPath}/system/listUser?currpage=${j.index}">${j.index}</option>
                    </c:if>
                </c:forEach></select>页
                <a onclick=targetUrl("${pageContext.request.contextPath}/system/listUser?currpage=${pageModel.nextPage}")  target="_self">下一页 </a>
                <a onclick=targetUrl("${pageContext.request.contextPath}/system/listUser?currpage=${pageModel.lastPage}") target="_self">末页 </a>&nbsp;
                <!-- 跳转到选择/输入的页面 --><%--
		   第 <select class="chzn-select" onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
		   <option value="${pageContext.request.contextPath}/system/listUser?currpage=${page}">${page}</option>
		   <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
           <c:if test="${j.index!=page}">
           <option value="${pageContext.request.contextPath}/system/listUser?currpage=${j.index}">${j.index}</option>
           </c:if>
           </c:forEach>
           </select> 页
           第--%>
            </div>
</div>
</div>
</div>
<div id= "importUser"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/system/importUser" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file" required="required" />
	         <input type="submit"  value ="开始上传" />
	         <br><br><hr><br>
		<a href="${pageContext.request.contextPath}/pages/importSample/userExample.xlsx">点击此处</a>，下载范例<br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/user.PNG" heigth ="100%" width="100%" />
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

