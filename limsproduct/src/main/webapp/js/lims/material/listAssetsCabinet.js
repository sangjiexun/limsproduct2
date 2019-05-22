var contextPath = $("meta[name='contextPath']").attr("content");


layui.use(['laypage', 'layer', 'table', 'element','form'], function() {
    var laypage = layui.laypage //分页
        ,
        layer = layui.layer //弹层
        ,
        table = layui.table ;//表格
    var tableIns=table.render({
        elem: '#assetCabinetList',
        url: $("#contextPath").val()+'/lims/api/material/assetsAllCabinetList', //数据接口
        // where: {keywords: keywords},
        title: '物品柜列表',
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
                field: 'cabinetCode',
                title: '物品柜编号',
                minWidth: 130,
                align: 'center',
                sort: true,
                // totalRowText: '项目预算合计：'
            }, {
                field: 'cabinetName',
                title: '物品柜名称',
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
        limits: [10, 20, 30,50],
        limit: 20 //每页默认显示的数量
        ,id: 'assetCabinetList'
    });
    //监听行工具事件
    table.on('tool(assetCabinetList)', function(obj) { //注：tool 是工具条事件名，parenthead 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail') {
            layer.msg('查看该项目');
        } else if(layEvent === 'del') {
            layer.confirm('确定删除?', function(index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                $.ajax({
                    url: contextPath + '/lims/api/material/deleteAssetsCabinet?id='+data.id,
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
        };
        //打开查看物资名录页面
        if(obj.event === 'detail') {
            var realURL=contextPath + '/lims/api/material/checkAssetsCabinetDetailAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '查看物品柜详情',
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
            var realURL=contextPath + '/lims/api/material/editAssetsCabinetDetailAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '编辑物品柜',
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
    });
    //新建物资申购页面
    var active = {
        newAssetsCabinet: function() {
            var realURL=contextPath + '/lims/api/material/editAssetsCabinetDetailAPI';
            //多窗口模式，层叠置顶
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '新建物品柜',
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
    $('.apply_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
});

