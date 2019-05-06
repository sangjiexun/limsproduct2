<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  
  <script type="text/javascript">
  $(document).ready(function(){
	});
  //取消查询
  function cancel()
  {
    window.location.href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=${status}&orderBy=${orderBy }";
  }
  //跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  </script>
<script type="text/javascript">
</script>	

</head>
  
<body>
  <div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">导师互选列表</a></li>
	  </ul>
	</div>
  </div>
  
  <div id="TabbedPanels1" class="TabbedPanels">
    <%--<ul class="TabbedPanelsTabGroup">
		<li class="TabbedPanelsTab" id="s0"><a href="${pageContext.request.contextPath}/ChoseThemeList?currpage=1">全部</a></li>
		<li class="TabbedPanelsTab" id="s1"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=1&orderBy=${orderBy }">草稿</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=2&orderBy=${orderBy }">审核中</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=3&orderBy=${orderBy }">审核通过</a></li>
		<li class="TabbedPanelsTab" id="s4"><a href="${pageContext.request.contextPath}/operation/listMyOperationItem?currpage=1&status=4&orderBy=${orderBy }">审核拒绝</a></li>
	</ul>
  --%><div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  
  <div class="content-box">
    <div class="title">
	  <div id="title">导师互选列表</div>
	  <a class="btn btn-new" href="${pageContext.request.contextPath}/nwuChose/ChoseProfessorBatchListForProfessor?currpage=1&themeId=${themeId}">返回</a>
	</div>
	<div class="tool-box">
	</div>
	<table class="tb" id="my_show">
	  <thead>
	  <tr>
	   <th>学号</th>
	   <th>姓名</th>
	   <th>班级</th>
	   <c:if test="${flag==1}">
	   	 <th>操作</th>
	   </c:if>
	   
	  </tr>
	  </thead>
	  <tbody>
	  <c:forEach items="${records}" var="curr" varStatus="i">
	  <tr>
	  	<input type="hidden" value="${curr.id}" name="recordId"/>
	    <td>${curr.user.username}</td>
	    <td>${curr.user.cname}</td>
	    <td>${curr.user.schoolClasses.className}</td>
	    <c:if test="${flag==1 }">
	   	  <td name="operator">
	   	  	<input type="checkbox" name="state" class="state" value="${pageContext.request.contextPath}/saveAduitResult?recordId=${curr.id}&aduitResult=1">
	   	  	<%-- <input type="radio" name="state" value="${pageContext.request.contextPath}/saveAduitResult?recordId=${curr.id}&aduitResult=1"/>同意
	   	  	<input type="radio" name="state" value="${pageContext.request.contextPath}/saveAduitResult?recordId=${curr.id}&aduitResult=2"/>不同意 --%>
	    	<%-- <a href="${pageContext.request.contextPath}/saveAduitResult?recordId=${curr.id}&aduitResult=1">同意</a>
	    	<a href="${pageContext.request.contextPath}/saveAduitResult?recordId=${curr.id}&aduitResult=2">不同意</a> --%>
	    </td>
	   </c:if>
	  </tr>
	  </c:forEach>
	  </tbody>
	</table>
     <c:if test="${flag==1}">
     <a class="btn btn-new" href="javascript:void(0)" onclick="sub()">提交</a>
     	剩余人数:${count}
     </c:if>
  </div>
  </div>
  </div>
  </div>
  <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var c=0;
            //$('input:checkbox').on('change', function(){
           // alert($(input:checkbox:checked').val())
                /* if($('input:checkbox:checked').val()) {
                	c++;
                    if(${count}<c){
                     alert("超过学生的最大数量")
                     $(this).removeAttr("checked","checked");
                    }
                }
            }) */

             $(".state").click(function(){
             if($(this).attr("checked")=="checked"){
             	 c++;
                    if(${count}<c){
                     alert("超过学生的最大数量");
                     $(this).removeAttr("checked","checked");
                     c--;
                    }
             }
             else{
             c--;
             }
             	    
             });
          //)};
        });
       
		function sub(){
			var checkBox = document.getElementsByName("state"); 
			var recordId = document.getElementsByName("recordId"); 
			var array=new Array() 
            for (var i=0; i<checkBox.length; i++){  
	            if (checkBox[i].checked) {  
	             	array.push(recordId[i].value);
	            }
	           
            } 
            if(array.length==0){
            	alert("至少选择一条");
            }
            else{
            	 window.location.href="${pageContext.request.contextPath}/nwuChose/saveAduitResult?themeId=${themeId}&recordIds="+array;  
            }
		}
	</script>
</body>
</html>
