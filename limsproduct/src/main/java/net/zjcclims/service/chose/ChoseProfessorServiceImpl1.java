package net.zjcclims.service.chose;

import net.zjcclims.dao.*;
import net.zjcclims.domain.ChoseAttention;
import net.zjcclims.domain.ChoseTheme;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Service("ChoseProfessorService1")
public class ChoseProfessorServiceImpl1 implements ChoseProfessorService1 {
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
    private ChoseThemeDAO choseThemeDAO;
	@Autowired
    private ChoseAttentionDAO choseAttentionDAO;
	
	/*
	 * Instantiates a new ShareServiceImpl.
	 */
	public ChoseProfessorServiceImpl1() {
	}
	
	/*
	 * 找到所有的导师互选--管理员功能
	 * 作者：屈晓瑞
	 * 时间：2017-12-1
	@Override
	public List<ChoseTheme> findChoseThemes(ChoseTheme choseTheme,int currpage, int pageSize) {
		StringBuilder hql = new StringBuilder("select c from ChoseTheme c where 1=1");
		return choseThemeDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}*/
	/*
	 * 1、找到所有的导师互选——注意事项数量；2、查询后的数量
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	@Override
	public int findChoseAttentionsCount(ChoseAttention choseAttention) {
		/*StringBuilder sql = new StringBuilder("select count(*) from view_chose_theme v where 1=1");
		Query query = entityManager.createNativeQuery(sql.toString());
		int count= ((BigInteger)query.getSingleResult()).intValue();
		return count;*/
		StringBuilder hql = new StringBuilder("select count(c) from ChoseAttention c where 1=1");
		if(choseAttention.getChoseTheme()!=null){

				//大纲条件
				if(choseAttention.getChoseTheme().getTittle()!=null && !"".equals(choseAttention.getChoseTheme().getTittle()))
				{
					hql.append(" and c.choseTheme.tittle like '%"+choseAttention.getChoseTheme().getTittle()+"%'");
				}
		//名称条件
				if(choseAttention.getTittle()!=null && !"".equals(choseAttention.getTittle()))
				{
					hql.append(" and c.tittle like '%"+choseAttention.getTittle()+"%'");
				}
		}
		return ((Long) choseAttentionDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}
	
	/*
	 * 找到所有的导师互选--管理员功能
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	@Override
	public List<ChoseAttention> findChoseAttentions(ChoseAttention choseAttention, int currpage, int pageSize) {
		/*StringBuilder sql = new StringBuilder("select * from view_chose_theme v where 1=1");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setFirstResult((currpage-1)*pagesize);
		query.setMaxResults(pagesize);
        List<Object[]> list= query.getResultList();*/
		StringBuilder hql = new StringBuilder("select c from ChoseAttention c where 1=1");
		if(choseAttention.getChoseTheme()!=null){
		//大纲名称条件
				if(choseAttention.getChoseTheme().getTittle()!=null && !"".equals(choseAttention.getChoseTheme().getTittle()))
				{
					hql.append(" and c.choseTheme.tittle like '%"+choseAttention.getChoseTheme().getTittle()+"%'");
				}
		//名称条件
				if(choseAttention.getTittle()!=null && !"".equals(choseAttention.getTittle()))
				{
					hql.append(" and c.tittle like '%"+choseAttention.getTittle()+"%'");
				}
		}
		return choseAttentionDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}
	/**
	 * 获取所有的学期数据
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	@Override
	public List<ChoseTheme> findAllChoseTheme(){
		StringBuffer hql = new StringBuffer("select c from ChoseTheme c ");
		hql.append(" order by c.id desc");
		
		return choseThemeDAO.executeQuery(hql.toString(), 0, -1);
	}
	@Override
	public List<ChoseAttention> findAllChoseAttention(){
		String sql="select e from ChoseAttention e where 1=1";//where s.academyNumber like '02__'  2015.10.02贺子龙
		List<ChoseAttention> list=new ArrayList<ChoseAttention>();
		List<ChoseAttention> s=choseAttentionDAO.executeQuery(sql, 0,-1);
		for (ChoseAttention a:s) {
			if(list.size()>100){//11
				break;
			}
			list.add(a);
		}
		return list;
		
		
	}
	/*
	 * 删除：导师互选——注意事项
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	@Override
	public boolean deleteChoseAttention(Integer choseAttentionId) {
		ChoseAttention choseAttention = choseAttentionDAO.findChoseAttentionByPrimaryKey(choseAttentionId);
		if(choseAttention!=null)
		{
			choseAttentionDAO.remove(choseAttention);
			choseAttentionDAO.flush();
			return true;
		}
		
		return false;
	}
	/**
	 * 根据实验中心主键查找实验中心对象
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 */
	@Override
	public ChoseAttention findChoseAttentionByPrimaryKey(Integer choseAttentionId) {
		return choseAttentionDAO.findChoseAttentionByPrimaryKey(choseAttentionId);
	}
/*	*//**
	 * 保存数据
	 * 作者：屈晓瑞
	 * 时间：2017-12-7
	 *//*
	@Override
	public ChoseAttention saveChoseAttention(ChoseAttention choseAttention) {
		if(choseAttention.getUser()==null || choseAttention.getUser().getUsername()==null)
		{
			choseAttention.setUser(null);  //实验中心主任
		}
		if(choseAttention.getSystemCampus()==null || choseAttention.getSystemCampus().getCampusNumber()==null)
		{
			choseAttention.setSystemCampus(null);  //所属校区
		}
		if(choseAttention.getSchoolAcademy()==null || choseAttention.getSchoolAcademy().getAcademyNumber()==null)
		{
			choseAttention.setSchoolAcademy(null);  //所属学院
		}
		if(choseAttention.getSystemBuild()==null || choseAttention.getSystemBuild().getBuildNumber()==null)
		{
			choseAttention.setSystemBuild(null);  //所属楼宇
		}
		return choseAttentionDAO.store(choseAttention);
	}*/

	@Override
	public ChoseAttention saveChoseAttention(ChoseAttention choseAttention) {
		return choseAttentionDAO.store(choseAttention);
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

	
}