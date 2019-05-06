var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function () {
    document.cookie = "term=NONE";// 判断默认学期
    //初始化表格,动态从服务器加载数据
    var url = contextPath + "/lims/preCourse/getPreCourseList";
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
       /* sortOrder: "asc",
        sortName: 'deviceName',*/
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
            width: "5%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "课程信息",
            field: "courseName",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName;
            }
        }, {
            title: "所属部门",
            field: "academyName",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.academyName;
            }
        }, {
            title: "上课人数",
            field: "stuNumber",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.stuNumber;
            }
        }, {
            title: "课程安排",
            field: "preTimetableSchedule",
            width: "10%",
            formatter: function (value, row, index) {
                var preTimetableScheduleVO = row.preTimetableScheduleVO;
                var rt = '';
                if(preTimetableScheduleVO != null) {
                    rt += '<table border="1"><tr><td width="20%">星期</td><td width="20%">节次</td><td width="20%">周次</td></tr>';
                    for (var i = 0, len = preTimetableScheduleVO.length; i < len; i++) {
                        rt += "<tr><td>" + preTimetableScheduleVO[i].startWeekDay + "</td><td>" + preTimetableScheduleVO[i].startClass + "-" + preTimetableScheduleVO[i].endClass + "</td><td>" + preTimetableScheduleVO[i].startWeek + "-" + preTimetableScheduleVO[i].endWeek + "</td></tr>";
                    }
                    rt += '</table>';
                }
                return rt;
            }
        }, {
            title: "授课教师",
            field: "teacher",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.teacher
            }
        }, {
            title: "指导教师",
            field: "tutor",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.tutor
            }
        }, {
            title: "预排课房间",
            field: "preLabRoomList",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                var result = "";
                var preLabRoomListVO = row.preLabRoomListVO;
                if(preLabRoomListVO != null) {
                    for (var i = 0, len = preLabRoomListVO.length; i < len; i++) {
                        result += "<a href='javascript:;' onclick='viewPreLabRoom(" + preLabRoomListVO[i].id + ")'>"+preLabRoomListVO[i].roomName+"</a><br>";
                    }
                }
                return result;
            }
        }, {
            title: "操作",
            field: "empty",
            width: "11%",
            formatter: function (value, row, index) {
                var result = "";
                if(row.state == 0){
                    result += "<a href='" + contextPath + "/lims/preCourse/changePreCourseState?id=" + row.id + "' class='btn btn-xs green' title='发布'>发布</a>" +
                        "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick='editPreCourse(" + row.id + ")'>编辑</a>";
                }
                result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick='viewPreCourse(" + row.id + ")'>查看</a>" +
                    "<a href='" + contextPath + "/lims/preCourse/deletePreCourse?id=" + row.id + "' class='btn btn-xs green' title='删除'>删除</a>";
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

    $("#term").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
});

//得到查询的参数
function queryParams(params) {
    t_start=document.cookie.indexOf("term=");
    t_end=document.cookie.indexOf(";",t_start);
    var iterm =document.cookie.substring(t_start,t_end);
    if(iterm.split("=")[1]=='NONE'){
        $("#term").val($("#termId").val());
        document.cookie = "term=YES";
    }
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        termId: $("#term").val(),
        isMy: $("#isMy").val(),
        status: $("input[name='view_status']:checked").val(),
        offset: params.offset,  //页码
        limit: params.limit,
        search: $("#search").val(),
        sort: params.sort,
        order: params.order,
        length: 7
    };
    return temp;
};

//刷新
function refreshBootstrapTable() {
    var url = contextPath + "/lims/preCourse/getPreCourseList";
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

/*
*查看房间
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

/*
*新建
*/
function newMyPreCourse(){
    var index = layer.open({
        type: 2,
        title: '新建',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/preCourse/newMyPreCourse'
    });
    layer.full(index);
}
/*
*编辑
*/
function editPreCourse(id){
    var index = layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/preCourse/editPreCourse?id='+id
    });
    layer.full(index);
}

/*
*查看
*/
function viewPreCourse(id){
    var index = layer.open({
        type: 2,
        title: '查看',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/preCourse/viewPreCourse?id='+id
    });
    layer.full(index);
}
