var authorization ="";
var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl ="";
$(document).ready(function () {
    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
    //获取jwt认证，获取token
    getJWTAuthority();
    var url = zuulUrl + "api/timetable/common/apiViewTimetableInfo";
    var ids = $("#timetableAppointmentIds").val().replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
    $("#table_timetable_info").bootstrapTable({
        //使用get请求到服务器获取数据
        method: "POST",
        //必须设置，不然request.getParameter获取不到请求参数
        contentType: "application/x-www-form-urlencoded",
        //获取数据的Servlet地址
        url: url,
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParams: queryParams,
        ajaxOptions:{
            headers: {Authorization: authorization}
        },
        //表格显示条纹
        striped: true,
        cache: false,
        //启动分页
        pagination: false,
        //是否启用排序
        sortable: true,
        //排序方式
        sortOrder: "asc",
        sortName: 'cname',
        //每页显示的记录数
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
            title: "排课时间",
            field: "timetable",
            width: "25%",
            sortable: true
        }, {
            title: "所属实验室",
            field: "labs",
            width: "25%",
            sortable: true
        }, {
            title: "授课教师",
            field: "teachers",
            width: "10%",
            sortable: true
        }, {
            title: "指导教师",
            field: "tutors",
            width: "10%",
            sortable: true
        }, {
            title: "实验项目",
            field: "items",
            width: "15%",
            sortable: true
        }]
    });
    $('#courseDetailNo').select2({
        width: "95%",
        placeholderOption: "first"
    });
    // 对每一列排课进行初始化
    for (var i = 0; i < ids.length; i++) {
        var timetableAppointmentId = ids[i];
        $('#labRoom_id'+timetableAppointmentId).select2({
            width: "100%",
            closeOnSelect: false,
            placeholder: '请输入实验室',
            placeholderOption: "first",
            ajax: {
                url: zuulUrl + "api/timetable/common/apiGetUsableList",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", getJWTAuthority());
                },
                type: "post",
                data: function (params) {
                    var thisId = this[0].id.replace(/[^0-9]/ig,"");
                    var soft = $('#soft_id' + thisId).val() == null ? [] : $('#soft_id' + thisId).val();
                    var classes = $('#classes' + thisId).val() == null ? [] : $('#classes' + thisId).val();
                    var weeks = $('#weeks' + thisId).val() == null ? [] : $('#weeks' + thisId).val();

                    var arr = new Object();
                    arr.soft =           soft.join();
                    arr.search =         params.term;
                    arr.academyNumber = $('#academyNumber').val();
                    arr.term =           $('#term').val();
                    arr.classes =        classes.join();
                    arr.weekday =        $('#weekday' + thisId).val();
                    arr.courseDetailNo ="";
                    arr.weeks =weeks.join();
                    arr.type = 0;
                    var arrs = JSON.stringify(arr);
                    return arrs;
                   /* var query = {
                        soft: soft.join(),
                        search: params.term,
                        academyNumber: $('#academyNumber').val(),
                        term: $('#term').val(),
                        classes: classes.join(),
                        weekday: $('#weekday' + thisId).val(),
                        courseDetailNo: "",
                        weeks: weeks.join(),
                        type: 0
                    };
                    return query;*/
                },
                results: function (data, page) {
                    return {
                        results: data
                    };
                }
            }
        });

        $('#soft_id'+timetableAppointmentId).select2({
            width: "95%",
            closeOnSelect: false,
            placeholder: '请输入软件...',
            placeholderOption: "first",
            ajax: {
                url: zuulUrl + "api/software/apiSoftWareListBySelect",
                dataType: "json",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", getJWTAuthority());
                },
                delay: 250,//延时0.5秒之后再进行请求
                type: "post",
                data: function (params) {
                    return {
                        search: params.term,
                        academyNumber: $('#academyNumber').val()
                    };
                },
                results: function (data, page) {
                    return {
                        results: data
                    };
                }
            }
        });

        $('#teacherRelated'+timetableAppointmentId).select2({
            width: "89%",
            placeholder: '请输入授课教师...',
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

        $('#tutorRelated'+timetableAppointmentId).select2({
            width: "89%",
            placeholder: '请输入指导教师...',
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

        $('#weekday'+timetableAppointmentId).select2({
            width: "100%",
            placeholder: '请选择星期',
            ajax: {
                url: zuulUrl + "api/timetable/common/apiGetUsableList",
                contentType: "application/json",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", getJWTAuthority());
                },
                dataType: "json",
                type: "post",
                data: function (params) {
                    var thisId = this[0].id.replace(/[^0-9]/ig,"");
                    var soft = $('#soft_id' + thisId).val() == null ? [] : $('#soft_id' + thisId).val();
                    var classes = $('#classes' + thisId).val() == null ? [] : $('#classes' + thisId).val();
                    var weeks = $('#weeks' + thisId).val() == null ? [] : $('#weeks' + thisId).val();
                    var labRoom_id = $('#labRoom_id' + thisId).val() == null ? [] : $('#labRoom_id' + thisId).val();

                    var arr = new Object();
                    arr.soft =           soft.join();
                    arr.search =         params.term;
                    arr.academyNumber = $('#academyNumber').val();
                    arr.term =           $('#term').val();
                    arr.classes =        classes.join();
                    //arr.weekday =        $('#weekday' + thisId).val();
                    arr.labRoomId= labRoom_id.join();
                    arr.courseDetailNo ="";
                    arr.weeks =weeks.join();
                    arr.type = 1;
                    var arrs = JSON.stringify(arr);
                    return arrs;

                    /*var query = {
                        soft: soft.join(),
                        search: params.term,
                        academyNumber: $('#academyNumber').val(),
                        term: $('#term').val(),
                        classes: classes.join(),
                        // weekday: $('#weekday' + thisId).val(),
                        labRoomId: labRoom_id.join(),
                        courseDetailNo: "",
                        weeks: weeks.join(),
                        type: 1
                    };
                    return query;*/
                },
                results: function (data, page) {
                    return {
                        results: data
                    };
                }
            }
        });

        $('#classes'+timetableAppointmentId).select2({
            width: "100%",
            closeOnSelect: false,
            placeholder: '请选择节次',
            placeholderOption: "first",
            allowClear: true,
            ajax: {
                url: zuulUrl + "api/timetable/common/apiGetUsableList",
                contentType: "application/json",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", getJWTAuthority());
                },
                dataType: "json",
                delay: 250,//延时0.5秒之后再进行请求
                type: "post",
                data: function (params) {
                    var thisId = this[0].id.replace(/[^0-9]/ig,"");
                    var soft = $('#soft_id' + thisId).val() == null ? [] : $('#soft_id' + thisId).val();
                    var labRoom_id = $('#labRoom_id' + thisId).val() == null ? [] : $('#labRoom_id' + thisId).val();
                    var weeks = $('#weeks' + thisId).val() == null ? [] : $('#weeks' + thisId).val();

                    var arr = new Object();
                    arr.soft =           soft.join();
                    arr.search =         params.term;
                    arr.academyNumber = $('#academyNumber').val();
                    arr.term =           $('#term').val();
                    arr.labRoomId= labRoom_id.join();
                    //arr.classes =        classes.join();
                    arr.weekday =        $('#weekday' + thisId).val();
                    arr.courseDetailNo ="";
                    arr.weeks =weeks.join();
                    arr.type = 2;
                    var arrs = JSON.stringify(arr);
                    return arrs;

                    /*var query = {
                        soft: soft.join(),
                        search: params.term,
                        academyNumber: $('#academyNumber').val(),
                        term: $('#term').val(),
                        labRoomId: labRoom_id.join(),
                        weekday: $('#weekday' + thisId).val(),
                        courseDetailNo: "",
                        weeks: weeks.join(),
                        type: 2
                    };
                    return query;*/
                },
                results: function (data, page) {
                    return {
                        results: data
                    };
                }
            }
        });

        $('#weeks'+timetableAppointmentId).select2({
            width: "100%",
            closeOnSelect: false,
            placeholder: '请选择周次',
            placeholderOption: "first",
            allowClear: true,
            ajax: {
                url: zuulUrl + "api/timetable/common/apiGetUsableList",
                contentType: "application/json",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", getJWTAuthority());
                },
                dataType: "json",
                delay: 250,//延时0.5秒之后再进行请求
                type: "post",
                data: function (params) {
                    var thisId = this[0].id.replace(/[^0-9]/ig,"");
                    var soft = $('#soft_id' + thisId).val() == null ? [] : $('#soft_id' + thisId).val();
                    var classes = $('#classes' + thisId).val() == null ? [] : $('#classes' + thisId).val();
                    var labRoom_id = $('#labRoom_id' + thisId).val() == null ? [] : $('#labRoom_id' + thisId).val();

                    var arr = new Object();
                    arr.soft =           soft.join();
                    arr.search =         params.term;
                    arr.academyNumber = $('#academyNumber').val();
                    arr.term =           $('#term').val();
                    arr.classes =        classes.join();
                    arr.weekday =        $('#weekday' + thisId).val();
                    arr.courseDetailNo ="";
                    arr.labRoomId =labRoom_id.join();
                    arr.type = 3;
                    var arrs = JSON.stringify(arr);
                    return arrs;

                    /*var query = {
                        soft: soft.join(),
                        search: params.term,
                        academyNumber: $('#academyNumber').val(),
                        term: $('#term').val(),
                        classes: classes.join(),
                        weekday: $('#weekday' + thisId).val(),
                        courseDetailNo: "",
                        labRoomId: labRoom_id.join(),
                        type: 3
                    };
                    return query;*/
                },
                results: function (data, page) {
                    return {
                        results: data
                    };
                }
            }
        });

        //实验项目
        $('#items'+timetableAppointmentId).select2({
            width: "95%",
            closeOnSelect: false,
            placeholder: '请选择实验项目...',
            placeholderOption: "first",
            allowClear: true,
            ajax: {
                url: zuulUrl + "api/operation/apiOperationItemListBySelect",
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
                    arr.courseNumber = $('#courseNumber').val();
                    arr.academyNumber = $('#academyNumber').val();
                    var arrs = JSON.stringify(arr);
                    return arrs;

                    /* var query = {
                         search: params.term,
                         academyNumber: $('#academyNumber').val(),
                         courseNumber: $('#courseNumber').val()
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
    }
    /*
    *二次分批排课弹出窗口
    */

    $("#submitButton").on('click', function () {
        if (validform().form()) {
            var arr = new Object();
            arr.courseNo = $("#courseNo").val();
            arr.courseDetailNo = $("#courseDetailNo").val();
            arr.groupId = 0;
            if($("#groupId").val()!=""){
                arr.groupId = $("#groupId").val();
            }
            arr.weeks = $("#weeks").val();
            arr.weekday = $("#weekday").val();
            arr.sameNumberId = $("#sameNumberId").val();
            if($("#sameNumberId").val()==""){
                arr.sameNumberId =-1;
            }
            arr.status = 10;
            arr.timetableStyle = $("#timetableStyle").val();
            arr.classes = $("#classes").val();
            arr.labRoomIds = $("#labRoom_id").val();
            arr.tearchs = $("#teacherRelated").val();
            arr.items = $("#items").val();
            arr.tutors = $("#tutorRelated").val();
            var arrs = JSON.stringify(arr);
            $.ajax({
                url: zuulUrl + "api/school/apiSaveCourseTimetableAppointment",
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", getJWTAuthority());
                },
                contentType: "application/json;charset=utf-8",
                headers: {Authorization: getJWTAuthority()},
                dataType: "json",
                type: "post",
                //async: false,
                data: arrs,
                success: function (json) {
                }
            });
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        } else {
            alert("请验证输入！");
        }
    })

    $("#form_lab").validate();

    for (var i = 0; i < ids.length; i++) {
        $("#labRoom_id" + ids[i]).change(function () {
            $(this).valid();
        });
        $("#weekday" + ids[i]).change(function () {
            $(this).valid();
        });
        $("#classes" + ids[i]).change(function () {
            $(this).valid();
        });
        $("#weeks" + ids[i]).change(function () {
            $(this).valid();
        });
        $("#teacherRelated" + ids[i]).change(function () {
            $(this).valid();
        });
    }
});

//得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        // limit: params.limit,   //页面大小

        sort: params.sort,
        order: params.order,
        courseNo: $("#courseNo").val()
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
        content: contextPath + '/lims/timetable/course/schoolCourseStudnetList?term=' + term + '&courseNo=' + courseNo
    });
    layer.full(index);
}

function validform() {
    return $("#form_lab").validate();
}

function checkSelected(){
    //初始化
    $("#td_soft").hide();
    $("td[id*=td_soft_id]").hide().val(null);
    $("select[id*=labRoom_id]").val(null);
    $('input:checkbox[name=select_check]:checked').each(function(k){
        if("SOFTWARE"==$(this).val()){
            $("td[id*=td_soft_id]").show();
            $("#td_soft").show();
        }
    })
}

function  doValidProperty(timetableId) {
    $("#weeks"+ timetableId).val("").change();
    $("#labRoom_id"+timetableId).val("").change();
}

function submitTimetable() {
    if (validform().form()) {
        var ids = $("#timetableAppointmentIds").val().replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
        var arr = new Array(ids.length);
        for (var i = 0; i < ids.length; i++) {
            var data = {};//直接排课的教学班编号
            data.courseNo = $("input[name=courseNo]").val();
            data.timetableId = ids[i];
            data.sameNumberId = -1;
            data.term = $("input[name=term]").val();
            data.status = 10;
            data.timetableStyle = 4;
            data.weekday = $("#weekday" + ids[i]).val();
            data.groupId = $("input[name=groupId]").val();
            //所选实验室
            data.labRoomIds = $("#labRoom_id" + ids[i]).val();
            data.classes = $("#classes" + ids[i]).val();
            data.weeks = $("#weeks" + ids[i]).val();
            //所选教师
            data.tearchs = $("#teacherRelated" + ids[i]).val();
            //所选指导教师
            data.tutors = $("#tutorRelated" + ids[i]).val();
            data.items = $("#items" + ids[i]).val();
            data.courseDetailNo = $("#courseDetailNo" + ids[i]).val();
            arr[i] = JSON.stringify(data);
        }
        var arrs = "[" + arr.join() + "]";
        $.ajax({
            url: zuulUrl + 'api/school/saveCopyTimetableGroup',
            contentType: 'application/json; charset=UTF-8',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", getJWTAuthority());
            },
            type: 'POST',
            headers: {Authorization: getJWTAuthority()},
            data: arrs,
            error: function (request) {
                alert('请求错误!');
            },
            success: function (data) {
                if(data == "success"){
                    alert("保存成功！");
                    window.parent.location.reload();
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else if(data == "fail"){
                    alert("与已有排课冲突");
                }else if(data == "repeat"){
                    alert("页面上有重复排课");
                }else{
                    alert("保存失败");
                }
            }
        });
    }else{
        alert("请验证输入");
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