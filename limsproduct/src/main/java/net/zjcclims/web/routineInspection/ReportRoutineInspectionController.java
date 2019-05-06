package net.zjcclims.web.routineInspection;

import net.zjcclims.constant.WordHandler;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.routineInspection.RoutineInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;


@Controller("ReportRoutineInspectionController")
@SessionAttributes("selected_academy")
@RequestMapping("/reportRoutineInspection")


public class ReportRoutineInspectionController<JsonResult> {

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
    private RoutineInspectionService routineInspectionService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private CommonDocumentDAO commonDocumentDAO;
    @Autowired
    private RoutineInspectionDAO routineInspectionDAO;
    @Autowired
    private SchoolWeekDAO schoolWeekDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;


    /*****************************************************************
     * @上报常规检查表-查询{首页、查询、分页都经由此}
     * @作者：赵昶
     * @日期：2017-09-12
     *****************************************************************/
    @RequestMapping("/reportInspect")
    public ModelAndView general(HttpServletRequest request,
                                @ModelAttribute RoutineInspection routineInspection,
                                @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        //当前中心所属学院
        SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(acno);
        mav.addObject("schoolAcademy", schoolAcademy);

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }
        int totalRecords = routineInspectionService.CountReportAcademy(routineInspection,  acno);
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

        //获取实验室(根据登录人的学院筛选)
        List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        mav.addObject("labRooms", labRooms);
        //获取实验中心(根据登录人的学院筛选)
        List<LabCenter> labCenters = routineInspectionService.getAcademyLabCenters(acno);
        mav.addObject("labCenters", labCenters);

        //获取周次，并存入list
        Set<SchoolWeek> weeks = schoolWeekDAO.findAllSchoolWeeks();
        List<SchoolWeek> list = new ArrayList<SchoolWeek>(weeks);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getWeek().equals(list.get(i).getWeek())) {
                    list.remove(j);
                }
            }
        }
        mav.addObject("schoolWeeks", list);

        //获取所有“常规检查上报表”记录
        mav.addObject("RoutineInspection", routineInspectionService.findAllReportRoutineInspection(currpage, pageSize, routineInspection, acno));

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        mav.setViewName("routineInspection/reportInspect.jsp");
        return mav;
    }

    /*****************************************************************
     * @上报常规检查表-导出word文档
     * @作者：张德冰
     * @日期：2018.08.21
     *****************************************************************/
    @RequestMapping("/exportReportInspect")
    public void exportReportInspect(@RequestParam int id, HttpServletResponse response) throws Exception {
        response.setContentType("application/doc;charset=UTF-8");
        //id对应的上报常规检查记录
        RoutineInspection ri = routineInspectionDAO.findRoutineInspectionById(id);
        Map map = new HashMap();
        //实验中心
        map.put("labcenter", ri.getLabCenter().getCenterName());
        //负责人
        map.put("user", ri.getUser().getCname());
        //日常管理情况
        map.put("checkContent", ri.getCheckContent());
        //安全管理情况
        map.put("safetyManagement", ri.getSafetyManagement());
        //年月日
        map.put("year", ri.getCreationDate().get(Calendar.YEAR));
        map.put("month", ri.getCreationDate().get(Calendar.MONTH) + 1);
        map.put("day", ri.getCreationDate().get(Calendar.DAY_OF_MONTH));
        //导出word
        WordHandler handler = new WordHandler();
        File outFile = handler.createDoc("/net/zjcclims/ftl", "reportInspectTable.ftl", map, "实验室常规检查情况表");

        FileInputStream in = null;
        OutputStream o = null;
        try {
            byte b[] = new byte[1024];
            in = new FileInputStream(outFile);
            o = response.getOutputStream();
            response.setContentType("application/x-doc");
            response.setHeader("Content-disposition", "attachment; filename="
                    + URLEncoder.encode("实验室常规检查情况表" + ".doc", "UTF-8"));// 指定下载的文件名
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

    /*****************************************************************
     * @return
     * @上报常规检查表-新建{包括附件图片}
     * @作者：赵昶
     * @日期：2017-09-12
     *****************************************************************/
    @RequestMapping("/newReportRoutineInspection")
    public String newReportRoutineInspection(HttpServletRequest request, HttpServletResponse response,
                                             @ModelAttribute RoutineInspection routineInspection,
                                             @RequestParam int genre) {

        //上传附件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag = false;
        String suiji = UUID.randomUUID().toString();
        String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "operation" + sep + suiji;
        int cdId = 999999;
        //存放文件文件夹名称
        for (; fileNames.hasNext(); ) {

            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
            byte[] bytes = file.getBytes();
            if (bytes.length != 0) {
                // 说明申请有附件
                if (!flag) {
                    File dirPath = new File(fileDir);
                    if (!dirPath.exists()) {
                        flag = dirPath.mkdirs();
                    }
                }
                String fileTrueName = file.getOriginalFilename();
                File uploadedFile = new File(fileDir + sep + fileTrueName);
                try {
                    FileCopyUtils.copy(bytes, uploadedFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                CommonDocument conn = new CommonDocument();
                conn.setDocumentName(fileTrueName);
                //
                fileDir = "/upload" + "/operation/" + suiji + "/";
                conn.setDocumentUrl(fileDir + fileTrueName);
                CommonDocument cd = commonDocumentDAO.store(conn);
                cdId = cd.getId();
            }
        }

        //判断提交还是保存，即待提交还是待审核
        if (genre == 1) {
            //待审核
            routineInspection.setTypeAuditing("3");
        } else {
            //待提交
            routineInspection.setTypeAuditing("4");
        }
        //2为上报常规检查表
        routineInspection.setTypeTable("2");
        //设置检查人
        routineInspection.setUser(shareService.getUser());
        //设置学期
        routineInspection.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
        //设置创建时间
        routineInspection.setCreationDate(Calendar.getInstance());
        //保存常规检查记录
        RoutineInspection op = routineInspectionService.saveRoutineInspection(routineInspection);
        //无附件的情况下不执行，以防报错
        if (cdId != 999999) {
            CommonDocument dd = commonDocumentDAO.findCommonDocumentById(cdId);
            dd.setRoutineInspection(op);
            commonDocumentDAO.store(dd);
        }
        return "redirect:/reportRoutineInspection/reportInspect?currpage=1";
    }

    /*****************************************************************
     * @return
     * @上报常规检查表-提交{提交修改后的审查内容，并改为带审核状态}
     * @作者：赵昶
     * @日期：2017-09-12
     *****************************************************************/
    @RequestMapping("/submitRoutineInspection")
    public String submitRoutineInspection(HttpServletRequest request,
                                          @ModelAttribute RoutineInspection routineInspection) {
        //提交上报常规检查表时变化的只是“CheckContent”字段 ，其他保持数据不变
        RoutineInspection ri = routineInspectionDAO.findRoutineInspectionById(routineInspection.getId());
        ri.setCheckContent(routineInspection.getCheckContent());
        ri.setSafetyManagement(routineInspection.getSafetyManagement());
        //设为待审核
        ri.setTypeAuditing("3");
        //保存
        routineInspectionService.saveRoutineInspection(ri);

        //有附件上传的话，保存附件并替换
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest.getFileMap() != null) {
            String sep = System.getProperty("file.separator");
            Map files = multipartRequest.getFileMap();
            Iterator fileNames = multipartRequest.getFileNames();
            boolean flag = false;
            String suiji = UUID.randomUUID().toString();
            String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "operation" + sep + suiji;
            //	 String fileDir =  "/upload"+ sep+"operation"+sep+suiji;
            //存放文件文件夹名称
            for (; fileNames.hasNext(); ) {

                String filename = (String) fileNames.next();
                CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
                byte[] bytes = file.getBytes();
                if (bytes.length != 0) {
                    // 说明申请有附件
                    if (!flag) {
                        File dirPath = new File(fileDir);
                        if (!dirPath.exists()) {
                            flag = dirPath.mkdirs();
                        }
                    }
                    String fileTrueName = file.getOriginalFilename();
                    //System.out.println("文件名称："+fileTrueName);
                    File uploadedFile = new File(fileDir + sep + fileTrueName);
                    //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
                    try {
                        FileCopyUtils.copy(bytes, uploadedFile);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    CommonDocument conn = new CommonDocument();
                    conn.setDocumentName(fileTrueName);
                    //
                    fileDir = "/upload" + "/operation/" + suiji + "/";
                    conn.setDocumentUrl(fileDir + fileTrueName);
                    //删除原来的附件
                    String sql = "select a from CommonDocument a where  1=1 and a.routineInspection.id=" + routineInspection.getId();
                    List<CommonDocument> commonDocuments = commonDocumentDAO.executeQuery(sql);
                    for (CommonDocument cd : commonDocuments) {
                        commonDocumentDAO.remove(cd);
                    }

                    //保存新的附件
                    conn.setRoutineInspection(ri);
                    CommonDocument cd = commonDocumentDAO.store(conn);
                }

            }
        }

        return "redirect:/reportRoutineInspection/reportInspect?currpage=1";
    }

    /*****************************************************************
     * @学院领导的上报常规检查表页面-查询{首页、查询、分页都经由此}
     * @作者：赵昶
     * @日期：2017-09-13
     *****************************************************************/
    @RequestMapping("/leaderReportInspect")
    public ModelAndView leader(HttpServletRequest request,
                               @ModelAttribute RoutineInspection routineInspection,
                               @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }

        int totalRecords = routineInspectionService.CountLeaderReport(routineInspection, acno);
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

        //获取实验中心(根据登录人的学院筛选)
        List<LabCenter> labCenters = routineInspectionService.getAcademyLabCenters(acno);
        mav.addObject("labCenters", labCenters);

        //获取周次，并存入list
        Set<SchoolWeek> weeks = schoolWeekDAO.findAllSchoolWeeks();
        List<SchoolWeek> list = new ArrayList<SchoolWeek>(weeks);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getWeek().equals(list.get(i).getWeek())) {
                    list.remove(j);
                }
            }
        }
        mav.addObject("schoolWeeks", list);

        //获取所有已提交的该登录人同学院的上报常规检查表记录
        mav.addObject("RoutineInspection", routineInspectionService.findLeaderReportRoutineInspection(currpage, pageSize, routineInspection, acno));

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        mav.setViewName("routineInspection/reportInspectLeader.jsp");
        return mav;
    }

    /*****************************************************************
     * @return
     * @学院领导的上传常规检查表页面-审核
     * @作者：赵昶
     * @日期：2017-09-12
     *****************************************************************/
    @RequestMapping("/auditLeader")
    public String auditLeader(HttpServletRequest request,
                              @ModelAttribute RoutineInspection routineInspection) {
        //提交常规检查表时变化的只是“type_auditing”字段 ，1-审核通过，2-审核不同过 其他保持数据不变
        String typeAuditing = request.getParameter("cked");
        RoutineInspection ri = routineInspectionDAO.findRoutineInspectionById(routineInspection.getId());
        ri.setTypeAuditing(typeAuditing);
        routineInspectionService.saveRoutineInspection(ri);

        return "redirect:/reportRoutineInspection/leaderReportInspect?currpage=1";
    }
}