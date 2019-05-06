<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta name="decorator" content="iframe"/>
    <style type="text/css">
        #Main{
            width: 1200px;
            height: 500px;
            overflow:hidden;
            margin: 0 auto;
            margin-top: 50px;

        }
        #mainDiv {
            width:150%;
            height:500px;
            margin-top:-20px;
            margin-left:-300px;
        }
    </style>
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/amcharts.js" type="text/javascript"></script>
    <script type="text/javascript">
        window.onload = function () {
            MakeChart(json);
        }
        var json = [
            { "name": "每个房间门口挂有安全信息牌，信息包括安全责任人、涉及危险类别、防护措施和有效的应急联系电话等，并及时更新", "value": "1" },
            { "name": "特殊实验室应张贴相应的安全警示标识", "value": "1" },
            { "name": "实验室消防通道通畅", "value": "1" },
            { "name": "门上有可视窗", "value": "1" },
            { "name": "不安装额外的铁栏栅门（特殊情况除外）", "value": "1" },
            { "name": "除一楼之外不安装防盗窗（特殊情况除外）", "value": "1" },
            { "name": "公共场所、通道无堆放仪器、物品现象", "value": "1" },
            { "name": "所有房间的钥匙有备用，存放在单位办公室或传达室内，由专人管理", "value": "1" }
        ]

        //根据json数据生成饼状图，并将饼状图显示到div中
        function MakeChart(value) {
            chartData = eval(value);
            //饼状图
            chart = new AmCharts.AmPieChart();

            chart.dataProvider = chartData;
            //标题数据
            chart.titleField = "name";
            //值数据
            chart.valueField = "value";
            //边框线颜色
            chart.outlineColor = "#fff";
            //边框线的透明度
            chart.outlineAlpha = .8;
            //边框线的狂宽度
            chart.outlineThickness = 1;
            chart.depth3D = 20;
            chart.angle = 30;
            chart.write("mainDiv");
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li>实验室评价</li>
            <li class="end">评价图片详细信息</li>
            <a class="btn btn-return" href="${pageContext.request.contextPath}/inspect/listInspectGrading?currpage=1">返回</a>
        </ul>
    </div>
</div>

<div class="content-box">
    <table>
        <tr>
            <th>实验室名称：</th><td>${commonDocument.labRoom.labRoomName}</td>
            <th>评价人：</th><td>${commonDocument.user.cname}</td>
        </tr>
        <th>评价意见：</th><td>${commonDocument.comments}</td>
        <th>上传时间：</th><td><fmt:formatDate value="${commonDocument.createdAt.time}" pattern="yyyy-MM-dd"/></td>

        <tr>
            <th>批次：</th><td>${batch.title}</td>
            <th>检查名称：</th><td>${batch.comment}</td>
        </tr>
        <tr>
            <td colspan = " 3 ">标准</td>
            <td>分数</td>

        </tr>
        <c:forEach items="${objects}" var="object">
            <tr>
                <td colspan = " 3 ">${object[1]}</td>
                <td>${object[0]}</td>
            </tr>
        </c:forEach>
        <tr>
            <th>图片：</th><td><img src="${pageContext.request.contextPath}${commonDocument.documentUrl}" style="width:400px;"/></td>
        </tr>
    </table>
</div>
<div id="mainDiv">

</div>
</body>
</html>
