package net.zjcclims.service.construction;



import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for LabConstructApp entities
 * 
 */
public interface LabConstructAppService {

	/**
	 * Load an existing LabConstructApp entity
	 * 
	 */
	public Set<LabConstructApp> loadLabConstructApps();

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public void saveLabConstructApp(LabConstructApp labconstructapp);

	/**
	 * Return all LabConstructApp entity
	 * 
	 */
	public List<LabConstructApp> findAllLabConstructApps(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing CProjectSource entity
	 * 
	 */
	public LabConstructApp deleteLabConstructAppCProjectSource(Integer labconstructapp_id, Integer related_cprojectsource_id);

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	public LabConstructApp deleteLabConstructAppCProjectPurpose(Integer labconstructapp_id_1, Integer related_cprojectpurpose_id);

	/**
	 * Save an existing CProjectSource entity
	 * 
	 */
	public LabConstructApp saveLabConstructAppCProjectSource(Integer id, CProjectSource related_cprojectsource);

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public LabConstructApp saveLabConstructAppProjectStartedReports(Integer id_1, ProjectStartedReport related_projectstartedreports);

	/**
	 */
	//public LabConstructApp findLabConstructAppByPrimaryKey(Integer id_2);

	/**
	 * Delete an existing User entity
	 * 
	 */
	public LabConstructApp deleteLabConstructAppUser(Integer labconstructapp_id_2, String related_user_username);

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	public LabConstructApp saveLabConstructAppProjectDevices(Integer id_3, ProjectDevice related_projectdevices);

	/**
	 * Return a count of all LabConstructApp entity
	 * 
	 */
	public Integer countLabConstructApps();

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public LabConstructApp deleteLabConstructAppProjectStartedReports(Integer labconstructapp_id_3, Integer related_projectstartedreports_id);

	/**
	 * Delete an existing LabConstructAppApproval entity
	 * 
	 */
	public LabConstructApp deleteLabConstructAppLabConstructAppApprovals(Integer labconstructapp_id_4, Integer related_labconstructappapprovals_id);

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	public LabConstructApp deleteLabConstructAppProjectDevices(Integer labconstructapp_id_5, Integer related_projectdevices_id);

	/**
	 * Save an existing LabConstructAppApproval entity
	 * 
	 */
	public LabConstructApp saveLabConstructAppLabConstructAppApprovals(Integer id_4, LabConstructAppApproval related_labconstructappapprovals);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public void deleteLabConstructApp(LabConstructApp labconstructapp_1);

	/**
	 * Save an existing User entity
	 * 
	 */
	public LabConstructApp saveLabConstructAppUser(Integer id_5, User related_user);

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	public LabConstructApp saveLabConstructAppCProjectPurpose(Integer id_6, CProjectPurpose related_cprojectpurpose);

	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	public List<LabConstructApp> findAllLabConstructAppByLabConstructApp(LabConstructApp labConstructApp);
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 ****************************************************************************/
	public List<LabConstructApp> findAllLabConstructAppByLabConstructApp(LabConstructApp labConstructApp,
                                                                         int page, int pageSize);
	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 * 时间：2015-03-09
	 ****************************************************************************/
	public Set<LabConstructApp> findAllLabConstructApp();
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 * 时间：2015-03-09
	 ****************************************************************************/
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer Id);

	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	public LabConstructApp save(LabConstructApp labConstructApp);

	/***********************************************************************************************
	 * @public String getLabConstructAppCode() {
	 * @param:
	 * @description:生成最新的申报编号值
	 * @author:李德   2015/03/23
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public String getLabConstructAppCode();

	/***********************************************************************************************
	 * 功能：实验室建设项目-导出
	 * 作者：李德
	 * 时间：2015-03-26
	 ***********************************************************************************************/
	public void exportExcelLabConstructApp(List<LabConstructApp> labConstructApps, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception ;
	/***********************************************************************************************
	 * 功能：根据用户对象查询满足条件的user数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int findUserByCname(User user, String academyNumber);
	/***********************************************************************************************
	 * 功能：根据用户对象查询user并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<User> findUserByCname(User user, String academyNumber,
                                      Integer page, int pageSize);
	/***********************************************************************************************
	 * 功能：保存实验建设参与人员
	 * 作者：李小龙
	 ***********************************************************************************************/
	public LabConstructUser save(LabConstructUser conUser);
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int findDeviceBydeviceName(String deviceName);
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<SchoolDevice> findDeviceBydeviceName(String deviceName,
                                                     Integer page, int pageSize);
	/***********************************************************************************************
	 * 功能：上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李小龙
	 ***********************************************************************************************/
	public String uploadFile(HttpServletRequest request,
                             HttpServletResponse response);
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除申请对应的参与人员
	 * 作者：李小龙
	 ***********************************************************************************************/
	public void deleteLabConstructAppUserByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除申请金额
	 * 作者：李小龙
	 ***********************************************************************************************/
	public void deleteProjectFeeListByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除设备
	 * 作者：李小龙
	 ***********************************************************************************************/
	public void deleteProjectDeviceByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除实验室项目
	 * 作者：李小龙
	 ***********************************************************************************************/
	public void deleteProjectCompletionItemByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除文档(暂不删除服务器上的文件)
	 * 作者：李小龙
	 ***********************************************************************************************/
	public void deleteCommonDocumentByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据实验室申请id查询实验室申请人员
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<LabConstructUser> findLabConstructUserByAppId(Integer AppId);
	/***********************************************************************************************
	 * 功能：根据实验室申请id查询经费
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<ProjectFeeList> findProjectFeeListByAppId(Integer AppId);

	/****************************************************************************
	 * 功能：查询用途
	 * 作者：李德
	 * 时间：2015-4-20
	 ****************************************************************************/
	public Set<CProjectPurpose> findAllProjectPurpose();

	/****************************************************************************
	 * 功能：实验室建设申请Map
	 * 作者：李德
	 * 时间：2015-6-01
	 ****************************************************************************/
	public void getData(Map<String, Object> dataMap, int idKey);
	/****************************************************************************
	 * 功能：实验室建设申请Map
	 * 作者：李德
	 * 时间：2015-7-14
	 ****************************************************************************/	
	public Map getUsersMap(Integer cid);
}