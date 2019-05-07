var contextPath = $("meta[name='contextPath']").attr("content");
var businessType = "";
var businessAppUid = "";
$(document).ready(function () {
    businessType = $("#businessType").val();
    businessAppUid = $("#businessAppUid").val();
    switch (businessType) {
        case "StationReservation":
        case "1StationReservation":
        case "2StationReservation":
            getLabRoomStationReservation();
            break;
        default:
    }
});
//得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        // limit: params.limit,   //页面大小
        search: businessAppUid
    };
    return temp;
};
// 教务课程信息
function getLabRoomStationReservation() {
    $("#table_list").bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    var url = contextPath + "/LabRoomReservation/getLabRoomStationReservation";
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
        pagination: false,
        //是否启用查询
        search: false,
        // searchAlign: 'left',
        //是否启用详细信息视图
        detailView: false,
        //表示服务端请求
        sidePagination: "server",
        //json数据解析
        responseHandler: function (res) {
            return {
                "rows": res.rows,
                "total": res.total
            };
        },
        //数据列
        columns: [{
            title: "实验室",
            field: "courseNumber",
            width: "20%",
            formatter: function (value, row, index) {
                return row.labRoomName + "<br/>(" + row.labRoomNumber + ")";
            }
        }, {
            title: "预约日期",
            field: "academyName",
            width: "15%",
            formatter: function (value, row, index) {
                return row.reservationDate;
            }
        }, {
            title: "预约时间",
            field: "coursePlan",
            width: "30%",
            formatter: function (value, row, index) {
                return row.reservationTime;
            }
        }, {
            title: "预约工位数",
            field: "timetableDTOs",
            width: "35%",
            formatter: function (value, row, index) {
                return row.stationCount;
            }
        }, {
            title: "申请人",
            width: "1%",
            field: "empty",
            formatter: function (value, row, index) {
                return row.createUser + "<br/>(" + row.createUsername + ")";
            }
        }]
    });
}