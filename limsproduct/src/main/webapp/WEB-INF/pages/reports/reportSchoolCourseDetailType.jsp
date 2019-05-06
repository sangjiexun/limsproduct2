<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
  <head>
  <meta name="decorator" content="iframe" />
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<!-- 打印开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 打印结束 -->
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">

<script type="text/javascript">
var temUrl = "";
//跳转
function targetUrl(url)
{
	temUrl = document.queryForm.action;
	document.queryForm.action=url;
	document.queryForm.submit();
	document.queryForm.action=temUrl;
}
//取消查询
function cancel()
{
  window.location.href="${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=1";
}

$(document).ready(function(){
      $('#fullview').click(function(){
           $('.sit_sider_bar').animate( { width:'0'}, 500 );
           $('.sit_maincontent').animate( { width:'100%'}, 500 );
           $('.toggle,.toggleLink,#fullview,.sit_footer,.sit_sider_bar > h3').css("display","none");
           $('#fullview1').css("display","inline");
      });
  
      $('#fullview1').click(function(){
           $('.sit_sider_bar').animate( { width:'23%'}, 500 );
           $('.sit_maincontent').animate( { width:'75%'}, 500 );
           $('#fullview1').css("display","none");
           $('.toggle,#fullview,.toggleLink,.sit_footer,.sit_sider_bar > h3 ').css("display","inline");
      });
      
      $('#myPrint').click(function(){
           $('#myShow').jqprint();
      });
});
                              
$(function(){
      var height = $(document).height();
      $(".sit_sider_bar").css('height',height);
      $(".sit_maincontent").css('height',height);
}) ;

function getSchoolCourse(){
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/report/findSchoolTermBySchoolCourse",
		data: {'schoolTerm':$("#schoolTerm").val()},
		success:function(data){
		$("#schoolCourse").html(data.schoolCourse);
		$("#schoolCourse").trigger("liszt:updated");
		}
	}); 
	}
function getLabCenter(){
	$.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/report/findLabCenterBySchoolTerm",
		data: {'schoolTerm':$("#schoolTerm").val()},
		success:function(data){
		$("#labCenter").html(data.labCenter);
		$("#labCenter").trigger("liszt:updated");
		}
	}); 
	}
</script>
  </head>
  
  <body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		  <li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
		  <li class="end"><a href="javascript:void(0)"><spring:message code="left.report.sitecourse" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">实践教学课程细化实验实训项目任务表</a>
		  </li>
		  <input class="btn btn-new" onclick="targetUrl('${pageContext.request.contextPath}/report/exportSchoolCourseDetailType')" type="button" value="导出" />
		  <input class="btn btn-new" id="myPrint" value="打印" type="button" />
	  </ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">实践教学课程细化实验实训项目任务表</div>--%>
	  <%--<input class="btn btn-new" onclick="targetUrl('${pageContext.request.contextPath}/report/exportSchoolCourseDetailType')" type="button" value="导出" />--%>
	  <%--<input class="btn btn-new" id="myPrint" value="打印" type="button" />--%>
	<%--</div>--%>
	
	<%--<table border="0" cellpadding="5" cellspacing="0" bgcolor="#F3EFEE" height="30" width="100%">--%>
	<%--<tbody>--%>
	  <div class="tool-box">
	<form name="queryForm" method="Post" action="${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=1">
		<ul>
			<li>
			学期:
			  <select class="chzn-select" name="term" id="schoolTerm" onchange="getSchoolCourse();">
			  	<c:forEach items="${schoolTermMap}" var="item">  
			  		<c:if test="${item.key eq term}">
                		<option value="${item.key }"  selected="selected">${item.value}</option>  
                	</c:if>
			  		<c:if test="${item.key ne term}">
                		<option value="${item.key }">${item.value}</option>  
                	</c:if>
				</c:forEach>
			  </select>
			</li>
			<li>
			实验中心:
			  <select class="chzn-select" name="centerId" id="labCenter">
			  <c:if test="${centerId eq 0}">
			    <option value="0" selected="selected">全部实验中心</option>
			  </c:if>  
			    <c:forEach items="${labCenterMap}" var="item">
			    	<c:if test="${item.key eq centerId}">
                		<option value="${item.key }"  selected="selected">${item.value}</option>  
                	</c:if>
			  		<c:if test="${item.key ne centerId}">
                		<option value="${item.key }">${item.value}</option>  
                	</c:if>
				</c:forEach>
			  </select>
			</li>
			<li>
			课程:
			  <select class="chzn-select" name="courseNo" id="schoolCourse" width=200px>
			  <option value="">全部课程</option>
				<c:forEach items="${schoolCourseMap}" var="item">
			  	<c:if test = "${not empty courseNoArray}">
			  		<c:forEach var="curr" items="${courseNoArray}">
			  		  <c:if test="${curr eq item.key}">
                          <option selected="selected" value="${item.key}">${item.value}</option>  
                      </c:if>
                      <c:if test="${curr ne item.key}">
                      	<option value="${item.key}">${item.value}</option>
                      </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${empty courseNoArray }">
                    <option value="${item.key}">${item.value}</option>  
                </c:if>
              </c:forEach>
			  </select>
			</li>
			<li>
			课程类型:
			  <select class="chzn-select" name="courseTypeId">
			  <option value="0">--请选择--</option>
				<c:forEach items="${schoolCourseTypeMap}" var="item">
			  	<c:if test = "${not empty courseTypeIdArray}">
			  		<c:forEach var="curr" items="${courseTypeIdArray}">
			  		  <c:if test="${curr eq item.key}">
                          <option selected="selected" value="${item.key}">${item.value}</option>  
                      </c:if>
                      <c:if test="${curr ne item.key}">
                      	<option value="${item.key}">${item.value}</option>
                      </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${empty courseTypeIdArray }">
                    <option value="${item.key}">${item.value}</option>  
                </c:if>
              </c:forEach>
			  </select>
			</li>
			<li>
			  
			<input type="submit" value="查询"/>
			<input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li><br>
		</ul>
	</form>
  </div>
	<%--</tbody>--%>
  <%--</table>--%>
	
<div id="myShow">	
<style type="text/css">
		td{border:solid 1px #4F4F4F !important;}
</style>
<form name="form" method="Post" action="${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=1">	
	<table class="tb" id="my_show">
	  
	  <thead style="center-content">
		  <tr>
		    <%--<th colspan="25">--%>
		    <%--</th>--%>
		  </tr>
		  <tr>
        	<th style="border:solid 1px #4F4F4F !important;" width= "4%">序号</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "8%">课程名称</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "5%">课程编号</th> 
        	<th style="border:solid 1px #4F4F4F !important;" width= "5%">开课教师</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "8%">学期</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "5%">总学时</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "5%">理论学时</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "5%">实验学时</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "8%">课程类型</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "8%">开课中心</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "8%">实际实验学时数</th>
        	<th style="border:solid 1px #4F4F4F !important;" width= "15%">实验项目名称</th>
		  </tr>
	  </thead>
	  
	  <tbody>
	  <c:forEach items="${schoolCourseDetailTypeLists}" var="curr" varStatus="i">
		  <tr>
	    	<td>${i.count+(currpage-1)*pageSize }</td>
	    	<td>${curr[0]}</td>
	    	<td>${curr[1]}</td>
	    	<td>${curr[15]}</td>
	    	<td>${curr[2]}</td>
            <td>${curr[8]}</td>
            <td>${curr[7]}</td>
		    <td>${curr[14]}</td>
	    	<td>${curr[5]}</td>
	    	<td>${curr[13]}</td>
           	<td>${curr[9]}</td>
            <td>${curr[10]}</td>
            <%--<c:set var="ifRowspanBatch" value="${curr[6]}"></c:set>--%>
		  </tr>
	  </c:forEach>
	  </tbody>
	</table>
</form>
</div>
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
	<c:if test="${j.index!=pageModel.currpage}">
	<option value="${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=${j.index}">${j.index}</option>
	</c:if>
	</c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/report/reportSchoolCourseDetailType?currpage=${pageModel.lastPage}')" target="_self">末页</a>
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
 
</body>
</html>