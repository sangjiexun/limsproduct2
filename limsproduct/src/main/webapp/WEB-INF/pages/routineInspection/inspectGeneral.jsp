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
                    document.queryForm2.action = "${pageContext.request.contextPath}/routineInspection/newRoutineInspection?genre=1 ";
                    document.queryForm2.submit();
                }
            }
        }

        //新建-保存
        function save() {
            if ($("#newCheckContent").val() == "" || $("#newCheckContent").val() == null) {
                alert("日常管理情况未填写！")
            } else {
                if ($("#newSafetyManagement").val() == "" || $("#newSafetyManagement").val() == null) {
                    alert("安全管理情况未填写！")
                } else {
                    document.queryForm2.action = "${pageContext.request.contextPath}/routineInspection/newRoutineInspection?genre=2 ";
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
                contentType: "text/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    $("#riTime").val(decodeURI(data.riTime));
                    $("#riCenterName").val(decodeURI(data.riCenterName));
                    $("#riRoomName").val(decodeURI(data.riRoomName));
                    $("#riCnam").val(decodeURI(data.riCnam));
                    $("#riCheckContent").val(decodeURI(data.riCheckContent));
                    $("#riSafetyManagement").val(decodeURI(data.riSafetyManagement));

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
                    $("#riTimeSub").val(data.riTime);
                    $("#riCenterNameSub").val(data.riCenterName);
                    $("#riRoomNameSub").val(data.riRoomName);
                    $("#riCnamSub").val(data.riCnam);
                    $("#riCheckContentSub").val(data.riCheckContent);
                    $("#riCheckSafetyManagement").val(data.riSafetyManagement);
                    $("#urlSub").attr("src", "${pageContext.request.contextPath}" + data.url)
                    $("#newDiv").hide();
                    $("#examineDiv").hide();
                    $("#subDiv").show();
                },
                error: function () {
                    alert("查询附件信息出错！");
                }
            });
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
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.routine.inspect"/></a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">常规检查</a>
            </li>
            <c:if test="${flag eq 1}">
                <input class="btn btn-new" type="button" value="新建" onclick="newRoutineInspection();"/>
            </c:if>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/routineInspection/general?currpage=1 "
                                   method="post" modelAttribute="routineInspection">
                            <ul>
                                <li>
                                    实验室:
                                    <form:select class="chzn-select" path="labRoom.id" id="labRoomId"
                                                 name="labRoom.id" style="width:100px">
                                        <form:option value="">请选择</form:option>
                                        <c:forEach items="${labRooms}" var="current">
                                            <form:option
                                                    value="${current.id}">${current.labRoomName} </form:option>
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
                                        <form:option value="">请选择</form:option>
                                        <c:forEach items="${schoolWeeks}" var="current">
                                            <form:option value="${current.week}">第${current.week}周</form:option>
                                        </c:forEach>
                                    </form:select>
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
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${RoutineInspection}" var="curr">
                            <tr>
                                <td>${curr.schoolTerm.termName} 第${curr.week}周</td>
                                <td>${curr.labRoom.labAnnex.labCenter.schoolAcademy.academyName}</td>
                                <td>${curr.labRoom.labRoomName}</td>
                                <td>${curr.user.cname}</td>
                                <td>
                                    <c:if test="${curr.typeAuditing!='4'}">
                                        <%--<input type="button" value="查看" onclick="examine(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="examine(${curr.id});">查看</a>
                                    </c:if>
                                    <c:if test="${curr.typeAuditing=='4'}">
                                        <%--<input type="button" value="查看" onclick="sub(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="sub(${curr.id});">查看</a>
                                    </c:if>
                                    <c:if test="${curr.typeAuditing=='4'}">
                                        <%--<input type="button" value="提交" onclick="sub(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="sub(${curr.id});">提交</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <!-- 分页开始 -->
                    <div class="page">
                        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/routineInspection/general?currpage=1&orderBy=${orderBy }')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/routineInspection/general?currpage=${pageModel.previousPage}&orderBy=${orderBy }')"
                           target="_self">上一页</a>
                        第<select onchange="targetUrl(this.options[this.selectedIndex].value);">
                        <option value="${pageContext.request.contextPath}/routineInspection/general?currpage=${pageModel.currpage}&orderBy=${orderBy }">${pageModel.currpage}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=pageModel.currpage}">
                                <option value="${pageContext.request.contextPath}/routineInspection/general?currpage=${j.index}&orderBy=${orderBy }">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/routineInspection/general?currpage=${pageModel.nextPage}&orderBy=${orderBy }')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="targetUrl('${pageContext.request.contextPath}/routineInspection/general?currpage=${pageModel.lastPage}&orderBy=${orderBy }')"
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
                                <td> 实验室：</td>
                                <td>
                                    <form:select class="chzn-select" path="labRoom.id" id="labRoomId" name="labRoom.id"
                                                 style="width:150px">
                                        <c:forEach items="${labRooms}" var="current">
                                            <form:option value="${current.id}">${current.labRoomName} </form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                                <td>
                                    检查人：${user.cname}

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
                                    <input class="btn-big" type="button" value="保存" onclick="save()"/>
                                </td>

                            </tr>
                        </form:form>
                        </tbody>
                    </table>
                    <div>
                    </div>
                </div>

                <div id="examineDiv"
                     style="display: none;width:97%;height: 420px;padding: 20px;border:1px solid #000;border-radius:5px;">
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
                                检查人：<input style="border:0px" type="text" readonly="true" size="30" id="riCnam"/>

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
                                   action="${pageContext.request.contextPath}/routineInspection/submitRoutineInspection"
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
                                实验室：<input style="border:0px" type="text" readonly="true" size="30" id="riRoomNameSub"/>
                            </td>
                            <td style="white-space:nowrap">
                                检查人：<input style="border:0px" type="text" readonly="true" size="30" id="riCnamSub"/>

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
