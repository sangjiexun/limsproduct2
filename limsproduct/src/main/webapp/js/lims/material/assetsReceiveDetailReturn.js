var contextPath = $("meta[name='contextPath']").attr("content");

layui.config({
    version: '1545041465480' //为了更新 js 缓存，可忽略
});

layui.use(['laypage', 'layer', 'table', 'element','form','laydate'], function() {
    var laypage = layui.laypage //分页
        ,
        layer = layui.layer //弹层
        ,
        table = layui.table //表格
        ,
        element = layui.element //元素操作
    var form = layui.form;//表单
    var laydate = layui.laydate;//日期
    var laydate2 = layui.laydate;//日期
    laydate.render({
        elem: '#begintime'
    });
    laydate2.render({
        elem: '#endtime'
    });
    var id=$("#id").val();
    var academyNumber;
    var department;
    var goodsCategory;
    var isNeedReturn;
    $.ajax({
        url: contextPath + '/lims/api/material/editAssetsReceive?id='+id,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            //初始表单赋值
            form.val('assetsReceiveDetail', {
                "applicationTime": data.applicationTime,
                "username": data.username,
                "academyNumber": data.academyNumber,
                "department": data.department,
                "goodsCategory": data.goodsCategory,
                "beginTime": data.beginTime,
                "endTime": data.endTime,
                "isNeedReturn": data.isNeedReturn,
            });
            academyNumber=data.academyNumber;
            department=data.department;
            goodsCategory=data.goodsCategory;
            isNeedReturn=data.isNeedReturn;
            if(data.status!=='1'){
                $("#check").hide();
            }
            if(data.status!=='2'){
                $("#confirm").hide();
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
    var tableIns=table.render({
        elem: '#assetsList',
        url: '../material/assetsReceiveItemList', //数据接口
        where: {id: id},
        title: '物资申领条目',
        page: true, //开启分页
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
                title: '申领数量',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'returnAmount',
                title: '归还数量',
                minWidth: 100,
                align: 'center',
                sort: true,
            }, {
                field: 'stockNumber',
                title: '当前库存柜余量',
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
    table.on('tool(assetsReceiveList)', function(obj) { //注：tool 是工具条事件名，parenthead 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,
            layEvent = obj.event; //获得 lay-event 对应的值
        console.log(data);
        //余料归还
        if(obj.event === 'return') {
            var realURL=contextPath + '/lims/api/material/returnRemainAssetsAPI?id='+data.id;
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '余料归还',
                area: ['390px', '260px'],
                shade: 0,
                maxmin: true,
                content: realURL,
                zIndex: layer.zIndex //重点1
                ,
            });
        };
    });
    //确认余料归还
    var active = {
        confirmReturnAssetsReceive: function() {
            $.ajax({
                url: contextPath + '/lims/api/material/confirmReturnAssetsReceive?id='+id,
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
        }
    };
    $('.return_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
});
