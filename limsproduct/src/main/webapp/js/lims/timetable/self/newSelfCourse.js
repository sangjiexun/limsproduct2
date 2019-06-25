var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl ="";
$(document).ready(function () {
    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
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

    $('#courseNumber').select2({
        width: "89%",
        placeholder: '请选择课程信息...',
        placeholderOption: "first",
        ajax: {
            url: zuulUrl + "api/school/apiSchoolCourseInfoListBySelect",
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", getJWTAuthority());
            },
            contentType: "application/json;charset=utf-8",
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

    $('#term').select2({
        width: "89%",
        placeholder: '请选择学期信息...',
        placeholderOption: "first",
        ajax: {
            url: zuulUrl + "api/school/apiSchoolTermListBySelect",
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
                arr.search = $("#acno").val();
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
                dataType: "json",
                type: "post",
                headers:{Authorization: getJWTAuthority()},
                async: false,
                data: arrs,
                success: function (json) {
                    if (json.responseText == "no") {
                        alert("所选择的实训室资源冲突，请重新选择或者用调整排课操作，谢谢。");
                        isConflict = 0;
                    }
                }
            });
            //window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        } else {
            alert("请验证输入！");
        }
    })

    $("#form_lab").validate();

    $("#labRoom_id").change(function () {
        $(this).valid();
    });

    $("#teacherRelated").change(function () {
        $(this).valid();
    });
});

function validform() {
    return $("#form_lab").validate();
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

// 新建课程
function newSchoolCourseInfo() {
    var url = contextPath + '/timetable/newSchoolCourseInfoForSelf';
    var index = layer.open({
        type: 2,
        title: '新建课程',
        maxmin: true,
        shadeClose: true,
        area: ['600px', '500px'],
        content: url,
        end: function () {
            // refreshBootstrapTable();
        }
    });
    layer.full(index);

}