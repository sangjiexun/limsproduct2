package net.zjcclims.service.report;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;



public interface TeachingReportService {


	/************************************************************
	 * @內容：获取周次，用于下拉框
	 * @作者：何立友
	 * @日期：2014-09-11
	 ************************************************************/
	public Map<Integer, String> getWeekMap(Integer termId);
	
	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有时间列表安排
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	public List<TimetableLabRelated> getLabTimetableAppointments(int termId, String acno, int week);
	
	/*************************************************************************************
	 * @內容：据实验室和节次及星期列出所有时间列表安排
	 * @作者： 何立友
	 * @日期：2015-05-12
	 *************************************************************************************/
	public List<TimetableLabRelated> getSelfLabTimetableAppointments(int termId, String acno, int week);
	
	/*************************************************************************************
	 * @內容：获取指定学期、登录者所在学院、有课程的教师(ajax)
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	public String getTeachersByTerm(int termId, String acno);
	
	/*************************************************************************************
	 * @內容：获取指定学期、指定教师选课组(ajax)
	 * @作者： 何立友
	 * @日期：2014-09-17
	 *************************************************************************************/
	public String getCourseDetailsByTermTeacher(int termId, String username);
	
	/*************************************************************************************
	 * @內容：根据courseNo(主键)获取课程
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	public SchoolCourse getCourseByCourseNo(String courseNo);
	
	/*************************************************************************************
	 * @內容：根据courseDetailNo(主键)获取选课组
	 * @作者： 何立友
	 * @日期：2014-09-17
	 *************************************************************************************/
	public SchoolCourseDetail getCourseDetailByCourseDetailNo(String courseDetailNo);
	
	/*************************************************************************************
	 * @內容：根据courseCode获取自主排课选课组
	 * @作者： 何立友
	 * @日期：2015-05-13
	 *************************************************************************************/
	public TimetableSelfCourse getTimetableSelfCourseByCourseCode(String courseCode);
	
	/*************************************************************************************
	 * @內容：获取指定学期、登录者所在学院、有课程的教师(Map)
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	public Map<String, String> getTeachersMapByTerm(int termId);
	
	/*************************************************************************************
	 * @內容：获取指定学期、指定教师课程(Map)
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	public Map<String, String> getCourseDetailsMapByTermTeacher(int termId, String username);
	
	/*************************************************************************************
	 * @內容：根据课程获取排课信息
	 * @作者： 何立友
	 * @日期：2014-09-12
	 *************************************************************************************/
	public List<TimetableAppointment> getTimetableAppointmentByCourseDetail(String courseDetailNo); 
	
	/*************************************************************************************
	 * @內容：根据学期获取已发布的排课信息
	 * @作者： 何立友
	 * @日期：2014-09-13
	 *************************************************************************************/
	public List<SchoolCourseDetail> getSchoolCourseDetailByTerm(int termId, int labCenterId);
	
	/**
	 * @內容：根据学期获取已发布的自主排课信息
	 * @作者： 何立友
	 * @日期：2015-05-13
	 */
	public List<TimetableSelfCourse> getTimetableSelfCourseByTerm(int termId, int labCenterId);
	
	/*************************************************************************************
	 * @內容：上机登记报表导出Excel
	 * @作者： 何立友
	 * @日期：2014-09-14
	 *************************************************************************************/
	public void exportExperimentRegister(SchoolCourseDetail schoolCourseDetail, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/*************************************************************************************
	 * @內容：实验准备报表导出Excel
	 * @作者： 何立友
	 * @日期：2014-09-15
	 *************************************************************************************/
	public void exportExperimentPrepare(SchoolTerm schoolTerm, int labCenterId, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/*************************************************************************************
	 * @內容：实验准备报表导出Excel
	 * @作者： 何立友
	 * @日期：2014-10-30
	 *************************************************************************************/
	public void exportTermRegister(SchoolTerm schoolTerm, String acno, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/*************************************************************************************
	 * @內容：获取登录者所在学院的实验室
	 * @作者： 何立友
	 * @日期：2014-10-30
	 *************************************************************************************/
	public List<LabRoom> getLabRooms();
	
	/*************************************************************************************
	 * @內容：根据参数条件获取排课信息
	 * @作者： 何立友
	 * @日期：2014-10-30
	 *************************************************************************************/
	public List<TimetableLabRelated> getLabTimetableAppointmenmt(int termId, int startClass, int endClass, int labId, int weekday, String academyNumber);
	/*************************************************************************************
	 * @throws Exception 
	 * @內容：导出周次登记
	 * @作者： 徐龙帅
	 * @日期：2015-01-14
	 *************************************************************************************/
	public void exportweekRegister(SchoolTerm schoolTerm, String acno, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/*************************************************************************************
	 * @內容：查找指定中心的实验室分室
	 * @作者： 何立友
	 * @日期：2015-06-17
	 *************************************************************************************/
	public List<LabRoom> getLabRoomsByLabCenter(String acno);
    /**
     * Description 月报报表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2019年4月24日
     */
	public void exportMonthRegister(HttpServletRequest request, HttpServletResponse response) throws Exception;
	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有时间列表安排
	 * @作者： 何立友
	 * @日期：2014-09-11
	 *************************************************************************************/
	public List<TimetableLabRelated> getLabTimetableAppointments(HttpServletRequest request, int termId,
			int roomId, int week,String teacher, String acno);
	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有时间列表安排
	 * @作者： 何立友
	 * @日期：2015-05-12
	 *************************************************************************************/
	public List<TimetableLabRelated> getSelfLabTimetableAppointment2s(HttpServletRequest request, int termId,
			int roomId, int week,String teacher);
	
	/*************************************************************************************
	 * @內容：获取实验室当前时间课程
	 * @作者： 戴昊宇
	 * @日期：2018-04-18
	 *************************************************************************************/
	public List<TimetableLabRelated> getCurrentLabTimetableAppointments(int termId, int roomId, int week,int weekday,int belongsClass);
	/*************************************************************************************
	 * @內容：获取实验室当前时间课程
	 * @作者： 戴昊宇
	 * @日期：2018-04-18
	 *************************************************************************************/
	public List<TimetableLabRelated> getCurrentSelfLabTimetableAppointments( int termId, 
			int roomId, int week,int weekday,int belongsClass ) ;

	/*************************************************************************************
	 * Description： 功能-根据学期和实验室，获取实验室学年的人时数
	 *
	 * @作者： 魏城
	 * @日期：2018-05-18
	 *************************************************************************************/
	public Integer getLabTimetableAppointmentHours(int termId,int roomId);

	/*************************************************************************************
	 * Description： 功能-根据学期和实验室，获取实验室开放的人时数
	 *
	 * @作者： 魏城
	 * @日期：2018-05-18
	 *************************************************************************************/
	public Integer[] getLabTimetableAppointmentSelfHours(int termId, int roomId);

	/**
	 * Description 根据实验室、星期、节次查询课程安排
	 * @param termId
	 * @param roomId
	 * @param week
	 * @return
	 * @author 陈乐为 2018-10-9
	 */
	public List<TimetableLabRelated> getSelfLabAppointments(int termId, int roomId, int week);

	/**
	 * Description 根据实验室日课表
	 * @param termId
	 * @param labCenterId
	 * @param roomId
	 * @param week
	 * @param weekday
	 * @return
	 * @author 陈乐为 2018-10-9
	 */
	public List<TimetableLabRelated> getLabAppointmentsByWeekday(int termId, int labCenterId,int roomId, int week,int weekday);

	/**
	 * Description 查询学期的所有课程
	 * @param termId
	 * @return
	 * @author 陈乐为 2018-10-9
	 */
	public List<SchoolCourse> getSchoolCourseLists(int termId);
	/*************************************************************************************
	 * @throws Exception
	 * @內容：导出课表
	 * @作者： 廖文辉
	 * @日期：2018-11-26
	 *************************************************************************************/
	public void exportTimetable(SchoolTerm schoolTerm, String acno, int roomId,int week,String teacher,HttpServletRequest request, HttpServletResponse response) throws Exception;
}