var contextPath = $("meta[name='contextPath']").attr("content");
var eduDirect = $("meta[name='eduDirect']").attr("content");
var eduAjust = $("meta[name='eduAjust']").attr("content");
var eduBatch = $("meta[name='eduBatch']").attr("content");
var eduNoBatch = $("meta[name='eduNoBatch']").attr("content");
var isRefuse = false;
$(document).ready(function () {
    document.cookie = "term=NONE";// 判断默认学期
    c_start=document.cookie.indexOf("itype=");
    c_end=document.cookie.indexOf(";",c_start);
    var itype =document.cookie.substring(c_start,c_end);
    if(c_start==-1){
        getTimetablePlanView();
    }else if(itype.split("=")[1]=='PLAN'){
        getTimetablePlanView();
    }else if(itype.split("=")[1]=='MANAGE'){
        getTimetableMangerView();
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
function schoolCourseStudents(term, courseNo) {
    var index = layer.open({
        type: 2,
        title: '查看学生选课情况',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/school/schoolcoursestudent/schoolCourseStudnetList?term=' + term + '&courseNo=' + courseNo
    });
    layer.full(index);
}

/*
*查看排课弹出窗口
*/
function viewTimetableInfo(courseNo) {
    var index = layer.open({
        type: 2,
        title: '查看排课情况',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/common/viewTimetableInfo?style=1&courseNo=' + courseNo
    });
    layer.full(index);
}

/*
*调整排课弹出窗口
*/
function doAdjustTimetable(term, courseNo) {
    var index = layer.open({
        type: 2,
        title: '调整排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        /*  content: contextPath + '/lims/timetable/engineer/educourse/openAdjustTimetableByScience?currpage=1&flag=0&courseDetailNo=' + courseDetailNo
          + '&tableAppId=' + 0*/
        content: contextPath + '/lims/timetable/engineer/educourse/newEduAdjustCourse?currpage=1&flag=0&courseNo=' + courseNo + "&term=" + term
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}

/*
*直接排课弹出窗口
 */
function startTimetable(courseNo) {
    $("#courseNo").val(courseNo);
    var index = layer.open({
        type: 2,
        title: '直接排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/educourse/newEduDirectCourse?currpage=1&flag=0&courseNo=' + courseNo
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
    /*$('#form_lab').attr(
        "action",
        contextPath +"/timetable/doDirectTimetable?courseCode=" + courseCode+"&currpage=1");*/
}

/*
*二次不分批排课弹出窗口
*/
function newEduReNoGroupCourse(term, courseNo) {
    var index = layer.open({
        type: 2,
        title: '二次不分批排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/edurecourse/newEduReTimetableCourse?currpage=1&flag=0&timetableStyle=3&courseNo=' + courseNo + "&term=" + term
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}

/*
*二次分批排课弹出窗口
*/
function newEduReGroupCourse(term, courseNo) {
    var index = layer.open({
        type: 2,
        title: '二次分批排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/edurecourse/newEduReGroupCourse?currpage=1&flag=0&courseNo=' + courseNo + "&term=" + term + "&groupId=-1"
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}

function publicTimetable(timetableStyle, courseNo, status) {
    var arr = new Object();
    arr.courseNo = courseNo;
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

function deleteTimetable(term, courseNo) {
    if (confirm('是否确认删除？')) {
        $.ajax({
            url: contextPath + "/lims/api/timetable/manage/apiDeleteTimetableByCourseNo?term=" + term + "&courseNo=" + courseNo,
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
    if($("input[name='view_status']:checked").val() == "REFUSE"){
        url = contextPath + "/lims/api/school/apiSchoolCourseListByPageForRefuse";
    }
    var url = "";
    var view_radio =$("input[name='view_radio']:checked").val();
    if (view_radio=="timetable") {
        url = contextPath + "/lims/api/school/apiSchoolCourseListByPage";
    }else{
        url = contextPath + "/lims/api/school/apiEduSchoolCourseByPage";
    }
    var opt = {
        silent: true
    };
    $("#table_list").bootstrapTable('refresh', opt);
}

function getTimetablePlanView() {
    isRefuse = false;
    if(document.cookie.indexOf("PLAN") < 0) {
        document.cookie = "itype=PLAN";
        document.getElementById("div_status2").style.display = "";
        document.getElementById("div_status5").style.display = "";
        document.getElementById("div_status6").style.display = "";
        document.getElementById("div_status3").style.display = "none";
        document.getElementById("div_status4").style.display = "none";
        document.getElementById("div_status7").style.display = "none";
        document.getElementById("view_status1").checked = "checked";
    }
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    var url = contextPath + "/lims/api/school/apiSchoolCourseListByPage";
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
            width: "3%",
            formatter: function (value, row, index) {
                // return index + 1;
                return row.courseNumber;
            }
        }, {
            title: "课程信息",
            field: "courseNumber",
            width: "8%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "<br/>(" + row.courseNumber + ")";
            }
        }, {
            title: "所属学院",
            field: "academyName",
            width: "5%",
            sortable: true
        }/*, {
            title: "班级信息",
            field: "classInfo",
            width: "5%",
            sortable: true
        }*/, {
            title: "课程计划",
            field: "coursePlan",
            width: "20%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td width="20%">星期</td><td width="20%">节次</td><td width="20%">周次</td><td width="30%">实验室</td></tr>';
                var schoolCourseDetailDTOs = row.schoolCourseDetailDTOs;
                var count = Number(0);
                for (var i = 0, len = schoolCourseDetailDTOs.length; i < len; i++) {
                    if (schoolCourseDetailDTOs[i].weekday == 0) {
                        return "";
                    }
                    //rt += schoolCourseDetailDTOs[i].coursePlan + "<br>";
                    rt += "<tr><td>" + schoolCourseDetailDTOs[i].weekday + "</td><td>" + schoolCourseDetailDTOs[i].startClass + "-" + schoolCourseDetailDTOs[i].endClass + "</td><td>" + schoolCourseDetailDTOs[i].startWeek + "-" + schoolCourseDetailDTOs[i].endWeek + "</td><td>" + schoolCourseDetailDTOs[i].labInfo + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        }, {
            title: "已排课表",
            field: "timetableDTOs",
            width: "35%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                    rt += '<table border="1"><tr><td width="21%">批/组</td><td width="12%">星期</td><td width="14%">节次</td><td width="14%">周次</td><td width="28%">实验室</td><td width="10%">授课教师</td><td width="10%">实验项目</td><td width="6%">名单</td></tr>';
                }
                if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="7%">星期</td><td width="13%">节次</td><td width="15%">周次</td><td width="30%">实验室</td><td width="15%">授课教师</td><td width="20%">实验项目</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableDTOs.length; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.courseNo + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+","+ timetableDTOs[i].groupStudents+",6)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupNumbers + "/" + timetableDTOs[i].groupStudents + "</span></button>";
                        rt += "<tr><td>" + timetableDTOs[i].batchName +"/" + timetableDTOs[i].groupName + "</td><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers +"/" + timetableDTOs[i].tutors + "</td><td>" + timetableDTOs[i].items + "</td><td>" + result + "</td></tr>";
                        rt += "<tr id=" + group_div_reality + " style=\"display: none;\"></tr>"
                    }
                    if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                        rt += "<tr><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers +"/" + timetableDTOs[i].tutors + "</td><td>" + timetableDTOs[i].items + "</td></tr>";
                    }
                }
                if (timetableDTOs.length > 0) {
                    rt += '</table>';
                }

                return rt;
            }
        }/*, {
            title: "已排课表",
            field: "timetableMergeDTOs",
            width: "20%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableMergeDTOs = row.timetableMergeDTOs;
                if(timetableMergeDTOs.length>0){
                    rt +=  '<table border="1"><tr><td width="15%">星期</td><td width="15%">节次</td><td width="15%">周次</td><td width="55%">实验室</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableMergeDTOs.length; i < len; i++) {
                    rt += "<tr><td>"+timetableMergeDTOs[i].weekday+"</td><td>"+timetableMergeDTOs[i].startClass+"-"+timetableMergeDTOs[i].endClass+"</td><td>"+timetableMergeDTOs[i].startWeek+"-"+timetableMergeDTOs[i].endWeek+"</td><td>"+timetableMergeDTOs[i].labInfo+"</td></tr>";
                }
                if(timetableMergeDTOs.length>0){
                    rt +='</table>';
                }
                return rt;
            }
        }*/, {
            title: "上课教师",
            field: "cname",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.cname + "(" + row.username + ")";
            }
        }, {
            title: "学生/班级",
            field: "student",
            width: "5%",
            formatter: function (value, row, index) {

                var result = "";
                result = "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"schoolCourseStudents(" + row.termId + ",'" + row.courseNo + "')\" >名单(" + row.student + ")</a><br>";
                result += row.classInfo;
                return result;
            }
        }, {
            title: "操作",
            width: "19%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if (!row.baseActionAuthDTO.addActionAuth && !row.baseActionAuthDTO.deleteActionAuth && !row.baseActionAuthDTO.updateActionAuth && !row.baseActionAuthDTO.publicActionAuth) {
                    result += "<b>操作未授权</b>";
                } else if (row.timetableStatus == 0) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table><tr><td>";
                        if (row.schoolCourseDetailDTOs.length > 0 && row.schoolCourseDetailDTOs[0].weekday > 0) {
                            if (eduDirect == "true") {
                                result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"startTimetable('" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>直接排课</span></a>";
                            }
                            if (eduAjust == "true") {
                                result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"doAdjustTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>调整排课</span></a>";
                            }
                        }
                        result += "</td></tr><tr><td>";
                        if (eduNoBatch == "true") {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"newEduReNoGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>不分批排</span></a>";
                        }
                        if (eduBatch == "true") {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"newEduReGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>分批排课</span></a>";
                        }
                        result += "</td></tr></table>";
                    }
                } else if (row.timetableStatus == 10) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table><tr><td>";
                        if (row.timetableStyle == 2) {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"doAdjustTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>继续调课</span></a>";
                        } else if (row.timetableStyle == 3) {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"newEduReNoGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>不分批调</span></a>";
                        } else if (row.timetableStyle == 4) {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"newEduReGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>分批调整</span></a>";
                        }
                        if (row.timetableStyle != 1) {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"publicTimetable(1,'" + row.courseNo + "',2)\" ><span class='glyphicon glyphicon-check'>调课完成</span></a>";
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"deleteTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a>";
                        }
                        result += "</td></tr></table>";
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

function getTimetableMangerView() {
    if(document.cookie.indexOf("PLAN") >= 0) {
        document.cookie = "itype=MANAGE";
        document.getElementById("div_status2").style.display = "none";
        document.getElementById("div_status5").style.display = "none";
        document.getElementById("div_status6").style.display = "none";
        document.getElementById("div_status3").style.display = "";
        document.getElementById("div_status4").style.display = "";
        document.getElementById("div_status7").style.display = "";
        document.getElementById("view_status1").checked = "checked";
    }
    $("#table_list").bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    var url = contextPath + "/lims/api/school/apiEduSchoolCourseByPage";
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
            width: "3%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "课程信息",
            field: "courseNumber",
            width: "7%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "<br/>(" + row.courseNumber + ")";
            }
        }, {
            title: "所属学院",
            field: "academyName",
            width: "6%",
            sortable: true
        }, {
            title: "课程计划",
            field: "coursePlan",
            width: "17%",
            formatter: function (value, row, index) {
                var rt = '<table border="1"><tr><td width="20%">星期</td><td width="20%">节次</td><td width="20%">周次</td><td width="40%">实验室</td></tr>';
                var schoolCourseDetailDTOs = row.schoolCourseDetailDTOs;
                var count = Number(0);
                for (var i = 0, len = schoolCourseDetailDTOs.length; i < len; i++) {
                    if (schoolCourseDetailDTOs[i].weekday == 0) {
                        return "";
                    }
                    //rt += schoolCourseDetailDTOs[i].coursePlan + "<br>";
                    rt += "<tr><td>" + schoolCourseDetailDTOs[i].weekday + "</td><td>" + schoolCourseDetailDTOs[i].startClass + "-" + schoolCourseDetailDTOs[i].endClass + "</td><td>" + schoolCourseDetailDTOs[i].startWeek + "-" + schoolCourseDetailDTOs[i].endWeek + "</td><td>" + schoolCourseDetailDTOs[i].labInfo + "</td></tr>";
                }
                rt += '</table>';
                return rt;
            }
        }, {
            title: "已排课表",
            field: "timetableDTOs",
            width: "31%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {

                    rt += '<table border="1"><tr><td width="13%">批/组</td><td width="7%">星期</td><td width="12%">节次</td><td width="12%">周次</td><td width="13%">实验室</td><td width="10%">授课教师</td><td width="11%">实验项目</td><td width="6%">名单</td></tr>';
                }
                if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="12%">星期</td><td width="12%">节次</td><td width="12%">周次</td><td width="15%">实验室</td><td width="16%">授课教师</td><td width="16%">实验项目</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableDTOs.length; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.courseNo + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+"," + timetableDTOs[i].groupStudents+",8)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupNumbers + "/" + timetableDTOs[i].groupStudents + "</span></button>";
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
                result = "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"schoolCourseStudents(" + row.termId + ",'" + row.courseNo + "')\" >名单(" + row.student + ")</a>";
                return result;
            }
        }, {
            title: "操作",
            width: "15%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if (row.timetableStatus == 1) {
                    result += "排课已发布|";
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"publicTimetable("+row.timetableStyle+",'" + row.courseNo + "',10)\" ><span class='glyphicon glyphicon-ok'>调课</span></a>";
                    }

                    if (row.baseActionAuthDTO.selectGroupActionAuth && row.timetableStyle == 4) {
                        if (row.isSelect == 1) {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"newEduReGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-check'>学生选课</span></a>";
                        } else {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"newEduReGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-check'>查看选课</span></a>";
                        }
                    }
                    /*如果未授权*/
                } else if (!row.baseActionAuthDTO.addActionAuth && !row.baseActionAuthDTO.deleteActionAuth && !row.baseActionAuthDTO.updateActionAuth && !row.baseActionAuthDTO.publicActionAuth && !row.baseActionAuthDTO.auditActionAuth) {
                    result += "<b>操作未授权</b>";
                } else if (row.timetableStatus == 2) {
                    if (row.baseActionAuthDTO.auditActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"auditTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-remove'>审核排课</span></a>";
                    }
                } else if(row.timetableStatus == 3){
                    if (row.baseActionAuthDTO.publicActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"publicTimetable(" + row.timetableStyle + ",'" + row.courseNo + "',1)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>";
                    }
                } else if(row.timetableStatus == 4){
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


function getTimetableRefuseView() {
    isRefuse = true;
        document.cookie = "itype=PLAN";
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    var url = contextPath + "/lims/api/school/apiSchoolCourseListByPageForRefuse";
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
            width: "3%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "课程信息",
            field: "businessId",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "<br/>(" + row.courseNumber + ")";
            }
        },  {
            title: "已排课表",
            field: "timetableDTOs",
            width: "31%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                    rt += '<table border="1"><tr><td width="21%">批/组</td><td width="12%">星期</td><td width="14%">节次</td><td width="14%">周次</td><td width="28%">实验室</td><td width="6%">名单</td></tr>';
                }
                if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="15%">星期</td><td width="15%">节次</td><td width="15%">周次</td><td width="55%">实验室</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableDTOs.length; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.courseNo + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+","+ timetableDTOs[i].groupStudents+",6)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupNumbers + "/" + timetableDTOs[i].groupStudents + "</span></button>";
                        rt += "<tr><td>" + timetableDTOs[i].batchName +"/" + timetableDTOs[i].groupName + "</td><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + result + "</td></tr>";
                        rt += "<tr id=" + group_div_reality + " style=\"display: none;\"></tr>"
                    }
                    if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                        rt += "<tr><td>" + timetableDTOs[i].weekday + "</td><td>" + timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass + "</td><td>" + timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek + "</td><td>" + timetableDTOs[i].labInfo + "</td></tr>";
                    }
                }
                if (timetableDTOs.length > 0) {
                    rt += '</table>';
                }

                return rt;
            }
        }, {
            title: "上课教师",
            field: "teacher",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.cname;
            }
        }, {
            title: "指导教师",
            field: "tutor",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.username;
            }
        },{
            title: "创建人",
            field: "creator",
            width: "5%",
            formatter: function (value, row, index) {
                return row.classInfo;
            }
        }, {
            title: "操作",
            width: "19%",
            field: "empty",
            formatter: function (value, row, index) {
                return row.academyName;
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
        if(isRefuse){
            getTimetablePlanView();
        }
        url = contextPath + "/lims/api/school/apiSchoolCourseListByPage";
    }else{
        url = contextPath + "/lims/api/school/apiEduSchoolCourseByPage";
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

//设置分组实际名单
function setTimetableGroupNumbersReality(courseNo, groupId,groupNumbers,groupStudentNumbers,colspanValue) {
    //变量：学生名单复选框的id和name
    var group_reality_check = "group_reality_check_"+groupId;
    if($("#div_reality_" + groupId).is(":hidden")) {
        $('#div_reality_' + groupId).show();
        $("#group_button_reality_" + groupId).text('返回');
        //获取学生名单
        var groupStudent = getTimetableGroupStudents(courseNo, groupId);
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
        htmlInfo += "</table></td>";
        $('#div_reality_' + groupId).html(htmlInfo);
        $(':button').attr("disabled", "true");
        $("#group_button_reality_" + groupId).removeAttr("disabled");
    } else {
        $("#group_button_reality_" + groupId).text(groupNumbers+"/"+groupStudentNumbers);
        $('#div_reality_' + groupId).hide();
        $(':button').removeAttr("disabled");
    }
}

//根据分组，获取已分组名单json的ajax
function getTimetableGroupStudents(courseNo, groupId) {
    var returnGroupStudents = "";
    $.ajax({
        url: contextPath + "/lims/api/timetable/manage/getTimetableGroupStudents?groupId=" + groupId + "&courseNo=" + courseNo,
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

function auditTimetable(termId, courseNo) {
    var index = layer.open({
        type: 2,
        title: '审核排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/engineer/educourse/auditTimetable?termId='+termId.toString()+'&courseNo=' + courseNo.toString()+'&type=1',
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);

}