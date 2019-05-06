layui.use('table', function () {
    var table = layui.table;
    //展示已知数据
    table.render({
        elem: '#LAY_table_user',
        url: 'test2.json',
        toolbar: '#toolbarDemo',
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,
            groups: 1 //只显示 1 个连续页码
            ,
            first: false //不显示首页
            ,
            last: false //不显示尾页

        },
        cols: [
            [ //标题栏
                {
                    fixed: 'left',
                    title: '序号',
                    templet: '#indexTpl',
                    width: 50
                }, {
                fixed: 'left',
                field: 'one',
                title: '实验室名称',
                width: 150
            }, {
                field: 'two',
                title: '实验室编号',
                minWidth: 110
            }, {
                field: 'three',
                title: '实验室地址',
                minWidth: 110
            }, {
                field: 'four',
                title: '实验室管理员',
                minWidth: 110
            }, {
                fixed: 'right',
                field: 'five',
                title: '操作',
                toolbar: '#operationbtn'
            }
            ]
        ],
        data: table
        //,skin: 'line' //表格风格
        ,
        even: true,
        page: true,
        limits: [5, 7, 10, 20],
        limit: 5 //每页默认显示的数量
    });

    var $ = layui.$,
        active = {
            reload: function () {
                var time = $('#time');
                var capacity = $('#capacity');
                var labmedia = $('#labmedia');
                var place = $('#place');

                //执行重载
                table.reload('testReload', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: {
                        key: {
                            id: time.val(),
                            id: capacity.val(),
                            id: labmedia.val(),
                            id: place.val(),
                        }
                    }
                });
            }
        };

    $('.demoTable .layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //头工具栏事件
    table.on('toolbar(LAY_table_user)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(LAY_table_user)', function (obj) {
        var data = obj.data;
        //console.log(obj)
        if (obj.event === 'showlabinfo') {
            layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '填写预约信息',
                area: ['100%', '100%'],
                shade: 0,
                maxmin: false,
                content: 'lab_info.html',
                zIndex: layer.zIndex //重点1
                ,
                success: function (layero) {
                    layer.setTop(layero); //重点2
                }
            });
        }
    });
});
layui.use('laydate', function () {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#time'
    });
});
