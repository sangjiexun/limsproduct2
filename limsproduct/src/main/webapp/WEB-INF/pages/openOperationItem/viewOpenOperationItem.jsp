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
            var url = "${pageContext.request.contextPath}/openOperationItem/listOpenOperationItem?currpage=1&status=" + ${status} + "&orderBy=9";
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
                                <th>实验项目开发人：</th><td>${operationItem.userByLpCreateUser.cname}</td>
                                <th>职称：</th><td>${operationItem.CDictionaryByTitle.CName}</td>
                                <th>院系：</th><td>${operationItem.userByLpCreateUser.schoolAcademy.academyName}</td>
                                <th>实验者人数：</th><td>${operationItem.lpStudentNumber}</td>
                            </tr>
                            <tr>
                                <th>实验最小单位：</th><td>${operationItem.CDictionaryByMinUnit.CName}</td>
                                <th>实验组数：</th><td>${operationItem.lpSetNumber}</td>
                                <th>每组人数：</th><td>${operationItem.lpStudentNumberGroup}</td>
                                <th>实验类型：</th><td>${operationItem.CDictionaryByLpCategoryApp.CName}</td>
                            </tr>
                            <tr>
                                <th>实验性质：</th><td>${operationItem.CDictionaryByLpCategoryNature.CName}</td>
                                <th>开放教师：</th><td>
                                <c:forEach items="${operationItem.openTeachers}" var="teacher" varStatus="i">
                                    ${teacher.user.cname}<c:if test="${!i.last}">,</c:if>
                                </c:forEach></td>
                                <th>相关实验室人员：</th><td>
                                <c:forEach items="${operationItem.labUsers}" var="labUser" varStatus="i">
                                    ${labUser.user.cname}<c:if test="${!i.last}">,</c:if>
                                </c:forEach></td>
                                <th>变动状态：</th><td>${operationItem.CDictionaryByLpStatusChange.CName}</td>
<%--                                <th>开放实验：</th><td>${operationItem.CDictionaryByLpCategoryPublic.CName}</td>--%>
                            </tr>
                            <tr>
                                <th>开放学院：</th><td>
                                <c:forEach items="${academyList}" var="academy" varStatus="i">
                                    ${academy.academyName}<c:if test="${!i.last}">,</c:if>
                                </c:forEach></td>
                                <th>开放专业：</th><td>
<%--                                    <c:if test="${operationItem.schoolMajor.majorName ne null}">--%>
<%--                                        ${operationItem.schoolMajor.majorName}[${operationItem.schoolMajor.majorNumber}]--%>
<%--                                    </c:if>--%>
                                <c:forEach items="${majorList}" var="major" varStatus="i">
                                    ${major.majorName}<c:if test="${!i.last}">,</c:if>
                                </c:forEach></td>
                                <th>实验隶属大纲：</th><td>${operationItem.operationOutline.labOutlineName}</td>
                                <th>实验隶属课程：</th><td>
                                    <c:if test="${operationItem.schoolCourseInfo.courseName ne null}">
                                        ${operationItem.schoolCourseInfo.courseName}[${operationItem.schoolCourseInfo.courseNumber}]
                                    </c:if></td>
                            </tr>
                            <tr>
                                <th>开放年级：</th><td>${operationItem.CDictionaryByOpenGrade.CName}</td>
                                <th>开放学期：</th><td>${operationItem.CDictionaryByOpenTerm.CName}</td>
                                <th>实验目标、要求：</th><td>${operationItem.itemAim}</td>
                                <th>实验原理：</th><td>${operationItem.itemTheory}</td>
                            </tr>
                            <tr>
                                <th>实验方法：</th><td>${operationItem.itemMethod}</td>
                                <th>实验结果及分析：</th><td>${operationItem.itemResult}</td>
                                <th>注意事项：</th><td>${operationItem.itemAttention}</td>
                                <th>实验结果形式：</th><td>${operationItem.CDictionaryByItemResultType.CName}</td>
                            </tr>
                            <tr>
                                <%--<th>指定审核人：</th><td>${operationItem.userByLpCheckUser.cname}[${operationItem.userByLpCheckUser.username}]</td>--%>
                                <th>项目状态：</th><td>${operationItem.CDictionaryByLpStatusCheck.CName}</td>
                                <th>实验预算：</th><td>${operationItem.itemBudget}</td>
                                <th>思考题：</th><td> <c:if test="${operationItem.itemQuestionDocument != null}">
                                    <a onclick="javascript:void(0)" href="${pageContext.request.contextPath}/${operationItem.itemQuestionDocument.documentUrl}">
                                            ${operationItem.itemQuestionDocument.documentName} 点击下载
                                    </a>
                                </c:if></td>
                                <th>计划周次：</th><td>${operationItem.planWeek}</td>
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
                        <div id="title">实验物资</div>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>物资名称</th>
                            <th>物资类型</th>
                            <th>型号/规格</th>
                            <th>单位</th>
                            <th>数量</th>
                            <%--                                                        <th>金额（元）</th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${operationItem.itemAssets}" var="curr" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${curr.asset.chName}</td>
                                <td>${materialKindMap[curr.asset.category]}</td>
                                <td>${curr.asset.specifications}</td>
                                <td>${curr.asset.unit}</td>
                                <td>${curr.amount}</td>
                                    <%--                                                            <td>${curr.lpmrAmount}</td>--%>
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

<script type="text/javascript">

    function getSimpleSpec(unit) {
        unit = unit.replace(/[^a-z^A-Z]/g,'');
        return unit;
    }

    for (var i = 1; i <= ${fn:length(operationItem.itemAssets)}; i++) {
        var old = $("#unit" + i).html();
        $("#unit" + i).html(getSimpleSpec(old));
    }
</script>
</body>
</html>
