<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
	<meta name="decorator" content="iframe"/>
<!-- 加载lodop打印组件 -->
<script language="javascript" src="${pageContext.request.contextPath}/js/LodopFuncs.js"></script>
<script>
function start(v){
try{printWB.ExecWB(v,1);}catch(e){}
window.close();
}
</script>
<script langauge="javascript">
//如果为查询则提交查询页面，如果为电子表格导出，则导出excel
function subform(gourl){ 
 var gourl;
 form.action=gourl;
 form.submit();
}
</script> 
<script type="text/javascript">
	// 基表2 弹出框
	function exportTxt(){
		$("#exportTxt").show();
		$("#exportTxt").window('open');
		
	}
	
	function targetUrl() {
		// 获取下拉框选值
		var aa = $("#yearCodeForTxt").val();
		// 导出TXT
		window.location.href="${pageContext.request.contextPath}/report/dataReport/reportOperationItemTxt?yearCode="+aa;
		// 关闭弹出框
		$("#exportTxt").window('close', true)
	}
	
	// 项目表导出excel弹出框
	function exportExcel() {
		$("#exportExcel").show();
		$("#exportExcel").window('open');
	}
	
	function exportExcelCom() {
		// 获取下拉框选值
		var aa = $("#yearCodeForExcel").val();
		// 导出TXT
		window.location.href="${pageContext.request.contextPath}/report/dataReport/reportProjectSummaryExcel?yearCode="+aa;
		// 关闭弹出框
		$("#exportExcel").window('close', true)
	}
	
</script>

<script type="text/javascript">
            function doPrint() {   
            	try{
                        print.portrait   =  false    ;//横向打印
                        printing.paperSize = "A4" 
                    }catch(e){
                        //alert("不支持此方法");
                    }
    			bdhtml=window.document.body.innerHTML;//获取当前页的html代码
 				sprnstr="<!--startprint-->";//设置打印开始区域
 				eprnstr="<!--endprint-->";//设置打印结束区域
 				prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取htm
 				prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html
                prnhtml.printing.rightMargin = 1.0;//右页边距
 				window.document.body.innerHTML=prnhtml;
 				window.print();
 				window.document.body.innerHTML=bdhtml;
			}   
            
        </script>
<object id="printWB" style="display:none" width=0 height=0 classid="clsid:8856F961-340A-11D0-A96B-00C04FD705A2" VIEWASTEXT></object><body topmargin=0 leftmargin=0 onload="start(7)"></body>
<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="WebBrowser3" width="0" VIEWASTEXT>
</OBJECT>
<script language="#">
function test()
{
document.all.WebBrowser3.ExecWB(7,1);
}
</script>
<INPUT type="button" value="Button" onclick="test()">
</head>

<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	  <li><a href="javascript:void(0)">基础报表</a></li>
	  <li><a href="javascript:void(0)">选课组列表</a></li>
		<li class="end"><a href="javascript:void(0)">实验教学计划表</a></li>
		
				
	  </ul>
	</div>
  </div>

<div class="sit_maincontent" style="width: 99%; height: 800px;">

<script type="text/javascript">
function WordWrap(textlength, id){
var obj=document.getElementById(id);
var strText=obj.innerHTML;
var tem="";
while(strText.length>textlength){
tem+=strText.substr(0,textlength)+"<br/>";
strText=strText.substr(textlength,strText.length);
}
tem+= strText;
obj.innerHTML=tem;
}
</script>


<div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
<div class="title">
	  <div id="title">实验教学计划表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/report/dataReport/reportTeachPlanExcel?courseDetailNo=${courseDetailNo}&&course_number=${course_number}&&termId=${termId}" type="button">导出Excel</a>
	  <a class="btn btn-new"><input type="button" value="打 印" onclick="doPrint();"style="
    background: #77bace;
    border: 1px solid #77bace;
    font-weight: bold;
    height: 22px;
    line-height: 22px;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    color: #fff;
    cursor: pointer;
"></a>
</div>
<!--startprint-->
<div>
	<p style="text-align: center;"><span style="font-size: 16px;font-weight: 900;">实验教学计划表(教师填报)</span></p>
	<p style="text-align: center;"><span>(${schoolTerm.termName})</span></p>
	<span>教研主任签字：</span><span style="float: right;margin-right: 200px;">院（部）盖章：</span>
</div>
<p style="text-align: center;"><span style="font-size: 16px;font-weight: 900;">实验课程信息</span></p><hr>

<table>
	<thead>
		<tr>
			<tr>
		    <th style="width: 8%;">课程名称</th>
		    <th style="width: 7%;">课程代码</th>
		    <th style="width: 5%;">实验<br>总学时</th>
		    <th style="width: 6%;">应开实验<br>项目个数</th>
		    <th style="width: 6%;">综合性/<br>设计性<br>实验课</th>
		    <th style="width: 22%;">开设班级</th>
		    
		    <th style="width: 5%;">总人数</th>
		    <th style="width: 5%;">实验者<br>类别</th>
		    <th style="width: 5%;">班级<br>属性</th>
		    <th style="width: 6%;">实验<br>指导教师</th>
		    <th style="width: 5%;">职称</th>
		    <th style="width: 10%;">实验教学<br>大纲名称(版本)</th>
		    <th>实验指导书<br>或教材</th>
		</tr>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td style="width: 8%;">${courseDetail[1]}</td>
			<td style="width: 7%;">${courseDetail[0]}</td>
			<td style="width: 5%;">${courseDetail[4]}</td>
			<td style="width: 6%;">${courseDetail[5]}</td>
			<td style="width: 6%;">${courseDetail[6]}</td>
			<td style="width: 22%;">${courseDetail[3]}</td>
			<td style="width: 5%;">${courseDetail[7]}</td>
			<td style="width: 5%;">${courseDetail[8]}</td>
			<td style="width: 5%;">${courseDetail[9]}</td>
			<td style="width: 6%;">${courseDetail[10]}</td>
			<td style="width: 5%;">${courseDetail[11]}</td>
			<td style="width: 10%;">${courseDetail[12]}</td>
			<td>${courseDetail[13]}</td>
		</tr>
	</tbody>
</table>

<hr><br>
<p style="text-align: center;"><span style="font-size: 16px;font-weight: 900;">实验项目信息</span></p><hr>
<table>
	<thead>
		<tr>
		    <th style="width: 8%;">实验中心名称</th>
		    <th style="width: 7%;">实验室名称</th>		    
		    <th style="width: 5%;">周次</th>
		    <th style="width: 6%;">星期</th>
		    <th style="width: 6%;">节次</th>
		    <th style="width: 22%;">实验项目名称</th>
		    <th style="width: 5%;">实验项<br>目学时</th>
		    <th style="width: 5%;">实验项<br>目类型</th>
		    <th style="width: 5%;">实验<br>要求</th>
		    <th style="width: 6%;">实验<br>性质</th>
		    <th style="width: 5%;">组数</th>
		    <th style="width: 10%;">每组人数</th>
		    <th>变动情况</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach  items="${courseDetails}" var="curr" varStatus="i">
		<tr>
			<td>${curr[0]}</td>
			<td>${curr[1]}</td>
			<td>${curr[2]}</td>
			<td>${curr[14]}</td>
			<td>${curr[15]}</td>
			<td>${curr[3]}</td>
			<td>${curr[17]}</td>
			<td>${curr[5]}</td>
			<td>${curr[6]}</td>
			<td>${curr[7]}</td>
			<td>${curr[8]}</td>
			<td>${curr[9]}</td>
			<td>${curr[10]}</td>
		</tr>
		</c:forEach>
	</tbody>
</table><br>
<span>填报人：</span><span style="float: right;margin-right: 100px;">填报日期：<fmt:formatDate type="date" value="${courseDetail[16]}" dateStyle="default" /></span>
<br><br>
<span>注：<br>
1.实验教学计划必须严格按照开课计划和实验教学大纲制定，由院部负责审核。<br>
2.实验课程名称：指单独开设的实验课或开出实验的课程名称。<br>
3.综合性/设计性实验课程，填写“是”或“否”，判断原则，只要该门课程中有一个实验项目为设计性或综合性，该门课程则为填“是”。<br>
4.实验性质：指基础、专业基础、专业、科研、生产、其他。<br>
5.实验类型：演示、验证、综合、设计。<br>
6.实验要求：指必修、选修、其他。<br>
7.实验者类别：指本科生、专科生、其他。<br>
8.变动状况：指未变、改变、新开、撤消、未开。
</span>
<!--endprint-->
</div>
</div>
</div>
</div>

</div>

</body>
</html>