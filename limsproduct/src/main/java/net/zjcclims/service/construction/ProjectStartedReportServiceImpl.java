package net.zjcclims.service.construction;


import net.zjcclims.service.common.BaseApplicationService;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
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
 * Spring service that handles CRUD requests for ProjectStartedReport entities
 * 
 */

@Service("ProjectStartedReportService")
@Transactional
public class ProjectStartedReportServiceImpl implements
		ProjectStartedReportService {

	/**
	 * DAO injected by Spring that manages LabConstructApp entities
	 * 
	 */
	@Autowired
	private LabConstructAppDAO labConstructAppDAO;

	/**
	 * DAO injected by Spring that manages ProjectDevice entities
	 * 
	 */
	@Autowired
	private ProjectDeviceDAO projectDeviceDAO;

	/**
	 * DAO injected by Spring that manages ProjectStartedReport entities
	 * 
	 */
	@Autowired
	private ProjectStartedReportDAO projectStartedReportDAO;
	
	@Autowired
	private BaseApplicationService baseApplicationService;
	
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private ProjectStartFeeListDAO projectStartFeeListDAO;
	@Autowired
	private ProjectStartCompletionItemDAO projectStartCompletionItemDAO;
	@Autowired
	private ProjectStartDeviceDAO projectStartDeviceDAO;
	@Autowired
	private CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private SchoolDeviceDAO schoolDeviceDAO;
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;
	/**
	 * Instantiates a new ProjectStartedReportServiceImpl.
	 *
	 */
	public ProjectStartedReportServiceImpl() {
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectStartedReport deleteProjectStartedReportLabConstructApp(Integer projectstartedreport_id, Integer related_labconstructapp_id) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport_id, -1, -1);
		LabConstructApp related_labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp_id, -1, -1);

		projectstartedreport.setLabConstructApp(null);
		related_labconstructapp.getProjectStartedReports().remove(projectstartedreport);
		projectstartedreport = projectStartedReportDAO.store(projectstartedreport);
		projectStartedReportDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		labConstructAppDAO.remove(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectstartedreport;
	}

	/**
	 * Load an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public Set<ProjectStartedReport> loadProjectStartedReports() {
		return projectStartedReportDAO.findAllProjectStartedReports();
	}

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public ProjectStartedReport deleteProjectStartedReportProjectDevices(Integer projectstartedreport_id, Integer related_projectdevices_id) {
		ProjectDevice related_projectdevices = projectDeviceDAO.findProjectDeviceByPrimaryKey(related_projectdevices_id, -1, -1);

		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport_id, -1, -1);

		related_projectdevices.setProjectStartedReport(null);
		//projectstartedreport.getProjectDevices().remove(related_projectdevices);

		projectDeviceDAO.remove(related_projectdevices);
		projectDeviceDAO.flush();

		return projectstartedreport;
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public void deleteProjectStartedReport(ProjectStartedReport projectstartedreport) {
		projectStartedReportDAO.remove(projectstartedreport);
		projectStartedReportDAO.flush();
	}

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public ProjectStartedReport saveProjectStartedReportProjectDevices(Integer id, ProjectDevice related_projectdevices) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(id, -1, -1);
		ProjectDevice existingprojectDevices = projectDeviceDAO.findProjectDeviceByPrimaryKey(related_projectdevices.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectDevices != null) {
			existingprojectDevices.setId(related_projectdevices.getId());
			existingprojectDevices.setEquipmentName(related_projectdevices.getEquipmentName());
			existingprojectDevices.setFormat(related_projectdevices.getFormat());
			existingprojectDevices.setAmount(related_projectdevices.getAmount());
			existingprojectDevices.setUnitPrice(related_projectdevices.getUnitPrice());
			existingprojectDevices.setCollection(related_projectdevices.getCollection());
			existingprojectDevices.setPurchasePattern(related_projectdevices.getPurchasePattern());
			related_projectdevices = existingprojectDevices;
		} else {
			related_projectdevices = projectDeviceDAO.store(related_projectdevices);
			projectDeviceDAO.flush();
		}

		related_projectdevices.setProjectStartedReport(projectstartedreport);
		//projectstartedreport.getProjectDevices().add(related_projectdevices);
		related_projectdevices = projectDeviceDAO.store(related_projectdevices);
		projectDeviceDAO.flush();

		projectstartedreport = projectStartedReportDAO.store(projectstartedreport);
		projectStartedReportDAO.flush();

		return projectstartedreport;
	}

	/**
	 * Return a count of all ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public Integer countProjectStartedReports() {
		return ((Long) projectStartedReportDAO.createQuerySingleResult("select count(o) from ProjectStartedReport o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public void saveProjectStartedReport(ProjectStartedReport projectstartedreport) {
		ProjectStartedReport existingProjectStartedReport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(projectstartedreport.getId());

		if (existingProjectStartedReport != null) {
			if (existingProjectStartedReport != projectstartedreport) {
				existingProjectStartedReport.setId(projectstartedreport.getId());
				//existingProjectStartedReport.setProjectSourceId(projectstartedreport.getProjectSourceId());
				existingProjectStartedReport.setLabAddress(projectstartedreport.getLabAddress());
				existingProjectStartedReport.setLabArea(projectstartedreport.getLabArea());
				//existingProjectStartedReport.setFeeApproval(projectstartedreport.getFeeApproval());
				existingProjectStartedReport.setFeeCode(projectstartedreport.getFeeCode());
				//existingProjectStartedReport.setAppliantId(projectstartedreport.getAppliantId());
				existingProjectStartedReport.setStartDate(projectstartedreport.getStartDate());
				//existingProjectStartedReport.setOpenLabItem(projectstartedreport.getOpenLabItem());
				existingProjectStartedReport.setEquipmentList(projectstartedreport.getEquipmentList());
				existingProjectStartedReport.setFeeApprovalDetail(projectstartedreport.getFeeApprovalDetail());
			}
			projectstartedreport = projectStartedReportDAO.store(existingProjectStartedReport);
		} else {
			projectstartedreport = projectStartedReportDAO.store(projectstartedreport);
		}
		projectStartedReportDAO.flush();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public ProjectStartedReport saveProjectStartedReportLabConstructApp(Integer id, LabConstructApp related_labconstructapp) {
		ProjectStartedReport projectstartedreport = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(id, -1, -1);
		LabConstructApp existinglabConstructApp = labConstructAppDAO.findLabConstructAppByPrimaryKey(related_labconstructapp.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabConstructApp != null) {
			existinglabConstructApp.setId(related_labconstructapp.getId());
			existinglabConstructApp.setLabConstructAppCode(related_labconstructapp.getLabConstructAppCode());
			existinglabConstructApp.setProjectName(related_labconstructapp.getProjectName());
			existinglabConstructApp.setPartyId(related_labconstructapp.getPartyId());
			existinglabConstructApp.setAppDate(related_labconstructapp.getAppDate());
			existinglabConstructApp.setParticipant(related_labconstructapp.getParticipant());
			existinglabConstructApp.setPrimaryObjective(related_labconstructapp.getPrimaryObjective());
			existinglabConstructApp.setSpecialInnovation(related_labconstructapp.getSpecialInnovation());
			existinglabConstructApp.setProjectBasis(related_labconstructapp.getProjectBasis());
			existinglabConstructApp.setConstructBasis(related_labconstructapp.getConstructBasis());
			existinglabConstructApp.setExpectedResult(related_labconstructapp.getExpectedResult());
			existinglabConstructApp.setAppropriationBudget(related_labconstructapp.getAppropriationBudget());
			existinglabConstructApp.setEquipmentDetail(related_labconstructapp.getEquipmentDetail());
			existinglabConstructApp.setOpenLabItem(related_labconstructapp.getOpenLabItem());
			existinglabConstructApp.setOtherAppendix(related_labconstructapp.getOtherAppendix());
			existinglabConstructApp.setApprovalAppendix(related_labconstructapp.getApprovalAppendix());
			related_labconstructapp = existinglabConstructApp;
		} else {
			related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
			labConstructAppDAO.flush();
		}

		projectstartedreport.setLabConstructApp(related_labconstructapp);
		related_labconstructapp.getProjectStartedReports().add(projectstartedreport);
		projectstartedreport = projectStartedReportDAO.store(projectstartedreport);
		projectStartedReportDAO.flush();

		related_labconstructapp = labConstructAppDAO.store(related_labconstructapp);
		labConstructAppDAO.flush();

		return projectstartedreport;
	}

	/**
	 * Return all ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public List<ProjectStartedReport> findAllProjectStartedReports(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<ProjectStartedReport>(projectStartedReportDAO.findAllProjectStartedReports(startResult, maxRows));
	}

	/**
	 */
/*	@Transactional
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id) {
		return projectStartedReportDAO.findProjectStartedReportByPrimaryKey(id);
	}*/
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public List<ProjectStartedReport> findAllProjectStartedReportByLabConstruct(ProjectStartedReport projectStartedReport) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectStartedReport c where 1=1";
		
		if(projectStartedReport.getProjectName()!=null&&!projectStartedReport.getProjectName().equals("")){
			sql+=" and c.projectName like '%"+projectStartedReport.getProjectName()+"%'";
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
		return projectStartedReportDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：根据申请报告ID查询启动报告
	 * 作者：李德
	 * 时间：2015-04-13
	 ****************************************************************************/
	@Override
	public List<ProjectStartedReport> findProjectStartedReportByLabConstructId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from ProjectStartedReport c where c.labConstructApp.id =" +idKey;
		

		return projectStartedReportDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public List<ProjectStartedReport> findAllProjectStartedReportByLabConstruct(ProjectStartedReport projectStartedReport,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		System.out.println(projectStartedReport.getProjectName());
		String sql="select c from ProjectStartedReport c where 1=1";
		if(projectStartedReport.getProjectName()!=null&&!projectStartedReport.getProjectName().equals("")){
			sql+=" and c.projectName like '%"+projectStartedReport.getProjectName()+"%'";
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
		return projectStartedReportDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public Set<ProjectStartedReport> findAllProjectStartedReport() {
		// TODO Auto-generated method stub
		return projectStartedReportDAO.findAllProjectStartedReports();
	}
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public ProjectStartedReport findProjectStartedReportByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return projectStartedReportDAO.findProjectStartedReportByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-20
	 ****************************************************************************/
	@Override
	public ProjectStartedReport save(ProjectStartedReport projectStartedReport) {
		// TODO Auto-generated method stub
		return projectStartedReportDAO.store(projectStartedReport);
	}	
	
	/***********************************************************************************************
	 * 功能：实验室建设项目启动报告-导出
	 * 作者：李德
	 * 时间：2015-03-27
	 ***********************************************************************************************/
	public void exportExcelProjectStartedReport(List<ProjectStartedReport> projectStartedReports,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		// 循环alls；
		for (ProjectStartedReport projectStartedReport : projectStartedReports) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			
			// 申请编号；
			map.put("labConstructAppCode", projectStartedReport.getLabConstructApp().getLabConstructAppCode());
			// 项目名称；
			map.put("projectName", projectStartedReport.getLabConstructApp().getProjectName());
			// 申请人；
			map.put("appUser", projectStartedReport.getLabConstructApp().getUser().getCname());
			// 所在系部；
			map.put("academyName", projectStartedReport.getLabConstructApp().getUser().getSchoolAcademy().getAcademyName());
			// 申报时间；
			if(projectStartedReport.getLabConstructApp().getAppDate()!=null){
				map.put("appDate", projectStartedReport.getLabConstructApp().getAppDate().getTime());
			}else{
				map.put("appDate", "");
			}
			// 用途；
/*			if(projectStartedReport.getLabConstructApp().getPurposeName()!=null){
	    	    String[] arrayPurposeName=projectStartedReport.getLabConstructApp().getPurposeName().split(",");
	    	    String purposeNames="";
	            for(String strAuth:arrayPurposeName)
	            {		
	    	      int idKey = Integer.valueOf(strAuth);
	              CProjectPurpose cProjectPurpose=cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(idKey);
	              purposeNames=purposeNames+" ";
	             }
	            map.put("purposeNames",purposeNames);
			}else{
				map.put("purposeNames","");
			}*/

			// 项目来源；
			map.put("projectSource", projectStartedReport.getLabConstructApp().getCProjectSource().getName());			

			;

			list1.add(map);
		}
		// 给表设置名称；
		String title = "实验建设项目申请表  " + 1 + "-" + this.countProjectStartedReports();
		// 给表设置表名；
		String[] hearders = new String[] { "申请编号", "项目名称", "申请人", "所在系部", "申报时间"
				, "项目来源"};// 表头数组
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "labConstructAppCode", "projectName", "appUser", "academyName"
				, "appDate",  "projectSource"};
		// 输出excel；
		baseApplicationService.exportExcel(list1, title, hearders, fields,
				request, response);

	}
	
	/****************************************************************************
	 * 功能：查询出所有的院系
	 * 作者：李德
	 * 时间：2015-04-15
	 ****************************************************************************/
	@Override
	public Set<SchoolAcademy> findAllSchoolAcademy() {
		// TODO Auto-generated method stub
		return schoolAcademyDAO.findAllSchoolAcademys();
	}
	
	/***********************************************************************************************
	 * 功能：根据启动报告id删除申请金额
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	@Override
	public void deleteProjectStartFeeListByReportId(Integer idKey) {
		String sql="select f from ProjectStartFeeList f where f.projectStartedReport.id="+idKey;
		List<ProjectStartFeeList> List=projectStartFeeListDAO.executeQuery(sql, 0,-1);
		for (ProjectStartFeeList p : List) {
			projectStartFeeListDAO.remove(p);
		}
		
	}
	/***********************************************************************************************
	 * 功能：根据启动报告id删除设备
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	@Override
	public void deleteProjectStartDeviceByReportId(Integer idKey) {
		String sql="select d from ProjectStartDevice d where d.projectStartedReport.id="+idKey;
		List<ProjectStartDevice> List=projectStartDeviceDAO.executeQuery(sql, 0,-1);
		for (ProjectStartDevice d : List) {
			projectStartDeviceDAO.remove(d);
		}
	}
	/***********************************************************************************************
	 * 功能：根据启动报告id删除实验室项目
	 * 作者：李德
	 * 时间：2015-4-15
	 ***********************************************************************************************/
	@Override
	public void deleteProjectStartCompletionItemByReportId(Integer idKey) {
		String sql="select t from ProjectStartCompletionItem t where t.projectStartedReport.id="+idKey;
		List<ProjectStartCompletionItem> List=projectStartCompletionItemDAO.executeQuery(sql, 0,-1);
		for (ProjectStartCompletionItem t : List) {
			projectStartCompletionItemDAO.remove(t);
		}
	}
		
    /****************************************************************************
     * 功能：根据实验室申请ID查询保存后的启动报告
     * 作者：李德
     * 时间：2015-04-15
	 ****************************************************************************/
	@Override
	public ProjectStartedReport queryProjectStartedReportByReportId(int idKey) {
		String sql="select c from ProjectStartedReport c where c.labConstructApp.id =" +idKey;
		List<ProjectStartedReport> projectStartedReport = projectStartedReportDAO.executeQuery(sql,0,-1);
		if (projectStartedReport.size()>0)
		{
			return projectStartedReport.get(0);
		}
		return null;
	}
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备数量
	 * 作者：李德
	 * 时间：2015-04-15
	 ***********************************************************************************************/
	@Override
	public int findStartDeviceBydeviceName(String deviceName) {
		String sql="select count(*) from SchoolDevice d where 1=1";
		
		if(deviceName!=null&&!deviceName.equals("")){
			sql+=" and d.deviceName like '%"+deviceName+"%'";
		}
		
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备并分页
	 * 作者：李德
	 * 时间：2015-04-15
	 ***********************************************************************************************/
	@Override
	public List<SchoolDevice> findStartDeviceBydeviceName(String deviceName,
			Integer page, int pageSize) {
		String sql="select d from SchoolDevice d where 1=1";
		
		if(deviceName!=null&&!deviceName.equals("")){
			sql+=" and d.deviceName like '%"+deviceName+"%'";
		}
		
		return schoolDeviceDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
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
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"projectStartReport"+sep;
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
		String url="upload/projectStartReport/"+fileTrueName;
		doc.setDocumentUrl(url);
		
		return commonDocumentDAO.store(doc);
	}
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询启动报告附件
	 * 作者：李德
	 * 时间：2015-04-14
	 ****************************************************************************/
	@Override
	public List<CommonDocument> findCommonDocumentByProAppId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from CommonDocument c where c.projectStartedReportByProjectStartReportApproval.id =" +idKey;
		

		return commonDocumentDAO.executeQuery(sql,0,-1);
	}
	
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除文档(暂不删除服务器上的文件)
	 * 作者：李德
	 * 时间：2015-04-14
	 ***********************************************************************************************/
	@Override
	public void deleteProjectStartReportCommonDocumentByAppId(Integer idKey) {
		String sql="select d from CommonDocument d where d.projectStartedReportByProjectStartReportApproval.id="+idKey;
		List<CommonDocument> List=commonDocumentDAO.executeQuery(sql, 0,-1);
		for (CommonDocument doc : List) {
			commonDocumentDAO.remove(doc);
		}
	}
}
