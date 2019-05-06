<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="iframe" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lab_project.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/browser.js"></script>
	<!-- 打印开始 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<!-- 打印结束 -->

<!-- 打印、导出组件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/LodopFuncs.js"></script>
 <style>
 .change_tab th{
	border: 1px solid #bbb;
    text-align: center;
    border-bottom: 1px solid #ccc !important;
    border-top: none;
	}
 </style>
</head>
<body>
	<div class="main_container cf rel w95p ma mb60">
		 <div class="img_box">
            <p class="more">
                <span>专任实验室人员表</span>
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                <input type="button" class="r btn a-no mr5 mt5" value="打 印"  id="myPrint">
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportLabTeamTxt">导出txt</a>
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportLabTeam">导出excel</a>
           </sec:authorize>
            </p>
					<%-- <div class="w95p ma">
						<div class="search">
                   <a class="search-button  " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">基表1-教学科研仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">基表2-教学科研仪器设备增减变动情况表</a> 
                      <a class="search-button " href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">基表3-贵重仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">基表4-教学实验项目表</a> 
                    <a class="search-button  search-button-change" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
               		<a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">基表7-实验室经费情况表</a>
                </div> --%>
                <div class="content_ma TabbedPanelsContent">
                <div class="content-box">				
<div id="contentarea">
<div id="content">
<div class="content-box">
						<div id="myShow">
            			<table class="change_tab">
							<thead>
								<tr>
									<th rowspan="2">学校代码</th>
									<th rowspan="2">人员编号</th>
									<th rowspan="2">实验室编号</th>
									<th rowspan="2">实验室名称</th>
									<th rowspan="2">姓名</th>
									<th rowspan="2">性别</th>
									<th rowspan="2">出生年月</th>
									<th rowspan="2">所属学科</th>
									<th rowspan="2">专业技术职务</th>
									<th rowspan="2">文化程度</th>
									<th rowspan="2">专家类别</th>
									<th colspan="2">国内培训 </th>
									<th colspan="2">国外培训</th>
								</tr>
								<tr>
								    <th>学历教育时间 </th>
									<th>非学历教育时间</th>
									<th>学历教育时间 </th>
									<th>非学历教育时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${labTeamLists}" var="curr"
									varStatus="i">
									<tr>
										<td>${curr[0]}</td>
										<td>${curr[1]}</td>
										<td>${curr[2]}</td>
										<td>${curr[3]}</td>
										<td>${curr[4]}</td>										
										<td>
										<c:if test="${curr[5] eq 1}">男</c:if>
										<c:if test="${curr[5] eq 2}">女</c:if>
										</td>	
										<td>${curr[6]}</td>									
										<td>${curr[7]}</td>
										<td>
										<c:if test ="${curr[8] eq 710}">工人</c:if>
										<c:if test ="${curr[8] eq 711}">技师</c:if>
										<c:if test ="${curr[8] eq 712}">高级技师</c:if>
										<c:if test ="${curr[8] eq 713}">未定专业技术职务</c:if>
										</td>
										<td>
										<c:if test ="${curr[9] eq 03 }">博士</c:if>
										<c:if test ="${curr[9] eq 04 }">硕士</c:if>
										</td>
										<td>${curr[10]}</td>
										<td>${curr[11]}</td>
										<td>${curr[12]}</td>
										<td>${curr[13]}</td>
										<td>${curr[14]}</td>
										<td>${curr[15]}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
						</div>
						</div>
						</div>
						</div>
						</div>
						</div>
						
						    <script>
function start(v){
try{printWB.ExecWB(v,1);}catch(e){}
window.close();
}

function printPreview(){
	/*LODOP=getLodop();  
	var strStyleCSS = "<link type='text/css' rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'>";
	    strStyleCSS +="<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/timetable/lmsReg.css'>";
	var strHtml = strStyleCSS+"<body>"+$("#myShow").html()+"</body>";
	LODOP.PRINT_INIT("");
	LODOP.SET_PRINT_STYLE("FontSize",18);  //字体大小
	LODOP.SET_PRINT_STYLE("Bold",1);  //是否粗体，1是，0否
	LODOP.ADD_PRINT_HTM(30,30,"RightMargin:30","BottomMargin:50",strHtml);
	LODOP.PREVIEW();*/
	$('#myShow').jqprint();
}
$('#myPrint').click(function(){
    printPreview();
});
</script>
					</div>
</body>