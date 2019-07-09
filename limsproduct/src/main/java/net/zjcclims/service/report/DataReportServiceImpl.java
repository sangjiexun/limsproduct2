package net.zjcclims.service.report;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.LabWorkerDAO;
import net.zjcclims.dao.OperationItemDAO;
import net.zjcclims.dao.SchoolTermDAO;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("DataReportService")
@Transactional
public class DataReportServiceImpl implements DataReportService {
    @Autowired private SchoolTermDAO schoolTermDAO;
    @Autowired private OperationItemDAO operationItemDAO;
    @Autowired private LabWorkerDAO labWorkerDAO;

    @Autowired private ShareService shareService;

    /**
     * Description 报表上报-查询获取学年
     * @author 陈乐为 2018-9-7
     */
    @Override
    public Map<String,String> findAllYearCodeMap() {
        Map<String,String> map = new HashMap<String, String>();
        // 查询存在符合条件的学年
        String sql=" select l from SchoolTerm l where 1=1 order by l.id";
        List<SchoolTerm> listSchoolTerms = schoolTermDAO.executeQuery(sql, -1,0);
        for (SchoolTerm schoolTerm : listSchoolTerms) {
            map.put(schoolTerm.getYearCode(),schoolTerm.getYearCode());
        }
        return map;
    }

    /**
     * Description 本学年纳入教学计划且实际开出的教学实验项目，毕业设计和课程设计的实验不包括在内。
     * @param yearCode
     * @return
     * @author 陈乐为 2018-9-7
     */
    @Override
    public List<OperationItem> findOperationItems(String yearCode) {
        String sql = "select c from OperationItem c where c.CDictionaryByLpStatusCheck.CNumber = 3"; // 审核通过的项目
        sql += " and length(c.lpCodeCustom) > 0"; // 项目编号不为空

        SchoolTerm currTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance()); // 当前学期
        // 构建查询条件（学期）
        Set<SchoolTerm> schoolTerms = schoolTermDAO.findSchoolTermByYearCode(yearCode);
        String termString = "(";
        if (schoolTerms!=null && schoolTerms.size()>0) {
            for (SchoolTerm schoolTerm : schoolTerms) {
                termString+=schoolTerm.getId()+",";
            }
        }
        termString+=" 0)";
        // 判断页面输入是否为空，
        if (!EmptyUtil.isStringEmpty(yearCode)) {
            sql += " and c.schoolTerm.id in"+termString;
        } else {
            sql += " and c.schoolTerm.id ="+currTerm.getId();
        }
        // 执行sql语句
        List<OperationItem> operationItems = operationItemDAO.executeQuery(sql, 0, -1);

        return operationItems;
    }

    /**
     * Description 教学实验项目表导出TXT
     * @param listOperationItems
     * @param tempFile
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-7
     */
    @Override
    public void exportOperationItemAVE(List<OperationItem> listOperationItems, File tempFile,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String content = "";
        for (OperationItem operationItem : listOperationItems) {
            // 实验编号
            String lpCodeCustom = String.format("%-13s", operationItem.getLpCodeCustom().trim());
            // 实验名称
            int len = this.length(operationItem.getLpName().trim());
            int emp = 50-len;
            String lpName = String.format("%-"+emp+"s", "");
            lpName = operationItem.getLpName().trim() + lpName;
            // 实验类别
            String lpCategoryMain;
            if(operationItem.getCDictionaryByLpCategoryMain()!=null && operationItem.getCDictionaryByLpCategoryMain().getCNumber()!=null) {
                lpCategoryMain = String.format("%-1s", operationItem.getCDictionaryByLpCategoryMain().getCNumber().trim());
            }else {
                lpCategoryMain = String.format("%-1s", 4);
            }
            // 实验类型
            // 网络实验加*
            String lpCategoryApp;
            if(operationItem.getCDictionaryByLpCategoryApp().getCNumber().equals("6") &&
                    operationItem.getCDictionaryByLpCategoryApp().getCCategory().equals("category_operation_item_app")) {
                if(operationItem.getCDictionaryByLpCategoryApp()!=null && operationItem.getCDictionaryByLpCategoryApp().getCNumber()!=null) {
                    lpCategoryApp = operationItem.getCDictionaryByLpCategoryApp().getCNumber().trim()+"*";
                }else {
                    lpCategoryApp = "5*";
                }
            }else {
                if(operationItem.getCDictionaryByLpCategoryApp()!=null && operationItem.getCDictionaryByLpCategoryApp().getCNumber()!=null) {
                    lpCategoryApp = String.format("%-2s", operationItem.getCDictionaryByLpCategoryApp().getCNumber().trim());
                }else {
                    lpCategoryApp = String.format("%-2s", 5);
                }
            }
            // 实验所属学科
            String lpSubject;
            if(operationItem.getSystemSubject12()!=null && operationItem.getSystemSubject12().getSNumber()!=null) {
                lpSubject = String.format("%-4s", operationItem.getSystemSubject12().getSNumber().substring(0, 4));
            }else {
                lpSubject = String.format("%-4s", " ");
            }
            // 实验要求
            String lpCategoryRequire;
            if(operationItem.getCDictionaryByLpCategoryRequire()!=null && operationItem.getCDictionaryByLpCategoryRequire().getCNumber()!=null) {
                lpCategoryRequire = String.format("%-1s", operationItem.getCDictionaryByLpCategoryRequire().getCNumber().trim());
            }else {
                lpCategoryRequire = String.format("%-1s", 3);
            }
            // 实验者类别
            String lpCategoryStudent = String.format("%1s", operationItem.getCDictionaryByLpCategoryStudent().getCNumber().trim());
            if(operationItem.getCDictionaryByLpCategoryStudent()!=null && operationItem.getCDictionaryByLpCategoryStudent().getCNumber()!=null) {
                lpCategoryStudent = String.format("%-1s", operationItem.getCDictionaryByLpCategoryStudent().getCNumber().trim());
            }else {
                lpCategoryStudent = String.format("%-1s", 5);
            }
            // 实验者人数
            String lpStudentNumber;
            if(operationItem.getLpStudentNumber()>0) {
                lpStudentNumber = String.format("%-6s", operationItem.getLpStudentNumber().toString().trim());
            }else {
                lpStudentNumber = String.format("%-6s", 1);
            }
            // 每组人数
            String lpStudentNumberGroup = String.format("%-2s", operationItem.getLpStudentNumberGroup().trim());
            // 实验学时数
            String lpDepartmentHours = String.format("%-4s", operationItem.getLpDepartmentHours().toString().trim());
            // 实验室编号-项目和实验室多对多，编号怎么取呢？插个小红旗埋个雷
            // 实验室编号-取实验室所在中心编号
            String labRoomNumber = String.format("%-10s", "none");
            if(operationItem.getLabCenter()!=null && operationItem.getLabCenter().getCenterNumber()!=null) {
                labRoomNumber = String.format("%-10s", operationItem.getLabCenter().getCenterNumber().trim());
            } else if (operationItem.getLabRooms()!=null && operationItem.getLabRooms().size() > 0) {
                for (LabRoom labRoom : operationItem.getLabRooms()) {
                    labRoomNumber = String.format("%-10s", labRoom.getLabCenter().getCenterNumber().trim());
                    break;
                }
            }
            // 实验室名称 ,实际取所属实验中心名称
            String labRoomName = String.format("%-50s", "none");
            if(operationItem.getLabCenter()!=null && operationItem.getLabCenter().getCenterName()!=null) {
                int lablen = this.length(operationItem.getLabCenter().getCenterName().trim());
                int labemp = 50-lablen;
                labRoomName = String.format("%-"+labemp+"s", "");
                labRoomName = operationItem.getLabCenter().getCenterName().trim()+labRoomName;
            }else if (operationItem.getLabRooms()!=null && operationItem.getLabRooms().size() > 0) {
                for (LabRoom labRoom : operationItem.getLabRooms()) {
                    int lablen = this.length(labRoom.getLabCenter().getCenterName().trim());
                    int labemp = 50-lablen;
                    labRoomName = String.format("%-"+labemp+"s", "");
                    labRoomName = labRoom.getLabCenter().getCenterName().trim()+labRoomName;
                    break;
                }
            }

            content += pConfigDTO.schoolCode
                    + lpCodeCustom
                    + lpName
                    + lpCategoryMain
                    + lpCategoryApp
                    + lpSubject
                    + lpCategoryRequire
                    + lpCategoryStudent
                    + lpStudentNumber
                    + lpStudentNumberGroup
                    + lpDepartmentHours
                    + labRoomNumber
                    + labRoomName
                    + "\r\n";
        }
        this.contentToTxt(tempFile, content);
        this.downLoad(tempFile, "SJ4"+pConfigDTO.schoolCode, response);
    }

    /**
     * Description 生成文件
     * @param tempFile
     * @param content
     * @author 陈乐为 2018-9-7
     */
    public void contentToTxt(File tempFile, String content) {
        try {
            if (!tempFile.exists()) {
                tempFile.createNewFile();// 不存在则创建
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(tempFile), "gbk");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Description 下载文件
     * @param tempFile
     * @param fileName
     * @param response
     * @author 陈乐为 2018-9-7
     */
    public void downLoad(File tempFile, String fileName, HttpServletResponse response) {
        response.setContentType("application/txt;charset=UTF-8");

        FileInputStream in = null;
        OutputStream o = null;
        try {
            byte b[] = new byte[1024];
            in = new FileInputStream(tempFile);
            o = response.getOutputStream();
            response.setContentType("application/x-doc");
            // Content-disposition扩展头,当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型
            // attachment 作为附件下载
            // 指定下载的文件名
            String as = fileName+".txt";
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(as, "UTF-8"));
            String fileLength = String.valueOf(as.length());
            response.setHeader("Content_Length", fileLength); // 设置头文件的长度为指定文件的长度
            int n = 0;
            while ((n = in.read(b)) != -1) {
                o.write(b, 0, n); // 将数组 b 中的  n 个字节按顺序写入输出流
                o.flush();
            }
        } catch (Exception e) { // 当try语句中出现异常是时，会执行catch中的语句
            e.printStackTrace(); // 在命令行打印异常信息在程序中出错的位置及原因
        } finally { // 执行顺序：try--Exception--finally   一般finally写的代码就是流的关闭语句
            try {
                //in.close(); // 关闭此文件输入流并释放与该流相关的所有系统资源。如果该流具有相关联的信道，则信道被关闭为好。
                o.flush(); // 刷新此输出流
                //o.close(); // 关闭此输出流
            } catch (IOException e) { // 捕捉的是输入输出异常
                e.printStackTrace(); // 在命令行打印异常信息在程序中出错的位置及原因
            }
        }
    }

    /**
     * Description 得到一个字符串的长度，一个汉字长度为2，英文长度为1
     * @param s
     * @return
     * @auhtor 陈乐为 2018-9-7
     */
    public int length(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!this.isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * Description 判断参数字符是否为字母
     * @param c 被判断参数字符
     * @return
     * @author 陈乐为 2018-9-7
     */
    public boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * Description 实验项目表导出Excel
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-8
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void exportOperationItem(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<Map> list = new ArrayList<Map>();
        List<OperationItem> operationItems = findOperationItems(request.getParameter("yearCodeForTxt")); //
        //实验中心所在学院的实验室项目
        int i=1;
        for (OperationItem reportOperationItem : operationItems)
        {
            Map map = new HashMap();

            map.put("serial number",i);//序号
            i++;
            map.put("project no",reportOperationItem.getLpCodeCustom());//实验编号
            map.put("project name",reportOperationItem.getLpName());//实验名称
            if(reportOperationItem.getCDictionaryByLpCategoryMain() != null && reportOperationItem.getCDictionaryByLpCategoryMain().getCName()!=null) {
                map.put("category", reportOperationItem.getCDictionaryByLpCategoryMain().getCName());//实验类别
            }
            if(reportOperationItem.getCDictionaryByLpCategoryApp()!=null && reportOperationItem.getCDictionaryByLpCategoryApp().getCName()!=null){
                map.put("test type",reportOperationItem.getCDictionaryByLpCategoryApp().getCName());//实验类型
            }
            if(reportOperationItem.getSystemSubject12()!= null && reportOperationItem.getSystemSubject12().getSName()!= null) {
                map.put("subject",reportOperationItem.getSystemSubject12().getSName());//实验所属学科
            }
            if(reportOperationItem.getCDictionaryByLpCategoryRequire()!=null && reportOperationItem.getCDictionaryByLpCategoryRequire().getCName()!=null){
                map.put("test request",reportOperationItem.getCDictionaryByLpCategoryRequire().getCName());//实验要求
            }
            if(reportOperationItem.getCDictionaryByLpCategoryStudent()!=null && reportOperationItem.getCDictionaryByLpCategoryStudent().getCName()!=null){
                map.put("user type",reportOperationItem.getCDictionaryByLpCategoryStudent().getCName());//实验者类别
            }
            if(reportOperationItem.getLpStudentNumber()!= null) {
                map.put("studentNum", reportOperationItem.getLpStudentNumber());//实验者人数
            }
            if(reportOperationItem.getLpStudentNumberGroup() != null) {
                map.put("studentGroup", reportOperationItem.getLpStudentNumberGroup());//每组人数
            }
            map.put("times",reportOperationItem.getLpDepartmentHours());//实验学时数
            if(reportOperationItem.getLabRoom()!=null && reportOperationItem.getLabRoom().getLabRoomNumber()!=null){
                map.put("class code",reportOperationItem.getLabRoom().getLabRoomNumber());//实验室编号
            }
            if(reportOperationItem.getLabRoom()!=null && reportOperationItem.getLabRoom().getLabRoomName()!=null){
                map.put("lab name", reportOperationItem.getLabRoom().getLabRoomName());//实验室名称
            }
            list.add(map);
        }
        String title = "实验项目汇总表";
        String[] hearders = new String[] {"序号","实验编号","实验名称","实验类别","实验类型","实验所属学科","实验要求","实验者类别","实验者人数","每组人数","实验学时数","实验室编号","实验室名称"};//表头数组
        String[] fields = new String[] {"serial number","project no","project name","category","test type","subject","test request","user type","studentNum","studentGroup",
                "times","class code","lab name"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title,  shareService.getUserDetail().getCname(), "实验项目数据汇总", td);
    }

    /**
     * Description 获取所有专任实验室人员列表
     * @return
     * @author 陈乐为 2018-9-8
     */
    @Override
    public List<LabWorker> findLabWorkers() {
        String sql = "select c from LabWorker c where 1=1";
        // 执行sql语句
        List<LabWorker> labWorkers = labWorkerDAO.executeQuery(sql);

        return labWorkers;
    }

    /**
     * Description 专任实验室人员表导出TXT
     * @param listLabWorkers
     * @param tempFile
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-8
     */
    @Override
    public void exportLabWorkerAVE(List<LabWorker> listLabWorkers, File tempFile, HttpServletResponse response) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        String content = "";
        for (LabWorker labWorker : listLabWorkers) {

            // 人员编号
            String customCode;
            if (labWorker.getLwCodeCustom() != null && !labWorker.getLwCodeCustom().equals("")) {
                customCode = CommonConstantInterface.STR_SPACE_7.replaceFirst(CommonConstantInterface.STR_SPACE_7.substring(0, labWorker.getLwCodeCustom().trim().length()), labWorker.getLwCodeCustom().trim());
            } else {
                customCode = CommonConstantInterface.STR_SPACE_7.replaceFirst(CommonConstantInterface.STR_SPACE_7.substring(0, 1), " ");
            }

            // 实验室编号
            String centerCode;
            if (labWorker.getLabCenter() != null && labWorker.getLabCenter().getCenterNumber() != null && !labWorker.getLabCenter().getCenterNumber().equals("")) {
                centerCode = CommonConstantInterface.STR_SPACE_10.replaceFirst(CommonConstantInterface.STR_SPACE_10.substring(0, labWorker.getLabCenter().getCenterNumber().trim().length()), labWorker.getLabCenter().getCenterNumber().trim());
            } else {
                centerCode = CommonConstantInterface.STR_SPACE_10.replaceFirst(CommonConstantInterface.STR_SPACE_10.substring(0, 1), " ");
            }

            // 实验室名称
            String centerName;
            if (labWorker.getLabCenter() != null && labWorker.getLabCenter().getCenterName() != null && !labWorker.getLabCenter().getCenterName().equals("")) {
                // 判断长度是否大于50，大于则缩写
                if(length(labWorker.getLabCenter().getCenterName().trim()) < 50) {
                    centerName = CommonConstantInterface.STR_SPACE_50.replaceFirst(CommonConstantInterface.STR_SPACE_50.substring(0, length(labWorker.getLabCenter().getCenterName().trim())), labWorker.getLabCenter().getCenterName().trim());
                } else {
                    centerName = CommonConstantInterface.STR_SPACE_50.replaceFirst(CommonConstantInterface.STR_SPACE_50,CommonConstantInterface.STR_SPACE_50.substring(0,50));
                }
            } else {
                centerName = CommonConstantInterface.STR_SPACE_50.replaceFirst(CommonConstantInterface.STR_SPACE_50.substring(0, 1), " ");
            }

            // 姓名
            String lwName;
            if(labWorker.getLwName() != null && !labWorker.getLwName().equals("")) {
                // 判断长度是否大于8，大于8则缩写
                if (length(labWorker.getLwName().trim()) < 8) { // length()*2 一个汉字占两个字节
                    lwName = CommonConstantInterface.STR_SPACE_8.replaceFirst(CommonConstantInterface.STR_SPACE_8.substring(0, length(labWorker.getLwName().trim())), labWorker.getLwName().trim());
                } else {
                    lwName = CommonConstantInterface.STR_SPACE_8.replaceFirst(CommonConstantInterface.STR_SPACE_8,CommonConstantInterface.STR_SPACE_8.substring(0,8));
                }
            } else {
                lwName = CommonConstantInterface.STR_SPACE_8.replaceFirst(CommonConstantInterface.STR_SPACE_8.substring(0, 1), " ");
            }

            // 性别
            String lwSex ;
            if(labWorker.getLwSex() != null && !labWorker.getLwSex().equals("")) {
                lwSex = CommonConstantInterface.STR_SPACE_1.replaceFirst(CommonConstantInterface.STR_SPACE_1.substring(0, labWorker.getLwSex().trim().length()), labWorker.getLwSex().trim());
            } else {
                lwSex = CommonConstantInterface.STR_SPACE_1.replaceFirst(CommonConstantInterface.STR_SPACE_1.substring(0, 1), " ");
            }

            // 出生年月
            String lwBirthday ;
            if (labWorker.getLwBirthday() != null) {
                String syyyymmdd = dateFormat.format(labWorker.getLwBirthday()
                        .getTime());
                lwBirthday = syyyymmdd.substring(0, 6);
            } else {
                lwBirthday = CommonConstantInterface.STR_SPACE_6;
            }

            // 所属学科
            String lwSubject ;
            if (labWorker.getCDictionaryByLwSubject() != null && labWorker.getCDictionaryByLwSubject().getCNumber() != null && !labWorker.getCDictionaryByLwSubject().getCNumber().equals("")) {
                lwSubject = CommonConstantInterface.STR_SPACE_4.replaceFirst(CommonConstantInterface.STR_SPACE_4.substring(0, labWorker.getCDictionaryByLwSubject().getCNumber().trim().length()), labWorker.getCDictionaryByLwSubject().getCNumber().trim());
            } else {
                lwSubject = CommonConstantInterface.STR_SPACE_4.replaceFirst(CommonConstantInterface.STR_SPACE_4.substring(0, 1), " ");
            }

            // 专业技术职务
            String lwSpecialtyDuty ;
            if (labWorker.getCDictionaryByLwSpecialtyDuty() != null && labWorker.getCDictionaryByLwSpecialtyDuty().getCNumber() != null && !labWorker.getCDictionaryByLwSpecialtyDuty().getCNumber().equals("")) {
                lwSpecialtyDuty = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, labWorker.getCDictionaryByLwSpecialtyDuty().getCNumber().trim().length()), labWorker.getCDictionaryByLwSpecialtyDuty().getCNumber().trim());
            } else {
                lwSpecialtyDuty = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, 1), "0");
            }

            // 文化程度
            String lwAcademicDegree ;
            if (labWorker.getCDictionaryByLwAcademicDegree() != null && labWorker.getCDictionaryByLwAcademicDegree().getCNumber() != null && !labWorker.getCDictionaryByLwAcademicDegree().getCNumber().equals("")) {
                lwAcademicDegree = CommonConstantInterface.STR_SPACE_2.replaceFirst(CommonConstantInterface.STR_SPACE_2.substring(0, labWorker.getCDictionaryByLwAcademicDegree().getCNumber().trim().length()), labWorker.getCDictionaryByLwAcademicDegree().getCNumber().trim());
            } else {
                lwAcademicDegree = CommonConstantInterface.STR_SPACE_2.replaceFirst(CommonConstantInterface.STR_SPACE_2.substring(0, 1), " ");
            }

            // 专家类别
            String lwCategoryExpert = null ;
            if (labWorker.getCDictionaryByLwCategoryExpert() != null && labWorker.getCDictionaryByLwCategoryExpert().getCNumber() != null) {
                // 无类别时，数据为0，报表要求为00
                lwCategoryExpert = CommonConstantInterface.STR_SPACE_2.replaceFirst(CommonConstantInterface.STR_SPACE_2.substring(0, labWorker.getCDictionaryByLwCategoryExpert().getCNumber().trim().length()+1), labWorker.getCDictionaryByLwCategoryExpert().getCNumber().trim()+"0");
            } else {
                lwCategoryExpert = CommonConstantInterface.STR_SPACE_2.replaceFirst(CommonConstantInterface.STR_SPACE_2.substring(0, 1), "0");
            }

            // 国内培训（学历教育时间）
            String lwTrainFormalInlandTime ;
            if (labWorker.getLwTrainFormalInlandTime() != null && !labWorker.getLwTrainFormalInlandTime().equals("")) {
                lwTrainFormalInlandTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, labWorker.getLwTrainFormalInlandTime().trim().length()), labWorker.getLwTrainFormalInlandTime().trim());
            } else {
                lwTrainFormalInlandTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, 3), "  0");
            }

            // 国内培训（非学历教育时间）
            String lwTrainInformalInlandTime ;
            if (labWorker.getLwTrainInformalInlandTime() != null && !labWorker.getLwTrainInformalInlandTime().equals("")) {
                lwTrainInformalInlandTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, labWorker.getLwTrainInformalInlandTime().trim().length()), labWorker.getLwTrainInformalInlandTime().trim());
            } else {
                lwTrainInformalInlandTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, 3), "  0");
            }

            // 国外培训（学历教育时间）
            String lwTrainFormalAbroadTime ;
            if (labWorker.getLwTrainFormalAbroadTime() != null && !labWorker.getLwTrainFormalAbroadTime().equals("")) {
                lwTrainFormalAbroadTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, labWorker.getLwTrainFormalAbroadTime().trim().length()), labWorker.getLwTrainFormalAbroadTime().trim());
            } else {
                lwTrainFormalAbroadTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, 3), "  0");
            }

            // 国外培训（非学历教育时间）
            String lwTrainInformalAbroadTime ;
            if (labWorker.getLwTrainInformalAbroadTime() != null && !labWorker.getLwTrainInformalAbroadTime().equals("")) {
                lwTrainInformalAbroadTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, labWorker.getLwTrainInformalAbroadTime().trim().length()), labWorker.getLwTrainInformalAbroadTime().trim());
            } else {
                lwTrainInformalAbroadTime = CommonConstantInterface.STR_SPACE_3.replaceFirst(CommonConstantInterface.STR_SPACE_3.substring(0, 3), "  0");
            }

            content += pConfigDTO.schoolCode
                    + customCode
                    + centerCode
                    + centerName
                    + lwName
                    + lwSex
                    + lwBirthday
                    + lwSubject
                    + lwSpecialtyDuty
                    + lwAcademicDegree
                    + lwCategoryExpert
                    + lwTrainFormalInlandTime
                    + lwTrainInformalInlandTime
                    + lwTrainFormalAbroadTime
                    + lwTrainInformalAbroadTime
                    + "\r\n";
        }
        this.contentToTxt(tempFile, content);
        this.downLoad(tempFile, "SJ5"+pConfigDTO.schoolCode, response);
    }
    /*************************************************************************************
     * @description：专任实验室人员表导出Excel
     * @author：陈乐为
     * @日期：2016-10-9
     *************************************************************************************/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void exportLabWorker(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<Map> list = new ArrayList<Map>();
        List<LabWorker> reportLabWorkers = findLabWorkers(); //
        //实验中心所在学院的实验室项目
        int i=1;
        for (LabWorker reportLabWorker : reportLabWorkers)
        {
            Map map = new HashMap();

            map.put("serial number",i);//序号
            i++;
            map.put("school code",12799);//实验部门
            map.put("worker code",reportLabWorker.getLwCodeCustom());//负责人
            if(reportLabWorker.getLabCenter()!=null&&reportLabWorker.getLabCenter().getCenterName()!=null
                    &&reportLabWorker.getLabCenter().getCenterNumber()!=null) {
                map.put("department no", reportLabWorker.getLabCenter().getCenterNumber());//实验室编号
                map.put("department name", reportLabWorker.getLabCenter().getCenterName());//实验室名称
            }
            map.put("cname",reportLabWorker.getLwName());//
            if(reportLabWorker != null && reportLabWorker.getLwSex() != null){
                map.put("sex", reportLabWorker.getLwSex());
//            if(Integer.valueOf(reportLabWorker.getLwSex())==1){
//              map.put("sex","男");//
//            }else{
//              map.put("sex","女");//
//            }
            }
            if(reportLabWorker.getLwBirthday()!=null) {
                map.put("birthday", reportLabWorker.getLwBirthday().getTime());//实验名称
            }
            if(reportLabWorker.getCDictionaryByLwSubject()!=null) {
                map.put("subject belongs", reportLabWorker.getCDictionaryByLwSubject().getCName());//专科人数
            }
            if(reportLabWorker.getCDictionaryByLwSpecialtyDuty()!=null) {
                map.put("specialized job", reportLabWorker.getCDictionaryByLwSpecialtyDuty().getCName());//专科课时
            }
            if(reportLabWorker.getCDictionaryByLwAcademicDegree()!=null) {
                map.put("education level", reportLabWorker.getCDictionaryByLwAcademicDegree().getCName());//专科人时数
            }
            if(reportLabWorker.getCDictionaryByLwCategoryExpert()!=null) {
                map.put("specialist type", reportLabWorker.getCDictionaryByLwCategoryExpert().getCName());//本科人数
            }
            map.put("domestic training",reportLabWorker.getLwTrainFormalInlandTime());//国内学历
            map.put("domestic training2",reportLabWorker.getLwTrainInformalInlandTime());//国内非学历
            map.put("foreign training",reportLabWorker.getLwTrainFormalAbroadTime());//国外学历
            map.put("foreign training2",reportLabWorker.getLwTrainInformalAbroadTime());//国外非学历
            list.add(map);
        }  //实验室遍历
        String title = "专任实验室人员表";
        String[] hearders = new String[] {"序号","学校代码","人员编号","实验室编号","实验室名称","姓名","性别","出生年月","所属学科",
                "专业技术职务","文化程度","专家类别","国内培训时间学历教育","国内培训时间非学历教育","国外培训时间学历教育","国外培训时间非学历教育"};//表头数组
        String[] fields = new String[] {"serial number","school code", "worker code", "department no", "department name","cname", "sex", "birthday", "subject belongs",
                "specialized job", "education level", "specialist type", "domestic training", "domestic training2", "foreign training","foreign training2"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title,  shareService.getUserDetail().getCname(), "专任实验室人员表", td);
    }

}