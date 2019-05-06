<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>

<head>
    <title></title>
    <!--[if lt IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <![endif]-->
    <!--[if gte IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>  
    <![endif]-->
    <meta name="decorator" content="iframe"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="" rel="stylesheet" type="text/css">
    <script type="text/javascript" src=""></script>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式结束 -->	
 <script type="text/javascript">
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
	//取消查询
	function cancelQuery(){
		window.location.href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1";
	} 
</script>   
    <style type="text/css">
        .layout_classroom_intro {
            height: 41px;
            font-size: 14px;
            line-height: 20px;
            text-align: center;
            padding: 8px 0 0;
        }
        .layout_classroom_intro span{
            font-size: 13px;
            letter-spacing: 0.5px;
            color: #22ade8;
            border-bottom: 1px solid #55beec;
            -webkit-transition: opacity ease-in-out .2s;
            -o-transition: opacity ease-in-out .2s;
            transition: opacity ease-in-out .2s;
        }
        .layout_classroom_list{
        	overflow:hidden;
			border: 1px solid #d0d6dc;
			margin-top: 10px;
        }
        .layout_classroom_list li {
            width: 25%;
            float: left;
            margin: 17px 0 8px;
            box-sizing: border-box;
            padding: 0 11px;
       }
        
        .layout_classroom {
            width: 90%;
            margin: auto;
            overflow: hidden;
        }
        
        .layout_classroom_pic {
            width: 100%;
            border: 0px;
        }
        
        li {
            list-style: none;
        }
        a{
            text-decoration: none;
        }
        .layout_classroom a{
            color:#6ba6b4
        }
        .layout_classroom a:hover{
            color:#555
        }
        .tab_fix{
            width: 98%;
            position: relative;
            left: 1%;
        }
        .tab_fix th {
            font-weight: normal;
            width: 95px;
            text-align: right;
            white-space: nowrap;
        }
       .tab_fix td {
            text-align: left;
            white-space: nowrap;
            padding-right: 20px;
            border-bottom: 1px solid #e4e5e7;
            padding: 10px 20px 10px 7px!important;
       }
       .tab_fix td>input[type="text"] {
            height: 24px;
            width: 100%;
            box-sizing: border-box;
            /*min-width: 210px;*/
       }
       .tab_fix td select{
           width:100%;
       }
       .chzn-container{
           width:100%!important;
       }
       .flag_img_limit{
           padding:42% 0 0 0;
       }
       .flag_img_limit>div img {
           position:absolute;
           top:0;
           bottom:0;
           left:0;
           right:0;
           margin:auto;
           width: 100%;
           height: auto;
           background:#f1f1f1;
       }
       .flag_img_limit>div:hover img {
           opacity: 1;
       }
       .layout_classroom_list li a img{
            -webkit-transition: all ease-in-out .4s;
            -o-transition: all ease-in-out .4s;
            transition: all ease-in-out .4s;
       }
       .layout_classroom_list li a:hover .flag_img_limit div img{
           opacity:0.7;           
           transform:scale(1.2);
       }
       .layout_classroom_list li a:hover .layout_classroom_intro span{
           color: #1d67f5;
           border-bottom: 1px solid #6c92ff;
       }
       .tool-box{
           overflow:visible;
           clear:both;
       }
       #username_chzn_chzn{
       	display:none;
       }
       #username_chzn{
       	display:block !important;
       }
		.tool-box input {
			float: none;
		}
    </style>
</head>

<body>
<div class="navigation">
		<div id="navigation">
			<ul>
				<li><a href="javascript:void(0)"><spring:message code="all.trainingInfo.management" /></a></li>
				<li class="end"><a href="javascript:void(0)"><spring:message code="left.device.management" /></a></li>
			</ul>
		</div>
	</div>
<%--<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
		<li class="TabbedPanelsTab selected" id="s1"><a href="${pageContext.request.contextPath}/device/listLabRoomDevice?page=1&isOrder=${idOrder}">设备管理</a></li>
		<li class="TabbedPanelsTab" id="s2"><a href="${pageContext.request.contextPath}/device/applyDeviceRepairList?page=1">保修登记</a></li>
		<li class="TabbedPanelsTab" id="s3"><a href="${pageContext.request.contextPath}/device/listLabRoomDeviceUsage?page=1">设备使用记录</a></li>
	</ul>

 --%>

<div id="TabbedPanels1" class="TabbedPanels">
 <div class="TabbedPanelsContent">
	 <ul class="TabbedPanelsTabGroup">
		 <li class="TabbedPanelsTab1 selected" id="s1">
			 <a href="javascript:void(0)">设备管理</a>
		 </li>
	 </ul>
	 <div class="TabbedPanelsContentGroup">
		 <div class="TabbedPanelsContent">
    <div class="content-box">
 <div class="tool-box">
            <form:form name="queryForm" action="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?page=1&roomId=-1" method="post" modelAttribute="labRoomDevice">
					<table class="tab_fix">
						<tr>
							<th><spring:message code="all.trainingRoom.labroom" />:</th>
							<td>
								<form:select class="chzn-select" path="labRoom.id" id="labRoom_id">
								<form:option value="">请选择</form:option>
									<form:options items="${rooms}" itemLabel="labRoomName" itemValue="id"></form:options>
								</form:select>		    				    				            
							</td>
							<th>设备开放:</th>
							<td>
								<form:select class="chzn-select" path="CDictionaryByAllowAppointment.CNumber" id="schoolDevice_allowAppointment">
									<form:option value="">请选择</form:option>
									<form:option value="1">是</form:option>
									<form:option value="2">否</form:option>
								</form:select>
							</td>
							<th>设备管理员:</th>
							<td>
								<form:select class="chzn-select"  path="user.username" id="username">
									<form:option value="">请选择</form:option>
									<form:options items="${userMap}"/>
								</form:select>
							</td>  
							</tr>
							<tr>						 							 
							<th>设备编号:</th>
							<td>
								<form:input path="schoolDevice.deviceNumber" id="schoolDevice_deviceNumber"/>
							</td> 
				     
							<th>设备名称:</th>
							<td>
								<form:input path="schoolDevice.deviceName" id="schoolDevice_deviceName"/>
							</td>
							<%--<td colspan="2" style="text-align:right;">--%>
							<td colspan="2">
								<input type="submit" value="查询">
								<input class="cancel-submit" type="button" value="取消" onclick="cancelQuery()"/>
							</td>
						</tr >
				</table>
				</form:form>	
</div>
</div>

<div class="right-content">		
	<ul class="layout_classroom_list">
	<c:forEach items="${roomsList}" var="room" varStatus="i">
        <li>
                <a href="${pageContext.request.contextPath}/device/listLabRoomDeviceNew?roomId=${room.id}&page=1" title="查看${room.labRoomName }设备列表">
	                <c:set var="flag" value="0"></c:set>
                	<c:forEach items="${room.commonDocuments}" var="d" varStatus="i">
	                	<c:if test="${d.type==4}">
	                	<c:set var="flag" value="1"></c:set>
	                    	<div class="flag_img_limit">
	                    	    <div>
	                    	        <img src="${pageContext.request.contextPath}/${d.documentUrl}" class="layout_classroom_pic">
	                    	   </div>
	                    	</div>
	                    </c:if>	
					</c:forEach>
					<c:if test="${flag == 0}">
	                    	<div class="flag_img_limit">
	                    	    <div>	                    	        
	                    	        <img src="${pageContext.request.contextPath}/images/no-img.jpg" class="layout_classroom_pic"/>
	                    	    </div>
	                    	</div>
	                    </c:if>	
                    <div class="layout_classroom_intro"><span>${room.labRoomName }</span></div>
                </a>
        </li>
	</c:forEach>
    </ul>
	
		<div class="TabbedPanelsContentGroup">
			<div class="TabbedPanelsContent">
			<!-- 分页模块 -->
			<div class="page" >
			        ${totalRecordsLabroom}条记录,共${pageModelLabroom.totalPage}页
			    <a href="javascript:void(0)"    onclick="first('${pageContext.request.contextPath}/device/listLabRoomDevice?page=1')" target="_self">首页</a>			    
				<a href="javascript:void(0)" onclick="previous('${pageContext.request.contextPath}/device/listLabRoomDevice?page=')" target="_self">上一页</a>
				第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
				<option value="${pageContext.request.contextPath}/device/listLabRoomDevice?page=${page}">${page}</option>
				<c:forEach begin="${pageModelLabroom.firstPage}" end="${pageModelLabroom.lastPage}" step="1" varStatus="j" var="current">	
			    <c:if test="${j.index!=page}">
			    <option value="${pageContext.request.contextPath}/device/listLabRoomDevice?page=${j.index}">${j.index}</option>
			    </c:if>
			    </c:forEach></select>页
				<a href="javascript:void(0)"     onclick="next('${pageContext.request.contextPath}/device/listLabRoomDevice?page=')" target="_self">下一页</a>
			 	<a href="javascript:void(0)"    onclick="last('${pageContext.request.contextPath}/device/listLabRoomDevice?page=${pageModelLabroom.totalPage}')" target="_self">末页</a>
			    </div>
			<!-- 分页模块 -->
			</div>
		</div>
		</div>
		 </div>
	 </div>
 </div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
      '.chzn-select'           : {search_contains:true},
      '.chzn-select-deselect'  : {allow_single_deselect:true},
      '.chzn-select-no-single' : {disable_search_threshold:10},
      '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chzn-select-width'     : {width:"100%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    
</script>
<!-- 下拉框的js -->

</body>

</html>