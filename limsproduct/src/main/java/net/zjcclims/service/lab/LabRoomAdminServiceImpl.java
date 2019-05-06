package net.zjcclims.service.lab;

import java.util.List;

import net.zjcclims.dao.LabRoomAdminDAO;
import net.zjcclims.domain.LabRoomAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LabRoomAdminService")
public class LabRoomAdminServiceImpl implements LabRoomAdminService {
	@Autowired
	private LabRoomAdminDAO labRoomAdminDAO;
	/**
	 * 根据LabRoomId查询实验室管理员
	 * 周志辉
	 * 2017-09-20
	 */
	public List<LabRoomAdmin> findAllLabRoomAdminsByLabRoomId(int labRoomId){
		String sql="select l from LabRoomAdmin l where 1=1";
		sql+=" and l.labRoom.id="+labRoomId;
		sql+=" and l.typeId=1";
		return labRoomAdminDAO.executeQuery(sql,0,-1);
	}
	
	public List<LabRoomAdmin> findLabRoomAdminForType3ByLabRoomId(int labRoomId){
		String sql="select l from LabRoomAdmin l where 1=1";
		sql+=" and l.labRoom.id="+labRoomId;
		sql+=" and l.typeId=3";
		return labRoomAdminDAO.executeQuery(sql,0,-1);
	}

	/**
	 * Description 通过用户名查找所管理的实验室
	 * @param username 用户名
	 * @param typeId 实验室管理员类型
	 * @return 该用户所管理的实验室
	 * @author 黄保钱 2018-08-22
	 */
	@Override
	public List findLabRoomAdminForByUsernameAndType(String username, int typeId){
		String sql="select l from LabRoomAdmin l where 1=1";
		sql+=" and l.user.username like '"+username;
		sql+="' and l.typeId="+typeId;
		return labRoomAdminDAO.createQuery(sql,-1,-1).getResultList();
	}
}
