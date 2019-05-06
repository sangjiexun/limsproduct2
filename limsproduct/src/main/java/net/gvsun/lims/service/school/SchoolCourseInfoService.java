package net.gvsun.lims.service.school;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SchoolCourseInfoService {

	/*************************************************************************************
	 * Description:课程管理-获取课程查询列表
	 *
	 * @author： 魏誠
	 * @date：2018-10-26
	 *************************************************************************************/
	public List<JsonValueDTO> apiSchoolCourseInfoListBySelect(String search);
}