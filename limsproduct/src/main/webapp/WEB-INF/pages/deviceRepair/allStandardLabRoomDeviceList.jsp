<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
	<meta name="decorator" content="iframe"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
	<link type="text/css" rel="stylesheet"
		  href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
	<!-- 全局的引用 -->
	<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 弹出框插件的引用 -->
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js"
			type="text/javascript"></script>
	<!-- bootstrap的引用 -->
	<link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" >
	<script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"
			type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/lims/device/deviceRepair/allStandardLabRoomDeviceList.js"
			type="text/javascript"></script>
	<style>
		.fixed-table-container thead th .sortable{
			background-image:url('${pageContext.request.contextPath}/images/sort.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:30px
		}
		.fixed-table-container thead th .asc{
			background-image:url('${pageContext.request.contextPath}/images/sort_asc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
		}
		.fixed-table-container thead th .desc{
			background-image:url('${pageContext.request.contextPath}/images/sort_dsc.gif');cursor:pointer;background-position:right;background-size:30px 30px;background-repeat:no-repeat;padding-right:10px
		}
	</style>
	<!-- 下拉的样式 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
	<!-- 下拉的样式结束 -->
	<style>
		.chzn-container{
			width: 100%!important;
		}
		.popup_box table th,
		.popup_box table td{
			width:16.6%;
		}
		.fixed-table-toolbar .btn-group>.btn-group:last-child>.btn i, .fixed-table-toolbar .btn-group>.btn-group:last-child>.btn span {
			position: relative;
			top: -6px;
		}
	</style>
<script type="text/javascript">
	
function cancelQuery(){
	window.location.href="${pageContext.request.contextPath}/device/allStandardDeviceList?page=1";
}	
function targetUrl(url)
{
  window.location.href=url;
}
</script>
<style type="text/css">
        .btn_reser{
            text-align:center;
            border: none!important;
            padding: 10px 0!important;
            background:#eee;
            border-bottom:1px solid #eee!important;
            position:relative;
        }
        .btn_reser:hover{
            opacity:0.9;
        }
        .btn_reser:after{
           content: "";
           height: 100%;
           width: 6px;
           padding: 0 0 2px 0;
           background: #fff;
           position: absolute;
           right: 0;
           top: -1px;
        }
        .br_top{
           position:absolute;
           left:-1px;
           top:-1px;
           width:100%;
           height:4px;
           background:#fff;
        }
        .br_btm{
           position:absolute;
           left:-1px;
           bottom:-1px;
           width:100%;
           height:4px;
           background:#fff;
        }
        .btn_reser a{
            display:block;
            width:20px;
            color:#777;
            line-height: 18px;
            white-space:normal;
            margin: 0 0 0 4px;
            font-weight:normal;
            padding: 0 9px!important;
        }
        .br_selected{
            background:#77bace;
            border-bottom:1px solid #77bace!important;
        }
        .br_selected a{
            color:#fff;
        }
        .cf:after{
		    display:block;
		    content:"gvsun";
		    height:0;
		    clear:both;
		    overflow:hidden;
		    visibility:hidden;}
		.tool-box input{
			float:none;
		}
		/*#labRoom_chzn,#usingObj_chzn{*/
			/*width:200px !important;*/
		/*}*/
		.content-box .tab_lab{
	    	width: 100%;
            left: 0;
            margin: -1px;
		}
		.tab_lab{
		width:100%;
		}
		.tab_lab,
		.tab_lab th,
		.tab_lab td{
		    border:1px solid #e4e5e7;
		}

		.tab_lab{
		border-left:none;
		}
		.tab_lab th{
		    background:#fafafa;
		    width:90px;
		    padding:0 15px 0 0;
		    text-align:right;
		}
		.tab_lab td{
		    text-align:left;
	    	padding:10px;
		}
		.tab_lab td input[type="text"],
		.tab_lab td textarea,
		.tab_lab td .spinner,
		.tab_lab td .combo{
		    resize:none;
		    border: 1px solid #cdcdcd;
		    border-radius:3px;
		    line-height:22px;
		    padding:0 10px;
		    outline:none;
		}
		.tab_lab td .spinner,
		.tab_lab td .combo{
		padding:1px 0;
		}
		.tab_lab td input[type="text"]{
		width:124px;
		}
		.tab_lab td .spinner input[type="text"],
		.tab_lab td .combo input[type="text"]{
		    border:none;
		}
		.tab_lab td textarea{
		    width:100%;
		    box-sizing:border-box;
		}
		.tab_lab td input[type="text"]:focus,
		.tab_lab td textarea:focus{
		    border:1px solid #f3ce7a;
		}
		.tab_lab td .combo input[type="text"]:focus{
		    border:none;
		}
		.tab_fix td{
		    text-align:left;
            white-space:nowrap;
            padding-right:20px;
		}
		.tab_fix td input[type="text"]{
		   height: 24px;
           width: 100%;
           box-sizing: border-box;
           /*min-width: 210px;*/
		}
        .tab_fix th{
            font-weight:normal;
            width: 95px;
            text-align: right;
            white-space:nowrap;
        }
        .space{
        margin:0 12px 0 0;
        }

</style>

</head>

<body>
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)">实验设备管理</a></li>
			<li class="end"><a href="javascript:void(0)">设备报修列表</a></li>
		</ul>
	</div>
</div>
<sec:authorize ifNotGranted="ROLE_STUDENT">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
			<c:if test="${sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR' && proj_name eq 'ycitlims'}">
			<li class="TabbedPanelsTab selected" id="s1">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/allStandardDeviceList">设备维修</a>
			</li>
			<li class="TabbedPanelsTab" id="s2">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/viewMyDeviceRepairApply">我的设备维修申请</a>
			</li>
			</c:if>
			<c:if test="${proj_name ne 'ycitlims'}">
				<li class="TabbedPanelsTab selected" id="s1">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/allStandardDeviceList">设备维修</a>
				</li>
				<li class="TabbedPanelsTab" id="s2">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/viewMyDeviceRepairApply">我的设备维修申请</a>
				</li>
			</c:if>
			<li class="TabbedPanelsTab" id="s3">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/deviceRepairCheckList">我的设备维修审核</a>
			</li>
            <li class="TabbedPanelsTab" id="s4">
                <a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/deviceRepairConfirmList">我的设备维修确认</a>
            </li>
			<li class="TabbedPanelsTab" id="s5">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/deviceRepair/deviceRepairViewList">所有设备维修查看</a>
			</li>
		</ul>
	</div>
</sec:authorize>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsTabGroup-box">
<div class="TabbedPanelsContent">
    	<form:form id="myForm" action="${pageContext.request.contextPath}/deviceRepair/saveDeviceRepairApply" method="POST">
			<input id="batchDeviceEles" name="batchDeviceEles" type="hidden" />
			<input id="batchLabRoomEles" name="batchLabRoomEles" type="hidden" />
     		<table class="tab_lab">
    		<tr>
    			<th>物资名称</th>
    			<td>
    				<input class="noallow" name="assetName" id="assetName" type="text" />
					<input type="radio" name="asset" id="chooseAsset" value="2"/>
					<label for="chooseAsset">物资</label>
					<input type="radio" name="asset" id="chooseDevice" value="1" checked/>
					<label for="chooseDevice">设备</label>
     			</td>
				<th>实验室名称</th>
				<td>
					<select class="chzn-select" id="labRoomName" name="labRoomName" style="width:180px" onchange="changeLabRoom()">
						<c:forEach items="${labRooms}" var="current">
								<option value ="${current.id}" >${current.labRoomName} </option>
						</c:forEach>
						<option value="" selected>全部设备</option>
						<%--<option id="editLabAddress" value="">填写实验室</option>--%>
					</select>
					<input type="text" name="labRoomName" id="placeName" style="display:none;"/>
				</td>
    		</tr>
				<tr>
					<th>报修人</th>
					<td>
						<select class="chzn-select" id="reportUser" name="reportUser">
							<option value="">请选择</option>
							<c:forEach items="${users}" var="user" varStatus="u">
								<option value="${user.username}">${user.cname}</option>
							</c:forEach>
						</select>
					</td>
					<th>预计金额</th>
					<td>
						<input type="number" id="expectAmount" name="expectAmount" />
					</td>
				</tr>
    		<tr>
    			<th>报修描述(原因)</th>
    			<td colspan="3">
    				<textarea id="content" name="content" rows="5"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="4" style="text-align:right;">
    				<input class="btn btn-new" type="button" value="提交" onclick="beforeSubmit()"/>
    			</td>
    		</tr>
    	</table>
    	</form:form>
	<div id="TabbedPanels1" class="TabbedPanels">
		<div class="site-box">
			<div class="site-content">
				<div>
					<div id="toolbar">
						<form class="form-inline">
							<div class="form-group">
								<label class="sr-only" for="msg_type">综合查询</label>
								<div class="input-group">
									<div class="input-group-addon">综合查询</div>
									<input type="text" id="search" name="search" value="" placeholder="多字段查询">
								</div>
							</div>
						</form>
					</div>
					<table id="table_list" style="text-align: left;"></table>
				</div>
			</div>
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
      '.chzn-select-width'     : {width:"95%"}
    };
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

    /**
	 * 表单发送前的方法
     */
    function beforeSubmit() {
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = "";
        $("input[name=selectDevice]:checked").each(function () {
            selectedDeviceStr = this.value + "_" + $("input[name=asset]:checked").val();
        });
        if(selectedDeviceStr == null || selectedDeviceStr == '_1' || selectedDeviceStr == ''){
            selectedDeviceStr = $("#assetName").val() + "_" + $("input[name=asset]:checked").val();
            if(selectedDeviceStr == null || selectedDeviceStr == '_2' || selectedDeviceStr == '_1'){
                layer.msg('您还没有选择任何设备，请选择!', {
                    icon: 1,
//                shade: [0.8, '#393D49'] // 透明度  颜色
                });
                return false;
            }
		}
        var selectLabRoom = $("#labRoomName").val() + "_" + 1;
        if(selectLabRoom == null || selectLabRoom == '_1'){
            selectLabRoom = $("#placeName").val() + "_" + 2;
            if(selectLabRoom == null || selectLabRoom == '_2'){
                layer.msg('您还没有选择任何实验室，请选择!');
                return false;
            }
        }
        $("#batchDeviceEles").val(selectedDeviceStr);
        $("#batchLabRoomEles").val(selectLabRoom);
		if($("#reportUser").val() == ""){
			layer.msg("请选择一个报修人!");
			return false;
		}
        if($("#expectAmount").val() == ""){
			layer.msg("您还没有填写预计金额，请填写!");
			return false;
		}
        $("#myForm").submit();
    }
    $(document).ready(function () {
        $("#assetName").attr({"disabled":"disabled"});
    });

    $("#chooseDevice").click(function () {
        $("#assetName").val("");
        $("#assetName").attr({"disabled":"disabled"});
        var a = document.getElementsByName("selectDevice");
        for(var i = 0; i < a.length; i++){
            a[i].disabled = false;
        }
		var editLabAddress = document.getElementById("labRoomName");
        editLabAddress.options.remove(editLabAddress.options.length - 1);
		editLabAddress.add(new Option("全部设备", "", true, true));

        // 关键点：触发 select 的 `chosen:updated` 事件
        $("#labRoomName").trigger('liszt:updated');
        $("#labRoomName").chosen();
        changeLabRoom();
    });

    $("#chooseAsset").click(function () {
        $("#assetName").removeAttr("disabled");
        var a = document.getElementsByName("selectDevice");
        for(var i = 0; i < a.length; i++){
            a[i].checked = false;
            a[i].disabled = true;
        }
		var editLabAddress = document.getElementById("labRoomName");
		editLabAddress.options.remove(editLabAddress.options.length - 1);
		editLabAddress.add(new Option("填写实验室", "", true, true));

		// 关键点：触发 select 的 `chosen:updated` 事件
        $("#labRoomName").trigger('liszt:updated');
        $("#labRoomName").chosen();
        changeLabRoom();
    });




</script>

<!-- 下拉框的js -->
</body>
</html>


