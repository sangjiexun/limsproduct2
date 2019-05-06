package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.common.SelectDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableMergeDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TimetableCommonService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-03
	 *************************************************************************************/
	public BaseDTO apiViewTimetableInfo(String courseNo);

	/*************************************************************************************
	 * Description:排课管理-获取节次列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> getClassesListBySelect(String search);

	/*************************************************************************************
	 * Description:排课管理-获取星期列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> getWeekdayListBySelect(String search);

	/*************************************************************************************
	 * Description:排课管理-获取星期列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> apiWeekDayAdjustTimetableBySelect(String courseNo);

	/*************************************************************************************
	 * Description:排课管理-获取星期列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> apiClassesAdjustTimetableBySelect(String courseDetailNo);

	/*************************************************************************************
	 * Description:排课管理-获取节次列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public int[] getWeeksListBySelect(int term);

	/*************************************************************************************
	 * Description:教务课程-保存调整排课
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean saveTimetableAppointmentSameNumber(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-保存调整排课
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean saveTimetableLabRooms(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-保存调整排课
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean saveTimetableTeachers(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-保存调整排课
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	@Transactional
	public boolean saveTimetableTutors(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:排课管理-发布排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public boolean publicTimetable(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-保存实验项目
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean saveTimetableItems(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-获取课程排课信息列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public List<TimetableDTO> apiTimetableDTOs(String courseNo);

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public BaseDTO apiViewTimetableInfo(Integer selfId);

	/*************************************************************************************
	 * Description:排课管理-输入判冲周次，返回可用的周次
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<Integer> getTimetableValidWeeksList(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-获取课程排课信息列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public List<TimetableMergeDTO> apiGetMergeTimetableClassAndWeek(TimetableMergeDTO timetableMergeDTO);

	/*************************************************************************************
	 * Description:教务课程-获取课程排课信息列表(自主排课)
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public List<TimetableDTO> apiTimetableDTOs(Integer selfId);

	/*************************************************************************************
	 * Description:排课管理-输入判冲实验室，返回可用的周次
	 *
	 * @author： 魏誠
	 * @date：2018-10-31
	 *************************************************************************************/
	public List<Integer> getTimetableValidLabRoomsList(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-获取课程排课信息列表(自主排课)
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public List<TimetableDTO> apiTimetableDTOsByGroup(Integer groupId);

	/*************************************************************************************
	 * Description:排课管理-输入判冲实验室，返回可用的周次
	 *
	 * @author： 魏誠
	 * @date：2018-10-31
	 *************************************************************************************/
	public List<Integer> getTimetableInValidLabRoomsList(TimetableParamVO timetableParamVO);

	/**
	 * Description 更新排课状态值
	 * @param param 审核服务的返回值（""/fail/pass）
	 * @param course_no 课程编号
	 * @return
	 * @author 陈乐为 2019-1-9
	 */
	public String updateTimetableStatus(String param, String course_no);

	/**
	 * Description 获取可用（实验室，星期，节次，周次）列表
	 * @param labRoomId 实验室id
	 * @param weekday 星期
	 * @param classes 节次
	 * @param weeks 周次
	 * @return 可用列表
	 * @author 黄保钱 2019-1-16
	 */
	SelectDTO GetUsableList(String labRoomId, String weekday, String classes, String weeks, Integer type, String academyNumber, Integer term, String soft,String search);
}