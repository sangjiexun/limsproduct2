var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function () {
    $('#teacher').select2({
        width: "89%",
        placeholder: '请选择授课教师...',
        placeholderOption: "first",
        ajax: {
            url: contextPath + "/lims/api/user/apiUserListBySelect",
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
            url: contextPath + "/lims/api/school/apiSchoolCourseInfoListBySelect",
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var query = {
                    search: params.term
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

    $('#term').select2({
        width: "89%",
        placeholder: '请选择学期信息...',
        placeholderOption: "first",
        ajax: {
            url: contextPath + "/lims/api/school/apiSchoolTermListBySelect",
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var query = {
                    search: params.term
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
            url: contextPath + "/lims/api/school/apiSchoolAcademyListBySelect",
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var query = {
                    search: params.term
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
                url: contextPath + "/lims/api/timetable/self/apiSaveTimetableSelfCourse",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                type: "post",
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