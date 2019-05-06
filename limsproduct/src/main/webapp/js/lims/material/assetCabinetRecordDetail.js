var contextPath = $("meta[name='contextPath']").attr("content");
var cols = null;
var detailCols = [
    {field:'id', title: '序号', fixed: true, type: 'numbers',minwidth: 80,totalRowText: '总数量'}
    ,{field:'cabinetCode',title:'物品柜编号'}
    ,{field:'cabinetName', title: '物品柜名称', }
    ,{field:'stockNumber', title: '物资数量' ,sort: true, totalRow: true}
];
var editCols = [
    {field:'id', title: '序号', fixed: true, type: 'numbers',minwidth: 80,totalRowText: '总数量'}
    ,{field:'cabinetCode',title:'物品柜编号'}
    ,{field:'cabinetName', title: '物品柜名称' }
    ,{field:'stockNumber', title: '物资数量' ,sort: true, totalRow: true                                              }
    ,{fixed: 'right', title:'操作', toolbar: '#assetCabinetRecordDetailToolBar', width:150}
]

layui.use(['form','table','layer','jquery'],function () {
    var data = null;
    var form = layui.form
    ,table = layui.table
    ,layer = layui.layer
    ,$=layui.jquery;
    var assetId = $("#assetId").val();
    var type = $("#type").val();
    var editFilter = null;
    if(type == "detail"){
        cols = detailCols;
    }else{
        cols = editCols;
        $("#add").removeClass('layui-hide');
    }
    $.ajax({
        async:false,
        contentType: "application/json;charset=UTF-8",
        type:"GET",
        url:contextPath + "/lims/api/material/viewAssetCabinetRecordDetails?assetId="+assetId,
        success:function (data1) {
            data = data1;
        },
        error:function () {
            if(assetId!=""){
                alert("后台出了点问题，请重试！");
                return false;
            }
        }
    })
    //表单初始赋值
    form.val("assetCabinetRecordDetail",{
        // "name": "value"
        "cname":data.cname
        ,"cas":data.cas
        ,"categroy":data.categroy
        ,"unit":data.unit
        ,"specifications":data.specifications
        ,"factory":data.factory
        ,"price":data.price
        ,"function":data.function
    })
    //表单更新全部
    layui.form.render();
    //渲染表格
    var tableData = data.assetCabinetRecordDTOList;
    table.render({
        elem: '#assetCabinetRecordDetailTable'
        ,title: '物资柜记录表'
        ,totalRow: true //合计行
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,page: false
        ,data:tableData
        ,cols: [cols]
        ,id: 'assetCabinetRecordDetailTable'
    });

    //监听工具条
    table.on('tool(assetCabinetRecordDetailTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var currData = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    url: contextPath + '/lims/api/material/deleteAssetCabinetRecordDetail?id='+currData.id,
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
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
            });
        }
        else if(obj.event === 'edit'){
            editStandard(currData);
            //表单更新全部
            layui.form.render();
            layer.open({
                type: 1
                , title: '编辑物资记录'
                , area: ['45%', '55%']
                /* , offset: type *///具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                // , id: 'layerDemo1'/* + type*/ //防止重复弹出
                , content: $('#addForm')
                , shade: 0 //不显示遮罩
            });
        }

    });
    //添加物资记录
    $('#addAssetRecord').on('click', function () {
        document.getElementById("addAssetCabinetRecord").reset();
        form.val('addAssetCabinetRecord',{
            "cname":data.cname,
        })
        // var addUrl = contextPath + '/lims/api/material/addAssetCabinetRecordAPI?assetId='+data.assetId+'&cname='+data.cname;
        layer.open({
            type: 1,
            title: '增加物资记录',
            maxmin: true,
            area: ['45%', '55%'],
            shadeClose: false, //点击遮罩关闭
            content: $('#addForm'),
        });
    });

    //表单初始赋值
    form.val('addAssetCabinetRecord',{
        "cname":data.cname,
    })
    $.ajax({
        url:contextPath + "/lims/api/material/getAllAssetsCabinet",
        contentType: "application/json;charset=UTF-8",
        type:"GET",
        success:function(list){
            var cabinetId = document.getElementById("cabinetId");
            for(var i=0;i<list.length;i++){
                var p = list[i];
                var option = document.createElement("option");  // 创建添加option属性
                option.setAttribute("value",p.id); // 给option的value添加值
                option.innerText=p.cabinetName;
                var capacityInput = document.createElement("input");
                capacityInput.setAttribute("value",p.capacity);
                capacityInput.setAttribute("id","capacity"+p.id);
                capacityInput.setAttribute("type","hidden");
                cabinetId.appendChild(option);           //给select添加option子标签
                cabinetId.appendChild(capacityInput);
                form.render("select");            // 刷性select，显示出数据
            }
        },
        error:function () {
            alert("后台出了点问题，请重试！");
            return false;
        }
    });
    layui.form.render();
    var quantity = null;
    $("#stockNumber").click(function() {
        var cabinetSelect = document.getElementById("cabinetId");//定位id(获取select)
        var cabinetIdValue = cabinetSelect.value;
        if (cabinetIdValue != null && cabinetIdValue != "") {
            // quantity = $("#capacity" + cabinetIdValue).val();
            // if (quantity != null && quantity != "" && quantity!="null") {
            //     alert("物资数量不能超过" + quantity + "!");
            //     var quantityDom = document.getElementById("quantity");
            //     quantityDom.setAttribute("placeholder", "物资数量不能超过" + quantity);
            // }
        } else {
            alert("请先选择物品柜！");
        }
    });
    //验证
    // form.verify({
    //     quantity:function(value,item){//value：表单的值、item：表单的DOM对象
    //         if (quantity != null && quantity != "" && quantity!="null") {
    //             if (parseInt(quantity) < parseInt(value)) {
    //                 return '输入的物资数量超过物品柜的剩余数量，请重新输入';
    //             }
    //         }
    //     }
    // });
    //监听提交
    form.on('submit(saveAssetsCabinetRecord)', function(addData){
        var addData = addData.field;
        var object = {
            "assetId":assetId,
            "cabinetId":addData.cabinetId,
            "stockNumber":addData.stockNumber,
        }
        object = JSON.stringify(object);
        // 保存物资记录
        $.ajax({
            url: contextPath + '/lims/api/material/saveAssetsCabinetRecord?id='+addData.id,
            data: object,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (result) {
                if(result=="success") {
                    // layer.close(layer.index); //它获取的始终是最新弹出的某个层，值是由layer内部动态递增计算的
                    layer.closeAll();
                    window.location.reload();
                }else {
                    alert("提交失败！");
                }
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

})

function editStandard(data) {
    console.log(Object.entries(data));
    //回显表单数据
    for (var i = 1; i < Object.entries(data).length; i++) {
        var id = '#' + Object.entries(data)[i][0];
        var text = Object.entries(data)[i][1];
        $(id).val(text);
    }
}
