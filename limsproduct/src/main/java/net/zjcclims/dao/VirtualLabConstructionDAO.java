package net.zjcclims.dao;


import net.zjcclims.domain.VirtualLabConstruction;
import org.skyway.spring.util.dao.JpaDao;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DAO to manage VirtualLabConstruction entities.
 * 
 */
public interface VirtualLabConstructionDAO extends
		JpaDao<VirtualLabConstruction> {

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorNameContaining(String majorName) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorNameContaining(String majorName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorName(String majorName_1) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorName(String majorName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionBySiteArea
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySiteArea(java.math.BigDecimal siteArea) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionBySiteArea
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySiteArea(BigDecimal siteArea, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByHugeDeviceAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByHugeDeviceAmount(Integer hugeDeviceAmount) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByHugeDeviceAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByHugeDeviceAmount(Integer hugeDeviceAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySociety
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySociety(java.math.BigDecimal useFrequencySociety) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySociety
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySociety(BigDecimal useFrequencySociety, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectName(String trainingProjectName) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectName(String trainingProjectName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByOwnDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByOwnDeviceValue(java.math.BigDecimal ownDeviceValue) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByOwnDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByOwnDeviceValue(BigDecimal ownDeviceValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByAddDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByAddDeviceValue(java.math.BigDecimal addDeviceValue) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByAddDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByAddDeviceValue(BigDecimal addDeviceValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectNameContaining(String trainingProjectName_1) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectNameContaining(String trainingProjectName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByManagePeopleAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByManagePeopleAmount(Integer managePeopleAmount) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByManagePeopleAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByManagePeopleAmount(Integer managePeopleAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseNameContaining(String projectBaseName) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseNameContaining(String projectBaseName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByMaterialFee
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMaterialFee(java.math.BigDecimal materialFee) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByMaterialFee
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMaterialFee(BigDecimal materialFee, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceAmount(Integer deviceAmount) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceAmount(Integer deviceAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionById
	 *
	 */
	public VirtualLabConstruction findVirtualLabConstructionById(Integer id) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionById
	 *
	 */
	public VirtualLabConstruction findVirtualLabConstructionById(Integer id, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseName(String projectBaseName_1) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseName(String projectBaseName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDonateDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDonateDeviceValue(java.math.BigDecimal donateDeviceValue) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDonateDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDonateDeviceValue(BigDecimal donateDeviceValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByBuildingArea
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByBuildingArea(java.math.BigDecimal buildingArea) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByBuildingArea
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByBuildingArea(BigDecimal buildingArea, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findAllVirtualLabConstructions
	 *
	 */
	public Set<VirtualLabConstruction> findAllVirtualLabConstructions() throws DataAccessException;

	/**
	 * JPQL Query - findAllVirtualLabConstructions
	 *
	 */
	public Set<VirtualLabConstruction> findAllVirtualLabConstructions(int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySchool
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySchool(java.math.BigDecimal useFrequencySchool) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySchool
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySchool(BigDecimal useFrequencySchool, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectAmount(Integer trainingProjectAmount) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectAmount(Integer trainingProjectAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByPrimaryKey
	 *
	 */
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id_1) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByPrimaryKey
	 *
	 */
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyNameContaining(String supportAcademyName) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyNameContaining
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyNameContaining(String supportAcademyName, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByLabRoomDeviceRepairId
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByLabRoomDeviceRepairId(Integer labRoomDeviceRepairId) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByLabRoomDeviceRepairId
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByLabRoomDeviceRepairId(Integer labRoomDeviceRepairId, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByPreDonateDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPreDonateDeviceValue(java.math.BigDecimal preDonateDeviceValue) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByPreDonateDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPreDonateDeviceValue(BigDecimal preDonateDeviceValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyName(String supportAcademyName_1) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyName
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyName(String supportAcademyName_1, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceValue(java.math.BigDecimal deviceValue) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceValue
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceValue(BigDecimal deviceValue, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByPartTimePeopleAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPartTimePeopleAmount(Integer partTimePeopleAmount) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByPartTimePeopleAmount
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPartTimePeopleAmount(Integer partTimePeopleAmount, int startResult, int maxRows) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceMaintainFee
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceMaintainFee(java.math.BigDecimal deviceMaintainFee) throws DataAccessException;

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceMaintainFee
	 *
	 */
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceMaintainFee(BigDecimal deviceMaintainFee, int startResult, int maxRows) throws DataAccessException;

}