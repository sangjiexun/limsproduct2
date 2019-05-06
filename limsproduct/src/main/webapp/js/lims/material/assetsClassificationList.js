var contextPath = $("meta[name='contextPath']").attr("content");

layui.config({
    version: '1545041465480' //为了更新 js 缓存，可忽略
});
layui.use(['table','layer'], function(){
    var table = layui.table//数据表格
    var layer = layui.layer;//弹出层
    table.render({
        elem: '#assetsClassificationList' //指定原始表格元素选择器（推荐id选择器）
        ,toolbar: 'table'
        ,height: 400//容器高度
        ,url: $("#contextPath").val()+'/lims/api/material/listAssetsClassification' //数据接口
        ,title:'物资分类列表'
        ,cols: [[ //设置表头
            {field: 'id', title: '序号',type:'numbers', minWidth:'50', align: 'center', fixed: 'left',}
            ,{field: 'cname', title: '分类名称', minWidth:'100',align: 'center' }
            ,{field: 'ename', title: '英文名称', minWidth:'100', align: 'center'}
            // ,{field: 'applyAudit', title: '申领审核', minWidth:'100', align: 'center', }
            // ,{field: 'storageAudit', title: '入库审核', minWidth:'100', align: 'center',}
            // ,{field: 'receiveAudit', title: '领用审核', minWidth:'100', align: 'center', }
            ,{field: 'isNeedReturn', title: '是否归还', minWidth:'100', align: 'center'}
            ,{field: 'info', title: '备注', minWidth:'100', align: 'center' }
            ,{fixed: 'right', title: '操作', width:250, align:'center',toolbar:"#classificationToolbar"}
        ]]
        ,even: true
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 5 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页

        }
        ,limits: [6,10]
        ,limit: 6 //每页默认显示的数量
    });
    //搜索查询
    var active = {
        reload: function(){
            var cname = $('#cname').val(); //传入搜索值
            //执行重载
            table.reload('assetsClassificationList', {
                url :$("#contextPath").val()+'/lims/api/material/listAssetsClassification',
                method:'post',
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                ,where: { //类似于 data
                    cname:cname
                }
            });
        }
    };
    $('#search').on('click', function(){
        var type = $(this).data('type');
        //不能为空验证
        if( $('#cname').val()==""){
            layer.msg('查询数据不能为空！');
            return false;
        }
        active[type] ? active[type].call(this) : '';
    });
    //监听工具条
    table.on('tool(classificationToolbar)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if(layEvent === 'detail'){ //查看
            var url=contextPath + '/lims/api/material/assetClassificationDetailAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '查看物资类别详情',
                area: ['390px', '500px'],
                shade: 0,
                maxmin: true,
                offset: [ //为了演示，随机坐标
                    0.4*($(window).height()-300)
                    ,0.4*($(window).width()-390)
                ],
                content: url,
            });
            layer.full(index);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除吗?', function(index) {
                $.ajax({
                    url: contextPath + '/lims/api/material/deleteAssetsClassification?id='+data.id,
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
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit'){ //编辑
            var url=contextPath + '/lims/api/material/newAssetsClassificationAPI?id='+data.id;
            var index = layer.open({
                type: 2,//type属性表示弹出层的类型
                skin: 'layui-layer-demo', //样式类名
                title: '编辑物资类别',
                offset:'auto',
                closeBtn: 1, //显示关闭按钮
                anim: 2,
                area: ['893px', '600px'],
                shadeClose: true, //开启遮罩关闭
                content: url,
            });
            layer.full(index);
            //同步更新缓存对应的值
            obj.update({
                username: '123'
                ,title: 'xxx'
            });
        }
    });
});
function newClassification() {
    var url=contextPath + '/lims/api/material/newAssetsClassificationAPI';
    var index = layer.open({
        type: 2,//type属性表示弹出层的类型
        skin: 'layui-layer-demo', //样式类名
        title: '新建物资类别',
        offset:'auto',
        closeBtn: 1, //显示关闭按钮
        anim: 2,
        area: ['893px', '600px'],
        shadeClose: true, //开启遮罩关闭
        content: url,
    });
    layer.full(index);
}

