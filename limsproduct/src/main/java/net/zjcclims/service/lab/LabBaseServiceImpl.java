package net.zjcclims.service.lab;

import net.zjcclims.dao.LabAnnexDAO;
import net.zjcclims.dao.LabRoomDAO;
import net.zjcclims.domain.CDictionary;
import net.zjcclims.domain.LabAnnex;
import net.zjcclims.domain.LabRoom;
import net.zjcclims.service.EmptyUtil;
import net.zjcclims.service.common.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("LabBaseService")
public class LabBaseServiceImpl implements LabBaseService {
	
	@Autowired
	private ShareService shareService;
	@Autowired
	private LabAnnexDAO labAnnexDAO;
	@Autowired
	private LabRoomDAO labRoomDAO;
	/***************************** 
	*Description 根据基地主键查找基地对象
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@Override
	public LabAnnex findLabAnnexByPrimaryKey(Integer labBaseId) {
		return labAnnexDAO.findLabAnnexByPrimaryKey(labBaseId);
	}

	/**
	*Description 获取所有的基地
	*@author 陈乐为 2018-8-6
	*/
	@Override
	public List<LabAnnex> findAllLabBaseByQuery(LabAnnex labAnnex, Integer currpage, Integer pageSize, HttpServletRequest request) {
		StringBuffer hql = new StringBuffer("select l from LabAnnex l where 1=1 ");
		hql.append(" and l.CDictionaryByLabAnnex.CNumber=4");// 实验基地
		if(!EmptyUtil.isObjectEmpty(labAnnex) && !EmptyUtil.isStringEmpty(labAnnex.getLabName()))
		{
			hql.append(" and l.labName like '%"+labAnnex.getLabName()+"%'");
		}
		// 获取当前系统权限
		String auth = request.getSession().getAttribute("selected_role").toString();
		// 根据权限等级筛选
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==5) {
			hql.append(" and l.labCenter.userByCenterManager.username='"+ shareService.getUserDetail().getUsername() +"'");
		}else if(authLevel==3 || authLevel==4) {
			hql.append(" and l.labCenter.schoolAcademy.academyNumber='"+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() +"'");
		}
		
		return labAnnexDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/**
	 * Description 实验基地总数
	 * @param labAnnex
	 * @return
	 * @author 陈乐为 2018-8-6
	 */
	@Override
	public int getAllLabBaseByQueryCount(LabAnnex labAnnex, HttpServletRequest request) {
		StringBuffer hql = new StringBuffer("select count(l) from LabAnnex l where 1=1 ");
		hql.append(" and l.CDictionaryByLabAnnex.CNumber=4");// 实验基地
		if(!EmptyUtil.isObjectEmpty(labAnnex) && !EmptyUtil.isStringEmpty(labAnnex.getLabName()))
		{
			hql.append(" and l.labName like '%"+labAnnex.getLabName()+"%'");
		}
		// 获取当前系统权限
		String auth = request.getSession().getAttribute("selected_role").toString();
		// 根据权限等级筛选
		int authLevel = shareService.getLevelByAuthName(auth);
		if(authLevel==5) {
			hql.append(" and l.labCenter.userByCenterManager.username='"+ shareService.getUserDetail().getUsername() +"'");
		}else if(authLevel==3 || authLevel==4) {
			hql.append(" and l.labCenter.schoolAcademy.academyNumber='"+ shareService.getUserDetail().getSchoolAcademy().getAcademyNumber() +"'");
		}

		return ((Long) labAnnexDAO.createQuerySingleResult(hql.toString()).getSingleResult()).intValue();
	}


	/***************************** 
	*Description 保存基地数据
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@Override
	public LabAnnex saveLabAnnex(LabAnnex labAnnex,HttpServletRequest request) {
		CDictionary cDictionary = shareService.getCDictionaryByCategory("c_lab_annex_type","4");
		labAnnex.setCDictionaryByLabAnnex(cDictionary);
		String academyNumber=request.getParameter("academy");
		if(academyNumber!=null&&!"".equals(academyNumber)) {
			labAnnex.setBelongDepartment(academyNumber);
		}
		return labAnnexDAO.store(labAnnex);
	}

	/**
	 * Description 删除基地
	 * @author 陈乐为 2018-8-6
	 */
	@Override
	public boolean deleteLabAnnex(Integer labBaseId) {
		LabAnnex labAnnex = labAnnexDAO.findLabAnnexByPrimaryKey(labBaseId);
		if(!EmptyUtil.isObjectEmpty(labAnnex))
		{
			labAnnexDAO.remove(labAnnex);
			labAnnexDAO.flush();
			return true;
		}
		
		return false;
	}
	/**
	 * Description 基地下实验室数
	 * @param labAnnex
	 * @return
	 * @author 廖文辉 2018-11-15
	 */
	@Override
	public int findLabRoomByQueryCount(LabAnnex labAnnex) {
		// TODO Auto-generated method stub
		String sql="Select count(c) from LabRoom c where 1=1";
		sql += " and c.labAnnex.id=" + labAnnex.getId();
		sql +=" and (c.isUsed=1 or c.isUsed=null)";
		sql +=" and c.labCategory=1";
		return ((Long) labRoomDAO.createQuerySingleResult(sql).getSingleResult()).intValue();
	}
	/**
	 * Description 基地下实验室
	 * @param labAnnex
	 * @param page
	 *  @param pageSize
	 * @return
	 * @author 廖文辉 2018-11-15
	 */
	@Override
	public List<LabRoom> findLabRoomByQuery(LabAnnex labAnnex, int page,
											   int pageSize)
	{
		// TODO Auto-generated method stub
		String sql="Select c from LabRoom c where 1=1";
		sql += " and c.labBase.id=" + labAnnex.getId();
		//取所有实验室包含不可用
//		sql += " and (c.isUsed=1 or c.isUsed=null)";
		sql += " and c.labCategory=1";
		return labRoomDAO.executeQuery(sql,(page-1)*pageSize,pageSize);
	}
}
