package net.zjcclims.service.lab;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.zjcclims.dao.SystemRoomDAO;
import net.zjcclims.domain.SystemRoom;
import net.zjcclims.service.system.SystemRoomService;


@Service("SystemRoomService")
public class SystemRoomServiceImpl implements SystemRoomService {

	/**
	 * 获取所有的系统房间
	 * @author 周志辉
	 * 2017.07.26
	 */
	@Autowired
	private SystemRoomDAO systemRoomDAO;
	@Override
	public List<SystemRoom> findAllSystemRoomByQuery(SystemRoom systemRoom,
			Integer currpage, Integer pageSize) {
		StringBuffer hql = new StringBuffer("select l from SystemRoom l where 1=1 ");
		if(systemRoom.getRoomNumber() != null)
		{
			hql.append(" and l.roomNumber="+systemRoom.getRoomNumber());
		}
		if(systemRoom.getRoomName()!=null && !"".equals(systemRoom.getRoomName()))
		{
			hql.append(" and l.roomName like '%"+systemRoom.getRoomName()+"%'");
		}
		
		return systemRoomDAO.executeQuery(hql.toString(), (currpage-1)*pageSize, pageSize);

	}

}
