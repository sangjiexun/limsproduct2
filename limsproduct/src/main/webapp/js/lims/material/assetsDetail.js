var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form','upload'], function() {
    var form = layui.form;
    var layer = layui.layer;
    var upload = layui.upload;
    var assetsId = $('#id').val();
    var kindId;
    $.ajax({
        url: contextPath + '/lims/api/material/editAssets?id='+assetsId,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            //物资详情表单赋值
            form.val('assetsDetail', {
                "kind": data.kind,
                "name": data.name,
                "type": data.type,
                "unit": data.unit,
                "price": data.price,
                "factory": data.factory,
                "picture": data.picture,
                "cas": data.cas,
                "function": data.function,
            });
            kindId=data.kind;
        },
        error:function () {
            alert("后台出了点问题，请重试！");
            return false;
        }
    })
    //获取物资类别
    $.ajax({
        url: contextPath+'/lims/api/material/assetsClassificationList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#kind').append(new Option(item.cname, item.id));// 下拉菜单里添加元素
                $('#kind').val(kindId);
            });
            layui.form.render("select");
        }
    });
    //上传
    var paclistView = $('#paclist');
    // 获取图片
    $.ajax({
        url: contextPath + '/lims/api/material/getAssetsRelatedImage?id='+assetsId+'&&type='+"asset",
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            $.each(data, function (index, item) {
                var image="";
                image +='<img class="img" ';
                image +=' src="'+item.imageUrl+'" ';
                image +=' width="50" ';
                image +=' height="50" ';
                image +=' onclick="previewImg(this)">';
                var tr = $(['<tr id="upload-' + index + '">', '<td class="wordbreak">' + item.imageName + '</td>', '<td>' + item.imageSize + '</td>', '<td>上传成功</td>',  '<td></td>', '</tr>'].join('')),
                    tds = tr.children();
                tds.eq(3).html(image);
                paclistView.append(tr);
            });
        }
    });
});
//点击图片预览
function previewImg(obj) {
    var img = new Image();
    img.src = obj.src;
    var height = img.height + 50; //获取图片高度
    var width = img.width; //获取图片宽度
    var imgHtml = "<img src='" + obj.src + "' />";
    //弹出层
    layer.open({
        type: 1,
        shade: 0.8,
        offset: 'auto',
        area: [width + 'px',height+'px'],
        shadeClose:true,//点击外围关闭弹窗
        scrollbar: false,//不现实滚动条
        title: "图片预览", //不显示标题
        content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {
            //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
        }
    });
}
