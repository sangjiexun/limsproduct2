<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<fmt:setBundle basename="bundles.projecttermination-resources" />

<html>
<head>
    <meta name="decorator" content="iframe" />
    <title><fmt:message key="html.title" />
    </title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
            <li><a href="javascript:void(0)"><spring:message code="left.outline.management"/></a></li>
            <li class="end"><a href="javascript:void(0)">查看大纲</a>
            </li>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">查看</div>
                        <a onclick="window.history.go(-1)" class="btn btn-return">返回</a>
                    </div>
                    <div class="new-classroom">
                        <fieldset class="introduce-box">
                            <legend>基本信息</legend>
                            <table>
                                <tr>
                                    <th>课程代码</th>
                                    <td>${infor.schoolCourseInfoByClassId.courseNumber }</td>
                                    <th>英文名称</th>
                                    <td>${infor.enName}</td>
                                </tr>
                                <tr>
                                    <th>面向专业</th>
                                    <td><c:forEach items="${infor.schoolMajors}" var="a">${a.majorName}/</c:forEach></td>
                                    <th>课程学分</th>
                                    <td>${infor.COperationOutlineCredit.credit}</td>
                                </tr>
                                <tr>
                                    <th>课程名称</th>
                                    <td>${infor.schoolCourseInfoByClassId.courseName}</td>
                                    <th>课程性质</th>
                                    <td><c:forEach items="${infor.CDictionarys}" var="a">${a.CName}/</c:forEach></td>
                                </tr>
                                <tr>
                                    <th>课程学时</th>
                                    <td>${infor.courseHour}</td>
                                    <th>讲课学时</th>
                                    <td>${infor.theoryCourseHour}</td>
                                </tr>
                                <tr>
                                    <th>实验学时</th>
                                    <td>${infor.experimentCourseHour}</td>
                                    <th>上机学时</th>
                                    <td>${infor.computerCourseHour}</td>
                                </tr>
                                <tr>
                                    <th>习题学时</th>
                                    <td>${infor.exerciseCourseHour}</td>
                                </tr>
                                <tr>
                                    <th>后续课程</th>
                                    <td>${infor.schoolCourseInfoByFollowUpCourses.courseName}</td>
                                    <th>先修课程</th>
                                    <td>${infor.schoolCourseInfoByFirstCourses.courseName}</td>
                                </tr>
                                <tr>
                                    <th>使用教材</th>
                                    <td>${infor.useMaterials}</td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>课程详细信息</legend>
                            <table>
                                <tr><th>课程简介</th></tr>
                                <tr><td>${infor.courseDescription}</td></tr>
                                <tr><th>选课建议</th></tr>
                                <tr><td>${infor.coursesAdvice}</td></tr>
                                <tr><th>课程任务和教学目标</th></tr>
                                <tr><td>${infor.outlineCourseTeachingTarget}</td></tr>
                                <c:forEach items="${courseObjectiveList}" var="o">
                                    <tr>
                                        <td>${o.objectiveName}:${o.objectiveContent}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>课程基本内容及要求</legend>
                            <table>
                                <tr><th>（一）课程基本内容</th></tr>
                                <tr><td>${infor.basicContentCourse}</td></tr>
                                <tr><th>（二）课程基本要求</th></tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>课程教学方法</legend>
                            <table>
                                <tr><th>课程教学方法详情</th></tr>
                                <tr><td>${infor.teachMethod}</td></tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>课程考核方法</legend>
                            <table>
                                <tr><th>课程考核方法详情</th></tr>
                                <tr><td>${infor.courseAppraise}</td></tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>学习目标及作业评定</legend>
                            <table>
                                <tr><th>作业、考核成绩及成绩评定</th></tr>
                                <tr><td>${infor.assResultsPerEvaluation}</td></tr>
                                <tr><th>课程任务和教学目标</th></tr>
                                <tr><td>${infor.outlineCourseTeachingTargetOver}</td></tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>附件</legend>
                            <table>
                                <c:forEach items="${infor.commonDocuments}" var="k">
                                    <tr><td>${k.documentName}</td><td><a href="${pageContext.request.contextPath}/operation/downloadfile?idkey=${k.id}">下载</a></td></tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>实验项目与内容添加</legend>
                            <table>
                                <tr>
                                    <td>实验项目与内容添加</td>
                                    <td>
                                </tr>
                                <table>
                                    <tr>
                                        <td>实验项目编号</td>
                                        <td>实验项目名称</td>
                                        <td>实验类型</td>
                                        <td>实验时数</td>
                                        <td>每组人数</td>
                                        <td><spring:message code="all.trainingRoom.labroom" /></td>
                                        <td>备注（必做/选做）</td>
                                    </tr>
                                    <c:forEach items="${infor.operationItems}" var="s">
                                        <tr>
                                            <td>${s.lpCodeCustom}</td>
                                            <td>${s.lpName}</td>
                                            <td>${s.CDictionaryByLpCategoryMain.CName}</td>
                                            <td>${s.lpDepartmentHours}</td>
                                            <td>${s.lpStudentNumberGroup}</td>
                                            <td><c:forEach items="${s.labRooms}" var="a">${a.labRoomName}/</c:forEach></td>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>审核信息</legend>
                            <table>
                                <th>大纲撰写人</th>
                                <td>${infor.user.cname}</td>
                                <th>大纲审核人</th>
                                <td>${infor.auditor}</td>
                            </table>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>