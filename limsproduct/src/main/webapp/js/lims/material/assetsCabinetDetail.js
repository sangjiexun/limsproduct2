var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var form = layui.form;
    var id = $('#id').val();
    //初始赋值
    $.ajax({
        url: contextPath + '/lims/api/material/editAssetsCabinet?id='+id,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            form.val('assetsCabinetDetail', {
                "cabinetCode": data.cabinetCode,
                "cabinetName": data.cabinetName,
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