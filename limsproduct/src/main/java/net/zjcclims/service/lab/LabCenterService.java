package net.zjcclims.service.lab;

import java.math.BigDecimal;
import java.util.List;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;

public interface LabCenterService {

	/**
	 * 根据实验中心主键查找实验中心对象
	 * @author hly
	 * 2015.07.24
	 */
	public LabCenter findLabCenterByPrimaryKey(Integer labCenterId);
	
	/**
	 * 获取所有的实验中心
	 * @author hly
	 * 2015.07.24
	 */
	public List<LabCenter> findAllLabCenterByQuery(HttpServletRequest request, LabCenter labCenter, Integer currpage, Integer pageSize);

	/**
	 * Description 中心数
	 * @param labCenter
	 * @return
	 */
	public int findAllLabCenterByQueryCount(LabCenter labCenter, HttpServletRequest request);
	
	/**
	 * 保存实验中心数据
	 * @author hly
	 * 2015.07.27
	 */
	public LabCenter saveLabCenter(LabCenter labCenter);
	
	/**
	 * 删除实验中心
	 * @author hly
	 * 2015.07.27
	 */
	public boolean deleteLabCenter(Integer labCenterId);

	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabCenter> findAllLabCenterByLabCenter(LabCenter labCenter);

	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabCenter> findAllLabCenterByLabCenter(LabCenter labCenter,
			int page, int pageSize);
	
	/****************************************************************************
	 * 功能：统计实验中心总面积
	 * 作者：周志辉
	 ****************************************************************************/
	public BigDecimal countAllLabRoomAreaByQuery(LabCenter labCenter);
	
	/****************************************************************************
	 * 功能：统计实验中心管理员数量
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomAdmin> findAllLabRoomAdminByLabCenter(LabCenter labCenter);
	
	/****************************************************************************
	 * 功能：查询实训中心所有实训室
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoom> findAllLabRoomByQuery(LabCenter labCenter,
			int page, int pageSize);
	
	/****************************************************************************
	 * 功能：查询实训中心所有设备
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomDevice> findAllLabRoomDeviceByQuery(LabCenter labCenter, int page, int pageSize);

	/****************************************************************************
	 * 功能：求和每个实训中心的设备列表里的设备价格
	 * 作者：黄保钱
	 ****************************************************************************/
	public List sumAllLabRoomDeviceByQuery(LabCenter labCenter, int page, int pageSize);

	/**
	 * Description 所有实验中心
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public List<LabCenter> findAllCenters();

	/**
	 * Description 中心设备数量
	 * @param labCenter
	 * @return
	 * @author 陈乐为 2018-8-7
	 */
	public int findAllLabRoomDeviceByQueryCount(LabCenter labCenter);

	/**
	 * Description 中心实验室管理员数
	 * @param labCenter
	 * @return
	 * @author 陈乐为 2018-8-7
	 */
	public int findAllLabRoomAdminByLabCenterCount(LabCenter labCenter);

	/**
	 * Description 中心下实验室数
	 * @param labCenter
	 * @return
	 * @author 陈乐为 2018-8-7
	 */
	public int findAllLabRoomByQueryCount(LabCenter labCenter);

	/**
	 * Description 按实验中心主任查找实验中心
	 * @param username 实验中心主任
	 * @return 该实验中心主任所管理的实验中心
	 * @author 黄保钱 2018-08-22
	 */
	public List findAllLabRoomByAdmin(String username);

	/**
	 * Description 根据学院查找所有实验中心
	 * @param academyNumber 学院id
	 * @return 该学院下所有实验中心
	 * @author 刘博越 2019-03-26
	 */
	public List<LabCenter> findAllLabCenterByAcademy(String academyNumber);

}
