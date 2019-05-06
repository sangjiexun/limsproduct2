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
                            <td colspan="3">
                                <p>${deviceRepair.content}</p>
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
                        <div>设备维修审核</div>
                    </div>
                    <table>
                        <tr>
                            <th>当前审核级别：</th>
                            <td>
                                ${authCName}审核
                            </td>
                        </tr>
                        <tr>
                            <td>审核：</td>
                            <td colspan="4">
                                <input type="radio" name="auditResult" value="1" checked="true"/>通过
                                <input type="radio" name="auditResult" value="0"/>拒绝
                            </td>
                        </tr>
                        <tr>
                            <td>审核意见：</td>
                            <td colspan="4"><!--<input type="text" id="remark" style="width:111px;height:111px"/>-->
                                <textarea name="" id="remark" style="vertical-align:top;outline:none;width:1500px;height:200px"></textarea></td>
                        </tr>
                        <tr>
                            <td colspan="5"><input type="button" value="提交" onclick="saveDeviceRepairAudit()"></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    function saveDeviceRepairAudit() {
        var auditResult = $('input:radio[name="auditResult"]:checked').val();
        var remark = $("#remark").val();
        if(remark==null||remark==""&&$('input[name="auditResult"]:checked').val()==0){
            alert("请填写审核意见");
        }else{
            var url = "${pageContext.request.contextPath}/deviceRepair/saveDeviceRepairAudit?id=${deviceRepair.id}&auditResult=" + auditResult + "&remark=" + remark;
            window.location.href = url;
        }
    }
</script>
</body>
<!-------------列表结束----------->
</html>