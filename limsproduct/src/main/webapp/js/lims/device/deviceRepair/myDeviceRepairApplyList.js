var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function () {
    //初始化表格,动态从服务器加载数据
    var url = contextPath + "/lims/api/deviceRepair/getDeviceRepairApplyList";
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
            width: "8%",
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
            title: "设备价格",
            field: "devicePrice",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.devicePrice;
            }
        }, {
            title: "报修地点",
            field: "labAddress",
            width: "10%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.labAddress;
            }
        }, {
            title: "报修人",
            field: "reportUser",
            width: "10%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.reportUser;
            }
        }, {
            title: "预计金额",
            field: "expectAmount",
            width: "10%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.expectAmount;
            }
        }, {
            title: "报修描述",
            field: "content",
            width: "10%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.content;
            }
        },{
            title: "报修时间",
            field: "createDate",
            width: "8%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.createDate;
            }
        }, {
            title: "状态",
            field: "auditStage",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                if(row.status == 0){
                    return "未提交";
                }else if(row.status == 1) {
                    var result = "";
                    $.ajax({
                        url: contextPath + "/deviceRepair/getCurrentStatus",
                        dataType: "text",
                        type: "post",
                        async: false,
                        data: {
                            appId: row.id
                        },
                        success: function (data) {
                            result = data;
                        }
                    });
                    return result;
                }else if(row.status == 2) {
                    return "审核通过";
                }else if(row.status == 3) {
                    var result = "";
                    $.ajax({
                        url: contextPath + "/deviceRepair/getRejectedReason",
                        dataType: "text",
                        type: "post",
                        async: false,
                        data: {
                            appId: row.id
                        },
                        success: function (data) {
                            result = data;
                        }
                    });
                    return result;
                }else if(row.status == 4) {
                    return "已维修";
                }else if(row.status == 5) {
                    return "未维修";
                }else if(row.status == 6) {
                    return "已填写";
                }else if(row.status == 7) {
                    return "已入账";
                }else if(row.status == 8){
                    return "已维修";
                }
            }
        }, {
            title: "操作",
            field: "empty",
            width: "11%",
            formatter: function (value, row, index) {
                var result = "";
                // 未提交
                if(row.status == 0) {
                    if (row.baseActionAuthDTO.deleteActionAuth) {
                        result += "<a href='javascript:;' " +
                            "class='btn btn-xs red' title='删除'" +
                            "onclick='deleteDeviceRepair(" +
                            row.id +
                            ")'>" +
                            "<span class='glyphicon glyphicon-plus'>删除</span></a>&nbsp;";
                    }
                    if (row.baseActionAuthDTO.updateActionAuth) {
                        result += "<a href='javascript:;' " +
                            "class='btn btn-xs green' title='编辑' " +
                            "onclick='editDeviceRepair(" +
                            row.id +
                            ")'>" +
                            "<span class='glyphicon glyphicon-plus'>编辑</span></a>&nbsp;";
                        result += "<a href='javascript:;' " +
                            "class='btn btn-xs green' title='提交'" +
                            "onclick='submitDeviceRepair(" + row.id + ")'>" +
                            "<span class='glyphicon glyphicon-check'>提交</span></a>&nbsp;";
                    }
                }
                // 审核中
                else if(row.status == 1){
                    if (row.baseActionAuthDTO.auditActionAuth) {
                        result += "<a href='javascript:;' " +
                            "class='btn btn-xs green' title='审核'" +
                            "onclick='auditDeviceRepair(" +
                            row.id +
                            ")'>" +
                            "<span class='glyphicon glyphicon-check'>审核</span></a>&nbsp;";
                    }
                    if(row.baseActionAuthDTO.publicActionAuth){

                    }
                }
                // else if(row.status == 2){
                //     if(row.baseActionAuthDTO.addActionAuth){
                //         result += "<button class='btn btn-xs green' " +
                //             "title='确认'  " +
                //             "onclick=\"confirmDeviceRepair(" +
                //             row.id +
                //             ")\" >" +
                //             "<span class='glyphicon glyphicon-plus'>确认</span></button>";
                //     }
                // }
                else if(row.status == 2){
                    if(row.baseActionAuthDTO.addActionAuth){
                        result += "<button class='btn btn-xs green' " +
                            "title='验收'  " +
                            "onclick=\"acceptanceDeviceRepair(" +
                            row.id +
                            ")\" >" +
                            "<span class='glyphicon glyphicon-plus'>验收</span></button>";
                    }
                }
                else if(row.status == 4){
                    if(row.baseActionAuthDTO.addActionAuth){
                        result += "<button class='btn btn-xs green' " +
                            "title='填写'  " +
                            "onclick=\"writeDeviceRepair(" +
                            row.id +
                            ")\" >" +
                            "<span class='glyphicon glyphicon-plus'>填写</span></button>";
                    }
                }
                else if(row.status == 6){
                    if(row.baseActionAuthDTO.addActionAuth){
                        result += "<button class='btn btn-xs green' " +
                            "title='入账'  " +
                            "onclick=\"recordDeviceRepair(" +
                            row.id +
                            ")\" >" +
                            "<span class='glyphicon glyphicon-plus'>入账</span></button>";
                    }
                }
                // 设备维修查看
                if (row.baseActionAuthDTO.searchActionAuth) {
                    result += "<a href='javascript:;' " +
                        "class='btn btn-xs green' title='查看' " +
                        "onclick='viewDeviceRepair(" +
                        row.id +
                        ")'>" +
                        "<span class='glyphicon glyphicon-ok'>查看</span></a>&nbsp;";
                }
                if(row.status > 0 && row.baseActionAuthDTO.publicActionAuth) {
                    result += "<a href='javascript:;' " +
                        "class='btn btn-xs green' title='导出' " +
                        "onclick='exportDeviceRepairApply(" +
                        row.id +
                        ")'>" +
                        "<span class='glyphicon glyphicon-ok'>导出</span></a>&nbsp;";
                }
                return result;
            }
        }]
    });
    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);

    $("#academyMenu").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
});

//得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        // limit: params.limit,   //页面大小
        auditStage: $("#auditStage").val(),
        offset: params.offset,  //页码
        limit: params.limit,
        search: $("#search").val(),
        sort: params.sort,
        order: params.order,
        starttime: $("#starttime").val(),
        endtime: $("#endtime").val(),
        length: 8
    };
    return temp;
}

function refreshBootstrapTable() {
    var url = contextPath + "/lims/api/deviceRepair/getDeviceRepairApplyList";
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

// 提交设备维修申请
function submitDeviceRepair(id) {
    $.ajax({
        url:contextPath + "/deviceRepair/submitDeviceRepair",
        dataType:"text",
        async: false,
        type:'POST',
        data:{id: id},
        success:function(result) {
            if(result == "success") {
                alert("提交成功！");
            }else{
                alert("提交失败！");
            }
        },
        error:function () {
            alert("提交失败");
        }
    });
    refreshBootstrapTable();
}

// 查看设备维修申请
function viewDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/viewDeviceRepairApply?id=" + id;
}

// 删除设备维修申请
function deleteDeviceRepair(id) {
    $.ajax({
        url:contextPath + "/deviceRepair/deleteDeviceRepairApply",
        dataType:"text",
        async: false,
        type:'POST',
        data:{id: id},
        success:function(result) {
            if(result == "success") {
                alert("删除成功！");
            }else{
                alert("删除失败！");
            }
        },
        error:function () {
            alert("删除失败");
        }
    });
    refreshBootstrapTable();
}

// 编辑设备维修申请
function editDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/editDeviceRepairApply?id=" + id;
}

// 审核设备维修申请
function auditDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/auditDeviceRepair?id=" + id;
}

// 确认设备维修
function confirmDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/confirmDeviceRepair?id=" + id;
}

// 验收设备维修
function acceptanceDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/acceptanceDeviceRepair?id=" + id;
}

// 填写设备维修
function writeDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/writeDeviceRepair?id=" + id;
}

// 入账设备维修
function recordDeviceRepair(id) {
    window.location.href = contextPath + "/deviceRepair/recordDeviceRepair?id=" + id;
}

// 导出设备维修申请单
function exportDeviceRepairApply(id) {
    window.location.href = contextPath + "/deviceRepair/exportDeviceRepairApply?id=" + id;
}

// 设备维修统计导出
function exportDeviceRepairCount() {
    document.exportForm.action=contextPath + "/deviceRepair/exportDeviceRepairCount";
    document.exportForm.submit();
}