<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <link href="${pageContext.request.contextPath}/jquery/jquery.treegrid.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap3-editable/css/bootstrap-editable.css"
          rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
    <!-- 全局的引用 -->
    <script src="${pageContext.request.contextPath}/jquery/lib/jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 弹出框插件的引用 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <!-- bootstrap的引用 -->
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap3-editable/js/bootstrap-editable.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-table/extensions/treegrid/bootstrap-table-treegrid.js"></script>
    <script src="${pageContext.request.contextPath}/jquery/jquery.treegrid.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/directoryEngine/directoryEngine-core.js"></script>
    <c:choose>
        <c:when test="${sessionScope.selected_role eq 'ROLE_STUDENT'}">
            <script src="${pageContext.request.contextPath}/js/lims/timetable/self/newSelfReGroupCourseByStudent.js"
                    type="text/javascript"></script>
        </c:when>
        <c:otherwise>
            <script src="${pageContext.request.contextPath}/js/operation/timetableChooseGroupList.js"
                    type="text/javascript"></script>
        </c:otherwise>
    </c:choose>

    <style>
        .fixed-table-container thead th .sortable {
            background-image: url('${pageContext.request.contextPath}/images/sort.gif');
            cursor: pointer;
            background-position: right;
            background-size: 30px 30px;
            background-repeat: no-repeat;
            padding-right: 30px
        }

        .fixed-table-container thead th .asc {
            background-image: url('${pageContext.request.contextPath}/images/sort_asc.gif');
            cursor: pointer;
            background-position: right;
            background-size: 30px 30px;
            background-repeat: no-repeat;
            padding-right: 10px
        }

        .fixed-table-container thead th .desc {
            background-image: url('${pageContext.request.contextPath}/images/sort_dsc.gif');
            cursor: pointer;
            background-position: right;
            background-size: 30px 30px;
            background-repeat: no-repeat;
            padding-right: 10px
        }
    </style>

</head>

<body>
<div class="iStyle_RightInner">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="site-box">
            <div class="site-content">
                <div>
                    <input type="hidden" id="selfId" name="selfId" value="${selfId }">
                    <input type="hidden" id="opId" name="opId" value="${opId }">
                    <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}"/>
                    <input type="hidden" id="term" name="term" value="${term }">
                    <input type="hidden" id="type" name="type" value="${type }">
                    <c:if test="${type ne 0}">
                        <div id="divAddBatch" style="width:100%;display:none;">
                            <form name="form1" method="Post"
                                  action="${pageContext.request.contextPath}/timetable/newTimetableGroup">
                                <table style="width:100%;">
                                    <tr>
                                        <th width="10%">批次名称(<font color=red>*必填</font>)</th>
                                        <th width="10%">每批组数(<font color=red>*必填</font>)</th>
                                        <th width="10%">每组人数(<font color=red>*必填</font>)</th>
                                        <th width="10%">每人可选组数(<font color=red>*必填</font>)</th>
                                    </tr>
                                    <thead>
                                    <tr>
                                        <th colspan=6 align="left">选择实验项目进行分批处理(<font
                                                color=red>*所有分组排课完成，才能进行进行“调整完成”</font>)
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><input type="text" name="batchName" id="batchName" value=""
                                                   style="width:80px;"
                                                   required="required"></td>
                                        <td><input type="text" name="countGroup" id="countGroup" value=""
                                                   style="width:50px;"
                                                   required="required"></td>
                                        <td><input type="text" name="numbers" id="numbers" value="" style="width:50px;"
                                                   required="required"></td>
                                        <td><input type="text" name="maxGroupNum" id="maxGroupNum" value="" style="width:50px;"
                                                   required="required"></td>
                                        <input id="startDate" name="startDate" type="hidden"
                                                   value="<fmt:formatDate value="${startTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />"/>
                                        <input id="endDate" name="endDate" type="hidden"
                                                   value="<fmt:formatDate value="${endTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />"/>
                                        <input type="hidden" name="item" value="1">
                                        <c:if test="${type eq 3}">
                                            <input type="hidden" name="ifselect" value="0"/>
                                        </c:if>
                                        <c:if test="${type eq 1}">
                                            <input type="hidden" name="ifselect" value="1"/>
                                        </c:if>
                                    </tr>
                                    <tr>
                                        <th colspan=6>
                                        </th>
                                    </tr>
                                    <tr>
                                        <th colspan=5>
                                        </th>
                                        <th>
                                            <input id="closeButton" class='btn btn-xs red' onclick="showdiv()"
                                                   type="button" value="关闭选项">
                                            <input id="submitButton" name="submitButton" class='btn btn-xs red'
                                                   type="button" value="创建分批">
                                        </th>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                            </br>
                        </div>
                    </c:if>
                    <div id="divGrid">
                        <table id="table_list" style="text-align: left;"></table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

