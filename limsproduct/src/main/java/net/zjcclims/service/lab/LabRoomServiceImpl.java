package net.zjcclims.service.lab;

import com.alibaba.fastjson.JSON;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.gvsun.web.util.Authorization;
import net.gvsun.web.util.AuthorizationUtil;
import net.luxunsh.util.EmptyUtil;
import net.sf.json.JSONObject;
import net.zjcclims.constant.LabAttendance;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.service.device.SchoolDeviceService;
import net.zjcclims.util.HttpClientUtil;
import net.zjcclims.vo.AgentIOT;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("LabRoomService")
public class LabRoomServiceImpl implements LabRoomService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private LabRoomPermitUserDAO labRoomPermitUserDAO;
	@Autowired
	private LabWorkerDAO labWorkerDAO;
	@Autowired
	private LabWorkerTrainingDAO labWorkerTrainingDAO;
	@Autowired
	OperationItemDAO operationItemDAO;
	@Autowired
	LabRoomDeviceService labRoomDeviceService;
	@Autowired
	LabRoomAgentDAO labRoomAgentDAO;
	@Autowired
	LabCenterDAO labCenterDAO;
	@Autowired
	CommonHdwlogDAO commonHdwlogDAO;
	@Autowired
	SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	ShareService shareService;
	@Autowired
	private CommonDocumentDAO documentDAO;
	@Autowired
	private CommonVideoDAO videoDAO;
	@Autowired
	private LabRoomLimitTimeDAO labRoomLimitTimeDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;
	@Autowired
	private LabRoomLendingDAO labRoomLendingDAO;
	@Autowired
	private LabRoomTrainingDAO labRoomTrainingDAO;
	@Autowired
	private LabRoomTrainingPeopleDAO labRoomTrainingPeopleDAO;
	@Autowired
	private LabRoomAdminDAO labRoomAdminDAO;

	@Autowired
	private SoftwareReserveDAO softwareReserveDAO;
	@Autowired
	private SoftwareReserveAuditDAO softwareReserveAuditDAO;
	@Autowired
	private LabRoomAttentionDAO labRoomAttentionDAO;
	@Autowired
	private SchoolDeviceService schoolDeviceService;
	@Autowired
	private PConfig pConfig;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private SchoolDeviceDAO schoolDeviceDAO;
	@Autowired
	private RefuseItemBackupDAO refuseItemBackupDAO;
	/**
	 * 根据主键获取实验室对象
	 *
	 * @author hly
	 * 2015.07.28
	 */
	@Override
	public LabRoom findLabRoomByPrimaryKey(Integer labRoomId) {
		return labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
	}

	/**
	 * 根据查询条件获取实验室数据
	 *
	 * @author hly
	 * 2015.07.28
	 */
	@Override
	public List<LabRoom> findAllLabRoomByQuery(Integer currpage, Integer pageSize, LabRoom labRoom) {
		StringBuffer hql = new StringBuffer("select l from LabRoom l where 1=1 ");
		if (labRoom.getLabRoomName() != null && !"".equals(labRoom.getLabRoomName())) {
			hql.append(" and l.labRoomName like '%" + labRoom.getLabRoomName() + "%'");
		}
		/*if(labRoom.getLabCenter()!=null && labRoom.getLabCenter().getId()!=null)
		{
			hql.append(" and l.labCenter.id="+labRoom.getLabCenter().getId());
		}*/
		hql.append(" and l.labRoomActive=1");//使用状态：1--可用  0--不可用
		hql.append(" and l.labCategory=1");
		return labRoomDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
	}

	/**
	 * 根据查询条件获取实验室数据
	 *
	 * @author hly
	 * 2015.07.28
	 */
	@Override
	public List<LabRoom> findAllLabRoomByQuery(Integer currpage, Integer pageSize, LabRoom labRoom, int orderBy, HttpServletRequest request) {
		StringBuffer hql = new StringBuffer("select l from LabRoom l where 1=1 ");
		/*if(labRoom.getLabRoomName()!=null && !"".equals(labRoom.getLabRoomName()))
		{
			hql.append(" and l.labRoomName like '%"+labRoom.getLabRoomName()+"%'");
		}*/
		if (labRoom != null && labRoom.getLabRoomName() != null && !"".equals(labRoom.getLabRoomName())) {
			hql.append(" and l.labRoomName like '%" + labRoom.getLabRoomName() + "%'");
		}
		if (!EmptyUtil.isObjectEmpty(labRoom) &&
				!EmptyUtil.isObjectEmpty(labRoom.getLabBase()) && !EmptyUtil.isStringEmpty(labRoom.getLabBase().getLabName())) {
			hql.append(" and l.labBase.labName like '%" + labRoom.getLabBase().getLabName() + "%'");
		}
		hql.append(" and l.labCategory=1");
		String worker = request.getParameter("worker");
		String area = request.getParameter("area");
		String searchflg = request.getParameter("searchflg");
		String searchflg1 = request.getParameter("searchflg1");
		if (searchflg != null && searchflg != "") {
			if (worker != null && worker != "") {
				if (searchflg.equals("1")) {
					hql.append(" and l.labRoomWorker = " + worker);
				} else if (searchflg.equals("2")) {
					hql.append(" and l.labRoomWorker > " + worker);
				} else if (searchflg.equals("3")) {
					hql.append(" and l.labRoomWorker < " + worker);
				}
			}
		}
		if (searchflg1 != null && searchflg1 != "") {
			if (area != null && area != "") {

				if (searchflg1.equals("1")) {
					hql.append(" and l.labRoomArea = " + area);
				} else if (searchflg1.equals("2")) {
					hql.append(" and l.labRoomArea > " + area);
				} else if (searchflg1.equals("3")) {
					hql.append(" and l.labRoomArea < " + area);
				}
			}
		}
		/*if(labRoom.getLabCenter()!=null && labRoom.getLabCenter().getId()!=null)
		{
			hql.append(" and l.labCenter.id="+labRoom.getLabCenter().getId());
		}*/
		//hql.append(" and l.labRoomActive=1");//使用状态：1--可用  0--不可用
		hql.append(" and (l.isUsed=1 or l.isUsed=null)");
		if (orderBy == 9) {//默认

		}
		if (orderBy == 0) {//编号--降序
			hql.append(" order by l.labRoomNumber desc");
		}
		if (orderBy == 10) {//编号--升序
			hql.append(" order by l.labRoomNumber");
		}
		if (orderBy == 1) {//名称--降序
			hql.append(" order by l.labRoomName desc");
		}
		if (orderBy == 11) {//名称--升序
			hql.append(" order by l.labRoomName");
		}
		if (orderBy == 2) {//所属实验中心--降序
			hql.append(" order by l.labCenter.centerName desc");
		}
		if (orderBy == 12) {//所属实验中心--升序
			hql.append(" order by l.labCenter.centerName");
		}
		if (orderBy == 3) {//容量--降序
			hql.append(" order by l.labRoomCapacity desc");
		}
		if (orderBy == 13) {//容量--升序
			hql.append(" order by l.labRoomCapacity");
		}
		if (orderBy == 4) {//使用面积--降序
			hql.append(" order by l.labRoomArea desc");
		}
		if (orderBy == 14) {//使用面积--升序
			hql.append(" order by l.labRoomArea");
		}
		if (orderBy == 5) {//使用状态--降序
			hql.append(" order by l.labRoomActive desc");
		}
		if (orderBy == 15) {//使用状态--升序
			hql.append(" order by l.labRoomActive");
		}
		if (orderBy == 6) {//预约状态--降序
			hql.append(" order by l.labRoomReservation desc");
		}
		if (orderBy == 16) {//预约状态--升序
			hql.append(" order by l.labRoomReservation");
		}
		if (orderBy == 7) {//房间号--降序
			hql.append(" order by l.labRoomAddress desc");
		}
		if (orderBy == 17) {//房间号--升序
			hql.append(" order by l.labRoomAddress");
		}
		//实训室管理员看自己所管理的是实训室
		Object selectedRole = request.getSession(false).getAttribute("selected_role");
		if (selectedRole != null && "ROLE_LABMANAGER".equals(selectedRole.toString())) {
			String sql1 = "select r from LabRoomAdmin r where r.user.username = '"
					+ shareService.getUser().getUsername() + "'";
			List<LabRoomAdmin> labRoomAdmin = labRoomAdminDAO.executeQuery(sql1, 0, -1);
			if (labRoomAdmin.size() != 0) {
				hql.append(" and  (l.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username = '"
						+ shareService.getUser().getUsername() + "'))");
			}
		}
		return labRoomDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
	}

	/**
	 * 根据查询条件获取实验室数据--增加排序，增加选择中心筛选
	 *
	 * @author 廖文辉
	 * 2018.09.26
	 */
	public List<LabRoom> findLabRoomByLabCenter(Integer currpage, Integer pageSize, int type, LabRoom labRoom, int orderBy, HttpServletRequest request, String acno) {
		StringBuffer hql = new StringBuffer("select l from LabRoom l where 1=1 ");
		// 根据权限等级筛选
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if (authLevel == 6) {
			hql = new StringBuffer("select l from LabRoom l, LabRoomAdmin a where a.labRoom.id=l.id ");
			hql.append(" and a.typeId = 1");
			hql.append(" and a.user.username='" + shareService.getUserDetail().getUsername() + "'");
		} else if (authLevel == 5) {
			hql.append(" and l.labCenter.userByCenterManager.username='" + shareService.getUserDetail().getUsername() + "'");
		} else if (authLevel == 3 || authLevel == 4) {
			hql.append(" and l.labCenter.schoolAcademy.academyNumber='" + acno + "'");
		}
		if (labRoom != null && labRoom.getLabCenter() != null && labRoom.getLabCenter().getId() != null && !"".equals(labRoom.getLabCenter())) {
			hql.append(" and l.labCenter.id=" + labRoom.getLabCenter().getId());
		}
		if (labRoom != null && labRoom.getLabRoomName() != null && !"".equals(labRoom.getLabRoomName())) {
			hql.append(" and ( l.labRoomName like '%" + labRoom.getLabRoomName() + "%'" +
					" or l.labRoomNumber like '%" + labRoom.getLabRoomName() + "%'" +
					" or l.labCenter.centerName like '%" + labRoom.getLabRoomName() + "%')");
		}
	/*	if(labRoom !=null && labRoom.getLabRoomNumber()!=null&&!"".equals(labRoom.getLabRoomNumber())){
			hql.append(" and l.labRoomNumber='"+labRoom.getLabRoomNumber()+"'");
		}*/
		if (labRoom != null && labRoom.getLabRoomAddress() != null && !"".equals(labRoom.getLabRoomAddress())) {
			hql.append(" and l.labRoomAddress like '%" + labRoom.getLabRoomAddress() + "%'");
		}
		if (labRoom != null && labRoom.getLabRoomCapacity() != null) {
			hql.append(" and l.labRoomCapacity=" + labRoom.getLabRoomCapacity());
		}
		if (labRoom != null && labRoom.getCDictionaryByIsMultimedia() != null && labRoom.getCDictionaryByIsMultimedia().getCNumber() != null
				&& !labRoom.getCDictionaryByIsMultimedia().getCNumber().equals("")) {
			hql.append(" and l.CDictionaryByIsMultimedia.CNumber='" + labRoom.getCDictionaryByIsMultimedia().getCNumber() + "'");
		}
		if (type == 1) {
			hql.append(" and l.labCategory = 1");
		} else if (type == 2) {
			hql.append(" and l.labCategory = 2");
		} else if (type == 3) {
			hql.append(" and l.labCategory=3");
		} else if (type == 4) {
			hql.append(" and l.labCategory=4");
		}
		if (!EmptyUtil.isObjectEmpty(labRoom) &&
				!EmptyUtil.isObjectEmpty(labRoom.getLabBase()) && !EmptyUtil.isStringEmpty(labRoom.getLabBase().getLabName())) {
			hql.append(" and l.labBase.labName like '%" + labRoom.getLabBase().getLabName() + "%'");
		}
		String worker = request.getParameter("worker");
		String area = request.getParameter("area");
		String searchflg = request.getParameter("searchflg");
		String searchflg1 = request.getParameter("searchflg1");
		if (searchflg != null && searchflg != "") {
			if (worker != null && worker != "") {
				if (searchflg.equals("1")) {
					hql.append(" and l.labRoomWorker = " + worker);
				} else if (searchflg.equals("2")) {
					hql.append(" and l.labRoomWorker > " + worker);
				} else if (searchflg.equals("3")) {
					hql.append(" and l.labRoomWorker < " + worker);
				}
			}
		}
		if (searchflg1 != null && searchflg1 != "") {
			if (area != null && area != "") {

				if (searchflg1.equals("1")) {
					hql.append(" and l.labRoomArea = " + area);
				} else if (searchflg1.equals("2")) {
					hql.append(" and l.labRoomArea > " + area);
				} else if (searchflg1.equals("3")) {
					hql.append(" and l.labRoomArea < " + area);
				}
			}
		}
		hql.append(" and (l.isUsed=1 or l.isUsed=null)");
		if (orderBy == 9) {//默认
			hql.append(" order by l.labCenter.id,l.labRoomNumber,l.floorNo");
		}
		if (orderBy == 0) {//编号--降序
			hql.append(" order by l.labRoomNumber desc");
		}
		if (orderBy == 10) {//编号--升序
			hql.append(" order by l.labRoomNumber");
		}
		if (orderBy == 1) {//名称--降序
			hql.append(" order by l.labRoomName desc");
		}
		if (orderBy == 11) {//名称--升序
			hql.append(" order by l.labRoomName");
		}
		if (orderBy == 2) {//所属实验中心--降序
			hql.append(" order by l.labCenter.centerName desc");
		}
		if (orderBy == 12) {//所属实验中心--升序
			hql.append(" order by l.labCenter.centerName");
		}
		if (orderBy == 3) {//容量--降序
			hql.append(" order by l.labRoomCapacity desc");
		}
		if (orderBy == 13) {//容量--升序
			hql.append(" order by l.labRoomCapacity");
		}
		if (orderBy == 4) {//使用面积--降序
			hql.append(" order by l.labRoomArea desc");
		}
		if (orderBy == 14) {//使用面积--升序
			hql.append(" order by l.labRoomArea");
		}
		if (orderBy == 5) {//使用状态--降序
			hql.append(" order by l.labRoomActive desc");
		}
		if (orderBy == 15) {//使用状态--升序
			hql.append(" order by l.labRoomActive");
		}
		if (orderBy == 6) {//预约状态--降序
			hql.append(" order by l.labRoomReservation desc");
		}
		if (orderBy == 16) {//预约状态--升序
			hql.append(" order by l.labRoomReservation");
		}
		if (orderBy == 7) {//房间号--降序
			hql.append(" order by l.labRoomAddress desc");
		}
		if (orderBy == 17) {//房间号--升序
			hql.append(" order by l.labRoomAddress");
		}
		return labRoomDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
	}
	/**
	 * 获取可开门实验室数据
	 * @author 刘博越
	 * 2019.5.29
	 */
	@Override
	public List<LabRoom> findLabRoomOpenDoorByLabCenter(Integer currpage, Integer pageSize, LabRoom labRoom, String username, HttpServletRequest request, String acno) {
//		String sql = "select l from LabRoom l,l.labRoomAdmins ld, l.labRoomAgents la where ld.user.username = '"+username+"'"+
//				" and la.CDictionary.CName='门禁'";
//		if(labRoom.getLabRoomName()!=null){
//			sql += " and l.labRoomName like "+labRoom.getLabRoomName();
//		}
//		sql += " order by l.id asc";
//		Query query= entityManager.createQuery(sql);
//		List<LabRoom> courses = query.getResultList();
//		return courses;

		StringBuffer hql = new StringBuffer("select distinct l from LabRoom l");
		hql.append(" join l.labRoomAdmins d join l.labRoomAgents a");
		hql.append(" where 1=1 and d.user.username = '"+username+"' and a.CDictionary.CName ='门禁'");
		if(labRoom.getLabRoomName()!=null&&!labRoom.getLabRoomName().equals("")){
			hql.append(" and l.labRoomName like '%"+labRoom.getLabRoomName()+"%'");
		}
		hql.append(" order by l.id asc");
		List<LabRoom> labRooms = labRoomDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
		return labRooms;

	}

	/**
	 * 根据是否可以开门进行排序
	 * @author 刘博越
	 * 2019.05.27
	 */
	@Override
	public List<LabRoom> sortLabRoomByAgent(List<LabRoom> labRooms, String username) {
		//存放有开门权限的实验室
		List<LabRoom> labRoomsWithDoorindex = new ArrayList<LabRoom>();
		//存放剩余无开门权限实验室
		List<LabRoom> labRoomsLeft = new ArrayList<LabRoom>();
/*		//记录开门权限实验室在原列表中位子
		List<Integer> count = new ArrayList<Integer>();*/
		String sql="select distinct lg.lab_room_id from lab_room_agent lg left join lab_room_admin ld" +
				" on lg.lab_room_id=ld.lab_room_id where lg.doorindex is not null and ld.username="+username+" order by lg.lab_room_id asc";
		Query query=entityManager.createNativeQuery(sql);
		List<Integer> objects=query.getResultList();
		if(objects.size()!=0){
			for(Integer o :objects){
				for (LabRoom labRoom:labRooms){
					if(o.intValue()==labRoom.getId().intValue()){
						labRoomsWithDoorindex.add(labRoom);
						break;
					}
				}
			}
			if(labRoomsWithDoorindex.size()!=0){//若符合条件实验室不为空，则要进一步拼接剩下的实验室
				for(LabRoom labRoom:labRooms){
					int flag =  1;
					for(Integer o:objects){
						if(labRoom.getId().intValue()==o.intValue()){
							flag=0;
						}
					}
					if(flag==1){
						labRoomsLeft.add(labRoom);
					}
				}
				//拼接两个list，生成排序后的list
				labRoomsWithDoorindex.addAll(labRoomsLeft);
				return labRoomsWithDoorindex;
			}else{//若无符合条件实验室，则直接返回原list
				return labRooms;
			}
		}else{//无开门权限实验室，直接返回原list
			return labRooms;
		}
	}

	/**
	 * 保存实验室数据
	 *
	 * @author hly
	 * 2015.07.28
	 */
	@Override
	public LabRoom saveLabRoom(LabRoom labRoom) {
		return labRoomDAO.store(labRoom);
	}

	/**
	 * 删除实验室数据
	 *
	 * @author hly
	 * 2015.07.28
	 */
	@Override
	public boolean deleteLabRoom(Integer labRoomId) {
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);
		if (labRoom != null) {
			labRoomDAO.remove(labRoom);
			labRoomDAO.flush();
			return true;
		}

		return false;
	}

	/**
	 * 根据主键获取实验室工作人员对象
	 *
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public LabWorker findLabWorkerByPrimaryKey(Integer labWorkerId) {
		return labWorkerDAO.findLabWorkerByPrimaryKey(labWorkerId);
	}

	/**
	 * 根据查询条件获取实验室工作人员数据
	 *
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public List<LabWorker> findAllLabWorkerByQuery(Integer currpage, Integer pageSize, LabWorker labWorker) {
		StringBuffer hql = new StringBuffer("select w from LabWorker w where 1=1 ");
		if (labWorker.getLwName() != null && !"".equals(labWorker.getLwName())) {
			hql.append(" and w.lwName like '%" + labWorker.getLwName() + "%'");
		}

		return labWorkerDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
	}

	/**
	 * 保存实验室工作人员数据
	 *
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public LabWorker saveLabWorker(LabWorker labWorker) {
		/*labWorker.setLabCenter(labCenterDAO.findLabCenterByPrimaryKey(labWorker.getLabCenter().getId()));
		labWorker.setCDictionaryByLwAcademicDegree(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwAcademicDegree().getId()));
		labWorker.setCDictionaryByLwBookLevel(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwBookLevel().getId()));
		labWorker.setCDictionaryByLwCategoryStaff(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwCategoryStaff().getId()));
		labWorker.setCDictionaryByLwDegree(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwDegree().getId()));
		labWorker.setCDictionaryByLwEmployment(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwEmployment().getId()));
		labWorker.setCDictionaryByLwForeignLanguage(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwForeignLanguage().getId()));
		labWorker.setCDictionaryByLwForeignLanguageLevel(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwForeignLanguageLevel().getId()));
		labWorker.setCDictionaryByLwMainWork(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwMainWork().getId()));
		labWorker.setCDictionaryByLwPaperLevel(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwPaperLevel().getId()));
		labWorker.setCDictionaryByLwReward(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwReward().getId()));
		labWorker.setCDictionaryByLwSubject(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwSubject().getId()));
		labWorker.setCDictionaryByLwSpecialtyDuty(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwSpecialtyDuty().getId()));
		labWorker.setCDictionaryByLwReward(cDictionaryDAO.findCDictionaryByPrimaryKey(labWorker.getCDictionaryByLwReward().getId()));
		*/
		return labWorkerDAO.store(labWorker);
	}

	/**
	 * 删除实验室工作人员数据
	 *
	 * @author hly
	 * 2015.07.29
	 */
	@Override
	public boolean deleteLabWorker(Integer labWorkerId) {
		LabWorker labWorker = findLabWorkerByPrimaryKey(labWorkerId);
		if (labWorker != null) {
			labWorkerDAO.remove(labWorker);
			labWorkerDAO.flush();

			return true;
		}

		return false;
	}

	/**
	 * 获取指定实验中心下的实验室
	 *
	 * @author hly
	 * 2015.08.18
	 */
	@Override
	public List<LabRoom> findLabRoomByLabCenterid(String acno, Integer isReservation) {
		StringBuffer hql = new StringBuffer("select l from LabRoom l where 1=1");
		hql.append(" and l.isUsed = 1");
		/*if (cid!=null) {
			hql.append(" where l.labCenter.id="+cid);
		}*/
		if (isReservation != null && isReservation == 1) {
			hql.append(" and l.labRoomActive = 1");
		}
		if (acno != null && !"".equals(acno)) {

			hql.append(" and l.schoolAcademy.academyNumber='" + acno + "'");
		}
		hql.append(" and l.labCategory=1");
		return labRoomDAO.executeQuery(hql.toString(), 0, -1);
	}

	/****************************************************************************
	 * 功能：根据中心id查询该中心的实验室（分页）
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByLabCenterid(LabRoomDevice device, String acno, Integer page, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select m from LabRoom m where 1=1";
		if (device.getLabRoom() != null) {
			if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
				sql += " and m.id=" + device.getLabRoom().getId();
			}
		}
		return labRoomDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 * 功能：根据实验室id和用户判断用户是否为该实验室的实验室管理员
	 * 作者：贺子龙
	 * 时间：2015-09-03
	 ****************************************************************************/
	@Override
	public boolean getLabRoomAdminReturn(Integer id, User user) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (user != null) {

			//实验室管理员
			List<LabRoomAdmin> adminList = labRoomDeviceService.findLabRoomAdminByRoomId(id, 1);
			for (LabRoomAdmin a : adminList) {
				if (a.getUser() == user) {
					flag = true;
				}
			}

			Set<Authority> auths = user.getAuthorities();
			for (Authority authority : auths) {//中心主任/超管/实践教学副院长/院系级系统管理员
				if (authority.getId() == 4 || authority.getId() == 11 || authority.getId() == 3 || authority.getId() == 6) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/****************************************************************************
	 * 功能：查询出所有的实验项目卡
	 * 作者：贺子龙
	 * 时间：2015-09-03
	 ****************************************************************************/
	@Override
	public List<OperationItem> findAllOperationItem(String number) {
		// TODO Auto-generated method stub
		String sql = "select p from OperationItem p where 1=1";
		// -1表示不做学院筛选
		if (!"-1".equals(number)) {
			sql += " and p.labCenter.schoolAcademy.academyNumber='" + number + "'";
		}
		return operationItemDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：根据实验室id查询出所有的实验项目
	 * 作者：张德冰
	 * 时间：2018.03.09
	 ****************************************************************************/
	@Override
	public List<OperationItem> findAllOperationItemByRoomId(Integer id) {
		// TODO Auto-generated method stub
		String sql = "select p from OperationItem p where p.id!=0 and p.labRoom.id = '" + id + "'";
		return operationItemDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：根据条件查询出实验项目
	 * 作者：张德冰
	 * 时间：2018.03.09
	 ****************************************************************************/
	@Override
	public List<OperationItem> findOperationItemByRoomId(Integer id, OperationItem operationItem) {
		// TODO Auto-generated method stub
		String sql = "select p from OperationItem p where p.id!=0 and p.labRoom.id = '" + id + "'";
		if (operationItem.getLpName() != null) {
			sql += " and p.lpName like '" + operationItem.getLpName() + "'";
		}
		return operationItemDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：根据实验室查询实验室硬件
	 * 作者：贺子龙
	 * 时间：2015-09-04
	 ****************************************************************************/
	@Override
	public List<LabRoomAgent> findLabRoomAgentByRoomId(Integer id) {
		String sql = "select a from LabRoomAgent a where a.labRoom.id=" + id;
		return labRoomAgentDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：保存实验室的实验项目
	 * 作者：贺子龙
	 * 时间：2015-09-07
	 ****************************************************************************/
	@Override
	public void saveLabRoomOperationItem(LabRoom room, String[] str) {
		// TODO Auto-generated method stub
		Set<OperationItem> currents = room.getOperationItems();//要添加的项目卡
		System.out.println("保存之前数量为：" + currents.size());
		for (String s : str) {
			int id = Integer.parseInt(s);
			//id对应的实验项目卡
			OperationItem ope = operationItemDAO.findOperationItemByPrimaryKey(id);
			currents.add(ope);
		}
		room.setOperationItems(currents);
		System.out.println("保存之后数量为：" + currents.size());
		labRoomDAO.store(room);
		labRoomDAO.flush();
	}

	/****************************************************************************
	 * 功能：删除实验室的实验项目
	 * 作者：贺子龙
	 * 时间：2015-09-07
	 ****************************************************************************/
	@Override
	public void deleteLabRoomOperationItem(LabRoom room, OperationItem m) {
		// TODO Auto-generated method stub
		Set<OperationItem> currents = room.getOperationItems();
		currents.remove(m);
		room.setOperationItems(currents);
		room = labRoomDAO.store(room);
		labRoomDAO.flush();
	}

	/****************************************************************************
	 * 功能：根据user对象和学院编号查询用户并分页
	 * 作者：贺子龙
	 * 修改：2015-09-08
	 ****************************************************************************/
	@Override
	public List<User> findUserByUserAndSchoolAcademy(User user, Integer roomId,
													 String academyNumber, Integer page, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select u from User u where 1=1";
		if (roomId != null && roomId != 0) {
			if (user != null && user.getUserType() != null) {
				sql += " and u.username not in(select a.user.username from LabRoomAdmin a where a.labRoom.id=" + roomId + ")";
			}
		}
		sql += " and u.enabled=true";
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}
		if (academyNumber != null && !academyNumber.equals("")) {
			sql += " and u.schoolAcademy.academyNumber like '" + academyNumber + "%'";
		}else {// 本学院优先列出
			sql += " order by case when u.schoolAcademy.academyNumber='" +
					shareService.getUser().getSchoolAcademy().getAcademyNumber()+ "' then 0 else 1 end ";
		}

		return userDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 * 功能：根据user对象和学院编号查询用户数量
	 * 作者：李小龙
	 * 修改：2014-12-4 14:58:16 去掉根据学院查询，即查询整个学校的用户
	 ****************************************************************************/
	@Override
	public int findUserByUserAndSchoolAcademy(User user, Integer roomId,
											  String academyNumber) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from User u where 1=1";
		if (roomId != null && roomId != 0) {
			sql += " and u.username not in(select a.user.username from LabRoomAdmin a where a.labRoom.id=" + roomId + ")";
		}
		if (academyNumber != null && !academyNumber.equals("")) {
			sql += " and u.schoolAcademy.academyNumber like '" + academyNumber + "%'";
		}
		sql += " and u.enabled=true";
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}

		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 * 功能：根据roomId查询该实验室的门禁
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<LabRoomAgent> findLabRoomAgentAccessByRoomId(Integer roomId) {
		// TODO Auto-generated method stub
		String sql = "select a from LabRoomAgent a where a.labRoom.id=" + roomId + " and a.CDictionary.id=548";
		return labRoomAgentDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：根据deviceNumber查找实验室电源控制器
	 * 作者：贺子龙
	 ****************************************************************************/
	public LabRoomAgent findGuardByRemark(String deviceNumber, int labId){
		String sql = "select l from LabRoomAgent l where 1=1";
		sql+=" and l.hardwareRemark like '"+deviceNumber+"'";
		sql+=" and l.labRoom.id = "+labId;
		List<LabRoomAgent> agents = labRoomAgentDAO.executeQuery(sql);
		if (agents!=null && agents.size()>0) {
			return agents.get(0);
		}else {
			return null;
		}
	}

	/*************************************************************************************
	 * @功能：根据学院查询实验室并分页--默认显示当前学院的
	 * @作者： 贺子龙
	 *************************************************************************************/
	@Override
	public List<LabRoom> findLabRoomBySchoolAcademyDefault(
			LabRoom labRoom, int page, int pageSize, int type, String acno) {
		String academyNumber = "";
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		if (acno != null && !acno.equals("-1")) {
			//获取选择的实验中心
			academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
		} else {
			if (shareService.getUser().getSchoolAcademy() != null
					&& !shareService.getUser().getSchoolAcademy().getAcademyNumber().equals("")) {
				academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
		}
		String sql = "select DISTINCT r from LabRoom r,LabRoomAgent la where 1=1 ";
		sql += " and la.labRoom.id=r.id";
		sql += " and la.CDictionary.id=" + type;
//			sql+=" and r.labCenter.schoolAcademy.academyNumber='"+academyNumber+"' ";

		if (labRoom.getId() != null && !labRoom.getId().equals(0)) {
			sql += " and r.id=" + labRoom.getId();
		}
		return labRoomDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录数量--增加查询功能
	 * @作者： 贺子龙
	 *************************************************************************************/
	@Override
	public int findLabRoomAccessByIpCount(CommonHdwlog commonHdwlog, String ip, String port, HttpServletRequest request) {
		String sql = "select count(*) from CommonHdwlog c where 1=1";
		if (ip != null && !ip.equals("")) {
			sql += " and c.hardwareid='" + ip + "' ";
		}

		if (port != null && !port.equals("")) {
			sql += " and c.doorindex='" + port + "' ";
		}

		if (commonHdwlog.getCardname() != null && !commonHdwlog.getCardname().equals("")) {
			sql += " and c.cardname like '%" + commonHdwlog.getCardname() + "%' ";

		}
		if (commonHdwlog.getUsername() != null && !commonHdwlog.getUsername().equals("")) {
			sql += " and c.username like '%" + commonHdwlog.getUsername() + "%' ";

		}
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");

		if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
			sql += " and c.datetime between '" + starttime + "' and '" + endtime + "' ";
		}

		int count = ((Long) commonHdwlogDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		return count;
	}

	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录并分页--增加查询功能
	 * @作者： 贺子龙
	 *************************************************************************************/
	@Override
	public List<LabAttendance> findLabRoomAccessByIp(CommonHdwlog commonHdwlog, String ip, String port, Integer page,
													 int pageSize, HttpServletRequest request) {
		String sql = "select c from CommonHdwlog c where 1=1";

		if (ip != null && !ip.equals("")) {
			sql += " and c.hardwareid='" + ip + "' ";
		}

		if (port != null && !port.equals("")) {
			sql += " and c.doorindex='" + port + "' ";
		}

		if (commonHdwlog.getCardname() != null && !commonHdwlog.getCardname().equals("")) {
			sql += " and c.cardname like '%" + commonHdwlog.getCardname() + "%' ";

		}
		if (commonHdwlog.getUsername() != null && !commonHdwlog.getUsername().equals("")) {
			sql += " and c.username like '%" + commonHdwlog.getUsername() + "%' ";

		}
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");

		if (starttime != null && starttime.length() > 0 && endtime != null && endtime.length() > 0) {
			sql += " and c.datetime between '" + starttime + "' and '" + endtime + "' ";
		}

		sql += " order by c.id desc";
		List<CommonHdwlog> commonHdwlogs = commonHdwlogDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
		//将查出来的日志数据导入labAttendanceList中
		List<LabAttendance> labAttendanceList = new ArrayList<LabAttendance>();
		for (CommonHdwlog commonHdwlog2 : commonHdwlogs) {
			LabAttendance labAttendance = new LabAttendance();
			//姓名
			labAttendance.setCname(commonHdwlog2.getCardname());
			//考勤时间
			String attendanceTime = commonHdwlog2.getDatetime();
			labAttendance.setAttendanceTime(attendanceTime.substring(0, attendanceTime.length() - 2));
			//default数据
			labAttendance.setClassName("暂无数据");
			labAttendance.setMajor("暂无数据");
			labAttendance.setAcademyName("暂无数据");
			//所属学院
			if (commonHdwlog2.getAcademyNumber() != null && !commonHdwlog2.getAcademyNumber().equals("")) {
				String academyName = "";
				SchoolAcademy schoolAcademy = schoolAcademyDAO.findSchoolAcademyByAcademyNumber(commonHdwlog2.getAcademyNumber());
				if (schoolAcademy != null && !schoolAcademy.getAcademyName().equals("")) {
					academyName = schoolAcademy.getAcademyName();
					labAttendance.setAcademyName(academyName);
				}

			}
			//学号
			String username = "";
			if (commonHdwlog2.getUsername() != null && !commonHdwlog2.getUsername().equals("")) {
				username = commonHdwlog2.getUsername();
			}
			if (!username.equals("")) {
				labAttendance.setUsername(username);
				User user = userDAO.findUserByPrimaryKey(username);
				//班级
				String className = "";
				if (user.getSchoolClasses() != null && !user.getSchoolClasses().getClassName().equals("")) {
					className = user.getSchoolClasses().getClassName();
					labAttendance.setClassName(className);
				}
				/*//所属专业方向
				String major="";
				if (user.getSchoolMajor()!=null&&!user.getSchoolMajor().getMajorName().equals("")) {
					major=user.getSchoolMajor().getMajorName();
					labAttendance.setMajor(major);
				}*/
			}
			//物联门禁刷卡状态
			String state = "暂无数据";
			if (commonHdwlog2.getStatus() != null && !commonHdwlog2.getStatus().equals("")) {
				CDictionary cDictionary = shareService.getCDictionaryByCategory("ofthings_acl_log_stat", commonHdwlog2.getStatus());
				if (cDictionary != null && !cDictionary.getCName().equals("")) {
					state = cDictionary.getCName();
				}
			}
			labAttendance.setState(state);
			labAttendanceList.add(labAttendance);

		}
		return labAttendanceList;
	}

	/****************************************************************************
	 * 功能：根据中心id和查询条件查询该中心的实验室数量
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public int findAllLabRoom(LabRoomDevice device, String acno, Integer isReservation) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from LabRoom m where 1=1";
		if (device.getLabRoom() != null) {
			if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
				sql += " and m.id=" + device.getLabRoom().getId();
			}
		}
		sql += " and m.isUsed = 1";
		if (isReservation == 1) {
			sql += " and m.labRoomActive = 1";
		}
		return ((Long) labRoomDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	@Override
	public int countLabRoomListByQuery(LabRoom labRoom, String username) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(l) from LabRoom l ");
		if (username != null && !"".equals(username)) {
			hql.append(" join l.labRoomAdmins a ");
		}
		hql.append(" where 1=1 ");
		if (labRoom.getLabCenter() != null && labRoom.getLabCenter().getId() != null) {
			hql.append(" and l.labCenter.id=" + labRoom.getLabCenter().getId());
		}
		if (labRoom.getLabRoomName() != null && !"".equals(labRoom.getLabRoomName())) {
			hql.append(" and l.labRoomName like '%" + labRoom.getLabRoomName() + "%'");
		}
		if (username != null && !"".equals(username)) {
			hql.append(" and a.user.username = '" + username + "'");
		}

		hql.append(" and l.labRoomActive=1 and l.labRoomReservation=1");
		int result = ((Long) labRoomDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
		return result;
	}

	@Override
	public List<LabRoom> findLabRoomListByQuery(LabRoom labRoom,
												String username, Integer currpage, int pageSize) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select l from LabRoom l ");
		if (username != null && !"".equals(username)) {
			hql.append(" join l.labRoomAdmins a ");
		}
		hql.append(" where 1=1 ");
		if (labRoom.getLabCenter() != null && labRoom.getLabCenter().getId() != null) {
			hql.append(" and l.labCenter.id=" + labRoom.getLabCenter().getId());
		}
		if (labRoom.getLabRoomName() != null && !"".equals(labRoom.getLabRoomName())) {
			hql.append(" and l.labRoomName like '%" + labRoom.getLabRoomName() + "%'");
		}
		if (username != null && !"".equals(username)) {
			hql.append(" and a.user.username = '" + username + "'");
		}

		hql.append(" and l.labRoomActive=1 and l.labRoomReservation=1");
		List<LabRoom> labRooms = labRoomDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
		return labRooms;
	}

	@Override
	public List<User> findAllLabRoomAdmins(LabRoom room, Integer type) {
		// TODO Auto-generated method stub
		String hql = "select u from LabRoomAdmin l join l.user u where 1=1 ";
		if (room != null && room.getId() != null) {
			hql += " and l.labRoom.id = '" + room.getId() + "'";
		}
		if (type != null) {
			hql += " and l.typeId = '" + type + "'";
		}
		hql += " group by l.user.username";
		List<User> users = userDAO.executeQuery(hql, 0, -1);
		return users;
	}

	/****************************************************************************
	 * 功能：保存实验分室的视频
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public void saveLabRoomVideo(String fileTrueName, Integer labRoomid) {
		//id对应的实验分室
		LabRoom room = labRoomDAO.findLabRoomByPrimaryKey(labRoomid);
		CommonVideo video = new CommonVideo();
		video.setVideoName(fileTrueName);
		String videoUrl = "upload/labroom/" + labRoomid + "/" + fileTrueName;
		video.setVideoUrl(videoUrl);
		video.setLabRoom(room);

		videoDAO.store(video);
	}

	/****************************************************************************
	 * 功能：保存实验分室的文档
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public void saveLabRoomDocument(String fileTrueName, Integer labRoomid, Integer type) {
		// TODO Auto-generated method stub
		//id对应的实验分室
		LabRoom room = labRoomDAO.findLabRoomByPrimaryKey(labRoomid);
		CommonDocument doc = new CommonDocument();
		doc.setType(type);
		doc.setDocumentName(fileTrueName);
		String imageUrl = "upload/labroom/" + labRoomid + "/" + fileTrueName;
		doc.setDocumentUrl(imageUrl);
		doc.setLabRoom(room);

		documentDAO.store(doc);
	}

	/****************************************************************************
	 * 功能：查询某一实验中心下有设备的实验室
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabRoom> findLabRoomWithDevices(Integer isReservation) {
		String sql = "select distinct m from LabRoom m inner join m.labRoomDevices ld where 1=1 ";
		sql += " and m.isUsed = 1";
		if (isReservation != null && isReservation == 1) {
			sql += "and m.labRoomActive = 1";
		}
		sql += " and m.labCategory=1";
		return labRoomDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：根据中心id查询该中心存放有设备的实验室（分页）
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomWithDevices(LabRoomDevice device, Integer page, int pageSize, Integer isReservation, String acno, HttpServletRequest request) {
		String sql = "select distinct m from LabRoom m, LabRoomDevice lr, LabRoomAdmin la where 1=1 and m.id=lr.labRoom.id";
		if (device.getLabRoom() != null) {
			if (device.getLabRoom().getId() != null && !device.getLabRoom().getId().equals("")) {
				sql += " and m.id=" + device.getLabRoom().getId();
			}
		}
		if (device != null && device.getCDictionaryByAllowAppointment() != null
				&& device.getCDictionaryByAllowAppointment().getCNumber() != null
				&& !device.getCDictionaryByAllowAppointment().getCNumber().equals("")) {
			sql += " and lr.CDictionaryByAllowAppointment.CNumber =" + device.getCDictionaryByAllowAppointment().getCNumber();
		}
		if (isReservation != null && isReservation == 1) {
			sql += " and m.labRoomActive = 1";
		}
		if (pConfig.PROJECT_NAME.equals("zjcclims") && !acno.equals("1036")) {
			sql += " and m.schoolAcademy.academyNumber='" + acno + "'";
		} else if (pConfig.PROJECT_NAME.equals("zjcclims") && acno.equals("1036")) {

		} else if (acno != null && !"".equals(acno) &&!acno.equals("-1")) {
			sql += " and m.schoolAcademy.academyNumber='" + acno + "'";
		}
		// 根据权限等级筛选
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if (authLevel == 7) {
			sql += " and lr.user.username='" + shareService.getUserDetail().getUsername() + "'";
		}else if(authLevel == 6) {
			sql += " and m.id=la.labRoom.id and la.typeId=1 and la.user.username='" + shareService.getUserDetail().getUsername() + "'";
		}
		sql += " and m.labCategory=1";
		return labRoomDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/**
	 * @param user、cid、academyNumber
	 * @comment：根据user对象和学院编号查询用户数量
	 * @return：
	 * @author：叶明盾
	 * @date：2015-10-28 下午10:35:05
	 */
	@Override
	public int findUserByUserAndAcademy(User user, Integer cid, String academyNumber) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from User u where 1=1";
		if (cid != null && cid != 0) {
			sql += " and u.username not in(select a.user.username from LabRoomAdmin a where 1=1)";
		}
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and u.schoolAcademy.academyNumber like '"+academyNumber+"%'";
		}*/
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}

		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/**
	 * @param user、cid、academyNumber、page、pageSize
	 * @comment：根据user对象和学院编号查询用户并分页
	 * @return：
	 * @author：叶明盾
	 * @date：2015-10-28 下午10:35:05
	 */
	@Override
	public List<User> findUserByUserAndAcademy(User user, Integer cid, String academyNumber, Integer page, int pageSize) {
		String sql = "select u from User u where 1=1";
		if (cid != null && cid != 0) {
			sql += " and u.username not in(select a.user.username from LabRoomAdmin a where 1=1)";
		}
		/*if(academyNumber!=null&&!academyNumber.equals("")){
			sql+=" and u.schoolAcademy.academyNumber like '"+academyNumber+"%'";
		}*/
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}
		sql += "ORDER BY CASE WHEN u.schoolAcademy.academyNumber like '" + academyNumber + "%' THEN 0 ELSE 1 END" +
				", CASE WHEN u.userRole=1 THEN 0 ELSE 1 END";
		sql += " ,u.username desc";
		return userDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 * 功能：删除排课相关的实验室禁用记录
	 * 作者：贺子龙
	 * 时间：2016-05-28
	 ****************************************************************************/
	public void deleteLabRoomLimitTimeByAppointment(int appointmentId) {
		String sql = "select l from LabRoomLimitTime l where 1=1";
		sql += " and l.flag = 1";
		sql += " and l.info like '" + appointmentId + "'";

		List<LabRoomLimitTime> times = labRoomLimitTimeDAO.executeQuery(sql, 0, -1);
		if (times != null && times.size() > 0) {
			for (LabRoomLimitTime labRoomLimitTime : times) {
				labRoomLimitTimeDAO.remove(labRoomLimitTime);
				labRoomLimitTimeDAO.flush();
			}
		}
	}

	/**************************************************************************************
	 * description：导入实验室人员记录
	 * @throws ParseException
	 * @author：郑昕茹
	 * @date：2016-12-19
	 **************************************************************************************/
	public void importLabWorker(String File) throws ParseException {
		Boolean isE2007 = false;
		if (File.endsWith("xlsx")) {
			isE2007 = true;
		}
		//建立输入流
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			Workbook wb = null;
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet = wb.getSheetAt(0);
			//获取第一个表单迭代器
			Iterator<Row> rows = sheet.rowIterator();
			Row rowContent = null;
			String username = "";// 工号
			String cname = "";//姓名
			String birthday = "";//出生年月
			String sexy = "";//性别
			String academicDegree = "";//学历
			String degree = "";//学位
			String lwProfessionSpecialty = "";//专业技术职务
			String duty = "";//承担任务
			String categoryStaff = "";//专职/兼职
			String employer = "";//单位（非必填）
			int a = 0;
			while (rows.hasNext()) {

				if (a == 0) {
					rowContent = rows.next();
					a = 1;
				}
				Row row = rows.next();
				int column = sheet.getRow(0).getPhysicalNumberOfCells();
				username = "";// 工号
				cname = "";//姓名
				birthday = "";//出生年月
				sexy = "";//性别
				academicDegree = "";//学历
				degree = "";//学位
				lwProfessionSpecialty = "";//专业技术职务
				duty = "";//承担任务
				categoryStaff = "";//专职/兼职
				employer = "";//单位（非必填）
				for (int k = 0; k < column; k++) {
					if (row.getCell(k) != null) {
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						String columnName = rowContent.getCell(k).getStringCellValue();
						String content = row.getCell(k).getStringCellValue();
						if (columnName.equals("工号(必填)")) {
							username = content;
						}
						if (columnName.equals("姓名")) {
							cname = content;
						}
						if (columnName.equals("出生年月")) {
							birthday = content;
						}
						if (columnName.equals("性别")) {
							sexy = content;
						}
						if (columnName.equals("学历")) {
							academicDegree = content;
						}
						if (columnName.equals("学位")) {
							degree = content;// 替换所有的非数字
						}
						if (columnName.equals("专业技术职务")) {
							lwProfessionSpecialty = content;
						}
						if (columnName.equals("承担任务")) {
							duty = content;
						}
						if (columnName.equals("专职/兼职")) {
							categoryStaff = content;
						}
						if (columnName.equals("单位（非必填）")) {
							employer = content;// 替换所有的非数字
						}
					}
				}

				LabWorker labWorker = new LabWorker();
				if (!username.equals("")) {
					User user = userDAO.findUserByPrimaryKey(username);
					if (user != null) {
						labWorker.setUser(user);
						labWorker.setLwName(user.getCname());
					}
				}
				if (!birthday.equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					Date date = sdf.parse(birthday);
					Calendar lwBirthday = Calendar.getInstance();
					lwBirthday.setTime(date);
					labWorker.setLwBirthday(lwBirthday);
				}
				if (!cname.equals("")) {
					labWorker.setLwName(cname);
				}
				if (!sexy.equals("") && sexy.equals("男")) {
					labWorker.setLwSex("1");
				}
				if (!sexy.equals("") && sexy.equals("女")) {
					labWorker.setLwSex("0");
				}
				if (!academicDegree.equals("")) {
					labWorker.setCDictionaryByLwAcademicDegree(this.findCDictionaryByLwAcademicDegreeByNameAndCategory(academicDegree, "category_lab_worker_academic_degree"));
				}
				if (!degree.equals("")) {
					labWorker.setCDictionaryByLwDegree(this.findCDictionaryByLwAcademicDegreeByNameAndCategory(degree, "category_lab_worker_degree"));
				}
				if (!lwProfessionSpecialty.equals("")) {
					labWorker.setCDictionaryByLwSpecialtyDuty(this.findCDictionaryByLwAcademicDegreeByNameAndCategory(lwProfessionSpecialty, "category_lab_worker_specialty_duty"));
					//labWorker.setCDictionaryByLwDegree(this.findCDictionaryByLwAcademicDegreeByNameAndCategory(lwProfessionSpecialty, "category_lab_worker_specialty_duty"));
				}
				if (!duty.equals("")) {
					labWorker.setLwDuty(duty);
				}
				if (!categoryStaff.equals("")) {
					labWorker.setCDictionaryByLwCategoryStaff(this.findCDictionaryByLwAcademicDegreeByNameAndCategory(categoryStaff, "category_lab_worker_category_staff"));
				}
				if (!employer.equals("")) {
					labWorker.setEmployer(employer);
				}
				labWorkerDAO.store(labWorker);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    /**************************************************************************************
     * description：导入实验室安全准入名单
     * @author：Hezhaoyi
     * @date：2019-4-15
     **************************************************************************************/
    public void importSecurityAccess(String File,Integer labRoomId){
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
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
            Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
            Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
            int index = 0;
            while (rows.hasNext()) {
                LabRoomPermitUser labRoomPermitUser = new LabRoomPermitUser();
                Row row = rows.next();// 获得行数据
                if (index == 0) {         //去表头
                    row = rows.next();
                }
                if(row.getCell(0)!=null){
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String username = row.getCell(0).getStringCellValue();// 用户工号
                    User user = userDAO.findUserByUsername(username);
                    if(!username.equals("") && user != null){
                        //判断是否已在名单中
						LabRoomPermitUser permitUser = this.findPermitUserByUsernameAndLabRoom(username,labRoomId);
						if (permitUser == null) {
                            labRoomPermitUser.setUser(user);
                            labRoomPermitUser.setLabRoom(labRoom);
                            labRoomPermitUser.setFlag(4);
                            labRoomPermitUserDAO.store(labRoomPermitUser);
						}
                    }
                }
                index++;
            }
            input.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /****************************************************************************
     * 功能：通过username和deviceId查询labRoomPermitUser
     * 作者：贺子龙
     * update Hezhaoyi 2019-4-14
     ****************************************************************************/
    public LabRoomPermitUser findPermitUserByUsernameAndLab(String username, int labId){
        String sql = "select u from LabRoomPermitUser u where 1=1";
        if (username!=null&&!username.equals("")
                &&labId!=0) {
            sql+=" and u.user.username like '"+ username +"'";
            sql+=" and u.labRoom.id ="+labId;
            List<LabRoomPermitUser> users = labRoomPermitUserDAO.executeQuery(sql, 0, -1);
            if (users.size()>0) {
                return users.get(0);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
	/**************************************************************************************
	 * description：根据所属类型和名称找到字典表中的记录
	 * @author：郑昕茹
	 * @date：2016-12-19
	 **************************************************************************************/
	public CDictionary findCDictionaryByLwAcademicDegreeByNameAndCategory(String name, String category) {
		String sql = "select c from CDictionary c where 1=1 and CCategory = '" + category + "' and CName = '" + name + "'";
		List<CDictionary> cDictionarys = cDictionaryDAO.executeQuery(sql, 0, -1);
		if (cDictionarys != null && cDictionarys.size() != 0) {
			return cDictionarys.get(0);
		}
		return null;
	}


	/***********************************************************************
	 * 功能：导入文件前的日期格式、数字格式检查
	 * 作者：郑昕茹
	 * 日期：2016-08-05
	 ************************************************************************/
	public String checkRegex(String filePath) throws ParseException {
		String checkResult = "";
		if (filePath != null && !filePath.equals("")) {// 判空
			if (filePath.endsWith("xls") || filePath.endsWith("xlsx")) {// 文件格式
				//判断是不是2007
				boolean isE2007 = false;
				if (filePath.endsWith("xlsx")) {
					isE2007 = true;
				}
				try {
					//建立输入流
					InputStream input = new FileInputStream(filePath);
					Workbook wb = null;
					if (isE2007) {
						wb = new XSSFWorkbook(input);
					} else {
						wb = new HSSFWorkbook(input);
					}
					//获取第一个表单数据
					Sheet sheet = wb.getSheetAt(0);
					//获取第一个表单迭代器
					Iterator<Row> rows = sheet.rowIterator();
					Row rowContent = null;
					String username = "";//工号
					String birth = "";//出生年月
					int a = 0;
					boolean isBreak = false;
					while (rows.hasNext() && !isBreak) {
						username = "";//品名
						birth = "";//是否需要库存提醒
						if (a == 0) {
							rowContent = rows.next();
							a = 1;
						}
						Row row = rows.next();
						int column = sheet.getRow(0).getPhysicalNumberOfCells();
						for (int k = 0; k < column; k++) {
							if (row.getCell(k) == null) {
								String columnName = rowContent.getCell(k).getStringCellValue();
								if (columnName.equals("工号(必填)")) {
									checkResult = "nullError";//品名为空错误
									isBreak = true;// 终止外层循环
									break;
								} else checkResult = "success";
							}
							if (row.getCell(k) != null) {
								row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
								String columnName = rowContent.getCell(k).getStringCellValue();
								if (columnName.equals("出生年月")) {
									String chName = row.getCell(k).getStringCellValue();
									String eL = "[0-9]{4}-[0-9]{2}";
									Pattern p = Pattern.compile(eL);
									Matcher m = p.matcher(chName);
									boolean dateFlag = m.matches();
									if (!dateFlag) {
										checkResult = "dateError";
									} else {
										checkResult = "success";
									}

								}

							}
						}
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				checkResult = "fileError";// 文件格式错误
			}
		}
		return checkResult;
	}

	/****************************************************************************
	 *Description：查找所有实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-26
	 ****************************************************************************/
	public int getReturnedLendingTotals() {
		String sql = "select count(l) from LabRoomLending l where 1=1";
		return ((Long) labRoomLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 *Description：查找所有实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-26
	 ****************************************************************************/
	public List<LabRoomLending> findAllLending(
			LabRoomLending labRoomLending, Integer page, int pageSize) {
		String sql = "select l from LabRoomLending l where 1=1";
		return labRoomLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 *Description：查找所有审核通过的实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-26
	 ****************************************************************************/
	public List<LabRoomLending> findAllPassLending(
			LabRoomLending labRoomLending, Integer page, int pageSize) {
		String sql = "select l from LabRoomLending l where l.lendingStatus=3";//3即为审核通过
		return labRoomLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 *Description：查找所有审核未通过的实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-28
	 ****************************************************************************/
	public List<LabRoomLending> findAllRejectLending(
			LabRoomLending labRoomLending, Integer page, int pageSize) {
		String sql = "select l from LabRoomLending l where l.lendingStatus=2";//2即为审核未通过
		return labRoomLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 *Description：查找所有审核未通过的实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-28
	 ****************************************************************************/
	public List<LabRoomLending> findAllWaitLending(
			LabRoomLending labRoomLending, Integer page, int pageSize) {
		String sql = "select l from LabRoomLending l where l.lendingStatus=1";//1即为未审核
		return labRoomLendingDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 *Description：根据id查找实训室借用单
	 *
	 *@author：邵志峰
	 *@date:2017-06-28
	 ****************************************************************************/
	public LabRoomLending findLabRoomLendingById(Integer idKey) {
		return labRoomLendingDAO.findLabRoomLendingByPrimaryKey(idKey);
	}

	/****************************************************************************
	 *Description：查找所有未审核的实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-28
	 ****************************************************************************/
	public int getWaitLendingTotals() {
		String sql = "select count(l) from LabRoomLending l where l.lendingStatus=1";
		return ((Long) labRoomLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 *Description：查找所有审核通过的实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-28
	 ****************************************************************************/
	public int getPassLendingTotals() {
		String sql = "select count(l) from LabRoomLending l where l.lendingStatus=3";
		return ((Long) labRoomLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 *Description：查找所有审核拒绝的实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-28
	 ****************************************************************************/
	public int getRejectLendingTotals() {
		String sql = "select count(l) from LabRoomLending l where l.lendingStatus=2";
		return ((Long) labRoomLendingDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 *Description：查找所有实验室
	 *
	 *@author：张愉
	 *@date：2017-07-6
	 ****************************************************************************/
	@Override
	public Set<LabRoom> findallLabRoom() {
		// TODO Auto-generated method stub
		return labRoomDAO.findAllLabRooms();
	}

	/****************************************************************************
	 *Description：查找所有实验室
	 *
	 *@author：廖文辉
	 *@date：2018-09-13
	 ****************************************************************************/
	public List<LabRoom> findLabRoomList() {
		StringBuffer hql = new StringBuffer("select l from LabRoom l ");
		hql.append(" where 1=1 ");
		hql.append(" and l.labCategory=1");
		List<LabRoom> labRooms = labRoomDAO.executeQuery(hql.toString(), 0, -1);
		return labRooms;
	}

	/****************************************************************************
	 * 功能：上传设备视频
	 * 作者：贺子龙
	 * 时间：2015-09-28 14:50:33
	 ****************************************************************************/
	@Override
	public void deviceVideoUpload(HttpServletRequest request,
								  HttpServletResponse response, Integer id) {
		// TODO Auto-generated method stub
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String sep = System.getProperty("file.separator");
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean flag = false;
		String fileDir = request.getSession().getServletContext().getRealPath("/") + "upload" + sep + "device" + sep + id;
		//存放文件文件夹名称
		for (; fileNames.hasNext(); ) {
			String filename = (String) fileNames.next();
			CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
			byte[] bytes = file.getBytes();
			if (bytes.length != 0) {
				// 说明申请有附件
				if (!flag) {
					File dirPath = new File(fileDir);
					if (!dirPath.exists()) {
						flag = dirPath.mkdirs();
					}
				}
				String fileTrueName = file.getOriginalFilename();
				//System.out.println("文件名称："+fileTrueName);
				File uploadedFile = new File(fileDir + sep + fileTrueName);
				//System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
				try {
					FileCopyUtils.copy(bytes, uploadedFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				saveLabRoomVideo(fileTrueName, id);
			}
		}
	}

	/****************************************************************************
	 * 功能：上传实验室图片
	 * 作者：孙虎
	 ****************************************************************************/

	public void labRoomDocumentUpload(HttpServletRequest request,
									  HttpServletResponse response, Integer id, int type) {
		String sep = System.getProperty("file.separator");
		String path = sep + "upload" + sep + "labroom" + sep + id;
		shareService.uploadFiles(request, path, "saveLabRoomDocument", id);
	}

	/****************************************************************************
	 * 功能：根据实训室id查询培训
	 * 作者：孙虎
	 ****************************************************************************/
	@Override
	public List<LabRoomTraining> findLabRoomTrainingById(LabRoomTraining train,
														 Integer Id) {
		// TODO Auto-generated method stub
		String sql = "select l from LabRoomTraining l where l.labRoom.id=" + Id;
		if (train != null) {
			if (train.getSchoolTerm() != null) {
				sql += " and l.schoolTerm.id=" + train.getSchoolTerm().getId();
			}
		}
		sql += " order by l.time desc";
		return labRoomTrainingDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：根据培训查询培训名单
	 * 作者：孙虎
	 ****************************************************************************/
	@Override
	public List<LabRoomTrainingPeople> findTrainingPeoplesByTrainingId(int id) {
		// TODO Auto-generated method stub
		String sql = "select p from LabRoomTrainingPeople p where p.labRoomTraining.id=" + id;
		return labRoomTrainingPeopleDAO.executeQuery(sql, 0, -1);
	}

	@Override
	public List<LabRoomPermitUser> findPermitUserByLabRoomId(
			LabRoomPermitUser labRoomPermitUser, int labRoomId, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select u from LabRoomPermitUser u where 1=1";
		if (labRoomId != 0) {
			sql += " and u.labRoom.id =" + labRoomId;
			sql += " and u.flag <>1";//1--单独培训
			if (labRoomPermitUser.getUser() != null) {
				if (labRoomPermitUser.getUser().getCname() != null && !labRoomPermitUser.getUser().getCname().equals("")) {
					sql += " and u.user.cname = '" + labRoomPermitUser.getUser().getCname() + "'";
				}
				if (labRoomPermitUser.getUser().getUsername() != null && !labRoomPermitUser.getUser().getUsername().equals("")) {
					sql += " and u.user.username = '" + labRoomPermitUser.getUser().getUsername() + "'";
				}
			}
			List<LabRoomPermitUser> users = labRoomPermitUserDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
			if (users.size() > 0) {
				return users;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/****************************************************************************
	 * 功能：通过labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public List<LabRoomPermitUser> findPermitUserByLabRoomId(
			Integer labRoomId, int currpage, int pageSize) {
		// TODO Auto-generated method stub
		String sql = "select u from LabRoomPermitUser u where 1=1";
		sql += " and u.labRoom.id =" + labRoomId;
		List<LabRoomPermitUser> users = labRoomPermitUserDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
		return users;
	}

	/****************************************************************************
	 * 功能：根据user对象和学院编号查询所有学生
	 * 作者：周志辉
	 * 时间：2017-08-21
	 ****************************************************************************/
	@Override
	public int findStudentByCnameAndUsername(User user, String academyNumber,
											 Integer labRoomId) {
		String sql = "select count(distinct u) from User u join u.authorities a where (a.id <> 4 and a.id <> 10)";
		sql += " and u.username not in (select p.user.username from LabRoomDevicePermitUsers p where p.labRoomDevice.id = " + labRoomId + " )";
		LabRoom labRoom = findLabRoomByPrimaryKey(labRoomId);
		if (labRoom.getUser() != null && labRoom.getUser().getUsername() != null) {
			sql += " and u.username <> '" + labRoom.getUser().getUsername() + "'";
		}
		// 找到所有的实验室中心主任
		List<User> userList = shareService.findUsersByAuthorityId(4);
		for (User director : userList) {
			sql += " and u.username <> '" + director.getUsername() + "'";
		}
		if (academyNumber != null && !academyNumber.equals("")) {
			sql += " and u.schoolAcademy.academyNumber like '" + academyNumber + "%'";
		}
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}
		// sql+=" and u.userRole<>1"; //可以有教师
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 * 功能：根据user对象和学院编号查询所有学生
	 * 作者：周志辉
	 * 时间：2017-08-21
	 ****************************************************************************/
	public List<User> findStudentByCnameAndUsername(User user, String academyNumber, Integer labRoomId, Integer page, int pageSize) {
		String sql = "select distinct u from User u join u.authorities a where (a.id <> 4 and a.id <> 10)";
		sql += " and u.username not in (select p.user.username from LabRoomPermitUser p where p.labRoom.id = " + labRoomId + " )";
		LabRoom labRoom = findLabRoomByPrimaryKey(labRoomId);
		if (labRoom.getUser() != null && labRoom.getUser().getUsername() != null) {
			sql += " and u.username <> '" + labRoom.getUser().getUsername() + "'";
		}
		// 找到所有的实验室中心主任
		List<User> userList = shareService.findUsersByAuthorityId(4);
		for (User director : userList) {
			sql += " and u.username <> '" + director.getUsername() + "'";
		}

		if (academyNumber != null && !academyNumber.equals("")) {
			sql += " and u.schoolAcademy.academyNumber like '" + academyNumber + "%'";
		}
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}
		//sql+=" and u.userRole<>1";  //可以有教师
		sql += "ORDER BY CASE WHEN u.schoolAcademy.academyNumber like '" + academyNumber + "%' THEN 0 ELSE 1 END";
		sql += " ,u.username desc";
		return userDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 * 功能：根据实验室id查询培训
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public List<LabRoomTraining> findLabRoomTrainingByLabRoomId(LabRoomTraining train,
																Integer labRoomId) {
		// TODO Auto-generated method stub
		String sql = "select l from LabRoomTraining l where l.labRoom.id=" + labRoomId;
		if (train != null) {
			if (train.getSchoolTerm() != null) {
				sql += " and l.schoolTerm.id=" + train.getSchoolTerm().getId();
			}
		}
		sql += " order by l.time desc";
		return labRoomTrainingDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：通过username和labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public LabRoomPermitUser findPermitUserByUsernameAndDeivce(String username, int labRoomId) {
		String sql = "select u from LabRoomPermitUser u where 1=1";
		if (username != null && !username.equals("")
				&& labRoomId != 0) {
			sql += " and u.user.username like '" + username + "'";
			sql += " and u.labRoom.id =" + labRoomId;
			List<LabRoomPermitUser> users = labRoomPermitUserDAO.executeQuery(sql, 0, -1);
			if (users.size() > 0) {
				return users.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/****************************************************************************
	 * 功能：当前用户取消已经预约的培训
	 * 作者：周志辉
	 ****************************************************************************/
	public void cancleTraining(int trainingId) {
		LabRoomTraining train = labRoomTrainingDAO.findLabRoomTrainingByPrimaryKey(trainingId);
		User user = shareService.getUser();
		// 找到对应id
		int labRoomTrainingPeopleId = 0;
		Set<LabRoomTrainingPeople> students = train.getLabRoomTrainingPeoples();
		for (LabRoomTrainingPeople labRoomTrainingPeople : students) {
			if (labRoomTrainingPeople.getUser().getUsername() == user.getUsername()) {
				students.remove(labRoomTrainingPeople);
				labRoomTrainingPeopleId = labRoomTrainingPeople.getId();
			}
		}
		train.setLabRoomTrainingPeoples(students);
		labRoomTrainingDAO.store(train);
		LabRoomTrainingPeople student = labRoomTrainingPeopleDAO.findLabRoomTrainingPeopleByPrimaryKey(labRoomTrainingPeopleId);
		if (student != null) {
			student.setLabRoomTraining(null);
			labRoomTrainingPeopleDAO.remove(student);
			labRoomTrainingPeopleDAO.flush();
		}
	}

	/****************************************************************************
	 * 功能：根据培训查询培训名单--已通过的学生
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public List<LabRoomTrainingPeople> findTrainingPassPeoplesByTrainingId(
			int id) {
		// TODO Auto-generated method stub
		String sql = "select p from LabRoomTrainingPeople p where p.labRoomTraining.id=" + id;
		//sql+=" and p.CDictionary.id=619";
		sql += " and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1'";
		return labRoomTrainingPeopleDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：通过username和labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public LabRoomPermitUser findPermitUserByUsernameAndLabRoom(String username, int labRoomId) {
		String sql = "select u from LabRoomPermitUser u where 1=1";
		if (username != null && !username.equals("")
				&& labRoomId != 0) {
			sql += " and u.user.username like '" + username + "'";
			sql += " and u.labRoom.id =" + labRoomId;
			List<LabRoomPermitUser> users = labRoomPermitUserDAO.executeQuery(sql, 0, -1);
			if (users.size() > 0) {
				return users.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/****************************************************************************
	 * 功能：删除labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public void deletePermitUser(LabRoomPermitUser user) {
		labRoomPermitUserDAO.remove(user);
		labRoomPermitUserDAO.flush();
	}

	/****************************************************************************
	 * 功能：根据培训id查询培训通过的人
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public List<LabRoomTrainingPeople> findPassLabRoomTrainingPeopleByTrainId(
			Integer id) {
		// TODO Auto-generated method stub
		//String sql="select p from LabRoomDeviceTrainingPeople p where p.CDictionary.id=619 and p.labRoomDeviceTraining.id="+id;
		String sql = "select p from LabRoomTrainingPeople p where 1=1 and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1' and p.labRoomTraining.id=" + id;
		return labRoomTrainingPeopleDAO.executeQuery(sql, 0, -1);
	}

	/****************************************************************************
	 * 功能：判断用户是否通过安全准入
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public String securityAccess(String username, Integer id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String str = "success";
		//id对应的设备
		LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(id);

		//是否需要安全准入
		CDictionary a = labRoom.getCDictionaryByAllowSecurityAccess();

		if (a != null && a.getCNumber().equals("1") && a.getCCategory().equals("c_active")) {//需要安全准入
//			//培训形式
//			CDictionary c = labRoom.getCDictionaryByTrainType();
//			if (c != null && c.getId() == 627) {//集中培训
//
//				System.out.println("进入现场培训验证流程");
//				//String sql="select p from LabRoomDeviceTrainingPeople p where p.labRoomDeviceTraining.labRoomDevice.id="
//				//+id+" and p.user.username like '%"+username+"%'"+" and p.CDictionary.id=619";
//				String sql = "select p from LabRoomTrainingPeople p where p.labRoomTraining.labRoom.id="
//						+ id + " and p.user.username like '%" + username + "%' ";
//				sql += " and p.CDictionary.CCategory='c_training_result' and p.CDictionary.CNumber ='1'";
//				List<LabRoomTrainingPeople> peoples = labRoomTrainingPeopleDAO.executeQuery(sql, 0, -1);
//				if (peoples.size() == 0) {
//					str = "error";
//				}
//			}
//			if (c != null && c.getId() == 628) {//单独培训
//				boolean pass = false;
//				Set<LabRoomPermitUser> students = labRoom.getLabRoomPermitUsers();
//				for (LabRoomPermitUser ss : students) {
//					System.out.println(ss);
//					if (Objects.equals(ss.getUser().getUsername(), username)) {
//						pass = true;
//						break;
//					}
//				}
//				if (!pass) {
//					str = "errorType2";
//				}
//			}
//	    	if (c!=null&&c.getId()==686) {//网上答题
//	    		boolean pass=false;
//	    		Set<LabRoomPermitUser> students =labRoom.getLabRoomPermitUsers();
//	    		for (LabRoomPermitUser ss : students) {
//	    			System.out.println(ss);
//	    			if (ss.getUser().getUsername()==username && ss.getFlag() != null && ss.getFlag() == 4) {
//	    				pass=true;break;
//	    			}
//	    		}
//	    		if (!pass) {
//	    			str="errorType3";
//	    		}
//	    	}
			boolean pass = false;
			Set<LabRoomPermitUser> students = labRoom.getLabRoomPermitUsers();
			for (LabRoomPermitUser ss : students) {
//					System.out.println(ss);
				if (Objects.equals(ss.getUser().getUsername(), username)) {
					pass = true;
					break;
				}
			}
			if (!pass) {
				str = "noTrainingError";
			}
		}
		// 获取当前用户
		User currUser = shareService.getUser();
		// 当前用户是否为实验室中心主任
		String judge = (String) request.getSession().getAttribute("selected_role");
//		if (labRoom.getUser() == null
//				|| labRoom.getUser().getUsername() == null
//				|| labRoom.getUser().getUsername().equals("")) {
//			//str = "noManager";
//		}else {
//				if(a!=null&&a.getId()==621){//需要安全准入
//					// 中心主任或设备管理员不需要进行培训
//					if (judge.contains(",1,") ||
//							(labRoom.getUser()!=null&&labRoom.getUser().getUsername().equals(username))) {
//						// do nothing
//					}else {
//						if (findPermitUserByUsernameAndLabRoom(username, id)==null) {
//							str = "error";
//						}else{
//						    // 除学生外都准入
//						    str = "success";
//                        }
//					}
//				}
//			}
		if (a != null && a.getCNumber().equals("1") && a.getCCategory().equals("c_active")) {//需要安全准入
			if (judge.equals("ROLE_STUDENT") ||
					(labRoom.getUser() != null && labRoom.getUser().getUsername().equals(username))) {
				// do nothing
			} else {
				// 除学生外都准入
				str = "success";
			}
		}
		return str;
	}


	/****************************************************************************
	 * 功能：当前用户是否lab_room_attention记录中(实验室)
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public boolean isInTheReaderList(int labId) {
		boolean isIn = false;
		// 建立查询
		String sql = "select r from LabRoomAttention r where 1=1";
		// 查询条件
		sql += " and r.labId = " + labId;
		// 用户
		sql += " and r.username = '" + shareService.getUser().getUsername() + "'";
		// enabled
		sql += " and r.enable = 1";
		// 执行查询
		List<LabRoomAttention> attentions = labRoomAttentionDAO.executeQuery(sql);

		if (attentions != null && attentions.size() > 0) {
			isIn = true;
		}
		return isIn;
	}

	/****************************************************************************
	 * 功能：当前用户是否lab_room_attention记录中(设备)
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public boolean isInTheReaderListDevice(String deviceNumber) {
		boolean isIn = false;
		// 建立查询
		String sql = "select r from LabRoomAttention r where 1=1";
		// 查询条件
		sql += " and r.deviceNo = '" + deviceNumber + "'";
		// 用户
		sql += " and r.username = '" + shareService.getUser().getUsername() + "'";
		// enabled
		sql += " and r.enable = 1";
		// 执行查询
		List<LabRoomAttention> attentions = labRoomAttentionDAO.executeQuery(sql);

		if (attentions != null && attentions.size() > 0) {
			isIn = true;
		}
		return isIn;
	}

	/****************************************************************************
	 * 功能：自动生成一条lab_room_attention记录(设备)
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public void generateLabRoomAttentionRecordDevice(String deviceNumber) {
		// 新建记录
		LabRoomAttention attention = new LabRoomAttention();
		// 设置设备编号
		attention.setDeviceNo(deviceNumber);
		SchoolDevice device = schoolDeviceService.findSchoolDeviceByPrimaryKey(deviceNumber);
		// 设置设备名称
		attention.setDeviceName(device.getDeviceName());
		// 设置阅读人工号
		attention.setUsername(shareService.getUser().getUsername());
		// 设置阅读人姓名
		attention.setCname(shareService.getUser().getCname());
		// 默认enabled = 1
		attention.setEnable(1);
		// 设置阅读时间
		attention.setDate(Calendar.getInstance());
		// 保存
		labRoomAttentionDAO.store(attention);
	}

	/****************************************************************************
	 * 功能：自动生成一条lab_room_attention记录
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public void generateLabRoomAttentionRecord(int labId) {
		// 新建记录
		LabRoomAttention attention = new LabRoomAttention();
		// 设置实验室
		attention.setLabId(labId);
		// 设置阅读人工号
		attention.setUsername(shareService.getUser().getUsername());
		// 设置阅读人姓名
		attention.setCname(shareService.getUser().getCname());
		// 默认enabled = 1
		attention.setEnable(1);
		// 设置阅读时间
		attention.setDate(Calendar.getInstance());
		// 保存
		labRoomAttentionDAO.store(attention);
	}

	/****************************************************************************
	 * 功能：当前实验室的lab_room_attention失效（当预约注意事项有修改时）--设备
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public void disableAllAttentionRecordDevice(String deviceNumber) {
		List<LabRoomAttention> attentions = this.findLabRoomAttentionByDevice(deviceNumber, 1, -1);
		// 循环设置为失效enable = 0
		if (attentions != null && attentions.size() > 0) {
			for (LabRoomAttention labRoomAttention : attentions) {
				if (labRoomAttention.getEnable().equals(1)) {
					labRoomAttention.setEnable(0);
					labRoomAttentionDAO.store(labRoomAttention);
				} else {
					continue;
				}
			}
		}
	}

	/****************************************************************************
	 * 功能：当前设备下lab_room_attention
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public List<LabRoomAttention> findLabRoomAttentionByDevice(String deviceNumber, int page, int pageSize) {
		// 建立查询
		String sql = "select r from LabRoomAttention r where 1=1";
		// 查询条件
		sql += " and r.deviceNo like '" + deviceNumber + "'";
		// 执行查询
		List<LabRoomAttention> attentions = labRoomAttentionDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
		// 返回结果
		return attentions;
	}

	/****************************************************************************
	 * 功能：当前设备下lab_room_attention数量
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public int countLabRoomAttentionByDevice(String deviceNumber) {
		// 建立查询
		String sql = "select count(*) from LabRoomAttention r where 1=1";
		// 查询条件
		sql += " and r.deviceNo like '" + deviceNumber + "'";
		return ((Long) labRoomAttentionDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 * 功能：当前实验室下lab_room_attention数量
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public int countLabRoomAttentionByLab(int labId) {
		// 建立查询
		String sql = "select count(*) from LabRoomAttention r where 1=1";
		// 查询条件
		sql += " and r.labId = " + labId;
		return ((Long) labRoomAttentionDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}


	/****************************************************************************
	 * 功能：当前实验室下lab_room_attention
	 * 作者：贺子龙
	 ****************************************************************************/
	@Override
	public List<LabRoomAttention> findLabRoomAttentionByLab(int labId, int page, int pageSize) {
		// 建立查询
		String sql = "select r from LabRoomAttention r where 1=1";
		// 查询条件
		sql += " and r.labId = " + labId;
		// 执行查询
		List<LabRoomAttention> attentions = labRoomAttentionDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
		// 返回结果
		return attentions;
	}

	/****************************************************************************
	 * 功能：更新某一设备下所有预约的审核结果
	 * 作者：周志辉
	 * @throws ParseException
	 ****************************************************************************/

	@Override
	public void updateReservationResult(int labRoomId) throws ParseException {
		// TODO Auto-generated method stub

	}

	/**
	 * 根据查询条件获取实验室人员培训数据
	 *
	 * @author 周志辉
	 * 2017.08.29
	 */
	@Override
	public List<LabWorkerTraining> findAllLabWorkerTrainingByQuery(Integer currpage, Integer pageSize, int labWorkerId) {
		StringBuffer hql = new StringBuffer("select l from LabWorkerTraining l where 1=1 ");
		hql.append(" and l.labWorker.id = " + labWorkerId);

		return labWorkerTrainingDAO.executeQuery(hql.toString(), (currpage - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 *Description：查找所有软件借用信息
	 *
	 *@author：张愉
	 *@date:2017-09-30
	 ****************************************************************************/
	public List<SoftwareReserve> findAllsoftwareLend(SoftwareReserve softwareReserve, Integer page, int pageSize, int tage, int isaudit) {
		String sql = "select l from SoftwareReserve l where 1=1";
		//新加需求，是我的审核还是我的预约，isAudit  1审核2预约
		if (isaudit == 1) {
			//实训部主任权限可以看到所有
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_PREEXTEACHING") != -1) {

			} else {
				// qun 判断 登陆者是不是超级管理员，实训部教学秘书 是的话下边权限不能进入
				int qun = 0;
				// shareService.getUser().getAuthorities().toString().indexOf(str)
				// 实验室超级管理员，实验教务,选择实验室中心的所有
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1
						|| SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_PRESECTEACHING") != -1) {
					qun++;
					String num = "";
					for (LabRoom iterable_element : labRoomDAO.findAllLabRooms()) {
						num += iterable_element.getId() + ",";
					}
					if (num != "") {
						sql += " and l.labRoom.id in (" + num.substring(0, num.length() - 1) + " ) ";
					}
				}
				// shi 判断登陆者 不是超级管理员，实验教务， 是不是实验室中心主任， 是的话下边权限不能进入
				int shi = 0;
				// 实验室中心主任，看到该中心下 自己实验室下边的实验室预约
				if (qun == 0
						&& (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_EXCENTERDIRECTOR") != -1 || SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_DEPARTMENTHEADER") != -1 || SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_COLLEGELEADER") != -1 || SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_ASSETMANAGEMENT") != -1
				)) {
					//ROLE_DEPARTMENTHEAD,ROLE_COLLEGELEADER,ROLE_ASSETMANAGEMENT为贺子龙  2015-11-28  新增
					shi++;
					String wq = "";
					for (LabCenter iter : shareService.getUser().getLabCentersForCenterManager()) {
						for (LabRoom ite : iter.getLabRooms()) {
							wq += ite.getId() + ",";
						}
					}
					if (wq != "") {
						sql += " and l.labRoom.id in (" + wq.substring(0, wq.length() - 1) + " ) ";
					}
					;

				}
				// guan 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
				int guan = 0;
				// 实验室管理员
				if (qun == 0
						&& shi == 0
						&& SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_LABMANAGER") != -1) {
					guan++;
					String sql1 = "select r from LabRoomAdmin r where r.user.username like '"
							+ shareService.getUser().getUsername() + "'";
					List<LabRoomAdmin> labRoomAdmin = labRoomAdminDAO.executeQuery(sql1, 0, 3);
					if (labRoomAdmin.size() > 0) {
						sql += " and l.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
								+ shareService.getUser().getUsername() + "')";
					}
				}
				// 判断登陆者 不是超级管理员，实验教务, 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
				// System.out.println("---qun--"+qun+"---shi---"+shi+"---guan---"+guan);

				// 老师和学生SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_STUDENT")
				// != -1 ||
				// SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_TEACHER")
				// != -1
				// xi 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,是不是系主任，是的话下边权限不能进入
				int xi = 0;
				if (qun == 0 && shi == 0 && guan == 0 && (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_CFO") != -1 || SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_ASSISTANT") != -1)) {
					xi++;
					if (shareService.getUser().getSchoolAcademy() != null) {
						sql += " and l.user.schoolAcademy.academyNumber='" + shareService.getUser().getSchoolAcademy().getAcademyNumber() + "' ";
					} else {
						sql += " and l.id = -1";
					}
				}
				// 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,不是系主任
				if (qun == 0 && shi == 0 && guan == 0 && xi == 0) {
					sql += " and l.id = -1";
				}
			}
		} else {
			sql += " and l.user.username='" + shareService.getUser().getUsername() + "' ";
		}

		// 1未审核
		if (tage == 1) {
			sql += " and l.approveState=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.approveState=2";
		}
		// 3审核拒绝
		if (tage == 3) {
			sql += " and l.approveState=3";
		}
		// 4审核通过
		if (tage == 4) {
			sql += " and l.approveState=4";
		}
		sql += "   order by l.id desc";
		return softwareReserveDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	public int softwareLendTotals() {
		String sql = "select count(l) from SoftwareReserve l where 1=1";
		return ((Long) softwareReserveDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/****************************************************************************
	 *Description：查找所有软件借用审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	@Override
	public List<SoftwareReserveAudit> findAllSoftwareReserveAudit(SoftwareReserve softwareReserve, String username) {
		String sql = "select l from SoftwareReserveAudit l where 1=1";
		sql += " and l.softwareReserve.id=" + softwareReserve.getId();
		sql += " and l.status=3";
		sql += " and l.user.username='" + username + "'";
		return softwareReserveAuditDAO.executeQuery(sql);
	}

	/****************************************************************************
	 *Description：查找所有软件借用信息-审核状态
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	@Override
	public List<SoftwareReserve> findAllsoftwareLendAudit(SoftwareReserve softwareReserve, Integer page, int pageSize, int state) {
		String sql = "";
		//获取超管权限
		User user = shareService.getUser();
		Set<Authority> auths = user.getAuthorities();
		int au = 0;
		for (Authority a : auths) {
			if (a.getId() == 11) {
				au = 1;
				break;
			}
		}
		if (au != 0) {
			sql = "select l from SoftwareReserve l where 1=1";
		} else {
			sql = "select l from SoftwareReserve l where 1=1";
			sql += " and l.approveState=" + state;
			sql += " and l.user.username='" + user.getCname() + "'";
			//查询当前登录人审核的记录
			if (softwareReserve != null) {
				Set<SoftwareReserveAudit> soft = softwareReserve.getSoftwareReserveAudits();
				if (soft != null) {
					for (SoftwareReserveAudit sa : soft) {
						if (sa.getUser().getUsername() == user.getUsername()) {
							sql += " or l.id=" + sa.getSoftwareReserve().getId();
						}
					}
				}
			}
		}
		sql += " and l.approveState=" + state;
		return softwareReserveDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	/****************************************************************************
	 *Description：判断实训室是否进行了预约的初始化设置
	 *
	 *@author：孙虎
	 *@date:2017-10-12
	 ****************************************************************************/
	@Override
	public Boolean isSettingForLabRoom(Integer labRoomId) {
		LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
		if (labRoom.getCDictionaryByIsAudit() == null) {
			return false;
//		}else if(labRoom.getCDictionaryByLabManagerAudit() == null || labRoom.getCDictionaryByTeacherAudit() ==null ||
//				labRoom.getDean() == null || labRoom.getTrainingCenterDirector() == null || labRoom.getTrainingDepartmentDirrector() == null){
//			return false;
		} else {
			return true;
		}
	}

	/**************************************
	 * 功能：学院下的设备统计
	 * 作者：贺子龙
	 * 日期：2016-03-01
	 **************************************/
	public List listSchoolDeviceAcademy(String academyNumber) {
		String sql = "select * from view_school_device_academy v where 1=1";
		if (!EmptyUtil.isStringEmpty(academyNumber)) {
			sql += " and v.lab_center_id in ( select l.id from lab_center l where 1=1 and l.building_number ='" + academyNumber + "')";
		}
		Query query = entityManager.createNativeQuery(sql);
		// 获取list对象
		List<Object[]> list = query.getResultList();
		return list;
	}

	/**************************************
	 * 功能：根据实训室名称查询实训室
	 * 作者：张德冰
	 * 日期：2018.03.15
	 **************************************/
	public LabRoom findLabRoomByLabRoomName(String labRoomName) {
		String sql = "select l from LabRoom l where 1=1 and l.labRoomName ='" + labRoomName + "'";
		List<LabRoom> list = labRoomDAO.executeQuery(sql);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/****************************************************************************
	 * 功能：根据中心id查询该中心的实验室
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<LabRoom> findLabRoomByLabCenterid(Integer cid) {
		// TODO Auto-generated method stub
		String sql = "select m from LabRoom m where 1=1 and m.isUsed=1 and m.labAnnex.labCenter.id=" + cid;
		return labRoomDAO.executeQuery(sql, 0, -1);
	}

	/**
	 * Description 获取实际审核状态
	 *
	 * @param labRoom 所要审核申请的实验室
	 * @param state   现在的状态
	 * @return 实际审核状态
	 * @author 黄保钱 2018-08-24
	 */
	@Override
	public Integer getAuditNumber(LabRoom labRoom, Integer state) {
		if (state == null || state <= 0) return state;
		Integer auditNumber;
		if (labRoom.getCDictionaryByIsAudit() != null
				&& "是".equals(labRoom.getCDictionaryByIsAudit().getCName())) {
			//demo
			String[] RSWITCH = {"on", "off"};
			Map<String, String> params = new HashMap<>();
			params.put("businessUid", labRoom.getId().toString());
			params.put("businessType", pConfig.PROJECT_NAME + "LabRoomReservation" + labRoom.getLabCenter().getSchoolAcademy().getAcademyNumber());
			String s = HttpClientUtil.doPost(pConfig.auditServerUrl + "audit/getBusinessAuditConfigs", params);
			com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(s);
			String status = jsonObject.getString("status");
			Map auditConfigs = JSON.parseObject(jsonObject.getString("data"), Map.class);
			boolean[] auditArray = new boolean[auditConfigs.size()];
			if (auditConfigs != null && auditConfigs.size() != 0) {
				for (int i = 0; i < auditConfigs.size(); i++) {
					String[] text = ((String) auditConfigs.get(i + 1)).split(":");
					auditArray[i] = text[1].equals(RSWITCH[0]);
				}
			}
			for (auditNumber = state - 1; auditNumber < auditConfigs.size(); auditNumber++) {
				if (auditArray[auditNumber]) {
					break;
				}
			}
			auditNumber++;
		} else {
			auditNumber = 6;
		}
		return auditNumber;
	}

	/**
	 * Description 实验室预约实验分室下拉框
	 *
	 * @param acno 所要预约的实验室
	 * @return 实验中心
	 * @author 廖文辉 2018-08-27
	 */
	public List<LabRoom> findLabRoom(String acno, HttpServletRequest request) {
		StringBuffer hql = new StringBuffer("select distinct l from LabRoom l,LabOpenUpAcademy loua");
        hql.append(" where l.id = loua.labRoomId");
/* 		if(acno!=null && !acno.equals("-1")){
			hql.append(" and l.labCenter.schoolAcademy.academyNumber='"+ acno +"'");
		}*/
		hql.append(" and l.labRoomActive=1 and l.labRoomReservation=1");
		// 浙江建设{实验室管理员和物联管理员不可预约自己管理的实验室}
		if (pConfig.PROJECT_NAME.equals("zjcclims") &&
				(request.getSession().getAttribute("selected_role").equals("ROLE_LABMANAGER") || request.getSession().getAttribute("selected_role").equals("ROLE_CABINETADMIN"))) {
			hql.append(" and l not in (select l from LabRoomAdmin lra, LabRoom l where lra.labRoom = l and lra.user.username = '" + shareService.getUserDetail().getUsername() + "')");
		}
//		if (acno != null && !acno.equals("-1")) {
//			// 开放范围{20190506为全校}
//			hql.append(" and l in (select l from LabRoom l join l.openSchoolAcademies openSAs where (openSAs.academyNumber = '" + acno + "' or openSAs.academyNumber='20190506'))");
//			hql.append(" order by case when l.labCenter.schoolAcademy.academyNumber='" + acno + "' then 0 else 1 end ");
//		}
		User user = shareService.getUser();
		Set<Authority> authorities = user.getAuthorities();
		if(acno!=null && !acno.equals("-1")){// 20190506全校
			// 开放范围/开放对象
			hql.append(" and (");
			hql.append(" ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 1 and loua.authorityId = -1)");
			for(Authority authority : authorities){
				hql.append(" or ((loua.academyNumber = '" + acno + "' or loua.academyNumber='20190506') and loua.type = 1 and loua.authorityId = " + authority.getId() + ")");
			}
			hql.append(")");
			hql.append(" order by case when l.labCenter.schoolAcademy.academyNumber='" + acno + "' then 0 else 1 end ");
		}
		List<LabRoom> labRooms = labRoomDAO.executeQuery(hql.toString(), 0, -1);
		return labRooms;
	}

	/**
	 * Description 根据实验室编号找到实验室下所有门禁的端口号
	 *
	 * @param roomId 实验室主键
	 * @author 陈乐为 2018-8-29
	 */
	@Override
	public String findAgentPortByRoomId(Integer roomId) {
		String sql = "select l from LabRoomAgent l where l.labRoom.id =" + roomId;
		sql += "and l.CDictionary.id = 548 group by l.manufactor";
		List<LabRoomAgent> list = labRoomAgentDAO.executeQuery(sql);
		String agentPort = list.get(0).getManufactor();
		int num = 0;//循环次数
		for (LabRoomAgent agent : list) {
			if (num > 0) {
				agentPort = agentPort + "," + agent.getManufactor();
			}
			num = num + 1;
		}
		return agentPort;
	}

	/****************************************************************************
	 * Description 查询出所有实验室
	 *
	 * @author 戴昊宇
	 * @date 2017-8-10
	 ****************************************************************************/
	@Override
	public List<LabRoom> findAllLabroom() {
		// TODO Auto-generated method stub
		String sql = "select l from LabRoom l where l.isUsed=1 ";
		return labRoomDAO.executeQuery(sql, 0, -1);
	}

	/**
	 * Description 同步电源控制器相关策略
	 *
	 * @param flag   0 关电源，1 开电源
	 * @param insUid 硬件id
	 * @return
	 * @throws IOException
	 * @author 陈乐为 2018-9-10
	 */
	@Override
	public String syncSmartAgent(Integer flag, String insUid) throws IOException {
		int close_equip = 0;
		int open_equip = 1;
		// 设备
		LabRoomAgent agent = labRoomAgentDAO.findLabRoomAgentById(Integer.valueOf(insUid));
		// 找到对应的ip
		String serverUrl = "";//服务器地址
		String serverIp = agent.getCommonServer().getServerIp();
		// 仪器电源控制器信息
		String ipAddress = agent.getHardwareIp();
		String agentNo = agent.getManufactor();
		if (flag == close_equip) {
			serverUrl = "http://" + serverIp + ":8082/services/ofthings/acldoor.asp?cmd=close_equip&ip=" + ipAddress + "&sn=" + agentNo;
		}
		if (flag == open_equip) {
			serverUrl = "http://" + serverIp + ":8082/services/ofthings/acldoor.asp?cmd=open_equip&ip=" + ipAddress + "&sn=" + agentNo;
		}
		System.out.println("url:" + serverUrl + "\n");
		Socket socket = new Socket(serverIp, 8082);
		//2、获取输出流，向服务器端发送信息
		OutputStream os = socket.getOutputStream();//字节输出流
		PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
		//3、获取输入流，并读取服务器端的响应信息
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		pw.write("GET " + serverUrl + " HTTP/1.1\n");
		pw.write("Content-type: application/x-java-serialized-object\n");
		pw.write("Cache-control: no-cache\n");
		pw.write("Pragma: no-cache\n");
		pw.write("User-Agent: Sakai Java/1.7.0_111\n");
		pw.write("Host: " + serverIp + ":8082\n");
		pw.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\n");
		pw.write("Connection: keep-alive\n");
		pw.write("Content-Length:0\n");
		pw.write("\n");
		pw.flush();
		String inputline = "";
		String info = ""; // 返回值
		int count = 0;
		while ((inputline = br.readLine()) != null) {
			count++;
			System.out.println("return:" + inputline);
			if (count == 5) {
				info += inputline;
			}
		}
		socket.shutdownInput();//关闭输入流
		pw.close();
		os.close();
		br.close();
		is.close();
		socket.close();
		System.out.println("to js:" + info);
		info = "{'result':true}";
		JSONObject results = JSONObject.fromObject(info);
		String returnString;
		try {
			returnString = results.get("result").toString();
		} catch (Exception e) {
			return "error";
		}
		return returnString;
	}

	/**
	 * Description 根据学院查找用户
	 *
	 * @param roomId 实验室ID
	 * @param acno   学院编号
	 * @return
	 * @author 廖文辉 2018-10-17
	 */
	public List<User> findUserByAcademy(Integer roomId, String acno) {
		// TODO Auto-generated method stub
		String sql = "select u from User u where 1=1";
		if (roomId != null && roomId != 0) {
			sql += " and u.username not in(select a.user.username from LabRoomAdmin a where a.labRoom.id=" + roomId + ")";
		}
		if (acno != null && !acno.equals("")) {
			sql += " and u.schoolAcademy.academyNumber = '" + acno + "'";
		}
		return userDAO.executeQuery(sql, 0, -1);

	}

	/****************************************************************************
	 * 功能：根据设备查询硬件
	 * 作者：廖文辉
	 * 时间：2018-12-12
	 ****************************************************************************/
	public List<LabRoomAgent> findLabRoomAgentByDeviceId(Integer id) {
		String sql = "select l from LabRoomAgent l  join l.labRoomDevices lds where 1=1";
		sql += " and lds.id=" + id;
		return labRoomAgentDAO.executeQuery(sql, 0, -1);
	}

	/**
	 * Description 导入物联和实验室管理员
	 *
	 * @param File 文件路径
	 * @return
	 * @author 廖文辉 2018-12-24
	 */
	public String[] importLabRoomAdmin(String File, Integer roomId, Integer typeId) {
		//记录添加成功数量
		Integer suc = 0;
		//记录添加没有成功数量
		Integer usc = 0;
		//失敗原因
		String result = "";
		//判断是不是2007
		boolean isE2007 = false;
		if (File.endsWith("xlsx")) {
			isE2007 = true;
		}
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			//根据文件格式（2003或2007）来初始化
			Workbook wb = null;
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet = wb.getSheetAt(0);
			int a = 0;
			//获取第一个表单迭代器
			Iterator<Row> rows = sheet.rowIterator();
			int i = 0;
			while (rows.hasNext()) {

				if (i == 0) {
					rows.next();
				}
				i++;
				Row row = rows.next();//获取行数据
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				String username = row.getCell(0).getStringCellValue();//姓名
				//判冲
				a++;
				User user = userDAO.findUserByUsername(username);
				LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
				List<LabRoomAdmin> labRoomAdmins = findLabRoomAdminByUsernameAndType(roomId, username, typeId);
				if (user != null && labRoomAdmins.size() == 0) {
					LabRoomAdmin labRoomAdmin = new LabRoomAdmin();
					labRoomAdmin.setLabRoom(labRoom);
					labRoomAdmin.setUser(user);
					labRoomAdmin.setTypeId(typeId);
					labRoomAdminDAO.store(labRoomAdmin);
					if (typeId == 2) {
						// 给用户赋予权限
						Set<Authority> ahths = user.getAuthorities();
						Authority auth = authorityDAO.findAuthorityByAuthorityName("CABINETADMIN").iterator().next();
						if (auth != null) {
							ahths.add(auth);
							user.setAuthorities(ahths);
							userDAO.store(user);
						}
					} else {
						// 给用户赋予权限
						Set<Authority> ahths = user.getAuthorities();
						Authority auth = authorityDAO.findAuthorityByAuthorityName("LABMANAGER").iterator().next();
						if (auth != null) {
							ahths.add(auth);
							user.setAuthorities(ahths);
							userDAO.store(user);
						}
					}
					suc++;
					RefuseItemBackup refuseItemBackup=new RefuseItemBackup();
					refuseItemBackup.setBusinessId(roomId.toString());
					if(typeId==2) {
						refuseItemBackup.setType("LabAgentAdmin");
						refuseItemBackup.setOperationItemName("添加物联管理员"+"("+user.getCname()+user.getUsername()+")");
					}else{
						refuseItemBackup.setType("LabRoomAdmin");
						refuseItemBackup.setOperationItemName("添加实验室管理员"+"("+user.getCname()+user.getUsername()+")");
					}
					refuseItemBackup.setTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
					refuseItemBackup.setLabRoomName(labRoom.getLabRoomName());
					refuseItemBackup.setCreator(shareService.getUserDetail().getUsername());
					refuseItemBackupDAO.store(refuseItemBackup);
				} else if (user == null) {
					usc++;
					result += "第" + a + "行的工号" + username + "不存在" + ";";
				} else if (labRoomAdmins.size() != 0) {
					usc++;
					result += "第" + a + "行的" + username + "已经是物联管理员或实验室管理员，无法再添加" + ";";
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] mess = {"成功导入" + suc.toString() + "条", "导入失败" + usc.toString() + "条", result};
		return mess;

	}

	/**
	 * Description 根据username和typeId找实验室管理员
	 *
	 * @param roomId 实验室id
	 * @param username 用户名
	 * @param typeId   管理员种类
	 * @return
	 * @author 廖文辉 2018-12-24
	 * @author 陈乐为 2019-4-4 判冲加实验室
	 */
	public List<LabRoomAdmin> findLabRoomAdminByUsernameAndType(int roomId, String username, int typeId) {
		String sql = "select l from LabRoomAdmin l where 1=1";
		sql += " and l.user.username = '" + username + "'";
		sql += " and (l.typeId=1 or l.typeId=2)";
		sql += " and l.labRoom.id="+roomId;
		return labRoomAdminDAO.createQuery(sql, -1, -1).getResultList();
	}

	/********************************
	 * 功能：管理员上传
	 * 作者：廖文辉
	 * 日期：2018-12-26
	 *********************************/
	@Override
	public String adminUpload(HttpServletRequest request) {

		// TODO Auto-generated method stub
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String sep = System.getProperty("file.separator");
		Map files = multipartRequest.getFileMap();
		Iterator fileNames = multipartRequest.getFileNames();
		boolean flag = false;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String PathDir = "/upload/" + dateformat.format(new Date());
		String fileDir = request.getSession().getServletContext().getRealPath(PathDir);
		String returnFilePathName = "";
		//存放文件文件夹名称
		for (; fileNames.hasNext(); ) {
			String filename = (String) fileNames.next();
			CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
			byte[] bytes = file.getBytes();
			if (bytes.length != 0) {
				// 说明申请有附件
				if (!flag) {
					File dirPath = new File(fileDir);
					if (!dirPath.exists()) {
						flag = dirPath.mkdirs();
					}
				}
				String fileTrueName = file.getOriginalFilename();
				returnFilePathName = PathDir + "/" + fileTrueName;
				//System.out.println("文件名称："+fileTrueName);
				File uploadedFile = new File(fileDir + sep + fileTrueName);
				//System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
				try {
					FileCopyUtils.copy(bytes, uploadedFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return returnFilePathName;
	}

	/**
	 * Description 导入仪器设备
	 *
	 *@param roomId 实验室
	 * @param File 文件路径
	 * @return
	 * @author 廖文辉 2018-12-26
	 */
	public String[] importLabRoomDevice(String File, Integer roomId)throws Exception {
		//记录添加成功数量
		Integer suc = 0;
		//记录添加没有成功数量
		Integer usc = 0;
		//失敗原因
		String result = "";
		//判断是不是2007
		boolean isE2007 = false;
		if (File.endsWith("xlsx")) {
			isE2007 = true;
		}
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			//根据文件格式（2003或2007）来初始化
			Workbook wb = null;
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet = wb.getSheetAt(0);
			int a = 0;
			//获取第一个表单迭代器
			Iterator<Row> rows = sheet.rowIterator();
			int i = 0;
			while (rows.hasNext()) {

				if (i == 0) {
					rows.next();
				}
				i++;
				Row row = rows.next();//获取行数据
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				String deviceNumber = row.getCell(0).getStringCellValue();//姓名
				//判冲
				a++;
				// 判断设备是否已经存在实验室中
				boolean isExist = labRoomDeviceService.ifLabRoomDeviceExist(deviceNumber,roomId);

				SchoolDevice schoolDevice=schoolDeviceDAO.findSchoolDeviceByPrimaryKey(deviceNumber);
				LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(roomId);
				if(isExist) {// 设备已经存在实验室中
					usc++;
					result += "第" + a + "行的设备号" + deviceNumber + "已经存在该实验室中" + ";";
				}else if (schoolDevice != null) {
					LabRoomDevice labRoomDevice = new LabRoomDevice();
					labRoomDevice.setLabRoom(labRoom);
					labRoomDevice.setSchoolDevice(schoolDevice);
					labRoomDevice = labRoomDeviceService.save(labRoomDevice);
					// 设备二维码
					String url = shareService.getDimensionalCode(labRoomDevice);
					labRoomDevice.setDimensionalCode(url);

					// 设备置为正常使用
					CDictionary cd = shareService.getCDictionaryByCategory("c_lab_room_device_status", "1");
					labRoomDevice.setCDictionaryByDeviceStatus(cd);

					labRoomDeviceService.save(labRoomDevice);
					suc++;
				}else {
					usc++;
					result += "第" + a + "行的设备号" + deviceNumber + "不存在" + ";";
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] mess = {"成功导入" + suc.toString() + "条", "导入失败" + usc.toString() + "条", result};
		return mess;

	}
	/**
	 * Description 项目实验室下拉框
	 *
	 * @return
	 * @author 廖文辉 2019-01-07
	 */
	public List<LabRoom> findLabRoomBySchoolAcademy(String acno){
		StringBuffer hql = new StringBuffer("select l from LabRoom l where 1=1 ");
		if(acno!=null&&!"".equals(acno)){
			hql.append( "and l.labCenter.schoolAcademy.academyNumber='"+acno+"'");
		}
		hql.append(" and l.labRoomActive=1");//使用状态：1--可用  0--不可用
		hql.append(" and l.labCategory=1");
		return labRoomDAO.executeQuery(hql.toString());
	}
	/**
	 * Description 查找本学院的人
	 *
	 * @return
	 * @author 廖文辉 2019-01-10
	 */
	public List<User> findUserByacno(String academyNumber, String search, int currpage, int pagesize){
		String sql="select u from User u where 1=1";
		if(pConfig.PROJECT_NAME.equals("fdulims")){
			// 复旦需要添加外院的人员
		}else if (academyNumber != null && !academyNumber.equals("")) {
			sql += " and u.schoolAcademy.academyNumber = '" + academyNumber + "'";
		}
		// 查询条件
		if (!EmptyUtil.isStringEmpty(search)) {
			sql += " and (u.cname like '%"+ search +"%' or u.username like '%"+ search +"%')";
		}
		sql += " and u.enabled=true";
		// 筛选老师和近五年的学生
		sql += " and (u.userRole = 1 or u.grade > (" + Calendar.getInstance().get(Calendar.YEAR) + "-6))";
		return userDAO.executeQuery(sql,(currpage-1)*pagesize, pagesize);
	}
	/**
	 * Description 查找本学期的操作日志
	 *
	 * @return
	 * @author 廖文辉 2019-01-10
	 */
	public List<Object[]> getRefuseItemBackup(Integer roomId){
		int termId=shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		String sql ="select r from RefuseItemBackup r where 1=1";
		if(roomId!=null&&!"".equals(roomId)){
			sql+=" and r.businessId="+roomId;
		}
		sql+=" and r.term="+termId;
		List<RefuseItemBackup> refuseItemBackups=refuseItemBackupDAO.executeQuery(sql);
		List<Object[]> list = new ArrayList<>();
		for(RefuseItemBackup refuseItemBackup:refuseItemBackups){
			Object[] objects=new Object[3];
			objects[0]=refuseItemBackup.getCreator();
			User user=userDAO.findUserByUsername(refuseItemBackup.getCreator());
			if(user!=null) {
				objects[1] = user.getCname();
			}else{
				objects[1]="";
			}
			objects[2]=refuseItemBackup.getOperationItemName();
			list.add(objects);
		}
		return list;
	}

	/**
	 * Description 刷新权限接口（jwt）
	 *
	 * @param id 实验室id
	 * @author 黄保钱 2019-1-27
	 */
	@Override
	public String sendRefreshInterfaceByJWT(Integer id) {
		// 先找到实验室
		String sql = "select agent from LabRoomAgent agent where 1=1 ";
		if(id != null){
			sql += " and agent.labRoom.id=" + id;
		}
		// 找到该实验室下为电源控制器和门禁的物联设备
		sql += " and agent.CDictionary.CCategory = 'c_agent_type' ";
		sql += " and (agent.CDictionary.CNumber = '2' or agent.CDictionary.CNumber = '4') ";
		List<LabRoomAgent> labRoomAgents = labRoomAgentDAO.executeQuery(sql);
		String s = "";// 物联脚本返回信息
		for(LabRoomAgent labRoomAgent: labRoomAgents){
			Map<String, String> params = new HashMap<>();
			// 物联设备类型CDictionary的id
			params.put("hardwaretype", labRoomAgent.getCDictionary().getId().toString());
			// 物联服务器ip
			params.put("commonserver", labRoomAgent.getCommonServer().getServerIp());
			// 物联服务器端口
			params.put("port", labRoomAgent.getCommonServer().getServerSn());
			// 硬件IP
			params.put("hardwareip", labRoomAgent.getHardwareIp());
			String[] jwtStr = new String[2];
			// 获取jwt
			Authorization a = AuthorizationUtil.getAuthorization(shareService.getUserDetail().getUsername(), params);
			jwtStr[0] = "Authorization";
			jwtStr[1] = a.getJwtToken();
			// 以jwt形式发送请求
			String url = "http://"+labRoomAgent.getCommonServer().getServerIp()+":"+labRoomAgent.getCommonServer().getServerSn()+"/reservation/regcard/";
			s = HttpClientUtil.doPost(url + "regcard/", jwtStr);
		}
		return s;
	}

	/**
	 * Description 远程开门接口（jwt）
	 *
	 * @param agentId agent的id
	 * @param doorIndex 门号
	 * @author 陈乐为 2019-2-23
	 */
	@Override
	public String sendOpenDoorInterfaceByJWT(Integer agentId, Integer doorIndex) {
		// 设备
		LabRoomAgent labRoomAgent = labRoomAgentDAO.findLabRoomAgentById(agentId);
		User user = shareService.getUserDetail();

//		Map<String, String> params = new HashMap<>();
//		// 物联设备类型CDictionary的id
//		params.put("hardwaretype", labRoomAgent.getCDictionary().getId().toString());
//		// 物联服务器ip
//		params.put("commonserver", labRoomAgent.getCommonServer().getServerIp());
//		// 物联服务器端口
//		params.put("port", labRoomAgent.getCommonServer().getServerSn());
//		// 硬件IP
//		params.put("hardwareip", labRoomAgent.getHardwareIp());
//		// 门号
//		params.put("doorindex", doorIndex.toString());
//		String[] jwtStr = new String[2];
//		// 获取jwt
//		Authorization a = AuthorizationUtil.getAuthorization(shareService.getUserDetail().getUsername(), params);
//		jwtStr[0] = "Authorization";
//		jwtStr[1] = a.getJwtToken();
		// 以jwt形式发送请求
//		String s =  HttpClientUtil.doPost(pConfig.refreshReservationUrl + "add/", jwtStr);
		// 2019年6月14日  新版物联
		Map<String,String> headers = new HashMap<>();
		headers.put("Authorization", "gvsunopendoorbyfallenleaf");

		Boolean flag = shareService.getAuthToOpenDoor(labRoomAgent.getLabRoom().getId());
		String s = "noAuth";
		if (flag) {
			String url = "http://"+labRoomAgent.getCommonServer().getServerIp()+":" + labRoomAgent.getCommonServer().getServerSn() +
					"/iot/acldoor/"+ labRoomAgent.getHardwareIp() +"/opendoor/"+ doorIndex+"/"+user.getUsername()+"/"+user.getCname();

			String[] jwtStr = new String[2];
			// 获取jwt
			jwtStr[0] = "Authorization";
			jwtStr[1] = "gvsunopendoorbyfallenleaf";

			System.out.println(url);
			s = HttpClientUtil.doPost(url, jwtStr);
		}

		return s;
	}

	/**
	 * Description 开关电源控制器（jwt）
	 * @param flag 1开，0关
	 * @param agentId 硬件id
	 * @return
	 * @author 陈乐为 2019-3-5
	 */
	@Override
	public String sendOpenAgentInterfaceByJWT(Integer flag, Integer agentId) {
		// 设备
		LabRoomAgent labRoomAgent = labRoomAgentDAO.findLabRoomAgentById(agentId);

		Map<String, String> params = new HashMap<>();
		// 物联设备类型CDictionary的id
		params.put("hardwaretype", labRoomAgent.getCDictionary().getId().toString());
		// 物联服务器ip
		params.put("commonserver", labRoomAgent.getCommonServer().getServerIp());
		// 物联服务器端口
		params.put("port", labRoomAgent.getCommonServer().getServerSn());
		// 硬件IP
		params.put("hardwareip", labRoomAgent.getHardwareIp());
		// cmd
		if(flag == 1) {
			params.put("cmd", "open");
		}else {
			params.put("cmd", "close");
		}
		String[] jwtStr = new String[2];
		// 获取jwt
		Authorization a = AuthorizationUtil.getAuthorization(shareService.getUserDetail().getUsername(), params);
		jwtStr[0] = "Authorization";
		jwtStr[1] = a.getJwtToken();
		// 以jwt形式发送请求
		String url = "http://"+labRoomAgent.getCommonServer().getServerIp()+":"+labRoomAgent.getCommonServer().getServerSn()+"/reservation/guard/";
		String s =  HttpClientUtil.doPost(url + "guard/", jwtStr);
		return s;
	}

	/**
	 * 门禁进出列表
	 * @param username 用户名
	 * @param cname 用户姓名
	 * @param startDate 起始门禁刷卡时间
	 * @param endDate 结束门禁刷卡时间
	 * @param labRoomName 实验室名称
	 * @return 列表
	 * @author 黄保钱 2019-3-4
	 */
	@Override
	public List<Object[]> getLabDoorRecords(String username, String cname, String startDate, String endDate, String labRoomName) {
		String hql = "select distinct u.cname, u.username, sa.academy_name, scl.class_name, sm.major_name, CONCAT(l.lab_room_name,'(',l.lab_room_number,')'), ch.datetime, ch.status from common_hdwlog ch ";
		hql += " left join lab_room_agent lra on ch.hardwareid = lra.hardware_ip and ch.doorindex = lra.doorindex ";
		hql += " left join lab_room l on l.id = lra.lab_room_id ";
		hql += " left join user u on u.username = ch.username ";
		hql += " left join school_academy sa on sa.academy_number = u.academy_number ";
		hql += " left join school_classes scl on scl.class_number = u.classes_number ";
		hql += " left join school_major sm on sm.major_number = u.major_number ";
		hql += " where 1=1 ";
		if (labRoomName != null && !"".equals(labRoomName)) {
			hql += " and l.lab_room_name like '%" + labRoomName + "%'";
		}
		if (username != null && !"".equals(username)) {
			hql += " and u.username like '%" + username + "%'";
		}
		if (cname != null && !"".equals(cname)) {
			hql += " and u.cname like '%" + cname + "%'";
		}
		if (startDate != null && !"".equals(startDate)) {
			hql += " and ch.datetime > '" + startDate + "'";
		}
		if (endDate != null && !"".equals(endDate)) {
			hql += " and ch.datetime < '" + endDate + "'";
		}
		Query query = entityManager.createNativeQuery(hql);
		List list = query.getResultList();
		return list;
	}

	/**
	 * 门禁进出总数
	 * @param username 用户名
	 * @param cname 用户姓名
	 * @param startDate 起始门禁刷卡时间
	 * @param endDate 结束门禁刷卡时间
	 * @param labRoomName 实验室名称
	 * @return 总数
	 * @author 黄保钱 2019-3-4
	 */
	@Override
	public int getLabDoorRecordsNum(String username, String cname, String startDate, String endDate, String labRoomName) {
		String hql = "select count(table1.id) from (select ch.id from common_hdwlog ch ";
		hql += " left join lab_room_agent lra on ch.hardwareid = lra.hardware_ip and ch.doorindex = lra.doorindex ";
		hql += " left join lab_room l on l.id = lra.lab_room_id ";
		hql += " left join user u on u.username = ch.username ";
		hql += " left join school_academy sa on sa.academy_number = u.academy_number ";
		hql += " left join school_classes scl on scl.class_number = u.classes_number ";
		hql += " left join school_major sm on sm.major_number = u.major_number ";
		hql += " where 1=1 ";
		if (labRoomName != null && !"".equals(labRoomName)) {
			hql += " and l.lab_room_name like '%" + labRoomName + "%'";
		}
		if (username != null && !"".equals(username)) {
			hql += " and u.username like '%" + username + "%'";
		}
		if (cname != null && !"".equals(cname)) {
			hql += " and u.cname like '%" + cname + "%'";
		}
		if (startDate != null && !"".equals(startDate)) {
			hql += " and ch.datetime > '" + startDate + "'";
		}
		if (endDate != null && !"".equals(endDate)) {
			hql += " and ch.datetime < '" + endDate + "'";
		}
		hql += " group by ch.datetime, u.username) as table1";
		Query query = entityManager.createNativeQuery(hql);
		return ((BigInteger)query.getSingleResult()).intValue();
	}

	/**
	 * Description 保存批量设置的实验室管理员/物联管理员
	 * @param type_code
	 * @param labs
	 * @param users
	 * @return
	 * @author 陈乐为 2019-4-3
	 */
	public String saveMultipleManager(int type_code, String[] labs, String[] users) {
		String memo = "异常提示：";
		for (String lab : labs) {
			int lab_id = Integer.valueOf(lab);
			LabRoom labRoom = this.findLabRoomByPrimaryKey(lab_id);
			for (String username : users) {
				User user = shareService.findUserByUsername(username);
				// 查询历史数据，判断是否已经是该实验室的管理人员
				// 物联管理员和实验室管理员不重复--物联读卡
				String sql = "select username from lab_room_admin where lab_room_id="+lab_id;
				sql += " and username='"+ username +"'";
//				sql += " and type_id="+type_code;
				Query query = entityManager.createNativeQuery(sql);
				List<Object[]> admins = query.getResultList();
				if(admins.size()==0) {// 不是管理员则新增
					LabRoomAdmin admin = new LabRoomAdmin();
					admin.setLabRoom(labRoom);
					admin.setUser(user);
					admin.setTypeId(type_code);
					labRoomAdminDAO.store(admin);
					labRoomAdminDAO.flush();
					// 给用户赋予权限
					if (type_code == 2) {// 物联管理员
						Set<Authority> ahths = user.getAuthorities();
						Authority a = authorityDAO.findAuthorityByAuthorityName("CABINETADMIN").iterator().next();
						if(a != null) {
							ahths.add(a);
							user.setAuthorities(ahths);
							userDAO.store(user);
						}
					} else {// 实验室管理员
						Set<Authority> ahths = user.getAuthorities();
						Authority a = authorityDAO.findAuthorityByAuthorityName("LABMANAGER").iterator().next();
						if(a != null) {
							ahths.add(a);
							user.setAuthorities(ahths);
							userDAO.store(user);
						}
					}
					// 创建操作日志
					RefuseItemBackup refuseItemBackup=new RefuseItemBackup();
					refuseItemBackup.setBusinessId(lab);
					if(type_code==2) {
						refuseItemBackup.setType("LabAgentAdmin");
						refuseItemBackup.setOperationItemName("添加物联管理员"+"("+user.getCname()+user.getUsername()+")");
					}else{
						refuseItemBackup.setType("LabRoomAdmin");
						refuseItemBackup.setOperationItemName("添加实验室管理员"+"("+user.getCname()+user.getUsername()+")");
					}
					refuseItemBackup.setTerm(shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId());
					refuseItemBackup.setLabRoomName(labRoom.getLabRoomName());
					refuseItemBackup.setCreator(shareService.getUserDetail().getUsername());
					refuseItemBackupDAO.store(refuseItemBackup);
				}else {
//					memo += user.getCname()+"["+user.getUsername()+"]已经是"+labRoom.getLabRoomName()+"["+labRoom.getLabRoomNumber()+"]的管理人员<br>";
					memo += user.getCname()+"已经是"+labRoom.getLabRoomName()+"的管理人员；";
				}
			}
		}
		return memo;
	}
	/**
	 * @Description: 查询某一实验中心下有设备的实验室
	 * @Author: 徐明杭
	 * @CreateDate: 2019/4/4 14:01
	 */
	public List<LabRoom> findLabRoomWithDevices(Integer isReservation,String acno){
		StringBuffer sql = new StringBuffer("select distinct m from LabRoom m inner join m.labRoomDevices ld where 1=1 ");
		sql.append(" and m.isUsed = 1");
		if (isReservation != null && isReservation == 1) {
			sql.append(" and m.labRoomActive = 1");
		}
		sql.append(" and m.labCategory=1 and m.schoolAcademy.academyNumber='"+acno+"'");
		return labRoomDAO.executeQuery(sql.toString(), 0, -1);
	}
	/**
	 * @Description: 根据时间和内容的输入返回labRoomDeviceLending
	 * @Author: 徐明杭
	 * @CreateDate: 2019/4/9 16:31
	 */
	public LabRoomDeviceLending checkTimeAndContent(String startTime, String returnTime, String content){
		// 判断表单内的值并翻页保存
		try {
		SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy");
		if ("".equals(startTime)&& "".equals(returnTime) && content==null){
			return new LabRoomDeviceLending();
		}else if( ("".equals(startTime)&& "".equals(returnTime))&& content!=null){
			LabRoomDeviceLending labRoomDeviceLending = new LabRoomDeviceLending();
			labRoomDeviceLending.setContent(content);
			return labRoomDeviceLending;
		}else if (("".equals(startTime) && "".equals(returnTime))&& content==null){
			LabRoomDeviceLending labRoomDeviceLending = new LabRoomDeviceLending();
			Date date1 =sdf.parse(startTime);
			Date date2 =sdf.parse(returnTime);
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(date1);
			end.setTime(date2);
			labRoomDeviceLending.setStartTime(start);
			labRoomDeviceLending.setReturnTime(end);
			return labRoomDeviceLending;
		}else if (!"".equals(startTime)&& !"".equals(returnTime)&& content!=null){
			LabRoomDeviceLending labRoomDeviceLending = new LabRoomDeviceLending();
			Date date1 =sdf.parse(startTime);
			Date date2 =sdf.parse(returnTime);
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(date1);
			end.setTime(date2);
			labRoomDeviceLending.setStartTime(start);
			labRoomDeviceLending.setReturnTime(end);
			labRoomDeviceLending.setContent(content);
			return labRoomDeviceLending;
		}
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * @Description: 导出实验室分室所有考勤记录
	 * @Author: 徐明杭
	 * @CreateDate: 2019/4/11 13:31
	 */
	public void exportEntranceList(List<net.zjcclims.common.LabAttendance> labAttendances, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//格式化时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//新建一个mapList集合
		List<Map> mapList = new ArrayList<Map>();
		for(net.zjcclims.common.LabAttendance labAttendance : labAttendances){
			// 新建一个HashMap对象
			Map<String, String> map = new HashMap<>();
			// 学生姓名
			if (labAttendance.getCname()!=null){
				map.put("cname",labAttendance.getCname());
			}else{
				map.put("cname","");
			}
			// 学生工号
			if (labAttendance.getUsername()!=null){
				map.put("username",labAttendance.getUsername());
			}else {
				map.put("username","");
			}
			// 学院代号
			if (labAttendance.getAcademyName()!=null){
				map.put("academyName",labAttendance.getAcademyName());
			}else {
				map.put("academyName","");
			}
			// 考勤时间
			if (labAttendance.getAttendanceTime()!=null){
				map.put("attendanceTime",labAttendance.getAttendanceTime());
			}else{
				map.put("attendanceTime","");
			}
			mapList.add(map);
		}
		//新建一个用来存放分sheet的List对象
		List<List<Map>> wrapList = new ArrayList<>();
		//定义一个sheet的最大条目容量
		int quantity = 60000;
		//定义起点坐标
		int count = 0;
		while (count < mapList.size()) {//判断equipments的容量能够分割成几个规定容量的List
			wrapList.add(new ArrayList(mapList.subList(count, (count + quantity) > mapList.size() ? mapList.size() : count + quantity)));
			count += quantity;
		}
		//给表设置名称
		String title = "实验室考勤记录";
		//给表设置表头名
		String[] hearders = new String[] {"学生姓名","学生工号", "学院名称", "考勤时间"};
		//属性数组，写数据到excel时的顺序定位
		String[] fields = new String[] {"cname","username", "academyName","attendanceTime"};
		//新建一个TableData的集合
		List<TableData> tableDataList = new ArrayList<TableData>();
		for(List<Map> tempList : wrapList){//将所需导出的数据集合遍历并拼接表头信息
			TableData td = ExcelUtils.createTableData(tempList, ExcelUtils.createTableHeader(hearders), fields);
			tableDataList.add(td);
		}
		JsGridReportBase report = new JsGridReportBase(request, response);

		report.exportToExcelForSheets(title, shareService.getUserDetail().getCname(), tableDataList);

	}

	/**
	 * Description 获取实验室某一类型可用的设备列表
	 * @param lab_id
	 * @param c_number
	 * @param c_catagory
	 * @return
	 * @author 陈乐为 2019年4月29日
	 */
	public List<LabRoomAgent> getAgentByType(Integer lab_id, String c_number, String c_catagory) {
		String sql = "select c from LabRoomAgent c where c.labRoom.id=" + lab_id;
		sql += " and c.CDictionary.CNumber = '"+c_number+"'";
		sql += " and c.CDictionary.CCategory = '"+c_catagory+"'";
		sql += " and c.CDictionary.enabled is true";

		return labRoomAgentDAO.executeQuery(sql, 0, -1);
	}

	/**
	 * Description 发送iot下发权限的信息
	 * @param lab_id 实验室id
	 * @return
	 * @author 陈乐为 2019年6月11日
	 */
	public String sendAgentInfoTodayToIOT(Integer lab_id) {
		// 实验室的门禁、实验室电源控制器、设备电源控制器
		String sql = "select agent from LabRoomAgent agent left join agent.labRoomDevices device where 1=1 ";
		sql += " and (agent.labRoom.id=" + lab_id +" or device.labRoom.id=" + lab_id +")";
		// 找到该实验室下为电源控制器和门禁的物联设备
		sql += " and agent.CDictionary.CCategory = 'c_agent_type' ";
		sql += " and (agent.CDictionary.CNumber = '2' or agent.CDictionary.CNumber = '4') ";
		List<LabRoomAgent> labRoomAgents = labRoomAgentDAO.executeQuery(sql);
		// 遍历
		for (LabRoomAgent agent : labRoomAgents) {
			String hql = "{call proc_agent_info_for_iot("+ lab_id +",'"+ agent.getHardwareIp() +"',"+ agent.getCDictionary().getId() +")}";
			Query query =entityManager.createNativeQuery(hql);
			List<Object[]> objects=query.getResultList();
			List<AgentIOT> agentIOTS = new ArrayList<>();
			for(Object[] obj : objects){
				AgentIOT agentIOT = new AgentIOT();
				agentIOT.setId(obj[0]!=null?obj[0].toString():null);
				agentIOT.setUsername(obj[1] != null ? obj[1].toString() : null);
				agentIOT.setCname(obj[2] != null ? obj[2].toString() : null);
				agentIOT.setCardno(obj[3] != null ? obj[3].toString() : null);
				agentIOT.setIsAdmin(obj[4] != null ? Integer.parseInt(obj[4].toString()) : null);
				agentIOT.setDeviceindex(obj[5] != null ? Integer.parseInt(obj[5].toString()) : null);
				agentIOT.setStarttime(obj[6] != null ? obj[6].toString() : null);
				agentIOT.setEndtime(obj[7] != null ? obj[7].toString() : null);
				agentIOTS.add(agentIOT);
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("serverIp", agent.getCommonServer().getServerIp());
			jsonObject.put("data", agentIOTS);
			String json = jsonObject.toString();
			String serverIp = agent.getCommonServer().getServerIp() + ":" + agent.getCommonServer().getServerSn();
			try {
				String url = "";
				if (agent.getCDictionary().getId()==548) {
					url = "http://"+ serverIp +"/reservation/iot/acldoor/" + agent.getHardwareIp() + "/regcard";
				} else {
					url = "http://"+ serverIp + "/reservation/iot/guard/" + agent.getHardwareIp() + "/regcard";
				}
				System.out.println("接口调用地址:" + url);
				HttpClientUtil.doPostJson(url, json);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return "success";
	}

}