package net.zjcclims.web.routineInspection;

import net.luxunsh.web.aop.SystemServiceLog;
import net.zjcclims.common.WordGenerator;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabAnnexService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.routineInspection.LabSecurityCheckService;
import net.zjcclims.service.routineInspection.RoutineInspectionService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("CasualInspectionController")
@SessionAttributes("selected_academy")
public class CasualInspectionController<JsonResult> {

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
    private LabRoomDAO labRoomDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private SchoolAcademyDAO schoolAcademyDAO;
    @Autowired
    private LabSecurityCheckService labSecurityCheckService;
    @Autowired
    private SpecialExaminationDAO specialExaminationDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private LabAnnexService labAnnexService;
    @Autowired
    private LabCenterService labCenterService;
    @Autowired
    private PConfig pConfig;

    /*****************************************************************
     * @抽检（学生，督导）-查询{首页、查询、分页都经由此,通过types判断是学生（1）还是督导（2）}
     * @作者：赵昶
     * @日期：2017-09-14
     *****************************************************************/
    @RequestMapping("/casualInspection/studnetCasual")
    public ModelAndView studnetCasual(HttpSession httpSession, HttpServletRequest request,
                                      @ModelAttribute RoutineInspection routineInspection,
                                      @RequestParam int currpage, int types, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        //获取登录实验中心所属学院
        mav.addObject("schoolAcademy", schoolAcademyDAO.findSchoolAcademyByAcademyNumber(acno));

        // 判断是否有超级管理员、运行与安全管理科身份、督导小组、设备管理科科员
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERVISIONGROUP") != -1||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_ASSETMANAGER") != -1) {
            acno = null;
        }
        //types(1学生，2督导,3实验室管理员)
        int totalRecords = routineInspectionService.countStudnetCasual(types, routineInspection, acno);
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

        //获取所有实验室
        List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        mav.addObject("labRooms", labRooms);

        //获取所有学期
        Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
        mav.addObject("terms", terms);

        //获取所有学院
        Set<SchoolAcademy> schoolAcademys = schoolAcademyDAO.findAllSchoolAcademys();
        mav.addObject("schoolAcademys", schoolAcademys);

        //获取抽检记录
        mav.addObject("RoutineInspection", routineInspectionService.findStudnetCasual(currpage, pageSize, routineInspection, types, acno));

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        //学生（1）督导（2）
        mav.addObject("types", types);
        mav.addObject("routineInspection", routineInspection);
        mav.setViewName("routineInspection/studnetCasual.jsp");
        return mav;
    }

    /*****************************************************************
     * @return
     * @抽检(学生，督导)-新建{包括附件图片，通过types判断是学生（1）还是督导（2）}
     * @作者：赵昶
     * @日期：2017-09-14
     *****************************************************************/
    @RequestMapping("/casualInspection/newStudnetCasual")
    public String newStudnetCasual(HttpServletRequest request, HttpServletResponse response,
                                   @ModelAttribute RoutineInspection routineInspection,
                                   @RequestParam int genre, int types) {

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

        if (types == 1) {
            //3为学生抽检
            routineInspection.setTypeTable("3");
        } else {
            //4为督导抽检
            routineInspection.setTypeTable("4");
        }
        //设置检查人
        routineInspection.setUser(shareService.getUser());
        //设置学期
        routineInspection.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
        //设置创建时间
        routineInspection.setCreationDate(Calendar.getInstance());
        //保存学生抽检记录
        RoutineInspection op = routineInspectionService.saveRoutineInspection(routineInspection);
        //无附件的情况下不执行，以防报错
        if (cdId != 999999) {
            CommonDocument dd = commonDocumentDAO.findCommonDocumentById(cdId);
            dd.setRoutineInspection(op);
            commonDocumentDAO.store(dd);
        }
        //学生抽检
        User user = shareService.getUser();
        if (types == 1) {
            //获取设备科
            List<User> users = shareService.findUsersByAuthorityId(16);
            for (User u : users) {
                //给所属学院的设备科推送消息
                if (user.getSchoolAcademy().getAcademyNumber() == u.getSchoolAcademy().getAcademyNumber()) {
                    Message message = new Message();
                    message.setSendUser(shareService.getUserDetail().getCname());//发送人
                    message.setTitle("学生抽检");
                    message.setContent("学生抽检  <a href='/zjcclims/casualInspection/studnetCasual?currpage=1&types=1'>点击查看</a>");//消息内容
                    message.setMessageState(0);//设置未读
                    message.setCreateTime(Calendar.getInstance());
                    message.setUsername(u.getUsername());
                    messageDAO.store(message);
                }
            }
        }
        if (types == 1) {
            return "redirect:/casualInspection/studnetCasual?currpage=1&types=1";
        } else {
            return "redirect:/casualInspection/studnetCasual?currpage=1&types=2";
        }

    }

    /*****************************************************************
     * @return
     * @抽检(学生,督导)-提交{提交修改后的审查内容，并改为待审核状态，通过types判断是学生（1）还是督导（2）}
     * @作者：赵昶
     * @日期：2017-09-14
     *****************************************************************/
    @RequestMapping("/casualInspection/subStudnetCasual")
    public String submitRoutineInspection(HttpServletRequest request,
                                          @ModelAttribute RoutineInspection routineInspection) {

        String types = request.getParameter("types");
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
                    commonDocumentDAO.store(conn);
                }

            }
        }

        if (types.equals("1")) {
            return "redirect:/casualInspection/studnetCasual?currpage=1&types=1";
        } else {
            return "redirect:/casualInspection/studnetCasual?currpage=1&types=2";
        }

    }

    /*****************************************************************
     * @抽检(设备科)-查询{首页、查询、分页都经由此，通过types判断是抽检学生（1）还是抽检督导（2）}
     * @作者：赵昶
     * @日期：2017-09-15
     *****************************************************************/
    @RequestMapping("/casualInspection/facilityCasual")
    public ModelAndView facilityCasual(HttpServletRequest request,
                                       @ModelAttribute RoutineInspection routineInspection,
                                       @RequestParam int currpage, int types, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份、督导小组
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERVISIONGROUP") != -1||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_ASSETMANAGER") != -1) {
            acno = null;
        }

        int totalRecords = routineInspectionService.findAllStudnetCasual(request, 0, -1, routineInspection, types, acno).size();
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

        //获取所有实验室
        List<LabRoom> labRooms = routineInspectionService.getAcademyLabRooms(acno);
        mav.addObject("labRooms", labRooms);

        //获取所有学期
        Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
        mav.addObject("terms", terms);

        //获取所有学院
        Set<SchoolAcademy> schoolAcademys = schoolAcademyDAO.findAllSchoolAcademys();
        mav.addObject("schoolAcademys", schoolAcademys);

        //获取所有抽检记录(学生or督导)
        mav.addObject("RoutineInspection", routineInspectionService.findAllStudnetCasual(request, currpage, pageSize, routineInspection, types, acno));

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        //types：1学生，2督导
        mav.addObject("types", types);
        mav.setViewName("routineInspection/facilityCasual.jsp");
        return mav;
    }

    /*****************************************************************
     * @return
     * @学院领导的抽检页面（抽检学生，抽检督导）-审核
     * @作者：赵昶
     * @日期：2017-09-15
     *****************************************************************/
    @RequestMapping("/casualInspection/auditFacility")
    public String auditFacility(HttpServletRequest request,
                                @ModelAttribute RoutineInspection routineInspection) {
        //提交常规检查表时变化的只是“type_auditing”字段 ，1-审核通过，2-审核不同过 其他保持数据不变
        String typeAuditing = request.getParameter("cked");
        RoutineInspection ri = routineInspectionDAO.findRoutineInspectionById(routineInspection.getId());
        ri.setTypeAuditing(typeAuditing);
        routineInspectionService.saveRoutineInspection(ri);

        if ("1".equals(request.getParameter("types"))) {
            return "redirect:/casualInspection/facilityCasual?currpage=1&types=1";
        } else {
            return "redirect:/casualInspection/facilityCasual?currpage=1&types=2";
        }
    }

    /*****************************************************************
     * @专项检查{首页、查询、分页都经由此}
     * @作者：赵昶
     * @日期：2017-10-9
     *****************************************************************/
    @RequestMapping("/specialExamination")
    public ModelAndView specialexamination(HttpServletRequest request,
                                           @ModelAttribute SpecialExamination specialExamination,
                                           @RequestParam int currpage, @ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();

        // 判断是否有超级管理员、运行与安全管理科身份
        if (request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_SUPERADMIN") != -1 ||
                request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_OPEARTIONSECURITYMANAGEMENT") != -1) {
            acno = null;
        }

        int totalRecords = routineInspectionService.countSpecialExamination(specialExamination, acno);
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

        //获取所有实验室
        List<LabAnnex> labAnnexs = labAnnexService.findAllLabAnnexBySchoolAcademy(acno);
        mav.addObject("labAnnexs", labAnnexs);

        //获取所有学期
        Set<SchoolTerm> terms = schoolTermDAO.findAllSchoolTerms();
        mav.addObject("terms", terms);

        //获取所有学院
        Set<SchoolAcademy> schoolAcademys = schoolAcademyDAO.findAllSchoolAcademys();
        mav.addObject("schoolAcademys", schoolAcademys);

        //获取专项检查记录
        mav.addObject("specialExaminations", routineInspectionService.findSpecialExamination(request, currpage, pageSize, specialExamination, acno));

        // 获取登录用户信息
        mav.addObject("user", shareService.getUserDetail());

        mav.addObject("specialExamination", specialExamination);
        // 获取所有参加检查人员
        mav.addObject("participant",shareService.getUsersMap());
        //检查类型:
        mav.addObject("checkTypes", labSecurityCheckService.findDictionariesCheckTypes());
        mav.setViewName("routineInspection/specialExamination.jsp");
        return mav;
    }

    /*****************************************************************
     * @专项检查-查询检查项
     * @作者：赵昶
     * @日期：2017-10-9
     *****************************************************************/
    @RequestMapping(value = "/getCheckItems", produces = "application/json;charset=utf-8")
    public @ResponseBody
    String getDocumentList(@RequestParam int checkType) throws UnsupportedEncodingException {
        System.out.println(checkType);

        List<String> checkItems = labSecurityCheckService.findDictionariesCheckItems(checkType);
        String jsonCheckItems = "[";
        int no = 1;
        for (String s : checkItems) {
            jsonCheckItems = jsonCheckItems + "{\"courseNo\":\"" + no++ + "\",\"value\":\"" + s + "\"},";
        }
        jsonCheckItems.substring(0, jsonCheckItems.length() - 1);
        jsonCheckItems = jsonCheckItems + "]";
        System.out.println(jsonCheckItems);

        return URLDecoder.decode(jsonCheckItems, "UTF-8");
    }

    /*****************************************************************
     * @return
     * @专项检查-新建-保存
     * @作者：赵昶
     * @日期：2017-10-10
     *****************************************************************/
    @RequestMapping("/saveSpecialExamination")
    public String saveSpecialExamination(HttpServletRequest request, HttpServletResponse response,
                                         @ModelAttribute SpecialExamination specialExamination, @ModelAttribute("selected_academy") String acno) throws ParseException {

        //上传附件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String sep = System.getProperty("file.separator");
        Map files = multipartRequest.getFileMap();
        Iterator fileNames = multipartRequest.getFileNames();
        boolean flag = false;
        String suiji = UUID.randomUUID().toString();
        String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "operation" + sep + suiji;
        //	 String fileDir =  "/upload"+ sep+"operation"+sep+suiji;
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
                String fileTrueName1 = "101" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
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
                CommonDocument cd = commonDocumentDAO.store(conn);
                cdId = cd.getId();
            }
        }

        if (specialExamination.getCheckType().equals("1")) {
            specialExamination.setCheckType("一类(涉化)");
        }
        if (specialExamination.getCheckType().equals("2")) {
            specialExamination.setCheckType("二类(机械)");
        }
        if (specialExamination.getCheckType().equals("3")) {
            specialExamination.setCheckType("三类(文化)");
        }
        //设置检查人
        specialExamination.setUser(shareService.getUser());
        //设置学期
        specialExamination.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));
        //设置创建时间
        String creationDate = request.getParameter("creationDate");
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(creationDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        specialExamination.setCreationDate(calendar);
        //学院
        specialExamination.setAcademyNumber(acno);
        //保存记录
        SpecialExamination op = specialExaminationDAO.store(specialExamination);
        //附件
        CommonDocument dd = commonDocumentDAO.findCommonDocumentById(cdId);
        dd.setSpecialExamination(op);
        commonDocumentDAO.store(dd);

        //消息推送
        Message message = new Message();
        message.setSendUser(shareService.getUserDetail().getCname());//当前登录人
        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());//当前登录人所在学院
        message.setTitle("专项检查整改：" + labAnnexService.findLabAnnexByPrimaryKey(op.getLabAnnex().getId()).getLabName());
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(Calendar.getInstance());
        String content = "";
        content = "专项检查查看";
        content += "<a  href='/" + pConfig.PROJECT_NAME + "/examineSpecialExamination?sEId=" + op.getId();
        content += "'>点击进行查看</a>";
        message.setContent(content);
        message.setTage(2);
//        List<User> colUsers = shareService.findUserByQuery(-1, "COLLEGELEADER");//院长
//        List<User> preUsers = shareService.findUserByQuery(-1, "PREEXTEACHING");//副院长
//        List<User> excUsers = shareService.findUserByQuery(-1, "EXCENTERDIRECTOR");//实验中心主任
        Set<User> users = new HashSet<>();
        List<User> colUsers = shareService.findAuthByAuthNameAndAcademy("COLLEGELEADER", op.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber());//院长
        List<User> preUsers = shareService.findAuthByAuthNameAndAcademy("PREEXTEACHING", op.getLabAnnex().getLabCenter().getSchoolAcademy().getAcademyNumber());//副院长
        User excUsers = op.getLabAnnex().getLabCenter().getUserByCenterManager();//实验中心主任
        List<User> fullUsers = shareService.findUsersByAuthorityName("FULLTIMEMANAGER");//专职管理员
        users.addAll(colUsers);
        users.addAll(preUsers);
        users.add(excUsers);
        colUsers.addAll(fullUsers);
        for (User u : users) {
            message.setUsername(u.getUsername());
            messageDAO.store(message);
        }

        return "redirect:/specialExamination?currpage=1";
    }

    /*****************************************************************
     * @专项检查-查看
     * @作者：赵昶
     * @日期：2017-10-10
     *****************************************************************/
    @RequestMapping("/examineSpecialExamination")
    public ModelAndView examineSpecialExamination(HttpServletRequest request, @RequestParam int sEId) {
        ModelAndView mav = new ModelAndView();
        SpecialExamination specialExamination = specialExaminationDAO.findSpecialExaminationById(sEId);

        mav.addObject("specialExamination", specialExamination);

        //图片URL
        String sql = "select s from CommonDocument s where 1=1";
        sql += " and s.specialExamination=" + specialExamination.getId();
        List<CommonDocument> commonDocuments = commonDocumentDAO.executeQuery(sql);
        mav.addObject("url", commonDocuments.get(0).getDocumentUrl());
        //网址
        StringBuffer requestURL = request.getRequestURL();
        requestURL.append("?sEId=" + sEId);
        mav.addObject("requestURL", requestURL);

        mav.setViewName("routineInspection/examineSpecialExamination.jsp");
        return mav;
    }

    /*****************************************************************
     * @专项检查-复查
     * @作者：张愉
     * @日期：2018-3-7
     *****************************************************************/
    @RequestMapping("/reviewExamineSpecialExamination")
    public ModelAndView reviewExamineSpecialExamination(HttpServletRequest request, @RequestParam int sEId) {
        ModelAndView mav = new ModelAndView();
        SpecialExamination specialExamination = specialExaminationDAO.findSpecialExaminationById(sEId);

        mav.addObject("specialExamination", specialExamination);

        //图片URL
        String sql = "select s from CommonDocument s where 1=1";
        sql += " and s.specialExamination=" + specialExamination.getId();
        List<CommonDocument> commonDocuments = commonDocumentDAO.executeQuery(sql);
        mav.addObject("url", commonDocuments.get(0).getDocumentUrl());
        //网址
        StringBuffer requestURL = request.getRequestURL();
        requestURL.append("?sEId=" + sEId);
        mav.addObject("requestURL", requestURL);
        mav.addObject("sEId", sEId);
        mav.setViewName("routineInspection/reviewExamineSpecialExamination.jsp");
        return mav;
    }

    /*****************************************************************
     * @return
     * @专项检查-復查-保存
     * @作者：张愉
     * @日期：2018-3-7
     *****************************************************************/
    @RequestMapping("/saveReviewExamineSpecialExamination")
    public String saveReviewExamineSpecialExamination(HttpServletRequest request, HttpServletResponse response, @RequestParam int sEId, @ModelAttribute("selected_academy") String acno) throws ParseException {
        SpecialExamination specialExamination = specialExaminationDAO.findSpecialExaminationById(sEId);
        //设置检查人
        specialExamination.setReviewUser(shareService.getUser());
        //设置复查时间
        String reviewTime = request.getParameter("reviewTime");
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(reviewTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        specialExamination.setReviewTime(calendar);
        //复查情况
        specialExamination.setReviewExam(request.getParameter("reviewExam"));
        //保存记录
        SpecialExamination op = specialExaminationDAO.store(specialExamination);
        return "redirect:/specialExamination?currpage=1";
    }


    /**********************************************
     * 功能：专项检查-word导出方法
     * 作者：张愉
     * 日期：2018-03-09
     ***********************************************/
    @RequestMapping("/casualInspection/exportExamineSpecialExamination")
    public void exportExamineSpecialExamination(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer specialExaminationId, @ModelAttribute("selected_academy") String acno)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, Object> map = new HashMap<String, Object>();
        SpecialExamination specialExamination = specialExaminationDAO.findSpecialExaminationById(specialExaminationId);
        // 中心所属学院
        String centername = "未填写";
        if(specialExamination.getAcademyNumber()!=null && !specialExamination.getAcademyNumber().equals("")){
            centername = shareService.findSchoolAcademyByPrimaryKey(specialExamination.getAcademyNumber()).getAcademyName();
        }
        map.put("labCenter", centername);//所属单位
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(specialExamination.getCreationDate().getTime());
        map.put("creationDate", dateStr);//检查时间
        if (specialExamination.getParticipant() != null) {
            String[] users = specialExamination.getParticipant().split(",");
            StringBuffer username = new StringBuffer("");
            for (String temp:users){
                username.append(temp+" ");
            }
            map.put("username", username);//参加检查人员
        } else {
            map.put("username", "");
        }
        if (specialExamination.getCheckContent() != null) {
            map.put("checkContent", specialExamination.getCheckContent());//检查内容及发现问题：
        } else {
            map.put("checkContent", "");
        }
        if (specialExamination.getRectification() != null) {
            map.put("rectification", specialExamination.getRectification());//整改要求
        } else {
            map.put("rectification", "");
        }
        if (specialExamination.getUser() != null) {
            map.put("user1", specialExamination.getUser().getCname());//检查负责人
        } else {
            map.put("user1", "");
        }
        if (specialExamination.getLabAnnex() != null && specialExamination.getLabAnnex().getLabCenter()!=null
                && specialExamination.getLabAnnex().getLabCenter().getUserByCenterManager()!=null) {// 被检查部门的实验中心主任
            map.put("user2", specialExamination.getLabAnnex().getLabCenter().getUserByCenterManager().getCname());//被检查部门负责人
        } else {
            map.put("user2", "");
        }
        if (specialExamination.getReviewExam() != null) {
            map.put("reviewExam", specialExamination.getReviewExam());//复查整改情况
        } else {
            map.put("reviewExam", "");
        }
        if (specialExamination.getReviewUser() != null) {
            map.put("reviewUser", specialExamination.getReviewUser().getCname());//复查人
        } else {
            map.put("reviewUser", "");
        }
        if (specialExamination.getReviewTime() != null) {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String reviewTime = sdf2.format(specialExamination.getReviewTime().getTime());
            map.put("reviewTime", reviewTime);//复查时间
        } else {
            map.put("reviewTime", "");
        }
        //检查图片
        map.put("image", "");
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类WordGenerator的createDoc方法生成Word文档
            file = WordGenerator.createDoc(map, "examineSpecialExamination", this.getClass().getClassLoader().getResource("/").getPath()
            );
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为testFor.doc
            if (specialExamination.getLabRoom() != null) {
                response.setHeader("Content-disposition", "attachment;filename=" + new String((specialExamination.getLabRoom().getLabRoomName() + "实验室安全记录检查.doc").getBytes("GBK"), "ISO-8859-1"));
            } else {
                response.setHeader("Content-disposition", "attachment;filename=" + new String(("实验室安全记录检查.doc").getBytes("GBK"), "ISO-8859-1"));
            }
            out = response.getOutputStream();
            byte[] buffer = new byte[512];    // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if (fin != null) fin.close();
            if (out != null) {
                out.flush();
                out.close();
            }
            if (file != null) file.delete();    // 删除临时文件
        }
    }

    /****************************************************************************
     * @description: 预览pdf文件
     * @author: 张德冰
     * @date: 2019-01-04
     ****************************************************************************/
    @SystemServiceLog("预览pdf")
    @ResponseBody
    @RequestMapping("/tcoursesite/showFileForspeE")
    public ModelAndView showFileForspeE(@RequestParam Integer id,HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        //id对应的文档
        String sql = "select s from CommonDocument s where 1=1";
        sql += " and s.specialExamination=" + id;
        List<CommonDocument> commonDocuments = commonDocumentDAO.executeQuery(sql);
        mav.addObject("cmmonDocument", commonDocuments.get(0));
        mav.setViewName("routineInspection/showFileForspeE.jsp");
        return mav;
    }
}