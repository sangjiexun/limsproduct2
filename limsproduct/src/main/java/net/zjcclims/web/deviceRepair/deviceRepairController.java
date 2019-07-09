package net.zjcclims.web.deviceRepair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.gvsun.lims.service.DeviceRepair.DeviceRepairService;
import net.gvsun.lims.service.auditServer.AuditService;
import net.zjcclims.constant.WordHandler;
import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.DeviceRepairDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.SchoolDeviceDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("deviceRepairController")
@SessionAttributes("selected_academy")
@RequestMapping("/deviceRepair")
public class deviceRepairController {

    @Autowired
    private DeviceRepairService deviceRepairService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private AuthorityDAO authorityDAO;

    @Autowired
    private LabRoomDAO labRoomDAO;

    @Autowired
    private ShareService shareService;

    @Autowired
    private DeviceRepairDAO deviceRepairDAO;
    @Autowired
    private SchoolDeviceDAO schoolDeviceDAO;
    @InitBinder
    public void initBinder(WebDataBinder binder) { // Register static property editors.
        binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
    }

    /**
     * Description 显示所有正常运行的设备
     *
     * @param acno 学院编号
     * @return 页面
     * @author 黄保钱 2018-9-30
     */
    @RequestMapping("/allStandardDeviceList")
    public ModelAndView allStandardDeviceList(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        // 实验室
        List<LabRoom> labRooms = deviceRepairService.getLabRoomByAcno(acno);
        mav.addObject("labRooms", labRooms);
        // 报修人列表
        List<User> users = deviceRepairService.findUsersByAuthorityNameAndAcademy("TEACHER", acno);
        mav.addObject("users", users);
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/allStandardLabRoomDeviceList.jsp");
        return mav;
    }

    /**
     * Description 保存设备维修申请
     *
     * @param request 请求
     * @return 重定向页面至我的设备维修申请列表
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/saveDeviceRepairApply")
    public String saveDeviceRepairApply(HttpServletRequest request) {
        // 获取页面传来的信息
        String labRoomName = request.getParameter("batchLabRoomEles");
        String deviceName = request.getParameter("batchDeviceEles");
        String memo = request.getParameter("content");
        Integer id = Objects.nonNull(request.getParameter("id")) ? Integer.valueOf(request.getParameter("id")) : null;
        String reportUsername = request.getParameter("reportUser");
        BigDecimal expectAmount = new BigDecimal(request.getParameter("expectAmount"));
        // 保存信息
        deviceRepairService.saveDeviceRepairApply(labRoomName, deviceName, memo, id, reportUsername, expectAmount);
        return "redirect:/deviceRepair/viewMyDeviceRepairApply";
    }

    /**
     * Description 查看我的设备维修申请
     *
     * @return 我的设备维修申请页面
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/viewMyDeviceRepairApply")
    public ModelAndView viewMyDeviceRepairApply() {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        mav.addObject("auditStage", 0);
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/myDeviceRepairApplyList.jsp");
        return mav;
    }

    /**
     * Description 提交设备维修申请单
     *
     * @param id 申请单id
     * @return 提交成功与否字符串
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/submitDeviceRepair")
    public @ResponseBody
    String submitDeviceRepair(@RequestParam Integer id, @ModelAttribute("selected_academy") String acno) {
        return deviceRepairService.submitDeviceRepair(id, acno);
    }

    /**
     * Description 待审核设备维修列表
     *
     * @return 查看列表页面
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/deviceRepairCheckList")
    public ModelAndView deviceRepairCheckList() {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        mav.addObject("auditStage", 1);
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/deviceRepairCheckList.jsp");
        return mav;
    }

    /**
     * Description 通过设备维修申请列表
     *
     * @return 查看列表页面
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/passDeviceRepairList")
    public ModelAndView passDeviceRepairList() {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        mav.addObject("auditStage", 2);
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/deviceRepairCheckList.jsp");
        return mav;
    }

    /**
     * Description 拒绝设备维修申请列表
     *
     * @return 查看列表页面
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/rejectedDeviceRepairList")
    public ModelAndView rejectedDeviceRepairList() {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        mav.addObject("auditStage", 3);
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/deviceRepairCheckList.jsp");
        return mav;
    }

    /**
     * Description 查看设备维修申请单
     *
     * @param id 申请单id
     * @return 查看页面
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/viewDeviceRepairApply")
    public ModelAndView viewDeviceRepairApply(@RequestParam Integer id, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 获取设备维修申请信息
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        mav.addObject("deviceRepair", deviceRepair);
        // 获取设备维修申请审核状态
        User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
        String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
        businessType = deviceRepairService.getBusinessLevels(businessType);
        try {
            String s = auditService.getBusinessLevel(
                    "-1",
                    deviceRepair.getId().toString(), businessType);
            StringBuilder result = new StringBuilder();
            JSONArray jsonArray = JSONObject.parseObject(s).getJSONArray("data");
            if (jsonArray != null && jsonArray.size() != 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    result.append(authorityDAO.findAuthorityByAuthorityName(jsonObject.getString("authName")).iterator().next().getCname());
                    result.append(jsonObject.getString("result")).append("\n");
                }
            }
            if("".equals(result.toString())){
                result.append("无需审核");
            }
            mav.addObject("s", result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("createUser", createUser);
        // 报修人
        mav.addObject("reportUser", shareService.findUserByUsername(deviceRepair.getReportUser()));
        // 验收人
        mav.addObject("acceptanceUser", shareService.findUserByUsername(deviceRepair.getAcceptanceUser()));
        // 设备价格
        if(deviceRepair.getType() == 1){
            LabRoomDevice lrd = deviceRepairService.getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            if (Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())) {
                mav.addObject("devicePrice", lrd.getSchoolDevice().getDevicePrice());
            }else {
                mav.addObject("devicePrice","无");
            }
        }else{
            mav.addObject("devicePrice", "无");
        }
        mav.setViewName("/deviceRepair/viewDeviceRepairApply.jsp");
        return mav;
    }

    /**
     * Description 删除设备维修申请单
     *
     * @param id 申请单id
     * @return 删除成功与否字符串
     * @author 黄保钱 2018-10-9
     */
    @RequestMapping("/deleteDeviceRepairApply")
    public @ResponseBody
    String deleteDeviceRepairApply(Integer id) {
        String result;
        try {
            result = deviceRepairService.deleteDeviceRepair(id);
        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
        }
        return result;
    }

    /**
     * Description 编辑设备维修申请单
     * @param id 申请单id
     * @param acno 学院编号
     * @return 编辑设备维修页面
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/editDeviceRepairApply")
    public ModelAndView editDeviceRepairApply(Integer id, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 设备下拉单
//        List<SchoolDevice> devices = deviceRepairService.getSchoolDevices(acno);
        // 实验室下拉单
        List<LabRoom> labRooms = deviceRepairService.getLabRoomByAcno(acno);
        mav.addObject("labRooms", labRooms);
        // 设备维修申请单
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        mav.addObject("deviceRepair", deviceRepair);
        List<LabRoomDevice> devices = deviceRepairService.getLabRoomDevices(deviceRepair.getLabAddress());
        mav.addObject("devices", devices);
        // 报修人列表
        List<User> users = deviceRepairService.findUsersByAuthorityNameAndAcademy("TEACHER", acno);
        mav.addObject("users", users);
        mav.addObject("username", deviceRepair.getReportUser());
        mav.setViewName("/deviceRepair/editDeviceRepairApply.jsp");
        return mav;
    }

    /**
     * Description 审核设备维修
     * @param id 设备维修申请单id
     * @return 审核页面
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/auditDeviceRepair")
    public ModelAndView auditDeviceRepair(Integer id, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        mav.addObject("deviceRepair", deviceRepair);
        User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
        String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
        businessType = deviceRepairService.getBusinessLevels(businessType);
        String s;
        try {
            // 获取当前审核的权限
            s = auditService.getCurrAudit(id.toString(), businessType);
            String authName = JSONObject.parseObject(s).getJSONArray("data").getJSONObject(0).getString("result");
            Iterator<Authority> authorities = authorityDAO.findAuthorityByAuthorityName(authName).iterator();
            if (authorities.hasNext()) {
                mav.addObject("authCName", authorities.next().getCname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("createUser", createUser);
        // 报修人
        mav.addObject("reportUser", shareService.findUserByUsername(deviceRepair.getReportUser()));
        mav.setViewName("/deviceRepair/auditDeviceRepair.jsp");
        return mav;
    }

    /**
     * Description 保存审核结果
     * @param id 设备维修审请单id
     * @param auditResult 审核结果
     * @param remark 审核备注
     * @return 重定向页面至设备审核列表页面
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/saveDeviceRepairAudit")
    public String saveDeviceRepairAudit(@RequestParam Integer id, @RequestParam Integer auditResult, @RequestParam String remark, @ModelAttribute("selected_academy") String acno) {
        deviceRepairService.saveDeviceRepairAudit(id, auditResult, remark, acno);
        return "redirect:/deviceRepair/deviceRepairCheckList";
    }

    /**
     * Description 设备维修确认列表
     * @return 我的设备维修确认页面
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/deviceRepairConfirmList")
    public ModelAndView deviceRepairConfirmList() {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/deviceRepairConfirmList.jsp");
        return mav;
    }

    /**
     * 确认设备维修
     * @param id 设备维修单id
     * @return 设备维修确认页面
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/confirmDeviceRepair")
    public ModelAndView confirmDeviceRepair(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView();
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        mav.addObject("deviceRepair", deviceRepair);
        mav.addObject("createUser", shareService.findUserByUsername(deviceRepair.getCreater()));
        // 报修人
        mav.addObject("reportUser", shareService.findUserByUsername(deviceRepair.getReportUser()));
        mav.setViewName("/deviceRepair/confirmDeviceRepair.jsp");
        return mav;
    }

    /**
     * Description 保存设备维修确认
     * @param id 设备维修申请单id
     * @param request 请求传参
     * @return 空字符串，防止无响应错误
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/saveDeviceRepairConfirm")
    public String saveDeviceRepairConfirm(@RequestParam Integer id, HttpServletRequest request) {
        BigDecimal confirmAmount = new BigDecimal(request.getParameter("confirmAmount"));
        String content = request.getParameter("content");
        String remark = request.getParameter("remark");
        deviceRepairService.saveDeviceRepairConfirm(id, confirmAmount, content, remark);
        return "";
    }

    /**
     * Description 设备维修验收
     * @param id 设备维修申请单
     * @return 验收页面
     * @author 黄保钱 2018-11-6
     */
    @RequestMapping("/acceptanceDeviceRepair")
    public ModelAndView acceptanceDeviceRepair(@RequestParam Integer id){
        ModelAndView mav = new ModelAndView();
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        mav.addObject("deviceRepair", deviceRepair);
        mav.addObject("createUser", shareService.findUserByUsername(deviceRepair.getCreater()));
        // 报修人
        mav.addObject("reportUser", shareService.findUserByUsername(deviceRepair.getReportUser()));
        // 设备价格
        if(deviceRepair.getType() == 1){
            LabRoomDevice lrd = deviceRepairService.getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            if (Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())) {
                mav.addObject("devicePrice", lrd.getSchoolDevice().getDevicePrice());
            }
        }else{
            mav.addObject("devicePrice", "无");
        }
        mav.setViewName("/deviceRepair/acceptanceDeviceRepair.jsp");
        return mav;
    }

    /**
     * Description 保存设备维修验收
     * @param id 设备维修申请单
     * @param request 请求传参
     * @return 字符串防止无响应
     * @author 黄保钱 2018-11-6
     */
    @RequestMapping("/saveAcceptanceDeviceRepair")
    public String saveAcceptanceDeviceRepair(@RequestParam Integer id, HttpServletRequest request){
        // 获取备注、维修与否参数
        String remark = request.getParameter("remark");
        Integer result = Integer.parseInt(request.getParameter("result"));
        deviceRepairService.saveAcceptanceDeviceRepair(id, remark, result);
        return "redirect:/deviceRepair/viewDeviceRepairApply?id="+id;
    }

    /**
     * Description 设备维修填写
     * @param id 设备维修申请单
     * @return 填写页面
     * @author 黄保钱 2018-11-6
     */
    @RequestMapping("/writeDeviceRepair")
    public ModelAndView writeDeviceRepair(@RequestParam Integer id){
        ModelAndView mav = new ModelAndView();
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        mav.addObject("deviceRepair", deviceRepair);
        mav.addObject("createUser", shareService.findUserByUsername(deviceRepair.getCreater()));
        // 报修人
        mav.addObject("reportUser", shareService.findUserByUsername(deviceRepair.getReportUser()));
        // 设备价格
        if(deviceRepair.getType() == 1){
            LabRoomDevice lrd = deviceRepairService.getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            if (Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())) {
                mav.addObject("devicePrice", lrd.getSchoolDevice().getDevicePrice());
            }
        }else{
            mav.addObject("devicePrice", "无");
        }
        mav.setViewName("/deviceRepair/writeDeviceRepair.jsp");
        return mav;
    }

    /**
     * Description 设备维修填写
     * @param id 设备维修申请单
     * @param request 请求传参
     * @return 字符串防止无响应
     * @author 黄保钱 2018-11-6
     */
    @RequestMapping("/saveWriteDeviceRepair")
    public String saveWriteDeviceRepair(@RequestParam Integer id, HttpServletRequest request){
        // 获取确认金额、内容参数
        BigDecimal confirmAmount = new BigDecimal(request.getParameter("confirmAmount"));
        String content = request.getParameter("content");
        deviceRepairService.saveWriteDeviceRepair(id, confirmAmount, content);
        return "redirect:/deviceRepair/viewDeviceRepairApply?id="+id;
    }

    /**
     * Description 设备维修入账
     * @param id 设备维修申请单
     * @return 重定向到查看
     * @author 黄保钱 2018-11-6
     */
    @RequestMapping("/recordDeviceRepair")
    public String recordDeviceRepair(@RequestParam Integer id, HttpServletRequest request){
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        // 状态置为已入账
        deviceRepair.setAuditStage(7);
        deviceRepairService.saveDeviceRepair(deviceRepair);
        return "redirect:/deviceRepair/viewDeviceRepairApply?id="+id;
    }

    /**
     * Description 设备维修查看列表
     * @return 我的设备维修确认页面
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/deviceRepairViewList")
    public ModelAndView deviceRepairViewList() {
        ModelAndView mav = new ModelAndView();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        mav.addObject("proj_name", pConfigDTO.PROJECT_NAME);
        mav.setViewName("/deviceRepair/deviceRepairViewList.jsp");
        return mav;
    }

    /**
     * Description 设备查看列表
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/getLabRoomDevices")
    public @ResponseBody
    String getLabRoomDevices(String labAddress) throws UnsupportedEncodingException {
        String devices = "<option value=\"\" selected>填写物资</option>";
        LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labAddress));
        List<LabRoomDevice> labRoomDevices = deviceRepairService.getLabRoomDevices(labRoom.getLabRoomName());
        for(LabRoomDevice l: labRoomDevices){
            devices += "<option " +
                    " value='" +
                    l.getSchoolDevice().getDeviceNumber() +
                    "'>" +
                    l.getSchoolDevice().getDeviceName() +
                    "[" +
                    l.getSchoolDevice().getDeviceNumber() +
                    "]" +
                    "</option>";
        }
        return shareService.htmlEncode(devices);
    }

    /**
     * Description 设备查看列表
     * @author 黄保钱 2018-10-12
     */
    @RequestMapping("/getCurrentStatus")
    public @ResponseBody
    String getCurrentStatus(Integer appId, @ModelAttribute("selected_academy") String acno) throws Exception {
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(appId);
        User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
        String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
        businessType = deviceRepairService.getBusinessLevels(businessType);
        String s = auditService.getBusinessLevel("-1", appId.toString(), businessType);
        String returnStr = "";
        JSONArray curJSONArray = JSONObject.parseObject(s).getJSONArray("data");
        if(curJSONArray.size() != 0) {
            for (int i = 0; i < curJSONArray.size(); i++) {
                JSONObject curJSONObject = curJSONArray.getJSONObject(i);
                String authName = curJSONObject.getString("authName");
                Authority authority = authorityDAO.findAuthorityByName(authName);
                String color = "未审核".equals(curJSONObject.getString("result")) ? "gray" : "black";
                returnStr += "<font style='color: " + color + "'>" + authority.getCname() + " ";
                if(curJSONObject.getString("auditUser") != null){
                    User auditUser = shareService.findUserByUsername(curJSONObject.getString("auditUser"));
                    returnStr += auditUser.getCname() + " ";
                }else {
                    returnStr += " ";
                }
                String result = curJSONObject.getString("result");
                returnStr += result + "</font><br>";
            }
        }
        return shareService.htmlEncode(returnStr);
    }


    /**
     * Description 导出设备维修申请单
     * @param id 申请单id
     * @param response 下载响应
     * @throws Exception
     */
    @RequestMapping("/exportDeviceRepairApply")
    public void exportDeviceRepairApply(@RequestParam int id, HttpServletResponse response) throws Exception {
        response.setContentType("application/doc;charset=UTF-8");
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        //id对应的设备维修记录
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(id);
        if(deviceRepair.getAuditStage() == 8){
            deviceRepair.setAuditStage(4);
            deviceRepair = deviceRepairService.save(deviceRepair);
        }
        User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
        User reportUser = shareService.findUserByUsername(deviceRepair.getReportUser());
        User acceptanceUser = shareService.findUserByUsername(deviceRepair.getAcceptanceUser());
        Map map = new HashMap();
        // 学校名称
        map.put("schoolName", pConfigDTO.schoolName);
        // 所属单位
        if(deviceRepair.getType() == 1) {
            LabRoomDevice labRoomDevice = deviceRepairService.getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            if (Objects.nonNull(labRoomDevice) && Objects.nonNull(labRoomDevice.getLabRoom())) {
                map.put("labCenter", labRoomDevice.getLabRoom().getLabCenter().getCenterName());
            }
        }else{
            map.put("labCenter", "校级");
        }
        // 所属部门
        map.put("schoolAcademy", createUser.getSchoolAcademy().getAcademyName());
        // 设备名称
        map.put("deviceName", deviceRepair.getDeviceName());
        // 设备型号和编号
        if(deviceRepair.getType() == 1) {
            LabRoomDevice device = deviceRepairService.getLabRoomDevice(deviceRepair.getDeviceNumber(), deviceRepair.getLabAddress());
            if (Objects.nonNull(device) && Objects.nonNull(device.getSchoolDevice().getDevicePattern())) {
                map.put("devicePattern", device.getSchoolDevice().getDevicePattern());
            }
            map.put("deviceNumber", deviceRepair.getDeviceNumber());
        }
        // 创建日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("createDate", sdf.format(deviceRepair.getCreateDate().getTime()));
        // 报修原因
        map.put("content", deviceRepair.getContent());
        // 预计费用
        map.put("expectCost", deviceRepair.getExpectAmount());
        // 申请人
        if(createUser != null) {
            map.put("createUserCname", createUser.getCname());
        }
        // 报修人
        if(reportUser != null) {
            map.put("reportUserCname", reportUser.getCname());
            map.put("reportUserTelephone", reportUser.getTelephone() == null ? "" : reportUser.getTelephone());
        }
        if(deviceRepair.getAuditStage() >= 4) {
            // 验收结果
            map.put("memo", deviceRepair.getMemo());
            // 验收人
            map.put("acceptanceUserCname", "");
            map.put("acceptanceDate","");
            if (acceptanceUser != null) {
                map.put("acceptanceUserCname", acceptanceUser.getCname());
                map.put("acceptanceDate", deviceRepair.getAcceptanceDate()==null?"":sdf.format(deviceRepair.getAcceptanceDate().getTime()));
        }
        }
        if(deviceRepair.getAuditStage() >= 6) {
            //修配件清单
            map.put("confirmContent", deviceRepair.getConfirmContent());
            // 填报人
            map.put("confirmUser", deviceRepair.getConfirmUser());
            // 填报日期
            map.put("confirmDate", sdf.format(deviceRepair.getConfirmDate().getTime()));
            // 填报金额
            map.put("comfirmAmount", deviceRepair.getConfirmAmount());
        }
        map.put("auditStage", deviceRepair.getAuditStage());
        // 审核结果
        List<Object[]> auditItems = new ArrayList<>();
        String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
        businessType = deviceRepairService.getBusinessLevels(businessType);
        String s = auditService.getBusinessLevel("-1", deviceRepair.getId().toString(), businessType);
        JSONArray curJSONArray = JSONObject.parseObject(s).getJSONArray("data");
        if(curJSONArray.size() != 0) {
            for (int i = 0; i < curJSONArray.size(); i++) {
                JSONObject curJSONObject = curJSONArray.getJSONObject(i);
                User auditUser = shareService.findUserByUsername(curJSONObject.getString("auditUser"));
                if(i==0){
                    if(auditUser != null) {
                        String firstAuditMemo = curJSONObject.getString("info");
                        String firstAuditUsername = curJSONObject.getString("auditUser");
                        String firstAuditDate = curJSONObject.getString("createTime");
                        String firstAuditUserCname = shareService.findUserByUsername(firstAuditUsername).getCname();
                        map.put("firstAuditMemo", firstAuditMemo);
                        map.put("firstAuditUserCname", firstAuditUserCname);
                        map.put("firstAuditDate",firstAuditDate.substring(0,firstAuditDate.indexOf(" ")));
                    }
                    continue;
                }
                Object[] auditItem = new Object[4];
                if(auditUser != null) {
                    // 占单元格数
                    auditItem[0] = 5 / (curJSONArray.size() - 1) + ((curJSONArray.size() - i) <= 5 % (curJSONArray.size() - 1) ? 1 : 0);
                    // 意见
                    auditItem[1] = curJSONObject.getString("info");
                    // 权限
                    String authName = curJSONObject.getString("authName");
                    Authority authority = authorityDAO.findAuthorityByName(authName);
                    auditItem[2] = authority.getCname() + "：" + auditUser.getCname() ;
                    // 日期
                    String creatT = curJSONObject.getString("createTime");
                    auditItem[3] = "日期："+ creatT.substring(0,creatT.indexOf(" "));
                }else{
                    // 占单元格数
                    auditItem[0] = 5 / (curJSONArray.size() - 1) + ((curJSONArray.size() - i) <= 5 % (curJSONArray.size() - 1) ? 1 : 0);
                    // 意见
                    auditItem[1] = "";
                    // 权限
                    auditItem[2] = "";
                    // 日期
                    auditItem[3] = "";
                }
                auditItems.add(auditItem);
            }
        }
        map.put("auditItems", auditItems);
        map = initMap(map);
        //导出word
        WordHandler handler = new WordHandler();
        File outFile = handler.createDoc("/net/zjcclims/ftl", "deviceRepair.ftl", map, "设备维修申请单");

        FileInputStream in = null;
        OutputStream o = null;
        try {
            byte b[] = new byte[1024];
            in = new FileInputStream(outFile);
            o = response.getOutputStream();
            response.setContentType("application/x-doc");
            response.setHeader("Content-disposition", "attachment; filename="
                    + URLEncoder.encode("设备维修申请单" + ".doc", "UTF-8"));// 指定下载的文件名
            response.setHeader("Content_Length", String.valueOf(outFile.length()));       // download the file.
            int n = 0;
            while ((n = in.read(b)) != -1) {
                o.write(b, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                o.flush();
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Description 将导出文档为空的变量设为空字符串，防止报错
     * @param map 变量映射
     * @return 变量映射
     * @author 黄保钱 2019-1-23
     */
    private Map initMap(Map map){
        map.putIfAbsent("reportUserCname", "");
        map.putIfAbsent("comfirmAmount", "");
        map.putIfAbsent("confirmContent", "");
        map.putIfAbsent("labCenter", "");
        map.putIfAbsent("memo", "");
        map.putIfAbsent("confirmUser", "");
        map.putIfAbsent("confirmDate", "");
        map.putIfAbsent("deviceName", "");
        map.putIfAbsent("deviceNumber", "");
        map.putIfAbsent("devicePattern", "");
        map.putIfAbsent("content", "");
        map.putIfAbsent("reportUserTelephone", "");
        map.putIfAbsent("devicePattern", "");
        map.putIfAbsent("createUserCname", "");
        map.putIfAbsent("schoolAcademy", "");
        map.putIfAbsent("createDate", "");
        map.putIfAbsent("expectCost", "");
        map.putIfAbsent("acceptanceUserCname", "");
        map.putIfAbsent("acceptanceDate", "");
        map.putIfAbsent("firstAuditMemo", "");
        map.putIfAbsent("firstAuditUserCname", "");
        map.putIfAbsent("firstAuditDate", "");
        map.putIfAbsent("auditItems", "");
        map.putIfAbsent("schoolName", "");
        return map;
    }

    /**
     * Description 检查用户电话号码
     * @param username 用户名
     * @return 是否有电话号码字符串
     * @author 黄保钱 2019-1-23
     */
    @RequestMapping("/checkTelephone")
    public String checkTelephone(@RequestParam String username){
        User user = shareService.findUserByUsername(username);
        String result = "success";
        if(user.getTelephone() == null){
            result = "fail";
        }
        return result;
    }
    /**
     * Description 获取审核拒绝的原因
     * @author 廖文辉 2018-01-08
     */
    @RequestMapping("/getRejectedReason")
    public @ResponseBody
    String getRejectedReason(Integer appId, @ModelAttribute("selected_academy") String acno) throws Exception {
        DeviceRepair deviceRepair = deviceRepairService.getDeviceRepairById(appId);
        User createUser = shareService.findUserByUsername(deviceRepair.getCreater());
        String businessType = "DeviceRepair" + createUser.getSchoolAcademy().getAcademyNumber();
        businessType = deviceRepairService.getBusinessLevels(businessType);
        String rejectedReason = "";
        try {
            String s = auditService.getBusinessLevel("-1", deviceRepair.getId().toString(), businessType);
            JSONArray jsonArray = JSONObject.parseObject(s).getJSONArray("data");
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    if ("审核拒绝".equals(o.getString("result"))) {
                        rejectedReason="审核拒绝"+"("+o.getString("info")+")";
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shareService.htmlEncode(rejectedReason);
    }


    /**
     * @Description 导出设备维修统计
     * @data 2019-01-09
     * @author 张德冰
     * @throws Exception
     */
    @RequestMapping("/exportDeviceRepairCount")
    public void exportDeviceRepairCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // sql查询语句
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        StringBuilder sql = new StringBuilder("select dr from DeviceRepair dr where 1=1 ");
        //查询条件
        String search = request.getParameter("search");
        //时间
        String start = request.getParameter("starttime");
        String starttime = "";
        if(start != null && !"".equals(start)) {
            String[] stmp = start.split("-");
            starttime = stmp[0] + "-" + stmp[1];
            Integer st = Integer.parseInt(stmp[2]) - 1;
            if (st < 10) {
                starttime += "-0" + st.toString();
            } else {
                starttime += "-" + st.toString();
            }
        }
        String end = request.getParameter("endtime");
        String endtime = "";
        if(end != null && !"".equals(end)) {
            String[] etmp = end.split("-");
            endtime = etmp[0] + "-" + etmp[1];
            Integer et = Integer.parseInt(etmp[2]) + 1;
            if (et < 10) {
                endtime += "-0" + et.toString();
            } else {
                endtime += "-" + et.toString();
            }
        }

        deviceRepairService.searchForDeviceRepair(search, sql);
        //验收/填写/入账
        sql.append(" and dr.auditStage > 3");

        if(starttime != null && !"".equals(starttime) && endtime != null && !"".equals(endtime)){
            sql.append(" and dr.createDate between '"+starttime +"' and '"+endtime+"' ");
        }else if(starttime != null && !"".equals(starttime) && (endtime == null ||"".equals(endtime))){
            sql.append(" and dr.createDate >= '"+starttime +"'");
        }else if((starttime != null || !"".equals(starttime)) && endtime != null && !"".equals(endtime)){
            sql.append(" and dr.createDate <= '"+endtime+"' ");
        }

        List<DeviceRepair> deviceRepairs = deviceRepairDAO.executeQuery(sql.toString(), 0, 0);
        List<Map> list = new ArrayList<Map>();
        int i = 1;
        for(DeviceRepair d:deviceRepairs){
            Map map = new HashMap();
            i++;
            //序号
            map.put("num", i);
            //申请人
            String creator ="";
            //所属单位 所属部门
            String academyName="";
            User creatorUser = shareService.findUserByUsername(d.getCreater());
            if(Objects.nonNull(creatorUser)){
                creator = creatorUser.getCname();
                academyName = creatorUser.getSchoolAcademy().getAcademyName();
            }
            map.put("creator", creator);
            map.put("academyName", academyName);
            //设备编号
            String deviceNumber ="";
            String devicePattern ="";
            if(Objects.nonNull(d.getDeviceNumber())){
                deviceNumber = d.getDeviceNumber();
                SchoolDevice s=schoolDeviceDAO.findSchoolDeviceByDeviceNumber(deviceNumber);
                //型号
                devicePattern = s.getDevicePattern();
            }
            map.put("deviceNumber", deviceNumber);
            map.put("devicePattern", devicePattern);
            //设备名称
            String deviceName ="";
            if(Objects.nonNull(d.getDeviceName())){
                deviceName = d.getDeviceName();
            }
            map.put("deviceName", deviceName);
            // 设备价格
            String devicePrice = "无";
            if(d.getType() == 1) {
                LabRoomDevice lrd = deviceRepairService.getLabRoomDevice(d.getDeviceNumber(), d.getLabAddress());
                if (Objects.nonNull(lrd) && Objects.nonNull(lrd.getSchoolDevice().getDevicePrice())) {
                    devicePrice = lrd.getSchoolDevice().getDevicePrice().toString();
                }
            }
            map.put("devicePrice", devicePrice);
            // 验收备注
            String acceptanceMemo = null;
            if(Objects.nonNull(d.getMemo())){
                acceptanceMemo = d.getMemo();
            }
            map.put("acceptanceMemo", acceptanceMemo);
            //报修人 报修人联系方式
            String reportUsername ="";
            String telephone ="";
            User reportUser = shareService.findUserByUsername(d.getReportUser());
            if(Objects.nonNull(reportUser)){
                reportUsername = reportUser.getCname();
                telephone = reportUser.getTelephone();
            }
            map.put("reportUsername", reportUsername);
            map.put("telephone", telephone);
            // 预计费用
            String expectAmountStr ="";
            if(Objects.nonNull(d.getExpectAmount())){
                expectAmountStr = d.getExpectAmount().toString();
            }
            map.put("expectAmountStr", expectAmountStr);
            //报修日期
            String createDate ="";
            if(Objects.nonNull(d.getCreateDate())){
                createDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(d.getCreateDate().getTime());
            }
            map.put("createDate", createDate);
            //故障现象及损坏原因
            String content ="";
            if(Objects.nonNull(d.getContent())){
                content = d.getContent();
            }
            map.put("content", content);
            //维修状态
            String status = "";
            //验收结果
            String result = "";
            if(Objects.nonNull(d.getAuditStage())){
                if(d.getAuditStage() == 5){
                    result = "未维修";
                }else {
                    result = "已维修";
                }
                if(d.getAuditStage() == 4) {
                    status = "验收";
                }else if(d.getAuditStage() == 6) {
                    status = "填写";
                }else if(d.getAuditStage() == 7) {
                    status = "入账";
                }else if(d.getAuditStage() == 8) {
                    status = "已维修";
                }else{
                    status = "未维修";
                }
            }
            map.put("result", result);
            map.put("status", status);
            //修配件清单及维修费用
            String confirmContent ="";
            if(Objects.nonNull(d.getConfirmContent())){
                confirmContent = d.getConfirmContent();
            }
            map.put("confirmContent", confirmContent);
            //实际维修费用
            String confirmAmount ="";
            if(Objects.nonNull(d.getConfirmAmount())){
                confirmAmount = d.getConfirmAmount().toString();
            }
            map.put("confirmAmount", confirmAmount);
            list.add(map);
        }
        String title = pConfigDTO.schoolName+"设备维修统计表";
        String[] hearders = new String[]{"序号", "申请人", "所属单位", "所属部门", "设备编号", "设备名称", "型号", "设备价格", "报修人", "报修人联系方式", "预计费用"
                , "报修日期", "故障现象及损坏原因(简要描述)", "验收结果", "验收结果备注", "维修配件清单及维修费用", "实际维修费用", "维修状态"};//表头数组
        String[] fields = new String[]{"num", "creator", "academyName", "academyName", "deviceNumber", "deviceName", "devicePattern", "devicePrice", "reportUsername", "telephone",
                "expectAmountStr", "createDate", "content", "result", "acceptanceMemo", "confirmContent", "confirmAmount", "status"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcelse(title, "", "", td, start, end);
    }
}
