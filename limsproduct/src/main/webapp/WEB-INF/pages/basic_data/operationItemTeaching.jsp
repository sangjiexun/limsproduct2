<%@ page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
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
                <span>教学实验项目表</span>
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                <input type="button" class="r btn a-no mr5 mt5" value="打 印"  id="myPrint">
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportOperationItemTeachingTxt?yearCode=${yearId}">导出txt</a>
                <a class="r a-no mr5" href="${pageContext.request.contextPath}/basic_data/exportOperationItemTeaching?yearCode=${yearId}">导出excel</a>
           		</sec:authorize>
            </p>
  <div class="content_ma TabbedPanelsContent">
					<%-- <div class="search">
                     <a class="search-button  " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button " href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">基表1-教学科研仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">基表2-教学科研仪器设备增减变动情况表</a> 
                      <a class="search-button " href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">基表3-贵重仪器设备表</a> 
                    <a class="search-button search-button-change" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">基表4-教学实验项目表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
               		<a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">基表7-实验室经费情况表</a>
                </div> --%>
                       
								<form:form name="queryForm" method="Post" action="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">
								
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
									<th>实验编号</th>
									<th>实验名称</th>
									<th>实验类别</th>
									<th>实验类型</th>
									<th>实验所属学科</th>
									<th>实验要求</th>
									<th>实验者类别</th>
									<th>实验者人数</th>
									<th>每组人数</th>
									<th>实验学时数</th>
									<th>实验室编号</th>
									<th>实验室名称</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${operationItemTeachingLists}" var="curr"
									varStatus="i">
									<tr>
										<td>${curr[0]}</td>
										<td>${curr[1]}</td>
										<td>${curr[2]}</td>
										<td>
								<c:if test="${curr[3] eq 454}">基础</c:if>
						        <c:if test="${curr[3] eq 455}">专业基础</c:if>
						        <c:if test="${curr[3] eq 456}">专业</c:if>
						        <c:if test="${curr[3] eq 457}">其他</c:if>
										</td>
										<td>
										<c:if test="${curr[4] eq 464}">演示性</c:if>
										<c:if test="${curr[4] eq 465}">验证性</c:if>
										<c:if test="${curr[4] eq 466}">综合性</c:if>
										<c:if test="${curr[4] eq 467}">设计研究</c:if>
										<c:if test="${curr[4] eq 468}">其它</c:if>
										</td>
										<td>${curr[5]}</td>
										<td>
								<c:if test="${curr[6] eq 485}">必修</c:if>
								<c:if test="${curr[6] eq 486}">选修</c:if>
								<c:if test="${curr[6] eq 487}">其他</c:if> 
										</td>
										<td>
								<c:if test="${curr[7] eq 470}">博士</c:if>
								<c:if test="${curr[7] eq 471}">硕士</c:if>
								<c:if test="${curr[7] eq 472}">本科</c:if>
								<c:if test="${curr[7] eq 473}">专科</c:if>
								<c:if test="${curr[7] eq 474}">其他</c:if>
										</td>
										<td>${curr[8]}</td>
										<td>${curr[9]}</td>
										<td>${curr[10]}</td>
										<td>${curr[11]}</td>
										<td>${curr[12]}</td>
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
						
						
						
    </div>
    <script type="text/javascript" src="js/pignose.tab.min.js"></script>
    <script type="text/javascript">
    function cancel(){
    	window.location.href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1";
    }
        $(function () {
            $('.tab').pignoseTab({
                animation: true,
                children: '.tab'
            });
        });
        $(function () {
            $(".slide_box").tabslider({
                handler: function (s) {}
            })
        })

        /*var config = {
            '.chosen-select': {},
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }*/
        $(".chosen-select").chosen({
            disable_search_threshold: 10,
            no_results_text: "Oops, nothing found!",
            width: "100"
        });

        $(".theory_experiment").find("td:first").addClass("t_e");
        $(".theory_experiment").click(function () {
            $(this).addClass("theory_experiment_icon").siblings().removeClass("theory_experiment_icon theory_icon oneself_icon")
        });
        $(".theory").find("td:first").addClass("t");
        $(".theory").click(function () {
            $(this).addClass("theory_icon").siblings().removeClass("theory_experiment_icon theory_icon oneself_icon")
        })
        $(".oneself").find("td:first").addClass("o");
        $(".oneself").click(function () {
            $(this).addClass("oneself_icon").siblings().removeClass("theory_experiment_icon theory_icon oneself_icon")
        })
    </script>
        <script>
        function cancel(){
        	window.location.href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1";
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
    </body>