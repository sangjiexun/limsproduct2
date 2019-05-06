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
                            <th>验收：</th>
                            <td>
                                <select name="result" id="result">
                                    <option value="">请选择</option>
                                    <option value="1">已维修</option>
                                    <option value="2">未维修</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>备注：</th>
                            <td colspan="4"><!--<input type="text" id="remark" name="remark"/> -->
                                <textarea name="" id="remark" style="vertical-align:top;outline:none;width:1500px;height:200px"></textarea></td>
                        </tr>
                        <tr>
                            <td colspan="5"><input type="button" value="提交" onclick="saveAcceptanceDeviceRepair()"></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    function saveAcceptanceDeviceRepair() {
        var result = $('#result').val();
        if(result == ""){
            alert("请选择维修与否！");
            return false;
        }
        var remark = $('#remark').val();
        var id = $('#confirmId').val();
        var url = "${pageContext.request.contextPath}/deviceRepair/saveAcceptanceDeviceRepair";
        $.ajax({
            url:url,
            type:"POST",
            data:{
                id: id,
                result: result,
                remark: remark
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