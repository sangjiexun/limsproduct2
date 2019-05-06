var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function () {
    getPreLabRoomList()
});

//得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        offset: params.offset,  //页码
        limit: params.limit,
        search: $("#search").val(),
        sort: params.sort,
        order: params.order,
        length: 6
    };
    return temp;
};

function refreshBootstrapTable() {
    var url = contextPath + "/lims/preCourse/apiPreLabRoomListByPage";
    var opt = {
        url: url,
        silent: true
    };
    $("#table_list").bootstrapTable('refresh', opt);
}

function getPreLabRoomList() {
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    var url = contextPath + "/lims/preCourse/apiPreLabRoomListByPage";
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
            title: '序号',//标题  可不加
            width: "3%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "实验室名称",
            field: "roomName",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.roomName;
            }
        }, {
            title: "容量",
            field: "capaRange",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.capaRange;
            }
        }, {
            title: "房间布局类型",
            field: "roomType",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.roomType;
            }
        }, {
            title: "学院名称",
            field: "academyName",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.academyName;
            }
        }, {
            title: "操作",
            width: "5%",
            formatter: function (value, row, index) {
                var result = "";
                result = "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick='viewPreLabRoom(" + row.id + ")'>查看</a>";
                if (row.isAudit == 1) {
                    result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick='editPreLabRoom(" + row.id + ")'>编辑</a>" +
                    "<a href='" + contextPath + "/lims/preCourse/deletePreLabRoom?id=" + row.id + "' class='btn btn-xs green' title='删除'>删除</a>";
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
    })
}

/*
*新建
*/
function newPreLabRoom(){
    var index = layer.open({
        type: 2,
        title: '新建',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/preCourse/newPreLabRoom'
    });
    layer.full(index);
}

/*
*编辑
*/
function editPreLabRoom(id){
    var index = layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/preCourse/editPreLabRoom?id='+id
    });
    layer.full(index);
}

/*
*查看
*/
function viewPreLabRoom(id){
    var index = layer.open({
        type: 2,
        title: '查看',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/preCourse/viewPreLabRoom?id='+id
    });
    layer.full(index);
}



