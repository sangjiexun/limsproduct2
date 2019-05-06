<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>

<head>
<meta name="decorator" content="back_main" />
<!-- 下拉框的样式 -->
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	
	<!-- 表头排序 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/zhbitlims/common/sortTable.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/zhbitlims/common/jquery.tablesorter.js"></script>
	
	<link href="${pageContext.request.contextPath}/css/cmsSystem/plugins/sweetalert/sweetalert.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lab_project.css" />
	
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cmsSystem/plugins/sweetalert/sweetalert.min.js"></script>
</head>
<body>
<div class="main_container cf rel w95p ma mb60">
<div class="img_box">
			
            <p class="more">
                <img src="${pageContext.request.contextPath}/static/img/uncheck.png"/>
                <span>学年管理</span>
               
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                 	<button data-new class="a-no mr20 mt75">新建</button> 
                 	<a class="a-no mr20 mt75" href="${pageContext.request.contextPath}/basic_data/exportYearTxt">导出txt</a>
                 	<%--<a class="a-no mr20 mt75" href="${pageContext.request.contextPath}/basic_data/exportReportTxtCurrYear">导出学年txt</a>--%>
                 	<%--<a class="a-no mr20 mt75" href="${pageContext.request.contextPath}/basic_data/exportReportCurrYear">导出学年报表</a>--%>
				 </sec:authorize> 
            </p>
            <p class="triangle">
                <span>◥</span>
            </p>
			
			<div class="w95p ma">
				<div class="search">
                    <a class="search-button  " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button  search-button-change" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceReport?currpage=1">基表1-教学科研仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/schoolDeviceChange?currpage=1">基表2-教学科研仪器设备增减变动情况表</a> 
                      <a class="search-button " href="${pageContext.request.contextPath}/basic_data/schoolDeviceValue?currpage=1">基表3-贵重仪器设备表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/operationItemTeaching?currpage=1">基表4-教学实验项目表</a> 
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labTeam?currpage=1">基表5-专任实验室人员表</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/labBasic?currpage=1">基表6-实验室基本情况表</a> 
               		<a class="search-button" href="${pageContext.request.contextPath}/basic_data/labRoomReportFund?currpage=1">基表7-实验室经费情况表</a>
                </div>
                <table class="title w100p p5">
                    <tr>
                        
                    </tr>
                </table>
				<div class="bg ptb5 mb15 overflow">
            	    <table class="tab w97p font">
					<thead>
						<tr> 
							<th class="td-bg">序号</th>
							<th class="td-bg">学年名称</th>
							<th class="td-bg">开始时间</th>
							<th class="td-bg">结束时间</th>
							<th class="td-bg">学年码</th>
							<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
							<th class="td-bg">操作</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${years}" var="current" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td>${current.yearName}</td>
								<td><fmt:formatDate value="${current.yearStart.time}"
										pattern="yyyy-MM-dd" />
								</td>
								<td><fmt:formatDate value="${current.yearEnd.time}"
										pattern="yyyy-MM-dd" />
								</td>
								<td>${current.code}</td>
								<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
							   <td>
						    	         <i data-id='${current.id }' class="fa fa-edit g9 f14 mr5 poi"></i>
						    	         <a class="a-no mr20 mt75" href="${pageContext.request.contextPath}/basic_data/exportReportTxtCurrYear?yearCode=${current.code}">导出学年txt</a>
                 						 <a class="a-no mr20 mt75" href="${pageContext.request.contextPath}/basic_data/exportReportCurrYear?yearCode=${current.code}">导出学年报表</a>
						    	       <%--   <i data-del='${current.id}' class="fa fa-trash-o g9 f14 mr5 poi"></i>       			 --%>						
							  </td> 
								</sec:authorize>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				<!-- 分页导航 -->
				<table class="tab2-1 w100p font pb">
            		<tr>
            			<td class="w16p">
            				<span class="l ml20">${totalRecords}</span>
            				<span class="l">条记录，共</span>
            				<span class="l">${pageModel.totalPage}</span>
            				<span class="l">页</span>
            			</td>
            			<td>
            				<a class="btn2 r mr15" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=${pageModel.lastPage}"  target="_self">末页</a>
                            <a class="btn2 r mr10" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=${pageModel.nextPage}" target="_self">下一页</a>
 	                        <span class="btn2 r mr10">
            					<span class="l">第</span>
            					<span class="l mlr5">
            					    <select class="bn br3 page-w" onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	                                    <option value="${pageContext.request.contextPath}/basic_data/listYear?currpage=${page}">${page}</option>
	                                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
                                            <c:if test="${j.index!=page}">
                                                <option value="${pageContext.request.contextPath}/basic_data/listYear?currpage=${j.index}">${j.index}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
            					</span>
            					<span class="l">页</span>
            				</span>
            				<a class="btn2 r mr10" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=${pageModel.previousPage}"  target="_self">上一页</a>
	                        <a class="btn2 r mr10" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=${pageModel.firstPage}"  target="_self">首页</a>			    
	  				    </td>
            		</tr>
            	</table>
            	<!-- 分页导航 -->
			</div>
		</div>
	</div>
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
	    
	    
	    $(document).ready(
	    	function (){ 
	    		   //获得编辑栏目的id
	    		  $('[data-new]').each(function(i,e){
	    		          $(e).on('click',function(){
	    		         layer.open({
	    		                    type: 2,
	    		                    title: '新建',
	    		                    maxmin: true,
	    		                    shadeClose: true, 
	    		                    area : ['700px' , '350px'],
	    		                    content: '${pageContext.request.contextPath}/basic_data/newYear"'
	    		                    })  
	    		      });                                              
	    		          $('[data-id]').each(function(i,e){
	    				         $(e).on('click',function(){
	    				         layer.open({
	    		                    type: 2,
	    		                    title: '编辑',
	    		                    maxmin: true,
	    		                    shadeClose: true,                
	    		                    area : ['700px' , '350px'],  
	    		                    content:  '${pageContext.request.contextPath}/basic_data/editYear?id='+$(e).attr('data-id')
	    		                    })  
	    		   		      });                                              
	    		   			});
	    		  });
	    		//删除栏目id
	    		 $('[data-del]').each(function(i,e){
	    		    $(e).on('click',function(){
	    		      var _index=$(this);
	    		          swal({
  		                       title: "您确定要删除这条信息吗",
  		                       text: "删除后将无法恢复，请谨慎操作！",
  		                       type: "warning",
  		                       showCancelButton: true,
  		                       confirmButtonColor: "#DD6B55",
  		                       confirmButtonText: "删除",
  		                       closeOnConfirm: false
	    		    }, function () {
	    		       $.ajax({                
	    		            url: '${pageContext.request.contextPath}/basic_data/deleteYear?id='+$(e).attr('data-del'), 
	    		            type: 'DELETE' 
	    		        }).done(function(data) { 
	    		            swal("操作成功!", "已成功删除数据！", "success"); 
	    		            $(_index).parent().parent().fadeOut(2000);
	    		        }).error(function(data) { 
	    		            swal("OMG", "删除操作失败了呢!", "error"); 
	    		        }); 
	    		    });
	    		  });                                              
	    		});    
	    		 });
	    
/* function initYsteps(stepId,steps){
			
			var status = 3;
			
			for(var i = 0 ; i<status+1;i++){
				var step = steps[i];
				step['enable'] = true;
			}
			
			$("#"+stepId).loadStep({
			    size: "large",
			    color: "green",
			    showContent:false,
			    steps: steps
			});
			$("#"+stepId).setStep(status+1);
		}
		 */
		
	    
	</script>
<!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
</div>
</body>