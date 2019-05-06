package net.gvsun.lims.service.school;

import net.gvsun.lims.dto.common.BaseDTO;

public interface SchoolCourseStudentService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-08-03
	 *************************************************************************************/
	public BaseDTO apiSchoolCourseStudentList(int termId, String courseNo, String sort, String order);

}