package net.zjcclims.service.construction;


import net.zjcclims.domain.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/*
 * Spring service that handles CRUD requests for ProjectAcceptanceApplication entities
 * 
 */
public interface ProjectAcceptanceApplicationService {

	/**
	 */
/*	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id);
*/
	/**
	 * Delete an existing ProjectCompletionItem entity
	 * 
	 */
	public ProjectAcceptanceApplication deleteProjectAcceptanceApplicationProjectCompletionItems(Integer projectacceptanceapplication_id, Integer related_projectcompletionitems_id);


	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public void deleteProjectAcceptanceApplication(ProjectAcceptanceApplication projectacceptanceapplication);

	/**
	 * Return all ProjectAcceptanceApplication entity
	 * 
	 */
	public List<ProjectAcceptanceApplication> findAllProjectAcceptanceApplications(Integer startResult, Integer maxRows);


	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public void saveProjectAcceptanceApplication(ProjectAcceptanceApplication projectacceptanceapplication_1);

	/**
	 * Save an existing ProjectCompletionItem entity
	 * 
	 */
	public ProjectAcceptanceApplication saveProjectAcceptanceApplicationProjectCompletionItems(Integer id_2, ProjectCompletionItem related_projectcompletionitems);

	/**
	 * Return a count of all ProjectAcceptanceApplication entity
	 * 
	 */
	public Integer countProjectAcceptanceApplications();

	/**
	 * Load an existing ProjectAcceptanceApplication entity
	 * 
	 */
	public Set<ProjectAcceptanceApplication> loadProjectAcceptanceApplications();
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	public List<ProjectAcceptanceApplication> findAllProjectAcceptanceByLabConstruct(ProjectAcceptanceApplication projectAcceptanceApplication);
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	public List<ProjectAcceptanceApplication> findAllProjectAcceptanceByLabConstruct(ProjectAcceptanceApplication projectAcceptanceApplication,
                                                                                     int page, int pageSize);

	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	public Set<ProjectAcceptanceApplication> findAllProjectAcceptanceApplication();
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer Id);

	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	public ProjectAcceptanceApplication save(ProjectAcceptanceApplication projectAcceptanceApplication);
	/***********************************************************************************************
	 * 功能：实验室建设项目验收申请-导出
	 * 作者：李德
	 * 时间：2015-03-27
	 ***********************************************************************************************/
	public void exportExcelProjectAcceptance(List<ProjectAcceptanceApplication> projectAcceptanceApplications, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception ;
	/****************************************************************************
	 * 功能：根据启动报告ID查询验收申请
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProAppId(int idKey);

	/****************************************************************************
	 * 功能：根据启动报告ID查询保存后的验收申请
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public ProjectAcceptanceApplication queryProjectAcceptanceApplicationByProAppId(int idKey);

	/****************************************************************************
	 * 功能：查询专业名称
	 * 作者：李德
	 * 时间：2015-4-14
	 ****************************************************************************/

	public Set<SchoolMajor> findAllSchoolMajor();

	/****************************************************************************
	 * 功能：查询专业名称
	 * 作者：李德
	 * 时间：2015-4-14
	 ****************************************************************************/

	public Set<SchoolCourse> findAllSchoolCourse();

	/***********************************************************************************************
	 * 功能：根据验收申请id删除申请金额
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	public void deleteProjectAcceptFeeListByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据验收申请id删除设备
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	public void deleteProjectAcceptDeviceByAppId(Integer idKey);
	/***********************************************************************************************
	 * 功能：根据验收申请id删除实验室项目
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	public void deleteProjectAcceptCompletionItemByAppId(Integer idKey);

	/***********************************************************************************************
	 * 功能：上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	public String uploadFile(HttpServletRequest request,
                             HttpServletResponse response);
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请附件
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public List<CommonDocument> findCommonDocumentByProAppId(int idKey);
	
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除文档(暂不删除服务器上的文件)
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	public void deleteProjectAcceptAppCommonDocumentByAppId(Integer idKey);

	/**
	 * Description 查询学期课程
	 * @return
	 * @author 陈乐为 2018-7-28
	 */
	public List<SchoolCourse> findSchoolCoursesByTerm();
}