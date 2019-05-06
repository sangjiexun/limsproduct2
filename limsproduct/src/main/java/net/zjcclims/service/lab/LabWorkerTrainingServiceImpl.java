package net.zjcclims.service.lab;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zjcclims.dao.CDictionaryDAO;
import net.zjcclims.dao.CommonDocumentDAO;
import net.zjcclims.dao.LabRoomDeviceRepairDAO;
import net.zjcclims.dao.LabWorkerTrainingDAO;
import net.zjcclims.dao.UserDAO;

import net.zjcclims.domain.CDictionary;
import net.zjcclims.domain.CommonDocument;
import net.zjcclims.domain.LabRoomDeviceRepair;
import net.zjcclims.domain.LabWorkerTraining;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Spring service that handles CRUD requests for LabWorkerTraining entities
 * 
 */

@Service("LabWorkerTrainingService")
@Transactional
public class LabWorkerTrainingServiceImpl implements LabWorkerTrainingService {

	/**
	 * DAO injected by Spring that manages CDictionary entities
	 * 
	 */
	@Autowired
	private LabRoomDeviceRepairDAO labRoomDeviceRepairDAO;
	@Autowired
	private CDictionaryDAO cDictionaryDAO;
	@Autowired   
	ShareService shareService;
	@Autowired
	private CommonDocumentDAO documentDAO;
	/**
	 * DAO injected by Spring that manages LabWorkerTraining entities
	 * 
	 */
	@Autowired
	private LabWorkerTrainingDAO labWorkerTrainingDAO;

	/**
	 * DAO injected by Spring that manages User entities
	 * 
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Instantiates a new LabWorkerTrainingServiceImpl.
	 *
	 */
	public LabWorkerTrainingServiceImpl() {
	}

	/**
	 * Save an existing User entity
	 * 
	 */
	@Transactional
	public LabWorkerTraining saveLabWorkerTrainingUser(Integer id, User related_user) {
		LabWorkerTraining labworkertraining = labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(id, -1, -1);
		User existinguser = userDAO.findUserByPrimaryKey(related_user.getUsername());

		// copy into the existing record to preserve existing relationships
		if (existinguser != null) {
			existinguser.setUsername(related_user.getUsername());
			existinguser.setCardno(related_user.getCardno());
			existinguser.setCname(related_user.getCname());
			existinguser.setPassword(related_user.getPassword());
			existinguser.setUserSexy(related_user.getUserSexy());
			existinguser.setUserStatus(related_user.getUserStatus());
			existinguser.setTeacherNumber(related_user.getTeacherNumber());
			//existinguser.setAcademyNumber(related_user.getAcademyNumber());
			existinguser.setMajorNumber(related_user.getMajorNumber());
			existinguser.setUserRole(related_user.getUserRole());
			//existinguser.setClassesNumber(related_user.getClassesNumber());
			existinguser.setLastLogin(related_user.getLastLogin());
			existinguser.setCreatedAt(related_user.getCreatedAt());
			existinguser.setUpdatedAt(related_user.getUpdatedAt());
			existinguser.setTelephone(related_user.getTelephone());
			existinguser.setEmail(related_user.getEmail());
			existinguser.setEnabled(related_user.getEnabled());
//			existinguser.setMajorDirection(related_user.getMajorDirection());
			existinguser.setEnrollmentStatus(related_user.getEnrollmentStatus());
			existinguser.setIfEnrollment(related_user.getIfEnrollment());
			existinguser.setUserType(related_user.getUserType());
			existinguser.setAttendanceTime(related_user.getAttendanceTime());
			existinguser.setGrade(related_user.getGrade());
			existinguser.setQq(related_user.getQq());
			//existinguser.setResearchId(related_user.getResearchId());
			existinguser.setCreditScore(related_user.getCreditScore());
			related_user = existinguser;
		} else {
			related_user = userDAO.store(related_user);
			userDAO.flush();
		}

		labworkertraining.setUser(related_user);
		related_user.getLabWorkerTrainings().add(labworkertraining);
		labworkertraining = labWorkerTrainingDAO.store(labworkertraining);
		labWorkerTrainingDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		return labworkertraining;
	}

//	/**
//	 * Save an existing LabWorkerTraining entity
//	 * 
//	 */
//	@Transactional
//	public void saveLabWorkerTraining(LabWorkerTraining labworkertraining) {
//		LabWorkerTraining existingLabWorkerTraining = labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(labworkertraining.getId());
//
//		if (existingLabWorkerTraining != null) {
//			if (existingLabWorkerTraining != labworkertraining) {
//				existingLabWorkerTraining.setId(labworkertraining.getId());
//				existingLabWorkerTraining.setBeginTime(labworkertraining.getBeginTime());
//				existingLabWorkerTraining.setEndTime(labworkertraining.getEndTime());
//				existingLabWorkerTraining.setScore(labworkertraining.getScore());
//				existingLabWorkerTraining.setOrganizer(labworkertraining.getOrganizer());
//				existingLabWorkerTraining.setLearnContent(labworkertraining.getLearnContent());
//				existingLabWorkerTraining.setAnnex(labworkertraining.getAnnex());
//			}
//			labworkertraining = labWorkerTrainingDAO.store(existingLabWorkerTraining);
//		} else {
//			labworkertraining = labWorkerTrainingDAO.store(labworkertraining);
//		}
//		labWorkerTrainingDAO.flush();
//	}

	/**
	 * Return a count of all LabWorkerTraining entity
	 * 
	 */
	@Transactional
	public Integer countLabWorkerTrainings() {
		return ((Long) labWorkerTrainingDAO.createQuerySingleResult("select count(o) from LabWorkerTraining o").getSingleResult()).intValue();
	}

	/**
	 * Delete an existing LabWorkerTraining entity
	 * 
	 */
	@Transactional
	public void deleteLabWorkerTraining(LabWorkerTraining labworkertraining) {
		labWorkerTrainingDAO.remove(labworkertraining);
		labWorkerTrainingDAO.flush();
	}

	/**
	 * Load an existing LabWorkerTraining entity
	 * 
	 */
	@Transactional
	public Set<LabWorkerTraining> loadLabWorkerTrainings() {
		return labWorkerTrainingDAO.findAllLabWorkerTrainings();
	}

	/**
	 * Return all LabWorkerTraining entity
	 * 
	 */
	@Transactional
	public List<LabWorkerTraining> findAllLabWorkerTrainings(Integer startResult, Integer maxRows) {
		return new java.util.ArrayList<LabWorkerTraining>(labWorkerTrainingDAO.findAllLabWorkerTrainings(startResult, maxRows));
	}

	/**
	 * Delete an existing CDictionary entity
	 * 
	 */
	@Transactional
	public LabWorkerTraining deleteLabWorkerTrainingCDictionary(Integer labworkertraining_id, Integer related_cdictionary_id) {
		LabWorkerTraining labworkertraining = labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(labworkertraining_id, -1, -1);
		CDictionary related_cdictionary = cDictionaryDAO.findCDictionaryByPrimaryKey(related_cdictionary_id, -1, -1);

		labworkertraining.setCDictionary(null);
		related_cdictionary.getLabWorkerTrainings().remove(labworkertraining);
		labworkertraining = labWorkerTrainingDAO.store(labworkertraining);
		labWorkerTrainingDAO.flush();

		related_cdictionary = cDictionaryDAO.store(related_cdictionary);
		cDictionaryDAO.flush();

		cDictionaryDAO.remove(related_cdictionary);
		cDictionaryDAO.flush();

		return labworkertraining;
	}

	/**
	 * Delete an existing User entity
	 * 
	 */
	@Transactional
	public LabWorkerTraining deleteLabWorkerTrainingUser(Integer labworkertraining_id, String related_user_username) {
		LabWorkerTraining labworkertraining = labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(labworkertraining_id, -1, -1);
		User related_user = userDAO.findUserByPrimaryKey(related_user_username, -1, -1);

		labworkertraining.setUser(null);
		related_user.getLabWorkerTrainings().remove(labworkertraining);
		labworkertraining = labWorkerTrainingDAO.store(labworkertraining);
		labWorkerTrainingDAO.flush();

		related_user = userDAO.store(related_user);
		userDAO.flush();

		userDAO.remove(related_user);
		userDAO.flush();

		return labworkertraining;
	}

	/**
	 * Save an existing CDictionary entity
	 * 
	 */
	@Transactional
	public LabWorkerTraining saveLabWorkerTrainingCDictionary(Integer id, CDictionary related_cdictionary) {
		LabWorkerTraining labworkertraining = labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(id, -1, -1);
		CDictionary existingCDictionary = cDictionaryDAO.findCDictionaryByPrimaryKey(related_cdictionary.getId());

		// copy into the existing record to preserve existing relationships
		if (existingCDictionary != null) {
			existingCDictionary.setId(related_cdictionary.getId());
			existingCDictionary.setCNumber(related_cdictionary.getCNumber());
			existingCDictionary.setCCategory(related_cdictionary.getCCategory());
			existingCDictionary.setCName(related_cdictionary.getCName());
			existingCDictionary.setEnabled(related_cdictionary.getEnabled());
			related_cdictionary = existingCDictionary;
		} else {
			related_cdictionary = cDictionaryDAO.store(related_cdictionary);
			cDictionaryDAO.flush();
		}

		labworkertraining.setCDictionary(related_cdictionary);
		related_cdictionary.getLabWorkerTrainings().add(labworkertraining);
		labworkertraining = labWorkerTrainingDAO.store(labworkertraining);
		labWorkerTrainingDAO.flush();

		related_cdictionary = cDictionaryDAO.store(related_cdictionary);
		cDictionaryDAO.flush();

		return labworkertraining;
	}

	/**
	 */
	@Transactional
	public LabWorkerTraining findLabWorkerTrainingByPrimaryKey(Integer id) {
		return labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(id);
	}
	
	/****************************************************************************
	 * 功能：上传附件
	 * 作者：周志辉
	 ****************************************************************************/
	@Override
	public void labWorkerTrainingDocumentUpload(HttpServletRequest request,
			HttpServletResponse response, Integer id,int type) {
		String sep = System.getProperty("file.separator"); 
		String path = sep+ "upload"+ sep+"labWorkerTraining"+sep+id;
		shareService.uploadFiles(request, path,"saveLabWorkerTraininDocument",id);
	}
	/****************************************************************************
	 * 功能：保存实验室人员队伍培训进修的文档
	 * 作者：周志辉
	 * 时间：2017-08-16
	 ****************************************************************************/
	public void saveLabWorkerTrainingDocument(String fileTrueName, Integer labWorkerTrainingid,Integer type) {
		// TODO Auto-generated method stub
		LabWorkerTraining labWorkerTraining=labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(labWorkerTrainingid);
		CommonDocument doc=new CommonDocument();
		doc.setType(type);
		doc.setDocumentName(fileTrueName);
		String imageUrl="upload/labWorkerTraining/"+labWorkerTrainingid+"/"+fileTrueName;
		doc.setDocumentUrl(imageUrl);
		doc.setLabWorkerTraining(labWorkerTraining);		
		documentDAO.store(doc);
	}
	/**
	 * 保存实验室队伍培训登记
	 * @author 周志辉
	 * 2017.08.16
	 */
	@Override
	public LabWorkerTraining saveLabWorkerTraining(LabWorkerTraining labWorkerTraining) {
		return labWorkerTrainingDAO.store(labWorkerTraining);
	}
	/***************************** 
	*Description 实训室队伍培训进修详情
	*
	*@author:周志辉
	*@param:
	*@date:2018.08.21
	*****************************/
	@Override
	public LabWorkerTraining findLabWorkerTrainingDetailByPrimaryKey(Integer labWorkerTrainingId) {
		return labWorkerTrainingDAO.findLabWorkerTrainingByPrimaryKey(labWorkerTrainingId);
	}

	@Override
	public List<LabWorkerTraining> findLabWorkerTrainingDetailByLabWorkerId(
			Integer labWorkerId) {
		// TODO Auto-generated method stub
		String sql="select l from LabWorkerTraining l where labWorker="+labWorkerId;
		return labWorkerTrainingDAO.executeQuery(sql);
	
	}
	/****************************************************************************
	 * 功能：保存实验室人员队伍培训进修的文档
	 * 作者：周志辉
	 * 时间：2017-08-16
	 ****************************************************************************/
	public void saveLabRoomDeviceRepairDocument(String fileTrueName, Integer labRoomDeviceRepairId,Integer type) {
		// TODO Auto-generated method stub
		LabRoomDeviceRepair labRoomDeviceRepair=labRoomDeviceRepairDAO.findLabRoomDeviceRepairByPrimaryKey(labRoomDeviceRepairId);
		CommonDocument doc=new CommonDocument();
		doc.setType(type);
		doc.setDocumentName(fileTrueName);
		String imageUrl="upload/labRoomDeviceRepair/"+labRoomDeviceRepairId+"/"+fileTrueName;
		doc.setDocumentUrl(imageUrl);
		doc.setLabRoomDeviceRepair(labRoomDeviceRepair);		
		documentDAO.store(doc);
	}
}
