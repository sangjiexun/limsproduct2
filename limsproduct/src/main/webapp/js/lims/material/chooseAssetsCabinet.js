var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var layer = layui.layer; //弹层
    var form = layui.form;
    //获取物品柜
    $.ajax({
        url: contextPath+'/lims/api/material/assetsCabinetList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#cabinet').append(new Option(item.cabinetName, item.id));// 下拉菜单里添加元素
            });
            layui.form.render("select");
        }
    });
    form.on('submit(chooseAssetsCabinet)', function(data){
        var cabinet=$("#cabinet").val();
        var id=$("#appId").val();
        var invoiceNumber=$("#invoiceNumber").val();
        var itemRemarks=$("#itemRemarks").val();
        $.ajax({
            url: contextPath + '/lims/api/material/chooseAssetsCabinetForApply?cabinet='+cabinet+'&&id='+id+'&&invoiceNumber='+invoiceNumber+'&&itemRemarks='+itemRemarks,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (res) {
                console.log(res);
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
});
