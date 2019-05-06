package net.gvsun.lims.api.wxAPI;

import net.zjcclims.dao.LabRoomAgentDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.RemoteOpenDoorDAO;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomAgent;
import net.zjcclims.domain.RemoteOpenDoor;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomLendingService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller("wxAPIController")
@RequestMapping("/wxAPI")
public class wxAPIController {

    @Autowired
    private LabRoomLendingService labRoomLendingService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private ShareService shareService;

    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    private RemoteOpenDoorDAO remoteOpenDoorDAO;
    @Autowired
    private PConfig pConfig;

    /**
     * Description 实验室预约判冲接口
     * @param labRoomId 实验室id
     * @param lendingTimeStr 借用日期字符串（例如：19700101 = 1970年1月1日）
     * @param startTimeStr 开始时间字符串（例如：00:00 = 零时零分）
     * @param endTimeStr 结束时间字符串（例如：00:00 = 零时零分）
     * @return 是否可预约
     * @author 黄保钱 2019-1-23
     */
    @RequestMapping("/judgeConflictForLab")
    @ResponseBody
    public boolean judgeConflictForLab(@RequestParam Integer labRoomId, @RequestParam String lendingTimeStr,
                                       @RequestParam String startTimeStr, @RequestParam String endTimeStr){
        boolean flag;
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        Calendar lendingTime = Calendar.getInstance();
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        try {
            lendingTime.setTime(sdfDate.parse(lendingTimeStr));
            startTime.setTime(sdfTime.parse(startTimeStr));
            endTime.setTime(sdfTime.parse(endTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        LabRoom l = labRoomDAO.findLabRoomById(labRoomId);
        String acno = l.getLabCenter().getSchoolAcademy().getAcademyNumber();
        flag = labRoomLendingService.findLendingEnableOrNot(labRoomId, lendingTime, startTime, endTime, acno) == 1;
        return flag;
    }

    @RequestMapping(value="/openDoorPython", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public @ResponseBody String openDoorPython(@RequestParam Integer agentId, HttpServletResponse response) throws IOException, InterruptedException {

        // 门禁设备
        LabRoomAgent a = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        String ip=a.getHardwareIp();
        String sn=a.getManufactor();

        if(pConfig.PROJECT_NAME.equals("zjcclims")) {
            User user = shareService.getUser();//当前登陆人
            List<LabRoomAgent> agentList = labRoomService.findLabRoomAgentAccessByRoomId(a.getLabRoom().getId());// 根据roomId查询该实验室的门禁
            LabRoom labRoom = labRoomDAO.findLabRoomById(a.getLabRoom().getId());
            Calendar time = Calendar.getInstance();
            for (LabRoomAgent labRoomAgent : agentList) {
                if (labRoomAgent.getCDictionary().getId() == 548) {
                    RemoteOpenDoor rod = new RemoteOpenDoor();
                    rod.setControllerId(labRoomAgent.getHardwareIp());
                    rod.setDoorNo(labRoomAgent.getManufactor());
                    rod.setCreaterName(user.getCname());
                    rod.setCreaterUsername(user.getUsername());
                    rod.setDeviceType(0);
                    rod.setLabRoomId(labRoom.getId());
                    rod.setLabRoomName(labRoom.getLabRoomName());
                    rod.setStatus(0);
                    rod.setRemoteAction(1);
                    rod.setCreateTime(time);
                    remoteOpenDoorDAO.store(rod);
                }
            }
            System.out.println("run python py");
            Process proc = Runtime.getRuntime().exec("python  /opt/python/lims2opendoor.py");
            proc.waitFor();

            return "sucess";
        }else {
            String port = "";// 端口
            String ServIP = "";// 主机
            String getURL = "";
            if (a.getCommonServer() != null) {
                String cmd = "";
                // 现老版本的命令不同  老的是open  新的是opendoor
                if (a.getCommonServer().getServerSn() != null
                        && a.getCommonServer().getServerSn().equals("8080")) {//  8080是IIS服务  8081是python服务
                    cmd = "open";
                } else {
                    cmd = "opendoor";
                }

                String doorIndex = "01";
                if (a.getDoorindex() != null) {
                    doorIndex = "0" + a.getDoorindex().toString();
                }
                // 主机和端口
                ServIP = a.getCommonServer().getServerIp();
                if (a.getCommonServer().getServerSn() != null && !a.getCommonServer().getServerSn().equals("")) {
                    port = a.getCommonServer().getServerSn();
                } else {//端口为空
                    port = "80";
                }

                String sysName = pConfig.PROJECT_NAME;
                if (sysName.contains("jitsoft")) {
                    getURL = "/services/ofthings/acldoor.asp?cmd=" + cmd + "&ip=" + ip + "&sn=" + sn + "&doorIndex=" + doorIndex;
                }else if(sysName.contains("fdulims")) {
                    getURL = "/services/ofthings/acldoor.asp?cmd=open&ip=" + ip + "&doorindex=" + doorIndex;
                } else {
                    getURL = "/services/ofthings/acldoor.asp?cmd=" + cmd + "&ip=" + ip + "&sn=" + sn;
                }
            }
            SocketAddress addr = new InetSocketAddress(ServIP, Integer.valueOf(port).intValue());
            //1、创建一个服务器端Socket
            Socket sock = new Socket();
            sock.connect(addr);
            StringBuffer headers = new StringBuffer("GET " + getURL + " HTTP/1.1\r\n");
            // 以下为请求头
            headers.append("Host: " + ServIP + ":" + port + "\r\n");
            headers.append("\r\n");
            //2、获取输出流，向服务器端发送信息
            OutputStream out = sock.getOutputStream();
            out.write(headers.toString().getBytes());
            //3、获取输入流，并读取服务器端的响应信息
            InputStream is = sock.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            String[] result;
            int len = -1;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            sock.close();
            result = new String(baos.toByteArray()).split("\r\n");
            int length = result.length;
            if (result[length - 1].contains("true")) {
                return "sucess";
            } else {
                return result[length - 1];
            }
        }
    }

    /**
     * Description 电源控制器远程开关
     * @param flag 0 关电源，1 开电源
     * @param insUid 硬件id
     * @return
     * @throws IOException
     * @author 陈乐为 2018-9-10
     */
    @ResponseBody
    @RequestMapping("/openAgent")
    public String openAgent(@RequestParam Integer flag, String insUid) throws IOException {
        return labRoomService.syncSmartAgent(flag,insUid);
    }

}
