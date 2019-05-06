package net.zjcclims.service.timetable;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import net.zjcclims.common.LabAttendance;
import net.zjcclims.domain.*;
import net.zjcclims.web.timetable.AttendancetableByWeek;
import net.zjcclims.web.timetable.CheckAttendancetable;

import org.springframework.web.bind.annotation.RequestParam;

import excelTools.Attendance;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring service that handles CRUD requests for TimetableAttendance entities
 * 
 */
public interface TimetableAttendanceService {

	/**
	 * Save an existing TimetableAttendance entity
	 * 
	 */
	public void saveTimetableAttendance(TimetableAttendance timetableattendance);

	/**
	 * Delete an existing TimetableAttendance entity
	 * 
	 */
	public void deleteTimetableAttendance(TimetableAttendance timetableattendance_1);
	
	
	public void saveTimetableAttendance(TimetableAttendance timetableattendance,Integer id,Integer idKey);
	
	/*public List<TimetableAppointment> getTimetableAppointmentsByQuery(int curr, int size);*/
	
	/*public int getCountTimetableAppointmentsByQuery();*/
	
	public int getCountTimetableAppointmentsByQuery(int status);
	
	public List<TimetableAppointment> getTimetableAppointmentsByQuery(
			int status,SchoolCourseDetail schoolCourseDetail, int curr, int size);
	
	/*public List<TimetableAppointment> getTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,
			int status, int curr, int size);*/
	
	public void saveMachineAttendance(List<TimetableAppointment> timetableAppointment) throws ParseException;
	/*************************************************************************************
	 * @內容：根据排课和周次查询考勤
	 * @作者： 李小龙
	 *************************************************************************************/
	public List<TimetableAttendance> showStudentAttendanceByWeek(TimetableAppointment t,Integer week);
	
	public void saveStudentAttendanceByWeek(String username,Integer id,Integer idKey,Integer flag);
	
	public List<CheckAttendancetable> checkTotalCourseAttendance(Set<TimetableAppointment> listtimeappoints,List<SchoolCourseStudent> schoolCourseStudent);
	
	public List<AttendancetableByWeek> showTimetableGroupStudentsByWeek(int studentNumber,TimetableAppointment t,
			List<TimetableGroupStudents> TimetableGroupStudents,@RequestParam Integer id,
			@RequestParam Integer idKey);
	
	
	/*************************************************************************************
	 * @內容：查看所有的预约的列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableAppointment> getTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,
			int status,String acno, int curr, int size);
	
	public int getCountTimetableAppointmentsByQuery(TimetableAppointment timetableAppointment,int status,int iLabCenter);
	/*************************************************************************************
	 * @內容：查询考勤信息表 timetable_attendance
	 * @作者： 徐龙帅
	 * @日期：2014-12-4
	 *************************************************************************************/
public List<TimetableAppointment> findTimetableAttendance(TimetableAppointment timetableAppointment);
public List<TimetableAppointment> findTimetableAttendance(TimetableAppointment timetableAppointment,int page,int pageSize);


/*************************************************************************************
 * 根据选课组编号查询学生名单
 * 李小龙
 *************************************************************************************/
public List<CheckAttendancetable> findStudentsByCourseCode(TimetableAppointment t) ;
/*************************************************************************************
 * 根据排课记录查询学生名单
 * 李小龙
 *************************************************************************************/
public Set<User> findStudentsByTimetableAppointment(TimetableAppointment timeAppt);
/*************************************************************************************
 * 根据学生名单和周次获取学生成绩
 * 李小龙
 *************************************************************************************/
public List<CheckAttendancetable> getTearmScoreByStudents(Set<User> students,int weeks,TimetableAppointment t);
/*************************************************************************************
 * 根据成绩结果获取成绩次数（取最大值）
 * 李小龙
 *************************************************************************************/
public int getTearmScoreTime(List<CheckAttendancetable> checkList);
/*************************************************************************************
 * 查询出当前登录人可以查看的课程考勤
 * 李小龙
 *************************************************************************************/
public List<Attendance> findAttendanceBySchoolCourse(SchoolCourse course,String acno);
/*************************************************************************************
 * 查询出当前登录人可以查看的实验室考勤
 * 贺子龙
 * 2015-11-27
 *************************************************************************************/
public List<LabAttendance> findLabAttendance(String username, String cname,String labName,String acno,int currpage, int pageSize);
/*************************************************************************************
 * 查询出当前登录人可以查看的实验室考勤人数
 * 贺子龙
 * 2015-11-27
 *************************************************************************************/
public int findLabAttendance(String username, String cname,String labName,String acno);


	/**
	 * 功能:获取当前课程
	 * 作者：周志辉
	 * 时间：2017.10.19
	 */
	public List<LabRoom> getAllLabRoomByFloor(int floor);

	/**
	 * 功能:获取当前课程
	 * 作者：周志辉
	 * 时间：2017.10.19
	 */
	public List<TimetableAppointment> getCurSch(int labId);

	/**
	 * 查询所有课表分配情况
	 * 周志辉
	 * 2017-10-20
	 */
	public Integer allTimetableCount(int currpage, int pageSize, HttpServletRequest request);

	/**
	 * 查询所有课表分配情况
	 * 周志辉
	 * 2017-10-20
	 */
	public List<Object[]> findAllTimetable(int currpage, int pageSize, HttpServletRequest request);

	/**
	 * Description 查询课程明细列表
	 * @param request
	 * @param acno
	 * @param currpage
	 * @param pageSize
	 * @return
	 * @author 陈乐为 2019-3-7
	 */
	public List<Object[]> findAttendanceCourseInfo(HttpServletRequest request,String acno, int currpage, int pageSize);

	/**
	 * Description 查询课程明细总数
	 * @param request
	 * @param acno
	 * @return
	 * @author 陈乐为 2019-3-7
	 */
	public int findAttendanceCourseInfoCount(HttpServletRequest request,String acno);
}
