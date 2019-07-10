package net.zjcclims.service.lab;

import api.net.gvsunlims.constant.ConstantInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.constant.CommonConstantInterface;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.service.device.LabRoomDeviceService;
import net.zjcclims.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description 实训室借用模块
 *
 * @author 陈乐为 2017-10-10
 */

@Service("LabRoomLendingService")
public class LabRoomLendingServiceImpl implements LabRoomLendingService {
    @Autowired
    private LabRoomDAO labRoomDAO;
    @Autowired
    private LabRoomLendingDAO labRoomLendingDAO;

    @Autowired
    private ShareService shareService;
    @Autowired
    private LabLendAuditDAO labLendAuditDAO;
    @Autowired
    private LabReservationDAO labReservationDAO;
    @Autowired
    private LabReservationAuditDAO labReservationAuditDAO;
    @Autowired
    private SchoolClassesDAO schoolClassesDAO;
    @Autowired
    private CDictionaryDAO cDictionaryDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private SoftwareReserveDAO softwareReserveDAO;
    @Autowired
    private LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private LabRoomService labRoomService;
    @Autowired
    private SystemTimeDAO systemTimeDAO;
    @Autowired
    private SchoolWeekDAO schoolWeekDAO;
    @Autowired
    private SchoolWeekdayDAO schoolWeekdayDAO;
    @Autowired
    private LabReservationTimeTableDAO labReservationTimeTableDAO;
    @Autowired
    private TimetableAppointmentDAO timetableAppointmentDAO;
    @Autowired
    private TimetableAppointmentSameNumberDAO timetableAppointmentSameNumberDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;
    @Autowired
    private LabRoomAdminService labRoomAdminService;
    @Autowired
    private AuthorityDAO authorityDAO;
    @Autowired
    private TimetableTeacherRelatedDAO timetableTeacherRelatedDAO;
    @Autowired
    private AuditRefuseBackupDAO auditRefuseBackupDAO;
    @Autowired
    private RefuseItemBackupDAO refuseItemBackupDAO;

    /**
     * Description 判断借用申请时间是否可用
     *
     * @param labRoomId   实训室主键
     * @param lendingTime 借用日期
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return
     * @author 陈乐为 2017-10-10
     */
    @Override
    public int findLendingEnableOrNot(Integer labRoomId, Calendar lendingTime, Calendar startTime, Calendar endTime, String acno) {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        //标志位-是否可借用-1可借用，2被预约，3被借用
        int lendingStatus = 1;
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);

        //实训室对应预约列表
        Set<LabRoomStationReservation> labRoomStationReservations = labRoom.getLabRoomStationReservations();
        //和本身的预约逻辑做判断
        if (labRoomStationReservations != null) {
            for (LabRoomStationReservation labRoomStationReservation : labRoomStationReservations) {//遍历该实验室已有的预约信息
                if (labRoomStationReservation.getResult()!=null && labRoomStationReservation.getResult() != 4) {//筛去审核拒绝的
                    if (labRoomStationReservation.getReservation().equals(lendingTime)) {//预约日期相同
                        if (labRoomStationReservation.getStartTime().after(endTime) ||
                                labRoomStationReservation.getEndTime().before(startTime) ||
                                labRoomStationReservation.getStartTime().equals(endTime) ||
                                labRoomStationReservation.getEndTime().equals(startTime)) {//未和所选时间冲突
                            //do nothing
                        } else {//和所选时间冲突
                            lendingStatus = 2;
                            return lendingStatus;
                        }
                    }
                }
            }
        }
        //实训室对应借用列表
        Set<LabReservation> labReservations = labRoom.getLabReservations();
        //demo
        Map<String, String> params = new HashMap<>();
        Iterator<LabReservation> it = labReservations.iterator();
        while (it.hasNext()) {
            LabReservation l = it.next();
            String businessType = pConfigDTO.PROJECT_NAME + "LabRoomReservation" + l.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
            //流水单号转换
            String businessAppUid = shareService.getSerialNumber(l.getId().toString(), businessType);
            params.put("businessAppUid", businessAppUid);
            params.put("businessType", businessType);
            String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params);
            JSONObject jsonObject = JSON.parseObject(s);
            String status = jsonObject.getString("status");
            if ("success".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (l.getId().toString().equals(jsonArray.getJSONObject(0).getString("businessAppId"))
                        && "0".equals(jsonArray.getJSONObject(0).getString("level"))) {
                    it.remove();
                }
            }
        }
        Set<SchoolWeek> schoolWeeks = schoolWeekDAO.findSchoolWeekByDate(lendingTime);
        SchoolWeek lendingDate = schoolWeeks.size() == 0 ? null : schoolWeeks.iterator().next();
        if (labReservations != null && labReservations.size() > 0 && lendingDate != null) {
            for (LabReservation labReservation : labReservations) {
                for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()) {
                    if (labReservation.getLendingTime()!=null && labReservation.getLendingTime().equals(lendingTime)) {//和借用日期在同一天的
                        if (lrtt.getStartTime().after(endTime) ||
                                lrtt.getEndTime().before(startTime) ||
                                lrtt.getStartTime().equals(endTime) ||
                                lrtt.getEndTime().equals(startTime)) {//未和所选时间冲突
                            //do nothing
                        } else {
                            lendingStatus = 3;
                            return lendingStatus;
                        }
                    }
                }
            }
        }
        Set<TimetableLabRelated> timetableLabRelateds = labRoom.getTimetableLabRelateds();
        if (schoolWeeks.size() != 0) {
            SchoolWeek schoolWeek = schoolWeeks.iterator().next();
            for (TimetableLabRelated t : timetableLabRelateds) {
                Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers =
                        t.getTimetableAppointment().getTimetableAppointmentSameNumbers();
                for (TimetableAppointmentSameNumber tas : timetableAppointmentSameNumbers) {
                    if (tas.getStartWeek() <= schoolWeek.getWeek() && tas.getEndWeek() >= schoolWeek.getWeek() && schoolWeek.getWeekday().equals(t.getTimetableAppointment().getWeekday()) && schoolWeek.getSchoolTerm().getId().equals(t.getTimetableAppointment().getSchoolTerm().getId())) {
                        SystemTime start = systemTimeDAO.findSystemTimeBySection(tas.getStartClass()).iterator().next();
                        SystemTime end = systemTimeDAO.findSystemTimeBySection(tas.getEndClass()).iterator().next();
                        if (start.getStartDate().after(endTime) ||
                                end.getEndDate().before(startTime) ||
                                start.getStartDate().equals(endTime) ||
                                end.getEndDate().equals(startTime)) {//未和所选时间冲突
                            //do nothing
                        } else {
                            lendingStatus = 3;
                            return lendingStatus;
                        }
                    }
                }
            }
        }

        return lendingStatus;
    }

    /**
     * Description 实训室借用-借用申请-保存
     *
     * @param labRoomId   实训室主键
     * @param lendingTime 借用日期
     * @param reservationTimes   借用时间
     * @param reason      借用原因
     * @author 陈乐为 2017-10-10
     */
    @Override
    @ResponseBody
    public int saveLabRoomLending(Integer labRoomId, Calendar lendingTime, String[] reservationTimes, String reason, HttpServletRequest request, Integer auditNumber) throws ParseException {
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);

        //保存实训室借用
        LabReservation labReservation = new LabReservation();
        if (request.getParameter("labRid") != null) {
            //当前软件申请记录
            labReservation = labReservationDAO.findLabReservationById(Integer.parseInt(request.getParameter("labRid")));
        }
        Set<SchoolWeek> schoolWeeks = schoolWeekDAO.findSchoolWeekByDate(lendingTime);
        if (schoolWeeks.size() != 0) {
            labReservation.setLabRoom(labRoom);//实训室
            labReservation.setLendingUserPhone(request.getParameter("lendingUserPhone"));//电话号码
            labReservation.setLendingReason(reason);//借用原因
            labReservation.setNumber(request.getParameter("number"));//人数
            labReservation.setLendingTime(lendingTime);//日期

            labReservation.setLendingUnit(request.getParameter("lendingUnit"));//借用单位
            labReservation.setSchoolClasses(schoolClassesDAO.findSchoolClassesByClassNumber(request.getParameter("class")));//班级
            if (request.getParameter("lendingType") != null) {
                labReservation.setCDictionaryByLendingType(shareService.getCDictionaryforLabResrevationuse(Integer.parseInt(request.getParameter("lendingType"))));
            }
            if (request.getParameter("lendingUserType") != null) {
                labReservation.setCDictionaryByLendingUserType(shareService.getCDictionaryforLabResrevationuse(Integer.parseInt(request.getParameter("lendingUserType"))));
            }
            labReservation.setUser(shareService.getUserDetail());//申请人
            labReservation = labReservationDAO.store(labReservation);
//        labReservation.setStartTime(startTime);//开始时间
//        labReservation.setEndTime(endTime);//结束时间

            // 时间保存在LabReservationTimeTable
            SchoolWeek s = schoolWeeks.iterator().next();
            CDictionary cd = shareService.getCDictionaryByCategory("c_lab_reservation_week", s.getWeek().toString());
            SchoolWeekday swd = schoolWeekdayDAO.findSchoolWeekdayById(s.getWeekday());
            Set<LabReservationTimeTable> labReservationTimeTables = new HashSet<>();
            for (String reservationTime : reservationTimes) {
                String[] rs = reservationTime.split("-");
                String startTimeS = rs[0];
                String endTimeS = rs[1];
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                Date startDate = sdf1.parse(startTimeS);
                Date endDate = sdf1.parse(endTimeS);
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                start.setTime(startDate);
                end.setTime(endDate);
                labReservation.setStartTime(start);
                labReservation.setEndTime(end);
                LabReservationTimeTable lrtt = new LabReservationTimeTable();
                lrtt.setSchoolTerm(s.getSchoolTerm());
                lrtt.setCDictionary(cd);
                lrtt.setSchoolWeekday(swd);
                lrtt.setStartTime(start);
                lrtt.setEndTime(end);
                lrtt.setLabReservation(labReservation);
                lrtt = labReservationTimeTableDAO.store(lrtt);
                labReservationTimeTableDAO.flush();
                labReservationTimeTables.add(lrtt);
            }
            labReservation.setLabReservationTimeTables(labReservationTimeTables);
            labReservationDAO.flush();

            // 更新申请人的用户信息--电话
            User user = labReservation.getUser();
            user.setTelephone(labReservation.getLendingUserPhone());
            shareService.saveUser(user);
        } else {
            return 0;
        }
//		if(auditNumber == 6){
//		    labReservation.setAuditResults(1);//已通过审核
//        }else {
//            labReservation.setAuditResults(3);//未审核状态
//        }
//        labReservation.setAuditStage(auditNumber);//检查需要第几级审核


        return labReservation.getId();
    }

    /**
     * Description 实验室预约禁用时间段判冲
     * @param startWeek
     * @param endWeek
     * @param Weekday
     * @param startClass
     * @param endClass
     * @param week
     * @param weekday
     * @param section
     * @return
     * @Author Hezhaoyi 2019-4-26
     */
    public boolean judgeLabReservationIsConflict(Integer startWeek,Integer endWeek,Integer Weekday,Integer startClass,Integer endClass,
                                                 Integer week,Integer weekday,Integer section){

        if((week > startWeek || week == startWeek) && (week < endWeek || week == endWeek)){
            if((weekday == Weekday)){
                if((section > startClass || section.equals(startClass)) && (section < endClass || section.equals(endClass))){
                        return false;
                }
            }
        }
        return true;
    }
    /**
     * Description  判断实验室预约时间在开放时间段内
     * @param startHour
     * @param endHour
     * @param section
     * @return
     * @Author Hezhaoyi 2019-4-29
     */
    public boolean isOpenLabReservation(BigDecimal startHour,BigDecimal endHour,Integer section){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        DecimalFormat df=new DecimalFormat("0.00");
        //解析节次获取起止时间
        Date starttime = systemTimeDAO.findSingleSystemTimeBySection(section).getStartDate().getTime();
        Date endttime = systemTimeDAO.findSingleSystemTimeBySection(section).getEndDate().getTime();

        String startTime = sdf.format(starttime);
        String endTime = sdf.format(endttime);
        String[] start_array = startTime.replace(":", ",").split(",");
        int startH = Integer.parseInt(start_array[0]);
        BigDecimal startHourChoice = new BigDecimal(startH);
        int part1 = Integer.parseInt(start_array[1]);
        BigDecimal startM = new BigDecimal(df.format((float)part1/60));
        startHourChoice = startHourChoice.add(startM);

        String[] end_array = endTime.replace(":", ",").split(",");
        int endH = Integer.parseInt(end_array[0]);
        BigDecimal endHourChoice = new BigDecimal(endH);
        int part2 = Integer.parseInt(end_array[1]);
        BigDecimal endM = new BigDecimal(df.format((float)part2/60));
        endHourChoice = endHourChoice.add(endM);

        if(startHourChoice.compareTo(startHour) > -1 && endHourChoice.compareTo(endHour) < 1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Description 查询对应实训室的借用记录并分页
     *
     * @param labRoomId
     * @param currpage
     * @param pageSize
     * @return
     * @author 陈乐为 2017-10-10
     */
    @Override
    public List<LabReservation> findLendingsByLabId(Integer labRoomId, int currpage, int pageSize) {
        LabRoom labRoom = labRoomDAO.findLabRoomById(labRoomId);
        String sql = "select c from LabReservation c where 1=1";
        sql += " and c.labRoom.id = " + labRoomId;

        return labReservationDAO.executeQuery(sql, (currpage - 1) * pageSize, pageSize);
    }

    /*********************************************************************************
     *@description:实验室借用申请审核记录
     *@author: 张愉
     *@date：2017-10-10
     ********************************************************************************/
    @Override
    public List<LabReservationAudit> findAllLabReservationAuditById(int Id) {
        String sql = "select l from LabReservationAudit l where 1=1";
        sql += " and l.labReservation.id=" + Id;
        sql += " and (l.status=0 or l.status=1)";
        return labReservationAuditDAO.executeQuery(sql, 0, -1);
    }

    /*************************************************************************************
     * Description 保存不同身份的审核结果
     * @author 孙虎
     * @throws NoSuchAlgorithmException
     * @date 2017-11-5
     *************************************************************************************/
    public LabReservation saveAuditResultForlab(LabReservation labReservation, LabReservationAudit labReservationAudit) throws NoSuchAlgorithmException {
        LabRoom labRoom = labReservation.getLabRoom();
        User user = shareService.getUserDetail();
        //消息
        Message message = new Message();
        message.setSendUser(user.getCname());
        message.setMessageState(0);//设置未读
        message.setCreateTime(Calendar.getInstance());
        message.setUsername(labReservation.getUser().getUsername());
        message.setSendCparty(user.getSchoolAcademy().getAcademyName());

        // 实际审核状态
        Integer auditNumber = labRoomService.getAuditNumber(labRoom, labReservation.getAuditStage());
        //审核结果
        Integer auditResult = Integer.valueOf(labReservationAudit.getResult());

        if (auditNumber == 1) {//系主任审核结果保存
            if (auditResult == 0) {//不通过
                labReservation.setAuditResults(4);
                labReservation.setAuditStage(4);
                message.setTitle("实验室借用申请拒绝");
                message.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>");//消息内容
                message.setTage(3);
            } else {//通过
                labReservation.setAuditResults(2);
                labReservation.setAuditStage(2);
                message.setTitle("实验室借用申请系主任通过");
                message.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>");//消息内容
                message.setTage(1);
                //给下一级发消息
                Message nextMessage = new Message();
                nextMessage.setSendUser(labReservation.getUser().getCname());
                nextMessage.setMessageState(0);//设置未读
                nextMessage.setCreateTime(Calendar.getInstance());
                nextMessage.setTitle("实验室预约增加");
                nextMessage.setSendCparty(labReservation.getUser().getSchoolAcademy().getAcademyName());
                nextMessage.setAuthId(2);
                nextMessage.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>审核</a>");//消息内容
                //实验室管理员
                List<User> labRoomAdmins = labRoomDeviceService.findAdminByLrid(labRoom.getId());
                for (User user2 : labRoomAdmins) {
                    nextMessage.setUsername(user2.getUsername());
                    messageDAO.store(nextMessage);
                    messageDAO.flush();
                }
            }
            labReservationAudit.setStatus(1);
            if (shareService.getUserDetail().getTelephone() != null) {
                try {
                    String result = shareService.sendMessage(shareService.getUserDetail().getTelephone(), message.getTitle());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            messageDAO.store(message);
            messageDAO.flush();
        } else if (auditNumber == 2) {//实训室管理员审核结果保存
            if (auditResult == 0) {//不通过
                labReservation.setAuditResults(4);
                labReservation.setAuditStage(4);
                message.setTitle("实验室借用申请拒绝");
                message.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>");//消息内容
                message.setTage(3);
            } else {//通过
                labReservation.setAuditResults(2);
                labReservation.setAuditStage(3);
                message.setTitle("实验室借用申请实验室管理员通过");
                message.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>");//消息内容
                message.setTage(1);
                //给下一级发消息
                Message nextMessage = new Message();
                nextMessage.setSendUser(labReservation.getUser().getCname());
                nextMessage.setMessageState(0);//设置未读
                nextMessage.setCreateTime(Calendar.getInstance());
                nextMessage.setTitle("实验室预约增加");
                nextMessage.setSendCparty(labReservation.getUser().getSchoolAcademy().getAcademyName());
                nextMessage.setAuthId(2);
                nextMessage.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>审核</a>");//消息内容
                //实训部教学秘书
                List<User> labRoomCenterDirectors = shareService.findUsersByAuthorityName("PREEXTEACHING");
                for (User user2 : labRoomCenterDirectors) {
                    nextMessage.setUsername(user2.getUsername());
                    messageDAO.store(nextMessage);
                    messageDAO.flush();
                }

            }
            labReservationAudit.setStatus(2);
            if (shareService.getUserDetail().getTelephone() != null) {
                try {
                    String result = shareService.sendMessage(shareService.getUserDetail().getTelephone(), message.getTitle());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            messageDAO.store(message);
            messageDAO.flush();
        } else if (auditNumber == 3) {//实训室部教学秘书审核结果保存
            if (auditResult == 0) {//不通过
                labReservation.setAuditResults(4);
                labReservation.setAuditStage(4);
                message.setTitle("实验室借用申请拒绝");
                message.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>");//消息内容
                message.setTage(3);
            } else {//通过
                labReservation.setAuditResults(1);
                labReservation.setAuditStage(4);
                message.setTitle("实验室借用申请实验室部教学秘书审核通过");
                message.setContent("<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>");//消息内容
                message.setTage(4);
            }
            labReservationAudit.setStatus(3);
            if (shareService.getUserDetail().getTelephone() != null) {
                try {
                    String result = shareService.sendMessage(shareService.getUserDetail().getTelephone(), message.getTitle());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            messageDAO.store(message);
            messageDAO.flush();
        }

        labReservation = labReservationDAO.store(labReservation);
        softwareReserveDAO.flush();
        labReservationAudit.setLabReservation(labReservation);
        labReservationAudit.setUser(user);
        labReservationAudit.setCreateDate(Calendar.getInstance());
        labReservationAuditDAO.store(labReservationAudit);
        labReservationAuditDAO.flush();
        return labReservation;
    }


    /**
     * Description 实验室预约审核结果保存
     * @param labReservation
     * @param sAuditResult1
     * @param remark
     * @param acno
     * @return
     * @throws NoSuchAlgorithmException
     * @author 谁？
     */
    @Transactional
    public LabReservation saveAuditResult(LabReservation labReservation, String sAuditResult1, String remark, String acno) throws NoSuchAlgorithmException {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        LabRoom labRoom = labReservation.getLabRoom();
        Integer auditResult = Integer.parseInt(sAuditResult1);
        User user = shareService.getUserDetail();

        String businessType = pConfigDTO.PROJECT_NAME + "LabRoomReservation" + labReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        // 业务转流水号，保存并返回流水号
        String businessAppUid = "";
        if(shareService.getSerialNumber(labReservation.getId().toString(), businessType)=="fail"){
            //没有流水单号就是用预约id用作业务id
            businessAppUid = labReservation.getId().toString();
        }else {
            businessAppUid = shareService.getSerialNumber(labReservation.getId().toString(), businessType);
            //有流水单号用流水单号做业务id
        }
//        String businessAppUid = shareService.getSerialNumber(labReservation.getId().toString(), businessType);

        Map<String, String> params2 = new HashMap<>();
        params2.put("businessType", businessType);
        params2.put("businessAppUid", businessAppUid);
        String s2 = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params2);
        JSONObject jsonObject2 = JSONObject.parseObject(s2);
        if (!"success".equals(jsonObject2.getString("status"))) {
            return null;
        }
        Integer state = 0;
        String nextAuthName = "";
        String currAuthName = "";
        if (jsonObject2.getJSONArray("data") != null && jsonObject2.getJSONArray("data").size() != 0) {
            state = jsonObject2.getJSONArray("data").getJSONObject(0).getIntValue("level");
            currAuthName = jsonObject2.getJSONArray("data").getJSONObject(0).getString("result");
        }

        // 实际审核状态
        Integer auditNumber = labRoomService.getAuditNumber(labRoom, state);

        Map<String, String> params = new HashMap<>();
        params.put("businessType", businessType);
        params.put("businessAppUid", businessAppUid);
        params.put("businessUid", labRoom.getId().toString());
        params.put("result", auditResult == 1 ? "pass" : "fail");
        params.put("info", remark);
        params.put("username", user.getUsername());
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/saveBusinessLevelAudit", params);

        Map<String, String> params4 = new HashMap<>();
        params4.put("businessType", businessType);
        params4.put("businessAppUid", businessAppUid);
        String s4 = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params4);
        JSONObject jsonObject5 = JSON.parseObject(s4);
        JSONArray jsonArrayCurStage = jsonObject5.getJSONArray("data");
        if(jsonArrayCurStage.size() != 0){
            JSONObject jsonObjectCurStage0 = jsonArrayCurStage.getJSONObject(0);
            Integer level = jsonObjectCurStage0.getIntValue("level");
            if(level == -1){// 审核通过
                TimetableAppointment timetableAppointment = new TimetableAppointment();
                timetableAppointment.setTimetableStyle(ConstantInterface.TIMETABLE_STYLE_LAB_RESERVATION);
                timetableAppointment.setCreatedDate(Calendar.getInstance());
                timetableAppointment.setUpdatedDate(Calendar.getInstance());
//            timetableAppointment.setCreatedBy(labReservation.getUser().getCname());
                timetableAppointment.setSchoolClasses(labReservation.getSchoolClasses());
                timetableAppointment.setCreatedBy(labReservation.getUser().getUsername());
                timetableAppointment.setStatus(ConstantInterface.TIMETABLE_STATUS_PUBLIC);
                timetableAppointment.setEnabled(true);
                timetableAppointment.setWeekday(labReservation.getLabReservationTimeTables().iterator().next().getSchoolWeekday().getId());
                timetableAppointment.setGroups(-1);
                // 预约人数类型变动，人数范围拆分
                String number = labReservation.getNumber();
                int stu_num = 0;
                if(number != null) {
                    String[] stunum = number.split("-");
                    if(stunum.length > 1) {
                        stu_num = Integer.valueOf(stunum[1]);
                    }else {
                        stu_num = Integer.valueOf(number);
                    }
                }
                timetableAppointment.setGroupCount(stu_num);
                timetableAppointment.setLabhours(-1);
                timetableAppointment.setConsumablesCosts(new BigDecimal(-1));
                timetableAppointment.setDeviceOrLab(2);
                timetableAppointment.setSchoolTerm(labReservation.getLabReservationTimeTables().iterator().next().getSchoolTerm());
                timetableAppointment = timetableAppointmentDAO.store(timetableAppointment);
                TimetableLabRelated tlr = new TimetableLabRelated();
                tlr.setLabRoom(labRoom);
                tlr.setTimetableAppointment(timetableAppointment);
                tlr = timetableLabRelatedDAO.store(tlr);
                Set<TimetableLabRelated> timetableLabRelateds = new HashSet<>();
                timetableLabRelateds.add(tlr);
                SimpleDateFormat compareSDF = new SimpleDateFormat("yyyyMMddHHmmss");
                Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers = new HashSet<>();
                Integer week = Integer.parseInt(labReservation.getLabReservationTimeTables().iterator().next().getCDictionary().getCNumber());
                List<List<Integer>> results = getSectionsList(labReservation);
                for(List<Integer> integerList: results){
                    TimetableAppointmentSameNumber tasn = new TimetableAppointmentSameNumber();
                    tasn.setStartWeek(week);
                    tasn.setEndWeek(week);
                    tasn.setStartClass(integerList.get(0));
                    tasn.setEndClass(integerList.get(integerList.size() - 1));
                    tasn.setTimetableAppointment(timetableAppointment);
                    timetableAppointmentSameNumbers.add(tasn);
                    timetableAppointmentSameNumberDAO.store(tasn);
                    timetableAppointmentSameNumberDAO.flush();
                }
                Set<TimetableTeacherRelated> timetableTeacherRelateds = new HashSet<>();
                TimetableTeacherRelated timetableTeacherRelated = new TimetableTeacherRelated();
                timetableTeacherRelated.setTimetableAppointment(timetableAppointment);
                timetableTeacherRelated.setUser(labReservation.getUser());
                timetableTeacherRelated = timetableTeacherRelatedDAO.store(timetableTeacherRelated);
                timetableTeacherRelateds.add(timetableTeacherRelated);
                timetableAppointment.setTimetableTeacherRelateds(timetableTeacherRelateds);
                timetableAppointment.setTimetableAppointmentSameNumbers(timetableAppointmentSameNumbers);
                timetableAppointment.setTimetableLabRelateds(timetableLabRelateds);
                timetableAppointmentDAO.flush();
                labReservation.setTimetableAppointment(timetableAppointment);
                labReservationDAO.store(labReservation);
            }
            nextAuthName = jsonObjectCurStage0.getString("result");
        }
        boolean[] auditArray = new boolean[5];

        //demo
        String[] RSWITCH = {"on", "off"};
        Map<String, String> params1 = new HashMap<>();
        params1.put("businessUid", labRoom.getId().toString());
        params1.put("businessType", businessType);
        String s1 = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessAuditConfigs", params1);
        com.alibaba.fastjson.JSONObject jsonObject1 = JSON.parseObject(s1);
        String status1 = jsonObject1.getString("status");
        Map auditConfigs = JSON.parseObject(jsonObject1.getString("data"), Map.class);
        if (auditConfigs != null && auditConfigs.size() != 0) {
            for (int i = 0; i < auditConfigs.size(); i++) {
                String[] text = ((String) auditConfigs.get(i + 1)).split(":");
                auditArray[i] = text[1].equals(RSWITCH[0]);
            }
        }

        //消息
        Message message = new Message();
        message.setSendUser(user.getCname());
        message.setSendCparty(user.getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle("实验室预约审核");
        String content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>审核</a>";
        message.setContent(content);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(Calendar.getInstance());
        message.setTage(2);
        // 当前审核权限
        String cName = authorityDAO.findAuthorityByAuthorityName(currAuthName).iterator().next().getCname();
        //第一级审核人
        switch (nextAuthName) {
            case "TEACHER":
                User teacher = labReservation.getTeacher();
                shareService.sendMsg(teacher, message);
                break;
            case "CFO":
                List<User> deans = shareService.findDeansByAcademyNumber(user.getSchoolAcademy());
                for (User user2 : deans) {
                    shareService.sendMsg(user2, message);
                }
                break;
            case "LABMANAGER":
                List<LabRoomAdmin> labRoomAdmins = labRoomAdminService.findAllLabRoomAdminsByLabRoomId(labReservation.getLabRoom().getId());
                for (LabRoomAdmin labRoomAdmin : labRoomAdmins) {
                    User user2 = labRoomAdmin.getUser();
                    shareService.sendMsg(user2, message);
                }
                break;
            case "EXCENTERDIRECTOR":
                shareService.sendMsg(labRoom.getLabCenter().getUserByCenterManager(), message);
                break;
            case "PREEXTEACHING":
                List<User> labRoomMasters = shareService.findUsersByAuthorityName("PREEXTEACHING");
                for (User user2 : labRoomMasters) {
                    shareService.sendMsg(user2, message);
                }
                break;
            case "pass":
                labReservation.setAuditStage(6);
                message.setTitle("实验室预约审核通过");
                content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
                message.setContent(content);
                message.setTage(1);
                shareService.sendMsg(labReservation.getUser(), message);
                break;
            case "fail":
                labReservation.setAuditStage(0);
                message.setTitle("实验室预约审核被"+cName+"拒绝");
                content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
                message.setContent(content);
                message.setTage(1);
                shareService.sendMsg(labReservation.getUser(), message);
                break;
            default:
                List<User> auditUsers = shareService.findUsersByAuthorityName(nextAuthName);
                for (User user2: auditUsers){
                    shareService.sendMsg(user2, message);
                }
                message.setTitle("实验室预约"+cName+user.getCname()+(auditResult == 1 ? "审核通过" : "审核拒绝"));
                content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
                message.setContent(content);
                message.setTage(1);
                message.setUsername(labReservation.getUser().getUsername());
                messageDAO.store(message);
                messageDAO.flush();
        }
        message.setTitle("实验室预约"+cName+user.getCname()+(auditResult == 1 ? "审核通过" : "审核拒绝"));
        content = "<a onclick='changeMessage(this)' href='../labRoomLending/checkButton?id=" + labReservation.getId() + "&tage=0&state=" + auditNumber + "&page=1'>查看</a>";
        message.setContent(content);
        message.setTage(1);
        shareService.sendMsg(labReservation.getUser(), message);

        labReservation = labReservationDAO.store(labReservation);
        labReservationDAO.flush();
        return labReservation;
    }

    public SystemTime getSystemTimeByStartAndEnd(Calendar start, Calendar end){
        SimpleDateFormat compareSDF = new SimpleDateFormat("yyyyMMddHHmmss");
        String sql = "select st from SystemTime st where startDate = "
                + compareSDF.format(start.getTime())
                + " and endDate = "
                + compareSDF.format(end.getTime());
        SystemTime systemTime = (SystemTime) systemTimeDAO.executeQuerySingleResult(sql);
        return systemTime;
    }
    /**
     * Description 判断借用申请时间是否可用
     *
     * @param labReservationId 预约主键
     * @param labRoomId 实训室主键
     * @param lendingTime 借用日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author 廖文辉 2018-12-20
     */
    public int findLendingEnableOrNot(Integer labReservationId,Integer labRoomId, Calendar lendingTime, Calendar startTime, Calendar endTime, String acno){
        //标志位-是否可借用-1可借用，2被预约，3被借用
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        int lendingStatus = 1;
        LabRoom labRoom = labRoomDAO.findLabRoomByPrimaryKey(labRoomId);

        //实训室对应预约列表
        Set<LabRoomStationReservation> labRoomStationReservations = labRoom.getLabRoomStationReservations();
        //和本身的预约逻辑做判断
        if (labRoomStationReservations != null) {
            for (LabRoomStationReservation labRoomStationReservation : labRoomStationReservations) {//遍历该实验室已有的预约信息
                if (labRoomStationReservation.getResult() != 4) {//筛去审核拒绝的
                    if (labRoomStationReservation.getReservation().equals(lendingTime)) {//预约日期相同
                        if (labRoomStationReservation.getStartTime().after(endTime) ||
                                labRoomStationReservation.getEndTime().before(startTime) ||
                                labRoomStationReservation.getStartTime().equals(endTime) ||
                                labRoomStationReservation.getEndTime().equals(startTime)) {//未和所选时间冲突
                            //do nothing
                        } else {//和所选时间冲突
                            lendingStatus = 2;
                            return lendingStatus;
                        }
                    }
                }
            }
        }
        //实训室对应借用列表
        Set<LabReservation> labReservations = labRoom.getLabReservations();
        //demo
        Map<String, String> params = new HashMap<>();
        Iterator<LabReservation> it = labReservations.iterator();
        while (it.hasNext()) {
            LabReservation l = it.next();
            String businessType = "LabRoomReservation" + l.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
            params.put("businessAppUid", l.getId().toString());
            params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
            String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params);
            JSONObject jsonObject = JSON.parseObject(s);
            String status = jsonObject.getString("status");
            if ("success".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (it.next().getId().toString().equals(jsonArray.getJSONObject(0).getString("businessAppId"))
                        && "0".equals(jsonArray.getJSONObject(0).getString("level"))) {
                    it.remove();
                }
            }
        }
        Set<SchoolWeek> schoolWeeks = schoolWeekDAO.findSchoolWeekByDate(lendingTime);
        SchoolWeek lendingDate = schoolWeeks.size() == 0 ? null : schoolWeeks.iterator().next();
        if (labReservations != null && labReservations.size() > 0 && lendingDate != null) {
            for (LabReservation labReservation : labReservations) {
                for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()) {
                    if (labReservation.getLendingTime().equals(lendingTime)&&labReservationId!=null&&!labReservation.getId().equals(labReservationId)) {//和借用日期在同一天的
                        if (lrtt.getStartTime().after(endTime) ||
                                lrtt.getEndTime().before(startTime) ||
                                lrtt.getStartTime().equals(endTime) ||
                                lrtt.getEndTime().equals(startTime)) {//未和所选时间冲突
                            //do nothing
                        } else {
                            lendingStatus = 3;
                            return lendingStatus;
                        }
                    }
                }
            }
        }
        Set<TimetableLabRelated> timetableLabRelateds = labRoom.getTimetableLabRelateds();
        if (schoolWeeks.size() != 0) {
            SchoolWeek schoolWeek = schoolWeeks.iterator().next();
            for (TimetableLabRelated t : timetableLabRelateds) {
                Set<TimetableAppointmentSameNumber> timetableAppointmentSameNumbers =
                        t.getTimetableAppointment().getTimetableAppointmentSameNumbers();
                for (TimetableAppointmentSameNumber tas : timetableAppointmentSameNumbers) {
                    if (tas.getStartWeek() <= schoolWeek.getWeek() && tas.getEndWeek() >= schoolWeek.getWeek() && schoolWeek.getWeekday().equals(t.getTimetableAppointment().getWeekday())) {
                        SystemTime start = systemTimeDAO.findSystemTimeBySection(tas.getStartClass()).iterator().next();
                        SystemTime end = systemTimeDAO.findSystemTimeBySection(tas.getEndClass()).iterator().next();
                        if (start.getStartDate().after(endTime) ||
                                end.getEndDate().before(startTime) ||
                                start.getStartDate().equals(endTime) ||
                                end.getEndDate().equals(startTime)) {//未和所选时间冲突
                            //do nothing
                        } else {
                            lendingStatus = 3;
                            return lendingStatus;
                        }
                    }
                }
            }
        }

        return lendingStatus;
    }

    /**
     * 实验室预约作废
     * @param labReservationId 实验室预约id
     * @return 成功的字符串
     * @author 黄保钱 2019-1-11
     */
    @Override
    public String obsoleteLabReservation(Integer labReservationId){
        AuditRefuseBackup auditRefuseBackup = new AuditRefuseBackup();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        LabReservation labReservation = labReservationDAO.findLabReservationById(labReservationId);
        String businessType = "LabRoomReservation" + labReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        Map<String, String> allParams = new HashMap<>();
        allParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        allParams.put("businessAppUid", labReservationId.toString());
        allParams.put("businessUid", labReservation.getLabRoom().getId().toString());
        String allStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", allParams);
        JSONArray allJsonArray = JSONObject.parseObject(allStr).getJSONArray("data");
        String auditInfo = "";
        String auditContent = "";
        boolean flag = true;
        for (int i = 0; i < allJsonArray.size(); i++) {
            JSONObject o = allJsonArray.getJSONObject(i);
            String result = o.getString("result");
            String authName = o.getString("authName");
            Authority authority = authorityDAO.findAuthorityByName(authName);
            auditInfo += authority.getCname();
            String auditUsername = o.getString("auditUser");
            User auditUser = shareService.findUserByUsername(auditUsername);
            auditInfo += auditUser.getCname() + result;
            auditContent += o.getString("info");
            auditInfo += ", ";
            auditContent += ", ";
            if ("审核拒绝".equals(result)) {
                flag = false;
                break;
            }
        }
        auditRefuseBackup.setAuditInfo(auditInfo);
        auditRefuseBackup.setAuditContent(auditContent);
        auditRefuseBackup = auditRefuseBackupDAO.store(auditRefuseBackup);
        Map<String, String> delParams = new HashMap<>();
        delParams.put("businessAppUid", labReservationId.toString());
        delParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        String delStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/deleteBusinessAudit", delParams);
        SchoolWeek schoolWeek = schoolWeekDAO.findSchoolWeekByDate(labReservation.getLendingTime()).iterator().next();
        for(List<Integer> integerList: getSectionsList(labReservation)){
            RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
            refuseItemBackup.setAuditRefuseBackup(auditRefuseBackup);
            refuseItemBackup.setBusinessId(labReservation.getLabRoom().getId().toString());
            refuseItemBackup.setCreator(labReservation.getUser().getUsername());
            refuseItemBackup.setStartClass(integerList.get(0));
            refuseItemBackup.setEndClass(integerList.get(integerList.size() - 1));
            refuseItemBackup.setStartWeek(schoolWeek.getWeek());
            refuseItemBackup.setEndWeek(schoolWeek.getWeek());
            refuseItemBackup.setTerm(schoolWeek.getSchoolTerm().getId());
            refuseItemBackup.setWeekday(schoolWeek.getWeekday());
            refuseItemBackup.setType("LabRoomReservation");
            refuseItemBackup.setLabRoomName(labReservation.getLabRoom().getLabRoomName());
            String operationItems = "";
            if (pConfigDTO.PROJECT_NAME.equals("shjulims")) {
                operationItems += "预约用途： " + labReservation.getCDictionaryByLendingType().getCName() +
                        "\n预约部门： " + labReservation.getLendingUnit() +
                        "\n使用人数： " + labReservation.getNumber() +
                        "\n预约人电话： " + labReservation.getLendingUserPhone() +
                        "\n预约原因： " + labReservation.getLendingReason() ;
            } else {
                operationItems += "预约用途： " + labReservation.getCDictionaryByLendingType().getCName() +
                        "\n使用对象： " + labReservation.getCDictionaryByLendingUserType().getCName() +
                        ("校内学生".equals(labReservation.getCDictionaryByLendingUserType().getCName()) ?
                                "\n班级： " + labReservation.getSchoolClasses().getClassName() : "") +
                        "\n预约部门： " + labReservation.getLendingUnit() +
                        "\n使用人数： " + labReservation.getNumber() +
                        "\n预约人电话： " + labReservation.getLendingUserPhone() +
                        "\n预约原因： " + labReservation.getLendingReason()
                ;
            }
            refuseItemBackup.setOperationItemName(operationItems);
            refuseItemBackup.setMemo("预约作废");
            refuseItemBackupDAO.store(refuseItemBackup);
        }
        if(flag){
            timetableAppointmentDAO.remove(labReservation.getTimetableAppointment());
        }else{
            labReservationDAO.remove(labReservation);
        }
        return "success";
    }

    /**
     * Description 根据预约获取该预约的节次
     * @param labReservation 预约
     * @return 节次列表
     * @author 黄保钱 2019-1-20
     */
    @Override
    public List<List<Integer>> getSectionsList(LabReservation labReservation){
        List<Integer> sections = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        for(LabReservationTimeTable lrtt: labReservation.getLabReservationTimeTables()){
            Integer section = getSystemTimeByStartAndEnd(lrtt.getStartTime(), lrtt.getEndTime()).getSection();
            sections.add(section);
        }
        Collections.sort(sections);
        List<Integer> temp = new ArrayList<>();
        boolean flag = false;
        if(sections.size() == 1){
            temp.add(sections.get(0));
            results.add(temp);
        }else {
            for (int i = 0; i < sections.size() - 1; i++) {
                if (flag) {
                    results.add(temp);
                    temp = new ArrayList<>();
                    flag = false;
                }
                temp.add(sections.get(i));
                if(sections.get(i+1) - sections.get(i) != 1){
                    flag = true;
                }
            }
            if (flag) {
                results.add(temp);
                temp = new ArrayList<>();
                temp.add(sections.get(sections.size() - 1));
                results.add(temp);
            }else{
                temp.add(sections.get(sections.size() - 1));
                results.add(temp);
            }
        }
        return results;
    }

    /**
     * Description 获取实验室预约审核拒绝日志列表
     * @param firstResult 偏移量
     * @param maxResult 获取的最大数据数量
     * @param labName 实验室名称
     * @return 实验室预约审核拒绝日志列表
     * @author 黄保钱 2019-1-20
     */
    @Override
    public List<AuditRefuseBackup> getAuditRefuseBackupForLabReservation(Integer firstResult, Integer maxResult, String labName){
        StringBuilder sql = new StringBuilder("select distinct arb from AuditRefuseBackup arb join arb.refuseItemBackup rib where 1=1 and (rib.type = 'LabRoomReservation' or rib.type = 'CancelLabRoomReservation')");
        if(labName != null) {
            sql.append(" and rib.labRoomName like '%").append(labName).append("%' ");
        }
        List<AuditRefuseBackup> auditRefuseBackups = auditRefuseBackupDAO.executeQuery(sql.toString(), firstResult, maxResult);
        return auditRefuseBackups;
    }

    /**
     * Description 获取实验室预约审核拒绝日志总数量
     * @param labName 实验室名称
     * @return 实验室预约审核拒绝日志总数量
     * @author 黄保钱 2019-1-20
     */
    @Override
    public Integer getCountAuditRefuseBackupForLabReservation(String labName){
        StringBuilder sql = new StringBuilder("select count(distinct arb) from AuditRefuseBackup arb join arb.refuseItemBackup rib where 1=1 and rib.type = 'LabRoomReservation' ");
        if(labName != null) {
            sql.append(" and rib.labRoomName like '%").append(labName).append("%' ");
        }
        Integer total = ((Long) auditRefuseBackupDAO.executeQuerySingleResult(sql.toString())).intValue();
        return total;
    }

    /**
     * 实验室预约取消
     *
     * @param labReservationId 实验室预约id
     * @return 成功的字符串
     * @author 黄保钱 2019-5-8
     */
    @Override
    public String cancelLabReservation(Integer labReservationId) {
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        LabReservation labReservation = labReservationDAO.findLabReservationById(labReservationId);
        labReservation.setAuditStage(7);
        labReservationDAO.store(labReservation);


        // 审核微服务
        Map<String, String> params = new HashMap<>();
        //默认教务排课，type=1
        String businessType = "CancelLabRoomReservation";
        params.put("businessUid", "-1");
        params.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        params.put("businessAppUid", labReservation.getId().toString());
        String s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/saveInitBusinessAuditStatus", params);
        JSONObject jsonObject = JSON.parseObject(s);
        String statusStr = jsonObject.getString("status");
        if(!statusStr.equals("success")){
            return "error";
        }
        s = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getCurrAuditStage", params);
        jsonObject = JSON.parseObject(s);
        statusStr = jsonObject.getString("status");
        if(!statusStr.equals("success")){
            return "error";
        }
        JSONArray currArray = jsonObject.getJSONArray("data");
        Integer curStage = -2;
        String curAuthName = "";
        if (currArray != null && currArray.size() > 0) {
            JSONObject o = currArray.getJSONObject(0);
            curStage = o.getIntValue("level");
            curAuthName = o.getString("result");
        }

        List<User> nextUsers = this.getNextUsers(curAuthName, labReservation.getId().toString());

        Message message = new Message();
        Calendar date1 = Calendar.getInstance();
        message.setSendUser(shareService.getUserDetail().getCname());
        message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
        message.setCond(0);
        message.setTitle("实验室取消预约审核");
        String content = "<a onclick='changeMessage(this)' href=\"../auditing/auditList?businessType=CancelLabRoomReservation&businessUid=-1&businessAppUid=" + labReservationId + "\">审核</a>";
        message.setContent(content);
        message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
        message.setCreateTime(date1);
        for(User user: nextUsers){
            message.setTage(2);
            shareService.sendMsg(user, message);
        }
        // 给预约人发消息
        message.setTitle("实验室取消预约提交成功");
        content = "<a onclick='changeMessage(this)' href=\"../auditing/auditList?businessType=" + businessType + "&businessUid=-1&businessAppUid=" + labReservation.getId() + "\">点击查看</a>";
        message.setContent(content);
        message.setTage(1);
        shareService.sendMsg(labReservation.getUser(), message);
        return "success";
    }

    /**
     * 实验室预约取消
     *
     * @param labReservationId 实验室预约id
     * @return 成功的字符串
     * @author 黄保钱 2019-5-8
     */
    @Override
    public String updateCancelLabReservation(Integer labReservationId) {
        AuditRefuseBackup auditRefuseBackup = new AuditRefuseBackup();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();

        LabReservation labReservation = labReservationDAO.findLabReservationById(labReservationId);
        String businessType = "CancelLabRoomReservation";
        Map<String, String> allParams = new HashMap<>();
        allParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        allParams.put("businessAppUid", labReservationId.toString());
        allParams.put("businessUid", "-1");
        String allStr = HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/getBusinessLevelStatus", allParams);
        JSONArray allJsonArray = JSONObject.parseObject(allStr).getJSONArray("data");
        String auditInfo = "";
        String auditContent = "";
        for (int i = 0; i < allJsonArray.size(); i++) {
            JSONObject o = allJsonArray.getJSONObject(i);
            String result = o.getString("result");
            String authName = o.getString("authName");
            Authority authority = authorityDAO.findAuthorityByName(authName);
            auditInfo += authority.getCname();
            String auditUsername = o.getString("auditUser");
            User auditUser = shareService.findUserByUsername(auditUsername);
            auditInfo += auditUser.getCname() + result;
            auditContent += o.getString("info");
            auditInfo += ", ";
            auditContent += ", ";
            if ("审核拒绝".equals(result)) {
                break;
            }
        }
        auditRefuseBackup.setAuditInfo(auditInfo);
        auditRefuseBackup.setAuditContent(auditContent);
        auditRefuseBackup = auditRefuseBackupDAO.store(auditRefuseBackup);
        Map<String, String> delParams = new HashMap<>();
        businessType = "LabRoomReservation" + labReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
        delParams.put("businessAppUid", labReservationId.toString());
        delParams.put("businessType", pConfigDTO.PROJECT_NAME + businessType);
        HttpClientUtil.doPost(pConfigDTO.auditServerUrl + "audit/deleteBusinessAudit", delParams);
        SchoolWeek schoolWeek = schoolWeekDAO.findSchoolWeekByDate(labReservation.getLendingTime()).iterator().next();
        for(List<Integer> integerList: getSectionsList(labReservation)){
            RefuseItemBackup refuseItemBackup = new RefuseItemBackup();
            refuseItemBackup.setAuditRefuseBackup(auditRefuseBackup);
            refuseItemBackup.setBusinessId(labReservation.getLabRoom().getId().toString());
            refuseItemBackup.setCreator(labReservation.getUser().getUsername());
            refuseItemBackup.setStartClass(integerList.get(0));
            refuseItemBackup.setEndClass(integerList.get(integerList.size() - 1));
            refuseItemBackup.setStartWeek(schoolWeek.getWeek());
            refuseItemBackup.setEndWeek(schoolWeek.getWeek());
            refuseItemBackup.setTerm(schoolWeek.getSchoolTerm().getId());
            refuseItemBackup.setWeekday(schoolWeek.getWeekday());
            refuseItemBackup.setType("CancelLabRoomReservation");
            refuseItemBackup.setLabRoomName(labReservation.getLabRoom().getLabRoomName());
            String operationItems = "";
            if (pConfigDTO.PROJECT_NAME.equals("shjulims")) {
                operationItems += "预约用途： " + labReservation.getCDictionaryByLendingType().getCName() +
                        "\n预约部门： " + labReservation.getLendingUnit() +
                        "\n使用人数： " + labReservation.getNumber() +
                        "\n预约人电话： " + labReservation.getLendingUserPhone() +
                        "\n预约原因： " + labReservation.getLendingReason() ;
            } else {
                operationItems += "预约用途： " + labReservation.getCDictionaryByLendingType().getCName() +
                        "\n使用对象： " + labReservation.getCDictionaryByLendingUserType().getCName() +
                        ("校内学生".equals(labReservation.getCDictionaryByLendingUserType().getCName()) ?
                                "\n班级： " + labReservation.getSchoolClasses().getClassName() : "") +
                        "\n预约部门： " + labReservation.getLendingUnit() +
                        "\n使用人数： " + labReservation.getNumber() +
                        "\n预约人电话： " + labReservation.getLendingUserPhone() +
                        "\n预约原因： " + labReservation.getLendingReason()
                ;
            }
            refuseItemBackup.setOperationItemName(operationItems);
            refuseItemBackup.setMemo("预约取消");
            refuseItemBackupDAO.store(refuseItemBackup);
        }
        timetableAppointmentDAO.remove(labReservation.getTimetableAppointment());
        return "success";
    }

    /**
     * 获取下一级审核人
     * @param nextAuth
     * @param businessAppUid
     * @return
     */
    @Override
    public List<User> getNextUsers(String nextAuth, String businessAppUid){
        Integer id = Integer.parseInt(businessAppUid);
        LabReservation labReservation = labReservationDAO.findLabReservationById(id);
        List<User> auditUsers = new ArrayList<>();
        switch (nextAuth){
            case "LABMANAGER":
                for(LabRoomAdmin admin: labReservation.getLabRoom().getLabRoomAdmins()){
                    if(admin.getTypeId() == 1) {
                        auditUsers.add(admin.getUser());
                    }
                }
                break;
            case "TEACHER":
                auditUsers.add(labReservation.getTeacher());
                break;
            case "EXCENTERDIRECTOR":
                auditUsers.add(labReservation.getLabRoom().getLabCenter().getUserByCenterManager());
                break;
            default:
                auditUsers.addAll(shareService.findUsersByAuthorityNameAndAcno(nextAuth, labReservation.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber()));
        }
        return auditUsers;
    }
}
