package net.zjcclims.service.timetable;

import net.zjcclims.constant.MonthReport;
import net.zjcclims.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

public interface TimetableProgressSchedulingService {

	/***********************************************************************************************
	 * Description：排课模块通用{根据选课组编号获取排课记录}
	 * @author：戴昊宇
	 * @Date：2017-08-25
	 ***********************************************************************************************/
	public List<TimetableAppointment> findTimetableAppointmentByCourseCode(String courseNo);
	/*************************************************************************************
	 * @Description: 教务排课--查看校历
	 * @author： 贺子龙
	 * @date：2017-10-07
	 *************************************************************************************/
	public List<SchoolWeek> findSchoolWeekByCourseCode(String courseCode);
	/***********************************************************************************************
	 * Description：排课模块{保存教务实验课}
	 *
	 * @author：戴昊宇
	 * @Date：2017-09-20
	 ***********************************************************************************************/
	public TimetableAppointment saveSchoolTimetable(int term, int[] classes, int[] labrooms, int[] weekArray,
                                                    int weekday, String teachers, String courseNo, Integer isOther, String teachers2, String content, Integer classType);
}