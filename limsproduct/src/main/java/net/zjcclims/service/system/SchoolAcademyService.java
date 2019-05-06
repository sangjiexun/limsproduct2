package net.zjcclims.service.system;

import java.util.List;

import net.zjcclims.domain.SchoolAcademy;
import net.zjcclims.domain.SystemRoom;

public interface SchoolAcademyService {
	/**
	 * 获取所有的系统房间
	 * @author 周志辉
	 * 2017.07.26
	 */
	public List<SchoolAcademy> findAllSchoolAcademyByQuery(SchoolAcademy schoolAcademy, Integer currpage, Integer pageSize);
}
