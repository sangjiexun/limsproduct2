package net.zjcclims.service.system;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.*;

import org.springframework.transaction.annotation.Transactional;

public interface SystemService {

	/**
	 * 获取所有的学院数据
	 * @author hly
	 * 2015.07.27
	 */
	public List<SchoolAcademy> getAllSchoolAcademy(Integer currpage, Integer pageSize);
	
	/**
	 * 获取所有的校区数据
	 * @author hly
	 * 2015.07.27
	 */
	public List<SystemCampus> getAllSystemCampus(Integer currpage, Integer pageSize);
	
	/**
	 * 根据查询条件获取所有的用户数据
	 * @author hly
	 * 2015.07.27
	 */
	public List<User> getAllUser(Integer currpage, Integer pageSize, User user);
	
	/**
	 * 根据查询条件获取所有的用户数据--获取相关权限的用户
	 * @author 贺子龙
	 * 2015-11-20
	 */
	public List<User> getUserByAuthority(User user,int authorityId);
	
	/**
	 * 获取所有的楼宇信息
	 * @author hly
	 * 2015.07.27
	 */
	public List<SystemBuild> getAllSystemBuild(Integer currpage, Integer pageSize);
	
	/**
	 * 获取12版学科数据
	 * @author hly
	 * 2015.08.04
	 */
	public List<SystemSubject12> getAllSystemSubject12(Integer currpage, Integer pageSize);
	
	/**
	 * 获取12版专业数据
	 * @author hly
	 * 2015.08.04
	 */
	public List<SchoolMajor> getAllSchoolMajor(Integer currpage, Integer pageSize);
	
	/**
	 * 根据专业编号获取专业信息
	 * @author hly
	 * 2015.08.06
	 */
	public SchoolMajor findSchoolMajorByNumber(String MNumber);

	public List<User> getUsersByAuthorityId(User user, Integer AuthorityId);

	public List<User> getAllJPAUser(Integer currpage, Integer pageSize, User user);
	
	/**************************************
	 * Description:载入一个存在的学期
	 * @author:邵志峰
	 * @date:2017-05-24
	 ****************************************/
	@Transactional
	public Set<SchoolTerm> loadSchoolTerms();
	
	/***********************************
	 * Description:载入一个存在的实训室
	 * @author:邵志峰
	 * @date:2017-05-24
	 ***********************************/
	@Transactional
	public Set<LabRoom> loadLabRooms();
	
	/***********************************
	 * Description:载入一个存在的软件
	 * @author:邵志峰
	 * @date:2017-05-24
	 ***********************************/
	@Transactional
	public Set<Software> loadSoftwares();
	
	/********************************
	 *Description：项目验收上传附件
	 *@author：邵志峰
	 *@date：2015-10-14
	 *********************************/
	public void softwareReserveUpload(HttpServletRequest request,HttpServletResponse response, Integer id,int type,int flaggy);
	
	/****************************************************************************
	 *Description：保存项目立项附件
	 *@param:fileTrueName 文件名
	 *@author：邵志峰
	 *@date：2017-05-26
	 ****************************************************************************/
	public void saveProjectDocument(String fileTrueName, Integer id,int type,int flaggy);
	
	/***********************************
	 * Description:载入全部班级
	 * 
	 * @author:邵志峰
	 * @date:2017-06-27
	 ***********************************/
	@Transactional
	public Set<SchoolClasses> loadSchoolClassess();
	
	/***********************************************************************************************
	 * Description:查找可用的实训室
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public List<LabRoom> getLabRoom();
    
    /***********************************************************************************************
	 * Description:查找所有身份为实训室管理员的用户
	 * 
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public List<User> loadUser();
    /**********************************
	 * 功能：获取节次对应时间
	 * 作者：缪军
	 * 日期：2017-07-18
	 **********************************/
	public Map<String, SystemTime> getAllTimebyjieci();

	/**
	 * @Description 根据学院获取校区
	 * @author 张德冰
	 * @data 2018-11-28
	 */
	public List<SystemCampus> getSystemCampusBySchoolAcademy(String academyNumber);
}
