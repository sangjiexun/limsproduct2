<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.user-resources"/>
<jsp:useBean id="now" class="java.util.Date" />
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>新建实验项目</title>
    <meta name="decorator" content="course">
    <!--[if lt IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <![endif]-->
    <!--[if gte IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>  
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/reset.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/lib.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/global.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/jquery.searchableSelect.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/skill/experiment.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/jquery.autosize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/jquery.searchableSelect.js"></script>

        <!-- ueditor编辑器 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.all.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script> 
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/tCourseSite/Calendar.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css"/> 

    <!-- 上传插件的css和js -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
</head>
<body>
	<input type="hidden"  id="contextPath" value="${pageContext.request.contextPath}"/>
	<input type="hidden"  id="sessionId" value="<%=session.getId()%>"/>
     <div class="course_con ma back_gray" >
        <div class="course_cont r back_gray">
            <div class="course_infobox">
                <h4 class="course_infobox_title">
                    <span>${tCourseSite.title}</span>
                </h4>
                <div class="course_infobox_profile">
                	<p>${tCourseSite.experimentSkillProfile}</p>
                </div>
                <div class="course_schedule">
                    <c:forEach begin="1" end="${skillDoList[0]}" step="1" varStatus="j" var="current">	
               			<div class="bggreen"></div>
                   	</c:forEach>
                   	<c:forEach begin="1" end="${skillDoList[1]}" step="1" varStatus="j" var="current">	
               			<div class="bgb"></div>
                   	</c:forEach>
                   	<c:forEach begin="1" end="${skillDoList[2]}" step="1" varStatus="j" var="current">	
               			<div class="bgg"></div>
                   	</c:forEach>
                    <p class="f16">( ${skillDoList[0]}/${fn:length(tExperimentSkills)})</p>
                </div>
            </div>
            <div class="course_content cf">
            	<form:form action="${pageContext.request.contextPath}/tcoursesite/skill/saveExperimentSkill?tCourseSiteId=${tCourseSite.id}" method="POST" modelAttribute="tExperimentSkill">
                <div class="f_right lab_item_ctrl">
                    <a class="btn_lab btn_blue" href="javascript:void(0)" onclick="window.history.go(-1)">返回</a>
                </div>
                <div class="exp_list">
                    实验项目新建
                </div>
                <div class="info">
                	
                        <h4 class="info_title">
                            基本信息
                        </h4>
                        <div class="info_content">
                        	<form:hidden path="id" id="id"/>
		                	<form:hidden path="siteId" id="siteId" value="${tCourseSite.id}"/>
		                	<div class="cf">
                            <div class="w45p l f16 info_detail ">
                                <label for="">实验名称:</label>
                                <form:input path="experimentName" id="experimentName" type="text" class="clear-input" />
                            </div>
                            <div class="w45p l f16 info_detail ">
                                <label for="">实验编号:</label>
                                <form:input path="experimentNo" id="experimentNo" type="text" class="clear-input"/>
                            </div>
                            </div>
                            <div class="cf">
                            <div class="w45p l f16 info_detail ">
                                <label for="" class="ml16 mr16">节次:</label>
                                <form:input path="sort" id="sort" class="clear-input" />
                            </div>
                            <div class="w45p l f16 info_detail ">
                                <label for="" class=" l">实验类别:</label>
                                <div class="w55p l">
                                <form:select path="experimentType" id="experimentType" class="w45p select_chosen">
                                    <form:option value="0">请选择</form:option>
                                    <form:option value="1">基础型</form:option>
                                    <form:option value="2">设计型</form:option>
                                    <form:option value="3">创新型</form:option>
                                    <form:option value="4">综合型</form:option>
                                    <form:option value="5">演示型</form:option>
                                    <form:option value="6">验证型</form:option>
                                </form:select>
                                </div>
                            </div>
                            </div>
                            <div class="cf">
                            <div class="w45p l f16 info_detail ">
                                <label for="" class=" l">是否启用:</label>
                                <div class="w55p l">
                                <form:select path="experimentIsopen" id="experimentIsopen" class="w45p select_chosen">
                                  <form:option value ="1">是</form:option>
                                  <form:option value ="0">否</form:option>
                                </form:select>
                                </div>
                            </div>
                            <div class="w45p l f16 info_detail ">
                                <label for="">实验版本:</label>
                                <form:input path="experimentVersion" id="experimentVersion" type="text" class="clear-input" />
                            </div>
                            </div>
                            <div class="cf">
                            <div class="w45p l f16 info_detail ">
                                <label for="">开始时间:</label>
                                <input type="text" id="startdate" name="startdate" 
                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"  
                                value="${startdate}" readonly="readonly" 
                                required="required" class="Wdate clear-input" />
                            </div>
                            <div class="w45p l f16 info_detail ">
                                <label for="">结束时间:</label>
                                <input type="text" id="duedate" name="duedate" 
                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" 
                                value="${startdate}" readonly="readonly" 
                                required="required" class="Wdate clear-input" />
                            </div>
                            </div>
                            
                            <div class="w100p f14 l mt10 hid">
						    	<div class="w95p l f16 info_detail ">
					     			<label >评分设置：</label>
					     			<input type="radio" name="toGradebook" value="yes"  id="toGradebookYes" <c:if test="${toGradebook eq 'yes'}">checked="checked"</c:if>/><label for="toGradebookYes">将实验报告添加到成绩簿</label> 
					     			<input type="radio" name="toGradebook" value="no"  id="toGradebookNo" <c:if test="${toGradebook eq 'no'}">checked="checked"</c:if>/><label for="toGradebookNo">不要将实验报告加入到成绩簿中 </label><br>
					     			<%--
					     			<form:radiobutton path="TAssignmentControl.gradeToStudent" name="TAssignmentControlGradeToStudent" value="yes" id="TAssignmentControlGradeToStudentYes" checked="checked"/><label for="TAssignmentControlGradeToStudentYes">将成绩公布给学生 </label>
					     			<form:radiobutton path="TAssignmentControl.gradeToStudent" name="TAssignmentControlGradeToStudent" value="no" id="TAssignmentControlGradeToStudentNo" /><label for="TAssignmentControlGradeToStudentNo">不将成绩公布给学生</label><br>
					     			<form:radiobutton path="TAssignmentControl.gradeToTotalGrade" name="TAssignmentControlGradeToTotalGrade" value="yes" id="TAssignmentControlGradeToTotalGradeYes" checked="checked"/><label for="TAssignmentControlGradeToTotalGradeYes">将成绩计入总成绩</label>
					     			<form:radiobutton path="TAssignmentControl.gradeToTotalGrade" name="TAssignmentControlGradeToTotalGrade" value="no" id="TAssignmentControlGradeToTotalGradeNo" /><label for="TAssignmentControlGradeToTotalGradeNo">不将成绩计入总成绩</label>
						 			--%>
						 		</div>
					 		</div>
                        </div>
                    </div>
                    <div class="mb10">
                        <h4 class="info_title">
                            实验目的
                        </h4>
                        <form:textarea path="experimentGoal" id="experimentGoal" class="f14 bdg w100p" ></form:textarea>
                    </div>

                    <div class="mb10">
                        <h4 class="info_title">
                            实验描述
                        </h4>
                        <div>
                            <script type="text/javascript">
                                var ue = UE.getEditor("experimentDescribe");
                            </script> 
                            
                               <form:textarea path="experimentDescribe" id="experimentDescribe"></form:textarea>
                            
                            
                        </div>
                    </div>

                    <div class="mb10">
                        <h4 class="info_title"> 实验指导书
                        <a class="btn btn-new" href="javascript:void(0)" onclick="upWkUpload('experimentalQuide',201)">上传</a>
                        </h4>
                        <div class="tab_list f14 mb2">
		                    <input name="experimentalQuidesList" id="experimentalQuidesList" type="text" class="hide"/><br>
		                    <div class="module_con  mtb10">
		                    	<ul id="experimentalQuidesNameList" class="">
		
		                    	</ul>
		                	</div>
	                    </div> 
                    </div>

                    <div class="mb10">
                        <h4 class="info_title"> 实验图片
                        <a class="btn btn-new" href="javascript:void(0)" onclick="upWkUpload('experimentalImage',202)">上传</a>
                        </h4>
                        <div class="tab_list f14 mb2">
		                    <input name="experimentalImagesList" id="experimentalImagesList" type="text" class="hide"/><br>
		                    <div class="module_con  mtb10">
		                    	<ul id="experimentalImagesNameList" class="">
		
		                    	</ul>
		                	</div>
	                    </div> 
                    </div>

                    <div class="mb10">
                        <h4 class="info_title">实验视频
                        <a class="btn btn-new" href="javascript:void(0)" onclick="upWkUpload('experimentalVideo',203)">上传</a>
                        </h4>
                        <div class="tab_list f14 mb2">
		                    <input name="experimentalVideosList" id="experimentalVideosList" type="text" class="hide"/><br>
		                    <div class="module_con  mtb10">
		                    	<ul id="experimentalVideosNameList" class="">
		
		                    	</ul>
		                	</div>
	                    </div> 
                    </div>
                    

                    <div class="mb10">
                        <h4 class="info_title"> 实验工具
                        <a class="btn btn-new" href="javascript:void(0)" onclick="upWkUpload('experimentalTool',204)">上传</a>
                        </h4>
                        <div class="tab_list f14 mb2">
		                    <input name="experimentalToolsList" id="experimentalToolsList" type="text" class="hide"/><br>
		                    <div class="module_con  mtb10">
		                    	<ul id="experimentalToolsNameList" class="">
		
		                    	</ul>
		                	</div>
	                    </div>
                    </div>
                    <div class="mb10 w45p f14   cf">
                        <h4 class="info_title">项目所属实训室:
                        </h4>
                        <select  multiple="multiple" class=" w100p lh30 br3 b1 mt5 select_chosen" onchange="changeLabRooms()" name="labRoomIds" id="labRoomIds">
                             <c:forEach items="${labRooms}" var="labRoom"  varStatus="i">
                                  <option value ="${labRoom.id}">${labRoom.labRoomName}</option>
                              </c:forEach>
                        </select> 
                    </div>
                    <div class="mb10 w45p f14   cf">
                        <h4 class="info_title">实验设备</h4>
                        <select  multiple="multiple" class=" w100p lh30 br3 b1 mt5 select_chosen" id="devices" name="devices">
                                  <option value ="">请选择</option>
                                  
                        </select> 
                    </div>
                    <div class="cf tc">
	                    <input type="submit" class="btn bgb l mt10 poi  plr20 br3 wh" value="保存"/>
	                </div>
               </form:form>
            </div>
        </div>
    </div>
    
    
    	<!--添加文件开始-->
	<div class="window_box hide fix zx2  " id="upWkUpload">
        <div class="window_con bgw b1 br3 rel bs10 ">
            <span class="close_icon f16 abs b1 br3 bs5 poi h20 w20 lh20">x</span>
             <div class="add_tit p10 bb f16">资源上传</div>
            <div class="add_con p10">
                <div class="add_module cf">
                    <div class="tab_box">
                    <div class="tab_list f14 mb2">
                    <input type="file" name="file" id="upWkUploadUploadifyPic" />
                    </div>
                    </div>

                </div>
                <div class="cf tc">
                    <div class="btn close_icon bgc l ml30 mt10 poi plr20 br3">取消</div>
                </div>
            </div>
        </div>

    </div>
    <!--添加文件结束-->
    <script type="text/javascript">
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/global.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/skill/experimentSkill.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>