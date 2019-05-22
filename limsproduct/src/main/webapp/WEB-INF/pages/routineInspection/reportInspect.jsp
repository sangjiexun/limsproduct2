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
        //新建-提交
        function subNew() {
            if ($("#newCheckContent").val() == "" || $("#newCheckContent").val() == null) {
                alert("日常管理情况未填写！")
            } else {
                if ($("#newSafetyManagement").val() == "" || $("#newSafetyManagement").val() == null) {
                    alert("安全管理情况未填写！")
                } else {
                    document.queryForm2.action = "${pageContext.request.contextPath}/reportRoutineInspection/newReportRoutineInspection?genre=1 ";
                    document.queryForm2.submit();
                }
            }
        }

        //新建-保存
        function save() {
            if ($("#newCheckContent").val() == "" || $("#newCheckContent").val() == null) {
                alert("检查内容未填写！")
            } else {
                if ($("#newSafetyManagement").val() == "" || $("#newSafetyManagement").val() == null) {
                    alert("安全管理情况未填写！")
                } else {
                    document.queryForm2.action = "${pageContext.request.contextPath}/reportRoutineInspection/newReportRoutineInspection?genre=2 ";
                    document.queryForm2.submit();
                }
            }
        }

        //新建
        function newRoutineInspection() {
            $("#examineDiv").hide();
            $("#subDiv").hide();
            $("#newDiv").show();
        }

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
                    $("#riLabCenterName").val(decodeURI(data.riLabCenterName));
                    $("#url").attr("src", "${pageContext.request.contextPath}" + decodeURI(data.url))
                    $("#subDiv").hide();
                    $("#newDiv").hide();
                    $("#examineDiv").show();
                },
                error: function () {
                    alert("查询附件信息出错！");
                }
            });
        }

        //关闭查看或者提交
        function closeDiv() {
            $("#examineDiv").hide();
            $("#newDiv").hide();
            $("#subDiv").hide();
        }

        //提交
        function sub(igId) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ajaxGetRecord",
                data: {id: igId},
                dataType: "json",
                success: function (data) {
                    $("#riIdSub").val(igId);
                    $("#riTimeSub").val(decodeURI(data.riTime));
                    $("#riCenterNameSub").val(decodeURI(data.riCenterName));
                    $("#riRoomNameSub").val(decodeURI(data.riRoomName));
                    $("#riCnamSub").val(decodeURI(data.riCnam));
                    $("#riCheckContentSub").val(decodeURI(data.riCheckContent));
                    $("#riCheckSafetyManagement").val(decodeURI(data.riSafetyManagement));
                    $("#riLabCenterNameSub").val(decodeURI(data.riLabCenterName));
                    $("#urlSub").attr("src", "${pageContext.request.contextPath}" + decodeURI(data.url))
                    $("#newDiv").hide();
                    $("#examineDiv").hide();
                    $("#subDiv").show();
                },
                error: function () {
                    alert("查询附件信息出错！");
                }
            });
        }

        //导出word文档
        function exportWord(id) {
            window.location.href = "${pageContext.request.contextPath}/reportRoutineInspection/exportReportInspect?id=" + id;
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
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.routine.report"/></a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">常规检查查看</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=1 "
                                   method="post" modelAttribute="routineInspection">
                            <ul>
                                <%--<li>--%>
                                <li>
                                    实验中心:
                                    <form:select class="chzn-select" path="labCenter.id" id="labCenterId"
                                                 name="labCenter.id" style="width:150px">
                                        <form:option value="">实验中心</form:option>
                                        <c:forEach items="${labCenters}" var="current">
                                            <form:option value="${current.id}">${current.centerName} </form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>
                                <li>
                                    当前学期是：${termName}
                                </li>
                                <li>
                                    周次:
                                    <form:select class="chzn-select" path="week" id="week" name="week"
                                                 style="width:90px">
                                        <form:option value="">周次</form:option>
                                        <c:forEach items="${schoolWeeks}" var="current">
                                            <form:option value="${current.week}">第${current.week}周</form:option>
                                        </c:forEach>
                                    </form:select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <input type="button" value="新建" onclick="newRoutineInspection();"/>
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
                            <th>实验中心</th>
                            <th>上报人</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${RoutineInspection}" var="curr">
                            <tr>
                                <td>${curr.schoolTerm.termName} 第${curr.week}周</td>
                                <td>${curr.labCenter.schoolAcademy.academyName}</td>
                                <td>
                                        <c:if test="${curr.labCenter != null}">${curr.labCenter.centerName}</c:if>
                                <td>${curr.user.cname}</td>
                                <td>
                                    <c:if test="${curr.typeAuditing!='4'}">
                                        <%--<input type="button" value="查看" onclick="examine(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="examine(${curr.id},1);">查看</a>
                                    </c:if>
                                    <c:if test="${curr.typeAuditing=='4'}">
                                        <%--<input type="button" value="查看" onclick="sub(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="sub(${curr.id},1);">查看</a>
                                    </c:if>
                                    <c:if test="${curr.typeAuditing=='4'}">
                                        <%--<input type="button" value="提交" onclick="sub(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="sub(${curr.id});">提交</a>
                                    </c:if>
                                    <%--<input type="button" value="导出" onclick="exportWord(${curr.id});"/>--%>
                                    <a href="javascript:void(0)" onclick="exportWord(${curr.id});">导出</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>


                    <!-- 分页开始 -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=1')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=${pageModel.previousPage}')"
                           target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=${pageModel.nextPage}')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/reportRoutineInspection/reportInspect?currpage=${pageModel.lastPage}')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页结束 -->
                </div>
                <br/>

                <div id="newDiv" style="display: none;width:97%;height: 400px;padding: 20px;border:1px solid #000;">
                    <table border="0" cellpadding="5" cellspacing="100" height="30" width="100%">
                        <tbody>
                        <form:form name="queryForm2" action="" method="post" modelAttribute="routineInspection"
                                   enctype="multipart/form-data">
                            <tr>
                                <td>
                                    时间：${termName}
                                </td>
                                <td>
                                    <select class="chzn-select" path="week" id="week" name="week" style="width:90px">
                                        <c:forEach items="${schoolWeeks}" var="current">
                                            <option value="${current.week}">第${current.week}周</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    学院：${schoolAcademy.academyName}
                                    <input type="hidden" path="academyNumber" name="academyNumber"
                                           value="${schoolAcademy.academyNumber}">
                                </td>
                                <td> 实验中心：
                                </td>
                                <td>
                                    <form:select class="chzn-select" path="labCenter.id" id="labCenterId"
                                                 name="labCenter.id" style="width:150px">
                                        <c:forEach items="${labCenters}" var="current">
                                            <form:option value="${current.id}">${current.centerName} </form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                                <td>
                                    上报人：${user.cname}

                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <br/>
                                    <div style="float:left"> 日常管理情况：</div>
                                    <br/>
                                    <div style=" float:left">
                                        <textarea id="newCheckContent" required="required" rows="20" cols="60"
                                                  path="checkContent" name="checkContent"></textarea>
                                    </div>
                                </td>
                                <td colspan="3">
                                    <br/>
                                    <div style="float:left"> 安全管理情况：</div>
                                    <br/>
                                    <div style=" float:left">
                                        <textarea id="newSafetyManagement" required="required" rows="20" cols="60"
                                                  path="safetyManagement" name="safetyManagement"></textarea>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                                <td>
                                    <br>
                                    <input class="btn-big" type="button" value="提交" onclick="subNew()"/>
                                    <input class="btn-return" type="button" value="保存" onclick="save()"/>
                                </td>

                            </tr>
                        </form:form>
                        </tbody>
                    </table>
                    <div>
                    </div>
                </div>

                <div id="examineDiv" style="display: none;width:97%;height: 420px;padding: 20px;border:1px solid #000;">
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
                                实验中心：<input style="border:0px" type="text" readonly="true" size="30"
                                            id="riLabCenterName"/>
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
                                <input class="btn-return" type="button" value="关闭" onclick="closeDiv()"/>
                            </td>

                        </tr>

                        </tbody>
                    </table>
                    <div>
                    </div>
                </div>

                <div id="subDiv" style="display: none;width:97%;height: 400px;padding: 20px;border:1px solid #000;">
                    <table border="0" cellpadding="5" cellspacing="100" height="30" width="100%">
                        <form:form name="subForm"
                                   action="${pageContext.request.contextPath}/reportRoutineInspection/submitRoutineInspection"
                                   method="post" modelAttribute="routineInspection" enctype="multipart/form-data">
                        <tbody>
                        <tr>
                            <td style="white-space:nowrap">
                                <input id="riIdSub" type="hidden" path="id" name="id"/>
                                时间：<input style="border:0px" type="text" readonly="true" size="30" id="riTimeSub"/>

                            </td>
                            <td style="white-space:nowrap">
                                学院：<input style="border:0px" type="text" readonly="true" size="30"
                                          id="riCenterNameSub"/>

                            </td>
                            <td style="white-space:nowrap">
                                实验中心：<input style="border:0px" type="text" readonly="true" size="30"
                                            id="riLabCenterNameSub"/>
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
                                <input class="btn-big" type="submit" value="提交"/>
                                <input class="btn-return" type="button" value="关闭" onclick="closeDiv()"/>
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

        <div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add"
             style="width:400px;height:200px">
            <form id="form_file" method="post" enctype="multipart/form-data">
                <table border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td>
                            <div id="queue"></div>
                            <input id="file_upload" name="file_upload" type="file" multiple="true">
                    </tr>
                </table>
            </form>
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
