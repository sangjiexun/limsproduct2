<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false"
         contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/iconFont.css"
          rel="stylesheet">
    <!-- 点击框的样式 -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/css/jquery.coolautosuggest.css"/>
    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.coolautosuggest.js"></script>
    <!-- 打印插件的引用 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <script type="text/javascript">
        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/report/experimentTeaching?currpage=1"
        }
    </script>
    <script>
        //自动合并表格js代码
        window.onload = function () {
            init(table1, 6);
        };

        function init(tb, colLength) {

            if (!checkTable(tb)) return;
            var i = 0;
            var j = 0;
            var rowCount = tb.rows.length; //行数
            var colCount = tb.rows[0].cells.length; //列数
            var obj1 = null;
            var obj2 = null;
            var setcol = 3;
            //为每个单元格命名
            for (i = 0; i < rowCount; i++) {
                for (j = 0; j < colCount; j++) {
                    tb.rows[i].cells[j].id = "tb__" + i.toString() + "_" + j.toString();
                }
            }
            //逐列检查合并
            for (i = 0; i < setcol; i++) {
                if (i == colLength) return;
                obj1 = document.getElementById("tb__0_" + i.toString())
                for (j = 1; j < rowCount; j++) {
                    obj2 = document.getElementById("tb__" + j.toString() + "_" + i.toString());
                    if (obj1.innerText == obj2.innerText) {
                        obj1.rowSpan++;
                        obj2.parentNode.removeChild(obj2);
                    } else {
                        obj1 = document.getElementById("tb__" + j.toString() + "_" + i.toString());
                    }
                }
            }
        }

        //功能：检查表格是否规整
        //参数：tb－－需要检查的表格ID
        function checkTable(tb) {
            if (tb.rows.length == 0) return false;
            if (tb.rows[0].cells.length == 0) return false;
            for (var i = 0; i < tb.rows.length; i++) {
                if (tb.rows[0].cells.length != tb.rows[i].cells.length) return false;
            }
            return true;
        }
    </script>
    <style>
        .content-box .lab_message tr td {
            border: none;
            width: 12.5%;
            padding: 0;
            margin: 0;
        }

        .tool-box ul li {
            font-weight: bold;
        }

        .total {
            background: #f3efef;
            margin-top: 5px;
        }

        .total ul {
            width: 100%;
            margin-left: 10px;
        }

        .total ul li {
            margin-left: 1%;
        }
    </style>

</head>
<body>
<!--导航  -->
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.report.labhourssdu" /></a></li>
        </ul>
    </div>
</div>
<!--导航结束  -->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)">实验教学情况表</a>
            </li>
        </ul>
        <div class="TabbedPanelsContentGroup1">
            <div class="TabbedPanelsContent">
                <div class="content-box">
        <div class="tool-box">
            <ul>
                <form name="form" action="${pageContext.request.contextPath}/report/experimentTeaching?currpage=1"
                      method="post">
                    <li>
                        <label>所属学期:</label>
                        <select name="termId">
                            <option value="">2017-2018学年第二学期</option>

                        </select>
                    </li>

                    <li><input type="submit" value="查询">
                        <a href="${pageContext.request.contextPath}/report/exportOperationItemReport"><input type="button" value="导出"></a></li>
                    <%--<li><input type="button" value="打印"></li>--%>
                </form>
            </ul>
        </div>
        </div>
        <div class="content-box">
            <table id="table1" >
                <thead>
                <tr>
                    <th>序号</th>
                    <th>院部</th>
                    <th>专业数</th>
                    <th>实验课程数</th>
                    <th>实验项目数</th>
                    <th>实验学时数</th>
                    <th>实验学生数</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>金贸学院</td>
                        <td>3</td>
                        <td>20</td>
                        <td>122</td>
                        <td>674</td>
                        <td>2400</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>管理学院</td>
                        <td>5&nbsp;</td>
                        <td>10</td>
                        <td>62</td>
                        <td>344</td>
                        <td>960</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>商学院</td>
                        <td>5</td>
                        <td>18</td>
                        <td>91</td>
                        <td>416</td>
                        <td>1380</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>文法学院</td>
                        <td>1</td>
                        <td>4</td>
                        <td>45</td>
                        <td>160</td>
                        <td>240</td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>外语学院</td>
                        <td>2</td>
                        <td>2</td>
                        <td>10</td>
                        <td>68</td>
                        <td>120</td>
                    </tr>
                    <tr>
                        <td>合计</td>
                        <td>5</td>
                        <td>16</td>
                        <td>54</td>
                        <td>330</td>
                        <td>1662</td>
                        <td>5100</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>
    </div>
    </div>
</div>
<script type="text/javascript">
    //首页
    function first(){
        document.form.action="${pageContext.request.contextPath}/report/experimentTeaching?currpage=1";
        document.form.submit();
    }

    function previous(){
        var page=$("#currpage").val();
        if(page==1){
            page=1;
        }else{
            page=page-1;
        }
        document.form.action="${pageContext.request.contextPath}/report/experimentTeaching?currpage="+page;
        document.form.submit();
    }

    function next(){
        var totalpage=$("#totalpage").val();
        var page=parseInt($("#currpage").val());
        if(page==totalpage){
            page=totalpage;
        }else{
            page=page+1;
        }
        document.form.action="${pageContext.request.contextPath}/report/experimentTeaching?currpage="+page;
        document.form.submit();
    }

    function last(){
        var page=$("#totalpage").val();
        var tage=parseInt($("#tage").val());
        document.form.action="${pageContext.request.contextPath}/report/experimentTeaching?currpage="+page;
        document.form.submit();
    }
</script>
</body>