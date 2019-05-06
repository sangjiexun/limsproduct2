package net.zjcclims.service.report;

import net.zjcclims.domain.LabWorker;
import net.zjcclims.domain.OperationItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface DataReportService {
    /**
     * Description 报表上报-查询获取学年
     * @author 陈乐为 2018-9-7
     */
    public Map<String,String> findAllYearCodeMap();

    /**
     * Description 本学年纳入教学计划且实际开出的教学实验项目，毕业设计和课程设计的实验不包括在内。
     * @param yearCode
     * @return
     * @author 陈乐为 2018-9-7
     */
    public List<OperationItem> findOperationItems(String yearCode);

    /**
     * Description 教学实验项目表导出TXT
     * @param listOperationItems
     * @param tempFile
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-7
     */
    public void exportOperationItemAVE(List<OperationItem> listOperationItems, File tempFile,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description 实验项目表导出Excel
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-8
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void exportOperationItem(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description 获取所有专任实验室人员列表
     * @return
     * @author 陈乐为 2018-9-8
     */
    public List<LabWorker> findLabWorkers();

    /**
     * Description 专任实验室人员表导出TXT
     * @param listLabWorkers
     * @param tempFile
     * @param response
     * @throws Exception
     * @author 陈乐为 2018-9-8
     */
    public void exportLabWorkerAVE(List<LabWorker> listLabWorkers, File tempFile, HttpServletResponse response) throws Exception;
    /*************************************************************************************
     * @description：专任实验室人员表导出Excel
     * @author：廖文辉
     * @日期：2018-10-22
     *************************************************************************************/
    public void exportLabWorker(HttpServletRequest request, HttpServletResponse response) throws Exception;

}