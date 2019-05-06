package net.zjcclims.web.teaching;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.ConvertUtil;
import net.zjcclims.service.common.ShareService;

import net.zjcclims.service.dictionary.CDictionaryService;
import net.zjcclims.service.lab.LabCenterService;
import net.zjcclims.service.operation.OperationService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.SchoolCourseInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/******************************************************************************************
 * 功能：课程站点管理模块 作者：魏诚 时间：2014-07-14
 *****************************************************************************************/
@Controller("TeachingCourseSiteController")
@SessionAttributes({"selected_courseSite","selected_academy"})
@RequestMapping("/teaching/coursesite")
public class TeachingCourseSiteController<JsonResult> {
    /**************************************************************************************
     * @初始化WebDataBinder，这个WebDataBinder用于填充被@InitBinder注释的处理 方法的command和form对象
     *
     *************************************************************************************/
    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
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
        binder.registerCustomEditor(java.util.Date.class, new org.skyway.spring.util.databinding.CustomDateEditor());
        binder.registerCustomEditor(String.class, new org.skyway.spring.util.databinding.StringEditor());
        binder.registerCustomEditor(Long.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Long.class, true));
        binder.registerCustomEditor(Double.class, new org.skyway.spring.util.databinding.NaNHandlingNumberEditor(
                Double.class, true));
    }
    @Autowired
    private OperationService operationService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private SchoolCourseInfoService schoolCourseInfoService;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private OperationOutlineDAO operationOutlineDAO;
    @Autowired
    private OperationItemDAO operationItemDAO;
    @Autowired
    private LabCenterService labCenterService;
    @Autowired
    private CommonDocumentDAO commonDocumentDAO;
    @Autowired
    private CDictionaryService cDictionaryService;
    @Autowired
    private OperationOutlineCourseDAO operationOutlineCourseDAO;
    @Autowired
    private SchoolWeekService schoolWeekService;
    @Autowired
    private LabRoomDAO labRoomDAO;
    /*********************************************************************************
     * 功能： 实验大纲管理
     * 作者：徐文
     * 日期：2016-05-27
     *********************************************************************************/
    @RequestMapping("/experimentalmanagement")
    public ModelAndView experimentalmanagement(@ModelAttribute OperationOutline operationOutline  ,@RequestParam int currpage,@ModelAttribute("selected_academy") String acno,HttpServletRequest request) {
        String[] terms=request.getParameterValues("searchterm");
        if(terms!=null){
            operationOutline.setSchoolTerm(schoolTermDAO.findSchoolTermById(Integer.parseInt(terms[0])));
        }
        ModelAndView mav = new ModelAndView();
        int pageSize=30;
        mav.addObject("newFlag", true);
        mav.addObject("operationOutline", operationOutline);

        int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        if(operationOutline.getSchoolTerm() != null
                && operationOutline.getSchoolTerm().getId() != null){
            term = operationOutline.getSchoolTerm().getId();
        }
        mav.addObject("term", term);
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerm", schoolTerms);
        //分页参数
        int totalRecords = operationService.getAllOutlineCount(operationOutline,acno);
        Map<String,Integer> pageModel = shareService.getPage(currpage, pageSize,totalRecords);
        mav.addObject("pageModel",pageModel);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("currpage", currpage);
        mav.addObject("pageSize", pageSize);
        //查找所有的实验大纲
        List<OperationOutline> Outlinelist=operationService.getOutlinelistpage(operationOutline,currpage,pageSize,acno);
        mav.addObject("Outlinelist",Outlinelist );

        mav.setViewName("/tcoursesite/experimentalmanagement.jsp");
        return mav;
    }

    /**************************************************************************************
     * @显示课程信息的主显示页面
     * @作者：魏诚
     * @日期：2014-07-14
     *************************************************************************************/
    @RequestMapping("/listSchoolCourseInfo")
    public ModelAndView listSchoolCourseInfo(@ModelAttribute SchoolCourseInfo schoolCourseInfo,
                                             @RequestParam int currpage) {
        ModelAndView mav = new ModelAndView();
        // 设置分页变量并赋值为20；
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        // 根据课程及id获取课程排课列表的计数
        int totalRecords = schoolCourseInfoService.getCountCourseInfoByQuery(schoolCourseInfo);
        mav.addObject("totalRecords", totalRecords);
        mav.addObject("schoolCourseInfo", schoolCourseInfo);
        // 获取登陆用户信息
        mav.addObject("user", shareService.getUserDetail());
        Map<String, Integer> pageModel = shareService.getPage(currpage, pageSize, totalRecords);
        mav.addObject("pageModel", pageModel);
        // 将currpage映射到page，用来获取当前页的页码
        mav.addObject("page", currpage);
        // 维护自建课程
        mav.addObject("schoolCourseInfoList",
                schoolCourseInfoService.getCourseInfoByQuery(schoolCourseInfo, 0, currpage, pageSize));
        mav.setViewName("/tcoursesite/listTCourseSite.jsp");
        return mav;
    }

    /************************************************
     *@课程库-课题列表页面
     *@作者：杨新蔚
     *@日期：2018-07-30
     ************************************************/
    @RequestMapping("/operationItemList")
    public ModelAndView chaptersList(HttpServletRequest request,@ModelAttribute OperationItem operationItem,
                                     @RequestParam String courseNumber,Integer currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = 20;
        int totalRecords =operationService.countOperationItemByCourseNumber(operationItem,courseNumber);
        List<OperationItem> operationItems = operationService.findOperationItemByCourseNumber(operationItem,courseNumber, currpage, pageSize);
        Map<String,Integer> pageModel =shareService.getPage(currpage,pageSize,totalRecords);
        mav.addObject("operationItems", operationItems);
        mav.addObject("pageModel", pageModel);
        //当前登录人
        User user = shareService.getUser();
        mav.addObject("user", user);
        mav.addObject("courseNumber", courseNumber);
        mav.addObject("operationItem", operationItem);
        mav.setViewName("/tcoursesite/operationItemList.jsp");
        return mav;
    }

    /*********************************************************************************
     * 功能:查看大纲内容
     * 作者：徐文
     * 日期：2016-05-27
     ********************************************************************************/
    @RequestMapping("/checkout")
    public ModelAndView checkout(@RequestParam int idkey,@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        OperationOutline operationOutline=operationService.getoperationoutlineinfor(idkey);
        if(operationOutline != null) {
            Set<User> operationUsers = operationOutline.getOperationUser();
            if(operationUsers != null && operationUsers.size() > 0) {
                String teachers = "";
                for(User user : operationUsers) {
                    teachers += user.getCname() + " ";
                }
                if(operationOutline.getExtraTeacher() != null) {
                    if(teachers.contains(operationOutline.getExtraTeacher())){
                        operationOutline.setExtraTeacher(teachers);
                    }else{
                        if(operationOutline.getExtraTeacher().contains(teachers)){

                        }else{
                            operationOutline.setExtraTeacher(teachers+operationOutline.getExtraTeacher());
                        }
                    }



                }else {
                    operationOutline.setExtraTeacher(teachers);
                }
            }
        }
        mav.addObject("infor", operationOutline);
        List<MOutlineCourse> mOutlineCourses=operationService.findMoutlineCourseByOutlineId(operationOutline.getId());
        List<MOutlineCourse> bixiuList=new ArrayList<MOutlineCourse>();
        //找到必修的
        for(MOutlineCourse m:mOutlineCourses){
            if(m.getFlag().equals("0")){
                bixiuList.add(m);
            }
        }
        mav.addObject("bixiuList",bixiuList);
        //找到选修的并且分类
        List<String> xuanxiuNameList=new ArrayList<String>();
        List<Integer> xuanxiuCombineList=new ArrayList<Integer>();
        for(MOutlineCourse m:mOutlineCourses){
            if(m.getFlag().equals("1")){
                xuanxiuNameList.add(m.getSchoolCourseInfo().getCourseName());
                xuanxiuCombineList.add(Integer.parseInt(m.getCombine()));
            }
        }


        //展示选修的字段
        String xuanxiuShow="";
        for(int i=0;i<xuanxiuCombineList.size();i++){
            if(i<xuanxiuCombineList.size()-1){
                if(xuanxiuCombineList.get(i)==xuanxiuCombineList.get(i+1)){
                    xuanxiuShow=xuanxiuShow+xuanxiuNameList.get(i)+"/";
                }else{
                    xuanxiuShow=xuanxiuShow+xuanxiuNameList.get(i)+"；";
                }

            }
        }
        if(xuanxiuNameList.size()>0)
            xuanxiuShow+=xuanxiuNameList.get(xuanxiuNameList.size()-1);

        mav.addObject("xuanxiuShow",xuanxiuShow);

        // mav.addObject("xuanxiuNameList",xuanxiuNameList);
        //mav.addObject("xuanxiuCombineList",xuanxiuCombineList);

        mav.addObject("mOutlineCourses",mOutlineCourses);
        //operationOutlineCourses
        List<OperationOutlineCourse> operationOutlineCourses=operationService.findOperationOutlineCourseByOutlineId(operationOutline.getId());
        mav.addObject("operationOutlineCourses",operationOutlineCourses);
        mav.setViewName("operationprogress/checout.jsp");
        return mav;
    }
    /*********************************************************************************
     * 功能:新建大纲内容
     * 作者：徐文
     * 日期：2016-05-30
     ********************************************************************************/
    @RequestMapping("/newoperationproject")
    public ModelAndView newoperationproject(@ModelAttribute("selected_academy") String acno) {
        ModelAndView mav = new ModelAndView();
        OperationOutline OperationOutline= new OperationOutline();
        List<SchoolCourseInfo> schoolCourseInfoLists= operationService.getSchoolCourseInfo();
        mav.addObject("schoolCourseInfoLists",schoolCourseInfoLists);
        OperationOutline.setSchoolAcademy(shareService.getUser().getSchoolAcademy());
        mav.addObject("operationOutline",OperationOutline);
        Set<SchoolMajor> majorsEdit = OperationOutline.getSchoolMajors();
        mav.addObject("majorsEdit",majorsEdit);
        Set<User> operationUser = OperationOutline.getOperationUser();
        mav.addObject("operationUser",operationUser);
        Set<CDictionary> property = OperationOutline.getCDictionarys();
        mav.addObject("property",property);
        //查找教师
        mav.addObject("allTeachers", shareService.findAllTeacheres());
        //查找已经有的大纲,课程
        Set<OperationOutline> outlineExistList=operationOutlineDAO.findAllOperationOutlines();
        List<SchoolCourseInfo> schoolCourseInfoExistList=new ArrayList<SchoolCourseInfo>();
        for(OperationOutline o:outlineExistList){
            schoolCourseInfoExistList.add(o.getSchoolCourseInfoByClassId());
        }
        Map<String,String> allSchoolCourseInfoList=operationService.getschoolcouresMap(acno);
        Iterator<Map.Entry<String, String>> it = allSchoolCourseInfoList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            for(SchoolCourseInfo s:schoolCourseInfoExistList){
                if(entry!=null)
                    if(s!=null)
                        if(entry.getKey().equals(s.getCourseNumber())){
                            allSchoolCourseInfoList.put(entry.getKey(), "");
                        }
            }

        }
        Map<String,String> allSchoolCourseInfoList1=operationService.getschoolcouresMap1();
        Iterator<Map.Entry<String, String>> it1 = allSchoolCourseInfoList1.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, String> entry1 = it1.next();
            for(SchoolCourseInfo s:schoolCourseInfoExistList){
                if(entry1!=null)
                    if(s!=null)
                        if(entry1.getKey().equals(s.getCourseName())){
                            allSchoolCourseInfoList1.put(entry1.getKey(), "");
                        }
            }

        }
        //课程编号
        mav.addObject("schoolCourseInfoMap1", allSchoolCourseInfoList);
        //课程名称
        mav.addObject("schoolCourseInfoMap2", allSchoolCourseInfoList1);


        //查找登录人所在学院课程
        //mav.addObject("schoolCourseInfoMap", allSchoolCourseInfoList);

        mav.addObject("schoolCourseInfoMap", operationService.getschoolcouresMap(acno));
        //查找登录人所在的学院专业
        mav.addObject("schoolmajer", operationService.getschoolmajerSet(acno));
        //查找学分
        mav.addObject("operationscareMap", operationService.getcoperationscareMap());
        //查找登录人所在学院的开课学院
        mav.addObject("operationstartschooleMap", operationService.getoperationstartschooleMap(acno));
        //查找开课性质
        List<CDictionary> m=operationService.getcommencementnatureSet();
        mav.addObject("commencementnaturemap", operationService.getcommencementnatureSet());
        //获取项目卡
        mav.addObject("operationItem", operationService.getoperationItemlist());
        mav.addObject("isNew", 1);
        //所有学期
        //mav.addObject("schoolTerms",shareService.getTermsMap());
        int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        mav.addObject("term", term);
        // 获取学期列表
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerm", schoolTerms);
        mav.setViewName("operationprogress/newoperationproject.jsp");
        return mav;
    }
    /*********************************************************************************
     * 功能:保存item
     * 作者：徐文
     * 日期：2016-05-31
     ********************************************************************************/
    @RequestMapping("/getuserprojectitems")
    public @ResponseBody String  getuserprojectitems(@RequestParam String projectitems){
        //System.out.println(projectitems);
        String stsr="";
        Integer ss=0;
        Integer aa=0;
        if(projectitems.length()>0){
            String[] str=projectitems.split(",");

            for (String string : str) {
                if(string!=null && string!=""){
                    OperationItem s=  operationItemDAO.findOperationItemById(Integer.parseInt(string));
                    if(s!=null){
                        String cop=null;
                        if(s.getCDictionaryByLpCategoryMain()==null){
                            cop="无";
                        }else{
                            cop=s.getCDictionaryByLpCategoryMain().getCName();
                        }
                        String sa=null;
                        if(s.getLabRooms()!=null){
                            for (LabRoom sts : s.getLabRooms()) {
                                sa+=sts.getLabRoomName()+",";
                            }

                        }else{
                            sa="无";
                        }
                        aa=s.getLpDepartmentHours();
                        stsr+="<tr id='"+s.getId()+"' ><td >"+s.getLpCodeCustom()+"</td><td >"+s.getLpName()+"</td><td >"+cop+"</td><td >"+aa+"</td><td >"+ss+"</td><td >"+sa+"</td><td ><input class='savebutton'    id='"+s.getId()+"'  onclick='del(this.id)'   type='button' value='删除' /></td></tr>";
                    }}}
        }

        return shareService.htmlEncode(stsr);
    }
    /*********************************************************************************
     * 功能:保存大纲内容
     * 作者：徐文
     * 日期：2016-05-31
     ********************************************************************************/
    @RequestMapping("/saveoperationoutline")
    public String  saveoperationoutline(Integer id,@ModelAttribute OperationOutline operationOutline ,HttpServletRequest request,@ModelAttribute("selected_academy") String acno) {


        //获取面向专业  多对多
        //String schoolMajors=request.getParameter("schoolMajorsa");
        //获取课程性质 多对多

        String[] commencementnaturemaps = request.getParameterValues("commencementnaturemap");
        String[] schoolMajors = request.getParameterValues("schoolMajorsa");
        String[] projectitrms = request.getParameter("projectitrms").split(",");
        String[] operationOutlineTeacher =request.getParameterValues("operationOutlineTeacher");
        String termId[]=request.getParameterValues("searchterm");
        //选修
        String[] electiveCourses=request.getParameterValues("electiveCourse");
        String[] requiredCourses=request.getParameterValues("requiredCourse");

        if(!acno.equals("-1")) {
            operationOutline.setSchoolAcademy(shareService.findSchoolAcademyByPrimaryKey(acno));
        }
        operationOutline.setSchoolTerm(schoolTermDAO.findSchoolTermById(Integer.parseInt(termId[0])));
        String docment=request.getParameter("docment");
        OperationOutline op=operationService.saveoperationoutline(operationOutline,schoolMajors,
                commencementnaturemaps,projectitrms,operationOutlineTeacher);
        //保存本大纲的选修与必修
        operationService.savemoutlinecourse(op,requiredCourses,electiveCourses);
        if(docment!=null && docment!=""){
            String[] str= docment.split(",");
            for (String string : str) {
                if(string!=null && string!=""){
                    CommonDocument dd= commonDocumentDAO.findCommonDocumentByPrimaryKey(Integer.parseInt(string));
                    if(dd!=null){
                        dd.setOperationOutline(op);
                        commonDocumentDAO.store(dd);
                    }
                }
            }
        }
        String[] courseId=request.getParameterValues("courseId");
        String[] content =request.getParameterValues("content");
        String[] week =request.getParameterValues("week");
        String[] courseHour=request.getParameterValues("courseHour");
        String[] cname=request.getParameterValues("cname");
        String[] courseTime=request.getParameterValues("courseTime");

        int n=0;
        if(courseTime!=null)
            n =courseTime.length;
        OperationOutlineCourse operationOutlineCourse = new OperationOutlineCourse();
        if(content!=null&&courseTime!=null){
            for(int i=0;i<n;i++){
                if(courseId!=null) {
                    if (courseId[i] != null) {
                        operationOutlineCourse.setId(Integer.valueOf(courseId[i]));
                    }
                }
                operationOutlineCourse.setCourseContent(content[i]);
                operationOutlineCourse.setWeek(Integer.valueOf(week[i]));
                operationOutlineCourse.setOperationOutline(op);
                operationOutlineCourse.setCourseTime(courseTime[i]);
                operationOutlineCourse.setcDictionary(cDictionaryService.finCDictionaryByPrimarykey(Integer.valueOf(cname[i])));
                operationOutlineCourseDAO.store(operationOutlineCourse);
            }
        }
        return "redirect:/operation/experimentalmanagement?currpage=1";
    }

    /*********************************************************************************
     * 功能:多文件上传
     * 作者：徐文
     * 日期：2016-06-01
     ********************************************************************************/
    @RequestMapping("/uploaddnolinedocment")
    public @ResponseBody String uploaddnolinedocment(HttpServletRequest request, HttpServletResponse response, BindException errors, Integer id) throws Exception {
        String ss=operationService.uploaddnolinedocment(request, response,id);
        return shareService.htmlEncode(ss);
    }
    /****************************************************************************
     * 功能：删除大纲文件
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @RequestMapping("/delectdnolinedocment")
    public @ResponseBody String delectdnolinedocment(@RequestParam Integer idkey) throws Exception {
        CommonDocument d=commonDocumentDAO.findCommonDocumentById(idkey);
        commonDocumentDAO.remove(d);
        return "ok";
    }
    /****************************************************************************
     * 功能：下载大纲文件
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @RequestMapping("/downloadfile ")
    public void donloudfujian(HttpServletRequest request, HttpServletResponse response,@RequestParam int idkey) throws Exception {
        CommonDocument d=commonDocumentDAO.findCommonDocumentById(idkey);
        String filename = d.getDocumentName();

        String path=d.getDocumentUrl();
        try{
            File sendPath = new File(path);
            FileInputStream fis = new FileInputStream(sendPath);
            response.setCharacterEncoding("utf-8");
            //解决上传中文文件时不能下载的问题
            response.setContentType("multipart/form-data;charset=UTF-8");
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                filename = new String(filename.getBytes("UTF-8"),"ISO8859-1");// firefox浏览器
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");// IE浏览器
            } else {
                filename = URLEncoder.encode(filename, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment;fileName="+filename);
            OutputStream fos = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while((count = fis.read(buffer))>0){
                fos.write(buffer,0,count);
            }
            fis.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /****************************************************************************
     * 功能：搜索项目卡
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/getitem")
    public @ResponseBody String getitem(String nameop) {

        List<OperationItem> d= operationService.getitem(nameop);
        String str="";
        if(d.size()>0){
            for (OperationItem itm : d) {
                str+="<tr align='center'><td align='center'><input type='checkbox' value='"+itm.getId()+"'></td><td align='center'>"+itm.getLpCodeCustom()+"</td><td align='center'>"+itm.getLpName()+"</td><td align='center'></td></tr>";
            }
        }
        return shareService.htmlEncode(str);
    }
    /****************************************************************************
     * 功能：导出实验室大纲
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
    @RequestMapping("/outline/exportOutline")
    public void export(@ModelAttribute OperationOutline operationOutline ,HttpServletRequest request, HttpServletResponse response,@RequestParam int page,@ModelAttribute("selected_academy") String acno) throws Exception {

        int pageSize=30;

        List<OperationOutline> findList=operationService.getOutlinelistpage(operationOutline,page,pageSize,acno);
        List<Map> list=new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (OperationOutline f : findList) {
            Map map=new HashMap();
            map.put("1", f.getLabOutlineName());//dagangmingcheng
            if(f.getUser().getCname() != null&&f.getUser().getCname() != null ){
                map.put("2", f.getUser().getCname());//jiaoshi
            }
            if(f.getSchoolCourseInfoByClassId()!=null&& f.getSchoolCourseInfoByClassId().getCourseNumber()!=null){
                map.put("3",   f.getSchoolCourseInfoByClassId().getCourseNumber());//kechengbiaohao
            }
            if(f.getSchoolCourseInfoByClassId()!=null && f.getSchoolCourseInfoByClassId().getCourseName()!=null){
                map.put("4",  f.getSchoolCourseInfoByClassId().getCourseName());//kechengmingcheng
            }
            if(f.getSchoolAcademy()!=null&&f.getSchoolAcademy().getAcademyName()!=null){
                map.put("5", f.getSchoolAcademy().getAcademyName());//xueyuan
            }
            list.add(map);
        }
        String title = "实验大纲列表";
        String[] hearders = new String[] {"大纲名称","教师", "课程编号","课程名称", "学院"};//表头数组
        String[] fields = new String[] {"1", "2","3","4","5"};//Financialresources对象属性数组
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title,shareService.getUser().getCname(), td);

    }


    /*******************************
     * @功能：课程大纲单行数据导出
     * @作者：张秦龙
     * @时间：2017-12-5
     ****************************/
    @RequestMapping("/printoutline")
    public void printoutline(@RequestParam int idkey, HttpServletRequest request,HttpServletResponse response) throws Exception{
        operationService.exportOutLine(idkey,request,response);
    }

    /****************************************************************************
     * 功能：删除实验室大纲
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @RequestMapping("/delectuotline")
    public String  delectuotline(@RequestParam int idkey) {
        operationService.delectloutline(idkey);
        return "redirect:/teaching/coursesite/experimentalmanagement?currpage=1";
    }
    /****************************************************************************
     * 功能：编辑实验室大纲
     * 作者：徐文
     * 日期：2016-06-01
     ****************************************************************************/
    @RequestMapping("/editoutline")
    public ModelAndView newoperationproject(@RequestParam int idkey,@ModelAttribute("selected_academy") String acno) {
        /**
         *
         */
        ModelAndView mav = new ModelAndView();
        // 查找被编辑的大纲对象
        OperationOutline operationOutline=operationService.getoperationoutlineinfor(idkey);
        mav.addObject("operationOutline",operationOutline);
        //必修
        List<SchoolCourseInfo> bixiuList=new ArrayList<SchoolCourseInfo>();
        //选修
        List<MOutlineCourse> xuanxiuList=new ArrayList<MOutlineCourse>();
        //找到必修的
        List<MOutlineCourse> mOutlineCourses=operationService.findMoutlineCourseByOutlineId(operationOutline.getId());
        for(MOutlineCourse m:mOutlineCourses){
            if(m.getFlag().equals("0")){
                bixiuList.add(m.getSchoolCourseInfo());
            }else{
                xuanxiuList.add(m);
            }
        }
        List<List<SchoolCourseInfo>> xuanxiuListList=new ArrayList<List<SchoolCourseInfo>>();
        int maxCombine=-1;
        if(xuanxiuList.size()==0){
            maxCombine=-1;
        }else{
            maxCombine=Integer.parseInt(xuanxiuList.get(xuanxiuList.size()-1).getCombine());

        }
        for(int i=0;i<=maxCombine;i++){
            List<SchoolCourseInfo> xuanxiuGroup=new ArrayList<SchoolCourseInfo>();
            for(MOutlineCourse m:xuanxiuList){
                if(m.getCombine().equals(String.valueOf(i))){
                    xuanxiuGroup.add(m.getSchoolCourseInfo());
                }
            }
            if(xuanxiuGroup.size()<4){
                int needToFill=4-xuanxiuGroup.size();
                for(int j=0;j<needToFill;j++){
                    SchoolCourseInfo mnull=new SchoolCourseInfo();
                    xuanxiuGroup.add(mnull);
                }
            }
            xuanxiuListList.add(xuanxiuGroup);
        }
        mav.addObject("bixiuList",bixiuList);
        mav.addObject("xuanxiuList",xuanxiuList);
        mav.addObject("xuanxiuListList",xuanxiuListList);


        // 被编辑对象的多选框内容
        Set<SchoolMajor> majorsEdit = operationOutline.getSchoolMajors();
        mav.addObject("majorsEdit",majorsEdit);
        Set<CDictionary> property = operationOutline.getCDictionarys();
        mav.addObject("property",property);
        Set<OperationItem> item = operationOutline.getOperationItems();
        mav.addObject("item",item);
        //所有学期
//		mav.addObject("schoolTerm"s,shareService.getTermsMap());
        List<SchoolTerm> schoolTerms = outerApplicationService.getSchoolTermList();
        mav.addObject("schoolTerm", schoolTerms);
        //查找登录人所在学院课程
        mav.addObject("schoolCourseInfoMap", operationService.getschoolcouresMap(acno));
        //查找登录人所在的学院专业
        mav.addObject("schoolmajer", operationService.getschoolmajerSet(acno));
        //查找学分
        mav.addObject("operationscareMap", operationService.getcoperationscareMap());
        //查找登录人所在学院的开课学院
        mav.addObject("operationstartschooleMap", operationService.getoperationstartschooleMap(acno));
        //查找开课性质
        mav.addObject("commencementnaturemap", operationService.getcommencementnatureSet());
        //获取项目卡
        mav.addObject("operationItem", operationService.getoperationItemlist());
        mav.addObject("isNew", 0);
        //查找教师
        mav.addObject("allTeachers", shareService.findAllTeacheres());
        Set<User> operationUser = operationOutline.getOperationUser();
        mav.addObject("operationUser",operationUser);
        //得到教学进度安排
        List<OperationOutlineCourse> operationOutlineCourses=operationService.findOperationOutlineCourseByOutlineId(operationOutline.getId());
        mav.addObject("operationOutlineCourses",operationOutlineCourses);
        //查找已经有的大纲,课程
        Set<OperationOutline> outlineExistList=operationOutlineDAO.findAllOperationOutlines();
        List<SchoolCourseInfo> schoolCourseInfoExistList=new ArrayList<SchoolCourseInfo>();
        for(OperationOutline o:outlineExistList){
            schoolCourseInfoExistList.add(o.getSchoolCourseInfoByClassId());
        }
        Map<String,String> allSchoolCourseInfoList=operationService.getschoolcouresMap(acno);
        Iterator<Map.Entry<String, String>> it = allSchoolCourseInfoList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            for(SchoolCourseInfo s:schoolCourseInfoExistList){
                if(entry!=null)
                    if(s!=null)
                        if(entry.getKey().equals(s.getCourseNumber())){
                            allSchoolCourseInfoList.put(entry.getKey(), "");

                        }
            }

        }
        Map<String,String> allSchoolCourseInfoList1=operationService.getschoolcouresMap1();
        Iterator<Map.Entry<String, String>> it1 = allSchoolCourseInfoList1.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry<String, String> entry1 = it1.next();
            for(SchoolCourseInfo s:schoolCourseInfoExistList){
                if(entry1!=null)
                    if(s!=null)
                        if(entry1.getKey().equals(s.getCourseName())){
                            allSchoolCourseInfoList1.put(entry1.getKey(), "");

                        }
            }

        }
        //allSchoolCourseInfoList.put(operationOutline.getSchoolCourseInfoByClassId().getCourseNumber(), operationOutline.getSchoolCourseInfoByClassId().getCourseName()+" "+operationOutline.getSchoolCourseInfoByClassId().getCourseNumber());
        //课程编号
        mav.addObject("schoolCourseInfoMap1", allSchoolCourseInfoList);
        mav.addObject("schoolCourseInfoMap2", allSchoolCourseInfoList1);
        mav.setViewName("operationprogress/newoperationproject.jsp");
        return mav;
    }

    /**
     * 新建项目时保存实验项目材料使用记录
     * @author hly
     * 2015.08.10
     */
    @RequestMapping("/saveItemMaterialRecordNewAfterAudit")
    public String saveItemMaterialRecordNewAfterAudit(@ModelAttribute OperationItemMaterialRecord operationItemMaterialRecord,@RequestParam String lp_name,@RequestParam int term_id,@RequestParam String course_number,
                                                      @RequestParam String lp_create_user,@RequestParam int page,@RequestParam int isMine,@RequestParam int status,
                                                      @RequestParam int orderBy,@RequestParam int id){
        operationService.saveItemMaterialRecord(operationItemMaterialRecord);
        return "redirect:/operationRest/listItemMaterialRecordRest/" + lp_name + "/"+ term_id + "/" + course_number + "/" + lp_create_user + "/"+page+ "/1/"+status+"/"+orderBy+"/" +id+"/";
    }

    /*********************************************************************************
     * @description:实验项目多文件上传
     * @author：郑昕茹
     * @date：2016-11-09
     ********************************************************************************/
    @RequestMapping("/uploadItemdocument")
    public @ResponseBody String uploadItemdocument(HttpServletRequest request, HttpServletResponse response, BindException errors,Integer id) throws Exception {
        String ss=operationService.uploadItemdocument(request, response, id);
        return shareService.htmlEncode(ss);
    }


    /**
     * 保存实验项目设备
     * @author hly
     * 2015.08.19
     */
    @RequestMapping("/saveItemDeviceRest")
    public @ResponseBody String saveItemDeviceRest(@RequestParam int itemId, String category, String ids){

        operationService.saveItemDevice(itemId, category, ids);

//		return "redirect:/operation/listItemDevice?itemId="+itemId+"&currpage=1";
        return "success";//2015-09-23 16:41:46   贺子龙  修改跳转页面
    }

    /**
     * 删除实验项目设备--自己删除
     * @author hly
     * 2015.08.20
     */
    @RequestMapping("/deleteItemDeviceRest")
    public @ResponseBody String deleteItemDeviceRest(@RequestParam int itemDeviceId){
        operationService.deleteItemDevice(itemDeviceId);

        return "success";
    }


    /**
     * 删除实验项目材料使用记录--自己删除
     * @author hly
     * 2015.08.10
     */
    @RequestMapping("/deleteItemMaterialRecordRest")
    public @ResponseBody String deleteItemMaterialRecordRest(@RequestParam int mrId){
        operationService.deleteItemMaterialRecord(mrId);
        return "success";
    }
    /****************************************************************************
     * 功能：课程内容添加
     * 作者：戴昊宇
     * 日期：2017-09-11
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/getcourse")
    public void getcourse(Integer count,HttpServletRequest request, HttpServletResponse response)  throws Exception{
        //获得当前学期下的周次
        Calendar time = Calendar.getInstance();
        SchoolTerm term = shareService.getBelongsSchoolTerm(time);
        List<SchoolWeek> findallschoolweekbytermId = schoolWeekService.findallschoolweekbytermId(term.getId());
        Set<LabRoom> rooms = labRoomDAO.findAllLabRooms();
        List<CDictionary> cDictionaries=cDictionaryService.findCurriculumNature();
        String str="";
        str+="<tr id='cou"+count+"' name='arrangement'><td>课次：<input id='courseTime' name='courseTime' value='"+count+"'></td>";
        //课程性质
        str+="<td>课程性质：<select id='cname' name='cname' onchange=showItems("+count+")>";
        for(CDictionary c:cDictionaries){
            str+="<option value="+c.getId()+">"+c.getCName()+"</option>";
        }
        str+="</select></td>";
        str+="<td>课程内容：<input id='content' name='content'></td><td>周次：<select id='week' name='week'>";
        str+="<option value="+count+">第"+count+"次</option>";
        for(SchoolWeek w:findallschoolweekbytermId){
            if(!w.getWeek().equals(count))
                str+="<option value="+w.getWeek()+">第"+w.getWeek()+"次</option>";
        }
        str+="</select></td>";
        str+="<td><input id='delete' type='button'value='删除' onclick='delcourse("+count+")'></td></tr>";
        response.getWriter().write(str);
    }

    /****************************************************************************
     * 功能：根据课程性质是否为实验选择课程内容
     * 作者：杨新蔚
     * 日期：2018-08-21
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/showItems")
    public void showItems(String courseCount,String cname,String courseName,HttpServletRequest request, HttpServletResponse response)  throws Exception{
        String str="";
        if(cname.contains("实验")) {
            List<OperationItem> operationItems=operationService.findOperationItemByCourseNumberAndStatus(courseName,"status_operation_item_check","3");
            //课程内容(针对实验关联)
            str += "课程内容：<select id='content' name='content' onchange='createNewItem("+ Integer.parseInt(courseCount)+")'><option value=''>请选择实验项目</option>";
            for (OperationItem oi : operationItems) {
                str += "<option value=" + oi.getLpName() + ">" + oi.getLpName() + "</option>";
            }
            str +="<option value='-1' style='color:blue'>新建实验项目</option>";
            str += "</select>";
        }else{
            str += "课程内容：<input id='content' name='content'>";
        }
        response.setContentType("text/xml;charset=UTF-8");
        response.getWriter().write(str);
    }
    /****************************************************************************
     * 功能：课程内容修改
     * 作者：廖文辉
     * 日期：2018-03-13
     ****************************************************************************/
    @SuppressWarnings("unused")
    @RequestMapping("/editcourse")
    public void editcourse(Integer courseCount,Integer id,HttpServletRequest request, HttpServletResponse response,String courseName)  throws Exception{
        //通过id取得该条进度安排
        OperationOutlineCourse operationOutlineCourse=operationOutlineCourseDAO.findOperationOutlineCourseById(id);
        String courseContent=operationOutlineCourse.getCourseContent();
        Integer week=operationOutlineCourse.getWeek();
        String  cName=operationOutlineCourse.getcDictionary().getCName();
        String courseTime = operationOutlineCourse.getCourseTime();

        //获得当前学期下的周次
        Calendar time = Calendar.getInstance();
        SchoolTerm term = shareService.getBelongsSchoolTerm(time);
        List<SchoolWeek> findallschoolweekbytermId = schoolWeekService.findallschoolweekbytermId(term.getId());
        Set<LabRoom> rooms = labRoomDAO.findAllLabRooms();
        List<CDictionary> cDictionaries=cDictionaryService.findCurriculumNature();
        String str="";
        str+="<td>课次：<input id='courseTime' name='courseTime' value='"+courseTime+"'></td>";
        str+="<input type='hidden' path='id' value="+id+" name='courseId'/>";
        //课程性质
        str+="<td>课程性质：<select id='cname' name='cname' onchange=showItems("+courseCount+")>";
        for(CDictionary c:cDictionaries){
            if(c.getCName().equals(cName)){
                str+="<option value="+c.getId()+" selected>"+c.getCName()+"</option>";

            }else{
                str+="<option value="+c.getId()+">"+c.getCName()+"</option>";
            }
        }
        str+="</select></td>";
        //课程内容
        if(cName.contains("实验")) {
            List<OperationItem> operationItems=operationService.findOperationItemByCourseNumberAndStatus(courseName,"status_operation_item_check","3");
            //课程内容(针对实验关联)
            str += "<td>课程内容：<select id='content' name='content' onchange='createNewItem("+ Integer.parseInt(courseTime)+")'><option value=''>请选择实验项目</option>";
            for (OperationItem oi : operationItems) {
                if(oi.getLpName().equals(courseContent)){
                    str += "<option value=" + oi.getLpName() + " selected>" + oi.getLpName() + "</option>";

                }else {
                    str += "<option value=" + oi.getLpName() + ">" + oi.getLpName() + "</option>";
                }
            }
            str +="<option value='-1' style='color:blue'>新建实验项目</option>";
            str += "</select>";
        }else{
            str += "<td>课程内容：<input id='content' name='content' value='"+courseContent+"'>";
        }
        str+="</td><td>周次：<select id='week' name='week'><option value="+courseTime+">第"+courseTime+"次</option>";
        for(SchoolWeek w:findallschoolweekbytermId){
            if(!w.getWeek().equals(courseTime))
                str+="<option value="+w.getWeek()+">第"+w.getWeek()+"次</option>";
        }
        str+="</select></td>";
        response.getWriter().write(str);
    }

    /**
     * 删除实验大纲教学进度安排
     * @param id
     * @return
     * 贺照易 2018-9-27
     */
    @ResponseBody
    @RequestMapping("/deleteThisOperationOutlineCourse")
    public String deleteThisOperationOutlineCourse(@RequestParam Integer id) {
        OperationOutlineCourse operationOutlineCourse=operationOutlineCourseDAO.findOperationOutlineCourseById(id);
        operationService.deleteThisOperationOutlineCourse(operationOutlineCourse);

        return "true";
    }

    /********************************
     * @功能：联动查询（课程代码和课程名称）
     * @作者：廖文辉
     * @时间：2018.03.13
     ********************************/
    @ResponseBody
    @RequestMapping("/findCourseNameByCourseNumber")
    public Map<String, String> findCourseNameByCourseNumber(@RequestParam String courseNumber){
        Map<String, String> map = new HashMap<String, String>();
        String courseNameValue = operationService.LinkCourseNumberAndCourseName(courseNumber);
        map.put("courseName", courseNameValue);
        return map;
    }

    /***********************************************************************************
     * @description：课程管理--课程大纲管理（导入教学进度）
     * @author 郑昕茹
     * @date：2016-12-19
     * **********************************************************************************/
    @RequestMapping("/importCourse")
    public ModelAndView importCourse(@RequestParam Integer idKey,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();

        String fileName = shareService.getUpdateFilePath(request);
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/");
        String filePath = logoRealPathDir + fileName;
        System.out.println(filePath);
        if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){
            operationService.importCourse(filePath,idKey);
        }
        mav.setViewName("redirect:/operation/editoutline?idkey="+idKey);
        return mav;
    }

}