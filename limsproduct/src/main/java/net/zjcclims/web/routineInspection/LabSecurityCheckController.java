package net.zjcclims.web.routineInspection;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.routineInspection.LabSecurityCheckService;
import net.zjcclims.service.routineInspection.RoutineInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller("LabSecurityCheckController")
@SessionAttributes("selected_academy")

public class LabSecurityCheckController<JsonResult> {

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) { // Register // static // property // editors.
        binder.registerCustomEditor(java.util.Calendar.class, new org.skyway.spring.util.databinding.CustomCalendarEditor());
        binder.registerCustomEditor(byte[].class, new org.springframework.web.multipart.support.ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(Boolean.class, new org.skyway.spring.util.databinding.EnhancedBooleanEditor(true));
        binder.registerCustomEditor(java.math.BigDecimal.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(java.math.BigDecimal.class, true));
        binder.registerCustomEditor(Integer.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Integer.class, true));
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(Double.class, true));
    }

    @Autowired
    private ShareService shareService;
    @Autowired
    private LabSecurityCheckService labsecurityCheckService;
    @Autowired
    private RoutineInspectionService routineInspectionService;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabSecurityCheckDetailsDAO labSecurityCheckDetailsDAO;
    @Autowired
    private LabSecurityCheckDAO labSecurityCheckDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;


    /*****************************************************************
     * @实验室管理员权限下的安全责任体系检查-查询{首页、查询、分页都经由此}
     * @作者：赵昶
     * @日期：2017-09-19
     *****************************************************************/
    @RequestMapping("/labSecurityCheck")
    public ModelAndView labSecurityCheck(HttpServletRequest request,
                                         @ModelAttribute LabSecurityCheck labSecurityCheck,
                                         @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }
        int totalRecords = labsecurityCheckService.count(request, labSecurityCheck, acno);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);

        //获取当前页的页码
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);

        //获取当前学期
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        mav.addObject("termName", term);

       /* //获取实验室(根据登录人所绑定的实验室筛选)
        List<LabRoom> labRoom = routineInspectionService.getLabRooms();
        // 获取实验室(未经过筛选)
        List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            mav.addObject("labRooms", labRoom);
        } else {
            mav.addObject("labRooms", labRooms);
        }*/
        //获取实验中心(根据登录人的学院筛选)
        List<LabCenter> labCenters = new ArrayList<>();
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1) {
            labCenters =  routineInspectionService.getLabCenterByCenterManager();
        }
        if(labCenters.size() == 0){
            labCenters = routineInspectionService.getAcademyLabCenters(acno);
        }else {
            labSecurityCheck.setLabCenter(labCenters.get(0));
        }
        mav.addObject("labCenters", labCenters);

        //月份：第一学期有9,10,11,12,1，第二学期有3,4,5,6,7,
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        List<String> month = new ArrayList<String>();
        if (termId % 2 == 0) {
            month.add("9");
            month.add("10");
            month.add("11");
            month.add("12");
            month.add("1");
        } else {
            month.add("3");
            month.add("4");
            month.add("5");
            month.add("6");
            month.add("7");
        }
        mav.addObject("month", month);

        mav.addObject("labSecurityCheck", labSecurityCheck);
        //获取所有“常规检查上报表”记录
        mav.addObject("labSecurityChecks", labsecurityCheckService.findlabSecurityChecks(request, currpage, pageSize, labSecurityCheck, acno));

        mav.setViewName("routineInspection/labSecurityCheck.jsp");
        return mav;
    }

    /*****************************************************************
     * @常规检查周次报表统计
     * @作者：李德
     * @日期：2017-11-13
     *****************************************************************/
    @RequestMapping("/inspection/labSecurityCheckStatistics")
    public ModelAndView labSecurityCheckStatistics(HttpServletRequest request,
                                                   @ModelAttribute LabSecurityCheck labSecurityCheck,
                                                   @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }

        //获取当前学期
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        mav.addObject("termName", term);

        //获取所有学期
        Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
        mav.addObject("terms", terms);

        //获取所有学院
        Set<SchoolAcademy> schoolAcademys = schoolAcademyDAO.findAllSchoolAcademys();
        mav.addObject("schoolAcademys", schoolAcademys);

        //获取实验室
       /* List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        mav.addObject("labRooms", labRooms);*/
        //获取实验中心(根据登录人的学院筛选)
        List<LabCenter> labCenters = new ArrayList<>();
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1) {
            labCenters =  routineInspectionService.getLabCenterByCenterManager();
        }
        if(labCenters.size() == 0){
            labCenters = routineInspectionService.getAcademyLabCenters(acno);
        }else {
            labSecurityCheck.setLabCenter(labCenters.get(0));
        }
        mav.addObject("labCenters", labCenters);

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());
        int pageSize = 20;

        List<LabCenter> securityCheckLabCenters = labsecurityCheckService.getSecurityCheckLabRooms(request, currpage, pageSize, labSecurityCheck, acno);
        mav.addObject("securityCheckLabCenters", securityCheckLabCenters);

        int totalRecords = labsecurityCheckService.getSecurityCheckLabRooms(request, 0, -1, labSecurityCheck, acno).size();

        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);

        //获取当前页的页码
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);

        mav.setViewName("routineInspection/labSecurityCheckStatistics.jsp");
        return mav;
    }

    /*****************************************************************
     * @安全责任体系检查周次报表导出
     * @作者：李德
     * @日期：2017-11-21
     *****************************************************************/
    @SuppressWarnings({"rawtypes", "unused", "unchecked"})
    @RequestMapping("/inspection/exportSecurityCheckLabRooms")
    public void exportSecurityCheckLabRooms(@ModelAttribute LabSecurityCheck labSecurityCheck, HttpServletRequest request, HttpServletResponse response, @RequestParam int page) throws Exception {

        //获取符合条件的实验室及其每月检查状态
        List<LabCenter> securityCheckLabCenters = labsecurityCheckService.getSecurityCheckLabRoomsExport(request, labSecurityCheck);
        labsecurityCheckService.exportSecurityCheckLabRooms(securityCheckLabCenters, request, response);
    }

    /*****************************************************************
     * @安全责任体系检查-新建{数据显示}
     * @作者：赵昶
     * @日期：2017-09-20
     *****************************************************************/
    @RequestMapping("/newCheckDetails")
    public ModelAndView newCheckDetails(HttpServletRequest request,
                                        @ModelAttribute LabSecurityCheck labSecurityCheck, @ModelAttribute("selected_academy") String acno) {

        ModelAndView mav = new ModelAndView();

        //类别：
        List<String> types = new ArrayList<String>();
        types.add("一类(涉化)");
        types.add("二类(机械)");
        types.add("三类(文化)");

        //时间：
        //获取当前学期
        String term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        mav.addObject("time", term);
        //当前中心所属学院
        String academyName = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(acno).getAcademyName();
        mav.addObject("academyName", academyName);
        // 当前月
        String curMonth = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        mav.addObject("curMonth", curMonth);

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }
       /* //实验室管理员
        int flag = 0;
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            flag = 1;
        }
        ////是当前用户的
        if (flag == 1) {
            //获取实验室(根据登录人所绑定的实验室筛选)
            List<LabRoom> labRooms = routineInspectionService.getLabRooms();
            mav.addObject("labRooms", labRooms);
        } else {
            //获取实验室(登录实验中心所属的学院)
            List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
            mav.addObject("labRooms", labRooms);
        }*/
        //获取实验中心(根据登录人的学院筛选)
        List<LabCenter> labCenters = new ArrayList<>();
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1) {
            labCenters =  routineInspectionService.getLabCenterByCenterManager();
        }
        if(labCenters.size() == 0){
            labCenters = routineInspectionService.getAcademyLabCenters(acno);
        }
        mav.addObject("labCenters", labCenters);

        //检查人：
        String cname = shareService.getUser().getCname();
        mav.addObject("cname", cname);

        mav.setViewName("routineInspection/newCheckDetails.jsp");
        return mav;

    }

    /*****************************************************************
     * @安全责任体系检查-新建{保存主表}
     * @作者：赵昶
     * @日期：2017-09-21
     *****************************************************************/
    @RequestMapping("/newChecks")
    public ModelAndView newChecks(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        LabSecurityCheck labSecurityCheck = new LabSecurityCheck();

        //保存为当前学期
        SchoolTerm term = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        labSecurityCheck.setSchoolTerm(term);

        //保存月份
        String month = request.getParameter("month");
        labSecurityCheck.setMonth(month);

        //保存实验中心labCenter
        String labIdS = request.getParameter("labCenter");
        int labIdI = Integer.parseInt(labIdS);
        LabCenter labCenter = labCenterDAO.findLabCenterById(labIdI);
        labSecurityCheck.setLabCenter(labCenter);

        //保存学院
        if (labCenter.getSchoolAcademy() != null) {
            labSecurityCheck.setAcademyNumber(labCenter.getSchoolAcademy().getAcademyNumber());
        }

        //保存类
        labSecurityCheck.setTypeTable(request.getParameter("types"));

        //保存检查人
        labSecurityCheck.setUser(shareService.getUser());

        //新建默认为待提交
        labSecurityCheck.setTypeAuditing("4");

        labSecurityCheck = labsecurityCheckService.saveLabSecurityCheck(labSecurityCheck);

        mav.addObject("labSecurityCheck", labSecurityCheck);

        //进入的页面为也修改：1，不可修改：2
        mav.addObject("ReadOnly", 1);

        //category：第几类，因为每类进入的页面的不同 输出的数据也不同，区别对待3种类型
        String category = labSecurityCheck.getTypeTable();

        //此为第一类推送的数据：
        if (category.equals("1")) {
            //提取第一类的第一项的安全选项(第几类，第几项，1（取所有）2（只取三级标题）)
            List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 1, 1);
            mav.addObject("sDictionarys1", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 2, 1);
            mav.addObject("sDictionarys2", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 3, 1);
            mav.addObject("sDictionarys3", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 4, 1);
            mav.addObject("sDictionarys4", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 5, 1);
            mav.addObject("sDictionarys5", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 6, 1);
            mav.addObject("sDictionarys6", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 7, 1);
            mav.addObject("sDictionarys7", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 8, 1);
            mav.addObject("sDictionarys8", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 9, 1);
            mav.addObject("sDictionarys9", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 10, 1);
            mav.addObject("sDictionarys10", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 11, 1);
            mav.addObject("sDictionarys11", sDictionarys);
        }

        //此为第二类推送的数据：
        if (category.equals("2")) {
            //提取第二类的第一项的安全选项(第几类，第几项，1（取所有）2（只取三级标题）)
            List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 1, 1);
            mav.addObject("sDictionarys1", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 2, 1);
            mav.addObject("sDictionarys2", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 3, 1);
            mav.addObject("sDictionarys3", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 4, 1);
            mav.addObject("sDictionarys4", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 5, 1);
            mav.addObject("sDictionarys5", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 6, 1);
            mav.addObject("sDictionarys6", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 7, 1);
            mav.addObject("sDictionarys7", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 8, 1);
            mav.addObject("sDictionarys8", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 9, 1);
            mav.addObject("sDictionarys9", sDictionarys);
        }

        //此为第三类推送的数据：
        if (category.equals("3")) {
            //提取第二类的第一项的安全选项(第几类，第几项，1（取所有）2（只取三级标题）)
            List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 1, 1);
            mav.addObject("sDictionarys1", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 2, 1);
            mav.addObject("sDictionarys2", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 3, 1);
            mav.addObject("sDictionarys3", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 4, 1);
            mav.addObject("sDictionarys4", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 5, 1);
            mav.addObject("sDictionarys5", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 6, 1);
            mav.addObject("sDictionarys6", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 7, 1);
            mav.addObject("sDictionarys7", sDictionarys);
        }

        //每类进入的页面不同
        mav.setViewName("check/check" + request.getParameter("types") + ".jsp");
        return mav;

    }

    /*****************************************************************
     * @安全责任体系检查-保存or提交
     * @作者：赵昶
     * @日期：2017-09-26
     *****************************************************************/
    @RequestMapping("/saveCheckDetails")
    public ModelAndView saveCheckDetails(HttpServletRequest request,
                                         @ModelAttribute LabSecurityCheck labSecurityCheck,
                                         @RequestParam int types) {
        ModelAndView mav = new ModelAndView();
        labSecurityCheck = labSecurityCheckDAO.findLabSecurityCheckById(labSecurityCheck.getId());
        int i = 1;
        LabSecurityCheckDetails labSecurityCheckDetails = new LabSecurityCheckDetails();
        LabSecurityCheckDetails labS = new LabSecurityCheckDetails();
        String category = labSecurityCheck.getTypeTable();

        //提取第几类安全选项
        List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionariesOnlyThree(Integer.parseInt(category));

        i = 1;
        for (SDictionary s : sDictionarys) {
            labSecurityCheckDetails.setLabSecurityCheck(labSecurityCheck);
            labSecurityCheckDetails.setSDictionary(s);
            labSecurityCheckDetails.setResult(request.getParameter("result" + i));
            labS = labSecurityCheckDetailsDAO.store(labSecurityCheckDetails);
            i++;
        }

        //判断是提交(2)还是保存(1)
        if (types == 2) {
            //提交后设为待审核状态
            labSecurityCheck.setTypeAuditing("3");
            labSecurityCheck = labsecurityCheckService.saveLabSecurityCheck(labSecurityCheck);
        }

        mav.setViewName("redirect:/labSecurityCheck?currpage=1");
        return mav;

    }

    /*****************************************************************
     * @安全责任体系检查-提交\查看
     * @作者：赵昶
     * @日期：2017-09-27
     *****************************************************************/
    @RequestMapping("/submitCheckDetails")
    public ModelAndView submitCheckDetails(HttpServletRequest request,
                                           @RequestParam Integer checkId, Integer types) {
        ModelAndView mav = new ModelAndView();

        //提交查看页面最上方要显示的LabSecurityCheck的字段信息
        LabSecurityCheck labSecurityCheck = labSecurityCheckDAO.findLabSecurityCheckById(checkId);
        mav.addObject("labSecurityCheck", labSecurityCheck);


        //types:1,2为实验室管理员，3,4为实验室专职管理员
        if (types == 1 || types == 3 || types == 4) {
            //types==1为查看：设页面为不可修改：2
            mav.addObject("ReadOnly", "2");
        } else {
            //types==2为提交：设为可修改：1
            mav.addObject("ReadOnly", "1");
        }

        mav.addObject("types", types);

        //进入的页面的分页框为：1
        mav.addObject("tage", 1);

        //category：第几类，因为每类进入的页面的不同 输出的数据也不同，区别对待3种类型
        String category = labSecurityCheck.getTypeTable();

        //此为第一类推送的数据：
        if (category.equals("1")) {
            //提取第一类的第一项的安全选项(第几类，第几项，1（取所有）2（只取三级标题）)
            List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 1, 1);
            mav.addObject("sDictionarys1", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 2, 1);
            mav.addObject("sDictionarys2", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 3, 1);
            mav.addObject("sDictionarys3", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 4, 1);
            mav.addObject("sDictionarys4", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 5, 1);
            mav.addObject("sDictionarys5", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 6, 1);
            mav.addObject("sDictionarys6", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 7, 1);
            mav.addObject("sDictionarys7", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 8, 1);
            mav.addObject("sDictionarys8", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 9, 1);
            mav.addObject("sDictionarys9", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 10, 1);
            mav.addObject("sDictionarys10", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(1, 11, 1);
            mav.addObject("sDictionarys11", sDictionarys);
        }

        //此为第二类推送的数据：
        if (category.equals("2")) {
            //提取第二类的第一项的安全选项(第几类，第几项，1（取所有）2（只取三级标题）)
            List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 1, 1);
            mav.addObject("sDictionarys1", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 2, 1);
            mav.addObject("sDictionarys2", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 3, 1);
            mav.addObject("sDictionarys3", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 4, 1);
            mav.addObject("sDictionarys4", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 5, 1);
            mav.addObject("sDictionarys5", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 6, 1);
            mav.addObject("sDictionarys6", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 7, 1);
            mav.addObject("sDictionarys7", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 8, 1);
            mav.addObject("sDictionarys8", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(2, 9, 1);
            mav.addObject("sDictionarys9", sDictionarys);
        }

        //此为第三类推送的数据：
        if (category.equals("3")) {
            //提取第二类的第一项的安全选项(第几类，第几项，1（取所有）2（只取三级标题）)
            List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 1, 1);
            mav.addObject("sDictionarys1", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 2, 1);
            mav.addObject("sDictionarys2", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 3, 1);
            mav.addObject("sDictionarys3", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 4, 1);
            mav.addObject("sDictionarys4", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 5, 1);
            mav.addObject("sDictionarys5", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 6, 1);
            mav.addObject("sDictionarys6", sDictionarys);

            sDictionarys = labsecurityCheckService.findChecskdictionaries(3, 7, 1);
            mav.addObject("sDictionarys7", sDictionarys);
        }

        //计算，总共多少条记录，测试用
        int sumSDictionarys = 0;
        //计算，每条记录对应的数，测试用
        int sumDetils = 0;
        List<LabSecurityCheckDetails> labSecurityCheckDetails = labsecurityCheckService.findChecskDetails(checkId);
        sumDetils = labSecurityCheckDetails.size();
        //此条数据测试用
        List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionariesOnlyThree(Integer.parseInt(category));
        sumSDictionarys = sDictionarys.size();
        //判断是否每一条记录是否对应每一条结果(测试用)
        if (sumDetils != sumSDictionarys) {
            //出错
        }

        //结果放入数组，前端使用
        String[] result = new String[sumDetils];
        int detailsNumber = 0;
        for (LabSecurityCheckDetails l : labSecurityCheckDetails) {
            result[detailsNumber] = l.getResult();
            detailsNumber++;
        }
        mav.addObject("result", result);

        //每类进入的页面不同
        mav.setViewName("check/submitCheck" + category + ".jsp");
        return mav;
    }

    /*****************************************************************
     * @安全责任体系检查-保存后再提交操作
     * @作者：赵昶
     * @日期：2017-09-27
     *****************************************************************/
    @RequestMapping("/saveSubmitCheckDetails")
    public ModelAndView saveSubmitCheckDetails(HttpServletRequest request,
                                               @ModelAttribute LabSecurityCheck labSecurityCheck) {
        ModelAndView mav = new ModelAndView();
        labSecurityCheck = labSecurityCheckDAO.findLabSecurityCheckById(labSecurityCheck.getId());

        //用户做了修改，首先删除原先的实验室安全检查详情表记录
        List<LabSecurityCheckDetails> listDetails = labsecurityCheckService.findChecskDetails(labSecurityCheck.getId());
        labsecurityCheckService.deleteListLabSecurityCheckDetails(listDetails);

        //保存新的实验室安全检查详情表记录
        //根据类别区
        String category = labSecurityCheck.getTypeTable();
        List<SDictionary> sDictionarys = labsecurityCheckService.findChecskdictionariesOnlyThree(Integer.parseInt(category));
        LabSecurityCheckDetails labSecurityCheckDetails = new LabSecurityCheckDetails();
        LabSecurityCheckDetails labS = new LabSecurityCheckDetails();
        int i = 1;
        for (SDictionary s : sDictionarys) {
            labSecurityCheckDetails.setLabSecurityCheck(labSecurityCheck);
            labSecurityCheckDetails.setSDictionary(s);
            labSecurityCheckDetails.setResult(request.getParameter("result" + i));
            labS = labSecurityCheckDetailsDAO.store(labSecurityCheckDetails);
            i++;
        }

        //再将实验室安全检查表的 状态设为待审核状态
        labSecurityCheck.setTypeAuditing("3");
        labSecurityCheck = labSecurityCheckDAO.store(labSecurityCheck);

        //给专职管理员推送消息
        //获取专职管理员
        List<User> users = shareService.findUsersByAuthorityId(23);
        for (User u : users) {
            Message message = new Message();
            message.setSendUser(shareService.getUserDetail().getCname());//发送人
            message.setTitle("安全责任体系检查");
            message.setContent("安全责任体系检查  <a href='/zjcclims/submitCheckDetails?checkId=" + labSecurityCheck.getId() + "&types=1'>点击查看</a>");//消息内容
            message.setMessageState(0);//设置未读
            message.setCreateTime(Calendar.getInstance());
            message.setUsername(u.getUsername());
            messageDAO.store(message);
        }

        //返回安全检查的首页
        mav.setViewName("redirect:/labSecurityCheck?currpage=1");
        return mav;

    }

    /*****************************************************************
     * @实验室专职管理员权限下的安全责任体系检查-查询{首页、查询、分页都经由此}
     * @作者：赵昶
     * @日期：2017-09-28
     *****************************************************************/
    @RequestMapping("/leaderLabSecurityCheck")
    public ModelAndView leaderLabSecurityCheck(
            HttpServletRequest request,
            @ModelAttribute LabSecurityCheck labSecurityCheck,
            @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {

        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }
        //页面翻页
        int totalRecords = labsecurityCheckService.countLeaderLabSecurityCheck(labSecurityCheck, acno);
        Integer pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);

        //页面显示的记录
        List<LabSecurityCheck> labSecurityChecks = labsecurityCheckService.findLeaderLabSecurityChecks(currpage, pageSize, labSecurityCheck, acno);
        mav.addObject("labSecurityChecks", labSecurityChecks);

        //获取当前页的页码
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);

        //获取当前学期
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        mav.addObject("termName", term);

        //获取实验中心(根据登录人的学院筛选)
        List<LabCenter> labCenters = new ArrayList<>();
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_EXCENTERDIRECTOR") != -1) {
            labCenters =  routineInspectionService.getLabCenterByCenterManager();
        }
        if(labCenters.size() == 0){
            labCenters = routineInspectionService.getAcademyLabCenters(acno);
        }
        mav.addObject("labCenters", labCenters);

        //月份：第一学期有9,10,11,12,1，第二学期有3,4,5,6,7,
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        List<String> month = new ArrayList<String>();
        if (termId % 2 == 0) {
            month.add("9");
            month.add("10");
            month.add("11");
            month.add("12");
            month.add("1");
        } else {
            month.add("3");
            month.add("4");
            month.add("5");
            month.add("6");
            month.add("7");
        }
        mav.addObject("month", month);

        //使查询记录在翻页时不丢失
        mav.addObject("labSecurityCheck", labSecurityCheck);

        mav.setViewName("routineInspection/labSecurityCheckLeader.jsp");
        return mav;
    }

    /*****************************************************************
     * @实验室专职管理员权限下的安全责任体系检查-审核
     * @作者：赵昶
     * @日期：2017-09-28
     *****************************************************************/
    @RequestMapping("/auttingLabSecurityCheck")
    public ModelAndView auttingLabSecurityCheck(
            @RequestParam String typeAuditing, int labSecurityCheckId) {

        ModelAndView mav = new ModelAndView();
        LabSecurityCheck labSecurityCheck = labSecurityCheckDAO.findLabSecurityCheckById(labSecurityCheckId);
        labSecurityCheck.setTypeAuditing(typeAuditing);
        labSecurityCheck = labSecurityCheckDAO.store(labSecurityCheck);
        mav.setViewName("redirect:/leaderLabSecurityCheck?currpage=1");
        return mav;
    }

    /**
     * Description 中心联动实验分室
     * @param center_id
     * @return
     * @author 陈乐为 2019-1-2
     */
    @ResponseBody
    @RequestMapping("/linkLabByCenter")
    public Map<String, String> linkLabByCenter(@RequestParam Integer center_id){
        Map<String, String> map = new HashMap<String, String>();
        String roomsValue = shareService.linkLabByCenter(center_id);
        map.put("roomsValue", roomsValue);
        return map;
    }

}