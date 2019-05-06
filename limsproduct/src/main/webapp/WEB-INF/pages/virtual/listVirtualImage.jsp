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
        function cancel()
        {
            window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9&type=1";
        }
        //跳转
        function targetUrl(url)
        {
            document.queryForm.action=url;
            document.queryForm.submit();
        }
        //排序

       /* var asc=true;//声明全局变量asc
        function orderByNumber(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=10&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=0&type=1";
        }
        function orderByName(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=11&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=1&type=1";
        }
        function orderByLabCenter(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=12&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=2&type=1";
        }
        function orderByCapacity(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=13&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=3&type=1";
        }
        function orderByArea(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=14&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=4&type=1";
        }
        function orderByActive(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=15&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=5&type=1";
        }
        function orderByReservation(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=16&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=6&type=1";
        }
        function orderByRoomAddress(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=17&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=7&type=1";
        }
        function orderByLevel(){
            asc=!asc;
            if(asc){
                window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=18&type=1";
            }else window.location.href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=8&type=1";
        }*/

	</script>
</head>

<body>
<script type="text/javascript">
    //设置
  /*  function openSetupLink(labRoomId,currpage,type){//将labRoomId page传递到后台
        var url ="${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/"+type;

        window.location.href=url;
    }*/

    function submitUrl(){
        window.location.href="${pageContext.request.contextPath}/virtual/updateVirtualImage";
    }

    function getLabRoomSoft(labId){
        window.location.href="${pageContext.request.contextPath}/public/listVirtualLabSoftware?currpage=1&labId="+labId;

    }
</script>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
			<li class="end"><a href="javascript:void(0)">虚拟镜像信息</a></li>
		</ul>
	</div>
</div>
<%--<div id="TabbedPanels1" class="TabbedPanels">
      <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
              <li class="TabbedPanelsTab selected" id="s1"><a
                  class="iStyle_Feelings_Tree_Leaf"
                  href="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=9"><spring:message code="all.trainingRoom.labroom" />列表</a>
              </li>
          <sec:authorize ifAnyGranted="ROLE_EXCENTERDIRECTOR,ROLE_SUPERADMIN,ROLE_LABMANAGER" >
              <li class="TabbedPanelsTab" id="s2"><a
                  class="iStyle_Feelings_Tree_Leaf"
                  href="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1">&nbsp;&nbsp;&nbsp;&nbsp;机房电脑使用记录</a>
              </li>
			</sec:authorize>
		</ul>
	</div>--%>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<div class="title">
						<div id="title">虚拟镜像列表</div>
						<%--<c:if test="${sessionScope.auth_level eq '1' || sessionScope.auth_level eq '3' || sessionScope.auth_level eq '5'}">
							<a class="btn btn-new" href="${pageContext.request.contextPath}/labRoom/editLabRoom?labRoomId=0&type=1&page=${page}">新建</a>
						</c:if>--%>
					</div>

					<div class="tool-box">
						<form:form name="queryForm" action="${pageContext.request.contextPath}/labRoom/listLabRoom?currpage=1&orderBy=${orderBy }&type=1" method="post" modelAttribute="labRoom">
							<ul>
								<li><input type="button" value="更新" onclick="submitUrl()">
								<%--<li><spring:message code="all.trainingRoom.labroom" />名称： </li>
								<li><form:input id="lab_name" path="labRoomName"/></li>
								<li><spring:message code="all.trainingRoom.labroom" />编号： </li>
								<li><form:input id="labRoomNumber" path="labRoomNumber"/></li>
								<c:if test="${baseManage eq 'true'}">
								<li>所属基地： </li>
								<li><form:input id="lab_base" path="labBase.labName"/></li>
                                </c:if>
								<li>所属中心： </li>
								<li><form:select id="labCenter" path="labCenter.id" class="chzn-select" >
                                    <form:option value="">请选择</form:option>
                                        <c:forEach items="${listLabCenter}" var="l">
                                         <form:option value="${l.id}">${l.centerName}</form:option>
                                    </c:forEach>
                                    </form:select>
								<li>可预约工位数：</li>
								<li>
									<select name="searchflg" id="searchflg">
										<option value="">-请选择-</option>
										<option value="1" <c:if test="${'1' eq searchflg}">selected</c:if>>等于</option>
										<option value="2" <c:if test="${'2' eq searchflg}">selected</c:if>>大于</option>
										<option value="3" <c:if test="${'3' eq searchflg}">selected</c:if>>小于</option>
									</select>
								</li>
								<li><input type="text" id="worker" name="worker" value="${worker}" style="float: none;width:50px;"></li>
								<li>面积：</li>
								<li>
									<select name="searchflg1" id="searchflg1">
										<option value="">-请选择-</option>
										<option value="1" <c:if test="${'1' eq searchflg1}">selected</c:if>>等于</option>
										<option value="2" <c:if test="${'2' eq searchflg1}">selected</c:if>>大于</option>
										<option value="3" <c:if test="${'3' eq searchflg1}">selected</c:if>>小于</option>
									</select>
								</li>
								<li><input type="text" id="area" name="area" value="${area}" style="float: none; width:50px;"></li>
								<li>
									<input type="button" value="取消" onclick="cancel();"/><input type="submit" value="查询"/></li>--%>
							</ul>
						</form:form>
					</div>

					<%--<div id="showRegulations" class="easyui-window" closed="true" modal="true" minimizable="true" title="规章制度详情" style="width: 580px;height: 250px;padding: 20px">
						<div class="content-box">
							<table id="my_show">
								<tbody id="labRoom_body">

								</tbody>
							</table>
						</div>
					</div>--%>

					<table class="tb" id="my_show">
						<thead>
						<tr>
							<th>镜像编号</th>
							<th>镜像名称</th>
							<th>硬件链接</th>
							<th>图片编号</th>
							<th>供应商</th>
							<th>备注</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${virtualImageList}" var="curr">
							<tr>
								<td>${curr.id}</td>
								<td>${curr.name}</td>
								<td>${curr.hardwareSet}</td>
								<td>${curr.imageCode}</td>
								<td>${curr.provider}</td>
								<td>${curr.setNote}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<!-- 分页[s] -->
					<div class="page" >
						${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=1')" target="_self">首页</a>
						<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
						第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
						<option value="${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
						<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
							<c:if test="${j.index!=pageModel.currpage}">
								<option value="${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=${j.index}">${j.index}</option>
							</c:if>
						</c:forEach></select>页
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
						<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=${pageModel.lastPage}')" target="_self">末页</a>
					</div>
					<!-- 分页[e] -->
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
