/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */ ;
layui.define(function(e) {
	layui.use("table", function() {
		var e = (layui.$, layui.table);
		e.render({
			elem: "#riskEarlyWarning",
			url: layui.setter.base + "json/lab/riskEarlyWarning.js",
			title: '实验室危险源管理',
			page: !0,
			cellMinWidth: 50,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "房间",
					minWidth: 50,
					sort: !0
				}, {
					field: "two",
					title: "责任人",
					minWidth: 50,
					sort: !0
				}, {
					field: "three",
					title: "危险源名称",
					minWidth: 130,
					sort: !0
				}, {
					field: "four",
					title: "风险类别",
					minWidth: 50,
					sort: !0
				}, {
					field: "five",
					title: "风险级别",
					minWidth: 50,
					sort: !0
				}, {
					field: "six",
					title: "控制措施",
					minWidth: 160,
					sort: !0
				}, {
					field: "seven",
					title: "检查反馈",
					minWidth: 160,
					sort: !0
				}, {
					field: "eight",
					title: "应急预案",
					minWidth: 80,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.eight }}" target="_blank" class="layui-table-link">{{ d.eight }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#visualSupervision",
			url: layui.setter.base + "json/lab/visualSupervision.js",
			title: '实验室安全监管',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "房间",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "危险源",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "视频",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.three }}" target="_blank" class="layui-table-link">{{ d.three }}</div>'
				}, {
					field: "four",
					title: "出入记录",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.four }}" target="_blank" class="layui-table-link">{{ d.four }}</div>'
				}, {
					field: "five",
					title: "异常记录",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.five }}" target="_blank" class="layui-table-link">{{ d.five }}</div>'
				}, {
					field: "six",
					title: "截图上报",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.six }}" target="_blank" class="layui-table-link">{{ d.six }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#safetyOrganizationManagement",
			url: layui.setter.base + "json/lab/safetyOrganizationManagement.js",
			title: '实验室安全管理',
			page: !0,
			cellMinWidth: 100,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "房间号",
					minWidth: 100,
					sort: !0
				}, {
					field: "two",
					title: "日期",
					minWidth: 100,
					sort: !0
				}, {
					field: "three",
					title: "异常事件",
					minWidth: 200,
					sort: !0
				}, {
					field: "four",
					title: "事件状态",
					minWidth: 100,
					sort: !0
				}, {
					field: "five",
					title: "事件来源",
					minWidth: 100,
					sort: !0
				}, {
					field: "six",
					title: "上报人",
					minWidth: 100,
					sort: !0
				}, {
					field: "seven",
					title: "相关事项",
					minWidth: 100,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}, {
					field: "eight",
					title: "出入日志",
					minWidth: 100,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.eight }}" target="_blank" class="layui-table-link">{{ d.eight }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#supervisionInspectionManagement",
			url: layui.setter.base + "json/lab/supervisionInspectionManagement.js",
			title: '实验室三废管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "房间编号",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "废弃物类别",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "废弃物名称",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "处置方式",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "处置日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "处置人",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "接收人",
					minWidth: 120,
					sort: !0
				}, {
					field: "eight",
					title: "相关图片",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.eight }}" target="_blank" class="layui-table-link">{{ d.eight }}</div>'
				}]
			],
			skin: "line"
		}), e.render({
			elem: "#safetyEmergencyManagement",
			url: layui.setter.base + "json/lab/safetyEmergencyManagement.js",
			title: '实验室安全准入管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "房间",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "授权人",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "授权方式",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "授权开始日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "授权结束日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "准入状态",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "出入记录",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}), e.render({
			elem: "#safetyEmergency",
			url: layui.setter.base + "json/lab/safetyEmergency.js",
			title: '实验室应急资源管理',
			page: !0,
			cellMinWidth: 100,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "房间",
					minWidth: 100,
					sort: !0
				}, {
					field: "two",
					title: "设施名称",
					minWidth: 100,
					sort: !0
				}, {
					field: "three",
					title: "维护提示",
					minWidth: 180,
					sort: !0
				}, {
					field: "four",
					title: "维护日期",
					minWidth: 100,
					sort: !0
				}, {
					field: "five",
					title: "维护人",
					minWidth: 100,
					sort: !0
				}, {
					field: "six",
					title: "设施状态",
					minWidth: 100,
					sort: !0
				}, {
					field: "seven",
					title: "所属预案",
					minWidth: 180,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		})
	}), e("console", {})
});