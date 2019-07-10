<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>


<html>
<head>
<title></title>
<meta name="decorator" content="iframe"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
<style type="text/css" media="screen">
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/icon.css");
			@import url("${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css");
			@import url("${pageContext.request.contextPath}/css/style.css");
			@import url("${pageContext.request.contextPath}/static_limsproduct/css/global_static.css");
</style>
<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">

<!-- 文件上传的样式和js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dhulims/labroom/editDevice.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<!-- 下拉框的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->


<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript">
    /**
     添加实验室禁用时间段
     */
    function newLimitTime() {
        $("#addLabRoomLimitTime").show();
        $("#addLabRoomLimitTime").window('open');
    }

    //实验室禁用时间表单提交
    function submitLimitTimeForm(){
        if($("#startTimeLimit").val()==''){
            alert("请填写起始时间")
        }else if($("#endTimeLimit").val()==''){
            alert("请填写结束时间")
        }else if($("#startDateLimit").val()==''){
            alert("请填写起始日期")
        }else if($("#endDateLimit").val()==''){
            alert("请填写结束日期")
        }else if($("#weekday1Limit").val()==''){
            alert("请选择星期")
        }else{
            document.labRoomLimitTimeForm.submit();
        }
    }

    //实验室开放时间设置
    function editOpenTime() {
        $("#editOpenTime").show();
        $("#showOpenTime").hide();
        $("#show_open_time").hide();
    }

    //开放设置保存
    function saveLabRoomOpenTime() {
        var startHour = $("#startHour").val();
        var startMinute = $("#startMinute").val();
        var endHour = $("#endHour").val();
        var endMinute = $("#endMinute").val();
        var startHourInweekend = $("#startHourInweekend").val();
        var endHourInweekend = $("#endHourInweekend").val();
        if (parseInt(startHourInweekend) > parseInt(endHourInweekend)) {
            alert("开始时间不能大于结束时间，请重新选择!");
        }
        else if (parseInt(startHour) > parseInt(endHour)) {
            alert("开始时间不能大于结束时间，请重新选择!");
        } else {
            document.openTimeForm.submit();
            $('#editOpenTime').hide();
            $('#showOpenTime').show();
            $('#show_open_time').show();
        }
    }
</script>

<script >

function closeMyWindow(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
</script>

<style>
    .chzn-container-multi .chzn-choices li.search-field input[type="text"] {
        width: 300px!important;
        height: 23px;}
.tool-box2 th{text-align:left;}
table label{
position:relative;
top:-6px;
margin-left:3px;
}
</style>
</head>


<body>

<div class="right-content">	
	<div id="TabbedPanels1" class="TabbedPanels">
	 <ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="openSetupLink(${labRoom.id},${currpage },${type})">实验室预约设置</a>
		</li>
        <li class="TabbedPanelsTab" tabindex="0">
		    <a href="javascript:void(0);" onclick="openStationReserSetting(${labRoom.id},${currpage },${type})">工位预约设置</a>
		</li>
         <c:if test="${labRoom.CDictionaryByAllowSecurityAccess.CCategory=='c_active' && labRoom.CDictionaryByAllowSecurityAccess.CNumber=='1' && labRoom.labRoomReservation.toString() == '1'}">
             <li class="TabbedPanelsTab" tabindex="0">
                 <a href="javascript:void(0);" onclick="editLabRoomTrainingRest(${labRoom.id},${currpage },${type})">准入管理</a>
             </li>
         </c:if>
         <c:if test="${isOpen == 1}">
             <li class="TabbedPanelsTab selected" tabindex="0">
                 <a href="javascript:void(0);" onclick="editLabRoomOpenSettingRest(${labRoom.id},${currpage },${type})">开放设置</a>
             </li>
         </c:if>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomImageRest(${labRoom.id},${currpage },${type})">相关图片</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomVideoRest(${labRoom.id},${currpage },${type})">相关视频</a>
		</li>
		<li class="TabbedPanelsTab" tabindex="0">
		<a href="javascript:void(0);" onclick="editLabRoomDocumentRest(${labRoom.id},${currpage },${type})">相关文档</a>
		</li>
	</ul>
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">
				<div id="title">开放设置</div>
				<a class="btn btn-new"  onclick="closeMyWindow()" >返回</a>
				</div>
					<div class="new-classroom">
			<table id="radioTable">
                <div class="tab-pane fade" id="p10">
                    <div class="content-box">
                        <div class="title">
                            <div id="title">
                                实验室禁用时间设置
                            </div>
                            <c:if test="${flag==true}">
                                <a class="btn btn-new" onclick="newLimitTime() ">添加实验室禁用时间段</a>
                            </c:if>
                                <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?annexId=${annexId}&page=1">返回列表页面</a>--%>
                        </div>
                    </div>
                    <div class="edit-content">
                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th width="35%">学期</th>
                                <th width="15%">周次</th>
                                <th width="15%">节次</th>
                                <th width="15%">星期</th>
                                <th width="10%">类型</th>
                                <c:if test="${flag==true}">
                                    <th width="10%">操作</th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${labRoomLimitTimes}" var="admin">
                                <tr align="center">
                                    <td>${admin.schoolTerm.termName }</td>
                                    <td>${admin.startweek}-${admin.endweek}周</td>
                                    <td>${admin.startclass}-${admin.endclass}节</td>
                                    <td>周${admin.weekday}</td>
                                    <td>
                                        <c:if test="${admin.flag eq 0 }">手动添加</c:if>
                                        <c:if test="${admin.flag eq 1 }">排课生成</c:if>
                                    </td>
                                        <%--<td>--%>
                                    <c:if test="${flag==true}">
                                        <td><a onclick="return confirm('确定要删除吗？')" href="${pageContext.request.contextPath}/labRoomSetting/deleteLabRoomLimitTime?id=${admin.id}&&labRoomId=${labRoom.id}">删除</a></td>
                                    </c:if>
                                        <%--</td>--%>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade" id="p11">
                    <div class="content-box">
                        <div class="title">
                            <div id="title">实验室开放时间设置</div>
                            <c:if test="${flag==true}">
                                <a class="btn btn-new" id="show_open_time" onclick="editOpenTime() ">设置</a>
                            </c:if>
                                <%--<a class="btn btn-new" href="${pageContext.request.contextPath}/appointment/findLabRoomByLabAnnexId?annexId=${annexId}&page=1">返回列表页面</a>--%>
                        </div>
                    </div>
                    <div class="edit-content">
                        <table id="showOpenTime">
                            <tr>
                                <th>是否允许周末预约</th>
                                <td>
                                    <c:if test="${reservationSetupItem.openInweekend eq 1}">是
                                    </c:if>
                                    <c:if test="${reservationSetupItem.openInweekend eq 0}">否
                                    </c:if>
                                    <c:if test="${reservationSetupItem.openInweekend ne 0 && reservationSetupItem.openInweekend ne 1}">否
                                    </c:if>
                                </td>
                                <!--  <td style="width:15%"></th>
                                 <td style="width:15%"></td>
                                 <td style="width:15%"></th> -->
                            </tr>
                            <c:if test="${reservationSetupItem.openInweekend eq 1}">
                                <tr>
                                    <th>周末预约起止时间</th>
                                    <td>
                                            ${startWeekendHour}&nbsp;时${startWeekendMinute}&nbsp;分
                                        至
                                            ${endWeekendHour}&nbsp;时${endWeekendMinute}&nbsp;分
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <th>工作日预约起止时间</th>
                                <td>
                                        ${startHour}&nbsp;时${startMinute}&nbsp;分
                                    至
                                        ${endHour}&nbsp;时${endMinute}&nbsp;分
                                </td>
                            </tr>

                        </table>
                    </div>
                </div>
			
			</table>
			</div>
	  		<input type="hidden" id="labRoomId" value="${labRoomId }">
            <input type="hidden" id="pageContext" value="${pageContext.request.contextPath }">
                <input type="hidden" id="page" value="${currpage }">
	</div>
</div>
          <%--添加实验室禁用时间段--%>
          <div id="addLabRoomLimitTime" class="easyui-window" title="禁用时间段设置" align="left" title="" modal="true" maximizable="false" collapsible="false" closed="true" minimizable="false" style="width: 800px; height: 300px;">
              <div class="content-box">
                  <br>
                  <form:form name="labRoomLimitTimeForm" action="${pageContext.request.contextPath}/labRoomSetting/saveLabRoomLimitTime" method="post"   modelAttribute="labRoomLimitTime">
                      <input type="hidden" name="labId" id="labId" value="${labRoom.id}" >
                      <table class="tb" id="my_show1">

                          <tr>
                              <td>禁用时间</td>
                              <td>
                                  <input id="startTimeLimit" class="Wdate" type="text" name="startTime"
                                         onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:140px;"
                                         readonly />
                              </td>
                              <td>
                                  <input id="endTimeLimit" class="Wdate" type="text" name="endTime"
                                         onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:140px;"
                                         readonly />
                              </td>
                          </tr>

                          <tr>
                              <td>禁用日期</td>
                              <td>
                                  <input id="startDateLimit" class="Wdate" type="text" name="startDate"
                                         onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:140px;"
                                         readonly />
                              </td>
                              <td>
                                  <input id="endDateLimit" class="Wdate" type="text" name="endDate"
                                         onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:140px;"
                                         readonly />
                              </td>
                          </tr>

                          <tr>
                              <td>星期：</td>
                              <td colspan="2">
                                  <select class="chzn-select" data-placeholder="请选择星期" multiple="multiple" id="weekday1Limit" name="weekday1" style="width:300px">
                                      <option value ="0" >全部 </option>
                                      <c:forEach var="var"  begin="1" end="7" varStatus="status">
                                          <option value ="${var}" >周${var} </option>
                                      </c:forEach>
                                  </select>
                              </td>
                          </tr>
                      </table>
                      <br>
                      <%--<hr>--%>
                      <input type="hidden" id="adminType">
                      <input type="button" onclick="submitLimitTimeForm()" value="提交">
                  </form:form>
              </div>
          </div>

          <div id="editOpenTime" style="display: none">
              <form id="openTimeForm" name="openTimeForm"
                    action="${pageContext.request.contextPath}/labRoomSetting/saveLabRoomOpenTime"
                    method="post" modelAttribute="reservationSetupItem">
                  <input type="hidden" name="id" id="id" value="${reservationSetupItem.id}" >
                  <input type="hidden" name="roomId" id="roomId" value="${labRoom.id}" >
                  <div>
                      <table>
                          <!-- <span><font color="red">温馨提示：起止时间相关填写框，只允许输入数字。输入数字与时间的对应关系举例：8 为 8时整，8.25 为  8时15分， 8.5 为  8时30分， 8.75 为  8时45分</font></span> -->
                          <tr>
                              <th>是否允许周末预约</th>
                              <td>
                                  <c:if test="${reservationSetupItem.openInweekend eq 1}">
                                      <input type="radio" name="openInweekend" checked="true" value="1"/>是
                                      <input type="radio" name="openInweekend" value="0"/>否
                                  </c:if>
                                  <c:if test="${reservationSetupItem.openInweekend eq 0}">
                                      <input type="radio" name="openInweekend" value="1"/>是
                                      <input type="radio" name="openInweekend" checked="true" value="0"/>否
                                  </c:if>
                                  <c:if test="${reservationSetupItem.openInweekend ne 0 && reservationSetupItem.openInweekend ne 1}">
                                      <input type="radio" name="openInweekend" value="1"/>是
                                      <input type="radio" name="openInweekend" value="0"/>否
                                  </c:if>
                              </td>
                          </tr>
                          <tr id="weekendOpen">
                              <th>周末预约起止时间</th>
                              <td>
                                  <select class="chzn-select" id="startHourInweekend" name="startHourInweekend">
                                      <option value="0">请选择</option>
                                      <option value="0"
                                              <c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
                                      </option>
                                      <option value="1"
                                              <c:if test="${'1' eq startWeekendHour}">selected</c:if>>1
                                      </option>
                                      <option value="2"
                                              <c:if test="${'2' eq startWeekendHour}">selected</c:if>>2
                                      </option>
                                      <option value="3"
                                              <c:if test="${'3' eq startWeekendHour}">selected</c:if>>3
                                      </option>
                                      <option value="4"
                                              <c:if test="${'4' eq startWeekendHour}">selected</c:if>>4
                                      </option>
                                      <option value="5"
                                              <c:if test="${'5' eq startWeekendHour}">selected</c:if>>5
                                      </option>
                                      <option value="6"
                                              <c:if test="${'6' eq startWeekendHour}">selected</c:if>>6
                                      </option>
                                      <option value="7"
                                              <c:if test="${'7' eq startWeekendHour}">selected</c:if>>7
                                      </option>
                                      <option value="8"
                                              <c:if test="${'8' eq startWeekendHour}">selected</c:if>>8
                                      </option>
                                      <option value="9"
                                              <c:if test="${'9' eq startWeekendHour}">selected</c:if>>9
                                      </option>
                                      <option value="10"
                                              <c:if test="${'10' eq startWeekendHour}">selected</c:if>>10
                                      </option>
                                      <option value="11"
                                              <c:if test="${'11' eq startWeekendHour}">selected</c:if>>11
                                      </option>
                                      <option value="12"
                                              <c:if test="${'12' eq startWeekendHour}">selected</c:if>>12
                                      </option>
                                      <option value="13"
                                              <c:if test="${'13' eq startWeekendHour}">selected</c:if>>13
                                      </option>
                                      <option value="14"
                                              <c:if test="${'14' eq startWeekendHour}">selected</c:if>>14
                                      </option>
                                      <option value="15"
                                              <c:if test="${'15' eq startWeekendHour}">selected</c:if>>15
                                      </option>
                                      <option value="16"
                                              <c:if test="${'16' eq startWeekendHour}">selected</c:if>>16
                                      </option>
                                      <option value="17"
                                              <c:if test="${'17' eq startWeekendHour}">selected</c:if>>17
                                      </option>
                                      <option value="18"
                                              <c:if test="${'18' eq startWeekendHour}">selected</c:if>>18
                                      </option>
                                      <option value="19"
                                              <c:if test="${'19' eq startWeekendHour}">selected</c:if>>19
                                      </option>
                                      <option value="20"
                                              <c:if test="${'20' eq startWeekendHour}">selected</c:if>>20
                                      </option>
                                      <option value="21"
                                              <c:if test="${'21' eq startWeekendHour}">selected</c:if>>21
                                      </option>
                                      <option value="22"
                                              <c:if test="${'22' eq startWeekendHour}">selected</c:if>>22
                                      </option>
                                      <option value="23"
                                              <c:if test="${'23' eq startWeekendHour}">selected</c:if>>23
                                      </option>
                                  </select>
                                  <span>时</span>
                                  <select class="chzn-select" id="startMinuteInweekend"
                                          name="startMinuteInweekend">
                                      <option value="00">请选择</option>
                                      <option value="00"
                                              <c:if test="${'0' eq startWeekendMinute}">selected</c:if>>00
                                      </option>
                                      <option value="15"
                                              <c:if test="${'15' eq startWeekendMinute}">selected</c:if>>15
                                      </option>
                                      <option value="30"
                                              <c:if test="${'30' eq startWeekendMinute}">selected</c:if>>30
                                      </option>
                                      <option value="45"
                                              <c:if test="${'45' eq startWeekendMinute}">selected</c:if>>45
                                      </option>
                                  </select>
                                  <span>分</span>
                                  至
                                  <select class="chzn-select" id="endHourInweekend" name="endHourInweekend">
                                      <option value="0">请选择</option>
                                      <option value="0"
                                              <c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
                                      </option>
                                      <option value="1" <c:if test="${'1' eq endWeekendHour}">selected</c:if>>
                                          1
                                      </option>
                                      <option value="2" <c:if test="${'2' eq endWeekendHour}">selected</c:if>>
                                          2
                                      </option>
                                      <option value="3" <c:if test="${'3' eq endWeekendHour}">selected</c:if>>
                                          3
                                      </option>
                                      <option value="4" <c:if test="${'4' eq endWeekendHour}">selected</c:if>>
                                          4
                                      </option>
                                      <option value="5" <c:if test="${'5' eq endWeekendHour}">selected</c:if>>
                                          5
                                      </option>
                                      <option value="6" <c:if test="${'6' eq endWeekendHour}">selected</c:if>>
                                          6
                                      </option>
                                      <option value="7" <c:if test="${'7' eq endWeekendHour}">selected</c:if>>
                                          7
                                      </option>
                                      <option value="8" <c:if test="${'8' eq endWeekendHour}">selected</c:if>>
                                          8
                                      </option>
                                      <option value="9" <c:if test="${'9' eq endWeekendHour}">selected</c:if>>
                                          9
                                      </option>
                                      <option value="10"
                                              <c:if test="${'10' eq endWeekendHour}">selected</c:if>>10
                                      </option>
                                      <option value="11"
                                              <c:if test="${'11' eq endWeekendHour}">selected</c:if>>11
                                      </option>
                                      <option value="12"
                                              <c:if test="${'12' eq endWeekendHour}">selected</c:if>>12
                                      </option>
                                      <option value="13"
                                              <c:if test="${'13' eq endWeekendHour}">selected</c:if>>13
                                      </option>
                                      <option value="14"
                                              <c:if test="${'14' eq endWeekendHour}">selected</c:if>>14
                                      </option>
                                      <option value="15"
                                              <c:if test="${'15' eq endWeekendHour}">selected</c:if>>15
                                      </option>
                                      <option value="16"
                                              <c:if test="${'16' eq endWeekendHour}">selected</c:if>>16
                                      </option>
                                      <option value="17"
                                              <c:if test="${'17' eq endWeekendHour}">selected</c:if>>17
                                      </option>
                                      <option value="18"
                                              <c:if test="${'18' eq endWeekendHour}">selected</c:if>>18
                                      </option>
                                      <option value="19"
                                              <c:if test="${'19' eq endWeekendHour}">selected</c:if>>19
                                      </option>
                                      <option value="20"
                                              <c:if test="${'20' eq endWeekendHour}">selected</c:if>>20
                                      </option>
                                      <option value="21"
                                              <c:if test="${'21' eq endWeekendHour}">selected</c:if>>21
                                      </option>
                                      <option value="22"
                                              <c:if test="${'22' eq endWeekendHour}">selected</c:if>>22
                                      </option>
                                      <option value="23"
                                              <c:if test="${'23' eq endWeekendHour}">selected</c:if>>23
                                      </option>
                                  </select>
                                  <span>时</span>
                                  <select class="chzn-select" id="endMinuteInweekend"
                                          name="endMinuteInweekend">
                                      <option value="00">请选择</option>
                                      <option value="00"
                                              <c:if test="${'0' eq endWeekendMinute}">selected</c:if>>00
                                      </option>
                                      <option value="15"
                                              <c:if test="${'15' eq endWeekendMinute}">selected</c:if>>15
                                      </option>
                                      <option value="30"
                                              <c:if test="${'30' eq endWeekendMinute}">selected</c:if>>30
                                      </option>
                                      <option value="45"
                                              <c:if test="${'45' eq endWeekendMinute}">selected</c:if>>45
                                      </option>
                                  </select>
                                  <span>分</span>
                              </td>
                          </tr>
                          <tr>
                              <th>工作日预约起止时间</th>
                              <td>
                                  <select class="chzn-select" id='startHour' name='startHour'>
                                      <option value="0">请选择</option>
                                      <option value="0"
                                              <c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
                                      </option>
                                      <option value="1" <c:if test="${'1' eq startHour}">selected</c:if>>1
                                      </option>
                                      <option value="2" <c:if test="${'2' eq startHour}">selected</c:if>>2
                                      </option>
                                      <option value="3" <c:if test="${'3' eq startHour}">selected</c:if>>3
                                      </option>
                                      <option value="4" <c:if test="${'4' eq startHour}">selected</c:if>>4
                                      </option>
                                      <option value="5" <c:if test="${'5' eq startHour}">selected</c:if>>5
                                      </option>
                                      <option value="6" <c:if test="${'6' eq startHour}">selected</c:if>>6
                                      </option>
                                      <option value="7" <c:if test="${'7' eq startHour}">selected</c:if>>7
                                      </option>
                                      <option value="8" <c:if test="${'8' eq startHour}">selected</c:if>>8
                                      </option>
                                      <option value="9" <c:if test="${'9' eq startHour}">selected</c:if>>9
                                      </option>
                                      <option value="10" <c:if test="${'10' eq startHour}">selected</c:if>>
                                          10
                                      </option>
                                      <option value="11" <c:if test="${'11' eq startHour}">selected</c:if>>
                                          11
                                      </option>
                                      <option value="12" <c:if test="${'12' eq startHour}">selected</c:if>>
                                          12
                                      </option>
                                      <option value="13" <c:if test="${'13' eq startHour}">selected</c:if>>
                                          13
                                      </option>
                                      <option value="14" <c:if test="${'14' eq startHour}">selected</c:if>>
                                          14
                                      </option>
                                      <option value="15" <c:if test="${'15' eq startHour}">selected</c:if>>
                                          15
                                      </option>
                                      <option value="16" <c:if test="${'16' eq startHour}">selected</c:if>>
                                          16
                                      </option>
                                      <option value="17" <c:if test="${'17' eq startHour}">selected</c:if>>
                                          17
                                      </option>
                                      <option value="18" <c:if test="${'18' eq startHour}">selected</c:if>>
                                          18
                                      </option>
                                      <option value="19" <c:if test="${'19' eq startHour}">selected</c:if>>
                                          19
                                      </option>
                                      <option value="20" <c:if test="${'20' eq startHour}">selected</c:if>>
                                          20
                                      </option>
                                      <option value="21" <c:if test="${'21' eq startHour}">selected</c:if>>
                                          21
                                      </option>
                                      <option value="22" <c:if test="${'22' eq startHour}">selected</c:if>>
                                          22
                                      </option>
                                      <option value="23" <c:if test="${'23' eq startHour}">selected</c:if>>
                                          23
                                      </option>
                                  </select>
                                  <span>时</span>
                                  <select class="chzn-select" id='startMinute' name='startMinute'>
                                      <option value="00">请选择</option>
                                      <option value="00" <c:if test="${'0' eq startMinute}">selected</c:if>>
                                          00
                                      </option>
                                      <option value="15" <c:if test="${'15' eq startMinute}">selected</c:if>>
                                          15
                                      </option>
                                      <option value="30" <c:if test="${'30' eq startMinute}">selected</c:if>>
                                          30
                                      </option>
                                      <option value="45" <c:if test="${'45' eq startMinute}">selected</c:if>>
                                          45
                                      </option>
                                  </select>
                                  <span>分</span>
                                  至
                                  <select class="chzn-select" id='endHour' name='endHour' value="${endHour}">

                                      <option value="0">请选择</option>
                                      <option value="0"
                                              <c:if test="${'0' eq startWeekendHour}">selected</c:if>>0
                                      </option>
                                      <option value="1" <c:if test="${'1' eq endHour}">selected</c:if>>1
                                      </option>
                                      <option value="2" <c:if test="${'2' eq endHour}">selected</c:if>>2
                                      </option>
                                      <option value="3" <c:if test="${'3' eq endHour}">selected</c:if>>3
                                      </option>
                                      <option value="4" <c:if test="${'4' eq endHour}">selected</c:if>>4
                                      </option>
                                      <option value="5" <c:if test="${'5' eq endHour}">selected</c:if>>5
                                      </option>
                                      <option value="6" <c:if test="${'6' eq endHour}">selected</c:if>>6
                                      </option>
                                      <option value="7" <c:if test="${'7' eq endHour}">selected</c:if>>7
                                      </option>
                                      <option value="8" <c:if test="${'8' eq endHour}">selected</c:if>>8
                                      </option>
                                      <option value="9" <c:if test="${'9' eq endHour}">selected</c:if>>9
                                      </option>
                                      <option value="10" <c:if test="${'10' eq endHour}">selected</c:if>>10
                                      </option>
                                      <option value="11" <c:if test="${'11' eq endHour}">selected</c:if>>11
                                      </option>
                                      <option value="12" <c:if test="${'12' eq endHour}">selected</c:if>>12
                                      </option>
                                      <option value="13" <c:if test="${'13' eq endHour}">selected</c:if>>13
                                      </option>
                                      <option value="14" <c:if test="${'14' eq endHour}">selected</c:if>>14
                                      </option>
                                      <option value="15" <c:if test="${'15' eq endHour}">selected</c:if>>15
                                      </option>
                                      <option value="16" <c:if test="${'16' eq endHour}">selected</c:if>>16
                                      </option>
                                      <option value="17" <c:if test="${'17' eq endHour}">selected</c:if>>17
                                      </option>
                                      <option value="18" <c:if test="${'18' eq endHour}">selected</c:if>>18
                                      </option>
                                      <option value="19" <c:if test="${'19' eq endHour}">selected</c:if>>19
                                      </option>
                                      <option value="20" <c:if test="${'20' eq endHour}">selected</c:if>>20
                                      </option>
                                      <option value="21" <c:if test="${'21' eq endHour}">selected</c:if>>21
                                      </option>
                                      <option value="22" <c:if test="${'22' eq endHour}">selected</c:if>>22
                                      </option>
                                      <option value="23" <c:if test="${'23' eq endHour}">selected</c:if>>23
                                      </option>
                                  </select>
                                  <span>时</span>
                                  <select class="chzn-select" id="endMinute" name="endMinute">
                                      <option value="00">请选择</option>
                                      <option value="00" <c:if test="${'0' eq endMinute}">selected</c:if>>00
                                      </option>
                                      <option value="15" <c:if test="${'15' eq endMinute}">selected</c:if>>
                                          15
                                      </option>
                                      <option value="30" <c:if test="${'30' eq endMinute}">selected</c:if>>
                                          30
                                      </option>
                                      <option value="45" <c:if test="${'45' eq endMinute}">selected</c:if>>
                                          45
                                      </option>
                                  </select>
                                  <span>分</span>
                              </td>
                          </tr>

                      </table>

                  </div>
                  <div class="editOpenTime-action" style="overflow:hidden">
                      <input style="    float: right;  margin: 0 10px 10px 0;" class="btn" id="save1"
                             type="button" value="保存" onclick="saveLabRoomOpenTime()"/>
                  </div>
              </form>
          </div>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
		charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chzn-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
</body>
</html>
