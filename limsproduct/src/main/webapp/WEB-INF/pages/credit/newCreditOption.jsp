<%@page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<head>
<meta name="decorator" content="iframe"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>
<script type="text/javascript"> 
$(function(){
$(".datebox :text").attr("readonly","readonly"); 
  $('#practice_name').blur(function(){
            var name = $('#practice_name').val();
            $.trim(name);
        });
});
</script>
    <style type="text/css">
        .content-box tr:last-child td {
            border-bottom: 1px solid #e4e5e7;
        }
    </style>
</head>
<!-- 导航栏 -->
<div class="navigation">
<div id="navigation">
<ul>
<li><a href=""><spring:message code="left.system.management"/></a></li>
<li><a href="${pageContext.request.contextPath}/credit/listCreditOption?currpage=1"><spring:message code="left.system.setting"/></a></li>
<li class="end"><a href="${pageContext.request.contextPath}/credit/newCreditOption">新增扣分项</a></li>
</ul>
</div>
</div>
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
	<div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
				<div class="title">新增扣分项
				</div>  
 <form:form id="myForm" action="${pageContext.request.contextPath}/credit/saveNewCreditOption" name="myForm" method="POST" modelAttribute="CreditOption">
 <table>
 <form:hidden path="id"/>
   <tr> 
     <th>扣分项<font color=red>*</font></td>
			<td><form:input class="easyui-validatebox" id="name" path="name" required="true" onblur="checkTerm0();" validType="length[0,25]" invalidMessage="不能超过25个字符！" />
			</td>
   </tr>
  <tr> 
     <th>扣分值<font color=red>*</font></td>
			<td><form:input class="easyui-validatebox" id="deduction" path="deduction" required="true" onblur="checkTerm0();" maxlength="2" onkeyup="this.value=this.value.replace(/\D/g, '')" validType="length[0,25]" invalidMessage="不能超过25个字符！" />
			
			</td>
   </tr>
 </table>
    <div class="moudle_footer">
        <div class="submit_link">
            <input class="btn btn-big" type="submit" value="保存" >
            <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
    </div>
   </form:form>
</div>
</div>
</div>
</div>