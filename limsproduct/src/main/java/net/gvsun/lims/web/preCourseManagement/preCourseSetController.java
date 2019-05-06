package net.gvsun.lims.web.preCourseManagement;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.service.preCourseManagement.PreCourseManagementService;
import net.zjcclims.dao.PreCapacityRangeDAO;
import net.zjcclims.dao.PreLabRoomDAO;
import net.zjcclims.dao.PreRoomTypeDAO;
import net.zjcclims.dao.PreSoftwareDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/****************************************************************************
 * @描述： 预排课管理设置模块-设置
 * @作者 ：张德冰
 * @时间： 2018-12-18
 ****************************************************************************/
@Controller("preCourseSetController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/preCourse")
public class preCourseSetController<JsonResult> {
    @Autowired
    private ShareService shareService;
    @Autowired
    private PreCourseManagementService preCourseManagementService;
    @Autowired
    private PreCapacityRangeDAO preCapacityRangeDAO;
    @Autowired
    private PreRoomTypeDAO preRoomTypeDAO;
    @Autowired
    private PreSoftwareDAO preSoftwareDAO;
    @Autowired
    private PreLabRoomDAO preLabRoomDAO;

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

    /****************************************************************************
     * @描述： 房间布局类型
     * @作者 ：张德冰
     * @时间： 2018-12-19
     ****************************************************************************/
    @RequestMapping("/preRoomTypeList")
    public ModelAndView preRoomTypeList(Integer currpage, @ModelAttribute PreRoomType preRoomType) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("preRoomType",preRoomType);

        //获取房间布局类型
        List<PreRoomType> preRoomTypes = preCourseManagementService.getPreRoomTypeListByPage(0, 0);
        mav.addObject("preRoomTypes",preRoomTypes);

        //每页记录
        Integer pagesize= 20;
        //总记录
        Integer totalRecords = preCourseManagementService.getPreRoomTypeListByPage(0, 0).size();
        //分页
        Map<String, Integer> pageModel = shareService.getPage(currpage, pagesize, totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("/lims/preCourseManagement/preCourseSet/preRoomTypeList.jsp");
        return mav;
    }


    /****************************************************************************
     * @描述： 房间布局类型-新建
     * @作者 ：张德冰
     * @时间： 2018-12-20
     ****************************************************************************/
    @RequestMapping("/newPreRoomType")
    public ModelAndView newPreRoomType() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("preRoomType",new PreRoomType());

        mav.setViewName("/lims/preCourseManagement/preCourseSet/newRoomType.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 房间布局类型-编辑
     * @作者 ：张德冰
     * @时间： 2018-12-20
     ****************************************************************************/
    @RequestMapping("/editPreRoomType")
    public ModelAndView editPreRoomType(Integer id) {
        ModelAndView mav = new ModelAndView();

        //需要编辑的数据
        mav.addObject("preRoomType",preRoomTypeDAO.findPreRoomTypeById(id));

        mav.setViewName("/lims/preCourseManagement/preCourseSet/newRoomType.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 房间布局类型-保存
     * @作者 ：张德冰
     * @时间： 2018-12-20
     ****************************************************************************/
    @RequestMapping("/savePreRoomType")
    public void savePreRoomType(@ModelAttribute PreRoomType preRoomType) {
        preRoomTypeDAO.store(preRoomType);
    }

    /****************************************************************************
     * @描述： 房间布局类型-删除
     * @作者 ：张德冰
     * @时间： 2018-12-20
     ****************************************************************************/
    @RequestMapping("/deletePreRoomType")
    public String deletePreRoomType(Integer id) {

        PreRoomType preRoomType = preRoomTypeDAO.findPreRoomTypeById(id);
        preRoomTypeDAO.remove(preRoomType);
        return "redirect:/lims/preCourse/preRoomTypeList?currpage=1";
    }

    /****************************************************************************
     * @描述： 容量范围
     * @作者 ：张德冰
     * @时间： 2018-12-20
     ****************************************************************************/
    @RequestMapping("/preCapacityRangeList")
    public ModelAndView preCapacityRangeList(Integer currpage) {
        ModelAndView mav = new ModelAndView();

        //获取容量范围
        List<PreCapacityRange> preCapacityRanges = preCourseManagementService.getPreCapacityRangeListByPage(0, 0);
        mav.addObject("preCapacityRanges",preCapacityRanges);

        //每页记录
        Integer pagesize= 20;
        //总记录
        Integer totalRecords = preCourseManagementService.getPreCapacityRangeListByPage(0, 0).size();
        //分页
        Map<String, Integer> pageModel = shareService.getPage(currpage, pagesize, totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("/lims/preCourseManagement/preCourseSet/preCapacityRangeList.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 容量范围-新建
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/newPreCapacityRange")
    public ModelAndView newPreCapacityRange() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("preCapacityRange",new PreCapacityRange());

        mav.setViewName("/lims/preCourseManagement/preCourseSet/newCapacityRange.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 容量范围-保存
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/savePreCapacityRange")
    public void savePreCapacityRange(@ModelAttribute PreCapacityRange preCapacityRange, HttpServletRequest request) {
        //获取范围
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String capaRange = min + "～" + max;
        //保存范围
        preCapacityRange.setCapaRange(capaRange);

        preCapacityRangeDAO.store(preCapacityRange);
    }

    /****************************************************************************
     * @描述： 容量范围-编辑
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/editPreCapacityRange")
    public ModelAndView editPreCapacityRange(Integer id) {
        ModelAndView mav = new ModelAndView();

        PreCapacityRange preCapacityRange = preCapacityRangeDAO.findPreCapacityRangeById(id);
        mav.addObject("preCapacityRange",preCapacityRange);

        String str = preCapacityRange.getCapaRange();
        //取到min
        String min=str.split("～")[0];
        //取到max
        String max=str.split("～")[1];

        mav.addObject("min",min);
        mav.addObject("max",max);

        mav.setViewName("/lims/preCourseManagement/preCourseSet/newCapacityRange.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 容量范围-删除
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/deletePreCapacityRange")
    public String deletePreCapacityRange(Integer id) {

        PreCapacityRange preCapacityRange = preCapacityRangeDAO.findPreCapacityRangeById(id);
        preCapacityRangeDAO.remove(preCapacityRange);
        return "redirect:/lims/preCourse/preCapacityRangeList?currpage=1";
    }

    /****************************************************************************
     * @描述： 所有软件
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/preSoftwareList")
    public ModelAndView preSoftwareList(Integer currpage) {
        ModelAndView mav = new ModelAndView();

        //获取所有软件
        List<PreSoftware> preSoftwares = preCourseManagementService.getPreSoftwareListByPage(0, 0);
        mav.addObject("preSoftwares",preSoftwares);

        //每页记录
        Integer pagesize= 20;
        //总记录
        Integer totalRecords = preCourseManagementService.getPreSoftwareListByPage(0, 0).size();
        //分页
        Map<String, Integer> pageModel = shareService.getPage(currpage, pagesize, totalRecords);
        mav.addObject("pageModel",pageModel);

        mav.setViewName("/lims/preCourseManagement/preCourseSet/preSoftwareList.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 所有软件-新建
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/newPreSoftware")
    public ModelAndView newPreSoftware() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("preSoftware",new PreSoftware());

        mav.setViewName("/lims/preCourseManagement/preCourseSet/newSoftware.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 所有软件-保存
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/savePreSoftware")
    public void savePreSoftware(@ModelAttribute PreSoftware preSoftware) {
        preSoftwareDAO.store(preSoftware);
    }

    /****************************************************************************
     * @描述： 所有软件-编辑
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/editPreSoftware")
    public ModelAndView editPreSoftware(Integer id) {
        ModelAndView mav = new ModelAndView();

        PreSoftware preSoftware = preSoftwareDAO.findPreSoftwareById(id);
        mav.addObject("preSoftware",preSoftware);

        mav.setViewName("/lims/preCourseManagement/preCourseSet/newSoftware.jsp");
        return mav;
    }

    /****************************************************************************
     * @描述： 所有软件-删除
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @RequestMapping("/deletePreSoftware")
    public String deletePreSoftware(Integer id) {

        PreSoftware preSoftware = preSoftwareDAO.findPreSoftwareById(id);
        preSoftwareDAO.remove(preSoftware);
        return "redirect:/lims/preCourse/preSoftwareList?currpage=1";
    }
}
