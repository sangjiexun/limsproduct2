<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
<meta name="decorator" content="iframe"/>
<!-- 下拉的样式 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
<!-- 下拉的样式 -->	
<link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css" />

<%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">--%>
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css" />
	
		<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/js/layer-v2.2/layer/skin/layer.css" />
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">
	
function cancelQuery(){
	window.location.href="${pageContext.request.contextPath}/device/allLendableDeviceList?page=1";
}	
function targetUrl(url)
{
    var content = $("#content").val()
    var startTime = $("input[name='startTime']").val()
	var returnTime = $("input[name='returnTime']").val()

  window.location.href=url+"&startTime="+startTime+"&returnTime="+returnTime+"&content="+content;
}
function cancelQuery(){
	window.location.href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1";
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
		    /*border:1px solid #f3ce7a;*/
		}
		.tab_lab td .combo input[type="text"]:focus{
		    /*border:none;*/
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
						<li><a href="javascript:void(0)"><spring:message code="left.open.appointment" /></a></li>
						<li class="end"><a href="javascript:void(0)"><spring:message code="left.equipment.use" /></a></li>
					</ul>
				</div>
			</div>
<sec:authorize ifNotGranted="ROLE_STUDENT">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
			<li class="TabbedPanelsTab selected" id="s1">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1">设备借用</a>
			</li>
			<li class="TabbedPanelsTab" id="s2">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingApplyList?page=1">我的设备借用申请</a>
			</li>
			<li class="TabbedPanelsTab" id="s3">
				<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/deviceLendingCheckList?page=1">我的设备借用审核</a>
			</li>
			<sec:authorize ifAnyGranted="ROLE_SUPERADMIN">
				<li class="TabbedPanelsTab" id="s4">
					<a class="iStyle_Feelings_Tree_Leaf " href="${pageContext.request.contextPath}/device/invalidDeviceLendingList?currpage=1">设备借用作废列表</a>
				</li>
			</sec:authorize>
		</ul>
	</div>
</sec:authorize>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsTabGroup-box">
<div class="TabbedPanelsContent">
    	<form:form id="myForm" action="${pageContext.request.contextPath}/device/saveLabRoomDeviceLending" method="POST">
			<input id="batchDeviceEles" name="batchDeviceEles" type="hidden" />
     		<table class="tab_lab">
    		<tr>
    			<th>借用日期</th>
    			<td>
    				<input name="startTime" id="startTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceLending.startTime.time}' pattern='MM/dd/yyyy'/>" onclick="new Calendar().show(this);"></input>
     			</td>
    			<th>归还日期</th>
    			<td>
    				<input name="returnTime" id="returnTime" class="easyui-datebox" value="<fmt:formatDate value='${labRoomDeviceLending.returnTime.time}' pattern='MM/dd/yyyy'/>" onclick="new Calendar().show(this);"></input>
    				<a onclick="viewDevice()">查看已选设备</a>
    			</td>
    		</tr>
    		<tr>
    			<th>借用原因(用途)</th>
    			<td colspan="3">
    				<textarea id="content" name="content" rows="5">${labRoomDeviceLending.content}</textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="4" style="text-align:right;">
    				<input class="btn btn-new" type="button" value="提交" onclick="beforeSubmit()"/>
    			</td>
    		</tr>
    	</table>
    	</form:form>
    <div class="TabbedPanelsContentGroup">
		<div class="TabbedPanelsContent">
		<div class="content-box">
		<div class="tool-box">
		     <form name="queryForm" action="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1" method="post" modelAttribute="labRoomDevice">
                 <table class="tab_fix">
					<tr><%--
						<td>
							学期：
							<form:select path="">
							<form:options items="${terms}" itemLabel="termName" itemValue="id"/>
							</form:select>
							
						</td>
						--%>
						<th>实验室/设备名称:</th>
						<td>
						    <input type="text" id="deviceName" name="deviceName" value="${deviceName}"/>
							<input type="submit" value="查询">
							<input class="cancel-submit" type="button" value="取消" onclick="cancelQuery();">
						</td>
					</tr >
			</table></form>	       
		   </div>
    	</div>
    	<div class="content-box">
    		<div class="title">
    			<div id="title">可借用设备列表</div>
    			<div style="float:right;">
    			    <a class="btn btn-primary " style="margin-left:50px" onclick="selectAll()">选择当前页所有设备</a>
    			    <a class="btn btn-primary" onclick="unselectAll()">清空选中</a>
    			</div>
    		</div>
            <table class="tb" id="my_show"> 
                <thead>
                    <tr>
                    	<th>序号</th>
                    	<th>设备编号</th>
                        <th>设备名称</th>
                        <th>设备型号</th>
                        <th>价格</th>
                        <th>实验室名称</th>
                        <th>实验室管理员</th>
                        <%--<th>操作</th>--%>
                    </tr>
                </thead>
                
                <tbody>
                		<c:forEach items="${allLendableDeviceList}" var="curr">
                		<tr>
                		<td><input class="chooseDeviceIndex" type="checkbox" id="${curr.labRoom.id}_${curr.id}" onclick="chooseDevice(this)"/></td>
                        <td>${curr.schoolDevice.deviceNumber}</td>
                        <td>${curr.schoolDevice.deviceName}</td>
                        <td>${curr.schoolDevice.devicePattern}</td>
                        <td>${curr.schoolDevice.devicePrice}</td>
                        <td>${curr.labRoom.labRoomName}</td>
                        <td>
                        	<c:forEach items="${curr.labRoom.labRoomAdmins}" var="admin">
                        		${admin.user.cname}.
                        	</c:forEach>
                        </td>
                        <%--<td><a href='${pageContext.request.contextPath}/device/deviceLendApply?idKey=${curr.id}'>申请借用</a></td>--%>
                        </tr>
                		</c:forEach>
                       
                </tbody>
            </table>
            <!-- 分页模块 -->
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页		    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=1')" target="_self">首页</a>			    
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="targetUrl(this.options[this.selectedIndex].value)">
	<option value="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/allLendableDeviceList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
</div>
<!-- 分页模块 -->
</div>
<!-- 弹出选择学生窗口 -->
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
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
    /**
     * 选择当前页所有设备
     */
    function selectAll(){
    	$("input[type=checkbox]").prop("checked",true);
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
        var selectedDeviceArr = new Array();
        //若为选中操作，增加元素
        if(selectedDeviceStr != null && selectedDeviceStr != ''){
            selectedDeviceArr = selectedDeviceStr.split(',');
        }
        //遍历选中项加入数组
        $.each($(".chooseDeviceIndex"), function(i, item){
            selectedDeviceArr.push(item.id);
        });
        //去重
        selectedDeviceArr = Array.from(new Set(selectedDeviceArr));
        selectedDeviceStr = selectedDeviceArr.toString();

        //重新设置设备id字符串
        sessionStorage.setItem('selectedDeviceId',selectedDeviceStr);
        console.log('当前选中的设备为:  '+sessionStorage.getItem('selectedDeviceId'))
    }
    /**
     * 清空选择项
     */
    function unselectAll(){
        //清空sessionStorage
        sessionStorage.clear()
        location.reload();
    }

    /**
	 * 批量申请
     */
    function batchApplication(){
    	var temp = "";
    	$("#listTable").find("tr:not(:first)").remove();
    	$("input[type=checkbox]:checked").each(function(){
    		if(!$(this).hasClass("selectAll")){
    			var $tr = $(this).parent().parent().clone();
    			$tr.find("td:first").remove();
    			$tr.find("td:last").find("a").remove();
    			var $del = $("<a class='btn btn-primary delDevice' >删除</a>");
    			$tr.find("td:last").append($del);
    			$("#listTable").find("tr:last").after($tr);
    			if(temp==""){temp=$(this).attr("id");}else{temp=temp+","+$(this).attr("id");}
    		}
    		
    	})
    	$(".delDevice").click(function(){
    		$(this).parent().parent().remove();
    	})
    	alert(temp);    	
    }

    /**
	 * 选择设备
     * @param element
     */
    function chooseDevice(element) {
        var deviceId = element.id;
        var checkStus = element.checked;

		//获取sessionstorage中的设备编号
        var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
        var selectedDeviceArr = new Array();
        //拼接字符串
        if(checkStus == true){
            //若为选中操作，增加元素
			if(selectedDeviceStr != null && selectedDeviceStr != ''){
                selectedDeviceArr = selectedDeviceStr.split(',');
			}

            selectedDeviceArr.push(deviceId);
            //去重
            selectedDeviceArr = Array.from(new Set(selectedDeviceArr));
            selectedDeviceStr = selectedDeviceArr.toString();

		}else{
            //若为反选操作，删除元素
            selectedDeviceArr = selectedDeviceStr.split(',');
            selectedDeviceArr = $.grep(selectedDeviceArr, function(value) {
                return value != deviceId;
            });
            selectedDeviceStr = selectedDeviceArr.toString();
		}

        //重新设置设备id字符串
       	sessionStorage.setItem('selectedDeviceId',selectedDeviceStr);
        console.log('当前选中的设备为:  '+sessionStorage.getItem('selectedDeviceId'))
    }

    /**
	 * 页面加载读取选中设备
     */
    $(function () {
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
        if(selectedDeviceStr != null && selectedDeviceStr != ''){
            var selectedDeviceArr = selectedDeviceStr.split(',');
            //遍历数组后渲染dom树
            $.each(selectedDeviceArr, function(i, item){
                $("#"+item).attr("checked","checked");
            });
        }
        console.log('当前选中的设备为:  '+sessionStorage.getItem('selectedDeviceId'))
    });

    /**
	 * 打开已选设备
     */
    function viewDevice() {
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
        if(selectedDeviceStr != null && selectedDeviceStr != ''){
            layer.open({
                type: 2,
                title: '查看已选设备',
                shadeClose: true,
                shade: 0.8,
                area: ['650px', '415px'],
                anim: 2,
                maxmin: true,
                content: '${pageContext.request.contextPath}/device/canLendableDeviceList?currpage=1&selectedDeviceStr='+selectedDeviceStr,
                cancel: function(index, layero){
                   
                }
            })
		}else{
            layer.msg('您还没有选择任何设备，请选择!', {
                icon: 1,
//                shade: [0.8, '#393D49'] // 透明度  颜色
            });
		}

    }

    /**
	 * 表单发送前的方法
     */
    function beforeSubmit() {
        //获取sessionstorage中的设备编号
        var selectedDeviceStr = sessionStorage.getItem('selectedDeviceId');
        // 需要系主任审核与否的标志，默认不需要
        var isDeanAudit = false;
        // 获取是否需要系主任审核
        $.ajax({
            url:"${pageContext.request.contextPath}/device/findDeanAuditStatus",
            type:"POST",
			async: false,
			data:{
                "selectedDevices": selectedDeviceStr
			},
            success:function(data){//AJAX查询成功
                if(data == "needDean"){
                    // 需要系主任审核
                    isDeanAudit = true;
                }else{
                    // 不需要系主任审核
                    isDeanAudit = false;
                }
            }
        });
        if(selectedDeviceStr != null && selectedDeviceStr != '' && (!isDeanAudit || ${dean eq "dean"})){
            //添加所选设备到表单
            $("#batchDeviceEles").val(selectedDeviceStr);
            //清除sessionstorage
            sessionStorage.clear();
            $("#myForm").submit();
		}else if (${dean eq "noDean"}){
            layer.msg("系统未找到您所属的系主任/系教学秘书，不可借用！");
		}
		else {
            layer.msg('您还没有选择任何设备，请选择!', {
                icon: 1,
//                shade: [0.8, '#393D49'] // 透明度  颜色
            });
		}

    }


</script>

<!-- 下拉框的js --> 
</body>
</html>


