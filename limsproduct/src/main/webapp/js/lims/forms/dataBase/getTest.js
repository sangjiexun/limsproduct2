var contextPath = $("meta[name='contextPath']").attr("content");
var urlPlan = "http://localhost:8085/reportserver/forms/dataBase/getTest";
$(document).ready(function () {
    // 页面参数传递
    getTimetablePlanView();
});

//得到查询的参数
function queryParams(params) {
    var arr = new Object();
    arr.termId = 17;
    // arr.offset = params.offset;
    // arr.limit = params.limit;
    // arr.search = $("#search").val();
    // arr.sort= params.sort;
    // arr.order= params.order;
    var arrs = JSON.stringify(arr);
    return arrs;
};

// 排课视图中不保留调课完成后的管理类操作
function getTimetablePlanView() {
    //获取jwt认证，获取token
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "post",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        //获取数据的Servlet地址
        url: urlPlan,
        ajaxOptions:{
            headers:{Authorization: getJWTAuthority()}
        },
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
        //sortOrder: "asc",
        //sortName: 'courseName',
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
                // return row.courseNumber;
            }
        }, {
            title: "实验室名称",
            field: "lab_name",
            width: "8%",
            sortable: true,
        }, {
            title: "日期",
            field: "class_date",
            width: "5%",
            sortable: true
        }, {
            title: "课程",
            field: "course_name",
            width: "20%",
        }, {
            title: "项目",
            field: "item_name",
            width: "35%",
        }, {
            title: "上课教师",
            field: "teacher",
            width: "5%",
            sortable: true,
        }, {
            title: "上课人数",
            field: "user_num",
            width: "5%",
        }, {
            title: "操作",
            width: "19%",
            field: "empty",
        }]
    });
    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);

    $("#term").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        params.ajaxOptions.headers.Authorization =getJWTAuthority();
        params.silent=true;
        $("#table_list").bootstrapTable('refresh', params);
    })
    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        params.ajaxOptions.headers.Authorization =getJWTAuthority();
        params.silent=true;
        $("#table_list").bootstrapTable('refresh', params);
    })

}

function getJWTAuthority() {
    var authorization ="";
    initDirectoryEngine({
        getHostsUrl:contextPath+"/shareApi/getHosts",
        getAuthorizationUrl:contextPath+"/shareApi/getAuthorization"
    });
    getAuthorization({
        async:false,
        success:function(data){
            authorization =data;
        }
    });
    return authorization;
}

