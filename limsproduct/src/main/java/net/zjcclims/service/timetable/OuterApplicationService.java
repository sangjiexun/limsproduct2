package net.zjcclims.service.timetable;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.zjcclims.domain.LabRoom;
import net.zjcclims.domain.OperationItem;
import net.zjcclims.domain.SchoolTerm;

public interface OuterApplicationService {

	/************************************************************ 
	 * 获取可排的实验室列表
	 * 作者：魏诚
	 * 日期：2014-07-14
	 ************************************************************/
	public Map<Integer, String> getLabRoomMap(String acno);
	
	/************************************************************ 
	 * 获取可选的教师列表
	 * 作者：魏诚
	 * 日期：2014-07-24
	 ************************************************************/
	public Map<String, String> getTimetableTearcherMap() ;
	
	/************************************************************ 
	 * 获取可排的实验项目列表
	 * 作者：魏诚
	 * 日期：2014-07-24
	 ************************************************************/
	public Map<Integer, String> getTimetableItemMap(String courseNo);
	
	/************************************************************ 
	 * 获取可选的教学周
	 * 作者：魏诚
	 * 日期：2014-07-24
	 ************************************************************/
	public Integer[] getValidWeeks(int term,int classids,int labroom,int weekday) ;
	
	/************************************************************
	 * @获取可选的教学周
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Integer[] getValidWeeks(int term, int[] classes, int[] labrooms,
			int weekday);
	/************************************************************
	 * @获取可选的教学周--编辑排课记录
	 * @作者：贺子龙
	 * @日期：2016-01-06
	 ************************************************************/
	public Integer[] getValidWeeks(int term, int[] classes, int[] labrooms,
			int weekday, int tableAppId);
	/************************************************************ 
	 * 根据选课组编号获取可排的实验项目列表
	 * 作者：魏诚
	 * 日期：2014-07-24
	 ************************************************************/
	public List<OperationItem> getCourseCodeItemList(String courseCode) ;
	
	/*************************************************************************************
	 * @內容：判断选课资源是否冲突
	 * @作者： 魏誠
	 * @日期：2015-03-10
	 *************************************************************************************/
	public boolean isTimetableConflict(int term, int weekday, int[] labrooms, int[] classes,int[] weeks);

	/************************************************************
	 * @获取可选的教学周-针对实验室预约
	 * @作者：魏诚
	 * @日期：2015-07-09
	 ************************************************************/
	public Integer[] getValidLabWeeks(int term, int[] classes, int[] labrooms, int weekday);
	/************************************************************
	 * @根据选课组，获取可选的软件 列表
	 * @作者：戴昊宇
	 * @日期：2017-07-24
	 ************************************************************/
	public Map<Integer, String> getTimetableSoftwarerMap();
	/************************************************************
	 * 功能：获取空闲的实验室
	 * 作者：贺子龙
	 * 日期：2016-04-19
	 ************************************************************/
	public List<LabRoom> getValidLabRooms( int term,int weeks, int weekday, int[] classes,Integer selfCourseId,Integer confinementTime,String coursecode);
	
	/************************************************************
	 * Description 根據實驗中心獲取所有教師列表
	 * 
	 * @param cid 中心主鍵
	 * @return map
	 * @author 陳樂為
	 * @date 2017年9月10日
	 ************************************************************/
	public Map<String, String> getAllTearchersMap(String acno);
	/************************************************************
	 * 功能：获取空闲的实验室（实验室工位预约）
	 * 作者：戴昊宇
	 * 日期：2017-09-27
	 ************************************************************/
	public Integer getValidLabRoomsStation(Calendar time,Integer labRoomId,Calendar startTime,Calendar endTime,Integer term);

	/************************************************************
	 * @根据选课组，获取可选的实验 列表
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Map<String, String> getTimetableTearcherMap(String acno);


	/************************************************************
	 * @ 获取可排的实验分室列表(key是实验室编号） @ 作者：廖文辉 @ 日期：2017-01-24
	 ************************************************************/
	public List<LabRoom>  getLabRoomNumberList(String acno);

	/************************************************************
	 * @根据选课组，可选学生列表
	 * @作者：戴昊宇
	 * @日期：2018-06-25
	 ************************************************************/
	public Map<String, String> getStudentMap();


	/**
	 * @Description 根据条件获取排课的实验室
	 * @data 2019-01-10
	 * @author 张德冰
	 * */
	public Map<Integer, String> findLabRoomMaps(String acno, Integer term, Integer week);
}