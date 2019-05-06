<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<html>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
<meta name="decorator" content="iframe"/>
	<meta charset="UTF-8">
		<title>回放</title>
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
		<script type="text/javascript">
			var request = UrlSearch()
//			var hardwareip = document.frmApp.IP.value;
			var hardwareport = "${port}";
//			if(hardwareport == undefined) {
//				hardwareport = "001313";
//			}

			function LoginPlat() {
				//Demo默认使用密码登录方式
				var IP = document.frmApp.IP.value;
				var port = document.frmApp.port.value;
				var UserName = document.frmApp.UserName.value;
				var Password = document.frmApp.Password.value;
				var v1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><LoginInfo><LoginType>2</LoginType><IP>" + IP + "</IP><Port>" + port +
					"</Port><UserName>" + UserName + "</UserName><Password>" + Password + "</Password></LoginInfo>";
				//alert(v1);
				var v = playback.LoginPlat(v1);
				if(v != 0)
					alert("登录失败，请查看日志playback.log");
			}

			function OnInit() {
				//document.getElementById('ocxContainer').innerHTML = '<object classid="clsid:88F0ADA4-0B55-49EE-BD4E-FC87AD058DEF" id="playback"  width="800" height="600" name="ocx" ></object>';
				var _param = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
					"<PlaybackOcxConfig><DecodeEffect>0</DecodeEffect><PlayMode>0</PlayMode><SupportFishEye>1</SupportFishEye>" +
					"<WindowToolbar><ShowMode>2</ShowMode><ShowItems><BtnItem>0</BtnItem><BtnItem>5</BtnItem><BtnItem>7</BtnItem>" +
					"<BtnItem>12</BtnItem><BtnItem>17</BtnItem><BtnItem>21</BtnItem><BtnItem>32</BtnItem></ShowItems></WindowToolbar>" +
					"<SnapParam><FileFormat>0</FileFormat><FilePath>C:\\Users\\huangjunyf2\\Documents\\capture\\</FilePath>" +
					"<FileCategorization>0</FileCategorization><FileNameFormat></FileNameFormat><SnapMode>2</SnapMode>" +
					"<ContinousNum>3</ContinousNum><ContinousMode>2</ContinousMode><ContinousInterval>1000</ContinousInterval></SnapParam>" +
					"<ClipParam><FilePath>C:\\Users\\huangjunyf2\\Documents\\clip\\</FilePath><FileNameFormat></FileNameFormat><PackSize>256</PackSize></ClipParam>" +
					"<DiskWarning><EnableDiskWarning>0</EnableDiskWarning><WarningSpace>500</WarningSpace><MinimumSpace>100</MinimumSpace></DiskWarning></PlaybackOcxConfig>";
				//alert(_param);
				playback.SetLocalParam(_param);
			}

			function OnPlaybackEx() {
				var _param = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
					"<PlaybackBasicInfo><CameraIndexCode>"+hardwareport+"</CameraIndexCode>" +
					"<StoreDeviceType>0</StoreDeviceType>" +
					"<BeginTime>"+document.frmApp.BeginTime.value+"</BeginTime>" +
					"<EndTime>"+document.frmApp.EndTime.value+"</EndTime>" +
					"</PlaybackBasicInfo>";
				//alert(_param);
				playback.SetBasicInfoEx(_param);
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
			function time_onchange(){
				var durtime=document.getElementById('durtime').value;
				var time="${time}";
				var id="${id}";
				window.location.href="${pageContext.request.contextPath}/labRoom/openVideoBackByDurtime?id="+id+"&time="+time+"&durtime="+durtime;
			}
		</script>
	</head>

	<body>
		<table border=0 width=100% height=550 valign=top>
			<tr>
				<td height="490" width="800" bgcolor="#CCCCCC" id="ocxContainer">
<object classid="clsid:88F0ADA4-0B55-49EE-BD4E-FC87AD058DEF" id="playback"  width="800" height="600" name="ocx" ></object>
				</td>
				<td rowspan="2">
					<table width="230" height="100%" cellpadding="10" cellspacing="0">

						<tr bgcolor="#162A4D">
							<td width="200" height="20" valign="bottom" align="left" class="zi">

								<font color="#ffffff" id=Node>功能按钮: </font>
								<br />
								<font color="#ffffff">功能按钮: </font>
								<!--<input type="button" id="play" name="play" style="display:" value="初始化" onclick="OnInit()" />-->
								<!--<input type="button" id="play" name="play" style="display:" value="登录" onclick="LoginPlat()" />-->
								<input type="button" id="play" name="play" style="display:" value="播放" onclick="OnPlaybackEx()" />
								<!--<input type="button" id="play" name="play" style="display:" value="停止" onclick="StopPreview()" />-->
								<!--<br /><br />-->
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td valign="top" align="left" height=100>
								<form name="frmApp" mothed="post" action="">
									开始时间:<input type="text" style="width:180px" name="BeginTime" id="BeginTime" value="${startime}"><br>
									结束时间:<input type="text" style="width:180px" name="EndTime" id="EndTime" value="${endtime}">
									<!--IP--><br><input type="hidden" style="width:180px" name="IP" id="IP" value="${ip}"><br>
									<!--Port--><br><input type="hidden" style="width:180px" name="port" id="port" value="85"><br>
									<!--User--><br><input type="hidden" style="width:180px" name="UserName" id="UserName" value="admin"><br>
									<!--Pass--><br><input type="hidden" style="width:180px" name="Password" id="Password" value="Gengshang123"><br>
									
								</form>
							</td>
						</tr>
						<tr>
							<td>
							截取时间（开门前后时间）：
							<select name="select" id="durtime" onchange="time_onchange()">
									<option value="">${durtime}</option>
									<OPTION value="0">30秒</OPTION>
									<OPTION value="1">1分钟</OPTION>
									<OPTION value="2">2分钟</OPTION>
									<OPTION value="5">5分钟</OPTION>
									<OPTION value="10">10分钟</OPTION>
									<OPTION value="30">30分钟</OPTION>
									<OPTION value="60">1小时</OPTION>
								</select>
							</td>
						</tr>
						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">节点: </font>
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td height=50 valign="center" align="left">

							</td>
						</tr>
						<tr bgcolor="#162A4D">
							<td width="200" height="25" valign="bottom" align="left" class="zi">
								<font color="#ffffff">软件下载: </font>
							</td>
						</tr>
						<tr bgcolor="#fffff">
							<td height=50 valign="center" align="left">
								<a href="http://download.hikvision.com/UploadFile/Soft/4200/iVMS-4200(v2.7.2.7).rar" target="_blank">OCX控件下载</a>${ip}${port}
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="230" id="msg" class="down"></td>
			</tr>
		</table>
		<SCRIPT LANGUAGE="JavaScript" FOR="window" EVENT="onLoad()">
			document.getElementById("msg").innerHTML = "正在初始化"
			OnInit()
			document.getElementById("msg").innerHTML = "初始化完成，正在登录"
			LoginPlat()
			document.getElementById("msg").innerHTML = "登录完成"
			playback.SetLayoutType(1);
		</script>
		<script language="javascript" for="playback" event="MsgNotify(iMsg,iError,szDetail,lWnd)">
			szMsg = "消息类型:" + iMsg + "，错误码:" + iError + "，详细信息:" + szDetail + "，窗口ID: " + lWnd;
			//alert(szMsg);
			document.getElementById("msg").innerHTML = szMsg;
			if(iMsg == 0x02000006) {
				//alert("set token");
				var id = iError;
				//playback.SetToken(id, "ST-64517-RIuQA2HVbKpaaiAgiMbb-cas");
				playback.SetToken(id, "");
			}
		</script>
	</body>

</html>