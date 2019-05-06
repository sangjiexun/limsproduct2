package net.zjcclims.service.instrument;

import net.zjcclims.vo.TimeTableAppointmentForInstrumentVO;

import java.util.List;

public interface TimeTableAppointmentForInstrumentService {
    /****************************************************************************
     * 功能：根据实验室ID获取排课记录
     * 作者：吴奇臻
     * 时间：2019-03-12
     ****************************************************************************/
    List<TimeTableAppointmentForInstrumentVO> getAllAppointmentForInstrumentByLabId(int id);
}
