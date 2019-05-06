<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe" />

<!-- 下拉框的样式 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->
<style type="text/css">
	#tab td:nth-child(even){
		 width: 173px;
         text-align: left;
	}
</style>
<script type="text/javascript">
  var isSumbiting = 0;
  $(document).ready(function(){
        var s=${status};
        for(var i=0;i<=7;i++)
        {
        	if(i==s)
        	{
        		$("#s"+i).addClass("TabbedPanelsTab selected");
        	}
        	else
        	{
        		$("#s"+i).addClass("TabbedPanelsTab");
        	}
        }
	});
//新建
  function newOperationItem()
  {
      window.location.href="${pageContext.request.contextPath}/operation/newOperationItem?isMine=1&flagId=0";
  }
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  
  //提交审核人数据
  function saveCheckUser()
  {
    if($("#username_check").val()==""){
    	alert("老师，您需要先选择指定审核人再提交。")
    }else{
	  document.check_user_form.action="${pageContext.request.contextPath}/operation/submitOperationItem?isMine=1";
	  document.check_user_form.submit();
    }
    	
  }
  //弹出填写审核人的表单
  function submitItem(id)
  {
      if(${thereIsAHeader == 1}){
          $("#lp_id").val(id);
          var $top = $("#"+id)[0].offsetTop;
          saveCheckUserRest();
      }else if(${thereIsAHeader == 0}) {
          alert("您好，系统还没有设置项目审核人权限，请联系相关人员进行设置。")
      }else {
          alert("您好，您所在学院暂时没有<${authname}>权限的审核人员，请联系相关人员进行设置。")
      }
  }
  //设置项目编号
  function saveCodeCustom()
  {
    document.edit_code_form.action="${pageContext.request.contextPath}/operation/saveCodeCustom";
    document.edit_code_form.submit();
  }
  //修改项目编号
  function editCodeCustom(id, code)
  {
    $("#oi_id").val(id);
    $("#edit_code form table #lpCodeCustom").val(code);
    $("#edit_code").show();
    $("#edit_code").window('open');
  }
  //排序
  
  var asc=${asc};//声明全局变量asc
  function orderByNumber(){
	  asc=!asc;
	  if(asc){
		  window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=10";
	  }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=0";
  }
  function orderByName(){
	  asc=!asc;
	  if(asc){
		  window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=11";
	  }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=1";
  }
  function orderByLab(){
	  asc=!asc;
	  if(asc){
		  window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=12";
	  }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=2";
  }
  function orderByCourse(){
	  asc=!asc;
	  if(asc){
		  window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=13";
	  }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=3";
  }
  function orderByStatus(){
	  asc=!asc;
	  if(asc){
		  window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=14";
	  }else window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=4";
  }
  </script>
<script type="text/javascript">
//查看详情--材料
function listItemMaterialRecordRest(id){
    var lp_name = $("#lp_name").val();
    var course_number = $("#course_number").val();
    var lp_create_user = "-1";
	if($("#lp_name").val()==""){
		lp_name ="-1";
	}
	if($("#course_number").val()==""){
		course_number ="-1";
	}
	//时间关系学期暂时给定值
	var url = "${pageContext.request.contextPath}/operationRest/listItemMaterialRecordRest/" + lp_name + "/"+ 15 + "/" + course_number + "/" + lp_create_user + "/${page}"+ "/${1}"+ "/${status}"+"/${orderBy }/" + id;
	//page后面的1--isMine
	window.location.href=url;
	}
//提交--保存审核人
function saveCheckUserRest(){
	var id = $("#lp_id").val();//由于之前的传递，lp_id的值就是对应项目卡的id
	var lp_name = $("#lp_name").val();
    var term_id = $("#term_id").val();
    var course_number = $("#course_number").val();
    var lp_create_user = "-1";
    var username_check = $("#username_check").val();
	if($("#lp_name").val()==""){
		lp_name ="-1";
	}
	if($("#term_id").val()==""){
		term_id ="-1";
	}
	if($("#course_number").val()==""){
		course_number ="-1";
	}
	username_check = "-1";
    	if(isSumbiting == 1){
    		alert("请勿重复提交！提交中，请耐心等待。");
    		return false;
    	}else{
		var url = "${pageContext.request.contextPath}/operationRest/submitOperationItemRest/" + lp_name + "/"+ 15 + "/" + course_number + "/" + lp_create_user + "/" + username_check + "/${page}"+ "/${1}"+ "/${status}"+"/${orderBy }/" + id;
		window.location.href=url;
		isSumbiting = 1;
    	}
}

</script>
<script type="text/javascript">
	//保存项目
function checkRequired(){
	  if($("#lpName").val() == ""){
		  alert("请填写实验名称!");
	  }
	  else if($("#lpDepartmentHours").val() == ""){
		  alert("请填写实验学时!");
	  }
	  else if($("#lpMajorFit").val() == ""){
		  alert("请选择面向专业!");
	  }
	  else if( $("#schoolCourseInfo").val() == ""){
		  alert("请选择所属课程!");
	  }
	  else{
          if(${thereIsAHeader == 1}){
              document.edit_form.action="${pageContext.request.contextPath}/operation/saveOperationItem?toMyList=${isMine}&flagId=${flagId}";
              document.edit_form.submit();
          }else if(${thereIsAHeader == 0}) {
              alert("您好，系统还没有设置项目审核人权限，请联系相关人员进行设置。")
          }else {
              alert("您好，您所在学院暂时没有<${authname}>权限的审核人员，请联系相关人员进行设置。")
          }
	  }
}
</script>
	<script type="text/javascript">
		function getSchoolCourseInfo() {
            $.ajax({
                url:"${pageContext.request.contextPath}/operation/getAllCourseInfos",
                contentType: "application/json;charset=utf-8",
                dataType: "text",
                type: "post",
                success: function (data) {
                    $("#schoolCourseInfo").html(data);
                    $("#schoolCourseInfo").trigger("liszt:updated");
                }
            });
        }
        window.onload = function () {
            getSchoolCourseInfo();
        };
		function moreInfos() {
		    if($(".infos").is(":hidden")){
                $(".infos").css("display", "table-row");
                $("#mbt").addClass("bt_class");
			}else {
                $(".infos").css("display", "none");
			}
        }
	</script>
	<style type="text/css">
		.bt_class{
			display: none;
		}
	</style>
</head>

<body>
<c:if test="${status == 6}">
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.item.operation"/></a></li>
		</ul>
	</div>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/operation/listOperationItem?currpage=1&status=0&orderBy=${orderBy }">全部</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
		<li class="TabbedPanelsTab" id="s5"><a href="${pageContext.request.contextPath}/operation/listOperationItem?currpage=1&status=5&orderBy=${orderBy }">我的审核</a></li>
		<li class="TabbedPanelsTab" id="s6"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=6&orderBy=${orderBy }">我的项目</a></li>
	</ul>
</c:if>
<c:if test="${status != 6}">
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
				<li class="end"><a href="javascript:void(0)">课题库</a></li>
			</ul>
		</div>
	</div>
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
			<%--<sec:authorize ifNotGranted="ROLE_STUDENT">
				<li class="TabbedPanelsTab" id="s0"><a
						class="iStyle_Feelings_Tree_Leaf"
						href="${pageContext.request.contextPath}/teaching/coursesite/experimentalmanagement?currpage=1">大纲管理</a>
				</li>
			</sec:authorize>--%>
			<sec:authorize ifNotGranted="ROLE_STUDENT">
				<li class="TabbedPanelsTab" id="s1"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/teaching/coursesite/listSchoolCourseInfo?currpage=1">课程库</a>
				</li>
			</sec:authorize>
			<sec:authorize ifNotGranted="ROLE_STUDENT">
				<li class="TabbedPanelsTab" id="s7"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=7&orderBy=0">课题库</a>
				</li>
			</sec:authorize>
			<sec:authorize ifNotGranted="ROLE_STUDENT">
				<li class="TabbedPanelsTab" id="s3"><a
					class="iStyle_Feelings_Tree_Leaf"
					href="${pageContext.request.contextPath}/operation/listMyOperationItemAuditing?currpage=1">课题审核</a>
				</li>
			</sec:authorize>
		</ul>
	</div>
	<div class="content-box">
		<div class="title">
			<div id="title">新增课题</div>
		</div>
		<div class="tool-box" style="overflow:initial">
			<form:form name="edit_form" action="${pageContext.request.contextPath}/operation/saveOperationItem?toMyList=${isMine}&flagId=${flagId}" method="POST" modelAttribute="operationItem">
				<table id="tab">
					<tr>
						<th style="width: 100px;">课题名称：</th>
						<td><form:input id="lpName" path="lpName" style="width: 200px; float:left;" /></td>
						<th style="width: 100px;">学时数(数字)：</th>
						<td><form:input path="lpDepartmentHours" id="lpDepartmentHours" required="true" onkeyup="value=value.replace(/[^\d]/g,'') " style="width: 200px; float:left;" />
						</td>
						<th style="width: 100px;">所属课程：</th>
						<td>
						<form:select 
						path="schoolCourseInfo.courseNumber" class="chzn-select"
						id="schoolCourseInfo" required="true" style="width: 200px;">
						     <form:option value="">请选择</form:option>
						     <c:forEach items="${schoolCourseInfo}" var="c">
								 <c:if test="${c.courseNumber == courseNumber}">
									 <form:option value ="${c.courseNumber}" selected="true">[${c.courseNumber}]${c.courseName}--${c.totalHours }学时</form:option>
								 </c:if>
						     <form:option value="${c.courseNumber}">[${c.courseNumber}]${c.courseName}--${c.totalHours }学时</form:option>
						     </c:forEach>
						</form:select>
						</td>
					</tr>
					<tr>
						<th>面向专业：</th>
						<td>
							<form:select path="lpMajorFit" id="lpMajorFit" class="chzn-select" style="width: 200px;">
							<form:option value="">请选择</form:option>
		                       <c:forEach items="${majorList}" var="m">
		        	              <form:option value="${m.majorNumber}" selected="selected">[${m.majorNumber}]${m.majorName}</form:option>
		                       </c:forEach>
		                       <c:forEach items="${majors}" var="m">
								   <c:if test="${m.majorNumber == schoolMajorsa}">
									   <form:option value ="${m.majorNumber}" selected="true">[${m.majorNumber}]${m.majorName}</form:option>
								   </c:if>
		                          <form:option value="${m.majorNumber}">[${m.majorNumber}]${m.majorName}</form:option>
		                       </c:forEach>
		                    </form:select>
	                    </td>
						<th>关联软件：</th>
						<td>
							<form:select path="software" class="chzn-select" id="software" >
							<form:option value="">请选择</form:option>
		                        <c:forEach items="${softwares}" var="m">
		        	               <form:option value="${m.id}" selected="selected">[${m.code}] ${m.name}</form:option>
		                        </c:forEach>
	          	                <c:forEach items="${listSoftware}" var="m">
	            	               <form:option value="${m.id}">[${m.code}] ${m.name}</form:option>
	          	                </c:forEach>
	                        </form:select>
						</td>
						<th>课题简介：</th>
						<td><form:textarea path="lpIntroduction" style="width: 200px;"></form:textarea></td>
					</tr>
					<tr class="infos" style="display: none">
						<th>课题人数：</th>
						<td><form:input path="lpStudentNumber" maxlength="6" placeholder="请输入大于零的整数" onkeyup="value=value.replace(/[^\d]/g,'') " style="width: 200px; float:left;" />
						</td>
						<th>课题组数：</th>
						<td><form:input path="lpSetNumber" maxlength="3" placeholder="请输入大于零的整数" onkeyup="value=value.replace(/[^\d]/g,'') " style="width: 200px; float:left;" />
						</td>
						<th>每组人数：</th>
						<td><form:input path="lpStudentNumberGroup" maxlength="2" placeholder="请输入大于零的整数" onkeyup="value=value.replace(/[^\d]/g,'') " style="width: 200px; float:left;" />
						</td>
					</tr>
					<tr class="infos" style="display: none">
						<th>主讲教师：</th>
						<td><form:select path="userByLpTeacherSpeakerId.username" id="teacher_speaker" class="chzn-select">
							<form:option value="${operationItem.userByLpTeacherSpeakerId.username }">${operationItem.userByLpTeacherSpeakerId.cname }</form:option>
							<c:forEach items="${users}" var="user">
								<form:option value="${user.username}">[${user.username}]${user.cname}</form:option>
							</c:forEach>
						</form:select></td>
						<th>考核方法：</th>
						<td><form:input path="lpAssessmentMethods" style="width: 200px; float:left;" /></td>
						<th>课前准备：</th>
						<td><form:textarea path="lpPreparation" style="width: 200px;"></form:textarea></td>
					</tr>
					<tr>
						<th></th>
						<td></td>
						<td colspan="2"><input id="mbt" class="btn" type="button" value="填写更多" onclick="moreInfos()"/></td>
						<td></td>
						<td><input class="btn" type="button" value="返回" onclick="window.history.go(-1)"/>
						<input class="btn" id="save" type="button" onclick="checkRequired()" value="新建保存"/></td>
					</tr>
				</table>
			</form:form>
			<!-- 设备仪器开始  -->
		
		<div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add" style="width:400px;height:200px">
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
		<!-- 设备仪器结束  -->
		</div>
	</div>
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
		</ul>
		</c:if>
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">

				<div class="content-box">
					<div class="title">
						<c:if test="${status != 6}"><div id="title">课题列表</div></c:if>
						<c:if test="${status == 6}"><div id="title"><spring:message code="all.training.name"/>项目列表</div></c:if>
					</div>

					<div class="tool-box" style="overflow:initial;">
						<form:form name="queryForm"
							action="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&&status=${status}&orderBy=${orderBy }"
							method="post" modelAttribute="operationItem">
							<ul>
								<li>综合查询：</li>
								<li><form:input id="lp_name" path="lpName" /></li>
								<li>审核状态：</li>
								<li><form:select path="CDictionaryByLpStatusCheck.CNumber" id="lp_status_check">
										<form:option value="">请选择</form:option>
										<form:option value="1">未审核</form:option>
										<form:option value="3">已审核</form:option>
									</form:select>
								</li>
								<li><input type="button" value="取消" onclick="cancel();" />
								<input type="submit" value="查询" /></li>
								<c:if test="${status == 6}"><li>
								<input type="button" value="新建" onclick="newOperationItem();"/>
								</li></c:if>
							</ul>

						</form:form>
					</div>

					<table class="tb" id="my_show">
						<thead>
							<tr>
								</th>
								<c:if test="${status != 6}"><th>课题名称</th></c:if>
								<c:if test="${status == 6}"><th>项目名称</th></c:if>
								<th><a href="javascript:void(0);" onclick="orderByCourse()";>所属课程</a>
								</th>
								<th>创建者</th>
								<%--<th>指定审核人</th>--%>
								<th><a href="javascript:void(0);" onclick="orderByStatus()";>审核状态</a>
								</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listOperationItem}" var="curr">
								<tr id="${curr.id }">
									<td>${curr.lpName}</td>
									<td>${curr.schoolCourseInfo.courseName}[${curr.schoolCourseInfo.courseNumber}]</td>
									<td>${curr.userByLpCreateUser.cname}</td>
									<%--<td>${curr.userByLpCheckUser.cname}</td>--%>
									<td>
									<c:choose>
									<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 2}">${auditShow[curr.id]}</c:when>
									<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 3}">审核通过</c:when>
									<c:when test="${curr.CDictionaryByLpStatusCheck.CNumber eq 4}">审核拒绝</c:when>
									<c:otherwise>未审核</c:otherwise>
									</c:choose>
									</td>
									<td><sec:authorize ifAnyGranted="ROLE_TEACHER">
											<%--<c:if test="${draft.id==curr.CDictionaryByLpStatusCheck.id || checkNo.id==curr.CDictionaryByLpStatusCheck.id}">--%>
										<c:if test="${draft.id==curr.CDictionaryByLpStatusCheck.id}">
													<c:if test="${nowUser.cname eq curr.userByLpCreateUser.cname}">
														<a href="javascript:void(0)" onclick="submitItem(${curr.id});">提交</a>
														<a href="${pageContext.request.contextPath}/operation/editOperationItem?operationItemId=${curr.id}&&isMine=1&flagId=1">编辑</a>
														<a href="${pageContext.request.contextPath}/operation/deleteOperationItem?operationItemId=${curr.id}&&isMine=1&status=${status}"
														   onclick="return confirm('确定删除？');">删除</a>
													</c:if>
											</c:if>
											<%--<c:if test="${toCheck.id eq curr.CDictionaryByLpStatusCheck.id }">--%>
												<a
													href="${pageContext.request.contextPath}/operation/operationItemAndAudit?oId=${curr.id}&cid=-1&flag=4">进度查看</a>
											<%--</c:if>--%>
											<a href="javascript:void(0);"
												onclick="listItemMaterialRecordRest(${curr.id});">详情</a>
										</sec:authorize>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="page">
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页 <a
							href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }')"
							target="_self">首页</a> <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=${pageModel.previousPage}&status=${status}&orderBy=${orderBy }')"
							target="_self">上一页</a> 第<select
							onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
							<option
								value="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=${pageModel.currpage}&status=${status}&orderBy=${orderBy }">${pageModel.currpage}</option>
							<c:forEach begin="${pageModel.firstPage}"
								end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
								<c:if test="${j.index!=pageModel.currpage}">
									<option
										value="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=${j.index}&status=${status}&orderBy=${orderBy }">${j.index}</option>
								</c:if>
							</c:forEach>
						</select>页 <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=${pageModel.nextPage}&status=${status}&orderBy=${orderBy }')"
							target="_self">下一页</a> <a href="javascript:void(0)"
							onclick="targetUrl('${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=${pageModel.lastPage}&status=${status}&orderBy=${orderBy }')"
							target="_self">末页</a>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div id="check_user" class="easyui-window" closed="true" modal="true"
		minimizable="true" title="指定审核人"
		style="width: 580px;height: 350px;padding: 20px">
		<div class="content-box">
			<form:form name="check_user_form" method="post"
				modelAttribute="operationItem">
				<table>
					<tr>
						<td>指定审核人</td>
						<td><form:hidden path="id" id="lp_id" /> <form:select
								path="userByLpCheckUser.username" id="username_check"
								class="chzn-select">
								<form:option value="">请选择</form:option>
								<c:forEach items="${auditorList}" var="u">
									<form:option value="${u.username}">[${u.username}]${u.cname}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
				</table>
				<div class="moudle_footer" style="background:none;border-top:none;">
					<div class="submit_link">
						<input class="btn" id="save" type="button" onclick="saveCheckUserRest();" value="提交">
					</div>
				</div>
			</form:form>
		</div>
	</div>

	<!-- 下拉框的js -->
	<script
		src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
		type="text/javascript" charset="utf-8"></script>
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
