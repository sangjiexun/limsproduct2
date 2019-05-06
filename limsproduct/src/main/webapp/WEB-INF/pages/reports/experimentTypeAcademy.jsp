<%@ taglib prefix="sec"
		   uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false"
		 contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<head>
	<meta name="decorator" content="iframe" />
	<link href="${pageContext.request.contextPath}/css/iconFont.css"
		  rel="stylesheet">
	<!-- 点击框的样式 -->
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/css/jquery.coolautosuggest.css" />
	<!-- 下拉框的样式 -->
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/highcharts/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.coolautosuggest.js"></script>
	<!-- 打印插件的引用 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
	<c:set var="A04" scope="session" value="" />
	<c:set var="A01" scope="session" value="" />
	<c:set var="A07" scope="session" value="" />
	<c:set var="A03" scope="session" value="" />
	<c:set var="A06" scope="session" value="" />
	<c:forEach items="${list}" var="curr" varStatus="i">
		<c:choose>
			<c:when test="${curr[20]=='04'}">
				<c:set var="A04" scope="session" value="${curr[1]},${curr[2]},${curr[3]+curr[7]},${curr[4]+curr[8]},${curr[11]},${curr[12]},${curr[15]},${curr[16]}" />
			</c:when>
			<c:when test="${curr[20]=='01'}">
				<c:set var="A01" scope="session" value="${curr[1]},${curr[2]},${curr[3]+curr[7]},${curr[4]+curr[8]},${curr[11]},${curr[12]},${curr[15]},${curr[16]}" />
			</c:when>
			<c:when test="${curr[20]=='07'}">
				<c:set var="A07" scope="session" value="${curr[1]},${curr[2]},${curr[3]+curr[7]},${curr[4]+curr[8]},${curr[11]},${curr[12]},${curr[15]},${curr[16]}" />
			</c:when>
			<c:when test="${curr[20]=='03'}">
				<c:set var="A03" scope="session" value="${curr[1]},${curr[2]},${curr[3]+curr[7]},${curr[4]+curr[8]},${curr[11]},${curr[12]},${curr[15]},${curr[16]}" />
			</c:when>
			<c:when test="${curr[20]=='06'}">
				<c:set var="A06" scope="session" value="${curr[1]},${curr[2]},${curr[3]},${curr[4]},${curr[7]},${curr[8]},${curr[11]},${curr[12]}" />
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
	</c:forEach>
	<script type="text/javascript">
        $(function () {
            $('#container').highcharts({
                chart: {
                    type: 'column'  //指定图表的类型，默认是折线图（line）
                },
                title: {
                    text: '学院实验类型情况' //大标题
                },
                subtitle: {
                    text: ''  //副标题
                },
                xAxis: {  //横轴
                    categories: [
						'总个数<br>实验项目汇总','实验总学时数<br>实验项目汇总','个数<br>演示验证型','实验学时数<br>演示验证型','个数<br>综合型','实验学时数<br>综合型','个数<br>设计型','实验学时数<br>设计型'
                    ]
                },
                yAxis: {
                    min: 0,  //坐标轴的最小值
                    title: {
                        text: '数值'  //指定y轴的标题
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.2f}</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                credits:{
                    enabled:false // 禁用版权信息（右下角的水印）
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{  //加载的数据
                    name: '商学院',
                    data: [${A04}]
                },{  //加载的数据
                    name: '金融贸易学院',
                    data: [${A01}]

                },{  //加载的数据
                    name: '文法学院',
                    data: [${A07}]

                },{  //加载的数据
                    name: '管理学院',
                    data: [${A03}]

                },{  //加载的数据
                    name: '外国语学院',
                    data: [${A06}]

                }],

                exporting: {
                    enabled: false  //是否支持导出图片
                }
            });
        });
        function cancel(){
            window.location.href="${pageContext.request.contextPath}/report/experimentTypeAcademy?currpage=1"
        }
	</script>

</head>
<body>
<!--导航  -->
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.report.system" /></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.report.academytype" /></a></li>
		</ul>
	</div>
</div>
<!--导航结束  -->


<div class="right-content">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab1 selected" id="s1">
				<a href="javascript:void(0)">学院实验类型情况表</a>
			</li>
		</ul>
		<div class="TabbedPanelsContentGroup1">
			<div class="TabbedPanelsContent">
				<div class="content-box">
				<div class="tool-box">
					<form id="subform" name='f' action='${pageContext.request.contextPath}/report/experimentTypeAcademy' method='POST'>
					<ul>
						<li>学期:<select class="chzn-select" id="term" name="term" style="width:180px">
							<c:forEach items="${schoolTerms}" var="current">
								<c:if test="${current.id == term}">
									<option value ="${current.id}" selected>${current.termName} </option>
								</c:if>
								<c:if test="${current.id != term}">
									<option value ="${current.id}" >${current.termName} </option>
								</c:if>
							</c:forEach>
						</select></li>
						<li><input type="submit" value="查询">
							<input class="cancel-submit" type="button" onclick="cancel()" value="取消"></li>

					</ul>
					</form>
				</div>
				</div>
			</div>
			<script src="${pageContext.request.contextPath}/js/highcharts/highcharts.js"></script>
			<script src="${pageContext.request.contextPath}/js/highcharts/exporting.js"></script>
			<script src="${pageContext.request.contextPath}/js/highcharts/grid.js"></script>
			<div class="content-box">
			<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
			<br />
			</div>
			<div class="content-box">
				<table border="1"  cellspacing="0"  cellpadding="0">
					<thead>
					<tr height="20px">
						<th height="20px" rowspan="3" align="center">序号</th>
						<th height="20px" rowspan="3" align="center">单位名称</th>
						<th height="20px" colspan="2" align="center">实验项目汇总</th>
						<th height="20px" colspan="4" align="center">演示型</th>
						<th height="20px" colspan="4" align="center">验证型</th>
						<th height="20px" colspan="4" align="center">综合型</th>
						<th height="20px" colspan="4" align="center">设计研究型</th>
					</tr>
					<tr height="20px">
						<th height="20px" rowspan="2">总个数</th>
						<th height="20px" rowspan="2">实验总学时数</th>
						<th height="20px" rowspan="2">个数</th>
						<th height="20px" rowspan="2">实验学时数</th>
						<th height="20px" colspan="2" align="center">占总实验%</th>
						<th height="20px" rowspan="2">个数</th>
						<th height="20px" rowspan="2">实验学时数</th>
						<th height="20px" colspan="2" align="center">占总实验%</th>
						<th height="20px" rowspan="2">个数</th>
						<th height="20px" rowspan="2">实验学时数</th>
						<th height="20px" colspan="2" align="center">占总实验%</th>
						<th height="20px" rowspan="2">个数</th>
						<th height="20px" rowspan="2">实验学时数</th>
						<th height="20px" colspan="2" align="center">占总实验%</th>
					</tr>
					<tr >
						<th height="20px">个数</th>
						<th height="20px">实验学时数</th>
						<th height="20px">个数</th>
						<th height="20px">实验学时数</th>
						<th height="20px">个数</th>
						<th height="20px">实验学时数</th>
						<th height="20px">个数</th>
						<th height="20px">实验学时数</th>

					</tr>
					</thead>
					<tbody>
					<c:set var="a0">0</c:set>
					<c:set var="a1">0</c:set>
					<c:set var="a2">0</c:set>
					<c:set var="a3">0</c:set>
					<c:set var="a4">0</c:set>
					<c:set var="a5">0</c:set>
					<c:set var="a6">0</c:set>
					<c:set var="a7">0</c:set>
					<c:set var="a8">0</c:set>
					<c:set var="a9">0</c:set>
					<c:forEach items="${list}" var="curr" varStatus="i">
						<tr>
							<td>${i.count}</td>
							<td>${curr[0]}</td>
							<td>${curr[1]}</td>
							<td>${curr[2]}</td>
							<td>${curr[3]}</td>
							<td>${curr[4]}</td>
							<td><fmt:formatNumber type="number" value="${curr[5]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td><fmt:formatNumber type="number" value="${curr[6]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td>${curr[7]}</td>
							<td>${curr[8]}</td>
							<td><fmt:formatNumber type="number" value="${curr[9]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td><fmt:formatNumber type="number" value="${curr[10]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td>${curr[11]}</td>
							<td>${curr[12]}</td>
							<td><fmt:formatNumber type="number" value="${curr[13]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td><fmt:formatNumber type="number" value="${curr[14]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td>${curr[15]}</td>
							<td>${curr[16]}</td>
							<td><fmt:formatNumber type="number" value="${curr[17]}" pattern="0.00" maxFractionDigits="2"/>%</td>
							<td><fmt:formatNumber type="number" value="${curr[18]}" pattern="0.00" maxFractionDigits="2"/>%</td>

						</tr>
						<c:set var="a0">${a0+curr[1]}</c:set>
						<c:set var="a1">${a1+curr[2]}</c:set>
						<c:set var="a2">${a2+curr[3]}</c:set>
						<c:set var="a3">${a3+curr[4]}</c:set>
						<c:set var="a4">${a4+curr[7]}</c:set>
						<c:set var="a5">${a5+curr[8]}</c:set>
						<c:set var="a6">${a6+curr[11]}</c:set>
						<c:set var="a7">${a7+curr[12]}</c:set>
						<c:set var="a8">${a8+curr[15]}</c:set>
						<c:set var="a9">${a9+curr[16]}</c:set>
					</c:forEach>
					<tr>
						<td colspan="2"><b>合计:</b></td>
						<td>${a0}</td>
						<td>${a1}</td>
						<td>${a2}</td>
						<td>${a3}</td>
						<td><fmt:formatNumber type="number" value="${a2*100/a0}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td><fmt:formatNumber type="number" value="${a3*100/a1}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td>${a4}</td>
						<td>${a5}</td>
						<td><fmt:formatNumber type="number" value="${a4*100/a0}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td><fmt:formatNumber type="number" value="${a5*100/a1}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td>${a6}</td>
						<td>${a7}</td>
						<td><fmt:formatNumber type="number" value="${a6*100/a0}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td><fmt:formatNumber type="number" value="${a7*100/a1}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td>${a8}</td>
						<td>${a9}</td>
						<td><fmt:formatNumber type="number" value="${a8*100/a0}" pattern="0.00" maxFractionDigits="2"/>%</td>
						<td><fmt:formatNumber type="number" value="${a9*100/a1}" pattern="0.00" maxFractionDigits="2"/>%</td>

					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>




</body>
