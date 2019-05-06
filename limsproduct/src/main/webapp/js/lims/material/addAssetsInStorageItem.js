var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form'], function() {
    var layer = layui.layer; //弹层
    var form = layui.form;
    var kindId;
    var id=$("#id").val();
    var assetsId;
    var cabinet;
    //物资名录表单初始赋值
    $.ajax({
        url: contextPath + '/lims/api/material/editAssetsInStorageItem?id='+id,
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
                "price": data.price,
                "factory": data.factory,
                "quantity": data.quantity,
                "cabinet":data.cabinet,
                "invoiceNumber":data.invoiceNumber,
                "itemRemarks":data.itemRemarks,
            });
            assetsId=data.assetsId;
            cabinet=data.cabinet
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
    //获取物品柜
    $.ajax({
        url: contextPath+'/lims/api/material/assetsCabinetList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#cabinet').append(new Option(item.cabinetName, item.id));// 下拉菜单里添加元素
                $('#cabinet').val(cabinet);
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
                    "name": data.name,
                    "type": data.type,
                    "unit": data.unit,
                    "price": data.price,
                    "factory": data.factory,
                });
                kindId=data.kind
            },
            error:function () {
                if(assetsId!=""){
                    alert("后台出了点问题，请重试！");
                    return false;
                }
            }
        });
    });
    form.on('submit(saveAddAssetsInStorageDetail)', function(data){
        console.log('保存')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        $.ajax({
            url: contextPath + '/lims/api/material/saveAddAssetsInStorageDetail',
            data: data,
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