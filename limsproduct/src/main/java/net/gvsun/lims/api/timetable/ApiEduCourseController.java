package net.gvsun.lims.api.timetable;

import api.net.gvsunlims.dto.timetable.progressScheduling.TimetableAppointmentDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.service.school.SchoolCourseService;
import net.gvsun.lims.service.school.SchoolCourseStudentService;
import net.gvsun.lims.service.timetable.EduCourseService;
import net.gvsun.lims.vo.timtable.engineer.NewEduCourseVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiEduCourseController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/school")
public class ApiEduCourseController<JsonResult> {
    @Autowired
    private SchoolCourseStudentService schoolCourseStudentService;
    @Autowired
    private EduCourseService eduCourseService;

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveTimetableAppointmentByEduDirectCourse")
    public boolean apiSaveTimetableAppointmentByEduDirectCourse(@RequestBody TimetableParamVO timetableParamVO) {
        //获取查询课程库列表
        boolean bool = eduCourseService.apiSaveTimetableAppointmentByEduDirectCourse(timetableParamVO);
        return bool;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveTimetableAppointmentByEduAdjustCourse")
    public boolean apiSaveTimetableAppointmentByEduAdjustCourse(@RequestBody TimetableParamVO timetableParamVO) {
        //获取查询课程库列表
        boolean bool = eduCourseService.apiSaveTimetableAppointmentByEduAdjustCourse(timetableParamVO);
        return bool;
    }
    /**************************************************************************
     * Description 教务排课-保存二次排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveCourseTimetableAppointment")
    public boolean apiSaveCourseTimetableAppointment(@RequestBody TimetableParamVO timetableParamVO) {
        //获取查询课程库列表
        boolean bool = eduCourseService.apiSaveCourseTimetableAppointment(timetableParamVO);
        return bool;
    }
}
