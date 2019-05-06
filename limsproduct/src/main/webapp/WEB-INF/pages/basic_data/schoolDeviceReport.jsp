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
</head>
<body>    
    <div class="main_container cf rel w95p ma mb60">
      <div class="img_box">
            <p class="more">
                <span>教学科研仪器设备表</span>
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                <input type="button" class="btn r a-no mt5 mr5" value="打 印"  id="myPrint">
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportReportTxt">导出txt</a>
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportReport">导出excel</a>
                </sec:authorize>
            </p>
  <div class="content_ma TabbedPanelsContent">
 
<%-- <div class="search">
                    <a class="search-button  " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
                    <a class="search-button  search-button-change" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">基表1-教学科研仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">基表2-教学科研仪器设备增减变动情况表</a> 
                      <a class="search-button " href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">基表3-贵重仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">基表4-教学实验项目表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
               		<a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">基表7-实验室经费情况表</a>
                </div> --%>

	<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">
		
		<table class="tab2" style="width:100%;">
            		<tr>
            			<td >
            				<span class="l ml20 mt3">学年：</span>
            				<select class="chzn-select l"  name="yearCode" style="width:200px">
							  <option value="0">-------------请选择------------</option>
							  <c:forEach items="${schoolYearMap}" var="item">
							  		<c:if test="${item.key eq yearId}">
							  			<option value="${item.key }" selected>${item.value}</option>
									</c:if>
									<c:if test="${item.key ne yearId}">
							  			<option value="${item.key }">${item.value}</option>
									</c:if>
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
            <table class="">
            <thead>
                 <tr>                   
                        <th>学校代码</th>
                        <th>仪器编号</th>
                        <th>分类号</th>
                        <th>仪器名称</th>
                        <th>型号</th>
                        <th>规格</th>
                        <th>仪器来源</th>
                        <th>国别码</th>
                        <th>单价</th>
                        <th>购置日期</th>
                        <th>现状码</th>
                        <th>使用方向</th>
                        <th>单位编号</th>
                        <th>单位名称</th>
                        <th>报废日期</th>
                    </tr>          
            </thead> 
            <tbody>
						 <c:forEach items="${schoolDeviceReportLists}" var="curr" varStatus="i">
						<tr>
						 <td>${curr[0]}</td>
						 <td>${curr[1]}</td>
						 <td>${curr[2]}</td>
						 <td>${curr[3]}</td>
						 <td>${curr[4]}</td>
						 <td>${curr[5]}</td>
						 <td>
						        <c:if test="${curr[6] eq 1}">购置</c:if>
						        <c:if test="${curr[6] eq 2}">捐赠</c:if>
						        <c:if test="${curr[6] eq 3}">自制</c:if>
						        <c:if test="${curr[6] eq 4}">校外调入</c:if>
						</td>
						 <td>${curr[7]}</td>
						 <td>${curr[8]}</td>
						 <td>${curr[9]}</td>
						 <td>
						        <c:if test="${curr[10] eq 1}">在用</c:if>
						        <c:if test="${curr[10] eq 2}">多余</c:if>
						        <c:if test="${curr[10] eq 3}">待修</c:if>
						        <c:if test="${curr[10] eq 4}">待报废</c:if>
						        <c:if test="${curr[10] eq 5}">报废</c:if>
						        <c:if test="${curr[10] eq 8}">降档</c:if>
						        <c:if test="${curr[10] eq 9}">其它</c:if>						 
					   </td>
						 <td>
					            <c:if test="${curr[11] eq 1}">教学为主</c:if>
						        <c:if test="${curr[11] eq 2}">科研为主</c:if>						 
						 </td>
						 <td>${curr[12]}</td>
						 <td>${curr[13]}</td>
						 <td>${curr[14]}</td>
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
    	window.location.href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1";
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