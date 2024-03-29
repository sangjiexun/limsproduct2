<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <script type="text/javascript">
  function addDevices()
  {
    $("#addDevice").show();
    $("#addDevice").window('open');
  }
  
  //全部选择或不选
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
  //保存实验项目设备
  function submitForm()
  {
    var flag = false;  //是否有checkbox被选中
    var category = $("#device_category").val();
    var ids = "";
    $("input[name='itemDevice']:checked").each(function(){
        ids += $(this).val()+",";
		flag = true;
	});
	
	if(flag)
	{
	  if(ids.length > 0)
	  {
	  	ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
	  }
	  if(category=="")
	  {
	    alert("请选择类型！");
	    return false;
	  }
	  $.ajax({
          url:"${pageContext.request.contextPath}/operation/saveItemDeviceRest?itemId=${operationItem.id}&category="+category+"&ids="+ids,
          type: 'POST',  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false, 
          
          error: function(request) {
              alert("请求错误");
          },
          success: function(data) {
        	  listItemDeviceRest(${operationItem.id});
          }
      });
	}
	else
	{
	  alert("至少选择一个设备！");
	}
  }
  
  
//保存实验项目设备
  function deleteItemDevice(id)
  {
	if(confirm('确认要删除吗？') == true)
	{
	  $.ajax({
          url:"${pageContext.request.contextPath}/operation/deleteItemDeviceRest?itemDeviceId="+id,
          type: 'POST',  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false, 
          
          error: function(request) {
              alert("请求错误");
          },
          success: function(data) {
        	  listItemDeviceRest(${operationItem.id});
          }
      });
	}
  }
  </script>
  
<script type="text/javascript">
/*所有rest方法*/
//返回列表页面
function listOperationItemRest(){
	var url = "${pageContext.request.contextPath}/operationRest/listOperationItemRest/" + "${lp_name}" + "/"+ ${term_id} + "/" + ${course_number} + "/" + "${lp_create_user}" + "/${page}"+ "/${1}"+ "/${status}"+ "/${orderBy}";
	//page 后面的1--flag
	window.location.href=url;
}
//返回我的列表页面
function listMyOperationItemRest(){
	var url = "${pageContext.request.contextPath}/operationRest/listMyOperationItemRest/" + "${lp_name}" + "/"+ ${term_id} + "/" + ${course_number} + "/" + "${lp_create_user}" + "/${page}"+ "/${0}"+ "/${status}"+ "/${orderBy}";
	//page 后面的0--flag
	window.location.href=url;
}
//跳转到仪器设备页面

function listItemDeviceRest(id){
	var url = "${pageContext.request.contextPath}/operationRest/listItemDeviceRest/" + "${lp_name}" + "/"+ ${term_id} + "/" + ${course_number} + "/" + "${lp_create_user}" + "/${page}"+ "/${isMine}"+ "/${status}"+ "/${orderBy}/"+id;
	
	window.location.href=url;
}

//查看详情--材料
function listItemMaterialRecordRest(id){
	var url = "${pageContext.request.contextPath}/operationRest/listItemMaterialRecordRest/" + "${lp_name}" + "/"+ ${term_id} + "/" + ${course_number} + "/" + "${lp_create_user}" + "/${page}"+ "/${isMine}"+ "/${status}"+"/${orderBy }/" + id;
	window.location.href=url;
	}
</script>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0);">实验项目管理</a></li>
			<li class="end"><a href="javascript:void(0);">实验项目详细信息</a></li>
			<c:if test="${isMine==0 }">
			<a class="btn btn-return" href="javascript:void(0);" onclick="listOperationItemRest();">返回</a>
			</c:if>
			<c:if test="${isMine==1 }">
			<a class="btn btn-return" href="javascript:void(0);" onclick="listMyOperationItemRest();">返回</a>
			</c:if>
			<c:if test="${isMine==3 }">
			<a class="btn btn-return" href="${pageContext.request.contextPath}/operation/listOperationItemImport?currpage=1&orderBy=${orderBy }">返回</a>
			</c:if>
		</ul>
	</div>
  </div>
  
  <div class="content-box">
    <table>
      <tr>
        <th>课题名称：</th><td>${operationItem.lpName}</td>
        <th>学时数：</th><td>${operationItem.lpDepartmentHours}</td>
        <th>所属课程：</th><td>${operationItem.schoolCourseInfo.courseName}[${operationItem.schoolCourseInfo.courseNumber}]</td>
        <th>面向专业：</th><td>${majorStr}</td>
      </tr>
      <tr>
        <th>关联软件：</th><td>${operationItem.software}</td>
        <th>课题人数：</th><td>${operationItem.lpStudentNumber}</td>
        <th>课题组数：</th><td>${operationItem.lpSetNumber}</td>
        <th>每组人数：</th><td>${operationItem.lpStudentNumberGroup}</td>
      </tr>
      <tr>
        <th>主讲教师：</th><td>${operationItem.userByLpTeacherSpeakerId.cname}[${operationItem.userByLpTeacherSpeakerId.username}]</td>
        <th>考核方法：</th><td>${operationItem.lpAssessmentMethods}</td>
        <th>课题简介：</th><td>${operationItem.lpIntroduction}</td>
        <th>课前准备：</th><td>${operationItem.lpPreparation}</td>
      </tr>
      <tr>
        <th>指定审核人：</th><td>${operationItem.userByLpCheckUser.cname}-${operationItem.userByLpCheckUser.username}</td>
        <th>审核状态：</th><td>${operationItem.CDictionaryByLpStatusCheck.CName}</td>
        <th></th><td></td>
        <th></th><td></td>
      </tr>
    </table>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s1"><a href="javascript:void(0);" onclick="listItemMaterialRecordRest(${operationItem.id});">材料使用</a></li>
		<li class="TabbedPanelsTab selected" id="s2"><a href="javascript:void(0);" onclick="listItemDeviceRest(${operationItem.id});">仪器设备</a></li>
	</ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent"></li>
    <div class="tool-box">
      <ul>
        <li><input type="button" onclick="addDevices();" value="批量添加"></li>
      </ul>
    </div>
    
    <div class="content-box">
    <table>
      <thead>
      <tr>
        <th>序号</th>
        <%--<th>类型</th>--%>
        <th>设备编号</th>
        <th>设备名称</th>
        <th>规格</th>
        <th>型号</th>
        <th>单价</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${listItemDevice}" var="curr" varStatus="i">
      <tr>
        <td>${i.count}</td>
        <%--<td>${curr.CDictionary.CName}</td>--%>
        <td>${curr.schoolDevice.deviceNumber}</td>
        <td>${curr.schoolDevice.deviceName}</td>
        <td>${curr.schoolDevice.deviceFormat}</td>
        <td>${curr.schoolDevice.devicePattern}</td>
        <td>${curr.schoolDevice.devicePrice}</td>
        <td>
          <a onclick="deleteItemDevice(${curr.id})">删除</a>
        </td>
      </tr>
      </c:forEach>
      </tbody>
    </table>
    </div>
  </div>
  </div>
  </div>
  
  <div id="addDevice" class="easyui-window" closed="true" modal="true" minimizable="true" title="设备" style="display:none;width: 600px;height: 500px;padding: 20px">
  <div class="content-box">
  <form name="device_form" method="post">
    <table>
      <tr>
        <%--<td>
           <select name="device_category" id="device_category">
             <option value="1">公用</option>
             <option value="2">专用</option>
           </select>
        </td>--%>
        <td colspan="100" style="text-align:right;"><input type="button" value="添加" onclick="submitForm();"/></td>
      </tr>
      
    </table>
    <table>
      <thead>
      <tr>
        <th><input id="check_all" type="checkbox" onclick="checkAll();"/></th>
        <th>设备编号</th>
        <th>设备名称</th>
        <th>领用单位号</th>
        <th>型号</th>
        <th>规格</th>
        <th>价格</th>
        <th>地点</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${listLabRoomDevice}" var="curr">
        <tr>
          <td><input id="device_${curr.id}" name="itemDevice" type="checkbox" value="${curr.id}"/></td>
          <td>${curr.deviceNumber}</td>
          <td>${curr.deviceName}</td>
          <td>${curr.schoolAcademy.academyNumber}</td>
          <td>${curr.devicePattern}</td>
          <td>${curr.deviceFormat}</td>
          <td>${curr.devicePrice}</td>
          <td>${curr.deviceAddress}</td>
        </tr>
      </c:forEach>
      <tr>
          <td colspan="100" style="text-align:right;"><input type="button" value="提交" onclick="submitForm();"/></td>
      </tr>
      </tbody>
    </table>
  </form>
  </div>
  </div>
  
</body>
</html>
