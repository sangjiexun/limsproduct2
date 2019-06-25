package net.zjcclims.service.timetable;

import net.luxunsh.util.EmptyUtil;
import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Service("SchoolCourseInfoService")
public class SchoolCourseInfoServiceImpl implements SchoolCourseInfoService {
	@Autowired
	private ShareService shareService;
	@Autowired
	private SchoolCourseInfoDAO schoolCourseInfoDAO;
	@Autowired
	private LabCenterDAO labCenterDAO;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SchoolTermDAO schoolTermDAO;
	
	/*************************************************************************************
	 * @內容：进行获取计数获取课程信息CourseInfo的分页列表信息
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public int getCountCourseInfoByQuery(SchoolCourseInfo schoolCourseInfo) {
		String query = "";
		// 如果查询条件为所有，者id为-1，否则增加查询条件
		if (!EmptyUtil.isStringEmpty(schoolCourseInfo.getCourseNumber())) {
			query = query + " and (c.courseNumber like '%" + schoolCourseInfo.getCourseNumber() + "%'" +
					" or c.courseName like '%"+schoolCourseInfo.getCourseNumber()+"%')";
		}
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select count(*)  from SchoolCourseInfo c where 1=1");
		// 将query添加到sb1后
		sql.append(query);
		return ((Long) schoolCourseInfoDAO.createQuerySingleResult(
				sql.toString()).getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：获取CourseInfo的分页列表信息,flag标记位-1为所有
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourseInfo> getCourseInfoByQuery(SchoolCourseInfo schoolCourseInfo,String acno, int flag,int curr, int size) {
		String academyNumber="";
		// 如果没有获取有效的实验分室列表-根据登录用户的所属学院
		SchoolAcademy academy = shareService.findSchoolAcademyByPrimaryKey(acno);
        if (academy!=null && academy.getAcademyNumber()!=null) {
    		//获取选择的实验中心
        	academyNumber = academy.getAcademyNumber();
        }else{
        	academyNumber = shareService.getUserDetail().getSchoolAcademy().getAcademyNumber();
        }
		String query = "";
		if (schoolCourseInfo!=null&&schoolCourseInfo.getCourseNumber()!=null) {
			query = query + " and c.courseNumber like '%" + schoolCourseInfo.getCourseNumber() + "%'";
		}
		if (schoolCourseInfo!=null&&schoolCourseInfo.getCourseName()!=null) {
			query = query + " and c.courseName like '%" + schoolCourseInfo.getCourseName() + "%'";
		}
		//flag标记位-1为所有
		if(flag!=-1){
			query = query + " and flag=1 ";
		}
		// 创建根据学期来查询课程；
		StringBuffer sql = new StringBuffer(
				"select c from SchoolCourseInfo c where  c.academyNumber like '%"	+ academyNumber + "%'");
		// 将query添加到sb1后
		sql.append(query);
		sql.append("order by length(c.courseNumber)");
	    // 执行sb语句
		List<SchoolCourseInfo> courses = schoolCourseInfoDAO.executeQuery(sql.toString(),(curr-1)*size,size);
		return courses;
	}
	
	/*************************************************************************************
	 * @內容：获取查找自建课程信息记录数
	 * @作者： 魏诚
	 * @日期：2014-08-25
	 *************************************************************************************/
	@Transactional
	public int getSelfSchoolCourseInfoTotalRecords() {
		// 得出用户数量（由于用户的数据量比较多，不能够使用userDAO.findAllUsers()方法查找用户）
	/*	String sql = "select max(REPLACE(courseNumber, concat('self-',c.academyNumber,'-'),''))+0 from SchoolCourseInfo c where 1=1 and flag=1 and c.academyNumber like '"
				+ shareService.getUserDetail()
				.getSchoolAcademy().getAcademyNumber()
		+ "'";*/
		String sql="select count(c) from SchoolCourseInfo c where 1=1";
		sql+=" and c.flag=1";
		sql+=" and c.academyNumber like '"+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()+ "'";
		sql+=" and c.courseNumber like '%self-%' ";
		if(schoolCourseInfoDAO
				.createQuerySingleResult(sql).getSingleResult()==null){
			return 0;
		}else{
			return ((Long) schoolCourseInfoDAO
					.createQuerySingleResult(sql).getSingleResult()).intValue();
		}
	}

	/*************************************************************************************
	 * @內容：获取CourseInfo的分页列表信息,flag标记位-1为所有
	 * @作者： 魏誠
	 * @日期：2014-07-24
	 *************************************************************************************/
	public List<SchoolCourseInfo> getCourseInfoByQuery(SchoolCourseInfo schoolCourseInfo, int flag, int curr, int size) {
		StringBuffer hql = new StringBuffer("select c from SchoolCourseInfo c where 1=1 ");
		if(schoolCourseInfo!=null && !EmptyUtil.isStringEmpty(schoolCourseInfo.getCourseNumber()))
		{
			hql.append(" and (c.courseNumber like '%"+schoolCourseInfo.getCourseNumber()+"%'" +
					" or c.courseName like '%"+ schoolCourseInfo.getCourseNumber() +"%')");
		}
		
		List<SchoolCourseInfo> courses = schoolCourseInfoDAO.executeQuery(hql.toString(), (curr-1)*size, size);
		return courses;
	}
	
	@Override
	public List<Object> findAllSchoolCourseInfo() {
		// TODO Auto-generated method stub
		String sql = "select c.course_number,c.course_name from school_course_info c";
		List<Object> objects = entityManager.createNativeQuery(sql).getResultList();
		return objects;
	}
	
	//获取list集合
	 public List<SchoolCourseInfo>getList(){
		String hql =" select c from SchoolCourseInfo c where 1=1";
		hql+="and c.courseNumber like '%self-%'";
		hql+=" and c.academyNumber like '"+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber()+ "'";
		hql+="order by length(c.courseNumber)";
		List<SchoolCourseInfo>list = schoolCourseInfoDAO.executeQuery(hql);
		return list;
	}

	/*************************************************************************************
	 * @內容：获取SchoolCourseInfo的分页列表信息数量
	 * @作者： 魏好
	 * @日期：2018-01-10
	 *************************************************************************************/
	@Override
	public int findCountSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo){
		StringBuffer sql= new StringBuffer(" select count(c) from SchoolCourseInfo c where 1=1 ");
		if(!EmptyUtil.isStringEmpty(schoolCourseInfo.getCourseNumber())){
			String b =" and (c.courseNumber like '%"+schoolCourseInfo.getCourseNumber()+"%'" +
					" or c.courseName like '%"+ schoolCourseInfo.getCourseNumber() +"%')";
			sql.append(b);
		}
		return ((Long) schoolCourseInfoDAO
				.createQuerySingleResult(sql.toString()).getSingleResult()).intValue();
	}

	/*************************************************************************************
	 * @內容：获取SchoolCourseInfo的分页列表信息
	 * @作者： 魏好
	 * @日期：2018-01-10
	 *************************************************************************************/
	@Override
	public List<SchoolCourseInfo> findListSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo,int curr, int size){
		StringBuffer hql = new StringBuffer();
		hql.append(" select u from SchoolCourseInfo as u where 1=1 ");
		if(schoolCourseInfo!=null && !EmptyUtil.isStringEmpty(schoolCourseInfo.getCourseNumber()))
		{
			hql.append(" and (u.courseNumber like '%"+schoolCourseInfo.getCourseNumber()+"%'" +
					" or u.courseName like '%"+ schoolCourseInfo.getCourseNumber() +"%')");
		}
		return schoolCourseInfoDAO.executeQuery(hql.toString(), (curr-1)*size , size);
	}

	/*************************************************************************************
	 * @內容：保存课程
	 * @作者： 戴昊宇
	 * @日期：2018-03-20
	 *************************************************************************************/
	@Override
	@Transactional
	public SchoolCourseInfo saveSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo,HttpServletRequest request){

		//保存课程性质
		if(!EmptyUtil.isStringEmpty(request.getParameter("schoolMajor1"))){
			String[] schoolMajor = request.getParameterValues("schoolMajor1");
			HashSet<SchoolMajor> cctype = new HashSet<SchoolMajor>();
			for(String cyp:schoolMajor){
				SchoolMajor findCCourseTypeById = schoolMajorDAO.findSchoolMajorByPrimaryKey(cyp);
				cctype.add(findCCourseTypeById);
			}
			schoolCourseInfo.setSchoolMajor(cctype);
		}
		//保存课程学期
		if(!EmptyUtil.isStringEmpty(request.getParameter("schoolTerm"))){
			String[] schoolTerms = request.getParameterValues("schoolTerm");
			HashSet<SchoolTerm> schoolTermHashSet = new HashSet<>();
			for(String st: schoolTerms){
				SchoolTerm schoolTerm = schoolTermDAO.findSchoolTermById(Integer.parseInt(st));
				schoolTermHashSet.add(schoolTerm);
			}
			schoolCourseInfo.setSchoolTerms(schoolTermHashSet);
		}
		//保存教师
		if(!EmptyUtil.isStringEmpty(request.getParameter("teacher"))){
			String[] teachers = request.getParameterValues("teacher");
			HashSet<User> tuser = new HashSet<User>();
			for(String t:teachers){
				User userByUsername = userDAO.findUserByUsername(t);
				tuser.add(userByUsername);
			}
			schoolCourseInfo.setUsers(tuser);
		}
		schoolCourseInfo.setCreatedAt(Calendar.getInstance());
		schoolCourseInfo.setUpdatedAt(Calendar.getInstance());
		schoolCourseInfo.setAcademyNumber(shareService.getUserDetail().getSchoolAcademy().getAcademyNumber());
		SchoolCourseInfo courseInfo = schoolCourseInfoDAO.store(schoolCourseInfo);
		return courseInfo;
	}

	/*************************************************************************************
	 * @內容：获取SchoolCourseInfo的信息
	 * @作者： 戴昊宇
	 * @日期：2018-01-10
	 *************************************************************************************/
	public List<SchoolCourseInfo> findSchoolCourseInfo(int flag){
		String sql=" select u from SchoolCourseInfo as u where 1=1";
		sql+=" and u.courseType="+flag;
		return schoolCourseInfoDAO.executeQuery(sql);
	}

	/**
	 * Description 保存自主课程
	 * @param courseInfo
	 * @author 陈乐为 2019-6-24
	 */
	@Transactional
	public void saveSchoolCourseInfoForSelf(SchoolCourseInfo courseInfo) {
		schoolCourseInfoDAO.store(courseInfo);
		schoolCourseInfoDAO.flush();
	}
}
