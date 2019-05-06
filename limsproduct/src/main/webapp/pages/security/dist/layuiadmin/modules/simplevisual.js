/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */ ;
layui.define(function(e) {
	layui.use("table", function() {
		var e = (layui.$, layui.table);
		e.render({
				elem: "#schooltab",
				url: layui.setter.base + "json/simplevisual/schooltab.js",
				title: '校级数据',
				page: !0,
				cellMinWidth: 100,
				cols: [
					[ //表头
						{
							fixed: 'left',
							title: '序号',
							type: 'numbers',
							width: 50
						}, {
							fixed: 'left',
							field: 'city',
							title: '学院',
							sort: true
						}, {
							field: 'aqi',
							title: '预警分值',
							sort: true
						}, {
							title: '源头预警',
							align: 'center',
							toolbar: '#levelOne'
						}, {
							title: '检查预警',
							align: 'center',
							toolbar: '#levelTwo'
						}
					]
				],
				skin: "line"
			}),
			e.render({
				elem: "#collegetab",
				url: layui.setter.base + "json/simplevisual/collegetab.js",
				title: '院级数据',
				page: !0,
				cellMinWidth: 100,
				cols: [
					[ //表头
						{
							fixed: 'left',
							title: '排名',
							type: 'numbers',
							width: 50
						}, {
							fixed: 'left',
							field: 'city',
							title: '实验中心',
							sort: true
						}, {
							field: 'aqi',
							title: '预警分值',
							sort: true
						}, {
							title: '源头预警',
							align: 'center',
							toolbar: '#levelOne'
						}, {
							title: '检查预警',
							align: 'center',
							toolbar: '#levelTwo'
						}
					]
				],
				skin: "line"
			}),
			e.render({
				elem: "#coretab",
				url: layui.setter.base + "json/simplevisual/coretab.js",
				title: '实验中心数据',
				page: !0,
				cellMinWidth: 100,
				cols: [
					[ //表头
						{
							fixed: 'left',
							title: '排名',
							type: 'numbers',
							width: 50
						}, {
							fixed: 'left',
							field: 'city',
							title: '实验室',
							sort: true
						}, {
							field: 'aqi',
							title: '预警分值',
							sort: true
						}, {
							title: '源头预警',
							align: 'center',
							toolbar: '#levelOne'
						}, {
							title: '检查预警',
							align: 'center',
							toolbar: '#levelTwo'
						}
					]
				],
				skin: "line"
			})
	}), e("console", {})
});