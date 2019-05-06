<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">评价时间设置表</a></li>
            <li class="end">新建</li>
        </ul>
    </div>
</div>
<!-- 内容栏开始 -->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <!-- 标题 -->
                    <div class="title">
                        <div id="title"><c:if test="${isEdit}">编辑</c:if><c:if test="${isNew}">新建</c:if>评价时间设置表</div>
                    </div>
                    <!-- 表单 -->
                    <form:form action="${pageContext.request.contextPath}/inspect/saveInspectSetting?idKey=0" method="POST" modelAttribute="labInspectSetting">
                        <div class="new-classroom">
                            <fieldset >
                                <form:hidden path="id"/>
                                <label style="width:auto">学期：</label>
                                <form:input style="width:80%;margin-bottom:5px" path="semeter"/>

                                <fieldset>
                                    <label style="width:auto">定期与否：</label>
                                    <form:select style="width:200px"  path="isRegular" >
                                        <form:option value="">请选择</form:option>
                                        <form:option value="1">定期</form:option>
                                        <form:option value="0">不定期</form:option>
                                    </form:select>
                                </fieldset>
                                <fieldset>
                                    <label style="width:auto">星期：</label>
                                    <select class="chzn-select"  multiple="multiple" data-placeholder='${weekString}' name="weekday" id="weekday" style="width:200px"  >
                                        <option value="1">星期一</option>
                                        <option value="2">星期二</option>
                                        <option value="3">星期三</option>
                                        <option value="4">星期四</option>
                                        <option value="5">星期五</option>
                                        <option value="6">星期六</option>
                                        <option value="7">星期七</option>
                                    </select>
                                </fieldset>
                            </fieldset>
                            <fieldset>
                                <td class="label" valign="top">项目名称<span style="color: red">*</span> :</td>
                                <td>
                                    <select id="standard" name="standard"  style="width:300px;" class="chzn-select"  multiple="true" required="true">
                                        <c:forEach items="${labInspects}" var="cur">
                                            <option value="${cur.id}" > ${cur.standardName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </fieldset>
                            <fieldset>
                                <label>批次：</label>
                                <form:input path="title"/>
                                <label>名称：</label>
                                <form:input path="comment"/>
                            </fieldset>
                            <%--<fieldset>
                                <label style="width:100%">不定期请选择：</label>
                                <fieldset>
                                    <label style="width:auto">评价开始时间：</label>
                                    <input name="startTime" class="easyui-datebox" style="width:250px" value="<fmt:formatDate value='${labInspectSetting.startTime.time}' pattern='yyyy-MM-dd'/>"/>
                                </fieldset>
                                <fieldset>
                                    <label style="width:auto">评价结束时间：</label>
                                    <input name="endTime" class="easyui-datebox" style="width:250px" value="<fmt:formatDate value='${labInspectSetting.endTime.time}' pattern='yyyy-MM-dd'/>"/>
                                    <input name="labRoomTimeCreate" class="easyui-datebox" value="<fmt:formatDate value='${labRoom.labRoomTimeCreate.time}' pattern='yyyy-MM-dd'/>" />
                                </fieldset>
                            </fieldset>--%>
                            <div class="submit_link">
                                <input class="btn" id="save" type="submit" value="保存">
                                <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
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

