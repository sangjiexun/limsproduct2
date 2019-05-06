<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.user-resources"/>
<jsp:useBean id="now" class="java.util.Date" />
<html>

<head>
    <title>CNST实验实训教学平台</title>
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
	<link href="${pageContext.request.contextPath}/css/tCourseSite/message/notice.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/jquery.autosize.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"></script>
    <!-- 打印开始 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
	<!-- 打印结束 -->
<script type="text/javascript">
	//打印
	$(document).ready(function(){	 
	      $("#myPrint1").click(function(){	    	  
	      $("#my_show1").jqprint();	    
	      });
	});
	$(document).ready(function(){	 
	      $("#myPrint2").click(function(){	    	  
	      $("#my_show2").jqprint();	    
	      });
	});
	$(document).ready(function(){	 
	      $("#myPrint3").click(function(){	    	  
	      $("#my_show3").jqprint();	    
	      });
	});
	$(document).ready(function(){	 
	      $("#myPrint4").click(function(){	    	  
	      $("#my_show4").jqprint();	    
	      });
	});
	$(document).ready(function(){	 
	      $("#myPrint5").click(function(){	    	  
	      $("#my_show5").jqprint();	    
	      });
	});
	$(document).ready(function(){	 
	      $("#myPrint").click(function(){	    	  
	      $(".my_show").jqprint();	    
	      });
	});
</script>
            
</head>

<body>
    <div class="course_con ma back_gray">
        <div class="course_cont r back_gray">
        	<div class="notice_cont ">
            <div class="w100p cf">
                
                <ul class="tool_box tab cf rel zx2 pt5 ">
                
                <li class="rel l pb5">
                    <div class="wire_btn1 l ml10 mt10 poi">
                        <a href="${pageContext.request.contextPath}/tcoursesite/student/courseStudentsList?tCourseSiteId=${tCourseSite.id}&currpage=1" class="g3">
                        <i class="fa fa-edit mr5"></i>班级成员
                        </a>
                    </div>
                </li>
                
                <li class="rel l pb5">
                    <div class="wire_btn1 l ml10 mt10 poi">
                        <a href="${pageContext.request.contextPath}/tcoursesite/attendence?tCourseSiteId=${tCourseSite.id}&attendenceId=-1" class="g3">
                        <i class="fa fa-edit mr5"></i>考勤
                        </a>
                    </div>
                </li>
                
                <li class="rel l pb5">
                    <div class="wire_btn1 l ml10 mt10 poi ">
                        <a href="#" class="g9">
                        <i class="fa fa-file-text-o mr5"></i>作业
                        </a>
                    </div>
                </li>
                
                <li class="rel l pb5">
                    <div class="wire_btn1 bgb l ml10 mt10 poi">
                        <a href="${pageContext.request.contextPath}/tcoursesite/gradeBook?tCourseSiteId=${tCourseSite.id}&type=assignment" class="g3">
                        <i class="fa fa-comments-o mr5"></i>成绩
                        </a>
                    </div>
                </li>
                
                <li class="rel l pb5">
                    <div class="wire_btn1 l ml10 mt10 poi">
                        <a href="#" class="g9">
                        <i class="fa fa-comments-o mr5"></i>学习行为
                        </a>
                    </div>
                </li>
                </ul>
            </div>
            </div>    
        
        
        
            <ul class="tool_box tab cf rel zx2 pt5  pb10">
                <li class="rel l pb5">
                    <div class="wire_btn l ml30 mt10 poi <c:if test="${type eq 'assignment'}">bgc </c:if>">
                        <i class="fa fa-bar-chart mr5"></i>作业成绩
                    </div>
                </li>
                <li class="rel l pb5">
                    <div class="wire_btn l ml10 mt10 poi">
                        <i class="fa fa-bar-chart mr5"></i>测试成绩
                    </div>
                </li>
                <li class="rel l pb5">
                    <div class="wire_btn l ml10 mt10 poi">
                        <i class="fa fa-bar-chart mr5"></i>考试成绩
                    </div>
                </li>
                 <li class="rel l pb5">
                    <div class="wire_btn l ml10 mt10 poi">
                        <i class="fa fa-bar-chart mr5"></i>考勤成绩
                    </div>
                </li>
                <li class="rel l pb5">
                    <div class="wire_btn l ml10 mt10 poi">
                        <i class="fa fa fa-line-chart mr5"></i>总成绩
                    </div>
                </li>
                <c:if test="${flag==1}">
                <li class="rel l pb5">
                    <div class="wire_btn l ml10 mt10 poi <c:if test="${type eq 'weight'}">bgc </c:if>">
                        <i class="fa fa-pie-chart mr5"></i>成绩权重
                    </div>
                </li>
                </c:if>
            </ul>
            <div class="tab_list f14 mb2 <c:if test="${type eq 'weight'}">hide</c:if>">
                <div class="lh40 bgg  pl30 f18 ">
                    <span class="bc">作业成绩</span>
                    <div class="h20 lh20 br3 plr10 r f12 bgb wh ml30 mt10 mr5 poi">
                        <%--<i class="fa fa-print mr5"></i>打印
                         --%><li><input type="button" value="打印" id="myPrint1"></li>                       
                    </div>
                    
                </div>
                <div class="module_con  mtb20">                   
                    <div class="plr30 lh30 f14">
                        <div class="w100p f14"><%--
                            <table class="w100p">
                                --%><table class="w100p"  id="my_show1">
                                <tr>
                                    <th class="w10p tl">姓名</th>
                                    <th class="tl">学号</th>
                                    <c:forEach items="${tAssignmentGradeObjects}" var="current"  varStatus="i">
                                    <th class="tl">${current.TAssignment.title }<br>${fn:substring(100*current.weight,0,fn:indexOf(100*current.weight, ".")) }%</th>
                                    </c:forEach>
                                    <th class="tl">作业成绩</th>
                                </tr>                             
                                <c:forEach items="${assignmentLists}" var="current"  varStatus="i">
                                 <c:if test="${flag==1||(flag==0&&current[1] eq user.username)}">
                                <tr>                                 
                                	<c:forEach items="${current }" var="current1" varStatus="j">
                                    <td>${current1 }</td>
                                    </c:forEach>                                  
                                </tr> 
                                 </c:if>
                                 </c:forEach>                                                   
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab_list f14 mb2 hide">
                <div class=" lh40 bgg  pl30 f18 ">
                    <span class="bc">测试成绩</span>
                    <div class="h20 lh20 br3 plr10 r f12 bgb wh ml30 mt10 mr5 poi"><%--
                        <i class="fa fa-print mr5"></i>打印
                    --%><li><input type="button" value="打印" id="myPrint2"></li> 
                    </div>
                </div>
                <div class="module_con  mtb20">
                    <div class="plr30 lh30 f14">
                        <div class="w100p f14"><%--
                            <table class="w100p">
                                --%><table class="w100p"  id="my_show2">
                                <tr>
                                    <th class="w10p tl">姓名</th>
                                    <th class="tl">学号</th>
                                    <c:forEach items="${tExamGradeObjects}" var="current"  varStatus="i">
                                    <th class="tl">${current.TAssignment.title }<br>${fn:substring(100*current.weight,0,fn:indexOf(100*current.weight, ".")) }%</th>
                                    </c:forEach>
                                    <th class="tl">测试成绩</th>
                                </tr>
                                 <c:forEach items="${examLists}" var="current"  varStatus="i">
                                  <c:if test="${flag==1||(flag==0&&current[1] eq user.username)}">
                                <tr>
                                	<c:forEach items="${current }" var="current1" varStatus="j">
                                    <td>${current1 }</td>
                                    </c:forEach>
                                </tr>                             
                                </c:if>                            	
                                </c:forEach>
                                </table>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="tab_list f14 mb2 hide">
                <div class=" lh40 bgg  pl30 f18 ">
                    <span class="bc">考试成绩</span>
                    <div class="h20 lh20 br3 plr10 r f12 bgb wh ml30 mt10 mr5 poi"><%--
                        <i class="fa fa-print mr5"></i>打印
                    --%><li><input type="button" value="打印" id="myPrint3"></li> 
                    </div>
                </div>
                <div class="module_con  mtb20">
                    <div class="plr30 lh30 f14">
                        <div class="w100p f14">
                            <%--<table class="w100p">
                                --%><table class="w100p"  id="my_show3">
                                <tr>
                                    <th class="w10p tl">姓名</th>
                                    <th class="tl">学号</th>
                                    <c:forEach items="${tTestGradeObjects}" var="current"  varStatus="i">
                                    <th class="tl">${current.TAssignment.title }<br>${fn:substring(100*current.weight,0,fn:indexOf(100*current.weight, ".")) }%</th>
                                    </c:forEach>
                                    <th class="tl">考试成绩</th>
                                </tr>
                                
                                <c:forEach items="${testLists}" var="current"  varStatus="i">
                                 <c:if test="${flag==1||(flag==0&&current[1] eq user.username)}">
                                <tr>
                                	<c:forEach items="${current }" var="current1" varStatus="j">
                                    <td>${current1 }</td>
                                    </c:forEach>
                                </tr>
                                </c:if>
                                </c:forEach>
                                
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
              <div class="tab_list f14 mb2 hide">
                <div class=" lh40 bgg  pl30 f18 ">
                    <span class="bc">考勤成绩</span>
                    <div class="h20 lh20 br3 plr10 r f12 bgb wh ml30 mt10 mr5 poi"><%--
                        <i class="fa fa-print mr5"></i>打印
                    --%><li><input type="button" value="打印" id="myPrint4"></li> 
                    </div>
                </div>
                <div class="module_con  mtb20">
                    <div class="plr30 lh30 f14">
                        <div class="w100p f14">
                            <%--<table class="w100p">
                                --%><table class="w100p"  id="my_show4">
                                <tr>
                                    <th class="w10p tl">姓名</th>
                                    <th class="tl">学号</th>
                                    <c:forEach items="${tAttendenceGradeObjects}" var="current"  varStatus="i">
                                    <th class="tl">${current.TAssignment.title }<br>${fn:substring(100*current.weight,0,fn:indexOf(100*current.weight, ".")) }%</th>
                                    </c:forEach>
                                    <th class="tl">考勤成绩</th>
                                </tr>
                                
                                <c:forEach items="${attendenceLists}" var="current"  varStatus="i">
                                 <c:if test="${flag==1||(flag==0&&current[1] eq user.username)}">
                                <tr>
                                	<c:forEach items="${current }" var="current1" varStatus="j">
                                    <td>${current1 }</td>
                                    </c:forEach>
                                </tr>
                                </c:if>
                                </c:forEach>
                                
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="tab_list f14 mb2 hide">
                <div class=" lh40 bgg  pl30 f18 ">
                    <span class="bc">总成绩</span>
                    <div class="h20 lh20 br3 plr10 r f12 bgb wh ml30 mt10 mr5 poi"><%--
                        <i class="fa fa-print mr5"></i>打印
                    --%><li><input type="button" value="打印" id="myPrint5"></li> 
                    </div>
                </div>
                <div class="module_con  mtb20">
                    <div class="plr30 lh30 f14">
                        <div class="w100p f14">
                            <%--<table class="w100p">
                                --%><table class="w100p"  id="my_show5">
                                <tr>
                                    <th class="w10p tl">姓名</th>
                                    <th class="tl">学号</th>
                                    <th class="tl">作业<br>${fn:substring(100*weightSettings[0].weight,0,fn:indexOf(100*weightSettings[0].weight, ".")) }%</th>
							        <th class="tl">测验<br>${fn:substring(100*weightSettings[1].weight,0,fn:indexOf(100*weightSettings[1].weight, ".")) }%</th>
							        <th class="tl">考试<br>${fn:substring(100*weightSettings[2].weight,0,fn:indexOf(100*weightSettings[2].weight, ".")) }%</th>
							        <th class="tl">考勤<br>${fn:substring(100*weightSettings[3].weight,0,fn:indexOf(100*weightSettings[3].weight, ".")) }%</th>
							        <th class="tl">总评</th>
                                </tr>                              
                                <c:forEach items="${sumLists}" var="current"  varStatus="i">
                                 <c:if test="${flag==1||(flag==0&&current[1] eq user.username)}"> 
									<tr> 
										<c:forEach items="${current }" var="current1" varStatus="j">
											<c:if test="${j.count==1||j.count==2 }">
												<td>	
													${current1 }
												</td>	
											</c:if>
											<c:if test="${j.count!=1&&j.count!=2 }">
												<td>	
													${fn:substring(current1,0,fn:indexOf(current1, ".")+2) }
												</td>
											</c:if>												
										</c:forEach>  
									</tr>
									</c:if>
								</c:forEach>                               
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="tab_list f14 mb2 <c:if test="${type ne 'weight'}">hide</c:if>"">
                <div class=" lh40 bgg  pl30 f18 ">
                    <span class="bc">成绩权重</span>
                    <div class="h20 lh20 br3 plr10 r f12 bgb wh ml30 mt10 mr5 poi">
                        <%--<i class="fa fa-print mr5"></i>打印
                    --%><li><input type="button" value="打印" id="myPrint"></li> 
                    </div>
                </div>
                <div class="module_con  mtb20">
                    <div class="plr30 lh30 f14">
                        <div class="w100p f14">
                        	<form action="${pageContext.request.contextPath }/tcoursesite/gradeBook/singleWeightSetting?tCourseSiteId=${tCourseSite.id}" method="POST"  onsubmit="return checkForm(this)">
								<div class="content-box" style="width: 23%;float: left;text-align:center;">
										
										<%--<table  id="my_show" class="w100p ">
										--%><table  class="my_show" style="width:100%">
												
											<thead>
											        <th style="display: block;border:none;color:#333;">作业权重</th>
											    <tr>                   
											        <th>标题</th>
											        <th class="w36p ">权重</th>
											    </tr>
											</thead>
											<tbody>
												<c:forEach items="${assignmentList }" var="current">
													<tr>
														<c:forEach items="${current }" var="current1" varStatus="i">
															<c:if test="${i.first }">
																<td style="display: none;"><input type="hidden" name="objectId" value="${current1 }" class=" w100p b1 br3 h20 lh20 mt5 plr5" > </td>
															</c:if>
															<c:if test="${!i.first&&!i.last }">
																<td>${current1 }</td>
															</c:if>
															<c:if test="${i.last }">
																<td><input name="weight" type="text" style="width: 38px;" value="${100*current1 }" class="easyui-numberbox w100p b1 br3 h20 lh20 mt5 plr5" required="required"/>% </td>
															</c:if>
														</c:forEach>
													</tr>
												</c:forEach>
												
											</tbody>
											
										</table>
										<button style="border:none;" class="bbtn bgb f14 r mt10 poi tc br3 wh w80" type="submit">确定</button>
									<br>
									
								</div>
							</form>
							
							<form action="${pageContext.request.contextPath }/tcoursesite/gradeBook/singleWeightSetting?tCourseSiteId=${tCourseSite.id}" method="POST"  onsubmit="return checkForm(this)">
								<%--<div class="content-box" style="width: 20%;float: left;margin-left: 5%;">
										测验权重
										--%><%--<table  id="my_show" class="w100p ">
										--%>
										<div class="content-box" style="width: 23%;float: left;margin-left:20px;text-align:center;">
										<table  class="my_show" style="width:100%">
											<thead>
													<th style="display: block;border:none;color:#333;">测验权重</th>
											    <tr>                   
											        <th>标题</th>
											        <th class="w36p">权重</th>
											    </tr>
											</thead>
											<tbody>
												<c:forEach items="${examList }" var="current">
													<tr>
														<c:forEach items="${current }" var="current1" varStatus="i">
															<c:if test="${i.first }">
																<td style="display: none;"><input type="hidden" name="objectId" value="${current1 }" class=" w100p b1 br3 h20 lh20 mt5 plr5"> </td>
															</c:if>
															<c:if test="${!i.first&&!i.last }">
																<td>${current1 }</td>
															</c:if>
															<c:if test="${i.last }">
																<td><input name="weight" type="text" style="width: 38px;" value="${100*current1 }" class="easyui-numberbox b1 br3 h20 lh20 mt5 plr5" required="required"/>% </td>
															</c:if>
														</c:forEach>
													</tr>
												</c:forEach>
												
											</tbody>
										</table>
										<button style="border:none;" class="bbtn bgb f14 r mt10 poi tc br3 wh w80" type="submit">确定</button>
									<br>
									
								</div>
							</form>
							
							<form action="${pageContext.request.contextPath }/tcoursesite/gradeBook/singleWeightSetting?tCourseSiteId=${tCourseSite.id}" method="POST"  onsubmit="return checkForm(this)">
								<%--<div class="content-box" style="width: 20%;float: left;margin-left: 5%;">
										考试权重
										--%><%--<table  id="my_show" class="w100p ">
										--%>
										<div class="content-box" style="width: 23%;float: left;margin-left:20px;text-align:center;">
										<table  class="my_show" style="width:100%">
											<thead>
											<th style="display: block;border:none;color:#333;">考试权重</th>
											    <tr>                   
											        <th>标题</th>
											        <th class="w36p ">权重</th>
											    </tr>
											</thead>
											<tbody>
												<c:forEach items="${testList }" var="current">
													<tr>
														<c:forEach items="${current }" var="current1" varStatus="i">
															<c:if test="${i.first }">
																<td style="display: none;"><input type="hidden" name="objectId" value="${current1 }" class=" w100p b1 br3 h20 lh20 mt5 plr5"> </td>
															</c:if>
															<c:if test="${!i.first&&!i.last }">
																<td>${current1 }</td>
															</c:if>
															<c:if test="${i.last }">
																<td><input name="weight" type="text" style="width: 38px;" value="${100*current1 }" class="easyui-numberbox b1 br3 h20 lh20 mt5 plr5" required="required"/>% </td>
																
															</c:if>
														</c:forEach>
													</tr>
												</c:forEach>
												
											</tbody>
										</table>
										<button style="border:none;" class="bbtn bgb f14 r mt10 poi tc br3 wh w80" type="submit">确定</button>
									<br>
									
								</div>
							</form>
							
							<form action="${pageContext.request.contextPath }/tcoursesite/gradeBook/singleWeightSetting?tCourseSiteId=${tCourseSite.id}" method="POST"  onsubmit="return checkForm(this)">
								<div class="content-box" style="width: 23%;float: left;text-align:center;">
										
										<%--<table  id="my_show" class="w100p ">
										--%><table  class="my_show" style="width:100%">
												
											<thead>
											        <th style="display: block;border:none;color:#333;">考勤权重</th>
											    <tr>                   
											        <th>标题</th>
											        <th class="w36p ">权重</th>
											    </tr>
											</thead>
											<tbody>
												<c:forEach items="${attendenceList }" var="current">
													<tr>
														<c:forEach items="${current }" var="current1" varStatus="i">
															<c:if test="${i.first }">
																<td style="display: none;"><input type="hidden" name="objectId" value="${current1 }" class=" w100p b1 br3 h20 lh20 mt5 plr5" > </td>
															</c:if>
															<c:if test="${!i.first&&!i.last }">
																<td>${current1 }</td>
															</c:if>
															<c:if test="${i.last }">
																<td><input name="weight" type="text" style="width: 38px;" value="${100*current1 }" class="easyui-numberbox w100p b1 br3 h20 lh20 mt5 plr5" required="required"/>% </td>
															</c:if>
														</c:forEach>
													</tr>
												</c:forEach>
												
											</tbody>
											
										</table>
										<button style="border:none;" class="bbtn bgb f14 r mt10 poi tc br3 wh w80" type="submit">确定</button>
									<br>
									
								</div>
							</form>
							
							<form action="${pageContext.request.contextPath }/tcoursesite/gradeBook/singleWeightSetting?tCourseSiteId=${tCourseSite.id}" method="POST" onsubmit="return checkForm(this)">
								<%--<div class="content-box" style="width: 20%;float: left;margin-left: 5%;">
										总评权重
										--%><%--<table  id="my_show" class="w100p ">
										--%>
										<div class="content-box" style="width: 23%;float: left;margin-left:20px;text-align:center;">
										<table  class="my_show" style="width:100%">
											<thead>
												<th style="display: block;border:none;color:#333;">总评权重</th>
											    <tr>                   
											        <th>标题</th>
											        <th class="w36p ">权重</th>
											    </tr>
											</thead>
											<tbody>
												<c:forEach items="${weightSettings }" var="current">
													<tr>
														<td style="display: none;"><input type="hidden" name="weightSettingId" value="${current.id }" class=" w100p b1 br3 h20 lh20 mt5 plr5"/></td>
														<td>
															<c:if test="${current.type eq 'assignment' }">作业</c:if>
															<c:if test="${current.type eq 'exam' }">测验</c:if>
															<c:if test="${current.type eq 'test' }">考试</c:if>
															<c:if test="${current.type eq 'attendence' }">考勤</c:if>
														</td>
														<td><input name="weight" type="text" style="width: 30px;" value="${100*current.weight }" class="easyui-numberbox b1 br3 h20 lh20 mt5 plr5" required="required"/>%</td>
														
													</tr>
												</c:forEach>
												
											</tbody>
											
										</table>
										<button style="border:none;" class="bbtn bgb f14 mt10 poi tc br3 wh w80" type="submit">确定</button>
									<br>
								</div>
							</form>
							
							
							
							
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">

    	$(".Lele").click(
            function () {
                $(".window_box").fadeIn(100)
            }
        );
        $(".close_icon").click(
            function () {
                $(".window_box").fadeOut(100)
            }
        );
        $(".module_tit").click(
            function () {
                $(this).siblings(".module_con").slideToggle(150)
            }
        )
        $(function () {
            $('textarea').autosize();
            //$('.animated').autosize();
        });
        $(".cm_list").not(".select").hover(
            function () {
                $(this).find("a").css("color", "#78B0FF")
            },
            function () {
                $(this).find("a").css("color", "#333")
            }
        )
        $(".tab li").click(
            function(){
                $(this).find(".wire_btn").addClass("bgc");
                 $(this).siblings().find(".wire_btn").removeClass("bgc")
                var i =$(this).index()
                //alert(i)
                $(".tab_list").eq(i).slideDown(150)
                $(".tab_list").eq(i).siblings(".tab_list").slideUp(150)
            }
        )
        
        function checkForm(obj){
    	var total = 0;
    	$(obj).find("input[type='text']").each(function(){
			total += Number($(this).val());    	
    	});
    	if (total!=100) {
			if (confirm("当前权重之和不为1，是否确认？")) {
				return true;
			}else {
				return false;
			}
		}
		return true;
    }
    </script>


</body>

</html>