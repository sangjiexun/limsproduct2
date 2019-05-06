package net.zjcclims.service.system;

import net.zjcclims.domain.SystemRoom;
import java.util.List;

public interface SystemRoomService {
	/**
	 * 获取所有的系统房间
	 * @author 周志辉
	 * 2017.07.26
	 */
	public List<SystemRoom> findAllSystemRoomByQuery(SystemRoom systemRoom, Integer currpage, Integer pageSize);
}
