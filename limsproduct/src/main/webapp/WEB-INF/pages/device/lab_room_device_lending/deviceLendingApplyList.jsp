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
	<link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">

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
	window.location.href="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1";
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
		//撤回
        function revokeDeviceLending(id) {
            if(confirm("确定撤回吗")) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/device/revokeDeviceLending?idKey=" + id,
                    contentType: "application/json;charset=utf-8",
                    dataType: "text",
                    type: "post",
                    async: false,
                    success: function (data) {
                        if (data == "success") {
                            alert("已撤回");
                            window.location.reload();
                        } else {
                            alert("撤回失败");
                        }
                    },
                    error: function () {
                        alert("撤回失败");
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
					<li class="TabbedPanelsTab selected" id="s2">
						<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1">我的设备借用申请</a>
					</li>
					<li class="TabbedPanelsTab" id="s3">
						<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1">我的设备借用审核</a>
					</li>
					<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
						<li class="TabbedPanelsTab" id="s4">
							<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1">设备借用作废列表</a>
						</li>
					</sec:authorize>
				</ul>
			</div>
		</sec:authorize>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">
		    <div class="tool-box">
		     <form name="queryForm" action="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1" method="post" modelAttribute="lrdlr">
				<table class="list_form">
					<tr>
						<td >
						    <span>名称/编号:</span>
						    <input type="text" id="deviceName" name="deviceName" placeholder="输入设备名称或编号查询" value="${deviceName}"/>
						</td>
						<td >
						    <span>流水号:</span>
						    <input type="text" id="lendBatch" name="lendBatch" placeholder="输入流水号" value="${lendBatch}"/>
						</td>
						<td class="label" valign="middle" >
						    <span style="position:relative;top:-6px;margin:0 5px 0 0;">借用日期:</span>
							<input  class="easyui-datebox"  id="starttime" name="starttime" value="${starttime}"  type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
						    <span style="position:relative;top:-5px;margin:0 2px;">到</span>
							<input class="easyui-datebox"  id="endtime" name="endtime"  value="${endtime}" type="text"  onclick="new Calendar().show(this);" style="width:100px;" />
						</td>
						<td class="tb_btm">
							<input type="submit" value="查询"/>
							<input class="cancel-submit" type="button" value="取消" onclick="cancelQuery();"/>
						</td>
					</tr >
					<%--<tr>--%>
						<%--<td class="tb_btm">--%>
							<%--<input type="submit" value="查询"/>--%>
							<%--<input type="button" value="取消" onclick="cancelQuery();"/>--%>
						<%--</td>--%>
					<%--</tr >--%>
			</table>
			</form>
		    </div>
		</div>
    	<div class="content-box">
    		<div class="title">
    			<div id="title">所有申请</div>
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
                        <th>审核结果</th>
                        <th>归还结果</th>
                        <th>更改/删除</th>
                        <th>提交</th>
                    </tr>
                </thead>
                
                <tbody>
	                <!-- 判断相同批次的当前批次 -->
					<c:set var="ifRowspanBatch" value="0"></c:set>
                		<c:forEach items="${deviceLendingApplyList}" var="reservation0" varStatus="i">
	                		<!--合并相同批次的变量  -->
							<c:set var="rowspanBatch" value="0"></c:set>  
	                		 <c:forEach items="${deviceLendingApplyList}" var="reservation1"  varStatus="j">
						         <c:if test="${reservation1.lendBatch==reservation0.lendBatch }">
						            <c:set value="${rowspanBatch + 1}" var="rowspanBatch" />
						         </c:if>
						     </c:forEach>
                		
                		<tr>
                        <td>${i.count}</td>
                        <c:if test="${ifRowspanBatch!=reservation0.lendBatch}">
                        	<td rowspan="${rowspanBatch }">${reservation0.lendBatch}</td>
                        </c:if>
                        <td>${reservation0.labRoomDevice.schoolDevice.deviceName}(${reservation0.labRoomDevice.schoolDevice.deviceNumber})</td>
                        <td>${reservation0.userByLendingUser.cname}</td>
                        <td>${reservation0.content}</td>
                        <td><fmt:formatDate value="${reservation0.lendingTime.time}" pattern="yyyy-MM-dd"/> </td>
						<td>
							<c:if test="${auditState.get(i.count-1)>0 }">
								<%--<td>审核中</td>--%>
								${auditShow.get(i.count-1)}
							</c:if>
								<c:if test="${auditState.get(i.count-1)==-2 }">
							审核通过
							</c:if>
							<c:if test="${auditState.get(i.count-1)==-1 }">
								审核通过
							</c:if>
							<c:if test="${auditState.get(i.count-1)==0 }">
								审核拒绝
							</c:if>
							</td>
                         <%--<c:forEach items="${reservation0.labRoomDeviceLendingResults}" var="s">
                              <c:if test="${s.CDictionary.id==615}">${s.user.cname}<font class="pass">(通过)</font><br></c:if>
                         </c:forEach>
                         <c:forEach items="${reservation0.labRoomDeviceLendingResults}" var="s">
                              <c:if test="${s.CDictionary.id==616}">${s.user.cname}<font class="reject">(拒绝)</font><br></c:if>
                         </c:forEach>--%>
							 <%--<c:if test="${reservation0.stage==1}">${reservation0.userByDepartmentHead.cname}(<font color="blue">通过</font>)<br></c:if>
							 <c:forEach items="${reservation0.labRoomDevice.labRoom.labRoomAdmins}" var="s">
								 <c:if test="${reservation0.stage==2}">
									 ${reservation0.userByDepartmentHead.cname}(<font color="blue">通过</font>)<br>
									 ${s.user.cname}(<font color="blue">通过</font>)<br>
								 </c:if>
								 <c:if test="${reservation0.stage==3}">
									 ${reservation0.userByDepartmentHead.cname}(<font color="blue">通过</font>)<br>
									 ${s.user.cname}(<font color="blue">通过</font>)<br>
									 ${labRoomHeads.cname}(<font color="blue">通过</font>)<br>
								 </c:if>
							 </c:forEach>--%>

                        <td>${reservation0.CDictionary.CName}
                        <td>
							<c:if test="${reservation0.stage!=null}">
								<a href="${pageContext.request.contextPath}/device/auditDepartmentDeviceLending?idKey=${reservation0.id}">查看审核结果</a>
								<c:if test="${reservation0.stage == 0}">
									<a href="${pageContext.request.contextPath}/device/deleteDeviceLending?idKey=${reservation0.id}" onclick="return confirm('确定撤回吗？');">撤回</a>
								</c:if>
								<c:if test="${reservation0.stage == 1 || reservation0.stage == 2}">
									<a href="javascript:void(0)" onclick="revokeDeviceLending('${reservation0.id}')">撤回</a>
								</c:if>
							</c:if>
	                        <c:if test="${reservation0.stage==null}">
		                        <a href="${pageContext.request.contextPath}/device/editDeviceLending?idKey=${reservation0.id}">编辑</a>
		                        <a href="${pageContext.request.contextPath}/device/deleteDeviceLending?idKey=${reservation0.id}">删除</a>
	                        </c:if>
                        </td>
                        <c:if test="${ifRowspanBatch!=reservation0.lendBatch}">
	                        <td rowspan="${rowspanBatch }">
		                        <c:if test="${reservation0.stage==null}">
			                        <a href="${pageContext.request.contextPath}/device/submitDeviceLending?lendBatch=${reservation0.lendBatch}">提交申请</a>
		                        </c:if>
	                        </td>
                        </c:if>
                        <c:set var="ifRowspanBatch" value="${reservation0.lendBatch}"></c:set>
                        </tr>
                        </c:forEach>
                       
                </tbody>
            </table>
            
<!-- 分页模块 -->
<div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/deviceLendingApplyList?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)" onclick="next('${pageContext.request.contextPath}/device/deviceLendingApplyList?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)" onclick="last('${pageContext.request.contextPath}/device/deviceLendingApplyList?page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
<!-- 分页模块 -->
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
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    
   
</script>
<!-- 下拉框的js -->

</body>
</html>


