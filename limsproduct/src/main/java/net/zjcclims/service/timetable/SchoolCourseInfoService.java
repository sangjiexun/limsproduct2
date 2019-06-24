package net.zjcclims.service.timetable;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import net.zjcclims.domain.SchoolCourseInfo;

import javax.servlet.http.HttpServletRequest;

public interface SchoolCourseInfoService {
	/*************************************************************************************
	 * @內容：进行获取计数获取课程信息CourseInfo的分页列表信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountCourseInfoByQuery(SchoolCourseInfo schoolCourseInfo) ;
	
	//获取list集合
    public List<SchoolCourseInfo> getList();
	
	/*************************************************************************************
	 * @內容：获取CourseInfo的分页列表信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourseInfo> getCourseInfoByQuery(SchoolCourseInfo schoolCourseInfo,String acno, int flag,int curr, int size);
	
	/*************************************************************************************
	 * @內容：获取查找自建课程信息记录数
	 * @作者： 魏诚
	 * @日期：2014-08-25
	 *************************************************************************************/
	@Transactional
	public int getSelfSchoolCourseInfoTotalRecords();

	/*************************************************************************************
	 * @內容：获取CourseInfo的分页列表信息,flag标记位-1为所有
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public Object getCourseInfoByQuery(SchoolCourseInfo schoolCourseInfo,
			int flag, int curr, int pageSize);

	/**
	 * 查询所有课程
	 * @return
	 */
	public List<Object> findAllSchoolCourseInfo();

	/*************************************************************************************
	 * @內容：获取SchoolCourseInfo的分页列表信息数量
	 * @作者： 魏好
	 * @日期：2018-01-10
	 *************************************************************************************/
	public int findCountSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo);

	/*************************************************************************************
	 * @內容：获取SchoolCourseInfo的分页列表信息
	 * @作者： 魏好
	 * @日期：2018-01-10
	 *************************************************************************************/
	public List<SchoolCourseInfo> findListSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo,int curr, int size);


	/*************************************************************************************
	 * @內容：保存课程
	 * @作者： 魏好
	 * @日期：2018-01-10
	 *************************************************************************************/
	public SchoolCourseInfo saveSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo,HttpServletRequest request);

	/*************************************************************************************
	 * @內容：获取SchoolCourseInfo的信息
	 * @作者： 戴昊宇
	 * @日期：2018-01-10
	 *************************************************************************************/
	public List<SchoolCourseInfo> findSchoolCourseInfo(int flag);

	/**
	 * Description 保存自主课程
	 * @param courseInfo
	 * @author 陈乐为 2019-6-24
	 */
	@Transactional
	public void saveSchoolCourseInfoForSelf(SchoolCourseInfo courseInfo);
}