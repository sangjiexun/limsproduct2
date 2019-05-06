package net.zjcclims.service.timetable;

import net.zjcclims.constant.MonthReport;
import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public interface TimetableAppointmentService {

	/*************************************************************************************
	 * @內容：查看所有的时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableAppointment> getTimetableAppointmentsByQuery(int termId,TimetableAppointment timetableAppointment,int status,int curr,
			int size,String acno);

	/*************************************************************************************
	 * @內容：查看计数的所有时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountTimetableAppointmentsByQuery(int termId,TimetableAppointment timetableAppointment,int status,String acno);
	
	/*************************************************************************************
     * @內容：查看所有的时间列表安排
     * @作者：贺子龙
     * @日期：2016-04-09
     *************************************************************************************/
    public List<TimetableAppointment> getTimetableAppointmentsByQuery(int termId,String courseNumber,int status,int curr,
            int size,int flag);

    /*************************************************************************************
     * @內容：查看计数的所有时间列表安排
     * @作者：贺子龙
     * @日期：2016-04-09
     *************************************************************************************/
    public int getCountTimetableAppointmentsByQuery(int termId,String courseNumber,int status, int flag);
	
	public String[] getTimetableWeekClass(int[] intWeeks);
	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有二次排课时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableLabRelated> getReListLabTimetableAppointments(HttpServletRequest request,String acno,int term);
	
	/*************************************************************************************
	 * @內容：根据实验室和节次及星期列出所有自建课程排课时间列表安排
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<TimetableLabRelated> getSelfListLabTimetableAppointments(HttpServletRequest request,String acno,int term);

	/*************************************************************************************
	 * @內容：发布所选选课组所在的排课内容
	 * @作者： 魏誠
	 * @日期：2014-08-4
	 *************************************************************************************/
	@Transactional
	public void doReleaseTimetableAppointments(String courseCode,int flag) ;

	/*************************************************************************************
	 * @內容：保存调整排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	public SchoolCourseDetail saveAdjustTimetable(HttpServletRequest request) throws ParseException;
	
	/*************************************************************************************
	 * @內容：保存二次排课的不分组排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	public TimetableAppointment saveNoGroupReTimetable(HttpServletRequest request)  throws ParseException ;
	
	/*************************************************************************************
	 * @內容：保存二次排课的分组排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	public TimetableAppointment saveGroupReTimetable(HttpServletRequest request)  throws ParseException ;
	
	/*************************************************************************************
	 * @內容：排课管理中，保存修改的排课的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
     *************************************************************************************/
	public void saveAdminTimetable(HttpServletRequest request);
	
	/*************************************************************************************
	 * @內容：排课管理中，删除排课信息的内容
	 * @作者： 魏誠
	 * @日期：2014-08-5
	 *************************************************************************************/
	public void deleteAppointment(int id) ;
	/*************************************************************************************
	 * @內容：排课管理中，确认
	 * @作者：孙虎
	 * @日期：2017-11-20
	 *************************************************************************************/
	public void confirmAppointment(int id, int flag, String remark);
	
	/*************************************************************************************
	 * @內容：进行termid,获取教务选课组编号
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public String getAdminCourseCodeList(int term, String acno);

	/*************************************************************************************
	 * @throws ParseException 
	 * @內容：根据学期和中心获取月报报表
	 * @作者： 贺子龙
	 * @日期：2015-12-27
	 *************************************************************************************/
	public List<MonthReport> getMonthReports(int term, int year, int month,
			String acno) throws ParseException;
	
	/*************************************************************************************
	 * @description：获取所有排课相关的教师
	 * @author： 郑昕茹
	 * @date：2016-11-30
	 *************************************************************************************/
	public  List<User>  getAllTimetableRelatedTeachers();
	
	/*************************************************************************************
	 * @throws ParseException 
	 * @內容：保存课程进度的内容
	 * @作者： 戴昊宇
	 * @日期：2017-07-23
	 *************************************************************************************/

	public SchoolCourseDetail saveTimetableAp(HttpServletRequest request) throws ParseException;
	/*************************************************************************************
	 * @throws ParseException 
	 * @內容：保存课程进度编辑的内容
	 * @作者： 戴昊宇
	 * @日期：2017-07-23
	 *************************************************************************************/

	public SchoolCourseDetail saveTimetableApedit(HttpServletRequest request) throws ParseException;
	/*************************************************************************************
	 * @內容：课程进度中，删除课程进度的内容
	 * @作者： 戴昊宇
	 * @日期：2017-07-20
	 *************************************************************************************/
	
	public void deleteTimetableAppointment(int id);
	/*************************************************************************************
	 * 功能：排课--根据courseCode（课程编号或者自主排课编号）获取相关排课记录list
	 * 作者：贺子龙
	 * 日期：2016-07-16
	 *************************************************************************************/
	public List<TimetableAppointment> findAppointmentByCourseCode(String courseCode);
	/*************************************************************************************
	 * 功能：排课--判断排课的用到的周次是不是在可用周次范围内(保存前，保存后)
	 * 作者：贺子龙
	 * 日期：2016-07-13
	 *************************************************************************************/
	public boolean isInValidWeeks(Integer appointmentId, Integer gapWeek,  HttpServletRequest request, Integer term);
	/*************************************************************************************
	 * 功能：顺延排课--生成一条提醒
	 * 作者：贺子龙
	 * 日期：2016-07-16
	 *************************************************************************************/
	public String createAlertForAppointment(int appointmentId, int gapWeek);
	/*************************************************************************************
	 * @throws ParseException 
	 * @內容：保存调整课程的内容
	 * @作者： 戴昊宇
	 * @日期：2017-09-23
	 *************************************************************************************/
	@SuppressWarnings("unused")
	@Transactional
	public SchoolCourseDetail saveTimetableadjustment2(HttpServletRequest request,Integer tapId) throws ParseException ;
	/*************************************************************************************
	 * @內容：排课管理--停课
	 * @作者： 戴昊宇
	 * @日期：2017-10-10
	 *************************************************************************************/
	public void disableAppointment(int id);
	/*************************************************************************************
	 * @內容：查询排课时间
	 * @作者： 戴昊宇
	 * @日期：2017-11-08
	 *************************************************************************************/
	public List<SchoolTermActive> findSchoolTermActiveByTerm(int term);
	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：孙虎
	 * @日期：2017-11-22
	 *************************************************************************************/
	public List<Object[]> getViewLabroomTimetableRegisters(TimetableAppointment timetableAppointment,int curr,int size);
	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图数量
	 * @作者：孙虎
	 * @日期：2017-11-22
	 *************************************************************************************/
	public int getViewLabroomTimetableRegistersCount(TimetableAppointment timetableAppointment);
	/*************************************************************************************
	 * @throws ParseException 
	 * @內容：保存调出内容(调出类型为2 调出实验室)
	 * @作者： 戴昊宇
	 * @日期：2017-11-22
	 *************************************************************************************/
	public TimetableAppointmentChange  saveAdjustMent(HttpServletRequest request)throws ParseException;
	/*************************************************************************************
	 * @throws ParseException 
	 * @內容：保存调出内容（调出类型为1   实验室内调整记录保存）
	 * @作者： 戴昊宇
	 * @日期：2017-11-23
	 *************************************************************************************/
	public TimetableAppointmentChange  saveAdjustMent2(HttpServletRequest request) throws ParseException;
	/****************************************************************************
	 *Description：查找所有调课申请
	 *@author：孙虎
	 *@date:2017-11-24
	 ****************************************************************************/
	public List<TimetableAppointmentChange> findAllTimetableAppointmentChange(TimetableAppointmentChange appointmentChange, Integer page, int pageSize,int tage,int isaudit);
	/*************************************************************************************
	 * Description 保存不同身份的审核结果
	 * @author 孙虎
	 * @throws NoSuchAlgorithmException 
	 * @date 2017-11-5
	 *************************************************************************************/
	public TimetableAppointmentChange saveAuditResultForTimetable(TimetableAppointmentChange appointmentChange, TimetableAppointmentChangeAduit appointmentChangeAduit) throws NoSuchAlgorithmException;
	
	//获取上课确认列表数据
	public List<Object[]> getViewLabroomTimetableRegistersConfirm(String courseNumber,int curr,int size);
	//获取上课确认列表数量
	public int getViewLabroomTimetableRegistersConfirmCount(String courseNumber,int curr,int size);
	
	//需要上课确认的数据推送到我的消息中
	public String saveMessageAppointment(int id);
	
	/**
	 * Description 保存排课审核结果
	 * @param courseCode 选课组编号
	 * @param flag 排课类型（自主排课、教务排课等）
	 * @param status 审核前审核状态值
	 * @param result 审核结果
	 * @author 陈乐为 2018-3-7
	 */
	@Transactional
	public void saveTimetableAppAudit(String courseCode, int flag, int status, int result,String remark,int labroomId,int tag);
	
	/************************************************************

	 * @功能：判断排课是否冲突
	 * @作者：张德冰
	 * @日期：2018.03.30
	 ************************************************************/
	public int getSzie(Integer term, int[] classes, int[] weeks,Integer weekday, String[] teachers, String courseCode);
	/************************************************************

	 * @功能：查看实验室管理员
	 * @作者：戴昊宇
	 * @日期：2018.06.13
	 ************************************************************/
	public List<LabRoomAdmin> getlabRoomAdmin(int labroomId);
	/************************************************************

	 * @功能：查看实验室管理员
	 * @作者：戴昊宇
	 * @日期：2018.06.13
	 ************************************************************/
	public List<TimetableLabRelated> getlabRelated(int id);

	/**
	 * 实验室自主排课记录-getSelfListLabTimetableAppointments方法已乱
	 * @param request
	 * @param term
	 * @return
	 * @author 陈乐为 2018-7-28
	 */
	public List<TimetableLabRelated> getSelfCourseLabTimetableAppointments(
			HttpServletRequest request, int term);

	/*************************************************************************************
	 * 功能：根据主键查找相应的timetableAppointment
	 * 作者：戴昊宇
	 * 日期：2017-08-26
	 *************************************************************************************/
	public TimetableAppointment findTimetableAppointmentByPrimaryKey(int id);
	/************************************************************

	 * @功能：查看自主排课关联实验室
	 * @作者：杨新蔚
	 * @日期：2018/08/31
	 ************************************************************/
	public List<TimetableLabRelated> getlabRelatedByAppointmentID(int id);


	/***********************************************************************************************
	 * Description：排课模块{保存教务实验课}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-31
	 ***********************************************************************************************/
	public TimetableAppointment saveSchoolTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
													int weekday, String teachers, String courseNo, Integer isOther,String teachers2,int[] items);

	/***********************************************************************************************
	 * Description：排课模块通用{根据选课组编号获取排课记录}
	 * @author：戴昊宇
	 * @Date：2017-08-25
	 ***********************************************************************************************/
	public List<TimetableAppointment> findTimetableAppointmentByCourseCode(String courseNo);

	/***********************************************************************************************
	 * Description：排课模块通用{根据选课组编号获取排课记录}
	 * @author：戴昊宇
	 * @Date：2017-08-25
	 ***********************************************************************************************/
	public List<TimetableAppointment> findCycleTimetableAppointmentByCourseNo(String courseNo);

    /***********************************************************************************************
     * Description：查询循环分批排课
     * @author：戴昊宇
     * @Date：2018-03-07
     ***********************************************************************************************/
    public List<TimetableAppointment> findCycleBatchTimetableAppointmentByCourseNo(String courseNo);

	/***********************************************************************************************
	 * Description：排课模块通用{根据选课组编号和分组id获取排课记录}
	 * @author：贺子龙
	 * @Date：2016-08-02
	 ***********************************************************************************************/
	public List<TimetableAppointment> findTimetableAppointmentByCourseAndGroup(String courseNo, int groupId);

	/**
	 * 查询所有学生
	 * @param user 查询对象
	 * @param page 页数
	 * @param pageSize 查询页面大小
	 * @return 符合条件的学生对象列表
	 * @author 黄保钱 2018-9-18
	 */
	public List<User> findStudent(User user, Integer page, Integer pageSize, Integer id);

	/**
	 * 查询学生数量
	 * @param user 查询对象
	 * @return 符合条件的所有学生对象数量
	 * @author 黄保钱 2018-9-18
	 */
	public Integer countStudent(User user, Integer id);
    /**
     * 根据周次和星期查询日期
     * @return 符合条件的所有对象数量
     * @author 廖文辉 2018-10-30
     */
    public List<SchoolWeek> getDate(int week,int weekday);
	/**
	 * 查询老师的课程
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableTeacherRelated> getTeacherTimetable(TimetableAppointment timetableAppointment);
	/**
	 * 查询实验室的课程
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableLabRelated> getLabTimetable(TimetableAppointment timetableAppointment);
	/**
	 * 查询学生的课程
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableGroupStudents> getStudentTimetable(TimetableAppointment timetableAppointment);
	/**
	 * 根据登录人查询教务排课
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<SchoolCourseStudent> getSchoolCourseStudent();
	/**
	 * 根据登录人查询自主排课
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<TimetableCourseStudent> getTimetableCourseStudent();
	/**
	 * 查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-10-30
	 */
	public List<Object[]> getTimetable(String date,String courseName,String weeks);
	/**
	 * 根据当前时间和学院查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-1
	 */
	public List<Object[]> getTimetableByDateAndSchoolAcaemy(String date,String acno);
    /**
     * 根据当前周和学院查询课表数据
     * @return 符合条件的所有对象数量
     * @author 廖文辉 2018-11-2
     */
    public List<Object[]> getCurrentWeekTimetable(String Firstdate,String Lastdate,String acno,String weeks);
	/**
	 * 根据当前学期和学院查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-2
	 */
	public List<Object[]> getCurrentTermTimetable(SchoolTerm schoolTerm,String acno);
	/**
	 * 根据当前周和学生查询课表数据
	 * @return 符合条件的所有对象数量
	 * @author 廖文辉 2018-11-2
	 */
	public List<Object[]> getStudentCurrWeekTimetable(String weeks);
	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：廖文辉
	 * @日期：2017-11-22
	 *************************************************************************************/
	public List<Object[]> findViewLabroomTimetableRegisters(TimetableAppointment timetableAppointment,int curr,int size,HttpServletRequest request,String acno);
	/**
	 * Description 计数
	 * @param timetableAppointment
	 * @param request
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-12-25
	 */
	public int findTimetableRegisterCounts(TimetableAppointment timetableAppointment,HttpServletRequest request,String acno);
	/*************************************************************************************
	 * @內容：获取ViewLabroomTimetableRegister视图
	 * @作者：廖文辉
	 * @日期：2018-11-09
	 *************************************************************************************/
	public int findViewLabroomTimetableRegistersCount(TimetableAppointment timetableAppointment,HttpServletRequest request,String acno);

	/**
	 * @Description 根据日期和实验室查询课表数据
	 * @author 张德冰
	 * @date 2018-11-21
	 */
	public List<Object[]> getLabCurrDayTimetable(HttpServletRequest request,Integer currpage,Integer pagesize);

	/**
	 * @Description 根据日期和实验室查询预约结果
	 * @author 张德冰
	 * @date 2018-11-21
	 */
	public List<Object[]> getLabCurrDayReservation(HttpServletRequest request,Integer currpage,Integer pagesize);
}