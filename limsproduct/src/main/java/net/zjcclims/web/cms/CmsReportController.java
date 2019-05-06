package net.zjcclims.web.cms;
/*****************************************************
 * @Description 门户可直接访问的报表
 * @attention 该文件不调用其他service方法{shareService除外}
 * @author 陈乐为 2018-10-26
 *****************************************************/

import net.zjcclims.common.LabAttendance;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.lab.LabReservationService;
import net.zjcclims.service.operation.OutlineService;
import net.zjcclims.service.report.ReportService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.device.LabRoomDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.common.ReservationList;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.CStaticValueDAO;
import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.dao.LabRoomDeviceReservationDAO;
import net.zjcclims.dao.LabRoomDeviceReservationResultDAO;
import net.zjcclims.dao.LabRoomLimitTimeDAO;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.dao.ViewTimetableDAO;
import net.zjcclims.domain.CDictionary;
import net.zjcclims.domain.CStaticValue;
import net.zjcclims.domain.CreditOption;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabRoomAdmin;
import net.zjcclims.domain.LabRoomDevice;
import net.zjcclims.domain.LabRoomDeviceReservation;
import net.zjcclims.domain.LabRoomDeviceReservationCredit;
import net.zjcclims.domain.LabRoomDeviceReservationResult;
import net.zjcclims.domain.SchoolDevice;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.User;
import net.zjcclims.domain.ViewTimetable;
import net.zjcclims.domain.ViewUseOfLc;
import net.zjcclims.service.ConvertUtil;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.credit.CreditOptionService;
import net.zjcclims.service.device.LabRoomDeviceReservationCreditService;
import net.zjcclims.service.device.LabRoomDeviceReservationService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.lab.LabRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller("CmsReportController")
@SessionAttributes({"is_reservation"})
@RequestMapping("cmsReport")
public class CmsReportController<JsonResult> {
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    private CommonHdwlogDAO commonHdwlogDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SystemBuildDAO systemBuildDAO;
    @Autowired
    private ShareService shareService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private LabCenterService labCenterService;
    @Autowired
    private LabReservationService labReservationService;
    @Autowired
    private OutlineService outlineService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    private LabRoomDeviceRepairDAO labRoomDeviceRepairDAO;
    /**
     * Description 实验室资源
     * @param labRoom
     * @param currpage
     * @return
     * @author 陈乐为 2018-10-26
     */
    @RequestMapping("/labResource")
    public ModelAndView labResource(@ModelAttribute LabRoom labRoom, int currpage) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoom", labRoom);
        // 学院
        List<SchoolAcademy> academies = schoolAcademyDAO.executeQuery("select c from SchoolAcademy c",0,-1);
        mav.addObject("academies", academies);
        // 楼栋
        List<SystemBuild> builds = systemBuildDAO.executeQuery("select DISTINCT c from SystemBuild c join c.labRooms s where s.systemBuild.buildNumber is not null",0,-1);
        mav.addObject("builds", builds);

        int pagesize = 20;
        String sql = "select c from LabRoom c where 1=1 and labCategory=1";
        // 实验室
        List<LabRoom> labRoomList = labRoomDAO.executeQuery(sql,0,-1);
        mav.addObject("labRoomList", labRoomList);
        String sqlc = "select COUNT(c) from LabRoom c where 1=1 and labCategory=1";
        // 查询条件
        if(labRoom!=null) {
            if(labRoom.getId()!=null) {
                sql += " and c.id="+ labRoom.getId();
                sqlc += " and c.id="+ labRoom.getId();
            }
            if(labRoom.getSchoolAcademy()!=null && labRoom.getSchoolAcademy().getAcademyNumber()!=null
                    && !labRoom.getSchoolAcademy().getAcademyNumber().equals("")) {
                sql += " and c.schoolAcademy.academyNumber='"+labRoom.getSchoolAcademy().getAcademyNumber()+"'";
                sqlc += " and c.schoolAcademy.academyNumber='"+labRoom.getSchoolAcademy().getAcademyNumber()+"'";
            }
            if(labRoom.getSystemBuild()!=null && labRoom.getSystemBuild().getBuildNumber()!=null
                    && !labRoom.getSystemBuild().getBuildNumber().equals("")) {
                sql += " and c.systemBuild.buildNumber='"+labRoom.getSystemBuild().getBuildNumber()+"'";
                sqlc += " and c.systemBuild.buildNumber='"+labRoom.getSystemBuild().getBuildNumber()+"'";
            }
        }
        List<LabRoom> labRooms = labRoomDAO.executeQuery(sql, (currpage-1)*pagesize, pagesize);
        mav.addObject("labRooms", labRooms);
        int totalRecords = ((Long) labRoomDAO.createQuerySingleResult(sqlc).getSingleResult()).intValue();
        mav.addObject("pageModel",shareService.getPage(currpage, pagesize, totalRecords));
        mav.addObject("pageModel", shareService.getPage(currpage, pagesize, totalRecords));
        mav.addObject("currpage", currpage);
        mav.addObject("pagesize", pagesize);

        mav.setViewName("/cmsReport/labResource.jsp");
        return mav;
    }

    /**
     * Description 楼宇联动实验室
     * @param buildNumber
     * @return
     * @author 陈乐为 2018-12-17
     */
    @ResponseBody
    @RequestMapping("/linkLabByBuild")
    public Map<String, String> linkLabByBuild(@RequestParam String buildNumber){
        Map<String, String> map = new HashMap<String, String>();
        String roomsValue = shareService.linkLabByBuild(buildNumber);
        map.put("roomsValue", roomsValue);
        return map;
    }

    /**
     * Description 实验室资源--刷卡记录
     * @param agentId
     * @param currpage
     * @return
     * @author 陈乐为 2018-10-26
     */
    @RequestMapping("/labEntranceList")
    public ModelAndView labEntranceList(@RequestParam int agentId, int currpage) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", agentId);
        int pagesize = 20;
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        String sql = "select c from CommonHdwlog c where 1=1";
        String sqlc = "select COUNT(c) from CommonHdwlog c where 1=1";
        if(agent!=null && agent.getHardwareIp()!=null && agent.getManufactor()!=null){
            sql += " and c.deviceno='"+agent.getManufactor()+"' ";
            sql += " and c.hardwareid='"+agent.getHardwareIp()+"' ";
            sqlc += " and c.deviceno='"+agent.getManufactor()+"' ";
            sqlc += " and c.hardwareid='"+agent.getHardwareIp()+"' ";
        }
        sql += " order by c.id desc";
        List<CommonHdwlog> commonHdwlogs = commonHdwlogDAO.executeQuery(sql, (currpage-1)*pagesize,pagesize);
        int totalRecords = ((Long) commonHdwlogDAO.createQuerySingleResult(sqlc).getSingleResult()).intValue();
        mav.addObject("pageModel",shareService.getPage(currpage, pagesize, totalRecords));
        mav.addObject("pageModel", shareService.getPage(currpage, pagesize, totalRecords));
        mav.addObject("currpage", currpage);
        mav.addObject("pagesize", pagesize);
        //将查出来的日志数据导入labAttendanceList中
        List<LabAttendance> labAttendanceList=new ArrayList<LabAttendance>();
        for (CommonHdwlog commonHdwlog2 : commonHdwlogs) {
            LabAttendance labAttendance=new LabAttendance();
            //姓名
            labAttendance.setCname(commonHdwlog2.getCardname());
            //考勤时间
            String attendanceTime=commonHdwlog2.getDatetime();
            labAttendance.setAttendanceTime(attendanceTime.substring(0, attendanceTime.length()-2));
            //default数据
            labAttendance.setClassName("暂无数据");
            labAttendance.setMajor("暂无数据");
            labAttendance.setAcademyName("暂无数据");
            //所属学院
            if (commonHdwlog2.getAcademyNumber()!=null&&!commonHdwlog2.getAcademyNumber().equals("")) {
                String academyName="";
                SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(commonHdwlog2.getAcademyNumber());
                if (schoolAcademy!=null&&!schoolAcademy.getAcademyName().equals("")) {
                    academyName=schoolAcademy.getAcademyName();
                    labAttendance.setAcademyName(academyName);
                }
            }
            //学号
            String username="";
            if (commonHdwlog2.getUsername()!=null&&!commonHdwlog2.getUsername().equals("")) {
                username=commonHdwlog2.getUsername();
            }
            if (!username.equals("")) {
                labAttendance.setUsername(username);
                User user = userDAO.findUserByPrimaryKey(username);
                //班级
                String className="";
                if (user.getSchoolClasses()!=null&&user.getSchoolClasses().getClassNumber()!=null
                        &&!user.getSchoolClasses().getClassNumber().equals("")
                        &&!user.getSchoolClasses().getClassName().equals("")) {
                    className=user.getSchoolClasses().getClassName();
                    labAttendance.setClassName(className);
                }
            }
            labAttendance.setStatus(commonHdwlog2.getStatus());
            labAttendanceList.add(labAttendance);
        }
        mav.addObject("accessList",labAttendanceList);

        mav.setViewName("/cmsReport/labEntranceList.jsp");
        return mav;
    }

    /**
     * Description 实验室资源--实验室详情
     * @param idKey
     * @return
     * @author 陈乐为 2018-10-26
     */
    @RequestMapping("/labInfo")
    public ModelAndView labInfo(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(idKey);
        mav.addObject("labRoom", labRoom);

        mav.setViewName("/cmsReport/labInfo.jsp");
        return mav;
    }
    /****************************************************************************
     * Description: 系统报表{实验室利用率报表--中心}
     * @author 贺子龙
     * @param termBack--从实验室利用率列表返回
     * @date 2016-10-10
     ****************************************************************************/
    @SuppressWarnings("unchecked")
    @RequestMapping("/reportLabRate")
    public ModelAndView reportLabRate(@RequestParam Integer termBack, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        // 当前学期
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        if (!EmptyUtil.isObjectEmpty(termBack)&&termBack!=-1) {
            termId = termBack;
        }
        // 选择
        if (!EmptyUtil.isStringEmpty(request.getParameter("term"))) {
            termId = Integer.parseInt(request.getParameter("term"));
        }
        mav.addObject("selectedTermId", termId);
        // 所有学期
        List<SchoolTerm> schoolTerms = shareService.findAllSchoolTerms();
        mav.addObject("selectTerms", schoolTerms);
        // 查询记录
        List<Object[]> labRates = reportService.getLabUseRate(request);
        // 横坐标：中心名称
        String xAxis = "";
        if(labRates != null && labRates.size() > 0){
            for(Object[] o: labRates){
                xAxis+="'"+o[1]+"',";
            }
        }
        mav.addObject("xAxis", xAxis);
        mav.addObject("labRates", labRates);
        mav.setViewName("/cmsReport/reportLabRate.jsp");
        return mav;
    }
    /****************************************************************************
     * Description: 实验室开放
     * @author 廖文辉
     * @param
     * @date 2018-10-29
     ****************************************************************************/
    @RequestMapping("/reportLabReservation")
    public ModelAndView reportLabReservation(){
        ModelAndView mav =new ModelAndView();
        List<LabReservation> labReservations = labReservationService.findLabReservationNoPage();
        mav.addObject("labReservations",labReservations);
        mav.setViewName("/cmsReport/labReservation.jsp");
        return mav;
    }
    /****************************************************************************
     * Description: 实验教学
     * @author 廖文辉
     * @param
     * @date 2018-10-29
     ****************************************************************************/
    @RequestMapping("/reportLabTeaching")
    public ModelAndView reportLabTeaching(){
        ModelAndView mav =new ModelAndView();
        List<OperationOutline> operationOutlines = outlineService.getOperationOutlineNoPage();
        mav.addObject("operationOutlines",operationOutlines);
        mav.setViewName("/cmsReport/reportTeaching.jsp");
        return mav;
    }
    /****************************************************************************
     * Description: 设备管理
     * @author 廖文辉
     * @param
     * @date 2019-1-7
     ****************************************************************************/
    @RequestMapping("/listLabRoomDevice")
    public ModelAndView listLabRoomDevice(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam Integer page, Integer isReservation,HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // 查询表单的对象
        mav.addObject("labRoomDevice", labRoomDevice);
        // 实验室
        List<LabRoom> rooms = labRoomService.findLabRoomWithDevices(isReservation);
        mav.addObject("rooms", rooms);
        int pageSize = 12;
        int pageSizeLabroom = 12;
        int totalRecordsLabroom = labRoomService.findLabRoomWithDevices(labRoomDevice, 0, 0, isReservation,"-1", request).size();
        // 实验室分页
        List<LabRoom> roomsList = labRoomService.findLabRoomWithDevices(labRoomDevice, page, pageSizeLabroom, isReservation,"-1", request);
        mav.addObject("roomsList", roomsList);

        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        //设备名称
        List<LabRoomDevice>labRoomDevices =labRoomDeviceService.getLabRoomDevice("-1");
        mav.addObject("labRoomDevices",labRoomDevices);
        // 查询出来的总记录条数
        /* int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", isReservation);*/
        int totalRecords = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", 1, -1, isReservation).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        Map<String, Integer> pageModelLabroom = shareService.getPage(page, pageSizeLabroom, totalRecordsLabroom);// listLabRoom.jsp页面分页

        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", page, pageSize, isReservation);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid("-1"));
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDeviceNew(labRoomDevice, "-1", 1, -1, isReservation);
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
        mav.setViewName("/cmsReport/listLabRoomDevice.jsp");

        return mav;
    }
    /****************************************************************************
     * Description: 设备详情
     * @author 廖文辉
     * @param
     * @date 2019-1-7
     ****************************************************************************/
    @RequestMapping("/listLabRoomDeviceNew")
    public ModelAndView listLabRoomDeviceNew(@ModelAttribute LabRoomDevice labRoomDevice, @RequestParam int roomId, Integer page, @ModelAttribute("is_reservation") Integer isReservtaion) {
        // 新建ModelAndView对象；
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
        List<LabRoom> rooms = labRoomService.findLabRoomWithDevices(isReservtaion);
        mav.addObject("rooms", rooms);
        int pageSize = 10;
        // 设备管理员
        List<User> users = shareService.findUsersByAuthorityId(10);
        mav.addObject("users", users);
        // 查询出来的总记录条数
        int totalRecords = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", roomId);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);// listLabRoomDevice.jsp页面分页
        // 获取所有的设备管理员
        mav.addObject("userMap", labRoomDeviceService.findDeviceManageCnamerByCid("-1"));
        // 根据分页信息查询出来的记录
        List<LabRoomDevice> listLabRoomDevice = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", page, pageSize, roomId);
        mav.addObject("listLabRoomDevice", listLabRoomDevice);
        // 查询所有设备记录
        List<LabRoomDevice> listLabRoomDeviceAll = labRoomDeviceService.findAllLabRoomDevice(labRoomDevice, "-1", 1, -1, roomId);
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

        mav.setViewName("/cmsReport/specialAcademyForLabroom.jsp");
        return mav;
    }
    /****************************************************************************
     * Description: 保修登记
     * @author 廖文辉
     * @param
     * @date 2019-1-7
     ****************************************************************************/
    @RequestMapping("/applyDeviceRepairList")
    public ModelAndView applyDeviceRepairList(@ModelAttribute LabRoomDeviceRepair lrdr, @RequestParam Integer page, Integer roomId) {
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

        mav.setViewName("/cmsReport/applyDeviceRepairList.jsp");
        return mav;
    }
    /****************************************************************************
     * @功能：中心下设备使用情况报表
     * @作者：廖文辉
     * @时间：2019-01-07
     ****************************************************************************/
    @RequestMapping("listLabRoomDeviceUsage")
    public ModelAndView listLabRoomDeviceUsage(HttpServletRequest request, @ModelAttribute LabRoomDeviceReservation reservation,
                                               @RequestParam int page, Integer roomId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("reservation", reservation);
        mav.addObject("roomId", roomId);

        // 获取当前登录人
        User user = shareService.getUser();
        String username = user.getUsername();
        username = "[" + username + "]";

        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomDeviceService.findLabRoomDeviceReservationByDeviceNumberAndCid(reservation, request, null,1, -1, 2, "-1").size();
        int pageSize = 20;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomDeviceReservation> reservationList = labRoomDeviceService.findLabRoomDeviceReservationByDeviceNumberAndCid(reservation, request, null, page, pageSize, 2, "-1");
        mav.addObject("reservationList", reservationList);
        List<LabRoomDeviceReservation> reservationLists = labRoomDeviceService.findLabRoomDeviceReservationByDeviceNumberAndCid(reservation, request, null, 1, -1, 2, "-1");
        // 找出设备预约对应的所有的申请人、指导老师、设备管理员、实验室
        Map<String, String> reserveUsers = new HashMap<String, String>();
        Map<String, String> teachers = new HashMap<String, String>();
        Map<String, String> manageUsers = new HashMap<String, String>();
        Map<Integer, String> labrooms = new HashMap<Integer, String>();
        Map<Integer, String> researchs = new HashMap<Integer, String>();
        for (LabRoomDeviceReservation labRoomDeviceReservation : reservationLists) {
            if (labRoomDeviceReservation.getUserByReserveUser() != null) {
                reserveUsers.put(labRoomDeviceReservation.getUserByReserveUser().getUsername(), labRoomDeviceReservation.getUserByReserveUser().getCname());
            }
            if (labRoomDeviceReservation.getUserByTeacher() != null) {
                teachers.put(labRoomDeviceReservation.getUserByTeacher().getUsername(), labRoomDeviceReservation.getUserByTeacher().getCname());
            }
            if (labRoomDeviceReservation.getLabRoomDevice().getUser() != null) {
                manageUsers.put(labRoomDeviceReservation.getLabRoomDevice().getUser().getUsername(), labRoomDeviceReservation.getLabRoomDevice().getUser().getCname());
            }
            if (labRoomDeviceReservation.getResearchProject() != null) {
                researchs.put(labRoomDeviceReservation.getResearchProject().getId(), labRoomDeviceReservation.getResearchProject().getName());
            }
            labrooms.put(labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getId(), labRoomDeviceReservation.getLabRoomDevice().getLabRoom().getLabRoomName());

        }
        mav.addObject("reserveUsers", reserveUsers);
        mav.addObject("teachers", teachers);
        mav.addObject("manageUsers", manageUsers);
        mav.addObject("researchs", researchs);
        mav.addObject("labrooms", labrooms);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 起止时间
        String begintime= request.getParameter("begintime");
        String endtime=	request.getParameter("endtime");

        mav.addObject("begintime", begintime);
        mav.addObject("endtime", endtime);

        mav.setViewName("/cmsReport/listLabRoomDeviceUsage.jsp");
        return mav;
    }



    /****************************************************************************
     * @功能：中心下设备使用情况报表
     * @作者：贺子龙
     * @时间：2016-05-19
     ****************************************************************************/
    @RequestMapping("/listLabRoomDeviceUsageInAppointment")
    public ModelAndView listLabRoomDeviceUsageInAppointment(HttpServletRequest request,
                                                            @RequestParam int page) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 30;
        // 查询记录
        List<Object[]> listLabRoomDeviceUsageInAppointments = labRoomDeviceService.getListLabRoomDeviceUsageInAppointment(request,page,pageSize);
        mav.addObject("listLabRoomDeviceUsageInAppointments",listLabRoomDeviceUsageInAppointments);

        int totalRecords = labRoomDeviceService.getListLabRoomDeviceUsageInAppointment(request,1,-1).size();
        mav.addObject("devices",labRoomDeviceService.getAllLabRoomDeviceUsageInAppointment());
        //所有排课相关项目
        mav.addObject("items",labRoomDeviceService.getAllTimetableRelatedItems());
        //上课教师
        mav.addObject("teachers",labRoomDeviceService.getAllTimetableRelatedTeachers());

        //所有课程
        mav.addObject("courses", labRoomDeviceService.getAllCoursesInAppointment(request));

        //所有学期
        mav.addObject("schoolTerms", shareService.findAllSchoolTerms());
        if(request.getParameter("deviceName") != null && !request.getParameter("deviceName").equals("")){
            mav.addObject("deviceName", request.getParameter("deviceName"));
        }
        if(request.getParameter("deviceNumber") != null && !request.getParameter("deviceNumber").equals("")){
            mav.addObject("deviceNumber", request.getParameter("deviceNumber"));
        }
        if(request.getParameter("courseName") != null && !request.getParameter("courseName").equals("")){
            mav.addObject("courseName", request.getParameter("courseName"));
        }
        if(request.getParameter("itemName") != null && !request.getParameter("itemName").equals("")){
            mav.addObject("itemName", request.getParameter("itemName"));
        }
        if(request.getParameter("teacherName") != null && !request.getParameter("teacherName").equals("")){
            mav.addObject("teacherName", request.getParameter("teacherName"));
        }
        if(request.getParameter("term") != null && !request.getParameter("term").equals("")){
            mav.addObject("term", request.getParameter("term"));
        }
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.addObject("pageSize",pageSize);
        mav.addObject("page", page);
        mav.addObject("totalRecords",totalRecords);
        mav.setViewName("/cmsReport/listLabRoomDeviceUsageInAppointment.jsp");
        return mav;
    }
    /****************************************************************************
     * Description: 保存保修
     * @author 廖文辉
     * @param
     * @date 2019-1-7
     ****************************************************************************/
    @RequestMapping("/saveLabRoomDeviceRepair")
    public String saveLabRoomDeviceRepair(@RequestParam int labRoomDeviceId, @ModelAttribute LabRoomDeviceRepair labRoomDeviceRepair,HttpServletRequest request) {
        if(labRoomDeviceId != -1){
            labRoomDeviceRepair.setLabRoomDevice(labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(labRoomDeviceId));
        }
        if(request!=null) {
            Calendar calendar = Calendar.getInstance();
            Calendar calendar1=Calendar.getInstance();
            String repairTime = request.getParameter("repairTime1");
            String restoreTime = request.getParameter("restoreTime1");
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date repairTimedate =sdf.parse(repairTime);
                calendar.setTime(repairTimedate);
                Date restoreTimedate=sdf1.parse(restoreTime);
                calendar1.setTime(restoreTimedate);
                labRoomDeviceRepair.setRepairTime(calendar);
                labRoomDeviceRepair.setRestoreTime(calendar1);
            }catch (ParseException e){
                e.printStackTrace();
            }

        }
        labRoomDeviceRepairDAO.store(labRoomDeviceRepair);
        return "redirect:/cmsReport/applyDeviceRepairList?page=1";
    }
}