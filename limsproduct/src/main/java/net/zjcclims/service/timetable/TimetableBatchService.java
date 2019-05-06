package net.zjcclims.service.timetable;

import java.util.List;

import net.zjcclims.domain.SchoolCourseStudent;
import net.zjcclims.domain.TimetableBatch;
import net.zjcclims.domain.TimetableGroup;

public interface TimetableBatchService {

	/*************************************************************************************
	 * @內容：查看所有的时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableBatch> getTimetableBatchByQuery(int termId, int status,int curr, int size);

	/*************************************************************************************
	 * @內容：查看计数的所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountTimetableBatchByQuery(int termId,int status,String acno);

	/***********************************************************************************************
	 * Description：排课模块通用{通过选课组编号查询分组}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-27
	 ***********************************************************************************************/
	public List<TimetableGroup> findTimetableGroupByCourseCode(String courseCode);

	/***********************************************************************************************
	 * Description：排课模块通用{教务排课：选课组编号查询选课组下的学生}
	 *
	 * @author：戴昊宇
	 * @Date：2017-08-26
	 ***********************************************************************************************/
	public List<SchoolCourseStudent> findSchoolCourseStudentByCourseCode(String courseCode);

	/***********************************************************************************************
	* Description：排课模块通用{保存分批信息}
	*
	* @author：贺子龙
	* @Date：2016-08-27
	***********************************************************************************************/
	public Integer saveTimetableBatch(TimetableBatch timetableBatch, int isSelf);

	public List<TimetableBatch> getTimetableBatchByCourseCode(String courseNo);

	/***********************************************************************************************
	 * Description：排课--分批排课{根据主键查找分组}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-27
	 ***********************************************************************************************/
	public List<TimetableGroup> findTimetableGroupsByBatchId(int batchId);
}