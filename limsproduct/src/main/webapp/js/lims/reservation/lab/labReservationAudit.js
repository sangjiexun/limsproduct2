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
    var labRId = $('#labRId').val();
    // console.log(parent.layer.methodConfig);
    // console.log(initjson.parentProject);
    var daata = {labRId:labRId};
    var jsonData = JSON.stringify(daata)
    $.ajax({
       url: zuulUrl  + "api/labReservation/apiLabRoomReservationAudit",
       data: jsonData,
       async: false,
       type: "POST",
       headers: {Authorization: getJWTAuthority()},
       contentType: "application/json;charset=UTF-8",
    	success:function (res) {
           //父项目详情表单初始赋值
            form.val('labReservationAudit', {
                "cname": res.cname,
                "academyName": res.academyName,
                "teacherName": res.teacherName,
                "reservationDate": res.reservationDate,
                "telephone": res.telephone,
                "userObjectName": res.userObjectName,
                "reason": res.reason,
                "startTime": res.startTime,
                "endTime": res.endTime,
                "eventType": res.eventType,
                "eventName": res.eventName,
                "number": res.number,
                "labRoomName": res.labRoomName,
            });
            var list = res.processLine;

            var uls = "<ul class=\"layui-timeline\">";
            var uls1 = "<ul>";
            var uls2 = "</ul>";
            var lis = "<li class=\"layui-timeline-item\">";
            var lis1 = "<li>";
            var lis2 = "</li>";
            var is = "<i class=\"layui-icon layui-timeline-axis\"></i>";
            var divs = "<div class=\"layui-timeline-content layui-text\">";
            var divs2 = "</div>";
            var h3s = "<h3 class=\"layui-timeline-title\">";
            var h3s2 = "</h3>";
            var ps = "<p>";
            var ps2 = "</p>";
            var br = "</br>";
            if(list.length>0){
                console.log(list);
                var content1 = "";
                content1 = content1+uls;
                for(var i=0; i<list.length; i++){
                    var content2 = "";
                    content2 = content2+lis+is+divs;
                    if(list[i][3]!=null&&list[i][3]!=''){
                        content2 = content2+h3s+list[i][3]+h3s2
                    }
                    if(list[i][0]!=null&&list[i][0]!=''){
                        var str=(list[i][2].substring(list[i][2].length-1)==',')?list[i][2].substring(0,list[i][2].length-1):list[i][2];
                        content2 = content2+ps+list[i][0]+list[i][1]+ps2;
                        content2 = content2+ps+str+ps2;
                    }

                    //可扩展
                    content2 = content2 + divs2+lis2;
                    content1 =content1+content2;
                }
                content1 = content1 +uls2;

                //再跟你想追加的代码加到一起插入div中
                document.getElementById('timelineTimetable').innerHTML = content1;
            }
       },
    	error:function () {
               alert("后台出了点问题，请重试！");
               return false;
       }
    })
    form.on('radio(redioF)', function(data){
//			    console.log(data.value); //被点击的radio的value值

        if(data.value == 1){
            $("#textRemark").hide();
        }else{
            $("#textRemark").show();

        }
    });
    form.on('submit(submitAudit)', function(data){
        console.log('审核')
        // console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var businessAppUid = $('#labRId').val();
        var result = data.field.gpexamine;
        var info = data.field.remark;
        var daata = {businessAppUid:businessAppUid,result:result,info:info};
        var jsonData = JSON.stringify(daata)
        $.ajax({
            url: zuulUrl  + "api/labReservation/apiSaveLabRoomReservationAudit",
            // data: data,
            async: false,
            data: jsonData,
            type: "POST",
            headers: {Authorization: getJWTAuthority()},
            contentType: "application/json;charset=UTF-8",
            success:function (res) {
                if(res == "success"){
                    alert("审核完成！");
                }else{
                    alert("审核失败！");
                }
                var index=parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                parent.location.reload();
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
    });
});
// }
function getJWTAuthority() {
    var authorization = "";
    initDirectoryEngine({
        getHostsUrl: contextPath + "/shareApi/getHosts",
        getAuthorizationUrl: contextPath + "/shareApi/getAuthorization"
    });
    getAuthorization({
        async: false,
        success: function (data) {
            authorization = data;
        }
    });
    return authorization;
}