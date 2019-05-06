package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.dto.timetable.TimetableSelfCourseDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface TimetableSelfCourseService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public BaseDTO apiSelfCourseListByPage(int termId, String search, String status, int curpage, String sort, String sortOrder, HttpServletRequest request);

	/**************************************************************************
	 * Description 自主排课-查看排课管理列表-获取数据
	 *
	 * @author 魏诚
	 * @date 2018年10月17日
	 **************************************************************************/
	public BaseDTO apiSelfCourseManageByPage(int termId, String search, String status, int curpage, String sort, String sortOrder, HttpServletRequest request);

	/*************************************************************************************
	 * Description:自主排课-保存二次排课
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	@Transactional
	public boolean apiSaveSelfReTimetable(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description: 自主排课-获取查找自建课程信息记录数
	 *
	 * @作者： 魏诚
	 * @日期：2018-10-26
	 *************************************************************************************/
	public int getTimetableSelfCourseTotalRecords();

	/*************************************************************************************
	 * Description:自主排课-保存教学班信息
	 *
	 * @author： 魏誠
	 * @date：2018-10-27
	 *************************************************************************************/
	public boolean apiSaveTimetableSelfCourse(TimetableSelfCourseDTO timetableSelfCourseDTO);

	/*************************************************************************************
	 * Description:自主课程-获取学生选课列表
	 *
	 * @author： 魏誠
	 * @date：2018-10-29
	 *************************************************************************************/
	public BaseDTO apiTimetableCourseStudentList(int termId, int selfId, String sort, String order);

	/*************************************************************************************
	 * Description:自主排课-删除教学班信息
	 *
	 * @author： 魏誠
	 * @date：2018-10-27
	 *************************************************************************************/
	public boolean apiDeleteTimetableSelfCourse(Integer selfId);

	/**************************************************************************
	 * Description 审核排课-获取审核课程对象信息-自主
	 * @param termId
	 * @param search
	 * @return
	 * @author 陈乐为 2019-1-17
	 **************************************************************************/
	public BaseDTO apiEduSelfCourseForAudit(int termId, String search);

}