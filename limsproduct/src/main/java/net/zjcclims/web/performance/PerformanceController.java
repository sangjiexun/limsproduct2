package net.zjcclims.web.performance;
/**
 * Description 个人绩效模块
 * @author 陈乐为 2018-9-14
 */

import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.dao.IndividualPerformanceDAO;
import net.zjcclims.dao.SchoolYearDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.performance.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import net.zjcclims.service.common.ShareService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.Map;
import java.util.List;
@Controller("PerformanceController")
@RequestMapping("/performance")
public class PerformanceController<JsonResult> {
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
        binder.registerCustomEditor(java.util.Calendar.class,new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class,new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class,new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
    }
    @Autowired
    ShareService shareService;
    @Autowired
    PerformanceService performanceService;
    @Autowired
    SchoolYearDAO schoolYearDAO;
    @Autowired
    IndividualPerformanceDAO individualPerformanceDAO;
    @Autowired
    CommonDocumentDAO commonDocumentDAO;
    /**
     * 个人绩效列表
     * @author 廖文辉
     * 2018.09.19
     */
    @RequestMapping("/listPerformance")
    public ModelAndView listPerformance(@ModelAttribute IndividualPerformance individualPerformance, @RequestParam int currpage,@RequestParam int type,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        int pageSize=20;
        int totalRecords=performanceService.findPerformanceCount(individualPerformance,type).size();
        //分页信息
        Map<String,Integer> pageModel =shareService.getPage(currpage, pageSize,totalRecords);
        List <IndividualPerformance> individualPerformanceList=performanceService.findPerformance(individualPerformance,currpage,pageSize,type);
        mav.addObject("individualPerformanceList",individualPerformanceList);
        mav.addObject("pageModel",pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("type",type);
        mav.addObject("schoolTerms", shareService.findAllSchoolTerms());
        mav.addObject("schoolYear",shareService.findSchoolTerm());
        mav.addObject("schoolAcademy",shareService.getAllSchoolAcademy());
        mav.addObject("teacher",performanceService.findTeacheresBySchoolAcademy(acno));
        mav.addObject("listSpecialtyDuty", shareService
                .getCDictionaryData("category_lab_worker_specialty_duty"));
        mav.addObject("schoolCourseInfoList",performanceService.getSchoolCourseInfoList());
        mav.addObject("schoolMajorList",performanceService.getSchoolMajorList());
        mav.setViewName("/performance/listPerformance"+type+".jsp");
        return mav;
    }
    /**
     * 保存个人绩效
     * @author 廖文辉
     * 2018.09.19
     */
    @RequestMapping("/savePerformance")
    @ResponseBody
    public String savePerformance(@ModelAttribute IndividualPerformance individualPerformance, @RequestParam int type){
        individualPerformance.setType(type);
        individualPerformanceDAO.store(individualPerformance);
        return "success";
    }
    /****************************************************************************
     * @功能：给实验室上传文档
     * @作者：廖文辉
     ****************************************************************************/
    @RequestMapping("/performanceUpload")
    public @ResponseBody String performanceUpload(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {
        performanceService.performanceDocumentUpload(request, response, id, 2);
        return "ok";
    }
    /****************************************************************************
     * 功能：编辑个人绩效
     * 作者：廖文辉
     * 时间：2018-9-21
     ****************************************************************************/
    @RequestMapping("/editPerformance")
    public ModelAndView editPerformance(@RequestParam int id,@RequestParam int type,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        IndividualPerformance individualPerformance = individualPerformanceDAO.findIndividualPerformanceById(id);
        mav.addObject("individualPerformance", individualPerformance);
        List<SchoolTerm> schoolTerms = shareService.findSchoolTerm();
        mav.addObject("schoolYear", schoolTerms);
        mav.addObject("schoolTerms", shareService.findAllSchoolTerms());
        mav.addObject("schoolAcademy", shareService.getAllSchoolAcademy());
        mav.addObject("teacher", performanceService.findTeacheresBySchoolAcademy(acno));
        mav.addObject("listSpecialtyDuty", shareService
                .getCDictionaryData("category_lab_worker_specialty_duty"));
        mav.addObject("schoolCourseInfoList",performanceService.getSchoolCourseInfoList());
        mav.addObject("schoolMajorList",performanceService.getSchoolMajorList());
        mav.addObject("type", type);
        mav.setViewName("/performance/editPerformance"+type+".jsp");
        return mav;
    }
    /****************************************************************************
     * 功能：删除个人绩效
     * 作者：廖文辉
     * 时间：2018-9-26
     ****************************************************************************/
    @RequestMapping("/delectPerformance")
    public String delectPerformance(@RequestParam int id,@RequestParam int type) {
        IndividualPerformance individualPerformance = individualPerformanceDAO.findIndividualPerformanceById(id);
        individualPerformanceDAO.remove(individualPerformance);
        individualPerformanceDAO.flush();
        return "redirect:/performance/listPerformance?currpage=1&type="+type;
    }
}