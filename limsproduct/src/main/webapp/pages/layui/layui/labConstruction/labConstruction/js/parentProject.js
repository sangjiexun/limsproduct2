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
	layer.msg('进入父项目列表');

	//执行一个父项目表单
	table.render({
		elem: '#parentproject',
		url: 'json/parentProject.json', //数据接口						
		title: '父项目列表',
		page: true, //开启分页						
		toolbar: 'table', //开启工具栏，此处显示默认图标，可以自定义模板，详见文档						
		totalRow: true, //开启合计行	
		page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
			layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
			//curr: 5, //设定初始在第 5 页				
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
					field: 'ppname',
					title: '父项目名称',
					minWidth: 130,
					sort: true,
					totalRowText: '项目预算合计：'
				}, {
					field: 'ppbudget',
					title: '经费预算',
					minWidth: 100,
					sort: true,
					totalRow: true
				}, {
					fixed: 'right',
					title: '操作',
					width: 250,
					align: 'center',
					toolbar: '#parentbar'
				}
			]
		],
		data: table,
		//skin: 'line' ,//表格风格			
		even: true,
		page: true,
		limits: [5, 7, 10, 20],
		limit: 5 //每页默认显示的数量
	});

	//监听行工具事件
	table.on('tool(parenthead)', function(obj) { //注：tool 是工具条事件名，parenthead 是 table 原始容器的属性 lay-filter="对应的值"
		var data = obj.data //获得当前行数据
			,
			layEvent = obj.event; //获得 lay-event 对应的值
		if(layEvent === 'detail') {
			layer.msg('查看该项目');
		} else if(layEvent === 'del') {
			layer.confirm('真的删除行么', function(index) {
				obj.del(); //删除对应行（tr）的DOM结构
				layer.close(index);
				//向服务端发送删除指令
			});
		} else if(layEvent === 'edit') {
			layer.msg('编辑该项目');
		} else if(layEvent === 'newson') {
			layer.msg('新建子项目');
		};
		//打开查看父项目页面
		if(obj.event === 'detail') {
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '查看父项目详情',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'detailParentProject.html',
				zIndex: layer.zIndex //重点1
					,
				success: function(layero) {
					layer.setTop(layero); //重点2
				}
			});
			layer.full(index);
		};
		//打开编辑父项目页面
		if(obj.event === 'edit') {
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '编辑父项目',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'editParentProject.html',
				zIndex: layer.zIndex //重点1
					,
				success: function(layero) {
					layer.setTop(layero); //重点2
				}
			});
			layer.full(index);
		};
		//打开新建子项目页面
		if(obj.event === 'newson') {
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
		};
	});
	//打开新建父项目页面
	var active = {
		newparent: function() {
			layer.msg('新建父项目');
			var that = this;
			//多窗口模式，层叠置顶
			var index = layer.open({
				type: 2 //此处以iframe举例
					,
				title: '新建父项目',
				area: ['390px', '260px'],
				shade: 0,
				maxmin: true,
				content: 'editParentProject.html',
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
	layer.tips("父项目：父项目的筛选条件", "#" + tagtips + "", {
		tips: [3, "#5fb878"]
	});
};