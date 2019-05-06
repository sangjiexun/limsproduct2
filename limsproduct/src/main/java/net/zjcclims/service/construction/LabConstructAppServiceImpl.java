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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Spring service that handles CRUD requests for LabConstructApp entities
 * 
 */

@Service("LabConstructAppService")
@Transactional
public class LabConstructAppServiceImpl implements LabConstructAppService {

	/**
	 * DAO injected by Spring that manages CProjectPurpose entities
	 * 
	 */
	@Autowired
	private CProjectPurposeDAO cProjectPurposeDAO;

	/**
	 * DAO injected by Spring that manages CProjectSource entities
	 * 
	 */
	@Autowired
	private CProjectSourceDAO cProjectSourceDAO;

	/**
	 * DAO injected by Spring that manages LabConstructAppApproval entities
	 * 
	 */
	@Autowired
	private LabConstructAppApprovalDAO labConstructAppApprovalDAO;

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

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	ShareService shareService;
	
	@Autowired
	private BaseApplicationService baseApplicationService;
	@Autowired
	private LabConstructUserDAO labConstructUserDAO;
	@Autowired
	private SchoolDeviceDAO schoolDeviceDAO;
	@Autowired
	private CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private ProjectFeeListDAO projectFeeListDAO;
	@Autowired
	private ProjectCompletionItemDAO projectCompletionItemDAO;
	@Autowired
	private LabConstructUserService labConstructUserService;
	@Autowired
	private ProjectFeeListService projectFeeListService;
	@Autowired
	private ProjectCompletionItemService projectCompletionItemService;
	@Autowired
	private ProjectDeviceService projectDeviceService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;

	/**
	 * Instantiates a new LabConstructAppServiceImpl.
	 *
	 */
	public LabConstructAppServiceImpl() {
	}

	/**
	 * Load an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public Set<LabConstructApp> loadLabConstructApps() {
		return labConstructAppDAO.findAllLabConstructApps();
	}

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public void saveLabConstructApp(LabConstructApp labconstructapp) {
		LabConstructApp existingLabConstructApp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp.getId());

		if (existingLabConstructApp != null) {
			if (existingLabConstructApp != labconstructapp) {
				existingLabConstructApp.setId(labconstructapp.getId());
				existingLabConstructApp.setLabConstructAppCode(labconstructapp.getLabConstructAppCode());
				existingLabConstructApp.setProjectName(labconstructapp.getProjectName());
				existingLabConstructApp.setPartyId(labconstructapp.getPartyId());
				existingLabConstructApp.setAppDate(labconstructapp.getAppDate());
				existingLabConstructApp.setParticipant(labconstructapp.getParticipant());
				existingLabConstructApp.setPrimaryObjective(labconstructapp.getPrimaryObjective());
				existingLabConstructApp.setSpecialInnovation(labconstructapp.getSpecialInnovation());
				existingLabConstructApp.setProjectBasis(labconstructapp.getProjectBasis());
				existingLabConstructApp.setConstructBasis(labconstructapp.getConstructBasis());
				existingLabConstructApp.setExpectedResult(labconstructapp.getExpectedResult());
				existingLabConstructApp.setAppropriationBudget(labconstructapp.getAppropriationBudget());
				existingLabConstructApp.setEquipmentDetail(labconstructapp.getEquipmentDetail());
				existingLabConstructApp.setOpenLabItem(labconstructapp.getOpenLabItem());
				existingLabConstructApp.setOtherAppendix(labconstructapp.getOtherAppendix());
				existingLabConstructApp.setApprovalAppendix(labconstructapp.getApprovalAppendix());
				existingLabConstructApp.setPlanSchedule(labconstructapp.getPlanSchedule());
			}
			labconstructapp = labConstructAppDAO.store(existingLabConstructApp);
		} else {
			labconstructapp = labConstructAppDAO.store(labconstructapp);
		}
		labConstructAppDAO.flush();
	}

	/**
	 * Return all LabConstructApp entity
	 * 
	 */
	@Transactional
	public List<LabConstructApp> findAllLabConstructApps(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<LabConstructApp>(labConstructAppDAO.findAllLabConstructApps(startResult, maxRows));
	}

	/**
	 * Delete an existing CProjectSource entity
	 * 
	 */
	@Transactional
	public LabConstructApp deleteLabConstructAppCProjectSource(Integer labconstructapp_id, Integer related_cprojectsource_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);
		CProjectSource related_cprojectsource = cProjectSourceDAO.findCProjectSourceByPrimaryKey(related_cprojectsource_id, -1, -1);

		labconstructapp.setCProjectSource(null);
		related_cprojectsource.getLabConstructApps().remove(labconstructapp);
		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		related_cprojectsource = cProjectSourceDAO.store(related_cprojectsource);
		cProjectSourceDAO.flush();

		cProjectSourceDAO.remove(related_cprojectsource);
		cProjectSourceDAO.flush();

		return labconstructapp;
	}

	/**
	 * Delete an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public LabConstructApp deleteLabConstructAppCProjectPurpose(Integer labconstructapp_id, Integer related_cprojectpurpose_id) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);
		CProjectPurpose related_cprojectpurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(related_cprojectpurpose_id, -1, -1);

		labconstructapp.setCProjectPurpose(null);
		related_cprojectpurpose.getLabConstructApps().remove(labconstructapp);
		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		related_cprojectpurpose = cProjectPurposeDAO.store(related_cprojectpurpose);
		cProjectPurposeDAO.flush();

		cProjectPurposeDAO.remove(related_cprojectpurpose);
		cProjectPurposeDAO.flush();

		return labconstructapp;
	}

	/**
	 * Save an existing CProjectSource entity
	 * 
	 */
	@Transactional
	public LabConstructApp saveLabConstructAppCProjectSource(Integer id, CProjectSource related_cprojectsource) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(id, -1, -1);
		CProjectSource existingCProjectSource = cProjectSourceDAO.findCProjectSourceByPrimaryKey(related_cprojectsource.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCProjectSource != null) {
			existingCProjectSource.setId(related_cprojectsource.getId());
			existingCProjectSource.setName(related_cprojectsource.getName());
			related_cprojectsource = existingCProjectSource;
		} else {
			related_cprojectsource = cProjectSourceDAO.store(related_cprojectsource);
			cProjectSourceDAO.flush();
		}

		labconstructapp.setCProjectSource(related_cprojectsource);
		related_cprojectsource.getLabConstructApps().add(labconstructapp);
		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		related_cprojectsource = cProjectSourceDAO.store(related_cprojectsource);
		cProjectSourceDAO.flush();

		return labconstructapp;
	}

	/**
	 * Save an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public LabConstructApp saveLabConstructAppProjectStartedReports(Integer id, ProjectStartedReport related_projectstartedreports) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(id, -1, -1);
		ProjectStartedReport existingprojectStartedReports = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreports.getId());

		// copy into the existing record to preserve existing relationships
		if (existingprojectStartedReports != null) {
			existingprojectStartedReports.setId(related_projectstartedreports.getId());
			//existingprojectStartedReports.setProjectSourceId(related_projectstartedreports.getProjectSourceId());
			existingprojectStartedReports.setLabAddress(related_projectstartedreports.getLabAddress());
			existingprojectStartedReports.setLabArea(related_projectstartedreports.getLabArea());
			//existingprojectStartedReports.setFeeApproval(related_projectstartedreports.getFeeApproval());
			existingprojectStartedReports.setFeeCode(related_projectstartedreports.getFeeCode());
			//existingprojectStartedReports.setAppliantId(related_projectstartedreports.getAppliantId());
			existingprojectStartedReports.setStartDate(related_projectstartedreports.getStartDate());
			//existingprojectStartedReports.setOpenLabItem(related_projectstartedreports.getOpenLabItem());
			existingprojectStartedReports.setEquipmentList(related_projectstartedreports.getEquipmentList());
			existingprojectStartedReports.setFeeApprovalDetail(related_projectstartedreports.getFeeApprovalDetail());
			related_projectstartedreports = existingprojectStartedReports;
		} else {
			related_projectstartedreports = projectStartedReportDAO.store(related_projectstartedreports);
			projectStartedReportDAO.flush();
		}

		related_projectstartedreports.setLabConstructApp(labconstructapp);
		labconstructapp.getProjectStartedReports().add(related_projectstartedreports);
		related_projectstartedreports = projectStartedReportDAO.store(related_projectstartedreports);
		projectStartedReportDAO.flush();

		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		return labconstructapp;
	}

	/**
	 */
/*	@Transactional
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer id) {
		return labConstructAppDAO.findLabConstructAppByPrimaryKey(id);
	}*/

	/**
	 * Delete an existing User entity
	 * 
	 */
	@Transactional
	public LabConstructApp deleteLabConstructAppUser(Integer labconstructapp_id, String related_user_username) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);
		User related_user = userDAO.findUserByPrimaryKey(related_user_username, -1, -1);

		labconstructapp.setUser(null);
		related_user.getLabConstructApps().remove(labconstructapp);
		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		userDAO.remove(related_user);
		userDAO.flush();

		return labconstructapp;
	}

	/**
	 * Save an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public LabConstructApp saveLabConstructAppProjectDevices(Integer id, ProjectDevice related_projectdevices) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(id, -1, -1);
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

		related_projectdevices.setLabConstructApp(labconstructapp);
		labconstructapp.getProjectDevices().add(related_projectdevices);
		related_projectdevices = projectDeviceDAO.store(related_projectdevices);
		projectDeviceDAO.flush();

		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		return labconstructapp;
	}

	/**
	 * Return a count of all LabConstructApp entity
	 * 
	 */
	@Transactional
	public Integer countLabConstructApps() {
		return ((Long) labConstructAppDAO.createQuerySingleResult("select count(o) from LabConstructApp o").getSingleResult()).intValue();
	}

	/**
	 * Delete an existing ProjectStartedReport entity
	 * 
	 */
	@Transactional
	public LabConstructApp deleteLabConstructAppProjectStartedReports(Integer labconstructapp_id, Integer related_projectstartedreports_id) {
		ProjectStartedReport related_projectstartedreports = projectStartedReportDAO.findProjectStartedReportByPrimaryKey(related_projectstartedreports_id, -1, -1);

		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		related_projectstartedreports.setLabConstructApp(null);
		labconstructapp.getProjectStartedReports().remove(related_projectstartedreports);

		projectStartedReportDAO.remove(related_projectstartedreports);
		projectStartedReportDAO.flush();

		return labconstructapp;
	}

	/**
	 * Delete an existing LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public LabConstructApp deleteLabConstructAppLabConstructAppApprovals(Integer labconstructapp_id, Integer related_labconstructappapprovals_id) {
		LabConstructAppApproval related_labconstructappapprovals = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(related_labconstructappapprovals_id, -1, -1);

		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		related_labconstructappapprovals.setLabConstructApp(null);
		labconstructapp.getLabConstructAppApprovals().remove(related_labconstructappapprovals);

		labConstructAppApprovalDAO.remove(related_labconstructappapprovals);
		labConstructAppApprovalDAO.flush();

		return labconstructapp;
	}

	/**
	 * Delete an existing ProjectDevice entity
	 * 
	 */
	@Transactional
	public LabConstructApp deleteLabConstructAppProjectDevices(Integer labconstructapp_id, Integer related_projectdevices_id) {
		ProjectDevice related_projectdevices = projectDeviceDAO.findProjectDeviceByPrimaryKey(related_projectdevices_id, -1, -1);

		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(labconstructapp_id, -1, -1);

		related_projectdevices.setLabConstructApp(null);
		labconstructapp.getProjectDevices().remove(related_projectdevices);

		projectDeviceDAO.remove(related_projectdevices);
		projectDeviceDAO.flush();

		return labconstructapp;
	}

	/**
	 * Save an existing LabConstructAppApproval entity
	 * 
	 */
	@Transactional
	public LabConstructApp saveLabConstructAppLabConstructAppApprovals(Integer id, LabConstructAppApproval related_labconstructappapprovals) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(id, -1, -1);
		LabConstructAppApproval existinglabConstructAppApprovals = labConstructAppApprovalDAO.findLabConstructAppApprovalByPrimaryKey(related_labconstructappapprovals.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabConstructAppApprovals != null) {
			existinglabConstructAppApprovals.setId(related_labconstructappapprovals.getId());
			existinglabConstructAppApprovals.setResult(related_labconstructappapprovals.getResult());
			existinglabConstructAppApprovals.setCreatedate(related_labconstructappapprovals.getCreatedate());
			existinglabConstructAppApprovals.setFlag(related_labconstructappapprovals.getFlag());
			existinglabConstructAppApprovals.setLevel(related_labconstructappapprovals.getLevel());
			related_labconstructappapprovals = existinglabConstructAppApprovals;
		} else {
			related_labconstructappapprovals = labConstructAppApprovalDAO.store(related_labconstructappapprovals);
			labConstructAppApprovalDAO.flush();
		}

		related_labconstructappapprovals.setLabConstructApp(labconstructapp);
		labconstructapp.getLabConstructAppApprovals().add(related_labconstructappapprovals);
		related_labconstructappapprovals = labConstructAppApprovalDAO.store(related_labconstructappapprovals);
		labConstructAppApprovalDAO.flush();

		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		return labconstructapp;
	}

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	@Transactional
	public void deleteLabConstructApp(LabConstructApp labconstructapp) {
		labConstructAppDAO.remove(labconstructapp);
		labConstructAppDAO.flush();
	}

	/**
	 * Save an existing User entity
	 * 
	 */
	@Transactional
	public LabConstructApp saveLabConstructAppUser(Integer id, User related_user) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(id, -1, -1);
		User existinguser = userDAO.findUserByPrimaryKey(related_user.getUsername());

		// copy into the existing record to preserve existing relationships
		if (existinguser != null) {
			existinguser.setUsername(related_user.getUsername());
			existinguser.setUsername(related_user.getUsername());
			existinguser.setCardno(related_user.getCardno());
			existinguser.setCname(related_user.getCname());
			existinguser.setPassword(related_user.getPassword());
			existinguser.setUserSexy(related_user.getUserSexy());
			existinguser.setUserStatus(related_user.getUserStatus());
			existinguser.setTeacherNumber(related_user.getTeacherNumber());
			//existinguser.setAcademyNumber(related_user.getAcademyNumber());
			//existinguser.setMajorNumber(related_user.getMajorNumber());
			existinguser.setUserRole(related_user.getUserRole());
			//existinguser.setClassesNumber(related_user.getClassesNumber());
			existinguser.setLastLogin(related_user.getLastLogin());
			existinguser.setCreatedAt(related_user.getCreatedAt());
			existinguser.setUpdatedAt(related_user.getUpdatedAt());
			existinguser.setTelephone(related_user.getTelephone());
			existinguser.setEmail(related_user.getEmail());
			existinguser.setEnabled(related_user.getEnabled());
			//existinguser.setMajorDirection(related_user.getMajorDirection());
			existinguser.setEnrollmentStatus(related_user.getEnrollmentStatus());
			existinguser.setIfEnrollment(related_user.getIfEnrollment());
			//existinguser.setUserType(related_user.getUserType());
			existinguser.setAttendanceTime(related_user.getAttendanceTime());
			existinguser.setGrade(related_user.getGrade());
			related_user = existinguser;
		} else {
			related_user = userDAO.store(related_user);
			userDAO.flush();
		}

		labconstructapp.setUser(related_user);
		related_user.getLabConstructApps().add(labconstructapp);
		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		return labconstructapp;
	}

	/**
	 * Save an existing CProjectPurpose entity
	 * 
	 */
	@Transactional
	public LabConstructApp saveLabConstructAppCProjectPurpose(Integer id, CProjectPurpose related_cprojectpurpose) {
		LabConstructApp labconstructapp = labConstructAppDAO.findLabConstructAppByPrimaryKey(id, -1, -1);
		CProjectPurpose existingCProjectPurpose = cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(related_cprojectpurpose.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCProjectPurpose != null) {
			existingCProjectPurpose.setId(related_cprojectpurpose.getId());
			existingCProjectPurpose.setName(related_cprojectpurpose.getName());
			related_cprojectpurpose = existingCProjectPurpose;
		} else {
			related_cprojectpurpose = cProjectPurposeDAO.store(related_cprojectpurpose);
			cProjectPurposeDAO.flush();
		}

		labconstructapp.setCProjectPurpose(related_cprojectpurpose);
		related_cprojectpurpose.getLabConstructApps().add(labconstructapp);
		labconstructapp = labConstructAppDAO.store(labconstructapp);
		labConstructAppDAO.flush();

		related_cprojectpurpose = cProjectPurposeDAO.store(related_cprojectpurpose);
		cProjectPurposeDAO.flush();

		return labconstructapp;
	}
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public List<LabConstructApp> findAllLabConstructAppByLabConstructApp(LabConstructApp labConstructApp) {
		// TODO Auto-generated method stub
		String sql="select c from LabConstructApp c where 1=1";
		if(labConstructApp.getProjectName()!=null&&!labConstructApp.getProjectName().equals("")){
			   sql+=" and c.projectName like '%"+labConstructApp.getProjectName()+"%'";
			}
		System.out.println(sql);
/*		if(labCenter.getCenterName()!=null&&!labCenter.getCenterName().equals("")){
			sql+=" and c.centerName like '%"+labCenter.getCenterName()+"%'";
		}*/
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
		return labConstructAppDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public List<LabConstructApp> findAllLabConstructAppByLabConstructApp(LabConstructApp labConstructApp,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from LabConstructApp c where 1=1";
		if(labConstructApp.getProjectName()!=null&&!labConstructApp.getProjectName().equals("")){
		   sql+=" and c.projectName like '%"+labConstructApp.getProjectName()+"%'";
		}
/*		if(labCenter.getCenterName()!=null&&!labCenter.getCenterName().equals("")){
			sql+=" and c.centerName like '%"+labCenter.getCenterName()+"%'";
		}*/
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
		return labConstructAppDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public Set<LabConstructApp> findAllLabConstructApp() {
		// TODO Auto-generated method stub
		return labConstructAppDAO.findAllLabConstructApps();
	}
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public LabConstructApp findLabConstructAppByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return labConstructAppDAO.findLabConstructAppByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-03-19
	 ****************************************************************************/
	@Override
	public LabConstructApp save(LabConstructApp labConstructApp) {
		// TODO Auto-generated method stub
		return labConstructAppDAO.store(labConstructApp);
	}	
	
	/***************************************************************************************
	 * 功能：生成最新的申报编号值
	 * 作者：李德
	 * 时间：2015/03/23
	 **************************************************************************************/
	@Transactional
	public String getLabConstructAppCode() {
		// 获取当前年月字符串：yyyymmdd
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String syyyymmdd = dateFormat.format(now);
		// 返回：当前年自动生成的资产编号
		String labConstructApp = ((String) labConstructAppDAO
				.createQuerySingleResult(
						"select max(labConstructAppCode)  from LabConstructApp o where 1=1")
				.getSingleResult());
		if (labConstructApp == null) {

			return syyyymmdd + "0001";
		} else {
			String sub =labConstructApp.substring(0,4);
			String yyyy = syyyymmdd.substring(0,4);

			//目前记录中最大编号是否是当年
			if (Integer.valueOf(sub) < Integer.valueOf(yyyy)) {
				
				return syyyymmdd + "0001";
			}else {
				
				return Long.toString((Long.parseLong(labConstructApp) + 1));
			}
		}

	}
	
	/***********************************************************************************************
	 * 功能：实验室建设项目-导出
	 * 作者：李德
	 * 时间：2015-03-26
	 ***********************************************************************************************/
	public void exportExcelLabConstructApp(List<LabConstructApp> labConstructApps,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		// 循环alls；
		for (LabConstructApp labConstructApp : labConstructApps) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			
			// 申请编号；
			map.put("labConstructAppCode", labConstructApp.getLabConstructAppCode());
			// 项目名称；
			map.put("projectName", labConstructApp.getProjectName());
			// 申请人；
			map.put("appUser", labConstructApp.getUser().getCname());
			// 所在系部；
			map.put("academyName", labConstructApp.getUser().getSchoolAcademy().getAcademyName());
			// 申报时间；
			if(labConstructApp.getAppDate()!=null){
				map.put("appDate", labConstructApp.getAppDate().getTime());
			}else{
				map.put("appDate", labConstructApp.getAppDate());
			}
			
			// 用途；
			if(labConstructApp.getPurposeName()!=null){
	    	    String[] arrayPurposeName=labConstructApp.getPurposeName().split(",");
	    	    String purposeNames="";
	            for(String strAuth:arrayPurposeName)
	            {		
	    	      int idKey = Integer.valueOf(strAuth);
	              CProjectPurpose cProjectPurpose=cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(idKey);
	              purposeNames += cProjectPurpose.getName()+" ";
	             }
	            map.put("purposeNames",purposeNames);
			}else{
				map.put("purposeNames","");
			}
		
			// 项目来源；
			map.put("projectSource", labConstructApp.getCProjectSource().getName());			

			;

			list1.add(map);
		}
		// 给表设置名称；
		String title = "实验建设项目申请表  " + 1 + "-" + this.countLabConstructApps();
		// 给表设置表名；
		String[] hearders = new String[] { "申请编号", "项目名称", "申请人", "所在系部", "申报时间", "用途"
				, "项目来源"};// 表头数组
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "labConstructAppCode", "projectName", "appUser", "academyName"
				, "appDate", "purposeNames", "projectSource"};
		// 输出excel；
		baseApplicationService.exportExcel(list1, title, hearders, fields,
				request, response);

	}
	/***********************************************************************************************
	 * 功能：根据用户对象查询满足条件的user数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public int findUserByCname(User user, String academyNumber) {
		String sql="select count(*) from User u where 1=1";
		
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and u.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		if(user!=null){
			if(user.getCname()!=null&&!user.getCname().equals("")){
				sql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			
		}
		
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***********************************************************************************************
	 * 功能：根据用户对象查询user并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<User> findUserByCname(User user, String academyNumber,
			Integer page, int pageSize) {
		String sql="select u from User u where 1=1";
		
		if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and u.schoolAcademy.academyNumber like '%"+academyNumber+"%'";
		}
		if(user!=null){
			if(user.getCname()!=null&&!user.getCname().equals("")){
				sql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			
		}
		
		return userDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/***********************************************************************************************
	 * 功能：保存实验建设参与人员
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public LabConstructUser save(LabConstructUser conUser) {
		
		return labConstructUserDAO.store(conUser);
	}
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public int findDeviceBydeviceName(String deviceName) {
		String sql="select count(*) from SchoolDevice d where 1=1";
		
		if(deviceName!=null&&!deviceName.equals("")){
			sql+=" and d.deviceName like '%"+deviceName+"%'";
		}
		
		return ((Long) schoolDeviceDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/***********************************************************************************************
	 * 功能：根据设备名称查询设备并分页
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<SchoolDevice> findDeviceBydeviceName(String deviceName,
			Integer page, int pageSize) {
		String sql="select d from SchoolDevice d where 1=1";
		
		if(deviceName!=null&&!deviceName.equals("")){
			sql+=" and d.deviceName like '%"+deviceName+"%'";
		}
		
		return schoolDeviceDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/***********************************************************************************************
	 * 功能：上传实验室建设申请附件，并返回文档字符串信息
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator"); 
		 Map files = multipartRequest.getFileMap(); 
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"labconstruct"+sep;
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
	 * 作者：李小龙
	 * 时间：2014-07-28
	 ****************************************************************************/
	public CommonDocument saveDocument(String fileTrueName) {
		
		CommonDocument doc = new CommonDocument( );
		doc.setDocumentName(fileTrueName);
		String url="upload/labconstruct/"+fileTrueName;
		doc.setDocumentUrl(url);
		
		return commonDocumentDAO.store(doc);
	}
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除申请对应的参与人员
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public void deleteLabConstructAppUserByAppId(Integer idKey) {
		String sql="select u from LabConstructUser u where u.labConstructApp.id="+idKey;
		List<LabConstructUser> users=labConstructUserDAO.executeQuery(sql, 0,-1);
		for (LabConstructUser u : users) {
			labConstructUserDAO.remove(u);
		}
	}
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除申请金额
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public void deleteProjectFeeListByAppId(Integer idKey) {
		String sql="select f from ProjectFeeList f where f.labConstructApp.id="+idKey;
		List<ProjectFeeList> List=projectFeeListDAO.executeQuery(sql, 0,-1);
		for (ProjectFeeList p : List) {
			projectFeeListDAO.remove(p);
		}
		
	}
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除设备
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public void deleteProjectDeviceByAppId(Integer idKey) {
		String sql="select d from ProjectDevice d where d.labConstructApp.id="+idKey;
		List<ProjectDevice> List=projectDeviceDAO.executeQuery(sql, 0,-1);
		for (ProjectDevice d : List) {
			projectDeviceDAO.remove(d);
		}
	}
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除实验室项目
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public void deleteProjectCompletionItemByAppId(Integer idKey) {
		String sql="select t from ProjectCompletionItem t where t.labConstructApp.id="+idKey;
		List<ProjectCompletionItem> List=projectCompletionItemDAO.executeQuery(sql, 0,-1);
		for (ProjectCompletionItem t : List) {
			projectCompletionItemDAO.remove(t);
		}
	}
	/***********************************************************************************************
	 * 功能：根据实验室建设申请id删除文档(暂不删除服务器上的文件)
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public void deleteCommonDocumentByAppId(Integer idKey) {
		String sql="select d from CommonDocument d where d.labConstructApp.id="+idKey;
		List<CommonDocument> List=commonDocumentDAO.executeQuery(sql, 0,-1);
		for (CommonDocument doc : List) {
			commonDocumentDAO.remove(doc);
		}
	}
	/***********************************************************************************************
	 * 功能：g根据实验室申请id查询实验室申请人员
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<LabConstructUser> findLabConstructUserByAppId(Integer AppId) {
		String sql="select u from LabConstructUser u where u.labConstructApp.id="+AppId;
		return labConstructUserDAO.executeQuery(sql, 0,-1);
	}
	/***********************************************************************************************
	 * 功能：根据实验室申请id查询经费
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<ProjectFeeList> findProjectFeeListByAppId(Integer AppId) {
		String sql="select f from ProjectFeeList f where f.labConstructApp.id="+AppId;
		return projectFeeListDAO.executeQuery(sql, 0,-1);
	}
	/****************************************************************************
	 * 功能：查询专业名称
	 * 作者：李德
	 * 时间：2015-4-20
	 ****************************************************************************/
	@Override
	public Set<CProjectPurpose> findAllProjectPurpose() {
		// TODO Auto-generated method stub
		return cProjectPurposeDAO.findAllCProjectPurposes();
	}
	
	/****************************************************************************
	 * 功能：实验室建设申请Map
	 * 作者：李德
	 * 时间：2015-6-01
	 ****************************************************************************/
	@Override
	public void getData(Map<String, Object> dataMap,int idKey){
		//id对应的实验室建设申请项目
		LabConstructApp app=labConstructAppDAO.findLabConstructAppByPrimaryKey(idKey);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//转换日期格式
      
    	Map map = new HashMap();
    	//项目名称
    	map.put("project_name",app.getProjectName());
    	//申请人
    	map.put("user_name",app.getUser().getCname());
    	//系部
    	map.put("party_name",app.getUser().getSchoolAcademy().getAcademyName());
    	//联系电话
    	if(app.getUser().getTelephone()!=null){
    		map.put("telefone",app.getUser().getTelephone());
    	}else{
    		map.put("telefone","");
    	}
    	//申请日期
    	map.put("appDate",app.getAppDate().getTime());
    	// 用途；
		if(app.getPurposeName()!=null){
    	    String[] arrayPurposeName=app.getPurposeName().split(",");
    	    String purposeNames="";
            for(String strAuth:arrayPurposeName)
            {		
    	      int id = Integer.valueOf(strAuth);
              CProjectPurpose cProjectPurpose=cProjectPurposeDAO.findCProjectPurposeByPrimaryKey(id);
              purposeNames=purposeNames+cProjectPurpose.getName()+" ";
             }
            map.put("purposeNames",purposeNames);
		}else{
			map.put("purposeNames","");
		}
    	//面向专业
    	if(app.getMajorName()!=null){
    	    String[] arraySchoolMajor=app.getMajorName().split(",");
    	    String schoolMajors="";
            for(String strAuth:arraySchoolMajor)
            {		
    	      SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
    	      schoolMajors=schoolMajors+schoolMajor.getMajorName()+" ";
             }
            map.put("schoolMajors",schoolMajors);
    	}else{
    		map.put("schoolMajors","");
    	}
    	//面向专业数
    	map.put("appDate",app.getAppDate().getTime());
    	//面向课程数
    	//申请人姓名
    	//申请人性别
    	map.put("sex",app.getUser().getUserSexy());
    	//申请人出生年月
    	map.put("bornDate","");
    	//申请人职务
    	map.put("title","");
    	//申请人职称
    	map.put("post","");
    	//申请人手机
    	map.put("mobileNo","");
    	//申请人电话
    	//申请人e-mail
    	map.put("eMail",app.getUser().getEmail());

        System.out.println(map);
        //人员
        List<LabConstructUser> labConstructUser = labConstructUserService.findLabConstructUserByAppKey(app.getId());
        List<Map<String,Object>> labConstructUserList = new ArrayList<Map<String,Object>>();
        for(LabConstructUser u:labConstructUser){
        	Map<String,Object> userMap = new HashMap<String,Object>();
        	userMap.put("userName", "");
        	labConstructUserList.add(userMap);
        	
		}
        map.put("labConstructUserList", labConstructUserList);
        //费用
        List<ProjectFeeList> projectFee = projectFeeListService.findProjectFeeListByAppKey(app.getId());
        List<Map<String,Object>> projectFeeList = new ArrayList<Map<String,Object>>();
        for(ProjectFeeList fee:projectFee){
        	Map<String,Object> feeMap = new HashMap<String,Object>();
        	feeMap.put("userName", "");
        	projectFeeList.add(feeMap);
        	
		}
        map.put("projectFeeList", projectFeeList);
        //设备
        List<ProjectDevice> projectDevice = projectDeviceService.findProjectDeviceByAppKey(app.getId());
        List<Map<String,Object>> projectDeviceList = new ArrayList<Map<String,Object>>();
        for(ProjectDevice device:projectDevice){
        	Map<String,Object> deviceMap = new HashMap<String,Object>();
        	deviceMap.put("userName", "");
        	projectDeviceList.add(deviceMap);
        	
		}
        map.put("projectDeviceList", projectDeviceList);
        //项目
        List<ProjectCompletionItem> projectCompletionItem = projectCompletionItemService.findProjectCompletionItemByAppKey(app.getId());
        List<Map<String,Object>> projectCompletionItemList = new ArrayList<Map<String,Object>>();
        for(ProjectCompletionItem item:projectCompletionItem){
        	Map<String,Object> itemMap = new HashMap<String,Object>();
        	itemMap.put("userName", "");
        	projectCompletionItemList.add(itemMap);
        	
		}
        map.put("projectCompletionItemList", projectCompletionItemList);
	}
	/****************************************************************************
	 * 功能：实验室建设申请Map
	 * 作者：李德
	 * 时间：2015-7-14
	 ****************************************************************************/	
	@Override
	public Map getUsersMap(Integer cid) {
		String id =null;
		/*if(shareService.getUser().getSchoolAcademy()!=null){
		id= " and u.schoolAcademy.academyNumber='"+labCenterDAO.findLabCenterById(cid).getSchoolAcademy().getAcademyNumber()+"'";
		}*/
		
		// 新建一个HashMap对象；
		Map userMap = new HashMap();
		// 循环users；
		for (User user : userDAO.executeQuery("select u from User u where 1=1 and u.enabled=true ")) {
			// 将user的Cname映射成id；
			userMap.put(user.getUsername(), user.getCname());
		}
		return userMap;
	}
	
}
