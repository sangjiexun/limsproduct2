package net.zjcclims.service.timetable;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.domain.SchoolCourseDetail;
import net.zjcclims.domain.SchoolTerm;
import net.zjcclims.domain.SchoolWeek;

import javax.servlet.http.HttpServletRequest;

public interface SchoolCourseDetailService {

	/*************************************************************************************
	 * @內容：根据时间查找学期
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolTerm> getSchoolTermsByNow();

	/*************************************************************************************
	 * @內容：创建一条通过周次和星期分组的语句
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolWeek> getWeeksByNow(int termId);

	/*************************************************************************************
	 * @內容：获取可用的实验室分室
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public Map<Integer, String> getLabRoomMap();

	/*************************************************************************************
	 * @內容：根据appointment_no获取实验安排表分组数
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getGroupedCourseAppointmentsByAppointNo();

	/*************************************************************************************
	 * @內容：根据以选课组为单元的获取实验安排表分组
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourseDetail> getCourseCodeInSchoolCourseDetail(int term);
	
	/*************************************************************************************
	 * @內容：根据以选课组为单元的获取实验安排表分组
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourse> getCourseCodeInSchoolCourse(int term,String acno);

	/*************************************************************************************
	 * @內容：根据学期和状态获取所有的排课课程数量
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountAllStatusTimetabledCourses(int termId, int statusId);

	/*************************************************************************************
	 * @內容：根据学期和状态获取已排课的课程列表
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourse> getHavedTimetabledCourses(int termId,
			int statusId, int curr, int size);

	
	/*************************************************************************************
	 * @內容：根据coursedetail获取实验室
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public Set<LabRoom> getLabsByCourse(SchoolCourseDetail courseDetail);

	/*************************************************************************************
	 * @內容：根据coursedetail获取实验室
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourseDetail> getNeedTimetabledAppointments(
			List<SchoolCourseDetail> details);

	/*************************************************************************************
	 * @內容：进行教务排课，根据SchoolCourseDetail
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public void doDirectTimetable(String courseCode,  String[] labs,String[] tutorRelateds,String[] teacherRelateds ,String[] sLabRoomDevice)  throws ParseException;

	/*************************************************************************************
	 * @內容：进行termid,获取教务CourseDetail的分页列表信息（教务排课的入口显示页面）
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourseDetail> getCourseDetailsByQuery(int termId, SchoolCourseDetail schoolCourseDetail,
			int curr, int size,String acno, HttpServletRequest request);
	public List<SchoolCourseDetail> getCourseDetailsByQuery(int termId, SchoolCourseDetail schoolCourseDetail,String teacher,String selectedDetailName,
															int curr, int size,int iLabCenter);

	/*************************************************************************************
	 * @內容：进行termid,获取计数获取教务CourseDetail的分页列表信息（教务排课的入口显示页面）
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountCourseDetailsByQuery(int termId, SchoolCourseDetail schoolCourseDetail, String acno, HttpServletRequest request);
	public int getCountCourseDetailsByQuery(int termId, SchoolCourseDetail schoolCourseDetail,String acno,String teacher);
	
	/*************************************************************************************
	 * @內容：获取可用的周次教务排课信息json
	 * @作者： 魏誠
	 * @日期：2014-08-4
	 *************************************************************************************/
	public String getValidWeek(int weekday,String[] labrooms,String courseCode) ;

	/*************************************************************************************
	 * @內容：获取可用的周次教务排课信息map
	 * @作者： 魏誠
	 * @日期：2014-08-4
	 *************************************************************************************/
	public Map<Integer,Integer> getValidWeekMap(int weekday,String[] labrooms,String courseCode);
	
	/*************************************************************************************
	 * @內容：根据选课组编号获取教务排课信息
	 * @作者： 魏誠
	 * @日期：2014-08-4
	 *************************************************************************************/
	public List<SchoolCourseDetail> getSchoolCourseDetailByCourseCode(String courseCode);	
	/*************************************************************************************
	 * @內容：进行termid,获取所有课程
	 * @作者： 戴昊宇
	 * @日期：2017-11-15
	 *************************************************************************************/
	public List<SchoolCourseDetail> getAllCourseDetails(int termId);

	/*************************************************************************************
	 * @內容：保存选课程
	 * @作者： 戴昊宇
	 * @日期：2018-01-11
	 *************************************************************************************/
	public SchoolCourse saveSchoolCourse(SchoolCourse schoolCourse,HttpServletRequest request);

	/*************************************************************************************
	 * @內容：保存SchoolCourseDetail表信息
	 * @作者： 戴昊宇
	 * @日期：2018-03-27
	 *************************************************************************************/
	public SchoolCourseDetail saveSchoolCourseDetail(String courseNo,int startWeek,int endWeek,int weekday,int startClass,int endClass,SchoolTerm schoolTerm,int count);

	/*************************************************************************************
	 * @內容：通过course_no查找SchoolCourseDetail
	 * @作者： 杨新蔚
	 * @日期：2018-07-23
	 *************************************************************************************/
	public List<SchoolCourseDetail> findSchoolCourseDetailByCourseNO(String courseNo);
}