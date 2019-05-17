package net.zjcclims.service.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.SystemLog;
import net.zjcclims.vo.QueryParamsVO;


/**
 * Spring service that handles CRUD requests for SystemLog entities
 * 
 */
public interface SystemLogService {
	
	
	/***********************************************************************************************
	 * 功能：保存项目卡片操作的日志
	 * 作者：贺子龙
	 * 日期：2015-12-19
	 * userIp：ip地址   tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id  0-查看所有   "-1"-新建
	 ***********************************************************************************************/
	public void saveOperationItemLog(String userIp, int tag, int action, int id);
	/***********************************************************************************************
	 * 功能：查询系统日志
	 * 作者：贺子龙
	 * 日期：2015-12-19
	 ***********************************************************************************************/
	public List<SystemLog> findSystemLogs(SystemLog systemLog, String acno,int currpage,int pageSize,HttpServletRequest request);
	/**********************************
	 * 功能：查询systemLog表中的用户详细字段
	 * 作者：贺子龙
	 * 时间：2015-12-19
	 *********************************/
	public Map<String, String> getUserMap(String acno);
	/**********************************
	 * 功能：删除系统日志
	 * 作者：贺子龙
	 * 时间：2015-12-22
	 *********************************/
	public void deleteSystemLog(String logIds);
	/***********************************************************************************************
	 * 功能：查询计划内实训室使用统计日志
	 * 作者：周志辉
	 * 日期：2017-9-22
	 ***********************************************************************************************/
	public List<Object[]> findAllLabRoomUsePlan(int currpage, int pageSize, HttpServletRequest request);
	/***********************************************************************************************
	 * 功能：查询计划内实训室使用统计日志个数
	 * 作者：周志辉
	 * 日期：2017-9-22
	 ***********************************************************************************************/
	public Integer allLabRoomUsePlanCount(int currpage, int pageSize, HttpServletRequest request);
	/***********************************************************************************************
	 * 功能：实训室课时课次使用统计表
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	public Integer allLabRoomCourseCount(int currpage, int pageSize, HttpServletRequest request);
	/***********************************************************************************************
	 * 功能：实训室课时课次使用统计表个数
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	public List<Object[]> findLabRoomCourseCount(int currpage, int pageSize, HttpServletRequest request);
	/***********************************************************************************************
	 * 功能：年度使用绩效评价表个数
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	public Integer allUsePerformanceEvaluation(int currpage, int pageSize, HttpServletRequest request);
	/***********************************************************************************************
	 * 功能：年度使用绩效评价表
	 * 作者：周志辉
	 * 日期：2017-9-25
	 ***********************************************************************************************/
	public List<Object[]> findUsePerformanceEvaluation(int currpage, int pageSize, HttpServletRequest request);
	/**
	 * Description 查询计划外实训室使用统计日志
	 * @param paramsVO
	 * @return
	 * @author 陈乐为 2019年4月17日
	 */
	public List<Object[]> findAllLabRoomUseUnplan(QueryParamsVO paramsVO);
	/**
	 * Description 查询计划外实训室使用统计个数
	 * @param paramsVO
	 * @return
	 * @author 陈乐为 2019年4月17日
	 */
	public Integer allLabRoomUseUnplanCount(QueryParamsVO paramsVO);

    /**
     * Description 开放项目相关报表-实验计划表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    public void exportListExperimentalSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description 开放项目相关报表-仪器借出登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    public void exportListInstrumentLendingegistration(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description 开放项目相关报表-低值易耗品领用登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    public void exportListReceiptOfLowValueConsumables(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description 开放项目相关报表-药品出库登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    public void exportListDrugDepotRegistrationForm(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description 开放项目相关报表-耗材领用登记表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author Hezhaoyi 2019-5-17
     */
    public void exportListConsumablesAcquisitionRecordSheet(HttpServletRequest request, HttpServletResponse response) throws Exception;

}