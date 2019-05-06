<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
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
    <c:choose>
        <c:when test="${sessionScope.selected_role eq 'ROLE_STUDENT'}">
            <script src="${pageContext.request.contextPath}/js/lims/timetable/engineer/edurecourse/newEduReGroupCourseByStudent.js"
                    type="text/javascript"></script>
        </c:when>
        <c:otherwise>
            <script src="${pageContext.request.contextPath}/js/lims/timetable/engineer/edurecourse/newEduReGroupCourse.js"
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
        .bootstrap-table .table{
            border-bottom:none;
        }
        #divAddBatch{
            display: none;
            margin: 20px 0 70px;
        }
        #divAddBatch th{
            background: #f9f9f9;
        }
        .bt_tit{
            text-align: left;
            margin: 0 0 5px 0;
            color: #404f6d;
            border-bottom: none;
        }
        .bt_tit font,
        .th-inner font{
            color: #ff0000;
        }
        #divAddBatch .pull-right{
            margin:10px 0 0 0;
        }
        .combo,
        #divAddBatch td>input[type="text"],
        #divAddBatch td>input[type="num"]{
            border: 1px solid #d3d3d3;
            border-radius: 4px;
        }
        #divAddBatch input[type="text"],
        #divAddBatch input[type="num"]{
            padding: 1px;
            border-radius: 4px;
        }
        .fixed-table-toolbar{
            margin:-7px 0;
        }
        .fixed-table-toolbar .bars{
            margin-top:0;
            margin-bottom:0;
        }
    </style>
    <script type="text/javascript">
        function myparser(s){
            if (!s) return new Date();
            var ss = (s.split('-'));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            } else {
                return new Date();
            }
        }
    </script>
</head>

<body>
<div class="iStyle_RightInner">
    <div class="navigation">
        <div id="navigation">
            <ul>
                <li><a href="javascript:void(0)">开始二次分批排课</a></li>
            </ul>
        </div>
    </div>
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="site-box">
            <div class="site-content">
                <div>
                    <div id="divAddBatch">
                        <form name="form1" method="Post"
                              action="${pageContext.request.contextPath}/timetable/newTimetableGroup">
                            <input type="hidden" id="courseNo" name="courseNo" value="${courseNo }">
                            <input type="hidden" id="term" name="term" value="${term }">
                            <div class="bt_tit">选择实验项目进行分批处理<font>（*所有分组排课完成，才能进行进行“调整完成”</font>）</div>
                            <div class="bootstrap-table">
                                <div class="fixed-table-container">
                                    <div class="fixed-table-body">
                                        <table class="table">
                                            <thead>
                                            <tr>
                                                <th width="15%"><div class="th-inner">批次名称(<font>*必填</font>)</div></th>
                                                <th width="10%"><div class="th-inner">每批组数(<font>*必填</font>)</div></th>
                                                <th width="10%"><div class="th-inner">每组人数(<font>*必填</font>)</div></th>
                                                <th width="20%"><div class="th-inner">选课开始日期(<font>*必填</font>)</div></th>
                                                <th width="20%"><div class="th-inner">选课结束日期(<font>*必填</font>)</div></th>
                                                <th width="25%"><div class="th-inner">选课形式</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td><input type="text" name="batchName" id="batchName" value="" style="width:80px;"
                                                           required="required"></td>
                                                <td><input type="text" name="countGroup" id="countGroup" value="" style="width:50px;"
                                                           required="required"></td>
                                                <td><input type="text" name="numbers" id="numbers" value="" style="width:50px;"
                                                           required="required"></td>
                                                <td><input class="easyui-datebox" id="startDate" name="startDate" type="text"
                                                           data-options="formatter:myformatter,parser:myparser"
                                                           value="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />"
                                                           onclick="new Calendar().show(this);" style="width:150px;"/>
                                                </td>
                                                <td><input class="easyui-datebox" id="endDate" name="endDate" type="text"
                                                           data-options="formatter:myformatter,parser:myparser"
                                                           value="<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />"
                                                           style="width:150px;"/>
                                                </td>
                                                <input type="hidden" name="item" value="1">
                                                <td>
                                                    <div class="fixed-table-toolbar">
                                                        <div class="bars">
                                                            <div class="form-inline">
                                                            <div class="form-group">
                                                                <div class="custom-control custom-radio">
                                                                    <input type="radio" id="ifselect1" name="ifselect" class="custom-control-input" value="0"  checked="checked"/>
                                                                    <label class="custom-control-label" for="ifselect1">自动选课</label>
                                                                </div>
                                                            </div>
                                                                <div class="form-group">
                                                                <div class="custom-control custom-radio">
                                                                    <input type="radio" id="ifselect2" name="ifselect" class="custom-control-input" value="1"/>
                                                                    <label class="custom-control-label" for="ifselect2">学生选课</label>
                                                                </div>
                                                            </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="pull-right">
                            <input id="closeButton" class='btn btn-xs red' onclick="showdiv()" type="button" value="关闭选项">
                            <input id="submitButton" name="submitButton"  class='btn btn-xs red' type="button" value="创建分批">
                            </div>
                        </form>
                    </div>
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

