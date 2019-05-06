package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.timetable.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

public interface TimetableAppointmentService {

	/*************************************************************************************
	 *  Description：根据实验室和周次时间列表安排
	 *
	 * @author： 魏誠
	 * @date：2018-11-19
	 *************************************************************************************/
	public BaseDTO getTimetableAppointments(TimetableForInstrumentParamDTO param );

	/*************************************************************************************
	 *  Description：根据起始时间结束时间，获取对应的周次节次
	 *
	 * @author： 魏誠
	 * @date：2018-11-19
	 *************************************************************************************/
	public TimetableForInstrumentParamDTO getDateByWeekAndClass(int startClass, int endClass, int week,int weekday,int term);
}