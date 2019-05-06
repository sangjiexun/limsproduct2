package net.zjcclims.service.lab;

import java.util.List;
import java.util.Set;

import net.zjcclims.domain.LabRoomDeviceLending;
import net.zjcclims.domain.LabRoomDeviceLendingResult;

/**
 * Spring service that handles CRUD requests for LabRoomDeviceLendingResult entities
 * 
 */
public interface LabRoomDeviceLendingResultService {

	/**
	 * Delete an existing LabRoomDeviceLendingResult entity
	 * 
	 */
	public void deleteLabRoomDeviceLendingResult(LabRoomDeviceLendingResult labroomdevicelendingresult);

	/**
	 * Load an existing LabRoomDeviceLendingResult entity
	 * 
	 */
	public Set<LabRoomDeviceLendingResult> loadLabRoomDeviceLendingResults();

	/**
	 * Return a count of all LabRoomDeviceLendingResult entity
	 * 
	 */
	public Integer countLabRoomDeviceLendingResults();

	/**
	 * Save an existing LabRoomDeviceLending entity
	 * 
	 */
	public LabRoomDeviceLendingResult saveLabRoomDeviceLendingResultLabRoomDeviceLending(Integer id, LabRoomDeviceLending related_labroomdevicelending);

	/**
	 * Delete an existing LabRoomDeviceLending entity
	 * 
	 */
	public LabRoomDeviceLendingResult deleteLabRoomDeviceLendingResultLabRoomDeviceLending(Integer labroomdevicelendingresult_id, Integer related_labroomdevicelending_id);

	/**
	 */
	public LabRoomDeviceLendingResult findLabRoomDeviceLendingResultByPrimaryKey(Integer id_1);

	/**
	 * Save an existing LabRoomDeviceLendingResult entity
	 * 
	 */
	public void saveLabRoomDeviceLendingResult(LabRoomDeviceLendingResult labroomdevicelendingresult_1);

	/**
	 * Return all LabRoomDeviceLendingResult entity
	 * 
	 */
	public List<LabRoomDeviceLendingResult> findAllLabRoomDeviceLendingResults(Integer startResult, Integer maxRows);
}