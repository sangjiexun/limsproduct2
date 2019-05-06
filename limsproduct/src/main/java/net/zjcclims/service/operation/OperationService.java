package net.zjcclims.service.operation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.*;

public interface OperationService {

	/**
	 * 根据主键获取实验项目对象
	 * @author hly
	 * 2015.07.29
	 */
	public OperationItem findOperationItemByPrimaryKey(Integer operationItemId);
	
	/**
	 * 根据查询条件实验项目列表
	 * @author hly
	 * 2015.07.29
	 */
	public List<OperationItem> findAllOperationItemByQuery(Integer currpage, Integer pageSize, OperationItem operationItem, Integer cid);
	
	/**
	 * 根据查询条件实验项目列表--除草稿
	 * @author hly
	 * 2015.07.29
	 */
	public List<OperationItem> findAllOperationItemExceptDraft(Integer currpage, Integer pageSize, OperationItem operationItem, String acno, int orderBy);
	
	
	/**
	 * 根据查询条件实验项目列表--增加排序
	 * @author 贺子龙
	 * 2015-11-20
	 */
	public List<OperationItem> findAllOperationItemByQuery(HttpServletRequest request,Integer currpage, Integer pageSize,
														   OperationItem operationItem, String acno, int orderBy);
	
	/**
	 * 实验室导入的时候需要默认显示当前学期之前的那个学期
	 * @author 贺子龙
	 * 2015-09-24 11:15:25
	 */
	public List<OperationItem> findAllOperationItemByQueryImport(Integer currpage, Integer pageSize, OperationItem operationItem, String acno);
	/**
	 * 实验室导入的时候需要默认显示当前学期之前的那个学期--增加排序
	 * @author 贺子龙
	 * 2015-09-24 11:15:25
	 */
	public List<OperationItem> findAllOperationItemByQueryImport(Integer currpage, Integer pageSize, OperationItem operationItem, String acno, int orderBy);
	
	
	/**
	 * 根据查询条件实验项目记录数量
	 * @author hly
	 * 2015.07.29
	 */
	public Integer findAllOperationItemByQueryCount(HttpServletRequest request,OperationItem operationItem, String acno);
	
	/**
	 * 根据查询条件实验项目记录数量--除草稿外
	 * @author hly
	 * 2015.07.29
	 */
	public Integer findAllOperationItemExceptDraft(OperationItem operationItem, String acno);
	
	/**
	 * 保存实验项目
	 * @author hly
	 * 2015.07.29
	 */
	public OperationItem saveOperationItem(OperationItem operationItem);
	
	/**
	 * 删除实验项目
	 * @author hly
	 * 2015.07.29
	 */
	public boolean deleteOperationItem(Integer operationItemId);
	
	/**
	 * 提交实验项目
	 * @author hly
	 * 2015.08.06
	 */
	public OperationItem submitOperationItem(OperationItem operationItem, String acno);
	
	/**
	 * 设置项目编号
	 * @author hly
	 * 2015.08.07
	 */
	public void saveCodeCustom(OperationItem operationItem);
	
	/**
	 * 导入整个学期的实验项目
	 * @author hly
	 * 2015.08.07
	 */
	public void importTermOperationItem(Integer sourceTermId, Integer targetTermId, String acno);
	
	/**
	 * 导入选中的实验项目
	 * @author hly
	 * 2015.08.07
	 */
	public void importOperationItem(HttpServletRequest request,Integer termId, String itemIds);

    /**
     * 导入选中学期的所有实验项目
     * @author hly
     * 2015.08.07
     * @author 贺照易修改 2018-11-12
     */
    public void importAllOperationItem(HttpServletRequest request,Integer termId, Integer itemId);
	/**
	 * Description  根据学期获取当前学期下所有实验项目
	 * @param id
	 * @return
	 * @author 贺照易  2018-11-12
	 */
	public List<OperationItem> getOperationItemListBytermId(Integer id);

    /**
	 * 获取指定实验项目的材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	public List<OperationItemMaterialRecord> getItemMaterialRecordByItem(Integer itemId, Integer currpage, Integer pageSize);
	/**
	 * 获取指定实验项目的材料使用记录不分页
	 * @author 贺子龙
	 * 2015.09.10
	 */
	public List<OperationItemMaterialRecord> getItemMaterialRecordByItem(Integer itemId);
	/**
	 * 获取指定实验项目的材料使用记录数量
	 * @author hly
	 * 2015.08.10
	 */
	public int getItemMaterialRecordByItemCount(Integer itemId);
	
	/**
	 * 保存实验项目材料使用记录
	 * @author hly
	 * 2015.08.10
	 */
	public OperationItemMaterialRecord saveItemMaterialRecord(OperationItemMaterialRecord operationItemMaterialRecord);
	
	/**
	 * 删除实验项目使用材料记录
	 * @author hly
	 * 2015.08.10
	 */
	public boolean deleteItemMaterialRecord(Integer mrId);
	
	/**
	 * 根据主键获取实验项目使用材料记录
	 * @author hly
	 * 2015.08.10
	 */
	public OperationItemMaterialRecord findItemMaterialRecordByPrimaryKey(Integer lpmrId);
	
	/**
	 * 获取指定实验项目的设备
	 * @author hly
	 * 2015.08.19
	 */
	public List<OperationItemDevice> getItemDeviceByItem(Integer itemId, Integer category, Integer currpage, Integer pageSize);
	
	/**
	 * 获取指定实验项目的设备数量
	 * @author hly
	 * 2015.08.19
	 */
	public int getItemDeviceByItemCount(Integer itemId, Integer category);
	
	/**
	 * 批量保存实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	public void saveItemDevice(Integer itemId, String category, String ids);
	
	/**
	 * 删除实验项目设备
	 * @author hly
	 * 2015.08.19
	 */
	public boolean deleteItemDevice(Integer itemDeviceId);
	
	/**
	 * 根据学院和角色获取用户数据
	 * @author hly
	 * 2015.08.28
	 */
	public List<Map<String, String>> getUserByAcademyRole(String academyNumber, String role);
	
	/**
	 * 根据学院和角色获取用户数据--加权限
	 * @author hly
	 * 2015.08.28
	 */
	public List<Map<String, String>> getUserByAcademyRole(String academyNumber, String role, int authorityId);
	
	/**********************************
	 * 功能：找出operationitem表中的创建者字段
	 * 作者：贺子龙
	 * 时间：2015-09-02
	 *********************************/
	
	public Map<String, String>getsome();
	

	/**********************************
	 * 功能：找出operationitem表中的课程段
	 * 作者：贺子龙
	 * 时间：2015-09-02
	 *********************************/
	public Map<String, String>getCourse(String acno);
	
	/**********************************
	 * 功能：找出operationitem表中的课程段(属于当前登录用户的)
	 * 作者：贺子龙
	 * 时间：2015-11-12
	 *********************************/
	public Map<String, String>getCourse(String acno,String username);
	/*************************************************************************************
     * 功能：查找所有的运行记录
     * 作者 ：徐文
     * 日期：2016-05-27
     ***************************************************************************************/
	public int  getOutlinelist(OperationOutline operationOutline,int currpage,int pagesize,int sid);
	/*************************************************************************************
     * 功能：查找所有的运行记录分页
     * 作者 ：徐文
     * 日期：2016-05-27
     ***************************************************************************************/
	public List<OperationOutline>  getOutlinelistpage(OperationOutline operationOutline,int currpage,int pagesize,String acno);

	/***************************************************************************************
     * 功能 ：查找大纲
     * 作者：徐文
     * 日期：2016-05-27
     **************************************************************************************/
	public  OperationOutline   getoperationoutlineinfor(int idkey);
	/***********************************************************************************
     * 功能 ： 查找未被大纲使用的项目卡项目卡数
     * 作者：徐文
     * 日期：2016-05-27
     ***********************************************************************************/
	public List<OperationItem>  getoperationItemlist();
	/***********************************************************************************
     * 功能 ： 查找所以课程info
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	@SuppressWarnings("rawtypes")
	public   Map  getschoolcouresMap(String acno);
	/***********************************************************************************
     * 功能 ： 查找所在专业
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	public List<SchoolMajor> getschoolmajerSet(String acno);
	/***********************************************************************************
     * 功能 ：查学分
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	@SuppressWarnings("rawtypes")
	public Map  getcoperationscareMap();
	/***********************************************************************************
     * 功能 ：查开课学院
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	@SuppressWarnings("rawtypes")
	public Map   getoperationstartschooleMap(String acno);
	/***********************************************************************************
     * 功能 ：查找课程性质
     * 作者：徐文
     * 日期：2016-05-30
     ***********************************************************************************/
	public List<CDictionary>  getcommencementnatureSet();
	/*********************************************************************************
	 * 功能:保存大纲内容
	 * 作者：徐文
	 * 日期：2016-05-30
	 ********************************************************************************/
	public  OperationOutline  saveoperationoutline(OperationOutline operationOutline,String[] schoolMajors,String[] commencementnaturemap,String[] projectitrms);
	/*********************************************************************************
	 * 功能:实验室大纲多文件上传
	 * 作者：徐文
	 * 日期：2016-06-01
	 ********************************************************************************/
	 public String   uploaddnolinedocment(HttpServletRequest request, HttpServletResponse response, Integer id);
	 /*********************************************************************************
	  * 功能:item搜索
	  * 作者：徐文
	  * 日期：2016-06-01
	  ********************************************************************************/
	 public  List<OperationItem> getitem(String a );
	 /****************************************************************************
	  * 功能：删除实验室大纲
	  * 作者：徐文
	  * 日期：2016-06-01
	  ****************************************************************************/
	 public void  delectloutline(int idkey);
	 
	 /*********************************************************************************
		 *@description:实验室项目多文件上传
		 *@author: 郑昕茹
		 *@date：2016-11-09
		 ********************************************************************************/
	public String uploadItemdocument(HttpServletRequest request, HttpServletResponse response, Integer id, int type);
	
	/*********************************************************************************
	 *@description:实验室审核记录
	 *@author: 张愉
	 *@date：2017-9-29
	 ********************************************************************************/
	public List<OperItemAudit> findAllOperItemAuditsByOperItemId(int Id);
	
	/*********************************************************************************
	 *@description:软件审核记录
	 *@author: 张愉
	 *@date：2017-10-10
	 ********************************************************************************/
	public List<SoftwareReserveAudit> findAllSoftwareReserveAuditBysoftwareReserveId(int Id);
	/*********************************************************************************
	 *@description:实训项目审核记录
	 *@author: 张愉
	 *@date：2017-10-12
	 ********************************************************************************/
	public List<OperItemAudit> findAllOperaItemAuditByoperaItemId(int Id);
	
	/****************************************************************************
	 *Description：查找所有实训项目审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	public List<OperItemAudit> findAllOperItemAuditBy(OperationItem oparetionItem,String username);
	/****************************************************************************
	 *Description：查找所有实训项目审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	public List<OperItemAudit> findAllOperItemAuditsForAudit();
	
	/****************************************************************************
	 *Description：查找所有实训项目审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	public List<OperItemAudit> findAllOperItemAuditsByUsername();
	
	/****************************************************************************
	 *Description：根据条件查找课题审核信息
	 *
	 *@author：张德冰
	 *@date:2018-3-6
	 ****************************************************************************/
	public List<OperationItem> findAllOperItemAuditBy2(OperationItem oparetionItem,String academyNumber,int currpage,int pageSize);
	/************************************************************************
	 *@Description:保存教研室主任审核
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	public Integer saveDepartmentHeaderAudit(OperationItem operationItem,String auditUsername,Integer audit,String mem,Integer flag);
	/************************************************************************
	 *@Description:查询课程库审核情况
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	public List<OperItemAudit> findAllOperItemAuditByOId(OperationItem operationItem);
	/************************************************************************
	 *@Description:保存实训室管理员审核
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	public Integer saveLabRoomManagerAudit(OperationItem operationItem,String auditUsername,Integer audit,String mem,Integer flag);
	/************************************************************************
	 *@Description:保存实训室管理员审核
	 *@Author:黄浩
	 *@Date:2018年6月12日
	 ************************************************************************/
	public Integer saveEXCenterDirectorAudit(OperationItem operationItem,String auditUsername,Integer audit,String mem,Integer flag);

	/**
	 * 作者 薛帅
	 * 功能 ：查找实验卡片
	 * */
	public  OperationItem  getopertioniteminfor(int idkey);
	/************************************************************************
	 *@Description:通过school_course_info和查询条件查找operation_item
	 *@Author:杨新蔚
	 *@Date:2018年7月30日
	 ************************************************************************/
	public List<OperationItem> findOperationItemByCourseNumber(OperationItem operationItem,String courseNumber, int currpage,int pageSize);

	/************************************************************************
	 *@Description:通过school_course_info和查询条件查找operation_item数量
	 *@Author:杨新蔚
	 *@Date:2018年7月30日
	 ************************************************************************/
	public int countOperationItemByCourseNumber(OperationItem operationItem,String courseNumber);

	/***********************************************************************************
	 * 功能 ： 通过class_id查找实验大纲
	 * 作者：戴昊宇
	 * 日期：2017-09-20
	 ***********************************************************************************/
	public List<OperationOutline> getoperationOutlinebyclass(String courseNumber);

	/********************************************************************************
	 * Description:实验项目{根据课程编号查找所有审核通过的实验项目卡}
	 * @author: 戴昊宇
	 * @date: 2017-08-25
	 *********************************************************************************/
	public List<OperationItem> findOperationItemByCourseNumber(String courseNumber);

	/**
	 * Description 实验大纲总计
	 *
	 * @author 陈乐为
	 * @date 2017-9-30
	 */
	public int getAllOutlineCount(OperationOutline operationOutline, String acno);
	/**
	 * 找到当前outline的所有moutlinecourse的记录
	 * @author 陆少凯
	 * 2017.10.06
	 */
	public List<MOutlineCourse> findMoutlineCourseByOutlineId(int outline_id);

	/**
	 * 找到当前outline的所有operationoutlinecourse的记录
	 * @author 陆少凯
	 * 2017.10.06
	 */
	public List<OperationOutlineCourse> findOperationOutlineCourseByOutlineId(int outline_id);
	/***********************************************************************************
	 * 功能 ： 查找所有课程info
	 * 作者：吴奇臻
	 * 日期：2018-06-29
	 ***********************************************************************************/
	public   List<SchoolCourseInfo>  getSchoolCourseInfo();
	/***********************************************************************************
	 * 功能 ： 查找所以课程info
	 * 作者：徐文
	 * 日期：2016-05-30
	 ***********************************************************************************/
	@SuppressWarnings("rawtypes")
	public   Map  getschoolcouresMap1();

	/*********************************************************************************
	 * 功能:保存大纲内容
	 * 作者：徐文
	 * 日期：2016-05-30
	 ********************************************************************************/
	public  OperationOutline  saveoperationoutline(OperationOutline operationOutline,String[] schoolMajors,String[] commencementnaturemap,String[] projectitrms,String[] operationOutlineTeacher);
	/*********************************************************************************
	 * 功能:保存大纲的必修与选修
	 * 作者：陆少凯
	 * 日期：2016-10-02
	 ********************************************************************************/
	public  void  savemoutlinecourse(OperationOutline operationOutline,String[] schoolMajors,String[] electiveCourses);
	/*******************************
	 * @功能：课程大纲单行数据导出
	 * @作者：张秦龙
	 * @时间：2017-12-5
	 ****************************/
	public void exportOutLine(int idkey,HttpServletRequest request,HttpServletResponse response) throws Exception;

	/*********************************************************************************
	 *@description:实验室项目多文件上传
	 *@author: 郑昕茹
	 *@date：2016-11-09
	 ********************************************************************************/
	public String uploadItemdocument(HttpServletRequest request, HttpServletResponse response, Integer id);
	/********************************************************************************
	 * Description:课程代码和课程名称联动查询
	 * @author: 廖文辉
	 * @date: 2018-03-13
	 *********************************************************************************/
	public String LinkCourseNumberAndCourseName(String CourseName);
	/**************************************************************************************
	 * description：导入教学进度安排
	 * @author：郑昕茹
	 * @date：2016-12-19
	 **************************************************************************************/
	public void importCourse(String File,Integer id);
	/*************************************************************************************
	 * @Description:通过课程编号、字典名称和字典编号查找operation_item
	 *
	 * @author: 杨新蔚
	 * @date: 2018/8/23
	 *************************************************************************************/
	public List<OperationItem> findOperationItemByCourseNumberAndStatus(String courseNumber,String cCategory,String dnumber);

	/**
	 * 删除实验大纲教学进度安排
	 * @param operationOutlineCourse
	 * 贺照易 2018-9-27
	 */
	public void deleteThisOperationOutlineCourse(OperationOutlineCourse operationOutlineCourse);

	/**
	 * Description 根据条件查询项目
	 * @param item
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	public List<OperationItem> findOperationItemForLims(OperationItem item, String acno, int currpage, int pageSize);

	/**
	 * Description 根据条件查询项目数
	 * @param item
	 * @param acno
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	public int findOperationItemCountForLims(OperationItem item, String acno);

	/**
	 * Description 根据学院和角色获取用户数据
	 * @param academyNumber 学院
	 * @param authorityName 权限
	 * @return
	 * @author 陈乐为 2018-8-25
	 */
	public List<User> getUserByQuery(String academyNumber, String authorityName);

	/**
	 * Description 保存实验项目设备
	 * @param itemId
	 * @param ids
	 * @author 陈乐为 2018-8-25
	 */
	public void saveItemDeviceLims(Integer itemId, String ids);

	/***********************************************************************************
	 * @功能：找到与schoolcourseinfo相关联的实验项目
	 * @author 戴昊宇
	 * @日期：2018-03-22
	 * **********************************************************************************/
	public List<OperationItem> findOperationItemBycourseNumber(String courseNumber);

	/***********************************************************************************
	 * 功能 ：保存teacher
	 * 作者：戴昊宇
	 * 日期：2017-09-18
	 * @author 陈乐为 修改2017-9-30
	 ***********************************************************************************/
	public void saveOperationOuterlineTeacher(int outlineId,String[] operationOutlineTeacher );

	/***********************************************************************************
	 * 功能 ：保存major
	 * 作者：徐文
	 * 日期：2016-05-31
	 ***********************************************************************************/
	public void saveSystemMajor(int outlineId,String[] systemmajor );

	/***********************************************************************************
	 * 功能 ：保存课程性质
	 * 作者：贺子龙
	 * 日期：2016-05-31
	 ***********************************************************************************/
	public void saveoperationoutlineproperty(int outlineId,String[] courseproperty );

	/***********************************************************************************
	 * 功能 ：保存item
	 * 作者：徐文
	 * 日期：2016-05-31
	 ***********************************************************************************/
	public void saveoperationoutlineitems(int outlineId,String[] courseitems );
}
