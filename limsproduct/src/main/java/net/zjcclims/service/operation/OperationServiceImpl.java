package net.zjcclims.service.operation;


import com.alibaba.fastjson.JSONObject;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.service.lab.LabRoomService;
import net.zjcclims.service.system.SystemLogService;
import net.zjcclims.service.timetable.SchoolCourseService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.web.common.PConfig;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service("OperationService")
public class OperationServiceImpl implements OperationService {

	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private OperationItemMaterialRecordDAO operationItemMaterialRecordDAO;
	@Autowired
	private OperationItemDeviceDAO operationItemDeviceDAO;
	@Autowired
	private SchoolDeviceService schoolDeviceService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SystemLogService systemLogService;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private OperationOutlineDAO  operationOutlineDAO;
	@Autowired
	private SchoolCourseInfoDAO  schoolCourseInfoDAO;
	@Autowired
	private COperationOutlineCreditDAO  cOperationOutlineCreditDAO;
	@Autowired
	private SchoolAcademyDAO  SchoolAcademyDAO;
	@Autowired
	private SchoolCourseService  schoolCourseService;
	@Autowired
	private CDictionaryDAO  cDictionaryDAO;
	@Autowired
	private CommonDocumentDAO  commonDocumentDAO;
	@Autowired
	private OperItemAuditDAO operItemAuditDAO;
	@Autowired
	private SoftwareReserveAuditDAO softwareReserveAuditDAO;
	@Autowired
	private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	private MessageDAO messageDAO;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private MOutlineCourseDAO mOutlineCourseDAO;
	@Autowired
	private OperationOutlineCourseDAO operationOutlineCourseDAO;
	@Autowired
	private LabRoomDeviceService labRoomDeviceService;
	@Autowired
	private LabRoomService labRoomService;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private PConfig pConfig;
	/**
	 * 根据主键获取实验项目对象
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public OperationItem findOperationItemByPrimaryKey(Integer operationItemId) {
		return operationItemDAO.findOperationItemByPrimaryKey(operationItemId);
	}

	/**
	 * 根据查询条件实验项目列表
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public List<OperationItem> findAllOperationItemByQuery(Integer currpage, Integer pageSize, OperationItem operationItem, Integer cid) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select i from OperationItem i  where 1=1");
		if(cid!=null && cid>0)
		{
			hql.append(" and i.labRoom.labCenter.id="+cid); 
		}
		if(operationItem != null && operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
		{
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			hql.append(" and i.schoolTerm.id="+termId);
		}
		if(operationItem != null && operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
		{
			hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(operationItem != null && operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
		{
			hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
		}
		if(operationItem != null && operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
		{
			hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
		}
		if(operationItem != null && operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}
		hql.append(" order by i.schoolTerm.id desc, i.createdAt desc");
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	
	/**
	 * 根据查询条件实验项目列表
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public List<OperationItem> findAllOperationItemExceptDraft(Integer currpage, Integer pageSize, OperationItem operationItem, String acno, int orderBy) {
		SchoolTerm term = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		StringBuffer hql = new StringBuffer("select i from OperationItem i where 1=1");
		if(acno!=null && !acno.equals("-1")) {
			hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		}
		if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null) {
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			hql.append(" and i.schoolTerm.id="+ term.getId());
		}
		if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName())) {
			hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null) {
			hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
		}
		hql.append(" and i.CDictionaryByLpStatusCheck.id <>543");
		if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="") {
			hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
		}
		if(operationItem.getUserByLpCheckUser()!=null && operationItem.getUserByLpCheckUser().getUsername()!="") {
			hql.append(" and i.userByLpCheckUser.username='"+operationItem.getUserByLpCheckUser().getUsername()+"'");
		}
		if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber())) {
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}
		if (orderBy==9) {//默认，按学期排序
			hql.append(" order by i.schoolTerm.id desc, i.createdAt desc");
		}
		if (orderBy==0) {//实验编号降序
			hql.append(" order by i.lpCodeCustom desc, i.createdAt desc");
		}
		if (orderBy==10) {//实验编号升序
			hql.append(" order by i.lpCodeCustom asc, i.createdAt desc");
		}
		if (orderBy==1) {//实验名称降序
			hql.append(" order by i.lpName desc, i.createdAt desc");
		}
		if (orderBy==11) {//实验名称升序
			hql.append(" order by i.lpName asc, i.createdAt desc");
		}
		if (orderBy==2) {//所属实验室降序
			hql.append(" order by i.labRoom.id desc, i.createdAt desc");
		}
		if (orderBy==12) {//所属实验室升序
			hql.append(" order by i.labRoom.id asc, i.createdAt desc");
		}
		if (orderBy==3) {//所属课程降序
			hql.append(" order by i.schoolCourseInfo.courseNumber desc, i.createdAt desc");
		}
		if (orderBy==13) {//所属课程升序
			hql.append(" order by i.schoolCourseInfo.courseNumber asc, i.createdAt desc");
		}
		if (orderBy==4) {//审核状态降序
			hql.append(" order by i.CDictionaryByLpStatusCheck.id desc, i.createdAt desc");
		}
		if (orderBy==14) {//审核状态升序
			hql.append(" order by i.CDictionaryByLpStatusCheck.id asc, i.createdAt desc");
		}
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	/**
	 * 根据查询条件实验项目列表--增加排序
	 * @author 贺子龙
	 * 2015-11-20
	 */
	@Override
	public List<OperationItem> findAllOperationItemByQuery(HttpServletRequest request,Integer currpage, Integer pageSize,
														   OperationItem operationItem, String acno, int orderBy) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select i from OperationItem i  where 1=1");
		//不是学生的其他权限
		User user=shareService.getUser();
		Set<Authority> auths = user.getAuthorities();
		int au=0;
		for (Authority a : auths) {
			if(a.getId()!=1){
				au=1;break;
			}
		}
		// 权限等级
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if(au!=0){
			if(acno!=null && !acno.equals("-1") && (authLevel<1 || authLevel>2))// 校级领导查看全部
			{
				hql.append(" and i.userByLpCreateUser.schoolAcademy.academyNumber='"+ acno +"'");
			}
							/*if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
							{
								hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
							}else {
								hql.append(" and i.schoolTerm.id="+termId);
							}*/
			if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
			{
				hql.append(" and (i.lpName like '%"+operationItem.getLpName()+"%'");
				hql.append(" or i.schoolCourseInfo.courseNumber like '%"+operationItem.getLpName()+"%'");
				hql.append(" or i.schoolCourseInfo.courseName like '%"+operationItem.getLpName()+"%'");
				hql.append(" or i.userByLpCreateUser.cname like '%"+operationItem.getLpName()+"%'");
				hql.append(" or i.userByLpCheckUser.cname like '%"+operationItem.getLpName()+"%')");
			}
			if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
			{
				hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
			}
			if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getCNumber()!=null)
			{
				if(operationItem.getCDictionaryByLpStatusCheck().getCNumber().equals("1")){
					hql.append(" and i.CDictionaryByLpStatusCheck.CNumber between '"+1+"' and '"+2+"' ");
				}
				if(operationItem.getCDictionaryByLpStatusCheck().getCNumber().equals("3")){
					hql.append(" and i.CDictionaryByLpStatusCheck.CNumber between '"+3+"' and '"+4+"' ");
				}
			}
			//当前登录人（我的项目）
			if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
			{
				hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
			}
			if (orderBy==9) {//默认，按学期排序
				hql.append(" order by i.schoolTerm.id desc, i.createdAt desc");
			}
			if (orderBy==0) {//实验编号降序
				hql.append(" order by i.lpCodeCustom desc, i.createdAt desc");
			}
			if (orderBy==10) {//实验编号升序
				hql.append(" order by i.lpCodeCustom asc, i.createdAt desc");
			}
			if (orderBy==1) {//实验名称降序
				hql.append(" order by i.lpName desc, i.createdAt desc");
			}
			if (orderBy==11) {//实验名称升序
				hql.append(" order by i.lpName asc, i.createdAt desc");
			}
			if (orderBy==2) {//所属实验室降序
				hql.append(" order by i.labRoom.id desc, i.createdAt desc");
			}
			if (orderBy==12) {//所属实验室升序
				hql.append(" order by i.labRoom.id asc, i.createdAt desc");
			}
			if (orderBy==3) {//所属课程降序
				hql.append(" order by i.schoolCourseInfo.courseNumber desc, i.createdAt desc");
			}
			if (orderBy==13) {//所属课程升序
				hql.append(" order by i.schoolCourseInfo.courseNumber asc, i.createdAt desc");
			}
			if (orderBy==4) {//审核状态降序
				hql.append(" order by i.CDictionaryByLpStatusCheck.id desc, i.createdAt desc");
			}
			if (orderBy==14) {//审核状态升序
				hql.append(" order by i.CDictionaryByLpStatusCheck.id asc, i.createdAt desc");
			}
		}else{
//							hql.append(" and i.userByLpCreateUser.username='"+user.getUsername()+"'");
			//查询当前登录人审核的记录
			if(operationItem!=null){
				Set<OperItemAudit> operItemAudits=operationItem.getOperItemAudits();
				if(operItemAudits!=null){
					for(OperItemAudit op:operItemAudits){
						if(op.getUser().getUsername()==user.getUsername()){
							hql.append(" or i.id="+op.getOperationItem().getId());
						}
					}
				}
			}
		}
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 实验室导入的时候需要默认显示当前学期之前的那个学期
	 * @author 贺子龙
	 * 2015-09-24 11:15:25
	 */
	@Override
	public List<OperationItem> findAllOperationItemByQueryImport(Integer currpage, Integer pageSize, OperationItem operationItem, String acno) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select i from OperationItem i  where 1=1");
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null)
		{
			hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		}
		if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
		{
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			hql.append(" and i.schoolTerm.id="+(termId-1));
		}
		if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
		{
			hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
		{
			hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
		}
		if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
		{
			hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
		}
		if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}
		hql.append(" order by i.schoolTerm.id desc");
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	/**
	 * 实验室导入的时候需要默认显示当前学期之前的那个学期--增加排序
	 * @author 贺子龙
	 * 2015-09-24 11:15:25
	 */
	@Override
	public List<OperationItem> findAllOperationItemByQueryImport(Integer currpage, Integer pageSize, OperationItem operationItem, String acno,int orderBy) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select i from OperationItem i  where 1=1");
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
		if(academy!=null && academy.getAcademyNumber()!=null)
		{
			hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		}
		if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
		{
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			hql.append(" and i.schoolTerm.id="+(termId-1));
		}
		if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
		{
			hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
		{
			hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
		}
		if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
		{
			hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
		}
		if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}
		if (orderBy==9) {//默认，按学期排序
			hql.append(" order by i.schoolTerm.id desc");
			
		}
		if (orderBy==0) {//实验编号降序
			hql.append(" order by i.lpCodeCustom desc");
			
		}
		if (orderBy==10) {//实验编号升序
			hql.append(" order by i.lpCodeCustom asc");
			
		}
		if (orderBy==1) {//实验名称降序
			hql.append(" order by i.lpName desc");
			
		}
		if (orderBy==11) {//实验名称升序
			hql.append(" order by i.lpName asc");
			
		}
		if (orderBy==2) {//所属实验室降序
			hql.append(" order by i.labRoom.id desc");
			
		}
		if (orderBy==12) {//所属实验室升序
			hql.append(" order by i.labRoom.id asc");
			
		}
		if (orderBy==3) {//所属课程降序
			hql.append(" order by i.schoolCourseInfo.courseNumber desc");
			
		}
		if (orderBy==13) {//所属课程升序
			hql.append(" order by i.schoolCourseInfo.courseNumber asc");
			
		}
		if (orderBy==4) {//审核状态降序
			hql.append(" order by i.CDictionaryByLpStatusCheck.id desc");
			
		}
		if (orderBy==14) {//审核状态升序
			hql.append(" order by i.CDictionaryByLpStatusCheck.id asc");
			
		}
		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	/**
	 * 根据查询条件实验项目记录数量
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public Integer findAllOperationItemByQueryCount(HttpServletRequest request,OperationItem operationItem, String acno) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select count(i) from OperationItem i where 1=1");
		//不是学生的其他权限
		User user=shareService.getUser();
		Set<Authority> auths = user.getAuthorities();
		int au=0;
		for (Authority a : auths) {
			if(a.getId()!=1){
				au=1;break;
			}
		}
		// 权限等级
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if(au!=0){
			if(acno!=null && !acno.equals("-1") && (authLevel<1 || authLevel>2))
			{
				hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
			}
			if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
			{
				hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
			}
			if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
			{
				hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
			}
			if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getCNumber()!=null)
			{
				if(operationItem.getCDictionaryByLpStatusCheck().getCNumber().equals("1")){
					hql.append(" and i.CDictionaryByLpStatusCheck.CNumber between '"+1+"' and '"+2+"' ");
				}
				if(operationItem.getCDictionaryByLpStatusCheck().getCNumber().equals("3")){
					hql.append(" and i.CDictionaryByLpStatusCheck.CNumber between '"+3+"' and '"+4+"' ");
				}
			}
			//当前登录人（我的项目）
			if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
			{
				hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
			}

			if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
			{
				hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
			}
		}else{
			//查询当前登录人审核的记录
			if(operationItem!=null){
				Set<OperItemAudit> operItemAudits=operationItem.getOperItemAudits();
				if(operItemAudits!=null){
					for(OperItemAudit op:operItemAudits){
						if(op.getUser().getUsername()==user.getUsername()){
							hql.append(" or i.id="+op.getOperationItem().getId());
						}
					}
				}
			}
		}
/*		if(cid!=null && cid>0)
		{
			hql.append(" and i.labCenter.id="+cid);
		}
	*//*	if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
		{
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			hql.append(" and i.schoolTerm.id="+termId);
		}*//*
	*//*	if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
		{
			hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
		}*//*
		if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
		{
			hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
		}
		if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
		{
			hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
		}
		if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}*/
		return ((Long) operationItemDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	
	
	/**
	 * 根据查询条件实验项目记录数量--除草稿外
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public Integer findAllOperationItemExceptDraft(OperationItem operationItem, String acno) {
		SchoolTerm s = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		int termId = s.getId();
		StringBuffer hql = new StringBuffer("select count(i) from OperationItem i where 1=1");
		if(acno!=null && !acno.equals("-1"))
		{
			hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		}
		if(operationItem.getSchoolTerm()!=null && operationItem.getSchoolTerm().getId()!=null)
		{
			hql.append(" and i.schoolTerm.id="+operationItem.getSchoolTerm().getId());
		}else {
			hql.append(" and i.schoolTerm.id="+termId);
		}
		if(operationItem.getLpName()!=null && !"".equals(operationItem.getLpName()))
		{
			hql.append(" and i.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(operationItem.getCDictionaryByLpStatusCheck()!=null && operationItem.getCDictionaryByLpStatusCheck().getId()!=null)
		{
			hql.append(" and i.CDictionaryByLpStatusCheck.id="+operationItem.getCDictionaryByLpStatusCheck().getId());
		}
		hql.append(" and i.CDictionaryByLpStatusCheck.id <>543");
		if(operationItem.getUserByLpCreateUser()!=null && operationItem.getUserByLpCreateUser().getUsername()!="")
		{
			hql.append(" and i.userByLpCreateUser.username='"+operationItem.getUserByLpCreateUser().getUsername()+"'");
		}
		if(operationItem.getUserByLpCheckUser()!=null && operationItem.getUserByLpCheckUser().getUsername()!="")
		{
			hql.append(" and i.userByLpCheckUser.username='"+operationItem.getUserByLpCheckUser().getUsername()+"'");
		}
		if(operationItem.getSchoolCourseInfo()!=null && !"".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			hql.append(" and i.schoolCourseInfo.courseNumber='"+operationItem.getSchoolCourseInfo().getCourseNumber()+"'");
		}
		return ((Long) operationItemDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * 保存实验项目
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public OperationItem saveOperationItem(OperationItem operationItem) {
		if(operationItem.getSchoolTerm()==null || operationItem.getSchoolTerm().getId()==null)
		{
			operationItem.setSchoolTerm(null);  //学期
		}
		if(operationItem.getUserByLpTeacherSpeakerId()==null || operationItem.getUserByLpTeacherSpeakerId().getUsername()==null || "".equals(operationItem.getUserByLpTeacherSpeakerId().getUsername()))
		{
			operationItem.setUserByLpTeacherSpeakerId(null);  //主讲教师
		}
		if(operationItem.getUserByLpTeacherAssistantId()==null || operationItem.getUserByLpTeacherAssistantId().getUsername()==null || "".equals(operationItem.getUserByLpTeacherAssistantId().getUsername()))
		{
			operationItem.setUserByLpTeacherAssistantId(null);  //辅导教师
		}
		if(operationItem.getLabRoom()==null || operationItem.getLabRoom().getId()==null)
		{
			operationItem.setLabRoom(null);  //实验室
		}
		if(operationItem.getSchoolCourseInfo()==null || operationItem.getSchoolCourseInfo().getCourseNumber()==null || "".equals(operationItem.getSchoolCourseInfo().getCourseNumber()))
		{
			operationItem.setSchoolCourseInfo(null);  //课程数据
		}
		if(operationItem.getSystemSubject12()==null || operationItem.getSystemSubject12().getSNumber()==null || "".equals(operationItem.getSystemSubject12().getSNumber()))
		{
			operationItem.setSystemSubject12(null);  //学科数据
		}
		if(operationItem.getSchoolMajor()==null || operationItem.getSchoolMajor().getMajorNumber()==null || "".equals(operationItem.getSchoolMajor().getMajorNumber()))
		{
			operationItem.setSchoolMajor(null);  //专业数据
		}
		
		if(operationItem.getCDictionaryByLpCategoryMain()==null || operationItem.getCDictionaryByLpCategoryMain().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryMain(null);  //实验项目类别
		}
		if(operationItem.getCDictionaryByLpCategoryApp()==null || operationItem.getCDictionaryByLpCategoryApp().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryApp(null);  //实验项目类型
		}
		if(operationItem.getCDictionaryByLpCategoryNature()==null || operationItem.getCDictionaryByLpCategoryNature().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryNature(null);  //实验项目性质
		}
		if(operationItem.getCDictionaryByLpCategoryStudent()==null || operationItem.getCDictionaryByLpCategoryStudent().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryStudent(null);  //实验者类型
		}
		if(operationItem.getCDictionaryByLpStatusChange()==null || operationItem.getCDictionaryByLpStatusChange().getId()==null)
		{
			operationItem.setCDictionaryByLpStatusChange(null);  //变动状态
		}
		if(operationItem.getCDictionaryByLpCategoryPublic()==null || operationItem.getCDictionaryByLpCategoryPublic().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryPublic(null);  //开放实验
		}
		if(operationItem.getCDictionaryByLpCategoryRewardLevel()==null || operationItem.getCDictionaryByLpCategoryRewardLevel().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryRewardLevel(null);  //获奖等级
		}
		if(operationItem.getCDictionaryByLpCategoryRequire()==null || operationItem.getCDictionaryByLpCategoryRequire().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryRequire(null);  //实验项目要求
		}
		if(operationItem.getCDictionaryByLpCategoryGuideBook()==null || operationItem.getCDictionaryByLpCategoryGuideBook().getId()==null)
		{
			operationItem.setCDictionaryByLpCategoryGuideBook(null);  //实验项目指导书
		}
		return operationItemDAO.store(operationItem);
	}

	/**
	 * 删除实验项目
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public boolean deleteOperationItem(Integer operationItemId) {
		OperationItem operationItem = findOperationItemByPrimaryKey(operationItemId);
		if(operationItem != null)
		{
			operationItemDAO.remove(operationItem);
			operationItemDAO.flush();
			return true;
		}
		
		return false;
	}

	/**
	 * 提交实验项目
	 * @author hly
	 * 2015.08.06
	 */
	@Override
	public OperationItem submitOperationItem(OperationItem operationItem, String acno) {
		OperationItem oi = new OperationItem();
		if(operationItem.getId() != null)
		{
			oi = findOperationItemByPrimaryKey(operationItem.getId());
				oi.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "2"));
			Map<String, String> params = new HashMap<>();
			String businessType = "OperationItem";
			params.put("businessUid", "-1");
			params.put("businessType", pConfig.PROJECT_NAME + businessType);
			params.put("businessAppUid", operationItem.getId().toString());
			String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
				operationItemDAO.store(oi);
				operationItemDAO.flush();
			// 提交完成后向审核人发送消息
			// 获取当前审核权限
				Map<String, String> curParams = new HashMap<>();
			curParams.put("businessType", pConfig.PROJECT_NAME + businessType);
			curParams.put("businessAppUid", operationItem.getId().toString());
			String curStr = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getCurrAuditStage", curParams);
			if("success".equals(JSONObject.parseObject(curStr).getString("status"))){
				String nextAuthName = JSONObject.parseObject(curStr).getJSONArray("data").getJSONObject(0).getString("result");
				// 获取权限所对应的用户
				List<User> auditUsers = new ArrayList<>();
				//如果是院级权限，则加学院查询条件；
				//教研室主任、实验室管理员、实验中心主任、系主任、助教、设备管理员、教师
				if(nextAuthName.equals("DEPARTMENTHEADER") || nextAuthName.equals("LABMANAGER") || nextAuthName.equals("EXCENTERDIRECTOR")
						|| nextAuthName.equals("CFO") || nextAuthName.equals("ASSISTANT") || nextAuthName.equals("EQUIPMENTADMIN")
						|| nextAuthName.equals("TEACHER")) {
					auditUsers = shareService.findUsersByQuery(nextAuthName, acno);
				}else {
					auditUsers = shareService.findUsersByAuthorityName(nextAuthName);
				}
				// 给这些用户发送消息
				for(User u: auditUsers) {
					Calendar date = Calendar.getInstance();
					Message message = new Message();
					message.setSendUser(shareService.getUserDetail().getCname());
					message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
					message.setCond(0);
					message.setTage(2);
					message.setTitle(pConfig.operationItemName + "审核");
					message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId=" + operationItem.getId() + "&cid=-1&flag=1'>审核</a>");
					message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
					message.setCreateTime(date);
					shareService.sendMsg(u, message);
				}
			}
		}
		return oi;
	}

	/**
	 * 设置项目编号
	 * @author hly
	 * 2015.08.07
	 */
	@Override
	public void saveCodeCustom(OperationItem operationItem) {
		OperationItem oi = new OperationItem();
		if(operationItem.getId()!=null)
		{
			oi = findOperationItemByPrimaryKey(operationItem.getId());
			if(operationItem.getLpCodeCustom()!=null && !"".equals(operationItem.getLpCodeCustom()))
			{
				oi.setLpCodeCustom(operationItem.getLpCodeCustom());
				
				operationItemDAO.store(oi);
				operationItemDAO.flush();
			}
		}
	}

	/**
	 * 导入整个学期的实验项目
	 * @author hly
	 * 2015.08.07
	 */
	@Override
	public void importTermOperationItem(Integer sourceTermId, Integer targetTermId, String acno) {
		if(sourceTermId!=null && targetTermId!=null && !sourceTermId.equals(targetTermId) && !acno.equals("-1"))
		{
			SchoolTerm t = new SchoolTerm();
			t.setId(targetTermId);
			
			StringBuffer hql = new StringBuffer("select i from OperationItem i where 1=1");
		    hql.append(" and i.schoolTerm.id="+sourceTermId);
		    hql.append(" and i.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		    
			//找到目标学期已经有的所有实验室项目
			List<OperationItem> operationItemOfTargetTerm=operationItemDAO.executeQuery("select i from OperationItem i  where i.schoolTerm.id="+targetTermId, 0, -1);
			String itemName=",";
			String majorName=",";
			for (OperationItem operationItem : operationItemOfTargetTerm) {
				itemName+=operationItem.getLpName()+",";
				if (operationItem.getSchoolMajor()!=null&&operationItem.getSchoolMajor().getMajorNumber()!=null) {
					majorName+=operationItem.getSchoolMajor().getMajorNumber()+",";
				}
			}
			List<OperationItem> operationItems = operationItemDAO.executeQuery(hql.toString(), 0, -1);
			for (OperationItem operationItem : operationItems)
			{
				//如果被导入实验的名字已经在该学期的名字里面，且专业号也相同，则跳出此次循环（跳过这个项目不导入）
				if (itemName.indexOf(","+operationItem.getLpName()+",")!=-1&&(operationItem.getSchoolMajor()!=null&&majorName.indexOf(","+operationItem.getSchoolMajor().getMajorNumber()+",")!=-1)) {
					System.out.println("本条数据不能被导入，因为有重名，且专业号也相同");
					continue;
				}
				else {
					OperationItem oi = new OperationItem();
					oi.copy(operationItem);
					
					//草稿状态
					oi.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));
					//学期
					oi.setSchoolTerm(t);
					//创建人=导入人
					oi.setUserByLpCreateUser(shareService.getUser());
					operationItemDAO.store(oi);
				}
			}
			operationItemDAO.flush();
		}
	}

	/**
	 * 导入选中的实验项目
	 * @author hly
	 * 2015.08.07
	 */
	@Override
	public void importOperationItem(HttpServletRequest request,Integer termId, String itemIds) {
		if(termId!=null && itemIds!=null && itemIds.length()>0)
		{
			SchoolTerm t = new SchoolTerm();
			t.setId(termId);  //导入的目标学期
			
			String[] ids = itemIds.split(",");
			try {
				for (String string : ids) 
				{
					OperationItem item = findOperationItemByPrimaryKey(Integer.parseInt(string));
					OperationItem oi = new OperationItem();
					oi.copy(item);
					oi.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));
					oi.setSchoolTerm(t);
					oi.setCreatedAt(Calendar.getInstance());
					oi.setUserByLpCreateUser(shareService.getUser());//导入人就是创建人（2015-11-12 贺子龙）
					// 清空指定审核人、项目编号
					oi.setUserByLpCheckUser(null);
					oi.setLpCodeCustom(null);
					operationItemDAO.store(oi);
					//导入日志
					String ip = shareService.getIpAddr(request);
					//保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
					//tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
					//action: 0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核 6 保存 7 审核保存 8 导入
					systemLogService.saveOperationItemLog(ip, 2, 8, Integer.parseInt(string));
				}
				operationItemDAO.flush();
			} catch (Exception e) {
				System.out.println("实验项目id类型转换错误");
			}
		}
	}

    /**
     * 导入选中学期的所有实验项目
     * @author hly
     * 2015.08.07
     * @author 贺照易修改 2018-11-12
     */
    @Override
    public void importAllOperationItem(HttpServletRequest request,Integer termId, Integer itemId) {
        if(termId!=null )
        {
            SchoolTerm t = new SchoolTerm();
            t.setId(termId);  //导入的目标学期

            List<Integer> aooid = new ArrayList<>();
            List<OperationItem> AllOperationItemlist=this.getOperationItemListBytermId(itemId);
            //获取该学期下所有的实验项目id
            for (OperationItem ol : AllOperationItemlist){
                int olid = ol.getId();
                aooid.add(olid);
            }
            try {
                for (Integer oid : aooid)
                {
                    OperationItem item = findOperationItemByPrimaryKey(oid);
                    OperationItem oi = new OperationItem();
                    oi.copy(item);
                    oi.setCDictionaryByLpStatusCheck(shareService.getCDictionaryByCategory("status_operation_item_check", "1"));
                    oi.setSchoolTerm(t);
                    oi.setCreatedAt(Calendar.getInstance());
                    oi.setUserByLpCreateUser(shareService.getUser());//导入人就是创建人（2015-11-12 贺子龙）
                    // 清空指定审核人、项目编号
                    oi.setUserByLpCheckUser(null);
                    oi.setLpCodeCustom(null);
                    operationItemDAO.store(oi);
                    //导入日志
                    String ip = shareService.getIpAddr(request);
                    //保存日志信息 saveOperationItemLog(String userIp, int tag, int action, int id)
                    //tag：子模块标志位  0-我的实验项目  1-实验项目管理  2-实验项目导入  id：项目卡的id 0-查看 -1--新建
                    //action: 0 新建 1 编辑 2 查看 3 删除 4 提交 5 审核 6 保存 7 审核保存 8 导入
                    systemLogService.saveOperationItemLog(ip, 2, 8, oid);
                }
                operationItemDAO.flush();
            } catch (Exception e) {
                System.out.println("实验项目id类型转换错误");
            }
        }
    }
    /**
     * Description  根据学期获取当前学期下所有实验项目
     * @param id
     * @return
     * @author 贺照易  2018-11-12
     */
    @Override
    public List<OperationItem> getOperationItemListBytermId(Integer id){
        String sql="select o from OperationItem o where o.schoolTerm.id ='"+ id +"'";

        return  operationItemDAO.executeQuery(sql,0,-1);
    }

	/**
	 * 获取指定实验项目的材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	@Override
	public List<OperationItemMaterialRecord> getItemMaterialRecordByItem(Integer itemId, Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select mr from OperationItemMaterialRecord mr where 1=1 ");
		if(itemId!=null && itemId>0)
		{
			hql.append(" and mr.operationItem.id="+itemId);
		}
		hql.append(" order by mr.lpmrTimeCreate desc ");
		
		return operationItemMaterialRecordDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	/**
	 * 功能：获取指定实验项目的材料使用记录不分页
	 * 作者： 贺子龙
	 * 时间：2015-09-10
	 */
	@Override
	public List<OperationItemMaterialRecord> getItemMaterialRecordByItem(Integer itemId) {
		StringBuffer hql = new StringBuffer("select mr from OperationItemMaterialRecord mr where 1=1 ");
		if(itemId!=null && itemId>0)
		{
			hql.append(" and mr.operationItem.id="+itemId);
		}
		hql.append(" order by mr.lpmrTimeCreate desc ");
		
		return operationItemMaterialRecordDAO.executeQuery(hql.toString(),0,-1);
	}

	/**
	 * 获取指定实验项目的材料使用记录数量
	 * @author hly
	 * 2015.08.10
	 */
	@Override
	public int getItemMaterialRecordByItemCount(Integer itemId) {
		StringBuffer hql = new StringBuffer("select count(mr) from OperationItemMaterialRecord mr where 1=1 ");
		if(itemId!=null && itemId>0)
		{
			hql.append(" and mr.operationItem.id="+itemId);
		}
		
		return ((Long) operationItemMaterialRecordDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * 保存实验项目材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	@Override
	public OperationItemMaterialRecord saveItemMaterialRecord(OperationItemMaterialRecord operationItemMaterialRecord) {
		if(operationItemMaterialRecord.getLpmrTimeCreate()==null)
		{
			operationItemMaterialRecord.setLpmrTimeCreate(Calendar.getInstance());
		}
		return operationItemMaterialRecordDAO.store(operationItemMaterialRecord);
	}

	/**
	 * 删除实验项目使用材料记录
	 * @author hly
	 * 2015.08.10
	 */
	@Override
	public boolean deleteItemMaterialRecord(Integer mrId) {
		OperationItemMaterialRecord mr = findItemMaterialRecordByPrimaryKey(mrId);
		if(mr!=null)
		{
		  operationItemMaterialRecordDAO.remove(mr);
		  operationItemMaterialRecordDAO.flush();
		  
		  return true;
		}
		
		return false;
	}

	/**
	 * 根据主键获取实验项目使用材料记录
	 * @author hly
	 * 2015.08.10
	 */
	@Override
	public OperationItemMaterialRecord findItemMaterialRecordByPrimaryKey(Integer lpmrId) {
		return operationItemMaterialRecordDAO.findOperationItemMaterialRecordByPrimaryKey(lpmrId);
	}
	
	/**
	 * 获取指定实验项目的设备
	 * @author hly
	 * 2015.08.19
	 */
	@Override
	public List<OperationItemDevice> getItemDeviceByItem(Integer itemId, Integer category, Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select oid from OperationItemDevice oid where 1=1 ");
		if(category!=null && !"".equals(category))
		{
		    hql.append(" and oid.CDictionary.id="+category);	
		}
		if(itemId!=null && itemId>0)
		{
			hql.append(" and oid.operationItem.id="+itemId);
		}
		
		return operationItemDeviceDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	/**
	 * 获取指定实验项目的设备数量
	 * @author hly
	 * 2015.08.19
	 */
	@Override
	public int getItemDeviceByItemCount(Integer itemId, Integer category){
		StringBuffer hql = new StringBuffer("select count(oid) from OperationItemDevice oid where 1=1 ");
		if(category!=null && !"".equals(category))
		{
		    hql.append(" and oid.CDictionary.id="+category);	
		}
		if(itemId!=null && itemId>0)
		{
			hql.append(" and oid.operationItem.id="+itemId);
		}
		
		return ((Long) operationItemDeviceDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * 批量保存实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	@Override
	public void saveItemDevice(Integer itemId, String category, String ids) {
		if(ids!=null && !"".equals(ids) && ids.length()>0)
		{
		  	String[] labRoomDeviceIdArr = ids.split(","); 
            
		  	for (String s : labRoomDeviceIdArr) 
		  	{
		  		OperationItemDevice operationItemDevice = new OperationItemDevice();
		  		try {
					SchoolDevice labRoomDevice = schoolDeviceService.findSchoolDeviceByPrimaryKey(s);
					operationItemDevice.setOperationItem(findOperationItemByPrimaryKey(itemId));
					operationItemDevice.setSchoolDevice(labRoomDevice);
					operationItemDeviceDAO.store(operationItemDevice);
				} catch (Exception e) {
					System.out.println("operationItemDevice保存失败");
				}
			}
		  	
		  	operationItemDeviceDAO.flush();
		}
	}

	/**
	 * 删除实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	@Override
	public boolean deleteItemDevice(Integer itemDeviceId) {
		OperationItemDevice operationItemDevice = operationItemDeviceDAO.findOperationItemDeviceByPrimaryKey(itemDeviceId);
		if(operationItemDevice!=null)
		{
			operationItemDeviceDAO.remove(operationItemDevice);
			operationItemDeviceDAO.flush();
		}
		return false;
	}

	/**
	 * 根据学院和角色获取用户数据
	 * @author hly
	 * 2015.08.28
	 */
	@Override
	public List<Map<String, String>> getUserByAcademyRole(String academyNumber, String role) {
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		
		StringBuffer hql = new StringBuffer("select u from User u where 1=1 ");
		if(academyNumber!=null && !"".equals(academyNumber))
		{
			hql.append(" and u.schoolAcademy.academyNumber='"+academyNumber+"' ");
		}
		if(role!=null && !"".equals(role))
		{
			hql.append(" and u.userRole='"+role+"'");
		}
		List<User> users = userDAO.executeQuery(hql.toString(), 0, -1);
		for (User user : users) 
		{
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("username", user.getUsername());
			map.put("cname", user.getCname());
			
			result.add(map);
		}
		
		return result;
	}
	
	
	/**
	 * 根据学院和角色获取用户数据
	 * @author hly
	 * 2015.08.28
	 */
	@Override
	public List<Map<String, String>> getUserByAcademyRole(String academyNumber, String role, int authorityId) {
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		
		StringBuffer hql = new StringBuffer("select u from User u where 1=1 ");
		if(academyNumber!=null && !"".equals(academyNumber))
		{
			hql.append(" and u.schoolAcademy.academyNumber='"+academyNumber+"' ");
		}
		if(role!=null && !"".equals(role))
		{
			hql.append(" and u.userRole='"+role+"'");
		}
		List<User> users = userDAO.executeQuery(hql.toString(), 0, -1);
		//判断当前登陆人是否为指定权限
		List<User> departmentHeaders=new ArrayList<User>();
		for (User user2 : users) {
			String judge=",";
			for(Authority authority:user2.getAuthorities()){
				judge = judge + "," + authority.getId() + "," ;
			}
			if(judge.indexOf(","+authorityId+",")>-1){//18--系主任
				departmentHeaders.add(user2);
			}
		}
		for (User user : departmentHeaders) 
		{
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("username", user.getUsername());
			map.put("cname", user.getCname());
			
			result.add(map);
		}
		
		return result;
	}

	/**********************************
	 * 功能：找出operationitem表中的创建者字段
	 * 作者：贺子龙
	 * 时间：2015-09-02
	 *********************************/
	
	

	@Override
	public Map<String, String> getsome() {
		Map<String, String> map=new HashMap<String, String>();
		
		String s="select u from OperationItem u where u.userByLpCreateUser.username is not null group by u.userByLpCreateUser.username";
		List<OperationItem>   list=operationItemDAO.executeQuery(s);
		if(list.size()>0){
		for(OperationItem op:list){
			map.put(op.getUserByLpCreateUser().getUsername(), op.getUserByLpCreateUser().getCname());
		}
		}
		return map;
		
	}

	/**********************************
	 * 功能：找出operationitem表中的课程段
	 * 作者：贺子龙
	 * 时间：2015-09-02
	 *********************************/
	public Map<String, String>getCourse(String acno){
		Map<String, String> map=new HashMap<String, String>();
		String s="select u from OperationItem u where u.schoolCourseInfo.courseNumber is not null";
		if(acno!=null && !acno.equals("-1")) {
			s += " and u.labCenter.schoolAcademy.academyNumber='"+ acno +"'";
		}
		s+=" group by u.schoolCourseInfo.courseNumber";
		// 去掉and (u.labRoom.id between 42 and 50)  贺子龙  2015-11-12
		List<OperationItem>   list=operationItemDAO.executeQuery(s,0,-1);
		if(list.size()>0){
		for(OperationItem op:list){
			map.put(op.getSchoolCourseInfo().getCourseNumber(), op.getSchoolCourseInfo().getCourseName());
		}
		}
		return map;
		
	}
	
	
	/**********************************
	 * 功能：找出operationitem表中的课程段(属于当前登录用户的)
	 * 作者：贺子龙
	 * 时间：2015-09-02
	 *********************************/
	public Map<String, String>getCourse(String acno,String username){
		Map<String, String> map=new HashMap<String, String>();
		String s="select u from OperationItem u where u.schoolCourseInfo.courseNumber is not null";
		s+=" and u.labCenter.schoolAcademy.academyNumber='"+ acno +"'";
		s+=" and u.userByLpCreateUser.username='"+username+"'";
		s+=" group by u.schoolCourseInfo.courseNumber";
		// 去掉and (u.labRoom.id between 42 and 50)  贺子龙  2015-11-12
		List<OperationItem>   list=operationItemDAO.executeQuery(s,0,-1);
		if(list.size()>0){
		for(OperationItem op:list){
			map.put(op.getSchoolCourseInfo().getCourseNumber(), op.getSchoolCourseInfo().getCourseName());
		}
		}
		return map;
		
	}
	/*************************************************************************************
     * 功能：查找所有的运行记录
     * 作者 ：徐文
     * 日期：2016-05-27
     ***************************************************************************************/
	@Override
	public int getOutlinelist(OperationOutline operationOutline,int currpage,int pagesize,int sid) {
		String sql="select count(o) from OperationOutline o where 1=1";
		
	 if(operationOutline.getLabOutlineName()!=null){
		   sql+=" and (o.labOutlineName like '%"+operationOutline.getLabOutlineName().trim()+"%'";
		   sql+=" or o.schoolCourseInfoByClassId.courseName  like '%"+operationOutline.getLabOutlineName().trim()+"%')";
		  }
	 

//		 LabCenter labCenter=labCenterDAO.findLabCenterById(sid);
//		   if(labCenter.getSchoolAcademy()!=null){ 
//		  sql+=" and  (o.schoolAcademy.academyNumber='"+labCenter.getSchoolAcademy().getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
//		   }
		   
//         try{
//        		return ((Long) operationOutlineDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
//     	}catch (Exception e) {
//     		
//     	return 0;
//     	} 
	 return ((Long) operationOutlineDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/*************************************************************************************
     * 功能：查找所有的运行记录分页
     * 作者 ：徐文
     * 日期：2016-05-27
     ***************************************************************************************/
	/*@Override
	public List<OperationOutline> getOutlinelistpage(
			OperationOutline operationOutline, int currpage, int pagesize,int sid) {
		String sql="select o from OperationOutline o where 1=1 ";
		//sql+="and o.schoolAcademy.academyNumber like  '%"+labCenterDAO.findLabCenterById(sid).getSchoolAcademy().getAcademyNumber()+"%'";
		 if(operationOutline.getLabOutlineName()!=null){
		   sql+=" and (o.labOutlineName like '%"+operationOutline.getLabOutlineName().trim()+"%'";
		   sql+=" or o.schoolCourseInfoByClassId.courseName  like '%"+operationOutline.getLabOutlineName().trim()+"%'";
		   sql+=" or o.schoolCourseInfoByClassId.courseNumber  like '%"+operationOutline.getLabOutlineName().trim()+"%')";
		   
		  
		  }
		 Integer centerId=sid;
		 if(sid != -1){
			 sql +=" and o.labCenter.id="+centerId.toString();
		 }
//		 if(operationOutline.getOperationItems() !=null){
//			 sql+=" and o.operationItem.lpName  '%"+operationOutline.getOperationItems() +"%' ";
//		 }
//		  LabCenter labCenter=labCenterDAO.findLabCenterById(sid);
//		   if(labCenter.getSchoolAcademy()!=null){ 
//		  sql+=" and  (o.schoolAcademy.academyNumber='"+labCenter.getSchoolAcademy().getAcademyNumber()+"' or o.user.username='"+shareService.getUser().getUsername()+"')";
//		   }
		 System.out.println(sql);
		 Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		 if(operationOutline != null && operationOutline.getSchoolTerm() != null 
				 && operationOutline.getSchoolTerm().getId() != null && !operationOutline.getSchoolTerm().getId().equals("")){
			 	termId = operationOutline.getSchoolTerm().getId();
		 }
		 if(termId != null){
			 sql += " and schoolTerm.id ="+termId;
		 }
		return  operationOutlineDAO.executeQuery(sql,(currpage-1)*pagesize,pagesize);
	}*/
	@Override
	public List<OperationOutline> getOutlinelistpage(
			OperationOutline operationOutline, int currpage, int pagesize,String acno) {
		String sql="select o from OperationOutline o where 1=1 ";
		//主页结果 教师只显示自己所属课程大纲； 张秦龙
		String username=shareService.getUser().getCname();
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1  ||
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){

		}else{
			//大纲内选择的教师和创建者均可看到相关大纲
			sql+=" and (o.user.cname='"+username+"' or o.extraTeacher like'%"+username+"%') ";      //extraTeacher
		}
		//根据大纲名称查询
		if(!EmptyUtil.isStringEmpty(operationOutline.getLabOutlineName())){

			sql+=" and (o.labOutlineName like '%"+operationOutline.getLabOutlineName().trim()+"%'";
			sql+=" or o.schoolCourseInfoByClassId.courseName  like '%"+operationOutline.getLabOutlineName().trim()+"%'";
			sql+=" or o.schoolCourseInfoByClassId.courseNumber  like '%"+operationOutline.getLabOutlineName().trim()+"%')";
			//sql+="and sc.userByTeacher.username ='"+username+"'";
		}
		//所属中心
		if(!acno.equals("-1")){
			sql +=" and o.schoolAcademy.academyNumber = '"+ acno +"'";
		}
			Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(operationOutline.getSchoolTerm() != null && operationOutline.getSchoolTerm().getId() != null){
			termId = operationOutline.getSchoolTerm().getId();
		}
		sql += " and schoolTerm.id ="+termId;//所属学期
		List<OperationOutline> operationOutlines = operationOutlineDAO.executeQuery(sql,(currpage-1)*pagesize,pagesize);
		ArrayList<OperationOutline> newOperationOutlines = new ArrayList<OperationOutline>();

		//拼接教师名单
		if(operationOutlines != null && operationOutlines.size() > 0) {
			for(OperationOutline opOutline : operationOutlines) {
				Set<User> operationUsers = opOutline.getOperationUser();
				if(operationUsers != null && operationUsers.size() > 0) {
					String teachers = "";
					for(User user : operationUsers) {
						teachers += user.getCname() + " ";
					}
					if(opOutline.getExtraTeacher() != null) {
						if(teachers.contains(opOutline.getExtraTeacher())){
							opOutline.setExtraTeacher(teachers);
						}else{
							if(opOutline.getExtraTeacher().contains(teachers)){

							}else{
								opOutline.setExtraTeacher(teachers+opOutline.getExtraTeacher());
							}
						}



					}else {
						opOutline.setExtraTeacher(teachers);
					}
				}
				newOperationOutlines.add(opOutline);
			}
		}
		return newOperationOutlines;
	}
	/***************************************************************************************
     * 功能 ：查找大纲
     * 作者：徐文
     * 日期：2016-05-27
     **************************************************************************************/
	@Override
	public OperationOutline getoperationoutlineinfor(int idkey) {
		
		return operationOutlineDAO.findOperationOutlineById(idkey);
	}
	/***********************************************************************************
     * 功能 ： 查找未被大纲使用的项目卡项目卡数
     * 作者：徐文
     * 日期：2016-05-27
     ***********************************************************************************/
	@Override
	public List<OperationItem> getoperationItemlist() {
		String sql="select o from OperationItem o where 1=1 and o.id!=0 ";
		 
		return  operationItemDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************
     * 功能 ：查找所有课程info
     * 作者：徐文
     * 日期：2016-05-27
     ***********************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getschoolcouresMap(String acno) {
		Map attributesMap=new HashMap();
		String sql="select s from SchoolCourseInfo s left join s.schoolCourses sc where 1=1 ";

		List<SchoolCourseInfo> d=	schoolCourseInfoDAO.executeQuery(sql,1,-1);
		for(SchoolCourseInfo schoolCourseInfo: d)
			attributesMap.put(schoolCourseInfo.getCourseNumber(), schoolCourseInfo.getCourseName()+" "+schoolCourseInfo.getCourseNumber());
		return attributesMap;
	}
	/***********************************************************************************
     * 功能 ： 查找所在专业
     * 作者：徐文
     * 日期：2016-05-27
     ***********************************************************************************/
	@Override
	public List<SchoolMajor> getschoolmajerSet(String acno) {
		 String sql="select s from  SchoolMajor s where 1=1 ";
		 List<SchoolMajor> systemMajors= schoolMajorDAO.executeQuery(sql,0,-1);
		 return systemMajors;
	}
	/***********************************************************************************
     * 功能 ：查学分
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getcoperationscareMap() {
		Map coperationOutlineCredit=new HashMap();
		for (COperationOutlineCredit it : cOperationOutlineCreditDAO.findAllCOperationOutlineCredits()) {
			coperationOutlineCredit.put(it.getId(), it.getCredit());
		}
		return coperationOutlineCredit;
	}
	/***********************************************************************************
     * 功能 ：查开课学院
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	@Override
	public Map getoperationstartschooleMap(String acno) {
		 Map academy=new HashMap();
		 String sql ="select s from SchoolAcademy s  where 1=1 ";
		 if(acno != null && !acno.equals("-1")) {
			 sql += "and s.academyNumber =  '" + acno + "'";
		 }List<SchoolAcademy> d = SchoolAcademyDAO.executeQuery(sql,0,-1);
		 for (SchoolAcademy schoolAcademy : d) {
			 academy.put(schoolAcademy.getAcademyNumber(), schoolAcademy.getAcademyName());
		}
		return academy;
	}
	/***********************************************************************************
     * 功能 ：查找课程性质
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	@Override
	public List<CDictionary> getcommencementnatureSet() {
		String sql="select s from  CDictionary s where s.CCategory ='c_operation_outline_property'";
		List<CDictionary> commencementnaturemap=cDictionaryDAO.executeQuery(sql,0,-1);
		return commencementnaturemap;
	}
	/*********************************************************************************
	 * 功能:保存大纲内容
	 * 作者：徐文
	 * 日期：2016-05-30
	 ********************************************************************************/
	@Override
	public OperationOutline saveoperationoutline(OperationOutline operationOutline,//commencementnaturemap是课程性质，projectitrms是item，schoolMajors是面向专业
			String[] schoolMajors,String[] commencementnaturemap,String[] projectitrms) {
		operationOutline.setSchoolCourseInfoByFollowUpCourses(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(operationOutline.getSchoolCourseInfoByFollowUpCourses().getCourseNumber()));
		operationOutline.setSchoolCourseInfoByFirstCourses(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(operationOutline.getSchoolCourseInfoByFirstCourses().getCourseNumber()));
		operationOutline.setSchoolCourseInfoByClassId(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(operationOutline.getSchoolCourseInfoByClassId().getCourseNumber()));
		
	     operationOutline.setUser(shareService.getUser());
		OperationOutline o=  operationOutlineDAO.store(operationOutline);
		     if(schoolMajors!=null ){
		       this.saveSystemMajor(o.getId(),schoolMajors);
		     }
		     if(commencementnaturemap!=null ){
	         	this.saveoperationoutlineproperty(o.getId(),commencementnaturemap);
		     }
		     if(projectitrms!=null && !projectitrms.equals("") && projectitrms.length>0  ){
		      this.saveoperationoutlineitems(o.getId(),projectitrms);
		      }
		     return o;
	}
	
	   /***********************************************************************************
	    * 功能 ：保存major
	    * 作者：徐文
	    * 日期：2016-05-31
	    ***********************************************************************************/
		public void saveSystemMajor(int outlineId,String[] systemmajor ){
			OperationOutline outline=operationOutlineDAO.findOperationOutlineById(outlineId);
			Set<SchoolMajor> historymajor = outline.getSchoolMajors();
			if (historymajor!=null && historymajor.size()>0) {
				for (SchoolMajor operationMajor : historymajor) {
					historymajor.remove(operationMajor);
				}
			}
			 if (systemmajor!=null ) {
				if (systemmajor!=null && systemmajor.length>0) {
					for (String majorString : systemmajor) {
						SchoolMajor major = schoolMajorDAO.findSchoolMajorByPrimaryKey(majorString);
						historymajor.add(major);
					}
				}
			}
			outline.setSchoolMajors(historymajor);
			operationOutlineDAO.store(outline);
			operationOutlineDAO.flush();	
		   }
   	/***********************************************************************************
     * 功能 ：保存课程性质
     * 作者：贺子龙
     * 日期：2016-05-31
     ***********************************************************************************/
	public void saveoperationoutlineproperty(int outlineId,String[] courseproperty ){
		/**
		* 1、把jsp传来的字符串转化成字符数组；
		* 2、轮询字符数组，找到对应的课程性质
		* 3、将所得到的课程性质集合赋值给OperationOutline对象
		*/
		OperationOutline outline=operationOutlineDAO.findOperationOutlineById(outlineId);
		Set<CDictionary> historyproperty=outline.getCDictionarys();
		if (historyproperty!=null && historyproperty.size()>0) {
			for (CDictionary cDictionary : historyproperty) {
				historyproperty.remove(cDictionary);
			}
		}
		if (courseproperty!=null) {
			if (courseproperty!=null && courseproperty.length>0) {
				for (String creditString : courseproperty) {
					//int creditId=Integer.parseInt(creditString);
					CDictionary property=cDictionaryDAO.findCDictionaryByPrimaryKey(Integer.parseInt(creditString));
					historyproperty.add(property);
				}
			}
		}
		outline.setCDictionarys(historyproperty);
		operationOutlineDAO.store(outline);
	   }
	/***********************************************************************************
     * 功能 ：保存item
     * 作者：徐文
     * 日期：2016-05-31
     ***********************************************************************************/
	public void saveoperationoutlineitems(int outlineId,String[] courseitems ){
		OperationOutline outline=operationOutlineDAO.findOperationOutlineById(outlineId);
			Set<OperationItem> historyitem=outline.getOperationItems();
			System.out.println(courseitems);
		
		if (historyitem!=null && historyitem.size()>0) {
			for (OperationItem operationItem : historyitem) {
				historyitem.remove(operationItem);
			}
		}
			if (courseitems!=null && courseitems.length>0 ) {
				for (String creditString : courseitems) {
					//int itemid=Integer.parseInt(creditString);
					if(creditString != null&& creditString!=""){
					OperationItem item=operationItemDAO.findOperationItemByPrimaryKey(Integer.parseInt(creditString));
					item.setOperationOutline(outline);
					operationItemDAO.store(item);
				}
				}
			}
		
		operationOutlineDAO.store(outline);
	   }
	/*********************************************************************************
	 * 功能:实验室大纲多文件上传
	 * 作者：徐文
	 * 日期：2016-06-01
	 ********************************************************************************/
	@SuppressWarnings({ "rawtypes" })
	@Override
	 public String   uploaddnolinedocment(HttpServletRequest request, HttpServletResponse response, Integer id){
		String  listid="";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator"); 
		 Map files = multipartRequest.getFileMap(); 
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String suiji = UUID.randomUUID().toString();
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"operation"+sep+suiji;
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
			  CommonDocument conn=new CommonDocument();
			  conn.setDocumentName(fileTrueName);
			  conn.setDocumentUrl(fileDir + sep + fileTrueName);
			  if(id!=null){
			  conn.setOperationOutline(operationOutlineDAO.findOperationOutlineById(id));
			  }
			 CommonDocument ids= commonDocumentDAO.store(conn);
			 listid="<tr id='s_"+ids.getId()+"'><td>"+ids.getDocumentName()+"</td><td><input type='button' onclick='delectuploaddocment("+ids.getId()+")' value='删除'/> </td></tr>"; 
		     
		  } 
		  
		}
		
		return listid;	 
	 }
	/*********************************************************************************
	  * 功能:实验室item搜索
	  * 作者：徐文
	  * 日期：2016-06-01
	  ********************************************************************************/
	 public  List<OperationItem> getitem(String a ){
		 String sql="select s from OperationItem s where 1=1  and s.id!=0 ";
			if(a!=null && a!=""){
				sql+=" and (s.lpName  like '%"+a+"%'";
			}
			if(a!=null && a!=""){
				sql+=" or s.lpCodeCustom like '%"+a+"%')";
			}  
		
		return operationItemDAO.executeQuery(sql,0,-1);
	 }
	 /****************************************************************************
	  * 功能：删除实验室大纲
	  * 作者：徐文
	  * 日期：2016-06-01
	  ****************************************************************************/
	 public void  delectloutline(int idkey){
		 OperationOutline s=operationOutlineDAO.findOperationOutlineById(idkey);
			
			operationOutlineDAO.remove(s);
	 }
	 
	 /*********************************************************************************
		 *@description:实验室项目多文件上传
		 *@author: 郑昕茹
		 *@date：2016-11-09
		 ********************************************************************************/
		@SuppressWarnings({ "rawtypes" })
		@Override
		 public String uploadItemdocument(HttpServletRequest request, HttpServletResponse response, Integer id, int type){
			String  listid="";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
			 String sep = System.getProperty("file.separator"); 
			 Map files = multipartRequest.getFileMap(); 
			 Iterator fileNames = multipartRequest.getFileNames();
			 boolean flag =false; 
			 String suiji = UUID.randomUUID().toString();
			 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"operation"+sep+suiji;
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
				  CommonDocument conn=new CommonDocument();
				  conn.setDocumentName(fileTrueName);
				  conn.setDocumentUrl(fileDir + sep + fileTrueName);
				  if(id!=null){
				  conn.setOperationItem(operationItemDAO.findOperationItemById(id));
				  }
				  if(type==1) {
					  conn.setFlag(101); // 项目简介
				  }else {
					  conn.setFlag(102); // 实验指导书
				  }
				 CommonDocument ids= commonDocumentDAO.store(conn);
				 listid="<tr id='s_"+ids.getId()+"'><td>"+ids.getDocumentName()+"</td><td><input type='button' onclick='delectuploaddocment("+ids.getId()+")' value='删除'/> </td></tr>"; 
			  } 
			}
			return listid;	 
		 }
		
		@Override
		public List<OperItemAudit> findAllOperItemAuditsByOperItemId(int Id){
			String sql="select l from OperItemAudit l where 1=1";
			sql+=" and l.operationItem.id="+Id;
			return operItemAuditDAO.executeQuery(sql,0,-1);
		}
		
		/*********************************************************************************
		 *@description:软件审核记录
		 *@author: 张愉
		 *@date：2017-10-10
		 ********************************************************************************/
		@Override
		public List<SoftwareReserveAudit> findAllSoftwareReserveAuditBysoftwareReserveId(int Id){
			String sql="select l from SoftwareReserveAudit l where 1=1";
			sql+=" and l.softwareReserve.id="+Id;
			sql+=" and (l.status=0 or l.status=1)";
			return softwareReserveAuditDAO.executeQuery(sql,0,-1);
		}
		/*********************************************************************************
		 *@description:实训项目审核记录
		 *@author: 张愉
		 *@date：2017-10-12
		 ********************************************************************************/
		@Override
		public List<OperItemAudit> findAllOperaItemAuditByoperaItemId(int Id){
			String sql="select l from OperItemAudit l where 1=1";
			sql+=" and l.operationItem.id="+Id;
			sql+=" and (l.status=0 or l.status=1)";
			return operItemAuditDAO.executeQuery(sql,0,-1);
		}
		/****************************************************************************
			 *Description：查找所有实训项目审核信息
			 *
			 *@author：张愉
			 *@date:2017-10-10
			 ****************************************************************************/
			@Override
			public List<OperItemAudit> findAllOperItemAuditBy(OperationItem oparetionItem,String username) {
				String sql="select l from OperItemAudit l where 1=1";
				sql+=" and l.operationItem.id="+oparetionItem.getId();
				sql+=" and l.status=3";
				sql+=" and l.user.username='"+username+"'";
			return operItemAuditDAO.executeQuery(sql);		
			}
			/****************************************************************************
			 *Description：查找所有实训项目审核信息
			 *
			 *@author：张愉
			 *@date:2017-10-10
			 ****************************************************************************/
			@Override
			public List<OperItemAudit> findAllOperItemAuditsForAudit() {
				String sql="select l from OperItemAudit l where 1=1";
				//实训部主任可以看到全部
				Set<Authority> aus=shareService.getUser().getAuthorities();
				int flag=0;
				for(Authority a:aus){
					if(a.getId()==3){
						flag=1;break;
					}
				}
				if(flag==1){
				sql+=" and l.status=3";
				}else{
				sql+=" and l.status=3";
				sql+=" and l.user.username='"+shareService.getUser().getUsername()+"'";
				}
			return operItemAuditDAO.executeQuery(sql);		
			}
			
			/****************************************************************************
			 *Description：查找所有实训项目审核信息
			 *
			 *@author：张愉
			 *@date:2017-10-10
			 ****************************************************************************/
			@Override
			public List<OperItemAudit> findAllOperItemAuditsByUsername() {
				String sql="select l from OperItemAudit l where 1=1";
				sql+=" and l.user.username='"+shareService.getUser().getUsername()+"'";
				sql+=" GROUP BY l.operationItem.id";
			return operItemAuditDAO.executeQuery(sql);		
			}
			
		/************************************************************************
		 *@Description:课题审核页面
		 *@Author:孙虎
		 *@Date:2018/5/30
		 ************************************************************************/
		@Override
		public List<OperationItem> findAllOperItemAuditBy2(OperationItem oparetionItem,String academyNumber,
														   int currpage, int pageSize){
			String sql="select l from OperationItem l where 1=1";
			sql+=" and l.CDictionaryByLpStatusCheck.CNumber = '"+2+"' ";
			//查询条件
			if(oparetionItem.getSchoolTerm()!=null && oparetionItem.getSchoolTerm().getId()!=null)
			{
				sql+=" and l.schoolTerm.id="+oparetionItem.getSchoolTerm().getId();
			}else {
				sql += " and l.schoolTerm.id=" + shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
			}
			if(oparetionItem.getLpName()!=null && !"".equals(oparetionItem.getLpName()))
			{
				sql+=" and l.lpName like '%"+oparetionItem.getLpName()+"%'";
			}
			if(oparetionItem.getSchoolCourseInfo()!=null && !"".equals(oparetionItem.getSchoolCourseInfo().getCourseNumber()))
			{
				sql+=" and l.schoolCourseInfo.courseNumber='"+oparetionItem.getSchoolCourseInfo().getCourseNumber()+"'";
			}
			return operationItemDAO.executeQuery(sql, (currpage-1)*pageSize, pageSize);
		}
	/************************************************************************
	 *@Description:保存教研室主任审核
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	@Override
	public Integer saveDepartmentHeaderAudit(OperationItem operationItem,String auditUsername,Integer audit,String mem,Integer flag){
		//当前时间
		Calendar date=Calendar.getInstance();
		OperItemAudit operItemAudit = this.findAllOperItemAuditByOId(operationItem).get(0);
		operItemAudit.setOperationItem(operationItem);
		operItemAudit.setUser(userDAO.findUserByPrimaryKey(auditUsername));
		operItemAudit.setCreateDate(date);
		operItemAudit.setMem(mem);
		//如果通过
		if (audit==1){
			operItemAudit.setResult("教研室主任审核通过");
			operItemAudit.setStatus(1);
		}else if (audit==0){
			operItemAudit.setResult("教研室主任审核未通过");
			operItemAudit.setStatus(2);
		}
		operItemAuditDAO.store(operItemAudit);
		//所选设备涉及的实训室管理员
		List<User> labManagers = new ArrayList<User>();
		List<OperationItemDevice> operationItemDevices = new ArrayList<OperationItemDevice>(operationItem.getOperationItemDevices());
		for (OperationItemDevice operationItemDevice:operationItemDevices){
			LabRoom labRoom = new ArrayList<LabRoomDevice>(operationItemDevice.getSchoolDevice().getLabRoomDevices()).get(0).getLabRoom();
			List<LabRoomAdmin> labRoomAdmins = new ArrayList<LabRoomAdmin>(labRoom.getLabRoomAdmins());
			for (LabRoomAdmin l:labRoomAdmins) {
				labManagers.add(l.getUser());
			}
		}
		//如果审核通过，发消息给下一阶段的审核人
		if (audit==1){
			for (User user:labManagers){
				Message message= new Message();
				message.setSendUser(shareService.getUserDetail().getCname());
				message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
				message.setTage(2);
				message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
				message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId="+operationItem.getId()+"&cid=-1&flag=2'>审核</a>");
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setUsername(user.getUsername());
				message=messageDAO.store(message);
				messageDAO.flush();
				flag += 1 ;
			}
			operationItem.setAuditStage(2);
			operationItemDAO.store(operationItem);
		}else if(audit==0){//如果未通过
			//将消息发回给申请人
			Message message= new Message();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setTage(3);
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
			message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId=1688&cid=-1&flag=4'>查看</a>");
			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
			message.setCreateTime(date);
			message.setUsername(operationItem.getUserByLpCreateUser().getUsername());
			message=messageDAO.store(message);
			messageDAO.flush();
			flag = 4;
			operationItem.setCDictionaryByLpStatusCheck(cDictionaryDAO.findCDictionaryById(546));
			operationItem.setAuditStage(4);
			operationItemDAO.store(operationItem);
		}
		return flag;
	}
	/************************************************************************
	 *@Description:查询课程库审核情况
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	@Override
	public List<OperItemAudit> findAllOperItemAuditByOId(OperationItem operationItem){
		String sql = "select o from OperItemAudit o where o.operationItem.id = "+operationItem.getId();
		Query query = entityManager.createQuery(sql);
		List<OperItemAudit> operItemAuditList = query.getResultList();
		return operItemAuditList;
	}
	/************************************************************************
	 *@Description:保存实训室管理员审核
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	@Override
	public Integer saveLabRoomManagerAudit(OperationItem operationItem,String auditUsername,Integer audit,String mem,Integer flag){
		//当前时间
		Calendar date=Calendar.getInstance();
		OperItemAudit operItemAudit = new OperItemAudit();
		operItemAudit.setOperationItem(operationItem);
		operItemAudit.setUser(userDAO.findUserByPrimaryKey(auditUsername));
		operItemAudit.setCreateDate(date);
		operItemAudit.setMem(mem);
		//如果通过
		if (audit==1){
			operItemAudit.setResult("实训室管理员审核通过");
			operItemAudit.setStatus(1);
		}else if (audit==0){
			operItemAudit.setResult("实训室管理员审核未通过");
			operItemAudit.setStatus(2);
		}
		operItemAuditDAO.store(operItemAudit);
		//如果审核通过，发消息给下一阶段的审核人
		if (audit==1){
			//获取中心主任
			List<User> labCenterManager = shareService.findAuthByAuthNameAndAcademy("EXCENTERDIRECTOR",operationItem.getUserByLpCreateUser().getSchoolAcademy().getAcademyNumber());
			for(User user: labCenterManager){
				Message message= new Message();
				message.setSendUser(shareService.getUserDetail().getCname());
				message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
				message.setTage(2);
				message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
				message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId="+operationItem.getId()+"&cid=-1&flag=3'>审核</a>");
				message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
				message.setCreateTime(date);
				message.setUsername(user.getUsername());
				message=messageDAO.store(message);
				messageDAO.flush();
				flag +=1;
			}
			operationItem.setAuditStage(3);
			operationItemDAO.store(operationItem);
		}else if(audit==0){//如果未通过
			//将消息发回给申请人
			Message message= new Message();
			message.setSendUser(shareService.getUserDetail().getCname());
			message.setTage(3);
			message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
			message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
			message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId=1688&cid=-1&flag=4'>查看</a>");
			message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
			message.setCreateTime(date);
			message.setUsername(operationItem.getUserByLpCreateUser().getUsername());
			message=messageDAO.store(message);
			messageDAO.flush();
			flag = 4;
			operationItem.setCDictionaryByLpStatusCheck(cDictionaryDAO.findCDictionaryById(546));
			operationItem.setAuditStage(4);
			operationItemDAO.store(operationItem);
		}

		return flag;
	}
	/************************************************************************
	 *@Description:保存实训室中心主任审核
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	@Override
	public Integer saveEXCenterDirectorAudit(OperationItem operationItem,String auditUsername,Integer audit,String mem,Integer flag){
		//当前时间
		Calendar date=Calendar.getInstance();
		OperItemAudit operItemAudit = new OperItemAudit();
		operItemAudit.setOperationItem(operationItem);
		operItemAudit.setUser(userDAO.findUserByPrimaryKey(auditUsername));
		operItemAudit.setCreateDate(date);

		operItemAudit.setMem(mem);
		//如果通过
		if (audit==1){
			operItemAudit.setResult("实训室中心主任审核通过");
			operItemAudit.setStatus(1);
		}else if (audit==0){
			operItemAudit.setResult("实训室中心主任审核未通过");
			operItemAudit.setStatus(2);
		}
		operItemAuditDAO.store(operItemAudit);
		//将消息发回给申请人
		Message message= new Message();
		message.setSendUser(shareService.getUserDetail().getCname());
		message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
		message.setTitle(CommonConstantInterface.STR_OPERATIONITEM_TITLE);
		message.setContent("<a onclick='changeMessage(this)' href='../operation/operationItemAndAudit?oId=1688&cid=-1&flag=4'>查看</a>");
		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
		message.setCreateTime(date);
		message.setUsername(operationItem.getUserByLpCreateUser().getUsername());
		if (audit==1){//如果审核通过
			message.setTage(4);
			operationItem.setCDictionaryByLpStatusCheck(cDictionaryDAO.findCDictionaryById(545));
			operationItem.setAuditStage(4);
			operationItemDAO.store(operationItem);
		}else if (audit==0){//如果未通过
			message.setTage(3);
			operationItem.setCDictionaryByLpStatusCheck(cDictionaryDAO.findCDictionaryById(546));
			operationItem.setAuditStage(4);
			operationItemDAO.store(operationItem);
		}
		message=messageDAO.store(message);
		messageDAO.flush();
		flag += 1;
		return flag;
	}

	@Override
	public OperationItem getopertioniteminfor(int idkey) {

		return operationItemDAO.findOperationItemById(idkey);
	}

	/************************************************************************
	 *@Description:通过school_course_info和查询条件查找operation_item
	 *@Author:杨新蔚
	 *@Date:2018年7月30日
	 ************************************************************************/
	@Override
	public List<OperationItem> findOperationItemByCourseNumber(OperationItem operationItem, String courseNumber, int currpage, int pageSize) {
		StringBuffer hql=new StringBuffer("select o from OperationItem o where 1=1");
		if(!EmptyUtil.isObjectEmpty(operationItem)&&operationItem.getLpName()!=null&&!"".equals(operationItem.getLpName())){
			hql.append(" and o.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(courseNumber!=null&&!"".equals(courseNumber)){
			hql.append(" and o.schoolCourseInfo.courseNumber ='"+courseNumber+"'");
		}
		return operationItemDAO.executeQuery(hql.toString(),(currpage-1)*pageSize, pageSize);
	}

	/************************************************************************
	 *@Description:通过school_course_info和查询条件查找operation_item数量
	 *@Author:杨新蔚
	 *@Date:2018年7月30日
	 ************************************************************************/
	@Override
	public int countOperationItemByCourseNumber(OperationItem operationItem, String courseNumber) {
		StringBuffer hql=new StringBuffer("select count(o) from OperationItem o where 1=1");
		if(!EmptyUtil.isObjectEmpty(operationItem)&&operationItem.getLpName()!=null&&!"".equals(operationItem.getLpName())){
			hql.append(" and o.lpName like '%"+operationItem.getLpName()+"%'");
		}
		if(courseNumber!=null&&!"".equals(courseNumber)){
			hql.append(" and o.schoolCourseInfo.courseNumber ='"+courseNumber+"'");
		}
		return ((Long)operationItemDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/***********************************************************************************
	 * 功能 ： 通过class_id查找实验大纲
	 * 作者：戴昊宇
	 * 日期：2017-09-20
	 ***********************************************************************************/
	@Override
	public List<OperationOutline> getoperationOutlinebyclass(String courseNumber) {
		String sql="select o from OperationOutline o where 1=1 ";
		sql +=" and o.schoolCourseInfoByClassId.courseNumber like '%"+courseNumber+"%'";
		return  operationOutlineDAO.executeQuery(sql,0,-1);
	}

	/********************************************************************************
	 * Description:实验项目{根据课程编号查找所有审核通过的实验项目卡}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public List<OperationItem> findOperationItemByCourseNumber(String courseNumber){
		String sql = "select c from OperationItem c left join c.schoolCourseInfo s left join s.schoolCourses sc where 1=1";
		sql+=" and c.schoolCourseInfo.courseNumber like '"+courseNumber+"'";
		sql+=" and c.CDictionaryByLpStatusCheck.CCategory = 'status_operation_item_check' " +
				"and c.CDictionaryByLpStatusCheck.CNumber = '3'";
		//sql +=" and c.schoolTerm.id = sc.schoolTerm.id";
		sql += " group by c.id";
		List<OperationItem> listOperationItem = operationItemDAO.executeQuery(sql, 0, 20);
		return listOperationItem;
	}

	/**
	 * Description 实验大纲总计
	 *
	 * @author 陈乐为
	 * @date 2017-9-30
	 */
	@Override
	public int getAllOutlineCount(OperationOutline operationOutline, String acno) {
		StringBuffer hql = new StringBuffer("select count(o) from OperationOutline o where 1=1 ");
		//主页数量 教师只显示自己所属课程大纲； 张秦龙
		String username = shareService.getUser().getCname();
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1  ||
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){

		}else{
			hql.append(" and o.extraTeacher ='"+username+"' ");
		}
		//根据大纲名称查询
		if(!EmptyUtil.isStringEmpty(operationOutline.getLabOutlineName())){
			hql.append(" and (o.labOutlineName like '%"+operationOutline.getLabOutlineName().trim()+"%'");
			hql.append(" or o.schoolCourseInfoByClassId.courseName  like '%"+operationOutline.getLabOutlineName().trim()+"%'");
			hql.append(" or o.schoolCourseInfoByClassId.courseNumber  like '%"+operationOutline.getLabOutlineName().trim()+"%')");
		}
		//所属学院
		if(!acno.equals("-1")){
			hql.append(" and o.schoolAcademy.academyNumber = '"+ acno +"'");
		}
		Integer termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if(operationOutline.getSchoolTerm() != null && operationOutline.getSchoolTerm().getId() != null){
			termId = operationOutline.getSchoolTerm().getId();
		}
		hql.append(" and schoolTerm.id ="+termId);//所属学期

		return ((Long) operationOutlineDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * 找到当前outline的所有moutlinecourse的记录
	 * @author 陆少凯
	 * 2017.10.06
	 */
	public List<MOutlineCourse> findMoutlineCourseByOutlineId(int outline_id){
		String sql="select m from MOutlineCourse m where m.operationOutline.id ="+outline_id+"";
		return mOutlineCourseDAO.executeQuery(sql);
	}
	/**
	 * 找到当前outline的所有operationoutlinecourse的记录
	 * @author 陆少凯
	 * 2017.10.06
	 */
	public List<OperationOutlineCourse> findOperationOutlineCourseByOutlineId(int outline_id){
		String sql="select m from OperationOutlineCourse m where m.operationOutline.id ="+outline_id+"";
		return operationOutlineCourseDAO.executeQuery(sql);
	}

	/***********************************************************************************
	 * 功能 ：查找所有课程info
	 * 作者：吴奇臻
	 * 日期：2018-06-29
	 ***********************************************************************************/
	public   List<SchoolCourseInfo>  getSchoolCourseInfo(){
		User user=shareService.getUser();
		String username = user.getUsername();
		String sql= "select  distinct s from SchoolCourseInfo s left join s.schoolCourses sc where 1=1 ";
		sql +="and sc.schoolCourseInfo.courseNumber=s.courseNumber ";
		//sql +="and sc.schoolAcademy.academyNumber like  '%"+labCenterDAO.findLabCenterById(sid).getSchoolAcademy().getAcademyNumber()+"%'";
		//当类型是教师不是超管
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1  ||
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){

		}else{
			sql +="and sc.userByTeacher.username ='"+username+"'";
		}
		return	schoolCourseInfoDAO.executeQuery(sql,0,-1);
	}
	/***********************************************************************************
	 * 功能 ：查找所有课程info
	 * 作者：徐文
	 * 日期：2016-05-27
	 ***********************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getschoolcouresMap1() {
		Map attributesMap=new HashMap();
		User user=shareService.getUser();
		String username = user.getUsername();
		String sql= "select  s from SchoolCourseInfo s left join s.schoolCourses sc where 1=1 ";
		sql +="and sc.schoolCourseInfo.courseNumber=s.courseNumber ";
		//当类型是教师不是超管
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1  ||
				SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){

		}else{
			sql +="and sc.userByTeacher.username ='"+username+"'";
		}

		List<SchoolCourseInfo> d=	schoolCourseInfoDAO.executeQuery(sql,0,-1);
		for(SchoolCourseInfo schoolCourseInfo: d)
			attributesMap.put(schoolCourseInfo.getCourseName(), schoolCourseInfo.getCourseName());
		return attributesMap;
	}

	/*********************************************************************************
	 * 功能:保存大纲内容
	 * 作者：徐文
	 * 日期：2016-05-30
	 ********************************************************************************/
	@Override
	public OperationOutline saveoperationoutline(OperationOutline operationOutline,//commencementnaturemap是课程性质，projectitrms是item，schoolMajors是面向专业
												 String[] schoolMajors,String[] commencementnaturemap,String[] projectitrms,String[] operationOutlineTeacher) {
		//operationOutline.setSchoolCourseInfoByFollowUpCourses(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(operationOutline.getSchoolCourseInfoByFollowUpCourses().getCourseNumber()));
		//operationOutline.setSchoolCourseInfoByFirstCourses(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(operationOutline.getSchoolCourseInfoByFirstCourses().getCourseNumber()));
		operationOutline.setSchoolCourseInfoByClassId(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(operationOutline.getSchoolCourseInfoByClassId().getCourseNumber()));
		//大纲名称的生成形式，直接由课程代码生成大纲名称，形式如下：课程代码-大纲。如：
		//法医病理学 MED130070-大纲；
		operationOutline.setLabOutlineName(operationOutline.getSchoolCourseInfoByClassId().getCourseName()+" "+operationOutline.getSchoolCourseInfoByClassId().getCourseNumber()+"-大纲");

		//大纲名称=课程代码-大纲
		//operationOutline.setLabOutlineName(operationOutline.getSchoolCourseInfoByClassId().getCourseNumber()+"-大纲");
		//保存学期默认当前
		//operationOutline.setSchoolTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()));

		operationOutline.setUser(shareService.getUser());
		OperationOutline o=  operationOutlineDAO.store(operationOutline);
		if(schoolMajors!=null ){
			this.saveSystemMajor(o.getId(),schoolMajors);
		}
		if(operationOutlineTeacher!=null){
			this.saveOperationOuterlineTeacher(o.getId(),operationOutlineTeacher);
		}
		if(commencementnaturemap!=null ){
			this.saveoperationoutlineproperty(o.getId(),commencementnaturemap);
		}
		if(projectitrms!=null && !projectitrms.equals("") && projectitrms.length>0  ){
			this.saveoperationoutlineitems(o.getId(),projectitrms);
		}
		return o;
	}

	/***********************************************************************************
	 * 功能 ：保存teacher
	 * 作者：戴昊宇
	 * 日期：2017-09-18
	 * @author 陈乐为 修改2017-9-30
	 ***********************************************************************************/
	public void saveOperationOuterlineTeacher(int outlineId,String[] operationOutlineTeacher ){
		OperationOutline outline=operationOutlineDAO.findOperationOutlineById(outlineId);
		Set<User> operationUser = outline.getOperationUser();
		if (operationUser!=null && operationUser.size()>0) {
			for (User opUser : operationUser) {
				operationUser.remove(opUser);
			}
		}
		if (operationOutlineTeacher!=null) {
			if (operationOutlineTeacher!=null && operationOutlineTeacher.length>0) {
				for (String opTeacher : operationOutlineTeacher) {
					if(!opTeacher.equals("-1")){//老师为了自主填写而选择的其他项剔除
						User opuser = userDAO.findUserByUsername(opTeacher);
						operationUser.add(opuser);
					}
				}
			}
		}
		outline.setOperationUser(operationUser);
		operationOutlineDAO.store(outline);
	}
	/*********************************************************************************
	 * 功能:保存大纲的必修与选修
	 * 作者：陆少凯
	 * 日期：2016-10-02
	 ********************************************************************************/
	public  void  savemoutlinecourse(OperationOutline operationOutline,String[] requiredCourses,String[] electiveCourses){
		//清空已存在的选修和必修
		List<MOutlineCourse> needToRemoveList=findMoutlineCourseByOutlineId(operationOutline.getId());
		for(MOutlineCourse m:needToRemoveList){
			mOutlineCourseDAO.remove(m);
		}
		mOutlineCourseDAO.flush();

		//每四个选修为一组
		int split=4;
		int requiredLength=0;
		int electiveLength=0;
		//一共count组选修
		if(electiveCourses!=null){
			electiveLength=electiveCourses.length;
		}
		int count=electiveLength/split;
		if(requiredCourses!=null){
			requiredLength=requiredCourses.length;
		}
		//得到必修的count
		//int majorCount=requiredCourses.length;
		for(int i=0;i<requiredLength;i++){
			MOutlineCourse m1=new MOutlineCourse();
			m1.setOperationOutline(operationOutline);
			m1.setSchoolCourseInfo(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(requiredCourses[i]));
			m1.setFlag("0");
			mOutlineCourseDAO.store(m1);
		}
		for(int i=0;i<count;i++){
			for(int j=0;j<split;j++){
				if(electiveCourses[i*split+j]!=null){
					if(electiveCourses[i*split+j]!=""){
						MOutlineCourse m=new MOutlineCourse();
						m.setOperationOutline(operationOutline);
						m.setSchoolCourseInfo(schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(electiveCourses[i*split+j]));
						m.setFlag("1");
						m.setCombine(String.valueOf(i));
						mOutlineCourseDAO.store(m);
					}
				}
			}
		}
	}

	/*******************************
	 * @功能：课程大纲单行数据导出
	 * @作者：张秦龙
	 * @时间：2017-12-5
	 ****************************/
	public void exportOutLine(int idkey,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Map>list = new ArrayList<Map>();
		List<OperationOutline> findSingle = getList(idkey);//所有数据
		int i = 1;
		for(OperationOutline s : findSingle){
			Map map=new HashMap();
			//大纲名称  labOutlineName
			if(s.getLabOutlineName() !=null){
				map.put("1", s.getLabOutlineName());
			}
			//课程编号
			if(s.getSchoolCourseInfoByClassId() !=null && s.getSchoolCourseInfoByClassId().getCourseNumber()!= null){
				map.put("2",s.getSchoolCourseInfoByClassId().getCourseNumber());
			}
			//课程名称
			if(s.getSchoolCourseInfoByClassId()!= null && s.getSchoolCourseInfoByClassId().getCourseName()!= null){
				map.put("3", s.getSchoolCourseInfoByClassId().getCourseName());
			}
			//老师
			if(s.getExtraTeacher() !=null){
				map.put("4",s.getExtraTeacher());
			}
			//学院  s.schoolAcademy.academyName
			if(s.getSchoolAcademy() !=null && s.getSchoolAcademy().getAcademyName()!= null){
				map.put("5", s.getSchoolAcademy().getAcademyName());
			}
			//学期  s.schoolTerm.termName
			if(s.getSchoolTerm() != null && s.getSchoolTerm().getTermName()!= null){
				map.put("6", s.getSchoolTerm().getTermName());
			}
			//教学目标任务 basicRequirementsCourse
			if(s.getBasicRequirementsCourse() !=null){
				map.put("7", s.getBasicRequirementsCourse());
			}
			//课程内容  basicContentCourse
			if(s.getBasicContentCourse() !=null){
				map.put("8", s.getBasicContentCourse());
			}
			list.add(map);
		}

		String title = "课程大纲详情表";
		String[] hearders = new String[] {"大纲名称","课程编号","课程名称","教师","学院","学期","教学目标任务","课程内容"}; //表头数组
		String[] fields = new String[] {"1","2","3","4","5","6","7","8"};

		TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders),fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title,shareService.getUser().getCname(), td);
	}
	//获取单行大纲记录
	public List<OperationOutline> getList(int idkey){
		String hql="select a from OperationOutline a where 1=1";
		hql+=" and a.id = "+idkey;
		//StringBuffer hql = new StringBuffer(" select a from OperationOutline a where 1=1 ");
		//hql.append(" and a.id ='"+idkey+"' ");
		List<OperationOutline> list=operationOutlineDAO.executeQuery(hql);  //hql.toString()
		return list;
	}

	/*********************************************************************************
	 *@description:实验室项目多文件上传
	 *@author: 郑昕茹
	 *@date：2016-11-09
	 ********************************************************************************/
	@SuppressWarnings({ "rawtypes" })
	@Override
	public String   uploadItemdocument(HttpServletRequest request, HttpServletResponse response, Integer id){
		String  listid="";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		String sep = System.getProperty("file.separator");
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean flag =false;
		String suiji = UUID.randomUUID().toString();
		String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"operation"+sep+suiji;
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
				CommonDocument conn=new CommonDocument();
				conn.setDocumentName(fileTrueName);
				conn.setDocumentUrl(fileDir + sep + fileTrueName);
				if(id!=null){
					conn.setOperationItem(operationItemDAO.findOperationItemById(id));
				}
				CommonDocument ids= commonDocumentDAO.store(conn);
				listid="<tr id='s_"+ids.getId()+"'><td>"+ids.getDocumentName()+"</td><td><input type='button' onclick='delectuploaddocment("+ids.getId()+")' value='删除'/> </td></tr>";

			}

		}

		return listid;
	}

	/********************************************************************************
	 * Description:课程代码和课程名称联动查询
	 * @author: 廖文辉
	 * @date: 2018-03-13
	 *********************************************************************************/
	public String LinkCourseNumberAndCourseName(String courseNumber){
		//查询课程编号
		String sql="select c from SchoolCourseInfo c where 1=1";
		sql+=" and c.courseNumber ='"+courseNumber+"'";
		SchoolCourseInfo schoolCourseInfo = schoolCourseInfoDAO.findSchoolCourseInfoByCourseNumber(courseNumber);

		String	courseName="<option value='"+schoolCourseInfo.getCourseName()+"'>"+schoolCourseInfo.getCourseName()+"</option>";
		String courseNameValue=shareService.htmlEncode(courseName);

		return courseNameValue;
	}
	/**************************************************************************************
	 * description：导入教学进度列表
	 * @author：郑昕茹
	 * @date：2016-12-19
	 **************************************************************************************/
	public void importCourse(String File,Integer id){
		Boolean isE2007=false;
		if(File.endsWith("xlsx")){
			isE2007=true;
		}
		//建立输入流
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			Workbook wb =null;
			if(isE2007){
				wb=new XSSFWorkbook(input);
			}else{
				wb=new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet= wb.getSheetAt(0);
			//获取第一个表单迭代器
			Iterator<Row>rows=sheet.rowIterator();
			Row rowContent=null;// 表头
			String courseContent ="";//课程内容
			String week="";//周次
			String courseTime="";//课次
			String curriculumNature ="";//课程性质
			String str="";
			Integer count=0;
			int a=0;
			while(rows.hasNext()){
				count = count +1;
				courseContent ="";
				week="";
				courseTime="";
				curriculumNature ="";
				if(a==0){
					rowContent=rows.next();
					a=1;
				}
				Row row =rows.next();
				int column=sheet.getRow(0).getPhysicalNumberOfCells();
				//chName ="";//品名
				for(int k=0;k<column;k++){
					if(row.getCell(k)!=null){
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						String columnName = rowContent.getCell(k).getStringCellValue();
						String content = row.getCell(k).getStringCellValue();
						if(columnName.equals("课程内容")){
							courseContent = content;
						}
						if(columnName.equals("周次")){
							week = content;
						}
						if(columnName.equals("课次")){
							courseTime = content;
						}
						if (columnName.equals("课程性质")) {
							curriculumNature = content;
						}

					}
				}
				OperationOutlineCourse course = new OperationOutlineCourse();

				course.setCourseContent(courseContent);
				course.setWeek(Integer.valueOf(week));
				course.setCourseTime(courseTime);
				CDictionary c = this.findCDictionaryByCName(curriculumNature);
				course.setcDictionary(c);

				OperationOutline operationOutline= getoperationoutlineinfor(id);
				course.setOperationOutline(operationOutline);
				operationOutlineCourseDAO.store(course);

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/********************************************************************************
	 * Description:通过字典名查找字典表
	 * @author: 邵志峰
	 * @date: 2018-04-20
	 *********************************************************************************/
	public CDictionary findCDictionaryByCName(String cname){
		String sql = "select c from CDictionary c  where 1=1";

		sql +=" and c.CName = '"+ cname +"'";
		List<CDictionary> list = cDictionaryDAO.executeQuery(sql);
		CDictionary cDictionary = new CDictionary();
		if(list.size()>0){
			cDictionary = list.get(0);
		}
		return cDictionary;
	}

	/*************************************************************************************
	 * @Description:通过课程编号、字典名称和字典编号查找operation_item
	 *
	 * @author: 杨新蔚
	 * @date: 2018/8/23
	 *************************************************************************************/
	public List<OperationItem> findOperationItemByCourseNumberAndStatus(String courseNumber,String cCategory,String dnumber){
		StringBuffer hql=new StringBuffer("select o from OperationItem o where 1=1");
		if(!EmptyUtil.isStringEmpty(courseNumber)){
			hql.append(" and o.schoolCourseInfo.courseNumber ='"+courseNumber+"'");
		}
		if(!EmptyUtil.isStringEmpty(cCategory)){
			hql.append(" and o.CDictionaryByLpStatusCheck.CCategory like '"+cCategory+"'");
		}
		if(!EmptyUtil.isStringEmpty(dnumber)){
			hql.append(" and o.CDictionaryByLpStatusCheck.CNumber like '"+dnumber+"'");
		}
		return operationItemDAO.executeQuery(hql.toString());

	}

	/**
	 * 删除实验大纲教学进度安排
	 * @param operationOutlineCourse
	 * 贺照易 2018-9-27
	 */
	@Override
	public void deleteThisOperationOutlineCourse(OperationOutlineCourse operationOutlineCourse){
		operationOutlineCourseDAO.remove(operationOutlineCourse);
		operationOutlineCourseDAO.flush();
	}

	/**
	 * Description 根据条件查询项目
	 * @param item
	 * @param cid
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@Override
	public List<OperationItem> findOperationItemForLims(OperationItem item, String acno, int currpage, int pageSize) {
		StringBuffer hql = new StringBuffer("select i from OperationItem i where 1=1");
		if(!EmptyUtil.isObjectEmpty(item)) {// 有效传参
			// 项目申请人
			if(!EmptyUtil.isObjectEmpty(item.getUserByLpCreateUser()) && !EmptyUtil.isStringEmpty(item.getUserByLpCreateUser().getUsername())) {
				hql.append(" and i.userByLpCreateUser.username = '"+ item.getUserByLpCreateUser().getUsername() +"'");
			}else {// 表示当前标签页为‘我的审核’或‘全部项目’，不显示草稿状态的数据
				hql.append(" and i.CDictionaryByLpStatusCheck.CNumber <> 1");
			}
			// 项目所处阶段
			if(!EmptyUtil.isObjectEmpty(item.getCDictionaryByLpStatusCheck()) && !EmptyUtil.isIntegerEmpty(item.getCDictionaryByLpStatusCheck().getId())) {
				hql.append(" and i.CDictionaryByLpStatusCheck.id = " + item.getCDictionaryByLpStatusCheck().getId());
			}
//			// 项目审核人
//			if(!EmptyUtil.isObjectEmpty(item.getUserByLpCheckUser())) {
//				hql.append(" and i.userByLpCheckUser.username = '"+ item.getUserByLpCheckUser().getUsername() +"'");
//			}
			// 项目名称
			if(!EmptyUtil.isStringEmpty(item.getLpName())) {
				hql.append(" and i.lpName like '%"+ item.getLpName() +"%'");
			}
			// 项目所属学期
			if(!EmptyUtil.isObjectEmpty(item.getSchoolTerm()) && !EmptyUtil.isIntegerEmpty(item.getSchoolTerm().getId())
					&&item.getSchoolTerm().getId()!=-1) {
				hql.append(" and i.schoolTerm.id = " + item.getSchoolTerm().getId());
			}
			// 所属课程
			if(!EmptyUtil.isObjectEmpty(item.getSchoolCourseInfo()) && !EmptyUtil.isStringEmpty(item.getSchoolCourseInfo().getCourseNumber())) {
				hql.append(" and i.schoolCourseInfo.courseNumber = '"+ item.getSchoolCourseInfo().getCourseNumber() +"'");
			}
		}
		// 中心参数有效
		if(acno!=null && !acno.equals("-1")) {
			hql.append(" and i.labCenter.schoolAcademy.academyNumber = '"+ acno +"'");
		}
		// 按照状态正序（草稿-审核中-...）
		hql.append(" order by i.CDictionaryByLpStatusCheck.CNumber");

		return operationItemDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * Description 根据条件查询项目数
	 * @param item
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@Override
	public int findOperationItemCountForLims(OperationItem item, String acno) {
		StringBuffer hql = new StringBuffer("select Count(i) from OperationItem i where 1=1");
		if(!EmptyUtil.isObjectEmpty(item)) {// 有效传参
			// 项目申请人
			if(!EmptyUtil.isObjectEmpty(item.getUserByLpCreateUser()) && !EmptyUtil.isStringEmpty(item.getUserByLpCreateUser().getUsername())) {
				hql.append(" and i.userByLpCreateUser.username = '"+ item.getUserByLpCreateUser().getUsername() +"'");
			}else {// 表示当前标签页为‘我的审核’或‘全部项目’，不显示草稿状态的数据
				hql.append(" and i.CDictionaryByLpStatusCheck.CNumber <> 1");
			}
			// 项目所处阶段
			if(!EmptyUtil.isObjectEmpty(item.getCDictionaryByLpStatusCheck()) && !EmptyUtil.isIntegerEmpty(item.getCDictionaryByLpStatusCheck().getId())) {
				hql.append(" and i.CDictionaryByLpStatusCheck.id = " + item.getCDictionaryByLpStatusCheck().getId());
			}
//			// 项目审核人
//			if(!EmptyUtil.isObjectEmpty(item.getUserByLpCheckUser())) {
//				hql.append(" and i.userByLpCheckUser.username = '"+ item.getUserByLpCheckUser().getUsername() +"'");
//			}
			// 项目名称
			if(!EmptyUtil.isStringEmpty(item.getLpName())) {
				hql.append(" and i.lpName like '%"+ item.getLpName() +"%'");
			}
			// 项目所属学期
			if(!EmptyUtil.isObjectEmpty(item.getSchoolTerm()) && !EmptyUtil.isIntegerEmpty(item.getSchoolTerm().getId())
					&&item.getSchoolTerm().getId()!=-1) {
				hql.append(" and i.schoolTerm.id = " + item.getSchoolTerm().getId());
			}
			// 所属课程
			if(!EmptyUtil.isObjectEmpty(item.getSchoolCourseInfo()) && !EmptyUtil.isStringEmpty(item.getSchoolCourseInfo().getCourseNumber())) {
				hql.append(" and i.schoolCourseInfo.courseNumber = '"+ item.getSchoolCourseInfo().getCourseNumber() +"'");
			}
		}
		// 中心参数有效
		if(acno!=null && !acno.equals("-1")) {
			hql.append(" and i.labCenter.schoolAcademy.academyNumber = '"+ acno +"'");
		}

		return ((Long)operationItemDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * Description 根据学院和角色获取用户数据
	 * @param academyNumber 学院
	 * @param authorityName 权限
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	@Override
	public List<User> getUserByQuery(String academyNumber, String authorityName) {
		StringBuffer hql = new StringBuffer("select u from User u join u.authorities a where a.authorityName = '"+ authorityName +"'");
		if(academyNumber!=null && !"".equals(academyNumber))
		{
			hql.append(" and u.schoolAcademy.academyNumber='"+academyNumber+"' ");
		}
		List<User> users = userDAO.executeQuery(hql.toString(), 0, -1);
		return users;
	}

	/**
	 * Description 保存实验项目设备
	 * @param itemId
	 * @param category
	 * @param ids
	 * @author 陈乐为 2018-8-25
	 */
	@Override
	public void saveItemDeviceLims(Integer itemId, String ids) {
		if(ids!=null && !"".equals(ids) && ids.length()>0)
		{
			String[] labRoomDeviceIdArr = ids.split(",");
			for (String s : labRoomDeviceIdArr)
			{
				OperationItemDevice operationItemDevice = new OperationItemDevice();
				try {
					LabRoomDevice labRoomDevice = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(Integer.parseInt(s));
					operationItemDevice.setOperationItem(findOperationItemByPrimaryKey(itemId));
					operationItemDevice.setLabRoomDevice(labRoomDevice);
					operationItemDevice.setSchoolDevice(labRoomDevice.getSchoolDevice());
//					operationItemDevice.setCDictionary(shareService.getCDictionaryByCategory("category_operation_item_device_main", category));  //类型：公用，专用
					operationItemDeviceDAO.store(operationItemDevice);
				} catch (Exception e) {
					System.out.println("operationItemDevice保存失败");
				}
			}
			operationItemDeviceDAO.flush();
		}
	}

	/***********************************************************************************
	 * @功能：找到与schoolcourseinfo相关联的实验项目
	 * @author 戴昊宇
	 * @日期：2018-03-22
	 * **********************************************************************************/
	public List<OperationItem> findOperationItemBycourseNumber(String courseNumber){
		//查询表中statusOrder为order的记录
		String hql = "select o from OperationItem o where 1=1";
		hql += " and o.schoolCourseInfo.courseNumber = '"+ courseNumber +"'";
		return operationItemDAO.executeQuery(hql,0,-1);
	}

}