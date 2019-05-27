var contextPath = $("meta[name='contextPath']").attr("content");

layui.config({
    version: '1545041465480' //为了更新 js 缓存，可忽略
});

layui.use(['laypage', 'layer', 'table', 'element','form','laydate','upload'], function() {
    var laypage = layui.laypage //分页
        ,
        layer = layui.layer //弹层
        ,
        table = layui.table //表格
        ,
        element = layui.element //元素操作
    var laydate = layui.laydate;//日期
    laydate.render({
        elem: '#applyDate'
    });
    var upload=layui.upload;
    var form = layui.form;//表单
    var id=$("#id").val();
    var academyNumber;
    var department;
    var goodsCategory;
    var cabinet;

    $.ajax({
        url: contextPath + '/lims/api/material/editAssetsInStorage?id='+id,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            //初始表单赋值
            form.val('assetsInStorageDetail', {
                "date": data.date,
                "username": data.username,
                "academyNumber": data.academyNumber,
                "department": data.department,
                "goodsCategory": data.goodsCategory,
                "totalPrice": data.totalPrice,
                "cabinet": data.cabinet,
                "applyDate": data.applyDate,
            });
            academyNumber=data.academyNumber;
            department=data.department;
            goodsCategory=data.goodsCategory;
            cabinet=data.cabinet;
            if(id===''){
                $("#item").hide();
                $("#price").hide();
            }
        },
        error:function () {
            if(id!=""){
                alert("后台出了点问题，请重试！");
                return false;
            }
        }
    });

    //获取学院
    $.ajax({
        url: contextPath+'/lims/api/material/schoolAcademyList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#academyNumber').append(new Option(item.academyName, item.academyNumber));// 下拉菜单里添加元素
                $('#academyNumber').val(academyNumber);
            });
            layui.form.render("select");
        }
    });

    //获取中心
    $.ajax({
        url: contextPath+'/lims/api/material/labCenterList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#department').append(new Option(item.centerName, item.id));// 下拉菜单里添加元素
                $('#department').val(department);
            });
            layui.form.render("select");
        }
    });

    //获取物资类别
    $.ajax({
        url: contextPath+'/lims/api/material/assetsClassificationList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#goodsCategory').append(new Option(item.cname, item.id));// 下拉菜单里添加元素
                $('#goodsCategory').val(goodsCategory);
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
    var tableIns=table.render({
        elem: '#assetsList',
        url: '../material/assetsInStorageItemList', //数据接口
        where: {id: id},
        title: '物资申购条目',
        page: true, //开启分页
        // toolbar: 'table', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        async:false,
        // totalRow: true, //开启合计行
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //curr: 5, //设定初始在第 5 页
            groups: 1, //只显示 1 个连续页码
            first: false, //不显示首页
            last: false //不显示尾页
        },
        cols: [
            [ //表头
                {
                    fixed: 'left',
                    title: '序号',
                    type: 'numbers',
                    align: 'center',
                    width: 50
                }, {
                fixed: 'left',
                field: 'cabinet',
                title: '物品柜',
                minWidth: 130,
                align: 'center',
                sort: true,
                // totalRowText: '项目预算合计：'
            }, {
                fixed: 'left',
                field: 'kind',
                title: '物资类别',
                minWidth: 130,
                align: 'center',
                sort: true,
                // totalRowText: '项目预算合计：'
            }, {
                field: 'name',
                title: '物资名称',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'type',
                title: '型号及规格',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'unit',
                title: '单位',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'amount',
                title: '数量',
                minWidth: 100,
                align: 'center',
                sort: true,
            },{
                field: 'price',
                title: '单价',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'totalPrice',
                title: '单项总价',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'factory',
                title: '供应商',
                minWidth: 100,
                align: 'center',
                sort: true,
            },{
                fixed: 'right',
                title: '操作',
                width: 250,
                align: 'center',
                toolbar: '#parentbar'
            }
            ]
        ],
        data: table,
        //skin: 'line' ,//表格风格
        even: true,
        page: true,
        limits: [5, 7, 10, 20],
        limit: 5 //每页默认显示的数量
        ,id: 'assetsList'
    });
    //监听行工具事件
    table.on('tool(assetsList)', function(obj) { //注：tool 是工具条事件名，parenthead 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail') {
            layer.msg('查看该项目');
        } else if(layEvent === 'del') {
            layer.confirm('确定删除?', function(index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                $.ajax({
                    url: contextPath + '/lims/api/material/deleteAssetsInStorageItem?id='+data.id,
                    // data: jsonData,
                    async: false,
                    type: "GET",
                    contentType: "application/json;charset=UTF-8",
                    success:function (res) {
                        console.log(res);
                        location.reload();
                    },
                    error: function () {
                        alert("后台出了点问题，请重试！");
                        return false;
                    }
                })
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit') {
            layer.msg('编辑该项目');
        };
        //打开查看物资名录页面
        if(obj.event === 'detail') {
            var realURL=contextPath + '/lims/api/material/assetsDetailAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '查看物资详情',
                area: ['390px', '260px'],
                shade: 0,
                maxmin: true,
                content: realURL,
                zIndex: layer.zIndex //重点1
                ,
                success: function(layero) {
                    layer.setTop(layero); //重点2
                    var body = layui.layer.getChildFrame('body', index);
                    body.find("#parentProId").val(data.id);
                }
            });
            layer.full(index);
        };
        //打开编辑物资名录页面
        if(obj.event === 'edit') {
            var realURL=contextPath + '/lims/api/material/addAssetsInStorageItemAPI?id='+data.id+'&&storeId='+id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '编辑物资名录',
                area: ['390px', '260px'],
                shade: 0,
                maxmin: true,
                content: realURL,
                zIndex: layer.zIndex //重点1
                ,
                success: function(layero) {
                    layer.setTop(layero); //重点2
                }
            });
            layer.full(index);
        };
    });
    //上传
    var paclistView = $('#paclist'),
        uploadListIns = upload.render({
            elem: '#assetsImage',
            url: contextPath + '/lims/api/material/uploadAssetsPic?id='+id, //上传接口
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
                    image +=' src="'+contextPath+'/upload/assetsPic/'+res.data+'" ';
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
        url: contextPath + '/lims/api/material/getAssetsRelatedImage?id='+id+'&&type='+"assetInStorage",
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
    //添加物资名录
    var active = {
        addAssetInStorage: function() {
            var realURL=contextPath + '/lims/api/material/addAssetsInStorageItemAPI?storeId='+id;
            //多窗口模式，层叠置顶
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '新建物资名录',
                area: ['390px', '500px'],
                shade: 0,
                maxmin: true  ,
                offset: [ //为了演示，随机坐标
                    0.4*($(window).height()-300)
                    ,0.4*($(window).width()-390)
                ],
                content: realURL,
                zIndex: layer.zIndex //重点1
                ,
                success: function(layero) {
                    layer.setTop(layero); //重点2
                }
            });
            layer.full(index);
        }
    };
    $('.add_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
    form.on('submit(saveAssetsInStorageDetail)', function(data){
        console.log('保存')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
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
        var data = JSON.stringify(data.field);
        $.ajax({
            url: contextPath + '/lims/api/material/saveAssetsInStorageDetail?imageUrl='+imageUrl+'&&names='+names+'&&sizes='+sizes,
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (res) {
                console.log(res);
                if(id===''){
                    window.location.href="../material/editAssetsInStorageDetailAPI?id="+res;
                }else{
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.location.reload();
                }
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
        return false;
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
