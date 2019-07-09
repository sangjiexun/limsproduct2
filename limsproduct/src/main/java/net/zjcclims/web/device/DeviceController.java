/****************************************************************************
 * 命名规范：
 * 本控制层调整必须是按照如下的路径调整：@RequestMapping("/device/sample")，全部小写。
 * 列表信息listxxxx，獲取信息：getxxxx 编辑信息：editxxxx 删除信息：detelexxxx 新增信息 newxxxx
 * 导出信息exportxxxx 保存信息：savexxxx  
 ****************************************************************************/

package net.zjcclims.web.device;

import net.gvsun.lims.dto.common.PConfigDTO;
import net.gvsun.lims.service.auditServer.AuditService;
import net.gvsun.lims.service.timetable.LabDeviceReservationService;
import net.luxunsh.util.EmptyUtil;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.CommonVideoService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceRepairService;
import net.zjcclims.service.device.LabRoomDeviceReservationService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.ShareDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.BindException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/****************************************************************************
 * @功能： 设备管理模块
 *
 * @作者：魏诚 @时间：2014-07-14
 ****************************************************************************/
@Controller("DeviceController")
@SessionAttributes({"selected_academy", "is_reservation"})
public class DeviceController<JsonResult> {
    // 读取属性文件中specialAcademy对应的值（此方法需要在web-content.xml中增加配置）
    @Value("${specialAcademy}")
    private String specialAcademy;
    @Autowired
    LabRoomDeviceService labRoomDeviceService;
    @Autowired
    LabRoomDAO labRoomDAO;
    @Autowired
    ShareService shareService;
    @Autowired
    SchoolDeviceDAO schoolDeviceDAO;
    @Autowired
    CDictionaryDAO cDictionaryDAO;
    @Autowired
    LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
    @Autowired
    SystemTimeDAO systemTimeDAO;
    @Autowired
    LabRoomDeviceTrainingDAO labRoomDeviceTrainingDAO;
    @Autowired
    LabRoomDeviceTrainingPeopleDAO labRoomDeviceTrainingPeopleDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CommonDocumentService commonDocumentService;
    @Autowired
    ShareDataService shareDataService;
    @Autowired
    CommonVideoService commonVideoService;
    @Autowired
    LabRoomService labRoomService;
    @Autowired
    LabRoomDeviceLendingDAO labRoomDeviceLendingDAO;
    @Autowired
    LabRoomDeviceLendingResultDAO labRoomDeviceLendingResultDAO;
    @Autowired
    LabRoomDeviceRepairDAO labRoomDeviceRepairDAO;
    @Autowired
    LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    LabCenterDAO labCenterDAO;
    @Autowired
    LabRoomAdminDAO labRoomAdminDAO;
    @Autowired
    AuthorityDAO authorityDAO;
    @Autowired
    CStaticValueDAO cStaticValueDAO;
    @Autowired
    LabRoomLimitTimeDAO labRoomLimitTimeDAO;
    @Autowired
    SchoolWeekDAO schoolWeekDAO;
    @Autowired
    LabRoomDevicePermitUsersDAO labRoomDevicePermitUsersDAO;
    @Autowired
    SchoolDeviceService schoolDeviceService;
    @Autowired
    LabRoomDeviceReservationService labRoomDeviceReservationService;
    @Autowired
    LabRoomDeviceRepairService labRoomDeviceRepairService;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    ResearchProjectDAO researchProjectDAO;
    @Autowired
    CommonServerDAO commonServerDAO;
    @Autowired
    LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    private AuditService auditService;

    @Autowired
    private LabDeviceReservationService labDeviceReservationService;

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
        // static
        // property
        // editors.
        binder.registerCustomEditor(Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
    }

    /****************************************************************************
     * @功能：删除实验分室设备
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deleteLabRoomDevice")
    public ModelAndView deleteLabRoomDevice(@RequestParam Integer id, Integer flg) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备
        LabRoomDevice d = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        // 设备所属实验室
        LabRoom room = d.getLabRoom();
        labRoomDeviceService.deleteLabRoomDevice(d);
        if (flg == 1) {
            mav.setViewName("redirect:/device/listLabRoomDeviceNew?page=1&roomId=" + room.getId());
        } else {
            mav.setViewName("redirect:/appointment/getLabRoom?id=" + room.getId());
        }
        return mav;
    }

    /****************************************************************************
     * @功能：设置实验室设备的管理员，实验室预约的时候的设备管理员审核
     * @作者：贺子龙 @时间：2015-09-22 14:14:17
     ****************************************************************************/
    @RequestMapping("/device/setLabRoomDeviceManager")
    public ModelAndView setLabRoomDeviceManager(@RequestParam Integer id, String[] array) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备
        LabRoomDevice d = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        // 设备所属实验室
        LabRoom room = d.getLabRoom();
        for (String i : array) {
            User u = userDAO.findUserByPrimaryKey(i);
            d.setUser(u);
            labRoomDeviceDAO.store(d);
            // 给用户赋予权限
            Authority authority = authorityDAO.findAuthorityByPrimaryKey(10);// 设备管理员
            Set<Authority> ahths = u.getAuthorities();
            ahths.add(authority);
            u.setAuthorities(ahths);
            userDAO.store(u);
        }
        mav.setViewName("redirect:/appointment/getLabRoom?id=" + room.getId());
        return mav;
    }

//	/****************************************************************************
//	 * @功能：给设备上传图片
//	 * @作者：李小龙
//	 ****************************************************************************/
//	@RequestMapping("/device/deviceImageUpload")
//	public @ResponseBody String deviceImageUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {
//		labRoomDeviceService.deviceImageUpload(request, response, id, 1);
//		return "redirect:/device/deviceImage?deviceId=" + id;
//	}

    /****************************************************************************
     * @throws UnsupportedEncodingException
     * @功能：原生上传
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/device/deviceImageUpload")
    public ModelAndView deviceImageUpload(HttpServletRequest request, HttpServletResponse response, Integer id,
                                          Integer labRoomId, Integer deviceNumber, String deviceName, Integer username,
                                          Integer page, Integer schoolDevice_allowAppointment) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        labRoomDeviceService.deviceImageUpload(request, response, id, 4);
        mav.setViewName("redirect:/device/editDeviceImageRest/" + labRoomId + "/" + deviceNumber + "/" + URLEncoder.encode(deviceName, "utf-8") + "/" + username + "/" + page + "/" + id + "/" + schoolDevice_allowAppointment);
        return mav;
    }
//	/****************************************************************************
//	 * @功能：给设备上传视频
//	 * @作者：李小龙
//	 ****************************************************************************/
//	@RequestMapping("/device/deviceVideoUpload")
//	public @ResponseBody String deviceVideoUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {
//		labRoomDeviceService.deviceVideoUpload(request, response, id);
//		return "ok";
//	}

    /****************************************************************************
     * @throws UnsupportedEncodingException
     * @功能：原生上传
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/device/deviceVideoUpload")
    public ModelAndView deviceVideoUpload(HttpServletRequest request, HttpServletResponse response, Integer id,
                                          Integer labRoomId, Integer deviceNumber, String deviceName, Integer username,
                                          Integer page, Integer schoolDevice_allowAppointment) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        labRoomDeviceService.deviceVideoUpload(request, response, id);
        mav.setViewName("redirect:/device/editDeviceVideoRest/" + labRoomId + "/" + deviceNumber + "/" + URLEncoder.encode(deviceName, "utf-8") + "/" + username + "/" + page + "/" + id + "/" + schoolDevice_allowAppointment);
        return mav;
    }

    /****************************************************************************
     * @功能：删除设备图片
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deleteDeviceDocument")
    public ModelAndView deleteDeviceDocument(@RequestParam Integer id, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的设备图片
        CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
        // 图片所属的设备
        LabRoomDevice device = doc.getLabRoomDevice();
        int idkey = device.getId();
        commonDocumentService.deleteCommonDocument(doc);
        // 删除服务器上的文件
        int type = doc.getType();
        // 所属学院
        SchoolAcademy schoolAcademy = shareService.findSchoolAcademyByPrimaryKey(acno);
        String academy = "";
        if(schoolAcademy!=null && schoolAcademy.getAcademyNumber()!=null) {
            academy = schoolAcademy.getAcademyNumber();
        }
        // 根据学院编号判断是否为配置文件配置的特殊学院（化工学院）
        boolean flag = specialAcademy.contains(academy);
        if (flag) {
            if (type == 1) {// 图片
                mav.setViewName("redirect:/device/deviceImage?deviceId=" + idkey);
            } else {// 文档
                mav.setViewName("redirect:/device/deviceDocument?deviceId=" + idkey);
            }

        } else {
            mav.setViewName("redirect:/device/editDeviceReservationInfo?id=" + idkey);
        }
        return mav;
    }

    /****************************************************************************
     * @功能：删除设备视频
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deleteLabRoomVideo")
    public ModelAndView deleteLabRoomVideo(@RequestParam Integer id, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的设备视频
        CommonVideo video = commonVideoService.findVideoByPrimaryKey(id);
        // 图片所属的设备
        LabRoomDevice device = video.getLabRoomDevice();
        int idkey = device.getId();
        commonVideoService.deleteCommonVideo(video);
        // 删除服务器上的文件

        // 根据学院编号判断是否为配置文件配置的特殊学院（化工学院）
        boolean flag = specialAcademy.contains(acno);
        if (flag) {
            mav.setViewName("redirect:/device/deviceVideo?deviceId=" + idkey);
        } else {
            mav.setViewName("redirect:/device/editDeviceReservationInfo?id=" + idkey);
        }

        return mav;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理--所有设备
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/listLabRoomDevice")
    public ModelAndView listLabRoomDevice(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer page, Integer isReservation, Integer isOrder, @ModelAttribute("selected_academy") String acno,HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        // 实验室
        List<LabRoom> rooms = labRoomService.findLabRoomWithDevices(isReservation,acno);
        mav.addObject("rooms", rooms);
        int pageSize = 12;
        int pageSizeLabroom = 12;
        // 实验室总记录数
        int totalRecordsLabroom =rooms.size();
//        int totalRecordsLabroom = labRoomService.findLabRoomWithDevices(labRoomDevice, 0, 0, isReservation,acno, request).size();
        // 实验室分页
        List<LabRoom> roomsList = labRoomService.findLabRoomWithDevices(labRoomDevice, page, pageSizeLabroom, isReservation,acno, request);
        mav.addObject("roomsList", roomsList);

        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        //设备名称
        List<LabRoomDevice>labRoomDevices =labRoomDeviceService.getLabRoomDevice(acno);
        mav.addObject("labRoomDevices",labRoomDevices);
        // 查询出来的总记录条数
       /* int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", isReservation);*/
//        int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, 1, -1, isReservation).size();
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, 1, -1, isReservation);
        mav.addObject("listLabRoomDeviceAll", listLabRoomDeviceAll);
        // 设备总数
        int totalRecords = listLabRoomDeviceAll.size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        Map<String, Integer> pageModelLabroom = shareService.getPage(page, pageSizeLabroom, totalRecordsLabroom);// listLabRoom.jsp页面分页

        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, page, pageSize, isReservation);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid(acno));
        mav.addObject("pageModel", pageModel);
        mav.addObject("pageModelLabroom", pageModelLabroom);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("totalRecordsLabroom", totalRecordsLabroom);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("pageSizeLabroom", pageSizeLabroom);
        // 获取当前用户传递到前台
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 加入全局变量来表征当前老师选择的是“设置”还是“预约”
        if (isReservation != null && isReservation.equals(1)) {
            mav.addObject("is_reservation", 1);
        } else {
            mav.addObject("is_reservation", 0);
        }
        //判断选择的是“大型仪器设备预约”还是“设备管理”
        mav.addObject("isOrder", isOrder);
        if (isOrder != null && isOrder.equals(1)) {
            mav.setViewName("/device/specialAcademy/listLabRoomDevices.jsp");
        } else {
            mav.setViewName("/device/specialAcademy/listLabRoom.jsp");
        }
        //mav.setViewName("/device/specialAcademy/listLabRoomDevices.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理--所有设备--微服务设备预约所有设备
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/apilistLabRoomDevice")
    public ModelAndView apilistLabRoomDevice(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer page, Integer isReservation, Integer isOrder, @ModelAttribute("selected_academy") String acno,HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        // 实验室
        List<LabRoom> rooms = labRoomService.findLabRoomWithDevices(isReservation);
        mav.addObject("rooms", rooms);
        int pageSize = 12;
        int pageSizeLabroom = 12;
        int totalRecordsLabroom = labRoomService.findLabRoomWithDevices(labRoomDevice, 0, 0, isReservation,acno, request).size();
        // 实验室分页
        List<LabRoom> roomsList = labRoomService.findLabRoomWithDevices(labRoomDevice, page, pageSizeLabroom, isReservation,acno, request);
        mav.addObject("roomsList", roomsList);

        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        //设备名称
        List<LabRoomDevice>labRoomDevices =labRoomDeviceService.getLabRoomDevice(acno);
        mav.addObject("labRoomDevices",labRoomDevices);
        // 查询出来的总记录条数
        /* int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", isReservation);*/
        int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, 1, -1, isReservation).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        Map<String, Integer> pageModelLabroom = shareService.getPage(page, pageSizeLabroom, totalRecordsLabroom);// listLabRoom.jsp页面分页

        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, page, pageSize, isReservation);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid(acno));
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, 1, -1, isReservation);
        mav.addObject("listLabRoomDeviceAll", listLabRoomDeviceAll);
        mav.addObject("pageModel", pageModel);
        mav.addObject("pageModelLabroom", pageModelLabroom);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("totalRecordsLabroom", totalRecordsLabroom);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("pageSizeLabroom", pageSizeLabroom);
        // 获取当前用户传递到前台
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 加入全局变量来表征当前老师选择的是“设置”还是“预约”
        if (isReservation != null && isReservation.equals(1)) {
            mav.addObject("is_reservation", 1);
        } else {
            mav.addObject("is_reservation", 0);
        }
        //判断选择的是“大型仪器设备预约”还是“设备管理”
        mav.addObject("isOrder", isOrder);
        if (isOrder != null && isOrder.equals(1)) {
            mav.setViewName("/device/specialAcademy/apilistLabRoomDevices.jsp");
        } else {
            mav.setViewName("/device/specialAcademy/listLabRoom.jsp");
        }
        //mav.setViewName("/device/specialAcademy/listLabRoomDevices.jsp");
        return mav;
    }
    /************************************************************
     * @功能：实验室与设备联动查询
     * @作者：廖文辉
     * @时间：2018.9.5
     ************************************************************/
    @RequestMapping("findLabRoomDeviceByLabRoom")
    @ResponseBody
    public Map<String,String> findLabRoomDeviceByLabRoom(@RequestParam String  labRoom,HttpServletRequest request,String deviceNumber){
        Map<String,String> map=new HashMap<String, String>();
        String labRoomDeviceValue=labRoomDeviceService.findLabRoomDevicesByLabRoom(labRoom,request,deviceNumber);
        map.put("labRoomDevice",labRoomDeviceValue);
        return map;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理--所有设备
     * @作者：李小龙
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/device/listLabRoomDeviceForLabroom")
    public ModelAndView listLabRoomDeviceForLabroom(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer page, @ModelAttribute("selected_academy") String acno, @ModelAttribute("is_reservation") Integer isReservation) {

        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int roomId = -1;
        if (labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getId() != null) {
            roomId = labRoomDevice.getLabRoom().getId();
        }
        mav.addObject("roomId", roomId);
        // 中心所属学院
        //String academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();

        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        // 实验室
        List<LabRoom> rooms = labRoomService.findLabRoomWithDevices(isReservation);
        int totalRecordsLabroom = labRoomService.findAllLabRoom(labRoomDevice,acno, isReservation);
        mav.addObject("rooms", rooms);
        int pageSize = 10;
        int pageSizeLabroom = 12;
        // 实验室分页
        List<LabRoom> roomsList = labRoomService.findLabRoomByLabCenterid(labRoomDevice,acno, page, pageSizeLabroom);
        mav.addObject("roomsList", roomsList);

        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        // 查询出来的总记录条数
        int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", isReservation);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        Map<String, Integer> pageModelLabroom = shareService.getPage(page, pageSizeLabroom, totalRecordsLabroom);// listLabRoom.jsp页面分页
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid(acno));
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, page, pageSize, 1);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno, 1, -1, 1);
        mav.addObject("listLabRoomDeviceAll", listLabRoomDeviceAll);
        mav.addObject("pageModel", pageModel);
        mav.addObject("pageModelLabroom", pageModelLabroom);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("totalRecordsLabroom", totalRecordsLabroom);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("pageSizeLabroom", pageSizeLabroom);
        mav.addObject("tag", 1);// 后台区分查询取消的跳转页面
        // 当前用户
        User user = shareService.getUser();
        mav.addObject("user", user);
        mav.setViewName("/device/specialAcademy/specialAcademyForLabroom.jsp");

        return mav;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理--所有设备
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/listLabRoomDeviceNew")
    public ModelAndView listLabRoomDeviceNew(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam int roomId, Integer page, @ModelAttribute("selected_academy") String acno, @ModelAttribute("is_reservation") Integer isReservtaion) {
        // 新建ModelAndView对象；
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验室
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(roomId);
        mav.addObject("labRoom", labRoom);
        if(labRoom != null) {
            labRoomDevice.setLabRoom(labRoom);
        }
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        // 实验室
        List<LabRoom> rooms = labRoomService.findLabRoomWithDevices(isReservtaion,acno);
        mav.addObject("rooms", rooms);
        int pageSize = 10;
        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        // 查询出来的总记录条数
//        int totalRecords = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", roomId);
        // 分页信息
//        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid(acno));
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", page, pageSize, roomId);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", 1, -1, roomId);
        int totalRecords = listLabRoomDeviceAll.size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        mav.addObject("listLabRoomDeviceAll", listLabRoomDeviceAll);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("roomId", roomId);
        mav.addObject("tag", 0);// 后台区分查询取消的跳转页面
        // 当前用户
        User user = shareService.getUser();
        mav.addObject("user", user);

        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/device/specialAcademy/specialAcademyForLabroom.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：实验室基础信息管理---设备管理--新建
     * @作者：张德冰
     * @时间：2018.03.13
     ****************************************************************************/
    @RequestMapping("/device/newLabRoomDevice")
    public ModelAndView newSchoolDevice(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam int roomId, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验室
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(roomId);
        mav.addObject("labRoom", labRoom);
        List<LabRoom> labRooms = labRoomService.findLabRoomWithDevices(1);
        mav.addObject("labRooms", labRooms);
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid(acno));
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, acno);
        mav.addObject("listLabRoomDeviceAll", listLabRoomDeviceAll);
        mav.setViewName("/device/specialAcademy/editLabRoomDevice.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：实验室基础信息管理---设备管理--保存
     * @作者：张德冰
     * @时间：2018.03.13
     ****************************************************************************/
    @RequestMapping("/device/savelabRoomDevice")
    public ModelAndView savelabRoomDevice(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice l = new LabRoomDevice();
        if (id != null) {
            l = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(id);
        }
        l.setIndicators(labRoomDevice.getIndicators());
        if (labRoomDevice.getLabRoom() != null && labRoomDevice.getLabRoom().getId() != null) {
            l.setLabRoom(labRoomDevice.getLabRoom());
        }
        if (labRoomDevice.getSchoolDevice() != null && labRoomDevice.getSchoolDevice().getDeviceNumber() != null) {
            l.setSchoolDevice(labRoomDevice.getSchoolDevice());
        }
        if (labRoomDevice.getUser() != null && labRoomDevice.getUser().getUsername() != null) {
            l.setUser(labRoomDevice.getUser());
        }
        if (labRoomDevice.getCDictionaryByDeviceStatus() != null && labRoomDevice.getCDictionaryByDeviceStatus().getId() != null) {
            l.setCDictionaryByDeviceStatus(labRoomDevice.getCDictionaryByDeviceStatus());
        }
        labRoomDeviceDAO.store(l);
        int roomId=labRoomDevice.getLabRoom().getId();
        mav.setViewName("redirect:/device/listLabRoomDeviceNew?page=1&roomId=" + roomId);
        return mav;
    }

    /************************************************************
     * @throws Exception
     * @功能：实验室基础信息管理---设备管理--导出设备列表
     * @作者：张德冰
     * @时间：2018.03.13
     ************************************************************/
    @RequestMapping("/device/exportLabRoomDevice")
    public void exportListStudentHouse(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam int roomId, Integer page, @ModelAttribute("selected_academy") String acno,
                                       @ModelAttribute("is_reservation") Integer isReservtaion, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<LabRoomDevice> labRoomDevices = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, acno, page, 10, roomId);
        labRoomDeviceService.exportLabRoomDevice(labRoomDevices, request, response);
    }

    /************************************************************
     * @功能：实验室基础信息管理---设备管理--导入设备列表
     * @作者：张德冰
     * @时间：2018.03.15
     ************************************************************/
    @RequestMapping("/device/importLabRoomDevice")
    public @ResponseBody String[] importLabRoomDevice(HttpServletRequest request, @RequestParam int roomId) {
        String fileName = shareService.getUpdateFilePath(request);
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
        String filePath = logoRealPathDir + fileName;
        System.out.println(filePath);
        String[] result = {"您上传的文件类型不正确，请上传正确的office excel表格文件"};
        if (filePath.endsWith("xls") || filePath.endsWith("xlsx")) {
            result = labRoomDeviceService.importLabRoomDevice(filePath);
        }
        return result;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理--关联设备
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/listInnerSameDevice")
    public ModelAndView listInnerSameDevice(@ModelAttribute("selected_academy") String acno, @RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        String deviceNumber = device.getSchoolDevice().getDeviceNumber();
        List<SchoolDevice> schoolDevices = schoolDeviceService.findInnerSameDevice(deviceNumber);
        mav.addObject("schoolDevices", schoolDevices);
        mav.addObject("device", device);
        mav.addObject("labRooms", labRoomService.findLabRoomByLabCenterid(acno, 1));  //实验室数据
        // 添加设备所需
        mav.addObject("schoolDevice", new SchoolDevice()); // 实验室设备

        mav.setViewName("/device/specialAcademy/listInnerSameDevice.jsp");
        return mav;
    }

    /****************************************************************************
     * @throws ParseException
     * @功能：安全准入验证
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/securityAccess")
    public @ResponseBody
    String securityAccess(@RequestParam Integer id) throws ParseException {
        // 更新预约结果
        labRoomDeviceService.updateReservationResult(id);
        User user = shareService.getUser();
        String data = labRoomDeviceService.securityAccess(user.getUsername(), id);
        return htmlEncode(data);
    }

    /****************************************************************************
     * @功能：设备预约
     * @作者：李小龙
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/device/reservationDevice")
    public ModelAndView reservationLabRoomDevice(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);

        CStaticValue cStaticValue = new CStaticValue();
        // 获取类型为设备预约审核有效@时间device_valid_time的字典，并取出第一个值
        for (CStaticValue cStaticValueTmp : cStaticValueDAO.findCStaticValueByCode("device_valid_time")) {
            if (cStaticValueTmp.getId() != null) {
                cStaticValue = cStaticValueTmp;
            }
        }

        // 赋予变量cStaticValue到前端
        mav.addObject("cStaticValue", cStaticValue);
        mav.addObject("id", id);
        // 获取当前@时间
        Calendar now = Calendar.getInstance();
        if (cStaticValue.getStaticValue() != null) {// 判空，贺子龙 2015-10-20
            if (device.getCDictionaryByIsAudit() != null && "1".equals(device.getCDictionaryByIsAudit().getCNumber())) {// 如果不需要审核，则不用加一天或两天时间
                // 贺子龙
                // 2015-11-04
                String[] abc = cStaticValue.getStaticValue().split(":");
                if (now.get(Calendar.HOUR_OF_DAY) > Integer.parseInt(abc[0])) {
                    now.add(Calendar.DAY_OF_YEAR, 2);
                } else if ((now.get(Calendar.HOUR_OF_DAY) == Integer.parseInt(abc[0]))) {
                    if ((now.get(Calendar.MINUTE) > Integer.parseInt(abc[1]))) {
                        now.add(Calendar.DAY_OF_YEAR, 2);
                    } else {
                        now.add(Calendar.DAY_OF_YEAR, 1);
                    }
                } else {
                    now.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
            /*
             * else {//但是，不能预约已经过去的时间 now.add(Calendar.HOUR_OF_DAY, 1);//加一个小时
             *
             * }
             */

        }

        // 获取当前@时间的年份映射给year
        mav.addObject("year", now.get(Calendar.YEAR));
        // 获取当前@时间的月份映射给month
        mav.addObject("month", now.get(Calendar.MONTH));
        // 获取当前@时间的天数映射给day；
        mav.addObject("day", now.get(Calendar.DAY_OF_MONTH));

        // 获取当前@时间的天数映射给小时；
        mav.addObject("hour", now.get(Calendar.HOUR_OF_DAY));

        // 获取当前@时间的天数映射给分钟；
        mav.addObject("minute", now.get(Calendar.MINUTE));

        // 获取当前@时间的天数映射给分钟；
        mav.addObject("second", now.get(Calendar.SECOND));

        if (device.getSchoolDevice() != null) {
            mav.addObject("schoolDeviceName", device.getSchoolDevice().getDeviceName());
        }

        // 设备所属实验室
        LabRoom room = device.getLabRoom();
        // 根据实验室查询实验室的排课
        List<TimetableLabRelated> relateds = labRoomDeviceService.findTimetableLabRelatedByRoomId(room.getId());

        // 导师集合
        User user = shareService.getUser();
        List<User> ts = shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber());
        List<String> teachers = new ArrayList<String>();
        for (User u : ts) {
            /*
             * if(u.getSchoolAcademy().getAcademyNumber().equals("0201")&&
             * u.getUsername().toString().indexOf("1005")==-1){//
             * 目前纺织学院正式帐号都是10055开头的，所以不包含10055的都要排除掉 continue; }
             */
            // 预约插件只支持纯数字，这边过滤掉username包含字母的数据
            if (Pattern.compile("(?i)[a-z]").matcher(u.getUsername()).find() == false) {
                teachers.add("{key" + ":" + u.getUsername().trim() + ",label:'" + u.getCname().trim() + u.getUsername() + "'}");
            }

        }
        mav.addObject("teachers", teachers);
        // // 申请性质集合
        // Set<CReservationProperty> ps =
        // cReservationPropertyDAO.findAllCReservationPropertys();
        // List<String> propertys = new ArrayList<String>();
        // for (CReservationProperty p : ps) {
        // propertys.add("{key" + ":" + p.getId() + ",label:'" + p.getName() +
        // "'}");
        // }
        // mav.addObject("propertys", propertys);
        mav.setViewName("/device/lab_room_device/reservationDevice.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：Ajax保存设备预约并返回是否保存成功 @作者：李小龙
     ****************************************************************************/
    @Transactional
    @RequestMapping("/device/saveDeviceReservation")
    public @ResponseBody
    String saveDeviceReservation(@RequestParam Integer equinemtid,
                                 String startDate, String endDate,
                                 String description, String phone,
                                 String teacher, Integer property,
                                 String research, HttpServletResponse response) throws Exception {

        // 保存设备预约前检查是否禁用时间
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(equinemtid);
        SchoolDevice schoolDevice = device.getSchoolDevice();
        User user = shareService.getUser();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(startDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        Date date2 = sdf.parse(endDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        // 获取当前学期
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        // 实验室禁用时间段列表
        List<LabRoomLimitTime> labRoomLimitTimes = labRoomLimitTimeDAO.executeQuery("select c from LabRoomLimitTime c where (c.type IS NULL OR c.type = 1) and c.labId= " + device.getLabRoom().getId() + " and c.schoolTerm.id=" + termId, 0, -1);
        // 如果是禁用时间匹配则返回limit
        if (!shareService.isLimitedByTime(labRoomLimitTimes, calendar1, calendar2)) {
            return "LIMIT";
        }
        // 判冲
        int flag = labRoomDeviceReservationService.judgeConflictForDeviceReservation(equinemtid, null, calendar1, calendar2);// 标记为0为失败，1为成功
        if(flag <= 0){
            return "error";
        }

        // 保存新的设备预约
        LabRoomDeviceReservation reservation = labRoomDeviceReservationService.saveALabRoomDeviceReservation(equinemtid, description, phone, teacher, startDate, endDate, research);

        // 提交设备预约到审核服务
        String businessType = "DeviceReservation" + device.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        auditService.saveInitBusinessAudit(device.getId().toString(), businessType, reservation.getId().toString());
        String currAuditStr = auditService.getCurrAudit(reservation.getId().toString(), businessType);
        JSONObject currAuditJSONObject = JSONObject.parseObject(currAuditStr);
        if(!"success".equals(currAuditJSONObject.getString("status"))){
            return "error";
        }
        JSONArray currAuditJSONArray = currAuditJSONObject.getJSONArray("data");
        String firstAuthName = currAuditJSONArray.getJSONObject(0).getString("result");

        // 不需要审核
        if (device.getCDictionaryByIsAudit() == null ||
                (device.getCDictionaryByIsAudit() != null && device.getCDictionaryByIsAudit().getCName().equals("否")) ||
                (currAuditJSONArray != null && currAuditJSONArray.size() != 0 && currAuditJSONArray.getJSONObject(0).getIntValue("level") == -1)) {
            //预约成功刷新设备使用机时和次数
            if (schoolDevice.getUseHours() != null) {
                schoolDevice.setUseHours(schoolDevice.getUseHours().add(reservation.getReserveHours()));
            } else {
                schoolDevice.setUseHours(reservation.getReserveHours());
            }
            if (schoolDevice.getUseCount() != null) {
                schoolDevice.setUseCount(schoolDevice.getUseCount() + 1);
            } else {
                schoolDevice.setUseCount(1);
            }
            schoolDeviceDAO.store(schoolDevice);
            schoolDeviceDAO.flush();
            CDictionary status = shareService.getCDictionaryByCategory("c_audit_result", "2");
            reservation.setCAuditResult(status);
            reservation.setRemark("该实验室预约不需要审核");
        } else if (device.getCDictionaryByIsAudit().getCName().equals("是")) {
            CDictionary status = shareService.getCDictionaryByCategory("c_audit_result", "1");
            reservation.setCAuditResult(status);
            reservation.setRemark("");
        }


        //消息(发送到审核人)
        Message message = new Message();
        Calendar date1 = Calendar.getInstance();
        message.setSendUser(shareService.getUserDetail().getCname());
        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle("设备预约增加");
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(date1);
        if("TEACHER".equals(firstAuthName) && !"0".equals(user.getUserRole())){
            String saveStr = auditService.saveBusinessLevel(reservation.getId().toString(), reservation.getLabRoomDevice().getId().toString(), "pass", "不是学生不需要导师审核", businessType, user.getUsername());
            JSONObject saveJSONObject = JSONObject.parseObject(saveStr);
            JSONObject saveResult = saveJSONObject.getJSONObject("data");
            firstAuthName = saveResult.getString("result");
        }

        //判断预约产生时处于第几级审核状态
        if ("TEACHER".equals(firstAuthName)) {
            //产生导师的消息
            String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
            message.setContent(content);
            message.setUsername(reservation.getUserByTeacher().getUsername());
            message.setTage(2);
            messageDAO.store(message);
            messageDAO.flush();
        } else if ("CFO".equals(firstAuthName)) {
            //产生系主任的消息
            String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + reservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
            message.setContent(content);
            List<User> deans = shareService.findDeansByAcademyNumber(reservation.getUserByReserveUser().getSchoolAcademy());
            if(deans != null && deans.size() > 0) {
                for(User dean: deans) {
                    message.setUsername(dean.getUsername());
                    message.setTage(2);
                    messageDAO.store(message);
                    messageDAO.flush();
                }
            }
        } else if ("LABMANAGER".equals(firstAuthName)) {
            //产生实训室管理员的消息
            String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + reservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
            message.setContent(content);
            if (device.getLabRoom().getLabRoomAdmins() != null) {
                for (LabRoomAdmin labRoomAdmin : device.getLabRoom().getLabRoomAdmins()) {
                    message.setUsername(labRoomAdmin.getUser().getUsername());
                    message.setTage(2);
                    messageDAO.store(message);
                    messageDAO.flush();
                }
            }
        } else if ("EXCENTERDIRECTOR".equals(firstAuthName)) {
            //产生实训中心主任的消息
            String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
            message.setContent(content);
            if (device.getLabRoom().getLabCenter().getUserByCenterManager() != null) {
                message.setUsername(device.getLabRoom().getLabCenter().getUserByCenterManager().getUsername());
                message.setTage(2);
                messageDAO.store(message);
                messageDAO.flush();
            }
        } else if (!"pass".equals(firstAuthName) && !"fail".equals(firstAuthName)) {
            //产生实训部主任的消息
            String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservation.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
            message.setContent(content);
            if (shareService.findUsersByAuthorityName(firstAuthName) != null) {
                for (User authUser : shareService.findUsersByAuthorityName(firstAuthName)) {
                    message.setUsername(authUser.getUsername());
                    message.setTage(2);
                    messageDAO.store(message);
                    messageDAO.flush();
                }
            }
        }  else if("pass".equals(firstAuthName)) {
            reservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
        }else if("fail".equals(firstAuthName)) {
            reservation.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
        }

        //消息(发送到自己)
        Message messageSendSelf = new Message();
        messageSendSelf.setSendUser(shareService.getUserDetail().getCname());
        messageSendSelf.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
        messageSendSelf.setCond(0);
        messageSendSelf.setTitle("设备预约");
        messageSendSelf.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        messageSendSelf.setCreateTime(date1);
        String contents = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservation.getId() + "&tage=0&state=0&currpage=1\">查看</a>";
        messageSendSelf.setContent(contents);
        messageSendSelf.setUsername(shareService.getUserDetail().getUsername());
        messageSendSelf.setTage(1);
        messageDAO.store(messageSendSelf);
        messageDAO.flush();

        String dateStr = sdf.format(Calendar.getInstance().getTime());
        if (flag > 0) {
            reservation.setStage(0);
            if (device.getSchoolDevice().getInnerSame() != null && !device.getSchoolDevice().getInnerSame().equals("")) {
                reservation.setInnerSame(device.getSchoolDevice().getInnerSame() + "-" + dateStr);
                reservation.setInnerDeviceName(device.getSchoolDevice().getInnerDeviceName().replace("]", "]</br>"));
            } else {
                reservation.setInnerSame("device-" + device.getId() + "-" + dateStr);// 为了查询时候的group by InnerSame
            }
            labRoomDeviceReservationService.saveLabRoomDeviceReservationNew(reservation, response);

            return "success";
        } else {
            return "error";
        }

    }

    /*
    *Description:微服务设备预约保存
    * author:Hezhaoyi
    * 2019-3-1
     */
    /*@Transactional
    @RequestMapping("/device/saveDeviceReservation")
    *//*public @ResponseBody
    String  saveDeviceReservation(@RequestBody LabDeviceReservationParamDTO labDeviceReservationParamDTO) throws Exception*//*
    public @ResponseBody
    String  saveDeviceReservation(@RequestParam Integer equinemtid, String startDate,
                                  String endDate, String description, String phone,
                                  String teacher, Integer property,HttpServletResponse response) throws Exception{

        LabDeviceReservationParamDTO ldrpDTO = new LabDeviceReservationParamDTO();
        ldrpDTO.setEquinemtid(equinemtid);
        ldrpDTO.setStartDate(startDate);
        ldrpDTO.setEndDate(endDate);
        ldrpDTO.setDescription(description);
        ldrpDTO.setPhone(phone);
        ldrpDTO.setTeacher(teacher);
        *//*ldrpDTO.setProperty(property);*//*
        //东华版本实验设备预约
        String result = labDeviceReservationService.saveLabDeviceReservation(ldrpDTO,response);

        return result;

    }*/



    /****************************************************************************
     * @功能：Ajax保存设备预约并返回是否保存成功
     * @作者：XL
     ****************************************************************************/
    @RequestMapping("/device/saveModifyDeviceReservation")
    public @ResponseBody
    String saveDeviceReservation(@RequestParam Integer equinemtid, String startDate, String endDate, String description, String phone, String teacher, Integer property, @RequestParam Integer reservalId) throws Exception {
        // 找到对应的实验室预约
        LabRoomDeviceReservation reservation = labRoomDeviceReservationDAO.findLabRoomDeviceReservationByPrimaryKey(reservalId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 更新开始@时间
        Date date = sdf.parse(startDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        reservation.setBegintime(calendar1);

        // 更新结束@时间
        Date date2 = sdf.parse(endDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        reservation.setEndtime(calendar2);

        // equinemtid对应的实验室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(equinemtid);
        // 获取当前学期
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();

        // 实验室禁用时间段列表
        List<LabRoomLimitTime> labRoomLimitTimes = labRoomLimitTimeDAO.executeQuery("select c from LabRoomLimitTime c where c.labId= " + device.getLabRoom().getId() + " and c.schoolTerm.id=" + termId, 0, -1);
        // 如果是禁用时间匹配则返回limit
        if (!shareService.isLimitedByTime(labRoomLimitTimes, calendar1, calendar2)) {
            return "LIMIT";
        }

        // 根据设备id查询设备的预约记录
        List<LabRoomDeviceReservation> reservationList = labRoomDeviceService.findReservationListByDeviceId(equinemtid);
        int flag = 1;// 标记为0为失败，1为成功
        // 循环遍历预约记录，看是否和以前的预约有冲突
        for (LabRoomDeviceReservation r : reservationList) {
            if (r.getId().equals(reservalId)) {// 不与自己判冲
                continue;
            }
            Calendar start = r.getBegintime();
            Calendar end = r.getEndtime();
            if (end.after(calendar1) && start.before(calendar2)) {
                flag = 0;
            }
        }

        if (flag > 0) {
            labRoomDeviceReservationDAO.store(reservation);
            return "success";
        } else {
            return "error";
        }

    }


    /****************************************************************************
     * @功能：查询以前所有的预约记录并显示在页面上
     * @作者：李小龙
     ****************************************************************************/
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping("/device/getLabReservation")
    public @ResponseBody
    List getLabReservation(@RequestParam Integer id, HttpServletRequest request) throws Exception {

        //,@RequestParam String
        String reservalId = request.getParameter("reservalId");

        // 创建一个新的数组对象；
        List list = new ArrayList();
        // 根据设备id查询设备预约记录
        List<LabRoomDeviceReservation> reservations = labRoomDeviceService.findReservationListByDeviceId(id);
        for (LabRoomDeviceReservation r : reservations) {

            String cname = r.getUserByReserveUser().getCname();
            String result = "未审核";
            if(r.getCAuditResult() != null){
                result = r.getCAuditResult().getCName();
            }
            String content = r.getContent();

            // 创建一个新的map对象给变量appointmentMap；
            Map<String, Object> map = new HashMap<String, Object>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdfSimple = new SimpleDateFormat("yyyy-MM-dd");
            // 开始@时间
            Calendar startDate = r.getBegintime();
            // 结束@时间
            Calendar endDate = r.getEndtime();
            // 解决跨天问题

            // 获取两个日期相隔的天数
            int days = (int) ((endDate.getTime().getTime() - startDate.getTime().getTime()) / (1000 * 60 * 60 * 24));
            // 如果不跨天
            if (days == 0) {
                map.put("start_date", sdf.format(startDate.getTime()));
                map.put("end_date", sdf.format(endDate.getTime()));
                if ((r.getId().intValue() + "").equals(reservalId)) {// 设备预约本身的颜色为绿色
                    map.put("color", "green");
                    map.put("text", "预约者：" + cname + "<br />审核状态：" + result + "<br />预约内容：" + content
                            + "<br /><br /><font color='red'>此色块为您正在修改的时间段</font>");
                } else {
                    map.put("text", "预约者：" + cname + "<br />审核状态：" + result + "<br />预约内容：" + content);
                }
                list.add(map);

            } else {

                for (int i = 0; i <= days; i++) {
                    Map<String, Object> mapTmp = new HashMap<String, Object>();
                    if (i == 0) {
                        mapTmp.put("start_date", sdf.format(startDate.getTime()));
                        mapTmp.put("end_date", sdfSimple.format(startDate.getTime()) + " 23:59:59");
                    } else if (i == days) {
                        mapTmp.put("start_date", sdfSimple.format(endDate.getTime()) + " 00:00:01");
                        mapTmp.put("end_date", sdf.format(endDate.getTime()));
                    } else {
                        startDate.add(Calendar.DATE, 1);//
                        mapTmp.put("start_date", sdfSimple.format(startDate.getTime()) + " 00:00:01");
                        mapTmp.put("end_date", sdfSimple.format(startDate.getTime()) + " 23:59:59");

                    }
                    if ((r.getId().intValue() + "").equals(reservalId)) {// 设备预约本身的颜色为绿色
                        mapTmp.put("text", "预约者：" + cname + "<br />审核状态：" + result + "<br />预约内容：" + content
                                + "<br /><br /><font color='red'>此色块为您正在修改的时间段</font>");
                        mapTmp.put("color", "green");
                    } else {
                        mapTmp.put("text", "预约者：" + cname + "<br />审核状态：" + result + "<br />预约内容：" + content);
                    }
                    list.add(mapTmp);

                }

            }

        }

        System.out.println(list.size());
        return list;
    }

    /****************************************************************************
     * @功能：获取设备禁止时间
     * @作者：XL
     ****************************************************************************/
    @SuppressWarnings("rawtypes")
    @RequestMapping("/device/getLimitLabReservation")
    public @ResponseBody
    List getLimitLabReservation(@RequestParam Integer id, @ModelAttribute("selected_academy") String acno) throws Exception {

        List<Map<String, String>> limits = new LinkedList<Map<String, String>>();
        Map<String, String> limit = null;

        // 获取room禁用时间
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        Integer roomID = labRoomDevice.getLabRoom().getId();
        Set<LabRoomLimitTime> limitTimes = labRoomLimitTimeDAO.findLabRoomLimitTimeByLabId(roomID);

        for (LabRoomLimitTime lrlt : limitTimes) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");

            long sTime = lrlt.getSchoolTerm().getTermStart().getTime().getTime();

            Integer weekday = lrlt.getWeekday();

            Integer startweek = lrlt.getStartweek();
            Integer endweek = lrlt.getEndweek();

            Integer startclass = lrlt.getStartclass();
            Integer endclass = lrlt.getEndclass();

            for (int i = startweek; i <= endweek; i++) {

                long dlT = (i - 1) * 7 * 24 * 3600 * 1000l + (weekday - 1) * 24 * 3600 * 1000l;
                String date = sdf.format(new Date(sTime + dlT));

                SystemTime STime = systemTimeDAO.findSystemTimeById(startclass);
                SystemTime ETime = systemTimeDAO.findSystemTimeById(endclass);

                limit = new HashMap<String, String>();
                limit.put("start_date", date + " " + hms.format(STime.getStartDate().getTime()));
                limit.put("end_date", date + " " + hms.format(ETime.getEndDate().getTime()));
                limit.put("color", "orange");
                limit.put("text", "设备禁止时间段");

                limits.add(limit);
            }

        }

        /*
         * Gson gson = new Gson(); System.out.println(gson.toJson(limits));
         */

        return limits;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理---编辑
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/editDeviceReservationInfo")
    public ModelAndView editDeviceReservationInfo(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);
        // 设备管理员
        User user = shareService.getUser();
        List<User> users = shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber());
        mav.addObject("users", users);
        // 设备状态
        //Set<CDeviceStatus> stutus = cDeviceStatusDAO.findAllCDeviceStatuss();
        List<CDictionary> stutus = shareService.getCDictionaryData("c_lab_room_device_status");
        mav.addObject("stutus", stutus);
        // 所属类型
        //Set<CDeviceType> types = cDeviceTypeDAO.findAllCDeviceTypes();
        List<CDictionary> types = shareService.getCDictionaryData("c_lab_room_device_type");
        mav.addObject("types", types);
        // 收费标准
        //Set<CDeviceCharge> charges = cDeviceChargeDAO.findAllCDeviceCharges();
        List<CDictionary> charges = shareService.getCDictionaryData("c_lab_room_device_charge");
        mav.addObject("charges", charges);

        // 当前@时间所属的学期
        Calendar time = Calendar.getInstance();
        SchoolTerm term = shareService.getBelongsSchoolTerm(time);
        // 根据学期和设备查询培训
        List<LabRoomDeviceTraining> trainings = labRoomDeviceService.findLabRoomDeviceTrainingByTermId(term.getId(), id);
        mav.addObject("trainings", trainings);
        // 培训表单的对象
        mav.addObject("train", new LabRoomDeviceTraining());
        // 培训教师

        // 安全准入形式
        //CLabAccessType accessType = device.getCLabAccessType();
        CDictionary accessType = device.getCDictionaryBySecurityAccessType();
        if (accessType != null) {
            if (accessType.getCNumber() != null && "1".equals(accessType.getCNumber())) {// 现场培训
                mav.setViewName("/device/lab_room_device_access/deviceTraining.jsp");
            }
            if (accessType.getCNumber() != null && "2".equals(accessType.getCNumber())) {// 网上考试
                // 答题设置

                mav.setViewName("/device/lab_room_device_access/deviceExam.jsp");
            }
        } else {// 默认为现场培训
            mav.setViewName("/device/lab_room_device_access/deviceTraining.jsp");
        }

        return mav;
    }

    /****************************************************************************
     * @功能：查看设备
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/showDeviceTraining")
    public ModelAndView showDeviceTraining(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);

        mav.setViewName("/device/lab_room_device/showDeviceTraining.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：保存实验设备培训
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/saveLabRoomDeviceTraining")
    public ModelAndView saveLabRoomDeviceTraining(@ModelAttribute LabRoomDeviceTraining train, @RequestParam Integer deviceId, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        train.setLabRoomDevice(device);
        // 当前@时间所属学期为培训所属学期
        SchoolTerm term = shareService.getBelongsSchoolTerm(train.getTime());
        train.setSchoolTerm(term);
        // 状态设置为待培训
        //CTrainingStatus status = cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(1);
        CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "1");
        train.setCTrainingStatus(status);
        // 参加人数默认为0
        train.setJoinNumber(0);
        if (train.getUser() != null && train.getUser().getUsername() != null && !train.getUser().getUsername().equals("")) {
            train.getUser().setUsername(train.getUser().getUsername());
        } else {
            train.setUser(null);
        }
        labRoomDeviceService.saveLabRoomDeviceTraining(train);
        // 中心所属学院
        boolean flag = specialAcademy.contains(acno);
        if (flag) {
            mav.setViewName("redirect:/device/deviceTraining?deviceId=" + deviceId);
        } else {
            mav.setViewName("redirect:/device/editDeviceReservationInfo?id=" + deviceId);
        }

        return mav;
    }


    /****************************************************************************
     * @功能：实验室设备管理---设备管理---保存实验设备信息
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/saveDeviceReservationInfo")
    public ModelAndView saveDeviceReservationInfo(@ModelAttribute LabRoomDevice device, @RequestParam Integer deviceId, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备
        LabRoomDevice d = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        User user = new User();
        if (device.getUser() != null) {
            user = userDAO.findUserByPrimaryKey(device.getUser().getUsername());
            d.setUser(user);
        }
		/*if (device.getCDeviceStatus() != null && device.getCDeviceStatus().getId() != null) {
			d.setCDeviceStatus(device.getCDeviceStatus());
		}
		if (device.getCDeviceType() != null && device.getCDeviceType().getId() != null) {
			d.setCDeviceType(device.getCDeviceType());
		}
		if (device.getCDeviceCharge() != null && device.getCDeviceCharge().getId() != null) {
			d.setCDeviceCharge(device.getCDeviceCharge());
		}*/
        if (device.getCDictionaryByDeviceStatus() != null && device.getCDictionaryByDeviceStatus().getId() != null) {
            d.setCDictionaryByDeviceStatus(device.getCDictionaryByDeviceStatus());
        }
        if (device.getCDictionaryByDeviceType() != null && device.getCDictionaryByDeviceType().getId() != null) {
            d.setCDictionaryByDeviceType(device.getCDictionaryByDeviceType());
        }
        if (device.getCDictionaryByDeviceCharge() != null && device.getCDictionaryByDeviceCharge().getId() != null) {
            d.setCDictionaryByDeviceCharge(device.getCDictionaryByDeviceCharge());
        }
        d.setIndicators(device.getIndicators());

        d.setPrice(device.getPrice());
        d.setFunction(device.getFunction());
        d.setFeatures(device.getFeatures());
        d.setApplications(device.getApplications());
        labRoomDeviceService.save(d);
        // 给用户赋予权限
        if (user != null) {

            Set<Authority> ahths = user.getAuthorities();

            Authority a = authorityDAO.findAuthorityByPrimaryKey(10);// 设备管理员
            ahths.add(a);
            user.setAuthorities(ahths);
            userDAO.store(user);
        }
        // 中心所属学院
//        String academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
//        if (academy.equals("0201")) {// 纺织学院
//            mav.setViewName("redirect:/device/listLabRoomDeviceNew?roomId=" + d.getLabRoom().getId() + "&page=1");
//        } else
            mav.setViewName("redirect:/device/listLabRoomDevice?page=1");
        // mav.setViewName("redirect:/device/listLabRoomDevice?page=1");
        return mav;
    }

    /****************************************************************************
     * @功能：根据设备id查询培训
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/findAllTrainingByDeviceId")
    public ModelAndView findAllTrainingByDeviceId(@ModelAttribute LabRoomDeviceTraining train, @RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 培训查询表单的对象
        mav.addObject("train", train);
        // 设备id
        mav.addObject("deviceId", deviceId);
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 根据设备id和培训对象查询培训
        List<LabRoomDeviceTraining> trainList = labRoomDeviceService.findLabRoomDeviceTrainingByDeviceId(train, deviceId);

        mav.addObject("trainList", trainList);
        // 当前登录人是否参加过培训
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 第一个培训的培训名单
        if (trainList.size() > 0) {
            for (LabRoomDeviceTraining t : trainList) {
                int i = t.getId();
                List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(i);
                mav.addObject("peoples", peoples);
                int flag = 1;// 默认为1：未参加，0为已参加
                for (LabRoomDeviceTrainingPeople p : peoples) {
                    if (p.getUser().getUsername().equals(user.getUsername())) {
                        flag = 0;
                        break;
                    } else {
                        flag = 1;
                    }
                }
                map.put(i, flag);
            }

        }
        mav.addObject("map", map);

        List<User> userList = shareService.findTheSameCollegeUser(user.getSchoolAcademy().getAcademyNumber());
        mav.addObject("userList", userList);
        // 培训结果
        //Set<CTrainingResult> results = cTrainingResultDAO.findAllCTrainingResults();
        List<CDictionary> results = shareService.getCDictionaryData("c_training_result");
        mav.addObject("results", results);

        mav.setViewName("/device/lab_room_device_access/trainList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：根据设备id查询培训和培训人名单
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/findTrainingPeopleByDeviceIdAndTrainId")
    public ModelAndView findTrainingPeopleByDeviceIdAndTrainId(@ModelAttribute LabRoomDeviceTraining train, @RequestParam Integer deviceId, Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备培训
        // 培训查询表单的对象
        mav.addObject("train", train);
        // 设备id
        mav.addObject("deviceId", deviceId);
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        // 培训人
        User user = shareService.getUser();
        List<User> userList = shareService.findTheSameCollegeUser(user.getSchoolAcademy().getAcademyNumber());
        mav.addObject("userList", userList);
        // 培训结果
        //Set<CTrainingResult> results = cTrainingResultDAO.findAllCTrainingResults();
        List<CDictionary> results = shareService.getCDictionaryData("c_training_result");
        mav.addObject("results", results);

        // 根据设备id查询培训
        List<LabRoomDeviceTraining> trainList = labRoomDeviceService.findLabRoomDeviceTrainingByDeviceId(train, deviceId);
        mav.addObject("trainList", trainList);
        // 根据培训id查询培训名单
        List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(id);

        mav.addObject("peoples", peoples);
        // 当前登录人是否参加过培训
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 第一个培训的培训名单
        if (trainList.size() > 0) {
            for (LabRoomDeviceTraining t : trainList) {
                int i = t.getId();
                List<LabRoomDeviceTrainingPeople> ps = labRoomDeviceService.findTrainingPeoplesByTrainingId(i);
                int flag = 1;
                for (LabRoomDeviceTrainingPeople p : ps) {
                    if (p.getUser().getUsername().equals(user.getUsername())) {
                        flag = 0;
                        break;
                    } else {
                        flag = 1;
                    }
                }
                map.put(i, flag);
            }

        }
        mav.addObject("map", map);
        mav.setViewName("/device/lab_room_device_access/trainingPeopleList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：保存实验设备培训人
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/saveLabRoomDeviceTrainingPeople")
    public ModelAndView saveLabRoomDeviceTrainingPeople(@ModelAttribute LabRoomDeviceTrainingPeople people) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int id = people.getLabRoomDeviceTraining().getId();
        // id对应的实验室设备培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);

        String usernames = people.getUser().getUsername();
        String name[] = usernames.split(",");
        for (int i = 0; i < name.length; i++) {
            // 培训对象
            LabRoomDeviceTrainingPeople p = new LabRoomDeviceTrainingPeople();
            p.setLabRoomDeviceTraining(train);
            // 培训人
            User u = userDAO.findUserByPrimaryKey(name[i]);
            p.setUser(u);
            // 培训结果
            //CTrainingResult result = cTrainingResultDAO.findCTrainingResultByPrimaryKey(2);
            CDictionary result = shareService.getCDictionaryByCategory("c_training_result", "2");

            p.setCDictionary(result);

            labRoomDeviceTrainingPeopleDAO.store(p);
        }

        mav.setViewName("redirect:/device/findAllTrainingByDeviceId?deviceId=" + train.getLabRoomDevice().getId());
        return mav;
    }

    /****************************************************************************
     * @功能：保存实验设备培训结果
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/saveTrainResult")
    public ModelAndView saveTrainResult(@RequestParam int idArray[], int valueArray[], @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        for (int i = 0; i < idArray.length; i++) {
            // 培训人
            LabRoomDeviceTrainingPeople p = labRoomDeviceTrainingPeopleDAO.findLabRoomDeviceTrainingPeopleByPrimaryKey(idArray[i]);
            // 培训结果
            //CTrainingResult result = cTrainingResultDAO.findCTrainingResultByPrimaryKey(valueArray[i]);
            CDictionary result = shareService.getCDictionaryByCategory("c_training_result", String.valueOf(valueArray[i]));

            p.setCDictionary(result);
            // 执行保存
            labRoomDeviceTrainingPeopleDAO.store(p);

            // 将通过的学生添加到LabRoomDevicePermitUsers中，flag为2
            // 先将该用户从permitUser里面清除，防止重复添加
            if (p.getUser() != null && p.getLabRoomDeviceTraining() != null && p.getLabRoomDeviceTraining().getLabRoomDevice() != null) {
                String username1 = p.getUser().getUsername();
                int deviceId = p.getLabRoomDeviceTraining().getLabRoomDevice().getId();
                LabRoomDevicePermitUsers permitUser = labRoomDeviceService.findPermitUserByUsernameAndDeivce(username1, deviceId);
                if (permitUser != null) {
                    labRoomDeviceService.deletePermitUser(permitUser);
                }
            }
            if (valueArray[i] == 1) {// 通过
                // username对应的用户
                LabRoomDevicePermitUsers student = new LabRoomDevicePermitUsers();
                student.setUser(p.getUser());
                if (p.getLabRoomDeviceTraining() != null && p.getLabRoomDeviceTraining().getLabRoomDevice() != null) {
                    student.setLabRoomDevice(p.getLabRoomDeviceTraining().getLabRoomDevice());
                }
                student.setFlag(2);// 标记位（1 单独培训通过 2 集训通过 3 集训后门）
                labRoomDevicePermitUsersDAO.store(student);
            } else {// 不通过
                // do nothing
            }
        }
        // 培训人对应的培训
        LabRoomDeviceTrainingPeople people = labRoomDeviceTrainingPeopleDAO.findLabRoomDeviceTrainingPeopleByPrimaryKey(idArray[0]);
        LabRoomDeviceTraining train = people.getLabRoomDeviceTraining();
        // 该培训的培训人
        Set<LabRoomDeviceTrainingPeople> peoples = train.getLabRoomDeviceTrainingPeoples();
        // 根据培训id查询培训通过的人
        List<LabRoomDeviceTrainingPeople> passPeoples = labRoomDeviceService.findPassLabRoomDeviceTrainingPeopleByTrainId(train.getId());

        // 计算通过率
        double a = passPeoples.size();
        double b = peoples.size();
        double c = a / b;
        // 获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        // 设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        String s = nt.format(c);
        train.setPassRate(s);
        // 状态改为已完成
        //CTrainingStatus status = cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(2);
        CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "2");
        train.setCTrainingStatus(status);
        labRoomDeviceTrainingDAO.store(train);

        mav.setViewName("redirect:/device/listLabRoomDeviceTraining?currpage=1&isTeacher=1");

        return mav;
    }

    /****************************************************************************
     * @功能：保存实验设备培训人
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/joinTraining")
    public ModelAndView joinTraining(@RequestParam Integer id, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        User user = shareService.getUser();
        LabRoomDeviceTrainingPeople people = new LabRoomDeviceTrainingPeople();
        people.setLabRoomDeviceTraining(train);
        people.setUser(user);
        labRoomDeviceTrainingPeopleDAO.store(people);
        Set<LabRoomDeviceTrainingPeople> peoples = train.getLabRoomDeviceTrainingPeoples();
        train.setJoinNumber(peoples.size());
        labRoomDeviceTrainingDAO.store(train);
        // 中心所属学院
        boolean flag = specialAcademy.contains(acno);
        if (flag) {
            mav.setViewName("redirect:/device/deviceTraining?deviceId=" + train.getLabRoomDevice().getId());
        } else {
            mav.setViewName("redirect:/device/findTrainingPeopleByDeviceIdAndTrainId?deviceId=" + train.getLabRoomDevice().getId() + "&id=" + train.getId());
        }

        return mav;
    }

    /****************************************************************************
     * @功能：删除培训
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deleteLabRoomDeviceTrain")
    public ModelAndView deleteLabRoomDeviceTrain(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的培训
        LabRoomDeviceTraining t = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        int deviceId = t.getLabRoomDevice().getId();
        labRoomDeviceTrainingDAO.remove(t);
        mav.setViewName("redirect:/device/editDeviceReservationInfo?id=" + deviceId);
        return mav;
    }

    /****************************************************************************
     * @功能：AJAX查询设备使用记录
     * @作者：徐龙帅
     ****************************************************************************/
    @RequestMapping("/device/findUsedDevice")
    public @ResponseBody
    String findUsedDevice(@RequestParam Integer deciceId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // System.out.println("lll");
        List<LabRoomDeviceReservation> showRecord = labRoomDeviceReservationDAO.executeQuery("SELECT l FROM LabRoomDeviceReservation l  WHERE l.labRoomDevice.id=" + deciceId);
        // System.out.println("showRecord="+showRecord);
        String s = "";
        for (LabRoomDeviceReservation l : showRecord) {
            s += "<tr>" + "<td>" + l.getUserByReserveUser().getCname() + "</td>" + "<td>" + l.getLabRoomDevice().getSchoolDevice().getDeviceNumber() + "</td>" + "<td>" + l.getLabRoomDevice().getSchoolDevice().getDeviceName() + "</td>" + "<td>" + l.getLabRoomDevice().getSchoolDevice().getDevicePattern() + "</td>" + "<td>" + sdf.format(l.getBegintime().getTime()) + "</td>" + "<td>" + sdf.format(l.getEndtime().getTime()) + "</td>" + "<td>" + l.getUseTime() + "</td>" + "</tr>";
        }
        // System.out.println("s="+s);
        return htmlEncode(s);

    }

    /****************************************************************************
     * @功能：处理ajax中文乱码
     * @作者：李小龙
     ****************************************************************************/
    public static String htmlEncode(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int c = (int) str.charAt(i);
            if (c > 127 && c != 160) {
                sb.append("&#").append(c).append(";");
            } else {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }

    /****************************************************************************
     * @功能：设备出借申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/deviceLendApply")
    public ModelAndView deviceLendApply(@RequestParam Integer idKey, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        //获取当前登陆人
        User user = shareService.getUser();
        mav.addObject("idKey", idKey);
        mav.addObject("date", shareService.getDate());
        mav.addObject("labRoomDeviceLend", new LabRoomDeviceLending());
        mav.addObject("labRoomDevice", labRoomDeviceService.findLabRoomDeviceByPrimaryKey(idKey));
        mav.addObject("user", shareService.getUser());
        // 查找当前中心所属学院
//		String num = labCenterDAO.findLabCenterById(cid).getSchoolAcademy().getAcademyNumber();
        mav.addObject("users", shareService.findAllTeacheres());
        mav.addObject("username", 1);
        if (shareService.findAllDepartmentHead(user).size() != 0 && shareService.findAllDepartmentHead(user) != null && !shareService.findAllDepartmentHead(user).equals("")) {
            mav.addObject("departmentHead", shareService.findAllDepartmentHead(user).get(0));
            mav.addObject("username", shareService.findAllDepartmentHead(user).get(0).getUsername());
        }
        mav.setViewName("/device/lab_room_device/deviceLendApply.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备领用申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/deviceKeepApply")
    public ModelAndView deviceKeepApply(@RequestParam Integer idKey, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("idKey", idKey);
        mav.addObject("date", shareService.getDate());
        mav.addObject("labRoomDeviceLend", new LabRoomDeviceLending());
        mav.addObject("labRoomDevice", labRoomDeviceService.findLabRoomDeviceByPrimaryKey(idKey));
        mav.addObject("user", shareService.getUser());
        // 查找当前中心所属学院
        mav.addObject("users", shareService.findTheSameCollegeTeacher(acno));
        mav.setViewName("/device/lab_room_device/deviceKeepApply.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：保存设备出借申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/saveDeviceLendApply")
    public String saveDeviceLendApply(@ModelAttribute LabRoomDeviceLending lrdl) {
        //获取当前登陆人
        User user = shareService.getUser();
        //获取系主任
        if (!shareService.findAllDepartmentHead(user).isEmpty()) {
            lrdl.setUserByDepartmentHead(shareService.findAllDepartmentHead(user).get(0));
        }
        lrdl = labRoomDeviceService.saveDeviceLendApply(lrdl);
        CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_room_device_status", "5");
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(lrdl.getLabRoomDevice().getId());
        labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);//将设备状态设为借用中
        labRoomDevice = labRoomDeviceService.save(labRoomDevice);
        return "redirect:/device/deviceLendingApplyList?page=1";
    }

    /****************************************************************************
     * @功能：保存设备领用申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/saveDeviceKeepApply")
    public String saveDeviceKeepApply(@ModelAttribute LabRoomDeviceLending lrdl) {
        labRoomDeviceService.saveDeviceLendApply(lrdl);
        return "redirect:/device/deviceKeepList?page=1";
    }

    /****************************************************************************
     * @功能：设备出借申请单列表
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/deviceLendList")
    public ModelAndView deviceLendList(@ModelAttribute LabRoomDeviceLending lrdl, @RequestParam Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdl);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getLabRoomLendsTotals(lrdl, request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLending> deviceLendList = labRoomDeviceService.findAllLabRoomLends(lrdl, page, pageSize, request);

        mav.addObject("deviceLendList", deviceLendList);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 11) {
                    mav.addObject("admin", true);
                }
                if (a.getId() == 4) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("labRoomHeads", shareService.findAllLabRoomtHead());
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/deviceLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备领用申请单列表
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/deviceKeepList")
    public ModelAndView deviceKeepList(@ModelAttribute LabRoomDeviceLending lrdl, @RequestParam Integer page, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdl);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getLabRoomKeepsTotals(lrdl);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLending> deviceLendList = labRoomDeviceService.findAllLabRoomKeeps(lrdl, page, pageSize);
        mav.addObject("deviceLendList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 11) {
                    mav.addObject("admin", true);
                }
                if (a.getId() == 4) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/deviceKeepList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：审核设备出借申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/auditLabRoomAdminDeviceLending")
    public ModelAndView auditLabRoomAdminDeviceLending(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(idKey);
        if(lrdl==null){
            mav.addObject("noLabRoomDeviceLending", 1);
            mav.setViewName("/device/lab_room_device/auditDeviceLending.jsp");
            return mav;
        }
        // 找到流水号
        String lendBatch = lrdl.getLendBatch();
        // 找到流水号下的设备借用
        List<LabRoomDeviceLending> lends = labRoomDeviceService.getDeviceLendingByBatchWithoutReject(lendBatch);
        mav.addObject("lends", lends);
        mav.addObject("lrdl", lrdl);
        mav.addObject("idKey", idKey);
        mav.addObject("user", shareService.getUser());
        mav.addObject("labRoomAdmins", lrdl.getLabRoomDevice().getLabRoom().getLabRoomAdmins());
        mav.addObject("result", labRoomDeviceService.getAuditResultMap());
        mav.addObject("lrdlr", new LabRoomDeviceLendingResult());
        mav.setViewName("/device/lab_room_device/auditDeviceLending.jsp");
        return mav;
    }

    /****************************************************************************
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @功能：保存实训室管理员审核的申请单
     * @作者：周志辉
     * @时间：1017-10-16
     ****************************************************************************/
    @RequestMapping("/device/saveLabRoomAdminDeviceLending")
    public @ResponseBody String saveLabRoomAdminDeviceLending(HttpServletRequest request, 
            Integer[] array_ok, Integer[] array_refuse) throws NoSuchAlgorithmException, InterruptedException {
        String remark = request.getParameter("remark");
        if (!EmptyUtil.isObjectEmpty(array_ok) && array_ok.length>0) {
            Integer lendId = 0;
            for (Integer id : array_ok) {
                // 新建并保存审核结果
                LabRoomDeviceLendingResult l = new LabRoomDeviceLendingResult();
                LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(id);
                l.setLabRoomDeviceLending(lrdl);
                l.setUser(shareService.getUser());
                l.setRemark(remark);
                CDictionary cDictionary = new CDictionary();
                cDictionary.setId(615);// 615审核通过
                l.setCDictionary(cDictionary);
                labRoomDeviceLendingResultDAO.store(l);
                // 根据结果和借用的id处理后续消息、状态等机制
                labRoomDeviceService.createAuditMessageByLendIdManager(id, 2+"");
                lendId = lrdl.getId();
            }
            labRoomDeviceService.sendMessageForDeviceLending(lendId, 2+"", 2);
        }
        if (!EmptyUtil.isObjectEmpty(array_refuse) && array_refuse.length>0) {
            Integer lendId = 0;
            for (Integer id : array_refuse) {
                // 新建并保存审核结果
                LabRoomDeviceLendingResult l = new LabRoomDeviceLendingResult();
                LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(id);
                l.setLabRoomDeviceLending(lrdl);
                l.setUser(shareService.getUser());
                l.setRemark(remark);
                CDictionary cDictionary = new CDictionary();
                cDictionary.setId(616);// 616审核拒绝
                l.setCDictionary(cDictionary);
                labRoomDeviceLendingResultDAO.store(l);
                // 根据结果和借用的id处理后续消息、状态等机制
                labRoomDeviceService.createAuditMessageByLendIdManager(id, 3+"");
                lendId = lrdl.getId();
            }
            labRoomDeviceService.sendMessageForDeviceLending(lendId, 3+"", 2);
        }
        return "success";
    }

    /****************************************************************************
     * @功能：系主任审核设备出借申请单
     * @作者：周志辉
     * @时间：2017-10-16
     ****************************************************************************/
    @RequestMapping("/device/auditDepartmentDeviceLending")
    public ModelAndView auditDepartmentDeviceLending(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(idKey);
        mav.setViewName("/device/lab_room_device/auditDepartmentDeviceLending.jsp");
        if(lrdl==null){
            mav.addObject("noLabRoomDeviceLending", 1);
            return mav;
        }
        // 找到流水号
        String lendBatch = lrdl.getLendBatch();
        // 找到流水号下的设备借用
        List<LabRoomDeviceLending> lends = labRoomDeviceService.getDeviceLendingByBatchWithoutReject(lendBatch);
        mav.addObject("lends", lends);

        mav.addObject("lrdl", lrdl);
        mav.addObject("idKey", idKey);
        mav.addObject("user", shareService.getUser());
        mav.addObject("username", shareService.getUser().getUsername());
        mav.addObject("result", labRoomDeviceService.getAuditResultMap());
        mav.addObject("lrdlr", new LabRoomDeviceLendingResult());
        return mav;
    }

    /****************************************************************************
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @功能：保存系主任审核的申请单
     * @作者：周志辉
     * @时间：1017-10-16
     ****************************************************************************/
    @RequestMapping("/device/saveDepartmentDeviceLending")
    public @ResponseBody String saveDepartmentDeviceLending(HttpServletRequest request, 
    		Integer[] array_ok, Integer[] array_refuse) throws NoSuchAlgorithmException, InterruptedException {
    	String remark = request.getParameter("remark");
    	if (!EmptyUtil.isObjectEmpty(array_ok) && array_ok.length>0) {
    	    Integer lendId = 0;
			for (Integer id : array_ok) {
				// 新建并保存审核结果
				LabRoomDeviceLendingResult l = new LabRoomDeviceLendingResult();
				LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(id);
				l.setLabRoomDeviceLending(lrdl);
				l.setUser(shareService.getUser());
				l.setRemark(remark);
				CDictionary cDictionary = new CDictionary();
				cDictionary.setId(615);// 615审核通过
				l.setCDictionary(cDictionary);
				labRoomDeviceLendingResultDAO.store(l);
				// 根据结果和借用的id处理后续消息、状态等机制
				labRoomDeviceService.createAuditMessageByLendId(id, 2+"");
				lendId = id;
			}
			labRoomDeviceService.sendMessageForDeviceLending(lendId, 2+"", 1);
		}
    	if (!EmptyUtil.isObjectEmpty(array_refuse) && array_refuse.length>0) {
    	    Integer lendId = 0;
			for (Integer id : array_refuse) {
				// 新建并保存审核结果
				LabRoomDeviceLendingResult l = new LabRoomDeviceLendingResult();
				LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(id);
				l.setLabRoomDeviceLending(lrdl);
				l.setUser(shareService.getUser());
				l.setRemark(remark);
				CDictionary cDictionary = new CDictionary();
				cDictionary.setId(616);// 616审核拒绝
				l.setCDictionary(cDictionary);
				labRoomDeviceLendingResultDAO.store(l);
				// 根据结果和借用的id处理后续消息、状态等机制
				labRoomDeviceService.createAuditMessageByLendId(id, 3+"");
				lendId = lrdl.getId();
			}
            labRoomDeviceService.sendMessageForDeviceLending(lendId, 3+"", 1);
		}
        return "success";
    }

    /****************************************************************************
     * @功能：实训室主任审核设备出借申请单
     * @作者：周志辉
     * @时间：2017-10-16
     ****************************************************************************/
    @RequestMapping("/device/auditLabRoomHeadDeviceLending")
    public ModelAndView auditLabRoomHeadDeviceLending(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(idKey);
        mav.setViewName("/device/lab_room_device/auditLabRoomHeadDeviceLending.jsp");
        if(lrdl==null){
            mav.addObject("noLabRoomDeviceLending", 1);
            return mav;
        }
        // 找到流水号
        String lendBatch = lrdl.getLendBatch();
        // 找到流水号下的设备借用
        List<LabRoomDeviceLending> lends = labRoomDeviceService.getDeviceLendingByBatchWithoutReject(lendBatch);
        mav.addObject("lends", lends);
        mav.addObject("lrdl", lrdl);
        mav.addObject("idKey", idKey);
        mav.addObject("user", shareService.getUser());
        mav.addObject("labRoomHeads", shareService.findAllLabRoomtHead());
        mav.addObject("result", labRoomDeviceService.getAuditResultMap());
        mav.addObject("lrdlr", new LabRoomDeviceLendingResult());
        return mav;
    }

    /****************************************************************************
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @功能：保存实训室主任审核的申请单
     * @作者：周志辉
     * @时间：1017-10-16
     ****************************************************************************/
    @RequestMapping("/device/saveLabRoomHeadDeviceLending")
    public @ResponseBody String saveLabRoomHeadDeviceLending(HttpServletRequest request, 
            Integer[] array_ok, Integer[] array_refuse) throws NoSuchAlgorithmException, InterruptedException {
        String remark = request.getParameter("remark");
        if (!EmptyUtil.isObjectEmpty(array_ok) && array_ok.length>0) {
            Integer lendId = 0;
            for (Integer id : array_ok) {
                // 新建并保存审核结果
                LabRoomDeviceLendingResult l = new LabRoomDeviceLendingResult();
                LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(id);
                l.setLabRoomDeviceLending(lrdl);
                l.setUser(shareService.getUser());
                l.setRemark(remark);
                CDictionary cDictionary = new CDictionary();
                cDictionary.setId(615);// 615审核通过
                l.setCDictionary(cDictionary);
                labRoomDeviceLendingResultDAO.store(l);
                // 根据结果和借用的id处理后续消息、状态等机制
                labRoomDeviceService.createAuditMessageByLendIdHead(id, 2+"");
                lendId = id;
            }
            labRoomDeviceService.sendMessageForDeviceLending(lendId, 2+"", 3);
        }
        if (!EmptyUtil.isObjectEmpty(array_refuse) && array_refuse.length>0) {
            Integer lendId = 0;
            for (Integer id : array_refuse) {
                // 新建并保存审核结果
                LabRoomDeviceLendingResult l = new LabRoomDeviceLendingResult();
                LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(id);
                l.setLabRoomDeviceLending(lrdl);
                l.setUser(shareService.getUser());
                l.setRemark(remark);
                CDictionary cDictionary = new CDictionary();
                cDictionary.setId(616);// 616审核拒绝
                l.setCDictionary(cDictionary);
                labRoomDeviceLendingResultDAO.store(l);
                // 根据结果和借用的id处理后续消息、状态等机制
                labRoomDeviceService.createAuditMessageByLendIdHead(id, 3+"");
                lendId = id;
            }
            labRoomDeviceService.sendMessageForDeviceLending(lendId, 2+"", 3);
        }
        return "success";
    }

    /****************************************************************************
     * @功能：审核设备领出申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/auditDeviceKeeping")
    public ModelAndView auditDeviceKeeping(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(idKey);
        mav.addObject("lrdl", lrdl);
        mav.addObject("idKey", idKey);
        mav.addObject("user", shareService.getUser());
        mav.addObject("result", labRoomDeviceService.getAuditResultMap());
        mav.addObject("lrdlr", new LabRoomDeviceLendingResult());
        mav.setViewName("/device/lab_room_device/auditDeviceKeeping.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：保存审核的申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/saveAuditDeviceLending")
    public String saveAuditDeviceLending(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer idKey) {
        LabRoomDeviceLendingResult l = labRoomDeviceLendingResultDAO.store(lrdlr);
        LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(idKey);
        LabRoomDevice lrd = lrdl.getLabRoomDevice();
		/*CDeviceStatus cds = new CDeviceStatus();
		cds.setId(5);*/
        CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "5");
        lrd.setCDictionaryByDeviceStatus(cds);
        labRoomDeviceDAO.store(lrd);
        if ("2".equals(l.getCDictionary().getCNumber())) {
			/*CLendingStatus cls = new CLendingStatus();
			cls.setId(1);*/
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "1");
            lrdl.setCDictionary(cls);
            labRoomDeviceLendingDAO.store(lrdl);
        }
        return "redirect:/device/deviceLendList?page=1";
    }

    /****************************************************************************
     * @功能：保存审核的申请单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/saveAuditDeviceKeeping")
    public String saveAuditDeviceKeeping(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer idKey) {
        LabRoomDeviceLendingResult l = labRoomDeviceLendingResultDAO.store(lrdlr);
        LabRoomDeviceLending lrdl = labRoomDeviceService.findDeviceLendingById(idKey);
        LabRoomDevice lrd = lrdl.getLabRoomDevice();
        CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "6");
        lrd.setCDictionaryByDeviceStatus(cds);
        labRoomDeviceDAO.store(lrd);
        if ("2".equals(l.getCDictionary().getCNumber())) {
			/*CLendingStatus cls = new CLendingStatus();
			cls.setId(1);
			lrdl.setCLendingStatus(cls);*/
            CDictionary cls = shareService.getCDictionaryByCategory("c_lending_status", "1");
            lrdl.setCDictionary(cls);
            labRoomDeviceLendingDAO.store(lrdl);
        }

        return "redirect:/device/deviceKeepList?page=1";
    }

    /****************************************************************************
     * @功能：审核通过的借用结果
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/passDeviceLendList")
    public ModelAndView passDeviceLendList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        LabRoomDeviceLending lrdl = new LabRoomDeviceLending();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getPassLendingTotals(lrdlr, request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        List<LabRoomDeviceLending> devicelendingList = labRoomDeviceService.findAllPassLending(page, pageSize, request);
        // 根据分页信息查询出来的记录
//		List<LabRoomDeviceLendingResult> deviceLendList = labRoomDeviceService.findAllPassLending(lrdlr, page, pageSize,request);
        mav.addObject("deviceLendList", devicelendingList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        mav.addObject("lendBatch", request.getParameter("lendBatch"));
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.addObject("projectName", pConfigDTO.PROJECT_NAME);
//		mav.addObject("labRoomAdmins",lrdlr.getLabRoomDeviceLending().getLabRoomDevice().getLabRoom().getLabRoomAdmins());
        mav.setViewName("/device/lab_room_device/passDeviceLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：审核通过的领用结果
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/passDeviceKeepList")
    public ModelAndView passDeviceKeepList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getPassKeepingTotals(lrdlr);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLendingResult> deviceLendList = labRoomDeviceService.findAllPassKeeping(lrdlr, page, pageSize);
        mav.addObject("deviceLendList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/passDeviceKeepList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：审核被拒绝的借用结果
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/rejectedDeviceLendList")
    public ModelAndView rejectedDeviceLendList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        LabRoomDeviceLending lrdl = new LabRoomDeviceLending();
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getRejectedLendingTotals(lrdlr, request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
//		LabRoomDeviceLendingResult labRoomDeviceLendingResult=new LabRoomDeviceLendingResult();
        List<LabRoomDeviceLending> devicelendingRejectedList = new ArrayList<LabRoomDeviceLending>();
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLendingResult> deviceLendList = labRoomDeviceService.findAllRejectedLending(lrdlr, page, pageSize, request);
        for (LabRoomDeviceLendingResult l : deviceLendList) {

            devicelendingRejectedList.add(l.getLabRoomDeviceLending());
        }
        mav.addObject("deviceLendList", devicelendingRejectedList);
//		mav.addObject("deviceLendList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        mav.addObject("lendBatch", request.getParameter("lendBatch"));
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/rejectedDeviceLending.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：审核被拒绝的领用结果
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/rejectedDeviceKeepList")
    public ModelAndView rejectedDeviceKeepList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getRejectedKeepingTotals(lrdlr);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLendingResult> deviceLendList = labRoomDeviceService.findAllRejectedKeeping(lrdlr, page, pageSize);
        mav.addObject("deviceLendList", deviceLendList);

//        mav.addObject("cid", cid);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/rejectedDeviceKeeping.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：已归还的设备借用审核单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/returnedDeviceLendList")
    public ModelAndView returnedDeviceLendList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getReturnedLendingTotals(lrdlr, request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLendingResult> deviceLendList = labRoomDeviceService.findAllReturnedLending(lrdlr, page, pageSize, request);
        mav.addObject("deviceLendList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/returnedDeviceLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：所有设备借用
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/allDeviceLendList")
    public ModelAndView allDeviceLendList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getAllLendTotals(request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        mav.addObject("deviceName", request.getParameter("deviceName"));
//		String deviceName=request.getParameter("deviceName");
//		System.out.println(deviceName);
        // 根据分页信息查询出来的记录;
        List<SchoolDevice> deviceLendList = labRoomDeviceService.findAllLendingList(page, pageSize, request);
        mav.addObject("deviceLendList", deviceLendList);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/allDeviceLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：已归还的设备领用审核单
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/returnedDeviceKeepList")
    public ModelAndView returnedDeviceKeepList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getReturnedKeepingTotals(lrdlr);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLendingResult> deviceLendList = labRoomDeviceService.findAllReturnedKeeping(lrdlr, page, pageSize);
        mav.addObject("deviceLendList", deviceLendList);

//        mav.addObject("cid", cid);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/returnedDeviceKeepList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：借用设备的归还
     * @作者：李鹏翔
     ****************************************************************************/
    @ResponseBody
    @RequestMapping("/device/returnDeviceLending")
    public String returnDeviceLending(@RequestParam Integer idKey, HttpServletRequest request) throws ParseException {
    	String remark = request.getParameter("remark");
    	String backtime = request.getParameter("backtime");
        labRoomDeviceService.returnDeviceLending(idKey, remark,backtime);
        return "success";
    }

    /****************************************************************************
     * @功能：设备维修管理
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/deviceRepairList")
    public ModelAndView deviceRepairList(@ModelAttribute LabRoomDeviceRepair lrdr, @RequestParam Integer page, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getAllRepairTotals(lrdr);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceRepair> deviceLendList = labRoomDeviceService.findAllRepairs(lrdr, page, pageSize);
        mav.addObject("deviceRepairList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 获得当前设备所在的实验中心和实验室
        LabRoom lr = new LabRoom();
        if (lrdr.getId() != null) {
            lr = lrdr.getLabRoomDevice().getLabRoom();
        }
//        LabCenter lc = labCenterDAO.findLabCenterByPrimaryKey(cid);
//        // 判断当前登录人是够有添加维修记录的权限
        List<User> us = labRoomDeviceService.findAdminByLrid(lr.getId());
//        User us1 = lc.getUserByCenterManager();
//        if (us1.getUsername().equals(user.getUsername())) {
//            mav.addObject("admin", true);
//        }
        if (us.size() > 0) {
            for (User u : us) {
                if (u.getUsername().equals(user.getUsername())) {
                    mav.addObject("admin", true);
                }
            }
        }
        mav.setViewName("/device/lab_room_device/deviceRepairList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：保存设备报修
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/saveNewDeviceRepair")
    public String saveNewDeviceRepair(@ModelAttribute LabRoomDeviceRepair lrdr) {
        labRoomDeviceService.saveNewDeviceRepair(lrdr);
        return "redirect:/device/deviceRepairList?page=1";
    }

    /****************************************************************************
     * @功能：报修状态设备列表
     * @作者：李鹏翔
     * 周志辉 2017-08-18 修改
     ****************************************************************************/
    @RequestMapping("/device/applyDeviceRepairList")
    public ModelAndView applyDeviceRepairList(@ModelAttribute LabRoomDeviceRepair lrdr, @RequestParam Integer page, Integer roomId, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("roomId", roomId);
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdr);
        // 查询出所有的设备设备预约记录

        int pageSize = 10;// 每页10条记录
        int totalRecords = labRoomDeviceService.findApplyRepairs(lrdr, 1, -1).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceRepair> deviceLendList = labRoomDeviceService.findApplyRepairs(lrdr, page, pageSize);
        mav.addObject("deviceRepairList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("labRoomDevices",labRoomDeviceDAO.findAllLabRoomDevices());
        // 当前登录人
		User user = shareService.getUser();
		mav.addObject("user", user);
		//获取当前实验中心的所有老师
        //List<User> users = shareService.findUserByCidAndAuthorities(cid,2);
        //获取所有老师
        Map users = shareService.getUsersMap();
        mav.addObject("users", users);

//		// 获得当前设备所在的实验中心和实验室
//		LabRoom lr = new LabRoom();
//		if (lrdr.getId() != null) {
//			lr = lrdr.getLabRoomDevice().getLabRoom();
//		}
//		LabCenter lc = labCenterDAO.findLabCenterByPrimaryKey(cid);
//		mav.addObject("cid", cid);
        // 判断当前登录人是够有添加维修记录的权限
//		List<User> us = labRoomDeviceService.findAdminByLrid(lr.getId());
//		User us1 = lc.getUserByCenterManager();
//		if (us1.getUsername().equals(user.getUsername())) {
//			mav.addObject("admin", true);
//		}
//		if (us.size() > 0) {
//			for (User u : us) {
//				if (u.getUsername().equals(user.getUsername())) {
//					mav.addObject("admin", true);
//				}
//			}
//		}
        mav.setViewName("/device/lab_room_device/applyDeviceRepairList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：报修状态设备列表
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/passDeviceRepairList")
    public ModelAndView passDeviceRepairList(@ModelAttribute LabRoomDeviceRepair lrdr, @RequestParam Integer page) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdr);
        // 查询出所有的设备设备预约记录

        int pageSize = 10;// 每页10条记录
        int totalRecords = labRoomDeviceService.findPassRepairs(lrdr, 1, -1).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceRepair> deviceLendList = labRoomDeviceService.findPassRepairs(lrdr, page, pageSize);
        mav.addObject("deviceRepairList", deviceLendList);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/passDeviceRepairList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：报修状态设备列表
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/rejectedDeviceRepairList")
    public ModelAndView rejectedDeviceRepairList(@ModelAttribute LabRoomDeviceRepair lrdr, @RequestParam Integer page) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdr);
        // 查询出所有的设备设备预约记录

        int pageSize = 10;// 每页10条记录
        int totalRecords = labRoomDeviceService.findRejectedRepairs(lrdr, 1, -1).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceRepair> deviceLendList = labRoomDeviceService.findRejectedRepairs(lrdr, page, pageSize);
        mav.addObject("deviceRepairList", deviceLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/rejectedDeviceRepairList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备维修
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/repairDevice")
    public ModelAndView repairDevice(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("lrdr", labRoomDeviceRepairDAO.findLabRoomDeviceRepairByPrimaryKey(idKey));
        //mav.addObject("status", cLabRoomDeviceRepairStatusDAO.findAllCLabRoomDeviceRepairStatuss());
        mav.addObject("status", shareService.getCDictionaryData("c_lab_room_device_repair_status"));

        mav.setViewName("/device/lab_room_device/repairDevice.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：保存设备维修记录
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/saveNewRepair")
    public String saveNewRepair(@ModelAttribute LabRoomDeviceRepair lrdr) {
        int id = lrdr.getId();
        LabRoomDeviceRepair repair = labRoomDeviceRepairDAO.findLabRoomDeviceRepairByPrimaryKey(id);
        repair.setCDictionaryByStatusId(lrdr.getCDictionaryByStatusId());
        repair.setRepairRecords(lrdr.getRepairRecords());
        repair.setRestoreTime(Calendar.getInstance());
        LabRoomDevice lrd = repair.getLabRoomDevice();
        if ("3".equals(lrdr.getCDictionaryByStatusId().getCNumber())) {
			/*CDeviceStatus cds = new CDeviceStatus();
			cds.setId(1);*/
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
            lrd.setCDictionaryByDeviceStatus(cds);
        } else {
			/*CDeviceStatus cds = new CDeviceStatus();
			cds.setId(4);
			lrd.setCDeviceStatus(cds);*/
            CDictionary cds = shareService.getCDictionaryByCategory("c_lab_room_device_status", "4");
            lrd.setCDictionaryByDeviceStatus(cds);

        }
        labRoomDeviceDAO.store(lrd);
        labRoomDeviceService.saveNewDeviceRepair(repair);
        return "redirect:/device/applyDeviceRepairList?page=1";
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理---查看
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/viewDeviceSetting")
    public ModelAndView viewDeviceSetting(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);
        mav.setViewName("/device/lab_room_device_access/viewDeviceSetting.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：确认维修
     * @作者：李鹏翔
     ****************************************************************************/
    @RequestMapping("/device/sureDeviceRepair")
    public String sureDeviceRepair(@RequestParam Integer idKey) {
        LabRoomDeviceRepair ldr = labRoomDeviceRepairDAO.findLabRoomDeviceRepairByPrimaryKey(idKey);
		/*CLabRoomDeviceRepairStatus status = new CLabRoomDeviceRepairStatus();
		status.setId(3);*/
        CDictionary status = shareService.getCDictionaryByCategory("c_lab_room_device_repair_status", "3");
//		ldr.setCLabRoomDeviceRepairStatus(status);
        ldr.setCDictionaryByStatusId(status);
        labRoomDeviceRepairDAO.store(ldr);
        return "redirect:/passDeviceRepairList?page=1";
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理---设置
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/editDeviceSetting")
    public ModelAndView editDeviceSetting(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);

        // 获取所有单选的结果集（是/否）
        /*Set<CActive> CActives = labRoomService.findAllCActive();*/
        List<CDictionary> CActives = shareService.getCDictionaryData("c_active");
        mav.addObject("CActives", CActives);
        // 预约形式
        /*Set<CAppointmentType> CAppointmentTypes = cAppointmentTypeDAO.findAllCAppointmentTypes();*/
        List<CDictionary> CAppointmentTypes = shareService.getCDictionaryData("c_appointment_type");
        mav.addObject("CAppointmentTypes", CAppointmentTypes);
        /*Set<CLabAccessType> CLabAccessTypes = cLabAccessTypeDAO.findAllCLabAccessTypes();*/
        List<CDictionary> CLabAccessTypes = shareService.getCDictionaryData("c_lab_access_type");
        mav.addObject("CLabAccessTypes", CLabAccessTypes);
        mav.setViewName("/device/lab_room_device_access/editDeviceSetting.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理---保存设置
     * @作者：李小龙
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/device/saveDeviceSetting")
    public ModelAndView saveDeviceSetting(@ModelAttribute LabRoomDevice device) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(device.getId());
        if (device.getCDictionaryByAllowLending() != null) {
            labDevice.setCDictionaryByAllowLending(device.getCDictionaryByAllowLending());
        }
        if (device.getCDictionaryByAllowAppointment() != null) {
            labDevice.setCDictionaryByAllowAppointment(device.getCDictionaryByAllowAppointment());
        }
        if (device.getCDictionaryByAppointmentType() != null) {
            labDevice.setCDictionaryByAppointmentType(device.getCDictionaryByAppointmentType());
        }
        if (device.getCDictionaryByIsAudit() != null) {
            labDevice.setCDictionaryByIsAudit(device.getCDictionaryByIsAudit());
        }
        if (device.getCDictionaryByTeacherAudit() != null) {
            labDevice.setCDictionaryByTeacherAudit(device.getCDictionaryByTeacherAudit());
        }
        if (device.getCDictionaryByManagerAudit() != null) {
            labDevice.setCDictionaryByManagerAudit(device.getCDictionaryByManagerAudit());
        }
        if (device.getCActiveByLabManagerAudit() != null) {
            labDevice.setCActiveByLabManagerAudit(device.getCActiveByLabManagerAudit());
        }
        if (device.getCDictionaryByAllowSecurityAccess() != null) {
            labDevice.setCDictionaryByAllowSecurityAccess(device.getCDictionaryByAllowSecurityAccess());
        }
        if (device.getCDictionaryBySecurityAccessType() != null) {
            labDevice.setCDictionaryBySecurityAccessType(device.getCDictionaryBySecurityAccessType());
        }
        LabRoomDevice d = labRoomDeviceService.save(labDevice);
        mav.setViewName("redirect:/device/listLabRoomDevice?page=1");
        return mav;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理---编辑（化工学院） @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/editDeviceInfo")
    public ModelAndView editDeviceInfo(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);
        // 设备管理员
        User user = shareService.getUser();
        List<User> users = shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber());
        mav.addObject("users", users);
        // 设备状态
        /*Set<CDeviceStatus> stutus = cDeviceStatusDAO.findAllCDeviceStatuss();*/
        List<CDictionary> stutus = shareService.getCDictionaryData("c_lab_room_device_status");
        mav.addObject("stutus", stutus);
        // 所属类型
        /*Set<CDeviceType> types = cDeviceTypeDAO.findAllCDeviceTypes();*/
        List<CDictionary> types = shareService.getCDictionaryData("c_lab_room_device_type");
        mav.addObject("types", types);
        // 收费标准
        /*Set<CDeviceCharge> charges = cDeviceChargeDAO.findAllCDeviceCharges();*/
        List<CDictionary> charges = shareService.getCDictionaryData("c_lab_room_device_charge");
        mav.addObject("charges", charges);

        // 当前@时间所属的学期
        Calendar time = Calendar.getInstance();
        SchoolTerm term = shareService.getBelongsSchoolTerm(time);
        // 根据学期和设备查询培训
        List<LabRoomDeviceTraining> trainings = labRoomDeviceService.findLabRoomDeviceTrainingByTermId(term.getId(), id);
        mav.addObject("trainings", trainings);
        // 培训表单的对象
        mav.addObject("train", new LabRoomDeviceTraining());
        // 培训教师

        mav.setViewName("/device/specialAcademy/editDeviceInfo.jsp");

        return mav;
    }

    /****************************************************************************
     * @功能：实验室设备管理---设备管理---查看（化工学院） @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/viewDeviceInfo")
    public ModelAndView viewDeviceInfo(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/viewDeviceInfo.jsp");

        return mav;
    }

    /****************************************************************************
     * @功能：设备图片（化工学院） @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deviceImage")
    public ModelAndView deviceImage(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/deviceImage.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备视频（化工学院） @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deviceVideo")
    public ModelAndView deviceVideo(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/deviceVideo.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备图片（化工学院）--查看
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/viewDeviceImage")
    public ModelAndView viewDeviceImage(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/viewDeviceImage.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备视频（化工学院）--查看
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/viewDeviceVideo")
    public ModelAndView viewDeviceVideo(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/viewDeviceVideo.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备二维码（化工学院）--查看
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/viewDimensionalCode")
    public ModelAndView viewDimensionalCode(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/viewDimensionalCode.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备视频（化工学院） @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/dimensionalCode")
    public ModelAndView dimensionalCode(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/dimensionalCode.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：根据设备id查询培训
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deviceTraining")
    public ModelAndView deviceTraining(@ModelAttribute LabRoomDeviceTraining train, @RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 培训查询表单的对象
        mav.addObject("train", train);
        // 设备id对应的设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 根据设备id和培训对象查询培训
        List<LabRoomDeviceTraining> trainList = labRoomDeviceService.findLabRoomDeviceTrainingByDeviceId(train, deviceId);

        mav.addObject("trainList", trainList);
        // 当前登录人是否参加过培训
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 第一个培训的培训名单
        if (trainList.size() > 0) {
            for (LabRoomDeviceTraining t : trainList) {
                int i = t.getId();
                List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(i);
                mav.addObject("peoples", peoples);
                int flag = 1;// 默认为1：未参加，0为已参加
                for (LabRoomDeviceTrainingPeople p : peoples) {
                    if (p.getUser().getUsername().equals(user.getUsername())) {
                        flag = 0;
                        break;
                    } else {
                        flag = 1;
                    }
                }
                map.put(i, flag);
            }

        }
        mav.addObject("map", map);
        // 添加培训表单的培训教师
        List<User> users = shareService.findTheSameCollegeTeacher(user.getSchoolAcademy().getAcademyNumber());
        mav.addObject("users", users);

        mav.setViewName("/device/specialAcademy/deviceTraining.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：查看培训计划
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/viewDeviceTraining")
    public ModelAndView viewDeviceTraining(@ModelAttribute LabRoomDeviceTraining train, @RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 培训查询表单的对象
        mav.addObject("train", train);
        // 设备id对应的设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 根据设备id和培训对象查询培训
        List<LabRoomDeviceTraining> trainList = labRoomDeviceService.findLabRoomDeviceTrainingByDeviceId(train, deviceId);
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("trainList", trainList);
        // 当前登录人是否参加过培训
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 第一个培训的培训名单
        if (trainList.size() > 0) {
            for (LabRoomDeviceTraining t : trainList) {
                int i = t.getId();
                List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(i);
                mav.addObject("peoples", peoples);
                int flag = 1;// 默认为1：未参加，0为已参加
                for (LabRoomDeviceTrainingPeople p : peoples) {
                    if (p.getUser().getUsername().equals(user.getUsername())) {
                        flag = 0;
                        break;
                    } else {
                        flag = 1;
                    }
                }
                map.put(i, flag);
            }

        }
        mav.addObject("map", map);

        mav.setViewName("/device/specialAcademy/viewDeviceTraining.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：培训预约管理主页面
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/listLabRoomDeviceTraining")
    public ModelAndView listLabRoomDeviceTraining(@ModelAttribute LabRoomDeviceTraining labRoomDeviceTraining, @RequestParam Integer currpage, int isTeacher) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;
        // 培训预约
        List<LabRoomDeviceTraining> labRoomDeviceTrainings = labRoomDeviceService.findLabRoomDeviceTrainingByUser(null, null, currpage, pageSize);
        // 总记录数
        int totalRecords = labRoomDeviceService.findLabRoomDeviceTrainingByUser(null, null, 1, -1).size();
        mav.addObject("labRoomDeviceTrainings", labRoomDeviceTrainings);
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("isTeacher", isTeacher);
        mav.addObject("user", shareService.getUser());

        mav.setViewName("/device/lab_room_device_training/listLabRoomDeviceTraining.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：取消培训预约
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/cancleTraining")
    public ModelAndView cancleTraining(@RequestParam int id, int isTeacher, int flag) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        // 取消培训预约
        labRoomDeviceService.cancleTraining(id);
        Set<LabRoomDeviceTrainingPeople> peoples = train.getLabRoomDeviceTrainingPeoples();
        train.setJoinNumber(peoples.size());
        labRoomDeviceTrainingDAO.store(train);
        String viewName = "";
        if (flag == 0) {
            viewName = "redirect:/personal/message/mySelfInfo";
        } else {
            viewName = "redirect:/device/listLabRoomDeviceTraining?currpage=1&isTeacher=" + isTeacher;
        }
        mav.setViewName(viewName);
        return mav;
    }

    /****************************************************************************
     * @功能：根据设备id查询培训
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/findTrainingPeopleByTrainId")
    public ModelAndView findTrainingPeopleByTrainId(@RequestParam Integer id) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        mav.addObject("train", train);
        // 培训所属的设备
        mav.addObject("device", train.getLabRoomDevice());
        List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(id);
        mav.addObject("peoples", peoples);
        // 培训结果
        //Set<CTrainingResult> results = cTrainingResultDAO.findAllCTrainingResults();
        List<CDictionary> CActives = shareService.getCDictionaryData("c_training_result");
        mav.addObject("results", CActives);
        mav.setViewName("/device/lab_room_device_training/peopleList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：批量生成二维码
     * @作者：李小龙
     * @throws Exception
     ****************************************************************************/
    @RequestMapping("/device/generateDimensionalCode")
    public @ResponseBody
    String generateDimensionalCode(HttpServletRequest request, @RequestParam int lab_id) {

        // System.out.println(request.getRequestURL());//http://localhost/zjcclims/device/generateDimensionalCode
        // System.out.println(request.getContextPath());///zjcclims
        // System.out.println(request.getServerName());//localhost
        String serverName = request.getServerName() + ":" + request.getServerPort();
        String data = "success";
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(lab_id);
        Set<LabRoomDevice> deviceList = labRoom.getLabRoomDevices();
        for (LabRoomDevice d : deviceList) {
            String url = "";
            try {
                url = shareService.getDimensionalCode(d, serverName);
            } catch (Exception e) {
                data = "error";
                break;
            }
            d.setDimensionalCode(url);
            labRoomDeviceDAO.store(d);
        }
        return data;
    }

    /****************************************************************************
     * @功能：设备文档（化工学院） @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deviceDocument")
    public ModelAndView deviceDocument(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/deviceDocument.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备文档（化工学院）--查看
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/viewDeviceDocument")
    public ModelAndView viewDeviceDocument(@RequestParam Integer deviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.setViewName("/device/specialAcademy/viewDeviceDocument.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：给设备上传文档
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/deviceDocumentUpload")
    public @ResponseBody
    String deviceDocumentUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {

        labRoomDeviceService.deviceDocumentUpload(request, response, id, 2);
        return "ok";
    }

    /****************************************************************************
     * @功能：下载设备文档
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/downloadDocument")
    public void downloadDocument(HttpServletRequest request, HttpServletResponse response, int id) {
        // id对应的文档
        CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
        labRoomDeviceService.downloadFile(doc, request, response);
    }

    /**
     * @param labRoomDevice、page、cid
     * @comment：设备管理员批量设置首页
     * @return：
     * @author：叶明盾 @date：2015-10-29 上午11:00:33
     */
    @SuppressWarnings("unused")
    @RequestMapping("/device/batchDeviceAdmin")
    public ModelAndView batchDeviceAdmin(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer page, @ModelAttribute("selected_academy") String acno) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 中心所属学院
//        String academy = "";
//        if (cid != null & cid != -1) {
//            // 中心所属学院
//            academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
//        } else {
//            academy = shareService.getUser().getSchoolAcademy().getAcademyNumber();
//        }
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        // 实验室
        List<LabRoom> rooms = labRoomService.findLabRoomByLabCenterid(acno, 1);
        mav.addObject("rooms", rooms);
        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        int pageSize = 30;// 每页20条记录
        // 查询出来的总记录条数
        int totalRecords = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, acno, 1);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", page, pageSize, 1);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", 1, -1, 1);
        mav.addObject("listLabRoomDeviceAll", listLabRoomDeviceAll);
        // 实验室管理员
        mav.addObject("admin", new LabRoomAdmin());
        // 实验中心id
//        mav.addObject("cid", cid);
        // 分页相关参数
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("/device/batchDeviceAdmin.jsp");
        return mav;
    }

    /**
     * @param cname
     * @param username
     * @param cid
     * @param page
     * @comment：AJAX 根据姓名、工号查询当前登录人所在学院的用户
     * @return：
     * @author：叶明盾 @date：2015-10-28 下午10:23:45
     */
    @RequestMapping("/device/findUserForDeviceAdmin")
    public @ResponseBody
    String findUserForDeviceAdmin(@RequestParam String cname, String username, Integer cid, Integer page) {

        User u = shareService.getUser();
        String academyNumber = "";
        if (u.getSchoolAcademy() != null) {
            academyNumber = u.getSchoolAcademy().getAcademyNumber();
        }
        User user = new User();
        user.setCname(cname);
        user.setUsername(username);

        // 分页开始
        int totalRecords = labRoomService.findUserByUserAndAcademy(user, cid, academyNumber);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<User> userList = labRoomService.findUserByUserAndAcademy(user, cid, academyNumber, page, pageSize);
        String s = "";
        for (User d : userList) {
            String academy = "";
            if (d.getSchoolAcademy() != null) {
                academy = d.getSchoolAcademy().getAcademyName();
            }
            s += "<tr>" + "<td><input type='radio' name='CK_name' value='" + d.getUsername() + "'/></td>" + "<td>" + d.getCname() + "</td>" + "<td>" + d.getUsername() + "</td>" + "<td>" + academy + "</td>" + "</tr>";
        }
        s += "<tr><td colspan='6'>" + "<a href='javascript:void(0)' onclick='firstPage(1);'>" + "首页" + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='previousPage(" + page + ");'>" + "上一页" + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='nextPage(" + page + "," + pageModel.get("totalPage") + ");'>" + "下一页" + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='lastPage(" + pageModel.get("totalPage") + ");'>" + "末页" + "</a>&nbsp;" + "当前第" + page + "页&nbsp; 共" + pageModel.get("totalPage") + "页  " + totalRecords + "条记录" + "</td></tr>";
        return htmlEncode(s);
    }

    /**
     * @param array、typeId
     * @comment：批量保存实验室管理员
     * @return：
     * @author：叶明盾 @date：2015-10-28 下午10:17:00
     */
    @RequestMapping("/device/saveBatchDeviceAdmin")
    public ModelAndView saveBatchDeviceAdmin(@RequestParam String[] deviceArray, String[] array, Integer typeId) {
        ModelAndView mav = new ModelAndView();

        for (String a : deviceArray) {
            // devceId对应的设备
            LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(Integer.valueOf(a));

            for (String i : array) {
                // username对应的用户
                User u = userDAO.findUserByPrimaryKey(i);
                device.setUser(u);
                labRoomDeviceDAO.store(device);
                // LabRoomAdmin admin=new LabRoomAdmin();
                // admin.setLabRoom(room);
                // admin.setUser(u);
                // admin.setTypeId(typeId);
                // labRoomDeviceDAO.store(admin);
                // 给用户赋予权限
                // Set<Authority> ahths=u.getAuthorities();
                // Authority
                // at=authorityDAO.findAuthorityByPrimaryKey(10);//设备管理员
                // ahths.add(at);
                // u.setAuthorities(ahths);
                // userDAO.store(u);
            }
        }
        mav.setViewName("redirect:/device/batchDeviceAdmin?page=1");
        return mav;
    }

    /****************************************************************************
     * 功能：AJAX 根据姓名、工号查询当前登录人所在学院的用户 作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/findStudentByCnameAndUsername")
    public @ResponseBody
    String findStudentByCnameAndUsername(@RequestParam String cname, String username, Integer page, Integer deviceId) {

        User u = shareService.getUser();
        String academyNumber = "";
        if (u.getSchoolAcademy() != null) {
            academyNumber = u.getSchoolAcademy().getAcademyNumber();
        }
        User user = new User();
        user.setCname(cname);
        user.setUsername(username);

        // 分页开始
        int totalRecords = labRoomDeviceService.findStudentByCnameAndUsername(user, academyNumber, deviceId);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<User> userList = labRoomDeviceService.findStudentByCnameAndUsername(user, academyNumber, deviceId, page, pageSize);
        String s = "";
        for (User d : userList) {
            String academy = "";
            if (d.getSchoolAcademy() != null) {
                academy = d.getSchoolAcademy().getAcademyName();
            }
            s += "<tr>" + "<td><input type='checkbox' name='CK_name' value='" + d.getUsername() + "'/></td>" + "<td>" + d.getCname() + "</td>" + "<td>" + d.getUsername() + "</td>" + "<td>" + academy + "</td>" +

                    "</tr>";
        }

        int previousPage;
        int nextPage;
        if (page == 1) {
            previousPage = page;
        } else {
            previousPage = page - 1;
        }

        if (page == (totalRecords + pageSize - 1) / pageSize) {
            nextPage = page;
        } else {
            nextPage = page + 1;
        }
        s += "<tr><td colspan='6'>" + "当前第" + page + "页&nbsp; 共" + pageModel.get("totalPage") + "页  " + totalRecords + "条记录" + "&nbsp<a href='javascript:void(0)' onclick='addStudent(1);'>" + "首页" + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='addStudent(" + previousPage + ");'>" + "上一页" + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='addStudent(" + nextPage + "," + pageModel.get("totalPage") + ");'>" + "下一页" + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='addStudent(" + (totalRecords + pageSize - 1) / pageSize + ");'>" + "末页" + "</a>&nbsp;" + "</td></tr>";
        return htmlEncode(s);
    }

    /****************************************
     * 功能：设备管理--培训计划--已通过名单列表 作者：贺子龙 日期：2016-03-22
     ***************************************/
    @RequestMapping("device/managePermitUser")
    public ModelAndView managePermitUser(@ModelAttribute LabRoomDevicePermitUsers labRoomDevicePermitUsers, @RequestParam int deviceId, int currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;
        int totalRecords = 0;
        if (labRoomDeviceService.findPermitUserByDeivceId(labRoomDevicePermitUsers, deviceId, 1, -1) != null) {
            totalRecords = labRoomDeviceService.findPermitUserByDeivceId(labRoomDevicePermitUsers, deviceId, 1, -1).size();
        }
        mav.addObject("allStudents", labRoomDeviceService.findPermitUserByDeivceId(deviceId, 1, -1));
        List<LabRoomDevicePermitUsers> students = labRoomDeviceService.findPermitUserByDeivceId(labRoomDevicePermitUsers, deviceId, currpage, pageSize);
        mav.addObject("students", students);
        mav.addObject("student", new LabRoomDevicePermitUsers());

        mav.addObject("deviceId", deviceId);
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("totalRecords", totalRecords);
        mav.setViewName("/device/managePermitUser.jsp");
        return mav;
    }

    /****************************************
     * 功能：设备管理--培训计划--已通过名单--删除 作者：贺子龙 日期：2016-03-22
     ***************************************/
    @RequestMapping("device/deletePermitUser")
    public ModelAndView deletePermitUser(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevicePermitUsers permitUser = labRoomDeviceService.findLabRoomDevicePermitUsersByPrimaryKey(id);
        String username = permitUser.getUser().getUsername();
        int deviceId = permitUser.getLabRoomDevice().getId();
        if (permitUser.getFlag() == 2) {// 集训通过的人，如果删除的话需要将集训结果置为未通过并重新计算培训的通过率
            LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
            for (LabRoomDeviceTraining training : device.getLabRoomDeviceTrainings()) {
                int trainingId = training.getId();
                List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(trainingId);
                for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : peoples) {
                    if (!labRoomDeviceTrainingPeople.getUser().getUsername().equals(username)) {// 不是当前用户，继续
                        continue;
                    } else {// 是当前用户，将培训结果设置为未通过
                        // 将集训结果置为未通过
                        //CTrainingResult result = cTrainingResultDAO.findCTrainingResultByPrimaryKey(2);
                        CDictionary result = shareService.getCDictionaryByCategory("c_training_result", "2");
                        labRoomDeviceTrainingPeople.setCDictionary(result);
                        labRoomDeviceTrainingPeopleDAO.store(labRoomDeviceTrainingPeople);
                    }
                }

                // 重新计算培训的通过率
                if (training.getCTrainingStatus().getId() == 2) {
                    // 该培训的培训人
                    Set<LabRoomDeviceTrainingPeople> trainingpeoples = training.getLabRoomDeviceTrainingPeoples();
                    // 根据培训id查询培训通过的人
                    List<LabRoomDeviceTrainingPeople> passPeoples = labRoomDeviceService.findPassLabRoomDeviceTrainingPeopleByTrainId(training.getId());
                    // 计算通过率
                    double a = passPeoples.size();
                    double b = trainingpeoples.size();
                    double c = a / b;
                    // 获取格式化对象
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    // 设置百分数精确度2即保留两位小数
                    nt.setMinimumFractionDigits(2);
                    String s = nt.format(c);
                    training.setPassRate(s);
                    labRoomDeviceTrainingDAO.store(training);
                }
            }
        }
        labRoomDeviceService.deletePermitUser(permitUser);
        mav.setViewName("redirect:/device/managePermitUser?deviceId=" + deviceId + "&currpage=1");
        return mav;
    }

    /****************************************************************************
     * 功能：保存集中培训学生后门 作者：贺子龙 日期：2016-03-22
     ****************************************************************************/
    @RequestMapping("device/savePermitUser")
    public ModelAndView savePermitUser(@RequestParam String usernameStr, @RequestParam int deviceId) {
        String[] array = usernameStr.split(";");
        ModelAndView mav = new ModelAndView();
        // id对应的培训
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        for (String i : array) {
            // username对应的用户
            LabRoomDevicePermitUsers student = new LabRoomDevicePermitUsers();
            User u = userDAO.findUserByPrimaryKey(i);
            student.setUser(u);
            student.setLabRoomDevice(device);
            student.setFlag(3);// 标记位（1 单独培训通过 2 集训通过 3 集训后门）
            labRoomDevicePermitUsersDAO.store(student);
        }
        mav.setViewName("redirect:/device/managePermitUser?deviceId=" + deviceId + "&currpage=1");
        return mav;
    }

    /************************************************************
     * 功能：判断该学生是否能进行该培训的预约 作者：贺子龙 日期：2016-03-22
     ************************************************************/
    @RequestMapping("/device/ifPermitted")
    public @ResponseBody
    String ifPermitted(@RequestParam int deviceId) {
        // 获取当前用户
        String username = shareService.getUser().getUsername();
        // 找到当前培训所对应的设备
        LabRoomDevicePermitUsers permitUser = labRoomDeviceService.findPermitUserByUsernameAndDeivce(username, deviceId);
        if (permitUser != null) {
            return "permitted";
        } else {
            LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
            for (LabRoomDeviceTraining training : device.getLabRoomDeviceTrainings()) {
                if (training.getCTrainingStatus().getId().equals(4)) {// 预约被取消
                    continue;
                }
                int trainingId = training.getId();
                List<LabRoomDeviceTrainingPeople> peoples = labRoomDeviceService.findTrainingPeoplesByTrainingId(trainingId);
                for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : peoples) {
                    if (!labRoomDeviceTrainingPeople.getUser().getUsername().equals(username)) {// 不是当前用户，继续
                        continue;
                    } else {// 是当前用户，判断是否已经通过
                        if (labRoomDeviceTrainingPeople.getCDictionary() == null) {// 未有结果
                            return "wait";
                        } else {
                            if ("1".equals(labRoomDeviceTrainingPeople.getCDictionary().getCNumber())) {
                                // 通过
                                return "permitted";
                            } else {
                                // 未通过
                                return "ok";
                            }
                        }
                    }
                }
            }
        }
        return "ok";
    }

    /****************************************************************************
     * 功能：ajax查询设备 作者：贺子龙 日期：2016-04-04
     * @throws UnsupportedEncodingException
     ****************************************************************************/
    @RequestMapping("/device/findSchoolDeviceByNameAndNumber")
    public @ResponseBody
    String findSchoolDeviceByNameAndNumber(@RequestParam int labRoomId, String deviceName, String deviceNumber, int page, @ModelAttribute("selected_academy") String acno) throws UnsupportedEncodingException {

        int pageSize = 100;
        deviceName = (java.net.URLDecoder.decode(deviceName, "UTF-8"));
        List<SchoolDevice> allSchoolDevice = schoolDeviceService.findSchoolDeviceByNameAndNumber(acno, labRoomId, deviceName, deviceNumber, page, pageSize);
        int totalRecords = schoolDeviceService.countSchoolDeviceByNameAndNumber(acno, labRoomId, deviceName, deviceNumber);

        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        String s = "";
        for (SchoolDevice device : allSchoolDevice) {
            s += "<tr>"
                    + "<td>" + device.getDeviceNumber() + "</td>"
                    + "<td>" + device.getDeviceName() + "</td>"
                    + "<td>" + device.getDevicePattern() + "</td>"
                    + "<td>" + device.getDevicePrice() + "</td>"
                    + "<td><input type='checkbox' name='CK' value='" + device.getDeviceNumber() + "'/></td>"
                    + "</tr>";

        }
        int previousPage;
        int nextPage;
        if (page == 1) {
            previousPage = page;
        } else {
            previousPage = page - 1;
        }

        if (page == (totalRecords + pageSize - 1) / pageSize) {
            nextPage = page;
        } else {
            nextPage = page + 1;
        }

        s += "<tr><td colspan='7'>" + "当前第" + page + "页&nbsp; 共" + pageModel.get("totalPage") + "页  " + totalRecords + "条记录&nbsp"
                + "<a href='javascript:void(0)' onclick='addDevices(1);'>" + "首页" + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='addDevices(" + previousPage + ");'>" + "上一页"
                + "</a>&nbsp;" + "<a href='javascript:void(0)' onclick='addDevices(" + nextPage + ");'>" + "下一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='addDevices(" + (totalRecords + pageSize - 1) / pageSize + ");'>" + "末页" + "</a>&nbsp;" + "</td></tr>";
        return shareService.htmlEncode(s);
    }

    /****************************************************************************
     * 功能：保存关联设备 作者：贺子龙 日期：2016-04-04
     ****************************************************************************/
    @RequestMapping("/device/saveInnerSameDevice")
    public String saveInnerSameDevice(@RequestParam int deviceId, String[] array) {
        // 根据id找到该实验室设备
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        SchoolDevice self = labRoomDevice.getSchoolDevice();
        // 判断该设备是否已经属于某一组合
        boolean isSeted = false;
        if (labRoomDevice.getSchoolDevice().getInnerSame() != null) {
            isSeted = true;
        }

        // 初始化innerSame,默认为0
        int innerSame = 0;
        // 关联设备关联后的名字
        String innerDeviceName = "";

        if (isSeted) {
            innerSame = labRoomDevice.getSchoolDevice().getInnerSame();
            innerDeviceName += labRoomDevice.getSchoolDevice().getInnerDeviceName() + "[" + labRoomDevice.getSchoolDevice().getDeviceNumber() + "]" + " ";
        } else {
            innerSame = schoolDeviceService.maxSchoolDeviceSet() + 1;
            innerDeviceName += self.getDeviceName() + "[" + self.getDeviceNumber() + "]" + " ";
        }

        for (String deviceNumber : array) {
            SchoolDevice schoolDevice = schoolDeviceService.findSchoolDeviceByPrimaryKey(deviceNumber);
            innerDeviceName += schoolDevice.getDeviceName() + "[" + schoolDevice.getDeviceNumber() + "]" + " ";
        }

        // 将本设备设置成关联设备
        self.setInnerSame(innerSame);
        self.setInnerDeviceName(innerDeviceName);
        schoolDeviceService.saveSchoolDevice(self);

        // 将选择的设备设置成关联设备
        for (String deviceNumber : array) {
            SchoolDevice schoolDevice = schoolDeviceService.findSchoolDeviceByPrimaryKey(deviceNumber);
            schoolDevice.setInnerSame(innerSame);
            schoolDevice.setInnerDeviceName(innerDeviceName);
            schoolDeviceService.saveSchoolDevice(schoolDevice);
        }

        // 将该设备组合中原来的设备设置一下innerDeviceName
        if (isSeted) {
            List<SchoolDevice> devices = schoolDeviceService.findSchoolDeviceSet(innerSame);
            for (SchoolDevice schoolDevice : devices) {
                schoolDevice.setInnerSame(innerSame);
                schoolDevice.setInnerDeviceName(innerDeviceName);
                schoolDeviceService.saveSchoolDevice(schoolDevice);
            }
        }

        return "redirect:/device/listInnerSameDevice?deviceId=" + deviceId;
    }

    /****************************************************************************
     * 功能：解除关联设备 作者：贺子龙 日期：2016-04-04
     ****************************************************************************/
    @RequestMapping("/device/deleteInnerSameDevice")
    public String deleteInnerSameDevice(@RequestParam int deviceId, String deviceNumber) {
        // 根据id找到该实验室设备
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        // 找到该套设备
        int innerSame = labRoomDevice.getSchoolDevice().getInnerSame();
        // 找到要解除关联的设备
        SchoolDevice targetDevice = schoolDeviceService.findSchoolDeviceByPrimaryKey(deviceNumber);
        targetDevice.setInnerSame(null);
        // 将解除的设备从innerDeviceName中去除
        String innerDeviceName = labRoomDevice.getSchoolDevice().getInnerDeviceName()
                .replaceFirst(targetDevice.getDeviceName(), "")
                .replaceFirst(targetDevice.getDeviceNumber(), "").replace("[]", "");

        List<SchoolDevice> devices = schoolDeviceService.findSchoolDeviceSet(innerSame);
        // 重新设置关联设备的innerSame和innerDeviceName
        if (devices.size() > 0) {
            for (SchoolDevice schoolDevice : devices) {
                schoolDevice.setInnerSame(innerSame);
                schoolDevice.setInnerDeviceName(innerDeviceName);
                schoolDeviceService.saveSchoolDevice(schoolDevice);
            }
        }
        return "redirect:/device/listInnerSameDevice?deviceId=" + deviceId;
    }

    /****************************************************************************
     * @功能：取消培训
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/cancelTraining")
    public @ResponseBody
    String cancelTraining(@RequestParam Integer id) {
        // id对应的培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        //CTrainingStatus status = cTrainingStatusDAO.findCTrainingStatusByPrimaryKey(4);
        CDictionary status = shareService.getCDictionaryByCategory("c_training_status", "3");
        train.setCTrainingStatus(status);
        labRoomDeviceTrainingDAO.store(train);
        // 告知培训学生
        Set<LabRoomDeviceTrainingPeople> people = train.getLabRoomDeviceTrainingPeoples();
        if (people != null && people.size() > 0) {
            for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : people) {
                labRoomDeviceTrainingPeople.setMessageFlag(2);
                labRoomDeviceTrainingPeopleDAO.store(labRoomDeviceTrainingPeople);
            }
        }

        return "success";
    }

    /****************************************************************************
     * @功能：针对学生--培训预约时间变化，我知道了
     * @作者：贺子龙
     ****************************************************************************/
    @RequestMapping("/device/alreadyKnownMessege")
    public @ResponseBody
    String alreadyKnownMessege(@RequestParam int id) {
        // 获取当前用户
        User user = shareService.getUser();
        List<LabRoomDeviceTrainingPeople> trainPeople = labRoomDeviceService.findTrainingPeoplesByTrainingId(id);
        if (trainPeople != null && trainPeople.size() > 0) {
            for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : trainPeople) {
                if (labRoomDeviceTrainingPeople.getUser().getUsername() == user.getUsername()) {
                    labRoomDeviceTrainingPeople.setMessageFlag(0);
                    labRoomDeviceTrainingPeopleDAO.store(labRoomDeviceTrainingPeople);
                } else {
                    continue;
                }
            }
        }
        return "success";
    }

    /************************************************************
     * 功能：排课管理--分组管理--修改分批时间
     * 作者：贺子龙
     * 日期：2016-03-21
     * @throws ParseException
     ************************************************************/
    @RequestMapping("/device/altTrainTime")
    public @ResponseBody
    String altTrainTime(@RequestParam int id, HttpServletRequest request) throws ParseException {
        // id对应的培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        if (train == null) {
            return "error";
        }
        if (request.getParameter("begintime") == null || request.getParameter("begintime").equals("")) {
            return "error";
        }
        //字符串转日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = sdf.parse(request.getParameter("begintime"));//从前台将begintime取出来，然后转化成calendar格式
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(start);

        train.setTime(startDate);

        labRoomDeviceTrainingDAO.store(train);

        // 告知培训学生
        Set<LabRoomDeviceTrainingPeople> people = train.getLabRoomDeviceTrainingPeoples();
        if (people != null && people.size() > 0) {
            for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : people) {
                labRoomDeviceTrainingPeople.setMessageFlag(1);
                labRoomDeviceTrainingPeopleDAO.store(labRoomDeviceTrainingPeople);
            }
        }

        return "success";
    }

    /****************************************************************************
     * @description：课题组管理-列表
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    @RequestMapping("/device/listAllResearchProjects")
    public ModelAndView listAllResearchProjects(@ModelAttribute ResearchProject researchProject, @RequestParam Integer page) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;
        //课题组分页
        List<ResearchProject> listResearchProjects = labRoomDeviceService.findAllResearchProjects(researchProject, page, pageSize);
        List<ResearchProject> listAllResearchProjects = labRoomDeviceService.findAllResearchProjects(new ResearchProject(), 1, -1);
        // 查询出来的总记录条数
        int totalRecords = labRoomDeviceService.findAllResearchProjects(researchProject, 1, -1).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        mav.addObject("listResearchProjects", listResearchProjects);
        mav.addObject("listAllResearchProjects", listAllResearchProjects);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("researchProject", researchProject);
        mav.setViewName("/device/listAllResearchProjects.jsp");
        return mav;
    }


    /****************************************************************************
     * @description：课题组管理-新建课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    @RequestMapping("/device/newResearchProject")
    public ModelAndView newResearchProject() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("researchProject", new ResearchProject());
        mav.setViewName("/device/newResearchProject.jsp");
        return mav;
    }

    /****************************************************************************
     * @description：课题组管理-编辑课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    @RequestMapping("/device/editResearchProject")
    public ModelAndView editResearchProject(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("researchProject", labRoomDeviceService.findResearchProjectByPrimaryKey(id));
        mav.setViewName("/device/newResearchProject.jsp");
        return mav;
    }

    /****************************************************************************
     * @description：课题组管理-保存课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    @RequestMapping("/device/saveResearchProject")
    public ModelAndView saveResearchProject(@ModelAttribute ResearchProject researchProject) {
        ModelAndView mav = new ModelAndView();
        labRoomDeviceService.saveResearchProject(researchProject);
        mav.setViewName("redirect:/device/listAllResearchProjects?page=1");
        return mav;
    }

    /****************************************************************************
     * @description：课题组管理-删除课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    @RequestMapping("/device/deleteResearchProject")
    public ModelAndView deleteResearchProject(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        labRoomDeviceService.deleteResearchProject(id);
        mav.setViewName("redirect:/device/listAllResearchProjects?page=1");
        return mav;
    }

    /************************************************************
     * 功能：排课管理--分组管理--修改分批时间
     * 作者：贺子龙
     * 日期：2016-03-21
     * @throws ParseException
     ************************************************************/
    @RequestMapping("/device/addTimeAndAddress")
    public @ResponseBody
    String addTimeAndAddress(@RequestParam int id, HttpServletRequest request) throws ParseException {
        // id对应的培训
        LabRoomDeviceTraining train = labRoomDeviceTrainingDAO.findLabRoomDeviceTrainingByPrimaryKey(id);
        if (train == null) {
            return "error";
        }
        if (request.getParameter("begintime") == null || request.getParameter("begintime").equals("")) {
            return "error";
        }
        //字符串转日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = sdf.parse(request.getParameter("begintime"));//从前台将begintime取出来，然后转化成calendar格式
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(start);

        train.setTime(startDate);
        train.setSchoolTerm(shareService.getBelongsSchoolTerm(startDate));
        train.setAddress(request.getParameter("address"));
        labRoomDeviceTrainingDAO.store(train);

        // 告知培训学生
        Set<LabRoomDeviceTrainingPeople> people = train.getLabRoomDeviceTrainingPeoples();
        if (people != null && people.size() > 0) {
            for (LabRoomDeviceTrainingPeople labRoomDeviceTrainingPeople : people) {
                Message message = new Message();
                Calendar date = Calendar.getInstance();
                message.setSendUser(shareService.getUserDetail().getCname());
                message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
                message.setCond(0);
                message.setTitle("培训申请确认");
                String content = "培训时间和地点已设置";
                content += "<a onclick='changeMessage(this)' href='../device/editDeviceTrainingRest/" + train.getLabRoomDevice().getLabRoom().getId() + "/" + "-1" + "/" + "-1" + "/-1/" + "1/" + train.getLabRoomDevice().getId() + "/-1" + "'";

                content += " >点击查看</a>";
//				message.setContent("申请成功，等待审核<a  href=\"../operation/viewOperationItem?operationItemId=44956&&flag=1&status=2'>点击查看</a>");
                message.setContent(content);
                message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                message.setCreateTime(date);
                message.setUsername(labRoomDeviceTrainingPeople.getUser().getUsername());
                message = messageDAO.store(message);
            }
        }

        return "success";
    }

    /****************************************************************************
     * @description：课题组管理-添加用户
     * @author：郑昕茹
     * @date:2016-12-21
     ****************************************************************************/
    @RequestMapping("/device/addResearchUser")
    public ModelAndView addResearchUser(@RequestParam Integer id, @RequestParam Integer currpage, @ModelAttribute User user) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 30;
        int totalRecords = labRoomDeviceService.findUserNotInThisResearch(user, id, -1, 1).size();
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        List<User> users = labRoomDeviceService.findUserNotInThisResearch(user, id, pageSize, currpage);
        mav.addObject("users", users);
        mav.addObject("pageSize", pageSize);
        mav.addObject("pageModel", pageModel);
        mav.addObject("currpage", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("user", user);
        mav.addObject("id", id);
        mav.setViewName("/device/addResearchUser.jsp");
        return mav;
    }

    /****************************************************************************
     * @description：课题组管理-保存添加的用户
     * @author：郑昕茹
     * @date:2016-12-22
     ****************************************************************************/
    @RequestMapping("/device/saveResearchUser")
    public @ResponseBody
    String saveResearchUser(@RequestParam Integer researchId, String[] array) {
        ResearchProject researchProject = labRoomDeviceService.findResearchProjectByPrimaryKey(researchId);
        for (String i : array) {
            //username对应的用户
            User u = userDAO.findUserByPrimaryKey(i);
            u.setResearchProject(researchProject);
            userDAO.store(u);
        }
        researchProject.setUserNum(researchProject.getUsers().size());
        labRoomDeviceService.saveResearchProject(researchProject);
        return "success";
    }


    /****************************************************************************
     * @description：课题组管理-显示已添加用户
     * @author：郑昕茹
     * @date:2016-12-21
     ****************************************************************************/
    @RequestMapping("/device/viewResearchUser")
    public ModelAndView viewResearchUser(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        ResearchProject researchProject = researchProjectDAO.findResearchProjectById(id);
        Set<User> users = researchProject.getUsers();
        mav.addObject("users", users);
        mav.addObject("id", id);
        mav.setViewName("/device/viewResearchUser.jsp");
        return mav;
    }


    /****************************************************************************
     * @description：课题组管理-显示已添加用户
     * @author：郑昕茹
     * @date:2016-12-21
     ****************************************************************************/
    @RequestMapping("/device/deleteUserInResearchProject")
    public String deleteUserInResearchProject(@RequestParam Integer id, @RequestParam String username) {
        ResearchProject researchProject = researchProjectDAO.findResearchProjectById(id);
        User user = userDAO.findUserByPrimaryKey(username);
        user.setResearchProject(null);
        ;
        userDAO.store(user);
        researchProject.setUserNum(researchProject.getUsers().size());
        researchProjectDAO.store(researchProject);
        return "redirect:/device/viewResearchUser?id=" + id;
    }

    /****************************************************************************
     * @description：显示所有可借用设备
     * @author：周志辉
     * @date:2017-08-11
     ****************************************************************************/
    @RequestMapping("/device/allLendableDeviceList")
    public ModelAndView allLendableDeviceList(@ModelAttribute("selected_academy") String acno, @RequestParam Integer currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = new LabRoomDevice();
        LabRoomDeviceLending labRoomDeviceLending = null;
        try{
        String startTime = request.getParameter("startTime");
        String returnTime = request.getParameter("returnTime");
        String content = request.getParameter("content");
        if (content!=null && !"".equals(content)){
        if(content.equals(new String(content.getBytes("iso8859-1"), "iso8859-1"))){
            content = new String(content.getBytes("iso8859-1"), "utf-8");
        }}
//        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");

        labRoomDeviceLending = labRoomService.checkTimeAndContent(startTime,returnTime,content);
        mav.addObject("labRoomDeviceLending", labRoomDeviceLending);
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        // 查询出所有的设备
        int totalRecords = labRoomDeviceService.getAllLendableDevice(request);
        //int pageSize = 10;// 每页10条记录
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> allLendableDeviceList = labRoomDeviceService.findAllLendableDevice(labRoomDevice, null, pageSize, currpage, request);
        mav.addObject("allLendableDeviceList", allLendableDeviceList);
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        // 当前登录人
        User user = shareService.getUser();
        List<User> dUsers = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
        if(dUsers == null || dUsers.size() == 0){
            mav.addObject("dean", "noDean");
        }else {
            mav.addObject("dean", "dean");
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/allLendableLabRoomDeviceList.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mav;
    }

    /****************************************************************************
     * @description：显示已选设别
     * @author：罗璇
     * @date:2018年3月29日
     ****************************************************************************/
    @RequestMapping("/device/canLendableDeviceList")
    public ModelAndView canLendableDeviceList(@ModelAttribute("selected_academy") String acno, String selectedDeviceStr, Integer currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = new LabRoomDevice();
        //重新截取id字符串合集
        String[] s = selectedDeviceStr.split(",");
        StringBuilder newSelectedDeviceStr = new StringBuilder("");
        for (int i = 0; i < s.length; i++) {
            newSelectedDeviceStr.append(s[i].split("_")[1]);
            newSelectedDeviceStr.append(",");
        }
        selectedDeviceStr = newSelectedDeviceStr.toString().substring(0,newSelectedDeviceStr.length() - 1);
        //重新截取id字符串合集结束
        // 查询出所有的设备
        Integer totalRecords = labRoomDeviceService.countSelectedDevice(selectedDeviceStr);
        Integer pageSize = CommonConstantInterface.INT_PAGESIZE;
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> allLendableDeviceList = labRoomDeviceService.getSelectedDevice(selectedDeviceStr, currpage, pageSize);
        mav.addObject("allLendableDeviceList", allLendableDeviceList);
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/canLendableLabRoomDeviceList.jsp");
        return mav;
    }

    /****************************************************************************
     * @description：显示所有可借用设备
     * @author：周志辉
     * @date:2017-08-11
     ****************************************************************************/
    @RequestMapping("/device/allLendableDeviceList1")
    public ModelAndView allLendableDeviceList1(@ModelAttribute("selected_academy") String acno, @RequestParam Integer currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = new LabRoomDevice();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        // 查询出所有的设备
        int totalRecords = labRoomDeviceService.getAllLendableDevice(request);
        //int pageSize = 10;// 每页10条记录
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> allLendableDeviceList = labRoomDeviceService.findAllLendableDevice(labRoomDevice, acno, pageSize, currpage, request);
        mav.addObject("allLendableDeviceList", allLendableDeviceList);
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        // 当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device/allLendableDeviceList.jsp");
        return mav;
    }

    /************************************************************
     * @throws ParseException
     * @共享数据--查看所有实验室设备列表
     * @作者：周志辉
     * @日期：2017-08-17
     ************************************************************/
    @RequestMapping("/device/findAllLabRoomDevice")
    public ModelAndView findAllLabRoomDevice(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam int page) {

        //新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        //查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        int pageSize = 30;//每页20条记录
        //查询出来的总记录条数
        int totalRecords = shareDataService.findLabRoomDevice(labRoomDevice, 1, -1).size();
        //分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        //根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = shareDataService.findLabRoomDevice(labRoomDevice, page, pageSize);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("listAllLabRoomDevice", shareDataService.findLabRoomDevice(new LabRoomDevice(), 1, -1));
        //找到所有的实验室
        mav.addObject("labRooms", labRoomService.findAllLabRoomByQuery(1, -1, new LabRoom()));
        //设备状态
        mav.addObject("listDeviceStatus", shareService.getCDictionaryData("c_lab_room_device_status"));
        //设备保管人
        List<User> listLabRoomDeviceKeepUser = shareDataService.findAllDeviceKeepUser();
        List<User> listLabRoomDeviceKeepUsers = new ArrayList<User>(new HashSet<User>(listLabRoomDeviceKeepUser));
        mav.addObject("listLabRoomDeviceKeepUsers", listLabRoomDeviceKeepUsers);
        mav.setViewName("/device/lab_room_device/deviceRepairList.jsp");

        return mav;

    }

    /*****************************
     *Description 实验室设备维修登记
     *
     *@author:周志辉
     *@date:2017.8.18
     *****************************/
    @RequestMapping("/editLabRoomDeviceRepair")
    public ModelAndView editLabRoomDeviceRepair(@RequestParam int labRoomDeviceId) {
        ModelAndView mav = new ModelAndView();
        //List<User> users =new ArrayList<User>(shareService.getUsersMap().values());
        Map users = shareService.getUsersMap();
        mav.addObject("users", users);
        mav.addObject("labRoomDeviceRepair", new LabRoomDeviceRepair());
        mav.addObject("labRoomDeviceId", labRoomDeviceId);
        mav.addObject("flag", 0);
        mav.addObject("device", labRoomDeviceService.findLabRoomDeviceByPrimaryKey(labRoomDeviceId));
        mav.setViewName("device/lab_room_device/editLabRoomDeviceRepair.jsp");
        return mav;
    }

    /*****************************
     *Description 编辑实验室设备维修登记
     *
     *@author:周志辉
     *@date:2017.10.7
     *****************************/
    @RequestMapping("/editLabRoomDeviceRepairDetail")
    public ModelAndView editLabRoomDeviceRepairDetail(@RequestParam int labRoomDeviceRepairId, int labRoomDeviceId) {
        ModelAndView mav = new ModelAndView();
        Map users = shareService.getUsersMap();
        mav.addObject("labRoomDeviceRepair", labRoomDeviceRepairService.findLabRoomDeviceRepairByPrimaryKey(labRoomDeviceRepairId));
        mav.addObject("labRoomDeviceId", labRoomDeviceRepairService.findLabRoomDeviceRepairByPrimaryKey(labRoomDeviceRepairId).getLabRoomDevice().getSchoolDevice().getDeviceNumber());
        mav.addObject("users", users);
        mav.addObject("labRoomDeviceRepairId", labRoomDeviceRepairId);
        mav.addObject("labRoomDeviceId", labRoomDeviceId);
        mav.addObject("flag", 1);
        mav.addObject("device", labRoomDeviceService.findLabRoomDeviceByPrimaryKey(labRoomDeviceId));
        mav.setViewName("device/lab_room_device/editLabRoomDeviceRepair.jsp");
        return mav;
    }

    /**
     * Description 保存实验室设备维修登记
     *
     * @author:周志辉
     * @date:2017.8.18
     */
    @RequestMapping("/device/saveLabRoomDeviceRepair")
    public String saveLabRoomDeviceRepair(@RequestParam int labRoomDeviceId, @ModelAttribute LabRoomDeviceRepair labRoomDeviceRepair) {
    	if(labRoomDeviceId != -1){
    		labRoomDeviceRepair.setLabRoomDevice(labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(labRoomDeviceId));
    	}
        labRoomDeviceRepairDAO.store(labRoomDeviceRepair);
        return "redirect:/device/applyDeviceRepairList?page=1";
    }

    /*****************************
     *Description 设备维修登记详情
     *
     *@author:周志辉
     *@param:
     *@date:2018.08.23
     *****************************/
    @RequestMapping("/device/labRoomDeviceRepairDetail")
    public ModelAndView labRoomDeviceRepairDetail(@RequestParam int labRoomDeviceRepairId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomDeviceRepair", labRoomDeviceRepairService.findLabRoomDeviceRepairByPrimaryKey(labRoomDeviceRepairId));
        mav.addObject("flag", 1);
        mav.addObject("labRoomDeviceId", labRoomDeviceRepairService.findLabRoomDeviceRepairByPrimaryKey(labRoomDeviceRepairId).getLabRoomDevice().getSchoolDevice().getDeviceNumber());
        mav.setViewName("device/lab_room_device/labRoomDeviceRepairDetail.jsp");
        return mav;
    }

    /*****************************
     *Description 删除设备维修登记详情
     *
     *@author:周志辉
     *@param:
     *@date:2018.10.7
     *****************************/
    @RequestMapping("/device/deleteLabRoomDeviceRepair")
    public String deleteLabRoomDeviceRepair(@RequestParam int labRoomDeviceRepairId) {
        LabRoomDeviceRepair labRoomDeviceRepair = labRoomDeviceRepairService.findLabRoomDeviceRepairByPrimaryKey(labRoomDeviceRepairId);
        labRoomDeviceRepairDAO.remove(labRoomDeviceRepair);
        labRoomDeviceRepairDAO.flush();
        return "redirect:/device/applyDeviceRepairList?page=1";
    }

    /****************************************************************************
     * @功能：给设备维修登记上传文档
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/device/labRoomDeviceRepairDocumentUpload")
    public ModelAndView labRoomDeviceRepairDocumentUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id, Integer labRoomDeviceId) throws Exception {
        ModelAndView mav = new ModelAndView();
        labRoomDeviceRepairService.findLabRoomDeviceRepairByPrimaryKey(id);
        mav.setViewName("redirect:/editLabRoomDeviceRepairDetail?labRoomDeviceRepairId=" + id + "&labRoomDeviceId=" + labRoomDeviceId);
        labRoomDeviceRepairService.labRoomDeviceRepairDocumentUpload(request, response, id, 2);
        return mav;
    }

    /****************************************************************************
     * @功能：设备出借申请单
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/device/editLabRoomDevicelending")
    public ModelAndView editLabRoomDevicelending(@RequestParam Integer idKey, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("idKey", idKey);
        mav.addObject("date", shareService.getDate());
        mav.addObject("labRoomDeviceLend", new LabRoomDeviceLending());
        mav.addObject("labRoomDevice", labRoomDeviceService.findLabRoomDeviceByPrimaryKey(idKey));
        mav.addObject("user", shareService.getUser());
        // 查找当前中心所属学院
        mav.addObject("users", shareService.findTheSameCollegeTeacher(acno));
        mav.setViewName("/device/lab_room_device/deviceLendApply.jsp");
        return mav;
    }

    /****************************************************************************
     * @throws UnsupportedEncodingException
     * @功能：删除设备维修登记文档
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("device/deleteDeviceRepairDocument")
    public String deleteDeviceDocumentRest(@RequestParam int id, int labRoomDeviceRepairId, int labRoomDeviceId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的设备图片
        CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
        commonDocumentService.deleteCommonDocument(doc);
        return "redirect:/editLabRoomDeviceRepairDetail?labRoomDeviceRepairId=" + labRoomDeviceRepairId + "&labRoomDeviceId=" + labRoomDeviceId;
    }


    /****************************************************************************
     * @throws UnsupportedEncodingException
     * @功能：原生上传
     * @作者：马帅
     ****************************************************************************/
    @RequestMapping("/device/uploadOri")
    public ModelAndView uploadOri(HttpServletRequest request, HttpServletResponse response, Integer id,
                                  Integer labRoomId, Integer deviceNumber, String deviceName, Integer username,
                                  Integer page, Integer schoolDevice_allowAppointment) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        labRoomDeviceService.deviceDocumentUpload(request, response, id, 2);
        mav.setViewName("redirect:/device/editDeviceDocumentRest/" + labRoomId + "/" + deviceNumber + "/" + URLEncoder.encode(deviceName, "utf-8") + "/" + username + "/" + page + "/" + id + "/" + schoolDevice_allowAppointment);
        return mav;
    }

    /****************************************************************************
     * @功能：下载设备文档
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/device/downloadDeviceRepairDocument")
    public void downloadDeviceRepairDocument(HttpServletRequest request, HttpServletResponse response, int id) {
        // id对应的文档
        CommonDocument doc = commonDocumentService.findCommonDocumentByPrimaryKey(id);
        labRoomDeviceService.downloadFile(doc, request, response);
    }

    /****************************************************************************
     * @功能：用户所有设备借用申请
     * @作者：周志辉
     * @时间：2017-11-03
     ****************************************************************************/
    @RequestMapping("/device/deviceLendingApplyList")
    public ModelAndView deviceLendingApplyList(@ModelAttribute LabRoomDeviceLendingResult lrdlr, Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdlr);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getAllDeviceLendingApplyList(request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录;
        List<LabRoomDeviceLending> deviceLendList = labRoomDeviceService.getAllDeviceLendingApplyList(page, pageSize, request);
        mav.addObject("deviceLendingApplyList", deviceLendList);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        mav.addObject("lendBatch", request.getParameter("lendBatch"));
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 4 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device_lending/deviceLendingApplyList.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：设备出借申请单列表
     * @作者：周志辉
     * @时间：2017-11-04
     ****************************************************************************/
    @RequestMapping("/device/deviceLendingCheckList")
    public ModelAndView deviceLendingCheckList(@ModelAttribute LabRoomDeviceLending lrdl, @RequestParam Integer page, @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("reservation", lrdl);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.getLabRoomLendsTotals(lrdl, request);
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceLending> deviceLendList = labRoomDeviceService.findAllLabRoomLends(lrdl, page, pageSize, request);

        mav.addObject("deviceLendList", deviceLendList);
        mav.addObject("deviceName", request.getParameter("deviceName"));
        mav.addObject("lendBatch", request.getParameter("lendBatch"));
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 7 || a.getId() == 11) {
                    mav.addObject("admin", true);
                }
                if (a.getId() == 4) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("labRoomHeads", shareService.findAllLabRoomtHead());
        mav.addObject("user", user);
        mav.setViewName("/device/lab_room_device_lending/deviceLendList.jsp");
        return mav;
    }

    @RequestMapping("/device/editDeviceLending")
    public ModelAndView editDeviceLending(@RequestParam int idKey) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomDeviceLend", labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(idKey));
        List<CDictionary> trainingTypes = shareService
                .getCDictionaryData("c_lab_room_training_type");
        mav.addObject("trainingTypes", trainingTypes);
        mav.addObject("idkey", idKey);
        mav.addObject("user", shareService.getUser());
        mav.addObject("labRoomDevice", labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(idKey).getLabRoomDevice());
        mav.setViewName("/device/lab_room_device/deviceLendApply.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：删除设备借用申请
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/device/deleteDeviceLending")
    public ModelAndView deletesDeviceLending(@RequestParam Integer idKey) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备
        LabRoomDeviceLending d = labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(idKey);
        CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(d.getLabRoomDevice().getId());
        labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
        labRoomDeviceService.save(labRoomDevice);
        labRoomDeviceLendingDAO.remove(d);
        mav.setViewName("redirect:/device/deviceLendingApplyList?page=1");
        return mav;
    }

    /****************************************************************************
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @功能：提交设备借用申请
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/device/submitDeviceLending")
    public ModelAndView submitDeviceLending(@RequestParam String lendBatch) throws NoSuchAlgorithmException, InterruptedException {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        List<LabRoomDeviceLending> lendings = labRoomDeviceService.getDeviceLendingByBatch(lendBatch);
        String deviceInfo = "";
        int idKey = 0;
        for (LabRoomDeviceLending d : lendings) {
        	deviceInfo = d.getLabRoomDevice().getSchoolDevice().getDeviceNumber() + 
        			"-" + d.getLabRoomDevice().getSchoolDevice().getDeviceName() ;
        	idKey = d.getId();
        	d.setStage(0);
            //审核中
            CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lending_status","3");
            d.setCDictionary(cDictionary);
        	labRoomDeviceLendingDAO.store(d);
		}
        
        
        //获取当前登陆人
        User user = shareService.getUser();
        /*//给自己发送消息
        Message messageToStudent = new Message();
        messageToStudent.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
        messageToStudent.setSendCparty(shareService.getUserDetail().getSchoolAcademy()
                .getAcademyName());// 当前登录人所在学院
        messageToStudent.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE);
        String content1 = "申请成功，等待审核";
        content1 += "<a  href=\"../device/deviceLendingApplyList?page=1";
        content1 += "'>点击查看</a>";
        messageToStudent.setTage(0);
        messageToStudent.setContent(content1);
        messageToStudent.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        messageToStudent.setCreateTime(Calendar.getInstance());
        // message.setUsername(excenterDirector.getUsername());
        messageToStudent.setUsername(user.getUsername());
        messageToStudent = messageDAO.store(messageToStudent);
        String messageContent = "您已成功提交对" + deviceInfo + "等的申请";
        try {
            String result = shareService.sendMessage(user.getTelephone(), messageContent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //给下级审核人发送消息
        Message message = new Message();
        message.setSendUser(shareService.getUserDetail().getCname());// 当前登录人
        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy()
                .getAcademyName());// 当前登录人所在学院
        message.setTitle(CommonConstantInterface.STR_LABROOMDEVICELENDING_TITLE);
        String content = "<a onclick='changeMessage(this)' href='../device/auditDepartmentDeviceLending?idKey=" + idKey;
        content += "'>审核</a>";
        message.setTage(2);
        message.setContent(content);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(Calendar.getInstance());
        // message.setUsername(excenterDirector.getUsername());
        // 默认只有一个系主任的意思？
        message.setUsername(shareService.findAllDepartmentHead(user).isEmpty() ? null : shareService.findAllDepartmentHead(user).get(0).getUsername());
        message = messageDAO.store(message);
        String messageContent1 = "您有一个设备申请需要审核，信息为：" + deviceInfo + "等";
        String result1 = shareService.sendMessage(shareService.findAllDepartmentHead(user).isEmpty() ? null : shareService.findAllDepartmentHead(user).get(0).getTelephone(), messageContent1);
        mav.setViewName("redirect:/device/deviceLendingApplyList?page=1");
        return mav;
    }

    /**
     * 批量申请设备借用
     *
     * @param request
     * @return
     */
    @RequestMapping("/device/saveLabRoomDeviceLending")
    public ModelAndView saveLabRoomDeviceLending(HttpServletRequest request) throws Exception{
        ModelAndView mav = new ModelAndView();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();

        //申请日期
        String startTime = request.getParameter("startTime");
        //归还日期
        String returnTime = request.getParameter("returnTime");
        //借用原因
        String content = request.getParameter("content");
        //批量设备编号
        String batchDeviceEles = request.getParameter("batchDeviceEles");
        String[] batchDeviceElesArr = batchDeviceEles.split(",");
        List<String> batchDeviceEleList = new ArrayList<String>();
        for (String batchDevice : batchDeviceElesArr) {
        	batchDeviceEleList.add(batchDevice);
        }
        Collections.sort(batchDeviceEleList);  
        
        //获取当前登陆人
        User user = shareService.getUser();
        // 本批次的流水号
        String lendBatch = "default";
        // 实验室id
        String labId = "_";
        for (String labRoom_device : batchDeviceEleList){
        	// labRoom_device由实验室id和device id构成
        	String[] batchArr = labRoom_device.split("_");
        	if (!labId.equals(batchArr[0])) {
        		lendBatch = labRoomDeviceService.getDeviceLendingBatch();
			}
        	labId = batchArr[0];
        	LabRoomDevice labRoomDevice = labRoomDeviceService.
        			findLabRoomDeviceByPrimaryKey(Integer.valueOf(batchArr[1]));
            LabRoomDeviceLending lrdl = new LabRoomDeviceLending();
            //流水号
            lrdl.setLendBatch(lendBatch);
            //申请日期
            lrdl.setLendingTime(Calendar.getInstance());
            //借用开始日期
            Date lendingTimeDate =sdf.parse(startTime);
            calendar.setTime(lendingTimeDate);
            lrdl.setStartTime(calendar);
            //归还日期
            Date returnTimeDate =sdf.parse(returnTime);
            calendar.setTime(returnTimeDate);
            lrdl.setReturnTime(calendar);
            //借用人
            lrdl.setUserByLendingUser(user);
            //借用内容
            lrdl.setContent(content);
            //借用设备
            lrdl.setLabRoomDevice(labRoomDevice);
            //lendType
            lrdl.setLendType("1");
            //获取系主任
            if (!shareService.findAllDepartmentHead(user).isEmpty()) {
                lrdl.setUserByDepartmentHead(shareService.findAllDepartmentHead(user).get(0));
            }

            labRoomDeviceService.saveDeviceLendApply(lrdl);

            CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_room_device_status", "5");
            //将设备状态设为借用中
            labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
            labRoomDeviceService.save(labRoomDevice);
        }

        mav.setViewName("redirect:/device/deviceLendingApplyList?page=1");


        return mav;
    }
    /****************************************************************************
     * @功能：删除实验分室设备
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping("/ssss")
    public ModelAndView test() {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的实验室设备
        mav.setViewName("exam.jsp");
        return mav;
    }

    /**
     * Description 判断设备借用时借用设备有没有需要系主任审核
     * @param request 请求
     * @return 系主任审核需要与否的字符串
     * @author 黄保钱 2018-9-17
     */
    @RequestMapping("/device/findDeanAuditStatus")
    public @ResponseBody String findDeanAuditStatus(HttpServletRequest request){
        boolean needDean = false;
        // 获取所选设备的实验室id和设备id
        String selectDeviceNo = request.getParameter("selectedDevices");
        // 将所选设备逐个分离
        String[] labDevice = selectDeviceNo.split(",");
        // 遍历所选设备获得其是否需要系主任审核
        for (String s : labDevice) {
            // 分离实验室id和设备id
            String[] ids = s.split("_");
            // 这里只要设备id来寻找设备表项
            LabRoomDevice d = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(Integer.parseInt(ids[1]));
            // 只要有一个设备需要系主任审核，则返回需要系主任
            if (d != null && d.getDean() != null && d.getDean() == 1 && d.getCDictionaryByIsAudit() != null && d.getCDictionaryByIsAudit().getCName().equals("是")) {
                needDean = true;
                break;
            }
        }
        // 返回需要系主任与否的字符串
        return needDean ? "needDean" : "noNeedDean";
    }
    /**
     * Description 保存物联硬件
     * @param agent 对象
     * @return
     * @author 廖文辉 2018-12-11
     */
    @RequestMapping("/device/saveLabRoomAgent")
    public ModelAndView saveLabRoomAgent(@ModelAttribute LabRoomAgent agent, Integer deviceId,Integer labRoomId, Integer deviceNumber, String deviceName, Integer username,
                                         Integer page, Integer schoolDevice_allowAppointment)throws UnsupportedEncodingException {
        System.out.println("saveLabRoomAgent coming in");
        ModelAndView mav = new ModelAndView();
        agent.setLabRoom(null);
        // 物联服务器
        System.out.println(agent.getCommonServer().getId());
        int serverId = agent.getCommonServer().getId();
        System.out.println("物联服务器号" + serverId);
        CommonServer server = commonServerDAO
                .findCommonServerByPrimaryKey(serverId);
        System.out.println(server.getServerName());
        agent.setCommonServer(server);
        agent=labRoomAgentDAO.store(agent);
        LabRoomDevice labRoomDevice=labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(deviceId);
        labRoomDevice.setLabRoomAgent(agent);
        labRoomDeviceDAO.store(labRoomDevice);
        mav.setViewName("redirect:/device/editAgentInfoRest/" + labRoomId + "/" + deviceNumber + "/" + URLEncoder.encode(deviceName, "utf-8") + "/" + username + "/" + page + "/" + deviceId + "/" + schoolDevice_allowAppointment);
        return mav;
    }
    /**
     * Description 删除物联硬件
     * @param agentId 物联硬件Id
     * @return
     * @author 廖文辉 2018-12-11
     */
    @RequestMapping("/device/deleteLabRoomAgent")
    public ModelAndView deleteLabRoomAgent(@RequestParam Integer agentId, Integer deviceId,Integer labRoomId, Integer deviceNumber, String deviceName, Integer username,
                                           Integer page, Integer schoolDevice_allowAppointment)throws UnsupportedEncodingException{
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联硬件
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        LabRoomDevice labRoomDevice=labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(deviceId);
        labRoomDevice.setLabRoomAgent(null);
        labRoomDeviceDAO.store(labRoomDevice);
        labRoomAgentDAO.remove(agent);
        mav.setViewName("redirect:/device/editAgentInfoRest/" + labRoomId + "/" + deviceNumber + "/" + URLEncoder.encode(deviceName, "utf-8") + "/" + username + "/" + page + "/" + deviceId + "/" + schoolDevice_allowAppointment);
        return mav;
    }
    /**
     * Description 删除物联硬件
     * @param agentId 物联硬件Id
     * @return
     * @author 廖文辉 2018-12-11
     */
    @RequestMapping("/device/updateLabRoomAgent")
    public ModelAndView updateLabRoomAgent(@RequestParam Integer agentId,Integer deviceId,Integer labRoomId, Integer deviceNumber, String deviceName, Integer username,
                                           Integer page, Integer schoolDevice_allowAppointment)throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联硬件
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        mav.addObject("agent", agent);
        // 物联硬件类型
        List<CDictionary> types = shareService.getOnlyCDictionaryData("c_agent_type",String.valueOf(4));//只取电源控制器
        mav.addObject("types", types);
        // 物联硬件服务器
        Set<CommonServer> serverList = commonServerDAO.findAllCommonServers();
        // 设备id对应的设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(deviceId);
        mav.addObject("device", device);
        mav.addObject("serverList", serverList);
        mav.addObject("labRoomId", labRoomId);
        mav.addObject("deviceName", deviceName);
        mav.addObject("deviceNumber", deviceNumber);
        mav.addObject("username", username);
        mav.addObject("page",page);
        mav.addObject("schoolDevice_allowAppointment", schoolDevice_allowAppointment);
        mav.setViewName("device/specialAcademy/updateDeviceAgent.jsp");
        return mav;
    }

    /**
     * @Description 设备借用审核中撤回
     * @author 张德冰
     * @data 2018-01-16
     */
    @RequestMapping("/device/revokeDeviceLending")
    public @ResponseBody
    String revokeDeviceLending(@RequestParam Integer idKey){
        String result = "success";
        // id对应的实验室设备
        LabRoomDeviceLending d = labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(idKey);
        //备份
        labRoomDeviceService.revokeDeviceLend(d);
        //发送消息给下一级审核人
        if (1 == d.getStage()) {
            //实验室管理员
            Set<LabRoomAdmin> admins = d.getLabRoomDevice().getLabRoom().getLabRoomAdmins();
            for (LabRoomAdmin a : admins) {
                Message message = new Message();
                Calendar date = Calendar.getInstance();
                message.setSendUser(shareService.getUserDetail().getCname());
                message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
                message.setCond(0);
                message.setTage(2);
                message.setTitle("设备借用已作废");
                String content = "申请人已撤回" + d.getLabRoomDevice().getSchoolDevice().getDeviceName() + "设备的借用申请，已不需要审核";
                message.setContent(content);
                message.setMessageState(0);
                message.setCreateTime(date);
                message.setUsername(a.getUser().getUsername());
                messageDAO.store(message);
                messageDAO.flush();
            }
        }
        if (2 == d.getStage()) {
            //实训室主任
            List<User> lrh = shareService.findAllLabRoomtHead();
            for (User ur : lrh) {
                Message message = new Message();
                Calendar date = Calendar.getInstance();
                message.setSendUser(shareService.getUserDetail().getCname());
                message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
                message.setCond(0);
                message.setTage(2);
                message.setTitle("设备借用已作废");
                String content = "申请人已撤回" + d.getLabRoomDevice().getSchoolDevice().getDeviceName() + "设备的借用申请，已不需要审核";
                message.setContent(content);
                message.setMessageState(0);
                message.setCreateTime(date);
                message.setUsername(ur.getUsername());
                messageDAO.store(message);
                messageDAO.flush();
            }
        }

        //释放资源，设备可以重新借用
        CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(d.getLabRoomDevice().getId());
        labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
        labRoomDeviceService.save(labRoomDevice);
        labRoomDeviceLendingDAO.remove(d);
        return result;
    }


    /**
     * @Description 设备借用审核完成后作废
     * @author 张德冰
     * @data 2018-01-17
     */
    @RequestMapping("/device/blankOutDeviceLending")
    public @ResponseBody
    String blankOutDeviceLending(@RequestParam Integer idKey){
        String result = "success";
        // id对应的实验室设备
        LabRoomDeviceLending d = labRoomDeviceLendingDAO.findLabRoomDeviceLendingById(idKey);
        //备份
        labRoomDeviceService.revokeDeviceLend(d);
        //发送消息给申请人
        Message message = new Message();
        Calendar date = Calendar.getInstance();
        message.setSendUser(shareService.getUserDetail().getCname());
        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTage(1);
        message.setTitle("设备借用已作废");
        String content = d.getLabRoomDevice().getSchoolDevice().getDeviceName() + "设备的借用申请已作废";
        message.setContent(content);
        message.setMessageState(0);
        message.setCreateTime(date);
        message.setUsername(d.getUserByLendingUser().getUsername());
        messageDAO.store(message);
        messageDAO.flush();

        //释放资源，设备可以重新借用
        CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
        LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(d.getLabRoomDevice().getId());
        labRoomDevice.setCDictionaryByDeviceStatus(cDictionary);
        labRoomDeviceService.save(labRoomDevice);
        labRoomDeviceLendingDAO.remove(d);
        return result;
    }

    /**
     * @Description 设备借用作废列表
     * @author 张德冰
     * @data 2018-01-18
     */
    @RequestMapping("/device/invalidDeviceLendingList")
    public ModelAndView invalidDeviceLendingList(@RequestParam Integer currpage, @ModelAttribute RefuseItemBackup refuseItemBackup){
        ModelAndView mav = new ModelAndView();

        mav.addObject("refuseItemBackup", refuseItemBackup);
        mav.addObject("currpage", currpage);
        int pageSize = 10;

        //设备借用作废
        String type = "LabRoomDeviceLending";
        List<RefuseItemBackup> refuseItemBackups = labRoomDeviceService.findRefuseItemBackupList(refuseItemBackup, type , currpage, pageSize);
        List<Object[]> items = new ArrayList<>();
        for (RefuseItemBackup r:refuseItemBackups){
            Object[] objects = new Object[8];
            //设备名称【编号】
            objects[0] = r.getLabRoomName()+"["+r.getBusinessId()+"]";
            //申请人
            objects[1] = userDAO.findUserByUsername(r.getCreator());

            String str = r.getOperationItemName();
            //借用时间
            objects[2] = str.substring(str.indexOf("借用时间：")+5,str.indexOf("预计归还时间："));
            //预计归还时间
            objects[3] = str.substring(str.indexOf("预计归还时间：")+7,str.indexOf("借用原因："));
            //借用原因
            objects[4] = str.substring(str.indexOf("借用原因：")+5);
            //审核信息
            objects[5] = r.getAuditRefuseBackup().getAuditInfo()+"<br>"+r.getAuditRefuseBackup().getAuditContent();
            items.add(objects);
        }
        mav.addObject("items", items);
        Integer totalRecords = labRoomDeviceService.findRefuseItemBackupTotalRecords(refuseItemBackup, type);
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.setViewName("device/lab_room_device_lending/invalidDeviceLendingList.jsp");
        return mav;
    }
    /**
     * @Description 设备列表
     * @author 黄浩
     * @data 2019年3月7日
     */
    @RequestMapping("/system/schoolDeviceList")
    public ModelAndView schoolDeviceList(@RequestParam Integer currpage, @ModelAttribute SchoolDevice schoolDevice){
        ModelAndView mav = new ModelAndView();

        mav.addObject("schoolDevice", schoolDevice);
        mav.addObject("currpage", currpage);
        int pageSize = 10;

        List<SchoolDevice> schoolDeviceList = schoolDeviceService.listSchoolDevice(schoolDevice, currpage, pageSize);
        mav.addObject("schoolDeviceList", schoolDeviceList);
        Integer totalRecords = schoolDeviceService.getSchoolDeviceRecords(schoolDevice);
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.setViewName("device/listSchoolDevice.jsp");
        return mav;
    }
    /***********************************************************************************
     * @description：导入设备
     * @author 黄浩
     * @date：2019年3月8日
     * **********************************************************************************/
    @RequestMapping("/system/importSchoolDevice")
    public ModelAndView importSchoolDevice(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String fileName = shareService.getUpdateFilePath(request);
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
        String filePath = logoRealPathDir + fileName;
        System.out.println(filePath);
        if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
            schoolDeviceService.importSchoolDevice(filePath);
        }
        mav.setViewName("redirect:/system/schoolDeviceList?currpage=1" );
        return mav;
    }

}