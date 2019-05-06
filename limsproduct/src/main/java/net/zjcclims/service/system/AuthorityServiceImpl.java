package net.zjcclims.service.system;


import net.zjcclims.dao.AuthorityDAO;
import net.zjcclims.dao.UserDAO;
import net.zjcclims.domain.Authority;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("AuthorityService")
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private ShareService shareService;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private UserDAO userDAO;



	/***********************************************************************************************
	 * 根据用户判断用户可管理的权限
	 * 作者：李小龙
	 ***********************************************************************************************/
	@Override
	public List<Authority> findZAuthorityByUser(User user) {
		// TODO Auto-generated method stub
		int type=1;
		Set<Authority> auths=user.getAuthorities();
		for (Authority a : auths) {
			if(a.getType()>type){
				type=a.getType();
			}
		}
		String sql="select a from Authority a where a.typeId<="+type;
		return authorityDAO.executeQuery(sql, 0,-1);
	}
	/************************************************************
	 * @用户权限列表
	 * @作者：魏诚
	 * @日期：2014-12-25
	 ************************************************************/
	@Transactional
	public List<showAcademyAuthority> getUserTotalRecords(){
		
		List<showAcademyAuthority> listNumber=new ArrayList<showAcademyAuthority>();
		User user=shareService.getUser();
		List<Authority> listZAuthority=findZAuthorityByUser(user);
		
		for (Authority authority : listZAuthority){
			String hql = "select count(distinct u) from User u join u.authorities a where  a.id="+authority.getId();
			
			showAcademyAuthority newdate=new showAcademyAuthority();
			newdate.setAuthorityNumber(((Long)userDAO.createQuerySingleResult(hql).getSingleResult()).intValue());
			newdate.setAuthorityName(authority.getAuthorityName());
			newdate.setAuthorityCname(authority.getCname());
			newdate.setAuthorityId(authority.getId());
			
			listNumber.add(newdate);
		}
		
		return listNumber;
	}


	
}
