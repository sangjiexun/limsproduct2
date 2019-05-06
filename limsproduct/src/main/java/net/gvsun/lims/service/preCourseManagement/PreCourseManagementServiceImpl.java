package net.gvsun.lims.service.preCourseManagement;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.vo.preCourseManagement.PreLabRoomListVO;
import net.gvsun.lims.vo.preCourseManagement.PreTimetableAppointmentListVO;
import net.gvsun.lims.vo.preCourseManagement.PreTimetableScheduleVO;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("PreCourseManagementService")
public class PreCourseManagementServiceImpl implements PreCourseManagementService {
    @Autowired
    private ShareService shareService;
    @Autowired
    private PreLabRoomDAO preLabRoomDAO;
    @Autowired
    private PreRoomTypeDAO preRoomTypeDAO;
    @Autowired
    private PreCapacityRangeDAO preCapacityRangeDAO;
    @Autowired
    private PreSoftwareDAO preSoftwareDAO;
    @Autowired
    private PreTimetableAppointmentDAO preTimetableAppointmentDAO;
    @Autowired
    private PreTimetableScheduleDAO preTimetableScheduleDAO;
    @Autowired
    private SchoolCourseInfoDAO schoolCourseInfoDAO;

    /****************************************************************************
     * @描述： 获取预排课房间记录
     * @作者 ：张德冰
     * @时间： 2018-12-19
     ****************************************************************************/
    @Override
    public BaseDTO getPreLabRoomListByPage(String search, int offset, int limit, String sort, String sortOrder,HttpServletRequest request) {

        //获取列表主查询语句
        StringBuffer listsql = new StringBuffer("select distinct p from PreLabRoom p where 1 = 1");
        //获取列表主查询语句
        StringBuffer cousql = new StringBuffer("select count(distinct p) from PreLabRoom p where 1 = 1");
        StringBuffer sql = new StringBuffer();

        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (p.roomName like '%" + search + "%' or");
            sql.append(" p.preCapacityRange.capaType like '%" + search + "%' or");
            sql.append(" p.preCapacityRange.capaRange like '%" + search + "%' or");
            sql.append(" p.preRoomType.roomType like '%" + search + "%' or");
            sql.append(" p.schoolAcademy.academyName like '%" + search + "%')");
        }

        //排序
        if ("roomName".equals(sort)||"roomName".equals(sort)) {
            sql.append(" order by p.roomName " +sortOrder);
        }else if ("capaRange".equals(sort)||"capaRange".equals(sort)) {
            sql.append(" order by p.preCapacityRange.capaRange " +sortOrder);
        }else if ("roomType".equals(sort)||"roomType".equals(sort)) {
            sql.append(" order by p.preRoomType.roomType " +sortOrder);
        }else if ("academyName".equals(sort)||"academyName".equals(sort)) {
            sql.append(" order by p.schoolAcademy.academyNumber " +sortOrder);
        }else{
            sql.append(" order by case when p.schoolAcademy.academyNumber='" +
                    request.getSession().getAttribute("selected_academy")+ "' then 0 else 1 end");
        }

        //总记录数
        cousql.append(sql);
        int total = ((Long) preLabRoomDAO.createQuerySingleResult(cousql.toString()).getSingleResult()).intValue();
        //获得记录
        listsql.append(sql);
        List<PreLabRoom> preLabRoomList = preLabRoomDAO.executeQuery(listsql.toString(), offset, limit);

        //判断是否有权限审核：0 没有，1 有
        Integer isAudit = 0;
        if (request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN")){
            isAudit = 1;
        }
        //封装VO
        List<PreLabRoomListVO> preLabRoomListVOs = new ArrayList<>();
        for (PreLabRoom l: preLabRoomList){
            PreLabRoomListVO preLabRoomListVO = new PreLabRoomListVO();
            preLabRoomListVO.setId(l.getId());
            //实验室名称
            preLabRoomListVO.setRoomName(l.getRoomName());
            //房间布局类型
            preLabRoomListVO.setRoomType(l.getPreRoomType().getRoomType());
            //容量
            String capaRange = "";
            if(l.getPreCapacityRange() != null) {
                capaRange = l.getPreCapacityRange().getCapaType() +"("+ l.getPreCapacityRange().getCapaRange()+"人)";
            }
            preLabRoomListVO.setCapaRange(capaRange);
            //学院名称
            if(l.getSchoolAcademy() != null){
                preLabRoomListVO.setAcademyName(l.getSchoolAcademy().getAcademyName());
            }
            //是否有权限操作
            preLabRoomListVO.setIsAudit(isAudit);
            preLabRoomListVOs.add(preLabRoomListVO);
        }

        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(preLabRoomListVOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /****************************************************************************
     * @描述： 获取房间布局类型记录
     * @作者 ：张德冰
     * @时间： 2018-12-19
     ****************************************************************************/
    @Override
    public List<PreRoomType> getPreRoomTypeListByPage(int currpage, int pagesize){
        String sql = "select distinct r from PreRoomType r where 1 = 1";
        return preRoomTypeDAO.executeQuery(sql,(currpage-1)*pagesize, pagesize);
    }

    /****************************************************************************
     * @描述： 容量范围
     * @作者 ：张德冰
     * @时间： 2018-12-20
     ****************************************************************************/
    @Override
    public List<PreCapacityRange> getPreCapacityRangeListByPage(int currpage, int pagesize){
        String sql = "select distinct c from PreCapacityRange c where 1 = 1";
        return preCapacityRangeDAO.executeQuery(sql,(currpage-1)*pagesize, pagesize);
    }

    /****************************************************************************
     * @描述： 所有软件
     * @作者 ：张德冰
     * @时间： 2018-12-21
     ****************************************************************************/
    @Override
    public List<PreSoftware> getPreSoftwareListByPage(int currpage, int pagesize){
        String sql = "select distinct s from PreSoftware s where 1 = 1";
        return preSoftwareDAO.executeQuery(sql,(currpage-1)*pagesize, pagesize);
    }

    /****************************************************************************
     * @描述： 获取预房间
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @Override
    public List<PreLabRoom> findPreLabRooms(String acno, Integer preCapacityRangeId, Integer preRoomTypeId, Integer preSoftwareId){
        StringBuffer sql = new StringBuffer("select distinct p from PreLabRoom p left join p.preSoftwares s where 1 = 1");
        //所属部门
        if (acno != null && !"".equals(acno)) {
            sql.append(" and p.schoolAcademy.academyNumber = " + acno);
        }
        //容量
        if (preCapacityRangeId != null && !"".equals(preCapacityRangeId)) {
            sql.append(" and p.preCapacityRange.id = " + preCapacityRangeId);
        }
        //布局类型
        if (preRoomTypeId != null && !"".equals(preRoomTypeId)) {
            sql.append(" and p.preRoomType.id = " + preRoomTypeId);
        }
        //软件
        if (preSoftwareId != null && !"".equals(preSoftwareId)) {
            sql.append(" and s.id = " + preSoftwareId);
        }
        return preLabRoomDAO.executeQuery(sql.toString(),0,0);
    }

    /****************************************************************************
     * @描述： 查询预排课记录(分页)
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @Override
    public List<PreTimetableAppointment> findPreTimetableAppointmentList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status){
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select distinct p from PreTimetableAppointment p left join p.preLabRooms l where 1 = 1");
        //学期
        sql.append(" and p.schoolTerm.id ="+termId);
        //获取当前登录人信息
        User user = shareService.getUserDetail();
        //判断是否为我的预排课，是、并且当前登录人不是超级管理员执行
        if(request.getParameter("isMy").equals("true") && !request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN")){
            sql.append(" and p.userByTeacher.username ='"+user.getUsername()+"'");

        }
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (p.schoolCourseInfo.courseName like '%" + search + "%' or");
            sql.append(" p.schoolCourseInfo.courseNumber like '%" + search + "%' or");
            sql.append(" p.userByTeacher.cname like '%" + search + "%' or");
            sql.append(" p.userByTutor.cname like '%" + search + "%' or");
            sql.append(" l.roomName like '%" + search + "%')");
        }

        if (status != null && !"".equals(status)) {
            //待发布
            if (status.equals("STAND_TO_RELEASE") ) {
                sql.append(" and p.state=0");
            }
            //已发布
            if (status.equals("RELEASED") ) {
                sql.append(" and p.state=1");
            }
        }

        //排序
        if ("courseName".equals(sort)||"courseName".equals(sort)) {
            sql.append(" order by p.schoolCourseInfo.courseName" +order);
        }else if ("stuNumber".equals(sort)||"stuNumber".equals(sort)) {
            sql.append(" order by p.stuNumber " +order);
        }else if ("teacher".equals(sort)||"teacher".equals(sort)) {
            sql.append(" order by p.userByTeacher.cname " +order);
        }else if ("tutor".equals(sort)||"tutor".equals(sort)) {
            sql.append(" order by p.userByTutor.cname " +order);
        }else if ("preLabRoomList".equals(sort)||"preLabRoomList".equals(sort)) {
            sql.append(" order by p.preLabRooms.roomName " +order);
        }else{
            sql.append(" order by case when p.schoolAcademy.academyNumber='" +
                    request.getSession().getAttribute("selected_academy")+ "' then 0 else 1 end");
        }
        return preTimetableAppointmentDAO.executeQuery(sql.toString(), offset, limit);
    }

    /****************************************************************************
     * @描述： 查询预排课记录记录数
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @Override
    public Integer countFindPreTimetableAppointmentList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status){
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select count(distinct p) from PreTimetableAppointment p left join p.preLabRooms l where 1 = 1");
        //学期
        sql.append(" and p.schoolTerm.id ="+termId);
        //获取当前登录人信息
        User user = shareService.getUserDetail();
        //判断是否为我的预排课，是、并且当前登录人不是超级管理员执行
        if(request.getParameter("isMy").equals("true") && !request.getSession().getAttribute("selected_role").equals("ROLE_SUPERADMIN")){
            sql.append(" and p.userByTeacher.username ='"+user.getUsername()+"'");

        }
        //查询条件
        if (search != null && !"".equals(search)) {
            sql.append(" and (p.schoolCourseInfo.courseName like '%" + search + "%' or");
            sql.append(" p.schoolCourseInfo.courseNumber like '%" + search + "%' or");
            sql.append(" p.userByTeacher.cname like '%" + search + "%' or");
            sql.append(" p.userByTutor.cname like '%" + search + "%' or");
            sql.append(" l.roomName like '%" + search + "%')");
        }

        if (status != null && !"".equals(status)) {
            //待发布
            if (status.equals("STAND_TO_RELEASE") ) {
                sql.append(" and p.state=0");
            }
            //已发布
            if (status.equals("RELEASED") ) {
                sql.append(" and p.state=1");
            }
        }
        return ((Long) preTimetableAppointmentDAO.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
    }


    /****************************************************************************
     * @描述： 获取预排课记录
     * @作者 ：张德冰
     * @时间： 2018-12-24
     ****************************************************************************/
    public BaseDTO getPreCourseList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status){

        //总记录数
        int total = countFindPreTimetableAppointmentList(offset, limit, search, sort, order, request, termId, status);
        //获得记录
        List<PreTimetableAppointment> preTimetableAppointmentList = findPreTimetableAppointmentList(offset, limit, search, sort, order, request, termId, status);

        //封装VO
        List<PreTimetableAppointmentListVO> preTimetableAppointmentListVOs = new ArrayList<>();
        for (PreTimetableAppointment l: preTimetableAppointmentList){
            PreTimetableAppointmentListVO preTimetableAppointmentListVO = new PreTimetableAppointmentListVO();
            preTimetableAppointmentListVO.setId(l.getId());
            //课程名称
            if(l.getSchoolCourseInfo() != null) {
                preTimetableAppointmentListVO.setCourseName(l.getSchoolCourseInfo().getCourseName());
            }
            //所属学院
            String acno = null;
            if(l.getSchoolAcademy() != null) {
                preTimetableAppointmentListVO.setAcademyName(l.getSchoolAcademy().getAcademyName());
                acno = l.getSchoolAcademy().getAcademyNumber();
            }
            //课程安排
            if(l.getPreTimetableSchedules().size() > 0){
                List<PreTimetableScheduleVO> preTimetableScheduleListVOs = new ArrayList<>();
                for (PreTimetableSchedule p:l.getPreTimetableSchedules()){
                    PreTimetableScheduleVO t = new PreTimetableScheduleVO();
                    //排课时间表id
                    t.setId(p.getId());
                    //星期
                    t.setStartWeekDay(p.getStartWday());
                    //开始周
                    t.setStartWeek(p.getStartWeek());
                    //结束周
                    t.setEndWeek(p.getEndWeek());
                    //起始节次
                    t.setStartClass(p.getStartClass());
                    //结束节次
                    t.setEndClass(p.getEndClass());
                    preTimetableScheduleListVOs.add(t);
                }
                preTimetableAppointmentListVO.setPreTimetableScheduleVO(preTimetableScheduleListVOs);
            }
            //上课人数
            preTimetableAppointmentListVO.setStuNumber(l.getStuNumber());
            //发布状态
            preTimetableAppointmentListVO.setState(l.getState().toString());
            //授课教师
            if(l.getUserByTeacher() != null){
                preTimetableAppointmentListVO.setTeacher(l.getUserByTeacher().getCname());
            }
            //指导教师
            if(l.getUserByTutor() != null){
                preTimetableAppointmentListVO.setTutor(l.getUserByTutor().getCname());
            }else {
                preTimetableAppointmentListVO.setTutor("无");
            }
            //预排课房间
            List<PreLabRoom> preLabRooms = findPreLabRoomsByCapacityRange(l);
            List<PreLabRoomListVO> preLabRoomListVOList = new ArrayList<>();
            for (PreLabRoom lr : preLabRooms) {
                PreLabRoomListVO pl = new PreLabRoomListVO();
                pl.setId(lr.getId());
                pl.setRoomName(lr.getRoomName());
                preLabRoomListVOList.add(pl);
            }
            preTimetableAppointmentListVO.setPreLabRoomListVO(preLabRoomListVOList);
            preTimetableAppointmentListVOs.add(preTimetableAppointmentListVO);
        }

        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(preTimetableAppointmentListVOs);
        baseVO.setTotal(total);
        return baseVO;
    }

    /****************************************************************************
     * @描述： 保存预排课时间
     * @作者 ：张德冰
     * @时间： 2018-12-25
     ****************************************************************************/
    @Override
    public boolean savePreTimetableSchedule(Integer preTimetableAppointmentId, int[] weeks, int[] classes, Integer weekday){
        Arrays.sort(classes);
        Arrays.sort(weeks);

        PreTimetableAppointment preTimetableAppointment = preTimetableAppointmentDAO.findPreTimetableAppointmentById(preTimetableAppointmentId);
        List<PreTimetableSchedule> TSweeks = new ArrayList<>();
        List<PreTimetableSchedule> TSclasses = new ArrayList<>();
        PreTimetableSchedule pts = new PreTimetableSchedule();

        //计算不连续排课日期的保存问题
        //对节次数组进行排序
        for (int i = 0; classes.length > i; i++) {
            if (i == 0&&i == classes.length - 1) {
                pts.setStartClass(classes[i]);
                pts.setEndClass(classes[i]);
                PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                pts_1.setStartClass(pts.getStartClass());
                pts_1.setEndClass(pts.getEndClass());
                TSclasses.add(pts_1);
            } else if (i == 0&&i != classes.length - 1) {
                pts.setStartClass(classes[i]);
                pts.setEndClass(classes[i]);
            } else if (i != 0&&i != classes.length - 1) {
                if (classes[i] - classes[i - 1] == 1) {
                    pts.setEndClass(classes[i]);
                } else {
                    PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                    pts_1.setStartClass(pts.getStartClass());
                    pts_1.setEndClass(pts.getEndClass());
                    TSclasses.add(pts_1);
                    pts.setStartClass(classes[i]);
                    pts.setEndClass(classes[i]);
                }
            } else if (i != 0&&i == classes.length - 1)  {
                if (classes[i] - classes[i - 1] == 1) {
                    pts.setEndClass(classes[i]);
                    PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                    pts_1.setStartClass(pts.getStartClass());
                    pts_1.setEndClass(pts.getEndClass());
                    TSclasses.add(pts_1);
                } else {
                    PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                    pts_1.setStartClass(pts.getStartClass());
                    pts_1.setEndClass(pts.getEndClass());
                    TSclasses.add(pts_1);
                    PreTimetableSchedule pts_2 = new PreTimetableSchedule();
                    pts_2.setStartClass(classes[i]);
                    pts_2.setEndClass(classes[i]);
                    TSclasses.add(pts_2);
                }
            }

        }

        //对周次数组进行排序
        for (int i = 0; weeks.length > i; i++) {
            if (i == 0&&i == weeks.length - 1) {
                pts.setStartWeek(weeks[i]);
                pts.setEndWeek(weeks[i]);
                PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                pts_1.setStartWeek(pts.getStartWeek());
                pts_1.setEndWeek(pts.getEndWeek());
                TSweeks.add(pts_1);
            } else if (i == 0&&i != weeks.length - 1) {
                pts.setStartWeek(weeks[i]);
                pts.setEndWeek(weeks[i]);
            } else if (i != 0&&i != weeks.length - 1) {
                if (weeks[i] - weeks[i - 1] == 1) {
                    pts.setEndWeek(weeks[i]);
                } else {
                    PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                    pts_1.setStartWeek(pts.getStartWeek());
                    pts_1.setEndWeek(pts.getEndWeek());
                    TSweeks.add(pts_1);
                    pts.setStartWeek(weeks[i]);
                    pts.setEndWeek(weeks[i]);
                }
            } else if (i != 0&&i == weeks.length - 1)  {
                if (weeks[i] - weeks[i - 1] == 1) {
                    pts.setEndWeek(weeks[i]);
                    PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                    pts_1.setStartWeek(pts.getStartWeek());
                    pts_1.setEndWeek(pts.getEndWeek());
                    TSweeks.add(pts_1);
                } else {
                    PreTimetableSchedule pts_1 = new PreTimetableSchedule();
                    pts_1.setStartWeek(pts.getStartWeek());
                    pts_1.setEndWeek(pts.getEndWeek());
                    TSweeks.add(pts_1);
                    PreTimetableSchedule pts_2 = new PreTimetableSchedule();
                    pts_2.setStartWeek(weeks[i]);
                    pts_2.setEndWeek(weeks[i]);
                    TSweeks.add(pts_2);
                }
            }

        }

        //节次数组和周次数组进行二次循环
        //对数组内连续周和节保存到PreTimetableSchedule，直到循环结束
        for(PreTimetableSchedule sameClass: TSclasses){
            for(PreTimetableSchedule sameWeek: TSweeks){
                PreTimetableSchedule timetableSave = new PreTimetableSchedule();
                timetableSave.setPreTimetableAppointment(preTimetableAppointment);
                timetableSave.setStartWday(weekday);
                timetableSave.setEndWday(weekday);
                timetableSave.setStartClass(sameClass.getStartClass());
                timetableSave.setEndClass(sameClass.getEndClass());
                timetableSave.setStartWeek(sameWeek.getStartWeek());
                timetableSave.setEndWeek(sameWeek.getEndWeek());
                preTimetableScheduleDAO.store(timetableSave);
            }
        }

        return true;
    }

    /****************************************************************************
     * @描述： 获取容量满足上课人数的实验室
     * @作者 ：张德冰
     * @时间： 2018-12-26
     ****************************************************************************/
    @Override
    public List<PreLabRoom> findPreLabRoomsByCapacityRange(PreTimetableAppointment preTimetableAppointment) {
        Integer preRoomTypeId = null;
        if (preTimetableAppointment.getPreRoomType() != null) {
            preRoomTypeId = preTimetableAppointment.getPreRoomType().getId();
        }
        Integer preSoftwareId = null;
        if (preTimetableAppointment.getPreSoftware() != null) {
            preSoftwareId = preTimetableAppointment.getPreSoftware().getId();
        }
        List<PreLabRoom> plr = findPreLabRooms(preTimetableAppointment.getSchoolAcademy().getAcademyNumber(), null, preRoomTypeId, preSoftwareId);
        List<PreLabRoom> preLabRoom = new ArrayList<>();
        if (plr.size() > 0) {
            for (PreLabRoom lr : plr) {
                String str = lr.getPreCapacityRange().getCapaRange();
                //取到min
                Integer min = Integer.parseInt(str.split("～")[0]);
                //取到max
                Integer max = Integer.parseInt(str.split("～")[1]);
                if (min <= preTimetableAppointment.getStuNumber() && max >= preTimetableAppointment.getStuNumber()) {
                    preLabRoom.add(lr);
                }
            }
        }
        return preLabRoom;
    }

    /****************************************************************************
     * @描述： 获取课程信息
     * @作者 ：张德冰
     * @时间： 2018-12-27
     ****************************************************************************/
    @Override
    public List<JsonValueDTO> findSchoolCourseInfo(String search, String acno){
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select c from SchoolCourseInfo c where 1=1" );
        //查询条件
        if(acno != null && !acno.equals("")) {
            sql.append(" and c.academyNumber = '" + acno + "'");
        }
        if (search != null && !"".equals(search)) {
            sql.append(" and (c.courseNumber like '%" + search + "%' or");
            sql.append(" c.courseName like '%" + search + "%')");
        }
        // 执行sb语句
        List<SchoolCourseInfo> schoolCourseInfos = schoolCourseInfoDAO.executeQuery(sql.toString(),0,0);
        //封装VO
        List<JsonValueDTO> schoolCourseInfoDTOs = new ArrayList<JsonValueDTO>();
        for(SchoolCourseInfo s:schoolCourseInfos){
            JsonValueDTO jsonValueDTO = new JsonValueDTO();
            //设置课程信息
            jsonValueDTO.setId(s.getCourseNumber());
            jsonValueDTO.setText(s.getCourseName()+"("+s.getCourseNumber()+")");
            schoolCourseInfoDTOs.add(jsonValueDTO);
        }
        return schoolCourseInfoDTOs;
    }


    /****************************************************************************
     * @描述： 根据查询条件获取预排课记录
     * @作者 ：张德冰
     * @时间： 2018-12-28
     ****************************************************************************/
    @Override
    public List<PreTimetableAppointment> findPreTimetableAppointmentList(PreTimetableAppointment preTimetableAppointment, int[] weeks, int[] classes, Integer weekday){
        //获取列表主查询语句
        StringBuffer sql = new StringBuffer("select distinct p from PreTimetableAppointment p left join p.preTimetableSchedules t where 1 = 1");
        if(preTimetableAppointment != null){
            if(preTimetableAppointment.getSchoolTerm() != null){
                sql.append(" and p.schoolTerm.id ="+preTimetableAppointment.getSchoolTerm().getId());
            }
            if(preTimetableAppointment.getUserByTeacher() != null){
                sql.append(" and p.userByTeacher.username ="+preTimetableAppointment.getUserByTeacher().getUsername());
            }
            /*if(preTimetableAppointment.getUserByTutor() != null){
                sql.append(" and p.userByTutor.username ="+preTimetableAppointment.getUserByTutor().getUsername());
            }*/
        }
        if(weekday != null){
            sql.append(" and (t.startWday ="+weekday+" or t.endWday ="+weekday+")");
            sql.append(" and ("+weeks[0]+" between t.startWeek and t.endWeek");
            for (int i = 1;i<weeks.length;i++){
                sql.append(" or "+weeks[i]+" between t.startWeek and t.endWeek");
            }
            sql.append(") and ("+classes[0]+" between t.startClass and t.endClass");
            for (int i = 1;i<classes.length;i++){
                sql.append(" or "+classes[i]+" between t.startClass and t.endClass");
            }
            sql.append(")");
        }

        return preTimetableAppointmentDAO.executeQuery(sql.toString(), 0, 0);
    }

}