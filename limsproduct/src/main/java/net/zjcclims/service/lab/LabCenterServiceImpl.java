package net.zjcclims.service.lab;

import net.zjcclims.dao.LabCenterDAO;
import net.zjcclims.dao.LabRoomAdminDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.dao.LabRoomDeviceDAO;
import net.zjcclims.domain.*;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.web.common.PConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service("LabCenterService")
public class LabCenterServiceImpl implements LabCenterService {
	
	@Autowired
	private LabCenterDAO labCenterDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	@Autowired
	private LabRoomAdminDAO labRoomAdminDAO;
	@Autowired
	private LabRoomDeviceDAO labRoomDeviceDAO;
	@Autowired
	private ShareService shareService;
	@Autowired
	private PConfig pConfig;
	/**
	 * 根据实验中心主键查找实验中心对象
	 * @author hly
	 * 2015.07.24
	 */
	@Override
	public LabCenter findLabCenterByPrimaryKey(Integer labCenterId) {
		return labCenterDAO.findLabCenterByPrimaryKey(labCenterId);
	}

	/**
	 * 获取所有的实验中心
	 * @author hly
	 * 2015.07.24
	 */
	@Override
	public List<LabCenter> findAllLabCenterByQuery(HttpServletRequest request, LabCenter labCenter, Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select l from LabCenter l where 1=1 ");
		if(labCenter.getId() != null && labCenter.getId() != -1)
		{
			hql.append(" and l.id="+labCenter.getId());
		}
		if(labCenter.getCenterName()!=null && !"".equals(labCenter.getCenterName()))
		{
			hql.append(" and l.centerName like '%"+labCenter.getCenterName()+"%'");
		}
		if(!EmptyUtil.isIntegerEmpty(labCenter.getStatus())) {
			hql.append(" and l.status = " + labCenter.getStatus());
		}
		// 获取当前系统权限
		String auth = request.getSession().getAttribute("selected_role").toString();
		// 根据权限等级筛选
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==5) {// 中心级
			hql.append(" and l.userByCenterManager.username='"+ shareService.getUserDetail().getUsername() +"'");
		}else if(authLevel==3 || authLevel==4 || authLevel==6) {// 院级
			if(labCenter!=null&&labCenter.getSchoolAcademy()!=null&&labCenter.getSchoolAcademy().getAcademyNumber()!=null) {
				hql.append(" and l.schoolAcademy.academyNumber='" + labCenter.getSchoolAcademy().getAcademyNumber() + "'");
			}
		}
//		if(!EmptyUtil.isObjectEmpty(labCenter.getSchoolAcademy()) && !EmptyUtil.isStringEmpty(labCenter.getSchoolAcademy().getAcademyNumber())) {
//			hql.append(" and l.schoolAcademy.academyNumber='"+ labCenter.getSchoolAcademy().getAcademyNumber() +"'");
//		}
		
		return labCenterDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * Description 中心数
	 * @param labCenter
	 * @return
	 */
	@Override
	public int findAllLabCenterByQueryCount(LabCenter labCenter, HttpServletRequest request) {
		StringBuffer hql = new StringBuffer("select count(l) from LabCenter l where 1=1 ");
		if(labCenter.getId() != null && labCenter.getId() != -1)
		{
			hql.append(" and l.id="+labCenter.getId());
		}
		if(labCenter.getCenterName()!=null && !"".equals(labCenter.getCenterName()))
		{
			hql.append(" and l.centerName like '%"+labCenter.getCenterName()+"%'");
		}
		if(!EmptyUtil.isIntegerEmpty(labCenter.getStatus())) {
			hql.append(" and l.status = " + labCenter.getStatus());
		}
		// 获取当前系统权限
		String auth = request.getSession().getAttribute("selected_role").toString();
		// 根据权限等级筛选
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==5) {
			hql.append(" and l.userByCenterManager.username='"+ shareService.getUserDetail().getUsername() +"'");
		}else if(authLevel==3 || authLevel==4) {
			hql.append(" and l.schoolAcademy.academyNumber='"+ labCenter.getSchoolAcademy().getAcademyNumber() +"'");
		}

		return ((Long) labCenterDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * 保存实验中心数据
	 * @author hly
	 * 2015.07.27
	 */
	@Override
	public LabCenter saveLabCenter(LabCenter labCenter) {
		if(labCenter.getUserByCenterManager()==null || labCenter.getUserByCenterManager().getUsername()==null)
		{
			labCenter.setUserByCenterManager(null);  //实验中心主任
		}
		if(labCenter.getSystemCampus()==null || labCenter.getSystemCampus().getCampusNumber()==null)
		{
			labCenter.setSystemCampus(null);  //所属校区
		}
		if(labCenter.getSchoolAcademy()==null || labCenter.getSchoolAcademy().getAcademyNumber()==null)
		{
			labCenter.setSchoolAcademy(null);  //所属学院
		}
		if(labCenter.getSystemBuild()==null || labCenter.getSystemBuild().getBuildNumber()==null)
		{
			labCenter.setSystemBuild(null);  //所属楼宇
		}
		return labCenterDAO.store(labCenter);
	}

	/**
	 * 删除实验中心
	 * @author hly
	 * 2015.07.27
	 */
	@Override
	public boolean deleteLabCenter(Integer labCenterId) {
		LabCenter labCenter = labCenterDAO.findLabCenterByPrimaryKey(labCenterId);
		if(labCenter!=null)
		{
			labCenterDAO.remove(labCenter);
			labCenterDAO.flush();
			return true;
		}
		
		return false;
	}

	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<LabCenter> findAllLabCenterByLabCenter(LabCenter labCenter) {
		// TODO Auto-generated method stub
		String sql="select c from LabCenter c where 1=1";
		if(labCenter.getCenterName()!=null&&!labCenter.getCenterName().equals("")){
			sql+=" and c.centerName like '%"+labCenter.getCenterName()+"%'";
		}
		//超级管理员和教务
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1){
			
		}else if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){
			
		}else{
			User user=shareService.getUser();
			if(user.getSchoolAcademy()!=null){
				if(user.getSchoolAcademy().getAcademyNumber()!=null&&!user.getSchoolAcademy().getAcademyNumber().equals("")){
					sql+=" and c.schoolAcademy.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"' ";
				}
			}
			
		}				
		sql+=" order by c.updatedAt desc";
		return labCenterDAO.executeQuery(sql,0,-1);
	}
	
	/****************************************************************************
	 * 功能：根据实验中心信息查询实验中心并分页
	 * 作者：李小龙
	 ****************************************************************************/
	@Override
	public List<LabCenter> findAllLabCenterByLabCenter(LabCenter labCenter,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String sql="select c from LabCenter c where 1=1";
		if(labCenter.getCenterName()!=null&&!labCenter.getCenterName().equals("")){
			sql+=" and c.centerName like '%"+labCenter.getCenterName()+"%'";
		}
		//超级管理员和教务
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_SUPERADMIN") != -1){
			
		}else if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().indexOf("ROLE_EXPERIMENTALTEACHING") != -1){
			
		}else{
			User user=shareService.getUser();
			if(user.getSchoolAcademy()!=null){
				if(user.getSchoolAcademy().getAcademyNumber()!=null&&!user.getSchoolAcademy().getAcademyNumber().equals("")){
					sql+=" and c.schoolAcademy.academyNumber='"+user.getSchoolAcademy().getAcademyNumber()+"' ";
				}
			}
			
		}		
		sql+=" order by c.updatedAt desc";
		return labCenterDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
	
	/**
	 * 统计一个实训室中心面积
	 * @author 周志辉
	 * 2017.07.27
	 */
	
	@SuppressWarnings("null")
	@Override
	public BigDecimal countAllLabRoomAreaByQuery(LabCenter labCenter) {
		// TODO Auto-generated method stub
//		BigDecimal area = new BigDecimal("0.0");
		BigDecimal areas = new BigDecimal("0.0");
//		String sql="Select c from LabRoom c where 1=1";
//		sql+=" and c.labCenter.id="+labCenter.getId();
//		List<LabRoom> labRoom=new ArrayList<LabRoom>(labRoomDAO.executeQuery(sql,0,-1));//所有实验室
//		int length=labRoom.size();
//		for(int i=0;i<length;i++)
//		{
//			if(labRoom.get(i).getLabRoomArea()!=null)
//			{
//				area=labRoom.get(i).getLabRoomArea();
//				areas=areas.add(area);
//			}
//		}
		String sql="Select sum(c.labRoomArea) from LabRoom c where 1=1";
		sql+=" and c.labCenter.id="+labCenter.getId();
		areas = (BigDecimal) labRoomDAO.createQuery(sql,0, -1).getResultList().get(0);
		return areas;
	}
	
	/**
	 * 统计一个实训中心管理员数量
	 * @author 周志辉
	 * 2017.07.28
	 */
	@Override
	public List<LabRoomAdmin> findAllLabRoomAdminByLabCenter(LabCenter labCenter){
		StringBuffer hql = new StringBuffer("select c from LabRoomAdmin c where 1=1");
		hql.append(" and c.labRoom.labCenter.id="+labCenter.getId());
		hql.append(" order by c.user,username,c.labRoom.labRoomNumber");
		return labRoomAdminDAO.executeQuery(hql.toString(), 0, -1);
	}
	/**
	 * 统计实训中心实训室数量
	 * @author 周志辉
	 * 2017.07.28
	 */
	@Override
	public List<LabRoom> findAllLabRoomByQuery(LabCenter labCenter, int page,
			int pageSize) 
	{
		// TODO Auto-generated method stub
		String sql="Select c from LabRoom c where 1=1";
		sql += " and c.labCenter.id=" + labCenter.getId();
		sql += " and (c.isUsed=1 or c.isUsed=null)";
		sql += " and c.labCategory=1";
		return labRoomDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}

	/**
	 * 统计每个实训中心的设备列表
	 * 周志辉
	 */
	@Override
	public List<LabRoomDevice> findAllLabRoomDeviceByQuery(LabCenter labCenter, int currpage, int pageSize) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select c from LabRoomDevice c where 1=1");
		hql.append(" and c.labRoom.labCenter.id=" + labCenter.getId());

		return labRoomDeviceDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * 求和每个实训中心的设备列表里的设备价格
	 * 黄保钱
	 */
	@Override
	public List sumAllLabRoomDeviceByQuery(LabCenter labCenter, int page, int pageSize) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select sum(c.schoolDevice.devicePrice) from LabRoomDevice c where 1=1");
		hql.append(" and c.labRoom.labCenter.id=" + labCenter.getId());

		Query query = labRoomDeviceDAO.createQuery(hql.toString(), (page-1)*pageSize, pageSize);
		return query.getResultList();
	}

	/**
	 * Description 所有实验中心
	 * @return
	 * @author 陈乐为 2018-7-23
	 */
	public List<LabCenter> findAllCenters(){
		String sql = "select l from LabCenter l where 1=1 order by l.id";
		List<LabCenter> centerList = labCenterDAO.executeQuery(sql, 0, -1);
		return centerList;
	}

	/**
	 * Description 中心设备数量
	 * @param labCenter
	 * @return
	 * @author 陈乐为 2018-8-7
	 */
	@Override
	public int findAllLabRoomDeviceByQueryCount(LabCenter labCenter) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("select count(c) from LabRoomDevice c where 1=1");
		hql.append(" and c.labRoom.labCenter.id=" + labCenter.getId());

		return ((Long) labRoomDeviceDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * Description 中心实验室管理员数
	 * @param labCenter
	 * @return
	 * @author 陈乐为 2018-8-7
	 */
	@Override
	public int findAllLabRoomAdminByLabCenterCount(LabCenter labCenter){
		StringBuffer hql = new StringBuffer("select count(distinct c.user.username) from LabRoomAdmin c where 1=1");
		hql.append(" and c.labRoom.labCenter.id="+labCenter.getId());
		return ((Long) labRoomAdminDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}

	/**
	 * Description 中心下实验室数
	 * @param labCenter
	 * @return
	 * @author 陈乐为 2018-8-7
	 */
	@Override
	public int findAllLabRoomByQueryCount(LabCenter labCenter) {
		// TODO Auto-generated method stub
		String sql="Select count(c) from LabRoom c where 1=1";
		sql += " and c.labCenter.id=" + labCenter.getId();
		sql +=" and (c.isUsed=1 or c.isUsed=null)";
		sql +=" and c.labCategory=1";
		return ((Long) labRoomDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}

	/**
	 * Description 按实验中心主任查找实验中心
	 * @param username 实验中心主任
	 * @return 该实验中心主任所管理的实验中心
	 * @author 黄保钱 2018-08-22
	 */
	@Override
	public List findAllLabRoomByAdmin(String username){
		String sql = "select l from LabCenter l where 1=1";
		sql += " and l.userByCenterManager.username like '" + username + "'";
		return labCenterDAO.createQuery(sql, -1, -1).getResultList();
	}

	/**
	 * Description 根据学院查找所有实验中心
	 * @param academyNumber 学院id
	 * @return 该学院下所有实验中心
	 * @author 刘博越 2019-03-26
	 */
	@Override
	public List<LabCenter> findAllLabCenterByAcademy(String academyNumber) {
		String sql = "select l from LabCenter l where 1=1";
		sql += " and l.schoolAcademy.academyNumber ='" + academyNumber + "'";
		List<LabCenter> centerList = labCenterDAO.executeQuery(sql, 0, -1);
		return centerList;
	}

}
