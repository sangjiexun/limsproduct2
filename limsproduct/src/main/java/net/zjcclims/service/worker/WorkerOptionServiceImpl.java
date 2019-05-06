package net.zjcclims.service.worker;

import java.util.List;
import java.util.Set;

import net.zjcclims.dao.UserDAO;
import net.zjcclims.dao.WorkerOptionDAO;

import net.zjcclims.domain.User;
import net.zjcclims.domain.WorkerOption;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for WorkerOption entities
 * 
 */

@Service("WorkerOptionService")
@Transactional
public class WorkerOptionServiceImpl implements WorkerOptionService {

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * DAO injected by Spring that manages WorkerOption entities
	 * 
	 */
	@Autowired
	private WorkerOptionDAO workerOptionDAO;

	/************************************************** 
	*@功能:根据查询条件获取可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public List<WorkerOption> workerOptions(WorkerOption workerOption,int curr,int size){
		String sql = "select w from WorkerOption w where 1=1";
		if(workerOption.getUser()!=null && workerOption.getUser().getCname()!= null){
			sql+="and w.user.cname like '%"+workerOption.getUser().getCname()+"%'";
		}
		if(workerOption.getWorker()!=null){
			sql+="and w.worker ="+workerOption.getWorker();
		}
		return workerOptionDAO.executeQuery(sql, curr, size);
	}
	
	/************************************************** 
	*@功能:获取教师信息
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public List<User> findAllTeacher(){
		String sql = "select u from User u where 1=1 and userRole = 1 and cname !=''";
		return userDAO.executeQuery(sql);
	}
	
	/************************************************** 
	*@功能:保存可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public void saveWorkerOption(WorkerOption workerOption){
		workerOptionDAO.store(workerOption);
		workerOptionDAO.flush();
	}
	
	/************************************************** 
	*@功能:删除可预约工位数
	*@作者:张德冰
	*@时间:2018.03.28
	***************************************************/
	public void deleteWorkerOption(WorkerOption workerOption){
		workerOptionDAO.remove(workerOption);
		workerOptionDAO.flush();
	}
}
