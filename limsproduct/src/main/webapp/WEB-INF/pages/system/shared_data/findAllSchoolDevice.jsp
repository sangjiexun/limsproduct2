<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>


<html>
<head>
<meta name="decorator" content="iframe"/>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 --> 


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
		window.location.href="${pageContext.request.contextPath}/findAllSchoolDevice?page=1";
	}
	function subform(gourl) {
		var gourl;
		form.action = gourl;
		form.submit();
	}
 </script>
 
</head>
<body>

<div class="iStyle_RightInner">
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">设备资产统计</a>
			</li>
			<li class="end">
			所有设备
			</li>
		</ul>
	</div>
</div>

<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
	  <div class="TabbedPanelsContentGroup">
	   <ul class="TabbedPanelsTabGroup">
  
			<li class="TabbedPanelsTab selected" tabindex="0">
			<a href="${pageContext.request.contextPath}/findAllSchoolDevice?page=1">所有设备</a>
			</li>
		
			<li class="TabbedPanelsTab"  tabindex="0">
			<a href="${pageContext.request.contextPath}/findAllLabRoomDevice?page=1">实训室设备</a>
			</li>
		
	  </ul>
		<div class="TabbedPanelsContent">
			<div class="content-box">
			<div class="title">
				<div id="title">设备信息列表</div>
				<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
				<a class="btn btn-new" href='${pageContext.request.contextPath}/newSchoolDevice'>新建</a>
				<%--<a class="btn btn-new" onclick="exportSelect();">导出</a>--%>
				</sec:authorize>
			</div>   	
			<div class="tool-box">
				<form:form name="form" id="form_act" method="post" modelAttribute="schoolDevice">
					<ul>
    				<li>设备名称： </li>
    				<li>
    					<form:input id="deviceName" path="deviceName"/>
    				</li>
    				<%--
    				<li><form:select id="deviceName" path="deviceName" class="chzn-select">
  					<form:option value="">请选择</form:option>
  						<c:forEach items="${listSchoolDeviceAll}" var="curr">
  							<form:option value="${curr.deviceName}">${curr.deviceName}</form:option>
  						</c:forEach>
  					</form:select></li>
    				--%>
    				<li>设备编号： </li>
    				<li>
    					<form:input id="deviceNumber" path="deviceNumber"/>
    				</li>
    				<%--<li><form:select id="deviceNumber" path="deviceNumber" class="chzn-select">
  					<form:option value="">请选择</form:option>
  						<c:forEach items="${listSchoolDeviceAll}" var="curr">
  							<form:option value="${curr.deviceNumber}">${curr.deviceNumber}</form:option>
  						</c:forEach>
  					</form:select></li>
    				--%>
    				<li>价格：</li>
    				<li>
    				<select name="searchflg" id="searchflg">
						<option value="">-请选择-</option>
						<option value="1" <c:if test="${'1' eq searchflg}">selected</c:if>>等于</option>
						<option value="2" <c:if test="${'2' eq searchflg}">selected</c:if>>不等于</option>
						<option value="3" <c:if test="${'3' eq searchflg}">selected</c:if>>大于等于</option>
						<option value="4" <c:if test="${'4' eq searchflg}">selected</c:if>>大于</option>
						<option value="5" <c:if test="${'5' eq searchflg}">selected</c:if>>小于等于</option>
						<option value="6" <c:if test="${'6' eq searchflg}">selected</c:if>>小于</option>
						<option value="7" <c:if test="${'7' eq searchflg}">selected</c:if>>介于</option>
					</select>	
    				</li>
    				<li><input type="text" id="price1" name="price1" value="${price1}" style="float: none;">到<input type="text" id="price2" name="price2" value="${price2}" style="float: none;"></li>
    				<li><input type="button" onclick="subform('${pageContext.request.contextPath}/findAllSchoolDevice?page=1');" value="查询"/>
    				<input class="cancel-submit" type="button" onclick="cancel()" value="取消查询"/>
    				<input type="button" onclick="subform('${pageContext.request.contextPath}/exportDeviceList');" value="导出"></li>
    				</ul>
				</form:form>
		       </div>
    		<!-- 实训室列表 -->
    		<div class="content-box">   		
            <table  class="tb"  id="my_show"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                        <th >设备编号</th>
                        <th >设备名称</th>
                        <th >设备型号</th>
                        <%--<th >设备分类</th>--%>
                        <th >设备价格</th>
                        <th>领用人</th>
                        <%--<th >设备生产厂商</th>--%>
                        <%--<th >领用部门编号</th>--%>
                        <sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
                        <th >操作</th>
                        </sec:authorize>
                </thead>
                <tbody>
                	<c:forEach items="${listSchoolDevice}" var="current"  varStatus="i">
                           <tr>
                           <td>${i.count+pageSize*(page-1) }</td>
                           <td>${current.deviceNumber}</td>
                           <td>${current.deviceName}</td>
                           <td>${current.devicePattern}</td>
                           
                           <%--<td></td>--%>
                           <td>${current.devicePrice}</td>
                           <td>${current.userByUserNumber.cname }</td>
                           <%--<td>${current.manufacturer}</td>--%>
                           
                           <%--<td>${current.schoolAcademy.academyNumber}</td>--%>
                           <sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
                           <td>
                            <a class="btn btn-common" href='${pageContext.request.contextPath}/showSchoolDeviceById?deviceNumber=${current.deviceNumber}'>查看</a> 
                           	<a class="btn btn-common" href='${pageContext.request.contextPath}/editSchoolDevice?deviceNumber=${current.deviceNumber}'>编辑</a>
							<a class="btn btn-common" onclick="deleteSchoolDevice('${current.deviceNumber}')" >删除</a>
                           </td>
                           </sec:authorize>
                           </tr>
                    </c:forEach> 
                </tbody>
            </table>
            

        <div class="page" >
        ${totalRecords}条记录,共${pageModel.totalPage}页
    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/findAllSchoolDevice?page=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/findAllSchoolDevice?page=')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/findAllSchoolDevice?page=${page}">${page}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=page}">
    <option value="${pageContext.request.contextPath}/findAllSchoolDevice?page=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/findAllSchoolDevice?page=')" target="_self">下一页</a>
 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/findAllSchoolDevice?page=${pageModel.totalPage}')" target="_self">末页</a>
    </div>
    <!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		    var config = {
		      '.chzn-select': {search_contains : true},
		      '.chzn-select-deselect'  : {allow_single_deselect:true},
		      '.chzn-select-no-single' : {disable_search_threshold:10},
		      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
		      '.chzn-select-width'     : {width:"95%"}
		    }
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
		    
		    function deleteSchoolDevice(deviceNumber){
		    	if(confirm('确定删除？') == true){
		    		$.ajax({
			            url:"${pageContext.request.contextPath}/deleteSchoolDevice?deviceNumber="+deviceNumber,
			            type: 'POST',  
			            error: function(request) {
			                alert("请求错误!");
			            },
			            success: function(data) {
			            	if(data == "success"){
			            		window.location.href="${pageContext.request.contextPath}/findAllSchoolDevice?page=${page}";
			            	}else{
			            		alert("设备在使用中，无法删除！");
			            	}
			            }
			        });
		    	}
		    }
		    function exportSelect(){
		   	 window.location.href="${pageContext.request.contextPath}/exportDeviceList";
		   	}
		</script>
	<!-- 下拉框的js -->
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>