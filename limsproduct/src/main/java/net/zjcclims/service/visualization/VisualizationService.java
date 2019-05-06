package net.zjcclims.service.visualization;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kenai.jaffl.annotations.In;
import net.zjcclims.domain.*;

public interface VisualizationService {
	
	/**
	 * 根据楼宇和楼层查找楼层房间列表
	 * 裴继超
	 * 2016年1月23日
	 */
	public List<LabRoom> getLabRoomsByBuildAndFloor(String buildNumber, String floor,
                                                    Integer page, int pageSize) ;
	/**
	 * 根据楼宇和楼层查找楼层房间列表（所选中心）
	 * 廖文辉
	 * 2018年9月20日
	 */
	public List<LabRoom> getLabRoomsByBuildAndFloorAndLabCenter(String buildNumber, String floor,
                                                                Integer page, int pageSize, String acno) ;
	/**
	 * 根据id查找实验室
	 * 裴继超
	 * 2016年1月23日
	 */
	public LabRoom findLabRoomByPrimaryKey(int id) ;
	
	/**
	 * 保存实验室设备
	 * 裴继超
	 * 2016年1月23日
	 */
	public void saveLabRoomDevice(LabRoomDevice labRoomDevice);
	
	/**
	 * 保存设备字典
	 * 裴继超
	 * 2016年1月27日
	 */
	public SchoolDevice saveSchoolDevice(LabRoomDevice labRoomDevice);
	
	/**
	 * 根据id查找实验室设备
	 * 裴继超
	 * 2016年1月27日
	 */
	public LabRoomDevice findLabRoomDeviceByPrimaryKey(int id);
	
	/**
	 * 删除设备字典
	 * 裴继超
	 * 2016年1月28日
	 */
	public void deletSchoolDevice(String schoolDeviceNumber);
	
	/**
	 * 删除实验室设备位置标记
	 * 裴继超
	 * 2016年1月28日
	 */
	public void deletLabRoomDeviceXY(int id);
	
	/**
	 * 根据是否存在坐标查找实验室设备
	 * 裴继超
	 * 2016年3月24日11:09:34
	 */
	public List<LabRoomDevice> findLabRoomDevicesByLabRoomIdAndXY(int labRoomId);
	
	/**
	 * floor页面替换实验室详细信息map
	 * 裴继超
	 * 2016年3月25日
	 */
	public Map findLabRoomMap(LabRoom labRoom);
	
	/**
	 * 根据tag查找channel
	 * 裴继超
	 * 2016年3月25日
	 *//*
	public List<Channel> findChannelsByTag(int tag);*/
	
	/****************************************************************************
	 * 功能：保存实验分室
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public LabRoom save(LabRoom labRoom);
	
	/**
	 * 根据主键查找字典实验室
	 * 裴继超
	 * 2016年5月27日
	 */
	public SystemRoom findSystemRoomByPrimaryKey(String nummber) ;
	
	/****************************************************************************
	 * 功能：给实验室上传图片
	 * 作者：裴继超
	 * 时间：2016年5月27日
	 ****************************************************************************/
	public void uploadImageForLabRoom(HttpServletRequest request,
                                      HttpServletResponse response, Integer id, Integer type);

	/****************************************************************************
	 * description:删除图片
	 * 
	 * author:于侃
	 * date:2016年9月21日 14:45:13
	 ****************************************************************************/
	public void deleteImageForLabRoom(Integer labRoomid, Integer type, HttpServletRequest request);
	
	/****************************************************************************
	 * @description:删除单独图片
	 * 
	 * @author:魏好
	 * @date:2017年12月06日 
	 ****************************************************************************/
	public void deleteImageForLabRoom(Integer labRoomid, Integer type, Integer photoId, HttpServletRequest request);
	
	/****************************************************************************
	 * description:下载图片
	 * 
	 * author:于侃
	 * date:2016年9月22日 14:21:28
	 ****************************************************************************/
	public void downloadImageForLabRoom(Integer id, HttpServletRequest request,
                                        HttpServletResponse response);

	/****************************************************************************
	 * description:查找设备
	 * 
	 * author:张愉
	 * date:2017年11月4日 
	 ****************************************************************************/
	public List<LabRoomDevice> getLabRoomDeviceBylabroomid(int id, Integer page, int pageSize);

	/**
	 * floor页面替换实验室设备详细信息map
	 * 魏号好
	 * 2018年1月12日
	 */
	public String findLabRoomDeviceMap(LabRoom labRoom);
	/**
	 * 页面替换实验室当前课表详细信息map
	 * 汪哲玮
	 * 2018年4月15日
	 */
	public Map<String, String> findLabRoomTimetableMap(LabRoom labRoom);

	/**
	 * 页面替换实验室软件详细信息map
	 * 汪哲玮
	 * 2018年4月15日
	 */
	public String findLabRoomSoftwareMap(LabRoom labRoom);
	/**
	 * 页面替换实验室设备详细信息mapBy筛选条件
	 * 汪哲玮
	 * 2018年4月15日
	 */
	public String findLabRoomDeviceMapSearched(LabRoom labRoom,
                                               String deviceName, int deviceLend, int deviceAppoint);
	/**
	 * 页面替换实验室楼全部实验室课表列表
	 * 汪哲玮
	 * 2018年4月19日
	 */
	public String findLabBuildingTimetableMap(String buildnumber, String campusNumber);

	/**
	 * 页面替换实验室楼全部实验室视频设备列表
	 * 汪哲玮
	 * 2018年4月19日
	 */
	public String findLabRoomAgent(LabRoom labRoom);
	/**
	 * 查找实验室楼全部实验室视频设备列表
	 * 汪哲玮
	 * 2018年5月10日
	 */
	List<LabRoomAgent> findLabRoomAgentList(LabRoom labRoom);
	/**
	 * 联动查询（樓宇编号和层数）
	 * 廖文辉
	 * 2018年8月29日
	 */
	public String LinkBuildNumberAndFloorNum(String buildNumber, HttpServletRequest request);

	/**
	 * @Description 根据楼宇和楼层获取楼层图
	 * @author 张德冰
	 * @data 2018-10-29
	 */
	public List<SystemFloorPic> findSystemFloorPic(String buildNumber, Integer floor);
	
	/*********************************************************************
	 * Description:获取所有实验室设备表信息
	 * @author: lay
	 * @date :2018/11/20
	 * @return:
	 **********************************************************************/
	public List<LabRoomDevice> getAllLabRoomDevices(LabRoomDevice labRoomDevice,Integer currPage,Integer pageSize);

	/*********************************************************************
	 * Description:获取所有校区
	 * @author: lay
	 * @date :2018/11/22
	 * @return:
	 **********************************************************************/
	public List<SystemCampus> getAllSystemCampus(LabRoomDevice labRoomDevice,Integer currPage,Integer pageSize);

	/*********************************************************************
	 * Description:获取所有楼宇
	 * @author: lay
	 * @date :2018/11/22
	 * @return:
	 **********************************************************************/
	public List<SystemBuild> getAllSystemBuilds(LabRoomDevice labRoomDevice,Integer currPage,Integer pageSize);

	/*********************************************************************
	 * Description:获取所有房间
	 * @author: lay
	 * @date :2018/11/22
	 * @return:
	 **********************************************************************/
	public List<SystemRoomVisual> getAllSystemRooms(LabRoomDevice labRoomDevice,Integer currPage,Integer pageSize);

	/**
	 * Description 根据楼宇编号获取
	 * @param buildNumber
	 * @return
	 * @author 陈乐为 2018-11-28
	 */
	public String LinkSystemRoomAndBuildNumber(String buildNumber);
	
	/*************************************************************************************
	 * Description:根据校区id获取楼宇
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	public List<Map<String,String>> getBuildByCampus(String CampusId);

	/*************************************************************************************
	 * Description:根据楼宇id获取实验室
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	public List<Map<String,String>> getRoomByBuild(String buildNumber);

	/*************************************************************************************
	 * Description:根据实验室id获取设备
	 *
	 * @author: 杨新蔚
	 * @date: 2018/12/11
	 *************************************************************************************/
	public List<Map<String,String>> getDeviceByRoom(String labRoomId);
	/*************************************************************************************
	 * Description:根据楼宇和楼层查找楼层房间列表,根据权限添加学院筛选
	 *
	 * @author: 廖文辉
	 * @date: 2018/12/21
	 *************************************************************************************/
	public List<LabRoom> getLabRoomsByBuildAndFloorAndAcno(String buildNumber, Integer floor,
													Integer page, int pageSize,HttpServletRequest request) ;

	/**
	 * Description 可视化--获取楼层可视化的起始楼层
	 * @param buildNumber
	 * @return
	 * @author 陈乐为 2019年4月13日
	 */
	public SystemFloorPic getStartFloor(String buildNumber);
}
