<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%--<fmt:setBundle basename="bundles.projecttermination-resources" />--%>

<html lang="en">
<head>
    <meta name="decorator" content="none" />
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_s.css">
	--%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/printShow.css" media="print">
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script language="javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>


<script language="javascript" type="text/javascript">
	function print(){
	    $("#my_show").jqprint({

		});
	}
</script>

	<title>实验实训室项目验收申请书</title>
<style>
body{
	margin:0;
	padding:0;
	font-size:14px;
	font-family: Microsoft YaHei;
}
ul{
	list-style:none;
	padding:0;
	margin:0;
}
td{
	margin:0;
	padding:0;
}
.hand_one{
	width: 800px;
	margin: 0 auto;
	text-align: center;
	margin-bottom: 50px;
}
.hand_img{
	margin-top: 50px;
	font-size: 42px;
	font-family: "Arial","Hiragino Sans GB", \5fae\8f6f\96c5\9ed1, "Helvetica", "sans-serif";
}
.hand_img img{
	width:530px;
}
.tilte_t{
	font-size: 30px;
    margin: 50px 0;
}
.subtitle{
	font-size:32px;
	margin-bottom: 100px;
}
.in_put ul{
	font-size:20px;
}
.in_put ul li input{
	border:0px;
	border-bottom: 1px solid #000;
	margin-bottom: 18px;
}
.tilte_le{
	font-size: 18px;
    margin: 150px 0;
}
.new_tlte{
	float:left;
	font-size: 20px;
    margin-bottom: 8px;
    text-align: left;
}
.new_T_tlte{
	font-size: 20px;
    margin-bottom: 8px;	
}
.time_one{
	height:85px;
	width:20%;
}
.Content_filling{
	width:16%;
}
.Expected_time{
	width:16%;
}
.Content_fill{
	width:14%;
}
.Actual_situation{
	width:14%;
}
.end_e{
	width:20%;
}
.Survey_s{
	height:86px;
}
.measure_m{
    height:43px;
}
.imp_content{
	border: 1px solid #808080;
    border-top: 0;
}
.fill_content{
    width:100%;
    height:100%;
    margin-top: 8px;
}
.xiang_left{
	float:left;
}
.kao_right{
	float:right;
}
.kao_right div input{
	border:0;
	border-bottom:1px solid #000;
	margin:8px 0;
}
.kuang_centent{
	border: 1px solid #808080;
    overflow: hidden;
}
.yang-yan{
	border-left: 1px solid #808080;
	border-bottom: 1px solid #808080;
	border-right: 1px solid #808080;
	overflow: hidden;
}
.y_centent_nt{
	margin:40px 0;
	font-size: 20px;
}
.y_centent_nt p{
	float:left;
	margin: 0;
}
.kuang_biankong{
	border: 1px solid #808080;
    overflow: hidden;
    border-top:0;
}
.kongzhi_kuandu td{
	width:25%;
}
.bottom_tom{
	margin: 40px 0;
}
.shiyan_ceping{
	float:left;
}
.shiyan_ceping p{
	margin:0;
	text-align: left;
}
.y_YY{
	overflow:hidden;
}
.list_page{
	text-align: left;
}
.page_end{
	margin-top:40px;
}
.in_put{
width: 380px;
margin: 0px auto;
text-align: left !important;
}
.in_py span{
font-size: 20px;
}
.komhzji{
 width:800px;
}
.Y-jifi{
width:800px;
}
table{
border-collapse: collapse;
}
</style>
</head>
<body>
	<div class="hand_one" id="my_show">
		<div>
		    <input type="button" value="返回" onclick="window.history.go(-1);">
			<input type="button" value="打印" onclick="print()">
			<a href="${pageContext.request.contextPath}/downloadProjectAcceptance?idKey=${projectAcceptanceApplication.id}"><input type="button" value="写入Word文档：" ></a>
			<input type="text" id="T2B" size="18" value="">
						<div class="hand_img">
			   <!--<img src="${pageContext.request.contextPath}/images/logoWord.jpg" alt="">-->
							<spring:message code="title.school.name" />
			</div>
			<div class="tilte_t">实验(实训)室建设项目</div>
			<div class="subtitle">验收申请书</div>
			<form:form name="subForm" method="post" modelAttribute="projectAcceptanceApplication" >
			<div class="in_put">
				<ul>
					<li>项目名称：${projectAcceptanceApplication.projectName}</li>
					<li>申&nbsp; 请&nbsp;人：${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.cname}</li>
					<li>系&nbsp;&nbsp;&nbsp;&nbsp;(部)：${projectAcceptanceApplication.schoolAcademy.academyName}</li>
					<li>联系电话：${projectAcceptanceApplication.projectStartedReport.labConstructApp.user.telephone}</li>
					<li>申请时间：<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectAcceptanceApplication.projectStartedReport.labConstructApp.appDate.time}"/></li>
				</ul>
			</div>
			<div class="tilte_le">教务处制</div>
		</div>
		<div>
			<div class="new_tlte">一 项目完成情况</div>
			<div style="clear:both"></div>
			<div class="komhzji">
              <table width="800px" border="1" cellspacing="0">
				  <tr>
				    <td class="time_one">立项时间</td>
				    <td class="Content_filling">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectAcceptanceApplication.projectStartDate.time}"/></td>
				    <td class="Expected_time">预期完成时间</td>
				    <td class="Content_fill">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectAcceptanceApplication.expectCompleteDate.time}"/></td>
				    <td calss="Actual_situation">实际完成情况</td>
				    <td class="end_e">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectAcceptanceApplication.realityCompleteDate.time}"/></td>
				  </tr>
				  <tr>
				    <td class="Survey_s" rowspan="2">原实验(实训)室概况</td>
				    <td class="measure_m">面积(㎡)</td>
				    <td>地点</td>
				    <td colspan="2">设备价值(万元)</td>
				    <td>开设实验(实训)项目数</td>
				  </tr>
				  <tr>
				    <td class="measure_m">${projectAcceptanceApplication.originalLabroomArea}</td>
				    <td>&nbsp;${projectAcceptanceApplication.originalLabroomAdd}</td>
				    <td colspan="2">&nbsp;${projectAcceptanceApplication.originalLabroomValue}</td>
				    <td>&nbsp;${projectAcceptanceApplication.originalLabroomItem}</td>
				  </tr>
				  <tr>
				    <td class="Survey_s" rowspan="2">建成后实验室(实训)室概况</td>
				    <td>面积(㎡)</td>
				    <td>地点</td>
				    <td colspan="2">设备价值(万元)</td>
				    <td>新增实验(实训)项目数</td>
				  </tr>
				  <tr>
				    <td>&nbsp;${projectAcceptanceApplication.targetLabroomArea}</td>
				    <td>&nbsp;${projectAcceptanceApplication.targetLabroomAdd}</td>
				    <td colspan="2">&nbsp;${projectAcceptanceApplication.targetLabroomValue}</td>
				    <td>&nbsp;${projectAcceptanceApplication.targetLabroomItem}</td>
				  </tr>
				  <tr>
				    <td class="Survey_s" rowspan="2">面向专业(方向)数</td>
				    <td rowspan="2">&nbsp;${projectAcceptanceApplication.majorAmount}</td>
				    <td rowspan="2">专业名称</td>
				    <td colspan="3">&nbsp;
				       <c:forEach items="${map}" var="s" >
			           <c:if test="${projectAcceptanceApplication.id==s.key}"> 
			           <c:forEach items = "${s.value }" var="k">${k.majorName } </c:forEach>
		               </c:if></c:forEach> 
				    </td>
				    </tr>
				  <tr>
				    <td colspan="3">&nbsp;</td>
				  </tr>
				  <tr>
				    <td class="Survey_s" rowspan="2">面向课程数</td>
				    <td rowspan="2">&nbsp;${projectAcceptanceApplication.courseAmount}</td>
				    <td rowspan="2">课程名称</td>
				    <td colspan="3">&nbsp;
				       <c:forEach items="${courseMap}" var="s" >
			           <c:if test="${projectAcceptanceApplication.id==s.key}"> 
			           <c:forEach items = "${s.value }" var="k">${k.courseName } </c:forEach>
		               </c:if></c:forEach>
				    </td>
				    </tr>
				  <tr>
				    <td colspan="3">&nbsp;</td>
				  </tr>
				  <tr>
				    <td class="Survey_s">可开设实验室(实训)项目数</td>
				    <td>&nbsp;${projectAcceptanceApplication.expectLabItem}</td>
				    <td colspan="3">实际开设实验(实训)项目数</td>
				    <td>&nbsp;${projectAcceptanceApplication.realityLabItem}</td>
				  </tr>
				</table>
            </div>
            <div class="imp_content">
	            <div class="new_tlte">1.立项概况</div>
				<div style="clear:both"></div>
				<div class="fill_content">${projectAcceptanceApplication.projectSituation}</div>
		    </div>
            <div class="imp_content">
	            <div class="new_tlte">2.立项预期效益</div>
				<div style="clear:both"></div>
				<div class="fill_content">${projectAcceptanceApplication.projectExpectedBenefits}</div>
		    </div>	
            <div class="imp_content">
	            <div class="new_tlte">3.建设完成情况</div>
				<div style="clear:both"></div>
				<div class="fill_content">${projectAcceptanceApplication.constructCondition}</div>
		    </div>	
            <div class="imp_content">
	            <div class="new_tlte">4.实际效益</div>
				<div style="clear:both"></div>
				<div class="fill_content">${projectAcceptanceApplication.actualBenefits}</div>
		    </div>
		    <div>
				<div class="new_tlte">二 经费使用情况</div>
				<div style="clear:both"></div>
				<div  class="Y-jifi">
					<table width="100%" border="1" cellspacing="0">
						<tr>
							<td></td>
							<td>基础费</td>
							<td>设备费</td>
							<td>业务费</td>
							<td>合&nbsp;&nbsp;计</td>
						</tr>
						<tr>
							<td>立项预算</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>实际使用</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</div>		    	
		    </div>
		    <div>
				<div class="new_tlte">三 设备费使用清单</div>
				<div style="clear:both"></div>
				<div  class="Y-jifi">
					<table  width="100%" border="1" cellspacing="0">
						<tr>
							<td>设备名称</td>
							<td>型号</td>
							<td>数量</td>
							<td>单价(万元)</td>
							<td>总价(万元)</td>
							<td>采购方式</td>
							<td>安置地点</td>
						</tr>
						<c:forEach items="${projectAcceptanceApplication.projectAcceptDevices}" var="device" varStatus="i">
						<tr>
							<td>${device.equipmentName}</td>
							<td>${device.format}</td>
							<td>${device.amount}</td>
							<td>${device.unitPrice}</td>
							<td>${device.amount}*${device.unitPrice}</td>
							<td></td>
							<td></td>
						</tr>
						</c:forEach>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</div>		    	
		    </div>
		    <div>
		    	<div class="new_tlte">四 建成后能开设的实验（实训）项目</div>
				<div style="clear:both"></div>
				<div  class="Y-jifi">
					<table width="100%" border="1" cellspacing="0">
						<tr>
							<td>序号</td>
							<td>实验(实训)名称</td>
							<td>实验(实训)性质</td>
							<td>所需学时</td>
							<td>对象</td>
							<td>设备台套数</td>
							<td>同时开设组数</td>
							<td>每组实验(实训)人数</td>
							<td>有无实验(实训)大纲</td>
							<td>有无实验(实训)指导书</td>
							<td>使用情况(是否开设)</td>
						</tr>
						<c:forEach items="${projectAcceptanceApplication.projectAcceptCompletionItems}" var="item" varStatus="i">
						<tr>
							<td>${i.count}</td>
							<td>${item.experimentName}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						</c:forEach>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
			
					</table>
				</div>
				<div class="xiang_left">
					注：请附上学生的实验（实训）报告及反馈信息。
				</div>				
		    </div>
		    <div style="clear:both"></div>
		    <div>
		    	<div class="new_tlte">五 验收组意见</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
					<div>
						
					</div>
					<div class="kao_right">
						<div>组长(签名)：</div>
						<div>成员(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
				</div>	
		    </div>
		    <div>
		    	<div class="new_tlte">六 系（部）评价</div>
				<div style="clear:both"></div>
			    <div class="kuang_centent">	
					<div>
						
					</div>
					<div class="kao_right">
						<div>系（部）主任(签名)：</div>
						<div>系（部）公章：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
			    </div>		
		    </div>
		    <div>
		    	<div class="new_tlte">七 教务结论</div>
				<div style="clear:both"></div>
			    <div class="kuang_centent">	
					<div>
						
					</div>
					<div class="kao_right">
						<div>学校主管领导(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月
						<span>&nbsp;</span>日</div>
					</div>
			    </div>		
		    </div>
		    <div>
		    	<div class="new_tlte">八 学校意见</div>
				<div style="clear:both"></div>
			    <div class="kuang_centent">	
					<div>
						
					</div>
					<div class="kao_right">
						<div>学校主管领导(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月
						<span>&nbsp;</span>日</div>
					</div>
			    </div>		
		    </div>
		    <div class="y_centent_nt">
			    <p>附件一：实验（实训）室建设立项报告</p><br/>
			    <p>附件二：项目总结报告</p><br/>
			    <p>附件三：实验（实训）项目测试报告</p><br/>
			    <p>附件四：学生实验（实训）报告（每个实验实训项目一份）</p><br/>
			    <p>附件五：实验（实训）指导书和大纲</p><br/>
			    <table id="document">
						<tr >
							 <td>文档名称</td>
							 <td>操作&nbsp;</td>
						 </tr>
					    </table>	
                	  <table>
						  <tbody id="doc">
						  	<c:forEach items="${CommonDocuments}" var="doc" varStatus="i">
								<tr >
							 	<td>${doc.documentName}</td>
								<td><a href="${pageContext.request.contextPath}/labconstruction/projectAcceptance/downloadFile?idkey=${doc.id}">下载</a></td>
								</tr>
							</c:forEach>	
						  </tbody>
					 </table
			</div>
		    <div class="bottom_tom">
		    	<div class="new_tlte">实验项目测试报告（一）</div>
				<div style="clear:both"></div>
			    <table width="800px" border="1" cellspacing="0">
			    	<tr class="kongzhi_kuandu">
			    		<td>测试实验名称</td>
			    		<td></td>
			    		<td>测试时所用的时间</td>
			    		<td></td>
			    	</tr>
			    </table>				
			    <div class="kuang_biankong">	
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>实验测试结果评价：</p>
	                        <div></div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>测试人：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
			    </div>		
		    </div>
		    <div>
		    	<div class="new_tlte">实验项目测试报告（二）</div>
				<div style="clear:both"></div>
			    <table width="800px" border="1" cellspacing="0">
			    	<tr class="kongzhi_kuandu">
			    		<td>测试实验名称</td>
			    		<td></td>
			    		<td>测试时所用的时间</td>
			    		<td></td>
			    	</tr>
			    </table>				
			    <div class="kuang_biankong">	
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>实验测试结果评价：</p>
	                        <div></div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>测试人：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
			    </div>		
		    </div>		    			    
		</div>
	</div>
</body>
</form:form>
</html>
