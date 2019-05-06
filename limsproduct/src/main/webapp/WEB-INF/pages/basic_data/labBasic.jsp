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
                <span>实验室基本情况表</span>
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                <input type="button" class="r btn a-no mr5 mt5" value="打 印"  id="myPrint">
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportLabBasicTxt">导出txt</a>
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportLabBasic">导出excel</a>
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
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button  search-button-change" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
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
									<th rowspan="3">学校代码</th>
									<th rowspan="3">实验室编号</th>
									<th rowspan="3">实验室名称</th>
									<th rowspan="3">实验室类别</th>
									<th rowspan="3">建立年份</th>
									<th rowspan="3">房屋使用面积</th>
									<th rowspan="3">实验室类型</th>
									<th rowspan="3">所属学科</th>
									<th colspan="3">教师获奖与成果</th>
									<th rowspan="3">学生获奖情况</th>
									<th colspan="5">论文和教材情况</th>
									<th colspan="5">科研及社会服务情况</th>
									<th colspan="3">毕业设计和论文人数</th>
									<th colspan="6">开放实验</th>
									<th rowspan="3">兼任人员数</th>
									<th colspan="2">实验教学运行经费</th>
								</tr>
								<tr>
						<th rowspan="3">国家级</th>
                        <th rowspan="3">省部级</th>  
                        <th rowspan="3">发明专利</th>
                        <th colspan="2">三大检索收录</th>
                        <th colspan="2">核心刊物</th> 
                        <th rowspan="3">实验教材</th>
                        <th colspan="2">科研项目数</th>
                        <th rowspan="3">社会服务项目数</th>
                        <th colspan="2">教研项目数</th>  
                        <th rowspan="3">专科生人数</th>
                        <th rowspan="3">本科生人数</th>
                        <th rowspan="3">研究生人数</th>
                        <th colspan="2">实验个数</th>
                        <th colspan="2">实验人数</th>
                        <th colspan="2">实验人时数</th>
                        <th rowspan="2">小计</th>
                        <th rowspan="2">教学实验年材料消耗费</th>
                        </tr>
                        
                        <tr>
                        <th>教学</th>
                        <th>科研</th>
                        <th>教学</th>
                        <th>科研</th>
                        <th>省部级以上</th>
                        <th>其他</th>
                        <th>省部级以上</th>
                        <th>其他</th>
                        <th>校内</th>
                        <th>校外</th>
                        <th>校内</th>
                        <th>校外</th>
                        <th>校内</th>
                        <th>校外</th>
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
										<td>${curr[24]}</td>
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