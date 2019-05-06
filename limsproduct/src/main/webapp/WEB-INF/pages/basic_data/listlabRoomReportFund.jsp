<%@ page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%><jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
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
     <a  href="${pageContext.request.contextPath}/basic_data/labRoomReportBasic?currpage=1">实验室基本情况表填写</a>
     <a class="a1 aml" href="${pageContext.request.contextPath}/basic_data/labReportFund?currpage=1">实验室经费情况表填写</a> 	
     <a class="a-no mr20 mt75" href="${pageContext.request.contextPath}/basic_data/newLabRoomReportFund">新增</a>
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
									<th rowspan="3" class="td-bg">所属学院</th>
									<th rowspan="3" class="td-bg">实验室个数</th>
									<th rowspan="3" class="td-bg">实验室房屋使用面积</th>
									<th colspan="10" class="td-bg">经费投入</th>
								</tr>									
								<tr>
									<th rowspan="2" class="td-bg">总计</th>
									<th class="td-bg"></th>
									<th colspan="2" class="td-bg">仪器设备购置经费</th>
									<th colspan="2" class="td-bg">仪器设备维护经费</th>
									<th colspan="1" class="td-bg">实验教学运行经费</th>
									<th rowspan="2" class="td-bg">实验室建设经费</th>
									<th rowspan="2" class="td-bg">实验教学研究与改革经费</th>
									<th rowspan="2" class="td-bg">其他</th>
								</tr>
								<tr>
								   <th class="td-bg">小计</th>
								   <th class="td-bg">其中教学仪器购置经费</th>
								   <th class="td-bg">小计</th>
								   <th class="td-bg">其中教学仪器维护经费</th>
								   <th class="td-bg">小计</th>
							   	   <th class="td-bg">其中年材料消耗经费</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${labRoomReportFundLists}" var="curr"
									varStatus="i">
									<tr>
										<td>12712</td>
										<td>${curr[14]}</td>		
										<td>${curr[2]}</td>									
										<td>${curr[3]}</td>
										<td>${curr[4]}</td>
										<td>${curr[5]}</td>
										<td>${curr[6]}</td>
										<td>${curr[7]}</td>
										<td>${curr[8]}</td>
										<td>${curr[9]}</td>
										<td>${curr[10]}</td>
							            <td>${curr[11]}</td>
										<td>${curr[12]}</td>
										<td>${curr[13]}</td> 																		
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
						</div>
						
<script>
function start(v){
try{printWB.ExecWB(v,1);}catch(e){}
window.close();
}
</script>
</div>
    </div>
    </body>