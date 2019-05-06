<%-- <%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
	<meta name="decorator" content="iframe"/>
</head>


<body>

	<div class="title">
        <div id="title">${softwareInfo.name}软件信息查看</div>	
		<ul id="button"><li class="new_bulid_1"><a   onclick="javascript:window.history.go(-1)" >返回</a></li></ul> 
	</div>
		<form:form action="#">
            <table class="tb" style="border: 1px solid #999;text-align: center;">
                <tr>
                	<td>软件编号</td>
                	<td>${softwareInfo.code}</td>
                </tr>
                <tr>
                	<td>软件名称</td>
                	<td>${softwareInfo.name}</td>
                </tr>
                <tr>
                	<td>有无软件客户端及安装教程</td>
                	<td>
                		${softwareInfo.property}
                	</td>
                </tr>
                <tr>
                	<td>资金来源</td>
                	<td>${softwareInfo.fund}</td>
                </tr>
                <tr>
                	<td>价格(元)</td>
                	<td>${softwareInfo.price}</td>
                </tr>
                <tr>
                	<td>采购日期</td>
                	<td>${softwareInfo.purchaseDate.time}</td>
                </tr>
                <tr>
                	<td>保修时间(年)</td>
                	<td>${softwareInfo.guaranteeTime}</td>
                </tr>
                <tr>
                	<td>软件升级情况</td>
                	<td>${softwareInfo.upgrade}</td>
                </tr>
                <tr>
                	<td>采购负责人</td>
                	<td>${softwareInfo.purchasePerson}</td>
                </tr>
                <tr>
                	<td>所属学院</td>
                	<td>${softwareInfo.schoolAcademy.academyNumber}</td>
                </tr>
                <tr>
                	<td>使用属性</td>
                	<td>${softwareInfo.attribute}</td>
                </tr>
                <tr>
                	<td>软件保存编号</td>
                	<td>${softwareInfo.fundSource}</td>
                </tr>
                <tr>
                	<td></td>
        		</tr>
    
			</table>
		</form:form>

</body>
</html> --%>

<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
</head>
<body>
  <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
	  <div id="title">教学软件列表</div>
	  <a class="btn btn-new" onclick="javascript:window.history.go(-1)">返回</a>
	</div>
	
	 <table class="tb" style="border: 1px solid #999;text-align: center;">
                <tr>
                	<td>软件编号</td>
                	<td>${softwareInfo.code}</td>
                </tr>
                <tr>
                	<td>软件名称</td>
                	<td>${softwareInfo.name}</td>
                </tr>
                <tr>
                	<td>软件版本</td>
                	<td>${softwareInfo.edition}</td>
                </tr>
                <tr>
                	<td>所属<spring:message code="all.trainingRoom.labroom" /></td>
                	   <td>${softwareInfo.labRoom}</td>
                </tr>
                <tr>
                	<td>系统操作要求</td>
                	<td>${softwareInfo.operationRequirement}</td>
                </tr>
                <tr>
                	<td>有无软件客户端及安装教程</td>
                	<td>
                		${softwareInfo.property}
                	</td>
                </tr>
                <tr>
                	<td>价格(元)</td>
                	<td>${softwareInfo.price}</td>
                </tr>
                <tr>
                	<td>供应商</td>
                	<td>${softwareInfo.supplier}</td>
                </tr>
                <tr>
                	<td>供应商联系方式</td>
                	<td>${softwareInfo.supplierTel}</td>
                </tr>
                <tr>
                	<td>采购日期</td>
                	<td>${softwareInfo.purchaseDate.time}</td>
                </tr>
                <tr>
                	<td>采购负责人</td>
                	<td>${softwareInfo.purchasePerson}</td>
                </tr>
                <tr>
                	<td>所属学院</td>
                	<td>${softwareInfo.schoolAcademy.academyName}</td>
                </tr>
                <tr>
                	<td>安装说明（上传附件）</td>
                	<td><c:forEach items="${softwareInfo.commonDocuments}" var="s">
                           <c:if test="${s.type==1}">
                           <li>
                               ${s.documentName}
                                   <a href="${pageContext.request.contextPath}/downloadDocument?id=${s.id}">下载</a>
                           </li>
                          </c:if>
                    </c:forEach>
                    </td>
                </tr>
                <tr>
                	<td>使用说明（上传附件）</td>
                	<td><c:forEach items="${softwareInfo.commonDocuments}" var="s">
                             <c:if test="${s.type==2}">
                           <li>
                          ${s.documentName}
                              <a href="${pageContext.request.contextPath}/downloadDocument?id=${s.id}">下载</a>
                           </li>
                        </c:if>
                      </c:forEach>

                 </td>
                </tr>
                <tr>
                	<td></td>
        		</tr>
    
			</table>
    
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>
