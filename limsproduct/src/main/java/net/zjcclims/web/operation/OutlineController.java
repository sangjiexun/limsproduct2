package net.zjcclims.web.operation;
/**
 * Description 实验大纲管理模块
 * @author 陈乐为 2018-9-14
 */

import api.net.gvsunlims.constant.ConstantInterface;
import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.operation.OutlineService;
import net.zjcclims.service.system.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller("OutlineController")
@SessionAttributes("selected_academy")
@RequestMapping("/outline")
public class OutlineController<JsonResult> {
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

    @Autowired private CommonDocumentDAO commonDocumentDAO;

    @Autowired private OutlineService outlineService;
    @Autowired private LabCenterService labCenterService;
    @Autowired private ShareService shareService;
    @Autowired private UserDetailService userDetailService;
    @Autowired private OperationService operationService;

    /**
     * Description 实验大纲管理列表
     * @param outline
     * @param currpage
     * @return
     * @author 陈乐为 2018-9-14
     */
    @RequestMapping("/listOutline")
    public ModelAndView listOutline(@ModelAttribute OperationOutline outline, @RequestParam int currpage,
                                    @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        int pageSize = ConstantInterface.PAGE_SIZE;
        // 默认中心所在学院
        SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if(academy!=null && academy.getAcademyNumber()!=null) {
            outline.setSchoolAcademy(academy);
        }
        int totalRecords = outlineService.findOutlineByQueryCount(outline);
        List<OperationOutline> outlineList = outlineService.findOutlineByQuery(outline, currpage, pageSize);
        mav.addObject("outlineList", outlineList);

        mav.addObject("user", shareService.getUserDetail());
        mav.addObject("outline", outline);
        mav.addObject("pageModel",shareService.getPage(currpage, pageSize, totalRecords));

        mav.setViewName("/outline/listOutline.jsp");
        return mav;
    }

    /**
     * Description 编辑大纲
     * @param idKey
     * @return
     * @author 陈乐为 2018-9-14
     */
    @RequestMapping("/editOutline")
    public ModelAndView editOutline(@RequestParam Integer idKey, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        OperationOutline outline = outlineService.findOutlineByPrimaryKey(idKey);
        if(!EmptyUtil.isObjectEmpty(outline) && !EmptyUtil.isIntegerEmpty(outline.getId())) {
            mav.addObject("outline", outline);
            mav.addObject("majorsEdit",outline.getSchoolMajors());
            mav.addObject("operationUser",outline.getOperationUser());
            mav.addObject("property",outline.getCDictionarys());
        }else {
            mav.addObject("outline", new OperationOutline());
        }
        mav.addObject("idKey", idKey);
        // 课程
        mav.addObject("courseInfoMap", outlineService.getSchoolCourseInfo(acno));
        // 学分
        mav.addObject("outlineCreditMap", outlineService.getOutlineCreditMap());
        // 学院
        mav.addObject("academys", userDetailService.getAllSchoolAcademy());
        //查找登录人所在的学院专业
        mav.addObject("schoolmajer", operationService.getschoolmajerSet(acno));
        //查找开课性质
        mav.addObject("commencementnaturemap", operationService.getcommencementnatureSet());
        //查找教师
        mav.addObject("allTeachers", shareService.findAllTeacheres());

        mav.setViewName("/outline/editOutline.jsp");
        return mav;
    }

    /**
     * Description 保存大纲
     * @param operationOutline
     * @param request
     * @return
     * @author 陈乐为 2018-9-17
     */
    @RequestMapping("/saveOutline")
    public String saveOutline(@ModelAttribute OperationOutline operationOutline ,HttpServletRequest request) {
        //获取面向专业  多对多
        String[] schoolMajors = request.getParameterValues("schoolMajorsa");
        //获取课程性质 多对多
        String[] properties = request.getParameterValues("commencementnaturemap");
        String[] items = request.getParameter("projectitrms").split(",");
        String[] teachers = request.getParameterValues("operationOutlineTeacher");
        String docment=request.getParameter("docment");
        operationOutline.setUser(shareService.getUserDetail());
        OperationOutline op = outlineService.saveOutline(operationOutline,schoolMajors,properties,items, teachers);
        if(docment!=null && docment!=""){
            String[] str= docment.split(",");
            for (String string : str) {
                if(string!=null && string!=""){
                    CommonDocument dd = commonDocumentDAO.findCommonDocumentByPrimaryKey(Integer.parseInt(string));
                    if(dd!=null){
                        dd.setOperationOutline(op);
                        commonDocumentDAO.store(dd);
                    }
                }
            }
        }
        return "redirect:/outline/listOutline?currpage=1";
    }

    /**
     * Description 保存
     * @param nameop
     * @return
     * @author 陈乐为 2018-9-17
     */
    @RequestMapping(value="/saveGenerateperations")
    public @ResponseBody String saveGenerateperations(String nameop) {
        List<OperationItem> d = outlineService.getSaveGenerateperations(nameop);
        String str="";
        if(d.size()>0){
            for (OperationItem itm : d) {
                if(itm.getCDictionaryByLpCategoryRequire()==null ){
                    str+="<tr align='center'><td align='center'><input type='checkbox' value='"+itm.getId()+"'></td><td align='center'>"+(itm.getLpCodeCustom()==null?"":itm.getLpCodeCustom())+"</td><td align='center'>"+itm.getLpName()+"</td><td align='center'></td></tr>";
                }else{
                    str+="<tr align='center'><td align='center'><input type='checkbox' value='"+itm.getId()+"'></td><td align='center'>"+(itm.getLpCodeCustom()==null?"":itm.getLpCodeCustom())+"</td><td align='center'>"+itm.getLpName()+"</td><td align='center'>"+itm.getCDictionaryByLpCategoryRequire().getCName()+"</td></tr>";
                }
            }
        }else{
            str+="<tr ><td align='center'></td><td align='center'></td><td align='center'>无</td><td align='center'></td></tr>";
        }
        return shareService.htmlEncode(str);
    }

    /**
     * Description 删除大纲
     * @param idKey
     * @return
     * @author 陈乐为 2018-9-18
     */
    @RequestMapping("/deleteOutline")
    public String deleteOutline(Integer idKey) {
        operationService.delectloutline(idKey);
        return "redirect:/outline/listOutline?currpage=1";
    }

    /**
     * Description 查看大纲详情
     * @param idKey
     * @return
     * @author 陈乐为 2018-9-18
     */
    @RequestMapping("/viewOutline")
    public ModelAndView checkout(@RequestParam int idKey) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("infor", operationService.getoperationoutlineinfor(idKey));
        mav.setViewName("outline/viewOutline.jsp");
        return mav;
    }

}