<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

<script type="text/javascript">
	//首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	function cancelQuery(){
		window.location.href="${pageContext.request.contextPath}/device/passDeviceLendList?page=1";
	}	
</script>
<style>
.tool-box td span {
    white-space: nowrap;
}
.tool-box input[type=text]{
	width:100px!important;
}
.combo{
    margin: 0;
    position: relative;
    top: 2px;
    overflow: hidden;
}
.tb_btm{
    text-align:left;
}
.tb_btm input{
    float:none;
}
.pass,
.reject{
    margin:0 0 0 5px;
    letter-spacing:1px;
}
.pass{
    color:#51ce3b;
}
.reject{
    color:#ff2828;
}
</style>
	<script type="text/javascript">
        //作废
        function blankOut(id) {
            if(confirm("确定作废吗")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/device/blankOutDeviceLending?idKey=" + id,
                    contentType: "application/json;charset=utf-8",
                    dataType: "text",
                    type: "post",
                    async: false,
                    success: function (data) {
                        if (data == "success") {
                            alert("已作废");
                            window.location.reload();
                        } else {
                            alert("作废失败");
                        }
                    },
                    error: function () {
                        alert("作废失败");
                    }
                });
            }
        }
	</script>
</head>

<body>
<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">开放预约</a></li>
						<li class="end"><a href="javascript:void(0)">设备借用管理</a></li>
					</ul>
				</div>
			</div>
			<sec:authorize ifNotGranted="ROLE_STUDENT">
<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
			<li class="TabbedPanelsTab" id="s1">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1">设备借用</a>
			</li>
			<li class="TabbedPanelsTab" id="s2">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1">我的设备借用申请</a>
			</li>
			<li class="TabbedPanelsTab selected" id="s3">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1">我的设备借用审核</a>
			</li>
			<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
				<li class="TabbedPanelsTab" id="s4">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1">设备借用作废列表</a>
				</li>
			</sec:authorize>
		</ul>
	</div></sec:authorize>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="sub_tit">
		<li tabindex="0">
		<a href="${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1">待审核</a>
		</li>
		<li class="st_selected" tabindex="0">
		<a href="${pageContext.request.contextPath}/device/passDeviceLendList?page=1">审核通过</a>
		</li>
		<li tabindex="0">
		<a href="${pageContext.request.contextPath}/device/rejectedDeviceLendList?page=1">审核拒绝</a>
		</li>
		
	</ul>
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">
		    <div class="tool-box">
		     <form:form name="queryForm" action="${pageContext.request.contextPath}/device/passDeviceLendList?page=1" method="post" modelAttribute="reservation">
				<table class="list_form">
					<tr>
						<td>
						    <span>名称/编号：</span>
						    <input type="text" id="deviceName" name="deviceName" placeholder="输入设备名称或编号查询" value="${deviceName}"/>
						</td>
						<td>
						    <span>流水号：</span>
						    <input type="text" id="lendBatch" name="lendBatch" placeholder="输入流水号" value="${lendBatch}"/>
						</td>
						<td class="label" valign="middle">
						    <span style="position:relative;margin:0 5px 0 0;">借用日期:</span>
						    <input  class="easyui-datebox"  id="starttime" name="starttime" value="${starttime}"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
						    <span style="position:relative;margin:0 2px;">到</span>
                            <input class="easyui-datebox"  id="endtime" name="endtime"  value="${endtime}" type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
						</td>
						<td class="tb_btm">
							<input type="submit" value="查询">
							<input class="cancel-submit" type="button" value="取消" onclick="cancelQuery();">
						</td>
					</tr >
					<%--<tr>--%>
						<%--<td class="tb_btm">--%>
							<%--<input type="submit" value="查询">--%>
							<%--<input class="cancel-submit" type="button" value="取消" onclick="cancelQuery();">--%>
						<%--</td>--%>
					<%--</tr >--%>
				</table>
			</form:form>
		    </div>		       
		    </div>
    	<div class="content-box">
    		<div class="title">
    			<div id="title">审核通过</div>
    		</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                        <th>流水号</th>
                        <th>借用设备</th>
                        <th>申请人</th>
                        <th>申请内容</th>
                        <th>借用日期</th>
                        <th>实际归还日期</th>
                        <th>审核结果</th>
						<c:if test="${projectName eq 'zjcclims'}">
							<sec:authorize ifAnyGranted="ROLE_LABMANAGER">
								<th>操作</th>
							</sec:authorize>
						</c:if>
						<c:if test="${projectName ne 'zjcclims'}">
							<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EQUIPMENTADMIN,ROLE_TEACHER">
								<th>操作</th>
							</sec:authorize>
						</c:if>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${deviceLendList}" var="reservation" varStatus="i">
                		<tr>
                        <td>${i.count}</td>
                        <td>${reservation.lendBatch}</td>
                        <td>${reservation.labRoomDevice.schoolDevice.deviceName}(${reservation.labRoomDevice.schoolDevice.deviceNumber})</td>
                        <td>${reservation.userByLendingUser.cname}</td>
                        
                        <td>${reservation.content}</td>
                        <td><fmt:formatDate value="${reservation.lendingTime.time}" pattern="yyyy-MM-dd"/> </td>
                        <td><fmt:formatDate value="${reservation.backTime.time}" pattern="yyyy-MM-dd"/> </td>

                         <%--<c:forEach items="${reservation.labRoomDeviceLendingResults}" var="s">
                              <c:if test="${s.CDictionary.id==615}">${s.user.cname}<font class="pass">(通过)</font><br></c:if>
                         </c:forEach>
                         <c:forEach items="${reservation.labRoomDeviceLendingResults}" var="s"> 
                              <c:if test="${s.CDictionary.id==616}">${s.user.cname}<font class="reject">(拒绝)</font><br></c:if>
                         </c:forEach>--%>
							 <c:if test="${auditState.get(i.count-1)==-2 }">
							<td>审核通过</td>
							</c:if>
							<c:if test="${auditState.get(i.count-1)==-1 }">
								<td>审核通过</td>
							</c:if>

                        <td>
	                    <c:if test="${reservation.CDictionary.id==653}">
							<c:if test="${projectName eq 'zjcclims'}">
								<sec:authorize ifAnyGranted="ROLE_LABMANAGER">
									<c:forEach items="${reservation.labRoomDevice.labRoom.labRoomAdmins}" var="la">
										<c:if test="${la.user.username eq user.username}">
											<a href="#" onclick="returnDevice(${reservation.id})">归还</a>&nbsp;&nbsp;
											<a href="#" onclick="blankOut('${reservation.id}')">作废</a>
										</c:if>
									</c:forEach>
								</sec:authorize>
							</c:if>
							<c:if test="${projectName ne 'zjcclims'}">
								<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_EQUIPMENTADMIN,ROLE_TEACHER">
									<a href="#" onclick="returnDevice(${reservation.id})">归还</a>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_LABMANAGER">
									<c:forEach items="${reservation.labRoomDevice.labRoom.labRoomAdmins}" var="la">
										<c:if test="${la.user.username eq user.username}">
											<a href="#" onclick="blankOut('${reservation.id}')">作废</a>
										</c:if>
									</c:forEach>
								</sec:authorize>
							</c:if>
                        </c:if>
							<c:if test="${reservation.CDictionary.id==654}">
								已归还<br>损坏情况及处理方式，损坏处理结果：<font color="blue">${reservation.remark}</font>
                        </c:if>
                        </td>      
                        </tr>
                        </c:forEach>
                       
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/passDeviceLendList?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/passDeviceLendList?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/passDeviceLendList?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/device/passDeviceLendList?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/device/passDeviceLendList?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/device/passDeviceLendList?page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
<!-- 分页模块 -->
</div>

</div>
</div>
</div>
</div>

<div id="return_device" class="easyui-window" closed="true" modal="true"
		minimizable="true" title="设备使用情况评价"
		style="width: 580px;height: 350px;padding: 20px">
		<div class="content-box">
			<form:form id="edit_form">
				<table>
					<tr>
						<td>损坏情况及处理方式，损坏处理结果</td>
						<th><input tpye="text" name="remark" /></th>
						<td>归还时间：</td>
						<th><input  class="easyui-datebox"  id="backtime" name="backtime"   type="text"  onclick="new Calendar().show(this);" style="width:100px;" /></th>
					</tr>
				</table>
				<div class="moudle_footer" style="background:none;border-top:none;">
					<div class="submit_link">
						<input class="btn" id="save" type="button"
							onclick="saveReturnDevice();" value="提交">
					</div>
				</div>
			</form:form>
		</div>
	</div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    
    var lend_id = 0;
    // 弹出归还表单
    function returnDevice(id)
    {
    	lend_id = id;
      $("#return_device").show();
      $("#return_device").window({height:"200px",top: "100px"});
      $("#return_device").window('open');
    }
    
    // 保存设备归还
    function saveReturnDevice(){
        var backtime = $('#backtime').datebox('getValue');
        if(!backtime || backtime == ""){
            alert("请选择归还时间！");
            return false;
		}
    	$.ajax({
            url:'${pageContext.request.contextPath}/device/returnDeviceLending?idKey='+lend_id,
            type:'POST',
           data:$('#edit_form').serialize(),
           error:function (request){
             alert('请求错误!');
           },
           success:function(){
        	   history.go(0);
           }         
     });
    }
</script>
<!-- 下拉框的js -->

</body>
</html>


