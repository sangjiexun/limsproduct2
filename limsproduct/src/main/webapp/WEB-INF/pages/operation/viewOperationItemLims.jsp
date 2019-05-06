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
                window.location.href="${pageContext.request.contextPath}/operation/auditOperationItem?operationItemId=${operationItem.id}&result=1";
            }
        }
        //审核拒绝
        function checkFail(){
            if(confirm('拒绝后不能恢复，确定审核拒绝？'))
            {
                window.location.href="${pageContext.request.contextPath}/operation/auditOperationItem?operationItemId=${operationItem.id}&result=0"
            }
        }
    </script>

    <script type="text/javascript">
        //返回列表页面
        function listOperationItemLims(){
            var url = "${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=" + ${status} + "&orderBy=9";
            window.location.href=url;
        }
    </script>

    <style>
        select{
            width:162px;
            margin-left:10px;
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

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="edit-content-box">
                    <div class="title">
                        <div id="title">实验项目详情</div>
                        <%--<a class="btn btn-new" href="javascript:void(0);" onclick="listOperationItemLims();">返回</a>--%>
                    </div>
                    <div class="edit-content">
                        <table>
                            <tr>
                                <th>实验名称：</th><td>${operationItem.lpName}</td>
                                <th>所属学期：</th><td>${operationItem.schoolTerm.termName}</td>
                                <th>所属学科：</th><td>${operationItem.systemSubject12.SName}[${operationItem.systemSubject12.SNumber}]</td>
                                <th>所属<spring:message code="all.trainingRoom.labroom"/>：</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${operationItem.labRoom ne null}">${operationItem.labRoom.labRoomName}</c:when>
                                        <c:otherwise>
                                            <c:if test="${operationItem.labRooms ne null}">
                                                <c:forEach items="${operationItem.labRooms}" var="lab">${lab.labRoomName}
                                                    <br></c:forEach>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <th>实验学时数：</th><td>${operationItem.lpDepartmentHours}</td>
                                <th>实验总学时：</th><td>${operationItem.lpDepartmentHoursTotal}</td>
                                <th>课程总学时：</th><td>${operationItem.lpCourseHoursTotal}</td>
                                <th>实验者人数：</th><td>${operationItem.lpStudentNumber}</td>
                            </tr>
                            <tr>
                                <th>实验组数：</th><td>${operationItem.lpSetNumber}</td>
                                <th>每组人数：</th><td>${operationItem.lpStudentNumberGroup}</td>
                                <th>实验类别：</th><td>${operationItem.CDictionaryByLpCategoryMain.CName}</td>
                                <th>实验类型：</th><td>${operationItem.CDictionaryByLpCategoryApp.CName}</td>
                            </tr>
                            <tr>
                                <th>实验性质：</th><td>${operationItem.CDictionaryByLpCategoryNature.CName}</td>
                                <th>实验者类型：</th><td>${operationItem.CDictionaryByLpCategoryStudent.CName}</td>
                                <th>变动状态：</th><td>${operationItem.CDictionaryByLpStatusChange.CName}</td>
                                <th>开放实验：</th><td>${operationItem.CDictionaryByLpCategoryPublic.CName}</td>
                            </tr>
                            <tr>
                                <th>获奖等级：</th><td>${operationItem.CDictionaryByLpCategoryRewardLevel.CName}</td>
                                <th>实验要求：</th><td>${operationItem.CDictionaryByLpCategoryRequire.CName}</td>
                                <th>所属专业：</th><td>
                                    <c:if test="${operationItem.schoolMajor.majorName ne null}">
                                        ${operationItem.schoolMajor.majorName}[${operationItem.schoolMajor.majorNumber}]
                                    </c:if>
                                </td>
                                <th>所属课程：</th><td>
                                    <c:if test="${operationItem.schoolCourseInfo.courseName ne null}">
                                        ${operationItem.schoolCourseInfo.courseName}[${operationItem.schoolCourseInfo.courseNumber}]
                                    </c:if></td>
                            </tr>
                            <tr>
                                <th>主讲教师：</th><td>
                                    <c:if test="${operationItem.userByLpTeacherSpeakerId.cname ne null}">
                                        ${operationItem.userByLpTeacherSpeakerId.cname}[${operationItem.userByLpTeacherSpeakerId.username}]
                                    </c:if></td>
                                <th>辅导教师：</th><td>
                                    <c:if test="${operationItem.userByLpTeacherAssistantId.cname ne null}">
                                        ${operationItem.userByLpTeacherAssistantId.cname}[${operationItem.userByLpTeacherAssistantId.username}]
                                    </c:if></td>
                                <th>指导书名称：</th><td>${operationItem.lpGuideBookTitle}</td>
                                <th>编者：</th><td>${operationItem.lpGuideBookAuthor}</td>
                            </tr>
                            <tr>
                                <th>考核方法：</th><td>${operationItem.lpAssessmentMethods}</td>
                                <th>计划开课周次：</th><td>${operationItem.planWeek}</td>
                                <th>项目简介：</th><td>${operationItem.lpIntroduction}</td>
                                <th>课前准备：</th><td>${operationItem.lpPreparation}</td>
                            </tr>
                            <tr>
                                <%--<th>指定审核人：</th><td>${operationItem.userByLpCheckUser.cname}[${operationItem.userByLpCheckUser.username}]</td>--%>
                                <th>项目状态：</th><td>${operationItem.CDictionaryByLpStatusCheck.CName}</td>
                            </tr>
                            <c:if test="${operationItem.CDictionaryByLpStatusCheck.id==toCheck.id
                                && canAudit}"><!-- && operationItem.userByLpCreateUser.username!=currUser.username -->
                                <tr>
                                    <td><input type="button" onclick="checkPass();" value="审核通过" /></td>
                                    <td><input type="button" onclick="checkFail();" value="审核拒绝" /></td>
                                </tr>
                            </c:if>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <!-- 材料使用 -->
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">材料使用</div>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>材料名称</th>
                            <th>材料类型</th>
                            <th>型号/规格</th>
                            <th>单位</th>
                            <th>数量</th>
                            <th>金额（元）</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listItemMaterialRecord}" var="curr" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${curr.lpmrName}</td>
                                <td>${curr.CDictionary.CName}</td>
                                <td>${curr.lpmrModel}</td>
                                <td>${curr.lpmrUnit}</td>
                                <td>${curr.lpmrQuantity}</td>
                                <td>${curr.lpmrAmount}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- 仪器设备 -->
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">仪器设备</div>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>类型</th>
                            <th>设备编号</th>
                            <th>设备名称</th>
                            <th>规格</th>
                            <th>型号</th>
                            <th>单价</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listItemDevice}" var="curr" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${curr.CDictionary.CName}</td>
                                <td>${curr.labRoomDevice.schoolDevice.deviceNumber}</td>
                                <td>${curr.labRoomDevice.schoolDevice.deviceName}</td>
                                <td>${curr.labRoomDevice.schoolDevice.deviceFormat}</td>
                                <td>${curr.labRoomDevice.schoolDevice.devicePattern}</td>
                                <td>${curr.labRoomDevice.schoolDevice.devicePrice}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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
