var contextPath = $("meta[name='contextPath']").attr("content");
var zuulUrl ="";
var labRoomId;
$(document).ready(function () {
    zuulUrl =$("#zuulServerUrl").val()+contextPath+"/labroom/";
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

    var url = zuulUrl + "api/labReservation/apiGetLabRoomList?search=" + search + "&sort=id&sortOrder=id";

    layui.use(['table', 'laypage', 'laydate'], function () {
        var table = layui.table,
            laydate = layui.laydate,
            laypage = layui.laypage;
        table.render({
            elem: '#LAY_table_user'
            ,
            url: url
            , toolbar: 'true'
            /*, where:{pagename:start,pagelimit:limitsize} //传参*/
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
                    field: 'labRoomNumber',
                    title: '实验室编号',
                    minWidth: 110
                }, {
                    field: 'address',
                    title: '实验室地址',
                    minWidth: 110
                }, {
                    field: 'managers',
                    title: '实验室管理员',
                    minWidth: 110,
                    templet: function (d) {
                        var result = "";
                        for (var i = 0, len = d.managers.length; i < len; i++) {
                            result += d.managers[i].cname + "&nbsp;";
                        }
                        return result;
                    }
                }, {
                    field: 'empty',
                    title: '操作',
                    minWidth: 60,
                    toolbar: '#barDemo'
                    // templet: function (d) {
                    //     var result = "<a href='javascript:;' class='btn btn-xs green' title='编辑'  onclick=\"newReverberation()\" ><span class='glyphicon glyphicon-check'>预约</span></a>&nbsp;";
                    //     return result;
                    // }
                }
                // ,{fixed: 'right', width:150, align:'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
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
            labRoomId = obj.data.id;
            if (layEvent === 'newReverberation') {
                var index = layer.open({
                    type: 2,
                    title: '开始实验室预约',
                    area: ['100%', '100%'],
                    maxmin: true,
                    content: contextPath + '/lims/reservation/lab/newLabReservation?labRoomId='+labRoomId,
                    success: function (layero) {
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
        area: ['100%', '100%'],
        maxmin: true,
        content: contextPath + '/lims/reservation/lab/newLabReservation',
        success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
    layer.full(index);

}