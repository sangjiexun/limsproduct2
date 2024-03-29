var contextPath = $("meta[name='contextPath']").attr("content");
var urlPlan = $('#zuulServerUrl').val()+"api/school/apiSchoolCourseListByPage";
var zuulUrl ="";
var audit = false;// 排课是否需要审核
var businessType = "";// 审核参数
var businessUid = "-1";
var historyFlag = 0;
var timetableFlag = 0;
$(document).ready(function () {
    // 页面参数传递
    businessType = $("#businessType").val();

    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
    urlPlan = zuulUrl+"api/school/apiSchoolCourseListByPage";
    document.cookie = "term=NONE";// 判断默认学期
    getTimetablePlanView();
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
    var arr = new Object();
    arr.termId = $("#term").val();
    arr.offset = params.offset;
    arr.status = $("input[name='view_status']:checked").val();
    arr.limit = params.limit;
    arr.search = $("#search").val();
    arr.sort= params.sort;
    arr.order= params.order;
    if(historyFlag == 1){
        arr.backupType = "EduTimetableAudit";
    }
    var arrs = JSON.stringify(arr);
    return arrs;
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
        content: contextPath + '/lims/timetable/course/schoolCourseStudnetList?term=' + term + '&courseNo=' + courseNo
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
        /*  content: contextPath + '/lims/timetable/course/openAdjustTimetableByScience?currpage=1&flag=0&courseDetailNo=' + courseDetailNo
          + '&tableAppId=' + 0*/
        content: contextPath + '/lims/timetable/course/newEduAdjustCourse?currpage=1&flag=0&courseNo=' + courseNo + "&term=" + term
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTableLayer();
        }
    });
    layer.full(index);
}
/*
 *学生判冲弹出窗口
 */
function studentTimetable(term, courseNo) {
    $("#courseNo").val(courseNo);
    var index = layer.open({
        type: 2,
        title: '学生判冲',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/course/judgeTimetableConflictByStudent?courseNo=' + courseNo
        + '&term=' + term,
        end: function () {
            refreshBootstrapTableLayer();
        }
    });
    layer.full(index);
    /*$('#form_lab').attr(
     "action",——
     contextPath +"/timetable/doDirectTimetable?courseCode=" + courseCode+"&currpage=1");*/
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
        content: contextPath + '/lims/timetable/course/newEduDirectCourse?currpage=1&flag=0&courseNo=' + courseNo
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTableLayer();
        }
    });
    layer.full(index);
    /*$('#form_lab').attr(
        "action",——
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
        content: contextPath + '/lims/timetable/course/newEduReTimetableCourse?currpage=1&flag=0&timetableStyle=3&courseNo=' + courseNo + "&term=" + term
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTableLayer();
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
        content: contextPath + '/lims/timetable/course/newEduReGroupCourse?currpage=1&flag=0&courseNo=' + courseNo + "&term=" + term + "&groupId=-1"
        + '&tableAppId=' + 0,
        end: function () {
            refreshBootstrapTableLayer();
        }
    });
    layer.full(index);
}

function publicTimetable(timetableStyle, courseNo, status) {
    //进行jwt握手，获取token
    //getJWTAuthority();
    var arr = new Object();
    arr.courseNo = courseNo;
    arr.timetableStyle = timetableStyle;
    arr.status = status;
    var arrs = JSON.stringify(arr);
    $.ajax({
        url: zuulUrl + "api/timetable/common/apiTimetablePublic",
        contentType: "application/json;charset=utf-8",
        headers:{Authorization: getJWTAuthority()},
        async: false,
        dataType: "json",
        type: "post",
        //async: false,
        data: arrs,
        success: function (json) {
            refreshBootstrapTableLayer();
        }
    });
    //window.parent.location.reload();
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

function deleteTimetable(term, courseNo) {
    if (confirm('是否确认删除？')) {
        var arr = new Object();
        arr.courseNo = courseNo;
        arr.termId = term;
        var arrs = JSON.stringify(arr);
        //获取jwt认证，获取token
        //getJWTAuthority();
        $.ajax({
            url: zuulUrl + "api/timetable/manage/apiDeleteTimetableByCourseNo",
            contentType: "application/json;charset=utf-8",
            headers:{Authorization: getJWTAuthority()},
            async: false,
            dataType: "json",
            type: "post",
            data: arrs,
            //async: false,
            success: function (json) {
            }
        });
        refreshBootstrapTableLayer();
    }
}

// 删除timetable_appointment_same_number
function deleteTimetableByBySameNumberId(sameNumberId) {
    if (confirm('删除后数据无法恢复，是否确认删除？')) {
        $.ajax({
            url: zuulUrl + "api/timetable/manage/apiDeleteTimetableBySameNumberId?id=" + sameNumberId,
            contentType: "application/json;charset=utf-8",
            headers:{Authorization: getJWTAuthority()},
            async: false,
            dataType: "json",
            type: "post",
            success: function (json) {
                layer.msg("已删除！");
            },
            error: function () {
                layer.msg('后台出了点问题，请重试!', {
                    icon: 1,
                });
                return false;
            }
        });
        refreshBootstrapTableLayer();
    }
}

function refreshBootstrapTable() {
    var url = "";
    if(historyFlag == 1){
        getTimetablePlanView();
        historyFlag = 0;
    }
    url = zuulUrl + "api/school/apiSchoolCourseListByPage";
    var params = $("#table_list").bootstrapTable('getOptions')
    params.ajaxOptions.headers.Authorization =getJWTAuthority();
    params.url = url;
    params.silent=true;
    $("#table_list").bootstrapTable('refresh', params);
}
// 排课视图中不保留调课完成后的管理类操作
function getTimetablePlanView(pageNumber) {
    if(pageNumber == null){
        pageNumber = 1;
    }
    historyFlag = 0;
    timetableFlag = 1;
    $("#allRadio").prop("checked",true);
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
        pageNumber: pageNumber,
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
            width: "2%",
            formatter: function (value, row, index) {
                return index + 1;
                // return row.courseNumber;
            }
        }, {
            title: "课程信息",
            field: "courseNumber",
            width: "8%",
            sortable: true,
            formatter: function (value, row, index) {
                var result = row.courseName + "<br/>(" + row.courseNumber + ")"+"<br/>"+"<br/>教学班编号"+"<br/>(" + row.courseNo + ")";
                if (row.timetableStyle == 4 && row.baseActionAuthDTO.addActionAuth) {
                    result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"courseBatchManage('" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>批/组管理</span></a>&nbsp;";
                }
                return result;
            }
        }, {
            title: "所属学院",
            field: "academyName",
            width: "5%",
            sortable: true
        }, {
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
            width: "40%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                var tlength = 0;
                if(timetableDTOs!=null){
                    tlength = timetableDTOs.length;
                }
                /**子表表头**/
                if (tlength > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                    rt += '<table border="1"><tr><td width="10%">批/组</td><td width="5%">星期</td><td width="8%">节次</td><td width="8%">周次</td><td width="10%">实验室</td><td width="10%">授课教师</td><td width="10%">实验项目</td><td width="6%">选课类型</td><td width="10%">选课时间</td><td width="6%">名单</td>';
                    if (row.timetableStatus==1 && row.baseActionAuthDTO.deleteManageAuth) {
                        rt += '<td width="6%">操作</td>';
                    }
                    rt += "</tr>";
                }
                if (tlength > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="12%">星期</td><td width="12%">节次</td><td width="12%">周次</td><td width="15%">实验室</td><td width="16%">授课教师</td><td width="16%">实验项目</td>';
                    if (row.timetableStatus==1 && row.baseActionAuthDTO.deleteManageAuth) {
                        rt += '<td width="6%">操作</td>';
                    }
                    rt += "</tr>";
                }
                /**子表数据**/
                for (var i = 0, len = tlength; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var startDate = new Date(timetableDTOs[i].startDate);
                        var endDate = new Date(timetableDTOs[i].endDate);
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.courseNo + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+"," + timetableDTOs[i].groupStudents+",8)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupStudents + "/" + timetableDTOs[i].groupNumbers + "</span></button>&nbsp;";
                        rt += "<tr><td>" + timetableDTOs[i].batchName +"/" + timetableDTOs[i].groupName + "</td><td>" + timetableDTOs[i].weekday + "</td><td>";
                        if (timetableDTOs[i].startClass == timetableDTOs[i].endClass) {
                            rt += timetableDTOs[i].endClass;
                        } else {
                            rt += timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass;
                        }
                        rt += "</td><td>";
                        if (timetableDTOs[i].startWeek == timetableDTOs[i].endWeek) {
                            rt += timetableDTOs[i].endWeek;
                        } else {
                            rt += timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek;
                        }
                        rt += "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers + "</td><td>" + timetableDTOs[i].items + "</td><td>";
                        if (timetableDTOs[i].ifSelect == 0) {
                            rt += "系统分配";
                        } else if (timetableDTOs[i].ifSelect == 1) {
                            rt += "学生自选";
                        }
                        rt += "</td><td>" + startDate.toLocaleDateString() + "~" + endDate.toLocaleDateString();
                        rt += "</td><td>" + result + "</td>";
                        if (row.timetableStatus==1 && row.baseActionAuthDTO.deleteManageAuth) {
                            rt += "<td><a href='javascript:;' class='btn btn-xs green' title='删除'  onclick=\"deleteTimetableByBySameNumberId(" + timetableDTOs[i].sameNumberId + ")\" >删除</a></td>";
                        }
                        rt += "</tr><tr id=" + group_div_reality + " style=\"display: none;\"></tr>"
                    }
                    if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                        rt += "<tr><td>" + timetableDTOs[i].weekday + "</td><td>";
                        if (timetableDTOs[i].startClass == timetableDTOs[i].endClass) {
                            rt += timetableDTOs[i].endClass;
                        } else {
                            rt += timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass;
                        }
                        rt += "</td><td>";
                        if (timetableDTOs[i].startWeek == timetableDTOs[i].endWeek) {
                            rt += timetableDTOs[i].endWeek;
                        } else {
                            rt += timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek;
                        }
                        rt += "</td><td>" + timetableDTOs[i].labInfo + "</td><td>" + timetableDTOs[i].teachers + "</td><td>" + timetableDTOs[i].items + "</td>";
                        if (row.timetableStatus==1 && row.baseActionAuthDTO.deleteManageAuth) {
                            rt += "<td><a href='javascript:;' class='btn btn-xs green' title='删除'  onclick=\"deleteTimetableByBySameNumberId(" + timetableDTOs[i].sameNumberId + ")\" >删除</a></td>";
                        }
                        rt += "</tr>";
                    }
                }
                if (tlength > 0) {
                    rt += '</table>';
                }
                return rt;
            }
        }, {
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
            width: "15%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if (row.timetableStatus == 1) {
                    result += "排课已发布<br>";
                } else if (row.timetableStatus == 0) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table>";
                        if (row.schoolCourseDetailDTOs.length > 0 && row.schoolCourseDetailDTOs[0].weekday > 0) {
                            result += "<tr><td height=\"25px\">"
                            if(eduDirect)
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"startTimetable('" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>直接排课</span></a>&nbsp;";
                            if(eduAjust)
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"doAdjustTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>调整排课</span></a>&nbsp;";
                            result += "</td></tr>";
                        }
                        result += "<tr><td height=\"25px\">";
                        if(eduBatch)
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReNoGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>不分批排</span></a>&nbsp;";
                        if(eduNoBatch)
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>分批排课</span></a>&nbsp;";
                        // result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"studentTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>学生判冲</span></a>&nbsp;";
                        result += "</td></tr></table>";
                    }
                } else if (row.timetableStatus == 2 || row.timetableStatus == 5) {// 待审核 || 审核中
                    if(auditOrNot) {// 设置需要审核
                        if(row.baseActionAuthDTO.auditActionAuth) {
                            result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"auditTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-remove'>审核排课</span></a>";
                        }else {
                            result += row.auditors;
                        }
                    }else {// 设置不需要审核
                        if (row.baseActionAuthDTO.publicActionAuth) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable("+row.timetableStyle+",'" + row.courseNo + "',1)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                        }
                    }
                }else if(row.timetableStatus == 3) {
                    if (row.baseActionAuthDTO.publicActionAuth) {
                        result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable("+row.timetableStyle+",'" + row.courseNo + "',1)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                    }else {
                        result += "审核完成，请等待发布";
                    }
                }else if(row.timetableStatus == 4) {
                    result += "审核未通过";
                }else if (row.timetableStatus == 10) {
                    if (row.baseActionAuthDTO.addActionAuth) {
                        result += "<table><tr><td height=\"25px\">";
                        if (row.timetableStyle == 2) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"doAdjustTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>继续调课</span></a>&nbsp;";
                        } else if (row.timetableStyle == 3) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReNoGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>不分批调</span></a>&nbsp;";
                        } else if (row.timetableStyle == 4) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReGroupCourse(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>分批调整</span></a>&nbsp;";
                        }
                        if (row.timetableStyle != 1) {
                            if(auditOrNot) {
                                // 需要审核
                                result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable(1,'" + row.courseNo + "',2)\" ><span class='glyphicon glyphicon-check'>排课完成</span></a></td></tr>&nbsp;";
                            }else{
                                // 不需要审核
                                result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable(1,'" + row.courseNo + "',3)\" ><span class='glyphicon glyphicon-check'>排课完成</span></a></td></tr>&nbsp;";
                            }
                            result += "<tr><td height=\"25px\"><div style=\"height:20px;\"><a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"deleteTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-remove'>删除排课</span></a></div>&nbsp;";
                            // result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"studentTimetable(" + row.termId + ",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>学生判冲</span></a>&nbsp;";
                        }
                        result += "</td></tr></table>";
                    }
                }else if (!row.baseActionAuthDTO.addActionAuth && !row.baseActionAuthDTO.deleteActionAuth && !row.baseActionAuthDTO.updateActionAuth && !row.baseActionAuthDTO.auditActionAuth && !row.baseActionAuthDTO.publicActionAuth) {
                    result += "<b>操作未授权</b>";
                }
                return result;
            }
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
    $("#search").keydown("input", function (event) {
        var params = $("#table_list").bootstrapTable('getOptions')
        params.ajaxOptions.headers.Authorization =getJWTAuthority();
        params.silent=true;
        if (event.keyCode==13){
            $("#table_list").bootstrapTable('refresh', params);
        }
        // $("#table_list").bootstrapTable('refresh', params);
    })

}

function getTimetableHistoryView(pageNumber) {
    if(pageNumber == null){
        pageNumber = 1;
    }
    historyFlag = 1;
    timetableFlag = 3;
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    var url = zuulUrl + "/api/timetable/common/apiTimetableHistory";
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        //获取数据的Servlet地址
        url: url,
        ajaxOptions:{
            headers: {Authorization: getJWTAuthority()}
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
        pageNumber: pageNumber,
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
            width: "2%",
            formatter: function (value, row, index) {
                return index + 1;
            }
        }, {
            title: "课程信息",
            field: "businessId",
            width: "5%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "<br/>(" + row.courseNumber + ")"+"<br/>"+"<br/>教学班编号"+"<br/>(" + row.courseNo + ")";
            }
        },  {
            title: "已排课表",
            field: "timetableDTOs",
            width: "40%",
            formatter: function (value, row, index) {
                var rt = "";
                var timetableDTOs = row.timetableDTOs;
                if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                    rt += '<table border="1"><tr><td width="12%">批/组</td><td width="8%">星期</td><td width="8%">节次</td><td width="8%">周次</td><td width="20%">实验室</td><td width="6%">选课类型</td><td width="10%">选课时间</td><td width="6%">名单</td></tr>';
                }
                if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                    rt += '<table border="1"><tr><td width="15%">星期</td><td width="15%">节次</td><td width="15%">周次</td><td width="50%">实验室</td></tr>';
                }
                var count = Number(0);
                for (var i = 0, len = timetableDTOs.length; i < len; i++) {
                    if (timetableDTOs.length > 0&&(row.timetableStyle==4||row.timetableStyle==6)) {
                        var startDate = new Date(timetableDTOs[i].startDate);
                        var endDate = new Date(timetableDTOs[i].endDate);
                        var group_button_reality = 'group_button_reality_' + timetableDTOs[i].groupId;
                        var group_div_reality = 'div_reality_' + timetableDTOs[i].groupId;
                        var result = "<button  id='" + group_button_reality + "' class='btn btn-xs green' onclick=\"setTimetableGroupNumbersReality('" + row.courseNo + "'," + timetableDTOs[i].groupId +","+timetableDTOs[i].groupNumbers+","+ timetableDTOs[i].groupStudents+",6)\" title='编辑' ><span class='glyphicon glyphicon'>" + timetableDTOs[i].groupStudents + "/" + timetableDTOs[i].groupNumbers + "</span></button>";
                        rt += "<tr><td>" + timetableDTOs[i].batchName +"/" + timetableDTOs[i].groupName + "</td><td>" + timetableDTOs[i].weekday + "</td><td>";
                        if (timetableDTOs[i].startClass == timetableDTOs[i].endClass) {
                            rt += timetableDTOs[i].endClass;
                        } else {
                            rt += timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass;
                        }
                        rt += "</td><td>";
                        if (timetableDTOs[i].startWeek == timetableDTOs[i].endWeek) {
                            rt += timetableDTOs[i].endWeek;
                        } else {
                            rt += timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek;
                        }
                        rt += "</td><td>" + timetableDTOs[i].labInfo + "</td><td>";
                        if (timetableDTOs[i].ifSelect == 0) {
                            rt += "系统分配";
                        } else if (timetableDTOs[i].ifSelect == 1) {
                            rt += "学生自选";
                        }
                        rt += "</td><td>" + startDate.toLocaleDateString() + "~" + endDate.toLocaleDateString();
                        rt += "</td><td>" + result + "</td></tr>";
                        rt += "<tr id=" + group_div_reality + " style=\"display: none;\"></tr>"
                    }
                    if (timetableDTOs.length > 0&&row.timetableStyle!=4&&row.timetableStyle!=6) {
                        rt += "<tr><td>" + timetableDTOs[i].weekday + "</td><td>";
                        if (timetableDTOs[i].startClass == timetableDTOs[i].endClass) {
                            rt += timetableDTOs[i].endClass;
                        } else {
                            rt += timetableDTOs[i].startClass + "-" + timetableDTOs[i].endClass;
                        }
                        rt += "</td><td>";
                        if (timetableDTOs[i].startWeek == timetableDTOs[i].endWeek) {
                            rt += timetableDTOs[i].endWeek;
                        } else {
                            rt += timetableDTOs[i].startWeek + "-" + timetableDTOs[i].endWeek;
                        }
                        rt += "</td><td>" + timetableDTOs[i].labInfo + "</td></tr>";
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
            width: "15%",
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
    $("#search").keydown("input", function (event) {
        var params = $("#table_list").bootstrapTable('getOptions')
        if (event.keyCode==13){
            $("#table_list").bootstrapTable('refresh', params);
        }
        // $("#table_list").bootstrapTable('refresh', params);
    })
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
        $("#group_button_reality_" + groupId).text(groupStudentNumbers+"/"+groupNumbers);
        $('#div_reality_' + groupId).hide();
        $(':button').removeAttr("disabled");
    }
}

//根据分组，获取已分组名单json的ajax
function getTimetableGroupStudents(courseNo, groupId) {
    var returnGroupStudents = "";
    $.ajax({
        url: zuulUrl + "api/timetable/manage/getTimetableGroupStudents?groupId=" + groupId + "&courseNo=" + courseNo,
        contentType: "application/json;charset=utf-8",
        headers:{Authorization: getJWTAuthority()},
        async: false,
        dataType: "json",
        type: "post",
        //async: false,
        success: function (json) {
            returnGroupStudents = json;
        }
    });
    return returnGroupStudents;
}

// 审核页面
function auditTimetable(termId, courseNo) {
    var index = layer.open({
        type: 2,
        title: '审核排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/course/auditTimetable?businessAppUid=' + courseNo.toString()+'&businessType='+businessType+'&businessUid='+businessUid,
        end: function () {
            refreshBootstrapTableLayer();
        }
    });
    layer.full(index);
}

// 获取课程的批组信息
function courseBatchManage(course_no) {
    var index = layer.open({
        type : 2,
        title : '批/组管理',
        maxmin : true,
        shadeClose : true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/course/batchManageList?course_no=' + course_no,
        end: function () {
            refreshBootstrapTableLayer();
        }
    });
    layer.full(index);
}

function refreshBootstrapTableLayer() {
    var url = "";
    if(historyFlag == 1){
        getTimetablePlanView();
        historyFlag = 0;
    }
    url = zuulUrl + "api/school/apiSchoolCourseListByPage";
    var params = $("#table_list").bootstrapTable('getOptions')
    params.ajaxOptions.headers.Authorization =getJWTAuthority();
    params.url = url;
    params.silent=true;
    var pageNumber = params.pageNumber;
    if(timetableFlag == 1){
        getTimetablePlanView(pageNumber);
    }else if(timetableFlag == 3){
        getTimetableHistoryView(pageNumber)
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

