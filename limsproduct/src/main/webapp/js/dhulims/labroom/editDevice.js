//返回到设备列表页面
function back(){
	var page = $("#page").val();
	var url = $("#pageContext").val()+"/LabRoomReservation/labRoomList?currpage="+page;
	//alert(url);
	window.location.href=url;
}
//准入管理
function editLabRoomTrainingRest(labRoomId,currpage,type){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
	var page = $("#page").val();
	var url = $("#pageContext").val()+"/labRoomSetting/editLabRoomTrainingRest/" + labRoomId + "/"+page+"/"+type;
	//alert(url);
	window.location.href=url;
}
//开放设置
function editLabRoomOpenSettingRest(id,currpage,type){//将labRoomId deviceNumber deviceName page传递到后台

    var page = $("#page").val();
    var username = $("#username").val();
    var url = $("#pageContext").val()+"/device/editLabRoomOpenSettingRest/" + id+ "/"+page+"/"+type;
    //alert(url);
    window.location.href=url;
}
//实验室预约设置
function openSetupLink(labRoomId,currpage,type){//将labRoomId page传递到后台
	var labRoomId = $("#labRoomId").val();
	var url =$("#pageContext").val()+"/device/editLabRoomSettingRest/"+labRoomId+"/"+currpage+"/"+type;
	//alert(url);
	window.location.href=url;
}
//工位预约设置
function openStationReserSetting(labRoomId,currpage,type){//将labRoomId page传递到后台
	var labRoomId = $("#labRoomId").val();
	var url =$("#pageContext").val()+"/device/editLabRoomStationReserSetting/"+labRoomId+"/"+currpage+"/"+type;
	//alert(url);
	window.location.href=url;
}
//准入管理
function editLabRoomSecurityAccess(labRoomId){//将labRoomId传递到后台
    var labRoomId = $("#labRoomId").val();
    var url =$("#pageContext").val()+"/device/editLabRoomSecurityAccess/"+labRoomId;
    //alert(url);
    window.location.href=url;
}
//相关图片
function editLabRoomImageRest(labRoomId,currpage,type){//将labRoomId deviceNumber deviceName page传递到后台
	var url = $("#pageContext").val()+"/labRoomSetting/editLabRoomImageRest/" + labRoomId + "/"+currpage+"/"+type;
	//alert(url);
	window.location.href=url;
}
//相关视频
function editLabRoomVideoRest(labRoomId,currpage,type){//将labRoomId deviceNumber deviceName page传递到后台
	var url = $("#pageContext").val()+"/labRoomSetting/editLabRoomVideoRest/" + labRoomId + "/"+currpage+"/"+type;
	//alert(url);
	window.location.href=url;
}
//相关文档
function editLabRoomDocumentRest(labRoomId,currpage,type){//将labRoomId deviceNumber deviceName page传递到后台
	var url = $("#pageContext").val()+"/labRoomSetting/editLabRoomDocumentRest/" + labRoomId + "/"+currpage+"/"+type;
	//alert(url);
	window.location.href=url;
}
//注意事项
function editDeviceAttentionRest(id){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
	var deviceName = $("#deviceName").val();
	var deviceNumber = $("#deviceNumber").val();
	var username = $("#username").val();
	var page = $("#page").val();
	var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	var url = $("#pageContext").val()+"/device/editDeviceAttentionRest/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+"/" + id+"/"+schoolDevice_allowAppointment;
	window.location.href=url;
}

//保存注意事项
function saveDeviceAttentionRest(id){
	
	var labRoomId = $("#labRoomId").val();
	var deviceName = $("#deviceName").val();
	var deviceNumber = $("#deviceNumber").val();
	var username = $("#username").val();
	var page = $("#page").val();
	var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	console.log(page);
    var deviceAttention = $("#applicationAttentions").val();
    if($("#applicationAttentions").val()==""){
    	deviceAttention="-1";
    }
    
	//特殊字符
	//1. +  URL 中+号表示空格 %2B
	//2. 空格 URL中的空格可以用+号或者编码 %20
	//3. /  分隔目录和子目录 %2F 
	//4. ?  分隔实际的 URL 和参数 %3F 
	//5. % 指定特殊字符 %25 
	//6. # 表示书签 %23 
	//7. & URL 中指定的参数间的分隔符 %26 
	//8. = URL 中指定参数的值 %3D 
    deviceAttention=deviceAttention.replace(/\+/g,"[Geng1Shang]");//将特殊字符"+"转化为[Geng1Shang]
    deviceAttention=deviceAttention.replace(/\ /g,"[Geng2Shang]");//将特殊字符" "转化为[Geng2Shang]
    deviceAttention=deviceAttention.replace(/\//g,"[Geng3Shang]");//将特殊字符"/"转化为[Geng3Shang]
    deviceAttention=deviceAttention.replace(/\?/g,"[Geng4Shang]");//将特殊字符"?"转化为[Geng4Shang]
    deviceAttention=deviceAttention.replace(/\%/g,"[Geng5Shang]");//将特殊字符"%"转化为[Geng5Shang]
    deviceAttention=deviceAttention.replace(/\#/g,"[Geng6Shang]");//将特殊字符"#"转化为[Geng6Shang]
    deviceAttention=deviceAttention.replace(/\&/g,"[Geng7Shang]");//将特殊字符"&"转化为[Geng7Shang]
    deviceAttention=deviceAttention.replace(/\=/g,"[Geng8Shang]");//将特殊字符"="转化为[Geng8Shang]
	var url = $("#pageContext").val()+"/device/saveDeviceAttentionRest/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+
	"/"+deviceAttention+"/"+ id+"/"+schoolDevice_allowAppointment;
	window.location.href=url;
}

//删除文件（图片、文档）
function deleteDeviceDocumentRest(id){//将labRoomId deviceNumber deviceName page传递到后台
	var currpage = $("#page").val();
	var type=$("#type").val();
	var url = $("#pageContext").val()+"/device/deleteDeviceDocumentRest/" + id + "/"+currpage+"/"+type;
	window.location.href=url;
}

//删除文件（视频）
function deleteLabRoomVideoRest(id){//将labRoomId deviceNumber deviceName page传递到后台
	var currpage = $("#page").val();
	var type=$("#type").val();
	var url = $("#pageContext").val()+"/device/deleteLabRoomVideoRest/" + id + "/"+currpage+"/"+type;
	//alert(url);
	window.location.href=url;
}


//针对培训预约

//根据设备id查询培训
function findTrainingPeopleByTrainIdRest(id,toChangeAudit){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
	var deviceName = $("#deviceName").val();
	var deviceNumber = $("#deviceNumber").val();
	var username = $("#username").val();
	var page = $("#page").val();
	var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	var url = $("#pageContext").val()+"/device/findTrainingPeopleByTrainIdRest/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/" +username + "/"+page+"/" + id+"/"+1 +"/"+toChangeAudit+"/"+schoolDevice_allowAppointment;
	window.location.href=url;
}

//保存实验设备培训人
function joinTrainingRest(id){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
	var deviceName = $("#deviceName").val();
	var deviceNumber = $("#deviceNumber").val();
	var username = $("#username").val();
	var page = $("#page").val();
	var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
 var url = $("#pageContext").val()+"/device/joinTrainingRest/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+"/" + id+"/"+schoolDevice_allowAppointment;
 //alert(url);
 window.location.href=url;
}

//删除培训
function deleteTrainingRest(id){
	var labRoomId = $("#labRoomId").val();
	var page = $("#page").val();
	var type = $("#type").val();
 var url = $("#pageContext").val()+"/labRoomSetting/deleteTrainingRest/" + labRoomId + "/"+ page + "/" + type +"/"+id;
 //alert(url);
 window.location.href=url;
}

//取消培训
function cancelTrainingRest(id){
	$.ajax({
        url:$("#pageContext").val()+"/labRoomSetting/cancelTraining?id="+id,
        type:"POST",
        success:function(data){//AJAX查询成功
        		if(data=="success"){
        			window.history.go(0);
        		}   
        }
	});
}

var trainId=0;
function moveId(id){
	trainId = id;
}

//更新选课时间
function info(){
	var begintime=$("#begintime").val();
	if(begintime==null||begintime==''){
		alert("请选择时间");
	}else{
		$.ajax({
		    url:$("#pageContext").val()+"/labRoomSetting/altTrainTime?id="+trainId,
		    type:"POST",
		    data: {'begintime':begintime},
		       success:function(data){//AJAX查询成功
		            if(data == 'success'){
		                $(".edit-edit").slideUp();
		                $(".btn-edit").slideDown();//修改信息显示
		                window.location.reload();//刷新页面
		            }else{
		                alert("未能修改成功，请稍后重试");
		            }
		       }
		});
	}
}

//保存培训信息
function saveLabRoomDeviceTrainingRest(id){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
	var deviceName = $("#deviceName").val();
	var deviceNumber = $("#deviceNumber").val();
	var username = $("#username").val();
	var page = $("#page").val();
	var time = $("#time").val();
	var address = $("#address").val();
	var content = $("#content").val();
	var usernameTeacher = $("#usernameTeacher").val();
	var number = $("#number").val();
	var schoolDevice_allowAppointment = $("#schoolDevice_allowAppointment").val();
	var url = $("#pageContext").val()+"/device/saveLabRoomDeviceTrainingRest/" + labRoomId + "/"+ deviceNumber + "/" + deviceName + "/"+username+"/"+page+ "/"+ time
	+ "/"+ address+ "/"+ content+ "/"+ usernameTeacher+ "/"+ number+"/" + id+"/"+schoolDevice_allowAppointment;
	window.location.href=url;
}
//题库
function findQuestionList(id){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
	var deviceName = $("#deviceName").val();
	var deviceNumber = $("#deviceNumber").val();
	var username = $("#username").val();
	//var username = '-1';
	var page = $("#page").val();
	var url = $("#pageContext").val()+"/tcoursesite/question/findQuestionList/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+"/" +id;
	window.location.href=url;
}
//考试
function findLabRoomTestList(deviceId){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
//	var deviceName = $("#deviceName").val();
//	var deviceNumber = $("#deviceNumber").val();
//	var username = $("#username").val();
	//var username = '-1';
	var page = $("#page").val();
	var status = -1;
//	var url = $("#pageContext").val()+"/teaching/test/labRoomTestList/" + labRoomId + "/"+ deviceNumber + "/" + deviceName +"/"+username+ "/"+page+"/" + deviceId+"/"+status;
	var url = $("#pageContext").val()+"/teaching/test/labRoomTestList/" + labRoomId + "/"+page+"/"+status;
	window.location.href=url;
}
//题库
function findLabRoomQuestionList(id){//将labRoomId deviceNumber deviceName page传递到后台
	var labRoomId = $("#labRoomId").val();
//	var deviceName = $("#deviceName").val();
//	var deviceNumber = $("#deviceNumber").val();
//	var username = $("#username").val();
	//var username = '-1';
	var page = $("#page").val();
	var url = $("#pageContext").val()+"/tcoursesite/question/findLabRoomQuestionList/"+page+"/" +id;
	window.location.href=url;
}
