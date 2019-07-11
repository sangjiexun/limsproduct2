package net.zjcclims.service.system;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

@Service("UserDetailService")
public class UserDetailServiceImpl implements UserDetailService {
	@Autowired
	private UserDAO userDAO;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO; 
	@Autowired
	private SchoolClassesDAO schoolClassesDAO;
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private SchoolMajorDAO schoolMajorDAO;
    @Autowired
    ShareService shareService;
	@Autowired
	private UserCardDAO userCardDAO;
	/*************************************************************************************
	 * @內容：获取用户的总记录数
	 * @作者： 叶明盾
	 * @日期：2014-08-14
	 *************************************************************************************/
	@Transactional
	public int getUserTotalRecords(String number){
		//得出用户数量（由于用户的数据量比较多，不能够使用userDAO.findAllUsers()方法查找用户）
		String sql="select count(u) from User u where  u.userStatus='1' and u.schoolAcademy.academyNumber like '"+number+"%'";
		return  ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：获取用户的总记录数
	 * @作者： 叶明盾
	 * @日期：2014-08-14
	 *************************************************************************************/
	@Transactional
	public int getUserTotalRecords(User user,String number){
		//得出用户数量（由于用户的数据量比较多，不能够使用userDAO.findAllUsers()方法查找用户）
		String sql="select count(u) from User u where 1=1";
		if(number != null && !number.equals("")) {
			sql += "and u.schoolAcademy.academyNumber = '" + number + "'";
		}
		if(user!=null&&user.getUsername() != null){
			sql+=" and (u.username like '%"+user.getUsername()+"%' or u.cname like '%"+ user.getUsername() +"%') ";
		}
		sql += " and userStatus = 1";
		return  ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：查找所有的用户信息
	 * @作者： 叶明盾
	 * @日期：2014-08-14
	 *************************************************************************************/
	public List<User> findAllUsers(int curr, int size){
		//利用sql语句从用户表中查找出所有的用户，并赋给StringBuffer类型的sb变量
		StringBuffer sb= new StringBuffer("select u from User u where 1=1");
		//给语句添加分页机制
		List<User> users=userDAO.executeQuery(sb.toString(), curr*size, size);
		return users;
	}
	
	/*************************************************************************************
	 * @內容：查找指定学院所有的用户信息
	 * @作者： 何立友
	 * @日期：2014-09-24
	 *************************************************************************************/
	public List<User> findUsersByAcademy(String academyNumber){
		StringBuffer hql = new StringBuffer("select u from User u where u.userStatus='1' ");
		if(academyNumber != null && !"".equals(academyNumber))
		{
			hql.append(" and u.schoolAcademy.academyNumber = '"+academyNumber+"'");
		}
		
		return userDAO.executeQuery(hql.toString(), 0, -1);
	}
	
	/*************************************************************************************
	 * @內容：获取在校用户user的分页列表信息
	 * @作者： 叶明盾
	 * @日期：2014-08-18
	 *************************************************************************************/
	public List<User> findUserByQuery(User user, int curr, int size,String number) {
		String query = "";
		//判断获取的用户的信息是否为空
		if (user.getUsername() != null) {
			query = query + " and u.username like '%" + user.getUsername() + "%'";
		}
		// 查询用户表；
		StringBuffer sql = new StringBuffer("select u from User u where u.userStatus='1' and u.schoolAcademy.academyNumber like '"+number+"%'");
		// 将query添加到sb1后
		sql.append(query);
		sql.append(" order by u.username");
	    // 执行sb语句
		List<User> users = userDAO.executeQuery(sql.toString(),curr*size,size);
		return users;
	}
	
	/*************************************************************************************
	 * @內容：获取查询的用户数
	 * @作者： 何立友
	 * @日期：2014-09-23
	 *************************************************************************************/
	public int getUserTotalRecordsByQuery(String queryStr)
	{
		StringBuffer hql = new StringBuffer("select count(u) from User u where 1=1 ");
		if(queryStr != null && !"".equals(queryStr))
		{
			hql.append(" and u.username like '%"+queryStr+"%'");
		}
		
		return ((Long) userDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	
	/*************************************************************************************
	 * @內容：根据用户的Username查找出相应的用户信息
	 * @作者： 叶明盾
	 * @日期：2014-09-02
	 *************************************************************************************/
	public User findUserByNum(String num) {
		return userDAO.findUserByUsername(num);
	}
	
	/*************************************************************************************
	* @內容：根据学院编号和user对象查询User并分页
	* @作者： 李小龙
	*************************************************************************************/
	@Override
	public List<User> getUserTotalRecords(User user, String number,int currpage, int pageSize) {
		//得出用户数量（由于用户的数据量比较多，不能够使用userDAO.findAllUsers()方法查找用户）
		String sql="select u from User u where 1=1";
		if(number != null && !number.equals("")) {
			sql += " and u.schoolAcademy.academyNumber = '" + number + "'";
		}
		if(user!=null&&user.getUsername() != null){
			sql+=" and (u.username like '%"+user.getUsername()+"%' or u.cname like '%"+ user.getUsername() +"%') ";
		}
		sql += " and u.userStatus = 1 and u.enabled = 1";
		return  userDAO.executeQuery(sql, (currpage-1)*pageSize,pageSize);
	}

	@Override
	public List<User> findAllUsers(String userRole) {
		// TODO Auto-generated method stub
		String sql = "select username,cname from `user` where enabled = '1'";
		if (userRole!=null) {
			sql += " and user_role = '"+userRole+"'";
		}
		List<Object> objects = entityManager.createNativeQuery(sql).getResultList();
		List<User> users = new ArrayList<User>();
		for (Object object : objects) {
			Object[] objs = (Object[])object;
			User user = new User();
			user.setUsername((String)objs[0]);
			user.setCname((String)objs[1]);
			users.add(user);
		}
		return users;
	}
	
	/**
	 * 获取当前学年下的本学院学生
	 * 裴继超
	 * 2016年7月12日17:18:32
	 */
	@Override
	public List<User> findUsersByAcademyAndGrade(String academyNumber, int year) {
		// TODO Auto-generated method stub
		String sql = "select c from User c where c.schoolAcademy.academyNumber like '" + academyNumber + "' and grade like '"+ year + "'";
		List<User> users = userDAO.executeQuery(sql, 0, -1);
		return users;
	}

	
	/**
	 * 获取当前学院下的学年列表
	 * 裴继超
	 * 2016年7月12日17:18:32
	 */
	@Override
	public List<User> findGradesByAcademy(String academyNumber) {
		// TODO Auto-generated method stub
		String sql = "select c from User c where c.schoolAcademy.academyNumber like '"+ academyNumber + "' group by c.grade";
		List<User> grades = userDAO.executeQuery(sql, 0, -1);
		return grades;
	}
	
	/**************************************************************************
	 * Description:系统管理-用户管理-获取用户的总记录数
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	@Transactional
	public int getUserTotalRecords(User user){
		String sql="select count(u) from User u where 1=1 and u.userStatus = 1 ";
		if(user!=null&&user.getUsername() != null){
			sql+=" and u.username like '%"+user.getUsername()+"%'";
		}
		if(user!=null&&user.getCname() != null){
			sql+=" and u.cname like '%"+user.getCname()+"%'";
		}
		if(user!=null&&user.getSchoolAcademy()!= null&&user.getSchoolAcademy().getAcademyNumber()!="-1"){
			sql+=" and u.schoolAcademy.academyNumber like '%"+user.getSchoolAcademy().getAcademyNumber()+"%'";
		}
		return  ((Long) userDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/**************************************************************************
	 * Description:系统管理-用户管理-查找所有的用户信息
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> findUsers(){
		StringBuffer hql = new StringBuffer("select u from User u where u.userStatus='1' ");
		return userDAO.executeQuery(hql.toString(), 0, -1);
	}
	
	/**************************************************************************
	 * Description:系统管理-用户管理-根据user对象查询User并分页
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	public List<User> getUserTotalRecords(User user,int currpage, int pageSize) {
		String sql="select u from User u where 1=1 and u.userStatus = 1";
		if(user!=null&&user.getUsername() != null){
			sql+=" and u.username like '%"+user.getUsername()+"%'";
		}
		if(user!=null&&user.getCname() != null){
			sql+=" and u.cname like '%"+user.getCname()+"%'";
		}
		if(user!=null&&user.getSchoolAcademy()!= null&&!user.getSchoolAcademy().getAcademyNumber().equals("-1")){
			sql+=" and u.schoolAcademy.academyNumber like '%"+user.getSchoolAcademy().getAcademyNumber()+"%'";
		}
		sql+="order by u.createdAt desc";
		return  userDAO.executeQuery(sql, (currpage-1)*pageSize,pageSize);
	}
	
	/**************************************************************************
	 * Description:系统管理-用户管理-新建User对象
	 * 
	 * @author：于侃 
	 * @date ：2016-08-24
	 **************************************************************************/
	public void saveUser(User user){
		userDAO.store(user);
		userDAO.flush();
	}
	
	
	/**************************************************************************************
     * description：导入用户列表
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public void importUser(String File){
		Boolean isE2007=false;
		if(File.endsWith("xlsx")){
			isE2007=true;
		}
		//建立输入流
		try {
			//建立输入流
			InputStream input = new FileInputStream(File);
			Workbook wb =null;
			if(isE2007){
				wb=new XSSFWorkbook(input);
			}else{
				wb=new HSSFWorkbook(input);
			}
			//获取第一个表单数据
			Sheet sheet= wb.getSheetAt(0);
			//获取第一个表单迭代器
			Iterator<Row>rows=sheet.rowIterator();
			Row rowContent=null;// 表头
			String username ="";//学号
			String name="";//姓名
			String sex="";//性别
			String role ="";//角色
			String academy="";//学院
			String major="";//专业
			String classes ="";//班级
			String majorDir="";//专业方向
			String mail="";//邮箱
			String qq ="";//qq
			String phone="";//电话
			int a=0;
			while(rows.hasNext()){
				username ="";//学号
				name="";//姓名
				sex="";//性别
				role ="";//角色
				academy="";//学院
				major="";//专业
				classes ="";//班级
				majorDir="";//专业方向
				mail="";//邮箱
				qq ="";//qq
				phone="";//电话
				if(a==0){
					rowContent=rows.next();
					a=1;
				}
				Row row =rows.next();
				int column=sheet.getRow(0).getPhysicalNumberOfCells();
				//chName ="";//品名
				for(int k=0;k<column;k++){
					if(row.getCell(k)!=null){
						row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
						String columnName = rowContent.getCell(k).getStringCellValue();
						String content = row.getCell(k).getStringCellValue();
						if(columnName.equals("学号")){
							username = content;
						}
						if(columnName.equals("姓名")){
							name = content;
						}
						if(columnName.equals("性别")){
							sex = content;
						}
						if (columnName.equals("角色")) {
							role = content;
						}
						if (columnName.equals("学院")) {
							academy = content;
						}
						if (columnName.equals("专业")) {
							major = content;
						}
						if(columnName.equals("班级")){
							classes = content;
						}
						if(columnName.equals("专业方向")){
							majorDir = content;
						}
						if(columnName.equals("邮箱")){
							mail = content;
						}
						if (columnName.equals("qq")) {
							qq = content;
						}
						if (columnName.equals("电话")) {
							phone = content;
						}
					}
				}
				if(!username.equals("")){
					User user = userDAO.findUserByPrimaryKey(username);
					//不存在该用户时才导入
					if(user == null){
						User u = new User();
						u.setUsername(username);
						u.setPassword(username);
						if(!name.equals("")){
							u.setCname(name);
						}
						if(!sex.equals("") && sex.equals("男")){
							u.setUserSexy("1");
						}
						if(!sex.equals("") && sex.equals("女")){
							u.setUserSexy("2");
						}
						if(!role.equals("") && role.equals("教师")){
							u.setUserRole("1");
						}
						if(!role.equals("") && role.equals("学生")){
							u.setUserRole("0");
						}
						if(!academy.equals("")){
							u.setSchoolAcademy(this.findSchoolAcademyByAcademyName(academy));
						}
						if(!major.equals("")){
							u.setMajorNumber(major);
						}
						if(!classes.equals("")){
							u.setSchoolClasses(this.findSchoolClassesByClassesName(classes));
						}
						if(!majorDir.equals("")){
//							u.setMajorDirection(major);
						}
						if(!mail.equals("")){
							u.setEmail(mail);
						}
						if(!qq.equals("")){
							u.setQq(phone);
						}
						if(!phone.equals("")){
							u.setTelephone(phone);
						}
						u.setUserStatus("1");
						u.setEnabled(true);
						u = userDAO.store(u);
						if(u.getUserRole() != null && u.getUserRole().equals("0")){
							Authority authority = authorityDAO.findAuthorityById(1);
							Set<Authority> authorities = new HashSet<Authority>();
							authorities.add(authority);
							u.setAuthorities(authorities);
							userDAO.store(u);
						}
						if(u.getUserRole() != null && u.getUserRole().equals("1")){
							Authority authority = authorityDAO.findAuthorityById(2);
							Set<Authority> authorities = new HashSet<Authority>();
							authorities.add(authority);
							u.setAuthorities(authorities);
							userDAO.store(u);
						}
						// 初始卡号88888888
						UserCard userCard = new UserCard();
						userCard.setCardNo("88888888");
						userCard.setEnabled("1");
						userCard.setUser(u);
						userCardDAO.store(userCard);
						userCardDAO.flush();
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	
	}
	
	
	/**************************************************************************************
     * description：根据学院名称找到学院
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public SchoolAcademy findSchoolAcademyByAcademyName(String academyName){
		String sql = " select s from SchoolAcademy s where 1=1 and academyName = '"+academyName+"'";
		List<SchoolAcademy> academys = schoolAcademyDAO.executeQuery(sql, 0, -1);
		if(academys != null && academys.size() != 0){
			return academys.get(0);
		}
		return null;
	}
	
	/**************************************************************************************
     * description：根据班级名称找到班级
     * @author：郑昕茹
     * @date：2016-12-19
     **************************************************************************************/
	public SchoolClasses findSchoolClassesByClassesName(String className){
		String sql = " select s from SchoolClasses s where 1=1 and className = '"+className+"'";
		List<SchoolClasses> academys = schoolClassesDAO.executeQuery(sql, 0, -1);
		if(academys != null && academys.size() != 0){
			return academys.get(0);
		}
		return null;
	}
	
	/***********************************************************************
	 * 功能：导入文件前的日期格式、数字格式检查
     * 作者：郑昕茹
     * 日期：2016-12-19
	 ************************************************************************/
	public String checkRegex(String filePath) throws ParseException{
		String checkResult = "";
		if (filePath!=null && !filePath.equals("")) {// 判空
			if(filePath.endsWith("xls")||filePath.endsWith("xlsx")){// 文件格式
				//判断是不是2007
		        boolean isE2007=false;
		        if(filePath.endsWith("xlsx")){
		            isE2007=true;
		        }
		        try {
		        	//建立输入流
		            InputStream input = new FileInputStream(filePath);
		            Workbook wb =null;
		            if(isE2007){
		                wb=new XSSFWorkbook(input);
		            }else{
		                wb=new HSSFWorkbook(input);
		            }
		            //获取第一个表单数据
		            Sheet sheet= wb.getSheetAt(0);
		            //获取第一个表单迭代器
		            Iterator<Row>rows=sheet.rowIterator();
		            Row rowContent=null;
		            String username ="";//工号
					String birth ="";//出生年月
		            int a=0;
		            boolean isBreak = false;
		            while(rows.hasNext() && !isBreak){
		            	username ="";//品名
		            	birth="";//是否需要库存提醒
			            if(a==0){
			                rowContent=rows.next();
			                a=1;
			            }
			            Row row =rows.next();
			            int column=sheet.getRow(0).getPhysicalNumberOfCells();
			            for(int k=0;k<column;k++){
			            	if(row.getCell(k)==null){
			            		String columnName = rowContent.getCell(k).getStringCellValue();
			            		if(columnName.equals("工号(必填)"))
			                    {
			                        checkResult = "nullError";//品名为空错误
			                        isBreak = true;// 终止外层循环
			                        break;
			                    }
			                    else checkResult = "success";
			            	}
			                
			            }
		            }
		            
		        } catch (FileNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			}else {
				checkResult = "fileError";// 文件格式错误
			}
		}
		return checkResult;
	}
	/**
	 * 功能：列出班级表的记录数
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@Override
	public int findSchoolClasses(SchoolClasses schoolClasses) {
		StringBuffer sb = new StringBuffer(" select count(s) from SchoolClasses s where 1 = 1 ");
		if (schoolClasses != null) {
			if (schoolClasses.getAcademyNumber()!=null&&schoolClasses.getAcademyNumber() != "") {
				sb.append(" and s.academyNumber = " + schoolClasses.getAcademyNumber());
			}
			if (schoolClasses.getMajorNumber()!=null&&schoolClasses.getMajorNumber() != "") {
				sb.append(" and s.majorNumber = " + schoolClasses.getMajorNumber());
			}
			if (schoolClasses.getClassGrade() != null && schoolClasses.getClassGrade() != "") {
				sb.append(" and s.classGrade = " + schoolClasses.getClassGrade());
			}
		}
		return ((Long)schoolClassesDAO.createQuerySingleResult(sb.toString()).getSingleResult()).intValue();
	}
	/**
	 * 功能：列出班级表的记录数
	 * 作者：廖文辉
	 * 日期：2018/8/30
	 */
	@Override
	public List<Object[]> findSchoolClasses(SchoolClasses schoolClasses, int currpage, int pageSize) {
		StringBuffer sb = new StringBuffer(" select s from SchoolClasses s where 1 = 1 ");
		if(schoolClasses!=null){
			if(schoolClasses.getAcademyNumber()!=null&&schoolClasses.getAcademyNumber() !=""){
				sb.append(" and s.academyNumber = "+schoolClasses.getAcademyNumber());
			}
			if(schoolClasses.getMajorNumber()!=null&&schoolClasses.getMajorNumber()!=""){
				sb.append(" and s.majorNumber = "+schoolClasses.getMajorNumber());
			}
			if(schoolClasses.getClassGrade()!=null&&schoolClasses.getClassGrade()!=""){
				sb.append(" and s.classGrade = "+schoolClasses.getClassGrade());
			}
		}
		List<Object[]> list =new ArrayList<Object[]>();
 		List <SchoolClasses> sc=schoolClassesDAO.executeQuery(sb.toString(),(currpage-1)*pageSize, pageSize);
		for (SchoolClasses s : sc){
			// 更新班级学生数
			String number = this.classesNumber(s.getClassNumber())+"";
			s.setClassStudentsNumber(number);
			s = schoolClassesDAO.store(s);

		    Object[]o=new Object[10];
		    o[0]=s.getClassNumber();
		    o[1]=s.getClassName();
		    o[2]=s.getClassGrade();
		    SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByMajorNumber(s.getMajorNumber());
		    if(schoolMajor != null && schoolMajor.getMajorName() != null && !schoolMajor.getMajorName().equals("")) {
				o[3] = schoolMajor.getMajorName();
			}
		    SchoolAcademy schoolAcademy=schoolAcademyDAO.findSchoolAcademyByAcademyNumber(s.getAcademyNumber());
		    if(schoolAcademy != null && schoolAcademy.getAcademyName() != null && !schoolAcademy.getAcademyName().equals("")) {
				o[4] = schoolAcademy.getAcademyName();
			}
		    o[5]=s.getClassStudentsNumber();
		    list.add(o);
		}
		return list;
	}
	/**
	 * 功能：根据班级号统计班级人数
	 * 作者：姜新剑
	 * 日期：2016.1.15
	 */
	/* (non-Javadoc)
	 * @see net.dhulims.service.system.ShareDataService#classesNumber(java.lang.String)
	 */
	@Override
	public int classesNumber(String classNumber) {
		String hql=" select count(*) from User u where 1 = 1 and u.schoolClasses.classNumber = '"+classNumber+"'";
		return ((Long)userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}
	/**
	 * 功能：添加学院下拉选择框
	 * 作者：姜新剑
	 * 日期：2016.1.12
	 */
	@Override
	public Map<String, String> getAllSchoolAcademy() {
		Map<String,String> major=new HashMap<String, String>();
		StringBuffer sb=new StringBuffer(" select s from SchoolAcademy s where 1 = 1 ");
		List<SchoolAcademy> schoolAcademy=schoolAcademyDAO.executeQuery(sb.toString(),0,-1);
		for (SchoolAcademy schoolAcademy2 : schoolAcademy) {
			major.put(schoolAcademy2.getAcademyNumber(),schoolAcademy2.getAcademyName());
		}
		return major;
	}
	@Override
	public Map<String, String> getAllSchoolMajor() {
		Map<String,String> major=new HashMap<String, String>();
		StringBuffer sb=new StringBuffer(" select s from SchoolMajor s where 1 = 1 ");
		List<SchoolMajor> schoolMajor=schoolMajorDAO.executeQuery(sb.toString(),0,-1);
		for (SchoolMajor schoolMajor2: schoolMajor) {
			major.put(schoolMajor2.getMajorNumber(),schoolMajor2.getMajorName());
		}
		return major;
	}
    /**
     * 功能：通过班级编号找学生
     * 作者：廖文辉
     * 日期：2018/8/29
     */
    @Override
    public List<Object[]> findUserByClassNumber(String classNumber) {
        String hql=" select u from User u where u.schoolClasses.classNumber like '"+classNumber+"'";
        List<Object[]> list =new ArrayList<Object[]>();
        List <User> users =userDAO.executeQuery(hql,0,-1);
        for (User  u:users){
            Object[]o=new Object[100];
            o[0]=u.getUsername();
            o[1]=u.getCname();
            o[2]=u.getSchoolAcademy().getAcademyName();
            SchoolMajor schoolMajor=schoolMajorDAO.findSchoolMajorByMajorNumber(u.getMajorNumber());
            if(schoolMajor!=null) {
				o[3] = schoolMajor.getMajorName();
			}else{
            	o[3] =" ";
			}
            o[4]=u.getSchoolClasses().getClassNumber();
            list.add(o);
        }
        return list;
    }
    /**
     * 功能：根据当前班级号查找学院号
     * 作者：廖文辉
     * 日期：2018/8/30
     */
    @Override
    public String findUserAcadenybyClassNumber(String classNumber) {
        String hql=" select u from User u where u.schoolClasses.classNumber like '"+classNumber+"'";
        String academy="";
        List<User> user=userDAO.executeQuery(hql,0,-1);
        for (User use : user) {
            academy=use.getSchoolAcademy().getAcademyNumber();
        }
        return academy;
    }
    /**
     * 功能：查找班级号不是当前班级号的学生
     * 作者：廖文辉
     * 日期：2018.8.30
     */
    @Override
    public List<Object[]> findUserByOtherClassNumber(String classNumber,String academy,Integer currpage, int pageSize) {
        String sql=" SELECT u.username, u.cname, sa.academy_name, u.major_number, sc.class_name from user u JOIN school_classes sc on sc.class_number=u.classes_number"+
                " JOIN school_major s ON s.major_number=u.major_number"+ " JOIN school_academy sa ON sa.academy_number=u.academy_number"+" WHERE u.academy_number = '"+academy+"'"+
				" and ( u.classes_number <> '"+classNumber+"' or u.classes_number= null ) and u.enabled = 1";
		Query query = entityManager.createNativeQuery(sql);
		if(pageSize!=-1) {
			query.setMaxResults(pageSize);
			query.setFirstResult((currpage - 1) * pageSize);
		}
		try {
			List<Object[]> list =new ArrayList<Object[]>(query.getResultList());
			return list;
		}catch (Exception e){
			e.printStackTrace();
		}
		List<Object[]> list=new ArrayList<>();
        return list;
    }
    /*************************************************************************************
     * @内容：导入班级
     * @作者：裴继超
     * @日期：2016年3月3日
     **************************************************************************************/
    @Override
    public String importClasses(String File) {
        String result = "";
        //判断是不是2007
        boolean isE2007= false;
        if(File.endsWith("xlsx")){
            isE2007=true;
        }
        try {
            //建立输入流
            InputStream input=new FileInputStream(File);
            //根据文件格式（2003或2007）来初始化
            Workbook wb=null;
            if(isE2007){
                wb=new XSSFWorkbook(input);
            }else{
                wb=new HSSFWorkbook(input);
            }
            //获取第一个表单数据
            Sheet sheet=wb.getSheetAt(0);
            int a=0;
            //获取第一个表单迭代器
            Iterator<Row> rows=sheet.rowIterator();
            int i = 0;
            while(rows.hasNext()){

                if(i==0){
                    rows.next();
                }
                i++;
                Row row=rows.next();//获取行数据
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String number = row.getCell(0).getStringCellValue();//班级编号
                String name= row.getCell(1).getStringCellValue();//班级名称
                String grade = row.getCell(2).getStringCellValue();// 入学年份
                String majornumber = row.getCell(3).getStringCellValue();// 所属专业
                String academynumber = row.getCell(4).getStringCellValue();// 所属学院
                //判冲
                a++;
                if(schoolClassesDAO.findSchoolClassesByClassNumber(number)==null){
                    SchoolClasses schoolClasses = new SchoolClasses();
                    schoolClasses.setClassNumber(number);
                    schoolClasses.setClassName(name);
                    schoolClasses.setClassGrade(grade);
                    schoolClasses.setId(null);
                    //导入外键 学院
                    for(SchoolAcademy schoolAcademy : schoolAcademyDAO.findSchoolAcademyByAcademyName(academynumber)){
                        schoolClasses.setAcademyNumber(schoolAcademy.getAcademyNumber());
                    }
                    //导入外键 专业
                    for(SchoolMajor schoolMajor : schoolMajorDAO.findSchoolMajorByMajorName(majornumber)){
                        schoolClasses.setMajorNumber(schoolMajor.getMajorNumber());
                    }

                    schoolClassesDAO.merge(schoolClasses);
                    if(schoolClassesDAO.findSchoolClassesByClassNumber(number).getClassNumber()==null){
                        result +=  "第"+a+"行的"+name+"数据没有导入进去"+";";
                    }
                }
                else
                    result+="第"+a+"行的数据重复"+";";
            }




        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	/*if (!"".equals(result)) {
		result = result.substring(0, result.length()-1);
	}*/
        return result;

    }
    @Override
    public User findUserByUsername(String username) {
        // TODO Auto-generated method stub
        return userDAO.findUserByPrimaryKey(username);
    }
    /**
     * 保存用户
     * 裴继超
     * 2016年1月25日10:42:24
     */
    public void storeUser(User user) {
        userDAO.store(user);
    }
	/**
	 * 根据工号找卡号
	 * 廖文辉
	 * 2018年9月3日
	 */
	public List<UserCard> findCardNoByUserName(String username){
		String sql="select u from UserCard u where u.enabled=1";
		sql+=" and u.user.username='"+username+"'";
		List<UserCard> userCards=userCardDAO.executeQuery(sql,0,-1);
		return userCards;
	}
}