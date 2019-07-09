package net.zjcclims.service.common;

import com.alibaba.fastjson.JSONObject;
import excelTools.People;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

public interface ShareService {
	
	/**
	 * 获取字典数据
	 * @author hly
	 * 2015.08.03
	 */
	public List<CDictionary> getCDictionaryData(String category);
	
	/**
	 * 根据类别和编号获取字典数据
	 * @author hly
	 * 2015.08.04
	 */
	public CDictionary getCDictionaryByCategory(String category, String number);
	
	/*
	 * 分页显示
	 */

	@Transactional
	public Map<String, Integer> getPage(int currpage, int pageSize, int totalRecords);

	/*
	 * 获取登录人的所有信息
	 */

	public User getUser();

	/*
	 * 处理中文乱码 作者：彭文玉
	 */

	public String htmlEncode(String str);

	/*
	 * 获得当前页数
	 */

	public int getCurrpage(HttpServletRequest request);

	/*
	 * 获取所有的周次；
	 */

	public Map getWeeksMap(int termId);

	/*
	 * 获取所有的星期； 作者：彭文玉
	 */

	public Map getWeekdays();

	/***********************************************************************************************
	 * @功能：遍历两个日期之间是否有周末
	 * @作者：贺子龙
	 * @日期：2017-04-14
	 ***********************************************************************************************/
	public boolean isInWeedend(Date date1, Date date2) throws Exception;

	/**
	 * Description 纺织学院设备预约{判断设备预约时间是否被占用}
	 * @param labRoomLimitTimes 实验室限制时间
	 * @param startDate 预约起始时间
	 * @param endDate 预约使用截止时间
	 * @param deviceId 预约设备id
	 * @return
	 * @author 陈乐为 2018-9-30
	 */
	public boolean isLimitedByAppointment(List<LabRoomLimitTime> labRoomLimitTimes,Calendar startDate,Calendar endDate,Integer deviceId);

    /***********************************************************************************************
     * @throws ParseException
     * @功能：通用模塊service層定義-判断指定实验室的禁用时间段内，根据日历表的起始时间和结束时间判断是否可用
     * @作者：魏诚
     * @日期：2016-03-08
     ***********************************************************************************************/
    public boolean isLimitedByTimeNew(Integer id,Calendar startDate,Calendar endDate) throws ParseException;

	/***********************************************************************************************
	 * @功能：判断两个日期是否为同一天
	 * @作者：贺子龙
	 * @日期：2016-08-07
	 ***********************************************************************************************/
	public boolean isSameDate(Date date1, Date date2);


    /*
	 * 获取所有的用户列表； 作者：彭文玉
	 */

	public Map getUsersMap();

	/*
	 * 得到所有的学期 作者：彭文玉
	 */

	public Map getTermsMap();

	/*
	 * 获取所有的周次 作者：彭文玉
	 */

	public Map getWeekMap();

	/*
	 * 得到本周的周次 作者：彭文玉
	 */

	public int findNewWeek();

	/*
	 * 密码加密md5； 鲁静 2013.08.26
	 */

	public String createMD5(String s);

	/*
	 * 获取当前日期 鲁静 2013.09.04
	 */

	public String getDate();

	/***********************************************************************************************
	 * 获取登录用户对象
	 * 作者：魏诚 
	 * 日期：2014-07-15
	 ***********************************************************************************************/
	public User getUserDetail();
	/***********************************************************************************************
	 * 获取上传文件的保存路径
	 * 作者：李小龙 
	 * 日期：2014-07-27
	 ***********************************************************************************************/
	public String getUpdateFilePath(HttpServletRequest request);
	/***********************************************************************************************
	 * 查询和当前登录人同一学院的用户
	 * 作者：李小龙 
	 ***********************************************************************************************/
	public List<User> findTheSameCollegeUser(String academyNumber);
	/***********************************************************************************************
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 作者：李小龙
	 ***********************************************************************************************/
	public boolean deleteDocument(String documentUrl);
	/***********************************************************************************************
	 * 将日期类型转化为String
	 * 作者：李小龙
	 ***********************************************************************************************/
	public String format(Calendar appDate);
	/***********************************************************************************************
	 * 根据学院编号查询实验项目
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<OperationItem> findOperationItemBySchoolAcademy(
			String academyNumber);
	/***********************************************************************************************
	 * 根据路径删除指定的目录（文件夹）以及目录下的文件
	 * 作者：李小龙
	 ***********************************************************************************************/
	public boolean deleteMenu(String documentUrl);
	/***********************************************************************************************
	 * 查询和当前登录人同一学院的老师
	 * 作者：李小龙 
	 ***********************************************************************************************/
	public List<User> findTheSameCollegeTeacher(String academyNumber);
	/***********************************************************************************************
	 * 判断时间所属的学期
	 * 作者：李小龙 
	 ***********************************************************************************************/
	public SchoolTerm getBelongsSchoolTerm(Calendar time);
	
	/***********************************************************************************************
	 * 获取指定学期和学院的可用课程
	 * 作者：魏诚 
	 ***********************************************************************************************/
	public List<SchoolCourse> getSchoolCourseList(int termId);
	/***********************************************************************************************
	 * 根据权限id查询用户
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<User> findUsersByAuthorityId(int i);
	/***********************************************************************************************
	 * 根据权限名字查询用户
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<User> findUsersByAuthorityName(String authorityName);
	/***********************************************************************************************
	 * 找到某学院的所有系主任和系教学秘书
	 * 作者：孙虎
	 ***********************************************************************************************/
	public List<User> findDeansByAcademyNumber(SchoolAcademy schoolAcademy);
	/***********************************************************************************************
	 * 查询所有的学期并倒序排列
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<SchoolTerm> findAllSchoolTerms();
	/***********************************************************************************************
	 * 查询所有的十二个学院
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<SchoolAcademy> findAllSchoolAcademys();

	/***********************************************************************************************
	 * 根据排课记录登录用户判断是否用户仅为老师权限，且是否为当前用户，或者为超级管理员或实验中心主任
	 * 作者：魏诚
	 ***********************************************************************************************/
	public boolean isOrNotAuthority(TimetableAppointment timetableAppointment);
	/***********************************************************************************************
	 * 获取所有的部门
	 * 作者：李小龙
	 ***********************************************************************************************/
	public Set<SchoolAcademy> getAllSchoolAcademy();
	/***********************************************************************************************
	 * 构建文件夹
	 * 作者：李小龙
	 ***********************************************************************************************/
	public File createDirdown();
	/***********************************************************************************************
	 * 下载文件
	 * 作者：李小龙
	 ***********************************************************************************************/
	public void downloadFile(String fileName, HttpServletRequest request,
			HttpServletResponse response);
	/***********************************************************************************************
	 * 查询排课所属学期的周次数量
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int getTermNumber(TimetableAppointment t);
	/***********************************************************************************************
	 * 判断当前时间是否在指定时间（周次、星期）之后
	 * 作者：李小龙
	 ***********************************************************************************************/
	public boolean getTimeMark(Integer week, Integer weekday);
	/***********************************************************************************************
	 * 根据对象生成二维码，并返回二维码保存路径
	 * 作者：李小龙
	 ***********************************************************************************************/
	public String getDimensionalCode(LabRoomDevice d) throws Exception;
	/***********************************************************************************************
	 * 获取当前时间所属的周次星期信息
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int getBelongsSchoolWeek(Calendar time);
	/***********************************************************************************************
	 * 获取当前时间所属的周次星期信息
	 * 作者：李小龙
	 ***********************************************************************************************/
	public int getBelongsSchoolWeekday(Calendar time);
	/***********************************************************************************************
	 * 获取已经排过课的课程
	 * 作者：李小龙
	 ***********************************************************************************************/
	public Set<SchoolCourse> findSchoolCourse();
	/***********************************************************************************************
	 * 根据课程获取教师
	 * 作者：李小龙
	 ***********************************************************************************************/
	public Set<User> findSchoolCourseTeachers(Set<SchoolCourse> schoolCourses);
	/***********************************************************************************************
	 * 根据选课组编号获取学生名单
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<SchoolCourseStudent> findStudentByCourseNo(String courseNo);
	/***********************************************************************************************
	 * 根据学生名单和周次统计学生实到次数
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<People> findPeopleByStudentsAndWeek(
			List<SchoolCourseStudent> studentList, Integer week,String courseNo);
	/***********************************************************************************************
	 * 根据选课组编号和周次统计缺勤次数
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<People> findAbsenceByStudentsAndWeek(Integer week, String courseNo);
	
	/**
	 * 判断指定用户是否具有指定权限
	 * @param username 用户工号
	 * @param authStr 权限标识
	 * @return boolean
	 * @author hely
	 * 2015.08.25
	 */
	public boolean checkAuthority(String username, String authStr);
	/***********************************************************************************************
	 * 获取所有教师数据
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public List<User> findAllTeacheres();
	/***********************************************************************************************
	 * @功能：获取ip地址
	 * @作者：魏诚
	 ***********************************************************************************************/
	public String getIpAddr(HttpServletRequest request);
	/***********************************************************************************************
	 * @功能：根据学号判断是否为本科生
	 * @作者：贺子龙
	 ***********************************************************************************************/
	public boolean isUndergraduate(String username);
	/***********************************************************************************************
	 * @功能：根据年、月数字获取一个时间段
	 * @作者：贺子龙
	 ***********************************************************************************************/
	public Calendar[] getTimePeriod(int year, int month) throws ParseException;
	/***********************************************************************************************
	 * 判断月份所属的学期
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public SchoolTerm getSchoolTermByMonth(int year, int month) throws ParseException;
	/***********************************************************************************************
	 * 判断该月份的起始和终止周次
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public int[] getSchoolWeekByMonth(int year, int month) throws ParseException;
	/***********************************************************************************************
	 * 判断时间所对应的周次
	 * 作者：贺子龙
	 ***********************************************************************************************/
	public SchoolWeek getSchoolWeekByDate(Calendar time) throws ParseException;
	
	/***********************************************************************************************
	 * @功能：替换特殊字符
	 * @作者：贺子龙
	 * @日期：2016-03-07
	 ***********************************************************************************************/
	public String replaceString(String oldString);
	
	/***********************************************************************************************
	 * @功能：通用模塊service層定義-根据对象生成二维码，并返回二维码保存路径
	 * @作者：李小龙
	 ***********************************************************************************************/
	public String getDimensionalCode(LabRoomDevice d, String serverName) throws Exception;

	/***********************************************************************************************
	 * @功能：通用模塊service層定義-根据对象生成二维码，并返回二维码保存路径
	 * @作者：李小龙
	 ***********************************************************************************************/
	public String getDimensionalCodeForAsset(Asset a, String serverName) throws Exception;

	/***********************************************************************************************
	 * 删除指定路径的文件
	 * 作者：李小龙
	 ***********************************************************************************************/
	public boolean deleteFile(String documentUrl);

    /***********************************************************************************************
	 * @功能：登录用户修改自己密码
	 * @作者：黄崔俊
	 ***********************************************************************************************/
	public User changeUserPassword(User user, String password);
	/**
	  *@comment：获取form上传的文件
	  *@param request
	  *@return：
	  *@author：叶明盾
	  *@date：2015-10-14 下午3:52:37
	 */
	public String getUpdateFileSavePath(HttpServletRequest request);

	/***********************************************************************************************
	 * 功能：将文件上传到指定路径并返回文件保存路径
	 * 作者：黄崔俊
	 * 时间：2015-12-14 14:02:30
	 ***********************************************************************************************/
	public String fileUpload(HttpServletRequest request, String folderName,
			String filePath);

	/***********************************************************************************************
	 * @2016-1-25 09:48:58：黄崔俊
	 * @时间：2016-1-25 09:48:58
	 ***********************************************************************************************/
	public User saveUser(User user1);
	
	/***********************************************************************************************
	 * @功能：通用模塊service層定義-上传文件
	 * @作者：魏诚 
	 * @日期：2014-07-27
	 ***********************************************************************************************/
	public void uploadFiles(HttpServletRequest request,String path,String type,int id) ;
	
	
	/***********************************************************************************************
	 * @功能：通用模塊service層定義-判断指定实验室的禁用时间段内，根据日历表的起始时间和结束时间判断是否可用
	 * @作者：魏诚
	 * @日期：2016-03-08
	 ***********************************************************************************************/
	public boolean isLimitedByTime(List<LabRoomLimitTime> labRoomLimitTimes,Calendar startDate,Calendar endDate) ;

	/***********************************************************************************************
	 * @功能：通用模塊service層定義-根据周次星期信息获取当前时间
	 * @作者：魏诚
	 * @日期：2016-03-05
	 ***********************************************************************************************/
	public String getDateByWeekdayClassWeek(int weekday,
			int startClass, int i, int term, int j);
	/***********************************************************************************************
	 * @功能：通用username找到user
	 * @作者：郑昕茹
	 * @日期：2016-08-07
	 ***********************************************************************************************/
	public User findUserByUsername(String username);
	
	/*************************************************************************************
	 * Description：排课模块-ajax获取可用周次{判断当前的实验室是否属于不需要判冲的特殊实验室}
	 * 
	 * @author： 贺子龙
	 * @date：2016-07-27
	 *************************************************************************************/
	public boolean isSpecialLabRoom(int labRoomId);
	
	/***********************************************************************************************
	 * Description:查找当前学期及以后的学期
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public List<SchoolCourse> getMyCourse();
    
    /***********************************************************************************************
	 * Descrption:当前学期
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public SchoolTerm getCurTerm();
    
	/******************************************************************
	 * Description:保存实训室借用申请表
	 * 
	 * @param：softwarereserve 软件申请表
	 * @author：邵志峰
	 * @date:2017-05-31 
	 *****************************************************************/
	@Transactional
	public int saveLabRoomLending(LabRoomLending labRoomLending);
	
	/***********************************************************************************************
	 * @description 判断时间所属的学年
	 * @author 郑昕茹
	 * @date 2016-10-16
	 ***********************************************************************************************/ 
	public SchoolYear getBelongsSchoolYear(Calendar time);
	/************************************************************ 
	 * @功能:获取节次
	 * @作者：戴昊宇
	 * @日期：2017-09-27
	 ************************************************************/
	public int getCurClass(Calendar time);
	/***********************************************************************************************
	 * 获取所有系主任数据
	 * 作者：周志辉
	 ***********************************************************************************************/
	public List<User> findAllDepartmentHead(User user);
	/***********************************************************************************************
	 * 获取所有实训室主任数据
	 * 作者：周志辉
	 ***********************************************************************************************/
	public List<User> findAllLabRoomtHead();
	
	/***********************************************************************************************
	 * Descrption:实验室借用-使用类型
	 * 
	 * @author：张愉
	 * @date：2017-10-29
	 ***********************************************************************************************/
    public CDictionary getCDictionaryforLabResrevationuse(int id);

	/**
	 * Description 消息保存接口
	 * @param receiveUser
	 * @param message
	 * @author 周志辉 2017-11-08
	 */
	public void sendMsg(User receiveUser, Message message);
    /***********************************************************************************************
	 * Descrption:短信发送接口
     * @throws NoSuchAlgorithmException 
	 * 
	 * @author：周志辉
	 * @date：2017-11-08
	 ***********************************************************************************************/
      public String sendMessage(String tel,String content) throws NoSuchAlgorithmException, InterruptedException ;

      /**************************************
  	  * 功能：根据名字查询管理员
  	  * 作者：张德冰
  	  * 日期：2018.03.15
  	  **************************************/
      public User findUserByCName(String userCName);

      /**************************************
   	  * 功能：查询状态id
   	  * 作者：张德冰
   	  * 日期：2018.03.15
   	  **************************************/
      public CDictionary findCDictionaryByCName(String status);

      /**************************************
       * 功能：根据用户名查询培训记录数量
       * 作者：张德冰
       * 日期：2018.03.19
       **************************************/
      public int counttrainings(String username);

      /**************************************
       * 功能：根据用户名查询培训记录
       * 作者：张德冰
       * 日期：2018.03.21
       **************************************/
      public List<LabRoomTrainingPeople> findTrainingRecordByUsername(
			String username);
      
      /***********************************************************************************************
    	 * 获取当前时间所属的节次
    	 * 作者：戴昊宇
    	 ***********************************************************************************************/
    	public int getBelongsClass(Calendar time);
	/************************************************************************
	 *@Description:获取所有实验中心主任lab_center表的manager
	 *@Author:孙虎
	 *@Date:2018/5/30
	 ************************************************************************/
    List<User> getLabCenterManagers();
	/************************************************************************
	 *@Description:获取某个学院的下的某个身份的全体成员
	 *@Author:孙虎
	 *@Date:2018/5/30
	 ************************************************************************/
    List<User> findAuthByAuthNameAndAcademy(String authName,String academyNumber);
	/***********************************************************************************************
	 * 找到某学院的所有教研室主任
	 * 作者：黄浩
	 ***********************************************************************************************/
	public List<User> findDepartmentHeaderByAcademyNumber(SchoolAcademy schoolAcademy);

	/**
	 * Description 获取所有实验室
	 * @return
	 * @author 陈乐为 2018-7-24
	 */
	public List<LabRoom> findAllLabRooms();

	/***********************************************************************************************
	 * 功能：下载文件
	 * 作者：李德
	 * 时间：2015-04-27
	 ***********************************************************************************************/
	public void downloadFileByDocumentId(int idkey, HttpServletRequest request,
										 HttpServletResponse response);

	/**
	 * Description 根据主键查找学期
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-8-26
	 */
	public SchoolTerm findSchoolTermByPrimaryKey(int idKey);
	/***********************************************************************************************
	 * @功能：将String转换成Calendar
	 * @作者：赵晶
	 * @日期：2017-12-25
	 ***********************************************************************************************/
	public Calendar toCalendar(String dateStr);
	/***********************************************************************************************
	 * @功能：将String转换成Calendar(精确到时分秒)
	 * @作者：赵晶
	 * @日期：2018-1-9
	 ***********************************************************************************************/
	public Calendar toCalendarDetil(String dateStr);

	/**
	 * Description 字典表数据判断
	 * @param idKey
	 * @return
	 * @author 陈乐为 2018-8-30
	 */
	public boolean checkCDictionary(Integer idKey, String cNumber, String cCategory);

	/**
	 * @Description 根据权限和实验中心获取所有人
	 * @author 张德冰
	 * @date 2018.09.12
	 */
	public List<User> findUserByCidAndAuthorities(Integer cid, Integer authoritieId);

	public JSONObject useAuditAPI(String type, String... param);
	/**
	 * 获取学期数据
	 * @author 廖文辉
	 * 2018.09.21
	 */
	public List<SchoolTerm> findSchoolTerm();

	/**
	 * Description 根据主键查询学院对象
	 * @param number
	 * @return
	 * @author 陈乐为 2018-9-21
	 */
	public SchoolAcademy findSchoolAcademyByPrimaryKey(String number);

	/**
	 * Description 获取当前系统权限等级
	 * @return
	 * @author 陈乐为 2018-9-29
	 */
	public int getLevelByAuthName(String author);

	/**
	 * Description lab_center查询
	 * @param hql
	 * @param currpage
	 * @param pagesize
	 * @return
	 * @author 陈乐为 2018-9-29
	 */
	public List<LabCenter> executeLabCenter(StringBuffer hql, int currpage, int pagesize);
	public int executeLabCenterCount(StringBuffer hql);
	/**
	 * Description 获取实验室类别字段表数据(除去实验基地）
	 * @param category 类别
	 * @return
	 * @author 廖文辉 2018-10-26
	 */
	public List<CDictionary> getLabType(String category);
	/**
	 * Description 获取学院列表
	 * @return
	 * @author 廖文辉 2018-11-01
	 */
	public List<SchoolAcademy> getSchoolAcademy();
	/**
	 * Description 获取当前周的日期
	 * @return
	 * @author 廖文辉 2018-11-02
	 */
	public List<SchoolWeek> getDate(int week);

	/**
	 * Description 根據學院和權限查找用戶
	 * @param cid
	 * @param authName
	 * @return
	 * @author 陳樂為 2017年10月2日
	 */
	public List<User> findUserByQuery(int cid, String authName);

	/**
	 * Description 根据学院、权限输出用户
	 * @param auth 权限英文名
	 * @param acno 学院编号
	 * @return
	 * @author 陈乐为 2018-11-29
	 */
	public List<User> findUsersByQuery(String auth, String acno);

	/**
	 * Description 项目可以添加的实验室设备
	 * @param labId
	 * @param itemId
	 * @return
	 * @author 陈乐为 2018-11-30
	 */
	public List<LabRoomDevice> findLabRoomDeviceByQuery(Integer labId, Integer itemId);

	/**
	 * Description 根据工号查找用户对象
	 * @param username
	 * @return
	 * @author 陈乐为 2018-12-7
	 */
	public User getUserByPrimaryKey(String username);
	/**
	 * Description 取某一个硬件
	 * @param category
	 * @param cnumber
	 * @return
	 * @author 廖文辉 2018-12-11
	 */
	public List<CDictionary> getOnlyCDictionaryData(String category,String cnumber);

	/**
	 * Description 楼宇联动实验室
	 * @param build_number
	 * @return
	 * @author 陈乐为 2018-12-17
	 */
	public String linkLabByBuild(String build_number);

	/**
	 * Description 获取未来时间段
	 * @return
	 * @author 陈乐为 2018-12-26
	 */
	public List<SystemTime> getNotObsoleteTime();

	/**
	 * Description 中心联动实验分室
	 * @param center_id
	 * @return
	 * @author 陈乐为 2019-1-2
	 */
	public String linkLabByCenter(Integer center_id);

	/**
	 * Description 获取学院下所有实验分室
	 * @param acno
	 * @labCategory 标志位（1实验分室，2工作室，3会议室）
	 * @return
	 */
	public List<LabRoom> getLabByAcademy(String acno);

	/**
	 * Description 判断设置是否需要审核
	 * @param businessType
	 * @return
	 * @author 陈乐为 2019-1-8
	 */
	public boolean getAuditOrNot(String businessType);

	/**
	 * Description 根据权限名查找对象
	 * @param authName
	 * @return
	 * @author 陈乐为 2019-1-9
	 */
	public Authority getAuthorityByName(String authName);

	/***********************************************************************************************
	 * @功能：判断时间字符串所属的节次
	 * @作者：贺子龙
	 * @日期：2016-05-18
	 ***********************************************************************************************/
	public int getSystemTimeByString(String timeString);

    /***********************************************************************************************
     * @功能：判断时间字符串所属的周次
     * @作者：贺子龙
     * @日期：2016-05-18
     ***********************************************************************************************/
    public SchoolWeek getSchoolWeekByString(String timeString);
	/***********************************************************************************************
	 * @功能：根据权限id查询当前学院的用户
	 * @作者：徐明杭
	 * @时间: 2019-3-20
	 ***********************************************************************************************/
	public List<User> findUsersByAuthorityNameAndAcno(String authorityName,String acno);

	/**
	 * Description 获取额外配置项
	 * @param businessType
	 * @return
	 * @author 黄保钱 2019-5-11
	 */
	boolean getExtendItem(String businessType);

	/**
	 * Description 保存业务流水单
	 * @param businessAppUid
	 * @param businessType
	 * @return
	 * @throws Exception
	 * @author 陈乐为 2019年5月27日
	 */
	public String saveAuditSerialNumbers(@RequestParam String businessAppUid, String businessType);

	/**
	 * Description 查询业务的当前流水单
	 * @param businessAppUid
	 * @param businessType
	 * @return
	 * @author 陈乐为 2019年5月27日
	 */
	public String getSerialNumber(@RequestParam String businessAppUid, String businessType);

    /**
     * Description 根据当前流水单反查预约id
     * @param uuid
     * @param businessType
     * @return
     * @author Hezhaoyi 2019年6月14日
     */
    public String getReservationIdBySerialNumber(@RequestParam String uuid, String businessType);

	/**
	 * Description 删除流水单
	 * @param uuid
	 * @author 陈乐为 2019年5月28日
	 */
	public void deleteSerialNumber(@RequestParam String uuid);

	/**
	 * Description 判断当前登录人是否有远程开门权限
	 * @param lab_id
	 * @return
	 * @author 陈乐为 2019年6月17日
	 */
	public boolean getAuthToOpenDoor(Integer lab_id);

	/**
	 * Description 判断业务日期是否为当天
	 * @param app_date
	 * @return
	 * @author 陈乐为 2019-6-20
	 */
	public boolean theSameDay(Date app_date);

	/**
	 * Description 获取当前数据源配置文件
	 * @param
	 * @return
	 * @author lay
	 */
	public PConfigDTO getCurrentDataSourceConfiguration();
}