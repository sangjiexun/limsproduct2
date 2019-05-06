package net.zjcclims.service.instrument;

import net.zjcclims.vo.TimeTableAppointmentForInstrumentVO;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service("TimeTableAppointmentForInstrumentService")
public class TimeTableAppointmentForInstrumentServicelmpl implements TimeTableAppointmentForInstrumentService {
    @PersistenceContext
    private EntityManager entityManager;
    /****************************************************************************
     * 功能：根据实验室ID获取排课记录
     * 作者：吴奇臻
     * 时间：2019-03-12
     ****************************************************************************/
   public List<TimeTableAppointmentForInstrumentVO> getAllAppointmentForInstrumentByLabId(int id){
        String sql="{call proc_get_timetable_appointment_for_instrument('" + id + "')}";
       Query query =entityManager.createNativeQuery(sql);
       List<Object[]> resultList=query.getResultList();
       List<TimeTableAppointmentForInstrumentVO> timeTableAppointmentForInstrumentVOList=new ArrayList<>();
       for(Object[] o: resultList){
           TimeTableAppointmentForInstrumentVO timeTableAppointmentForInstrumentVO =new TimeTableAppointmentForInstrumentVO();
           timeTableAppointmentForInstrumentVO.setStartWeek(Integer.parseInt(o[0].toString()));
           timeTableAppointmentForInstrumentVO.setEndWeek(Integer.parseInt(o[1].toString()));
           timeTableAppointmentForInstrumentVO.setStartDate(o[2].toString());
           timeTableAppointmentForInstrumentVO.setStartTime(o[3].toString());
           timeTableAppointmentForInstrumentVO.setEndTime(o[4].toString());
           timeTableAppointmentForInstrumentVOList.add(timeTableAppointmentForInstrumentVO);
       }
       return timeTableAppointmentForInstrumentVOList;
    }
}
