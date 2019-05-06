package net.zjcclims.service.lab;

import java.util.List;

import net.zjcclims.dao.LabWorkRoomDAO;
import net.zjcclims.domain.LabWorkRoom;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("LabWorkRoomService")
public class LabWorkRoomServiceImpl implements LabWorkRoomService {
	
	@Autowired
	private LabWorkRoomDAO labWorkRoomDAO;
	@Autowired
	private ShareService shareService;

	/***************************** 
	*Description 根据工作室主键查找工作室对象
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@Override
	public LabWorkRoom findLabWorkRoomByPrimaryKey(Integer labWorkRoomId) {
		return labWorkRoomDAO.findLabWorkRoomByPrimaryKey(labWorkRoomId);
	}

	/***************************** 
	*Description 获取所有的工作室
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@Override
	public List<LabWorkRoom> findAllLabWorkRoomByQuery(LabWorkRoom labWorkRoom, Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select l from LabWorkRoom l where 1=1 ");
		if(labWorkRoom.getId()!=null)
		{
			hql.append(" and l.id="+labWorkRoom.getId());
		}
		if(labWorkRoom.getWorkRoomName()!=null && !"".equals(labWorkRoom.getWorkRoomName()))
		{
			hql.append(" and l.workRoomName like '%"+labWorkRoom.getWorkRoomName()+"%'");
		}
		
		return labWorkRoomDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);
	}

	/***************************** 
	*Description 保存工作室信息
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@Override
	public LabWorkRoom saveLabWorkRoom(LabWorkRoom labWorkRoom) {
		return labWorkRoomDAO.store(labWorkRoom);
	}

	/***************************** 
	*Description 删除工作室信息
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	@Override
	public boolean deleteLabWorkRoom(Integer labWorkRoomId) {
		LabWorkRoom labWorkRoom = labWorkRoomDAO.findLabWorkRoomByPrimaryKey(labWorkRoomId);
		if(labWorkRoom!=null)
		{
			labWorkRoomDAO.remove(labWorkRoom);
			labWorkRoomDAO.flush();
			return true;
		}
		
		return false;
	}
}
