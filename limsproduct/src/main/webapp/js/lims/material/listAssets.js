var contextPath = $("meta[name='contextPath']").attr("content");

layui.config({
    version: '1545041465480' //为了更新 js 缓存，可忽略
});

layui.use(['laypage', 'layer', 'table', 'element','form'], function() {
    var laypage = layui.laypage //分页
        ,
        layer = layui.layer //弹层
        ,
        table = layui.table //表格
        ,
        element = layui.element //元素操作
        ,
        form = layui.form //元素操作

    var tableIns=table.render({
        elem: '#assetsList',
        url: $("#contextPath").val()+'/lims/api/material/listAssets', //数据接口
        // where: {keywords: keywords},
        title: '物资名录',
        page: true, //开启分页
        toolbar: 'table', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
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
                },{
                fixed: 'left',
                field: 'cas',
                title: 'cas号',
                minWidth: 130,
                align: 'center',
                sort: true,
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
                title: '物品名称',
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
            },{
                field: 'price',
                title: '参考单价',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
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
        limits: [25, 50, 100],
        limit: 25 //每页默认显示的数量
        ,id: 'assetsList'
    });
    //获取物资类别
    $.ajax({
        url: contextPath+'/lims/api/material/assetsClassificationList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#kind').append(new Option(item.cname, item.id));// 下拉菜单里添加元素
                $('#kind').val(kind);
            });
            layui.form.render("select");
        }
    });
    //数据获取
    var active2 = {
        search: function () {
            var keywords=$("#keywords").val();
            table.reload('assetsList', {
                url: $("#contextPath").val()+'/lims/api/material/listAssets',
                where: {keywords: keywords} //设定异步数据接口的额外参数
            });
            // tableIns.reload(
            //     {
            //         where: { //设定异步数据接口的额外参数，任意设
            //
            //         }
            //         ,page: {
            //             curr: 1 //重新从第 1 页开始
            //         }
            //     }
            // )
        }
    };
    form.on('select(kind)', function(data){
        console.log(data.value)
        table.reload('assetsList', {
            url: $("#contextPath").val()+'/lims/api/material/listAssets',
            where: {kind: data.value} //设定异步数据接口的额外参数
        });
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
                    url: contextPath + '/lims/api/material/deleteAssets?id='+data.id,
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
                area: ['390px', '500px'],
                shade: 0,
                maxmin: true,
                offset: [ //为了演示，随机坐标
                    0.4*($(window).height()-300)
                    ,0.4*($(window).width()-390)
                ],
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
            var realURL=contextPath + '/lims/api/material/editAssetsDetailAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '编辑物资名录',
                area: ['390px', '500px'],
                shade: 0,
                maxmin: true,
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
        };
    });
    //打开新建物资名录页面
    var active = {
        newAssets: function() {
            var realURL=contextPath + '/lims/api/material/editAssetsDetailAPI';
            //多窗口模式，层叠置顶
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '新建物资名录',
                area: ['390px', '500px'],
                shade: 0,
                maxmin: true,
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
    var active3 = {
        newAssetsApply: function() {
            var realURL=contextPath + '/lims/api/material/editAssetsApplyDetailAPI';
            //多窗口模式，层叠置顶
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '新建物资申购',
                area: ['390px', '500px'],
                shade: 0,
                maxmin: true,
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
    //
    var active4 = {
        reload: function() {
            window.location.reload();
        }
    };
    $('.new_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
    $('.search_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active2[method] ? active2[method].call(this, othis) : '';
    });
    $('.apply_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active3[method] ? active3[method].call(this, othis) : '';
    });
    $('.reload_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active4[method] ? active4[method].call(this, othis) : '';
    });

});

