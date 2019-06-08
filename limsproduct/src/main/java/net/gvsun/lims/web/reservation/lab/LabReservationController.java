package net.gvsun.lims.web.reservation.lab;

import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.dao.SchoolCourseDetailDAO;
import net.zjcclims.domain.CStaticValue;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.dao.SystemTimeDAO;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.SystemTime;
import net.zjcclims.service.common.CStaticValueService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/****************************************************************************
 * Descriptions：实验室预约管理
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("labReservationController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/reservation/lab")
public class LabReservationController<JsonResult> {
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private PConfig pConfig;
    @Autowired
    SystemTimeDAO systemTimeDAO;
    /************************************************************
     * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
     *
     ************************************************************/
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register
        binder.registerCustomEditor(Calendar.class,
                new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class,
                new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(false));
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class,
                new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Integer.class, true));
        binder.registerCustomEditor(Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }

    /************************************************************
     * Descriptions：实验室预约管理-可预约的实验室列表页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/labRoomList")
    public ModelAndView labRoomList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/reservation/lab/labRoomList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：实验室预约管理-显示我的预约记录页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/labReservationList")
    public ModelAndView labReservationList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/reservation/lab/labReservationList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：实验室预约管理-显示我的预约审核页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/labReservationAuditList")
    public ModelAndView labReservationAuditList(HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.setViewName("lims/reservation/lab/labReservationAuditList.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：实验室预约管理-实验室预约主页面
     *
     * @作者：魏诚
     * @时间：2018-09-04
     ************************************************************/
    @RequestMapping("/newLabReservation")
    public ModelAndView newLabReservation(HttpServletRequest request,@ModelAttribute("selected_academy") String acno,String labRoomId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("labRoomMap", outerApplicationService.getLabRoomMap(acno));
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.addObject("labRoomId", labRoomId);
        mav.setViewName("lims/reservation/lab/newLabReservation.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：实验室预约管理-实验室预约查看页面
     *
     * @作者：Hezhaoyi
     * @时间：2019-4-17
     ************************************************************/
    @RequestMapping("/LabRoomReservation")
    public ModelAndView LabRoomReservation(HttpServletRequest request,@ModelAttribute("selected_academy") String acno,String labRoomId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.addObject("labRoomId", labRoomId);
        mav.setViewName("lims/reservation/lab/labReservationDetail.jsp");
        return mav;
    }

    /************************************************************
     * Descriptions：实验室预约管理-实验室审核查看页面
     *
     * @作者：Hezhaoyi
     * @时间：2019-4-25
     ************************************************************/
    @RequestMapping("/labReservationAudit")
    public ModelAndView labReservationAudit(HttpServletRequest request,@ModelAttribute("selected_academy") String acno,String labRId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("zuulServerUrl", pConfig.zuulServerUrl);
        mav.addObject("labRId", labRId);
        mav.setViewName("lims/reservation/lab/labReservationAudit.jsp");
        return mav;
    }
}
