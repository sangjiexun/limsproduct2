<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta name="decorator" content="iframe" />
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
    <!-- 打印开始 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <!-- 打印结束 -->
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/timetable/lmsReg.css">

    <script type="text/javascript">
        var temUrl = "";
        //跳转
        function targetUrl(url)
        {
            temUrl = document.queryForm.action;
            document.queryForm.action=url;
            document.queryForm.submit();
            document.queryForm.action=temUrl;
        }

        $(document).ready(function(){
            $('#fullview').click(function(){
                $('.sit_sider_bar').animate( { width:'0'}, 500 );
                $('.sit_maincontent').animate( { width:'100%'}, 500 );
                $('.toggle,.toggleLink,#fullview,.sit_footer,.sit_sider_bar > h3').css("display","none");
                $('#fullview1').css("display","inline");
            });

            $('#fullview1').click(function(){
                $('.sit_sider_bar').animate( { width:'23%'}, 500 );
                $('.sit_maincontent').animate( { width:'75%'}, 500 );
                $('#fullview1').css("display","none");
                $('.toggle,#fullview,.toggleLink,.sit_footer,.sit_sider_bar > h3 ').css("display","inline");
            });

            $('#myPrint').click(function(){
                $('#myShow').jqprint();
            });
        });

        $(function(){
            var height = $(document).height();
            $(".sit_sider_bar").css('height',height);
            $(".sit_maincontent").css('height',height);
        }) ;

        function getLabRoom(){
            var termId = $("#term").val();
            $.ajax({
                async: false,
                type: "POST",
                url: "${pageContext.request.contextPath}/report/findLabRoomByLabCenter?termId="+termId,
                data: {'labCenter':$("#labCenter").val()},
                success:function(data){
                    $("#labRoom").html(data.labroom);
                    $("#labRoom").trigger("liszt:updated");
                }
            });
        }

    </script>
    <style type="text/css">
        .chzn-choices {
            border: none;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.report.labtype" /></a></li>
        </ul>
    </div>
</div>

<div id="TabbedPanels1" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab1 selected" id="s1">
            <a href="javascript:void(0)">实验类型统计表</a>
        </li>
        <a href="#" onclick="targetUrl('${pageContext.request.contextPath}/report/exportOperationItemType')"><input class="btn btn-new" type="button" value="导出"></a>
        <input class="btn btn-new" id="myPrint" value="打印" type="button" />
    </ul>
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">

            <div class="content-box">
                <%--<div class="title">--%>
                    <%--<div id="title">实验类型统计表</div>--%>
                    <%--<a href="#" onclick="targetUrl('${pageContext.request.contextPath}/report/exportOperationItemType')"><input class="btn btn-new" type="button" value="导出"></a>--%>
                    <%--<input class="btn btn-new" id="myPrint" value="打印" type="button" />--%>
                <%--</div>--%>
                <div class="tool-box">
                    <table border="0" cellpadding="5" cellspacing="0" bgcolor="#F3EFEE" height="30" width="100%">
                        <tbody>
                        <form name="queryForm" method="Post" action="${pageContext.request.contextPath}/report/reportOperationItemType?currpage=1">
                            <ul>
                                <li>学期:
                                    <select class="chzn-select" name="term" id="term">
                                        <c:forEach items="${schoolTermMap}" var="item">
                                            <c:if test="${item.key eq term}">
                                                <option value="${item.key }"  selected="selected">${item.value}</option>
                                            </c:if>
                                            <c:if test="${item.key ne term}">
                                                <option value="${item.key }">${item.value}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>

                                <li>实验中心:
                                    <select class="chzn-select" name="centerId" id="labCenter" onchange="getLabRoom();">
                                        <option value="0">全部实验中心</option>
                                        <c:forEach items="${labCenterMap}" var="item">
                                            <c:if test="${item.key eq centerId}">
                                                <option value="${item.key }"  selected="selected">${item.value}</option>
                                            </c:if>
                                            <c:if test="${item.key ne centerId}">
                                                <option value="${item.key }">${item.value}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>

                                <li>实验室:
                                    <select class="chzn-select" path="labRoom.id" name="roomId" id="labRoom" style="width:300px" multiple="multiple" data-placeholder="未选择实验室">
                                        <option value="0">全部实验室</option>
                                        <c:forEach items="${labRoomMap}" var="item">
                                            <option value="${item.key}">${item.value}</option>
                                        </c:forEach>
                                        <c:forEach var="curr" items="${labRoomSelectedList}">
                                            <option selected="selected" value="${curr.key}">${curr.value}</option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    <input type="submit" value="查询"/>
                                    <a href="${pageContext.request.contextPath}/report/reportOperationItemType?currpage=1"><input class="cancel-submit" type="button" value="取消"></input></a>
                                </li><br>
                            </ul>
                        </form>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="content-box">
                <div id="myShow">
                    <style type="text/css">
                        td{border:solid 1px #4F4F4F !important;}
                    </style>
                    <form name="form" method="Post" action="${pageContext.request.contextPath}/report/reportOperationItemType?currpage=1">
                        <table class="tb" id="my_show">
                            <thead style="center-content">
                            <tr>
                                <%--<th colspan="25">--%>
                                <%--</th>--%>
                            </tr>
                            <tr>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="3">序号</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="3">单位编号</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="3">单位名称</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">实开实验</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="4">演示</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="4">验证</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="4">综合</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="4">设计研究</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="4">其他</th>
                            </tr>
                            <tr>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">占总实验(%)</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">占总实验(%)</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">占总实验(%)</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">占总实验(%)</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " rowspan="2">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% " colspan="2">占总实验(%)</th>
                            </tr>
                            <tr>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">个数</th>
                                <th style="border:solid 1px #4F4F4F !important;" width= "3% ">时数</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:if test="${items.size() ne 1 }"> <!-- 当选择的是某一个具体中心时没有必要进行这些操作 -->
                                <!-- 计算整个学院的五种类型的分别的总个数和总时数 -->
                                <c:set var="count1College" value="0"></c:set>
                                <c:set var="hour1College" value="0"></c:set>
                                <c:set var="count2College" value="0"></c:set>
                                <c:set var="hour2College" value="0"></c:set>
                                <c:set var="count3College" value="0"></c:set>
                                <c:set var="hour3College" value="0"></c:set>
                                <c:set var="count4College" value="0"></c:set>
                                <c:set var="hour4College" value="0"></c:set>
                                <c:set var="count5College" value="0"></c:set>
                                <c:set var="hour5College" value="0"></c:set>
                                <c:forEach items="${items}" var="curr" varStatus="i">
                                    <c:set var="count1College" value="${count1College+curr.count1 }"></c:set>
                                    <c:set var="hour1College" value="${hour1College+curr.hour1 }"></c:set>
                                    <c:set var="count2College" value="${count2College+curr.count2 }"></c:set>
                                    <c:set var="hour2College" value="${hour2College+curr.hour2 }"></c:set>
                                    <c:set var="count3College" value="${count3College+curr.count3 }"></c:set>
                                    <c:set var="hour3College" value="${hour3College+curr.hour3 }"></c:set>
                                    <c:set var="count4College" value="${count4College+curr.count4 }"></c:set>
                                    <c:set var="hour4College" value="${hour4College+curr.hour4 }"></c:set>
                                    <c:set var="count5College" value="${count5College+curr.count5 }"></c:set>
                                    <c:set var="hour5College" value="${hour5College+curr.hour5 }"></c:set>
                                </c:forEach>
                            </c:if>

                            <c:forEach items="${items}" var="curr" varStatus="i">
                                <c:if test="${items.size() ne 1 }"> <!-- 当选择的是某一个具体中心时没有必要进行这些操作 -->
                                    <c:if test="${i.count eq 1}">
                                        <tr>
                                            <td>1</td>
                                            <td>clw201807</td>
                                            <td>clw庚商学院</td>
                                            <td>${curr.countTotalCollege }</td>
                                            <td>${curr.hourTotalCollege }</td>
                                            <td>${count1College }</td>
                                            <td>${hour1College }</td>
                                            <td><fmt:formatNumber type="number" value="${count1College/curr.countTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${hour1College/curr.hourTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td>${count2College }</td>
                                            <td>${hour2College }</td>
                                            <td><fmt:formatNumber type="number" value="${count2College/curr.countTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${hour2College/curr.hourTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td>${count3College }</td>
                                            <td>${hour3College }</td>
                                            <td><fmt:formatNumber type="number" value="${count3College/curr.countTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${hour3College/curr.hourTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td>${count4College }</td>
                                            <td>${hour4College }</td>
                                            <td><fmt:formatNumber type="number" value="${count4College/curr.countTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${hour4College/curr.hourTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td>${count5College }</td>
                                            <td>${hour5College }</td>
                                            <td><fmt:formatNumber type="number" value="${count5College/curr.countTotalCollege*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${hour5College/curr.hourTotalCollege*100}" maxFractionDigits="2"/></td>
                                        </tr>
                                    </c:if>
                                </c:if>
                                <tr>
                                    <td>
                                        <c:if test="${items.size() ne 1 }">
                                            ${i.count+1 }
                                        </c:if>
                                        <c:if test="${items.size() eq 1 }">
                                            ${i.count }
                                        </c:if>
                                    </td>
                                    <td>${curr.centerNumber }</td>
                                    <td>${curr.centerName }</td>
                                    <td>${curr.countTotal }</td>
                                    <td>${curr.hourTotal }</td>
                                    <td>${curr.count1 }</td>
                                    <td>${curr.hour1 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count1/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour1/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count2 }</td>
                                    <td>${curr.hour2 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count2/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour2/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count3 }</td>
                                    <td>${curr.hour3 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count3/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour3/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count4 }</td>
                                    <td>${curr.hour4 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count4/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour4/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count5 }</td>
                                    <td>${curr.hour5 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count5/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour5/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                </tr>
                            </c:forEach>
                            <c:forEach items="${item}" var="curr" varStatus="i">
                                <c:if test="${item.size() eq 1 }"> <!-- 当选择的是某一个具体中心时进行这些操作 -->
                                    <c:if test="${i.count eq 1}">
                                        <tr>
                                            <td>1</td>
                                            <td>${curr.centerNumber}</td>
                                            <td>${curr.centerName}</td>
                                            <td>${curr.countTotal}</td>
                                            <td>${curr.hourTotal}</td>
                                            <td>${curr.centerCount1}</td>
                                            <td>${curr.centerHour1}</td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerCount1/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerHour1/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                            <td>${curr.centerCount2 }</td>
                                            <td>${curr.centerHour2 }</td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerCount2/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerHour2/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                            <td>${curr.centerCount3 }</td>
                                            <td>${curr.centerHour3 }</td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerCount3/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerHour3/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                            <td>${curr.centerCount4}</td>
                                            <td>${curr.centerHour4 }</td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerCount4/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerHour4/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                            <td>${curr.centerCount5}</td>
                                            <td>${curr.centerHour5 }</td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerCount5/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                            <td><fmt:formatNumber type="number" value="${curr.centerHour5/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                        </tr>
                                    </c:if>
                                </c:if>
                                <tr>
                                    <td>
                                        <c:if test="${item.size() eq 1 }">
                                            ${i.count+1 }
                                        </c:if>
                                        <c:if test="${item.size() ne 1 }">
                                            ${i.count }
                                        </c:if>
                                    </td>
                                    <td>${curr.labRoomNumber }</td>
                                    <td>${curr.labRoomName }</td>
                                    <td>${curr.countTotalLab }</td>
                                    <td>${curr.hourTotalLab }</td>
                                    <td>${curr.count1 }</td>
                                    <td>${curr.hour1 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count1/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour1/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count2 }</td>
                                    <td>${curr.hour2 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count2/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour2/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count3 }</td>
                                    <td>${curr.hour3 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count3/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour3/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count4 }</td>
                                    <td>${curr.hour4 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count4/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour4/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                    <td>${curr.count5 }</td>
                                    <td>${curr.hour5 }</td>
                                    <td><fmt:formatNumber type="number" value="${curr.count5/curr.countTotal*100}" maxFractionDigits="2"/></td>
                                    <td><fmt:formatNumber type="number" value="${curr.hour5/curr.hourTotal*100}" maxFractionDigits="2"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </form>
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