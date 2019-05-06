<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  $(function() {
    $("[id^='lwCategoryExpert']").click(function(){
    	clickCheckbox();
    });
});

   function clickCheckbox()
  {
	var i=0;
	
	$("[id^='lwCategoryExpert']").each(function(){
		if($(this).attr('checked'))
		{
			i++;
		}
	});

	if(i > 2)
	{
	    $("[id^='lwCategoryExpert']").removeAttr("checked");
		alert('专家类别不能多于2个！');
	}
  }
  </script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.trainingTeam.management"/></a></li>
			<li class="end"><a href="javascript:void(0)">新建</a></li>
		</ul>
	</div>
  </div>
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
  <div class="title">
      <div id="title">新建<spring:message code="all.trainingRoom.labroom"/>工作人员</div>
  </div>
  <form:form action="${pageContext.request.contextPath}/labRoom/saveLabWorker?page=${page}" method="POST" modelAttribute="labWorker">
  <div class="new-classroom"> 
  <fieldset>
  <form:hidden path="id"/>
    <label>姓名：</label>
    <form:select path="lwName" class="chzn-select">
	    <form:option value="">请选择</form:option>
	    <c:forEach items="${listUser}" var="curr">
	    	<form:option value="${curr.value}">${curr.value}</form:option>
	    </c:forEach>
    </form:select>
  </fieldset> 
  <fieldset>
    <label>毕业学校：</label>
    <form:input path="lwGraduationSchool" required="true"/>
  </fieldset>
  <fieldset>
    <label>所学专业：</label>
    <form:input path="lwGraduationMajor" required="true"/>
  </fieldset>
    <fieldset>
      <label>所属专业系：</label>
      <form:select path="schoolAcademy.academyNumber" class="chzn-select">
        <form:options items="${listSchoolAcademy}" itemLabel="academyName" itemValue="academyNumber"/>
      </form:select>
    </fieldset>
    <fieldset>
    <label>民族：</label>
    <form:input path="lwNation" required="true"/>
  </fieldset>
  <fieldset>
    <label>出生地：</label>
    <form:input path="lwBirthplace" required="true"/>
  </fieldset>
  <fieldset>
    <label>出生年月：</label>
    <input name="lwBirthday" class="easyui-datebox" value="<fmt:formatDate value='${labWorker.lwBirthday.time}' pattern='yyyy-MM-dd'/>" />
  </fieldset>
  <fieldset>
    <label>性别：</label>
    <form:select path="lwSex">
      <form:option value="1">男</form:option>
      <form:option value="0">女</form:option>
    </form:select>
  </fieldset>
  <fieldset>
    <label>学历：</label>
    <form:select path="CDictionaryByLwAcademicDegree.id" class="chzn-select">
      <form:options items="${listAcademicDegree}" itemLabel="CName" itemValue="id"/>
    </form:select>
  </fieldset>
  <fieldset>
    <label>学位：</label>
    <form:select path="CDictionaryByLwDegree.id" class="chzn-select">
      <form:options items="${listDegree}" itemLabel="CName" itemValue="id"/>
    </form:select>
  </fieldset>
  <fieldset>
    <label>业务特长：</label>
    <form:input path="lwProfessionSpecialty" required="true"/>
  </fieldset>
  <fieldset>
    <label>承担任务：</label>
    <form:input path="lwDuty" required="true"/>
  </fieldset>
  <fieldset>
  <label>所属学科：</label>
    <form:select path="CDictionaryByLwSubject.id" class="chzn-select">
      <form:options items="${listSubject}" itemLabel="CName" itemValue="id"/>
    </form:select>
  </fieldset>   
  <fieldset>
    <label>专职/兼职：</label>
    <form:select path="CDictionaryByLwCategoryStaff.id" class="chzn-select">
      <form:options items="${listCategoryStaff}" itemLabel="CName" itemValue="id"/>
    </form:select>
  </fieldset>
  <fieldset>
    <label>工作时间：</label>
    <input name="lwWorkTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorker.lwWorkTime.time}' pattern='yyyy-MM-dd'/>" />
  </fieldset>
  <fieldset>
    <label>毕业时间：</label>
    <input name="lwGraduationTime" class="easyui-datebox" value="<fmt:formatDate value='${labWorker.lwGraduationTime.time}' pattern='yyyy-MM-dd'/>" />
  </fieldset>
  <fieldset>
    <label>专家类别：</label>
    <form:select path="CDictionaryByLwCategoryExpert.id" class="chzn-select">
      <form:options items="${listCategoryExpert}" itemLabel="CName" itemValue="id"/>
    </form:select>
  </fieldset>
  <fieldset>
    <label>专业技术职务：</label>
    <form:select path="CDictionaryByLwSpecialtyDuty.id" class="chzn-select">
      <form:options items="${listSpecialtyDuty}" itemLabel="CName" itemValue="id"/>
    </form:select>
  </fieldset>
  <fieldset>
  <label>所属<spring:message code="all.trainingRoom.labroom"/></label>
  <form:select path="LabRoom.id" class="chzn-select">
      <form:options items="${listLabRoom}" itemLabel="labRoomName" itemValue="id"/>
    </form:select>
  </fieldset>
    <fieldset>
      <label>职业资格证书：</label>
      <form:input path="vocationalQualification"/>
    </fieldset>
  </div>
  <div class="moudle_footer">
      <div class="submit_link">
      <input class="btn" id="save" type="submit" value="确定">
      <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
      </div>
  </div>
  </form:form>
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
  </div>
  </div>
  </div>
  </div>
  </div>
  
</body>
</html>
