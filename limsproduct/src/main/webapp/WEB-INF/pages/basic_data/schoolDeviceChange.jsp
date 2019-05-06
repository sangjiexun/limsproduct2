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
                <span>教学科研仪器设备增减变动情况表</span>
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                <input type="button" class="r btn a-no mr5 mt5" value="打 印"  onclick=" printPreview();" id="myPrint">
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceChangeTxt?yearCode=${yearCode}">导出txt</a>
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportSchoolDeviceChange?yearCode=${yearCode}">导出excel</a>
           </sec:authorize>
            </p>
  
   
<div class="content_ma TabbedPanelsContent">
	<%-- <div class="search">
                     <a class="search-button  " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">基表1-教学科研仪器设备表</a> 
                    <a class="search-button  search-button-change" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">基表2-教学科研仪器设备增减变动情况表</a> 
                      <a class="search-button " href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">基表3-贵重仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">基表4-教学实验项目表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
               		<a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">基表7-实验室经费情况表</a>
                </div> --%>
	<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">
		
		
		<table class="tab2" style="width:100%;">
            		<tr>
            			<td>
            				<span class="l ml20 mt3">学年：</span>
            				<select class="chzn-select l"  name="yearCode" style="width:200px">
							  <option value="">-------------请选择------------</option>
							  <c:forEach items="${schoolYearMap}" var="item">
							  		<option value="${item.key }">${item.value}</option>
				              </c:forEach>
				              </select>
            			</td>
            			<td>
							<input class="btn r mr15 cancel-submit" type="button" value="取消" onclick="cancel();">
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
                        <th rowspan="3">学校代码</th> 
                        <th colspan="4">上学年末实有数</th>
                        <th colspan="2">本学年增加数</th>
                        <th colspan="2">本学年减少数</th>
                        <th colspan="4">本学年末实有数</th>                       
                    </tr>   
                    <tr>
                        <th rowspan="2">台件</th>
                        <th rowspan="2">金额</th>  
                        <th colspan="2">其中10万元（含）以上</th>
                        <th rowspan="2">台件</th>
                        <th rowspan="2">金额</th> 
                        <th rowspan="2">台件</th>
                        <th rowspan="2">金额</th>
                        <th rowspan="2">台件</th>
                        <th rowspan="2">金额</th>  
                        <th colspan="2">其中10万元（含）以上</th>     
                    </tr>  
                    <tr>
                           <th>台件</th>
                           <th>金额</th>  
                           <th>台件</th>
                           <th>金额</th>  
                    </tr>     
            </thead> 
            <tbody>
						 
						<tr>
						 <td></td>
						 <td>${report.deviceNumberLast}</td> <td>${report.devicePriceLast}</td> 
						 <td>${report.deviceNumberValueLast}</td> <td>${report.devicePriceValueLast}</td>						  
						 <td>${report.deviceNumberAdd}</td> <td>${report.devicePriceAdd}</td>
						 <td>${report.deviceNumberReduce}</td> <td>${report.devicePriceReduce}</td>						   
						 <td>${report.deviceNumberThis}</td> <td>${report.devicePriceThis}</td>
						 <td>${report.deviceNumberValueThis}</td> <td>${report.devicePriceValueThis}</td>
					  </tr>	 				
		     		         
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
     	window.location.href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1";
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
   