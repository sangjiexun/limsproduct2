package net.zjcclims.service.construction;

import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.BaseApplicationService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.operation.OperationService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * Spring service that handles CRUD requests for VirtualLabConstruction entities
 * 
 */

@Service("VirtualLabConstructionService")
@Transactional
public class VirtualLabConstructionServiceImpl implements
		VirtualLabConstructionService {

	/**
	 * DAO injected by Spring that manages LabRoomDevice entities
	 * 
	 */
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;

	/**
	 * DAO injected by Spring that manages LabRoomProject entities
	 * 
	 */
	@Autowired
	private LabRoomProjectDAO labRoomProjectDAO;

	/**
	 * DAO injected by Spring that manages SchoolAcademy entities
	 * 
	 */
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;

	/**
	 * DAO injected by Spring that manages SystemRoom entities
	 * 
	 */
	@Autowired
	private SystemRoomDAO systemRoomDAO;

	/**
	 * DAO injected by Spring that manages VirtualLabConstruction entities
	 * 
	 */
	@Autowired
	private VirtualLabConstructionDAO virtualLabConstructionDAO;
	
	@Autowired
	ShareService shareService;
	
	@Autowired
	private BaseApplicationService baseApplicationService;
	@Autowired
	LabRoomDAO labRoomDAO;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private CommonDocumentDAO commonDocumentDAO;
	@Autowired
	private OperationService operationService;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private OperationItemDAO operationItemDAO;

	/**
	 * Instantiates a new VirtualLabConstructionServiceImpl.
	 *
	 */
	public VirtualLabConstructionServiceImpl() {
	}

	/**
	 * Load an existing VirtualLabConstruction entity
	 * 
	 */
	@Transactional
	public Set<VirtualLabConstruction> loadVirtualLabConstructions() {
		return virtualLabConstructionDAO.findAllVirtualLabConstructions();
	}

	/**
	 * Save an existing LabRoomDevice entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction saveVirtualLabConstructionLabRoomDevice(Integer id, LabRoomDevice related_labroomdevice) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(id, -1, -1);
		LabRoomDevice existinglabRoomDevice = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(related_labroomdevice.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabRoomDevice != null) {
			existinglabRoomDevice.setId(related_labroomdevice.getId());
/*			existinglabRoomDevice.setLabRoomId(related_labroomdevice.getLabRoomId());
			existinglabRoomDevice.setSchoolDeviceId(related_labroomdevice.getSchoolDeviceId());
			existinglabRoomDevice.setAllowAppointment(related_labroomdevice.getAllowAppointment());
			existinglabRoomDevice.setAllowLending(related_labroomdevice.getAllowLending());
			existinglabRoomDevice.setAppointmentType(related_labroomdevice.getAppointmentType());
			existinglabRoomDevice.setAllowSecurityAccess(related_labroomdevice.getAllowSecurityAccess());
			existinglabRoomDevice.setSecurityAccessType(related_labroomdevice.getSecurityAccessType());
			existinglabRoomDevice.setIsAudit(related_labroomdevice.getIsAudit());
			existinglabRoomDevice.setTeacherAudit(related_labroomdevice.getTeacherAudit());
			existinglabRoomDevice.setManagerAudit(related_labroomdevice.getManagerAudit());
			existinglabRoomDevice.setManagerUser(related_labroomdevice.getManagerUser());
			existinglabRoomDevice.setDeviceStatus(related_labroomdevice.getDeviceStatus());
			existinglabRoomDevice.setDeviceType(related_labroomdevice.getDeviceType());
			existinglabRoomDevice.setIndicators(related_labroomdevice.getIndicators());
			existinglabRoomDevice.setDeviceCharge(related_labroomdevice.getDeviceCharge());*/
			existinglabRoomDevice.setPrice(related_labroomdevice.getPrice());
			existinglabRoomDevice.setFunction(related_labroomdevice.getFunction());
			existinglabRoomDevice.setFeatures(related_labroomdevice.getFeatures());
			existinglabRoomDevice.setApplications(related_labroomdevice.getApplications());
			related_labroomdevice = existinglabRoomDevice;
		} else {
			related_labroomdevice = labRoomDeviceDAO.store(related_labroomdevice);
			labRoomDeviceDAO.flush();
		}

		virtuallabconstruction.setLabRoomDevice(related_labroomdevice);
		//related_labroomdevice.getVirtualLabConstructions().add(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_labroomdevice = labRoomDeviceDAO.store(related_labroomdevice);
		labRoomDeviceDAO.flush();

		return virtuallabconstruction;
	}

	/**
	 * Save an existing VirtualLabConstruction entity
	 * 
	 */
	@Transactional
	public void saveVirtualLabConstruction(VirtualLabConstruction virtuallabconstruction) {
		VirtualLabConstruction existingVirtualLabConstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(virtuallabconstruction.getId());

		if (existingVirtualLabConstruction != null) {
			if (existingVirtualLabConstruction != virtuallabconstruction) {
				existingVirtualLabConstruction.setId(virtuallabconstruction.getId());
				existingVirtualLabConstruction.setProjectBaseName(virtuallabconstruction.getProjectBaseName());
				existingVirtualLabConstruction.setMajorName(virtuallabconstruction.getMajorName());
				existingVirtualLabConstruction.setSupportAcademyName(virtuallabconstruction.getSupportAcademyName());
				existingVirtualLabConstruction.setBuildingArea(virtuallabconstruction.getBuildingArea());
				existingVirtualLabConstruction.setSiteArea(virtuallabconstruction.getSiteArea());
				existingVirtualLabConstruction.setDeviceValue(virtuallabconstruction.getDeviceValue());
				existingVirtualLabConstruction.setAddDeviceValue(virtuallabconstruction.getAddDeviceValue());
				existingVirtualLabConstruction.setOwnDeviceValue(virtuallabconstruction.getOwnDeviceValue());
				existingVirtualLabConstruction.setDonateDeviceValue(virtuallabconstruction.getDonateDeviceValue());
				existingVirtualLabConstruction.setPreDonateDeviceValue(virtuallabconstruction.getPreDonateDeviceValue());
				existingVirtualLabConstruction.setDeviceAmount(virtuallabconstruction.getDeviceAmount());
				existingVirtualLabConstruction.setHugeDeviceAmount(virtuallabconstruction.getHugeDeviceAmount());
				existingVirtualLabConstruction.setTrainingProjectAmount(virtuallabconstruction.getTrainingProjectAmount());
				existingVirtualLabConstruction.setTrainingProjectName(virtuallabconstruction.getTrainingProjectName());
				existingVirtualLabConstruction.setUseFrequencySchool(virtuallabconstruction.getUseFrequencySchool());
				existingVirtualLabConstruction.setUseFrequencySociety(virtuallabconstruction.getUseFrequencySociety());
				existingVirtualLabConstruction.setMaterialFee(virtuallabconstruction.getMaterialFee());
				existingVirtualLabConstruction.setDeviceMaintainFee(virtuallabconstruction.getDeviceMaintainFee());
				existingVirtualLabConstruction.setManagePeopleAmount(virtuallabconstruction.getManagePeopleAmount());
				existingVirtualLabConstruction.setPartTimePeopleAmount(virtuallabconstruction.getPartTimePeopleAmount());
				existingVirtualLabConstruction.setLabRoomDeviceRepairId(virtuallabconstruction.getLabRoomDeviceRepairId());
			}
			virtuallabconstruction = virtualLabConstructionDAO.store(existingVirtualLabConstruction);
		} else {
			virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		}
		virtualLabConstructionDAO.flush();
	}

	/**
	 * Delete an existing SchoolAcademy entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction deleteVirtualLabConstructionSchoolAcademy(Integer virtuallabconstruction_id, String related_schoolacademy_academyNumber) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(virtuallabconstruction_id, -1, -1);
		SchoolAcademy related_schoolacademy = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(related_schoolacademy_academyNumber, -1, -1);

		virtuallabconstruction.setSchoolAcademy(null);
		//related_schoolacademy.getVirtualLabConstructions().remove(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_schoolacademy = schoolAcademyDAO.store(related_schoolacademy);
		schoolAcademyDAO.flush();

		schoolAcademyDAO.remove(related_schoolacademy);
		schoolAcademyDAO.flush();

		return virtuallabconstruction;
	}

	/**
	 * Save an existing LabRoomProject entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction saveVirtualLabConstructionLabRoomProject(Integer id, LabRoomProject related_labroomproject) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(id, -1, -1);
		LabRoomProject existinglabRoomProject = labRoomProjectDAO.findLabRoomProjectByPrimaryKey(related_labroomproject.getId());

		// copy into the existing record to preserve existing relationships
		if (existinglabRoomProject != null) {
			existinglabRoomProject.setId(related_labroomproject.getId());
			//existinglabRoomProject.setLabRoomId(related_labroomproject.getLabRoomId());
			existinglabRoomProject.setOperationItem(related_labroomproject.getOperationItem());
			related_labroomproject = existinglabRoomProject;
		} else {
			related_labroomproject = labRoomProjectDAO.store(related_labroomproject);
			labRoomProjectDAO.flush();
		}

		virtuallabconstruction.setLabRoomProject(related_labroomproject);
		//related_labroomproject.getVirtualLabConstructions().add(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_labroomproject = labRoomProjectDAO.store(related_labroomproject);
		labRoomProjectDAO.flush();

		return virtuallabconstruction;
	}

	/**
	 * Return all VirtualLabConstruction entity
	 * 
	 */
	@Transactional
	public List<VirtualLabConstruction> findAllVirtualLabConstructions(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<VirtualLabConstruction>(virtualLabConstructionDAO.findAllVirtualLabConstructions(startResult, maxRows));
	}

	/**
	 * Delete an existing SystemRoom entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction deleteVirtualLabConstructionSystemRoom(Integer virtuallabconstruction_id, String related_systemroom_roomNumber) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(virtuallabconstruction_id, -1, -1);
		SystemRoom related_systemroom = systemRoomDAO.findSystemRoomByPrimaryKey(related_systemroom_roomNumber, -1, -1);

		virtuallabconstruction.setSystemRoom(null);
		//related_systemroom.getVirtualLabConstructions().remove(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_systemroom = systemRoomDAO.store(related_systemroom);
		systemRoomDAO.flush();

		systemRoomDAO.remove(related_systemroom);
		systemRoomDAO.flush();

		return virtuallabconstruction;
	}

	/**
	 * Delete an existing VirtualLabConstruction entity
	 * 
	 */
	@Transactional
	public void deleteVirtualLabConstruction(VirtualLabConstruction virtuallabconstruction) {
		virtualLabConstructionDAO.remove(virtuallabconstruction);
		virtualLabConstructionDAO.flush();
	}

	/**
	 * Return a count of all VirtualLabConstruction entity
	 * 
	 */
	@Transactional
	public Integer countVirtualLabConstructions() {
		return ((Long) virtualLabConstructionDAO.createQuerySingleResult("select count(o) from VirtualLabConstruction o").getSingleResult()).intValue();
	}

	/**
	 * Save an existing SchoolAcademy entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction saveVirtualLabConstructionSchoolAcademy(Integer id, SchoolAcademy related_schoolacademy) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(id, -1, -1);
		SchoolAcademy existingschoolAcademy = schoolAcademyDAO.findSchoolAcademyByPrimaryKey(related_schoolacademy.getAcademyNumber());

		// copy into the existing record to preserve existing relationships
		if (existingschoolAcademy != null) {
			existingschoolAcademy.setAcademyNumber(related_schoolacademy.getAcademyNumber());
			existingschoolAcademy.setAcademyNumber(related_schoolacademy.getAcademyNumber());
			existingschoolAcademy.setAcademyName(related_schoolacademy.getAcademyName());
			existingschoolAcademy.setIsVaild(related_schoolacademy.getIsVaild());
			existingschoolAcademy.setAcademyType(related_schoolacademy.getAcademyType());
			existingschoolAcademy.setCreatedAt(related_schoolacademy.getCreatedAt());
			existingschoolAcademy.setUpdatedAt(related_schoolacademy.getUpdatedAt());
			related_schoolacademy = existingschoolAcademy;
		} else {
			related_schoolacademy = schoolAcademyDAO.store(related_schoolacademy);
			schoolAcademyDAO.flush();
		}

		virtuallabconstruction.setSchoolAcademy(related_schoolacademy);
		//related_schoolacademy.getVirtualLabConstructions().add(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_schoolacademy = schoolAcademyDAO.store(related_schoolacademy);
		schoolAcademyDAO.flush();

		return virtuallabconstruction;
	}

	/**
	 * Save an existing SystemRoom entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction saveVirtualLabConstructionSystemRoom(Integer id, SystemRoom related_systemroom) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(id, -1, -1);
		SystemRoom existingsystemRoom = systemRoomDAO.findSystemRoomByPrimaryKey(related_systemroom.getRoomNumber());

		// copy into the existing record to preserve existing relationships
		if (existingsystemRoom != null) {
			existingsystemRoom.setRoomNumber(related_systemroom.getRoomNumber());
			existingsystemRoom.setRoomNo(related_systemroom.getRoomNo());
			existingsystemRoom.setRoomName(related_systemroom.getRoomName());
			existingsystemRoom.setRoomUse(related_systemroom.getRoomUse());
			//existingsystemRoom.setBuildNumber(related_systemroom.getBuildNumber());
			existingsystemRoom.setDepartmentNumber(related_systemroom.getDepartmentNumber());
			existingsystemRoom.setCreatedAt(related_systemroom.getCreatedAt());
			existingsystemRoom.setUpdatedAt(related_systemroom.getUpdatedAt());
			related_systemroom = existingsystemRoom;
		} else {
			related_systemroom = systemRoomDAO.store(related_systemroom);
			systemRoomDAO.flush();
		}

		virtuallabconstruction.setSystemRoom(related_systemroom);
		//related_systemroom.getVirtualLabConstructions().add(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_systemroom = systemRoomDAO.store(related_systemroom);
		systemRoomDAO.flush();

		return virtuallabconstruction;
	}

/*	*//**
	 *//*
	@Transactional
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id) {
		return virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(id);
	}*/

	/**
	 * Delete an existing LabRoomDevice entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction deleteVirtualLabConstructionLabRoomDevice(Integer virtuallabconstruction_id, Integer related_labroomdevice_id) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(virtuallabconstruction_id, -1, -1);
		LabRoomDevice related_labroomdevice = labRoomDeviceDAO.findLabRoomDeviceByPrimaryKey(related_labroomdevice_id, -1, -1);

		virtuallabconstruction.setLabRoomDevice(null);
		//related_labroomdevice.getVirtualLabConstructions().remove(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_labroomdevice = labRoomDeviceDAO.store(related_labroomdevice);
		labRoomDeviceDAO.flush();

		labRoomDeviceDAO.remove(related_labroomdevice);
		labRoomDeviceDAO.flush();

		return virtuallabconstruction;
	}

	/**
	 * Delete an existing LabRoomProject entity
	 * 
	 */
	@Transactional
	public VirtualLabConstruction deleteVirtualLabConstructionLabRoomProject(Integer virtuallabconstruction_id, Integer related_labroomproject_id) {
		VirtualLabConstruction virtuallabconstruction = virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(virtuallabconstruction_id, -1, -1);
		LabRoomProject related_labroomproject = labRoomProjectDAO.findLabRoomProjectByPrimaryKey(related_labroomproject_id, -1, -1);

		virtuallabconstruction.setLabRoomProject(null);
		//related_labroomproject.getVirtualLabConstructions().remove(virtuallabconstruction);
		virtuallabconstruction = virtualLabConstructionDAO.store(virtuallabconstruction);
		virtualLabConstructionDAO.flush();

		related_labroomproject = labRoomProjectDAO.store(related_labroomproject);
		labRoomProjectDAO.flush();

		labRoomProjectDAO.remove(related_labroomproject);
		labRoomProjectDAO.flush();

		return virtuallabconstruction;
	}
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public List<VirtualLabConstruction> findAllVirtualLabByVirtualLab(VirtualLabConstruction virtualLabConstruction) {
		// TODO Auto-generated method stub
		String sql="select c from VirtualLabConstruction c where 1=1";
		if(virtualLabConstruction.getProjectBaseName()!=null&&!virtualLabConstruction.getProjectBaseName().equals("")){
			sql+=" and c.projectBaseName like '%"+virtualLabConstruction.getProjectBaseName()+"%'";
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
		return virtualLabConstructionDAO.executeQuery(sql,0,-1);
	}
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public List<VirtualLabConstruction> findAllVirtualLabByVirtualLab(VirtualLabConstruction virtualLabConstruction,
                                                                      int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from VirtualLabConstruction c where 1=1";
		if(virtualLabConstruction.getProjectBaseName()!=null&&!virtualLabConstruction.getProjectBaseName().equals("")){
			sql+=" and c.projectBaseName like '%"+virtualLabConstruction.getProjectBaseName()+"%'";
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
		return virtualLabConstructionDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * 功能：查询所有的实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public Set<VirtualLabConstruction> findAllVirtualLabConstruction() {
		// TODO Auto-generated method stub
		return virtualLabConstructionDAO.findAllVirtualLabConstructions();
	}
	/****************************************************************************
	 * 功能：根据主键查询实验中心
	 * 作者：李德
	 ****************************************************************************/
	@Override
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return virtualLabConstructionDAO.findVirtualLabConstructionByPrimaryKey(id);
	}
	
	/***********************************************************************************************
	 * 功能：根据条件导出到电子表格
	 * 作者：李德
	 * 时间：2015-03-19
	 ***********************************************************************************************/
	public void exportExcelVirtualLab(List<VirtualLabConstruction> virtualLabConstructions,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		// 循环alls；
		for (VirtualLabConstruction virtualLabConstruction : virtualLabConstructions) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			System.out.println(virtualLabConstruction.getProjectBaseName());
			// 映射实践基地名称；
			map.put("projectBaseName", virtualLabConstruction.getProjectBaseName());
			// 映射面向专业；
			map.put("majorName", virtualLabConstruction.getMajorName());
			// 映射支持部门；
			map.put("supportAcademyName", virtualLabConstruction.getSupportAcademyName());
			// 映射建筑面积；
			map.put("buildingArea", virtualLabConstruction.getBuildingArea());
			// 映射占地面积；
			map.put("siteArea", virtualLabConstruction.getSiteArea());
			// 映射设备价值；
			map.put("deviceValue", virtualLabConstruction.getDeviceValue());
			// 映射当年新增设备值；
			map.put("addDeviceValue", virtualLabConstruction.getAddDeviceValue());
			// 映射自主研制设备值；
			map.put("ownDeviceValue", virtualLabConstruction.getOwnDeviceValue());			
			// 映射社会捐赠设备值；
			map.put("donateDeviceValue", virtualLabConstruction.getDonateDeviceValue());
			// 映射社会准捐赠设备值；
			map.put("preDonateDeviceValue", virtualLabConstruction.getPreDonateDeviceValue());
			// 映射设备总数；
			map.put("deviceAmount", virtualLabConstruction.getDeviceAmount());
			// 映射大型设备数；
			map.put("hugeDeviceAmount", virtualLabConstruction.getHugeDeviceAmount());
			// 映射总数；
			map.put("trainingProjectAmount", virtualLabConstruction.getTrainingProjectAmount());
			// 映射主要项目名称；
			map.put("trainingProjectName", virtualLabConstruction.getTrainingProjectName());
			// 映射学年使用频率-校内；
			map.put("useFrequencySchool", virtualLabConstruction.getUseFrequencySchool());
			// 映射学年使用频率-社会；
			map.put("useFrequencySociety", virtualLabConstruction.getUseFrequencySociety());
			// 映射原材料(耗材)费用；
			map.put("materialFee", virtualLabConstruction.getMaterialFee());
			// 映射设备维护费；
			map.put("deviceMaintainFee", virtualLabConstruction.getDeviceMaintainFee());
			// 映射专职管理人员数；
			map.put("managePeopleAmount", virtualLabConstruction.getManagePeopleAmount());
			// 映射兼职管理人员数；
			map.put("partTimePeopleAmount", virtualLabConstruction.getPartTimePeopleAmount());			


			;

			list1.add(map);
		}
		// 给表设置名称；
		String title = "虚拟实验室表  " + 1 + "-" + this.countVirtualLabConstructions();
		// 给表设置表名；
		String[] hearders = new String[] { "实践基地名称", "面向专业", "支持部门", "建筑面积", "占地面积", "设备价值"
				, "当年新增设备值", "自主研制设备值", "社会捐赠设备值", "社会准捐赠设备值", "设备总数", "大型设备数", "总数"
				, "主要项目名称", "学年使用频率-校内", "学年使用频率-社会", "原材料(耗材)费用", "设备维护费", "专职管理人员数"
				, "兼职管理人员数"};// 表头数组
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "projectBaseName", "majorName", "supportAcademyName", "buildingArea"
				, "siteArea", "deviceValue", "addDeviceValue", "ownDeviceValue", "donateDeviceValue"
				, "preDonateDeviceValue", "deviceAmount", "hugeDeviceAmount", "trainingProjectAmount"
				, "trainingProjectName", "useFrequencySchool", "useFrequencySociety", "materialFee"
				, "deviceMaintainFee", "managePeopleAmount", "partTimePeopleAmount"};
		// 输出excel；
		baseApplicationService.exportExcel(list1, title, hearders, fields,
				request, response);

	}	
	
	/****************************************************************************
	 * 功能：根据实验室id查询实验分室并分页
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByAll(LabRoom labRoom,
                                          Integer page, int pageSize) {
		String sql="select r from LabRoom r where  r.isUsed=1";
		if(labRoom.getLabRoomName()!=null&&!labRoom.getLabRoomName().equalsIgnoreCase("")){
			sql+=" and r.labRoomName like '%"+labRoom.getLabRoomName()+"%'";
		}
		sql+=" order by r.id desc";
		return labRoomDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/****************************************************************************
	 * 功能：根据实验室id查询实验分室
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByVirtualLab(LabRoom labRoom) {
		// TODO Auto-generated method stub
		String sql="select r from LabRoom r where  r.isUsed=1";
		if(labRoom.getLabRoomName()!=null&&!labRoom.getLabRoomName().equalsIgnoreCase("")){
			sql+=" and r.labRoomName like '%"+labRoom.getLabRoomName()+"%'";
		}
		sql+=" order by r.id desc";
		return labRoomDAO.executeQuery(sql);
	}
	
	/***********************************************************************************************
	 * public void exportExcelCar(String name,String servBegin) {
	 * 
	 * @param:
	 * @description:根据条件导出到电子表格
	 * @author:李德
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public void exportExcelLabRoom(List<LabRoom> labRooms,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		// 循环alls；
		for (LabRoom labRoom : labRooms) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			// 映射学校代码；
			map.put("schoolCode", "11733");
			// 映射实验室编号；
			map.put("labRoomNumber", labRoom.getLabRoomNumber());
			// 映射实验室名称；
			map.put("labRoomName", labRoom.getLabRoomName());
			// 映射实验室类别；
			map.put("labRoomType", labRoom.getCDictionaryByLabRoom().getCName());
			// 映射房屋使用面积；
			map.put("labRoomArea", labRoom.getLabRoomArea());
			// 映射所属学科；
			map.put("labRoomSubject", labRoom.getSystemSubject12());

			list1.add(map);
		}
		// 给表设置名称；
		String title = "实验室基本情况表  " + 1 + "-" + this.countVirtualLabConstructions();
		// 给表设置表名；
		String[] hearders = new String[] { "学校代码", "实验室编号", "实验室名称", "实验室类别", "房屋使用面积", "所属学科"
			};// 表头数组
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "schoolCode", "labRoomNumber", "labRoomName", "labRoomType"
				, "labRoomArea", "labRoomSubject"};
		// 输出excel；
		/*baseApplicationService.exportExcel(list1, title, hearders, fields,
				request, response);*/
		
		// 封装表格数据
		TableData tableData = ExcelUtils.createTableData(list1, ExcelUtils.createTableHeader(hearders), fields);
		// 导出工具类
		JsGridReportBase report = new JsGridReportBase(request, response);
		// 创建新的Excel 工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 根据模板文件，初始化表头样式
		HashMap<String, HSSFCellStyle> styles = report.initStyles(wb);
		wb = report.writeSheet(wb,title,styles,shareService.getUser().getCname(),tableData);//写入工作表
		
		//tableData.getRowCount()是数据的行数，不包括表头、标题等
/*		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount(), tableData.getRowCount(), 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+1, tableData.getRowCount()+1, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+2, tableData.getRowCount()+2, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+3, tableData.getRowCount()+3, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+4, tableData.getRowCount()+4, 0, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
*/		
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(3, 4, 0, 0));

		HSSFCellStyle style = wb.createCellStyle();
		//设置居中或者左右对齐
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);  //最后一行右对齐
		// 设置背景色
		style.setFillBackgroundColor((short) 13);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//设置字体
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);//设置字体大小

		HSSFFont font2 = wb.createFont();
		font2.setFontName("仿宋_GB2312");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font2.setFontHeightInPoints((short) 12);

		style.setFont(font);//选择需要用到的字体格式
		//设置列宽
		HSSFSheet sheet = wb.createSheet();
		wb.getSheetAt(0).setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		//设置自动换行
		style.setWrapText(true);//设置自动换行

		wb.getSheetAt(0).getRow(wb.getSheetAt(0).getLastRowNum()).getCell(0).setCellStyle(style);
		
		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());

	}	
	
	/***********************************************************************************************
	 * public void exportExcelCar(String name,String servBegin) {
	 * 
	 * @param:
	 * @description:根据条件导出到电子表格
	 * @author:李德
	 * @version:
	 * @modify:
	 ***********************************************************************************************/
	public void exportExcelExperimentUser(List<User> users,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		// 循环alls；
		for (User user : users) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
            //学校代码
			map.put("schoolCode", "11733");
			//人员编号
			map.put("userId", user.getUsername());
			// 映射学号/工号；
			map.put("username", user.getUsername());
			// 所属学科；
			map.put("schoolAcademy", user.getSchoolAcademy());

			;

			list1.add(map);
		}
		// 给表设置名称；
		String title = "专任实验室人员表  " ;
		// 给表设置表名；
		String[] hearders = new String[] { "学校代码", "人员编号", "姓名", "所属学科"};// 表头数组
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "schoolCode", "userId", "username", "schoolAcademy"};
		// 输出excel；
		baseApplicationService.exportExcel(list1, title, hearders, fields,
				request, response);

	}
	
	/****************************************************************************
	 * 功能：保存实验室对象
	 * 作者：李德
	 * 时间：2015-04-09
	 ****************************************************************************/
	@Override
	public VirtualLabConstruction save(VirtualLabConstruction virtualLabConstruction) {
		// TODO Auto-generated method stub
		Set<SchoolMajor> schoolMajors=new HashSet<SchoolMajor>();
		System.out.println("1111="+virtualLabConstruction.getMajorName());
		if(!(virtualLabConstruction.getMajorName()==null))
		{
			String[] arraycstaff=virtualLabConstruction.getMajorName().split(",");
			//System.out.println(arraycstaff);
			for(String strAuth:arraycstaff)
		   {		
			//int cstaffId=Integer.parseInt(strAuth);
			//System.out.println("cstaffId="+cstaffId);
			SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
			schoolMajors.add(schoolMajor);	
		   }
		
		}
//		virtualLabConstruction.setMajorName(schoolMajors);
		return virtualLabConstructionDAO.store(virtualLabConstruction);
	}	
	
	/****************************************************************************
	 * 功能：查询部门名称
	 * 作者：李德
	 * 时间：2014-8-25
	 ****************************************************************************/
	@Override
	public Set<SchoolMajor> findAllSchoolMajor() {
		// TODO Auto-generated method stub
		return schoolMajorDAO.findAllSchoolMajors();
	}
	
	/***********************************************************************************************
	 * 功能：根据条件导出到电子表格
	 * 作者：李德
	 * 时间：2015-03-19
	 ***********************************************************************************************/
	public void exportExcelVirtualLabTest(List<VirtualLabConstruction> virtualLabConstructions,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		int count = 1;
		// 循环alls；
		for (VirtualLabConstruction virtualLabConstruction : virtualLabConstructions) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			System.out.println(virtualLabConstruction.getProjectBaseName());
			// 映射实践基地名称；
			map.put("projectBaseName", virtualLabConstruction.getProjectBaseName());
			// 映射面向专业；
			map.put("majorName", virtualLabConstruction.getMajorName());
			// 映射支持部门；
			map.put("supportAcademyName", virtualLabConstruction.getSupportAcademyName());
			// 映射建筑面积；
			map.put("buildingArea", virtualLabConstruction.getBuildingArea());
			// 映射占地面积；
			map.put("siteArea", virtualLabConstruction.getSiteArea());
			// 映射设备价值；
			map.put("deviceValue", virtualLabConstruction.getDeviceValue());
			// 映射当年新增设备值；
			map.put("addDeviceValue", virtualLabConstruction.getAddDeviceValue());
			// 映射自主研制设备值；
			map.put("ownDeviceValue", virtualLabConstruction.getOwnDeviceValue());			
			// 映射社会捐赠设备值；
			map.put("donateDeviceValue", virtualLabConstruction.getDonateDeviceValue());
			// 映射社会准捐赠设备值；
			map.put("preDonateDeviceValue", virtualLabConstruction.getPreDonateDeviceValue());
			// 映射设备总数；
			map.put("deviceAmount", virtualLabConstruction.getDeviceAmount());
			// 映射大型设备数；
			map.put("hugeDeviceAmount", virtualLabConstruction.getHugeDeviceAmount());
			// 映射总数；
			map.put("trainingProjectAmount", virtualLabConstruction.getTrainingProjectAmount());
			// 映射主要项目名称；
			map.put("trainingProjectName", virtualLabConstruction.getTrainingProjectName());
			// 映射学年使用频率-校内；
			map.put("useFrequencySchool", virtualLabConstruction.getUseFrequencySchool());
			// 映射学年使用频率-社会；
			map.put("useFrequencySociety", virtualLabConstruction.getUseFrequencySociety());
			// 映射原材料(耗材)费用；
			map.put("materialFee", virtualLabConstruction.getMaterialFee());
			// 映射设备维护费；
			map.put("deviceMaintainFee", virtualLabConstruction.getDeviceMaintainFee());
			// 映射专职管理人员数；
			map.put("managePeopleAmount", virtualLabConstruction.getManagePeopleAmount());
			// 映射兼职管理人员数；
			map.put("partTimePeopleAmount", virtualLabConstruction.getPartTimePeopleAmount());
			//
			map.put("count", count);
            count = count +1;

			;

			list1.add(map);
		}
		// 给表设置名称；
		String title = "虚拟实验室表  " + 1 + "-" + this.countVirtualLabConstructions();
		// 给表设置表名；
/*		String[] parentHeaders = new String[] {"序号","实践基地名称","面向专业","被列为实训基地项目","建筑面积（平方米）",
				"设备值（万元）","当年设备来源(万元）","设备数（台套）","实训项目","学年使用频率（人时）","费用（万元）","人员（名）"};
		String[][] childrenHeaders = new String[][] {{"序号"},{"实践基地名称"}, {"总数（个）", "主要专业"}, {"支持部门", "批准日期（年）"},
				{"建筑面积"}, {"设备价值", "当年新增设备值"}, {"自主研制设备值", "社会捐赠设备值", "社会准捐赠设备值"}, {"设备总数", "大型设备数"}
				, {"总数", "主要项目名称"}, {"校内", "社会"}, {"原材料(耗材)费用", "设备维护费"}, {"专职管理人员数", "兼职管理人员数"}};// 表头数组
*/		
		String[] parentHeaders = new String[] {"实践基地名称","面向专业","被列为实训基地项目","建筑面积（平方米）",
				 "设备值（万元）","当年设备来源(万元）","设备数（台套）","实训项目","学年使用频率（人时）","费用（万元）","人员（名）"};
		String[][] childrenHeaders = new String[][] {{""}, {"总数（个）", "主要专业"}, {"支持部门", "批准日期（年）"},
		         {" "}, {"设备价值", "当年新增设备值"}, {"自主研制设备值", "社会捐赠设备值", "社会准捐赠设备值"}, {"设备总数", "大型设备数"}
		         , {"总数", "主要项目名称"}, {"校内", "社会"}, {"原材料(耗材)费用", "设备维护费"}, {"专职管理人员数", "兼职管理人员数"}};
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "projectBaseName", "majorName", "supportAcademyName", "buildingArea"
				, "siteArea", "deviceValue", "addDeviceValue", "ownDeviceValue", "donateDeviceValue"
				, "preDonateDeviceValue", "deviceAmount", "hugeDeviceAmount", "trainingProjectAmount"
				, "trainingProjectName", "useFrequencySchool", "useFrequencySociety", "materialFee"
				, "deviceMaintainFee", "managePeopleAmount", "partTimePeopleAmount","count" };
		// 封装表格数据
		TableData tableData = ExcelUtils.createTableData(list1, ExcelUtils.createTableHeader(parentHeaders, childrenHeaders), fields);
		// 导出工具类
		JsGridReportBase report = new JsGridReportBase(request, response);
		// 创建新的Excel 工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 根据模板文件，初始化表头样式
		HashMap<String, HSSFCellStyle> styles = report.initStyles(wb);
		wb = report.writeSheet(wb,title,styles,shareService.getUser().getCname(),tableData);//写入工作表
		
		//tableData.getRowCount()是数据的行数，不包括表头、标题等
/*		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount(), tableData.getRowCount(), 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+1, tableData.getRowCount()+1, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+2, tableData.getRowCount()+2, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+3, tableData.getRowCount()+3, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+4, tableData.getRowCount()+4, 0, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
*/		
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(3, 4, 0, 0));

		HSSFCellStyle style = wb.createCellStyle();
		//设置居中或者左右对齐
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);  //最后一行右对齐
		// 设置背景色
		style.setFillBackgroundColor((short) 13);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//设置字体
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 16);//设置字体大小

		HSSFFont font2 = wb.createFont();
		font2.setFontName("仿宋_GB2312");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font2.setFontHeightInPoints((short) 12);

		style.setFont(font);//选择需要用到的字体格式
		//设置列宽
		HSSFSheet sheet = wb.createSheet();
		wb.getSheetAt(0).setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		//设置自动换行
		style.setWrapText(true);//设置自动换行

		wb.getSheetAt(0).getRow(wb.getSheetAt(0).getLastRowNum()).getCell(0).setCellStyle(style);
		
		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());

	}	
		
	/***********************************************************************************************
	 * 功能：上传实验实训项目附件，并返回文档字符串信息
	 * 作者：李德
	 * 时间：2015-04-24
	 ***********************************************************************************************/
	@Override
	public String uploadFile(Integer id, HttpServletRequest request,
                             HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator");
		 Map files = multipartRequest.getFileMap();
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"operationItem"+sep;
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
			  CommonDocument doc=saveDocument(fileTrueName,id);
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
	public CommonDocument saveDocument(String fileTrueName, Integer id) {
		
		CommonDocument doc = new CommonDocument( );
		doc.setDocumentName(fileTrueName);
		OperationItem operationItem=operationService.getopertioniteminfor(id);
		doc.setOperationItem(operationItem);
		String url="upload/operationItem/"+fileTrueName;
		doc.setDocumentUrl(url);
		
		return commonDocumentDAO.store(doc);
	}
	
	/****************************************************************************
	 * 功能：根据验收申请ID查询实验实训项目附件
	 * 作者：李德
	 * 时间：2015-04-24
	 ****************************************************************************/
	@Override
	public List<CommonDocument> findCommonDocumentByOperationItemId(int idKey) {
		// TODO Auto-generated method stub
		String sql="select c from CommonDocument c where c.operationItem.id =" +idKey;

		return commonDocumentDAO.executeQuery(sql,0,-1);
	}
	
	/***********************************************************************************************
	 * 功能：根据实验实训项目id删除文档(暂不删除服务器上的文件)
	 * 作者：李德
	 * 时间：2015-04-24
	 ***********************************************************************************************/
	@Override
	public void deleteOperationItemCommonDocumentByOperationItemId(Integer idKey) {
		String sql="select d from CommonDocument d where d.operationItem.id="+idKey;
		List<CommonDocument> List=commonDocumentDAO.executeQuery(sql, 0,-1);
		for (CommonDocument doc : List) {
			commonDocumentDAO.remove(doc);
		}
	}
	
 	/***********************************************************************************************
	 * 功能：查找所有的实验实训项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	@Override
	public int getOperationItemExperiment(OperationItem operationItem,String acno) {
		String sql="select count(o) from OperationItem o where 1=1 and o.complex=null and o.COperationItemType.id!=4";
		if(operationItem.getLpName()!=null && operationItem.getLpName()!=""){
			sql+=" and (o.lpName like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or o.itemNumber  like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or o.experimentItemCardNumber  like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or  o.schoolCourseByClassNo.courseName  like '%"+operationItem.getLpName().trim()+"%')";
		}
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if( academy!=null && academy.getAcademyNumber()!=null){
			sql+=" and (o.schoolAcademyByCollege.academyNumber='"+academy.getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
		}
		System.out.println(sql);
		try{
			return ((Long) operationItemDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		}catch (Exception e) {
			return 0;
		}
	}
	
	/***********************************************************************************************
	 * 功能：查找所有的实验实训项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	@Override
	public List<OperationItem> getOperationItemExperimentpage(
			OperationItem operationItem, int currpage, int pagesize,String acno) {

		String sql="select o from OperationItem o where 1=1 and o.complex=null and o.COperationItemType.id!=4";
		if(operationItem.getLpName()!=null && operationItem.getLpName()!=""){
			sql+=" and (o.lpName like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or  o.schoolCourseByClassNo.courseNo!=null and o.schoolCourseByClassNo.courseName  like '%"+operationItem.getLpName().trim()+"%' )";
		}
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if( academy!=null && academy.getAcademyNumber()!=null){
			sql+=" and  (o.schoolAcademyByCollege.academyNumber='"+academy.getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
		}
		sql+=" order by o.id  desc" ;
		List<OperationItem> Item= operationItemDAO.executeQuery(sql,(currpage-1)*pagesize,pagesize);
		if(Item.size()==0){
			sql="select o from OperationItem o where 1=1 and o.complex=null and o.COperationItemType.id!=4";
			if(operationItem.getLpName()!=null && operationItem.getLpName()!=""){
				sql+=" and (o.lpName like '%"+operationItem.getLpName().trim()+"%'";
				sql+=" or o.itemNumber  like '%"+operationItem.getLpName().trim()+"%')";
			}
			if( academy!=null && academy.getAcademyNumber()!=null){
				sql+=" and  (o.schoolAcademyByCollege.academyNumber='"+academy.getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
			}
			sql+=" order by o.id  desc" ;
			Item= operationItemDAO.executeQuery(sql,(currpage-1)*pagesize,pagesize);
		}
		return  Item;
	}
	
	/***********************************************************************************************
	 * 功能：查找所有的实习项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	@Override
	public int getOperationItemPractice(OperationItem operationItem,String acno) {
		String sql="select count(o) from OperationItem o where 1=1 and o.complex=null and o.COperationItemType.id=4";
		if(operationItem.getLpName()!=null && operationItem.getLpName()!=""){
			sql+=" and (o.lpName like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or o.itemNumber  like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or o.experimentItemCardNumber  like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or  o.schoolCourseByClassNo.courseName  like '%"+operationItem.getLpName().trim()+"%')";

		}
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if( academy!=null && academy.getAcademyNumber()!=null){
			sql+=" and ( o.schoolAcademyByCollege.academyNumber='"+academy.getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
		}
		try{
			System.out.println(sql);
			return ((Long) operationItemDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		}catch (Exception e) {
			return 0;
		}
	}
	
	/***********************************************************************************************
	 * 功能：查找所有的实验实训项目卡数
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	@Override
	public List<OperationItem> getOperationItemPracticepage(
			OperationItem operationItem, int currpage, int pagesize,String acno) {

		String sql="select o from OperationItem o where 1=1 and o.complex=null and o.COperationItemType.id=4";
		if(operationItem.getLpName()!=null && operationItem.getLpName()!=""){
			sql+=" and (o.lpName like '%"+operationItem.getLpName().trim()+"%'";
			sql+=" or  o.schoolCourseByClassNo.courseNo!=null and o.schoolCourseByClassNo.courseName  like '%"+operationItem.getLpName().trim()+"%' )";
		}
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if( academy!=null && academy.getAcademyNumber()!=null){
			sql+=" and  (o.schoolAcademyByCollege.academyNumber='"+academy.getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
		}
		sql+=" order by o.id  desc" ;
		List<OperationItem> Item= operationItemDAO.executeQuery(sql,(currpage-1)*pagesize,pagesize);
		if(Item.size()==0){
			sql="select o from OperationItem o where 1=1 and o.complex=null and o.COperationItemType.id=4";
			if(operationItem.getLpName()!=null && operationItem.getLpName()!=""){
				sql+=" and (o.lpName like '%"+operationItem.getLpName().trim()+"%'";
				sql+=" or o.itemNumber  like '%"+operationItem.getLpName().trim()+"%')";
			}
			if( academy!=null && academy.getAcademyNumber()!=null){
				sql+=" and  (o.schoolAcademyByCollege.academyNumber='"+academy.getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
			}
			sql+=" order by o.id  desc" ;
			Item= operationItemDAO.executeQuery(sql,(currpage-1)*pagesize,pagesize);
		}
		return  Item;
	}
	
	/***********************************************************************************************
	 * 功能：根据条件导出到电子表格
	 * 作者：李德
	 * 时间：2015-04-29
	 ***********************************************************************************************/
	public void exportExcelLabconstructAnnex(List<LabAnnex> labAnnexs,
                                             HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 新建一个map的list集合；
		List<Map> list1 = new ArrayList<Map>();
		int count = 1;
		// 循环alls；
		for (LabAnnex labAnnex : labAnnexs) {
			// 新建一个HashMap对象；
			Map map = new HashMap();
			// 映射实践基地名称；
			map.put("projectBaseName", labAnnex.getLabName());

			//查询实验分室
			List<LabRoom> listLabRooms=this.findLabRoomByLabAnnexId(labAnnex.getId());
			String schoolMajors = "";
			String operationItems = "";
			int schoolMajorCount=0;	
			int operationItemsCount=0;	
			for (LabRoom labRoom : listLabRooms) {
				// 映射面向专业；
				if(!(labRoom.getMajorName()==null)){
					String[] arraySchoolMajor=labRoom.getMajorName().split(",");
					for(String strAuth:arraySchoolMajor){
						SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByPrimaryKey(strAuth);
						schoolMajors=schoolMajors+schoolMajor.getMajorName()+" ";
						schoolMajorCount = schoolMajorCount+1;
					}
				}
				//
				List<OperationItem> operationItem = this.findOperationItem(labRoom.getId());
				for(OperationItem items:operationItem){
					//System.out.println("items.getItemName():"+items.getItemName());
					operationItems = operationItems+items.getLpName()+" ";
					//System.out.println("operationItems:"+operationItems);
					operationItemsCount = operationItemsCount+1;
				}
				
			}
			map.put("majorName", schoolMajors);
			map.put("majorAmount", schoolMajorCount);
			map.put("operationItems", operationItems);
			map.put("operationItemsCount", operationItemsCount);
			
			// 映射支持部门；
			map.put("supportAcademyName", labAnnex.getLabCenter().getCenterName());
			// 批准日期；
			map.put("approveDate", "");
			//面积及设备
			
			BigDecimal buildingArea = new BigDecimal(0.0);
			BigDecimal deviceValue = new BigDecimal(0.0);
			int deviceAmount = 0;
			for (LabRoom labRoom : listLabRooms) {
				if(labRoom.getLabRoomArea()!=null){
					buildingArea =buildingArea.add(labRoom.getLabRoomArea());
				}
				
				
				List<LabRoomDevice> listLabRoomDevices=this.findLabRoomDeviceByLabRoomId(labRoom.getId());
				for (LabRoomDevice labRoomDevice : listLabRoomDevices) {
					if(labRoomDevice.getPrice()!=null){
						deviceValue.add(labRoomDevice.getPrice());
					}			
					deviceAmount = deviceAmount +1;
				}
			}
			// 映射建筑面积；
			map.put("buildingArea", buildingArea);
			// 映射设备价值；
			map.put("deviceValue", deviceValue);
			// 映射当年新增设备值；
			map.put("addDeviceValue", "");
			// 映射自主研制设备值；
			map.put("ownDeviceValue", "");			
			// 映射社会捐赠设备值；
			map.put("donateDeviceValue", "");
			// 映射社会准捐赠设备值；
			map.put("preDonateDeviceValue", "");
			// 映射设备总数；
			map.put("deviceAmount", deviceAmount);
			// 映射大型设备数；
			map.put("hugeDeviceAmount", "");
			// 映射总数；
			map.put("trainingProjectAmount", "");
			// 映射主要项目名称；
			map.put("trainingProjectName", "");
			// 映射学年使用频率-校内；
			map.put("useFrequencySchool", "");
			// 映射学年使用频率-社会；
			map.put("useFrequencySociety", "");
			// 映射原材料(耗材)费用；
			map.put("materialFee", "");
			// 映射设备维护费；
			map.put("deviceMaintainFee", "");
			// 映射专职管理人员数；
			map.put("managePeopleAmount", "1");
			// 映射兼职管理人员数；
			map.put("partTimePeopleAmount", "");
			//
			map.put("count", count);
            count = count +1;

			;

			list1.add(map);
		}
		// 给表设置名称；
		//String title = "上海出版印刷高等专科学校虚拟实验室表  " + 1 + "-" + this.countVirtualLabConstructions();
		String title = "虚拟实验室表  ";
		// 给表设置表名；		
		String[] parentHeaders = new String[] {"实践基地名称","面向专业","被列为实训基地项目","建筑面积（平方米）",
				 "设备值（万元）","当年设备来源(万元）","设备数（台套）","实训项目","学年使用频率（人时）","费用（万元）","人员（名）"};
		String[][] childrenHeaders = new String[][] {{""}, {"总数（个）", "主要专业"}, {"支持部门", "批准日期（年）"},
		         {" "}, {"设备价值", "当年新增设备值"}, {"自主研制设备值", "社会捐赠设备值", "社会准捐赠设备值"}, {"设备总数", "大型设备数"}
		         , {"总数", "主要项目名称"}, {"校内", "社会"}, {"原材料(耗材)费用", "设备维护费"}, {"专职管理人员数", "兼职管理人员数"}};
		// 属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] { "projectBaseName", "majorAmount", "majorName", "supportAcademyName"
				,"approveDate", "buildingArea","deviceValue", "addDeviceValue", "ownDeviceValue"
				, "donateDeviceValue", "preDonateDeviceValue", "deviceAmount", "hugeDeviceAmount"
				, "operationItemsCount", "operationItems", "useFrequencySchool", "useFrequencySociety"
				, "materialFee", "deviceMaintainFee", "managePeopleAmount", "partTimePeopleAmount" };
		// 封装表格数据
		TableData tableData = ExcelUtils.createTableData(list1, ExcelUtils.createTableHeader(parentHeaders, childrenHeaders), fields);
		// 导出工具类
		JsGridReportBase report = new JsGridReportBase(request, response);
		// 创建新的Excel 工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 根据模板文件，初始化表头样式
		HashMap<String, HSSFCellStyle> styles = report.initStyles(wb);
		wb = report.writeSheet(wb,title,styles,shareService.getUser().getCname(),tableData);//写入工作表
		
		//tableData.getRowCount()是数据的行数，不包括表头、标题等
/*		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount(), tableData.getRowCount(), 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+1, tableData.getRowCount()+1, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+2, tableData.getRowCount()+2, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+3, tableData.getRowCount()+3, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount()+4, tableData.getRowCount()+4, 0, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
*/		
		wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(3, 4, 0, 0));

		HSSFCellStyle style = wb.createCellStyle();
		//设置居中或者左右对齐
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);  //最后一行右对齐
		// 设置背景色
		style.setFillBackgroundColor((short) 13);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//设置字体
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);//设置字体大小

		HSSFFont font2 = wb.createFont();
		font2.setFontName("仿宋_GB2312");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font2.setFontHeightInPoints((short) 12);

		style.setFont(font);//选择需要用到的字体格式
		//设置列宽
		HSSFSheet sheet = wb.createSheet();
		wb.getSheetAt(0).setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		//设置自动换行
		style.setWrapText(true);//设置自动换行

		wb.getSheetAt(0).getRow(wb.getSheetAt(0).getLastRowNum()).getCell(0).setCellStyle(style);
		
		String sFileName = title + ".xls";
		response.setHeader("Content-Disposition", "attachment;filename="
				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		wb.write(response.getOutputStream());

	}	
	
	/****************************************************************************
	 * 功能：根据中心id和labAnnexid查询该中心的实验室
	 * 作者:李德
	 * 时间：2015-04-29
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByLabAnnexId(Integer labAnnexId) {
		// TODO Auto-generated method stub
		String sql="select m from LabRoom m where 1=1 and m.isUsed=1 and m.labAnnex.id="+labAnnexId;
		return labRoomDAO.executeQuery(sql, 0,-1);
	}
	
	/****************************************************************************
	 * 功能：根据中心id和labAnnexid查询该中心的实验室
	 * 作者:李德
	 * 时间：2015-04-29
	 ****************************************************************************/
	@Override
	public List<LabRoomDevice> findLabRoomDeviceByLabRoomId(Integer labRoomId) {
		// TODO Auto-generated method stub
		String sql="select n from LabRoomDevice n where 1=1 and n.labRoom.id="+labRoomId;
		return labRoomDeviceDAO.executeQuery(sql, 0,-1);
	}
	
	/****************************************************************************
	 * 功能：根据schoolAcademyNo查询专业
	 * 作者:李德
	 * 时间：2015-04-29
	 ****************************************************************************/
	@Override
	public List<SchoolMajor> findSchoolMajorSchoolAcademyId(String schoolAcademyNo) {
		// TODO Auto-generated method stub
		String sql="select o from SchoolMajor o where 1=1 and o.schoolAcademy.academyNumber='"+schoolAcademyNo+"'";
		return schoolMajorDAO.executeQuery(sql, 0,-1);
	}
	
	/****************************************************************************
	 * 功能：根据labRoomId查询实验项目
	 * 作者:李德
	 * 时间：2015-09-17
	 ****************************************************************************/
	@Override
	public List<OperationItem> findOperationItem(int labRoomId) {
		String sql="select o from OperationItem o join o.labRooms l where 1=1 and o.complex=null and l.id ="+ labRoomId;


		List<OperationItem> Item= operationItemDAO.executeQuery(sql, 0,-1);
		
		return Item;
	}
	
}
