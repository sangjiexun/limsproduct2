package net.gvsun.lims.api.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.dto.timetable.TimetableSelfCourseDTO;
import net.gvsun.lims.service.school.SchoolCourseService;
import net.gvsun.lims.service.school.SchoolCourseStudentService;
import net.gvsun.lims.service.timetable.EduCourseService;
import net.gvsun.lims.service.timetable.TimetableSelfCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

/****************************************************************************
 * Descriptions：自主排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiSelfCourseController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/timetable/self")
public class ApiSelfCourseController<JsonResult> {
    @Autowired
    private SchoolCourseStudentService schoolCourseStudentService;
    @Autowired
    private TimetableSelfCourseService timetableSelfCourseService;
    @Autowired
    private SchoolCourseService schoolCourseService;

    /**************************************************************************
     * Description 自主排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSelfCourseListByPage")
    public BaseDTO apiSelfCourseListByPage(int offset, int limit, int termId, String search, String status, String sort, String order, HttpServletRequest request) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableSelfCourseService.apiSelfCourseListByPage(termId,search, status,offset / limit, sort, order,request);
        return baseVo;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSelfCourseManageByPage")
    public BaseDTO apiSelfCourseManageByPage(int offset, int limit, int termId, String search,String status, String sort, String order, HttpServletRequest request) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableSelfCourseService.apiSelfCourseManageByPage(termId,search, status,offset / limit, sort, order,request);
        return baseVo;
    }

    /**************************************************************************
     * Description 自主排课-保存二次排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveSelfReTimetable")
    public boolean apiSaveSelfReTimetable(@RequestBody TimetableParamVO timetableParamVO) {
        //获取查询课程库列表
        boolean bool = timetableSelfCourseService.apiSaveSelfReTimetable(timetableParamVO);
        return bool;
    }

    /**************************************************************************
     * Description 自主排课-保存自主排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveTimetableSelfCourse")
    public boolean apiSaveTimetableSelfCourse(@RequestBody TimetableSelfCourseDTO timetableSelfCourseDTO) {
        //获取查询课程库列表
        boolean bool = timetableSelfCourseService.apiSaveTimetableSelfCourse(timetableSelfCourseDTO);
        return bool;
    }

    /**************************************************************************
     * Description 自主排课-删除自主排课
     *
     * @author 魏诚
     * @date 2018年10月30日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableSelfCourse")
    public boolean apiDeleteTimetableSelfCourse(Integer selfId) {
        //获取查询课程库列表
        boolean bool = timetableSelfCourseService.apiDeleteTimetableSelfCourse(selfId);
        return bool;
    }

    /**************************************************************************
     * Description 自主排课-查看选课学生列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiTimetableCourseStudentList")
    public BaseDTO apiTimetableCourseStudentList(int termId, int selfId,String sort, String order) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableSelfCourseService.apiTimetableCourseStudentList(termId, selfId,sort,order);
        return baseVo;
    }

    /**************************************************************************
     * Description 排课审核-获取当前审核的课程信息-自主
     * @param termId
     * @param search
     * @return
     * @author 陈乐为 2019-1-16
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiEduSelfCourseForAudit")
    public BaseDTO apiEduSelfCourseForAudit(int termId, String search) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableSelfCourseService.apiEduSelfCourseForAudit(termId,search);
        return baseVo;
    }

}
