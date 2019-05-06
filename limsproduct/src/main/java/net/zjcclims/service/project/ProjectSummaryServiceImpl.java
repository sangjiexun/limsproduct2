package net.zjcclims.service.project;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.lab.LabRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
@Service("ProjectSummaryServce")
@Transactional
public class ProjectSummaryServiceImpl implements ProjectSummaryService{
	
	@Autowired private OperationItemDAO operationItemDAO;
	@Autowired private ShareService shareService;
	@Autowired private LabRoomService labRoomService;
	@Autowired private SchoolTermDAO schoolTermDAO;
	@Autowired private LabRoomDAO labRoomDAO;
	@Autowired private SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired private SchoolAcademyDAO schoolAcademyDAO;
	//返回记录数
	public int Count(Integer sid){
		int RecordCount=this.getList(sid).size();		
		return RecordCount;
	}

	/*********************************************************************
	 * @内容：获取所有的项目信息列表
	 * @作者：陈乐为
	 * @日期：2016-01-05
	 *********************************************************************/
	public List<OperationItem> getList(Integer sid) {
		//StringBuffer sql=new StringBuffer("select c from OperationItem c where 1=1");
		List<OperationItem> list=getList(0,-1,sid);
		return list;
	}
	
	/*********************************************************************
	 * @功能：查看所有的项目信息列表
	 * @作者：陈乐为
	 * @日期：2016-01-05
	 *********************************************************************/
	@Override
	public List<OperationItem> findAllOperationItem(HttpServletRequest request, Integer currpage, Integer pageSize, OperationItem operationItem, Integer sid) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select i from OperationItem i where 1=1");
		/*hql.append(" and i.labCenter.id ="+sid);*/
		hql.append(" and i.CDictionaryByLpStatusCheck.id =545");
		
		if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
		{
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			//hql.append(" and i.schoolTerm.id="+termId);
		}
		if(operationItem.getLabRoom()!=null && !"".equals(operationItem.getLabRoom().getLabRoomName()))
		{
			hql.append(" and i.labRoom.labRoomName like '%"+operationItem.getLabRoom().getLabRoomName()+"%'");
		}
		if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}
		// 新建时间
		if(request.getParameter("starttime")!=null && !request.getParameter("starttime").equals("")) {
			hql.append(" and i.createdAt >= '"+ request.getParameter("starttime") +"'");
		}
		if(request.getParameter("endtime")!=null && !request.getParameter("endtime").equals("")) {
			hql.append(" and i.createdAt <= '"+ request.getParameter("endtime") +"'");
		}
		
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	//返回指定的表
	public OperationItem getOperationItemByPrimaryKey(Integer id){
		return operationItemDAO.findOperationItemByPrimaryKey(id);                                
	}
	
	//返回指定分页的记录列表
	public List<OperationItem> getList(int startResult, int maxRows,Integer sid){
		String hql="select a from OperationItem a where 1=1";                                                                   //my,新增
		hql += " and a.labCenter.id ="+sid;
		List<OperationItem> list=operationItemDAO.executeQuery(hql,startResult,maxRows);           //my（-1，-1）
		return list;
	}

	/*********************************************************************
	 * Description 根据条件查询结果(中心&实验室名称&学期&课程名称+课程代码)
	 * @author 陈乐为
	 * @date 2016-10-10
	 *********************************************************************/
	@Override
	public List<OperationItem> findOperationItems(HttpServletRequest request,Integer currpage, Integer pageSize, String acno) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select i from OperationItem i where i.CDictionaryByLpStatusCheck.id=545 and length(i.lpCodeCustom) > 0");// 审核通过
		// 实验中心查询
		if (!EmptyUtil.isStringEmpty(request.getParameter("labCenter")) && !request.getParameter("labCenter").equals("0")) {
			hql.append(" and i.labRoom.labCenter.id="+Integer.valueOf(request.getParameter("labCenter")));
		} else if (!EmptyUtil.isStringEmpty(request.getParameter("labCenter")) && request.getParameter("labCenter").equals("0")) {
			hql.append("");
			//cid = -1;
		}else {
			hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'"); // 当前登录中心
		}
		// 学期查询
		if(request.getParameterValues("schoolTerm") != null && Integer.valueOf(request.getParameter("schoolTerm"))!=0){
			String[] labRoomIdArray = request.getParameterValues("schoolTerm");
			hql.append(" and (1=0");
			for(String labRoomIdString : labRoomIdArray){
				hql.append(" or i.schoolTerm.id = '"+labRoomIdString+"'");
			}
			hql.append(" )");
		}else {
			hql.append(" and i.schoolTerm.id="+termId); // 当前学期
		}
		// 实验室查询
		if(request.getParameter("labRoom") != null && !request.getParameter("labRoom").equals("")) {
			hql.append(" and i.labRoom.id ="+ request.getParameter("labRoom"));
		}
		// 课程查询
		if(request.getParameter("courseNumber") != null && !request.getParameter("courseNumber").equals("")) {
			hql.append(" and i.schoolCourseInfo.courseNumber ='"+request.getParameter("courseNumber")+"'");
		}
		// System.out.println("查询列表："+hql);
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/*******************************************************************
	 * Description：根据页面所选学期id查询
	 * @param：termIds 学期id数组
	 * @author：陈乐为
	 * @2016-10-10
	 *******************************************************************/
	public Map<Integer, String> getTermSelectedMap(String[] termIds) {
		String termIdStr = "";
		for(String s : termIds){
			termIdStr +=  s + "," ;
		}
		termIdStr = termIdStr.substring(0, termIdStr.length()-1);
		String sql = "select c from SchoolTerm c where c.id in ("+termIdStr+")";
		sql += " group by c.id";
		List<SchoolTerm> schoolTerms = schoolTermDAO.executeQuery(sql);
		Map<Integer, String> schoolTermMap = new HashMap<Integer, String>();
		for (SchoolTerm schoolTerm : schoolTerms) {
			if(schoolTerm.getId()!=null){
				schoolTermMap.put(schoolTerm.getId(),schoolTerm.getTermName());
			}
		}
		return schoolTermMap;
	}

	/*************************************************************************************
	 * Description 获取所有实验室
	 * @author 陈乐为
	 * @date 2016.03.28
	 *************************************************************************************/
	@Override
	public List<LabRoom> findAllLabRoom(String acno) {
		StringBuffer hql = new StringBuffer("select distinct l from LabRoom l , TimetableLabRelated t where 1=1" +
				" and l.labCenter.schoolAcademy.academyNumber = '"+ acno +"' and l.id = t.labRoom.id ");
		return labRoomDAO.executeQuery(hql.toString(), 0, -1);
	}

	/***************************************************************************************
	 * Description 查询所有的课程名称
	 * @author 陈乐为
	 * @date 2016.03.30
	 ***************************************************************************************/
	public Map<String, String> findAllcourseName(List<OperationItem> l){
		Map<String,String>map=new HashMap<String, String>();
		for(OperationItem operationItems:l){
			if(operationItems.getSchoolCourseInfo()!=null&&operationItems.getSchoolCourseInfo().getCourseName()!=null&&!operationItems.getSchoolCourseInfo().getCourseName().equals("")){
				map.put( operationItems.getSchoolCourseInfo().getCourseNumber(), operationItems.getSchoolCourseInfo().getCourseName());
			}
		}
		return map;
	}

	/*************************************************************************************
	 * Description 获取所有课程
	 * @author 陈乐为
	 * @date 2016.03.30
	 *************************************************************************************/
	@Override
	public List<SchoolCourseInfo> findSchoolCourseInfo(HttpServletRequest request, String acno) {
		StringBuffer hql = new StringBuffer(" select s from SchoolCourseInfo s, OperationItem t " +
				"where t.CDictionaryByLpStatusCheck.id=545 " +
				"and s.courseNumber=t.schoolCourseInfo.courseNumber ");
		if(acno!=null && !acno.equals("-1")) {
			hql.append(" and t.labRoom.labCenter.id like '"+ acno +"'");
		}
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();

		if(request.getParameterValues("schoolTerm") != null && Integer.valueOf(request.getParameter("schoolTerm"))!=0){
			String[] labRoomIdArray = request.getParameterValues("schoolTerm");
			hql.append(" and (1=0");
			for(String labRoomIdString : labRoomIdArray){
				hql.append(" or t.schoolTerm.id = '"+labRoomIdString+"'");
			}
			hql.append(" )");
		}else {
			hql.append(" and t.schoolTerm.id="+termId); // 当前学期
		}

		hql.append(" group by s.courseNumber");

		return schoolCourseInfoDAO.executeQuery(hql.toString(), 0, -1);
	}

	/*************************************************************************************
	 * Description 获取所有项目
	 * @author 陈乐为
	 * @date 2016.03.30
	 *************************************************************************************/
	@Override
	public List<OperationItem> findAllOperationItem(String acno) {
		StringBuffer hql = new StringBuffer("select t from OperationItem t where 1=1");
		if(acno!=null && !acno.equals("-1")) {
			hql.append(" and t.labCenter.schoolAcademy.academyNumber = '"+ acno +"'");// 学院
		}
		hql.append(" and t.CDictionaryByLpStatusCheck.id = 545");// 审核通过
		return operationItemDAO.executeQuery(hql.toString(), 0, -1);
	}
	/*************************************************************************************
	 * @內容：实验项目汇总导出Excel(查询结果)
	 * @作者：陈乐为
	 * @日期：2016.03.26
	 *************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void exportOperationItem(HttpServletRequest request, HttpServletResponse response, @RequestParam String acno) throws Exception
	{
		List<Map> list = new ArrayList<Map>();

		List<OperationItem> operationItems = findOperationItems(request, 1, -1, acno); //
		//List<OperationItem> operationItems = getOperationItem(roomid, courseNum, labCenterid, schoolItemId); //
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
			if(reportOperationItem.getSchoolCourseInfo()!=null && reportOperationItem.getSchoolCourseInfo().getCourseNumber()!=null){
				map.put("course number",reportOperationItem.getSchoolCourseInfo().getCourseNumber());//课程编号
			}
			if(reportOperationItem.getSchoolCourseInfo()!=null && reportOperationItem.getSchoolCourseInfo().getCourseName()!=null){
				map.put("course name",reportOperationItem.getSchoolCourseInfo().getCourseName());//课程名称
			}
			if(reportOperationItem.getLpMajorFit()!=null){
				map.put("major",reportOperationItem.getLpMajorFit());//面向专业
			}

			list.add(map);
		}
      /*LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
      String labRoomString = labRoom.getLabRoomName();
      LabCenter labCenter = labCenterDAO.findLabCenterByPrimaryKey(labCenterId);
      String labCenterString = labCenter.getCenterName();*/
		String title = "实践教学课时细化实验实训项目汇总表";
		String[] hearders = new String[] {"序号","课程编号","课程名称","实验名称","实验编号","实验学时数","面向专业","实验要求","实验类型","实验类别","实验所属学科","实验者类别","实验者人数","每组人数","实验室名称","实验室编号"};//表头数组
		String[] fields = new String[] {"serial number","course number","course name","project name","project no","times","major","test request","test type","category","subject","user type","studentNum","studentGroup","lab name",
				"class code"};
		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		// 导出excel中需要显示开课学院和学期信息
		String info = "";
		if (!EmptyUtil.isStringEmpty(request.getParameter("labCenter"))) {
			int centerId = Integer.parseInt(request.getParameter("labCenter"));
			if (centerId!=0) {
				info +=" 单位：";
				SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(acno);
				if(schoolAcademy != null && schoolAcademy.getAcademyName() != null && !"".equals(schoolAcademy.getAcademyName())) {
					info += schoolAcademy.getAcademyName();
				}
			}
		}
		if(request.getParameterValues("schoolTerm") != null && Integer.valueOf(request.getParameter("schoolTerm"))!=0){
			info +=" 学期：";
			String[] termArray = request.getParameterValues("schoolTerm");
			for(String termString : termArray){
				SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermByPrimaryKey(Integer.parseInt(termString));
				if(schoolTerm != null && schoolTerm.getTermName() != null && !"".equals(schoolTerm.getTermName())) {
					info += " " + schoolTerm.getTermName();
				}
			}
		}


		report.exportExcel(title,  shareService.getUserDetail().getCname(), info, td);
	}
}