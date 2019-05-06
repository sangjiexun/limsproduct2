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
    margin-top: -1px;
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
		    <%--<a class="btn btn-new" href="#" onclick="javascript:prn3_preview();"><span>打印</span></a>
		    --%>
		    <input type="button" value="返回" onclick="window.history.go(-1);">
		    <input type="button" value="打印" onclick="print()">
				<a href="${pageContext.request.contextPath}/downloadLabConstructApp?idKey=${labConstructApp.id}"><input type="button" value="写入Word文档：" ></a>
            <input type="text" id="T2B" size="18" value=""><%--
            <a href="javascript:OutToFileMoreSheet();">导出数据</a>
			--%><div class="hand_img">
				<spring:message code="title.school.name" />
			</div>
			<div class="tilte_t">实验(实训)室建设项目</div>
			<div class="subtitle">申请书</div>
			<form:form name="subForm" method="post" modelAttribute="labConstructApp" >
			<div class="in_put">
			    <div class="in_py">
					<span>项目名称：${labConstructApp.projectName}</span><br/>
					<span>申&nbsp; 请&nbsp;人：${labConstructApp.user.cname}</span><br/>
					<span>系&nbsp;&nbsp;&nbsp;&nbsp;(部)：${labConstructApp.user.schoolAcademy.academyName}</span><br/>
					<span>联系电话：${labConstructApp.user.telephone}</span><br/>
					<span>申请时间：<fmt:formatDate pattern="yyyy-MM-dd" type="both" value="${labConstructApp.appDate.time}"/></span><br/>
			    </div>
			</div>
			<div class="tilte_le">教务处制</div>
		</div>
		<div>
			<div class="new_T_tlte">
				<spring:message code="title.school.name" />实验（实训）室建设项目申请书简表
			</div>
			<div class="komhzji">
              <table width="800px" border="1" cellspacing="0" >
                  <tr>
                    <td colspan="2">项目名称</td>
                    <td colspan="9">&nbsp;${labConstructApp.projectName}</td>
                  </tr>
                  <tr>
                    <td colspan="2">所在系（部）</td>
                    <td colspan="9">&nbsp;${labConstructApp.user.schoolAcademy.academyName}</td>
                  </tr>
                  <tr>
                    <td colspan="2">用途</td>
                    <td colspan="9">
                       <c:forEach items="${purposeMap}" var="p" >
			             <c:if test="${labConstructApp.id==p.key}"> 
			             <c:forEach items = "${p.value }" var="i">${i.name } </c:forEach>
		               </c:if></c:forEach>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2">面向专业数</td>
                    <td colspan="2">&nbsp;${labConstructApp.majorAmount}</td>
                    <td colspan="2">专业名称</td>
                    <td colspan="5">&nbsp;
                       <c:forEach items="${map}" var="s" >
			           <c:if test="${labConstructApp.id==s.key}"> 
			           <c:forEach items = "${s.value }" var="k">${k.majorName } </c:forEach>
		               </c:if></c:forEach>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2">面向课程数</td>
                    <td colspan="2">&nbsp;${labConstructApp.majorAmount}</td>
                    <td colspan="2">课程名称</td>
                    <td colspan="5">&nbsp;
                       <c:forEach items="${courseMap}" var="s" >
			           <c:if test="${labConstructApp.id==s.key}"> 
			           <c:forEach items = "${s.value }" var="k">${k.courseName } </c:forEach>
		               </c:if></c:forEach>
                    </td>
                  </tr>
                  <tr>
                    <td rowspan="2">申请人</td>
                    <td width="152">姓名</td>
                    <td colspan="2">&nbsp;${labConstructApp.user.cname}</td>
                    <td width="67">性别</td>
                    <td width="67">&nbsp;${labConstructApp.user.userSexy}</td>
                    <td width="67">出生年月</td>
                    <td width="67">&nbsp;</td>
                    <td width="67">职务</td>
                    <td colspan="2">&nbsp;</td>
                  </tr>
                  <tr>
                    <td>职称</td>
                    <td colspan="2">&nbsp;</td>
                    <td>手机</td>
                    <td colspan="2">&nbsp;</td>
                    <td>电话</td>
                    <td>&nbsp;${labConstructApp.user.telephone}</td>
                    <td width="67">e-mail</td>
                    <td width="74">&nbsp;${labConstructApp.user.email}</td>
                  </tr>
                  <tr>
                    <td width="44" rowspan="20">参加人数</td>
                    <td colspan="2">姓名</td>
                    <td width="85"> 性别</td>
                    <td>年龄</td>
                    <td colspan="2">职务</td>
                    <td>职称</td>
                    <td colspan="3">负责内容</td>
                  </tr>
                  <c:forEach items="${labConstructApp.labConstructUsers}" var="user">
                  <tr>
                    <td colspan="2">&nbsp;${user.cname}</td>
                    <td>&nbsp;${user.sex}</td>
                    <td>&nbsp;${user.age}</td>
                    <td colspan="2">&nbsp;${user.position}</td>
                    <td>&nbsp;${user.jobTitle}</td>
                    <td colspan="3">&nbsp;${user.responsibilityContent}</td>
                  </tr>
                  </c:forEach>
                  <tr>
                    <td colspan="2">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td colspan="2">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td colspan="3">&nbsp;</td>
                  </tr>
                  <tr>
                    <td colspan="2">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td colspan="2">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td colspan="3">&nbsp;</td>
                  </tr>
                </table>

            </div>
            <div class="imp_content">
	            <div class="new_tlte">主要目标</div>
				<div style="clear:both"></div>
				<div class="fill_content">${labConstructApp.primaryObjective}</div>
		    </div>
            <div class="imp_content">
	            <div class="new_tlte">特色或创新</div>
				<div style="clear:both"></div>
				<div class="fill_content">${labConstructApp.specialInnovation}</div>
		    </div>
		    <div>
		    	<div class="new_tlte">
		    	      一、立项依据(项目建设的意义、国内外现状分析)
		    	</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
					<div>
						&nbsp;${labConstructApp.projectBasis}
					</div>
				</div>	
		    </div>
		    <div>
		    	<div class="new_tlte">
		    	      二、建设的基础(实验实训室用房、已取得的成绩、已具备的条件)
		    	</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
					<div>
						&nbsp;${labConstructApp.constructBasis}
					</div>
				</div>	
		    </div>
		    <div>
		    	<div class="new_tlte">
                                                    三、计划进度
		    	</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
					<div>
						&nbsp;${labConstructApp.planSchedule}
					</div>
				</div>	
		    </div>
		    <div>
		    	<div class="new_tlte">
                                                    四、预期成果
		    	</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
				    <div class="list_page">
				    1. 预期的成果、考核指标及预期成果形式（实验实训项目填写附件清单）
				    </div>
					<div>
						&nbsp;${labConstructApp.expectedResult}
					</div>
					<div  class="list_page">
					2. 项目完成后的效益（对学生、学校、经济、社会）
					</div>
					<div>
						&nbsp;
					</div>					
				</div>	
		    </div>		    		    		    			    	
		    <div>
				<div class="new_tlte">五、经费预算</div>
				<div style="clear:both"></div>
				<div  class="Y-jifi">
					<table width="100%" border="1" cellspacing="0">
                         <tr>
                         	<td height="80">申请资助经费名称(万元)</td>
                         	<td colspan="2">${labConstructApp.feeAmount}</td>
                         	<td colspan="2">其他经费来源及金额(万元)</td>
                         	<td colspan="2">${labConstructApp.otherFee}</td>
                       	 </tr>
                         <tr>
                         	<td height="80" colspan="2">预算科目</td>
                         	<td colspan="2">金额(万元)</td>
                         	<td colspan="2">预算科目</td>
                         	<td>金额(万元)</td>
                         </tr>
                         <!-- 判断List中该次循环的数据属于第几条的变量 -->
                         <c:set var="countLine" value="0"></c:set>
                         <c:forEach items="${labConstructApp.projectFeeLists}" var="fee" varStatus="i">
                         <c:set var="countLine" value="${i.count}"></c:set>
                         <c:if test="${countLine%2==1 }">
                         <tr>   
                           <td height="80" colspan="2">${fee.CFundAppItem.name}</td>
                           <td colspan="2">${fee.amount/10000}</td>
                         </c:if>
                         <c:if test="${countLine%2==0 }">
                           <td colspan="2">${fee.CFundAppItem.name}</td>
                         	<td>${fee.amount/10000}</td></tr>
                         </c:if>
                           </c:forEach>
                         <c:if test="${countLine%2==1 }">	
                            <td colspan="2"></td>
                         	<td></td></tr>
                         </c:if>
                         <tr>
                           <td height="80" colspan="2"></td>
                           <td colspan="2"></td>
                         	<td colspan="2"></td>
                         	<td></td>
                         </tr>
                         <tr>
                           <td height="80" colspan="2"></td>
                           <td colspan="2"></td>
                         	<td colspan="2"></td>
                         	<td></td>
                         </tr>
					</table>
				</div>		    	
		    </div>
		    <div>
				<div class="new_tlte">六、设备明细表</div>
				<div style="clear:both"></div>
				<div class="Y-jifi">
					<table  width="100%" border="1" cellspacing="0">
						<tr>
							<td>序号</td>
							<td>名称</td>
							<td>规格或型号</td>
							<td>数量</td>
							<td>单价(元)</td>
							<td>合计</td>
						</tr>
						<c:forEach items="${labConstructApp.projectDevices}" var="device" varStatus="i">
						<tr>
							<td>${i.count}</td>
							<td>${device.equipmentName}</td>
							<td>${device.format}</td>
							<td>${device.amount}</td>
							<td>${device.unitPrice}</td>
							<td></td>
						</tr>
						</c:forEach>
					</table>
				</div>		    	
		    </div>

		    <div>
		    	<div class="new_tlte">七、审核</div>
				<div style="clear:both"></div>
				<div class="kuang_centent">
					<div class="y_YY">
					   <div class="shiyan_ceping">
	                        <p>专家组意见</p>
	                        <div></div>
	                   </div>
					</div>
					<div class="kao_right">
						<div>组长(签名)：</div>
						<div>成员(签名)：</div>
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
						<div>系（部）公章：</div>
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
						<div>教务处公章：</div>
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
		    	<div class="new_tlte">附件：建成后可开设实验（实训）项目清单</div>
				<div style="clear:both"></div>
                <div>
                	<table width="800px" border="1" cellspacing="0">
                			<tr>
                				<td>序号</td>
                				<td>建成后可开设实验（实训）项目名称</td>
                			</tr>
                			<c:forEach items="${labConstructApp.projectCompletionItems}" var="item" varStatus="i">
                			<tr>
                				<td>${i.count}</td>
                				<td>${item.experimentName}</td>
                			</tr>
                			</c:forEach>
                			<tr>
                				<td></td>
                				<td></td>
                			</tr>
                		</table>
                	    <table id="document">
						 <tr>
							 <td>文档名称</td>
							 <td>操作&nbsp;</td>
						 </tr>
					    </table>
					 <table>
						  <tbody id="doc">
						  	<c:forEach items="${labConstructApp.commonDocuments}" var="doc" varStatus="i">
								<tr >
							 	<td>${doc.documentName}</td>
								<td><a href="${pageContext.request.contextPath}/labconstruction/labConstructApp/downloadFile?idkey=${doc.id}">下载</a></td>
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
