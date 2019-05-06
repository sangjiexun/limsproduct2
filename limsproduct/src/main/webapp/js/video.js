var Speed=4;CmdID=0;
//设置视频源移动速度
function speed_onchange(){
	speed=document.getElementById('speed')
	Speed=speed.options[speed.selectedIndex].value;
}
function LoginPlat(IP) {
	//Demo默认使用密码登录方式
	var port = "85";
	var UserName = "admin";
	var Password = "Gengshang123";
	var v1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><LoginInfo><LoginType>2</LoginType><SynLogin>1</SynLogin><IP>" + IP + "</IP><Port>" + port +
		"</Port><UserName>" + UserName + "</UserName><Password>" + Password + "</Password></LoginInfo>";
	var result = preview.LoginPlat(v1);
	if(result == 0) isLogin = true
	return result
}

function StartPreview(code) {
	var _param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Preview><CamIndexCode>" + code + "</CamIndexCode></Preview>";
	preview.StartPreview(_param);
}

function showMessage(data) {
	msg = document.getElementById("msg")
	msg.innerHTML = data
}

function StopPreview() {
	var idx = preview.GetSelWnd();
	preview.StopPreview(idx);
}

function StopAll() {
	var v = preview.StopPreview(-1);
}

function PtzControl(Code, vFunid, CmdId, vSpeed) {
	//				var Code = document.frmApp.CameraIndexCode.value;
	//				var vFunid = document.frmApp.FunId.value;
	//				var CmdId = document.frmApp.CmdId.value;
	//				var vSpeed = document.frmApp.Speed.value;
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>" + vFunid + "</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify><Param>1</Param>" +
		"<ControInfo><CmdId>" + CmdId + "</CmdId><Action></Action><Speed>" + vSpeed + "</Speed></ControInfo>" +
		"<PresetInfo><Info No=\"1\" Name=\"2\" Type=\"1\" /><Info No=\"2\" Name=\"3\" Type=\"2\" /></PresetInfo>" +
		"<CruiseInfo><Info No=\"1\" Dwell=\"1\" Speed=\"1\" /><Info No=\"2\" Dwell=\"2\" Speed=\"2\" /></CruiseInfo></PtzControl>";
	//alert(_param);
	var v = preview.PtzControl(_param);
	alert(v);
}

function AddSecuriteZone(IP) //添加信任站点
{
//	var IP = document.frmApp.IP.value;
	var v = preview.AddSecuriteZone(IP);
}

function EnterFishEye() //进入鱼眼模式
{
	preview.EnterFishEye();
}

function ExitFishEye() //退出鱼眼模式
{
	preview.ExitFishEye();
}

function SetLayoutType(v) //设置窗口布局
{
//	var v = document.frmApp.Layout.value;
	preview.SetLayoutType(v);
}

function SetInstPlayCfg(v1) //设置即时回放时间
{
//	var v1 = document.frmApp.data.value;
	var v = preview.SetInstPlayCfg(v1);
}

function GetVersion() //获取版本号
{
	var v = preview.GetVersion();
//	alert(v);
	return v
}

function SetPerCfgInfo() //设置性能计划，如预览大于9路自动切换为子码流，双击放大单窗口显示时自动切换为主码流
{
	var v1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Per><ChgWndNum>9</ChgWndNum><AutoChg>1</AutoChg><DecodePer>5</DecodePer><PlayPer>0</PlayPer></Per>";
	var v = preview.SetPerCfgInfo(v1);
}

function EnableMenu(v1) //设置鼠标右键菜单栏是否启用
{
//	var v1 = document.frmApp.data.value;
	var v = preview.EnableMenu(v1);
}

function GetDirectoryPath() //弹出目录选择框，返回最终用户选择的路径
{
	var v = preview.GetDirectoryPath();
}

function GetSystemDrive() //获取当前系统驱动盘符
{
	var v = preview.GetSystemDrive();
//	alert(v);
	return v
}

function GetUserDocument() //获取用户文档目录
{
	var v = preview.GetUserDocument();
//	alert(v);
	return v
}

function GetSelWndIsPlay() //获取当前选中窗口是否在播放状态
{
	var v = preview.GetSelWndIsPlay();
//	alert(v);
	return v
}

function SetMainToolBarShow(data) //设置主工具栏的显示隐藏
{
//	var data = document.frmApp.data.value;
	preview.SetMainToolBarShow(data);
}

function SetToolBarVisible(data) //设置播放窗口的小工具栏的显示隐藏
{
//	var data = document.frmApp.data.value;
	preview.SetToolBarVisible(data);
}

function EnableSysCfgButton(data) //设置系统设置按钮的使能控制
{
//	var data = document.frmApp.data.value;
	preview.EnableSysCfgButton(data);
}

function ShowFullScreenButton(data) //设置切换全屏按钮的显示隐藏
{
//	var data = document.frmApp.data.value;
	preview.ShowFullScreenButton(data);
}

//云镜控制各接口
function zoomin(Code,vSpeed) //焦距缩小
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=12
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>12</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";

	var v = preview.PtzControl(_param);
	alert(v);
}

function zoomout(Code,vSpeed) //焦距放大
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=11
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>11</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function qianyi(Code,vSpeed) //焦点前移
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=14
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>14</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function houyi(Code,vSpeed) //焦点后移
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=13
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>13</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function zoomin2(Code,vSpeed) //光圈缩小
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=16
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>16</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function zoomout2(Code,vSpeed) //光圈放大
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=15
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>15</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function lock(Code) //锁定
{
//	var Code = document.frmApp.CameraIndexCode.value;

	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>1</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify><Param>60</Param></PtzControl>"; //60秒
	var v = preview.PtzControl(_param);
//	alert(v);
	return v
}

function unlock(Code) //解锁
{
//	var Code = document.frmApp.CameraIndexCode.value;
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>2</FunId> <CamIndexCode>" + Code + "</CamIndexCode><MsgNotify>0</MsgNotify></PtzControl>"; //60秒
	var v = preview.PtzControl(_param);
//	alert(v);
	return v
}

function DZoom(Code,vDZoom) //设置当前窗口3D放大状态
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vDZoom = document.frmApp.data.value;
	if(vDZoom != 0) {
		vDZoom = 1;
//		document.frmApp.data.value = 0;
	}
//	else
//		document.frmApp.data.value = 1;
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>3</FunId><CamIndexCode>" + Code + "</CamIndexCode><Param>" + vDZoom + "</Param></PtzControl>";
	var v = preview.PtzControl(_param);
//	alert(v);
	return v
}

function dengguang(vOpen,Code,vFunid,vSpeed) {
//	var vOpen = document.frmApp.data.value; //控制打开还是关闭灯光
	if(vOpen != 0) {
		vOpen = 1;
//		document.frmApp.data.value = 0;
	}
//	} else
//		document.frmApp.data.value = 1;
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vFunid = document.frmApp.FunId.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=2
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>2</CmdId><Action>" + vOpen + "</Action><Param></Param><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
//	alert(v);
	return v
}

function yushua(vOpen,Code,vFunid,vSpeed) {
//	var vOpen = document.frmApp.data.value; //控制打开还是关闭雨刷
	if(vOpen != 0) {
		vOpen = 1;
//		document.frmApp.data.value = 0;
	}
//	} else
//		document.frmApp.data.value = 1;

//	var Code = document.frmApp.CameraIndexCode.value;
//	var vFunid = document.frmApp.FunId.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=3
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>3</CmdId><Action>" + vOpen + "</Action><Param></Param><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
//	alert(v);
	return v
}

function scan(Code,vScan,vSpeed) {
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vScan = document.frmApp.data.value;
	if(vScan != 0) {
		vScan = 1;
//		document.frmApp.data.value = 0;
	}
//	} else
//		document.frmApp.data.value = 1;
	CmdID=29
//	var vSpeed = document.frmApp.Speed.value;
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify><Param>0</Param>" +
		"<ControInfo><CmdId>29</CmdId><Action>" + vScan + "</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}
//八方向
function actiontop(Code,vSpeed) //向上
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=21
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>21</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function righttop(Code,vSpeed) //右上
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=26
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>26</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function actionright(Code,vSpeed) //向右
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=24
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>24</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function rightbottom(Code,vSpeed) //右下
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=28
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>28</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function actionbottom(Code,vSpeed) //向下
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=22
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>22</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function leftbottom(Code,vSpeed) //左下
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=27
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>27</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function actionleft(Code,vSpeed) //向左
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=23
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>23</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	//alert(_param)
	var v = preview.PtzControl(_param);
}
function actionstop(Code,vSpeed){
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>"+CmdID+"</CmdId><Action>0</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

function lefttop(Code,vSpeed) //左上
{
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vSpeed = document.frmApp.Speed.value;
	CmdID=25
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>4</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Wait>0</Wait><MsgNotify>0</MsgNotify>" +
		"<ControInfo><CmdId>25</CmdId><Action>1</Action><Speed>" + vSpeed + "</Speed></ControInfo></PtzControl>";
	var v = preview.PtzControl(_param);
}

//设置预置点
function SetPresetInfo(Code) {
//	var Code = document.frmApp.CameraIndexCode.value;
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>7</FunId> <CamIndexCode>" + Code + "</CamIndexCode><MsgNotify>0</MsgNotify>" +
		"<PresetInfo><Info No=\"1\" Name=\"1\" Type=\"1\" /></PresetInfo></PtzControl>";
	var v = preview.PtzControl(_param);
	//alert(v);
}

//获取预置点
function GetPresetInfo(Code,vType) {
//	var Code = document.frmApp.CameraIndexCode.value;
//	var vType = document.frmApp.data.value; //预置点类型：1:预置点 2:巡航
	var _param = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<PtzControl><FunId>5</FunId> <CamIndexCode>" + Code + "</CamIndexCode><Param>" + vType + "</Param></PtzControl>";
	var v = preview.PtzControl(_param);
//	alert(v);
	return v
}

//642新增接口
function EnterEagleEye(vCount) {
//	var vCount = document.frmApp.data.value; //大鹰眼设备节点下监控点个数
	var v = preview.EnterEagleEye(v);
}

function ExitEagleEye() {
	var v = preview.ExitEagleEye();
}

function GetPtzTraceMode() {
	var v = preview.GetPtzTraceMode();
	//alert(v);
}

function SetPtzTraceMode(vState) {
//	var vState = document.frmApp.data.value; //0或1
	var v = preview.SetPtzTraceMode(vState);
}

function SetBrowserType(vType) {
//	var vType = document.frmApp.data.value; //0或1
	var v = preview.SetBrowserType(vType);
}

function SwitchWindow(vIndex1,vIndex2) {
//	var vIndex1 = document.frmApp.Speed.value;
//	var vIndex2 = document.frmApp.data.value;
	var v = preview.SwitchWindow(vIndex1, vIndex2);
//	alert(v);
	return v
}

function SetPlayWndStateText(vData,vIndex) {
//	var vData = document.frmApp.Result.value; //0或1
//	var vIndex = document.frmApp.data.value;
	var v = preview.SetPlayWndStateText(vIndex, vData);
}