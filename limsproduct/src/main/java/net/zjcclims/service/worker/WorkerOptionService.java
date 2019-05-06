package net.zjcclims.service.worker;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.User;
import net.zjcclims.domain.WorkerOption;

/**
 * Spring service that handles CRUD requests for WorkerOption entities
 * 
 */
public interface WorkerOptionService {

	/************************************************** 
	*@功能:根据查询条件获取可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public List<WorkerOption> workerOptions(WorkerOption workerOption,int curr,int size);

	/************************************************** 
	*@功能:获取教师信息
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public List<User> findAllTeacher();

	/************************************************** 
	*@功能:保存可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public void saveWorkerOption(WorkerOption workerOption);

	/************************************************** 
	*@功能:删除可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public void deleteWorkerOption(WorkerOption workerOption);
}