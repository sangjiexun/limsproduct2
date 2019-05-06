package net.zjcclims.service.system;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.luxunsh.util.EmptyUtil;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import flex.messaging.io.ArrayList;

@Service("SystemService")
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Autowired
	private SystemCampusDAO systemCampusDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SystemBuildDAO systemBuildDAO;
	@Autowired
	private SystemSubject12DAO systemSubject12DAO;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private SoftwareDAO softWareDAO;
	@Autowired
	private SoftwareReserveDAO softwareReserveDAO;
	@Autowired
	 ShareService shareService;
	@Autowired private CommonDocumentDAO documentDAO;
	@Autowired
	private SchoolClassesDAO schoolClassesDAO;
	@Autowired
	private SystemTimeDAO systemTimeDAO;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	/**
	 * 获取所有的学院数据
	 * @author hly
	 * 2015.07.27
	 */
	@Override
	public List<SchoolAcademy> getAllSchoolAcademy(Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select a from SchoolAcademy a");
		
		return schoolAcademyDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 获取所有的校区数据
	 * @author hly
	 * 2015.07.27
	 */
	@Override
	public List<SystemCampus> getAllSystemCampus(Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select c from SystemCampus c");
		
		return systemCampusDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 根据查询条件获取所有的用户数据
	 * @author hly
	 * 2015.07.27
	 */
	@Override
	public List<User> getAllUser(Integer currpage, Integer pageSize, User user) {
		StringBuffer hql = new StringBuffer("select u from User u where 1=1 ");
		if(user.getUserRole()!=null && !"".equals(user.getUserRole()))
		{
			hql.append(" and u.userRole='"+user.getUserRole()+"'");  //用户角色
		}
		if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null && !"".equals(user.getSchoolAcademy().getAcademyNumber()))
		{
			hql.append(" and u.schoolAcademy.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"'");//用户所在学院
			
		}
		
		return userDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	
	/**
	 * 根据查询条件获取所有的用户数据--获取相关权限的用户
	 * @author 贺子龙
	 * 2015-11-20
	 */
	public List<User> getUserByAuthority(User user,int authorityId){
		StringBuffer hql = new StringBuffer("SELECT u.username FROM user u JOIN user_authority ua on u.username = " +
				"ua.user_id JOIN authority a on ua.authority_id =  " + authorityId);
		
		if(user.getSchoolAcademy()!=null && 
				!EmptyUtil.isStringEmpty(user.getSchoolAcademy().getAcademyNumber()))
		{
			hql.append(" and u.academy_number = '"+user.getSchoolAcademy().getAcademyNumber()+"'");//用户所在学院
		}
		List<String> userList=entityManager.createNativeQuery(hql.toString()).getResultList();

		//判断当前登陆人是否为指定权限
		List<User> departmentHeaders=new ArrayList();
		for (String username : userList) {
			departmentHeaders.add(userDAO.findUserByPrimaryKey(username));
		}
		return departmentHeaders;
	}

	/**
	 * 获取所有的楼宇信息
	 * @author hly
	 * 2015.07.27
	 */
	@Override
	public List<SystemBuild> getAllSystemBuild(Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select b from SystemBuild b ");
		
		return systemBuildDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 获取12版学科数据
	 * @author hly
	 * 2015.08.04
	 */
	@Override
	public List<SystemSubject12> getAllSystemSubject12(Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select s from SystemSubject12 s");
		
		return systemSubject12DAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 获取12版专业数据
	 * @author hly
	 * 2015.08.04
	 */
	@Override
	public List<SchoolMajor> getAllSchoolMajor(Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select m from SchoolMajor m");
		
		return schoolMajorDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 根据专业编号获取专业信息
	 * @author hly
	 * 2015.08.06
	 */
	@Override
	public SchoolMajor findSchoolMajorByNumber(String MNumber) {
		return schoolMajorDAO.findSchoolMajorByPrimaryKey(MNumber);
	}

	@Override
	public List<User> getUsersByAuthorityId(User user, Integer AuthorityId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select u from User u join u.authorities a where a.id = '"+AuthorityId+"' ");
		if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null && !"".equals(user.getSchoolAcademy().getAcademyNumber()))
		{
			hql.append(" and u.schoolAcademy.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"'");//用户所在学院
			
		}
		List<User> users = userDAO.executeQuery(hql.toString(), 0, -1);
		return users;
	}
	
	@Override
	public List<User> getAllJPAUser(Integer currpage, Integer pageSize, User user) {
		String sql = "select u.username,u.cname from user u where 1=1 ";
		if(user.getUserRole()!=null && !"".equals(user.getUserRole()))
		{
			sql+=" and u.user_role='"+user.getUserRole()+"'";  //用户角色
		}
		if(user.getSchoolAcademy()!=null && user.getSchoolAcademy().getAcademyNumber()!=null && !"".equals(user.getSchoolAcademy().getAcademyNumber()))
		{
			sql+=" and u.academy_number='"+user.getSchoolAcademy().getAcademyNumber()+"'";//用户所在学院
			
		}
		List<Object> objects = entityManager.createNativeQuery(sql).getResultList();
		List<User> users = new ArrayList();
		for (Object object : objects) {
			Object[] objs = (Object[])object;
			User u = new User();
			u.setUsername((String)objs[0]);
			u.setCname((String)objs[1]);
			users.add(u);
		}
		
		return users;
	}
	
	/********************************
	 * Description:载入一个存在的学期
	 * @author:邵志峰
	 * @date:2017-05-24
	 *********************************/
	@Transactional
	public Set<SchoolTerm> loadSchoolTerms() {
		return schoolTermDAO.findAllSchoolTerms();
	}

	/***********************************
	 * Description:载入一个存在的实训室
	 * @author:邵志峰
	 * @date:2017-05-24
	 ***********************************/
	@Transactional
	public Set<LabRoom> loadLabRooms() {
		return labRoomDAO.findAllLabRooms();
	}

	/***********************************
	 * Description:载入一个存在的软件
	 * @author:邵志峰
	 * @date:2017-05-24
	 ***********************************/
	@Transactional
	public Set<Software> loadSoftwares() {
		return softWareDAO.findAllSoftwares();	
	}
	
	/********************************
	 *Description：项目验收上传附件
	 *@author：邵志峰
	 *@date：2015-10-14
	 *********************************/
	
	public void softwareReserveUpload(HttpServletRequest request,HttpServletResponse response, Integer id,int type,int flaggy){
		
		// TODO Auto-generated method stub
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 String sep = System.getProperty("file.separator"); 
		 Map files = multipartRequest.getFileMap(); 
		 Iterator fileNames = multipartRequest.getFileNames();
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"labConstructionAcceptance"+sep+id;
		//存放文件文件夹名称
		for(; fileNames.hasNext();){
		  String filename = (String) fileNames.next(); 
		  CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename); 
		  byte[] bytes = file.getBytes(); 
		  if(bytes.length != 0) {
			  // 说明申请有附件
			  if(!flag) { 
				  File dirPath = new File(fileDir); 
				  if(!dirPath.exists()) { 
					  flag = dirPath.mkdirs();
		              } 
		      } 
			  String fileTrueName = file.getOriginalFilename(); 
			  //System.out.println("文件名称："+fileTrueName);
			  File uploadedFile = new File(fileDir + sep + fileTrueName); 
			  //System.out.println("文件存放路径为："+fileDir + sep + fileTrueName);
			  try {
				FileCopyUtils.copy(bytes,uploadedFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  saveProjectDocument(fileTrueName,id,type,flaggy);
		  } 
		}
	}
	
	/****************************************************************************
	 *Description：保存项目立项附件
	 *@param:fileTrueName 文件名
	 *@author：邵志峰
	 *@date：2017-05-26
	 ****************************************************************************/
	public void saveProjectDocument(String fileTrueName, Integer id,int type,int flaggy) {
		// TODO Auto-generated method stub
		//id对应的设备
		SoftwareReserve softwareReserve=softwareReserveDAO.findSoftwareReserveByPrimaryKey(id);
		CommonDocument doc = new CommonDocument( );
		doc.setDocumentName(fileTrueName);
		String imageUrl="upload/labConstructionAcceptance/"+id+"/"+fileTrueName;
		doc.setDocumentUrl(imageUrl);
		doc.setSoftwareReserve(softwareReserve);
		doc.setType(type);//图片和文档
		doc.setFlag(flaggy);//（ 1 项目建设阶段资料  2项目教学阶段资料  仪器设备资料   4 综合效益资料）
		doc.setCreatedAt(Calendar.getInstance());
		User user=new User();
		user=shareService.getUser();
		doc.setUser(user);//上传人为当前登录人
		documentDAO.store(doc);
	}
	
	/***********************************
	 * Description:载入全部班级
	 * 
	 * @author:邵志峰
	 * @date:2017-06-27
	 ***********************************/
	@Transactional
	public Set<SchoolClasses> loadSchoolClassess() {
		return schoolClassesDAO.findAllSchoolClassess();
	}
	
	/***********************************************************************************************
	 * Description:查找可用的实训室
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public List<LabRoom> getLabRoom(){    	
    	String sqlStr="select l from LabRoom l where l.isUsed= 1 ";
    	
    	sqlStr+=" group by l.id";
    	return labRoomDAO.executeQuery(sqlStr);
    }
    
    /***********************************************************************************************
	 * Description:查找所有身份为实训室管理员的用户
	 * 
	 * @author：邵志峰
	 * @date：2017-5-26
	 ***********************************************************************************************/
    public List<User> loadUser(){    	
    	String sqlStr="select u from User u where u.userRole=1 and u.enabled=1 ";
    	
    	List<User> users= userDAO.executeQuery(sqlStr);//获取所有用户信息
    	List<User> userlist = new ArrayList();
    	for(User user2 : users){
    		for(Authority au : user2.getAuthorities()) {
    			if(au.getId() == 5){
    				userlist.add(user2);
    			}
    		}
    	}
    	return userlist;
    	}
    /**********************************
	 * 功能：获取节次对应时间
	 * 作者：缪军
	 * 日期：2017-07-18
	 **********************************/
	public Map<String, SystemTime> getAllTimebyjieci(){
		Set<SystemTime> systemTimeSet = systemTimeDAO.findAllSystemTimes();
		Map<String, SystemTime> map=new LinkedHashMap<>(0);
		for (SystemTime systemTime : systemTimeSet) {
			map.put(systemTime.getSection().toString(), systemTime);
		}
		return map;
	}

	/**
	 * @Description 根据学院获取校区
	 * @author 张德冰
	 * @data 2018-11-28
	 */
	@Override
	public List<SystemCampus> getSystemCampusBySchoolAcademy(String academyNumber) {
		String sql = "select distinct sc from SystemCampus sc left join sc.systemBuilds sb where 1=1";
		if(academyNumber != null && !academyNumber.equals("")){
			sql += " and sb.schoolAcademy.academyNumber = '"+academyNumber+"'";
		}
		return systemCampusDAO.executeQuery(sql, -1, 0);
	}
}
