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
            // formatter: function (i,row) {            // 每次加载 checkbox 时判断当前 row 的 id 是否已经存在全局 Set() 里
            //     if($.inArray(row.id,Array.from(overAllIds))!=-1){    // 因为 Set是集合,需要先转换成数组
            //         return {
            //             checked : true               // 存在则选中
            //         }
            //     }
            // }
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

    // $('#table_list').on('uncheck.bs.table check.bs.table check-all.bs.table uncheck-all.bs.table',function(e,rows){
    //     var datas = $.isArray(rows) ? rows : [rows];        // 点击时获取选中的行或取消选中的行
    //     examine(e.type,datas);                                 // 保存到全局 Set() 里
    // });

    var overAllIds = new Set();                // 全局保存选中行的对象

    function examine(type,datas){            // 操作类型，选中的行
        if(type.indexOf('uncheck')==-1){
            $.each(datas,function(i,v){        // 如果是选中则添加选中行的 id
                overAllIds.add(v.id);
            });
        }else{
            $.each(datas,function(i,v){
                overAllIds.delete(v.id);     // 删除取消选中行的 id
            });
        }
    }
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

