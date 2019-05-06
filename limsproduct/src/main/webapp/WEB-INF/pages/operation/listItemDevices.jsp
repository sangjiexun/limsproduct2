<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
        function checkAll() {
            if($("#check_all").attr("checked")) {
                $(":checkbox").attr("checked", true);
            }else {
                $(":checkbox").attr("checked", false);
            }
        }

        //取消查询
        function cancel() {
            window.location.href="${pageContext.request.contextPath}/operation/listItemDevices?currpage=1&itemId=${itemId}";
        }
    </script>
    <script type="text/javascript">
        // 查询
        function query() {
            document.queryForm.action="${pageContext.request.contextPath}/operation/listItemDevices?itemId=${itemId}&currpage=1";
            document.queryForm.submit();
        }
        //跳转
        function targetUrl(url) {
            document.queryForm.action=url;
            document.queryForm.submit();
        };
        //保存实验项目设备
        function submitForm() {
            var flag = false;  //是否有checkbox被选中
            var ids = "";
            $("input[name='itemDevice']:checked").each(function(){
                ids += $(this).val()+",";
                flag = true;
            });

            if(flag) {
                if(ids.length > 0) {
                    ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
                }
                $.ajax({
                    type:"POST",
                    url:"${pageContext.request.contextPath}/operation/saveItemDeviceLims",
                    data:{itemId:${itemId},ids:ids},
                    success:function(data){
                        window.parent.location.reload();
                    }
                });
            }
            else
            {
                alert("至少选择一个设备！");
            }
        }
    </script>
</head>

<body>
<div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
            <div class="content-box">
                <div class="tool-box">
                    <form name="queryForm" action="${pageContext.request.contextPath}/operation/listItemDevices" method="post">
                        <ul>
                            <li>实验室/设备名称:</li>
                            <li><input type="text" id="lp_name" name="param"/></li>
                            <li>
                                <input type="button" value="取消" onclick="cancel();"/>
                                <input class="cancel-submit" type="submit" onclick="query();return false;" value="查询"/>
                            </li>
                        </ul>
                    </form>
                </div>

                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <th><input id="check_all" type="checkbox" onclick="checkAll();"/>
                            <input type="button" value="保存" onclick="submitForm();"/></th>
                        <th>设备编号</th>
                        <th>设备名称</th>
                        <th>所属<spring:message code="all.trainingRoom.labroom"/></th>
                        <th>规格</th>
                        <th>型号</th>
                        <th>单价</th>
                        <th>地点</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${labRoomDevices}" var="curr">
                        <tr>
                            <td><input id="device_${curr.id}" name="itemDevice" type="checkbox" value="${curr.id}"/></td>
                            <td>${curr.schoolDevice.deviceNumber}</td>
                            <td>${curr.schoolDevice.deviceName}</td>
                            <td>${curr.labRoom.labRoomName}</td>
                            <td>${curr.schoolDevice.deviceFormat}</td>
                            <td>${curr.schoolDevice.devicePattern}</td>
                            <td>${curr.schoolDevice.devicePrice}</td>
                            <td>${curr.schoolDevice.deviceAddress}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页 -->
                <div class="page" >
                    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/listItemDevices?currpage=1&itemId=${itemId}')" target="_self">首页</a>
                    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/operation/listItemDevices?currpage=${pageModel.previousPage}&itemId=${itemId}')" target="_self">上一页</a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/operation/listItemDevices?currpage=${pageModel.currpage}&itemId=${itemId}">${pageModel.currpage}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=pageModel.currpage}">
                            <option value="${pageContext.request.contextPath}/operation/listItemDevices?currpage=${j.index}&itemId=${itemId}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/listItemDevices?currpage=${pageModel.nextPage}&itemId=${itemId}')" target="_self">下一页</a>
                    <a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/operation/listItemDevices?currpage=${pageModel.lastPage}&itemId=${itemId}')" target="_self">末页</a>
                </div>
            </div>
        </div>
    </div>
</div>
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
</body>
</html>
