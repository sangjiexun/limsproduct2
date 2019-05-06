<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式 -->
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css"/>

    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/js/layer-v2.2/layer/skin/layer.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript">
        function targetUrl(url) {
            //获取sessionstorage中的设备编号
            var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
            window.location.href = url+'&selectedDeviceStr='+selectedDeviceStr;
        }
    </script>

</head>

<body>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsTabGroup-box">


            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">

                    <div class="content-box">

                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th>设备编号</th>
                                <th>设备名称</th>
                                <th>设备型号</th>
                                <th>实验室名称</th>
                                <th>设备管理员</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${allLendableDeviceList}" var="curr">
                                <tr>
                                    <td>${curr.schoolDevice.deviceNumber}</td>
                                    <td>${curr.schoolDevice.deviceName}</td>
                                    <td>${curr.schoolDevice.devicePattern}</td>
                                    <td>${curr.labRoom.labRoomName}</td>
                                    <td>${curr.user.cname}</td>
                                    <td><button  type="button" id="${curr.id}" onclick="cancelDevice(this)">删除</button></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                        <!-- 分页模块 -->
                        <div class="page">
                            ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=1')"
                               target="_self">首页</a>
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=${pageModel.previousPage}')"
                               target="_self">上一页</a>
                            第<select onchange="targetUrl(this.options[this.selectedIndex].value)">
                            <option value="${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
                            <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                       var="current">
                                <c:if test="${j.index!=pageModel.currpage}">
                                    <option value="${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=${j.index}">${j.index}</option>
                                </c:if>
                            </c:forEach></select>页
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=${pageModel.nextPage}')"
                               target="_self">下一页</a>
                            <a href="javascript:void(0)"
                               onclick="targetUrl('${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=${pageModel.lastPage}')"
                               target="_self">末页</a>
                        </div>
                        <!-- 分页模块 -->
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
        '.chzn-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

    /**
     * 删除选中设备
     * @param element
     */
    function cancelDevice(element) {
        var deviceId = element.id;
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
        //删除选中设备
        var selectedDeviceArr = new Array();
        selectedDeviceArr = selectedDeviceStr.split(',');
        selectedDeviceArr = $.grep(selectedDeviceArr, function(value) {
            return value.split("_")[1] != deviceId;
        });
        selectedDeviceStr = selectedDeviceArr.toString();
        //重新设置设备id字符串
        sessionStorage.setItem('selectedDeviceId',selectedDeviceStr);
        console.log('当前选中的设备为:  '+sessionStorage.getItem('selectedDeviceId'))

        //删除dom节点
        $(element).parent().parent().remove();
    }

</script>
<!-- 下拉框的js -->
</body>
</html>


