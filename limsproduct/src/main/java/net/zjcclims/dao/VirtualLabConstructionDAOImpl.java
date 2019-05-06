package net.zjcclims.dao;


import net.zjcclims.domain.VirtualLabConstruction;
import org.skyway.spring.util.dao.AbstractJpaDao;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DAO to manage VirtualLabConstruction entities.
 * 
 */
@Repository("VirtualLabConstructionDAO")
@Transactional
public class VirtualLabConstructionDAOImpl extends AbstractJpaDao<VirtualLabConstruction>
		implements VirtualLabConstructionDAO {

	/**
	 * Set of entity classes managed by this DAO.  Typically a DAO manages a single entity.
	 *
	 */
	private final static Set<Class<?>> dataTypes = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { VirtualLabConstruction.class }));

	/**
	 * EntityManager injected by Spring for persistence unit dhulimsConn
	 *
	 */
	@PersistenceContext(unitName = "zjcclimsConn")
	private EntityManager entityManager;

	/**
	 * Instantiates a new VirtualLabConstructionDAOImpl
	 *
	 */
	public VirtualLabConstructionDAOImpl() {
		super();
	}

	/**
	 * Get the entity manager that manages persistence unit 
	 *
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Returns the set of entity classes managed by this DAO.
	 *
	 */
	public Set<Class<?>> getTypes() {
		return dataTypes;
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorNameContaining
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorNameContaining(String majorName) throws DataAccessException {

		return findVirtualLabConstructionByMajorNameContaining(majorName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorNameContaining(String majorName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByMajorNameContaining", startResult, maxRows, majorName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorName
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorName(String majorName) throws DataAccessException {

		return findVirtualLabConstructionByMajorName(majorName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByMajorName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMajorName(String majorName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByMajorName", startResult, maxRows, majorName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionBySiteArea
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySiteArea(java.math.BigDecimal siteArea) throws DataAccessException {

		return findVirtualLabConstructionBySiteArea(siteArea, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionBySiteArea
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySiteArea(java.math.BigDecimal siteArea, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionBySiteArea", startResult, maxRows, siteArea);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByHugeDeviceAmount
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByHugeDeviceAmount(Integer hugeDeviceAmount) throws DataAccessException {

		return findVirtualLabConstructionByHugeDeviceAmount(hugeDeviceAmount, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByHugeDeviceAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByHugeDeviceAmount(Integer hugeDeviceAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByHugeDeviceAmount", startResult, maxRows, hugeDeviceAmount);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySociety
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySociety(java.math.BigDecimal useFrequencySociety) throws DataAccessException {

		return findVirtualLabConstructionByUseFrequencySociety(useFrequencySociety, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySociety
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySociety(java.math.BigDecimal useFrequencySociety, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByUseFrequencySociety", startResult, maxRows, useFrequencySociety);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectName
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectName(String trainingProjectName) throws DataAccessException {

		return findVirtualLabConstructionByTrainingProjectName(trainingProjectName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectName(String trainingProjectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByTrainingProjectName", startResult, maxRows, trainingProjectName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByOwnDeviceValue
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByOwnDeviceValue(java.math.BigDecimal ownDeviceValue) throws DataAccessException {

		return findVirtualLabConstructionByOwnDeviceValue(ownDeviceValue, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByOwnDeviceValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByOwnDeviceValue(java.math.BigDecimal ownDeviceValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByOwnDeviceValue", startResult, maxRows, ownDeviceValue);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByAddDeviceValue
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByAddDeviceValue(java.math.BigDecimal addDeviceValue) throws DataAccessException {

		return findVirtualLabConstructionByAddDeviceValue(addDeviceValue, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByAddDeviceValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByAddDeviceValue(java.math.BigDecimal addDeviceValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByAddDeviceValue", startResult, maxRows, addDeviceValue);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectNameContaining
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectNameContaining(String trainingProjectName) throws DataAccessException {

		return findVirtualLabConstructionByTrainingProjectNameContaining(trainingProjectName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectNameContaining(String trainingProjectName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByTrainingProjectNameContaining", startResult, maxRows, trainingProjectName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByManagePeopleAmount
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByManagePeopleAmount(Integer managePeopleAmount) throws DataAccessException {

		return findVirtualLabConstructionByManagePeopleAmount(managePeopleAmount, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByManagePeopleAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByManagePeopleAmount(Integer managePeopleAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByManagePeopleAmount", startResult, maxRows, managePeopleAmount);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseNameContaining
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseNameContaining(String projectBaseName) throws DataAccessException {

		return findVirtualLabConstructionByProjectBaseNameContaining(projectBaseName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseNameContaining(String projectBaseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByProjectBaseNameContaining", startResult, maxRows, projectBaseName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByMaterialFee
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMaterialFee(java.math.BigDecimal materialFee) throws DataAccessException {

		return findVirtualLabConstructionByMaterialFee(materialFee, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByMaterialFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByMaterialFee(java.math.BigDecimal materialFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByMaterialFee", startResult, maxRows, materialFee);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceAmount
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceAmount(Integer deviceAmount) throws DataAccessException {

		return findVirtualLabConstructionByDeviceAmount(deviceAmount, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceAmount(Integer deviceAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByDeviceAmount", startResult, maxRows, deviceAmount);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionById
	 *
	 */
	@Transactional
	public VirtualLabConstruction findVirtualLabConstructionById(Integer id) throws DataAccessException {

		return findVirtualLabConstructionById(id, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionById
	 *
	 */

	@Transactional
	public VirtualLabConstruction findVirtualLabConstructionById(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVirtualLabConstructionById", startResult, maxRows, id);
			return (VirtualLabConstruction) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseName
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseName(String projectBaseName) throws DataAccessException {

		return findVirtualLabConstructionByProjectBaseName(projectBaseName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByProjectBaseName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByProjectBaseName(String projectBaseName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByProjectBaseName", startResult, maxRows, projectBaseName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDonateDeviceValue
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDonateDeviceValue(java.math.BigDecimal donateDeviceValue) throws DataAccessException {

		return findVirtualLabConstructionByDonateDeviceValue(donateDeviceValue, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDonateDeviceValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDonateDeviceValue(java.math.BigDecimal donateDeviceValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByDonateDeviceValue", startResult, maxRows, donateDeviceValue);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByBuildingArea
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByBuildingArea(java.math.BigDecimal buildingArea) throws DataAccessException {

		return findVirtualLabConstructionByBuildingArea(buildingArea, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByBuildingArea
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByBuildingArea(java.math.BigDecimal buildingArea, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByBuildingArea", startResult, maxRows, buildingArea);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findAllVirtualLabConstructions
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findAllVirtualLabConstructions() throws DataAccessException {

		return findAllVirtualLabConstructions(-1, -1);
	}

	/**
	 * JPQL Query - findAllVirtualLabConstructions
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findAllVirtualLabConstructions(int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findAllVirtualLabConstructions", startResult, maxRows);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySchool
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySchool(java.math.BigDecimal useFrequencySchool) throws DataAccessException {

		return findVirtualLabConstructionByUseFrequencySchool(useFrequencySchool, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByUseFrequencySchool
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByUseFrequencySchool(java.math.BigDecimal useFrequencySchool, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByUseFrequencySchool", startResult, maxRows, useFrequencySchool);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectAmount
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectAmount(Integer trainingProjectAmount) throws DataAccessException {

		return findVirtualLabConstructionByTrainingProjectAmount(trainingProjectAmount, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByTrainingProjectAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByTrainingProjectAmount(Integer trainingProjectAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByTrainingProjectAmount", startResult, maxRows, trainingProjectAmount);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByPrimaryKey
	 *
	 */
	@Transactional
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id) throws DataAccessException {

		return findVirtualLabConstructionByPrimaryKey(id, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByPrimaryKey
	 *
	 */

	@Transactional
	public VirtualLabConstruction findVirtualLabConstructionByPrimaryKey(Integer id, int startResult, int maxRows) throws DataAccessException {
		try {
			Query query = createNamedQuery("findVirtualLabConstructionByPrimaryKey", startResult, maxRows, id);
			return (VirtualLabConstruction) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyNameContaining
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyNameContaining(String supportAcademyName) throws DataAccessException {

		return findVirtualLabConstructionBySupportAcademyNameContaining(supportAcademyName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyNameContaining
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyNameContaining(String supportAcademyName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionBySupportAcademyNameContaining", startResult, maxRows, supportAcademyName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByLabRoomDeviceRepairId
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByLabRoomDeviceRepairId(Integer labRoomDeviceRepairId) throws DataAccessException {

		return findVirtualLabConstructionByLabRoomDeviceRepairId(labRoomDeviceRepairId, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByLabRoomDeviceRepairId
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByLabRoomDeviceRepairId(Integer labRoomDeviceRepairId, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByLabRoomDeviceRepairId", startResult, maxRows, labRoomDeviceRepairId);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByPreDonateDeviceValue
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPreDonateDeviceValue(java.math.BigDecimal preDonateDeviceValue) throws DataAccessException {

		return findVirtualLabConstructionByPreDonateDeviceValue(preDonateDeviceValue, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByPreDonateDeviceValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPreDonateDeviceValue(java.math.BigDecimal preDonateDeviceValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByPreDonateDeviceValue", startResult, maxRows, preDonateDeviceValue);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyName
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyName(String supportAcademyName) throws DataAccessException {

		return findVirtualLabConstructionBySupportAcademyName(supportAcademyName, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionBySupportAcademyName
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionBySupportAcademyName(String supportAcademyName, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionBySupportAcademyName", startResult, maxRows, supportAcademyName);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceValue
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceValue(java.math.BigDecimal deviceValue) throws DataAccessException {

		return findVirtualLabConstructionByDeviceValue(deviceValue, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceValue
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceValue(java.math.BigDecimal deviceValue, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByDeviceValue", startResult, maxRows, deviceValue);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByPartTimePeopleAmount
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPartTimePeopleAmount(Integer partTimePeopleAmount) throws DataAccessException {

		return findVirtualLabConstructionByPartTimePeopleAmount(partTimePeopleAmount, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByPartTimePeopleAmount
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByPartTimePeopleAmount(Integer partTimePeopleAmount, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByPartTimePeopleAmount", startResult, maxRows, partTimePeopleAmount);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceMaintainFee
	 *
	 */
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceMaintainFee(java.math.BigDecimal deviceMaintainFee) throws DataAccessException {

		return findVirtualLabConstructionByDeviceMaintainFee(deviceMaintainFee, -1, -1);
	}

	/**
	 * JPQL Query - findVirtualLabConstructionByDeviceMaintainFee
	 *
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public Set<VirtualLabConstruction> findVirtualLabConstructionByDeviceMaintainFee(java.math.BigDecimal deviceMaintainFee, int startResult, int maxRows) throws DataAccessException {
		Query query = createNamedQuery("findVirtualLabConstructionByDeviceMaintainFee", startResult, maxRows, deviceMaintainFee);
		return new LinkedHashSet<VirtualLabConstruction>(query.getResultList());
	}

	/**
	 * Used to determine whether or not to merge the entity or persist the entity when calling Store
	 * @see
	 * 
	 *
	 */
	public boolean canBeMerged(VirtualLabConstruction entity) {
		return true;
	}
}
