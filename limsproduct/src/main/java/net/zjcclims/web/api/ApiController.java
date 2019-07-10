package net.zjcclims.web.api;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import net.zjcclims.dao.LabRoomAgentDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.LabRoomDeviceReservationDAO;
import net.zjcclims.dao.RemoteOpenDoorDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller("ApiController")
@SessionAttributes({"selected_academy"})
@RequestMapping("/api")
public class ApiController<JsonResult> {
	@InitBinder
	public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
																				// static
																				// property
																				// editors.
		binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
		binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
		binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
		binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
		binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
		binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
		binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
	}
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private RemoteOpenDoorDAO remoteOpenDoorDAO;
	
	@RequestMapping(value="/getApi"/*\,produces="text/html;charset=UTF-8"*/,
			produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public @ResponseBody String getApi(@RequestParam int term){
		JSONObject aapJson = new JSONObject();  
		List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "ok");
		itemList.add(map);
    	aapJson.put("callback", itemList);
    	String item = "successCallback("+aapJson.toString()+")";
		return item;
	}
	
	/**
	 * 小程序：实验室预约判冲
	 * @param term
	 * @param weekday
	 * @param labrooms
	 * @param startclass
	 * @param endclass
	 * @param weeks
	 * @return
	 */
	@RequestMapping(value="/getVailLabroom"/*,produces="text/html;charset=UTF-8"*/,
			produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public @ResponseBody String getVailLabroom(@RequestParam int term,int weekday,int[] labrooms,int startclass,int endclass,int[] weeks){
		int[] classes = new int[endclass-startclass+1];
		int a= endclass-startclass+1;
		for(int i =0;i<a;i++){
			classes[i]=startclass;
			startclass++;
		}
		boolean timetableConflict = outerApplicationService.isTimetableConflict(term, weekday, labrooms, classes, weeks);
		if(timetableConflict==true){
			return "ok";
		}
		return "no";
	}
	
	
	/**
	 * 小程序：设备预约判冲
	 * @param deviceId
	 * @param starTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/getVailDevice",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public @ResponseBody String getVailDevice (@RequestParam int deviceId,String starTime,String endTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int flag=1;
		Date stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(starTime);
        Date etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
		// 循环遍历设备预约表lab_room_device_reservation判断是否有冲突，如果有，则提示返回
		List<LabRoomDeviceReservation> labRoomDeviceReservations = labRoomDeviceReservationDAO
				.executeQuery("select c from LabRoomDeviceReservation c where c.labRoomDevice.id ="
						+ deviceId, 0, -1);
		for (LabRoomDeviceReservation labRoomDeviceReservation :labRoomDeviceReservations ) {
			if (etime.compareTo(labRoomDeviceReservation.getBegintime().getTime()) >= 0&&stime.compareTo(labRoomDeviceReservation.getBegintime().getTime())<= 0
					|| etime.compareTo(labRoomDeviceReservation.getEndtime().getTime()) >= 0&&stime.compareTo(labRoomDeviceReservation.getEndtime().getTime()) <= 0
					|| stime.compareTo(labRoomDeviceReservation.getBegintime().getTime()) >= 0&&etime.compareTo(labRoomDeviceReservation.getEndtime().getTime()) <= 0)
			{
				flag=0;
			}
		}
		if(flag==0){
			return "no";
		}
		return "yes";
	
	}

	/****************************************************************************
	 * @功能：当天预约审核通过后门禁注册--实验室（不用登录）
	 * @作者：张德冰
	 * @时间：2018.06.25
	 ****************************************************************************/
	@RequestMapping(value="/refreshLabRoomReservation", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	public @ResponseBody String refreshLabRoomReservation(@RequestParam Integer roomId) throws IOException {
		// 找到对应的电源控制器
		List<LabRoomAgent> doors = labRoomService.findLabRoomAgentAccessByRoomId(roomId);
		LabRoomAgent door = new LabRoomAgent();
		if (doors!=null && doors.size()>0) {
			door = doors.get(0);
		}else {
			return "no door";
			}
		String serverUrl="";//服务器地址
		if(door!=null && door.getCommonServer()!=null){
			serverUrl="http://"+door.getCommonServer().getServerIp()+":8081/services/ofthings/acldoor.asp?cmd=regcard&roomnumber="+roomId;
		}	
		URL url=new URL(serverUrl);
		System.out.println(serverUrl);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); 
		// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false; 
		urlConn.setDoOutput(true);
		// 设置是否从httpUrlConnection读入，默认情况下是true;
		urlConn.setDoInput(true);
		// Post 请求不能使用缓存
		urlConn.setUseCaches(false);
		// 设定传送的内容类型是可序列化的java对象  
		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
		urlConn.setRequestProperty("Content-type","application/x-java-serialized-object");
		// 设定请求的方法为"POST"，默认是GET
		urlConn.setRequestMethod("POST"); 
		// 连接，上面对urlConn的所有配置必须要在connect之前完成
		try{
			urlConn.connect();
			}catch(IOException e){
				return "error";
			}
				
		// 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，  
		// 所以在开发中不调用上述的connect()也可以)。
		OutputStream outStrm = urlConn.getOutputStream();
				
		// 调用HttpURLConnection连接对象的getInputStream()函数,  
		// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。  
		InputStream inStrm = urlConn.getInputStream(); // <===注意，实际发送请求的代码段就在这里
			    
		InputStreamReader  inStream = new InputStreamReader(inStrm,"UTF-8");
		String inputline="";
		String info="";//返回的参数
		BufferedReader buffer=new BufferedReader(inStream);
			    
		while((inputline=buffer.readLine())!=null){
			info+=inputline;
			}
		//System.out.println("返回的参数为："+info);
		//设置超时时间  
		HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
		urlCon.setConnectTimeout(30000);  
		urlCon.setReadTimeout(30000);  
		if(info.contains("true")){
			return "sucess";
		}else{
			return "error";
		}
	}
	
}

