package net.zjcclims.service.timetable;

import java.util.List;

import net.zjcclims.domain.SchoolCourse;

import javax.servlet.http.HttpServletRequest;

public interface SchoolCourseService {
	
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-09-16
	 *************************************************************************************/
	public List<SchoolCourse> getCourseCodeListAll(int termId,String acno);
	
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String getCourseCodeList(int term, String acno);

	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-09-16
	 *************************************************************************************/
	public List<SchoolCourse> getCourseCodeList(int termId);
	/*************************************************************************************
	 * @內容：通过主键查询school_course
	 * @作者：贺子龙
	 * @日期：2016-07-16
	 *************************************************************************************/
	public SchoolCourse findSchoolCourseByPrimaryKey(String courseNo);

	/*************************************************************************************
	 * @內容：保存选课程
	 * @作者： 戴昊宇
	 * @日期：2018-01-11
	 *************************************************************************************/
	public SchoolCourse saveSchoolCourse(SchoolCourse schoolCourse,HttpServletRequest request);
}