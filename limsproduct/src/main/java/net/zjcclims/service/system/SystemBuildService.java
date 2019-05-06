package net.zjcclims.service.system;

import net.zjcclims.domain.SystemBuild;
import net.zjcclims.domain.SystemRoom;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface SystemBuildService {
	/*************************************************************************************
	 * @內容：根据校区id查询楼栋
	 * @作者：李小龙
	 *************************************************************************************/
	public List<SystemBuild> findBuildByCampusId(String campusNumber);
	/*************************************************************************************
	 * @內容：根据楼栋编号查询房间
	 * @作者：李小龙
	 *************************************************************************************/
	public List<SystemRoom> findRoomByBuildNumber(String buildNumber);
	/*************************************************************************************
	 * @內容：根据学员编号查询楼栋
	 * @作者：李小龙
	 *************************************************************************************/
	public List<SystemBuild> finSystemBuildByAcademy(String academyNumber);
	
	/*************************************************************************************
	 * @內容：查询所有楼栋
	 * @作者：裴继超
	 * @时间：2016年3月22日14:36:37
	 *************************************************************************************/
	public List<SystemBuild> finAllSystemBuilds();
	
	/*************************************************************************************
	 * 保存楼栋
	 * 裴继超
	 * 2016年3月22日
	 **************************************************************************************/
	public void saveSystemBuild(SystemBuild systemBuild);
	
	
	/*************************************************************************************
	 * @內容：根据id查询楼栋
	 * @作者：裴继超
	 * @时间：2016年3月22日14:36:37
	 *************************************************************************************/
	public SystemBuild finSystemBuildById(String buildNumber);
	
	/**
	 * 根据是否存在坐标查楼宇
	 * 裴继超
	 * 2016年4月1日
	 */
	public List<SystemBuild> findBuildingByXY();

	/**
	 * 根据id获取楼宇
	 * 裴继超
	 * 2016年4月5日
	 */
	public SystemBuild findBuildingbyBuildNumber(String buildNumber);

	/**
	 * @Description 根据查询条件获取楼宇
	 * @author 张德冰
	 * @data 2018-10-29
	 */
	public List<SystemBuild> findSystemBuildByQuery(SystemBuild systemBuild, HttpServletRequest request, Integer currpage, Integer pageSize);
	/**
	 * @Description 根据查询条件获取楼宇
	 * @author 张廖文辉
	 * @data 2018-12-21
	 */
	public List<SystemBuild> findBuildByCampusIdAndAcno(String campusNumber,HttpServletRequest request);


}