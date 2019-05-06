<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<fmt:setBundle basename="bundles.projecttermination-resources" />

<html>
<head>
<meta name="decorator" content="iframe" />
<title><fmt:message key="html.title" />
</title>
<!--分页js  -->
 <script type="text/javascript"
	src="${pageContext.request.contextPath}/js/operation/operationoutline.js"></script>
<!--分页js  -->

<!-- 打印插件的引用 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	$("#print").click(function(){
	$("#ssssss").jqprint();
	})
	});
	
function exportAlls(){
  window.location.href="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=1";
}

    function exportAll(url){
        var num=$("#currpage").val();
        document.form.action=url;
        document.form.submit();
    }
//导入选中
    function submitForm()
    {
        var flag = false;  //是否有checkbox被选中
        var ids = "";
        $("input[name='items']:checked").each(function(){
            ids += $(this).val()+",";
            flag = true;
        });

        if(flag)
        {
            if(ids.length > 0)
            {
                ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
            }
            if($("#term").val()=="")
            {
                alert("请选择导入学期！");
                return false;
            }

            document.itemsForm.action="${pageContext.request.contextPath}/operation/importOperationproject?termId="+$("#term").val()+"&itemIds="+ids;
            document.itemsForm.submit();
        }
        else
        {
            alert("至少选择一个实验项目！");
        }
    }

    //全选
    function checkAll()
    {
        if($("#check_all").attr("checked"))
        {
            $(":checkbox").attr("checked", true);
        }
        else
        {
            $(":checkbox").attr("checked", false);
        }
    }

    //页面跳转
    function first(){
        document.form.action="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=1";
        document.form.submit();
    }

    function previous(){
        var page=$("#currpage").val();
        if(page==1){
            page=1;
        }else{
            page=page-1;
        }
        document.form.action="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage="+page;
        document.form.submit();
    }

    function next(){
        var totalpage=$("#totalpage").val();
        var page=parseInt($("#currpage").val());
        if(page==totalpage){
            page=totalpage;
        }else{
            page=page+1;
        }
        document.form.action="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage="+page;
        document.form.submit();
    }

    function last(){
        var page=$("#totalpage").val();
        var tage=parseInt($("#tage").val());
        document.form.action="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage="+page;
        document.form.submit();
    }
</script>
<!-- 打印插件的引用 -->

<%--<link type="text/css" rel="stylesheet"--%>
	<%--href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/favicon.gif" />
	<style type="text/css">
		.btn-new {
			margin-top: 5px!important;
		}
	</style>
</head>

<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.item.outline"/></a></li>
			</ul>
		</div>
	</div>


	<!-- 结项申报列表 -->
	<!-- <div class="tab"> -->
	<div class="right-content">
		<div id="TabbedPanels1" class="TabbedPanels">
			<div class="TabbedPanelsContentGroup">
				<div class="TabbedPanelsContent">
					<ul class="TabbedPanelsTabGroup">
						<li class="TabbedPanelsTab1 selected" id="s1">
							<a href="javascript:void(0)">实验大纲管理</a>
						</li>
						<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_TEACHER"><a class="btn btn-new"
																												href="${pageContext.request.contextPath}/teaching/coursesite/newoperationproject">新建</a></sec:authorize>
						<input class="btn btn-new" type="button" value="打印" id="print">
						<input class="btn btn-new" type="button" value="导出"
							   onclick="exportAll('${pageContext.request.contextPath}/teaching/coursesite/outline/exportOutline?page=${currpage}');">
					</ul>
					<div class="content-box">
						<%--<div class="title">--%>
							<%--<div id="title">实验大纲管理</div>--%>
							<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXPERIMENTALTEACHING,ROLE_TEACHER"><a class="btn btn-new"--%>
								<%--href="${pageContext.request.contextPath}/teaching/coursesite/newoperationproject">新建</a></sec:authorize>--%>
							<%--<input class="btn btn-new" type="button" value="打印" id="print">--%>
							<%--<input class="btn btn-new" type="button" value="导出"--%>
								<%--onclick="exportAll('${pageContext.request.contextPath}/teaching/coursesite/outline/exportOutline?page=${currpage}');">--%>
						<%--</div>--%>
						<div class="tool-box">
							<form:form name="form"
								action="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=1"
								method="post" modelAttribute="operationOutline">
								<ul>
								   	<li>
									   实验大纲名称:<form:input path="labOutlineName"  />
									</li>
								</ul>
								<ul>
								<li>学期:
									<form:select id="searchterm" name="searchterm" path="schoolTerm.id">
										<c:forEach items="${schoolTerm}" var="current">
											<form:option value ="${current.id}" >${current.termName}</form:option>
										</c:forEach>
    								</form:select>
								</li>
        						<li style="overflow:hidden;">
        						       <input style="margin: 0 0 0 25px;" type="button"
										onclick="exportAll('${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=1');"
										value="搜索">
									<input class="cancel-submit" type="button" onclick="exportAlls()" value="取消搜索">
										</li>
									<li>导入学期:
										<select id="term" name="term" >
											<option value="">请选择</option>
											<c:forEach items="${schoolTerm}" var="t">
												<option value="${t.id}">${t.termName}</option>
											</c:forEach>
										</select>
									</li>
									<li><input type="button" value="导入选中" onclick="submitForm();"/></li>
								</ul>
							</form:form>
						</div>
						<div class="content-box">
							<form name="itemsForm" method="post">
							<table  id="ssssss">
								<thead>
									<tr align="left">
										<th class="thead" width="3%">
											<input id="check_all" type="checkbox" onclick="checkAll();"/>
										</th>
										<th class="thead" width="3%">序号</th>
										<th class="thead" width="15%">大纲名称</th>
										<th class="thead" width="10%">课程编号</th>
										<th class="thead" width="15%">课程名称</th>
										<th class="thead" width="5%">教师</th>
										<th class="thead" width="10%">学院</th>
										<th class="thead" width="10%">学期</th>
										<th class="thead" width="10%">操作</th>

									</tr>
								</thead>
								<tbody>
 
									<!-- 根据control层传递参数projects，对未结项项目列表并操作 -->
									<c:forEach items="${Outlinelist}" varStatus="i" var="s">
										<c:choose>
											<c:when test="${(i.count) % 2 == 0}">
												<c:set var="rowclass" value="rowtwo" />
											</c:when>
											<c:otherwise>
												<c:set var="rowclass" value="rowone" />
											</c:otherwise>
										</c:choose>
										<tr align="left" class="${rowclass}">
											<td><input id="check_${s.id}" name="items" type="checkbox" value="${s.id}"/></td>
											<td>${i.index+1 }</td>
											<td>${s.labOutlineName}</td>
											<td>${s.schoolCourseInfoByClassId.courseNumber}</td>
											<td>${s.schoolCourseInfoByClassId.courseName}</td>
											<td>${s.user.cname}</td>
											<td>${s.schoolAcademy.academyName}</td>
											<td>${s.schoolTerm.termName }</td>
											<td>
											<a href="${pageContext.request.contextPath}/teaching/coursesite/checkout?idkey=${s.id}"  > 查看</a>
											<sec:authorize ifAnyGranted="ROLE_EXPERIMENTALTEACHING,ROLE_SUPERADMIN">
											<a href="${pageContext.request.contextPath}/teaching/coursesite/editoutline?idkey=${s.id}"  > 编辑</a>
											<a href="${pageContext.request.contextPath}/teaching/coursesite/printoutline?idkey=${s.id}">导出</a>
											<a href="${pageContext.request.contextPath}/teaching/coursesite/delectuotline?idkey=${s.id}" onclick="return confirm('您确认删除吗？')">删除</a>
											</sec:authorize>
											<%--<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EXCENTERDIRECTOR,ROLE_TEACHER">
											<!-- <a href="javascript:void(0)"  >下载</a> -->
											</sec:authorize>
											--%></td>
										</tr>
									</c:forEach>
								</tbody>
								<!-- 分页导航 -->

							</table>
							</form>
							<div class="page">${totalRecords}条记录 &nbsp;共${pageModel.totalPage}页
								<input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp;
								<input type="hidden" value="${currpage}" id="currpage" />
								<a href="javascript:void(0)" onclick="first();" target="_self">首页</a>
								<a href="javascript:void(0)" onclick="previous();" target="_self">上一页</a>
								<a href="javascript:void(0)">
									<!-- 跳转到选择/输入的页面 -->
								第 <select class="chzn-select" onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
									<option value="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=${currpage}">${currpage}</option>
									<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
										<c:if test="${j.index!=currpage}">
											<option value="${pageContext.request.contextPath}/operation/experimentalmanagement?currpage=${j.index}">${j.index}</option>
										</c:if>
									</c:forEach>
								</select> 页
								</a>
								<input type="hidden" value="${currpage}" id="currpage" />
								<a href="javascript:void(0)" onclick="next();" target="_self">下一页</a>
								<a href="javascript:void(0)" onclick="last();" target="_self">末页</a>
							</div>
							</div>
						</div>

					</div>
				</div>

			</div>
		</div>
	</div>
</body>
<!-------------列表结束----------->
</html>