<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<head>
<meta name="decorator" content="iframe" />
<link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">
<!-- 下拉框的样式 -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/chosen/chosen.css" />
	
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/js/jquery-easyui/themes/default/easyui.css" />
<!-- 下拉的样式结束 -->
<!-- 打印插件的引用 -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
  <!-- 弹窗 -->
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>

	<script type="text/javascript">

        function onChangeDate() {
            if($("#labRoom").val() == ""){
                alert("请先填写实验室名称")
            }
            if ($("#labRoom").val() != "") {
                if ($("#lendingTime").val() != "") {
                    $.ajax({
                        url:"${pageContext.request.contextPath}/labRoomLending/checkConflict",
                        type:"POST",
                        data:{
                            labRoom: $("#labRoom").val(),
                            lendingTime: $("#lendingTime").val()
                        },
                        success:function (data) {
                            $("#reservationTime").html(data);
                            $("#reservationTime").trigger("liszt:updated");
                        }
                    });
                }
            }
        }
	</script>
  <script type="text/javascript">
  //取消查询
  function cancel(){
	  window.location.href="${pageContext.request.contextPath}/lab/labAnnexList?currpage=1"
  }
  //分页跳转
  function targetUrl(url)
  {
    document.form.action=url;
    document.form.submit();
  }
  //弹出学生预约框
  function showLabRoomReservation(labRoomId){
	  layer.ready(function(){
	        layer.open({
	            type: 2,
	            title: '实验室预约',
	            fix: true,
	            maxmin:true,
	            shift:-1,
	            closeBtn: 1,
	            shadeClose: true,
	            move:false,
	            maxmin: false,
	            area: ['1000px', '420px'],
	            content: '../LabRoomReservation/showLabRoomReservation?labRoomId='+labRoomId,
	            end: function(){
	            }
	        });
	    });
  }
  //弹出教师预约框
  function showLabRoomReservationTeacher(labRoomId){
	  layer.ready(function(){
	        layer.open({
	            type: 2,
	            title: '教师-实验室预约',
	            fix: true,
	            maxmin:true,
	            shift:-1,
	            closeBtn: 1,
	            shadeClose: true,
	            move:false,
	            maxmin: false,
	            area: ['1000px', '420px'],
	            content: '../LabRoomReservation/showLabRoomReservationTeacher?labRoomId='+labRoomId,
	            end: function(){
	            }
	        });
	    });
  }
    //弹出实验室实况框
  function viewNow(labRoomId){
	  layer.ready(function(){
	        layer.open({
	            type: 2,
	            title: '实验室实况',
	            fix: true,
	            maxmin:true,
	            shift:-1,
	            closeBtn: 1,
	            shadeClose: true,
	            move:false,
	            maxmin: false,
	            area: ['1000px', '420px'],
	            content: '../LabRoomReservation/showLabRoomNow?labRoomId='+labRoomId,
	            end: function(){
	            }
	        });
	    });
  }
//弹出软件详情
  function showLabRoomSoftware(labRoomId){
	  layer.ready(function(){
	        layer.open({
	            type: 2,
	            title: '实验室软件详情',
	            fix: true,
	            maxmin:true,
	            shift:-1,
	            closeBtn: 1,
	            shadeClose: true,
	            move:false,
	            maxmin: false,
	            area: ['1000px', '420px'],
	            content: '../LabRoomReservation/showLabRoomSoftware?labRoomId='+labRoomId,
	            end: function(){
	            }
	        });
	    });
  }
//弹出设备详情
  function showLabRoomDevice(labRoomId){
	  layer.ready(function(){
	        layer.open({
	            type: 2,
	            title: '实验室设备详情',
	            fix: true,
	            maxmin:true,
	            shift:-1,
	            closeBtn: 1,
	            shadeClose: true,
	            move:false,
	            maxmin: false,
	            area: ['1000px', '420px'],
	            content: '../LabRoomReservation/showLabRoomDevice?labRoomId='+labRoomId,
	            end: function(){
	            }
	        });
	    });
  }
  //学生预约资格判断
  function judgeAccess(labRoomId){
	  $.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/LabRoomReservation/judgeAccess?labRoomId="+labRoomId,
			data: '',
			dataType:'text',
			success:function(data){
				if(data == "noSetting"){
					layer.msg('该实验室未进行初始化设置，不可预约', {icon: 5});
				}else if(data == "noAccess"){
					layer.msg('该实验室需要参加培训并通过后方可预约', {icon: 5});
				}else if(data == "noPassTest"){
					layer.msg('该实验室需要参加网上测试并通过后方可预约', {icon: 5});
				}else if(data == "noCreditScore"){
					layer.msg('您的信誉积分低于80分，不可预约', {icon: 5});
				}else if(data == "noDean"){
					layer.msg('系统未查到您所属的系主任/系教学秘书，不可预约', {icon: 5});
				}else{
					//可预约，跳出弹框
					 showLabRoomReservation(labRoomId);
				}
			},
		  error: function(){
				layer.msg('发生未知错误', {icon: 5});
			}
	 	})
  }
//教师预约预约资格判断
  function judgeAccessTeacher(labRoomId){
	  $.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath}/LabRoomReservation/judgeAccess?labRoomId="+labRoomId,
			data: '',
			dataType:'text',
			success:function(data){
				if(data == "noSetting"){
					layer.msg('该实验室未进行初始化设置，不可预约', {icon: 5});
				}else if(data == "noAccess"){
					layer.msg('该实验室需要参加培训并通过后方可预约', {icon: 5});
				}else if(data == "noPassTest"){
					layer.msg('该实验室需要参加网上测试并通过后方可预约', {icon: 5});
				}else if(data == "noCreditScore"){
					layer.msg('您的信誉积分低于80分，不可预约', {icon: 5});
				}else if(data == "noDean"){
					layer.msg('系统未查到您所属的系主任/系教学秘书，不可预约', {icon: 5});
				}else{
					//可预约，跳出弹框
					showLabRoomReservationTeacher(labRoomId);
				}
			},
	  		error:function(){
	  			layer.msg('发生未知错误', {icon: 5});
	  		}
	 	})
  }
//实验室设置
  function openSetupLink(labRoomId,currpage){//将labRoomId page传递到后台
  	//alert(labRoomId);
  	var url ="${pageContext.request.contextPath}/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/1";
  	
  	window.location.href=url;
  }
//培训预约(编辑--针对老师)
  function editLabRoomTrainingRest(id){
      var labRoom_id = $("#labRoom_id").val();
      var schoolDevice_deviceNumber = $("#schoolDevice_deviceNumber").val();
      var schoolDevice_deviceName =$("#schoolDevice_deviceName").val();
      var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
      var username = $("#username").val();
      //alert(schoolDevice_deviceName);
  	if($("#labRoom_id").val()==""){
  	  labRoom_id ="-1";
  	}
  	if($("#schoolDevice_deviceNumber").val()==""){
  	  schoolDevice_deviceNumber ="-1";
  	}
  	if($("#schoolDevice_deviceName").val()==""){
  	  schoolDevice_deviceName ="-1";
  	}
  	if($("#username").val()==""){
  		username ="-1";
  	}
  	if($("#schoolDevice_allowAppointment").val()==""){
  		schoolDevice_allowAppointment ="-1";
  	}
  	var url = "${pageContext.request.contextPath}/device/editLabRoomTrainingRest/"+ labRoom_id + "/"+ schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/${page}/" + id+"/"+schoolDevice_allowAppointment;
  	//alert(url);
  	window.location.href=url;
  }
//培训预约(查看--针对学生)
  function viewLabRoomTrainingRest(id){
      var labRoom_id = -1;
      var schoolDevice_deviceNumber = -1;
      var schoolDevice_deviceName =-1;
      var username = -1;
      var labRoom_allowAppointment = -1;

  	var url = "${pageContext.request.contextPath}/labReservation/viewLabRoomTrainingRest/" +labRoom_id + "/"+schoolDevice_deviceNumber + "/" + schoolDevice_deviceName + "/" + username +"/"+1+"/" + id+"/"+labRoom_allowAppointment;
  	
  	window.location.href=url;
  }
  
//考试
  function findTestListForAdmin(labRoomId){//将labRoomId deviceNumber deviceName page传递到后台
	//alert("11");
	var page = ${currpage};
	var status = -1;
  	var url = "${pageContext.request.contextPath}/teaching/test/labRoomTestList/" + labRoomId + "/"+page+"/"+status;
  	window.location.href=url;
  }
//考试
  function findTestList(labRoomId){//将labRoomId deviceNumber deviceName page传递到后台
  	var page = ${currpage};
  	var status = -1;
  	var url = "${pageContext.request.contextPath}/teaching/test/labRoomTestListForStudentAndTeacher/" + labRoomId + "/"+page+"/"+status;
  	window.location.href=url;
  }
  </script>
<script type="text/javascript">
		 $(document).ready(function () {  
		 			/* $(".addStudent") */
		 			$("#addStudent").attr("style","display:none;");
            }); 
		 
		 function checkObj(){
					var obj = document.getElementById("usingObj").value;
					if(obj == 1){
						$("#addStudent").show();
					}else{
						$("#addStudent").attr("style","display:none;");
					}
		}
		
		//ajax查询用户的班级表  
  function getSchoolClasses(grade){ 		
  	$.ajax({
  		type: "POST",
  		url: "${pageContext.request.contextPath}/timetable/selfTimetable/getSchoolClasses",
  		data: {'grade':grade},
  		dataType:'json',
  		success:function(data){
  			var jslength=1;
  			var currLine=1;
  			for(var js2 in data){jslength++;}
  			if(jslength==0){alert("本周无课程数据");}else{}
  			var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td colspan=3><b>选择班级</b></td></tr>";//新建html字符
  			$.each(data,function(key,values){  
  			   if(currLine%3==0){
  		           tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClassesUser("+ key +")'>" + values + "</a></td><tr>";
  			   }else  if(currLine%3==1){
  			       tableStr = tableStr + "<tr><td><a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClassesUser("+ key +")'>" + values + "</a></td>";
  			   }
  			   else  if(currLine%3==2){
  			       tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClassesUser("+ key +")'>" + values + "</a></td>";
  			   }
  			   currLine=currLine+1
  			   jslength=jslength+1;
  			 }); 
  			 tableStr = tableStr + "</tr></table>";
  			 document.getElementById('schoolClasses').innerHTML=tableStr; 	
  		},
  		error:function(){
  			//alert("加载课表失败!");
  			}
  	});
  }
  
  //ajax查询班级用户列表  
  function putSchoolClassesUser(){ 		
  	 var obj = document.getElementsByName("username");
  	 var s='';//如果这样定义var s;变量s中会默认被赋个null值
       for(var i=0;i<obj.length;i++){
           if(obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
           s+=obj[i].value+",";   //如果选中，将value添加到变量s中    
       }
       var str = $('#students').val() +"," +s;
       $('#students').val(s);
       $("#newStudents").window('close');
  }
//ajax查询班级用户列表  
  function getSchoolClassesUser(classNumber){ 		
  	$.ajax({
  		type: "POST",
  		url: "${pageContext.request.contextPath}/timetable/selfTimetable/getSchoolClassesUser",
  		data: {'classNumber':classNumber},
  		dataType:'json',
  		success:function(data){
  			var jslength=1;
  			var currLine=1;
  			var allLine=1;
  			for(var js2 in data){jslength++;}
  			if(jslength==0){alert("本周无课程数据");}else{}

  			var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td><b>选择学生</b></td><td colspan=3><input class='btn btn-primary btn-lg' type='button' onclick='putSchoolClassesUser()' value='提交'/></td></tr>";//新建html字符
  			$.each(data,function(key,values){  
  			   if(currLine%4==0){
  		           tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td><tr>";
  			   }else  if(currLine%4==1){
  			       tableStr = tableStr + "<tr><td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
  			   }else  if(currLine%4==2){
  			       tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
  			   }else if(currLine%4==3){
  			       tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
  			   }
  			   //currLine=currLine%4;
  			   jslength=jslength+1;
  			   currLine = currLine +1;
  			   allLine =allLine+1;
  			 }); 
  			 if(currLine%4==0){
  			   tableStr = tableStr + "</table>";
  			 }else if(currLine%4==1){
  			   tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td><td&nbsp;></td></tr></table>";
  			 }else if(currLine%4==2){
  			   tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td></tr></table>";
  			 }else if(currLine%4==3){
  			   tableStr = tableStr + "<td>&nbsp;</td></tr></table>";
  			 }
  			
  			 document.getElementById('schoolClassesUser').innerHTML=tableStr; 	
  		},
  		error:function(){
  			//alert("加载课表失败!");
  			}
  	});
  }
  
  //查询剩余工位数
 function findRestStations(){
	//console.log($("input[name='reservationtime']") .val());
	 if($("input[name='reservationtime']") .val() == ""){
		 alert("请选择日期");
		 return false;
	 }
	 if($("#starttime").val()){
	 }else{
		 alert("请选择开始时间");
		 return false;
	 }
	 if($("#endtime").val()){
	 }else{
		 alert("请选择结束时间");
		 return false;
	 }
	 if($("#starttime").val() > $("#endtime").val()){
		 alert("开始时间不可大于结束时间");
		 return false;
	 }
	 
	 var myData = {
		 'reservationTime':$("input[name='reservationtime']") .val(),
		 'startTime':$("#starttime").val(),
		 'endTime':$("#endtime").val()
	 }
	 var labRoomId = document.getElementById("labRoom").value;
	 $.ajax({
		type: "POST",
		url: "${pageContext.request.contextPath}/LabRoomReservation/findRestStations?labRoomId="+labRoomId,
		data: myData,
		dataType:'json',
		success:function(data){
			$("#restStations").text(data);
		},
		error:function(){
  			alert("查询失败！后台出了点问题！");
  		} 
 	})
 }
 </script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/labreservation/labreservation.js"></script>
<script type="text/javascript">
function cancel(){
	window.location.href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1";
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
		.content-box .tab_lab{
	    	width: 100%;
            left: 0;
            margin: -1px;
		}
		.tab_lab{
		width:100%;
		}
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
			vertical-align: top;
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
<!--导航  -->
<div class="navigation">
	<div id="navigation">
		<ul>
			<li><a href="javascript:void(0)"><spring:message code="left.open.appointment" /></a></li>
			<li class="end"><a href="javascript:void(0)"><spring:message code="left.station.appointment" /></a></li>
		</ul>
	</div>
</div>
<!--导航结束  -->
<div id="TabbedPanels1" class="TabbedPanels">
	<ul class="TabbedPanelsTabGroup" style="margin-bottom:10px;">
		<li class="TabbedPanelsTab selected" id="s1">
			<a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=1"><spring:message
					code="all.trainingRoom.station"/>预约</a><%--实验室--%></li>
		<li class="TabbedPanelsTab" id="s2"><a
				href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=0&currpage=1&isaudit=2">我的申请</a>
		</li>
		<sec:authorize ifNotGranted="ROLE_STUDENT">
			<li class="TabbedPanelsTab" id="s3"><a
					href="${pageContext.request.contextPath}/LabRoomReservation/labRoomReservationList?tage=0&currpage=1&isaudit=1">我的审核</a>
			</li>
		</sec:authorize>
	</ul>
</div>
<div class="right-content">
<div id="TabbedPanels1" class="TabbedPanels">
<div class="TabbedPanelsTabGroup-box">
<div class="TabbedPanelsContentGroup">
<div class="TabbedPanelsContent">
	<form id="formid" method="POST">
				<table class="tab_lab">
					<tr>
						<th>
							<input type="hidden" id="deviceType" name="deviceType">
							<spring:message code="all.trainingRoom.labroom" />名称
						</th>
						<td>
							<select class="chzn-select" id="labRoom" onchange="selectLabRoom()">
							<option value="">--请选择--</option>
							<c:forEach items="${labRooms}" var="labRoom">
							<c:if test="${labRoom.id==selectedRoomId }">
							<option value="${labRoom.id}" selected="selected">${labRoom.labRoomName }</option>
							</c:if>
							<c:if test="${labRoom.id !=selectedRoomId }">
								<option value="${labRoom.id}">${labRoom.labRoomName }</option>
								</c:if>
							</c:forEach>
							</select>
						</td>
						<th>选择日期</th>
						<%--<td>--%>
							<%--<input  class="easyui-datebox"  id="reservationTime" name="reservationtime"  type="text"  onclick="new Calendar().show(this);"/>--%>
						<%--<span>--%>
							<%--<font class="space"></font>--%>
						<%--预约开始时间&nbsp;:--%>
							<%--<input  class="easyui-timespinner"  id="starttime" name="starttime" type="text"  style="width:80px;"--%>
    <%--required="required" />--%>
						<%--<font class="space"></font>--%>
						<%--预约结束时间&nbsp;:--%>
							<%--<input  class="easyui-timespinner"  id="endtime" name="endtime"  type="text" style="width:80px;"--%>
    <%--required="required"/>--%>
							<%--<a onclick="findRestStations()">查询</a>--%>
						<%--</span>--%>
							<%--&lt;%&ndash;<font style="color: red">请选择准确时间以查询剩余的工位数量</font>--%>
						<%--&ndash;%&gt;</td>--%>
						<td colspan="3">
							<input class="Wdate" id="lendingTime" name="lendingTime" type="text"
								   value="<fmt:formatDate value="${labReservation.lendingTime.time}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({minDate:'%y-%M-{%d}',dateFmt:'yyyy-MM-dd',onpicked:function(){onChangeDate();}})" />
								<%--<input  class="easyui-datebox"  id="reservationTime" name="reservationtime"  type="text"  onclick="new Calendar().show(this);"/>--%>
							<font class="space"></font>
							预约时间&nbsp;:
							<select class="chzn-select" name="reservationTime" id="reservationTime" style="" multiple>

							</select>
						</td>
					</tr>
					<tr>
					<c:if test="${user.userRole ne 0 }">
					<th>
						使用对象
					</th>
					<td>
						<select class="chzn-select" id="usingObj" name="userRole" onchange="checkObj()">
								<option value="2">教师</option>
								<option value="1">学生</option>
						</select>
					</td>
					</c:if>
					<c:if test="${user.userRole eq 0 }">
                          <input id="usingObj" name="userRole"  type="hidden"  value="1" />
					</c:if>
					<th>剩余工位数</th>
					<td>
					<span id="restStations"></span>
					</td>
					</tr>
					<tr>
						<%--工位预约/实验室预约分拆--%>
						<%--<sec:authorize ifNotGranted="ROLE_STUDENT">--%>
							<%--<c:if test="${!fn:contains('zjcclims',PROJECT_NAME)
							|| sessionScope.selected_role ne 'ROLE_STUDENT'}">
					    <td rowspan="2" class="btn_reser" style="vertical-align: middle">
					        <div class="br_top"></div>
					        <a href="${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"><spring:message code="all.trainingRoom.labroom" />预约111</a
					    </td>
							</c:if>--%>
					    <%--</sec:authorize>--%>
						<th>预约原因</th>
						<td colspan="3">
							<textarea id="reason" type="text" rows="5"></textarea>
						</td>
						<%--<c:if test="${dean eq 1}">系主任这一级的刷选逻辑修改了，不需要自己选
						<th>选择审核系主任</th>
						<td>
						<select id="dean" style="height: 25px;width: 80px;">
						<c:forEach items="${deanList}" var="s">
						<option value="${s.username}">${s.cname}</option>
						</c:forEach>
						</select>
						</td>
						</c:if>
					--%>
					</tr>
					<c:if test="${user.userRole ne 0 }">
						<tr id="addStudent">
							<th style="min-width:120px;text-align:right;">添加学生<a class="btn btn-common" href='javascript:void(0)'	onclick='newStudents()'>选择添加</a>
								<br/>(或输入学生学号，以逗号分开)
							</th>
							<td style="text-align:left;"><textarea rows="" cols=""
								name="students" id="students"
								style="width: 400px;height: 60px;padding: 5px"></textarea></td>
							<td></td>
						</tr>
					</c:if>
					<tr>
						<td colspan="4" style="text-align:right;"><input class="btn" type="button" value="提交" onclick="openChooseTeacher()"></td>
					</tr>
				</table>					
			</form>	
<div class="right-content">
			<div class="content-box">
				<div class="tool-box">
					<table class="tab_fix">
						<form:form name="form" action="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=1" method="post" modelAttribute="labRoom">
							<tr>
							<th><spring:message code="all.trainingRoom.labroom" />名称：</th>
							<td colspan="3"><form:input path="labRoomName" /></td>
							<th>软件名称：</th>
							<td><form:input path="labRoomEnName"/></td><%--这里并不是labRoomEnName 只是为了放这个条件--%>
							</tr>
							<tr>
							<th>设备名称：</th>
							<td><form:input path="labRoonAbbreviation"/></td><%--这里并不是labRoonAbbreviation 只是为了放这个条件--%>
							<th style="padding-left:35px;">可预约工位数：</th>
							<td><select name="searchflg" id="searchflg" class="search_flg">
								<option value="">-请选择-</option>
								<option value="1" <c:if test="${'1' eq searchflg}">selected</c:if>>等于</option>
								<option value="2" <c:if test="${'2' eq searchflg}">selected</c:if>>大于</option>
								<option value="3" <c:if test="${'3' eq searchflg}">selected</c:if>>小于</option>
							</select>
							<input type="text" id="worker" name="worker" value="${worker}" style="float: none;width:auto;">
							</td>
							<td colspan="2" style="text-align:right;">
							    <input type="submit" value="搜索"/>
							    <input type="button" onclick="cancel()" value="取消"/>
							</td>
							</tr>
						</form:form>
					</table>
				</div>
				</div>
			</div>
			<div class="content-box">
				<table>
					<thead>
						<tr>
							<th>序号</th>
							<th><spring:message code="all.trainingRoom.labroom" />编号</th>
							<th><spring:message code="all.trainingRoom.labroom" />名称</th>
							<th><spring:message code="all.trainingRoom.labroom" />楼层</th>
							<th><spring:message code="all.trainingRoom.labroom" />等级</th>
							<%--<th><spring:message code="all.trainingRoom.labroom" />容量</th>--%>
							<th>可预约工位数</th>
							<th>设备详情</th>
							<th>软件详情</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listLabRoom}" var="s" varStatus="i">
							<tr >
								<td>${i.count+(currpage-1)*pageSize }</td>
								<td>${s.labRoomNumber}</td>
								<td>${s.labRoomName}</td>
								<td><c:if test="${s.floorNo ne null}">${s.systemBuild.buildName}(${s.floorNo}楼)</c:if>
									<c:if test="${s.floorNo eq null || s.floorNo eq ''}"></c:if></td>
								<c:if test="${s.labRoomLevel eq 0}">
									<td>特级</td>
								</c:if>
								<c:if test="${s.labRoomLevel eq 1}">
									<td>一级</td>
								</c:if>
								<c:if test="${s.labRoomLevel eq 2}">
									<td>二级</td>
								</c:if>
								<c:if test="${empty s.labRoomLevel}">
									<td>未设置</td>
								</c:if>
								<%--<td>${s.labRoomCapacity}</td>--%>
								<td>${s.labRoomWorker}</td>
								<td><a onclick="showLabRoomDevice(${s.id})" href="javascript:void(0)"  >设备详情</a></td>
								<td><a onclick="showLabRoomSoftware(${s.id})" href="javascript:void(0)"  >软件详情</a></td>
								<td>
									<c:if test="${!fn:contains('zjcclims',PROJECT_NAME)}"><!-- 非浙江建设项目 -->
										<a onclick="viewNow(${s.id})" href="javascript:void(0)">实况</a>
									</c:if>
									<c:if test="${fn:contains('zjcclims',PROJECT_NAME) && not empty s.labRoomLevel}">
										<c:if test="${s.labRoomLevel ne 0}">
											<a onclick="viewNow(${s.id})" href="javascript:void(0)">实况</a>
										</c:if>
										<c:if test="${s.labRoomLevel eq 0}">
											<font style="color: red">特级<spring:message code="all.trainingRoom.labroom" />不开放</font>
										</c:if>
									</c:if>
									<c:if test="${fn:contains('zjcclims',PROJECT_NAME) && empty s.labRoomLevel}">
									<font style="color: red">未设置<spring:message code="all.trainingRoom.labroom" />等级</font>
									</c:if>
									<c:if test="${fn:contains('zjcclims',PROJECT_NAME) && (
										sessionScope.selected_role eq 'ROLE_SUPERADMIN' ||
										sessionScope.selected_role eq 'ROLE_EXPERIMENTALTEACHING' ||
										sessionScope.selected_role eq 'ROLE_PREEXTEACHING' ||
										sessionScope.selected_role eq 'ROLE_EXCENTERDIRECTOR'
										)}">
										<a  href="javascript:void(0)" onclick="openSetupLink(${s.id},${currpage})" title="设置">设置</a>
									</c:if>
									<c:if test="${(s.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && s.CDictionaryByAllowSecurityAccess.CNumber == 1)}">

										<c:if test="${(
											sessionScope.selected_role eq 'ROLE_STUDENT' ||
											sessionScope.selected_role eq 'ROLE_TEACHER'
											) &&
											!(sessionScope.selected_role eq 'ROLE_SUPERADMIN') &&
											!(sessionScope.selected_role eq 'ROLE_EQUIPMENTADMIN')}">
										<a href="javascript:void(0)" onclick="viewLabRoomTrainingRest(${s.id})";>培训申请</a>
										</c:if>
									</c:if>
									<c:if test="${(s.CDictionaryByAllowSecurityAccess.CCategory =='c_active' && s.CDictionaryByAllowSecurityAccess.CNumber == 1 && s.CDictionaryByTrainType.CNumber == 3)}">
										<sec:authorize ifAnyGranted="ROLE_SUPERADMIN,ROLE_TEACHER,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN">
											<a href="javascript:void(0)" onclick="findTestList(${s.id})";>考试</a>
										</sec:authorize>
										<sec:authorize ifAnyGranted="ROLE_STUDENT">
											<sec:authorize ifNotGranted="ROLE_SUPERADMIN,ROLE_TEACHER,ROLE_EXPERIMENTALTEACHING,ROLE_PREEXTEACHING,ROLE_EXCENTERDIRECTOR,ROLE_LABMANAGER,ROLE_EQUIPMENTADMIN">
												<a href="javascript:void(0)" onclick="findTestList(${s.id})";>考试</a>
											</sec:authorize>
										</sec:authorize>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page">
					${totalRecords}条记录 &nbsp;   共${pageModel.totalPage}页 &nbsp; 
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=1')" target="_self">首页</a> 
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
					<input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp; <input type="hidden" value="${currpage}" id="currpage" /> 
					&nbsp;第 
					<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
					   <option value="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=${currpage}">${currpage}</option>
					   <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">	
			           <c:if test="${j.index!=currpage}">
			           <option value="${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=${j.index}">${j.index}</option>
			           </c:if>
			           </c:forEach>
           			</select>页&nbsp;
					 
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
					<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/LabRoomReservation/labRoomStationList?currpage=${pageModel.lastPage}')" target="_self">末页</a>
				</div>
	</div>
</div>
</div>
</div>
</div>
</div>
<!-- 弹出选择学生窗口 -->
<div id="newStudents" class="easyui-window" title="选择添加学生" modal="true" dosize="true" maximizable="true" collapsible="true" minimizable="false" closed="true" iconcls="icon-add" style="width:800px; height:600px;">
	<div class="TabbedPanelsContentGroup">
	<div class="TabbedPanelsContent">
	
	<div class="content-box">
	<form:form action="" method="post">
	<fieldset class="introduce-box">
         <legend>年级信息</legend>
         <div>
         <table id="listTable" width="85%" cellpadding="0">
          <tr><td><b>选择年级</b></td><tr>
          <tr>
         	<td>
         	<c:forEach items="${grade }" var="s" varStatus="i">
         	<c:if test="${s.yearCode>2010 }">
			 <a class='btn btn-common' href='javascript:void(0)' onclick='getSchoolClasses(${s.yearCode})' >${s.yearCode}</a>
			</c:if>
			</c:forEach></td>
         </tr>
         </table>
         </div>
         <div id="schoolClasses"></div>
         <div id="schoolClassesUser"></div>
	</fieldset>
	</form:form>
	</div>
	</div>
	</div>
	
</div><div id="check_user" class="easyui-window" closed="true" modal="true" minimizable="true" title="指定审核导师" style="width: 580px;height: 350px;padding: 20px">
	<div class="content-box">
		<table>
			<tr>
				<td>选择导师</td>
				<td>
					<select name="teacher" id="teacher" class="chzn-select">
						<option value="">请选择</option>
						<c:forEach items="${teacherList}" var="u">
							<option value="${u.username}">[${u.username}]${u.cname}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		<div class="moudle_footer">
			<div class="submit_link">
				<input class="btn" id="save" type="button" onclick="submitForAudit();" value="提交">
			</div>
		</div>
	</div>
</div>
		<!-- 下拉框的js -->

		<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"  type="text/javascript"></script>

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
						  //弹出选择学生窗口
						    function newStudents() {
						        var sessionId=$("#sessionId").val();
						        //获取当前屏幕的绝对位置
						        var topPos = window.pageYOffset;
						        //使得弹出框在屏幕顶端可见
						        $('#newStudents').window({left:"0px", top:topPos+"px"}); 
						        $("#newStudents").window('open');
						    }
						  //ajax查询班级用户列表  
						    function getSchoolClassesUser(classNumber){ 		
						    	$.ajax({
						    		type: "POST",
						    		url: "${pageContext.request.contextPath}/timetable/selfTimetable/getSchoolClassesUser",
						    		data: {'classNumber':classNumber},
						    		dataType:'json',
						    		success:function(data){
						    			var jslength=1;
						    			var currLine=1;
						    			var allLine=1;
						    			for(var js2 in data){jslength++;}
						    			if(jslength==0){alert("本周无课程数据");}else{}

						    			var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td><b>选择学生</b></td><td colspan=3><input class='btn btn-primary btn-lg' type='button' onclick='putSchoolClassesUser()' value='提交'/></td></tr>";//新建html字符
						    			$.each(data,function(key,values){  
						    			   if(currLine%4==0){
						    		           tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td><tr>";
						    			   }else  if(currLine%4==1){
						    			       tableStr = tableStr + "<tr><td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
						    			   }else  if(currLine%4==2){
						    			       tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
						    			   }else if(currLine%4==3){
						    			       tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
						    			   }
						    			   //currLine=currLine%4;
						    			   jslength=jslength+1;
						    			   currLine = currLine +1;
						    			   allLine =allLine+1;
						    			 }); 
						    			 if(currLine%4==0){
						    			   tableStr = tableStr + "</table>";
						    			 }else if(currLine%4==1){
						    			   tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td><td&nbsp;></td></tr></table>";
						    			 }else if(currLine%4==2){
						    			   tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td></tr></table>";
						    			 }else if(currLine%4==3){
						    			   tableStr = tableStr + "<td>&nbsp;</td></tr></table>";
						    			 }
						    			
						    			 document.getElementById('schoolClassesUser').innerHTML=tableStr; 	
						    		},
						    		error:function(){
						    			//alert("加载课表失败!");
						    			}
						    	});
						    }
						    var audit = "";
						  //保存实训室预约
						    function saveLabRoomReservation(){
						    	//console.log($("input[name='reservationtime']") .val());
						    	var dean = "${dean}";
						    	var dean1 = $('#dean option:selected').val();
						    	/* if(flag == 1 ){
						    		 alert("正在保存预约，请勿重复提交！");
						    		 return false;
						    	 } */
						    	//更改标志位
						    	flag = 1;
//						    	 if($("input[name='reservationtime']") .val() == ""){
//						    		 alert("请选择日期");
//						    		 flag = 0;
//						    		 return false;
//						    	 }
//						    	 if($("#starttime").val()){
//						    	 }else{
//						    		 alert("请选择开始时间");
//						    		 flag = 0;
//						    		 return false;
//						    	 }
//						    	 if($("#endtime").val()){
//						    	 }else{
//						    		 alert("请选择结束时间");
//						    		 flag = 0;
//						    		 return false;
//						    	 }
                                if ($("input[name='lendingTime']").val() == "") {
                                    alert("请选择日期");
                                    return false;
                                }
                                if ($("#reservationTime").val()) {
                                } else {
                                    alert("请选择开始时间");
                                    return false;
                                }
						    	 var obj = document.getElementById("usingObj").value;
						    	 if(obj == 1){
						    	 	if($("#students").val() == ""){
						    		 	alert("学生不可为空");
						    		 	flag = 0;
						    		 	return false;
						    	 	}
						    	 }
						    	 if($("#starttime").val() >= $("#endtime").val()){
						    		 alert("开始时间不可大于等于结束时间");
						    		 flag = 0;
						    		 return false;
						    	 }

						    	 var myData = {
//						    		 'reservationTime':$("input[name='reservationtime']") .val(),
                                     'reservationTime': $("#reservationTime").val(),
                                     'lendingTime': $("input[name='lendingTime']").val(),
						    		 'startTime':$("#starttime").val(),
						    		 'endTime':$("#endtime").val(),
						    		 'reason':$("#reason").val(),
						    		 'students':$("#students").val(),
						    		 'userRole':$("#usingObj").val(),
						    		 //'dean':dean1
									 'teacher':$("#teacher").val()
						    	 }
						    	 var labRoomId = document.getElementById("labRoom").value;
						    	 $.ajax({
						    		type: "POST",
						    		url: "${pageContext.request.contextPath}/LabRoomReservation/saveLabRoomReservationTeacher?labRoomId="+labRoomId,
						    		data: myData,
						    		dataType:'text',
						    		success:function(data){
						    			console.log(data);
						    			if(data=="over"){
						    				alert("预约失败，您选的时间段工位数不够！");
						    			}else if(data=="overMax"){
                                            alert("预约失败，超过系统设置的最大可预约数！");
                                        }else if(data=="lent"){
                                            alert("预约失败，实验室已被借用");
                                        }else if(data=="reserved"){
                                            alert("预约失败，实验室已被预约");
                                        }else if(data=="success"){
						    				alert("预约信息已提交等待审核…");
						    				flag = 0;
						    				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						                    parent.layer.close(index);//关闭弹窗
											window.location.reload();
						    			}else{
                                            layer.msg('预约失败！'+data+'不是合法学号', {icon: 5});
                                            }
                                            flag = 0;
						    			//else if(data=="success2"){
						    			// 	alert("预约成功，二级实训室无需审核");
						    			// 	flag = 0;
						    			// 	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						                 //    parent.layer.close(index);//关闭弹窗
                                         //    window.location.reload();
						    			// }else if(data=="noAudit1"){
											// alert("预约成功，一级实训室无需审核");
											// flag = 0;
											// var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
											// parent.layer.close(index);//关闭弹窗
											// window.location.reload();
                                        // }else if(data=="noAudit2"){
											// alert("预约成功，二级实训室无需审核");
											// flag = 0;
											// var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
											// parent.layer.close(index);//关闭弹窗
											// window.location.reload();
                                        // }else{
						    			// 	layer.msg('预约失败！'+data+'不是合法学号', {icon: 5});
						    			// }
						    			//flag = 0;
						    		},
						    		error:function(){
						      			alert("预约未成功！请重试！");
						      			flag = 0;
						      		}
						     	})
						    }
						    var needTutor = 0;
						    function selectLabRoom() {
                                if($("#labRoom").val() == "请选择"){
                                    return;
                                }
                                var labRoomId = parseInt($("#labRoom").val());
                                $.ajax({
                                    type: "POST",
                                    url: "../labRoom/securityAccess?id="+labRoomId,
                                    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
									data:{
                                    	requestType: "labRoomStation"
									},
                                    dataType:'text',
                                    async:false,
                                    success: function (data) {
                                        if (data == "error") {
                                            alert("您还未通过培训,请先预约培训!");
                                            window.location.reload();
                                        } else if (data == "errorType2") {
                                            alert("您还未通过单独培训!");
                                            window.location.reload();
                                        } else if(data=="noSetting"){
                                            alert("设备未进行初始化设置，不可预约！");
                                            window.location.reload();
                                        } else if (data == "needTutor"){
                                            needtutor = 1;
                                        } else if (data == "noNeedTutor"){
                                            needtutor = 0;
                                        }else if (data == "noReservation") {
                                            alert("此实验室已被设为不可预约!");
                                            window.location.reload();
                                        }else if (data === "noTrainingError") {
											alert("您未通过此实验室的培训，请先预约培训!");
											window.location.reload();
										}else if(data=="noAudit1"){
											audit = data;
											needtutor = 0;
										}else if(data=="noAudit2"){
											audit = data;
											needtutor = 0;
										}
                                    },
                                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                                        alert("请求错误");
                                    }
                                })
                            }

                            function submitForAudit(){
                                if($("#teacher").val()==""){
                                    alert("请选择审核导师！");
                                }else{
                                    teacher = $("#teacher").val();
                                    saveLabRoomReservation();
                                }
                            }
                            function openChooseTeacher() {
                                if(needtutor == 1) {
                                    $("#check_user").show();
                                    $("#check_user").window('open');
                                }else{
                                    saveLabRoomReservation();
                                }
                            }
						    
						   
						</script>
	</div></div>