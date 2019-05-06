package net.zjcclims.service.system;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.persist.Transactional;

import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SchoolClasses;
import net.zjcclims.domain.User;
import net.zjcclims.domain.UserCard;

public interface UserDetailService {

	/*************************************************************************************
	 * @內容：根据学院编号获取用户的总记录数
	 * @作者： 叶明盾
	 * @日期：2014-08-14
	 *************************************************************************************/
	public int getUserTotalRecords(String number);
	
	/*************************************************************************************
	 * @內容：根据学院编号获取用户的总记录数
	 * @作者： 叶明盾
	 * @日期：2014-08-14
	 *************************************************************************************/
	public int getUserTotalRecords(User user,String number);
	
	/*************************************************************************************
	 * @內容：获取查询的用户数
	 * @作者： 何立友
	 * @日期：2014-09-23
	 *************************************************************************************/
	public int getUserTotalRecordsByQuery(String queryStr);
	
	/*************************************************************************************
	 * @內容：查找所有的用户信息
	 * @作者： 叶明盾
	 * @日期：2014-08-14
	 *************************************************************************************/
	public List<User> findAllUsers(int curr, int size);
	
	/*************************************************************************************
	 * @內容：获取用户user的分页列表信息
	 * @作者： 叶明盾
	 * @日期：2014-08-18
	 *************************************************************************************/
	public List<User> findUserByQuery(User user, int curr, int size,String number);
	
	/*************************************************************************************
	 * @內容：根据用户的Username查找出相应的用户信息
	 * @作者： 叶明盾
	 * @日期：2014-09-02
	 *************************************************************************************/
	public User findUserByNum(String id);
	
	/*************************************************************************************
	 * @內容：查找指定学院所有的用户信息
	 * @作者： 何立友
	 * @日期：2014-09-24
	 *************************************************************************************/
	public List<User> findUsersByAcademy(String academyNumber);
	
	/*************************************************************************************
	 * @內容：根据学院编号和user对象查询User并分页
	 * @作者： 李小龙
	 *************************************************************************************/
	public List<User> getUserTotalRecords(User user, String number, int currpage, int pageSize);
	
	public List<User> findAllUsers(String userRole);
	
	
	/**
	 * 获取当前学年下的本学院学生
	 * 裴继超
	 * 2016年7月12日17:18:32
	 */
	public List<User> findUsersByAcademyAndGrade(String academyName, int year);

	/**
	 * 获取当前学院下的学年列表
	 * 裴继超
	 * 2016年7月12日17:18:32
	 */
	public List<User> findGradesByAcademy(String academyName);
	
	/**************************************************************************
	 * Description:系统管理-用户管理-获取用户的总记录数
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	@Transactional
	public int getUserTotalRecords(User user);
	
	/**************************************************************************
	 * Description:系统管理-用户管理-查找所有的用户信息
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> findUsers();
	
	/**************************************************************************
	 * Description:系统管理-用户管理-根据user对象查询User并分页
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> getUserTotalRecords(User user,int currpage, int pageSize);
	
	/**************************************************************************
	 * Description:系统管理-用户管理-新建User对象
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	public void saveUser(User user);
	
	/**************************************************************************************
     * description：根据学院名称找到学院
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public SchoolAcademy findSchoolAcademyByAcademyName(String academyName);
	
	/**************************************************************************************
     * description：根据班级名称找到班级
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public SchoolClasses findSchoolClassesByClassesName(String className);
	
	/**************************************************************************************
     * description：导入用户列表
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public void importUser(String File);
	
	/***********************************************************************
	 * 功能：导入文件前的日期格式、数字格式检查
     * 作者：郑昕茹
     * 日期：2016-12-19
	 ************************************************************************/
	public String checkRegex(String filePath) throws ParseException;
	/***********************************************************************
	 * 功能：查找班级
	 * 作者：廖文辉
	 * 日期：2018-08-29
	 ************************************************************************/
	public int findSchoolClasses(SchoolClasses schoolClasses);
    /**
     * 功能：列出班级表的记录数
     * 作者：廖文辉
     * 日期：2018/8/30
     */
	public List<Object[]> findSchoolClasses(SchoolClasses schoolClasses,int currpage,
												 int pageSize);
    /**
     * 功能：根据班级号统计班级人数
     * 作者：姜新剑
     * 日期：2016.1.15
     */
	public int classesNumber(String classNumber);
    /**
     * 功能：添加学院下拉选择框
     * 作者：姜新剑
     * 日期：2016.1.12
     */
	public Map<String,String> getAllSchoolAcademy();
    /**
     * 功能：添加z专业下拉选择框
     * 作者：姜新剑
     * 日期：2016.1.12
     */
	public Map<String,String> getAllSchoolMajor();
    /**
     * 功能：通过班级编号找学生
     * 作者：廖文辉
     * 日期：2018/8/29
     */
    public List<Object[]> findUserByClassNumber(String classNumber);
    /**
     * 功能：根据当前班级号查找学院号
     * 作者：廖文辉
     * 日期：2018/8/30
     */
    public String findUserAcadenybyClassNumber(String classNumber);
    /**
     * 功能：查找班级号不是当前班级号的学生
     * 作者：廖文辉
     * 日期：2018.8.30
     */
    public List<Object[]> findUserByOtherClassNumber(String classNumber,String academy,Integer currpage,int pageSize);
    /*************************************************************************************
     * @内容：导入班级
     * @作者：裴继超
     * @日期：2016年3月3日
     **************************************************************************************/
    public String importClasses(String File) ;
    public User findUserByUsername(String username);
    /**
     * 保存用户
     * 裴继超
     * 2016年1月25日10:42:24
     */
    public void storeUser(User user);
	/**
	 * 根据工号找卡号
	 * 廖文辉
	 * 2018年9月3日
	 */
	public List<UserCard> findCardNoByUserName(String username);

}