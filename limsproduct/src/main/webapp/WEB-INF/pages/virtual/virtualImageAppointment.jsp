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



    <script type="text/javascript">

        //分页跳转
        function targetUrl(url) {
            document.form.action = url;
            document.form.submit();
        }
        function saveReservation(){
            //判断预约时间是否为当前时间的十分钟后
            var now=new Date();
            var start=new Date($("#startTime").val());
            var num = (start.getTime()-now.getTime())/(1000*60);
            if(num<10){
                alert("预约时间需为当前时间的十分钟后");
                return false;
            }
            //判断预约时长是否超过两小时
            var end=new Date($("#endTime").val());
            var long = (end.getTime()-start.getTime())/(1000*60*60);
            if(long<0){
                alert("结束时间需大于开始时间");
                return false;
            }
            if(long>2){
                alert("预约时长不可超过两小时");
                return false;
            }
            //判断当前时间段是否可用
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/virtual/checkImage",
                data:{'VirtualImage': $("#VirtualImage").val(),'startTime': $("#startTime").val(),'endTime': $("#endTime").val(),'remarks':$("#remarks").val()},
                dataType: 'text',
                success: function (data) {
                    if(data=="success") {
                        //保存预约记录
                        $.ajax({
                            type: "POST",
                            url: "${pageContext.request.contextPath}/virtual/saveVirtualImageReservation",
                            data:{'VirtualImage': $("#VirtualImage").val(),'startTime': $("#startTime").val(),'endTime': $("#endTime").val(),'remarks':$("#remarks").val()},
                            dataType: 'text',
                            success: function (data) {
                                if(data=="success"){
                                    alert("预约成功");
                                    window.location.href = "${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1"
                                }else if(data=="interfaceSuccess"){
                                    alert("接口错误");
                                    window.location.href = "${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1"
                                }else if(data=="dataSuccess"){
                                    alert("该镜像桌面已被占用");
                                    window.location.href = "${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1"
                                }else if(data=="idSuccess"){
                                    alert("未知错误");
                                    window.location.href = "${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1"
                                }else{
                                    alert("其他错误");
                                    window.location.href = "${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1"
                                }
                            }
                        });
                    }else if (data=="used") {
                        alert("预约的镜像已被占用");
                    }else if(data=="booked"){
                        alert("您在该时间段已预约有镜像");
                    }else if(data=="fail"){
                        alert("预约出错");
                    }else{
                        alert("预约失败")
                    }
                }
            });

        }

        function VirtualLogin(id) {
            url="${pageContext.request.contextPath}/virtual/virtualLogin?virtualImageReservationid="+id;
            window.open(url);
            /*$.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/virtual/virtualLogin",
                data:{"virtualImageReservationid":id},
                dataType: 'text',
                success: function (data) {
                    if(data="success"){
                        alert("下载成功");
                    }else if(data=="fileError") {
                        alert("创建本地文件错误");
                    }else if(data=="runError"){
                        alert("运行错误");
                    }else{
                        alert("其他错误");
                    }
                }
            });*/
        }
    </script>
    <script type="text/javascript">

    </script>

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
          <li class="TabbedPanelsTab selected" id="s1">
              <a href="${pageContext.request.contextPath}/virtual/virtualImageReservation?currpage=1">虚拟镜像预约</a></li>
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
                    <div>
                        <%--<ul class="btn_reser cf">
                            <li ><a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=1">工位预约</a></li>
                            <li class="selected"><a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1">实训室预约</a></li>
                        </ul>
                    --%></div>
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
                                    因启动原因，只能预约10分钟后的镜像
                                </td>

                                <th>预约结束时间</th>

                                <td colspan="3">
                                     <input id="endTime" name="endTime" class="Wdate" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px;" />
                                </td>
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
                                    <input type="button" value="提交" class="btn btn-new" onclick="saveReservation()">
                                </td>
                            </tr>

                        </table>
                    </form>

                    <div class="right-content">
                        <div class="content-box">
                            <div class="tool-box">
                                <ul>
                                    <form:form name="form"
                                               action="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"
                                               method="post" modelAttribute="labRoom">
                                        <%-- <li><spring:message code="all.trainingRoom.labroom"/>名称：</li>
                                         <li><form:input path="labRoomName"/></li>
                                         <li><input type="submit" value="搜索"/></li>
                                         &lt;%&ndash;<li><input type="button" value="打印" id="print"></li>&ndash;%&gt;
                                         &lt;%&ndash;<li>
                                           <input type="button" value="导出" onclick="exportAll('${pageContext.request.contextPath}/lab/labAnnexListexportall?page=${currpage}');">
                                         </li>&ndash;%&gt;
                                         <li><input type="button" onclick="cancel()" value="取消"></li>--%>
                                     </form:form>
                                </ul>
                            </div>
                        </div>
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
                                    </tr>
                                </c:forEach>
                                <%--<tr>
                                    <th>镜像名称</th>
                                    <th>预约人</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>预约理由</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${listVirtualImage}" var="s" varStatus="i">
                                    <tr>
                                        <td>${s.virtualImage.name}</td>
                                        <td>${s.user.cname}</td>
                                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${s.startTime.time}" /></td>
                                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${s.endTime.time}" /></td>
                                        <td>${s.remarks}</td>
                                        <td>
                                            <a href="javascript:void(0)"
                                               onclick="VirtualLogin('${s.id}')">登陆</a>

                                        </td>
                                    </tr>
                                </c:forEach>--%>
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