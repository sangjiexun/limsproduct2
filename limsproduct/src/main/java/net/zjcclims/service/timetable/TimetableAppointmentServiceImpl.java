package net.zjcclims.service.timetable;

import api.net.gvsunlims.constant.ConstantInterface;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.constant.MonthReport;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.MySQLService;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.evaluation.EvaluationService;
import net.zjcclims.service.system.SchoolWeekService;
import net.zjcclims.web.common.PConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("TimetableAppointmentService")
public class TimetableAppointmentServiceImpl implements TimetableAppointmentService {

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
	private SchoolWeekDAO schoolWeekDAO;
	@Autowired
	private SchoolCourseDetailService schoolCourseDetailService;
	@Autowired
	private SchoolCourseDAO schoolCourseDAO;
	private PConfig pConfig;
	@Autowired
	private EvaluationService evaluationService;
	@Autowired
	SchoolCourseStudentDAO schoolCourseStudentDAO;
	@Autowired
	TimetableCourseStudentDAO timetableCourseStudentDAO;
	/*************************************************************************************
	 * @內容：查看所有的预约的列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableAppointment> getTimetableAppointmentsByQuery(int termId,
			TimetableAppointment timetableAppointment, int status, int curr, int size, String acno) {
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select distinct c from TimetableAppointment  c ,TimetableTeacherRelated d where 1=1 and d.timetableAppointment.id = c.id ");
		if (status != -1) {
			sql.append(" and c.status = " + status);
		}

		if (timetableAppointment.getId() != -1  && timetableAppointment.getSchoolCourseInfo() != null
				&& timetableAppointment.getSchoolCourseInfo().getCourseNumber() != null) {
			sql.append(" and c.schoolCourseInfo.courseNumber like '"
					+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "' ");
		}
		if(timetableAppointment.getDetail() != null){
			sql.append(" and d.user.username like '"+ timetableAppointment.getDetail() + "'");
		}
		/**
		 * 判断是否只是老师角色
		 **/
		User user = shareService.getUserDetail();
		String judge = ",";
		for (Authority authority : user.getAuthorities()) {
			judge = judge + "," + authority.getId() + ",";
		}

		if (timetableAppointment.getSchoolCourseInfo() != null
				&& !timetableAppointment.getSchoolCourseInfo().getCourseNumber().equals("-1")) {
			sql.append(" and c.schoolCourseInfo.courseNumber like '%"
					+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "%'");
		}

		// 按照课程排序
		sql.append(" and (c.schoolCourseInfo.academyNumber like '%" + acno
				+ "%') ");

		// 自主排课
		String sqlSelf = sql.toString();
		// 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
		if (judge.indexOf(",2,") != -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1) {
			sqlSelf = sqlSelf + " and (c.timetableSelfCourse.user.username like '" + user.getUsername() + "'"
					+" or d.user.username like '"+ user.getUsername() + "')";
		}
		sqlSelf = sqlSelf + " and (c.timetableStyle!=1 and c.timetableStyle!=2 and c.timetableStyle!=3 and c.timetableStyle!=4 and c.timetableSelfCourse.schoolTerm ="
				+ termId + ") order by c.courseCode ,c.weekday,c.timetableNumber desc";
		List<TimetableAppointment> timetableAppointmentSelfs = timetableAppointmentDAO.executeQuery(sqlSelf, curr
				* size, size);

		// 教务排课
		String sqlAcademy = sql.toString();
		// 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
		if (judge.indexOf(",2,") != -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1) {
			sqlAcademy = sqlAcademy + " and (c.schoolCourseDetail.user.username like '" + user.getUsername() + "'"
					+" or d.user.username like '"+ user.getUsername() + "')";
		}
		sqlAcademy = sqlAcademy + " and (c.timetableStyle!=5 and c.timetableStyle!=6 and c.schoolCourse.schoolTerm ="
				+ termId + ") order by c.courseCode ,c.weekday,c.timetableNumber desc ";
		List<TimetableAppointment> timetableAppointmentAcademys = timetableAppointmentDAO.executeQuery(sqlAcademy, curr
				* size, size);

		// 合并教务排课及自主排课内容
		for (TimetableAppointment timetableAppointment1 : timetableAppointmentSelfs) {
			timetableAppointmentAcademys.add(timetableAppointment1);
		}

		Collections.sort(timetableAppointmentAcademys, new Comparator<TimetableAppointment>() {
			public int compare(TimetableAppointment arg0, TimetableAppointment arg1) {

				if (!arg0.getTimetableGroups().isEmpty() && !arg1.getTimetableGroups().isEmpty()) {
					return arg0.getTimetableGroups().iterator().next().getTimetableBatch().getId()
							.compareTo(arg1.getTimetableGroups().iterator().next().getTimetableBatch().getId());
				}  else {
					return 0;
				}

			}
		});
		return timetableAppointmentAcademys;
	}

	/*************************************************************************************
	 * @內容：查看计数的所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountTimetableAppointmentsByQuery(int termId, TimetableAppointment timetableAppointment, int status,
			String acno) {
		// 创建根据学期来查询课程(二次排课)；
		StringBuffer sql = new StringBuffer(
				"select count(distinct c)  from TimetableAppointment c ,TimetableTeacherRelated d where 1=1  and d.timetableAppointment.id = c.id  and c.schoolCourseInfo.academyNumber like '%"
						+ acno + "%'");
		if (status != -1) {
            sql.append(" and c.status = " + status);
        }

		if(timetableAppointment.getDetail() != null){
			sql.append(" and d.user.username like '"+ timetableAppointment.getDetail() + "'");
		}

		/*	*//**
		 * 判断是否只是老师角色
		 **/
		User user = shareService.getUserDetail();
		String judge = ",";
		for (Authority authority : user.getAuthorities()) {
			judge = judge + "," + authority.getId() + ",";
		}

		if (timetableAppointment.getSchoolCourseInfo() != null
				&& !timetableAppointment.getSchoolCourseInfo().getCourseNumber().equals("-1")) {
			sql.append(" and c.schoolCourseInfo.courseNumber like '%"
					+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "%'");

		}

		// 自主排课
		String sqlSelf = sql.toString();
		// 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
		if (judge.indexOf(",2,") != -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1) {
			sqlSelf = sqlSelf + " and (c.timetableSelfCourse.user.username like '" + user.getUsername() + "'"
					+" or d.user.username like '"+ user.getUsername() + "')";
		}
		sqlSelf = sqlSelf + " and (c.timetableStyle!=1 and c.timetableStyle!=2 and c.timetableStyle!=3 and c.timetableStyle!=4 and c.timetableSelfCourse.schoolTerm ="
				+ termId + ") ";
		if (timetableAppointment.getSchoolCourseInfo() != null
				&& !timetableAppointment.getSchoolCourseInfo().getCourseNumber().equals("-1")) {
			sqlSelf += " and c.schoolCourseInfo.courseNumber like '%"
					+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "%'";

		}
		int iSelf = ((Long) timetableAppointmentDAO.createQuerySingleResult(sqlSelf).getSingleResult()).intValue();
		// 教务排课
		String sqlAcademy = sql.toString();
		// 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
		if (judge.indexOf(",2,") != -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1) {
			sqlAcademy = sqlAcademy + " and (c.schoolCourseDetail.user.username like '" + user.getUsername() + "'"
					+" or d.user.username like '"+ user.getUsername() + "')";
		}
		if (timetableAppointment.getSchoolCourseInfo() != null
				&& !timetableAppointment.getSchoolCourseInfo().getCourseNumber().equals("-1")) {
			sqlAcademy += " and c.schoolCourseInfo.courseNumber like '%"
					+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "%'";

		}
		sqlAcademy = sqlAcademy + " and (c.timetableStyle!=5 and c.timetableStyle!=6 and c.schoolCourse.schoolTerm ="
				+ termId + ") ";
		int iAcademy = ((Long) timetableAppointmentDAO.createQuerySingleResult(sqlAcademy).getSingleResult())
				.intValue();

		return iSelf + iAcademy;
	}

	/*************************************************************************************
     * @內容：查看所有的预约的列表安排
     * @作者：贺子龙
     * @日期：2016-04-09
     *************************************************************************************/
    public List<TimetableAppointment> getTimetableAppointmentsByQuery(int termId,
            String courseNumber, int status, int curr, int size,int flag) {
        // 创建根据学期来查询课程；
        StringBuffer sql = new StringBuffer(
                "select DISTINCT c from TimetableAppointment  c ,TimetableTeacherRelated d where 1=1 and d.timetableAppointment.id = c.id ");
        if(status != -1) {
        	sql.append(" and c.status="+status);
        }else{
			// 排除未提交
			sql.append(" and c.status != " + ConstantInterface.TIMETABLE_STATUS_NO_CONFIRM);
		}
        // 确认排课为1调课为0
        if(flag==1){
            sql.append(" and c.enabled=" + 1);
        }
        if (!courseNumber.equals("")) {
            sql.append(" and c.schoolCourseInfo.courseNumber like '" + courseNumber + "' ");
        }

        /**
         * 判断是否只是老师角色
         **/
        User user = shareService.getUserDetail();
        String judge = ",";
        for (Authority authority : user.getAuthorities()) {
            judge = judge + "," + authority.getId() + ",";
        }

        if (!courseNumber.equals("")) {
            sql.append(" and c.schoolCourseInfo.courseNumber like '%" + courseNumber + "%'");
        }

        // 自主排课
        String sqlSelf = sql.toString();
        // 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
        if (judge.indexOf(",2,") != -1 && judge.indexOf(",7,") == -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1 && judge.indexOf(",5,") == -1) {
            sqlSelf = sqlSelf + " and (c.timetableSelfCourse.user.username like '" + user.getUsername() + "'"
            		+" or d.user.username like '"+ user.getUsername() + "')";
        }
        sqlSelf = sqlSelf + " and (c.timetableStyle!=1 and c.timetableStyle!=2 and c.timetableStyle!=3 and c.timetableStyle!=4 and c.timetableSelfCourse.schoolTerm ="
                + termId + ") order by c.courseCode ,c.weekday,c.timetableNumber desc";
        List<TimetableAppointment> timetableAppointmentSelfs = timetableAppointmentDAO.executeQuery(sqlSelf, curr*size, size);

        // 教务排课
        String sqlAcademy = sql.toString();
        // 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
        if (judge.indexOf(",2,") != -1 && judge.indexOf(",7,") == -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1 && judge.indexOf(",5,") == -1) {
            sqlAcademy = sqlAcademy + " and (c.schoolCourseDetail.user.username like '" + user.getUsername() + "'"
            		+" or d.user.username like '"+ user.getUsername() + "')";
        }
        sqlAcademy = sqlAcademy + " and (c.timetableStyle!=5 and c.timetableStyle!=6 and c.schoolCourse.schoolTerm ="
                + termId + ") order by c.courseCode ,c.weekday,c.id desc ";
        List<TimetableAppointment> timetableAppointmentAcademys = timetableAppointmentDAO.executeQuery(sqlAcademy, curr*size, size);

        // 合并教务排课及自主排课内容
        for (TimetableAppointment timetableAppointment1 : timetableAppointmentSelfs) {
            timetableAppointmentAcademys.add(timetableAppointment1);
        }

        Collections.sort(timetableAppointmentAcademys, new Comparator<TimetableAppointment>() {
            public int compare(TimetableAppointment arg0, TimetableAppointment arg1) {

                if (!arg0.getTimetableGroups().isEmpty() && !arg1.getTimetableGroups().isEmpty()) {
                    int batchId1= arg0.getTimetableGroups().iterator().next().getTimetableBatch().getId();
					int batchId2=arg1.getTimetableGroups().iterator().next().getTimetableBatch().getId();
					if(batchId1>batchId2){
						return 1;
					}else if(batchId1<batchId2){
						return -1;
					}else{
						return 0;
					}
                }  else {
                    return 0;
                }

            }
        });
        return timetableAppointmentAcademys;
    }

    /*************************************************************************************
     * @內容：查看计数的所有时间列表安排
     * @作者： 贺子龙
     * @日期：2016-04-09
     *************************************************************************************/
    public int getCountTimetableAppointmentsByQuery(int termId, String courseNumber, int status, int flag) {
        // 创建根据学期来查询课程(二次排课)；
        StringBuffer sql = new StringBuffer(
                "select count(distinct c)  from TimetableAppointment c ,TimetableTeacherRelated d where 1=1  and d.timetableAppointment.id = c.id ");
        // 1已审核，其他未审核
        if(status != -1) {
			sql.append(" and c.status= "+status);
        }
        // 确认排课为1调课为0
        if(flag==1){
             sql.append(" and c.enabled= " + 1);
        }
        /**
         * 判断是否只是老师角色
         **/
        User user = shareService.getUserDetail();
        String judge = ",";
        for (Authority authority : user.getAuthorities()) {
            judge = judge + "," + authority.getId() + ",";
        }

        if (!courseNumber.equals("")) {
            sql.append(" and c.schoolCourseInfo.courseNumber like '%" + courseNumber + "%'");
        }

        // 筛选已确认的课程
//		sql.append(" and c.status = 10");

        // 自主排课
        String sqlSelf = sql.toString();
        // 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
        if (judge.indexOf(",2,") != -1 && judge.indexOf(",7,") == -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1 && judge.indexOf(",5,") == -1) {
            sqlSelf = sqlSelf + " and (c.timetableSelfCourse.user.username like '" + user.getUsername() + "'"
            		+" or d.user.username like '"+ user.getUsername() + "')";
        }
        sqlSelf = sqlSelf + " and (c.timetableStyle!=1 and c.timetableStyle!=2 and c.timetableStyle!=3 and c.timetableStyle!=4 and c.timetableSelfCourse.schoolTerm ="
                + termId + ") ";
        int iSelf = ((Long) timetableAppointmentDAO.createQuerySingleResult(sqlSelf).getSingleResult()).intValue();
        // 教务排课
        String sqlAcademy = sql.toString();
        // 如果权限仅为教师，不为实验中心主任或超级管理员，则增加过滤
        if (judge.indexOf(",2,") != -1 && judge.indexOf(",7,") == -1 && judge.indexOf(",11,") == -1 && judge.indexOf(",4,") == -1 && judge.indexOf(",5,") == -1) {
            sqlAcademy = sqlAcademy + " and (c.schoolCourseDetail.user.username like '" + user.getUsername() + "'"
            +" or d.user.username like '"+ user.getUsername() + "')";
        }
        sqlAcademy = sqlAcademy + " and (c.timetableStyle!=5 and c.timetableStyle!=6 and c.schoolCourse.schoolTerm ="
                + termId + ") ";
        int iAcademy = ((Long) timetableAppointmentDAO.createQuerySingleResult(sqlAcademy).getSingleResult())
                .intValue();

        return iSelf + iAcademy;
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

	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableLabRelated> getReListLabTimetableAppointments(HttpServletRequest request, String acno,
			int term) {
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (acno!=null && !acno.equals("-1")) {
    		//获取选择的实验中心
        	academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }else{
        	academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
        }		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select c from TimetableLabRelated c where 1=1 and  c.timetableAppointment.schoolCourse.schoolTerm.id ="
						+ term );
//						+ " and " + "c.timetableAppointment.schoolCourseInfo.academyNumber like '"
//						+ academyNumber + "' ");
		if (request.getParameter("labroom") != null && !request.getParameter("labroom").equals("-1")) {
			sql.append(" and c.labRoom.id = " + request.getParameter("labroom"));
		}
		// 教师只查自己的课程安排
		if(request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_TEACHER")!=-1) {
			User user = shareService.getUserDetail();
			sql.append(" and c.timetableAppointment.schoolCourse.userByTeacher.username = '"+user.getUsername()+"'");
		}

		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(sql.toString(), 0, -1);
		return timetableLabRelateds;
	}

	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableLabRelated> getSelfListLabTimetableAppointments(HttpServletRequest request, String acno,
			int term) {
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        if (acno!=null && !acno.equals("-1")) {
    		//获取选择的实验中心
        	academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }else{
        	academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
        }
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select c from TimetableLabRelated c where " +
				" c.timetableAppointment.schoolCourse.schoolTerm.id ="+ term );
		StringBuffer sql = new StringBuffer(
				"select c from TimetableLabRelated c where "+
				" c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" +term);
		if (request.getParameter("labroom") != null && !request.getParameter("labroom").equals("-1")) {
			hql.append(" and c.labRoom.id = " + request.getParameter("labroom"));
			sql.append(" and c.labRoom.id = " + request.getParameter("labroom"));
		}
		List<TimetableLabRelated> timetableLabRelated1s = timetableLabRelatedDAO.executeQuery(hql.toString());
		List<TimetableLabRelated> timetableLabRelated2s = timetableLabRelatedDAO.executeQuery(sql.toString());
		List<TimetableLabRelated> timetableLabRelateds = new ArrayList<TimetableLabRelated>();
		if(timetableLabRelated1s!=null){
			timetableLabRelateds.addAll(timetableLabRelated1s);
		}
		if(timetableLabRelated2s!=null){
			timetableLabRelateds.addAll(timetableLabRelated2s);
		}
		return timetableLabRelateds;
	}

	/*************************************************************************************
	 * @內容：发布所选选课组所在的排课内容
	 * @作者： 魏誠
	 * @日期：2014-08-4
	 *************************************************************************************/
	@Transactional
	public void doReleaseTimetableAppointments(String courseCode, int flag) {
		// 判断是否为自主排课；
		String sqlstring = "";
		// flag==0为教务排课，flag=1为自建排课
		if (flag >= 1 && flag <= 4) {
			sqlstring = "select c from TimetableAppointment c where c.schoolCourseDetail.schoolCourse.courseCode like '"
					+ courseCode + "'";
		} else {
			sqlstring = "select c from TimetableAppointment c where c.timetableSelfCourse.courseCode like '"
					+ courseCode + "'";
		}
		StringBuffer sql = new StringBuffer(sqlstring);
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO.executeQuery(sql.toString());
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
			timetableAppointment.setStatus(1);
			timetableAppointmentDAO.store(timetableAppointment);
		}
	}

	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存调整排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public SchoolCourseDetail saveAdjustTimetable(HttpServletRequest request) throws ParseException {
		// 调整排课的实验室选择
		String[] labRooms = request.getParameterValues("labRooms");
		//调整排课的实验室设备选择
		String[] devices = request.getParameterValues("devices");
		//调整排课的实验室设备选择
		String[]  sLabRoomDevice = request.getParameterValues("labRoomDevice_id");
		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");

		// 调整排课的授课教师选择
		String[] items = request.getParameterValues("items");

		String weekDay = request.getParameter("weekday");
		// 调整排课的星期选择
		String[] weeks = request.getParameterValues("weeks");
		int[] intWeeks = new int[weeks.length];
		for (int i = 0; i < weeks.length; i++) {
			intWeeks[i] = Integer.parseInt(weeks[i]);
		}
		// 周次进行排序
		String[] sWeek = this.getTimetableWeekClass(intWeeks);
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int i = 0; i < classes.length; i++) {
			intClasses[i] = Integer.parseInt(classes[i]);
		}

		// 节次进行排序
		String[] sClasses = this.getTimetableWeekClass(intClasses);
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(request
				.getParameter("courseDetailNo"));

		/**
		 *
		*/

		TimetableAppointment timetableAppointment1 = new TimetableAppointment();
		timetableAppointment1.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
		// timetableAppointment1.setTimetableNumber(timetableAppointment.getTimetableNumber());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setWeekday(Integer.parseInt(weekDay));
		timetableAppointment1.setCreatedDate(Calendar.getInstance());
		timetableAppointment1.setUpdatedDate(Calendar.getInstance());
		// timetableAppointment1.setTeacherRelated(timetableAppointment.getTeacherRelated());
		// timetableAppointment1.setTutorRelated(timetableAppointment.getTutorRelated());
		timetableAppointment1.setSchoolCourseDetail(schoolCourseDetail);
		timetableAppointment1.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
		//保存学期
		timetableAppointment1.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setSchoolCourseInfo(schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo());
		// 设置排课方式
		timetableAppointment1.setTimetableStyle(2);
		// 设置排课状态
		timetableAppointment1.setStatus(10);
		// 设置选课组编号
		timetableAppointment1.setCourseCode(schoolCourseDetail.getSchoolCourse().getCourseCode());

		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setCreatedDate(Calendar.getInstance());
		// timetableAppointment1.setWeekday(timetableAppointment.getWeekday());
		timetableAppointment1.setUpdatedDate(Calendar.getInstance());
		if (request.getParameter("preparation") != null) {
			timetableAppointment1.setPreparation(request.getParameter("preparation"));
		} else {
			timetableAppointment1.setPreparation("");
		}
		if (request.getParameter("groups") != null && !request.getParameter("groups").isEmpty()) {
			timetableAppointment1.setGroups(Integer.parseInt(request.getParameter("groups")));
		} else {
			timetableAppointment1.setGroups(-1);
		}
		if (request.getParameter("labhours") != null && !request.getParameter("labhours").isEmpty()) {
			timetableAppointment1.setLabhours(Integer.parseInt(request.getParameter("labhours")));
		} else {
			timetableAppointment1.setLabhours(-1);
		}
		if (request.getParameter("groupCount") != null && !request.getParameter("groupCount").isEmpty()) {
			timetableAppointment1.setGroupCount(Integer.parseInt(request.getParameter("groupCount")));
		} else {
			timetableAppointment1.setGroupCount(-1);
		}
		if (request.getParameter("consumablesCosts") != null) {
			timetableAppointment1.setConsumablesCosts(new BigDecimal((request.getParameter("consumablesCosts"))));
		} else {
			timetableAppointment1.setConsumablesCosts(new BigDecimal(-1));
		}
		// 设置调整排课的内容
		if (sWeek[0].indexOf(("-")) == -1) {
			timetableAppointment1.setTotalWeeks("1");
			timetableAppointment1.setStartWeek(Integer.parseInt(sWeek[0]));
			timetableAppointment1.setEndWeek(Integer.parseInt(sWeek[0]));

		} else {
			timetableAppointment1.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[0].split("-")[1]) - Integer
					.parseInt(sWeek[0].split("-")[0]))));
			timetableAppointment1.setStartWeek(Integer.parseInt(sWeek[0].split("-")[0]));
			timetableAppointment1.setEndWeek(Integer.parseInt(sWeek[0].split("-")[1]));
		}

		if (sClasses[0].indexOf(("-")) == -1) {
			timetableAppointment1.setTotalClasses(Integer.parseInt(sClasses[0]));
			timetableAppointment1.setStartClass(Integer.parseInt(sClasses[0]));
			timetableAppointment1.setEndClass(Integer.parseInt(sClasses[0]));
		} else {
			timetableAppointment1.setTotalClasses((Integer.parseInt(sClasses[0].split("-")[1]) - Integer
					.parseInt(sClasses[0].split("-")[0])));
			timetableAppointment1.setStartClass(Integer.parseInt(sClasses[0].split("-")[0]));
			timetableAppointment1.setEndClass(Integer.parseInt(sClasses[0].split("-")[1]));
		}

		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);

		// * 对排课预约选定的实验分室进行保存

		TimetableLabRelated timetableLabRelated = new TimetableLabRelated();

		int countLabRoomDevice = 0; // 计算所选实验室总共有多少设备
		// 如果matchLabs不为空时
		if (labRooms != null && labRooms.length > 0) {
			for (int i1 = 0; i1 < labRooms.length; i1++) {
				// 将matchLabs添加到matchLabs中
				LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[i1]));
				timetableLabRelated.setLabRoom(labRoom);
				timetableLabRelated.setTimetableAppointment(timetableAppointment1);
				TimetableLabRelated timetableLabRelatedTmp = timetableLabRelatedDAO.store(timetableLabRelated);
				timetableLabRelatedDAO.flush();

				// 实验室设备数量累加
                if (labRoom.getLabRoomDevices()!=null && labRoom.getLabRoomDevices().size()>0) {
                    countLabRoomDevice+=labRoom.getLabRoomDevices().size();
                }

				/*if(sLabRoomDevice.length>0){
					timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
				}*/
			}
		}

		// 设置此次排课的针对对象（1 设备  2 实验室）
		if ((sLabRoomDevice!=null && countLabRoomDevice == sLabRoomDevice.length)
				|| sLabRoomDevice==null) {// i.全选情况--纺织学院   ii.其他学院sLabRoomDevice字段为空
			timetableAppointment1.setDeviceOrLab(2);// 此次排课针对实验室
		}else {
			timetableAppointment1.setDeviceOrLab(1);// 此次排课针对设备
		}
		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);

		// * 对排课预约选定的实验项目进行保存

		TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
		// 如果matchItems不为空时
		if (items != null && items.length > 0) {
			for (int i1 = 0; i1 < items.length; i1++) {
				// 将matchItems添加到matchItems中
				timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(Integer
						.parseInt(items[i1])));
				timetableItemRelated.setTimetableAppointment(timetableAppointment1);
				timetableItemRelatedDAO.store(timetableItemRelated);
			}
		}

		// * 对排课预约选定的指导老师进行保存

		TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
		// 获取选择的实验室列表
		List<User> matchTeachers = new ArrayList<User>();
		// 如果matchLabs不为空时
		if (teachers != null && teachers.length > 0) {
			for (int i1 = 0; i1 < teachers.length; i1++) {
				// 将matchLabs添加到matchLabs中
				matchTeachers.add(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setTimetableAppointment(timetableAppointment1);
				timetableTeacherRelatedDAO.store(timetableTeacherRelated);
			}
		}

		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {
				TimetableAppointmentSameNumber timetableAppointmentSameNumber = new TimetableAppointmentSameNumber();
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedDate(Calendar.getInstance());
				timetableAppointmentSameNumber.setUpdatedDate(Calendar.getInstance());

				// 设置调整排课的内容
				if (sWeek[i].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalWeeks("1");
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i]));

				} else {
					timetableAppointmentSameNumber
							.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[i].split("-")[1]) - Integer
									.parseInt(sWeek[i].split("-")[0]))));
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i].split("-")[0]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i].split("-")[1]));
				}

				if (sClasses[j].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalClasses(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j]));
				} else {
					timetableAppointmentSameNumber
							.setTotalClasses((Integer.parseInt(sClasses[j].split("-")[1]) - Integer
									.parseInt(sClasses[j].split("-")[0])));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j].split("-")[0]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j].split("-")[1]));
				}
				timetableAppointmentSameNumber.setTimetableAppointment(timetableAppointment1);
				timetableAppointmentSameNumberDAO.store(timetableAppointmentSameNumber);
				timetableAppointmentSameNumberDAO.flush();
			}
		}

		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id="+timetableAppointment1.getId(), 0,-1);
		if(timetableAppointment1.getDeviceOrLab().equals(1)){
			for(TimetableLabRelated timetableLabRelatedTmp:timetableLabRelateds){
				timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
			}
		}else {
			mysqlService.createLabLimitByAppointment(timetableAppointment1.getId());
		}
		return schoolCourseDetail;
	}
	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存调出内容（调出类型为2   调出教室内容保存）
	 * @作者： 戴昊宇
	 * @日期：2017-11-22
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public TimetableAppointmentChange  saveAdjustMent(HttpServletRequest request) throws ParseException {
		// 调整排课的实验室选择
		String labRoom = request.getParameter("labroom");
		// 调整排课的授课教师选择
		String teacher = request.getParameter("teachers");
		// 调整排课的授课教师选择
		String itemName = request.getParameter("ItemName");
		// 调整排课的星期选择
		String weekDay = request.getParameter("weekday");
		String cause = request.getParameter("cause");
		String weeks = request.getParameter("weeks");
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int i = 0; i < classes.length; i++) {
			intClasses[i] = Integer.parseInt(classes[i]);
		}
        String tappId = request.getParameter("id");
        TimetableAppointmentChange timetableAppointmentChange = new TimetableAppointmentChange();
        timetableAppointmentChange.setItemName(itemName);
        timetableAppointmentChange.setWeek(Integer.valueOf(weeks));
        timetableAppointmentChange.setWeekday(Integer.valueOf(weekDay));
        timetableAppointmentChange.setStartClass(intClasses[0]);
        timetableAppointmentChange.setEndClass(intClasses[classes.length-1]);
        timetableAppointmentChange.setTeacher(teacher);
        timetableAppointmentChange.setAddress(labRoom);
        timetableAppointmentChange.setCause(cause);
        timetableAppointmentChange.setStatus(1);
        timetableAppointmentChange.setState(3);
        timetableAppointmentChange.setUser(shareService.getUserDetail());
        // 调出课程类型
        timetableAppointmentChange.setFlag(2);
        // 根据id获得timetableAppointment
        TimetableAppointment findTimetableAppointmentById = timetableAppointmentDAO.findTimetableAppointmentById(Integer.parseInt(tappId));
        // 被调出排课状态修改
        findTimetableAppointmentById.setEnabled(false);
        // 1为调出中
        findTimetableAppointmentById.setCalloutType(1);
        //调出时拿到原实训室
        Set<TimetableLabRelated> timetableLabRelateds = findTimetableAppointmentById.getTimetableLabRelateds();
        for (Iterator iterator = timetableLabRelateds.iterator(); iterator.hasNext();) {
			TimetableLabRelated timetableLabRelated = (TimetableLabRelated) iterator.next();
			timetableAppointmentChange.setLabRoom(timetableLabRelated.getLabRoom());
		}
        timetableAppointmentDAO.store(findTimetableAppointmentById);
        timetableAppointmentDAO.flush();
        timetableAppointmentChange.setTimetableAppointment(findTimetableAppointmentById);
        timetableAppointmentChangeDAO.store(timetableAppointmentChange);
        timetableAppointmentChangeDAO.flush();
		return timetableAppointmentChange;
	}
	/*************************************************************************************
	 * @內容：排课管理中，保存修改的排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	@Transactional
	public void saveAdminTimetable(HttpServletRequest request) {
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		// 调整排课的实验室选择

		String[] labRooms = request.getParameterValues("labRooms1");
		// 调整排课的实验项目选择
		// String[] items = request.getParameterValues("items");
		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");
		// 调整排课的授课教师选择
		String[] tutors = request.getParameterValues("tutors");

		// String weekday = request.getParameter("weekday");
		// 调整排课的星期选择
		String[] weeks = request.getParameterValues("weeks");
		int[] intWeeks = new int[weeks.length];

		for (int k = 0; k < weeks.length; k++) {
			intWeeks[k] = Integer.parseInt(weeks[k]);
		}
		// 周次进行排序
		String[] sWeek = this.getTimetableWeekClass(intWeeks);
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int k = 0; k < classes.length; k++) {
			intClasses[k] = Integer.parseInt(classes[k]);
		}

		// 节次进行排序
		String[] sClasses = this.getTimetableWeekClass(intClasses);
		TimetableAppointment requestTimetableAppointment = timetableAppointmentDAO.findTimetableAppointmentById(Integer
				.parseInt(request.getParameter("id")));
		SchoolCourseDetail schoolCourseDetail = requestTimetableAppointment.getSchoolCourseDetail();

		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {
				timetableAppointment.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
				// timetableAppointment.setTimetableNumber(timetableAppointment.getTimetableNumber());
				//保存学期
				timetableAppointment.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
				timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointment.setCreatedDate(Calendar.getInstance());
				timetableAppointment.setUpdatedDate(Calendar.getInstance());
				timetableAppointment.setSchoolCourseDetail(schoolCourseDetail);
				// 设置排课方式为二次排课的分组排课模式
				timetableAppointment.setTimetableStyle(requestTimetableAppointment.getTimetableStyle());
				// 设置排课状态为待发布
				timetableAppointment.setStatus(requestTimetableAppointment.getStatus());
				timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointment.setCreatedDate(Calendar.getInstance());
				timetableAppointment.setSchoolCourseInfo(requestTimetableAppointment.getSchoolCourseInfo());
				// 保存选课组
				timetableAppointment.setCourseCode(requestTimetableAppointment.getCourseCode());
				// 保存课程
				timetableAppointment.setSchoolCourse(requestTimetableAppointment.getSchoolCourse());
				timetableAppointment.setWeekday(requestTimetableAppointment.getWeekday());
				timetableAppointment.setUpdatedDate(Calendar.getInstance());
				if (request.getParameter("preparation") != null) {
					timetableAppointment.setPreparation(request.getParameter("preparation"));
				} else {
					timetableAppointment.setPreparation("");
				}
				if (request.getParameter("groups") != null) {
					timetableAppointment.setGroups(Integer.parseInt(request.getParameter("groups")));
				} else {
					timetableAppointment.setGroups(-1);
				}
				if (request.getParameter("labhours") != null) {
					timetableAppointment.setLabhours(Integer.parseInt(request.getParameter("labhours")));
				} else {
					timetableAppointment.setLabhours(-1);
				}
				if (request.getParameter("groupCount") != null) {
					timetableAppointment.setGroupCount(Integer.parseInt(request.getParameter("groupCount")));
				} else {
					timetableAppointment.setGroupCount(-1);
				}
				if (request.getParameter("consumablesCosts") != null) {
					timetableAppointment
							.setConsumablesCosts(new BigDecimal((request.getParameter("consumablesCosts"))));
				} else {
					timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
				}
				// 设置调整排课的内容
				if (sWeek[i].indexOf(("-")) == -1) {
					timetableAppointment.setTotalWeeks("1");
					timetableAppointment.setStartWeek(Integer.parseInt(sWeek[i]));
					timetableAppointment.setEndWeek(Integer.parseInt(sWeek[i]));

				} else {
					timetableAppointment
							.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[i].split("-")[1]) - Integer
									.parseInt(sWeek[i].split("-")[0]))));
					timetableAppointment.setStartWeek(Integer.parseInt(sWeek[i].split("-")[0]));
					timetableAppointment.setEndWeek(Integer.parseInt(sWeek[i].split("-")[1]));
				}

				if (sClasses[j].indexOf(("-")) == -1) {
					timetableAppointment.setTotalClasses(Integer.parseInt(sClasses[j]));
					timetableAppointment.setStartClass(Integer.parseInt(sClasses[j]));
					timetableAppointment.setEndClass(Integer.parseInt(sClasses[j]));
				} else {
					timetableAppointment.setTotalClasses((Integer.parseInt(sClasses[j].split("-")[1]) - Integer
							.parseInt(sClasses[j].split("-")[0])));
					timetableAppointment.setStartClass(Integer.parseInt(sClasses[j].split("-")[0]));
					timetableAppointment.setEndClass(Integer.parseInt(sClasses[j].split("-")[1]));
				}
				TimetableAppointment timetableAppointmentNew = timetableAppointmentDAO.store(timetableAppointment);
				// timetableAppointmentDAO.flush();
				TimetableLabRelated timetableLabRelated = new TimetableLabRelated();
				// 如果matchLabs不为空时
				if (labRooms != null && labRooms.length > 0) {
					for (int i1 = 0; i1 < labRooms.length; i1++) {
						// 将matchLabs添加到matchLabs中
						timetableLabRelated.setLabRoom(labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[i1])));
						timetableLabRelated.setTimetableAppointment(timetableAppointmentNew);
						timetableLabRelatedDAO.store(timetableLabRelated);
						// timetableLabRelatedDAO.flush();
					}
				}
				/*
				 * 对排课预约选定的指导老师进行保存
				 */
				TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
				// 获取选择的实验室列表
				List<User> matchTeachers = new ArrayList<User>();
				// 如果matchLabs不为空时
				if (teachers.length > 0) {
					for (int i1 = 0; i1 < teachers.length; i1++) {
						// 将matchLabs添加到matchLabs中
						matchTeachers.add(userDAO.findUserByUsername(teachers[i1]));
						timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i1]));
						timetableTeacherRelated.setTimetableAppointment(timetableAppointmentNew);
						timetableTeacherRelatedDAO.store(timetableTeacherRelated);
						timetableTeacherRelatedDAO.flush();
					}
				}

				/*
				 * 对排课预约选定的指导老师进行保存
				 */
				TimetableTutorRelated timetableTutorRelated = new TimetableTutorRelated();
				// 获取选择的实验室列表
				List<User> matchTutors = new ArrayList<User>();
				// 如果matchLabs不为空时
				if (tutors.length > 0) {
					for (int i1 = 0; i1 < tutors.length; i1++) {
						// 将matchLabs添加到matchLabs中
						matchTutors.add(userDAO.findUserByUsername(tutors[i1]));
						timetableTutorRelated.setUser(userDAO.findUserByUsername(tutors[i1]));
						timetableTutorRelated.setTimetableAppointment(timetableAppointmentNew);
						timetableTutorRelatedDAO.store(timetableTutorRelated);
						timetableTutorRelatedDAO.flush();
					}
				}

				/*
				 * 对排课预约选定的实验项目进行保存
				 */
				// TimetableItemRelated timetableItemRelated = new
				// TimetableItemRelated();
				// 获取选择的实验室列表
				// List<OperationItem> matchItems = new
				// ArrayList<OperationItem>();
				// 如果matchLabs不为空时
				/*
				 * if (items.length > 0) { for (int i1 = 0; i1 < items.length;
				 * i1++) { // 将matchLabs添加到matchLabs中
				 * matchItems.add(operationItemDAO
				 * .findOperationItemById(Integer .parseInt(items[i1])));
				 * timetableItemRelated.setOperationItem(operationItemDAO
				 * .findOperationItemById(Integer .parseInt(items[i1])));
				 * timetableItemRelated
				 * .setTimetableAppointment(timetableAppointmentNew);
				 * timetableItemRelatedDAO.store(timetableItemRelated);
				 * timetableItemRelatedDAO.flush(); } }
				 */
			}
		}

	}

	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存二次排课的不分组排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	@Transactional
	public TimetableAppointment saveNoGroupReTimetable(HttpServletRequest request) throws ParseException {

		TimetableAppointment timetableAppointment = new TimetableAppointment();
		// 调整排课的实验室选择
		String[] labRooms = request.getParameterValues("labRooms");
		//调整排课的实验室设备选择
		String[]  sLabRoomDevice = request.getParameterValues("labRoomDevice_id");
		// 调整排课的实验项目选择
		String[] items = request.getParameterValues("items");
		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");
		String weekday = request.getParameter("weekday");
		// 调整排课的星期选择
		String[] weeks = request.getParameterValues("weeks");
		int[] intWeeks = new int[weeks.length];

		for (int k = 0; k < weeks.length; k++) {
			intWeeks[k] = Integer.parseInt(weeks[k]);
		}
		// 周次进行排序
		String[] sWeek = this.getTimetableWeekClass(intWeeks);
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int k = 0; k < classes.length; k++) {
			intClasses[k] = Integer.parseInt(classes[k]);
		}

		// 节次进行排序
		String[] sClasses = this.getTimetableWeekClass(intClasses);
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(request
				.getParameter("courseDetailNo"));
		/**
         *
		*/

		timetableAppointment.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
		//保存学期
		timetableAppointment.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
		// timetableAppointment.setTimetableNumber(timetableAppointment.getTimetableNumber());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedDate(Calendar.getInstance());
		timetableAppointment.setUpdatedDate(Calendar.getInstance());
		timetableAppointment.setSchoolCourseDetail(schoolCourseDetail);
		// 设置排课方式为二次排课的分组排课模式
		timetableAppointment.setTimetableStyle(3);
		// 设置排课状态为待发布
		timetableAppointment.setStatus(10);
		timetableAppointment.setSchoolCourseInfo(schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedDate(Calendar.getInstance());
		// 保存选课组
		timetableAppointment.setCourseCode(request.getParameter("courseCode").toString());
		// 保存课程
		timetableAppointment.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
		timetableAppointment.setSchoolCourseInfo(schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo());
		timetableAppointment.setWeekday(Integer.parseInt(weekday));
		timetableAppointment.setUpdatedDate(Calendar.getInstance());
		if (request.getParameter("preparation") != null) {
			timetableAppointment.setPreparation(request.getParameter("preparation"));
		} else {
			timetableAppointment.setPreparation("");
		}
		if (request.getParameter("preparation") != null && !request.getParameter("groups").isEmpty()) {
			timetableAppointment.setGroups(Integer.parseInt(request.getParameter("groups")));
		} else {
			timetableAppointment.setGroups(-1);
		}
		if (request.getParameter("preparation") != null && !request.getParameter("labhours").isEmpty()) {
			timetableAppointment.setLabhours(Integer.parseInt(request.getParameter("labhours")));
		} else {
			timetableAppointment.setLabhours(-1);
		}
		if (request.getParameter("preparation") != null && !request.getParameter("groupCount").isEmpty()) {
			timetableAppointment.setGroupCount(Integer.parseInt(request.getParameter("groupCount")));
		} else {
			timetableAppointment.setGroupCount(-1);
		}
		if (request.getParameter("consumablesCosts") != null) {
			timetableAppointment.setConsumablesCosts(new BigDecimal((request.getParameter("consumablesCosts"))));
		} else {
			timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
		}
		// 设置调整排课的内容
		if (sWeek[0].indexOf(("-")) == -1) {
			timetableAppointment.setTotalWeeks("1");
			timetableAppointment.setStartWeek(Integer.parseInt(sWeek[0]));
			timetableAppointment.setEndWeek(Integer.parseInt(sWeek[0]));

		} else {
			timetableAppointment.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[0].split("-")[1]) - Integer
					.parseInt(sWeek[0].split("-")[0]))));
			timetableAppointment.setStartWeek(Integer.parseInt(sWeek[0].split("-")[0]));
			timetableAppointment.setEndWeek(Integer.parseInt(sWeek[0].split("-")[1]));
		}

		if (sClasses[0].indexOf(("-")) == -1) {
			timetableAppointment.setTotalClasses(Integer.parseInt(sClasses[0]));
			timetableAppointment.setStartClass(Integer.parseInt(sClasses[0]));
			timetableAppointment.setEndClass(Integer.parseInt(sClasses[0]));
		} else {
			timetableAppointment.setTotalClasses((Integer.parseInt(sClasses[0].split("-")[1]) - Integer
					.parseInt(sClasses[0].split("-")[0])));
			timetableAppointment.setStartClass(Integer.parseInt(sClasses[0].split("-")[0]));
			timetableAppointment.setEndClass(Integer.parseInt(sClasses[0].split("-")[1]));
		}
		TimetableAppointment timetableAppointmentNew = timetableAppointmentDAO.store(timetableAppointment);
		timetableAppointmentDAO.flush();
		TimetableLabRelated timetableLabRelated = new TimetableLabRelated();

		int countLabRoomDevice = 0; // 计算所选实验室总共有多少设备
		// 如果matchLabs不为空时
		if (labRooms != null && labRooms.length > 0) {
			for (int i1 = 0; i1 < labRooms.length; i1++) {
				LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[i1]));
				// 将matchLabs添加到matchLabs中
				timetableLabRelated.setLabRoom(labRoom);
				timetableLabRelated.setTimetableAppointment(timetableAppointmentNew);
				timetableLabRelatedDAO.store(timetableLabRelated);
				timetableLabRelatedDAO.flush();

				// 实验室设备数量累加
                if (labRoom.getLabRoomDevices()!=null && labRoom.getLabRoomDevices().size()>0) {
                    countLabRoomDevice+=labRoom.getLabRoomDevices().size();
                }

			}
		}
		// 设置此次排课的针对对象（1 设备  2 实验室）
		if ((sLabRoomDevice!=null && countLabRoomDevice == sLabRoomDevice.length)
				|| sLabRoomDevice==null) {// i.全选情况--纺织学院   ii.其他学院sLabRoomDevice字段为空
			timetableAppointmentNew.setDeviceOrLab(2);// 此次排课针对实验室
		}else {
			timetableAppointmentNew.setDeviceOrLab(1);// 此次排课针对设备
		}
		timetableAppointmentNew = timetableAppointmentDAO.store(timetableAppointmentNew);

		/*
		 * 对排课预约选定的指导老师进行保存
		 */
		TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
		// 获取选择的实验室列表
		List<User> matchTeachers = new ArrayList<User>();
		// 如果matchLabs不为空时
		if (teachers.length > 0) {
			for (int i1 = 0; i1 < teachers.length; i1++) {
				// 将matchLabs添加到matchLabs中
				matchTeachers.add(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setTimetableAppointment(timetableAppointmentNew);
				timetableTeacherRelatedDAO.store(timetableTeacherRelated);
				timetableTeacherRelatedDAO.flush();
			}
		}

		/*
		 * 对排课预约选定的实验项目进行保存
		 */
		TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
		// 获取选择的实验室列表
		List<OperationItem> matchItems = new ArrayList<OperationItem>();
		// 如果matchLabs不为空时
		if (items.length > 0) {
			for (int i1 = 0; i1 < items.length; i1++) {
				// 将matchLabs添加到matchLabs中
				matchItems.add(operationItemDAO.findOperationItemById(Integer.parseInt(items[i1])));
				timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(Integer
						.parseInt(items[i1])));
				timetableItemRelated.setTimetableAppointment(timetableAppointmentNew);
				timetableItemRelatedDAO.store(timetableItemRelated);
				timetableItemRelatedDAO.flush();
			}
		}

		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {

				TimetableAppointmentSameNumber timetableAppointmentSameNumber = new TimetableAppointmentSameNumber();
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedDate(Calendar.getInstance());
				timetableAppointmentSameNumber.setUpdatedDate(Calendar.getInstance());

				// 设置调整排课的内容
				if (sWeek[i].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalWeeks("1");
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i]));

				} else {
					timetableAppointmentSameNumber
							.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[i].split("-")[1]) - Integer
									.parseInt(sWeek[i].split("-")[0]))));
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i].split("-")[0]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i].split("-")[1]));
				}

				if (sClasses[j].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalClasses(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j]));
				} else {
					timetableAppointmentSameNumber
							.setTotalClasses((Integer.parseInt(sClasses[j].split("-")[1]) - Integer
									.parseInt(sClasses[j].split("-")[0])));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j].split("-")[0]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j].split("-")[1]));
				}
				timetableAppointmentSameNumber.setTimetableAppointment(timetableAppointmentNew);
				timetableAppointmentSameNumberDAO.store(timetableAppointmentSameNumber);
				timetableAppointmentSameNumberDAO.flush();

			}

		}
		timetableLabRelatedDAO.flush();
		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id="+timetableAppointmentNew.getId(), 0,-1);
		if(timetableAppointmentNew.getDeviceOrLab().equals(1)){
			for(TimetableLabRelated timetableLabRelatedTmp:timetableLabRelateds){
				timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());

			}
		}else {
			mysqlService.createLabLimitByAppointment(timetableAppointmentNew.getId());
		}
		return timetableAppointment;
	}

	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存二次排课的分组排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	@Transactional
	public TimetableAppointment saveGroupReTimetable(HttpServletRequest request) throws ParseException {
		TimetableAppointment timetableAppointment = new TimetableAppointment();
		TimetableAppointment timetableAppointmentReturn = new TimetableAppointment();
		// 调整排课的实验室选择
		String[] labRooms = request.getParameterValues("labRooms");
		//调整排课的实验室设备选择
		String[]  sLabRoomDevice = request.getParameterValues("labRoomDevice_id");
		// 调整排课的实验项目选择
		String[] items = request.getParameterValues("items");
		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");
		String weekday = request.getParameter("weekday");
		// 调整排课的星期选择
		String[] weeks = request.getParameterValues("weeks");
		int[] intWeeks = new int[weeks.length];

		for (int i = 0; i < weeks.length; i++) {
			intWeeks[i] = Integer.parseInt(weeks[i]);
		}
		// 周次进行排序
		String[] sWeek = this.getTimetableWeekClass(intWeeks);
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int i = 0; i < classes.length; i++) {
			intClasses[i] = Integer.parseInt(classes[i]);
		}

		// 节次进行排序
		String[] sClasses = this.getTimetableWeekClass(intClasses);
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(request
				.getParameter("courseDetailNo"));

		/**
		 *
		*/
		timetableAppointment.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
		// timetableAppointment.setTimetableNumber(timetableAppointment.getTimetableNumber());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedDate(Calendar.getInstance());
		timetableAppointment.setUpdatedDate(Calendar.getInstance());
		// 设置排课方式为二次排课的分组排课模式
		timetableAppointment.setTimetableStyle(4);
		// 设置排课状态为待发布
		timetableAppointment.setStatus(10);
		timetableAppointment.setSchoolCourseInfo(schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo());
		// 设置排课的选课组编号
		timetableAppointment.setCourseCode(schoolCourseDetail.getSchoolCourse().getCourseCode());
		timetableAppointment.setSchoolCourseDetail(schoolCourseDetail);
		timetableAppointment.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment.setCreatedDate(Calendar.getInstance());
		if (request.getParameter("preparation") != null) {
			timetableAppointment.setPreparation(request.getParameter("preparation"));
		} else {
			timetableAppointment.setPreparation("");
		}
		if (request.getParameter("groups") != null) {
			timetableAppointment.setGroups(Integer.parseInt(request.getParameter("groups")));
		} else {
			timetableAppointment.setGroups(-1);
		}
		if (request.getParameter("labhours") != null) {
			timetableAppointment.setLabhours(Integer.parseInt(request.getParameter("labhours")));
		} else {
			timetableAppointment.setLabhours(-1);
		}
		if (request.getParameter("groupCount") != null) {
			timetableAppointment.setGroupCount(Integer.parseInt(request.getParameter("groupCount")));
		} else {
			timetableAppointment.setGroupCount(-1);
		}
		if (request.getParameter("consumablesCosts") != null) {
			timetableAppointment.setConsumablesCosts(new BigDecimal((request.getParameter("consumablesCosts"))));
		} else {
			timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
		}
		timetableAppointment.setWeekday(Integer.parseInt(weekday));
		timetableAppointment.setUpdatedDate(Calendar.getInstance());
		// 设置调整排课的内容
		if (sWeek[0].indexOf(("-")) == -1) {
			timetableAppointment.setTotalWeeks("1");
			timetableAppointment.setStartWeek(Integer.parseInt(sWeek[0]));
			timetableAppointment.setEndWeek(Integer.parseInt(sWeek[0]));

		} else {
			timetableAppointment.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[0].split("-")[1]) - Integer
					.parseInt(sWeek[0].split("-")[0]))));
			timetableAppointment.setStartWeek(Integer.parseInt(sWeek[0].split("-")[0]));
			timetableAppointment.setEndWeek(Integer.parseInt(sWeek[0].split("-")[1]));
		}

		if (sClasses[0].indexOf(("-")) == -1) {
			timetableAppointment.setTotalClasses(Integer.parseInt(sClasses[0]));
			timetableAppointment.setStartClass(Integer.parseInt(sClasses[0]));
			timetableAppointment.setEndClass(Integer.parseInt(sClasses[0]));
		} else {
			timetableAppointment.setTotalClasses((Integer.parseInt(sClasses[0].split("-")[1]) - Integer
					.parseInt(sClasses[0].split("-")[0])));
			timetableAppointment.setStartClass(Integer.parseInt(sClasses[0].split("-")[0]));
			timetableAppointment.setEndClass(Integer.parseInt(sClasses[0].split("-")[1]));
		}

		// 保存排课分组信息
		Set<TimetableGroup> timetableGroupList = new HashSet<TimetableGroup>();
		// timetableGroupDAO.executeQuery("SELECT t FROM TimetableGroup t join t.timetableAppointments s where t.id = ",0,-1);
		timetableGroupList.addAll(timetableGroupDAO.executeQuery(
				"SELECT t FROM TimetableGroup t join t.timetableAppointments s where t.id = "
						+ Integer.parseInt(request.getParameter("group")), 0, -1));
		timetableGroupList
				.add(timetableGroupDAO.findTimetableGroupById(Integer.parseInt(request.getParameter("group"))));
		timetableAppointment.setTimetableGroups(timetableGroupList);

		// 保存排课记录
		TimetableAppointment timetableAppointmentNew = timetableAppointmentDAO.store(timetableAppointment);
		timetableAppointmentDAO.flush();
		timetableAppointmentReturn = timetableAppointmentNew;
		TimetableLabRelated timetableLabRelated = new TimetableLabRelated();

		int countLabRoomDevice = 0; // 计算所选实验室总共有多少设备
		// 如果matchLabs不为空时
		if (labRooms != null && labRooms.length > 0) {
			for (int i1 = 0; i1 < labRooms.length; i1++) {
				LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[i1]));
				// 将matchLabs添加到matchLabs中
				timetableLabRelated.setLabRoom(labRoom);
				timetableLabRelated.setTimetableAppointment(timetableAppointmentNew);
				timetableLabRelatedDAO.store(timetableLabRelated);
				timetableLabRelatedDAO.flush();

				// 实验室设备数量累加
                if (labRoom.getLabRoomDevices()!=null && labRoom.getLabRoomDevices().size()>0) {
                    countLabRoomDevice+=labRoom.getLabRoomDevices().size();
                }

			}
		}

		// 设置此次排课的针对对象（1 设备  2 实验室）
		if ((sLabRoomDevice!=null && countLabRoomDevice == sLabRoomDevice.length)
				|| sLabRoomDevice==null) {// i.全选情况--纺织学院   ii.其他学院sLabRoomDevice字段为空
			timetableAppointmentNew.setDeviceOrLab(2);// 此次排课针对实验室
		}else {
			timetableAppointmentNew.setDeviceOrLab(1);// 此次排课针对设备
		}
		timetableAppointmentNew = timetableAppointmentDAO.store(timetableAppointmentNew);
		/*
		 * 对排课预约选定的指导老师进行保存
		 */
		TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
		// 获取选择的实验室列表
		List<User> matchTeachers = new ArrayList<User>();
		// 如果matchLabs不为空时
		if (teachers.length > 0) {
			for (int i1 = 0; i1 < teachers.length; i1++) {
				// 将matchLabs添加到matchLabs中
				matchTeachers.add(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setTimetableAppointment(timetableAppointmentNew);
				timetableTeacherRelatedDAO.store(timetableTeacherRelated);
				timetableTeacherRelatedDAO.flush();
			}
		}

		/*
		 * 对排课预约选定的实验项目进行保存
		 */
		TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
		// 获取选择的实验室列表
		List<OperationItem> matchItems = new ArrayList<OperationItem>();
		// 如果matchLabs不为空时
		if (items.length > 0) {
			for (int i1 = 0; i1 < items.length; i1++) {
				// 如果item为空则跳出循环
				if (items[i1] == null || items[i1].equals("")) {
					continue;
				}
				// 去重
				if (i1 > 0 && items[i1].equals(items[i1 - 1])) {
					continue;
				}
				// 将matchLabs添加到matchLabs中
				matchItems.add(operationItemDAO.findOperationItemById(Integer.parseInt(items[i1])));
				timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(Integer
						.parseInt(items[i1])));
				timetableItemRelated.setTimetableAppointment(timetableAppointmentNew);
				timetableItemRelatedDAO.store(timetableItemRelated);
				timetableItemRelatedDAO.flush();
			}
		}

		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {
				TimetableAppointmentSameNumber timetableAppointmentSameNumber = new TimetableAppointmentSameNumber();
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedDate(Calendar.getInstance());
				timetableAppointmentSameNumber.setUpdatedDate(Calendar.getInstance());

				// 设置调整排课的内容
				if (sWeek[i].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalWeeks("1");
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i]));

				} else {
					timetableAppointmentSameNumber
							.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[i].split("-")[1]) - Integer
									.parseInt(sWeek[i].split("-")[0]))));
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i].split("-")[0]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i].split("-")[1]));
				}

				if (sClasses[j].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalClasses(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j]));
				} else {
					timetableAppointmentSameNumber
							.setTotalClasses((Integer.parseInt(sClasses[j].split("-")[1]) - Integer
									.parseInt(sClasses[j].split("-")[0])));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j].split("-")[0]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j].split("-")[1]));
				}
				timetableAppointmentSameNumber.setTimetableAppointment(timetableAppointmentNew);
				timetableAppointmentSameNumberDAO.store(timetableAppointmentSameNumber);
				timetableAppointmentSameNumberDAO.flush();
			}
		}
		timetableLabRelatedDAO.flush();
		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id="+timetableAppointmentNew.getId(), 0,-1);
		if(timetableAppointmentNew.getDeviceOrLab().equals(1)){
			for(TimetableLabRelated timetableLabRelatedTmp:timetableLabRelateds){
				timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
			}
		}else {
			mysqlService.createLabLimitByAppointment(timetableAppointmentNew.getId());
		}
		return timetableAppointmentReturn;
	}

	/*************************************************************************************
	 * @內容：排课管理中，删除排课信息的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	@Transactional
	public void deleteAppointment(int id) {
		// 删除相关的学生选课信息
		List<TimetableGroupStudents> timetableGroupStudentses = null;
		if (timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().size() > 0) {
			timetableGroupStudentses = timetableGroupStudentsDAO
					.executeQuery("select c from TimetableGroupStudents c where c.timetableGroup.timetableBatch.id = "
							+ timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().iterator()
									.next().getTimetableBatch().getId());

		}
		if (timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().size() > 0) {
			for (TimetableGroupStudents timetableGroupStudents : timetableGroupStudentses) {
				timetableGroupStudentsDAO.remove(timetableGroupStudents);
			}
		}
		TimetableBatch timetableBatch = null;
		// 设置排课批次标记为未调整完成
		if (timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().size() > 0) {
			timetableBatch = timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().iterator()
					.next().getTimetableBatch();
			timetableBatch.setFlag(null);
			timetableBatchDAO.store(timetableBatch);
			// 设置排课批次标记为待发布
			for (TimetableGroup timetableGroup : timetableBatch.getTimetableGroups()) {
				for (TimetableAppointment timetableAppointment : timetableGroup.getTimetableAppointments()) {
					// 设置为待发布
					timetableAppointment.setStatus(10);
					// 保存排课分组信息
					// Set<TimetableGroup> timetableGroupList=new
					// HashSet<TimetableGroup>();
					// timetableGroupDAO.executeQuery("SELECT t FROM TimetableGroup t join t.timetableAppointments s where t.id = ",0,-1);
					// timetableGroupList.addAll(timetableGroupDAO.executeQuery("SELECT t FROM TimetableAppointment t join t.timetableGroups s where t.id = "
					// +timetableAppointment.getId() ,0,-1));

					// timetableAppointment.setTimetableGroups(timetableGroupList);
					timetableAppointmentDAO.store(timetableAppointment);
				}
			}
		}

		// 删除相关实验分室
		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO
				.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id =" + id);
		for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
			//根据实验室相关设备遍历，相关实验分室下的实验室设备资源
			for(TimetableLabRelatedDevice timetableLabRelatedDevice:timetableLabRelated.getTimetableLabRelatedDevices()){
				List<LabRoomDeviceReservation> labRoomDeviceReservations = labRoomDeviceReservationDAO.executeQuery("select c from LabRoomDeviceReservation c where c.timetableLabDevice ="+timetableLabRelatedDevice.getId(), 0,-1);
			    //开始删除
				for(LabRoomDeviceReservation labRoomDeviceReservation:labRoomDeviceReservations){
					labRoomDeviceReservationDAO.remove(labRoomDeviceReservation);
				}
			}
			timetableLabRelatedDAO.remove(timetableLabRelated);
		}
		// 删除相关授课教师
		List<TimetableTeacherRelated> timetableTeacherRelateds = timetableTeacherRelatedDAO
				.executeQuery("select c from TimetableTeacherRelated c where c.timetableAppointment.id =" + id);
		for (TimetableTeacherRelated timetableTeacherRelated : timetableTeacherRelateds) {
			timetableTeacherRelatedDAO.remove(timetableTeacherRelated);
		}
		// 删除相关指导教师
		List<TimetableTutorRelated> timetableTutorRelateds = timetableTutorRelatedDAO
				.executeQuery("select c from TimetableTutorRelated c where c.timetableAppointment.id =" + id);
		for (TimetableTutorRelated timetableTutorRelated : timetableTutorRelateds) {
			timetableTutorRelatedDAO.remove(timetableTutorRelated);
		}

		// 删除相关指导教师
		List<TimetableItemRelated> timetableItemRelateds = timetableItemRelatedDAO
				.executeQuery("select c from TimetableItemRelated c where c.timetableAppointment.id =" + id);
		for (TimetableItemRelated timetableItemRelated : timetableItemRelateds) {
			timetableItemRelatedDAO.remove(timetableItemRelated);
		}

		// 移除分组选课数据

		timetableAppointmentDAO.remove(timetableAppointmentDAO.findTimetableAppointmentById(id));

	}
	/*************************************************************************************
	 * @內容：排课管理中，确认
	 * @作者：孙虎
	 * @日期：2017-11-20
	 *************************************************************************************/
	@Transactional
	public void confirmAppointment(int id, int flag, String remark) {
		User user = shareService.getUser();
				if(flag == 1){
					TimetableAppointment appointment = timetableAppointmentDAO.findTimetableAppointmentById(id);
					appointment.setConfirmUser(shareService.getUserDetail().getCname());
					appointment.setConfirmDate(Calendar.getInstance());
					boolean b = shareService.checkAuthority(user.getUsername(), "TEACHER");
					if(b)
					{
						appointment.setConfirmType(2);
					}
					boolean a = shareService.checkAuthority(user.getUsername(), "LABMANAGER");
					if(a){
						appointment.setConfirmType(3);
					}
					appointment.setConfirmRemark(remark);
					timetableAppointmentDAO.store(appointment);
					timetableAppointmentDAO.flush();
				}else if(flag ==2){
					TimetableAppointment appointment = timetableAppointmentDAO.findTimetableAppointmentById(id);
					appointment.setConfirmUser(shareService.getUserDetail().getCname());
					appointment.setConfirmDate(Calendar.getInstance());
					appointment.setConfirmRemark(remark);
					timetableAppointmentDAO.store(appointment);
					timetableAppointmentDAO.flush();
				}
	}
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String getAdminCourseCodeList(int term, String acno) {
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		if(acno!=null && !acno.equals("-1")) {
    		//获取选择的实验中心
        	academyNumber = shareService.findSchoolAcademyByPrimaryKey(acno).getAcademyNumber();
        }else{
        	academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
        }
		// 返回可用的星期信息
		String sql = "select c from TimetableAppointment c";
		List<TimetableAppointment> list = timetableAppointmentDAO.executeQuery(sql, 0, -1);
		String jsonWeek = "[";

		for (TimetableAppointment timetableAppointment : list) {
			// 教务排课
			if (timetableAppointment.getTimetableStyle() == 1 || timetableAppointment.getTimetableStyle() == 2
					|| timetableAppointment.getTimetableStyle() == 3 || timetableAppointment.getTimetableStyle() == 4) {
				if (timetableAppointment.getSchoolCourse().getSchoolTerm().getId() == term
						&& timetableAppointment.getSchoolCourse().getSchoolAcademy().getAcademyNumber()
								.equals(academyNumber)) {
					jsonWeek = jsonWeek + "{\"courseNumber\":\""
							+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "\",\"value\":\""
							+ timetableAppointment.getSchoolCourse().getSchoolCourseInfo().getCourseNumber()
							+ timetableAppointment.getSchoolCourse().getUserByTeacher().getCname()
							+ timetableAppointment.getSchoolCourse().getCourseName() + "；选课组编号："
							+ timetableAppointment.getSchoolCourse().getCourseCode() + "\"},";
				}
				// 自主排课
			} else if (timetableAppointment.getTimetableStyle() == 5 || timetableAppointment.getTimetableStyle() == 6) {
				if (timetableAppointment.getTimetableSelfCourse() != null
						&& timetableAppointment.getTimetableSelfCourse().getSchoolTerm().getId() == term
						&& timetableAppointment.getTimetableSelfCourse().getSchoolAcademy().getAcademyNumber()
								.equals(academyNumber)) {
					jsonWeek = jsonWeek + "{\"courseNumber\":\""
							+ timetableAppointment.getSchoolCourseInfo().getCourseNumber() + "\",\"value\":\""
							+ timetableAppointment.getTimetableSelfCourse().getSchoolCourseInfo().getCourseNumber()
							+ timetableAppointment.getTimetableSelfCourse().getUser().getCname()
							+ timetableAppointment.getTimetableSelfCourse().getSchoolCourseInfo().getCourseName()
							+ "；选课组编号：" + timetableAppointment.getTimetableSelfCourse().getCourseCode() + "\"},";
				}
			}
		}

		jsonWeek.substring(0, jsonWeek.length() - 1);
		jsonWeek = jsonWeek + "]";
		return jsonWeek;

	}

	/*************************************************************************************
	 * @throws ParseException
	 * @內容：根据学期和中心获取月报报表
	 * @作者： 贺子龙
	 * @日期：2015-12-27
	 *************************************************************************************/
	public List<MonthReport> getMonthReports(int term,int year, int month,String acno) throws ParseException{
		// 根据课程及id获取课程排课列表
		List<TimetableAppointment> timetableAppointments=
				getTimetableAppointmentsByMonth(term,1,year, month,acno);//里面的1  是status   1--已发布  -1--测试用
		//根据年月生成一个时间段
		Calendar[] timePeriod=shareService.getTimePeriod(year, month);
		List<MonthReport> monthReports=new ArrayList<MonthReport>();
		for (TimetableAppointment ta : timetableAppointments) {
			List<Calendar> dates=schoolWeekService.getTimetableAppointmentDate(ta);

			Set<TimetableLabRelated> labRooms=ta.getTimetableLabRelateds();//获取排课对应的实验室
			List<TimetableLabRelated> labRoomList=new ArrayList<TimetableLabRelated>(labRooms);
			for (Calendar date : dates) {
				if (date.before(timePeriod[1])&&date.after(timePeriod[0])) {//上课日期落在查询的月份中

					for (TimetableLabRelated timetableLab : labRoomList) {//两个实验室、一个老师，则显示两条记录
						MonthReport monthReport=creatMonthReport(acno);
						String laboratoryName="";//实验室名称
						String place="";//实验室地点
						if (timetableLab!=null && timetableLab.getLabRoom()!=null
								&&timetableLab.getLabRoom().getLabRoomName()!=null&& !timetableLab.getLabRoom().getLabRoomName().equals("")) {
							laboratoryName=timetableLab.getLabRoom().getLabRoomName();
						}
						monthReport.setLaboratoryName(laboratoryName);//实验室名称
						if (timetableLab!=null && timetableLab.getLabRoom()!=null
								&&timetableLab.getLabRoom().getLabRoomAddress()!=null&&!timetableLab.getLabRoom().getLabRoomAddress().equals("")) {
							place=timetableLab.getLabRoom().getLabRoomAddress();
						}
						monthReport.setPlace(place);//实验室地点
						monthReport.setDate(date);//上课日期
						String courseName="";//课程名称
						if (ta.getSchoolCourseInfo()!=null&&ta.getSchoolCourseInfo().getCourseName()!=null&&!ta.getSchoolCourseInfo().getCourseName().equals("")) {
							courseName=ta.getSchoolCourseInfo().getCourseName();
						}
						monthReport.setCourseName(courseName);

						String experimentName="";//项目卡名称
						String experimentType="";//项目卡类别
						String assessment="";//项目卡考核方式
						Set<TimetableItemRelated> items=ta.getTimetableItemRelateds();
						if (items.size()!=0) {
							int count=0;
							for (TimetableItemRelated item : items) {
								count++;
								if (item.getOperationItem()!=null
										&&item.getOperationItem().getLabRoom().getId()==timetableLab.getId()) {//属于当前实验室的项目卡
									experimentName+=item.getOperationItem().getLpName()+",";
								}
								if (count==1) {//节省时间
									if (item.getOperationItem()!=null&&item.getOperationItem().getCDictionaryByLpCategoryMain()!=null
											&&!item.getOperationItem().getCDictionaryByLpCategoryMain().getCName().equals("")) {
										experimentType=item.getOperationItem().getCDictionaryByLpCategoryMain().getCName();
									}
									if (item.getOperationItem()!=null&&!item.getOperationItem().getLpAssessmentMethods().equals("")) {
										assessment=item.getOperationItem().getLpAssessmentMethods();
									}
								}
							}
						}
						if (experimentName.length()>1) {
							experimentName=experimentName.substring(0, experimentName.length()-1);
						}
						monthReport.setExperimentName(experimentName);
						monthReport.setExperimentType(experimentType);
						monthReport.setAssessment(assessment);
						//学生上课的人时数  1--机房  2--其他  3--本科  4--专科
						String[] classNumberString=getTimetableAppointmentType(ta);
						int[] classNumber=new int[classNumberString.length-1];
						for (int i = 0; i < classNumberString.length-1; i++) {//最后一位是班级名称，不用转化
							classNumber[i]=Integer.parseInt(classNumberString[i]);
						}
						int juniorNumber=0;
						int juniorClass=0;
						int undergraduateNumber=0;
						int undergraduateClass=0;
						int otherNumber=0;
						int otherClass=0;
						int roomNumber=0;
						int roomClass=0;

						switch (classNumber[0]) {
						case 1:
						{
							roomNumber=classNumber[1];
							roomClass=classNumber[2];
						}
						break;
						case 2:
						{
							otherNumber=classNumber[1];
							otherClass=classNumber[2];
						}
						break;
						case 4:
						{
							juniorNumber=classNumber[1];
							juniorClass=classNumber[2];
						}
						break;

						default://默认为本科
						{
							undergraduateNumber=classNumber[1];
							undergraduateClass=classNumber[2];
						}
						break;
						}
						monthReport.setJuniorNumber(juniorNumber);
						monthReport.setJuniorClass(juniorClass);
						monthReport.setJuniorTime(juniorClass*juniorNumber);
						monthReport.setUndergraduateNumber(undergraduateNumber);
						monthReport.setUndergraduateClass(undergraduateClass);
						monthReport.setUndergraduateTime(undergraduateClass*undergraduateNumber);
						monthReport.setOtherNumber(otherNumber);
						monthReport.setOtherClass(otherClass);
						monthReport.setOtherTime(otherClass*otherNumber);
						monthReport.setRoomNumber(roomNumber);
						monthReport.setRoomClass(roomClass);
						monthReport.setRoomTime(roomClass*roomNumber);
						String guidingTeacher="";//指导教师
						if (ta.getSchoolCourse()!=null&&ta.getSchoolCourse().getUserByTeacher()!=null
								&&!ta.getSchoolCourse().getUserByTeacher().getCname().equals("")) {
							guidingTeacher=ta.getSchoolCourse().getUserByTeacher().getCname()+"["+ta.getSchoolCourse().getUserByTeacher().getUsername()+"]";
						}
						monthReport.setGuidingTeacher(guidingTeacher);
						//上课班级
						String className="";
						if (!classNumberString[3].equals("")) {
							className=classNumberString[3];
						}
						monthReport.setClassName(className);
						String note="";//备注
						monthReport.setNote(note);
						monthReports.add(monthReport);
					}
				}
			}


		}
		return monthReports;
	}

	/*************************************************************************************
	 * @throws ParseException
	 * @內容：查看所有的预约的列表安排--月报报表
	 * @作者：贺子龙
	 * @日期：2015-12-29
	 *************************************************************************************/
	public List<TimetableAppointment> getTimetableAppointmentsByMonth(int termId, int status, int year, int month, String acno)
			throws ParseException {
		String academyNumber="";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if (academy!=null && academy.getAcademyNumber()!=null) {
    		//获取选择的实验中心
        	academyNumber = academy.getAcademyNumber();
        }else{
        	if(shareService.getUserDetail().getSchoolAcademy()!=null&&shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()!=null) {
				academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
			}
        }
		// 创建根据学期来查询课程；
		String sql = "select c from TimetableAppointment  c ,TimetableLabRelated e left join c.timetableAppointmentSameNumbers tas where 1=1" +
							" and e.timetableAppointment.id=c.id";

		if (status != -1) {
			sql+=" and c.status = " + status;
		}
		if (year!=0&&month!=0) {//根据年月确定学期
			termId=shareService.getSchoolTermByMonth(year, month).getId();
		}
		int[] weekId=shareService.getSchoolWeekByMonth(year, month);
		if (weekId[0]!=0&&weekId[1]!=0) {//限制周次
			sql+= " and ((tas.startWeek<="+weekId[1]+" and tas.endWeek>="+weekId[1]+")";
			sql+= " or (tas.startWeek<="+weekId[0]+" and tas.endWeek>="+weekId[0]+"))";
		}
		//如果排课中的实验室在该学院下，则显示在排课管理中。
		sql+=" and e.labRoom.labCenter.schoolAcademy.academyNumber like '%" + academyNumber + "%'";
		sql+= " and c.schoolTerm.id ="+ termId;
		sql+=" order by c.courseCode ,c.weekday,c.timetableNumber desc";
		List<TimetableAppointment> timetableAppointmentAcademys = timetableAppointmentDAO.executeQuery(sql, 0, -1);

		return timetableAppointmentAcademys;
	}

	/*************************************************************************************
	 * @內容：根据信息得到一条月报报表记录
	 * @作者： 贺子龙
	 * @日期：2015-12-27
	 *************************************************************************************/
	public MonthReport creatMonthReport(String acno){
		MonthReport monthReport=new MonthReport();
		//系别
		String department="";
		//获取当前实验中
		department=shareService.getUser().getSchoolAcademy().getAcademyName();
		//中心主任
		String responsiblePerson="";
		/*if (labCenter.getUserByCenterManager()!=null&&!labCenter.getUserByCenterManager().getCname().equals("")) {
			responsiblePerson=labCenter.getUserByCenterManager().getCname()+"["+labCenter.getUserByCenterManager().getUsername()+"]";
		}*/
		monthReport.setDepartment(department);//系别
		//monthReport.setExperimentDepartment(labCenter.getCenterName());//实验部门
		monthReport.setResponsiblePerson(responsiblePerson);//中心主任

		return monthReport;
	}

	/*************************************************************************************
	 * @內容：判断排课的人时数类型、人数、课时数
	 * @作者： 贺子龙
	 * @日期：2015-12-28
	 *************************************************************************************/
	public String[] getTimetableAppointmentType(TimetableAppointment timetableAppointment){
		boolean haveItem=true;//有无实验室项目
		boolean isOther=true;//是否为其他学时
		boolean isUndergraduate=true;//是否为本科生
		String[] result={"0","0","0","0"};
		int totalNumber=0;
		Set<TimetableItemRelated> items=timetableAppointment.getTimetableItemRelateds();
		if (items.size()!=0) {//有实验室项目
			for (TimetableItemRelated item : items) {
				if (item.getOperationItem()!=null
						&&item.getOperationItem().getCDictionaryByLpCategoryMain()!=null) {//属于当前实验室的项目卡
					//实验项目卡类型
					int typeId=item.getOperationItem().getCDictionaryByLpCategoryMain().getId();
					if (typeId==454||typeId==455||typeId==456||typeId==460||typeId==461) {
						//判断实验类型是否为基础、专业基础、专业、毕业论文和毕业设计
						isOther=false;
					}
				}
			}
		}else {
			haveItem=false;
		}
		String username="";//该排课对应的其中一名学生的学号
		//timetableStyle排课方式：1直接排课 2调整排课 3二次不分组排课 4二次分组排课 5自主排课不分组 6自主排课分组
		int style=timetableAppointment.getTimetableStyle();
		if (style==1||style==2||style==3) {
			Set<SchoolCourseStudent> students=timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents();
			List<SchoolCourseStudent> studentList=new ArrayList<SchoolCourseStudent>(students);
			if (studentList.get(0).getUserByStudentNumber()!=null) {//将第一个学生取出来
				username=studentList.get(0).getUserByStudentNumber().getUsername();
			}
			totalNumber=students.size();//统计上课学生人数
		}else if (style==4||style==6) {//分组
			Set<TimetableGroup> groups=timetableAppointment.getTimetableGroups();
			List<TimetableGroup> groupList=new ArrayList<TimetableGroup>(groups);
			if (groupList.get(0)!=null) {
				for (TimetableGroup timetableGroup : groupList) {
					Set<TimetableGroupStudents> students=timetableGroup.getTimetableGroupStudentses();
					totalNumber+=students.size();//统计上课学生人数
				}
				Set<TimetableGroupStudents> studentsII=groupList.get(0).getTimetableGroupStudentses();
				List<TimetableGroupStudents> studentList=new ArrayList<TimetableGroupStudents>(studentsII);
				if (studentList.get(0).getUser()!=null) {//将第一个学生取出来
					username=studentList.get(0).getUser().getUsername();
				}
			}
		}else if (style==5) {
			Set<TimetableCourseStudent> students=timetableAppointment.getTimetableSelfCourse().getTimetableCourseStudents();
			totalNumber=students.size();//统计上课学生人数
			if (totalNumber>0) {
				List<TimetableCourseStudent> studentList=new ArrayList<TimetableCourseStudent>(students);
				if (studentList.get(0).getUser()!=null) {//将第一个学生取出来
					username=studentList.get(0).getUser().getUsername();
				}

			}

		}else if (style==6) {
			//跟style==4归为一种情况
			}

		isUndergraduate=shareService.isUndergraduate(username);//判断是否为本科生
		int type=4;//专科
		if (haveItem) {
			if (isOther) {
				type=2;//其他
			}else{
				if (isUndergraduate) {
					type=3;//本科
				}
			}
		}else{
			type=1;//机房
		}

		result[0]=type+"";//类型1--机房  2--其他  3--本科  4--专科
		result[1]=totalNumber+"";//人数
		int sections = 0;
		for (TimetableAppointmentSameNumber tasn: timetableAppointment.getTimetableAppointmentSameNumbers()){
			sections += tasn.getEndClass() - tasn.getStartClass() + 1;
		}
		result[2]=sections+"";//时数
		String className="";
		if (!username.equals("")) {
			User user=userDAO.findUserByPrimaryKey(username);
			if (user!=null&&user.getSchoolClasses()!=null&&!user.getSchoolClasses().getClassName().equals("")) {
				className=user.getSchoolClasses().getClassName();
			}
		}

		result[3]=className;
		return result;
	}


	/*************************************************************************************
	 * @description：获取所有排课相关的教师
	 * @author： 郑昕茹
	 * @date：2016-11-30
	 *************************************************************************************/
	public List<User> getAllTimetableRelatedTeachers(){
		String sql ="select u from TimetableTeacherRelated t left join t.user u where 1=1 ";
		sql += " group by t.user.username";
		return userDAO.executeQuery(sql, 0, -1);
	}
	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存课程进度的内容
	 * @作者： 戴昊宇
	 * @日期：2017-07-23
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public SchoolCourseDetail saveTimetableAp(HttpServletRequest request) throws ParseException {
		// 调整排课的实验室选择判断实验室匹配度
		String[] labRooms = null;
		if (request.getParameterValues("labRooms1") != null) {
			labRooms = request.getParameterValues("labRooms1");
		} else if (request.getParameterValues("labRooms2") != null) {
			labRooms = request.getParameterValues("labRooms2");
		} else {
			labRooms = request.getParameterValues("labRooms3");
		}
		//调整排课的实验室设备选择
		String[] devices = request.getParameterValues("devices");
		//调整排课的实验室设备选择
		String[]  sLabRoomDevice = request.getParameterValues("labRoomDevice_id");
		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");
		// 项目
		String[] operationitems = request.getParameterValues("operationitems");

		String weekDay = request.getParameter("weekday");
		// 调整排课的星期选择
		String[] weeks = request.getParameterValues("weeks");

		String[] softwares = request.getParameterValues("softwares");



		int[] intWeeks = new int[weeks.length];
		for (int i = 0; i < weeks.length; i++) {
			intWeeks[i] = Integer.parseInt(weeks[i]);
		}
		// 周次进行排序
		String[] sWeek = this.getTimetableWeekClass(intWeeks);
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int i = 0; i < classes.length; i++) {
			intClasses[i] = Integer.parseInt(classes[i]);
		}

		// 节次进行排序
		String[] sClasses = this.getTimetableWeekClass(intClasses);
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(request
				.getParameter("courseDetailNo"));

		/**
		 *
		*/

		TimetableAppointment timetableAppointment1 = new TimetableAppointment();

		timetableAppointment1.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
		// timetableAppointment1.setTimetableNumber(timetableAppointment.getTimetableNumber());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setWeekday(Integer.parseInt(weekDay));
		timetableAppointment1.setCreatedDate(Calendar.getInstance());
		timetableAppointment1.setUpdatedDate(Calendar.getInstance());
		// timetableAppointment1.setTeacherRelated(timetableAppointment.getTeacherRelated());
		// timetableAppointment1.setTutorRelated(timetableAppointment.getTutorRelated());
		timetableAppointment1.setSchoolCourseDetail(schoolCourseDetail);
		//保存学期
		timetableAppointment1.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
		timetableAppointment1.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setSchoolCourseInfo(schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo());
		// 设置排课方式
		timetableAppointment1.setTimetableStyle(2);
		// 设置排课状态
		timetableAppointment1.setStatus(10);
		// 设置选课组编号
		timetableAppointment1.setCourseCode(schoolCourseDetail.getSchoolCourse().getCourseCode());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setCreatedDate(Calendar.getInstance());
		// timetableAppointment1.setWeekday(timetableAppointment.getWeekday());
		timetableAppointment1.setUpdatedDate(Calendar.getInstance());
		if (request.getParameter("preparation") != null) {
			timetableAppointment1.setPreparation(request.getParameter("preparation"));
		} else {
			timetableAppointment1.setPreparation("");
		}
		if (request.getParameter("groups") != null && !request.getParameter("groups").isEmpty()) {
			timetableAppointment1.setGroups(Integer.parseInt(request.getParameter("groups")));
		} else {
			timetableAppointment1.setGroups(-1);
		}
		if (request.getParameter("labhours") != null && !request.getParameter("labhours").isEmpty()) {
			timetableAppointment1.setLabhours(Integer.parseInt(request.getParameter("labhours")));
		} else {
			timetableAppointment1.setLabhours(-1);
		}
		if (request.getParameter("groupCount") != null && !request.getParameter("groupCount").isEmpty()) {
			timetableAppointment1.setGroupCount(Integer.parseInt(request.getParameter("groupCount")));
		} else {
			timetableAppointment1.setGroupCount(-1);
		}
		if (request.getParameter("consumablesCosts") != null) {
			timetableAppointment1.setConsumablesCosts(new BigDecimal((request.getParameter("consumablesCosts"))));
		} else {
			timetableAppointment1.setConsumablesCosts(new BigDecimal(-1));
		}
		// 设置调整排课的内容
		if (sWeek[0].indexOf(("-")) == -1) {
			timetableAppointment1.setTotalWeeks("1");
			timetableAppointment1.setStartWeek(Integer.parseInt(sWeek[0]));
			timetableAppointment1.setEndWeek(Integer.parseInt(sWeek[0]));

		} else {
			timetableAppointment1.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[0].split("-")[1]) - Integer
					.parseInt(sWeek[0].split("-")[0]))));
			timetableAppointment1.setStartWeek(Integer.parseInt(sWeek[0].split("-")[0]));
			timetableAppointment1.setEndWeek(Integer.parseInt(sWeek[0].split("-")[1]));
		}

		if (sClasses[0].indexOf(("-")) == -1) {
			timetableAppointment1.setTotalClasses(Integer.parseInt(sClasses[0]));
			timetableAppointment1.setStartClass(Integer.parseInt(sClasses[0]));
			timetableAppointment1.setEndClass(Integer.parseInt(sClasses[0]));
		} else {
			timetableAppointment1.setTotalClasses((Integer.parseInt(sClasses[0].split("-")[1]) - Integer
					.parseInt(sClasses[0].split("-")[0])));
			timetableAppointment1.setStartClass(Integer.parseInt(sClasses[0].split("-")[0]));
			timetableAppointment1.setEndClass(Integer.parseInt(sClasses[0].split("-")[1]));
		}
		// 先写死排课保存
		//timetableAppointment1.setTimetableSelfCourse(timetableSelfCourseDAO.findTimetableSelfCourseById(365));
		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);

		// * 对排课软件进行保存
         TimetableSoftwareRelated timetableSoftwaRelated = new TimetableSoftwareRelated();
		if(softwares!=null && softwares.length>0 ){
			for(int i2=0;i2<softwares.length;i2++){
				Software software = softwarenDAO.findSoftwareById(Integer.parseInt(softwares[i2]));
				List<Software> list = new ArrayList<Software>();
				list.add(software);
				timetableSoftwaRelated.setSoftware(list.get(0));
				timetableSoftwaRelated.setTimetableAppointment(timetableAppointment1);
				TimetableSoftwareRelated timetableSoftware = timetableSoftwareRelatedDAO.store(timetableSoftwaRelated);
				timetableSoftwareRelatedDAO.flush();
			}

		}


		TimetableLabRelated timetableLabRelated = new TimetableLabRelated();

		int countLabRoomDevice = 0; // 计算所选实验室总共有多少设备
		// 如果matchLabs不为空时
		if (labRooms != null && labRooms.length > 0) {
			for (int i1 = 0; i1 < labRooms.length; i1++) {
				// 将matchLabs添加到matchLabs中
				LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[i1]));
				timetableLabRelated.setLabRoom(labRoom);
				timetableLabRelated.setTimetableAppointment(timetableAppointment1);
				TimetableLabRelated timetableLabRelatedTmp = timetableLabRelatedDAO.store(timetableLabRelated);
				timetableLabRelatedDAO.flush();

				// 实验室设备数量累加
                if (labRoom.getLabRoomDevices()!=null && labRoom.getLabRoomDevices().size()>0) {
                    countLabRoomDevice+=labRoom.getLabRoomDevices().size();
                }

				/*if(sLabRoomDevice.length>0){
					timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
				}*/
			}
		}

		// 设置此次排课的针对对象（1 设备  2 实验室）
		if ((sLabRoomDevice!=null && countLabRoomDevice == sLabRoomDevice.length)
				|| sLabRoomDevice==null) {// i.全选情况--纺织学院   ii.其他学院sLabRoomDevice字段为空
			timetableAppointment1.setDeviceOrLab(2);// 此次排课针对实验室
		}else {
			timetableAppointment1.setDeviceOrLab(1);// 此次排课针对设备
		}
		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);

		// * 对排课预约选定的实验项目进行保存

		TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
		// 如果matchItems不为空时
		if (operationitems != null && operationitems.length > 0) {
			for (int i1 = 0; i1 < operationitems.length; i1++) {
				// 将matchItems添加到matchItems中
				timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(Integer
						.parseInt(operationitems[i1])));
				timetableItemRelated.setTimetableAppointment(timetableAppointment1);
				timetableItemRelatedDAO.store(timetableItemRelated);
			}
		}

		// * 对排课预约选定的指导老师进行保存

		TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
		// 获取选择的实验室列表
		List<User> matchTeachers = new ArrayList<User>();
		// 如果matchLabs不为空时
		if (teachers != null && teachers.length > 0) {
			for (int i1 = 0; i1 < teachers.length; i1++) {
				// 将matchLabs添加到matchLabs中
				matchTeachers.add(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setTimetableAppointment(timetableAppointment1);
				timetableTeacherRelatedDAO.store(timetableTeacherRelated);
			}
		}

		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {
				TimetableAppointmentSameNumber timetableAppointmentSameNumber = new TimetableAppointmentSameNumber();
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedDate(Calendar.getInstance());
				timetableAppointmentSameNumber.setUpdatedDate(Calendar.getInstance());

				// 设置调整排课的内容
				if (sWeek[i].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalWeeks("1");
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i]));

				} else {
					timetableAppointmentSameNumber
							.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[i].split("-")[1]) - Integer
									.parseInt(sWeek[i].split("-")[0]))));
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i].split("-")[0]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i].split("-")[1]));
				}

				if (sClasses[j].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalClasses(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j]));
				} else {
					timetableAppointmentSameNumber
							.setTotalClasses((Integer.parseInt(sClasses[j].split("-")[1]) - Integer
									.parseInt(sClasses[j].split("-")[0])));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j].split("-")[0]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j].split("-")[1]));
				}
				timetableAppointmentSameNumber.setTimetableAppointment(timetableAppointment1);
				timetableAppointmentSameNumberDAO.store(timetableAppointmentSameNumber);
				timetableAppointmentSameNumberDAO.flush();
			}
		}

		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id="+timetableAppointment1.getId(), 0,-1);
		if(timetableAppointment1.getDeviceOrLab().equals(1)){
			for(TimetableLabRelated timetableLabRelatedTmp:timetableLabRelateds){
				timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
			}
		}else {
			mysqlService.createLabLimitByAppointment(timetableAppointment1.getId());
		}
		return schoolCourseDetail;
	}


	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存课程进度编辑的内容
	 * @作者： 戴昊宇
	 * @日期：2017-07-23
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public SchoolCourseDetail saveTimetableApedit(HttpServletRequest request) throws ParseException {

        String content =request.getParameter("content");
         String Type = request.getParameter("classType");
         int classType = Integer.parseInt(Type);
		String id = request.getParameter("taid");
		int taid = Integer.parseInt(id);

		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(request
				.getParameter("courseDetailNo"));

		TimetableAppointment timetableAppointment1 = new TimetableAppointment();
		timetableAppointment1 = timetableAppointmentDAO.findTimetableAppointmentById(taid);

		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);
		return schoolCourseDetail;
	}
	/*************************************************************************************
	 * @內容：课程进度中，删除课程进度的内容
	 * @作者： 戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	@Transactional
	public void deleteTimetableAppointment(int id) {

		TimetableBatch timetableBatch = null;
		// 设置排课批次标记为未调整完成
		if (timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().size() > 0) {
			timetableBatch = timetableAppointmentDAO.findTimetableAppointmentById(id).getTimetableGroups().iterator()
					.next().getTimetableBatch();
			timetableBatch.setFlag(null);
			timetableBatchDAO.store(timetableBatch);
			// 设置排课批次标记为待发布
			for (TimetableGroup timetableGroup : timetableBatch.getTimetableGroups()) {
				for (TimetableAppointment timetableAppointment : timetableGroup.getTimetableAppointments()) {
					// 设置为待发布
					timetableAppointment.setStatus(10);
					// 保存排课分组信息
					// Set<TimetableGroup> timetableGroupList=new
					// HashSet<TimetableGroup>();
					// timetableGroupDAO.executeQuery("SELECT t FROM TimetableGroup t join t.timetableAppointments s where t.id = ",0,-1);
					// timetableGroupList.addAll(timetableGroupDAO.executeQuery("SELECT t FROM TimetableAppointment t join t.timetableGroups s where t.id = "
					// +timetableAppointment.getId() ,0,-1));

					// timetableAppointment.setTimetableGroups(timetableGroupList);
					timetableAppointmentDAO.store(timetableAppointment);
				}
			}
		}

		// 删除相关实验分室
		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO
				.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id =" + id);
		for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
			//根据实验室相关设备遍历，相关实验分室下的实验室设备资源
			for(TimetableLabRelatedDevice timetableLabRelatedDevice:timetableLabRelated.getTimetableLabRelatedDevices()){
				List<LabRoomDeviceReservation> labRoomDeviceReservations = labRoomDeviceReservationDAO.executeQuery("select c from LabRoomDeviceReservation c where c.timetableLabDevice ="+timetableLabRelatedDevice.getId(), 0,-1);
			    //开始删除
				for(LabRoomDeviceReservation labRoomDeviceReservation:labRoomDeviceReservations){
					labRoomDeviceReservationDAO.remove(labRoomDeviceReservation);
				}
			}
			timetableLabRelatedDAO.remove(timetableLabRelated);
		}
		// 删除相关授课教师
		List<TimetableTeacherRelated> timetableTeacherRelateds = timetableTeacherRelatedDAO
				.executeQuery("select c from TimetableTeacherRelated c where c.timetableAppointment.id =" + id);
		for (TimetableTeacherRelated timetableTeacherRelated : timetableTeacherRelateds) {
			timetableTeacherRelatedDAO.remove(timetableTeacherRelated);
		}
		// 删除相关指导教师
		List<TimetableTutorRelated> timetableTutorRelateds = timetableTutorRelatedDAO
				.executeQuery("select c from TimetableTutorRelated c where c.timetableAppointment.id =" + id);
		for (TimetableTutorRelated timetableTutorRelated : timetableTutorRelateds) {
			timetableTutorRelatedDAO.remove(timetableTutorRelated);
		}

		// 删除相关指导教师
		List<TimetableItemRelated> timetableItemRelateds = timetableItemRelatedDAO
				.executeQuery("select c from TimetableItemRelated c where c.timetableAppointment.id =" + id);
		for (TimetableItemRelated timetableItemRelated : timetableItemRelateds) {
			timetableItemRelatedDAO.remove(timetableItemRelated);
		}

		// 移除分组选课数据

		timetableAppointmentDAO.remove(timetableAppointmentDAO.findTimetableAppointmentById(id));

	}
	/*************************************************************************************
	 * 功能：排课--根据courseCode（课程编号或者自主排课编号）获取相关排课记录list
	 * 作者：贺子龙
	 * 日期：2016-07-16
	 *************************************************************************************/
	public List<TimetableAppointment> findAppointmentByCourseCode(String courseCode){

		String sql = "select t from TimetableAppointment t where 1=1";
		sql+=" and t.courseCode like '"+courseCode+"'";
		List<TimetableAppointment> appointments = timetableAppointmentDAO.executeQuery(sql, 0, -1);
		return appointments;

	}
	/*************************************************************************************
	 * 功能：排课--判断排课的用到的周次是不是在可用周次范围内(保存前，保存后)
	 * 作者：贺子龙
	 * 日期：2016-07-13
	 *************************************************************************************/
	public boolean isInValidWeeks(Integer appointmentId, Integer gapWeek,  HttpServletRequest request, Integer term){

		// 该方法分保存前和保存后两种情况来判断

		boolean isInValidWeeks = true;

		if (!EmptyUtil.isIntegerEmpty(appointmentId) && appointmentId!=0) {// 保存后

			TimetableAppointment appointment = this.findTimetableAppointmentByPrimaryKey(appointmentId);

			// 接口转化
			// 学期
			int appointTerm = this.getTermForAppointment(appointment);
			// 实验室
			Set<TimetableLabRelated> labRelates = appointment.getTimetableLabRelateds();
			List<Integer> labRoomList = new ArrayList<Integer>();
			for (TimetableLabRelated timetableLabRelated : labRelates) {
				labRoomList.add(timetableLabRelated.getLabRoom().getId());
			}
			Integer[] labroomsInteger = (Integer[])labRoomList.toArray(new Integer[labRoomList.size()]);
			int[] labrooms = new int[labroomsInteger.length];
			for (int i = 0; i < labroomsInteger.length; i++) {
				labrooms[i] = labroomsInteger[i];
			}

			List<Integer> classList = new ArrayList<Integer>();

			// 节次
			Set<TimetableAppointmentSameNumber> sameNumbers = appointment.getTimetableAppointmentSameNumbers();
			if (sameNumbers.size()>0) {// 有跳节或跳周
				for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : sameNumbers) {
					int startClass = timetableAppointmentSameNumber.getStartClass();
					int endClass = timetableAppointmentSameNumber.getEndClass();
					for (int i = startClass; i <= endClass; i++) {
						if (!classList.contains(i)) {// 去重
							classList.add(i);
						}
					}
				}
			}else {// 没有跳节或跳周
				int startClass = appointment.getStartClass();
				int endClass = appointment.getEndClass();
				for (int i = startClass; i <= endClass; i++) {
					if (!classList.contains(i)) {// 去重
						classList.add(i);
					}
				}
			}
			Integer[] classesInteger = (Integer[])classList.toArray(new Integer[classList.size()]);
			int[] classes = new int[classesInteger.length];
			for (int i = 0; i < classesInteger.length; i++) {
				classes[i] = classesInteger[i];
			}
			// 星期
			int weekday = appointment.getWeekday();

			// 通过接口来查出可用的周次
			Integer[] validWeeks = outerApplicationServiceImpl.getValidWeeks(appointTerm, classes, labrooms, weekday, appointmentId);

			// 周次
			if (sameNumbers.size()>0) {// 有跳节或跳周
				for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : sameNumbers) {
					int startWeek = timetableAppointmentSameNumber.getStartWeek()+gapWeek;
					int endWeek = timetableAppointmentSameNumber.getEndWeek()+gapWeek;
					for (int i = startWeek; i <= endWeek; i++) {
						// 任何一个周次不在可用周次内，都会将isInValidWeeks置为false
						if (!EmptyUtil.isIntegerArray(validWeeks, i)) {
							isInValidWeeks = false;
							return isInValidWeeks;
						}
					}
				}
			}else {// 没有跳节或跳周
				int startWeek = appointment.getStartWeek()+gapWeek;
				int endWeek = appointment.getEndWeek()+gapWeek;
				for (int i = startWeek; i <= endWeek; i++) {
					// 任何一个周次不在可用周次内，都会将isInValidWeeks置为false
					if (EmptyUtil.isIntegerArray(validWeeks, i)) {
						isInValidWeeks = false;
						return isInValidWeeks;
					}
				}
			}


		}else {// 保存前
			if (!EmptyUtil.isStringArrayEmpty(request.getParameterValues("labRooms"))) {
				// 实验室选择
				String[] labRoomString = request.getParameterValues("labRooms");
				// 星期选择
				String weekdayString = request.getParameter("weekday");
				// 节次选择
				String[] classString = request.getParameterValues("classes");

				// 接口转化
				int[] labrooms = new int[labRoomString.length];
				for (int i = 0; i <= labrooms.length; i++) {
					labrooms[i] =  Integer.parseInt(labRoomString[i]);
				}
				int[] classes = new int[classString.length];
				for (int i = 0; i <= classes.length; i++) {
					classes[i] = Integer.parseInt(classString[i]);
				}
				int weekday = Integer.parseInt(weekdayString);

				// 通过接口来查出可用的周次
				Integer[] validWeeks = outerApplicationServiceImpl.getValidWeeks(term, classes, labrooms, weekday, 0);


				// 判断所选周次选择是不是在可用周次内
				String[] weekString = request.getParameterValues("weeks");
				for (String week : weekString) {
					// 任何一个周次不在可用周次内，都会将isInValidWeeks置为false
					if (!EmptyUtil.isIntegerArray(validWeeks, Integer.parseInt(week))) {
						isInValidWeeks = false;
						return isInValidWeeks;
					}
				}
			}
		}

		return isInValidWeeks;
	}

	/*************************************************************************************
	 * 功能：根据主键查找相应的timetableAppointment
	 * 作者：贺子龙
	 * 日期：2016-01-23
	 *************************************************************************************/
	public TimetableAppointment findTimetableAppointmentByPrimaryKey(int id){
		TimetableAppointment timetableAppointment=timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(id);
		if (timetableAppointment!=null) {
			return timetableAppointment;
		}else {
			return null;
		}
	}
	/*************************************************************************************
	 * 功能：排课--拿到排课的学期数据
	 * 作者：贺子龙
	 * 日期：2016-07-13
	 *************************************************************************************/
	public int getTermForAppointment(TimetableAppointment appointment){
		// 获取学期
		int appointTerm = 0;// 初始化
		int style = appointment.getTimetableStyle();
		if (style!=5 && style!=6) {// 非自主排课
			appointTerm = appointment.getSchoolCourse().getSchoolTerm().getId();
		}else {
			appointTerm = appointment.getTimetableSelfCourse().getSchoolTerm().getId();
		}
		return appointTerm;
	}
	/*************************************************************************************
	 * 功能：顺延排课--生成一条提醒
	 * 作者：贺子龙
	 * 日期：2016-07-16
	 *************************************************************************************/
	public String createAlertForAppointment(int appointmentId, int gapWeek){

		String alert = "";
		String labRoomString = "";

		TimetableAppointment timetableAppointment = timetableAppointmentDAO.findTimetableAppointmentByPrimaryKey(appointmentId);

		// 实验室
		Set<TimetableLabRelated> labRelateds = timetableAppointment.getTimetableLabRelateds();
		if (labRelateds!=null && labRelateds.size()>0) {
			for (TimetableLabRelated timetableLabRelated : labRelateds) {
				labRoomString += timetableLabRelated.getLabRoom().getLabRoomName()+"、";
			}
		}
		// 周次、节次
		List<Integer> classList = new ArrayList<Integer>();
		List<Integer> weekList = new ArrayList<Integer>();

		String classString = "第";
		String weekString = "第";
		Set<TimetableAppointmentSameNumber> sameNumbers = timetableAppointment.getTimetableAppointmentSameNumbers();
		if (sameNumbers.size()>0) {// 有跳节或跳周
			for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : sameNumbers) {
				int startClass = timetableAppointmentSameNumber.getStartClass();
				int endClass = timetableAppointmentSameNumber.getEndClass();
				for (int i = startClass; i <= endClass; i++) {
					if (!classList.contains(i)) {// 去重
						classList.add(i);
					}
				}

				int startWeek = timetableAppointmentSameNumber.getStartWeek()+gapWeek;
				int endWeek = timetableAppointmentSameNumber.getEndWeek()+gapWeek;
				for (int i = startWeek; i <= endWeek; i++) {
					if (!weekList.contains(i)) {// 去重
						weekList.add(i);
					}
				}

			}
		}else {// 没有跳节或跳周
			int startClass = timetableAppointment.getStartClass();
			int endClass = timetableAppointment.getEndClass();
			for (int i = startClass; i <= endClass; i++) {
				if (!classList.contains(i)) {// 去重
					classList.add(i);
				}
			}

			int startWeek = timetableAppointment.getStartWeek()+gapWeek;
			int endWeek = timetableAppointment.getEndWeek()+gapWeek;
			for (int i = startWeek; i <= endWeek; i++) {
				if (!weekList.contains(i)) {// 去重
					weekList.add(i);
				}
			}

		}

		for (Integer weekInteger : weekList) {
			classString += weekInteger+"、";
		}

		for (Integer classInteger : classList) {
			weekString += classInteger+"、";
		}


		classString = classString.substring(0, classString.length()-1);
		weekString = weekString.substring(0, weekString.length()-1);

		classString += "节";
		weekString += "周";

		labRoomString = labRoomString.substring(0, labRoomString.length()-1);

		alert = labRoomString+"，"+weekString+"，"+classString+"已经被其他课程占用，请另行安排时间段。";

		return alert;

	}
	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存调整课程的内容
	 * @作者： 戴昊宇
	 * @日期：2017-09-23
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public SchoolCourseDetail saveTimetableadjustment2(HttpServletRequest request,Integer tapId) throws ParseException {
		// 调整排课的实验室选择判断实验室匹配度
		String[] labRooms = null;
		if (request.getParameterValues("labRooms1") != null) {
			labRooms = request.getParameterValues("labRooms1");
		} else if (request.getParameterValues("labRooms2") != null) {
			labRooms = request.getParameterValues("labRooms2");
		} else {
			labRooms = request.getParameterValues("labRooms3");
		}
		//调整排课的实验室设备选择
		String[] devices = request.getParameterValues("devices");
		//调整排课的实验室设备选择
		String[]  sLabRoomDevice = request.getParameterValues("labRoomDevice_id");
		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");
		// 项目
		String[] operationitems = request.getParameterValues("operationitems");

		String weekDay = request.getParameter("weekday");
		// 调整排课的星期选择
		String[] weeks = request.getParameterValues("weeks");

		String[] softwares = request.getParameterValues("softwares");



		int[] intWeeks = new int[weeks.length];
		for (int i = 0; i < weeks.length; i++) {
			intWeeks[i] = Integer.parseInt(weeks[i]);
		}
		// 周次进行排序
		String[] sWeek = this.getTimetableWeekClass(intWeeks);
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int i = 0; i < classes.length; i++) {
			intClasses[i] = Integer.parseInt(classes[i]);
		}

		// 节次进行排序
		String[] sClasses = this.getTimetableWeekClass(intClasses);
		SchoolCourseDetail schoolCourseDetail = schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(request
				.getParameter("courseDetailNo"));

		/**
		 *
		*/

		TimetableAppointment timetableAppointment1 = new TimetableAppointment();

		timetableAppointment1.setAppointmentNo(schoolCourseDetail.getCourseDetailNo());
		//保存学期
		timetableAppointment1.setSchoolTerm(schoolCourseDetail.getSchoolTerm());
		// timetableAppointment1.setTimetableNumber(timetableAppointment.getTimetableNumber());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setWeekday(Integer.parseInt(weekDay));
		timetableAppointment1.setCreatedDate(Calendar.getInstance());
		timetableAppointment1.setUpdatedDate(Calendar.getInstance());
		// timetableAppointment1.setTeacherRelated(timetableAppointment.getTeacherRelated());
		// timetableAppointment1.setTutorRelated(timetableAppointment.getTutorRelated());
		timetableAppointment1.setSchoolCourseDetail(schoolCourseDetail);
		timetableAppointment1.setSchoolCourse(schoolCourseDetail.getSchoolCourse());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setSchoolCourseInfo(schoolCourseDetail.getSchoolCourse().getSchoolCourseInfo());
		// 设置排课方式
		timetableAppointment1.setTimetableStyle(2);
		// 设置排课状态
		timetableAppointment1.setStatus(10);
		// 设置选课组编号
		timetableAppointment1.setCourseCode(schoolCourseDetail.getSchoolCourse().getCourseCode());
		timetableAppointment1.setCreatedBy(shareService.getUserDetail().getUsername());
		timetableAppointment1.setCreatedDate(Calendar.getInstance());
		// timetableAppointment1.setWeekday(timetableAppointment.getWeekday());
		timetableAppointment1.setUpdatedDate(Calendar.getInstance());
		if (request.getParameter("preparation") != null) {
			timetableAppointment1.setPreparation(request.getParameter("preparation"));
		} else {
			timetableAppointment1.setPreparation("");
		}
		if (request.getParameter("groups") != null && !request.getParameter("groups").isEmpty()) {
			timetableAppointment1.setGroups(Integer.parseInt(request.getParameter("groups")));
		} else {
			timetableAppointment1.setGroups(-1);
		}
		if (request.getParameter("labhours") != null && !request.getParameter("labhours").isEmpty()) {
			timetableAppointment1.setLabhours(Integer.parseInt(request.getParameter("labhours")));
		} else {
			timetableAppointment1.setLabhours(-1);
		}
		if (request.getParameter("groupCount") != null && !request.getParameter("groupCount").isEmpty()) {
			timetableAppointment1.setGroupCount(Integer.parseInt(request.getParameter("groupCount")));
		} else {
			timetableAppointment1.setGroupCount(-1);
		}
		if (request.getParameter("consumablesCosts") != null) {
			timetableAppointment1.setConsumablesCosts(new BigDecimal((request.getParameter("consumablesCosts"))));
		} else {
			timetableAppointment1.setConsumablesCosts(new BigDecimal(-1));
		}
		// 设置调整排课的内容
		if (sWeek[0].indexOf(("-")) == -1) {
			timetableAppointment1.setTotalWeeks("1");
			timetableAppointment1.setStartWeek(Integer.parseInt(sWeek[0]));
			timetableAppointment1.setEndWeek(Integer.parseInt(sWeek[0]));

		} else {
			timetableAppointment1.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[0].split("-")[1]) - Integer
					.parseInt(sWeek[0].split("-")[0]))));
			timetableAppointment1.setStartWeek(Integer.parseInt(sWeek[0].split("-")[0]));
			timetableAppointment1.setEndWeek(Integer.parseInt(sWeek[0].split("-")[1]));
		}

		if (sClasses[0].indexOf(("-")) == -1) {
			timetableAppointment1.setTotalClasses(Integer.parseInt(sClasses[0]));
			timetableAppointment1.setStartClass(Integer.parseInt(sClasses[0]));
			timetableAppointment1.setEndClass(Integer.parseInt(sClasses[0]));
		} else {
			timetableAppointment1.setTotalClasses((Integer.parseInt(sClasses[0].split("-")[1]) - Integer
					.parseInt(sClasses[0].split("-")[0])));
			timetableAppointment1.setStartClass(Integer.parseInt(sClasses[0].split("-")[0]));
			timetableAppointment1.setEndClass(Integer.parseInt(sClasses[0].split("-")[1]));
		}
		//保存原先调课信息
		timetableAppointment1.setOriginalTapid(tapId);
		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);

		// * 对排课软件进行保存
         TimetableSoftwareRelated timetableSoftwaRelated = new TimetableSoftwareRelated();
		if(softwares!=null && softwares.length>0 ){
			for(int i2=0;i2<softwares.length;i2++){
				Set<Software> software = softwarenDAO.findSoftwareByCode(softwares[i2]);
				List<Software> list = new ArrayList<Software>();
				list.addAll(software);
				if(list.size()>0){
				timetableSoftwaRelated.setSoftware(list.get(0));
				timetableSoftwaRelated.setTimetableAppointment(timetableAppointment1);
				TimetableSoftwareRelated timetableSoftware = timetableSoftwareRelatedDAO.store(timetableSoftwaRelated);
				timetableSoftwareRelatedDAO.flush();
				}
			}

		}


		TimetableLabRelated timetableLabRelated = new TimetableLabRelated();

		int countLabRoomDevice = 0; // 计算所选实验室总共有多少设备
		// 如果matchLabs不为空时
		if (labRooms != null && labRooms.length > 0) {
			for (int i1 = 0; i1 < labRooms.length; i1++) {
				// 将matchLabs添加到matchLabs中
				LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[i1]));
				timetableLabRelated.setLabRoom(labRoom);
				timetableLabRelated.setTimetableAppointment(timetableAppointment1);
				TimetableLabRelated timetableLabRelatedTmp = timetableLabRelatedDAO.store(timetableLabRelated);
				timetableLabRelatedDAO.flush();

				// 实验室设备数量累加
                if (labRoom.getLabRoomDevices()!=null && labRoom.getLabRoomDevices().size()>0) {
                    countLabRoomDevice+=labRoom.getLabRoomDevices().size();
                }

				/*if(sLabRoomDevice.length>0){
					timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
				}*/
			}
		}

		// 设置此次排课的针对对象（1 设备  2 实验室）
		if ((sLabRoomDevice!=null && countLabRoomDevice == sLabRoomDevice.length)
				|| sLabRoomDevice==null) {// i.全选情况--纺织学院   ii.其他学院sLabRoomDevice字段为空
			timetableAppointment1.setDeviceOrLab(2);// 此次排课针对实验室
		}else {
			timetableAppointment1.setDeviceOrLab(1);// 此次排课针对设备
		}
		timetableAppointment1 = timetableAppointmentDAO.store(timetableAppointment1);

		// * 对排课预约选定的实验项目进行保存

		TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
		// 如果matchItems不为空时
		if (operationitems != null && operationitems.length > 0) {
			for (int i1 = 0; i1 < operationitems.length; i1++) {
				// 将matchItems添加到matchItems中
				timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(Integer
						.parseInt(operationitems[i1])));
				timetableItemRelated.setTimetableAppointment(timetableAppointment1);
				timetableItemRelatedDAO.store(timetableItemRelated);
			}
		}

		// * 对排课预约选定的指导老师进行保存

		TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
		// 获取选择的实验室列表
		List<User> matchTeachers = new ArrayList<User>();
		// 如果matchLabs不为空时
		if (teachers != null && teachers.length > 0) {
			for (int i1 = 0; i1 < teachers.length; i1++) {
				// 将matchLabs添加到matchLabs中
				matchTeachers.add(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setUser(userDAO.findUserByUsername(teachers[i1]));
				timetableTeacherRelated.setTimetableAppointment(timetableAppointment1);
				timetableTeacherRelatedDAO.store(timetableTeacherRelated);
			}
		}

		for (int i = 0; i < sWeek.length; i++) {
			for (int j = 0; j < sClasses.length; j++) {
				TimetableAppointmentSameNumber timetableAppointmentSameNumber = new TimetableAppointmentSameNumber();
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedBy(shareService.getUserDetail().getUsername());
				timetableAppointmentSameNumber.setCreatedDate(Calendar.getInstance());
				timetableAppointmentSameNumber.setUpdatedDate(Calendar.getInstance());

				// 设置调整排课的内容
				if (sWeek[i].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalWeeks("1");
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i]));

				} else {
					timetableAppointmentSameNumber
							.setTotalWeeks(String.valueOf((Integer.parseInt(sWeek[i].split("-")[1]) - Integer
									.parseInt(sWeek[i].split("-")[0]))));
					timetableAppointmentSameNumber.setStartWeek(Integer.parseInt(sWeek[i].split("-")[0]));
					timetableAppointmentSameNumber.setEndWeek(Integer.parseInt(sWeek[i].split("-")[1]));
				}

				if (sClasses[j].indexOf(("-")) == -1) {
					timetableAppointmentSameNumber.setTotalClasses(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j]));
				} else {
					timetableAppointmentSameNumber
							.setTotalClasses((Integer.parseInt(sClasses[j].split("-")[1]) - Integer
									.parseInt(sClasses[j].split("-")[0])));
					timetableAppointmentSameNumber.setStartClass(Integer.parseInt(sClasses[j].split("-")[0]));
					timetableAppointmentSameNumber.setEndClass(Integer.parseInt(sClasses[j].split("-")[1]));
				}
				timetableAppointmentSameNumber.setTimetableAppointment(timetableAppointment1);
				timetableAppointmentSameNumberDAO.store(timetableAppointmentSameNumber);
				timetableAppointmentSameNumberDAO.flush();
			}
		}

		List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery("select c from TimetableLabRelated c where c.timetableAppointment.id="+timetableAppointment1.getId(), 0,-1);
		if(timetableAppointment1.getDeviceOrLab().equals(1)){
			for(TimetableLabRelated timetableLabRelatedTmp:timetableLabRelateds){
				timetableCourseSchedulingService.saveTimetableLabroomDeviceReservation(timetableLabRelatedTmp, sLabRoomDevice,schoolCourseDetail.getSchoolTerm().getId());
			}
		}else {
			mysqlService.createLabLimitByAppointment(timetableAppointment1.getId());
		}
		return schoolCourseDetail;
	}

	/*************************************************************************************
	 * @內容：排课管理--停课
	 * @作者： 戴昊宇
	 * @日期：2017-10-10
	 *************************************************************************************/
	public void disableAppointment(int id) {
		TimetableAppointment timetableAppointment = timetableAppointmentDAO.findTimetableAppointmentById(id);
        timetableAppointment.setEnabled(false);
        timetableAppointmentDAO.store(timetableAppointment);
        timetableAppointmentDAO.flush();
	}
	/*************************************************************************************
	 * @內容：查询排课时间
	 * @作者： 戴昊宇
	 * @日期：2017-11-08
	 *************************************************************************************/
	public List<SchoolTermActive> findSchoolTermActiveByTerm(int term){
		String sql = "select c from SchoolTermActive  c  where c.deadLine="+term;
		List<SchoolTermActive> list = schoolTermActiveDAO.executeQuery(sql,0,-1);
		return list;
	}
	 /*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：孙虎
	 * @日期：2017-11-22
	 *************************************************************************************/
    @Override
	public List<Object[]> getViewLabroomTimetableRegisters(TimetableAppointment timetableAppointment,int curr,int size){
		String sql = "select * from view_labroom_appointment_register c where 1=1";
		if (timetableAppointment.getSchoolCourseInfo() != null && timetableAppointment.getSchoolCourseInfo().getCourseName() != null){
			sql += " and c.course_name like '%"+timetableAppointment.getSchoolCourseInfo().getCourseName()+"%'";
		}
		if (timetableAppointment.getCDictionaryByObject()!= null && timetableAppointment.getCDictionaryByObject().getCName() != ""){
			sql += " and c.object = '"+timetableAppointment.getCDictionaryByObject().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByApplication()!= null && timetableAppointment.getCDictionaryByApplication().getCName()!= ""){
			sql += " and c.application = '"+timetableAppointment.getCDictionaryByApplication().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByEquipmentSituation()!= null && timetableAppointment.getCDictionaryByEquipmentSituation().getCName()!= ""){
			sql += " and c.equipment_situation = '"+timetableAppointment.getCDictionaryByEquipmentSituation().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByTidySituation()!= null && timetableAppointment.getCDictionaryByTidySituation().getCName()!= ""){
			sql += " and c.tidy_situation = '"+timetableAppointment.getCDictionaryByTidySituation().getCName()+"'";
		}
		if(timetableAppointment.getConfirmRemark()!=null&timetableAppointment.getConfirmRemark()!=""){
			sql +=" and c.confirm_remark='"+timetableAppointment.getConfirmRemark()+"'";
		}
		if(timetableAppointment.getCreatedBy()!=null){
			sql +=" and c.cname like '%"+timetableAppointment.getCreatedBy()+"%'";
		}
		sql+=" order by c.start_week desc";
		Query query= entityManager.createNativeQuery(sql);
		query.setMaxResults(size);
		query.setFirstResult(curr);
        List<Object[]> list= query.getResultList();
		return list;
	}

   //获取上课确认栏 列表数据
    @Override
	public List<Object[]> getViewLabroomTimetableRegistersConfirm(String courseNumber,int curr,int size){
		Calendar now = Calendar.getInstance();
		int weekday = shareService.getBelongsSchoolWeekday(now);//当前星期
		int week = shareService.getBelongsSchoolWeek(now);//当前周次
		if(weekday == 1 && week != 1){//若为周一则显示上个星期周五信息
			weekday = 5;
		}
		String sql = "select * from view_labroom_appointment_register c where 1=1";
		if(!courseNumber.equals("")){
			sql += " and c.course_code = '"+courseNumber+"'";
		}
		sql += " and c.start_week <" + week;
		sql += " and c.confirmm_user is null";
		sql += " or ( c.weekday <" + weekday;
		sql += " and c.start_week = " + week +")";
		Query query= entityManager.createNativeQuery(sql);
		query.setMaxResults(size);
		query.setFirstResult(curr);
        List<Object[]> list= query.getResultList();
		return list;
	}
	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：孙虎
	 * @日期：2017-11-22
	 *************************************************************************************/
	@Override
	public int getViewLabroomTimetableRegistersCount(TimetableAppointment timetableAppointment){
		String sql = "select count(*) from view_labroom_appointment_register c where 1=1";
		if (timetableAppointment.getSchoolCourseInfo() != null && timetableAppointment.getSchoolCourseInfo().getCourseName() != ""){
			sql += " and c.course_name like '%"+timetableAppointment.getSchoolCourseInfo().getCourseName()+"%'";
		}
		if (timetableAppointment.getCDictionaryByObject()!= null && timetableAppointment.getCDictionaryByObject().getCName() != ""){
			sql += " and c.object = '"+timetableAppointment.getCDictionaryByObject().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByApplication()!= null && timetableAppointment.getCDictionaryByApplication().getCName()!= ""){
			sql += " and c.application = '"+timetableAppointment.getCDictionaryByApplication().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByEquipmentSituation()!= null && timetableAppointment.getCDictionaryByEquipmentSituation().getCName()!= ""){
			sql += " and c.equipment_situation = '"+timetableAppointment.getCDictionaryByEquipmentSituation().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByTidySituation()!= null && timetableAppointment.getCDictionaryByTidySituation().getCName()!= ""){
			sql += " and c.tidy_situation = '"+timetableAppointment.getCDictionaryByTidySituation().getCName()+"'";
		}
		//教师模糊查询
		if(!StringUtils.isEmpty(timetableAppointment.getConfirmRemark())){
			sql += " and c.cname like'%" + timetableAppointment.getConfirmRemark() + "%'";
		}
		Query query= entityManager.createNativeQuery(sql);
		int count = ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	//获取上课确认栏 列表数量
	@Override
	public int getViewLabroomTimetableRegistersConfirmCount(String courseNumber,int curr,int size){
		Calendar now = Calendar.getInstance();
		int weekday = shareService.getBelongsSchoolWeekday(now);//当前星期
		int week = shareService.getBelongsSchoolWeek(now);//当前周次
		if(weekday == 1 && week != 1){//若为周一则显示上个星期周五信息
			weekday = 5;
		}
		String sql = "select count(*) from view_labroom_appointment_register c where 1=1";
		if(!courseNumber.equals("")){
			sql += " and c.course_code = '"+courseNumber+"'";
		}
		sql += " and c.start_week <" + week;
		sql += " and c.confirmm_user is null";
		sql += " or ( c.weekday <" + weekday;
		sql += " and c.start_week = " + week +")";
		Query query= entityManager.createNativeQuery(sql);
		int count = ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}
	/*************************************************************************************
	 * @throws ParseException
	 * @內容：保存调出内容（调出类型为1   实验室内调整记录保存）
	 * @作者： 戴昊宇
	 * @日期：2017-11-22
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public TimetableAppointmentChange  saveAdjustMent2(HttpServletRequest request) throws ParseException {
		// 调整排课的实验室选择判断实验室匹配度
		String[] labRooms = null;
		if (request.getParameterValues("labRooms1") != null) {
			labRooms = request.getParameterValues("labRooms1");
		} else if (request.getParameterValues("labRooms2") != null) {
			labRooms = request.getParameterValues("labRooms2");
		}

		// 调整排课的授课教师选择
		String[] teachers = request.getParameterValues("teachers");
		// 项目
		String[] operationitems = request.getParameterValues("operationitems");

		String weekDay = request.getParameter("weekday");
		// 调整排课的星期选择
		String weeks = request.getParameter("weeks");
		// 调整排课的节次选择
		String[] classes = request.getParameterValues("classes");
		int[] intClasses = new int[classes.length];
		for (int i = 0; i < classes.length; i++) {
			intClasses[i] = Integer.parseInt(classes[i]);
		}

        String tappId = request.getParameter("id");
        TimetableAppointmentChange timetableAppointmentChange = new TimetableAppointmentChange();
        if(operationitems!=null){
    		OperationItem operationItem = operationItemDAO.findOperationItemById(Integer.parseInt(operationitems[0]));
    		 timetableAppointmentChange.setItemName(operationItem.getLpName());
    		}
        timetableAppointmentChange.setUser(shareService.getUserDetail());
        timetableAppointmentChange.setWeek(Integer.valueOf(weeks));
        timetableAppointmentChange.setWeekday(Integer.valueOf(weekDay));
        timetableAppointmentChange.setStartClass(intClasses[0]);
        timetableAppointmentChange.setEndClass(intClasses[classes.length-1]);
        timetableAppointmentChange.setTeacher(userDAO.findUserByUsername(teachers[0]).getCname());
        timetableAppointmentChange.setStatus(1);
        timetableAppointmentChange.setState(2);
        if(labRoomDAO.findLabRoomById(Integer.valueOf(labRooms[0]))!=null){
        timetableAppointmentChange.setLabRoom(labRoomDAO.findLabRoomById(Integer.valueOf(labRooms[0])));
        }
        if(labRooms!=null){
    		LabRoom labRoom = labRoomDAO.findLabRoomById(Integer.parseInt(labRooms[0]));
    		timetableAppointmentChange.setAddress(labRoom.getLabRoomName());
    		}

        // 调出课程类型(实训室内部调整)
        timetableAppointmentChange.setFlag(1);
        // 根据id获得timetableAppointment
        TimetableAppointment findTimetableAppointmentById = timetableAppointmentDAO.findTimetableAppointmentById(Integer.parseInt(tappId));
        // 被调出排课状态修改
        findTimetableAppointmentById.setEnabled(false);
      /*  // 1为调出中
        findTimetableAppointmentById.setCalloutType(1);*/
        timetableAppointmentDAO.store(findTimetableAppointmentById);
        timetableAppointmentDAO.flush();
        timetableAppointmentChange.setTimetableAppointment(findTimetableAppointmentById);
        timetableAppointmentChangeDAO.store(timetableAppointmentChange);
        timetableAppointmentChangeDAO.flush();
		return timetableAppointmentChange;
	}
	/****************************************************************************
	 *Description：查找所有调课申请
	 *@author：孙虎
	 *@date:2017-11-24
	 ****************************************************************************/
	public List<TimetableAppointmentChange> findAllTimetableAppointmentChange(TimetableAppointmentChange appointmentChange, Integer page, int pageSize,int tage,int isaudit){
		String sql="select l from TimetableAppointmentChange l where 1=1";
		//新加需求，是我的审核还是我的预约，isAudit  1审核2预约
		if(isaudit == 1){
			//实训部主任权限可以看到所有
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_PREEXTEACHING") != -1){

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
				// shi 判断登陆者 不是超级管理员，实验教务， 是不是实验室中心主任， 是的话下边权限不能进入
				int shi = 0;
				// 实验室中心主任，看到该中心下 自己实验室下边的实验室预约
				if (qun == 0
						&& (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_EXCENTERDIRECTOR") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_DEPARTMENTHEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_COLLEGELEADER") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_ASSETMANAGEMENT") != -1||SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
								.indexOf("ROLE_PRESECTEACHING") != -1
								)) {
					//ROLE_DEPARTMENTHEADER,ROLE_COLLEGELEADER,ROLE_ASSETMANAGEMENT为贺子龙  2015-11-28  新增
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
					if(shareService.getUser().getSchoolAcademy() != null){
						sql += " and l.user.schoolAcademy.academyNumber='" + shareService.getUser().getSchoolAcademy().getAcademyNumber() + "' ";
					}else{
						sql += " and l.id = -1";
					}
				}
				// 判断登陆者 不是超级管理员，实验教务 不是实验室中心主任，不是实验室管理员 ,不是系主任
				if (qun == 0 && shi == 0 && guan == 0 && xi ==0) {
					sql += " and l.id = -1";
				}
			}
		}else{
			sql += " and l.user.username='" + shareService.getUser().getUsername() + "' ";
		}

		// 1未审核
		if (tage == 1) {
			sql += " and l.status=1";
		}
		// 2审核中
		if (tage == 2) {
			sql += " and l.status=2";
		}
		// 3审核拒绝
		if (tage == 3) {
			sql += " and l.status=3";
		}
		// 4审核通过
		if (tage == 4) {
			sql += " and l.status=4";
		}
		sql += " order by l.id desc";
		return timetableAppointmentChangeDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}

	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * 内部调课三级审核：通过时cahnge表status=4 不通过对应的timetable.enable=false，对应的timetable.original_tapid对应的timetable.enable=true,timetable.original_tapid=null
	 * 外部调课一级审核：通过时cahnge表status=4 对应的timetable.enable=false，timetable.callout_type=2,不通过对应的timetable.callout_type=null
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException
	 * @date 2017-11-5
	 *************************************************************************************/
	@Transactional
	public TimetableAppointmentChange saveAuditResultForTimetable(TimetableAppointmentChange appointmentChange, TimetableAppointmentChangeAduit appointmentChangeAduit) throws NoSuchAlgorithmException {
		LabRoom labRoom = new LabRoom();
		TimetableAppointment appointment = appointmentChange.getTimetableAppointment();
		//判断内部调课还是外部调课
		if(appointmentChange.getFlag() ==1){
			labRoom = appointmentChange.getLabRoom();
		}else{
			Set<TimetableLabRelated> appId = appointment.getTimetableLabRelateds();
			for (Iterator iterator = appId.iterator(); iterator.hasNext();) {
				TimetableLabRelated timetableLabRelated = (TimetableLabRelated) iterator.next();
				labRoom = timetableLabRelated.getLabRoom();
			}
		}
		User user = shareService.getUserDetail();
		//消息
		Message message=new Message();
		message.setSendUser(user.getCname());
		message.setMessageState(0);//设置未读
		message.setCreateTime(Calendar.getInstance());
		message.setUsername(appointmentChange.getUser().getUsername());

		//审核结果
		Integer auditResult = Integer.valueOf(appointmentChangeAduit.getResult());

		if(appointmentChange.getState() == 2){//系主任审核结果保存
			if(auditResult == 0){//不通过
				appointmentChange.setStatus(3);
				appointmentChange.setState(6);
				message.setTitle("调课申请拒绝");
				//message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课被拒绝， <a href='/zjcclims/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2'>点击查看</a>");//消息内容
				message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课被拒绝， <a href='../timetableChangeAudit/checkButton?id="+appointmentChange.getId()+"&tage=0&state="+appointmentChange.getState()+"&page=1'>点击查看</a>");//消息内容
				message.setTage(3);
				appointment.setEnabled(false);
				TimetableAppointment appointment2 = timetableAppointmentDAO.findTimetableAppointmentById(appointment.getOriginalTapid());
				if(appointment2 != null){
					appointment2.setEnabled(true);
					appointment2.setOriginalTapid(null);
				}
				timetableAppointmentDAO.store(appointment);
				timetableAppointmentDAO.store(appointment2);
				timetableAppointmentDAO.flush();
			}else{//通过
				appointmentChange.setStatus(2);
				appointmentChange.setState(3);
				message.setTitle("调课申请系主任通过");
				//message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课系主任审核通过， <a href='/zjcclims/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2'>点击查看</a>");//消息内容
				message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课系主任审核通过， <a href='../timetableChangeAudit/checkButton?id="+appointmentChange.getId()+"&tage=0&state="+appointmentChange.getState()+"&page=1'>点击查看</a>");//消息内容
				message.setTage(1);
			}
			appointmentChangeAduit.setStatus(2);
			/*if(shareService.getUserDetail().getTelephone() != null){
				try {
					String result = shareService.sendMessage(shareService.getUserDetail().getTelephone(), message.getTitle());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			messageDAO.store(message);
			messageDAO.flush();
		}else if(appointmentChange.getState() == 3){//实训室管理员审核结果保存
			if(auditResult == 0){//不通过
				appointmentChange.setStatus(3);
				appointmentChange.setState(6);
				message.setTitle("调课申请拒绝");
				//message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课被拒绝， <a href='/zjcclims/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2'>点击查看</a>");//消息内容
				message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课被拒绝， <a href='../timetableChangeAudit/checkButton?id="+appointmentChange.getId()+"&tage=0&state="+appointmentChange.getState()+"&page=1'>点击查看</a>");//消息内容
				message.setTage(3);
				if(appointmentChange.getFlag() == 1){
					appointment.setEnabled(false);
					TimetableAppointment appointment2 = timetableAppointmentDAO.findTimetableAppointmentById(appointment.getOriginalTapid());
					if(appointment2 != null){
						appointment2.setEnabled(true);
						appointment2.setOriginalTapid(null);
						timetableAppointmentDAO.store(appointment);
						timetableAppointmentDAO.store(appointment2);
						timetableAppointmentDAO.flush();
					}
				}else{
					appointment.setCalloutType(null);
					timetableAppointmentDAO.store(appointment);
					timetableAppointmentDAO.flush();
				}
			}else{//通过
				//判断是内部调还是外部调
				if(appointmentChange.getFlag() == 1){
					appointmentChange.setStatus(2);
					appointmentChange.setState(5);
				}else{
					appointmentChange.setStatus(4);
					appointmentChange.setState(6);
				}
				message.setTitle("调课申请实训室管理员通过");
				//message.setContent("教学软件申请，您申请"+appointmentChange.getItemName()+" 的调课实训室管理员审核通过， <a href='/zjcclims/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2'>点击查看</a>");//消息内容
				message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课实训室管理员审核通过， <a href='../timetableChangeAudit/checkButton?id="+appointmentChange.getId()+"&tage=0&state="+appointmentChange.getState()+"&page=1'>点击查看</a>");//消息内容
				message.setTage(1);
				if(appointmentChange.getFlag() == 2){
					appointment.setEnabled(false);
					appointment.setCalloutType(2);
					timetableAppointmentDAO.store(appointment);
					timetableAppointmentDAO.flush();
				}
			}
			appointmentChangeAduit.setStatus(3);
			messageDAO.store(message);
			messageDAO.flush();
		}else if(appointmentChange.getState() == 5){//实训室部主任审核结果保存
			if(auditResult == 0){//不通过
				appointmentChange.setStatus(3);
				appointmentChange.setState(6);
				message.setTitle("调课申请拒绝");
				//message.setContent("调课申请，您申请"+appointmentChange.getItemName()+"的调课被拒绝， <a href='/zjcclims/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2'>点击查看</a>");//消息内容
				message.setContent("调课申请，您申请"+appointmentChange.getItemName()+"的调课被拒绝， <a href='../timetableChangeAudit/checkButton?id="+appointmentChange.getId()+"&tage=0&state="+appointmentChange.getState()+"&page=1'>点击查看</a>");//消息内容
				message.setTage(3);
				appointment.setEnabled(false);
				TimetableAppointment appointment2 = timetableAppointmentDAO.findTimetableAppointmentById(appointment.getOriginalTapid());
				if(appointment2 != null){
					appointment2.setEnabled(true);
					appointment2.setOriginalTapid(null);
					timetableAppointmentDAO.store(appointment);
					timetableAppointmentDAO.store(appointment2);
					timetableAppointmentDAO.flush();
				}
			}else{//通过
				appointmentChange.setStatus(4);
				appointmentChange.setState(6);
				message.setTitle("调课申请通过");
				//message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课通过， <a href='/zjcclims/timetable/timetableChangeAuditList?page=1&tage=0&isaudit=2'>点击查看</a>");//消息内容
				message.setContent("调课申请，您申请"+appointmentChange.getItemName()+" 的调课通过， <a href='../timetableChangeAudit/checkButton?id="+appointmentChange.getId()+"&tage=0&state="+appointmentChange.getState()+"&page=1'>点击查看</a>");//消息内容
				message.setTage(4);
			}
			appointmentChangeAduit.setStatus(5);
			messageDAO.store(message);
			messageDAO.flush();
		}

		appointmentChange = timetableAppointmentChangeDAO.store(appointmentChange);
		timetableAppointmentChangeDAO.flush();
		appointmentChangeAduit.setTimetableAppointmentChange(appointmentChange);
		appointmentChangeAduit.setUser(user);
		appointmentChangeAduit.setCreateDate(Calendar.getInstance());
		appointmentChangeAduitDAO.store(appointmentChangeAduit);
		appointmentChangeAduitDAO.flush();
		return appointmentChange;
	}


	//需要上课确认的数据推送到我的消息中
	public String saveMessageAppointment(int id){
		Message message= new Message();
		message.setSendUser(shareService.getUserDetail().getCname());//当前登录人
		message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());//当前登录人所在学院
		message.setTitle("课表执行上课确认");
		String content="请点击";
		content+="<a onclick='changeMessage(this)' href='../timetable/timetableAdminIframeConfirm?id=";
		content+=id;
		content+="&flag=1'>上课确认</a>";

		/*String content="申请成功，等待审核";
		content+="<a  href='/zjcclims/operation/viewOperationItem?operationItemId=";
		content+=oItem.getId();
		content+="&&flag=1&status=2'>点击查看</a>";*/
		message.setContent(content);
		message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
		message.setCreateTime(Calendar.getInstance());
		message.setUsername(shareService.getUserDetail().getUsername());
		message.setTage(5);
		message = messageDAO.store(message);
		return null;
	}

	/**
	 * Description 保存排课审核结果
	 * @param courseCode 选课组编号
	 * @param flag 排课类型（自主排课、教务排课等）
	 * @param status 审核前审核状态值
	 * @param result 审核结果
	 * @author 陈乐为 2018-3-7
	 */
	@Transactional
	public void saveTimetableAppAudit(String courseCode, int flag, int status, int result,String remark,int labroomId,int tag) {
		// 判断是否为自主排课；
		String sqlstring = "";
		// flag==0为教务排课，flag=1为自建排课
		if (flag >= 1 && flag <= 4) {
			sqlstring = "select c from TimetableAppointment c where c.schoolCourseDetail.schoolCourse.courseCode like '"
					+ courseCode + "'";
		} else {
			sqlstring = "select c from TimetableAppointment c where c.timetableSelfCourse.courseCode like '"
					+ courseCode + "'";
		}
		StringBuffer sql = new StringBuffer(sqlstring);
		List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO.executeQuery(sql.toString());
		int isResult=0;
		for (TimetableAppointment timetableAppointment : timetableAppointments) {
//			TimetableLabRelated timetableLabRelated = timetableAppointment.getTimetableLabRelateds().iterator().next();
			for(TimetableLabRelated timetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
				if (labroomId == timetableLabRelated.getLabRoom().getId()) {
					if (result == 1) { // 审核通过
						if (tag == 1) {// 一级审核
							timetableAppointment.setStatus(3);
							//标记为保存审核表
							isResult = 1;
						}
						if (tag == 2) {// 二级审核
							timetableAppointment.setStatus(1);
							isResult = 2;
						}
					} else {
						timetableAppointment.setStatus(4);
						if (tag == 1) {
							isResult = 3;
						}
						if (tag == 2) {
							isResult = 4;
						}
					}
				}
			}
			timetableAppointmentDAO.store(timetableAppointment);

		}
		if(isResult==1){
			//一级审核通过过程
			TimetableAppointmentResult tar= new TimetableAppointmentResult();
			tar.setAuditResult("1");
			tar.setCoursecode(courseCode);
			tar.setLabroomId(labroomId);
			tar.setUser(shareService.getUser().getUsername());
			tar.setRemark(remark);
			tar.setTag(1);
			timetableAppointmentResultDAO.store(tar);
		}
		if(isResult==2){
			//二级审核通过过程
			TimetableAppointmentResult tar= new TimetableAppointmentResult();
			tar.setAuditResult("1");
			tar.setCoursecode(courseCode);
			tar.setLabroomId(labroomId);
			tar.setUser(shareService.getUser().getUsername());
			tar.setRemark(remark);
			tar.setTag(2);
			timetableAppointmentResultDAO.store(tar);
			// 插入评教数据
			evaluationService.insertIntoEvaluationSource(timetableAppointments);
		}
		if(isResult==3){
			//一级审核不通过过程
			TimetableAppointmentResult tar= new TimetableAppointmentResult();
			tar.setAuditResult("2");
			tar.setCoursecode(courseCode);
			tar.setLabroomId(labroomId);
			tar.setUser(shareService.getUser().getUsername());
			tar.setRemark(remark);
			tar.setTag(1);
			timetableAppointmentResultDAO.store(tar);
		}
		if(isResult==4){
			//二级审核不通过过程
			TimetableAppointmentResult tar= new TimetableAppointmentResult();
			tar.setAuditResult("2");
			tar.setCoursecode(courseCode);
			tar.setLabroomId(labroomId);
			tar.setUser(shareService.getUser().getUsername());
			tar.setRemark(remark);
			tar.setTag(2);
			timetableAppointmentResultDAO.store(tar);
		}

	}

	/************************************************************

	 * @功能：判断排课是否冲突
	 * @作者：张德冰
	 * @日期：2018.03.30
	 ************************************************************/
	@Transactional
	public int getSzie(Integer term, int[] classes, int[] weeks,
			Integer weekday,String[] teachers, String courseCode){
		String hql = "select c from TimetableAppointment c where 1=1 and c.schoolCourse.schoolTerm.id ="+ term;
		hql += " and c.courseCode = '"+ courseCode + "'";
		hql += " and c.weekday = "+weekday;
		hql += " and (c.startWeek between " + weeks[0] + " and "
				+ weeks[weeks.length - 1] + " or c.endWeek between "
				+ weeks[0] + " and " + weeks[weeks.length - 1]+")";
		hql += " and (c.startClass between " + classes[0] + " and "
				+ classes[classes.length - 1] + " or c.endClass between "
				+ classes[0] + " and " + classes[classes.length - 1]+")";
		for(int i=0;i < teachers.length;i++){
			hql += " and c.createdBy = '"+ teachers[i]+"'";
		}
		List<TimetableAppointment> list = timetableAppointmentDAO.executeQuery(hql);
		return list.size();
	}
	/************************************************************

	 * @功能：查看实验室管理员
	 * @作者：戴昊宇
	 * @日期：2018.06.13
	 ************************************************************/
	public List<LabRoomAdmin> getlabRoomAdmin(int labroomId){
		String hql="select c from LabRoomAdmin c where c.labRoom.id="+labroomId;
		List<LabRoomAdmin> list = labRoomAdminDAO.executeQuery(hql);
		return list;
	}
	/************************************************************

	 * @功能：查看实验室管理员
	 * @作者：戴昊宇
	 * @日期：2018.06.13
	 ************************************************************/
	public List<TimetableLabRelated> getlabRelated(int id){
		String hql="select c from TimetableLabRelated c where c.id="+id;
		List<TimetableLabRelated> list = timetableLabRelatedDAO.executeQuery(hql);
		return list;
	}

	/**
	 * 实验室自主排课记录-getSelfListLabTimetableAppointments方法已乱
	 * @param request
	 * @param term
	 * @return
	 * @author 陈乐为 2018-7-28
	 */
	public List<TimetableLabRelated> getSelfCourseLabTimetableAppointments(
			HttpServletRequest request, int term) {
		User user = shareService.getUserDetail();
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select c from TimetableLabRelated c where "+
						" c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" +term);
		if (request.getParameter("labroom") != null && !request.getParameter("labroom").equals("-1")) {
			sql.append(" and c.labRoom.id = " + request.getParameter("labroom"));
		}
		// 教师只查自己的课程安排
		if(request.getSession().getAttribute("selected_role").toString().indexOf("ROLE_TEACHER")!=-1) {
			sql.append(" and c.timetableAppointment.timetableSelfCourse.user.username = '"+user.getUsername()+"'");
		}
		List<TimetableLabRelated> timetableLabRelated2s = timetableLabRelatedDAO.executeQuery(sql.toString());
		List<TimetableLabRelated> timetableLabRelateds = new ArrayList<TimetableLabRelated>();
		if(timetableLabRelated2s!=null){
			timetableLabRelateds.addAll(timetableLabRelated2s);
		}
		return timetableLabRelateds;
	}

	/************************************************************

	 * @功能：查看自主排课关联实验室
	 * @作者：杨新蔚
	 * @日期：2018/08/31
	 ************************************************************/
	public List<TimetableLabRelated> getlabRelatedByAppointmentID(int id){
		String sql = "select c from TimetableLabRelated c where c.timetableAppointment.id =" +id;
		List<TimetableLabRelated> timetableLabRelated = timetableLabRelatedDAO.executeQuery(sql.toString());
		return timetableLabRelated;
	}

	/***********************************************************************************************
	 * Description：排课模块{保存教务实验课}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-31
	 ***********************************************************************************************/
	public TimetableAppointment saveSchoolTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
													int weekday, String teachers, String courseNo, Integer isOther,String teachers2,int[] items){

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
			SchoolCourse course = schoolCourseDAO.findSchoolCourseByPrimaryKey(courseNo);
			String courseNumber = course.getSchoolCourseInfo().getCourseNumber();
			// 保存排课主表
			TimetableAppointment timetableAppointment = new TimetableAppointment();
			// 保存操作时间
			timetableAppointment.setCreatedDate(Calendar.getInstance());
			timetableAppointment.setUpdatedDate(Calendar.getInstance());
			// 设置排课的选课组编号
			timetableAppointment.setCourseCode(course.getCourseCode());
			timetableAppointment.setSchoolCourse(course);
			timetableAppointment.setSchoolCourseInfo(course.getSchoolCourseInfo());
			if(course != null && course.getSchoolCourseDetails() != null && course.getSchoolCourseDetails().size() > 0)
			{
				for(SchoolCourseDetail s:course.getSchoolCourseDetails()){
					timetableAppointment.setSchoolCourseDetail(s);
					timetableAppointment.setAppointmentNo(s.getCourseDetailNo());
				}

			}

			// 生成主表公用数据
			timetableAppointment = this.saveTimetableMain(timetableAppointment, sWeek, sClasses, weekday);
			// 保存排课类型为教务不分批
			if (!EmptyUtil.isIntegerEmpty(isOther) && isOther.equals(1)) {// 其他时间段
				timetableAppointment.setTimetableStyle(3);
			}else {// 教务安排时间段
				timetableAppointment.setTimetableStyle(2);
			}
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
			if (!EmptyUtil.isStringEmpty(teachers2)) {
				this.saveTimetableTeacherRelated(timetableAppointment,teachers2);
			}
			// 3 保存项目
			this.saveTimetableItemRelated(timetableAppointment, items);
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
	 * Description：排课保存公用方法{保存项目}
	 * @author： 贺子龙
	 * @Date：2016-09-01
	 *************************************************************************************/
	public void saveTimetableItemRelated(TimetableAppointment timetableAppointment, int[] items){
		// 新建关联表
		TimetableItemRelated timetableItemRelated = new TimetableItemRelated();
		// 保存关联表
		if (items!=null&&items.length > 0) {
			for (int i = 0; i < items.length; i++) {
				timetableItemRelated.setOperationItem(operationItemDAO.findOperationItemById(items[i]));
				timetableItemRelated.setTimetableAppointment(timetableAppointment);
				timetableItemRelatedDAO.store(timetableItemRelated);
				timetableItemRelatedDAO.flush();
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
		sql+=" and t.timetableStyle="+2;
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

	/***********************************************************************************************
	 * Description：查询循环排课
	 * @author：戴昊宇
	 * @Date：2017-08-25
	 ***********************************************************************************************/
	public List<TimetableAppointment> findCycleTimetableAppointmentByCourseNo(String courseNo){
		// 建立查询
		String sql = "select t from TimetableAppointment t where 1=1";
		// 限制条件
		sql+=" and t.courseCode like '"+courseNo+"'";
		sql+=" and t.timetableStyle="+9;
		sql+=" order by t.startWeek,t.weekday";
		// 执行查询
		List<TimetableAppointment> appointments = timetableAppointmentDAO.executeQuery(sql, 0, -1);
		// 返回结果
		return appointments;
	}

	/***********************************************************************************************
	 * Description：查询循环分批排课
	 * @author：戴昊宇
	 * @Date：2018-03-07
	 ***********************************************************************************************/
	public List<TimetableAppointment> findCycleBatchTimetableAppointmentByCourseNo(String courseNo){
		// 建立查询
		String sql = "select t from TimetableAppointment t where 1=1";
		// 限制条件
		sql+=" and t.courseCode like '"+courseNo+"'";
		sql+=" and t.timetableStyle="+10;
		sql+=" order by t.startWeek,t.weekday";
		// 执行查询
		List<TimetableAppointment> appointments = timetableAppointmentDAO.executeQuery(sql, 0, -1);
		// 返回结果
		return appointments;
	}


	/***********************************************************************************************
	 * Description：排课模块通用{根据选课组编号和分组id获取排课记录}
	 * @author：贺子龙
	 * @Date：2016-08-02
	 ***********************************************************************************************/
	public List<TimetableAppointment> findTimetableAppointmentByCourseAndGroup(String courseNo, int groupId){
		// 建立查询
		String sql = "select t from TimetableAppointment t left join t.timetableGroups tg where 1=1";
		// 限制条件
		sql+=" and t.courseCode like '"+courseNo+"'";
		sql+=" and tg.id = "+groupId;
		// 执行查询
		List<TimetableAppointment> appointments = timetableAppointmentDAO.executeQuery(sql, 0, -1);
		// 返回结果
		return appointments;
	}


	/**
	 * 查询所有学生
	 * @param user 查询对象
	 * @param page 页数
	 * @param pageSize 查询页面大小
	 * @return 符合条件的学生对象列表
	 * @author 黄保钱 2018-9-18
	 */
	@Override
	public List<User> findStudent(User user, Integer page, Integer pageSize, Integer id){
		String sql="select u from User u where 1=1";
		if(user!=null){
			if(user.getCname()!=null&&!user.getCname().equals("")){
				sql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			if(user.getUsername()!=null&&!user.getUsername().equals("")){
				sql+=" and u.username like '%"+user.getUsername()+"%'";
			}
		}
		sql+=" and u.username not in(select a.user.username from TimetableGroupStudents a where a.timetableGroup.id="+id+")";
		sql += " and u.userRole = 0";
		return userDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	/**
	 * 根据周次和星期查询日期
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<SchoolWeek> getDate(int week,int weekday){
		String sql=" select s from schoolWeek s where 1=1";
		sql += " and s.week=" + week;
		sql += " and s.weekday=" + weekday;
		return schoolWeekDAO.executeQuery(sql,0,-1);
	}
	/**
	 * 查询学生数量
	 * @param user 查询对象
	 * @return 符合条件的所有学生对象数量
	 * @author 黄保钱 2018-9-18
	 */
	@Override
	public Integer countStudent(User user, Integer id) {
		String sql = "select count(*) from User u where 1=1";
		if (user != null) {
			if (user.getCname() != null && !user.getCname().equals("")) {
				sql += " and u.cname like '%" + user.getCname() + "%'";
			}
			if (user.getUsername() != null && !user.getUsername().equals("")) {
				sql += " and u.username like '%" + user.getUsername() + "%'";
			}
		}
		sql+=" and u.username not in(select a.user.username from TimetableGroupStudents a where a.timetableGroup.id="+id+")";
		sql += " and u.userRole = 0";
		return ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/**
	 * 查询老师的课程
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableTeacherRelated> getTeacherTimetable(TimetableAppointment timetableAppointment){
		User user = shareService.getUser();
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String sql=" select t from TimetableTeacherRelated t " +
				" join  t.timetableAppointment.timetableAppointmentSameNumbers tas" +
				" where t.user.username= '"+ user.getUsername()+"'"+
				" and t.timetableAppointment.schoolCourseDetail.schoolTerm.id="+schoolTerm.getId();
		if(timetableAppointment!=null
		       &&timetableAppointment.getSchoolCourseDetail()!=null
				&&timetableAppointment.getSchoolCourseDetail().getSchoolCourse()!=null) {
			sql += " and t.timetableAppointment.schoolCourseDetail.schoolCourse.courseName " +
					" like'%" + timetableAppointment.getSchoolCourseDetail().getSchoolCourse().getCourseName() + "%'";
		}
		if(timetableAppointment!=null
				&& timetableAppointment.getStartWeek()!=null) {
			sql += " and tas.startWeek <=" + timetableAppointment.getStartWeek();
			sql += " and tas.endWeek >=" + timetableAppointment.getStartWeek();

		}
		// 根据权限更改查询方式
		SimpleGrantedAuthority sga = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
		if(sga.getAuthority().contains("ROLE_SUPERADMIN")){
			sql=" select t from TimetableTeacherRelated t " +
					" join t.timetableAppointment.timetableAppointmentSameNumbers tas" +
					" where"+
					" t.timetableAppointment.schoolCourseDetail.schoolTerm.id="+schoolTerm.getId();
			if(timetableAppointment!=null
					&&timetableAppointment.getSchoolCourseDetail()!=null
					&&timetableAppointment.getSchoolCourseDetail().getSchoolCourse()!=null) {
				sql += " and t.timetableAppointment.schoolCourseDetail.schoolCourse.courseName " +
						" like'%" + timetableAppointment.getSchoolCourseDetail().getSchoolCourse().getCourseName() + "%'";
			}
			if(timetableAppointment!=null&&
					timetableAppointment.getStartWeek()!=null) {
				sql += " and tas.startWeek <=" + timetableAppointment.getStartWeek();
				sql += " and tas.endWeek >=" + timetableAppointment.getStartWeek();

			}
		}
		if(sga.getAuthority().contains("ROLE_COLLEGELEADER")
				|| sga.getAuthority().contains("ROLE_EXCENTERDIRECTOR")
				|| sga.getAuthority().contains("ROLE_CFO")){
			sql=    " select t from TimetableTeacherRelated t " +
					" join t.timetableAppointment.timetableAppointmentSameNumbers tas" +
					" where"+
					" t.timetableAppointment.schoolCourseDetail.schoolTerm.id="+schoolTerm.getId();
			sql+=" and t.timetableAppointment.schoolCourseDetail.schoolAcademy.academyNumber="+user.getSchoolAcademy().getAcademyNumber();
			if(timetableAppointment!=null
					&&timetableAppointment.getSchoolCourseDetail()!=null
					&&timetableAppointment.getSchoolCourseDetail().getSchoolCourse()!=null) {
				sql += " and t.timetableAppointment.schoolCourseDetail.schoolCourse.courseName " +
						" like'%" + timetableAppointment.getSchoolCourseDetail().getSchoolCourse().getCourseName() + "%'";
			}
			if(timetableAppointment!=null&&
					timetableAppointment.getStartWeek()!=null) {
				sql += " and tas.startWeek <=" + timetableAppointment.getStartWeek();
				sql += " and tas.endWeek >=" + timetableAppointment.getStartWeek();

			}
		}
		// 根据当前用户去查找教师的预约课程的id的集合
		List<TimetableTeacherRelated> timetableTeacherRelated = timetableTeacherRelatedDAO
				.executeQuery(sql);
		return timetableTeacherRelated;
	}
	/**
	 * 查询实验室的课程
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableLabRelated> getLabTimetable(TimetableAppointment timetableAppointment){
		SchoolTerm schoolTerm = shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String sql=" select t from TimetableLabRelated t" +
				" join t.timetableAppointment.timetableAppointmentSameNumbers tas" +
				" where t.timetableAppointment.schoolCourseDetail.schoolTerm.id="+schoolTerm.getId();
		if(timetableAppointment!=null
				&&timetableAppointment.getSchoolCourseDetail()!=null
				&&timetableAppointment.getSchoolCourseDetail().getSchoolCourse()!=null) {
			sql += " and t.timetableAppointment.schoolCourseDetail.schoolCourse.courseName " +
					" like'%" + timetableAppointment.getSchoolCourseDetail().getSchoolCourse().getCourseName() + "%'";
		}
		if(timetableAppointment!=null&&
				timetableAppointment.getStartWeek()!=null) {
			sql += " and tas.startWeek <=" + timetableAppointment.getStartWeek();
			sql += " and tas.endWeek >=" + timetableAppointment.getStartWeek();

		}
		List<TimetableLabRelated> timetableLabRelated=timetableLabRelatedDAO.executeQuery(sql,0,-1);
		return  timetableLabRelated;
	}
	/**
	 * 查询学生的课程
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableGroupStudents> getStudentTimetable(TimetableAppointment timetableAppointment){
		String sql = "select t from TimetableGroupStudents t where  t.user.username like '"
				+ shareService.getUserDetail().getUsername() + "' ";
		if(timetableAppointment!=null
				&&timetableAppointment.getSchoolCourseDetail()!=null
				&&timetableAppointment.getSchoolCourseDetail().getSchoolCourse()!=null) {
			sql += " and t.timetableGroup.timetableAppointments.schoolCourseDetail.schoolCourse.courseName " +
					" like'%" + timetableAppointment.getSchoolCourseDetail().getSchoolCourse().getCourseName() + "%'";
		}
		List<TimetableGroupStudents> tass = timetableGroupStudentsDAO.executeQuery(sql, 0, -1);
		return tass;
	}
	/**
	 * 根据登录人查询排课
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<SchoolCourseStudent> getSchoolCourseStudent(){
		String sql = "select  c from SchoolCourseStudent c where c.userByStudentNumber.username like '"
				+ shareService.getUserDetail().getUsername() + "' and c.state=1 ";
		List<SchoolCourseStudent> tas = schoolCourseStudentDAO.executeQuery(sql, 0, -1);
		return tas;
	}
	/**
	 * 根据登录人查询自主排课
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableCourseStudent> getTimetableCourseStudent(){
		String sql = "select  c from TimetableCourseStudent c where c.user.username like '"
				+ shareService.getUserDetail().getUsername() + "'";
		List<TimetableCourseStudent> tase = timetableCourseStudentDAO.executeQuery(sql, 0, -1);
		return tase;
	}
	/**
	 * 查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-1
	 */
	public List<Object[]> getTimetable(String date,String courseName,String weeks) {
	    User user=shareService.getUser();
		SchoolTerm schoolTerm =shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String sql = " SELECT * FROM report_schedule rs where 1=1";
		if (courseName!=null){
			sql+=" AND rs.course_name like'%"+courseName+ "%'";
	    }
	    if(weeks!=null&&!"".equals(weeks)) {
			sql+=" AND rs.weeks='" +weeks+"'";
		}
        if(date!=null&&!"".equals(date)){
			sql+=" AND rs.class_date='"+date+"'";
		}
		if("1".equals(user.getUserRole())){
		    sql+=" AND rs.job_no='"+user.getUsername()+"'";
        }else if("0".equals(user.getUserRole())){
		    sql+=" AND rs.username='"+user.getUsername()+"'";
        }
        sql+=" AND rs.term_id="+schoolTerm.getId();
		sql+=" group by rs.weeks,rs.course_no,rs.start_class,rs.end_class,rs.lab_name,rs.job_no";
		sql+=" order by rs.class_date,rs.weeks,rs.weekday,rs.start_class asc ";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
		return list;
	}
	/**
	 * 根据当前时间和学院查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-1
	 */
	public List<Object[]> getTimetableByDateAndSchoolAcaemy(String date,String acno) {
		SchoolTerm schoolTerm =shareService.getBelongsSchoolTerm(Calendar.getInstance());
		String sql = " SELECT * FROM report_schedule rs where 1=1";
		if(acno!=null){
			sql+=" and rs.academy_number='"+acno+"'";
		}
		if(date!=null){
			sql+=" AND rs.class_date='"+date+"'";
		}
		sql+=" AND rs.term_id="+schoolTerm.getId();
		sql+=" group by rs.weeks,rs.course_no,rs.start_class,rs.end_class";
		sql+=" order by rs.class_date  asc ";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
		return list;
	}
    /**
     * 根据当前周和学院查询课表数据
     * @return 符合条件的所有对象数量
     * @author 廖文辉 2018-11-2
     */
    public List<Object[]> getCurrentWeekTimetable(String Firstdate,String Lastdate,String acno,String weeks){
		SchoolTerm schoolTerm =shareService.getBelongsSchoolTerm(Calendar.getInstance());
        String sql = " SELECT * FROM report_schedule rs where 1=1";
        if(acno!=null){
            sql+=" and rs.academy_number='"+acno+"'";
        }
        if(weeks!=null&&!"".equals(weeks)) {
            sql+=" AND rs.weeks='" +weeks+"'";
        }
        if(Firstdate!=null&&Lastdate!=null){
            sql+=" AND rs.class_date between'"+Firstdate+"'and '"+Lastdate+"'";
        }
		sql+=" AND rs.term_id="+schoolTerm.getId();
		sql+=" group by rs.weeks,rs.course_no,rs.start_class,rs.end_class";
        sql+=" order by rs.class_date,rs.weeks,rs.weekday,rs.start_class asc ";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
        return list;
    }
	/**
	 * 根据当前学期和学院查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-2
	 */
	public List<Object[]> getCurrentTermTimetable(SchoolTerm schoolTerm,String acno){
		String sql = " SELECT * FROM report_schedule rs where 1=1";
		if(acno!=null){
			sql+=" and rs.academy_number='"+acno+"'";
		}
		if(schoolTerm!=null){
			sql+=" and rs.term_id="+schoolTerm.getId();
		}
		sql+=" group by rs.weeks,rs.course_no,rs.start_class,rs.end_class";
		sql+=" order by rs.class_date,rs.weeks,rs.weekday,rs.start_class asc ";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
		return list;
	}
	/**
	 * 根据当前周和学生查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-2
	 */
	public List<Object[]> getStudentCurrWeekTimetable(String weeks){
		User user=shareService.getUser();
		String sql = " SELECT rs.course_name," +
				" GROUP_concat(distinct rs.classes)," +
				" rs.weekday," +
				" rs.weeks," +
				" rs.start_class," +
				" rs.end_class," +
				" GROUP_concat(distinct rs.lab_name)," +
				" GROUP_concat(distinct rs.teacher)," +
				" rs.course_no," +
				" 0," +	//课程开始时间占位符
				" 0" +	//课程结束时间占位符
				" FROM report_schedule rs where 1=1";
		int term=shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		sql+=" AND rs.term_id="+term;
		if(weeks!=null&&!"".equals(weeks)) {
			sql+=" AND rs.weeks='" +weeks+"'";
		}
		if("0".equals(user.getUserRole())){
			sql+=" and rs.username='"+user.getUsername()+"'";
		}else if("1".equals(user.getUserRole())){
			sql+=" and rs.job_no='"+user.getUsername()+"'";
		}
		sql+=" group by rs.course_no,rs.start_class,rs.end_class,rs.weeks,rs.weekday";
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> list =new ArrayList<Object[]>(query.getResultList());

		//遍历list，为每节课添加开始时间和结束时间（yyyy-MM-dd HH:mm:ss）
		for(Object[] course : list) {
			try {
				//根据学期，第几周，周几，开使节次，结束节次确定时间
				String formatSQL = "SELECT DATE_FORMAT(date,'%%Y-%%m-%%d') AS date FROM school_week WHERE term_id = %s AND `week` = %s AND weekday = %s";
				query = entityManager.createNativeQuery(String.format(formatSQL, term, weeks, course[2].toString()));
				Object dateString = query.getSingleResult();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				formatSQL = "SELECT start_date FROM system_time WHERE section = %s";
				query = entityManager.createNativeQuery(String.format(formatSQL, course[4].toString()));
				Time startTime = (Time) query.getSingleResult();
				formatSQL = "SELECT end_date FROM system_time WHERE section = %s";
				query = entityManager.createNativeQuery(String.format(formatSQL, course[5]));
				Time endTime = (Time)query.getSingleResult();

				Date startDate = simpleDateFormat.parse(dateString.toString() + " " + startTime.toString());
				Date endDate = simpleDateFormat.parse(dateString.toString() + " " + endTime.toString());

				course[9] = startDate; course[10] = endDate;
			} catch (ParseException e) {
				e.printStackTrace();;
			}
		}
		return list;
	}

	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：廖文辉
	 * @日期：2018-11-09
	 *************************************************************************************/
	@Override
	public List<Object[]> findViewLabroomTimetableRegisters(TimetableAppointment timetableAppointment,int curr,int size,HttpServletRequest request,String acno){
		String sql = "select * from view_labroom_appointment_register c where 1=1";
		// 根据权限等级筛选
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==6){
			sql+=" and c.lab_room_admin='"+shareService.getUserDetail().getUsername()+"'";
		}else if(authLevel==5) {
			sql += " and c.center_manager='" + shareService.getUserDetail().getUsername() + "'";
		}else if(authLevel==3||authLevel==4){
			sql+=" and c.academy_number='"+acno+"'";
		}
		if (auth.equals("ROLE_TEACHER")){
			sql+=" and c.cname='"+shareService.getUserDetail().getUsername()+"'";
		}
		if (timetableAppointment.getCDictionaryByObject()!= null && timetableAppointment.getCDictionaryByObject().getCName() != ""){
			sql += " and c.object = '"+timetableAppointment.getCDictionaryByObject().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByApplication()!= null && timetableAppointment.getCDictionaryByApplication().getCName()!= ""){
			sql += " and c.application = '"+timetableAppointment.getCDictionaryByApplication().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByEquipmentSituation()!= null && timetableAppointment.getCDictionaryByEquipmentSituation().getCName()!= ""){
			sql += " and c.equipment_situation = '"+timetableAppointment.getCDictionaryByEquipmentSituation().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByTidySituation()!= null && timetableAppointment.getCDictionaryByTidySituation().getCName()!= ""){
			sql += " and c.tidy_situation = '"+timetableAppointment.getCDictionaryByTidySituation().getCName()+"'";
		}
		// 查询条件
		if(timetableAppointment.getConfirmRemark()!=null&timetableAppointment.getConfirmRemark()!=""){
			sql +=" and (c.confirm_remark like '%"+ timetableAppointment.getConfirmRemark() +"%'" +
					" or c.course_name like '%"+ timetableAppointment.getConfirmRemark() +"%'" +
					" or c.cname like '%"+ timetableAppointment.getConfirmRemark() +"%'" +
					" or c.lab_room_name like '%"+ timetableAppointment.getConfirmRemark() +"%')";
		}
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term_id="+term;
		sql+=" order by c.week";
		Query query = entityManager.createNativeQuery(sql);
		query.setMaxResults(size);
		query.setFirstResult(curr*size);
		List<Object[]> list= query.getResultList();
		return list;
	}

	/**
	 * Description 计数
	 * @param timetableAppointment
	 * @param request
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-12-25
	 */
	@Override
	public int findTimetableRegisterCounts(TimetableAppointment timetableAppointment,HttpServletRequest request,String acno){
		String sql = "select count(*) from view_labroom_appointment_register c where 1=1";
		// 根据权限等级筛选
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==6){
			sql+=" and c.lab_room_admin='"+shareService.getUserDetail().getUsername()+"'";
		}else if(authLevel==5) {
			sql += " and c.center_manager='" + shareService.getUserDetail().getUsername() + "'";
		}else if(authLevel==3||authLevel==4){
			sql+=" and c.academy_number='"+acno+"'";
		}
		if (auth.equals("ROLE_TEACHER")){
			sql+=" and c.cname='"+shareService.getUserDetail().getUsername()+"'";
		}
		if (timetableAppointment.getCDictionaryByObject()!= null && timetableAppointment.getCDictionaryByObject().getCName() != ""){
			sql += " and c.object = '"+timetableAppointment.getCDictionaryByObject().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByApplication()!= null && timetableAppointment.getCDictionaryByApplication().getCName()!= ""){
			sql += " and c.application = '"+timetableAppointment.getCDictionaryByApplication().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByEquipmentSituation()!= null && timetableAppointment.getCDictionaryByEquipmentSituation().getCName()!= ""){
			sql += " and c.equipment_situation = '"+timetableAppointment.getCDictionaryByEquipmentSituation().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByTidySituation()!= null && timetableAppointment.getCDictionaryByTidySituation().getCName()!= ""){
			sql += " and c.tidy_situation = '"+timetableAppointment.getCDictionaryByTidySituation().getCName()+"'";
		}
		// 查询条件
		if(timetableAppointment.getConfirmRemark()!=null&timetableAppointment.getConfirmRemark()!=""){
			sql +=" and (c.confirm_remark like '%"+ timetableAppointment.getConfirmRemark() +"%'" +
					" or c.course_name like '%"+ timetableAppointment.getConfirmRemark() +"%'" +
					" or c.cname like '%"+ timetableAppointment.getConfirmRemark() +"%'" +
					" or c.lab_room_name like '%"+ timetableAppointment.getConfirmRemark() +"%')";
		}
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term_id="+term;
		Query query = entityManager.createNativeQuery(sql);
		System.out.println(sql);
		BigInteger count = (BigInteger) query.getSingleResult();
		return count.intValue();
	}

	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：廖文辉
	 * @日期：2018-11-09
	 *************************************************************************************/
	@Override
	public int findViewLabroomTimetableRegistersCount(TimetableAppointment timetableAppointment,HttpServletRequest request,String acno){
		String sql = "select count(*) from view_labroom_appointment_register c where 1=1";
		// 根据权限等级筛选
		String auth = request.getSession().getAttribute("selected_role").toString();
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==6){
			sql+=" and c.lab_room_admin='"+shareService.getUserDetail().getUsername()+"'";
		}else if(authLevel==5) {
			sql += " and c.center_manager='" + shareService.getUserDetail().getUsername() + "'";
		}else if(authLevel==3||authLevel==4){
			sql+=" and c.academy_number='"+acno+"'";
		}
		if (timetableAppointment.getSchoolCourseInfo() != null && timetableAppointment.getSchoolCourseInfo().getCourseName() != ""){
			sql += " and c.course_name like '%"+timetableAppointment.getSchoolCourseInfo().getCourseName()+"%'";
		}
		if (timetableAppointment.getCDictionaryByObject()!= null && timetableAppointment.getCDictionaryByObject().getCName() != ""){
			sql += " and c.object = '"+timetableAppointment.getCDictionaryByObject().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByApplication()!= null && timetableAppointment.getCDictionaryByApplication().getCName()!= ""){
			sql += " and c.application = '"+timetableAppointment.getCDictionaryByApplication().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByEquipmentSituation()!= null && timetableAppointment.getCDictionaryByEquipmentSituation().getCName()!= ""){
			sql += " and c.equipment_situation = '"+timetableAppointment.getCDictionaryByEquipmentSituation().getCName()+"'";
		}
		if (timetableAppointment.getCDictionaryByTidySituation()!= null && timetableAppointment.getCDictionaryByTidySituation().getCName()!= ""){
			sql += " and c.tidy_situation = '"+timetableAppointment.getCDictionaryByTidySituation().getCName()+"'";
		}
		if(timetableAppointment.getConfirmRemark()!=null&timetableAppointment.getConfirmRemark()!=""){
			sql +=" and c.confirm_remark='"+timetableAppointment.getConfirmRemark()+"'";
		}
		if(timetableAppointment.getCreatedBy()!=null&&!"".equals(timetableAppointment.getCreatedBy())){
			sql +=" and c.cname like '%"+timetableAppointment.getCreatedBy()+"%'";
		}
		String labRooms =request.getParameter("labRoom");
		if(labRooms!=null&&!"".equals(labRooms)){
			LabRoom labRoom=labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(labRooms));
			sql +=" and c.lab_room_name ='"+labRoom.getLabRoomName()+"'";
		}
		int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
		if (request.getParameter("term")!=null&&!request.getParameter("term").equals("")) {
			term = Integer.parseInt(request.getParameter("term"));
		}
		sql+=" and c.term_id="+term;
		Query query= entityManager.createNativeQuery(sql);
		int count = ((BigInteger)query.getSingleResult()).intValue();
		return count;
	}

	/**
	 * @Description 根据日期和实验室查询课表数据
	 * @author 张德冰
	 * @date 2018-11-21
	 */
	@Override
	public List<Object[]> getLabCurrDayTimetable(HttpServletRequest request,Integer currpage,Integer pagesize) {
		String sql = " SELECT * FROM view_lab_table_reservation l where 1=1";
		sql+=" and l.course_name !='实验室预约'";

		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置格式
		String time =request.getParameter("time");
		if(time != null && !"".equals(time)){
			Date datetime = null;
			try {
				datetime = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			date.setTime(datetime);
		}
		//获取当前学期
		String term = shareService.getBelongsSchoolTerm(date).getId().toString();
		sql+=" and l.term='"+term+"'";
		//获取当前周
		String schoolWeek = String.valueOf(shareService.getBelongsSchoolWeek(date));
		sql += " and l.start_week <='"+schoolWeek+"'";
		sql += " and l.end_week >='"+schoolWeek+"'";
		//获取当前星期
		String weekday=String.valueOf(shareService.getBelongsSchoolWeekday(date));
		sql+=" and l.weekday='"+weekday+"'";

		String labRooms =request.getParameter("labRoom");
		if(labRooms!=null&&!"".equals(labRooms)){
			LabRoom labRoom=labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(labRooms));
			sql +=" and l.lab_room_name like'%"+labRoom.getLabRoomName()+"%'";
		}

		Query query= entityManager.createNativeQuery(sql);
		if(pagesize != 0) {
			query.setMaxResults(pagesize);
			query.setFirstResult((currpage-1)*pagesize);
		}
		List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
		return list;
	}

	/**
	 * @Description 根据日期和实验室查询预约结果
	 * @author 张德冰
	 * @date 2018-11-21
	 */
	@Override
	public List<Object[]> getLabCurrDayReservation(HttpServletRequest request,Integer currpage,Integer pagesize){
		String sql = " SELECT * FROM view_lab_table_reservation l where 1=1";
		sql+=" and l.course_name ='实验室预约'";
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 设置格式
		String Currentdate = sdf.format(date.getTime());
		String time =request.getParameter("time");
		if(time != null && !"".equals(time)){
			Currentdate = time;
		}
		sql +=" and l.term ='"+Currentdate+"'";

		String labRooms =request.getParameter("labRoom");
		if(labRooms!=null&&!"".equals(labRooms)){
			LabRoom labRoom=labRoomDAO.findLabRoomByPrimaryKey(Integer.parseInt(labRooms));
			sql +=" and l.lab_room_name like'%"+labRoom.getLabRoomName()+"%'";
		}

		Query query= entityManager.createNativeQuery(sql);
		if(pagesize != 0) {
			query.setMaxResults(pagesize);
			query.setFirstResult((currpage-1)*pagesize);
		}
		List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
		return list;
	}
}