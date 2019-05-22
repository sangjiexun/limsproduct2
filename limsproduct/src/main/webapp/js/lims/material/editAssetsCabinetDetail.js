var contextPath = $("meta[name='contextPath']").attr("content");
//加载form模块
layui.use('form', function() {
    var $=layui.jquery;
    var form = layui.form;
    form.on('submit(saveAssetCabinet)', function(data){
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        $.ajax({
            url: contextPath + '/lims/api/material/saveAssetsCabinet',
            data: data,
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
    if(id!==""&&id!=null) {
        $.ajax({
            url: contextPath + '/lims/api/material/editAssetsCabinet?id='+id,
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                //物资详情表单赋值
                form.val('assetCabinetDetail', {
                    "cabinetCode": data.cabinetCode,
                    "cabinetName": data.cabinetName,
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


