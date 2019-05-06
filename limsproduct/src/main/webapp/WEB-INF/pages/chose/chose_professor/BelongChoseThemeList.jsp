<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@page import="java.util.Date"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <!-- layer弹窗 -->
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
  <script type="text/javascript">
	  $(document).ready(function(){
		});
	  //取消查询
	  function cancel()
	  {
	    window.location.href="${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=1";
	  }
	  //跳转
	  function targetUrl(url)
	  {
	    document.queryForm.action=url;
	    document.queryForm.submit();
	  }
  </script>	
</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">填写志愿--学生</a></li>
	  </ul>
	</div>
  </div>
  <div id="TabbedPanels1" class="TabbedPanels">
	  <ul class="TabbedPanelsTabGroup">
		  <li class="TabbedPanelsTab1 selected" id="s1">
			  <a href="javascript:void(0)">填写志愿--学生</a>
		  </li>
	  </ul>
   <div class="TabbedPanelsContentGroup">
    <div class="TabbedPanelsContent">
     <div class="content-box">
      <%--<div class="title">--%>
	   <%--<div id="title">导师互选列表</div>--%>
	  <%--</div>--%>
	   <div class="tool-box">
		<form:form name="queryForm" action="${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=1" method="post" modelAttribute="choseTheme">
		 <ul>
  		  <li>
			  大纲编号:
			  <form:input id="id" path="id"/>
		  </li>
  		  <li><input type="submit" value="查询"/>
		  <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
  		 </ul>
		</form:form>
	   </div>
	   <table class="tb" id="my_show">
	    <thead>
	     <tr>
	    <th>编号</th>
	    <th>全部学生数量</th>
	    <th>全部导师数量</th>
	    <th>导师可带最大学生数量</th>
	    <th>开始时间</th>
	    <th>结束时间</th>
	    <th>志愿数量</th>
	    <th>提前通知天数</th>
	    <th>所属届</th>
	   <!--  <th>状态</th> -->
	    <th>操作</th>
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${choseThemeList}" var="curr" varStatus="i">
	  <tr>
	  	<input type="hidden" value="${curr.id}" name="ids"/>
	  	<input type="hidden" value="${i.index+1}" name="flag"/>
	    <td>${(pageModel.currpage-1)*pageSize+i.count }</td>
	    <td>${curr.studentNumber}</td>
	    <td>${curr.teacherNumber}</td>
	    <td>${curr.maxStudent}</td>
	    <td><fmt:formatDate  value='${curr.startTime.time}' var="startTime" pattern='yyyy-MM-dd'/>${startTime }</td>
	    <td><fmt:formatDate value='${curr.endTime.time}' var="endTime" pattern='yyyy-MM-dd'/>${endTime }</td>
	    <td>${curr.batchNumber}</td>
	    <td>${curr.advanceDay}</td>
	    <td>${curr.theYear}</td>
	    <input name="st" type="hidden"  value="${startTime }"/>
	    <input name="et" type="hidden"  value="${endTime }"/>
	     <td name="start">
	     </td>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
	<span id="c"><fmt:formatDate  value="<%=new Date()%>" var="startTime" pattern='yyyy-MM-dd'/></span>
	<div class="page" >
        ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/nwuChose/BelongChoseThemeList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
    </div>
    
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	     $(document).ready(function(){
	    	var st=$("input[name='st']");
	    	var et=$("input[name='et']");
	    	var ids=$("input[name='ids']");
	    	var tds=$("td[name='start']");
	    	//当前时间
	    	var currTime=new Date();
	    	//初始化当前时间
	    	currTime.setHours(0);
	    	currTime.setMinutes(0, 0, 0);
	    	currTime.setSeconds(0, 0);
	    	//开始时间和结束时间
	    	var startTime;
	    	var endTime;
	    	if(${isChoseProfessor==null or isChoseProfessor == 0}){
		    	for(var i=0;i<st.length;i++){
		        	startTime=new Date(st.eq(i).val()).setHours(0, 0, 0, 0);
		        	endTime=new Date(et.eq(i).val()).setHours(0, 0, 0, 0); 
		        	if(currTime.getTime()>=startTime&&currTime.getTime()<=endTime){
		        		var themeId=ids.eq(i).val();
		        		//var t="<a href='${pageContext.request.contextPath}/toAddBatch?i=1&currpage=1&themeId="+ids.eq(i).val()+"'>填写志愿</a>";
		        		var t="<a href='javaScript:void(0)' onclick='checkIfReadAttention("+themeId+")'>填写志愿</a>";
		        		tds.eq(i).html(t);
		        	}
		        } 
	    	}
	    	else{
	    		for(var i=0;i<st.length;i++){
	    			    var t="<a href='${pageContext.request.contextPath}/nwuChose/findBatchState?themeId="+ids.eq(i).val()+"'>查看状态</a>";
		        		tds.eq(i).html(t);
	    		}
	    	}
	         
	    }); 
	   function  checkIfReadAttention(themeId){
       //type 所属类型   1导师制互选导师   2导师制互选学生   3论文制互选导师  4论文制互选学生
   	     $.ajax({
   		url:"${pageContext.request.contextPath}/nwuChose/checkIfReadAttention?themeId="+themeId,
   		type:"post",
   		success:function(result){
   			if(result=="success"){
   				window.location.href="${pageContext.request.contextPath}/nwuChose/toAddBatch?i=1&currpage=1&themeId="+themeId;
   			}else{
   				//弹出注意事项框
			  layer.ready(function(){
			        layer.open({
			            type: 2,
			            title: '',
			            fix: true,
			            maxmin:true,
			            shift:-1,
			            closeBtn: 1,
			            shadeClose: true,
			            move:false,
			            maxmin: false,
			            area: ['1000px', '420px'],
			            content: '${pageContext.request.contextPath}/nwuChose/findChoseAttentionByType?type=1&themeId='+themeId,
			            end: function(){
			            }
			        });
			    });
   			}
   		}
   	}) 
   }
	</script>
	<!-- 下拉框的js -->
</body>
</html>
