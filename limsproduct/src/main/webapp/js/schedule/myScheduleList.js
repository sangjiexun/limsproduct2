var contextPath = $("meta[name='contextPath']").attr("content");
// 请求链接
var url = '';
$(document).ready(function () {
    url = $("#zuulServerUrl").val()+"/timetable-service/api/schedule/apiTest";
    scheduleList();
});

//得到查询的参数
function queryParams(params) {
    var arr = new Object();
    arr.termId = $("#term").val();
    var arrs = JSON.stringify(arr);
    return arrs;
};

// 课程安排列表
function scheduleList() {
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    $("#table_list").bootstrapTable({
        method: "post",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        //获取数据的Servlet地址
        url: url,
        ajaxOptions:{
            headers:{Authorization: getJWTAuthority()}
        },
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
            title: '星期/节次',//标题  可不加
            field: "lesson",
            width: "3%",
            formatter: function (value, row) {
                return row.lesson;
            }
        },{
            title: '周一',//标题  可不加
            field: "mon_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.mon_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '周二',//标题  可不加
            field: "tues_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.tues_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '周三',//标题  可不加
            field: "wed_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.wed_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '周四',//标题  可不加
            field: "thur_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.thur_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '周五',//标题  可不加
            field: "fri_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.fri_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '周六',//标题  可不加
            field: "sat_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.sat_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '周日',//标题  可不加
            field: "sun_sched",
            width: "13%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td>序号</td><td>课程</td><td>实验室</td><td>项目</td><td>教师</td></tr>';
                var courseInfo1 = row.sun_sched;
                for (var i = 0, len = courseInfo1.length; i < len; i++) {
                    var j = i+1;
                    rt += "<tr><td>"+j+"</td><td>" + courseInfo1[i].course_name + "</td><td>" + courseInfo1[i].lab_name + "</td><td>" + courseInfo1[i].items + "</td><td>" + courseInfo1[i].teachers + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        },{
            title: '',//标题  可不加
            field: " ",
            width: "0%",
        }]
    });
    //默认展开
    // $("#table_list").bootstrapTable('expandRow', 1);

    /*$("#term").on("change", function () {
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
    })*/
};

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
};