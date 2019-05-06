package net.zjcclims.service.construction;



import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for ProjectStartedReport entities
 * 
 */
public interface ProjectStartedReportService {

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public ProjectStartedReport deleteProjectStartedReportLabConstructApp(Integer projectstartedreport_id, Integer related_labconstructapp_id);

	/**
	 * Load an existing ProjectStartedReport entity
	 * 
	 */
	public Set<ProjectStartedReport> loadProjectStartedReports();

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	public ProjectStartedReport deleteProjectStartedReportProjectDevices(Integer projectstartedreport_id_1, Integer related_projectdevices_id);

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	public void deleteProjectStartedReport(ProjectStartedReport projectstartedreport);

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	public ProjectStartedReport saveProjectStartedReportProjectDevices(Integer id, ProjectDevice related_projectdevices);

	/**
	 * Return a count of all ProjectStartedReport entity
	 * 
	 */
	public Integer countProjectStartedReports();

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	public void saveProjectStartedReport(ProjectStartedReport projectstartedreport_1);

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public ProjectStartedReport saveProjectStartedReportLabConstructApp(Integer id_1, LabConstructApp related_labconstructapp);

	/**
	 * Return all ProjectStartedReport entity
	 * 
	 */
	public List<ProjectStartedReport> findAllProjectStartedReports(Integer startResult, Integer maxRows);

	/**
	 */
/*	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id_2);*/
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	public List<ProjectStartedReport> findAllProjectStartedReportByLabConstruct(ProjectStartedReport projectStartedReport);
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	public List<ProjectStartedReport> findAllProjectStartedReportByLabConstruct(ProjectStartedReport projectStartedReport,
                                                                                int page, int pageSize);

	/****************************************************************************
	 * 功能：根据申请报告ID查询启动报告
	 * 作者：李德
	 * 时间：2015-04-13
	 ****************************************************************************/
	public List<ProjectStartedReport> findProjectStartedReportByLabConstructId(int idKey);

	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	public Set<ProjectStartedReport> findAllProjectStartedReport();
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer Id);

	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	public ProjectStartedReport save(ProjectStartedReport projectStartedReport);

	/***********************************************************************************************
	 * 功能：实验室建设项目启动报告-导出
	 * 作者：李德
	 * 时间：2015-03-27
	 ***********************************************************************************************/
	public void exportExcelProjectStartedReport(List<ProjectStartedReport> projectStartedReports, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception ;

	/****************************************************************************
	 * 功能：查询出所有的院系
	 * 作者：李德
	 * 时间：2015-04-15
	 ****************************************************************************/
	public Set<SchoolAcademy> findAllSchoolAcademy();

	/***********************************************************************************************
	 * 功能：根据启动报告id删除申请金额
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	public void deleteProjectStartFeeListByReportId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据启动报告id删除设备
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	public void deleteProjectStartDeviceByReportId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据启动报告id删除实验室项目
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	public void deleteProjectStartCompletionItemByReportId(Integer idKey);

	/****************************************************************************
	 * 功能：根据实验室申请ID查询保存后的启动报告
	 * 作者：李德
	 * 时间：2015-04-15
	 ****************************************************************************/
	public ProjectStartedReport queryProjectStartedReportByReportId(int idKey);

	/***********************************************************************************************
	 * 功能：根据设备名称查询设备数量
	 * 作者：李德
	 * 时间：2015-04-15
	 ***********************************************************************************************/
	public int findStartDeviceBydeviceName(String deviceName);
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备并分页
	 * 作者：李德
	 * 时间：2015-04-15
	 ***********************************************************************************************/
	public List<SchoolDevice> findStartDeviceBydeviceName(String deviceName,
                                                          Integer page, int pageSize);

	/***********************************************************************************************
	 * 功能：上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	public String uploadFile(HttpServletRequest request,
                             HttpServletResponse response);
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询启动报告附件
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<CommonDocument> findCommonDocumentByProAppId(int idKey);
	
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除文档(暂不删除服务器上的文件)
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	public void deleteProjectStartReportCommonDocumentByAppId(Integer idKey);
}
