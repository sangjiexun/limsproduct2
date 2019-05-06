package net.gvsun.lims.service.preCourseManagement;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.zjcclims.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PreCourseManagementService {

	/****************************************************************************
	 * @描述： 获取预排课房间记录
	 * @作者 ：张德冰
	 * @时间： 2018-12-19
	 ****************************************************************************/
	public BaseDTO getPreLabRoomListByPage(String search, int offset, int limit, String sort, String sortOrder, HttpServletRequest request);

	/****************************************************************************
	 * @描述： 获取房间布局类型记录
	 * @作者 ：张德冰
	 * @时间： 2018-12-19
	 ****************************************************************************/
	public List<PreRoomType> getPreRoomTypeListByPage(int currpage, int pagesize);

	/****************************************************************************
	 * @描述： 容量范围
	 * @作者 ：张德冰
	 * @时间： 2018-12-20
	 ****************************************************************************/
	public List<PreCapacityRange> getPreCapacityRangeListByPage(int currpage, int pagesize);

	/****************************************************************************
	 * @描述： 所有软件
	 * @作者 ：张德冰
	 * @时间： 2018-12-21
	 ****************************************************************************/
	public List<PreSoftware> getPreSoftwareListByPage(int currpage, int pagesize);

	/****************************************************************************
	 * @描述： 获取预排课记录
	 * @作者 ：张德冰
	 * @时间： 2018-12-24
	 ****************************************************************************/
	public BaseDTO getPreCourseList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status);

	/****************************************************************************
	 * @描述： 保存预排课时间
	 * @作者 ：张德冰
	 * @时间： 2018-12-25
	 ****************************************************************************/
	public boolean savePreTimetableSchedule(Integer preTimetableAppointmentId, int[] weeks, int[] classes, Integer weekday);

	/****************************************************************************
	 * @描述： 获取预房间
	 * @作者 ：张德冰
	 * @时间： 2018-12-26
	 ****************************************************************************/
	public List<PreLabRoom> findPreLabRooms(String acno, Integer preCapacityRangeId, Integer preRoomTypeId, Integer preSoftwareId);

	/****************************************************************************
	 * @描述： 查询预排课记录(分页)
	 * @作者 ：张德冰
	 * @时间： 2018-12-26
	 ****************************************************************************/
	public List<PreTimetableAppointment> findPreTimetableAppointmentList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status);

	/****************************************************************************
	 * @描述： 查询预排课记录记录数
	 * @作者 ：张德冰
	 * @时间： 2018-12-26
	 ****************************************************************************/
	public Integer countFindPreTimetableAppointmentList(int offset, int limit, String search, String sort, String order, HttpServletRequest request, Integer termId, String status);

	/****************************************************************************
	 * @描述： 获取容量满足上课人数的实验室
	 * @作者 ：张德冰
	 * @时间： 2018-12-26
	 ****************************************************************************/
	public List<PreLabRoom> findPreLabRoomsByCapacityRange(PreTimetableAppointment preTimetableAppointment);

	/****************************************************************************
	 * @描述： 获取课程信息
	 * @作者 ：张德冰
	 * @时间： 2018-12-27
	 ****************************************************************************/
	public List<JsonValueDTO> findSchoolCourseInfo(String search, String acno);

	/****************************************************************************
	 * @描述： 根据查询条件获取预排课记录
	 * @作者 ：张德冰
	 * @时间： 2018-12-28
	 ****************************************************************************/
	public List<PreTimetableAppointment> findPreTimetableAppointmentList(PreTimetableAppointment preTimetableAppointment, int[] weeks, int[] classes, Integer weekday);
}