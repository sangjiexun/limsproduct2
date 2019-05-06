<%@ page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="back_main" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lab_project.css" />
</head>
<body>
		<div class="main_container cf rel w95p ma mb60">
        <div class="img_box">
        	<p class="more">
     <a  href="${pageContext.request.contextPath}/basic_data/schoolDeviceUse?currpage=1">贵重仪器设备表填写</a>  
     <a class="a1 aml"  href="${pageContext.request.contextPath}/basic_data/labRoomReportBasic?currpage=1">实验室基本情况表填写</a>
     <a href="${pageContext.request.contextPath}/basic_data/labReportFund?currpage=1">实验室经费情况表填写</a> 	
      </p>
            <p class="triangle">
                <span>◥<sapn>
            </p>	   
             
					 <div class="w95p ma">
						
						  <div class="bg ptb5 mb15 overflow">
            			<table class="tab w97p font">
							<thead>
								<tr>
									<th rowspan="3" class="td-bg">学校代码</th>
									<th rowspan="3" class="td-bg">实验室编号</th>
									<th rowspan="3" class="td-bg">实验室名称</th>
									<th rowspan="3" class="td-bg">实验室类别</th>
									<th rowspan="3" class="td-bg">建立年份</th>
									<th rowspan="3" class="td-bg">房屋使用面积</th>
									<th rowspan="3" class="td-bg">实验室类型</th>
									<th rowspan="3" class="td-bg">所属学科</th>
									<th colspan="3" class="td-bg">教师获奖与成果</th>
									<th rowspan="3" class="td-bg">学生获奖情况</th>
									<th colspan="5" class="td-bg">论文和教材情况</th>
									<th colspan="5" class="td-bg">科研及社会服务情况</th>
									<th colspan="3" class="td-bg">毕业设计和论文人数</th>
									<th colspan="6" class="td-bg">开放实验</th>
									<th rowspan="3" class="td-bg">兼任人员数</th>
									<th colspan="2" class="td-bg">实验教学运行经费</th>
									<th rowspan="3" class="td-bg">操作</th>
								</tr>
								<tr>
						<th rowspan="3" class="td-bg">国家级</th>
                        <th rowspan="3" class="td-bg">省部级</th>  
                        <th rowspan="3" class="td-bg">发明专利</th>
                        <th colspan="2" class="td-bg">三大检索收录</th>
                        <th colspan="2" class="td-bg">核心刊物</th> 
                        <th rowspan="3" class="td-bg">实验教材</th>
                        <th colspan="2" class="td-bg">科研项目数</th>
                        <th rowspan="3" class="td-bg">社会服务项目数</th>
                        <th colspan="2" class="td-bg">教研项目数</th>  
                        <th rowspan="3" class="td-bg">专科生人数</th>
                        <th rowspan="3" class="td-bg">本科生人数</th>
                        <th rowspan="3" class="td-bg">研究生人数</th>
                        <th colspan="2" class="td-bg">实验个数</th>
                        <th colspan="2" class="td-bg">实验人数</th>
                        <th colspan="2" class="td-bg">实验人时数</th>
                        <th rowspan="2" class="td-bg">小计</th>
                        <th rowspan="2" class="td-bg">教学实验年材料消耗费</th>
                        </tr>
                        
                        <tr>
                        <th class="td-bg">教学</th>
                        <th class="td-bg">科研</th>
                        <th class="td-bg">教学</th>
                        <th class="td-bg">科研</th>
                        <th class="td-bg">省部级以上</th>
                        <th class="td-bg">其他</th>
                        <th class="td-bg">省部级以上</th>
                        <th class="td-bg">其他</th>
                        <th class="td-bg">校内</th>
                        <th class="td-bg">校外</th>
                        <th class="td-bg">校内</th>
                        <th class="td-bg">校外</th>
                        <th class="td-bg">校内</th>
                        <th class="td-bg">校外</th>
                        </tr>
                        
							</thead>
							<tbody>
								<c:forEach items="${labBasicLists}" var="curr" varStatus="i">
									<tr>
										<td>${curr[0]}</td>
										<td>${curr[1]}</td>
										<td>${curr[2]}</td>
										<td>
										<c:if test="${curr[3] eq 1}">国家级实验教学示范中心</c:if>
										<c:if test="${curr[3] eq 2}">省级实验教学示范中心</c:if> 
										<c:if test="${curr[3] eq 3}">按平台建设的校、院（系）实验室</c:if>
										<c:if test="${curr[3] eq 4}">其它实验室</c:if>
										</td>
										<td>${curr[4]}</td>
										<td>${curr[5]}</td>
										<td>
										<c:if test="${curr[6] eq 1}">教学为主</c:if>
										<c:if test="${curr[6] eq 2}">科研为主</c:if> 
										<c:if test="${curr[6] eq 3}">其他</c:if>
										</td>
										<td>${curr[7]}</td>
										<td>${curr[8]}</td>
										<td>${curr[9]}</td>
										<td>${curr[10]}</td>
										<td>${curr[11]}</td>
										<td>${curr[12]}</td>
										<td>${curr[13]}</td>
										<td>${curr[14]}</td>
										<td>${curr[15]}</td>
										<td>${curr[16]}</td>
										<td>${curr[17]}</td>
										<td>${curr[18]}</td>
										<td>${curr[19]}</td>
										<td>${curr[20]}</td>
										<td>${curr[21]}</td>
										<td>${curr[22]}</td>
										<td>${curr[23]}</td>
										<td>${curr[24]}</td>
										<td>${curr[25]}</td>
										<td>${curr[26]}</td>
										<td>${curr[27]}</td>
										<td>${curr[28]}</td>
										<td>${curr[29]}</td>
										<td>${curr[30]}</td>
										<td>${curr[31]}</td>
										<td>${curr[32]}</td>
										<td>${curr[33]}</td>
										<c:if test="${curr[36] eq null}">
										<td><a href="${pageContext.request.contextPath}/basic_data/newLabRoomReportBasic?labRoomId=${curr[34]}">填写</a></td>
									    </c:if>
									    <c:if test="${curr[36] ne  null}">
										<td><a href="${pageContext.request.contextPath}/basic_data/editLabRoomReportBasic?labRoomId=${curr[34]}">修改</a></td>
									    </c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
						</div>
						</div>
						
					</div>
</body>