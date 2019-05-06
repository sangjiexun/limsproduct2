<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html lang="en">

	<head>
		<meta name="decorator" content="iframe" />
    	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta charset="UTF-8">
		<title></title>
		<style>
			.down {
				BORDER-LEFT: #999999 1px solid;
				BORDER-TOP: #999999 1px solid;
				BORDER-BOTTOM: #ffffff 1px solid;
				BORDER-RIGHT: #ffffff 1px solid;
				BACKGROUND-COLOR: #cccccc;
			}
			
			td,
			a {
				font: 9pt 宋体;
			}
			
			a:link,
			a:visited {
				color: #000000;
				text-decoration: none;
			}
			
			a:hover {
				text-decoration: none;
			}
		</style>
		<script type="text/javascript" src="/zjcclims/js/video.js"></script>
	</head>

	<body>
		<table border=0 width=100% height=550 valign=top>
			<tr>
				<td height="490" width="800" bgcolor="#CCCCCC">
					<object classid="clsid:BE020CC9-521F-4641-85E1-3B631B7ADDD9" id="preview" width="800" height="500" name="preview">
						<PARAM  NAME="UserName" VALUE="admin"/>
						<PARAM  NAME="PrivilegeCode" VALUE="0401,0402,"/>
					</object>
				</td>
				<td rowspan="2">
					<table width="230" height="100%" cellpadding="10" cellspacing="0">

						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">功能按钮: </font>
								<input type="button" id="play" name="play" style="display:" value="播放" onclick="startPlay()" />
								<input type="button" id="play" name="play" style="display:" value="停止" onclick="StopPreview()" />
								<br /><br />
								<font color="#ffffff">移动速度：</font>
								<select name="select" id="speed" onchange="speed_onchange()">
									<OPTION value="1">1</OPTION>
									<OPTION value="2">2</OPTION>
									<OPTION value="3">3</OPTION>
									<OPTION value="4" selected>4</OPTION>
									<OPTION value="5">5</OPTION>
									<OPTION value="6">6</OPTION>
									<OPTION value="7">7</OPTION>
									<OPTION value="8">8</OPTION>
									<OPTION value="9">9</OPTION>
									<OPTION value="10">10</OPTION>
									<OPTION value="11">11</OPTION>
									<OPTION value="12">12</OPTION>
									<OPTION value="13">13</OPTION>
									<OPTION value="14">14</OPTION>
									<OPTION value="15">15</OPTION>
									<OPTION value="16">16</OPTION>
								</select>
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td valign="top" align="left" height=100>
								<input type="button" value="左上" onclick="lefttop(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<input type="button" value="向上" onclick="actiontop(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<input type="button" value="右上" onclick="righttop(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<br><input type="button" value="向左" onclick="actionleft(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<input type="button" value="　　" onclick="actionstop(hardwareport,Speed)"/>
								<input type="button" value="向右" onclick="actionright(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<br><input type="button" value="左下" onclick="leftbottom(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<input type="button" value="向下" onclick="actionbottom(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<input type="button" value="右下" onclick="rightbottom(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<br><input type="button" value="放大" onclick="zoomout(hardwareport,Speed);actionstop(hardwareport,Speed)" />
								<input type="button" value="缩小" onclick="zoomin(hardwareport,Speed);actionstop(hardwareport,Speed)" />
							</td>
						</tr>
						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">节点: </font>
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td height=50 valign="center" align="left">
								<select name="cameras" id="cameras"  style="width:200px;" onchange="cameras_change()">
									<OPTION value="001401">C403-3建设工程评标模拟实训室</OPTION>
									<OPTION value="001402">C403-2建设工程评标模拟实训室</OPTION>
									<OPTION value="001414">192.168.10.33_8000_通道1</OPTION>
									<OPTION value="001413">192.168.10.34_8000_通道1</OPTION>
									<OPTION value="001403">C612-2</OPTION>
									<OPTION value="001405">C612-1</OPTION>
									<OPTION value="001407">C611-2</OPTION>
									<OPTION value="001408">C611-1</OPTION>
									<OPTION value="001409">C604-1 BIM工作室</OPTION>
									<OPTION value="001410">C602-2审计中心工作室</OPTION>
									<OPTION value="001411">C601-2</OPTION>
									<OPTION value="001415">C601-1</OPTION>
									<OPTION value="001404">C613-1建筑规划与景观设计工作室</OPTION>
									<OPTION value="001406">C604-2 BIM工作室</OPTION>
									<OPTION value="001412">C605-1协同创新工作室</OPTION>
									<OPTION value="001417">C613-2建筑规划与景观设计工作室</OPTION>
									<OPTION value="001418">C602-1审计中心工作室</OPTION>
									<OPTION value="001419">C610-3</OPTION>
									<OPTION value="001420">C605-2协同创新工作室</OPTION>
									<OPTION value="001423">C510-3施工项目管理实务模拟实训室</OPTION>
									<OPTION value="001425">C509-3建筑远程监控实训室</OPTION>
									<OPTION value="001426">C501-1工程监理专业智慧化中心</OPTION>
									<OPTION value="001427">C507-2工程资料管理实务模拟实训室</OPTION>
									<OPTION value="001384">C508-1分户验收实训室</OPTION>
									<OPTION value="001385">192.168.10.59_8000_通道1</OPTION>
									<OPTION value="001386">C座六楼安全门北</OPTION>
									<OPTION value="001380">C座六楼安全门南</OPTION>
									<OPTION value="001387">C座六楼安全门中</OPTION>
									<OPTION value="001391">C203-2建筑模型实训室</OPTION>
									<OPTION value="001392">C座二楼安全门中</OPTION>
									<OPTION value="001393">C座二楼安全门南</OPTION>
									<OPTION value="001382">C座二楼安全门北</OPTION>
									<OPTION value="001396">C511-3专项施工方案编制模拟实训室</OPTION>
									<OPTION value="001388">C506-2建筑工程过程管理综合实训室</OPTION>
									<OPTION value="001381">C505-1经济信息化实训室</OPTION>
									<OPTION value="001389">C座五楼消防门南</OPTION>
									<OPTION value="001390">C504-1施工图识读实务模拟实训室</OPTION>
									<OPTION value="001394">C座五楼消防门中</OPTION>
									<OPTION value="001395">C503-1工程项目管理沙盘室</OPTION>
									<OPTION value="001383">C座五楼消防门北</OPTION>
									<OPTION value="001397">C座四楼安全门北</OPTION>
									<OPTION value="001398">C座四楼安全门南</OPTION>
									<OPTION value="001369">C403-1建设工程评标模拟实训室</OPTION>
									<OPTION value="001370">C408-2项目评价、分析实训室</OPTION>
									<OPTION value="001361">C407-2建设工程交易模拟实训室</OPTION>
									<OPTION value="001372">192.168.10.82_8000_通道1</OPTION>
									<OPTION value="001373">C座四楼消防门北</OPTION>
									<OPTION value="001367">C405-3房地产营销策划实训室</OPTION>
									<OPTION value="001371">C402-4物业管理实训室</OPTION>
									<OPTION value="001374">C座三楼安全门中</OPTION>
									<OPTION value="001362">C座三楼安全门南</OPTION>
									<OPTION value="001376">C308-3建筑设计实训室一</OPTION>
									<OPTION value="001365">C307-3建筑设计实训室二</OPTION>
									<OPTION value="001378">C303-1装饰与环境艺术设计实训室</OPTION>
									<OPTION value="001360">C306-3花艺设计实训室</OPTION>
									<OPTION value="001363">C座三楼安全门北</OPTION>
									<OPTION value="001364">C305-1植物与环境实训室</OPTION>
									<OPTION value="001375">C304-3植物标本实训室</OPTION>
									<OPTION value="001379">C301-3美术实训室</OPTION>
									<OPTION value="001377">C座一楼安全门中</OPTION>
									<OPTION value="001366">C座一楼安全门南</OPTION>
									<OPTION value="001345">C座大门</OPTION>
									<OPTION value="001341">C106-4创意实训室</OPTION>
									<OPTION value="001351">C614-2建筑规划与景观设计工作室</OPTION>
									<OPTION value="001340">C610-1</OPTION>
									<OPTION value="001357">C203-1建筑模型实训室</OPTION>
									<OPTION value="001355">C203-3建筑模型实训室</OPTION>
									<OPTION value="001346">C203-4建筑模型实训室</OPTION>
									<OPTION value="001347">C203-5建筑模型实训室</OPTION>
									<OPTION value="001342">C203-6建筑模型实训室</OPTION>
									<OPTION value="001349">实训场3</OPTION>
									<OPTION value="001352">实训场2</OPTION>
									<OPTION value="001354">实训场1</OPTION>
									<OPTION value="001343">C510-1施工项目管理实务模拟实训室</OPTION>
									<OPTION value="001358">C506-1建筑工程过程管理综合实训室</OPTION>
									<OPTION value="001356">C511-1专项施工方案编制模拟实训室</OPTION>
									<OPTION value="001348">C511-3专项施工方案编制模拟实训室</OPTION>
									<OPTION value="001359">C505-2经济信息化实训室</OPTION>
									<OPTION value="001344">C511-4专项施工方案编制模拟实训室</OPTION>
									<OPTION value="001353">C510-2施工项目管理实务模拟实训室</OPTION>
									<OPTION value="001324">C504-2施工图识读实务模拟实训室</OPTION>
									<OPTION value="001320">C504-3施工图识读实务模拟实训室</OPTION>
									<OPTION value="001337">C505-3经济信息化实训室</OPTION>
									<OPTION value="001335">C503-3工程项目管理沙盘室</OPTION>
									<OPTION value="001328">C509-4建筑远程监控实训室</OPTION>
									<OPTION value="001331">C507-3工程资料管理实务模拟实训室</OPTION>
									<OPTION value="001327">C509-2建筑远程监控实训室</OPTION>
									<OPTION value="001339">C509-1建筑远程监控实训室</OPTION>
									<OPTION value="001329">C507工程资料管理实务模拟实训室</OPTION>
									<OPTION value="001330">C508分户验收实训室</OPTION>
									<OPTION value="001326">C510-4施工项目管理实务模拟实训室</OPTION>
									<OPTION value="001332">C503-2工程项目管理沙盘室</OPTION>
									<OPTION value="001333">C501-3工程监理专业智慧化中心</OPTION>
									<OPTION value="001325">C501-2工程监理专业智慧化中心</OPTION>
									<OPTION value="001338">C508-3分户验收实训室</OPTION>
									<OPTION value="001336">C508-2分户验收实训室</OPTION>
									<OPTION value="001307">C408-3项目评价、分析实训室</OPTION>
									<OPTION value="001313">C408-1项目评价、分析实训室</OPTION>
									<OPTION value="001316">C407-3建设工程交易模拟实训室</OPTION>
									<OPTION value="001318">C407-1建设工程交易模拟实训室</OPTION>
									<OPTION value="001312">C406-3三维仿真技术多功能实训厅</OPTION>
									<OPTION value="001300">C406-2三维仿真技术多功能实训厅</OPTION>
									<OPTION value="001308">C406-1三维仿真技术多功能实训厅</OPTION>
									<OPTION value="001309">C404-3建设工程编标综合实务实训室</OPTION>
									<OPTION value="001317">C404-1建设工程编标综合实务实训室</OPTION>
									<OPTION value="001306">C405-4房地产营销策划实训室</OPTION>
									<OPTION value="001310">C405-2房地产营销策划实训室</OPTION>
									<OPTION value="001301">C405-1房地产营销策划实训室</OPTION>
									<OPTION value="001319">C402-3物业管理实训室</OPTION>
									<OPTION value="001280">C402-2物业管理实训室</OPTION>
									<OPTION value="001282">C402-1物业管理实训室</OPTION>
									<OPTION value="001281">C306-1花艺设计实训室</OPTION>
									<OPTION value="001297">C308-4建筑设计实训室一</OPTION>
									<OPTION value="001283">C308-2建筑设计实训室一</OPTION>
									<OPTION value="001291">C308-1建筑设计实训室一</OPTION>
									<OPTION value="001294">C307-4建筑设计实训室二</OPTION>
									<OPTION value="001298">C307-2建筑设计实训室二</OPTION>
									<OPTION value="001295">C307-1建筑设计实训室二</OPTION>
									<OPTION value="001288">C303-4装饰与环境艺术设计实训室</OPTION>
									<OPTION value="001292">C303-3装饰与环境艺术设计实训室</OPTION>
									<OPTION value="001299">C303-2装饰与环境艺术设计实训室</OPTION>
									<OPTION value="001293">C306-4花艺设计实训室</OPTION>
									<OPTION value="001290">C306-2花艺设计实训室</OPTION>
									<OPTION value="001262">C305-4植物与环境实训室</OPTION>
									<OPTION value="001276">C305-3植物与环境实训室</OPTION>
									<OPTION value="001260">C305-2植物与环境实训室</OPTION>
									<OPTION value="001263">C304-4植物标本实训室</OPTION>
									<OPTION value="001272">C304-2植物标本实训室</OPTION>
									<OPTION value="001268">C304-1植物标本实训室</OPTION>
									<OPTION value="001265">C301-4美术实训室</OPTION>
									<OPTION value="001261">C301-2美术实训室</OPTION>
									<OPTION value="001271">C301-1美术实训室</OPTION>
									<OPTION value="001264">C106-2创意实训室</OPTION>
									<OPTION value="001274">C106-1创意实训室</OPTION>
									<OPTION value="001278">C106-3创意实训室</OPTION>
									<OPTION value="001277">C106-5创意实训室</OPTION>

								</select>
							</td>
						</tr>
						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">软件下载: </font>
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td height=50 valign="center" align="left">
								<a href="download/HIKOCX.exe" target="_blank">OCX控件下载</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="230" id="msg" class="down"></td>
			</tr>
		</table>
		<script type="text/javascript">
			var request = UrlSearch();
			var hardWareip="${serverIp}";
			var hardwareport="${hardwarePort}";
//			var hardwareip = "10.80.4.237";
//			var hardwareport = request['id'];
//			if(hardwareport == undefined) {
//				hardwareport = "001313";
//			}
			var isLogin = false;
			function cameras_change(){
				StopPreview();
				cameras=document.getElementById("cameras");
				hardwareport=cameras.options[cameras.selectedIndex].value;
				startPlay();
			}
			function startPlay() {
				if(isLogin == false) LoginPlat(hardWareip);
				SetLayoutType(1);
				StartPreview(hardwareport);

			}
			function startPlay1(code) {
				if(isLogin == false) LoginPlat(hardWareip);
				SetLayoutType(1);
				StartPreview(code);

			}
			function UrlSearch() {
				var name, value;
				var str = location.href; //取得整个地址
				var num = str.indexOf("?");
				str = str.substr(num + 1); //取得所有参数
				var arr = str.split("&"); //分到数组中
				for(var i = 0; i < arr.length; i++) {
					num = arr[i].indexOf("=");
					if(num > 0) {
						name = arr[i].substr(0, num);
						value = arr[i].substr(num + 1);
						arr[name] = value;
					}
				}
				return arr;
			}
		</script>
		<script LANGUAGE="JavaScript" FOR="window" EVENT="onLoad()">
			showMessage("正在登录平台[" + hardWareip + "]...");
			result = LoginPlat(hardWareip);
			if(result == 0) {
				showMessage("登录成功");
				SetLayoutType(1);
			} else {
				showMessage("登录失败，请查看日志preview.log");
			}
		</script>

	</body>

</html>