package net.zjcclims.web.cms.lab;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.CDictionaryDAO;
import net.zjcclims.dao.CommonServerDAO;
import net.zjcclims.dao.LabRoomAdminDAO;
import net.zjcclims.dao.LabRoomAgentDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.dao.LabRoomFurnitureDAO;
import net.zjcclims.dao.MessageDAO;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.dao.TimetableSelfCourseDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.LabRoomAgent;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.dictionary.CDictionaryService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.lab.LabReservationService;
import net.zjcclims.service.lab.LabRoomFurnitureService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.ByteArrayOutputStream;


@Controller("CmsLabRoomController")
@SessionAttributes("selected_academy")
@RequestMapping("/cms/labRoom")
public class CmsLabRoomController<JsonResult> {

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
        binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
    }

    @Autowired
    private ShareService shareService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    CDictionaryService cDictionaryService;
    @Autowired
    private LabCenterService labCenterService;
    @Autowired
    private LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private CommonServerDAO commonServerDAO;
    @Autowired
    private OperationItemDAO operationItemDAO;
    @Autowired
    private CDictionaryDAO cDictionaryDAO;
    @Autowired
    private LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LabRoomAdminDAO labRoomAdminDAO;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private SchoolDeviceService schoolDeviceService;
    @Autowired
    private LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabRoomFurnitureDAO labRoomFurnitureDAO;
    @Autowired
    private LabRoomFurnitureService labRoomFurnitureService;
    @Autowired
    private LabReservationService labReservationService;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private MessageDAO messageDAO;

  /****************************************************************************
     * 功能：socket方法远程开门
     * 作者：叶晨凯
     * 时间：2018-08-13
     ****************************************************************************/
    @RequestMapping("/openDoor")
    public @ResponseBody String openDoor(@RequestParam Integer roomId, HttpServletResponse response) throws IOException {

        //根据roomId查询该实验室的门禁
        List<LabRoomAgent> agentList=labRoomService.findLabRoomAgentAccessByRoomId(roomId);
        LabRoomAgent a=new LabRoomAgent();
        if(agentList.size()>0){
            a=agentList.get(0);
        }
        String ip=a.getHardwareIp();
        String sn=a.getManufactor();

        String serverUrl="";//http://192.168.5.201:8082/services/ofthings/acldoor.asp?cmd=open&ip="+ip;//服务器地址
        String port="";
        String ServIP="";
        String getURL="";
        if(a.getCommonServer()!=null){
            //格式------http://192.168.10.252:8080/services/ofthings/acldoor.asp?cmd=open&ip=
            ServIP=a.getCommonServer().getServerIp();
            getURL="/services/ofthings/acldoor.asp?cmd=open&ip="+ip+"&sn="+sn/**+"&doorindex="+doorindex**/;

            if(a.getCommonServer().getServerSn()!=null&&!a.getCommonServer().getServerSn().equals("")){
                port=a.getCommonServer().getServerSn();
//				serverUrl="http://"+a.getCommonServer().getServerIp()+":"+a.getCommonServer().getServerSn()+"/services/ofthings/acldoor.asp?cmd=opendoor&ip="+ip+"&sn="+sn;
            }else{//端口为空
                port="80";
//				serverUrl="http://"+a.getCommonServer().getServerIp()+"/services/ofthings/acldoor.asp?cmd=open&ip="+ip+"&sn="+sn;
            }
        }
        SocketAddress addr = new InetSocketAddress(ServIP,Integer.valueOf(port).intValue());
        Socket sock = new Socket();
        sock.connect(addr);
//		String[] headers = {"GET "+getURL+" HTTP/1.1\r\n","Host: "+ServIP+":"+port+"\r\n","\r\n"};
        StringBuffer headers = new StringBuffer("GET "+getURL+" HTTP/1.1\r\n");
        // 以下为请求头
        headers.append("Host: "+ServIP+":"+port+"\r\n");
        headers.append("\r\n");
        OutputStream out = sock.getOutputStream();
        out.write(headers.toString().getBytes());
//		out.flush();
        InputStream is = sock.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        String[] result;
        int len = -1;
        while ((len = is.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }
        //System.out.println(baos/****new String(baos.toByteArray())****/);
        sock.close();
        result=new String(baos.toByteArray()).split("\r\n");
//		System.out.println("学院物联服务器的地址：" + serverUrl);
        int length=result.length;
        if(result[length-1].contains("true")){
            return "sucess";
        }else{
            return result[length-1];
        }

    }

}
