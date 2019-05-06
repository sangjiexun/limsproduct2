package net.zjcclims.service.chose;

import net.zjcclims.dao.*;
import net.zjcclims.domain.*;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("ChoseDissertationService")
public class ChoseDissertationServiceImpl implements ChoseDissertationService {
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
    private ChoseProfessorRecordDAO choseProfessorRecordDAO;
	@Autowired
    private ShareService shareService;
	@Autowired
    private ChoseDissertationDAO choseDissertationDAO;
	@Autowired
    private ChoseThemeDAO choseThemeDAO;
	@Autowired
    private ChoseDissertationDirectionDAO choseDissertationDirectionDAO;
	@Autowired
    private ChoseDissertationRecordDAO choseDissertationRecordDAO;
	@Autowired
    private CommonDocumentDAO commonDocumentDAO;
	@Autowired
    private ChoseProfessorBatchDAO choseProfessorBatchDAO;
	/***************************************************************************************
	 * 分权限找到所有的毕业论文互选主题
	 * 作者：孙虎
	 * 时间：2017-12-22
	 ****************************************************************************************/
	@Override
	public List<ChoseTheme> findChoseThemesForCD(ChoseTheme choseTheme, int currpage, int pageSize) {
		StringBuilder hql = new StringBuilder("select c from ChoseTheme c where 1=1 and c.type = 2 ");
		if(choseTheme!=null){
			if(choseTheme.getTheYear()!=null&&!"".equals(choseTheme.getTheYear())){
				hql.append(" and c.theYear="+choseTheme.getTheYear());
			}
		}
		return choseThemeDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/***************************************************************************************
	 * 分权限找到所有的毕业论文互选主题数量
	 * 作者：孙虎
	 * 时间：2017-12-22
	 ****************************************************************************************/
	@Override
	public int findChoseThemesForCDCount(ChoseTheme choseTheme) {
		StringBuilder hql = new StringBuilder("select count(c) from ChoseTheme c where 1=1 and c.type = 2 ");
		if(choseTheme!=null){
			if(choseTheme.getTheYear()!=null&&!"".equals(choseTheme.getTheYear())){
				hql.append(" and c.theYear="+choseTheme.getTheYear());
			}
		}
		return ((Long) choseThemeDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/**************************************************************************
	 * @Description 保存ChoseTheme
	 * @return ChoseTheme
	 * @author 孙虎
	 * @date 2017-12-24
	 **************************************************************************/
	@Override
	public ChoseTheme saveChoseTheme(ChoseTheme choseTheme){
		ChoseTheme theme = choseThemeDAO.store(choseTheme);
		choseThemeDAO.flush();
		return theme;
	}
	/**************************************************************************
	 * @Description 根据届找到同届的的大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2018-1-16
	 **************************************************************************/
	public List<ChoseTheme> sameTheYearChoseThemeList(Integer theYear) {
		String hql="select ct from ChoseTheme ct where ct.state!=2 and ct.type=2 and ct.theYear="+theYear;
		return choseThemeDAO.executeQuery(hql, 0,-1);
	}
	/**************************************************************************
	 * @Description 找到所有方向
	 * @return List<ChoseDissertationDirection>
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public List<ChoseDissertationDirection> findAllDirection() {
		// TODO Auto-generated method stub
		String hql="select cdd from ChoseDissertationDirection cdd";
		return choseDissertationDirectionDAO.executeQuery(hql, 0,-1);
	}
	/**************************************************************************
	 * @Description 根据导师的username和大纲id找到导师
	 * @return ChoseProfessor
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public ChoseProfessor findChoseProfessorByProfessorUsername(
            String username, Integer themeId) {
		String hql="select cp from ChoseProfessor cp where cp.choseTheme.id="+themeId+" and cp.user.username='"+username+"'";
		return choseProfessorDAO.executeQuery(hql, 0,-1).get(0);
	}
	/**************************************************************************
	 * @Description 根据导师的username论文互选的所有大纲
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public List<ChoseProfessor> findchoseProfessorList(String username) {
		String hql="select cp from ChoseProfessor cp where cp.type=2 and cp.user.username='"+username+"'";
		return choseProfessorDAO.executeQuery(hql, 0,-1);
	}
	/**************************************************************************
	 * @Description 根据themeId找到所有的导师列表
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public List<ChoseProfessor> findChoseProfessorListByThemeId(
            Integer themeId, Integer currpage, int pageSize) {
		String hql="select cp from ChoseProfessor cp where cp.choseTheme.id="+themeId;
		return choseProfessorDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/**************************************************************************
	 * @Description 根据themeId找到所有的导师列表数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-17
	 **************************************************************************/
	public int findChoseProfessorListByThemeIdCount(Integer themeId) {
		String hql="select count(*) from ChoseProfessor cp where cp.choseTheme.id="+themeId;
		return ((Long)(choseProfessorDAO.executeQuerySingleResult(hql))).intValue();
	}
	/**************************************************************************
	 * @Description 根据professorId找到所有的论文列表
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public List<ChoseDissertation> findChoseDissertationListByProfessorId(
            Integer professorId, Integer currpage, int pageSize) {
		String hql="select cd from ChoseDissertation cd where cd.choseProfessor.id="+professorId;
		return choseDissertationDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/**************************************************************************
	 * @Description 根据professorId找到所有的论文记录数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public int findChoseDissertationListByProfessorIdCount(Integer professorId) {
		String hql="select count(*) from ChoseDissertation cd where cd.choseProfessor.id="+professorId;
		return ((Long)(choseDissertationDAO.executeQuerySingleResult(hql))).intValue();
	}
	/**************************************************************************
	 * @Description 找到大纲中非本届的没有论文的学生
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public List<User> findNoProfessorStudentList(String attendanceTime) {
		String hql="select u from User u left join u.choseUser cu where u.userRole=0 and  (cu.choseDissertation.id is null or cu.choseDissertation.id ='') and  cu.realAttendanceTime='"+attendanceTime+"'";
		return userDAO.executeQuery(hql, 0,-1);
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
			hql+=" and (cu.choseDissertation.id is null or cu.choseDissertation.id='')";
			hql+=" and (cu.realAttendanceTime is null or cu.realAttendanceTime='')";
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
			hql+=" and (cu.choseDissertation.id is null or cu.choseDissertation.id='')";
			hql+=" and (cu.realAttendanceTime is null or cu.realAttendanceTime='')";
			//设置为学生
			hql+=" and u.userRole=0";
		}
		return ((Long) userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @Description 查询本次大纲下attendanceTime等于所选届的学生（没有论文）数量
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-18
	 ****************************************************************************/
	public int findStudentByAttendanceTimeCount(Integer theYear) {
		String hql="select count(*) from User u where u.attendanceTime="+theYear;
		hql+=" and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime='')";
		hql+=" and (u.choseUser.choseDissertation.id is null or u.choseUser.choseDissertation.id='')";
		hql+="and u.userRole=0";
		return ((Long)userDAO.createQuerySingleResult(hql).getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 找到所属的备选大纲列表
	 * @return List<ChoseTheme>
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public List<ChoseTheme> BelongChoseThemeList(ChoseTheme choseTheme, User user,
                                                 int currpage, int pageSize) {
		StringBuffer hql=new StringBuffer(" select c from ChoseTheme c where 1=1 and c.type=2");
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
	 * @Description 统计找到所属的备选大纲
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-18
	 **************************************************************************/
	public int findBelongChoseThemeListCount(ChoseTheme choseTheme,User user) {
		StringBuffer hql=new StringBuffer(" select count(*) from ChoseTheme c where 1=1 and c.type=2");
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
		return ((Long) userDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/****************************************************************************
	 * @Description 找到当前志愿下未被选的论文-学生填志愿
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-01-19
	 ****************************************************************************/
	public List<ChoseDissertation> findNoSelectedDissertationList(
            List<Integer> professorIds, Integer currpage,
            ChoseDissertation choseDissertation,
            List<Integer> choseDissertationIds, int pageSize) {
		StringBuffer hql=new StringBuffer(" select cd from ChoseDissertation cd where 1=1 ");
		//找到属于大纲下所有论文
		if(professorIds!=null&&professorIds.size()!=0){
			hql.append(" and cd.choseProfessor.id in(");
			for(int i=0;i<professorIds.size();i++){
				if(i+1==professorIds.size()){
					hql.append(professorIds.get(i)+")");
				}else{
					hql.append(professorIds.get(i)+",");
				}
			}
		}
		//根据立题的题目模糊查询
		if(choseDissertation!=null){
			if(choseDissertation.getTittle()!=null&&!"".equals(choseDissertation.getTittle())){
					hql.append(" and cd.tittle like'"+choseDissertation.getTittle()+"'");
			}
			if(choseDissertation.getDirection()!=null&&!"".equals(choseDissertation.getDirection())){
				hql.append(" and cd.direction= '"+choseDissertation.getDirection()+"'");
			}
		}
		//排除当前登陆人已经选的论文
		if(choseDissertationIds!=null&&choseDissertationIds.size()!=0){
			hql.append(" and cd.id not in(");
			for(int i=0;i<choseDissertationIds.size();i++){
				if(i==choseDissertationIds.size()-1){
					hql.append(choseDissertationIds.get(i)+")");
				}
				else{
					hql.append(choseDissertationIds.get(i)+",");
				}
			}
		}
		return choseDissertationDAO.executeQuery(hql.toString(),(currpage-1)*pageSize,pageSize);
	}
	/****************************************************************************
	 * @Description 统计找到当前志愿下未被选的论文数-学生填志愿
	 * @return int
	 * @author 赵晶
	 * @date 2018-01-19
	 ****************************************************************************/
	public int findNoSelectedProfessorListQueryCount(
            List<Integer> professorIds, ChoseDissertation choseDissertation,
            List<Integer> choseDissertationIds) {
		StringBuffer hql=new StringBuffer(" select count(*) from ChoseDissertation cd where 1=1 ");
		//找到属于大纲下所有论文
		if(professorIds!=null&&professorIds.size()!=0){
			hql.append(" and cd.choseProfessor.id in(");
			for(int i=0;i<professorIds.size();i++){
				if(i+1==professorIds.size()){
					hql.append(professorIds.get(i)+")");
				}else{
					hql.append(professorIds.get(i)+",");
				}
			}
		}
		//根据立题的题目模糊查询
		if(choseDissertation!=null){
			if(choseDissertation.getTittle()!=null&&!"".equals(choseDissertation.getTittle())){
					hql.append(" and cd.tittle like'"+choseDissertation.getTittle()+"'");
			}
			if(choseDissertation.getDirection()!=null&&!"".equals(choseDissertation.getDirection())){
				hql.append(" and cd.direction= '"+choseDissertation.getDirection()+"'");
			}
		}
		//排除当前登陆人已经选的论文
		if(choseDissertationIds!=null&&choseDissertationIds.size()!=0){
			hql.append(" and cd.id not in(");
			for(int i=0;i<choseDissertationIds.size();i++){
				if(i==choseDissertationIds.size()-1){
					hql.append(choseDissertationIds.get(i)+")");
				}
				else{
					hql.append(choseDissertationIds.get(i)+",");
				}
			}
		}
		return ((Long)(choseDissertationDAO.executeQuerySingleResult(hql.toString()))).intValue();
	}
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-1-19
	 **************************************************************************/
	public int choseProfessorBatchListForProfessorCount(Integer themeId,
			String username) {
//		StringBuffer sql=new StringBuffer("SELECT cpb.id,ct.the_year,cpb.batch_number,cpb.start_time,cpb.end_time,cpb.chose_record_id,");
//		sql.append("SUM(CASE WHEN(cdr.t_username='"+username+"' and (cu.dissertation_id is null or cu.dissertation_id='' )) THEN 1 ELSE 0 END) AS sum_count,");
		//每个批次下选报该批次的学生(除掉已经有论文的学生)   sum_count(批次中学生学的所有论文中不属于学生的数量)+result_count(批次中学生学的所有论文中已经审核通过的学生)
//		sql.append("SUM(CASE WHEN(cdr.t_username='"+username+"' and (cu.dissertation_id is null or cu.dissertation_id='' )) THEN 1 ELSE 0 END) AS sum_count,");
//		sql.append("SUM(CASE WHEN(cdr.aduit_result=1 AND cdr.t_username='"+username+"') THEN 1 ELSE 0 END) AS result_count ,");
//		sql.append(" cp.final_number,");
//		sql.append(" cp.is_audit,");
//		sql.append(" ct.max_student,");
//		sql.append(" cp.expect_number,");
//		//计算当前批次下学生选的所有论文中不同论文的个数
//		sql.append(" count(DISTINCT CASE WHEN(cdr.t_username='"+username+"' ) THEN cdr.dissertation_id ELSE NULL END)  as dissertation_count,");
//		//计算不同论文中已经有学生的个数
//		sql.append(" count(DISTINCT CASE WHEN (cdr.t_username = '"+username+"' AND (cd.student IS NOT NULL AND cd.student!= '')) THEN cdr.dissertation_id ELSE NULL END) AS have_student_dissertation");
        StringBuffer sql = new StringBuffer("SELECT count(*) from ");
        sql.append("(select cpb.id");
        sql.append(" from chose_theme AS ct");
		sql.append(" JOIN chose_professor_batch AS cpb ON ct.id = cpb.chose_record_id ");
		sql.append(" LEFT JOIN chose_dissertation_record AS cdr ON cdr.batch_id= cpb.id ");
		sql.append(" LEFT JOIN user AS u ON u.username=cdr.student");
		/*sql.append(" LEFT JOIN chose_dissertation AS cd ON cd.id=cdr.dissertation_id");
		sql.append(" LEFT JOIN chose_professor AS cp ON cp.id=cd.professor_id");*/
		sql.append(" LEFT JOIN chose_dissertation AS cd ON cd.id = cdr.dissertation_id");
		sql.append(" LEFT JOIN chose_professor AS cp ON cp.theme_id=ct.id");
		sql.append(" LEFT JOIN chose_user AS cu ON cu.username=cdr.student");
		sql.append(" WHERE cpb.chose_record_id ="+themeId+" and ct.type=2 and cp.username='"+username+"' and cpb.start_time < " + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		sql.append(" GROUP BY cpb.id ");
        sql.append(") a1");
		Query query = entityManager.createNativeQuery(sql.toString());
//		List<Object> lists = query.getResultList();
//		if(lists!=null&&lists.size()!=0){
//			return lists.size();
//		}
//		else{
//			return 0;
//		}
        return ((BigInteger) query.getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 根据导师username和大纲id找到符合条件的批次列表-导师
	 * @return List<Object[]>
	 * @author 赵晶
	 * @param pageSize 
	 * @param currpage 
	 * @date 2018-1-19
	 **************************************************************************/
	public List<Object[]> choseProfessorBatchListForProfessor(Integer themeId,
                                                              String username, Integer currpage, int pageSize) {
		StringBuffer sql=new StringBuffer("SELECT cpb.id,ct.the_year,cpb.batch_number,cpb.start_time,cpb.end_time,cpb.chose_record_id,");
		//每个批次下选报该批次的学生(除掉已经有论文的学生)   sum_count(批次中学生学的所有论文中不属于学生的数量)+result_count(批次中学生学的所有论文中已经审核通过的学生)
		sql.append("SUM(CASE WHEN(cdr.t_username='"+username+"' and (cu.dissertation_id is null or cu.dissertation_id='' )) THEN 1 ELSE 0 END) AS sum_count,");
		sql.append("SUM(CASE WHEN(cdr.aduit_result=1 AND cdr.t_username='"+username+"') THEN 1 ELSE 0 END) AS result_count ,");
		sql.append(" cp.final_number,");
		sql.append(" cp.is_audit,");
		sql.append(" ct.max_student,");
		sql.append(" cp.expect_number,");
		//计算当前批次下学生选的所有论文中不同论文的个数
		sql.append(" count(DISTINCT CASE WHEN(cdr.t_username='"+username+"' ) THEN cdr.dissertation_id ELSE NULL END)  as dissertation_count,");
		//计算不同论文中已经有学生的个数
		sql.append(" count(DISTINCT CASE WHEN (cdr.t_username = '"+username+"' AND (cd.student IS NOT NULL AND cd.student!= '')) THEN cdr.dissertation_id ELSE NULL END) AS have_student_dissertation");
		sql.append(" from chose_theme AS ct");
		sql.append(" JOIN chose_professor_batch AS cpb ON ct.id = cpb.chose_record_id ");
		sql.append(" LEFT JOIN chose_dissertation_record AS cdr ON cdr.batch_id= cpb.id ");
		sql.append(" LEFT JOIN user AS u ON u.username=cdr.student");
		/*sql.append(" LEFT JOIN chose_dissertation AS cd ON cd.id=cdr.dissertation_id");
		sql.append(" LEFT JOIN chose_professor AS cp ON cp.id=cd.professor_id");*/
		sql.append(" LEFT JOIN chose_dissertation AS cd ON cd.id = cdr.dissertation_id");
		sql.append(" LEFT JOIN chose_professor AS cp ON cp.theme_id=ct.id");
		sql.append(" LEFT JOIN chose_user AS cu ON cu.username=cdr.student");
		sql.append(" WHERE cpb.chose_record_id ="+themeId+" and ct.type=2 and cp.username='"+username+"' and cpb.start_time < " + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		sql.append(" GROUP BY cpb.id ");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setFirstResult((currpage-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	/***************************************************************************
	 * @Description 根据导师username和themeId找到大纲下的属于老师的立题列表
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-1-19
	 **************************************************************************/
	public List<ChoseDissertation> findDissertationListByProUsername(
            String username, Integer themeId, Integer currpage, Integer pageSize) {
		String hql="select cd from ChoseDissertation cd where cd.choseProfessor.user.username='"+username+"'";
		hql+=" and cd.choseProfessor.choseTheme.id="+themeId;
		return choseDissertationDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 根据导师username和themeId找到大纲下的属于老师的立题列表记录数
	 * @return int 
	 * @author 赵晶
	 * @date 2018-1-20
	 **************************************************************************/
	public int findDissertationListByProUsernameCount(String username,
				Integer themeId) {
	    String hql="select count(*) from ChoseDissertation cd where cd.choseProfessor.user.username='"+username+"'";
		hql+=" and cd.choseProfessor.choseTheme.id ="+themeId;
		return ((Long)(choseDissertationDAO.executeQuerySingleResult(hql))).intValue();
	}
	/***************************************************************************
	 * @Description 根据大纲的id和导师username找到备选导师
	 * @return ChoseProfessor 
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public ChoseProfessor findChoseProfessorByThemeIdAndTUsername(
            Integer themeId, String username) {
		String hql="select cp form ChoseProfessor cp where cp.choseTheme.id="+themeId;
		hql+=" and cp.user.username='"+username+"'";
		List<ChoseProfessor> list = choseProfessorDAO.executeQuery(hql, 0,-1);
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	/***************************************************************************
	 * @Description 导师审核列表页面
	 * @return List<String> 
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<Object[]> findAduitDissertationList(Integer currpage,
                                                    int pageSize, Integer batchId, String username) {
		StringBuffer sql=new StringBuffer("SELECT cdr.batch_id,cd.tittle,cd.student,count(*) AS select_count,cdr.dissertation_id");
		sql.append(" from chose_dissertation_record cdr");
		sql.append(" JOIN chose_dissertation cd");
		sql.append(" ON cdr.dissertation_id=cd.id");
		sql.append(" JOIN user u ON cdr.student = u.username");
		sql.append(" JOIN chose_user cu ON cdr.student = cu.username");
		sql.append(" WHERE cdr.t_username = '"+username+"'");
		sql.append(" AND cdr.batch_id ="+batchId);
		sql.append(" AND(cu.dissertation_id IS NULL OR cu.dissertation_id = '' OR cdr.aduit_result=1)");
		sql.append(" GROUP BY cd.id");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setFirstResult((currpage-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	/***************************************************************************
	 * @Description 导师审核列表记录数
	 * @return int 
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int findAduitDissertationListCount(Integer batchId, String username) {
		StringBuffer sql=new StringBuffer("select count(*) ");
		sql.append(" from chose_dissertation_record cdr");
		sql.append(" JOIN chose_dissertation cd");
		sql.append(" ON cdr.dissertation_id=cd.id");
		sql.append(" JOIN user u ON cdr.student = u.username");
		sql.append(" JOIN chose_user cu ON cdr.student = cu.username");
		sql.append(" WHERE cdr.t_username = '"+username+"'");
		sql.append(" AND cdr.batch_id ="+batchId);
		sql.append(" AND(cu.dissertation_id IS NULL OR cu.dissertation_id = '' OR cdr.aduit_result=1)");
		sql.append(" GROUP BY cd.id");
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(list!=null&&list.size()!=0){
			return list.size();
		}
		return 0;
	}
	/***************************************************************************
	 * @Description 导师审核学生记录列表
	 * @return List<ChoseDissertationRecord> 
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<ChoseDissertationRecord> findAduitStudentList(Integer currpage,
                                                              int pageSize, Integer batchId, String username, Integer dissertationId) {
		String hql="select cdr from ChoseDissertationRecord cdr where cdr.choseProfessorBatch.id="+batchId;
		hql+=" and cdr.tUsername='"+username+"'";
		hql+=" and (cdr.user.choseUser.choseDissertation.id is null or cdr.user.choseUser.choseDissertation.id='' )";
		hql+=" and cdr.choseDissertation.id="+dissertationId;
		return choseDissertationRecordDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 导师审核没被选上的学生记录列表
	 * @return List<ChoseDissertationRecord> 
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<ChoseDissertationRecord> findNoAduitStudentList(Integer batchId, String username, String name) {
		String hql="select cdr from ChoseDissertationRecord cdr where cdr.choseProfessorBatch.id="+batchId;
		hql+=" and cdr.tUsername='"+username+"'";
		hql+=" and cdr.user.username!='"+name+"'";
		hql+=" and (cdr.user.choseUser.choseDissertation.id is null or cdr.user.choseUser.choseDissertation.id='' )";
		return choseDissertationRecordDAO.executeQuery(hql, 0,-1);
	}
	/***************************************************************************
	 * @Description 导师审核学生记录数
	 * @return List<ChoseDissertationRecord> 
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int findAduitStudentListCount(Integer batchId, String username, Integer dissertationId) {
		String hql="select count(*) from ChoseDissertationRecord cdr where cdr.choseProfessorBatch.id="+batchId;
		hql+=" and cdr.tUsername='"+username+"'";
		hql+=" and (cdr.user.choseUser.choseDissertation.id is null or cdr.user.choseUser.choseDissertation.id='' )";
		hql+=" and cdr.choseDissertation.id="+dissertationId;
		return ((Long)(choseDissertationRecordDAO.executeQuerySingleResult(hql))).intValue();
	}
	/***************************************************************************
	 * @Description 查询没有导师的学生数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int noDissertationStudentListCount(Integer attendanceTime,
                                              Integer currpage, int pageSize) {
		StringBuffer hql=new StringBuffer("select count(*) from User u where (u.choseUser.choseDissertation.id='' or u.choseUser.choseDissertation.id is null) and  ((u.attendanceTime="+attendanceTime);
		hql.append(" and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime='')) ");
		hql.append(" or u.choseUser.realAttendanceTime="+attendanceTime+")");
		hql.append(" and u.userRole=0");
		return ((Long) userDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 查询没有导师的学生记录
	 * @return List<User>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<User> noDissertationStudentList(Integer attendanceTime,
                                                Integer currpage, int pageSize) {
		StringBuffer hql=new StringBuffer("select u from User u where (u.choseUser.choseDissertation.id='' or u.choseUser.choseDissertation.id is null) and  ((u.attendanceTime="+attendanceTime);
		hql.append(" and (u.choseUser.realAttendanceTime is null or u.choseUser.realAttendanceTime='')) ");
		hql.append(" or u.choseUser.realAttendanceTime="+attendanceTime+")");
		hql.append(" and u.userRole=0");
		return userDAO.executeQuery(hql.toString(), (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 查询当前大纲下没有选立题列表
	 * @return List<Object[]>
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public List<Object[]> havePositionDissertationList(String tittle,
                                                       Integer choseThemeId, Integer currpage, int pageSize) {
		StringBuffer sql=new StringBuffer("select ifnull(ct.id,''),ifnull(cd.id,'') ,ifnull(cp.id,''),cd.tittle,u.cname,cp.final_number,cp.expect_number-cp.final_number as optional_number from  chose_theme ct");
		sql.append("  inner JOIN chose_professor cp ON ct.id=cp.theme_id");
		sql.append("  inner JOIN user u ON u.username=cp.username");
		sql.append("  inner JOIN chose_dissertation cd ON cd.professor_id=cp.id");
		sql.append("  WHERE (cd.student is null or cd.student='')");
		sql.append("  and ct.id="+choseThemeId);
		if(tittle!=null&&!"".equals(tittle)){
			sql.append(" and cd.tittle like '%"+tittle+"%'");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setFirstResult((currpage-1)*pageSize);
		query.setMaxResults(pageSize);
		List<Object[]> list = query.getResultList();
		if(list!=null){
			return list;
		}
		return null;
	}
	/***************************************************************************
	 * @Description 查询没有选立题记录数
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int havePositionDissertationListCount(String tittle,
			Integer choseThemeId) {
		StringBuffer sql=new StringBuffer("select count(*) from  chose_theme ct");
		sql.append("  inner JOIN chose_professor cp ON ct.id=cp.theme_id");
		sql.append("  inner JOIN chose_dissertation cd ON cd.professor_id=cp.id");
		sql.append("  WHERE (cd.student is null or cd.student='')");
		sql.append("  and ct.id="+choseThemeId);
		if(tittle!=null&&!"".equals(tittle)){
			sql.append(" and cd.tittle like '%"+tittle+"%'");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		Object object = query.getSingleResult();
		if(object!=null){
			return Integer.parseInt(String.valueOf(object));
		}
		return 0;
	}
	/***************************************************************************
	 * @Description 找到所有的导师结果数量--管理员功能
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-21
     **************************************************************************/
	public int findChoseResultCount(ChoseTheme choseTheme, int themeId){
		StringBuilder hql = new StringBuilder("select count(c) from ChoseProfessor c where 1=1 and c.choseTheme.id="+themeId);
		return ((Long) choseProfessorDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	/***************************************************************************
	 * @Description 找到所有的导师结果列表--管理员功能
	 * @return List<ChoseProfessor>
	 * @author 赵晶
	 * @date 2018-1-22
     **************************************************************************/
	public List<ChoseProfessor> findChoseResultList(ChoseTheme choseTheme, int currpage, int pageSize, int themeId){
		StringBuilder hql = new StringBuilder("select c from ChoseProfessor c where 1=1 and c.choseTheme.id="+themeId);
		return choseProfessorDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/****************************************************************************
	 * @Description 根据批次ids数组和导师id找到某大纲下某导师中所有审核通过的学生
	 * @return List<ChoseProfessorRecord>
	 * @author 赵晶
	 * @date 2017-01-09
	 ****************************************************************************/
	public List<ChoseDissertationRecord> passStudentListForProfessor(
			int[] batchIds, Integer professorId) {
		StringBuffer hql=new StringBuffer("select cdr from ChoseDissertationRecord cdr where 1=1");
		hql.append(" and cdr.choseDissertation.choseProfessor.id="+professorId);
		if(batchIds!=null && batchIds.length != 0){
			hql.append(" and cdr.choseProfessorBatch.id in(");
			for(int i=0;i<batchIds.length;i++){
				if(i==batchIds.length-1){
					hql.append(batchIds[i]+")");
				}
				else{
					hql.append(+batchIds[i]+",");
				}
			}
		}
		hql.append(" and cdr.aduitResult=1");
		List<ChoseDissertationRecord> list = choseDissertationRecordDAO.executeQuery(hql.toString(), 0,-1);
		return list;
	}
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到审核通过的学生名单-导师
	 * @return List<ChoseDisssertationRecord>
	 * @author 赵晶
	 * @date 2017-1-22
	 **************************************************************************/
	public List<ChoseDissertationRecord> studentList(String username,
                                                     Integer batchId, Integer pageSize, Integer currpage){
		String hql="select cdr from ChoseDissertationRecord cdr where cdr.tUsername='"+username+"'";
		hql+=" and cdr.choseProfessorBatch.id="+batchId;
		hql+=" and cdr.currAduit=2";//审核
		hql+=" and cdr.aduitResult=1";//同意
		return choseDissertationRecordDAO.executeQuery(hql,(currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 根据导师tname和批次id找到审核通过的学生名单-导师
	 * @return int
	 * @author 赵晶
	 * @date 2017-1-22
	 **************************************************************************/
	public int studentListCount(String username, Integer batchId) {
		String hql="select count(*) from ChoseDissertationRecord cdr where cdr.tUsername='"+username+"'";
		hql+=" and cdr.choseProfessorBatch.id="+batchId;
		hql+=" and cdr.currAduit=2";//审核
		hql+=" and cdr.aduitResult=1";//同意
		return ((Long)(choseDissertationRecordDAO.executeQuerySingleResult(hql))).intValue();
	}
	/***************************************************************************
	 * @Description 保存毕业论文，返回存放的相对路径
	 * @return String
	 * @author 赵晶
	 * @date 2018-1-23
	 **************************************************************************/
	public List<String> uploadDessistationForYear(HttpServletRequest request, HttpServletResponse response){
		
		/*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request; 
		 ArrayList<String> addressAndName = new ArrayList<String>();
		 String sep = System.getProperty("file.separator"); 
		 Map files = multipartRequest.getFileMap(); 
		 Iterator fileNames = multipartRequest.getFileNames();
		 String fileNewName = "";
		 boolean flag =false; 
		 String fileDir = request.getSession().getServletContext().getRealPath( "/") +  "upload"+ sep+"choseDissertation"+sep+id;
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
		}*/
		
		return null;
	}
	/***************************************************************************
	 * @Description 保存毕业论文对应的commonDocument
	 * @return CommonDocument
	 * @author 赵晶
	 * @date 2018-1-23
	 **************************************************************************/
	public CommonDocument saveDessitationDocument(List<String> saveFileAddressName) {
		CommonDocument document = new CommonDocument();
		document.setDocumentName(saveFileAddressName.get(1));
		document.setDocumentUrl(saveFileAddressName.get(0));
		document.setType(2);
		document.setCreatedAt(Calendar.getInstance());
		//设置上传人信息
		document.setUser(shareService.getUserDetail());
		document = commonDocumentDAO.store(document);
		commonDocumentDAO.flush();
		return document;
	}
	/***************************************************************************
	 * @Description 审核通过的记录-导师
	 * @return List<ChoseDissertationRecord>
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public List<ChoseDissertationRecord> findMyAduitPassDissertationRecords(
            String username, Integer currpage, Integer pageSize) {
		String hql="select cdr from ChoseDissertationRecord cdr where cdr.aduitResult=1 and cdr.tUsername='"+username+"'";
		return choseDissertationRecordDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 审核通过的记录数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-24
	 **************************************************************************/
	public int findMyAduitPassDissertationRecordCount(String username) {
			String hql="select count(*) from ChoseDissertationRecord cdr where cdr.aduitResult=1 and cdr.tUsername='"+username+"'";
			return ((Long)(choseDissertationRecordDAO.executeQuerySingleResult(hql))).intValue();
	}
	/***************************************************************************
	 * @Description 根据导师id找到所有的立题列表-导师
	 * @return List<ChoseDissertation>
	 * @author 赵晶
	 * @date 2018-1-29
	 **************************************************************************/
	public List<ChoseDissertation> findChoseDissertationByProfessorId(
            Integer id, Integer currpage, int pageSize) {
		String hql="select cd from ChoseDissertation cd where cd.choseProfessor.id="+id;
		return choseDissertationDAO.executeQuery(hql, (currpage-1)*pageSize,pageSize);
	}
	/***************************************************************************
	 * @Description 根据导师id找到所有的立题记录数-导师
	 * @return int
	 * @author 赵晶
	 * @date 2018-1-29
	 **************************************************************************/
	public int findChoseDissertationByProfessorIdCount(Integer id) {
		String hql="select count(*) from ChoseDissertation cd where cd.choseProfessor.id="+id;
		return((Long)(choseDissertationDAO.executeQuerySingleResult(hql))).intValue();
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
		String hql="select cpb from ChoseProfessorBatch cpb where cpb.choseTheme.type=2 and cpb.choseTheme.state=1 and cpb.startTime<'"+time+"'";
		hql+=" and cpb.endTime>'"+time+"'";
		List<ChoseProfessorBatch> list = choseProfessorBatchDAO.executeQuery(hql, 0,-1);
		if(list!=null&list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	/****************************************************************************
	 * @Description 根据届找到未被选人数
	 * @return int
	 * @author 赵晶
	 * @date 2017-01-29
	 ****************************************************************************/
	public int findNoSelectedNumberBythemeId(Integer theYear, Integer themeId) {
		StringBuffer hql=new StringBuffer("SELECT count(*)");
		hql.append(" from User u ");
		hql.append(" WHERE (u.choseUser.choseDissertation.id=''");
		hql.append(" OR u.choseUser.choseDissertation.id is null) ");
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
}