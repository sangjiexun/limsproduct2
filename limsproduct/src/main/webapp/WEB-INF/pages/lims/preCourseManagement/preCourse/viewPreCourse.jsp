<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
</head>

<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        基础信息
                    </div>
                    <div class="new-classroom">
                        <fieldset>
                            <label>学期：</label>
                            ${pta.schoolTerm.termName}
                        </fieldset>
                        <fieldset>
                            <label>课程信息：</label>
                            ${pta.schoolCourseInfo.courseName}
                        </fieldset>
                        <fieldset>
                            <label>授课教师：</label>
                            ${pta.userByTeacher.cname}
                        </fieldset>
                        <fieldset>
                            <label>指导教师：</label>
                            <c:if test="${pta.userByTutor != null}">
                                ${pta.userByTutor.cname}
                            </c:if>
                        </fieldset>
                        <fieldset>
                            <label>所属部门：</label>
                            ${pta.schoolAcademy.academyName}
                        </fieldset>
                        <fieldset>
                            <label>上课人数：</label>
                            ${pta.stuNumber}
                        </fieldset>
                        <fieldset>
                            <label>房间布局类型：</label>
                            ${pta.preRoomType.roomType}
                        </fieldset>
                        <fieldset>
                            <label>需要软件：</label>
                            ${pta.preSoftware.name}
                        </fieldset>
                        <fieldset>
                            <label>预排课房间：</label>
                            <c:forEach items="${preLabRoom}" var="s">
                                ${s.roomName}<br>
                            </c:forEach>

                            </select>
                        </fieldset>
                    </div>
                    <div class="title">
                        预排课时间
                    </div>
                    <div class="new-classroom">
                        <fieldset style="width: 90%">
                            <table>
                                <thead>
                                <tr>
                                    <td>星期</td>
                                    <td>周次</td>
                                    <td>节次</td>
                                </tr>
                                </thead>
                                <tbody id="add_time">
                                <c:if test="${preTimetableSchedules != null}">
                                    <c:forEach items="${preTimetableSchedules}" var="p">
                                        <tr>
                                            <td>星期${p.startWday}</td>
                                            <td>${p.startWeek}-${p.endWeek}周</td>
                                            <td>${p.startClass}-${p.endClass}节</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>

                                </tbody>
                            </table>
                        </fieldset>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

