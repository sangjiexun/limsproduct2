package net.zjcclims.service.device;

import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface LabRoomDeviceService {

    /****************************************************************************
     * 功能：保存实验分室设备
     * 作者：李小龙
     ****************************************************************************/
    public LabRoomDevice save(LabRoomDevice device);

    /****************************************************************************
     * 功能：根据实验分室id查询实验分室的设备
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDevice> findLabRoomDeviceByRoomId(Integer id);

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备数量
     * 作者：李小龙
     ****************************************************************************/
    public int findAllLabRoomDeviceNew(LabRoomDevice device, String acno, Integer isReservation);

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备并分页
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDevice> findAllLabRoomDeviceNew(LabRoomDevice device,String acno, Integer page, int pageSize, Integer isReservation);

    /****************************************************************************
     * 功能：删除实验室设备
     * 作者：李小龙
     ****************************************************************************/
    public void deleteLabRoomDevice(LabRoomDevice d);

    /****************************************************************************
     * 功能：根据实验分室设备主键查询对象
     * 作者：李小龙
     ****************************************************************************/
    public LabRoomDevice findLabRoomDeviceByPrimaryKey(Integer id);

    /****************************************************************************
     * 功能：根据实验室id查询管理员
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomAdmin> findLabRoomAdminByRoomId(Integer id, Integer typeId);

    /****************************************************************************
     * 功能：根据实验室查询实验室的排课
     * 作者：李小龙
     ****************************************************************************/
    public List<TimetableLabRelated> findTimetableLabRelatedByRoomId(Integer id);

    /****************************************************************************
     * 功能：查找实验室管理员
     * 作者：李鹏翔
     ****************************************************************************/
    public List<User> findAdminByLrid(Integer idKey);

    /****************************************************************************
     * 功能：判断当前登录用户是否为实验室管理员
     * 作者：李小龙
     ****************************************************************************/
    public Boolean getLabRoomAdmin(Integer roomId, String username);


    /****************************************************************************
     * 功能：上传设备图片
     * 作者：贺子龙
     * 时间：2015-09-28 14:47:21
     ****************************************************************************/
    public void deviceImageUpload(HttpServletRequest request,
                                  HttpServletResponse response, Integer id, int type);

    /****************************************************************************
     * 功能：上传设备视频
     * 作者：贺子龙
     * 时间：2015-09-28 14:47:30
     ****************************************************************************/
    public void deviceVideoUpload(HttpServletRequest request,
                                  HttpServletResponse response, Integer id);

    /****************************************************************************
     * 功能：根据设备id查询设备的预约记录
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findReservationListByDeviceId(
            Integer equinemtid);

    /****************************************************************************
     * 功能：根据设备预约信息和审核状态查询设备预约并分页
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservation(
            LabRoomDeviceReservation reservation, int cId,String acno, Integer page,
            int pageSize);

    /****************************************************************************
     * 功能：根据设备预约信息和审核状态查询设备预约并分页(我的审核)
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservation(
            LabRoomDeviceReservation reservation, Integer cId, String acno, Integer page,
            int pageSize, int tag);

    /****************************************************************************
     * 功能：找出所有设备预约的实验室
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    public Map<Integer, String> findAllLabrooms(String acno);

    /****************************************************************************
     * 功能：根据设备预约信息和审核状态查询设备预约
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findAllLabRoomDeviceReservation(
            LabRoomDeviceReservation reservation, Integer cId, Integer centerId);

    /****************************************************************************
     * 功能：找出所有设备预约的申请人
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    public Map<String, String> findAllreserveUsers(Integer centerId);

    /****************************************************************************
     * 功能：找出所有设备预约的指导老师
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    public Map<String, String> findAllTeachers(Integer centerId);

    /****************************************************************************
     * 功能：找出所有设备预约的设备管理员
     * 作者：贺子龙
     * 时间：2015-10-10
     ****************************************************************************/
    public Map<String, String> findAllManageUsers(Integer centerId);

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备
     * 作者：贺子龙
     * 时间：2015-09-22 10:24:59
     ****************************************************************************/
    public List<LabRoomDevice> findAllLabRoomDeviceNew(LabRoomDevice device, String acno);

    /**
     * 查看所有设备名称
     *
     * @param acno
     * @return
     */
    public List<LabRoomDevice> findAllLabRoomDeviceNumbers(LabRoomDevice device, String acno);

    /****************************************************************************
     * 功能：根据设备id查询培训
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingByDeviceId(LabRoomDeviceTraining train,
                                                                           Integer deviceId);

    /****************************************************************************
     * 功能：根据培训id查询培训通过的人
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceTrainingPeople> findPassLabRoomDeviceTrainingPeopleByTrainId(
            Integer id);

    /****************************************************************************
     * 功能：根据培训查询培训名单
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceTrainingPeople> findTrainingPeoplesByTrainingId(
            int id);

    /****************************************************************************
     * 功能：保存设备培训
     * 作者：李小龙
     ****************************************************************************/
    public LabRoomDeviceTraining saveLabRoomDeviceTraining(
            LabRoomDeviceTraining train);

    /****************************************************************************
     * 功能：根据培训查询培训名单--已通过的学生
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDeviceTrainingPeople> findTrainingPassPeoplesByTrainingId(
            int id);

    /****************************************************************************
     * 功能：当前用户取消已经预约的培训
     * 作者：贺子龙
     ****************************************************************************/
    public void cancleTraining(int trainingId);
    /****************************************************************************
     * 功能：当天预约审核通过后门禁注册--实验室
     * 作者：贺子龙
     ****************************************************************************/
    public void callLabDeviceReservationFunction();
    /****************************************************************************
     * 功能：当天预约审核通过后门禁注册--设备
     * 作者：贺子龙
     ****************************************************************************/
    public String refreshDeviceReservation(LabRoomDeviceReservation reservation) throws IOException;

    /****************************************************************************
     * 功能：当天预约审核通过后电源控制器注册--设备
     * 作者：贺子龙
     ****************************************************************************/
    public String refreshLabRoomDeviceReservation(LabRoomDeviceReservation reservation) throws IOException;

    /****************************************************************************
     * 功能：判断用户是否通过安全准入
     * 作者：李小龙
     ****************************************************************************/
    public String securityAccess(String username, Integer id);

    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备，添加所在实验室条件
     * 作者：李小龙
     ****************************************************************************/
    public int findAllLabRoomDevice(LabRoomDevice device, String acno, int roomId);

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    public int findStudentByCnameAndUsername(User user, String academyNumber);

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    public List<User> findStudentByCnameAndUsername(User user, String academyNumber, Integer page, int pageSize);

    /****************************************************************************
     * 功能：根据设备主键查询设备被预约记录
     * 作者：黄崔俊
     * 时间：2016-5-18 10:07:45
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findLabRoomDeviceReservationInfoByDeviceId(
            Integer labRoomDeviceId, Integer currpage, int pageSize);

    /****************************************************************************
     * 功能：根据设备主键统计设备被预约次数
     * 作者：黄崔俊
     * 时间：2016-5-18 10:07:52
     ****************************************************************************/
    public int countLabRoomDeviceReservationInfoByDeviceId(
            Integer labRoomDeviceId);

    /****************************************************************************
     * 功能：根据设备主键查询设备培训通过名单
     * 作者：黄崔俊
     * 时间：2016-5-18 10:07:52
     ****************************************************************************/
    public List<LabRoomDevicePermitUsers> findPermitUserByDeivceId(
            Integer labRoomDeviceId, int currpage, int pageSize);

    /****************************************************************************
     * 功能：根据设备id查询培训记录
     * 作者：黄崔俊
     * 时间：2016-5-19 16:46:40
     ****************************************************************************/
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingListByDeviceId(
            Integer labRoomDeviceId, Integer currpage, int pageSize);

    /****************************************************************************
     * 功能：根据设备id查询培训记录数
     * 作者：黄崔俊
     * 时间：2016-5-19 16:46:40
     ****************************************************************************/
    public int countLabRoomDeviceTrainingListByDeviceId(Integer labRoomDeviceId);

    /****************************************************************************
     * 功能：保存设备的文档
     * 作者：李小龙
     ****************************************************************************/
    public void saveDeviceDocument(String fileTrueName, Integer id, int type);


    /****************************************************************************
     * 功能：根据查询条件查询出所有的实验分室设备并分页，添加所在实验室条件
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDevice> findAllLabRoomDevice(LabRoomDevice device, String acno,
                                                    Integer page, int pageSize, int roomId);

    /****************************************************************************
     * 功能：根据设备id查询设备的预约记录
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findLabRoomDeviceReservationByDeviceNumber(String deviceNumber);

    /****************************************************************************
     * 功能：上传设备文档
     * 作者：李小龙
     ****************************************************************************/
    public void deviceDocumentUpload(HttpServletRequest request,
                                     HttpServletResponse response, Integer id, int type);

    /****************************************************************************
     * 功能：根据学期查询培训
     * 作者：李小龙
     ****************************************************************************/
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingByTermId(
            Integer termId, Integer id);

    /****************************************************************************
     * 功能：根据实验室设备查询设备预约
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDeviceReservation> findLabRoomDeviceReservationByDeviceNumberAndCid(LabRoomDeviceReservation reservation, HttpServletRequest request,
                                                                                           String deviceNumber, int page, int pageSize, int status, String acno);

    /****************************************************************************
     * 功能：根据实验室设备查询设备预约--数量
     * 作者：贺子龙
     ****************************************************************************/
    public int countLabRoomDeviceReservationByDeviceNumberAndCid(LabRoomDeviceReservation reservation, HttpServletRequest request,
                                                                 String deviceNumber, int status, Integer cid);

    /****************************************************************************
     * 功能：获取一个设备预约的使用机时数
     * 作者：贺子龙
     ****************************************************************************/
    public BigDecimal getReserveHoursOfReservation(LabRoomDeviceReservation labRoomDeviceReservation);

    /****************************************************************************
     * 功能：保存新建的出借申请单
     * 作者：李鹏翔
     ****************************************************************************/
    public LabRoomDeviceLending saveDeviceLendApply(LabRoomDeviceLending lrdl);

    /****************************************************************************
     * 功能：查找所有设备借用单的记录总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getLabRoomLendsTotals(LabRoomDeviceLending lrdl, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有设备借用单的记录总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getLabRoomKeepsTotals(LabRoomDeviceLending lrdl);

    /****************************************************************************
     * 功能：查找所有设备借用单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllLabRoomLends(LabRoomDeviceLending lrdl,
                                                          Integer page, int pageSize, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有设备领用单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllLabRoomKeeps(LabRoomDeviceLending lrdl,
                                                          Integer page, int pageSize);

    /****************************************************************************
     * 功能：根据id查找设备借用单
     * 作者：李鹏翔
     ****************************************************************************/
    public LabRoomDeviceLending findDeviceLendingById(Integer idKey);

    /****************************************************************************
     * 功能：查找审核结果map
     * 作者：李鹏翔
     ****************************************************************************/
    public Map<Integer, String> getAuditResultMap();

    /****************************************************************************
     * 功能：保存审核之后的借用单
     * 作者：李鹏翔
     ****************************************************************************/
    public void saveAuditDeviceLending(LabRoomDeviceLendingResult lrdlr);

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getPassLendingTotals(LabRoomDeviceLendingResult lrdlr, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getPassKeepingTotals(LabRoomDeviceLendingResult lrdlr);

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllPassLending(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllPassKeeping(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：查找所有审核被拒绝的设备借用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getRejectedLendingTotals(LabRoomDeviceLendingResult lrdlr, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有审核被拒绝的设备领用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getRejectedKeepingTotals(LabRoomDeviceLendingResult lrdlr);

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllRejectedLending(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllRejectedKeeping(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：查找所有审核被拒绝的设备借用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getReturnedLendingTotals(LabRoomDeviceLendingResult lrdlr, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有审核被拒绝的设备领用审核单总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getReturnedKeepingTotals(LabRoomDeviceLendingResult lrdlr);

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllReturnedLending(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize, HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有审核通过的设备领用审核单
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceLendingResult> findAllReturnedKeeping(
            LabRoomDeviceLendingResult lrdlr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：归还借出的设备
     * 作者：李鹏翔
     ****************************************************************************/
    public void returnDeviceLending(Integer idKey, String remark, String backtime) throws ParseException;

    /****************************************************************************
     * 功能：查找所有维修列表总数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getAllRepairTotals(LabRoomDeviceRepair lrdr);

    /****************************************************************************
     * 功能：查找所有维修列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findAllRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：查找当前用户的设备报修或审核
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findAllRepairsByUser(String username);

    /****************************************************************************
     * 功能：保存设备报修
     * 作者：李鹏翔
     ****************************************************************************/
    public void saveNewDeviceRepair(LabRoomDeviceRepair lrdr);

    /****************************************************************************
     * 功能：查找所有报修列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findApplyRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：查找所有已修复列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findPassRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：查找所有未修复列表
     * 作者：李鹏翔
     ****************************************************************************/
    public List<LabRoomDeviceRepair> findRejectedRepairs(
            LabRoomDeviceRepair lrdr, Integer page, int pageSize);

    /****************************************************************************
     * 功能：查找所有借用记录条数
     * 作者：李鹏翔
     ****************************************************************************/
    public int getAllLendTotals(HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有借用申请和审核
     * 作者：李鹏翔
     ****************************************************************************/
    public List<SchoolDevice> findAllLendingList(int curr, int size, HttpServletRequest request);

    /****************************************************************************
     * 功能：下载设备文档（化工学院）
     * 作者：李小龙
     ****************************************************************************/
    public void downloadFile(CommonDocument doc, HttpServletRequest request,
                             HttpServletResponse response);

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    public int findStudentByCnameAndUsername(User user, String academyNumber, Integer deviceId);

    /****************************************************************************
     * 功能：根据user对象和学院编号查询所有学生
     * 作者：贺子龙
     * 时间：2015-11-05
     ****************************************************************************/
    public List<User> findStudentByCnameAndUsername(User user, String academyNumber, Integer deviceId, Integer page, int pageSize);

    /****************************************************************************
     * 功能：通过username和deviceId查询labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public LabRoomDevicePermitUsers findPermitUserByUsernameAndDeivce(String username, int deviceId);

    /****************************************************************************
     * 功能：通过deviceId查询labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDevicePermitUsers> findPermitUserByDeivceId(LabRoomDevicePermitUsers labRoomDevicePermitUsers, int deviceId, int page, int pageSize);

    /****************************************************************************
     * 功能：删除labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public void deletePermitUser(LabRoomDevicePermitUsers user);

    /****************************************************************************
     * 功能：通过主键查询labRoomDevicePermitUser
     * 作者：贺子龙
     ****************************************************************************/
    public LabRoomDevicePermitUsers findLabRoomDevicePermitUsersByPrimaryKey(int id);

    /****************************************************************************
     * 功能：更新某一设备下所有预约的审核结果
     * 作者：贺子龙
     ****************************************************************************/
    public void updateReservationResult(int deviceId) throws ParseException;

    /****************************************************************************
     * 功能：设备预约审核拒绝后放开被预约掉的时间段，使其设备可以重新预约该时间段
     * 作者：贺子龙
     ****************************************************************************/
    public void alterTimeAfterRefused(LabRoomDeviceReservation reservation, int flag) throws ParseException;

    /****************************************************************************
     * 功能：根据设备编号查询实验室设备
     * 作者：贺子龙
     ****************************************************************************/
    public LabRoomDevice findLabRoomDeviceByDeviceNumber(String deviceNumber);

    /****************************************************************************
     * 功能：找到当前用户的所有培训预约
     * 作者：贺子龙
     ****************************************************************************/
    public List<LabRoomDeviceTraining> findLabRoomDeviceTrainingByUser(Integer deviceId, Integer termId, int page, int pageSize);

    /****************************************************************************
     * description：设备教学使用报表
     * author：郑昕茹
     ****************************************************************************/
    public List getListLabRoomDeviceUsageInAppointment(HttpServletRequest request, int page, int pageSize);

    /****************************************************************************
     * description：设备教学使用报表-找到所有被排课用到的设备
     * author：郑昕茹
     ****************************************************************************/
    public List<TimetableLabRelatedDevice> getAllLabRoomDeviceUsageInAppointment();

    /****************************************************************************
     * @description：设备教学使用报表-找到所有上课教师
     * @author：郑昕茹
     ****************************************************************************/
    public List<TimetableTeacherRelated> getAllTimetableRelatedTeachers();

    /****************************************************************************
     * @description：设备教学使用报表-找到所有排课相关的实验项目
     * @author：郑昕茹
     ****************************************************************************/
    public List<TimetableItemRelated> getAllTimetableRelatedItems();

    /****************************************************************************
     * description：设备教学使用报表-找到所有的课程名称
     * author：郑昕茹
     ****************************************************************************/
    public List getAllCoursesInAppointment(HttpServletRequest request);

    /*************************************************************************************
     * @description：设备教学使用报表
     * @author：郑昕茹
     * @date：2016-10-25
     *************************************************************************************/
    public void exportLabRoomDeviceUsageInAppointment(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /*************************************************************************************
     * @description：设备使用报表
     * @author：郑昕茹
     * @date：2016-10-25
     *************************************************************************************/
    public void exportLabRoomDeviceUsage(HttpServletRequest request, HttpServletResponse response, LabRoomDeviceReservation reservation, String acno) throws Exception;

    /****************************************************************************
     * @description：课题组管理-查询到所有的课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public List<ResearchProject> findAllResearchProjects(ResearchProject researchProject, int currpage, int pageSize);

    /****************************************************************************
     * @description：课题组管理-保存新建的课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public ResearchProject saveResearchProject(ResearchProject researchProject);

    /****************************************************************************
     * @description：课题组管理-删除课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public boolean deleteResearchProject(int id);

    /****************************************************************************
     * @description：课题组管理-根据主键找到课题组
     * @author：郑昕茹
     * @date:2016-11-06
     ****************************************************************************/
    public ResearchProject findResearchProjectByPrimaryKey(int id);

    /****************************************************************************
     * 功能：查出某一中心下的所有设备管理员
     * 作者：贺子龙
     ****************************************************************************/
    public Map<String, String> findDeviceManageCnamerByCid(String acno);

    /****************************************************************************
     * @description：课题组管理-找到不在该课题组下的所有用户
     * @author：郑昕茹
     * @date:2016-12-21
     ****************************************************************************/
    public List<User> findUserNotInThisResearch(User user, Integer researchId, int pageSize, int currpage);

    /****************************************************************************
     *Description：根据id查找实训室借用单
     *
     *@param:idDey 实训室借用单编号
     *@author：邵志峰
     *@date:2017-06-28
     ****************************************************************************/
    public LabRoomLending findLabRoomLendingById(Integer idKey);

    /****************************************************************************
     * 功能：查找所有可借用设备的条数
     * 作者：周志辉
     * 时间：2017-08-11
     ****************************************************************************/
    public int getAllLendableDevice(HttpServletRequest request);

    /****************************************************************************
     * 功能：查找所有可借用设备的条数
     * 作者：周志辉
     * 时间：2017-08-11
     ****************************************************************************/
    public List<LabRoomDevice> findAllLendableDevice(LabRoomDevice labRoomDevice, String acno, int pageSize, int currpage, HttpServletRequest request);

    /****************************************************************************
     * 功能：根据主键查询物联硬件
     * 作者：周志辉
     ****************************************************************************/
    public LabRoomAgent findLabRoomAgentByPrimaryKey(Integer id);

    /****************************************************************************
     * 找到当前中心下所有被预约设备（预约审核成功的）--对应的labRoom、manager等map信息
     * @author 贺子龙
     * 2016-07-18
     ***************************************************************************/
    public Map<String, String> findMapWithDeviceReservation(String acno, Integer term, String mapType, String termMulti);

    /****************************************************************************
     * 功能：找到当前中心下所有被预约设备（预约审核成功的）--数量
     * 作者：贺子龙
     ****************************************************************************/
    public int countLabRoomDeviceWithReservation(LabRoomDevice labRoomDevice, String acno, Integer term, String termMulti);

    /****************************************************************************
     * 功能：找到当前中心下所有被预约设备（预约审核成功的）
     * 作者：贺子龙
     * @param terms 可以用","来分割成字符数组
     ****************************************************************************/
    public List<LabRoomDevice> findLabRoomDeviceWithReservation(LabRoomDevice labRoomDevice, String acno,
                                                                int currpage, int pageSize, Integer term, String termMulti);

    /****************************************************************************
     * 功能：查找所有审核通过的设备借用审核单
     * 作者：周志辉
     * 时间：2017-10-18
     ****************************************************************************/
    public List<LabRoomDeviceLending> findAllPassLending(Integer page, int pageSize, HttpServletRequest request);

    /****************************************************************************
     * @功能：用户所有设备借用申请数量
     * @作者：周志辉
     * @时间：2017-11-03
     ****************************************************************************/
    public Integer getAllDeviceLendingApplyList(HttpServletRequest request);

    /****************************************************************************
     * @功能：用户所有设备借用申请
     * @作者：周志辉
     * @时间：2017-11-03
     ****************************************************************************/
    public List<LabRoomDeviceLending> getAllDeviceLendingApplyList(Integer page, int pageSize, HttpServletRequest request);

    /****************************************************************************
     * @param request
     * @功能：机房设备适用记录
     * @作者：周志辉
     * @时间：2017-11-17
     ****************************************************************************/
    public List<ViewUseOfLc> findAllallViewUseOfLcList(ViewUseOfLc viewUseOfLc, int pageSize, int currpage, HttpServletRequest request);

    /****************************************************************************
     * @功能：查询实验室分室里的设备根据单价倒序
     * @作者：魏好
     * @时间：2018-1-12
     ****************************************************************************/
    public List<LabRoomDevice> getAllDeviceByLabRoomId(int labRoomId);

    /****************************************************************************
     * @功能：机房电脑使用所有记录
     * @作者：张德冰
     * @时间：2017.03.09
     ****************************************************************************/
    public List<ViewUseOfLc> findAllallViewUseOfLcListAll();

    /****************************************************************************
     * @param request
     * @功能：根据查询条件获取机房电脑使用记录
     * @作者：张德冰
     * @时间：2017.03.12
     ****************************************************************************/
    public List<ViewUseOfLc> findAllallViewUseOfLcListByLabRoomName(
            ViewUseOfLc viewUseOfLc, String labRoomName, HttpServletRequest request);

    /************************************************************
     * @功能：导出设备列表
     * @作者：张德冰
     * @时间：2018.03.13
     ************************************************************/
    public void exportLabRoomDevice(List<LabRoomDevice> labRoomDevices,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception;

    /************************************************************
     * @功能：导入设备列表
     * @作者：张德冰
     * @时间：2018.03.15
     ************************************************************/
    public String[] importLabRoomDevice(String File);

    /**
     * 查询所有已选设备信息count
     *
     * @param selectedDeviceStr
     * @return
     */
    public Integer countSelectedDevice(String selectedDeviceStr);

    /**
     * 查询所有已选设备信息
     * @param selectedDeviceStr
     * @param currpage
     * @param pageSize
     * @return
     */
    public List<LabRoomDevice> getSelectedDevice(String selectedDeviceStr, Integer currpage, Integer pageSize);

    /************************************************************
     * @功能：获取流水号
     * @作者：贺子龙
     * @时间：2018.04.17
     ************************************************************/
    public String getDeviceLendingBatch();
    
    /************************************************************
     * @功能：根据流水号获取所有的借用
     * @作者：贺子龙
     * @时间：2018年4月24日
     ************************************************************/
    public List<LabRoomDeviceLending> getDeviceLendingByBatch(String lendBatch);
    
    /************************************************************
     * @功能：根据流水号获取所有的借用(已经审核拒绝的除外)
     * @作者：贺子龙
     * @时间：2018年4月24日
     ************************************************************/
    public List<LabRoomDeviceLending> getDeviceLendingByBatchWithoutReject(String lendBatch);
    
    /************************************************************
     * @功能：根据结果和借用的id处理后续消息、状态等机制(系主任)
     * @作者：贺子龙
     * @时间：2018年4月27日
     ************************************************************/
    public void createAuditMessageByLendId(Integer id, String tag) 
    		throws NoSuchAlgorithmException, InterruptedException;
    
    /************************************************************
     * @功能：根据结果和借用的id处理后续消息、状态等机制(实训室管理员)
     * @作者：贺子龙
     * @时间：2018年4月27日
     ************************************************************/
    public void createAuditMessageByLendIdManager(Integer id, String tag) 
    		throws NoSuchAlgorithmException, InterruptedException;
    
    /************************************************************
     * @功能：根据结果和借用的id处理后续消息、状态等机制(实训部主任)
     * @作者：贺子龙
     * @时间：2018年4月27日
     ************************************************************/
    public void createAuditMessageByLendIdHead(Integer id, String tag) 
    		throws NoSuchAlgorithmException, InterruptedException;
    
    /************************************************************
     * @功能：根据设备名和借用预约条件查询设备列表
     * @作者：汪哲玮
     * @时间：2018.04.17
     ************************************************************/
	public List<LabRoomDevice> getAllDeviceByLabRoomIdAndLabDevice(Integer id,
			String deviceName, int deviceLend, int deviceAppoint);
    /************************************************************
     * @功能：实验室与设备联动查询
     * @作者：廖文辉
     * @时间：2018.9.5
     ************************************************************/
    public String findLabRoomDevicesByLabRoom(String labRoom,HttpServletRequest request,String deviceNumber);
    /************************************************************
     * @功能：查找labRoomDevice
     * @作者：廖文辉
     * @时间：2018.9.5
     ************************************************************/
    public List<LabRoomDevice> getLabRoomDevice(String acno);
    /****************************************************************************
     * @Description：实验室根据培训查询培训名单
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    public List<LabRoomTrainingPeople> findLabRoomTrainingPeoplesByTrainingId(
            int id);
    /****************************************************************************
     * @Description：根据培训id查询培训通过的人
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    public List<LabRoomTrainingPeople> findPassLabRoomTrainingPeopleByTrainId(
            Integer id);
    /****************************************************************************
     * @Description：：删除labRoomDevicePermitUser
     * @author：廖文辉
     * @date:2018-09-29
     ****************************************************************************/
    public void deleteLabRoomPermitUser(LabRoomPermitUser user);


    /**
     * Description 设备借用消息发送提取
     * @param id 设备借用单id
     * @param tag 通过与否
     * @param stage 审核级别
     * @throws InterruptedException 消息发送异常
     * @throws NoSuchAlgorithmException 消息发送异常
     * @author 黄保钱 2019-1-2
     */
    void sendMessageForDeviceLending(Integer id, String tag, Integer stage) throws InterruptedException, NoSuchAlgorithmException;

    /**
     * @Description 设备借用撤回备份
     * @author 张德冰
     * @data 2018-01-16
     */
    public void revokeDeviceLend(LabRoomDeviceLending deviceLending);

    /**
     * @Description 查找设备借用作废
     * @author 张德冰
     * @data 2018-01-18
     */
    public List<RefuseItemBackup> findRefuseItemBackupList(RefuseItemBackup refuseItemBackup,String type, Integer currpage, Integer pageSize);

    /**
     * @Description 查找设备借用作废总记录
     * @author 张德冰
     * @data 2018-01-18
     */
    public Integer findRefuseItemBackupTotalRecords(RefuseItemBackup refuseItemBackup,String type);

    /**
     * description 判断设备是否已经存在实验室中
     * @param device_number
     * @param lab_id
     * @return
     * @author 陈乐为 2019-3-26
     */
    public boolean ifLabRoomDeviceExist(String device_number, int lab_id);

    /**
     * Description 获取实验室的设备总价
     * @param lab_id
     * @return
     * @author 陈乐为 2019年4月29日
     */
    public BigDecimal getAgentPriceByLab(Integer lab_id);

}
