package net.zjcclims.service.project;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.zjcclims.constant.MonthReport;
import net.zjcclims.domain.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectSummaryService{
	/*********************************************************************
	 * @内容：查看所有的项目信息列表
	 * @作者：陈乐为
	 * @日期：2016-01-05
	 *********************************************************************/
	public List<OperationItem> getList(Integer sid);
	
	//返回记录数
	public int Count(Integer sid);
	
	//返回指定的表
	public OperationItem getOperationItemByPrimaryKey(Integer id);
	
	//返回指定分页的记录列表
	public List<OperationItem> getList(int startResult, int maxRows, Integer sid);
	
	//
	public List<OperationItem> findAllOperationItem(HttpServletRequest request, Integer currpage, 
			Integer pageSize, OperationItem operationItem,Integer sid);

	/*********************************************************************
	 * Description 根据条件查询结果(中心&实验室名称&学期&课程名称+课程代码)
	 * @author 陈乐为
	 * @date 2016-10-10
	 *********************************************************************/
	public List<OperationItem> findOperationItems(HttpServletRequest request,Integer currpage, Integer pageSize, String acno);

	/*******************************************************************
	 * Description：根据页面所选学期id查询
	 * @param：termIds 学期id数组
	 * @author：陈乐为
	 * @2016-10-10
	 *******************************************************************/
	public Map<Integer, String> getTermSelectedMap(String[] termIds);

	/*************************************************************************************
	 * Description 获取所有实验室
	 * @author 陈乐为
	 * @date 2016.03.28
	 *************************************************************************************/
	public List<LabRoom> findAllLabRoom(String acno);

	/***************************************************************************************
	 * Description 查询所有的课程名称
	 * @author 陈乐为
	 * @date 2016.03.30
	 ***************************************************************************************/
	public Map<String, String> findAllcourseName(List<OperationItem> l);

	/*************************************************************************************
	 * Description 获取所有课程
	 * @author 陈乐为
	 * @date 2016.03.30
	 *************************************************************************************/
	public List<SchoolCourseInfo> findSchoolCourseInfo(HttpServletRequest request, String acno);

	/*************************************************************************************
	 * Description 获取所有项目
	 * @author 陈乐为
	 * @date 2016.03.30
	 *************************************************************************************/
	public List<OperationItem> findAllOperationItem(String acno);
	/*************************************************************************************
	 * @內容：实验项目汇总导出Excel
	 * @作者：陈乐为
	 * @日期：2018.10.22
	 *************************************************************************************/
	public void exportOperationItem(HttpServletRequest request, HttpServletResponse response, @RequestParam String acno) throws Exception;

}