package net.zjcclims.service.report;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.Map;

import net.zjcclims.constant.OperationItemByCategory;
import net.zjcclims.constant.OperationItemByChange;
import net.zjcclims.constant.OperationItemByRequire;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.ReportRate;
import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolDevice;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.User;
import net.zjcclims.vo.QueryParamsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportService {
	
	/**
	 * 根据字符串获取学期集合
	 * @param queryStr 形式如1,4
	 * @author hely
	 * 2014.09.10
	 */
	public List<SchoolTerm> getSchoolTermsByStr(String queryStr);
	
	/**
	 * 根据字符串获取学期，用于下拉框
	 * @param queryStr 形式如 1,4
	 * @param flag 为true则查出字符串所包含的学期，否则查出不包含的学期
	 * @author hely
	 * 2014.09.10
	 */
	public Map<Integer, String> getSelectTerms(String queryStr, String yearCode, boolean flag);
	
	/**
	 * 获取需要计算绩效报表学院的名称字符串
	 * @author hely
	 * 2014.09.04
	 */
	public String getAcademyNames();
	
	/**
	 * 得到学年的数据，用于下拉框
	 * @author hely
	 * 2014.09.10
	 */
	public Map<Integer, String> getTermsMap();
	
	/**
	 * 根据指定学期查询绩效报表信息
	 * @param schoolTerms 学期
	 * @author hely
	 * 2014.09.04
	 */
	//public List<ReportRate> getReportRateByTerms(List<SchoolTerm> schoolTerms);
	
	
	public List<SchoolAcademy> getSchoolAcademyByQuery();
	
	/**
	 * 根据指定学期查询绩效报表信息
	 * @param schoolTerms 学期
	 * @author hely
	 * 2014.09.04
	 */
	public List<ReportRate> getReportRateByTerms(List<SchoolTerm> schoolTerms);
	
	/**
	 * 根据学院和所给时间段获取实验室利用率公式中涉及到的数据
	 * @param academyNumber  学院编号
	 * @param schoolTerms  时间段
	 * @author hely
	 * 2014.08.26
	 */
	public List<LabRoom> getLabRateDetailInfo(List<SchoolTerm> schoolTerms);
	/**
	 * 获取指定学期的学院绩效指标数据
	 * @param academyNumber 学院编号
	 * @param schoolTerms 学期（学年或者单个学期）
	 * @author hely
	 * 2014.12.11
	 */
	public ReportRate getReportRateByTermsAcademy(String academyNumber, List<SchoolTerm> schoolTerms);
	/**
	 * 获取学院实验室容量总和
	 * @param academyNumber 学院编号
	 * @author hely
	 * 2014.08.26
	 */
	public int getLabCapacityByAcademy(String academyNumber);
	/**
	 * 实验室额定课时数（学校给的数据）
	 * @author hely
	 * 2014.08.06
	 */
	public int getRatedCourseTime(String academyNumber);
	/**
	 * 获取需要计算绩效报表的实验室
	 * @author zhangyu
	 * 
	 */
	public List<ReportRate> getlabRoomByQuery();
	/**
	 * 根据学年编号(year_code)获取学期
	 * @param yearCode 学年编号
	 * @author hely
	 * 2014.09.10
	 */
	public String getTermsByYearCode(String yearCode);

	/*******************************************************************
	 * @description：根据页面所选roomID查询
	 * @param roomIds 多选实验室查询条件
	 * @author：陈乐为 2018-7-20
	 *******************************************************************/
	public Map<Integer, String> getLabRoomSelectedMap(String[] roomIds);

	/********************************************************************
	 * @description：获取所有中心
	 * @author：陈乐为
	 * @date：2018-7-20
	 ********************************************************************/
	public Map<Integer, String> getAllLabCenterMap();

	/**
	 * Description 获取实验室-实验类型统计专用-中心&学期&实验类型&审核通过
	 * @param centerId
	 * @param termId
	 * @return
	 * @author 陈乐为 2018-7-20
	 */
	public Map<Integer, String> getLabRoomsListMap(Integer centerId, Integer termId);

	/**
	 * Description 实验类型统计表
	 * @param request
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemViews(HttpServletRequest request, int page , int pageSize);

	/*************************************************
	 * Description 将实验室项目卡按实验类型分类的查询的结果转化为页面方便显示的样式
	 * @param operationItemTypeList 项目集
	 * @author 陈乐为 2018-7-20
	 *************************************************/
	public List<OperationItemByCategory> operationItemTypechangeViews(List<Object[]> operationItemTypeList);

	/**************************************
	 * Description 视图-实验类型统计表-list
	 * @author 陈乐为 2018-7-20
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemView(HttpServletRequest request, int page ,int pageSize);

	/*************************************************
	 * Description 将实验中心项目卡按实验类型分类的查询的结果转化为页面方便显示的样式
	 * @param operationItemTypeList 项目集
	 * @author 陈乐为 2018-7-20
	 *************************************************/
	public List<OperationItemByCategory> operationItemTypechangeView(List<Object[]> operationItemTypeList);

	/**************************************
	 * Description 视图-实验类型统计表-count
	 * @author 陈乐为 2018-7-20
	 **************************************/
	public int operationItemViewCount(HttpServletRequest request);

	/*************************************************************************************
	 * Description 实验类型统计表导出Excel
	 * @author 陈乐为 2018-7-20
	 *************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportOperationItemType(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**************************************
	 * Description 视图-实验要求统计表-roomId不为空
	 * @author 陈乐为 2018-7-23
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemRequireViews(HttpServletRequest request, int page ,int pageSize);

	/*************************************************
	 * Description 将查询的结果转化为页面方便显示的样式-roomId不为空
	 * @author 陈乐为 2018-7-23
	 *************************************************/
	public List<OperationItemByRequire> operationItemRequireChangeViews(List<Object[]> operationItemRequireList);

	/**************************************
	 * Description 视图-实验要求统计表-roomId为空
	 * @author 陈乐为 2018-7-23
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemRequireView(HttpServletRequest request, int page ,int pageSize);

	/*************************************************
	 * Description 将结果转化为页面方便显示的样式-roomId为空
	 * @author 陈乐为 2018-7-23
	 *************************************************/
	public List<OperationItemByRequire> operationItemRequireChangeView(List<Object[]> operationItemRequireList);

	/**************************************
	 * Description 视图-实验要求统计表-count
	 * @author 陈乐为 2018-7-23
	 **************************************/
	public int operationItemRequireViewCount(HttpServletRequest request);

	/**
	 * Description 实验中心与实验室联动
	 * @param labCenter
	 * @param termId
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public Map<String, String> LinkLabCenter(String labCenter, Integer termId);

	/**
	 * Description 实验要求统计表导出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportOperationItemRequire(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * Description 实验变动统计表-roomId不为空
	 * @param request
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemChangeViews(HttpServletRequest request, int page ,int pageSize);

	/**
	 * Description 将查询的结果转化为页面方便显示的样式-roomId不为空
	 * @param operationItemChangeList
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public List<OperationItemByChange> operationItemChangeViews(List<Object[]> operationItemChangeList);

	/**
	 * Description 实验变动统计表-roomId为空
	 * @param request
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemChangeView(HttpServletRequest request, int page ,int pageSize);

	/**
	 * Description 将结果转化为页面方便显示的样式-roomId为空
	 * @param operationItemChangeList
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public List<OperationItemByChange> operationItemChangeView(List<Object[]> operationItemChangeList);

	/**
	 * Description 实验变动统计表-count
	 * @param request
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public int operationItemChangeViewCount(HttpServletRequest request);

	/**
	 * Description 实验变动统计表导出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportOperationItemChange(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/****************************************************
	 * @description：系统报表-实验学时数汇总表
	 * @author： 郑昕茹
	 * @date：2016-10-20
	 *****************************************************/
	@SuppressWarnings("rawtypes")
	public List getOperationItemStudentAndHours(HttpServletRequest request, Integer flag);

	/*************************************************************************************
	 * @description：实验学时数汇总表导出Excel
	 * @author：郑昕茹
	 * @date：2016-10-20
	 *************************************************************************************/
	public void exportOperationItemStudentAndHours(Integer flag, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/****************************************************
	 * 功能：系统报表-实验室利用率
	 * 作者： 贺子龙
	 * 日期：2016-10-14
	 *****************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getLabUseRate(HttpServletRequest request);

	/****************************************************
	 * 功能：系统报表-实验室利用率--实验室
	 * 作者： 贺子龙
	 * 日期：2016-10-14
	 *****************************************************/
	@SuppressWarnings("rawtypes")
	public List getLabUseRateRoom(Integer centerId, Integer termId);

	/**************************************************
	 * @description：實驗室使用率報表導出
	 * @author：陳樂為
	 * @date：2016-10-24
	 **************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportReportLabRate(HttpServletRequest request, HttpServletResponse response, List<Object[]> labRates,int flag) throws Exception;

	/**************************************
	 * Description 视图-实验室任务及耗材统计表-list
	 * @author 陈乐为
	 * @date 2016.05.04
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List viewTaskAndConsume(HttpServletRequest request, int page ,int pageSize);

	/**************************************
	 * Description 视图-实验室任务及耗材统计表-count
	 * @author 陈乐为
	 * @date 2016.05.04
	 **************************************/
	public int viewTaskAndConsumeCount(HttpServletRequest request);

	/*************************************************************************************
	 * Description 实验室任务及耗材统计表导出Excel
	 * @author 陈乐为
	 * @date 2016.05.04
	 *************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportTaskAndConsume(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/************************************************************
	 * Description 获取所有课程--课程类型试用
	 * @author 陈乐为
	 * @date 2016.05.16
	 ************************************************************/
	public Map<String, String> findSchoolCourseMapByQuery(Integer termId, int cid);

	/************************************************************
	 * Description 获取所有课程类型
	 * @author 陈乐为
	 * @date 2016.04.21
	 ************************************************************/
	public Map<String, String> findAllSchoolCourseTypeMap();

	/**************************************
	 * Description 视图-课程类型统计表-list
	 * @author 陈乐为
	 * @date 2016.04.14
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List viewSchoolCourseDetail(HttpServletRequest request, int page ,int pageSize, int labCenterId);

	/**************************************
	 * Description 视图-实践教学课程细化表-count
	 * @author 陈乐为
	 * @date 2016.04.14
	 **************************************/
	public int viewSchoolCourseDetailCount(HttpServletRequest request, int labCenterId);

	/**************************************************************************************
	 * Description 学期与课程联动
	 * @author 陈乐为
	 * @date 2016.04.19
	 **************************************************************************************/
	public Map<String, String> LinkSchoolTerm(String schoolTerm);

	/**************************************************************************************
	 * Description 学期与中心联动
	 * @author 陈乐为
	 * @date 2016.05.18
	 **************************************************************************************/
	public Map<String, String> LinkSchoolTerms(String schoolTerm);

	/*************************************************************************************
	 * Description 课程类型统计表导出Excel
	 * @author 陈乐为
	 * @date 2016.04.14
	 *************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportSchoolCourseDetailType(HttpServletRequest request, HttpServletResponse response, String acno) throws Exception;

	/**
	 * 获取所有实验室教学情况汇总表数据-totalRecords
	 *
	 * @param request
	 * @return
	 * @author 罗璇
	 * @date 2018年4月24日
	 */
	public Integer countOperationItemReport(HttpServletRequest request);

	/**
	 * 获取所有实验室教学情况汇总表数据-分页
	 *
	 * @param request
	 * @param pageModel
	 * @return
	 * @author 罗璇
	 * @date 2018年4月24日
	 */
	public List<Object[]> getOperationItemReport(HttpServletRequest request, Map<String, Integer> pageModel);

	/**
	 * 获取实验室教学情况汇总表数据(课程数，课程类型，试验项目)
	 *
	 * @param request
	 * @return
	 * @author 罗璇
	 * @date 2018年4月25日
	 */
	public Object[] countOperationItemReportRecords(HttpServletRequest request);

	/**
	 * 导出实验室教学情况汇总表
	 * @author 罗璇
	 * @date 2018年4月24日
	 * @param request
	 * @param response
	 * @param labRates
	 * @throws Exception
	 */
	public void exportOperationItemReport(HttpServletRequest request, HttpServletResponse response, List<Object[]> labRates) throws Exception;

	/**
	 * 获取所有实验室教学情况汇总表数据
	 *
	 * @param request
	 * @return
	 * @author 罗璇
	 * @date 2018年4月23日
	 */
	public List<Object[]> getOperationItemReport(HttpServletRequest request);

	/**
	 * @Description 根据查询条件获取督导教务实验课表数据（分页）
	 * @param request
	 * @author 张德冰
	 * @date 2018-9-19
	 */
	public List<Object[]> getReportSupervise(HttpServletRequest request, Integer currpage, Integer pageSize, String acno);

	/**
	 * @Description 根据查询条件获取督导教务实验课表数据数量
	 * @param request
	 * @author 张德冰
	 * @date 2018-9-19
	 */
	public Integer getCountReportSupervise(HttpServletRequest request, String acno);

	/**
	 * Description 月报报表
	 * @param queryParamsVO
	 * @return
	 * @author 陈乐为 2019年4月16日
	 */
	public List<Object[]> getMonthReport(QueryParamsVO queryParamsVO);
}
