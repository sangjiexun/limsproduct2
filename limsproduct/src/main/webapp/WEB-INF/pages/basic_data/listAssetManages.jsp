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
    <script type="text/javascript">
  
  function cancel(){
	  location.href="${pageContext.request.contextPath}/expendable/listExpendable?currpage=1";
  }
//跳转
  function targetUrl(url)
  {
    document.queryForm.action=url;
    document.queryForm.submit();
  }
  var expendableId; 
  
//导入消耗品记录
  function importSample(){
  	$('#importSample').window({left:"50px",top:"50px"});
  	$("#importSample").window('open');
  	
  }
//校验上传文件是否为excel格式的文件
  function checkFormat() {
      var strs = new Array(); //定义一数组
      var excel1= $("#file").val();
      strs = excel1.split('.');
      var suffix = strs [strs .length - 1];
      if (suffix != 'xls' && suffix != 'xlsx') {
          alert("你选择的不是excel格式的文件,请重新上传！");
          var obj = document.getElementById('file');
          obj.outerHTML = obj.outerHTML; //兼容IE8
      }
  }
//AJAX验证是否有不规范的字段
  function checkRegex(){
  	var formData = new FormData(document.forms.namedItem("importForm"));
  	$.ajax({
          url:"${pageContext.request.contextPath}/importSample/checkRegex",
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false, 
          
          error: function(request) {
              alert("暂时不能判断该文档是否规范");
          },
          success: function(data) {
          	if(data == 'success'){
          		// do nothing
          	}else if(data == 'dateError'){
          		alert("请检查上传文档中的日期格式，然后再次上传");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else if(data == 'dateNotInTerm'){
          		alert("当前日期不属于任何一个学期，请您到系统管理--学期管理新建学期");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else if(data == 'numError'){
          		alert("请检查上传文档中的数字格式，然后再次上传");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else if(data == 'teacherError'){
          		alert("当前授课教师不在教师列表中，请您到系统管理--用户管理中进行添加");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}else{
          		alert("文件格式错误，请您上传excel格式的文档");
          		var obj = document.getElementById('file');
                  obj.outerHTML = obj.outerHTML; //兼容IE8
          	}
          }
      });
  	
  }

  
  </script>
</head>
<body>
<div class="main_container cf rel w95p ma mb60">
<div class="img_box">
            <p class="more">
                <img src="${pageContext.request.contextPath}/static/img/uncheck.png"/>
                <span>资产管理</span>
               
                <sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_DEAN,ROLE_CONSTRUCTIONAUDIT">
                 	<button  class="a-no mr20 mt75" onclick="importSample()">导入</button> 
				 </sec:authorize> 
            </p>
            <p class="triangle">
                <span>◥</span>
            </p>
			
			<div class="w95p ma">
				<div class="search">
                    <a class="search-button   search-button-change " href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=1">资产管理</a>
                    <a class="search-button" href="${pageContext.request.contextPath}/basic_data/listYear?currpage=1">学年管理</a></li>
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
							<th class="td-bg" rowspan="2">序号</th>
							<th class="td-bg" rowspan="2">实验室名称</th>
							<th class="td-bg" rowspan="2">资产分类</th>
							<th class="td-bg" rowspan="2">资产名称</th>
							<th class="td-bg" rowspan="2">附件</th>
							<th class="td-bg" rowspan="2">规格型号</th>
							<th class="td-bg" rowspan="2">图片</th>
							<th class="td-bg" rowspan="2">设备编号</th>
							<th class="td-bg" rowspan="2">存放地点</th>
							<th class="td-bg" rowspan="2">使用人</th>
							<th class="td-bg" rowspan="2">使用情况</th>
							<th class="td-bg" rowspan="2">计量单位</th>
							<th class="td-bg" colspan="4">财务账面值</th>
							<th class="td-bg" rowspan="2">经费来源</th>
							<th class="td-bg">实盘数</th>
							<th class="td-bg">盘盈</th>
							<th class="td-bg">盘亏</th>
							<th class="td-bg" rowspan="2">备注</th> 
						</tr>
						<tr>
							<th class="td-bg">数量</th>
							<th class="td-bg">单价</th>
							<th class="td-bg">金额</th>
							<th class="td-bg">总金额</th>
							<th class="td-bg">复盘数量</th>
							<th class="td-bg">数量</th>
							<th class="td-bg">数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${assetManages}" var="current" varStatus="i">
							<tr>
								<td>${i.count+(page-1)*pageSize}</td>
								<td>${current.labRoom.labRoomName}</td>
								<td>${current.fixedAssetClass}</td>
								<td>${current.fixedAssetName}</td>
								<td>${current.files}</td>
								<td>${current.specification}</td>
								<td>${current.pics}</td>
								<td>${current.equipmentNumber}</td>
								<td>${current.location}</td>
								<td>${current.user}</td>
								<td>${current.conditions}</td>
								<td>${current.units}</td>
								<td>${current.num}</td>
								<td>${current.unitPrice}</td>
								<td>${current.financialAmount}</td>
								<td>${current.financialTotal}</td>
								<td>${current.sourceFund}</td>
								<td>${current.actualComplex}</td>
								<td>${current.num1}</td>
								<td>${current.num2}</td>
								<td>${current.mem}</td>
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
            				<a class="btn2 r mr15" href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=${pageModel.lastPage}"  target="_self">末页</a>
                            <a class="btn2 r mr10" href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=${pageModel.nextPage}" target="_self">下一页</a>
 	                        <span class="btn2 r mr10">
            					<span class="l">第</span>
            					<span class="l mlr5">
            					    <select class="bn br3 page-w" onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	                                    <option value="${pageContext.request.contextPath}/basic_data/assetManage?currpage=${page}">${page}</option>
	                                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
                                            <c:if test="${j.index!=page}">
                                                <option value="${pageContext.request.contextPath}/basic_data/assetManage?currpage=${j.index}">${j.index}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
            					</span>
            					<span class="l">页</span>
            				</span>
            				<a class="btn2 r mr10" href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=${pageModel.previousPage}"  target="_self">上一页</a>
	                        <a class="btn2 r mr10" href="${pageContext.request.contextPath}/basic_data/assetManage?currpage=${pageModel.firstPage}"  target="_self">首页</a>			    
	  				    </td>
            		</tr>
            	</table>
            	<!-- 分页导航 -->
			</div>
		</div>
	</div>
</div>

<div id= "importSample"  class ="easyui-window panel-body panel-body-noborder window-body" title= "导入模版" modal="true" dosize="true" maximizable= "true" collapsible ="true" minimizable= "false" closed="true" iconcls="icon-add" style=" width: 600px; height :400px;">
        <form:form name="importForm" action="${pageContext.request.contextPath}/basic_data/importAssetManage" enctype ="multipart/form-data">
	         <br>
	         <input type="file" id="file" name ="file"  required="required" />
	         <input type="submit"  value ="开始上传" />
	         <br><br><hr><br>
		<%--<a href="${pageContext.request.contextPath}/pages/importSample/消耗品记录.xlsx">点击此处</a>，下载范例<br><br><hr><br>
		示例图片：<br>
		<img src="${pageContext.request.contextPath}/images/importSample/expendable.png" heigth ="65%" width="65%" />--%>
        </form:form>
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
	    
 
	    
	</script>
<!-- 下拉框的js -->
	<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
</div>
</body>