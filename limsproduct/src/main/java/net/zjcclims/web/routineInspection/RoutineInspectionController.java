package net.zjcclims.web.routineInspection;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;
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
import java.io.IOException;
import java.net.BindException;
import java.util.*;

@Controller("RoutineInspectionController")
@SessionAttributes("selected_academy")
public class RoutineInspectionController<JsonResult> {

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
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

    @Autowired
    private ShareService shareService;
    @Autowired
    private RoutineInspectionService routineInspectionService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private CommonDocumentDAO commonDocumentDAO;
    @Autowired
    private RoutineInspectionDAO routineInspectionDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private SchoolWeekDAO schoolWeekDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;

    /*****************************************************************
     * @常规检查表-查询{首页、查询、分页都经由此
     * @作者：赵昶
     * @日期：2017-09-04
     *****************************************************************/
    @RequestMapping("/routineInspection/general")
    public ModelAndView general(HttpServletRequest request,
                                @ModelAttribute RoutineInspection routineInspection,
                                @RequestParam int currpage,
                                @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        int totalRecords = routineInspectionService.Count(request, routineInspection, acno);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);

        // 获取当前页的页码
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);

        // 获取当前学期
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getTermName();
        mav.addObject("termName", term);

        //获取实验室(根据登录人所绑定的实验室筛选)
        List<LabRoom> labRoom = routineInspectionService.getLabRooms();
        // 获取实验室(未经过筛选)
        List<LabRoom> labRooms = new ArrayList<>();
        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") == -1 &&
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") == -1) {
            labRooms = routineInspectionService.getAcademyLabRooms(acno);
        }else {
            labRooms = routineInspectionService.getAcademyLabRooms(null);
        }
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_LABMANAGER") != -1) {
            mav.addObject("labRooms", labRoom);
        } else {
            mav.addObject("labRooms", labRooms);
        }

        // 获取实验室(根据登录人所绑定的实验室筛选)
        List<LabRoom> labAdminRooms = routineInspectionService.getLabRooms();
        mav.addObject("labAdminRooms", labAdminRooms);

        // 获取周次，并存入list
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

        // 获取所有常规检查表记录
        mav.addObject("RoutineInspection", routineInspectionService
                .findAllRoutineInspection(request, currpage, pageSize,
                        routineInspection, acno));
        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());
        mav.addObject("routineInspection", routineInspection);

        // 判断是否有实验室管理员身份
        int flag = 0;
        Set<Authority> authorities = shareService.getUserDetail()
                .getAuthorities();
        for (Authority authoritie : authorities) {
            if (authoritie.getAuthorityName().equals("LABMANAGER")) {
                flag = 1;
                break;
            }
        }
        mav.addObject("flag", flag);
        //当前中心所属学院
        SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(acno);
        mav.addObject("schoolAcademy", schoolAcademy);

        mav.setViewName("routineInspection/inspectGeneral.jsp");
        return mav;
    }

    /*****************************************************************
     * @常规检查周次报表统计
     * @作者：李德
     * @日期：2017-11-13
     *****************************************************************/
    @RequestMapping("/inspection/inspectionStatistics")
    public ModelAndView inspectionStatistics(HttpServletRequest request,
                                             @ModelAttribute RoutineInspection routineInspection,
                                             @RequestParam int currpage,
                                             @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }

        // 获取当前学期
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance())
                .getTermName();
        mav.addObject("termName", term);

        // 获取所有学期
        Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
        mav.addObject("terms", terms);

/*        // 获取所有学院
        Set<SchoolAcademy> schoolAcademys = schoolAcademyDAO
                .findAllSchoolAcademys();
        mav.addObject("schoolAcademys", schoolAcademys);*/

        // 获取实验室
        List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        mav.addObject("labRooms", labRooms);

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        Set<SchoolWeek> weeks = schoolWeekDAO.findAllSchoolWeeks();
        List<SchoolWeek> list = new ArrayList<SchoolWeek>(weeks);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getWeek().equals(list.get(i).getWeek())) {
                    list.remove(j);
                }
            }
        }
        mav.addObject("list", list);

        int weekCount = list.size();

        int pageSize = 20;

        List<LabRoom> inspectionLabRooms = routineInspectionService
                .getInspectionLabRooms(request, currpage, pageSize,
                        routineInspection, weekCount, acno);
        mav.addObject("inspectionLabRooms", inspectionLabRooms);

        int totalRecords = routineInspectionService
                .getInspectionLabRooms(request, 0, -1,
                        routineInspection, weekCount, acno).size();
        ;

        Map<String, Integer> pageModel = shareService.getPage(currpage,
                pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);


        // 获取当前页的页码
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);

        mav.setViewName("routineInspection/inspectionRecordStatistics.jsp");
        return mav;
    }

    /*****************************************************************
     * @常规检查周次报表导出
     * @作者：李德
     * @日期：2017-11-22
     *****************************************************************/
    @SuppressWarnings({"rawtypes", "unused", "unchecked"})
    @RequestMapping("/inspection/exportGeneralCheckLabRooms")
    public void exportGeneralCheckLabRooms(
            @ModelAttribute RoutineInspection routineInspection,
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam int page) throws Exception {

        // 周次循环
        Set<SchoolWeek> weeks = schoolWeekDAO.findAllSchoolWeeks();
        List<SchoolWeek> list = new ArrayList<SchoolWeek>(weeks);
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getWeek().equals(list.get(i).getWeek())) {
                    list.remove(j);
                }
            }
        }

        int weekCount = list.size();
        // 获取符合条件的实验室及其每周检查状态
        List<LabRoom> generalCheckLabRooms = routineInspectionService
                .getInspectionLabRoomsExport(request, routineInspection,
                        weekCount);
        routineInspectionService.exportGeneralCheckLabRooms(
                generalCheckLabRooms, request, response);
    }

    /*****************************************************************
     * @return
     * @常规检查表-新建{包括附件图片
     * @作者：赵昶
     * @日期：2017-09-04
     *****************************************************************/
    @RequestMapping("/routineInspection/newRoutineInspection")
    public String newRoutineInspection(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @ModelAttribute RoutineInspection routineInspection,
                                       @RequestParam int genre) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag = false;
        String suiji = UUID.randomUUID().toString();
        String fileDir = request.getSession().getServletContext()
                .getRealPath("/")
                + "upload" + sep + "operation" + sep + suiji;
        int cdId = 999999;
        // 存放文件文件夹名称
        for (; fileNames.hasNext(); ) {

            String filename = (String) fileNames.next();
            CommonsMultipartFile file = (CommonsMultipartFile) files
                    .get(filename);
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
                // System.out.println("文件名称："+fileTrueName);
                File uploadedFile = new File(fileDir + sep + fileTrueName);
                // System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
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

        // 判断提交还是保存，即待提交还是待审核
        if (genre == 1) {
            // 待审核
            routineInspection.setTypeAuditing("3");
        } else {
            // 待提交
            routineInspection.setTypeAuditing("4");
        }
        // 1为常规检查表
        routineInspection.setTypeTable("1");
        // 设置检查人
        routineInspection.setUser(shareService.getUser());
        // 设置学期
        routineInspection.setSchoolTerm(shareService
                .getBelongsSchoolTerm(Calendar.getInstance()));
        // 设置创建时间
        routineInspection.setCreationDate(Calendar.getInstance());
        // 保存常规检查记录
        RoutineInspection op = routineInspectionService
                .saveRoutineInspection(routineInspection);
        // 无附件的情况下不执行，以防报错
        if (cdId != 999999) {
            CommonDocument dd = commonDocumentDAO.findCommonDocumentById(cdId);
            dd.setRoutineInspection(op);
            commonDocumentDAO.store(dd);
        }

        return "redirect:/routineInspection/general?currpage=1";
    }

    /*****************************************************************
     * @常规检查表-上传附件(多附件){谷歌浏览器不支持该插件上传多附件功能，此功能暂时放弃
     * @作者：赵昶
     * @日期：2017-09-06
     *****************************************************************/
    @RequestMapping("/uploaddnolinedocment")
    public @ResponseBody
    String uploaddnolinedocment(HttpServletRequest request,
                                HttpServletResponse response, BindException errors, Integer id)
            throws Exception {
        String ss = routineInspectionService.uploaddnolinedocment(request,
                response, id);
        return shareService.htmlEncode(ss);
    }

    /*****************************************************************
     * @常规检查表-删除附件{谷歌浏览器不支持该插件上传多附件功能，此功能暂时放弃
     * @作者：赵昶
     * @日期：2017-09-06
     *****************************************************************/
    @RequestMapping("/delectdnolinedocment")
    public @ResponseBody
    String delectdnolinedocment(@RequestParam Integer idkey) throws Exception {
        CommonDocument d = commonDocumentDAO.findCommonDocumentById(idkey);
        commonDocumentDAO.remove(d);
        return "ok";
    }

    /*****************************************************************
     * @常规检查表-查看{页面通过ajax返回id对应的记录
     * @作者：赵昶
     * @日期：2017-09-07
     *****************************************************************/
    @RequestMapping(value = "/ajaxGetRecord", produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String ajaxGetRecord(HttpServletRequest request,@RequestParam int id) {
        RoutineInspection ri = routineInspectionDAO
                .findRoutineInspectionById(id);
        StringBuffer result = new StringBuffer();
        result.append("{");
        // 时间：
        result.append("\"riTime\"" + ":" + "\""
                + ri.getSchoolTerm().getTermName() + ri.getWeek() + "\"" + ",");
        if (ri.getLabRoom() != null) {
            // 学院
            result.append("\"riCenterName\"" + ":" + "\""
                    + ri.getLabRoom().getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyName()
                    + "\"" + ",");
            // 实验室
            result.append("\"riRoomName\"" + ":" + "\""
                    + ri.getLabRoom().getLabRoomName() + "\"" + ",");
        }
        // 检查人
        if(request.getSession().getAttribute("selected_role").equals("ROLE_EXCENTERDIRECTOR")){
            result.append("\"riCnam\"" + ":" + "\"" + "保密" + "\"" + ",");
        }else {
            result.append("\"riCnam\"" + ":" + "\"" + ri.getUser().getCname() + "\"" + ",");
        }

        // 系统换行符
        String lineSeparator = "\r\n";
        // 日常管理情况
        result.append("\"riCheckContent\"" + ":" + "\"" + ri.getCheckContent().replaceAll(lineSeparator, "\\\\n")
                + "\"" + ",");
        // 安全管理情况
        result.append("\"riSafetyManagement\"" + ":" + "\"" + ri.getSafetyManagement().replaceAll(lineSeparator, "\\\\n")
                + "\"" + ",");

        if (ri.getLabCenter() != null) {
            // 实验中心
            result.append("\"riLabCenterName\"" + ":" + "\""
                    + ri.getLabCenter().getCenterName() + "\"" + ",");
            if (ri.getLabRoom() == null) {
                // 学院
                result.append("\"riCenterName\"" + ":" + "\""
                        + ri.getLabCenter().getSchoolAcademy().getAcademyName()
                        + "\"" + ",");
            }
        }

        // 图片url，注意图片地址链接中的“斜杠”传递时可能出错
        String sql = "select s from CommonDocument s where 1=1";
        sql += " and s.routineInspection=" + id;
        List<CommonDocument> commonDocuments = commonDocumentDAO
                .executeQuery(sql);
        if (commonDocuments != null && commonDocuments.size() > 0) {
            result.append("\"url\"" + ":" + "\""
                    + commonDocuments.get(0).getDocumentUrl() + "\"" + ",");
            result.append("\"doc_name\"" + ":" + "\"点击下载 "
                    + commonDocuments.get(0).getDocumentName() + "\"" + ",");
        }

        // 审核情况
        result.append("\"riTypeAuditing\"" + ":" + "\"" + ri.getTypeAuditing()
                + "\"");
        result.append("}");

        return result.toString();
    }

    /*****************************************************************
     * @return
     * @常规检查表-提交{提交修改后的审查内容，并改为待审核状态
     * @作者：赵昶
     * @日期：2017-09-04
     *****************************************************************/
    @RequestMapping("/routineInspection/submitRoutineInspection")
    public String submitRoutineInspection(HttpServletRequest request,
                                          @ModelAttribute RoutineInspection routineInspection) {
        //提交常规检查表时变化的有“CheckContent”字段 ，有修改图片则要修改图片
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
        //常规检查
        if (ri.getTypeTable().equals('1')) {
            //给专职管理员推送消息
            //获取专职管理员
            List<User> users = shareService.findUsersByAuthorityId(23);
            for (User u : users) {
                Message message = new Message();
                message.setSendUser(shareService.getUserDetail().getCname());//发送人
                message.setTitle("常规检查");
                message.setContent("常规检查  <a href='/zjcclims/routineInspection/general?currpage=1'>点击查看</a>");//消息内容
                message.setMessageState(0);//设置未读
                message.setCreateTime(Calendar.getInstance());
                message.setUsername(u.getUsername());
                messageDAO.store(message);
            }
        }
        //常规检查上报
        if (ri.getTypeTable().equals('2')) {
            //给副院长推送消息
            //获取副院长
            List<User> users = shareService.findUsersByAuthorityId(3);
            for (User u : users) {
                Message message = new Message();
                message.setSendUser(shareService.getUserDetail().getCname());//发送人
                message.setTitle("常规检查上报");
                message.setContent("常规检查上报  <a href='/zjcclims/reportRoutineInspection/reportInspect?currpage=1'>点击查看</a>");//消息内容
                message.setMessageState(0);//设置未读
                message.setCreateTime(Calendar.getInstance());
                message.setUsername(u.getUsername());
                messageDAO.store(message);
            }
        }
        return "redirect:/routineInspection/general?currpage=1";
    }

    /*****************************************************************
     * @专职管理员或者学院领导的常规检查表页面-查询{首页、查询、分页都经由此
     * @作者：赵昶
     * @日期：2017-09-08
     *****************************************************************/
    @RequestMapping("/routineInspection/leader")
    public ModelAndView leader(HttpServletRequest request,
                               @ModelAttribute RoutineInspection routineInspection,
                               @RequestParam int currpage,
                               @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }
        int totalRecords = routineInspectionService.CountAcademy(
                routineInspection, acno);
        int pageSize = 20;
        Map<String, Integer> pageModel = shareService.getPage(currpage,
                pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);

        // 获取当前页的页码
        mav.addObject("page", currpage);
        mav.addObject("totalRecords", totalRecords);

        // 获取当前学期
        String term = "";
        term = shareService.getBelongsSchoolTerm(Calendar.getInstance())
                .getTermName();
        mav.addObject("termName", term);

        // 获取实验室(根据登录人的学院筛选)
        List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        mav.addObject("labRooms", labRooms);
        // 获取周次，并存入list
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

        // 获取所有常规检查表记录
        mav.addObject("RoutineInspection", routineInspectionService
                .findAllAcademyRoutineInspection(currpage, pageSize,
                        routineInspection, acno));

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        mav.setViewName("routineInspection/inspectLeader.jsp");
        return mav;
    }

    /*****************************************************************
     * @return
     * @专职管理员的常规检查表页面-审核
     * @作者：赵昶
     * @日期：2017-09-12
     *****************************************************************/
    @RequestMapping("/routineInspection/auditRoutineInspection")
    public String auditRoutineInspection(HttpServletRequest request,
                                         @ModelAttribute RoutineInspection routineInspection) {
        // 提交常规检查表时变化的只是“type_auditing”字段 ，1-审核通过，2-审核不同过 其他保持数据不变
        String typeAuditing = request.getParameter("cked");
        RoutineInspection ri = routineInspectionDAO
                .findRoutineInspectionById(routineInspection.getId());
        ri.setTypeAuditing(typeAuditing);
        routineInspectionService.saveRoutineInspection(ri);

        return "redirect:/routineInspection/leader?currpage=1";
    }

}