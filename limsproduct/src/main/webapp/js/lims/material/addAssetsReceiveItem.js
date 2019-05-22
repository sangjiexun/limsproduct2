var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var layer = layui.layer; //弹层
    var form = layui.form;
    var cabinetId;
    var id=$("#id").val();
    var assetsId;
    var cabinet;
    //物资名录表单初始赋值
    $.ajax({
        url: contextPath + '/lims/api/material/editAssetsReceiveItem?id='+id,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            //物资详情表单赋值
            form.val('addAssets', {
                "assetsId":data.assetsId,
                "name": data.name,
                "type": data.type,
                "unit": data.unit,
                "quantity": data.quantity,
                "cabinet":data.cabinet,
                "factory": data.factory,
                "amount":data.amount,

            });
            assetsId=data.assetsId;
            cabinet=data.cabinet;
            $.ajax({
                url: contextPath+'/lims/api/material/assetsCabinetList?assetId='+assetsId,
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    $("#cabinet").find("option").remove();
                    $("#cabinet").append(new Option("请选择"));
                    $.each(data, function (index, item) {
                        $('#cabinet').append(new Option(item.cabinetName, item.id));// 下拉菜单里添加元素
                        $('#cabinet').val(cabinet);// 赋值
                    });
                    layui.form.render("select");
                }
            });
        },
    });
    //获取原有物资名录
    $.ajax({
        url: contextPath+'/lims/api/material/assetsList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#assetsId').append(new Option(item.name, item.id));// 下拉菜单里添加元素
                $('#assetsId').val(assetsId);
            });
            layui.form.render("select");
        }
    });
    form.on('select(assets)', function(data){
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        //物资名录表单初始赋值
        $.ajax({
            url: contextPath + '/lims/api/material/editAssets?id='+data.value,
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success:function (data) {
                //物资详情表单赋值
                form.val('addAssets', {
                    "kind": data.kind,
                    "name": data.name,
                    "type": data.type,
                    "unit": data.unit,
                    "price": data.price,
                    "factory": data.factory,
                });
                assetsId=data.id;
                $.ajax({
                    url: contextPath+'/lims/api/material/assetsCabinetList?assetId='+assetsId,
                    dataType: 'json',
                    type: 'get',
                    success: function (data) {
                        $("#cabinet").find("option").remove();
                        $.each(data, function (index, item) {
                            $('#cabinet').append(new Option(item.cabinetName, item.id));// 下拉菜单里添加元素
                        });
                        layui.form.render("select");
                    }
                });
            }
        });
    });
    form.on('select(cabinet)', function(data){
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        console.log(assetsId);
        $.ajax({
            url: contextPath + '/lims/api/material/getAssetsAmountFromCabinet?id='+data.value+'&&assetId='+assetsId,
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success:function (data) {
                //物资剩余数量赋值
                form.val('addAssets', {
                    "amount": data
                });
            }
        });
    });
    //监控输入数量
    $("#quantity").bind("input propertychange",function(){
        var quantity=$("#quantity").val();
        var cabinet=$("#cabinet").val();
        $.ajax({
            url: contextPath + '/lims/api/material/getAssetsAmountFromCabinet?id='+cabinet+'&&assetId='+assetsId+'&&quantity='+quantity+'&&itemId='+id,
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success:function (data) {
                //物资剩余数量赋值
                form.val('addAssets', {
                    "amount": data
                });
            }
        });
    });
    form.on('submit(saveAddAssetsReceiveDetail)', function(data){
        console.log('保存')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        $.ajax({
            url: contextPath + '/lims/api/material/saveAddAssetsReceiveDetail',
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (res) {
                if(res=="insufficient"){
                    alert("库存不足");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.location.reload();
                }else if(res=="notEnough"){
                    alert("单个物品柜数量不足，请联系库存管理员获取");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.location.reload();
                }else {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.location.reload();
                }
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
    });
});