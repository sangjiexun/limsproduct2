<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false"
         contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/chosen.css"/>

    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css"/>
    <!-- 下拉的样式结束 -->
    <!-- 打印插件的引用 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 弹窗 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/virtual/virtualImageAppointmentCitrix.js"></script>

    <style type="text/css">
        /*#labRoom_chzn, #usingObj_chzn {*/
            /*width: 200px !important;*/
        /*}*/

        #formid table tr td, th {
            text-align: left;
        }

        .btn_reser {
            text-align: center;
            border: none !important;
            padding: 10px 0 !important;
            background: #eee;
            border-bottom: 1px solid #eee !important;
            position: relative;
        }

        .btn_reser:hover {
            opacity: 0.9;
        }

        .btn_reser:after {
            content: "";
            height: 100%;
            width: 6px;
            padding: 0 0 2px 0;
            background: #fff;
            position: absolute;
            right: 0;
            top: -1px;
        }

        .br_top {
            position: absolute;
            left: -1px;
            top: -1px;
            width: 100%;
            height: 4px;
            background: #fff;
        }

        .br_btm {
            position: absolute;
            left: -1px;
            bottom: -1px;
            width: 100%;
            height: 4px;
            background: #fff;
        }

        .btn_reser a {
            display: block;
            width: 20px;
            color: #777;
            line-height: 18px;
            white-space: normal;
            margin: 0 0 0 4px;
            font-weight: normal;
            padding: 0 9px !important;
        }

        .br_selected {
            background: #77bace;
            border-bottom: 1px solid #77bace !important;
        }

        .br_selected a {
            color: #fff;
        }

        .cf:after {
            display: block;
            content: "gvsun";
            height: 0;
            clear: both;
            overflow: hidden;
            visibility: hidden;
        }

        .tool-box input {
            float: none;
        }

        .content-box .tab_lab {
            width: 100%;
            left: 0;
            margin: -1px;
        }

        .tab_lab {
            width: 100%;
        }

        .tab_lab,
        .tab_lab th,
        .tab_lab td {
            border: 1px solid #e4e5e7;
        }

        .tab_lab th {
            background: #fafafa;
            width: 90px;
            padding: 0 15px 0 0;
            text-align: right;
        }

        .tab_lab td {
            text-align: left;
            padding: 10px;
        }

        .tab_lab td input[type="text"],
        .tab_lab td textarea,
        .tab_lab td .spinner,
        .tab_lab td .combo {
            resize: none;
            border: 1px solid #cdcdcd;
            border-radius: 3px;
            line-height: 22px;
            padding: 0 10px;
            outline: none;
        }

        .tab_lab td .spinner,
        .tab_lab td .combo {
            padding: 1px 0;
        }

        .tab_lab td input[type="text"] {
            width: 124px;
        }

        .tab_lab td .spinner input[type="text"],
        .tab_lab td .combo input[type="text"] {
            border: none;
        }

        .tab_lab td textarea {
            width: 100%;
            box-sizing: border-box;
        }

        .tab_lab td input[type="text"]:focus,
        .tab_lab td textarea:focus {
            border: 1px solid #f3ce7a;
        }

        .tab_fix td {
            text-align: left;
            white-space: nowrap;
            padding-right: 20px;
        }

        .tab_fix td input[type="text"] {
            height: 24px;
            width: 100%;
            box-sizing: border-box;
            /*min-width: 210px;*/
        }

        .tab_fix th {
            font-weight: normal;
            width: 95px;
            text-align: right;
            white-space: nowrap;
        }

        .datebox,
        .spinner {
            margin: 0 12px 0 0;
        }
    </style>
</head>
<body>
<!--导航  -->
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">开放预约 </a></li>
            <li class="end"><a href="javascript:void(0)">虚拟镜像预约</a></li>
        </ul>
    </div>
</div>
<!--导航结束  -->
<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
          <li class="TabbedPanelsTab" id="s1">
              <a href="${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1">虚拟镜像预约</a></li>
        <li class="TabbedPanelsTab selected" id="s4">
            <a href="${pageContext.request.contextPath}/virtual/virtualImageReservationCitrix?currpage=1">虚拟镜像预约(直连)</a></li>
        <li class="TabbedPanelsTab" id="s2"><a
                  href="${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=0&page=1&isaudit=2">我的虚拟镜像申请</a>
          </li>
          <sec:authorize ifNotGranted="ROLE_STUDENT">
              <li class="TabbedPanelsTab" id="s3"><a
                      href="${pageContext.request.contextPath}/virtual/virtualImageReservationList?tage=0&page=1&isaudit=1">我的虚拟镜像审核</a>
              </li>
          </sec:authorize>

    </ul>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <form id="formid" method="POST">
                        <table class="tab_lab">
                            <tr>
                                <th>镜像名称</th>
                                <td>
                                    <select class="chzn-select" id="VirtualImage" >
                                        <c:forEach items="${VirtualImages}" var="vi">
                                            <option value="${vi.id}">${vi.name }</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>预约开始时间</th>

                                <td colspan="3">
                                    <input id="startTime" name="startTime" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px;" />
                                    预约镜像后，最多可用3小时
                                </td>

                                <%--<th>预约结束时间</th>

                                <td colspan="3">
                                     <input id="endTime" name="endTime" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px;" />
                                </td>--%>
                            </tr>
                            <tr>
                                <th>预约原因<span style="color:red;">*</span></th>
                                <td colspan="5">
                                    <textarea id="remarks" name="remarks" type="text"
                                              rows="5"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6" style="text-align:right;">
                                    <input type="button" value="更新镜像" class="btn btn-new" onclick="updateImage()">
                                    <input type="button" value="提交" class="btn btn-new" onclick="saveReservation()">
                                </td>
                            </tr>

                        </table>
                    </form>

                    <div class="right-content">
                        <div class="content-box">
                            <table>
                                <thead>
                                <tr>
                                    <th>镜像编号</th>
                                    <th>镜像名称</th>
                                    <th>硬件链接</th>
                                    <th>图片编号</th>
                                    <th>供应商</th>
                                    <th>备注</th>
                                    <th>预约记录</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${VirtualImages}" var="curr">
                                    <tr>
                                        <td>${curr.id}</td>
                                        <td>${curr.name}</td>
                                        <td>${curr.hardwareSet}</td>
                                        <td>${curr.imageCode}</td>
                                        <td>${curr.provider}</td>
                                        <td>${curr.setNote}</td>
                                        <td><a href='javascript:layerListVirtualImageReservation("${curr.id}")'>查看</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="page" >
                                ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                                <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1')" target="_self">首页</a>
                                <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
                                第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                                    <option value="${pageContext.request.contextPath}/virtual/listVirtualImage?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                                        <c:if test="${j.index!=pageModel.currpage}">
                                            <option value="${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=${j.index}">${j.index}</option>
                                        </c:if>
                                    </c:forEach></select>页

                                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
                                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=${pageModel.lastPage}')" target="_self">末页</a>
                            </div>

                        </div>
                    </div>


                    <!-- 下拉框的js -->

                    <script
                            src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                            type="text/javascript"></script>

                    <script
                            src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                            type="text/javascript" charset="utf-8"></script>

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

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>