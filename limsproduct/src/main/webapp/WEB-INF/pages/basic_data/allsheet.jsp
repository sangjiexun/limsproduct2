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
<style>
    .content-box td{
        padding:0;
    }
    .list_form td{
        height:50px;
        line-height:50px;
        padding:0 0 10px 0;
    }
</style>
  
  
<script type="text/javascript">
//跳转
function targetUrl(url)
{
  document.queryForm.action=url;
  document.queryForm.submit();
}

//取消查询
function cancel(){
	window.location.href="${pageContext.request.contextPath}/log/listOperationLog?currpage=1";
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
	  location.href="${pageContext.request.contextPath}/log/deleteOperationLog?logIds="+ids;
	}
	else
	{
	  alert("您还没有勾选呦");
	}
}
</script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
		  <li><a href="javascript:void(0)"><spring:message code="left.Performance.statement" /></a></li>
		  <li class="end"><a href="javascript:void(0)"><spring:message code="left.sevenList.education" /></a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
     <table class="tb" id="my_show" style="width:100%;">
		<thead>
		 <tr>
			<%--<th><input id="check_all" type="checkbox" onclick="checkAll();"/></th>
		   --%><th>名称</th>
		   <th>操作</th>
		 </tr>
		</thead>
		
		<tbody>
		 <tr>
		 <td>基表1-教学科研仪器设备表</td>
		 <td style="text-align:center;"><a class="search-button  search-button-change" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">查看</a></td>
		 </tr>
		 <tr>
		 <td>基表2-教学科研仪器设备增减变动情况表</td>
		 <td style="text-align:center;"> <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">查看</a> </td>
		 </tr>
		 <tr>
		 <td>基表3-贵重仪器设备表</td>
		 <td style="text-align:center;"><a class="search-button " href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">查看</a></td>
		 </tr>
		 <tr>
		 <td>基表4-教学实验项目表</td>
		 <td style="text-align:center;"><a class="search-button" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">查看</a></td>
		 </tr>
		 <tr>
		 <td>基表5-专任实验室人员表</td>
		 <td style="text-align:center;"> <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">查看</a></td>
		 </tr>
		 <tr>
		 <td>基表6-实验室基本情况表</td>
		 <td style="text-align:center;"><a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">查看</a> </td>
		 </tr>
		 <tr>
		 <td>基表7-实验室经费情况表</td>
		 <td style="text-align:center;"><a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">查看</a></td>
		 </tr>
		</tbody>
	</table>
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
