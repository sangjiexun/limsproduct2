<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
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
  //审核通过
  function checkPass(){
    if(confirm('通过后不能恢复，确定审核通过？'))
    {
      window.location.href="${pageContext.request.contextPath}/softwareAudit?Id=${id}&result=1&flag=${step}";
    }
  }
  //审核拒绝
  function checkFail(){
    if(confirm('拒绝后不能恢复，确定审核拒绝？'))
    {
      window.location.href="${pageContext.request.contextPath}/softwareAudit?Id=${id}&result=0&flag=${step}"
    }
  }
  </script>
  
    <script type="text/javascript">
    //材料使用
  function addMaterialRecord()
  {
    $("#lpmrId").val(null);
    $("#lpmr_name").val("");
    $("#lpmr_category").val("");
    $("#lpmr_model").val("");
    $("#lpmr_unit").val("");
    $("#lpmr_quantity").val("");
    $("#lpmr_amount").val("");
    $("#lpmr_remark").val("");
  	
    $("#editMaterialRecord").show();
  	//获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    $('#editMaterialRecord').window({left:"100px", top:topPos+"px"});
    $("#editMaterialRecord").window('open');
  }
  
  function getSpec(osel){
	     var content = osel.options[osel.selectedIndex].text;
	      
	      var x,y;
	      for(var i = 0; i<content.length; i++){
	     	 if(content[i] == '['){
	     		 x=i;
	     	 }
	     	 if(content[i] == ']'){
	     		 y=i;
	     		 break;
	     	 }
	      }
	      var unit  = content.substring(x,y+1);
	      unit = unit.replace(/[^a-z^A-Z]/g,'');
	      $("#unit").html(unit);
	  }
  
  function saveMaterialRecord()
  {
    document.form_material.action="${pageContext.request.contextPath}/operation/saveItemMaterialRecord?flag="+${flag}+"&status="+${status}+"&id="+${operationItem.id};
    if(isNaN($("#lpmr_quantity").val())){
			alert("【数量】填写数字");
			return false;
		}
    document.form_material.submit();
    
  }
  
  function editMaterialRecord(mrId)
  {
    $.ajax({
      type:"GET",
      url:"${pageContext.request.contextPath}/operation/ajaxGetMaterialRecord",
      data:{mrId:mrId},
      dataType:"json",
      success:function(data){
        $("#lpmrId").val(mrId);
        $("#lpmr_name").val(data.lpmrName);
        $("#lpmr_category").val(data.lpmrCategory);
        $("#lpmr_model").val(data.lpmrModel);
        $("#lpmr_unit").val(data.lpmrUnit);
        $("#lpmr_quantity").val(data.lpmrQuantity);
        $("#lpmr_amount").val(data.lpmrAmount);
        $("#lpmr_remark").val(data.lpmrRemark);
        
        $("#editMaterialRecord").show();
        $("#editMaterialRecord").window('open');
      }
    });
  }
  
  
  //设备关联
  function addDevices()
  {
    $("#addDevice").show();
    //获取当前屏幕的绝对位置
    var topPos = window.pageYOffset;
    //使得弹出框在屏幕顶端可见
    $('#addDevice').window({left:"100px", top:topPos+"px"});
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
	  
	  document.device_form.action="${pageContext.request.contextPath}/operation/saveItemDeviceNew?itemId=${operationItem.id}&category="+category+"&ids="+ids+"&flag="+${flag}+"&status="+${status};
      document.device_form.submit();
	}
	else
	{
	  alert("至少选择一个设备！");
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
	
	
</script>
  
  <style>
  	select{
  		width:162px;
  		margin-left:10px;
  	}
  	.chzn-container{
  	width:162px !important;
  	margin-left:10px
  	}
  	.edit-content-box {
    border: 1px solid #9BA0AF;
    border-radius: 5px;
    overflow: visible;
    margin-top: 15px;
}
  </style>

</head>
  
<body>



  <div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0);">软件安装申请</a></li>
				<li class="end"><a href="javascript:void(0);">软件安装申请详细信息</a></li>
			</ul>
		</div>
  </div>
 
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="edit-content-box">
    <div class="title">
    </div>
    <div class="edit-content">
    <table>
      <tr>
        <th>申请人：</th><td>${softwareReserve.user.username}</td>
        <th>申请时间：</th><td></td>
        <th>安装<spring:message code="all.trainingRoom.labroom" />：</th><td>${softwareReserve.labRoom.labRoomName}</td>
        <th>软件名称：</th><td>${softwareReserve.code}</td>
      </tr>
      <tr>
        <th>系统要求：</th><td>${softwareReserve.requirement}</td>
        <th>加密狗：</th><td>${softwareReserve.dongle}</td>
        <th>光盘：</th><td>${softwareReserve.cd}</td>
        <th>课程：</th><td>${softwareReserve.course}</td>
      </tr>
      <tr>
        <th>安装时间：</th><td></td>
        <th>软件架构：</th><td>${softwareReserve.frame}</td>
        <th>供应商：</th><td>${softwareReserve.supply}</td>
        <th>供应商联系方式：</th><td>${softwareReserve.supplyPhone}</td>
      </tr>
      <tr>
        <th>申请理由：</th><td>${softwareReserve.applyReason}</td>
        <th>安装说明：</th><td></td>
        <th>使用说明：</th><td></td>
      </tr>
      
      
      <tr>
        <c:if test="${softwareReserve.approveState==1 }">
        <th>审核状态：</th><td>审核中</td>
        </c:if>
        <c:if test="${softwareReserve.approveState!=1 }">
        <th>审核状态：</th><td>已审核</td>
        </c:if>
      </tr>
      <c:forEach items="${softwareReserveAudits}" var="current"  varStatus="i">
      <tr><td>审核人：</td><td>${current.user.cname }</td>
      <c:if test="${current.status==0 }">
      <td>审核情况：</td><td>审核拒绝</td>
      </c:if>
      <c:if test="${current.status==1 }">
      <td>审核情况：</td><td>审核通过</td>
      </c:if>
      </tr>
      </c:forEach>
      <c:if test="${flag!=1}">
      <tr>
        <td><input type="button" onclick="checkPass();" value="审核通过" /></td>
        <td><input type="button" onclick="checkFail();" value="审核拒绝" /></td>
      </tr>
      </c:if>
    </table>
    </div>
    
    <script type="text/javascript">
		$(".btn-edit").click(function(){
			$(this).parent().next().slideUp(); //原信息隐藏
			$(this).parent().next().next().children(".edit-edit").slideDown();//修改信息显示
			$(this).hide();//修改按钮隐藏
			$(this).parent().next().next().children(".edit-action").show();	//返回保存按钮显示					
		});
		$(".btn-return").click(function(){
			$(this).parent().hide();//返回保存按钮隐藏
			$(this).parent().parent().prev().prev().children(".btn-edit").show();//修改按钮显示
			$(this).parent().prev().slideUp();//修改信息隐藏
			$(this).parent().parent().prev().slideDown();//原始信息显示

		})
	</script>
    
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
