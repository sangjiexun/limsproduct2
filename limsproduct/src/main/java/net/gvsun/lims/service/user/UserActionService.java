package net.gvsun.lims.service.user;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;
import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;

import java.util.List;

public interface UserActionService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public BaseActionAuthDTO getBaseActionAuthDTO(String action, String author, String username);

	/*************************************************************************************
	 * Description:用户权限管理-排课模板-教师模板
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public BaseActionAuthDTO getAuthTimetableTypeByTeacher(String author, String username);

	/*************************************************************************************
	 * Description:用户权限管理-排课模板-教师模板
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public BaseActionAuthDTO getAuthTimetableTypeByAcademy(String author, String username);
}