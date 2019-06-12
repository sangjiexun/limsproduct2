package net.zjcclims.service.report;

import edu.emory.mathcs.backport.java.util.Arrays;
import excelTools.ExcelUtils;
import excelTools.JsGridReportBase;
import excelTools.TableData;
import net.zjcclims.constant.MonthReport;
import net.zjcclims.constant.SchoolConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.timetable.OuterApplicationService;
import net.zjcclims.service.timetable.TimetableAppointmentService;
import net.zjcclims.vo.QueryParamsVO;
import net.zjcclims.web.common.PConfig;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("TeachingReportService")
public class TeachingReportServiceImpl implements TeachingReportService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ShareService shareService;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private SchoolTermDAO schoolTermDAO;
    @Autowired
    private TimetableSelfCourseDAO timetableSelfCourseDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LabCenterDAO labCenterDAO;
    @Autowired
    private OuterApplicationService outerApplicationService;
    @Autowired
    private SchoolWeekDAO schoolWeekDAO;
    @Autowired
    private SchoolClassesDAO schoolClassesDAO;
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;
    @Autowired
    private PConfig pConfig;
    @Autowired
    private ReportService reportService;
    /************************************************************
     * @内容：获取周次，用于下拉框
     * @作者：何立友
     * @日期：2014-09-11
     ************************************************************/
    @Override
    public Map<Integer, String> getWeekMap(Integer termId) {
        String sql = "select max(week) from SchoolWeek where schoolTerm.id = " + termId;
        Object schoolWeek = schoolWeekDAO.createQuery(sql, 0, -1).getResultList().get(0);
        int sss = 0;
        if(schoolWeek != null) {
            sss = (Integer) schoolWeek;
        }
        int str = 21;
        if(sss > 0) {
            str = sss;
        }
        Map<Integer, String> weeks = new LinkedHashMap<Integer, String>();
        for (int i = 1; i <= str; i++) {
            weeks.put(i, "第" + i + "周");
        }
        return weeks;
    }

    /*************************************************************************************
     * @內容：根据实验室和节次及星期列出所有时间列表安排
     * @作者： 何立友
     * @日期：2014-09-11
     *************************************************************************************/
    public List<TimetableLabRelated> getLabTimetableAppointments(int termId, String acno, int week) {
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s where 1=1 ");
        hql.append(" and c.timetableAppointment.schoolTerm.id =" + termId + " ");
        hql.append(" and c.timetableAppointment.status=1");  //已发布
/*        if (acno!=null && !acno.equals("-1")) {
            //hql.append(" and c.timetableAppointment.schoolCourseInfo.academyNumber like '%"+labCenterDAO.findLabCenterById(labCenterId).getSchoolAcademy().getAcademyNumber()+"%' ");
            //由于school_course_info和school_course表中的academy_number不一致，暂时先将这个条件屏蔽
        }*/
        if (week != 0) {
            hql.append(" and (s.startWeek <=" + week + " and s.endWeek >=" + week + ")");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /*************************************************************************************
     * @內容：根据实验室和节次及星期列出所有时间列表安排
     * @作者： 何立友
     * @日期：2015-05-12
     *************************************************************************************/
    @Override
    public List<TimetableLabRelated> getSelfLabTimetableAppointments(int termId, String acno, int week) {
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s where 1=1 ");
        hql.append(" and c.timetableAppointment.schoolTerm.id =" + termId );
        hql.append(" and c.timetableAppointment.status=1");  //已发布
        if (acno!=null && !acno.equals("-1")) {
            //hql.append(" and c.timetableAppointment.schoolCourseInfo.academyNumber like '%"+labCenterDAO.findLabCenterById(labCenterId).getSchoolAcademy().getAcademyNumber()+"%' ");
            //由于school_course_info和school_course表中的academy_number不一致，暂时先将这个条件屏蔽
        }
        if (week != 0) {
            hql.append(" and (s.startWeek <=" + week + " and s.endWeek >=" + week + ")");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /*************************************************************************************
     * @內容：根据实验室和节次及星期列出所有时间列表安排
     * @作者： 何立友
     * @日期：2015-05-12
     *************************************************************************************/
    @Override
    public List<TimetableLabRelated> getSelfLabTimetableAppointment2s(HttpServletRequest request, int termId,
                                                                       int roomId, int week, String teacher) {
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt where 1=1 ");
        hql.append(" and c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + termId + " ");
        if (request.getParameter("schoolClass") != null && !request.getParameter("schoolClass").equals("")) {
            hql.append(" and c.timetableAppointment.schoolCourseDetail.courseClassName = '" + request.getParameter("schoolClass") + "'");
        }
        //hql.append(" and c.timetableAppointment.status=1");  //已发布
        hql.append(" and c.timetableAppointment.enabled is true");
        String academyNumber = "";
        // 创建根据学期来查询课程；
        if (roomId != 0 && roomId != -1) {
            //hql.append(" and c.timetableAppointment.schoolCourseInfo.academyNumber like '%"+labCenterDAO.findLabCenterById(labCenterId).getSchoolAcademy().getAcademyNumber()+"%' ");
            //由于school_course_info和school_course表中的academy_number不一致，暂时先将这个条件屏蔽
            hql.append(" and c.labRoom.id =" + roomId);
        }
        if (week != 0 && week != -1) {
            hql.append(" and (s.startWeek <=" + week + " and s.endWeek >=" + week + ")");
        }
        if (teacher != null && teacher != "") {
            hql.append(" and tt.user.username='" + teacher + "'");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /*************************************************************************************
     * @內容：获取指定学期、登录者所在学院、有课程的教师
     * @作者： 何立友
     * @日期：2014-09-12
     *************************************************************************************/
    @Override
    public String getTeachersByTerm(int termId, String acno) {
        Set<User> teachers = new HashSet<User>();
        StringBuffer courses = new StringBuffer("<option value='0'>请选择教师</option>");
        StringBuffer hql = new StringBuffer("select distinct c.userByTeacher from SchoolCourse c where 1=1 ");
        SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if (academy!=null && academy.getAcademyNumber()!=null) {
            hql.append(" and c.schoolAcademy.academyNumber like '" + academy.getAcademyNumber() + "' ");
        }
        hql.append(" and c.schoolTerm.id = " + termId);
        teachers.addAll(userDAO.executeQuery(hql.toString(), 0, -1));
        StringBuffer selfHql = new StringBuffer("select distinct sc.user from TimetableSelfCourse sc where 1=1 ");
        if (academy!=null && academy.getAcademyNumber()!=null) {
            selfHql.append(" and sc.schoolAcademy.academyNumber='" + academy.getAcademyNumber() + "'");
        }
        selfHql.append(" and sc.schoolTerm.id=" + termId);
        teachers.addAll(userDAO.executeQuery(selfHql.toString(), 0, -1));
        for (User u : teachers) {
            courses.append("<option value='" + u.getUsername() + "'>" + u.getCname() + "</option>");
        }
        return courses.toString();
    }

    /*************************************************************************************
     * @內容：获取指定学期、登录者所在学院、有课程的教师(Map)
     * @作者： 何立友
     * @日期：2014-09-12
     *************************************************************************************/
    @Override
    public Map<String, String> getTeachersMapByTerm(int termId) {
        Map<String, String> teachers = new HashMap<String, String>();
        Set<User> teacherSet = new HashSet<User>();
        StringBuffer hql = new StringBuffer("select distinct c.userByTeacher from SchoolCourse c where 1=1 ");
        hql.append(" and c.schoolAcademy.academyNumber like '" + shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "' ");
        hql.append(" and c.schoolTerm.id = " + termId);
        teacherSet.addAll(userDAO.executeQuery(hql.toString(), 0, -1));
        StringBuffer selfHql = new StringBuffer("select distinct sc.user from TimetableSelfCourse sc where 1=1 ");
        selfHql.append(" and sc.schoolAcademy.academyNumber='" + shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() + "'");
        selfHql.append(" and sc.schoolTerm.id=" + termId);
        teacherSet.addAll(userDAO.executeQuery(selfHql.toString(), 0, -1));
        for (User u : teacherSet) {
            teachers.put(u.getUsername(), u.getCname());
        }
        return teachers;
    }

    /*************************************************************************************
     * @內容：获取指定学期、指定教师选课组(ajax)
     * @作者： 何立友
     * @日期：2014-09-17
     *************************************************************************************/
    @Override
    public String getCourseDetailsByTermTeacher(int termId, String username) {
        StringBuffer courses = new StringBuffer("<option value='0'>请选择课程</opion>");
        StringBuffer hql = new StringBuffer("select cd from SchoolCourseDetail cd where 1=1 ");
        hql.append(" and cd.user.username = '" + username + "' ");
        hql.append(" and cd.schoolTerm.id = " + termId);
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailDAO.executeQuery(hql.toString(), 0, -1);
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            courses.append("<option value='" + schoolCourseDetail.getCourseDetailNo() + "'>" + schoolCourseDetail.getSchoolCourse().getCourseCode() + "," + schoolCourseDetail.getSchoolCourse().getCourseName() + "," + schoolCourseDetail.getUser().getCname() + "</option>");
        }
        StringBuffer selfHql = new StringBuffer("select sc from TimetableSelfCourse sc where 1=1");
        selfHql.append(" and sc.schoolTerm.id=" + termId);
        selfHql.append(" and sc.user.username='" + username + "'");
        List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(selfHql.toString(), 0, -1);
        for (TimetableSelfCourse timetableSelfCourse : timetableSelfCourses) {
            courses.append("<option value='" + timetableSelfCourse.getCourseCode() + "'>" + timetableSelfCourse.getCourseCode() + "," + timetableSelfCourse.getSchoolCourseInfo().getCourseName() + "," + timetableSelfCourse.getUser().getCname() + "</option>");
        }
        return courses.toString();
    }

    /*************************************************************************************
     * @內容：根据courseNo(主键)获取课程
     * @作者： 何立友
     * @日期：2014-09-12
     *************************************************************************************/
    @Override
    public SchoolCourse getCourseByCourseNo(String courseNo) {
        return schoolCourseDAO.findSchoolCourseByCourseNo(courseNo);
    }

    /*************************************************************************************
     * @內容：根据courseDetailNo(主键)获取选课组
     * @作者： 何立友
     * @日期：2014-09-17
     *************************************************************************************/
    public SchoolCourseDetail getCourseDetailByCourseDetailNo(String courseDetailNo) {
        return schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(courseDetailNo);
    }

    /*************************************************************************************
     * @內容：根据courseCode获取自主排课选课组
     * @作者： 何立友
     * @日期：2015-05-13
     *************************************************************************************/
    @Override
    public TimetableSelfCourse getTimetableSelfCourseByCourseCode(String courseCode) {
        StringBuffer hql = new StringBuffer("select sc from TimetableSelfCourse sc where 1=1 ");
        hql.append(" and sc.courseCode='" + courseCode + "'");
        List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(hql.toString(), 0, -1);
        if (timetableSelfCourses.size() > 0) {
            return timetableSelfCourses.get(0);
        }
        return null;
    }

    /*************************************************************************************
     * @內容：获取指定学期、指定教师课程(Map)
     * @作者： 何立友
     * @日期：2014-09-12
     *************************************************************************************/
    @Override
    public Map<String, String> getCourseDetailsMapByTermTeacher(int termId, String username) {
        Map<String, String> courseDetails = new HashMap<String, String>();
        StringBuffer hql = new StringBuffer("select cd from SchoolCourseDetail cd where 1=1 ");
        hql.append(" and cd.user.username = '" + username + "' ");
        hql.append(" and cd.schoolTerm.id = " + termId);
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailDAO.executeQuery(hql.toString(), 0, -1);
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            courseDetails.put(schoolCourseDetail.getCourseDetailNo(), schoolCourseDetail.getSchoolCourse().getCourseCode() + "," + schoolCourseDetail.getSchoolCourse().getCourseName() + "," + schoolCourseDetail.getUser().getCname());
        }
        //自主排课
        StringBuffer selfHql = new StringBuffer("select sc from TimetableSelfCourse sc where 1=1 ");
        selfHql.append(" and sc.schoolTerm.id=" + termId);
        selfHql.append(" and sc.user.username='" + username + "'");
        List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(selfHql.toString(), 0, 1);
        for (TimetableSelfCourse timetableSelfCourse : timetableSelfCourses) {
            courseDetails.put(timetableSelfCourse.getCourseCode(), timetableSelfCourse.getCourseCode() + "," + timetableSelfCourse.getSchoolCourseInfo().getCourseName() + "," + timetableSelfCourse.getUser().getCname());
        }
        return courseDetails;
    }

    /*************************************************************************************
     * @內容：根据课程获取排课信息
     * @作者： 何立友
     * @日期：2014-09-12
     *************************************************************************************/
    @Override
    public List<TimetableAppointment> getTimetableAppointmentByCourseDetail(String courseDetailNo) {
        StringBuffer hql = new StringBuffer("select ta from TimetableAppointment ta where 1=1 ");
        hql.append(" and ta.schoolCourseDetail.courseDetailNo = '" + courseDetailNo + "' ");
        hql.append(" and ta.status = 1 ");  //已发布
        List<TimetableAppointment> timetableAppointments = timetableAppointmentDAO.executeQuery(hql.toString(), 0, -1);
        StringBuffer selfHql = new StringBuffer("select ta from TimetableAppointment ta where 1=1 ");
        selfHql.append(" and ta.status=1 ");  //已发布
        selfHql.append(" and ta.timetableSelfCourse.courseCode='" + courseDetailNo + "'");
        timetableAppointments.addAll(timetableAppointmentDAO.executeQuery(selfHql.toString(), 0, -1));
        return timetableAppointments;
    }

    /*************************************************************************************
     * @內容：根据学期获取已发布的排课信息
     * @作者： 何立友
     * @日期：2014-09-13
     *************************************************************************************/
    @Override
    public List<SchoolCourseDetail> getSchoolCourseDetailByTerm(int termId, int labCenterId) {
        StringBuffer hql = new StringBuffer("select distinct cd from SchoolCourseDetail cd join cd.timetableAppointments ta where 1=1 ");
        hql.append(" and cd.schoolTerm.id = " + termId);  //指定学期的数据
        hql.append(" and ta.status=1");  //已发布的排课
        if (labCenterId != 0) {
            hql.append(" and ta.schoolCourseInfo.academyNumber = '" + labCenterDAO.findLabCenterById(labCenterId).getSchoolAcademy().getAcademyNumber() + "'");  //登录者所在学院
        }
        List<SchoolCourseDetail> schoolCourseDetails = schoolCourseDetailDAO.executeQuery(hql.toString(), 0, -1);
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            StringBuffer weekdays = new StringBuffer();  //构造星期字符串
            StringBuffer weeks = new StringBuffer();  //周次字符串
            Set<Integer> weeksSet = new HashSet<Integer>();  //去掉重复周次
            StringBuffer classes = new StringBuffer(); //节次字符串
            Set<Integer> classesSet = new HashSet<Integer>();  //去掉重负节次
            for (TimetableAppointment timetableAppointment : schoolCourseDetail.getTimetableAppointments()) {
                if (weekdays.indexOf(timetableAppointment.getWeekday().toString()) == -1) {
                    weekdays.append(timetableAppointment.getWeekday());
                }
                for (int i = timetableAppointment.getStartWeek(); i <= timetableAppointment.getEndWeek(); i++) {
                    weeksSet.add(i);
                }
                for (int i = timetableAppointment.getStartClass(); i <= timetableAppointment.getEndClass(); i++) {
                    classesSet.add(i);
                }
            }
            //--------------------星期字符串[start]--------------------
            char[] weekdaysArr = weekdays.toString().toCharArray();
            Arrays.sort(weekdaysArr);
            weekdays.setLength(0);
            for (int i = 0; i < weekdaysArr.length; i++) {
                weekdays.append(weekdaysArr[i] + ",");
            }
            schoolCourseDetail.setMajorName(weekdays.deleteCharAt(weekdays.length() - 1).toString()); //星期字符串
            //--------------------星期字符串[end]--------------------
            //--------------------周次字符串[start]--------------------
            int[] weeksArr = new int[weeksSet.size()];
            int j = 0;
            for (Integer week : weeksSet) {
                weeksArr[j] = week;
                j++;
            }
            Arrays.sort(weeksArr);
            for (int i = 0; i < weeksArr.length; i++) {
                weeks.append(weeksArr[i] + ",");
            }
            schoolCourseDetail.setMajorDirectionName(weeks.deleteCharAt(weeks.length() - 1).toString());  //周次字符串
            //--------------------周次字符串[end]--------------------
            //--------------------节次字符串[start]--------------------
            int[] classesArr = new int[classesSet.size()];
            int k = 0;
            for (Integer c : classesSet) {
                classesArr[k] = c;
                k++;
            }
            Arrays.sort(classesArr);
            for (int i = 0; i < classesArr.length; i++) {
                classes.append(classesArr[i] + ",");
            }
            schoolCourseDetail.setCourseTypeName(classes.deleteCharAt(classes.length() - 1).toString());  //节次字符串
            //--------------------节次字符串[end]--------------------
        }
        return schoolCourseDetails;
    }

    /**
     * @內容：根据学期获取已发布的自主排课信息
     * @作者： 何立友
     * @日期：2015-05-13
     */
    @Override
    public List<TimetableSelfCourse> getTimetableSelfCourseByTerm(int termId, int labCenterId) {
        StringBuffer hql = new StringBuffer("select distinct sc from TimetableSelfCourse sc join sc.timetableAppointments ta where 1=1 ");
        hql.append(" and sc.schoolTerm.id = " + termId);  //指定学期的数据
        hql.append(" and ta.status=1");  //已发布的排课
        if (labCenterId != 0) {
            hql.append(" and ta.schoolCourseInfo.academyNumber = '" + labCenterDAO.findLabCenterById(labCenterId).getSchoolAcademy().getAcademyNumber() + "'");  //登录者所在学院
        }
        List<TimetableSelfCourse> timetableSelfCourses = timetableSelfCourseDAO.executeQuery(hql.toString(), 0, -1);
        for (TimetableSelfCourse timetableSelfCourse : timetableSelfCourses) {
            StringBuffer weekdays = new StringBuffer();  //构造星期字符串
            StringBuffer weeks = new StringBuffer();  //周次字符串
            Set<Integer> weeksSet = new HashSet<Integer>();  //去掉重复周次
            StringBuffer classes = new StringBuffer(); //节次字符串
            Set<Integer> classesSet = new HashSet<Integer>();  //去掉重负节次
            for (TimetableAppointment timetableAppointment : timetableSelfCourse.getTimetableAppointments()) {
                if (weekdays.indexOf(timetableAppointment.getWeekday().toString()) == -1) {
                    weekdays.append(timetableAppointment.getWeekday());
                }
                for (int i = timetableAppointment.getStartWeek(); i <= timetableAppointment.getEndWeek(); i++) {
                    weeksSet.add(i);
                }
                for (int i = timetableAppointment.getStartClass(); i <= timetableAppointment.getEndClass(); i++) {
                    classesSet.add(i);
                }
            }
            //--------------------星期字符串[start]--------------------
            char[] weekdaysArr = weekdays.toString().toCharArray();
            Arrays.sort(weekdaysArr);
            weekdays.setLength(0);
            for (int i = 0; i < weekdaysArr.length; i++) {
                weekdays.append(weekdaysArr[i] + ",");
            }
            timetableSelfCourse.setName(weekdays.deleteCharAt(weekdays.length() - 1).toString()); //星期字符串
            //--------------------星期字符串[end]--------------------
            //--------------------周次字符串[start]--------------------
            int[] weeksArr = new int[weeksSet.size()];
            int j = 0;
            for (Integer week : weeksSet) {
                weeksArr[j] = week;
                j++;
            }
            Arrays.sort(weeksArr);
            for (int i = 0; i < weeksArr.length; i++) {
                weeks.append(weeksArr[i] + ",");
            }
            timetableSelfCourse.setCourseCode(weeks.deleteCharAt(weeks.length() - 1).toString());  //周次字符串
            //--------------------周次字符串[end]--------------------
            //--------------------节次字符串[start]--------------------
            int[] classesArr = new int[classesSet.size()];
            int k = 0;
            for (Integer c : classesSet) {
                classesArr[k] = c;
                k++;
            }
            Arrays.sort(classesArr);
            for (int i = 0; i < classesArr.length; i++) {
                classes.append(classesArr[i] + ",");
            }
            SchoolAcademy schoolAcademy = new SchoolAcademy();
            schoolAcademy.setAcademyName(classes.deleteCharAt(classes.length() - 1).toString());
            timetableSelfCourse.setSchoolAcademy(schoolAcademy);  //节次字符串
            //--------------------节次字符串[end]--------------------
        }
        return timetableSelfCourses;
    }

    /*************************************************************************************
     * @內容：上机登记报表导出Excel
     * @作者： 何立友
     * @日期：2014-09-14
     *************************************************************************************/
    @Override
    public void exportExperimentRegister(SchoolCourseDetail schoolCourseDetail, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/excel;charset=UTF-8");
        List<Map> list = new ArrayList<Map>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TimetableAppointment> timetableAppointments = getTimetableAppointmentByCourseDetail(schoolCourseDetail.getCourseDetailNo());
        StringBuffer preparation = new StringBuffer();  //实验环境
        for (TimetableAppointment timetableAppointment : timetableAppointments) {
            if (timetableAppointment.getPreparation() != null && timetableAppointment.getPreparation().indexOf(preparation.toString()) == -1) {
                preparation.append(timetableAppointment.getPreparation() + ",");
            }
            Map map = new HashMap();
            //构造实验项目名称字符串
            StringBuffer itemNames = new StringBuffer();
            for (TimetableItemRelated curr : timetableAppointment.getTimetableItemRelateds()) {
                if (curr.getOperationItem().getId() != null && curr.getOperationItem().getId() != 0) {
                    itemNames.append(curr.getOperationItem().getLpName() + ",");
                } else {
                    itemNames.append(timetableAppointment.getSchoolCourse().getCourseName() + "(课程名称),");
                }
            }
            if (itemNames.length() > 0) {
                itemNames.deleteCharAt(itemNames.length() - 1);
            }
            map.put("itemName", itemNames);  //项目名称
            map.put("studentAmount", timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size());  //上机人数
            map.put("weeks", timetableAppointment.getStartWeek() + "-" + timetableAppointment.getEndWeek());  //周次
            map.put("weekday", timetableAppointment.getWeekday());  //星期
            map.put("classes", timetableAppointment.getStartClass() + "-" + timetableAppointment.getEndClass());  //课次
            //构造实验室字符串
            StringBuffer labNames = new StringBuffer();
            for (TimetableLabRelated curr : timetableAppointment.getTimetableLabRelateds()) {
                labNames.append(curr.getLabRoom().getLabRoomNumber() + ",");
            }
            if (labNames.length() > 0) {
                labNames.deleteCharAt(labNames.length() - 1);
            }
            map.put("lab", labNames);  //机房
            list.add(map);
        }
        if (preparation.length() > 0) {
            preparation.deleteCharAt(preparation.length() - 1);  //去掉最后的逗号
        }
        //实验环境
        Map map1 = new HashMap();
        map1.put("itemName", "实验环境");
        map1.put("studentAmount", preparation);
        list.add(map1);
        //实验环境
        Map map2 = new HashMap();
        map2.put("itemName", "教师签名");
        map2.put("studentAmount", "");
        list.add(map2);
        //实验环境
        Map map3 = new HashMap();
        map3.put("itemName", "院/系负责人意见");
        map3.put("studentAmount", "");
        list.add(map3);
        //实验环境
        Map map4 = new HashMap();
        map4.put("itemName", "实验室负责人意见");
        map4.put("studentAmount", "");
        list.add(map4);
        //实验环境
        Map map5 = new HashMap();
        map5.put("itemName", shareService.getUser().getSchoolAcademy().getAcademyName() + "实验室   打印日期" + sdf.format(new Date()));
        list.add(map5);
        //表明
        String title = "上机登记表";
        //一级表头信息
        String[] parentHeaders = new String[]{"教师姓名", schoolCourseDetail.getUser().getCname(), "课程", schoolCourseDetail.getSchoolCourse().getCourseName()};
        //二级表头信息
        String[][] childrenHeaders = new String[][]{{"实验名"}, {"上机人数"}, {"周次"}, {"星期", "课次", "机房"}};
        //表头对应的数据别名(与上面list中的别名相同且与表头顺序要一致)
        String[] fields = new String[]{"itemName", "studentAmount", "weeks", "weekday", "classes", "lab"};
        // 封装表格数据
        TableData tableData = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(parentHeaders, childrenHeaders), fields);
        // 导出工具类
        JsGridReportBase report = new JsGridReportBase(request, response);
        //查询信息
        String info = schoolCourseDetail.getSchoolTerm().getTermName() + "," + schoolCourseDetail.getUser().getCname() + ","
                + schoolCourseDetail.getSchoolCourse().getCourseCode() + "," + schoolCourseDetail.getCourseName();
        // 创建新的Excel 工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 根据模板文件，初始化表头样式
        HashMap<String, HSSFCellStyle> styles = report.initStyles(wb);
        wb = report.writeSheets(wb, title, info, styles, shareService.getUser().getCname(), tableData);//写入工作表
        //tableData.getRowCount()是数据的行数，不包括表头、标题等
        wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount(), tableData.getRowCount(), 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
        wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount() + 1, tableData.getRowCount() + 1, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
        wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount() + 2, tableData.getRowCount() + 2, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
        wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount() + 3, tableData.getRowCount() + 3, 1, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
        wb.getSheetAt(0).addMergedRegion(new CellRangeAddress(tableData.getRowCount() + 4, tableData.getRowCount() + 4, 0, 5));//合并标题行：起始行号，终止行号， 起始列号，终止列号
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);  //最后一行右对齐
        wb.getSheetAt(0).getRow(wb.getSheetAt(0).getLastRowNum()).getCell(0).setCellStyle(style);
        String sFileName = title + ".xls";
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        wb.write(response.getOutputStream());
    }

    /*************************************************************************************
     * @內容：实验准备报表导出Excel
     * @作者： 何立友
     * @日期：2014-09-15
     *************************************************************************************/
    @Override
    public void exportExperimentPrepare(SchoolTerm schoolTerm, int labCenterId,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Map> list = new ArrayList<Map>();
        List<SchoolCourseDetail> schoolCourseDetails = getSchoolCourseDetailByTerm(schoolTerm.getId(), labCenterId);
        for (SchoolCourseDetail schoolCourseDetail : schoolCourseDetails) {
            Map map = new HashMap();
            map.put("teacherName", schoolCourseDetail.getUser().getCname());  //教师姓名
            map.put("courseName", schoolCourseDetail.getSchoolCourse().getCourseName());  //课程名称
            map.put("studentAmount", schoolCourseDetail.getSchoolCourseStudents().size());  //上机人数
            map.put("weeks", schoolCourseDetail.getMajorDirectionName());  //上课周次
            map.put("weekdays", schoolCourseDetail.getMajorName());  //上课星期
            map.put("classes", schoolCourseDetail.getCourseTypeName());  //上课课次
            StringBuffer labs = new StringBuffer();  //构造机房字符串
            StringBuffer preparation = new StringBuffer();  //构造实验准备字符串
            for (TimetableAppointment timetableAppointment : schoolCourseDetail.getTimetableAppointments()) {
                for (TimetableLabRelated TimetableLabRelated : timetableAppointment.getTimetableLabRelateds()) {
                    labs.append(TimetableLabRelated.getLabRoom().getLabRoomNumber() + ",");
                }
                if (timetableAppointment.getPreparation() != null && timetableAppointment.getPreparation().indexOf(preparation.toString()) == -1) {
                    preparation.append(timetableAppointment.getPreparation() + ",");
                }
            }
            if (labs.length() > 0) {
                labs.deleteCharAt(labs.length() - 1);  //去掉最后的逗号
            }
            if (preparation.length() > 0) {
                preparation.deleteCharAt(preparation.length() - 1);  //去掉最后的逗号
            }
            map.put("labs", labs);  //上课机房
            map.put("experimentPrepare", preparation);  //实验准备
            list.add(map);
        }
        SchoolTerm schoolTerms =
                schoolTermDAO.findSchoolTermById(schoolTerm.getId());
        String title = "实验准备";
        String[] headers = new String[]{"教师姓名", "课程名称", "上课人数", "上课周次", "上课星期", "上课课次", "上课机房", "实验准备"};
        String[] fields = new String[]{"teacherName", "courseName", "studentAmount", "weeks", "weekdays", "classes", "labs", "experimentPrepare"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(headers), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), schoolTerms.getTermName(), td);
    }

    /*************************************************************************************
     * @內容：学期登记报表导出Excel
     * @作者： 何立友
     * @日期：2014-10-30
     *************************************************************************************/
    @Override
    public void exportTermRegister(SchoolTerm schoolTerm, String acno, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map> list = new ArrayList<Map>();
        List<LabRoom> labRooms = getLabRoomsByLabCenter(acno); //实验中心所在学院的实验室分室
        List<TimetableLabRelated> lbs = getLabTimetableAppointments(schoolTerm.getId(), acno, 0);
        List<TimetableLabRelated> sef = getSelfLabTimetableAppointments(schoolTerm.getId(), acno, 0);
        for (int i = 1; i <= 9; i++) {
            for (LabRoom labRoom : labRooms) {
                Map map = new HashMap();
                switch (i) {
                    case 1:
                        map.put("classes", "第一节~第二节");
                        break;
                    case 2:
                        map.put("classes", "第三节~第四节");
                        break;
                    case 3:
                        map.put("classes", "第五节~第六节");
                        break;
                    case 4:
                        map.put("classes", "第七节~第八节");
                        break;
                    case 5:
                        map.put("classes", "第九节");
                        break;
                    case 6:
                        map.put("classes", "第十节");
                        break;
                    case 7:
                        map.put("classes", "第十一节");
                        break;
                    case 8:
                        map.put("classes", "第十二节");
                        break;
                    case 9:
                        map.put("classes", "第十三节");
                }
                map.put("lab", labRoom.getLabRoomNumber());
                for (int iWeek = 1; iWeek <= 7; iWeek++) {
                    StringBuffer result = new StringBuffer();
                    for (TimetableLabRelated tr : lbs) {
                        if (tr.getLabRoom().getId() == labRoom.getId()) {
                            if (tr.getTimetableAppointment().getWeekday() == iWeek && tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                if (i <= 4) {
                                    if (tr.getTimetableAppointment().getStartClass() <= i * 2 - 1 && tr.getTimetableAppointment().getEndClass() >= i * 2 - 1 ||
                                            tr.getTimetableAppointment().getStartClass() >= i * 2 && tr.getTimetableAppointment().getEndClass() <= i * 2) {
                                        if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                            if (tr.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //显示周次、节次
                                        if (tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            //节次
                                            if (tr.getTimetableAppointment().getStartClass().equals(tr.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "-" + tr.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            //周次
                                            if (tr.getTimetableAppointment().getStartWeek().equals(tr.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "-" + tr.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                }
                                if (i > 4) {
                                    if (tr.getTimetableAppointment().getStartClass() <= i + 4 && tr.getTimetableAppointment().getEndClass() >= i + 4) {
                                        if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                            result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                        }
                                        for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //显示周次节次
                                        if (tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            //节次
                                            if (tr.getTimetableAppointment().getStartClass().equals(tr.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "-" + tr.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            //周次
                                            if (tr.getTimetableAppointment().getStartWeek().equals(tr.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "-" + tr.getTimetableAppointment().getEndWeek());
                                            }
                                        }
                                    }
                                }
                            }  //TimetableAppointmentSameNumbers().size()==0的情况
                            if (tr.getTimetableAppointment().getWeekday() == iWeek && tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() > 0) {
                                if (i <= 4) {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if ((entry.getStartClass() <= i * 2 - 1 && entry.getEndClass() >= i * 2 - 1) || (entry.getStartClass() >= i * 2 && entry.getEndClass() <= i * 2)) {
                                            if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                                //周次、节次
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                    result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "   ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if (entry.getStartClass() <= i + 4 && entry.getEndClass() >= i + 4) {
                                            if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                                //周次、节次
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                    result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "  ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }  //判断实验室
                    }  //循环课程信息lbs
                    //自主排课信息
                    for (TimetableLabRelated lSelftimetable : sef) {
                        if (lSelftimetable.getLabRoom().getId() == labRoom.getId()) {
                            if (lSelftimetable.getTimetableAppointment().getWeekday() == iWeek && lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                if (i <= 4) {
                                    if ((lSelftimetable.getTimetableAppointment().getStartClass() <= i * 2 - 1 && lSelftimetable.getTimetableAppointment().getEndClass() >= i * 2 - 1) || (lSelftimetable.getTimetableAppointment().getStartClass() >= i * 2 && lSelftimetable.getTimetableAppointment().getEndClass() <= i * 2)) {
                                        if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //周次、节次
                                        if (lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            if (lSelftimetable.getTimetableAppointment().getStartClass().equals(lSelftimetable.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            if (lSelftimetable.getTimetableAppointment().getStartWeek().equals(lSelftimetable.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                } else {
                                    if (lSelftimetable.getTimetableAppointment().getStartClass() <= i + 4 && lSelftimetable.getTimetableAppointment().getEndClass() >= i + 4) {
                                        if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() > 0) {
                                                //result.append(lSelftimetable.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                result.append(lSelftimetable.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                            if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //周次、节次
                                        if (lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            if (lSelftimetable.getTimetableAppointment().getStartClass().equals(lSelftimetable.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            if (lSelftimetable.getTimetableAppointment().getStartWeek().equals(lSelftimetable.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                }
                            }
                            if (lSelftimetable.getTimetableAppointment().getWeekday() == iWeek && lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() > 0) {
                                if (i <= 4) {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if ((entry.getStartClass() <= i * 2 - 1 && entry.getEndClass() >= i * 2 - 1) || (entry.getStartClass() >= i * 2 && entry.getEndClass() <= i * 2)) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() > 0) {
                                                        //result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getCourseCode()+"-");
                                                        result.append(lSelftimetable.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                        result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "  ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if (entry.getStartClass() <= i + 4 && entry.getEndClass() >= i + 4) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                                //周次、节次
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() > 0) {
                                                        //result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getCourseCode()+"-");
                                                        result.append(lSelftimetable.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                        result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "  ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }  //判断实验室
                    }  //循环自主排课的信息
                    map.put("weekday" + iWeek, result);
                }  //周1-7循环
                list.add(map);
            }  //实验室遍历
        }
        SchoolTerm schoolTerms =
                schoolTermDAO.findSchoolTermById(schoolTerm.getId());
        String title = "学期登记表";
        String[] hearders = new String[]{"节次", "实验室", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};//表头数组
        String[] fields = new String[]{"classes", "lab", "weekday1", "weekday2", "weekday3", "weekday4", "weekday5", "weekday6", "weekday7"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), schoolTerms.getTermName(), td);
    }

    /*************************************************************************************
     * @內容：获取登录者所在学院的实验室
     * @作者： 何立友
     * @日期：2014-10-30
     *************************************************************************************/
    @Override
    public List<LabRoom> getLabRooms() {
        // 获取有效的实验分室列表-根据登录用户的所属学院
        StringBuffer sql = new StringBuffer("select c from LabRoom c "
                + "where c.CDictionaryByLabRoom.id=583 and c.labCenter.schoolAcademy.academyNumber like '%"
                + shareService.getUserDetail().getSchoolAcademy().getAcademyNumber().substring(0, SchoolConstantInterface.INT_SCHOOLACADEMY_NUMBER)
                + "%' and (c.isUsed=1 or c.isUsed=null)");
        return labRoomDAO.executeQuery(sql.toString(), 0, -1);
    }

    /*************************************************************************************
     * @內容：根据参数条件获取排课信息
     * @作者： 何立友
     * @日期：2014-10-30
     *************************************************************************************/
    @Override
    public List<TimetableLabRelated> getLabTimetableAppointmenmt(int termId, int startClass, int endClass, int labId, int weekday, String academyNumber) {
        StringBuffer hql = new StringBuffer("select c from TimetableLabRelated c where 1=1 ");
        hql.append(" and c.labRoom.id=" + labId);
        hql.append(" and c.timetableAppointment.weekday=" + weekday);
        hql.append(" and c.timetableAppointment.status=1");  //已发布
        hql.append(" and c.timetableAppointment.startClass >=" + startClass + " and c.timetableAppointment.c.timetableAppointment.endClass<=" + endClass);
        hql.append(" and c.timetableAppointment.schoolCourse.schoolTerm.id=" + termId);
        hql.append(" and c.timetableAppointment.schoolCourse.schoolAcademy.academyNumber='" + academyNumber + "' ");
        return timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
    }

    /*************************************************************************************
     * @throws Exception
     * @內容：导出周次登记
     * @作者： 何立友
     * @日期：2015-06-18
     *************************************************************************************/
    @Override
    public void exportweekRegister(SchoolTerm schoolTerm, String acno, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map> list = new ArrayList<Map>();
        int weekId = 0;
        List<LabRoom> labRooms = getLabRoomsByLabCenter(acno); //实验中心所在学院的实验室分室
        try {
            if (schoolTerm.getTermCode() != null && !"".equals(schoolTerm.getTermCode())) {
                weekId = schoolTerm.getTermCode();
            }
        } catch (Exception e) {
        }
        List<TimetableLabRelated> lbs = getLabTimetableAppointments(schoolTerm.getId(), acno, weekId);
        List<TimetableLabRelated> sef = getSelfLabTimetableAppointments(schoolTerm.getId(), acno, weekId);
        for (int i = 1; i <= 9; i++) {
            for (LabRoom labRoom : labRooms) {
                Map map = new HashMap();
                switch (i) {
                    case 1:
                        map.put("classes", "第一节~第二节");
                        break;
                    case 2:
                        map.put("classes", "第三节~第四节");
                        break;
                    case 3:
                        map.put("classes", "第五节~第六节");
                        break;
                    case 4:
                        map.put("classes", "第七节~第八节");
                        break;
                    case 5:
                        map.put("classes", "第九节");
                        break;
                    case 6:
                        map.put("classes", "第十节");
                        break;
                    case 7:
                        map.put("classes", "第十一节");
                        break;
                    case 8:
                        map.put("classes", "第十二节");
                        break;
                    case 9:
                        map.put("classes", "第十三节");
                }
                map.put("lab", labRoom.getLabRoomNumber());
                for (int iWeek = 1; iWeek <= 7; iWeek++) {
                    StringBuffer result = new StringBuffer();
                    for (TimetableLabRelated tr : lbs) {
                        if (tr.getLabRoom().getId() == labRoom.getId()) {
                            if (tr.getTimetableAppointment().getWeekday() == iWeek && tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                if (i <= 4) {
                                    if (tr.getTimetableAppointment().getStartClass() <= i * 2 - 1 && tr.getTimetableAppointment().getEndClass() >= i * 2 - 1 ||
                                            tr.getTimetableAppointment().getStartClass() >= i * 2 && tr.getTimetableAppointment().getEndClass() <= i * 2) {
                                        if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                            if (tr.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //显示周次、节次
                                        if (tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            //节次
                                            if (tr.getTimetableAppointment().getStartClass().equals(tr.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "-" + tr.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            //周次
                                            if (tr.getTimetableAppointment().getStartWeek().equals(tr.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "-" + tr.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                }
                                if (i > 4) {
                                    if (tr.getTimetableAppointment().getStartClass() <= i + 4 && tr.getTimetableAppointment().getEndClass() >= i + 4) {
                                        if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                            result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                        }
                                        for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //显示周次节次
                                        if (tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            //节次
                                            if (tr.getTimetableAppointment().getStartClass().equals(tr.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "-" + tr.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            //周次
                                            if (tr.getTimetableAppointment().getStartWeek().equals(tr.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "-" + tr.getTimetableAppointment().getEndWeek());
                                            }
                                        }
                                    }
                                }
                            }  //TimetableAppointmentSameNumbers().size()==0的情况
                            if (tr.getTimetableAppointment().getWeekday() == iWeek && tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() > 0) {
                                if (i <= 4) {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if ((entry.getStartClass() <= i * 2 - 1 && entry.getEndClass() >= i * 2 - 1) || (entry.getStartClass() >= i * 2 && entry.getEndClass() <= i * 2)) {
                                            if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                                //周次、节次
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                    result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "   ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if (entry.getStartClass() <= i + 4 && entry.getEndClass() >= i + 4) {
                                            if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                                //周次、节次
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                    result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "  ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : tr.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }  //判断实验室
                    }  //循环课程信息lbs
                    //自主排课信息
                    for (TimetableLabRelated lSelftimetable : sef) {
                        if (lSelftimetable.getLabRoom().getId() == labRoom.getId()) {
                            if (lSelftimetable.getTimetableAppointment().getWeekday() == iWeek && lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                if (i <= 4) {
                                    if ((lSelftimetable.getTimetableAppointment().getStartClass() <= i * 2 - 1 && lSelftimetable.getTimetableAppointment().getEndClass() >= i * 2 - 1) || (lSelftimetable.getTimetableAppointment().getStartClass() >= i * 2 && lSelftimetable.getTimetableAppointment().getEndClass() <= i * 2)) {
                                        if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //周次、节次
                                        if (lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            if (lSelftimetable.getTimetableAppointment().getStartClass().equals(lSelftimetable.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            if (lSelftimetable.getTimetableAppointment().getStartWeek().equals(lSelftimetable.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                } else {
                                    if (lSelftimetable.getTimetableAppointment().getStartClass() <= i + 4 && lSelftimetable.getTimetableAppointment().getEndClass() >= i + 4) {
                                        if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() > 0) {
                                                //result.append(lSelftimetable.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                result.append(lSelftimetable.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                            if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //周次、节次
                                        if (lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            if (lSelftimetable.getTimetableAppointment().getStartClass().equals(lSelftimetable.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + lSelftimetable.getTimetableAppointment().getStartClass() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            if (lSelftimetable.getTimetableAppointment().getStartWeek().equals(lSelftimetable.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + lSelftimetable.getTimetableAppointment().getStartWeek() + "-");
                                                result.append(lSelftimetable.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                }
                            }
                            if (lSelftimetable.getTimetableAppointment().getWeekday() == iWeek && lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() > 0) {
                                if (i <= 4) {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if ((entry.getStartClass() <= i * 2 - 1 && entry.getEndClass() >= i * 2 - 1) || (entry.getStartClass() >= i * 2 && entry.getEndClass() <= i * 2)) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() > 0) {
                                                        //result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getCourseCode()+"-");
                                                        result.append(lSelftimetable.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                        result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "  ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    StringBuffer sameStart = new StringBuffer("-");
                                    for (TimetableAppointmentSameNumber entry : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                        if (entry.getStartClass() <= i + 4 && entry.getEndClass() >= i + 4) {
                                            if (lSelftimetable.getTimetableAppointment().getTimetableStyle() == 5 || lSelftimetable.getTimetableAppointment().getTimetableStyle() == 6) {
                                                //周次、节次
                                                StringBuffer v_param = new StringBuffer("-" + entry.getStartClass() + "-");
                                                if (sameStart.toString().indexOf(v_param.toString()) < 0) {
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() > 0) {
                                                        //result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getCourseCode()+"-");
                                                        result.append(lSelftimetable.getTimetableAppointment().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    if (lSelftimetable.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                        result.append(lSelftimetable.getTimetableAppointment().getTimetableSelfCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                                    }
                                                    for (TimetableTeacherRelated tt : lSelftimetable.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                                        result.append(tt.getUser().getCname() + "  ");
                                                    }
                                                    if (entry.getStartClass().equals(entry.getEndClass())) {
                                                        result.append("节次：" + entry.getStartClass() + "  ");
                                                    } else {
                                                        result.append("节次：" + entry.getStartClass() + "-" + entry.getEndClass() + "  ");
                                                    }
                                                    sameStart.append("-" + entry.getStartClass() + "-");
                                                    result.append("周次：");
                                                    for (TimetableAppointmentSameNumber entry1 : lSelftimetable.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                                                        if (entry.getStartClass().equals(entry1.getStartClass())) {
                                                            if (entry1.getStartWeek().equals(entry1.getEndWeek())) {
                                                                result.append(entry1.getStartWeek() + "  ");
                                                            } else {
                                                                result.append(entry1.getStartWeek() + "-" + entry1.getEndWeek() + "  ");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }  //判断实验室
                    }  //循环自主排课的信息
                    map.put("weekday" + iWeek, result);
                }  //周1-7循环
                list.add(map);
            }  //实验室遍历
        }
        SchoolTerm schoolTerms =
                schoolTermDAO.findSchoolTermById(schoolTerm.getId());
        String title = "周次登记表";
        String[] hearders = new String[]{"节次", "实验室", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};//表头数组
        String[] fields = new String[]{"classes", "lab", "weekday1", "weekday2", "weekday3", "weekday4", "weekday5", "weekday6", "weekday7"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), schoolTerms.getTermName(), td);
    }

    /*************************************************************************************
     * @內容：查找指定中心的实验室分室
     * @作者： 何立友
     * @日期：2015-06-17
     *************************************************************************************/
    @Override
    public List<LabRoom> getLabRoomsByLabCenter(String acno) {
        String academyNumber = "";
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if (academy!=null && academy.getAcademyNumber()!=null) {
            // 获取选择的实验中心
            academyNumber = academy.getAcademyNumber();
        } else {
            academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
        }
        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
        String s = "select c from LabRoom c "
                + "where c.CDictionaryByLabRoom.id=583 and c.labCenter.schoolAcademy.academyNumber like '%";
        //防止学院编号过短导致报错
        if(academyNumber.length()>=SchoolConstantInterface.INT_SCHOOLACADEMY_NUMBER){
            s += academyNumber.substring(0, SchoolConstantInterface.INT_SCHOOLACADEMY_NUMBER) + "%' and (c.isUsed=1 or c.isUsed=null)";
        }else{
            s += academyNumber + "%' and (c.isUsed=1 or c.isUsed=null)";
        }
        StringBuffer sql = new StringBuffer(s);
        List<LabRoom> list = labRoomDAO.executeQuery(sql.toString(), 0, -1);
        return list;
    }

    /**
     * Description 月报报表{导出excel}
     * @param request
     * @param response
     * @throws Exception
     * @author 陈乐为 2019年4月24日
     */
    @Override
    public void exportMonthRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map> list = new ArrayList<Map>();
        int term = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        if (request.getParameter("term") != null) {
            term = Integer.parseInt(request.getParameter("term"));
        }
        // 查询条件
        String params = request.getParameter("params");
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        // 封装参数
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setTerm_id(term);
        queryParamsVO.setQuery_params(params);
        queryParamsVO.setStart_date(start_date);
        queryParamsVO.setEnd_date(end_date);
        queryParamsVO.setCurr_page(1);
        queryParamsVO.setPage_size(99999);
        List<Object[]> lists = reportService.getMonthReport(queryParamsVO);
        int i = 1;
        for (Object[] monthReport : lists) {
            Map map = new HashMap();
            map.put("serial number", i);//序号
            i++;
            map.put("department", monthReport[0]);//系别
            map.put("experiment Department", monthReport[1]);//实验部门
            map.put("responsible person", monthReport[2]);//负责人
            map.put("laboratory name", monthReport[3]);//实验实训室名称
            map.put("place", monthReport[4]);//地点
            map.put("date", monthReport[5]);//日期
            map.put("name of the course", monthReport[6]);//课程名称
            map.put("name of the experiment", monthReport[7]);//实验名称
            map.put("junior number", monthReport[8]);//人数
            map.put("junior class", monthReport[9]);//课时
            map.put("junior time", monthReport[10]);//人时数
            map.put("experimental class", monthReport[11]);//实验类别
            map.put("experimental type", monthReport[12]);//实验类别
            map.put("guiding teacher", monthReport[13]);//指导教师
            map.put("class", monthReport[14]);//班级
            map.put("notes", monthReport[15]);//备注
            map.put("assessment method", monthReport[16]);//考核方法
            list.add(map);
        }  //实验室遍历
        SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(term);
        String title = "月报登记表";
        String[] hearders = new String[]{"序号", "系别", "实验部门", "负责人", "实验室名称",
                "地点", "日期", "课程名称", "实验名称", "上课人数", "上课课时", "上课人时数",
                "实验类别","实验类型", "指导教师", "班级", "备注", "考核方法"};//表头数组
        String[] fields = new String[]{"serial number", "department", "experiment Department", "responsible person", "laboratory name",
                "place", "date", "name of the course", "the name of the experiment", "junior number", "junior class", "junior time",
                "experimental class","experimental type", "guiding teacher", "class", "notes", "assessment method"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), schoolTerm.getTermName(), td);
    }

    /*************************************************************************************
     * @內容：根据实验室和节次及星期列出所有时间列表安排
     * @作者： 何立友
     * @日期：2014-09-11
     *************************************************************************************/
    public List<TimetableLabRelated> getLabTimetableAppointments(HttpServletRequest request, int termId,
                                                                 int roomId, int week, String teacher, String acno  ) {
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c " +
                " left join c.timetableAppointment.timetableAppointmentSameNumbers s " +
                " left join c.timetableAppointment.timetableTeacherRelateds tt " +
                " left join c.timetableAppointment.timetableTutorRelateds ttu " +
                " left join c.timetableAppointment.schoolCourse.schoolClasseses scs" +
                " where 1=1 ");
        hql.append(" and c.timetableAppointment.status = 1");
        hql.append(" and c.timetableAppointment.schoolTerm.id =" + termId + " ");
        // 班级查询
        if (request.getParameter("schoolClass") != null && !request.getParameter("schoolClass").equals("")) {
            SchoolClasses schoolClasses=schoolClassesDAO.findSchoolClassesByClassNumber(request.getParameter("schoolClass"));
            hql.append(" and scs.className = '" + schoolClasses.getClassName() + "'");
        }
        hql.append(" and c.timetableAppointment.enabled is true");
        // 当前学院筛选
        if(acno != null && !pConfig.PROJECT_NAME.equals("jitsoft")) {
            hql.append(" and c.labRoom.labCenter.schoolAcademy.academyNumber like '").append(acno).append("'");
        }
        // 创建根据学期来查询课程；
        if (roomId != 0 && roomId != -1) {
            //由于school_course_info和school_course表中的academy_number不一致，暂时先将这个条件屏蔽
            hql.append(" and c.labRoom.id =" + roomId);
        }
        if (week != 0 && week != -1) {
            hql.append(" and (s.startWeek <=" + week + " and s.endWeek >=" + week + ")");
        }
        if (teacher != null && teacher != "") {
            hql.append(" and (tt.user.username='" + teacher + "'");
            hql.append(" or ttu.user.username='" + teacher + "')");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /*************************************************************************************
     * @內容：获取实验室当前时间课程
     * @作者： 戴昊宇
     * @日期：2018-04-18
     *************************************************************************************/
    public List<TimetableLabRelated> getCurrentLabTimetableAppointments(int termId, int roomId, int week, int weekday, int belongsClass) {
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt where 1=1 ");
        hql.append(" and c.timetableAppointment.schoolTerm.id =" + termId + " ");
        hql.append(" and c.timetableAppointment.enabled is true");
        hql.append(" and c.labRoom.id =" + roomId);
        hql.append(" and (s.startWeek <=" + week + " and s.endWeek >=" + week + ")");
        hql.append(" and (s.startClass <=" + belongsClass + " and s.endClass >=" + belongsClass + ")");
        hql.append(" and c.timetableAppointment.weekday =" + weekday);
        hql.append(" and c.timetableAppointment.status = 1");// 调停课还没做限制

        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /*************************************************************************************
     * @內容：获取实验室当前时间课程
     * @作者： 戴昊宇
     * @日期：2018-04-18
     *************************************************************************************/
    @Override
    public List<TimetableLabRelated> getCurrentSelfLabTimetableAppointments(int termId,
                                                                            int roomId, int week, int weekday, int belongsClass) {
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt where 1=1 ");
        hql.append(" and c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + termId + " ");
        hql.append(" and c.timetableAppointment.enabled is true");
        hql.append(" and c.labRoom.id =" + roomId);
        hql.append(" and ((c.timetableAppointment.startWeek <=" + week + " and c.timetableAppointment.endWeek >=" + week + ") ");
        hql.append(" or (s.startWeek <=" + week + " and s.endWeek >=" + week + "))");
        hql.append(" and ((c.timetableAppointment.startClass <=" + belongsClass + " and c.timetableAppointment.endClass >=" + belongsClass + ") ");
        hql.append(" or (s.startClass <=" + belongsClass + " and s.endClass >=" + belongsClass + "))");
        hql.append(" and c.timetableAppointment.weekday =" + weekday);
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /*************************************************************************************
     * Description： 功能-根据学期和实验室，获取实验室学年正常教学安排的人时数
     *
     * @作者： 魏城
     * @日期：2018-05-18
     *************************************************************************************/
    public Integer getLabTimetableAppointmentHours(int termId, int roomId) {
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt where 1=1 ");
        hql.append(" and c.timetableAppointment.schoolCourse.schoolTerm.id =" + termId + " ");
        //hql.append(" and c.timetableAppointment.status=1");  //已发布
        hql.append(" and c.timetableAppointment.enabled is true");
        String academyNumber = "";
        if (roomId != 0 && roomId != -1) {
            hql.append(" and c.labRoom.id =" + roomId);
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        int totalHours = 0;
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            TimetableAppointment timetableAppointment = timetableLabRelated.getTimetableAppointment();
            //判断是否连续节次
            if (timetableAppointment.getTimetableAppointmentSameNumbers().size() == 0) {
                totalHours = totalHours + (timetableAppointment.getEndClass() - timetableAppointment.getStartClass() + 1) * (timetableAppointment.getEndWeek() - timetableAppointment.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
            } else {
                for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointment.getTimetableAppointmentSameNumbers()) {
                    totalHours = totalHours + (timetableAppointmentSameNumber.getEndClass() - timetableAppointmentSameNumber.getStartClass() + 1) * (timetableAppointmentSameNumber.getEndWeek() - timetableAppointmentSameNumber.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                }
            }
        }
        return totalHours;
    }

    /*************************************************************************************
     * Description： 功能-根据学期和实验室，获取实验室开放的人时数
     *
     * @作者： 魏城
     * @日期：2018-05-18
     *************************************************************************************/
    public Integer[] getLabTimetableAppointmentSelfHours(int termId, int roomId) {
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt where 1=1 ");
        hql.append(" and c.timetableAppointment.timetableSelfCourse.schoolTerm.id =" + termId + " ");
        hql.append(" and c.timetableAppointment.status=1");  //已发布
        hql.append(" and c.timetableAppointment.enabled is true");
        String academyNumber = "";
        if (roomId != 0 && roomId != -1) {
            hql.append(" and c.labRoom.id =" + roomId);
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        //校内
        int totalInHours = 0;
        //校外学生
        int totalOutStudentHours = 0;
        //校外社会培训
        int totalOutTrainingHours = 0;
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            TimetableAppointment timetableAppointment = timetableLabRelated.getTimetableAppointment();
            //判断是否连续节次
            if (timetableAppointment.getTimetableAppointmentSameNumbers().size() == 0) {
                //判断校内校外，进行累加
                if(timetableAppointment.getCDictionaryByObject()!=null&&timetableAppointment.getCDictionaryByObject().getId()==719){
                    totalOutStudentHours = totalOutStudentHours + (timetableAppointment.getEndClass() - timetableAppointment.getStartClass() + 1) * (timetableAppointment.getEndWeek() - timetableAppointment.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                }else if(timetableAppointment.getCDictionaryByObject()!=null&&timetableAppointment.getCDictionaryByObject().getId()==720){
                    totalOutTrainingHours = totalOutTrainingHours + (timetableAppointment.getEndClass() - timetableAppointment.getStartClass() + 1) * (timetableAppointment.getEndWeek() - timetableAppointment.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                }else{
                    totalInHours = totalInHours + (timetableAppointment.getEndClass() - timetableAppointment.getStartClass() + 1) * (timetableAppointment.getEndWeek() - timetableAppointment.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                }
           } else {
                for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableAppointment.getTimetableAppointmentSameNumbers()) {
                    if(timetableAppointment.getCDictionaryByObject()!=null&&timetableAppointment.getCDictionaryByObject().getId()==719){
                        totalOutStudentHours = totalOutStudentHours + (timetableAppointmentSameNumber.getEndClass() - timetableAppointmentSameNumber.getStartClass() + 1) * (timetableAppointmentSameNumber.getEndWeek() - timetableAppointmentSameNumber.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                    }else if(timetableAppointment.getCDictionaryByObject()!=null&&timetableAppointment.getCDictionaryByObject().getId()==720){
                        totalOutTrainingHours = totalOutTrainingHours + (timetableAppointmentSameNumber.getEndClass() - timetableAppointmentSameNumber.getStartClass() + 1) * (timetableAppointmentSameNumber.getEndWeek() - timetableAppointmentSameNumber.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                    }else{
                        totalInHours = totalInHours + (timetableAppointmentSameNumber.getEndClass() - timetableAppointmentSameNumber.getStartClass() + 1) * (timetableAppointmentSameNumber.getEndWeek() - timetableAppointmentSameNumber.getStartWeek() + 1) * timetableAppointment.getSchoolCourseDetail().getSchoolCourseStudents().size();
                    }
                }
            }
        }
        Integer[] a = {totalInHours,totalOutStudentHours,totalOutTrainingHours};
        return a;
    }

    /**
     * Description 根据实验室、星期、节次查询课程安排
     * @param termId
     * @param roomId
     * @param week
     * @return
     * @author 陈乐为 2018-10-9
     */
    @Override
    public List<TimetableLabRelated> getSelfLabAppointments(int termId, int roomId, int week) {
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s where 1=1 ");
        hql.append(" and c.timetableAppointment.timetableSelfCourse.schoolTerm.id ="+termId+" ");
        hql.append(" and c.timetableAppointment.status=1");  //已发布
//        hql.append(" and c.timetableAppointment.enabled is true");

        if(roomId != 0 && roomId!=-1) {
            hql.append(" and c.labRoom.id ="+roomId);
        }
        if(week != 0 && week!=-1) {
            hql.append(" and ((c.timetableAppointment.startWeek <="+week+" and c.timetableAppointment.endWeek >="+week+") ");
            hql.append(" or (s.startWeek <="+week+" and s.endWeek >="+week+"))");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /**
     * Description 根据实验室日课表
     * @param termId
     * @param labCenterId
     * @param roomId
     * @param week
     * @param weekday
     * @return
     * @author 陈乐为 2018-10-9
     */
    public List<TimetableLabRelated> getLabAppointmentsByWeekday(int termId, int labCenterId,int roomId, int week,int weekday) {
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt where 1=1 ");
        hql.append(" and c.timetableAppointment.schoolTerm.id ="+termId+" ");
        hql.append(" and c.timetableAppointment.status=1");  //已发布
//        String academyNumber = "";
//        // 如果没有获取有效的实验分室列表-根据登录用户的所属学院
//        if (labCenterId != -1) {
//            // 获取选择的实验中心
//            academyNumber = labCenterDAO.findLabCenterById(labCenterId)
//                    .getSchoolAcademy().getAcademyNumber();
//        } else {
//            academyNumber = shareService.getUserDetail().getSchoolAcademy()
//                    .getAcademyNumber();
//        } // 创建根据学期来查询课程；

        if(roomId != 0) {
            //hql.append(" and c.timetableAppointment.schoolCourseInfo.academyNumber like '%"+labCenterDAO.findLabCenterById(labCenterId).getSchoolAcademy().getAcademyNumber()+"%' ");
            //由于school_course_info和school_course表中的academy_number不一致，暂时先将这个条件屏蔽
            hql.append(" and c.labRoom.id ="+roomId);
        }
        if(week != 0&&week !=-1)
        {
            hql.append(" and s.startWeek <="+week+" and s.endWeek >="+week+" ");
        }
        if(weekday !=0&&weekday !=-1){
            hql.append(" and c.timetableAppointment.weekday="+weekday + " order by s.startClass asc");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        return timetableLabRelateds;
    }

    /**
     * Description 查询学期的所有课程
     * @param termId
     * @return
     * @author 陈乐为 2018-10-9
     */
    @Override
    public List<SchoolCourse> getSchoolCourseLists(int termId) {
        String sql = "select c from SchoolCourse c where c.schoolTerm.id =" + termId ;
        sql += " group by c.courseNo";
        List <SchoolCourse> schoolCourses = schoolCourseDAO.executeQuery(sql, 0,-1);
        return schoolCourses;
    }
    /*************************************************************************************
     * @throws Exception
     * @內容：导出课表
     * @作者： 廖文辉
     * @日期：2018-11-26
     *************************************************************************************/
    public void exportTimetable(SchoolTerm schoolTerm, String acno,int roomId,int week,String teacher, HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<Map> list = new ArrayList<Map>();
        int weekId = 0;
        List<LabRoom> labRooms = getLabRoomsByLabCenter(acno); //实验中心所在学院的实验室分室
        try {
            if (schoolTerm.getTermCode() != null && !"".equals(schoolTerm.getTermCode())) {
                weekId = schoolTerm.getTermCode();
            }
        } catch (Exception e) {
        }
        List<TimetableLabRelated> lbs = getLabTimetableAppointments(request,schoolTerm.getId(),roomId,week,teacher,acno);
        for (int i = 1; i <= 8; i++) {
            for (LabRoom labRoom : labRooms) {
                Map map = new HashMap();
                switch (i) {
                    case 1:
                        map.put("classes", "第一节~第二节");
                        break;
                    case 2:
                        map.put("classes", "第三节~第四节");
                        break;
                    case 3:
                        map.put("classes", "第五节~第六节");
                        break;
                    case 4:
                        map.put("classes", "第七节~第八节");
                        break;
                    case 5:
                        map.put("classes", "第九节");
                        break;
                    case 6:
                        map.put("classes", "第十节");
                        break;
                    case 7:
                        map.put("classes", "第十一节");
                        break;
                    case 8:
                        map.put("classes", "第十二节");
                }
                map.put("lab", labRoom.getLabRoomName());
                for (int iWeek = 1; iWeek <= 7; iWeek++) {
                    StringBuffer result = new StringBuffer();
                    for (TimetableLabRelated tr : lbs) {
                        if (tr.getLabRoom().getId() == labRoom.getId()) {
                            if (tr.getTimetableAppointment().getWeekday() == iWeek ) {
                                if (i <= 4) {
                                    if (tr.getTimetableAppointment().getStartClass() <= i * 2 - 1 && tr.getTimetableAppointment().getEndClass() >= i * 2 - 1 ||
                                            tr.getTimetableAppointment().getStartClass() >= i * 2 && tr.getTimetableAppointment().getEndClass() <= i * 2) {
                                        if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                            if (tr.getTimetableAppointment().getTimetableGroups().size() == 0) {
                                                //result.append(tr.getTimetableAppointment().getSchoolCourse().getCourseCode()+"-");
                                                result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                            }
                                        }
                                        for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //显示周次、节次
                                        if (tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            //节次
                                            if (tr.getTimetableAppointment().getStartClass().equals(tr.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "-" + tr.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            //周次
                                            if (tr.getTimetableAppointment().getStartWeek().equals(tr.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "-" + tr.getTimetableAppointment().getEndWeek() + "  ");
                                            }
                                        }
                                    }
                                }
                                if (i > 4) {
                                    if (tr.getTimetableAppointment().getStartClass() <= i + 4 && tr.getTimetableAppointment().getEndClass() >= i + 4) {
                                        if (tr.getTimetableAppointment().getTimetableStyle() != 5 && tr.getTimetableAppointment().getTimetableStyle() != 6) {
                                            result.append(tr.getTimetableAppointment().getSchoolCourse().getSchoolCourseInfo().getCourseName() + "  ");
                                        }
                                        for (TimetableTeacherRelated tt : tr.getTimetableAppointment().getTimetableTeacherRelateds()) {
                                            result.append(tt.getUser().getCname() + "  ");
                                        }
                                        //显示周次节次
                                        if (tr.getTimetableAppointment().getTimetableAppointmentSameNumbers().size() == 0) {
                                            //节次
                                            if (tr.getTimetableAppointment().getStartClass().equals(tr.getTimetableAppointment().getEndClass())) {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "  ");
                                            } else {
                                                result.append("节次：" + tr.getTimetableAppointment().getStartClass() + "-" + tr.getTimetableAppointment().getEndClass() + "  ");
                                            }
                                            //周次
                                            if (tr.getTimetableAppointment().getStartWeek().equals(tr.getTimetableAppointment().getEndWeek())) {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "  ");
                                            } else {
                                                result.append("周次：" + tr.getTimetableAppointment().getStartWeek() + "-" + tr.getTimetableAppointment().getEndWeek());
                                            }
                                        }
                                    }
                                }
                            }  //TimetableAppointmentSameNumbers().size()==0的情况

                        }
                    }
                    map.put("weekday" + iWeek, result);
                }  //周1-7循环
                list.add(map);
            }  //实验室遍历
        }
        SchoolTerm schoolTerms =
                schoolTermDAO.findSchoolTermById(schoolTerm.getId());
        String title = "课表";
        String[] hearders = new String[]{"节次", "实验室","星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};//表头数组
        String[] fields = new String[]{"classes","lab", "weekday1", "weekday2", "weekday3", "weekday4", "weekday5", "weekday6", "weekday7"};
        TableData td = ExcelUtils.createTableData(list, ExcelUtils.createTableHeader(hearders), fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportExcel(title, shareService.getUserDetail().getCname(), schoolTerms.getTermName(), td);
    }
}
