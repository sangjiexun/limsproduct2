<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf8">
    <title>孙项目审核</title>
    <meta name="decorator" content="timetable"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/construction/expand.css" media="all">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/jquery-1.11.0.min.js"></script>
    <!--孙项目详情列表专属表头长度-->
    <style>
        .layui-form-label {
            width: 105px;
        }

        .layui-input-block {
            margin-left: 135px;
        }
    </style>
</head>

<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-main">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>（${grandSonProject.projectName}）审核</legend>
        </fieldset>
        <div class="layui-col-lg8">
            <form class="layui-form" action="" lay-filter="examinegrandsonproject">
                <div class="detail_item">
                    <div class="layui-row">
                        <input type="hidden" id="grandSonId" value="${grandSonProject.id}" />
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">孙项目名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="projectName" autocomplete="off" value="${grandSonProject.projectName}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">所属子项目</label>
                            <div class="layui-input-block">
                                <input type="text" name="sonProjectName" autocomplete="off" value="${grandSonProject.sonProjectName}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">所属父项目</label>
                            <div class="layui-input-block">
                                <input type="text" name="parentProjectName" autocomplete="off" value="${grandSonProject.parentProjectName}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">创建人</label>
                            <div class="layui-input-block">
                                <input type="text" name="createUser" autocomplete="off" value="${grandSonProject.createUser}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">创建人所属部门</label>
                            <div class="layui-input-block">
                                <input type="text" name="unitName" autocomplete="off" value="${grandSonProject.unitName}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">创建时间</label>
                            <div class="layui-input-block">
                                <input type="text" name="createTime" autocomplete="off" value="${grandSonProject.createTime}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">经费预算</label>
                            <div class="layui-input-block">
                                <input type="text" name="budget" autocomplete="off" value="${grandSonProject.budget}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                        <div class="layui-col-lg6">
                            <label class="layui-form-label">项目实施时间</label>
                            <div class="layui-input-block">
                                <input type="text" name="projectImplementTime" autocomplete="off" value="${grandSonProject.projectImplementTime}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">相关文档</label>
                            <c:if test="${!empty grandSonProject.relatedDocuments}">
                                <c:forEach items="${grandSonProject.relatedDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.relatedDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                            <%--<div class="layui-input-block">--%>
                            <%--<a class="layui-input file_download" href="tagbox.js" download="tagbox.js" title="下载">新提交文件名称.txt</a>--%>
                            <%--</div>--%>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">论证报告</label>
                            <c:if test="${!empty grandSonProject.reportDocuments}">
                                <c:forEach items="${grandSonProject.reportDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.reportDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                            <%--<div class="layui-input-block">--%>
                            <%--<a class="layui-input file_download" href="tagbox.js" download="tagbox.js" title="下载">新提交文件名称.txt</a>--%>
                            <%--</div>--%>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">采购文件</label>
                            <c:if test="${!empty grandSonProject.purchaseDocuments}">
                                <c:forEach items="${grandSonProject.purchaseDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.purchaseDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">相关合同</label>
                            <c:if test="${!empty grandSonProject.relatedContracts}">
                                <c:forEach items="${grandSonProject.relatedContracts}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.relatedContracts}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">招标纪要实际金额</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" name="tenderActualAmount" autocomplete="off" value="${grandSonProject.tenderActualAmount}" class="layui-input" disabled="disabled" readonly="readonly" />
                            </div>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-lg12">
                            <label class="layui-form-label">验收申请表</label>
                            <c:if test="${!empty grandSonProject.acceptanceDocuments}">
                                <c:forEach items="${grandSonProject.acceptanceDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.acceptanceDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </div>
                        <div class="layui-row">
                            <div class="layui-col-lg12">
                                <label class="layui-form-label">实际金额</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="acceptanceActualAmount" autocomplete="off" value="${grandSonProject.acceptanceActualAmount}" class="layui-input" disabled="disabled" readonly="readonly" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-row layui-col-space10 layui-form-item" style="margin-top:20px;">
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">新提交文件：</label>
                        <c:if test="${grandSonProject.progressStage == 1}">
                            <c:if test="${!empty grandSonProject.relatedDocuments}">
                                <c:forEach items="${grandSonProject.relatedDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.relatedDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </c:if>
                        <c:if test="${grandSonProject.progressStage == 2}">
                            <c:if test="${!empty grandSonProject.reportDocuments}">
                                <c:forEach items="${grandSonProject.reportDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.reportDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </c:if>
                        <c:if test="${grandSonProject.progressStage == 3}">
                            <c:if test="${!empty grandSonProject.purchaseDocuments}">
                                <c:forEach items="${grandSonProject.purchaseDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.purchaseDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </c:if>
                        <c:if test="${grandSonProject.progressStage == 4}">
                            <c:if test="${!empty grandSonProject.relatedContracts}">
                                <c:forEach items="${grandSonProject.relatedContracts}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.relatedContracts}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </c:if>
                        <c:if test="${grandSonProject.progressStage == 5}">
                            <c:if test="${!empty grandSonProject.acceptanceDocuments}">
                                <c:forEach items="${grandSonProject.acceptanceDocuments}" var="map">
                                    <div class="layui-input-block">
                                        <a class="layui-input file_download" href="${pageContext.request.contextPath}${map.value}" download="${map.key}" title="下载">${map.key}</a>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty grandSonProject.acceptanceDocuments}">
                                <div class="layui-input-block"></div>
                            </c:if>
                        </c:if>
                        <%--<div class="layui-input-block">--%>
                            <%--<a class="layui-input file_download" href="tagbox.js" download="tagbox.js" title="下载">新提交文件名称.txt</a>--%>
                        <%--</div>--%>
                    </div>
                    <div class="layui-col-lg12">
                        <label class="layui-form-label">文件审核：</label>
                        <div class="layui-input-block switch_btn">
                            <div class="layui-tab">
                                <ul class="layui-tab-title">
                                    <li class="layui-this">
                                        <input type="radio"  name="gpexamine" value="1" title="通过" checked="" />
                                    </li>
                                    <li>
                                        <input type="radio" name="gpexamine" value="2" title="不通过" />
                                    </li>
                                </ul>
                                <div class="layui-tab-content">
                                    <div class="layui-tab-item layui-show"></div>
                                    <div class="layui-tab-item">
                                        <textarea class="layui-textarea"   name="remark" rows="3" placeholder="请填写文件审核不通过的原因"></textarea>
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
            <div class="timeline_tit">孙项目流程线</div>
            <ul class="layui-timeline scrollbar">
                <c:forEach items="${grandSonProject.processLine}" var="curr">
                    <c:if test="${curr[1]!='完成所有阶段'}">
                        <c:if test="${curr[0]==0}">
                            <li class="layui-timeline-item upload passed">
                                <i class="layui-icon layui-timeline-axis"></i>
                                <div class="layui-timeline-content layui-text">
                                    <div class="layui-timeline-title">${curr[1]}</div>
                                </div>
                            </li>
                        </c:if>
                        <c:if test="${curr[0]==1}">
                            <li class="layui-timeline-item success passed">
                                <i class="layui-icon layui-timeline-axis"></i>
                                <div class="layui-timeline-content layui-text">
                                    <div class="layui-timeline-title">${curr[1]}</div>
                                </div>
                            </li>
                        </c:if>
                        <c:if test="${curr[0]==2}">
                            <li class="layui-timeline-item notpass passed">
                                <i class="layui-icon layui-timeline-axis"></i>
                                <div class="layui-timeline-content layui-text">
                                    <div class="layui-timeline-title">${curr[1]}</div>
                                </div>
                            </li>
                        </c:if>
                        <c:if test="${curr[0]==3}">
                            <li class="layui-timeline-item in_audit passed">
                                <i class="layui-icon layui-timeline-axis"></i>
                                <div class="layui-timeline-content layui-text">
                                    <div class="layui-timeline-title">${curr[1]}</div>
                                </div>
                            </li>
                        </c:if>
                    </c:if>
                    <c:if test="${curr[1]=='完成所有阶段'}">
                        <li class="layui-timeline-item complete">
                            <i class="layui-icon layui-timeline-axis"></i>
                            <div class="layui-timeline-content layui-text">
                                <div class="layui-timeline-title">${curr[1]}</div>
                            </div>
                        </li>
                    </c:if>
                </c:forEach>
                <%--<li class="layui-timeline-item upload passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">【上传人】正在准备上传文件</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item upload passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">【上传人】正在准备上传文件</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item in_audit passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">2019-01-01【上传人】文件上传成功，正在审核...</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item notpass passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">2019-01-02【审核人】文件审核不通过--%>
                            <%--<textarea class="layui-textarea notpass_textarea" rows="3" disabled="disabled" readonly="readonly">不通过的原因</textarea>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item upload passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">等待【上传人】再次上传文件</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item in_audit passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">2019-01-03【上传人】文件上传成功，正在审核...</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item success passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">2019-01-04【审核人】文件审核通过，进入下一阶段</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item upload passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">【上传人】正在准备上传文件</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item in_audit passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">2019-01-05【上传人】文件上传成功，正在审核...</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item success passed">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">2019-01-05【审核人】文件审核通过，进入下一阶段</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li class="layui-timeline-item complete">--%>
                    <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                    <%--<div class="layui-timeline-content layui-text">--%>
                        <%--<div class="layui-timeline-title">完成所有阶段</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/reservation/lab/construction/examineGrandsonProject.js"></script>
</body>


</html>