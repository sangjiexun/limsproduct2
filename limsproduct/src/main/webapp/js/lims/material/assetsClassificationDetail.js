var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var form = layui.form;
    var id = $('#id').val();
    //初始赋值
    $.ajax({
        url: contextPath + '/lims/api/material/editAssetClassification?id='+id,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            var applyAudit = data.applyAudit;
            var applyAuditName = "";
            var applyArray=new Array();
            if(applyAudit!="无需审核"){
                applyArray = applyAudit.split(',');
                for (var a = 0; a < applyArray.length; a++) {
                    if (applyArray[a]=="TEACHER") {
                        applyAuditName += "教师" + "->";
                    } else if (applyArray[a]=="EXCENTERDIRECTOR") {
                        applyAuditName += "实验中心主任" + "->";
                    } else if (applyArray[a]=="EQUIPMENTADMIN") {
                        applyAuditName += "设备管理员" + "->";
                    } else if (applyArray[a]=="CHARGEADMIN") {
                        applyAuditName += "费用管理员" + "->";
                    } else if (applyArray[a]=="TEAMHEADER") {
                        applyAuditName += "课题组负责人" + "->";
                    }
                }
                applyAudit = applyAuditName.substring(0, applyAuditName.length- 2);
            }
            var storageAudit = data.storageAudit;
            var storageAuditName = "";
            var storageArray=new Array();
            if(storageAudit!="无需审核"){
                storageArray = storageAudit.split(',');
                for (var s = 0; s < storageArray.length; s++) {
                    if (storageArray[s]=="TEACHER") {
                        storageAuditName += "教师" + "->";
                    } else if (storageArray[s]=="EXCENTERDIRECTOR") {
                        storageAuditName += "实验中心主任" + "->";
                    } else if (storageArray[s]=="EQUIPMENTADMIN") {
                        storageAuditName += "设备管理员" + "->";
                    } else if (storageArray[s]=="CHARGEADMIN") {
                        storageAuditName += "费用管理员" + "->";
                    } else if (storageArray[s]=="TEAMHEADER") {
                        storageAuditName += "课题组负责人" + "->";
                    }
                }
                storageAudit = storageAuditName.substring(0, storageAuditName.length - 2);
            }
            var receiveAudit = data.receiveAudit;
            var receiveAuditName = "";
            var receiveArray=new Array();
            if(receiveAudit!="无需审核"){
                receiveArray = receiveAudit.split(',');
                for (var r = 0; r < receiveArray.length; r++) {
                    if (receiveArray[r]=="TEACHER") {
                        receiveAuditName += "教师" + "->";
                    } else if (receiveArray[r]=="EXCENTERDIRECTOR") {
                        receiveAuditName += "实验中心主任" + "->";
                    } else if (receiveArray[r]=="EQUIPMENTADMIN") {
                        receiveAuditName += "设备管理员" + "->";
                    } else if (receiveArray[r]=="CHARGEADMIN") {
                        receiveAuditName += "费用管理员" + "->";
                    } else if (receiveArray[r]=="TEAMHEADER") {
                        receiveAuditName += "课题组负责人" + "->";
                    }
                }
                receiveAudit = receiveAuditName.substring(0, receiveAuditName.length - 2);
            }
            form.val('assetsClassificationDetail', {
                "cname": data.cname,
                "ename": data.ename,
                "isNeedReturn": data.isNeedReturn,
                "info": data.info,
                "applyAudit": applyAudit,
                "storageAudit": storageAudit,
                "receiveAudit": receiveAudit,
            });
            layui.form.render();
        },
        error:function () {
            if(id!=""){
                alert("后台出了点问题，请重试！");
                return false;
            }
        }
    });
});