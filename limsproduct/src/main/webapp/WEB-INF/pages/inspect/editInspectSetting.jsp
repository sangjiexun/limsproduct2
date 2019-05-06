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
            <li class="end"><c:if test="${isEdit}"><a href="javascript:void(0)">编辑</a></c:if><c:if test="${isNew}"><a href="javascript:void(0)">新建</a></c:if></li>
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
                        <div id="title">编辑评价时间设置</div>
                    </div>

                    <!-- 表单 -->
                    <form:form action="${pageContext.request.contextPath}/inspect/saveInspectSetting?idKey=${inspectSetting.id}" method="POST" modelAttribute="inspectSetting">
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="id"/>
                                <label style="width:auto">学期：</label>
                                <form:input style="width:80%;margin-bottom:5px" path="semeter"/>
                            </fieldest>
                            <fieldset>
                                <label>定期与否：</label>
                                <form:select path="isRegular" id="number" name="number" >
                                    <form:option value="">请选择</form:option>
                                    <form:option value="1">定期</form:option>
                                    <form:option value="0">不定期</form:option>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>星期：</label>
                                <td>
                                    <select class="chzn-select" multiple="multiple" data-placeholder='${weekstring}' name="weekday" id="weekday"  style="width:400px"  >
                                        <option value="1">星期一</option>
                                        <option value="2">星期二</option>
                                        <option value="3">星期三</option>
                                        <option value="4">星期四</option>
                                        <option value="5">星期五</option>
                                        <option value="6">星期六</option>
                                        <option value="7">星期七</option>
                                    </select>
                                </td>
                            </fieldset>
                            </td>
                            <%--<fieldset>--%>
                                <%--<label>评价开始时间：</label>--%>
                                <%--<input name="startTime" type="date" value="<fmt:formatDate value='${inspectSetting.startTime.time}' pattern='yyyy-MM-dd'/>"/>--%>
                                <%--<label>评价结束时间：</label>--%>
                                <%--<input name="endTime" type="date" value="<fmt:formatDate value='${inspectSetting.endTime.time}' pattern='yyyy-MM-dd'/>"/>--%>
                            <%--</fieldset>--%>
                            <fieldset>
                                <td class="label" valign="top">项目名称<span style="color: red">*</span> :</td>
                                <td>
                                    <select name="standard"  style="width:300px;" class="chzn-select"  multiple="true" required="true">
                                        <c:forEach items="${labInspects }" var="cur">
                                            <option value="${cur.id}" selected="selected"> ${cur.standardName}</option>
                                        </c:forEach>
                                        <c:forEach items="${labInspectList }" var="cur">
                                            <option value="${cur.id}" > ${cur.standardName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </fieldset>
                            <fieldest>
                                <label>批次：</label>
                                <form:input path="title"/>
                                <label>名称：</label>
                                <form:input path="comment"/>
                            </fieldest>
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

