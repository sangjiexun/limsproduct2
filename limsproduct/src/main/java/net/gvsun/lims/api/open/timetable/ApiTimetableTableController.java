package net.gvsun.lims.api.open.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableForInstrumentParamDTO;
import net.gvsun.lims.service.timetable.TimetableAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiTimetableTableController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/open/timetable")
public class ApiTimetableTableController<JsonResult> {
    @Autowired
    private TimetableAppointmentService timetableAppointmentService;

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiTimetableTable")
    public BaseDTO apiTimetableTable(@RequestBody TimetableForInstrumentParamDTO param) {
        //获取查询课程库列表
        BaseDTO baseDTO = timetableAppointmentService.getTimetableAppointments(param);
        return baseDTO;
    }

}
