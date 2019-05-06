package net.zjcclims.service.system;


import net.zjcclims.domain.Authority;
import net.zjcclims.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface AuthorityService {

	/***********************************************************************************************
	 * 根据用户判断用户可管理的权限
	 * 作者：李小龙
	 ***********************************************************************************************/
	public List<Authority> findZAuthorityByUser(User user);
	/************************************************************
	 * @用户权限列表
	 * @作者：魏诚
	 * @日期：2014-12-25
	 ************************************************************/
	public List<showAcademyAuthority> getUserTotalRecords();

}
