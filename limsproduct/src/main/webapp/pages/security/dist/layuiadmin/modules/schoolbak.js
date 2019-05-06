/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */ ;
layui.define(function(e) {
	layui.use("table", function() {
		var e = (layui.$, layui.table);
		e.render({
			elem: "#riskEarlyWarning",
			url: layui.setter.base + "json/school/riskEarlyWarning.js",
			title: '校级风险预警',
			page: !0,
			cellMinWidth: 50,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "学院",
					minWidth: 150,
					sort: !0
				}, {
					field: "two",
					title: "房间",
					minWidth: 100,
					sort: !0
				}, {
					field: "three",
					title: "危险源",
					minWidth: 100,
					sort: !0
				}, {
					field: "four",
					title: "风险类别",
					minWidth: 100,
					sort: !0
				}, {
					field: "five",
					title: "责任人",
					minWidth: 100,
					sort: !0
				}, {
					field: "six",
					title: "控制措施",
					minWidth: 200,
					sort: !0
   			    }, {
					field: "seven",
					title: "风险值",
					minWidth: 50,
					sort: !0
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#visualSupervision",
			url: layui.setter.base + "json/school/visualSupervision.js",
			title: '校级可视化监管',
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
					title: "所在房间",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "所属学院",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "所属中心/课题组",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "数量",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "责任人",
					minWidth: 120,
					sort: !0
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
			url: layui.setter.base + "json/school/safetyOrganizationManagement.js",
			title: '校级安全组织管理',
			page: !0,
			cellMinWidth: 100,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "单位名称",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "部门",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "负责人",
					minWidth: 120,
					sort: !0
				}, {
					field: "four",
					title: "制度名称",
					minWidth: 230,
					sort: !0
				}, {
					field: "five",
					title: "发布时间",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "相关文件",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.six }}" target="_blank" class="layui-table-link">{{ d.six }}</div>'
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#supervisionInspectionManagement",
			url: layui.setter.base + "json/school/supervisionInspectionManagement.js",
			title: '校级监督检查管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "检查类别",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "检查名称",
					minWidth: 160,
					sort: !0
				}, {
					field: "three",
					title: "检查范围",
					minWidth: 160,
					sort: !0
				}, {
					field: "four",
					title: "未整改隐患",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.four }}" target="_blank" class="layui-table-link">{{ d.four }}</div>'
				}, {
					field: "five",
					title: "负责人",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "开始日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "结束日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "eight",
					title: "检查状态",
					minWidth: 120,
					sort: !0
				}]
			],
			skin: "line"
		}),e.render({
			elem: "#safetyTrainingManagement",
			url: layui.setter.base + "json/school/safetyTrainingManagement.js",
			title: '校级安全培训管理',
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
					minWidth: 160,
					sort: !0
				}, {
					field: "three",
					title: "发起部门",
					minWidth: 160,
					sort: !0
				}, {
					field: "four",
					title: "负责人",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "培训范围",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "开始日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "结束日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "eight",
					title: "培训明细",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.eight }}" target="_blank" class="layui-table-link">{{ d.eight }}</div>'
				}]
			],
			skin: "line"
		}), e.render({
			elem: "#safetyEmergencyManagement",
			url: layui.setter.base + "json/school/safetyEmergencyManagement.js",
			title: '校级安全应急管理',
			page: !0,
			cellMinWidth: 120,
			cols: [
				[{
					type: "numbers",
					fixed: "left"
				}, {
					field: "one",
					title: "单位名称",
					minWidth: 120,
					sort: !0
				}, {
					field: "two",
					title: "部门",
					minWidth: 120,
					sort: !0
				}, {
					field: "three",
					title: "预案名称",
					minWidth: 220,
					sort: !0
				}, {
					field: "four",
					title: "责任人",
					minWidth: 120,
					sort: !0
				}, {
					field: "five",
					title: "上传日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "six",
					title: "维护日期",
					minWidth: 120,
					sort: !0
				}, {
					field: "seven",
					title: "预案详情",
					minWidth: 120,
					sort: !0,
					templet: '<div><a href="https://www.baidu.com/s?wd={{ d.seven }}" target="_blank" class="layui-table-link">{{ d.seven }}</div>'
				}]
			],
			skin: "line"
		}), e.render({
            elem: "#message",
            url: layui.setter.base + "json/school/message.js",
            title: '通知公告',
            page: !0,
            cellMinWidth: 120,
            cols: [
                [ {
                    field: "one",
                    title: "标题",
                    minWidth: 240,
                    sort: !0
                }, {
                    field: "two",
                    title: "消息类型",
                    minWidth: 60,
                    sort: !0
                }, {
                    field: "three",
                    title: "发布人",
                    minWidth: 60,
                    sort: !0
                }, {
                    field: "four",
                    title: "发布日期",
                    minWidth: 120,
                    sort: !0
                }, {
                    field: "five",
                    title: "浏览次数",
                    minWidth: 120,
                    sort: !0
                }]
            ],
            skin: "line"
        })
	}), e("console", {})
});