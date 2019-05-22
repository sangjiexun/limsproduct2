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
    var assetsApplyId=$("#id").val();
    var academyNumber;
    var department;
    var goodsCategory;
    $.ajax({
        url: contextPath + '/lims/api/material/editAssetsApply?id='+assetsApplyId,
        async: false,
        type: "GET",
        contentType: "application/json;charset=UTF-8",
        success:function (data) {
            //初始表单赋值
            form.val('assetsApplyDetail', {
                "date": data.date,
                "applicantUserName": data.applicantUserName,
                "startDate": data.startDate,
                "endDate": data.endDate,
                "academyNumber": data.academyNumber,
                "department": data.department,
                "goodsCategory": data.goodsCategory,
                "price": data.price,
                "rejectReason": data.rejectReason,
            });
            academyNumber=data.academyNumber;
            department=data.department;
            goodsCategory=data.goodsCategory;
            if(data.status==='0'){
                $("#audit").hide();
            }
            if(data.status!=='3'){
                $("#reject").hide();
            }
        },
        error:function () {
            if(assetsApplyId!=""){
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

    //获取审核标志位
    $.ajax({
        url: contextPath+'/lims/api/material/getAssetsApplyAuditFlag?id='+assetsApplyId,
        dataType: 'json',
        type: 'get',
        success: function (data) {
            if(data!==true){
                $("#audit").hide();
            }
        }
    });
    var tableIns=table.render({
        elem: '#assetsList',
        url: '../material/assetsApplyItemList', //数据接口
        where: {id: assetsApplyId},
        title: '物资申购条目',
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
    //审核通过
    var active = {
        admitAssetsApply: function() {
            $.ajax({
                url: contextPath + '/lims/api/material/changeAssetsApplyStatus?id='+assetsApplyId+'&&result=pass',
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
    //审核拒绝
    var active2 = {
        rejectAssetsApply: function() {
            var realURL=contextPath + '/lims/api/material/rejectAssetsRelatedProcess?id='+assetsApplyId+'&&type=Apply';
            var index = layer.open({
                type: 2 //此处以iframe举例
                ,
                title: '请填写拒绝原因',
                area: ['450px', '300px'],
                shade: 0,
                maxmin: true,
                content: realURL,
                zIndex: layer.zIndex //重点1
                ,
            });
        }
    };
    $('.admit_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
    $('.reject_btn').on('click', function() {
        var othis = $(this),
            method = othis.data('method');
        active2[method] ? active2[method].call(this, othis) : '';
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
