<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
                <span>贵重仪器设备表</span>
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                <input type="button" class="r btn a-no mr5 mt5" value="打 印"  id="myPrint">
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceValueTxt">导出txt</a>
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceValue">导出excel</a>
            	</sec:authorize>
            </p>
  <div class="content_ma TabbedPanelsContent">
    
<%-- <div class="search">
                     <a class="search-button  " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">基表1-教学科研仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">基表2-教学科研仪器设备增减变动情况表</a> 
                      <a class="search-button   search-button-change" href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">基表3-贵重仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">基表4-教学实验项目表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
               		<a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">基表7-实验室经费情况表</a>
                </div> --%>
	<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">
		<table class="tab2" style="width:100%;">
            		<tr>
            			<td>
            				<span class="l ml20 mt3">学年：</span>
            				<select class="chzn-select l"  name="yearCode" style="width:200px">
							  <option value="">-------------请选择------------</option>
							  <c:forEach items="${yearCodeMap}" var="item">
							  		<option value="${item.id }">${item.yearName}</option>
				              </c:forEach>
				              </select>
            			</td>
            			<td>
							<input class="btn r mr15" type="button" value="取消" onclick="cancel();">
							<input class="btn r mr5" type="submit" value="查询" >
            			</td>
            		</tr>
            	</table>
	</form:form>
	
 <div class="content-box">				
<div id="contentarea">
<div id="content">
<div class="content-box">
  <div id="myShow">
            <table class="change_tab">
            <thead>
                 <tr>                   
                        <th rowspan="2">学校代码</th>
                        <th rowspan="2">仪器编号</th>
                        <th rowspan="2">分类号</th>
                        <th rowspan="2">仪器名称</th>
                        <th rowspan="2">单价</th>
                        <th rowspan="2">型号</th>
                        <th rowspan="2">规格</th>
                        <th colspan="4">使用机时</th>
                        <th rowspan="2">测样数</th>
                        <th colspan="3">培训人员数</th>
                        <th rowspan="2">教学实验项目数</th>
                        <th rowspan="2">科研项目数</th>
                        <th rowspan="2">社会服务项目数</th>
                        <th colspan="2">获奖情况</th>
                        <th colspan="2">发明专利</th>
                        <th colspan="2">论文情况</th>
                        <th rowspan="2">负责人姓名</th>      
                    </tr>
                    <tr>
                        <th>教学</th>
                        <th>科研</th>                     
                        <th>社会服务</th>
                        <th>其中开放使用机时</th>
                        <th>学生</th>
                        <th>教师</th>
                        <th>其他</th>
                        <th>国家级</th>
                        <th>省部级</th>                     
                        <th>教师</th>
                        <th>学生</th>
                        <th>三大检索</th>
                        <th>核心刊物</th>
                    </tr>          
            </thead> 
            <tbody>
						 <c:forEach items="${schoolDeviceValueLists}" var="curr" varStatus="i">
						<tr>
						 <td>${curr[0]}</td>
						 <td>${curr[1]}</td>
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
     function cancel(){
     	window.location.href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1";
     }
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