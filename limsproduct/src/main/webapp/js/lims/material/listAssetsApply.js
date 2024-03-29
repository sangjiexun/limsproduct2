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
        form = layui.form //表单操作


    var tableIns=table.render({
        elem: '#assetsApplyList',
        url: $("#contextPath").val()+'/lims/api/material/assetsApplyList', //数据接口
        // where: {keywords: keywords},
        title: '物资申购名录',
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
                }, {
                fixed: 'left',
                field: 'batchNumber',
                title: '申购编号',
                minWidth: 130,
                align: 'center',
                sort: true,
                // totalRowText: '项目预算合计：'
            }, {
                field: 'date',
                title: '申购日期',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'applicantUserName',
                title: '申购人',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'academyNumber',
                title: '所属学院',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'price',
                title: '申请金额',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'goodsCategory',
                title: '物资类别',
                minWidth: 100,
                align: 'center',
                sort: true,
            },{
                field: 'status',
                title: '状态',
                minWidth: 100,
                align: 'center',
                sort: true,
                templet:'#zh'
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
        limits: [10, 20, 30,50],
        limit: 20 //每页默认显示的数量
        ,id: 'assetsApplyList'
    });
    //数据获取
    var active2 = {
        search: function () {
            var keywords=$("#keywords").val();
            table.reload('assetsApplyList', {
                url: $("#contextPath").val()+'/lims/api/material/assetsApplyList',
                where: {keywords: keywords} //设定异步数据接口的额外参数
            });
        }
    };
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
    form.on('select(kind)', function(data){
        console.log(data.value)
        table.reload('assetsApplyList', {
            url: $("#contextPath").val()+'/lims/api/material/assetsApplyList',
            where: {kind: data.value} //设定异步数据接口的额外参数
        });
    });
    //监听行工具事件
    table.on('tool(assetsApplyList)', function(obj) { //注：tool 是工具条事件名，parenthead 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail') {
            layer.msg('查看该项目');
        } else if(layEvent === 'del') {
            layer.confirm('确定删除?', function(index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                $.ajax({
                    url: contextPath + '/lims/api/material/deleteAssetsApply?id='+data.id,
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
                });
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'submit') {
            layer.confirm('确定提交?', function(index) {
                $.ajax({
                    url: contextPath + '/lims/api/material/submitAssetsApply?id='+data.id,
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
                });
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit') {
            layer.msg('编辑该项目');
        };
        //打开查看物资名录页面
        if(obj.event === 'detail') {
            var realURL=contextPath + '/lims/api/material/checkAssetsApplyDetailAPI?id='+data.id;
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
        //打开编辑物资申请页面
        if(obj.event === 'edit') {
            var realURL=contextPath + '/lims/api/material/editAssetsApplyDetailAPI?id='+data.id;
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
                }, end: function () {
                    location.reload();
                }
            });
            layer.full(index);
        };
        //发起入库
        if(obj.event === 'inbound') {
            var realURL=contextPath + '/lims/api/material/putAssetsInStorageAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '发起入库',
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
    //新建物资申购页面
    var active = {
        newAssetsApply: function() {
            var realURL=contextPath + '/lims/api/material/editAssetsApplyDetailAPI';
            //多窗口模式，层叠置顶
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '新建物资申购',
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
                }, end: function () {
                    location.reload();
                }
            });
            layer.full(index);
        }
    };
    $('.search_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active2[method] ? active2[method].call(this, othis) : '';
    });
    $('.apply_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });

});

