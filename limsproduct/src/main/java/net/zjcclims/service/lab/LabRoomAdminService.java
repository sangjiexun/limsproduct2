package net.zjcclims.service.lab;

import java.util.List;

import net.zjcclims.domain.LabRoomAdmin;

public interface LabRoomAdminService {
	/**
	 * 根据LabRoomId查询实验室管理员
	 * 周志辉
	 * 2017-09-20
	 */
	public List<LabRoomAdmin> findAllLabRoomAdminsByLabRoomId(int labRoomId);
	
	public List<LabRoomAdmin> findLabRoomAdminForType3ByLabRoomId(int labRoomId);

	/**
	 * Description 通过用户名查找所管理的实验室
	 * @param username 用户名
	 * @param typeId 实验室管理员类型
	 * @return 该用户所管理的实验室
	 * @author 黄保钱 2018-08-22
	 */
	public List findLabRoomAdminForByUsernameAndType(String username, int typeId);
}
