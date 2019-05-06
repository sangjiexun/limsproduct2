<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<fmt:setBundle basename="bundles.projecttermination-resources" />

<html>
<head>
<meta name="decorator" content="iframe" />
<title><fmt:message key="html.title" />
</title>
<!-- <meta name="decorator" content="iframe"/> -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript">
	function addproject() {

	}
</script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
<style>
.content-box legend {
    position: relative;
    top: 2px;
    padding: 0 16px 5px;
    margin-left: 15px;
    font-weight: 700;
    text-shadow: 0.5px 0.5px 0.5px #fff;
    font-size: 13px;
    color:#383944;
    display: block;
    letter-spacing: 2px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 5px;
}
.content-box fieldset{
    width: 94%!important;
    padding: 2.2% 0%;
    margin: 1% 3% 3%;
    background: #fcfcfd;
    border: 1px solid #caccd6;
    border-radius: 5px;
    box-shadow: 0 0 3px #fff inset;
}
.w100{width:100px;}
.w170{width:170px;}
fieldset th, fieldset td{
    color:#5a5b6b;
}
</style>
</head>
<body>
	<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)">实验室运行管理</a>
				</li>
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
										<th class="w100">课程代码：</th>
										<td>${infor.schoolCourseInfoByClassId.courseNumber }</td>
										</select>
										</td>
										<th class="w170">英文名称：</th>
										<td>${infor.enName}</td>
									</tr>
									<tr>
									<th>面向专业：</th>
									<td><c:forEach items="${infor.schoolMajors}" var="a">${a.majorName}/</c:forEach>
									</td>
									</select>
									</td>
									<th>课程学分：</th>
									<td>${infor.inputCredit}</td>
									</tr>
									<tr>
									<th>周学时：</th>
									<td>${infor.theoryCourseHour}+${infor.experimentCourseHour}（理论课+实验课）</td>
									<th>任课教师 / 课程负责人：</th>
									<td>${infor.extraTeacher}</td>
									</tr>
									<tr>
									<th>开课学院：</th>
									<td>${infor.schoolAcademy.academyName}</td>
									<th>课程名称：</th>
									<td>${infor.schoolCourseInfoByClassId.courseName}</td>
									</td>
									<tr>
									<th>课程性质：</th>
									<td><c:forEach
											items="${infor.CDictionarys}" var="a">${a.CName}/</c:forEach>
									</td>
									<th>学期：</th>
									<td>${infor.schoolTerm.termName}</td>
									</tr>

									<!-- <th>后续课程</th>
									<td>${infor.schoolCourseInfoByFollowUpCourses.courseName}</td>
									</select>
									</td>
									<th>先修课程</th>
									<td>${infor.schoolCourseInfoByFirstCourses.courseName}</td>
									</tr>
									 -->
									 <tr>
									 <th>预修课程：</th>
									 <td>
									 <span>必修 <c:forEach items="${bixiuList}" var="a">${a.schoolCourseInfo.courseName}</c:forEach></span>
									 <span>选修  ${xuanxiuShow}</span>
									 </td>
									</tr>

								</table>
							</fieldset>

							<fieldset class="introduce-box">
								<legend>课程基本内容及要求</legend>
								<table>
								<tr>	<th>（一）课程基本内容</th></tr>
								<td>${infor.basicContentCourse}</td>
							
								<tr>
								<th>（二）课程基本要求</th></tr>
								<td>${infor.basicRequirementsCourse}</td>
					
								</table>
							</fieldset>

							 	<fieldset class="introduce-box">
									<legend>教材和教学参考资料</legend>
									<table>
									<td>${infor.useMaterials}</td>
                                     
									</table>
							</fieldset>
					 	<fieldset class="introduce-box">
									<legend>教学进度安排</legend>
									<table>
								<c:forEach items="${operationOutlineCourses}" var="o">
					<tr>
					<td>课次：${o.courseTime}
					</td>
					<td>课程性质：${o.cDictionary.CName }
					</td>
					<td>课程内容：${o.courseContent }
					</td>
					<td>周次：${o.week}
					</td>
					</tr>
					</c:forEach>
                                     
									</table>
							</fieldset>
						
							</div>

								</div>
								<!-- </div> -->

								</div>

								</div>
								<!-- </div> -->

								</div>
</body>
<!-------------列表结束----------->
</html>