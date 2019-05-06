package net.zjcclims.service.construction;


import net.zjcclims.service.common.BaseApplicationService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Spring service that handles CRUD requests for ProjectAcceptanceApplication entities
 * 
 */

@Service("ProjectAcceptanceApplicationService")
@Transactional
public class ProjectAcceptanceApplicationServiceImpl implements
		ProjectAcceptanceApplicationService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectAcceptanceApplication entities
	 * 
	 */
	@Autowired
	private ProjectAcceptanceApplicationDAO projectAcceptanceApplicationDAO;

	/**
	 * DAO injected by Spring that manages ProjectCompletionItem entities
	 * 
	 */
	@Autowired
	private ProjectCompletionItemDAO projectCompletionItemDAO;
	@Autowired
	private BaseApplicationService baseApplicationService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private ProjectAcceptFeeListDAO projectAcceptFeeListDAO;
	@Autowired
	private ProjectAcceptCompletionItemDAO projectAcceptCompletionItemDAO;
	@Autowired
	private ProjectAcceptDeviceDAO projectAcceptDeviceDAO;
	@Autowired
	private CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private ShareService shareService;
	
	/**
	 * Instantiates a new ProjectAcceptanceApplicationServiceImpl.
	 *
	 */
	public ProjectAcceptanceApplicationServiceImpl() {
	}

	/**
	 */
/*	@Transactional
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id) {
		return projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(id);
	}*/

	/**
	 * Delete an existing ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public ProjectAcceptanceApplication deleteProjectAcceptanceApplicationProjectCompletionItems(Integer projectacceptanceapplication_id, Integer related_projectcompletionitems_id) {
		ProjectCompletionItem related_projectcompletionitems = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(related_projectcompletionitems_id, -1, -1);

		ProjectAcceptanceApplication projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(projectacceptanceapplication_id, -1, -1);

		related_projectcompletionitems.setProjectAcceptanceApplication(null);
		//projectacceptanceapplication.getProjectCompletionItems().remove(related_projectcompletionitems);

		projectCompletionItemDAO.remove(related_projectcompletionitems);
		projectCompletionItemDAO.flush();

		return projectacceptanceapplication;
	}



	/**
	 * Delete an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public void deleteProjectAcceptanceApplication(ProjectAcceptanceApplication projectacceptanceapplication) {
		projectAcceptanceApplicationDAO.remove(projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();
	}

	/**
	 * Return all ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public List<ProjectAcceptanceApplication> findAllProjectAcceptanceApplications(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectAcceptanceApplication>(projectAcceptanceApplicationDAO.findAllProjectAcceptanceApplications(startResult, maxRows));
	}



	/**
	 * Save an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public void saveProjectAcceptanceApplication(ProjectAcceptanceApplication projectacceptanceapplication) {
		ProjectAcceptanceApplication existingProjectAcceptanceApplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(projectacceptanceapplication.getId());

		if (existingProjectAcceptanceApplication != null) {
			if (existingProjectAcceptanceApplication != projectacceptanceapplication) {
				existingProjectAcceptanceApplication.setId(projectacceptanceapplication.getId());
				existingProjectAcceptanceApplication.setExpectCompleteDate(projectacceptanceapplication.getExpectCompleteDate());
				existingProjectAcceptanceApplication.setRealityCompleteDate(projectacceptanceapplication.getRealityCompleteDate());
				existingProjectAcceptanceApplication.setOriginalLabroomArea(projectacceptanceapplication.getOriginalLabroomArea());
				existingProjectAcceptanceApplication.setOriginalLabroomAdd(projectacceptanceapplication.getOriginalLabroomAdd());
				existingProjectAcceptanceApplication.setOriginalLabroomValue(projectacceptanceapplication.getOriginalLabroomValue());
				existingProjectAcceptanceApplication.setOriginalLabroomItem(projectacceptanceapplication.getOriginalLabroomItem());
				existingProjectAcceptanceApplication.setMajorAmount(projectacceptanceapplication.getMajorAmount());
				existingProjectAcceptanceApplication.setCourseAmount(projectacceptanceapplication.getCourseAmount());
				existingProjectAcceptanceApplication.setExpectLabItem(projectacceptanceapplication.getExpectLabItem());
				existingProjectAcceptanceApplication.setRealityLabItem(projectacceptanceapplication.getRealityLabItem());
				existingProjectAcceptanceApplication.setConstructCondition(projectacceptanceapplication.getConstructCondition());
				existingProjectAcceptanceApplication.setOpenLabItem(projectacceptanceapplication.getOpenLabItem());
			}
			projectacceptanceapplication = projectAcceptanceApplicationDAO.store(existingProjectAcceptanceApplication);
		} else {
			projectacceptanceapplication = projectAcceptanceApplicationDAO.store(projectacceptanceapplication);
		}
		projectAcceptanceApplicationDAO.flush();
	}

	/**
	 * Save an existing ProjectCompletionItem entity
	 * 
	 */
	@Transactional
	public ProjectAcceptanceApplication saveProjectAcceptanceApplicationProjectCompletionItems(Integer id, ProjectCompletionItem related_projectcompletionitems) {
		ProjectAcceptanceApplication projectacceptanceapplication = projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(id, -1, -1);
		ProjectCompletionItem existingprojectCompletionItems = projectCompletionItemDAO.findProjectCompletionItemByPrimaryKey(related_projectcompletionitems.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectCompletionItems != null) {
			existingprojectCompletionItems.setId(related_projectcompletionitems.getId());
			existingprojectCompletionItems.setExperimentName(related_projectcompletionitems.getExperimentName());
			existingprojectCompletionItems.setExperimentProperty(related_projectcompletionitems.getExperimentProperty());
			existingprojectCompletionItems.setRequiredHour(related_projectcompletionitems.getRequiredHour());
			existingprojectCompletionItems.setObjectItem(related_projectcompletionitems.getObjectItem());
			existingprojectCompletionItems.setEquipmentAmount(related_projectcompletionitems.getEquipmentAmount());
			existingprojectCompletionItems.setSimultaneouslyAmount(related_projectcompletionitems.getSimultaneouslyAmount());
			existingprojectCompletionItems.setPerGroupAmount(related_projectcompletionitems.getPerGroupAmount());
			existingprojectCompletionItems.setExperimentOutline(related_projectcompletionitems.getExperimentOutline());
			existingprojectCompletionItems.setExperimentInstructor(related_projectcompletionitems.getExperimentInstructor());
			existingprojectCompletionItems.setUseSitutation(related_projectcompletionitems.getUseSitutation());
			related_projectcompletionitems = existingprojectCompletionItems;
		} else {
			related_projectcompletionitems = projectCompletionItemDAO.store(related_projectcompletionitems);
			projectCompletionItemDAO.flush();
		}

		related_projectcompletionitems.setProjectAcceptanceApplication(projectacceptanceapplication);
		//projectacceptanceapplication.getProjectCompletionItems().add(related_projectcompletionitems);
		related_projectcompletionitems = projectCompletionItemDAO.store(related_projectcompletionitems);
		projectCompletionItemDAO.flush();

		projectacceptanceapplication = projectAcceptanceApplicationDAO.store(projectacceptanceapplication);
		projectAcceptanceApplicationDAO.flush();

		return projectacceptanceapplication;
	}

	/**
	 * Return a count of all ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public Integer countProjectAcceptanceApplications() {
		return ((Long) projectAcceptanceApplicationDAO.createQuerySingleResult("select count(o) from ProjectAcceptanceApplication o").getSingleResult()).intValue();
	}

	/**
	 * Load an existing ProjectAcceptanceApplication entity
	 * 
	 */
	@Transactional
	public Set<ProjectAcceptanceApplication> loadProjectAcceptanceApplications() {
		return projectAcceptanceApplicationDAO.findAllProjectAcceptanceApplications();
	}
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@Override
	public List<ProjectAcceptanceApplication> findAllProjectAcceptanceByLabConstruct(ProjectAcceptanceApplication projectAcceptanceApplication) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectAcceptanceApplication c where 1=1";
		if(projectAcceptanceApplication.getProjectName()!=null&&!projectAcceptanceApplication.getProjectName().equals("")){
			sql+=" and c.projectName like '%"+projectAcceptanceApplication.getProjectName()+"%'";
		}
		//超级管理员和教务
/*		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1){
			
		}else if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){
			
		}else{
			User user=shareService.getUser();
			if(user.getSchoolAcademy()!=null){
				if(user.getSchoolAcademy().getAcademyNumber()!=null&&!user.getSchoolAcademy().getAcademyNumber().equals("")){
					sql+=" and c.schoolAcademy.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"' ";
				}
			}
			
		}				
		sql+=" order by c.updatedAt desc";*/
		return projectAcceptanceApplicationDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@Override
	public List<ProjectAcceptanceApplication> findAllProjectAcceptanceByLabConstruct(ProjectAcceptanceApplication projectAcceptanceApplication,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectAcceptanceApplication c where 1=1";
		if(projectAcceptanceApplication.getProjectName()!=null&&!projectAcceptanceApplication.getProjectName().equals("")){
			sql+=" and c.projectName like '%"+projectAcceptanceApplication.getProjectName()+"%'";
		}
		//超级管理员和教务
/*		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1){
			
		}else if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){
			
		}else{
			User user=shareService.getUser();
			if(user.getSchoolAcademy()!=null){
				if(user.getSchoolAcademy().getAcademyNumber()!=null&&!user.getSchoolAcademy().getAcademyNumber().equals("")){
					sql+=" and c.schoolAcademy.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"' ";
				}
			}
			
		}*/		
		//sql+=" order by c.updatedAt desc";
		return projectAcceptanceApplicationDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@Override
	public Set<ProjectAcceptanceApplication> findAllProjectAcceptanceApplication() {
		// TODO Auto-generated method stub
		return projectAcceptanceApplicationDAO.findAllProjectAcceptanceApplications();
	}
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@Override
	public ProjectAcceptanceApplication findProjectAcceptanceApplicationByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return projectAcceptanceApplicationDAO.findProjectAcceptanceApplicationByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-23
	 ****************************************************************************/
	@Override
	public ProjectAcceptanceApplication save(ProjectAcceptanceApplication ProjectAcceptanceApplication) {
		// TODO Auto-generated method stub
		return projectAcceptanceApplicationDAO.store(ProjectAcceptanceApplication);
	}	
	
	/***********************************************************************************************
	 * 功能：实验室建设项目启动报告-导出
	 * 作者：李德
	 * 时间：2015-03-27
	 ***********************************************************************************************/
	public void exportExcelProjectAcceptance(List<ProjectAcceptanceApplication> projectAcceptanceApplications,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		// 循环alls；
		for (ProjectAcceptanceApplication projectAcceptanceApplication : projectAcceptanceApplications) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			
			// 申请编号；
			map.put("labConstructAppCode", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getLabConstructAppCode());
			// 项目名称；
			map.put("projectName", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getProjectName());
			// 申请人；
			map.put("appUser", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getUser().getCname());
			// 所在系部；
			map.put("academyName", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getUser().getSchoolAcademy().getAcademyName());
			// 申报时间；
			if(projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getAppDate()!=null){
				map.put("appDate", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getAppDate().getTime());
			}else{
				map.put("appDate", "");
			}
			
			// 用途；
			//map.put("purpose", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getCProjectPurpose().getName());
			// 项目来源；
			map.put("projectSource", projectAcceptanceApplication.getProjectStartedReport().getLabConstructApp().getCProjectSource().getName());			

			;

			list1.add(map);
		}
		// 给表设置名称；
		String title = "实验建设项目申请表  " + 1 + "-" + this.countProjectAcceptanceApplications();
		// 给表设置表名；
		String[] hearders = new String[] { "申请编号", "项目名称", "申请人", "所在系部", "申报时间"
				, "项目来源"};// 表头数组
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "labConstructAppCode", "projectName", "appUser", "academyName"
				, "appDate", "projectSource"};
		// 输出excel；
		baseApplicationService.exportExcel(list1, title, hearders, fields,
				request, response);

	}
	
	/****************************************************************************
	 * 功能：根据启动报告ID查询验收申请
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<ProjectAcceptanceApplication> findProjectAcceptanceApplicationByProAppId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectAcceptanceApplication c where c.projectStartedReport.id =" +idKey;

		return projectAcceptanceApplicationDAO.executeQuery(sql,0,-1);
	}
	
     /****************************************************************************
	 * 功能：根据启动报告ID查询保存后的验收申请
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public ProjectAcceptanceApplication queryProjectAcceptanceApplicationByProAppId(int idKey) {
		String sql="select c from ProjectAcceptanceApplication c where c.projectStartedReport.id =" +idKey;
		List<ProjectAcceptanceApplication> projectAcceptanceApplication = projectAcceptanceApplicationDAO.executeQuery(sql,0,-1);
		if (projectAcceptanceApplication.size()>0)
		{
			return projectAcceptanceApplication.get(0);
		}
		return null;
	}
	
	/****************************************************************************
	 * 功能：查询专业名称
	 * 作者：李德
	 * 时间：2015-4-14
	 ****************************************************************************/
	@Override
	public Set<SchoolMajor> findAllSchoolMajor() {
		// TODO Auto-generated method stub
		return schoolMajorDAO.findAllSchoolMajors();
	}
	
	/****************************************************************************
	 * 功能：查询专业名称
	 * 作者：李德
	 * 时间：2015-4-14
	 ****************************************************************************/
	@Override
	public Set<SchoolCourse> findAllSchoolCourse() {
		// TODO Auto-generated method stub
		return schoolCourseDAO.findAllSchoolCourses();
	}
	
	/***********************************************************************************************
	 * 功能：根据验收申请id删除申请金额
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	@Override
	public void deleteProjectAcceptFeeListByAppId(Integer idKey) {
		String sql="select f from ProjectAcceptFeeList f where f.projectAcceptanceApplication.id="+idKey;
		List<ProjectAcceptFeeList> List=projectAcceptFeeListDAO.executeQuery(sql, 0,-1);
		for (ProjectAcceptFeeList p : List) {
			projectAcceptFeeListDAO.remove(p);
		}
		
	}
	/***********************************************************************************************
	 * 功能：根据验收申请id删除设备
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	@Override
	public void deleteProjectAcceptDeviceByAppId(Integer idKey) {
		String sql="select d from ProjectAcceptDevice d where d.projectAcceptanceApplication.id="+idKey;
		List<ProjectAcceptDevice> List=projectAcceptDeviceDAO.executeQuery(sql, 0,-1);
		for (ProjectAcceptDevice d : List) {
			projectAcceptDeviceDAO.remove(d);
		}
	}
	/***********************************************************************************************
	 * 功能：根据验收申请id删除实验室项目
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	@Override
	public void deleteProjectAcceptCompletionItemByAppId(Integer idKey) {
		String sql="select t from ProjectAcceptCompletionItem t where t.projectAcceptanceApplication.id="+idKey;
		List<ProjectAcceptCompletionItem> List=projectAcceptCompletionItemDAO.executeQuery(sql, 0,-1);
		for (ProjectAcceptCompletionItem t : List) {
			projectAcceptCompletionItemDAO.remove(t);
		}
	}
	
	/***********************************************************************************************
	 * 功能：上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	@Override
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator"); 
		 Map files = multipartRequest.getFileMap(); 
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"projectAcceptanceApp"+sep;
		 //返回到页面的文档信息
		 Set<CommonDocument> docSet=new HashSet<CommonDocument>();
		//存放文件文件夹名称
		for(; fileNames.hasNext();){
		  String filename = (String) fileNames.next(); 
		  CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename); 
		  byte[] bytes = file.getBytes(); 
		  if(bytes.length != 0) {
			  // 说明申请有附件
			  if(!flag) { 
				  File dirPath = new File(fileDir); 
				  if(!dirPath.exists()) { 
					  flag = dirPath.mkdirs();
		              } 
		      } 
			  String fileTrueName = file.getOriginalFilename(); 
			  //System.out.println("文件名称："+fileTrueName);
			  File uploadedFile = new File(fileDir + sep + fileTrueName); 
			  //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
			  try {
				FileCopyUtils.copy(bytes,uploadedFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  CommonDocument doc=saveDocument(fileTrueName);
			  docSet.add(doc);
		  } 
		}
		String str="";
		for (CommonDocument d : docSet) {
			str+="<tr>"+
			    	"<td>"+d.getDocumentName()+"<input type='hidden' name='docId' value='"+d.getId()+"'>"+"</td>"+
					"<td><a href='javascript:void(0)'  onclick='javascript:this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);' >删除</a></td>"+
					"</tr>";		
		}
		return str;
	}	
	/****************************************************************************
	 * 功能：保存实验室建设申请的文档
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	public CommonDocument saveDocument(String fileTrueName) {
		
		CommonDocument doc = new CommonDocument( );
		doc.setDocumentName(fileTrueName);
		String url="upload/projectAcceptanceApp/"+fileTrueName;
		doc.setDocumentUrl(url);
		
		return commonDocumentDAO.store(doc);
	}
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询验收申请附件
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<CommonDocument> findCommonDocumentByProAppId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from CommonDocument c where c.projectAcceptanceApplicationByProjectAcceptAppItem.id =" +idKey;
		

		return commonDocumentDAO.executeQuery(sql,0,-1);
	}
	
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除文档(暂不删除服务器上的文件)
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	@Override
	public void deleteProjectAcceptAppCommonDocumentByAppId(Integer idKey) {
		String sql="select d from CommonDocument d where d.projectAcceptanceApplicationByProjectAcceptAppItem.id="+idKey;
		List<CommonDocument> List=commonDocumentDAO.executeQuery(sql, 0,-1);
		for (CommonDocument doc : List) {
			commonDocumentDAO.remove(doc);
		}
	}

	/**
	 * Description 查询学期课程
	 * @return
	 * @author 陈乐为 2018-7-28
	 */
	@Override
	public List<SchoolCourse> findSchoolCoursesByTerm() {
		// TODO Auto-generated method stub
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		StringBuffer hql = new StringBuffer("select s from SchoolCourse s where s.schoolTerm.id = " + schoolTerm.getId());

		return schoolCourseDAO.executeQuery(hql.toString(), 0, -1);
	}
}
