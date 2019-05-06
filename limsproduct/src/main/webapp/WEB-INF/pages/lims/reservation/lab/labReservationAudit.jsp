<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>实验室预约审核</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--父项目详情列表专属表头长度-->
    <style>
        .layui-form-label {
            width: 100px;
        }

        .layui-input-block {
            margin-left: 130px;
        }
        .layui-input-block input{
            margin-top: 7px;
        }
        input[type='text'] {
            width: 100%;
        }
        .timeline_tit {
            font-size: 19px;
            padding: 0 0 0px 10px;
            margin: 0 0 20px;
            border-left: 4px solid #409eff;
        }
        .layui-text h1, .layui-text h2, .layui-text h3 {
            font-weight: lighter;
            color: #333;
        }
        .layui-timeline-axis {
            color: #409eff;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>实验室预约审核</legend>
        </fieldset>
        <div class="layui-col-lg8">
            <form class="layui-form" action="" lay-filter="labReservationAudit">
                <input id="labRId" type="hidden" value="${labRId}"/>
                <input id="username" type="hidden" value="${username}"/>
                <input type="hidden" id="zuulServerUrl" value="${zuulServerUrl}" />
                <input type="hidden" id="auditServerUrl" value="${auditServerUrl}" />
                <input type="hidden" id="projectName" value="${projectName}" />
                <div class="detail_item">
                    <div class="layui-row">
                        <input type="hidden" id="grandSonId" value="${grandSonProject.id}" />
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">预约者</label>
                            <div class="layui-input-block">
                                <input type="text" name="cname" autocomplete="off"class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">所在学院</label>
                            <div class="layui-input-block">
                                <input type="text" name="academyName" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">导师</label>
                            <div class="layui-input-block">
                                <input type="text" name="teacherName" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">预约时间</label>
                            <div class="layui-input-block">
                                <input type="text" name="reservationDate" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">联系电话</label>
                            <div class="layui-input-block">
                                <input type="text" name="telephone" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">预约对象名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="userObjectName" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">预约原因</label>
                            <div class="layui-input-block">
                                <input type="text" name="reason" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">预约起始时间</label>
                            <div class="layui-input-block">
                                <input type="text" name="startTime" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">	预约结束时间</label>
                            <div class="layui-input-block">
                                <input type="text" name="endTime" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">活动类别</label>
                            <div class="layui-input-block">
                                <input type="text" name="eventType" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">活动名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="eventName" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg4">
                            <label class="layui-form-label">活动人数</label>
                            <div class="layui-input-block">
                                <input type="text" name="number" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">实验室名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="labRoomName" autocomplete="off" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>




                    </div>
                <div class="layui-row layui-col-space10 layui-form-item" style="margin-top:20px;">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">审核意见：</label>
                        <div class="layui-input-block switch_btn">
                            <div class="layui-tab">
                                <ul class="layui-tab-title">
                                    <li class="layui-this">
                                        <input type="radio" lay-filter="redioF"  name="gpexamine" value="1" title="通过" checked="" />
                                    </li>
                                    <li>
                                        <input type="radio" lay-filter="redioF" name="gpexamine" value="2" title="不通过" />
                                    </li>
                                </ul>
                                <div class="layui-tab-content">
                                    <div class="layui-tab-item"></div>
                                    <div class="layui-tab-item layui-show">
                                        <textarea style="display: none;" class="layui-textarea" id="textRemark"   name="remark" rows="3" placeholder="请填写实验室预约审核不通过的原因"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">

                        <button class="layui-btn" lay-submit lay-filter="submitAudit">立即提交</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-col-lg4 timeline_box">
            <div class="timeline_tit">审核流转记录</div>
            <div id="timelineTimetable"></div>
            <%--<ul class="layui-timeline scrollbar">--%>
            <%--</ul>--%>
        </div>
    </div>
</div>
<script>
    $(function(){
     <%--showDetail({--%>
         <%--parentProject: ${parentProject},--%>
     <%--})--%>
        <%--console.log(${parentProject});--%>
    });
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/labReservationAudit.js"></script>
</body>

</html>