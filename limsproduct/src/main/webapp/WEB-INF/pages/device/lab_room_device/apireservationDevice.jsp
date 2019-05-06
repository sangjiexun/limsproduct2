<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<head>
<meta name="decorator" content="ijquery"/>

<script src="${pageContext.request.contextPath}/js/dhtmlx/dhtmlxscheduler.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/dhtmlx/ext/dhtmlxscheduler_dhx_terrace.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/dhtmlx/ext/dhtmlxscheduler_year_view.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/dhtmlx/ext/dhtmlxscheduler_minical.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/dhtmlx/ext/dhtmlxscheduler_editors.js" type="text/javascript" charset="utf-8"></script>
   <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结�--> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/dhtmlxcombo.css">
<script src="${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/dhtmlxcombo.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/dhtmlx/dhtmlxscheduler.css" type="text/css" title="no title" charset="utf-8">
<style type="text/css" media="screen"> 
   .panel,.window,.messager-window{
   	position:absolute;
   	background:#fff;
   	border-radius:3px;
   	overflow:hidden; 
   	    box-shadow: 0px 0px 5px 0px #CECECE;
   }
   .messager-body,.panel-body,.panel-body-noborder,.window-body{
   	padding:8px 5px;
   	
   	line-height:20px;
   	
   }
   .messager-body span,.panel-body span,.panel-body-noborder span,.window-body span{font-size:12px !important;}
   .panel-title{
   	background:#eee;
   	line-height:30px;
   	padding:0 5px;
   	font-weight:bold;
   }
   .l-btn{width:100px;
   text-align:center;
   display:block;
   margin:10px auto 0px auto !important;
   height: 25px;
       line-height: 25px;
    background: #77bace;
    border-radius: 3px;
    color: #FFF;
    text-decoration: none;
    outline: none;
    font-family: arial;
   }
   
</style>
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
<script type="text/javascript">
    var contextPath = $("meta[name='contextPath']").attr("content");
    var zuulUrl = "${zuulServerUrl}" + "${pageContext.request.contextPath}" + "/timetable/";
	$(document).ready(function init() {

	    scheduler.ignore_week = function(weekday){// 隐藏掉周�-按周显示�
	         if (weekday.getDay() == 6 || weekday.getDay() == 0)
	             return true;
	     };
	    scheduler.ignore_month = function(weekday){// 隐藏掉周�-按月显示�
	         if (weekday.getDay() == 6 || weekday.getDay() == 0)
	             return true;
	     };
	});
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //�
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //�
	        "s+": this.getSeconds(), //�
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}

	var equinemtid=${id};

	// 判断是否是纺织学�
	var isFZ = ${isFZ};

	var year=${year};//�
	var month=${month};//�
	var day=${day};//�
	var hour=${hour};//小时
	var minute=${minute};//分钟
	var second=${second};//�
	var first_hour = 0;
	var last_hour = 24;

    //var openInweekend = ${openInweekend};    设备所属实验室周末开放情况

	var applyUserName = '${applyUserName}';
	var project = '${project}';
	var auditServerUrl = '${auditServerUrl}';
	var t=${teachers};//导师
	//var d=${deans};//系主任---------更改系主任级的筛选方式时隐掉
	//var propertys=${propertys};//申请性质
	var a=1028;
	var r=${researchs};
	$(function(){
		
		/*if(isFZ){
			first_hour=8;//插件开始时�
			last_hour=17;//插件开始时�
			$("#name_tip").html("此设备的预约时间范围段是8:15-16:30")
		}else{
			first_hour=8;//插件开始时�
			last_hour=20;//插件开始时�
			//$("#name_tip").html("此设备的预约时间范围段是8:30-20:15")
		}*/
		
		$.messager.alert("提示","<span style='font-size: 14px; color: red'>在你需要预约的日期上拖动一个时间块区,就是您所要预约的时间!<br>添加之后不能进行修改，请谨慎操作！</span>" );
		scheduler.locale.labels.section_type = "项目名称";
		if("${isStudent}" == "0"){
			scheduler.config.lightbox.sections = [	 
      			{name:"phone", height:24, map_to:"phone", type:"textarea"},
      			//{name:"teacher", height:21, map_to:"teacher", type:"select",options:t},
      			/*{name:"research", height:21, map_to:"research", type:"select",options:r},*/
      			//{name:"research",options:r, map_to:"research", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
      			{name:"teacher",options:t, map_to:"teacher", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
      			//{name:"dean",options:t, map_to:"dean", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
      			//{name:"property",options:propertys, map_to:"property", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
      			/* {name:"property", height:21, map_to:"property", type:"select",  options:propertys}, */
      			{name:"time",  height:72, type:"time",   map_to:"auto"},
      			{name:"description", height:150, map_to:"description", type:"textarea" , focus:true}
      		];
		}else{
			scheduler.config.lightbox.sections = [	 
       			{name:"phone", height:24, map_to:"phone", type:"textarea"},
       			//{name:"teacher", height:21, map_to:"teacher", type:"select",options:t},
       			/*{name:"research", height:21, map_to:"research", type:"select",options:r},*/
       			//{name:"research",options:r, map_to:"research", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
                {name:"teacher",options:t, map_to:"teacher", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
       			//{name:"dean",options:t, map_to:"dean", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
       			//{name:"property",options:propertys, map_to:"property", type:"combo",image_path: "${pageContext.request.contextPath}/js/dhtmlx/dhtmlxCombo/imgs/", height:21, filtering: true },
       			
       			/* {name:"property", height:21, map_to:"property", type:"select",  options:propertys}, */
       			{name:"time",  height:72, type:"time",   map_to:"auto"},
       			{name:"description", height:150, map_to:"description", type:"textarea" , focus:true}
       		];
		}
		
		
		scheduler.config.prevent_cache = false;
		scheduler.config.first_hour=${startHour};//插件开始时�
		scheduler.config.last_hour=${endHour};//插件开始时�
		scheduler.config.limit_time_select=true;
		scheduler.config.time_step=5;//间隔时间（单位：分）
		scheduler.config.api_date="%Y-%m-%d %H:%i";
		scheduler.config.xml_date="%Y-%m-%d %H:%i";
		scheduler.config.hour_date="%H:%i";
		scheduler.config.default_date="%Y年%m月%d日";
		scheduler.config.month_date="%Y年 %m月";
		scheduler.config.day_date="%D %m月%d日";
		scheduler.config.start_on_monday=true;//星期一开�
		scheduler.config.multi_day = false;
		scheduler.config.separate_short_events = true;
		scheduler.config.positive_closing = false;
		scheduler.config.drag_resize = false;
		scheduler.config.drag_move = false;
		scheduler.config.details_on_create = true;
		scheduler.config.positive_closing = true;
		scheduler.config.details_on_dblclick = false; 
		scheduler.config.show_loading = true;
		scheduler.init('scheduler_here',new Date(year,month,day),"week");
		var convert = scheduler.date.date_to_str("%Y-%m-%d %H:%i:%s");
		var dateConvert = scheduler.date.date_to_str('%Y-%m-%d');
		//载入文件

		scheduler.load("${pageContext.request.contextPath}/device/getLabReservation?id="+equinemtid,"json");  
		scheduler.load("${pageContext.request.contextPath}/device/getLimitLabReservation?id="+equinemtid,"json");
		scheduler.attachEvent("onEventCancel", function(event_id, is_new_event){
		    scheduler.deleteEvent(event_id);
		});
		scheduler.attachEvent("onDblClick", function (event_id, native_event_object){
		    return false;
		});
		scheduler.attachEvent("onClick", function (event_id, native_event_object){
		    return false;
		});
		
		
		scheduler.attachEvent("onEventCreated", function(id,e){
			var sevent = scheduler.getEvent(id)
			sevent.phone="${user.telephone}";
		});
		

		scheduler.attachEvent("onLightbox", function (id){
			$(".dhx_cal_lsection").each(function(i){
		    	if(i<=2){
		    		$(this).css("color","black")
				}
			})
		});
        scheduler.attachEvent("onEventSave", function (event_id, event_object) {
            //申请理由
            var description=event_object.description;
            //联系电话
            var phone=event_object.phone;
            //alert(event_id);
            //导师
            var teacher=event_object.teacher;
            //系主任-----------------更改系主任级的筛选方式时隐掉
            //var dean=event_object.dean;
            if(!description){
                alert("请填写备注！");
                return false;
            }
            if(!phone){
                alert("请填写联系电话！");
                return false;
            }
            //学生身份且设置需要导师审核时，需选择导师
            if("${isStudent}" == "0"){
                if(!teacher){
                    alert("请选择导师！");
                    return false;
                }
            }
//            else{
//                teacher = "-1";
//            }
            return true;
        });

		//以下代码为绑定事件：增加
		scheduler.attachEvent("onEventAdded", function(event_id,event_object){
			
		    var testss = event_object.text;
		   	// var type=event_object.type;
		   	//获取的是event_object的map_to
		    var typea=event_object.typea;
		    var typef=event_object.typef;
		    
		    //申请理由
		    var description=event_object.description;
		    //联系电话
		    var phone=event_object.phone;
		    //alert(event_id);
		    //导师
		    var teacher=event_object.teacher;
		    //系主任-----------------更改系主任级的筛选方式时隐掉
		    //var dean=event_object.dean;
			if(!description){
			    alert("请填写备注！");
			}
			if(!phone){
			    alert("请填写联系电话！");
			}
		    //学生身份且设置需要导师审核时，需选择导师
		    if("${isStudent}" == "0"){
		    	if(!teacher){
		    		alert("请选择导师！");
		    	}
		    }
//		    else{
//		    	teacher = "-1";
//		    }
		    /*设置需要系主任审核时，需选择系主任-----------更改系主任级的筛选方式时隐掉
		    if("${device.dean}" == "1"){
		    	if(!dean){
		    		alert("请选择系主任！");
		    		return false;
		    	}
		    }else{
		    	dean = "-1";
		    }*/
		    //申请性质
		    var property=event_object.property;
		    
		    //课题组
		    //var research = event_object.research;
		    var research = "";
		    //开始时间
		     var startdate = convert(event_object.start_date);
		  	//结束时间
		    var enddate = convert(event_object.end_date);
		    
		    var date = dateConvert(event_object.end_date);
		    var sdate = dateConvert(event_object.start_date);
		   
		    var nowdate=dateConvert(new Date(year,month,day,hour,minute,second));    
		    if(research == null){
		    	$.messager.alert("提示","<span style='font-size: 14px; color: red'>课题组不可为空</span>" );
		    	scheduler.deleteEvent(event_id);
		    }
		    else{
			if(event_object.start_date.Format("yyyy-MM-dd hh:mm")<new Date(year,month,day,hour,minute,second).Format("yyyy-MM-dd hh:mm")){
			  	$.messager.alert("提示","<span style='font-size: 14px; color: red'>此时间已过时�/span>" );
			   scheduler.deleteEvent(event_id);
			}else{
			    var data = {equinemtid:equinemtid,startDate:startdate,endDate:enddate,description:description,phone:phone,teacher:teacher,property:property,research:research,applyUserName:applyUserName,project:project,auditServerUrl:auditServerUrl};
			    var jsonData = JSON.stringify(data)
				console.log(jsonData);
                $.ajax({
                    url: zuulUrl + "api/labdevice/apiSaveDeviceReservation",
                    type: "POST",
                    data: jsonData,
//                    headers: {Authorization: getJWTAuthority()},
                    async: false,
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if(data == 'success') {
                            if (${isStudent eq '0'&&device.isAuditTimeLimit!=null&&device.isAuditTimeLimit==1&&device.auditTimeLimit!=null &&device.auditTimeLimit!=''}) {
                                var alertString = ${device.auditTimeLimit}+"";
                                alert("预约已提交，需导师在预约后"+alertString+"小时之内审核，若超过时间未审核，则预约失效，需要重新预约�");
                            }
                            $.messager.show({
                                title: '消息',
                                msg: "<span style='font-size: 14px'>设备预约成功!<br>页面正在跳转请稍�...</span>",
                                showType: 'fade',
                                timeout: 2000
                            });
                            window.setTimeout('window.location.reload()',1000);

                        }
                        if(data == 'LIMIT'){
                            $.messager.alert('错误','你预约的时间在实训室禁用期间!','error',function () {
                                window.location.reload();
                            });
                            scheduler.deleteEvent(event_id);
                            //$.post('delReg',{regstamp: regstamp});
                        }
                        if(data == 'error'){
                            $.messager.alert('错误','你预约的时间和其他预约存在时间冲突或者您的预约时间小于当前时!','error',function () {
                                window.location.reload();
                            });
                            scheduler.deleteEvent(event_id);
                            //$.post('delReg',{regstamp: regstamp});
                        }
                        //window.location.href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=2&currpage=1&isaudit=2";
                    }
                });
				<%--$.post('${pageContext.request.contextPath}/device/saveDeviceReservation',function(data){--%>
			        <%--if(data == 'success') {--%>
			        	<%--if (${isStudent eq '0'&&device.isAuditTimeLimit!=null&&device.isAuditTimeLimit==1&&device.auditTimeLimit!=null &&device.auditTimeLimit!=''}) {--%>
			        		<%--var alertString = ${device.auditTimeLimit}+"";--%>
							<%--alert("预约已提交，需导师在预约后"+alertString+"小时之内审核，若超过时间未审核，则预约失效，需要重新预约�");--%>
						<%--}--%>
			            <%--$.messager.show({--%>
			                <%--title: '消息',--%>
			                <%--msg: "<span style='font-size: 14px'>设备预约成功!<br>页面正在跳转请稍�...</span>",--%>
			                <%--showType: 'fade',--%>
			                <%--timeout: 2000--%>
			            <%--});--%>
			            <%--window.setTimeout('window.location.reload()',1000);--%>
			            <%----%>
			        <%--}--%>
			        <%--if(data == 'LIMIT'){--%>
			            <%--$.messager.alert('错误','你预约的时间在实训室禁用期间!','error');--%>
			            <%--scheduler.deleteEvent(event_id);--%>
			            <%--$.post('delReg',{regstamp: regstamp});--%>
			        <%--}--%>
			        <%--if(data == 'error'){--%>
			            <%--$.messager.alert('错误','你预约的时间和其他预约存在时间冲突或者您的预约时间小于当前时!','error');--%>
			            <%--scheduler.deleteEvent(event_id);--%>
			            <%--$.post('delReg',{regstamp: regstamp});--%>
			        <%--}--%>
			        <%--window.location.href="${pageContext.request.contextPath}/LabRoomDeviceReservation/labRoomDeviceReservationList?tage=2&currpage=1&isaudit=2";--%>
			    <%--});--%>
			}
			}
		});
	    scheduler.attachEvent('onEventDeleted',function(event_id,event_object){
	        $.post('delReg',{regstamp: event_id}); 
	    });
	 	//如果日期是周六、周日则格子成黄�
		scheduler.templates.scalex_class = function(date){
			if (date.getDay()==0 || date.getDay()==6)  return "yellow_cell";
			return "";
		} 
	  
		scheduler.templates.month_date_class = function(date, today){  
		    var time = new Date(date).getTime();  
		    var from = time;  
		    var to = time + 86400000;  
		    //插件中此方法不兼容ie 要修复一�修复代码如下  
		     
		    scheduler.getEvents = function(from, to) { 
		        var result = []; 
		        for (var a in this._events) { 
		            try{ 
		                var ev = this._events[a];
		                var start = new Date(ev.start_date).getTime(); 
		                var end = new Date(ev.end_date).getTime(); 
		                if((start < to) && (end > from)){ 
		                    result.push(ev); 
		                } 
		            }catch(e){} 
		            //原来代码 
		            //if (ev && ( (!from && !to) || (ev.start_date < to && ev.end_date > from) )) 
		                //result.push(ev); 
		        } 
		        return result; 
		    }; 
		}
	});
       
        
    
</script>
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
</head>
