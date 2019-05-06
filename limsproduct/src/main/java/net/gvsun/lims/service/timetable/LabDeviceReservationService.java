package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.timetable.LabDeviceReservationParamDTO;
import net.zjcclims.domain.LabRoomDevice;

import javax.servlet.http.HttpServletResponse;

public interface LabDeviceReservationService {
    /****************************************************************************
     * Description：保存设备预约并返回是否保存成功
     * author：Hezhaoyi
     * 2019-3-1
     ****************************************************************************/
    public String saveLabDeviceReservation(LabDeviceReservationParamDTO labDeviceReservationParamDTO,HttpServletResponse response)throws Exception;



}
