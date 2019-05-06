package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import net.gvsun.lims.vo.timtable.engineer.NewEduCourseVO;
import net.zjcclims.domain.TimetableGroup;
import org.springframework.transaction.annotation.Transactional;

public interface EduCourseService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean apiSaveTimetableAppointmentByEduDirectCourse(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean apiSaveTimetableAppointmentByEduAdjustCourse(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-保存教务通识排课（调整，二次分批，不分批）
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public boolean apiSaveCourseTimetableAppointment(TimetableParamVO timetableParamVO);

	/*************************************************************************************
	 * Description:教务课程-保存直接排课
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public TimetableGroup apiSaveTimetableGroupStudent(TimetableGroup timetableGroup);

}