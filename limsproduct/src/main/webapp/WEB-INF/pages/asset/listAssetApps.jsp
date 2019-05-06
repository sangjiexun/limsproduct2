<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
//取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=9";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  $(document).ready(function(){
      var s=${assetAuditStatus};
      if(s==9){
    	  $("#s9").addClass("TabbedPanelsTab selected");
    	  $("#s4").addClass("TabbedPanelsTab");
    	  $("#s1").addClass("TabbedPanelsTab");
    	  $("#s2").addClass("TabbedPanelsTab");
      }
      if(s==4){
    	  $("#s4").addClass("TabbedPanelsTab selected");
    	  $("#s9").addClass("TabbedPanelsTab");
    	  $("#s1").addClass("TabbedPanelsTab");
    	  $("#s2").addClass("TabbedPanelsTab");
      }
      if(s==1){
    	  $("#s1").addClass("TabbedPanelsTab selected");
    	  $("#s9").addClass("TabbedPanelsTab");
    	  $("#s4").addClass("TabbedPanelsTab");
    	  $("#s2").addClass("TabbedPanelsTab");
      }
      if(s==2){
    	  $("#s2").addClass("TabbedPanelsTab selected");
    	  $("#s1").addClass("TabbedPanelsTab");
    	  $("#s9").addClass("TabbedPanelsTab");
    	  $("#s4").addClass("TabbedPanelsTab");
      }
	});
	function exportAssetApp(url){
		location.href=url;
	}
  </script>
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)"><spring:message code="left.material.management"/></a></li>
		<li class="end"><a href="javascript:void(0)"><spring:message code="left.material.purchase"/></a></li>
	  </ul>
	</div>
  </div>
  
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s9"><a href="${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=9">全部</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=4">未提交</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=1">通过</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=2">拒绝</a></li>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/asset/newAssetApp?flag=0">新建申购</a>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/asset/exportAssetApp?currpage=${currpage}&assetAuditStatus=${assetAuditStatus}&isAudit=0">导出</a>
</ul>
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <%--<div class="title">--%>
	  <%--<div id="title">申购列表</div>--%>
		<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/asset/newAssetApp?flag=0">新建申购</a>--%>
		<%--<a class="btn btn-new" href="${pageContext.request.contextPath}/asset/exportAssetApp?currpage=${currpage}&assetAuditStatus=${assetAuditStatus}&isAudit=0">导出</a>--%>
		<%--<c:if test="${startDate ne null}">--%>
			<%--<span class="medcine_time">--%>
				<%--<span>药品申购提交时间为:</span>--%>
	          <%--<fmt:formatDate value="${startDate.time}" pattern="yyyy-MM-dd" />--%>
	          <%--~--%>
	          <%--<fmt:formatDate value="${endDate.time}" pattern="yyyy-MM-dd" />--%>
	        <%--</span>--%>
		<%--</c:if>--%>
	<%--</div>--%>
	
	<div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=${assetAuditStatus}" method="post" modelAttribute="assetApp">
			 <ul>
  				<li>申购编号:
  					<form:select id="appNo" path="appNo" class="chzn-select">
  					<form:option value="">请选择</form:option>
  						<c:forEach items="${listAssetApps}" var="curr">
  							<form:option value="${curr.appNo}">${curr.appNo}</form:option>
  						</c:forEach>
  					</form:select>
  				</li>  
  				<li>
					<input type="submit" value="查询"/>
			      <input class="cancel-submit" type="button" value="取消" onclick="cancel()"/>
			      </li>
				 <c:if test="${startDate ne null}">
			<span class="medcine_time">
				<span>药品申购提交时间为:</span>
	          <fmt:formatDate value="${startDate.time}" pattern="yyyy-MM-dd" />
	          ~
	          <fmt:formatDate value="${endDate.time}" pattern="yyyy-MM-dd" />
	        </span>
				 </c:if>
  				</ul>
			 
		 </form:form>
	</div>
	
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	    <th>序号</th>
	    <th>申购编号</th>
	    <th>申购时间</th>
	    <th>物资种类</th>
	    <th>实验大纲</th>
	    <th>实验项目</th>
	    <th>申购总金额</th>
	    <th>申请人</th>
	    <th>状态</th>
	    <th>审核状态</th>
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${listAssetApps}" var="curr" varStatus="i">
	  <tr>
	    <td>${i.count+pageSize*(currpage-1)}</td>
	    <td>${curr.appNo}</td>
	    <td><fmt:formatDate value="${curr.appDate.time}" pattern="yyyy-MM-dd" /></td>
	    <td>${num[i.count-1]}</td>
	    <td>
	    	<c:if test = "${curr.type == 0}">公用</c:if>
	    	<c:if test = "${curr.type != 0}">${curr.operationItem.operationOutline.labOutlineName}</c:if>
	    </td> 
	    <td>
	    	<c:if test = "${curr.type == 0}">--</c:if>
	    	<c:if test = "${curr.type != 0}">${curr.operationItem.lpName}</c:if>
	    </td>
	    <td>${totalPrice[i.count-1]}</td>
	    <td>${curr.user.cname}</td>
	    
	    <c:if test="${curr.assetAuditStatus eq 4 &&fn:length(curr.assetAppRecords)>0}"><td><a onclick="chooseFirstAuditer(${curr.id})">提交</a></td></c:if>
		  <c:if test="${curr.assetAuditStatus eq 4 &&fn:length(curr.assetAppRecords)==0}"><td><font color="red">请添加申购药品</font></td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 1}"><td>已提交</td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 2}"><td>已提交</td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 3}"><td>已提交</td></c:if>
	     <c:if test="${curr.assetAuditStatus eq 5}"><td>已提交</td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 4}"><td>未提交</td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 1}"><td>审核通过</td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 3 or curr.assetAuditStatus eq 5}"><td>审核中</td></c:if>
	    <c:if test="${curr.assetAuditStatus eq 2}"><td><font color="red">审核拒绝</font></td></c:if>
	    <td><a href="${pageContext.request.contextPath}/asset/getAssetApp?id=${curr.id}">查看</a>
	     <c:if test="${curr.assetAuditStatus eq 4}">
	      <a href="${pageContext.request.contextPath}/asset/editAssetApp?id=${curr.id}">编辑</a>
	      <a href="${pageContext.request.contextPath}/asset/deleteAssetApp?id=${curr.id}" onclick="return confirm('确定删除？');">删除</a>
	      
	      </c:if>
				<%--<c:if test="${curr.assetAuditStatus eq 2}">
                 <a href="${pageContext.request.contextPath}/asset/editAssetApp?id=${curr.id}">编辑</a>
                <a href="${pageContext.request.contextPath}/asset/submitAssetApps?id=${curr.id}">提交</a></c:if>
                <c:if test="${curr.auditStatus lt 1}">
                <a href="${pageContext.request.contextPath}/asset/viewNDevicePurchase?id=${curr.id}">添加设备</a>
                </c:if>
                <c:if test="${curr.auditStatus eq 0}">
                <a href="${pageContext.request.contextPath}/asset/submitDevicePurchase?id=${curr.id}">提交</a>
               </c:if>
              --%>
	    </td>
	  </tr>
 	  </c:forEach>
	  </tbody>
	</table>
	
	<div id="check_user" class="easyui-window" closed="true" modal="true" minimizable="true" title="指定一级审核人" style="width: 580px;height: 350px;padding: 20px">
  <div class="content-box">
    <table>
      <tr>
        <td>指定审核人</td>
        <td>
          <select name="firstAudit" id="firstAudit" class="chzn-select">
          <option value="">请选择</option>
            <c:forEach items="${fisrtAuditUsers}" var="u">
              <option value="${u.username}">[${u.username}]${u.cname}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
    </table>
    <div class="moudle_footer">
    <div class="submit_link">
        <input class="btn" id="save" type="button" onclick="submitForAudit();" value="提交">
    </div>
</div>
  </div>
  </div>
	 <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
			var appId = 0;
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
		    
		    function chooseFirstAuditer(id){
			//href="${pageContext.request.contextPath}/asset/submitAssetApps?id=${id}"  
			appId =id;
		    $("#check_user").show();
		    $("#check_user").window('open'); 
		}
		
		function submitForAudit(){
			if($("#firstAudit").val()==""){
				alert("请选择一级审核人！");
			}else{
				window.location.href="${pageContext.request.contextPath}/asset/submitAssetApps?id="+appId+"&auditUser="+$("#firstAudit").val();
			}
		}
		</script>
	<!-- 下拉框的js -->
	<!-- 分页[s] -->
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetApps?currpage=1&assetAuditStatus=${assetAuditStatus}')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetApps?currpage=${pageModel.previousPage}&assetAuditStatus=${assetAuditStatus}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/asset/listAssetApps?currpage=${pageModel.currpage}&assetAuditStatus=${assetAuditStatus}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/asset/listAssetApps?currpage=${j.index}&assetAuditStatus=${assetAuditStatus}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetApps?currpage=${pageModel.nextPage}&assetAuditStatus=${assetAuditStatus}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/asset/listAssetApps?currpage=${pageModel.lastPage}&assetAuditStatus=${assetAuditStatus}')" target="_self">末页</a>
    </div>
    <!-- 分页[e] -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
