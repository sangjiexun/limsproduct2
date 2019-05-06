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
        function testDuplicated(){
            var labRoomNumber=$("#labRoomNumber").val();
            $.post('${pageContext.request.contextPath}/labRoom/testDuplicated?labRoomNumber='+labRoomNumber,function(data){
                if(data=="isDuplicated"){
                    alert("对不起，编号与现存的编号重复，请核实后重新填写！");
                }
            });
        }
	</script>
	<script type="text/javascript">
        function getLabAnnex(){
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/labRoom/findLabAnnexByLabCenter",
                data:{'labCenter':$("#labCenter").val()},
                success:function(data){
                    $("#labAnnex").html(data.labAnnex);
                    $("#labAnnex").trigger("liszt:updated");
                }
            });
        }
	</script>
	<script type="text/javascript">
        function submitForm(){
            if($("#labCenter").val()=="") {// 实验中心
                alert("请选择所属中心！");
                return false;
			}
            if(${annexManage eq 'true'}) {// 实验室管理模块
                if($("#labAnnex").val()=="") {
                    alert("请选择所属实验室！");
                    return false;
                }
            }
			if($("#buildNumber").val()==""){
				alert("请选择所属楼宇！");
				return false;
			}
            if($("#floor").val()==""){
                alert("请选择所在楼层！");
                return false;
            }
            /*if($("#LabRoomClassification").val()=="") {
                alert("请选择类别！");
                return false;
            }*/
        }
	</script>
	<script type="text/javascript">
        function getFloor(){
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/visualization/findFloorNumByBuildNumber",
                data:{'buildNumber':$("#buildNumber").val()},
                success:function(data){
                    $("#floor").html(data.floorNum);
                    $("#floor").trigger("liszt:updated");
                }
            });
        };

        function getSystemRoom() {
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/visualization/getSystemRoomByBuildNumber",
                data:{'buildNumber':$("#buildNumber").val()},
                success:function(data){
                    $("#systemRoom").html(data.roomValue);
                    $("#systemRoom").trigger("liszt:updated");
                }
            });
        }
        function saveLabRoom() {
            if($("#labCenter").val()=="") {// 实验中心
                alert("请选择所属中心！");
                return false;
            }
            if(${annexManage eq 'true'}) {// 实验室管理模块
                if($("#labAnnex").val()=="") {
                    alert("请选择所属实验室！");
                    return false;
                }
            }
            if($("#buildNumber").val()==""){
                alert("请选择所属楼宇！");
                return false;
            }
            if($("#floor").val()==""){
                alert("请选择所在楼层！");
                return false;
            }
            /*if($("#LabRoomClassification").val()=="") {
                alert("请选择类别！");
                return false;
            }*/
            $.ajax({
                url:'${pageContext.request.contextPath}/labRoom/saveLabRoom?type=1&page=${page}',
                type:'POST',
                data:$('#queryForm').serialize(),
                async:false,
                error:function (request){
                    alert('请求错误!');
                },
                success:function(){
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                }
            });
        }
        function closeMyWindow(){
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
	</script>

</head>

<body>
<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
				<div class="content-box">
					<form:form id="queryForm" action="${pageContext.request.contextPath}/labRoom/saveLabRoom?type=1&page=${page}" method="POST" modelAttribute="labRoom">
						<div class="new-classroom" style="border: 1px solid #ccc;">
							<label style="float: left; width: 100%; font-size: 14px;text-align: center;"><spring:message code="all.trainingRoom.labroom"/>基本信息（必填）</label>
							<fieldset>
								<form:hidden path="id"/>
								<form:hidden path="CDictionaryByIsAudit.id"/>
								<form:hidden path="CDictionaryByAllowLending.id"/>
								<form:hidden path="CDictionaryByAllowSecurityAccess.id"/>
								<form:hidden path="CDictionaryByTrainType.id"/>
								<form:hidden path="labRoomReservation" />
								<label>编号： </label>
								<form:input path="labRoomNumber" id="labRoomNumber"  required="true" onchange="testDuplicated();"/>
							</fieldset>
							<fieldset>
								<label>名称：</label>
								<form:input path="labRoomName" required="true"/>
							</fieldset>
							<fieldset>
								<label>所属<spring:message code="all.training.name"/>中心：</label>
								<form:select id="labCenter" path="labCenter.id" class="chzn-select" onchange="getLabAnnex()" required="required">
									<form:option value="">请选择</form:option>
									<c:forEach items="${listLabCenter}" var="curr">
										<form:option value="${curr.id}">${curr.centerName}</form:option>
									</c:forEach>
								</form:select>
							</fieldset>
							<c:if test="${annexManage eq 'true'}">
								<fieldset>
									<label>所属<spring:message code="all.trainingRoom.labroom"/>：</label>
									<form:select id="labAnnex" path="labAnnex.id" class="chzn-select" required="true">
										<form:option value="">请选择</form:option>
										<form:options items="${labAnnexList}" itemLabel="labName" itemValue="id"/>
									</form:select>
								</fieldset>
							</c:if>
							<fieldset>
								<label>所属楼宇：</label>
								<form:select id="buildNumber" path="systemBuild.buildNumber" class="chzn-select" onchange="getFloor();getSystemRoom();">
									<form:option value="">请选择</form:option>
									<form:options items="${systemBuilds}" itemLabel="buildName" itemValue="buildNumber"/>
								</form:select>
							</fieldset>
							<fieldset>
								<label>所在楼层：</label>
								<form:select id="floor" path="floorNo" class="chzn-select" required="true">
									<form:option value="">请先切换楼宇</form:option>
									<c:forEach items="${floors}" var="floor">
										<form:option value="${floor.floorNo}">${floor.floorName}</form:option>
									</c:forEach>
								</form:select>
							</fieldset>
							<c:if test="${project eq 'zjcclims'}">
								<fieldset>
									<label><spring:message code="all.trainingRoom.labroom"/>是否开放：</label>
									<form:select path="isOpen" required="true">
										<form:option value="">请选择</form:option>
										<form:option value="1">是</form:option>
										<form:option value="0">否</form:option>
									</form:select>
								</fieldset>
							</c:if>
							<c:if test="${stationNum eq 'true'}"><!-- 有工位相关需求时显示 -->
							<fieldset>
								<label>可预约工位数：</label>
								<form:input path="labRoomWorker" required="true"/>
							</fieldset>
							</c:if>
						</div>
						<div class="new-classroom" style="border: 1px solid #ccc;">
							<label style="float: left; width: 100%; font-size: 14px;text-align: center;"><spring:message code="all.trainingRoom.labroom"/>附加信息（非必填）</label>
							<c:if test="${baseManage eq 'true'}">
								<fieldset>
									<label>所属基地：</label>
									<form:select  id="labBase" path="labBase.id" class="chzn-select">
										<form:option value="">请选择</form:option>
										<form:options items="${labBaseList}" itemLabel="labName" itemValue="id"/>
									</form:select>
								</fieldset>
							</c:if>
							<fieldset>
								<label><spring:message code="all.trainingRoom.labroom"/>地点：</label>
								<form:select id="systemRoom" path="systemRoom.roomNumber" class="chzn-select">
									<form:option value="">请先切换楼宇</form:option>
									<option value="${labRoom.systemRoom.roomNumber}" selected="selected">${labRoom.systemRoom.roomName}</option>
								</form:select>
							</fieldset>
							<%--<fieldset>
								<label>排课是否判冲：<font color="red">*</font></label>
								<form:select path="isSpecial" required="true">
									<form:option value="">请选择</form:option>
									<form:option value="0">是</form:option>
									<form:option value="1">否</form:option>
								</form:select>
							</fieldset>--%>
							<c:if test="${project eq 'zjcclims'}">
								<fieldset>
									<label><spring:message code="all.trainingRoom.labroom"/>等级：</label>
									<form:select path="labRoomLevel">
										<form:option value="">请选择</form:option>
										<form:option value="0">特级</form:option>
										<form:option value="1">一级</form:option>
										<form:option value="2">二级</form:option>
									</form:select>
								</fieldset>
							</c:if>
							<fieldset>
								<label><spring:message code="all.trainingRoom.labroom"/>类别：</label>
								<form:select id="LabRoomClassification" path="CDictionaryByLabRoomClassification.id" class="chzn-select">
									<form:option value="">请选择</form:option>
									<form:options items="${labRoomClassifications}" itemLabel="CName" itemValue="id"/>
								</form:select>
							</fieldset>
							<fieldset>
								<label>建立时间：</label>
								<input name="labRoomTimeCreate" class="easyui-datebox" value="<fmt:formatDate value='${labRoom.labRoomTimeCreate.time}' pattern='yyyy-MM-dd'/>" />
							</fieldset>
							<fieldset>
								<label>容量：</label>
								<form:input path="labRoomCapacity" onkeyup="value=value.replace(/[^\d]/g,'') " placeholder="请输入大于零的整数"/>
							</fieldset>
							<fieldset>
								<label>使用面积：</label>
								<form:input path="labRoomArea"/>
							</fieldset>
							<fieldset>
								<label><spring:message code="all.trainingRoom.labroom"/>分类：</label>
								<form:select id="CDictionaryByLabRoomSort" path="CDictionaryByLabRoomSort.id" class="chzn-select">
									<form:option value="">请选择</form:option>
									<form:options items="${labRoomSorts}" itemLabel="CName" itemValue="id"/>
								</form:select>
							</fieldset>
							<fieldset>
								<label><spring:message code="all.trainingRoom.labroom"/>类型</label>
								<form:select id="CDictionaryByLabRoom" path="CDictionaryByLabRoom.id" class="chzn-select">
									<form:option value="">请选择</form:option>
									<form:options items="${labRoomTypes}" itemLabel="CName" itemValue="id"/>
								</form:select>
							</fieldset>
							<fieldset>
								<label>有无多媒体：</label>
								<form:select id="CDictionaryByIsMultimedia" path="CDictionaryByIsMultimedia.id" class="chzn-select">
									<form:option value="">请选择</form:option>
									<form:options items="${isMultimedia}" itemLabel="CName" itemValue="id"/>
								</form:select>
							</fieldset>
							<fieldset>
								<label>所属学科：</label>
								<form:select path="systemSubject12.SNumber" class="chzn-select">
									<form:option value="">请选择</form:option>
									<c:forEach items="${subject12s}" var="subject">
										<form:option value="${subject.SNumber}">${subject.SName}</form:option>
									</c:forEach>
								</form:select>
							</fieldset>
							<fieldset>
								<label>是否可用：</label>
								<form:select path="labRoomActive">
									<form:option value="1">可用</form:option>
									<form:option value="0">不可用</form:option>
								</form:select>
							</fieldset>
							<%--<fieldset>
								<label>是否可预约：</label>
								<form:select path="labRoomReservation">
									<form:option value="">请选择</form:option>
									<form:option value="1">可预约</form:option>
									<form:option value="0">不可预约</form:option>
								</form:select>
							</fieldset>--%>
							<fieldset>
								<label>是否校企共建：</label>
								<form:select path="isSchoolEnterpriseCooperation">
									<form:option value="">请选择</form:option>
									<form:option value="1">是</form:option>
									<form:option value="0">否</form:option>
								</form:select>
							</fieldset>
							<fieldset>
								<label>是否生产性<spring:message code="all.trainingRoom.labroom"/>：</label>
								<form:select path="isRoductivity">
									<form:option value="">请选择</form:option>
									<form:option value="1">是</form:option>
									<form:option value="0">否</form:option>
								</form:select>
							</fieldset>
							<fieldset>
								<label>是否仿真<spring:message code="all.trainingRoom.labroom"/>：</label>
								<form:select path="isSimulation">
									<form:option value="">请选择</form:option>
									<form:option value="1">是</form:option>
									<form:option value="0">否</form:option>
								</form:select>
							</fieldset>
							<fieldset>
								<label><spring:message code="all.trainingRoom.labroom"/>描述：</label>
								<form:textarea path="labRoomIntroduction" style="width:1000px;height:100px"/>
							</fieldset>
							<fieldset>
								<label>规章制度：</label>
								<form:textarea path="labRoomRegulations" style="width:1000px;height:100px"/>
							</fieldset>
							<fieldset>
								<label><spring:message code="all.trainingRoom.labroom"/>注意事项：</label>
								<form:textarea path="labRoomAttentions" style="width:1000px;height:100px"/>
							</fieldset>
							<fieldset>
								<label>获奖信息：</label>
								<form:textarea path="labRoomPrizeInformation" style="width:1000px;height:100px"/>
							</fieldset>
						</div>
						<div class="moudle_footer">
							<div class="submit_link">
								<input class="btn" id="save" type="button" onclick="saveLabRoom()" value="确定">
								<input class="btn btn-return" type="button" value="返回" onclick="closeMyWindow()">
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
