package net.zjcclims.service.common;

import net.zjcclims.domain.LabRoomLimitTime;
import net.zjcclims.domain.TimetableAppointment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface MySQLService {
	/**************************************************************************************
	 * 读取视图的数据并分页
	 * 李小龙
	 **************************************************************************************/
	public List getViewOfStudentCourse(int pageSize,int page);
	/**************************************************************************************
	 * 根据课程id读取微课表的章节
	 * 李小龙
	 **************************************************************************************/
	public List getWKCourseChapter(int id);
	
	/**************************************************************************************
	 * 根据课程id读取微课的课时
	 * 李小龙
	 **************************************************************************************/
	public List getWKCourseLesson(int id);
	
	/****************************************************
	 * 功能：调用存储过程--将针对实验室的排课映射到实验室禁用时间（直接排课专用）
	 * 作者： 贺子龙
	 * 日期：2016-05-28
	 *****************************************************/
	public void createLabLimitByDirectAppointment(Integer appointmentId);
	
	/****************************************************
	 * 功能：调用存储过程--将针对实验室的排课映射到实验室禁用时间
	 * 作者： 贺子龙
	 * 日期：2016-05-28
	 *****************************************************/
	public void createLabLimitByAppointment(Integer appointmentId);

	/********************************************************************************
	 * Description: 排课-排课首页{列出所有的选课组}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public List listCourseDetails(HttpServletRequest request, String acno, int page, int pageSize);
	/********************************************************************************
	 * Description: 排课-排课首页{所有选课组数量}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public int countCourseDetails(HttpServletRequest request, String acno);
	/********************************************************************************
	 * Description: 排课-排课首页{列出所有的选课组}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public List listCourseDetailsFortearch(HttpServletRequest request,String acno, int page,int pageSize);
	/********************************************************************************
	 * Description: 排课-排课首页{查询下拉框列出所有选课组}
	 * @author: 杨新蔚
	 * @date: 2018-08-07
	 *********************************************************************************/
	public List listCourseDetailsQuery();

	/********************************************************************************
	 * Description: 我的排课选课组
	 * @author: 戴昊宇
	 * @date: 2018-04-03
	 *********************************************************************************/
	public List mylistCourseDetails(HttpServletRequest request,String acno, int page,int pageSize);
	/********************************************************************************
	 * Description: 我的排课选课组数量
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public int countmyCourseDetails(HttpServletRequest request,String acno);
	/********************************************************************************
	 * Description: 排课-排课首页{列出所有的选课组}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public List listCourseDetails(HttpServletRequest request,String acno, int page,int pageSize,Integer flag);

	/********************************************************************************
	 * Description: 排课-排课首页{所有选课组数量}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public int countCourseDetails(HttpServletRequest request, Integer cid,int flag);

	/**
	 * Description 获取直播链接
	 * @author 黄保钱 2018-11-21
	 */
	public Map<Integer, String> getLivePathByApp(List<TimetableAppointment> timetableAppointments);

	/**
	 * Description 获取录播链接
	 * @author 黄保钱 2018-11-21
	 */
	public Map<Integer, List<String>> getHttpPathByApp(List<TimetableAppointment> timetableAppointments);

    /****************************************************************************
     * Description：保存实验室禁用时间段
     *
     * @作者：魏诚
     * @时间：2016-03-05
     ****************************************************************************/
    public void saveLabRoomLimitTime(HttpServletRequest request, LabRoomLimitTime labRoomLimitTime,Integer type);
}
