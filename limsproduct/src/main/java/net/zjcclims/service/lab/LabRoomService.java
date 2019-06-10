package net.zjcclims.service.lab;

import net.zjcclims.constant.LabAttendance;
import net.zjcclims.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface LabRoomService {

	/**
	 * 根据主键获取实验室对象
	 * @author hly
	 * 2015.07.28
	 */
	public LabRoom findLabRoomByPrimaryKey(Integer labRoomId);
	
	/**
	 * 根据查询条件获取实验室数据
	 * @author hly
	 * 2015.07.28
	 */
	public List<LabRoom> findAllLabRoomByQuery(Integer currpage, Integer pageSize, LabRoom labRoom);
	/**
	 * 根据查询条件获取实验室数据--增加排序
	 * @author hly
	 * 2015.07.28
	 */
	public List<LabRoom> findAllLabRoomByQuery(Integer currpage, Integer pageSize, LabRoom labRoom,int orderBy,HttpServletRequest request);
	/**
	 * 根据查询条件获取实验室数据--增加排序，增加选择中心筛选
	 * @author 廖文辉
	 * 2018.08.26
	 */
 	public List<LabRoom> findLabRoomByLabCenter(Integer currpage, Integer pageSize,int type, LabRoom labRoom,int orderBy,HttpServletRequest request,String acno);
	/**
	 * 获取可开门实验室数据
	 * @author 刘博越
	 * 2019.5.29
	 */
	public List<LabRoom> findLabRoomOpenDoorByLabCenter(Integer currpage, Integer pageSize, LabRoom labRoom,String username,HttpServletRequest request,String acno);
 	/**
	 * 根据是否可以开门进行排序
	 * @author 刘博越
	 * 2019.05.27
	 */
	public List<LabRoom> sortLabRoomByAgent(List<LabRoom> labRooms,String username);
	/**
	 * 保存实验室数据
	 * @author hly
	 * 2015.07.28
	 */
	public LabRoom saveLabRoom(LabRoom labRoom);
	
	/**
	 * 删除实验室数据
	 * @author hly
	 * 2015.07.28
	 */
	public boolean deleteLabRoom(Integer labRoomId);
	
	/**
	 * 根据主键获取实验室工作人员对象
	 * @author hly
	 * 2015.07.29
	 */
	public LabWorker findLabWorkerByPrimaryKey(Integer labWorkerId);
	
	/**
	 * 根据查询条件获取实验室工作人员数据
	 * @author hly
	 * 2015.07.29
	 */
	public List<LabWorker> findAllLabWorkerByQuery(Integer currpage, Integer pageSize, LabWorker labWorker);
	
	/**
	 * 保存实验室工作人员数据
	 * @author hly
	 * 2015.07.29
	 */
	public LabWorker saveLabWorker(LabWorker labWorker);
	
	/**
	 * 删除实验室工作人员数据
	 * @author hly
	 * 2015.07.29
	 */
	public boolean deleteLabWorker(Integer labWorkerId);
	
	/**
	 * 获取指定实验中心下的实验室
	 * @author hly
	 * 2015.08.18
	 */
	public List<LabRoom> findLabRoomByLabCenterid(String acno, Integer isReservation);
	
	/****************************************************************************
	 * 功能：根据实验室id和用户判断用户是否为该实验室的实验室管理员
	 * 作者：贺子龙
	 * 时间：2015-09-03
	 ****************************************************************************/
	public boolean getLabRoomAdminReturn(Integer id, User user);
	/****************************************************************************
	 * 功能：查询出所有的实验项目卡
	 * 作者：贺子龙
	 * 时间：2015-09-03
	 ****************************************************************************/
	public List<OperationItem> findAllOperationItem(String number);
	/****************************************************************************
	 * 功能：根据条件查询出实验项目
	 * 作者：张德冰
	 * 时间：2018.03.09
	 ****************************************************************************/
	public List<OperationItem> findOperationItemByRoomId(Integer id,OperationItem operationItem);
	/****************************************************************************
	 * 功能：根据实验室id查询出所有的实验项目
	 * 作者：张德冰
	 * 时间：2018.03.09
	 ****************************************************************************/
	public List<OperationItem> findAllOperationItemByRoomId(Integer id);
	/****************************************************************************
	 * 功能：根据实验室查询实验室硬件
	 * 作者：贺子龙
	 * 时间：2015-09-04
	 ****************************************************************************/
	public List<LabRoomAgent> findLabRoomAgentByRoomId(Integer id);

	/****************************************************************************
	 * 功能：保存实验室的实验项目
	 * 作者：贺子龙
	 * 时间：2015-09-07
	 ****************************************************************************/
	public void saveLabRoomOperationItem(LabRoom room, String[] str);
	/****************************************************************************
	 * 功能：删除实验室的实验项目
	 * 作者：贺子龙
	 * 时间：2015-09-07
	 ****************************************************************************/
	public void deleteLabRoomOperationItem(LabRoom room, OperationItem m);
	/****************************************************************************
	 * 功能：根据user对象和学院编号查询用户并分页
	 * 作者：贺子龙
	 * 修改：2015-09-08
	 ****************************************************************************/
	public List<User> findUserByUserAndSchoolAcademy(User user,Integer roomId,
			String academyNumber, Integer page, int pageSize);
	/****************************************************************************
	 * 功能：根据user对象和学院编号查询用户数量
	 * 作者：贺子龙
	 * 修改：2015-09-08
	 ****************************************************************************/
	public int findUserByUserAndSchoolAcademy(User user,Integer roomId,
			String academyNumber);
	/****************************************************************************
	 * 功能：根据roomId查询该实验室的门禁
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabRoomAgent> findLabRoomAgentAccessByRoomId(Integer roomId);
	/****************************************************************************
	 * 功能：根据deviceNumber查找实验室电源控制器
	 * 作者：贺子龙
	 ****************************************************************************/
	public LabRoomAgent findGuardByRemark(String deviceNumber, int labId);
	/*************************************************************************************
	 * @功能：根据学院查询实验室并分页--默认显示当前学院的
	 * @作者： 贺子龙 
	 *************************************************************************************/
	public List<LabRoom> findLabRoomBySchoolAcademyDefault(
			LabRoom labRoom, int page, int pageSize,int type, String acno);
	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录数量--增加查询功能
	 * @作者：贺子龙
	 *************************************************************************************/
	public int findLabRoomAccessByIpCount(CommonHdwlog commonHdwlog,String ip,String port,HttpServletRequest request);
	/*************************************************************************************
	 * @功能：根据ip查询刷卡记录并分页--增加查询功能
	 * @作者：贺子龙
	 *************************************************************************************/
	public List<LabAttendance> findLabRoomAccessByIp(CommonHdwlog commonHdwlog,String ip,String port, Integer page,
			int pageSize,HttpServletRequest request);
	
	/****************************************************************************
	 * 功能：根据中心id和查询条件查询该中心的实验室数量
	 * 作者：李小龙
	 ****************************************************************************/
	public int findAllLabRoom(LabRoomDevice device, String acno, Integer isReservation) ;
	
	/****************************************************************************
	 * 功能：根据中心id查询该中心的实验室（分页）
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabRoom> findLabRoomByLabCenterid(LabRoomDevice device,String acno, Integer page, int pageSize);

	/**
	 * 统计实验室数量
	 * @param labRoom
	 * @param username
	 * @return
	 */
	public int countLabRoomListByQuery(LabRoom labRoom, String username);

	/**
	 * 查询实验室列表
	 * @param labRoom
	 * @param username
	 * @param currpage
	 * @param pageSize
	 * @return
	 */
	public List<LabRoom> findLabRoomListByQuery(LabRoom labRoom,
			String username, Integer currpage, int pageSize);

	/**
	 * 根据实验室和类型查询管理员
	 * @return
	 */
	public List<User> findAllLabRoomAdmins(LabRoom room,Integer type);
	
	/****************************************************************************
	 * 功能：保存实验分室的视频
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public void saveLabRoomVideo(String fileTrueName, Integer labRoomid);
	
	/****************************************************************************
	 * 功能：保存实验分室的文档
	 * 作者：李小龙
	 * 时间：2014-07-29
	 ****************************************************************************/
	public void saveLabRoomDocument(String fileTrueName, Integer labRoomid,Integer type);
	
	/****************************************************************************
	 * 功能：查询某一实验中心下有设备的实验室
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabRoom> findLabRoomWithDevices(Integer isReservation);
	
	/****************************************************************************
	 * 功能：根据中心id查询该中心存放有设备的实验室（分页）
	 * 作者：贺子龙
	 ****************************************************************************/
	public List<LabRoom> findLabRoomWithDevices(LabRoomDevice device, Integer page, int pageSize,Integer isReservation,String acno,HttpServletRequest request);

	/**
	  *@comment：根据user对象和学院编号查询用户数量
	  *@param user、cid、academyNumber
	  *@return：
	  *@author：叶明盾
	  *@date：2015-10-28 下午10:35:05
	 */
	public int findUserByUserAndAcademy(User user,Integer cid,String academyNumber);
	
	/**
	  *@comment：根据user对象和学院编号查询用户并分页
	  *@param user、cid、academyNumber、page、pageSize
	  *@return：
	  *@author：叶明盾
	  *@date：2015-10-28 下午10:35:05
	 */
	public List<User> findUserByUserAndAcademy(User user,Integer cid,String academyNumber, Integer page, int pageSize);

	/****************************************************************************
	 * 功能：删除排课相关的实验室禁用记录
	 * 作者：贺子龙
	 * 时间：2016-05-28
	 ****************************************************************************/
	public void deleteLabRoomLimitTimeByAppointment(int id);
	
	/**************************************************************************************
     * description：根据所属类型和名称找到字典表中的记录
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public CDictionary findCDictionaryByLwAcademicDegreeByNameAndCategory(String name, String category);
	
	/**************************************************************************************
     * description：导入实验室人员记录
	 * @throws ParseException 
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public void importLabWorker(String File) throws ParseException;

	/**************************************************************************************
     * description：导入实验室安全准入名单
     * @author：Hezhaoyi
     * @date：2019-4-15
	 **************************************************************************************/
	public void importSecurityAccess(String File,Integer labRoomId);

	/****************************************************************************
	 * 功能：通过username和deviceId查询labRoomPermitUser
	 * 作者：贺子龙
	 * update Hezhaoyi 2019-4-14
	 ****************************************************************************/
	public LabRoomPermitUser findPermitUserByUsernameAndLab(String username, int labId);
	/***********************************************************************
	 * 功能：导入文件前的日期格式、数字格式检查
     * 作者：郑昕茹
     * 日期：2016-08-05
	 ************************************************************************/
	public String checkRegex(String filePath) throws ParseException;
	
	/****************************************************************************
	 *Description：查找所有实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-26
	 ****************************************************************************/
	public int getReturnedLendingTotals();
	
	/****************************************************************************
	 *Description：查找所有实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-26
	 ****************************************************************************/
	public List<LabRoomLending> findAllLending(
			LabRoomLending labRoomLending, Integer page, int pageSize);
	
	/****************************************************************************
	 *Description：查找所有审核通过的实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-26
	 ****************************************************************************/
	public List<LabRoomLending> findAllPassLending(
			LabRoomLending labRoomLending, Integer page, int pageSize);
	
	/****************************************************************************
	 *Description：查找所有审核未通过的实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-26
	 ****************************************************************************/
	public List<LabRoomLending> findAllRejectLending(
			LabRoomLending labRoomLending, Integer page, int pageSize);
	
	/****************************************************************************
	 *Description：查找所有审核未通过的实训室借用信息
	 *
	 *@author：邵志峰
	 *@date:2017-06-28
	 ****************************************************************************/
	public List<LabRoomLending> findAllWaitLending(
			LabRoomLending labRoomLending, Integer page, int pageSize);
	
	/****************************************************************************
	 *Description：根据id查找实训室借用单
	 *
	 *@author：邵志峰
	 *@date:2017-06-28
	 ****************************************************************************/
	public LabRoomLending findLabRoomLendingById(Integer idKey);
	
	/****************************************************************************
	 *Description：查找所有未审核的实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-28
	 ****************************************************************************/
	public int getWaitLendingTotals();
	
	/****************************************************************************
	 *Description：查找所有审核通过的实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-28
	 ****************************************************************************/
	public int getPassLendingTotals();
	
	/****************************************************************************
	 *Description：查找所有审核拒绝的实训室借用记录总数
	 *
	 *@author：邵志峰
	 *@date：2017-06-28
	 ****************************************************************************/
	public int getRejectLendingTotals();
	
	/****************************************************************************
	 *Description：查找所有实验室
	 *
	 *@author：张愉
	 *@date：2017-07-6
	 ****************************************************************************/
	public Set<LabRoom> findallLabRoom();
	/****************************************************************************
	 *Description：查找所有实验室
	 *
	 *@author：廖文辉
	 *@date：2018-09-13
	 ****************************************************************************/
	public List<LabRoom> findLabRoomList();
	public void deviceVideoUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id);

	public void labRoomDocumentUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id, int i);
	
	/****************************************************************************
	 * 功能：根据实训室id查询培训
	 * 作者：孙虎
	 ****************************************************************************/
	public List<LabRoomTraining> findLabRoomTrainingById(LabRoomTraining train,	Integer Id);
	/****************************************************************************
	 * 功能：根据培训查询培训名单
	 * 作者：孙虎
	 ****************************************************************************/
	public List<LabRoomTrainingPeople> findTrainingPeoplesByTrainingId(int id);
	/****************************************************************************
	 * 功能：通过labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomPermitUser> findPermitUserByLabRoomId(LabRoomPermitUser labRoomPermitUser,int labRoomId, int page, int pageSize);
	/****************************************************************************
	 * 功能：通过labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomPermitUser> findPermitUserByLabRoomId(
			Integer labRoomId, int currpage, int pageSize);
	/****************************************************************************
	 * 功能：根据user对象和学院编号查询所有学生
	 * 作者：周志辉
	 * 时间：2017-08-21
	 ****************************************************************************/
	public int findStudentByCnameAndUsername(User user, String academyNumber, Integer labRoomId);
	/****************************************************************************
	 * 功能：根据user对象和学院编号查询所有学生
	 * 作者：周志辉
	 * 时间：2017-08-21
	 ****************************************************************************/
	public List<User> findStudentByCnameAndUsername(User user, String academyNumber, Integer labRoomId, Integer page, int pageSize);
	/****************************************************************************
	 * 功能：根据设备id查询培训
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomTraining> findLabRoomTrainingByLabRoomId(LabRoomTraining train,
			Integer labRoomId);
	/****************************************************************************
	 * 功能：通过username和labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public LabRoomPermitUser findPermitUserByUsernameAndDeivce(String username, int labRoomId);
	/****************************************************************************
	 * 功能：当前用户取消已经预约的培训
	 * 作者：周志辉
	 ****************************************************************************/
	public void cancleTraining(int trainingId);
	/****************************************************************************
	 * 功能：根据培训查询培训名单--已通过的学生
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomTrainingPeople> findTrainingPassPeoplesByTrainingId(
			int id);
	/****************************************************************************
	 * 功能：通过username和labRoomId查询labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public LabRoomPermitUser findPermitUserByUsernameAndLabRoom(String username, int labRoomId);
	/****************************************************************************
	 * 功能：删除labRoomPermitUser
	 * 作者：周志辉
	 ****************************************************************************/
	public void deletePermitUser(LabRoomPermitUser user);
	/****************************************************************************
	 * 功能：根据培训id查询培训通过的人
	 * 作者：周志辉
	 ****************************************************************************/
	public List<LabRoomTrainingPeople> findPassLabRoomTrainingPeopleByTrainId(
			Integer id);
	/****************************************************************************
	 * 功能：更新某一设备下所有预约的审核结果
	 * 作者：周志辉
	 * @throws ParseException 
	 ****************************************************************************/
	public void updateReservationResult(int labRoomId) throws ParseException;
	
	/****************************************************************************
	 * 功能：判断用户是否通过安全准入
	 * 作者：周志辉
	 ****************************************************************************/
 	 public String securityAccess(String username, Integer id, HttpServletRequest request);
	/****************************************************************************
	 * 功能：当前用户是否lab_room_attention记录中(实验室)
	 * 作者：贺子龙
	 ****************************************************************************/
	public boolean isInTheReaderList(int labId);
	/****************************************************************************
	 * 功能：当前用户是否lab_room_attention记录中(设备)
	 * 作者：贺子龙
	 ****************************************************************************/
	public boolean isInTheReaderListDevice(String deviceNumber);
	/****************************************************************************
	 * 功能：自动生成一条lab_room_attention记录(设备)
	 * 作者：贺子龙
	 ****************************************************************************/
	public void generateLabRoomAttentionRecordDevice(String deviceNumber);

	/****************************************************************************
	 * 功能：自动生成一条lab_room_attention记录
	 * 作者：贺子龙
	 ****************************************************************************/
	public void generateLabRoomAttentionRecord(int labId);

	/****************************************************************************
	 * 功能：当前实验室的lab_room_attention失效（当预约注意事项有修改时）--设备
	 * 作者：贺子龙
	 ****************************************************************************/
	public void disableAllAttentionRecordDevice(String deviceNumber);

	/****************************************************************************
	 * 功能：当前设备下lab_room_attention
	 * 作者：贺子龙
	 ****************************************************************************/
	public List<LabRoomAttention> findLabRoomAttentionByDevice(String deviceNumber, int page, int pageSize);

	/****************************************************************************
	 * 功能：当前设备下lab_room_attention数量
	 * 作者：贺子龙
	 ****************************************************************************/
	public int countLabRoomAttentionByDevice(String deviceNumber);

	/****************************************************************************
	 * 功能：当前设备下lab_room_attention数量
	 * 作者：贺子龙
	 ****************************************************************************/
	public int countLabRoomAttentionByLab(int labId);


	/****************************************************************************
	 * 功能：当前实验室下lab_room_attention
	 * 作者：贺子龙
	 ****************************************************************************/
	public List<LabRoomAttention> findLabRoomAttentionByLab(int labId, int page, int pageSize);
	/**
	 * 根据查询条件获取实验室人员培训数据
	 * @author 周志辉
	 * 2017.08.29
	 */
	public List<LabWorkerTraining> findAllLabWorkerTrainingByQuery(Integer currpage, Integer pageSize, int labWorkerId);

	/****************************************************************************
	 *Description：查找所有软件借用信息
	 *
	 *@author：张愉
	 *@date:2017-09-30
	 ****************************************************************************/
	public List<SoftwareReserve> findAllsoftwareLend(SoftwareReserve softwareReserve, Integer page, int pageSize,int tage,int isaudit);
	public int softwareLendTotals();
	
	/****************************************************************************
	 *Description：查找所有软件借用信息-审核状态
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	public List<SoftwareReserve> findAllsoftwareLendAudit(SoftwareReserve softwareReserve, Integer page, int pageSize,int state);
	
	/****************************************************************************
	 *Description：查找所有软件借用审核信息
	 *
	 *@author：张愉
	 *@date:2017-10-10
	 ****************************************************************************/
	public List<SoftwareReserveAudit> findAllSoftwareReserveAudit(SoftwareReserve softwareReserve, String username);
	/****************************************************************************
	 *Description：判断实训室是否进行了预约的初始化设置
	 *
	 *@author：孙虎
	 *@date:2017-10-12
	 ****************************************************************************/
	public Boolean isSettingForLabRoom(Integer labRoom);

	/**************************************
	 * 功能：学院下的设备统计
	 * 作者：贺子龙
	 * 日期：2016-03-01
	 **************************************/
	public List listSchoolDeviceAcademy(String academyNumber);

	/**************************************
	 * 功能：根据实训室名称查询实训室
	 * 作者：张德冰
	 * 日期：2018.03.15
	 **************************************/
	public LabRoom findLabRoomByLabRoomName(String labRoomName);

	/****************************************************************************
	 * 功能：根据中心id查询该中心的实验室
	 * 作者：李小龙
	 ****************************************************************************/
	public List<LabRoom> findLabRoomByLabCenterid(Integer cid);

	/**
	 * Description 获取实际审核状态
	 * @param labRoom 所要审核申请的实验室
	 * @return 实际审核状态
	 * @author 黄保钱 2018-08-24
	 */
	public Integer getAuditNumber(LabRoom labRoom, Integer state);
	/**
	 * Description 实验室预约实验分室下拉框
	 * @param labRoom 所要预约的实验室
	 * @return 实验中心
	 * @author 廖文辉 2018-08-27
	 */
	public List<LabRoom> findLabRoom(String acno, HttpServletRequest request);

	/**
	 * Description 根据实验室编号找到实验室下所有门禁的端口号
	 * @param roomId 实验室主键
	 * @author 陈乐为 2018-8-29
	 */
	public String findAgentPortByRoomId(Integer roomId);


	/****************************************************************************
	 * Description 查询出所有实验室
	 *
	 * @author 戴昊宇
	 * @date 2017-8-10
	 ****************************************************************************/
	public List<LabRoom> findAllLabroom();

	/**
	 * Description 同步电源控制器相关策略
	 * @param flag 0 关电源，1 开电源
	 * @param insUid 硬件id
	 * @return
	 * @throws IOException
	 * @author 陈乐为 2018-9-10
	 */
	public String syncSmartAgent(Integer flag, String insUid)throws IOException;
	/**
	 * Description 根据学院查找用户
	 * @param roomId 实验室ID
	 * @param acno 学院编号
	 * @return
	 * @author 廖文辉 2018-10-17
	 */
	public List<User> findUserByAcademy(Integer roomId, String acno);
    /****************************************************************************
     * 功能：根据设备查询实验室硬件
     * 作者：廖文辉
     * 时间：2018-12-12
     ****************************************************************************/
    public List<LabRoomAgent> findLabRoomAgentByDeviceId(Integer id);
	/**
	 * Description 导入物联和实验室管理员
	 * @param File 文件路径
	 * @return
	 * @author 廖文辉 2018-12-24
	 */
	public String[] importLabRoomAdmin(String File,Integer roomId,Integer typeId);
	/**
	 * Description 根据username和typeId找实验室管理员
	 * @param username 用户名
	 *  @param typeId 管理员种类
	 * @return
	 * @author 廖文辉 2018-12-24
	 */
	public List<LabRoomAdmin> findLabRoomAdminByUsernameAndType(int roomId, String username,int typeId);
	/********************************
	 * 功能：管理员上传
	 * 作者：廖文辉
	 * 日期：2018-12-26
	 *********************************/
	public String adminUpload(HttpServletRequest request);
	/**
	 * Description 导入仪器设备
	 * @param File 文件路径
	 * @return
	 * @author 廖文辉 2018-12-26
	 */
	public String[] importLabRoomDevice(String File,Integer roomId)throws Exception;
	/**
	 * Description 项目实验室下拉框
	 *
	 * @return
	 * @author 廖文辉 2019-01-07
	 */
	public List<LabRoom> findLabRoomBySchoolAcademy(String acno);
	/**
	 * Description 查找本学院的人
	 *
	 * @return
	 * @author 廖文辉 2019-01-10
	 */
	public List<User> findUserByacno(String academyNumber);
	/**
	 * Description 查找本学期的操作日志
	 *
	 * @return
	 * @author 廖文辉 2019-01-10
	 */
	public List<Object[]> getRefuseItemBackup(Integer roomId);

    /**
     * Description 刷新权限接口（jwt）
     *
     * @param id 实验室id
     * @author 黄保钱 2019-1-27
     */
	String sendRefreshInterfaceByJWT(Integer id);

	/**
	 * Description 远程开门接口（jwt）
	 *
	 * @param agentId 实验室id
	 * @param doorIndex 门号
	 * @author 陈乐为 2019-2-23
	 */
	public String sendOpenDoorInterfaceByJWT(Integer agentId, Integer doorIndex);

	/**
	 * Description 开关电源控制器（jwt）
	 * @param flag 1开，0关
	 * @param agentId 硬件id
	 * @return
	 * @author 陈乐为 2019-3-5
	 */
	public String sendOpenAgentInterfaceByJWT(Integer flag, Integer agentId);

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
	public List<Object[]> getLabDoorRecords(String username, String cname, String startDate,
											String endDate, String labRoomName);

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
	int getLabDoorRecordsNum(String username, String cname, String startDate, String endDate, String labRoomName);

	/**
	 * Description 保存批量设置的实验室管理员/物联管理员
	 * @param type_code
	 * @param labs
	 * @param users
	 * @return
	 * @author 陈乐为 2019-4-3
	 */
	public String saveMultipleManager(int type_code, String[] labs, String[] users);

	/**
	 * @Description: 查询某一实验中心下有设备的实验室
	 * @Author: 徐明杭
	 * @CreateDate: 2019/4/4 14:01
	 */
	public List<LabRoom> findLabRoomWithDevices(Integer isReservation,String acno);

	/**
	* @Description: 根据时间和内容的输入返回labRoomDeviceLending
	* @Author: 徐明杭
	* @CreateDate: 2019/4/9 16:31
	*/
	LabRoomDeviceLending checkTimeAndContent(String startTime,String returnTime, String content);
	/**
	* @Description: 导出实验室分室所有考勤记录
	* @Author: 徐明杭
	* @CreateDate: 2019/4/11 13:31
	*/
	void exportEntranceList(List<net.zjcclims.common.LabAttendance> labAttendances,HttpServletRequest request,HttpServletResponse response) throws Exception;

	/**
	 * Description 获取实验室某一类型可用的设备列表
	 * @param lab_id
	 * @param c_number
	 * @param c_catagory
	 * @return
	 * @author 陈乐为 2019年4月29日
	 */
	public List<LabRoomAgent> getAgentByType(Integer lab_id, String c_number, String c_catagory);
}
