<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <script src="${pageContext.request.contextPath}/video/Scripts/modernizr.custom.js" type="text/javascript"></script>
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.trainingRoom.infoManagement" /></a></li>
            <li><a href="javascript:void(0)"><spring:message code="left.trainingAnnex.management" /></a></li>
            <li class="end"><a href="javascript:void(0)">详情</a></li>

        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title"><spring:message code="all.trainingRoom.labroom" />详情</div>

                    </div>
                    <div class="new-classroom">
                        <fieldset>
                            <label><spring:message code="all.trainingRoom.labroom"/>名称：</label>${labAnnex.labName}
                        </fieldset>
                        <fieldset>
                            <label><spring:message code="all.trainingRoom.labroom"/>英文名称：</label>${labAnnex.labEnName}
                        </fieldset>
<%--                        <fieldset>
                            <label>所属学科：</label>${labAnnex.labSubject}
<%--                        </fieldset>&ndash;%&gt;
                        <fieldset>
                            <label>管理机构:</label>${labAnnex.belongDepartment}
                        </fieldset>--%>
                        <fieldset>
                            <label><spring:message
                                    code="all.trainingRoom.labroom"/>类别:</label>${labAnnex.CDictionaryByLabAnnex.CName}
                        </fieldset>
                        <fieldset>
                            <label>所属中心:</label>${labAnnex.labCenter.centerName}
                        </fieldset>
<%--                        <fieldset>
                            <label>联系方式:</label>${labAnnex.contact}
                        </fieldset>--%>
                        <fieldset class="introduce-box">
                            <label><spring:message code="all.trainingRoom.labroom"/>简介:</label>
                            <textarea rows="" cols="">${labAnnex.labDescription}</textarea>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <label>规章制度:</label>
                            <textarea rows="" cols="">${labAnnex.labAttention}</textarea>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <label> 获奖信息: </label>
                            <textarea rows="" cols="">${labAnnex.awardInformation}</textarea>
                        </fieldset>
<%--                        <fieldset class="introduce-box">
                            <label>图片: </label>
                            <ul class="img-box">
                                <c:forEach items="${labAnnex.commonDocuments}" var="d">
                                    <li><img alt="" src="${pageContext.request.contextPath}/${d.documentUrl}" width="200px" height="150px"> </li>
                                </c:forEach>
                            </ul>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <label>视频: </label>
                            <ul class="img-box">
                                <c:forEach items="${labAnnex.commonVideos}" var="v">
                                    <li>
                                        <embed src="${pageContext.request.contextPath}/${v.videoUrl}"  autostart="true" loop="true" width="450" height="400">
                                        </embed>
                                    </li>
                                </c:forEach>
                            </ul>
                        </fieldset>--%>
                    </div>
                    <div class="moudle_footer">
                        <div class="submit_link">
                            <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>


</html>