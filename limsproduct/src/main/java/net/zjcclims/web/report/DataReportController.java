package net.zjcclims.web.report;

import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.report.DataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller("DataReportController")
@RequestMapping("/dataReport")
public class DataReportController<JsonResult> {
    @Autowired private ShareService shareService;
    @Autowired private DataReportService dataReportService;
    /*****************************************************************
     * @description：数据上报列表页面
     * @author 陈乐为
     * @return jsp
     * @日期：2016-7-25
     *****************************************************************/
    @RequestMapping("/listDataReport")
    public ModelAndView listDataReport(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance()); // 当前学期
        String yearCode = schoolTerm.getYearCode(); // 当前学年
        mav.addObject("currYear", yearCode);
        // 获取页面所选学年
        if (request.getParameter("yearCode") != null && !request.getParameter("yearCode").equals("")) {
            yearCode = request.getParameter("yearCode");
        }
        mav.addObject("yearCode", yearCode);
        // 获取学年列表
        Map<String,String> yearCodes = dataReportService.findAllYearCodeMap();
        mav.addObject("yearCodes", yearCodes);
        // 跳转
        mav.setViewName("report/listDataReport.jsp");
        return mav;
    }

    /**
     * Description 导出TXT-实验项目
     * @param operationItem
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-7
     */
    @RequestMapping("/reportOperationItemTxt")
    public void reportOperationItemTxt(@ModelAttribute OperationItem operationItem, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        // 得到文件保存目录的真实路径
        String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath();

        File tempFile = new File(logoRealPathDir+File.separator+"temp.txt"); //logoRealPathDir+File.separator+"temp.txt"    "D:/temp.txt"
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        // 项目列表
        List<OperationItem> listOperationItems = dataReportService.findOperationItems(request.getParameter("yearCodeForTxt"));
        dataReportService.exportOperationItemAVE(listOperationItems, tempFile, request, response);
    }

    /**
     * Description 项目表导出excel
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-8
     */
    @RequestMapping("/reportProjectSummaryExcel")
    public void reportProjectSummaryExcel(HttpServletRequest request, HttpServletResponse response)throws Exception{
        dataReportService.exportOperationItem(request, response);
    }

    /**
     * Description 导出TXT-实验室专任人员
     * @param labWorker
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-8
     */
    @RequestMapping("/reportLabWorkerTxt")
    public void reportLabWorkerTxt(@ModelAttribute LabWorker labWorker, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 得到文件保存目录的真实路径
        String logoRealPathDir = this.getClass().getClassLoader().getResource("/").getPath();

        File tempFile = new File(logoRealPathDir+File.separator+"temp.txt");
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        // 人员列表
        List<LabWorker> listLabWorkers = dataReportService.findLabWorkers();
        dataReportService.exportLabWorkerAVE(listLabWorkers, tempFile, response);

    }
    /************************************************************
     * @description：专任实验室人员表导出Excel
     * @author：廖文辉
     * @日期：2018-10-22
     ************************************************************/
    @RequestMapping("/reportLabWorkerExcel")
    public void reportLabWorkerExcel(HttpServletRequest request, HttpServletResponse response)throws Exception{
        dataReportService.exportLabWorker(request, response);
    }
}