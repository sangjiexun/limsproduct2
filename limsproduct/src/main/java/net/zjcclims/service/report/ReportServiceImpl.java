package net.zjcclims.service.report;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.constant.OperationItemByCategory;
import net.zjcclims.constant.OperationItemByChange;
import net.zjcclims.constant.OperationItemByRequire;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.vo.QueryParamsVO;
import org.apache.cxf.common.util.SortedArraySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.*;

@Service("ReportService")
public class ReportServiceImpl implements ReportService 
{
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	private SchoolDeviceDAO schoolDeviceDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private TimetableCourseStudentDAO timetableCourseStudentDAO;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired 
	private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	private LabReservationDAO labReservationDAO;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	private ReportRateDAO reportRateDAO;
	@Autowired
	private ReportParameterDAO reportParameterDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private SchoolCourseInfoDAO schoolCourseInfoDAO;
	
	public ReportServiceImpl(){
	}

	/**
	 * 根据字符串获取学期集合
	 * @param queryStr 形式如1,4
	 * @author hely
	 * 2014.09.10
	 */
	@Override
	public List<SchoolTerm> getSchoolTermsByStr(String queryStr) 
	{
		StringBuffer hql = new StringBuffer("select t from SchoolTerm t where 1=1 ");
		if(queryStr != null && !"".equals(queryStr))
		{
			hql.append(" and t.id in ("+queryStr+") ");
		}
		
	    return schoolTermDAO.executeQuery(hql.toString(), 0, -1);
	}
	
	/**
	 * 根据字符串获取学期，用于下拉框
	 * @param queryStr 形式如 1,4
	 * @param flag 为true则查出字符串所包含的学期，否则查出不包含的学期
	 * @author hely
	 * 2014.09.10
	 */
	@Override
	public Map<Integer, String> getSelectTerms(String queryStr, String yearCode, boolean flag) 
	{
		StringBuffer hql = new StringBuffer("select t from SchoolTerm t where 1=1 ");
		if(yearCode != null && !"".equals(yearCode))
		{
			hql.append(" and t.yearCode = '"+yearCode+"' ");
		}
		if(queryStr != null && !"".equals(queryStr))
		{
			if(flag)
			{
				hql.append(" and t.id in ("+queryStr+")");
			}
			else
			{
				hql.append(" and t.id not in ("+queryStr+")");
			}
		}
		List<SchoolTerm> schoolTerms = schoolTermDAO.executeQuery(hql.toString(), 0, -1);
		
		Map<Integer, String> termsMap = new LinkedHashMap<Integer, String>();
		for (SchoolTerm schoolTerm : schoolTerms) 
		{
			termsMap.put(schoolTerm.getId(), schoolTerm.getTermName());
		}
		
		return termsMap;
	}
	
	/**
	 * 获取需要计算绩效报表学院的名称字符串
	 * @author hely
	 * 2014.09.04
	 */
	@Override
	public String getAcademyNames() 
	{
		StringBuffer academyNames = new StringBuffer();
		
		List<ReportRate> reportRates = getlabRoomByQuery(); //获取需要计算绩效报表的学院

		if(reportRates.size()>0) {
			for (ReportRate reportRate : reportRates) {
				academyNames.append("'" + reportRate.getLabRoom().getLabRoomNumber() + "',");
			}

			return academyNames.deleteCharAt(academyNames.length() - 1).toString();
		}else{
			return "";
		}
	}
	
	/**
	 * 获取需要计算绩效报表的学院
	 * @author hely
	 * 2014.09.02
	 */
	@Override
	public List<SchoolAcademy> getSchoolAcademyByQuery() 
	{
		StringBuffer hql = new StringBuffer("select sa from SchoolAcademy sa where sa.academyNumber like '101%'");
		hql.append(" order by sa.academyNumber asc ");
		List<SchoolAcademy> schoolAcademies = schoolAcademyDAO.executeQuery(hql.toString(), 0, -1);
		
		return schoolAcademies;
	}
	/**
	 * 获取需要计算绩效报表的实验室
	 * @author zhangyu
	 * 
	 */
	@Override
	public List<ReportRate> getlabRoomByQuery() 
	{
		StringBuffer hql = new StringBuffer("select r from ReportRate r where 1=1");
		//hql.append(" order by sa.academyNumber asc ");
		List<ReportRate> reportRates = reportRateDAO.executeQuery(hql.toString(), 0, -1);
		
		return reportRates;
	}
	/**
	 * 得到学年的数据，用于下拉框
	 * @author hely
	 * 2014.09.10
	 */
	@Override
	public Map<Integer, String> getTermsMap() 
	{
		StringBuffer hql = new StringBuffer("select t from SchoolTerm t order by t.termStart desc");
		List<SchoolTerm> schoolTerms = schoolTermDAO.executeQuery(hql.toString(), 0, -1);
		
		Map<Integer, String> termsMap = new LinkedHashMap<Integer, String>(); //使用LinkedHashMap是为了保证顺序和插入顺序一致
//		termsMap.put(0, "---请选择---");
		for (SchoolTerm schoolTerm : schoolTerms) 
		{
			termsMap.put(schoolTerm.getId(), schoolTerm.getTermName());
		}
		
		return termsMap;
	}
	
	/**
	 * 根据指定学期查询绩效报表信息
	 * @param schoolTerms 学期
	 * @author hely
	 * 2014.09.04
	 */
	@Override
	public List<ReportRate> getReportRateByTerms(List<SchoolTerm> schoolTerms) 
	{
		StringBuffer terms = new StringBuffer();
		for (SchoolTerm schoolTerm : schoolTerms) 
		{
			terms.append(schoolTerm.getId()+",");
		}
		if(terms.length() > 0)
		{
			terms.deleteCharAt(terms.length()-1); //去掉最后一个逗号
		}
		
		StringBuffer hql = new StringBuffer("select rr from ReportRate rr where rr.terms = '"+terms+"' order by rr.schoolAcademy.academyNumber ");
		List<ReportRate> reportRates = reportRateDAO.executeQuery(hql.toString(), 0, -1);
		
		return reportRates;
	}
	/**
	 * 根据学院和所给时间段获取实验室利用率公式中涉及到的数据
	 * @param schoolTerms  时间段
	 * @author hely
	 * 2014.08.26
	 */
	@Override
	public List<LabRoom> getLabRateDetailInfo(List<SchoolTerm> schoolTerms) 
	{
		List<LabRoom> labRooms = new ArrayList<LabRoom>(); //用于JSP页面遍历
		List<SchoolAcademy> schoolAcademies = getSchoolAcademyByQuery(); //找出需要计算绩效报表的学院
		
		for (SchoolAcademy schoolAcademy : schoolAcademies) 
		{
			LabRoom labRoom = new LabRoom();
			String academyNumber = schoolAcademy.getAcademyNumber(); //学院编号
			ReportRate reportRate =  getReportRateByTermsAcademy(academyNumber, schoolTerms);
			int studentTimeSum = 0; //实验人时数
			if(reportRate!=null && reportRate.getStudentTime() != null)
			{
				studentTimeSum = reportRate.getStudentTime();
			}
			int labRoomCapacity = getLabCapacityByAcademy(academyNumber); //实验室容量
			int ratedCourseTime = getRatedCourseTime(academyNumber); //实验室额定课时
			double ratedCourseTimeTerm = (schoolTerms.size() == 1)? ratedCourseTime/2.0:ratedCourseTime;
			
			labRooms.add(labRoom);
		}
		
		return labRooms;
	}


	/**
	 * 获取指定学期的学院绩效指标数据
	 * @param academyNumber 学院编号
	 * @param schoolTerms 学期（学年或者单个学期）
	 * @author hely
	 * 2014.12.11
	 */
	@Override
	public ReportRate getReportRateByTermsAcademy(String academyNumber, List<SchoolTerm> schoolTerms)
	{
		StringBuffer terms = new StringBuffer();
		for (SchoolTerm schoolTerm : schoolTerms) 
		{
			terms.append(schoolTerm.getId()+",");
		}
		if(terms.length() > 0)
		{
			terms.deleteCharAt(terms.length()-1); //去掉最后一个逗号
		}
		
		StringBuffer hql = new StringBuffer("select rr from ReportRate rr where rr.terms = '"+terms+"' and rr.schoolAcademy.academyNumber='"+academyNumber+"' ");
		List<ReportRate> reportRates = reportRateDAO.executeQuery(hql.toString(), 0, -1);
		
		if(reportRates.size() > 0)
		{
			return reportRates.get(0);  //指定学院、指定学期的数据只有一条
		}
		return null;
	}


	/**
	 * 获取学院实验室容量总和
	 * @param academyNumber 学院编号
	 * @author hely
	 * 2014.08.26
	 */
	@Override
	public int getLabCapacityByAcademy(String academyNumber) 
	{
		int LabCapacitySum = 0;
		
		StringBuffer hql = new StringBuffer("select sum(lr.labRoomCapacity) from LabRoom lr where lr.labAnnex.labCenter.schoolAcademy.academyNumber='"+academyNumber+"'");
		try 
		{
			LabCapacitySum =  ((Long) labRoomDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
		} catch (Exception e) {}
				
		return LabCapacitySum;
	}

	/**
	 * 实验室额定课时数（学校给的数据）
	 * @author hely
	 * 2014.08.06
	 */
	@Override
	public int getRatedCourseTime(String academyNumber) 
	{
		int ratedCourseTime = 0;
		
		StringBuffer hql = new StringBuffer("select r from ReportParameter r where r.schoolAcademy.academyNumber=?");
		List<ReportParameter> reportParameters = reportParameterDAO.executeQuery(hql.toString(), 0, -1, academyNumber);
		
		if(reportParameters.size() > 0)
		{
			if(reportParameters.get(0).getRatedCourseTime() != null)
				ratedCourseTime = reportParameters.get(0).getRatedCourseTime();
		}
		
		return ratedCourseTime;
	}
	
	/**
	 * 根据学年编号(year_code)获取学期
	 * @param yearCode 学年编号
	 * @author hely
	 * 2014.09.10
	 */
	public String getTermsByYearCode(String yearCode)
	{
		StringBuffer hql = new StringBuffer("select t from SchoolTerm t where t.yearCode='"+yearCode+"' order by t.termStart asc");
		List<SchoolTerm> shoolTerms = schoolTermDAO.executeQuery(hql.toString(), 0, -1);
		
		StringBuffer options = new StringBuffer();
		for (SchoolTerm schoolTerm : shoolTerms) 
		{
			options.append("<option value='"+schoolTerm.getId()+"'>"+schoolTerm.getTermName()+"</option>");
		}
		
		return options.toString();
	}

	/*******************************************************************
	 * @description：根据页面所选roomID查询
	 * @param roomIds 多选实验室查询条件
	 * @author：陈乐为 2018-7-20
	 *******************************************************************/
	@Override
	public Map<Integer, String> getLabRoomSelectedMap(String[] roomIds) {
		String roomIdStr = "";
		for(String s : roomIds){
			roomIdStr +=  s + "," ;
		}
		roomIdStr = roomIdStr.substring(0, roomIdStr.length()-1);
		String sql = "select c from LabRoom c where c.id in ("+roomIdStr+")";
		sql += " group by c.id";
		List<LabRoom> labRooms = labRoomDAO.executeQuery(sql);
		Map<Integer, String> labRoomMap = new HashMap<Integer, String>();
		for (LabRoom labRoom : labRooms) {
			if(labRoom.getId()!=null){
				labRoomMap.put(labRoom.getId(),labRoom.getLabRoomName());
			}
		}
		return labRoomMap;
	}

	/********************************************************************
	 * @description：获取所有中心
	 * @author：陈乐为
	 * @date：2018-7-20
	 ********************************************************************/
	@Override
	public Map<Integer, String> getAllLabCenterMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		String sql=" select l from LabCenter l ";
		List<LabCenter> centerList = labCenterDAO.executeQuery(sql, -1,0);
		for (LabCenter labCenter : centerList) {
			map.put(labCenter.getId(),labCenter.getCenterName());
		}
		return map;
	}

	/**
	 * Description 获取实验室-实验类型统计专用-中心&学期&实验类型&审核通过
	 * @param centerId
	 * @param termId
	 * @return
	 * @author 陈乐为 2018-7-20
	 */
	@Override
	public Map<Integer, String> getLabRoomsListMap(Integer centerId, Integer termId) {
		String sql = "select c from OperationItem l " +
				" left join l.labRooms c" +
				" where 1=1";
		sql += " and (l.CDictionaryByLpStatusCheck.CCategory='status_operation_item_check' and " +
				" l.CDictionaryByLpStatusCheck.CNumber = 3)";
		if(centerId != 0) {
			sql += " and c.labCenter.id like '" + centerId + "'";
		}
		sql += " and l.schoolTerm.id like '"+termId+"'";
		sql += " and (l.CDictionaryByLpCategoryApp.CCategory='category_operation_item_app' and (" +
				" l.CDictionaryByLpCategoryApp.CNumber=1" +
				" or l.CDictionaryByLpCategoryApp.CNumber=2" +
				" or l.CDictionaryByLpCategoryApp.CNumber=3" +
				" or l.CDictionaryByLpCategoryApp.CNumber=4" +
				" or l.CDictionaryByLpCategoryApp.CNumber=5))";
		sql += " group by c.id";
		List<LabRoom> labRooms = labRoomDAO.executeQuery(sql);
		Map<Integer, String> labRoomMap = new HashMap<Integer, String>();
		for (LabRoom labRoom : labRooms) {
			if(labRoom.getId()!=null){
				labRoomMap.put(labRoom.getId(),labRoom.getLabRoomName());
			}
		}
		return labRoomMap;
	}

	/**
	 * Description 实验类型统计表
	 * @param request
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemViews(HttpServletRequest request, int page , int pageSize){
		String sql = "select * from view_report_labtype_5 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId()-1;
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.labRoomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		sql+=" order by v.termId, v.centerId, v.labRoomId";

		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}

	/*************************************************
	 * Description 将实验室项目卡按实验类型分类的查询的结果转化为页面方便显示的样式
	 * @param operationItemTypeList 项目集
	 * @author 陈乐为 2018-7-20
	 *************************************************/
	@Override
	public List<OperationItemByCategory> operationItemTypechangeViews(List<Object[]> operationItemTypeList){

		List<OperationItemByCategory> items = new ArrayList<OperationItemByCategory>();
		int roomId = 0;
		for (Object[] operationItemType : operationItemTypeList) {
			if (Integer.parseInt(operationItemType[5].toString()) != roomId) {// 该条件决定了每个实验室创建一条OperationItemByCategory记录
				// 将当前中心主键赋值
				roomId = Integer.parseInt(operationItemType[5].toString());
				// 创建一个OperationItemByCategory对象
				OperationItemByCategory item = new OperationItemByCategory();
				// 记录通用的几个字段
				item.setCenterNumber(operationItemType[3].toString());
				item.setCenterName(operationItemType[2].toString());
				item.setLabRoomNumber(operationItemType[5].toString());
				item.setLabRoomName(operationItemType[4].toString());
				item.setCountTotal(Integer.parseInt(operationItemType[13].toString()));
				item.setHourTotal(Integer.parseInt(operationItemType[14].toString()));
				item.setCountTotalLab(Integer.parseInt(operationItemType[15].toString()));
				item.setHourTotalLab(Integer.parseInt(operationItemType[16].toString()));
				// 需要结合centerId相同的所有记录的字段
				for (Object[] operationItemTypeInn : operationItemTypeList) {
					if (Integer.parseInt(operationItemTypeInn[5].toString()) == roomId) {
						switch (Integer.parseInt(operationItemTypeInn[9].toString())) {
							case 464:// 演示
								item.setCount1(Integer.parseInt(operationItemTypeInn[6].toString()));
								item.setHour1(Integer.parseInt(operationItemTypeInn[7].toString()));
								item.setCenterCount1(Integer.parseInt(operationItemTypeInn[10].toString()));
								item.setCenterHour1(Integer.parseInt(operationItemTypeInn[11].toString()));
								break;
							case 465:// 验证
								item.setCount2(Integer.parseInt(operationItemTypeInn[6].toString()));
								item.setHour2(Integer.parseInt(operationItemTypeInn[7].toString()));
								item.setCenterCount2(Integer.parseInt(operationItemTypeInn[10].toString()));
								item.setCenterHour2(Integer.parseInt(operationItemTypeInn[11].toString()));
								break;
							case 466:// 综合
								item.setCount3(Integer.parseInt(operationItemTypeInn[6].toString()));
								item.setHour3(Integer.parseInt(operationItemTypeInn[7].toString()));
								item.setCenterCount3(Integer.parseInt(operationItemTypeInn[10].toString()));
								item.setCenterHour3(Integer.parseInt(operationItemTypeInn[11].toString()));
								break;
							case 467:// 设计研究
								item.setCount4(Integer.parseInt(operationItemTypeInn[6].toString()));
								item.setHour4(Integer.parseInt(operationItemTypeInn[7].toString()));
								item.setCenterCount4(Integer.parseInt(operationItemTypeInn[10].toString()));
								item.setCenterHour4(Integer.parseInt(operationItemTypeInn[11].toString()));
								break;

							default:// 其他
								item.setCount5(Integer.parseInt(operationItemTypeInn[6].toString()));
								item.setHour5(Integer.parseInt(operationItemTypeInn[7].toString()));
								item.setCenterCount5(Integer.parseInt(operationItemTypeInn[10].toString()));
								item.setCenterHour5(Integer.parseInt(operationItemTypeInn[11].toString()));
								break;
						}
					}
				}
				items.add(item);
			}
		}
		return items;
	}

	/**************************************
	 * Description 视图-实验类型统计表-list
	 * @author 陈乐为 2018-7-20
	 **************************************/
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemView(HttpServletRequest request, int page ,int pageSize){
		String sql = "select * from view_report_labtype_6 v where 1=1";
		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = "+term;

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.labRoomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}

		sql+=" order by v.centerId";
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		//operationItemTypechangeView(list);
		return list;
	}

	/*************************************************
	 * Description 将实验中心项目卡按实验类型分类的查询的结果转化为页面方便显示的样式
	 * @param operationItemTypeList 项目集
	 * @author 陈乐为 2018-7-20
	 *************************************************/
	@Override
	public List<OperationItemByCategory> operationItemTypechangeView(List<Object[]> operationItemTypeList){

		List<OperationItemByCategory> items = new ArrayList<OperationItemByCategory>();
		int centerId = 0;
		for (Object[] operationItemType : operationItemTypeList) {
			if (Integer.parseInt(operationItemType[3].toString())!=centerId) {// 该条件决定了每个中心创建一条OperationItemByCategory记录
				// 将当前中心主键赋值
				centerId = Integer.parseInt(operationItemType[3].toString());
				// 创建一个OperationItemByCategory对象
				OperationItemByCategory item = new OperationItemByCategory();
				// 记录通用的几个字段
				item.setCenterNumber(operationItemType[12].toString());
				item.setCenterName(operationItemType[2].toString());
				item.setCountTotal(Integer.parseInt(operationItemType[8].toString()));
				item.setHourTotal(Integer.parseInt(operationItemType[9].toString()));
				item.setCountTotalCollege(Integer.parseInt(operationItemType[10].toString()));
				item.setHourTotalCollege(Integer.parseInt(operationItemType[11].toString()));
				// 需要结合centerId相同的所有记录的字段
				for (Object[] operationItemTypeInn : operationItemTypeList) {
					if (Integer.parseInt(operationItemTypeInn[3].toString())==centerId) {// 当前中心下的5中类型的集合
						switch (Integer.parseInt(operationItemTypeInn[7].toString())) {// 类型主键作为筛选条件
							case 464:// 演示
								item.setCount1(Integer.parseInt(operationItemTypeInn[4].toString()));
								item.setHour1(Integer.parseInt(operationItemTypeInn[5].toString()));
								break;
							case 465:// 验证
								item.setCount2(Integer.parseInt(operationItemTypeInn[4].toString()));
								item.setHour2(Integer.parseInt(operationItemTypeInn[5].toString()));
								break;
							case 466:// 综合
								item.setCount3(Integer.parseInt(operationItemTypeInn[4].toString()));
								item.setHour3(Integer.parseInt(operationItemTypeInn[5].toString()));
								break;
							case 467:// 设计研究
								item.setCount4(Integer.parseInt(operationItemTypeInn[4].toString()));
								item.setHour4(Integer.parseInt(operationItemTypeInn[5].toString()));
								break;

							default:// 其他
								item.setCount5(Integer.parseInt(operationItemTypeInn[4].toString()));
								item.setHour5(Integer.parseInt(operationItemTypeInn[5].toString()));
								break;
						}
					}
				}
				items.add(item);
			}
		}
		return items;
	}

	/**************************************
	 * Description 视图-实验类型统计表-count
	 * @author 陈乐为 2018-7-20
	 **************************************/
	@Override
	public int operationItemViewCount(HttpServletRequest request){
		String sql = "";
		// 判断是从哪个业务表中查询
		if(request.getParameterValues("roomId") != null){
			sql += "select count(*) from view_report_labtype_5 v where 1=1";
		}else{
			sql += "select count(*) from view_report_labtype_6 v where 1=1";
		}
		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";
		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}
		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.labRoomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		Query query = entityManager.createNativeQuery(sql);
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/*************************************************************************************
	 * Description 实验类型统计表导出Excel
	 * @author 陈乐为 2018-7-20
	 *************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void exportOperationItemType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map> lists = new ArrayList<Map>();
		String sql = "";
		if(request.getParameter("roomId") != null){
			sql += "select * from view_report_labtype_5 v where 1=1";
		}else{
			sql += "select * from view_report_labtype_6 v where 1=1";
		}

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}
		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.labRoomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		if(request.getParameter("roomId") != null) {
			sql += " order by v.termId, v.centerId, v.labRoomId";
		}else {
			sql+=" order by v.centerId";
		}
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();

		//实验中心所在学院的实验室项目
		int i=1;
		if(request.getParameter("roomId") != null){
			for (OperationItemByCategory operationItemByCategory : operationItemTypechangeViews(list))
			{
				Map map = new HashMap();

				map.put("serial number",i);//序号
				i++;
				map.put("2",operationItemByCategory.getLabRoomNumber());//单位编号
				map.put("3",operationItemByCategory.getLabRoomName());//单位名称
				map.put("4",operationItemByCategory.getCountTotalLab());//实开实验个数
				map.put("5",operationItemByCategory.getHourTotalLab());//实开实验时数
				map.put("6",operationItemByCategory.getCount1());//演示实验个数
				map.put("7",operationItemByCategory.getHour1());//演示实验时数
				map.put("8",(float)operationItemByCategory.getCount1()/operationItemByCategory.getCountTotal()*100);//演示实验个数比
				map.put("9",(float)operationItemByCategory.getHour1()/operationItemByCategory.getHourTotal()*100);//演示实验时数比
				map.put("10",operationItemByCategory.getCount2());//验证实验个数
				map.put("11",operationItemByCategory.getHour2());//验证实验时数
				map.put("12",(float)operationItemByCategory.getCount2()/operationItemByCategory.getCountTotal()*100);//验证实验个数比
				map.put("13",(float)operationItemByCategory.getHour2()/operationItemByCategory.getHourTotal()*100);//验证实验时数比
				map.put("14",operationItemByCategory.getCount3());//综合实验个数
				map.put("15",operationItemByCategory.getHour3());//综合实验时数
				map.put("16",(float)operationItemByCategory.getCount3()/operationItemByCategory.getCountTotal()*100);//综合实验个数比
				map.put("17",(float)operationItemByCategory.getHour3()/operationItemByCategory.getHourTotal()*100);//综合实验时数比
				map.put("18",operationItemByCategory.getCount4());//设计实验个数
				map.put("19",operationItemByCategory.getHour4());//设计实验时数
				map.put("20",(float)operationItemByCategory.getCount4()/operationItemByCategory.getCountTotal()*100);//设计实验个数比
				map.put("21",(float)operationItemByCategory.getHour4()/operationItemByCategory.getHourTotal()*100);//设计实验时数比
				map.put("22",operationItemByCategory.getCount5());//其他实验个数
				map.put("23",operationItemByCategory.getHour5());//其他实验时数
				map.put("24",(float)operationItemByCategory.getCount5()/operationItemByCategory.getCountTotal()*100);//其他实验个数比
				map.put("25",(float)operationItemByCategory.getHour5()/operationItemByCategory.getHourTotal()*100);//其他实验时数比
				lists.add(map);
			}  //实验室遍历
		}else{
			if(operationItemTypechangeView(list).size() != 1){
				int count1College=0;
				int hour1College=0;
				int count2College=0;
				int hour2College=0;
				int count3College=0;
				int hour3College=0;
				int count4College=0;
				int hour4College=0;
				int count5College=0;
				int hour5College=0;
				for(OperationItemByCategory operationItemByCategory : operationItemTypechangeView(list)){
					count1College += operationItemByCategory.getCount1();
					hour1College += operationItemByCategory.getHour1();
					count2College += operationItemByCategory.getCount2();
					hour2College += operationItemByCategory.getHour2();
					count3College += operationItemByCategory.getCount3();
					hour3College += operationItemByCategory.getHour3();
					count4College += operationItemByCategory.getCount4();
					hour4College += operationItemByCategory.getHour4();
					count5College += operationItemByCategory.getCount5();
					hour5College += operationItemByCategory.getHour5();
				}
				for(OperationItemByCategory operationItemByCategory : operationItemTypechangeView(list)){
					Map map = new HashMap();
					map.put("serial number","0");
					map.put("2","clw201807");//单位编号
					map.put("3","clw庚商学院");//单位名称
					map.put("4",operationItemByCategory.getCountTotalCollege());//实开实验个数
					map.put("5",operationItemByCategory.getHourTotalCollege());//实开实验时数
					map.put("6",count1College);//必修实验个数
					map.put("7",hour1College);//必修实验时数
					map.put("8",(float)count1College/operationItemByCategory.getCountTotalCollege()*100);//必修实验个数比
					map.put("9",(float)hour1College/operationItemByCategory.getHourTotalCollege()*100);//必修实验时数比
					map.put("10",count2College);//选修实验个数
					map.put("11",hour2College);//选修实验时数
					map.put("12",(float)count2College/operationItemByCategory.getCountTotalCollege()*100);//选修实验个数比
					map.put("13",(float)hour2College/operationItemByCategory.getHourTotalCollege()*100);//选修实验时数比
					map.put("14",count3College);//其他实验个数
					map.put("15",hour3College);//其他实验时数
					map.put("16",(float)count3College/operationItemByCategory.getCountTotalCollege()*100);//其他实验个数比
					map.put("17",(float)hour3College/operationItemByCategory.getHourTotalCollege()*100);//其他实验时数比
					map.put("18",count4College);//选修实验个数
					map.put("19",hour4College);//选修实验时数
					map.put("20",(float)count4College/operationItemByCategory.getCountTotalCollege()*100);//选修实验个数比
					map.put("21",(float)hour4College/operationItemByCategory.getHourTotalCollege()*100);//选修实验时数比
					map.put("22",count5College);//其他实验个数
					map.put("23",hour5College);//其他实验时数
					map.put("24",(float)count5College/operationItemByCategory.getCountTotalCollege()*100);//其他实验个数比
					map.put("25",(float)hour5College/operationItemByCategory.getHourTotalCollege()*100);//其他实验时数比
					lists.add(map);
				}
			}
			for (OperationItemByCategory operationItemByCategory : operationItemTypechangeView(list))
			{
				Map map = new HashMap();

				map.put("serial number",i);//序号
				i++;
				map.put("2",operationItemByCategory.getCenterNumber());//单位编号
				map.put("3",operationItemByCategory.getCenterName());//单位名称
				map.put("4",operationItemByCategory.getCountTotal());//实开实验个数
				map.put("5",operationItemByCategory.getHourTotal());//实开实验时数
				map.put("6",operationItemByCategory.getCount1());//演示实验个数
				map.put("7",operationItemByCategory.getHour1());//演示实验时数
				map.put("8",(float)operationItemByCategory.getCount1()/operationItemByCategory.getCountTotal()*100);//演示实验个数比
				map.put("9",(float)operationItemByCategory.getHour1()/operationItemByCategory.getHourTotal()*100);//演示实验时数比
				map.put("10",(float)operationItemByCategory.getCount2());//验证实验个数
				map.put("11",(float)operationItemByCategory.getHour2());//验证实验时数
				map.put("12",(float)operationItemByCategory.getCount2()/operationItemByCategory.getCountTotal()*100);//验证实验个数比
				map.put("13",(float)operationItemByCategory.getHour2()/operationItemByCategory.getHourTotal()*100);//验证实验时数比
				map.put("14",(float)operationItemByCategory.getCount3());//综合实验个数
				map.put("15",(float)operationItemByCategory.getHour3());//综合实验时数
				map.put("16",(float)operationItemByCategory.getCount3()/operationItemByCategory.getCountTotal()*100);//综合实验个数比
				map.put("17",(float)operationItemByCategory.getHour3()/operationItemByCategory.getHourTotal()*100);//综合实验时数比
				map.put("18",(float)operationItemByCategory.getCount4());//设计实验个数
				map.put("19",(float)operationItemByCategory.getHour4());//设计实验时数
				map.put("20",(float)operationItemByCategory.getCount4()/operationItemByCategory.getCountTotal()*100);//设计实验个数比
				map.put("21",(float)operationItemByCategory.getHour4()/operationItemByCategory.getHourTotal()*100);//设计实验时数比
				map.put("22",(float)operationItemByCategory.getCount5());//其他实验个数
				map.put("23",(float)operationItemByCategory.getHour5());//其他实验时数
				map.put("24",(float)operationItemByCategory.getCount5()/operationItemByCategory.getCountTotal()*100);//其他实验个数比
				map.put("25",(float)operationItemByCategory.getHour5()/operationItemByCategory.getHourTotal()*100);//其他实验时数比
				lists.add(map);
			}  //实验室遍历
		}
		String title = "实验类型统计表";
		if(request.getParameter("roomId") != null){
			String[] hearders = new String[] {"序号","单位编号","单位名称","实开实验个数","实开实验时数","演示实验个数","演示实验时数","演示实验个数比","演示实验时数比","验证实验个数","验证实验时数","验证实验个数比",
					"验证实验时数比","综合实验个数","综合实验时数","综合实验个数比","综合实验时数比","设计实验个数","设计实验时数","设计实验个数比","设计实验时数比","其他实验个数","其他实验时数","其他实验个数比","其他实验时数比"};//表头数组
			String[] fields = new String[] {"serial number","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
			TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "实验类型统计表", td);
		}else{
			String[] hearders = new String[] {"序号","单位编号","单位名称","实开实验个数","实开实验时数","演示实验个数","演示实验时数","演示实验个数比","演示实验时数比","验证实验个数","验证实验时数","验证实验个数比",
					"验证实验时数比","综合实验个数","综合实验时数","综合实验个数比","综合实验时数比","设计实验个数","设计实验时数","设计实验个数比","设计实验时数比","其他实验个数","其他实验时数","其他实验个数比","其他实验时数比"};//表头数组
			String[] fields = new String[] {"serial number","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
			TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "实验类型统计表", td);
		}
	}

	/**************************************
	 * Description 视图-实验要求统计表-roomId不为空
	 * @author 陈乐为 2018-7-23
	 **************************************/
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemRequireViews(HttpServletRequest request, int page ,int pageSize){
		String sql = "select * from view_report_require_6 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		sql+=" order by v.termId, v.centerId, v.roomId";
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		this.operationItemRequireChangeViews(list);
		return list;
	}

	/*************************************************
	 * Description 将查询的结果转化为页面方便显示的样式-roomId不为空
	 * @author 陈乐为 2018-7-23
	 *************************************************/
	public List<OperationItemByRequire> operationItemRequireChangeViews(List<Object[]> operationItemRequireList){

		List<OperationItemByRequire> items = new ArrayList<OperationItemByRequire>();
		int roomId = 0;
		for (Object[] operationItemRequire : operationItemRequireList) {
			if (Integer.parseInt(operationItemRequire[5].toString()) != roomId) {// 该条件决定了每个实验室创建一条OperationItemByCategory记录
				// 将当前中心主键赋值
				roomId = Integer.parseInt(operationItemRequire[5].toString());
				// 创建一个OperationItemByCategory对象
				OperationItemByRequire item = new OperationItemByRequire();
				// 记录通用的几个字段
				item.setCenterNumber(operationItemRequire[13].toString());
				item.setCenterName(operationItemRequire[2].toString());
				item.setLabRoomNumber(operationItemRequire[12].toString());
				item.setLabRoomName(operationItemRequire[4].toString());
				item.setCountTotal(Integer.parseInt(operationItemRequire[14].toString()));
				item.setHourTotal(Integer.parseInt(operationItemRequire[15].toString()));
				item.setCountTotalLab(Integer.parseInt(operationItemRequire[10].toString()));
				item.setHourTotalLab(Integer.parseInt(operationItemRequire[11].toString()));
				// 需要结合centerId相同的所有记录的字段
				for (Object[] operationItemRequireInn : operationItemRequireList) {
					if (Integer.parseInt(operationItemRequireInn[5].toString()) == roomId) {
						switch (Integer.parseInt(operationItemRequireInn[7].toString())) {
							case 485:// 必修
								item.setCount1(Integer.parseInt(operationItemRequireInn[8].toString()));
								item.setHour1(Integer.parseInt(operationItemRequireInn[9].toString()));
								item.setCenterCount1(Integer.parseInt(operationItemRequireInn[16].toString()));
								item.setCenterHour1(Integer.parseInt(operationItemRequireInn[17].toString()));
								break;
							case 486:// 选修
								item.setCount2(Integer.parseInt(operationItemRequireInn[8].toString()));
								item.setHour2(Integer.parseInt(operationItemRequireInn[9].toString()));
								item.setCenterCount2(Integer.parseInt(operationItemRequireInn[16].toString()));
								item.setCenterHour2(Integer.parseInt(operationItemRequireInn[17].toString()));
								break;
							default:// 其他
								item.setCount3(Integer.parseInt(operationItemRequireInn[8].toString()));
								item.setHour3(Integer.parseInt(operationItemRequireInn[9].toString()));
								item.setCenterCount3(Integer.parseInt(operationItemRequireInn[16].toString()));
								item.setCenterHour3(Integer.parseInt(operationItemRequireInn[17].toString()));
								break;
						}
					}
				}
				items.add(item);
			}
		}
		return items;
	}

	/**************************************
	 * Description 视图-实验要求统计表-roomId为空
	 * @author 陈乐为 2018-7-23
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemRequireView(HttpServletRequest request, int page ,int pageSize){
		String sql = "select * from view_report_require_5 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}

		sql+=" order by v.centerId";
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		this.operationItemRequireChangeView(list);
		return list;
	}

	/*************************************************
	 * Description 将结果转化为页面方便显示的样式-roomId为空
	 * @author 陈乐为 2018-7-23
	 *************************************************/
	public List<OperationItemByRequire> operationItemRequireChangeView(List<Object[]> operationItemRequireList){

		List<OperationItemByRequire> items = new ArrayList<OperationItemByRequire>();
		int centerId = 0;
		for (Object[] operationItemRequire : operationItemRequireList) {
			if (Integer.parseInt(operationItemRequire[3].toString())!=centerId) {// 该条件决定了每个中心创建一条OperationItemByCategory记录
				// 将当前中心主键赋值
				centerId = Integer.parseInt(operationItemRequire[3].toString());
				// 创建一个OperationItemByCategory对象
				OperationItemByRequire item = new OperationItemByRequire();
				// 记录通用的几个字段
				item.setCenterNumber(operationItemRequire[14].toString());
				item.setCenterName(operationItemRequire[2].toString());
				item.setCountTotal(Integer.parseInt(operationItemRequire[8].toString()));
				item.setHourTotal(Integer.parseInt(operationItemRequire[9].toString()));
				item.setCountTotalCollege(Integer.parseInt(operationItemRequire[10].toString()));
				item.setHourTotalCollege(Integer.parseInt(operationItemRequire[11].toString()));
				// 需要结合centerId相同的所有记录的字段
				for (Object[] operationItemRequireInn : operationItemRequireList) {
					if (Integer.parseInt(operationItemRequireInn[3].toString())==centerId) {// 当前中心下的3种要求类型的集合
						switch (Integer.parseInt(operationItemRequireInn[5].toString())) {// 要求类型主键作为筛选条件
							case 485:// 必修
								item.setCount1(Integer.parseInt(operationItemRequireInn[6].toString()));
								item.setHour1(Integer.parseInt(operationItemRequireInn[7].toString()));
								break;
							case 486:// 选修
								item.setCount2(Integer.parseInt(operationItemRequireInn[6].toString()));
								item.setHour2(Integer.parseInt(operationItemRequireInn[7].toString()));
								break;
							default:// 其他
								item.setCount3(Integer.parseInt(operationItemRequireInn[6].toString()));
								item.setHour3(Integer.parseInt(operationItemRequireInn[7].toString()));
								break;
						}
					}
				}
				items.add(item);
			}
		}
		return items;
	}

	/**************************************
	 * Description 视图-实验要求统计表-count
	 * @author 陈乐为 2018-7-23
	 **************************************/
	public int operationItemRequireViewCount(HttpServletRequest request){
		String sql = "";
		// 判断是从哪个业务表中查询
		if(request.getParameterValues("roomId") != null){
			sql += "select count(*) from view_report_require_6 v where 1=1";
		}else{
			sql += "select count(*) from view_report_require_5 v where 1=1";
		}

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}

		Query query = entityManager.createNativeQuery(sql);
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/**
	 * Description 实验中心与实验室联动
	 * @param labCenter
	 * @param termId
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@Override
	public Map<String, String> LinkLabCenter(String labCenter, Integer termId) {
		//实验中心跟实验室联动
		Map<String,String> map=new HashMap<String, String>();
		//查询当前中心下的实验室
		String sql=" select l from OperationItem l where 1=1";
		sql += " and l.CDictionaryByLpStatusCheck.id=545";
		sql += " and l.labRoom.labCenter.id = '"+labCenter+"'";
		sql += " and l.schoolTerm.id = '"+termId+"'";
		// and (l.labRoom.isUsed = 1 or l.labRoom.isUsed is null) and l.labRoom.labRoomActive = 1
		Map<String,String> labRoomMap=new HashMap<String, String>();
		//遍历实验室放到map集合中
		for(OperationItem labRooms:operationItemDAO.executeQuery(sql, 0,-1)){
			if(labRooms.getLabRoom()!=null && labRooms.getLabRoom().getId()!=null){
				labRoomMap.put(String.valueOf(labRooms.getLabRoom().getId()),labRooms.getLabRoom().getLabRoomName());
			}
		}
		String labRoom="<option  value=0>全部实验室</option>";
		//获取map集合的迭代器
		Iterator<Map.Entry<String, String>> it = labRoomMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			labRoom+="<option  value='"+ entry.getKey() +"'>"+entry.getValue()+"</option>";
		}

		String labRoomValue= shareService.htmlEncode(labRoom);
		map.put("labroom",labRoomValue);
		return map;
	}

	/**
	 * Description 实验要求统计表导出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void exportOperationItemRequire(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map> lists = new ArrayList<Map>();
		String sql = "";
		if(request.getParameter("roomId") != null){
			sql += "select * from view_report_require_6 v where 1=1";
		}else{
			sql += "select * from view_report_require_5 v where 1=1";
		}

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}
		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		if(request.getParameter("roomId") != null) {
			sql += " order by v.termId, v.centerId, v.roomId";
		}else {
			sql+=" order by v.centerId";
		}
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		if(request.getParameter("roomId") != null){
			operationItemRequireChangeViews(list);
		}else{
			operationItemRequireChangeView(list);
		}

		//实验中心所在学院的实验室项目
		int i=1;
		if(request.getParameter("roomId") != null){
			for (OperationItemByRequire operationItemByRequire : operationItemRequireChangeViews(list))
			{
				Map map = new HashMap();

				map.put("serial number",i);//序号
				i++;
				map.put("2",operationItemByRequire.getLabRoomNumber());//单位编号
				map.put("3",operationItemByRequire.getLabRoomName());//单位名称
				map.put("4",operationItemByRequire.getCountTotalLab());//实开实验个数
				map.put("5",operationItemByRequire.getHourTotalLab());//实开实验时数
				map.put("6",operationItemByRequire.getCount1());//演示实验个数
				map.put("7",operationItemByRequire.getHour1());//演示实验时数
				map.put("8",(float)operationItemByRequire.getCount1()/operationItemByRequire.getCountTotal()*100);//演示实验个数比
				map.put("9",(float)operationItemByRequire.getHour1()/operationItemByRequire.getHourTotal()*100);//演示实验时数比
				map.put("10",operationItemByRequire.getCount2());//验证实验个数
				map.put("11",operationItemByRequire.getHour2());//验证实验时数
				map.put("12",(float)operationItemByRequire.getCount2()/operationItemByRequire.getCountTotal()*100);//验证实验个数比
				map.put("13",(float)operationItemByRequire.getHour2()/operationItemByRequire.getHourTotal()*100);//验证实验时数比
				map.put("14",operationItemByRequire.getCount3());//综合实验个数
				map.put("15",operationItemByRequire.getHour3());//综合实验时数
				map.put("16",(float)operationItemByRequire.getCount3()/operationItemByRequire.getCountTotal()*100);//综合实验个数比
				map.put("17",(float)operationItemByRequire.getHour3()/operationItemByRequire.getHourTotal()*100);//综合实验时数比
				lists.add(map);
			}  //实验室遍历
		}else{
			if(operationItemRequireChangeView(list).size() != 1){
				int count1College=0;
				int hour1College=0;
				int count2College=0;
				int hour2College=0;
				int count3College=0;
				int hour3College=0;
				for(OperationItemByRequire operationItemByRequire : operationItemRequireChangeView(list)){
					count1College += operationItemByRequire.getCount1();
					hour1College += operationItemByRequire.getHour1();
					count2College += operationItemByRequire.getCount2();
					hour2College += operationItemByRequire.getHour2();
					count3College += operationItemByRequire.getCount3();
					hour3College += operationItemByRequire.getHour3();
				}
				for(OperationItemByRequire operationItemByRequire : operationItemRequireChangeView(list)){
					Map map = new HashMap();
					map.put("serial number","0");
					map.put("2","clw201807");//单位编号
					map.put("3","clw庚商学院");//单位名称
					map.put("4",operationItemByRequire.getCountTotalCollege());//实开实验个数
					map.put("5",operationItemByRequire.getHourTotalCollege());//实开实验时数
					map.put("6",count1College);//必修实验个数
					map.put("7",hour1College);//必修实验时数
					map.put("8",(float)count1College/operationItemByRequire.getCountTotalCollege()*100);//必修实验个数比
					map.put("9",(float)hour1College/operationItemByRequire.getHourTotalCollege()*100);//必修实验时数比
					map.put("10",count2College);//选修实验个数
					map.put("11",hour2College);//选修实验时数
					map.put("12",(float)count2College/operationItemByRequire.getCountTotalCollege()*100);//选修实验个数比
					map.put("13",(float)hour2College/operationItemByRequire.getHourTotalCollege()*100);//选修实验时数比
					map.put("14",count3College);//其他实验个数
					map.put("15",hour3College);//其他实验时数
					map.put("16",(float)count3College/operationItemByRequire.getCountTotalCollege()*100);//其他实验个数比
					map.put("17",(float)hour3College/operationItemByRequire.getHourTotalCollege()*100);//其他实验时数比
					lists.add(map);
				}

			}
			for (OperationItemByRequire operationItemByRequire : operationItemRequireChangeView(list))
			{
				Map map = new HashMap();

				map.put("serial number",i);//序号
				i++;
				/*map.put("1",1);//学期*/
				map.put("2",operationItemByRequire.getCenterNumber());//单位编号
				map.put("3",operationItemByRequire.getCenterName());//单位名称
				map.put("4",operationItemByRequire.getCountTotal());//实开实验个数
				map.put("5",operationItemByRequire.getHourTotal());//实开实验时数
				map.put("6",operationItemByRequire.getCount1());//必修实验个数
				map.put("7",operationItemByRequire.getHour1());//必修实验时数
				map.put("8",(float)operationItemByRequire.getCount1()/operationItemByRequire.getCountTotal()*100);//必修实验个数比
				map.put("9",(float)operationItemByRequire.getHour1()/operationItemByRequire.getHourTotal()*100);//必修实验时数比
				map.put("10",operationItemByRequire.getCount2());//选修实验个数
				map.put("11",operationItemByRequire.getHour2());//选修实验时数
				map.put("12",(float)operationItemByRequire.getCount2()/operationItemByRequire.getCountTotal()*100);//选修实验个数比
				map.put("13",(float)operationItemByRequire.getHour2()/operationItemByRequire.getHourTotal()*100);//选修实验时数比
				map.put("14",operationItemByRequire.getCount3());//其他实验个数
				map.put("15",operationItemByRequire.getHour3());//其他实验时数
				map.put("16",(float)operationItemByRequire.getCount3()/operationItemByRequire.getCountTotal()*100);//其他实验个数比
				map.put("17",(float)operationItemByRequire.getHour3()/operationItemByRequire.getHourTotal()*100);//其他实验时数比
				lists.add(map);
			}  //实验室遍历
		}
		String title = "clw庚商学院实验要求统计表";
		if(request.getParameter("roomId") != null){
			String[] hearders = new String[] {"序号","单位编号","单位名称","实开实验个数","实开实验时数","必修实验个数","必修实验时数","必修实验个数比","必修实验时数比","选修实验个数","选修实验时数","选修实验个数比",
					"选修实验时数比","其他实验个数","其他实验时数","其他实验个数比","其他实验时数比"};//表头数组
			String[] fields = new String[] {"serial number","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"};
			TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "clw庚商学院实验要求统计表", td);
		}else{
			String[] hearders = new String[] {"序号","单位编号","单位名称","实开实验个数","实开实验时数","必修实验个数","必修实验时数","必修实验个数比","必修实验时数比","选修实验个数","选修实验时数","选修实验个数比",
					"选修实验时数比","其他实验个数","其他实验时数","其他实验个数比","其他实验时数比"};//表头数组
			String[] fields = new String[] {"serial number","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"};
			TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "clw庚商学院实验要求统计表", td);
		}
	}

	/**
	 * Description 实验变动统计表-roomId不为空
	 * @param request
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemChangeViews(HttpServletRequest request, int page ,int pageSize){
		String sql = "select * from view_report_change_6 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		sql+=" order by v.roomId";
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();

		return list;
	}

	/**
	 * Description 将查询的结果转化为页面方便显示的样式-roomId不为空
	 * @param operationItemChangeList
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@Override
	public List<OperationItemByChange> operationItemChangeViews(List<Object[]> operationItemChangeList){

		List<OperationItemByChange> items = new ArrayList<OperationItemByChange>();
		int roomId = 0;
		for (Object[] operationItemChange : operationItemChangeList) {
			if (Integer.parseInt(operationItemChange[5].toString()) != roomId) {// 该条件决定了每个实验室创建一条OperationItemByCategory记录
				// 将当前中心主键赋值
				roomId = Integer.parseInt(operationItemChange[5].toString());
				// 创建一个OperationItemByCategory对象
				OperationItemByChange item = new OperationItemByChange();
				// 记录通用的几个字段
				item.setCenterNumber(operationItemChange[13].toString());
				item.setCenterName(operationItemChange[2].toString());
				item.setLabRoomNumber(operationItemChange[12].toString());
				item.setLabRoomName(operationItemChange[4].toString());
				item.setCountTotal(Integer.parseInt(operationItemChange[14].toString()));
				item.setHourTotal(Integer.parseInt(operationItemChange[15].toString()));
				item.setCountTotalLab(Integer.parseInt(operationItemChange[10].toString()));
				item.setHourTotalLab(Integer.parseInt(operationItemChange[11].toString()));
				// 需要结合centerId相同的所有记录的字段
				for (Object[] operationItemChangeInn : operationItemChangeList) {
					if (Integer.parseInt(operationItemChangeInn[5].toString()) == roomId) {
						switch (Integer.parseInt(operationItemChangeInn[7].toString())) {
							case 540:// 新开
								item.setCount1(Integer.parseInt(operationItemChangeInn[8].toString()));
								item.setHour1(Integer.parseInt(operationItemChangeInn[9].toString()));
								item.setCenterCount1(Integer.parseInt(operationItemChangeInn[16].toString()));
								item.setCenterHour1(Integer.parseInt(operationItemChangeInn[17].toString()));
								break;
							case 539:// 改进
								item.setCount2(Integer.parseInt(operationItemChangeInn[8].toString()));
								item.setHour2(Integer.parseInt(operationItemChangeInn[9].toString()));
								item.setCenterCount2(Integer.parseInt(operationItemChangeInn[16].toString()));
								item.setCenterHour2(Integer.parseInt(operationItemChangeInn[17].toString()));
								break;
							case 538:// 未变动
								item.setCount3(Integer.parseInt(operationItemChangeInn[8].toString()));
								item.setHour3(Integer.parseInt(operationItemChangeInn[9].toString()));
								item.setCenterCount3(Integer.parseInt(operationItemChangeInn[16].toString()));
								item.setCenterHour3(Integer.parseInt(operationItemChangeInn[17].toString()));
								break;
							case 542:// 未开
								item.setCount4(Integer.parseInt(operationItemChangeInn[8].toString()));
								item.setHour4(Integer.parseInt(operationItemChangeInn[9].toString()));
								item.setCenterCount4(Integer.parseInt(operationItemChangeInn[16].toString()));
								item.setCenterHour4(Integer.parseInt(operationItemChangeInn[17].toString()));
								break;
							default:// 撤消
								item.setCount5(Integer.parseInt(operationItemChangeInn[8].toString()));
								item.setHour5(Integer.parseInt(operationItemChangeInn[9].toString()));
								item.setCenterCount5(Integer.parseInt(operationItemChangeInn[16].toString()));
								item.setCenterHour5(Integer.parseInt(operationItemChangeInn[17].toString()));
								break;

						}
					}
				}
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Description 实验变动统计表-roomId为空
	 * @param request
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List operationItemChangeView(HttpServletRequest request, int page ,int pageSize){
		String sql = "select * from view_report_change_5 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		sql+=" order by v.centerId";
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		this.operationItemChangeView(list);
		return list;
	}

	/**
	 * Description 将结果转化为页面方便显示的样式-roomId为空
	 * @param operationItemChangeList
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public List<OperationItemByChange> operationItemChangeView(List<Object[]> operationItemChangeList){

		List<OperationItemByChange> items = new ArrayList<OperationItemByChange>();
		int centerId = 0;
		for (Object[] operationItemChange : operationItemChangeList) {
			if (Integer.parseInt(operationItemChange[3].toString())!=centerId) {// 该条件决定了每个中心创建一条OperationItemByChange记录
				// 将当前中心主键赋值
				centerId = Integer.parseInt(operationItemChange[3].toString());
				// 创建一个OperationItemByCategory对象
				OperationItemByChange item = new OperationItemByChange();
				// 记录通用的几个字段
				item.setCenterNumber(operationItemChange[14].toString());
				item.setCenterName(operationItemChange[2].toString());
				item.setCountTotal(Integer.parseInt(operationItemChange[8].toString()));//各中心总个数
				item.setHourTotal(Integer.parseInt(operationItemChange[9].toString()));//各中心总时数
				item.setCountTotalCollege(Integer.parseInt(operationItemChange[10].toString()));
				item.setHourTotalCollege(Integer.parseInt(operationItemChange[11].toString()));
				// 需要结合centerId相同的所有记录的字段
				for (Object[] operationItemChangeInn : operationItemChangeList) {
					if (Integer.parseInt(operationItemChangeInn[3].toString())==centerId) {// 当前中心下的5种变动类型的集合
						switch (Integer.parseInt(operationItemChangeInn[5].toString())) {// 要求类型主键作为筛选条件
							case 540:// 新开
								item.setCount1(Integer.parseInt(operationItemChangeInn[6].toString()));
								item.setHour1(Integer.parseInt(operationItemChangeInn[7].toString()));
								break;
							case 539:// 改进
								item.setCount2(Integer.parseInt(operationItemChangeInn[6].toString()));
								item.setHour2(Integer.parseInt(operationItemChangeInn[7].toString()));
								break;
							case 538:// 未变动
								item.setCount3(Integer.parseInt(operationItemChangeInn[6].toString()));
								item.setHour3(Integer.parseInt(operationItemChangeInn[7].toString()));
								break;
							case 542:// 未开
								item.setCount4(Integer.parseInt(operationItemChangeInn[6].toString()));
								item.setHour4(Integer.parseInt(operationItemChangeInn[7].toString()));
								break;
							default:// 撤消
								item.setCount5(Integer.parseInt(operationItemChangeInn[6].toString()));
								item.setHour5(Integer.parseInt(operationItemChangeInn[7].toString()));
								break;
						}
					}
				}
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Description 实验变动统计表-count
	 * @param request
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public int operationItemChangeViewCount(HttpServletRequest request){
		String sql = "";
		// 判断是从哪个业务表中查询
		if(request.getParameterValues("roomId") != null){
			sql += "select count(*) from view_report_change_6 v where 1=1";
		}else{
			sql += "select count(*) from view_report_change_5 v where 1=1";
		}

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}

		Query query = entityManager.createNativeQuery(sql);
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/**
	 * Description 实验变动统计表导出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author 陈乐为 2018-7-23
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void exportOperationItemChange(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map> lists = new ArrayList<Map>();
		String sql = "";
		if(request.getParameter("roomId") != null){
			sql += "select * from view_report_change_6 v where 1=1";
		}else{
			sql += "select * from view_report_change_5 v where 1=1";
		}

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		// 中心查询
		if (request.getParameter("centerId") != null && Integer.valueOf(request.getParameter("centerId"))!=0) {
			sql+=" and v.centerId='"+request.getParameter("centerId")+"' ";
		}

		// 实验室查询
		if(request.getParameterValues("roomId") != null && Integer.valueOf(request.getParameter("roomId"))!=0){
			String[] labRoomIdArray = request.getParameterValues("roomId");
			sql+=" and (1=0";
			for(String labRoomIdString : labRoomIdArray){
				sql+=" or v.roomId = '"+labRoomIdString+"'";
			}
			sql+=" or 1=0)";
		}
		if(request.getParameter("roomId") != null){
			sql += " order by v.roomId";
		}
		// 开始查询
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		if(request.getParameter("roomId") != null){
			operationItemChangeViews(list);
		}else{
			operationItemChangeView(list);
		}

		//实验中心所在学院的实验室项目
		int i=1;
		if(request.getParameter("roomId") != null){
			for (OperationItemByChange operationItemByChange : operationItemChangeViews(list))
			{
				Map map = new HashMap();

				map.put("serial number",i);//序号
				i++;
				map.put("2",operationItemByChange.getLabRoomNumber());//单位编号
				map.put("3",operationItemByChange.getLabRoomName());//单位名称
				map.put("4",operationItemByChange.getCountTotalLab());//总实验个数
				map.put("5",operationItemByChange.getHourTotalLab());//总实验时数
				map.put("6",operationItemByChange.getCount1());//新开实验个数
				map.put("7",operationItemByChange.getHour1());//新开实验时数
				map.put("8",(float)operationItemByChange.getCount1()/operationItemByChange.getCountTotal()*100);//新开实验个数比
				map.put("9",(float)operationItemByChange.getHour1()/operationItemByChange.getHourTotal()*100);//新开实验时数比
				map.put("10",operationItemByChange.getCount2());//改进实验个数
				map.put("11",operationItemByChange.getHour2());//改进实验时数
				map.put("12",(float)operationItemByChange.getCount2()/operationItemByChange.getCountTotal()*100);//改进实验个数比
				map.put("13",(float)operationItemByChange.getHour2()/operationItemByChange.getHourTotal()*100);//改进实验时数比
				map.put("14",operationItemByChange.getCount3());//未变动实验个数
				map.put("15",operationItemByChange.getHour3());//未变动实验时数
				map.put("16",(float)operationItemByChange.getCount3()/operationItemByChange.getCountTotal()*100);//未变动实验个数比
				map.put("17",(float)operationItemByChange.getHour3()/operationItemByChange.getHourTotal()*100);//未变动实验时数比
				map.put("18",operationItemByChange.getCount4());//未开实验个数
				map.put("19",operationItemByChange.getHour4());//未开实验时数
				map.put("20",(float)operationItemByChange.getCount4()/operationItemByChange.getCountTotal()*100);//未开实验个数比
				map.put("21",(float)operationItemByChange.getHour4()/operationItemByChange.getHourTotal()*100);//未开实验时数比
				map.put("22",operationItemByChange.getCount5());//撤消实验个数
				map.put("23",operationItemByChange.getHour5());//撤消实验时数
				map.put("24",(float)operationItemByChange.getCount5()/operationItemByChange.getCountTotal()*100);//撤消实验个数比
				map.put("25",(float)operationItemByChange.getHour5()/operationItemByChange.getHourTotal()*100);//撤消实验时数比
				lists.add(map);
			}  //实验室遍历
		}else{
			for (OperationItemByChange operationItemByChange : operationItemChangeView(list))
			{
				Map map = new HashMap();

				map.put("serial number",i);//序号
				i++;
				/*map.put("1",1);//学期*/
				map.put("2",operationItemByChange.getCenterNumber());//单位编号
				map.put("3",operationItemByChange.getCenterName());//单位名称
				map.put("4",operationItemByChange.getCountTotal());//总实验个数
				map.put("5",operationItemByChange.getHourTotal());//总实验时数
				map.put("6",operationItemByChange.getCount1());//新开实验个数
				map.put("7",operationItemByChange.getHour1());//新开实验时数
				map.put("8",(float)operationItemByChange.getCount1()/operationItemByChange.getCountTotal()*100);//新开实验个数比
				map.put("9",(float)operationItemByChange.getHour1()/operationItemByChange.getHourTotal()*100);//新开实验时数比
				map.put("10",operationItemByChange.getCount2());//改进实验个数
				map.put("11",operationItemByChange.getHour2());//改进实验时数
				map.put("12",(float)operationItemByChange.getCount2()/operationItemByChange.getCountTotal()*100);//改进实验个数比
				map.put("13",(float)operationItemByChange.getHour2()/operationItemByChange.getHourTotal()*100);//改进实验时数比
				map.put("14",operationItemByChange.getCount3());//未变动实验个数
				map.put("15",operationItemByChange.getHour3());//未变动实验时数
				map.put("16",(float)operationItemByChange.getCount3()/operationItemByChange.getCountTotal()*100);//未变动实验个数比
				map.put("17",(float)operationItemByChange.getHour3()/operationItemByChange.getHourTotal()*100);//未变动实验时数比
				map.put("18",operationItemByChange.getCount4());//未开实验个数
				map.put("19",operationItemByChange.getHour4());//未开实验时数
				map.put("20",(float)operationItemByChange.getCount4()/operationItemByChange.getCountTotal()*100);//未开实验个数比
				map.put("21",(float)operationItemByChange.getHour4()/operationItemByChange.getHourTotal()*100);//未开实验时数比
				map.put("22",operationItemByChange.getCount5());//撤消实验个数
				map.put("23",operationItemByChange.getHour5());//撤消实验时数
				map.put("24",(float)operationItemByChange.getCount5()/operationItemByChange.getCountTotal()*100);//撤消实验个数比
				map.put("25",(float)operationItemByChange.getHour5()/operationItemByChange.getHourTotal()*100);//撤消实验时数比
				lists.add(map);
			}  //实验室遍历
		}
		String title = "clw庚商学院实验变动统计表";
		if(request.getParameter("roomId") != null){
			String[] hearders = new String[] {"序号","单位编号","单位名称","总实验个数","总实验时数","新开实验个数","新开实验时数","新开实验个数比","新开实验时数比","改进实验个数","改进实验时数","改进实验个数比",
					"改进实验时数比","未变动实验个数","未变动实验时数","未变动实验个数比","未变动实验时数比","未开实验个数","未开实验时数","未开实验个数比","未开实验时数比","撤消实验个数","撤消实验时数","撤消实验个数比","撤消实验时数比"};//表头数组
			String[] fields = new String[] {"serial number","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
			TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "clw庚商学院实验变动统计表", td);
		}else{
			String[] hearders = new String[] {"序号","单位编号","单位名称","总实验个数","总实验时数","新开实验个数","新开实验时数","新开实验个数比","新开实验时数比","改进实验个数","改进实验时数","改进实验个数比",
					"改进实验时数比","未变动实验个数","未变动实验时数","未变动实验个数比","未变动实验时数比","未开实验个数","未开实验时数","未开实验个数比","未开实验时数比","撤消实验个数","撤消实验时数","撤消实验个数比","撤消实验时数比"};//表头数组
			String[] fields = new String[] {"serial number","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
			TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "clw庚商学院实验变动统计表", td);
		}
	}

	/****************************************************
	 * @description：系统报表-实验学时数汇总表
	 * @author： 郑昕茹
	 * @date：2016-10-20
	 *****************************************************/
	@SuppressWarnings("rawtypes")
	public List getOperationItemStudentAndHours(HttpServletRequest request, Integer flag){
		String sql = "select center_name, sum(hourEvery), sum(studentNumberHour),sum(itemNumber) from view_report_itemshours_1 v where 1=1";
		// 当前学期
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		String[] terms = request.getParameterValues("term");
		int count = 0;
		if(flag != -1){// 有查询
			if(terms != null && terms.length != 0){
				for(String t:terms){
					if(count == 0)
					{
						sql += " and (v.term ="+t;
						count++;
					}
					else{
						sql += " or v.term="+t;
					}
				}
				sql += ")";
			}
		}else {// 有查询
			sql += " and v.term="+termId;
		}
		sql +=" group by v.centerId";
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/*************************************************************************************
	 * @description：实验学时数汇总表导出Excel
	 * @author：郑昕茹
	 * @date：2016-10-20
	 *************************************************************************************/
	@Override
	public void exportOperationItemStudentAndHours(Integer flag, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		List<Object[]> studentAndHours = this.getOperationItemStudentAndHours(request,flag);
		//实验中心所在学院的实验室项目
		int i=1;
		int totalStudent = 0;
		int totalHour = 0;
		int totalItem = 0;
		for (Object[] o : studentAndHours)
		{
			Map map = new HashMap();
			totalStudent += Integer.parseInt(o[1].toString());
			totalHour += Integer.parseInt(o[2].toString());
			totalItem += Integer.parseInt(o[3].toString());
			map.put("serial number",i);//序号
			map.put("centerName", o[0]);
			map.put("hourEvery", o[1]);
			map.put("studentNumberHour", o[2]);
			map.put("itemNumber", o[3]);
			i++;

			list.add(map);
		}  //实验室遍历
		Map map = new HashMap();

		map.put("serial number","合计");//序号
		map.put("centerName", "上海建桥学院");
		map.put("hourEvery", totalStudent);
		map.put("studentNumberHour", totalHour);
		map.put("itemNumber", totalItem);

		list.add(map);
		String title = "实验学时数汇总表";
		String[] hearders = new String[] {"序号","中心","人次数","人时数","项目数"};//表头数组
		String[] fields = new String[] {"serial number","centerName", "hourEvery", "studentNumberHour", "itemNumber"};
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		//System.out.println(schoolTerms.getTermName());
		String info = "";
		if(request.getParameterValues("term") != null && Integer.valueOf(request.getParameter("term"))!=0){
			info +=" 学期：";
			String[] termArray = request.getParameterValues("term");
			for(String termString : termArray){
				SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermByPrimaryKey(Integer.parseInt(termString));
				info +=" "+schoolTerm.getTermName();
			}
		}
		report.exportExcel(title,  shareService.getUserDetail().getCname(),info,td);
	}

	/****************************************************
	 * 功能：系统报表-实验室利用率
	 * 作者： 贺子龙
	 * 日期：2016-10-14
	 *****************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getLabUseRate(HttpServletRequest request){
		String sql = "select * from view_report_labrate_8 v where 1=1";
		// 当前学期
		int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		// 选择
		if (!EmptyUtil.isStringEmpty(request.getParameter("term"))) {
			termId = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = "+termId;

		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/****************************************************
	 * 功能：系统报表-实验室利用率--实验室
	 * 作者： 贺子龙
	 * 日期：2016-10-14
	 *****************************************************/
	@SuppressWarnings("rawtypes")
	public List getLabUseRateRoom(Integer centerId, Integer termId){
		// 建立查询
		String sql = "select * from view_report_labrate_9 v where 1=1";
		// 学期条件
		sql+=" and v.termId = "+termId;
		// 中心条件
		sql+=" and v.centerId = "+centerId;
		// 执行查询
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/**************************************************
	 * @description：實驗室使用率報表導出
	 * @author：陳樂為
	 * @date：2016-10-24
	 **************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportReportLabRate(HttpServletRequest request, HttpServletResponse response, List<Object[]> labRates,int flag) throws Exception
	{
		List<Map> list = new ArrayList<Map>();
		//实验中心所在学院的实验室项目
		for (Object[] labRate : labRates)
		{
			Map map = new HashMap();
			if(flag == 1) {
				map.put("center",labRate[1]);//实验部门
				map.put("centerRealHour",labRate[3]);//负责人
				map.put("centerPlanHour", labRate[4]);//
				map.put("centerRate",labRate[5]);//
			}else if (flag == 2) {
				map.put("center",labRate[2]);//实验部门
				map.put("centerRealHour",labRate[4]);//负责人
				map.put("centerPlanHour", labRate[5]);//
				map.put("centerRate",labRate[6]);//
			}
			list.add(map);
		}  //实验室遍历
		if(flag == 1) {
			String title = "实验室使用率报表-中心";
			String[] hearders = new String[] {"中心名称","本中心实验室学时数（实际）","本中心实验室学时数（额定）","实验室利用率（%）"};//表头数组
			String[] fields = new String[] {"center","centerRealHour", "centerPlanHour", "centerRate"};
			TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "", td);
		}else if (flag == 2) {
			String title = "实验室使用率报表-实验室";
			String[] hearders = new String[] {"实验室名称","实验室学时数（实际）","实验室学时数（额定）","实验室利用率（%）"};//表头数组
			String[] fields = new String[] {"center","centerRealHour", "centerPlanHour", "centerRate"};
			TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportExcel(title,  shareService.getUserDetail().getCname(), "", td);
		}
	}

	/**************************************
	 * Description 视图-实验室任务及耗材统计表-list
	 * @author 陈乐为
	 * @date 2016.05.04
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List viewTaskAndConsume(HttpServletRequest request, int page ,int pageSize){
		String sql = "select * from view_report_taskandconsume_8 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/**************************************
	 * Description 视图-实验室任务及耗材统计表-count
	 * @author 陈乐为
	 * @date 2016.05.04
	 **************************************/
	public int viewTaskAndConsumeCount(HttpServletRequest request){
		String sql = "select count(*) from view_report_taskandconsume_8 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/*************************************************************************************
	 * Description 实验室任务及耗材统计表导出Excel
	 * @author 陈乐为
	 * @date 2016.05.04
	 *************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void exportTaskAndConsume(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Map> lists = new ArrayList<Map>();
		String sql = "select * from view_report_taskandconsume_8 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";

		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();

		int i=1;
		for (Object[] objects : list)
		{
			Map map = new HashMap();

			map.put("1", i);//序号
			i++;
			map.put("2", objects[0]);//单位编号
			map.put("3", objects[1]);//单位名称
			map.put("4", objects[2]);//实验室数
			map.put("5", objects[3]);//课程总数
			map.put("6", objects[4]);//计划学时
			map.put("7", objects[5]);//计划实验数
			map.put("8", objects[6]);//计划实验学时
			map.put("9", objects[7]);//实开实验数
			map.put("10", objects[8]);//实开实验学时
			map.put("11", objects[9]);//实验开出率
			map.put("12", objects[10]);//一次性耗材耗价
			map.put("13", objects[11]);//非一次性耗材耗价
			map.put("14", objects[12]);//耗价总计
			lists.add(map);
		}  //实验室遍历

		String title = "clw庚商学院实验室任务及耗材统计表";
		String[] hearders = new String[] {"序号","单位编号","单位名称","实验室数","课程总数","计划学时","计划实验数","计划实验学时","实开实验数","实开实验学时","实验开出率","一次性耗材耗价","非一次性耗材耗价","耗价总计"};//表头数组"序号",,"开课学院","实际实验学时数","实验项目名称"
		String[] fields = new String[] {"1","2", "3", "4", "5","6", "7", "8", "9","10", "11", "12","13", "14"};
		TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title,  shareService.getUserDetail().getCname(), "clw庚商学院实验室任务及耗材统计表", td);
	}

	/************************************************************
	 * Description 获取所有课程--课程类型试用
	 * @author 陈乐为
	 * @date 2016.05.16
	 ************************************************************/
	@Override
	public Map<String, String> findSchoolCourseMapByQuery(Integer termId, int cid){

		LabCenter center = labCenterDAO.findLabCenterByPrimaryKey(cid);

		String sql = "select c from SchoolCourseInfo c, SchoolCourseDetail s where 1=1";
		sql+=" and c.courseNumber=s.courseNumber";
		sql+=" and s.schoolTerm.id = "+termId;
		if(center != null && center.getSchoolAcademy() != null) {
			sql+=" and s.schoolAcademy.academyNumber like '"+center.getSchoolAcademy().getAcademyNumber()+"'";
		}
		List<SchoolCourseInfo> courseList = schoolCourseInfoDAO.executeQuery(sql, -1, 0);
		Map<String, String> schoolCourseMap = new HashMap<String, String>();
		for (SchoolCourseInfo course : courseList) {
			if(course != null && course.getCourseNumber() != null){
				schoolCourseMap.put(course.getCourseNumber(),course.getCourseName()+"["+course.getCourseNumber()+"]");
			}
		}
		return schoolCourseMap;
	}

	/************************************************************
	 * Description 获取所有课程类型
	 * @author 陈乐为
	 * @date 2016.04.21
	 ************************************************************/
	@Override
	public Map<String, String> findAllSchoolCourseTypeMap(){
		String sql = "select v from SchoolCourseInfo v where 1=1";
		List<SchoolCourseInfo> courseTypeList = schoolCourseInfoDAO.executeQuery(sql, -1, 0);
		Map<String, String> schoolCourseTypeMap = new HashMap<String, String>();
		for (SchoolCourseInfo schoolCourseDetail : courseTypeList) {
			schoolCourseTypeMap.put(schoolCourseDetail.getCourseType(),schoolCourseDetail.getCourseTypeName());
		}
		return schoolCourseTypeMap;
	}

	/**************************************
	 * Description 视图-实践教学课程细化表-list
	 * @author 陈乐为
	 * @date 2016.04.14
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List viewSchoolCourseDetail(HttpServletRequest request, int page ,int pageSize, int labCenterId){
		String sql = "select * from view_report_sitecourse_3 v where 1=1";// and (courseType=1 or courseType=2 or courseType=3)

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";
		// 中心查询
		int centerId = labCenterId;
		if(request.getParameter("centerId") != null){
			centerId = Integer.parseInt(request.getParameter("centerId"));
		}
		if(centerId != 0 && centerId != -1) {
			LabCenter center = labCenterDAO.findLabCenterByPrimaryKey(centerId);
			String academyNumber = center.getSchoolAcademy().getAcademyNumber();
			sql += " and v.academyNumber like '"+academyNumber+"'";
		}
		//课程查询
		if("".equals(request.getParameter("courseNo"))){
			;
		}else if(request.getParameter("courseNo") != null){
			String[] courseNoArray = request.getParameterValues("courseNo");
			for(String courseNoString : courseNoArray){
				sql+=" and v.courseNum = '"+courseNoString+"'";
			}
		}
		// 课程类型查询
		if(request.getParameter("courseTypeId") != null && Integer.parseInt(request.getParameter("courseTypeId")) != 0){
			String[] courseTypeIdArray = request.getParameterValues("courseTypeId");
			for(String courseTypeIdString : courseTypeIdArray){
				sql += " and v.courseType = '"+courseTypeIdString+"'";
			}
		}
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		query.setMaxResults(pageSize);
		query.setFirstResult((page-1)*pageSize);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/**************************************
	 * Description 视图-实践教学课程细化表-count
	 * @author 陈乐为
	 * @date 2016.04.14
	 **************************************/
	public int viewSchoolCourseDetailCount(HttpServletRequest request, int labCenterId){
		String sql = "select count(*) from view_report_sitecourse_3 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";
		// 中心查询
		int centerId = labCenterId;
		if(request.getParameter("centerId") != null){
			centerId = Integer.parseInt(request.getParameter("centerId"));
		}
		if(centerId != 0 && centerId != -1) {
			LabCenter center = labCenterDAO.findLabCenterByPrimaryKey(centerId);
			String academyNumber = center.getSchoolAcademy().getAcademyNumber();
			sql += " and v.academyNumber like '"+academyNumber+"'";
		}
		//课程查询
		if("".equals(request.getParameter("courseNo"))){
			;
		}else if(request.getParameter("courseNo") != null){
			String[] courseNoArray = request.getParameterValues("courseNo");
			for(String courseNoString : courseNoArray){
				sql+=" and v.courseNum = '"+courseNoString+"'";
			}
		}

		// 课程类型查询
		int courseTypeId = 0;
		if(request.getParameter("courseTypeId") != null && Integer.parseInt(request.getParameter("courseTypeId")) != 0){
			courseTypeId = Integer.parseInt(request.getParameter("courseTypeId"));
			sql += " and v.courseType = '"+courseTypeId+"'";
		}

		Query query = entityManager.createNativeQuery(sql);
		// 获取对象条数
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/**************************************************************************************
	 * Description 学期与课程联动
	 * @author 陈乐为
	 * @date 2016.04.19
	 **************************************************************************************/
	@Override
	public Map<String, String> LinkSchoolTerm(String schoolTerm) {

		Map<String,String> map=new HashMap<String, String>();
		//查询当前学期下的课程
		String sql=" select l from OperationItem l, SchoolCourseInfo c where l.schoolCourseInfo.courseNumber=c.courseNumber";
		sql += " and l.CDictionaryByLpStatusCheck.id=545";
		sql+=" and l.schoolTerm.id = '"+schoolTerm+"' ";
		Map<String,String> operationItemMap=new HashMap<String, String>();
		//遍历课程放到map集合中
		for(OperationItem operationItem:operationItemDAO.executeQuery(sql, 0,-1)){
			operationItemMap.put(operationItem.getSchoolCourseInfo().getCourseNumber(),operationItem.getSchoolCourseInfo().getCourseName());
		}
		String schoolCourse="<option value=>全部课程</option>";
		//获取map集合的迭代器
		Iterator<Map.Entry<String, String>> it = operationItemMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			schoolCourse+="<option  value='"+ entry.getKey() +"'>"+entry.getValue()+"</option>";
		}

		String operationItemValue= shareService.htmlEncode(schoolCourse);
		map.put("schoolCourse",operationItemValue);
		return map;
	}

	/**************************************************************************************
	 * Description 学期与中心联动
	 * @author 陈乐为
	 * @date 2016.05.18
	 **************************************************************************************/
	@Override
	public Map<String, String> LinkSchoolTerms(String schoolTerm) {

		Map<String,String> map=new HashMap<String, String>();
		//查询当前学期下的课程
		String sql=" select l from OperationItem l, LabRoom c where l.labRoom.id = c.id";
		sql += " and l.CDictionaryByLpStatusCheck.id=545";
		sql+=" and l.schoolTerm.id = '"+schoolTerm+"' ";
		Map<String,String> labCenterMap=new HashMap<String, String>();
		//遍历课程放到map集合中
		for(OperationItem operationItem:operationItemDAO.executeQuery(sql, 0,-1)){
			labCenterMap.put(String.valueOf(operationItem.getLabRoom().getLabCenter().getId()),operationItem.getLabRoom().getLabCenter().getCenterName());
		}
		String labCenters="<option value=>全部中心</option>";
		//获取map集合的迭代器
		Iterator<Map.Entry<String, String>> it = labCenterMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			labCenters+="<option  value='"+ entry.getKey() +"'>"+entry.getValue()+"</option>";
		}

		String labCenterValue= shareService.htmlEncode(labCenters);
		map.put("labCenter",labCenterValue);
		return map;
	}

	/*************************************************************************************
	 * Description 课程类型统计表导出Excel
	 * @author 陈乐为
	 * @date 2016.04.14
	 *************************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void exportSchoolCourseDetailType(HttpServletRequest request, HttpServletResponse response, String acno) throws Exception{
		List<Map> lists = new ArrayList<Map>();
		String sql = "select * from view_report_sitecourse_3 v where 1=1";

		// 学期查询
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(request.getParameter("term") != null){
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and v.termId = '"+term+"'";
		// 中心查询
		if(request.getParameter("centerId") != null){
//			centerId = Integer.parseInt(request.getParameter("centerId"));
		}
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		String academyNumber = "";
		String academyName ="";
		if(academy!=null && academy.getAcademyNumber()!=null) {
			academyNumber = academy.getAcademyNumber();
			academyName = academy.getAcademyName();
			sql += " and v.academyNumber like '"+academyNumber+"'";
		}
		//课程查询
		if("".equals(request.getParameter("courseNo"))){
			;
		}else if(request.getParameter("courseNo") != null){
			String[] courseNoArray = request.getParameterValues("courseNo");
			for(String courseNoString : courseNoArray){
				sql+=" and v.courseNum = '"+courseNoString+"'";
			}
		}

		// 课程类型查询
		int courseTypeId = 0;
		if(request.getParameter("courseTypeId") != null && Integer.parseInt(request.getParameter("courseTypeId")) != 0){
			courseTypeId = Integer.parseInt(request.getParameter("courseTypeId"));
			sql += " and v.courseType = '"+courseTypeId+"'";
		}

		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();

		int i=1;
		for (Object[] objects : list)
		{
			Map map = new HashMap();

			map.put("1", i);//序号
			i++;
			map.put("2", objects[0]);//课程名称
			map.put("3", objects[1]);//课程编号
			map.put("12", objects[15]);//开课教师
			map.put("4", objects[2]);//学期
			map.put("5", objects[8]);//总学时
			map.put("6", objects[7]);//理论学时
			map.put("7", objects[14]);//实验学时
			map.put("8", objects[5]);//课程类型
			map.put("9", objects[13]);//开课学院
			map.put("10", objects[9]);//实际实验学时数
			String itemName = "";
			if (!EmptyUtil.isObjectEmpty(objects[10])&&!EmptyUtil.isStringEmpty(objects[10].toString())) {
				itemName = objects[10].toString();
				itemName = itemName.replace("<br>", "\r\n");
			}
			map.put("11", itemName);//实验项目名称
			lists.add(map);
		}  //实验室遍历

		// 导出excel中需要显示开课学院和学期信息
		String info = "开课学院："+academyName;

		String title = "实践教学课程细化实验实训项目任务表";
		String[] hearders = new String[] {"序号","课程名称","课程编号","开课教师","学期","总学时","理论学时","实验学时","课程类型","开课学院","实际实验学时数","实验项目名称"};//表头数组"序号",,"开课学院","实际实验学时数","实验项目名称"
		String[] fields = new String[] {"1","2", "3","12", "4", "5","6", "7", "8", "9","10", "11"};
		TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title,  shareService.getUserDetail().getCname(), info, td);
	}

	/**
	 * 获取所有实验室教学情况汇总表数据-totalRecords
	 *
	 * @param request
	 * @return
	 * @author 罗璇
	 * @date 2018年4月24日
	 */
	@Override
	public Integer countOperationItemReport(HttpServletRequest request) {
		String sql = "select count(*) from view_report_labhourssdu_1 voi where 1=1 ";
		//学期条件
		String termId = request.getParameter("termId");
		if (!EmptyUtil.isStringEmpty(termId)) {
			sql += "and voi.term_id = " + termId;
		}
		//所属单位条件
		String academyId = request.getParameter("academyId");
		if (!EmptyUtil.isStringEmpty(academyId)) {
			sql += " and voi.academy_number = '" + academyId + "' ";
		}
		//所属实验室条件
		String labRoomId = request.getParameter("labRoomId");
		if (!EmptyUtil.isStringEmpty(labRoomId)) {
			sql += " and voi.lab_room_id = " + labRoomId;
		}

		Query query = entityManager.createNativeQuery(sql);
		Integer count = Integer.valueOf(query.getSingleResult().toString());

		return count;
	}

	/**
	 * 获取所有实验室教学情况汇总表数据-分页
	 *
	 * @param request
	 * @param pageModel
	 * @return
	 * @author 罗璇
	 * @date 2018年4月24日
	 */
	@Override
	public List<Object[]> getOperationItemReport(HttpServletRequest request, Map<String, Integer> pageModel) {
		String sql = "select * from view_report_labhourssdu_1 voi where 1=1 ";
		//学期条件
		String termId = request.getParameter("termId");
		if (!EmptyUtil.isStringEmpty(termId)) {
			sql += " and voi.term_id = " + termId;
		}
		//所属单位条件
		String academyId = request.getParameter("academyId");
		if (!EmptyUtil.isStringEmpty(academyId)) {
			sql += " and voi.academy_number = '" + academyId + "' ";
		}
		//所属实验室条件
		String labRoomId = request.getParameter("labRoomId");
		if (!EmptyUtil.isStringEmpty(labRoomId)) {
			sql += " and voi.lab_room_id = " + labRoomId;
		}
		//按课程编号正序
		sql += " order by voi.course_number";
		Query query = entityManager.createNativeQuery(sql);

		//设置分页
		query.setFirstResult((pageModel.get("currpage")-1) * pageModel.get("pageSize"));
		query.setMaxResults(pageModel.get("pageSize"));

		List<Object[]> list = query.getResultList();
		return list;
	}

	/**
	 * 获取实验室教学情况汇总表数据(课程数，课程类型，试验项目)
	 *
	 * @param request
	 * @return
	 * @author 罗璇
	 * @date 2018年4月25日
	 */
	@Override
	public Object[] countOperationItemReportRecords(HttpServletRequest request) {
		String sql = "SELECT " +
				" count(distinct(voi.course_number)) AS count, " +
				" count(case when voi.teach_way_id = 0 then 0 end) AS teach_way_zero, "+
				" count(case when voi.teach_way_id = 1 then 1 end) AS teach_way_one, "+
				" count(voi.operation_item_id) AS count_operation_item, " +
				" sum(voi.department_hours) AS department_hours, " +
				" sum(voi.student_number) as count_student " +
				" FROM " +
				" view_report_labhourssdu_1 AS voi WHERE 1=1 ";
		//学期条件
		String termId = request.getParameter("termId");
		if (!EmptyUtil.isStringEmpty(termId)) {
			sql += " and voi.term_id = " + termId;
		}
		//所属单位条件
		String academyId = request.getParameter("academyId");
		if (!EmptyUtil.isStringEmpty(academyId)) {
			sql += " and voi.academy_number = '" + academyId + "' ";
		}
		//所属实验室条件
		String labRoomId = request.getParameter("labRoomId");
		if (!EmptyUtil.isStringEmpty(labRoomId)) {
			sql += " and voi.lab_room_id = " + labRoomId;
		}
		Query query = entityManager.createNativeQuery(sql);

		return (Object[])query.getResultList().get(0);
	}

	/**
	 * 导出实验室教学情况汇总表
	 * @author 罗璇
	 * @date 2018年4月24日
	 * @param request
	 * @param response
	 * @param labRates
	 * @throws Exception
	 */
	@Override
	public void exportOperationItemReport(HttpServletRequest request, HttpServletResponse response, List<Object[]> labRates) throws Exception {
		List<Map> list = new ArrayList<Map>();
		for (Object[] obj : labRates) {
			Map<String,Object> map = new HashMap<String, Object>();
			//课程编号
			map.put("cell1",obj[0]);
			//课程名称
			map.put("cell2",obj[1]);
			//授课方式
			map.put("cell3",obj[2]);
			//实验编号
			map.put("cell4",obj[3]);
			//实验名称
			map.put("cell5",obj[4]);
			//实验类别
			map.put("cell6",obj[5]);
			//实验类型
			map.put("cell7",obj[6]);
			//实验者人数
			map.put("cell8",obj[7]);
			//实验学时数
			map.put("cell9",obj[8]);
			//实验室名称
			map.put("cell10",obj[9]);
			//单位名称
			map.put("cell11",obj[10]);

			list.add(map);
		}
		//标题
		String title = "实验室教学情况汇总表";
		//表头
		String[] hearders = new String[] {"课程编号","课程名称","授课方式","实验编号","实验名称",
				"实验类别","实验类型","实验者人数","实验学时数","实验室名称",
				"单位名称"};
		//表头对应的key值
		String[] fields = new String[] {"cell1","cell2","cell3","cell4","cell5","cell6","cell7","cell8",
				"cell9","cell10","cell11"};
		//表数据
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportExcel(title, shareService.getUserDetail().getCname(), "", td);

	}

	/**
	 * 获取所有实验室教学情况汇总表数据
	 *
	 * @param request
	 * @return
	 * @author 罗璇
	 * @date 2018年4月23日
	 */
	@Override
	public List<Object[]> getOperationItemReport(HttpServletRequest request) {
		String sql = "select * from view_report_labhourssdu_1 voi where 1=1 ";
		//按课程编号正序
		sql += "order by voi.course_number";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();

		return list;
	}

	/**
	 * @Description 根据查询条件获取督导教务实验课表数据
	 * @param request
	 * @author 张德冰
	 * @date 2018-9-19
	 */
	@Override
	public List<Object[]> getReportSupervise(HttpServletRequest request, Integer currpage, Integer pageSize, String acno){
        //获取查询条件
		String term = request.getParameter("term");
		String courseName = request.getParameter("courseName");
		String cName = request.getParameter("cName");

		//获取查询语句的hql 根据视图view_school_course_supervise获取数据集合
		StringBuffer queryHQL = new StringBuffer(
				"SELECT * from view_school_course_supervise  WHERE academy_number = '" + acno + "'");
		//判断是否为教务处
		String role = request.getSession().getAttribute("selected_role").toString();
		if(role.equals("ROLE_DEAN") || EmptyUtil.isStringEmpty(acno) || "-1".equals(acno)){
			//如果是教务处则输出所有专业的数据
			queryHQL = new StringBuffer(
					"SELECT * from view_school_course_supervise  WHERE 1=1");
		}

		StringBuffer extra = new StringBuffer();
		// 学期查询条件
		if (!EmptyUtil.isStringEmpty(term)) {
			extra.append(" AND term_id = " + term);
		}else {
			//当前学期
			int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
			extra.append(" AND term_id = " +termId);
		}
		// 课程名称查询条件
		if (!EmptyUtil.isStringEmpty(courseName)) {
			extra.append(" AND course_name like '%" + courseName + "%'");
		}
		// 教师姓名查询条件
		if (!EmptyUtil.isStringEmpty(request.getParameter("cName"))) {
			extra.append(" AND cname like '%" + cName + "%'");
		}
		// 获取数据列表
		Query queryList = entityManager.createNativeQuery(queryHQL.toString() + extra.toString());
//		// 设置分页
//		if(currpage > 0) {
//			queryList.setFirstResult((currpage - 1) * pageSize);
//			queryList.setMaxResults(pageSize);
//		}
		//获取分页后数据
		List<Object[]> originList = queryList.getResultList();
		//进行转换
		List<Object[]> list = getOutputList(originList);
		return list;
	}

	/**
	 * @Description 根据查询条件获取督导教务实验课表数据数量
	 * @param request
	 * @author 张德冰
	 * @date 2018-9-19
	 */
	@Override
	public Integer getCountReportSupervise(HttpServletRequest request, String acno){
		//获取查询条件
		String term = request.getParameter("term");
		String courseName = request.getParameter("courseName");
		String cName = request.getParameter("cName");

		//获取查询语句的hql 根据视图view_school_course_supervise获取数据集合
		StringBuffer countHQL = new StringBuffer(
//				"SELECT count(*) from view_school_course_supervise  WHERE academy_number = '" + acno + "'");
				"SELECT * from view_school_course_supervise  WHERE academy_number = '" + acno + "'");
		//判断是否为教务处
		String role = request.getSession().getAttribute("selected_role").toString();
		if(role.equals("ROLE_DEAN") || EmptyUtil.isStringEmpty(acno) || "-1".equals(acno)){
			//如果是教务处则输出所有专业的数据
			countHQL = new StringBuffer(
//					"SELECT count(*) from view_school_course_supervise  WHERE 1=1");
					"SELECT * from view_school_course_supervise  WHERE 1=1");
		}

		StringBuffer extra = new StringBuffer();
		// 学期查询条件
		if (!EmptyUtil.isStringEmpty(term)) {
			extra.append(" AND term_id = " + term);
		}else {
			//当前学期
			int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
			extra.append(" AND term_id = " +termId);
		}
		// 课程名称查询条件
		if (!EmptyUtil.isStringEmpty(courseName)) {
			extra.append(" AND course_name like '%" + courseName + "%'");
		}
		// 教师姓名查询条件
		if (!EmptyUtil.isStringEmpty(request.getParameter("cName"))) {
			extra.append(" AND cname like '%" + cName + "%'");
		}
		// 获取总记录数
		Query queryCount = entityManager.createNativeQuery(countHQL.toString() + extra.toString());
//		int totalRecords = ((BigInteger) queryCount.getSingleResult()).intValue();
		//获取所有数据
		List<Object[]> originList = queryCount.getResultList();
		//进行转换
		List<Object[]> list = getOutputList(originList);
		//获取总数
		int totalRecords = list.size();
		return totalRecords;
	}


	/**
	 * Description 解析周次字符串获得周次整数列表
	 * @param origin 周次字符串
	 * @return 周次整数列表
	 * @author 黄保钱 2018-10-12
	 */
	private List<Integer> getN(String origin) {
		List<Integer> nature = new ArrayList<>();
		if (origin.contains("第") && origin.contains("周")) {
			String s = origin.substring(origin.lastIndexOf("第") + 1, origin.lastIndexOf("周"));
			// 连续周的情况
			if (s.contains("-")) {
				String[] t = s.split("-");
				Integer t1 = Integer.parseInt(t[0]);
				int t2 = Integer.parseInt(t[1]);
				for (int i = t1; i <= t2; i++) {
					nature.add(i);
				}
			}
			// 非连续周
			else {
				Integer t1 = Integer.parseInt(s);
				nature.add(t1);
			}
		}
		return nature;
	}

	/**
	 * Description 将整数列表格式化成所需周次字符串
	 * @param t 列表
	 * @return 周次字符串
	 * @author 黄保钱 2018-10-12
	 */
	private String getString(Set<Integer> t) {
		List<Integer> middle = new ArrayList<>();

		// 当前连续的方式
		int flag = -1;

		Integer temp = null;
		String result = "{";
		Iterator<Integer> tit = t.iterator();
		while (tit.hasNext()) {
			Integer i = tit.next();
			// 第一次无序直接计入第一组
			if (temp == null) {
				temp = i;
				middle.add(i);
				// 如果只有一个直接计入一组
				if(!tit.hasNext()){
					result = switchNature(middle, flag, result);
				}
				continue;
			}
			// 遇到以下条件分组
			// 1.这周是单双周和连续周的转折点
			// 2.这周是其他序的下一周
			if (
					(Math.abs(i - temp) != flag
							&& (flag == 1 || flag == 2)
							&& (Math.abs(i - temp) == 1 || Math.abs(i - temp) == 2)
							&& middle.size() > 1
					)
							|| (Math.abs(i - temp) != 1 && Math.abs(i - temp) != 2)) {
				result = switchNature(middle, flag, result);
			}
			flag = Math.abs(i - temp);
			middle.add(i);
			if(!tit.hasNext()){
				result = switchNature(middle, flag, result);
			}
			temp = i;
		}
		result = result.substring(0, result.length() - 1);
		return result += "}";
	}

	/**
	 * Description 获得指定字符串
	 * @param middle 一种类型周的列表
	 * @param flag 周的类型（1 连续周；2 单双周）
	 * @param result 要输出的字符串
	 * @return 输出字符串
	 * @author 黄保钱 2018-10-12
	 */
	private String switchNature(List<Integer> middle, int flag, String result) {
		// 其他周或者只有一周的情况
		if ( middle.size() == 1) {
			result += "第";
			result += middle.get(0);
			result += "周";
			result += ",";
		}
		// 连续周
		else if (flag == 1) {
			result += "第";
			result += middle.get(0);
			result += "-";
			result += middle.get(middle.size() - 1);
			result += "周";
			result += ",";
		}
		// 单双周
		else if (flag == 2) {
			result += "第";
			result += middle.get(0);
			result += "-";
			result += middle.get(middle.size() - 1);
			result += middle.get(0) % 2 == 1 ? "单周" : "双周";
			result += ",";
		}
		middle.clear();
		return result;
	}

	/**
	 * Description 将原输出列表进行转换
	 * @param list 原输出列表
	 * @return 新输出列表
	 * @author 黄保钱 2018-10-16
	 */
	private List<Object[]> getOutputList(List<Object[]> list){
		// 新的输出列表
		List<Object[]> outputList = new ArrayList<>();
		Object[] origin = new Object[20];
		String temp = null;
		// 将输出进行变换
		for(Object[] o: list){
			// 进行周次输出格式的转换
			String test = (String) o[12];
			// 计算选课的总周次
			int timeSize = 0;
			if(test != null && !test.equals("")){
				String[] testSplit = test.split(";");
				// 建立星期和节次与周次的映射
				Map<String, Set<Integer>> natures = new LinkedHashMap<>();
				// 将原来的字符串转换成整数数列
				for (String t : testSplit) {
					String tKey = t.substring(0, t.indexOf('{'));
					// 将相同星期和节次的周次合并
					Set<Integer> newSet = natures.get(tKey);
					if (newSet == null) {
						newSet = new SortedArraySet<>();
					}
					newSet.addAll(getN(t));
					timeSize += getN(t).size();
					natures.put(tKey, newSet);
				}
				// 将整数数列转换成格式化后的字符串
				o[12] = "";
				for (String front : natures.keySet()) {
					o[12] += front + getString(natures.get(front)) + "；";
				}
				o[12] = ((String ) o[12]).substring(0, ((String ) o[12]).length() - 1);
			}

			// 合并相同课程编号的项
			if(o[16] == null) o[16] = "0";
			if(o[15] == null) o[15] = "0";
			// 总课时 = 课程学分 * 总周次
			double a17 = Double.parseDouble((String) o[16]) *  Double.parseDouble((String) o[15]);
			// 实验室课时 = 课程学分 * 实验上课周次
			double a19 =  Double.parseDouble((String) o[16]) * timeSize;
			// 理论课时 = 总课时 - 实验室课时
			double a18 = a17 - a19;
			// 第一次进入或者课程编号不相同
			if(!(temp != null && temp.equals(o[14]))){
				// 第一次进入不需要添加结果
				if(temp != null)
					outputList.add(origin);
				// 建立新的项
				origin = new Object[20];
				for(int i = 0; i < 17; i++){
					if(o[i] == null) o[i] = "无";
					origin[i] = o[i];
				}
				origin[17] = Double.toString(a17);
				origin[19] = Double.toString(a19);
				origin[18] = Double.toString(a18);
			}
			// 课程编号相同
			else{
				// 将相同课程编号的项拼接
				for(int i = 10; i < 14; i++){
					if(o[i] == null) o[i] = "无";
					origin[i] += "/" + o[i];
				}
				origin[19] += "/" + Double.toString(a19);
				origin[18] += "/" + Double.toString(a18);
			}
			temp = (String) o[14];
		}
		if(list != null && list.size() != 0)
			outputList.add(origin);
		return outputList;
	}

	/**
	 * Description 月报报表
	 * @param queryParamsVO
	 * @return
	 * @author 陈乐为 2019年4月16日
	 */
	public List<Object[]> getMonthReport(QueryParamsVO queryParamsVO) {

		Query query = entityManager.createNativeQuery("call proc_report_month("+queryParamsVO.getTerm_id()+",'"+queryParamsVO.getQuery_params()+"','" +
				queryParamsVO.getStart_date()+"','" + queryParamsVO.getEnd_date()+"',"+queryParamsVO.getCurr_page()+","+queryParamsVO.getPage_size()+")");
		List<Object[]> list = query.getResultList();
		return list;
	}

	/**
	 * Description 实验教学计划表-根据查询条件获取
	 * @return
	 * @author 刘博越 2019年5月10日
	 */
	public List getViewCourseDetail(SchoolCourseDetail schoolCourseDetail, int page, int pageSize){
		String sql = "select * from view_timetable_course_detail v where 1=1";
		if(schoolCourseDetail != null && schoolCourseDetail.getSchoolTerm() != null) {
			sql += " and v.termId = " + schoolCourseDetail.getSchoolTerm().getId();
		}else {
			// 当前学期
			SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
			sql += " and v.termId = " + schoolTerm.getId();
		}
		if(schoolCourseDetail != null && schoolCourseDetail.getSchoolCourse() != null &&
				!schoolCourseDetail.getSchoolCourse().getCourseNo().equals("")) {
			//school_course_detail--course_no--school_course
			sql += " and v.courseNo = '"+ schoolCourseDetail.getSchoolCourse().getCourseNo() +"'";
		}
		sql += " order by v.courseNumber";
		Query query= entityManager.createNativeQuery(sql);
		// 以下两行是分页设置
		if(pageSize > 0) {
			query.setMaxResults(pageSize);
			query.setFirstResult((page-1)*pageSize);
		}
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/****************************************************************************
	 * Description 实验教学计划表-根据课程编号查询对应信息
	 *
	 * @author 刘博越
	 * @date 2019-5-10
	 ****************************************************************************/
	public List getViewTimetableCourseDetail(String courseDetailNo) {
		String sql = "select * from view_timetable_course_detail v where 1=1";
		sql=sql+" and v.detail_id = '"+courseDetailNo+"'";
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/****************************************************************************
	 * Description：实验教学计划表
	 *
	 * @author 刘博越 2019年5月10日
	 ****************************************************************************/
	public List getListTimetableFull(String course_number){
		String sql = "select * from view_timetable_full v where 1=1";
		//根据选课组编号
		sql += " and v.detail_id = '"+ course_number +"'";
//		sql += " and v.course_number = '"+ course_number +"'";
		Query query= entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list= query.getResultList();
		return list;
	}

	/****************************************************************************
	 * Description 报表上报-查询获取学年
	 *
	 * @author 刘博越
	 * @date 2019-5-10
	 ****************************************************************************/
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
}
