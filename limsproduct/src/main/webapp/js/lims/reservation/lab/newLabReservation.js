var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl = $("#zuulServerUrl").val() + contextPath + "/timetable/";
var weekdayUrl = zuulUrl + "api/timetable/common/apiWeekDayListBySelect";
var classesUrl = zuulUrl + "api/timetable/common/apiClassListBySelect";
var weekUrl = zuulUrl + "api/timetable/common/apiWeekListBySelect";
var usableListUrl = zuulUrl + "api/timetable/common/apiGetUsableList";
var usableListDateUrl = zuulUrl + "api/timetable/common/apiGetUsableDateList";
var currTermUrl = zuulUrl + "api/school/apiCurrSchoolTerm";
var dateUrl = zuulUrl + "api/common/apiSystemTimeList";
var saveReserByWeekUrl = zuulUrl + "api/labReservation/apiSaveLabRoomReservation";
var saveReserByDateUrl = zuulUrl + "api/labReservation/apiSaveLabRoomReservationByDate";
var saveReserByDateToWeekUrl = zuulUrl + "api/labReservation/apiSaveLabRoomReservationDateToWeek";
var termId;
var activitynumberType;
// 单双击
$(document).ready(function () {
// 单双击
    refreshRule('section_box');
    // 1 获取第一个的value
    var sectionBox = document.querySelector('#section_box');
    var checkboxs = sectionBox.getElementsByClassName('layui-form-checkbox');
    $("#test1").click(function () {
        // 获取复选框div

        // 获取div里的所有复选框

        // 获取第一个复选框里的value
        var bV = checkboxs[0].getAttribute('value');
        console.log('第一个复选框里的value值：', bV)
    })
    // 2 设置value值
    $("#test2").next().click(function () {
        var v = $("#test2").val();
        checkboxs[1].setAttribute("value", v);
        // 刷新获取
        checkboxs = sectionBox.getElementsByClassName('layui-form-checkbox');
        console.log("第2个复选框里的value值：[" + checkboxs[1].getAttribute('value') + "]")
    })
    // 获取week_box里已经选中的复选框
    $("#test3").click(function () {
        var checkeds = sectionBox.getElementsByClassName('layui-form-checked');
        console.log(checkeds)
        // value
        for (var i = 0; i < checkeds.length; i++) {
            console.log('周：' + (Number(checkeds[i].getAttribute('sectionson')) + 1) + ' 下标：' + i + ' value:' + checkeds[i].getAttribute('value'))
        }

    })

    $("#section_box .layui-form-checkbox").each(function (i, j) {
        j.onclick = function () {
            this.classList.toggle('layui-form-checked');
            // oldChecked toggle
            if (this.getAttribute('oldChecked') == null) {
                this.setAttribute('oldChecked', '');
            } else {
                this.removeAttribute('oldChecked');
            }
        }
    });

    //获取字典表
    var eventTypeUrl = zuulUrl + "api/common/apiCDictionaryList?category=lab_room_lending_type";
    $.ajax({
        url: eventTypeUrl, //此次url改为真正需要的url
        success: function (data, status) {
            $.each(data.results, function (index, item) {
                $("#activity").append(  //此处向select中循环绑定数据
                    "<option value=" + item.id + ">" + item.text + "</option>");
            });
        },
    });

    //获取字典表
    var userObjectUrl = zuulUrl + "api/common/apiCDictionaryList?category=lab_room_lending_user_type";
    $.ajax({
        url: userObjectUrl, //此次url改为真正需要的url
        success: function (data, status) {
            $.each(data.results, function (index, item) {
                $("#userType").append(  //此处向select中循环绑定数据
                    "<option value=" + item.id + ">" + item.text + "</option>");
            });
        },
    });



// })
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate;

        //日期范围
        lay('.test-item').each(function(){
            laydate.render({
                elem: this
                ,trigger: 'click'
                ,done: function(value, date, endDate){
                    // console.log(value); //得到日期生成的值，如：2017-08-18
                    // console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    // console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                    getTime1(value)
                }
            });
        });
        // laydate.render({
        //     elem: '#date-range',
        //     // trigger: 'click'
        //     // , range: '~'
        // });

        //时间范围
        // laydate.render({
        //     elem: '#time-range'
        //     , type: 'time'
        //     , range: '~'
        // });

        //表单初始赋值
        // form.val('cappointment_tab', {
        //     "term": "默认显示当前学期"
        // });

    });

    layui.use(['layer', 'form', 'element', 'jquery', 'layer'], function () {
        var layer = layui.layer
            , form = layui.form
            , element = layui.element
            , $ = layui.$
            , layer = layui.layer;
        $('#dateorsection').val(1);
        //获取当前学期
        $.ajax({
            url: currTermUrl,
            headers: {Authorization: getJWTAuthority()},
            async: false,
            type: "GET",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                console.log(result);
                form.val('cappointment_tab', {
                    "term": result.text
                });
                termId = result.id;
            }
        });

        $("#week_all").click(function () {//周次全选
            $("#week_box input[type='checkbox']").prop("checked", true);
            form.render('checkbox');
        })
        $("#time_all").click(function () {//时间全选
            $("#time_box input[type='checkbox']").prop("checked", true);
            form.render('checkbox');
        })

        $("#week_opposite").click(function () {//周次反选
            $("#week_box input[type='checkbox']").each(function () {
                if ($(this).prop("checked")) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            })
            form.render('checkbox');
        })
        $("#time_opposite").click(function () {//时间反选
            $("#time_box input[type='checkbox']").each(function () {
                if ($(this).prop("checked")) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            })
            form.render('checkbox');
        })

        $("#week_none").click(function () {//周次全不选
            $("#week_box input[type='checkbox']").prop("checked", false);
            form.render("checkbox");
        })
        $("#time_none").click(function () {//时间全不选
            $("#time_box input[type='checkbox']").prop("checked", false);
            form.render("checkbox");
        })

        $("#section_all").click(function () {//节次全选
            console.log($("#section_box input[type='checkbox']"))
            $("#section_box input[type='checkbox']").prop("checked", true);
            form.render('checkbox');
        })

        $("#section_opposite").click(function () {//节次反选
            $("#section_box input[type='checkbox']").each(function () {
                if ($(this).prop("checked")) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            })
            form.render('checkbox');
        })

        $("#section_none").click(function () {//节次全不选
            $("#section_box input[type='checkbox']").prop("checked", false);
            form.render("checkbox");
        })

            // form.on('checkbox(classes_choose)', function(data){
            //     var weekFlag = $('#weekday').val();
            //     if(weekFlag!=null&&weekFlag!=''&&weekFlag!=undefined){
            //     // $("#week_div").show();
            //     // $("#week_box input[type='checkbox']").prop("checked", true);
            //         getWeeks1();
            //     }
            // });
        function showWeeks(section){
            $("#"+section+" .layui-form-checkbox").each(function(i,j){
                j.onclick=function(){
                    this.classList.toggle('layui-form-checked');
                    // oldChecked toggle
                    if(this.getAttribute('oldChecked')==null){
                        this.setAttribute('oldChecked','');
                    }else{
                        this.removeAttribute('oldChecked');
                    }
                    var weekFlag = $('#weekday').val();
                    if (weekFlag != null && weekFlag != '' && weekFlag != undefined) {
                        // $("#week_div").show();
                        // $("#week_box input[type='checkbox']").prop("checked", true);
                        getWeeks1();
                    }
                }
            });
        }
        form.on('checkbox(allChoose)', function (data) {
        // $(".section-class").click(function () {
            $("#week_div").show();
            $("#week_box input[type='checkbox']").prop("checked", true);
            var weekday = $('#weekday').val();
            var data1 = JSON.stringify({
                "classes": "1,2,3,5,6",
                "labRoomId": "176",
                "term": termId,
                "type": 3,
                "weekday": weekday
            });
            $.ajax({
                // url: weekUrl + "?term=16&weekday=-1",
                url: usableListUrl,
                headers: {Authorization: getJWTAuthority()},
                data: data1,
                async: false,
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    //add_role_name给select定义的id
                    $("#week_box").empty();
                    for (var i = 0; i < list.length; i++) {
                        $("#week_box").append(" <input type=\"checkbox\" name=\"week\" title='第" + list[i].text + "周' value='" + list[i].id + "' lay-filter=\"allChoose_1\">");
                        form.render("checkbox");
                    }
                }
            });

        })
        getWeekday1();
        getClasses1();
        getWeeks1();
        getTime1('');

        function getWeekday() {
            $.ajax({
                url: weekdayUrl,
                type: "GET",
                headers: {Authorization: getJWTAuthority()},
                async: false,
                dataType: "json",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    var weekday = document.getElementById("weekday");        //add_role_name给select定义的id
                    for (var i = 0; i < list.length; i++) {
                        var option = document.createElement("option");    // 创建添加option属性
                        option.setAttribute("value", list[i].id);                  // 给option的value添加值
                        option.innerText = list[i].text;             // 打印option对应的纯文本 （超级管理员、管理员）
                        weekday.appendChild(option);                          // 给select 添加option子标签
                        form.render("select");                                // 刷性select，显示出数据
                    }
                }
            });
        }
        function getWeekday1(){
            var weekss = "";
            // $("input:checkbox[name='week']:checked").each(function() {
            //     weekss += $(this).val()+",";
            // });
            $("#week_box >div").each(function(){
                // alert($(this).attr("id"));  //打印子div的ID
                if($(this).hasClass('layui-form-checked')){
                    weekss += $(this).attr("value")+",";
                }
            });
            weekss = weekss.slice(0,weekss.length-1);
            var classs = "";
            // $("input:checkbox[name='classes']:checked").each(function() {
            //     classs += $(this).val()+",";
            // });
            $("#section_box >div").each(function(){
                // alert($(this).attr("id"));  //打印子div的ID
                if($(this).hasClass('layui-form-checked')){
                    classs += $(this).attr("value")+",";
                }
            });
            classs = classs.slice(0,classs.length-1);
            var data1 = JSON.stringify({
                "weeks": weekss,
                "labRoomId": "176",
                "term": termId,
                "type": 1,
                "classes": classs
            });
            $.ajax({
                url: usableListUrl,
                type: "POST",
                data: data1,
                headers: {Authorization: getJWTAuthority()},
                async: false,
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    var weekday = document.getElementById("weekday");
                    var option = document.createElement("option");
                    // option.setAttribute("value", '');                
                    // option.innerText = "请选择123";             
                    // weekday.appendChild(option);          
                    for (var i = 0; i < list.length; i++) {
                           // 创建添加option属性
                        var option = document.createElement("option");
                        option.setAttribute("value", list[i].id);                  // 给option的value添加值
                        option.innerText = "星期"+list[i].text;             // 打印option对应的纯文本 （超级管理员、管理员）
                        weekday.appendChild(option);                          // 给select 添加option子标签
                        form.render("select");                                // 刷性select，显示出数据
                    }
                }
            });
        }

        function getClasses() {
            $.ajax({
                url: classesUrl,
                type: "GET",
                headers: {Authorization: getJWTAuthority()},
                async: false,
                dataType: "json",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    //add_role_name给select定义的id
                    for (var i = 0; i < list.length; i++) {
                        var tempClasses = '<div class="layui-unselect layui-form-checkbox " value="' + list[i].id + '">';
                        tempClasses += '<span>' + list[i].text + '</span>';
                        tempClasses += '<i class="layui-icon layui-icon-ok"></i></div>';
                        $("#section_box").append(tempClasses);
                        refreshRule('section_box');
                        /*$("#section_box").append(" <input type=\"checkbox\" name=\"classes\" title='" + list[i].text + "' value='" + list[i].id + "' lay-filter=\"allChoose\">");*/
                        form.render();                                // 刷性select，显示出数据
                    }
                    allRule('section_all', 'section_box')
                    oppositeRule('section_opposite', 'section_box')
                    noneRule('section_none', 'section_box')
                    allRule('week_all', 'week_box')
                    oppositeRule('week_opposite', 'week_box')
                    noneRule('week_none', 'week_box')
                }
            });
        }
        function getClasses1() {
            var weekday = $('#weekday').val();
            var weekss = "";
            // $("input:checkbox[name='week']:checked").each(function() {
            //     weekss += $(this).val()+",";
            // });
            $("#week_box >div").each(function(){
                // alert($(this).attr("id"));  //打印子div的ID
                if($(this).hasClass('layui-form-checked')){
                    weekss += $(this).attr("value")+",";
                }
            });
            weekss = weekss.slice(0,weekss.length-1);
            var data1 = JSON.stringify({
                "weeks": weekss,
                "labRoomId": $('#labRoomId').val(),
                "term": termId,
                "type": 2,
                "weekday": weekday
            });
            $.ajax({
                url: usableListUrl,
                type: "POST",
                data: data1,
                headers: {Authorization: getJWTAuthority()},
                async: false,
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    //add_role_name给select定义的id
                    for (var i = 0; i < list.length; i++) {
                        // $("#section_box").append(" <input type=\"checkbox\" name=\"classes\" title='第" + list[i].text + "节' value='" + list[i].id + "' lay-filter=\"classes_choose\" >");
                        var x = "<div lay-filter='classes_choose' name='classes' value=" + list[i].id + " class=\"layui-unselect layui-form-checkbox\"><span>第"+ list[i].text+"节</span><i class='layui-icon layui-icon-ok'></i></div>"
                        // $("#section_box").append(" <input type=\"checkbox\" name=\"classes\" title='第" + list[i].text + "111节' value='" + list[i].id + "' lay-filter=\"classes_choose\" >");
                        $("#section_box").append(x);
                        form.render("checkbox");
                    }
                    allRule('section_all', 'section_box')
                    oppositeRule('section_opposite', 'section_box')
                    noneRule('section_none', 'section_box')
                    optionRule('section_box')
                    clickRule('section_box')
                    showWeeks('section_box')
                }
            });
        }
        function getWeeks1() {
            var weekday = $('#weekday').val();
            var classs = "";
            $("#section_box >div").each(function(){
                // alert($(this).attr("id"));  //打印子div的ID
                if($(this).hasClass('layui-form-checked')){
                    classs += $(this).attr("value")+",";
                }
            });
            classs = classs.slice(0,classs.length-1);
            console.log(classs);
            var data1 = JSON.stringify({
                "classes": classs,
                "labRoomId": $('#labRoomId').val(),
                "term": termId,
                "type": 3,
                "weekday": weekday
            });
            $.ajax({
                // url: weekUrl + "?term=16&weekday=-1",
                url: usableListUrl,
                headers: {Authorization: getJWTAuthority()},
                data: data1,
                async: false,
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    //add_role_name给select定义的id
                    $("#week_box").empty();
                    for (var i = 0; i < list.length; i++) {
                        var x = "<div lay-filter='allChoose_1' name='week' id='week"+ list[i].id +"' value=" + list[i].id + " class=\"layui-unselect layui-form-checkbox\"><span>第"+ list[i].text+"周</span><i class='layui-icon layui-icon-ok'></i></div>"
                        // $("#week_box").append(" <input type=\"checkbox\" name=\"week\" title='第" + list[i].text + "周' value='" + list[i].id + "' lay-filter=\"allChoose_1\">");
                        $("#week_box").append(x);
                        form.render("checkbox");
                    }
                    allRule('week_all', 'week_box');
                    oppositeRule('week_opposite', 'week_box');
                    noneRule('week_none', 'week_box');
                    optionRule('week_box')
                    clickRule('week_box')
                }
            });
        }
        function getTime1(date) {
            var data1 = JSON.stringify({
                "labRoomId": $('#labRoomId').val(),
                "term": termId,
                "type": 2,
                "date":date,
            });
            $.ajax({
                url: usableListDateUrl,
                type: "POST",
                data: data1,
                headers: {Authorization: getJWTAuthority()},
                async: false,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    console.log(list);
                    //add_role_name给select定义的id
                    for (var i = 0; i < list.length; i++) {
                        // $("#time_box").append(" <input type=\"checkbox\" name=\"times\" title='" + list[i].text + "' value='" + list[i].id + "' lay-filter=\"date_choose\" >");
                        var x = "<div lay-filter='date_choose' name='times' id='times"+ list[i].id +"' value=" + list[i].id + " class=\"layui-unselect layui-form-checkbox\"><span>"+ list[i].text+"</span><i class='layui-icon layui-icon-ok'></i></div>"
                        $("#time_box").append(x);
                        form.render("checkbox");
                    }
                    allRule('time_all', 'time_box');
                    oppositeRule('time_opposite', 'time_box');
                    noneRule('time_none', 'time_box');
                    optionRule('time_box')
                    clickRule('time_box')
                }
            });
        }

        // form.on('switch(outside-sw)', function(data){
        //     console.log(data.elem); //得到checkbox原始DOM对象
        //     console.log(data.elem.checked); //开关是否开启，true或者false
        //     console.log(data.value); //开关value值，也可以通过data.elem.value得到
        //     console.log(data.othis); //得到美化后的DOM对象
        // });
        form.on('submit(reservationSubmit)', function(data){
            var data1;
            if($('#dateorsection').val() == 1){
                var classs = "";
                // $("input:checkbox[name='classes']:checked").each(function() { // 遍历name=standard的多选框
                //     classs += $(this).val()+",";
                // });
                $("#section_box >div").each(function(){
                    // alert($(this).attr("id"));  //打印子div的ID
                    if($(this).hasClass('layui-form-checked')){
                        classs += $(this).attr("value")+",";
                    }
                });
                classs = classs.slice(0,classs.length-1);
                var weekss = "";
                // $("input:checkbox[name='week']:checked").each(function() {
                //     weekss += $(this).val()+",";
                // });
                $("#week_box >div").each(function(){
                    // alert($(this).attr("id"));  //打印子div的ID
                    if($(this).hasClass('layui-form-checked')){
                        weekss += $(this).attr("value")+",";
                    }
                });
                weekss = weekss.slice(0,weekss.length-1);
                data.field.sections = classs;
                data.field.weeks = weekss;
                data.field.term = termId;
                data.field.labRoom = $("#labRoomId").val();
                data.field.number = $("#number").val();
                data.field.eventType = $("#activity").val();
                data.field.objectType = $("#userType").val();
                data.field.reason = $("#reason").val();
                data.field.content = $("#content").val();
                data.field.telephone = $("#telephone").val();
                if(data.field.outside == "on") {
                    data.field.outside = "1";
                } else {
                    data.field.outside = "0";
                }
                if(activitynumberType == 1){
                    data.field.activitynumber = $('#activitynumber_input').val();
                }else {
                    data.field.activitynumber = $('#activitynumber_select').val();
                }
                data1 = JSON.stringify(data.field);
                $.ajax({
                    // url: weekUrl + "?term=16&weekday=-1",
                    url: saveReserByWeekUrl,
                    headers: {Authorization: getJWTAuthority()},
                    data: data1,
                    async: false,
                    type: "POST",
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        if(result == "success"){
                            alert('实验室预约成功')
                        }else if(result == "fail"){
                            alert('实验室预约失败')
                        }else if(result == "NeedContinuation"){
                            alert('请预约连续节次(时间段)!')
                        }else if(result == "LIMIT"){
                            alert('当前预约的时间在实验室禁用时间段内，请重新预约!')
                        }else if(result == "Conflict"){
                            alert('当前预约的时间在实验室占用期间，请重新预约!')
                        }else if(result == "NoOpen"){
                            alert('当前预约的时间不在实验室开放时间段内，请重新预约!')
                        }
                    }
                });
            }else if ($('#dateorsection').val() == 2){
                data.field.term = termId;
                data.field.date = $('#date-range').val();
                data.field.labRoom = $("#labRoomId").val();
                data.field.eventType = $("#activity").val();
                data.field.objectType = $("#userType").val();
                data.field.reason = $("#reason").val();
                data.field.content = $("#content").val();
                data.field.telephone = $("#telephone").val();
                var timess = "";
                $("#time_box >div").each(function(){
                    // alert($(this).attr("id"));  //打印子div的ID
                    if($(this).hasClass('layui-form-checked')){
                        timess += $(this).attr("value")+",";
                    }
                });
                // timess = timess.slice(0,timess.length-1);
                data.field.time = timess;
                if(data.field.outside == "on") {
                    data.field.outside = "1";
                } else {
                    data.field.outside = "0";
                }
                if(activitynumberType == 1){
                    data.field.activitynumber = $('#activitynumber_input').val();
                }else {
                    data.field.activitynumber = $('#activitynumber_select').val();
                }
                data1 = JSON.stringify(data.field);
                $.ajax({
                    // url: weekUrl + "?term=16&weekday=-1",
                    url: saveReserByDateToWeekUrl,
                    headers: {Authorization: getJWTAuthority()},
                    data: data1,
                    async: false,
                    type: "POST",
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        if(result == "success"){
                            alert('实验室预约成功')
                        }else if(result == "fail"){
                            alert('实验室预约失败')
                        }else if(result == "NeedContinuation"){
                            alert('请预约连续节次(时间段)!')
                        }else if(result == "LIMIT"){
                            alert('当前预约的时间在实验室禁用时间段内，请重新预约!')
                        }else if(result == "Conflict"){
                            alert('当前预约的时间在实验室占用期间，请重新预约!')
                        }else if(result == "NoOpen"){
                            alert('当前预约的时间不在实验室开放时间段内，请重新预约!')
                        }
                    }
                });
            }
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);//关闭当前页
            return false;
        });
    });

//触发周次显示
    function weekShow() {
        $("#week_div").show();

    }

    <!-- 执行渲染, 把原始select美化~~ -->
    <!-- 这里的weekend是xm-select的属性，是多选的ID, 如多处使用请保证全局唯一 -->

    $(".section_btn").click(
        function () {
            $(this).addClass("breadcrumb_select").siblings("a").removeClass("breadcrumb_select");
            $('#dateorsection').val(1);
        }
    );
    $(".date_btn").click(
        function () {
            $(this).addClass("breadcrumb_select").siblings("a").removeClass("breadcrumb_select");
            $('#dateorsection').val(2);
        }
    );

    <!-- 模式切换 -->
    $(".date_item").hide();
    $(".time_item").hide();

    $(".section_btn").click(
        function () {
            $(".date_item").hide();
            $(".time_item").hide();
            $(".day_item").show();
            $(".week_item").show();
            $(".section_item").show();

        }
    );

    $(".date_btn").click(
        function () {
            $(".day_item").hide();
            $(".week_item").hide();
            $(".section_item").hide();
            $(".date_item").show();
            $(".time_item").show();
        }
    );

    function saveLabReservation() {
        function saveLabRoomLending() {
            var labRoomId = $("#labRoom").val();
            var myData = {
                'lendingTime': $("input[name='lendingTime']").val(),
                'reason': $("#reason").val(),
                'number': $("#number").val(),
                'lendingUserPhone': $("#lendingUserPhone").val(),
                'lendingUnit': $("#lendingUnit").val(),
                'class': $("#class").val(),
                'lendingType': $("#lendingType").val(),
                'lendingUserType': $("#lendingUserType").val(),
                'teacher': teacher,
                'reservationTime': $("#reservationTime").val()
            };
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/labRoomLending/saveLabRoomLending?labRoomId=" + labRoomId,
                data: myData,
                dataType: 'text',
                success: function (data) {
                    console.log(data);
                    if (data == "reserved") {
                        alert("借用失败，您选的时间段已经被预约！");
                    } else if (data == "lent") {
                        alert("借用失败，您选的时间段已经被借用！");
                    } else if (data == "noDean") {
                        alert("借用失败，未找到您所属学院的系主任！");
                    } else if (data == "error") {
                        alert("您还未通过培训,请先预约培训!");
                        window.location.reload();
                    } else if (data == "errorType2") {
                        alert("您还未通过单独培训!");
                        window.location.reload();
                    } else {
                        alert("操作成功，请等待审核！");
                        window.location.href = "${pageContext.request.contextPath}/LabRoomReservation/labRoomList?currpage=1"
                    }
                }
            })
        }
    }

// 重写
    function checkedOn(ipt, num) {
        ipt[num].classList.add('layui-form-checked');
        ipt[num].setAttribute('oldChecked', '');
        // 没有oldChecked 为null 有则为 ''
        // 获取被选中的value
        console.log('下标【' + num + '】被双击选中的value值：' + ipt[num].getAttribute('value'))
        // 存在一个bug,被双击的值会出现两次 还未解决
    }

    function getJWTAuthority() {
        var authorization = "";
        initDirectoryEngine({
            getHostsUrl: contextPath + "/shareApi/getHosts",
            getAuthorizationUrl: contextPath + "/shareApi/getAuthorization"
        });
        getAuthorization({
            async: false,
            success: function (data) {
                authorization = data;
            }
        });
        return authorization;
    }
    

})

function changeTypeNum() {
    var number_select = document.getElementById("number_select");
    var number_input = document.getElementById("number_input");
    if(number_select.style.display == "block"){
        activitynumberType = 1;
        number_select.style.display = "none"
    }else{
        activitynumberType = 0;
        number_select.style.display = "block"
    }
    if(number_input.style.display == "block"){
        number_input.style.display = "none"
    }else{
        number_input.style.display = "block"
    }
}

