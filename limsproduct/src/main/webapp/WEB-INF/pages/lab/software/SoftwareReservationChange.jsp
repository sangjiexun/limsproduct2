<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />

    <script src="${pageContext.request.contextPath}/js/Calendar.js" type="text/javascript"></script>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">

        $(function () {
            //alert(state);
            $("#c li").eq(0).css("padding", "4px 10px");
            $("#c li").eq(0).css("background-color", "#FFF");
            $("#c li").eq(0).css("border-bottom", "1px solid #FFF");
            $("#c li").eq(0).css("color", "#999");
        });
    </script>

    <script type="text/javascript">
        function uploadDocument(flag) {
            $('#searchFile').window({top: 300});
            $('#searchFile').window('open');
            $('#file_upload').uploadify({
                'formData': {id: '${softwareReserve.id}', flag: flag},    //传值
                'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
                'uploader': '${pageContext.request.contextPath}/softwareDocumentUpload;jsessionid=<%=session.getId()%>',
                //提交的controller和要在火狐下使用必须要加的id
                buttonText: '选择附件',
                onQueueComplete: function (data) {//当队列中的所有文件全部完成上传时触发
                    //当上传玩所有文件的时候关闭对话框并且转到显示界面
                    $('#searchFile').window('close');
                    window.location.href = "${pageContext.request.contextPath}/SoftwareReservationChange?id=${softwareReserve.id}";
                }
            });

        }
    </script>
    <style type="text/css">
        input[type='text'] {
            height: 30px;
            line-height: 30px;
        }

        .combo {
            margin: 0 !important;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.softwareInstall.application"/></a></li>
            <li class="end"><a href="javascript:void(0)">软件安装申请</a></li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">修改软件安装申请</div>
                    </div>

                    <form:form id="softForm" action="${pageContext.request.contextPath}/saveSoftwareReservationChange"
                               method="POST" modelAttribute="softwareReserve">
                        <form:hidden id="approve_state" path="approveState" value="1"/>
                        <form:hidden path="id"/>
                        <form:hidden path="teacher" value="${user.cname}"/>
                        <form:hidden path="point" value="${softwareReserve.point}"/>
                        <form:hidden path="state" value="${softwareReserve.state}"/>
                        <form:hidden path="buttonMark" value="${softwareReserve.buttonMark}" />
                        <table class="tb" id="table">
                            <tr>
                                <td>通知声明:</td>
                                <td colspan="3"><font color="#DF0029" size="3px">请认真填写</font></td>
                            </tr>
                            <tr>
                                <td>申请人:</td>
                                <td>
                                    <form:select id="userId" class="chzn-select" cssStyle="width:170px;"
                                                 path="user.username">
                                        <option Value="${user.username}"/>
                                        ${user.cname}
                                    </form:select>
                                </td>
                                <td>申请时间:</td>
                                <td>
                                    <%--<input id="createtime" class="easyui-datebox" type="text" name="createTime"
                                                data-options="required:true,showSeconds:false" path="createTime"
                                                style="width:160px;"/>--%>
                                    <input class="easyui-datebox"  id="createtime" value="${createTime}" name="create_time" onclick="new Calendar().show(this);"  readonly="readonly"/>
                                </td>
                            </tr>

                            <tr>
                                <td>安装实训室:</td>
                                <td>
                                    <form:select id="labId" class="chzn-select" cssStyle="width:170px;"
                                                 path="labRoom.id" onchange="getLabSoftwareList(this.value)">
                                        <form:options items="${labList}" itemLabel="labRoomName" itemValue="id"/>
                                    </form:select>
                                </td>
                                <td>软件名称:</td>
                                <td>
                                    <form:select class="chzn-select" path="code" data-placeholder="请选择添加软件..."
                                                 onchange="cleanSoftList()">
                                        <option value="0">请选择</option>
                                        <c:forEach items="${softwareList}" var="soft">

                                            <option value="${soft.id}">${soft.name }&nbsp;&nbsp;(${soft.code })</option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                            </tr>


                            <tr>
                                <td>未搜索到的软件名称:</td>
                                <td><form:input path="name"/></td>
                                <td>系统要求:</td>
                                <td><form:input path="requirement" required="true"/></td>
                            </tr>

                            <tr>
                                <td>加密狗（有/无）</td>
                                <td>
                                    <form:radiobutton path="dongle" value="true" required="true"/>有
                                    <form:radiobutton path="dongle" value="false" required="true"/>无
                                </td>
                                <td>光盘（有/无）</td>
                                <td>
                                    <form:radiobutton path="cd" value="true" required="true"/>有
                                    <form:radiobutton path="cd" value="false" required="true"/>无
                                </td>
                            </tr>

                            <tr>
                                <td>课程:</td>
                                <td>
                                    <form:select class="chzn-select" cssStyle="width:170px;" data-placeholder="请选择课程"
                                                 path="course">
                                        <c:forEach items="${courses }" var="course">
                                            <form:option
                                                    value="${course.courseName}">${course.courseName}[${course.courseCode}]</form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                                <td>安装时间:</td>
                                <td><%--<form:input id="requiretime" class="easyui-datetimebox" type="text"
                                                data-options="required:true,showSeconds:false" name="requireTime"
                                                path="requireTime" style="width:160px;"/>--%>
                                    <input class="easyui-datebox"  id="requiretime" value="${requireTime}" name="require_time" onclick="new Calendar().show(this);"  readonly="readonly"/>

                                </td>
                            </tr>
                            <tr>
                                <td>软件架构:</td>
                                <td>
                                    <form:input path="frame" required="true"/>
                                </td>
                                <td>申请理由:</td>
                                <td>
                                    <form:input path="applyReason" required="true"/>
                                </td>
                            </tr>

                            <tr>
                                <td>供应商:</td>
                                <td>
                                    <form:input path="supply" required="true"/>
                                </td>
                                <td>供应商联系方式:</td>
                                <td>
                                    <form:input path="supplyPhone" required="true" maxlength="15"/>
                                </td>
                            </tr>

<%--                            <tr>
                                <td>安装说明:</td>
                                <td>
                                    <div id="TabbedPanels1" class="TabbedPanels">
                                        <div class="TabbedPanelsContentGroup">
                                            <div class="TabbedPanelsContent">

                                                <input type="button" onclick="uploadDocument(1);" value="上传附件">
                                                <br>
                                                <c:forEach items="${commonDocuments1}" var="c1">
                                                    ${c1.documentName}<br>
                                                </c:forEach>

                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>使用说明:</td>
                                <td>
                                    <div id="TabbedPanels1" class="TabbedPanels">
                                        <div class="TabbedPanelsContentGroup">
                                            <div class="TabbedPanelsContent">

                                                <input type="button" onclick="uploadDocument(2);" value="上传附件">
                                                <br>
                                                <c:forEach items="${commonDocuments2}" var="c2">
                                                    ${c2.documentName}<br>
                                                </c:forEach>

                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>--%>

                            <tr>
                                <td colspan="4" style="border: 1px solid #999;text-align:right;">
                                    <input class="btn btn-new" type="submit" value="保存"/>
                                </td>
                            </tr>
                        </table>
                    </form:form>

                    <!-- 上传图片 -->
                    <div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add"
                         style="width:400px;height:200px;">
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
                    <!-- 上传图片结束 -->


                    <script type="text/javascript"
                            src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"></script>
                    <script type="text/javascript"
                            src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"></script>
                    <!-- 下拉框的js -->
                    <script type="text/javascript">
                        $(function ($) {
                            var config = {
                                '.chzn-select': {search_contains: true, width: "auto"},
                                '.chzn-select-deselect': {allow_single_deselect: true},
                                '.chzn-select-no-single': {disable_search_threshold: 10},
                                '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
                                '.chzn-select-width': {}
                            }
                            for (var selector in config) {
                                $(selector).chosen(config[selector]);
                            }

                            $(".chzn-container").each(function () {
                                $(this).css("width", "200px");
                            });

                            getLabSoftwareList($("#labId").val());
                        });

                        //获取后台返回的软件集合并显示在页面
                        function getLabSoftwareList(labId) {
                            $.ajax({
                                url: 'getLabSoftwareList?labId=' + labId,
                                dataType: 'json',
                                success: function (json) {
                                    var str = "";
                                    if (json.length > 0) {
                                        $.each(json, function (index, array) {
                                            str += "<input type='checkbox' style='margin-left:10px;' value=" + array['id'] + " onclick='selSoftware(this.value)'/>" + array['name'] + "&nbsp&nbsp&nbsp";

                                        });
                                        $("#labSoftwareList").html(str)
                                    } else {
                                        $("#labSoftwareList").text("此实训室无软件")
                                    }
                                },
                                error: function () {
                                    alert("数据加载失败");
                                }
                            });
                        }

                        //选中select
                        function selSoftware(softwareId) {
                            $("#code").val(softwareId);
                            $(":checkbox").each(function () {
                                $(this).removeAttr("checked");
                            })
                            $("input[value='" + softwareId + "']").prop("checked", true)
                            $(".chzn-select").trigger("liszt:updated");
                        }

                        function cleanSoftList() {
                            $(":checkbox").each(function () {
                                $(this).removeAttr("checked");
                            })
                        }
                    </script>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
