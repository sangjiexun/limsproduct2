/**
 * Created by ylj on 2019/4/17.
 */
var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
var parentProject;
// function showDetail(initjson) {
layui.use(['form'], function() {
    var form = layui.form;
    var layer = layui.layer;
    var labRId = $('#labRoomId').val();
    var username = $('#username').val();
    var auditServerUrl = $('#auditServerUrl').val();
    var projectName = $('#auditServerUrl').val();
    // console.log(parent.layer.methodConfig);
    // console.log(initjson.parentProject);
    var daata = {username:username,labRId:labRId,auditServerUrl:auditServerUrl,projectName:projectName}
    var jsonData = JSON.stringify(daata)
    $.ajax({
       url: zuulUrl  + "api/labReservation/apiLabRoomReservationAudit",
       data: jsonData,
       async: false,
       type: "POST",
       contentType: "application/json;charset=UTF-8",
    	success:function (res) {
           //父项目详情表单初始赋值
            form.val('labReservationDetail', {
                "labRoomName": res.labRoomName,
                "cname": res.cname,
                "reservationDate": res.reservationDate,
                "startTime": res.startTime+"-"+res.endTime,
                "eventType": res.eventType,
                "userObjectName": res.userObjectName,
                "number": res.number,
                "telephone": res.telephone,
                "reason": res.reason,
            });
       },
    	error:function () {
               alert("后台出了点问题，请重试！");
               return false;
       }
    })

});
// }
