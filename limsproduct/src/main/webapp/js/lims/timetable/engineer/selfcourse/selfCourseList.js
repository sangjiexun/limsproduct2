var contextPath = $("meta[name='contextPath']").attr("content");
var selfBatch = $("meta[name='selfBatch']").attr("content");
var selfNoBatch = $("meta[name='selfNoBatch']").attr("content");
$(document).ready(function () {
    document.cookie = "term=NONE";// 判断默认学期
    c_start=document.cookie.indexOf("type=");
    c_end=document.cookie.indexOf(";",c_start);
    var type =document.cookie.substring(c_start,c_end);
    if(c_start==-1){
        getTimetablePlanView();
    }else if(type.split("=")[1]=='PLAN'){
        getTimetablePlanView();
    }else if(type.split("=")[1]=='MANAGE'){
        getTimetableMangerView();
    }
    for (var i=0;i<25;i++){
        MergeCell("tb"+i,0,25,0);
    }
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
        // limit: params.limit,   //页面大小
        termId: $("#term").val(),
        status: $("input[name='view_status']:checked").val(),
        offset: params.offset,  //页码
        limit: params.limit,
        search: $("#search").val(),
        sort: params.sort,
        order: params.order,
        length: 6
    };
    return temp;
};

/*
*查看学生名单
*/
function schoolCourseStudents(term, selfId) {
    var index = layer.open({
        type: 2,
        title: '查看学生选课情况',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/school/schoolcoursestudent/schoolCourseStudnetList?term=' + term + '&selfId=' + selfId
    });
    layer.full(index);
}

/*
*查看排课弹出窗口
*/
function viewTimetableInfo(selfId) {
    var index = layer.open({
        type: 2,
        title: '查看排课情况',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/common/viewTimetableInfo?style=1&selfId=' + selfId
    });
    layer.full(index);
}

/*
*二次不分批排课弹出窗口
*/
function newSelfReNoGroupCourse(term, selfId) {
    var index = layer.open({
        type: 2,
        title: '二次自主不分批排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/selfcourse/newSelfReTimetableCourse?currpage=1&flag=0&timetableStyle=5&selfId=' + selfId + "&term=" + term
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}

/*
*二次不分批排课弹出窗口
*/
function newSelfReGroupCourse(term, selfId) {
    var index = layer.open({
        type: 2,
        title: '二次自主分批排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/selfcourse/newSelfReGroupCourse?currpage=1&flag=0&selfId=' + selfId + "&term=" + term + "&groupId=-1"
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}

function newSelfCourse(selfId) {
    var url = contextPath + '/lims/timetable/engineer/selfcourse/newSelfCourse?id=-1&term=-1';
    if(selfId!=null){
        url = contextPath + '/lims/timetable/engineer/selfcourse/newSelfCourse?id='+ selfId +'&term=-1';
    }
    var index = layer.open({
        type: 2,
        title: '教学班管理',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: url,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}


function publicTimetable(timetableStyle, selfId, status) {
    var arr = new Object();
    arr.selfId = selfId;
    arr.timetableStyle = timetableStyle;
    arr.status = status;
    var arrs = JSON.stringify(arr);
    $.ajax({
        url: contextPath + "/lims/api/timetable/common/apiTimetablePublic",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        type: "post",
        async: false,
        data: arrs,
        success: function (json) {
            refreshBootstrapTable();
        }
    });
    //window.parent.location.reload();
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

function deleteTimetable(term, selfId) {
    if (confirm('是否确认删除？')) {
        $.ajax({
            url: contextPath + "/lims/api/timetable/manage/apiDeleteTimetableBySelfId?term=" + term + "&selfId=" + selfId,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            type: "post",
            async: false,
            success: function (json) {
            }
        });
        refreshBootstrapTable();
    }
}

function deleteTimetableSelfCourse(selfId) {
    if (confirm('是否确认删除？')) {
        $.ajax({
            url: contextPath + "/lims/api/timetable/self/apiDeleteTimetableSelfCourse?selfId=" + selfId,
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            type: "post",
            async: false,
            success: function (json) {
            }
        });
        refreshBootstrapTable();
    }
}

function refreshBootstrapTable() {
    var url = "";
    var view_radio =$("input[name='view_radio']:checked").val();
    if (view_radio=="timetable") {
        url = contextPath + "/lims/api/timetable/self/apiSelfCourseListByPage";
    }else{
        url = contextPath + "/lims/api/timetable/self/apiSelfCourseManageByPage";
    }
    var opt = {
        silent: true
    };
    $("#table_list").bootstrapTable('refresh', opt);
}

function getTimetablePlanView() {
    document.cookie = "type=PLAN";
    document.getElementById("div_status2").style.display="";
    document.getElementById("div_status5").style.display="";
    document.getElementById("div_status6").style.display="";
    document.getElementById("div_status3").style.display="none";
    document.getElementById("div_status4").style.display="none";
    document.getElementById("div_status7").style.display="none";
    document.getElementById("view_status1").checked="checked";
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    var url = contextPath + "/lims/api/timetable/self/apiSelfCourseListByPage";
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
            //field: 'Number',//可不加
            title: '序号',//标题  可不加
            width: "5%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "课程信息",
            field: "courseNumber",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "<br>(" + row.courseNumber + ")";
            }
        }, {
            title: "所属学院",
            field: "academyName",
            width: "10%",
            sortable: true
        }, {
            title: "已排课表",
            field: "timetableDTOs",
            width: "38%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                    rt += '<table id="tb'+index+'" border="1"><tr><td width="13%">批/组</td><td width="7%">星期</td><td width="7%">节次</td><td width="7%">周次</td><td width="18%">实验室</td><td width="15%">授课教师</td><td width="18%">实验项目</td><td width="7%">名单</td></tr>';
                }
                if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="7%">星期</td><td width="7%">节次</td><td width="7%">周次</td><td width="32%">实验室</td><td width="15%">授课教师</td><td width="32%">实验项目</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableDTOs.length; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.selfId + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+"," + timetableDTOs[i].groupStudents+",8)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupNumbers + "/" + timetableDTOs[i].groupStudents + "</span></button>&nbsp;";
                        rt += "<tr><td>" + timetableDTOs[i].batchName +"/" + timetableDTOs[i].groupName + "</td><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers + "</td><td>" + timetableDTOs[i].items + "</td><td>" + result + "</td></tr>";
                        rt += "<tr id=" + group_div_reality + " style=\"display: none;\"></tr>"
                    }
                    if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                        rt += "<tr><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers + "</td><td>" + timetableDTOs[i].items + "</td></tr>";
                    }
                }
                if (timetableDTOs.length > 0) {
                    rt += '</table>';
                }
                return rt;
            }
        }, {
            title: "上课教师",
            field: "cname",
            width: "6%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.cname + "<br>(" + row.username + ")";
            }
        }, {
            title: "学生名单",
            field: "student",
            width: "5%",
            formatter: function (value, row, index) {

                var result = "";
                result = "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"timetableCourseStudents(" + row.termId + ",'" + row.selfId + "')\" >名单(" + row.student + ")</a>";
                return result;
            }
        }, {
            title: "操作",
            width: "15%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if (!row.baseActionAuthDTO.addActionAuth && !row.baseActionAuthDTO.deleteActionAuth && !row.baseActionAuthDTO.updateActionAuth && !row.baseActionAuthDTO.publicActionAuth) {
                    result += "<b>操作未授权</b>";
                } else if (row.timetableStatus == 0) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table><tr><td height=\"25px\"><div style=\"height:20px;\">";
                        if (selfNoBatch == "true") {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReNoGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-plus'>不分批排</span></a>&nbsp;";
                        }
                        if (selfBatch == "true") {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-plus'>分批排课</span></a>&nbsp;";
                        }
                        result += "</td></tr></table>";

                    }
                } else if (row.timetableStatus == 2) {
                    if (row.baseActionAuthDTO.publicActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a>&nbsp;";

                    }
                } else if (row.timetableStatus == 10) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        if (row.timetableStyle == 5) {
                            result += "<table><tr><td height=\"25px\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReNoGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-edit'>不分批调</span></a>&nbsp;";
                        } else if (row.timetableStyle == 6) {
                            result += "<table><tr><td height=\"25px\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-edit'>分批调整</span></a>&nbsp;";
                        } else {
                            result += "<table><tr><td height=\"25px\">&nbsp;";

                        }
                        if (row.timetableStyle == 5||row.timetableStyle == 6) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable('"+row.timetableStyle+"','" + row.selfId + "',2)\" ><span class='glyphicon glyphicon-check'>调课完成</span></a></div></td></tr>&nbsp;";
                            result += "<tr><td height=\"25px\"><div style=\"height:20px;\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a></div></td></tr></table>&nbsp;";
                        }
                    }
                }else if(row.timetableStatus == 4){
                    result += "审核拒绝";
                }
                return result;
            }
        }]
    });
    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);

    $("#term").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })

}

function getTimetableMangerView() {
    document.cookie = "type=MANAGE";
    document.getElementById("div_status2").style.display="none";
    document.getElementById("div_status5").style.display="none";
    document.getElementById("div_status6").style.display="none";
    document.getElementById("div_status3").style.display="";
    document.getElementById("div_status4").style.display="";
    document.getElementById("div_status7").style.display="";
    document.getElementById("view_status1").checked="checked";
    $("#table_list").bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    var url = contextPath + "/lims/api/timetable/self/apiSelfCourseManageByPage";
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
            //field: 'Number',//可不加
            title: '序号',//标题  可不加
            width: "5%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "课程信息",
            field: "courseNumber",
            width: "8%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "(" + row.courseNumber + ")";
            }
        }, {
            title: "所属学院",
            field: "academyName",
            width: "8%",
            sortable: true
        }, {
            title: "已排课表",
            field: "timetableDTOs",
            width: "40%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                    rt += '<table id="tb'+index+'" border="1"><tr><td width="10%">批/组</td><td width="7%">星期</td><td width="7%">节次</td><td width="7%">周次</td><td width="18%">实验室</td><td width="15%">授课教师</td><td width="18%">实验项目</td><td width="7%">名单</td></tr>';
                }
                if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="7%">星期</td><td width="7%">节次</td><td width="7%">周次</td><td width="32%">实验室</td><td width="15%">授课教师</td><td width="32%">实验项目</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableDTOs.length; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.selfId + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+"," + timetableDTOs[i].groupStudents+",8)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupNumbers + "/" + timetableDTOs[i].groupStudents + "</span></button>&nbsp;";
                        rt += "<tr><td>" + timetableDTOs[i].batchName +"/" + timetableDTOs[i].groupName + "</td><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers + "</td><td>" + timetableDTOs[i].items + "</td><td>" + result + "</td></tr>";
                        rt += "<tr id=" + group_div_reality + " style=\"display: none;\"></tr>"
                    }
                    if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                        rt += "<tr><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers + "</td><td>" + timetableDTOs[i].items + "</td></tr>";
                    }
                 }
                if (timetableDTOs.length > 0) {
                    rt += '</table>';
                }
                return rt;
            }
        }, {
            title: "学生名单",
            field: "student",
            width: "5%",
            formatter: function (value, row, index) {

                var result = "";
                result = "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"timetableCourseStudents(" + row.termId + ",'" + row.selfId + "')\" >名单(" + row.student + ")</a>";
                return result;
            }
        }, {
            title: "排课操作",
            width: "15%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if (row.timetableStatus == 1) {
                    result += "排课已发布";
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "|<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable("+row.timetableStyle+",'" + row.selfId + "',10)\" ><span class='glyphicon glyphicon-ok'>调课</span></a>&nbsp;";
                    }
                    if (row.baseActionAuthDTO.selectGroupActionAuth && row.timetableStyle == 6) {
                        if (row.isSelect == 1) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-check'>学生选课</span></a>&nbsp;";
                        } else {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-check'>查看选课</span></a>&nbsp;";
                        }
                    }
                    /*如果未授权*/
                } else if (!row.baseActionAuthDTO.addActionAuth && !row.baseActionAuthDTO.deleteActionAuth && !row.baseActionAuthDTO.updateActionAuth && !row.baseActionAuthDTO.publicActionAuth && !row.baseActionAuthDTO.auditActionAuth) {
                    result += "<b>操作未授权</b>";
                } else if (row.timetableStatus == 2) {
                    // if (row.baseActionAuthDTO.publicActionAuth) {
                    //     result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a>&nbsp;";
                    // }
                    if (row.baseActionAuthDTO.auditActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"auditTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>审核排课</span></a>";
                    }
                } else if (row.timetableStatus == 10) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        if (row.timetableStyle == 5) {
                            result += "<table><tr><td height=\"25px\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReNoGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-edit'>不分批调</span></a>&nbsp;";
                        } else if (row.timetableStyle == 6) {
                            result += "<table><tr><td height=\"25px\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-edit'>分批调整</span></a>&nbsp;";
                        } else {
                            result += "<table><tr><td height=\"25px\">&nbsp;";

                        }
                        if (row.timetableStyle == 5||row.timetableStyle == 6) {
                            result += "<tr><td height=\"25px\"><div style=\"height:20px;\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a></div></td></tr></table>&nbsp;";
                        }
                    }
                }else if(row.timetableStatus == 3){
                    if (row.baseActionAuthDTO.publicActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',1)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                    }
                }else if(row.timetableStatus == 4){
                    result += "审核拒绝";
                }
                return result;
            }
        }, {
            title: "计划维护&nbsp;<button class='btn btn-xs green' title='添加'  onclick=\"newSelfCourse()\" ><span class='glyphicon glyphicon-plus'>添加计划</span></button>",
            width: "10%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if (row.timetableStatus == 1) {
                    /*如果未授权*/
                } else if (!row.baseActionAuthDTO.addActionAuth && !row.baseActionAuthDTO.deleteActionAuth && !row.baseActionAuthDTO.updateActionAuth && !row.baseActionAuthDTO.publicActionAuth) {
                    result += "<b>操作未授权</b>";
                } else if (row.timetableStatus == 0) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table>";
                        result += "<tr><td height=\"25px\"><div style=\"height:20px;\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfCourse(" + row.selfId + ")\" ><span class='glyphicon glyphicon-plus'>编辑</span></a>&nbsp;";
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetableSelfCourse(" + row.selfId + ")\" ><span class='glyphicon glyphicon-plus'>删除</span></a>&nbsp;</td></tr></table>";

                    }
                } else if (row.timetableStatus == 2) {
                    if (row.baseActionAuthDTO.publicActionAuth) {
                        //result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable('"+row.timetableStyle+"','" + row.selfId + "',1)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                        //result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a>&nbsp;";

                    }
                } else if (row.timetableStatus == 10) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table><tr><td height=\"25px\">&nbsp;";
                        if (row.timetableStyle == 5||row.timetableStyle == 6) {
                            //result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable('"+row.timetableStyle+"','" + row.selfId + "',2)\" ><span class='glyphicon glyphicon-check'>调课完成</span></a></div></td></tr>&nbsp;";
                            //result += "<tr><td height=\"25px\"><div style=\"height:20px;\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a></div></td></tr></table>&nbsp;";
                            result += "</table>&nbsp;";

                        }
                    }
                }
                return result;
            }
        }]
    });
    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);

    $("#term").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
}

function getTimetableStatus() {
    var url = "";
    var view_radio =$("input[name='view_radio']:checked").val();
    if (view_radio=="timetable") {
        url = contextPath + "/lims/api/timetable/self/apiSelfCourseListByPage";
    }else{
        url = contextPath + "/lims/api/timetable/self/apiSelfCourseManageByPage";
    }
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
*查看学生名单
*/
function timetableCourseStudents(term, selfId) {
    var index = layer.open({
        type: 2,
        title: '查看学生选课情况',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/selfcourse/timetableCourseStudentList?term=' + term + '&selfId=' + selfId
    });
    layer.full(index);
}

function MergeCell(tableId, startRow, endRow, col) {
    var tb = document.getElementById(tableId);
    if(tb==null){
        return;
    }
    if (col >= tb.rows[0].cells.length) {
        return;
    }
    //当检查第0列时检查所有行
    if (col == 0 || endRow == 0) {
        endRow = tb.rows.length - 1;
    }
    for (var i = startRow; i < endRow; i++) {
        //程序是自左向右合并
        if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[col].innerHTML) {
            //如果相同则删除下一行的第0列单元格
            tb.rows[i + 1].cells[col].style.display='none';
            //更新rowSpan属性
            tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;
            //当循环到终止行前一行并且起始行和终止行不相同时递归(因为上面的代码已经检查了i+1行，所以此处只到endRow-1)
            if (i == endRow - 1 && startRow != endRow) {
                MergeCell(tableId, startRow, endRow, col + 1);
            }
        } else {
            //起始行，终止行不变，检查下一列
            MergeCell(tableId, startRow, i, col + 1);
            //增加起始行
            startRow = i + 1;
        }
    }
}

//根据分组，获取已分组名单json的ajax
function getTimetableGroupStudents(selfId, groupId) {
    var returnGroupStudents = "";
    $.ajax({
        url: contextPath + "/lims/api/timetable/manage/getSelfTimetableGroupStudents?groupId=" + groupId + "&selfId=" + selfId,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        type: "post",
        async: false,
        success: function (json) {
            returnGroupStudents = json;
        }
    });
    return returnGroupStudents;
}

//设置分组实际名单
function setTimetableGroupNumbersReality(selfId, groupId,groupNumbers,groupStudentNumbers,colspanValue) {
    //变量：学生名单复选框的id和name
    var group_reality_check = "group_reality_check_"+groupId;
    if($("#div_reality_" + groupId).is(":hidden")) {
        $('#div_reality_' + groupId).show();
        $("#group_button_reality_" + groupId).text('返回');
        //获取学生名单
        var groupStudent = getTimetableGroupStudents(selfId, groupId);
        //var htmlInfo = "<table border=\"1\"><tr><td colspan='4'><b>请重新确定分组学生名单</b></td><td>选择</td></tr>";
        var htmlInfo = "<td colspan='"+colspanValue+"'><table border=\"1\"><tr><td colspan='4'><b>查看分组学生名单</b></td><td>选择</td></tr>";
        $.each(groupStudent.userDTOs, function (idx, obj) {
            if (obj.selected == "1") {
                htmlInfo += "<tr><td>姓名：</td><td>" + obj.cname + "</td><td>学号：</td><td>" + obj.username + ";</td><td>该组学生</td></tr>";
            } else if(obj.selected == "0") {
                htmlInfo += "<tr><td>姓名：</td><td>" + obj.cname + "</td><td>学号：</td><td>" + obj.username + ";</td><td><font color='red'> 未分组学生</font></td></tr>";
            } else if(obj.selected == "-1") {
                htmlInfo += "<tr><td>姓名：</td><td>" + obj.cname + "</td><td>学号：</td><td>" + obj.username + ";</td><td><font color='blue'>其他学生</font></td></tr>";
            }
        });
        htmlInfo += "</table>";
        $('#div_reality_' + groupId).html(htmlInfo);
        $(':button').attr("disabled", "true");
        $("#group_button_reality_" + groupId).removeAttr("disabled");
    }else {
        $("#group_button_reality_" + groupId).text(groupNumbers+"/"+groupStudentNumbers);
        $('#div_reality_' + groupId).hide();
        $(':button').removeAttr("disabled");
    }
}

function auditTimetable(termId, courseNo) {
    var index = layer.open({
        type: 2,
        title: '审核排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/educourse/auditTimetable?termId='+termId.toString()+'&courseNo=' + courseNo.toString()+'&type=2',
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}



