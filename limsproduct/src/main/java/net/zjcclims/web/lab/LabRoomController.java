package net.zjcclims.web.lab;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import net.luxunsh.util.EmptyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.zjcclims.JsonDateValueProcessor;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.constant.LabAttendance;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.CommonDocumentService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.credit.CreditOptionService;
import net.zjcclims.service.device.LabRoomDeviceReservationCreditService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.dictionary.CDictionaryService;
import net.zjcclims.service.lab.*;
import net.zjcclims.service.message.MessageService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.report.LabWorkerService;
import net.zjcclims.service.software.SoftwareService;
import net.zjcclims.service.softwareRoomRelated.SoftwareRoomRelatedService;
import net.zjcclims.service.system.SchoolAcademyService;
import net.zjcclims.service.system.SystemBuildService;
import net.zjcclims.service.system.SystemRoomService;
import net.zjcclims.service.system.SystemService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.visualization.VisualizationService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("LabRoomController")
@SessionAttributes({"selected_academy", "isAudit"})
@RequestMapping("/labRoom")
public class LabRoomController<JsonResult> {

    @Autowired
    LabRoomDAO labRoomDAO;
    @Autowired
    LabRoomFurnitureDAO labRoomFurnitureDAO;
    @Autowired
    LabRoomFurnitureService labRoomFurnitureService;
    @Autowired
    OuterApplicationService outerApplicationService;
    @Autowired
    LabWorkerTrainingService labWorkerTrainingService;
    @Autowired
    LabRoomLendingDAO labRoomLendingDAO;
    @Autowired
    LabRoomLendingResultDAO labRoomLendingResultDAO;
    @Autowired
    LabWorkerTrainingDAO labWorkerTrainingDAO;
    @Autowired
    RemoteOpenDoorDAO remoteOpenDoorDAO;
    @Autowired
    CreditOptionDAO creditOptionDAO;
    @Autowired
    ViewUseOfLcDAO viewUseOfLcDAO;
    @Autowired
    private LabReservationService labReservationService;
    @Autowired
    RefuseItemBackupDAO refuseItemBackupDAO;
    @Autowired
    LabRoomLimitTimeDAO labRoomLimitTimeDAO;

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
        // //
        // static
        // //
        // property
        // //
        // editors.
        binder.registerCustomEditor(java.util.Calendar.class,
                new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(
                byte[].class,
                new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(Boolean.class,
                new org.skyway.spring.util.databinding.EnhancedBooleanEditor(
                        true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class,
                new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class,
                new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        Long.class, true));
        binder.registerCustomEditor(Double.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                        Double.class, true));
    }
    @PersistenceContext
    private EntityManager entityManager;
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
    private LabBaseService labBaseService;
    @Autowired
    private SoftwareRoomRelatedService softwareRoomRelatedService;
    @Autowired
    private SystemRoomService systemRoomService;
    @Autowired
    private SchoolAcademyService schoolAcademyService;
    @Autowired
    SoftwareDAO softwareDAO;
    @Autowired
    SoftwareRoomRelatedDAO softwareRoomRelatedDAO;
    @Autowired
    SoftwareService softwareService;
    @Autowired
    LabRoomDeviceService labRoomDeviceService;
    @Autowired
    CommonServerDAO commonServerDAO;
    @Autowired
    OperationItemDAO operationItemDAO;
    @Autowired
    CDictionaryDAO cDictionaryDAO;
    @Autowired
    LabRoomAgentDAO labRoomAgentDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    LabRoomAdminDAO labRoomAdminDAO;
    @Autowired
    AuthorityDAO authorityDAO;
    @Autowired
    SchoolDeviceService schoolDeviceService;
    @Autowired
    LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    LabAnnexService labAnnexService;
    @Autowired
    LabRoomReservationService labRoomReservationService;
    @Autowired
    MessageService messageService;
    @Autowired
    LabWorkerService labWorkerService;
    @Autowired
    LabWorkerDAO labWorkerDAO;
    @Autowired
    LabRoomPermitUserDAO labRoomPermitUserDAO;
    @Autowired
    LabRoomTrainingPeopleDAO labRoomTrainingPeopleDAO;
    @Autowired
    LabRoomTrainingDAO labRoomTrainingDAO;
    @Autowired
    CommonDocumentService commonDocumentService;
    @Autowired
    LabRoomDeviceReservationCreditService labRoomDeviceReservationCreditService;
    @Autowired
    OperationService operationService;
    @Autowired
    LabRoomAdminService labRoomAdminService;
    @Autowired
    CreditOptionService creditOptionService;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private SystemBuildService systemBuildService;
    @Autowired
    private VisualizationService visualizationService;

    /**
     * 实验室列表
     *
     * @author hly 2015.07.28
     */
    @RequestMapping("/listLabRoom")
    public ModelAndView listLabRoom(@RequestParam int currpage, int orderBy,int type,
                                    @ModelAttribute LabRoom labRoom,
                                    @ModelAttribute("selected_academy") String acno, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        int totalRecords=0;
        if(type==ConstantInterface.LAB_FOR_ROOM) {// 实验室
            totalRecords = labRoomService.findLabRoomByLabCenter(1, -1, 1, labRoom,9, request, acno).size();
            mav.addObject("listLabRoom", labRoomService.findLabRoomByLabCenter(currpage, pageSize,1, labRoom, orderBy, request,acno));
            // 本学院所有可用实验室
            List<LabRoom> labRoomList = labRoomService.findLabRoomByLabCenter(1, -1, 1, null, 9, request, acno);
            mav.addObject("labRoomList", labRoomList);
            mav.setViewName("lab/lab_room/listLabRoom.jsp");
        }else if(type==ConstantInterface.LAB_FOR_WORKSPACE){// 工作室
            totalRecords = labRoomService.findLabRoomByLabCenter(1, -1, 2, labRoom,9, request, acno).size();
            mav.addObject("listLabWorkRoom",labRoomService.findLabRoomByLabCenter(currpage, pageSize,2, labRoom, orderBy, request,acno));
            mav.setViewName("lab/lab_workroom/listLabWorkRoom.jsp");
        }else if(type==ConstantInterface.LAB_FOR_MEETING){// 会议室
            totalRecords = labRoomService.findLabRoomByLabCenter(1, -1, 3, labRoom,9, request, acno).size();
            mav.addObject("listLabMeetingRoom",labRoomService.findLabRoomByLabCenter(currpage,pageSize,3,labRoom,orderBy,request,acno));
            mav.setViewName("lab/lab_meeting/listLabMeetingRoom.jsp");
        }else if(type==ConstantInterface.LAB_FOR_CLASSROOM){// 班级
            totalRecords = labRoomService.findLabRoomByLabCenter(1,-1,4,labRoom,9,request,acno).size();
            mav.addObject("listClassRoom",labRoomService.findLabRoomByLabCenter(currpage,pageSize,4,labRoom,orderBy,request,acno));
            mav.setViewName("lab/lab_room/listClassRoom.jsp");
        }
        mav.addObject("labRoom", labRoom);
        mav.addObject("pageModel",
                shareService.getPage(currpage, pageSize, totalRecords));

        // 决定升序还是降序
        boolean asc = true;
        if (orderBy < 10) {
            asc = false;
        }
        mav.addObject("newServer", pConfig.newServer);
        mav.addObject("asc", asc);
        mav.addObject("orderBy", orderBy);
        mav.addObject("worker", request.getParameter("worker"));
        mav.addObject("area", request.getParameter("area"));
        mav.addObject("searchflg", request.getParameter("searchflg"));
        mav.addObject("searchflg1", request.getParameter("searchflg1"));
        // 有无多媒体
        mav.addObject("isMultimedia", shareService.getCDictionaryData("c_is_multimedia"));
        //读取左侧栏显示配置文件
        mav.addObject("baseManage", pConfig.baseManage);
        //实验中心下拉框
        mav.addObject("listLabCenter",labCenterService.findAllCenters());
        mav.addObject("type",type);
        mav.addObject("page",currpage);
        // 获取权限等级
        String auth = request.getSession().getAttribute("selected_role").toString();
        int authLevel = shareService.getLevelByAuthName(auth);
        mav.addObject("authLevel", authLevel);
        if(authLevel < 6 && authLevel > 0) {
            // 本学院所有在校教师及近5年学生
            List<User> userList = labRoomService.findUserByacno(acno);
            mav.addObject("userList", userList);
        }
        // 当前用户
        mav.addObject("username", shareService.getUserDetail().getUsername());

        return mav;
    }
    /************************************************************
      * @功能：实验室与设备联动查询
      * @作者：廖文辉
      * @时间：2018.9.5
      ************************************************************/
    @RequestMapping("findLabAnnexByLabCenter")
    @ResponseBody
    public Map<String,String> findLabAnnexByLabCenter(@RequestParam String  labCenter){
                Map<String,String> map=new HashMap<String, String>();
                String labAnnexValue=labAnnexService.findLabAnnexByLabCenter(labCenter);
                map.put("labAnnex",labAnnexValue);
                return map;
            }

    /**
     * Description 编辑实验分室
     * @param type
     * @param labRoomId
     * @param acno
     * @return
     * @author 陈乐为 2018-9-27
     */
    @RequestMapping("/editLabRoom")
    public ModelAndView editLabRoom(@RequestParam int labRoomId,int type, @ModelAttribute("selected_academy") String acno,HttpServletRequest request,int page) {
        ModelAndView mav = new ModelAndView();
        if(labRoomId>0) {
            LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
            mav.addObject("labRoom", labRoom);
            // 楼层名
            if(labRoom.getSystemBuild() != null && !labRoom.getSystemBuild().getBuildNumber().equals("")) {
                List<SystemFloorPic> systemFloorPics = visualizationService.findSystemFloorPic(labRoom.getSystemBuild().getBuildNumber(), null);
                mav.addObject("floors", systemFloorPics);
            }
        }else {
            mav.addObject("labRoom", new LabRoom());
        }
//        mav.addObject("labRoomId",labRoomId);
        //读取左侧栏显示配置文件
        mav.addObject("annexManage", pConfig.annexManage);
        mav.addObject("baseManage", pConfig.baseManage);
        if("true".equals(pConfig.annexManage)) {
            List<LabAnnex> labAnnexList = labAnnexService.findLabAnnexByLabType(-1,acno);
            mav.addObject("labAnnexList",labAnnexList);
        }
        if("true".equals(pConfig.baseManage)) {
            List<LabAnnex> labBaseList = labAnnexService.findLabAnnexByLabType(4,acno);
            mav.addObject("labBaseList",labBaseList);
        }
        mav.addObject("stationNum", pConfig.stationNum);
        mav.addObject("project", pConfig.PROJECT_NAME);

        // 实验室类型
        mav.addObject("labRoomTypes", shareService.getCDictionaryData("c_lab_room_type"));
        // 实验室分类
        mav.addObject("labRoomSorts", shareService.getCDictionaryData("c_lab_room_sort"));
        // 有无多媒体
        mav.addObject("isMultimedia", shareService.getCDictionaryData("c_is_multimedia"));
        // 实验室类别
        mav.addObject("labRoomClassifications", shareService.getCDictionaryData("c_lab_room_classification"));
        // 实验室地点
//        mav.addObject("listSystemRoom", systemRoomService.findAllSystemRoomByQuery(new SystemRoom(), 1, -1));
        // 学科
        mav.addObject("subject12s", systemService.getAllSystemSubject12(1, -1)); // 学科数据(12版)
//        if(type==ConstantInterface.LAB_FOR_MEETING){
//            //管理员
//            List<User> userList=labRoomService.findUserByAcademy(labRoomId,acno);
//            mav.addObject("userList",userList);
//        }
        // 中心
        LabCenter labCenter = new LabCenter();
        labCenter.setSchoolAcademy(shareService.findSchoolAcademyByPrimaryKey(acno));
        mav.addObject("listLabCenter", labCenterService.findAllLabCenterByQuery(request,labCenter, 1, -1));
        mav.addObject("page",page);
        // 楼宇
        List<SystemBuild> systemBuilds = systemBuildService.finAllSystemBuilds();
        mav.addObject("systemBuilds", systemBuilds);
        if(type==ConstantInterface.LAB_FOR_ROOM) {
            mav.setViewName("lab/lab_room/editLabRoom.jsp");
        }else if(type==ConstantInterface.LAB_FOR_WORKSPACE){
            mav.setViewName("lab/lab_workroom/editLabWorkRoom.jsp");
        }else if(type==ConstantInterface.LAB_FOR_MEETING){
            mav.setViewName("lab/lab_meeting/editLabMeetingRoom.jsp");
        }else if(type==ConstantInterface.LAB_FOR_CLASSROOM){
            mav.setViewName("lab/lab_room/editClassRoom.jsp");
        }
        return mav;
    }

    /**
     * Description 保存实验分室
     * @param labRoom
     * @param type
     * @param request
     * @return
     * @author 陈乐为 2018-9-27
     */
    @RequestMapping("/saveLabRoom")
    public ModelAndView saveLabRoom(@ModelAttribute LabRoom labRoom,int type,
                              HttpServletRequest request,@ModelAttribute("selected_academy") String acno,int page) {
        ModelAndView mav=new ModelAndView();
        //开放范围
        LabRoom labRoom1 = labRoomDAO.findLabRoomByPrimaryKey(labRoom.getId());
        if (labRoom1 == null) {
            labRoom1 = new LabRoom();
        }
        /////////////////////////////////编辑字段开始///////////////////////////////////
        // 实验室编号
        labRoom1.setLabRoomNumber(labRoom.getLabRoomNumber());
        // 实验室名称
        labRoom1.setLabRoomName(labRoom.getLabRoomName());
        // 所属中心
        labRoom1.setLabCenter(labRoom.getLabCenter());
        // 所属实验室
        if(!EmptyUtil.isObjectEmpty(labRoom) && !EmptyUtil.isObjectEmpty(labRoom.getLabAnnex())
                && !EmptyUtil.isIntegerEmpty(labRoom.getLabAnnex().getId())) {
            labRoom1.setLabAnnex(labBaseService.findLabAnnexByPrimaryKey(labRoom.getLabAnnex().getId()));
        }else {
            labRoom1.setLabAnnex(null);
        }
        // 所属楼宇
        labRoom1.setSystemBuild(labRoom.getSystemBuild());
        // 所在楼层
        labRoom1.setFloorNo(labRoom.getFloorNo());
        // 浙江建设--所有实验室允许跨学院排课
        if (pConfig.PROJECT_NAME.equals("zjcclims")) {
            labRoom1.setIsOpen(1);
        }
        // 可预约工位数
        labRoom1.setLabRoomWorker(labRoom.getLabRoomWorker());
        //////////////////////////////////页面附加信息/////////////////////////////////
        // 基地
        if(!EmptyUtil.isObjectEmpty(labRoom) && !EmptyUtil.isObjectEmpty(labRoom.getLabBase())
                && !EmptyUtil.isIntegerEmpty(labRoom.getLabBase().getId())) {
            labRoom1.setLabBase(labBaseService.findLabAnnexByPrimaryKey(labRoom.getLabBase().getId()));
        }else {
            labRoom1.setLabBase(null);
        }
        // 地点
        if(labRoom.getSystemRoom()==null || EmptyUtil.isStringEmpty(labRoom.getSystemRoom().getRoomNumber())) {
            labRoom1.setSystemRoom(null);
        }else {
            labRoom1.setSystemRoom(labRoom.getSystemRoom());
        }
        // 实验室等级
        labRoom1.setLabRoomLevel(labRoom.getLabRoomLevel());
        // 实验室类别
        if(labRoom.getCDictionaryByLabRoomClassification()==null || labRoom.getCDictionaryByLabRoomClassification().getId()==null) {
            labRoom1.setCDictionaryByLabRoomClassification(null);
        }else {
            labRoom1.setCDictionaryByLabRoomClassification(labRoom.getCDictionaryByLabRoomClassification());
        }
        // 建立时间
        labRoom1.setLabRoomTimeCreate(labRoom.getLabRoomTimeCreate());
        // 实验室容量
        labRoom1.setLabRoomCapacity(labRoom.getLabRoomCapacity());
        // 使用面积
        labRoom1.setLabRoomArea(labRoom.getLabRoomArea());
        // 实验室分类
        if(labRoom.getCDictionaryByLabRoomSort()==null || labRoom.getCDictionaryByLabRoomSort().getId()==null) {
            labRoom1.setCDictionaryByLabRoomSort(null);
        }else {
            labRoom1.setCDictionaryByLabRoomSort(labRoom.getCDictionaryByLabRoomSort());
        }
        // 实验室类型
        if(labRoom.getCDictionaryByLabRoom()==null || labRoom.getCDictionaryByLabRoom().getId()==null) {
            labRoom1.setCDictionaryByLabRoom(null);
        }else {
            labRoom1.setCDictionaryByLabRoom(labRoom.getCDictionaryByLabRoom());
        }
        // 有无多媒体
        if(labRoom.getCDictionaryByIsMultimedia()==null || labRoom.getCDictionaryByIsMultimedia().getId()==null) {
            labRoom1.setCDictionaryByIsMultimedia(null);
        }else {
            labRoom1.setCDictionaryByIsMultimedia(labRoom.getCDictionaryByIsMultimedia());
        }
        // 所属学科
        if(labRoom.getSystemSubject12()==null || labRoom.getSystemSubject12().getSNumber()==null
                || labRoom.getSystemSubject12().getSNumber().equals("")) {
            labRoom1.setSystemSubject12(null);
        }else {
            labRoom1.setSystemSubject12(labRoom.getSystemSubject12());
        }
        // 是否可用
        labRoom1.setLabRoomActive(labRoom.getLabRoomActive());
        // 是否校企共建
        labRoom1.setIsSchoolEnterpriseCooperation(labRoom.getIsSchoolEnterpriseCooperation());
        // 是否生产性实验室
        labRoom1.setIsRoductivity(labRoom.getIsRoductivity());
        // 是否仿真实验室
        labRoom1.setIsSimulation(labRoom.getIsSimulation());
        // 实验室描述
        labRoom1.setLabRoomIntroduction(labRoom.getLabRoomIntroduction());
        // 规章制度
        labRoom1.setLabRoomRegulations(labRoom.getLabRoomRegulations());
        // 实验室注意事项
        labRoom1.setLabRoomAttentions(labRoom.getLabRoomAttentions());
        // 获奖信息
        labRoom1.setLabRoomPrizeInformation(labRoom.getLabRoomPrizeInformation());
        ///////////////////////////页面编辑字段结束///////////////////////////////////
        ///////////////////////////关联更新数据开始///////////////////////////////////
        // 学院
        if(labRoom.getLabCenter()!=null && labRoom.getLabCenter().getSchoolAcademy()!=null
                && labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber()!=null) {
            labRoom1.setSchoolAcademy(labRoom.getLabCenter().getSchoolAcademy());
        }else if(acno!=null && !acno.equals("-1")) {
            labRoom1.setSchoolAcademy(shareService.findSchoolAcademyByPrimaryKey(acno));
        }
        ///////////////////////////关联更新数据结束///////////////////////////////////

//        // 审核
//        if(labRoom.getCDictionaryByIsAudit()==null || labRoom.getCDictionaryByIsAudit().getId()==null) {
//            labRoom1.setCDictionaryByIsAudit(null);
//        }else {
//            labRoom1.setCDictionaryByIsAudit(labRoom.getCDictionaryByIsAudit());
//        }
//        // 借用
//        if(labRoom.getCDictionaryByAllowLending()==null || labRoom.getCDictionaryByAllowLending().getId()==null) {
//            labRoom1.setCDictionaryByAllowLending(null);
//        }else {
//            labRoom1.setCDictionaryByAllowLending(labRoom.getCDictionaryByAllowLending());
//        }
//        // 安全准入
//        if(labRoom.getCDictionaryByAllowSecurityAccess()==null || labRoom.getCDictionaryByAllowSecurityAccess().getId()==null) {
//            labRoom1.setCDictionaryByAllowSecurityAccess(null);
//        }else {
//            labRoom1.setCDictionaryByAllowSecurityAccess(labRoom.getCDictionaryByAllowSecurityAccess());
//        }
//        // 培训形式
//        if(labRoom.getCDictionaryByTrainType()==null || labRoom.getCDictionaryByTrainType().getId()==null) {
//            labRoom1.setCDictionaryByTrainType(null);
//        }else {
//            labRoom1.setCDictionaryByTrainType(labRoom.getCDictionaryByTrainType());
//        }
        if (labRoom.getId() == null) {
            labRoom1.setIsUsed(1);
            labRoom1.setReservationNumber(1);
        }
        if(type==ConstantInterface.LAB_FOR_ROOM){
            labRoom1.setLabCategory(1);
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=1");
        }else if(type==ConstantInterface.LAB_FOR_WORKSPACE){
            labRoom1.setLabCategory(2);
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=2");
        }else if(type==ConstantInterface.LAB_FOR_MEETING){
            labRoom1.setLabCategory(3);
            CDictionary cDictionary=cDictionaryDAO.findCDictionaryById(labRoom.getCDictionaryByIsMultimedia().getId());
            labRoom1.setCDictionaryByIsMultimedia(cDictionary);
            labRoom1=labRoomService.saveLabRoom(labRoom1);
//            String username=request.getParameter("labRoomAdmin");
//            User user=userDAO.findUserByUsername(username);
//            LabRoomAdmin labRoomAdmin=new LabRoomAdmin();
//            labRoomAdmin.setUser(user);
//            labRoomAdmin.setLabRoom(labRoom);
//            labRoomAdmin.setTypeId(1);
//            labRoomAdminDAO.store(labRoomAdmin);
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=3");
        }else if(type==ConstantInterface.LAB_FOR_CLASSROOM){
            labRoom1.setLabCategory(4);
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=4");
        }
        labRoomService.saveLabRoom(labRoom1);
        return  mav;
    }
    /**
     * 删除实验室数据
     *
     * @author hly 2015.07.28
     */
    @RequestMapping("/deleteLabRoom")
    public ModelAndView deleteLabRoom(@RequestParam int labRoomId,int type,int page) {
        ModelAndView mav =new ModelAndView();
        LabRoom room = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        room.setIsUsed(0);// 假删
        labRoomService.saveLabRoom(room);
        // labRoomService.deleteLabRoom(labRoomId); 真删 由于和实验项目的外键关系，会报错删不掉
        if(type==ConstantInterface.LAB_FOR_ROOM){
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=1");
        }else if(type==ConstantInterface.LAB_FOR_WORKSPACE){
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=2");
        }else if(type==ConstantInterface.LAB_FOR_MEETING){
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=3");
        }else if(type==ConstantInterface.LAB_FOR_CLASSROOM){
            mav.setViewName("redirect:/labRoom/listLabRoom?currpage="+page+"&orderBy=9&type=4");
        }
        return  mav;
    }
    /**
     * 实验室工作人员列表
     *
     * @author hly 2015.07.29
     */
    @RequestMapping("/listLabWorker")
    public ModelAndView listLabWorker(@RequestParam int currpage,
                                      @ModelAttribute LabWorker labWorker) {
        ModelAndView mav = new ModelAndView();
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        int totalRecords = labRoomService.findAllLabWorkerByQuery(1, -1,
                labWorker).size();

        mav.addObject("listLabWorker", labRoomService.findAllLabWorkerByQuery(
                currpage, pageSize, labWorker));
        mav.addObject("pageModel",
                shareService.getPage(currpage, pageSize, totalRecords));
        mav.setViewName("lab/lab_worker/listLabWorker.jsp");
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", currpage);
        mav.addObject("pageSize", pageSize);
        return mav;
    }

    /**
     * 新建实验室工作人员
     *
     * @author hly 2015.07.29
     */
    @RequestMapping("/newLabWorker")
    public ModelAndView newLabWorker(
            @ModelAttribute("selected_academy") String acno, HttpServletRequest request,int page) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("labWorker", new LabWorker());
        mav.addObject("listLabCenter", labCenterService
                .findAllLabCenterByQuery(request, new LabCenter(), 1, -1));
        // mav.addObject("listUser",
        // outerApplicationService.getTimetableTearcherMap());
        mav.addObject("listUser",
                outerApplicationService.getAllTearchersMap(acno));
        mav.addObject("listAcademicDegree", shareService
                .getCDictionaryData("category_lab_worker_academic_degree")); // 文化程度
        mav.addObject("listSubject",
                shareService.getCDictionaryData("category_lab_worker_subject")); // 所属学科
        mav.addObject("listSpecialtyDuty", shareService
                .getCDictionaryData("category_lab_worker_specialty_duty")); // 专业职称
        mav.addObject("listCategoryStaff", shareService
                .getCDictionaryData("category_lab_worker_category_staff")); // 人员类别
        mav.addObject("listEmployment", shareService
                .getCDictionaryData("category_lab_worker_employment")); // 聘任情况
        mav.addObject("listReward",
                shareService.getCDictionaryData("category_lab_worker_reward")); // 成果奖励
        mav.addObject("listForeignLanguage", shareService
                .getCDictionaryData("category_lab_worker_foreign_language")); // 外语语种
        mav.addObject(
                "listForeignLanguageLevel",
                shareService
                        .getCDictionaryData("category_lab_worker_foreign_language_level")); // 外语水平
        mav.addObject("listDegree",
                shareService.getCDictionaryData("category_lab_worker_degree")); // 学位
        mav.addObject("listMainWork", shareService
                .getCDictionaryData("category_lab_worker_main_work")); // 主要工作
        mav.addObject("listPaperLevel", shareService
                .getCDictionaryData("category_lab_worker_paper_level")); // 论文级别
        mav.addObject("listBookLevel", shareService
                .getCDictionaryData("category_lab_worker_book_level")); // 著作级别
        mav.addObject("listCategoryExpert", shareService
                .getCDictionaryData("category_lab_worker_category_expert")); // 专家类别
        mav.addObject("listSpecialtyDuty", shareService
                .getCDictionaryData("category_lab_worker_specialty_duty"));// 专业技术职务
        mav.addObject("listLabRoom", labRoomService.findLabRoomList());// 所属实训室
        mav.addObject("page",page);
        //所有学院
        List<SchoolAcademy> allSchoolAcademys = shareService.findAllSchoolAcademys();
        mav.addObject("listSchoolAcademy", allSchoolAcademys);
        mav.setViewName("lab/lab_worker/editLabWorker.jsp");
        return mav;
    }

    /**
     * 编辑实验室工作人员
     *
     * @author hly 2015.07.29
     */
    @RequestMapping("/editLabWorker")
    public ModelAndView editLabWorker(@RequestParam int labWorkerId,
                                      @ModelAttribute("selected_academy") String acno, HttpServletRequest request,int page) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("labWorker",
                labRoomService.findLabWorkerByPrimaryKey(labWorkerId));
        mav.addObject("listLabCenter", labCenterService
                .findAllLabCenterByQuery(request, new LabCenter(), 1, -1));
        mav.addObject("listUser",
                outerApplicationService.getAllTearchersMap(acno));
        mav.addObject("listAcademicDegree", shareService
                .getCDictionaryData("category_lab_worker_academic_degree")); // 文化程度
        mav.addObject("listSubject",
                shareService.getCDictionaryData("category_lab_worker_subject")); // 所属学科
        mav.addObject("listSpecialtyDuty", shareService
                .getCDictionaryData("category_lab_worker_specialty_duty")); // 专业职称
        mav.addObject("listCategoryStaff", shareService
                .getCDictionaryData("category_lab_worker_category_staff")); // 人员类别
        mav.addObject("listEmployment", shareService
                .getCDictionaryData("category_lab_worker_employment")); // 聘任情况
        mav.addObject("listReward",
                shareService.getCDictionaryData("category_lab_worker_reward")); // 成果奖励
        mav.addObject("listForeignLanguage", shareService
                .getCDictionaryData("category_lab_worker_foreign_language")); // 外语语种
        mav.addObject(
                "listForeignLanguageLevel",
                shareService
                        .getCDictionaryData("category_lab_worker_foreign_language_level")); // 外语水平
        mav.addObject("listDegree",
                shareService.getCDictionaryData("category_lab_worker_degree")); // 学位
        mav.addObject("listMainWork", shareService
                .getCDictionaryData("category_lab_worker_main_work")); // 主要工作
        mav.addObject("listPaperLevel", shareService
                .getCDictionaryData("category_lab_worker_paper_level")); // 论文级别
        mav.addObject("listBookLevel", shareService
                .getCDictionaryData("category_lab_worker_book_level")); // 著作级别
        mav.addObject("listCategoryExpert", shareService
                .getCDictionaryData("category_lab_worker_category_expert")); // 专家类别
        mav.addObject("listLabRoom", labRoomService.findLabRoomList());// 所属实训室
        mav.addObject("listSchoolAcademy",schoolAcademyService.findAllSchoolAcademyByQuery(new SchoolAcademy(),-1,-1));//所属学院
        mav.addObject("page",page);
        mav.setViewName("lab/lab_worker/editLabWorker.jsp");
        return mav;
    }

    /**
     * 保存实验室工作人员数据
     *
     * @author hly 2015.07.29
     */
    @RequestMapping("/saveLabWorker")
    public String saveLabWorker(@ModelAttribute LabWorker labWorker,int page) {
        labRoomService.saveLabWorker(labWorker);

        return "redirect:/labRoom/listLabWorker?currpage="+page;
    }

    /**
     * 删除实验室工作人员
     *
     * @author hly 2015.07.29
     */
    @RequestMapping("/deleteLabWorker")
    public String deleteLabWorker(@RequestParam int labWorkerId,int page) {
        if (labWorkerTrainingService.findLabWorkerTrainingDetailByLabWorkerId(
                labWorkerId).size() == 0) {
            labRoomService.deleteLabWorker(labWorkerId);
        } else {

        }

        return "redirect:/labRoom/listLabWorker?currpage="+page;
    }

    /****************************************************************************
     * 功能：查看实验分室详情 作者：贺子龙 时间：2015-09-03
     ****************************************************************************/
    @RequestMapping("/getLabRoom")
    public ModelAndView getLabRoom(@RequestParam Integer id, String labRoomName,@RequestParam int type,
                                   @ModelAttribute SchoolDevice schoolDevice, OperationItem operationItem, ViewUseOfLc viewUseOfLc,
                                   @ModelAttribute Software software, @RequestParam Integer currpage, HttpServletRequest request) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUser();
        mav.addObject("user", user);
        String academyNumber="";
        if(user.getSchoolAcademy().getAcademyNumber()!=null){
            academyNumber=user.getSchoolAcademy().getAcademyNumber();
        }
//        List<User> userList=labRoomService.findUserByacno(academyNumber);
//        mav.addObject("userList",userList);
        boolean flag = labRoomService.getLabRoomAdminReturn(id, user);
        mav.addObject("flag", flag);
        // System.out.println(flag);
        // id对应的实验分室信息
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(id);
        mav.addObject("labRoom", labRoom);
        // 获取所有实验项目
        //List<OperationItem> operationItems = labRoomService.findAllOperationItemByRoomId(id);
        //mav.addObject("operationItems", operationItems);
        Set<OperationItem> ops=labRoom.getOperationItems();
        mav.addObject("operationItems", ops);
        // 根据查询条件获取实验项目
        List<OperationItem> operationItems = new ArrayList<>();
        if(!"".equals(operationItem.getLpName()) && operationItem.getLpName() != null){
            for(OperationItem op:ops){
                if(op.getLpName().equals(operationItem.getLpName())){
                    operationItems.add(op);
                }
            }
            mav.addObject("operationItem1", operationItems);
        } else if (operationItem.getSchoolCourseInfo() != null
                && !"".equals(operationItem.getSchoolCourseInfo().getCourseName()) && operationItem.getSchoolCourseInfo().getCourseName() != null) {
            for (OperationItem op : ops) {
                if (op.getSchoolCourseInfo().getCourseName().equals(operationItem.getSchoolCourseInfo().getCourseName())) {
                    operationItems.add(op);
                }
            }
            mav.addObject("operationItem1", operationItems);
        }else if(!"".equals(operationItem.getLpName()) && operationItem.getLpName() != null && operationItem.getSchoolCourseInfo() != null
                && !"".equals(operationItem.getSchoolCourseInfo().getCourseName()) && operationItem.getSchoolCourseInfo().getCourseName() != null){
            for (OperationItem op : ops) {
                if (op.getSchoolCourseInfo().getCourseName().equals(operationItem.getSchoolCourseInfo().getCourseName())) {
                    if(op.getLpName().equals(operationItem.getLpName())){
                        operationItems.add(op);
                    }
                }
            }
            mav.addObject("operationItem1", operationItems);
        }else {
            mav.addObject("operationItem1", ops);
        }

        // 获取所有电脑使用记录
        labRoomName = labRoom.getLabRoomName();
        Set<ViewUseOfLc> allViewUseOfLcLists = viewUseOfLcDAO.findViewUseOfLcByLabRoomName(labRoomName);
        mav.addObject("allViewUseOfLcLists", allViewUseOfLcLists);
        // 根据查询条件获取电脑使用记录
        List<ViewUseOfLc> allViewUseOfLcList = labRoomDeviceService.findAllallViewUseOfLcListByLabRoomName(viewUseOfLc, labRoomName, request);
        mav.addObject("allViewUseOfLcList", allViewUseOfLcList);
        // 所有的实验项目卡
        List<OperationItem> items = null;
        if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null) {
            items = labRoomService.findAllOperationItem(user.getSchoolAcademy().getAcademyNumber());
        }else {
            items = labRoomService.findAllOperationItem("-1");
        }
        mav.addObject("items", items);
        // 实验室家具
        List<LabRoomFurniture> labRoomFurniture = labRoomFurnitureService
                .findLabRoomFurnitureByRooId(id);
        mav.addObject("labRoomFurniture", labRoomFurniture);
        mav.addObject("labRoomFurnitures", new LabRoomFurniture());
        // 根据实验室查询实验室管理员
        List<LabRoomAdmin> adminList = labRoomDeviceService
                .findLabRoomAdminByRoomId(id, 1);
        mav.addObject("adminList", adminList);
        // 实验室物联管理员
        List<LabRoomAdmin> agentAdmin = labRoomDeviceService
                .findLabRoomAdminByRoomId(id, 2);
        mav.addObject("agentAdmin", agentAdmin);
        // 被授权人员
        List<LabRoomAdmin> authorizeUsers = labRoomDeviceService
                .findLabRoomAdminByRoomId(id, 3);
        mav.addObject("authorizeUsers", authorizeUsers);
        // 实验室管理员
        mav.addObject("admin", new LabRoomAdmin());
        // 物联硬件
        mav.addObject("agent", new LabRoomAgent());
        List<LabRoomAgent> agentList = labRoomService
                .findLabRoomAgentByRoomId(id);
        mav.addObject("agentList", agentList);
        // 判断中控是否已经添加
        for(LabRoomAgent agent : agentList) {
            if(agent.getCDictionary().getCNumber().equals("7")) {
                mav.addObject("isCtrl", true);
            }
        }
        // 物联硬件类型
        List<CDictionary> types = cDictionaryService.findallCType();
        mav.addObject("types", types);
        // 物联硬件服务器
        Set<CommonServer> serverList = commonServerDAO.findAllCommonServers();
        mav.addObject("serverList", serverList);

        // 根据实验室id查询实验室软件
        List<Software> softwareList = softwareService.findSoftwareByRoomId(id);
        mav.addObject("softwareList", softwareList);

        // 根据实验分室id查询实验室设备
        List<LabRoomDevice> labDeviceList = labRoomDeviceService
                .findLabRoomDeviceByRoomId(id);
        mav.addObject("labDeviceList", labDeviceList);
        // 设备查询表单对象
        mav.addObject("schoolDevice", schoolDevice);
        // 设备信息设置表单对象
        mav.addObject("labRoomDevice", new LabRoomDevice());
        mav.addObject("pageModel", shareService.getPage(currpage, 1, 1));
        mav.addObject("type",type);
        // 门禁
        for (LabRoomAgent a : agentList) {
            if (a.getCDictionary().getCCategory().equals("c_agent_type") && a.getCDictionary().getCNumber().equals("2")) {
                mav.addObject("Access", a);
            }
        }
        // 云台参数
        mav.addObject("yuntai",pConfig.yuntai);
        //禁用时间段
        // 实验室禁用时间段列表
        mav.addObject("labRoomLimitTimes",
                labRoomLimitTimeDAO.executeQuery("select c from LabRoomLimitTime c where (c.type IS NULL OR c.type = 1) and c.labId= " + id, 0, -1));
        //开放设置
        if (labRoom.getOpenInweekend() == null
                || labRoom.getOpenInweekend() != null && labRoom.getOpenInweekend() == 0) {
            mav.addObject("openInweekend", 0);
        } else {
            mav.addObject("openInweekend", 1);
        }
        if (labRoom.getStartHour() != null) {
            Double startTime = labRoom.getStartHour().doubleValue();
            Integer startHour = (int) Math.floor(startTime);
            mav.addObject("startHour", startHour);
            mav.addObject("startMinute", (startTime - startHour) * 60);
        } else {
            mav.addObject("startHour", -1);
            mav.addObject("startMinute", -1);
        }
        if (labRoom.getEndHour() != null) {
            Double endTime = labRoom.getEndHour().doubleValue();
            Integer endHour = (int) Math.floor(endTime);
            mav.addObject("endHour", endHour);
            mav.addObject("endMinute", (endTime - endHour) * 60);
        } else {
            mav.addObject("endHour", -1);
            mav.addObject("endMinute", -1);
        }
        if (labRoom.getStartHourInweekend() != null) {
            Double startWeekendTime = labRoom.getStartHourInweekend().doubleValue();
            Integer startWeekendHour = (int) Math.floor(startWeekendTime);
            mav.addObject("startWeekendHour", startWeekendHour);
            mav.addObject("startWeekendMinute", (startWeekendTime - startWeekendHour) * 60);
        } else {
            mav.addObject("startWeekendHour", -1);
            mav.addObject("startWeekendMinute", -1);
        }
        if (labRoom.getEndHourInweekend() != null) {
            Double endWeekendTime = labRoom.getEndHourInweekend().doubleValue();
            Integer endWeekendHour = (int) Math.floor(endWeekendTime);
            mav.addObject("endWeekendHour", endWeekendHour);
            mav.addObject("endWeekendMinute", (endWeekendTime - endWeekendHour) * 60);
        } else {
            mav.addObject("endWeekendHour", -1);
            mav.addObject("endWeekendMinute", -1);
        }
        // 权限等级
        int authLevel = shareService.getLevelByAuthName(request.getSession().getAttribute("selected_role").toString());
        mav.addObject("authLevel", authLevel);
        List<Object[]> logs=labRoomService.getRefuseItemBackup(id);
        mav.addObject("logs",logs);
        mav.addObject("newServer", pConfig.newServer);
        mav.addObject("proj_name", pConfig.PROJECT_NAME);
        mav.setViewName("lab/lab_room/labRoomDetail.jsp");
        return mav;
    }
    /****************************************************************************
     * 功能：保存实验室项目卡 作者：贺子龙 时间：2015-09-07
     ****************************************************************************/
    @RequestMapping("/saveLabRoomOperationItem")
    public ModelAndView saveLabRoomOperationItem(@RequestParam Integer roomId,@RequestParam int type,
                                                 HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验室
        LabRoom room = labRoomService.findLabRoomByPrimaryKey(roomId);

        String s = request.getParameter("operationItem");
        String str[] = s.split(",");
        labRoomService.saveLabRoomOperationItem(room, str);
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }

    /****************************************************************************
     * 功能：删除实验项目卡 作者：贺子龙 时间：2015-09-07
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomOperationItem")
    public ModelAndView deleteLabRoomOperationItem(
            @RequestParam Integer roomId,@RequestParam int type, Integer id) {
        System.out.println("delete coming in");
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验室
        LabRoom room = labRoomService.findLabRoomByPrimaryKey(roomId);
        // id对应的实验项目卡
        OperationItem m = operationItemDAO.findOperationItemByPrimaryKey(id);
        System.out.println("**********" + m.getLpName());
        labRoomService.deleteLabRoomOperationItem(room, m);
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }
    /****************************************************************************
     * 功能：保存实验室物联硬件 作者：贺子龙 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/saveLabRoomAgent")
    public ModelAndView saveLabRoomAgent(@ModelAttribute LabRoomAgent agent,
                                         @RequestParam Integer roomId,@RequestParam int type,HttpServletRequest request) {
        System.out.println("saveLabRoomAgent coming in");
        ModelAndView mav = new ModelAndView();
        // id对应的实验室
        LabRoom room = labRoomDAO.findLabRoomByPrimaryKey(roomId);
        // System.out.println("实验室号"+roomId);
        agent.setLabRoom(room);
        // 物联服务器
        System.out.println(agent.getCommonServer().getId());
        int serverId = agent.getCommonServer().getId();
        System.out.println("物联服务器号" + serverId);
        CommonServer server = commonServerDAO
                .findCommonServerByPrimaryKey(serverId);
        System.out.println(server.getServerName());
        if(request.getParameter("doorIndexInput")!=null&&!"".equals(request.getParameter("doorIndexInput"))){
            agent.setDoorindex(Integer.parseInt(request.getParameter("doorIndexInput")));
        }
        if(request.getParameter("harewareModuleInput")!=null&&!"".equals(request.getParameter("harewareModuleInput"))){
            agent.setHarewareModule(request.getParameter("harewareModuleInput"));
        }
        agent.setCommonServer(server);
        // agent.setId(50);
        labRoomAgentDAO.store(agent);
        // System.out.println(agent+"00000");
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }

    /****************************************************************************
     * 功能：删除实验室物联硬件 作者：贺子龙 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomAgent")
    public ModelAndView deleteLabRoomAgent(@RequestParam Integer id,@RequestParam int type) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联硬件
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
        labRoomAgentDAO.remove(agent);
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id="
                + agent.getLabRoom().getId()+"&type="+type);
        return mav;
    }

    /****************************************************************************
     * 功能：修改实验室物联硬件 作者：贺子龙 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/updateLabRoomAgent")
    public ModelAndView updateLabRoomAgent(@RequestParam Integer id,@RequestParam int type) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联硬件
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
        mav.addObject("agent", agent);
        // 物联硬件类型
        List<CDictionary> types = cDictionaryService.findallCType();
        mav.addObject("types", types);
        // 物联硬件服务器
        Set<CommonServer> serverList = commonServerDAO.findAllCommonServers();
        mav.addObject("serverList", serverList);
        mav.addObject("type",type);
        if(agent.getCDictionary().getCNumber().equals("4")&&agent.getCDictionary().getCCategory().equals("c_agent_type")) {// 电源控制器
            mav.setViewName("lab/lab_room/updateLabRoomAgent.jsp");
        }else if(!agent.getCDictionary().getCNumber().equals("4")&&agent.getCDictionary().getCCategory().equals("c_agent_type")){
            mav.setViewName("lab/lab_room/elseUpdateLabRoomAgent.jsp");
        }

        return mav;
    }

    /****************************************************************************
     * 功能：处理ajax中文乱码 作者：贺子龙 时间：2015-09-08
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
     * 功能：AJAX 根据姓名、工号查询当前登录人所在学院的用户 作者：贺子龙 时间：2015-09-08
     *
     * @throws UnsupportedEncodingException
     ****************************************************************************/
    @SuppressWarnings("deprecation")
    @RequestMapping("/findUserByCnameAndUsername")
    public @ResponseBody
    String findUserByCnameAndUsername(@RequestParam String cname,
                                      String username, Integer roomId, Integer page, Integer typeId)
            throws UnsupportedEncodingException {
        if (cname != null) {

            // cname = java.net.URLDecoder.decode(cname, "UTF-8");// 转成utf-8；

        }
        User u = shareService.getUser();
        String academyNumber = "";
        if (u.getSchoolAcademy() != null) {
            academyNumber = u.getSchoolAcademy().getAcademyNumber();
        }
        if("true".equals(pConfig.labAddAdim)){
            academyNumber = null;
        }
        User user = new User();
        user.setCname(java.net.URLDecoder.decode(cname, "UTF-8"));
        //借用一个字段临时存放类型
        user.setUserType(typeId);
        user.setUsername(username);

        int pageSize = 20;
        // 根据分页信息查询出来的记录
        List<User> userList = labRoomService.findUserByUserAndSchoolAcademy(
                user, roomId, academyNumber, page, pageSize);
        // 分页开始
        int totalRecords = labRoomService.findUserByUserAndSchoolAcademy(
                user, roomId, academyNumber);
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        String s = "";
        for (User d : userList) {
            String academy = "";
            if (d.getSchoolAcademy() != null) {
                academy = d.getSchoolAcademy().getAcademyName();
            }
            s += "<tr>" + "<td><input type='checkbox' name='CK_name' value='"
                    + d.getUsername() + "'/></td>" + "<td>" + d.getCname()
                    + "</td>" + "<td>" + d.getUsername() + "</td>" + "<td>"
                    + academy + "</td>" +

                    "</tr>";
        }
        s += "<tr><td colspan='6'>"
                + "<a href='javascript:void(0)' onclick='firstPage(1,"+typeId+");'>"
                + "首页" + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='previousPage("
                + page
                + ","+typeId+");'>"
                + "上一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='nextPage("
                + page
                + ","
                + pageModel.get("totalPage")
                +","+typeId+");'>"
                + "下一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='lastPage("
                + pageModel.get("totalPage")
                + ","+typeId+");'>"
                + "末页"
                + "</a>&nbsp;"
                + "当前第"
                + page
                + "页&nbsp; 共"
                + pageModel.get("totalPage")
                + "页  " + totalRecords + "条记录" + "</td></tr>";
        return htmlEncode(s);
    }

    /****************************************************************************
     * 功能：保存实验室管理员 作者：贺子龙 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/saveLabRoomAdmin")
    public ModelAndView saveLabRoomAdmin(@RequestParam Integer roomId,@RequestParam int type,
                                         String[] array, Integer typeId) {
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验分室
        LabRoom room = labRoomService.findLabRoomByPrimaryKey(roomId);
        for (String i : array) {
            // username对应的用户
            User u = userDAO.findUserByPrimaryKey(i);
            LabRoomAdmin admin = new LabRoomAdmin();
            admin.setLabRoom(room);
            admin.setUser(u);
            admin.setTypeId(typeId);
            labRoomAdminDAO.store(admin);
            if (typeId == 2) {
                // 给用户赋予权限
                Set<Authority> ahths = u.getAuthorities();
//                Authority a = authorityDAO.findAuthorityByPrimaryKey(19);// 药品柜管理员
                Authority a = authorityDAO.findAuthorityByAuthorityName("CABINETADMIN").iterator().next();
                if(a != null) {
                    ahths.add(a);
                    u.setAuthorities(ahths);
                    userDAO.store(u);
                }
            } else {
                // 给用户赋予权限
                Set<Authority> ahths = u.getAuthorities();
//                Authority a = authorityDAO.findAuthorityByPrimaryKey(5);// 实验室管理员
                Authority a = authorityDAO.findAuthorityByAuthorityName("LABMANAGER").iterator().next();
                if(a != null) {
                    ahths.add(a);
                    u.setAuthorities(ahths);
                    userDAO.store(u);
                }
            }
            RefuseItemBackup refuseItemBackup=new RefuseItemBackup();
            refuseItemBackup.setBusinessId(roomId.toString());
            if(typeId==2) {
                refuseItemBackup.setType("LabAgentAdmin");
                refuseItemBackup.setOperationItemName("添加物联管理员"+"("+u.getCname()+u.getUsername()+")");
            }else{
                refuseItemBackup.setType("LabRoomAdmin");
                refuseItemBackup.setOperationItemName("添加实验室管理员"+"("+u.getCname()+u.getUsername()+")");
            }
            refuseItemBackup.setTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
            refuseItemBackup.setLabRoomName(room.getLabRoomName());
            refuseItemBackup.setCreator(shareService.getUserDetail().getUsername());
            refuseItemBackupDAO.store(refuseItemBackup);
        }
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }

    /****************************************************************************
     * 功能：删除实验室管理员 作者：贺子龙 时间：2015-09-15
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomAdmin")
    public ModelAndView deleteLabRoomAdmin(@RequestParam Integer id,@RequestParam int type) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联管理员
        LabRoomAdmin admin = labRoomAdminDAO.findLabRoomAdminByPrimaryKey(id);
        labRoomAdminDAO.remove(admin);
        // 判断该实验室管理员是否还有其他实验室管理
        List labRooms = labRoomAdminService.findLabRoomAdminForByUsernameAndType(admin.getUser().getUsername(), admin.getTypeId());
        if(labRooms == null || labRooms.size() == 0) {
            Authority a;
            User u = admin.getUser();
            if (admin.getTypeId() == 2) {
//            a = authorityDAO.findAuthorityById(19);
                a = authorityDAO.findAuthorityByAuthorityName("CABINETADMIN").iterator().next();
            } else {
//            a = authorityDAO.findAuthorityById(5);
                a = authorityDAO.findAuthorityByAuthorityName("LABMANAGER").iterator().next();
            }
            Set<Authority> ahths = u.getAuthorities();
            ahths.remove(a);
            u.setAuthorities(ahths);
            userDAO.store(u);
            RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
            refuseItemBackup.setBusinessId(admin.getLabRoom().getId().toString());
            if (admin.getTypeId() == 2) {
                refuseItemBackup.setType("LabAgentAdmin");
                refuseItemBackup.setOperationItemName("删除物联管理员"+"("+u.getCname()+u.getUsername()+")");
            } else {
                refuseItemBackup.setType("LabRoomAdmin");
                refuseItemBackup.setOperationItemName("添加实验室管理员"+"("+u.getCname()+u.getUsername()+")");
            }
            refuseItemBackup.setTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
            refuseItemBackup.setLabRoomName(admin.getLabRoom().getLabRoomName());
            refuseItemBackup.setCreator(shareService.getUserDetail().getUsername());
            refuseItemBackupDAO.store(refuseItemBackup);
        }
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id="
                + admin.getLabRoom().getId()+"&type="+type);
        return mav;
    }
    /**
     * 批量删除实验项目
     * @author 廖文辉
     * 2018-12-24
     */
    @RequestMapping("/batchDeleteLabRoomOperationItem")
    public ModelAndView batchDeleteLabRoomOperationItem(@RequestParam int[] array,@RequestParam Integer roomId,@RequestParam int type){
        ModelAndView mav = new ModelAndView();
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(roomId);
        for (int operationItemId : array) {
            OperationItem m = operationItemDAO.findOperationItemByPrimaryKey(operationItemId);
            Set<OperationItem> currents=labRoom.getOperationItems();
            currents.remove(m);
            labRoom.setOperationItems(currents);
            labRoom = labRoomDAO.store(labRoom);
            labRoomDAO.flush();
        }
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }
    /**
     * 批量删除物联管理员
     * @author 廖文辉
     * 2018-12-24
     */
    @RequestMapping("/batchDeleteLabRoomAdmin")
    public ModelAndView batchDeleteLabRoomAdmin(@RequestParam int[] array,@RequestParam Integer roomId,@RequestParam int type){
        ModelAndView mav = new ModelAndView();
        for (int id : array) {
            // id对应的实验室物联管理员
            LabRoomAdmin admin = labRoomAdminDAO.findLabRoomAdminByPrimaryKey(id);
            labRoomAdminDAO.remove(admin);
            // 判断该实验室管理员是否还有其他实验室管理
            List labRooms = labRoomAdminService.findLabRoomAdminForByUsernameAndType(admin.getUser().getUsername(), admin.getTypeId());
            if (labRooms == null || labRooms.size() == 0) {
                Authority a;
                User u = admin.getUser();
                if (admin.getTypeId() == 2) {
//            a = authorityDAO.findAuthorityById(19);
                    a = authorityDAO.findAuthorityByAuthorityName("CABINETADMIN").iterator().next();
                } else {
//            a = authorityDAO.findAuthorityById(5);
                    a = authorityDAO.findAuthorityByAuthorityName("LABMANAGER").iterator().next();
                }
                Set<Authority> ahths = u.getAuthorities();
                ahths.remove(a);
                u.setAuthorities(ahths);
                userDAO.store(u);
                LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
                RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
                refuseItemBackup.setBusinessId(roomId.toString());
                if (admin.getTypeId() == 2) {
                    refuseItemBackup.setType("LabAgentAdmin");
                    refuseItemBackup.setOperationItemName("删除物联管理员"+"("+u.getCname()+u.getUsername()+")");
                } else {
                    refuseItemBackup.setType("LabRoomAdmin");
                    refuseItemBackup.setOperationItemName("删除实验室管理员"+"("+u.getCname()+u.getUsername()+")");
                }
                refuseItemBackup.setTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
                refuseItemBackup.setLabRoomName(labRoom.getLabRoomName());
                refuseItemBackup.setCreator(shareService.getUserDetail().getUsername());
                refuseItemBackupDAO.store(refuseItemBackup);
            }
        }
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }
    /****************************************************************************
     * 功能：AJAX 根据软件名称、软件版本查询当前登录人所在学院的软件 作者：周志辉 时间：2017-08-01
     ****************************************************************************/
    @RequestMapping("/findSoftwareByNameAndEdition")
    public @ResponseBody
    String findSoftwareByNameAndEdition(@RequestParam String name,
                                        String edition, Integer roomId, Integer page,
                                        @ModelAttribute("selected_academy") String acno) {
        String academyNumber = shareService.getUser().getSchoolAcademy()
                .getAcademyNumber();
        Software software = new Software();
        software.setName(name);
        software.setEdition(edition);
        // 分页开始
        int totalRecords = softwareService
                .findSoftwareByAcademyNumberAndSoftware(academyNumber,
                        software, roomId);
        int pageSize = 100;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<Software> softwareList = softwareService
                .findSoftwareByAcademyNumberAndSoftware(academyNumber,
                        software, roomId, page, pageSize);

        String s = "";
        for (Software d : softwareList) {
            String dongle = "";
            if (d.getDongle() == null) {
                dongle = "无";
            } else if (d.getDongle() != null) {
                if (d.getDongle() == 1) {
                    dongle = "有";
                } else {
                    dongle = "无";
                }
            }

            s += "<tr>" + "<td>" + d.getId() + "</td>" + "<td>" + d.getName()
                    + "</td>" + "<td>" + d.getEdition() + "</td>" + "<td>"
                    + d.getPrice() + "</td>" + "<td>" + dongle + "</td>"
                    + "<td><input type='checkbox' name='CK' value='"
                    + d.getId() + "'/></td>" + "</tr>";
        }
        s += "<tr><td colspan='7'>"
                + "<a href='javascript:void(0)' onclick='firstSoft(1);'>" + "首页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='previousSoft("
                + page
                + ");'>"
                + "上一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='nextSoft("
                + page
                + ","
                + pageModel.get("totalPage")
                + ");'>"
                + "下一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='lastSoft("
                + pageModel.get("totalPage")
                + ");'>"
                + "末页"
                + "</a>&nbsp;"
                + "当前第"
                + page
                + "页&nbsp; 共"
                + pageModel.get("totalPage")
                + "页  " + totalRecords + "条记录" + "</td></tr>";
        return htmlEncode(s);
    }

    /****************************************************************************
     * 功能：AJAX 根据设备名称、设备编号查询当前登录人所在学院的设备 作者：贺子龙 时间：2015-09-08
     *
     * @throws UnsupportedEncodingException
     ****************************************************************************/
    @RequestMapping("/findSchoolDeviceByNameAndNumber")
    public @ResponseBody
    String findSchoolDeviceByNameAndNumber(@RequestParam String name,
                                           String number, String deviceAddress, Integer page,
                                           @ModelAttribute("selected_academy") String acno)
            throws UnsupportedEncodingException {
        String academyNumber = shareService.getUser().getSchoolAcademy()
                .getAcademyNumber();
        SchoolDevice schoolDevice = new SchoolDevice();
        schoolDevice.setDeviceName(name);
        schoolDevice.setDeviceNumber(number);
        schoolDevice.setDeviceAddress(deviceAddress);
        /*
         * //设备保管员 if(keepUser!=null&&!keepUser.equals("")){ User u=new User();
		 * u.setCname(keepUser); schoolDevice.setUserByKeepUser(u); }
		 */
        // 分页开始
        int totalRecords = schoolDeviceService
                .findSchoolDeviceByAcademyNumberAndSchoolDeviceInLabRoomDevice(academyNumber,
                        schoolDevice);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<SchoolDevice> deviceList = schoolDeviceService
                .findSchoolDeviceByAcademyNumberAndSchoolDeviceInLabRoomDevice(academyNumber,
                        schoolDevice, page, pageSize);

        String s = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (SchoolDevice d : deviceList) {
            String time = "";// 购买日期
            if (d.getDeviceBuyDate() != null) {
                time = sdf.format(d.getDeviceBuyDate().getTime());
            }
            String academy = "";// 部门
            if (d.getSchoolAcademy() != null) {
                academy = d.getSchoolAcademy().getAcademyName();
            }
            String cname = "";// 保管员
            if (d.getUserByKeepUser() != null) {
                cname = d.getUserByKeepUser().getCname();
            }
            s += "<tr>" + "<td>" + d.getDeviceNumber() + "</td>" + "<td>"
                    + d.getDeviceName() + "</td>" + "<td>"
                    + d.getDevicePattern() + "</td>";
            if (d.getUserByKeepUser() != null
                    && d.getUserByKeepUser().getCname() != null) {
                s += "<td>" + d.getUserByKeepUser().getCname() + "</td>";
            } else {
                s += "<td></td>";
            }
            s += "<td>" + d.getDeviceFormat() + "</td>" + "<td>"
                    + d.getDevicePrice() + "</td>";
            if (d.getDeviceAddress() != null) {
                s += "<td>" + d.getDeviceAddress() + "</td>";
            } else {
                s += "<td></td>";
            }
            s += "<td><input type='checkbox' name='CK' value='"
                    + d.getDeviceNumber() + "'/></td>" + "</tr>";
        }
        s += "<tr><td colspan='7'>"
                + "<a href='javascript:void(0)' onclick='first(1);'>" + "首页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='previous("
                + page
                + ");'>"
                + "上一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='next("
                + page
                + ","
                + pageModel.get("totalPage")
                + ");'>"
                + "下一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='last("
                + pageModel.get("totalPage")
                + ");'>"
                + "末页"
                + "</a>&nbsp;"
                + "当前第"
                + page
                + "页&nbsp; 共"
                + pageModel.get("totalPage")
                + "页  " + totalRecords + "条记录" + "</td></tr>";
        return htmlEncode(s);
    }

    /****************************************************************************
     * 功能：保存实验室设备 作者：贺子龙 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/saveLabRoomDevice")
    public ModelAndView saveLabRoomDevice(@RequestParam Integer roomId,@RequestParam int type,
                                          String[] array) throws Exception {
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验分室
        LabRoom room = labRoomService.findLabRoomByPrimaryKey(roomId);
        for (String i : array) {
            // 设备编号对应的设备
            SchoolDevice d = schoolDeviceService
                    .findSchoolDeviceByPrimaryKey(i);
            LabRoomDevice device = new LabRoomDevice();
            device.setLabRoom(room);
            device.setSchoolDevice(d);
            /*
             * //默认为现场培训 CLabAccessType
			 * accessType=cLabAccessTypeDAO.findCLabAccessTypeById(1);
			 * device.setCLabAccessType(accessType); //默认为日历形式 CAppointmentType
			 * type=cAppointmentTypeDAO.findCAppointmentTypeById(2);
			 * device.setCAppointmentType(type);
			 */
            device = labRoomDeviceService.save(device);
            // 设备二维码
            String url = shareService.getDimensionalCode(device);
            device.setDimensionalCode(url);

            // 设备置为正常使用
            CDictionary cd = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
            device.setCDictionaryByDeviceStatus(cd);

            labRoomDeviceService.save(device);
        }
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }

    /****************************************************************************
     * 功能：保存实验室软件 作者：周志辉 时间：2017-08-02
     ****************************************************************************/
    @RequestMapping("/saveLabRoomSoftware")
    public ModelAndView saveLabRoomSoftware(@RequestParam Integer roomId,@RequestParam int type,
                                            int[] array) throws Exception {
        ModelAndView mav = new ModelAndView();
        // roomId对应的实验分室
        LabRoom room = labRoomService.findLabRoomByPrimaryKey(roomId);
        for (int i : array) {
            // 设备编号对应的设备
            Software d = softwareService.findSoftwareByPrimaryKey1(i);
            SoftwareRoomRelated softwareRoomRelated = new SoftwareRoomRelated();
            softwareRoomRelated.setLabRoom(room);
            softwareRoomRelated.setSoftware(d);
            softwareRoomRelated = softwareRoomRelatedService
                    .save(softwareRoomRelated);
            softwareRoomRelatedService.save(softwareRoomRelated);
        }
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }

    /****************************************************************************
     * 功能：删除实验室设备 作者：贺子龙 时间：2015-09-15
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomDeviceNew")
    public ModelAndView deleteLabRoomDeviceNew(@RequestParam Integer labDeviceId,@RequestParam int type) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联管理员
        LabRoomDevice device = labRoomDeviceDAO
                .findLabRoomDeviceByPrimaryKey(labDeviceId);
        labRoomDeviceDAO.remove(device);
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id="
                + device.getLabRoom().getId()+"&type="+type);
        return mav;
    }
    /**
     * 批量删除仪器设备
     * @author 廖文辉
     * 2018-12-24
     */
    @RequestMapping("/batchDeleteLabDevice")
    public ModelAndView batchDeleteLabDevice(@RequestParam int[] array,@RequestParam Integer roomId,@RequestParam int type){
        ModelAndView mav = new ModelAndView();
        for (int labDeviceId : array) {
            // id对应的实验室物联管理员
            LabRoomDevice device = labRoomDeviceDAO
                    .findLabRoomDeviceByPrimaryKey(labDeviceId);
            labRoomDeviceDAO.remove(device);
        }
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }
    /****************************************************************************
     * 功能：删除实验室软件 作者：周志辉 时间：2017-08-01
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomSoftware")
    public ModelAndView deleteLabRoomSoftware(@RequestParam Integer softwareId,@RequestParam int type,
                                              @RequestParam Integer labRoomId) {
        ModelAndView mav = new ModelAndView();
        Software software = softwareDAO.findSoftwareByPrimaryKey(softwareId);
        List<SoftwareRoomRelated> softwareRoomRelateds = new ArrayList<SoftwareRoomRelated>(
                software.getSoftwareRoomRelateds());
        for (SoftwareRoomRelated softwareRoomRelated : softwareRoomRelateds) {
            int i = softwareRoomRelated.getLabRoom().getId();// 判断两个integer是否相等不能用==
            if (labRoomId == i) {
                softwareRoomRelatedDAO.remove(softwareRoomRelated);
            }
        }
        mav.addObject("type",type);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id="
                + labRoomId+"&type="+type);
        return mav;
    }
    /**
     * 批量删除实验室软件
     * @author 廖文辉
     * 2018-12-24
     */
    @RequestMapping("/batchDeleteLabRoomSoftWare")
    public ModelAndView batchDeleteLabRoomSoftWare(@RequestParam int[] array,@RequestParam Integer roomId,@RequestParam int type) {
        ModelAndView mav = new ModelAndView();
        for (int softwareId : array) {
            Software software = softwareDAO.findSoftwareByPrimaryKey(softwareId);
            List<SoftwareRoomRelated> softwareRoomRelateds = new ArrayList<SoftwareRoomRelated>(
                    software.getSoftwareRoomRelateds());
            for (SoftwareRoomRelated softwareRoomRelated : softwareRoomRelateds) {
                if(softwareRoomRelated.getLabRoom()!=null&&!"".equals(softwareRoomRelated)) {
                    int i = softwareRoomRelated.getLabRoom().getId();// 判断两个integer是否相等不能用==
                    if (roomId == i) {
                        softwareRoomRelatedDAO.remove(softwareRoomRelated);
                    }
                }
            }
        }
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId + "&type=" + type);
        return  mav;
    }
    /****************************************************************************
     * 功能：删除家具 作者：方正
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomFurniture")
    public String deleteLabRoomFurniture(@RequestParam Integer i) {
        LabRoomFurniture labRoomFurniture = labRoomFurnitureDAO
                .findLabRoomFurnitureByPrimaryKey(i);
        labRoomFurnitureService.deleteLabRoomFurniture(labRoomFurniture);
        int t = labRoomFurniture.getLabRoom().getId();
        return "redirect:/labRoom/getLabRoom?currpage=1&id=" + t;

    }

    /****************************************************************************
     * 功能：添加家具家具 作者：贺子龙 时间：2015-09-21 15:05:43
     ****************************************************************************/
    @RequestMapping("/saveLabRoomFurniture")
    public String saveLabRoomFurniture(
            @ModelAttribute LabRoomFurniture labRoomFurniture) {
        int i = labRoomFurniture.getLabRoom().getId();
        labRoomFurnitureService.saveLabRoomFurniture(labRoomFurniture);
        return "redirect:/labRoom/getLabRoom?currpage=1&id=" + i;

    }

    /****************************************************************************
     * 功能：AJAX 根据设备名称、设备编号查询当前登录人所在学院的设备
     * 作者：李小龙
     ****************************************************************************/
    @RequestMapping("/appointment/openVideo")
    public ModelAndView openVideo(@RequestParam Integer agentId) {
        ModelAndView mav = new ModelAndView();
        //物联设备
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        // 流媒体服务器地址
        String serverIp = agent.getCommonServer().getServerIp();
        // 端口
        //String hardwarePort = agent.getHardwarePort();
        String hardwarePort = "1935";

        // 摄像头本身ip的 xxx.xxx.xxx.123   最后那个123
        String lastFour = "";
        // 192.168.0.sz
        String hardWareIp = agent.getHardwareIp();
        // split .有问题，所以替换成了 , 逗号
        hardWareIp = hardWareIp.replace(".", ",");
        if (!EmptyUtil.isStringEmpty(hardWareIp)
                && !EmptyUtil.isObjectEmpty(hardWareIp.split(","))
                && hardWareIp.split(",").length > 3
                ) {
            lastFour = hardWareIp.split(",")[2]+hardWareIp.split(",")[3];
        }
        mav.addObject("serverIp", serverIp);
        mav.addObject("hardwarePort", hardwarePort);
        mav.addObject("lastFour", lastFour);

        // 测试输出
        System.out.println("rtmp://" + serverIp + ":" + hardwarePort + "/live/" + lastFour);
        mav.setViewName("lab/labVideo.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：AJAX 根据设备名称、设备编号查询当前登录人所在学院的设备
     * 作者：李小龙
     ****************************************************************************/
    @RequestMapping("/appointment/openVideoSet")
    public ModelAndView openVideoSet(@RequestParam Integer agentId) {
        ModelAndView mav = new ModelAndView();

        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        // 流媒体服务器地址
        String serverIp = agent.getSnNo();
        // 端口
        String hardwarePort = agent.getManufactor();
        // 摄像头本身ip的 xxx.xxx.xxx.123   最后那个123
        String lastFour = "";
        // 192.168.0.sz
        String hardWareIp = agent.getHardwareIp();
        // split .有问题，所以替换成了 , 逗号
        hardWareIp = hardWareIp.replace(".", ",");
        if (!EmptyUtil.isStringEmpty(hardWareIp)
                && !EmptyUtil.isObjectEmpty(hardWareIp.split(","))
                && hardWareIp.split(",").length > 3
                ) {
            lastFour = hardWareIp.split(",")[3];
        }
        mav.addObject("serverIp", serverIp);
        mav.addObject("hardwarePort", hardwarePort);
        mav.addObject("lastFour", lastFour);
        // 测试输出
        //System.out.println("rtmp://"+serverIp+":"+hardwarePort+"/live/"+lastFour);
        mav.setViewName("lab/videoHk.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：socket方法远程开门
     * 作者：叶晨凯
     * 时间：2018-08-13
     ****************************************************************************/
    @RequestMapping("/openDoorPython")
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

            return "success";
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
                String sysName = pConfig.PROJECT_NAME;
                String doorIndex = "01";
                if (a.getDoorindex() != null) {
                    if(sysName.contains("fdulims")) {
                        doorIndex = a.getDoorindex().toString();
                    }else {
                        doorIndex = "0" + a.getDoorindex().toString();
                    }
                }
                // 主机和端口
                ServIP = a.getCommonServer().getServerIp();
                if (a.getCommonServer().getServerSn() != null && !a.getCommonServer().getServerSn().equals("")) {
                    port = a.getCommonServer().getServerSn();
                } else {//端口为空
                    port = "80";
                }

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
            System.out.println("开门URL："+headers);
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
                return "success";
            } else {
                return result[length - 1];
            }
        }
    }

    /**
     * Description 班牌开门{升达项目}
     * @param agentId
     * @return
     * @throws IOException
     */
    @RequestMapping("/appointment/openScrPython")
    public @ResponseBody String openScrPython(@RequestParam Integer agentId) throws IOException {
        //根据roomId查询该实验室的门禁
        LabRoomAgent a = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(agentId);
        String ip=a.getHardwareIp();
        // http://10.101.251.252:8082/services/ofthings/acldoor.asp?cmd=open_scr_door%ip=10.101.251.20
        String cmd = "open_scr_door";
        //1、创建一个服务器端Socket，指定主机和端口，并连接
        String Host=a.getCommonServer().getServerIp();
        int Port=Integer.parseInt("8082");
        // 测试信息（便于后台查看）
        System.out.println("Host: "+Host+":"+Port+"\n");
        System.out.println("GET /services/ofthings/acldoor.asp?cmd="+cmd+"&ip="+ip+" HTTP/1.1\n");
        Socket socket =new Socket(Host,Port);
        //2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw =new PrintWriter(os);//将输出流包装成打印流
        //3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        pw.write("GET "+a.getCommonServer().getServerIp()+"/services/ofthings/acldoor.asp?cmd="+cmd+"&ip="+ip+" HTTP/1.1\n");
        pw.write("Content-type: application/x-java-serialized-object\n");
        pw.write("Cache-control: no-cache\n");
        pw.write("Pragma: no-cache\n");
        pw.write("User-Agent: Sakai Java/1.7.0_111\n");
        pw.write("Host: "+Host+":"+Port+"\n");
        pw.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\n");
        pw.write("Connection: keep-alive\n");
        pw.write("Content-Length:0\n");
        pw.write("\n");
        pw.flush();
        String inputline ="";
        String info =""; // 返回值
        int count = 0;
        while((inputline=br.readLine())!=null){
            count++;
            System.out.println("return:"+inputline);
            if (count==5) {
                info += inputline;
            }
        }
        socket.shutdownInput();//关闭输入流
        pw.close();
        os.close();
        br.close();
        is.close();
        socket.close();
        System.out.println("to js:"+info);
        return info;
    }

    /****************************************************************************
     * 功能：AJAX返回刷新权限的结果 作者：李小龙
     *
     * @throws IOException
     ****************************************************************************/
    @RequestMapping("/refreshPermissions")
    public @ResponseBody
    String refreshPermissions(@RequestParam Integer roomId,
                              HttpServletResponse response) throws IOException,InterruptedException {

        // 根据roomId查询该实验室的门禁
        List<LabRoomAgent> agentList = labRoomService
                .findLabRoomAgentAccessByRoomId(roomId);
        LabRoomAgent a = new LabRoomAgent();
        if (agentList.size() > 0) {
            a = agentList.get(0);
        }

        if(pConfig.PROJECT_NAME.equals("zjcclims")) {
            // 调用Python脚本
            Process proc = Runtime.getRuntime().exec("python  /opt/python/lims2opendoor.py");
            proc.waitFor();

            return "success";
        }else {
            String agentPort = labRoomService.findAgentPortByRoomId(roomId);
            //1、创建一个服务器端Socket，指定主机和端口，并连接
            String Host = a.getCommonServer().getServerIp();
            int Port = Integer.parseInt(a.getCommonServer().getServerSn());
            // 测试信息（便于后台查看）
            System.out.println("Host: " + Host + ":" + Port + "\n");
            Socket socket = new Socket(Host, Port);
            //2、获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();//字节输出流
            PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
            //3、获取输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if (pConfig.PROJECT_NAME.equals("jitsoft")) {
                System.out.println("GET /services/ofthings/acldoor.asp?cmd=regcard&roomnumber=" + agentPort + " HTTP/1.1\n");
                pw.write("GET /services/ofthings/acldoor.asp?cmd=regcard&roomnumber=" + agentPort + " HTTP/1.1\n");
            }else if(pConfig.PROJECT_NAME.equals("fdulims")) {
                pw.write("GET /services/ofthings/room.asp?cmd=regcard&roomnumber=" + roomId + " HTTP/1.1\n");
                System.out.println("GET /services/ofthings/room.asp?cmd=regcard&roomnumber=" + roomId + " HTTP/1.1\n");
            } else {
                System.out.println("GET /services/ofthings/acldoor.asp?cmd=regcard&roomnumber=" + roomId + " HTTP/1.1\n");
                pw.write("GET /services/ofthings/acldoor.asp?cmd=regcard&roomnumber=" + roomId + " HTTP/1.1\n");
            }
            pw.write("Content-type: application/x-java-serialized-object\n");
            pw.write("Cache-control: no-cache\n");
            pw.write("Pragma: no-cache\n");
            pw.write("User-Agent: Sakai Java/1.7.0_111\n");
            pw.write("Host: " + Host + ":" + Port + "\n");
            pw.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\n");
            pw.write("Connection: keep-alive\n");
            pw.write("Content-Length:0\n");
            pw.write("\n");
            pw.flush();
            String inputline = "";
            String info = ""; // 返回值
            int count = 0;
            while ((inputline = br.readLine()) != null) {
                count++;
                System.out.println("return:" + inputline);
                if (count == 5) {
                    info += inputline;
                }
            }
            socket.shutdownInput();//关闭输入流

            pw.close();
            os.close();
            br.close();
            is.close();
            socket.close();
            System.out.println("to js:" + info);
            return info;
        }
    }

    /**
     * Description 刷新权限-新版物联
     * @param roomId 实验室id
     * @return
     * @author 陈乐为 2019-2-23
     */
    @RequestMapping("/refreshPerm")
    public @ResponseBody
    String refreshPerm(@RequestParam Integer roomId) {
        return labRoomService.sendRefreshInterfaceByJWT(roomId);
    }

    /**
     * Description 远程开门-新版物联
     * @param agentId
     * @param doorIndex
     * @return
     * @author 陈乐为 2019-2-23
     */
    @RequestMapping("/openDoorNew")
    public @ResponseBody
    String openDoorNew(@RequestParam Integer agentId, Integer doorIndex) {
        return labRoomService.sendOpenDoorInterfaceByJWT(agentId, doorIndex);
    }

    /****************************************************************************
     * 功能：判断所填写的编号是否与数据库中已有的可用状态实验室编号重复 作者：贺子龙 日期：2015-12-23
     ****************************************************************************/
    @RequestMapping("/testDuplicated")
    public @ResponseBody
    String testDuplicated(@RequestParam String labRoomNumber,
                          HttpServletResponse response) {
        Set<LabRoom> labRoomNumberDup = labRoomDAO
                .findLabRoomByLabRoomNumber(labRoomNumber);// 根据所填写的labRoomNumber查找数据库中是否有重名
        boolean isDuplicated = false;
        if (labRoomNumberDup.size() == 0) {
            // do nothing
        } else {
            for (LabRoom labRoom : labRoomNumberDup) {
                if (labRoom.getIsUsed() == null
                        || (labRoom.getIsUsed() != null && labRoom.getIsUsed()
                        .equals(1))) {// 正常使用状态
                    isDuplicated = true;
                    break;
                }
            }
        }

        if (isDuplicated) {
            return "isDuplicated";
        } else {
            return "isNotDuplicated";
        }

    }

    /****************************************************************************
     * 功能：门禁进出记录 作者：贺子龙 时间：2015-11-30
     ****************************************************************************/
    @RequestMapping("/entranceManageForLab")
    public ModelAndView entranceManageForLab(@ModelAttribute LabRoom labRoom,
                                             @RequestParam Integer page,
                                             @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取当前用户
        User user = shareService.getUser();
        mav.addObject("user", user);
        // 查询表单的对象
        mav.addObject("labRoom", labRoom);
        // 设置分页变量并赋值为20
        int pageSize = 15;
        // 查询出来的总记录条数
        int totalRecords = labRoomService.findLabRoomBySchoolAcademyDefault(
                labRoom, 1, -1, 548, acno).size();
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 查询框中的实验室
        List<LabRoom> labRoomListAll = labRoomService
                .findLabRoomBySchoolAcademyDefault(labRoom, 1, -1, 548, acno);// 门禁--548
        mav.addObject("labRoomListAll", labRoomListAll);
        // 页面显示的实验室
        List<LabRoom> labRoomList = labRoomService
                .findLabRoomBySchoolAcademyDefault(labRoom, page, pageSize,
                        548, acno);// 门禁--548
        mav.addObject("labRoomList", labRoomList);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("lab/lab_record/listEntranceManageForLab.jsp");
        return mav;
    }

    /*************************************************************************************
     * @內容：开放实验室资源--门禁
     * @作者：贺子龙
     * @日期：2015-12-01
     *************************************************************************************/
    @RequestMapping("/entranceList")
    public ModelAndView entranceList(@RequestParam Integer id, Integer page,
                                     @ModelAttribute CommonHdwlog commonHdwlog,
                                     HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        mav.addObject("commonHdwlog", commonHdwlog);

        mav.addObject("id", id);
        // id对应的物联设备
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
        LabRoomAgent videoAgent = new LabRoomAgent();
        Set<LabEntranceVideo> list = agent.getLabEntranceVideosForEntranceId();
        for (LabEntranceVideo lev : list) {
            videoAgent = lev.getLabRoomAgentByVideoId();
        }
        mav.addObject("videoAgentId", videoAgent.getId());
        System.out.println(videoAgent.getId());
        mav.addObject("labRoomId", agent.getLabRoom().getId());
        String ip = agent.getHardwareIp();
        String port = agent.getManufactor();
        // 设置分页变量并赋值为20
        // int pageSize = CommonConstantInterface.INT_PAGESIZE;
        int pageSize = 30;
        // 查询出来的总记录条数
        int totalRecords = labRoomService.findLabRoomAccessByIpCount(
                commonHdwlog, ip, port, request);
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 页面显示的实验室
        List<LabAttendance> accessList = labRoomService.findLabRoomAccessByIp(
                commonHdwlog, ip, port, page, pageSize, request);

        mav.addObject("accessList", accessList);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("lab/lab_record/listLabRoomEntrance.jsp");
        return mav;
    }

    /*************************************************************************************
     * @throws ParseException
     * @內容：打开门禁视频监控
     * @作者：周志辉
     * @日期：2017-11-22
     *************************************************************************************/
    @RequestMapping("/openVideoBack")
    public ModelAndView openVideoBack(@RequestParam Integer id, String time) throws ParseException {
        ModelAndView mav = new ModelAndView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        DateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date opentime = sdf.parse(time);
        String str = format.format(opentime);
        System.out.println(str);
        mav.addObject("两天前的日期：" + sdf.format(new Date(opentime.getTime() - 60 * 1000)));
        // id对应的物联设备
        String starTime = sdf.format(new Date(opentime.getTime() - 60 * 1000));
        String endTime = sdf.format(new Date(opentime.getTime() + 60 * 1000));
        StringBuffer stime = new StringBuffer(starTime);
        StringBuffer etime = new StringBuffer(endTime);
        stime.insert(10, "T");
        etime.insert(10, "T");
        mav.addObject("time", time);
        mav.addObject("startime", stime);
        mav.addObject("endtime", etime);
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
        String ip = "";
        String port = "";
        if (agent != null) {
            ip = agent.getSnNo();
            port = agent.getManufactor();
        }
        mav.addObject("id", id);
        mav.addObject("agent", agent);
        mav.addObject("ip", ip);
        mav.addObject("port", port);
        mav.setViewName("lab/videoback.jsp");
        return mav;
    }

    /*************************************************************************************
     * @throws ParseException
     * @內容：打开门禁视频监控
     * @作者：周志辉
     * @日期：2017-11-22
     *************************************************************************************/
    @RequestMapping("/openVideoBackByDurtime")
    public ModelAndView openVideoBackByDurtime(@RequestParam Integer id, String time, String durtime) throws ParseException {
        ModelAndView mav = new ModelAndView();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        DateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date opentime = sdf.parse(time);
        String str = format.format(opentime);
        System.out.println(str);
        Integer duringTime = Integer.parseInt(durtime);

        mav.addObject("两天前的日期：" + sdf.format(new Date(opentime.getTime() - 60 * 1000)));
        // id对应的物联设备
        String starTime = "";
        String endTime = "";
        if (duringTime == 0) {
            mav.addObject("durtime", "30秒");
            starTime = sdf.format(new Date(opentime.getTime() - 30 * 1000));
            endTime = sdf.format(new Date(opentime.getTime() + 30 * 1000));
        } else {
            starTime = sdf.format(new Date(opentime.getTime() - duringTime * 60 * 1000));
            endTime = sdf.format(new Date(opentime.getTime() + duringTime * 60 * 1000));
            mav.addObject("durtime", durtime + "分钟");
        }
        StringBuffer stime = new StringBuffer(starTime);
        StringBuffer etime = new StringBuffer(endTime);
        stime.insert(10, "T");
        etime.insert(10, "T");
        mav.addObject("time", time);
        mav.addObject("startime", stime);
        mav.addObject("endtime", etime);
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
        String ip = "";
        String port = "";
        if (agent != null) {
            ip = agent.getSnNo();
            port = agent.getManufactor();
        }
        mav.addObject("id", id);
        mav.addObject("agent", agent);
        mav.addObject("ip", ip);
        mav.addObject("port", port);
        mav.setViewName("lab/videoback.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：AJAX 根据姓名、工号查询当前登录人所在学院的用户 作者：贺子龙 时间：2015-09-08
     ****************************************************************************/
    @RequestMapping("/getLabRoomDetail")
    public @ResponseBody
    String getLabRoomDetail(@RequestParam Integer roomId) {
        // id对应的实验分室信息
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
        // 根据分页信息查询出来的记录
        String s = "";
        s += "<tr>" + "<td>实验室名称：" + labRoom.getLabRoomName() + "</td>"
                + "<tr><td>实验室描述：" + labRoom.getLabRoomIntroduction()
                + "</td><tr>" + "<tr><td>实验室注意事项："
                + labRoom.getLabRoomAttentions() + "</td><tr>"
                + "<tr><td>实验室规章制度：" + labRoom.getLabRoomRegulations()
                + "</td><tr>" + "<tr><td>实验室获奖信息："
                + labRoom.getLabRoomPrizeInformation() + "</td><tr>" + "</tr>";
        return htmlEncode(s);
    }

    /****************************************************************************
     * 功能：修改实验分室 作者：李小龙
     ****************************************************************************/
    @RequestMapping("/appointment/showLabRoom")
    public ModelAndView showLabRoom(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验分室
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
        mav.addObject("labRoom", labRoom);
        mav.setViewName("lab/lab_room/showLabRoom.jsp");
        return mav;
    }

    /***********************************************************************************
     * @description 药品溶液管理-药品申购｛设置物品仓库信息｝
     * @author 郑昕茹
     * @date 2016-08-16
     * **********************************************************************************/
    @RequestMapping("/addAgent")
    public ModelAndView addAgent(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = labRoomDeviceService
                .findLabRoomDeviceByPrimaryKey(id);
        mav.addObject("labRoomDevice", labRoomDevice);
        mav.addObject("listLabRoomAgents", labRoomDevice.getLabRoomAgent());
        if (labRoomDevice.getLabRoomAgent() == null) {
            mav.addObject("flag", 1);
        } else {
            mav.addObject("flag", 0);
        }
        mav.addObject("listAgents", labRoomAgentDAO.executeQuery(
                "select l from LabRoomAgent l where" + " 1=1 and l.labRoom.id="
                        + labRoomDevice.getLabRoom().getId(), 0, -1));
        // mav.addObject("listAgents",labRoomAgentDAO.executeQuery("select l from LabRoomAgent l where"
        // +
        // " 1=1 and l.labRoom.id="+labRoomDevice.getLabRoom().getId()+" and CDictionary.CNumber = 4",
        // 0, -1));
        mav.addObject("id", id);
        mav.setViewName("lab/lab_room/editLabRoomDeviceAgent.jsp");
        return mav;

    }

    /***********************************************************************************
     * @description 药品溶液管理-药品申购｛设置物品仓库信息｝
     * @author 郑昕茹
     * @date 2016-08-16
     * **********************************************************************************/
    @RequestMapping("/saveLabRoomDeviceAgent")
    public ModelAndView saveLabRoomDeviceAgent(@RequestParam int deviceId,
                                               HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = labRoomDeviceService
                .findLabRoomDeviceByPrimaryKey(deviceId);
        String agentId = request.getParameter("selectAgent");
        LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentById(Integer
                .parseInt(agentId));
        labRoomDevice.setLabRoomAgent(agent);
        labRoomDeviceDAO.store(labRoomDevice);
        mav.setViewName("redirect:/labRoom/addAgent?id=" + deviceId);
        return mav;

    }

    /***********************************************************************************
     * @description 药品溶液管理-药品申购｛设置物品仓库信息｝
     * @author 郑昕茹
     * @date 2016-08-16
     * **********************************************************************************/
    @RequestMapping("/deleteLabRoomDeviceAgent")
    public ModelAndView deleteLabRoomDeviceAgent(@RequestParam int deviceId) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = labRoomDeviceService
                .findLabRoomDeviceByPrimaryKey(deviceId);
        labRoomDevice.setLabRoomAgent(null);
        labRoomDeviceDAO.store(labRoomDevice);
        mav.setViewName("redirect:/labRoom/addAgent?id=" + deviceId);
        return mav;

    }

    /***********************************************************************************
     * @description 保存物联硬件
     * @author 周志辉
     * @date 2017-08-28
     * **********************************************************************************/
    @RequestMapping("/saveLabRoomDeviceAgentAjax")
    public ModelAndView saveLabRoomDeviceAgentAjax(@RequestParam int deviceId,
                                                   @RequestParam int agentId) {
        ModelAndView mav = new ModelAndView();
        LabRoomDevice labRoomDevice = labRoomDeviceService
                .findLabRoomDeviceByPrimaryKey(deviceId);
        labRoomDevice.setLabRoomAgent(labRoomDeviceService
                .findLabRoomAgentByPrimaryKey(agentId));
        labRoomDeviceDAO.store(labRoomDevice);
        mav.setViewName("redirect:/labRoom/addAgent?id=" + deviceId);
        return mav;

    }

    /**
     * 实验室列表(App用)
     *
     * @throws IOException
     * @author hly 2015.07.28
     */
    @RequestMapping("/listLabRoomApp")
    public void listLabRoomApp(HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        Configuration config = new Configuration().configure();
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();
        Transaction ts = session.beginTransaction();
        response.setContentType("text/ html；charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String sql = " select id,lab_room_name, lab_room_number, lab_room_address, reservation_number from lab_room"
                + " where is_used = 1 and lab_room_reservation = 1";
        SQLQuery queryList = session.createSQLQuery(sql); // 返回对象
        queryList.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> results = queryList.list();
        JsonConfig jsonConfig = new JsonConfig();
        // 设置javabean中日期转换时的格式
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());
        JSONArray jsonArray = JSONArray.fromObject(results, jsonConfig);
        // out.println(jsonArray);
        jsonObject.put("labRooms", jsonArray);
        out.print(jsonObject);
    }

    /***********************************************************************************
     * @throws ParseException
     * @description：实验工作人员--实验室工作人员记录（导入实验室工作人员）
     * @author 郑昕茹
     * @date：2016-12-19
     * **********************************************************************************/
    @RequestMapping("/importLabWorker")
    public ModelAndView importLabWorker(HttpServletRequest request)
            throws ParseException {
        ModelAndView mav = new ModelAndView();
        String fileName = shareService.getUpdateFilePath(request);
        String logoRealPathDir = request.getSession().getServletContext()
                .getRealPath("/");
        String filePath = logoRealPathDir + fileName;
        System.out.println(filePath);
        try {
            if (filePath.endsWith("xls") || filePath.endsWith("xlsx")) {
                labRoomService.importLabWorker(filePath);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mav.setViewName("redirect:/labRoom/listLabWorker?currpage=1");
        return mav;
    }

    /***********************************************************************************
     * @description：实验工作人员--实验室工作人员记录（导入实验室工作人员钱的格式检查）
     * @author 郑昕茹
     * @日期：2016-08-04
     * **********************************************************************************/
    @RequestMapping("/checkRegex")
    public @ResponseBody
    String checkRegex(HttpServletRequest request) throws ParseException {
        // 获取文件地址
        String fileName = shareService.getUpdateFilePath(request);
        // 获取服务器地址
        String logoRealPathDir = request.getSession().getServletContext()
                .getRealPath("/");
        // 获取文件全部地址
        String filePath = logoRealPathDir + fileName;
        return labRoomService.checkRegex(filePath);
    }

    /*****************************
     * Description 实训室开放预约-信誉积分管理
     *
     * @author:余南新
     * @param:cid(实训中心id)
     * @date:2017.4.26
     *****************************/
    /*
	 * @RequestMapping("/listLabRoomCreditManage") public ModelAndView
	 * listLabRoomCreditManage(@RequestParam int page) { ModelAndView mav = new
	 * ModelAndView();
	 *
	 * int pageSize=20;//每页20条记录 //查询出来的总记录条数 int
	 * totalRecords=labRoomReservationService
	 * .getCountUserCreditWarningByQuery(); //int
	 * totalRecords=labRoomReservationService
	 * .getCountUserCreditWarningByCenter(cid); //分页信息 Map<String,Integer>
	 * pageModel =shareService.getPage(page, pageSize,totalRecords);
	 * //根据分页信息查询出来的记录 List<User>
	 * users=labRoomReservationService.findUserCreditWarningByQuery(page,
	 * pageSize); //List<User>
	 * users=labRoomReservationService.findUserCreditWarningByCenter(cid, page,
	 * pageSize);
	 *
	 * mav.addObject("users",users); mav.addObject("pageModel",pageModel);
	 * mav.addObject("totalRecords", totalRecords); mav.addObject("page", page);
	 * mav.addObject("pageSize", pageSize); //mav.addObject("cid", cid);
	 * mav.setViewName
	 * ("labroom/lab_room_reservation/listLabRoomCreditManage.jsp"); return mav;
	 * }
	 */

    /*****************************
     * Description 实训室开放预约-信誉积分修改
     *
     * @author:余南新
     * @param:username(user表主键)
     * @date:2017.4.26
     *****************************/
    @RequestMapping("/modifyCredit")
    public ModelAndView modifyCredit(@RequestParam String username) {
        ModelAndView mav = new ModelAndView();
        User user = userDAO.findUserByPrimaryKey(username);
        mav.addObject("user", user);
        //查询所有可用的扣分项
        List<CreditOption> creditOptions = creditOptionService.findAllUseCreditOptionByQuery();
        mav.addObject("creditOptions",creditOptions);
        //查询所有通过的预约根据username
        List<LabReservation> labReservations = creditOptionService.findLabRoomReservstionByReserveUserAndStatus(user.getUsername());
        mav.addObject("labReservations",labReservations);
        List<LabRoomDeviceReservation> labRoomDeviceReservations = creditOptionService.findLabDeviceReservstionByReserveUserAndStatus(user.getUsername());
        mav.addObject("labRoomDeviceReservations",labRoomDeviceReservations);
        List<LabRoomStationReservation> labRoomStationReservations = creditOptionService.findLabRoomStationReservationByReserveUserAndStatus(user.getUsername());
        mav.addObject("labRoomStationReservations",labRoomStationReservations);
        mav.setViewName("labroom/lab_room_reservation/modifyCredit.jsp");
        return mav;
    }

    /*****************************
     * Description 实训室开放预约-信誉积分保存
     *
     * @author:余南新
     * @param:
     * @date:2017.4.26
     *****************************/
    @RequestMapping("/saveCreditUser")
    public ModelAndView saveCreditUser(@ModelAttribute User user,HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        User userTrue = userDAO.findUserByPrimaryKey(user.getUsername());
        //获取预约的类型
        String reservationsType = request.getParameter("reservationsType");
        //获取扣分项id
        String reditOption = request.getParameter("sumDeduction");
        //扣分总数初始化
        Integer sumDeduction = 0;
        if(reservationsType!=null&&reditOption!=null&&!reservationsType.equals("")&&!reditOption.equals("")){
        	creditOptionService.saveDeductingRecord(reservationsType, reditOption, user.getUsername());
        	sumDeduction = creditOptionService.deductingRecordMark(reditOption);
        }
        userTrue.setCreditScore(userTrue.getCreditScore()-sumDeduction);
        userDAO.store(userTrue);
        if (userTrue.getUserRole().equals("1")) {
            mav.setViewName("redirect:/labRoom/listLabRoomTeacherCreditManage?page=1");
        } else if (userTrue.getUserRole().equals("0")) {
            mav.setViewName("redirect:/labRoom/listLabRoomStudentCreditManage?page=1");
        }
        // mav.setViewName("redirect:/labRoom/listLabRoomStudentCreditManage?page=1");
        return mav;
    }

    /****************************************************************************
     * Description：实训室借用列表
     *
     * @param:labRoomLending 实训室借用列表
     * @author：邵志峰
     * @date:2017-06-26
     ****************************************************************************/
    @RequestMapping("/labRoomLendList")
    public ModelAndView labRoomLendList(
            @ModelAttribute LabRoomLending labRoomLending, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("labRoomLending", labRoomLending);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.getReturnedLendingTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomLending> labRoomLendList = labRoomService.findAllLending(
                labRoomLending, page, pageSize);
        mav.addObject("labRoomLendList", labRoomLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
		/*
		 * if (user.getAuthorities().size() > 0) { for (Authority a :
		 * user.getAuthorities()) { if (a.getId() == 7 || a.getId() == 4 ||
		 * a.getId() == 11) { mav.addObject("ca", true); } } }
		 */
        mav.addObject("user", user);
        mav.setViewName("/lab/lab_room/labRoomLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：实训室借用申请
     *
     * @author：邵志峰
     * @date:2017-05-24
     ****************************************************************************/
    @RequestMapping("/labRoomLendingApply")
    public ModelAndView labRoomLendingApply() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomLending", new LabRoomLending());// 新建申请单对象
        // 查找可以预约的软件，暂时不做
        mav.addObject("labRoomList", systemService.getLabRoom());// 返回可用的实训室
        // mav.addObject("lendingUserTypeList",systemService.loadLendingUserTypes());//返回使用人类型
        // mav.addObject("labRoomLendingTypeList",systemService.loadLabRoomLendingTypes());//借用类型
        mav.addObject("currUser", shareService.getUser());// 返回当前用户
        mav.addObject("user", systemService.loadUser());// 返回所有实训室管理员
        mav.addObject("schoolClassess", systemService.loadSchoolClassess());// 返回所有班级
        mav.setViewName("/lab/lab_room/labRoomLendApply.jsp");
        return mav;
    }

    /****************************************************************************
     * @throws ParseException
     * @功能：安全准入验证
     * @作者：李小龙
     ****************************************************************************/
    @RequestMapping(value = "/securityAccess", method = RequestMethod.POST)
    public @ResponseBody
    String securityAccess(@RequestParam Integer id, HttpServletRequest request) throws ParseException {
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
        if (labRoom.getLabRoomReservation() == null || labRoom.getLabRoomReservation() == 0) {
            return "noReservation";
        }
        if (!labRoomService.isSettingForLabRoom(id)) {
            return "noSetting";
        }
        User user = shareService.getUser();
        String data = labRoomService.securityAccess(user.getUsername(), id, request);
        boolean LabRoomStationGradedOrNot = shareService.getAuditOrNot("LabRoomStationGradedOrNot");
        String grade = "";
        if(LabRoomStationGradedOrNot){
            grade = labRoom.getLabRoomLevel().toString();
            boolean audit = shareService.getExtendItem(grade + "LabRoomStationGradedOrNot");
            if (!audit) {
                data = "noAudit" + grade;
            }
        }
        if ("success".equals(data)) {
            //demo
            boolean flag = true;
            String[] RSWITCH = {"on", "off"};
            String[] auditLevelName = {"TEACHER", "CFO", "LABMANAGER", "EXCENTERDIRECTOR", "PREEXTEACHING"};
            Map<String, String> params = new HashMap<>();
            params.put("businessUid", labRoom.getId().toString());
            params.put("businessType", pConfig.PROJECT_NAME + "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber());
            if (request.getParameter("requestType") != null && request.getParameter("requestType").equals("labRoomStation")) {
                params.put("businessType",grade + "StationReservation");
            }
            String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
            String status = jsonObject.getString("status");
            Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
            if (auditConfigs != null && auditConfigs.size() != 0) {
                for (int i = 0; i < auditConfigs.size(); i++) {
                    String[] text = ((String) auditConfigs.get(i + 1)).split(":");
                    if (text[0].equals(auditLevelName[0])) {
                        flag = text[1].equals(RSWITCH[0]);
                        break;
                    }
                }
            }
            if (request.getParameter("requestType") != null && request.getParameter("requestType").equals("labRoomStation")) {
                if (flag) {
                    data = "needTutor";
                } else {
                    data = "noNeedTutor";
                }
            } else {
                if (flag && "ROLE_STUDENT".equals(request.getSession().getAttribute("selected_role"))) {
                    data = "needTutor";
                } else {
                    data = "noNeedTutor";
                }
            }
        }
        return htmlEncode(data);
    }

    /****************************************************************************
     * @throws ParseException
     * @功能：实验室预约安全准入验证
     * @作者：Hezhaoyi
     * 2019-4-14
     ****************************************************************************/
    @RequestMapping(value = "/labSecurityAccess", method = RequestMethod.POST)
    public @ResponseBody
    String labSecurityAccess(@RequestParam Integer id, HttpServletRequest request) throws ParseException {

        String returnStr = "success";
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(id);
        if(labRoom.getLabRoomReservation() == null || labRoom.getLabRoomReservation() == 0){
            returnStr = "noReservation";
        } else if(!labRoomService.isSettingForLabRoom(id)){
            returnStr = "noSetting";
        } else {
            User user = shareService.getUser();
            String username = user.getUsername();

            // 当前用户是否为实验室中心主任
            String judge = ",";
            for (Authority authority : user.getAuthorities()) {
                judge = judge + "," + authority.getId() + ",";
            }

            CDictionary allowSecurityAccess = labRoom.getCDictionaryByAllowSecurityAccess();
            if (allowSecurityAccess != null && allowSecurityAccess.getId() == 621) {//需要安全准入
                // 中心主任或设备管理员不需要进行培训
                if (judge.indexOf(",4,") > -1 ||
                        (labRoom.getLabRoomAdmins() != null && labRoom.getLabRoomAdmins().contains(username))) {
                    // do nothing
                } else {
                    if (labRoomService.findPermitUserByUsernameAndLab(username, id) == null) {
                        returnStr = "needAccess";
                    }
                }
            }
        }
        // 判断是否需要教师审核{以及审核权限为教师&当前权限为学生=bingo}
        if (returnStr.equals("success")) {
            //demo
            boolean flag = true;
            String[] RSWITCH = {"on", "off"};
            String[] auditLevelName = {"TEACHER", "CFO", "LABMANAGER", "EXCENTERDIRECTOR", "PREEXTEACHING"};
            Map<String, String> params = new HashMap<>();
            params.put("businessUid", labRoom.getId().toString());
            params.put("businessType", pConfig.PROJECT_NAME + "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber());
            String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
            Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
            if (auditConfigs != null && auditConfigs.size() != 0) {
                for (int i = 0; i < auditConfigs.size(); i++) {
                    String[] text = ((String) auditConfigs.get(i + 1)).split(":");
                    if (text[0].equals(auditLevelName[0])) {
                        flag = text[1].equals(RSWITCH[0]);
                        break;
                    }
                }
            }
            if (flag && "ROLE_STUDENT".equals(request.getSession().getAttribute("selected_role"))) {
                returnStr = "needTutor";
            } else {
                returnStr = "noNeedTutor";
            }
        }
        return returnStr;
    }

    /**********************************************************************
     * Description:实训室借用申请保存
     *
     * @author：邵志峰
     * @date:2017-06-27
     *********************************************************************/
    @RequestMapping("/saveLabRoomLending")
    public String saveLabRoomLending(
            @ModelAttribute LabRoomLending labRoomLending) throws Exception {

        int id = shareService.saveLabRoomLending(labRoomLending);// 保存申请单
        // 发送消息
        messageService.saveMessages(shareService.getUser().getUsername(),
                "实验室借用申请",
                "您申请借用的实验室成功，请等待审核 <a href='softwareReserveInfo?idKey=" + id
                        + "'>点击查看</a>", 2);
        return "redirect:/labRoom/labRoomLendList?page=1";
    }

    /****************************************************************************
     * Description：审核通过的实训室借用列表
     *
     * @param:labRoomLending 实训室借用列表
     * @author：邵志峰
     * @date:2017-06-28
     ****************************************************************************/
    @RequestMapping("/passLabRoomLendList")
    public ModelAndView passLabRoomLendList(
            @ModelAttribute LabRoomLending labRoomLending, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("labRoomLending", labRoomLending);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.getPassLendingTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomLending> labRoomLendList = labRoomService
                .findAllPassLending(labRoomLending, page, pageSize);
        mav.addObject("labRoomLendList", labRoomLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
		/*
		 * if (user.getAuthorities().size() > 0) { for (Authority a :
		 * user.getAuthorities()) { if (a.getId() == 7 || a.getId() == 4 ||
		 * a.getId() == 11) { mav.addObject("ca", true); } } }
		 */
        mav.addObject("user", user);
        mav.setViewName("/lab/lab_room/passLabRoomLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：审核未通过的实训室借用列表
     *
     * @param:labRoomLending 实训室借用列表
     * @author：邵志峰
     * @date:2017-06-28
     ****************************************************************************/
    @RequestMapping("/rejectLabRoomLendList")
    public ModelAndView rejectLabRoomLendList(
            @ModelAttribute LabRoomLending labRoomLending, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("labRoomLending", labRoomLending);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.getRejectLendingTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomLending> labRoomLendList = labRoomService
                .findAllRejectLending(labRoomLending, page, pageSize);
        mav.addObject("labRoomLendList", labRoomLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
		/*
		 * if (user.getAuthorities().size() > 0) { for (Authority a :
		 * user.getAuthorities()) { if (a.getId() == 7 || a.getId() == 4 ||
		 * a.getId() == 11) { mav.addObject("ca", true); } } }
		 */
        mav.addObject("user", user);
        mav.setViewName("/lab/lab_room/rejectLabRoomLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：未审核的实训室借用列表
     *
     * @param:labRoomLending 实训室借用列表
     * @author：邵志峰
     * @date:2017-06-28
     ****************************************************************************/
    @RequestMapping("/waitLabRoomLendList")
    public ModelAndView waitLabRoomLendList(
            @ModelAttribute LabRoomLending labRoomLending, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("labRoomLending", labRoomLending);
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.getWaitLendingTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomLending> labRoomLendList = labRoomService
                .findAllWaitLending(labRoomLending, page, pageSize);
        mav.addObject("labRoomLendList", labRoomLendList);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        // 当前登录人
        User user = shareService.getUser();
        // 判断当前登录人是否为系教学秘书或者超级管理员或者是实训部教学秘书
        if (user.getAuthorities().size() > 0) {
            for (Authority a : user.getAuthorities()) {
                if (a.getId() == 22 || a.getId() == 9 || a.getId() == 11) {
                    mav.addObject("ca", true);
                }
            }
        }
        mav.addObject("user", user);
        mav.setViewName("/lab/lab_room/waitLabRoomLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：审核实训室出借申请单
     *
     * @author：邵志峰
     * @ate：2017-06-28
     ****************************************************************************/
    @RequestMapping("/auditLabRoomLending")
    public ModelAndView auditLabRoomLending(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        LabRoomLending lrdl = labRoomDeviceService
                .findLabRoomLendingById(idKey);
        mav.addObject("lrdl", lrdl);
        mav.addObject("idKey", idKey);
        mav.addObject("user", shareService.getUser());
        mav.addObject("result", labRoomDeviceService.getAuditResultMap());
        mav.addObject("lrdlr", new LabRoomLendingResult());
        mav.setViewName("/lab/lab_room/auditLabRoomLending.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：保存审核的实训室申请单
     *
     * @author：邵志峰
     * @date：2017-06-28
     ****************************************************************************/
    @RequestMapping("/saveAuditLabRoomLending")
    public String saveAuditDeviceLending(
            @ModelAttribute LabRoomLendingResult lrdlr, Integer idKey) {
        LabRoomLendingResult l = labRoomLendingResultDAO.store(lrdlr);
        LabRoomLending lrdl = labRoomService.findLabRoomLendingById(idKey);

        if ("2".equals(l.getCDictionary().getCNumber())) {// 字典表中2为审核通过
            lrdl.setLendingStatus(3);// 3即为审核通过
            labRoomLendingDAO.store(lrdl);
        }
        if ("3".equals(l.getCDictionary().getCNumber())) {// 字典表中3为审核拒绝
            lrdl.setLendingStatus(2);// 2即为审核拒绝
            labRoomLendingDAO.store(lrdl);
        }
        return "redirect:/labRoom/labRoomLendList?page=1";
    }

    /*****************************
     * Description 实训室教师信誉积分管理
     *
     * @author:周志辉
     * @date:2017.8.14
     *****************************/
    @RequestMapping("/listLabRoomTeacherCreditManage")
    public ModelAndView listLabRoomTeacherCreditManage(@RequestParam int page,
                                                       @ModelAttribute User user, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        int pageSize = 20;// 每页20条记录
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService
                .getCountTeacherCreditWarningByQuery(user);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<User> users = labRoomReservationService
                .findTeacherCreditWarningByQuery(page, pageSize, user);
        mav.addObject("me", shareService.getUser());
        mav.addObject("users", users);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("labroom/lab_room_reservation/listLabRoomTeacherCreditManage.jsp");
        return mav;
    }

    /*****************************
     * Description 查询扣分纪录
     *
     * @author:周志辉
     * @date:2017.10.9
     *****************************/
    @RequestMapping("/creditRecord")
    public ModelAndView creditRecord(@RequestParam String username) {
        ModelAndView mav = new ModelAndView();
        List<LabRoomDeviceReservationCredit> listLabRoomDeviceReservationCredit = labRoomDeviceReservationCreditService
                .findCreditByUsername(username);
        List<LabRoomStationReservationCredit> listLabRoomStationReservationCredit = labRoomReservationService
                .findCreditByUsername(username);
        List<LabRoomReservationCredit> listLabRoomReservationCredit = labReservationService.findCreditByUsername(username);
        mav.addObject("listLabRoomDeviceReservationCredit",
                listLabRoomDeviceReservationCredit);
        mav.addObject("listLabRoomStationReservationCredit", listLabRoomStationReservationCredit);
        mav.addObject("listLabRoomReservationCredit", listLabRoomReservationCredit);
        mav.setViewName("labroom/lab_room_reservation/userCreditRecord.jsp");
        return mav;
    }

    /*****************************
     * Description 实训室教师信誉积分管理
     *
     * @author:周志辉
     * @date:2017.8.14
     *****************************/
    @RequestMapping("/initializeTeacherCredit")
    public String initializeTeacherCredit(@RequestParam int page) {
        List<User> users = labRoomReservationService.findTeacherByQuery();
        for (User user : users) {
            if (user.getUserRole().equals("1")) {
                user.setCreditScore(100);
                userDAO.store(user);
            }
        }

        return "redirect:/labRoom/listLabRoomTeacherCreditManage?page=1";
    }

    /*****************************
     * Description 实训室教师信誉积分管理
     *
     * @author:周志辉
     * @date:2017.8.14
     *****************************/
    @RequestMapping("/initializeStudentCredit")
    public String initializeStudentCredit(@RequestParam int page) {
        List<User> users = labRoomReservationService.findStudentByQuery();
        for (User user : users) {
            if (user.getUserRole().equals("0")) {
                user.setCreditScore(100);
                userDAO.store(user);
            }
        }

        return "redirect:/labRoom/listLabRoomStudentCreditManage?page=1";
    }

    /*****************************
     * Description 实训室教师信誉积分管理
     *
     * @author:周志辉
     * @date:2017.8.14
     *****************************/
    @RequestMapping("/listLabRoomStudentCreditManage")
    public ModelAndView listLabRoomStudentCreditManage(@RequestParam int page,
                                                       @ModelAttribute User user) {
        ModelAndView mav = new ModelAndView();

        int pageSize = 20;// 每页20条记录
        // 查询出来的总记录条数
        int totalRecords = labRoomReservationService
                .getCountStudentCreditWarningByQuery(user);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<User> users = labRoomReservationService
                .findStudentCreditWarningByQuery(page, pageSize, user);
        mav.addObject("me", shareService.getUser());
        mav.addObject("users", users);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("labroom/lab_room_reservation/listLabRoomStudentCreditManage.jsp");
        return mav;
    }

    /*****************************
     * Description 编辑机培训进修登记
     *
     * @author:周志辉
     * @date:2017.8.16
     *****************************/
    @RequestMapping("/editLabWorkerTraining")
    public ModelAndView editLabWorkerTraining(@RequestParam int labWorkerId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labWorkerTraining", new LabWorkerTraining());
        List<CDictionary> trainingTypes = shareService
                .getCDictionaryData("c_lab_room_training_type");
        mav.addObject("trainingTypes", trainingTypes);
        mav.addObject("labWorkerId", labWorkerId);
        mav.setViewName("lab/lab_room/editLabWorkerTraining.jsp");
        return mav;
    }

    /**
     * 保存实验室队伍培训进修登记
     *
     * @author 周志辉 2017.08.16
     */
    @RequestMapping("/saveLabWorkerTraining")
    public String saveLabWorkerTraining(
            @ModelAttribute LabWorkerTraining labWorkerTraining,
            @RequestParam int labWorkerId) {

        labWorkerTraining.setLabWorker(labWorkerDAO
                .findLabWorkerById(labWorkerId));
        labWorkerTrainingDAO.store(labWorkerTraining);
        // 获取所有培训纪录取最后一次插入的数据
        // List<LabWorkerTraining>
        // labWorkerTrainings=labWorkerTrainingService.findAllLabWorkerTrainings(0,
        // -1);
        // LabWorkerTraining
        // labWorkerTrainingDetail=labWorkerTrainings.get(labWorkerTrainings.size()-1);
        return "redirect:/labRoom/listLabWorkerTraining?currpage=1&labWorkerId=" + labWorkerId;
    }

    /****************************************************************************
     * @功能：给实验室队伍培训进修上传文档
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/labWorkerTrainingDocumentUpload")
    public ModelAndView labWorkerTrainingDocumentUpload(HttpServletRequest request,
                                                        HttpServletResponse response, BindException errors, Integer id)
            throws Exception {
        ModelAndView mav = new ModelAndView();
        labWorkerTrainingService.labWorkerTrainingDocumentUpload(request,
                response, id, 2);
        mav.setViewName("redirect:/labRoom/labWorkerTrainingDetail?labWorkerTrainingId=" + id + "&flag=0");
        return mav;
    }

    /*****************************
     * Description 实训室队伍培训进修详情
     *
     * @author:周志辉
     * @param:
     * @date:2018.08.21
     *****************************/
    @RequestMapping("/labWorkerTrainingDetail")
    public ModelAndView labWorkerTrainingDetail(
            @RequestParam int labWorkerTrainingId, int flag) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("flag", flag);
        mav.addObject("labWorkerTraining", labWorkerTrainingService
                .findLabWorkerTrainingDetailByPrimaryKey(labWorkerTrainingId));
        mav.addObject("labWorkerId", labWorkerTrainingService
                .findLabWorkerTrainingDetailByPrimaryKey(labWorkerTrainingId)
                .getLabWorker().getId());
        mav.setViewName("lab/lab_room/labWorkerTrainingDetail.jsp");
        return mav;
    }

    /****************************************
     * 功能：实验室管理--培训计划--已通过名单列表 作者：周志辉 日期：2017-08-21
     ***************************************/
    @RequestMapping("/managePermitUser")
    public ModelAndView managePermitUser(
            @ModelAttribute LabRoomPermitUser labRoomPermitUser,
            @RequestParam int labRoomId, int currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;
        int totalRecords = 0;
        if (labRoomService.findPermitUserByLabRoomId(labRoomPermitUser,
                labRoomId, 1, -1) != null) {
            totalRecords = labRoomService.findPermitUserByLabRoomId(labRoomId,
                    1, -1).size();
        }
        mav.addObject("allStudents",
                labRoomService.findPermitUserByLabRoomId(labRoomId, 1, -1));
        List<LabRoomPermitUser> students = labRoomService
                .findPermitUserByLabRoomId(labRoomPermitUser, labRoomId,
                        currpage, pageSize);
        mav.addObject("students", students);
        mav.addObject("student", new LabRoomPermitUser());

        mav.addObject("labRoomId", labRoomId);
        mav.addObject("pageModel",
                shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("totalRecords", totalRecords);
        mav.setViewName("lab/lab_room/managePermitUser.jsp");
        return mav;
    }
    /****************************************************************************
     * Description：已培训名单删除
     * @param id
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    @RequestMapping("/deletePermitUser")
    public ModelAndView deletePermitUser(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        LabRoomPermitUser permitUser = labRoomPermitUserDAO.findLabRoomPermitUserById(id);
        String username = permitUser.getUser().getUsername();
        int labRoomId = permitUser.getLabRoom().getId();
        if (permitUser.getFlag() == 2) {// 集训通过的人，如果删除的话需要将集训结果置为未通过并重新计算培训的通过率
            LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
            for (LabRoomTraining training : labRoom.getLabRoomTrainings()) {
                int trainingId = training.getId();
                List<LabRoomTrainingPeople> peoples = labRoomDeviceService.findLabRoomTrainingPeoplesByTrainingId(trainingId);
                for (LabRoomTrainingPeople labRoomTrainingPeople : peoples) {
                    if (!labRoomTrainingPeople.getUser().getUsername().equals(username)) {// 不是当前用户，继续
                        continue;
                    } else {// 是当前用户，将培训结果设置为未通过
                        // 将集训结果置为未通过
                        //CTrainingResult result = cTrainingResultDAO.findCTrainingResultByPrimaryKey(2);
                        CDictionary result = shareService.getCDictionaryByCategory("c_training_result", "2");
                        labRoomTrainingPeople.setCDictionary(result);
                        labRoomTrainingPeopleDAO.store(labRoomTrainingPeople);
                    }
                }

                // 重新计算培训的通过率
                if (training.getCDictionary().getId() == 2) {
                    // 该培训的培训人
                    Set<LabRoomTrainingPeople> trainingpeoples = training.getLabRoomTrainingPeoples();
                    // 根据培训id查询培训通过的人
                    List<LabRoomTrainingPeople> passPeoples = labRoomDeviceService.findPassLabRoomTrainingPeopleByTrainId(training.getId());
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
                    labRoomTrainingDAO.store(training);
                }
            }
        }
        labRoomDeviceService.deleteLabRoomPermitUser(permitUser);
        mav.setViewName("redirect:/labRoom/managePermitUser?labRoomId=" + labRoomId + "&currpage=1");
        return mav;
    }
    /****************************************************************************
     * 功能：AJAX 根据姓名、工号查询当前登录人所在学院的用户 作者：周志辉
     ****************************************************************************/
    @RequestMapping("/findStudentByCnameAndUsername")
    public @ResponseBody
    String findStudentByCnameAndUsername(@RequestParam String cname,
                                         String username, Integer page, Integer labRoomId) {

        User u = shareService.getUser();
        String academyNumber = "";
        if (u.getSchoolAcademy() != null) {
            academyNumber = u.getSchoolAcademy().getAcademyNumber();
        }
        User user = new User();
        user.setCname(cname);
        user.setUsername(username);

        // 分页开始
        int totalRecords = labRoomService.findStudentByCnameAndUsername(user,
                academyNumber, labRoomId);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<User> userList = labRoomService.findStudentByCnameAndUsername(
                user, academyNumber, labRoomId, page, pageSize);
        String s = "";
        for (User d : userList) {
            String academy = "";
            if (d.getSchoolAcademy() != null) {
                academy = d.getSchoolAcademy().getAcademyName();
            }
            s += "<tr>" + "<td><input type='checkbox' name='CK_name' value='"
                    + d.getUsername() + "'/></td>" + "<td>" + d.getCname()
                    + "</td>" + "<td>" + d.getUsername() + "</td>" + "<td>"
                    + academy + "</td>" +

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
        s += "<tr><td colspan='6'>"
                + "<a href='javascript:void(0)' onclick='addStudent(1);'>"
                + "首页" + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='addStudent("
                + previousPage
                + ");'>"
                + "上一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='addStudent("
                + nextPage
                + ","
                + pageModel.get("totalPage")
                + ");'>"
                + "下一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='addStudent("
                + (totalRecords + pageSize - 1) / pageSize
                + ");'>"
                + "末页"
                + "</a>&nbsp;"
                + "当前第"
                + page
                + "页&nbsp; 共"
                + pageModel.get("totalPage")
                + "页  "
                + totalRecords
                + "条记录"
                + "</td></tr>";
        return htmlEncode(s);
    }

    /****************************************************************************
     * 功能：保存集中培训学生后门 作者：周志辉 日期：2017-08-21
     ****************************************************************************/
    @RequestMapping("/savePermitUser")
    public ModelAndView savePermitUser(@RequestParam String usernameStr,
                                       @RequestParam int labRoomId) {
        String[] array = usernameStr.split(";");
        ModelAndView mav = new ModelAndView();
        // id对应的培训
        LabRoom labRoom = labRoomService.findLabRoomByPrimaryKey(labRoomId);
        for (String i : array) {
            // username对应的用户
            LabRoomPermitUser student = new LabRoomPermitUser();
            User u = userDAO.findUserByPrimaryKey(i);
            student.setUser(u);
            student.setLabRoom(labRoom);
            student.setFlag(3);// 标记位（1 单独培训通过 2 集训通过 3 集训后门）
            labRoomPermitUserDAO.store(student);
        }
        mav.setViewName("redirect:/labRoom/managePermitUser?labRoomId="
                + labRoomId + "&currpage=1");
        return mav;
    }

    /****************************************************************************
     * @throws UnsupportedEncodingException
     * @功能：删除培训进修登记文档
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/deleteLabWorkerTrainingDocument")
    public ModelAndView deleteLabWorkerTrainingDocument(@RequestParam int id,
                                                        int labWorkerTrainingId) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        // id对应的设备图片
        CommonDocument doc = commonDocumentService
                .findCommonDocumentByPrimaryKey(id);
        commonDocumentService.deleteCommonDocument(doc);
        mav.setViewName("redirect:/labRoom/labWorkerTrainingDetail?labWorkerTrainingId=" + labWorkerTrainingId + "&flag=0");
        return mav;
    }

    /**
     * 实验室人员培训进修登记列表
     *
     * @author 周志辉 2017.08.29
     */
    @RequestMapping("/listLabWorkerTraining")
    public ModelAndView listLabWorkerTraining(@RequestParam int currpage, int labWorkerId) {
        ModelAndView mav = new ModelAndView();
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        int totalRecords = labRoomService.findAllLabWorkerTrainingByQuery(1, -1, labWorkerId).size();
        mav.addObject("pageModel",
                shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("listLabWorkerTraining", labRoomService
                .findAllLabWorkerTrainingByQuery(currpage, pageSize, labWorkerId));
        mav.addObject("labWorkerId", labWorkerId);
        mav.setViewName("lab/lab_worker/listLabWorkerTraining.jsp");
        return mav;
    }

    /**
     * 删除实验室数据
     *
     * @author hly 2015.07.28
     */
    @RequestMapping("/deleteLabWorkerTraining")
    public String deleteLabWorkerTraining(@RequestParam int labWorkerTrainingId) {
        LabWorkerTraining labWorkerTraining = labWorkerTrainingService
                .findLabWorkerTrainingByPrimaryKey(labWorkerTrainingId);
        labWorkerTrainingService.deleteLabWorkerTraining(labWorkerTraining);
        // labRoomService.deleteLabRoom(labRoomId); 真删 由于和实验项目的外键关系，会报错删不掉

        return "redirect:/labRoom/listLabWorkerTraining?currpage=1";
    }

    /****************************************************************************
     * 功能：删除实验室设备 作者：贺子� 时间�015-09-15
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomDeviceNewV")
    public ModelAndView deleteLabRoomDeviceNewV(
            @RequestParam Integer labDeviceId) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联管理�
        LabRoomDevice device = labRoomDeviceDAO
                .findLabRoomDeviceByPrimaryKey(labDeviceId);
        int id = device.getLabRoom().getId();
        labRoomDeviceDAO.remove(device);
        mav.setViewName("redirect:/visualization/addLabRoomDevice?id=" + id);
        return mav;
    }

    /****************************************************************************
     * 功能：AJAX 根据设备名称、设备编号查询当前登录人所在学院的设备(可视化-实验室监控) 作者：李小龙
     ****************************************************************************/
    @RequestMapping("/openVideoForV")
    public @ResponseBody
    String openVideoForV(@RequestParam Integer roomId) {
        String url = "";
        // id对应的实验分室
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
        // 物联设备
        Set<LabRoomAgent> agents = labRoom.getLabRoomAgents();
        LabRoomAgent agent = null;
        for (LabRoomAgent a : agents) {
//            CDictionary cDictionary = shareService.findCDictionaryByPrimaryKey(a.getCDictionary().getId());
//            if (cDictionary.getCNumber().equals("3") && cDictionary.getCCategory().equals("c_agent_type")) {// 视频
//                agent = a;
//                break;
//            }
        }
        if (agent != null) {
            // LabRoomAgent
            // agent=labRoomAgentDAO.findLabRoomAgentByPrimaryKey(id);
            // 根据实验室判断实验室所属学院的视频为一期或者二期

			/*
			 * if(time==2){//二期 System.out.println("-----");
			 * url="http://"+agent.
			 * getHardwareIp()+":"+agent.getHardwarePort()+"/PageCam"
			 * +agent.getHardwareRemark(); }else{//一期
			 * System.out.println("-++++");
			 * url="http://"+agent.getCommonServer()
			 * .getServerIp()+"/webcu/index2.php?id="
			 * +agent.getHardwarePort()+"&ip="+agent.getHardwareIp(); }
			 */
            String[] ip = agent.getHardwareIp().split("\\.");
            String ip3 = ip[2] + ip[3];
            url = "rtmp://" + agent.getCommonServer().getServerIp()
                    + ":1935/live/" + ip3;
//			url = "http://" + agent.getCommonServer().getServerIp()
//					+ ":8080/players/jwplayer6.html?stream=" + ip3;

            return htmlEncode(url);
        } else {
            return htmlEncode(url);
        }
    }

    /****************************************************************************
     * 功能：AJAX 根据设备名称、设备编号查询当前登录人所在学院的设备
     * 作者：李小龙
     ****************************************************************************/
    @RequestMapping("/appointment/openVideo2")
    public ModelAndView openVideo2(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        //物联设备
        String url = "";
        // id对应的实验分室
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(id);
        // 物联设备
        Set<LabRoomAgent> agents = labRoom.getLabRoomAgents();
        LabRoomAgent agent = null;
        for (LabRoomAgent a : agents) {
            if (shareService.checkCDictionary(a.getCDictionary().getId(),"3","c_agent_type")) {// 视频设备
                agent = a;
                break;
            }
        }
        if (agent != null) {
//			int time = shareService.getLabRoomBelongsTime(labRoom);
//			String[] ip = agent.getHardwareIp().split("\\.");
//			String ip3 = ip[3];
//			url = "rtmp://" + agent.getCommonServer().getServerIp()
//					+ ":1935/live/" + ip3;
            String[] ip = agent.getHardwareIp().split("\\.");
            String ip3 = ip[3];
//			String url = "http://" + agent.getCommonServer().getServerIp()+ ":8080/players/jwplayer6.html?stream=" + ip3;
            mav.addObject("agentPort", 1935);
            mav.addObject("agentIp", agent.getCommonServer().getServerIp());
            mav.addObject("agentRemark", ip3);
        }
        mav.setViewName("lab/labVideo.jsp");
        return mav;
    }

    /****************************************************************************
     * @功能：下载文档
     * @作者：周志辉
     ****************************************************************************/
    @RequestMapping("/downloadLabWorkerTrainingDocument")
    public void downloadLabWorkerTrainingDocument(HttpServletRequest request,
                                                  HttpServletResponse response, int id) {
        // id对应的文档
        CommonDocument doc = commonDocumentService
                .findCommonDocumentByPrimaryKey(id);
        softwareService.downloadFile(doc, request, response);
    }

    /****************************************************************************
     * Description：软件安装申请
     *
     *
     ****************************************************************************/

    @RequestMapping("/ceshi")
    public ModelAndView applyInstallSoftwares(@RequestParam int currPage,
                                              @ModelAttribute Software software) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("softwareReserve", new SoftwareReserve());//新建申请单对象
        int pageSize = 10;
        int totalRecords = softwareService.findAllSoftwareByQuery(1, -1,
                software).size();
        List<Software> listSoftware = softwareService.findAllSoftwareByQuery(currPage, pageSize, software);
        // 取实验室名称页面显示
        Map<String, String> labRoomNames = new HashMap<>();
        for(Software s: listSoftware){
            String labRoomName = "";
            if(s.getLabRoom() != null && !"".equals(s.getLabRoom())){
                List<String> labRooms = entityManager.createQuery("select l.labRoomName from LabRoom l where l.id = " + s.getLabRoom()).getResultList();
                if(labRooms.size() > 0){
                    labRoomName = labRooms.get(0);
                }
            }
            labRoomNames.put(s.getLabRoom(), labRoomName);
        }
        mav.addObject("labRoomNames", labRoomNames);
        //分页
        mav.addObject("pageModel", shareService.getPage(currPage, pageSize, totalRecords));
        mav.addObject("listSoftware", listSoftware);
        mav.addObject("currPage", currPage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("pageModel",
                shareService.getPage(currPage, pageSize, totalRecords));
        mav.addObject("softwareList",systemService.loadSoftwares());
//		mav.addObject("termList",systemService.loadSchoolTerms());//返回学期
        mav.addObject("labList",labRoomService.findLabRoomList());//返回实验室
        mav.addObject("user", shareService.getUser());//返回用户
        mav.addObject("courses", shareService.getMyCourse());//获取用户的课程列表
        mav.setViewName("/lab/lab_room/applyInstallSoftwares.jsp");
        return mav;

    }

    /****************************************************************************
     * Description：软件申请列表
     *
     * @param:labRoomLending 软件申请列表
     * @author:张愉
     * @date:2017-09-30
     ****************************************************************************/
    @RequestMapping("/SoftwareReserveList")
    public ModelAndView softwareReserveList(
            @ModelAttribute SoftwareReserve softwareReserve, Integer page, Integer isaudit, Integer tage,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 学期
        List<SchoolTerm> terms = shareService.findAllSchoolTerms();
        mav.addObject("terms", terms);
        mav.addObject("softwareReserve", softwareReserve);
        User user = shareService.getUserDetail();
        // 查询出所有的设备设备预约记录
        int pageSize = 10;// 每页10条记录
        int totalRecords = labRoomService.findAllsoftwareLend(softwareReserve, page, pageSize, tage, isaudit).size();
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<SoftwareReserve> softwareReserves = labRoomService.findAllsoftwareLend(softwareReserve, page, pageSize, tage, isaudit);
        //判断所处审核阶段，关联到前端的按钮
        if (softwareReserves != null) {
            for (SoftwareReserve softwareReserve2 : softwareReserves) {
                //先初始化为0
                softwareReserve2.setButtonMark(0);
                if (softwareReserve2.getState() != null) {
                    //系主任审核阶段
                    if (softwareReserve2.getState() == 2) {
                        //当前登陆人是审核系主任
                        List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
                        if (deans != null) {
                            for (User user2 : deans) {
                                if (user2.getUsername().equals(softwareReserve2.getUser().getUsername())) {
                                    softwareReserve2.setButtonMark(2);
                                    break;
                                }
                            }
                        }
                    }
                    //实训室管理员审核阶段
                    if (softwareReserve2.getState() == 3) {
                        //当前登陆人是审核实训室管理员
                        if (labRoomDeviceService.getLabRoomAdmin(softwareReserve2.getLabRoom().getId(), user.getUsername()) == true) {
                            softwareReserve2.setButtonMark(3);
                        }
                    }
                    //实训中心主任审核阶段
                    if (softwareReserve2.getState() == 4) {
                        //当前登陆人是审核实验室中心主任
                        if (user.getUsername().equals(softwareReserve2.getLabRoom().getLabCenter().getUserByCenterManager().getUsername())) {
                            softwareReserve2.setButtonMark(4);
                        }
                    }
                    //实训部主任审核阶段
                    if (softwareReserve2.getState() == 5) {
                        //当前登陆人是审核实训部主任
                        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityId(3);
                        if (labRoomCenterDirectors != null) {
                            for (User user2 : labRoomCenterDirectors) {
                                if (user.getUsername().equals(user2.getUsername())) {
                                    softwareReserve2.setButtonMark(5);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        mav.addObject("softwareReserves", softwareReserves);

        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("isAudit", isaudit);
        mav.addObject("tage", tage);

        // 判断当前登录人是否为实验教务或者超级管理员或者是本中心主任
		/*
		 * if (user.getAuthorities().size() > 0) { for (Authority a :
		 * user.getAuthorities()) { if (a.getId() == 7 || a.getId() == 4 ||
		 * a.getId() == 11) { mav.addObject("ca", true); } } }
		 */
        mav.addObject("user", user);
        mav.setViewName("/lab/lab_room/softwareLendList.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：查看安装软件申请
     *
     * @author：张愉
     * @date:2017-09-30
     ****************************************************************************/
    @RequestMapping("/softwareReserveInfoList")
    public ModelAndView softwareReserveInfoList(int idKey, int flag, int step)
            throws Exception {
        ModelAndView mav = new ModelAndView();
        // 附件分为两类（ 1 申请说明 2安装说明 ）
        SoftwareReserve software = softwareService
                .findSoftwareReserveById(idKey);
        List<CommonDocument> commonDocuments1 = new ArrayList<CommonDocument>();
        List<CommonDocument> commonDocuments2 = new ArrayList<CommonDocument>();
        mav.addObject("commonDocuments1", commonDocuments1);
        mav.addObject("commonDocuments2", commonDocuments2);
        mav.addObject("softwareReserve", software);
        // 查找审核记录
        List<SoftwareReserveAudit> softwareReserveAudits = operationService
                .findAllSoftwareReserveAuditBysoftwareReserveId(software
                        .getId());
        mav.addObject("softwareReserveAudits", softwareReserveAudits);
        // 查找可以预约的软件，暂时不做
        mav.addObject("softwareList", systemService.loadSoftwares());
        mav.addObject("termList", systemService.loadSchoolTerms());// 返回学期
        mav.addObject("labList", systemService.loadLabRooms());// 返回实验室
        mav.addObject("user", shareService.getUser());// 返回用户
        mav.addObject("courses", shareService.getMyCourse());// 获取用户的课程列表
        mav.addObject("id", idKey);
        // 审核阶段
        int labroomid = software.getLabRoom().getId();
        List<LabRoomAdmin> labRoomAdmins = labRoomAdminService
                .findAllLabRoomAdminsByLabRoomId(labroomid);
        for (LabRoomAdmin l : labRoomAdmins) {

        }
        mav.addObject("step", step);
        // flag1为查看，2为可审核
        mav.addObject("flag", flag);
        mav.setViewName("lab/lab_room/viewsoftwareReserve.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：软件申请列表-审核通过
     *
     * @param: 软件申请列表
     * @author:张愉
     * @date:2017-09-30
     ****************************************************************************/
    @RequestMapping("/SoftwareReserveAuditedList")
    public ModelAndView SoftwareReserveAuditedList(
            @ModelAttribute SoftwareReserve softwareReserve, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.softwareLendTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<SoftwareReserve> softwareReserves = labRoomService
                .findAllsoftwareLendAudit(softwareReserve, page, pageSize, 3);
        mav.addObject("softwareReserves", softwareReserves);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("/lab/software/softwareReserveAudited.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：软件申请列表-审核中
     *
     * @param: 软件申请列表
     * @author:张愉
     * @date:2017-09-30
     ****************************************************************************/
    @RequestMapping("/SoftwareReserveAuditingList")
    public ModelAndView SoftwareReserveAuditingList(
            @ModelAttribute SoftwareReserve softwareReserve, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.softwareLendTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<SoftwareReserve> softwareReserves = labRoomService
                .findAllsoftwareLendAudit(softwareReserve, page, pageSize, 1);
        mav.addObject("softwareReserves", softwareReserves);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("/lab/software/softwareReserveAuditing.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：软件申请列表-审核拒绝
     *
     * @param: 软件申请列表
     * @author:张愉
     * @date:2017-09-30
     ****************************************************************************/
    @RequestMapping("/SoftwareReserveAuditRefuseList")
    public ModelAndView SoftwareReserveAuditRefuseList(
            @ModelAttribute SoftwareReserve softwareReserve, Integer page,
            @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 查询出所有的设备设备预约记录
        int totalRecords = labRoomService.softwareLendTotals();
        int pageSize = 10;// 每页10条记录
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<SoftwareReserve> softwareReserves = labRoomService
                .findAllsoftwareLendAudit(softwareReserve, page, pageSize, 2);
        mav.addObject("softwareReserves", softwareReserves);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.setViewName("/lab/software/softwareReserveAuditRefuse.jsp");
        return mav;
    }

    /*****************************
     * Description 查询扣分标准
     *
     * @author:张愉
     * @date:2017.11.26
     *****************************/
    @RequestMapping("/getCreditOption")
    public ModelAndView getCreditOption() {
        ModelAndView mav = new ModelAndView();
        Set<CreditOption> creditOptions = creditOptionDAO.findAllCreditOptions();
        mav.addObject("creditOptions", creditOptions);
        mav.setViewName("labroom/lab_room_reservation/getCreditOption.jsp");
        return mav;
    }

    /****************************************************************************
     * 功能：根据实验室id查寻实验分室
     * 作者：李小龙
     * 时间：2014-07-29
     ****************************************************************************/
    @RequestMapping("/appointment/findLabRoomByLabAnnexId")
    public ModelAndView findLabRoomByLabAnnexId(@ModelAttribute LabRoom labRoom,@RequestParam Integer id,Integer page){
        //新建ModelAndView对象；
        ModelAndView mav=new ModelAndView();
        //System.out.println("session中的id="+cid);
        //查询表单的对象
        mav.addObject("labRoom", labRoom);
        int pageSize=10;//每页20条记录
        //查询出来的总记录条数
        int totalRecords=labAnnexService.findLabRoomByLabAnnexId(labRoom,id).size();
        //分页信息
        Map<String,Integer> pageModel =shareService.getPage(pageSize, page,totalRecords);
        //根据分页信息查询出来的记录
        List<LabRoom> listLabRoom=labAnnexService.findLabRoomByLabAnnexId(labRoom,id,page,pageSize);
        mav.addObject("listLabRoom",listLabRoom);
        mav.addObject("pageModel",pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        //实验室id
        mav.addObject("id", id);
        mav.setViewName("lab/lab_room/labRoomList.jsp");
        return mav;
    }

    /****************************************************************************
     * @Description:实验室管理--查看当前实验室下的阅读设备预约注意事项的人
     * @author: 贺子龙
     * @Date: 2017-04-23
     ****************************************************************************/
    @RequestMapping("/labRoom/viewLabRoomAttentionRecord")
    public ModelAndView viewLabRoomAttentionRecord(@RequestParam Integer labId, int page) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;// 每页20条记录
        // 查询出来的总记录条数
        int totalRecords = labRoomService.countLabRoomAttentionByLab(labId);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(pageSize, page, totalRecords);
        // 根据分页信息查询出来的记录
        List<LabRoomAttention> attentions = labRoomService.findLabRoomAttentionByLab(labId, page, pageSize);
        mav.addObject("attentions", attentions);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);

        mav.setViewName("lab/lab_room/viewLabRoomAttentionRecord.jsp");
        return mav;
    }

    /****************************************************************************
     * @Description:实验室管理--查看当前实验室下的阅读设备预约注意事项的人(设备预约界面)
     * @author: 贺子龙
     * @Date: 2017-04-23
     ****************************************************************************/
    @RequestMapping("/viewLabRoomAttentionRecordDevice")
    public ModelAndView viewLabRoomAttentionRecordDevice(@RequestParam String deviceNumber, int page) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;// 每页20条记录
        // 找到实验室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByDeviceNumber(deviceNumber);
        // 找到学校设备
        SchoolDevice schoolDevice = device.getSchoolDevice();
        // 定义空list
        List<LabRoomAttention> attentions = new flex.messaging.io.ArrayList();
        // 定义总记录数并初始化为0
        int totalRecords = 0;
        // 如果设备没有注意事项，则按照设备显示；否则，按实验室显示
        if (!EmptyUtil.isStringEmpty(device.getApplicationAttentions())) {
            // 查询出来的总记录条数
            totalRecords = labRoomService.countLabRoomAttentionByDevice(schoolDevice.getDeviceNumber());
            // 根据分页信息查询出来的记录
            attentions = labRoomService.findLabRoomAttentionByDevice(deviceNumber, page, pageSize);
        } else {
            // 找到实验室
            int labId = device.getLabRoom().getId();
            // 查询出来的总记录条数
            totalRecords = labRoomService.countLabRoomAttentionByLab(labId);
            // 根据分页信息查询出来的记录
            attentions = labRoomService.findLabRoomAttentionByLab(labId, page, pageSize);
        }
        mav.addObject("attentions", attentions);
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("deviceNumber", deviceNumber);

        mav.setViewName("lab/lab_room/viewLabRoomAttentionRecord.jsp");
        return mav;
    }

    /**
     * Description
     * @param name
     * @param number
     * @param deviceAddress
     * @param page
     * @param acno
     * @param roomId
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/findSchoolDeviceForLab")
    public @ResponseBody
    String findSchoolDeviceForLab(@RequestParam String name, String number, String deviceAddress, Integer page,
            @ModelAttribute("selected_academy") String acno, Integer roomId) {
        SchoolDevice schoolDevice = new SchoolDevice();
        schoolDevice.setDeviceName(name);
        schoolDevice.setDeviceNumber(number);
        schoolDevice.setDeviceAddress(deviceAddress);
        // 分页开始
        int totalRecords = schoolDeviceService.findSchoolDeviceForLabCount(schoolDevice, roomId);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        // 根据分页信息查询出来的记录
        List<SchoolDevice> deviceList = schoolDeviceService
                .findSchoolDeviceForLab(schoolDevice, roomId, page, pageSize);
        String s = "";
        for (SchoolDevice d : deviceList) {
            s += "<tr>" + "<td>" + d.getDeviceNumber() + "</td>" + "<td>"
                    + d.getDeviceName() + "</td>" + "<td>"
                    + d.getDevicePattern() + "</td>";
            if (d.getUserByKeepUser() != null
                    && d.getUserByKeepUser().getCname() != null) {
                s += "<td>" + d.getUserByKeepUser().getCname() + "</td>";
            } else {
                s += "<td></td>";
            }
            s += "<td>" + d.getDeviceFormat() + "</td>" + "<td>"
                    + d.getDevicePrice() + "</td>";
            if (d.getDeviceAddress() != null) {
                s += "<td>" + d.getDeviceAddress() + "</td>";
            } else {
                s += "<td></td>";
            }
            s += "<td><input type='checkbox' name='CK' value='"
                    + d.getDeviceNumber() + "'/></td>" + "</tr>";
        }
        s += "<tr><td colspan='7'>"
                + "<a href='javascript:void(0)' onclick='first(1);'>" + "首页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='previous("
                + page
                + ");'>"
                + "上一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='next("
                + page
                + ","
                + pageModel.get("totalPage")
                + ");'>"
                + "下一页"
                + "</a>&nbsp;"
                + "<a href='javascript:void(0)' onclick='last("
                + pageModel.get("totalPage")
                + ");'>"
                + "末页"
                + "</a>&nbsp;"
                + "当前第"
                + page
                + "页&nbsp; 共"
                + pageModel.get("totalPage")
                + "页  " + totalRecords + "条记录" + "</td></tr>";
        return htmlEncode(s);
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

    /**
     * Description 新版物联-电源控制器-开关电源
     * @param flag 1开，0关
     * @param agentId 硬件id
     * @return
     * @throws IOException
     * @author 陈乐为 2019-3-5
     */
    @ResponseBody
    @RequestMapping("/openAgentNew")
    public String openAgentNew(@RequestParam Integer flag, Integer agentId) throws IOException {
        return labRoomService.sendOpenAgentInterfaceByJWT(flag,agentId);
    }

    @RequestMapping(value = "/viewProjectors/{ctrl_ip}/{idKey}")
    public ModelAndView viewProjectors(@PathVariable String ctrl_ip, @PathVariable Integer idKey) {
        ModelAndView mav = new ModelAndView();
        // 实验室
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(idKey);
        mav.addObject("labRoom", labRoom);
        StringBuffer hql = new StringBuffer("select * from lab_projector c where 1=1");
        hql.append(" and c.ctrl_ip='"+ ctrl_ip +"'");
        Query queryList = entityManager.createNativeQuery(hql.toString());
        mav.addObject("queryHQLs", queryList.getResultList());

        mav.setViewName("lab/lab_room/viewProjectors.jsp");
        return mav;
    }
    /**************************************************************************************
     * description：导入物联和实验室管理员
     * @throws ParseException
     * @author：廖文辉
     * @date：2018-12-25
     **************************************************************************************/
    @RequestMapping("/importLabRoomAdmin")
    public @ResponseBody String[] importLabRoomAdmin(HttpServletRequest request,@RequestParam Integer roomId,@RequestParam int type,@RequestParam Integer typeId){
        //获取文件地址
        String fileName=labRoomService.adminUpload(request);
        //获取服务器地址
        String logoRealPathDir=request.getSession().getServletContext().getRealPath("/");
        //获取文件全部地址
        String filePath=logoRealPathDir+fileName;
        if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
            String[] result=labRoomService.importLabRoomAdmin(filePath,roomId,typeId);
            return result;
        }else{
            String[] result={};
            return result;
        }
    }
    /**************************************************************************************
     * description：导入仪器设备
     * @throws ParseException
     * @author：廖文辉
     * @date：2018-12-26
     **************************************************************************************/
    @RequestMapping("/importLabRoomDevice")
    public @ResponseBody String[] importLabRoomDevice(HttpServletRequest request,@RequestParam Integer roomId,@RequestParam int type)throws Exception{
        //获取文件地址
        String fileName=labRoomService.adminUpload(request);
        //获取服务器地址
        String logoRealPathDir=request.getSession().getServletContext().getRealPath("/");
        //获取文件全部地址
        String filePath=logoRealPathDir+fileName;
        if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
            String[] result=labRoomService.importLabRoomDevice(filePath,roomId);
            return result;
        }else{
            String[] result={};
            return result;
        }
    }
    /****************************************************************************
     * @功能：删除授权名单
     * @作者：廖文辉
     * @时间：2019-01-10
     ****************************************************************************/
    @RequestMapping("/deleteLabRoomAuthorizeUser")
    public ModelAndView deleteLabRoomAuthorizeUser(@RequestParam Integer id,@RequestParam int type) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        LabRoomAdmin labRoomAuthorizeUser=labRoomAdminDAO.findLabRoomAdminByPrimaryKey(id);
        labRoomAdminDAO.remove(labRoomAuthorizeUser);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id="
                + labRoomAuthorizeUser.getLabRoom().getId()+"&type="+type);
        return mav;
    }
    /****************************************************************************
     * @功能：保存授权名单
     * @作者：廖文辉
     * @时间：2019-01-10
     ****************************************************************************/
    @RequestMapping("/saveLabRoomAuthorized")
    public ModelAndView saveLabRoomAuthorized(@ModelAttribute LabRoomAdmin labRoomAdmin,@RequestParam Integer roomId,@RequestParam int type,HttpServletRequest request) throws ParseException{
        ModelAndView mav = new ModelAndView();
        // id对应的实验室
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
        if(request!=null){
            Calendar calendarStartDate=Calendar.getInstance();
            Calendar calendarEndDate=Calendar.getInstance();
            Calendar calendarStartTime=Calendar.getInstance();
            Calendar calendarEndTime=Calendar.getInstance();
            String startDate=request.getParameter("startDate");
            String endDate=request.getParameter("endDate");
            String startTime=request.getParameter("startTime");
            String endTime=request.getParameter("endTime");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2=new SimpleDateFormat("HH:mm");
            SimpleDateFormat sdf3=new SimpleDateFormat("HH:mm");
            try {
                Date startDateTime =sdf.parse(startDate);
                calendarStartDate.setTime(startDateTime);
                Date endDateTime=sdf1.parse(endDate);
                calendarEndDate.setTime(endDateTime);
                Date startTimeTime=sdf2.parse(startTime);
                calendarStartTime.setTime(startTimeTime);
                Date endTimeTime=sdf3.parse(endTime);
                calendarEndTime.setTime(endTimeTime);
                labRoomAdmin.setStartDate(calendarStartDate);
                labRoomAdmin.setEndDate(calendarEndDate);
                labRoomAdmin.setStartTime(calendarStartTime);
                labRoomAdmin.setEndTime(calendarEndTime);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        if(request.getParameter("username3")!=null){
            labRoomAdmin.setUser(userDAO.findUserByUsername(request.getParameter("username3")));
        }
        labRoomAdmin.setTypeId(3);
        labRoomAdmin.setLabRoom(labRoom);
        labRoomAdminDAO.store(labRoomAdmin);
        mav.setViewName("redirect:/labRoom/getLabRoom?currpage=1&id=" + roomId+"&type="+type);
        return mav;
    }
    /****************************************************************************
     * @功能：保存授权名单
     * @作者：廖文辉
     * @时间：2019-01-10
     ****************************************************************************/
    @RequestMapping("/updateLabRoomAuthorizeUser")
    public ModelAndView updateLabRoomAuthorizeUser(@RequestParam Integer id,@RequestParam int type) {
        ModelAndView mav = new ModelAndView();
        // id对应的实验室物联硬件
        LabRoomAdmin labRoomAdmin=labRoomAdminDAO.findLabRoomAdminByPrimaryKey(id);
        mav.addObject("labRoomAdmin",labRoomAdmin);
        mav.addObject("type",type);
        mav.addObject("id",id);
        User user = shareService.getUser();
        mav.addObject("user", user);
        String academyNumber="";
        if(user.getSchoolAcademy().getAcademyNumber()!=null){
            academyNumber=user.getSchoolAcademy().getAcademyNumber();
         }
//         List<User> userList=labRoomService.findUserByacno(academyNumber);
//        mav.addObject("userList",userList);
        mav.setViewName("lab/lab_room/updateLabRoomAuthorizeUser.jsp");

        return mav;
    }

    /**
     * 门禁进出列表页面
     * @param username 用户名
     * @param cname 用户姓名
     * @param startDate 起始门禁刷卡时间
     * @param endDate 结束门禁刷卡时间
     * @param labRoomName 实验室名称
     * @param request 请求
     * @return 页面
     * @author 黄保钱 2019-3-4
     */
    @RequestMapping("/listLabDoorRecord")
    public ModelAndView listLabDoorRecord(Integer currpage, String username, String cname, String startDate,
                                          String endDate, String labRoomName, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", username);
        mav.addObject("cname", cname);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("labRoomName", labRoomName);
        List<Object[]> listLabDoorRecords = labRoomService.getLabDoorRecords(username, cname, startDate, endDate, labRoomName);
        mav.addObject("listLabDoorRecords", listLabDoorRecords);
        // 分页
        int pageSize = 10;
        int totalRecords = labRoomService.getLabDoorRecordsNum(username, cname, startDate, endDate, labRoomName);
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.setViewName("/lab/lab_room/listLabDoorRecord.jsp");
        return mav;
    }

    /**
     * Description 查找本学院近五年的用户
     * @param acno
     * @return
     * @author 黄保钱 2019-3-15
     */
    @RequestMapping("/listUserForLabRoom")
    @ResponseBody
    public String listUserForLabRoom(@ModelAttribute("selected_academy") String acno){
        List<User> userList=labRoomService.findUserByacno(acno);
        String a="请选择";
        String s="<option  value='" + "'>" +a+ "</option>";
        for (User u : userList) {
            s+="<option  value='" +u.getUsername() + "'>" +u.getCname() + u.getUsername() + "</option>";
        }
        return shareService.htmlEncode(s);
    }

    /**
     * Description 保存批量设置实验室管理员/物联管理员
     * @param request
     * @return
     * @author 陈乐为 2019-4-3
     */
    @RequestMapping("/saveMultipleManager")
    @ResponseBody
    public String[] saveMultipleManager(HttpServletRequest request) {
        int type_code = Integer.valueOf(request.getParameter("type_code"));
        String[] labtwo = request.getParameterValues("labtwo[]");
        String[] peopletwo = request.getParameterValues("peopletwo[]");
        String str = labRoomService.saveMultipleManager(type_code, labtwo, peopletwo);
        String[] returnStr = {str};
        return returnStr;
    }

    /**
     * Description 工训调用接口{判断排课导入时的实验室是否存在}
     * @param lab_name
     * @return
     * @author 陈乐为 2019年4月24日
     */
    @RequestMapping("/existLabRoom")
    public @ResponseBody String existLabRoom(@RequestParam String lab_name) {
        LabRoom labRoom = labRoomService.findLabRoomByLabRoomName(lab_name);
        if(labRoom!=null) {
            return "success";
        }else {
            return "fail";
        }
    }
}
