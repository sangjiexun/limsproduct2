<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <script>
        //查看
        function examine(igId) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ajaxGetRecord",
                data: {id: igId},
                dataType: "json",
                success: function (data) {
                    $("#riTime").val(decodeURI(data.riTime));
                    $("#riCenterName").val(decodeURI(data.riCenterName));
                    $("#riRoomName").val(decodeURI(data.riRoomName));
                    $("#riCnam").val(decodeURI(data.riCnam));
                    $("#riCheckContent").val(decodeURI(data.riCheckContent));
                    $("#riSafetyManagement").val(decodeURI(data.riSafetyManagement));
                    $("#url").attr("src", "${pageContext.request.contextPath}" + decodeURI(data.url));
                    $("#doc_name").text(decodeURI(data.doc_name));
                    $("#doc_name").attr("href", "${pageContext.request.contextPath}" + decodeURI(data.url));
                    $("#auditDiv").hide();
                    $("#examineDiv").show();
                },
                error: function () {
                    alert("查询附件图片出错！");
                }
            });
        }

        //关闭查看或者审核
        function closeDiv() {
            $("#examineDiv").hide();
            $("#auditDiv").hide();
        }

        //判断是否审核
        function sunCk() {
            if (document.getElementById("ck1").checked || document.getElementById("ck2").checked) {
                return true;
            } else {
                alert("还未审核！")
                return false;
            }

        }

        //跳转
        function targetUrl(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

    </script>

</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
            <li class="end"><a href="javascript:void(0)"><c:if test="${types == 1}"><spring:message code="left.student.inspection"/></c:if> <c:if
                    test="${types == 2}"><spring:message code="left.supervision.inspection"/></c:if></a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><c:if test="${types == 1}"><spring:message code="left.student.inspection"/></c:if> <c:if
                        test="${types == 2}"><spring:message code="left.supervision.inspection"/></c:if></a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage=1&types=${types}"
                                   method="post" modelAttribute="routineInspection">
                            <ul>
                                <li>
                                    实验室:
                                    <select class="chzn-select" path="labRoom.id" id="labRoomId" name="labRoom.id"
                                            style="width:150px">
                                        <option value="999999" selected>实验室</option>
                                        <c:forEach items="${labRooms}" var="current">
                                            <option value="${current.id}">${current.labRoomName} </option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    学期:
                                    <select class="chzn-select" path="schoolTerm.id" id="schoolTerm.id"
                                            name="schoolTerm.id" style="width:180px">
                                        <option value="999999" selected>学期</option>
                                        <c:forEach items="${terms}" var="current">
                                            <option value="${current.id}">${current.termName} </option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                </li>
                            </ul>
                        </form:form>
                    </div>
                </div>
                <div class="content-box">
                    <table class="tb" id="my_show">
                        <thead style="center-content">
                        <tr>
                            <th>学期</th>
                            <th>学院</th>
                            <th>实验室</th>
                            <th>检查人</th>
                            <th>检查时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${RoutineInspection}" var="curr">
                            <tr>
                                <td>${curr.schoolTerm.termName}${curr.week}</td>
                                <td>${curr.labRoom.labAnnex.labCenter.schoolAcademy.academyName}</td>
                                <td>${curr.labRoom.labRoomName}</td>
                                <td><c:if test="${sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'}">保密</c:if>
                                    <c:if test="${sessionScope.selected_role ne 'ROLE_EXCENTERDIRECTOR'}">${curr.user.cname}</c:if></td>
                                <td><fmt:formatDate value="${curr.creationDate.time}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                    <%--<input type="button" value="查看" onclick="examine(${curr.id});"/>--%>
                                    <a href="javascript:void(0)" onclick="examine(${curr.id});">查看</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!-- 分页开始 -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage==1&types=${types}')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage==${pageModel.previousPage}&types=${types}')"
                           target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage==${pageModel.currpage}&types=${types}">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage==${j.index}&types=${types}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage==${pageModel.nextPage}&types=${types}')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/casualInspection/facilityCasual?currpage==${pageModel.lastPage}&types=${types}')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页结束 -->
                </div>
                <br/>
                <div id="examineDiv" style="display: none;width:97%;height: 400px;padding: 20px;border:1px solid #000;">
                    <table border="0" cellpadding="5" cellspacing="100" height="30" width="100%">
                        <tbody>
                        <tr>
                            <td style="white-space:nowrap">
                                时间：<input style="border:0px" type="text" readonly="true" size="30" id="riTime"/>
                            </td>
                            <td style="white-space:nowrap">
                                学院：<input style="border:0px" type="text" readonly="true" size="30" id="riCenterName"/>

                            </td>
                            <td style="white-space:nowrap">
                                实验室：<input style="border:0px" type="text" readonly="true" size="30" id="riRoomName"/>
                            </td>
                            <td style="white-space:nowrap">
                                上报人：<input style="border:0px" type="text" readonly="true" size="30" id="riCnam"/>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <br/>
                                <div style="float:left">日常管理情况：</div>
                                <br/>
                                <div style="float:left;">
                                    <textarea readonly="ture" rows="20" cols="60" id="riCheckContent"></textarea>
                                </div>
                            </td>
                            <td colspan="3">
                                <br/>
                                <div style="float:left"> 安全管理情况：</div>
                                <br/>
                                <div style=" float:left">
                                    <textarea id="riSafetyManagement" rows="20" cols="60" readonly="ture"></textarea>
                                </div>
                            </td>
                            <c:if test="${types == 2}">
                                <td colspan="3">
                                    <br>
                                    <img id="url" alt="" src="" width="200px" height="300px"/>
                                    <a href="${pageContext.request.contextPath}" id="doc_name"></a>
                                </td>
                            </c:if>
                        </tr>
                        <tr>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <br>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                                <input type="button" class="btn-return" value="关闭" onclick="closeDiv()"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div>
                    </div>
                </div>

                <div id="auditDiv" style="display: none;width:97%;height: 400px;padding: 20px;border:1px solid #000;">
                    <table border="0" cellpadding="5" cellspacing="100" height="30" width="100%">
                        <form:form name="subForm"
                                   action="${pageContext.request.contextPath}/casualInspection/auditFacility"
                                   method="post" modelAttribute="routineInspection" onsubmit="return sunCk();">
                        <tbody>
                        <tr>
                            <td style="white-space:nowrap">
                                <input id="riIdSub" type="hidden" path="id" name="id"/>
                                <input type="hidden" name="types" value="${types}"/>
                                时间：<input style="border:0px" type="text" readonly="true" size="30" id="riTimeSub"/>
                            </td>
                            <td style="white-space:nowrap">
                                学院：<input style="border:0px" type="text" readonly="true" size="30"
                                          id="riCenterNameSub"/>
                            </td>
                            <td style="white-space:nowrap">
                                实验室：<input style="border:0px" type="text" readonly="true" size="30" id="riRoomNameSub"/>
                            </td>
                            <td style="white-space:nowrap">
                                上报人：<input style="border:0px" type="text" readonly="true" size="30" id="riCnamSub"/>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <br/>
                                <div style="float:left"> 日常管理情况和安全管理情况：</div>
                                <br/>
                                <div style="float:left;">
                                    <textarea rows="20" cols="80" path="checkContent" name="checkContent"
                                              id="riCheckContentSub"></textarea></div>
                            </td>
                            <td colspan="3">
                                <br/>
                                <div style="float:left"> 安全管理情况：</div>
                                <br/>
                                <div style=" float:left">
                                    <textarea id="riCheckSafetyManagement" rows="20" cols="60" path="safetyManagement"
                                              name="safetyManagement"></textarea>
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <br>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>

                                    <%--设备科和超级管理员可以提交   --%>
                                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_ASSETMANAGEMENT">
                                    <input type="submit" value="提交"/>
                                </sec:authorize>

                            </td>
                        </tr>
                        </form:form>
                        </tbody>
                    </table>
                    <div>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->

</body>
</html>
