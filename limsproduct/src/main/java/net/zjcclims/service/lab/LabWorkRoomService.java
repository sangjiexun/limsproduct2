package net.zjcclims.service.lab;

import java.util.List;
import java.util.Map;

import net.zjcclims.domain.LabWorkRoom;

public interface LabWorkRoomService {

	/***************************** 
	*Description 根据工作室主键查找工作室对象
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	public LabWorkRoom findLabWorkRoomByPrimaryKey(Integer labWorkRoomId);
	
	/***************************** 
	*Description 获取所有的工作室
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	public List<LabWorkRoom> findAllLabWorkRoomByQuery(LabWorkRoom labWorkRoom, Integer currpage, Integer pageSize);
	
	/***************************** 
	*Description 保存工作室信息
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	public LabWorkRoom saveLabWorkRoom(LabWorkRoom labWorkRoom);
	
	/***************************** 
	*Description 删除工作室信息
	*
	*@author:余南新
	*@param:
	*@date:2017.4.25
	*****************************/
	public boolean deleteLabWorkRoom(Integer labWorkRoomId);

}
