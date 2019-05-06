var contextPath = $("meta[name='contextPath']").attr("content");
$(document).ready(function () {
    //初始化表格,动态从服务器加载数据
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
        //排序方式
        sortOrder: "asc",
        sortName: 'courseName',
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
            title: "课程名称",
            field: "courseNumber",
            width: "11%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.courseName + "(" + row.courseNumber + ")";
            }
        }, {
            title: "教学班编号",
            field: "courseNo",
            width: "11%",
            sortable: true
        }, {
            title: "课程计划",
            field: "coursePlan",
            width: "12%",
            formatter: function (value, row, index) {
                var rt = '';
                var schoolCourseDetailDTOs = row.schoolCourseDetailDTOs;
                var count = Number(0);
                for (var i=0,len=schoolCourseDetailDTOs.length; i<len; i++){
                    rt += schoolCourseDetailDTOs[i].coursePlan+"<br>";
                }
                return rt;
            }
        },{
            title: "已排课表",
            field: "timetableDTOs",
            width: "12%",
            formatter: function (value, row, index) {
                var rt = '';
                var timetableDTOs = row.timetableDTOs;
                var count = Number(0);
                for (var i=0,len=timetableDTOs.length; i<len; i++){
                    rt += timetableDTOs[i].timetable+"<br>";
                }
                return rt;
            }
        }, {
            title: "上课教师",
            field: "cname",
            width: "9%",
            sortable: true,
            formatter: function (value, row, index) {
                return row.cname + "(" + row.username + ")";
            }
        },{
            title: "学生名单",
            field: "student",
            width: "8%",
            formatter: function (value, row, index) {

                var result = "";
                result = "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"schoolCourseStudents("+ row.termId + ",'"+ row.courseNo + "')\" >学生名单(" + row.student + ")</a>";
                return result;
            }
        }, {
            title: "操作",
            width: "14%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if(row.timetableStatus==1){

                    result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"viewTimetableInfo('" + row.courseNo + "')\" ><span class='glyphicon glyphicon-search'>查看排课</span></a>&nbsp;";
                }else if(row.timetableStatus==0){
                    if(row.baseActionAuthDTO.addActionAuth){
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReNoGroupCourse('" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>不分批排课</span></a>&nbsp;";
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReGroupCourse("+ row.termId +",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-plus'>分批排课</span></a>&nbsp;";
                    }
                }else if(row.timetableStatus==2 ){
                    result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"viewTimetableInfo('" + row.courseNo + "')\" ><span class='glyphicon glyphicon-search'>查看排课</span></a>&nbsp;";
                    if(row.baseActionAuthDTO.publicActionAuth){
                        result += "<a href='javascript:;' class='btn btn-xs green' title='查看'  onclick=\"publicTimetable(1,'" + row.courseNo + "',1)\" ><span class='glyphicon glyphicon-ok'>发布排课</span></a>&nbsp;";
                    }
                }else if(row.timetableStatus==10){
                    if(row.baseActionAuthDTO.addActionAuth){
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newEduReGroupCourse("+ row.termId +",'" + row.courseNo + "')\" ><span class='glyphicon glyphicon-edit'>继续排课</span></a>&nbsp;";
                        result += "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"publicTimetable(1,'"+ row.courseNo +"',2)\" ><span class='glyphicon glyphicon-check'>调课完成</span></a>&nbsp;";

                    }
                }
                return result;
            }
        }, {
            title: "状态",
            width: "5%",
            field: "empty",
            formatter: function (value, row, index) {
                var id = value;
                var result = "";
                if(row.timetableStatus==0&&!row.baseActionAuthDTO.addActionAuth){
                    result += "<b>操作未被授权</b>";
                }else if(row.timetableStatus==0&&row.baseActionAuthDTO.addActionAuth){
                    result += "尚未排课";
                }else if(row.timetableStatus==2 ){
                    result += "待发布";
                }else if(row.timetableStatus==10 ){
                    result += "正在排课";
                }else if(row.timetableStatus==1 ){
                    result += "已发布";
                }
                return result;
            }
        }]
    });

    $("#term").on("change", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
    $("#search").on("input", function () {
        var params = $("#table_list").bootstrapTable('getOptions')
        $("#table_list").bootstrapTable('refresh', params);
    })
});

//得到查询的参数
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        // limit: params.limit,   //页面大小
        termId: $("#term").val(),
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
function schoolCourseStudents(term,courseNo) {
    var index = layer.open({
        type: 2,
        title: '查看学生选课情况',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath+'/lims/school/schoolcoursestudent/schoolCourseStudnetList?term='+ term +'&courseNo=' + courseNo
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
        content: contextPath+'/lims/timetable/common/viewTimetableInfo?style=1&courseNo=' + courseNo
    });
    layer.full(index);
}

/*
*二次分批排课弹出窗口
*/
function newEduReGroupCourse(term,courseNo) {
    var index = layer.open({
        type: 2,
        title: '二次分批排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
      /*  content: contextPath + '/lims/timetable/engineer/educourse/openAdjustTimetableByScience?currpage=1&flag=0&courseDetailNo=' + courseDetailNo
        + '&tableAppId=' + 0*/
        content: contextPath + '/lims/timetable/engineer/edurecourse/newEduReGroupCourse?currpage=1&flag=0&courseNo=' + courseNo+"&term="+term
        + '&tableAppId=' + 0
    });
    layer.full(index);
}

/*
*二次不分批排课
 */
function newEduReNoGroupCourse(term,courseNo) {
    var index = layer.open({
        type: 2,
        title: '二次不分批排课',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        /*  content: contextPath + '/lims/timetable/engineer/educourse/openAdjustTimetableByScience?currpage=1&flag=0&courseDetailNo=' + courseDetailNo
          + '&tableAppId=' + 0*/
        content: contextPath + '/lims/timetable/engineer/edurecourse/newEduReTimetableCourse?currpage=1&flag=0&timetableStyle=3&courseNo=' + courseNo+"&term="+term
        + '&tableAppId=' + 0
    });
    layer.full(index);
}

function publicTimetable(timetableStyle,courseNo,status){
    var arr = new Object();
    arr.courseNo = courseNo;
    arr.timetableStyle = timetableStyle;
    arr.status = status;
    var arrs = JSON.stringify(arr);
    $.ajax({
        url: contextPath + "/lims/api/timetable/common/apiTimetablePublic",
        contentType:"application/json;charset=utf-8",
        dataType: "json",
        type:"post",
        async:false,
        data:arrs,
        success:function (json) {
            if (json.responseText == "no") {
                alert("所选择的实训室资源冲突，请重新选择或者用调整排课操作，谢谢。");
                isConflict = 0;
            }
        }
    });
    window.parent.location.reload();
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

