package net.zjcclims.service.lab;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.LabRoomDeviceLending;
import net.zjcclims.domain.LabRoomDeviceLendingResult;

/**
 * Spring service that handles CRUD requests for LabRoomDeviceLending entities
 * 
 */
public interface LabRoomDeviceLendingService {

	/**
	 * Save an existing LabRoomDeviceLending entity
	 * 
	 */
	public void saveLabRoomDeviceLending(LabRoomDeviceLending labroomdevicelending);

	/**
	 * Load an existing LabRoomDeviceLending entity
	 * 
	 */
	public Set<LabRoomDeviceLending> loadLabRoomDeviceLendings();

	/**
	 * Save an existing LabRoomDeviceLendingResult entity
	 * 
	 */
	public LabRoomDeviceLending saveLabRoomDeviceLendingLabRoomDeviceLendingResults(Integer id, LabRoomDeviceLendingResult related_labroomdevicelendingresults);

	/**
	 * Delete an existing LabRoomDeviceLendingResult entity
	 * 
	 */
	public LabRoomDeviceLending deleteLabRoomDeviceLendingLabRoomDeviceLendingResults(Integer labroomdevicelending_id, Integer related_labroomdevicelendingresults_id);

	/**
	 */
	public LabRoomDeviceLending findLabRoomDeviceLendingByPrimaryKey(Integer id_1);

	/**
	 * Return all LabRoomDeviceLending entity
	 * 
	 */
	public List<LabRoomDeviceLending> findAllLabRoomDeviceLendings(Integer startResult, Integer maxRows);

	/**
	 * Return a count of all LabRoomDeviceLending entity
	 * 
	 */
	public Integer countLabRoomDeviceLendings();

	/**
	 * Delete an existing LabRoomDeviceLending entity
	 * 
	 */
	public void deleteLabRoomDeviceLending(LabRoomDeviceLending labroomdevicelending_1);
}