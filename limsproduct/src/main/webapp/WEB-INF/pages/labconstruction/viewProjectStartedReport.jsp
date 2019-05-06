<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%--<fmt:setBundle basename="bundles.lab-resources"/>--%>


<html lang="en">
<head>
    <meta name="decorator" content="none" />
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_s.css">
	--%><script language="javascript" src="${pageContext.request.contextPath}/js/LodopFuncs.js"></script>
<script language="javascript" type="text/javascript">   
        var LODOP; //声明为全局变量 
	function prn1_preview() {	
		CreateOneFormPage();	
		LODOP.PREVIEW();	
	};
	function prn1_print() {		
		CreateOneFormPage();
		LODOP.PRINT();	
	};
	function prn1_printA() {		
		CreateOneFormPage();
		LODOP.PRINTA(); 	
	};	
	function CreateOneFormPage(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
		LODOP.SET_PRINT_STYLE("FontSize",18);
		LODOP.SET_PRINT_STYLE("Bold",1);
		LODOP.ADD_PRINT_TEXT(50,231,260,39,"打印页面部分内容");
		LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("form1").innerHTML);
	};	                     
	function prn2_preview() {	
		CreateTwoFormPage();	
		LODOP.PREVIEW();	
	};
	function prn2_manage() {	
		CreateTwoFormPage();
		LODOP.PRINT_SETUP();	
	};	
	function CreateTwoFormPage(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单二");
		LODOP.ADD_PRINT_RECT(70,27,634,242,0,1);
		LODOP.ADD_PRINT_TEXT(29,236,279,38,"页面内容改变布局打印");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",18);
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.ADD_PRINT_HTM(88,40,321,185,document.getElementById("form1").innerHTML);
		LODOP.ADD_PRINT_HTM(87,355,285,187,document.getElementById("form2").innerHTML);
		LODOP.ADD_PRINT_TEXT(319,58,500,30,"注：其中《表单一》按显示大小，《表单二》在程序控制宽度(285px)内自适应调整");
	};              
	function prn3_preview(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_全页");
		LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.documentElement.innerHTML);
		LODOP.PREVIEW();	
	};	
</script>
<script language="javascript" type="text/javascript"> 
        var LODOP; //声明为全局变量  
	function readfile(strFilename) {
		LODOP=getLodop();  
		return LODOP.GET_FILE_TEXT(strFilename);
	};	
	function writefile(intWriteMode,strFilename,strText) {
		LODOP=getLodop();  
	 	var strResult=LODOP.WRITE_FILE_TEXT(intWriteMode,strFilename,strText);
		if (strResult=="ok") alert("写入成功！");else alert(strResult);
	};
	function writefileByUTF8(WriteEncode,strFilename,strText) {
		LODOP=getLodop();  
	 	var strResult=LODOP.WRITE_FILE_TEXT(WriteEncode,strFilename,strText);
		if (strResult=="ok") alert("写入成功！");else alert(strResult);
	};	
	function isfileExist(strFilename) {
		LODOP=getLodop();  
		if (LODOP.IS_FILE_EXIST(strFilename)) alert("文件存在！");else alert("文件不存在！");
	};
	function readfileTime(strFilename) {
		LODOP=getLodop();  
		return LODOP.GET_FILE_TIME(strFilename);
	};
	function readINIfile(strPrintTaskName) {
		LODOP=getLodop();  
		return LODOP.GET_PRINT_INIFFNAME(strPrintTaskName);
	};			
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
	<div class="hand_one">
		<div>
		    <input type="button" value="返回" onclick="window.history.go(-1);">
		    <input type="button" value="打印" onclick="javascript:prn3_preview();">
			<a href="${pageContext.request.contextPath}/downloadProjectStartedReport?idKey=${projectStartedReport.id}"><input type="button" value="写入Word文档：" ></a>
            <input type="text" id="T2B" size="18" value="">
						<div class="hand_img">
			   <!--<img src="${pageContext.request.contextPath}/images/logoWord.jpg" alt="">-->
							<spring:message code="title.school.name" />
			</div>
			<div class="tilte_t">实验(实训)室建设项目</div>
			<div class="subtitle">启动报告</div>
			<form:form name="subForm" method="post" modelAttribute="projectStartedReport" >
			<div class="in_put">
				<ul>
					<li>项目名称：${projectStartedReport.projectName}</li>
					<li>申&nbsp; 请&nbsp;人：${projectStartedReport.labConstructApp.user.cname}</li>
					<li>系&nbsp;&nbsp;&nbsp;&nbsp;(部)：${projectStartedReport.schoolAcademy.academyName}</li>
					<li>联系电话：${projectStartedReport.labConstructApp.user.telephone}</li>
					<li>申请时间：<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${projectStartedReport.labConstructApp.appDate.time}"/></li>
				</ul>
			</div>
			<div class="tilte_le">教务处制</div>
		</div>
		<div>
			<div class="new_T_tlte">
				<spring:message code="title.school.name" />实验（实训）室建设项目启动报告
			</div>
		   	<div class="new_tlte">
		    	一、项目基本情况
		    </div>
		    <div style="clear:both"></div>			
			<div class="komhzji">
              <table width="100%" border="1" cellspacing="0">
                  <tr>
                  	<td  height="80">项目名称</td>
                  	<td colspan="3">${projectStartedReport.projectName}</td>
               	  </tr>
                  <tr>
                  	<td   height="80">所在系（部）</td>
                  	<td>${projectStartedReport.schoolAcademy.academyName}</td>
                  	<td>实训室地址及面积（m2）</td>
                  	<td>${projectStartedReport.labAddress}${projectStartedReport.labArea}</td>
                  </tr>
                  <tr>
                  	<td   height="80">申报费用（万元）</td>
                  	<td></td>
                  	<td>批复经费（万元）</td>
                  	<td>${projectStartedReport.feeAmount}</td>
                  </tr>
                  <tr>
                  	<td   height="80">经费编号</td>
                  	<td colspan="3">${projectStartedReport.feeCode}</td>
               	  </tr>
                </table>
            </div>
            <div>
		   	<div class="new_tlte">
		    	二、批复经费明细
		    </div>
		    <div style="clear:both"></div>			
			<div  class="Y-jifi">
              <table width="100%" border="1" cellspacing="0">
                  <tr>
                  	<td  height="80">批复经费（万元）</td>
                  	<td>${projectStartedReport.feeAmount}</td>
                  	<td>其他经费来源及金额</td>
                  	<td colspan="2">${projectStartedReport.otherFee}</td>
               	  </tr>
                  <tr>
                  	<td   height="80" colspan="2">预算科目</td>
                  	<td>金额（万元）</td>
                  	<td>预算科目</td>
                  	<td>金额（万元）</td>
                  </tr>
                  <c:set var="countLine" value="0"></c:set>
                  <c:forEach items="${projectStartedReport.projectStartFeeLists}" var="fee" varStatus="i">
                     <c:set var="countLine" value="${i.count}"></c:set>
                     <c:if test="${i.count%2==1 }">
                         <tr>   
                           <td height="80" colspan="2">${fee.CFundAppItem.name}</td>
                           <td>${fee.amount/10000}</td>
                     </c:if>
                     <c:if test="${i.count%2==0 }">
                           <td>${fee.CFundAppItem.name}</td>
                         	<td>${fee.amount/10000}</td></tr>
                     </c:if>
                           </c:forEach>
                     <c:if test="${countLine%2==1 }">	
                            <td></td>
                         	<td></td></tr>
                     </c:if>
                  <tr>
                  	<td   height="80" colspan="2"></td>
                  	<td></td>
                  	<td></td>
                  	<td></td>
                  </tr>
                  <tr>
                  	<td   height="80" colspan="2"></td>
                  	<td></td>
                  	<td></td>
                  	<td></td>
                  </tr>
                </table>
            </div>
            <div>
		    	<div class="new_tlte">三、设备明细表</div>
				<div style="clear:both"></div>
				<div class="Y-jifi">
					<table width="100%" border="1" cellspacing="0">
						<tr>
							<td>序号</td>
							<td>名称</td>
							<td>规格或型号</td>
							<td>数量</td>
							<td>单价(元)</td>
							<td>合计</td>
						</tr>
						<c:forEach items="${projectStartedReport.projectStartDevices}" var="device" varStatus="i">
						<tr>
							<td>${i.count}</td>
							<td>${device.equipmentName}</td>
							<td>${device.format}</td>
							<td>${device.amount}</td>
							<td>${device.unitPrice}</td>
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
						</tr>
						<tr>
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
						</tr>
					</table>
				</div>            	
            </div>
		    <div>
		    	<div class="new_tlte">四、启动审核</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>申请启动</p>
	                        <div>
	                        	该项目已经通过专家论证，论证意见附后。因项目经费到位，现申请启动。
	                        </div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>项目负责人(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
				</div>	
		    </div>
		    <div>
				<div style="clear:both"></div>
				<div class="yang-yan">
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>系部意见</p>
	                        <div></div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>系（部）主任(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
				</div>	
		    </div>
		    <div>
				<div style="clear:both"></div>
				<div class="yang-yan">
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>教务处意见</p>
	                        <div></div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>处长(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
				</div>	
		    </div>
		    <div>
				<div style="clear:both"></div>
				<div class="yang-yan">
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>学校意见</p>
	                        <div></div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>学校主管领导(签名)：</div>
						<div><span>&nbsp;</span>年<span>&nbsp;</span>月<span>&nbsp;</span>日</div>
					</div>
				</div>	
		    </div>		    
		    <div class="page_end">
		    	<div class="new_tlte">
		    	   <p>附件1：项目申请书的论证意见复印件</p>
		    	   <p>附件2：建成后可开设实验（实训）项目清单</p>
		    	</div>
				<div style="clear:both"></div>
                <div class="Y-jifi">
                	<table width="100%" border="1" cellspacing="0">
                			<tr>
                				<td>序号</td>
                				<td>建成后可开设实验（实训）项目名称</td>
                			</tr>
                			<c:forEach items="${projectStartedReport.projectStartCompletionItems}" var="item" varStatus="i">
                			<tr>
                				<td>${i.count}</td>
                				<td>${item.experimentName}</td>
                			</tr>
                			</c:forEach>
                			<tr>
                				<td></td>
                				<td></td>
                			</tr>
                			<tr>
                				<td></td>
                				<td></td>
                			</tr>
                			<tr>
                				<td></td>
                				<td></td>
                			</tr>
                		</table>
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
								<td><a href="${pageContext.request.contextPath}/labconstruction/projectStartedReport/downloadFile?idkey=${doc.id}">下载</a></td>
								</tr>
							</c:forEach>	
						  </tbody>
					 </table>
                </div>
		    </div>		    		    			    
		</div>
	</div>
</body>
</form:form>
</html>