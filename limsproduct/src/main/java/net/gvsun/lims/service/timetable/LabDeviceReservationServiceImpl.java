package net.gvsun.lims.service.timetable;

        import com.alibaba.fastjson.JSONArray;
        import com.alibaba.fastjson.JSONObject;
        import net.gvsun.lims.dto.timetable.LabDeviceReservationParamDTO;
        import net.zjcclims.constant.CommonConstantInterface;
        import net.zjcclims.dao.*;
        import net.zjcclims.domain.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;

        import net.zjcclims.service.common.ShareService;
        import net.zjcclims.service.device.LabRoomDeviceReservationService;
        import net.zjcclims.service.device.LabRoomDeviceService;
        import net.gvsun.lims.service.auditServer.AuditService;
        import org.springframework.web.bind.annotation.ResponseBody;


        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

/**************************************************************************************
 * Description：设备预约模块
 *
 * author: Hezhaoyi
 * 2019-3-1
 *************************************************************************************/
@Service("labReservationService")
public class LabDeviceReservationServiceImpl implements LabDeviceReservationService {

    @Autowired
    LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    LabRoomLimitTimeDAO labRoomLimitTimeDAO;
    @Autowired
    LabCenterDAO labCenterDAO;
    @Autowired
    CDictionaryDAO cDictionaryDAO;
    @Autowired
    SchoolDeviceDAO schoolDeviceDAO;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    ShareService shareService;
    @Autowired
    LabRoomDeviceReservationService labRoomDeviceReservationService;
    @Autowired
    LabRoomDeviceService labRoomDeviceService;
    @Autowired
    private AuditService auditService;


    /****************************************************************************
     * Description：保存设备预约并返回是否保存成功
     * author：李小龙
     ****************************************************************************/
    public String saveLabDeviceReservation(LabDeviceReservationParamDTO labDeviceReservationParamDTO,HttpServletResponse response) throws Exception {

        //实验设备id
        int equinemtid = labDeviceReservationParamDTO.getEquinemtid();
        //描述
        String description = labDeviceReservationParamDTO.getDescription();
        //联系电话
        String phone = labDeviceReservationParamDTO.getPhone();
        //导师
        String teacher = labDeviceReservationParamDTO.getTeacher();
        //开始时间
        String startDate = labDeviceReservationParamDTO.getStartDate();
        //结束时间
        String endDate = labDeviceReservationParamDTO.getEndDate();
        // id对应的实验室设备
        LabRoomDevice device = labRoomDeviceService.findLabRoomDeviceByPrimaryKey(equinemtid);
        // 当前登录人
        User user = shareService.getUser();
        // 要保存进数据库的预约对象
        LabRoomDeviceReservation reservation = new LabRoomDeviceReservation();
        reservation.setLabRoomDevice(device);// 预约的设备
        reservation.setUserByReserveUser(user);// 预约人
        reservation.setContent(description);// 描述
        reservation.setPhone(phone);// 联系电话
        // 指导老师
        User u = userDAO.findUserByPrimaryKey(teacher);
        reservation.setUserByTeacher(u);
        // 申请性质--默认为预约
        CDictionary p = cDictionaryDAO.findCDictionaryByPrimaryKey(617);
        reservation.setCReservationProperty(p);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 开始@时间
        Date date = sdf.parse(startDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        reservation.setBegintime(calendar1);
        // 结束@时间
        Date date2 = sdf.parse(endDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        reservation.setEndtime(calendar2);
        // 申请@时间
        reservation.setTime(Calendar.getInstance());
        // 申请所属的学期
        SchoolTerm term = shareService.getBelongsSchoolTerm(Calendar.getInstance());
        reservation.setSchoolTerm(term);
        // 获取当前学期
        int termId = shareService.getBelongsSchoolTerm(Calendar.getInstance()).getId();
        // 实验室禁用时间段列表
        List<LabRoomLimitTime> labRoomLimitTimes = labRoomLimitTimeDAO.executeQuery("select c from LabRoomLimitTime c where c.labId= " + device.getLabRoom().getId() + " and c.schoolTerm.id=" + termId, 0, -1);
        // 如果是禁用时间匹配则返回limit
        //东华纺织学院特殊配置-配置成全校
        /*String academy = labCenterDAO.findLabCenterByPrimaryKey(cid).getSchoolAcademy().getAcademyNumber();
        if(academy.equals("0201")) {// 纺织学院，排课时设备设置对外开放
            if (!shareService.isLimitedByAppointment(labRoomLimitTimes, calendar1, calendar2, equinemtid)) {
                return "LIMIT";
            }
        }else {
            if (device.getLabRoom() != null
                    && device.getLabRoom().getOpenInweekend() != null
                    && device.getLabRoom().getOpenInweekend() == 1
                    && !shareService.isLimitedByTimeNew(equinemtid, calendar1, calendar2, cid)) {
                return "LIMIT";
            }
            if (!shareService.isLimitedByTime(labRoomLimitTimes, calendar1, calendar2)) {
                return "LIMIT";
            }
        }*/
        if (!shareService.isLimitedByAppointment(labRoomLimitTimes, calendar1, calendar2, equinemtid)) {
            return "LIMIT";
        }
        if (device.getLabRoom() != null
                    && device.getLabRoom().getOpenInweekend() != null
                    && device.getLabRoom().getOpenInweekend() == 1
                    && !shareService.isLimitedByTimeNew(equinemtid, calendar1, calendar2)) {
            return "LIMIT";
        }
        if (!shareService.isLimitedByTime(labRoomLimitTimes, calendar1, calendar2)) {
            return "LIMIT";
        }

        // 设置了不能周末预约，却把时间选在周末的情况，要返回limit
        if (device.getLabRoom() != null
                && device.getLabRoom().getOpenInweekend() != null
                && device.getLabRoom().getOpenInweekend() == 0
                && shareService.isInWeedend(calendar1.getTime(), calendar2.getTime())) {
            return "LIMIT";
        }
        // 状态
        // 是否需要审核-审核微服务切换
        /*String isAudit = device.getCDictionaryByIsAudit().getCNumber();
        boolean needRefresh = false;
        if (isAudit == "2" && (device.getUser() != null && user.getUsername().equals(device.getUser().getUsername()))) {// 不需要审核或者预约人为设备管理员本人
            CDictionary status = cDictionaryDAO.findCDictionaryByPrimaryKey(615);
            reservation.setCAuditResult(status);
            needRefresh = true;
        }
        if (isAudit == "2" && (device.getUser() != null && !user.getUsername().equals(device.getUser().getUsername()))) {// 需要审核并且预约人不是设备管理员本人
            // 默认先设为审核中
            CDictionary status = cDictionaryDAO.findCDictionaryByPrimaryKey(614);
            reservation.setCAuditResult(status);
            if (device.getCDictionaryByTeacherAudit() != null) {// 贺子龙 2015-11-06
                int CActiveByTeacherAuditId = device.getCDictionaryByTeacherAudit().getId();
                if (CActiveByTeacherAuditId == 622) {// 不需要导师
                    if (device.getCActiveByLabManagerAudit() != null) {
                        int CActiveByLabManagerAuditId = device.getCActiveByLabManagerAudit().getId();
                        if (CActiveByLabManagerAuditId == 622) {// 不需要实验室管理员
                            if (device.getCDictionaryByManagerAudit() != null) {
                                int CActiveByManagerAuditId = device.getCDictionaryByManagerAudit().getId();
                                if (CActiveByManagerAuditId == 622) {// 不需要设备管理员
                                    // 审核通过
                                    CDictionary pass = cDictionaryDAO.findCDictionaryByPrimaryKey(615);
                                    reservation.setCAuditResult(pass);
                                    // 调用存储过程
                                    System.out.println("---预约直接通过---");
                                    labRoomDeviceService.callLabDeviceReservationFunction();
                                    System.out.println("---调用存储过程---");
                                    // 针对当天预约，把预约人刷新到门禁和电源控制器中去
                                    try {
                                        // 门禁
                                        System.out.println("---刷新门禁控制器---");
                                        labRoomDeviceService.refreshDeviceReservation(reservation);
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    try {
                                        // 电源
                                        labRoomDeviceService.refreshLabRoomDeviceReservation(reservation);
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }*/
        // 根据设备id查询设备的预约记录
        List<LabRoomDeviceReservation> reservationList = labRoomDeviceService.findReservationListByDeviceId(equinemtid);
        int flag = 1;// 标记为0为失败，1为成功
        // 循环遍历预约记录，看是否和以前的预约有冲突
        for (LabRoomDeviceReservation r : reservationList) {
            Calendar start = r.getBegintime();
            Calendar end = r.getEndtime();
            if (end.after(calendar1) && start.before(calendar2)) {
                flag = 0;
            }
        }
        String dateStr = sdf.format(Calendar.getInstance().getTime());
        if (flag > 0) {
            reservation.setStage(0);
            if (device.getSchoolDevice().getInnerSame() != null && !device.getSchoolDevice().getInnerSame().equals("")) {
                reservation.setInnerSame(device.getSchoolDevice().getInnerSame() + "-" + dateStr);
                reservation.setInnerDeviceName(device.getSchoolDevice().getInnerDeviceName().replace("]", "]</br>"));
            } else {
                reservation.setInnerSame("device-" + device.getId() + "-" + dateStr);// 为了查询时候的group by InnerSame
            }
            LabRoomDeviceReservation reservationNew = labRoomDeviceReservationService.saveLabRoomDeviceReservation(reservation);
            SchoolDevice schoolDevice = device.getSchoolDevice();
            //审核微服务开始
            // 提交设备预约到审核服务
            String businessType = "DeviceReservation" + device.getLabRoom().getLabCenter().getSchoolAcademy().getAcademyNumber();
            //保存业务预约初始的审核级别状态
            auditService.saveInitBusinessAudit(device.getId().toString(), businessType, reservationNew.getId().toString());
            //获取当前业务预约的审核状态
            String currAuditStr = auditService.getCurrAudit(reservationNew.getId().toString(), businessType);
            JSONObject currAuditJSONObject = JSONObject.parseObject(currAuditStr);
            if(!"success".equals(currAuditJSONObject.getString("status"))){
                return "error";
            }
            JSONArray currAuditJSONArray = currAuditJSONObject.getJSONArray("data");
            String firstAuthName = currAuditJSONArray.getJSONObject(0).getString("result");

            // 不需要审核
            if (device.getCDictionaryByIsAudit() == null ||
                    (device.getCDictionaryByIsAudit() != null && device.getCDictionaryByIsAudit().getCName().equals("否")) ||
                    (currAuditJSONArray != null && currAuditJSONArray.size() != 0 && currAuditJSONArray.getJSONObject(0).getIntValue("level") == -1)) {
                //预约成功刷新设备使用机时和次数
                if(reservationNew.getReserveHours() != null){
                    if (schoolDevice.getUseHours() != null) {
                        schoolDevice.setUseHours(schoolDevice.getUseHours().add(reservationNew.getReserveHours()));
                    } else {
                        schoolDevice.setUseHours(reservationNew.getReserveHours());
                    }
                }
                /*if (schoolDevice.getUseHours() != null ) {
                    schoolDevice.setUseHours(schoolDevice.getUseHours().add(reservationNew.getReserveHours()));
                } else {
                    schoolDevice.setUseHours(reservationNew.getReserveHours());
                }*/
                if (schoolDevice.getUseCount() != null) {
                    schoolDevice.setUseCount(schoolDevice.getUseCount() + 1);
                } else {
                    schoolDevice.setUseCount(1);
                }
                schoolDeviceDAO.store(schoolDevice);
                schoolDeviceDAO.flush();
                CDictionary status = shareService.getCDictionaryByCategory("c_audit_result", "2");
                reservationNew.setCAuditResult(status);
                reservationNew.setRemark("该实验室预约不需要审核");

                System.out.println("---预约直接通过---");
                labRoomDeviceService.callLabDeviceReservationFunction();
                System.out.println("---调用存储过程---");
                // 针对当天预约，把预约人刷新到门禁和电源控制器中去
                try {
                    // 门禁
                    System.out.println("---刷新门禁控制器---");
                    labRoomDeviceService.refreshDeviceReservation(reservation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    // 电源
                    labRoomDeviceService.refreshLabRoomDeviceReservation(reservation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (device.getCDictionaryByIsAudit().getCName().equals("是")) {
                //首先设置审核状态为审核中
                CDictionary status = shareService.getCDictionaryByCategory("c_audit_result", "1");
                reservationNew.setCAuditResult(status);
                reservationNew.setRemark("");

                //消息(发送到审核人)
                Message message = new Message();
                Calendar date1 = Calendar.getInstance();
                message.setSendUser(shareService.getUserDetail().getCname());
                message.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
                //紧急状态默认为紧急
                message.setCond(0);
                message.setTitle("设备预约增加");
                message.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                message.setCreateTime(date1);
                //如果当前审核状态为老师 审核状态为审核中 且 当前登陆人即申请人角色不是学生
                if("TEACHER".equals(firstAuthName) && !"0".equals(user.getUserRole())){
                    //申请人不是学生不需要导师审核-保存业务单级审核结果-并返回审核后所处结果状态
                    String saveStr = auditService.saveBusinessLevel(reservationNew.getId().toString(),
                            reservationNew.getLabRoomDevice().getId().toString(), "pass", "不是学生不需要导师审核",
                            businessType, user.getUsername());
                    JSONObject saveJSONObject = JSONObject.parseObject(saveStr);
                    JSONObject saveResult = saveJSONObject.getJSONObject("data");
                    firstAuthName = saveResult.getString("result");
                }

                //判断预约产生时处于第几级审核状态
                if ("TEACHER".equals(firstAuthName)) {
                    //如果当前需要教师审核将消息推到相关老师
                    String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
                    message.setContent(content);
                    message.setUsername(reservationNew.getUserByTeacher().getUsername());
                    //保存消息类型为我的审核
                    message.setTage(2);
                    messageDAO.store(message);
                    messageDAO.flush();
                } else if ("CFO".equals(firstAuthName)) {
                    //产生系主任的消息
                    String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
                    message.setContent(content);
                    //根据申请人所属的学院获取所有的系主任
                    List<User> deans = shareService.findDeansByAcademyNumber(reservationNew.getUserByReserveUser().getSchoolAcademy());
                    if(deans != null && deans.size() > 0) {
                        for(User dean: deans) {
                            message.setUsername(dean.getUsername());
                            message.setTage(2);
                            messageDAO.store(message);
                            messageDAO.flush();
                        }
                    }
                }else if ("EQUIPMENTADMIN".equals(firstAuthName)) {
                    //产生设备管理员的消息
                    String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
                    message.setContent(content);
                    if (device.getUser() != null){
                        message.setUsername(device.getUser().getUsername());
                        message.setTage(2);
                        messageDAO.store(message);
                        messageDAO.flush();
                    }
                } else if ("LABMANAGER".equals(firstAuthName)) {
                    //产生实训室管理员的消息
                    String content = "<a onclick='changeMessage(this)'  href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
                    message.setContent(content);
                    if (device.getLabRoom().getLabRoomAdmins() != null) {
                        for (LabRoomAdmin labRoomAdmin : device.getLabRoom().getLabRoomAdmins()) {
                            message.setUsername(labRoomAdmin.getUser().getUsername());
                            message.setTage(2);
                            messageDAO.store(message);
                            messageDAO.flush();
                        }
                    }
                } else if ("EXCENTERDIRECTOR".equals(firstAuthName)) {
                    //产生实训中心主任的消息
                    String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
                    message.setContent(content);
                    if (device.getLabRoom().getLabCenter().getUserByCenterManager() != null) {
                        message.setUsername(device.getLabRoom().getLabCenter().getUserByCenterManager().getUsername());
                        message.setTage(2);
                        messageDAO.store(message);
                        messageDAO.flush();
                    }
                } else if (!"pass".equals(firstAuthName) && !"fail".equals(firstAuthName)) {
                    //产生实训部主任的消息
                    String content = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=" + 1 + "&currpage=1\">审核</a>";
                    message.setContent(content);
                    if (shareService.findUsersByAuthorityName(firstAuthName) != null) {
                        for (User authUser : shareService.findUsersByAuthorityName(firstAuthName)) {
                            message.setUsername(authUser.getUsername());
                            message.setTage(2);
                            messageDAO.store(message);
                            messageDAO.flush();
                        }
                    }
                }  else if("pass".equals(firstAuthName)) {
                    reservationNew.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "2"));
                }else if("fail".equals(firstAuthName)) {
                    reservationNew.setCAuditResult(shareService.getCDictionaryByCategory("c_audit_result", "3"));
                }

                //消息(发送到自己)
                Message messageSendSelf = new Message();
                messageSendSelf.setSendUser(shareService.getUserDetail().getCname());
                messageSendSelf.setSendCparty(shareService.getUserDetail().getSchoolAcademy().getAcademyName());
                messageSendSelf.setCond(0);
                messageSendSelf.setTitle("设备预约");
                messageSendSelf.setMessageState(CommonConstantInterface.INT_Flag_ZERO);
                messageSendSelf.setCreateTime(date1);
                String contents = "<a onclick='changeMessage(this)' href=\"../LabRoomDeviceReservation/checkButton?id=" + reservationNew.getId() + "&tage=0&state=0&currpage=1\">查看</a>";
                messageSendSelf.setContent(contents);
                messageSendSelf.setUsername(shareService.getUserDetail().getUsername());
                messageSendSelf.setTage(1);
                messageDAO.store(messageSendSelf);
                messageDAO.flush();

            /*String dateStr1 = sdf.format(Calendar.getInstance().getTime());
            reservation.setStage(0);
            if (device.getSchoolDevice().getInnerSame() != null && !device.getSchoolDevice().getInnerSame().equals("")) {
                reservation.setInnerSame(device.getSchoolDevice().getInnerSame() + "-" + dateStr1);
                reservation.setInnerDeviceName(device.getSchoolDevice().getInnerDeviceName().replace("]", "]</br>"));
            } else {
                reservation.setInnerSame("device-" + device.getId() + "-" + dateStr1);// 为了查询时候的group by InnerSame
            }

            labRoomDeviceReservationService.saveLabRoomDeviceReservationNew(reservation, response);*/

                // 调用存储过程
                labRoomDeviceService.callLabDeviceReservationFunction();
                // 针对当天预约，把预约人刷新到门禁和电源控制器中去
                try {
                    // 门禁
                    labRoomDeviceService.refreshDeviceReservation(reservation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    // 电源
                    labRoomDeviceService.refreshLabRoomDeviceReservation(reservation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return "success";
        }else{
            return "error";
        }
    }

}
