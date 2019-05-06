package net.zjcclims.service.video;

import net.zjcclims.domain.TimetableAppointment;

import java.util.List;

public interface VideoPublishService{
	/************************************************************ 
	 * @功能:获取当前节次(视频发布系统特别版，SystemTime的数据是特制的)
	 * @作者：由controller移入
	 * @日期：2016-07-12
	 ************************************************************/
	public int getCurClass();
	
	/**
	 * 功能:获取当前课程
	 * 由controller移入
	 * 2016.07.12
	 */
	public List<TimetableAppointment> getCurSch(int classSection,int labId);
	
	/*************************************************************************************
	 * @內容：根据排课和周次查询实际考勤人数
	 * @作者： 贺子龙
	 *************************************************************************************/
	public int countStudentAttendanceByWeek(int appId,Integer week, int weekday, int classSection);

	/**
	 * Description 查询当前学期所有排课、预约的实验室
	 * @return
	 * @author 陈乐为 2018-9-20
	 */
	public String findAllLabByCourseTerm(int currpage, int pageSize, Integer type, String id, Integer floor);

	public List<Object[]> findAllLabByCourseTerms(int currpage, int pageSize, Integer type, String id, Integer floor);

	/**
	 * Description 当前正在上课的实验室总数
	 * @return
	 * @author 陈乐为 2018-9-25
	 */
	public int findAllLabCountByCourseTerm(Integer type, String id, Integer floor);
	
}