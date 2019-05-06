package net.gvsun.lims.service.timetable;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.gvsun.lims.dto.timetable.TimetableBatchDTO;
import net.gvsun.lims.dto.timetable.TimetableDTO;
import net.gvsun.lims.dto.timetable.TimetableGroupStudentDTO;
import net.gvsun.lims.dto.timetable.TimetableParamVO;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

public interface TimetableManagerService {

	/*************************************************************************************
	 * Description:排课管理-获取批次
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/

	public BaseDTO apiTimetableBatchList(String courseNo, String sort, String order);
	/*************************************************************************************
	 * Description:排课管理-获取批次
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public BaseDTO apiSelfTimetableBatchList(Integer selfId, String sort, String order);

	/*************************************************************************************
	 * Description:排课管理-获取批次
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public BaseDTO apiTimetableGroupList(int batchId, String sort, String order);

	/*************************************************************************************
	 * Description:排课管理-保存分批排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	public boolean saveTimetableBatch(TimetableBatchDTO timetableBatchDTO);

	/*************************************************************************************
	 * Description:排课管理-删除分批排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	public boolean deleteTimetableBatch(int batchId);

	/*************************************************************************************
	 * Description:排课管理-删除分组
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	public boolean deleteTimetableGroup(int groupId);

	/*************************************************************************************
	 * Description:排课管理-保存分组
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	public boolean saveTimetableGroup(int batchId);

	/*************************************************************************************
	 * Description:排课管理-删除教务类排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	public boolean deleteTimetableByCourseNo(int term,String courseNo);

	/*************************************************************************************
	 * Description:排课管理-删除自主类排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	@Transactional
	public boolean deleteTimetableBySelfId(int term, Integer selfId);

	/*************************************************************************************
	 * Description:排课管理-保存分组
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	public String getTimetableByOrder(String CourseNo);

	/*************************************************************************************
	 * Description:排课管理-保存学生选择分组
	 *
	 * @author： 魏誠
	 * @date：2018-09-26
	 *************************************************************************************/
	public boolean apiSelectBatchGroup(int group);

	/*************************************************************************************
	 * Description:排课管理-分批分组管理-修改分组的学生数量
	 *
	 * @author： 魏誠
	 * @date：2018-10-10
	 *************************************************************************************/
	public int saveTimetableGroupNumbers(int groupId,int numbers);

	/*************************************************************************************
	 * Description:排课管理-分批分组管理-根据分组和教学班编号获取选定分组的名单
	 *
	 * @author： 魏誠
	 * @date：2018-10-10
	 *************************************************************************************/
	public TimetableGroupStudentDTO getTimetableGroupStudents(String courseNo, int groupId);

	/*************************************************************************************
	 * Description:排课管理-分批分组维护-保存分组名单
	 *
	 * @author： 魏誠
	 * @date：2018-10-11
	 *************************************************************************************/
	public int saveTimetableGroupNumbersReality(int groupId,String[] usernames);

	/*************************************************************************************
	 * Description:排课管理-分批分组维护-保存分批信息
	 *
	 * @author： 魏誠
	 * @date：2018-10-11
	 *************************************************************************************/
	public boolean apiUpdateTimetableBatch(int batchId,String batchName,String startDate,String endDate) throws ParseException;


	/*************************************************************************************
	 * Description:排课管理-分批分组管理-根据分组获取选定分组的名单
	 *
	 * @author： 魏誠
	 * @date：2018-10-10
	 *************************************************************************************/
	public TimetableGroupStudentDTO getSelfTimetableGroupStudents(int selfId, int groupId);
	/*************************************************************************************
	 * Description:排课管理-删除自主类排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	@Transactional
	public boolean deleteTimetableByByTimetableId(Integer id);

	/*************************************************************************************
	 * Description:排课管理-删除自主类排课
	 *
	 * @author： 魏誠
	 * @date：2018-09-19
	 *************************************************************************************/
	@Transactional
	public boolean deleteTimetableByBySameNumberId(Integer id);

}