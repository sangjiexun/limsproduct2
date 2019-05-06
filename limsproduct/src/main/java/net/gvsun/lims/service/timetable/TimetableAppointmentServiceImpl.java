package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableDateFormatDTO;
import net.gvsun.lims.dto.timetable.TimetableForInstrumentDTO;
import net.gvsun.lims.dto.timetable.TimetableForInstrumentParamDTO;
import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.dao.SchoolWeekDAO;
import net.zjcclims.dao.SystemTimeDAO;
import net.zjcclims.dao.TimetableLabRelatedDAO;
import net.zjcclims.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("timetableAppointmentService")
public class TimetableAppointmentServiceImpl implements TimetableAppointmentService {
    @Autowired
    private LabRoomDeviceDAO labRoomDeviceDAO;
    @Autowired
    private SchoolWeekDAO schoolWeekDAO;
    @Autowired
    private SystemTimeDAO systemTimeDAO;
    @Autowired
    private TimetableLabRelatedDAO timetableLabRelatedDAO;

    /*************************************************************************************
     *  Description：根据实验室和周次时间列表安排
     *
     * @author： 魏誠
     * @date：2018-11-19
     *************************************************************************************/
    public BaseDTO getTimetableAppointments(TimetableForInstrumentParamDTO param) {
        //根据查询条件的起始时间，获取对应的周次节次；
        TimetableDateFormatDTO startDto = this.getWeekAndClassByDate(param.getStartDate());
        TimetableDateFormatDTO endDto = this.getWeekAndClassByDate(param.getEndDate());
        // 创建根据学期来查询课程；
        StringBuffer hql = new StringBuffer("select distinct c from TimetableLabRelated c left join c.timetableAppointment.timetableAppointmentSameNumbers s left join c.timetableAppointment.timetableTeacherRelateds tt " +
                " left join c.timetableAppointment.schoolCourse.schoolClasseses scs" +
                " where 1=1 ");
        hql.append(" and c.timetableAppointment.enabled is true");
        //周次查询
        hql.append(" and (s.startWeek <=" + startDto.getWeek() + " and s.endWeek >=" + startDto.getWeek() + ")");
        //所属实验室查询
        //首先根据设备获取实验室
        List<LabRoomDevice> devices = labRoomDeviceDAO.executeQuery("select c from LabRoomDevice c where c.schoolDevice.deviceNumber like '" + param.getDeviceNumber() + "'");
        if (devices.size() > 0) {
            hql.append(" and (c.labRoom.id =" + devices.get(0).getLabRoom().getId() + " )");
        }
        List<TimetableLabRelated> timetableLabRelateds = timetableLabRelatedDAO.executeQuery(hql.toString(), 0, -1);
        //获取排课信息
        List<TimetableForInstrumentDTO> imetableForInstrumentDTOs = new ArrayList<TimetableForInstrumentDTO>();
        for (TimetableLabRelated timetableLabRelated : timetableLabRelateds) {
            for (TimetableAppointmentSameNumber timetableAppointmentSameNumber : timetableLabRelated.getTimetableAppointment().getTimetableAppointmentSameNumbers()) {
                for (int startWeek= timetableAppointmentSameNumber.getStartWeek();startWeek<=timetableAppointmentSameNumber.getEndWeek();startWeek++) {
                    TimetableForInstrumentDTO timetableForInstrumentDTO = new TimetableForInstrumentDTO();
                    //周次节次转换日期
                    //日期转换为字符
                    timetableForInstrumentDTO.setDeviceNumber(param.getDeviceNumber());
                    timetableForInstrumentDTO.setDescription("排课及预约");
                    TimetableForInstrumentParamDTO timetableForInstrumentParamDTO = this.getDateByWeekAndClass(timetableAppointmentSameNumber.getStartClass(), timetableAppointmentSameNumber.getEndClass(),startWeek,timetableAppointmentSameNumber.getTimetableAppointment().getWeekday(),timetableAppointmentSameNumber.getTimetableAppointment().getSchoolTerm().getId() );
                    timetableForInstrumentDTO.setStartDate(timetableForInstrumentParamDTO.getStartDate());
                    timetableForInstrumentDTO.setEndDate(timetableForInstrumentParamDTO.getEndDate());
                    imetableForInstrumentDTOs.add(timetableForInstrumentDTO);
                }
            }
        }
        BaseDTO baseVO = new BaseDTO();
        baseVO.setRows(imetableForInstrumentDTOs);
        baseVO.setTotal(imetableForInstrumentDTOs.size());
        return baseVO;
    }

    /*************************************************************************************
     *  Description：根据起始时间结束时间，获取对应的周次节次
     *
     * @author： 魏誠
     * @date：2018-11-19
     *************************************************************************************/
    public TimetableDateFormatDTO getWeekAndClassByDate(String date) {
        //日期转换为字符
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //获取排课信息
        TimetableDateFormatDTO dto = new TimetableDateFormatDTO();
        //周次节次转换日期
        String startDateSql = "select c from SchoolWeek c where c.date='" + date + "'";
        List<SchoolWeek> schoolWeeks = schoolWeekDAO.executeQuery(startDateSql);
        if (schoolWeeks.size() > 0) {
            dto.setWeek(schoolWeeks.get(0).getWeek());
            dto.setWeekday(schoolWeeks.get(0).getWeekday());
        }
        List<SystemTime> systemTimes = systemTimeDAO.executeQuery("select c from SystemTime c order by c.startDate asc");
        dto.setStartClass(systemTimes.get(0).getSection());
        dto.setEndClass(systemTimes.get(systemTimes.size() - 1).getSection());
        return dto;
    }

    /*************************************************************************************
     *  Description：根据周次节次星期,获取对应的起始时间结束时间
     *
     * @author： 魏誠
     * @date：2018-11-19
     *************************************************************************************/
    public TimetableForInstrumentParamDTO getDateByWeekAndClass(int startClass, int endClass, int week,int weekday,int term) {
        TimetableForInstrumentParamDTO timetableForInstrumentParamDTO = new TimetableForInstrumentParamDTO();
        //周次节次转换日期
        String startDateSql = "select c from SchoolWeek c where c.week=" + week + " and c.weekday="+weekday + " and c.schoolTerm.id="+term;
        List<SchoolWeek> schoolWeeks = schoolWeekDAO.executeQuery(startDateSql);
        List<SystemTime> systemTimes = systemTimeDAO.executeQuery("select c from SystemTime c where c.id>= " + startClass + " and c.id<= " + endClass + "  order by c.startDate asc");
        if (schoolWeeks.size() > 0) {
            //日期转换为字符
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
            String startDate = formatter.format(schoolWeeks.get(0).getDate().getTime()) + " " + formatterTime.format(systemTimeDAO.findSystemTimeById(startClass).getStartDate().getTime());
            String endDate = formatter.format(schoolWeeks.get(0).getDate().getTime()) + " " + formatterTime.format(systemTimeDAO.findSystemTimeById(endClass).getEndDate().getTime());

            timetableForInstrumentParamDTO.setStartDate(startDate);
            timetableForInstrumentParamDTO.setEndDate(endDate);
        }
        return timetableForInstrumentParamDTO;
    }
}