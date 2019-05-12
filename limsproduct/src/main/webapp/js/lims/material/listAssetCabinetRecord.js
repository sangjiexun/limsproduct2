var contextPath = $("meta[name='contextPath']").attr("content");

layui.use(['table','jquery','layer'], function(){
    var table = layui.table
    ,$ = jquery = layui.$
    ,layer = layui.layer;
    //方法级渲染
    var tableIns = table.render({
        elem: '#assetCabinetRecordList'
        ,toolbar: '#assetCabinetRecordList' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,url: $("#contextPath").val()+'/lims/api/material/assetCabinetRecordList'//数据接口
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            ,curr: 1 //设定初始在第 5 页
            ,groups: 6 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
            ,limit: 6
            ,limits:[5,6,7,8,9,10,20]
        }
        ,cols: [[
            {field:'assetId', title: '序号', fixed: true, type: 'numbers',minwidth: 80}
            ,{field:'cas',title:'cas号'}
            ,{field:'cname', title: '物资名称', }
            ,{field:'categoryCname', title: '物资类别'}
            ,{field:'unit', title: '计量单位'}
            ,{field:'specifications', title: '物资规格'}
            ,{field:'sum', title: '物资数量', sort: true}
            ,{title:'操作', fixed: 'right', toolbar: '#assetRecordToolBar', minwidth:150}
        ]]
        ,id: 'assetCabinetRecordList'
    });
    //数据表格的重载
    var active = {
        reload: function () {
            var cas = $("#cas").val();
            //方法级渲染的重载
            tableIns.reload({
                url: $("#contextPath").val() + '/lims/api/material/assetCabinetRecordList'
                , where: { //设定异步数据接口的额外参数，任意设
                    cas: cas
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
    }
    $("#searchCas").on('click', function(){
        //不能为空验证
        if( $('#cas').val()==""){
           alert("查询数据不能为空！");
                // layer.msg('查询数据不能为空！');
            return false;
        }
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //监听行工具事件
    table.on('tool(assetCabinetRecordList)', function(obj){ //注：tool是工具条事件名，assetCabinetRecordList是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        if(obj.event === 'detail'){
            var url = contextPath + '/lims/api/material/assetCabinetRecordDetailAPI?assetId='+data.assetId+'&type='+"detail";
            //多窗口模式，层叠置顶
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '查看物资记录详情'
                ,area: ['700px', '760px']
                ,shade: 0 //不显示遮罩
                ,maxmin: true //最大最小化  默认不显示最大小化按钮
                ,offset: 'auto'//坐标  默认：垂直水平居中
                ,content: url
                ,closeBtn: 1 //关闭按钮  默认1
                ,zIndex: layer.zIndex //层叠顺序
                ,success: function(layero,index){
                    layer.setTop(layero); //置顶当前窗口
                    layer.full(index); //最大化
                }
            });
        } else if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    url: contextPath + '/lims/api/material/deleteAssetCabinetRecords?assetId='+data.assetId,
                    // data: jsonData,
                    async: false,
                    type: "GET",
                    contentType: "application/json;charset=UTF-8",
                    success:function (res) {
                        location.reload();
                    },
                    error: function () {
                        alert("后台出了点问题，请重试！");
                        return false;
                    }
                })
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            var url = contextPath + '/lims/api/material/assetCabinetRecordDetailAPI?assetId='+data.assetId+'&type='+"edit";
            //多窗口模式，层叠置顶
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '查看物资记录详情'
                ,area: ['700px', '760px']
                ,shade: 0 //不显示遮罩
                ,maxmin: true //最大最小化  默认不显示最大小化按钮
                ,offset: 'auto'//坐标  默认：垂直水平居中
                ,content: url
                ,closeBtn: 1 //关闭按钮  默认1
                ,zIndex: layer.zIndex //层叠顺序
                ,success: function(layero,index){
                    layer.setTop(layero); //置顶当前窗口
                    layer.full(index); //最大化
                }
            });
        }else if(obj.event === 'show'){
            var url = contextPath + '/lims/api/material/assetCabinetAccessRecordDetailAPI?id='+data.assetId;
            //多窗口模式，层叠置顶
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '查看物资出入库记录'
                ,area: ['700px', '760px']
                ,shade: 0 //不显示遮罩
                ,maxmin: true //最大最小化  默认不显示最大小化按钮
                ,offset: 'auto'//坐标  默认：垂直水平居中
                ,content: url
                ,closeBtn: 1 //关闭按钮  默认1
                ,zIndex: layer.zIndex //层叠顺序
                ,success: function(layero,index){
                    layer.setTop(layero); //置顶当前窗口
                    layer.full(index); //最大化
                }
            });
        }
    });
});