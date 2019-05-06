package net.zjcclims.service.chose;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("ChoseProfessorService")
public class ChoseProfessorServiceImpl implements ChoseProfessorService {
	//读取属性文件中specialAcademy对应的值（此方法需要在web-content.xml中增加配置）
	@Value("${showDevice}")
	private String showDeviceURL;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
    private UserDAO userDAO;
	@Autowired
    private ChoseProfessorDAO choseProfessorDAO;
	@Autowired
    private ChoseProfessorBatchDAO choseProfessorBatchDAO;
	@Autowired
    private ChoseProfessorRecordDAO choseProfessorRecordDAO;
	@Autowired
    private ShareService shareService;
	@Autowired
    private ChoseThemeDAO choseThemeDAO;
	@Autowired
    private ChoseDessitationForYearDAO choseDessitationForYearDAO;
	@Autowired
    private CommonDocumentDAO commonDocumentDAO;
	@Autowired
    private ChoseAttentionDAO choseAttentionDAO;
	@Autowired
    private ChoseAttentionRecordDAO choseAttentionRecordDAO;
	/*
	 * Instantiates a new ShareServiceImpl.
	 */
	public ChoseProfessorServiceImpl() {
	}
	
	/*
	 * 找到所有的导师互选--管理员功能
	 * 作者：孙虎
	 * 时间：2017-12-1
	 */
	@Override
	public List<ChoseTheme> findChoseThemes(ChoseTheme choseTheme, int currpage, int pageSize) {
		StringBuilder hql = new StringBuilder("select c from ChoseTheme c where 1=1 and c.type=1");
		if(choseTheme!=null){
			if(choseTheme.getTheYear()!=null&&!"".equals(choseTheme.getTheYear())){
				hql.append(" and c.theYear="+choseTheme.getTheYear());
			}
		}
		return choseThemeDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/*
	 * 找到所有的导师互选数量--管理员功能
	 * 作者：孙虎
	 * 时间：2017-12-1
	 */
	@Override
	public int findChoseThemesCount(ChoseTheme choseTheme) {
		StringBuilder hql = new StringBuilder("select count(v) from ChoseTheme v where 1=1 and v.type=1");
		if(choseTheme!=null){
			if(choseTheme.getTheYear()!=null&&!"".equals(choseTheme.getTheYear())){
				hql.append(" and v.theYear ="+choseTheme.getTheYear());
			}
		}
		return ((Long) choseThemeDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/*
	 * 找到所有的导师结果--管理员功能
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public List<ChoseProfessor> findChoseResultList(ChoseTheme choseTheme, int currpage, int pageSize, int themeId){
		StringBuilder hql = new StringBuilder("select c from ChoseProfessor c where 1=1 and c.choseTheme.id="+themeId);
		return choseProfessorDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/*
	 * 找到所有的导师结果数量--管理员功能
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public int findChoseResultCount(ChoseTheme choseTheme, int themeId){
		StringBuilder hql = new StringBuilder("select count(c) from ChoseProfessor c where 1=1 and c.choseTheme.id="+themeId);
		return ((Long) choseProfessorDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/*
	 * 找到所有的学生申请 根据themId和导师
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public List<ChoseProfessorRecord> findChoseStudents(ChoseTheme choseTheme, int currpage, int pageSize, int themeId){
		User user = shareService.getUserDetail();
		StringBuilder hql = new StringBuilder("select c from ChoseProfessorRecord c where 1=1 and c.choseProfessorBatch.choseTheme.id="+themeId+" and c.choseProfessor.user.username='"+user.getUsername()+"'");
		return choseProfessorRecordDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/*
	 * 找到所有的导师结果数量--管理员功能
	 * 作者：孙虎
	 * 时间：2017-12-4
	 */
	public int findChoseStudentsCount(ChoseTheme choseTheme, int themeId){
		User user = shareService.getUserDetail();
		StringBuilder hql = new StringBuilder("select count(c) from ChoseProfessorRecord c where 1=1 and c.choseProfessorBatch.choseTheme.id="+themeId+" and c.choseProfessor.user.username='"+user.getUsername()+"'");
		return ((Long) choseProfessorRecordDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/**
	 * 根据主键查找大纲
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	@Override
	public ChoseTheme findChoseThemeByPrimaryKey(Integer choseThemeId) {
		return choseThemeDAO.findChoseThemeByPrimaryKey(choseThemeId);
	}
	/***********************************************************************************************
	 * 查询所有的大纲
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 ***********************************************************************************************/
	@Override
	public List<ChoseTheme> findAllChoseThemes() {
		// TODO Auto-generated method stub
		String sql="select e from ChoseTheme e where 1=1";//where s.academyNumber like '02__'  2015.10.02贺子龙
		List<ChoseTheme> list=new ArrayList<ChoseTheme>();
		List<ChoseTheme> s=choseThemeDAO.executeQuery(sql, 0,-1);
		for (ChoseTheme a:s) {
			if(list.size()>100){//11
				break;
			}
			list.add(a);
		}
		return list;
	}

	/**
	 * 根据大纲id找出备选导师名单
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public List<ChoseProfessor> findChoseProfessorByThemeId(Integer termId, Integer currpage, Integer pageSize, ChoseProfessor choseProfessor) {
		StringBuffer hql=new StringBuffer("select cp from ChoseProfessor cp where cp.choseTheme.id="+termId);
		if(choseProfessor!=null){
			if(choseProfessor.getUser()!=null){
				if(choseProfessor.getUser().getCname()!=null&&!"".equals(choseProfessor.getUser().getCname())){
					hql.append(" and cp.user.cname like '%"+choseProfessor.getUser().getCname()+"%'");
				}
			}
		}
		return choseProfessorDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/**
	 * 根据大纲id找出备选导师名单的总记录数
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public int findCountChoseProfessorByThemeId(Integer termId, ChoseProfessor choseProfessor){
		StringBuilder hql = new StringBuilder("select count(c) from ChoseProfessor c where 1=1 and c.choseTheme.id="+termId);
		if(choseProfessor!=null){
			if(choseProfessor.getUser()!=null){
				if(choseProfessor.getUser().getCname()!=null&&!"".equals(choseProfessor.getUser().getCname())){
					hql.append(" and c.user.cname like '%"+choseProfessor.getUser().getCname()+"%'");
				}
			}
		}
		return ((Long) choseProfessorDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * 根据user表找出所有的导师列表
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public List<User> findChoseProfessorByUser(Integer currpage, Integer pageSize) {
		String hql="select u from User u where u.userRole=1";
		return userDAO.executeQuery(hql, (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 根据user表找出所有的导师列表总记录数
	 * @author 赵晶
	 * @date 2017.12.08
	 */
	public int findCountChoseProfessorByUser() {
		StringBuilder hql = new StringBuilder("select count(*) from User u where u.userRole=1");
		return ((Long) choseProfessorDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 *从user表里根据cname和username找到所有的导师
	 *@author 赵晶
	 *@date 2017.12.09
	 */
	public List<User> findUserByCnameAndUsername(User user,
                                                 Integer currpage, int pageSize, String[] usernameArray) {
		StringBuffer hql=new StringBuffer("select u from User u where 1=1 and u.userRole=1");
		if(user.getCname()!=null&&!"".equals(user.getCname())){
			hql.append(" and u.cname like '%"+user.getCname()+"%'");
		}
		if(user.getUsername()!=null&&!"".equals(user.getUsername())){
			hql.append(" and u.username like '%"+user.getUsername()+"%'");
		}
		//排除已经选择导师
		if(usernameArray!=null&&usernameArray.length!=0){
			hql.append(" and u.username not in(");
			for(int i=0;i<usernameArray.length;i++){
				if(i==usernameArray.length-1){
					hql.append("'"+usernameArray[i]+"'");
				}
				else{
					hql.append("'"+usernameArray[i]+"',");
				}
				
			}
			hql.append(")");				
		}
		return userDAO.executeQuery(hql.toString(),(currpage-1)*pageSize,pageSize);
	}

	/**
	 *从user表里根据cname和username找到所有的导师的记录数
	 *@author 赵晶
	 *@date 2017.12.09
	 */
	public int findCountUserByCnameAndUsername(User user,String[] usernameArray) {
		StringBuffer hql=new StringBuffer("select count(*) from User u where 1=1 and u.userRole=1");
		if(user.getCname()!=null&&!"".equals(user.getCname())){
			hql.append(" and u.cname like '%"+user.getCname()+"%'");
		}
		if(user.getUsername()!=null&&!"".equals(user.getUsername())){
			hql.append(" and u.username='"+user.getUsername()+"'");
		}
		//排除已经选择导师
		if(usernameArray!=null&&usernameArray.length!=0){
			hql.append(" and u.username not in(");
			for(int i=0;i<usernameArray.length;i++){
				if(i==usernameArray.length-1){
					hql.append("'"+usernameArray[i]+"'");
				}
				else{
					hql.append("'"+usernameArray[i]+"',");
				}
				
			}
			hql.append(")");				
		}
		return ((Long) userDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 *根据termId、cname找到所有的备选导师
	 *@author 赵晶
	 *@date 2017.12.11
	 */
	public List<ChoseProfessor> choseProfessorList(
            ChoseProfessor choseProfessor, Integer currpage, Integer pageSize) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("select p from ChoseProfessor p where 1=1 ");
		if(choseProfessor!=null){
			if(choseProfessor.getChoseTheme()!=null){
				if(choseProfessor.getChoseTheme().getId()!=null&&!"".equals(choseProfessor.getChoseTheme().getId())){
					hql.append(" and p.choseTheme.id="+choseProfessor.getChoseTheme().getId());
				}
			}
			if(choseProfessor.getUser()!=null){
				if(choseProfessor.getUser().getCname()!=null&&!"".equals(choseProfessor.getUser().getCname())){
					hql.append(" and p.user.cname like '%"+choseProfessor.getUser().getCname()+"%'");
				}
			}
		}
		return choseProfessorDAO.executeQuery(hql.toString(), (currpage-1)*pageSize,pageSize);
	}

	/**
	 *根据termId、cname找到所有的备选导师的总记录数
	 *@author 赵晶
	 *@date 2017.12.11
	 */
	public int countProfessorList(ChoseProfessor choseProfessor) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("select count(*) from ChoseProfessor p where 1=1");
		if(choseProfessor!=null){
			if(choseProfessor.getChoseTheme()!=null){
				if(choseProfessor.getChoseTheme().getId()!=null&&!"".equals(choseProfessor.getChoseTheme().getId())){
					hql.append(" and p.choseTheme.id="+choseProfessor.getChoseTheme().getId());
				}
			}
			if(choseProfessor.getUser()!=null){
				if(choseProfessor.getUser().getCname()!=null&&!"".equals(choseProfessor.getUser().getCname())){ 
					hql.append(" and p.user.cname like '%"+choseProfessor.getUser().getCname()+"%'");
				}
			}
		}
		return ((Long) choseProfessorDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/**
	 * 根据termId、username找到的备选导师
	 *@author 赵晶
	 *@date 2017.12.15
	 */
	public ChoseProfessor findChoseProfessor(ChoseProfessor choseProfessor) {
		String hql="select c from ChoseProfessor c where 1=1  ";
		if(choseProfessor!=null){
			if(choseProfessor.getUser()!=null){
				hql+=" and c.user.username='"+choseProfessor.getUser().getUsername()+"'";
			}
			if(choseProfessor.getChoseTheme()!=null){
				hql+=" and c.choseTheme.id="+choseProfessor.getChoseTheme().getId();
			}
			//Object executeQuerySingleResult = choseProfessorDAO.executeQuerySingleResult(hql);
			if(choseProfessorDAO.executeQuery(hql, 0, 1)!=null&&choseProfessorDAO.executeQuery(hql, 0, 1).size()!=0){
				ChoseProfessor choseProfessor1 =choseProfessorDAO.executeQuery(hql, 0, 1).get(0);
				return choseProfessor1;
			}
		}
		return null;
	}
	/***************************************************************************
	 * @Description 找到不在某大纲下的剩余可添加user
	 * @return List<User>
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	@Override
	public List<User> findUserNotInProfessor(int themeId, int currpage, int pagesize){
		//已在大纲下的导师
		List<String> usernames = entityManager.createNativeQuery("select username from chose_professor p where p.theme_id ="+themeId).getResultList();
		StringBuilder s = new StringBuilder("");
		for (String username : usernames) {
			s.append("'");
			s.append(username);
			s.append("',");
		}
		s.append("''");
		//未在大纲下的user
		List<User> list = userDAO.executeQuery("select u from User u where u.userRole ='1' and u.username not in("+s.toString()+")",(currpage-1)*pagesize,pagesize);
		return list;
	}
	/***************************************************************************
	 * @Description 找到不在某大纲下的剩余导师数量
	 * @return List<User>
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	public int findUserNotInProfessorCount(int themeId){
		List<User> users = new ArrayList<User>();
		List<String> usernames = entityManager.createNativeQuery("select username from chose_professor p where p.theme_id ="+themeId).getResultList();
		StringBuilder s = new StringBuilder("");
		for (String username : usernames) {
			s.append("'");
			s.append(username);
			s.append("',");
		}
		s.append("''");
		return BigInteger.valueOf((Long)userDAO.executeQuerySingleResult("select count(u) from User u where u.userRole ='1' and u.username not in("+s.toString()+")")).intValue();
	}
	/***************************************************************************
	 * @Description 找到不在某届的所有学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-12-25
	 **************************************************************************/
	public List<User> findStudentByUser(Integer currpage, int pageSize,
                                        User user) {
		String hql="select u from User u where 1=1 ";
		if(user!=null){
			//根据非批次查询
			if(user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime())){
				hql+=" and u.attendanceTime!="+user.getAttendanceTime();
			}
			//根据学号查询
			if(user.getUsername()!=null&&!"".equals(user.getUsername())){
				hql+=" and u.username like '%"+user.getUsername()+"%'";
			}
			//根据姓名查询
			if(user.getCname()!=null&&!"".equals(user.getCname())){
				hql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			//设置为学生
			hql+=" and u.userRole=0";
		}
		return userDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}

	/***************************************************************************
	 * @Description 统计找到不在某届的所有学生数
	 * @return int
	 * @author 赵晶
	 * @date 2017-12-25
	 **************************************************************************/
	public int findCountStudentByUser(User user) {
		String hql="select count(*) from User u where 1=1 ";
		if(user!=null){
			//根据非批次查询
			if(user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime())){
				hql+=" and u.attendanceTime!="+user.getAttendanceTime();
			}
			//根据学号查询
			if(user.getUsername()!=null&&!"".equals(user.getUsername())){
				hql+=" and u.username like '%"+user.getUsername()+"%'";
			}
			//根据姓名查询
			if(user.getCname()!=null&&!"".equals(user.getCname())){
				hql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			//根据学生查询
			hql+=" and u.userRole=0";
		}
		return ((Long) userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}

	/***************************************************************************
	 * @Description 找出选中其它届的所有学生realAttendanceTime为选中的届
	 * @return List
	 * @author 赵晶
	 * @date 2017-12-28
	 **************************************************************************/
	public List<User> findSelectStudentByUser(int currpage, int pageSize, User user) {
		String hql="select u from User u where 1=1 ";
		if(user!=null){
			//根据非批次查询
			if(user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime())){
				hql+=" and u.choseUser.realAttendanceTime="+user.getAttendanceTime();
			}
			//根据学号查询
			if(user.getUsername()!=null&&!"".equals(user.getUsername())){
				hql+=" and u.username like '%"+user.getUsername()+"%'";
			}
			//根据姓名查询
			if(user.getCname()!=null&&!"".equals(user.getCname())){
				hql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			hql+=" and (u.choseUser.professor is null or u.choseUser.professor='')";
			//设置为学生
			hql+=" and u.userRole=0";
		}
		return userDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}

	/***************************************************************************
	 * @Description 统计选中其它届的所有学生realAttendanceTime为选中的届
	 * @return int
	 * @author 赵晶
	 * @date 2017-12-28
	 **************************************************************************/
	public int findCountSelectStudentByUser(User user) {
		String hql="select count(*) from User u where 1=1 ";
		if(user!=null){
			//根据非批次查询
			if(user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime())){
				hql+=" and u.choseUser.realAttendanceTime="+user.getAttendanceTime();
			}
			//根据学号查询
			if(user.getUsername()!=null&&!"".equals(user.getUsername())){
				hql+=" and u.username like '%"+user.getUsername()+"%'";
			}
			//根据姓名查询
			if(user.getCname()!=null&&!"".equals(user.getCname())){
				hql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			hql+=" and (u.choseUser.professor is null or u.professor='')";
			//设置为学生
			hql+=" and u.userRole=0";
		}
		return ((Long) userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}

	/***************************************************************************
	 * @Description 找到不在某届的所有学生记录
	 * @return List
	 * @author 赵晶
	 * @date 2017-12-28
	 **************************************************************************/
	public List<User> findOtherBatchStudentByuser(Integer currpage,
                                                  int pageSize, User user, String[] usernameArray) {
		String hql="select u from User u left join u.choseUser cu where 1=1 ";
		if(user!=null){
			//根据非批次查询
			if(user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime())){
				hql+=" and u.attendanceTime!="+user.getAttendanceTime();
			}
			//根据学号查询
			if(user.getUsername()!=null&&!"".equals(user.getUsername())){
				hql+=" and u.username like '%"+user.getUsername()+"%'";
			}
			//根据姓名查询
			if(user.getCname()!=null&&!"".equals(user.getCname())){
				hql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			hql+=" and u.userRole=0 ";
			//排除已经选择非本届的学生
			if(usernameArray!=null&&usernameArray.length!=0){
				hql+=" and u.username not in(";
				for(int i=0;i<usernameArray.length;i++){
					if(i==usernameArray.length-1){
						hql+="'"+usernameArray[i]+"'";
					}
					else{
						hql+="'"+usernameArray[i]+"',";
					}
					
				}
				hql+=")";				
			}
			hql+=" and (cu.professor is null or cu.professor='')";
			hql+=" and (cu.realAttendanceTime is null or cu.realAttendanceTime='' or cu.realAttendanceTime='"+user.getAttendanceTime()+"')";
			//设置为学生
			hql+=" and u.userRole=0";
		}
		return userDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 统计不在某届的所有学生记录
	 * @return List
	 * @author 赵晶
	 * @date 2017-12-28
	 **************************************************************************/
	public int findCountOtherBatchStudentByuser(User user,String[] usernameArray) {
		String hql="select count(*) from User u left join u.choseUser cu where 1=1 ";
		if(user!=null){
			//根据非批次查询
			if(user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime())){
				hql+=" and u.attendanceTime!="+user.getAttendanceTime();
			}
			//根据学号查询
			if(user.getUsername()!=null&&!"".equals(user.getUsername())){
				hql+=" and u.username like '%"+user.getUsername()+"%'";
			}
			//根据姓名查询
			if(user.getCname()!=null&&!"".equals(user.getCname())){
				hql+=" and u.cname like '%"+user.getCname()+"%'";
			}
			//排除已经选择非本届的学生
			if(usernameArray!=null&&usernameArray.length!=0){
				hql+=" and u.username in(";
				for(int i=0;i<usernameArray.length;i++){
					if(i==usernameArray.length-1){
						hql+="'"+usernameArray[i]+"'";
					}
					else{
						hql+="'"+usernameArray[i]+"',";
					}
					
				}
				hql+=")";				
			}
			hql+=" and (cu.professor is null or cu.professor='')";
			hql+=" and (cu.realAttendanceTime is null or cu.realAttendanceTime='')";
			//设置为学生
			hql+=" and u.userRole=0";
		}
		return ((Long) userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 统计找到所属的备选大纲
	 * @return int
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public int findBelongChoseThemeListCount(ChoseTheme choseTheme,User user) {
		StringBuffer hql=new StringBuffer(" select count(*) from ChoseTheme c where 1=1 and c.type=1");
		//根据大纲编号查询
		if(choseTheme!=null){
			if(choseTheme.getId()!=null&&!"".equals(choseTheme.getId())){
				hql.append(" and c.id like '%"+choseTheme.getId() +"%'");
			}
		}
		//已发布
		hql.append(" and c.state!=0");
		//根据进入学校所属的届attendance_time和real_attendance_time
		if(user!=null){
			//只有attendanceTime有值
			if((user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime()))&&(user.getChoseUser()==null||user.getChoseUser().getRealAttendanceTime()==null||"".equals(user.getChoseUser().getRealAttendanceTime()))){
				hql.append(" and c.theYear='"+user.getAttendanceTime()+"'");
			}
			//有realAttendanceTime有值
			if(user.getChoseUser()!=null&& user.getChoseUser().getRealAttendanceTime()!=null&&!"".equals(user.getChoseUser().getRealAttendanceTime())){
				hql.append(" and c.theYear='"+user.getChoseUser().getRealAttendanceTime()+"'");
			}
		}
		return ((Long) userDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 找到所属的备选大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public List<ChoseTheme> BelongChoseThemeList(ChoseTheme choseTheme, User user,
                                                 int currpage, int pageSize) {
		StringBuffer hql=new StringBuffer(" select c from ChoseTheme c where 1=1 and c.type=1");
		//根据大纲编号查询
		if(choseTheme!=null){
			if(choseTheme.getId()!=null&&!"".equals(choseTheme.getId())){
				hql.append(" and c.id like '%"+choseTheme.getId() +"%'");
			}
		}
		//已发布
		hql.append(" and c.state!=0");
		//根据进入学校所属的届attendance_time和real_attendance_time
		if(user!=null){
			//只有attendanceTime有值
			if((user.getAttendanceTime()!=null&&!"".equals(user.getAttendanceTime()))&&(user.getChoseUser()==null||user.getChoseUser().getRealAttendanceTime()==null||"".equals(user.getChoseUser().getRealAttendanceTime()))){
				hql.append(" and c.theYear='"+user.getAttendanceTime()+"'");
			}
			//有realAttendanceTime有值
			if(user.getChoseUser()!=null&&user.getChoseUser().getRealAttendanceTime()!=null&&!"".equals(user.getChoseUser().getRealAttendanceTime())){
				hql.append(" and c.theYear='"+user.getChoseUser().getRealAttendanceTime()+"'");
			}
		}
		return choseThemeDAO.executeQuery(hql.toString(), (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 根据备选大纲id找到可选的所有导师-学生
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public List<ChoseProfessor> findProfessorListByChemeId(Integer id) {
		StringBuffer hql=new StringBuffer(" select c from ChoseProfessor c where 1=1 and c.choseTheme.id='"+id+"'");
		return choseProfessorDAO.executeQuery(hql.toString(),0,-1);
	}
	/***************************************************************************
	 * @Description 根据备选大纲id找到所属互选志愿批次-学生
	 * @return ChoseProfessorBatch
	 * @author 赵晶
	 * @date 2017-12-29
	 **************************************************************************/
	public List<ChoseProfessorBatch> belongChoseProfessorBatchList(Integer id) {
		StringBuffer hql=new StringBuffer(" select c from ChoseProfessorBatch c where 1=1 and c.choseTheme.id='"+id+"'");
		return choseProfessorBatchDAO.executeQuery(hql.toString(),0,-1);
	}
	/***************************************************************************
	 * @Description 根据已经选择的批次-学生
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-2
	 **************************************************************************/
	public ChoseProfessorRecord getChoseProfessorRecordByIdAndUsername(
            Integer id, String username) {
		StringBuffer hql=new StringBuffer(" select c from ChoseProfessorRecord c where 1=1 and c.choseProfessorBatch.id='"+id+"'"+" and c.user.username='"+username+"'");
		if(choseProfessorRecordDAO.executeQuery(hql.toString(),0,-1).size()==0){
			return null;
		}
		return choseProfessorRecordDAO.executeQuery(hql.toString(),0,-1).get(0);
	}
	/***************************************************************************
	 * @Description 根据导师username找到符合条件的备选导师列表-导师
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public List<ChoseProfessor> choseProfessorListByUsername(String username) {
		StringBuffer hql=new StringBuffer("select c from ChoseProfessor c where 1=1 and c.user.username='"+username+"'");
		return choseProfessorDAO.executeQuery(hql.toString(), 0,-1);
	}
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表-导师
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public List<Object[]> choseProfessorBatchListForProfessor(
            Integer themeId, String username, Integer currpage, int pageSize) {
		StringBuffer sql=new StringBuffer("SELECT cpb.id,ct.the_year,cpb.batch_number,cpb.start_time,cpb.end_time,cpb.chose_record_id,");
		sql.append("SUM(CASE WHEN( (cu.professor is null or cu.professor='') and cpr.t_username='"+username+"') THEN 1 ELSE 0 END) AS sum_count,");
		sql.append("SUM(CASE WHEN(cpr.aduit_result=1 AND cpr.t_username='"+username+"') THEN 1 ELSE 0 END) AS result_count, ");
		sql.append(" cp.final_number,");
		sql.append(" cp.is_audit,");
		sql.append(" cp.expect_number");
		sql.append(" from chose_theme AS ct");
		sql.append(" JOIN chose_professor_batch AS cpb ON ct.id = cpb.chose_record_id ");
		sql.append(" LEFT JOIN chose_professor_record AS cpr ON cpr.batch_id = cpb.id ");
		sql.append(" LEFT JOIN user as u ON u.username=cpr.student");
		sql.append(" LEFT JOIN chose_professor cp ON cpr.professor_id = cp.id");
		sql.append(" LEFT JOIN chose_user cu ON cu.username=cpr.student");
		sql.append(" WHERE cpb.chose_record_id ="+themeId+" and ct.type=1 and cpb.start_time < " + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		sql.append(" and cp.username = '" + username + "'");
		sql.append(" GROUP BY cpb.id ");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setFirstResult((currpage-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public int choseProfessorBatchListForProfessorCount(Integer themeId,
			String username) {
		StringBuffer sql = new StringBuffer("SELECT count(*) from ");
		sql.append("(select cpb.id");
		sql.append(" from chose_theme AS ct");
		sql.append(" JOIN chose_professor_batch AS cpb ON ct.id = cpb.chose_record_id ");
		sql.append(" LEFT JOIN chose_professor_record AS cpr ON cpr.batch_id = cpb.id ");
		sql.append("LEFT JOIN user as u ON u.username=cpr.student");
		sql.append(" LEFT JOIN chose_professor cp ON cpr.professor_id = cp.id");
		sql.append(" LEFT JOIN chose_user cu ON cu.username=cpr.student");
		sql.append(" WHERE cpb.chose_record_id ="+themeId+" and ct.type=1 and cpb.start_time < " + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		sql.append(" and cpr.t_username = '" + username + "'");
		sql.append(" GROUP BY cpb.id ");
		sql.append(") a1");
		Query query = entityManager.createNativeQuery(sql.toString());
		return ((BigInteger) query.getSingleResult()).intValue();
		
	}
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到未审核的学生名单-导师
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public List<ChoseProfessorRecord> aduitStudentList(String tname,
                                                       Integer batchId) {
		String hql="select r from ChoseProfessorRecord r where r.tUsername='"+tname+"'";
		hql+=" and r.choseProfessorBatch.id="+batchId;
		hql+=" and r.currAduit=0";//未审核
		hql+=" and ( r.user.choseUser.professor=null or r.user.choseUser.professor='' )";
		return choseProfessorRecordDAO.executeQuery(hql, 0,-1);
	}
	/***************************************************************************
	 * @Description 获取所属大纲的已选学生数量-导师
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-3
	 **************************************************************************/
	public Object[]  choseThemeStudentCount(String username, Integer themeId) {
		StringBuffer sql=new StringBuffer("SELECT cp.expect_number,SUM(CASE WHEN (cpr.aduit_result = 1 AND cpr.t_username='"+username+"')THEN 1 ELSE 0 END) AS result_count");
		sql.append(" from chose_theme ct");
		sql.append(" join chose_professor cp on cp.theme_id = ct.id");
		sql.append(" LEFT JOIN chose_professor_batch cpb ON ct.id = cpb.chose_record_id ");
		sql.append(" LEFT JOIN chose_professor_record cpr ON cpb.id = cpr.batch_id ");
		sql.append(" where cpb.chose_record_id="+themeId+" and ct.type=1");
		sql.append(" and cp.username = '"+username + "'");
		sql.append(" GROUP BY ct.id ");
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到审核通过的学生名单-导师
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-1-4
	 **************************************************************************/
	public List<ChoseProfessorRecord> studentList(String username,
                                                  Integer batchId) {
		String hql="select r from ChoseProfessorRecord r where r.tUsername='"+username+"'";
		hql+=" and r.choseProfessorBatch.id="+batchId;
		//hql+=" and r.choseProfessorBatch.choseTheme.id="+themeId;
		hql+=" and r.currAduit=2";//审核
		hql+=" and r.aduitResult=1";//同意
		hql+=" and r.user.choseUser.professor!=null";
		hql+=" and r.user.choseUser.professor!=''";
		return choseProfessorRecordDAO.executeQuery(hql, 0,-1);
	}
	/****************************************************************************
	 * 功能：查询没有导师的学生列表-导师
	 * 作者：赵晶
	 * 时间：2017-01-05
	 ****************************************************************************/
	public List<User> noProfessorStudentList(Integer attendanceTime, Integer currpage, Integer pageSize) {
		StringBuffer hql=new StringBuffer("select u from User u" +
				" left join u.choseUser cu " +
				" where (cu.professor='' or cu.professor is null) and  ((u.attendanceTime="+attendanceTime);
		hql.append(" and (cu.realAttendanceTime is null or cu.realAttendanceTime='')) ");
		hql.append(" or cu.realAttendanceTime="+attendanceTime+")");
		hql.append(" and u.userRole=0");
		return userDAO.executeQuery(hql.toString(), (currpage-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * @Description 统计没有导师的学生数量-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-05
	 ****************************************************************************/
	public int noProfessorStudentListCount(Integer attendanceTime,
                                           Integer currpage, Integer pageSize) {
		StringBuffer hql=new StringBuffer("select count(*) from User u" +
				" left join u.choseUser cu " +
				" where (cu.professor='' or cu.professor is null) and  ((u.attendanceTime="+attendanceTime);
		hql.append(" and (cu.realAttendanceTime is null or cu.realAttendanceTime='')) ");
		hql.append(" or cu.realAttendanceTime="+attendanceTime+")");
		hql.append(" and u.userRole=0");
		return ((Long) userDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @Description 有剩余位置的导师列表-管理员
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2017-01-06
	 ****************************************************************************/
	
	public List<Object[]> havePositionProfessorList(User user, Integer choseThemeId,
                                                    Integer currpage, int pageSize) {
		StringBuffer sql=new StringBuffer("select ct.id,u.username,u.cname,cp.final_number,cp.expect_number-cp.final_number as optional_number from  chose_theme ct");
		sql.append("  inner JOIN chose_professor cp ON ct.id=cp.theme_id");
		sql.append("  inner JOIN user u ON cp.username=u.username");
		sql.append("  WHERE cp.expect_number-cp.final_number>0");
		
		sql.append("  and ct.id="+choseThemeId);
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setFirstResult((currpage-1)*pageSize);
		query.setMaxResults(pageSize);
		List<Object[]> list = query.getResultList();
		if(list!=null){
			return list;
		}
		return null;
	}
	/****************************************************************************
	 * @Description 有剩余位置的导师列表数-管理员
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findhavePositionProfessorListCount(User user, Integer choseThemeId) {
		StringBuffer sql=new StringBuffer("select count(*) from  chose_theme ct");
		sql.append("  inner JOIN chose_professor cp ON ct.id=cp.theme_id");
		sql.append("  inner JOIN user u ON cp.username=u.username");
		sql.append("  WHERE cp.expect_number-cp.final_number>0");
	    sql.append(" and ct.id="+choseThemeId);
		Query query = entityManager.createNativeQuery(sql.toString());
		Object object = query.getSingleResult();
		if(object!=null){
			return Integer.parseInt(String.valueOf(object));
		}
		return 0;
	}
	/****************************************************************************
	 * @Description 根据导师的username和所属的大纲choseThemeId找到导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public ChoseProfessor getChoseProfessorByUsernameAndThemeId(
            String professor, Integer choseThemeId) {
		String hql="select c from ChoseProfessor c where c.user.username='"+professor+"'";
		hql+=" and c.choseTheme.id="+choseThemeId;
		List<ChoseProfessor> list = choseProfessorDAO.executeQuery(hql, 0,-1);
		if(list!=null&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	/****************************************************************************
	 * @Description 找到属于当前时间的批次
	 * @return ChoseProfessorBatch
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public ChoseProfessorBatch belongCurrentTimeChoseProfessorBath(
			Calendar currTime) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String time= sdf.format(currTime.getTime());
		String hql="select cpb from ChoseProfessorBatch cpb where cpb.choseTheme.type=1 and cpb.choseTheme.state=1 and cpb.startTime<'"+time+"'";
		hql+=" and cpb.endTime>'"+time+"'";
		List<ChoseProfessorBatch> list = choseProfessorBatchDAO.executeQuery(hql, 0,-1);
		if(list!=null&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	/****************************************************************************
	 * @Description 根据choseThemeId找到参选人数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findParticipantNumberBythemeId(Integer theYear) {
		StringBuffer hql=new StringBuffer("SELECT count(*)");
		hql.append(" from User u ");
		hql.append(" where ((u.attendanceTime="+theYear);
		hql.append(" and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime=''))");
		hql.append(" or u.choseUser.realAttendanceTime="+theYear+")");
		hql.append(" and u.userRole=0");
		Object object = userDAO.createQuerySingleResult(hql.toString()).getSingleResult();
		if(object!=null){
			return Integer.parseInt(String.valueOf(object));
		}
		return 0;
	}
	/****************************************************************************
	 * @Description 根据choseThemeId找到未被选人数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-08
	 ****************************************************************************/
	public int findNoSelectedNumberBythemeId(Integer theYear, Integer themeId) {
		StringBuffer hql=new StringBuffer("SELECT count(*)");
		hql.append(" from User u ");
		hql.append(" WHERE (u.choseUser.professor=''");
		hql.append(" OR u.choseUser.professor is null) ");
		hql.append(" and ((u.attendanceTime='"+theYear+"'");
		hql.append(" and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime=''))");
		hql.append(" OR u.choseUser.realAttendanceTime='"+theYear+"')");
		hql.append(" and u.userRole=0");
		Object object = userDAO.createQuerySingleResult(hql.toString()).getSingleResult();
		if(object!=null){
			return Integer.parseInt(String.valueOf(object));
		}
		return 0;
	}
	/****************************************************************************
	 * @Description 根据批次ids数组和导师id找到某大纲下某导师中所有审核通过的学生
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public List<ChoseProfessorRecord> passStudentListForProfessor(
			int[] batchIds, Integer professorId) {
		StringBuffer hql=new StringBuffer("select cpr from ChoseProfessorRecord cpr where 1=1");
		hql.append(" and cpr.choseProfessor.id="+professorId);
		if(batchIds!=null && batchIds.length>0){
			hql.append(" and cpr.choseProfessorBatch.id in(");
			for(int i=0;i<batchIds.length;i++){
				if(i==batchIds.length-1){
					hql.append(batchIds[i]+")");
				}
				else{
					hql.append(+batchIds[i]+",");
				}
			    
			}
		}
		
		hql.append(" and cpr.aduitResult=1");
		List<ChoseProfessorRecord> list = choseProfessorRecordDAO.executeQuery(hql.toString(), 0,-1);
		
		return list;
	}
	/****************************************************************************
	 * @Description 根据导师的username找到所属的导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public ChoseProfessor findProfessorByProfessorUsername(String professor) {
		String hql="select cp from ChoseProfessor cp where cp.user.username='"+professor+"'";
		List<ChoseProfessor> list = choseProfessorDAO.executeQuery(hql, 0,-1);
		if(list!=null&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/****************************************************************************
	 * @Description 根据导师username找到导师所有的学生列表
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public List<User> myStudentForProfessor(String username) {
		String hql="select u from User u where u.choseUser.professor='"+username+"'";
		List<User> list = userDAO.executeQuery(hql, 0,-1);
		return list;
	}
	/****************************************************************************
	 * @Description 根据导师username找到导师所有的导师列表
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2017-01-10
	 ****************************************************************************/
	public List<ChoseProfessor> findchoseProfessorList(String tUsername) {
		String hql="select cp from ChoseProfessor cp where cp.user.username='"+tUsername+"'";
		List<ChoseProfessor> list = choseProfessorDAO.executeQuery(hql, 0,-1);
		return list;
	}
	/****************************************************************************
	 * @Description 根据大纲id找到大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-01-10
	 ****************************************************************************/
	public List<ChoseTheme> findChoseThemeListByThemeIds(Integer[] themeIds,
                                                         ChoseTheme choseTheme, Integer currpage, int pageSize) {
		StringBuffer hql=new StringBuffer("select ct from ChoseTheme ct where 1=1 and ct.type=1");
		hql.append(" and ct.id in(");
		for(int i=0;i<themeIds.length;i++){
			if(i==themeIds.length-1){
				hql.append(themeIds[i]+")");
			}
			else{
				hql.append(+themeIds[i]+",");
			}
		}
		List<ChoseTheme> list = choseThemeDAO.executeQuery(hql.toString(), (currpage-1)*pageSize,pageSize);
		return list;
	}
	/****************************************************************************
	 * @Description 根据大纲id找到大纲记录数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-10
	 ****************************************************************************/
	public int findChoseThemeListByThemeIdsCount(Integer[] themeIds,
			ChoseTheme choseTheme) {
		StringBuffer hql=new StringBuffer("select count(*) from ChoseTheme ct where 1=1 and ct.type=1");
		hql.append(" and ct.id in(");
		for(int i=0;i<themeIds.length;i++){
			if(i==themeIds.length-1){
				hql.append(themeIds[i]+")");
			}
			else{
				hql.append(+themeIds[i]+",");
			}
		}
		return ((Long) choseThemeDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @Description 找到所有的学部
	 * @return List<String>
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public List<String> findAllTeachingDepartment(){
		String hql="select distinct u from User u group by teachingDepartment";
		List<User> userList = userDAO.executeQuery(hql, 0,-1);
		List<String> result = new ArrayList<>();
		for(User u: userList){
			result.add(u.getTeachingDepartment());
		}
		return result;
	}
	/****************************************************************************
	 * @Description 找到未被选的导师-学生填志愿
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public List<ChoseProfessor> findNoSelectedProfessorListQuery(Integer id,
                                                                 Integer currpage, ChoseProfessor choseProfessor, List<Integer> professorIds, Integer pageSize) {
		StringBuffer hql=new StringBuffer(" select cp from ChoseProfessor cp where 1=1 and cp.choseTheme.id="+id);
		if(choseProfessor!=null){
			if(choseProfessor.getUser()!=null){
				if(choseProfessor.getUser().getCname()!=null&&!"".equals(choseProfessor.getUser().getCname())){
					hql.append(" and cp.user.cname like'"+choseProfessor.getUser().getCname()+"'");
				}
				if(choseProfessor.getUser().getTeachingDepartment()!=null&&!"".equals(choseProfessor.getUser().getTeachingDepartment())){
					if(choseProfessor.getUser().getTeachingDepartment()!=null){
						hql.append(" and cp.user.teachingDepartment = '"+choseProfessor.getUser().getTeachingDepartment() + "'");
					}
				}
			}
		}
		if(professorIds!=null&&professorIds.size()!=0){
			hql.append(" and cp.id not in(");
			for(int i=0;i<professorIds.size();i++){
				if(i==professorIds.size()-1){
					hql.append(professorIds.get(i)+")");
				}
				else{
					hql.append(professorIds.get(i)+",");
				}
			}
		}
		
		return choseProfessorDAO.executeQuery(hql.toString(),(currpage-1)*pageSize,pageSize);
	}

	/****************************************************************************
	 * @Description 找到未被选的导师记录-学生填志愿
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public int findNoSelectedProfessorListQueryCount(Integer id,
			ChoseProfessor choseProfessor, List<Integer> professorIds) {
		StringBuffer hql=new StringBuffer(" select count(*) from ChoseProfessor cp where 1=1 and cp.choseTheme.id='"+id+"'");
		if(choseProfessor!=null){
			if(choseProfessor.getUser()!=null){
				if(choseProfessor.getUser().getCname()!=null&&!"".equals(choseProfessor.getUser().getCname())){
					hql.append(" and cp.user.cname like'"+choseProfessor.getUser().getCname()+"'");
				}
				if(choseProfessor.getUser().getTeachingDepartment()!=null&&!"".equals(choseProfessor.getUser().getTeachingDepartment())){
					if(choseProfessor.getUser().getTeachingDepartment()!=null){
						hql.append(" and cp.user.teachingDepartment = '"+choseProfessor.getUser().getTeachingDepartment() + "'");
					}
				}
			}
		}
		if(professorIds!=null&&professorIds.size()!=0){
			hql.append(" and cp.id not in(");
			for(int i=0;i<professorIds.size();i++){
				if(i==professorIds.size()-1){
					hql.append(professorIds.get(i)+")");
				}
				else{
					hql.append(+professorIds.get(i)+",");
				}
			}
		}
		
		return ((Long) choseProfessorDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @Description 查询本次大纲下attendanceTime等于所选届的学生（没有导师）数量
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-11
	 ****************************************************************************/
	public int findStudentByAttendanceTimeCount(Integer theYear) {
		String hql="select count(*) from User u where u.attendanceTime="+theYear;
		hql+=" and (u.choseUser.professor is null or u.choseUser.professor='')";
		hql+=" and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime='')";
		return ((Long)userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @Description 根据届找到同届的未关闭的大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2017-01-15
	 ****************************************************************************/
	public List<ChoseTheme> UncloseSameOutlineList(Integer theYear) {
		String hql="select ct from ChoseTheme ct where ct.state!=2 and ct.type=1 and ct.theYear="+theYear;
		return choseThemeDAO.executeQuery(hql, 0,-1);
	}
	/****************************************************************************
	 * @Description 找到关闭大纲下的没有导师学生的列表
	 * @return List<User>
	 * @author 赵晶
	 * @date 2017-01-15
	 ****************************************************************************/
	public List<User> findNoProfessorUserByAttendancTime(String attendanceTime) {
		StringBuffer hql1=new StringBuffer("select u from User u where u.attendanceTime='"+attendanceTime+"'");
		hql1.append("and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime='')");
		hql1.append(" and (u.choseUser.professor is null or u.choseUser.professor='')");
		hql1.append(" and u.choseUser.isChoseProfessor=1");
		StringBuffer hql2=new StringBuffer("select u from User u where u.choseUser.realAttendanceTime='"+attendanceTime+"'");
		hql2.append(" and (u.choseUser.professor is null or u.choseUser.professor='')");
		hql2.append(" and u.choseUser.isChoseProfessor=1");
		List<User> usrs1=userDAO.executeQuery(hql1.toString(), 0,-1);
		List<User> usrs2=userDAO.executeQuery(hql2.toString(), 0,-1);
		usrs1.addAll(usrs2);
		return usrs1;
	}
	/***************************************************************************
	 * @Description 根据username查找学年论文
	 * @return List<TeachingEvaluation>
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public List<ChoseDessitationForYear> findChoseDisserationForYears(String username, int currpage, int pageSize, int state) {
		StringBuilder hql = new StringBuilder("select c from ChoseDessitationForYear c where 1=1");
		if(!username.equals("")){
			hql.append(" and (c.teacher = '"+username+"' or c.student = '"+username+"')");
		}
		if(state == 1){
			hql.append(" and c.state = "+state);
		}
		return choseDessitationForYearDAO.executeQuery(hql.toString(),(currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 根据username查找学年论文数量
	 * @return int
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public int findChoseDisserationForYearsCount(String username) {
		StringBuilder hql = new StringBuilder("select count(c) from ChoseDessitationForYear c where 1=1");
		if(!username.equals("")){
			hql.append(" and (c.teacher = '"+username+"' or c.student = '"+username+"')");
		}
		return ((Long) choseDessitationForYearDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 保存学年论文
	 * @return ChoseDessitationForYear
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public ChoseDessitationForYear saveChoseDessitationForYear(ChoseDessitationForYear choseDessitationForYear) {
		if(choseDessitationForYear != null){
			choseDessitationForYear = choseDessitationForYearDAO.store(choseDessitationForYear);
			choseDessitationForYearDAO.flush();
		}
		return choseDessitationForYear;
	}
	/***************************************************************************
	 * @Description 是否已发布学年论文
	 * @return ChoseDessitationForYear
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public boolean isAllreadyReleasedDessitationForYear(String teacher, String student, Integer year) {
		StringBuilder hql = new StringBuilder("select c from ChoseDessitationForYear c where 1=1");
		hql.append(" and c.teacher = '"+teacher+"'");
		hql.append(" and c.student = '"+student+"'");
		hql.append(" and c.theYear = "+year);
		hql.append(" and c.state =1");
		List<ChoseDessitationForYear> dessitationForYears = choseDessitationForYearDAO.executeQuery(hql.toString(), 0, -1);
		if(dessitationForYears.size() != 0){
			return true;
		}
		return false;
	}
	/***************************************************************************
	 * @Description 发布学年论文
	 * @return ChoseDessitationForYear
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public ChoseDessitationForYear releasedDessitationForYear(ChoseDessitationForYear dessitationForYear) {
		dessitationForYear.setState(1);
		return this.saveChoseDessitationForYear(dessitationForYear);
	}
	/***************************************************************************
	 * @Description 保存年度论文，返回存放的相对路径
	 * @return String
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public List<String> uploadDessistationForYear(HttpServletRequest request, HttpServletResponse response, int id) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		 ArrayList<String> addressAndName = new ArrayList<String>();
		 String sep = System.getProperty("file.separator");
		 Map files = multipartRequest.getFileMap();
		 Iterator fileNames = multipartRequest.getFileNames();
		 String fileNewName = "";
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"dessistationForYear"+sep+id;
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
			  //文件重命名
			  int endAddress = fileTrueName.lastIndexOf(".");
			  String ss = fileTrueName.substring(endAddress, fileTrueName.length());//后缀名
			  //String fileNewName = "bookImageType"+type+ss; 
			  fileNewName = fileTrueName;//"bookImage"+fileTrueName;
			  //System.out.println("文件名称："+fileTrueName);
			  File uploadedFile = new File(fileDir + sep + fileNewName);
			  //System.out.println("文件存放路径为："+fileDir + sep + fileNewName);
			  try {
				FileCopyUtils.copy(bytes,uploadedFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		  } 
		}
		addressAndName.add("upload"+ sep+"dessistationForYear"+sep+id+sep+fileNewName);
		addressAndName.add(fileNewName);
		return addressAndName;
	}
	/***************************************************************************
	 * @Description 保存年度论文对应的commonDocument
	 * @return CommonDocument
	 * @author 孙虎
	 * @date 2018-1-19
	 **************************************************************************/
	@Override
	public CommonDocument saveDessitationDocument(List<String> saveFileAddressName) {
		CommonDocument document = new CommonDocument();
		document.setDocumentName(saveFileAddressName.get(1));
		document.setDocumentUrl(saveFileAddressName.get(0));
		document.setType(2);
		document.setCreatedAt(Calendar.getInstance());
		document.setUser(shareService.getUserDetail());
		document = commonDocumentDAO.store(document);
		commonDocumentDAO.flush();
		return document;
	}
	/***************************************************************************
	 * @Description 下载学年论文
	 * @return void
	 * @author 孙虎
	 * @date 2018-1-23
	 **************************************************************************/
	@Override
	public void downloadDessistationForYear(HttpServletRequest request, HttpServletResponse response, Integer id, Integer dessistationId) {
		try {
			// id对应的document
			CommonDocument doc = commonDocumentDAO.findCommonDocumentByPrimaryKey(id);
			// 文件名称
			String fileName = doc.getDocumentName();
			//File directory = new File("");//设定为当前文件夹 
			String root = URLDecoder.decode(request.getSession().getServletContext().getRealPath(""),"utf-8");;
			String sep = System.getProperty("file.separator");
			// File.separator windows是\，unix是/
			String url = root+sep+ "upload"+ sep+"dessistationForYear"+sep+dessistationId;
			FileInputStream fis = new FileInputStream(url + sep + fileName);
			response.setCharacterEncoding("utf-8");
			// 解决上传中文文件时不能下载的问题
			response.setContentType("multipart/form-data;charset=UTF-8");
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName.replaceAll(" ", ""));

			OutputStream fos = response.getOutputStream();
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***************************************************************************
	 * @Description 根据类型找到注意事项列表
	 * @return List<ChoseAttention>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseAttention> findChoseAttentionByType(Integer type) {
		String hql="select ca from ChoseAttention ca where ca.type="+type;
		return choseAttentionDAO.executeQuery(hql, 0,-1);
	}

	/***************************************************************************
	 * @Description 根据类型数组找到注意事项列表
	 * @return List<ChoseAttention>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseAttention> findChoseAttentionByTypeArray(Integer[] types) {
		StringBuffer hql=new StringBuffer("select ca from ChoseAttention ca where 1=1");
		if(types!=null){
			hql.append(" and ca.type in(");
			for(int i=0;i<types.length;i++){
				if(i+1==types.length){
					hql.append(types[i]+")");
				}
				else{
					hql.append(types[i]+",");
				}
			}
		}
		return choseAttentionDAO.executeQuery(hql.toString(), 0,-1);
	}
	/***************************************************************************
	 * @Description 根据大纲id和用户username找到注意事项记录
	 * @return List<ChoseAttention>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseAttentionRecord> findChoseAttentinRecordByThemIdAndUsername(
            String username, Integer themeId) {
		String hql="select car from ChoseAttentionRecord car where car.username='"+username+"'";
		hql+=" and car.themeId="+themeId;
		return choseAttentionRecordDAO.executeQuery(hql, 0,-1);
	}

	/***************************************************************************
	 * @Description 找到user里的所有不同的批次
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2018-1-27
	 **************************************************************************/
	public List<Object> findAllAttendanceTime() {
		String sql="SELECT DISTINCT u.attendance_time from user u WHERE u.attendance_time is not null and u.attendance_time !=''";
		Query query = entityManager.createNativeQuery(sql);
		List resultList = query.getResultList();
		return resultList;
	}

	/***************************************************************************
	 * @Description 根据大纲id找到未填写期望学生数量的导师
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-28
	 **************************************************************************/
	public List<ChoseProfessor> findNoExpectNumberBythemeId(Integer themeId) {
		String hql="select cp from ChoseProfessor cp where cp.choseTheme.id="+themeId;
		hql+=" and (cp.expectNumber is null or cp.expectNumber ='')";
		return choseProfessorDAO.executeQuery(hql, 0,-1);
	}
	/***************************************************************************
	 * @Description 根据所属届找到本届的所有学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-28
	 **************************************************************************/
	public List<User> findStudentListByAttendanceTime(Integer theYear) {
		String hql="select u from User u left join u.choseUser cu where u.attendanceTime="+theYear;
		//不属于其他批次
		hql+=" and (cu.realAttendanceTime is null or cu.realAttendanceTime='')";
		//没有导师
		hql+=" and (cu.professor is null or cu.professor ='')";
		hql+=" and u.userRole=0";
		return userDAO.executeQuery(hql, 0,-1);
	}
	/***************************************************************************
	 * @Description 根据所属届找到非本届的所有学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-29
	 **************************************************************************/
	public List<User> findOhterStudentListByRealAttendanceTime(int attendanceTime) {
		// TODO Auto-generated method stub
		String hql="select u from User u where  u.choseUser.realAttendanceTime='"+attendanceTime+"'";
		hql+=" and (u.choseUser.professor is null or u.choseUser.professor ='')";
		hql+=" and u.userRole=0";
		return userDAO.executeQuery(hql, 0,-1);
	}
}
