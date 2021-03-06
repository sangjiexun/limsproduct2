package net.gvsun.lims.service.user;

import net.gvsun.lims.dto.common.BaseDTO;
import net.gvsun.lims.dto.common.JsonValueDTO;
import net.zjcclims.domain.User;

import java.util.List;

public interface UserService {

	/*************************************************************************************
	 * Description:教务课程-获取课程库列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public BaseDTO getUserByUsername(String username);

	/*************************************************************************************
	 * Description:用户管理-获取用户查询列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public BaseDTO getUserListBySearch(String userRole, String search);

	/*************************************************************************************
	 * Description:用户管理-获取用户查询列表
	 *
	 * @author： 魏誠
	 * @date：2018-09-06
	 *************************************************************************************/
	public List<JsonValueDTO> getUserListBySelect(String userRole, String search);
	/*************************************************************************************
	 * Description:用户管理-获取指定学院的系主任
	 *
	 * @author： Hezhaoyi
	 * @date：2019-7-7
	 *************************************************************************************/
	public List<User> findDeansByAcademyNumber(String academyNumber);
    /*************************************************************************************
     * Description:用户管理-根据权限名称查询用户
     *
     * @author： Hezhaoyi
     * @date：2019-7-7
     *************************************************************************************/
    public List<User> findUserByAuthorityName(String authorityName);
}