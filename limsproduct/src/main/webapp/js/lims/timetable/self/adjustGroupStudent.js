var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl ="";
var url ="";

$(document).ready(function () {
    zuulUrl = $("#zuulServerUrl").val()+contextPath+"/timetable/";
    getGroupStudentView();
});

//得到查询的参数
function queryParams(params) {
    var arr = new Object();
    arr.id = $("#group_id").val();
    var arrs = JSON.stringify(arr);
    return arrs;
};

function getGroupStudentView() {
    $("#table_list").bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    var url = zuulUrl + "api/timetable/manage/apiGroupStudentList";
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "post",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        //获取数据的Servlet地址
        url: url,
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
        pagination: false,
        //是否启用排序
        sortable: false,
        silentSort: false,
        //是否显示所有的列（选择显示的列）
        showColumns: false,
        showRefresh: false,
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
            title: "选择",
            field: "id",
            width: "8%",
            checkbox: true,
        }, {
            title: "序号",
            field: "academyName",
            width: "8%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "学生姓名",
            field: "cname",
            width: "5%",
            formatter: function (value, row, index) {
                return row.cname;
            }
        }, {
            title: "学号",
            field: "username",
            width: "5%",
            formatter: function (value, row, index) {
                return row.username;
            }
        }, {
            title: "学院",
            field: "academyName",
            width: "40%",
            formatter: function (value, row, index) {
                return row.academyName;
            }
        }, {
            title: "",
            width: "1%",
            field: "empty",
        }]
    });

    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);
}

// 调整分组
function adjustToGroup() {
    var in_group = $('input[name="ifselect"]:checked').val();
    // 验证是否选择调进的小组
    if (in_group == null || in_group == '') {
        layer.msg("请选择调进的分组!", { icon: 1 });
        return false;
    }
    // 验证是否选择学生
    var selected = $('#table_list').bootstrapTable('getSelections');
    var ids = new Array();
    for (var i=0;i<selected.length;i++){
        ids[i] = selected[i].username;
    }
    if (ids == null || ids.length == 0) {
        layer.msg("请选择需要调出的学生!", { icon: 1 });
        return false;
    }
    if (confirm('是否确认调整？')) {
        // 保存调整后的学生名单
        var arr = new Object();
        arr.students = ids;
        arr.groupId = $("#group_id").val();
        arr.inGroupId = in_group;
        var arrs = JSON.stringify(arr);

        $.ajax({
            url: zuulUrl + "api/timetable/manage/apiSaveAdjustGroupStudent",
            type: "post",
            contentType: "application/json",
            dataType: "json",
            data: arrs,
            success : function(data,status) {
                layer.msg("名单调整成功!",{
                    icon: 1
                });
            },
            error: function () {
                layer.msg("后台出了点问题，请重试!",{
                    icon: 6
                });
            },
            complete: function () {
                getGroupStudentView();
            }
        });
    }
}

// 添加学生
function addGroupStudent(group_id) {
    var course_no = $("#course_no").val();
    var index = layer.open({
        type : 2,
        title : '添加学生',
        maxmin : true,
        shadeClose : true,
        area: ['600px', '400px'],
        content: contextPath + '/lims/timetable/course/addGroupStudent?course_no='+course_no+'&group_id='+ group_id,
        end: function () {
            getGroupStudentView();
        }
    });
    layer.full(index);
}

// 批量删除
function deleteFromGroup() {
    // 验证是否选择学生
    var selected = $('#table_list').bootstrapTable('getSelections');
    var ids = new Array();
    for (var i=0;i<selected.length;i++){
        ids[i] = selected[i].username;
    }
    if (ids == null || ids.length == 0) {
        layer.msg("请选择需要删除的学生!", { icon: 1 });
        return false;
    }
    if (confirm('是否确认删除？')) {
        // 保存调整后的学生名单
        var arr = new Object();
        arr.students = ids;
        arr.groupId = $("#group_id").val();
        var arrs = JSON.stringify(arr);

        $.ajax({
            url: zuulUrl + "api/timetable/manage/apiSaveAdjustGroupStudent",
            type: "post",
            contentType: "application/json",
            dataType: "json",
            data: arrs,
            success : function(data,status) {
                layer.msg("名单删除成功!",{
                    icon: 1
                });
            },
            error: function () {
                layer.msg("后台出了点问题，请重试!",{
                    icon: 6
                });
            },
            complete: function () {
                getGroupStudentView();
            }
        });
    }
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

