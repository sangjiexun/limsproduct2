<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<script type="text/javascript">
$(function(){
	var s=${isAudit};
	 if(1==s){
	 $("#s3").addClass("TabbedPanelsTab selected");
	 $("#s1").addClass("TabbedPanelsTab");
	  $("#s2").addClass("TabbedPanelsTab");
	 }
	  if(2==s){
	 $("#s2").addClass("TabbedPanelsTab selected");
	 $("#s1").addClass("TabbedPanelsTab ");
	    $("#s3").addClass("TabbedPanelsTab");
	 }

		});
	//首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}

</script>

</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">我的工作平台</a></li>
						<li class="end"><a href="javascript:void(0)">我的课表</a></li>
					</ul>
				</div>
			</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
		<li class="TabbedPanelsTab1" id="s1"><a href="${pageContext.request.contextPath}/personal/message/mySelfTimetable?tage=0&currpage=1">我的课表</a></li>
		<li class="TabbedPanelsTab1" id="s2"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2">我的申请</a></li>
		<li class="TabbedPanelsTab1" id="s3"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=1">我的审核</a></li>
	</ul>
	<%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=0&page=1&isaudit=${isAudit}">全部</a>
		</li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=2&page=1&isaudit=${isAudit}">审核中</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=4&page=1&isaudit=${isAudit}">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=3&page=1&isaudit=${isAudit}">审核拒绝</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=1&page=1&isaudit=${isAudit}">未审核</a>
		</li>
	</ul>
	--%>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
    	<div class="content-box">
            <table id="my_show">
                <thead>
                    <tr>
                        <th>课题组编号</th>
                        <th>课题组名称</th>
                        <th>项目名称</th>
                        <th>调课星期</th>
                        <th>调课周次</th>
                        <th>调课节次</th>
                        <th>调课地点</th>
                        <th>上课教师</th>
                        <th>调课人</th>
                        <th>审核状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
              		<c:forEach items="${timetableAppointmentChanges}" var="current" varStatus="i">
                      <tr>
                      	<td>${current.timetableAppointment.courseCode}</td>
                      	<td>${current.timetableAppointment.schoolCourseInfo.courseName}</td>
                      	<td>${current.itemName}</td>
                      	<td>${current.weekday}</td>
                      	<td>${current.week}</td>
                      	<td>${current.startClass}-${current.endClass}</td>
                      	<td>${current.address}</td>
                      	<td>${current.teacher}</td>
                      	<td>${current.user.cname}</td>
                      	<td>
                      	<c:if test="${current.status==1 }">
                      		未审核
                      	</c:if>
                      	<c:if test="${current.status==2 }">
                      		审核中
                      	</c:if>
                      	<c:if test="${current.status==3 }">
                      		审核拒绝
                      	</c:if>	
                      	<c:if test="${current.status==4 }">
                      		审核通过
                      	</c:if>
                      	</td>
                      	<td>
                     <c:if test="${isAudit eq 1}">
				     	 <a href="${pageContext.request.contextPath}/timetableChangeAudit/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">查看</a>
				     </c:if>
				     <c:if test="${current.buttonMark eq 2 and current.flag eq 1}">
				     	 <a href="${pageContext.request.contextPath}/timetableChangeAudit/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">系主任审核</a>
				     </c:if>
				     <c:if test="${current.buttonMark eq 3}">
				     	 <a href="${pageContext.request.contextPath}/timetableChangeAudit/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">实训室管理员审核</a>
				     </c:if><%--
				     <c:if test="${current.buttonMark eq 4}">
				     	 <a href="${pageContext.request.contextPath}/timetableChangeAudit/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">实训中心主任审核</a>
				     </c:if>
				     --%><c:if test="${current.buttonMark eq 5 and current.flag eq 1}">
				     	 <a href="${pageContext.request.contextPath}/timetableChangeAudit/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">实训部主任审核</a>
				     </c:if>
				     <c:if test="${isAudit eq 2}">
				     <a href="${pageContext.request.contextPath}/timetableChangeAudit/checkButton?id=${current.id}&tage=${tage}&state=${current.state}&page=${page}">查看详情</a>
				     </c:if>
	    			</td>
                      </tr>
              		</c:forEach>
                </tbody>
            </table>
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=1&tage=${tage }&isaudit=${isAudit }')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=${page}&tage=${tage }&isaudit=${isAudit }">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/timetable/timetableChangeAuditList?page=${j.index}&tage=${tage }&isaudit=${isAudit }">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=${tage }&isaudit=${isAudit }&page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/timetable/timetableChangeAuditList?tage=${tage }&isaudit=${isAudit }&page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
<!-- 分页模块 -->
</div>

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
    
</script>
<!-- 下拉框的js -->

</body>
</html>


