var contextPath = $("meta[name='contextPath']").attr("content");
// var zuulUrl ="";
var zuulUrl = $("#zuulServerUrl").val() + contextPath + "/timetable/";
var studentTimetableUrl = zuulUrl + "api/school/judgeTimetableConflictByStudent";
$(document).ready(function () {
    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
    layui.use(['layer', 'form', 'element', 'jquery', 'layer'], function () {
        var layer = layui.layer
            , form = layui.form
            , element = layui.element
            , $ = layui.$
            , layer = layui.layer;
        getWeekday1();
        getClasses1();
        getWeeks1();
        function getWeekday1(){
            var term = $('#term').val();
            // var data1 = JSON.stringify({
            //     "term": $('#term').val(),
            //     "type": 2,
            // });
            $.ajax({
                url: zuulUrl + "api/timetable/common/apiDateListJudgeConflictByStu?type=2&termId="+term,
                type: "GET",
                // data: data1,
                headers: {Authorization: getJWTAuthority()},
                // async: false,
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    //add_role_name给select定义的id
                    for (var i = 0; i < list.length; i++) {
                        // listclass += list[i].id+",";
                        // $("#section_box").append(" <input type=\"checkbox\" name=\"classes\" id='classes" + list[i].id + "' title='" + list[i].text + "' value='" + list[i].id + "' lay-filter=\"classes_choose\" >");
                        var x = "<div lay-filter='classes_choose' name='weekdays' value=" + list[i].id + " class=\"layui-unselect layui-form-checkbox\"><span>"+ list[i].text+"</span><i class='layui-icon layui-icon-ok'></i></div>"
                        $("#weekday_box").append(x);
                        form.render("checkbox");
                    }
                    // var x = "<div lay-filter='classes_choose' name='classes' value='111' class=\"layui-unselect layui-form-checkbox\"><span>1111</span><i class='layui-icon layui-icon-ok'></i></div>"
                    // $("#section_box").append(x);
                    allRule('weekday_all', 'weekday_box')
                    oppositeRule('weekday_opposite', 'weekday_box')
                    noneRule('weekday_none', 'weekday_box')
                    optionRule('weekday_box')
                    clickRule('weekday_box')
                    // showWeeks('weekday_box')
                }
            });
        }


        function getClasses1() {
            var term = $('#term').val();
            // var data1 = JSON.stringify({
            //     "term": $('#term').val(),
            //     "type": 2,
            // });
            $.ajax({
                url: zuulUrl + "api/timetable/common/apiDateListJudgeConflictByStu?type=3&termId="+term,
                type: "GET",
                // data: data1,
                headers: {Authorization: getJWTAuthority()},
                // async: false,
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    //add_role_name给select定义的id
                    for (var i = 0; i < list.length; i++) {
                        // listclass += list[i].id+",";
                            // $("#section_box").append(" <input type=\"checkbox\" name=\"classes\" id='classes" + list[i].id + "' title='" + list[i].text + "' value='" + list[i].id + "' lay-filter=\"classes_choose\" >");
                            var x = "<div lay-filter='classes_choose' name='classes' value=" + list[i].id + " class=\"layui-unselect layui-form-checkbox\"><span>"+ list[i].text+"</span><i class='layui-icon layui-icon-ok'></i></div>"
                            $("#section_box").append(x);
                            form.render("checkbox");
                    }
                    // var x = "<div lay-filter='classes_choose' name='classes' value='111' class=\"layui-unselect layui-form-checkbox\"><span>1111</span><i class='layui-icon layui-icon-ok'></i></div>"
                    // $("#section_box").append(x);
                    allRule('section_all', 'section_box')
                    oppositeRule('section_opposite', 'section_box')
                    noneRule('section_none', 'section_box')
                    optionRule('section_box')
                    clickRule('section_box')
                    // showWeeks('section_box')
                }
            });
        }

        function getWeeks1() {
            var term = $('#term').val();
            // var data1 = JSON.stringify({
            //     "term": $('#term').val(),
            //     "type": 2,
            // });
            $.ajax({
                // url: weekUrl + "?term=16&weekday=-1",
                url: zuulUrl + "api/timetable/common/apiDateListJudgeConflictByStu?type=1&termId="+term,
                headers: {Authorization: getJWTAuthority()},
                // data: data1,
                // async: false,
                type: "GET",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    var list = result.results;    //返回的数据
                    for (var i = 0; i < list.length; i++) {
                            // $("#week_box").append(" <input type=\"checkbox\" name=\"week\" title='第" + list[i].text + "周' value='" + list[i].id + "' lay-filter=\"allChoose_1\">");
                            var x = "<div lay-filter='allChoose_1' name='week' id='week"+ list[i].id +"' value=" + list[i].id + " class=\"layui-unselect layui-form-checkbox\"><span>第"+ list[i].id+"周</span><i class='layui-icon layui-icon-ok'></i></div>"
                            $("#week_box").append(x);
                            form.render("checkbox");
                    }
                    allRule('week_all', 'week_box');
                    oppositeRule('week_opposite', 'week_box');
                    noneRule('week_none', 'week_box');
                    optionRule('week_box')
                    clickRule('week_box')
                    // showClasses('week_box')
                }
            });
        }
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
                    getWeeks1();
                }
            });
        }
        function showClasses(section){
            $("#"+section+" .layui-form-checkbox").each(function(i,j){
                j.onclick=function(){
                    this.classList.toggle('layui-form-checked');
                    // oldChecked toggle
                    if(this.getAttribute('oldChecked')==null){
                        this.setAttribute('oldChecked','');
                    }else{
                        this.removeAttribute('oldChecked');
                    }
                    getClasses1();
                }
            });
        }
        form.on('checkbox(allChoose_1)', function(data){
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //是否被选中，true或者false
            console.log(data.value); //复选框value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
            getClasses1();
        });
        form.on('checkbox(classes_choose)', function(data){
            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //是否被选中，true或者false
            console.log(data.value); //复选框value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
            getWeeks1();
        });
        $("#weekday").change(function () {
            $(this).valid();
            getClasses1();
            getWeeks1();
        });
        $("#labRoom_id").change(function () {
            $(this).valid();
            getClasses1();
            getWeeks1();
        });
        form.on('submit(timetableSubmit)', function(data){
            var data1;
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
                // classs = classs.slice(0,classs.length-1);
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
                // weekss = weekss.slice(0,weekss.length-1);
                var weekdayss = "";
                // $("input:checkbox[name='week']:checked").each(function() {
                //     weekss += $(this).val()+",";
                // });
                $("#weekday_box >div").each(function(){
                    // alert($(this).attr("id"));  //打印子div的ID
                    if($(this).hasClass('layui-form-checked')){
                        weekdayss += $(this).attr("value")+",";
                    }
                });
                // weekdayss = weekdayss.slice(0,weekdayss.length-1);
                data.field.sections = classs;
                data.field.weeks = weekss;
                data.field.termId = $("#term").val();
                data.field.courseNo = "225151-17-10061363";
                // data.field.courseNo = $("#courseNo").val();
                data.field.weekdays = weekdayss;
                data1 = JSON.stringify(data.field);
                $.ajax({
                    url: zuulUrl + "api/school/judgeTimetableConflictByStudent",
                    headers: {Authorization: getJWTAuthority()},
                    data: data1,
                    async: false,
                    type: "POST",
                    contentType: "application/json;charset=UTF-8",
                    success: function (result) {
                        $("#table_student").html("");
                        console.log(result);
                        var section = result.data[0].sections;
                        var week = result.data[0].weeks;
                        var weekday = result.data[0].weekdays;
                        var sectionss=section.slice(0,section.length-1).split(",");
                        var weekss=week.slice(0,week.length-1).split(",");
                        var weekdayss=weekday.slice(0,weekday.length-1).split(",");

                        var str = "";
                        str+="<table class='tab_stu' id='tab_stu' border='1' align='center'><caption>";
                        str+="学生判冲";
                        str+="</caption>";
                        str+="<thead>";
                        str+="<tr>";
                        str+="<th>星期</th>";
                        str+="<th>节次</th>";
                        for(var i=0;i<weekss.length;i++){
                            str+="<th>第"+weekss[i]+"周</th>";
                        }
                        str+="</tr>";
                        str+="</thead>";
                        str+="<tbody>";
                        var haved
                        for(var i=0;i<weekdayss.length;i++){
                            for(var j=0;j<sectionss.length;j++){
                                str+="<tr>"
                                if(haved!=i){
                                    str+="<td rowspan='"+ sectionss.length +"'>星期"+weekdayss[i]+"</td>";
                                    haved = i;
                                }
                                str+="<td>第"+sectionss[j]+"节</td>";
                                for(var y=0;y<weekss.length;y++){
                                    for(var x=1;x<result.data.length;x++){
                                        // console.log(result.data[x]);
                                        if(result.data[x].week == weekss[y]){
                                            if(result.data[x].weekday == weekdayss[i]){
                                                if(result.data[x].section == sectionss[j]){
                                                    str+="<td><span>"+ result.data[x].conflictRate +"%</span></td>";
                                                }
                                            }
                                        }
                                    }
                                }
                                str+="</tr>"
                            }
                        }
                        str+="</tbody>";
                        str+="</table>";
                        $("#table_student").append(str);

                        var key = 0;
                        var arrPos = new Array();
                        $("#tab_stu").mousemove(function(e){
                            var x = e.clientX, y = e.clientY;
                            if (arrPos.length > 0) {
                                if (y <= arrPos[0][1]+10 &&y >= arrPos[0][1]-10 &&1==key && e.target.tagName =="td")
                                {
                                    $(e.target).css("background","#666").addClass("selected");
                                }
                            }
                        });
                        $("#tab_stu").mousedown(function(e){
                            var x = e.clientX, y = e.clientY;
                            arrPos.push(Array(x,y));
                            $("#result").html("X:"+x+";Y:"+y)
                            key=1;
                        });
                        $("#tab_stu").mouseup(function(e){
                            arrPos=new Array();
                            key=0;
                        });

                        // $("#tab_stu tbody td").mousedown(function () {
                        //     //每次先清除一下上次选中的单元格的背景色
                        //     $("#tab_stu tbody td").css('background-color', '');
                        //
                        //     $("#tab_stu tbody td").mousemove(onMousemove);
                        //     $("#tab_stu tbody td").mouseup(onMouseup);
                        // });
                        //
                        // function onMousemove() {
                        //     $(this).css('background-color', '#aaa');
                        // }
                        //
                        // var cellVal = parseFloat(0,10);
                        // var cellIndex = 0;
                        // var re = /(^[\-0-9][0-9]*(.[0-9]+)?)$/; //判断字符串是否为数字
                        // function onMouseup() {
                        //     $("#tab_stu tbody").find("td").each(function () {
                        //
                        //         if($(this).attr('style')=="background-color: rgb(170, 170, 170);"){
                        //             var nubmer = $(this).context.innerText;
                        //             if (!re.test(nubmer)) {
                        //                 nubmer = 0;
                        //             }
                        //
                        //             cellVal += parseFloat(nubmer,10);//cellIndex
                        //             cellIndex = $(this).context.cellIndex;//选中数据所在第几列
                        //         }
                        //     });
                        //     var html = "";
                        //     for(var i=0;i<cellIndex;i++){
                        //         html+="<td></td>"
                        //     }
                        //
                        //     html+="<td>"+cellVal.toFixed(2)+"</td>";
                        //
                        //     //共有多少列
                        //     var totalTh = $("#tab_stu th").size();
                        //
                        //     for(var i=0;i<totalTh - (cellIndex+1);i++){
                        //         html+="<td></td>"
                        //     }
                        //
                        //     $("tfoot").html(html);
                        //     cellVal = 0;
                        //     cellIndex = 0;
                        //     $("#tab_stu tbody td").unbind('mousemove', onMousemove);
                        // }
                    }
                });
            // var index = parent.layer.getFrameIndex(window.name);
            // parent.layer.close(index);//关闭当前页
            return false;
        });


    })

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
//获取jwt认证，获取token
//getJWTAuthority();
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