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
  //提交表单
  function submitForm(){
    if($.trim($("#centerNumber").val())=="")
    {
      alert("请填写实验中心编号！");
      return false;
    }
    if($.trim($("#centerName").val())=="")
    {
      alert("请填写实验中心名称！");
      return false;
    }
    if($("#user").val()=="")
    {
      alert("请填写中心主任！");
      return false;
    }
    if($("#campus").val()=="")
    {
      alert("请填写所属校区！");
      return false;
    }
    if($("#academy").val()=="")
    {
      alert("请填写所属学院！");
      return false;
    }
    if($("#building").val()=="")
    {
      alert("请填写所属楼宇！");
      return false;
    }
    document.center_form.action="${pageContext.request.contextPath}/labCenter/saveLabCenter";
    document.center_form.submit();
  }
  </script>
    <style>
        .content-box .tab_lab{
            margin:8px 0;
        }
    </style>
</head>
  
<body>
  <div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">导师互选</a></li>
			<li class="end"><a href="${pageContext.request.contextPath}/nwuChose/ChoseAttentionList?currpage=1">注意事项详情</a></li>
			
		</ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
      <div id="title">注意事项详情</div>
        <input class="btn btn-new" type="button" value="返回" onclick="window.history.go(-1)"/>
	</div>
    <table class="tab_lab">
      <tr>
        <th>编号</th><td>${choseAttention.id}</td>
        <th>标题</th><td>${choseAttention.tittle}</td>
      </tr>
        <th>内容</th><td>${choseAttention.content}</td>
        <th>类型</th>
        <c:if test="${choseAttention.type==1}">
	        <td> 导师制互选导师</td>
	    </c:if>
	    <c:if test="${choseAttention.type==2}">
	        <td> 导师制互选学生</td>
	    </c:if>
	    <c:if test="${choseAttention.type==3}">
	         <td> 论文制互选导师</td>
	    </c:if><c:if test="${choseAttention.type==4}">
	          <td> 论文制互选学生</td>
	     </c:if>
      <tr>
      	 <th>所属大纲</th><td colspan="3">${choseAttention.choseTheme.tittle}</td>
        
      </tr>
    </table>
	
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
