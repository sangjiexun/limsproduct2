var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var appId=$("#appId").val();
    var type=$("#type").val();
    var active = {
        admit: function() {
            var reason=$("#reason").val();
            var url;
            if(type==="InStorage"){
                url=contextPath + '/lims/api/material/changeAssetsInStorageStatus?id='+appId+'&&result=fail'+'&&reason='+reason;
            }else if(type==="Apply"){
                url=contextPath + '/lims/api/material/changeAssetsApplyStatus?id='+appId+'&&result=fail'+'&&reason='+reason;
            }else if(type==="Receive"){
                url=contextPath + '/lims/api/material/changeAssetsReceiveStatus?id='+appId+'&&result=fail'+'&&reason='+reason;
            }
            $.ajax({
                url: url,
                async: false,
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                success:function (res) {
                    console.log(res);
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    layer.closeAll();
                    window.parent.parent.location.reload();
                },
                error:function(){
                    alert("后台出了点问题，请重试！");
                    return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
                }
            });
        }
    };
    var activ2 = {
        cancel: function() {
            $.ajax({
                url: contextPath + '/lims/api/material/changeAssetsInStorageStatus?id='+id+'&&result=pass',
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
        }
    };
    $('.admit_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
    $('.reject_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active2[method] ? active2[method].call(this, othis) : '';
    });
});
