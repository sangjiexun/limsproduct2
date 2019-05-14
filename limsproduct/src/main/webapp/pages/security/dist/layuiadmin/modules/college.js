/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */ ;
layui.define(function(e) {
	layui.use("table", function() {
		var e = (layui.$, layui.table);
		e.render({
			elem: "#riskMonitoring",
			url: layui.setter.base + "json/college/riskMonitoring.js",
			title: '院级风险监控',
			page: !0,
			cellMinWidth: 20,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "所属中心/课题组",
					minWidth: 140,
					sort: !0
				}, {
					field: "two",
					title: "负责人",
					minWidth: 30,
					sort: !0
				}, {
					field: "three",
					title: "危险源",
					minWidth: 130,
					sort: !0
				}, {
					field: "four",
					title: "风险点",
					minWidth: 30,
					sort: !0
				}, {
					field: "five",
					title: "房间",
					minWidth: 30,
					sort: !0
				}, {
					field: "six",
					title: "风险级别",
					minWidth: 30,
					sort: !0
				}, {
					field: "seven",
					title: "管控措施",
					minWidth: 160,
					sort: !0
				}, {
					field: "eight",
					title: "隐患整改",
					minWidth: 150,
					sort: !0,
					templet: '<div><a href="../cite/listInspectGrading.html" class="layui-table-link">{{ d.eight }}</div>'
				}]
			],
			skin: "line"
		}), e.render({
			elem: "#visualMonitoring",
			url: layui.setter.base + "json/college/visualMonitoring.js",
			title: '院级可视化监察',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "危险源",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "责任人",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "所在房间",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "风险等级",
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
			elem: "#safetyResponsibilityManagement",
			url: layui.setter.base + "json/college/safetyResponsibilityManagement.js",
			title: '院级安全职责管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "所属部门",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "角色",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "职责描述",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "责任人",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "开始时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "结束时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "相关协议",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="../cite/document.html" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#safetyInspectionManagement",
			url: layui.setter.base + "json/college/safetyInspectionManagement.js",
			title: '院级安全检查管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "检查类别",
					minWidth: 150,
					sort: !0
				}, {
					field: "two",
					title: "检查名称",
					minWidth: 200,
					sort: !0
				}, {
					field: "three",
					title: "检查范围",
					minWidth: 150,
					sort: !0
				}, {
					field: "four",
					title: "发起人",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "开始时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "结束时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "检查明细",
					minWidth: 120,
					sort: !0,					
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#safetyTrainingManagement",
			url: layui.setter.base + "json/college/safetyTrainingManagement.js",
			title: '院级安全培训管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "培训类别",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "培训名称",
					minWidth: 180,
					sort: !0
				}, {
					field: "three",
					title: "培训范围",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "负责人",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "开始时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "结束时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "培训成绩",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="../cite/training.html" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#emergencyPlanManagement",
			url: layui.setter.base + "json/college/emergencyPlanManagement.js",
			title: '院级应急预案管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "预案名称",
					minWidth: 200,
					sort: !0
				}, {
					field: "two",
					title: "所属中心/课题组",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "所属房间",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "责任人",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "发布日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "更新日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "预案查看",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="../cite/document.html" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		})
	}), e("console", {})
});