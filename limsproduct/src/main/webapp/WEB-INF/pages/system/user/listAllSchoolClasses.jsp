<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<meta name="decorator" content="iframe"/>

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
 <script type="text/javascript">
if("${result}" !=''){
var ar="${result}".split(";").length-1;
var i=0;
for(i;i<ar;i++){
alert("${result}".split(";")[i]+";");
}
}else{
}
</script>
 <script type="text/javascript">
  $(function(){
     $("#print").click(function(){
	$("#my_show").jqprint();
	});
	$("#lab_name").focus();
	$("#lab_name").bind('keydown', function (e) {

            var key = e.which;

            if (key == 13) {

                e.preventDefault();
              document.form.action="${pageContext.request.contextPath}/selectLabList";
	           document.form.submit();

            }

        });
  });

	function getSchoolAcademy(){
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/findSchoolMajorBySchoolAcademy",
		data: {'schoolAcademy':$("#schoolAcademy").val()},
		success:function(data){
		$("#schoolMajor").html(data.schoolMajor);
        $("#schoolMajor").trigger("liszt:updated");
		}
	}); 
	
	}
	
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/system/listSchoolClasses?currpage=1";
	}
 </script>
 <script type="text/javascript">
function importClasses(){
//获取当前屏幕的绝对位置
 var topPos=window.pageYOffset;
$("#import").window({left:"0px",top:topPos+"px"});
$("#import").window('open'); 
}
//分页跳转
function targetUrl(url) {
    document.queryForm.action = url;
    document.queryForm.submit();
}
</script>
 
</head>
<body>

<div class="right-content">
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message
                code="left.system.management"/></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message
                code="left.class.management"/></a></li>
	  </ul>
	</div>
  </div>
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">班级列表</a>
        </li>
        <a class="btn btn-new"  href= '${pageContext.request.contextPath}/newSchoolClasses' style="margin:1px 0 0 5px;">新建</a>
        <input class="btn btn-new" type="button" value="导入" onclick="importClasses();"/>
    </ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
			<%--<div class="title">--%>
				<%--<div id="title">班级列表</div>--%>
			<%--</div>   	--%>
				<form:form name="queryForm" action="${pageContext.request.contextPath}/system/listSchoolClasses?currpage=1" method="post" modelAttribute="schoolClasses">
					<div class="tool-box">
					    <ul>
                            <li>所属学院:
									    	<form:select class="chzn-select" id="schoolAcademy" path="academyNumber"   cssStyle=" width:180px;">
                                            <form:option value="" label ="--请选择--"/>
  											<form:options items="${academy}" />
  											</form:select>
											</li>
                            <li>所属专业:
									    	<form:select class="chzn-select" id="schoolMajor"  path="majorNumber" cssStyle=" width:180px;">
                                            <form:option value="" label ="--请选择--"/>
  											<form:options items="${major}" />
  											</form:select>
											</li>
										<li>入学年份:

									    	<form:input type="text"   id ="classGrade" path="classGrade" />
    					                </li>
                            <li>
                                <%--<a class="btn btn-new"  href= '${pageContext.request.contextPath}/newSchoolClasses' style="margin:1px 0 0 5px;">新建</a>--%>
                                <%--<input type="button" value="导入" onclick="importClasses();"/>--%>
                                <input type="submit" value="查询"/>
                                <input class="cancel-submit" type="button" value="取消查询"  onclick="cancel()"/>
                            </li>
		            </div>
				</form:form>
            </div>
            <div class="content-box">
                <input type="hidden" id="academy" value="${academy }">
                <input type="hidden" id="major" value="${major }">
                <input type="hidden" id="major" value="${major }">
    		<!-- 实验室列表 --> 		
            <table  class="tb"  id="my_show"> 
                <thead>
                    <tr>
                        <th><center>班级编号</center></th>
					    <th><center>班级名称</center></th>
					    <th><center>入学年份</center></th>
					      <th><center>所属专业</center></th>
					        <th><center>所属学院</center></th>
					    <th><center>班级人数</center></th>
					    <th>操作</th> 
                </thead>
                <tbody>
                	<c:forEach items="${listSchoolClasses}" var="current"  varStatus="i">
                           <tr>
                           <td><center>${current[0]}</center></td>
						    <td><center>${current[1]}</center></td>
						    <td><center>${current[2]}</center></td>
						    <td><center>${current[3]}</center></td>
						    <td><center>${current[4]}</center></td>
						    <td ><center><a href='${pageContext.request.contextPath}/numberSchoolClasses?classNumber=${current[0]}'>${current[5]}</a></center></td>
                           <td>
                            <a href='${pageContext.request.contextPath}/editSchoolClasses?classNumber=${current[0]}'>修改</a>
                            <a href='${pageContext.request.contextPath}/deleteSchoolClasses?classNumber=${current[0]}' onclick="return confirm('确定删除吗？')">删除</a>
                           </td>
                           </tr>
                    </c:forEach> 
                </tbody>
            </table>

                <div class="page">
                    ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/system/listSchoolClasses?currpage=1')"
                       target="_self">首页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${pageModel.previousPage}')"
                       target="_self">上一页</a>
                    <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp; <input
                        type="hidden" value="${currpage}" id="currpage"/>
                    &nbsp;第
                    <select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${currpage}">${currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1"
                                   varStatus="j" var="current">
                            <c:if test="${j.index!=currpage}">
                                <option value="${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach>
                    </select>页&nbsp;

                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${pageModel.nextPage}')"
                       target="_self">下一页</a>
                    <a href="javascript:void(0)"
                       onclick="targetUrl('${pageContext.request.contextPath}/system/listSchoolClasses?currpage=${pageModel.lastPage}')"
                       target="_self">末页</a>
                </div>
</div>
</div>
</div>
</div>

<div id= "import" class ="easyui-window panel-body panel-body-noborder window-body" title= "导入班级" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
           <form:form action=" ${pageContext.request.contextPath}/importClasses" enctype ="multipart/form-data">
           <br>
           <p><b>导入班级(xls) </b></p>
           <hr>
           <br>
           <input type= "file" name ="file"  required="required" />
           <input type= "submit" value ="提交">
           <br> 下载：<a href=" ${pageContext.request.contextPath}/pages/importSample/importClasses.xls">下载班级范例 </a><br>
                   范例： <br>
                    <img src=" ${pageContext.request.contextPath}/images/importsample.png" heigth ="90%" width="90%" />
           </form:form>
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
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>