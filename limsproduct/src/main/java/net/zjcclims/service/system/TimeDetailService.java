package net.zjcclims.service.system;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.SystemCampus;
import net.zjcclims.domain.SystemTime;

public interface TimeDetailService {

	/*************************************************************************************
	 * @內容：获取节次的总记录数
	 * @作者： 叶明盾
	 * @日期：2014-07-28
	 *************************************************************************************/
	public int getTimeTotalRecords();
	
	/*************************************************************************************
	 * @內容：查找所有的节次信息
	 * @作者： 叶明盾
	 * @日期：2014-07-28
	 *************************************************************************************/
	public Set<SystemTime> findAllTimes(int curr, int size);
	
	/*************************************************************************************
	 * @內容：保存节次
	 * @作者： 叶明盾
	 * @日期：2014-07-29
	 *************************************************************************************/
	public SystemTime saveTime(SystemTime systemTime);
	
	/*************************************************************************************
	 * @內容：根据节次的名称查找节次
	 * @作者： 叶明盾
	 * @日期：2014-07-29
	 *************************************************************************************/
	public Set<SystemTime> findTimesByTimeName(String timeName);

	/*************************************************************************************
	 * @內容：根据节次对应的ID查找节次
	 * @作者： 叶明盾
	 * @日期：2014-07-31
	 *************************************************************************************/
	public SystemTime findTimeById(Integer idKey);
	/*************************************************************************************
	 * @內容：根据校区查询节次总记录数
	 * @作者： 李小龙
	 * @日期：2014-12-31
	 *************************************************************************************/
	public int getTimeTotalRecords(String campusNumber);
	/*************************************************************************************
	 * @內容：根据校区编号查找所有的节次信息
	 * @作者： 李小龙
	 * @日期：2014-12-31
	 *************************************************************************************/
	public List<SystemTime> findAllTimes(int page, int size,String campusNumber);
	/*************************************************************************************
	 * @內容：查询出学校的两个校区（松江和延安路校区）
	 * @作者： 李小龙
	 * @日期：2014-12-31
	 *************************************************************************************/
	public List<SystemCampus> findAllCampus();
	/*************************************************************************************
	 * @內容：根据节次名称和校区编号查询节次
	 * @作者： 李小龙
	 * @日期：2015-1-4
	 *************************************************************************************/
	public List<SystemTime> findTimesByTimeNameAndCampusNumber(String timeName,
			String campusNumber);
	/*************************************************************************************
	 * @內容：根据校区编号查询校区
	 * @作者： 李小龙
	 * @日期：2015-1-4
	 *************************************************************************************/
	public SystemCampus findSystemCampusByPromaryKey(String campusNumber);
}