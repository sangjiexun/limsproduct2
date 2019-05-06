package net.zjcclims.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.zjcclims.dao.SchoolAcademyDAO;
import net.zjcclims.dao.SystemRoomDAO;
import net.zjcclims.domain.SchoolAcademy;

@Service("SchoolAcademyService")
public class SchoolAcademyServiceImpl implements SchoolAcademyService {

	/**
	 * 获取所有的系统房间
	 * @author 周志辉
	 * 2017.07.26
	 */
	@Autowired
	private SchoolAcademyDAO schoolAcademyDAO;
	@Override
	public List<SchoolAcademy> findAllSchoolAcademyByQuery(
			SchoolAcademy schoolAcademy, Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select l from SchoolAcademy l where 1=1 ");
		if(schoolAcademy.getAcademyNumber() != null)
		{
			hql.append(" and l.academyNumber="+schoolAcademy.getAcademyNumber());
		}
		if(schoolAcademy.getAcademyName()!=null && !"".equals(schoolAcademy.getAcademyName()))
		{
			hql.append(" and l.academyName like '%"+schoolAcademy.getAcademyName()+"%'");
		}
		
		return schoolAcademyDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

}
