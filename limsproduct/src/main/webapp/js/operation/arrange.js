var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl ="";
var businessType = "";// 审核参数
var businessUid = "-1";
var opType = "";
var title = "";
var allNumber = "";
var selected_role = "";
var PROJECT_NAME = "";
$(document).ready(function () {
    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
    opType=$("#type").val();
    title=$("#title").val();
    allNumber=$("#allNumber").val();
    selected_role = $("#selected_role").val();
    PROJECT_NAME = $("#PROJECT_NAME").val();
    document.cookie = "term=NONE";// 判断默认学期
    c_start=document.cookie.indexOf("selfType=");
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
    $('#teacher').select2({
        width: "89%",
        placeholder: '请选择授课教师...',
        placeholderOption: "first",
        ajax: {
            url: zuulUrl + "api/user/apiUserListBySelect",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", getJWTAuthority());
            },
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var query = {
                    search: params.term,
                    userRole: '1'
                }
                return query;
            },
            results: function (data, page) {
                return {
                    results: data
                };
            }
        }
    });

    $('#academyNumber').select2({
        width: "89%",
        placeholder: '请选择学院信息...',
        placeholderOption: "first",
        ajax: {
            url: zuulUrl + "api/school/apiSchoolAcademyListBySelect",
            contentType: "application/json;charset=utf-8",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", getJWTAuthority());
            },
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var arr = new Object();
                arr.search = params.term;
                var arrs = JSON.stringify(arr);
                return arrs;
                /*var query = {
                    search: params.term
                }
                return query;*/
            },
            results: function (data, page) {
                return {
                    results: data
                };
            }
        }
    });

    $("#submitButton").on('click', function () {
        alert('安排已提交，正在处理，请稍后!');
        if (validform().form()) {
            var arr = new Object();
            arr.courseNumber = $("#courseNumber").val();
            arr.term = $("#term").val();
            arr.academyNumber = $("#academyNumber").val();
            arr.teacher = $("#teacher").val();
            arr.id = $("#selfId").val();
            arr.courseCount = $("#courseCount").val();
            arr.courseCode = $("#courseCode").val();
            arr.students = $("#students").val();
            var arrs = JSON.stringify(arr);
            $.ajax({
                url: zuulUrl + "api/timetable/self/apiSaveTimetableSelfCourse",
                contentType: "application/json;charset=utf-8",
                dataType: "text",
                type: "post",
                headers:{Authorization: getJWTAuthority()},
                async: false,
                data: arrs,
                success: function (selfId) {
                    console.log(selfId);
                    if (selfId == "") {
                        alert("所选择的实训室资源冲突，请重新选择或者用调整排课操作，谢谢。");
                        isConflict = 0;
                        return;
                    }
                    $.ajax({
                        url: contextPath + "/openOperationItem/saveArrangeData",
                        type: "post",
                        headers: {Authorization: getJWTAuthority()},
                        async: false,
                        data: {
                            "type": $("#type").val(),
                            "operId": $("#operId").val(),
                            "selfId": selfId,
                            "startTime": $("#startTime").val(),
                            "endTime": $("#endTime").val()
                        },
                        success:function (result) {
                            if(result == "success"){
                                if($("#type").val() == 0 || $("#type").val() == 1) {
                                    var startDate = $("#startTime").val();
                                    var endDate = $("#endTime").val();
                                    startDate = startDate.replace(" ", "T");
                                    endDate = endDate.replace(" ", "T");
                                    var arr = new Object();
                                    arr.selfId = selfId;
                                    arr.batchName = "无";
                                    arr.countGroup = 1;
                                    arr.numbers = allNumber;
                                    arr.startDate = startDate;
                                    arr.endDate = endDate;
                                    arr.ifselect = 1;
                                    var arrs = JSON.stringify(arr);
                                    $.ajax({
                                        url: zuulUrl + "api/timetable/manage/apiSaveTimetableBatch",
                                        contentType: "application/json;charset=utf-8",
                                        dataType: "json",
                                        type: "post",
                                        headers: {Authorization: getJWTAuthority()},
                                        async: false,
                                        data: arrs,
                                        success: function (json) {
                                            if (json.responseText == "no") {
                                                alert("所选择的实训室资源冲突，请重新选择或者用调整排课操作，谢谢。");
                                                isConflict = 0;
                                            }
                                            alert("提交成功！");
                                        },
                                        error:function () {
                                            alert("提交失败！");
                                        }
                                    });
                                }
                                window.location.reload();
                            }
                        }
                    });
                }
            });
        } else {
            alert("请验证输入！");
        }
    })

    $("#form_operation").validate();

    $("#labRoom_id").change(function () {
        $(this).valid();
    });

    $("#teacherRelated").change(function () {
        $(this).valid();
    });
});

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
// 排课视图中不保留调课完成后的管理类操作
function getTimetablePlanView() {
    historyFlag = 0;
    $("#allRadio").prop("checked",true);
    document.cookie = "selfType=PLAN";
    //初始化表格,动态从服务器加载数据
    $("#table_list").bootstrapTable('destroy');
    var url = zuulUrl + "api/timetable/self/apiSelfCourseListByPage";
    $("#table_list").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "post",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/json;charset=utf-8",
        //获取数据的Servlet地址
        dataType: "json",
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
                if (row.timetableStatus == 1) {
                    result += "排课已发布<br>";
                    if (row.baseActionAuthDTO.selectGroupActionAuth && row.timetableStyle == 6) {
                        if (row.isSelect == 1) {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-check'>学生选课</span></a>&nbsp;";
                        } else {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-check'>查看选课</span></a>&nbsp;";
                        }
                    }
                    /*如果未授权*/
                }else if (row.timetableStatus == 0) {
                    result += "<table><tr><td height=\"25px\">";
                    if (opType == 2)
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReNoGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-plus'>排课</span></a>&nbsp;";
                    else
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-plus'>排课</span></a>&nbsp;";
                    result += "</td></tr></table>";
                } else if (row.timetableStatus == 3) {
                    if (PROJECT_NAME=="ndyzlims") {
                        if (selected_role != 'ROLE_TEACHER') {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',1,0)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                        }
                    }else {
                        result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',1,0)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                    }
                } else if (row.timetableStatus == 10) {
                    result += "<table><tr><td height=\"25px\">";
                    if (opType == 2) {
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReNoGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-plus'>排课</span></a>&nbsp;";
                        if (PROJECT_NAME=="ndyzlims") {
                            if (selected_role != 'ROLE_TEACHER') {
                                result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',3,1)\" ><span class='glyphicon glyphicon-check'>完12成</span></a></div></td></tr>&nbsp;";
                            }
                        }else {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',3,1)\" ><span class='glyphicon glyphicon-check'>完123成</span></a></div></td></tr>&nbsp;";
                        }
                    }
                    else
                    {
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newSelfReGroupCourse(" + row.termId + ",'" + row.selfId + "')\" ><span class='glyphicon glyphicon-plus'>排课</span></a>&nbsp;";
                        if (PROJECT_NAME=="ndyzlims") {
                            if (selected_role != 'ROLE_TEACHER') {
                                result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',3,1)\" ><span class='glyphicon glyphicon-check'>完23成</span></a></div></td></tr>&nbsp;";
                            }
                        }else {
                            result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable('" + row.timetableStyle + "','" + row.selfId + "',3,1)\" ><span class='glyphicon glyphicon-check'>完234成</span></a></div></td></tr>&nbsp;";
                        }
                    }
                    result += "</td></tr></table>";
                }
                return result;
            }
        }]
    });
    //默认展开
    $("#table_list").bootstrapTable('expandRow', 1);

    $("#term").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions');
        params.ajaxOptions.headers.Authorization =getJWTAuthority();
        params.silent=true;
        $("#table_list").bootstrapTable('refresh', params);
    })
    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions');
        params.ajaxOptions.headers.Authorization =getJWTAuthority();
        params.silent=true;
        $("#table_list").bootstrapTable('refresh', params);
    })

}

function refreshBootstrapTable() {
    var url = "";
    getTimetablePlanView();
    url = zuulUrl + "api/timetable/self/apiSelfCourseListByPage";
    var params = $("#table_list").bootstrapTable('getOptions')
    params.ajaxOptions.headers.Authorization = getJWTAuthority();
    params.url = url;
    params.silent = true;
    $("#table_list").bootstrapTable('refresh', params);
}

/*
*二次不分批排课弹出窗口
*/
function newSelfReNoGroupCourse(term, selfId) {
    var index = layer.open({
        type: 2,
        title: title,
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '100%'],
        content: contextPath + '/openOperationItem/timetableNoBatchNoChoose?currpage=1&flag=0&timetableStyle=5&selfId=' + selfId + "&term=" + term
            + '&tableAppId=' + 0+"&opId="+ $("#operId").val(),
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
        title: title,
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/openOperationItem/timetableChooseGroupList?currpage=1&flag=0&selfId=' + selfId + "&term=" + term + "&groupId=-1"
            + '&tableAppId=' + 0+"&opId="+ $("#operId").val(),
        end: function () {
            refreshBootstrapTable();
        }
    });
    layer.full(index);
}

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
    arr.status = "OpenOperation";
    arr.offset = params.offset;
    arr.limit = params.limit;
    arr.search = $("#search").val();
    arr.sort = params.sort;
    arr.order = params.order;
    arr.operationId = $("#operId").val();
    arr.operationType = $("#type").val();
    arr.length = 6;
    var arrs = JSON.stringify(arr);
    return arrs;

    /*var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
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
    return temp;*/
};
function validform() {
    return $("#form_operation").validate();
}

function checkSelected(){
    //初始化
    $("#tr_soft").hide();
    $("#soft_id").val(null);
    $("#labRoom_id").val(null);
    $('input:checkbox[name=select_check]:checked').each(function(k){
        if("SOFTWARE"==$(this).val()){
            $("#tr_soft").show();
        }
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

var childIndex;
//弹出选择学生窗口
function newStudents() {
    childIndex = layer.open({
        type: 1,
        title: '添加学生',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '900px'],
        content: $("#newStudents")
    });
    layer.full(childIndex);
}

//ajax查询班级用户列表
function putSchoolClassesUser(){
    var obj = document.getElementsByName("username");
    var s='';//如果这样定义var s;变量s中会默认被赋个null值
    for(var i=0;i<obj.length;i++){
        if(obj[i].checked) //取到对象数组后，我们来循环检测它是不是被选中
            s+=obj[i].value+'\n';   //如果选中，将value添加到变量s中
    }
    var str = $('#students').val() +'\n' +s;
    $('#students').val(str);
    // $("#newStudents").window('close');
    layer.close(childIndex);
}

function jumpto(id) {
    var bottombox = document.getElementById(id);
    bottombox.scrollIntoView();
}

//ajax查询用户的班级表
function getSchoolClasses(grade){
    $.ajax({
        type: "POST",
        url: zuulUrl + "api/timetable/self/apiGetSchoolClasses?grade=" + grade,
        contentType: "application/json;charset=utf-8",
        headers:{Authorization: getJWTAuthority()},
        async: false,
        success:function(data){
            var jslength=1;
            var currLine=1;
            for(var js2 in data){jslength++;}
            if(jslength==0){alert("本周无课程数据");}else{}
            var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td colspan=3><b>选择班级</b></td></tr>";//新建html字符
            $.each(data,function(key,values){
                if(currLine%3==0){
                    tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick=\"getSchoolClassesUser('"+ key +"')\">" + values + "</a></td><tr>";
                }else  if(currLine%3==1){
                    tableStr = tableStr + "<tr><td><a class='btn btn-common' href='javascript:void(0)' onclick=\"getSchoolClassesUser('"+ key +"')\">" + values + "</a></td>";
                }
                else  if(currLine%3==2){
                    tableStr = tableStr + "<td><a class='btn btn-common' href='javascript:void(0)' onclick=\"getSchoolClassesUser('"+ key +"')\">" + values + "</a></td>";
                }
                currLine=currLine+1
                jslength=jslength+1;
            });
            tableStr = tableStr + "</tr></table>";
            document.getElementById('schoolClasses').innerHTML=tableStr;
            jumpto('schoolClasses');
        },
        error:function(){
            //alert("加载课表失败!");
        }
    });
}

//ajax查询班级用户列表
function getSchoolClassesUser(classNumber){
    $.ajax({
        type: "POST",
        url: zuulUrl + "api/timetable/self/apiGetSchoolClassesUser?classNumber=" + classNumber,
        contentType: "application/json;charset=utf-8",
        headers:{Authorization: getJWTAuthority()},
        dataType:'json',
        async: false,
        success:function(data){
            var jslength=1;
            var currLine=1;
            var allLine=1;
            for(var js2 in data){jslength++;}
            if(jslength==0){alert("本周无课程数据");}else{}

            var tableStr="<table id='listTable' width='80%' cellpadding='0'><tr><td><b>选择学生</b></td><td colspan=3><input class='btn btn-primary btn-lg' type='button' onclick='putSchoolClassesUser()' value='提交'/></td></tr>";//新建html字符
            $.each(data,function(key,values){
                if(currLine%4==0){
                    tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td><tr>";
                }else  if(currLine%4==1){
                    tableStr = tableStr + "<tr><td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
                }else  if(currLine%4==2){
                    tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
                }else if(currLine%4==3){
                    tableStr = tableStr + "<td><input name='username' id='username" + allLine + "' type='checkbox' value='" + key + "' checked='checked' />" + key + "：" + values + "</a></td>";
                }
                //currLine=currLine%4;
                jslength=jslength+1;
                currLine = currLine +1;
                allLine =allLine+1;
            });
            if(currLine%4==0){
                tableStr = tableStr + "</table>";
            }else if(currLine%4==1){
                tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td><td&nbsp;></td></tr></table>";
            }else if(currLine%4==2){
                tableStr = tableStr + "<td>&nbsp;</td><td>&nbsp;</td></tr></table>";
            }else if(currLine%4==3){
                tableStr = tableStr + "<td>&nbsp;</td></tr></table>";
            }

            document.getElementById('schoolClassesUser').innerHTML=tableStr;
            jumpto('schoolClassesUser');
        },
        error:function(){
            //alert("加载课表失败!");
        }
    });
}
function publicTimetable(timetableStyle, selfId, status,relevantEquipment) {
    var arr = new Object();
    arr.selfId = selfId;
    arr.timetableStyle = timetableStyle;
    arr.status = status;
    arr.relevantEquipment = relevantEquipment;   //排课完成生成相关联的设备借用记录
    var arrs = JSON.stringify(arr);
    $.ajax({
        url: zuulUrl + "api/timetable/common/apiTimetablePublic",
        contentType: "application/json;charset=utf-8",
        headers:{Authorization: getJWTAuthority()},
        async: false,
        dataType: "json",
        type: "post",
        data: arrs,
        success: function (json) {
            //refreshBootstrapTable();
            $.ajax({
                url: contextPath + "/openOperationItem/saveAssetReceive?selfId="+selfId,
                async: false,
                dataType: "text",
                type: "post",
                success: function (json) {
                    //refreshBootstrapTable();

                }
            });
        }
    });
    refreshBootstrapTable();
}

/*
*查看学生名单
*/
function timetableCourseStudents(term, selfId) {
    var index = layer.open({
        type: 2,
        title: '查看学生选课情况',
        maxmin: false,
        shadeClose: false,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/timetable/self/timetableCourseStudentList?term=' + term + '&selfId=' + selfId
    });
    layer.full(index);
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

//根据分组，获取已分组名单json的ajax
function getTimetableGroupStudents(selfId, groupId) {
    var returnGroupStudents = "";
    $.ajax({
        url: zuulUrl + "api/timetable/manage/getSelfTimetableGroupStudents?groupId=" + groupId + "&selfId=" + selfId,
        contentType: "application/json;charset=utf-8",
        headers:{Authorization: getJWTAuthority()},
        dataType: "json",
        type: "post",
        async: false,
        success: function (json) {
            returnGroupStudents = json;
        }
    });
    return returnGroupStudents;
}