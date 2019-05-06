<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>

<html>
<head>
<meta name="decorator" content="iframe"/>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

 <script type="text/javascript">  
	
    //取消
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/labSecurityCheck?currpage=1";
	};
    
    //切换页面
    function switchover(tage){
    	 //导航栏切换
    	 $(".TabbedPanelsTabGroup").find("li").eq(tage).addClass("selected")
    	 $(".TabbedPanelsTabGroup").find("li").eq(tage).siblings().removeClass("selected")
    	 //切换div显示
    	 $("#div1").hide(); $("#div2").hide(); $("#div3").hide();
    	 $("#div4").hide(); $("#div5").hide(); $("#div6").hide();
    	 $("#div7").hide(); $("#div8").hide(); $("#div9").hide();
    	 $("#div10").hide(); $("#div0").hide();
 		 $("#div"+tage).show();
    	 
    };
    //提交或保存
    function submitCheck(types){
        document.queryForm.action="${pageContext.request.contextPath}/saveCheckDetails?&types="+types;
        document.queryForm.submit();
    }
       
 	//确保进入该页是导航栏显示的是第一项
	$(document).ready(function(){	
	 $("#s1").addClass("TabbedPanelsTab selected");
	 	$("#s2").addClass("TabbedPanelsTab");
	  	$("#s3").addClass("TabbedPanelsTab");
	    $("#s4").addClass("TabbedPanelsTab");
	    $("#s5").addClass("TabbedPanelsTab");
	 	$("#s6").addClass("TabbedPanelsTab");
	  	$("#s7").addClass("TabbedPanelsTab");
	    $("#s8").addClass("TabbedPanelsTab");
	    $("#s9").addClass("TabbedPanelsTab");
	 	$("#s10").addClass("TabbedPanelsTab");
	  	$("#s11").addClass("TabbedPanelsTab");
		});
		
 </script> 
</head>


<body>

<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
			<li><a href="javascript:void(0)"><spring:message code="left.safety.inspection"/></a></li>
			<li class="end"><a href="javascript:void(0)">新建</a></li>
		</ul>
	</div>
</div>
	<form:form name="queryForm" action="" method="post" modelAttribute="labSecurityCheck" >
	<table style="width:85%;align:center" >
		<tr>
			<br/>	 
    		<td><form:hidden path="id" name="id" value="${labSecurityCheck.id}"/>
    			<c:if test="${labSecurityCheck.typeTable=='1'}">一类(涉化)</c:if>
    			<c:if test="${labSecurityCheck.typeTable=='2'}">二类(机械)</c:if>
    			<c:if test="${labSecurityCheck.typeTable=='3'}">三类(文化)</c:if>
			</td>
		    <td>时间：${labSecurityCheck.schoolTerm.termName}第${labSecurityCheck.month}月</td>
			<td>学院：${labSecurityCheck.labCenter.schoolAcademy.academyName}</td>
			<td>实验中心：${labSecurityCheck.labCenter.centerName}</td>
			<td>检查人：${labSecurityCheck.user.cname}</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td><c:if test="${ReadOnly=='2'}">  <input type="button" value="返回" onclick="cancel();"/>  </c:if>
				<c:if test="${ReadOnly=='1'}">  
					<input type="button" value="保存" onclick="submitCheck(1)"/>
					<input type="button" value="提交" onclick="submitCheck(2);"/>
				</c:if>			
			</td>
		</tr>	
	</table>	
			<br/>
			<ul class="TabbedPanelsTabGroup">				
				<li class="TabbedPanelsTab" id="s1"><a herf="javacaript:void(0)" onclick="switchover(0)">1组织体系</a></li>
				<li class="TabbedPanelsTab" id="s2"><a herf="javacaript:void(0)" onclick="switchover(1)">2规章制度</a></li>
				<li class="TabbedPanelsTab" id="s3"><a herf="javacaript:void(0)" onclick="switchover(2)">3安全教育</a></li>
				<li class="TabbedPanelsTab" id="s4"><a herf="javacaript:void(0)" onclick="switchover(3)">4实验中心环境与管理</a></li>
				<li class="TabbedPanelsTab" id="s5"><a herf="javacaript:void(0)" onclick="switchover(4)">5安全设施</a></li>
				<li class="TabbedPanelsTab" id="s6"><a herf="javacaript:void(0)" onclick="switchover(5)">6水电安全</a></li>
				<li class="TabbedPanelsTab" id="s7"><a herf="javacaript:void(0)" onclick="switchover(6)">7化学安全</a></li>
				<li class="TabbedPanelsTab" id="s8"><a herf="javacaript:void(0)" onclick="switchover(7)">8生物安全</a></li>
				<li class="TabbedPanelsTab" id="s9"><a herf="javacaript:void(0)" onclick="switchover(8)">9辐射安全</a></li>
				<li class="TabbedPanelsTab" id="s10"><a herf="javacaript:void(0)" onclick="switchover(9)">10仪器设备安全</a></li>
				<li class="TabbedPanelsTab" id="s11"><a herf="javacaript:void(0)" onclick="switchover(10)">11个人防护与其它</a></li>				
			</ul>	
	
	
	<div >	
	<table class="tb" id="my_show"  cellpadding="5"  border="1" height="50" width="100%">
	  <thead >
		  <tr> 
		  <th>编号</th>
		  <th>安全责任项目</th>
		  <th>符合</th>
		  <th>不符合</th>
		  <th>不适用</th>
		  </tr>
	  </thead>
	  <c:set var="radioNo" value="${0}" />
<!-------------------------------------- 1组织体系   ------------------------------------------>
	<div> 
	  <tbody id="div0" >
		  	<c:forEach items="${sDictionarys1}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 2规章制度   ------------------------------------------>
	<div > 
	  <tbody id="div1" style="display: none">
		  	<c:forEach items="${sDictionarys2}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 3安全教育   ------------------------------------------>
	<div > 
	  <tbody id="div2" style="display: none">
		  	<c:forEach items="${sDictionarys3}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}"> 
			  	 <c:set var="radioNo" value="${radioNo+1}" /> 
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div>
<!-------------------------------------- 4实验室环境与管理   ------------------------------------------>
	<div > 
	  <tbody id="div3" style="display: none">
		  	<c:forEach items="${sDictionarys4}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 5安全设施   ------------------------------------------>
	<div> 
	  <tbody id="div4" style="display: none">
		  	<c:forEach items="${sDictionarys5}" var="curr" varStatus="i">
		  		<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}"> 
			  	 <c:set var="radioNo" value="${radioNo+1}" /> 
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 6水电安全   ------------------------------------------>
	<div> 
	  <tbody id="div5" style="display: none">
		  	<c:forEach items="${sDictionarys6}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}"> 
			  	 <c:set var="radioNo" value="${radioNo+1}" /> 
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 7化学安全   ------------------------------------------>
	<div> 
	  <tbody id="div6" style="display: none">
		  	<c:forEach items="${sDictionarys7}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 8生物安全   ------------------------------------------>
	<div> 
	  <tbody id="div7" style="display: none">
		  	<c:forEach items="${sDictionarys8}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
	<!-------------------------------------- 9辐射安全   ------------------------------------------>
	<div> 
	  <tbody id="div8" style="display: none">
		  	<c:forEach items="${sDictionarys9}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 10仪器设备安全   ------------------------------------------>
		<div> 
	  <tbody id="div9" style="display: none">
		  	<c:forEach items="${sDictionarys10}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}"> 
			  	 <c:set var="radioNo" value="${radioNo+1}" /> 
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
<!-------------------------------------- 11个人防护与其它   ------------------------------------------>
	<div> 
	  <tbody id="div10" style="display: none">
		  	<c:forEach items="${sDictionarys11}" var="curr" varStatus="i">
			  	<c:if test="${curr.noThree == '0' && curr.noTwo != null && curr.noTwo != ''}">  
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td></td>
					  <td></td>
					  <td></td>
				    </tr>
			  	</c:if>
			  	<c:if test="${curr.noThree != '0' && curr.noTwo != null && curr.noTwo != ''}">  
			  	 <c:set var="radioNo" value="${radioNo+1}" />
				  	 <tr> 
				      <td>${curr.no}</td>
					  <td>${curr.name}</td>
					  <td><input type="radio" name="result${radioNo}" value="1" checked="checked"></td>
					  <td><input type="radio" name="result${radioNo}" value="2"></td>
					  <td><input type="radio" name="result${radioNo}" value="3"></td>
				    </tr>
			  	</c:if>
		  </c:forEach>	
	  </tbody>
	</div> 
	 
	</table>	
	</form:form>	
	</div>
</body>
</html>
