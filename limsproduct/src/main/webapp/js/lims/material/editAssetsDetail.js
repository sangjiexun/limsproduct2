var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form','upload'], function() {
    var layer = layui.layer; //弹层
    var upload = layui.upload;
    var form = layui.form;
    var assetsId = $('#id').val();
    var kindId;
    //物资名录表单初始赋值
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
            kindId=data.kind
        },
        error:function () {
            if(assetsId!=""){
                alert("后台出了点问题，请重试！");
                return false;
            }
        }
    });
    //根据不同物资显示不同信息
    form.on('select(kind)', function(data){
        console.log(data.value);
        if(data.value==='1'||data.value==='2'){
            $("#cas").show();
        }else{
            $("#cas").hide();
        }
    });
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
    var paclistView = $('#paclist'),
        uploadListIns = upload.render({
            elem: '#assetsImage',
            url: contextPath + '/lims/api/material/uploadAssetsPic?id='+assetsId, //上传接口
            accept: 'file', //普通文件
            multiple: true, //多个上传
            auto: false, //是否直接选择文件后上传
            bindAction: '#pacbtn',
            choose: function(obj) {
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result) {
                    var tr = $(['<tr id="upload-' + index + '">', '<td class="wordbreak">' + file.name + '</td>', '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>', '<td>等待上传</td>',  '<td></td>','<td>', '<button class="layui-btn layui-btn-xs demo-reload" onClick="return false;">重传</button>', '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>', '</td>', '</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function() {
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function() {
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });
                    paclistView.append(tr);
                });
            },
            done: function(res, index, upload) {
                if(res.code == 0) { //上传成功
                    var tr = paclistView.find('tr#upload-' + index),
                        tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    var image="";
                    image +='<img class="img" ';
                    image +=' src="/limsproduct/upload/assetsPic/'+res.data+'" ';//先把limsproduct写死，以后有需求再变
                    image +=' width="50" ';
                    image +=' height="50" ';
                    image +=' onclick="previewImg(this)">';
                    tds.eq(3).html(image); //
                    tds.eq(4).html('<button type="button" class="layui-btn layui-btn-xs layui-btn-danger delete-btn">删除</button>');
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            },
            error: function(index, upload) {
                var tr = paclistView.find('tr#upload-' + index),
                    tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            },
        });
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
                var tr = $(['<tr id="upload-' + index + '">', '<td class="wordbreak">' + item.imageName + '</td>', '<td>' + item.imageSize + '</td>', '<td>上传成功</td>',  '<td></td>','<td>','<button type="button" class="layui-btn layui-btn-xs layui-btn-danger delete-btn">删除</button>', '</td>', '</tr>'].join('')),
                    tds = tr.children();
                    tds.eq(3).html(image);
                paclistView.append(tr);
            });
        }
    });
    // 删除上传的图片
    paclistView.on("click", '.delete-btn', function () {
        var delid = this.id;
        var currentDelIndex = parseInt(delid.split("_")[1]);
        var theCurrentTr = $(this).parent().parent();
        // 更新表格中当前行后面每一行的序号
        // 找到当前行后面的每一行
        theCurrentTr.nextAll().each(function () {
            // each中的this和外面的this不一样了!
            var oldDelid = $(this).children().eq(4).find(".delete-btn").attr("id");
            if (oldDelid && oldCsid) {
                $(this).children().eq(2).find(".delete-btn").attr("id", oldDelid.split("_")[0] + "_" + (oldDelid.split("_")[1] - 1));
            }
        });
        // 找到当前行,删除
        theCurrentTr.remove();
    });
    form.on('submit(saveAssetsDetail)', function(data){
        console.log('保存');
        console.log(data.elem); //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form);//被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field);//当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        console.log(data);
        //获取上传图片
        var imageUrl="";//图片路径
        var sizes="";//图片大小
        var names="";//图片名称
        var array=new Array();
        var array2=new Array();
        var array3=new Array();
        paclistView.find("tr").each(function(){
            var name=$(this).children().eq(0).text();
            var size=$(this).children().eq(1).text();
            var image=$(this).children().eq(3).children().attr("src");
            array.push(image);
            array2.push(name);
            array3.push(size);
        });
        imageUrl=array.join(",");
        names=array2.join(",");
        sizes=array3.join(",");
        //保存审核信息
        $.ajax({
            url: contextPath + '/lims/api/material/saveAssetsDetail?imageUrl='+imageUrl+'&&names='+names+'&&sizes='+sizes,
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