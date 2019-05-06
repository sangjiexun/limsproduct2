var contextPath = $("meta[name='contextPath']").attr("content");
//加载form模块
layui.use('form', function() {
    var $=layui.jquery;
    var form = layui.form;
    form.on('select(showAuditLevelApply)', function(data){
        var auditLevelApply=$("#auditLevelApply").val();
        if(auditLevelApply!=-2){
            $("#auditLevelApplyName").show();
            for(var i = 0;i<auditLevelApply;i++){
                $( $("#auditLevelApplyName").children().get(i)).css("display","");
            }
            for(var i=auditLevelApply;i<$("#auditLevelApplyName").children().size();i++){
                $( $("#auditLevelApplyName").children().get(i)).css("display","none");
            }
        }
    });
    form.on('select(showAuditLevelStorage)', function(data){
        var auditLevelStorage=$("#auditLevelStorage").val();
        if(auditLevelStorage!=-2){
            $("#auditLevelStorageName").show();
            for(var i = 0;i<auditLevelStorage;i++){
                $( $("#auditLevelStorageName").children().get(i)).css("display","");
            }
            for(var i=auditLevelStorage;i<$("#auditLevelStorageName").children().size();i++){
                $( $("#auditLevelStorageName").children().get(i)).css("display","none");
            }
        }
    });
    form.on('select(showAuditLevelReceive)', function(data){
        var auditLevelReceive=$("#auditLevelReceive").val();
        if(auditLevelReceive!=-2){
            $("#auditLevelReceiveName").show();
            for(var i = 0;i<auditLevelReceive;i++){
                $( $("#auditLevelReceiveName").children().get(i)).css("display","");
            }
            for(var i=auditLevelReceive;i<$("#auditLevelReceiveName").children().size();i++){
                $( $("#auditLevelReceiveName").children().get(i)).css("display","none");
            }
        }
    });

    form.on('submit(saveAssetClassification)', function(data){
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        var array=new Array();//定义一数组
        array=data.split(","); //字符分割
        var id = array[0].split(":")[1].replace("\"","").replace("\"","");
        var cname = array[1].split(":")[1].replace("\"","").replace("\"","");
        var ename = array[2].split(":")[1].replace("\"","").replace("\"","");
        var info = "";
        if(array.length==23) {
            info = array[21].split(":")[1].replace("\"", "").replace("\"", "");
        }else if(array.length==22){
            info = array[21].split(":")[1].replace("\"", "").replace("\"", "").split("}")[0];
        }
        var isNeedReturn="off";
        if(array.length==23) {
            isNeedReturn = array[22].split(":")[1].replace("\"", "").replace("\"", "").split("}")[0];
        }else if(array.length==22)
        {
            isNeedReturn="off";
        }
        if(isNeedReturn=="on"){
            isNeedReturn = 1;
        }else if(isNeedReturn=="off"){
            isNeedReturn = 0;
        }
        var applyAuditFlag = array[3].split(":")[1].replace("\"","").replace("\"","");
        var storageAuditFlag = array[9].split(":")[1].replace("\"","").replace("\"","");
        var receiveAuditFlag = array[15].split(":")[1].replace("\"","").replace("\"","");
        var applyAudit = "";
        var storageAudit = "";
        var receiveAudit = "";
        if(applyAuditFlag!=-2){
            for(var i=1;i<5;i++){
                if(array[i+3].split(":")[1].replace("\"","").replace("\"","")!=-2){
                    applyAudit += array[i+3].split(":")[1].replace("\"","").replace("\"","")+",";
                }
            }
            applyAudit=applyAudit.substring(0, applyAudit.length-1);
        }else {
            applyAudit = "无需审核";
        }
        if(storageAuditFlag!=-2){
            for(var i=1;i<5;i++){
                if(array[i+9].split(":")[1].replace("\"","").replace("\"","")!=-2){
                    storageAudit += array[i+9].split(":")[1].replace("\"","").replace("\"","")+",";
                }
            }
            storageAudit = storageAudit.substring(0,storageAudit.length-1);
        }else {
            storageAudit = "无需审核";
        }
        if(receiveAuditFlag!=-2){
            for(var i=1;i<5;i++){
                if(array[i+15].split(":")[1].replace("\"","").replace("\"","")!=-2){
                    receiveAudit += array[i+15].split(":")[1].replace("\"","").replace("\"","")+",";
                }
            }
            receiveAudit = receiveAudit.substring(0,receiveAudit.length-1);
        }else {
            receiveAudit = "无需审核";
        }
        var myObject = {
            "id":id,
            "cname":cname,
            "ename":ename,
            "info":info,
            "isNeedReturn":isNeedReturn,
            "applyAudit":applyAudit,
            "storageAudit":storageAudit,
            "receiveAudit":receiveAudit
        }
        var json = JSON.stringify(myObject);
        $.ajax({
            url: contextPath + '/lims/api/material/saveAssetClassification',
            data: json,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (flag) {
                console.log(flag);
                var index=parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.parent.location.reload();
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
    });
    // 物资类别表单初始赋值
    var id = $("#id").val();
    if(id!=""&&id!=null) {
        $.ajax({
            url: contextPath + '/lims/api/material/editAssetClassification?id=' + id,
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                var isNeedReturn = data.isNeedReturn;
                if (isNeedReturn == 1) {
                    $("#isNeedReturn").attr('checked', true);
                } else {
                    $("#isNeedReturn").removeAttr('checked');
                }
                var applyAuditLevel = data.applyAuditLevel;
                var applyAudit = data.applyAudit;
                var applyName1 = -2;
                var applyName2 = -2;
                var applyName3 = -2;
                var applyName4 = -2;
                var applyName5 = -2;
                if (applyAudit != "无需审核" && applyAudit != "" && applyAudit != null) {
                    var arrayAudit1 = new Array();//定义一数组
                    arrayAudit1 = applyAudit.split(",");
                    var level1 = arrayAudit1.length;
                    if (level1 != 5) {
                        for (var a = 5; a > level1; a--) {
                            arrayAudit1[a - 1] = -2;
                        }
                    }
                    applyName1 = arrayAudit1[0];
                    applyName2 = arrayAudit1[1];
                    applyName3 = arrayAudit1[2];
                    applyName4 = arrayAudit1[3];
                    applyName5 = arrayAudit1[4];
                    $("#auditLevelApplyName").show();
                    for (var i = 0; i < applyAuditLevel; i++) {
                        $($("#auditLevelApplyName").children().get(i)).css("display", "");
                    }
                    for (var i = applyAuditLevel; i < $("#auditLevelApplyName").children().size(); i++) {
                        $($("#auditLevelApplyName").children().get(i)).css("display", "none");
                    }
                } else {
                    applyAudit = -2;
                }
                var storageAudit = data.storageAudit;
                var storageAuditLevel = data.storageAuditLevel;
                var storageName1 = -2;
                var storageName2 = -2;
                var storageName3 = -2;
                var storageName4 = -2;
                var storageName5 = -2;
                if (storageAudit != "无需审核" && storageAudit != "" && storageAudit != null) {
                    var arrayAudit2 = new Array();//定义一数组
                    arrayAudit2 = storageAudit.split(",");
                    var level2 = arrayAudit2.length;
                    if (level2 != 5) {
                        for (var s = 5; s > level2; s--) {
                            arrayAudit2[s - 1] = -2;
                        }
                    }
                    storageName1 = arrayAudit2[0];
                    storageName2 = arrayAudit2[1];
                    storageName3 = arrayAudit2[2];
                    storageName4 = arrayAudit2[3];
                    storageName5 = arrayAudit2[4];
                    $("#auditLevelStorageName").show();
                    for (var i = 0; i < storageAuditLevel; i++) {
                        $($("#auditLevelStorageName").children().get(i)).css("display", "");
                    }
                    for (var i = storageAuditLevel; i < $("#auditLevelStorageName").children().size(); i++) {
                        $($("#auditLevelStorageName").children().get(i)).css("display", "none");
                    }
                }
                else {
                    storageAudit = -2;
                }
                var receiveAudit = data.receiveAudit;
                var receiveAuditLevel = data.receiveAuditLevel;
                var receiveName1 = -2;
                var receiveName2 = -2;
                var receiveName3 = -2;
                var receiveName4 = -2;
                var receiveName5 = -2;
                if (receiveAudit != "无需审核" && receiveAudit != "" && receiveAudit != null) {
                    var arrayAudit3 = new Array();//定义一数组
                    arrayAudit3 = receiveAudit.split(",");
                    var level3 = arrayAudit3.length;
                    if (level3 != 5) {
                        for (var r = 5; r > level3; r--) {
                            arrayAudit3[r - 1] = -2;
                        }
                    }
                    receiveName1 = arrayAudit3[0];
                    receiveName2 = arrayAudit3[1];
                    receiveName3 = arrayAudit3[2];
                    receiveName4 = arrayAudit3[3];
                    receiveName5 = arrayAudit3[4];
                    $("#auditLevelReceiveName").show();
                    for (var i = 0; i < receiveAuditLevel; i++) {
                        $($("#auditLevelReceiveName").children().get(i)).css("display", "");
                    }
                    for (var i = receiveAuditLevel; i < $("#auditLevelReceiveName").children().size(); i++) {
                        $($("#auditLevelReceiveName").children().get(i)).css("display", "none");
                    }
                } else {
                    receiveAudit = -2;
                }
                //物资详情表单赋值
                form.val('assetClassificationDetail', {
                    "cname": data.cname,
                    "ename": data.ename,
                    "info": data.info,
                    "applyAudit": applyAuditLevel,
                    "applyName1": applyName1,
                    "applyName2": applyName2,
                    "applyName3": applyName3,
                    "applyName4": applyName4,
                    "applyName5": applyName5,
                    "storageAudit": storageAuditLevel,
                    "storageName1": storageName1,
                    "storageName2": storageName2,
                    "storageName3": storageName3,
                    "storageName4": storageName4,
                    "storageName5": storageName5,
                    "receiveAudit": receiveAuditLevel,
                    "receiveName1": receiveName1,
                    "receiveName2": receiveName2,
                    "receiveName3": receiveName3,
                    "receiveName4": receiveName4,
                    "receiveName5": receiveName5,
                });
                //重新渲染
                layui.form.render('select');
            },
            error: function () {
                if (id != "") {
                    alert("后台出了点问题，请重试！");
                    return false;
                }
            }
        });
    }
});


