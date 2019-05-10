var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var layer = layui.layer; //弹层
    var form = layui.form;
    form.on('submit(returnRemainAssets)', function(data){
        var amount=$("#amount").val();
        var id=$("#appId").val();
        $.ajax({
            url: contextPath + '/lims/api/material/returnAssetsRemainForReceive?amount='+amount+'&&id='+id,
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
