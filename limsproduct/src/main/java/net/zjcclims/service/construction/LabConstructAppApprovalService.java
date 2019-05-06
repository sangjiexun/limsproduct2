package net.zjcclims.service.construction;



import net.zjcclims.domain.LabConstructApp;
import net.zjcclims.domain.LabConstructAppApproval;
import net.zjcclims.domain.User;

import java.util.List;
import java.util.Set;

/**
 * Spring service that handles CRUD requests for LabConstructAppApproval entities
 * 
 */
public interface LabConstructAppApprovalService {

	/**
	 * Save an existing User entity
	 * 
	 */
	public LabConstructAppApproval saveLabConstructAppApprovalUser(Integer id, User related_user);

	/**
	 */
	public LabConstructAppApproval findLabConstructAppApprovalByPrimaryKey(Integer id_1);

	/**
	 * Delete an existing LabConstructApp entity
	 * 
	 */
	public LabConstructAppApproval deleteLabConstructAppApprovalLabConstructApp(Integer labconstructappapproval_id, Integer related_labconstructapp_id);

	/**
	 * Save an existing LabConstructAppApproval entity
	 * 
	 */
	public void saveLabConstructAppApproval(LabConstructAppApproval labconstructappapproval);

	/**
	 * Load an existing LabConstructAppApproval entity
	 * 
	 */
	public Set<LabConstructAppApproval> loadLabConstructAppApprovals();

	/**
	 * Delete an existing User entity
	 * 
	 */
	public LabConstructAppApproval deleteLabConstructAppApprovalUser(Integer labconstructappapproval_id_1, String related_user_username);

	/**
	 * Return all LabConstructAppApproval entity
	 * 
	 */
	public List<LabConstructAppApproval> findAllLabConstructAppApprovals(Integer startResult, Integer maxRows);

	/**
	 * Save an existing LabConstructApp entity
	 * 
	 */
	public LabConstructAppApproval saveLabConstructAppApprovalLabConstructApp(Integer id_2, LabConstructApp related_labconstructapp);

	/**
	 * Return a count of all LabConstructAppApproval entity
	 * 
	 */
	public Integer countLabConstructAppApprovals();

	/**
	 * Delete an existing LabConstructAppApproval entity
	 * 
	 */
	public void deleteLabConstructAppApproval(LabConstructAppApproval labconstructappapproval_1);
}