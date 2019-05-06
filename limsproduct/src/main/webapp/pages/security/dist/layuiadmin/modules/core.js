/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */ ;
layui.define(function(e) {
	layui.use("table", function() {
		var e = (layui.$, layui.table);
		e.render({
			elem: "#riskEarlyWarning",
			url: layui.setter.base + "json/core/riskEarlyWarning.js",
			title: '实验室中心风险管理',
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
					minWidth: 100,
					sort: !0
				}, {
					field: "three",
					title: "危险源",
					minWidth: 130,
					sort: !0
				}, {
					field: "four",
					title: "风险点",
					minWidth: 80,
					sort: !0
				}, {
					field: "five",
					title: "未整改隐患",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "控制措施",
					minWidth: 160,
					sort: !0
				},{
					field: "seven",
					title: "风险级别",
					minWidth: 100,
					sort: !0
				}, {
					field: "eight",
					title: "问题数/检查次数",
					minWidth: 140,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.eight }}" target="_blank" class="layui-table-link">{{ d.eight }}</div>'
				}, {
					field: "nine",
					title: "运行日志",
					minWidth: 100,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.nine }}" target="_blank" class="layui-table-link">{{ d.nine }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#visualSupervision",
			url: layui.setter.base + "json/core/visualSupervision.js",
			title: '实验室中心可视化监督',
			page: !0,
			cellMinWidth: 100,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "风险等级",
					minWidth: 100,
					sort: !0
				}, {
					field: "two",
					title: "危险源",
					minWidth: 150,
					sort: !0
				}, {
					field: "three",
					title: "责任人",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "所在房间",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "安全标识",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.five }}" target="_blank" class="layui-table-link">{{ d.five }}</div>'
				}, {
					field: "six",
					title: "应急措施",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.six }}" target="_blank" class="layui-table-link">{{ d.six }}</div>'
				}, {
					field: "seven",
					title: "查看视频",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#safetyOrganizationManagement",
			url: layui.setter.base + "json/core/safetyOrganizationManagement.js",
			title: '实验室中心安全规范管理',
			page: !0,
			cellMinWidth: 50,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "制度类别",
					minWidth: 60,
					sort: !0
				}, {
					field: "two",
					title: "制度名称",
					minWidth: 200,
					sort: !0
				}, {
					field: "three",
					title: "制度范围",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "主要内容",
					minWidth: 190,
					sort: !0
				}, {
					field: "five",
					title: "发布人",
					minWidth: 100,
					sort: !0
				}, {
					field: "six",
					title: "发布日期",
					minWidth: 100,
					sort: !0
				}, {
					field: "seven",
					title: "有效日期",
					minWidth: 100,
					sort: !0
				}, {
					field: "eight",
					title: "查看附件",
					minWidth: 100,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.eight }}" target="_blank" class="layui-table-link">{{ d.eight }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#supervisionInspectionManagement",
			url: layui.setter.base + "json/core/supervisionInspectionManagement.js",
			title: '实验室中心安全巡查管理',
			page: !0,
			cellMinWidth: 80,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "巡检日期",
					minWidth: 80,
					sort: !0
				}, {
					field: "two",
					title: "巡检房间",
					minWidth: 80,
					sort: !0
				}, {
					field: "three",
					title: "巡检人",
					minWidth: 80,
					sort: !0
				}, {
					field: "four",
					title: "问题发现",
					minWidth: 250,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.four }}" target="_blank" class="layui-table-link">{{ d.four }}</div>'
				}, {
					field: "five",
					title: "问题反馈",
					minWidth: 200,
					sort: !0
				}, {
					field: "six",
					title: "问题状态",
					minWidth: 100,
					sort: !0
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#safetyTrainingManagement",
			url: layui.setter.base + "json/core/safetyTrainingManagement.js",
			title: '实验室中心培训准入管理',
			page: !0,
			cellMinWidth: 100,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "培训类别",
					minWidth: 100,
					sort: !0
				}, {
					field: "two",
					title: "培训名称",
					minWidth: 200,
					sort: !0
				}, {
					field: "three",
					title: "培训范围",
					minWidth: 150,
					sort: !0
				}, {
					field: "four",
					title: "负责人",
					minWidth: 100,
					sort: !0
				}, {
					field: "five",
					title: "开始时间",
					minWidth: 100,
					sort: !0
				}, {
					field: "six",
					title: "结束时间",
					minWidth: 100,
					sort: !0
				}, {
					field: "seven",
					title: "培训成绩",
					minWidth: 100,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}), e.render({
			elem: "#safetyEmergencyManagement",
			url: layui.setter.base + "json/core/safetyEmergencyManagement.js",
			title: '实验室中心应急演练管理',
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
					title: "责任人",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "预案类别",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "预案名称",
					minWidth: 200,
					sort: !0
				}, {
					field: "five",
					title: "上报日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "维护日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "演练明细",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		})
	}), e("console", {})
});