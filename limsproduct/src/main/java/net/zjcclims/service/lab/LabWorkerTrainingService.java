package net.zjcclims.service.lab;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.domain.CDictionary;
import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.LabWorkerTraining;
import net.zjcclims.domain.User;

/**
 * Spring service that handles CRUD requests for LabWorkerTraining entities
 * 
 */
public interface LabWorkerTrainingService {

	/**
	 * Save an existing User entity
	 * 
	 */
	public LabWorkerTraining saveLabWorkerTrainingUser(Integer id, User related_user);

	/**
	 * Return a count of all LabWorkerTraining entity
	 * 
	 */
	public Integer countLabWorkerTrainings();

	/**
	 * Delete an existing LabWorkerTraining entity
	 * 
	 */
	public void deleteLabWorkerTraining(LabWorkerTraining labworkertraining_1);

	/**
	 * Load an existing LabWorkerTraining entity
	 * 
	 */
	public Set<LabWorkerTraining> loadLabWorkerTrainings();

	/**
	 * Return all LabWorkerTraining entity
	 * 
	 */
	public List<LabWorkerTraining> findAllLabWorkerTrainings(Integer startResult, Integer maxRows);

	/**
	 * Delete an existing CDictionary entity
	 * 
	 */
	public LabWorkerTraining deleteLabWorkerTrainingCDictionary(Integer labworkertraining_id, Integer related_cdictionary_id);

	/**
	 * Delete an existing User entity
	 * 
	 */
	public LabWorkerTraining deleteLabWorkerTrainingUser(Integer labworkertraining_id_1, String related_user_username);

	/**
	 * Save an existing CDictionary entity
	 * 
	 */
	public LabWorkerTraining saveLabWorkerTrainingCDictionary(Integer id_1, CDictionary related_cdictionary);

	/**
	 */
	public LabWorkerTraining findLabWorkerTrainingByPrimaryKey(Integer id_2);
	/****************************************************************************
	 * 功能：上传附件
	 * 作者：周志辉
	 ****************************************************************************/
	public void labWorkerTrainingDocumentUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id,int type);
	
	/****************************************************************************
	 * 功能：保存实验室人员队伍培训进修的文档
	 * 作者：周志辉
	 * 时间：2017-08-16
	 ****************************************************************************/
	public void saveLabWorkerTrainingDocument(String fileTrueName, Integer labWorkerTrainingid,Integer type);
	/**
	 * 保存实验室队伍培训登记
	 * @author 周志辉
	 * 2017.08.16
	 */
	public LabWorkerTraining saveLabWorkerTraining(LabWorkerTraining labWorkerTraining);
	
	/***************************** 
	*Description 实训室队伍培训进修详情
	*
	*@author:周志辉
	*@param:
	*@date:2018.08.21
	*****************************/
	public LabWorkerTraining findLabWorkerTrainingDetailByPrimaryKey(Integer labWorkerTrainingId);
	
	/***************************** 
	*Description 查询实训室人员培训进修纪录
	*
	*@author:周志辉
	*@param:
	*@date:2018.08.21
	*****************************/
	public List<LabWorkerTraining> findLabWorkerTrainingDetailByLabWorkerId(Integer labWorkerId);
	/****************************************************************************
	 * 功能：保存设备维修登记的文档
	 * 作者：周志辉
	 * 时间：2017-08-23
	 ****************************************************************************/
	public void saveLabRoomDeviceRepairDocument(String fileTrueName, Integer labRoomDeviceRepairId,Integer type);
}
