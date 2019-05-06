var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function () {
    $('#labRoom_id').select2({
        width: "85%",
        closeOnSelect: false,
        placeholder: '请输入关键词...',
        ajax: {
            url: contextPath + "/lims/api/labroom/apiLabRoomListByDirectSelect",
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var soft = $('#soft_id').val();
                var courseNo = $('#courseNo').val();

                var query = {
                    soft: soft.join(),
                    search: params.term,
                    academyNumber: $('#academyNumber').val(),
                    courseNo:courseNo,
                    term: -1,
                    classes: -1,
                    weekday: -1,
                    courseDetailNo: "",
                    weeks: ""
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

    $('#labRoomDevice_id').select2({
        width: "85%",
        closeOnSelect: false,
        placeholder: '请输入关键词...',
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

    $('#soft_id').select2({
        width: "95%",
        closeOnSelect: false,
        placeholder: '请输入软件...',
        placeholderOption: "first",
        ajax: {
            url: contextPath + "/lims/api/software/apiSoftWareListBySelect",
            dataType: "json",
            delay: 250,//延时0.5秒之后再进行请求
            type: "post",
            data: function (params) {
                var query = {
                    search: params.term,
                    academyNumber: $('#academyNumber').val()
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

    $('#teacherRelated').select2({
        width: "85%",
        closeOnSelect: false,
        placeholder: '请输入关键词...',
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

    $('#tutorRelated').select2({
        width: "85%",
        closeOnSelect: false,
        placeholder: '请输入关键词...',
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
    /*
    *直接排课弹出窗口
    */
    $("#submitButton").on('click', function () {
        if (validform().form()) {
            var arr = new Object();
            arr.courseNo = $("#courseNo").val();
            arr.labRoomIds = $("#labRoom_id").val();
            arr.tearchs = $("#teacherRelated").val();
            arr.tutors = $("#tutorRelated").val();
            arr.status = 2;
            /*arr.push(courseNo);
            arr.push(labRoomDTO);
            arr.push(tearchDTO);
            arr.push(tutorDTO);*/
            //var str = {"courseNo":"a-b-c", "tearchs":["a","b"],"tutors":["a","b"],"labRoomIds":["a","b"]}
            var arrs = JSON.stringify(arr);
            $.ajax({
                url: contextPath + "/lims/api/school/apiSaveTimetableAppointmentByEduDirectCourse",
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
        }else {
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