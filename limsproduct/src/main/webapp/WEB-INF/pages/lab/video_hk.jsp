<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html lang="en">

	<head>
		<meta name="decorator" content="iframe" />
    	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta charset="UTF-8">
		<title></title>
		<style>
			.down{BORDER-LEFT: #999999 1px solid;BORDER-TOP: #999999 1px solid;BORDER-BOTTOM: #ffffff 1px solid;BORDER-RIGHT: #ffffff 1px solid;BACKGROUND-COLOR: #cccccc;}
		</style>
		<script src="${pageContext.request.contextPath}/lab_video/jquery.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/lab_video/video_hk/video.js"></script>
	<script type="text/javascript">
			var hardwareip="${serverIp}";
			var hardwareport="${hardwarePort}";
			var isLogin=false;
			function startPlay(){
				if(isLogin==false)LoginPlat(hardwareip);
				SetLayoutType(1)
				StartPreview(hardwareport);
				
			}
		</script>
		<script LANGUAGE="JavaScript" FOR="window" EVENT="onLoad()">
			showMessage("正在登录平台${serverIp}...");
			result=LoginPlat("${serverIp}");
			if(result==0){
				showMessage("登录成功");SetLayoutType(1);
			}else{
				showMessage("登录失败，请查看日志preview.log");
			}
		</script>
	</head>

	<body>
		<table border=0 width=100% height=550 valign="top">
			<tr>
				<td height="490" width="800" bgcolor="#CCCCCC">
					<object classid="clsid:BE020CC9-521F-4641-85E1-3B631B7ADDD9" id="preview" width="800" height="500" name="preview">
						<PARAM  NAME="UserName" VALUE="admin"/>
						<PARAM  NAME="PrivilegeCode" VALUE="0401,0402,"/>
					</object>
				</td>
				<td rowspan="2" >
					<table width="230" height="100%" cellpadding="10" cellspacing="0">
						
						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">功能按钮: </font>
								<input type="button" id="play" name="play" style="display:" value="播放" onclick="startPlay()" />
								<br /><br /><font color="#ffffff">移动速度：</font>
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
							<td valign="top" align="left">
								
								<input type="button" value="向上" onclick="top1(hardwareport,Speed)" />
								<input type="button" value="右上" onclick="righttop(hardwareport,Speed)" />
								<input type="button" value="向右" onclick="right(hardwareport,Speed)" />
								<input type="button" value="右下" onclick="rightbottom(hardwareport,Speed)" />
								<input type="button" value="向下" onclick="bottom(hardwareport,Speed)" />
								<input type="button" value="左下" onclick="leftbottom(hardwareport,Speed)" />
								<input type="button" value="向左" onclick="left(hardwareport,Speed)" />
								<input type="button" value="左上" onclick="lefttop(hardwareport,Speed)" />
							</td>
						</tr>
						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">软件下载: </font>
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td height=50 valign="center" align="left">
								<a href="${pageContext.request.contextPath}/lab_video/video_hk/HIKOCX.exe">OCX控件下载</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="230" id="msg" class="down"></td>
			</tr>
		</table>
	</body>

</html>