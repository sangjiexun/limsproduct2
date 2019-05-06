package net.zjcclims.service.timetable;


import net.zjcclims.domain.TimetableAppointment;
import net.zjcclims.domain.TimetableAppointmentCycle;
import net.zjcclims.domain.TimetableGroup;

import java.util.List;


/**********************************************
 * Description: 排课模块{各种形式排课的保存接口}
 * 
 * @author 贺子龙
 * @date 2016-08-31
 ***********************************************/
public interface TimetableAppointmentSaveService {
	/***********************************************************************************************
	 * Description：排课模块{保存教务实验课}
	 * 
	 * @author：贺子龙
	 * @Date：2016-08-31
	 ***********************************************************************************************/
	public TimetableAppointment saveSchoolTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
													int weekday, int[] items, String teachers, String courseNo, Integer isOther);


	/***********************************************************************************************
	 * Description：排课模块{保存分组排课}
	 *
	 * @author：贺子龙
	 * @Date：2016-08-31
	 ***********************************************************************************************/
	public TimetableAppointment saveGroupTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
                                                   int weekday, int[] items, String teachers, String courseNo, int groupId, Integer isAdmin, String teachers2);


	/***********************************************************************************************
	 * Description：排课模块{保存修改的排课}
	 *
	 * @author：郑昕茹
	 * @Date：2016-11-05
	 ***********************************************************************************************/
	public TimetableAppointment saveChangeTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
                                                    int weekday, int[] items, int tableAppId);
	/***********************************************************************************************
	 * Description：排课预存
	 * @author：贺子龙
	 * @Date：2016-08-31
	 ***********************************************************************************************/
	public TimetableAppointmentCycle saveCycleTimetable(int labroom, int item, String teachers, String teachers2, String courseNo);

	/***********************************************************************************************
	 * Description：查找排课预存
	 * @author：戴昊宇
	 * @Date：2018-03-2
	 ***********************************************************************************************/
	public int findCycleTimetable(String courseNo);
	/***********************************************************************************************
	 * Description：通过courseNo查找排课预存
	 * @author：戴昊宇
	 * @Date：2018-03-2
	 ***********************************************************************************************/
	public List<TimetableAppointmentCycle> findCycleTimetableBycourseNo(String courseNo);
	/***********************************************************************************************
	 * Description：通过courseNo查找循环排课分组
	 * @author：戴昊宇
	 * @Date：2018-03-2
	 ***********************************************************************************************/
	public List<TimetableGroup> findTimetableGroupBycourseNo(String courseCode);
	/***********************************************************************************************
	 * Description：循环排课 保存
	 *
	 * @author：戴昊宇
	 * @Date：2018-03-05
	 ***********************************************************************************************/
	public TimetableAppointment saveCycleTimetableAppointment(int term, int[] classes, int labroom, int week,
                                                              int weekday, int item, String teachers, String courseNo, int groupId, String tutor, int flag);
	/***********************************************************************************************
	 * Description：通过courseNo查找分批循环排课分组
	 * @author：戴昊宇
	 * @Date：2018-03-2
	 ***********************************************************************************************/
	public List<TimetableGroup> findTimetableBatchGroupBycourseNo(String courseCode);

}
