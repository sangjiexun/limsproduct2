var contextPath = $("meta[name='contextPath']").attr("content");
var parentProjectIds = [];

layui.config({
	version: '1545041465480' //为了更新 js 缓存，可忽略
});

layui.use(['laypage', 'layer', 'table', 'element'], function() {
	var laypage = layui.laypage //分页
		,
		layer = layui.layer //弹层
		,
		table = layui.table //表格
		,
		element = layui.element //元素操作

	//向世界问个好
	// layer.msg('进入子项目列表');

	// console.log(parentProjectIds);

    //执行一个子项目表单
    table.render({
        elem: '#sonproject'+parentProjectIds[0],
        // url: $("#contextPath").val()+'/js/lims/reservation/lab/construction/json/sonProject.json', //数据接口
        url: $("#contextPath").val()+'/lims/api/labConstruction/getSonProjects?id='+parentProjectIds[0], //数据接口
        title: '子项目列表',
        page: true, //开启分页
        toolbar: 'table', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        totalRow: true, //开启合计行
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
            //curr: 5 ,//设定初始在第 5 页
            groups: 1, //只显示 1 个连续页码
            first: false, //不显示首页
            last: false //不显示尾页
        },
        cols: [
            [ //表头
                {
                    fixed: 'left',
                    title: '序号',
                    type: 'numbers',
                    width: 50
                }, {
                fixed: 'left',
                field: 'projectName',
                title: '子项目名称',
                minWidth: 130,
                sort: true,
                totalRowText: '项目预算合计：'
            }, {
                field: 'academyName',
                title: '学院',
                minWidth: 130,
                sort: true
            }, {
                field: 'budget',
                title: '经费预算',
                minWidth: 100,
                sort: true,
                totalRow: true
            }, {
                field: 'budgetBalanceTime',
                title: '预算结算时间',
                minWidth: 130,
                sort: true
            }, {
                field: 'projectImplementTime',
                title: '项目实施时间',
                minWidth: 130,
                sort: true
            }, {
                fixed: 'right',
                title: '操作',
                width: 250,
                align: 'center',
                toolbar: '#sonbar'
            }
            ]
        ],
        data: table,
        //skin: 'line',//表格风格
        even: true,
        page: true,
        limits: [5, 7, 10, 20],
        limit: 5 //每页默认显示的数量
    });
    element.on('collapse(sonProjectColl)', function(data){
        console.log(data.show); //得到当前面板的展开状态，true或者false
        console.log(data.title[0].id); //得到当前点击面板的标题区域DOM对象
        // console.log(data.content); //得到当前点击面板的内容区域DOM对象
        //执行一个子项目表单
        table.render({
            elem: '#sonproject'+ data.title[0].id,
            // url: $("#contextPath").val()+'/js/lims/reservation/lab/construction/json/sonProject.json', //数据接口
            url: $("#contextPath").val()+'/lims/api/labConstruction/getSonProjects?id='+ data.title[0].id, //数据接口
            title: '子项目列表',
            page: true, //开启分页
            toolbar: 'table', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            totalRow: true, //开启合计行
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //curr: 5 ,//设定初始在第 5 页
                groups: 1, //只显示 1 个连续页码
                first: false, //不显示首页
                last: false //不显示尾页
            },
            cols: [
                [ //表头
                    {
                        fixed: 'left',
                        title: '序号',
                        type: 'numbers',
                        width: 50
                    }, {
                    fixed: 'left',
                    field: 'projectName',
                    title: '子项目名称',
                    minWidth: 130,
                    sort: true,
                    totalRowText: '项目预算合计：'
                }, {
                    field: 'academyName',
                    title: '学院',
                    minWidth: 130,
                    sort: true
                }, {
                    field: 'budget',
                    title: '经费预算',
                    minWidth: 100,
                    sort: true,
                    totalRow: true
                }, {
                    field: 'budgetBalanceTime',
                    title: '预算结算时间',
                    minWidth: 130,
                    sort: true
                }, {
                    field: 'projectImplementTime',
                    title: '项目实施时间',
                    minWidth: 130,
                    sort: true
                }, {
                    fixed: 'right',
                    title: '操作',
                    width: 250,
                    align: 'center',
                    toolbar: '#sonbar'
                }
                ]
            ],
            data: table,
            //skin: 'line',//表格风格
            even: true,
            page: true,
            limits: [5, 7, 10, 20],
            limit: 5 //每页默认显示的数量
        });
    });


	//监听行工具事件
	table.on('tool(sonhead)', function(obj) { //注：tool 是工具条事件名，sonhead 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,
			layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'detail') {
			// layer.msg('查看该项目');
		} else if(layEvent === 'del') {
			// layer.confirm('真的删除行么', function(index) {
            // 	obj.del(); //删除对应行（tr）的DOM结构
            // 	layer.close(index);
            // 	//向服务端发送删除指令
            // });
            layer.confirm('are you sure?', function(index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                $.ajax({
                    url: contextPath + '/lims/api/labConstruction/deleteSonProject?id='+data.id,
                    // data: jsonData,
                    async: false,
                    type: "GET",
                    contentType: "application/json;charset=UTF-8",
                    success:function (res) {
                        console.log(res);
                        location.reload();
                    },
                    error: function () {
                        alert("后台出了点问题，请重试！");
                        return false;
                    }
                })
                layer.close(index);
                //向服务端发送删除指令
            });
		} else if(layEvent === 'edit') {
			// layer.msg('编辑该项目');
		} else if(layEvent === 'newgrandson') {
			// layer.msg('新建孙项目');
		};
		//打开查看子项目页面
		if(obj.event === 'detail') {
			console.log(data)
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '查看子项目',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'detailSonProject?id='+data.id,
				zIndex: layer.zIndex //重点1
					,
				success: function(layero) {
					layer.setTop(layero); //重点2
				}
			});
			layer.full(index);
		};
		//打开编辑子项目页面
		if(obj.event === 'edit') {
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '编辑子项目',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'editSonProject?id='+data.id,
				zIndex: layer.zIndex //重点1
					,
				success: function(layero) {
					layer.setTop(layero); //重点2
				}
			});
			layer.full(index);
		};
		//打开新建孙项目页面
		if(obj.event === 'newgrandson') {
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '新建孙项目',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'editGrandsonProject?sonId='+data.id,
				zIndex: layer.zIndex //重点1
					,
				success: function(layero) {
					layer.setTop(layero); //重点2
				}
			});
			layer.full(index);
		};
	});
	//打开新建子项目页面
	var active = {
		newson: function() {
			layer.msg('新建子项目');
			var that = this;
			//多窗口模式，层叠置顶
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '新建子项目',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'editSonProject.html',
				zIndex: layer.zIndex //重点1
					,
				success: function(layero) {
					layer.setTop(layero); //重点2
				}
			});
			layer.full(index);
		}
	};
	$('.new_btn').on('click', function() {
		var othis = $(this),
			method = othis.data('method');
		active[method] ? active[method].call(this, othis) : '';
	});
});

//筛选条件综合提示
function showtagtips(tagtips) {
	layer.tips("父项目：父项目的筛选条件<br/>子项目：子项目的筛选条件", "#" + tagtips + "", {
		tips: [3, "#5fb878"]
	});
}