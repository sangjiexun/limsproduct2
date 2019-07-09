var contextPath = $("meta[name='contextPath']").attr("content");

layui.use(['laypage', 'layer', 'table', 'element','form'], function() {
    var laypage = layui.laypage //分页
        ,
        layer = layui.layer //弹层
        ,
        table = layui.table //表格
        ,
        element = layui.element; //元素操作
    var id=$("#id").val();

    var tableIns=table.render({
        elem: '#assetCabinetAccessRecordList',
        url: $("#contextPath").val()+'/lims/api/material/assetCabinetAccessRecordList?id='+id, //数据接口
        title: '物资出入库记录',
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
                field: 'recordType',
                title: '记录类型',
                minWidth: 130,
                align: 'center',
                sort: true,
                templet:'#zh',
            }, {
                field: 'date',
                title: '发起日期',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'username',
                title: '发起人',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'schoolAcademy',
                title: '所属学院',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'cabinetName',
                title: '物品柜',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'quantity',
                title: '数量',
                minWidth: 100,
                align: 'center',
                sort: true,
            },{
                field: 'amount',
                title: '剩余数量',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                fixed: 'right',
                title: '操作',
                width: 250,
                align: 'center',
                toolbar: '#assetCabinetAccessRecordDetailToolBar'
            }
            ]
        ],
        data: table,
        //skin: 'line' ,//表格风格
        even: true,
        page: true,
        limits: [10, 20, 30,50],
        limit: 20 //每页默认显示的数量
        ,id: 'assetCabinetAccessRecordList'
    });
    //监听行工具事件
    table.on('tool(assetCabinetAccessRecordList)', function(obj) { //注：tool 是工具条事件名，parenthead 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail') {
            layer.msg('查看该项目');
        }
        //打开查看物资名录页面
        if(obj.event === 'detail') {
            var type=data.recordType;
            var realURL;
            if(type==="InStorage") {
                realURL=contextPath + '/lims/api/material/checkAssetsInStorageDetailAPI?id='+data.id;
            }else{
                realURL=contextPath + '/lims/api/material/checkAssetsReceiveDetailAPI?id='+data.id;
            }
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '查看详情',
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
        }
    });
});

