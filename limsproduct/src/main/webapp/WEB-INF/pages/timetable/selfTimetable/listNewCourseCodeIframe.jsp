<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.user-resources"/>
<html>
<head>
<meta name="decorator" content="iframe"/>
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/iconFont.css"	rel="stylesheet">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript">
$(document).ready(function(){
$("#print").click(function(){
$("#my_show").jqprint();
});
 $("#turn").click(function(){
 	var page=${pageModel.totalPage};
    var id=$("#page").val();
     if(id.length==0){
      alert("请输入数字！");
      }else{
      reg=/^[-+]?\d*$/;
       if(!reg.test(id)){    
          alert("对不起，您输入的整数类型格式不正确!");//请将“整数类型”要换成你要验证的那个属性名称！    
        }else{
            if(id<=page){
          	  window.location.href="${pageContext.request.contextPath}/userList?currpage="+id;
                }else{
              	  alert("对不起，您输入的整数不正确!");
                    }
        }    
      }
 	});
});
</script> 
<script type="text/javascript">
function exportSelect(){
	 document.form.action="exportExcalSelectUser";
	 document.form.submit();
	}
	
</script>
</head>
<body>

<div class="iStyle_RightInner">
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
<div class="tool-box">
    <input type="hidden" id="labroom" value="-1"/>
<form:form name="form" action="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=1" method="post" modelAttribute="timetableSelfCourse">
<table class="list_form">
<tr>
   <td>
   <select id="schoolTerm.id" name="schoolTerm.id" >
       <c:forEach items="${schoolTerms}" var="current">
    	    <c:if test="${current.id == termId}">
    	       	<option value ="${current.id}" selected>${current.termName} </option>
    	    </c:if>
    	    <c:if test="${current.id != termId}">
    	    	<option value ="${current.id}" >${current.termName} </option>
    	    </c:if>		
        </c:forEach>
    </select>
         搜索课程:
   <form:input id="schoolCourseInfo.courseName" path="schoolCourseInfo.courseName" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\w]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5\w]/g,''))"/>
    </td>
    <td>
    <input type="submit" value="查询">
    </td>
    <td>
    <a  class="btn btn-common" style="margin-left:15px;" href="${pageContext.request.contextPath}/timetable/selfTimetable/newCourseCodeIframeDetail?id=-1" >新建项目</a>
   </td>    
</tr>
</table>
</form:form>
</div>
<div class="content-box">
<div class="title">开放项目管理列表<div style="float: right;"><a class="btn" href="${pageContext.request.contextPath}/timetable/selfTimetable/listSelfTimetable">查看排课</a></div></div>
<table class="tb" cellspacing="0" id="my_show"> 
<thead>
<tr>
    <th>序号</th>
    <th>课程编号</th>
    <th>课程名称</th>
    <th>项目编号</th>
    <th>所属学期</th>
    <th>安排教师</th>
    <th>学生名单</th>
    <th>操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${timetableSelfCourseMap}" var="current"  varStatus="i">	
<tr>
   <td>${i.count}</td>
   <td>${current.schoolCourseInfo.courseNumber}</td>
   <td>${current.schoolCourseInfo.courseName}</td>
   <td>${current.courseCode}</td>
   <td>${current.schoolTerm.termName}</td>
   <td>${current.user.cname}</td>
   <td>
   <a class="btn btn-common" href='${pageContext.request.contextPath}/timetable/selfTimetable/listTimetableCourseStudents?id=${current.id}'>名单：${current.timetableCourseStudents.size()}</a>
   </td>
   <td>
       <c:set var="flag" value=""/>
       <c:forEach items="${current.timetableAppointments}" var="ta">
           <c:if test="${ta.status eq 1}">
               <c:set var="flag" value="1"/>
           </c:if>
       </c:forEach>
       <c:if test="${flag ne 1}">
           <a href='${pageContext.request.contextPath}/timetable/selfTimetable/deleteTimetableSelfCourse?id=${current.id }'>删除</a>&nbsp;&nbsp;
           <a href='${pageContext.request.contextPath}/timetable/selfTimetable/newCourseCodeIframeDetail?id=${current.id }'>编辑</a>
       </c:if>
   <%--<a href="${pageContext.request.contextPath}/tcoursesite/createTCourseSite?type=2&timetableSelfCourseId=${current.id}">预约</a>--%>
   <%--<a href="javascript:void(0)" onclick="showTimetable('${termId}', ${weekday}, '1', '${current.id }')">预约</a>--%>
   <a href="javascript:void(0)" onclick="arrange('${termId}', ${weekday}, '1', '${current.id }')">安排</a>
   </td>
</tr>
</c:forEach>
</tbody>
</table>
<c:choose><c:when test='${newFlag}'>

共${totalRecords}条 共${pageModel.totalPage}页
<a href="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=${pageModel.firstPage}" target="_self">首页</a>				    
<a href="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=${pageModel.previousPage}" target="_self">上一页</a>
 第
<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;"><option value="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=${page}">${page}</option>
    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
        <c:if test="${j.index!=page}">
        <option value="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=${j.index}">${j.index}</option>
        </c:if>
    </c:forEach>
</select>页
<a href="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=${pageModel.nextPage}" target="_self">下一页</a>
<a href="${pageContext.request.contextPath}/timetable/selfTimetable/listCourseCodes?currpage=${pageModel.lastPage}" target="_self">尾页</a>
</div>

</c:when><c:otherwise>

</c:otherwise></c:choose>    
</div>
</div>
</div>
</div>
</div>
</div>            
</div>
<script type="text/javascript">
    //弹出排课界面的方法
    function showTimetable(term,weekday,classids,courseCode) {
        var labroom = $("#labroom").val();
        <%--&lt;%&ndash;var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term=' + term + '&weekday='+weekday+'&classids='+classids+'&courseCode=' + courseCode + '&labroom=' + labroom + '" style="width:100%;height:100%;"></iframe>'&ndash;%&gt;--%>
        <%--var con = '<iframe scrolling="yes" id="message" frameborder="0"  src="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term=' + term + '&labroom='+ labroom +'&weekday='+weekday + '&classids='+classids+'&courseCode='+courseCode+'" style="width:100%;height:100%;"></iframe>'--%>
        <%--$("#showTimetable").html(con);--%>
        <%--//获取当前屏幕的绝对位置--%>
        <%--var topPos = window.pageYOffset;--%>
        <%--//使得弹出框在屏幕顶端可见--%>
        <%--$('#showTimetable').window({left:"0px", top:topPos+"px"});--%>
        <%--$("#showTimetable").window('open');--%>
        <%--parent.location.href="${pageContext.request.contextPath}/timetable/selfTimetable/doSelfTimetableIframe?term=" + term + "&labroom="+ labroom +"&weekday="+weekday + "&classids="+classids+"&courseCode="+courseCode;--%>
        <%--parent.location.href="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term=16&weekday=1&classids=1&courseCode=11&labroom=-1&tableAppId=0;--%>
        window.location.href="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeNoGroupSelfTimetable?term=" + term + "&labroom="+ labroom +"&weekday="+weekday + "&classids="+classids+"&courseCode="+courseCode + "&tableAppId=0";
    }

    function arrange(term,weekday,classids,courseCode) {
        var labroom = $("#labroom").val();
        window.location.href="${pageContext.request.contextPath}/timetable/selfTimetable/doIframeGroupSelfTimetable?term=" + term + "&labroom="+ labroom +"&weekday="+weekday + "&classids="+classids+"&courseCode="+courseCode;
    }
</script>
</body>
</html>

