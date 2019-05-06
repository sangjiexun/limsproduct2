<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<meta name="decorator" content="iframe"/>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<link rel="stylesheet" href="/dhulims/css/iconFont.css">

 <script type="text/javascript">
  $(function(){
     $("#print").click(function(){
	$("#my_show").jqprint();
	});
	$("#lab_name").focus();
	$("#lab_name").bind('keydown', function (e) {

            var key = e.which;

            if (key == 13) {

                e.preventDefault();
              document.form.action="${pageContext.request.contextPath}/selectLabList";
	           document.form.submit();

            }

        });
  });


	//首页
	function first(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//末页
	function last(url){
		document.queryForm.action=url;
		document.queryForm.submit();
	}
	//上一页
	function previous(url){
		var page=${page};
		if(page==1){
			page=1;
		}else{
			page=page-1;
		}
		//alert("上一页的路径："+url+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	//下一页
	function next(url){
		var totalPage=${pageModel.totalPage};
		var page=${page};
		if(page>=totalPage){
			page=totalPage;
		}else{
			page=page+1
		}
		//alert("下一页的路径："+page);
		document.queryForm.action=url+page;
		document.queryForm.submit();
	}
	
	function cancel(){
		window.location.href="${pageContext.request.contextPath}/appointment/listLabAnnex?modelId=202&page=1";
	}
 </script> 
</head>
<body>

<div class="navigation">
				<div id="navigation">
					<ul>
						<li><a href="javascript:void(0)">实验室及预约管理</a></li>
						<li class="end"><a href="javascript:void(0)">实验室管理</a></li>
					</ul>
				</div>
			</div>


<!-- 结项申报列表 -->
<!-- <div class="tab"> -->




<!-- 查询表单 -->
<div>

</div>






<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
			<div class="content-box">
			<div class="title">
				<div id="title">实验室列表</div>
				<c:if test="${addLabAnnex.isActive!=0}">
				<a class="btn btn-new" href="${pageContext.request.contextPath}/appointment/newLabAnnex">新建</a>
				</c:if>
				
			</div>   	
			<div class="tool-box">
				<form:form name="queryForm" action="${pageContext.request.contextPath}/appointment/listLabAnnex?page=1" method="post" modelAttribute="labAnnex">
					 <ul>
    				<li>实验室名称： </li>
    				<li><form:input id="lab_name" path="labName"/></li>
    				<li><input type="submit" value="查询"/>
					                <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/></li>
					                <a class="btn btn-new" href="${pageContext.request.contextPath}/labconstruction/annex/exportLabconstructAnnex"> 导出</a>
    				</ul>
				</form:form>
		       </div>
    		<!-- 实验室列表 -->
    		<div class="content-box">   		
            <table  class="tb"  id="my_show"> 
                <thead>
                    <tr>
                        <th width="20%">实验室名称</th>
                        <th width="20%">实验分室数量</th>
                        <th width="20%">所属中心</th>
                        <th width="10%">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${listLabAnnex}" var="labAnnex"  varStatus="i">	
                        <tr>
                            <td><a class="classroom-name" href="${pageContext.request.contextPath}/labAnnex/getLabAnnex?id=${labAnnex.id}"> ${labAnnex.labName}</a></td>
                            <td><a href="${pageContext.request.contextPath}/labRoom/appointment/findLabRoomByLabAnnexId?id=${labAnnex.id}&page=1">${labAnnex.labRooms.size()}</a></td>
                            <td>${labAnnex.labCenter.centerName}</td>
                            <td><a href="${pageContext.request.contextPath}/labRoom/appointment/findLabRoomByLabAnnexId?id=${labAnnex.id}&page=1"  >查看</a>
                            <c:if test="${editLabAnnex.isActive!=0}">
                            <a href="${pageContext.request.contextPath}/labAnnex/appointment/updateLabAnnex?id=${labAnnex.id}"  >编辑</a>
                            </c:if>
                            <c:if test="${deleteLabAnnex.isActive!=0}">
                            <a href="${pageContext.request.contextPath}/appointment/deleteLabAnnex?id=${labAnnex.id}" onclick="return confirm('确认要删除吗？删除实验室将删除该实验室的分室，请谨慎操作。')"  >删除</a>
                            </c:if>
                            
                            </td>     
                        </tr>
                        </c:forEach>
                </tbody>
            </table>
        <div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/appointment/listLabAnnex?page=1&modelId=202')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/appointment/listLabAnnex?modelId=202&page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/appointment/listLabAnnex?modelId=202&page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/appointment/listLabAnnex?modelId=202&page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/appointment/listLabAnnex?modelId=202&page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/appointment/listLabAnnex?modelId=202&page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
    
</div>
</div>
</div>
</div>
</div>
</body>
</html>