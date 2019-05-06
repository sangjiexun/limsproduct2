package net.zjcclims.service.video;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service("VideoPublishService")
public class VideoPublishServiceImpl implements VideoPublishService {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SystemTimeDAO systemTimeDAO;
	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private TimetableLabRelatedDAO timetableLabRelatedDAO;

	@Autowired
	private ShareService shareService;
	@Autowired
	private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;

	/************************************************************ 
	 * @功能:获取当前节次(视频发布系统特别版，SystemTime的数据是特制的)
	 * @作者：由controller移入
	 * @日期：2016-07-12
	 ************************************************************/
	public int getCurClass() {
		//查找节次,节次表id大于29的是被修改过的特殊节次时间
		StringBuffer sb = new StringBuffer(
				"select c from SystemTime c where (now() >= " +
						"c.startDate and now() <=c.endDate ) ");

		List<SystemTime> systemTime = systemTimeDAO.executeQuery(sb.toString());
		if (systemTime.size() > 0) {
			return systemTime.get(0).getSection();
		}
		return 0;
	}

	/**
	 * 功能:获取当前课程
	 * 由controller移入
	 * 2016.07.12
	 */
	public List<TimetableAppointment> getCurSch(int classSection, int labId) {
		List<TimetableAppointment> cdd = new ArrayList<TimetableAppointment>();
		// 当前时间
		Calendar calendar = Calendar.getInstance();
		// 当前是周几
		int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		// 当前学期
		int term = shareService.getBelongsSchoolTerm(calendar).getId();

		StringBuffer sb = new StringBuffer(
				"select distinct c from TimetableAppointment c, TimetableLabRelated tr"//, SchoolCourse sc
						+"  join c.timetableAppointmentSameNumbers tasn"
						+ " where tr.timetableAppointment.id = c.id"
						+ " and c.weekday=" + weekday
						+ " and tasn.startClass<=" + classSection + " and tasn.endClass>=" + classSection
						+ " and tr.labRoom.id = " + labId
						+ " and c.schoolCourse.schoolTerm.id = " + term
//				+" and sc.schoolTerm.id = "+term
		);
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());//获取当前周次
		// 教务排课
		List<TimetableAppointment> schoolAppointments = timetableAppointmentDAO.executeQuery(sb.toString());
		if (schoolAppointments != null && schoolAppointments.size() > 0) {
			for (TimetableAppointment timetableAppointment : schoolAppointments) {
				int startWeek = timetableAppointment.getStartWeek();//起始周
				int endWeek = timetableAppointment.getEndWeek();//结束周
				if (week >= startWeek && week <= endWeek) {
					cdd.add(timetableAppointment);
				}
			}
		}

		// 自主排课
		StringBuffer sb_self = new StringBuffer(
				"select distinct c from TimetableAppointment c, TimetableLabRelated tr"//, TimetableSelfCourse tsc
						+"  join c.timetableAppointmentSameNumbers tasn"
						+ " where tr.timetableAppointment.id = c.id"
//				+" and c.timetableSelfCourse.id = tsc.id"
						+ " and c.weekday=" + weekday
						+ " and tasn.startClass<=" + classSection + " and tasn.endClass>=" + classSection
						+ " and tr.labRoom.id = " + labId
						+ " and c.timetableSelfCourse.schoolTerm.id = " + term
//				+" and tsc.schoolTerm.id = "+term
		);
		List<TimetableAppointment> selfAppointments = timetableAppointmentDAO.executeQuery(sb_self.toString());
		if (selfAppointments != null && selfAppointments.size() > 0) {
			for (TimetableAppointment timetableAppointment : selfAppointments) {
				int startWeek = timetableAppointment.getStartWeek();//起始周
				int endWeek = timetableAppointment.getEndWeek();//结束周
				if (week >= startWeek && week <= endWeek) {
					cdd.add(timetableAppointment);
				}
			}
		}
		return cdd;
	}

	/*************************************************************************************
	 * @內容：根据排课和周次查询实际考勤人数
	 * @作者： 贺子龙
	 *************************************************************************************/
	public int countStudentAttendanceByWeek(int appId, Integer week, int weekday, int classSection) {
		// 周次限定
		String sql = "select count(*) from timetable_attendance t where t.week=" + week;
		// 排课限定
		sql += " and t.appointment_id=" + appId;
		// 考勤限定
		sql += " and (t.attendance_machine = 1 or t.actual_attendance = 1)";
		// 节次限定
		sql += " and t.start_class<=" + classSection + " and t.end_class>=" + classSection;
		// 星期限定
		sql += " and t.weekday=" + weekday;
		// 执行查询
		Query query = entityManager.createNativeQuery(sql);
		System.out.println(sql);
		BigInteger count = (BigInteger) query.getSingleResult();
		return count.intValue();
	}

	/**
	 * Description 查询当前学期所有排课、预约的实验室
	 *
	 * @return
	 * @author 陈乐为 2018-9-20
	 */
	@Override
	public String findAllLabByCourseTerm(int currpage, int pageSize, Integer type, String id, Integer floor) {
		List<TimetableAppointment> cdd = new ArrayList<TimetableAppointment>();
		// 当前时间
		Calendar calendar = Calendar.getInstance();
		// 当前是周几
		int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		// 当前学期
		int term = shareService.getBelongsSchoolTerm(calendar).getId();
		// 当前节次
		int classSection = this.getCurClass();
		// 当前周次
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());//获取当前周次
		// 获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		// 根据类型参数获取应用的sql片段
		String typeSql = getTypeSql(type, id, floor, classSection);
		List<LabRoom> labRoomList = labRoomDAO.executeQuery(
				"select DISTINCT t.labRoom from TimetableLabRelated t "
						+" join t.timetableAppointment.timetableAppointmentSameNumbers tasn "
						+ "where t.timetableAppointment.schoolTerm.id=" + schoolTerm.getId()
						+ " and t.timetableAppointment.weekday=" + weekday
						+ typeSql
						+ " and tasn.startWeek<=" + week + " and tasn.endWeek>=" + week
						+ " and t.timetableAppointment.status = 1 order by t.labRoom.labRoomName", 0, -1);
		List<TimetableAppointmentSameNumber> appointments = timetableAppointmentSameNumberDAO.executeQuery(
				"select tasn from TimetableAppointmentSameNumber tasn " +
						" join tasn.timetableAppointment.timetableLabRelateds t"
						+ " where t.timetableAppointment.schoolTerm.id=" + schoolTerm.getId()
						+ " and t.timetableAppointment.weekday=" + weekday
						+ typeSql
						+ " and tasn.startWeek<=" + week + " and tasn.endWeek>=" + week
						+ " and t.timetableAppointment.status = 1"
						+ " order by t.labRoom.labRoomName,tasn.timetableAppointment.schoolCourseInfo.courseNumber,tasn.startClass", (currpage - 1) * pageSize, pageSize);

		String labName = "";// 实验室名称
		String courseName = "";//课程名称
		String teacher = "-";//老师
		String classes = "-";//班级
		int stuNum = 1;//应到人数
		String clas = "";// 节次
		String jsonWeek = "[";
		for (TimetableAppointmentSameNumber appointment : appointments) {
			teacher = "";//老师
			// 判断是否预约
			if(appointment.getTimetableAppointment().getTimetableStyle()==7) {
				teacher = shareService.getUserByPrimaryKey(appointment.getTimetableAppointment().getCreatedBy()).getCname();
				courseName = "预约使用";
					stuNum = appointment.getTimetableAppointment().getGroupCount();
					classes = appointment.getTimetableAppointment().getSchoolClasses().getClassName();
			} else {
				// 教师名称
				Set<TimetableTeacherRelated> relateds = appointment.getTimetableAppointment().getTimetableTeacherRelateds();
				for (TimetableTeacherRelated timetableTeacherRelated : relateds) {
					teacher += timetableTeacherRelated.getUser().getCname() + " ";
				}
				// 课程名称
				courseName = appointment.getTimetableAppointment().getSchoolCourseInfo().getCourseName();
				if (appointment.getTimetableAppointment().getTimetableStyle() == 5 || appointment.getTimetableAppointment().getTimetableStyle() == 6) {// 自主排课
					classes = "自主排课班级";
					if (!EmptyUtil.isObjectEmpty(appointment.getTimetableAppointment().getTimetableSelfCourse())) {
						stuNum = appointment.getTimetableAppointment().getTimetableSelfCourse().getTimetableCourseStudents().size();
					}
				} else {// 非自主排课
					//需要新加字段，先拿来充数。。。
					if (!EmptyUtil.isObjectEmpty(appointment.getTimetableAppointment().getSchoolCourse())) {
						Set<SchoolClasses> schoolClasses = appointment.getTimetableAppointment().getSchoolCourse().getSchoolClasseses();
						for(SchoolClasses schoolClass : schoolClasses) {
							classes += schoolClass.getClassName() + " ";
//							if(schoolClass.getClassStudentsNumber() != null) {
//								stuNum += Integer.valueOf(schoolClass.getClassStudentsNumber());
//							}
						}
					}
					stuNum = appointment.getTimetableAppointment().getSchoolCourseDetail().getSchoolCourseStudents().size();
				}
			}
			// 实验室名称
			Set<TimetableLabRelated> labRelateds = appointment.getTimetableAppointment().getTimetableLabRelateds();
			for (TimetableLabRelated labRelated : labRelateds) {
				for (LabRoom labRoom : labRoomList) {
					if (labRelated.getLabRoom().getId().equals(labRoom.getId())) {
						labName = labRoom.getLabRoomName();
						break;
					}
				}
			}
			// 节次
			if(appointment.getStartClass().equals(appointment.getEndClass())) {
				clas = "第"+appointment.getStartClass()+"节";
			}else {
				clas = "第"+appointment.getStartClass()+"-"+appointment.getEndClass()+"节";
			}
			jsonWeek = jsonWeek + "{\"labName\":\"" + labName + "\",\"courseName\":\"" + courseName + "\",\"teacher\":\"" + teacher + "\"," +
					"\"classes\":\"" + classes + "\",\"status\":\""+clas+"\",\"stuNum\":\"" + stuNum + "\"},";
		}
		jsonWeek = jsonWeek.substring(0, jsonWeek.length() - 1);
		jsonWeek = jsonWeek + "]";
		return jsonWeek;
	}

	@Override
	public List<Object[]> findAllLabByCourseTerms(int currpage, int pageSize, Integer type, String id, Integer floor) {
		List<TimetableAppointment> cdd = new ArrayList<TimetableAppointment>();
		// 当前时间
		Calendar calendar = Calendar.getInstance();
		// 当前是周几
		int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		// 当前学期
		int term = shareService.getBelongsSchoolTerm(calendar).getId();
		// 当前节次
		int classSection = this.getCurClass();
		// 当前周次
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());//获取当前周次
		// 获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		// 根据类型参数获取应用的sql片段
		String typeSql = getTypeSql(type, id, floor, classSection);
		List<LabRoom> labRoomList = labRoomDAO.executeQuery(
				"select DISTINCT t.labRoom from TimetableLabRelated t "
						+" join t.timetableAppointment.timetableAppointmentSameNumbers tasn "
						+ "where t.timetableAppointment.schoolTerm.id=" + schoolTerm.getId()
						+ " and t.timetableAppointment.weekday=" + weekday
						+ typeSql
						+ " and tasn.startWeek<=" + week + " and tasn.endWeek>=" + week
						+ " and t.timetableAppointment.status = 1 order by t.labRoom.labRoomName", 0, -1);
		List<TimetableAppointmentSameNumber> appointments = timetableAppointmentSameNumberDAO.executeQuery(
				"select tasn from TimetableAppointmentSameNumber tasn " +
						" join tasn.timetableAppointment.timetableLabRelateds t"
						+ " where t.timetableAppointment.schoolTerm.id=" + schoolTerm.getId()
						+ " and t.timetableAppointment.weekday=" + weekday
						+ typeSql
						+ " and tasn.startWeek<=" + week + " and tasn.endWeek>=" + week
						+ " and t.timetableAppointment.status = 1 "
						+ " order by t.labRoom.labRoomName,tasn.timetableAppointment.schoolCourseInfo.courseNumber,tasn.startClass", (currpage - 1) * pageSize, pageSize);

		String labName = "";// 实验室名称
		String courseName = "预约使用";//课程名称
		String teacher = "";//老师
		String classes = "-";//班级
		int stuNum = 0;//应到人数
		List<Object[]> objects = new ArrayList<>();
		for (TimetableAppointmentSameNumber appointment : appointments) {
			teacher = "";//老师
			// 实验室名称
			Set<TimetableLabRelated> labRelateds = appointment.getTimetableAppointment().getTimetableLabRelateds();
			for (TimetableLabRelated labRelated : labRelateds) {
				for (LabRoom labRoom : labRoomList) {
					if (labRelated.getLabRoom().getId().equals(labRoom.getId())) {
						labName = labRoom.getLabRoomName();
						break;
					}
				}
			}
			// 课程名称
			if(appointment.getTimetableAppointment().getSchoolCourseInfo()!=null) {
				courseName = appointment.getTimetableAppointment().getSchoolCourseInfo().getCourseName();
			}
			// 教师名称
			Set<TimetableTeacherRelated> relateds = appointment.getTimetableAppointment().getTimetableTeacherRelateds();
			if (relateds != null && relateds.size() > 0) {
				for (TimetableTeacherRelated timetableTeacherRelated : relateds) {
					teacher += timetableTeacherRelated.getUser().getCname() + " ";
				}
			}
			if (appointment.getTimetableAppointment().getTimetableStyle() == 5 || appointment.getTimetableAppointment().getTimetableStyle() == 6) {// 自主排课
				classes = "自主排课班级";
				if (!EmptyUtil.isObjectEmpty(appointment.getTimetableAppointment().getTimetableSelfCourse())) {
					stuNum = appointment.getTimetableAppointment().getTimetableSelfCourse().getTimetableCourseStudents().size();
				}
			} else {// 非自主排课
//					if (!EmptyUtil.isObjectEmpty(timetableAppointment.getSchoolCourse())
//							&& !EmptyUtil.isStringEmpty(timetableAppointment.getSchoolCourse().getClassesName())) {
//						classes = timetableAppointment.getSchoolCourse().getClassesName().replace(",", "-");
//					}
				//需要新加字段，先拿来充数。。。
				if (!EmptyUtil.isObjectEmpty(appointment.getTimetableAppointment().getSchoolCourseDetail()) &&
						!EmptyUtil.isStringEmpty(appointment.getTimetableAppointment().getSchoolCourseDetail().getCourseClassName())) {
					classes = appointment.getTimetableAppointment().getSchoolCourseDetail().getCourseClassName();
				}
				if (!EmptyUtil.isObjectEmpty(appointment.getTimetableAppointment().getSchoolCourseDetail()))
				stuNum = appointment.getTimetableAppointment().getSchoolCourseDetail().getSchoolCourseStudents().size();
				if(appointment.getTimetableAppointment().getTimetableStyle() == 7){
					stuNum = appointment.getTimetableAppointment().getGroupCount();
					if(appointment.getTimetableAppointment().getSchoolClasses()!=null && appointment.getTimetableAppointment().getSchoolClasses().getClassName()!=null) {
						classes = appointment.getTimetableAppointment().getSchoolClasses().getClassName();
					}
				}
			}
			Object[] obj = new Object[7];
			obj[0] = labName;
			obj[1] = courseName;
			obj[2] = teacher;
			obj[3] = classes;
			if(appointment.getStartClass().equals(appointment.getEndClass())) {
				obj[4] = "第"+appointment.getStartClass()+"节";
			}else {
				obj[4] = "第"+appointment.getStartClass()+"-"+appointment.getEndClass()+"节";
			}
			obj[5] = stuNum;
			obj[6] = "-";
			objects.add(obj);
		}
		return objects;
	}

	/**
	 * Description 当前正在上课的实验室总数
	 *
	 * @return
	 * @author 陈乐为 2018-9-25
	 */
	@Override
	public int findAllLabCountByCourseTerm(Integer type, String id, Integer floor) {
		List<TimetableAppointment> cdd = new ArrayList<TimetableAppointment>();
		// 当前时间
		Calendar calendar = Calendar.getInstance();
		// 当前是周几
		int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		// 当前节次
		int classSection = this.getCurClass();
		// 当前周次
		int week = shareService.getBelongsSchoolWeek(Calendar.getInstance());//获取当前周次
		// 获取当前学期
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		// 根据类型参数获取应用的sql片段
		String typeSql = getTypeSql(type, id, floor, classSection);
		String sql = "select DISTINCT count(t.labRoom) from TimetableLabRelated t "
				+" join t.timetableAppointment.timetableAppointmentSameNumbers tasn "
				+ "where t.timetableAppointment.schoolTerm.id=" + schoolTerm.getId()
				+ " and t.timetableAppointment.weekday=" + weekday
				+ typeSql
				+ " and tasn.startWeek<=" + week + " and tasn.endWeek>=" + week
				+ " and t.timetableAppointment.status = 1";

		int pages = ((Long) labRoomDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
		return pages;
	}

	private String getTypeSql(Integer type, String id, Integer floor, int classSection) {
		String typeSql = "";
		switch (type){
			case 1:
				typeSql = " and t.labRoom.id="+id;
				break;
			case 2:
				typeSql = " and t.labRoom.floorNo="+floor+" and t.labRoom.systemBuild.buildNumber= '"+ id +"'";
				break;
			case 3:
				typeSql = " and t.labRoom.systemBuild.buildNumber= '"+ id +"'";
				break;
			case 4:
				typeSql = " and tasn.startClass<=" + classSection + " and tasn.endClass>=" + classSection;
				break;
			default:
		}
		return typeSql;
	}


	}
