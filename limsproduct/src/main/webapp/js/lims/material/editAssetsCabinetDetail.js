var contextPath = $("meta[name='contextPath']").attr("content");
//加载form模块
layui.use(['table', 'element','form'], function() {
    var $=layui.jquery;
    var form = layui.form;
    var table = layui.table; //表格
    var serverId;
    form.on('submit(saveAssetCabinet)', function(data){
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        $.ajax({
            url: contextPath + '/lims/api/material/saveAssetsCabinet',
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (flag) {
                console.log(flag);
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
    // 物资类别表单初始赋值
    var id = $("#id").val();
    if(id!==""&&id!=null) {
        $.ajax({
            url: contextPath + '/lims/api/material/editAssetsCabinet?id='+id,
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                //物资详情表单赋值
                form.val('assetCabinetDetail', {
                    "cabinetCode": data.cabinetCode,
                    "cabinetName": data.cabinetName,
                    "type":data.type,
                    "location":data.location,
                    "hardwareType":data.hardwareType,
                    "hardwareIp":data.hardwareIp,
                    "serverId":data.serverId
                });
                serverId=data.serverId
                //重新渲染
                layui.form.render('select');
                if(data.type==='1'){
                    $("#hardware").hide();
                    $("#item").hide();
                }
            },
            error: function () {
                if (id != "") {
                    alert("后台出了点问题，请重试！");
                    return false;
                }
            }
        });
    }
    var tableIns=table.render({
        elem: '#cabinetWareHouseList',
        url: '../material/cabinetWareHouseList', //数据接口
        where: {id: id},
        title: '智能柜柜门',
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
                field: 'wareHouseCode',
                title: '柜门编号',
                minWidth: 130,
                align: 'center',
                sort: true,
            }, {
                fixed: 'left',
                field: 'wareHouseName',
                title: '柜门名称',
                minWidth: 130,
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
        ,id: 'cabinetWareHouseList'
    });
    form.on('select(type)', function(data){
        var type=$("#type").val();
        if(type==1){
            $("#hardware").hide();
            $("#item").hide();
        }else{
            $("#hardware").show();
            $("#item").show();
        }
    });
    //获取物品柜
    $.ajax({
        url: contextPath+'/lims/api/material/commonServerList',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#serverId').append(new Option(item.serverName, item.id));// 下拉菜单里添加元素
                $('#serverId').val(serverId);
            });
            layui.form.render("select");
        }
    });
    //添加物资名录
    var active = {
        addCabinetWareHouse: function() {
            var realURL=contextPath + '/lims/api/material/addCabinetWareHouseAPI?cabinetId='+id;
            //多窗口模式，层叠置顶
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '新增具体柜门信息',
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
});


