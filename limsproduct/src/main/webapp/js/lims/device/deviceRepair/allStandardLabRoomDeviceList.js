var contextPath = $("meta[name='contextPath']").attr("content");
var deviceId = undefined;
$(document).ready(function () {
    //初始化表格,动态从服务器加载数据
    var url = contextPath + "/lims/api/deviceRepair/getDeviceRepairList";
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: url,
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParams: queryParams,
        //表格显示条纹
        striped: true,
        cache: false,
        toolbar: "#toolbar",
        //启动分页
        pagination: true,
        //是否启用排序
        sortable: true,
        silentSort: true,
        //排序方式
        sortOrder: "asc",
        sortName: 'deviceName',
        //是否显示所有的列（选择显示的列）
        showColumns: true,
        showRefresh: true,
        //每页显示的记录数
        pageSize: 15,
        //当前第几页
        pageNumber: 1,
        //记录数可选列表
        pageList: [5, 10, 15, 20, 25],
        //是否启用查询
        search: false,
        searchAlign: 'left',
        //是否启用详细信息视图
        detailView: false,
        //表示服务端请求
        sidePagination: "server",
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "limit",
        //json数据解析
        responseHandler: function (res) {
            return {
                "rows": res.rows,
                "total": res.total
            };
        },
        //数据列
        columns: [{
            //field: 'Number',//可不加
            title: '序号',//标题  可不加
            width: "5%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "设备编号",
            field: "deviceNumber",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.deviceNumber;
            }
        }, {
            title: "设备名称",
            field: "deviceName",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.deviceName;
            }
        }, {
            title: "设备型号",
            field: "devicePattern",
            width: "10%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.devicePattern;
            }
        }, {
            title: "价格",
            field: "devicePrice",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.devicePrice
            }
        }, {
            title: "操作",
            field: "empty",
            width: "11%",
            formatter: function (value, row, index) {
                var result = "";
                var deviceNumber = row.deviceNumber;
                if(deviceId == undefined || deviceId != row.id) {
                    result += "<input type='radio' name='selectDevice' onclick='getLabRoomDevice(" + row.labAddress + ", "+row.id+")' value='" + deviceNumber + "'/>";
                }else{
                    result += "<input type='radio' name='selectDevice' onclick='getLabRoomDevice(" + row.labAddress + ", "+row.id+")' value='" + deviceNumber + "' checked='checked'/>";
                    deviceId = undefined;
                }
                return result;
            }
        }]
    });
    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);

    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    });

    $("#labRoomName").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    });
});

//得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        // limit: params.limit,   //页面大小
        labRoom: $("#labRoomName").val(),
        deviceId: deviceId,
        offset: params.offset,  //页码
        limit: params.limit,
        search: $("#search").val(),
        sort: params.sort,
        order: params.order,
        length: 7
    };
    return temp;
};

function refreshBootstrapTable() {
    var url = contextPath + "/lims/api/deviceRepair/getDeviceRepairList";
    var opt = {
        url: url,
        silent: true,
        query: {
            type: 1,
            level: 2
        }
    };
    $("#table_list").bootstrapTable('refresh', opt);
}

function changeLabRoom(){
    if($("#labRoomName").val() == "") {
        //$("#placeName").removeAttr("disabled");
        document.getElementById("placeName").style.display="";
    }
    else {
        $("#placeName").val("");
        //$("#placeName").attr({"disabled": "disabled"});
        document.getElementById("placeName").style.display="none";
    }
}

function getLabRoomDevice(id, checkId) {
    if($("#labRoomName").val() == "") {
        var sel = document.getElementById('labRoomName');
        for(var i=0;i<sel.options.length;i++)
        {
            var s = sel.options[i];
            if(id==s.value)
            {
                s.selected=true;
            }
        }
        $("#labRoomName").trigger('liszt:updated');
        $("#labRoomName").chosen();
        deviceId = checkId;
        var params = $("#table_list").bootstrapTable('getOptions');
        $("#table_list").bootstrapTable('refresh', params);
    }
}
