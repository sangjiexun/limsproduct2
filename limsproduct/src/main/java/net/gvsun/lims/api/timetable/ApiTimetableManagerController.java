package net.gvsun.lims.api.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.MessageDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.dto.timetable.TimetableBatchDTO;
import net.gvsun.lims.dto.timetable.TimetableGroupStudentDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.dto.user.UserDTO;
import net.gvsun.lims.service.timetable.TimetableCommonService;
import net.gvsun.lims.service.timetable.TimetableManagerService;
import net.zjcclims.dao.SchoolCourseDAO;
import net.zjcclims.dao.SchoolCourseDetailDAO;
import net.zjcclims.domain.SchoolCourse;
import net.zjcclims.domain.SchoolCourseStudent;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiTimetableManagerController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/timetable/manage")
public class ApiTimetableManagerController<JsonResult> {
    @Autowired
    private SchoolCourseDAO schoolCourseDAO;
    @Autowired
    private TimetableManagerService timetableManagerService;

    /**************************************************************************
     * Description 排课管理--获取批次数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiTimetableBatchList")
    public BaseDTO apiTimetableBatchList(String courseNo, String sort, String order) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableManagerService.apiTimetableBatchList(courseNo, sort, order);
        return baseVo;
    }

    /**************************************************************************
     * Description 排课管理--获取批次数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSelfTimetableBatchList")
    public BaseDTO apiTimetableBatchList(Integer selfId, String sort, String order) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableManagerService.apiSelfTimetableBatchList(selfId, sort, order);
        return baseVo;
    }

    /**************************************************************************
     * Description 排课管理--获取批次的分组数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiTimetableGroupList")
    public BaseDTO apiTimetableGroupList(int batchId, String sort, String order) {
        //获取查询课程库列表
        BaseDTO baseVo = timetableManagerService.apiTimetableGroupList(batchId, sort, order);
        return baseVo;
    }

    /**************************************************************************
     * Description 排课管理-保存分批
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveTimetableBatch")
    public boolean apiSaveTimetableBatch(@RequestBody TimetableBatchDTO timetableBatchDTO) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.saveTimetableBatch(timetableBatchDTO);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-保存分批
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSelectBatchGroup")
    public boolean apiSelectBatchGroup(int groupId) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.apiSelectBatchGroup(groupId);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除分组
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableGroup")
    public boolean apiDeleteTimetableGroup(int groupId) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.deleteTimetableGroup(groupId);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除分组
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableBatch")
    public boolean apiDeleteTimetableBatch(int batchId) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.deleteTimetableBatch(batchId);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除分组
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiUpdateTimetableBatch")
    public boolean apiUpdateTimetableBatch(int batchId,String batchName,String startDate,String endDate) throws ParseException {
        //获取查询课程库列表
        boolean bool = timetableManagerService.apiUpdateTimetableBatch(batchId,batchName,startDate,endDate);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-修改分组的人数设置
     *
     * @author 魏诚
     * @date 2018年10月10日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/saveTimetableGroupNumbers")
    public JsonValueDTO saveTimetableGroupNumbers(int groupId, int numbers) {
        JsonValueDTO jsonValueDTO = new JsonValueDTO();
        //获取查询课程库列表
        int returnNumbers = timetableManagerService.saveTimetableGroupNumbers(groupId, numbers);
        jsonValueDTO.setId(String.valueOf(groupId));
        jsonValueDTO.setText("学生分组数");
        jsonValueDTO.setStatus(returnNumbers);
        return jsonValueDTO;
    }

    /**************************************************************************
     * Description 排课管理-分批分组关联-根据分组获取选定分组的名单
     *
     * @author 魏诚
     * @date 2018年10月10日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/getTimetableGroupStudents")
    public TimetableGroupStudentDTO getTimetableGroupStudents(String courseNo,int groupId) {
        //目标：获取当前教学班的所有学生选课名单
        //将已加入当前分组的学生名单，进行标记
        //返回列表
        TimetableGroupStudentDTO timetableGroupStudentDTO = timetableManagerService.getTimetableGroupStudents(courseNo, groupId);
        return timetableGroupStudentDTO;
    }

    /**************************************************************************
     * Description 排课管理-分批分组关联-根据分组获取选定分组的名单
     *
     * @author 魏诚
     * @date 2018年10月10日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/getSelfTimetableGroupStudents")
    public TimetableGroupStudentDTO getSelfTimetableGroupStudents(int selfId,int groupId) {
        //目标：获取当前教学班的所有学生选课名单
        //将已加入当前分组的学生名单，进行标记
        //返回列表
        TimetableGroupStudentDTO timetableGroupStudentDTO = timetableManagerService.getSelfTimetableGroupStudents(selfId, groupId);
        return timetableGroupStudentDTO;
    }

    /**************************************************************************
     * Description 排课管理-分批分组关联-根据分组获取选定分组的名单
     *
     * @author 魏诚
     * @date 2018年10月10日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/saveTimetableGroupNumbersReality")
    public int saveTimetableGroupNumbersReality(int groupId,String[] usernames) {
        //目标：获取当前教学班的所有学生选课名单
        //将已加入当前分组的学生名单，进行标记
        //保存选中的学生分组名单
        int returnValue=timetableManagerService.saveTimetableGroupNumbersReality(groupId,usernames);
        return returnValue;
    }

    /**************************************************************************
     * Description 排课管理-删除教务类排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableByCourseNo")
    public boolean deleteTimetable(int term, String courseNo) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.deleteTimetableByCourseNo(term, courseNo);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除教务类排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableBySelfId")
    public boolean deleteTimetable(int term, Integer selfId) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.deleteTimetableBySelfId(term, selfId);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除教务类排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableByTimetableId")
    public boolean apiDeleteTimetableByTimetableId(Integer id) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.deleteTimetableByByTimetableId(id);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除教务类排课
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiDeleteTimetableBySameNumberId")
    public boolean apiDeleteTimetableBySameNumberId(Integer id) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.deleteTimetableByBySameNumberId(id);
        return bool;
    }

    /**************************************************************************
     * Description 排课管理-删除分组
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiSaveTimetableGroup")
    public boolean apiSaveTimetableGroup(int batchId) {
        //获取查询课程库列表
        boolean bool = timetableManagerService.saveTimetableGroup(batchId);
        return bool;
    }
}
