var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl ="";
var username = $("#username").val();
var projectName = $("#projectName").val();
var auditServerUrl = $("#auditServerUrl").val();
var userRole = $("#userRole").val();
$(document).ready(function () {
    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/timetable/";
    productsearch("", curnum, limitcount);
    $('#search').keyup(function () {
        // 1.获得搜索框的值；
        // 2.判断如果有值。layer.msg()
        // 3.定义延时函数，8s后刷新表格，并传递参数；
        // 4.关闭layer.msg()
        var keyWord = $('#search').val();
        setTimeout(function () {
            productsearch(keyWord, curnum, limitcount);
        }, 100);
    });
});

var limitcount = 10;
var curnum = 1;
//列表查询方法
function productsearch(search, start, limitsize) {
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

    var url = zuulUrl + "api/labReservation/apiLabRoomReservationAuditList";

    layui.use(['table', 'laypage', 'laydate'], function () {
        var table = layui.table,
            laydate = layui.laydate,
            laypage = layui.laypage;
        table.render({
            elem: '#LAY_table_user'
            ,method:'POST'
            ,contentType: 'application/json'
            ,url: url
            , toolbar: 'true'
            , where:{search:search,sort:"id",sortOrder:"id",username:username,
                projectName:projectName,auditServerUrl:auditServerUrl,userRole:userRole} //传参*/
            , request: {
                page: 'page' //页码的参数名称，默认：page
                , limit: 'nums' //每页数据量的参数名，默认：limit
            }
            , headers: {Authorization: authorization}
            , size: 'sm'
            , cols: [
                [{
                    title: '序号',
                    templet: '#indexTpl',
                    fixed: 'left',
                    width: 60
                }, {
                    field: 'labRoomName',
                    title: '实验室名称',
                    fixed: 'left',
                    width: 250
                }, {
                    field: 'cname',
                    title: '申请人',
                    minWidth: 110
                }, {
                    field: 'reservationDate',
                    title: '预约日期',
                    minWidth: 110
                }, {
                    field: 'empty',
                    title: '预约时间',
                    minWidth: 110,
                    templet: function (d) {
                        return d.startTime+"-"+d.endTime;
                    }
                },
                //     {
                //     field: 'eventName',
                //     title: '预约用途',
                //     minWidth: 110
                // }, {
                //     field: 'userObjectName',
                //     title: '预约对象',
                //     minWidth: 110
                // },
                    {
                    field: 'number',
                    title: '使用人数',
                    minWidth: 110
                }, {
                    field: 'telephone',
                    title: '联系电话',
                    minWidth: 110
                }, {
                    field: 'reason',
                    title: '预约原因',
                    minWidth: 110
                }, {
                    field: 'empty',
                    title: '操作',
                    // minWidth: 60,
                    // templet: function (d) {
                    //     var result = "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newReverberation()\" ><span class='glyphicon glyphicon-check'>预约</span></a>&nbsp;";
                    //     return result;
                    // }
                    toolbar: '#toolBar'
                }
                ]]
            ,
            page: { //分页设定
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                , curr: 1 //设定初始在第 1 页
                , limit: 20//每页多少数据
                , groups: 5 //只显示 5 个连续页码
            }
            ,
            even: true
            , done: function (res, curr, count) {

                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            }

        })
        //监听工具条
        table.on('tool(LAY_table_user)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') {
                viewLableInfo(data.attrId);
                layer.msg(data.attrId);
            } else if (layEvent === 'del') {
                layer.msg('删除');
            } else if (layEvent === 'edit') {
                layer.msg('编辑操作');
            } else if (layEvent === 'examine') {
                var index = layer.open({
                    type: 2 //此处以iframe举例
                    ,
                    title: '实验室预约审核',
                    // area: auto,
                    area: ['100%', '100%'],
                    maxmin: true,
                    content: contextPath + '/lims/reservation/lab/labReservationAudit?labRId='+data.appointmentId,
                    zIndex: layer.zIndex //重点1
                    ,
                    success: function(layero) {
                        layer.setTop(layero); //重点2
                    }
                });
                layer.full(index);
            }
        });
        //常规用法
        laydate.render({
            elem: '#createDate'
        });
        //常规用法
        laydate.render({
            elem: '#processingTime'
        });

    });
}

function newReverberation() {
    var index = layer.open({
        type: 2,
        title: '开始实验室预约',
        maxmin: true,
        shadeClose: true,
        area: ['1100px', '500px'],
        content: contextPath + '/lims/reservation/lab/newLabReservation',
        success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
    layer.full(index);

}