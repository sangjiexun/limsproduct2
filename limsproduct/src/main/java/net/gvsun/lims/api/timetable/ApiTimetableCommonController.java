package net.gvsun.lims.api.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.MessageDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.service.timetable.TimetableCommonService;
import net.zjcclims.dao.SchoolCourseDetailDAO;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/****************************************************************************
 * Descriptions：教务排课管理模块
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ****************************************************************************/
@Controller("apiTimetableCommonController")
@SessionAttributes("selected_academy")
@RequestMapping("/lims/api/timetable/common")
public class ApiTimetableCommonController<JsonResult> {
    @Autowired
    private TimetableCommonService timetableCommonService;
    @Autowired
    private SchoolCourseDetailDAO schoolCourseDetailDAO;

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiViewTimetableInfo")
    public BaseDTO apiViewTimetableInfo(String courseNo) {
        //获取查询课程库列表
        BaseDTO baseDTO = timetableCommonService.apiViewTimetableInfo(courseNo);
        return baseDTO;
    }

    /**************************************************************************
     * Description 教务排课-查看课程库列表-获取数据
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiViewSelfTimetableInfo")
    public BaseDTO apiViewSelfTimetableInfo(Integer selfId) {
        //获取查询课程库列表
        BaseDTO baseDTO = timetableCommonService.apiViewTimetableInfo(selfId);
        return baseDTO;
    }

    /**************************************************************************
     * Description 教务排课-获取调整排课的可用星期
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiClassListBySelect")
    public SelectDTO apiClassListBySelect(String userRole, String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = timetableCommonService.getClassesListBySelect(search);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-获取调整排课的可用星期
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiWeekDayListBySelect")
    public SelectDTO apiWeekDayListBySelect(String search) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = timetableCommonService.getWeekdayListBySelect(search);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-获取调整排课的可用星期
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiWeekDayAdjustTimetableBySelect")
    public SelectDTO apiWeekDayAdjustTimetableBySelect(String search, String courseNo) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = timetableCommonService.apiWeekDayAdjustTimetableBySelect(courseNo);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-获取调整排课的可用星期
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiClassesAdjustTimetableBySelect")
    public SelectDTO apiClassesAdjustTimetableBySelect(String search, String courseDetailNo) {
        //获取查询课程库列表
        List<JsonValueDTO> jsonValueDTOs = timetableCommonService.apiClassesAdjustTimetableBySelect(courseDetailNo);
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(jsonValueDTOs);
        return SelectDTO;
    }

    /**************************************************************************
     * Description 教务排课-获取调整排课的可用星期
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiWeekListBySelect")
    public SelectDTO apiWeekListBySelect(int term, int weekday, Integer search, String classes, String labRoomIds, String courseDetailNo) {
        TimetableParamVO timetableParamVO = new TimetableParamVO();
        timetableParamVO.setWeekday(weekday);
        //转换参数，将逗号分隔字符串，转换为int数组
        String[] strClasses = classes.split(",");
        int[] intClasses = new int[strClasses.length];
        for (int i = 0; i < intClasses.length; i++) {
            if(!"".equals(strClasses[i])&&Objects.nonNull(strClasses[i])){
                intClasses[i] = Integer.parseInt(strClasses[i]);
            }
        }
        String[] strLabrooms = labRoomIds.split(",");
        int[] intLabrooms = new int[strLabrooms.length];
        for (int i = 0; i < intLabrooms.length; i++) {
            if(!"".equals(strLabrooms[i])&&Objects.nonNull(strLabrooms[i])){
                intLabrooms[i] = Integer.parseInt(strLabrooms[i]);
            }
        }
        //如果为调整排课，传递的courseDetailNo同时传递school_course_detail指定的星期
        if (weekday == 0) {
            timetableParamVO.setWeekday(schoolCourseDetailDAO.findSchoolCourseDetailByCourseDetailNo(courseDetailNo).getWeekday());
        } else {
            timetableParamVO.setWeekday(weekday);
        }
        timetableParamVO.setLabRoomIds(intLabrooms);
        timetableParamVO.setTerm(term);
        timetableParamVO.setClasses(intClasses);
        //获取查询课程库列表
        int[] intWeeks = new int[1];
        if(search == null) {
            intWeeks = timetableCommonService.getWeeksListBySelect(timetableParamVO.getTerm());
        }else {
            intWeeks[0] = search;
        }
        //进行判冲
        timetableParamVO.setWeeks(intWeeks);
        List<Integer> validDTOs = timetableCommonService.getTimetableValidWeeksList(timetableParamVO);
        List<JsonValueDTO> valueDTOs = new ArrayList<JsonValueDTO>();
        for (Integer valid : validDTOs) {
            if( ArrayUtils.contains(intWeeks, valid)){
                JsonValueDTO jsonValueDTO = new JsonValueDTO();
                jsonValueDTO.setId(valid.toString());
                jsonValueDTO.setText(valid.toString());
                valueDTOs.add(jsonValueDTO);
            }
        }
        //
        SelectDTO SelectDTO = new SelectDTO();
        SelectDTO.setResults(valueDTOs );
        return SelectDTO;
    }

    /**************************************************************************
     * Description 排课管理-排课发布
     *
     * @author 魏诚
     * @date 2018年8月22日
     **************************************************************************/
    @ResponseBody
    @RequestMapping("/apiTimetablePublic")
    public MessageDTO apiTimetablePublic(@RequestBody TimetableParamVO timetableParamVO) {
        //发布排课
        timetableCommonService.publicTimetable(timetableParamVO);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setType("apiTimetablePublic");
        messageDTO.setText("OK");
        return messageDTO;
    }

    /**
     * Description 获取可用（实验室，星期，节次，周次）列表
     * @param request 请求
     * @return 可用列表
     * @author 黄保钱 2019-1-16
     */
    @ResponseBody
    @RequestMapping("/apiGetUsableList")
    public SelectDTO apiGetUsableList(TimetableParamVO timetableParamVO, HttpServletRequest request){
        // 星期
        String weekday = request.getParameter("weekday");
        // 实验室id字符串
        String labRoomIdstr = request.getParameter("labRoomId");
        // 判冲类型
        Integer type = request.getParameter("type") == null ? -1 : Integer.valueOf(request.getParameter("type"));
        // 学期
        Integer term = request.getParameter("term") == null ? -1 : Integer.valueOf(request.getParameter("term"));
        // 学院
        String academyNumber = request.getParameter("academyNumber");
        // 软件
        String soft = request.getParameter("soft");
        // 输入框筛选条件
        String search = request.getParameter("search");
        // 节次
        String classes = request.getParameter("classes");
        // 周次
        String weeks = request.getParameter("weeks");
        // 获取判冲数据
        SelectDTO selectDTO = timetableCommonService.GetUsableList(labRoomIdstr, weekday, classes, weeks, type, academyNumber, term, soft, search);
        return selectDTO;
    }

}
