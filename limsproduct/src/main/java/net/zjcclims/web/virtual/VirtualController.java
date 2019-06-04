package net.zjcclims.web.virtual;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.dao.VirtualImageReservationDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.virtual.VirtualService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.vo.CourseSchedule;
import net.zjcclims.vo.virtual.VirtualImageReservationVO;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("VirtualController")
@SessionAttributes({"select ed_academy", "isAudit"})
@RequestMapping("/virtual")
public class VirtualController<JsonResult> {

    @Autowired
    private ShareService shareService;
    @Autowired
    private VirtualService virtualService;
    @Autowired
    PConfig pConfig;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private VirtualImageReservationDAO virtualImageReservationDAO;

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register

        binder.registerCustomEditor(Calendar.class,
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
        binder.registerCustomEditor(Date.class,
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

    /*************************************************************************************
     * Description:虚拟实验室列表
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping("/listVirtualLabRoom")
    public ModelAndView listVirtualLabRoom(@ModelAttribute LabRoom labRoom,@RequestParam int currpage) {
        // 新建ModelAndView对象；
        ModelAndView mav = new ModelAndView();
        int pageSize = 10;
        // 所有虚拟实验室
        int totalRecords = virtualService.getAllVirtualLabRoomCount(labRoom);
        Map<String, Integer> pageModel = shareService.getPage(currpage,pageSize, totalRecords);
        List<LabRoom> virtualLabRoomList = virtualService.getAllVirtualLabRoom(labRoom, currpage, pageSize);
        mav.addObject("pageModel", pageModel);
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("virtualLabRoomList", virtualLabRoomList);
        mav.setViewName("virtual/listVirtualLabRoom.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:更新虚拟实验室信息
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping(value = "/updateVirtualLabRoom", produces = "application/json; charset=utf-8")
    public ModelAndView updateVirtualLabRoom() throws IOException {
        ModelAndView mav = new ModelAndView();
        virtualService.updateVirtualLabRoom();
        mav.setViewName("redirect:/virtual/listVirtualLabRoom?currpage=1");
        return mav;
    }

    /*************************************************************************************
     * Description:虚拟镜像列表
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping("/listVirtualImage")
    public ModelAndView listVirtualImage(@ModelAttribute VirtualImage virtualImage, @RequestParam int currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 10;
        //所有虚拟镜像
        int totalRecords = virtualService.getAllVirtualImageCount(virtualImage);
        Map<String, Integer> pageModel = shareService.getPage(currpage,pageSize, totalRecords);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(virtualImage, currpage, pageSize);
        mav.addObject("pageModel", pageModel);
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("virtualImageList", virtualImageList);
        mav.setViewName("virtual/listVirtualImage.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:更新虚拟镜像信息
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping(value = "/updateVirtualImage", produces = "application/json; charset=utf-8")
    public ModelAndView updateVirtualImage() throws IOException {
        ModelAndView mav = new ModelAndView();
        virtualService.updateVirtualImage();
        mav.setViewName("redirect:/virtual/listVirtualImage?currpage=1");
        return mav;
    }

    /*************************************************************************************
     * Description:查看某虚拟镜像的预约记录
     *
     * @author: 杨新蔚
     * @date: 2019/6/3
     *************************************************************************************/
    @RequestMapping(value = "/layerListVirtualImageReservation", produces = "application/json; charset=utf-8")
    public ModelAndView layerListVirtualImageReservation(@RequestParam String imageId,@RequestParam int currpage) throws IOException {
        ModelAndView mav = new ModelAndView();
        int pageSize = 10;
        List<VirtualImageReservationVO> virtualImageReservations=virtualService.getVirtualImageReservationByImageId(imageId,currpage,pageSize);
        Integer totalRecords = virtualService.getCountVirtualImageReservationByImageId(imageId);
        Map<String, Integer> pageModel = shareService.getPage(currpage,pageSize, totalRecords);
        mav.addObject("virtualImageReservations",virtualImageReservations);
        mav.addObject("imageId",imageId);
        //分页参数
        mav.addObject("pageModel", pageModel);
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.setViewName("virtual/layerListVirtualImageReservation.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:查看虚拟实验室下的镜像
     *
     * @author: 贺照易
     * @date: 2018/12/22
     *************************************************************************************/
    @RequestMapping(value = "/getLabRoomSoft", produces = "application/json; charset=utf-8")
    public ModelAndView getLabRoomSoft(@RequestParam String labNum) throws IOException {
        ModelAndView mav = new ModelAndView();
        int currpage = 1;
        int pageSize = 10;
        // 设置镜像的总记录数并赋值
        int totalRecords = virtualService.getLabRoomAllVirtualImageCount(labNum);
        Map<String, Integer> pageModel = shareService.getPage(currpage,pageSize, totalRecords);
        //获取虚拟实验室下的镜像
        List<Map> LabRoomVirtualImageList=virtualService.getLabRoomVirtualImage(labNum);
        mav.addObject("LabRoomVirtualImageList",LabRoomVirtualImageList);
        //获取所有虚拟镜像
        List<Map> virtualImageList = virtualService.getAlladdVirtualImage();
        mav.addObject("virtualImageList", virtualImageList);
        //虚拟实验室id
        mav.addObject("labNum",labNum);
        //分页参数
        mav.addObject("pageModel", pageModel);
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.setViewName("virtual/listVirtualLabRoomImageDetail.jsp");
        return mav;
    }


    /*************************************************************************************
     * Description:添加虚拟镜像至实验室
     *
     * @author: 贺照易
     * @date: 2018/12/20
     *************************************************************************************/
    @RequestMapping(value = "/addVirtualImage", produces = "application/json; charset=utf-8")
    public String addVirtualImage(HttpServletRequest request) throws ParseException {
        String roomNum = request.getParameter("LabRoomNumber");
        String ImageId = request.getParameter("VirtualImageId");
        virtualService.addVirtualImage(roomNum,ImageId);
        return "redirect:/virtual/listVirtualLabRoom?currpage=1";
    }
    /****************************************************************************
     * Description:删除虚拟镜像
     *
     * @author: 贺照易
     * @date: 2018/12/21
     ****************************************************************************/
    @RequestMapping(value = "/deleteVirtualImage", produces = "application/json; charset=utf-8")
    public String deleteVirtualImage(@RequestParam String roomNum ,@RequestParam String ImageId)throws ParseException {
        virtualService.deleteVirtualImage(roomNum,ImageId);
        return "redirect:/virtual/listVirtualLabRoom?currpage=1";

    }

    /*************************************************************************************
     * Description:镜像预约模块
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping("/virtualImageReservation")
    public ModelAndView virtualImageReservation(@ModelAttribute VirtualImageReservation virtualImageReservation, @RequestParam int currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 10;
        // 获取可预约虚拟镜像(可以不关联的实验室)
        int totalRecords = virtualService.getAllVirtualImageCount(null);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null,1,-1);
        Map<String, Integer> pageModel = shareService.getPage(currpage,pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("VirtualImages", virtualImageList);
        mav.setViewName("/virtual/virtualImageAppointment.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:镜像预约直连模块
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping("/virtualImageReservationCitrix")
    public ModelAndView virtualImageReservationCitrix(@ModelAttribute VirtualImageReservation virtualImageReservation, @RequestParam int currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 10;
        // 获取可预约虚拟镜像(可以不关联的实验室)
        int totalRecords = virtualService.getAllVirtualImageCount(null);
        List<VirtualImage> virtualImageList = virtualService.getAllVirtualImage(null,1,-1);
        Map<String, Integer> pageModel = shareService.getPage(currpage,pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("VirtualImages", virtualImageList);
        mav.setViewName("/virtual/virtualImageAppointmentCitrix.jsp");
        return mav;
    }

    /*************************************************************************************
     * Description:更新镜像
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value="/updateImageCitrix",produces = "application/json; charset=utf-8")
    public String updateImageCitrix(HttpServletRequest request) throws ParseException {
        return virtualService.updateImageCitrix(request);
    }

    /*************************************************************************************
     * Description:镜像预约检查
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value="/checkImage",produces = "application/json; charset=utf-8")
    public String checkImage(HttpServletRequest request) throws ParseException {
        return virtualService.checkImage(request);
    }

    /*************************************************************************************
     * Description:镜像预约检查(直连)
     *
     * @author: 杨新蔚
     * @date: 2019/06/03
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value="/checkImageCitrix",produces = "application/json; charset=utf-8")
    public String checkImageCitrix(HttpServletRequest request) throws ParseException {
        return virtualService.checkImageCitrix(request);
    }


    /****************************************************************************
     * Description：镜像预约申请列表
     *
     * @author: 杨新蔚
     * @date: 2019/1/6
     ****************************************************************************/
    @RequestMapping("/virtualImageReservationList")
    public ModelAndView labReserveList(@ModelAttribute VirtualImageReservation virtualImageReservation, Integer page, Integer isaudit, Integer tage, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        int pageSize = 15;
        List<VirtualImageReservationVO> virtualImageReservations = virtualService.findAllVirtualImageReservation(virtualImageReservation, page, pageSize, tage, isaudit);
        int totalRecords = virtualService.findAllVirtualImageReservation(virtualImageReservation, 0, 0, tage, isaudit).size();
        List<Integer> auditState = new ArrayList<>();
        List<String> auditShow = new ArrayList<>();
        //判断所处审核阶段，关联到前端的按钮
        if (virtualImageReservations != null) {
            for (int i = 0; i < virtualImageReservations.size(); i++) {
                if(virtualImageReservations.get(i).getAuditStage()!=null&&6==(virtualImageReservations.get(i).getAuditStage())) {
                    auditState.add(-1);
                }else{
                    auditState.add(-2);
                }




               /* // 获取当前审核状态
                Map<String, String> params2 = new HashMap<>();
                String businessType = "VirtualImageReservation";
                params2.put("businessType", pConfig.PROJECT_NAME + businessType);
                params2.put("businessAppUid", virtualImageReservations.get(i).getId().toString());
                String s2 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params2);
                com.alibaba.fastjson.JSONObject jsonObject2 = JSON.parseObject(s2);
                String status2 = jsonObject2.getString("status");
                Integer auditNumber = null;
                if("success".equals(status2)){
                    com.alibaba.fastjson.JSONArray jsonArray = jsonObject2.getJSONArray("data");
                    if(jsonArray != null) {
                        com.alibaba.fastjson.JSONObject jsonObject3 = jsonArray.getJSONObject(0);
                        auditNumber = virtualService.getAuditNumber(virtualImageReservations.get(i).getVirtualImage(), jsonObject3.getIntValue("level"));
                        auditState.add(jsonObject3.getIntValue("level"));
                    }
                }else{
                    auditState.add(-2);
                }
                // 获取所有审核状态
                Map<String, String> allAuditStateParams = new HashMap<>();
                allAuditStateParams.put("businessType", pConfig.PROJECT_NAME + businessType);
                allAuditStateParams.put("businessAppUid", virtualImageReservations.get(i).getId().toString());
                allAuditStateParams.put("businessUid", "-1");
                String allAuditStateStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allAuditStateParams);
                com.alibaba.fastjson.JSONObject allAuditStateJSON = com.alibaba.fastjson.JSONObject.parseObject(allAuditStateStr);
                String htmlStr = "";
                if(!"fail".equals(status2)) {
                    com.alibaba.fastjson.JSONArray allAuditStateJSONArray = allAuditStateJSON.getJSONArray("data");
                    if (allAuditStateJSONArray != null && allAuditStateJSONArray.size() != 0) {
                        for (int j = 0; j < allAuditStateJSONArray.size(); j++) {
                            com.alibaba.fastjson.JSONObject o = allAuditStateJSONArray.getJSONObject(j);
                            User auditUser = null;
                            if(o.getString("auditUser") != null){
                                htmlStr += "<span style='color: black";
                                auditUser = userDAO.findUserByUsername(o.getString("auditUser"));
                            }else {
                                htmlStr += "<span style='color: gray";
                            }
                            htmlStr += "'>";
                            String authCName = authorityDAO.findAuthorityByAuthorityName(o.getString("authName")).iterator().next().getCname();
                            htmlStr += authCName + " ";
                            htmlStr += auditUser == null ? "" : auditUser.getCname() + " ";
                            htmlStr += o.getString("result");
                            htmlStr += "</span><br>";
                        }
                    }
                }
                auditShow.add(htmlStr);
                mav.addObject("auditShow", auditShow);

                //先初始化为0
                virtualImageReservations.get(i).setButtonMark(0);
                if (auditNumber != null) {
                    //教师审核阶段
                    if (auditNumber == 1) {
                        //当前登陆人是审核实训部主任
                        User teacher = virtualImageReservations.get(i).getTeacher();
                        if (teacher != null) {
                            if (user.getUsername().equals(teacher.getUsername())) {
                                virtualImageReservations.get(i).setButtonMark(1);
                            }
                        }
                    }
                    //系主任审核阶段
                    if (auditNumber == 2) {
                        //当前登陆人是审核系主任
                        List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
                        if (deans != null) {
                            for (User user2 : deans) {
                                if (user2.getUsername().equals(user.getUsername())) {
                                    virtualImageReservations.get(i).setButtonMark(2);
                                    break;
                                }
                            }
                        }
                    }
                    //实训室管理员审核阶段
                    if (auditNumber == 3) {
                        //当前登陆人是审核实训室管理员
                        if (labRoomDeviceService.getLabRoomAdmin(virtualImageReservations.get(i).getVirtualImage().getLabRoom().getId(), user.getUsername()) == true) {
                            virtualImageReservations.get(i).setButtonMark(3);
                        }
                    }
                    //实验中心主任审核阶段
                    if (auditNumber == 4) {
                        User labRoomCenterDirector = virtualImageReservations.get(i).getVirtualImage().getLabRoom().getLabCenter().getUserByCenterManager();
                        if (labRoomCenterDirector != null) {
                            if (user.getUsername().equals(labRoomCenterDirector.getUsername())) {
                                virtualImageReservations.get(i).setButtonMark(4);
                            }
                        }
                    }
                    //实训部教学秘书审核阶段
                    if (auditNumber == 5) {
                        //当前登陆人是审核实训部主任
                        List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityName("PREEXTEACHING");
                        if (labRoomCenterDirectors != null) {
                            for (User user2 : labRoomCenterDirectors) {
                                if (user.getUsername().equals(user2.getUsername())) {
                                    virtualImageReservations.get(i).setButtonMark(5);
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    virtualImageReservations.get(i).setButtonMark(0);
                }*/

            }
//            totalRecords = labReservations.size();
        }
        // 分页信息
        Map<String, Integer> pageModel = shareService.getPage(page, pageSize,
                totalRecords);
        mav.addObject("virtualImageReservations", virtualImageReservations);
        mav.addObject("auditState", auditState);
        mav.addObject("pageModel", pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("page", page);
        mav.addObject("pageSize", pageSize);
        mav.addObject("isAudit", isaudit);
        mav.addObject("tage", tage);
        mav.addObject("jobReservation", pConfig.jobReservation);
        mav.addObject("user", user);
        mav.setViewName("/virtual/virtualImageReservationList.jsp");
        return mav;
    }


    /****************************************************************************
     * Description：实验室借用申请-申请审核/查看
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping("/checkButton")
    public ModelAndView checkButtonforlabRoomLend(@RequestParam int id, int tage, int state, Integer page, HttpServletRequest request,
                                                  @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        VirtualImageReservation virtualImageReservation = virtualImageReservationDAO.findVirtualImageReservationByPrimaryKey(id);
        VirtualImage virtualImage = virtualService.getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId());
//        state = labRoomService.getAuditNumber(labRoom, state);
        mav.addObject("state", state);
        mav.addObject("virtualImageReservation", virtualImageReservation);
        mav.addObject("virtualImage", virtualImage);
        mav.addObject("tage", tage);
        mav.addObject("page", page);
        //demo
        String[] RSWITCH = {"on", "off"};
        List<String> titles = new ArrayList<>();
        List<Integer> isOpens = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        String businessType = "VirtualImageReservation";
        params.put("businessUid", "-1");
        params.put("businessType", pConfig.PROJECT_NAME + businessType);
        String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
        String status = jsonObject.getString("status");
        if("success".equals(status)) {
            Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
            if (auditConfigs != null && auditConfigs.size() != 0) {
                for (int i = 0; i < auditConfigs.size(); i++) {
                    String[] auditSwitch = ((String) auditConfigs.get(i+1)).split(":");
                    titles.add(authorityDAO.findAuthorityByAuthorityName(auditSwitch[0]).iterator().next().getCname() + "审核");
                    isOpens.add(
                            (auditSwitch[1].equals(RSWITCH[0])
                                    && (!auditSwitch[0].equals("TEACHER") || virtualImageReservation.getTeacher() != null))
                                    ? 1 : 2);
                }
            }
        }
        mav.addObject("titles", titles);
        mav.addObject("isOpens", isOpens);
        mav.setViewName("/virtual/viewVirtualImageAudit.jsp");
        return mav;
    }

    /****************************************************************************
     * Description：实验室借用申请-实验室管理员审核
     *
     * @author:张愉
     * @date:2017-11-24
     ****************************************************************************/
    @RequestMapping(value="/virtualImageReservationAllAudit",produces = "application/json; charset=utf-8")
    public ModelAndView virtualImageReservationAllAudit(@RequestParam int id, int tage,int state,Integer page, HttpServletRequest request,
                                               @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        VirtualImageReservation virtualImageReservation =virtualImageReservationDAO.findVirtualImageReservationByPrimaryKey(id);
        User user = shareService.getUserDetail();
        List<User> isAuditUsers = new ArrayList<>();
        com.alibaba.fastjson.JSONObject resultObject = new com.alibaba.fastjson.JSONObject();
        Map<String, String> allParams = new HashMap<>();
        String businessType = "VirtualImageReservation";
        allParams.put("businessType", pConfig.PROJECT_NAME + businessType);
        allParams.put("businessAppUid", virtualImageReservation.getId().toString());
        allParams.put("businessUid", "-1");
        String allString = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessLevelStatus", allParams);
        com.alibaba.fastjson.JSONObject allObject = JSON.parseObject(allString);
        String allStatus = allObject.getString("status");
        com.alibaba.fastjson.JSONArray allJSONArray = allObject.getJSONArray("data");
        if (!allStatus.equals("fail")) {
            for (int i = 0; i < allJSONArray.size(); i++) {
                com.alibaba.fastjson.JSONObject o = allJSONArray.getJSONObject(i);
                if(o.getIntValue("level") == state){
                    resultObject = o;
                    break;
                }
            }
        }
        Map<String, String> curParams = new HashMap<>();
        curParams.put("businessType", pConfig.PROJECT_NAME + businessType);
        curParams.put("businessAppUid", virtualImageReservation.getId().toString());
        String curString = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", curParams);
        com.alibaba.fastjson.JSONObject curObject = com.alibaba.fastjson.JSONObject.parseObject(curString);
        String curPassStatus = curObject.getString("status");
        com.alibaba.fastjson.JSONArray curJSONArray = curObject.getJSONArray("data");
        com.alibaba.fastjson.JSONObject curJSONObject = curJSONArray.getJSONObject(0);
        Integer curStage = curJSONObject.getIntValue("level");
        String authName = curStage >= 1 ? curJSONObject.getString("result") : "";
        String allAuthName = state >= 1 ? resultObject.getString("authName") : "";
        // 中心主任--临时
        User user1 = shareService.findUsersByQuery("EXCENTERDIRECTOR", acno).get(0);
        switch (allAuthName){
            case "TEACHER":
                isAuditUsers.add(virtualImageReservation.getTeacher());
                break;
            case "CFO":
                isAuditUsers.addAll(shareService.findUsersByQuery("CFO",virtualImageReservation.getUser().getSchoolAcademy().getAcademyNumber()));
                break;
            case "LABMANAGER":
                isAuditUsers.addAll(labRoomDeviceService.findAdminByLrid(virtualService.getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId()).getLabRoom().getId()));
                break;
            case "EXCENTERDIRECTOR":
                isAuditUsers.add(user1);
                break;
            default:
                isAuditUsers.addAll(shareService.findUsersByAuthorityName(allAuthName));
        }
        //是否为审核人
        int isAudit = 0;
        if(state == curStage){//在此审核人审核阶段
            if (("ROLE_" + authName).equals(request.getSession().getAttribute("selected_role"))
                    && ((!"TEACHER".equals(authName) || virtualImageReservation.getTeacher().getUsername().equals(user.getUsername()))
                    && (!"LABMANAGER".equals(authName) || labRoomDeviceService.getLabRoomAdmin(virtualService.getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId()).getLabRoom().getId(), user.getUsername()))
                    && (!"CFO".equals(authName) || user.getSchoolAcademy().getAcademyNumber().equals(virtualImageReservation.getUser().getSchoolAcademy().getAcademyNumber()))
                    && (!"EXCENTERDIRECTOR".equals(authName) || user1.getUsername().equals(user.getUsername()))
            )) {//是审核人
                isAudit = 1;
            }else{
                mav.addObject("isAuditUser", isAuditUsers);
            }
        }else{//非此审核人审核阶段
            if(state > curStage && curStage >= 1){//未到此审核人审核阶段
                mav.addObject("isAuditUser", isAuditUsers);
            }else {//此审核人已审核完成阶段
                User auditUser = userDAO.findUserByUsername(resultObject.getString("auditUser"));
                mav.addObject("user", auditUser);
                mav.addObject("result", resultObject.getString("result"));
                mav.addObject("mem", resultObject.getString("info"));
                String createTimeStr = resultObject.getString("createTime");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Calendar createTime = Calendar.getInstance();
                try {
                    createTime.setTime(sdf.parse(createTimeStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mav.addObject("createDate", createTime);
            }
        }
        mav.addObject("isAudit", isAudit);
        mav.addObject("state", state);
        mav.addObject("curStage", curStage);
        mav.addObject("tage", tage);
        mav.addObject("id", id);
        mav.addObject("page", page);
        mav.setViewName("/virtual/virtualImageAllAudit.jsp");
        return mav;
    }


    @Transactional
    @RequestMapping(value="/saveLabCenterManagerAuditforlabRoomlend",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveLabCenterManagerAuditforlabRoomlend(@RequestParam int id, int tage, int state, Integer page, String auditResult,HttpServletRequest request,
                                                          @ModelAttribute("selected_academy") String acno) throws NoSuchAlgorithmException {
        VirtualImageReservation virtualImageReservation = virtualImageReservationDAO.findVirtualImageReservationByPrimaryKey(id);
//        LabReservationAudit labReservationAudit = new LabReservationAudit();
//        labReservationAudit.setResult(auditResult);
            Integer auditResult1 = Integer.parseInt(auditResult);
            User user = shareService.getUserDetail();
            String businessType = "VirtualImageReservation";
            Map<String, String> params2 = new HashMap<>();
            params2.put("businessType", pConfig.PROJECT_NAME + businessType);
            params2.put("businessAppUid", virtualImageReservation.getId().toString());
            String s2 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params2);
            com.alibaba.fastjson.JSONObject jsonObject2 = com.alibaba.fastjson.JSONObject.parseObject(s2);
            if (!"success".equals(jsonObject2.getString("status"))) {
                return null;
            }
            state = 0;
            String nextAuthName = "";
            String currAuthName = "";
            if (jsonObject2.getJSONArray("data") != null && jsonObject2.getJSONArray("data").size() != 0) {
                state = jsonObject2.getJSONArray("data").getJSONObject(0).getIntValue("level");
                currAuthName = jsonObject2.getJSONArray("data").getJSONObject(0).getString("result");
            }

            // 实际审核状态
            Integer auditNumber = virtualService.getAuditNumber(virtualService.getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId()), state);

            Map<String, String> params = new HashMap<>();
            params.put("businessType", pConfig.PROJECT_NAME + businessType);
            params.put("businessAppUid", virtualImageReservation.getId().toString());
            params.put("businessUid", "-1");
            params.put("result", auditResult1 == 1 ? "pass" : "fail");
            params.put("info", request.getParameter("remark"));
            params.put("username", user.getUsername());
            String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveBusinessLevelAudit", params);
            com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
            String status = jsonObject.getString("status");
            Map<String, String> params4 = new HashMap<>();
            params4.put("businessType", pConfig.PROJECT_NAME + businessType);
            params4.put("businessAppUid", virtualImageReservation.getId().toString());
            String s4 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", params4);
            com.alibaba.fastjson.JSONObject jsonObject5 = JSON.parseObject(s4);
            com.alibaba.fastjson.JSONArray jsonArrayCurStage = jsonObject5.getJSONArray("data");
            if(jsonArrayCurStage.size() != 0){
                com.alibaba.fastjson.JSONObject jsonObjectCurStage0 = jsonArrayCurStage.getJSONObject(0);
                Integer level = jsonObjectCurStage0.getIntValue("level");
                nextAuthName = jsonObjectCurStage0.getString("result");
            }
            boolean[] auditArray = new boolean[5];

            //demo
            String[] RSWITCH = {"on", "off"};
            Map<String, String> params1 = new HashMap<>();
            params1.put("businessUid", "-1");
            params1.put("businessType", pConfig.PROJECT_NAME + businessType);
            String s1 = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params1);
            com.alibaba.fastjson.JSONObject jsonObject1 = JSON.parseObject(s1);
            String status1 = jsonObject1.getString("status");
            Map auditConfigs = JSON.parseObject(jsonObject1.getString("data"), Map.class);
            if (auditConfigs != null && auditConfigs.size() != 0) {
                for (int i = 0; i < auditConfigs.size(); i++) {
                    String[] text = ((String) auditConfigs.get(i + 1)).split(":");
                    auditArray[i] = text[1].equals(RSWITCH[0]);
                }
            }

            //消息
            Message message = new Message();
            message.setSendUser(user.getCname());
            message.setSendCparty(user.getSchoolAcademy().getAcademyName());
            message.setCond(0);
            message.setTitle("虚拟镜像预约增加");
            /*String content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>审核</a>";
            message.setContent(content);*/
            message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
            message.setCreateTime(Calendar.getInstance());
            message.setTage(2);
            //第一级审核人
            switch (nextAuthName) {
                case "TEACHER":
                    User teacher = virtualImageReservation.getTeacher();
                   /* sendMsg(teacher, message);*/
                    break;
                case "CFO":
                    List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
                    for (User user2 : deans) {
                       /* sendMsg(user2, message);*/
                    }
                    break;
              /*  case "LABMANAGER":
                    List<LabRoomAdmin> labRoomAdmins = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labReservation.getLabRoom().getId());
                    for (LabRoomAdmin labRoomAdmin : labRoomAdmins) {
                        User user2 = labRoomAdmin.getUser();
                        sendMsg(user2, message);
                    }
                    break;*/
                /*case "EXCENTERDIRECTOR":
                    sendMsg(labRoom.getLabCenter().getUserByCenterManager(), message);
                    break;
                case "PREEXTEACHING":
                    List<User> labRoomMasters = shareService.findUsersByAuthorityName("PREEXTEACHING");
                    for (User user2 : labRoomMasters) {
                        sendMsg(user2, message);
                    }
                    break;*/
                case "pass":
                    virtualImageReservation.setAuditStage(6);
                    //加入调用接口方法
                    try {
                        //调用预约接口
                        String url = "http://10.2.39.41/v1/courseDesk";
                        Map<String, String> map = new HashMap();
                        map.put("num", "1");
                        map.put("soft_id", virtualService.getVirtualImageByVirtualImageReservationID(virtualImageReservation.getId()).getId());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar start= virtualImageReservation.getStartTime();
                        start.add(Calendar.MINUTE, -5);
                        String startTime = sdf.format(start.getTime());
                        String endTime = sdf.format(virtualImageReservation.getEndTime().getTime());
                        map.put("start_time", startTime);
                        map.put("end_time", endTime);
                        String realUrl = virtualService.getUrl(url);
                        String json = virtualService.post(realUrl, map);
                        System.out.println(json);
                        JSONObject jsonObjects = JSONObject.fromObject(json);
                        String code = jsonObjects.getString("code");
                        String msg = jsonObjects.getString("msg");
                        String data = jsonObjects.getString("data");
                        if ("课表桌面预约成功".equals(msg)) {
                            JSONArray jsonArrays = JSONArray.fromObject(data);
                            for (int k = 0; k < jsonArrays.size(); k++) {
                                JSONObject jsonObject9 = jsonArrays.getJSONObject(k);
                                String uid = jsonObject9.getString("id");
                                if (!"TODO".equals(uid)) {
                                    virtualImageReservation.setMachineId(Integer.parseInt(uid));
                                }
                            }
                        }
                    } catch (Exception e) {
                    } finally {
                    }
                    break;
                case "fail":
                    virtualImageReservation.setAuditStage(0);
                    break;
                default:
                    List<User> auditUsers = shareService.findUsersByAuthorityName(nextAuthName);
                    /*for (User user2: auditUsers){
                        sendMsg(user2, message);
                    }*/
            }
            String cName = authorityDAO.findAuthorityByAuthorityName(currAuthName).iterator().next().getCname();
           /* message.setTitle("实验室预约"+cName+user.getCname()+(auditResult1 == 1 ? "审核通过" : "审核拒绝"));
            content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
            message.setContent(content);
            message.setTage(1);
            message.setUsername(labReservation.getUser().getUsername());
            messageDAO.store(message);
            messageDAO.flush();*/
            virtualImageReservationDAO.store(virtualImageReservation);
            virtualImageReservationDAO.flush();
        return "success";
    }

    /*************************************************************************************
     * Description:镜像预约保存
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value="/saveVirtualImageReservation",produces = "application/json; charset=utf-8")
    public String saveVirtualImageReservation(HttpServletRequest request) throws ParseException {
        return virtualService.saveVirtualImageReservation(request);
    }

    /*************************************************************************************
     * Description:镜像预约保存(直连Citrix)
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value="/saveVirtualImageReservationCitrix",produces = "application/json; charset=utf-8")
    public String saveVirtualImageReservationCitrix(HttpServletRequest request) throws ParseException {
        return virtualService.saveVirtualImageReservationCitrix(request);
    }


    /*************************************************************************************
     * Description:citrix登陆
     *
     * @author: 杨新蔚
     * @date: 2018/12/17
     *************************************************************************************/
    @RequestMapping(value="/virtualLogin",produces = "application/json; charset=utf-8")
    public void virtualLogin(Integer virtualImageReservationid,HttpServletRequest request,HttpServletResponse response) {
        virtualService.virtualLogin(virtualImageReservationid,request,response);
    }

    /*************************************************************************************
     * Description:citrix登陆(直连)
     *
     * @author: 杨新蔚
     * @date: 2019/05/28
     *************************************************************************************/
    @RequestMapping(value="/virtualLoginCitrix",produces = "application/json; charset=utf-8")
    public void virtualLoginCitrix(Integer virtualImageReservationid,HttpServletRequest request,HttpServletResponse response) {
        virtualService.virtualLoginCitrix(virtualImageReservationid,request,response);
    }



    /*************************************************************************************
     * Description: 学生登陆前查询没有被使用的虚拟桌面ID，用该ID登陆
     *
     * @param username          学生id
     * @param course_id         课程ID
     * @param virtual_image_id  虚拟镜像ID
     * @param start_time        课程开始时间
     * @param end_time          课程结束时间
     * @return 返回"-1"表示虚拟桌面用尽
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value="/virtualCheckAndLogin",produces = "application/json; charset=utf-8")
    public String virtualCheckAndLogin(@RequestParam String username, @RequestParam String course_id, @RequestParam String virtual_image_id, @RequestParam String start_time, @RequestParam String end_time) {
        String virtualImageReservationID = virtualService.getNotUsedDesktopByID(username, course_id, virtual_image_id, start_time, end_time);
        return virtualImageReservationID;
    }

    /*************************************************************************************
     * Description: 回调地址
     *
     * @author 陈敬2019年3月13日
     *************************************************************************************/
    @ResponseBody
    @RequestMapping(value = "/reserveVirtualImageCallback", produces = "application/json; charset=utf-8")
    public void reserveVirtualImageCallback(HttpServletRequest request) throws IOException, ParseException {
        StringBuilder jsonString = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        System.out.println(jsonString);
        JSONObject jsonObject=JSONObject.fromObject(jsonString.toString());
        String courseID=jsonObject.get("course").toString();
        String virtualImageID=jsonObject.get("soft_id").toString();
        //开始时间延时10分钟
        String courseStartTime=jsonObject.get("create_time").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = sdf.parse(courseStartTime);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.add(Calendar.MINUTE, 10);
        String startTenTime = sdf.format(startCalendar.getTime());

        String courseEndTime=jsonObject.get("del_time").toString();
        JSONArray jac = jsonObject.getJSONArray("basic_id");
        String[] desktopIds=new String[jac.size()];
        for(int i=0;i<jac.size();i++){
            desktopIds[i]=jac.get(i).toString();
        }
        virtualService.saveDesktopIDs(courseID, virtualImageID, desktopIds, startTenTime, courseEndTime);
    }

    /**************************************************************************
     * 手动推数据，需要把@PostConstruct注释掉
     **************************************************************************/
    @RequestMapping(value = "/StartVirtualImageByCourseSchedules", method = RequestMethod.GET)
    @ResponseBody
    public String startVirtualImageByCourseSchedules() {
        try {
            virtualService.autoStartVirtualImageByCourseSchedules();
        }catch (Exception e){
            return "false";
        }
        return "success";
    }
}
