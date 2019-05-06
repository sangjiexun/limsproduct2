<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <title><fmt:message key="html.title"/></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif"/>
</head>
<body style="overflow:hidden">
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">实验设备管理</a></li>
            <li class="end"><a href="javascript:void(0)">设备维修审核</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">设备维修申请单</div>
                    </div>
                    <table class="table table-bordered table-striped with-check" style="
                    word-break:break-all; word-wrap: break-word; text-align: center; vertical-align: center; horiz-align: center">
                        <tr>
                            <th width="10%">物资名称</th>
                            <td width="40%">
                                <label>${deviceRepair.deviceName}</label>
                            </td>
                            <th width="10%">实验室名称</th>
                            <td width="40%">
                                <label>${deviceRepair.labAddress}</label>
                            </td>
                        </tr>
                        <tr>
                            <th>报修人</th>
                            <td>
                                <label>${reportUser.cname}</label>
                            </td>
                            <th>预计金额</th>
                            <td>
                                <label>${deviceRepair.expectAmount}</label>
                            </td>
                        </tr>
                        <tr>
                            <th>报修描述(原因)</th>
                            <td>
                                <p>${deviceRepair.content}</p>
                            </td>
                            <th>设备价格</th>
                            <td>
                                <p>${devicePrice}</p>
                            </td>
                        </tr>
                        <tr>
                            <th>报修时间</th>
                            <td>
                                <fmt:formatDate value="${deviceRepair.createDate.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <th>创建人</th>
                            <td>
                                <label>${createUser.cname}</label>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="content-box">
                    <div class="title">
                        <div>设备维修确认</div>
                    </div>
                    <table>
                        <input type="hidden" name="confirmId" id="confirmId" value="${deviceRepair.id}"/>
                        <tr>
                            <th>确认金额</th>
                            <td>
                                <input type="number" name="confirmAmount" id="confirmAmount"/>
                            </td>
                        </tr>
                        <tr>
                            <th>确认内容</th>
                            <td colspan="4">
                                <textarea id="content" name="content" title="确认内容" style="height: 100px; width: 50%; box-sizing: border-box"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5"><input type="button" value="提交" onclick="saveWriteDeviceRepair()"></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    function saveWriteDeviceRepair() {
        var confirmAmount = $('#confirmAmount').val();
        if(confirmAmount == ""){
            alert("请填写确认金额！");
            return false;
        }
        var content = $('#content').val();
        if(content == ""){
            alert("请填写确认内容！");
            return false;
        }
        var id = $('#confirmId').val();
        var url = "${pageContext.request.contextPath}/deviceRepair/saveWriteDeviceRepair";
        $.ajax({
            url:url,
            type:"POST",
            data:{
                id: id,
                confirmAmount: confirmAmount,
                content: content
            },
            success:function () {
                alert("保存成功");
                window.location.href = "${pageContext.request.contextPath}/deviceRepair/deviceRepairConfirmList";
            },
            error:function () {
                alert("保存失败");
            }
        });
    }
</script>
</body>
<!-------------列表结束----------->
</html>