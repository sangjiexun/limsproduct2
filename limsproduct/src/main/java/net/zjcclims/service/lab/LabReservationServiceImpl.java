package net.zjcclims.service.lab;

import com.google.inject.persist.Transactional;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceReservationService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.TimetableSelfSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("LabReservationService")
public class LabReservationServiceImpl implements LabReservationService {

	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private SystemTimeDAO systemTimeDAO;
	@Autowired
	private SchoolWeekdayDAO schoolWeekdayDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	@Autowired
	private LabReservationDAO labReservationDAO;
	@Autowired
	private LabReservationAuditDAO labReservationAuditDAO;
	@Autowired
	private LabReservationTimeTableDAO labReservationTimeTableDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private LabReservationTimeTableStudentDAO labReservationTimeTableStudentDAO;
	@Autowired
	private TimetableLabRelatedDAO timetableLabRelatedDAO;
	@Autowired
	private OuterApplicationService outerApplicationService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	private SchoolWeekDAO schoolWeekDAO;
	@Autowired
	private TimetableSelfSchedulingService timetableSelfSchedulingService;
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;
	@Autowired
	private ViewTimetableDAO viewTimetableDAO;
	@Autowired
	private LabRoomDeviceReservationService labRoomDeviceReservationService;
	@Autowired
	private LabRoomReservationCreditDAO labRoomReservationCreditDAO;	@Autowired
	private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;

	/****************************************************************************
	 * 功能：查询出所有的实验室类别 作者：李小龙 时间：2014-07-29
	 ****************************************************************************/
	@Override
	public List<CDictionary> findAllCLabRoomType() {
		return shareService.getCDictionaryData("c_lab_room_type");
	}

	@Override
	public List<LabRoom> findLabRoom(LabRoom labRoom) {

		String sql = "select l from LabRoom l where 1=1 and l.labRoomActive=1 and l.labRoomReservation=1 ";
		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
			sql += " and (l.labRoomName like '%" + labRoom.getLabRoomName() + "%' or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')";
		}
		return labRoomDAO.executeQuery(sql, 0, -1);
	}

	@Override
	public List<LabRoom> findLabRoompage(LabRoom labRoom, int currpage, int pageSize) {
		String sql = "select l from LabRoom l where 1=1 and l.labRoomActive=1 and l.labRoomReservation=1 ";
		if (labRoom.getLabRoomName() != null && !labRoom.getLabRoomName().equals("")) {
			sql += " and  (l.labRoomName like '%" + labRoom.getLabRoomName() + "%'  or l.labRoomNumber like '%"
					+ labRoom.getLabRoomName() + "%')";
		}
		return labRoomDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
	}

	@Override
	public Map appointmenttype() {
		Map attributesMap = new HashMap();
		for (CDictionary iter : shareService.getCDictionaryData("c_lab_reservation_type")) {
			attributesMap.put(iter.getId(), iter.getCName());
		}
		return attributesMap;

	}

	@Override
	public Map getallweek() {
		Map attributesMap = new TreeMap();
		for (CDictionary iter : shareService.getCDictionaryData("c_lab_reservation_week")) {
			attributesMap.put(iter.getId(), iter.getCName());
		}
		return attributesMap;

	}

	@Override
	public Map getalldate() {
		Map attributesMap = new HashMap();

		for (SchoolWeekday iter : schoolWeekdayDAO.findAllSchoolWeekdays()) {
			attributesMap.put(iter.getId(), iter.getWeekdayName());
		}
		return attributesMap;

	}

	@Override
	public Map getallfestivaltimes() {
		Map attributesMap = new HashMap();
		for (SystemTime iter : systemTimeDAO.findAllSystemTimes()) {
			attributesMap.put(iter.getId(), iter.getSectionName());
		}
		return attributesMap;
	}

	@Override
	public Map getallactivitycategory() {
		Map attributesMap = new HashMap();
		for (CDictionary iter : shareService.getCDictionaryData("c_lab_reservation_activity_category")) {
			attributesMap.put(iter.getId(), iter.getCName());
		}
		return attributesMap;

	}

	@Override
	public Map getUsersMap() {
		Map attributesMap = new HashMap();
		for (User iter : userDAO.executeQuery("select u from User u where  u.enabled=true and  u.userRole=1 ")) {
			attributesMap.put(iter.getUsername(), iter.getCname());
		}
		return attributesMap;
	}

	@Override
	public Map getallclassgruop() {
		Map attributesMap = new HashMap();
		for (SchoolCourse ite : schoolCourseDAO.executeQuery("select e from SchoolCourse e group by  e.courseCode")) {
			attributesMap.put(ite.getCourseNo(), ite.getCourseName());
		}
		return attributesMap;
	}

	@Override
	public Map getallclass() {
		Map attributesMap = new HashMap();
		for (SchoolCourse ite : schoolCourseDAO.executeQuery("select e from SchoolCourse e")) {
			attributesMap.put(ite.getCourseNo(), ite.getCourseName());
		}
		return attributesMap;
	}

	@Override
	public int savelabReservation(LabReservation labReservation, int idkey, String schoolTermid, String[] Week,
			String[] systemTime, String[] jieci) {

		labReservation.setLabRoom(labRoomDAO.findLabRoomById(idkey));
		labReservation.setAuditResults(3);
		LabReservation ff = labReservationDAO.store(labReservation);

		if (systemTime != null && !systemTime.equals("")) {

			SchoolTerm term = new SchoolTerm();
			term.setId(Integer.parseInt(schoolTermid));

			for (String string : systemTime) {
				if (jieci != null && !jieci.equals("")) {

					for (String string2 : jieci) {
						if (Week.length > 0) {
							for (String string3 : Week) {
								CDictionary c = shareService.getCDictionaryByCategory("c_lab_reservation_week", string3); 

								LabReservationTimeTable lab = new LabReservationTimeTable();
								lab.setSchoolTerm(term);
								lab.setCDictionary(c);

								SchoolWeekday weekday = new SchoolWeekday();
								weekday.setId(Integer.parseInt(string));
								lab.setSchoolWeekday(weekday);
								SystemTime time = new SystemTime();
								time.setId(Integer.parseInt(string2));
								lab.setSystemTime(time);
								lab.setLabReservation(ff);
								labReservationTimeTableDAO.store(lab);
							}
						}
					}
				}
			}

		}
		System.out.println("labReservation 在save方法中id是"+ff.getId());
		return ff.getId();
	}

	@Override
	public List<LabReservation> findLabreservationmanage(LabReservation labReservation, int tage, int sid) {
		String sql = "select l from LabReservation l where 1=1 ";
		if (labReservation.getEventName() != null && labReservation.getEventName() != "") {
			sql += " and (l.labReservation.eventName like '" + labReservation.getEventName().trim()
					+ "' or  l.labReservation.labRoom.labRoomName like '" + labReservation.getEventName().trim()
					+ "' )";

		}

		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1
				|| SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {

		} else {
			if (labCenterDAO.findLabCenterByPrimaryKey(sid) != null
					&& labCenterDAO.findLabCenterByPrimaryKey(sid).getSchoolAcademy() != null) {
				sql += " and  l.labRoom.labCenter.schoolAcademy.academyNumber='"
						+ labCenterDAO.findLabCenterById(sid).getSchoolAcademy().getAcademyNumber() + "'";
			}
		}
		// 通过
		if (tage == 1) {
			sql += " and l.auditResults=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.auditResults=2";
		}
		// 3未审核
		if (tage == 3) {
			sql += " and l.auditResults=3";
		}
		// 4审核未通过
		if (tage == 4) {
			sql += " and l.auditResults=4";
		}
		return labReservationDAO.executeQuery(sql,0,-1);
	}

	@Override
	public Set<LabReservation> findLabreservationManage(LabReservation labReservation, int tage, int currpage,
			int pageSize) {
		String sql = "select l from LabReservation l where 1=1 ";

		//新加需求，是我的审核还是我的预约，sid  1审核2预约
		// 中心修改学院模糊问题备注--为什么要用所选中心id作为标志位判断是1？
		//实训部主任权限可以看到所有
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("PRESECTEACHING") != -1){

		}else{

			// qun 判断 登陆者是不是超级管理员，实验教务 是的话下边权限不能进入
			int qun = 0;
			// shareService.getUser().getAuthorities().toString().indexOf(str)
			// 实验室超级管理员，实验教务,选择实验室中心的所有
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1
					|| SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
					.indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {
				qun++;
				String num = "";
				for (LabRoom iterable_element : labRoomDAO.findAllLabRooms()) {
					num += iterable_element.getId() + ",";
				}
				if (num != "") {
					sql += " and l.labRoom.id in (" + num.substring(0, num.length() - 1) + " ) ";
				}
			}
			// shi 判断登陆者 不是超级管理员，实验教务 是不是实验室中心主任， 是的话下边权限不能进入
			int shi = 0;
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
					sql += " and  l.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
							+ shareService.getUser().getUsername() + "')";
				}
			}
			// 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
			// System.out.println("---qun--"+qun+"---shi---"+shi+"---guan---"+guan);

			// 老师和学生SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_STUDENT")
			// != -1 ||
			// SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_TEACHER")
			// != -1
			if (qun == 0 && shi == 0 && guan == 0) {
				sql += " and  l.user.username='" + shareService.getUser().getUsername() + "' ";
			}

			// 通过
			if (tage == 1) {
				sql += " and l.auditResults=1";
			}
			// 2审核中
			if (tage == 2) {
				sql += " and l.auditResults=2";
			}
			// 3未审核
			if (tage == 3) {
				sql += " and l.auditResults=3";
			}
			// 4审核未通过
			if (tage == 4) {
				sql += " and l.auditResults=4";
			}
			sql += "   order by l.id desc";
		}
		List<LabReservation> lll = labReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
		Set<LabReservation> sliet = new HashSet<LabReservation>();
		for (LabReservation lab : lll) {
			sliet.add(lab);
		}
		return sliet;
	}

	@Override
	public LabReservation getlabReservationinfor(int idkey) {
		return labReservationDAO.findLabReservationById(idkey);
	}

	@Override
	public void auditsavelabreservtion(LabReservationAudit labReservationAudit, int idkey, HttpServletResponse response) throws IOException {
		LabReservation d = labReservationDAO.findLabReservationById(idkey);
		labReservationAudit.setLabReservation(d);
		//下面c_opreation_audit_results", "4",原来这里的4  是  1.
		if (labReservationAudit.getCDictionary()!=null && shareService.getCDictionaryByCategory("c_opreation_audit_results", "4").getId().equals(labReservationAudit.getCDictionary().getId())) {
			/*if (d.getCDictionaryByLabReservetYpe().getId().equals(shareService.getCDictionaryByCategory("c_lab_reservation_type", "1").getId())) {
				d.setAuditResults(1);
			} else {
				d.setAuditResults(1);
			}*/
			d.setAuditResults(1);
			d.setItemReleasese(1);
			//修正排课表timetableAppointment的状态值
			TimetableAppointment timetableAppointment = d.getTimetableAppointment();
			timetableAppointment.setStatus(1);
			d.setTimetableAppointment(timetableAppointment);
		} else {
			d.setAuditResults(4);
		}
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		d = labReservationDAO.store(d);
		if(d.getAuditResults() == 1){
			Set<LabReservationTimeTable> labReservationTimetables = d.getLabReservationTimeTables();
			if(labReservationTimetables != null && labReservationTimetables.size() != 0){
				for(LabReservationTimeTable l:labReservationTimetables){
					SchoolWeek week = this.findSchoolWeekByTermAndWeekAndWeekday(l.getSchoolTerm().getId(), Integer.parseInt(l.getCDictionary().getCNumber()), l.getSchoolWeekday().getId());
					SystemTime systemTime = l.getSystemTime();
					String date = sdfDate.format(week.getDate().getTime());
					String startTime = sdfTime.format(systemTime.getStartDate().getTime());
					String endTime = sdfTime.format(systemTime.getEndDate().getTime());
					startTime = date+" "+startTime;
					endTime = date+" "+endTime;
					ViewTimetable viewTimetable = new ViewTimetable();
					viewTimetable.setStartTime(startTime);
					viewTimetable.setEndTime(endTime);
					viewTimetable.setLabId(d.getLabRoom().getId());
					viewTimetable.setCourseNo("预约");
					viewTimetable.setPId("0");
					viewTimetable.setUsername(d.getUser().getUsername());
					viewTimetableDAO.store(viewTimetable);
				}
			}
			labRoomDeviceReservationService.refreshPermissions(d.getLabRoom().getId(), response);
		}
		labReservationAuditDAO.store(labReservationAudit);
		
	}

	@Override
	public List<LabReservation> findLabreservationmanage(LabReservation labReservation, int tage, int currpage,
			int pageSize, String username) {
		String sql = "select l from LabReservation l where 1=1 and l.user.username=" + username;
		if (labReservation.getEventName() != null && labReservation.getEventName() != "") {
			sql += " and l.eventName like '" + labReservation.getEventName() + "'";
		}
		// 通过
		if (tage == 1) {
			sql += " and l.auditResults=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.auditResults=2";
		}
		// 3未审核
		if (tage == 3) {
			sql += " and l.auditResults=3";
		}
		// 4审核未通过
		if (tage == 4) {
			sql += " and l.auditResults=4";
		}
		return labReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
	}

	@Override
	public List<LabReservation> findLabreservationmanage(LabReservation labReservation, int tage, String username,
			int sid) {
		String sql = "select l from LabReservation l where 1=1 and l.user.username=" + username;
		if (labReservation.getEventName() != null && labReservation.getEventName() != "") {
			sql += " and l.eventName like '" + labReservation.getEventName() + "'";
		}
		// 通过
		if (tage == 1) {
			sql += " and l.auditResults=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.auditResults=2";
		}
		// 3未审核
		if (tage == 3) {
			sql += " and l.auditResults=3";
		}
		// 4审核未通过
		if (tage == 4) {
			sql += " and l.auditResults=4";
		}
		return labReservationDAO.executeQuery(sql,0,-1);
	}

	// 方正 根据当前用户查找改用户的实验室预约 获取前4行的数据
	@Override
	public List<LabReservation> findLabreservationmanage(String username) {
		String sql = "select l from LabReservation l where 1=1 and l.user.username= " + username;
		sql += " order by l.id asc";
		return labReservationDAO.executeQuery(sql, 0, 4);
	}

	// 方正 根据当前用户查找改用户的实验室预约 不包含分页信息
	@Override
	public List<LabReservation> getLabReservationByUserId(String username) {
		String sql = "select l from LabReservation l where 1=1 and l.user.username=" + username;
		sql += " order by l.id asc";
		return labReservationDAO.executeQuery(sql,0,-1);
	}

	// 方正 根据当前用户查找改用户的实验室预约 包含分页等信息
	@Override
	public List<LabReservation> getLabReservationByUserId(String username, int page, int pageSize) {
		String sql = "select l from LabReservation l where 1=1 and l.user.username=" + username;
		return labReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	// 方正 查询根据前段封装对象来查找改用户的实验室预约 不包含分页信息
	@Override
	public List<LabReservation> findLabReservationByLabReservation(LabReservation labReservation) {
		String sql = "select l from LabReservation l where 1=1";
		if (labReservation.getId() != null && labReservation.getId() != 0) {
			/*
			 * if(labRoomDeviceReparation.getLabRoom().getLabRoomName()!=null&&!
			 * labRoomDeviceReparation
			 * .getLabRoom().getLabRoomName().equalsIgnoreCase("")){
			 */
			sql += " and l.id like '%" + labReservation.getId() + "%'";
		}
		/* sql+=" order by l.CConsumables desc"; */
		// 给语句添加分页机制；
		List<LabReservation> labReservations = labReservationDAO.executeQuery(sql,0,-1);
		return labReservations;
	}

	// 方正 查询根据前段封装对象来查找改用户的实验室预约 包含分页信息
	@Override
	public List<LabReservation> findLabReservationByLabReservation(LabReservation labReservation, int page, int pageSize) {
		String sql = "select l from LabReservation l where 1=1";
		if (labReservation.getId() != null && labReservation.getId() != 0) {
			/*
			 * if(labRoomDeviceReparation.getLabRoom().getLabRoomName()!=null&&!
			 * labRoomDeviceReparation
			 * .getLabRoom().getLabRoomName().equalsIgnoreCase("")){
			 */
			sql += " and l.id like '%" + labReservation.getId() + "%'";
		}
		/* sql+=" order by l.CConsumables desc"; */
		// 给语句添加分页机制；
		List<LabReservation> labReservations = labReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
		return labReservations;
	}

	// 方正 根据当前用户查找我要打印的信息 包含分页信息
	@Override
	public List<LabReservation> findLabReservationByUserId(String username, int pageSize, int currpage) {
		String sql = "select c from LabReservation c where c.user.username=" + username;
		List<LabReservation> labReservation = labReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
		return labReservation;
	}

	@Override
	public Set<SystemTime> getscreeningtake(int idkey, int labid, int time) {
		// 获取指定学期和周次
		List<LabReservationTimeTable> week = labReservationTimeTableDAO
				.executeQuery("select t from LabReservationTimeTable t where 1=1  t.labReservation.labRoom.id=" + labid
						+ "   and t.CDictionary.id=" + idkey + " and t.schoolTerm.id=" + time);
		// 获取节次
		Set<SystemTime> dys = new HashSet<SystemTime>();
		dys.addAll(systemTimeDAO.findAllSystemTimes());
		// 获取周次
		Set<SchoolWeekday> xingqi = schoolWeekdayDAO.findAllSchoolWeekdays();
		if (week.size() > 0) {
			for (LabReservationTimeTable labe : week) {
				List<LabReservationTimeTable> everyday = labReservationTimeTableDAO
						.executeQuery("select t from LabReservationTimeTable t where 1=1  t.labReservation.labRoom.id="
								+ labid + "   and t.CDictionary.id=" + idkey + " and t.schoolTerm.id=" + time
								+ " and t.systemTime.id=" + labe.getSystemTime().getId());
				if (everyday.size() <= dys.size()) {
					dys.remove(everyday.get(0).getSystemTime());
				}

			}
		}

		return dys;
	}

	@Override
	public List<SchoolTerm> getschoolterm() {
		return schoolTermDAO.executeQuery("select t from SchoolTerm t order by termEnd desc");
	}

	//CLabReservationWeek
	@Override
	public List<CDictionary> getscreeningxueqi(int idkey, int labid) {
		List<LabReservationTimeTable> week = labReservationTimeTableDAO
				.executeQuery("select t from LabReservationTimeTable t where 1=1 and t.labReservation.labRoom.id="
						+ labid + "  and t.schoolTerm.id=" + idkey);
		// 获取节次
		Set<SystemTime> dys = systemTimeDAO.findAllSystemTimes();
		// 获取周次
		//Set<CLabReservationWeek> wee = new HashSet<CLabReservationWeek>();
		//wee.addAll(cLabReservationWeekDAO.findAllCLabReservationWeeks());
		List<CDictionary> wee = new ArrayList<CDictionary>();
		wee.addAll(shareService.getCDictionaryData("c_lab_reservation_week"));
		// 获取星期
		Set<SchoolWeekday> xingqi = schoolWeekdayDAO.findAllSchoolWeekdays();
		if (week.size() > 0) {
			for (LabReservationTimeTable cLab : week) {
				// 根据实验室，学期周次计算出该周次一排的课的节数
				List<LabReservationTimeTable> weekwek = labReservationTimeTableDAO
						.executeQuery("select t from LabReservationTimeTable t where 1=1 and   t.labReservation.labRoom.id="
								+ labid
								+ "  and t.schoolTerm.id="
								+ idkey
								+ "  and t.CDictionary.id="
								+ cLab.getCDictionary().getId());
				if (weekwek.size() > 0) {
					// 判断改周已排课的技术 和一周有多少节课进行对别如果大于总共节数，那就本周一排完 ，移除掉
					if (weekwek.size() <= dys.size() * xingqi.size()) {
						wee.remove(weekwek.get(0).getCDictionary());
					}
				}
			}
		}
		return wee;
	}

	@Override
	public Set<SchoolWeekday> getscreeningtaketime(int idkey) {
		List<LabReservationTimeTable> week = labReservationTimeTableDAO
				.executeQuery("select t from LabReservationTimeTable t where 1=1 and t.schoolWeekday.id=" + idkey);
		Set<SchoolWeekday> sys = schoolWeekdayDAO.findAllSchoolWeekdays();
		if (week.size() > 0) {
			// 查找已使用的周次
			for (LabReservationTimeTable ite : week) {
				sys.remove(ite.getSchoolWeekday());
			}
		}
		return sys;
	}

	@Override
	public void savelabReservationstudent(int s, String student) {
		LabReservation intdd = labReservationDAO.findLabReservationById(s);
		if (student != null && student != "") {
			student = student.replace("\r\n", ",");
			student = student.replace("，", ",");
			String[] str = student.split(",");
			for (String string : str) {
				LabReservationTimeTableStudent stude = new LabReservationTimeTableStudent();
				stude.setLabReservation(intdd);
				User user = userDAO.findUserByUsername(string);
				if (user != null) {
					stude.setUser(user);
					labReservationTimeTableStudentDAO.store(stude);
				}

			}
		}

	}

	//CLabReservationWeek
	@Override
	public List<CDictionary> getscreeningtakexingqi(int idkey) {
		List<LabReservationTimeTable> s = labReservationTimeTableDAO.executeQuery(
				"select t from LabReservationTimeTable t where 1=1 and t.schoolWeekday.id=" + idkey, 0, -1);
		//Set<CLabReservationWeek> week = cLabReservationWeekDAO.findAllCLabReservationWeeks();
		List<CDictionary> week = shareService.getCDictionaryData("c_lab_reservation_week");
		if (s.size() > 0) {
			// 查找已使用的周次
			for (LabReservationTimeTable ite : s) {
				week.remove(ite.getCDictionary());

			}
		}
		return week;
	}

	@Override
	public Set<SystemTime> getscreeningtakejicebyxingqi(int idkey) {
		List<LabReservationTimeTable> s = labReservationTimeTableDAO.executeQuery(
				"select t from LabReservationTimeTable t where 1=1 and t.systemTime.id=" + idkey, 0, -1);
		Set<SystemTime> week = systemTimeDAO.findAllSystemTimes();
		if (s.size() > 0) {
			// 查找已使用的周次
			for (LabReservationTimeTable ite : s) {
				week.remove(ite.getSystemTime());
			}
		}
		return week;
	}

	@Override
	public int savelabReservation1(LabReservation labReservation, int iskey, String schoolTermid, String[] xueqi,
			String xingqi, String[] jieci) {
		System.out.println("savelabReservation1 coming in ");
		// 获取实验室yuyue记录
		labReservation.setLabRoom(labRoomDAO.findLabRoomById(iskey));
		// 创建状态
		labReservation.setAuditResults(3);
		// 保存
		LabReservation ff = labReservationDAO.store(labReservation);

		if (xueqi != null && xueqi.length > 0) {

			SchoolTerm term = new SchoolTerm();
			term.setId(Integer.parseInt(schoolTermid));
			SchoolWeekday xingqi1 = new SchoolWeekday();
			xingqi1.setId(Integer.parseInt(xingqi));
			// System.out.println(xueqi);
			for (int i = 0; i < xueqi.length; i++) {
				for (String strings : xueqi[i].split(",")) {

					//CLabReservationWeek zhou = new CLabReservationWeek();
					CDictionary zhou = shareService.getCDictionaryByCategory("c_lab_reservation_week", strings);

					for (String string2 : jieci) {
						for (String stringq : string2.split(",")) {

							LabReservationTimeTable lab = new LabReservationTimeTable();
							lab.setSchoolTerm(term);
							lab.setCDictionary(zhou);

							lab.setSchoolWeekday(xingqi1);
							SystemTime time = new SystemTime();
							time.setId(Integer.parseInt(stringq));
							lab.setSystemTime(time);
							lab.setLabReservation(ff);

							labReservationTimeTableDAO.store(lab);
						}
					}
				}
			}
		}
		return ff.getId();
	}

	@Override
	public List<LabReservationTimeTableStudent> getstudentforlabreation(
			LabReservationTimeTableStudent labReservationTimeTableStudent, int idkey) {
		String sql = "select l from LabReservationTimeTableStudent l where 1=1 ";
		if (labReservationTimeTableStudent.getUser() != null
				&& labReservationTimeTableStudent.getUser().getCname() != null) {
			sql += " and l.user.cname like '%" + labReservationTimeTableStudent.getUser().getCname() + "%'";
		}
		sql += " and l.labReservation.id=" + idkey;
		return labReservationTimeTableStudentDAO.executeQuery(sql, 0, -1);
	}

	@Override
	public List<LabReservationTimeTableStudent> getstudentforlabreationpage(
			LabReservationTimeTableStudent labReservationTimeTableStudent, Integer idkey, Integer currpage,
			Integer pageSize) {

		String sql = "select l from LabReservationTimeTableStudent l where 1=1 ";
		if (labReservationTimeTableStudent.getUser() != null
				&& labReservationTimeTableStudent.getUser().getCname() != null) {
			sql += " and l.user.cname like '%" + labReservationTimeTableStudent.getUser().getCname() + "%'";
		}
		sql += " and l.labReservation.id=" + idkey;
		return labReservationTimeTableStudentDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
	}

	@Override
	public List<LabReservation> getAllLabReservation(LabReservation labReservation) {
		String sql = "select l from LabReservation l ";
		return labReservationDAO.executeQuery(sql,0,-1);
	}

	@Override
	public List<LabReservation> getAllLabReservation(LabReservation labReservation, int page, int pageSize) {
		String sql = "select l from LabReservation l ";
		return labReservationDAO.executeQuery(sql, (page - 1) * pageSize, pageSize);
	}

	//CLabReservationWeek
	@Override
	public List<CDictionary> getzhouci(int xing, int labid, int time, int[] jie) {
		//Set<CLabReservationWeek> wek = new HashSet<CLabReservationWeek>();
		//Set<CLabReservationWeek> wwek = new HashSet<CLabReservationWeek>();
		List<CDictionary> wek = new ArrayList<CDictionary>();
		List<CDictionary> wwek = new ArrayList<CDictionary>();
		List<SchoolWeek> itne = schoolWeekDAO.executeQuery("select t from SchoolWeek t where t.schoolTerm.id=" + time + " group by  t.week");
		for (SchoolWeek schoolWeek : itne) {
			for (CDictionary labReservationWeek : shareService.getCDictionaryData("c_lab_reservation_week")) {
				if (labReservationWeek.getId().equals(schoolWeek.getWeek())) {
					wek.add(labReservationWeek);
				}
			}
		}

		String sql = "select t from LabReservationTimeTable t where 1=1 and t.schoolWeekday.id=" + xing
				+ " and t.labReservation.labRoom.id=" + labid;
		sql += " and t.schoolTerm.id=" + time + " ";
		String ss = "";
		if (jie.length > 0) {
			for (int i : jie) {
				ss += i + ",";
			}

			ss = ss.substring(0, ss.length() - 1);
			sql += " and t.systemTime.id in (" + ss + ")";
		}

		// 返回可用的星期信息
		Integer[] intWeeks = this.getValidWeek(time, jie, labid, xing);

		if (intWeeks.length != 21) {
			// 遍历实验分室
			if (intWeeks.length != 0 && intWeeks[0] != null) {
				for (Integer integer : intWeeks) {
					//CLabReservationWeek l = cLabReservationWeekDAO.findCLabReservationWeekById(integer);
					CDictionary l = shareService.getCDictionaryByCategory("c_lab_reservation_week", integer.toString());
					if (l != null) {
						wwek.add(l);
					}
				}
			}
		}
		List<LabReservationTimeTable> s = labReservationTimeTableDAO.executeQuery(sql, 0, -1);
		for (LabReservationTimeTable labReservationTimeTable : s) {
			wek.remove(labReservationTimeTable.getCDictionary());
		}
		//Set<CLabReservationWeek> bak = new HashSet<CLabReservationWeek>();
        List<CDictionary> bak = new ArrayList<CDictionary>();
		for (CDictionary weks : wek) {
			if (wwek.size() > 0) {
				for (CDictionary wweks : wwek) {
					if (weks.getId().equals(wweks.getId())) {
						bak.add(weks);
					}
				}
			} else {
				bak.addAll(wek);
			}
		}

		return bak;
	}

	public Integer[] getValidWeek(int term, int[] classes, int labrooms, int weekday) {
		Integer[] allWeeks = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		// 获取有效的实验分室列表-根据登录用户的所属学院
		StringBuffer sql = new StringBuffer("select c from TimetableLabRelated c where 1=0 ");

		sql.append("or ( c.timetableAppointment.schoolCourse.schoolTerm.id =" + term
				+ " and c.timetableAppointment.weekday =" + weekday + " and c.labRoom.id =" + labrooms + ") ");

		List<TimetableLabRelated> list = timetableLabRelatedDAO.executeQuery(sql.toString());
		if (list.size() > 0) {
			// String weeks ="";
			String weeks = "";
			for (TimetableLabRelated timetableLabRelated : list) {
				boolean isIn = true;

				for (int intClass : classes) {

					isIn = timetableLabRelated.getTimetableAppointment().getEndClass() >= intClass
							&& timetableLabRelated.getTimetableAppointment().getStartClass() <= intClass;
					if (isIn) {
						break;
					}
				}

				if (isIn && timetableLabRelated.getTimetableAppointment().getWeekday() >= weekday) {
					for (int i = timetableLabRelated.getTimetableAppointment().getStartWeek(); i <= timetableLabRelated
							.getTimetableAppointment().getEndWeek();) {
						weeks = weeks + i + ";";
						i++;
					}
				}
			}
			if (!weeks.equals("")) {
				String[] sWeeks = weeks.split(";");

				int[] intWeeks = new int[sWeeks.length];
				// 获取可用的教学
				for (int i = 0; i < intWeeks.length; i++) {
					intWeeks[i] = Integer.parseInt(sWeeks[i]);
				}

				// 数据排序去重
				Arrays.sort(intWeeks);

				String strWeek = "";
				Integer[] newIntWeeks1 = this.getDistinct(intWeeks);
				// int j = allWeeks.length;
				for (int i = 0; i < allWeeks.length; i++) {
					int a = Arrays.binarySearch(newIntWeeks1, allWeeks[i]);
					if (a < 0) {
						strWeek = strWeek + allWeeks[i] + ";";
					}
				}

				Integer[] newIntWeeks = new Integer[strWeek.split(";").length];
				if (strWeek.equals("")) {
					return newIntWeeks;
				}
				if (strWeek.split(";").length != 0 && strWeek.split(";")[0] != null) {

					for (int i = 0; i < strWeek.split(";").length; i++) {
						newIntWeeks[i] = Integer.parseInt(strWeek.split(";")[i]);

					}
				}
				return newIntWeeks;
			} else {
				return allWeeks;
			}
		} else {
			return allWeeks;

		}

	}

	/************************************************************
	 * @获取数组中去重的结果
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	public Integer[] getDistinct(int num[]) {
		List<Integer> list = new java.util.ArrayList<Integer>();
		for (int i = 0; i < num.length; i++) {
			if (!list.contains(num[i])) {// 如果list数组不包括num[i]中的值的话，就返回true。
				list.add(num[i]); // 在list数组中加入num[i]的值。已经过滤过。
			}
		}
		return list.toArray(new Integer[0]);
	}
	
	/************************************************************
	 * @功能：根据labReservation表的主键删除实验室预约、实验室预约的排课、实验室预约相关的实验室
	 * @作者：魏诚
	 * @日期：2014-07-24
	 ************************************************************/
	@Transactional
	public void labreationdelectitem(int idkey) {
		LabReservation i = labReservationDAO.findLabReservationById(idkey);
		
		//删除实验室预约相关的实验室
		/*for(TimetableLabRelated timetableLabRelated:i.getTimetableAppointment().getTimetableLabRelateds()){
			timetableLabRelatedDAO.remove(timetableLabRelated);
		}*/
		//删除实验室预约排课记录
		//timetableAppointmentService.deleteAppointment(i.getTimetableAppointment().getId());
		//删除实验室预约
		labReservationDAO.remove(i);
	}

	public List<SchoolTerm> timemap() {

		return schoolTermDAO.executeQuery("select s from SchoolTerm s where 1=1 order by s.id desc");
	}

	/*************************************************************************************
	 * @內容：保存实验室预约信息
	 * @作者： 魏誠
	 * @日期：2015-05-29
	 *************************************************************************************/
	@Transactional
	public LabReservation saveLabReservationProc(LabReservation labReservation, HttpServletRequest request) throws ParseException {
		String idkey = request.getParameter("deviceType");
		String idkey1 = request.getParameter("deviceType1");
		// 获取学期
		String schoolTermid = request.getParameter("schoolTermid");
		// 获取周次
		String[] Week = request.getParameterValues("weeks");
		// 获取节次
		String[] jieci = request.getParameterValues("classes");
		// 获取星期
		String[] systemTime = request.getParameterValues("weekday");
		// 获取学生
		String students = request.getParameter("student1");
		String xueqi = request.getParameter("xueqi");
		String labRooms = request.getParameter("labRooms");
		// 获取节次
		String[] time = request.getParameterValues("jieci");//此处原来写的是jiexi  贺子龙
		// 获取学生
		String student = request.getParameter("student");
		int sa = 0;

		if (labReservation.getLabRoom()!=null&&labReservation.getLabRoom().getId()!=null) {
			idkey = labReservation.getLabRoom().getId().toString();
		}else {
			if (idkey == null) {
				idkey = idkey1;
			}
		}

		labReservation.setUser(shareService.getUser());
		// 将排课时间存储到表timetableAppointment中
		TimetableAppointment timetableAppointment = timetableSelfSchedulingService.saveNoGroupSelfTimetable(request);
		timetableAppointment.setTimetableStyle(7);
		labReservation.setTimetableAppointment(timetableAppointment);
		// 保存选课组信息
		labReservation.setTimetableSelfCourse(timetableSelfCourseDAO.findTimetableSelfCourseById(Integer
				.parseInt(request.getParameter("courseCode"))));
		if (labReservation.getCDictionaryByActivityCategory() != null && schoolTermid != null && schoolTermid != ""
				&& Week != null && Week.length > 0 && systemTime != null && systemTime.length > 0 && jieci != null
				&& jieci.length > 0) {

			sa = this.savelabReservation(labReservation, Integer.parseInt(idkey), schoolTermid, Week, systemTime, jieci);
			TimetableLabRelated timetableLabRelated = new TimetableLabRelated();
			if (labReservation.getLabRoom().getId() != null) {
									// 将matchLabs添加到matchLabs中
					timetableLabRelated.setLabRoom(labReservation.getLabRoom());
					timetableLabRelated.setTimetableAppointment(labReservation.getTimetableAppointment());
					timetableLabRelatedDAO.store(timetableLabRelated);
			}

		}

		if (xueqi != null && xueqi != "" && Week != null && Week.length > 0 && schoolTermid != null && schoolTermid != ""
				&& time != null && time.length > 0) {
			sa = this.savelabReservation1(labReservation, Integer.parseInt(idkey), xueqi, Week, schoolTermid, time);

		}
		if (student != null && student != "") {
			this.savelabReservationstudent(sa, student);
		}
		if (students != null && students != "") {
			this.savelabReservationstudent(sa, students);
		}
		labReservation.setId(sa);
		return labReservation;
	}

	/*************************************************************************************
	 * @內容：返回可用的星期信息-实验室预约判冲
	 * @作者： 魏誠
	 * @日期：2015-07-09
	 *************************************************************************************/
	public String getValidWeeksMap(int term, int weekday, int[] labrooms, int[] classes) {
		// 返回可用的星期信息
		Integer[] intWeeks = outerApplicationService.getValidLabWeeks(term, classes, labrooms, weekday);
		String jsonWeek = "[";
		// 遍历实验分室
		if (intWeeks.length != 0 && intWeeks[0] != null) {
			for (int intWeek : intWeeks) {
				if (intWeeks[intWeeks.length - 1] == intWeek) {
					jsonWeek = jsonWeek + "{\"id\":\"" + intWeek + "\",\"value\":\"" + intWeek + "\",\"weekday\":\""
							+ weekday + "\"}";

				} else {
					jsonWeek = jsonWeek + "{\"id\":\"" + intWeek + "\",\"value\":\"" + intWeek + "\",\"weekday\":\""
							+ weekday + "\"},";

				}

			}
		}
		jsonWeek = jsonWeek + "]";
		return jsonWeek;

	}

	@Override
	public List<LabReservation> findLabreservationList(int currpage, int pageSize) {
		// TODO Auto-generated method stub
		String hql = "select l from LabReservation l";
		List<LabReservation> labReservations = labReservationDAO.executeQuery(hql, (currpage-1)*pageSize, pageSize);
		return labReservations;
	}
	
	
	/*************************************************************************************
	 * description:根据term_id,week,weekday找到schoolweek
	 * @author： 郑昕茹
	 * @date：201-03-02
	 *************************************************************************************/
	public SchoolWeek findSchoolWeekByTermAndWeekAndWeekday(Integer termId, Integer week, Integer weekday) {
		String sql = "select s from SchoolWeek s where 1=1 and schoolTerm.id ="+termId+" and week = "+week+" and weekday = "+weekday;
		List<SchoolWeek> weeks = schoolWeekDAO.executeQuery(sql, 0, -1);
		if(weeks != null && weeks.size() != 0){
			return weeks.get(0);
		}
		return null;
	}
	
	/****************************************************************************
	 *Description：查找所有实验室借用审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-28
	 ****************************************************************************/
	@Override
	public List<LabReservationAudit> findAllLabReservationAudit(LabReservation labReservation,String username,String auditstage) {
		String sql="select l from LabReservationAudit l where 1=1";
		if(labReservation.getId()!=null){
		sql+=" and l.labReservation.id="+labReservation.getId();
		}
		sql+=" and l.status=3";
		sql+=" and l.user.username='"+username+"'";
		if(!"0".equals(auditstage)){
		sql+=" and l.result='"+auditstage+"'";
		}
		return labReservationAuditDAO.executeQuery(sql);		
	}
	/****************************************************************************
	 *Description：查找所有实验室借用审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-28
	 ****************************************************************************/
	@Override
	public List<LabReservationAudit> findAllLabReservationAuditByResult(LabReservation labReservation,String username,int result) {
		String sql="select l from LabReservationAudit l where 1=1";
		sql+=" and l.user.username='"+username+"'";
		//审核通过
		if(result==1){
		sql+=" and l.status=1";
		}
		//审核拒绝
		if(result==2){
			sql+=" and l.status=2";
		}
		//待审核
		if(result==3){
			sql+=" and l.status=3";
		}
		return labReservationAuditDAO.executeQuery(sql);		
	}
	
	/****************************************************************************
	 *Description：查找所有实验室借用信息
	 *
	 *@author：张愉
	 *@date:2017-09-30
	 ****************************************************************************/
	public List<LabReservation> findAlllabReservation(LabReservation labReservation, Integer page, int pageSize,int tage,int isaudit) {
		String sql = "select l from LabReservation l where 1=1 ";
		if(labReservation.getLabRoom()!= null && labReservation.getLabRoom().getLabRoomName() != null){
			sql +=" and (l.labRoom.labRoomName like '%"+labReservation.getLabRoom().getLabRoomName()+"%'";
			sql +=" or l.labRoom.labRoomNumber like '%"+labReservation.getLabRoom().getLabRoomName()+"%')";
		}
        SimpleGrantedAuthority sga = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
		//新加需求，是我的审核还是我的预约，isAudit  1审核2预约
		if(isaudit == 1){
			//实训部主任、实训教训秘书	权限可以看到所有
			if (sga.getAuthority().indexOf("ROLE_PREEXTEACHING") != -1 || sga.getAuthority().indexOf("ROLE_PRESECTEACHING") != -1){
				
			}else{
				// qun 判断 登陆者是不是超级管理员，实验教务 是的话下边权限不能进入
				int qun = 0;
				// shareService.getUser().getAuthorities().toString().indexOf(str)
				// 实验室超级管理员，实验教务,选择实验室中心的所有
				if (sga.getAuthority().indexOf("ROLE_SUPERADMIN") != -1
						|| sga.getAuthority()
								.indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {
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
						&& (sga.getAuthority()
								.indexOf("ROLE_EXCENTERDIRECTOR") != -1||sga.getAuthority()
								.indexOf("ROLE_DEPARTMENTHEADER") != -1||sga.getAuthority()
								.indexOf("ROLE_COLLEGELEADER") != -1||sga.getAuthority()
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
						&& sga.getAuthority()
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

				// 老师和学生sga.getAuthority().indexOf("ROLE_STUDENT")
				// != -1 ||
				// sga.getAuthority().indexOf("ROLE_TEACHER")
				// != -1
				// xi 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,是不是系主任，是的话下边权限不能进入
				int xi = 0;
				if (qun == 0 && shi == 0 && guan == 0 && (sga.getAuthority()
						.indexOf("ROLE_CFO") != -1 || SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
						.indexOf("ROLE_ASSISTANT") != -1)) {
					xi++;
					if(shareService.getUser().getSchoolAcademy() != null){
						sql += " and l.user.schoolAcademy.academyNumber='" + shareService.getUser().getSchoolAcademy().getAcademyNumber() + "' ";
					}else{
						sql += " and l.id = -1";
					}
				}
				// 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,不是系主任
				if (qun == 0 && shi == 0 && guan == 0 && xi ==0 && !sga.getAuthority().contains("ROLE_TEACHER")) {
					sql += " and l.id = -1";
				}else if(sga.getAuthority().contains("ROLE_TEACHER")){
					sql += " and l.teacher = '" + shareService.getUserDetail().getUsername() + "'";
				}
			}
		}else{
			sql += " and l.user.username='" + shareService.getUser().getUsername() + "' ";
		}
		
		// 1审核通过
		if (tage == 1) {
			sql += " and l.auditResults=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.auditResults=2";
		}
		// 3未审核
		if (tage == 3) {
			sql += " and l.auditResults=3";
		}
		// 4审核拒绝
		if (tage == 4) {
			sql += " and l.auditResults=4";
		}
		sql += " order by l.id desc";
		return labReservationDAO.executeQuery(sql,(page-1)*pageSize,pageSize);		
	}
	/****************************************************************************
	 * 功能：查询信誉登记纪录by username
	 * 作者：周志辉
	 * @return 
	 ****************************************************************************/
	@Override
	public List<LabRoomReservationCredit> findCreditByUsername(
			String username) {
		String sql="Select l from LabRoomReservationCredit l where 1=1";
		sql+=" and l.username like '"+username+"'";
		return labRoomReservationCreditDAO.executeQuery(sql);
	}


	/**
	 * 查询实验室预约信息
	 * @param labReservation
	 * @param tage
	 * @param currpage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Set<LabReservation> findLabreservationmanage(LabReservation labReservation, int tage, int currpage,
														int pageSize) {
		String sql = "select l from LabReservation l where 1=1 ";

		if (labReservation.getEventName() != null && labReservation.getEventName() != "") {
			sql += " and (l.eventName like '%" + labReservation.getEventName().trim()+"%'"
					+ " or  l.labRoom.labRoomName like '%" + labReservation.getEventName().trim()+"%')";
		}
		if (labReservation.getRemarks() != null && labReservation.getRemarks() != "") {
			sql += " and l.timetableSelfCourse.schoolTerm.id=" + Integer.parseInt(labReservation.getRemarks());
		}

		// qun 判断 登陆者是不是超级管理员，实验教务 是的话下边权限不能进入
		int qun = 0;
		// shareService.getUser().getAuthorities().toString().indexOf(str)
		// 实验室超级管理员，实验教务,选择实验室中心的所有
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1
				|| SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				.indexOf("ROLE_EXPERIMENTALTEACHING") != -1) {
			qun++;
			String num = "";
			for (LabRoom iterable_element : labRoomDAO.findAllLabRooms()) {
				num += iterable_element.getId() + ",";
			}
			if (num != "") {
				sql += " and l.labRoom.id in (" + num.substring(0, num.length() - 1) + " ) ";
			}
		}
		// shi 判断登陆者 不是超级管理员，实验教务 是不是实验室中心主任， 是的话下边权限不能进入
		int shi = 0;
		// 实验室中心主任，看到该中心下 自己实验室下边的实验室预约
		if (qun == 0
				&& (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				.indexOf("ROLE_EXCENTERDIRECTOR") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				.indexOf("ROLE_DEPARTMENTHEAD") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				.indexOf("ROLE_COLLEGELEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
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
				sql += " and  l.labRoom.id in (select r.labRoom.id from LabRoomAdmin r where r.user.username like '"
						+ shareService.getUser().getUsername() + "')";
			}
		}
		// 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，是不是实验室管理员 是的话下边权限不能进入
		// System.out.println("---qun--"+qun+"---shi---"+shi+"---guan---"+guan);

		// 老师和学生SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_STUDENT")
		// != -1 ||
		// sga.getAuthority().indexOf("ROLE_TEACHER")
		// != -1
//		if (qun == 0 && shi == 0 && guan == 0) {
//			if(shareService.getUser().getUsername()!=null){
//				sql += " and  l.user.username='" + shareService.getUser().getUsername() + "' ";
//			}
//
//		}

		// 通过
		if (tage == 1) {
			sql += " and l.auditResults=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.auditResults=2";
		}
		// 3未审核
		if (tage == 3) {
			sql += " and l.auditResults=3";
		}
		// 4审核未通过
		if (tage == 4) {
			sql += " and l.auditResults=4";
		}
		sql += "   order by l.id desc";
		List<LabReservation> lll = labReservationDAO.executeQuery(sql);
		Set<LabReservation> sliet = new HashSet<LabReservation>();
		for (LabReservation lab : lll) {
			sliet.add(lab);
		}
		return sliet;
	}
	/****************************************************************************
	 *Description：查找所有实验室预约信息（不分页)
	 *
	 *@author：廖文辉
	 *@date:2018-10-29
	 ****************************************************************************/
	public List<LabReservation> findLabReservationNoPage(){
		String sql = "select l from LabReservation l where 1=1 ";
		List<LabReservation> labReservations=labReservationDAO.executeQuery(sql,0,-1);
		return labReservations;
	}
	/****************************************************************************
	 *Description：根据实验室预约查找实验室预约时间表
	 *
	 *@author：廖文辉
	 *@date:2018-12-18
	 ****************************************************************************/
	public List<LabReservationTimeTable> findLabReservationTimetableByLabReservation(LabReservation labReservation){
		String sql =" select l from LabReservationTimeTable l where 1=1";
		if(labReservation!=null) {
			sql += " and l.labReservation.id=" + labReservation.getId();
		}
		List<LabReservationTimeTable> labReservationTimeTables=labReservationTimeTableDAO.executeQuery(sql);
		return labReservationTimeTables;
	}
	/****************************************************************************
	 *Description：根据实验室预约查找timetableappointment
	 *
	 *@author：廖文辉
	 *@date:2018-12-19
	 ****************************************************************************/
	public List<TimetableAppointmentSameNumber> getTimetableAppointmentSameNumber(LabReservation labReservation){
		String sql="select t from TimetableAppointmentSameNumber t where 1=1";
		if(labReservation!=null&&labReservation.getTimetableAppointment()!=null) {
			sql += " and t.timetableAppointment.id=" + labReservation.getTimetableAppointment().getId();
		}
		List<TimetableAppointmentSameNumber> TimetableAppointmentSameNumber=timetableAppointmentSameNumberDAO.executeQuery(sql);
		return  TimetableAppointmentSameNumber;
	}
}