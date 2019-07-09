package net.zjcclims.service.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.system.SchoolWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service("TimetableProgressSchedulingService")
public class TimetableProgressSchedulingServiceImpl implements TimetableProgressSchedulingService {

	@Autowired
	private TimetableAppointmentDAO timetableAppointmentDAO;
	@Autowired
	private TimetableLabRelatedDAO timetableLabRelatedDAO;
	@Autowired
	private SchoolCourseDetailDAO schoolCourseDetailDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private OperationItemDAO operationItemDAO;
	@Autowired
	private TimetableItemRelatedDAO timetableItemRelatedDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
	@Autowired
	private TimetableTutorRelatedDAO timetableTutorRelatedDAO;
	@Autowired
	private TimetableGroupDAO timetableGroupDAO;
	@Autowired
	private TimetableGroupStudentsDAO timetableGroupStudentsDAO;
	@Autowired
	private TimetableBatchDAO timetableBatchDAO;
	@Autowired
	private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private LabRoomDeviceReservationDAO labRoomDeviceReservationDAO;
	@Autowired
	private TimetableCourseSchedulingService timetableCourseSchedulingService;
	@Autowired
	private MySQLService mysqlService;
	@Autowired
	private SchoolWeekService schoolWeekService;
	@Autowired
	private SoftwareDAO softwarenDAO;
	@Autowired
	private TimetableSoftwareRelatedDAO timetableSoftwareRelatedDAO;
	@Autowired
	private OuterApplicationServiceImpl outerApplicationServiceImpl;
	@Autowired
	private TimetableSelfCourseDAO timetableSelfCourseDAO;
	@Autowired
	private SchoolTermActiveDAO schoolTermActiveDAO;
	@Autowired
	private TimetableAppointmentChangeDAO timetableAppointmentChangeDAO;
	@PersistenceContext  
    private EntityManager entityManager;
	@Autowired
	private LabroomTimetableRegisterDAO labroomTimetableRegisterDAO;
	@Autowired
	private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private TimetableAppointmentChangeAduitDAO appointmentChangeAduitDAO;
	@Autowired
	private TimetableAppointmentResultDAO timetableAppointmentResultDAO;

	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private SchoolWeekDAO schoolWeekDAO;
	@Autowired
	private SchoolCourseService schoolCourseService;
	/***********************************************************************************************
	 * Description：排课模块通用{根据选课组编号获取排课记录}
	 * @author：戴昊宇
	 * @Date：2017-08-25
	 ***********************************************************************************************/
	public List<TimetableAppointment> findTimetableAppointmentByCourseCode(String courseNo){
		// 建立查询
		String sql = "select t from TimetableAppointment t where 1=1";
		// 限制条件
		sql+=" and t.courseCode like '"+courseNo+"'";
		sql+=" order by t.startWeek,t.weekday";
		// 执行查询
		List<TimetableAppointment> appointments = timetableAppointmentDAO.executeQuery(sql, 0, -1);
		// 返回结果
		return appointments;
	}
	/*************************************************************************************
	 * @Description: 教务排课--查看校历
	 * @author： 贺子龙
	 * @date：2017-10-07
	 *************************************************************************************/
	public List<SchoolWeek> findSchoolWeekByCourseCode(String courseCode){
		List<SchoolWeek> schoolWeeks = new ArrayList<SchoolWeek>();
		// 根据选课组编号获取教务排课信息
		List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailService.getSchoolCourseDetailByCourseCode(courseCode);
		for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
			int termId = schoolCourseDetail.getSchoolTerm().getId();
			int weekday = schoolCourseDetail.getWeekday();
			int startWeek = schoolCourseDetail.getStartWeek();
			int endWeek = schoolCourseDetail.getEndWeek();
			String sql = "select s from SchoolWeek s where 1=1"
					+ " and s.week>="+startWeek
					+ " and s.week<="+endWeek
					+ "and s.weekday="+weekday
					+" and s.schoolTerm.id="+termId;
			List<SchoolWeek> schoolWeeksPart = schoolWeekDAO.executeQuery(sql);
			schoolWeeks.addAll(schoolWeeksPart);
		}
		return schoolWeeks;
	}

	public String[] getTimetableWeekClass(int[] intWeeks) {
		String startWeek = "1";
		String endWeek = "1";
		String sWeek = "";
		Arrays.sort(intWeeks);
		// 创建根据学期来查询课程；
		for (int i = 0; i < intWeeks.length; i++) {

			if (i == 0) {
				startWeek = String.valueOf(intWeeks[i]);
				if (intWeeks.length == 1) {
					sWeek = startWeek + ";";
				}
			} else {
				if (intWeeks[i] - intWeeks[i - 1] == 1) {
					if (i == intWeeks.length - 1) {
						endWeek = String.valueOf(intWeeks[i]);
						sWeek = sWeek + startWeek + "-" + endWeek + ";";
					} else {
						continue;
					}
				} else if (intWeeks[i] - intWeeks[i - 1] > 1 && intWeeks.length > i + 1) {
					endWeek = String.valueOf(intWeeks[i - 1]);
					sWeek = sWeek + startWeek + "-" + endWeek + ";";
					startWeek = String.valueOf(intWeeks[i]);

				} else if (intWeeks[i] - intWeeks[i - 1] > 1 && intWeeks.length == i + 1) {
					endWeek = String.valueOf(intWeeks[i - 1]);
					sWeek = sWeek + startWeek + "-" + endWeek + ";";
					sWeek = sWeek + String.valueOf(intWeeks[i]) + "-" + String.valueOf(intWeeks[i]);
				}
			}
		}
		return sWeek.split(";");
	}

	/***********************************************************************************************
	 * Description：排课模块{保存教务实验课}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-31
	 ***********************************************************************************************/
	public TimetableAppointment saveSchoolTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
													int weekday, String teachers, String courseNo, Integer isOther,String teachers2,String content,Integer classType){

		// 定义布尔变量，表征是否满足判冲条件
		boolean timeOK = true;
		// 把已选的周次和可用周次比对
		if (courseNo.contains("isCopy")) {
			// do nothing 复制的课程，不用判冲，直接保存
			courseNo = courseNo.replaceAll("isCopy", "");
		}else{
			// 通过接口来查出可用的周次
			Integer[] validWeeks = outerApplicationServiceImpl.getValidWeeks(term, classes, labrooms, weekday, 0);
			for (int chosenWeek : weekArray) {
				if (!EmptyUtil.isIntegerArray(validWeeks, chosenWeek)) {// 所选的周次有不包含在可用周次范围内的
					timeOK = false;
				}
			}
		}

		if (timeOK) {// 时间、地点合法，允许排课、或者复制的排课
			// 周次进行排序
			String[] sWeek = this.getTimetableWeekClass(weekArray);

			// 节次进行排序
			String[] sClasses = this.getTimetableWeekClass(classes);
			// 判断有没有跳节、跳周
			boolean jumpTime = false; // 默认没有
			if (sWeek.length > 1 || sClasses.length > 1) {
				jumpTime = true;
			}
			// 根据编号找到对应教务课程
			SchoolCourse course = schoolCourseService.findSchoolCourseByPrimaryKey(courseNo);
			String courseNumber = course.getSchoolCourseInfo().getCourseNumber();
			// 保存排课主表
			TimetableAppointment timetableAppointment = new TimetableAppointment();
			//schoolselfcourse关联外键先写死保存
			timetableAppointment.setTimetableSelfCourse(timetableSelfCourseDAO.findTimetableSelfCourseById(365));
			//上课地点保存到SchoolCourseDetail的classroom_type中
			if(course != null && course.getSchoolCourseDetails() != null && course.getSchoolCourseDetails().size() > 0)
			{
				for(SchoolCourseDetail s:course.getSchoolCourseDetails()){
					timetableAppointment.setSchoolCourseDetail(s);
					timetableAppointment.setAppointmentNo(s.getCourseDetailNo());
				}

			}
			timetableAppointment.getSchoolCourseDetail().setClassroomType(String.valueOf(labrooms));
			// 保存操作时间
			timetableAppointment.setCreatedDate(Calendar.getInstance());
			timetableAppointment.setUpdatedDate(Calendar.getInstance());
			// 设置排课的选课组编号
			timetableAppointment.setCourseCode(course.getCourseCode());
			timetableAppointment.setSchoolCourse(course);
			//保存学期
			timetableAppointment.setSchoolTerm(course.getSchoolTerm());
			timetableAppointment.setSchoolCourseInfo(course.getSchoolCourseInfo());


			// 生成主表公用数据
			timetableAppointment = this.saveTimetableMain(timetableAppointment, sWeek, sClasses, weekday);
			// 保存排课类型为教务不分批
			if (!EmptyUtil.isIntegerEmpty(isOther) && isOther.equals(1)) {// 其他时间段
				timetableAppointment.setTimetableStyle(3);
			}else {// 教务安排时间段
				timetableAppointment.setTimetableStyle(2);
			}
			// 保存课程内容
			timetableAppointment.setContent(content);
			// 保存课程类型
			timetableAppointment.setClassType(classType);
			timetableAppointment.setCreatedBy(shareService.getUser().getUsername());
			// 保存主表
			timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
			// 保存排课关联表
			// 1 保存排课跳周表（如果有的话）
			if (jumpTime) {
				this.saveTimetableAppointmentSameNumber(timetableAppointment, sWeek, sClasses);
			}
			// 2 保存教师
			if (!EmptyUtil.isStringEmpty(teachers)) {
				this.saveTimetableTeacherRelated(timetableAppointment, teachers);
			}
			if (!EmptyUtil.isStringEmpty(teachers2)&&(!teachers2.equals(teachers))) {
				this.saveTimetableTeacherRelated(timetableAppointment,teachers2);
			}
			// 3 保存项目
			//this.saveTimetableItemRelated(timetableAppointment, items);
			// 4 保存实验室
			this.saveTimetableLabRelated(timetableAppointment, labrooms);
			// 返回成功
			return timetableAppointment;
		}else {// 该时间、该实验室已经被占用，不能排课
			// 返回失败
			return new TimetableAppointment();
		}

	}

	/*************************************************************************************
	 * Description：排课保存公用方法{生成主表字段，除去选课组关联（自主排课选课组和教务排课选课组不同）}
	 * @author： 贺子龙
	 * @Date：2016-09-01
	 *************************************************************************************/
	public TimetableAppointment saveTimetableMain(TimetableAppointment timetableAppointment,
												  String[] sWeek, String[] sClasses, int weekday){
		// 保存操作时间
		timetableAppointment.setCreatedDate(Calendar.getInstance());
		timetableAppointment.setUpdatedDate(Calendar.getInstance());
		// 设置时间
		// 设置星期
		timetableAppointment.setWeekday(weekday);
		// 没有跳周、跳节
		// 设置周次
		int startWeek = 0;
		int endWeek = 0;
		if (sWeek[0].indexOf(("-")) == -1) {// 判断是否只有一周
			startWeek = Integer.parseInt(sWeek[0]);
			endWeek = Integer.parseInt(sWeek[0]);
		} else {// 如果是一个区间
			startWeek = Integer.parseInt(sWeek[0].split("-")[0]);
			endWeek = Integer.parseInt(sWeek[0].split("-")[1]);
		}
		timetableAppointment.setTotalWeeks(String.valueOf(( endWeek - startWeek + 1)));
		timetableAppointment.setStartWeek(startWeek);
		timetableAppointment.setEndWeek(endWeek);
		// 设置节次
		int startClass = 0;
		int endClass = 0;
		if (sClasses[0].indexOf(("-")) == -1) {// 判断是否只有一节
			startClass = Integer.parseInt(sClasses[0]);
			endClass = Integer.parseInt(sClasses[0]);
		} else {// 如果是一个区间
			startClass = Integer.parseInt(sClasses[0].split("-")[0]);
			endClass = Integer.parseInt(sClasses[0].split("-")[1]);
		}
		timetableAppointment.setTotalClasses(endClass - startClass + 1);
		timetableAppointment.setStartClass(startClass);
		timetableAppointment.setEndClass(endClass);
		// 保存排课状态为待提交
		timetableAppointment.setStatus(ConstantInterface.TIMETABLE_STATUS_NO_CONFIRM);
		return timetableAppointment;
	}

	/*************************************************************************************
	 * Description：排课保存公用方法{保存教师}
	 * @author： 贺子龙
	 * @Date：2016-09-01
	 *************************************************************************************/
	public void saveTimetableTeacherRelated(TimetableAppointment timetableAppointment, String teacherString){
		// 转化为字符数组
		String[] teachers = teacherString.split(",");
		// 新建关联表
		TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
		// 保存关联表
		if (teachers!=null&&teachers.length > 0) {
			for (int i = 0; i < teachers.length; i++) {
				timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i]));
				timetableTeacherRelated.setTimetableAppointment(timetableAppointment);
				timetableTeacherRelatedDAO.store(timetableTeacherRelated);
				timetableTeacherRelatedDAO.flush();
			}
		}
	}
	/*************************************************************************************
	 * Description：排课保存公用方法{保存实验室}
	 * @author： 贺子龙
	 * @Date：2016-09-01
	 *************************************************************************************/
	public void saveTimetableLabRelated(TimetableAppointment timetableAppointment, int[] labRooms){
		// 新建关联表
		TimetableLabRelated timetableLabRelated = new TimetableLabRelated();
		if (labRooms != null && labRooms.length > 0) {
			for (int labroomId : labRooms) {
				// 获取实验室
				LabRoom labRoom = labRoomDAO.findLabRoomById(labroomId);
				// 设置实验室
				timetableLabRelated.setLabRoom(labRoom);
				timetableLabRelated.setTimetableAppointment(timetableAppointment);
				timetableLabRelatedDAO.store(timetableLabRelated);
				timetableLabRelatedDAO.flush();
			}
		}
	}

	/*************************************************************************************
	 * Description：排课保存公用方法{保存跳周}
	 * @author： 贺子龙
	 * @Date：2016-09-01
	 *************************************************************************************/
	public void saveTimetableAppointmentSameNumber(TimetableAppointment timetableAppointment,
												   String[] sWeek, String[] sClasses){
		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {
				TimetableAppointmentSameNumber sameNumber = new TimetableAppointmentSameNumber();
				sameNumber.setCreatedDate(Calendar.getInstance());
				sameNumber.setUpdatedDate(Calendar.getInstance());
				// 设置跳周
				int startWeekSame = 0;
				int endWeekSame = 0;
				if (sWeek[i].indexOf(("-")) == -1) {// 判断是否只有一周
					startWeekSame = Integer.parseInt(sWeek[i]);
					endWeekSame = Integer.parseInt(sWeek[i]);
				} else {// 如果是一个区间
					startWeekSame = Integer.parseInt(sWeek[i].split("-")[0]);
					endWeekSame = Integer.parseInt(sWeek[i].split("-")[1]);
				}
				sameNumber.setTotalWeeks(String.valueOf(endWeekSame - startWeekSame + 1));
				sameNumber.setStartWeek(startWeekSame);
				sameNumber.setEndWeek(endWeekSame);
				// 设置跳节
				int startClassSame = 0;
				int endClassSame = 0;
				if (sClasses[j].indexOf(("-")) == -1) {// 判断是否只有一节
					startClassSame = Integer.parseInt(sClasses[j]);
					endClassSame = Integer.parseInt(sClasses[j]);
				} else {// 如果是一个区间
					startClassSame = Integer.parseInt(sClasses[j].split("-")[0]);
					endClassSame = Integer.parseInt(sClasses[j].split("-")[1]);
				}
				sameNumber.setTotalClasses(endClassSame - startClassSame + 1);
				sameNumber.setStartClass(startClassSame);
				sameNumber.setEndClass(endClassSame);
				sameNumber.setTimetableAppointment(timetableAppointment);
				timetableAppointmentSameNumberDAO.store(sameNumber);
				timetableAppointmentSameNumberDAO.flush();
			}
		}
	}

}