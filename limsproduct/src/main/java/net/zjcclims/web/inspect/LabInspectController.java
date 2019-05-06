package net.zjcclims.web.inspect;

import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.dao.LabInspectDAO;
import net.zjcclims.dao.LabInspectSettingDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.inspect.LabInspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**************************************************
 * Description 实验室安全检查
 * @param <JsonResult>
 * @author 陈乐为 2018-10-31
 **************************************************/
@Controller("LabInspectController")
@RequestMapping("/inspect")

public class LabInspectController<JsonResult> {
    @Autowired private LabInspectDAO labInspectDAO;
    @Autowired private LabInspectSettingDAO labInspectSettingDAO;
    @Autowired private LabRoomDAO labRoomDAO;
    @Autowired private CommonDocumentDAO commonDocumentDAO;
    @Autowired private ShareService shareService;
    @Autowired private LabInspectService labInspectService;

    /**
     * Description 实验室安全检查{列表}
     * @param currpage
     * @return
     * @author 陈乐为 2018-10-31
     */
    @RequestMapping("/listLabInspect")
    public ModelAndView listLabInspect(@ModelAttribute LabInspect labInspect, @RequestParam int currpage) {
        // 新建ModelAndView对象
        ModelAndView mav = new ModelAndView();
        // 每页最大数量
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        StringBuffer hql = new StringBuffer("select i from LabInspect i where 1=1");
        // 总数
        StringBuffer hqlc = new StringBuffer("select count(i) from LabInspect i where 1=1");
        // 如果搜索框中输入的项目名称不是空或者不是空格 则追加hql条件进行模糊查询
        if (labInspect.getStandardName() != null
                && !"".equals(labInspect.getStandardName())) {
            // 根绝项目名称进行模糊查询
            hql.append(" and i.standardName like '%" + labInspect.getStandardName() + "%'");
            hqlc.append(" and i.standardName like '%" + labInspect.getStandardName() + "%'");
        }
        List<LabInspect> inspectList = labInspectDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
        int totalRecords = ((Long) labInspectDAO.createQuerySingleResult(hqlc.toString()).getSingleResult()).intValue();
        mav.addObject("inspectList", inspectList);
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("inspect", labInspect);
        mav.setViewName("/inspect/listLabInspect.jsp");
        return mav;
    }

    /**
     * Description 实验室安全检查{编辑}
     * @param idKey
     * @return
     * @author 陈乐为 2018-10-31
     */
    @RequestMapping("/editLabInspect")
    public ModelAndView editLabInspect(@RequestParam int idKey) {
        ModelAndView mav = new ModelAndView();
        LabInspect inspect = labInspectDAO.findLabInspectByPrimaryKey(idKey);
        if(inspect!=null) {
            mav.addObject("inspect", inspect);
        }else {
            mav.addObject("inspect", new LabInspect());
        }

        mav.setViewName("/inspect/editInspect.jsp");
        return mav;
    }

    /**
     * Description 保存
     * @param labInspect
     * @return
     * @author 陈乐为 2018-10-31
     */
    @RequestMapping("/saveLabInspect")
    public String saveLabInspect(@ModelAttribute LabInspect labInspect) {
        labInspectDAO.store(labInspect);
        return "redirect:/inspect/listLabInspect?currpage=1";
    }

    /**
     * Description 删除
     * @param idKey
     * @author 陈乐为 2018-10-31
     */
    @RequestMapping("/deleteLabInspect")
    public void deleteLabInspect(@RequestParam int idKey) {
        LabInspect labInspect = labInspectDAO.findLabInspectByPrimaryKey(idKey);
        labInspectDAO.remove(labInspect);
        labInspectDAO.flush();
    }
    /***************************************************************************************************/

    /**
     * Description 批次设置列表
     * @param inspectSetting
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/listInspectSetting")
    public ModelAndView listLabInspectSetting(@ModelAttribute LabInspectSetting inspectSetting, @RequestParam int currpage) {
        ModelAndView mav = new ModelAndView();
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        StringBuffer hql = new StringBuffer("select w from LabInspectSetting w where 1=1");
        StringBuffer hqlc = new StringBuffer("select COUNT(w) from LabInspectSetting w where 1=1");
        if (inspectSetting.getSemeter() != null && !"".equals(inspectSetting.getSemeter())) {
            hql.append(" and w.name like '%" + inspectSetting.getSemeter() + "%'");
            hqlc.append(" and w.name like '%" + inspectSetting.getSemeter() + "%'");
        }
        List<LabInspectSetting> inspectSettings = labInspectSettingDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
        int totalRecords = ((Long) labInspectSettingDAO.createQuerySingleResult(hqlc.toString()).getSingleResult()).intValue();
        //将定期时间和不定期时间统一转换成String类型输出
        List<String> evaluationTime=new ArrayList<String>();
        for (LabInspectSetting labInspectSetting : inspectSettings) {
            StringBuffer sb=new StringBuffer("");
            if(labInspectSetting.getIsRegular()&&(labInspectSetting.getWeek()!=null)){
                if((labInspectSetting.getWeek()/1000000)==1){
                    sb.append("周一  ");
                }
                if(((labInspectSetting.getWeek()/100000)%10)==1){
                    sb.append("周二  ");
                }
                if(((labInspectSetting.getWeek()/10000)%10)==1){
                    sb.append("周三  ");
                }
                if(((labInspectSetting.getWeek()/1000)%10)==1){
                    sb.append("周四  ");
                }
                if(((labInspectSetting.getWeek()/100)%10)==1){
                    sb.append("周五  ");
                }
                if(((labInspectSetting.getWeek()/10)%10)==1){
                    sb.append("周六  ");
                }
                if((labInspectSetting.getWeek()%10)==1){
                    sb.append("周日  ");
                }
            }else if(labInspectSetting.getStartTime()!=null&&labInspectSetting.getEndTime()!=null){
                Date d1=labInspectSetting.getStartTime().getTime();
                Date d2=labInspectSetting.getEndTime().getTime();
                sb.append((d1.getYear()+1900)+"年"+(d1.getMonth()+1)+"月"+d1.getDate()+"日"+"---"+(d2.getYear()+1900)+"年"+(d2.getMonth()+1)+"月"+d2.getDate()+"日");
            }else{
                sb.append("暂无设置评价开放时间");
            }
            evaluationTime.add(sb.toString());
        }
        mav.addObject("inspectSettings", inspectSettings);
        mav.addObject("evaluationTime",evaluationTime);
        mav.addObject("pageModel",shareService.getPage(currpage, pageSize, totalRecords));

        mav.setViewName("/inspect/listInspectSetting.jsp");
        return mav;
    }

    /**
     * Description 新建批次设置
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/newInspectSetting")
    public ModelAndView newInspectSetting() {
        ModelAndView mav = new ModelAndView();
        LabInspectSetting labInspectSetting = new LabInspectSetting();
        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        labInspectSetting.setSemeter(schoolTerm.getTermName());
        //设置当前学期学期
        String weekString="定期请选择，不定期不用选择:";
        mav.addObject("weekString",weekString);
        List<LabInspect> labInspects = labInspectService.findAllLabInspects();
        mav.addObject("labInspects", labInspects);
        mav.addObject("labInspectSetting", labInspectSetting);

        mav.setViewName("/inspect/newInspectSetting.jsp");
        return mav;
    }

    /**
     * Description 保存批次设置
     * @param labInspect
     * @param request
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/saveNewInspectSetting")
    public String saveNewInspectSetting(@ModelAttribute LabInspect labInspect,
                                        @ModelAttribute LabInspectSetting labInspectSetting,
                                        HttpServletRequest request) {
        if(request.getParameterValues("weekday")!=null){
            String[] wd=request.getParameterValues("weekday");
            //将获取的字符串数组的字符转成labEvaluationTimeSetting表属性week的int类型，
            //其中7位整数中最高位保存星期一的信息，0代表星期一不可以评价，1代表星期一可以评价，依次类推最后一位保存的是星期天
            int k=0;
            for (int i = 0; i < wd.length; i++) {
                if(wd[i].equals("1")){
                    k=k+1000000;
                }
                if(wd[i].equals("2")){
                    k=k+100000;
                }
                if(wd[i].equals("3")){
                    k=k+10000;
                }
                if(wd[i].equals("4")){
                    k=k+1000;
                }
                if(wd[i].equals("5")){
                    k=k+100;
                }
                if(wd[i].equals("6")){
                    k=k+10;
                }
                if(wd[i].equals("7")){
                    k=k+1;
                }
            }
            labInspectSetting.setWeek(k);
        }
        if(labInspectSetting.getIsRegular()!=null){
            LabInspectSetting labEva = labInspectSettingDAO.store(labInspectSetting);
            // 保存项目
            String[] standardId = request.getParameterValues("standard");
            Set<LabInspect> standards = new HashSet<LabInspect>();
            for(String tempString : standardId){
                standards.add(labInspectDAO.findLabInspectByPrimaryKey(Integer.parseInt(tempString)));
            }
            Set<LabInspect> labstandards = standards;
            for (LabInspect labRoomStandard1 : labstandards) {
                MInspectSetting m = new MInspectSetting();
                m.setLabInspect(labRoomStandard1);
                m.setBatchId(labEva.getId());
                labInspectService.saveMInspectSetting(m);
            }
        }
        return "redirect:/inspect/listInspectSetting?currpage=1";
    }

    /**
     * Description 编辑批次设置
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/editInspectSetting")
    public ModelAndView editInspectSetting(@RequestParam int idKey) {
        ModelAndView mav = new ModelAndView();
        LabInspectSetting inspectSetting = labInspectSettingDAO.findLabInspectSettingByPrimaryKey(idKey);
        StringBuffer sb=new StringBuffer("");
        if(inspectSetting.getIsRegular()&&(inspectSetting.getWeek()!=null)){
            if((inspectSetting.getWeek()/1000000)==1){
                sb.append("周一  ");
            }
            if(((inspectSetting.getWeek()/100000)%10)==1){
                sb.append("周二  ");
            }
            if(((inspectSetting.getWeek()/10000)%10)==1){
                sb.append("周三  ");
            }
            if(((inspectSetting.getWeek()/1000)%10)==1){
                sb.append("周四  ");
            }
            if(((inspectSetting.getWeek()/100)%10)==1){
                sb.append("周五  ");
            }
            if(((inspectSetting.getWeek()/10)%10)==1){
                sb.append("周六  ");
            }
            if((inspectSetting.getWeek()%10)==1){
                sb.append("周日  ");
            }
            mav.addObject("weekstring",sb.toString());
        }
        mav.addObject("inspectSetting", inspectSetting);
        // 根据批次id查询对应评价项目
        StringBuffer hql = new StringBuffer("select p from LabInspect p , MInspectSetting lrs" +
                " where lrs.labInspect.id=p.id and lrs.batchId=").append(idKey);
        List<LabInspect> labInspects = labInspectDAO.executeQuery(hql.toString(),0,-1);
        mav.addObject("labInspects", labInspects);
        // 所有评价项目
        StringBuffer hqla = new StringBuffer("select p from LabInspect p");
        List<LabInspect> labInspectList = labInspectDAO.executeQuery(hqla.toString(),0,-1);
        mav.addObject("labInspectList",labInspectList);

        mav.setViewName("/inspect/editInspectSetting.jsp");
        return mav;
    }

    /**
     * Description 保存批次设置
     * @param labInspect
     * @param inspectSetting
     * @param idKey
     * @param request
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/saveInspectSetting")
    public String saveInspectSetting(@ModelAttribute LabInspect labInspect, @ModelAttribute LabInspectSetting inspectSetting,
                                     @RequestParam int idKey, HttpServletRequest request) {
        if(request.getParameterValues("weekday")!=null){
            String[] wd=request.getParameterValues("weekday");
            //将获取的字符串数组的字符转成labEvaluationTimeSetting表属性week的int类型，
            //其中7位整数中最高位保存星期一的信息，0代表星期一不可以评价，1代表星期一可以评价，依次类推最后一位保存的是星期天
            int k=0;
            for (int i = 0; i < wd.length; i++) {
                if(wd[i].equals("1")){
                    k=k+1000000;
                }
                if(wd[i].equals("2")){
                    k=k+100000;
                }
                if(wd[i].equals("3")){
                    k=k+10000;
                }
                if(wd[i].equals("4")){
                    k=k+1000;
                }
                if(wd[i].equals("5")){
                    k=k+100;
                }
                if(wd[i].equals("6")){
                    k=k+10;
                }
                if(wd[i].equals("7")){
                    k=k+1;
                }
            }
            inspectSetting.setWeek(k);
        }
        if(inspectSetting.getIsRegular()!=null){
            inspectSetting.setEndTime(null);
            inspectSetting.setStartTime(null);
            LabInspectSetting labEva = labInspectSettingDAO.store(inspectSetting);
            // 保存项目
            String[] standardId = request.getParameterValues("standard");
            Set<LabInspect> standards = new HashSet<LabInspect>();
            for(String tempString : standardId){
                standards.add(labInspectDAO.findLabInspectByPrimaryKey(Integer.parseInt(tempString)));
            }
            Set<LabInspect> labInspects = standards;

            List<MInspectSetting> mInspectSettings = labInspectService.findMInspectSettingByBatchId(idKey);
            for (MInspectSetting mInspectSetting : mInspectSettings) {
                labInspectService.deleteMInspectSetting(mInspectSetting);
            }
            for (LabInspect labInspect1 : labInspects) {
                MInspectSetting m = new MInspectSetting();
                m.setLabInspect(labInspect1);
                m.setBatchId(labEva.getId());
                labInspectService.saveMInspectSetting(m);
            }
        }

        return "redirect:/inspect/listInspectSetting?currpage=1";
    }

    /**
     * Description 删除批次设置
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/deleteInspectSetting")
    public String deleteInspectSetting(@RequestParam Integer idKey) {
        LabInspectSetting labInspectSetting = labInspectSettingDAO.findLabInspectSettingByPrimaryKey(idKey);
        labInspectSettingDAO.remove(labInspectSetting);
        labInspectSettingDAO.flush();

        return "redirect:/inspect/listInspectSetting?currpage=1";
    }

    /***************************************************************************************************/
    /**
     * Description 评价列表
     * @param commonDocument
     * @param currpage
     * @param request
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/listInspectGrading")
    public ModelAndView listInspectGrading(@ModelAttribute CommonDocument commonDocument,
                                           @RequestParam int currpage, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        List <Object[]> listLabRoomComments = null;
        int pageSize = CommonConstantInterface.INT_PAGESIZE;
        int totalRecords = labInspectService.findAlldocumentByQueryCount(commonDocument, request);
        if(user.getUserRole()!=null && user.getUserRole().equals("0")) {
            listLabRoomComments = labInspectService.findMyLabRoomCommentByQuery(user.getUsername(), currpage, pageSize, commonDocument, request);
        }else {
            listLabRoomComments = labInspectService.findMyLabRoomCommentByQuery(null, currpage, pageSize, commonDocument, request);
        }
        mav.addObject("pageModel", shareService.getPage(currpage, pageSize, totalRecords));
        mav.addObject("listLabRoomComments", listLabRoomComments);
        mav.addObject("userRole", user.getUserRole());

        mav.setViewName("/inspect/listInspectGrading.jsp");
        return mav;
    }

    /**
     * Description 新建
     * @param labInspectSetting
     * @param request
     * @param roomId
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/newInspectGrading")
    public ModelAndView newInspectGrading(@ModelAttribute LabInspectSetting labInspectSetting,
                                          HttpServletRequest request, @RequestParam Integer roomId, Integer idKey) {
        ModelAndView mav = new ModelAndView();
        User user = shareService.getUserDetail();
        Date date=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        mav.addObject("user", user);
        if(roomId!=null&&roomId!=-1){
            LabRoom room = labRoomDAO.findLabRoomById(roomId);
            mav.addObject("room", room);
        }
        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        mav.addObject("schoolTerm", schoolTerm);
        Set<LabInspectSetting> inspectSettings = labInspectSettingDAO.findLabInspectSettingBySemeter(schoolTerm.getTermName());
        boolean isTimeForEvaluation = labInspectService.isTimeForEvaluation(date, inspectSettings);
        if(isTimeForEvaluation){
            CommonDocument commonDocument = new CommonDocument();
            //列出所有实验室
            Set<LabRoom> listLabRooms = labRoomDAO.findAllLabRooms();
            mav.addObject("commonDocument", commonDocument);
            mav.addObject("listLabRooms", listLabRooms);
            mav.addObject("username", user.getUsername());
            mav.addObject("date", cal);
            //判断是否在上传设置时间内
            //查询当前评价批次
            LabInspectSetting batch = labInspectService.findBatchByNow(date, inspectSettings);
            if(request.getParameter("batchId")!=null) {
                batch = labInspectSettingDAO.findLabInspectSettingById(Integer.valueOf(request.getParameter("batchId")));
            }
            mav.addObject("batch", batch);
            //查找当前id的实验室评价
            LabInspectSetting lets=labInspectSettingDAO.findLabInspectSettingByPrimaryKey(idKey);
            mav.addObject("lets",lets);
            //根据id获取对应的检查名称
            StringBuffer hql = new StringBuffer("select l from LabInspectSetting l where l.id =" + idKey);
            List<LabInspectSetting> comment = labInspectSettingDAO.executeQuery(hql.toString(),0,-1);
            mav.addObject("comment",comment);

            Set<LabInspectSetting> labEvaluationTimeSetting=labInspectSettingDAO.findLabInspectSettingBySemeter(schoolTerm.getTermName());
            mav.addObject("labEvaluationTimeSetting",labEvaluationTimeSetting);

            List<LabInspect> standards = labInspectService.findLabInspectByBatchId(idKey);
            System.out.println("standards:"+standards);
            mav.addObject("standards", standards);

            mav.setViewName("/inspect/newInspectGrading.jsp");
        }else{
            mav.setViewName("/inspect/noInspectGrading.jsp");
        }

        return mav;
    }

    /**
     * Description 保存评价内容
     * @param commonDocument
     * @param batchId
     * @param request
     * @return
     * @throws IOException
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/saveInspectGrading")
    public String saveInspectGrading(@ModelAttribute CommonDocument commonDocument,
                                     Integer batchId,HttpServletRequest request) throws IOException {
        batchId = commonDocument.getSettingId();
        String saveUrl= "";
        String name = "";

        // TODO Auto-generated method stub
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /**日期格式**/
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        /** 构建文件保存的目录* */
        String PathDir = "/upload/"+ dateformat.format(new Date());
        /** 得到文件保存目录的真实路径* */
        String RealPathDir = request.getSession().getServletContext().getRealPath(PathDir);
        //System.out.println("文件保存目录的真实路径:"+logoRealPathDir);
        /** 根据真实路径创建目录* */
        File SaveFile = new File(RealPathDir);
        if (!SaveFile.exists()){
            SaveFile.mkdirs();
        }
        /** 页面控件的文件流* */
        System.out.println("准备获取文件---");
        MultipartFile multipartFile = multipartRequest.getFile("file");
        /** 获取文件的后缀* */
        System.out.println("上传的文件名称"+multipartFile.getOriginalFilename());

        byte[] bytes = multipartFile.getBytes();
        name = multipartFile.getOriginalFilename();
        /**判断文件不为空*/
        if(!multipartFile.isEmpty()){
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            /** 使用UUID生成文件名称* */
            String logImageName = UUID.randomUUID().toString() + suffix;// 构建文件名称
            /** 拼成完整的文件保存路径加文件* */
            String fileName = RealPathDir + File.separator + logImageName;

            File file = new File(fileName);
            try {
                multipartFile.transferTo(file);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /** 上传到服务器的文件的绝对路径* */
            saveUrl=PathDir+"/"+logImageName;
        }
        commonDocument.setDocumentName(name);
        commonDocument.setDocumentUrl(saveUrl);
        commonDocument.setSettingId(batchId);
        commonDocument.setCreatedAt(Calendar.getInstance());
        commonDocument.setType(5);
        commonDocument = commonDocumentDAO.store(commonDocument);
        //获取评价标准
        List<LabInspect> standards = labInspectService.findLabInspectByBatchId(batchId);
        for(LabInspect s:standards){
            String pointStr = request.getParameter("standard"+s.getId());
            Integer point = Integer.parseInt(pointStr);
            labInspectService.saveGrading(s.getId(),point,batchId,commonDocument.getLabRoom().getId(),commonDocument.getId());
        }
        return "redirect:/inspect/listInspectGrading?currpage=1";
    }

    /**
     * Description 查看评价详情
     * @param idKey
     * @return
     * @author 陈乐为 2018-11-1
     */
    @RequestMapping("/viewInspectGrading")
    public ModelAndView viewInspectGrading(@RequestParam Integer idKey) {
        ModelAndView mav = new ModelAndView();
        CommonDocument commonDocument = commonDocumentDAO.findCommonDocumentByPrimaryKey(idKey);
        //获取分数列表
        List<Object> objects = labInspectService.findGradings(commonDocument.getId());
        LabInspectSetting labInspectSetting = labInspectSettingDAO.findLabInspectSettingByPrimaryKey(commonDocument.getSettingId());
        mav.addObject("objects", objects);
        mav.addObject("commonDocument", commonDocument);
        mav.addObject("labInspectSetting", labInspectSetting);

        mav.setViewName("/inspect/viewInspectGrading.jsp");
        return mav;
    }

    /**
     * Description 删除评价记录
     * @param idKey
     * @return
     */
    @RequestMapping("/deleteInspectGrading")
    public String deleteInspectGrading(@RequestParam Integer idKey) {
        CommonDocument commonDocument = commonDocumentDAO.findCommonDocumentByPrimaryKey(idKey);
        commonDocumentDAO.remove(commonDocument);
        commonDocumentDAO.flush();

        return "redirect:/inspect/listInspectGrading?currpage=1";
    }

}